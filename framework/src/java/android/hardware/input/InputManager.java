package android.hardware.input;

/* loaded from: classes2.dex */
public final class InputManager {
    public static final java.lang.String ACTION_QUERY_KEYBOARD_LAYOUTS = "android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS";
    public static final long BLOCK_UNTRUSTED_TOUCHES = 158002302;
    public static final int INJECT_INPUT_EVENT_MODE_ASYNC = 0;
    public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH = 2;
    public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT = 1;
    public static final java.lang.String META_DATA_KEYBOARD_LAYOUTS = "android.hardware.input.metadata.KEYBOARD_LAYOUTS";
    public static final int SWITCH_STATE_OFF = 0;
    public static final int SWITCH_STATE_ON = 1;
    public static final int SWITCH_STATE_UNKNOWN = -1;
    private final android.content.Context mContext;
    private static final java.lang.String TAG = "InputManager";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private java.lang.Boolean mIsStylusPointerIconEnabled = null;
    private final android.hardware.input.InputManagerGlobal mGlobal = android.hardware.input.InputManagerGlobal.getInstance();
    private final android.hardware.input.IInputManager mIm = this.mGlobal.getInputManagerService();

    public interface InputDeviceBatteryListener {
        void onBatteryStateChanged(int i, long j, android.hardware.BatteryState batteryState);
    }

    public interface InputDeviceListener {
        void onInputDeviceAdded(int i);

        void onInputDeviceChanged(int i);

        void onInputDeviceRemoved(int i);
    }

    public interface KeyboardBacklightListener {
        void onKeyboardBacklightChanged(int i, android.hardware.input.KeyboardBacklightState keyboardBacklightState, boolean z);
    }

    public interface OnTabletModeChangedListener {
        void onTabletModeChanged(long j, boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RemappableModifierKey {
        public static final int REMAPPABLE_MODIFIER_KEY_ALT_LEFT = 57;
        public static final int REMAPPABLE_MODIFIER_KEY_ALT_RIGHT = 58;
        public static final int REMAPPABLE_MODIFIER_KEY_CAPS_LOCK = 115;
        public static final int REMAPPABLE_MODIFIER_KEY_CTRL_LEFT = 113;
        public static final int REMAPPABLE_MODIFIER_KEY_CTRL_RIGHT = 114;
        public static final int REMAPPABLE_MODIFIER_KEY_META_LEFT = 117;
        public static final int REMAPPABLE_MODIFIER_KEY_META_RIGHT = 118;
        public static final int REMAPPABLE_MODIFIER_KEY_SHIFT_LEFT = 59;
        public static final int REMAPPABLE_MODIFIER_KEY_SHIFT_RIGHT = 60;
    }

    public interface StickyModifierStateListener {
        void onStickyModifierStateChanged(android.hardware.input.StickyModifierState stickyModifierState);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SwitchState {
    }

    public InputManager(android.content.Context context) {
        this.mContext = context;
    }

    @java.lang.Deprecated
    public static android.hardware.input.InputManager getInstance() {
        return (android.hardware.input.InputManager) ((android.app.Application) java.util.Objects.requireNonNull(android.app.ActivityThread.currentApplication())).getSystemService(android.hardware.input.InputManager.class);
    }

    public java.lang.String getVelocityTrackerStrategy() {
        return this.mGlobal.getVelocityTrackerStrategy();
    }

    public android.view.InputDevice getInputDevice(int i) {
        return this.mGlobal.getInputDevice(i);
    }

    public android.view.InputDevice.ViewBehavior getInputDeviceViewBehavior(int i) {
        android.view.InputDevice inputDevice = getInputDevice(i);
        if (inputDevice == null) {
            return null;
        }
        return inputDevice.getViewBehavior();
    }

    public android.view.InputDevice getInputDeviceByDescriptor(java.lang.String str) {
        return this.mGlobal.getInputDeviceByDescriptor(str);
    }

    public int[] getInputDeviceIds() {
        return this.mGlobal.getInputDeviceIds();
    }

    public boolean isInputDeviceEnabled(int i) {
        return this.mGlobal.isInputDeviceEnabled(i);
    }

    public void enableInputDevice(int i) {
        this.mGlobal.enableInputDevice(i);
    }

    public void disableInputDevice(int i) {
        this.mGlobal.disableInputDevice(i);
    }

    public void registerInputDeviceListener(android.hardware.input.InputManager.InputDeviceListener inputDeviceListener, android.os.Handler handler) {
        this.mGlobal.registerInputDeviceListener(inputDeviceListener, handler);
    }

    public void unregisterInputDeviceListener(android.hardware.input.InputManager.InputDeviceListener inputDeviceListener) {
        this.mGlobal.unregisterInputDeviceListener(inputDeviceListener);
    }

    public int isInTabletMode() {
        try {
            return this.mIm.isInTabletMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerOnTabletModeChangedListener(android.hardware.input.InputManager.OnTabletModeChangedListener onTabletModeChangedListener, android.os.Handler handler) {
        this.mGlobal.registerOnTabletModeChangedListener(onTabletModeChangedListener, handler);
    }

    public void unregisterOnTabletModeChangedListener(android.hardware.input.InputManager.OnTabletModeChangedListener onTabletModeChangedListener) {
        this.mGlobal.unregisterOnTabletModeChangedListener(onTabletModeChangedListener);
    }

    public int isMicMuted() {
        try {
            return this.mIm.isMicMuted();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayouts() {
        try {
            return this.mIm.getKeyboardLayouts();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.String> getKeyboardLayoutDescriptorsForInputDevice(android.view.InputDevice inputDevice) {
        android.hardware.input.KeyboardLayout[] keyboardLayoutsForInputDevice = getKeyboardLayoutsForInputDevice(inputDevice.getIdentifier());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.input.KeyboardLayout keyboardLayout : keyboardLayoutsForInputDevice) {
            arrayList.add(keyboardLayout.getDescriptor());
        }
        return arrayList;
    }

    public java.lang.String getKeyboardLayoutTypeForLayoutDescriptor(java.lang.String str) {
        for (android.hardware.input.KeyboardLayout keyboardLayout : getKeyboardLayouts()) {
            if (str.equals(keyboardLayout.getDescriptor())) {
                return keyboardLayout.getLayoutType();
            }
        }
        return "";
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        return this.mGlobal.getKeyboardLayoutsForInputDevice(inputDeviceIdentifier);
    }

    public android.hardware.input.KeyboardLayout getKeyboardLayout(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        }
        try {
            return this.mIm.getKeyboardLayout(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        try {
            return this.mIm.getCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        this.mGlobal.setCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
    }

    public java.lang.String[] getEnabledKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        if (inputDeviceIdentifier == null) {
            throw new java.lang.IllegalArgumentException("inputDeviceDescriptor must not be null");
        }
        try {
            return this.mIm.getEnabledKeyboardLayoutsForInputDevice(inputDeviceIdentifier);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        if (inputDeviceIdentifier == null) {
            throw new java.lang.IllegalArgumentException("inputDeviceDescriptor must not be null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        }
        try {
            this.mIm.addKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        if (inputDeviceIdentifier == null) {
            throw new java.lang.IllegalArgumentException("inputDeviceDescriptor must not be null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        }
        try {
            this.mIm.removeKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void remapModifierKey(int i, int i2) {
        try {
            this.mIm.remapModifierKey(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearAllModifierKeyRemappings() {
        try {
            this.mIm.clearAllModifierKeyRemappings();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Map<java.lang.Integer, java.lang.Integer> getModifierKeyRemapping() {
        try {
            return this.mIm.getModifierKeyRemapping();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.input.TouchCalibration getTouchCalibration(java.lang.String str, int i) {
        try {
            return this.mIm.getTouchCalibrationForInputDevice(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTouchCalibration(java.lang.String str, int i, android.hardware.input.TouchCalibration touchCalibration) {
        try {
            this.mIm.setTouchCalibrationForInputDevice(str, i, touchCalibration);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.input.KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        try {
            return this.mIm.getKeyboardLayoutForInputDevice(inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype, java.lang.String str) {
        if (inputDeviceIdentifier == null) {
            throw new java.lang.IllegalArgumentException("identifier must not be null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        }
        try {
            this.mIm.setKeyboardLayoutForInputDevice(inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayoutListForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (inputDeviceIdentifier == null) {
            throw new java.lang.IllegalArgumentException("inputDeviceDescriptor must not be null");
        }
        try {
            return this.mIm.getKeyboardLayoutListForInputDevice(inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMousePointerSpeed() {
        try {
            return this.mIm.getMousePointerSpeed();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void tryPointerSpeed(int i) {
        if (i < -7 || i > 7) {
            throw new java.lang.IllegalArgumentException("speed out of range");
        }
        try {
            this.mIm.tryPointerSpeed(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getMaximumObscuringOpacityForTouch() {
        return android.hardware.input.InputSettings.getMaximumObscuringOpacityForTouch(this.mContext);
    }

    public boolean[] deviceHasKeys(int[] iArr) {
        return deviceHasKeys(-1, iArr);
    }

    public boolean[] deviceHasKeys(int i, int[] iArr) {
        return this.mGlobal.deviceHasKeys(i, iArr);
    }

    public int getKeyCodeForKeyLocation(int i, int i2) {
        return this.mGlobal.getKeyCodeForKeyLocation(i, i2);
    }

    public android.graphics.drawable.Drawable getKeyboardLayoutPreview(android.hardware.input.KeyboardLayout keyboardLayout, int i, int i2) {
        if (!com.android.hardware.input.Flags.keyboardLayoutPreviewFlag()) {
            return null;
        }
        return new android.hardware.input.KeyboardLayoutPreviewDrawable(this.mContext, new android.hardware.input.PhysicalKeyLayout(this.mGlobal.getKeyCharacterMap(keyboardLayout), keyboardLayout), i, i2);
    }

    public boolean injectInputEvent(android.view.InputEvent inputEvent, int i, int i2) {
        return this.mGlobal.injectInputEvent(inputEvent, i, i2);
    }

    public boolean injectInputEvent(android.view.InputEvent inputEvent, int i) {
        return this.mGlobal.injectInputEvent(inputEvent, i);
    }

    public android.view.VerifiedInputEvent verifyInputEvent(android.view.InputEvent inputEvent) {
        try {
            return this.mIm.verifyInputEvent(inputEvent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPointerIconType(int i) {
        this.mGlobal.setPointerIconType(i);
    }

    public void setCustomPointerIcon(android.view.PointerIcon pointerIcon) {
        this.mGlobal.setCustomPointerIcon(pointerIcon);
    }

    public boolean setPointerIcon(android.view.PointerIcon pointerIcon, int i, int i2, int i3, android.os.IBinder iBinder) {
        return this.mGlobal.setPointerIcon(pointerIcon, i, i2, i3, iBinder);
    }

    public boolean isStylusPointerIconEnabled() {
        if (this.mIsStylusPointerIconEnabled == null) {
            this.mIsStylusPointerIconEnabled = java.lang.Boolean.valueOf(android.hardware.input.InputSettings.isStylusPointerIconEnabled(this.mContext));
        }
        return this.mIsStylusPointerIconEnabled.booleanValue();
    }

    public void requestPointerCapture(android.os.IBinder iBinder, boolean z) {
        this.mGlobal.requestPointerCapture(iBinder, z);
    }

    public android.view.InputMonitor monitorGestureInput(java.lang.String str, int i) {
        return this.mGlobal.monitorGestureInput(str, i);
    }

    public void addPortAssociation(java.lang.String str, int i) {
        try {
            this.mIm.addPortAssociation(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removePortAssociation(java.lang.String str) {
        try {
            this.mIm.removePortAssociation(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUniqueIdAssociation(java.lang.String str, java.lang.String str2) {
        this.mGlobal.addUniqueIdAssociation(str, str2);
    }

    public void removeUniqueIdAssociation(java.lang.String str) {
        this.mGlobal.removeUniqueIdAssociation(str);
    }

    public android.hardware.input.HostUsiVersion getHostUsiVersion(android.view.Display display) {
        return this.mGlobal.getHostUsiVersion(display);
    }

    public java.lang.String getInputDeviceBluetoothAddress(int i) {
        return this.mGlobal.getInputDeviceBluetoothAddress(i);
    }

    public android.os.Vibrator getInputDeviceVibrator(int i, int i2) {
        return new android.hardware.input.InputDeviceVibrator(i, i2);
    }

    public void cancelCurrentTouch() {
        this.mGlobal.cancelCurrentTouch();
    }

    public void pilferPointers(android.os.IBinder iBinder) {
        this.mGlobal.pilferPointers(iBinder);
    }

    public void addInputDeviceBatteryListener(int i, java.util.concurrent.Executor executor, android.hardware.input.InputManager.InputDeviceBatteryListener inputDeviceBatteryListener) {
        this.mGlobal.addInputDeviceBatteryListener(i, executor, inputDeviceBatteryListener);
    }

    public void removeInputDeviceBatteryListener(int i, android.hardware.input.InputManager.InputDeviceBatteryListener inputDeviceBatteryListener) {
        this.mGlobal.removeInputDeviceBatteryListener(i, inputDeviceBatteryListener);
    }

    public boolean areTouchpadGesturesAvailable(android.content.Context context) {
        return true;
    }

    public void registerKeyboardBacklightListener(java.util.concurrent.Executor executor, android.hardware.input.InputManager.KeyboardBacklightListener keyboardBacklightListener) throws java.lang.IllegalArgumentException {
        this.mGlobal.registerKeyboardBacklightListener(executor, keyboardBacklightListener);
    }

    public void unregisterKeyboardBacklightListener(android.hardware.input.InputManager.KeyboardBacklightListener keyboardBacklightListener) {
        this.mGlobal.unregisterKeyboardBacklightListener(keyboardBacklightListener);
    }

    public void registerStickyModifierStateListener(java.util.concurrent.Executor executor, android.hardware.input.InputManager.StickyModifierStateListener stickyModifierStateListener) throws java.lang.IllegalArgumentException {
        if (!android.hardware.input.InputSettings.isAccessibilityStickyKeysFeatureEnabled()) {
            return;
        }
        this.mGlobal.registerStickyModifierStateListener(executor, stickyModifierStateListener);
    }

    public void unregisterStickyModifierStateListener(android.hardware.input.InputManager.StickyModifierStateListener stickyModifierStateListener) {
        if (!android.hardware.input.InputSettings.isAccessibilityStickyKeysFeatureEnabled()) {
            return;
        }
        this.mGlobal.unregisterStickyModifierStateListener(stickyModifierStateListener);
    }
}
