package com.android.server.contentsuggestions;

/* loaded from: classes.dex */
public class ContentSuggestionsManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.contentsuggestions.ContentSuggestionsManagerService, com.android.server.contentsuggestions.ContentSuggestionsPerUserService> {
    private static final java.lang.String EXTRA_BITMAP = "android.contentsuggestions.extra.BITMAP";
    private static final int MAX_TEMP_SERVICE_DURATION_MS = 120000;
    private static final java.lang.String TAG = com.android.server.contentsuggestions.ContentSuggestionsManagerService.class.getSimpleName();
    private static final boolean VERBOSE = false;
    private com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;

    public ContentSuggestionsManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultAttentionService), "no_content_suggestions");
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.contentsuggestions.ContentSuggestionsPerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.contentsuggestions.ContentSuggestionsPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("content_suggestions", new com.android.server.contentsuggestions.ContentSuggestionsManagerService.ContentSuggestionsManagerStub());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_CONTENT_SUGGESTIONS", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return MAX_TEMP_SERVICE_DURATION_MS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceCaller(int i, java.lang.String str) {
        if (getContext().checkCallingPermission("android.permission.MANAGE_CONTENT_SUGGESTIONS") == 0 || this.mServiceNameResolver.isTemporary(i) || this.mActivityTaskManagerInternal.isCallerRecents(android.os.Binder.getCallingUid())) {
            return;
        }
        java.lang.String str2 = "Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " expected caller is recents";
        android.util.Slog.w(TAG, str2);
        throw new java.lang.SecurityException(str2);
    }

    private class ContentSuggestionsManagerStub extends android.app.contentsuggestions.IContentSuggestionsManager.Stub {
        private ContentSuggestionsManagerStub() {
        }

        public void provideContextBitmap(int i, @android.annotation.NonNull android.graphics.Bitmap bitmap, @android.annotation.NonNull android.os.Bundle bundle) {
            if (bitmap == null) {
                throw new java.lang.IllegalArgumentException("Expected non-null bitmap");
            }
            if (bundle == null) {
                throw new java.lang.IllegalArgumentException("Expected non-null imageContextRequestExtras");
            }
            com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.enforceCaller(android.os.UserHandle.getCallingUserId(), "provideContextBitmap");
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this).mLock) {
                try {
                    com.android.server.contentsuggestions.ContentSuggestionsPerUserService contentSuggestionsPerUserService = (com.android.server.contentsuggestions.ContentSuggestionsPerUserService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.getServiceForUserLocked(i);
                    if (contentSuggestionsPerUserService != null) {
                        bundle.putParcelable(com.android.server.contentsuggestions.ContentSuggestionsManagerService.EXTRA_BITMAP, bitmap);
                        contentSuggestionsPerUserService.provideContextImageFromBitmapLocked(bundle);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void provideContextImage(int i, int i2, @android.annotation.NonNull android.os.Bundle bundle) {
            android.hardware.HardwareBuffer hardwareBuffer;
            android.window.TaskSnapshot taskSnapshotBlocking;
            if (bundle == null) {
                throw new java.lang.IllegalArgumentException("Expected non-null imageContextRequestExtras");
            }
            com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.enforceCaller(android.os.UserHandle.getCallingUserId(), "provideContextImage");
            int i3 = 0;
            if (!bundle.containsKey(com.android.server.contentsuggestions.ContentSuggestionsManagerService.EXTRA_BITMAP) && (taskSnapshotBlocking = com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.mActivityTaskManagerInternal.getTaskSnapshotBlocking(i2, false)) != null) {
                hardwareBuffer = taskSnapshotBlocking.getHardwareBuffer();
                android.graphics.ColorSpace colorSpace = taskSnapshotBlocking.getColorSpace();
                if (colorSpace != null) {
                    i3 = colorSpace.getId();
                }
            } else {
                hardwareBuffer = null;
            }
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this).mLock) {
                try {
                    com.android.server.contentsuggestions.ContentSuggestionsPerUserService contentSuggestionsPerUserService = (com.android.server.contentsuggestions.ContentSuggestionsPerUserService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.getServiceForUserLocked(i);
                    if (contentSuggestionsPerUserService != null) {
                        contentSuggestionsPerUserService.provideContextImageLocked(i2, hardwareBuffer, i3, bundle);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void suggestContentSelections(int i, @android.annotation.NonNull android.app.contentsuggestions.SelectionsRequest selectionsRequest, @android.annotation.NonNull android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) {
            com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.enforceCaller(android.os.UserHandle.getCallingUserId(), "suggestContentSelections");
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this).mLock) {
                try {
                    com.android.server.contentsuggestions.ContentSuggestionsPerUserService contentSuggestionsPerUserService = (com.android.server.contentsuggestions.ContentSuggestionsPerUserService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.getServiceForUserLocked(i);
                    if (contentSuggestionsPerUserService != null) {
                        contentSuggestionsPerUserService.suggestContentSelectionsLocked(selectionsRequest, iSelectionsCallback);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void classifyContentSelections(int i, @android.annotation.NonNull android.app.contentsuggestions.ClassificationsRequest classificationsRequest, @android.annotation.NonNull android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) {
            com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.enforceCaller(android.os.UserHandle.getCallingUserId(), "classifyContentSelections");
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this).mLock) {
                try {
                    com.android.server.contentsuggestions.ContentSuggestionsPerUserService contentSuggestionsPerUserService = (com.android.server.contentsuggestions.ContentSuggestionsPerUserService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.getServiceForUserLocked(i);
                    if (contentSuggestionsPerUserService != null) {
                        contentSuggestionsPerUserService.classifyContentSelectionsLocked(classificationsRequest, iClassificationsCallback);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void notifyInteraction(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.Bundle bundle) {
            com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.enforceCaller(android.os.UserHandle.getCallingUserId(), "notifyInteraction");
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this).mLock) {
                try {
                    com.android.server.contentsuggestions.ContentSuggestionsPerUserService contentSuggestionsPerUserService = (com.android.server.contentsuggestions.ContentSuggestionsPerUserService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.getServiceForUserLocked(i);
                    if (contentSuggestionsPerUserService != null) {
                        contentSuggestionsPerUserService.notifyInteractionLocked(str, bundle);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void isEnabled(int i, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            boolean isDisabledLocked;
            com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.enforceCaller(android.os.UserHandle.getCallingUserId(), "isEnabled");
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.contentsuggestions.ContentSuggestionsManagerService.this).mLock) {
                isDisabledLocked = com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.isDisabledLocked(i);
            }
            iResultReceiver.send(!isDisabledLocked ? 1 : 0, (android.os.Bundle) null);
        }

        public void resetTemporaryService(int i) {
            com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.resetTemporaryService(i);
        }

        public void setTemporaryService(int i, @android.annotation.NonNull java.lang.String str, int i2) {
            com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.setTemporaryService(i, str, i2);
        }

        public void setDefaultServiceEnabled(int i, boolean z) {
            com.android.server.contentsuggestions.ContentSuggestionsManagerService.this.setDefaultServiceEnabled(i, z);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid != 2000 && callingUid != 0) {
                android.util.Slog.e(com.android.server.contentsuggestions.ContentSuggestionsManagerService.TAG, "Expected shell caller");
            } else {
                new com.android.server.contentsuggestions.ContentSuggestionsManagerServiceShellCommand(com.android.server.contentsuggestions.ContentSuggestionsManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        }
    }
}
