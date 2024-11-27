package com.android.server.translation;

/* loaded from: classes2.dex */
public final class TranslationManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.translation.TranslationManagerService, com.android.server.translation.TranslationManagerServiceImpl> {
    private static final int MAX_TEMP_SERVICE_SUBSTITUTION_DURATION_MS = 120000;
    private static final java.lang.String TAG = "TranslationManagerService";

    public TranslationManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultQrCodeComponent), null, 4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.translation.TranslationManagerServiceImpl newServiceLocked(int i, boolean z) {
        return new com.android.server.translation.TranslationManagerServiceImpl(this, this.mLock, i, z);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_UI_TRANSLATION", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return MAX_TEMP_SERVICE_SUBSTITUTION_DURATION_MS;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
        super.dumpLocked(str, printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceCallerHasPermission(java.lang.String str) {
        getContext().enforceCallingPermission(str, "Permission Denial from pid =" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " doesn't hold " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isDefaultServiceLocked(int i) {
        java.lang.String defaultServiceName = this.mServiceNameResolver.getDefaultServiceName(i);
        if (defaultServiceName == null) {
            return false;
        }
        return defaultServiceName.equals(this.mServiceNameResolver.getServiceName(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isCalledByServiceAppLocked(int i, @android.annotation.NonNull java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        java.lang.String serviceName = this.mServiceNameResolver.getServiceName(i);
        if (serviceName == null) {
            android.util.Slog.e(TAG, str + ": called by UID " + callingUid + ", but there's no service set for user " + i);
            return false;
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(serviceName);
        if (unflattenFromString == null) {
            android.util.Slog.w(TAG, str + ": invalid service name: " + serviceName);
            return false;
        }
        try {
            int packageUidAsUser = getContext().getPackageManager().getPackageUidAsUser(unflattenFromString.getPackageName(), i);
            if (callingUid != packageUidAsUser) {
                android.util.Slog.e(TAG, str + ": called by UID " + callingUid + ", but service UID is " + packageUidAsUser);
                return false;
            }
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, str + ": could not verify UID for " + serviceName);
            return false;
        }
    }

    final class TranslationManagerServiceStub extends android.view.translation.ITranslationManager.Stub {
        TranslationManagerServiceStub() {
        }

        public void onTranslationCapabilitiesRequest(int i, int i2, android.os.ResultReceiver resultReceiver, int i3) throws android.os.RemoteException {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                try {
                    com.android.server.translation.TranslationManagerServiceImpl translationManagerServiceImpl = (com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(i3);
                    if (translationManagerServiceImpl == null || (!com.android.server.translation.TranslationManagerService.this.isDefaultServiceLocked(i3) && !com.android.server.translation.TranslationManagerService.this.isCalledByServiceAppLocked(i3, "getTranslationCapabilities"))) {
                        android.util.Slog.v(com.android.server.translation.TranslationManagerService.TAG, "onGetTranslationCapabilitiesLocked(): no service for " + i3);
                        resultReceiver.send(2, null);
                    }
                    translationManagerServiceImpl.onTranslationCapabilitiesRequestLocked(i, i2, resultReceiver);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void registerTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback, int i) {
            com.android.server.translation.TranslationManagerServiceImpl translationManagerServiceImpl;
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                translationManagerServiceImpl = (com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(i);
            }
            if (translationManagerServiceImpl != null) {
                translationManagerServiceImpl.registerTranslationCapabilityCallback(iRemoteCallback, android.os.Binder.getCallingUid());
            }
        }

        public void unregisterTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback, int i) {
            com.android.server.translation.TranslationManagerServiceImpl translationManagerServiceImpl;
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                translationManagerServiceImpl = (com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(i);
            }
            if (translationManagerServiceImpl != null) {
                translationManagerServiceImpl.unregisterTranslationCapabilityCallback(iRemoteCallback);
            }
        }

        public void onSessionCreated(android.view.translation.TranslationContext translationContext, int i, com.android.internal.os.IResultReceiver iResultReceiver, int i2) throws android.os.RemoteException {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                try {
                    com.android.server.translation.TranslationManagerServiceImpl translationManagerServiceImpl = (com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(i2);
                    if (translationManagerServiceImpl == null || (!com.android.server.translation.TranslationManagerService.this.isDefaultServiceLocked(i2) && !com.android.server.translation.TranslationManagerService.this.isCalledByServiceAppLocked(i2, "onSessionCreated"))) {
                        android.util.Slog.v(com.android.server.translation.TranslationManagerService.TAG, "onSessionCreated(): no service for " + i2);
                        iResultReceiver.send(2, (android.os.Bundle) null);
                    }
                    translationManagerServiceImpl.onSessionCreatedLocked(translationContext, i, iResultReceiver);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void updateUiTranslationState(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.os.IBinder iBinder, int i2, android.view.translation.UiTranslationSpec uiTranslationSpec, int i3) {
            com.android.server.translation.TranslationManagerService.this.enforceCallerHasPermission("android.permission.MANAGE_UI_TRANSLATION");
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                try {
                    com.android.server.translation.TranslationManagerServiceImpl translationManagerServiceImpl = (com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(i3);
                    if (translationManagerServiceImpl != null) {
                        if (!com.android.server.translation.TranslationManagerService.this.isDefaultServiceLocked(i3)) {
                            if (com.android.server.translation.TranslationManagerService.this.isCalledByServiceAppLocked(i3, "updateUiTranslationState")) {
                            }
                        }
                        translationManagerServiceImpl.updateUiTranslationStateLocked(i, translationSpec, translationSpec2, list, iBinder, i2, uiTranslationSpec);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void registerUiTranslationStateCallback(android.os.IRemoteCallback iRemoteCallback, int i) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                try {
                    com.android.server.translation.TranslationManagerServiceImpl translationManagerServiceImpl = (com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(i);
                    if (translationManagerServiceImpl != null) {
                        translationManagerServiceImpl.registerUiTranslationStateCallbackLocked(iRemoteCallback, android.os.Binder.getCallingUid());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void unregisterUiTranslationStateCallback(android.os.IRemoteCallback iRemoteCallback, int i) {
            com.android.server.translation.TranslationManagerServiceImpl translationManagerServiceImpl;
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                translationManagerServiceImpl = (com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(i);
            }
            if (translationManagerServiceImpl != null) {
                translationManagerServiceImpl.unregisterUiTranslationStateCallback(iRemoteCallback);
            }
        }

        public void onTranslationFinished(boolean z, android.os.IBinder iBinder, android.content.ComponentName componentName, int i) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                ((com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(i)).onTranslationFinishedLocked(z, iBinder, componentName);
            }
        }

        public void getServiceSettingsActivity(com.android.internal.os.IResultReceiver iResultReceiver, int i) {
            com.android.server.translation.TranslationManagerServiceImpl translationManagerServiceImpl;
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                translationManagerServiceImpl = (com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(i);
            }
            if (translationManagerServiceImpl == null) {
                try {
                    iResultReceiver.send(2, (android.os.Bundle) null);
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.translation.TranslationManagerService.TAG, "Unable to send getServiceSettingsActivity(): " + e);
                    return;
                }
            }
            android.content.ComponentName serviceSettingsActivityLocked = translationManagerServiceImpl.getServiceSettingsActivityLocked();
            if (serviceSettingsActivityLocked == null) {
                try {
                    iResultReceiver.send(1, (android.os.Bundle) null);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.w(com.android.server.translation.TranslationManagerService.TAG, "Unable to send getServiceSettingsActivity(): " + e2);
                }
            }
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(serviceSettingsActivityLocked);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    iResultReceiver.send(1, com.android.internal.util.SyncResultReceiver.bundleFor(android.app.PendingIntent.getActivityAsUser(com.android.server.translation.TranslationManagerService.this.getContext(), 0, intent, 67108864, null, new android.os.UserHandle(i))));
                } catch (android.os.RemoteException e3) {
                    android.util.Slog.w(com.android.server.translation.TranslationManagerService.TAG, "Unable to send getServiceSettingsActivity(): " + e3);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.translation.TranslationManagerService.this.getContext(), com.android.server.translation.TranslationManagerService.TAG, printWriter)) {
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.translation.TranslationManagerService.this).mLock) {
                    try {
                        com.android.server.translation.TranslationManagerService.this.dumpLocked("", printWriter);
                        com.android.server.translation.TranslationManagerServiceImpl translationManagerServiceImpl = (com.android.server.translation.TranslationManagerServiceImpl) com.android.server.translation.TranslationManagerService.this.getServiceForUserLocked(android.os.UserHandle.getCallingUserId());
                        if (translationManagerServiceImpl != null) {
                            translationManagerServiceImpl.dumpLocked("  ", fileDescriptor, printWriter);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            new com.android.server.translation.TranslationManagerServiceShellCommand(com.android.server.translation.TranslationManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("translation", new com.android.server.translation.TranslationManagerService.TranslationManagerServiceStub());
    }
}
