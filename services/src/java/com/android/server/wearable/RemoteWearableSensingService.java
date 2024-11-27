package com.android.server.wearable;

/* loaded from: classes.dex */
final class RemoteWearableSensingService extends com.android.internal.infra.ServiceConnector.Impl<android.service.wearable.IWearableSensingService> {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = com.android.server.wearable.RemoteWearableSensingService.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"mSecureConnectionLock"})
    private com.android.server.wearable.RemoteWearableSensingService.SecureWearableConnectionContext mNextSecureConnectionContext;
    private final java.lang.Object mSecureConnectionLock;

    @com.android.internal.annotations.GuardedBy({"mSecureConnectionLock"})
    private boolean mSecureConnectionProvided;

    RemoteWearableSensingService(android.content.Context context, android.content.ComponentName componentName, int i) {
        super(context, new android.content.Intent("android.service.wearable.WearableSensingService").setComponent(componentName), 67112960, i, new com.android.server.ambientcontext.RemoteWearableSensingService$$ExternalSyntheticLambda1());
        this.mSecureConnectionLock = new java.lang.Object();
        this.mSecureConnectionProvided = false;
        connect();
    }

    protected long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    public void provideSecureConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) {
        if (!android.app.wearable.Flags.enableRestartWssProcess()) {
            android.util.Slog.d(TAG, "FLAG_ENABLE_RESTART_WSS_PROCESS is disabled. Do not attempt to restart the WearableSensingService process");
            provideSecureConnectionInternal(parcelFileDescriptor, remoteCallback);
            return;
        }
        synchronized (this.mSecureConnectionLock) {
            try {
                if (this.mNextSecureConnectionContext != null) {
                    android.util.Slog.i(TAG, "A new wearable connection is provided before the process restart triggered by the previous connection is complete. Discarding the previous connection.");
                    if (android.app.wearable.Flags.enableProvideWearableConnectionApi()) {
                        com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(this.mNextSecureConnectionContext.mStatusCallback, 7);
                    }
                    this.mNextSecureConnectionContext = new com.android.server.wearable.RemoteWearableSensingService.SecureWearableConnectionContext(parcelFileDescriptor, remoteCallback);
                    return;
                }
                if (!this.mSecureConnectionProvided) {
                    provideSecureConnectionInternal(parcelFileDescriptor, remoteCallback);
                    this.mSecureConnectionProvided = true;
                } else {
                    this.mNextSecureConnectionContext = new com.android.server.wearable.RemoteWearableSensingService.SecureWearableConnectionContext(parcelFileDescriptor, remoteCallback);
                    killWearableSensingServiceProcess();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void provideSecureConnectionInternal(final android.os.ParcelFileDescriptor parcelFileDescriptor, final android.os.RemoteCallback remoteCallback) {
        android.util.Slog.d(TAG, "Providing secure wearable connection.");
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda5
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.wearable.RemoteWearableSensingService.lambda$provideSecureConnectionInternal$0(parcelFileDescriptor, remoteCallback, (android.service.wearable.IWearableSensingService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$provideSecureConnectionInternal$0(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback, android.service.wearable.IWearableSensingService iWearableSensingService) throws java.lang.Exception {
        iWearableSensingService.provideSecureConnection(parcelFileDescriptor, remoteCallback);
        try {
            parcelFileDescriptor.close();
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Unable to close the local parcelFileDescriptor.", e);
        }
    }

    public void binderDied() {
        super.binderDied();
        synchronized (this.mSecureConnectionLock) {
            try {
                if (this.mNextSecureConnectionContext != null) {
                    provideSecureConnectionInternal(this.mNextSecureConnectionContext.mSecureConnection, this.mNextSecureConnectionContext.mStatusCallback);
                    this.mNextSecureConnectionContext = null;
                } else {
                    this.mSecureConnectionProvided = false;
                    android.util.Slog.w(TAG, "Binder died but there is no secure wearable connection to provide.");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void killWearableSensingServiceProcess() {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda0
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).killProcess();
            }
        });
    }

    public void provideDataStream(final android.os.ParcelFileDescriptor parcelFileDescriptor, final android.os.RemoteCallback remoteCallback) {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda7
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.wearable.RemoteWearableSensingService.lambda$provideDataStream$2(parcelFileDescriptor, remoteCallback, (android.service.wearable.IWearableSensingService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$provideDataStream$2(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback, android.service.wearable.IWearableSensingService iWearableSensingService) throws java.lang.Exception {
        iWearableSensingService.provideDataStream(parcelFileDescriptor, remoteCallback);
        try {
            parcelFileDescriptor.close();
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Unable to close the local parcelFileDescriptor.", e);
        }
    }

    public void provideData(final android.os.PersistableBundle persistableBundle, final android.os.SharedMemory sharedMemory, final android.os.RemoteCallback remoteCallback) {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda8
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).provideData(persistableBundle, sharedMemory, remoteCallback);
            }
        });
    }

    public void registerDataRequestObserver(final int i, final android.os.RemoteCallback remoteCallback, final int i2, final java.lang.String str, final android.os.RemoteCallback remoteCallback2) {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda3
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).registerDataRequestObserver(i, remoteCallback, i2, str, remoteCallback2);
            }
        });
    }

    public void unregisterDataRequestObserver(final int i, final int i2, final java.lang.String str, final android.os.RemoteCallback remoteCallback) {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda9
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).unregisterDataRequestObserver(i, i2, str, remoteCallback);
            }
        });
    }

    public void startHotwordRecognition(final android.os.RemoteCallback remoteCallback, final android.os.RemoteCallback remoteCallback2) {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda2
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).startHotwordRecognition(remoteCallback, remoteCallback2);
            }
        });
    }

    public void stopHotwordRecognition(final android.os.RemoteCallback remoteCallback) {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda4
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).stopHotwordRecognition(remoteCallback);
            }
        });
    }

    public void onValidatedByHotwordDetectionService() {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda1
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).onValidatedByHotwordDetectionService();
            }
        });
    }

    public void stopActiveHotwordAudio() {
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda6
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).stopActiveHotwordAudio();
            }
        });
    }

    private static class SecureWearableConnectionContext {
        final android.os.ParcelFileDescriptor mSecureConnection;
        final android.os.RemoteCallback mStatusCallback;

        SecureWearableConnectionContext(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) {
            this.mSecureConnection = parcelFileDescriptor;
            this.mStatusCallback = remoteCallback;
        }
    }
}
