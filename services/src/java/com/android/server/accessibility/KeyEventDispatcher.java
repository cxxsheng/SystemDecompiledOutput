package com.android.server.accessibility;

/* loaded from: classes.dex */
public class KeyEventDispatcher implements android.os.Handler.Callback {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = "KeyEventDispatcher";
    private static final int MAX_POOL_SIZE = 10;
    public static final int MSG_ON_KEY_EVENT_TIMEOUT = 1;
    private static final long ON_KEY_EVENT_TIMEOUT_MILLIS = 500;
    private final android.os.Handler mHandlerToSendKeyEventsToInputFilter;
    private android.os.Handler mKeyEventTimeoutHandler;
    private final java.lang.Object mLock;
    private final int mMessageTypeForSendKeyEvent;
    private final android.util.Pools.Pool<com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent> mPendingEventPool;
    private final java.util.Map<com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter, java.util.ArrayList<com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent>> mPendingEventsMap;
    private final android.os.PowerManager mPowerManager;
    private final android.view.InputEventConsistencyVerifier mSentEventsVerifier;

    public interface KeyEventFilter {
        boolean onKeyEvent(android.view.KeyEvent keyEvent, int i);
    }

    private static final class PendingKeyEvent {
        android.view.KeyEvent event;
        boolean handled;
        int policyFlags;
        int referenceCount;

        private PendingKeyEvent() {
        }
    }

    public KeyEventDispatcher(android.os.Handler handler, int i, java.lang.Object obj, android.os.PowerManager powerManager) {
        this.mPendingEventPool = new android.util.Pools.SimplePool(10);
        this.mPendingEventsMap = new android.util.ArrayMap();
        if (android.view.InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            this.mSentEventsVerifier = new android.view.InputEventConsistencyVerifier(this, 0, com.android.server.accessibility.KeyEventDispatcher.class.getSimpleName());
        } else {
            this.mSentEventsVerifier = null;
        }
        this.mHandlerToSendKeyEventsToInputFilter = handler;
        this.mMessageTypeForSendKeyEvent = i;
        this.mKeyEventTimeoutHandler = new android.os.Handler(handler.getLooper(), this);
        this.mLock = obj;
        this.mPowerManager = powerManager;
    }

    public KeyEventDispatcher(android.os.Handler handler, int i, java.lang.Object obj, android.os.PowerManager powerManager, android.os.Handler handler2) {
        this(handler, i, obj, powerManager);
        this.mKeyEventTimeoutHandler = handler2;
    }

    public boolean notifyKeyEventLocked(android.view.KeyEvent keyEvent, int i, java.util.List<? extends com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter> list) {
        android.view.KeyEvent obtain = android.view.KeyEvent.obtain(keyEvent);
        com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent pendingKeyEvent = null;
        for (int i2 = 0; i2 < list.size(); i2++) {
            com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter keyEventFilter = list.get(i2);
            if (keyEventFilter.onKeyEvent(obtain, obtain.getSequenceNumber())) {
                if (pendingKeyEvent == null) {
                    pendingKeyEvent = obtainPendingEventLocked(obtain, i);
                }
                java.util.ArrayList<com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent> arrayList = this.mPendingEventsMap.get(keyEventFilter);
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList<>();
                    this.mPendingEventsMap.put(keyEventFilter, arrayList);
                }
                arrayList.add(pendingKeyEvent);
                pendingKeyEvent.referenceCount++;
            }
        }
        if (pendingKeyEvent == null) {
            obtain.recycle();
            return false;
        }
        this.mKeyEventTimeoutHandler.sendMessageDelayed(this.mKeyEventTimeoutHandler.obtainMessage(1, pendingKeyEvent), 500L);
        return true;
    }

    public void setOnKeyEventResult(com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter keyEventFilter, boolean z, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent removeEventFromListLocked = removeEventFromListLocked(this.mPendingEventsMap.get(keyEventFilter), i);
                if (removeEventFromListLocked != null) {
                    if (z && !removeEventFromListLocked.handled) {
                        removeEventFromListLocked.handled = z;
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            this.mPowerManager.userActivity(removeEventFromListLocked.event.getEventTime(), 3, 0);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    removeReferenceToPendingEventLocked(removeEventFromListLocked);
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public void flush(com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter keyEventFilter) {
        synchronized (this.mLock) {
            try {
                java.util.ArrayList<com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent> arrayList = this.mPendingEventsMap.get(keyEventFilter);
                if (arrayList != null) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        removeReferenceToPendingEventLocked(arrayList.get(i));
                    }
                    this.mPendingEventsMap.remove(keyEventFilter);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        if (message.what != 1) {
            android.util.Slog.w(LOG_TAG, "Unknown message: " + message.what);
            return false;
        }
        com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent pendingKeyEvent = (com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent) message.obj;
        synchronized (this.mLock) {
            try {
                java.util.Iterator<java.util.ArrayList<com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent>> it = this.mPendingEventsMap.values().iterator();
                while (it.hasNext() && (!it.next().remove(pendingKeyEvent) || !removeReferenceToPendingEventLocked(pendingKeyEvent))) {
                }
            } finally {
            }
        }
        return true;
    }

    private com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent obtainPendingEventLocked(android.view.KeyEvent keyEvent, int i) {
        com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent pendingKeyEvent = (com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent) this.mPendingEventPool.acquire();
        if (pendingKeyEvent == null) {
            pendingKeyEvent = new com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent();
        }
        pendingKeyEvent.event = keyEvent;
        pendingKeyEvent.policyFlags = i;
        pendingKeyEvent.referenceCount = 0;
        pendingKeyEvent.handled = false;
        return pendingKeyEvent;
    }

    private static com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent removeEventFromListLocked(java.util.List<com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent> list, int i) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent pendingKeyEvent = list.get(i2);
            if (pendingKeyEvent.event.getSequenceNumber() == i) {
                list.remove(pendingKeyEvent);
                return pendingKeyEvent;
            }
        }
        return null;
    }

    private boolean removeReferenceToPendingEventLocked(com.android.server.accessibility.KeyEventDispatcher.PendingKeyEvent pendingKeyEvent) {
        int i = pendingKeyEvent.referenceCount - 1;
        pendingKeyEvent.referenceCount = i;
        if (i > 0) {
            return false;
        }
        this.mKeyEventTimeoutHandler.removeMessages(1, pendingKeyEvent);
        if (!pendingKeyEvent.handled) {
            if (this.mSentEventsVerifier != null) {
                this.mSentEventsVerifier.onKeyEvent(pendingKeyEvent.event, 0);
            }
            this.mHandlerToSendKeyEventsToInputFilter.obtainMessage(this.mMessageTypeForSendKeyEvent, pendingKeyEvent.policyFlags | 1073741824, 0, pendingKeyEvent.event).sendToTarget();
        } else {
            pendingKeyEvent.event.recycle();
        }
        this.mPendingEventPool.release(pendingKeyEvent);
        return true;
    }
}
