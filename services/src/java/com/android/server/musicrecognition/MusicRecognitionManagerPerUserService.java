package com.android.server.musicrecognition;

/* loaded from: classes2.dex */
public final class MusicRecognitionManagerPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.musicrecognition.MusicRecognitionManagerPerUserService, com.android.server.musicrecognition.MusicRecognitionManagerService> implements com.android.server.musicrecognition.RemoteMusicRecognitionService.Callbacks {
    private static final int BYTES_PER_SAMPLE = 2;
    private static final java.lang.String KEY_MUSIC_RECOGNITION_SERVICE_ATTRIBUTION_TAG = "android.media.musicrecognition.attributiontag";
    private static final int MAX_STREAMING_SECONDS = 24;
    private static final java.lang.String MUSIC_RECOGNITION_MANAGER_ATTRIBUTION_TAG = "MusicRecognitionManagerService";
    private static final java.lang.String TAG = com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.class.getSimpleName();
    private final android.app.AppOpsManager mAppOpsManager;
    private final java.lang.String mAttributionMessage;
    private java.util.concurrent.CompletableFuture<java.lang.String> mAttributionTagFuture;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.musicrecognition.RemoteMusicRecognitionService mRemoteService;
    private android.content.pm.ServiceInfo mServiceInfo;

    MusicRecognitionManagerPerUserService(@android.annotation.NonNull com.android.server.musicrecognition.MusicRecognitionManagerService musicRecognitionManagerService, @android.annotation.NonNull java.lang.Object obj, int i) {
        super(musicRecognitionManagerService, obj, i);
        this.mAppOpsManager = (android.app.AppOpsManager) getContext().createAttributionContext(MUSIC_RECOGNITION_MANAGER_ATTRIBUTION_TAG).getSystemService(android.app.AppOpsManager.class);
        this.mAttributionMessage = java.lang.String.format("MusicRecognitionManager.invokedByUid.%s", java.lang.Integer.valueOf(i));
        this.mAttributionTagFuture = null;
        this.mServiceInfo = null;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
            if (!"android.permission.BIND_MUSIC_RECOGNITION_SERVICE".equals(serviceInfo.permission)) {
                android.util.Slog.w(TAG, "MusicRecognitionService from '" + serviceInfo.packageName + "' does not require permission android.permission.BIND_MUSIC_RECOGNITION_SERVICE");
                throw new java.lang.SecurityException("Service does not require permission android.permission.BIND_MUSIC_RECOGNITION_SERVICE");
            }
            return serviceInfo;
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.musicrecognition.RemoteMusicRecognitionService ensureRemoteServiceLocked(android.media.musicrecognition.IMusicRecognitionManagerCallback iMusicRecognitionManagerCallback) {
        if (this.mRemoteService == null) {
            java.lang.String componentNameLocked = getComponentNameLocked();
            if (componentNameLocked == null) {
                if (((com.android.server.musicrecognition.MusicRecognitionManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "ensureRemoteServiceLocked(): not set");
                }
                return null;
            }
            this.mRemoteService = new com.android.server.musicrecognition.RemoteMusicRecognitionService(getContext(), android.content.ComponentName.unflattenFromString(componentNameLocked), this.mUserId, this, new com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.MusicRecognitionServiceCallback(iMusicRecognitionManagerCallback), ((com.android.server.musicrecognition.MusicRecognitionManagerService) this.mMaster).isBindInstantServiceAllowed(), ((com.android.server.musicrecognition.MusicRecognitionManagerService) this.mMaster).verbose);
            try {
                this.mServiceInfo = getContext().getPackageManager().getServiceInfo(this.mRemoteService.getComponentName(), 128);
                this.mAttributionTagFuture = this.mRemoteService.getAttributionTag();
                android.util.Slog.i(TAG, "Remote service bound: " + this.mRemoteService.getComponentName());
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(TAG, "Service was not found.", e);
            }
        }
        return this.mRemoteService;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void beginRecognitionLocked(@android.annotation.NonNull final android.media.musicrecognition.RecognitionRequest recognitionRequest, @android.annotation.NonNull android.os.IBinder iBinder) {
        final android.media.musicrecognition.IMusicRecognitionManagerCallback asInterface = android.media.musicrecognition.IMusicRecognitionManagerCallback.Stub.asInterface(iBinder);
        this.mRemoteService = ensureRemoteServiceLocked(asInterface);
        if (this.mRemoteService == null) {
            try {
                asInterface.onRecognitionFailed(3);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        android.util.Pair<android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor> createPipe = createPipe();
        if (createPipe == null) {
            try {
                asInterface.onRecognitionFailed(7);
            } catch (android.os.RemoteException e2) {
            }
        } else {
            final android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) createPipe.second;
            android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) createPipe.first;
            this.mAttributionTagFuture.thenAcceptAsync(new java.util.function.Consumer() { // from class: com.android.server.musicrecognition.MusicRecognitionManagerPerUserService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.this.lambda$beginRecognitionLocked$0(recognitionRequest, asInterface, parcelFileDescriptor, (java.lang.String) obj);
                }
            }, (java.util.concurrent.Executor) ((com.android.server.musicrecognition.MusicRecognitionManagerService) this.mMaster).mExecutorService);
            this.mRemoteService.onAudioStreamStarted(parcelFileDescriptor2, recognitionRequest.getAudioFormat());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: streamAudio, reason: merged with bridge method [inline-methods] */
    public void lambda$beginRecognitionLocked$0(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull android.media.musicrecognition.RecognitionRequest recognitionRequest, android.media.musicrecognition.IMusicRecognitionManagerCallback iMusicRecognitionManagerCallback, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        int min = java.lang.Math.min(recognitionRequest.getMaxAudioLengthSeconds(), 24);
        if (min <= 0) {
            android.util.Slog.i(TAG, "No audio requested. Closing stream.");
            try {
                parcelFileDescriptor.close();
                iMusicRecognitionManagerCallback.onAudioStreamClosed();
                return;
            } catch (android.os.RemoteException e) {
                return;
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Problem closing stream.", e2);
                return;
            }
        }
        try {
            startRecordAudioOp(str);
            android.media.AudioRecord createAudioRecord = createAudioRecord(recognitionRequest, min);
            try {
                try {
                    try {
                        android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                        try {
                            streamAudio(recognitionRequest, min, createAudioRecord, autoCloseOutputStream);
                            autoCloseOutputStream.close();
                            createAudioRecord.release();
                            finishRecordAudioOp(str);
                            iMusicRecognitionManagerCallback.onAudioStreamClosed();
                        } catch (java.lang.Throwable th) {
                            try {
                                autoCloseOutputStream.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (android.os.RemoteException e3) {
                    }
                } catch (java.io.IOException e4) {
                    android.util.Slog.e(TAG, "Audio streaming stopped.", e4);
                    createAudioRecord.release();
                    finishRecordAudioOp(str);
                    iMusicRecognitionManagerCallback.onAudioStreamClosed();
                }
            } catch (java.lang.Throwable th3) {
                createAudioRecord.release();
                finishRecordAudioOp(str);
                try {
                    iMusicRecognitionManagerCallback.onAudioStreamClosed();
                } catch (android.os.RemoteException e5) {
                }
                throw th3;
            }
        } catch (java.lang.SecurityException e6) {
            android.util.Slog.e(TAG, "RECORD_AUDIO op not permitted on behalf of " + this.mServiceInfo.getComponentName(), e6);
            try {
                iMusicRecognitionManagerCallback.onRecognitionFailed(7);
            } catch (android.os.RemoteException e7) {
            }
        }
    }

    private void streamAudio(@android.annotation.NonNull android.media.musicrecognition.RecognitionRequest recognitionRequest, int i, android.media.AudioRecord audioRecord, java.io.OutputStream outputStream) throws java.io.IOException {
        int bufferSizeInFrames = audioRecord.getBufferSizeInFrames() / i;
        byte[] bArr = new byte[bufferSizeInFrames];
        int ignoreBeginningFrames = recognitionRequest.getIgnoreBeginningFrames() * 2;
        audioRecord.startRecording();
        int i2 = 0;
        int i3 = 0;
        while (i2 >= 0 && i3 < audioRecord.getBufferSizeInFrames() * 2 && this.mRemoteService != null) {
            i2 = audioRecord.read(bArr, 0, bufferSizeInFrames);
            if (i2 > 0) {
                i3 += i2;
                if (ignoreBeginningFrames > 0) {
                    ignoreBeginningFrames -= i2;
                    if (ignoreBeginningFrames < 0) {
                        outputStream.write(bArr, i2 + ignoreBeginningFrames, -ignoreBeginningFrames);
                    }
                } else {
                    outputStream.write(bArr);
                }
            }
        }
        android.util.Slog.i(TAG, java.lang.String.format("Streamed %s bytes from audio record", java.lang.Integer.valueOf(i3)));
    }

    final class MusicRecognitionServiceCallback extends android.media.musicrecognition.IMusicRecognitionServiceCallback.Stub {
        private final android.media.musicrecognition.IMusicRecognitionManagerCallback mClientCallback;

        private MusicRecognitionServiceCallback(android.media.musicrecognition.IMusicRecognitionManagerCallback iMusicRecognitionManagerCallback) {
            this.mClientCallback = iMusicRecognitionManagerCallback;
        }

        public void onRecognitionSucceeded(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle) {
            try {
                com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.sanitizeBundle(bundle);
                this.mClientCallback.onRecognitionSucceeded(mediaMetadata, bundle);
            } catch (android.os.RemoteException e) {
            }
            com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.this.destroyService();
        }

        public void onRecognitionFailed(int i) {
            try {
                this.mClientCallback.onRecognitionFailed(i);
            } catch (android.os.RemoteException e) {
            }
            com.android.server.musicrecognition.MusicRecognitionManagerPerUserService.this.destroyService();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.media.musicrecognition.IMusicRecognitionManagerCallback getClientCallback() {
            return this.mClientCallback;
        }
    }

    public void onServiceDied(@android.annotation.NonNull com.android.server.musicrecognition.RemoteMusicRecognitionService remoteMusicRecognitionService) {
        try {
            remoteMusicRecognitionService.getServerCallback().getClientCallback().onRecognitionFailed(5);
        } catch (android.os.RemoteException e) {
        }
        android.util.Slog.w(TAG, "remote service died: " + remoteMusicRecognitionService);
        destroyService();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void destroyService() {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteService != null) {
                    this.mRemoteService.destroy();
                    this.mRemoteService = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void startRecordAudioOp(@android.annotation.Nullable java.lang.String str) {
        android.app.AppOpsManager appOpsManager = this.mAppOpsManager;
        java.lang.String permissionToOp = android.app.AppOpsManager.permissionToOp("android.permission.RECORD_AUDIO");
        java.util.Objects.requireNonNull(permissionToOp);
        int startProxyOp = appOpsManager.startProxyOp(permissionToOp, this.mServiceInfo.applicationInfo.uid, this.mServiceInfo.packageName, str, this.mAttributionMessage);
        if (startProxyOp != 0) {
            throw new java.lang.SecurityException(java.lang.String.format("Failed to obtain RECORD_AUDIO permission (status: %d) for receiving service: %s", java.lang.Integer.valueOf(startProxyOp), this.mServiceInfo.getComponentName()));
        }
        android.util.Slog.i(TAG, java.lang.String.format("Starting audio streaming. Attributing to %s (%d) with tag '%s'", this.mServiceInfo.packageName, java.lang.Integer.valueOf(this.mServiceInfo.applicationInfo.uid), str));
    }

    private void finishRecordAudioOp(@android.annotation.Nullable java.lang.String str) {
        android.app.AppOpsManager appOpsManager = this.mAppOpsManager;
        java.lang.String permissionToOp = android.app.AppOpsManager.permissionToOp("android.permission.RECORD_AUDIO");
        java.util.Objects.requireNonNull(permissionToOp);
        appOpsManager.finishProxyOp(permissionToOp, this.mServiceInfo.applicationInfo.uid, this.mServiceInfo.packageName, str);
    }

    private static android.media.AudioRecord createAudioRecord(@android.annotation.NonNull android.media.musicrecognition.RecognitionRequest recognitionRequest, int i) {
        return new android.media.AudioRecord(recognitionRequest.getAudioAttributes(), recognitionRequest.getAudioFormat(), getBufferSizeInBytes(recognitionRequest.getAudioFormat().getSampleRate(), i), recognitionRequest.getCaptureSession());
    }

    private static int getBufferSizeInBytes(int i, int i2) {
        return i * 2 * i2;
    }

    private static android.util.Pair<android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor> createPipe() {
        try {
            android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
            if (createPipe.length != 2) {
                android.util.Slog.e(TAG, "Failed to create audio stream pipe, unexpected number of file descriptors");
                return null;
            }
            if (!createPipe[0].getFileDescriptor().valid() || !createPipe[1].getFileDescriptor().valid()) {
                android.util.Slog.e(TAG, "Failed to create audio stream pipe, didn't receive a pair of valid file descriptors.");
                return null;
            }
            return android.util.Pair.create(createPipe[0], createPipe[1]);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to create audio stream pipe", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sanitizeBundle(@android.annotation.Nullable android.os.Bundle bundle) {
        if (bundle == null) {
            return;
        }
        for (java.lang.String str : bundle.keySet()) {
            java.lang.Object obj = bundle.get(str);
            if (obj instanceof android.os.Bundle) {
                sanitizeBundle((android.os.Bundle) obj);
            } else if ((obj instanceof android.os.IBinder) || (obj instanceof android.os.ParcelFileDescriptor)) {
                bundle.remove(str);
            }
        }
    }
}
