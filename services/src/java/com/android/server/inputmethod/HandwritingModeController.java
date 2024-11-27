package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class HandwritingModeController {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final boolean DEBUG = false;
    private static final int EVENT_BUFFER_SIZE = 100;
    private static final long HANDWRITING_DELEGATION_IDLE_TIMEOUT_MS = 3000;
    private static final int LONG_EVENT_BUFFER_SIZE = 2000;
    public static final java.lang.String TAG = com.android.server.inputmethod.HandwritingModeController.class.getSimpleName();
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private java.lang.String mDelegatePackageName;
    private boolean mDelegationConnectionlessFlow;
    private android.os.Handler mDelegationIdleTimeoutHandler;
    private java.lang.Runnable mDelegationIdleTimeoutRunnable;
    private boolean mDelegatorFromDefaultHomePackage;

    @android.annotation.Nullable
    private java.lang.String mDelegatorPackageName;
    private final java.lang.Runnable mDiscardDelegationTextRunnable;
    private java.util.ArrayList<android.view.MotionEvent> mHandwritingBuffer;
    private android.view.InputEventReceiver mHandwritingEventReceiver;
    private com.android.server.inputmethod.HandwritingEventReceiverSurface mHandwritingSurface;
    private java.lang.Runnable mInkWindowInitRunnable;
    private final android.os.Looper mLooper;
    private java.util.function.IntConsumer mPointerToolTypeConsumer;
    private boolean mRecordingGesture;
    private int mCurrentDisplayId = -1;
    private final com.android.server.input.InputManagerInternal mInputManagerInternal = (com.android.server.input.InputManagerInternal) com.android.server.LocalServices.getService(com.android.server.input.InputManagerInternal.class);
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
    private int mCurrentRequestId = 0;

    HandwritingModeController(android.content.Context context, android.os.Looper looper, java.lang.Runnable runnable, java.util.function.IntConsumer intConsumer, java.lang.Runnable runnable2) {
        this.mContext = context;
        this.mLooper = looper;
        this.mInkWindowInitRunnable = runnable;
        this.mPointerToolTypeConsumer = intConsumer;
        this.mDiscardDelegationTextRunnable = runnable2;
    }

    void initializeHandwritingSpy(int i) {
        reset(i == this.mCurrentDisplayId);
        this.mCurrentDisplayId = i;
        if (this.mHandwritingBuffer == null) {
            this.mHandwritingBuffer = new java.util.ArrayList<>(getHandwritingBufferSize());
        }
        java.lang.String str = "stylus-handwriting-event-receiver-" + i;
        android.view.InputChannel createInputChannel = this.mInputManagerInternal.createInputChannel(str);
        java.util.Objects.requireNonNull(createInputChannel, "Failed to create input channel");
        android.view.SurfaceControl surface = this.mHandwritingSurface != null ? this.mHandwritingSurface.getSurface() : this.mWindowManagerInternal.getHandwritingSurfaceForDisplay(i);
        if (surface == null) {
            android.util.Slog.e(TAG, "Failed to create input surface");
            return;
        }
        this.mHandwritingSurface = new com.android.server.inputmethod.HandwritingEventReceiverSurface(str, i, surface, createInputChannel);
        this.mHandwritingEventReceiver = new android.view.BatchedInputEventReceiver.SimpleBatchedInputEventReceiver(createInputChannel.dup(), this.mLooper, android.view.Choreographer.getInstance(), new android.view.BatchedInputEventReceiver.SimpleBatchedInputEventReceiver.InputEventListener() { // from class: com.android.server.inputmethod.HandwritingModeController$$ExternalSyntheticLambda2
            public final boolean onInputEvent(android.view.InputEvent inputEvent) {
                boolean onInputEvent;
                onInputEvent = com.android.server.inputmethod.HandwritingModeController.this.onInputEvent(inputEvent);
                return onInputEvent;
            }
        });
        this.mCurrentRequestId++;
    }

    java.util.OptionalInt getCurrentRequestId() {
        if (this.mHandwritingSurface == null) {
            android.util.Slog.e(TAG, "Cannot get requestId: Handwriting was not initialized.");
            return java.util.OptionalInt.empty();
        }
        return java.util.OptionalInt.of(this.mCurrentRequestId);
    }

    boolean isStylusGestureOngoing() {
        return this.mRecordingGesture;
    }

    boolean hasOngoingStylusHandwritingSession() {
        return this.mHandwritingSurface != null && this.mHandwritingSurface.isIntercepting();
    }

    void prepareStylusHandwritingDelegation(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, boolean z) {
        android.content.ComponentName defaultHomeActivity;
        this.mDelegatePackageName = str;
        this.mDelegatorPackageName = str2;
        this.mDelegatorFromDefaultHomePackage = false;
        if (!str2.equals(str) && (defaultHomeActivity = this.mPackageManagerInternal.getDefaultHomeActivity(i)) != null) {
            this.mDelegatorFromDefaultHomePackage = str2.equals(defaultHomeActivity.getPackageName());
        }
        this.mDelegationConnectionlessFlow = z;
        if (!z) {
            if (this.mHandwritingBuffer == null) {
                this.mHandwritingBuffer = new java.util.ArrayList<>(getHandwritingBufferSize());
            } else {
                this.mHandwritingBuffer.ensureCapacity(getHandwritingBufferSize());
            }
        }
        scheduleHandwritingDelegationTimeout();
    }

    @android.annotation.Nullable
    java.lang.String getDelegatePackageName() {
        return this.mDelegatePackageName;
    }

    @android.annotation.Nullable
    java.lang.String getDelegatorPackageName() {
        return this.mDelegatorPackageName;
    }

    boolean isDelegatorFromDefaultHomePackage() {
        return this.mDelegatorFromDefaultHomePackage;
    }

    boolean isDelegationUsingConnectionlessFlow() {
        return this.mDelegationConnectionlessFlow;
    }

    private void scheduleHandwritingDelegationTimeout() {
        if (this.mDelegationIdleTimeoutHandler == null) {
            this.mDelegationIdleTimeoutHandler = new android.os.Handler(this.mLooper);
        } else {
            this.mDelegationIdleTimeoutHandler.removeCallbacks(this.mDelegationIdleTimeoutRunnable);
        }
        this.mDelegationIdleTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.inputmethod.HandwritingModeController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.inputmethod.HandwritingModeController.this.lambda$scheduleHandwritingDelegationTimeout$0();
            }
        };
        this.mDelegationIdleTimeoutHandler.postDelayed(this.mDelegationIdleTimeoutRunnable, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleHandwritingDelegationTimeout$0() {
        android.util.Slog.d(TAG, "Stylus handwriting delegation idle timed-out.");
        clearPendingHandwritingDelegation();
        if (this.mHandwritingBuffer != null) {
            this.mHandwritingBuffer.forEach(new com.android.server.inputmethod.HandwritingModeController$$ExternalSyntheticLambda1());
            this.mHandwritingBuffer.clear();
            this.mHandwritingBuffer.trimToSize();
            this.mHandwritingBuffer.ensureCapacity(getHandwritingBufferSize());
        }
    }

    private int getHandwritingBufferSize() {
        if (this.mDelegatePackageName != null && this.mDelegatorPackageName != null) {
            return 2000;
        }
        return 100;
    }

    void clearPendingHandwritingDelegation() {
        if (this.mDelegationIdleTimeoutHandler != null) {
            this.mDelegationIdleTimeoutHandler.removeCallbacks(this.mDelegationIdleTimeoutRunnable);
            this.mDelegationIdleTimeoutHandler = null;
        }
        this.mDelegationIdleTimeoutRunnable = null;
        this.mDelegatorPackageName = null;
        this.mDelegatePackageName = null;
        this.mDelegatorFromDefaultHomePackage = false;
        if (this.mDelegationConnectionlessFlow) {
            this.mDelegationConnectionlessFlow = false;
            this.mDiscardDelegationTextRunnable.run();
        }
    }

    @android.annotation.RequiresPermission("android.permission.MONITOR_INPUT")
    @android.annotation.Nullable
    com.android.server.inputmethod.HandwritingModeController.HandwritingSession startHandwritingSession(int i, int i2, int i3, android.os.IBinder iBinder) {
        clearPendingHandwritingDelegation();
        if (this.mHandwritingSurface == null) {
            android.util.Slog.e(TAG, "Cannot start handwriting session: Handwriting was not initialized.");
            return null;
        }
        if (i != this.mCurrentRequestId) {
            android.util.Slog.e(TAG, "Cannot start handwriting session: Invalid request id: " + i);
            return null;
        }
        if (!this.mRecordingGesture || this.mHandwritingBuffer.isEmpty()) {
            android.util.Slog.e(TAG, "Cannot start handwriting session: No stylus gesture is being recorded.");
            return null;
        }
        java.util.Objects.requireNonNull(this.mHandwritingEventReceiver, "Handwriting session was already transferred to IME.");
        android.view.MotionEvent motionEvent = this.mHandwritingBuffer.get(0);
        if (!this.mWindowManagerInternal.isPointInsideWindow(iBinder, this.mCurrentDisplayId, motionEvent.getRawX(), motionEvent.getRawY())) {
            android.util.Slog.e(TAG, "Cannot start handwriting session: Stylus gesture did not start inside the focused window.");
            return null;
        }
        android.hardware.input.InputManagerGlobal.getInstance().pilferPointers(this.mHandwritingSurface.getInputChannel().getToken());
        this.mHandwritingEventReceiver.dispose();
        this.mHandwritingEventReceiver = null;
        this.mRecordingGesture = false;
        if (this.mHandwritingSurface.isIntercepting()) {
            throw new java.lang.IllegalStateException("Handwriting surface should not be already intercepting.");
        }
        this.mHandwritingSurface.startIntercepting(i2, i3);
        if (com.android.input.flags.Flags.enablePointerChoreographer()) {
            android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
            java.util.Objects.requireNonNull(inputManager);
            inputManager.setPointerIcon(android.view.PointerIcon.getSystemIcon(this.mContext, 1), motionEvent.getDisplayId(), motionEvent.getDeviceId(), motionEvent.getPointerId(0), this.mHandwritingSurface.getInputChannel().getToken());
        } else {
            android.hardware.input.InputManagerGlobal.getInstance().setPointerIconType(1);
        }
        return new com.android.server.inputmethod.HandwritingModeController.HandwritingSession(this.mCurrentRequestId, this.mHandwritingSurface.getInputChannel(), this.mHandwritingBuffer);
    }

    void reset() {
        reset(false);
    }

    void setInkWindowInitializer(java.lang.Runnable runnable) {
        this.mInkWindowInitRunnable = runnable;
    }

    private void reset(boolean z) {
        if (this.mHandwritingEventReceiver != null) {
            this.mHandwritingEventReceiver.dispose();
            this.mHandwritingEventReceiver = null;
        }
        if (this.mHandwritingBuffer != null) {
            this.mHandwritingBuffer.forEach(new com.android.server.inputmethod.HandwritingModeController$$ExternalSyntheticLambda1());
            this.mHandwritingBuffer.clear();
            if (!z) {
                this.mHandwritingBuffer = null;
            }
        }
        if (this.mHandwritingSurface != null) {
            this.mHandwritingSurface.getInputChannel().dispose();
            if (!z) {
                this.mHandwritingSurface.remove();
                this.mHandwritingSurface = null;
            }
        }
        if (!this.mDelegationConnectionlessFlow) {
            clearPendingHandwritingDelegation();
        }
        this.mRecordingGesture = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onInputEvent(android.view.InputEvent inputEvent) {
        if (this.mHandwritingEventReceiver == null) {
            throw new java.lang.IllegalStateException("Input Event should not be processed when IME has the spy channel.");
        }
        if (!(inputEvent instanceof android.view.MotionEvent)) {
            android.util.Slog.wtf(TAG, "Received non-motion event in stylus monitor.");
            return false;
        }
        android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
        if (this.mPointerToolTypeConsumer != null && motionEvent.getAction() == 0) {
            this.mPointerToolTypeConsumer.accept(motionEvent.getToolType(motionEvent.getActionIndex()));
        }
        if (!motionEvent.isStylusPointer()) {
            return false;
        }
        if (motionEvent.getDisplayId() != this.mCurrentDisplayId) {
            android.util.Slog.wtf(TAG, "Received stylus event associated with the incorrect display.");
            return false;
        }
        onStylusEvent(motionEvent);
        return true;
    }

    private void onStylusEvent(android.view.MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.mInkWindowInitRunnable != null && (actionMasked == 9 || motionEvent.getAction() == 9)) {
            this.mInkWindowInitRunnable.run();
            this.mInkWindowInitRunnable = null;
            return;
        }
        if (motionEvent.isHoverEvent()) {
            return;
        }
        if ((android.text.TextUtils.isEmpty(this.mDelegatePackageName) || this.mDelegationConnectionlessFlow) && (actionMasked == 1 || actionMasked == 3)) {
            this.mRecordingGesture = false;
            this.mHandwritingBuffer.clear();
            return;
        }
        if (actionMasked == 0) {
            this.mRecordingGesture = true;
        }
        if (!this.mRecordingGesture) {
            return;
        }
        if (this.mHandwritingBuffer.size() >= getHandwritingBufferSize()) {
            this.mRecordingGesture = false;
        } else {
            this.mHandwritingBuffer.add(android.view.MotionEvent.obtain(motionEvent));
        }
    }

    static final class HandwritingSession {
        private final android.view.InputChannel mHandwritingChannel;
        private final java.util.List<android.view.MotionEvent> mRecordedEvents;
        private final int mRequestId;

        private HandwritingSession(int i, android.view.InputChannel inputChannel, java.util.List<android.view.MotionEvent> list) {
            this.mRequestId = i;
            this.mHandwritingChannel = inputChannel;
            this.mRecordedEvents = list;
        }

        int getRequestId() {
            return this.mRequestId;
        }

        android.view.InputChannel getHandwritingChannel() {
            return this.mHandwritingChannel;
        }

        java.util.List<android.view.MotionEvent> getRecordedEvents() {
            return this.mRecordedEvents;
        }
    }
}
