package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemUserInfoHelper extends com.android.server.location.injector.UserInfoHelper {

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.app.IActivityManager mActivityManager;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.app.ActivityManagerInternal mActivityManagerInternal;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.os.UserManager mUserManager;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private com.android.server.pm.UserManagerInternal mUserManagerInternal;

    public SystemUserInfoHelper(android.content.Context context) {
        this.mContext = context;
    }

    public synchronized void onSystemReady() {
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        java.util.Objects.requireNonNull(userManagerInternal);
        com.android.server.pm.UserManagerInternal userManagerInternal2 = userManagerInternal;
        this.mUserManagerInternal = userManagerInternal;
        this.mUserManagerInternal.addUserVisibilityListener(new com.android.server.pm.UserManagerInternal.UserVisibilityListener() { // from class: com.android.server.location.injector.SystemUserInfoHelper$$ExternalSyntheticLambda0
            @Override // com.android.server.pm.UserManagerInternal.UserVisibilityListener
            public final void onUserVisibilityChanged(int i, boolean z) {
                com.android.server.location.injector.SystemUserInfoHelper.this.lambda$onSystemReady$0(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$0(int i, boolean z) {
        dispatchOnVisibleUserChanged(i, z);
    }

    @android.annotation.Nullable
    protected final android.app.ActivityManagerInternal getActivityManagerInternal() {
        synchronized (this) {
            try {
                if (this.mActivityManagerInternal == null) {
                    this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mActivityManagerInternal;
    }

    @android.annotation.Nullable
    protected final android.app.IActivityManager getActivityManager() {
        synchronized (this) {
            try {
                if (this.mActivityManager == null) {
                    this.mActivityManager = android.app.ActivityManager.getService();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mActivityManager;
    }

    @android.annotation.Nullable
    protected final android.os.UserManager getUserManager() {
        synchronized (this) {
            try {
                if (this.mUserManager == null) {
                    this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mUserManager;
    }

    @Override // com.android.server.location.injector.UserInfoHelper
    public int[] getRunningUserIds() {
        android.app.IActivityManager activityManager = getActivityManager();
        if (activityManager != null) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    return activityManager.getRunningUserIds();
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return new int[0];
    }

    @Override // com.android.server.location.injector.UserInfoHelper
    public boolean isCurrentUserId(int i) {
        android.app.ActivityManagerInternal activityManagerInternal = getActivityManagerInternal();
        if (activityManagerInternal != null) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return activityManagerInternal.isCurrentProfile(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return false;
    }

    @Override // com.android.server.location.injector.UserInfoHelper
    public int getCurrentUserId() {
        android.app.ActivityManagerInternal activityManagerInternal = getActivityManagerInternal();
        if (activityManagerInternal != null) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return activityManagerInternal.getCurrentUserId();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return com.android.server.am.ProcessList.INVALID_ADJ;
    }

    @Override // com.android.server.location.injector.UserInfoHelper
    public boolean isVisibleUserId(int i) {
        boolean isUserVisible;
        synchronized (this) {
            com.android.internal.util.Preconditions.checkState(this.mUserManagerInternal != null);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this) {
                isUserVisible = this.mUserManagerInternal.isUserVisible(i);
            }
            return isUserVisible;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.UserInfoHelper
    protected int[] getProfileIds(int i) {
        android.os.UserManager userManager = getUserManager();
        com.android.internal.util.Preconditions.checkState(userManager != null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return userManager.getEnabledProfileIds(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.UserInfoHelper
    public void dump(java.io.FileDescriptor fileDescriptor, android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
        int[] runningUserIds = getRunningUserIds();
        if (runningUserIds.length > 1) {
            indentingPrintWriter.println("running users: u" + java.util.Arrays.toString(runningUserIds));
        }
        android.app.ActivityManagerInternal activityManagerInternal = getActivityManagerInternal();
        if (activityManagerInternal == null) {
            return;
        }
        int[] currentProfileIds = activityManagerInternal.getCurrentProfileIds();
        indentingPrintWriter.println("current users: u" + java.util.Arrays.toString(currentProfileIds));
        android.os.UserManager userManager = getUserManager();
        if (userManager != null) {
            for (int i : currentProfileIds) {
                if (userManager.hasUserRestrictionForUser("no_share_location", android.os.UserHandle.of(i))) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("u" + i + " restricted");
                    indentingPrintWriter.decreaseIndent();
                }
            }
        }
    }
}
