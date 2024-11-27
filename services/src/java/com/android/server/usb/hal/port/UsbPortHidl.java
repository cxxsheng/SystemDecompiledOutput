package com.android.server.usb.hal.port;

/* loaded from: classes2.dex */
public final class UsbPortHidl implements com.android.server.usb.hal.port.UsbPortHal {
    private static final int USB_HAL_DEATH_COOKIE = 1000;
    private com.android.server.usb.hal.port.UsbPortHidl.HALCallback mHALCallback;
    private final java.lang.Object mLock = new java.lang.Object();
    private com.android.server.usb.UsbPortManager mPortManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.usb.V1_0.IUsb mProxy;
    public com.android.internal.util.IndentingPrintWriter mPw;
    private boolean mSystemReady;
    private static final java.lang.String TAG = com.android.server.usb.hal.port.UsbPortHidl.class.getSimpleName();
    private static int sUsbDataStatus = 0;

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public int getUsbHalVersion() throws android.os.RemoteException {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    throw new android.os.RemoteException("IUsb not initialized yet");
                }
                if (android.hardware.usb.V1_3.IUsb.castFrom((android.os.IHwInterface) this.mProxy) != null) {
                    i = 13;
                } else if (android.hardware.usb.V1_2.IUsb.castFrom((android.os.IHwInterface) this.mProxy) != null) {
                    i = 12;
                } else if (android.hardware.usb.V1_1.IUsb.castFrom((android.os.IHwInterface) this.mProxy) != null) {
                    i = 11;
                } else {
                    i = 10;
                }
                com.android.server.usb.UsbPortManager.logAndPrint(4, null, "USB HAL HIDL version: " + i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    final class DeathRecipient implements android.os.IHwBinder.DeathRecipient {
        public com.android.internal.util.IndentingPrintWriter pw;

        DeathRecipient(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            this.pw = indentingPrintWriter;
        }

        public void serviceDied(long j) {
            if (j == 1000) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.pw, "Usb hal service died cookie: " + j);
                synchronized (com.android.server.usb.hal.port.UsbPortHidl.this.mLock) {
                    com.android.server.usb.hal.port.UsbPortHidl.this.mProxy = null;
                }
            }
        }
    }

    final class ServiceNotification extends android.hidl.manager.V1_0.IServiceNotification.Stub {
        ServiceNotification() {
        }

        @Override // android.hidl.manager.V1_0.IServiceNotification
        public void onRegistration(java.lang.String str, java.lang.String str2, boolean z) {
            com.android.server.usb.UsbPortManager.logAndPrint(4, null, "Usb hal service started " + str + " " + str2);
            com.android.server.usb.hal.port.UsbPortHidl.this.connectToProxy(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectToProxy(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            if (this.mProxy != null) {
                return;
            }
            try {
                this.mProxy = android.hardware.usb.V1_0.IUsb.getService();
                this.mProxy.linkToDeath(new com.android.server.usb.hal.port.UsbPortHidl.DeathRecipient(indentingPrintWriter), 1000L);
                this.mProxy.setCallback(this.mHALCallback);
                this.mProxy.queryPortStatus();
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb hal service not responding", e);
            } catch (java.util.NoSuchElementException e2) {
                com.android.server.usb.UsbPortManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb hal service not found. Did the service fail to start?", e2);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void systemReady() {
        this.mSystemReady = true;
    }

    static boolean isServicePresent(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        try {
            android.hardware.usb.V1_0.IUsb.getService(true);
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbPortManager.logAndPrintException(indentingPrintWriter, "IUSB hal service present but failed to get service", e);
        } catch (java.util.NoSuchElementException e2) {
            com.android.server.usb.UsbPortManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb hidl hal service not found.", e2);
            return false;
        }
        return true;
    }

    public UsbPortHidl(com.android.server.usb.UsbPortManager usbPortManager, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.util.Objects.requireNonNull(usbPortManager);
        this.mPortManager = usbPortManager;
        this.mPw = indentingPrintWriter;
        this.mHALCallback = new com.android.server.usb.hal.port.UsbPortHidl.HALCallback(null, this.mPortManager, this);
        try {
            if (!android.hidl.manager.V1_0.IServiceManager.getService().registerForNotifications(android.hardware.usb.V1_0.IUsb.kInterfaceName, "", new com.android.server.usb.hal.port.UsbPortHidl.ServiceNotification())) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, null, "Failed to register service start notification");
            }
            connectToProxy(this.mPw);
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbPortManager.logAndPrintException(null, "Failed to register service start notification", e);
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void enableContaminantPresenceDetection(java.lang.String str, boolean z, long j) {
        synchronized (this.mLock) {
            if (this.mProxy == null) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                return;
            }
            try {
                try {
                    android.hardware.usb.V1_2.IUsb.castFrom((android.os.IHwInterface) this.mProxy).enableContaminantPresenceDetection(str, z);
                } catch (java.lang.ClassCastException e) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Method only applicable to V1.2 or above implementation", e);
                }
            } catch (android.os.RemoteException e2) {
                com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to set contaminant detection", e2);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void queryPortStatus(long j) {
        synchronized (this.mLock) {
            if (this.mProxy == null) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                return;
            }
            try {
                this.mProxy.queryPortStatus();
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbPortManager.logAndPrintException(null, "ServiceStart: Failed to query port status", e);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void switchMode(java.lang.String str, int i, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                    return;
                }
                android.hardware.usb.V1_0.PortRole portRole = new android.hardware.usb.V1_0.PortRole();
                portRole.type = 2;
                portRole.role = i;
                try {
                    this.mProxy.switchRole(str, portRole);
                } catch (android.os.RemoteException e) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to set the USB port mode: portId=" + str + ", newMode=" + android.hardware.usb.UsbPort.modeToString(portRole.role), e);
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
                    com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                    return;
                }
                android.hardware.usb.V1_0.PortRole portRole = new android.hardware.usb.V1_0.PortRole();
                portRole.type = 1;
                portRole.role = i;
                try {
                    this.mProxy.switchRole(str, portRole);
                } catch (android.os.RemoteException e) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to set the USB power role: portId=" + str + ", newPowerRole=" + android.hardware.usb.UsbPort.powerRoleToString(portRole.role), e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void enableLimitPowerTransfer(java.lang.String str, boolean z, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        try {
            iUsbOperationInternal.onOperationComplete(2);
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete", e);
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void enableUsbDataWhileDocked(java.lang.String str, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        try {
            iUsbOperationInternal.onOperationComplete(2);
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete", e);
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void switchDataRole(java.lang.String str, int i, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                    return;
                }
                android.hardware.usb.V1_0.PortRole portRole = new android.hardware.usb.V1_0.PortRole();
                portRole.type = 0;
                portRole.role = i;
                try {
                    this.mProxy.switchRole(str, portRole);
                } catch (android.os.RemoteException e) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to set the USB data role: portId=" + str + ", newDataRole=" + android.hardware.usb.UsbPort.dataRoleToString(portRole.role), e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public void resetUsbPort(java.lang.String str, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        try {
            iUsbOperationInternal.onOperationComplete(2);
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e);
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public boolean enableUsbData(java.lang.String str, boolean z, long j, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        int i;
        boolean enableUsbDataSignal;
        try {
            if (getUsbHalVersion() != 13) {
                try {
                    iUsbOperationInternal.onOperationComplete(2);
                } catch (android.os.RemoteException e) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e);
                }
                return false;
            }
            synchronized (this.mLock) {
                i = 1;
                try {
                    enableUsbDataSignal = android.hardware.usb.V1_3.IUsb.castFrom((android.os.IHwInterface) this.mProxy).enableUsbDataSignal(z);
                } catch (android.os.RemoteException e2) {
                    com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed enableUsbData: opId:" + j + " portId=" + str, e2);
                    try {
                        iUsbOperationInternal.onOperationComplete(1);
                    } catch (android.os.RemoteException e3) {
                        com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e3);
                    }
                    return false;
                }
            }
            if (enableUsbDataSignal) {
                sUsbDataStatus = z ? 0 : 16;
            }
            if (enableUsbDataSignal) {
                i = 0;
            }
            try {
                iUsbOperationInternal.onOperationComplete(i);
            } catch (android.os.RemoteException e4) {
                com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e4);
            }
            return false;
        } catch (android.os.RemoteException e5) {
            com.android.server.usb.UsbPortManager.logAndPrintException(this.mPw, "Failed to query USB HAL version. opID:" + j + " portId:" + str, e5);
            return false;
        }
    }

    private static class HALCallback extends android.hardware.usb.V1_2.IUsbCallback.Stub {
        public com.android.server.usb.UsbPortManager mPortManager;
        public com.android.internal.util.IndentingPrintWriter mPw;
        public com.android.server.usb.hal.port.UsbPortHidl mUsbPortHidl;

        HALCallback(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, com.android.server.usb.UsbPortManager usbPortManager, com.android.server.usb.hal.port.UsbPortHidl usbPortHidl) {
            this.mPw = indentingPrintWriter;
            this.mPortManager = usbPortManager;
            this.mUsbPortHidl = usbPortHidl;
        }

        @Override // android.hardware.usb.V1_0.IUsbCallback
        public void notifyPortStatusChange(java.util.ArrayList<android.hardware.usb.V1_0.PortStatus> arrayList, int i) {
            if (!this.mUsbPortHidl.mSystemReady) {
                return;
            }
            if (i != 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "port status enquiry failed");
                return;
            }
            java.util.ArrayList<com.android.server.usb.hal.port.RawPortInfo> arrayList2 = new java.util.ArrayList<>();
            java.util.Iterator<android.hardware.usb.V1_0.PortStatus> it = arrayList.iterator();
            while (it.hasNext()) {
                android.hardware.usb.V1_0.PortStatus next = it.next();
                arrayList2.add(new com.android.server.usb.hal.port.RawPortInfo(next.portName, next.supportedModes, 0, next.currentMode, next.canChangeMode, next.currentPowerRole, next.canChangePowerRole, next.currentDataRole, next.canChangeDataRole, false, 0, false, 0, com.android.server.usb.hal.port.UsbPortHidl.sUsbDataStatus, false, 0, false, new int[0], 0, 0, null));
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "ClientCallback V1_0: " + next.portName);
            }
            this.mPortManager.updatePorts(arrayList2);
        }

        @Override // android.hardware.usb.V1_1.IUsbCallback
        public void notifyPortStatusChange_1_1(java.util.ArrayList<android.hardware.usb.V1_1.PortStatus_1_1> arrayList, int i) {
            if (!this.mUsbPortHidl.mSystemReady) {
                return;
            }
            if (i != 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "port status enquiry failed");
                return;
            }
            java.util.ArrayList<com.android.server.usb.hal.port.RawPortInfo> arrayList2 = new java.util.ArrayList<>();
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                android.hardware.usb.V1_1.PortStatus_1_1 portStatus_1_1 = arrayList.get(i2);
                int i3 = size;
                arrayList2.add(new com.android.server.usb.hal.port.RawPortInfo(portStatus_1_1.status.portName, portStatus_1_1.supportedModes, 0, portStatus_1_1.currentMode, portStatus_1_1.status.canChangeMode, portStatus_1_1.status.currentPowerRole, portStatus_1_1.status.canChangePowerRole, portStatus_1_1.status.currentDataRole, portStatus_1_1.status.canChangeDataRole, false, 0, false, 0, com.android.server.usb.hal.port.UsbPortHidl.sUsbDataStatus, false, 0, false, new int[0], 0, 0, null));
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "ClientCallback V1_1: " + portStatus_1_1.status.portName);
                i2++;
                size = i3;
            }
            this.mPortManager.updatePorts(arrayList2);
        }

        @Override // android.hardware.usb.V1_2.IUsbCallback
        public void notifyPortStatusChange_1_2(java.util.ArrayList<android.hardware.usb.V1_2.PortStatus> arrayList, int i) {
            if (!this.mUsbPortHidl.mSystemReady) {
                return;
            }
            if (i != 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, "port status enquiry failed");
                return;
            }
            java.util.ArrayList<com.android.server.usb.hal.port.RawPortInfo> arrayList2 = new java.util.ArrayList<>();
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                android.hardware.usb.V1_2.PortStatus portStatus = arrayList.get(i2);
                int i3 = size;
                arrayList2.add(new com.android.server.usb.hal.port.RawPortInfo(portStatus.status_1_1.status.portName, portStatus.status_1_1.supportedModes, portStatus.supportedContaminantProtectionModes, portStatus.status_1_1.currentMode, portStatus.status_1_1.status.canChangeMode, portStatus.status_1_1.status.currentPowerRole, portStatus.status_1_1.status.canChangePowerRole, portStatus.status_1_1.status.currentDataRole, portStatus.status_1_1.status.canChangeDataRole, portStatus.supportsEnableContaminantPresenceProtection, portStatus.contaminantProtectionStatus, portStatus.supportsEnableContaminantPresenceDetection, portStatus.contaminantDetectionStatus, com.android.server.usb.hal.port.UsbPortHidl.sUsbDataStatus, false, 0, false, new int[0], 0, 0, null));
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, "ClientCallback V1_2: " + portStatus.status_1_1.status.portName);
                i2++;
                size = i3;
            }
            this.mPortManager.updatePorts(arrayList2);
        }

        @Override // android.hardware.usb.V1_0.IUsbCallback
        public void notifyRoleSwitchStatus(java.lang.String str, android.hardware.usb.V1_0.PortRole portRole, int i) {
            if (i == 0) {
                com.android.server.usb.UsbPortManager.logAndPrint(4, this.mPw, str + " role switch successful");
                return;
            }
            com.android.server.usb.UsbPortManager.logAndPrint(6, this.mPw, str + " role switch failed");
        }
    }
}
