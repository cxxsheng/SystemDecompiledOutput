package com.android.net.module.util;

/* loaded from: classes5.dex */
public class LocationPermissionChecker {
    public static final int ERROR_LOCATION_MODE_OFF = 1;
    public static final int ERROR_LOCATION_PERMISSION_MISSING = 2;
    public static final int SUCCEEDED = 0;
    private static final java.lang.String TAG = "LocationPermissionChecker";
    private final android.app.AppOpsManager mAppOpsManager;
    private final android.content.Context mContext;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LocationPermissionCheckStatus {
    }

    public LocationPermissionChecker(android.content.Context context) {
        this.mContext = context;
        this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
    }

    public boolean checkLocationPermission(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) {
        return checkLocationPermissionInternal(str, str2, i, str3) == 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x002d, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int checkLocationPermissionWithDetailInfo(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) {
        int checkLocationPermissionInternal = checkLocationPermissionInternal(str, str2, i, str3);
        switch (checkLocationPermissionInternal) {
            case 1:
                android.util.Log.e(TAG, "Location mode is disabled for the device");
                break;
            case 2:
                android.util.Log.e(TAG, "UID " + i + " has no location permission");
                break;
        }
    }

    public void enforceLocationPermission(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws java.lang.SecurityException {
        switch (checkLocationPermissionInternal(str, str2, i, str3)) {
            case 1:
                throw new java.lang.SecurityException("Location mode is disabled for the device");
            case 2:
                throw new java.lang.SecurityException("UID " + i + " has no location permission");
            default:
                return;
        }
    }

    private int checkLocationPermissionInternal(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) {
        checkPackage(i, str);
        if (checkNetworkSettingsPermission(i) || checkNetworkSetupWizardPermission(i) || checkNetworkStackPermission(i) || checkMainlineNetworkStackPermission(i)) {
            return 0;
        }
        if (isLocationModeEnabled()) {
            return !checkCallersLocationPermission(str, str2, i, true, str3) ? 2 : 0;
        }
        return 1;
    }

    public boolean checkCallersLocationPermission(java.lang.String str, java.lang.String str2, int i, boolean z, java.lang.String str3) {
        java.lang.String str4;
        boolean isTargetSdkLessThan = isTargetSdkLessThan(str, 29, i);
        if (z && isTargetSdkLessThan) {
            str4 = android.Manifest.permission.ACCESS_COARSE_LOCATION;
        } else {
            str4 = android.Manifest.permission.ACCESS_FINE_LOCATION;
        }
        if (getUidPermission(str4, i) == -1) {
            return false;
        }
        if (noteAppOpAllowed(android.app.AppOpsManager.OPSTR_FINE_LOCATION, str, str2, i, str3)) {
            return true;
        }
        if (z && isTargetSdkLessThan) {
            return noteAppOpAllowed(android.app.AppOpsManager.OPSTR_COARSE_LOCATION, str, str2, i, str3);
        }
        return false;
    }

    public boolean isLocationModeEnabled() {
        try {
            return ((android.location.LocationManager) this.mContext.getSystemService(android.location.LocationManager.class)).isLocationEnabledForUser(android.os.UserHandle.of(getCurrentUser()));
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failure to get location mode via API, falling back to settings", e);
            return false;
        }
    }

    private boolean isTargetSdkLessThan(java.lang.String str, int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, android.os.UserHandle.getUserHandleForUid(i2)).targetSdkVersion < i) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
    }

    private boolean noteAppOpAllowed(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) {
        return this.mAppOpsManager.noteOp(str, i, str2, str3, str4) == 0;
    }

    private void checkPackage(int i, java.lang.String str) throws java.lang.SecurityException {
        if (str == null) {
            throw new java.lang.SecurityException("Checking UID " + i + " but Package Name is Null");
        }
        this.mAppOpsManager.checkPackage(i, str);
    }

    protected int getCurrentUser() {
        return android.app.ActivityManager.getCurrentUser();
    }

    private int getUidPermission(java.lang.String str, int i) {
        return this.mContext.checkPermission(str, -1, i);
    }

    public boolean checkNetworkSettingsPermission(int i) {
        return getUidPermission(android.Manifest.permission.NETWORK_SETTINGS, i) == 0;
    }

    public boolean checkNetworkSetupWizardPermission(int i) {
        return getUidPermission(android.Manifest.permission.NETWORK_SETUP_WIZARD, i) == 0;
    }

    public boolean checkNetworkStackPermission(int i) {
        return getUidPermission(android.Manifest.permission.NETWORK_STACK, i) == 0;
    }

    public boolean checkMainlineNetworkStackPermission(int i) {
        return getUidPermission(android.net.NetworkStack.PERMISSION_MAINLINE_NETWORK_STACK, i) == 0;
    }
}
