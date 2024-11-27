package com.android.server.rotationresolver;

/* loaded from: classes2.dex */
public class RotationResolverManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.rotationresolver.RotationResolverManagerService, com.android.server.rotationresolver.RotationResolverManagerPerUserService> {
    private static final boolean DEFAULT_SERVICE_ENABLED = true;
    private static final java.lang.String KEY_SERVICE_ENABLED = "service_enabled";
    static final int ORIENTATION_UNKNOWN = 0;
    static final int RESOLUTION_DISABLED = 6;
    static final int RESOLUTION_FAILURE = 8;
    static final int RESOLUTION_UNAVAILABLE = 7;
    private static final java.lang.String TAG = com.android.server.rotationresolver.RotationResolverManagerService.class.getSimpleName();
    private final android.content.Context mContext;
    boolean mIsServiceEnabled;
    private final android.hardware.SensorPrivacyManager mPrivacyManager;

    public RotationResolverManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultNetworkRecommendationProviderPackage), null, 68);
        this.mContext = context;
        this.mPrivacyManager = android.hardware.SensorPrivacyManager.getInstance(context);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService, com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("rotation_resolver", getContext().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.rotationresolver.RotationResolverManagerService$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.rotationresolver.RotationResolverManagerService.this.lambda$onBootPhase$0(properties);
                }
            });
            this.mIsServiceEnabled = android.provider.DeviceConfig.getBoolean("rotation_resolver", KEY_SERVICE_ENABLED, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$0(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
    }

    private void onDeviceConfigChange(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        if (set.contains(KEY_SERVICE_ENABLED)) {
            this.mIsServiceEnabled = android.provider.DeviceConfig.getBoolean("rotation_resolver", KEY_SERVICE_ENABLED, true);
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("resolver", new com.android.server.rotationresolver.RotationResolverManagerService.BinderService());
        publishLocalService(android.rotationresolver.RotationResolverInternal.class, new com.android.server.rotationresolver.RotationResolverManagerService.LocalService());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.rotationresolver.RotationResolverManagerPerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.rotationresolver.RotationResolverManagerPerUserService(this, this.mLock, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public void onServiceRemoved(com.android.server.rotationresolver.RotationResolverManagerPerUserService rotationResolverManagerPerUserService, int i) {
        synchronized (this.mLock) {
            rotationResolverManagerPerUserService.destroyLocked();
        }
    }

    public static boolean isServiceConfigured(android.content.Context context) {
        return !android.text.TextUtils.isEmpty(getServiceConfigPackage(context));
    }

    android.content.ComponentName getComponentNameShellCommand(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.rotationresolver.RotationResolverManagerPerUserService serviceForUserLocked = getServiceForUserLocked(i);
                if (serviceForUserLocked != null) {
                    return serviceForUserLocked.getComponentName();
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void resolveRotationShellCommand(int i, android.rotationresolver.RotationResolverInternal.RotationResolverCallbackInternal rotationResolverCallbackInternal, android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest) {
        synchronized (this.mLock) {
            try {
                com.android.server.rotationresolver.RotationResolverManagerPerUserService serviceForUserLocked = getServiceForUserLocked(i);
                if (serviceForUserLocked != null) {
                    serviceForUserLocked.resolveRotationLocked(rotationResolverCallbackInternal, rotationResolutionRequest, new android.os.CancellationSignal());
                } else {
                    android.util.Slog.i(TAG, "service not available for user_id: " + i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static java.lang.String getServiceConfigPackage(android.content.Context context) {
        return context.getPackageManager().getRotationResolverPackageName();
    }

    private final class LocalService extends android.rotationresolver.RotationResolverInternal {
        private LocalService() {
        }

        public boolean isRotationResolverSupported() {
            boolean z;
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.rotationresolver.RotationResolverManagerService.this).mLock) {
                z = com.android.server.rotationresolver.RotationResolverManagerService.this.mIsServiceEnabled;
            }
            return z;
        }

        public void resolveRotation(@android.annotation.NonNull android.rotationresolver.RotationResolverInternal.RotationResolverCallbackInternal rotationResolverCallbackInternal, java.lang.String str, int i, int i2, long j, @android.annotation.NonNull android.os.CancellationSignal cancellationSignal) {
            android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest;
            java.util.Objects.requireNonNull(rotationResolverCallbackInternal);
            java.util.Objects.requireNonNull(cancellationSignal);
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.rotationresolver.RotationResolverManagerService.this).mLock) {
                try {
                    boolean z = !com.android.server.rotationresolver.RotationResolverManagerService.this.mPrivacyManager.isSensorPrivacyEnabled(2);
                    if (com.android.server.rotationresolver.RotationResolverManagerService.this.mIsServiceEnabled && z) {
                        com.android.server.rotationresolver.RotationResolverManagerPerUserService rotationResolverManagerPerUserService = (com.android.server.rotationresolver.RotationResolverManagerPerUserService) com.android.server.rotationresolver.RotationResolverManagerService.this.getServiceForUserLocked(android.os.UserHandle.getCallingUserId());
                        if (str == null) {
                            rotationResolutionRequest = new android.service.rotationresolver.RotationResolutionRequest("", i2, i, true, j);
                        } else {
                            rotationResolutionRequest = new android.service.rotationresolver.RotationResolutionRequest(str, i2, i, true, j);
                        }
                        rotationResolverManagerPerUserService.resolveRotationLocked(rotationResolverCallbackInternal, rotationResolutionRequest, cancellationSignal);
                    } else {
                        if (z) {
                            android.util.Slog.w(com.android.server.rotationresolver.RotationResolverManagerService.TAG, "Rotation Resolver service is disabled.");
                        } else {
                            android.util.Slog.w(com.android.server.rotationresolver.RotationResolverManagerService.TAG, "Camera is locked by a toggle.");
                        }
                        rotationResolverCallbackInternal.onFailure(0);
                        com.android.server.rotationresolver.RotationResolverManagerService.logRotationStats(i, i2, 6);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class BinderService extends android.os.Binder {
        private BinderService() {
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.rotationresolver.RotationResolverManagerService.this.mContext, com.android.server.rotationresolver.RotationResolverManagerService.TAG, printWriter)) {
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.rotationresolver.RotationResolverManagerService.this).mLock) {
                    com.android.server.rotationresolver.RotationResolverManagerService.this.dumpLocked("", printWriter);
                }
            }
        }

        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            com.android.server.rotationresolver.RotationResolverManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_ROTATION_RESOLVER", com.android.server.rotationresolver.RotationResolverManagerService.TAG);
            new com.android.server.rotationresolver.RotationResolverShellCommand(com.android.server.rotationresolver.RotationResolverManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    static void logRotationStatsWithTimeToCalculate(int i, int i2, int i3, long j) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.AUTO_ROTATE_REPORTED, surfaceRotationToProto(i2), surfaceRotationToProto(i), i3, j);
    }

    static void logRotationStats(int i, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.AUTO_ROTATE_REPORTED, surfaceRotationToProto(i2), surfaceRotationToProto(i), i3);
    }

    static int errorCodeToProto(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
                return 0;
            case 3:
            default:
                return 8;
            case 4:
                return 7;
        }
    }

    static int surfaceRotationToProto(int i) {
        switch (i) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 5;
            default:
                return 8;
        }
    }
}
