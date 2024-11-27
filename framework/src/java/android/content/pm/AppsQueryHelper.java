package android.content.pm;

/* loaded from: classes.dex */
public class AppsQueryHelper {
    private java.util.List<android.content.pm.ApplicationInfo> mAllApps;
    private final android.content.pm.IPackageManager mPackageManager;
    public static int GET_NON_LAUNCHABLE_APPS = 1;
    public static int GET_APPS_WITH_INTERACT_ACROSS_USERS_PERM = 2;
    public static int GET_IMES = 4;
    public static int GET_REQUIRED_FOR_SYSTEM_USER = 8;

    public AppsQueryHelper(android.content.pm.IPackageManager iPackageManager) {
        this.mPackageManager = iPackageManager;
    }

    public AppsQueryHelper() {
        this(android.app.AppGlobals.getPackageManager());
    }

    public java.util.List<java.lang.String> queryApps(int i, boolean z, android.os.UserHandle userHandle) {
        int i2 = 0;
        boolean z2 = (GET_NON_LAUNCHABLE_APPS & i) > 0;
        boolean z3 = (GET_APPS_WITH_INTERACT_ACROSS_USERS_PERM & i) > 0;
        boolean z4 = (GET_IMES & i) > 0;
        boolean z5 = (GET_REQUIRED_FOR_SYSTEM_USER & i) > 0;
        if (this.mAllApps == null) {
            this.mAllApps = getAllApps(userHandle.getIdentifier());
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (i == 0) {
            int size = this.mAllApps.size();
            while (i2 < size) {
                android.content.pm.ApplicationInfo applicationInfo = this.mAllApps.get(i2);
                if (!z || applicationInfo.isSystemApp()) {
                    arrayList.add(applicationInfo.packageName);
                }
                i2++;
            }
            return arrayList;
        }
        if (z2) {
            java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser = queryIntentActivitiesAsUser(new android.content.Intent(android.content.Intent.ACTION_MAIN).addCategory(android.content.Intent.CATEGORY_LAUNCHER), userHandle.getIdentifier());
            android.util.ArraySet arraySet = new android.util.ArraySet();
            int size2 = queryIntentActivitiesAsUser.size();
            for (int i3 = 0; i3 < size2; i3++) {
                arraySet.add(queryIntentActivitiesAsUser.get(i3).activityInfo.packageName);
            }
            int size3 = this.mAllApps.size();
            for (int i4 = 0; i4 < size3; i4++) {
                android.content.pm.ApplicationInfo applicationInfo2 = this.mAllApps.get(i4);
                if (!z || applicationInfo2.isSystemApp()) {
                    java.lang.String str = applicationInfo2.packageName;
                    if (!arraySet.contains(str)) {
                        arrayList.add(str);
                    }
                }
            }
        }
        if (z3) {
            java.util.List<android.content.pm.PackageInfo> packagesHoldingPermission = getPackagesHoldingPermission(android.Manifest.permission.INTERACT_ACROSS_USERS, userHandle.getIdentifier());
            int size4 = packagesHoldingPermission.size();
            for (int i5 = 0; i5 < size4; i5++) {
                android.content.pm.PackageInfo packageInfo = packagesHoldingPermission.get(i5);
                if ((!z || packageInfo.applicationInfo.isSystemApp()) && !arrayList.contains(packageInfo.packageName)) {
                    arrayList.add(packageInfo.packageName);
                }
            }
        }
        if (z4) {
            java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser = queryIntentServicesAsUser(new android.content.Intent(android.view.inputmethod.InputMethod.SERVICE_INTERFACE), userHandle.getIdentifier());
            int size5 = queryIntentServicesAsUser.size();
            for (int i6 = 0; i6 < size5; i6++) {
                android.content.pm.ServiceInfo serviceInfo = queryIntentServicesAsUser.get(i6).serviceInfo;
                if ((!z || serviceInfo.applicationInfo.isSystemApp()) && !arrayList.contains(serviceInfo.packageName)) {
                    arrayList.add(serviceInfo.packageName);
                }
            }
        }
        if (z5) {
            int size6 = this.mAllApps.size();
            while (i2 < size6) {
                android.content.pm.ApplicationInfo applicationInfo3 = this.mAllApps.get(i2);
                if ((!z || applicationInfo3.isSystemApp()) && applicationInfo3.isRequiredForSystemUser()) {
                    arrayList.add(applicationInfo3.packageName);
                }
                i2++;
            }
        }
        return arrayList;
    }

    protected java.util.List<android.content.pm.ApplicationInfo> getAllApps(int i) {
        try {
            return this.mPackageManager.getInstalledApplications(8704L, i).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser(android.content.Intent intent, int i) {
        try {
            return this.mPackageManager.queryIntentActivities(intent, null, 795136L, i).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser(android.content.Intent intent, int i) {
        try {
            return this.mPackageManager.queryIntentServices(intent, null, 819328L, i).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected java.util.List<android.content.pm.PackageInfo> getPackagesHoldingPermission(java.lang.String str, int i) {
        try {
            return this.mPackageManager.getPackagesHoldingPermissions(new java.lang.String[]{str}, 0L, i).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
