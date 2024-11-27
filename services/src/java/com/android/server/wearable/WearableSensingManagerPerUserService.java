package com.android.server.wearable;

/* loaded from: classes.dex */
final class WearableSensingManagerPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.wearable.WearableSensingManagerPerUserService, com.android.server.wearable.WearableSensingManagerService> {
    private static final java.lang.String TAG = com.android.server.wearable.WearableSensingManagerPerUserService.class.getSimpleName();
    private android.content.ComponentName mComponentName;

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.wearable.RemoteWearableSensingService mRemoteService;

    @com.android.internal.annotations.GuardedBy({"mSecureChannelLock"})
    private com.android.server.wearable.WearableSensingSecureChannel mSecureChannel;
    private final java.lang.Object mSecureChannelLock;

    @android.annotation.Nullable
    private android.service.voice.VoiceInteractionManagerInternal mVoiceInteractionManagerInternal;

    WearableSensingManagerPerUserService(@android.annotation.NonNull com.android.server.wearable.WearableSensingManagerService wearableSensingManagerService, java.lang.Object obj, int i) {
        super(wearableSensingManagerService, obj, i);
        this.mSecureChannelLock = new java.lang.Object();
    }

    public static void notifyStatusCallback(android.os.RemoteCallback remoteCallback, int i) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", i);
        remoteCallback.sendResult(bundle);
    }

    void destroyLocked() {
        android.util.Slog.d(TAG, "Trying to cancel the remote request. Reason: Service destroyed.");
        if (this.mRemoteService != null) {
            synchronized (this.mLock) {
                this.mRemoteService.unbind();
                this.mRemoteService = null;
            }
        }
        synchronized (this.mSecureChannelLock) {
            try {
                if (this.mSecureChannel != null) {
                    this.mSecureChannel.close();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void ensureRemoteServiceInitiated() {
        if (this.mRemoteService == null) {
            this.mRemoteService = new com.android.server.wearable.RemoteWearableSensingService(getContext(), this.mComponentName, getUserId());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean ensureVoiceInteractionManagerInternalInitiated() {
        if (this.mVoiceInteractionManagerInternal == null) {
            this.mVoiceInteractionManagerInternal = (android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class);
        }
        return this.mVoiceInteractionManagerInternal != null;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean setUpServiceIfNeeded() {
        if (this.mComponentName == null) {
            this.mComponentName = updateServiceInfoLocked();
        }
        if (this.mComponentName == null) {
            return false;
        }
        try {
            return android.app.AppGlobals.getPackageManager().getServiceInfo(this.mComponentName, 0L, this.mUserId) != null;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "RemoteException while setting up service");
            return false;
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 0L, this.mUserId);
            if (serviceInfo != null && !"android.permission.BIND_WEARABLE_SENSING_SERVICE".equals(serviceInfo.permission)) {
                throw new java.lang.SecurityException(java.lang.String.format("Service %s requires %s permission. Found %s permission", serviceInfo.getComponentName(), "android.permission.BIND_WEARABLE_SENSING_SERVICE", serviceInfo.permission));
            }
            return serviceInfo;
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected void dumpLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            super.dumpLocked(str, printWriter);
        }
        if (this.mRemoteService != null) {
            this.mRemoteService.dump("", new android.util.IndentingPrintWriter(printWriter, "  "));
        }
    }

    public void onProvideConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, final android.os.RemoteCallback remoteCallback) {
        android.util.Slog.i(TAG, "onProvideConnection in per user service.");
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Detection service is not available at this moment.");
                    notifyStatusCallback(remoteCallback, 3);
                    return;
                }
                synchronized (this.mSecureChannelLock) {
                    if (this.mSecureChannel != null) {
                        this.mSecureChannel.close();
                    }
                    try {
                        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
                        this.mSecureChannel = com.android.server.wearable.WearableSensingSecureChannel.create((android.companion.CompanionDeviceManager) getContext().getSystemService(android.companion.CompanionDeviceManager.class), parcelFileDescriptor, new com.android.server.wearable.WearableSensingSecureChannel.SecureTransportListener() { // from class: com.android.server.wearable.WearableSensingManagerPerUserService.1
                            @Override // com.android.server.wearable.WearableSensingSecureChannel.SecureTransportListener
                            public void onSecureTransportAvailable(android.os.ParcelFileDescriptor parcelFileDescriptor2) {
                                android.util.Slog.i(com.android.server.wearable.WearableSensingManagerPerUserService.TAG, "calling over to remote service.");
                                synchronized (com.android.server.wearable.WearableSensingManagerPerUserService.this.mLock) {
                                    com.android.server.wearable.WearableSensingManagerPerUserService.this.ensureRemoteServiceInitiated();
                                    com.android.server.wearable.WearableSensingManagerPerUserService.this.mRemoteService.provideSecureConnection(parcelFileDescriptor2, remoteCallback);
                                }
                            }

                            @Override // com.android.server.wearable.WearableSensingSecureChannel.SecureTransportListener
                            public void onError() {
                                if (android.app.wearable.Flags.enableRestartWssProcess()) {
                                    synchronized (com.android.server.wearable.WearableSensingManagerPerUserService.this.mSecureChannelLock) {
                                        try {
                                            if (com.android.server.wearable.WearableSensingManagerPerUserService.this.mSecureChannel != null && com.android.server.wearable.WearableSensingManagerPerUserService.this.mSecureChannel == atomicReference.get()) {
                                                com.android.server.wearable.WearableSensingManagerPerUserService.this.mRemoteService.killWearableSensingServiceProcess();
                                                com.android.server.wearable.WearableSensingManagerPerUserService.this.mSecureChannel = null;
                                            }
                                        } finally {
                                        }
                                    }
                                }
                                if (android.app.wearable.Flags.enableProvideWearableConnectionApi()) {
                                    com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 7);
                                }
                            }
                        });
                        atomicReference.set(this.mSecureChannel);
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Unable to create the secure channel.", e);
                        if (android.app.wearable.Flags.enableProvideWearableConnectionApi()) {
                            notifyStatusCallback(remoteCallback, 7);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onProvideDataStream(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) {
        android.util.Slog.i(TAG, "onProvideDataStream in per user service.");
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Detection service is not available at this moment.");
                    notifyStatusCallback(remoteCallback, 3);
                } else {
                    android.util.Slog.i(TAG, "calling over to remote servvice.");
                    ensureRemoteServiceInitiated();
                    this.mRemoteService.provideDataStream(parcelFileDescriptor, remoteCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onProvidedData(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.RemoteCallback remoteCallback) {
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Detection service is not available at this moment.");
                    notifyStatusCallback(remoteCallback, 3);
                } else {
                    ensureRemoteServiceInitiated();
                    if (sharedMemory != null) {
                        sharedMemory.setProtect(android.system.OsConstants.PROT_READ);
                    }
                    this.mRemoteService.provideData(persistableBundle, sharedMemory, remoteCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onRegisterDataRequestObserver(int i, android.os.RemoteCallback remoteCallback, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback2) {
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Detection service is not available at this moment.");
                    notifyStatusCallback(remoteCallback2, 3);
                } else {
                    ensureRemoteServiceInitiated();
                    this.mRemoteService.registerDataRequestObserver(i, remoteCallback, i2, str, remoteCallback2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onUnregisterDataRequestObserver(int i, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback) {
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Detection service is not available at this moment.");
                    notifyStatusCallback(remoteCallback, 3);
                } else {
                    ensureRemoteServiceInitiated();
                    this.mRemoteService.unregisterDataRequestObserver(i, i2, str, remoteCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onStartHotwordRecognition(android.content.ComponentName componentName, android.os.RemoteCallback remoteCallback) {
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Detection service is not available at this moment.");
                    notifyStatusCallback(remoteCallback, 3);
                } else if (!ensureVoiceInteractionManagerInternalInitiated()) {
                    android.util.Slog.w(TAG, "Voice interaction manager is not available at this moment.");
                    notifyStatusCallback(remoteCallback, 3);
                } else {
                    ensureRemoteServiceInitiated();
                    this.mRemoteService.startHotwordRecognition(createWearableHotwordCallback(componentName), remoteCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onStopHotwordRecognition(android.os.RemoteCallback remoteCallback) {
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Detection service is not available at this moment.");
                    notifyStatusCallback(remoteCallback, 3);
                } else {
                    ensureRemoteServiceInitiated();
                    this.mRemoteService.stopHotwordRecognition(remoteCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onValidatedByHotwordDetectionService() {
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Wearable sensing service is not available at this moment.");
                } else {
                    ensureRemoteServiceInitiated();
                    this.mRemoteService.onValidatedByHotwordDetectionService();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopActiveHotwordAudio() {
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Wearable sensing service is not available at this moment.");
                } else {
                    ensureRemoteServiceInitiated();
                    this.mRemoteService.stopActiveHotwordAudio();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.os.RemoteCallback createWearableHotwordCallback(final android.content.ComponentName componentName) {
        return new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.wearable.WearableSensingManagerPerUserService$$ExternalSyntheticLambda0
            public final void onResult(android.os.Bundle bundle) {
                com.android.server.wearable.WearableSensingManagerPerUserService.this.lambda$createWearableHotwordCallback$0(componentName, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createWearableHotwordCallback$0(android.content.ComponentName componentName, android.os.Bundle bundle) {
        android.service.voice.HotwordAudioStream hotwordAudioStream = (android.service.voice.HotwordAudioStream) bundle.getParcelable("android.app.wearable.HotwordAudioStreamBundleKey", android.service.voice.HotwordAudioStream.class);
        if (hotwordAudioStream == null) {
            android.util.Slog.w(TAG, "No hotword audio stream received, unable to process hotword.");
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mVoiceInteractionManagerInternal.startListeningFromWearable(hotwordAudioStream.getAudioStreamParcelFileDescriptor(), hotwordAudioStream.getAudioFormat(), hotwordAudioStream.getMetadata(), componentName, getUserId(), createHotwordDetectionCallback());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private android.service.voice.VoiceInteractionManagerInternal.WearableHotwordDetectionCallback createHotwordDetectionCallback() {
        return new android.service.voice.VoiceInteractionManagerInternal.WearableHotwordDetectionCallback() { // from class: com.android.server.wearable.WearableSensingManagerPerUserService.2
            public void onDetected() {
                android.util.Slog.i(com.android.server.wearable.WearableSensingManagerPerUserService.TAG, "hotwordDetectionCallback onDetected.");
                com.android.server.wearable.WearableSensingManagerPerUserService.this.onValidatedByHotwordDetectionService();
            }

            public void onRejected() {
                android.util.Slog.i(com.android.server.wearable.WearableSensingManagerPerUserService.TAG, "hotwordDetectionCallback onRejected.");
                com.android.server.wearable.WearableSensingManagerPerUserService.this.stopActiveHotwordAudio();
            }

            public void onError(java.lang.String str) {
                android.util.Slog.i(com.android.server.wearable.WearableSensingManagerPerUserService.TAG, "hotwordDetectionCallback onError. ErrorMessage: " + str);
                com.android.server.wearable.WearableSensingManagerPerUserService.this.stopActiveHotwordAudio();
            }
        };
    }
}
