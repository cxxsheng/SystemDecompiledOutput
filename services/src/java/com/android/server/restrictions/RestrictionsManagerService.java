package com.android.server.restrictions;

/* loaded from: classes2.dex */
public final class RestrictionsManagerService extends com.android.server.SystemService {
    static final boolean DEBUG = false;
    static final java.lang.String LOG_TAG = "RestrictionsManagerService";
    private final com.android.server.restrictions.RestrictionsManagerService.RestrictionsManagerImpl mRestrictionsManagerImpl;

    public RestrictionsManagerService(android.content.Context context) {
        super(context);
        this.mRestrictionsManagerImpl = new com.android.server.restrictions.RestrictionsManagerService.RestrictionsManagerImpl(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("restrictions", this.mRestrictionsManagerImpl);
    }

    class RestrictionsManagerImpl extends android.content.IRestrictionsManager.Stub {
        final android.content.Context mContext;
        private final android.app.admin.IDevicePolicyManager mDpm;
        private final android.app.admin.DevicePolicyManagerInternal mDpmInternal;
        private final android.os.IUserManager mUm;

        public RestrictionsManagerImpl(android.content.Context context) {
            this.mContext = context;
            this.mUm = com.android.server.restrictions.RestrictionsManagerService.this.getBinderService("user");
            this.mDpm = com.android.server.restrictions.RestrictionsManagerService.this.getBinderService("device_policy");
            this.mDpmInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.restrictions.RestrictionsManagerService.this.getLocalService(android.app.admin.DevicePolicyManagerInternal.class);
        }

        @java.lang.Deprecated
        public android.os.Bundle getApplicationRestrictions(java.lang.String str) throws android.os.RemoteException {
            return this.mUm.getApplicationRestrictions(str);
        }

        public java.util.List<android.os.Bundle> getApplicationRestrictionsPerAdminForUser(int i, java.lang.String str) throws android.os.RemoteException {
            if (this.mDpmInternal != null) {
                return this.mDpmInternal.getApplicationRestrictionsPerAdminForUser(str, i);
            }
            return new java.util.ArrayList();
        }

        public boolean hasRestrictionsProvider() throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            if (this.mDpm == null) {
                return false;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return this.mDpm.getRestrictionsProvider(callingUserId) != null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            if (this.mDpm != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.content.ComponentName restrictionsProvider = this.mDpm.getRestrictionsProvider(userId);
                    if (restrictionsProvider == null) {
                        throw new java.lang.IllegalStateException("Cannot request permission without a restrictions provider registered");
                    }
                    enforceCallerMatchesPackage(callingUid, str, "Package name does not match caller ");
                    android.content.Intent intent = new android.content.Intent("android.content.action.REQUEST_PERMISSION");
                    intent.setComponent(restrictionsProvider);
                    intent.putExtra("android.content.extra.PACKAGE_NAME", str);
                    intent.putExtra("android.content.extra.REQUEST_TYPE", str2);
                    intent.putExtra("android.content.extra.REQUEST_ID", str3);
                    intent.putExtra("android.content.extra.REQUEST_BUNDLE", persistableBundle);
                    this.mContext.sendBroadcastAsUser(intent, new android.os.UserHandle(userId));
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public android.content.Intent createLocalApprovalIntent() throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            if (this.mDpm != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.content.ComponentName restrictionsProvider = this.mDpm.getRestrictionsProvider(callingUserId);
                    if (restrictionsProvider == null) {
                        throw new java.lang.IllegalStateException("Cannot request permission without a restrictions provider registered");
                    }
                    java.lang.String packageName = restrictionsProvider.getPackageName();
                    android.content.Intent intent = new android.content.Intent("android.content.action.REQUEST_LOCAL_APPROVAL");
                    intent.setPackage(packageName);
                    android.content.pm.ResolveInfo resolveIntent = android.app.AppGlobals.getPackageManager().resolveIntent(intent, (java.lang.String) null, 0L, callingUserId);
                    if (resolveIntent != null && resolveIntent.activityInfo != null && resolveIntent.activityInfo.exported) {
                        intent.setComponent(new android.content.ComponentName(resolveIntent.activityInfo.packageName, resolveIntent.activityInfo.name));
                        return intent;
                    }
                    return null;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return null;
        }

        public void notifyPermissionResponse(java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            if (this.mDpm != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.content.ComponentName restrictionsProvider = this.mDpm.getRestrictionsProvider(userId);
                    if (restrictionsProvider == null) {
                        throw new java.lang.SecurityException("No restrictions provider registered for user");
                    }
                    enforceCallerMatchesPackage(callingUid, restrictionsProvider.getPackageName(), "Restrictions provider does not match caller ");
                    android.content.Intent intent = new android.content.Intent("android.content.action.PERMISSION_RESPONSE_RECEIVED");
                    intent.setPackage(str);
                    intent.putExtra("android.content.extra.RESPONSE_BUNDLE", persistableBundle);
                    this.mContext.sendBroadcastAsUser(intent, new android.os.UserHandle(userId));
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        private void enforceCallerMatchesPackage(int i, java.lang.String str, java.lang.String str2) {
            try {
                java.lang.String[] packagesForUid = android.app.AppGlobals.getPackageManager().getPackagesForUid(i);
                if (packagesForUid != null && !com.android.internal.util.ArrayUtils.contains(packagesForUid, str)) {
                    throw new java.lang.SecurityException(str2 + i);
                }
            } catch (android.os.RemoteException e) {
            }
        }
    }
}
