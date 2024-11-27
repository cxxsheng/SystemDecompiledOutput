package android.hardware.usb;

/* loaded from: classes2.dex */
public class UsbManager {
    public static final java.lang.String ACTION_USB_ACCESSORY_ATTACHED = "android.hardware.usb.action.USB_ACCESSORY_ATTACHED";
    public static final java.lang.String ACTION_USB_ACCESSORY_DETACHED = "android.hardware.usb.action.USB_ACCESSORY_DETACHED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_USB_ACCESSORY_HANDSHAKE = "android.hardware.usb.action.USB_ACCESSORY_HANDSHAKE";
    public static final java.lang.String ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final java.lang.String ACTION_USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_USB_PORT_CHANGED = "android.hardware.usb.action.USB_PORT_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_USB_PORT_COMPLIANCE_CHANGED = "android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";
    public static final java.lang.String EXTRA_ACCESSORY = "accessory";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ACCESSORY_HANDSHAKE_END = "android.hardware.usb.extra.ACCESSORY_HANDSHAKE_END";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ACCESSORY_START = "android.hardware.usb.extra.ACCESSORY_START";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ACCESSORY_STRING_COUNT = "android.hardware.usb.extra.ACCESSORY_STRING_COUNT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ACCESSORY_UEVENT_TIME = "android.hardware.usb.extra.ACCESSORY_UEVENT_TIME";
    public static final java.lang.String EXTRA_CAN_BE_DEFAULT = "android.hardware.usb.extra.CAN_BE_DEFAULT";
    public static final java.lang.String EXTRA_DEVICE = "device";
    public static final java.lang.String EXTRA_PACKAGE = "android.hardware.usb.extra.PACKAGE";
    public static final java.lang.String EXTRA_PERMISSION_GRANTED = "permission";
    public static final java.lang.String EXTRA_PORT = "port";
    public static final java.lang.String EXTRA_PORT_STATUS = "portStatus";

    @android.annotation.SystemApi
    public static final long FUNCTION_ACCESSORY = 2;

    @android.annotation.SystemApi
    public static final long FUNCTION_ADB = 1;

    @android.annotation.SystemApi
    public static final long FUNCTION_AUDIO_SOURCE = 64;

    @android.annotation.SystemApi
    public static final long FUNCTION_MIDI = 8;

    @android.annotation.SystemApi
    public static final long FUNCTION_MTP = 4;

    @android.annotation.SystemApi
    public static final long FUNCTION_NCM = 1024;

    @android.annotation.SystemApi
    public static final long FUNCTION_NONE = 0;

    @android.annotation.SystemApi
    public static final long FUNCTION_PTP = 16;

    @android.annotation.SystemApi
    public static final long FUNCTION_RNDIS = 32;

    @android.annotation.SystemApi
    public static final long FUNCTION_UVC = 128;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_NOT_SUPPORTED = -1;
    public static final java.lang.String GADGET_HAL_UNKNOWN = "unknown";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_V1_0 = 10;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_V1_1 = 11;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_V1_2 = 12;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int GADGET_HAL_V2_0 = 20;
    public static final java.lang.String GADGET_HAL_VERSION_1_0 = "V1_0";
    public static final java.lang.String GADGET_HAL_VERSION_1_1 = "V1_1";
    public static final java.lang.String GADGET_HAL_VERSION_1_2 = "V1_2";
    public static final java.lang.String GADGET_HAL_VERSION_2_0 = "V2_0";
    private static final long SETTABLE_FUNCTIONS = 1212;
    private static final java.lang.String TAG = "UsbManager";

    @android.annotation.SystemApi
    public static final java.lang.String USB_CONFIGURED = "configured";

    @android.annotation.SystemApi
    public static final java.lang.String USB_CONNECTED = "connected";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_10G = 10240;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_20G = 20480;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_40G = 40960;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_5G = 5120;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_FULL_SPEED = 12;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_HIGH_SPEED = 480;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_LOW_SPEED = 2;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_DATA_TRANSFER_RATE_UNKNOWN = -1;
    public static final java.lang.String USB_DATA_UNLOCKED = "unlocked";
    public static final java.lang.String USB_FUNCTION_ACCESSORY = "accessory";
    public static final java.lang.String USB_FUNCTION_ADB = "adb";
    public static final java.lang.String USB_FUNCTION_AUDIO_SOURCE = "audio_source";
    public static final java.lang.String USB_FUNCTION_MIDI = "midi";
    public static final java.lang.String USB_FUNCTION_MTP = "mtp";

    @android.annotation.SystemApi
    public static final java.lang.String USB_FUNCTION_NCM = "ncm";
    public static final java.lang.String USB_FUNCTION_NONE = "none";
    public static final java.lang.String USB_FUNCTION_PTP = "ptp";

    @android.annotation.SystemApi
    public static final java.lang.String USB_FUNCTION_RNDIS = "rndis";
    public static final java.lang.String USB_FUNCTION_UVC = "uvc";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_NOT_SUPPORTED = -1;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_RETRY = -2;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V1_0 = 10;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V1_1 = 11;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V1_2 = 12;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V1_3 = 13;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int USB_HAL_V2_0 = 20;
    public static final java.lang.String USB_HOST_CONNECTED = "host_connected";
    private final android.content.Context mContext;
    private android.util.ArrayMap<android.hardware.usb.UsbManager.DisplayPortAltModeInfoListener, java.util.concurrent.Executor> mDisplayPortListeners;
    private final java.lang.Object mDisplayPortListenersLock = new java.lang.Object();
    private android.hardware.usb.UsbManager.DisplayPortAltModeInfoDispatchingListener mDisplayPortServiceListener;
    private final android.hardware.usb.IUsbManager mService;
    private static final java.util.Map<java.lang.String, java.lang.Long> FUNCTION_NAME_TO_CODE = new java.util.HashMap();
    private static final java.util.concurrent.atomic.AtomicInteger sUsbOperationCount = new java.util.concurrent.atomic.AtomicInteger();

    @android.annotation.SystemApi
    public interface DisplayPortAltModeInfoListener {
        void onDisplayPortAltModeInfoChanged(java.lang.String str, android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UsbFunctionMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UsbGadgetHalVersion {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UsbHalVersion {
    }

    static {
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_MTP, 4L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_PTP, 16L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_RNDIS, 32L);
        FUNCTION_NAME_TO_CODE.put("midi", 8L);
        FUNCTION_NAME_TO_CODE.put("accessory", 2L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_AUDIO_SOURCE, 64L);
        FUNCTION_NAME_TO_CODE.put("adb", 1L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_NCM, 1024L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_UVC, 128L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DisplayPortAltModeInfoDispatchingListener extends android.hardware.usb.IDisplayPortAltModeInfoListener.Stub {
        private DisplayPortAltModeInfoDispatchingListener() {
        }

        @Override // android.hardware.usb.IDisplayPortAltModeInfoListener
        public void onDisplayPortAltModeInfoChanged(final java.lang.String str, final android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) {
            synchronized (android.hardware.usb.UsbManager.this.mDisplayPortListenersLock) {
                for (java.util.Map.Entry entry : android.hardware.usb.UsbManager.this.mDisplayPortListeners.entrySet()) {
                    java.util.concurrent.Executor executor = (java.util.concurrent.Executor) entry.getValue();
                    final android.hardware.usb.UsbManager.DisplayPortAltModeInfoListener displayPortAltModeInfoListener = (android.hardware.usb.UsbManager.DisplayPortAltModeInfoListener) entry.getKey();
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        try {
                            executor.execute(new java.lang.Runnable() { // from class: android.hardware.usb.UsbManager$DisplayPortAltModeInfoDispatchingListener$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.hardware.usb.UsbManager.DisplayPortAltModeInfoListener.this.onDisplayPortAltModeInfoChanged(str, displayPortAltModeInfo);
                                }
                            });
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(android.hardware.usb.UsbManager.TAG, "Exception during onDisplayPortAltModeInfoChanged from executor: " + executor, e);
                        }
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }
    }

    public UsbManager(android.content.Context context, android.hardware.usb.IUsbManager iUsbManager) {
        this.mContext = context;
        this.mService = iUsbManager;
    }

    public java.util.HashMap<java.lang.String, android.hardware.usb.UsbDevice> getDeviceList() {
        java.util.HashMap<java.lang.String, android.hardware.usb.UsbDevice> hashMap = new java.util.HashMap<>();
        if (this.mService == null) {
            return hashMap;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        try {
            this.mService.getDeviceList(bundle);
            for (java.lang.String str : bundle.keySet()) {
                hashMap.put(str, (android.hardware.usb.UsbDevice) bundle.get(str));
            }
            return hashMap;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.usb.UsbDeviceConnection openDevice(android.hardware.usb.UsbDevice usbDevice) {
        try {
            java.lang.String deviceName = usbDevice.getDeviceName();
            android.os.ParcelFileDescriptor openDevice = this.mService.openDevice(deviceName, this.mContext.getPackageName());
            if (openDevice != null) {
                android.hardware.usb.UsbDeviceConnection usbDeviceConnection = new android.hardware.usb.UsbDeviceConnection(usbDevice);
                boolean open = usbDeviceConnection.open(deviceName, openDevice, this.mContext);
                openDevice.close();
                if (open) {
                    return usbDeviceConnection;
                }
                return null;
            }
            return null;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "exception in UsbManager.openDevice", e);
            return null;
        }
    }

    public android.hardware.usb.UsbAccessory[] getAccessoryList() {
        if (this.mService == null) {
            return null;
        }
        try {
            android.hardware.usb.UsbAccessory currentAccessory = this.mService.getCurrentAccessory();
            if (currentAccessory == null) {
                return null;
            }
            return new android.hardware.usb.UsbAccessory[]{currentAccessory};
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.ParcelFileDescriptor openAccessory(android.hardware.usb.UsbAccessory usbAccessory) {
        try {
            return this.mService.openAccessory(usbAccessory);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.ParcelFileDescriptor getControlFd(long j) {
        try {
            return this.mService.getControlFd(j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(android.hardware.usb.UsbDevice usbDevice) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasDevicePermission(usbDevice, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i, int i2) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasDevicePermissionWithIdentity(usbDevice, str, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(android.hardware.usb.UsbAccessory usbAccessory) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasAccessoryPermission(usbAccessory);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(android.hardware.usb.UsbAccessory usbAccessory, int i, int i2) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasAccessoryPermissionWithIdentity(usbAccessory, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPermission(android.hardware.usb.UsbDevice usbDevice, android.app.PendingIntent pendingIntent) {
        try {
            this.mService.requestDevicePermission(usbDevice, this.mContext.getPackageName(), pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPermission(android.hardware.usb.UsbAccessory usbAccessory, android.app.PendingIntent pendingIntent) {
        try {
            this.mService.requestAccessoryPermission(usbAccessory, this.mContext.getPackageName(), pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantPermission(android.hardware.usb.UsbDevice usbDevice) {
        grantPermission(usbDevice, android.os.Process.myUid());
    }

    public void grantPermission(android.hardware.usb.UsbDevice usbDevice, int i) {
        try {
            this.mService.grantDevicePermission(usbDevice, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void grantPermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str) {
        try {
            grantPermission(usbDevice, this.mContext.getPackageManager().getPackageUidAsUser(str, this.mContext.getUserId()));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Package " + str + " not found.", e);
        }
    }

    @java.lang.Deprecated
    public boolean isFunctionEnabled(java.lang.String str) {
        try {
            return this.mService.isFunctionEnabled(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setCurrentFunctions(long j) {
        int incrementAndGet = sUsbOperationCount.incrementAndGet() + android.os.Binder.getCallingUid();
        try {
            this.mService.setCurrentFunctions(j, incrementAndGet);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "setCurrentFunctions: failed to call setCurrentFunctions. functions:" + j + ", opId:" + incrementAndGet, e);
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setCurrentFunction(java.lang.String str, boolean z) {
        int incrementAndGet = sUsbOperationCount.incrementAndGet() + android.os.Binder.getCallingUid();
        try {
            this.mService.setCurrentFunction(str, z, incrementAndGet);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "setCurrentFunction: failed to call setCurrentFunction. functions:" + str + ", opId:" + incrementAndGet, e);
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public long getCurrentFunctions() {
        try {
            return this.mService.getCurrentFunctions();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setScreenUnlockedFunctions(long j) {
        try {
            this.mService.setScreenUnlockedFunctions(j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getScreenUnlockedFunctions() {
        try {
            return this.mService.getScreenUnlockedFunctions();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public int getUsbBandwidthMbps() {
        try {
            return usbSpeedToBandwidth(this.mService.getCurrentUsbSpeed());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public int getGadgetHalVersion() {
        try {
            return this.mService.getGadgetHalVersion();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public int getUsbHalVersion() {
        try {
            return this.mService.getUsbHalVersion();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void resetUsbGadget() {
        try {
            this.mService.resetUsbGadget();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static boolean isUvcSupportEnabled() {
        return android.os.SystemProperties.getBoolean("ro.usb.uvc.enabled", false);
    }

    public boolean enableUsbDataSignal(boolean z) {
        return setUsbDataSignal(getPorts(), !z, true);
    }

    private boolean setUsbDataSignal(java.util.List<android.hardware.usb.UsbPort> list, boolean z, boolean z2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < list.size(); i++) {
            android.hardware.usb.UsbPort usbPort = list.get(i);
            if (isPortDisabled(usbPort) != z) {
                arrayList.add(usbPort);
                if (usbPort.enableUsbData(!z) != 0 && z2) {
                    android.util.Log.e(TAG, "Failed to set usb data signal for portID(" + usbPort.getId() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    setUsbDataSignal(arrayList, !z, false);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPortDisabled(android.hardware.usb.UsbPort usbPort) {
        return (getPortStatus(usbPort).getUsbDataStatus() & 16) == 16;
    }

    @android.annotation.SystemApi
    public java.util.List<android.hardware.usb.UsbPort> getPorts() {
        if (this.mService == null) {
            return java.util.Collections.emptyList();
        }
        try {
            java.util.List<android.hardware.usb.ParcelableUsbPort> ports = this.mService.getPorts();
            if (ports == null) {
                return java.util.Collections.emptyList();
            }
            int size = ports.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(ports.get(i).getUsbPort(this));
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.hardware.usb.UsbPortStatus getPortStatus(android.hardware.usb.UsbPort usbPort) {
        try {
            return this.mService.getPortStatus(usbPort.getId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean isModeChangeSupported(android.hardware.usb.UsbPort usbPort) {
        try {
            return this.mService.isModeChangeSupported(usbPort.getId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setPortRoles(android.hardware.usb.UsbPort usbPort, int i, int i2) {
        android.util.Log.d(TAG, "setPortRoles Package:" + this.mContext.getPackageName());
        try {
            this.mService.setPortRoles(usbPort.getId(), i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void enableContaminantDetection(android.hardware.usb.UsbPort usbPort, boolean z) {
        try {
            this.mService.enableContaminantDetection(usbPort.getId(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void enableLimitPowerTransfer(android.hardware.usb.UsbPort usbPort, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(usbPort, "enableLimitPowerTransfer:port must not be null. opId:" + i);
        try {
            this.mService.enableLimitPowerTransfer(usbPort.getId(), z, i, iUsbOperationInternal);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "enableLimitPowerTransfer failed. opId:" + i, e);
            try {
                iUsbOperationInternal.onOperationComplete(1);
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(TAG, "enableLimitPowerTransfer failed to call onOperationComplete. opId:" + i, e2);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    void resetUsbPort(android.hardware.usb.UsbPort usbPort, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(usbPort, "resetUsbPort: port must not be null. opId:" + i);
        try {
            this.mService.resetUsbPort(usbPort.getId(), i, iUsbOperationInternal);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "resetUsbPort: failed. ", e);
            try {
                iUsbOperationInternal.onOperationComplete(1);
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(TAG, "resetUsbPort: failed to call onOperationComplete. opId:" + i, e2);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    boolean enableUsbData(android.hardware.usb.UsbPort usbPort, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(usbPort, "enableUsbData: port must not be null. opId:" + i);
        try {
            return this.mService.enableUsbData(usbPort.getId(), z, i, iUsbOperationInternal);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "enableUsbData: failed. opId:" + i, e);
            try {
                iUsbOperationInternal.onOperationComplete(1);
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(TAG, "enableUsbData: failed to call onOperationComplete. opId:" + i, e2);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    void enableUsbDataWhileDocked(android.hardware.usb.UsbPort usbPort, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(usbPort, "enableUsbDataWhileDocked: port must not be null. opId:" + i);
        try {
            this.mService.enableUsbDataWhileDocked(usbPort.getId(), i, iUsbOperationInternal);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "enableUsbDataWhileDocked: failed. opId:" + i, e);
            try {
                iUsbOperationInternal.onOperationComplete(1);
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(TAG, "enableUsbDataWhileDocked: failed to call onOperationComplete. opId:" + i, e2);
            }
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean registerDisplayPortAltModeEventsIfNeededLocked() {
        android.hardware.usb.UsbManager.DisplayPortAltModeInfoDispatchingListener displayPortAltModeInfoDispatchingListener = new android.hardware.usb.UsbManager.DisplayPortAltModeInfoDispatchingListener();
        try {
            if (this.mService.registerForDisplayPortEvents(displayPortAltModeInfoDispatchingListener)) {
                this.mDisplayPortServiceListener = displayPortAltModeInfoDispatchingListener;
                return true;
            }
            return false;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void registerDisplayPortAltModeInfoListener(java.util.concurrent.Executor executor, android.hardware.usb.UsbManager.DisplayPortAltModeInfoListener displayPortAltModeInfoListener) {
        java.util.Objects.requireNonNull(executor, "registerDisplayPortAltModeInfoListener: executor must not be null.");
        java.util.Objects.requireNonNull(displayPortAltModeInfoListener, "registerDisplayPortAltModeInfoListener: listener must not be null.");
        synchronized (this.mDisplayPortListenersLock) {
            if (this.mDisplayPortListeners == null) {
                this.mDisplayPortListeners = new android.util.ArrayMap<>();
            }
            if (this.mDisplayPortServiceListener == null && !registerDisplayPortAltModeEventsIfNeededLocked()) {
                throw new java.lang.IllegalStateException("Unexpected failure registering service listener");
            }
            if (this.mDisplayPortListeners.containsKey(displayPortAltModeInfoListener)) {
                throw new java.lang.IllegalStateException("Listener has already been registered.");
            }
            this.mDisplayPortListeners.put(displayPortAltModeInfoListener, executor);
        }
    }

    private void unregisterDisplayPortAltModeEventsLocked() {
        if (this.mDisplayPortServiceListener != null) {
            try {
                try {
                    this.mService.unregisterForDisplayPortEvents(this.mDisplayPortServiceListener);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                this.mDisplayPortServiceListener = null;
            }
        }
    }

    @android.annotation.SystemApi
    public void unregisterDisplayPortAltModeInfoListener(android.hardware.usb.UsbManager.DisplayPortAltModeInfoListener displayPortAltModeInfoListener) {
        synchronized (this.mDisplayPortListenersLock) {
            if (this.mDisplayPortListeners == null) {
                return;
            }
            this.mDisplayPortListeners.remove(displayPortAltModeInfoListener);
            if (this.mDisplayPortListeners.isEmpty()) {
                unregisterDisplayPortAltModeEventsLocked();
            }
        }
    }

    public void setUsbDeviceConnectionHandler(android.content.ComponentName componentName) {
        try {
            this.mService.setUsbDeviceConnectionHandler(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean areSettableFunctions(long j) {
        if (j != 0) {
            return ((-1213) & j) == 0 && (java.lang.Long.bitCount(j) == 1 || j == 1056);
        }
        return true;
    }

    public static java.lang.String usbFunctionsToString(long j) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
        if ((4 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_MTP);
        }
        if ((16 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_PTP);
        }
        if ((32 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_RNDIS);
        }
        if ((8 & j) != 0) {
            stringJoiner.add("midi");
        }
        if ((2 & j) != 0) {
            stringJoiner.add("accessory");
        }
        if ((64 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_AUDIO_SOURCE);
        }
        if ((1024 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_NCM);
        }
        if ((128 & j) != 0) {
            stringJoiner.add(USB_FUNCTION_UVC);
        }
        if ((j & 1) != 0) {
            stringJoiner.add("adb");
        }
        return stringJoiner.toString();
    }

    public static long usbFunctionsFromString(java.lang.String str) {
        long j = 0;
        if (str == null || str.equals("none")) {
            return 0L;
        }
        for (java.lang.String str2 : str.split(",")) {
            if (FUNCTION_NAME_TO_CODE.containsKey(str2)) {
                j |= FUNCTION_NAME_TO_CODE.get(str2).longValue();
            } else if (str2.length() > 0) {
                throw new java.lang.IllegalArgumentException("Invalid usb function " + str);
            }
        }
        return j;
    }

    public static int usbSpeedToBandwidth(int i) {
        switch (i) {
        }
        return 20480;
    }

    public static java.lang.String usbGadgetHalVersionToString(int i) {
        if (i == 20) {
            return GADGET_HAL_VERSION_2_0;
        }
        if (i == 12) {
            return GADGET_HAL_VERSION_1_2;
        }
        if (i == 11) {
            return GADGET_HAL_VERSION_1_1;
        }
        if (i == 10) {
            return GADGET_HAL_VERSION_1_0;
        }
        return "unknown";
    }
}
