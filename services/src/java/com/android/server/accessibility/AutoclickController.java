package com.android.server.accessibility;

/* loaded from: classes.dex */
public class AutoclickController extends com.android.server.accessibility.BaseEventStreamTransformation {
    private static final java.lang.String LOG_TAG = com.android.server.accessibility.AutoclickController.class.getSimpleName();
    private com.android.server.accessibility.AutoclickController.ClickDelayObserver mClickDelayObserver;
    private com.android.server.accessibility.AutoclickController.ClickScheduler mClickScheduler;
    private final android.content.Context mContext;
    private final com.android.server.accessibility.AccessibilityTraceManager mTrace;
    private final int mUserId;

    public AutoclickController(android.content.Context context, int i, com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager) {
        this.mTrace = accessibilityTraceManager;
        this.mContext = context;
        this.mUserId = i;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mTrace.isA11yTracingEnabledForTypes(4096L)) {
            this.mTrace.logTrace(LOG_TAG + ".onMotionEvent", 4096L, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        if (motionEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1)) {
            if (this.mClickScheduler == null) {
                android.os.Handler handler = new android.os.Handler(this.mContext.getMainLooper());
                this.mClickScheduler = new com.android.server.accessibility.AutoclickController.ClickScheduler(handler, 600);
                this.mClickDelayObserver = new com.android.server.accessibility.AutoclickController.ClickDelayObserver(this.mUserId, handler);
                this.mClickDelayObserver.start(this.mContext.getContentResolver(), this.mClickScheduler);
            }
            handleMouseMotion(motionEvent, i);
        } else if (this.mClickScheduler != null) {
            this.mClickScheduler.cancel();
        }
        super.onMotionEvent(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onKeyEvent(android.view.KeyEvent keyEvent, int i) {
        if (this.mTrace.isA11yTracingEnabledForTypes(4096L)) {
            this.mTrace.logTrace(LOG_TAG + ".onKeyEvent", 4096L, "event=" + keyEvent + ";policyFlags=" + i);
        }
        if (this.mClickScheduler != null) {
            if (android.view.KeyEvent.isModifierKey(keyEvent.getKeyCode())) {
                this.mClickScheduler.updateMetaState(keyEvent.getMetaState());
            } else {
                this.mClickScheduler.cancel();
            }
        }
        super.onKeyEvent(keyEvent, i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
        if (i == 8194 && this.mClickScheduler != null) {
            this.mClickScheduler.cancel();
        }
        super.clearEvents(i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
        if (this.mClickDelayObserver != null) {
            this.mClickDelayObserver.stop();
            this.mClickDelayObserver = null;
        }
        if (this.mClickScheduler != null) {
            this.mClickScheduler.cancel();
            this.mClickScheduler = null;
        }
    }

    private void handleMouseMotion(android.view.MotionEvent motionEvent, int i) {
        switch (motionEvent.getActionMasked()) {
            case 7:
                if (motionEvent.getPointerCount() == 1) {
                    this.mClickScheduler.update(motionEvent, i);
                    break;
                } else {
                    this.mClickScheduler.cancel();
                    break;
                }
            case 8:
            default:
                this.mClickScheduler.cancel();
                break;
            case 9:
            case 10:
                break;
        }
    }

    private static final class ClickDelayObserver extends android.database.ContentObserver {
        private final android.net.Uri mAutoclickDelaySettingUri;
        private com.android.server.accessibility.AutoclickController.ClickScheduler mClickScheduler;
        private android.content.ContentResolver mContentResolver;
        private final int mUserId;

        public ClickDelayObserver(int i, android.os.Handler handler) {
            super(handler);
            this.mAutoclickDelaySettingUri = android.provider.Settings.Secure.getUriFor("accessibility_autoclick_delay");
            this.mUserId = i;
        }

        public void start(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull com.android.server.accessibility.AutoclickController.ClickScheduler clickScheduler) {
            if (this.mContentResolver != null || this.mClickScheduler != null) {
                throw new java.lang.IllegalStateException("Observer already started.");
            }
            if (contentResolver == null) {
                throw new java.lang.NullPointerException("contentResolver not set.");
            }
            if (clickScheduler == null) {
                throw new java.lang.NullPointerException("clickScheduler not set.");
            }
            this.mContentResolver = contentResolver;
            this.mClickScheduler = clickScheduler;
            this.mContentResolver.registerContentObserver(this.mAutoclickDelaySettingUri, false, this, this.mUserId);
            onChange(true, this.mAutoclickDelaySettingUri);
        }

        public void stop() {
            if (this.mContentResolver == null || this.mClickScheduler == null) {
                throw new java.lang.IllegalStateException("ClickDelayObserver not started.");
            }
            this.mContentResolver.unregisterContentObserver(this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (this.mAutoclickDelaySettingUri.equals(uri)) {
                this.mClickScheduler.updateDelay(android.provider.Settings.Secure.getIntForUser(this.mContentResolver, "accessibility_autoclick_delay", 600, this.mUserId));
            }
        }
    }

    private final class ClickScheduler implements java.lang.Runnable {
        private static final double MOVEMENT_SLOPE = 20.0d;
        private boolean mActive;
        private android.view.MotionEvent.PointerCoords mAnchorCoords;
        private int mDelay;
        private int mEventPolicyFlags;
        private android.os.Handler mHandler;
        private android.view.MotionEvent mLastMotionEvent = null;
        private int mMetaState;
        private long mScheduledClickTime;
        private android.view.MotionEvent.PointerCoords[] mTempPointerCoords;
        private android.view.MotionEvent.PointerProperties[] mTempPointerProperties;

        public ClickScheduler(android.os.Handler handler, int i) {
            this.mHandler = handler;
            resetInternalState();
            this.mDelay = i;
            this.mAnchorCoords = new android.view.MotionEvent.PointerCoords();
        }

        @Override // java.lang.Runnable
        public void run() {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (uptimeMillis < this.mScheduledClickTime) {
                this.mHandler.postDelayed(this, this.mScheduledClickTime - uptimeMillis);
            } else {
                sendClick();
                resetInternalState();
            }
        }

        public void update(android.view.MotionEvent motionEvent, int i) {
            this.mMetaState = motionEvent.getMetaState();
            boolean detectMovement = detectMovement(motionEvent);
            cacheLastEvent(motionEvent, i, this.mLastMotionEvent == null || detectMovement);
            if (detectMovement) {
                rescheduleClick(this.mDelay);
            }
        }

        public void cancel() {
            if (!this.mActive) {
                return;
            }
            resetInternalState();
            this.mHandler.removeCallbacks(this);
        }

        public void updateMetaState(int i) {
            this.mMetaState = i;
        }

        public void updateDelay(int i) {
            this.mDelay = i;
        }

        private void rescheduleClick(int i) {
            long j = i;
            long uptimeMillis = android.os.SystemClock.uptimeMillis() + j;
            if (this.mActive && uptimeMillis > this.mScheduledClickTime) {
                this.mScheduledClickTime = uptimeMillis;
                return;
            }
            if (this.mActive) {
                this.mHandler.removeCallbacks(this);
            }
            this.mActive = true;
            this.mScheduledClickTime = uptimeMillis;
            this.mHandler.postDelayed(this, j);
        }

        private void cacheLastEvent(android.view.MotionEvent motionEvent, int i, boolean z) {
            if (this.mLastMotionEvent != null) {
                this.mLastMotionEvent.recycle();
            }
            this.mLastMotionEvent = android.view.MotionEvent.obtain(motionEvent);
            this.mEventPolicyFlags = i;
            if (z) {
                this.mLastMotionEvent.getPointerCoords(this.mLastMotionEvent.getActionIndex(), this.mAnchorCoords);
            }
        }

        private void resetInternalState() {
            this.mActive = false;
            if (this.mLastMotionEvent != null) {
                this.mLastMotionEvent.recycle();
                this.mLastMotionEvent = null;
            }
            this.mScheduledClickTime = -1L;
        }

        private boolean detectMovement(android.view.MotionEvent motionEvent) {
            if (this.mLastMotionEvent == null) {
                return false;
            }
            int actionIndex = motionEvent.getActionIndex();
            return java.lang.Math.hypot((double) (this.mAnchorCoords.x - motionEvent.getX(actionIndex)), (double) (this.mAnchorCoords.y - motionEvent.getY(actionIndex))) > 20.0d;
        }

        private void sendClick() {
            if (this.mLastMotionEvent == null || com.android.server.accessibility.AutoclickController.this.getNext() == null) {
                return;
            }
            int actionIndex = this.mLastMotionEvent.getActionIndex();
            if (this.mTempPointerProperties == null) {
                this.mTempPointerProperties = new android.view.MotionEvent.PointerProperties[1];
                this.mTempPointerProperties[0] = new android.view.MotionEvent.PointerProperties();
            }
            this.mLastMotionEvent.getPointerProperties(actionIndex, this.mTempPointerProperties[0]);
            if (this.mTempPointerCoords == null) {
                this.mTempPointerCoords = new android.view.MotionEvent.PointerCoords[1];
                this.mTempPointerCoords[0] = new android.view.MotionEvent.PointerCoords();
            }
            this.mLastMotionEvent.getPointerCoords(actionIndex, this.mTempPointerCoords[0]);
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, 1, this.mTempPointerProperties, this.mTempPointerCoords, this.mMetaState, 1, 1.0f, 1.0f, this.mLastMotionEvent.getDeviceId(), 0, this.mLastMotionEvent.getSource(), this.mLastMotionEvent.getFlags());
            android.view.MotionEvent obtain2 = android.view.MotionEvent.obtain(obtain);
            obtain2.setAction(11);
            obtain2.setActionButton(1);
            android.view.MotionEvent obtain3 = android.view.MotionEvent.obtain(obtain);
            obtain3.setAction(12);
            obtain3.setActionButton(1);
            obtain3.setButtonState(0);
            android.view.MotionEvent obtain4 = android.view.MotionEvent.obtain(obtain);
            obtain4.setAction(1);
            obtain4.setButtonState(0);
            com.android.server.accessibility.AutoclickController.super.onMotionEvent(obtain, obtain, this.mEventPolicyFlags);
            obtain.recycle();
            com.android.server.accessibility.AutoclickController.super.onMotionEvent(obtain2, obtain2, this.mEventPolicyFlags);
            obtain2.recycle();
            com.android.server.accessibility.AutoclickController.super.onMotionEvent(obtain3, obtain3, this.mEventPolicyFlags);
            obtain3.recycle();
            com.android.server.accessibility.AutoclickController.super.onMotionEvent(obtain4, obtain4, this.mEventPolicyFlags);
            obtain4.recycle();
        }

        public java.lang.String toString() {
            return "ClickScheduler: { active=" + this.mActive + ", delay=" + this.mDelay + ", scheduledClickTime=" + this.mScheduledClickTime + ", anchor={x:" + this.mAnchorCoords.x + ", y:" + this.mAnchorCoords.y + "}, metastate=" + this.mMetaState + ", policyFlags=" + this.mEventPolicyFlags + ", lastMotionEvent=" + this.mLastMotionEvent + " }";
        }
    }
}
