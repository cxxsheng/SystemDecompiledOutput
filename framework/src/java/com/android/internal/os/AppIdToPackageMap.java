package com.android.internal.os;

/* loaded from: classes4.dex */
public final class AppIdToPackageMap {
    private final android.util.SparseArray<java.lang.String> mAppIdToPackageMap;

    public AppIdToPackageMap(android.util.SparseArray<java.lang.String> sparseArray) {
        this.mAppIdToPackageMap = sparseArray;
    }

    public static com.android.internal.os.AppIdToPackageMap getSnapshot() {
        try {
            java.util.List<android.content.pm.PackageInfo> list = android.app.AppGlobals.getPackageManager().getInstalledPackages(794624L, 0).getList();
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            for (android.content.pm.PackageInfo packageInfo : list) {
                int i = packageInfo.applicationInfo.uid;
                if (packageInfo.sharedUserId != null && sparseArray.indexOfKey(i) >= 0) {
                    sparseArray.put(i, "shared:" + packageInfo.sharedUserId);
                } else {
                    sparseArray.put(i, packageInfo.packageName);
                }
            }
            return new com.android.internal.os.AppIdToPackageMap(sparseArray);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String mapAppId(int i) {
        java.lang.String str = this.mAppIdToPackageMap.get(i);
        return str == null ? java.lang.String.valueOf(i) : str;
    }

    public java.lang.String mapUid(int i) {
        java.lang.String str = this.mAppIdToPackageMap.get(android.os.UserHandle.getAppId(i));
        java.lang.String formatUid = android.os.UserHandle.formatUid(i);
        return str == null ? formatUid : str + '/' + formatUid;
    }
}
