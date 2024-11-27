package com.android.server.smartspace;

/* loaded from: classes2.dex */
public class SmartspaceManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.smartspace.SmartspaceManagerService, com.android.server.smartspace.SmartspacePerUserService> {
    private static final boolean DEBUG = false;
    private static final int MAX_TEMP_SERVICE_DURATION_MS = 120000;
    private static final java.lang.String TAG = com.android.server.smartspace.SmartspaceManagerService.class.getSimpleName();
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;

    public SmartspaceManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultOnDeviceSandboxedInferenceService), null, 17);
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.smartspace.SmartspacePerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.smartspace.SmartspacePerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("smartspace", new com.android.server.smartspace.SmartspaceManagerService.SmartspaceManagerStub());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_SMARTSPACE", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageUpdatedLocked(int i) {
        com.android.server.smartspace.SmartspacePerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
        if (peekServiceForUserLocked != null) {
            peekServiceForUserLocked.onPackageUpdatedLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageRestartedLocked(int i) {
        com.android.server.smartspace.SmartspacePerUserService peekServiceForUserLocked = peekServiceForUserLocked(i);
        if (peekServiceForUserLocked != null) {
            peekServiceForUserLocked.onPackageRestartedLocked();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return MAX_TEMP_SERVICE_DURATION_MS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SmartspaceManagerStub extends android.app.smartspace.ISmartspaceManager.Stub {
        private SmartspaceManagerStub() {
        }

        public void createSmartspaceSession(@android.annotation.NonNull final android.app.smartspace.SmartspaceConfig smartspaceConfig, @android.annotation.NonNull final android.app.smartspace.SmartspaceSessionId smartspaceSessionId, @android.annotation.NonNull final android.os.IBinder iBinder) {
            runForUserLocked("createSmartspaceSession", smartspaceSessionId, new java.util.function.Consumer() { // from class: com.android.server.smartspace.SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.smartspace.SmartspacePerUserService) obj).onCreateSmartspaceSessionLocked(smartspaceConfig, smartspaceSessionId, iBinder);
                }
            });
        }

        public void notifySmartspaceEvent(final android.app.smartspace.SmartspaceSessionId smartspaceSessionId, final android.app.smartspace.SmartspaceTargetEvent smartspaceTargetEvent) {
            runForUserLocked("notifySmartspaceEvent", smartspaceSessionId, new java.util.function.Consumer() { // from class: com.android.server.smartspace.SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.smartspace.SmartspacePerUserService) obj).notifySmartspaceEventLocked(smartspaceSessionId, smartspaceTargetEvent);
                }
            });
        }

        public void requestSmartspaceUpdate(final android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
            runForUserLocked("requestSmartspaceUpdate", smartspaceSessionId, new java.util.function.Consumer() { // from class: com.android.server.smartspace.SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.smartspace.SmartspacePerUserService) obj).requestSmartspaceUpdateLocked(smartspaceSessionId);
                }
            });
        }

        public void registerSmartspaceUpdates(@android.annotation.NonNull final android.app.smartspace.SmartspaceSessionId smartspaceSessionId, @android.annotation.NonNull final android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
            runForUserLocked("registerSmartspaceUpdates", smartspaceSessionId, new java.util.function.Consumer() { // from class: com.android.server.smartspace.SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.smartspace.SmartspacePerUserService) obj).registerSmartspaceUpdatesLocked(smartspaceSessionId, iSmartspaceCallback);
                }
            });
        }

        public void unregisterSmartspaceUpdates(final android.app.smartspace.SmartspaceSessionId smartspaceSessionId, final android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
            runForUserLocked("unregisterSmartspaceUpdates", smartspaceSessionId, new java.util.function.Consumer() { // from class: com.android.server.smartspace.SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.smartspace.SmartspacePerUserService) obj).unregisterSmartspaceUpdatesLocked(smartspaceSessionId, iSmartspaceCallback);
                }
            });
        }

        public void destroySmartspaceSession(@android.annotation.NonNull final android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
            runForUserLocked("destroySmartspaceSession", smartspaceSessionId, new java.util.function.Consumer() { // from class: com.android.server.smartspace.SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.smartspace.SmartspacePerUserService) obj).onDestroyLocked(smartspaceSessionId);
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) {
            new com.android.server.smartspace.SmartspaceManagerServiceShellCommand(com.android.server.smartspace.SmartspaceManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        private void runForUserLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.app.smartspace.SmartspaceSessionId smartspaceSessionId, @android.annotation.NonNull java.util.function.Consumer<com.android.server.smartspace.SmartspacePerUserService> consumer) {
            int handleIncomingUser = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), smartspaceSessionId.getUserHandle().getIdentifier(), false, 0, (java.lang.String) null, (java.lang.String) null);
            android.content.Context context = com.android.server.smartspace.SmartspaceManagerService.this.getContext();
            if (context.checkCallingPermission("android.permission.MANAGE_SMARTSPACE") != 0 && ((!android.app.smartspace.flags.Flags.accessSmartspace() || context.checkCallingPermission("android.permission.ACCESS_SMARTSPACE") != 0) && !((com.android.server.infra.AbstractMasterSystemService) com.android.server.smartspace.SmartspaceManagerService.this).mServiceNameResolver.isTemporary(handleIncomingUser) && !com.android.server.smartspace.SmartspaceManagerService.this.mActivityTaskManagerInternal.isCallerRecents(android.os.Binder.getCallingUid()))) {
                java.lang.String str2 = "Permission Denial: Cannot call " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid();
                android.util.Slog.w(com.android.server.smartspace.SmartspaceManagerService.TAG, str2);
                throw new java.lang.SecurityException(str2);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.smartspace.SmartspaceManagerService.this).mLock) {
                    consumer.accept((com.android.server.smartspace.SmartspacePerUserService) com.android.server.smartspace.SmartspaceManagerService.this.getServiceForUserLocked(handleIncomingUser));
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
