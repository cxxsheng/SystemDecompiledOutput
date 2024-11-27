package com.android.server.usb.hal.gadget;

/* loaded from: classes2.dex */
public final class UsbGadgetHidl implements com.android.server.usb.hal.gadget.UsbGadgetHal {
    private static final int USB_GADGET_HAL_DEATH_COOKIE = 2000;
    private com.android.server.usb.UsbDeviceManager mDeviceManager;

    @com.android.internal.annotations.GuardedBy({"mGadgetProxyLock"})
    private android.hardware.usb.gadget.V1_0.IUsbGadget mGadgetProxy;
    private final java.lang.Object mGadgetProxyLock = new java.lang.Object();
    private final com.android.internal.util.IndentingPrintWriter mPw;
    private com.android.server.usb.hal.gadget.UsbGadgetHidl.UsbGadgetCallback mUsbGadgetCallback;

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public int getGadgetHalVersion() throws android.os.RemoteException {
        int i;
        synchronized (this.mGadgetProxyLock) {
            try {
                if (this.mGadgetProxy == null) {
                    throw new android.os.RemoteException("IUsbGadget not initialized yet");
                }
                if (android.hardware.usb.gadget.V1_2.IUsbGadget.castFrom(this.mGadgetProxy) != null) {
                    i = 12;
                } else if (android.hardware.usb.gadget.V1_1.IUsbGadget.castFrom(this.mGadgetProxy) != null) {
                    i = 11;
                } else {
                    i = 10;
                }
                com.android.server.usb.UsbDeviceManager.logAndPrint(4, this.mPw, "USB Gadget HAL HIDL version: " + i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    final class DeathRecipient implements android.os.IHwBinder.DeathRecipient {
        private final com.android.internal.util.IndentingPrintWriter mPw;

        DeathRecipient(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            this.mPw = indentingPrintWriter;
        }

        public void serviceDied(long j) {
            if (j == 2000) {
                com.android.server.usb.UsbDeviceManager.logAndPrint(6, this.mPw, "Usb Gadget hal service died cookie: " + j);
                synchronized (com.android.server.usb.hal.gadget.UsbGadgetHidl.this.mGadgetProxyLock) {
                    com.android.server.usb.hal.gadget.UsbGadgetHidl.this.mGadgetProxy = null;
                }
            }
        }
    }

    final class ServiceNotification extends android.hidl.manager.V1_0.IServiceNotification.Stub {
        ServiceNotification() {
        }

        @Override // android.hidl.manager.V1_0.IServiceNotification
        public void onRegistration(java.lang.String str, java.lang.String str2, boolean z) {
            com.android.server.usb.UsbDeviceManager.logAndPrint(4, com.android.server.usb.hal.gadget.UsbGadgetHidl.this.mPw, "Usb gadget hal service started " + str + " " + str2);
            com.android.server.usb.hal.gadget.UsbGadgetHidl.this.connectToProxy(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectToProxy(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mGadgetProxyLock) {
            if (this.mGadgetProxy != null) {
                return;
            }
            try {
                this.mGadgetProxy = android.hardware.usb.gadget.V1_0.IUsbGadget.getService();
                this.mGadgetProxy.linkToDeath(new com.android.server.usb.hal.gadget.UsbGadgetHidl.DeathRecipient(indentingPrintWriter), 2000L);
            } catch (android.os.RemoteException e) {
                com.android.server.usb.UsbDeviceManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb gadget hal service not responding", e);
            } catch (java.util.NoSuchElementException e2) {
                com.android.server.usb.UsbDeviceManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb gadget hal service not found. Did the service fail to start?", e2);
            }
        }
    }

    static boolean isServicePresent(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        try {
            android.hardware.usb.gadget.V1_0.IUsbGadget.getService(true);
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(indentingPrintWriter, "IUSBGadget hal service present but failed to get service", e);
        } catch (java.util.NoSuchElementException e2) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb gadget hidl hal service not found.", e2);
            return false;
        }
        return true;
    }

    public UsbGadgetHidl(com.android.server.usb.UsbDeviceManager usbDeviceManager, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.util.Objects.requireNonNull(usbDeviceManager);
        this.mDeviceManager = usbDeviceManager;
        this.mPw = indentingPrintWriter;
        try {
            if (!android.hidl.manager.V1_0.IServiceManager.getService().registerForNotifications("android.hardware.usb.gadget@1.0::IUsbGadget", "", new com.android.server.usb.hal.gadget.UsbGadgetHidl.ServiceNotification())) {
                com.android.server.usb.UsbDeviceManager.logAndPrint(6, indentingPrintWriter, "Failed to register service start notification");
            }
            connectToProxy(this.mPw);
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(indentingPrintWriter, "Failed to register service start notification", e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void getCurrentUsbFunctions(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.getCurrentUsbFunctions(new com.android.server.usb.hal.gadget.UsbGadgetHidl.UsbGadgetCallback());
            }
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling getCurrentUsbFunctions", e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void getUsbSpeed(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                try {
                    if (android.hardware.usb.gadget.V1_2.IUsbGadget.castFrom(this.mGadgetProxy) != null) {
                        android.hardware.usb.gadget.V1_2.IUsbGadget.castFrom(this.mGadgetProxy).getUsbSpeed(new com.android.server.usb.hal.gadget.UsbGadgetHidl.UsbGadgetCallback());
                    }
                } finally {
                }
            }
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(this.mPw, "get UsbSpeed failed", e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void reset(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                try {
                    if (android.hardware.usb.gadget.V1_1.IUsbGadget.castFrom(this.mGadgetProxy) != null) {
                        android.hardware.usb.gadget.V1_1.IUsbGadget.castFrom(this.mGadgetProxy).reset();
                    }
                } finally {
                }
            }
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling reset", e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void setCurrentUsbFunctions(int i, long j, boolean z, int i2, long j2) {
        try {
            this.mUsbGadgetCallback = new com.android.server.usb.hal.gadget.UsbGadgetHidl.UsbGadgetCallback(null, i, j, z);
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.setCurrentUsbFunctions(j, this.mUsbGadgetCallback, i2);
            }
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling setCurrentUsbFunctions mRequest = " + i + ", mFunctions = " + j + ", timeout = " + i2 + ", mChargingFunctions = " + z + ", operationId =" + j2, e);
        }
    }

    private class UsbGadgetCallback extends android.hardware.usb.gadget.V1_2.IUsbGadgetCallback.Stub {
        public boolean mChargingFunctions;
        public long mFunctions;
        public int mRequest;

        UsbGadgetCallback() {
        }

        UsbGadgetCallback(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, int i, long j, boolean z) {
            this.mRequest = i;
            this.mFunctions = j;
            this.mChargingFunctions = z;
        }

        public void setCurrentUsbFunctionsCb(long j, int i) {
            com.android.server.usb.hal.gadget.UsbGadgetHidl.this.mDeviceManager.setCurrentUsbFunctionsCb(j, i, this.mRequest, this.mFunctions, this.mChargingFunctions);
        }

        public void getCurrentUsbFunctionsCb(long j, int i) {
            com.android.server.usb.hal.gadget.UsbGadgetHidl.this.mDeviceManager.getCurrentUsbFunctionsCb(j, i);
        }

        public void getUsbSpeedCb(int i) {
            com.android.server.usb.hal.gadget.UsbGadgetHidl.this.mDeviceManager.getUsbSpeedCb(i);
        }
    }
}
