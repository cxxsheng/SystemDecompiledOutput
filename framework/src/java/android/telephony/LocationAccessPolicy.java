package android.telephony;

/* loaded from: classes3.dex */
public final class LocationAccessPolicy {
    private static final boolean DBG = false;
    public static final int MAX_SDK_FOR_ANY_ENFORCEMENT = 10000;
    private static final java.lang.String TAG = "LocationAccessPolicy";

    public enum LocationPermissionResult {
        ALLOWED,
        DENIED_SOFT,
        DENIED_HARD
    }

    public static class LocationPermissionQuery {
        public final java.lang.String callingFeatureId;
        public final java.lang.String callingPackage;
        public final int callingPid;
        public final int callingUid;
        public final boolean logAsInfo;
        public final java.lang.String method;
        public final int minSdkVersionForCoarse;
        public final int minSdkVersionForFine;

        private LocationPermissionQuery(java.lang.String str, java.lang.String str2, int i, int i2, int i3, int i4, boolean z, java.lang.String str3) {
            this.callingPackage = str;
            this.callingFeatureId = str2;
            this.callingUid = i;
            this.callingPid = i2;
            this.minSdkVersionForCoarse = i3;
            this.minSdkVersionForFine = i4;
            this.logAsInfo = z;
            this.method = str3;
        }

        public static class Builder {
            private java.lang.String mCallingFeatureId;
            private java.lang.String mCallingPackage;
            private int mCallingPid;
            private int mCallingUid;
            private java.lang.String mMethod;
            private int mMinSdkVersionForCoarse = -1;
            private int mMinSdkVersionForFine = -1;
            private int mMinSdkVersionForEnforcement = -1;
            private boolean mLogAsInfo = false;

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder setCallingPackage(java.lang.String str) {
                this.mCallingPackage = str;
                return this;
            }

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder setCallingFeatureId(java.lang.String str) {
                this.mCallingFeatureId = str;
                return this;
            }

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder setCallingUid(int i) {
                this.mCallingUid = i;
                return this;
            }

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder setCallingPid(int i) {
                this.mCallingPid = i;
                return this;
            }

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder setMinSdkVersionForCoarse(int i) {
                this.mMinSdkVersionForCoarse = i;
                return this;
            }

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder setMinSdkVersionForFine(int i) {
                this.mMinSdkVersionForFine = i;
                return this;
            }

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder setMinSdkVersionForEnforcement(int i) {
                this.mMinSdkVersionForEnforcement = i;
                return this;
            }

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder setMethod(java.lang.String str) {
                this.mMethod = str;
                return this;
            }

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder setLogAsInfo(boolean z) {
                this.mLogAsInfo = z;
                return this;
            }

            public android.telephony.LocationAccessPolicy.LocationPermissionQuery build() {
                if (this.mMinSdkVersionForCoarse < 0 || this.mMinSdkVersionForFine < 0) {
                    throw new java.lang.IllegalArgumentException("Must specify min sdk versions for enforcement for both coarse and fine permissions");
                }
                if (this.mMinSdkVersionForFine > 1 && this.mMinSdkVersionForCoarse > 1 && this.mMinSdkVersionForEnforcement != java.lang.Math.min(this.mMinSdkVersionForCoarse, this.mMinSdkVersionForFine)) {
                    throw new java.lang.IllegalArgumentException("setMinSdkVersionForEnforcement must be called.");
                }
                if (this.mMinSdkVersionForFine < this.mMinSdkVersionForCoarse) {
                    throw new java.lang.IllegalArgumentException("Since fine location permission includes access to coarse location, the min sdk level for enforcement of the fine location permission must not be less than the min sdk level for enforcement of the coarse location permission.");
                }
                return new android.telephony.LocationAccessPolicy.LocationPermissionQuery(this.mCallingPackage, this.mCallingFeatureId, this.mCallingUid, this.mCallingPid, this.mMinSdkVersionForCoarse, this.mMinSdkVersionForFine, this.mLogAsInfo, this.mMethod);
            }
        }
    }

    private static void logError(android.content.Context context, android.telephony.LocationAccessPolicy.LocationPermissionQuery locationPermissionQuery, java.lang.String str) {
        if (locationPermissionQuery.logAsInfo) {
            android.util.Log.i(TAG, str);
            return;
        }
        android.util.Log.e(TAG, str);
        try {
            if (com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE) {
                android.widget.Toast.makeText(context, str, 0).show();
            }
        } catch (java.lang.Throwable th) {
        }
    }

    private static android.telephony.LocationAccessPolicy.LocationPermissionResult appOpsModeToPermissionResult(int i) {
        switch (i) {
            case 0:
                return android.telephony.LocationAccessPolicy.LocationPermissionResult.ALLOWED;
            case 1:
            default:
                return android.telephony.LocationAccessPolicy.LocationPermissionResult.DENIED_SOFT;
            case 2:
                return android.telephony.LocationAccessPolicy.LocationPermissionResult.DENIED_HARD;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static java.lang.String getAppOpsString(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1888586689:
                if (str.equals(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -63024214:
                if (str.equals(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return android.app.AppOpsManager.OPSTR_FINE_LOCATION;
            case 1:
                return android.app.AppOpsManager.OPSTR_COARSE_LOCATION;
            default:
                return null;
        }
    }

    private static android.telephony.LocationAccessPolicy.LocationPermissionResult checkAppLocationPermissionHelper(android.content.Context context, android.telephony.LocationAccessPolicy.LocationPermissionQuery locationPermissionQuery, java.lang.String str) {
        java.lang.String str2 = android.Manifest.permission.ACCESS_FINE_LOCATION.equals(str) ? "fine" : "coarse";
        if (checkManifestPermission(context, locationPermissionQuery.callingPid, locationPermissionQuery.callingUid, str)) {
            int noteOpNoThrow = ((android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class)).noteOpNoThrow(getAppOpsString(str), locationPermissionQuery.callingUid, locationPermissionQuery.callingPackage, locationPermissionQuery.callingFeatureId, (java.lang.String) null);
            if (noteOpNoThrow == 0) {
                return android.telephony.LocationAccessPolicy.LocationPermissionResult.ALLOWED;
            }
            android.util.Log.i(TAG, locationPermissionQuery.callingPackage + " is aware of " + str2 + " but the app-ops permission is specifically denied.");
            return appOpsModeToPermissionResult(noteOpNoThrow);
        }
        int i = android.Manifest.permission.ACCESS_FINE_LOCATION.equals(str) ? locationPermissionQuery.minSdkVersionForFine : locationPermissionQuery.minSdkVersionForCoarse;
        if (i > 10000) {
            logError(context, locationPermissionQuery, "Allowing " + locationPermissionQuery.callingPackage + " " + str2 + " because we're not enforcing API " + i + " yet. Please fix this app because it will break in the future. Called from " + locationPermissionQuery.method);
            return null;
        }
        if (!isAppAtLeastSdkVersion(context, locationPermissionQuery.callingPackage, i)) {
            logError(context, locationPermissionQuery, "Allowing " + locationPermissionQuery.callingPackage + " " + str2 + " because it doesn't target API " + i + " yet. Please fix this app. Called from " + locationPermissionQuery.method);
            return null;
        }
        return android.telephony.LocationAccessPolicy.LocationPermissionResult.DENIED_HARD;
    }

    public static android.telephony.LocationAccessPolicy.LocationPermissionResult checkLocationPermission(android.content.Context context, android.telephony.LocationAccessPolicy.LocationPermissionQuery locationPermissionQuery) {
        android.telephony.LocationAccessPolicy.LocationPermissionResult checkAppLocationPermissionHelper;
        android.telephony.LocationAccessPolicy.LocationPermissionResult checkAppLocationPermissionHelper2;
        if (locationPermissionQuery.callingUid == 1001 || locationPermissionQuery.callingUid == 1000 || locationPermissionQuery.callingUid == 1073 || locationPermissionQuery.callingUid == 0) {
            return android.telephony.LocationAccessPolicy.LocationPermissionResult.ALLOWED;
        }
        if (!checkSystemLocationAccess(context, locationPermissionQuery.callingUid, locationPermissionQuery.callingPid, locationPermissionQuery.callingPackage)) {
            return android.telephony.LocationAccessPolicy.LocationPermissionResult.DENIED_SOFT;
        }
        if (locationPermissionQuery.minSdkVersionForFine < Integer.MAX_VALUE && (checkAppLocationPermissionHelper2 = checkAppLocationPermissionHelper(context, locationPermissionQuery, android.Manifest.permission.ACCESS_FINE_LOCATION)) != null) {
            return checkAppLocationPermissionHelper2;
        }
        if (locationPermissionQuery.minSdkVersionForCoarse < Integer.MAX_VALUE && (checkAppLocationPermissionHelper = checkAppLocationPermissionHelper(context, locationPermissionQuery, android.Manifest.permission.ACCESS_COARSE_LOCATION)) != null) {
            return checkAppLocationPermissionHelper;
        }
        return android.telephony.LocationAccessPolicy.LocationPermissionResult.ALLOWED;
    }

    private static boolean checkManifestPermission(android.content.Context context, int i, int i2, java.lang.String str) {
        return context.checkPermission(str, i, i2) == 0;
    }

    private static boolean checkSystemLocationAccess(android.content.Context context, int i, int i2, java.lang.String str) {
        if (isLocationModeEnabled(context, android.os.UserHandle.getUserHandleForUid(i).getIdentifier()) || isLocationBypassAllowed(context, str)) {
            return isCurrentProfile(context, i) || checkInteractAcrossUsersFull(context, i2, i);
        }
        return false;
    }

    public static boolean isLocationModeEnabled(android.content.Context context, int i) {
        android.location.LocationManager locationManager = (android.location.LocationManager) context.getSystemService(android.location.LocationManager.class);
        if (locationManager == null) {
            android.util.Log.w(TAG, "Couldn't get location manager, denying location access");
            return false;
        }
        return locationManager.isLocationEnabledForUser(android.os.UserHandle.of(i));
    }

    private static boolean isLocationBypassAllowed(android.content.Context context, java.lang.String str) {
        for (java.lang.String str2 : getLocationBypassPackages(context)) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static java.lang.String[] getLocationBypassPackages(android.content.Context context) {
        return context.getResources().getStringArray(com.android.internal.R.array.config_serviceStateLocationAllowedPackages);
    }

    private static boolean checkInteractAcrossUsersFull(android.content.Context context, int i, int i2) {
        return checkManifestPermission(context, i, i2, android.Manifest.permission.INTERACT_ACROSS_USERS_FULL);
    }

    private static boolean isCurrentProfile(android.content.Context context, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (android.os.UserHandle.getUserHandleForUid(i).getIdentifier() == android.app.ActivityManager.getCurrentUser()) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
            android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(android.app.ActivityManager.class);
            if (activityManager != null) {
                return activityManager.isProfileForeground(android.os.UserHandle.getUserHandleForUid(android.app.ActivityManager.getCurrentUser()));
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static boolean isAppAtLeastSdkVersion(android.content.Context context, java.lang.String str, int i) {
        if (context.getPackageManager().getApplicationInfo(str, 0).targetSdkVersion < i) {
            return false;
        }
        return true;
    }
}
