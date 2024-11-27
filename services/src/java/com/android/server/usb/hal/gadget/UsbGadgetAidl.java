package com.android.server.usb.hal.gadget;

/* loaded from: classes2.dex */
public final class UsbGadgetAidl implements com.android.server.usb.hal.gadget.UsbGadgetHal {
    private static final java.lang.String TAG = com.android.server.usb.hal.gadget.UsbGadgetAidl.class.getSimpleName();
    private static final java.lang.String USB_GADGET_AIDL_SERVICE = android.hardware.usb.gadget.IUsbGadget.DESCRIPTOR + "/default";
    private final com.android.server.usb.UsbDeviceManager mDeviceManager;

    @com.android.internal.annotations.GuardedBy({"mGadgetProxyLock"})
    private android.hardware.usb.gadget.IUsbGadget mGadgetProxy;
    private final java.lang.Object mGadgetProxyLock = new java.lang.Object();
    public final com.android.internal.util.IndentingPrintWriter mPw;
    private com.android.server.usb.hal.gadget.UsbGadgetAidl.UsbGadgetCallback mUsbGadgetCallback;

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public int getGadgetHalVersion() throws android.os.RemoteException {
        synchronized (this.mGadgetProxyLock) {
            if (this.mGadgetProxy == null) {
                throw new android.os.RemoteException("IUsb not initialized yet");
            }
        }
        android.util.Slog.i(TAG, "USB Gadget HAL AIDL version: GADGET_HAL_V2_0");
        return 20;
    }

    public void serviceDied() {
        com.android.server.usb.UsbDeviceManager.logAndPrint(6, this.mPw, "Usb Gadget AIDL hal service died");
        synchronized (this.mGadgetProxyLock) {
            this.mGadgetProxy = null;
        }
        connectToProxy(null);
    }

    private void connectToProxy(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mGadgetProxyLock) {
            if (this.mGadgetProxy != null) {
                return;
            }
            try {
                this.mGadgetProxy = android.hardware.usb.gadget.IUsbGadget.Stub.asInterface(android.os.ServiceManager.waitForService(USB_GADGET_AIDL_SERVICE));
            } catch (java.util.NoSuchElementException e) {
                com.android.server.usb.UsbDeviceManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb gadget hal service not found. Did the service fail to start?", e);
            }
        }
    }

    static boolean isServicePresent(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        try {
            return android.os.ServiceManager.isDeclared(USB_GADGET_AIDL_SERVICE);
        } catch (java.util.NoSuchElementException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb gadget Aidl hal service not found.", e);
            return false;
        }
    }

    public UsbGadgetAidl(com.android.server.usb.UsbDeviceManager usbDeviceManager, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.util.Objects.requireNonNull(usbDeviceManager);
        this.mDeviceManager = usbDeviceManager;
        this.mPw = indentingPrintWriter;
        connectToProxy(this.mPw);
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void getCurrentUsbFunctions(long j) {
        synchronized (this.mGadgetProxyLock) {
            try {
                try {
                    this.mGadgetProxy.getCurrentUsbFunctions(new com.android.server.usb.hal.gadget.UsbGadgetAidl.UsbGadgetCallback(), j);
                } catch (android.os.RemoteException e) {
                    com.android.server.usb.UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling getCurrentUsbFunctions, opID:" + j, e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void getUsbSpeed(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.getUsbSpeed(new com.android.server.usb.hal.gadget.UsbGadgetAidl.UsbGadgetCallback(), j);
            }
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling getUsbSpeed, opID:" + j, e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void reset(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.reset(new com.android.server.usb.hal.gadget.UsbGadgetAidl.UsbGadgetCallback(), j);
            }
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling getUsbSpeed, opID:" + j, e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void setCurrentUsbFunctions(int i, long j, boolean z, int i2, long j2) {
        try {
            this.mUsbGadgetCallback = new com.android.server.usb.hal.gadget.UsbGadgetAidl.UsbGadgetCallback(null, i, j, z);
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.setCurrentUsbFunctions(j, this.mUsbGadgetCallback, i2, j2);
            }
        } catch (android.os.RemoteException e) {
            com.android.server.usb.UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling setCurrentUsbFunctions: mRequest=" + i + ", mFunctions=" + j + ", mChargingFunctions=" + z + ", timeout=" + i2 + ", opID:" + j2, e);
        }
    }

    private class UsbGadgetCallback extends android.hardware.usb.gadget.IUsbGadgetCallback.Stub {
        public boolean mChargingFunctions;
        public long mFunctions;
        public com.android.internal.util.IndentingPrintWriter mPw;
        public int mRequest;

        UsbGadgetCallback() {
        }

        UsbGadgetCallback(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, int i, long j, boolean z) {
            this.mPw = indentingPrintWriter;
            this.mRequest = i;
            this.mFunctions = j;
            this.mChargingFunctions = z;
        }

        public void setCurrentUsbFunctionsCb(long j, int i, long j2) {
            if (i == 0) {
                com.android.server.usb.UsbDeviceManager.logAndPrint(4, this.mPw, "Usb setCurrentUsbFunctionsCb ,functions:" + j + " ,status:" + i + " ,transactionId:" + j2);
            } else {
                com.android.server.usb.UsbDeviceManager.logAndPrint(6, this.mPw, "Usb setCurrentUsbFunctionsCb failed ,functions:" + j + " ,status:" + i + " ,transactionId:" + j2);
            }
            com.android.server.usb.hal.gadget.UsbGadgetAidl.this.mDeviceManager.setCurrentUsbFunctionsCb(j, i, this.mRequest, this.mFunctions, this.mChargingFunctions);
        }

        public void getCurrentUsbFunctionsCb(long j, int i, long j2) {
            if (i == 0) {
                com.android.server.usb.UsbDeviceManager.logAndPrint(4, this.mPw, "Usb getCurrentUsbFunctionsCb ,functions:" + j + " ,status:" + i + " ,transactionId:" + j2);
            } else {
                com.android.server.usb.UsbDeviceManager.logAndPrint(6, this.mPw, "Usb getCurrentUsbFunctionsCb failed ,functions:" + j + " ,status:" + i + " ,transactionId:" + j2);
            }
            com.android.server.usb.hal.gadget.UsbGadgetAidl.this.mDeviceManager.getCurrentUsbFunctionsCb(j, i);
        }

        public void getUsbSpeedCb(int i, long j) {
            com.android.server.usb.UsbDeviceManager.logAndPrint(4, this.mPw, "getUsbSpeedCb speed:" + i + " ,transactionId:" + j);
            com.android.server.usb.hal.gadget.UsbGadgetAidl.this.mDeviceManager.getUsbSpeedCb(i);
        }

        public void resetCb(int i, long j) {
            if (i == 0) {
                com.android.server.usb.UsbDeviceManager.logAndPrint(4, this.mPw, "Usb resetCb status:" + i + " ,transactionId:" + j);
            } else {
                com.android.server.usb.UsbDeviceManager.logAndPrint(6, this.mPw, "Usb resetCb status" + i + " ,transactionId:" + j);
            }
            com.android.server.usb.hal.gadget.UsbGadgetAidl.this.mDeviceManager.resetCb(i);
        }

        public java.lang.String getInterfaceHash() {
            return "cb628c69682659911bca5c1d04042adba7f0de4b";
        }

        public int getInterfaceVersion() {
            return 1;
        }
    }
}
