package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class HdmiClient {
    private static final java.lang.String TAG = "HdmiClient";
    private static final int UNKNOWN_VENDOR_ID = 16777215;
    private android.hardware.hdmi.IHdmiVendorCommandListener mIHdmiVendorCommandListener;
    final android.hardware.hdmi.IHdmiControlService mService;

    public interface OnDeviceSelectedListener {
        void onDeviceSelected(int i, int i2);
    }

    abstract int getDeviceType();

    HdmiClient(android.hardware.hdmi.IHdmiControlService iHdmiControlService) {
        this.mService = iHdmiControlService;
    }

    public void selectDevice(int i, java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiClient.OnDeviceSelectedListener onDeviceSelectedListener) {
        if (onDeviceSelectedListener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor must not be null.");
        }
        try {
            this.mService.deviceSelect(i, getCallbackWrapper(i, executor, onDeviceSelectedListener));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to select device: ", e);
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiClient$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.hdmi.IHdmiControlCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.hardware.hdmi.HdmiClient.OnDeviceSelectedListener val$listener;
        final /* synthetic */ int val$logicalAddress;

        AnonymousClass1(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiClient.OnDeviceSelectedListener onDeviceSelectedListener, int i) {
            this.val$executor = executor;
            this.val$listener = onDeviceSelectedListener;
            this.val$logicalAddress = i;
        }

        @Override // android.hardware.hdmi.IHdmiControlCallback
        public void onComplete(final int i) {
            final java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.hdmi.HdmiClient.OnDeviceSelectedListener onDeviceSelectedListener = this.val$listener;
            final int i2 = this.val$logicalAddress;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.hardware.hdmi.HdmiClient$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.hdmi.HdmiClient$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.hdmi.HdmiClient.OnDeviceSelectedListener.this.onDeviceSelected(r2, r3);
                        }
                    });
                }
            });
        }
    }

    private static android.hardware.hdmi.IHdmiControlCallback getCallbackWrapper(int i, java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiClient.OnDeviceSelectedListener onDeviceSelectedListener) {
        return new android.hardware.hdmi.HdmiClient.AnonymousClass1(executor, onDeviceSelectedListener, i);
    }

    public android.hardware.hdmi.HdmiDeviceInfo getActiveSource() {
        try {
            return this.mService.getActiveSource();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "getActiveSource threw exception ", e);
            return null;
        }
    }

    public void sendKeyEvent(int i, boolean z) {
        try {
            this.mService.sendKeyEvent(getDeviceType(), i, z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "sendKeyEvent threw exception ", e);
        }
    }

    public void sendVolumeKeyEvent(int i, boolean z) {
        try {
            this.mService.sendVolumeKeyEvent(getDeviceType(), i, z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "sendVolumeKeyEvent threw exception ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendVendorCommand(int i, byte[] bArr, boolean z) {
        try {
            this.mService.sendVendorCommand(getDeviceType(), i, bArr, z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to send vendor command: ", e);
        }
    }

    public void setVendorCommandListener(android.hardware.hdmi.HdmiControlManager.VendorCommandListener vendorCommandListener) {
        setVendorCommandListener(vendorCommandListener, 16777215);
    }

    public void setVendorCommandListener(android.hardware.hdmi.HdmiControlManager.VendorCommandListener vendorCommandListener, int i) {
        if (vendorCommandListener == null) {
            throw new java.lang.IllegalArgumentException("listener cannot be null");
        }
        if (this.mIHdmiVendorCommandListener != null) {
            throw new java.lang.IllegalStateException("listener was already set");
        }
        try {
            android.hardware.hdmi.IHdmiVendorCommandListener listenerWrapper = getListenerWrapper(vendorCommandListener);
            this.mService.addVendorCommandListener(listenerWrapper, i);
            this.mIHdmiVendorCommandListener = listenerWrapper;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to set vendor command listener: ", e);
        }
    }

    private static android.hardware.hdmi.IHdmiVendorCommandListener getListenerWrapper(final android.hardware.hdmi.HdmiControlManager.VendorCommandListener vendorCommandListener) {
        return new android.hardware.hdmi.IHdmiVendorCommandListener.Stub() { // from class: android.hardware.hdmi.HdmiClient.2
            @Override // android.hardware.hdmi.IHdmiVendorCommandListener
            public void onReceived(int i, int i2, byte[] bArr, boolean z) {
                android.hardware.hdmi.HdmiControlManager.VendorCommandListener.this.onReceived(i, i2, bArr, z);
            }

            @Override // android.hardware.hdmi.IHdmiVendorCommandListener
            public void onControlStateChanged(boolean z, int i) {
                android.hardware.hdmi.HdmiControlManager.VendorCommandListener.this.onControlStateChanged(z, i);
            }
        };
    }
}
