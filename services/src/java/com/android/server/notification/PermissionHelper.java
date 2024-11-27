package com.android.server.notification;

/* loaded from: classes2.dex */
public final class PermissionHelper {
    private static final java.lang.String NOTIFICATION_PERMISSION = "android.permission.POST_NOTIFICATIONS";
    private static final java.lang.String TAG = "PermissionHelper";
    private final android.content.Context mContext;
    private final android.content.pm.IPackageManager mPackageManager;
    private final android.permission.IPermissionManager mPermManager;

    public PermissionHelper(android.content.Context context, android.content.pm.IPackageManager iPackageManager, android.permission.IPermissionManager iPermissionManager) {
        this.mContext = context;
        this.mPackageManager = iPackageManager;
        this.mPermManager = iPermissionManager;
    }

    public boolean hasPermission(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mContext.checkPermission(NOTIFICATION_PERMISSION, -1, i) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean hasRequestedPermission(java.lang.String str, java.lang.String str2, int i) {
        android.content.pm.PackageInfo packageInfo;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                packageInfo = this.mPackageManager.getPackageInfo(str2, 4096L, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.d(TAG, "Could not reach system server", e);
            }
            if (packageInfo == null || packageInfo.requestedPermissions == null) {
                return false;
            }
            for (java.lang.String str3 : packageInfo.requestedPermissions) {
                if (str.equals(str3)) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    java.util.Set<android.util.Pair<java.lang.Integer, java.lang.String>> getAppsRequestingPermission(int i) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (android.content.pm.PackageInfo packageInfo : getInstalledPackages(i)) {
            if (packageInfo.requestedPermissions != null) {
                java.lang.String[] strArr = packageInfo.requestedPermissions;
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (!NOTIFICATION_PERMISSION.equals(strArr[i2])) {
                        i2++;
                    } else {
                        hashSet.add(new android.util.Pair(java.lang.Integer.valueOf(packageInfo.applicationInfo.uid), packageInfo.packageName));
                        break;
                    }
                }
            }
        }
        return hashSet;
    }

    private java.util.List<android.content.pm.PackageInfo> getInstalledPackages(int i) {
        android.content.pm.ParceledListSlice parceledListSlice;
        try {
            parceledListSlice = this.mPackageManager.getInstalledPackages(4096L, i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.d(TAG, "Could not reach system server", e);
            parceledListSlice = null;
        }
        if (parceledListSlice == null) {
            return java.util.Collections.emptyList();
        }
        return parceledListSlice.getList();
    }

    java.util.Set<android.util.Pair<java.lang.Integer, java.lang.String>> getAppsGrantedPermission(int i) {
        android.content.pm.ParceledListSlice parceledListSlice;
        java.util.HashSet hashSet = new java.util.HashSet();
        try {
            parceledListSlice = this.mPackageManager.getPackagesHoldingPermissions(new java.lang.String[]{NOTIFICATION_PERMISSION}, 0L, i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Could not reach system server", e);
            parceledListSlice = null;
        }
        if (parceledListSlice == null) {
            return hashSet;
        }
        for (android.content.pm.PackageInfo packageInfo : parceledListSlice.getList()) {
            hashSet.add(new android.util.Pair(java.lang.Integer.valueOf(packageInfo.applicationInfo.uid), packageInfo.packageName));
        }
        return hashSet;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> getNotificationPermissionValues(int i) {
        android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap = new android.util.ArrayMap<>();
        java.util.Set<android.util.Pair<java.lang.Integer, java.lang.String>> appsRequestingPermission = getAppsRequestingPermission(i);
        java.util.Set<android.util.Pair<java.lang.Integer, java.lang.String>> appsGrantedPermission = getAppsGrantedPermission(i);
        for (android.util.Pair<java.lang.Integer, java.lang.String> pair : appsRequestingPermission) {
            arrayMap.put(pair, new android.util.Pair<>(java.lang.Boolean.valueOf(appsGrantedPermission.contains(pair)), java.lang.Boolean.valueOf(isPermissionUserSet((java.lang.String) pair.second, i))));
        }
        return arrayMap;
    }

    public void setNotificationPermission(java.lang.String str, int i, boolean z, boolean z2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Could not reach system server", e);
            }
            if (packageRequestsNotificationPermission(str, i) && !isPermissionFixed(str, i) && (!isPermissionGrantedByDefaultOrRole(str, i) || z2)) {
                boolean hasPermission = hasPermission(this.mPackageManager.getPackageUid(str, 0L, i));
                if (z && !hasPermission) {
                    this.mPermManager.grantRuntimePermission(str, NOTIFICATION_PERMISSION, "default:0", i);
                } else if (!z && hasPermission) {
                    this.mPermManager.revokeRuntimePermission(str, NOTIFICATION_PERMISSION, "default:0", i, TAG);
                }
                if (z2) {
                    this.mPermManager.updatePermissionFlags(str, NOTIFICATION_PERMISSION, 3, 1, true, "default:0", i);
                } else {
                    this.mPermManager.updatePermissionFlags(str, NOTIFICATION_PERMISSION, 3, 0, true, "default:0", i);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setNotificationPermission(com.android.server.notification.PermissionHelper.PackagePermission packagePermission) {
        if (packagePermission != null && packagePermission.packageName != null && !isPermissionFixed(packagePermission.packageName, packagePermission.userId)) {
            setNotificationPermission(packagePermission.packageName, packagePermission.userId, packagePermission.granted, true);
        }
    }

    public boolean isPermissionFixed(java.lang.String str, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int permissionFlags = this.mPermManager.getPermissionFlags(str, NOTIFICATION_PERMISSION, "default:0", i);
            return ((permissionFlags & 16) == 0 && (permissionFlags & 4) == 0) ? false : true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Could not reach system server", e);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean isPermissionUserSet(java.lang.String str, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return (this.mPermManager.getPermissionFlags(str, NOTIFICATION_PERMISSION, "default:0", i) & 3) != 0;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Could not reach system server", e);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean isPermissionGrantedByDefaultOrRole(java.lang.String str, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return (this.mPermManager.getPermissionFlags(str, NOTIFICATION_PERMISSION, "default:0", i) & 32800) != 0;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Could not reach system server", e);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean packageRequestsNotificationPermission(java.lang.String str, int i) {
        try {
            android.content.pm.PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 4096L, i);
            if (packageInfo != null) {
                return com.android.internal.util.ArrayUtils.contains(packageInfo.requestedPermissions, NOTIFICATION_PERMISSION);
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Could not reach system server", e);
            return false;
        }
    }

    public static class PackagePermission {
        public final boolean granted;
        public final java.lang.String packageName;
        public final int userId;
        public final boolean userModifiedSettings;

        public PackagePermission(java.lang.String str, int i, boolean z, boolean z2) {
            this.packageName = str;
            this.userId = i;
            this.granted = z;
            this.userModifiedSettings = z2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.server.notification.PermissionHelper.PackagePermission packagePermission = (com.android.server.notification.PermissionHelper.PackagePermission) obj;
            if (this.userId == packagePermission.userId && this.granted == packagePermission.granted && this.userModifiedSettings == packagePermission.userModifiedSettings && java.util.Objects.equals(this.packageName, packagePermission.packageName)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.packageName, java.lang.Integer.valueOf(this.userId), java.lang.Boolean.valueOf(this.granted), java.lang.Boolean.valueOf(this.userModifiedSettings));
        }

        public java.lang.String toString() {
            return "PackagePermission{packageName='" + this.packageName + "', userId=" + this.userId + ", granted=" + this.granted + ", userSet=" + this.userModifiedSettings + '}';
        }
    }
}
