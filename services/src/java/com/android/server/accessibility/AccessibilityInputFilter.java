package com.android.server.accessibility;

/* loaded from: classes.dex */
class AccessibilityInputFilter extends android.view.InputFilter implements com.android.server.accessibility.EventStreamTransformation {
    private static final boolean DEBUG = false;
    static final int FEATURES_AFFECTING_MOTION_EVENTS = 7131;
    static final int FLAG_FEATURE_AUTOCLICK = 8;
    static final int FLAG_FEATURE_CONTROL_SCREEN_MAGNIFIER = 32;
    static final int FLAG_FEATURE_FILTER_KEY_EVENTS = 4;
    static final int FLAG_FEATURE_INJECT_MOTION_EVENTS = 16;
    static final int FLAG_FEATURE_INTERCEPT_GENERIC_MOTION_EVENTS = 2048;
    static final int FLAG_FEATURE_MAGNIFICATION_SINGLE_FINGER_TRIPLE_TAP = 1;
    static final int FLAG_FEATURE_MAGNIFICATION_TWO_FINGER_TRIPLE_TAP = 4096;
    static final int FLAG_FEATURE_TOUCH_EXPLORATION = 2;
    static final int FLAG_FEATURE_TRIGGERED_SCREEN_MAGNIFIER = 64;
    static final int FLAG_REQUEST_2_FINGER_PASSTHROUGH = 512;
    static final int FLAG_REQUEST_MULTI_FINGER_GESTURES = 256;
    static final int FLAG_SEND_MOTION_EVENTS = 1024;
    static final int FLAG_SERVICE_HANDLES_DOUBLE_TAP = 128;
    private static final java.lang.String TAG = com.android.server.accessibility.AccessibilityInputFilter.class.getSimpleName();
    private final com.android.server.accessibility.AccessibilityManagerService mAms;
    private com.android.server.accessibility.AutoclickController mAutoclickController;
    private int mCombinedGenericMotionEventSources;
    private int mCombinedMotionEventObservedSources;
    private final android.content.Context mContext;
    private int mEnabledFeatures;
    private final android.util.SparseArray<com.android.server.accessibility.EventStreamTransformation> mEventHandler;
    private com.android.server.accessibility.AccessibilityInputFilter.GenericMotionEventStreamState mGenericMotionEventStreamState;
    private boolean mInstalled;
    private com.android.server.accessibility.KeyboardInterceptor mKeyboardInterceptor;
    private com.android.server.accessibility.AccessibilityInputFilter.EventStreamState mKeyboardStreamState;
    private android.view.MotionEvent mLastActiveDeviceMotionEvent;
    private final android.util.SparseArray<com.android.server.accessibility.magnification.MagnificationGestureHandler> mMagnificationGestureHandler;
    private final android.util.SparseArray<com.android.server.accessibility.MotionEventInjector> mMotionEventInjectors;
    private final android.util.SparseArray<com.android.server.accessibility.AccessibilityInputFilter.EventStreamState> mMouseStreamStates;
    private final android.os.PowerManager mPm;
    private android.util.SparseArray<java.lang.Boolean> mServiceDetectsGestures;
    private final android.util.SparseArray<com.android.server.accessibility.gestures.TouchExplorer> mTouchExplorer;
    private final android.util.SparseArray<com.android.server.accessibility.AccessibilityInputFilter.EventStreamState> mTouchScreenStreamStates;
    private int mUserId;

    private static android.view.MotionEvent cancelMotion(android.view.MotionEvent motionEvent) {
        int i;
        int pointerCount;
        if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 10 || motionEvent.getActionMasked() == 1) {
            throw new java.lang.IllegalArgumentException("Can't cancel " + motionEvent);
        }
        if (motionEvent.getActionMasked() == 9 || motionEvent.getActionMasked() == 7) {
            i = 10;
        } else {
            i = 3;
        }
        if (motionEvent.getActionMasked() == 6) {
            pointerCount = motionEvent.getPointerCount() - 1;
        } else {
            pointerCount = motionEvent.getPointerCount();
        }
        android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = new android.view.MotionEvent.PointerProperties[pointerCount];
        android.view.MotionEvent.PointerCoords[] pointerCoordsArr = new android.view.MotionEvent.PointerCoords[pointerCount];
        int i2 = 0;
        for (int i3 = 0; i3 < motionEvent.getPointerCount(); i3++) {
            if (motionEvent.getActionMasked() != 6 || motionEvent.getActionIndex() != i3) {
                android.view.MotionEvent.PointerCoords pointerCoords = new android.view.MotionEvent.PointerCoords();
                pointerCoords.x = motionEvent.getX(i3);
                pointerCoords.y = motionEvent.getY(i3);
                pointerCoordsArr[i2] = pointerCoords;
                android.view.MotionEvent.PointerProperties pointerProperties = new android.view.MotionEvent.PointerProperties();
                pointerProperties.id = motionEvent.getPointerId(i3);
                pointerProperties.toolType = motionEvent.getToolType(i3);
                pointerPropertiesArr[i2] = pointerProperties;
                i2++;
            }
        }
        return android.view.MotionEvent.obtain(motionEvent.getDownTime(), android.os.SystemClock.uptimeMillis(), i, pointerCount, pointerPropertiesArr, pointerCoordsArr, motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getDisplayId(), motionEvent.getFlags(), motionEvent.getClassification());
    }

    AccessibilityInputFilter(android.content.Context context, com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService) {
        this(context, accessibilityManagerService, new android.util.SparseArray(0));
    }

    AccessibilityInputFilter(android.content.Context context, com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, android.util.SparseArray<com.android.server.accessibility.EventStreamTransformation> sparseArray) {
        super(context.getMainLooper());
        this.mTouchExplorer = new android.util.SparseArray<>(0);
        this.mMagnificationGestureHandler = new android.util.SparseArray<>(0);
        this.mMotionEventInjectors = new android.util.SparseArray<>(0);
        this.mServiceDetectsGestures = new android.util.SparseArray<>();
        this.mMouseStreamStates = new android.util.SparseArray<>(0);
        this.mTouchScreenStreamStates = new android.util.SparseArray<>(0);
        this.mCombinedGenericMotionEventSources = 0;
        this.mCombinedMotionEventObservedSources = 0;
        this.mLastActiveDeviceMotionEvent = null;
        this.mContext = context;
        this.mAms = accessibilityManagerService;
        this.mPm = (android.os.PowerManager) context.getSystemService("power");
        this.mEventHandler = sparseArray;
    }

    public void onInstalled() {
        this.mInstalled = true;
        disableFeatures();
        enableFeatures();
        this.mAms.onInputFilterInstalled(true);
        super.onInstalled();
    }

    public void onUninstalled() {
        this.mInstalled = false;
        disableFeatures();
        this.mAms.onInputFilterInstalled(false);
        super.onUninstalled();
    }

    void onDisplayAdded(@android.annotation.NonNull android.view.Display display) {
        enableFeaturesForDisplayIfInstalled(display);
    }

    void onDisplayRemoved(int i) {
        disableFeaturesForDisplayIfInstalled(i);
    }

    public void onInputEvent(android.view.InputEvent inputEvent, int i) {
        if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(4096L)) {
            this.mAms.getTraceManager().logTrace(TAG + ".onInputEvent", 4096L, "event=" + inputEvent + ";policyFlags=" + i);
        }
        com.android.server.accessibility.Flags.handleMultiDeviceInput();
        onInputEventInternal(inputEvent, i);
    }

    private void onInputEventInternal(android.view.InputEvent inputEvent, int i) {
        if (this.mEventHandler.size() == 0) {
            super.onInputEvent(inputEvent, i);
            return;
        }
        com.android.server.accessibility.AccessibilityInputFilter.EventStreamState eventStreamState = getEventStreamState(inputEvent);
        if (eventStreamState == null) {
            super.onInputEvent(inputEvent, i);
            return;
        }
        int source = inputEvent.getSource();
        int displayId = inputEvent.getDisplayId();
        if ((1073741824 & i) == 0) {
            eventStreamState.reset();
            clearEventStreamHandler(displayId, source);
            super.onInputEvent(inputEvent, i);
            return;
        }
        if (eventStreamState.updateInputSource(inputEvent.getSource())) {
            clearEventStreamHandler(displayId, source);
        }
        if (!eventStreamState.inputSourceValid()) {
            super.onInputEvent(inputEvent, i);
            return;
        }
        if (inputEvent instanceof android.view.MotionEvent) {
            if ((this.mEnabledFeatures & FEATURES_AFFECTING_MOTION_EVENTS) != 0) {
                processMotionEvent(eventStreamState, (android.view.MotionEvent) inputEvent, i);
                return;
            } else {
                super.onInputEvent(inputEvent, i);
                return;
            }
        }
        if (inputEvent instanceof android.view.KeyEvent) {
            processKeyEvent(eventStreamState, (android.view.KeyEvent) inputEvent, i);
        }
    }

    private com.android.server.accessibility.AccessibilityInputFilter.EventStreamState getEventStreamState(android.view.InputEvent inputEvent) {
        if (inputEvent instanceof android.view.MotionEvent) {
            int displayId = inputEvent.getDisplayId();
            if (this.mGenericMotionEventStreamState == null) {
                this.mGenericMotionEventStreamState = new com.android.server.accessibility.AccessibilityInputFilter.GenericMotionEventStreamState();
            }
            if (this.mGenericMotionEventStreamState.shouldProcessMotionEvent((android.view.MotionEvent) inputEvent)) {
                return this.mGenericMotionEventStreamState;
            }
            if (inputEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3)) {
                com.android.server.accessibility.AccessibilityInputFilter.EventStreamState eventStreamState = this.mTouchScreenStreamStates.get(displayId);
                if (eventStreamState == null) {
                    com.android.server.accessibility.AccessibilityInputFilter.TouchScreenEventStreamState touchScreenEventStreamState = new com.android.server.accessibility.AccessibilityInputFilter.TouchScreenEventStreamState();
                    this.mTouchScreenStreamStates.put(displayId, touchScreenEventStreamState);
                    return touchScreenEventStreamState;
                }
                return eventStreamState;
            }
            if (inputEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1)) {
                com.android.server.accessibility.AccessibilityInputFilter.EventStreamState eventStreamState2 = this.mMouseStreamStates.get(displayId);
                if (eventStreamState2 == null) {
                    com.android.server.accessibility.AccessibilityInputFilter.MouseEventStreamState mouseEventStreamState = new com.android.server.accessibility.AccessibilityInputFilter.MouseEventStreamState();
                    this.mMouseStreamStates.put(displayId, mouseEventStreamState);
                    return mouseEventStreamState;
                }
                return eventStreamState2;
            }
        } else if ((inputEvent instanceof android.view.KeyEvent) && inputEvent.isFromSource(257)) {
            if (this.mKeyboardStreamState == null) {
                this.mKeyboardStreamState = new com.android.server.accessibility.AccessibilityInputFilter.KeyboardEventStreamState();
            }
            return this.mKeyboardStreamState;
        }
        return null;
    }

    private void clearEventStreamHandler(int i, int i2) {
        com.android.server.accessibility.EventStreamTransformation eventStreamTransformation = this.mEventHandler.get(i);
        if (eventStreamTransformation != null) {
            eventStreamTransformation.clearEvents(i2);
        }
    }

    boolean shouldProcessMultiDeviceEvent(android.view.InputEvent inputEvent, int i) {
        if (inputEvent instanceof android.view.MotionEvent) {
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
            if (motionEvent.isFromSource(2) && motionEvent.getAction() != 8) {
                boolean z = this.mLastActiveDeviceMotionEvent != null && this.mLastActiveDeviceMotionEvent.getDeviceId() == motionEvent.getDeviceId();
                switch (motionEvent.getActionMasked()) {
                    case 0:
                    case 7:
                    case 9:
                        if (this.mLastActiveDeviceMotionEvent != null && this.mLastActiveDeviceMotionEvent.getDeviceId() != motionEvent.getDeviceId()) {
                            onInputEventInternal(cancelMotion(this.mLastActiveDeviceMotionEvent), i);
                        }
                        this.mLastActiveDeviceMotionEvent = android.view.MotionEvent.obtain(motionEvent);
                        return true;
                    case 1:
                    case 3:
                    case 10:
                        if (!z) {
                            return false;
                        }
                        this.mLastActiveDeviceMotionEvent = null;
                        return true;
                    case 2:
                    case 5:
                    case 6:
                        if (!z) {
                            return false;
                        }
                        this.mLastActiveDeviceMotionEvent = android.view.MotionEvent.obtain(motionEvent);
                        return true;
                    case 4:
                    case 8:
                    default:
                        if (this.mLastActiveDeviceMotionEvent != null && inputEvent.getDeviceId() != this.mLastActiveDeviceMotionEvent.getDeviceId()) {
                            return false;
                        }
                        break;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    private void processMotionEvent(com.android.server.accessibility.AccessibilityInputFilter.EventStreamState eventStreamState, android.view.MotionEvent motionEvent, int i) {
        if (!eventStreamState.shouldProcessScroll() && motionEvent.getActionMasked() == 8) {
            super.onInputEvent(motionEvent, i);
        } else {
            if (!eventStreamState.shouldProcessMotionEvent(motionEvent)) {
                return;
            }
            handleMotionEvent(motionEvent, i);
        }
    }

    private void processKeyEvent(com.android.server.accessibility.AccessibilityInputFilter.EventStreamState eventStreamState, android.view.KeyEvent keyEvent, int i) {
        if (!eventStreamState.shouldProcessKeyEvent(keyEvent)) {
            super.onInputEvent(keyEvent, i);
        } else {
            this.mEventHandler.get(0).onKeyEvent(keyEvent, i);
        }
    }

    private void handleMotionEvent(android.view.MotionEvent motionEvent, int i) {
        this.mPm.userActivity(motionEvent.getEventTime(), false);
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(motionEvent);
        int displayId = motionEvent.getDisplayId();
        com.android.server.accessibility.EventStreamTransformation eventStreamTransformation = this.mEventHandler.get(isDisplayIdValid(displayId) ? displayId : 0);
        if (eventStreamTransformation != null) {
            eventStreamTransformation.onMotionEvent(obtain, motionEvent, i);
        }
        obtain.recycle();
    }

    private boolean isDisplayIdValid(int i) {
        return this.mEventHandler.get(i) != null;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (!this.mInstalled) {
            android.util.Slog.w(TAG, "onMotionEvent called before input filter installed!");
        } else {
            sendInputEvent(motionEvent, i);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onKeyEvent(android.view.KeyEvent keyEvent, int i) {
        if (!this.mInstalled) {
            android.util.Slog.w(TAG, "onKeyEvent called before input filter installed!");
        } else {
            sendInputEvent(keyEvent, i);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void setNext(com.android.server.accessibility.EventStreamTransformation eventStreamTransformation) {
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public com.android.server.accessibility.EventStreamTransformation getNext() {
        return null;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
    }

    void setUserAndEnabledFeatures(int i, int i2) {
        if (this.mEnabledFeatures == i2 && this.mUserId == i) {
            return;
        }
        if (this.mInstalled) {
            disableFeatures();
        }
        this.mUserId = i;
        this.mEnabledFeatures = i2;
        if (this.mInstalled) {
            enableFeatures();
        }
    }

    void notifyAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        for (int i = 0; i < this.mEventHandler.size(); i++) {
            com.android.server.accessibility.EventStreamTransformation valueAt = this.mEventHandler.valueAt(i);
            if (valueAt != null) {
                valueAt.onAccessibilityEvent(accessibilityEvent);
            }
        }
    }

    void notifyAccessibilityButtonClicked(int i) {
        com.android.server.accessibility.magnification.MagnificationGestureHandler magnificationGestureHandler;
        if (this.mMagnificationGestureHandler.size() != 0 && (magnificationGestureHandler = this.mMagnificationGestureHandler.get(i)) != null) {
            magnificationGestureHandler.notifyShortcutTriggered();
        }
    }

    private void enableFeatures() {
        resetAllStreamState();
        java.util.ArrayList<android.view.Display> validDisplayList = this.mAms.getValidDisplayList();
        for (int size = validDisplayList.size() - 1; size >= 0; size--) {
            enableFeaturesForDisplay(validDisplayList.get(size));
        }
        enableDisplayIndependentFeatures();
    }

    private void enableFeaturesForDisplay(android.view.Display display) {
        android.content.Context createDisplayContext = this.mContext.createDisplayContext(display);
        int displayId = display.getDisplayId();
        if (this.mAms.isDisplayProxyed(displayId)) {
            return;
        }
        if (!this.mServiceDetectsGestures.contains(displayId)) {
            this.mServiceDetectsGestures.put(displayId, false);
        }
        if ((this.mEnabledFeatures & 8) != 0) {
            if (this.mAutoclickController == null) {
                this.mAutoclickController = new com.android.server.accessibility.AutoclickController(this.mContext, this.mUserId, this.mAms.getTraceManager());
            }
            addFirstEventHandler(displayId, this.mAutoclickController);
        }
        if ((this.mEnabledFeatures & 2) != 0) {
            com.android.server.accessibility.gestures.TouchExplorer touchExplorer = new com.android.server.accessibility.gestures.TouchExplorer(createDisplayContext, this.mAms);
            if ((this.mEnabledFeatures & 128) != 0) {
                touchExplorer.setServiceHandlesDoubleTap(true);
            }
            if ((this.mEnabledFeatures & 256) != 0) {
                touchExplorer.setMultiFingerGesturesEnabled(true);
            }
            if ((this.mEnabledFeatures & 512) != 0) {
                touchExplorer.setTwoFingerPassthroughEnabled(true);
            }
            if ((this.mEnabledFeatures & 1024) != 0) {
                touchExplorer.setSendMotionEventsEnabled(true);
            }
            touchExplorer.setServiceDetectsGestures(this.mServiceDetectsGestures.get(displayId).booleanValue());
            addFirstEventHandler(displayId, touchExplorer);
            this.mTouchExplorer.put(displayId, touchExplorer);
        }
        if ((this.mEnabledFeatures & 2048) != 0) {
            addFirstEventHandler(displayId, new com.android.server.accessibility.BaseEventStreamTransformation() { // from class: com.android.server.accessibility.AccessibilityInputFilter.1
                @Override // com.android.server.accessibility.EventStreamTransformation
                public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
                    boolean z;
                    boolean z2 = true;
                    if (com.android.server.accessibility.AccessibilityInputFilter.this.anyServiceWantsGenericMotionEvent(motionEvent)) {
                        if (!com.android.server.accessibility.AccessibilityInputFilter.this.mAms.sendMotionEventToListeningServices(motionEvent)) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (!com.android.server.accessibility.AccessibilityInputFilter.this.anyServiceWantsToObserveMotionEvent(motionEvent)) {
                            z2 = z;
                        }
                    }
                    if (z2) {
                        super.onMotionEvent(motionEvent, motionEvent2, i);
                    }
                }
            });
        }
        if ((this.mEnabledFeatures & 32) != 0 || (this.mEnabledFeatures & 1) != 0 || (this.mEnabledFeatures & 4096) != 0 || (this.mEnabledFeatures & 64) != 0) {
            com.android.server.accessibility.magnification.MagnificationGestureHandler createMagnificationGestureHandler = createMagnificationGestureHandler(displayId, createDisplayContext);
            addFirstEventHandler(displayId, createMagnificationGestureHandler);
            this.mMagnificationGestureHandler.put(displayId, createMagnificationGestureHandler);
        }
        if ((this.mEnabledFeatures & 16) != 0) {
            com.android.server.accessibility.MotionEventInjector motionEventInjector = new com.android.server.accessibility.MotionEventInjector(this.mContext.getMainLooper(), this.mAms.getTraceManager());
            addFirstEventHandler(displayId, motionEventInjector);
            this.mMotionEventInjectors.put(displayId, motionEventInjector);
        }
    }

    private void enableDisplayIndependentFeatures() {
        if ((this.mEnabledFeatures & 16) != 0) {
            this.mAms.setMotionEventInjectors(this.mMotionEventInjectors);
        }
        if ((this.mEnabledFeatures & 4) != 0) {
            this.mKeyboardInterceptor = new com.android.server.accessibility.KeyboardInterceptor(this.mAms, (com.android.server.policy.WindowManagerPolicy) com.android.server.LocalServices.getService(com.android.server.policy.WindowManagerPolicy.class));
            addFirstEventHandler(0, this.mKeyboardInterceptor);
        }
    }

    private void addFirstEventHandler(int i, com.android.server.accessibility.EventStreamTransformation eventStreamTransformation) {
        com.android.server.accessibility.EventStreamTransformation eventStreamTransformation2 = this.mEventHandler.get(i);
        if (eventStreamTransformation2 != null) {
            eventStreamTransformation.setNext(eventStreamTransformation2);
        } else {
            eventStreamTransformation.setNext(this);
        }
        this.mEventHandler.put(i, eventStreamTransformation);
    }

    private void disableFeatures() {
        java.util.ArrayList<android.view.Display> validDisplayList = this.mAms.getValidDisplayList();
        for (int size = validDisplayList.size() - 1; size >= 0; size--) {
            disableFeaturesForDisplay(validDisplayList.get(size).getDisplayId());
        }
        this.mAms.setMotionEventInjectors(null);
        disableDisplayIndependentFeatures();
        resetAllStreamState();
    }

    private void disableFeaturesForDisplay(int i) {
        com.android.server.accessibility.MotionEventInjector motionEventInjector = this.mMotionEventInjectors.get(i);
        if (motionEventInjector != null) {
            motionEventInjector.onDestroy();
            this.mMotionEventInjectors.remove(i);
        }
        com.android.server.accessibility.gestures.TouchExplorer touchExplorer = this.mTouchExplorer.get(i);
        if (touchExplorer != null) {
            touchExplorer.onDestroy();
            this.mTouchExplorer.remove(i);
        }
        com.android.server.accessibility.magnification.MagnificationGestureHandler magnificationGestureHandler = this.mMagnificationGestureHandler.get(i);
        if (magnificationGestureHandler != null) {
            magnificationGestureHandler.onDestroy();
            this.mMagnificationGestureHandler.remove(i);
        }
        if (this.mEventHandler.get(i) != null) {
            this.mEventHandler.remove(i);
        }
    }

    void enableFeaturesForDisplayIfInstalled(android.view.Display display) {
        if (this.mInstalled) {
            resetStreamStateForDisplay(display.getDisplayId());
            enableFeaturesForDisplay(display);
        }
    }

    void disableFeaturesForDisplayIfInstalled(int i) {
        if (this.mInstalled) {
            disableFeaturesForDisplay(i);
            resetStreamStateForDisplay(i);
        }
    }

    private void disableDisplayIndependentFeatures() {
        if (this.mAutoclickController != null) {
            this.mAutoclickController.onDestroy();
            this.mAutoclickController = null;
        }
        if (this.mKeyboardInterceptor != null) {
            this.mKeyboardInterceptor.onDestroy();
            this.mKeyboardInterceptor = null;
        }
    }

    private com.android.server.accessibility.magnification.MagnificationGestureHandler createMagnificationGestureHandler(int i, android.content.Context context) {
        boolean z = (this.mEnabledFeatures & 1) != 0;
        boolean z2 = (this.mEnabledFeatures & 4096) != 0;
        boolean z3 = (this.mEnabledFeatures & 64) != 0;
        if (this.mAms.getMagnificationMode(i) == 2) {
            return new com.android.server.accessibility.magnification.WindowMagnificationGestureHandler(context.createWindowContext(2039, null), this.mAms.getMagnificationConnectionManager(), this.mAms.getTraceManager(), this.mAms.getMagnificationController(), z, z2, z3, i);
        }
        android.content.Context createWindowContext = context.createWindowContext(2027, null);
        return new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler(createWindowContext, this.mAms.getMagnificationController().getFullScreenMagnificationController(), this.mAms.getTraceManager(), this.mAms.getMagnificationController(), z, z2, z3, new com.android.server.accessibility.magnification.WindowMagnificationPromptController(context, this.mUserId), i, new com.android.server.accessibility.magnification.FullScreenMagnificationVibrationHelper(createWindowContext));
    }

    void resetAllStreamState() {
        java.util.ArrayList<android.view.Display> validDisplayList = this.mAms.getValidDisplayList();
        for (int size = validDisplayList.size() - 1; size >= 0; size--) {
            resetStreamStateForDisplay(validDisplayList.get(size).getDisplayId());
        }
        if (this.mKeyboardStreamState != null) {
            this.mKeyboardStreamState.reset();
        }
    }

    void resetStreamStateForDisplay(int i) {
        com.android.server.accessibility.AccessibilityInputFilter.EventStreamState eventStreamState = this.mTouchScreenStreamStates.get(i);
        if (eventStreamState != null) {
            eventStreamState.reset();
            this.mTouchScreenStreamStates.remove(i);
        }
        com.android.server.accessibility.AccessibilityInputFilter.EventStreamState eventStreamState2 = this.mMouseStreamStates.get(i);
        if (eventStreamState2 != null) {
            eventStreamState2.reset();
            this.mMouseStreamStates.remove(i);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
    }

    public void refreshMagnificationMode(android.view.Display display) {
        int displayId = display.getDisplayId();
        com.android.server.accessibility.magnification.MagnificationGestureHandler magnificationGestureHandler = this.mMagnificationGestureHandler.get(displayId);
        if (magnificationGestureHandler == null || magnificationGestureHandler.getMode() == this.mAms.getMagnificationMode(displayId)) {
            return;
        }
        magnificationGestureHandler.onDestroy();
        com.android.server.accessibility.magnification.MagnificationGestureHandler createMagnificationGestureHandler = createMagnificationGestureHandler(displayId, this.mContext.createDisplayContext(display));
        switchEventStreamTransformation(displayId, magnificationGestureHandler, createMagnificationGestureHandler);
        this.mMagnificationGestureHandler.put(displayId, createMagnificationGestureHandler);
    }

    private void switchEventStreamTransformation(int i, com.android.server.accessibility.EventStreamTransformation eventStreamTransformation, com.android.server.accessibility.EventStreamTransformation eventStreamTransformation2) {
        com.android.server.accessibility.EventStreamTransformation eventStreamTransformation3 = this.mEventHandler.get(i);
        if (eventStreamTransformation3 == null) {
            return;
        }
        if (eventStreamTransformation3 == eventStreamTransformation) {
            eventStreamTransformation2.setNext(eventStreamTransformation.getNext());
            this.mEventHandler.put(i, eventStreamTransformation2);
            return;
        }
        while (eventStreamTransformation3 != null) {
            if (eventStreamTransformation3.getNext() == eventStreamTransformation) {
                eventStreamTransformation3.setNext(eventStreamTransformation2);
                eventStreamTransformation2.setNext(eventStreamTransformation.getNext());
                return;
            }
            eventStreamTransformation3 = eventStreamTransformation3.getNext();
        }
    }

    private static class EventStreamState {
        private int mSource = -1;

        EventStreamState() {
        }

        public boolean updateInputSource(int i) {
            if (this.mSource == i) {
                return false;
            }
            reset();
            this.mSource = i;
            return true;
        }

        public boolean inputSourceValid() {
            return this.mSource >= 0;
        }

        public void reset() {
            this.mSource = -1;
        }

        public boolean shouldProcessScroll() {
            return false;
        }

        public boolean shouldProcessMotionEvent(android.view.MotionEvent motionEvent) {
            return false;
        }

        public boolean shouldProcessKeyEvent(android.view.KeyEvent keyEvent) {
            return false;
        }
    }

    private static class MouseEventStreamState extends com.android.server.accessibility.AccessibilityInputFilter.EventStreamState {
        private boolean mMotionSequenceStarted;

        public MouseEventStreamState() {
            reset();
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final void reset() {
            super.reset();
            this.mMotionSequenceStarted = false;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean shouldProcessScroll() {
            return true;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean shouldProcessMotionEvent(android.view.MotionEvent motionEvent) {
            boolean z = true;
            if (this.mMotionSequenceStarted) {
                return true;
            }
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0 && actionMasked != 7) {
                z = false;
            }
            this.mMotionSequenceStarted = z;
            return this.mMotionSequenceStarted;
        }
    }

    private static class TouchScreenEventStreamState extends com.android.server.accessibility.AccessibilityInputFilter.EventStreamState {
        private boolean mHoverSequenceStarted;
        private boolean mTouchSequenceStarted;

        public TouchScreenEventStreamState() {
            reset();
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final void reset() {
            super.reset();
            this.mTouchSequenceStarted = false;
            this.mHoverSequenceStarted = false;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean shouldProcessMotionEvent(android.view.MotionEvent motionEvent) {
            if (motionEvent.isTouchEvent()) {
                if (this.mTouchSequenceStarted) {
                    return true;
                }
                this.mTouchSequenceStarted = motionEvent.getActionMasked() == 0;
                return this.mTouchSequenceStarted;
            }
            if (this.mHoverSequenceStarted) {
                return true;
            }
            this.mHoverSequenceStarted = motionEvent.getActionMasked() == 9;
            return this.mHoverSequenceStarted;
        }
    }

    private class GenericMotionEventStreamState extends com.android.server.accessibility.AccessibilityInputFilter.EventStreamState {
        private GenericMotionEventStreamState() {
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public boolean shouldProcessMotionEvent(android.view.MotionEvent motionEvent) {
            return com.android.server.accessibility.AccessibilityInputFilter.this.anyServiceWantsGenericMotionEvent(motionEvent);
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public boolean shouldProcessScroll() {
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean anyServiceWantsToObserveMotionEvent(android.view.MotionEvent motionEvent) {
        return (!motionEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3) || (this.mEnabledFeatures & 2) == 0) && ((motionEvent.getSource() & (-256)) & (this.mCombinedGenericMotionEventSources & this.mCombinedMotionEventObservedSources)) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean anyServiceWantsGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return (!motionEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3) || (this.mEnabledFeatures & 2) == 0) && ((motionEvent.getSource() & (-256)) & this.mCombinedGenericMotionEventSources) != 0;
    }

    public void setCombinedGenericMotionEventSources(int i) {
        this.mCombinedGenericMotionEventSources = i;
    }

    public void setCombinedMotionEventObservedSources(int i) {
        this.mCombinedMotionEventObservedSources = i;
    }

    private static class KeyboardEventStreamState extends com.android.server.accessibility.AccessibilityInputFilter.EventStreamState {
        private android.util.SparseBooleanArray mEventSequenceStartedMap = new android.util.SparseBooleanArray();

        public KeyboardEventStreamState() {
            reset();
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final void reset() {
            super.reset();
            this.mEventSequenceStartedMap.clear();
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public boolean updateInputSource(int i) {
            return false;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public boolean inputSourceValid() {
            return true;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean shouldProcessKeyEvent(android.view.KeyEvent keyEvent) {
            int deviceId = keyEvent.getDeviceId();
            if (this.mEventSequenceStartedMap.get(deviceId, false)) {
                return true;
            }
            boolean z = keyEvent.getAction() == 0;
            this.mEventSequenceStartedMap.put(deviceId, z);
            return z;
        }
    }

    public void setGestureDetectionPassthroughRegion(int i, android.graphics.Region region) {
        if (region != null && this.mTouchExplorer.contains(i)) {
            this.mTouchExplorer.get(i).setGestureDetectionPassthroughRegion(region);
        }
    }

    public void setTouchExplorationPassthroughRegion(int i, android.graphics.Region region) {
        if (region != null && this.mTouchExplorer.contains(i)) {
            this.mTouchExplorer.get(i).setTouchExplorationPassthroughRegion(region);
        }
    }

    public void setServiceDetectsGesturesEnabled(int i, boolean z) {
        if (this.mTouchExplorer.contains(i)) {
            this.mTouchExplorer.get(i).setServiceDetectsGestures(z);
        }
        this.mServiceDetectsGestures.put(i, java.lang.Boolean.valueOf(z));
    }

    public void resetServiceDetectsGestures() {
        this.mServiceDetectsGestures.clear();
    }

    public void requestTouchExploration(int i) {
        if (this.mTouchExplorer.contains(i)) {
            this.mTouchExplorer.get(i).requestTouchExploration();
        }
    }

    public void requestDragging(int i, int i2) {
        if (this.mTouchExplorer.contains(i)) {
            this.mTouchExplorer.get(i).requestDragging(i2);
        }
    }

    public void requestDelegating(int i) {
        if (this.mTouchExplorer.contains(i)) {
            this.mTouchExplorer.get(i).requestDelegating();
        }
    }

    public void onDoubleTap(int i) {
        if (this.mTouchExplorer.contains(i)) {
            this.mTouchExplorer.get(i).onDoubleTap();
        }
    }

    public void onDoubleTapAndHold(int i) {
        if (this.mTouchExplorer.contains(i)) {
            this.mTouchExplorer.get(i).onDoubleTapAndHold();
        }
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (this.mEventHandler == null) {
            return;
        }
        printWriter.append("A11yInputFilter Info : ");
        printWriter.println();
        java.util.ArrayList<android.view.Display> validDisplayList = this.mAms.getValidDisplayList();
        for (int i = 0; i < validDisplayList.size(); i++) {
            int displayId = validDisplayList.get(i).getDisplayId();
            com.android.server.accessibility.EventStreamTransformation eventStreamTransformation = this.mEventHandler.get(displayId);
            if (eventStreamTransformation != null) {
                printWriter.append("Enabled features of Display [");
                printWriter.append((java.lang.CharSequence) java.lang.Integer.toString(displayId));
                printWriter.append("] = ");
                java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",", "[", "]");
                while (eventStreamTransformation != null) {
                    if (eventStreamTransformation instanceof com.android.server.accessibility.magnification.MagnificationGestureHandler) {
                        stringJoiner.add("MagnificationGesture");
                    } else if (eventStreamTransformation instanceof com.android.server.accessibility.KeyboardInterceptor) {
                        stringJoiner.add("KeyboardInterceptor");
                    } else if (eventStreamTransformation instanceof com.android.server.accessibility.gestures.TouchExplorer) {
                        stringJoiner.add("TouchExplorer");
                    } else if (eventStreamTransformation instanceof com.android.server.accessibility.AutoclickController) {
                        stringJoiner.add("AutoclickController");
                    } else if (eventStreamTransformation instanceof com.android.server.accessibility.MotionEventInjector) {
                        stringJoiner.add("MotionEventInjector");
                    }
                    eventStreamTransformation = eventStreamTransformation.getNext();
                }
                printWriter.append((java.lang.CharSequence) stringJoiner.toString());
            }
            printWriter.println();
        }
    }
}
