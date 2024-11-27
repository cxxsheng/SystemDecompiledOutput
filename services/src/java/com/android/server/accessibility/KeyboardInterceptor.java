package com.android.server.accessibility;

/* loaded from: classes.dex */
public class KeyboardInterceptor extends com.android.server.accessibility.BaseEventStreamTransformation implements android.os.Handler.Callback {
    private static final java.lang.String LOG_TAG = "KeyboardInterceptor";
    private static final int MESSAGE_PROCESS_QUEUED_EVENTS = 1;
    private final com.android.server.accessibility.AccessibilityManagerService mAms;
    private com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder mEventQueueEnd;
    private com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder mEventQueueStart;
    private final android.os.Handler mHandler;
    private final com.android.server.policy.WindowManagerPolicy mPolicy;

    public KeyboardInterceptor(com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, com.android.server.policy.WindowManagerPolicy windowManagerPolicy) {
        this.mAms = accessibilityManagerService;
        this.mPolicy = windowManagerPolicy;
        this.mHandler = new android.os.Handler(this);
    }

    public KeyboardInterceptor(com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, com.android.server.policy.WindowManagerPolicy windowManagerPolicy, android.os.Handler handler) {
        this.mAms = accessibilityManagerService;
        this.mPolicy = windowManagerPolicy;
        this.mHandler = handler;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onKeyEvent(android.view.KeyEvent keyEvent, int i) {
        if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(4096L)) {
            this.mAms.getTraceManager().logTrace("KeyboardInterceptor.onKeyEvent", 4096L, "event=" + keyEvent + ";policyFlags=" + i);
        }
        long eventDelay = getEventDelay(keyEvent, i);
        if (eventDelay < 0) {
            return;
        }
        if (eventDelay > 0 || this.mEventQueueStart != null) {
            addEventToQueue(keyEvent, i, eventDelay);
        } else {
            this.mAms.notifyKeyEvent(keyEvent, i);
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        if (message.what != 1) {
            android.util.Slog.e(LOG_TAG, "Unexpected message type");
            return false;
        }
        processQueuedEvents();
        if (this.mEventQueueStart != null) {
            scheduleProcessQueuedEvents();
        }
        return true;
    }

    private void addEventToQueue(android.view.KeyEvent keyEvent, int i, long j) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis() + j;
        if (this.mEventQueueStart == null) {
            com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder obtain = com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder.obtain(keyEvent, i, uptimeMillis);
            this.mEventQueueStart = obtain;
            this.mEventQueueEnd = obtain;
            scheduleProcessQueuedEvents();
            return;
        }
        com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder obtain2 = com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder.obtain(keyEvent, i, uptimeMillis);
        obtain2.next = this.mEventQueueStart;
        this.mEventQueueStart.previous = obtain2;
        this.mEventQueueStart = obtain2;
    }

    private void scheduleProcessQueuedEvents() {
        if (!this.mHandler.sendEmptyMessageAtTime(1, this.mEventQueueEnd.dispatchTime)) {
            android.util.Slog.e(LOG_TAG, "Failed to schedule key event");
        }
    }

    private void processQueuedEvents() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        while (this.mEventQueueEnd != null && this.mEventQueueEnd.dispatchTime <= uptimeMillis) {
            long eventDelay = getEventDelay(this.mEventQueueEnd.event, this.mEventQueueEnd.policyFlags);
            if (eventDelay > 0) {
                this.mEventQueueEnd.dispatchTime = uptimeMillis + eventDelay;
                return;
            }
            if (eventDelay == 0) {
                this.mAms.notifyKeyEvent(this.mEventQueueEnd.event, this.mEventQueueEnd.policyFlags);
            }
            com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder keyEventHolder = this.mEventQueueEnd;
            this.mEventQueueEnd = this.mEventQueueEnd.previous;
            if (this.mEventQueueEnd != null) {
                this.mEventQueueEnd.next = null;
            }
            keyEventHolder.recycle();
            if (this.mEventQueueEnd == null) {
                this.mEventQueueStart = null;
            }
        }
    }

    private long getEventDelay(android.view.KeyEvent keyEvent, int i) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 25 || keyCode == 24) {
            return this.mPolicy.interceptKeyBeforeDispatching(null, keyEvent, i);
        }
        return 0L;
    }

    private static class KeyEventHolder {
        private static final int MAX_POOL_SIZE = 32;
        private static final android.util.Pools.SimplePool<com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder> sPool = new android.util.Pools.SimplePool<>(32);
        public long dispatchTime;
        public android.view.KeyEvent event;
        public com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder next;
        public int policyFlags;
        public com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder previous;

        private KeyEventHolder() {
        }

        public static com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder obtain(android.view.KeyEvent keyEvent, int i, long j) {
            com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder keyEventHolder = (com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder) sPool.acquire();
            if (keyEventHolder == null) {
                keyEventHolder = new com.android.server.accessibility.KeyboardInterceptor.KeyEventHolder();
            }
            keyEventHolder.event = android.view.KeyEvent.obtain(keyEvent);
            keyEventHolder.policyFlags = i;
            keyEventHolder.dispatchTime = j;
            return keyEventHolder;
        }

        public void recycle() {
            this.event.recycle();
            this.event = null;
            this.policyFlags = 0;
            this.dispatchTime = 0L;
            this.next = null;
            this.previous = null;
            sPool.release(this);
        }
    }
}
