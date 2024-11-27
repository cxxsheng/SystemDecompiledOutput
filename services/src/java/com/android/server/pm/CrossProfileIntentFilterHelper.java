package com.android.server.pm;

/* loaded from: classes2.dex */
public class CrossProfileIntentFilterHelper {
    private final android.content.Context mContext;
    private final com.android.server.pm.PackageManagerTracedLock mLock;
    private final com.android.server.pm.Settings mSettings;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;
    private final com.android.server.pm.UserManagerService mUserManagerService;

    public CrossProfileIntentFilterHelper(com.android.server.pm.Settings settings, com.android.server.pm.UserManagerService userManagerService, com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock, com.android.server.pm.UserManagerInternal userManagerInternal, android.content.Context context) {
        this.mSettings = settings;
        this.mUserManagerService = userManagerService;
        this.mLock = packageManagerTracedLock;
        this.mContext = context;
        this.mUserManagerInternal = userManagerInternal;
    }

    public void updateDefaultCrossProfileIntentFilter() {
        int profileParentId;
        for (android.content.pm.UserInfo userInfo : this.mUserManagerInternal.getUsers(false)) {
            android.content.pm.UserProperties userProperties = this.mUserManagerInternal.getUserProperties(userInfo.id);
            if (userProperties != null && userProperties.getUpdateCrossProfileIntentFiltersOnOTA() && (profileParentId = this.mUserManagerInternal.getProfileParentId(userInfo.id)) != userInfo.id) {
                clearCrossProfileIntentFilters(userInfo.id, this.mContext.getOpPackageName(), java.lang.Integer.valueOf(profileParentId));
                clearCrossProfileIntentFilters(profileParentId, this.mContext.getOpPackageName(), java.lang.Integer.valueOf(userInfo.id));
                this.mUserManagerInternal.setDefaultCrossProfileIntentFilters(profileParentId, userInfo.id);
            }
        }
    }

    public void clearCrossProfileIntentFilters(int i, java.lang.String str, java.lang.Integer num) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.CrossProfileIntentResolver editCrossProfileIntentResolverLPw = this.mSettings.editCrossProfileIntentResolverLPw(i);
                java.util.Iterator it = new android.util.ArraySet(editCrossProfileIntentResolverLPw.filterSet()).iterator();
                while (it.hasNext()) {
                    com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter = (com.android.server.pm.CrossProfileIntentFilter) it.next();
                    if (crossProfileIntentFilter.getOwnerPackage().equals(str)) {
                        if (num != null && crossProfileIntentFilter.mTargetUserId != num.intValue()) {
                        }
                        if (this.mUserManagerService.isCrossProfileIntentFilterAccessible(i, crossProfileIntentFilter.mTargetUserId, false)) {
                            editCrossProfileIntentResolverLPw.removeFilter((com.android.server.pm.CrossProfileIntentResolver) crossProfileIntentFilter);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }
}
