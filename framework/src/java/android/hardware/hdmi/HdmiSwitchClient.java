package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class HdmiSwitchClient extends android.hardware.hdmi.HdmiClient {
    private static final java.lang.String TAG = "HdmiSwitchClient";

    @android.annotation.SystemApi
    public interface OnSelectListener {
        void onSelect(int i);
    }

    HdmiSwitchClient(android.hardware.hdmi.IHdmiControlService iHdmiControlService) {
        super(iHdmiControlService);
    }

    private static android.hardware.hdmi.IHdmiControlCallback getCallbackWrapper(final android.hardware.hdmi.HdmiSwitchClient.OnSelectListener onSelectListener) {
        return new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: android.hardware.hdmi.HdmiSwitchClient.1
            @Override // android.hardware.hdmi.IHdmiControlCallback
            public void onComplete(int i) {
                android.hardware.hdmi.HdmiSwitchClient.OnSelectListener.this.onSelect(i);
            }
        };
    }

    @Override // android.hardware.hdmi.HdmiClient
    public int getDeviceType() {
        return 6;
    }

    public void selectDevice(int i, android.hardware.hdmi.HdmiSwitchClient.OnSelectListener onSelectListener) {
        java.util.Objects.requireNonNull(onSelectListener);
        try {
            this.mService.deviceSelect(i, getCallbackWrapper(onSelectListener));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to select device: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void selectPort(int i, android.hardware.hdmi.HdmiSwitchClient.OnSelectListener onSelectListener) {
        java.util.Objects.requireNonNull(onSelectListener);
        try {
            this.mService.portSelect(i, getCallbackWrapper(onSelectListener));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to select port: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void selectDevice(int i, java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiSwitchClient.OnSelectListener onSelectListener) {
        java.util.Objects.requireNonNull(onSelectListener);
        try {
            this.mService.deviceSelect(i, new android.hardware.hdmi.HdmiSwitchClient.AnonymousClass2(executor, onSelectListener));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to select device: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiSwitchClient$2, reason: invalid class name */
    class AnonymousClass2 extends android.hardware.hdmi.IHdmiControlCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.hardware.hdmi.HdmiSwitchClient.OnSelectListener val$listener;

        AnonymousClass2(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiSwitchClient.OnSelectListener onSelectListener) {
            this.val$executor = executor;
            this.val$listener = onSelectListener;
        }

        @Override // android.hardware.hdmi.IHdmiControlCallback
        public void onComplete(final int i) {
            final java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.hdmi.HdmiSwitchClient.OnSelectListener onSelectListener = this.val$listener;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.hardware.hdmi.HdmiSwitchClient$2$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.hdmi.HdmiSwitchClient$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.hdmi.HdmiSwitchClient.OnSelectListener.this.onSelect(r2);
                        }
                    });
                }
            });
        }
    }

    @android.annotation.SystemApi
    public void selectPort(int i, java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiSwitchClient.OnSelectListener onSelectListener) {
        java.util.Objects.requireNonNull(onSelectListener);
        try {
            this.mService.portSelect(i, new android.hardware.hdmi.HdmiSwitchClient.AnonymousClass3(executor, onSelectListener));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to select port: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiSwitchClient$3, reason: invalid class name */
    class AnonymousClass3 extends android.hardware.hdmi.IHdmiControlCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.hardware.hdmi.HdmiSwitchClient.OnSelectListener val$listener;

        AnonymousClass3(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiSwitchClient.OnSelectListener onSelectListener) {
            this.val$executor = executor;
            this.val$listener = onSelectListener;
        }

        @Override // android.hardware.hdmi.IHdmiControlCallback
        public void onComplete(final int i) {
            final java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.hdmi.HdmiSwitchClient.OnSelectListener onSelectListener = this.val$listener;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.hardware.hdmi.HdmiSwitchClient$3$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.hardware.hdmi.HdmiSwitchClient$3$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.hdmi.HdmiSwitchClient.OnSelectListener.this.onSelect(r2);
                        }
                    });
                }
            });
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceList() {
        try {
            return this.mService.getDeviceList();
        } catch (android.os.RemoteException e) {
            android.util.Log.e("TAG", "Failed to call getDeviceList():", e);
            return java.util.Collections.emptyList();
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() {
        try {
            return this.mService.getPortInfo();
        } catch (android.os.RemoteException e) {
            android.util.Log.e("TAG", "Failed to call getPortInfo():", e);
            return java.util.Collections.emptyList();
        }
    }
}
