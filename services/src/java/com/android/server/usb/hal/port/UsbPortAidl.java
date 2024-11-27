package com.android.server.usb.hal.port;

/* loaded from: classes2.dex */
public final class UsbPortAidl implements com.android.server.usb.hal.port.UsbPortHal {
    public static final int AIDL_USB_DATA_STATUS_DISABLED_CONTAMINANT = 3;
    public static final int AIDL_USB_DATA_STATUS_DISABLED_DEBUG = 6;
    public static final int AIDL_USB_DATA_STATUS_DISABLED_DOCK = 4;
    public static final int AIDL_USB_DATA_STATUS_DISABLED_DOCK_DEVICE_MODE = 8;
    public static final int AIDL_USB_DATA_STATUS_DISABLED_DOCK_HOST_MODE = 7;
    public static final int AIDL_USB_DATA_STATUS_DISABLED_FORCE = 5;
    public static final int AIDL_USB_DATA_STATUS_DISABLED_OVERHEAT = 2;
    public static final int AIDL_USB_DATA_STATUS_ENABLED = 1;
    public static final int AIDL_USB_DATA_STATUS_UNKNOWN = 0;
    private static final java.lang.String USB_AIDL_SERVICE = "android.hardware.usb.IUsb/default";
    private android.os.IBinder mBinder;
    private com.android.server.usb.hal.port.UsbPortAidl.HALCallback mHALCallback;
    private final java.lang.Object mLock = new java.lang.Object();
    private com.android.server.usb.UsbPortManager mPortManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.usb.IUsb mProxy;
    public com.android.internal.util.IndentingPrintWriter mPw;
    private boolean mSystemReady;
    private long mTransactionId;
    private static final java.lang.String TAG = com.android.server.usb.hal.port.UsbPortAidl.class.getSimpleName();
    private static final android.util.LongSparseArray<android.hardware.usb.IUsbOperationInternal> sCallbacks = new android.util.LongSparseArray<>();

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public int getUsbHalVersion() throws android.os.RemoteException {
        synchronized (this.mLock) {
            if (this.mProxy == null) {
                throw new android.os.RemoteException("IUsb not initialized yet");
            }
        }
        com.android.server.usb.UsbPortManager.logAndPrint(4, null, "USB HAL AIDL version: USB_HAL_V2_0");
        return 20;
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void systemReady() {
        this.mSystemReady = true;
    }

    public void serviceDied() {
        com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Usb AIDL hal service died");
        synchronized (this.mLock) {
            this.mProxy = null;
        }
        connectToProxy(null);
    }

    private void connectToProxy(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            if (this.mProxy != null) {
                return;
            }
            try {
                this.mBinder = android.os.ServiceManager.waitForService(USB_AIDL_SERVICE);
                this.mProxy = android.hardware.usb.IUsb.Stub.asInterface(this.mBinder);
                this.mBinder.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.usb.hal.port.UsbPortAidl$$ExternalSyntheticLambda0
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        com.android.server.usb.hal.port.UsbPortAidl.this.serviceDied();
                    }
                }, 0);
                this.mProxy.setCallback(this.mHALCallback);
                android.hardware.usb.IUsb iUsb = this.mProxy;
                long j = this.mTransactionId + 1;
                this.mTransactionId = j;
                iUsb.queryPortStatus(j);
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb hal service not responding", e);
            } catch (java.util.NoSuchElementException e2) {
                com.android.server.usb.UsbPortManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb hal service not found. Did the service fail to start?", e2);
            }
        }
    }

    static boolean isServicePresent(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        try {
            return android.os.ServiceManager.isDeclared(USB_AIDL_SERVICE);
        } catch (java.util.NoSuchElementException e) {
            com.android.server.usb.UsbPortManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb Aidl hal service not found.", e);
            return false;
        }
    }

    public UsbPortAidl(com.android.server.usb.UsbPortManager usbPortManager, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.util.Objects.requireNonNull(usbPortManager);
        this.mPortManager = usbPortManager;
        this.mPw = indentingPrintWriter;
        this.mHALCallback = new com.android.server.usb.hal.port.UsbPortAidl.HALCallback(null, this.mPortManager, this);
        connectToProxy(this.mPw);
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void enableContaminantPresenceDetection(java.lang.String str, boolean z, long j) {
        synchronized (this.mLock) {
            if (this.mProxy == null) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID: " + j);
                return;
            }
            try {
                this.mProxy.enableContaminantPresenceDetection(str, z, j);
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to set contaminant detection. opID:" + j, e);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void queryPortStatus(long j) {
        synchronized (this.mLock) {
            if (this.mProxy == null) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID:" + j);
                return;
            }
            try {
                this.mProxy.queryPortStatus(j);
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(null, "ServiceStart: Failed to query port status. opID:" + j, e);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void switchMode(java.lang.String str, int i, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID:" + j);
                    return;
                }
                android.hardware.usb.PortRole portRole = new android.hardware.usb.PortRole();
                portRole.setMode((byte) i);
                try {
                    this.mProxy.switchRole(str, portRole, j);
                } catch (android.os.RemoteException e) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to set the USB port mode: portId=" + str + ", newMode=" + android.hardware.usb.UsbPort.modeToString(i) + "opID:" + j, e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void switchPowerRole(java.lang.String str, int i, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID:" + j);
                    return;
                }
                android.hardware.usb.PortRole portRole = new android.hardware.usb.PortRole();
                portRole.setPowerRole((byte) i);
                try {
                    this.mProxy.switchRole(str, portRole, j);
                } catch (android.os.RemoteException e) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to set the USB power role: portId=" + str + ", newPowerRole=" + android.hardware.usb.UsbPort.powerRoleToString(i) + "opID:" + j, e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void switchDataRole(java.lang.String str, int i, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID:" + j);
                    return;
                }
                android.hardware.usb.PortRole portRole = new android.hardware.usb.PortRole();
                portRole.setDataRole((byte) i);
                try {
                    this.mProxy.switchRole(str, portRole, j);
                } catch (android.os.RemoteException e) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to set the USB data role: portId=" + str + ", newDataRole=" + android.hardware.usb.UsbPort.dataRoleToString(i) + "opID:" + j, e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void resetUsbPort(java.lang.String str, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        long j2;
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(iUsbOperationInternal);
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "resetUsbPort: Proxy is null. Retry !opID:" + j);
                    iUsbOperationInternal.onOperationComplete(1);
                }
                j2 = j;
                while (sCallbacks.get(j2) != null) {
                    try {
                        j2 = java.util.concurrent.ThreadLocalRandom.current().nextInt();
                    } catch (android.os.RemoteException e) {
                        e = e;
                        com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "resetUsbPort: Failed to call onOperationComplete portID=" + str + "opID:" + j, e);
                        sCallbacks.remove(j2);
                    }
                }
                if (j2 != j) {
                    com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "resetUsbPort: operationID exists ! opID:" + j + " key:" + j2);
                }
                try {
                    sCallbacks.put(j2, iUsbOperationInternal);
                    this.mProxy.resetUsbPort(str, j2);
                } catch (android.os.RemoteException e2) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "resetUsbPort: Failed to resetUsbPort: portID=" + str + "opId:" + j, e2);
                    iUsbOperationInternal.onOperationComplete(1);
                    sCallbacks.remove(j2);
                }
            } catch (android.os.RemoteException e3) {
                e = e3;
                j2 = j;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public boolean enableUsbData(java.lang.String str, boolean z, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        long j2;
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(iUsbOperationInternal);
        synchronized (this.mLock) {
            try {
                if (this.mProxy != null) {
                    j2 = j;
                    while (sCallbacks.get(j2) != null) {
                        try {
                            j2 = java.util.concurrent.ThreadLocalRandom.current().nextInt();
                        } catch (android.os.RemoteException e) {
                            e = e;
                            com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "enableUsbData: Failed to call onOperationComplete portID=" + str + "opID:" + j, e);
                            sCallbacks.remove(j2);
                            return false;
                        }
                    }
                    if (j2 != j) {
                        com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "enableUsbData: operationID exists ! opID:" + j + " key:" + j2);
                    }
                    try {
                        sCallbacks.put(j2, iUsbOperationInternal);
                        this.mProxy.enableUsbData(str, z, j2);
                        return true;
                    } catch (android.os.RemoteException e2) {
                        com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "enableUsbData: Failed to invoke enableUsbData: portID=" + str + "opID:" + j, e2);
                        iUsbOperationInternal.onOperationComplete(1);
                        sCallbacks.remove(j2);
                        return false;
                    }
                }
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "enableUsbData: Proxy is null. Retry !opID:" + j);
                iUsbOperationInternal.onOperationComplete(1);
                return false;
            } catch (android.os.RemoteException e3) {
                e = e3;
                j2 = j;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void enableLimitPowerTransfer(java.lang.String str, boolean z, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(str);
        synchronized (this.mLock) {
            try {
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "enableLimitPowerTransfer: Failed to call onOperationComplete portID=" + str + " opID:" + j, e);
            }
            if (this.mProxy != null) {
                long j2 = j;
                while (sCallbacks.get(j2) != null) {
                    j2 = java.util.concurrent.ThreadLocalRandom.current().nextInt();
                }
                if (j2 != j) {
                    com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "enableUsbData: operationID exists ! opID:" + j + " key:" + j2);
                }
                try {
                    sCallbacks.put(j2, iUsbOperationInternal);
                    this.mProxy.limitPowerTransfer(str, z, j2);
                } catch (android.os.RemoteException e2) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "enableLimitPowerTransfer: Failed while invoking AIDL HAL portID=" + str + " opID:" + j, e2);
                    if (iUsbOperationInternal != null) {
                        iUsbOperationInternal.onOperationComplete(1);
                    }
                    sCallbacks.remove(j2);
                }
                return;
            }
            com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "enableLimitPowerTransfer: Proxy is null. Retry !opID:" + j);
            iUsbOperationInternal.onOperationComplete(1);
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void enableUsbDataWhileDocked(java.lang.String str, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(str);
        synchronized (this.mLock) {
            try {
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "enableUsbDataWhileDocked: Failed to call onOperationComplete portID=" + str + " opID:" + j, e);
            }
            if (this.mProxy != null) {
                long j2 = j;
                while (sCallbacks.get(j2) != null) {
                    j2 = java.util.concurrent.ThreadLocalRandom.current().nextInt();
                }
                if (j2 != j) {
                    com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "enableUsbDataWhileDocked: operationID exists ! opID:" + j + " key:" + j2);
                }
                try {
                    sCallbacks.put(j2, iUsbOperationInternal);
                    this.mProxy.enableUsbDataWhileDocked(str, j2);
                } catch (android.os.RemoteException e2) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "enableUsbDataWhileDocked: error while invoking halportID=" + str + " opID:" + j, e2);
                    if (iUsbOperationInternal != null) {
                        iUsbOperationInternal.onOperationComplete(1);
                    }
                    sCallbacks.remove(j2);
                }
                return;
            }
            com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "enableUsbDataWhileDocked: Proxy is null. Retry !opID:" + j);
            iUsbOperationInternal.onOperationComplete(1);
        }
    }

    private static class HALCallback extends android.hardware.usb.IUsbCallback.Stub {
        public com.android.server.usb.UsbPortManager mPortManager;
        public com.android.internal.util.IndentingPrintWriter mPw;
        public com.android.server.usb.hal.port.UsbPortAidl mUsbPortAidl;

        HALCallback(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, com.android.server.usb.UsbPortManager usbPortManager, com.android.server.usb.hal.port.UsbPortAidl usbPortAidl) {
            this.mPw = indentingPrintWriter;
            this.mPortManager = usbPortManager;
            this.mUsbPortAidl = usbPortAidl;
        }

        private int toPortMode(byte b) {
            switch (b) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Unrecognized aidlPortMode:" + ((int) b));
                    break;
            }
            return 0;
        }

        private int toSupportedModes(byte[] bArr) {
            int i = 0;
            for (byte b : bArr) {
                i |= toPortMode(b);
            }
            return i;
        }

        private int toContaminantProtectionStatus(byte b) {
            switch (b) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Unrecognized aidlContaminantProtection:" + ((int) b));
                    break;
            }
            return 0;
        }

        private int toSupportedContaminantProtectionModes(byte[] bArr) {
            int i = 0;
            for (byte b : bArr) {
                i |= toContaminantProtectionStatus(b);
            }
            return i;
        }

        private int toUsbDataStatusInt(byte[] bArr) {
            int i = 0;
            for (byte b : bArr) {
                switch (b) {
                    case 1:
                        i |= 1;
                        break;
                    case 2:
                        i |= 2;
                        break;
                    case 3:
                        i |= 4;
                        break;
                    case 4:
                        i = i | 8 | 64 | 128;
                        break;
                    case 5:
                        i |= 16;
                        break;
                    case 6:
                        i |= 32;
                        break;
                    case 7:
                        i = i | 64 | 8;
                        break;
                    case 8:
                        i = i | 128 | 8;
                        break;
                    default:
                        i |= 0;
                        break;
                }
            }
            com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "AIDL UsbDataStatus:" + i);
            return i;
        }

        private int[] formatComplianceWarnings(int[] iArr) {
            java.util.Objects.requireNonNull(iArr);
            android.util.IntArray intArray = new android.util.IntArray();
            java.util.Arrays.sort(iArr);
            for (int i : iArr) {
                if (intArray.indexOf(i) == -1 && i >= 1) {
                    if (android.hardware.usb.flags.Flags.enableUsbDataComplianceWarning()) {
                        if (i > 9) {
                            intArray.add(1);
                        } else {
                            intArray.add(i);
                        }
                    } else if (i > 4) {
                        intArray.add(1);
                    } else {
                        intArray.add(i);
                    }
                }
            }
            return intArray.toArray();
        }

        private int toSupportedAltModesInt(android.hardware.usb.AltModeData[] altModeDataArr) {
            int i = 0;
            for (android.hardware.usb.AltModeData altModeData : altModeDataArr) {
                switch (altModeData.getTag()) {
                    case 0:
                        i = 1;
                        break;
                }
            }
            return i;
        }

        private int toDisplayPortAltModeNumLanesInt(int i) {
            switch (i) {
                case 1:
                case 3:
                case 5:
                    return 4;
                case 2:
                case 4:
                case 6:
                    return 2;
                default:
                    return 0;
            }
        }

        private android.hardware.usb.DisplayPortAltModeInfo formatDisplayPortAltModeInfo(android.hardware.usb.AltModeData[] altModeDataArr) {
            for (android.hardware.usb.AltModeData altModeData : altModeDataArr) {
                if (altModeData.getTag() == 0) {
                    android.hardware.usb.AltModeData.DisplayPortAltModeData displayPortAltModeData = altModeData.getDisplayPortAltModeData();
                    return new android.hardware.usb.DisplayPortAltModeInfo(displayPortAltModeData.partnerSinkStatus, displayPortAltModeData.cableStatus, toDisplayPortAltModeNumLanesInt(displayPortAltModeData.pinAssignment), displayPortAltModeData.hpd, displayPortAltModeData.linkTrainingStatus);
                }
            }
            return null;
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyPortStatusChange(android.hardware.usb.PortStatus[] portStatusArr, int i) {
            android.hardware.usb.PortStatus[] portStatusArr2 = portStatusArr;
            if (!this.mUsbPortAidl.mSystemReady) {
                return;
            }
            if (i != 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "port status enquiry failed");
                return;
            }
            java.util.ArrayList<com.android.server.usb.hal.port.RawPortInfo> arrayList = new java.util.ArrayList<>();
            int i2 = 0;
            for (int length = portStatusArr2.length; i2 < length; length = length) {
                android.hardware.usb.PortStatus portStatus = portStatusArr2[i2];
                arrayList.add(new com.android.server.usb.hal.port.RawPortInfo(portStatus.portName, toSupportedModes(portStatus.supportedModes), toSupportedContaminantProtectionModes(portStatus.supportedContaminantProtectionModes), toPortMode(portStatus.currentMode), portStatus.canChangeMode, portStatus.currentPowerRole, portStatus.canChangePowerRole, portStatus.currentDataRole, portStatus.canChangeDataRole, portStatus.supportsEnableContaminantPresenceProtection, toContaminantProtectionStatus(portStatus.contaminantProtectionStatus), portStatus.supportsEnableContaminantPresenceDetection, portStatus.contaminantDetectionStatus, toUsbDataStatusInt(portStatus.usbDataStatus), portStatus.powerTransferLimited, portStatus.powerBrickStatus, portStatus.supportsComplianceWarnings, formatComplianceWarnings(portStatus.complianceWarnings), portStatus.plugOrientation, toSupportedAltModesInt(portStatus.supportedAltModes), formatDisplayPortAltModeInfo(portStatus.supportedAltModes)));
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "ClientCallback AIDL V1: " + portStatus.portName);
                i2++;
                portStatusArr2 = portStatusArr;
            }
            this.mPortManager.updatePorts(arrayList);
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyRoleSwitchStatus(java.lang.String str, android.hardware.usb.PortRole portRole, int i, long j) {
            if (i == 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, str + " role switch successful. opID:" + j);
                return;
            }
            com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, str + " role switch failed. err:" + i + "opID:" + j);
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyQueryPortStatus(java.lang.String str, int i, long j) {
            if (i == 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, str + ": opID:" + j + " successful");
                return;
            }
            com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, str + ": opID:" + j + " failed. err:" + i);
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyEnableUsbDataStatus(java.lang.String str, boolean z, int i, long j) {
            int i2;
            if (i == 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "notifyEnableUsbDataStatus:" + str + ": opID:" + j + " enable:" + z);
            } else {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, str + "notifyEnableUsbDataStatus: opID:" + j + " failed. err:" + i);
            }
            try {
                android.hardware.usb.IUsbOperationInternal iUsbOperationInternal = (android.hardware.usb.IUsbOperationInternal) com.android.server.usb.hal.port.UsbPortAidl.sCallbacks.get(j);
                if (i == 0) {
                    i2 = 0;
                } else {
                    i2 = 1;
                }
                iUsbOperationInternal.onOperationComplete(i2);
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "notifyEnableUsbDataStatus: Failed to call onOperationComplete", e);
            }
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyContaminantEnabledStatus(java.lang.String str, boolean z, int i, long j) {
            if (i == 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "notifyContaminantEnabledStatus:" + str + ": opID:" + j + " enable:" + z);
                return;
            }
            com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, str + "notifyContaminantEnabledStatus: opID:" + j + " failed. err:" + i);
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyLimitPowerTransferStatus(java.lang.String str, boolean z, int i, long j) {
            int i2;
            if (i == 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, str + ": opID:" + j + " successful");
            } else {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, str + "notifyLimitPowerTransferStatus: opID:" + j + " failed. err:" + i);
            }
            try {
                if (((android.hardware.usb.IUsbOperationInternal) com.android.server.usb.hal.port.UsbPortAidl.sCallbacks.get(j)) != null) {
                    android.hardware.usb.IUsbOperationInternal iUsbOperationInternal = (android.hardware.usb.IUsbOperationInternal) com.android.server.usb.hal.port.UsbPortAidl.sCallbacks.get(j);
                    if (i == 0) {
                        i2 = 0;
                    } else {
                        i2 = 1;
                    }
                    iUsbOperationInternal.onOperationComplete(i2);
                }
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "enableLimitPowerTransfer: Failed to call onOperationComplete", e);
            }
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyEnableUsbDataWhileDockedStatus(java.lang.String str, int i, long j) {
            int i2;
            if (i == 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, str + ": opID:" + j + " successful");
            } else {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, str + "notifyEnableUsbDataWhileDockedStatus: opID:" + j + " failed. err:" + i);
            }
            try {
                if (((android.hardware.usb.IUsbOperationInternal) com.android.server.usb.hal.port.UsbPortAidl.sCallbacks.get(j)) != null) {
                    android.hardware.usb.IUsbOperationInternal iUsbOperationInternal = (android.hardware.usb.IUsbOperationInternal) com.android.server.usb.hal.port.UsbPortAidl.sCallbacks.get(j);
                    if (i == 0) {
                        i2 = 0;
                    } else {
                        i2 = 1;
                    }
                    iUsbOperationInternal.onOperationComplete(i2);
                }
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "notifyEnableUsbDataWhileDockedStatus: Failed to call onOperationComplete", e);
            }
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyResetUsbPortStatus(java.lang.String str, int i, long j) {
            int i2;
            if (i == 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "notifyResetUsbPortStatus:" + str + ": opID:" + j);
            } else {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, str + "notifyEnableUsbDataStatus: opID:" + j + " failed. err:" + i);
            }
            try {
                android.hardware.usb.IUsbOperationInternal iUsbOperationInternal = (android.hardware.usb.IUsbOperationInternal) com.android.server.usb.hal.port.UsbPortAidl.sCallbacks.get(j);
                if (i == 0) {
                    i2 = 0;
                } else {
                    i2 = 1;
                }
                iUsbOperationInternal.onOperationComplete(i2);
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "notifyResetUsbPortStatus: Failed to call onOperationComplete", e);
            }
        }

        @Override // android.hardware.usb.IUsbCallback
        public java.lang.String getInterfaceHash() {
            return "7fe46e9531884739d925b8caeee9dba5c411e228";
        }

        @Override // android.hardware.usb.IUsbCallback
        public int getInterfaceVersion() {
            return 3;
        }
    }
}
