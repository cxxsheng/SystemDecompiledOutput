package android.hardware.input;

/* loaded from: classes2.dex */
public interface IInputManager extends android.os.IInterface {
    void addKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) throws android.os.RemoteException;

    void addPortAssociation(java.lang.String str, int i) throws android.os.RemoteException;

    void addUniqueIdAssociation(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void cancelCurrentTouch() throws android.os.RemoteException;

    void cancelVibrate(int i, android.os.IBinder iBinder) throws android.os.RemoteException;

    void clearAllModifierKeyRemappings() throws android.os.RemoteException;

    void closeLightSession(int i, android.os.IBinder iBinder) throws android.os.RemoteException;

    void disableInputDevice(int i) throws android.os.RemoteException;

    void disableSensor(int i, int i2) throws android.os.RemoteException;

    void enableInputDevice(int i) throws android.os.RemoteException;

    boolean enableSensor(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    boolean flushSensor(int i, int i2) throws android.os.RemoteException;

    android.hardware.input.IInputDeviceBatteryState getBatteryState(int i) throws android.os.RemoteException;

    java.lang.String getCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) throws android.os.RemoteException;

    java.lang.String[] getEnabledKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) throws android.os.RemoteException;

    android.hardware.input.HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) throws android.os.RemoteException;

    android.view.InputDevice getInputDevice(int i) throws android.os.RemoteException;

    java.lang.String getInputDeviceBluetoothAddress(int i) throws android.os.RemoteException;

    int[] getInputDeviceIds() throws android.os.RemoteException;

    android.view.KeyCharacterMap getKeyCharacterMap(java.lang.String str) throws android.os.RemoteException;

    int getKeyCodeForKeyLocation(int i, int i2) throws android.os.RemoteException;

    android.hardware.input.KeyboardLayout getKeyboardLayout(java.lang.String str) throws android.os.RemoteException;

    android.hardware.input.KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) throws android.os.RemoteException;

    android.hardware.input.KeyboardLayout[] getKeyboardLayoutListForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) throws android.os.RemoteException;

    android.hardware.input.KeyboardLayout[] getKeyboardLayouts() throws android.os.RemoteException;

    android.hardware.input.KeyboardLayout[] getKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) throws android.os.RemoteException;

    android.hardware.lights.LightState getLightState(int i, int i2) throws android.os.RemoteException;

    java.util.List<android.hardware.lights.Light> getLights(int i) throws android.os.RemoteException;

    java.util.Map getModifierKeyRemapping() throws android.os.RemoteException;

    int getMousePointerSpeed() throws android.os.RemoteException;

    android.hardware.input.InputSensorInfo[] getSensorList(int i) throws android.os.RemoteException;

    android.hardware.input.TouchCalibration getTouchCalibrationForInputDevice(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String getVelocityTrackerStrategy() throws android.os.RemoteException;

    int[] getVibratorIds(int i) throws android.os.RemoteException;

    boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) throws android.os.RemoteException;

    boolean injectInputEvent(android.view.InputEvent inputEvent, int i) throws android.os.RemoteException;

    boolean injectInputEventToTarget(android.view.InputEvent inputEvent, int i, int i2) throws android.os.RemoteException;

    int isInTabletMode() throws android.os.RemoteException;

    boolean isInputDeviceEnabled(int i) throws android.os.RemoteException;

    int isMicMuted() throws android.os.RemoteException;

    boolean isVibrating(int i) throws android.os.RemoteException;

    android.view.InputMonitor monitorGestureInput(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void openLightSession(int i, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    void pilferPointers(android.os.IBinder iBinder) throws android.os.RemoteException;

    void registerBatteryListener(int i, android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener) throws android.os.RemoteException;

    void registerInputDevicesChangedListener(android.hardware.input.IInputDevicesChangedListener iInputDevicesChangedListener) throws android.os.RemoteException;

    void registerKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener) throws android.os.RemoteException;

    boolean registerSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) throws android.os.RemoteException;

    void registerStickyModifierStateListener(android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener) throws android.os.RemoteException;

    void registerTabletModeChangedListener(android.hardware.input.ITabletModeChangedListener iTabletModeChangedListener) throws android.os.RemoteException;

    boolean registerVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException;

    void remapModifierKey(int i, int i2) throws android.os.RemoteException;

    void removeKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) throws android.os.RemoteException;

    void removePortAssociation(java.lang.String str) throws android.os.RemoteException;

    void removeUniqueIdAssociation(java.lang.String str) throws android.os.RemoteException;

    void requestPointerCapture(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) throws android.os.RemoteException;

    void setCustomPointerIcon(android.view.PointerIcon pointerIcon) throws android.os.RemoteException;

    void setKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype, java.lang.String str) throws android.os.RemoteException;

    void setLightStates(int i, int[] iArr, android.hardware.lights.LightState[] lightStateArr, android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean setPointerIcon(android.view.PointerIcon pointerIcon, int i, int i2, int i3, android.os.IBinder iBinder) throws android.os.RemoteException;

    void setPointerIconType(int i) throws android.os.RemoteException;

    void setTouchCalibrationForInputDevice(java.lang.String str, int i, android.hardware.input.TouchCalibration touchCalibration) throws android.os.RemoteException;

    void tryPointerSpeed(int i) throws android.os.RemoteException;

    void unregisterBatteryListener(int i, android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener) throws android.os.RemoteException;

    void unregisterKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener) throws android.os.RemoteException;

    void unregisterSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) throws android.os.RemoteException;

    void unregisterStickyModifierStateListener(android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener) throws android.os.RemoteException;

    boolean unregisterVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException;

    android.view.VerifiedInputEvent verifyInputEvent(android.view.InputEvent inputEvent) throws android.os.RemoteException;

    void vibrate(int i, android.os.VibrationEffect vibrationEffect, android.os.IBinder iBinder) throws android.os.RemoteException;

    void vibrateCombined(int i, android.os.CombinedVibration combinedVibration, android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.hardware.input.IInputManager {
        @Override // android.hardware.input.IInputManager
        public java.lang.String getVelocityTrackerStrategy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public android.view.InputDevice getInputDevice(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int[] getInputDeviceIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public boolean isInputDeviceEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void enableInputDevice(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void disableInputDevice(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public int getKeyCodeForKeyLocation(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public android.view.KeyCharacterMap getKeyCharacterMap(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int getMousePointerSpeed() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public void tryPointerSpeed(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean injectInputEvent(android.view.InputEvent inputEvent, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean injectInputEventToTarget(android.view.InputEvent inputEvent, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public android.view.VerifiedInputEvent verifyInputEvent(android.view.InputEvent inputEvent) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.input.TouchCalibration getTouchCalibrationForInputDevice(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void setTouchCalibrationForInputDevice(java.lang.String str, int i, android.hardware.input.TouchCalibration touchCalibration) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.input.KeyboardLayout[] getKeyboardLayouts() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.input.KeyboardLayout[] getKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.input.KeyboardLayout getKeyboardLayout(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public java.lang.String getCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void setCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public java.lang.String[] getEnabledKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void addKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.input.KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void setKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.input.KeyboardLayout[] getKeyboardLayoutListForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void remapModifierKey(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void clearAllModifierKeyRemappings() throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public java.util.Map getModifierKeyRemapping() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void registerInputDevicesChangedListener(android.hardware.input.IInputDevicesChangedListener iInputDevicesChangedListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public int isInTabletMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public void registerTabletModeChangedListener(android.hardware.input.ITabletModeChangedListener iTabletModeChangedListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public int isMicMuted() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public void vibrate(int i, android.os.VibrationEffect vibrationEffect, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void vibrateCombined(int i, android.os.CombinedVibration combinedVibration, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void cancelVibrate(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public int[] getVibratorIds(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public boolean isVibrating(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean registerVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean unregisterVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.input.IInputDeviceBatteryState getBatteryState(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void setPointerIconType(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void setCustomPointerIcon(android.view.PointerIcon pointerIcon) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean setPointerIcon(android.view.PointerIcon pointerIcon, int i, int i2, int i3, android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void requestPointerCapture(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public android.view.InputMonitor monitorGestureInput(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void addPortAssociation(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removePortAssociation(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void addUniqueIdAssociation(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeUniqueIdAssociation(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.input.InputSensorInfo[] getSensorList(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public boolean registerSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean enableSensor(int i, int i2, int i3, int i4) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void disableSensor(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean flushSensor(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public java.util.List<android.hardware.lights.Light> getLights(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.lights.LightState getLightState(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void setLightStates(int i, int[] iArr, android.hardware.lights.LightState[] lightStateArr, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void openLightSession(int i, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void closeLightSession(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void cancelCurrentTouch() throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerBatteryListener(int i, android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterBatteryListener(int i, android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public java.lang.String getInputDeviceBluetoothAddress(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void pilferPointers(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public android.hardware.input.HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void registerStickyModifierStateListener(android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterStickyModifierStateListener(android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.input.IInputManager {
        public static final java.lang.String DESCRIPTOR = "android.hardware.input.IInputManager";
        static final int TRANSACTION_addKeyboardLayoutForInputDevice = 23;
        static final int TRANSACTION_addPortAssociation = 48;
        static final int TRANSACTION_addUniqueIdAssociation = 50;
        static final int TRANSACTION_cancelCurrentTouch = 63;
        static final int TRANSACTION_cancelVibrate = 37;
        static final int TRANSACTION_clearAllModifierKeyRemappings = 29;
        static final int TRANSACTION_closeLightSession = 62;
        static final int TRANSACTION_disableInputDevice = 6;
        static final int TRANSACTION_disableSensor = 56;
        static final int TRANSACTION_enableInputDevice = 5;
        static final int TRANSACTION_enableSensor = 55;
        static final int TRANSACTION_flushSensor = 57;
        static final int TRANSACTION_getBatteryState = 42;
        static final int TRANSACTION_getCurrentKeyboardLayoutForInputDevice = 20;
        static final int TRANSACTION_getEnabledKeyboardLayoutsForInputDevice = 22;
        static final int TRANSACTION_getHostUsiVersionFromDisplayConfig = 70;
        static final int TRANSACTION_getInputDevice = 2;
        static final int TRANSACTION_getInputDeviceBluetoothAddress = 66;
        static final int TRANSACTION_getInputDeviceIds = 3;
        static final int TRANSACTION_getKeyCharacterMap = 9;
        static final int TRANSACTION_getKeyCodeForKeyLocation = 8;
        static final int TRANSACTION_getKeyboardLayout = 19;
        static final int TRANSACTION_getKeyboardLayoutForInputDevice = 25;
        static final int TRANSACTION_getKeyboardLayoutListForInputDevice = 27;
        static final int TRANSACTION_getKeyboardLayouts = 17;
        static final int TRANSACTION_getKeyboardLayoutsForInputDevice = 18;
        static final int TRANSACTION_getLightState = 59;
        static final int TRANSACTION_getLights = 58;
        static final int TRANSACTION_getModifierKeyRemapping = 30;
        static final int TRANSACTION_getMousePointerSpeed = 10;
        static final int TRANSACTION_getSensorList = 52;
        static final int TRANSACTION_getTouchCalibrationForInputDevice = 15;
        static final int TRANSACTION_getVelocityTrackerStrategy = 1;
        static final int TRANSACTION_getVibratorIds = 38;
        static final int TRANSACTION_hasKeys = 7;
        static final int TRANSACTION_injectInputEvent = 12;
        static final int TRANSACTION_injectInputEventToTarget = 13;
        static final int TRANSACTION_isInTabletMode = 32;
        static final int TRANSACTION_isInputDeviceEnabled = 4;
        static final int TRANSACTION_isMicMuted = 34;
        static final int TRANSACTION_isVibrating = 39;
        static final int TRANSACTION_monitorGestureInput = 47;
        static final int TRANSACTION_openLightSession = 61;
        static final int TRANSACTION_pilferPointers = 67;
        static final int TRANSACTION_registerBatteryListener = 64;
        static final int TRANSACTION_registerInputDevicesChangedListener = 31;
        static final int TRANSACTION_registerKeyboardBacklightListener = 68;
        static final int TRANSACTION_registerSensorListener = 53;
        static final int TRANSACTION_registerStickyModifierStateListener = 71;
        static final int TRANSACTION_registerTabletModeChangedListener = 33;
        static final int TRANSACTION_registerVibratorStateListener = 40;
        static final int TRANSACTION_remapModifierKey = 28;
        static final int TRANSACTION_removeKeyboardLayoutForInputDevice = 24;
        static final int TRANSACTION_removePortAssociation = 49;
        static final int TRANSACTION_removeUniqueIdAssociation = 51;
        static final int TRANSACTION_requestPointerCapture = 46;
        static final int TRANSACTION_setCurrentKeyboardLayoutForInputDevice = 21;
        static final int TRANSACTION_setCustomPointerIcon = 44;
        static final int TRANSACTION_setKeyboardLayoutForInputDevice = 26;
        static final int TRANSACTION_setLightStates = 60;
        static final int TRANSACTION_setPointerIcon = 45;
        static final int TRANSACTION_setPointerIconType = 43;
        static final int TRANSACTION_setTouchCalibrationForInputDevice = 16;
        static final int TRANSACTION_tryPointerSpeed = 11;
        static final int TRANSACTION_unregisterBatteryListener = 65;
        static final int TRANSACTION_unregisterKeyboardBacklightListener = 69;
        static final int TRANSACTION_unregisterSensorListener = 54;
        static final int TRANSACTION_unregisterStickyModifierStateListener = 72;
        static final int TRANSACTION_unregisterVibratorStateListener = 41;
        static final int TRANSACTION_verifyInputEvent = 14;
        static final int TRANSACTION_vibrate = 35;
        static final int TRANSACTION_vibrateCombined = 36;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.hardware.input.IInputManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.input.IInputManager)) {
                return (android.hardware.input.IInputManager) queryLocalInterface;
            }
            return new android.hardware.input.IInputManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVelocityTrackerStrategy";
                case 2:
                    return "getInputDevice";
                case 3:
                    return "getInputDeviceIds";
                case 4:
                    return "isInputDeviceEnabled";
                case 5:
                    return "enableInputDevice";
                case 6:
                    return "disableInputDevice";
                case 7:
                    return "hasKeys";
                case 8:
                    return "getKeyCodeForKeyLocation";
                case 9:
                    return "getKeyCharacterMap";
                case 10:
                    return "getMousePointerSpeed";
                case 11:
                    return "tryPointerSpeed";
                case 12:
                    return "injectInputEvent";
                case 13:
                    return "injectInputEventToTarget";
                case 14:
                    return "verifyInputEvent";
                case 15:
                    return "getTouchCalibrationForInputDevice";
                case 16:
                    return "setTouchCalibrationForInputDevice";
                case 17:
                    return "getKeyboardLayouts";
                case 18:
                    return "getKeyboardLayoutsForInputDevice";
                case 19:
                    return "getKeyboardLayout";
                case 20:
                    return "getCurrentKeyboardLayoutForInputDevice";
                case 21:
                    return "setCurrentKeyboardLayoutForInputDevice";
                case 22:
                    return "getEnabledKeyboardLayoutsForInputDevice";
                case 23:
                    return "addKeyboardLayoutForInputDevice";
                case 24:
                    return "removeKeyboardLayoutForInputDevice";
                case 25:
                    return "getKeyboardLayoutForInputDevice";
                case 26:
                    return "setKeyboardLayoutForInputDevice";
                case 27:
                    return "getKeyboardLayoutListForInputDevice";
                case 28:
                    return "remapModifierKey";
                case 29:
                    return "clearAllModifierKeyRemappings";
                case 30:
                    return "getModifierKeyRemapping";
                case 31:
                    return "registerInputDevicesChangedListener";
                case 32:
                    return "isInTabletMode";
                case 33:
                    return "registerTabletModeChangedListener";
                case 34:
                    return "isMicMuted";
                case 35:
                    return "vibrate";
                case 36:
                    return "vibrateCombined";
                case 37:
                    return "cancelVibrate";
                case 38:
                    return "getVibratorIds";
                case 39:
                    return "isVibrating";
                case 40:
                    return "registerVibratorStateListener";
                case 41:
                    return "unregisterVibratorStateListener";
                case 42:
                    return "getBatteryState";
                case 43:
                    return "setPointerIconType";
                case 44:
                    return "setCustomPointerIcon";
                case 45:
                    return "setPointerIcon";
                case 46:
                    return "requestPointerCapture";
                case 47:
                    return "monitorGestureInput";
                case 48:
                    return "addPortAssociation";
                case 49:
                    return "removePortAssociation";
                case 50:
                    return "addUniqueIdAssociation";
                case 51:
                    return "removeUniqueIdAssociation";
                case 52:
                    return "getSensorList";
                case 53:
                    return "registerSensorListener";
                case 54:
                    return "unregisterSensorListener";
                case 55:
                    return "enableSensor";
                case 56:
                    return "disableSensor";
                case 57:
                    return "flushSensor";
                case 58:
                    return "getLights";
                case 59:
                    return "getLightState";
                case 60:
                    return "setLightStates";
                case 61:
                    return "openLightSession";
                case 62:
                    return "closeLightSession";
                case 63:
                    return "cancelCurrentTouch";
                case 64:
                    return "registerBatteryListener";
                case 65:
                    return "unregisterBatteryListener";
                case 66:
                    return "getInputDeviceBluetoothAddress";
                case 67:
                    return "pilferPointers";
                case 68:
                    return "registerKeyboardBacklightListener";
                case 69:
                    return "unregisterKeyboardBacklightListener";
                case 70:
                    return "getHostUsiVersionFromDisplayConfig";
                case 71:
                    return "registerStickyModifierStateListener";
                case 72:
                    return "unregisterStickyModifierStateListener";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            boolean[] zArr;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String velocityTrackerStrategy = getVelocityTrackerStrategy();
                    parcel2.writeNoException();
                    parcel2.writeString(velocityTrackerStrategy);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.InputDevice inputDevice = getInputDevice(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputDevice, 1);
                    return true;
                case 3:
                    int[] inputDeviceIds = getInputDeviceIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(inputDeviceIds);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInputDeviceEnabled = isInputDeviceEnabled(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInputDeviceEnabled);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableInputDevice(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableInputDevice(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    int readInt7 = parcel.readInt();
                    if (readInt7 < 0) {
                        zArr = null;
                    } else {
                        zArr = new boolean[readInt7];
                    }
                    parcel.enforceNoDataAvail();
                    boolean hasKeys = hasKeys(readInt5, readInt6, createIntArray, zArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasKeys);
                    parcel2.writeBooleanArray(zArr);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int keyCodeForKeyLocation = getKeyCodeForKeyLocation(readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyCodeForKeyLocation);
                    return true;
                case 9:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.view.KeyCharacterMap keyCharacterMap = getKeyCharacterMap(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCharacterMap, 1);
                    return true;
                case 10:
                    int mousePointerSpeed = getMousePointerSpeed();
                    parcel2.writeNoException();
                    parcel2.writeInt(mousePointerSpeed);
                    return true;
                case 11:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    tryPointerSpeed(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.view.InputEvent inputEvent = (android.view.InputEvent) parcel.readTypedObject(android.view.InputEvent.CREATOR);
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean injectInputEvent = injectInputEvent(inputEvent, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(injectInputEvent);
                    return true;
                case 13:
                    android.view.InputEvent inputEvent2 = (android.view.InputEvent) parcel.readTypedObject(android.view.InputEvent.CREATOR);
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean injectInputEventToTarget = injectInputEventToTarget(inputEvent2, readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(injectInputEventToTarget);
                    return true;
                case 14:
                    android.view.InputEvent inputEvent3 = (android.view.InputEvent) parcel.readTypedObject(android.view.InputEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.view.VerifiedInputEvent verifyInputEvent = verifyInputEvent(inputEvent3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyInputEvent, 1);
                    return true;
                case 15:
                    java.lang.String readString2 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.input.TouchCalibration touchCalibrationForInputDevice = getTouchCalibrationForInputDevice(readString2, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(touchCalibrationForInputDevice, 1);
                    return true;
                case 16:
                    java.lang.String readString3 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    android.hardware.input.TouchCalibration touchCalibration = (android.hardware.input.TouchCalibration) parcel.readTypedObject(android.hardware.input.TouchCalibration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTouchCalibrationForInputDevice(readString3, readInt15, touchCalibration);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.hardware.input.KeyboardLayout[] keyboardLayouts = getKeyboardLayouts();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyboardLayouts, 1);
                    return true;
                case 18:
                    android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier = (android.hardware.input.InputDeviceIdentifier) parcel.readTypedObject(android.hardware.input.InputDeviceIdentifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.input.KeyboardLayout[] keyboardLayoutsForInputDevice = getKeyboardLayoutsForInputDevice(inputDeviceIdentifier);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyboardLayoutsForInputDevice, 1);
                    return true;
                case 19:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.input.KeyboardLayout keyboardLayout = getKeyboardLayout(readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyboardLayout, 1);
                    return true;
                case 20:
                    android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier2 = (android.hardware.input.InputDeviceIdentifier) parcel.readTypedObject(android.hardware.input.InputDeviceIdentifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String currentKeyboardLayoutForInputDevice = getCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier2);
                    parcel2.writeNoException();
                    parcel2.writeString(currentKeyboardLayoutForInputDevice);
                    return true;
                case 21:
                    android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier3 = (android.hardware.input.InputDeviceIdentifier) parcel.readTypedObject(android.hardware.input.InputDeviceIdentifier.CREATOR);
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier3, readString5);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier4 = (android.hardware.input.InputDeviceIdentifier) parcel.readTypedObject(android.hardware.input.InputDeviceIdentifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String[] enabledKeyboardLayoutsForInputDevice = getEnabledKeyboardLayoutsForInputDevice(inputDeviceIdentifier4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(enabledKeyboardLayoutsForInputDevice);
                    return true;
                case 23:
                    android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier5 = (android.hardware.input.InputDeviceIdentifier) parcel.readTypedObject(android.hardware.input.InputDeviceIdentifier.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addKeyboardLayoutForInputDevice(inputDeviceIdentifier5, readString6);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier6 = (android.hardware.input.InputDeviceIdentifier) parcel.readTypedObject(android.hardware.input.InputDeviceIdentifier.CREATOR);
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeKeyboardLayoutForInputDevice(inputDeviceIdentifier6, readString7);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier7 = (android.hardware.input.InputDeviceIdentifier) parcel.readTypedObject(android.hardware.input.InputDeviceIdentifier.CREATOR);
                    int readInt16 = parcel.readInt();
                    android.view.inputmethod.InputMethodInfo inputMethodInfo = (android.view.inputmethod.InputMethodInfo) parcel.readTypedObject(android.view.inputmethod.InputMethodInfo.CREATOR);
                    android.view.inputmethod.InputMethodSubtype inputMethodSubtype = (android.view.inputmethod.InputMethodSubtype) parcel.readTypedObject(android.view.inputmethod.InputMethodSubtype.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.input.KeyboardLayoutSelectionResult keyboardLayoutForInputDevice = getKeyboardLayoutForInputDevice(inputDeviceIdentifier7, readInt16, inputMethodInfo, inputMethodSubtype);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyboardLayoutForInputDevice, 1);
                    return true;
                case 26:
                    android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier8 = (android.hardware.input.InputDeviceIdentifier) parcel.readTypedObject(android.hardware.input.InputDeviceIdentifier.CREATOR);
                    int readInt17 = parcel.readInt();
                    android.view.inputmethod.InputMethodInfo inputMethodInfo2 = (android.view.inputmethod.InputMethodInfo) parcel.readTypedObject(android.view.inputmethod.InputMethodInfo.CREATOR);
                    android.view.inputmethod.InputMethodSubtype inputMethodSubtype2 = (android.view.inputmethod.InputMethodSubtype) parcel.readTypedObject(android.view.inputmethod.InputMethodSubtype.CREATOR);
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setKeyboardLayoutForInputDevice(inputDeviceIdentifier8, readInt17, inputMethodInfo2, inputMethodSubtype2, readString8);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier9 = (android.hardware.input.InputDeviceIdentifier) parcel.readTypedObject(android.hardware.input.InputDeviceIdentifier.CREATOR);
                    int readInt18 = parcel.readInt();
                    android.view.inputmethod.InputMethodInfo inputMethodInfo3 = (android.view.inputmethod.InputMethodInfo) parcel.readTypedObject(android.view.inputmethod.InputMethodInfo.CREATOR);
                    android.view.inputmethod.InputMethodSubtype inputMethodSubtype3 = (android.view.inputmethod.InputMethodSubtype) parcel.readTypedObject(android.view.inputmethod.InputMethodSubtype.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.input.KeyboardLayout[] keyboardLayoutListForInputDevice = getKeyboardLayoutListForInputDevice(inputDeviceIdentifier9, readInt18, inputMethodInfo3, inputMethodSubtype3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyboardLayoutListForInputDevice, 1);
                    return true;
                case 28:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    remapModifierKey(readInt19, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    clearAllModifierKeyRemappings();
                    parcel2.writeNoException();
                    return true;
                case 30:
                    java.util.Map modifierKeyRemapping = getModifierKeyRemapping();
                    parcel2.writeNoException();
                    parcel2.writeMap(modifierKeyRemapping);
                    return true;
                case 31:
                    android.hardware.input.IInputDevicesChangedListener asInterface = android.hardware.input.IInputDevicesChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerInputDevicesChangedListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int isInTabletMode = isInTabletMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(isInTabletMode);
                    return true;
                case 33:
                    android.hardware.input.ITabletModeChangedListener asInterface2 = android.hardware.input.ITabletModeChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTabletModeChangedListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int isMicMuted = isMicMuted();
                    parcel2.writeNoException();
                    parcel2.writeInt(isMicMuted);
                    return true;
                case 35:
                    int readInt21 = parcel.readInt();
                    android.os.VibrationEffect vibrationEffect = (android.os.VibrationEffect) parcel.readTypedObject(android.os.VibrationEffect.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    vibrate(readInt21, vibrationEffect, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt22 = parcel.readInt();
                    android.os.CombinedVibration combinedVibration = (android.os.CombinedVibration) parcel.readTypedObject(android.os.CombinedVibration.CREATOR);
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    vibrateCombined(readInt22, combinedVibration, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt23 = parcel.readInt();
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelVibrate(readInt23, readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] vibratorIds = getVibratorIds(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(vibratorIds);
                    return true;
                case 39:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVibrating = isVibrating(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVibrating);
                    return true;
                case 40:
                    int readInt26 = parcel.readInt();
                    android.os.IVibratorStateListener asInterface3 = android.os.IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerVibratorStateListener = registerVibratorStateListener(readInt26, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerVibratorStateListener);
                    return true;
                case 41:
                    int readInt27 = parcel.readInt();
                    android.os.IVibratorStateListener asInterface4 = android.os.IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterVibratorStateListener = unregisterVibratorStateListener(readInt27, asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterVibratorStateListener);
                    return true;
                case 42:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.input.IInputDeviceBatteryState batteryState = getBatteryState(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(batteryState, 1);
                    return true;
                case 43:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPointerIconType(readInt29);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    android.view.PointerIcon pointerIcon = (android.view.PointerIcon) parcel.readTypedObject(android.view.PointerIcon.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCustomPointerIcon(pointerIcon);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    android.view.PointerIcon pointerIcon2 = (android.view.PointerIcon) parcel.readTypedObject(android.view.PointerIcon.CREATOR);
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean pointerIcon3 = setPointerIcon(pointerIcon2, readInt30, readInt31, readInt32, readStrongBinder4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pointerIcon3);
                    return true;
                case 46:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestPointerCapture(readStrongBinder5, readBoolean);
                    return true;
                case 47:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    java.lang.String readString9 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.InputMonitor monitorGestureInput = monitorGestureInput(readStrongBinder6, readString9, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(monitorGestureInput, 1);
                    return true;
                case 48:
                    java.lang.String readString10 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPortAssociation(readString10, readInt34);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePortAssociation(readString11);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addUniqueIdAssociation(readString12, readString13);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeUniqueIdAssociation(readString14);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.input.InputSensorInfo[] sensorList = getSensorList(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(sensorList, 1);
                    return true;
                case 53:
                    android.hardware.input.IInputSensorEventListener asInterface5 = android.hardware.input.IInputSensorEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerSensorListener = registerSensorListener(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerSensorListener);
                    return true;
                case 54:
                    android.hardware.input.IInputSensorEventListener asInterface6 = android.hardware.input.IInputSensorEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSensorListener(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enableSensor = enableSensor(readInt36, readInt37, readInt38, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableSensor);
                    return true;
                case 56:
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableSensor(readInt40, readInt41);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean flushSensor = flushSensor(readInt42, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(flushSensor);
                    return true;
                case 58:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.lights.Light> lights = getLights(readInt44);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(lights, 1);
                    return true;
                case 59:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.lights.LightState lightState = getLightState(readInt45, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lightState, 1);
                    return true;
                case 60:
                    int readInt47 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    android.hardware.lights.LightState[] lightStateArr = (android.hardware.lights.LightState[]) parcel.createTypedArray(android.hardware.lights.LightState.CREATOR);
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setLightStates(readInt47, createIntArray2, lightStateArr, readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt48 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    openLightSession(readInt48, readString15, readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt49 = parcel.readInt();
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeLightSession(readInt49, readStrongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    cancelCurrentTouch();
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int readInt50 = parcel.readInt();
                    android.hardware.input.IInputDeviceBatteryListener asInterface7 = android.hardware.input.IInputDeviceBatteryListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBatteryListener(readInt50, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt51 = parcel.readInt();
                    android.hardware.input.IInputDeviceBatteryListener asInterface8 = android.hardware.input.IInputDeviceBatteryListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterBatteryListener(readInt51, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String inputDeviceBluetoothAddress = getInputDeviceBluetoothAddress(readInt52);
                    parcel2.writeNoException();
                    parcel2.writeString(inputDeviceBluetoothAddress);
                    return true;
                case 67:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    pilferPointers(readStrongBinder10);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    android.hardware.input.IKeyboardBacklightListener asInterface9 = android.hardware.input.IKeyboardBacklightListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerKeyboardBacklightListener(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    android.hardware.input.IKeyboardBacklightListener asInterface10 = android.hardware.input.IKeyboardBacklightListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterKeyboardBacklightListener(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.input.HostUsiVersion hostUsiVersionFromDisplayConfig = getHostUsiVersionFromDisplayConfig(readInt53);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hostUsiVersionFromDisplayConfig, 1);
                    return true;
                case 71:
                    android.hardware.input.IStickyModifierStateListener asInterface11 = android.hardware.input.IStickyModifierStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStickyModifierStateListener(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    android.hardware.input.IStickyModifierStateListener asInterface12 = android.hardware.input.IStickyModifierStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStickyModifierStateListener(asInterface12);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.input.IInputManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.input.IInputManager.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.input.IInputManager
            public java.lang.String getVelocityTrackerStrategy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.view.InputDevice getInputDevice(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.InputDevice) obtain2.readTypedObject(android.view.InputDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getInputDeviceIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean isInputDeviceEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void enableInputDevice(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void disableInputDevice(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(zArr.length);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readBooleanArray(zArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getKeyCodeForKeyLocation(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.view.KeyCharacterMap getKeyCharacterMap(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.KeyCharacterMap) obtain2.readTypedObject(android.view.KeyCharacterMap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getMousePointerSpeed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void tryPointerSpeed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean injectInputEvent(android.view.InputEvent inputEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean injectInputEventToTarget(android.view.InputEvent inputEvent, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.view.VerifiedInputEvent verifyInputEvent(android.view.InputEvent inputEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.VerifiedInputEvent) obtain2.readTypedObject(android.view.VerifiedInputEvent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.input.TouchCalibration getTouchCalibrationForInputDevice(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.input.TouchCalibration) obtain2.readTypedObject(android.hardware.input.TouchCalibration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setTouchCalibrationForInputDevice(java.lang.String str, int i, android.hardware.input.TouchCalibration touchCalibration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(touchCalibration, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.input.KeyboardLayout[] getKeyboardLayouts() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.input.KeyboardLayout[]) obtain2.createTypedArray(android.hardware.input.KeyboardLayout.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.input.KeyboardLayout[] getKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.input.KeyboardLayout[]) obtain2.createTypedArray(android.hardware.input.KeyboardLayout.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.input.KeyboardLayout getKeyboardLayout(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.input.KeyboardLayout) obtain2.readTypedObject(android.hardware.input.KeyboardLayout.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public java.lang.String getCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public java.lang.String[] getEnabledKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.input.KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(inputMethodInfo, 0);
                    obtain.writeTypedObject(inputMethodSubtype, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.input.KeyboardLayoutSelectionResult) obtain2.readTypedObject(android.hardware.input.KeyboardLayoutSelectionResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(inputMethodInfo, 0);
                    obtain.writeTypedObject(inputMethodSubtype, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.input.KeyboardLayout[] getKeyboardLayoutListForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(inputMethodInfo, 0);
                    obtain.writeTypedObject(inputMethodSubtype, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.input.KeyboardLayout[]) obtain2.createTypedArray(android.hardware.input.KeyboardLayout.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void remapModifierKey(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void clearAllModifierKeyRemappings() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public java.util.Map getModifierKeyRemapping() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerInputDevicesChangedListener(android.hardware.input.IInputDevicesChangedListener iInputDevicesChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputDevicesChangedListener);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int isInTabletMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerTabletModeChangedListener(android.hardware.input.ITabletModeChangedListener iTabletModeChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTabletModeChangedListener);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int isMicMuted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void vibrate(int i, android.os.VibrationEffect vibrationEffect, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(vibrationEffect, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void vibrateCombined(int i, android.os.CombinedVibration combinedVibration, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(combinedVibration, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void cancelVibrate(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getVibratorIds(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean isVibrating(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean registerVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean unregisterVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.input.IInputDeviceBatteryState getBatteryState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.input.IInputDeviceBatteryState) obtain2.readTypedObject(android.hardware.input.IInputDeviceBatteryState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setPointerIconType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setCustomPointerIcon(android.view.PointerIcon pointerIcon) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pointerIcon, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setPointerIcon(android.view.PointerIcon pointerIcon, int i, int i2, int i3, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pointerIcon, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void requestPointerCapture(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(46, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.view.InputMonitor monitorGestureInput(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.InputMonitor) obtain2.readTypedObject(android.view.InputMonitor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addPortAssociation(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removePortAssociation(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addUniqueIdAssociation(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeUniqueIdAssociation(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.input.InputSensorInfo[] getSensorList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.input.InputSensorInfo[]) obtain2.createTypedArray(android.hardware.input.InputSensorInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean registerSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputSensorEventListener);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputSensorEventListener);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean enableSensor(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void disableSensor(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean flushSensor(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public java.util.List<android.hardware.lights.Light> getLights(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.lights.Light.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.lights.LightState getLightState(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.lights.LightState) obtain2.readTypedObject(android.hardware.lights.LightState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setLightStates(int i, int[] iArr, android.hardware.lights.LightState[] lightStateArr, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedArray(lightStateArr, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void openLightSession(int i, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void closeLightSession(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void cancelCurrentTouch() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerBatteryListener(int i, android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInputDeviceBatteryListener);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterBatteryListener(int i, android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInputDeviceBatteryListener);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public java.lang.String getInputDeviceBluetoothAddress(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void pilferPointers(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyboardBacklightListener);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyboardBacklightListener);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public android.hardware.input.HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.input.HostUsiVersion) obtain2.readTypedObject(android.hardware.input.HostUsiVersion.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerStickyModifierStateListener(android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStickyModifierStateListener);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterStickyModifierStateListener(android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.input.IInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStickyModifierStateListener);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setCurrentKeyboardLayoutForInputDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
        }

        protected void addKeyboardLayoutForInputDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
        }

        protected void removeKeyboardLayoutForInputDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
        }

        protected void setKeyboardLayoutForInputDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
        }

        protected void remapModifierKey_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.REMAP_MODIFIER_KEYS, getCallingPid(), getCallingUid());
        }

        protected void clearAllModifierKeyRemappings_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.REMAP_MODIFIER_KEYS, getCallingPid(), getCallingUid());
        }

        protected void getModifierKeyRemapping_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.REMAP_MODIFIER_KEYS, getCallingPid(), getCallingUid());
        }

        protected void getInputDeviceBluetoothAddress_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BLUETOOTH, getCallingPid(), getCallingUid());
        }

        protected void pilferPointers_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MONITOR_INPUT, getCallingPid(), getCallingUid());
        }

        protected void registerKeyboardBacklightListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MONITOR_KEYBOARD_BACKLIGHT, getCallingPid(), getCallingUid());
        }

        protected void unregisterKeyboardBacklightListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MONITOR_KEYBOARD_BACKLIGHT, getCallingPid(), getCallingUid());
        }

        protected void registerStickyModifierStateListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MONITOR_STICKY_MODIFIER_STATE, getCallingPid(), getCallingUid());
        }

        protected void unregisterStickyModifierStateListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MONITOR_STICKY_MODIFIER_STATE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 71;
        }
    }
}
