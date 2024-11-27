package android.companion.virtual;

/* loaded from: classes.dex */
public interface IVirtualDevice extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtual.IVirtualDevice";

    void addActivityPolicyExemption(android.content.ComponentName componentName) throws android.os.RemoteException;

    void close() throws android.os.RemoteException;

    void createVirtualDpad(android.hardware.input.VirtualDpadConfig virtualDpadConfig, android.os.IBinder iBinder) throws android.os.RemoteException;

    void createVirtualKeyboard(android.hardware.input.VirtualKeyboardConfig virtualKeyboardConfig, android.os.IBinder iBinder) throws android.os.RemoteException;

    void createVirtualMouse(android.hardware.input.VirtualMouseConfig virtualMouseConfig, android.os.IBinder iBinder) throws android.os.RemoteException;

    void createVirtualNavigationTouchpad(android.hardware.input.VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, android.os.IBinder iBinder) throws android.os.RemoteException;

    void createVirtualStylus(android.hardware.input.VirtualStylusConfig virtualStylusConfig, android.os.IBinder iBinder) throws android.os.RemoteException;

    void createVirtualTouchscreen(android.hardware.input.VirtualTouchscreenConfig virtualTouchscreenConfig, android.os.IBinder iBinder) throws android.os.RemoteException;

    int getAssociationId() throws android.os.RemoteException;

    android.graphics.PointF getCursorPosition(android.os.IBinder iBinder) throws android.os.RemoteException;

    int getDeviceId() throws android.os.RemoteException;

    int getDevicePolicy(int i) throws android.os.RemoteException;

    int[] getDisplayIds() throws android.os.RemoteException;

    int getInputDeviceId(android.os.IBinder iBinder) throws android.os.RemoteException;

    java.lang.String getPersistentDeviceId() throws android.os.RemoteException;

    int getVirtualCameraId(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException;

    java.util.List<android.companion.virtual.sensor.VirtualSensor> getVirtualSensorList() throws android.os.RemoteException;

    void launchPendingIntent(int i, android.app.PendingIntent pendingIntent, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void onAudioSessionEnded() throws android.os.RemoteException;

    void onAudioSessionStarting(int i, android.companion.virtual.audio.IAudioRoutingCallback iAudioRoutingCallback, android.companion.virtual.audio.IAudioConfigChangedCallback iAudioConfigChangedCallback) throws android.os.RemoteException;

    void registerIntentInterceptor(android.companion.virtual.IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor, android.content.IntentFilter intentFilter) throws android.os.RemoteException;

    void registerVirtualCamera(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException;

    void removeActivityPolicyExemption(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean sendButtonEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseButtonEvent virtualMouseButtonEvent) throws android.os.RemoteException;

    boolean sendDpadKeyEvent(android.os.IBinder iBinder, android.hardware.input.VirtualKeyEvent virtualKeyEvent) throws android.os.RemoteException;

    boolean sendKeyEvent(android.os.IBinder iBinder, android.hardware.input.VirtualKeyEvent virtualKeyEvent) throws android.os.RemoteException;

    boolean sendRelativeEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseRelativeEvent virtualMouseRelativeEvent) throws android.os.RemoteException;

    boolean sendScrollEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseScrollEvent virtualMouseScrollEvent) throws android.os.RemoteException;

    boolean sendSensorEvent(android.os.IBinder iBinder, android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent) throws android.os.RemoteException;

    boolean sendStylusButtonEvent(android.os.IBinder iBinder, android.hardware.input.VirtualStylusButtonEvent virtualStylusButtonEvent) throws android.os.RemoteException;

    boolean sendStylusMotionEvent(android.os.IBinder iBinder, android.hardware.input.VirtualStylusMotionEvent virtualStylusMotionEvent) throws android.os.RemoteException;

    boolean sendTouchEvent(android.os.IBinder iBinder, android.hardware.input.VirtualTouchEvent virtualTouchEvent) throws android.os.RemoteException;

    void setDevicePolicy(int i, int i2) throws android.os.RemoteException;

    void setDisplayImePolicy(int i, int i2) throws android.os.RemoteException;

    void setShowPointerIcon(boolean z) throws android.os.RemoteException;

    void unregisterInputDevice(android.os.IBinder iBinder) throws android.os.RemoteException;

    void unregisterIntentInterceptor(android.companion.virtual.IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) throws android.os.RemoteException;

    void unregisterVirtualCamera(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException;

    public static class Default implements android.companion.virtual.IVirtualDevice {
        @Override // android.companion.virtual.IVirtualDevice
        public int getAssociationId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getDeviceId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public java.lang.String getPersistentDeviceId() throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int[] getDisplayIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getDevicePolicy(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void close() throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setDevicePolicy(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void addActivityPolicyExemption(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void removeActivityPolicyExemption(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void onAudioSessionStarting(int i, android.companion.virtual.audio.IAudioRoutingCallback iAudioRoutingCallback, android.companion.virtual.audio.IAudioConfigChangedCallback iAudioConfigChangedCallback) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void onAudioSessionEnded() throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualDpad(android.hardware.input.VirtualDpadConfig virtualDpadConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualKeyboard(android.hardware.input.VirtualKeyboardConfig virtualKeyboardConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualMouse(android.hardware.input.VirtualMouseConfig virtualMouseConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualTouchscreen(android.hardware.input.VirtualTouchscreenConfig virtualTouchscreenConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualNavigationTouchpad(android.hardware.input.VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualStylus(android.hardware.input.VirtualStylusConfig virtualStylusConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void unregisterInputDevice(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getInputDeviceId(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendDpadKeyEvent(android.os.IBinder iBinder, android.hardware.input.VirtualKeyEvent virtualKeyEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendKeyEvent(android.os.IBinder iBinder, android.hardware.input.VirtualKeyEvent virtualKeyEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendButtonEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseButtonEvent virtualMouseButtonEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendRelativeEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseRelativeEvent virtualMouseRelativeEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendScrollEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseScrollEvent virtualMouseScrollEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendTouchEvent(android.os.IBinder iBinder, android.hardware.input.VirtualTouchEvent virtualTouchEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendStylusMotionEvent(android.os.IBinder iBinder, android.hardware.input.VirtualStylusMotionEvent virtualStylusMotionEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendStylusButtonEvent(android.os.IBinder iBinder, android.hardware.input.VirtualStylusButtonEvent virtualStylusButtonEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public java.util.List<android.companion.virtual.sensor.VirtualSensor> getVirtualSensorList() throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendSensorEvent(android.os.IBinder iBinder, android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void launchPendingIntent(int i, android.app.PendingIntent pendingIntent, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public android.graphics.PointF getCursorPosition(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setShowPointerIcon(boolean z) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setDisplayImePolicy(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void registerIntentInterceptor(android.companion.virtual.IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor, android.content.IntentFilter intentFilter) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void unregisterIntentInterceptor(android.companion.virtual.IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void registerVirtualCamera(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void unregisterVirtualCamera(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getVirtualCameraId(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtual.IVirtualDevice {
        static final int TRANSACTION_addActivityPolicyExemption = 8;
        static final int TRANSACTION_close = 6;
        static final int TRANSACTION_createVirtualDpad = 12;
        static final int TRANSACTION_createVirtualKeyboard = 13;
        static final int TRANSACTION_createVirtualMouse = 14;
        static final int TRANSACTION_createVirtualNavigationTouchpad = 16;
        static final int TRANSACTION_createVirtualStylus = 17;
        static final int TRANSACTION_createVirtualTouchscreen = 15;
        static final int TRANSACTION_getAssociationId = 1;
        static final int TRANSACTION_getCursorPosition = 31;
        static final int TRANSACTION_getDeviceId = 2;
        static final int TRANSACTION_getDevicePolicy = 5;
        static final int TRANSACTION_getDisplayIds = 4;
        static final int TRANSACTION_getInputDeviceId = 19;
        static final int TRANSACTION_getPersistentDeviceId = 3;
        static final int TRANSACTION_getVirtualCameraId = 38;
        static final int TRANSACTION_getVirtualSensorList = 28;
        static final int TRANSACTION_launchPendingIntent = 30;
        static final int TRANSACTION_onAudioSessionEnded = 11;
        static final int TRANSACTION_onAudioSessionStarting = 10;
        static final int TRANSACTION_registerIntentInterceptor = 34;
        static final int TRANSACTION_registerVirtualCamera = 36;
        static final int TRANSACTION_removeActivityPolicyExemption = 9;
        static final int TRANSACTION_sendButtonEvent = 22;
        static final int TRANSACTION_sendDpadKeyEvent = 20;
        static final int TRANSACTION_sendKeyEvent = 21;
        static final int TRANSACTION_sendRelativeEvent = 23;
        static final int TRANSACTION_sendScrollEvent = 24;
        static final int TRANSACTION_sendSensorEvent = 29;
        static final int TRANSACTION_sendStylusButtonEvent = 27;
        static final int TRANSACTION_sendStylusMotionEvent = 26;
        static final int TRANSACTION_sendTouchEvent = 25;
        static final int TRANSACTION_setDevicePolicy = 7;
        static final int TRANSACTION_setDisplayImePolicy = 33;
        static final int TRANSACTION_setShowPointerIcon = 32;
        static final int TRANSACTION_unregisterInputDevice = 18;
        static final int TRANSACTION_unregisterIntentInterceptor = 35;
        static final int TRANSACTION_unregisterVirtualCamera = 37;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.companion.virtual.IVirtualDevice.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.companion.virtual.IVirtualDevice asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtual.IVirtualDevice)) {
                return (android.companion.virtual.IVirtualDevice) queryLocalInterface;
            }
            return new android.companion.virtual.IVirtualDevice.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAssociationId";
                case 2:
                    return "getDeviceId";
                case 3:
                    return "getPersistentDeviceId";
                case 4:
                    return "getDisplayIds";
                case 5:
                    return "getDevicePolicy";
                case 6:
                    return "close";
                case 7:
                    return "setDevicePolicy";
                case 8:
                    return "addActivityPolicyExemption";
                case 9:
                    return "removeActivityPolicyExemption";
                case 10:
                    return "onAudioSessionStarting";
                case 11:
                    return "onAudioSessionEnded";
                case 12:
                    return "createVirtualDpad";
                case 13:
                    return "createVirtualKeyboard";
                case 14:
                    return "createVirtualMouse";
                case 15:
                    return "createVirtualTouchscreen";
                case 16:
                    return "createVirtualNavigationTouchpad";
                case 17:
                    return "createVirtualStylus";
                case 18:
                    return "unregisterInputDevice";
                case 19:
                    return "getInputDeviceId";
                case 20:
                    return "sendDpadKeyEvent";
                case 21:
                    return "sendKeyEvent";
                case 22:
                    return "sendButtonEvent";
                case 23:
                    return "sendRelativeEvent";
                case 24:
                    return "sendScrollEvent";
                case 25:
                    return "sendTouchEvent";
                case 26:
                    return "sendStylusMotionEvent";
                case 27:
                    return "sendStylusButtonEvent";
                case 28:
                    return "getVirtualSensorList";
                case 29:
                    return "sendSensorEvent";
                case 30:
                    return "launchPendingIntent";
                case 31:
                    return "getCursorPosition";
                case 32:
                    return "setShowPointerIcon";
                case 33:
                    return "setDisplayImePolicy";
                case 34:
                    return "registerIntentInterceptor";
                case 35:
                    return "unregisterIntentInterceptor";
                case 36:
                    return "registerVirtualCamera";
                case 37:
                    return "unregisterVirtualCamera";
                case 38:
                    return "getVirtualCameraId";
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
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int associationId = getAssociationId();
                    parcel2.writeNoException();
                    parcel2.writeInt(associationId);
                    return true;
                case 2:
                    int deviceId = getDeviceId();
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceId);
                    return true;
                case 3:
                    java.lang.String persistentDeviceId = getPersistentDeviceId();
                    parcel2.writeNoException();
                    parcel2.writeString(persistentDeviceId);
                    return true;
                case 4:
                    int[] displayIds = getDisplayIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(displayIds);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int devicePolicy = getDevicePolicy(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(devicePolicy);
                    return true;
                case 6:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDevicePolicy(readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    addActivityPolicyExemption(componentName);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeActivityPolicyExemption(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt4 = parcel.readInt();
                    android.companion.virtual.audio.IAudioRoutingCallback asInterface = android.companion.virtual.audio.IAudioRoutingCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.companion.virtual.audio.IAudioConfigChangedCallback asInterface2 = android.companion.virtual.audio.IAudioConfigChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onAudioSessionStarting(readInt4, asInterface, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    onAudioSessionEnded();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.hardware.input.VirtualDpadConfig virtualDpadConfig = (android.hardware.input.VirtualDpadConfig) parcel.readTypedObject(android.hardware.input.VirtualDpadConfig.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualDpad(virtualDpadConfig, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.hardware.input.VirtualKeyboardConfig virtualKeyboardConfig = (android.hardware.input.VirtualKeyboardConfig) parcel.readTypedObject(android.hardware.input.VirtualKeyboardConfig.CREATOR);
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualKeyboard(virtualKeyboardConfig, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.hardware.input.VirtualMouseConfig virtualMouseConfig = (android.hardware.input.VirtualMouseConfig) parcel.readTypedObject(android.hardware.input.VirtualMouseConfig.CREATOR);
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualMouse(virtualMouseConfig, readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.hardware.input.VirtualTouchscreenConfig virtualTouchscreenConfig = (android.hardware.input.VirtualTouchscreenConfig) parcel.readTypedObject(android.hardware.input.VirtualTouchscreenConfig.CREATOR);
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualTouchscreen(virtualTouchscreenConfig, readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.hardware.input.VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig = (android.hardware.input.VirtualNavigationTouchpadConfig) parcel.readTypedObject(android.hardware.input.VirtualNavigationTouchpadConfig.CREATOR);
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualNavigationTouchpad(virtualNavigationTouchpadConfig, readStrongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.hardware.input.VirtualStylusConfig virtualStylusConfig = (android.hardware.input.VirtualStylusConfig) parcel.readTypedObject(android.hardware.input.VirtualStylusConfig.CREATOR);
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualStylus(virtualStylusConfig, readStrongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterInputDevice(readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int inputDeviceId = getInputDeviceId(readStrongBinder8);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputDeviceId);
                    return true;
                case 20:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    android.hardware.input.VirtualKeyEvent virtualKeyEvent = (android.hardware.input.VirtualKeyEvent) parcel.readTypedObject(android.hardware.input.VirtualKeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendDpadKeyEvent = sendDpadKeyEvent(readStrongBinder9, virtualKeyEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendDpadKeyEvent);
                    return true;
                case 21:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    android.hardware.input.VirtualKeyEvent virtualKeyEvent2 = (android.hardware.input.VirtualKeyEvent) parcel.readTypedObject(android.hardware.input.VirtualKeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendKeyEvent = sendKeyEvent(readStrongBinder10, virtualKeyEvent2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendKeyEvent);
                    return true;
                case 22:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    android.hardware.input.VirtualMouseButtonEvent virtualMouseButtonEvent = (android.hardware.input.VirtualMouseButtonEvent) parcel.readTypedObject(android.hardware.input.VirtualMouseButtonEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendButtonEvent = sendButtonEvent(readStrongBinder11, virtualMouseButtonEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendButtonEvent);
                    return true;
                case 23:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    android.hardware.input.VirtualMouseRelativeEvent virtualMouseRelativeEvent = (android.hardware.input.VirtualMouseRelativeEvent) parcel.readTypedObject(android.hardware.input.VirtualMouseRelativeEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendRelativeEvent = sendRelativeEvent(readStrongBinder12, virtualMouseRelativeEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendRelativeEvent);
                    return true;
                case 24:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    android.hardware.input.VirtualMouseScrollEvent virtualMouseScrollEvent = (android.hardware.input.VirtualMouseScrollEvent) parcel.readTypedObject(android.hardware.input.VirtualMouseScrollEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendScrollEvent = sendScrollEvent(readStrongBinder13, virtualMouseScrollEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendScrollEvent);
                    return true;
                case 25:
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    android.hardware.input.VirtualTouchEvent virtualTouchEvent = (android.hardware.input.VirtualTouchEvent) parcel.readTypedObject(android.hardware.input.VirtualTouchEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendTouchEvent = sendTouchEvent(readStrongBinder14, virtualTouchEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendTouchEvent);
                    return true;
                case 26:
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    android.hardware.input.VirtualStylusMotionEvent virtualStylusMotionEvent = (android.hardware.input.VirtualStylusMotionEvent) parcel.readTypedObject(android.hardware.input.VirtualStylusMotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendStylusMotionEvent = sendStylusMotionEvent(readStrongBinder15, virtualStylusMotionEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendStylusMotionEvent);
                    return true;
                case 27:
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    android.hardware.input.VirtualStylusButtonEvent virtualStylusButtonEvent = (android.hardware.input.VirtualStylusButtonEvent) parcel.readTypedObject(android.hardware.input.VirtualStylusButtonEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendStylusButtonEvent = sendStylusButtonEvent(readStrongBinder16, virtualStylusButtonEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendStylusButtonEvent);
                    return true;
                case 28:
                    java.util.List<android.companion.virtual.sensor.VirtualSensor> virtualSensorList = getVirtualSensorList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(virtualSensorList, 1);
                    return true;
                case 29:
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent = (android.companion.virtual.sensor.VirtualSensorEvent) parcel.readTypedObject(android.companion.virtual.sensor.VirtualSensorEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendSensorEvent = sendSensorEvent(readStrongBinder17, virtualSensorEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendSensorEvent);
                    return true;
                case 30:
                    int readInt5 = parcel.readInt();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    launchPendingIntent(readInt5, pendingIntent, resultReceiver);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    android.graphics.PointF cursorPosition = getCursorPosition(readStrongBinder18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cursorPosition, 1);
                    return true;
                case 32:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowPointerIcon(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayImePolicy(readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    android.companion.virtual.IVirtualDeviceIntentInterceptor asInterface3 = android.companion.virtual.IVirtualDeviceIntentInterceptor.Stub.asInterface(parcel.readStrongBinder());
                    android.content.IntentFilter intentFilter = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerIntentInterceptor(asInterface3, intentFilter);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    android.companion.virtual.IVirtualDeviceIntentInterceptor asInterface4 = android.companion.virtual.IVirtualDeviceIntentInterceptor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterIntentInterceptor(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig = (android.companion.virtual.camera.VirtualCameraConfig) parcel.readTypedObject(android.companion.virtual.camera.VirtualCameraConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerVirtualCamera(virtualCameraConfig);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig2 = (android.companion.virtual.camera.VirtualCameraConfig) parcel.readTypedObject(android.companion.virtual.camera.VirtualCameraConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterVirtualCamera(virtualCameraConfig2);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig3 = (android.companion.virtual.camera.VirtualCameraConfig) parcel.readTypedObject(android.companion.virtual.camera.VirtualCameraConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    int virtualCameraId = getVirtualCameraId(virtualCameraConfig3);
                    parcel2.writeNoException();
                    parcel2.writeInt(virtualCameraId);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtual.IVirtualDevice {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtual.IVirtualDevice.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getAssociationId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getDeviceId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public java.lang.String getPersistentDeviceId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int[] getDisplayIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getDevicePolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDevicePolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void addActivityPolicyExemption(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void removeActivityPolicyExemption(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void onAudioSessionStarting(int i, android.companion.virtual.audio.IAudioRoutingCallback iAudioRoutingCallback, android.companion.virtual.audio.IAudioConfigChangedCallback iAudioConfigChangedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAudioRoutingCallback);
                    obtain.writeStrongInterface(iAudioConfigChangedCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void onAudioSessionEnded() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualDpad(android.hardware.input.VirtualDpadConfig virtualDpadConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualDpadConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualKeyboard(android.hardware.input.VirtualKeyboardConfig virtualKeyboardConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualKeyboardConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualMouse(android.hardware.input.VirtualMouseConfig virtualMouseConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualMouseConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualTouchscreen(android.hardware.input.VirtualTouchscreenConfig virtualTouchscreenConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualTouchscreenConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualNavigationTouchpad(android.hardware.input.VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualNavigationTouchpadConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualStylus(android.hardware.input.VirtualStylusConfig virtualStylusConfig, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualStylusConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterInputDevice(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getInputDeviceId(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendDpadKeyEvent(android.os.IBinder iBinder, android.hardware.input.VirtualKeyEvent virtualKeyEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualKeyEvent, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendKeyEvent(android.os.IBinder iBinder, android.hardware.input.VirtualKeyEvent virtualKeyEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualKeyEvent, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendButtonEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseButtonEvent virtualMouseButtonEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualMouseButtonEvent, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendRelativeEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseRelativeEvent virtualMouseRelativeEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualMouseRelativeEvent, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendScrollEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseScrollEvent virtualMouseScrollEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualMouseScrollEvent, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendTouchEvent(android.os.IBinder iBinder, android.hardware.input.VirtualTouchEvent virtualTouchEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualTouchEvent, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendStylusMotionEvent(android.os.IBinder iBinder, android.hardware.input.VirtualStylusMotionEvent virtualStylusMotionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualStylusMotionEvent, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendStylusButtonEvent(android.os.IBinder iBinder, android.hardware.input.VirtualStylusButtonEvent virtualStylusButtonEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualStylusButtonEvent, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public java.util.List<android.companion.virtual.sensor.VirtualSensor> getVirtualSensorList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.companion.virtual.sensor.VirtualSensor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendSensorEvent(android.os.IBinder iBinder, android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualSensorEvent, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void launchPendingIntent(int i, android.app.PendingIntent pendingIntent, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public android.graphics.PointF getCursorPosition(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.PointF) obtain2.readTypedObject(android.graphics.PointF.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setShowPointerIcon(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDisplayImePolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void registerIntentInterceptor(android.companion.virtual.IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor, android.content.IntentFilter intentFilter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDeviceIntentInterceptor);
                    obtain.writeTypedObject(intentFilter, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterIntentInterceptor(android.companion.virtual.IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDeviceIntentInterceptor);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void registerVirtualCamera(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualCameraConfig, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterVirtualCamera(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualCameraConfig, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getVirtualCameraId(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualCameraConfig, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void close_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void setDevicePolicy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void addActivityPolicyExemption_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void removeActivityPolicyExemption_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void onAudioSessionStarting_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void onAudioSessionEnded_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualDpad_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualKeyboard_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualMouse_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualTouchscreen_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualNavigationTouchpad_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualStylus_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void unregisterInputDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendDpadKeyEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendKeyEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendButtonEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendRelativeEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendScrollEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendTouchEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendStylusMotionEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendStylusButtonEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void getVirtualSensorList_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendSensorEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void setShowPointerIcon_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void setDisplayImePolicy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void registerIntentInterceptor_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void unregisterIntentInterceptor_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void registerVirtualCamera_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void unregisterVirtualCamera_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void getVirtualCameraId_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 37;
        }
    }
}
