package com.android.server.companion.virtual;

/* loaded from: classes.dex */
class InputController {
    static final java.lang.String NAVIGATION_TOUCHPAD_DEVICE_TYPE = "touchNavigation";
    static final java.lang.String PHYS_TYPE_DPAD = "Dpad";
    static final java.lang.String PHYS_TYPE_KEYBOARD = "Keyboard";
    static final java.lang.String PHYS_TYPE_MOUSE = "Mouse";
    static final java.lang.String PHYS_TYPE_NAVIGATION_TOUCHPAD = "NavigationTouchpad";
    static final java.lang.String PHYS_TYPE_STYLUS = "Stylus";
    static final java.lang.String PHYS_TYPE_TOUCHSCREEN = "Touchscreen";
    private static final java.lang.String TAG = "VirtualInputController";
    private static final java.util.concurrent.atomic.AtomicLong sNextPhysId = new java.util.concurrent.atomic.AtomicLong(1);
    private final android.content.AttributionSource mAttributionSource;
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.companion.virtual.InputController.InputDeviceDescriptor> mInputDeviceDescriptors;
    private final com.android.server.input.InputManagerInternal mInputManagerInternal;
    final java.lang.Object mLock;
    private final com.android.server.companion.virtual.InputController.NativeWrapper mNativeWrapper;
    private final com.android.server.companion.virtual.InputController.DeviceCreationThreadVerifier mThreadVerifier;
    private final android.view.WindowManager mWindowManager;

    @com.android.internal.annotations.VisibleForTesting
    interface DeviceCreationThreadVerifier {
        boolean isValidThread();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PhysType {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeCloseUinput(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenUinputDpad(java.lang.String str, int i, int i2, java.lang.String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenUinputKeyboard(java.lang.String str, int i, int i2, java.lang.String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenUinputMouse(java.lang.String str, int i, int i2, java.lang.String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenUinputStylus(java.lang.String str, int i, int i2, java.lang.String str2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenUinputTouchscreen(java.lang.String str, int i, int i2, java.lang.String str2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeWriteButtonEvent(long j, int i, int i2, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeWriteDpadKeyEvent(long j, int i, int i2, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeWriteKeyEvent(long j, int i, int i2, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeWriteRelativeEvent(long j, float f, float f2, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeWriteScrollEvent(long j, float f, float f2, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeWriteStylusButtonEvent(long j, int i, int i2, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeWriteStylusMotionEvent(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeWriteTouchEvent(long j, int i, int i2, int i3, float f, float f2, float f3, float f4, long j2);

    InputController(@android.annotation.NonNull final android.os.Handler handler, @android.annotation.NonNull android.view.WindowManager windowManager, android.content.AttributionSource attributionSource) {
        this(new com.android.server.companion.virtual.InputController.NativeWrapper(), handler, windowManager, attributionSource, new com.android.server.companion.virtual.InputController.DeviceCreationThreadVerifier() { // from class: com.android.server.companion.virtual.InputController$$ExternalSyntheticLambda1
            @Override // com.android.server.companion.virtual.InputController.DeviceCreationThreadVerifier
            public final boolean isValidThread() {
                boolean lambda$new$0;
                lambda$new$0 = com.android.server.companion.virtual.InputController.lambda$new$0(handler);
                return lambda$new$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$new$0(android.os.Handler handler) {
        return !handler.getLooper().isCurrentThread();
    }

    @com.android.internal.annotations.VisibleForTesting
    InputController(@android.annotation.NonNull com.android.server.companion.virtual.InputController.NativeWrapper nativeWrapper, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.view.WindowManager windowManager, android.content.AttributionSource attributionSource, @android.annotation.NonNull com.android.server.companion.virtual.InputController.DeviceCreationThreadVerifier deviceCreationThreadVerifier) {
        this.mLock = new java.lang.Object();
        this.mInputDeviceDescriptors = new android.util.ArrayMap<>();
        this.mHandler = handler;
        this.mNativeWrapper = nativeWrapper;
        this.mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        this.mInputManagerInternal = (com.android.server.input.InputManagerInternal) com.android.server.LocalServices.getService(com.android.server.input.InputManagerInternal.class);
        this.mWindowManager = windowManager;
        this.mAttributionSource = attributionSource;
        this.mThreadVerifier = deviceCreationThreadVerifier;
    }

    void close() {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<java.util.Map.Entry<android.os.IBinder, com.android.server.companion.virtual.InputController.InputDeviceDescriptor>> it = this.mInputDeviceDescriptors.entrySet().iterator();
                if (it.hasNext()) {
                    java.util.Map.Entry<android.os.IBinder, com.android.server.companion.virtual.InputController.InputDeviceDescriptor> next = it.next();
                    android.os.IBinder key = next.getKey();
                    com.android.server.companion.virtual.InputController.InputDeviceDescriptor value = next.getValue();
                    it.remove();
                    closeInputDeviceDescriptorLocked(key, value);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void createDpad(@android.annotation.NonNull final java.lang.String str, final int i, final int i2, @android.annotation.NonNull android.os.IBinder iBinder, int i3) throws com.android.server.companion.virtual.InputController.DeviceCreationException {
        final java.lang.String createPhys = createPhys(PHYS_TYPE_DPAD);
        createDeviceInternal(4, str, i, i2, iBinder, i3, createPhys, new java.util.function.Supplier() { // from class: com.android.server.companion.virtual.InputController$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Long lambda$createDpad$1;
                lambda$createDpad$1 = com.android.server.companion.virtual.InputController.this.lambda$createDpad$1(str, i, i2, createPhys);
                return lambda$createDpad$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Long lambda$createDpad$1(java.lang.String str, int i, int i2, java.lang.String str2) {
        return java.lang.Long.valueOf(this.mNativeWrapper.openUinputDpad(str, i, i2, str2));
    }

    void createKeyboard(@android.annotation.NonNull final java.lang.String str, final int i, final int i2, @android.annotation.NonNull android.os.IBinder iBinder, int i3, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3) throws com.android.server.companion.virtual.InputController.DeviceCreationException {
        final java.lang.String createPhys = createPhys(PHYS_TYPE_KEYBOARD);
        this.mInputManagerInternal.addKeyboardLayoutAssociation(createPhys, str2, str3);
        try {
            createDeviceInternal(1, str, i, i2, iBinder, i3, createPhys, new java.util.function.Supplier() { // from class: com.android.server.companion.virtual.InputController$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Long lambda$createKeyboard$2;
                    lambda$createKeyboard$2 = com.android.server.companion.virtual.InputController.this.lambda$createKeyboard$2(str, i, i2, createPhys);
                    return lambda$createKeyboard$2;
                }
            });
        } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e) {
            this.mInputManagerInternal.removeKeyboardLayoutAssociation(createPhys);
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Long lambda$createKeyboard$2(java.lang.String str, int i, int i2, java.lang.String str2) {
        return java.lang.Long.valueOf(this.mNativeWrapper.openUinputKeyboard(str, i, i2, str2));
    }

    void createMouse(@android.annotation.NonNull final java.lang.String str, final int i, final int i2, @android.annotation.NonNull android.os.IBinder iBinder, int i3) throws com.android.server.companion.virtual.InputController.DeviceCreationException {
        final java.lang.String createPhys = createPhys(PHYS_TYPE_MOUSE);
        createDeviceInternal(2, str, i, i2, iBinder, i3, createPhys, new java.util.function.Supplier() { // from class: com.android.server.companion.virtual.InputController$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Long lambda$createMouse$3;
                lambda$createMouse$3 = com.android.server.companion.virtual.InputController.this.lambda$createMouse$3(str, i, i2, createPhys);
                return lambda$createMouse$3;
            }
        });
        setVirtualMousePointerDisplayId(i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Long lambda$createMouse$3(java.lang.String str, int i, int i2, java.lang.String str2) {
        return java.lang.Long.valueOf(this.mNativeWrapper.openUinputMouse(str, i, i2, str2));
    }

    void createTouchscreen(@android.annotation.NonNull final java.lang.String str, final int i, final int i2, @android.annotation.NonNull android.os.IBinder iBinder, int i3, final int i4, final int i5) throws com.android.server.companion.virtual.InputController.DeviceCreationException {
        final java.lang.String createPhys = createPhys(PHYS_TYPE_TOUCHSCREEN);
        createDeviceInternal(3, str, i, i2, iBinder, i3, createPhys, new java.util.function.Supplier() { // from class: com.android.server.companion.virtual.InputController$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Long lambda$createTouchscreen$4;
                lambda$createTouchscreen$4 = com.android.server.companion.virtual.InputController.this.lambda$createTouchscreen$4(str, i, i2, createPhys, i4, i5);
                return lambda$createTouchscreen$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Long lambda$createTouchscreen$4(java.lang.String str, int i, int i2, java.lang.String str2, int i3, int i4) {
        return java.lang.Long.valueOf(this.mNativeWrapper.openUinputTouchscreen(str, i, i2, str2, i3, i4));
    }

    void createNavigationTouchpad(@android.annotation.NonNull final java.lang.String str, final int i, final int i2, @android.annotation.NonNull android.os.IBinder iBinder, int i3, final int i4, final int i5) throws com.android.server.companion.virtual.InputController.DeviceCreationException {
        final java.lang.String createPhys = createPhys(PHYS_TYPE_NAVIGATION_TOUCHPAD);
        this.mInputManagerInternal.setTypeAssociation(createPhys, NAVIGATION_TOUCHPAD_DEVICE_TYPE);
        try {
            createDeviceInternal(5, str, i, i2, iBinder, i3, createPhys, new java.util.function.Supplier() { // from class: com.android.server.companion.virtual.InputController$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Long lambda$createNavigationTouchpad$5;
                    lambda$createNavigationTouchpad$5 = com.android.server.companion.virtual.InputController.this.lambda$createNavigationTouchpad$5(str, i, i2, createPhys, i4, i5);
                    return lambda$createNavigationTouchpad$5;
                }
            });
        } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e) {
            this.mInputManagerInternal.unsetTypeAssociation(createPhys);
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Long lambda$createNavigationTouchpad$5(java.lang.String str, int i, int i2, java.lang.String str2, int i3, int i4) {
        return java.lang.Long.valueOf(this.mNativeWrapper.openUinputTouchscreen(str, i, i2, str2, i3, i4));
    }

    void createStylus(@android.annotation.NonNull final java.lang.String str, final int i, final int i2, @android.annotation.NonNull android.os.IBinder iBinder, int i3, final int i4, final int i5) throws com.android.server.companion.virtual.InputController.DeviceCreationException {
        final java.lang.String createPhys = createPhys(PHYS_TYPE_STYLUS);
        createDeviceInternal(6, str, i, i2, iBinder, i3, createPhys, new java.util.function.Supplier() { // from class: com.android.server.companion.virtual.InputController$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Long lambda$createStylus$6;
                lambda$createStylus$6 = com.android.server.companion.virtual.InputController.this.lambda$createStylus$6(str, i, i2, createPhys, i4, i5);
                return lambda$createStylus$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Long lambda$createStylus$6(java.lang.String str, int i, int i2, java.lang.String str2, int i3, int i4) {
        return java.lang.Long.valueOf(this.mNativeWrapper.openUinputStylus(str, i, i2, str2, i3, i4));
    }

    void unregisterInputDevice(@android.annotation.NonNull android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor remove = this.mInputDeviceDescriptors.remove(iBinder);
                if (remove == null) {
                    android.util.Slog.w(TAG, "Could not unregister input device for given token.");
                } else {
                    closeInputDeviceDescriptorLocked(iBinder, remove);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void closeInputDeviceDescriptorLocked(android.os.IBinder iBinder, com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor) {
        iBinder.unlinkToDeath(inputDeviceDescriptor.getDeathRecipient(), 0);
        this.mNativeWrapper.closeUinput(inputDeviceDescriptor.getNativePointer());
        java.lang.String phys = inputDeviceDescriptor.getPhys();
        android.hardware.input.InputManagerGlobal.getInstance().removeUniqueIdAssociation(phys);
        if (inputDeviceDescriptor.getType() == 5) {
            this.mInputManagerInternal.unsetTypeAssociation(phys);
        }
        if (inputDeviceDescriptor.getType() == 1) {
            this.mInputManagerInternal.removeKeyboardLayoutAssociation(phys);
        }
        if (inputDeviceDescriptor.isMouse() && getVirtualMousePointerDisplayId() == inputDeviceDescriptor.getDisplayId()) {
            updateActivePointerDisplayIdLocked();
        }
    }

    int getInputDeviceId(android.os.IBinder iBinder) {
        int inputDeviceId;
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    throw new java.lang.IllegalArgumentException("Could not get device id for given token");
                }
                inputDeviceId = inputDeviceDescriptor.getInputDeviceId();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return inputDeviceId;
    }

    void setShowPointerIcon(boolean z, int i) {
        this.mInputManagerInternal.setPointerIconVisible(z, i);
    }

    void setMousePointerAccelerationEnabled(boolean z, int i) {
        this.mInputManagerInternal.setMousePointerAccelerationEnabled(z, i);
    }

    void setDisplayEligibilityForPointerCapture(boolean z, int i) {
        this.mInputManagerInternal.setDisplayEligibilityForPointerCapture(i, z);
    }

    void setDisplayImePolicy(int i, int i2) {
        this.mWindowManager.setDisplayImePolicy(i, i2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateActivePointerDisplayIdLocked() {
        com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = null;
        for (int i = 0; i < this.mInputDeviceDescriptors.size(); i++) {
            com.android.server.companion.virtual.InputController.InputDeviceDescriptor valueAt = this.mInputDeviceDescriptors.valueAt(i);
            if (valueAt.isMouse() && (inputDeviceDescriptor == null || valueAt.getCreationOrderNumber() > inputDeviceDescriptor.getCreationOrderNumber())) {
                inputDeviceDescriptor = valueAt;
            }
        }
        if (inputDeviceDescriptor != null) {
            setVirtualMousePointerDisplayId(inputDeviceDescriptor.getDisplayId());
        } else {
            setVirtualMousePointerDisplayId(-1);
        }
    }

    private void validateDeviceName(java.lang.String str) throws com.android.server.companion.virtual.InputController.DeviceCreationException {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mInputDeviceDescriptors.size(); i++) {
                try {
                    if (this.mInputDeviceDescriptors.valueAt(i).mName.equals(str)) {
                        throw new com.android.server.companion.virtual.InputController.DeviceCreationException("Input device name already in use: " + str);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static java.lang.String createPhys(java.lang.String str) {
        return android.text.TextUtils.formatSimple("virtual%s:%d", new java.lang.Object[]{str, java.lang.Long.valueOf(sNextPhysId.getAndIncrement())});
    }

    private void setUniqueIdAssociation(int i, java.lang.String str) {
        android.hardware.input.InputManagerGlobal.getInstance().addUniqueIdAssociation(str, this.mDisplayManagerInternal.getDisplayInfo(i).uniqueId);
    }

    boolean sendDpadKeyEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualKeyEvent virtualKeyEvent) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                return this.mNativeWrapper.writeDpadKeyEvent(inputDeviceDescriptor.getNativePointer(), virtualKeyEvent.getKeyCode(), virtualKeyEvent.getAction(), virtualKeyEvent.getEventTimeNanos());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean sendKeyEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualKeyEvent virtualKeyEvent) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                return this.mNativeWrapper.writeKeyEvent(inputDeviceDescriptor.getNativePointer(), virtualKeyEvent.getKeyCode(), virtualKeyEvent.getAction(), virtualKeyEvent.getEventTimeNanos());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean sendButtonEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualMouseButtonEvent virtualMouseButtonEvent) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                if (inputDeviceDescriptor.getDisplayId() != getVirtualMousePointerDisplayId()) {
                    setVirtualMousePointerDisplayId(inputDeviceDescriptor.getDisplayId());
                }
                return this.mNativeWrapper.writeButtonEvent(inputDeviceDescriptor.getNativePointer(), virtualMouseButtonEvent.getButtonCode(), virtualMouseButtonEvent.getAction(), virtualMouseButtonEvent.getEventTimeNanos());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean sendTouchEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualTouchEvent virtualTouchEvent) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                return this.mNativeWrapper.writeTouchEvent(inputDeviceDescriptor.getNativePointer(), virtualTouchEvent.getPointerId(), virtualTouchEvent.getToolType(), virtualTouchEvent.getAction(), virtualTouchEvent.getX(), virtualTouchEvent.getY(), virtualTouchEvent.getPressure(), virtualTouchEvent.getMajorAxisSize(), virtualTouchEvent.getEventTimeNanos());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean sendRelativeEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualMouseRelativeEvent virtualMouseRelativeEvent) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                if (inputDeviceDescriptor.getDisplayId() != getVirtualMousePointerDisplayId()) {
                    setVirtualMousePointerDisplayId(inputDeviceDescriptor.getDisplayId());
                }
                return this.mNativeWrapper.writeRelativeEvent(inputDeviceDescriptor.getNativePointer(), virtualMouseRelativeEvent.getRelativeX(), virtualMouseRelativeEvent.getRelativeY(), virtualMouseRelativeEvent.getEventTimeNanos());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean sendScrollEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualMouseScrollEvent virtualMouseScrollEvent) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                if (inputDeviceDescriptor.getDisplayId() != getVirtualMousePointerDisplayId()) {
                    setVirtualMousePointerDisplayId(inputDeviceDescriptor.getDisplayId());
                }
                return this.mNativeWrapper.writeScrollEvent(inputDeviceDescriptor.getNativePointer(), virtualMouseScrollEvent.getXAxisMovement(), virtualMouseScrollEvent.getYAxisMovement(), virtualMouseScrollEvent.getEventTimeNanos());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.graphics.PointF getCursorPosition(@android.annotation.NonNull android.os.IBinder iBinder) {
        android.graphics.PointF cursorPosition;
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    throw new java.lang.IllegalArgumentException("Could not get cursor position for input device for given token");
                }
                if (inputDeviceDescriptor.getDisplayId() != getVirtualMousePointerDisplayId()) {
                    setVirtualMousePointerDisplayId(inputDeviceDescriptor.getDisplayId());
                }
                cursorPosition = ((com.android.server.input.InputManagerInternal) com.android.server.LocalServices.getService(com.android.server.input.InputManagerInternal.class)).getCursorPosition(inputDeviceDescriptor.getDisplayId());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return cursorPosition;
    }

    boolean sendStylusMotionEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualStylusMotionEvent virtualStylusMotionEvent) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                return this.mNativeWrapper.writeStylusMotionEvent(inputDeviceDescriptor.getNativePointer(), virtualStylusMotionEvent.getToolType(), virtualStylusMotionEvent.getAction(), virtualStylusMotionEvent.getX(), virtualStylusMotionEvent.getY(), virtualStylusMotionEvent.getPressure(), virtualStylusMotionEvent.getTiltX(), virtualStylusMotionEvent.getTiltY(), virtualStylusMotionEvent.getEventTimeNanos());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean sendStylusButtonEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualStylusButtonEvent virtualStylusButtonEvent) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor = this.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    return false;
                }
                return this.mNativeWrapper.writeStylusButtonEvent(inputDeviceDescriptor.getNativePointer(), virtualStylusButtonEvent.getButtonCode(), virtualStylusButtonEvent.getAction(), virtualStylusButtonEvent.getEventTimeNanos());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.println("    InputController: ");
        synchronized (this.mLock) {
            try {
                printWriter.println("      Active descriptors: ");
                for (int i = 0; i < this.mInputDeviceDescriptors.size(); i++) {
                    com.android.server.companion.virtual.InputController.InputDeviceDescriptor valueAt = this.mInputDeviceDescriptors.valueAt(i);
                    printWriter.println("        ptr: " + valueAt.getNativePointer());
                    printWriter.println("          displayId: " + valueAt.getDisplayId());
                    printWriter.println("          creationOrder: " + valueAt.getCreationOrderNumber());
                    printWriter.println("          type: " + valueAt.getType());
                    printWriter.println("          phys: " + valueAt.getPhys());
                    printWriter.println("          inputDeviceId: " + valueAt.getInputDeviceId());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addDeviceForTesting(android.os.IBinder iBinder, long j, int i, int i2, java.lang.String str, java.lang.String str2, int i3) {
        synchronized (this.mLock) {
            this.mInputDeviceDescriptors.put(iBinder, new com.android.server.companion.virtual.InputController.InputDeviceDescriptor(j, new android.os.IBinder.DeathRecipient() { // from class: com.android.server.companion.virtual.InputController$$ExternalSyntheticLambda4
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.companion.virtual.InputController.lambda$addDeviceForTesting$7();
                }
            }, i, i2, str, str2, i3));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addDeviceForTesting$7() {
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    java.util.Map<android.os.IBinder, com.android.server.companion.virtual.InputController.InputDeviceDescriptor> getInputDeviceDescriptors() {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        synchronized (this.mLock) {
            arrayMap.putAll((java.util.Map) this.mInputDeviceDescriptors);
        }
        return arrayMap;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static class NativeWrapper {
        protected NativeWrapper() {
        }

        public long openUinputDpad(java.lang.String str, int i, int i2, java.lang.String str2) {
            return com.android.server.companion.virtual.InputController.nativeOpenUinputDpad(str, i, i2, str2);
        }

        public long openUinputKeyboard(java.lang.String str, int i, int i2, java.lang.String str2) {
            return com.android.server.companion.virtual.InputController.nativeOpenUinputKeyboard(str, i, i2, str2);
        }

        public long openUinputMouse(java.lang.String str, int i, int i2, java.lang.String str2) {
            return com.android.server.companion.virtual.InputController.nativeOpenUinputMouse(str, i, i2, str2);
        }

        public long openUinputTouchscreen(java.lang.String str, int i, int i2, java.lang.String str2, int i3, int i4) {
            return com.android.server.companion.virtual.InputController.nativeOpenUinputTouchscreen(str, i, i2, str2, i3, i4);
        }

        public long openUinputStylus(java.lang.String str, int i, int i2, java.lang.String str2, int i3, int i4) {
            return com.android.server.companion.virtual.InputController.nativeOpenUinputStylus(str, i, i2, str2, i3, i4);
        }

        public void closeUinput(long j) {
            com.android.server.companion.virtual.InputController.nativeCloseUinput(j);
        }

        public boolean writeDpadKeyEvent(long j, int i, int i2, long j2) {
            return com.android.server.companion.virtual.InputController.nativeWriteDpadKeyEvent(j, i, i2, j2);
        }

        public boolean writeKeyEvent(long j, int i, int i2, long j2) {
            return com.android.server.companion.virtual.InputController.nativeWriteKeyEvent(j, i, i2, j2);
        }

        public boolean writeButtonEvent(long j, int i, int i2, long j2) {
            return com.android.server.companion.virtual.InputController.nativeWriteButtonEvent(j, i, i2, j2);
        }

        public boolean writeTouchEvent(long j, int i, int i2, int i3, float f, float f2, float f3, float f4, long j2) {
            return com.android.server.companion.virtual.InputController.nativeWriteTouchEvent(j, i, i2, i3, f, f2, f3, f4, j2);
        }

        public boolean writeRelativeEvent(long j, float f, float f2, long j2) {
            return com.android.server.companion.virtual.InputController.nativeWriteRelativeEvent(j, f, f2, j2);
        }

        public boolean writeScrollEvent(long j, float f, float f2, long j2) {
            return com.android.server.companion.virtual.InputController.nativeWriteScrollEvent(j, f, f2, j2);
        }

        public boolean writeStylusMotionEvent(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j2) {
            return com.android.server.companion.virtual.InputController.nativeWriteStylusMotionEvent(j, i, i2, i3, i4, i5, i6, i7, j2);
        }

        public boolean writeStylusButtonEvent(long j, int i, int i2, long j2) {
            return com.android.server.companion.virtual.InputController.nativeWriteStylusButtonEvent(j, i, i2, j2);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    static final class InputDeviceDescriptor {
        static final int TYPE_DPAD = 4;
        static final int TYPE_KEYBOARD = 1;
        static final int TYPE_MOUSE = 2;
        static final int TYPE_NAVIGATION_TOUCHPAD = 5;
        static final int TYPE_STYLUS = 6;
        static final int TYPE_TOUCHSCREEN = 3;
        private static final java.util.concurrent.atomic.AtomicLong sNextCreationOrderNumber = new java.util.concurrent.atomic.AtomicLong(1);
        private final long mCreationOrderNumber = sNextCreationOrderNumber.getAndIncrement();
        private final android.os.IBinder.DeathRecipient mDeathRecipient;
        private final int mDisplayId;
        private final int mInputDeviceId;
        private final java.lang.String mName;
        private final java.lang.String mPhys;
        private final long mPtr;
        private final int mType;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface Type {
        }

        InputDeviceDescriptor(long j, android.os.IBinder.DeathRecipient deathRecipient, int i, int i2, java.lang.String str, java.lang.String str2, int i3) {
            this.mPtr = j;
            this.mDeathRecipient = deathRecipient;
            this.mType = i;
            this.mDisplayId = i2;
            this.mPhys = str;
            this.mName = str2;
            this.mInputDeviceId = i3;
        }

        public long getNativePointer() {
            return this.mPtr;
        }

        public int getType() {
            return this.mType;
        }

        public boolean isMouse() {
            return this.mType == 2;
        }

        public android.os.IBinder.DeathRecipient getDeathRecipient() {
            return this.mDeathRecipient;
        }

        public int getDisplayId() {
            return this.mDisplayId;
        }

        public long getCreationOrderNumber() {
            return this.mCreationOrderNumber;
        }

        public java.lang.String getPhys() {
            return this.mPhys;
        }

        public int getInputDeviceId() {
            return this.mInputDeviceId;
        }
    }

    private final class BinderDeathRecipient implements android.os.IBinder.DeathRecipient {
        private final android.os.IBinder mDeviceToken;

        BinderDeathRecipient(android.os.IBinder iBinder) {
            this.mDeviceToken = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Slog.e(com.android.server.companion.virtual.InputController.TAG, "Virtual input controller binder died");
            com.android.server.companion.virtual.InputController.this.unregisterInputDevice(this.mDeviceToken);
        }
    }

    private class WaitForDevice implements java.lang.AutoCloseable {
        private final java.util.concurrent.CountDownLatch mDeviceAddedLatch = new java.util.concurrent.CountDownLatch(1);
        private int mInputDeviceId = -2;
        private final android.hardware.input.InputManager.InputDeviceListener mListener;

        WaitForDevice(final java.lang.String str, final int i, final int i2, final int i3) {
            this.mListener = new android.hardware.input.InputManager.InputDeviceListener() { // from class: com.android.server.companion.virtual.InputController.WaitForDevice.1
                @Override // android.hardware.input.InputManager.InputDeviceListener
                public void onInputDeviceAdded(int i4) {
                    onInputDeviceChanged(i4);
                }

                @Override // android.hardware.input.InputManager.InputDeviceListener
                public void onInputDeviceRemoved(int i4) {
                }

                @Override // android.hardware.input.InputManager.InputDeviceListener
                public void onInputDeviceChanged(int i4) {
                    if (isMatchingDevice(i4)) {
                        com.android.server.companion.virtual.InputController.WaitForDevice.this.mInputDeviceId = i4;
                        com.android.server.companion.virtual.InputController.WaitForDevice.this.mDeviceAddedLatch.countDown();
                    }
                }

                private boolean isMatchingDevice(int i4) {
                    android.view.InputDevice inputDevice = android.hardware.input.InputManagerGlobal.getInstance().getInputDevice(i4);
                    java.util.Objects.requireNonNull(inputDevice, "Newly added input device was null.");
                    if (!inputDevice.getName().equals(str)) {
                        return false;
                    }
                    android.hardware.input.InputDeviceIdentifier identifier = inputDevice.getIdentifier();
                    return identifier.getVendorId() == i && identifier.getProductId() == i2 && inputDevice.getAssociatedDisplayId() == i3;
                }
            };
            android.hardware.input.InputManagerGlobal.getInstance().registerInputDeviceListener(this.mListener, com.android.server.companion.virtual.InputController.this.mHandler);
        }

        int waitForDeviceCreation() throws com.android.server.companion.virtual.InputController.DeviceCreationException {
            try {
                if (!this.mDeviceAddedLatch.await(1L, java.util.concurrent.TimeUnit.MINUTES)) {
                    throw new com.android.server.companion.virtual.InputController.DeviceCreationException("Timed out waiting for virtual device to be created.");
                }
                if (this.mInputDeviceId == -2) {
                    throw new java.lang.IllegalStateException("Virtual input device was created with an invalid id=" + this.mInputDeviceId);
                }
                return this.mInputDeviceId;
            } catch (java.lang.InterruptedException e) {
                throw new com.android.server.companion.virtual.InputController.DeviceCreationException("Interrupted while waiting for virtual device to be created.", e);
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            android.hardware.input.InputManagerGlobal.getInstance().unregisterInputDeviceListener(this.mListener);
        }
    }

    static class DeviceCreationException extends java.lang.Exception {
        DeviceCreationException() {
        }

        DeviceCreationException(java.lang.String str) {
            super(str);
        }

        DeviceCreationException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }

        DeviceCreationException(java.lang.Throwable th) {
            super(th);
        }
    }

    private void createDeviceInternal(int i, java.lang.String str, int i2, int i3, android.os.IBinder iBinder, int i4, java.lang.String str2, java.util.function.Supplier<java.lang.Long> supplier) throws com.android.server.companion.virtual.InputController.DeviceCreationException {
        java.lang.String metricIdForInputType;
        if (!this.mThreadVerifier.isValidThread()) {
            throw new java.lang.IllegalStateException("Virtual device creation should happen on an auxiliary thread (e.g. binder thread) and not from the handler's thread.");
        }
        validateDeviceName(str);
        setUniqueIdAssociation(i4, str2);
        try {
            com.android.server.companion.virtual.InputController.WaitForDevice waitForDevice = new com.android.server.companion.virtual.InputController.WaitForDevice(str, i2, i3, i4);
            try {
                long longValue = supplier.get().longValue();
                if (longValue == 0) {
                    throw new com.android.server.companion.virtual.InputController.DeviceCreationException("A native error occurred when creating virtual input device: " + str);
                }
                try {
                    int waitForDeviceCreation = waitForDevice.waitForDeviceCreation();
                    com.android.server.companion.virtual.InputController.BinderDeathRecipient binderDeathRecipient = new com.android.server.companion.virtual.InputController.BinderDeathRecipient(iBinder);
                    try {
                        iBinder.linkToDeath(binderDeathRecipient, 0);
                        waitForDevice.close();
                        synchronized (this.mLock) {
                            this.mInputDeviceDescriptors.put(iBinder, new com.android.server.companion.virtual.InputController.InputDeviceDescriptor(longValue, binderDeathRecipient, i, i4, str2, str, waitForDeviceCreation));
                        }
                        if (android.companion.virtualdevice.flags.Flags.metricsCollection() && (metricIdForInputType = getMetricIdForInputType(i)) != null) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid(metricIdForInputType, this.mAttributionSource.getUid());
                        }
                    } catch (android.os.RemoteException e) {
                        throw new com.android.server.companion.virtual.InputController.DeviceCreationException("Client died before virtual device could be created.", e);
                    }
                } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e2) {
                    this.mNativeWrapper.closeUinput(longValue);
                    throw e2;
                }
            } finally {
            }
        } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e3) {
            android.hardware.input.InputManagerGlobal.getInstance().removeUniqueIdAssociation(str2);
            throw e3;
        }
    }

    private static java.lang.String getMetricIdForInputType(int i) {
        switch (i) {
            case 1:
                return "virtual_devices.value_virtual_keyboard_created_count";
            case 2:
                return "virtual_devices.value_virtual_mouse_created_count";
            case 3:
                return "virtual_devices.value_virtual_touchscreen_created_count";
            case 4:
                return "virtual_devices.value_virtual_dpad_created_count";
            case 5:
                return "virtual_devices.value_virtual_navigationtouchpad_created_count";
            case 6:
                return "virtual_devices.value_virtual_stylus_created_count";
            default:
                android.util.Log.e(TAG, "No metric known for input type: " + i);
                return null;
        }
    }

    private void setVirtualMousePointerDisplayId(int i) {
        if (com.android.input.flags.Flags.enablePointerChoreographer()) {
            return;
        }
        this.mInputManagerInternal.setVirtualMousePointerDisplayId(i);
    }

    private int getVirtualMousePointerDisplayId() {
        if (com.android.input.flags.Flags.enablePointerChoreographer()) {
            return -1;
        }
        return this.mInputManagerInternal.getVirtualMousePointerDisplayId();
    }
}
