package android.service.carrier;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class CarrierMessagingServiceWrapper implements java.lang.AutoCloseable {
    private volatile android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingServiceConnection mCarrierMessagingServiceConnection;
    private android.content.Context mContext;
    private volatile android.service.carrier.ICarrierMessagingService mICarrierMessagingService;
    private java.lang.Runnable mOnServiceReadyCallback;
    private java.util.concurrent.Executor mServiceReadyCallbackExecutor;

    @android.annotation.SystemApi
    public boolean bindToCarrierMessagingService(android.content.Context context, java.lang.String str, java.util.concurrent.Executor executor, java.lang.Runnable runnable) {
        com.android.internal.util.Preconditions.checkState(this.mCarrierMessagingServiceConnection == null);
        java.util.Objects.requireNonNull(context);
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(runnable);
        android.content.Intent intent = new android.content.Intent(android.service.carrier.CarrierMessagingService.SERVICE_INTERFACE);
        intent.setPackage(str);
        this.mCarrierMessagingServiceConnection = new android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingServiceConnection();
        this.mOnServiceReadyCallback = runnable;
        this.mServiceReadyCallbackExecutor = executor;
        this.mContext = context;
        return context.bindService(intent, this.mCarrierMessagingServiceConnection, 1);
    }

    @android.annotation.SystemApi
    public void disconnect() {
        com.android.internal.util.Preconditions.checkNotNull(this.mCarrierMessagingServiceConnection);
        this.mContext.unbindService(this.mCarrierMessagingServiceConnection);
        this.mCarrierMessagingServiceConnection = null;
        this.mOnServiceReadyCallback = null;
        this.mServiceReadyCallbackExecutor = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onServiceReady(android.service.carrier.ICarrierMessagingService iCarrierMessagingService) {
        this.mICarrierMessagingService = iCarrierMessagingService;
        if (this.mOnServiceReadyCallback != null && this.mServiceReadyCallbackExecutor != null) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mServiceReadyCallbackExecutor.execute(this.mOnServiceReadyCallback);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @android.annotation.SystemApi
    public void receiveSms(android.service.carrier.MessagePdu messagePdu, java.lang.String str, int i, int i2, java.util.concurrent.Executor executor, android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallback carrierMessagingCallback) {
        if (this.mICarrierMessagingService != null) {
            try {
                this.mICarrierMessagingService.filterSms(messagePdu, str, i, i2, new android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal(carrierMessagingCallback, executor));
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    @android.annotation.SystemApi
    public void sendTextSms(java.lang.String str, int i, java.lang.String str2, int i2, java.util.concurrent.Executor executor, android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallback carrierMessagingCallback) {
        java.util.Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.sendTextSms(str, i, str2, i2, new android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal(carrierMessagingCallback, executor));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    public void sendDataSms(byte[] bArr, int i, java.lang.String str, int i2, int i3, java.util.concurrent.Executor executor, android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallback carrierMessagingCallback) {
        java.util.Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.sendDataSms(bArr, i, str, i2, i3, new android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal(carrierMessagingCallback, executor));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    public void sendMultipartTextSms(java.util.List<java.lang.String> list, int i, java.lang.String str, int i2, java.util.concurrent.Executor executor, android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallback carrierMessagingCallback) {
        java.util.Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.sendMultipartTextSms(list, i, str, i2, new android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal(carrierMessagingCallback, executor));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    public void sendMms(android.net.Uri uri, int i, android.net.Uri uri2, java.util.concurrent.Executor executor, android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallback carrierMessagingCallback) {
        java.util.Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.sendMms(uri, i, uri2, new android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal(carrierMessagingCallback, executor));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    public void downloadMms(android.net.Uri uri, int i, android.net.Uri uri2, java.util.concurrent.Executor executor, android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallback carrierMessagingCallback) {
        java.util.Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.downloadMms(uri, i, uri2, new android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal(carrierMessagingCallback, executor));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        disconnect();
    }

    private final class CarrierMessagingServiceConnection implements android.content.ServiceConnection {
        private CarrierMessagingServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            android.service.carrier.CarrierMessagingServiceWrapper.this.onServiceReady(android.service.carrier.ICarrierMessagingService.Stub.asInterface(iBinder));
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
        }
    }

    @android.annotation.SystemApi
    public interface CarrierMessagingCallback {
        default void onReceiveSmsComplete(int i) {
        }

        default void onSendSmsComplete(int i, int i2) {
        }

        default void onSendMultipartSmsComplete(int i, int[] iArr) {
        }

        default void onSendMmsComplete(int i, byte[] bArr) {
        }

        default void onDownloadMmsComplete(int i) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class CarrierMessagingCallbackInternal extends android.service.carrier.ICarrierMessagingCallback.Stub {
        final android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallback mCarrierMessagingCallback;
        final java.util.concurrent.Executor mExecutor;

        CarrierMessagingCallbackInternal(android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallback carrierMessagingCallback, java.util.concurrent.Executor executor) {
            this.mCarrierMessagingCallback = carrierMessagingCallback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFilterComplete$0(int i) {
            this.mCarrierMessagingCallback.onReceiveSmsComplete(i);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onFilterComplete(final int i) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onFilterComplete$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSendSmsComplete$1(int i, int i2) {
            this.mCarrierMessagingCallback.onSendSmsComplete(i, i2);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onSendSmsComplete(final int i, final int i2) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onSendSmsComplete$1(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSendMultipartSmsComplete$2(int i, int[] iArr) {
            this.mCarrierMessagingCallback.onSendMultipartSmsComplete(i, iArr);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onSendMultipartSmsComplete(final int i, final int[] iArr) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onSendMultipartSmsComplete$2(i, iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSendMmsComplete$3(int i, byte[] bArr) {
            this.mCarrierMessagingCallback.onSendMmsComplete(i, bArr);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onSendMmsComplete(final int i, final byte[] bArr) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onSendMmsComplete$3(i, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDownloadMmsComplete$4(int i) {
            this.mCarrierMessagingCallback.onDownloadMmsComplete(i);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onDownloadMmsComplete(final int i) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.carrier.CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onDownloadMmsComplete$4(i);
                }
            });
        }
    }
}
