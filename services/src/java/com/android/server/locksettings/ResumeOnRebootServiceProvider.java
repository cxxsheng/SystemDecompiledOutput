package com.android.server.locksettings;

/* loaded from: classes2.dex */
public class ResumeOnRebootServiceProvider {
    static final java.lang.String PROP_ROR_PROVIDER_PACKAGE = "persist.sys.resume_on_reboot_provider_package";
    private static final java.lang.String PROVIDER_PACKAGE = android.provider.DeviceConfig.getString("ota", "resume_on_reboot_service_package", "");
    private static final java.lang.String PROVIDER_REQUIRED_PERMISSION = "android.permission.BIND_RESUME_ON_REBOOT_SERVICE";
    private static final java.lang.String TAG = "ResumeOnRebootServiceProvider";
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;

    public ResumeOnRebootServiceProvider(android.content.Context context) {
        this(context, context.getPackageManager());
    }

    @com.android.internal.annotations.VisibleForTesting
    public ResumeOnRebootServiceProvider(android.content.Context context, android.content.pm.PackageManager packageManager) {
        this.mContext = context;
        this.mPackageManager = packageManager;
    }

    @android.annotation.Nullable
    private android.content.pm.ServiceInfo resolveService() {
        int i;
        android.content.Intent intent = new android.content.Intent();
        intent.setAction("android.service.resumeonreboot.ResumeOnRebootService");
        java.lang.String str = android.os.SystemProperties.get(PROP_ROR_PROVIDER_PACKAGE, "");
        if (!str.isEmpty()) {
            android.util.Slog.i(TAG, "Using test app: " + str);
            intent.setPackage(str);
            i = 4;
        } else {
            if (PROVIDER_PACKAGE != null && !PROVIDER_PACKAGE.equals("")) {
                intent.setPackage(PROVIDER_PACKAGE);
            }
            i = 1048580;
        }
        for (android.content.pm.ResolveInfo resolveInfo : this.mPackageManager.queryIntentServices(intent, i)) {
            if (resolveInfo.serviceInfo != null && PROVIDER_REQUIRED_PERMISSION.equals(resolveInfo.serviceInfo.permission)) {
                return resolveInfo.serviceInfo;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    public com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection getServiceConnection() {
        android.content.pm.ServiceInfo resolveService = resolveService();
        if (resolveService == null) {
            return null;
        }
        return new com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection(this.mContext, resolveService.getComponentName());
    }

    public static class ResumeOnRebootServiceConnection {
        private static final java.lang.String TAG = "ResumeOnRebootServiceConnection";
        private android.service.resumeonreboot.IResumeOnRebootService mBinder;
        private final android.content.ComponentName mComponentName;
        private final android.content.Context mContext;

        @android.annotation.Nullable
        android.content.ServiceConnection mServiceConnection;

        private ResumeOnRebootServiceConnection(android.content.Context context, @android.annotation.NonNull android.content.ComponentName componentName) {
            this.mContext = context;
            this.mComponentName = componentName;
        }

        public void unbindService() {
            if (this.mServiceConnection != null) {
                this.mContext.unbindService(this.mServiceConnection);
            }
            this.mBinder = null;
        }

        public void bindToService(long j) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            if (this.mBinder == null || !this.mBinder.asBinder().isBinderAlive()) {
                final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
                android.content.Intent intent = new android.content.Intent();
                intent.setComponent(this.mComponentName);
                this.mServiceConnection = new android.content.ServiceConnection() { // from class: com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection.1
                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                        com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection.this.mBinder = android.service.resumeonreboot.IResumeOnRebootService.Stub.asInterface(iBinder);
                        countDownLatch.countDown();
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(android.content.ComponentName componentName) {
                        com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection.this.mBinder = null;
                    }
                };
                if (!this.mContext.bindServiceAsUser(intent, this.mServiceConnection, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, com.android.internal.os.BackgroundThread.getHandler(), android.os.UserHandle.SYSTEM)) {
                    android.util.Slog.e(TAG, "Binding: " + this.mComponentName + " u" + android.os.UserHandle.SYSTEM + " failed.");
                    return;
                }
                waitForLatch(countDownLatch, "serviceConnection", j);
            }
        }

        public byte[] wrapBlob(byte[] bArr, long j, long j2) throws android.os.RemoteException, java.util.concurrent.TimeoutException, java.io.IOException {
            if (this.mBinder == null || !this.mBinder.asBinder().isBinderAlive()) {
                throw new android.os.RemoteException("Service not bound");
            }
            java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceCallback resumeOnRebootServiceCallback = new com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceCallback(countDownLatch);
            this.mBinder.wrapSecret(bArr, j, new android.os.RemoteCallback(resumeOnRebootServiceCallback));
            waitForLatch(countDownLatch, "wrapSecret", j2);
            if (resumeOnRebootServiceCallback.getResult().containsKey("exception_key")) {
                throwTypedException((android.os.ParcelableException) resumeOnRebootServiceCallback.getResult().getParcelable("exception_key", android.os.ParcelableException.class));
            }
            return resumeOnRebootServiceCallback.mResult.getByteArray("wrapped_blob_key");
        }

        public byte[] unwrap(byte[] bArr, long j) throws android.os.RemoteException, java.util.concurrent.TimeoutException, java.io.IOException {
            if (this.mBinder == null || !this.mBinder.asBinder().isBinderAlive()) {
                throw new android.os.RemoteException("Service not bound");
            }
            java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceCallback resumeOnRebootServiceCallback = new com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceCallback(countDownLatch);
            this.mBinder.unwrap(bArr, new android.os.RemoteCallback(resumeOnRebootServiceCallback));
            waitForLatch(countDownLatch, "unWrapSecret", j);
            if (resumeOnRebootServiceCallback.getResult().containsKey("exception_key")) {
                throwTypedException((android.os.ParcelableException) resumeOnRebootServiceCallback.getResult().getParcelable("exception_key", android.os.ParcelableException.class));
            }
            return resumeOnRebootServiceCallback.getResult().getByteArray("unrwapped_blob_key");
        }

        private void throwTypedException(android.os.ParcelableException parcelableException) throws java.io.IOException, android.os.RemoteException {
            if (parcelableException != null && (parcelableException.getCause() instanceof java.io.IOException)) {
                parcelableException.maybeRethrow(java.io.IOException.class);
                return;
            }
            throw new android.os.RemoteException("ResumeOnRebootServiceConnection wrap/unwrap failed", parcelableException, true, true);
        }

        private void waitForLatch(java.util.concurrent.CountDownLatch countDownLatch, java.lang.String str, long j) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            try {
                if (!countDownLatch.await(j, java.util.concurrent.TimeUnit.SECONDS)) {
                    throw new java.util.concurrent.TimeoutException("Latch wait for " + str + " elapsed");
                }
            } catch (java.lang.InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
                throw new android.os.RemoteException("Latch wait for " + str + " interrupted");
            }
        }
    }

    private static class ResumeOnRebootServiceCallback implements android.os.RemoteCallback.OnResultListener {
        private android.os.Bundle mResult;
        private final java.util.concurrent.CountDownLatch mResultLatch;

        private ResumeOnRebootServiceCallback(java.util.concurrent.CountDownLatch countDownLatch) {
            this.mResultLatch = countDownLatch;
        }

        public void onResult(@android.annotation.Nullable android.os.Bundle bundle) {
            this.mResult = bundle;
            this.mResultLatch.countDown();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.os.Bundle getResult() {
            return this.mResult;
        }
    }
}
