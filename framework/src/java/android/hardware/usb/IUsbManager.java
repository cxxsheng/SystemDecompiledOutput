package android.hardware.usb;

/* loaded from: classes2.dex */
public interface IUsbManager extends android.os.IInterface {
    void addAccessoryPackagesToPreferenceDenied(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void addDevicePackagesToPreferenceDenied(android.hardware.usb.UsbDevice usbDevice, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void clearDefaults(java.lang.String str, int i) throws android.os.RemoteException;

    void enableContaminantDetection(java.lang.String str, boolean z) throws android.os.RemoteException;

    void enableLimitPowerTransfer(java.lang.String str, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException;

    boolean enableUsbData(java.lang.String str, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException;

    void enableUsbDataWhileDocked(java.lang.String str, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor getControlFd(long j) throws android.os.RemoteException;

    android.hardware.usb.UsbAccessory getCurrentAccessory() throws android.os.RemoteException;

    long getCurrentFunctions() throws android.os.RemoteException;

    int getCurrentUsbSpeed() throws android.os.RemoteException;

    void getDeviceList(android.os.Bundle bundle) throws android.os.RemoteException;

    int getGadgetHalVersion() throws android.os.RemoteException;

    android.hardware.usb.UsbPortStatus getPortStatus(java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.hardware.usb.ParcelableUsbPort> getPorts() throws android.os.RemoteException;

    long getScreenUnlockedFunctions() throws android.os.RemoteException;

    int getUsbHalVersion() throws android.os.RemoteException;

    void grantAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory, int i) throws android.os.RemoteException;

    void grantDevicePermission(android.hardware.usb.UsbDevice usbDevice, int i) throws android.os.RemoteException;

    boolean hasAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory) throws android.os.RemoteException;

    boolean hasAccessoryPermissionWithIdentity(android.hardware.usb.UsbAccessory usbAccessory, int i, int i2) throws android.os.RemoteException;

    boolean hasDefaults(java.lang.String str, int i) throws android.os.RemoteException;

    boolean hasDevicePermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str) throws android.os.RemoteException;

    boolean hasDevicePermissionWithIdentity(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i, int i2) throws android.os.RemoteException;

    boolean isFunctionEnabled(java.lang.String str) throws android.os.RemoteException;

    boolean isModeChangeSupported(java.lang.String str) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openAccessory(android.hardware.usb.UsbAccessory usbAccessory) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openDevice(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean registerForDisplayPortEvents(android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws android.os.RemoteException;

    void removeAccessoryPackagesFromPreferenceDenied(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void removeDevicePackagesFromPreferenceDenied(android.hardware.usb.UsbDevice usbDevice, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void requestAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void requestDevicePermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void resetUsbGadget() throws android.os.RemoteException;

    void resetUsbPort(java.lang.String str, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException;

    void setAccessoryPackage(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str, int i) throws android.os.RemoteException;

    void setAccessoryPersistentPermission(android.hardware.usb.UsbAccessory usbAccessory, int i, android.os.UserHandle userHandle, boolean z) throws android.os.RemoteException;

    void setCurrentFunction(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    void setCurrentFunctions(long j, int i) throws android.os.RemoteException;

    void setDevicePackage(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i) throws android.os.RemoteException;

    void setDevicePersistentPermission(android.hardware.usb.UsbDevice usbDevice, int i, android.os.UserHandle userHandle, boolean z) throws android.os.RemoteException;

    void setPortRoles(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void setScreenUnlockedFunctions(long j) throws android.os.RemoteException;

    void setUsbDeviceConnectionHandler(android.content.ComponentName componentName) throws android.os.RemoteException;

    void unregisterForDisplayPortEvents(android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws android.os.RemoteException;

    public static class Default implements android.hardware.usb.IUsbManager {
        @Override // android.hardware.usb.IUsbManager
        public void getDeviceList(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public android.os.ParcelFileDescriptor openDevice(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public android.hardware.usb.UsbAccessory getCurrentAccessory() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public android.os.ParcelFileDescriptor openAccessory(android.hardware.usb.UsbAccessory usbAccessory) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public void setDevicePackage(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setAccessoryPackage(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void addDevicePackagesToPreferenceDenied(android.hardware.usb.UsbDevice usbDevice, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void addAccessoryPackagesToPreferenceDenied(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void removeDevicePackagesFromPreferenceDenied(android.hardware.usb.UsbDevice usbDevice, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void removeAccessoryPackagesFromPreferenceDenied(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setDevicePersistentPermission(android.hardware.usb.UsbDevice usbDevice, int i, android.os.UserHandle userHandle, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setAccessoryPersistentPermission(android.hardware.usb.UsbAccessory usbAccessory, int i, android.os.UserHandle userHandle, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasDevicePermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasDevicePermissionWithIdentity(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasAccessoryPermissionWithIdentity(android.hardware.usb.UsbAccessory usbAccessory, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void requestDevicePermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void requestAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void grantDevicePermission(android.hardware.usb.UsbDevice usbDevice, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void grantAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasDefaults(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void clearDefaults(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isFunctionEnabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void setCurrentFunctions(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setCurrentFunction(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public long getCurrentFunctions() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.usb.IUsbManager
        public int getCurrentUsbSpeed() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public int getGadgetHalVersion() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public void setScreenUnlockedFunctions(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public long getScreenUnlockedFunctions() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.usb.IUsbManager
        public void resetUsbGadget() throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void resetUsbPort(java.lang.String str, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean enableUsbData(java.lang.String str, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void enableUsbDataWhileDocked(java.lang.String str, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public int getUsbHalVersion() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public android.os.ParcelFileDescriptor getControlFd(long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public java.util.List<android.hardware.usb.ParcelableUsbPort> getPorts() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public android.hardware.usb.UsbPortStatus getPortStatus(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isModeChangeSupported(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void setPortRoles(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void enableLimitPowerTransfer(java.lang.String str, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void enableContaminantDetection(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setUsbDeviceConnectionHandler(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean registerForDisplayPortEvents(android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void unregisterForDisplayPortEvents(android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.usb.IUsbManager {
        public static final java.lang.String DESCRIPTOR = "android.hardware.usb.IUsbManager";
        static final int TRANSACTION_addAccessoryPackagesToPreferenceDenied = 8;
        static final int TRANSACTION_addDevicePackagesToPreferenceDenied = 7;
        static final int TRANSACTION_clearDefaults = 22;
        static final int TRANSACTION_enableContaminantDetection = 42;
        static final int TRANSACTION_enableLimitPowerTransfer = 41;
        static final int TRANSACTION_enableUsbData = 33;
        static final int TRANSACTION_enableUsbDataWhileDocked = 34;
        static final int TRANSACTION_getControlFd = 36;
        static final int TRANSACTION_getCurrentAccessory = 3;
        static final int TRANSACTION_getCurrentFunctions = 26;
        static final int TRANSACTION_getCurrentUsbSpeed = 27;
        static final int TRANSACTION_getDeviceList = 1;
        static final int TRANSACTION_getGadgetHalVersion = 28;
        static final int TRANSACTION_getPortStatus = 38;
        static final int TRANSACTION_getPorts = 37;
        static final int TRANSACTION_getScreenUnlockedFunctions = 30;
        static final int TRANSACTION_getUsbHalVersion = 35;
        static final int TRANSACTION_grantAccessoryPermission = 20;
        static final int TRANSACTION_grantDevicePermission = 19;
        static final int TRANSACTION_hasAccessoryPermission = 15;
        static final int TRANSACTION_hasAccessoryPermissionWithIdentity = 16;
        static final int TRANSACTION_hasDefaults = 21;
        static final int TRANSACTION_hasDevicePermission = 13;
        static final int TRANSACTION_hasDevicePermissionWithIdentity = 14;
        static final int TRANSACTION_isFunctionEnabled = 23;
        static final int TRANSACTION_isModeChangeSupported = 39;
        static final int TRANSACTION_openAccessory = 4;
        static final int TRANSACTION_openDevice = 2;
        static final int TRANSACTION_registerForDisplayPortEvents = 44;
        static final int TRANSACTION_removeAccessoryPackagesFromPreferenceDenied = 10;
        static final int TRANSACTION_removeDevicePackagesFromPreferenceDenied = 9;
        static final int TRANSACTION_requestAccessoryPermission = 18;
        static final int TRANSACTION_requestDevicePermission = 17;
        static final int TRANSACTION_resetUsbGadget = 31;
        static final int TRANSACTION_resetUsbPort = 32;
        static final int TRANSACTION_setAccessoryPackage = 6;
        static final int TRANSACTION_setAccessoryPersistentPermission = 12;
        static final int TRANSACTION_setCurrentFunction = 25;
        static final int TRANSACTION_setCurrentFunctions = 24;
        static final int TRANSACTION_setDevicePackage = 5;
        static final int TRANSACTION_setDevicePersistentPermission = 11;
        static final int TRANSACTION_setPortRoles = 40;
        static final int TRANSACTION_setScreenUnlockedFunctions = 29;
        static final int TRANSACTION_setUsbDeviceConnectionHandler = 43;
        static final int TRANSACTION_unregisterForDisplayPortEvents = 45;
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

        public static android.hardware.usb.IUsbManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.usb.IUsbManager)) {
                return (android.hardware.usb.IUsbManager) queryLocalInterface;
            }
            return new android.hardware.usb.IUsbManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDeviceList";
                case 2:
                    return "openDevice";
                case 3:
                    return "getCurrentAccessory";
                case 4:
                    return "openAccessory";
                case 5:
                    return "setDevicePackage";
                case 6:
                    return "setAccessoryPackage";
                case 7:
                    return "addDevicePackagesToPreferenceDenied";
                case 8:
                    return "addAccessoryPackagesToPreferenceDenied";
                case 9:
                    return "removeDevicePackagesFromPreferenceDenied";
                case 10:
                    return "removeAccessoryPackagesFromPreferenceDenied";
                case 11:
                    return "setDevicePersistentPermission";
                case 12:
                    return "setAccessoryPersistentPermission";
                case 13:
                    return "hasDevicePermission";
                case 14:
                    return "hasDevicePermissionWithIdentity";
                case 15:
                    return "hasAccessoryPermission";
                case 16:
                    return "hasAccessoryPermissionWithIdentity";
                case 17:
                    return "requestDevicePermission";
                case 18:
                    return "requestAccessoryPermission";
                case 19:
                    return "grantDevicePermission";
                case 20:
                    return "grantAccessoryPermission";
                case 21:
                    return "hasDefaults";
                case 22:
                    return "clearDefaults";
                case 23:
                    return "isFunctionEnabled";
                case 24:
                    return "setCurrentFunctions";
                case 25:
                    return "setCurrentFunction";
                case 26:
                    return "getCurrentFunctions";
                case 27:
                    return "getCurrentUsbSpeed";
                case 28:
                    return "getGadgetHalVersion";
                case 29:
                    return "setScreenUnlockedFunctions";
                case 30:
                    return "getScreenUnlockedFunctions";
                case 31:
                    return "resetUsbGadget";
                case 32:
                    return "resetUsbPort";
                case 33:
                    return "enableUsbData";
                case 34:
                    return "enableUsbDataWhileDocked";
                case 35:
                    return "getUsbHalVersion";
                case 36:
                    return "getControlFd";
                case 37:
                    return "getPorts";
                case 38:
                    return "getPortStatus";
                case 39:
                    return "isModeChangeSupported";
                case 40:
                    return "setPortRoles";
                case 41:
                    return "enableLimitPowerTransfer";
                case 42:
                    return "enableContaminantDetection";
                case 43:
                    return "setUsbDeviceConnectionHandler";
                case 44:
                    return "registerForDisplayPortEvents";
                case 45:
                    return "unregisterForDisplayPortEvents";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.Bundle bundle = new android.os.Bundle();
                    parcel.enforceNoDataAvail();
                    getDeviceList(bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundle, 1);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openDevice = openDevice(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openDevice, 1);
                    return true;
                case 3:
                    android.hardware.usb.UsbAccessory currentAccessory = getCurrentAccessory();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentAccessory, 1);
                    return true;
                case 4:
                    android.hardware.usb.UsbAccessory usbAccessory = (android.hardware.usb.UsbAccessory) parcel.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openAccessory = openAccessory(usbAccessory);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openAccessory, 1);
                    return true;
                case 5:
                    android.hardware.usb.UsbDevice usbDevice = (android.hardware.usb.UsbDevice) parcel.readTypedObject(android.hardware.usb.UsbDevice.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDevicePackage(usbDevice, readString3, readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.hardware.usb.UsbAccessory usbAccessory2 = (android.hardware.usb.UsbAccessory) parcel.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                    java.lang.String readString4 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAccessoryPackage(usbAccessory2, readString4, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.hardware.usb.UsbDevice usbDevice2 = (android.hardware.usb.UsbDevice) parcel.readTypedObject(android.hardware.usb.UsbDevice.CREATOR);
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addDevicePackagesToPreferenceDenied(usbDevice2, createStringArray, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.hardware.usb.UsbAccessory usbAccessory3 = (android.hardware.usb.UsbAccessory) parcel.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccessoryPackagesToPreferenceDenied(usbAccessory3, createStringArray2, userHandle2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.hardware.usb.UsbDevice usbDevice3 = (android.hardware.usb.UsbDevice) parcel.readTypedObject(android.hardware.usb.UsbDevice.CREATOR);
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeDevicePackagesFromPreferenceDenied(usbDevice3, createStringArray3, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.usb.UsbAccessory usbAccessory4 = (android.hardware.usb.UsbAccessory) parcel.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    android.os.UserHandle userHandle4 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeAccessoryPackagesFromPreferenceDenied(usbAccessory4, createStringArray4, userHandle4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.hardware.usb.UsbDevice usbDevice4 = (android.hardware.usb.UsbDevice) parcel.readTypedObject(android.hardware.usb.UsbDevice.CREATOR);
                    int readInt3 = parcel.readInt();
                    android.os.UserHandle userHandle5 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDevicePersistentPermission(usbDevice4, readInt3, userHandle5, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.hardware.usb.UsbAccessory usbAccessory5 = (android.hardware.usb.UsbAccessory) parcel.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                    int readInt4 = parcel.readInt();
                    android.os.UserHandle userHandle6 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAccessoryPersistentPermission(usbAccessory5, readInt4, userHandle6, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.hardware.usb.UsbDevice usbDevice5 = (android.hardware.usb.UsbDevice) parcel.readTypedObject(android.hardware.usb.UsbDevice.CREATOR);
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasDevicePermission = hasDevicePermission(usbDevice5, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDevicePermission);
                    return true;
                case 14:
                    android.hardware.usb.UsbDevice usbDevice6 = (android.hardware.usb.UsbDevice) parcel.readTypedObject(android.hardware.usb.UsbDevice.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasDevicePermissionWithIdentity = hasDevicePermissionWithIdentity(usbDevice6, readString6, readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDevicePermissionWithIdentity);
                    return true;
                case 15:
                    android.hardware.usb.UsbAccessory usbAccessory6 = (android.hardware.usb.UsbAccessory) parcel.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasAccessoryPermission = hasAccessoryPermission(usbAccessory6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasAccessoryPermission);
                    return true;
                case 16:
                    android.hardware.usb.UsbAccessory usbAccessory7 = (android.hardware.usb.UsbAccessory) parcel.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasAccessoryPermissionWithIdentity = hasAccessoryPermissionWithIdentity(usbAccessory7, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasAccessoryPermissionWithIdentity);
                    return true;
                case 17:
                    android.hardware.usb.UsbDevice usbDevice7 = (android.hardware.usb.UsbDevice) parcel.readTypedObject(android.hardware.usb.UsbDevice.CREATOR);
                    java.lang.String readString7 = parcel.readString();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestDevicePermission(usbDevice7, readString7, pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.hardware.usb.UsbAccessory usbAccessory8 = (android.hardware.usb.UsbAccessory) parcel.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                    java.lang.String readString8 = parcel.readString();
                    android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestAccessoryPermission(usbAccessory8, readString8, pendingIntent2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.hardware.usb.UsbDevice usbDevice8 = (android.hardware.usb.UsbDevice) parcel.readTypedObject(android.hardware.usb.UsbDevice.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDevicePermission(usbDevice8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.hardware.usb.UsbAccessory usbAccessory9 = (android.hardware.usb.UsbAccessory) parcel.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantAccessoryPermission(usbAccessory9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    java.lang.String readString9 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasDefaults = hasDefaults(readString9, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDefaults);
                    return true;
                case 22:
                    java.lang.String readString10 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDefaults(readString10, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isFunctionEnabled = isFunctionEnabled(readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFunctionEnabled);
                    return true;
                case 24:
                    long readLong = parcel.readLong();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentFunctions(readLong, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String readString12 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentFunction(readString12, readBoolean3, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    long currentFunctions = getCurrentFunctions();
                    parcel2.writeNoException();
                    parcel2.writeLong(currentFunctions);
                    return true;
                case 27:
                    int currentUsbSpeed = getCurrentUsbSpeed();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentUsbSpeed);
                    return true;
                case 28:
                    int gadgetHalVersion = getGadgetHalVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(gadgetHalVersion);
                    return true;
                case 29:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setScreenUnlockedFunctions(readLong2);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    long screenUnlockedFunctions = getScreenUnlockedFunctions();
                    parcel2.writeNoException();
                    parcel2.writeLong(screenUnlockedFunctions);
                    return true;
                case 31:
                    resetUsbGadget();
                    parcel2.writeNoException();
                    return true;
                case 32:
                    java.lang.String readString13 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    android.hardware.usb.IUsbOperationInternal asInterface = android.hardware.usb.IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    resetUsbPort(readString13, readInt15, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    java.lang.String readString14 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt16 = parcel.readInt();
                    android.hardware.usb.IUsbOperationInternal asInterface2 = android.hardware.usb.IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean enableUsbData = enableUsbData(readString14, readBoolean4, readInt16, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableUsbData);
                    return true;
                case 34:
                    java.lang.String readString15 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    android.hardware.usb.IUsbOperationInternal asInterface3 = android.hardware.usb.IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableUsbDataWhileDocked(readString15, readInt17, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int usbHalVersion = getUsbHalVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbHalVersion);
                    return true;
                case 36:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor controlFd = getControlFd(readLong3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(controlFd, 1);
                    return true;
                case 37:
                    java.util.List<android.hardware.usb.ParcelableUsbPort> ports = getPorts();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(ports, 1);
                    return true;
                case 38:
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.usb.UsbPortStatus portStatus = getPortStatus(readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(portStatus, 1);
                    return true;
                case 39:
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isModeChangeSupported = isModeChangeSupported(readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isModeChangeSupported);
                    return true;
                case 40:
                    java.lang.String readString18 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPortRoles(readString18, readInt18, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    java.lang.String readString19 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt20 = parcel.readInt();
                    android.hardware.usb.IUsbOperationInternal asInterface4 = android.hardware.usb.IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableLimitPowerTransfer(readString19, readBoolean5, readInt20, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    java.lang.String readString20 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableContaminantDetection(readString20, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUsbDeviceConnectionHandler(componentName);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    android.hardware.usb.IDisplayPortAltModeInfoListener asInterface5 = android.hardware.usb.IDisplayPortAltModeInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerForDisplayPortEvents = registerForDisplayPortEvents(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerForDisplayPortEvents);
                    return true;
                case 45:
                    android.hardware.usb.IDisplayPortAltModeInfoListener asInterface6 = android.hardware.usb.IDisplayPortAltModeInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForDisplayPortEvents(asInterface6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.usb.IUsbManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.usb.IUsbManager.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IUsbManager
            public void getDeviceList(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public android.os.ParcelFileDescriptor openDevice(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public android.hardware.usb.UsbAccessory getCurrentAccessory() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.usb.UsbAccessory) obtain2.readTypedObject(android.hardware.usb.UsbAccessory.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public android.os.ParcelFileDescriptor openAccessory(android.hardware.usb.UsbAccessory usbAccessory) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setDevicePackage(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setAccessoryPackage(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void addDevicePackagesToPreferenceDenied(android.hardware.usb.UsbDevice usbDevice, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void addAccessoryPackagesToPreferenceDenied(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void removeDevicePackagesFromPreferenceDenied(android.hardware.usb.UsbDevice usbDevice, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void removeAccessoryPackagesFromPreferenceDenied(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String[] strArr, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setDevicePersistentPermission(android.hardware.usb.UsbDevice usbDevice, int i, android.os.UserHandle userHandle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setAccessoryPersistentPermission(android.hardware.usb.UsbAccessory usbAccessory, int i, android.os.UserHandle userHandle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDevicePermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDevicePermissionWithIdentity(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasAccessoryPermissionWithIdentity(android.hardware.usb.UsbAccessory usbAccessory, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void requestDevicePermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void requestAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void grantDevicePermission(android.hardware.usb.UsbDevice usbDevice, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void grantAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDefaults(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void clearDefaults(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isFunctionEnabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setCurrentFunctions(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setCurrentFunction(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public long getCurrentFunctions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getCurrentUsbSpeed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getGadgetHalVersion() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setScreenUnlockedFunctions(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public long getScreenUnlockedFunctions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void resetUsbGadget() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void resetUsbPort(java.lang.String str, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean enableUsbData(java.lang.String str, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableUsbDataWhileDocked(java.lang.String str, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getUsbHalVersion() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public android.os.ParcelFileDescriptor getControlFd(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public java.util.List<android.hardware.usb.ParcelableUsbPort> getPorts() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.usb.ParcelableUsbPort.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public android.hardware.usb.UsbPortStatus getPortStatus(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.usb.UsbPortStatus) obtain2.readTypedObject(android.hardware.usb.UsbPortStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isModeChangeSupported(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setPortRoles(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableLimitPowerTransfer(java.lang.String str, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableContaminantDetection(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setUsbDeviceConnectionHandler(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean registerForDisplayPortEvents(android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayPortAltModeInfoListener);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void unregisterForDisplayPortEvents(android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayPortAltModeInfoListener);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void hasDevicePermissionWithIdentity_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void hasAccessoryPermissionWithIdentity_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void grantDevicePermission_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void grantAccessoryPermission_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void setCurrentFunctions_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getCurrentFunctions_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getCurrentUsbSpeed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getGadgetHalVersion_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void setScreenUnlockedFunctions_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getScreenUnlockedFunctions_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void resetUsbGadget_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getUsbHalVersion_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getControlFd_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_MTP, getCallingPid(), getCallingUid());
        }

        protected void getPorts_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void isModeChangeSupported_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void setUsbDeviceConnectionHandler_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 44;
        }
    }
}
