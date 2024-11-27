package com.android.server.pm;

/* loaded from: classes2.dex */
public class UserNeedsBadgingCache {
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mUserCache = new android.util.SparseBooleanArray();

    @android.annotation.NonNull
    private final com.android.server.pm.UserManagerService mUserManager;

    public UserNeedsBadgingCache(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
        this.mUserManager = userManagerService;
    }

    public void delete(int i) {
        synchronized (this.mLock) {
            this.mUserCache.delete(i);
        }
    }

    public boolean get(int i) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mUserCache.indexOfKey(i);
                if (indexOfKey >= 0) {
                    return this.mUserCache.valueAt(indexOfKey);
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(i);
                    boolean z = userInfo != null && userInfo.isManagedProfile();
                    synchronized (this.mLock) {
                        this.mUserCache.put(i, z);
                    }
                    return z;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
