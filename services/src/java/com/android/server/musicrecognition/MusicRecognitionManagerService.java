package com.android.server.musicrecognition;

/* loaded from: classes2.dex */
public class MusicRecognitionManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.musicrecognition.MusicRecognitionManagerService, com.android.server.musicrecognition.MusicRecognitionManagerPerUserService> {
    private static final int MAX_TEMP_SERVICE_SUBSTITUTION_DURATION_MS = 60000;
    private static final java.lang.String TAG = com.android.server.musicrecognition.MusicRecognitionManagerService.class.getSimpleName();
    final java.util.concurrent.ExecutorService mExecutorService;
    private com.android.server.musicrecognition.MusicRecognitionManagerService.MusicRecognitionManagerStub mMusicRecognitionManagerStub;

    public MusicRecognitionManagerService(@android.annotation.NonNull android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultDisplayCompatHostActivity), null);
        this.mExecutorService = java.util.concurrent.Executors.newCachedThreadPool();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    @android.annotation.Nullable
    public com.android.server.musicrecognition.MusicRecognitionManagerPerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.musicrecognition.MusicRecognitionManagerPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mMusicRecognitionManagerStub = new com.android.server.musicrecognition.MusicRecognitionManagerService.MusicRecognitionManagerStub();
        publishBinderService("music_recognition", this.mMusicRecognitionManagerStub);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceCaller(java.lang.String str) {
        if (getContext().checkCallingPermission("android.permission.MANAGE_MUSIC_RECOGNITION") == 0) {
            return;
        }
        throw new java.lang.SecurityException("Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " doesn't hold android.permission.MANAGE_MUSIC_RECOGNITION");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_MUSIC_RECOGNITION", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return 60000;
    }

    final class MusicRecognitionManagerStub extends android.media.musicrecognition.IMusicRecognitionManager.Stub {
        MusicRecognitionManagerStub() {
        }

        public void beginRecognition(@android.annotation.NonNull android.media.musicrecognition.RecognitionRequest recognitionRequest, @android.annotation.NonNull android.os.IBinder iBinder) {
            com.android.server.musicrecognition.MusicRecognitionManagerService.this.enforceCaller("beginRecognition");
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.musicrecognition.MusicRecognitionManagerService.this).mLock) {
                try {
                    int callingUserId = android.os.UserHandle.getCallingUserId();
                    com.android.server.musicrecognition.MusicRecognitionManagerPerUserService musicRecognitionManagerPerUserService = (com.android.server.musicrecognition.MusicRecognitionManagerPerUserService) com.android.server.musicrecognition.MusicRecognitionManagerService.this.getServiceForUserLocked(callingUserId);
                    if (musicRecognitionManagerPerUserService == null || (!isDefaultServiceLocked(callingUserId) && !isCalledByServiceAppLocked("beginRecognition"))) {
                        try {
                            android.media.musicrecognition.IMusicRecognitionManagerCallback.Stub.asInterface(iBinder).onRecognitionFailed(3);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                    musicRecognitionManagerPerUserService.beginRecognitionLocked(recognitionRequest, iBinder);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            new com.android.server.musicrecognition.MusicRecognitionManagerServiceShellCommand(com.android.server.musicrecognition.MusicRecognitionManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean isDefaultServiceLocked(int i) {
            java.lang.String defaultServiceName = ((com.android.server.infra.AbstractMasterSystemService) com.android.server.musicrecognition.MusicRecognitionManagerService.this).mServiceNameResolver.getDefaultServiceName(i);
            if (defaultServiceName == null) {
                return false;
            }
            return defaultServiceName.equals(((com.android.server.infra.AbstractMasterSystemService) com.android.server.musicrecognition.MusicRecognitionManagerService.this).mServiceNameResolver.getServiceName(i));
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean isCalledByServiceAppLocked(@android.annotation.NonNull java.lang.String str) {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            int callingUid = android.os.Binder.getCallingUid();
            java.lang.String serviceName = ((com.android.server.infra.AbstractMasterSystemService) com.android.server.musicrecognition.MusicRecognitionManagerService.this).mServiceNameResolver.getServiceName(callingUserId);
            if (serviceName == null) {
                android.util.Slog.e(com.android.server.musicrecognition.MusicRecognitionManagerService.TAG, str + ": called by UID " + callingUid + ", but there's no service set for user " + callingUserId);
                return false;
            }
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(serviceName);
            if (unflattenFromString == null) {
                android.util.Slog.w(com.android.server.musicrecognition.MusicRecognitionManagerService.TAG, str + ": invalid service name: " + serviceName);
                return false;
            }
            try {
                int packageUidAsUser = com.android.server.musicrecognition.MusicRecognitionManagerService.this.getContext().getPackageManager().getPackageUidAsUser(unflattenFromString.getPackageName(), android.os.UserHandle.getCallingUserId());
                if (callingUid != packageUidAsUser) {
                    android.util.Slog.e(com.android.server.musicrecognition.MusicRecognitionManagerService.TAG, str + ": called by UID " + callingUid + ", but service UID is " + packageUidAsUser);
                    return false;
                }
                return true;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.w(com.android.server.musicrecognition.MusicRecognitionManagerService.TAG, str + ": could not verify UID for " + serviceName);
                return false;
            }
        }
    }
}
