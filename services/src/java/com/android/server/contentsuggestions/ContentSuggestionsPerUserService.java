package com.android.server.contentsuggestions;

/* loaded from: classes.dex */
public final class ContentSuggestionsPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.contentsuggestions.ContentSuggestionsPerUserService, com.android.server.contentsuggestions.ContentSuggestionsManagerService> {
    private static final java.lang.String TAG = com.android.server.contentsuggestions.ContentSuggestionsPerUserService.class.getSimpleName();

    @android.annotation.NonNull
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.contentsuggestions.RemoteContentSuggestionsService mRemoteService;

    ContentSuggestionsPerUserService(com.android.server.contentsuggestions.ContentSuggestionsManagerService contentSuggestionsManagerService, java.lang.Object obj, int i) {
        super(contentSuggestionsManagerService, obj, i);
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
            if (!"android.permission.BIND_CONTENT_SUGGESTIONS_SERVICE".equals(serviceInfo.permission)) {
                android.util.Slog.w(TAG, "ContentSuggestionsService from '" + serviceInfo.packageName + "' does not require permission android.permission.BIND_CONTENT_SUGGESTIONS_SERVICE");
                throw new java.lang.SecurityException("Service does not require permission android.permission.BIND_CONTENT_SUGGESTIONS_SERVICE");
            }
            return serviceInfo;
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        updateRemoteServiceLocked();
        return updateLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void provideContextImageFromBitmapLocked(@android.annotation.NonNull android.os.Bundle bundle) {
        provideContextImageLocked(-1, null, 0, bundle);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void provideContextImageLocked(int i, @android.annotation.Nullable android.hardware.HardwareBuffer hardwareBuffer, int i2, @android.annotation.NonNull android.os.Bundle bundle) {
        com.android.server.contentsuggestions.RemoteContentSuggestionsService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
        if (ensureRemoteServiceLocked != null) {
            ensureRemoteServiceLocked.provideContextImage(i, hardwareBuffer, i2, bundle);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void suggestContentSelectionsLocked(@android.annotation.NonNull android.app.contentsuggestions.SelectionsRequest selectionsRequest, @android.annotation.NonNull android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) {
        com.android.server.contentsuggestions.RemoteContentSuggestionsService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
        if (ensureRemoteServiceLocked != null) {
            ensureRemoteServiceLocked.suggestContentSelections(selectionsRequest, iSelectionsCallback);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void classifyContentSelectionsLocked(@android.annotation.NonNull android.app.contentsuggestions.ClassificationsRequest classificationsRequest, @android.annotation.NonNull android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) {
        com.android.server.contentsuggestions.RemoteContentSuggestionsService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
        if (ensureRemoteServiceLocked != null) {
            ensureRemoteServiceLocked.classifyContentSelections(classificationsRequest, iClassificationsCallback);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void notifyInteractionLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.Bundle bundle) {
        com.android.server.contentsuggestions.RemoteContentSuggestionsService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
        if (ensureRemoteServiceLocked != null) {
            ensureRemoteServiceLocked.notifyInteraction(str, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateRemoteServiceLocked() {
        if (this.mRemoteService != null) {
            this.mRemoteService.destroy();
            this.mRemoteService = null;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.contentsuggestions.RemoteContentSuggestionsService ensureRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            java.lang.String componentNameLocked = getComponentNameLocked();
            if (componentNameLocked == null) {
                if (((com.android.server.contentsuggestions.ContentSuggestionsManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "ensureRemoteServiceLocked(): not set");
                    return null;
                }
                return null;
            }
            this.mRemoteService = new com.android.server.contentsuggestions.RemoteContentSuggestionsService(getContext(), android.content.ComponentName.unflattenFromString(componentNameLocked), this.mUserId, new com.android.server.contentsuggestions.RemoteContentSuggestionsService.Callbacks() { // from class: com.android.server.contentsuggestions.ContentSuggestionsPerUserService.1
                public void onServiceDied(@android.annotation.NonNull com.android.server.contentsuggestions.RemoteContentSuggestionsService remoteContentSuggestionsService) {
                    android.util.Slog.w(com.android.server.contentsuggestions.ContentSuggestionsPerUserService.TAG, "remote content suggestions service died");
                    com.android.server.contentsuggestions.ContentSuggestionsPerUserService.this.updateRemoteServiceLocked();
                }
            }, ((com.android.server.contentsuggestions.ContentSuggestionsManagerService) this.mMaster).isBindInstantServiceAllowed(), ((com.android.server.contentsuggestions.ContentSuggestionsManagerService) this.mMaster).verbose);
        }
        return this.mRemoteService;
    }
}
