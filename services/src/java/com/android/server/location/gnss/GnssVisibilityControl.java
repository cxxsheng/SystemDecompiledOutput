package com.android.server.location.gnss;

/* loaded from: classes2.dex */
class GnssVisibilityControl {
    private static final int ARRAY_MAP_INITIAL_CAPACITY_PROXY_APPS_STATE = 5;
    private static final long EMERGENCY_EXTENSION_FOR_MISMATCH = 128000;
    private static final long LOCATION_ICON_DISPLAY_DURATION_MILLIS = 5000;
    private static final java.lang.String LOCATION_PERMISSION_NAME = "android.permission.ACCESS_FINE_LOCATION";
    private static final long ON_GPS_ENABLED_CHANGED_TIMEOUT_MILLIS = 3000;
    private static final java.lang.String TAG = "GnssVisibilityControl";
    private static final java.lang.String WAKELOCK_KEY = "GnssVisibilityControl";
    private static final long WAKELOCK_TIMEOUT_MILLIS = 60000;
    private final android.app.AppOpsManager mAppOps;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private boolean mIsGpsEnabled;
    private final com.android.internal.location.GpsNetInitiatedHandler mNiHandler;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private static final boolean DEBUG = android.util.Log.isLoggable("GnssVisibilityControl", 3);
    private static final java.lang.String[] NO_LOCATION_ENABLED_PROXY_APPS = new java.lang.String[0];
    private android.util.ArrayMap<java.lang.String, com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState> mProxyAppsState = new android.util.ArrayMap<>(5);
    private android.content.pm.PackageManager.OnPermissionsChangedListener mOnPermissionsChangedListener = new android.content.pm.PackageManager.OnPermissionsChangedListener() { // from class: com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda3
        public final void onPermissionsChanged(int i) {
            com.android.server.location.gnss.GnssVisibilityControl.this.lambda$new$1(i);
        }
    };

    private native boolean native_enable_nfw_location_access(java.lang.String[] strArr);

    private static final class ProxyAppState {
        private boolean mHasLocationPermission;
        private boolean mIsLocationIconOn;

        private ProxyAppState(boolean z) {
            this.mHasLocationPermission = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(final int i) {
        runOnHandler(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssVisibilityControl.this.lambda$new$0(i);
            }
        });
    }

    GnssVisibilityControl(android.content.Context context, android.os.Looper looper, com.android.internal.location.GpsNetInitiatedHandler gpsNetInitiatedHandler) {
        this.mContext = context;
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService("power")).newWakeLock(1, "GnssVisibilityControl");
        this.mHandler = new android.os.Handler(looper);
        this.mNiHandler = gpsNetInitiatedHandler;
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mPackageManager = this.mContext.getPackageManager();
        runOnHandler(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssVisibilityControl.this.handleInitialize();
            }
        });
    }

    void onGpsEnabledChanged(final boolean z) {
        if (!this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssVisibilityControl.this.lambda$onGpsEnabledChanged$2(z);
            }
        }, 3000L) && !z) {
            android.util.Log.w("GnssVisibilityControl", "Native call to disable non-framework location access in GNSS HAL may get executed after native_cleanup().");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportNfwNotification$3(java.lang.String str, byte b, java.lang.String str2, byte b2, java.lang.String str3, byte b3, boolean z, boolean z2) {
        handleNfwNotification(new com.android.server.location.gnss.GnssVisibilityControl.NfwNotification(str, b, str2, b2, str3, b3, z, z2));
    }

    void reportNfwNotification(final java.lang.String str, final byte b, final java.lang.String str2, final byte b2, final java.lang.String str3, final byte b3, final boolean z, final boolean z2) {
        runOnHandler(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssVisibilityControl.this.lambda$reportNfwNotification$3(str, b, str2, b2, str3, b3, z, z2);
            }
        });
    }

    void onConfigurationUpdated(com.android.server.location.gnss.GnssConfiguration gnssConfiguration) {
        final java.util.List<java.lang.String> proxyApps = gnssConfiguration.getProxyApps();
        runOnHandler(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssVisibilityControl.this.lambda$onConfigurationUpdated$4(proxyApps);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInitialize() {
        listenForProxyAppsPackageUpdates();
    }

    private void listenForProxyAppsPackageUpdates() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.location.gnss.GnssVisibilityControl.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                if (action == null) {
                }
                switch (action.hashCode()) {
                    case -810471698:
                        if (action.equals("android.intent.action.PACKAGE_REPLACED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 172491798:
                        if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 525384130:
                        if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544582882:
                        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                            c = 0;
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
                    case 1:
                    case 2:
                    case 3:
                        com.android.server.location.gnss.GnssVisibilityControl.this.handleProxyAppPackageUpdate(intent.getData().getEncodedSchemeSpecificPart(), action);
                        break;
                }
            }
        }, android.os.UserHandle.ALL, intentFilter, null, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleProxyAppPackageUpdate(java.lang.String str, java.lang.String str2) {
        com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState proxyAppState = this.mProxyAppsState.get(str);
        if (proxyAppState == null) {
            return;
        }
        if (DEBUG) {
            android.util.Log.d("GnssVisibilityControl", "Proxy app " + str + " package changed: " + str2);
        }
        boolean shouldEnableLocationPermissionInGnssHal = shouldEnableLocationPermissionInGnssHal(str);
        if (proxyAppState.mHasLocationPermission != shouldEnableLocationPermissionInGnssHal) {
            android.util.Log.i("GnssVisibilityControl", "Proxy app " + str + " location permission changed. IsLocationPermissionEnabled: " + shouldEnableLocationPermissionInGnssHal);
            proxyAppState.mHasLocationPermission = shouldEnableLocationPermissionInGnssHal;
            updateNfwLocationAccessProxyAppsInGnssHal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleUpdateProxyApps, reason: merged with bridge method [inline-methods] */
    public void lambda$onConfigurationUpdated$4(java.util.List<java.lang.String> list) {
        if (!isProxyAppListUpdated(list)) {
            return;
        }
        if (list.isEmpty()) {
            if (!this.mProxyAppsState.isEmpty()) {
                this.mPackageManager.removeOnPermissionsChangeListener(this.mOnPermissionsChangedListener);
                resetProxyAppsState();
                updateNfwLocationAccessProxyAppsInGnssHal();
                return;
            }
            return;
        }
        if (this.mProxyAppsState.isEmpty()) {
            this.mPackageManager.addOnPermissionsChangeListener(this.mOnPermissionsChangedListener);
        } else {
            resetProxyAppsState();
        }
        for (java.lang.String str : list) {
            this.mProxyAppsState.put(str, new com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState(shouldEnableLocationPermissionInGnssHal(str)));
        }
        updateNfwLocationAccessProxyAppsInGnssHal();
    }

    private void resetProxyAppsState() {
        for (java.util.Map.Entry<java.lang.String, com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState> entry : this.mProxyAppsState.entrySet()) {
            com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState value = entry.getValue();
            if (value.mIsLocationIconOn) {
                this.mHandler.removeCallbacksAndMessages(value);
                android.content.pm.ApplicationInfo proxyAppInfo = getProxyAppInfo(entry.getKey());
                if (proxyAppInfo != null) {
                    clearLocationIcon(value, proxyAppInfo.uid, entry.getKey());
                }
            }
        }
        this.mProxyAppsState.clear();
    }

    private boolean isProxyAppListUpdated(java.util.List<java.lang.String> list) {
        if (list.size() != this.mProxyAppsState.size()) {
            return true;
        }
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            if (!this.mProxyAppsState.containsKey(it.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleGpsEnabledChanged, reason: merged with bridge method [inline-methods] */
    public void lambda$onGpsEnabledChanged$2(boolean z) {
        if (DEBUG) {
            android.util.Log.d("GnssVisibilityControl", "handleGpsEnabledChanged, mIsGpsEnabled: " + this.mIsGpsEnabled + ", isGpsEnabled: " + z);
        }
        this.mIsGpsEnabled = z;
        if (!this.mIsGpsEnabled) {
            disableNfwLocationAccess();
        } else {
            setNfwLocationAccessProxyAppsInGnssHal(getLocationPermissionEnabledProxyApps());
        }
    }

    private void disableNfwLocationAccess() {
        setNfwLocationAccessProxyAppsInGnssHal(NO_LOCATION_ENABLED_PROXY_APPS);
    }

    private static class NfwNotification {
        private static final byte NFW_RESPONSE_TYPE_ACCEPTED_LOCATION_PROVIDED = 2;
        private static final byte NFW_RESPONSE_TYPE_ACCEPTED_NO_LOCATION_PROVIDED = 1;
        private static final byte NFW_RESPONSE_TYPE_REJECTED = 0;
        private final boolean mInEmergencyMode;
        private final boolean mIsCachedLocation;
        private final java.lang.String mOtherProtocolStackName;
        private final byte mProtocolStack;
        private final java.lang.String mProxyAppPackageName;
        private final byte mRequestor;
        private final java.lang.String mRequestorId;
        private final byte mResponseType;

        private NfwNotification(java.lang.String str, byte b, java.lang.String str2, byte b2, java.lang.String str3, byte b3, boolean z, boolean z2) {
            this.mProxyAppPackageName = str;
            this.mProtocolStack = b;
            this.mOtherProtocolStackName = str2;
            this.mRequestor = b2;
            this.mRequestorId = str3;
            this.mResponseType = b3;
            this.mInEmergencyMode = z;
            this.mIsCachedLocation = z2;
        }

        @android.annotation.SuppressLint({"DefaultLocale"})
        public java.lang.String toString() {
            return java.lang.String.format("{proxyAppPackageName: %s, protocolStack: %d, otherProtocolStackName: %s, requestor: %d, requestorId: %s, responseType: %s, inEmergencyMode: %b, isCachedLocation: %b}", this.mProxyAppPackageName, java.lang.Byte.valueOf(this.mProtocolStack), this.mOtherProtocolStackName, java.lang.Byte.valueOf(this.mRequestor), this.mRequestorId, getResponseTypeAsString(), java.lang.Boolean.valueOf(this.mInEmergencyMode), java.lang.Boolean.valueOf(this.mIsCachedLocation));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getResponseTypeAsString() {
            switch (this.mResponseType) {
                case 0:
                    return "REJECTED";
                case 1:
                    return "ACCEPTED_NO_LOCATION_PROVIDED";
                case 2:
                    return "ACCEPTED_LOCATION_PROVIDED";
                default:
                    return "<Unknown>";
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isRequestAccepted() {
            return this.mResponseType != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isLocationProvided() {
            return this.mResponseType == 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isRequestAttributedToProxyApp() {
            return !android.text.TextUtils.isEmpty(this.mProxyAppPackageName);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmergencyRequestNotification() {
            return this.mInEmergencyMode && !isRequestAttributedToProxyApp();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handlePermissionsChanged, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0(int i) {
        if (this.mProxyAppsState.isEmpty()) {
            return;
        }
        for (java.util.Map.Entry<java.lang.String, com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState> entry : this.mProxyAppsState.entrySet()) {
            java.lang.String key = entry.getKey();
            android.content.pm.ApplicationInfo proxyAppInfo = getProxyAppInfo(key);
            if (proxyAppInfo != null && proxyAppInfo.uid == i) {
                boolean shouldEnableLocationPermissionInGnssHal = shouldEnableLocationPermissionInGnssHal(key);
                com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState value = entry.getValue();
                if (shouldEnableLocationPermissionInGnssHal != value.mHasLocationPermission) {
                    android.util.Log.i("GnssVisibilityControl", "Proxy app " + key + " location permission changed. IsLocationPermissionEnabled: " + shouldEnableLocationPermissionInGnssHal);
                    value.mHasLocationPermission = shouldEnableLocationPermissionInGnssHal;
                    updateNfwLocationAccessProxyAppsInGnssHal();
                    return;
                }
                return;
            }
        }
    }

    private android.content.pm.ApplicationInfo getProxyAppInfo(java.lang.String str) {
        try {
            return this.mPackageManager.getApplicationInfo(str, 0);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            if (DEBUG) {
                android.util.Log.d("GnssVisibilityControl", "Proxy app " + str + " is not found.");
                return null;
            }
            return null;
        }
    }

    private boolean shouldEnableLocationPermissionInGnssHal(java.lang.String str) {
        return isProxyAppInstalled(str) && hasLocationPermission(str);
    }

    private boolean isProxyAppInstalled(java.lang.String str) {
        android.content.pm.ApplicationInfo proxyAppInfo = getProxyAppInfo(str);
        return proxyAppInfo != null && proxyAppInfo.enabled;
    }

    private boolean hasLocationPermission(java.lang.String str) {
        return this.mPackageManager.checkPermission(LOCATION_PERMISSION_NAME, str) == 0;
    }

    private void updateNfwLocationAccessProxyAppsInGnssHal() {
        if (!this.mIsGpsEnabled) {
            return;
        }
        setNfwLocationAccessProxyAppsInGnssHal(getLocationPermissionEnabledProxyApps());
    }

    private void setNfwLocationAccessProxyAppsInGnssHal(java.lang.String[] strArr) {
        java.lang.String arrays = java.util.Arrays.toString(strArr);
        android.util.Log.i("GnssVisibilityControl", "Updating non-framework location access proxy apps in the GNSS HAL to: " + arrays);
        if (!native_enable_nfw_location_access(strArr)) {
            android.util.Log.e("GnssVisibilityControl", "Failed to update non-framework location access proxy apps in the GNSS HAL to: " + arrays);
        }
    }

    private java.lang.String[] getLocationPermissionEnabledProxyApps() {
        java.util.Iterator<com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState> it = this.mProxyAppsState.values().iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().mHasLocationPermission) {
                i2++;
            }
        }
        java.lang.String[] strArr = new java.lang.String[i2];
        for (java.util.Map.Entry<java.lang.String, com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState> entry : this.mProxyAppsState.entrySet()) {
            java.lang.String key = entry.getKey();
            if (entry.getValue().mHasLocationPermission) {
                strArr[i] = key;
                i++;
            }
        }
        return strArr;
    }

    public boolean hasLocationPermissionEnabledProxyApps() {
        return getLocationPermissionEnabledProxyApps().length > 0;
    }

    private void handleNfwNotification(com.android.server.location.gnss.GnssVisibilityControl.NfwNotification nfwNotification) {
        if (DEBUG) {
            android.util.Log.d("GnssVisibilityControl", "Non-framework location access notification: " + nfwNotification);
        }
        if (nfwNotification.isEmergencyRequestNotification()) {
            handleEmergencyNfwNotification(nfwNotification);
            return;
        }
        java.lang.String str = nfwNotification.mProxyAppPackageName;
        com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState proxyAppState = this.mProxyAppsState.get(str);
        boolean isRequestAccepted = nfwNotification.isRequestAccepted();
        boolean isPermissionMismatched = isPermissionMismatched(proxyAppState, nfwNotification);
        logEvent(nfwNotification, isPermissionMismatched);
        if (!nfwNotification.isRequestAttributedToProxyApp()) {
            if (!isRequestAccepted) {
                if (DEBUG) {
                    android.util.Log.d("GnssVisibilityControl", "Non-framework location request rejected. ProxyAppPackageName field is not set in the notification: " + nfwNotification + ". Number of configured proxy apps: " + this.mProxyAppsState.size());
                    return;
                }
                return;
            }
            android.util.Log.e("GnssVisibilityControl", "ProxyAppPackageName field is not set. AppOps service not notified for notification: " + nfwNotification);
            return;
        }
        if (proxyAppState == null) {
            android.util.Log.w("GnssVisibilityControl", "Could not find proxy app " + str + " in the value specified for config parameter: NFW_PROXY_APPS. AppOps service not notified for notification: " + nfwNotification);
            return;
        }
        android.content.pm.ApplicationInfo proxyAppInfo = getProxyAppInfo(str);
        if (proxyAppInfo == null) {
            android.util.Log.e("GnssVisibilityControl", "Proxy app " + str + " is not found. AppOps service not notified for notification: " + nfwNotification);
            return;
        }
        if (nfwNotification.isLocationProvided()) {
            showLocationIcon(proxyAppState, nfwNotification, proxyAppInfo.uid, str);
            this.mAppOps.noteOpNoThrow(1, proxyAppInfo.uid, str);
        }
        if (isPermissionMismatched) {
            android.util.Log.w("GnssVisibilityControl", "Permission mismatch. Proxy app " + str + " location permission is set to " + proxyAppState.mHasLocationPermission + " and GNSS HAL enabled is set to " + this.mIsGpsEnabled + " but GNSS non-framework location access response type is " + nfwNotification.getResponseTypeAsString() + " for notification: " + nfwNotification);
        }
    }

    private boolean isPermissionMismatched(com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState proxyAppState, com.android.server.location.gnss.GnssVisibilityControl.NfwNotification nfwNotification) {
        boolean isRequestAccepted = nfwNotification.isRequestAccepted();
        if (proxyAppState == null || !this.mIsGpsEnabled) {
            return isRequestAccepted;
        }
        return proxyAppState.mHasLocationPermission != isRequestAccepted;
    }

    private void showLocationIcon(com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState proxyAppState, com.android.server.location.gnss.GnssVisibilityControl.NfwNotification nfwNotification, int i, final java.lang.String str) {
        boolean z = proxyAppState.mIsLocationIconOn;
        if (!z) {
            if (!updateLocationIcon(true, i, str)) {
                android.util.Log.w("GnssVisibilityControl", "Failed to show Location icon for notification: " + nfwNotification);
                return;
            }
            proxyAppState.mIsLocationIconOn = true;
        } else {
            this.mHandler.removeCallbacksAndMessages(proxyAppState);
        }
        if (DEBUG) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Location icon on. ");
            sb.append(z ? "Extending" : "Setting");
            sb.append(" icon display timer. Uid: ");
            sb.append(i);
            sb.append(", proxyAppPkgName: ");
            sb.append(str);
            android.util.Log.d("GnssVisibilityControl", sb.toString());
        }
        if (!this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssVisibilityControl.this.lambda$showLocationIcon$5(str);
            }
        }, proxyAppState, LOCATION_ICON_DISPLAY_DURATION_MILLIS)) {
            clearLocationIcon(proxyAppState, i, str);
            android.util.Log.w("GnssVisibilityControl", "Failed to show location icon for the full duration for notification: " + nfwNotification);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleLocationIconTimeout, reason: merged with bridge method [inline-methods] */
    public void lambda$showLocationIcon$5(java.lang.String str) {
        android.content.pm.ApplicationInfo proxyAppInfo = getProxyAppInfo(str);
        if (proxyAppInfo != null) {
            clearLocationIcon(this.mProxyAppsState.get(str), proxyAppInfo.uid, str);
        }
    }

    private void clearLocationIcon(@android.annotation.Nullable com.android.server.location.gnss.GnssVisibilityControl.ProxyAppState proxyAppState, int i, java.lang.String str) {
        updateLocationIcon(false, i, str);
        if (proxyAppState != null) {
            proxyAppState.mIsLocationIconOn = false;
        }
        if (DEBUG) {
            android.util.Log.d("GnssVisibilityControl", "Location icon off. Uid: " + i + ", proxyAppPkgName: " + str);
        }
    }

    private boolean updateLocationIcon(boolean z, int i, java.lang.String str) {
        if (z) {
            if (this.mAppOps.startOpNoThrow(41, i, str) != 0) {
                return false;
            }
            if (this.mAppOps.startOpNoThrow(42, i, str) != 0) {
                this.mAppOps.finishOp(41, i, str);
                return false;
            }
            return true;
        }
        this.mAppOps.finishOp(41, i, str);
        this.mAppOps.finishOp(42, i, str);
        return true;
    }

    private void handleEmergencyNfwNotification(com.android.server.location.gnss.GnssVisibilityControl.NfwNotification nfwNotification) {
        boolean z;
        boolean z2 = true;
        if (nfwNotification.isRequestAccepted()) {
            z = false;
        } else {
            android.util.Log.e("GnssVisibilityControl", "Emergency non-framework location request incorrectly rejected. Notification: " + nfwNotification);
            z = true;
        }
        if (this.mNiHandler.getInEmergency(EMERGENCY_EXTENSION_FOR_MISMATCH)) {
            z2 = z;
        } else {
            android.util.Log.w("GnssVisibilityControl", "Emergency state mismatch. Device currently not in user initiated emergency session. Notification: " + nfwNotification);
        }
        logEvent(nfwNotification, z2);
        if (nfwNotification.isLocationProvided()) {
            postEmergencyLocationUserNotification(nfwNotification);
        }
    }

    private void postEmergencyLocationUserNotification(com.android.server.location.gnss.GnssVisibilityControl.NfwNotification nfwNotification) {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.mContext.getSystemService("notification");
        if (notificationManager == null) {
            android.util.Log.w("GnssVisibilityControl", "Could not notify user of emergency location request. Notification: " + nfwNotification);
            return;
        }
        notificationManager.notifyAsUser(null, 0, createEmergencyLocationUserNotification(this.mContext), android.os.UserHandle.ALL);
    }

    private static android.app.Notification createEmergencyLocationUserNotification(android.content.Context context) {
        java.lang.String string = context.getString(android.R.string.gadget_host_error_inflating);
        java.lang.String string2 = context.getString(android.R.string.font_family_display_2_material);
        return new android.app.Notification.Builder(context, com.android.internal.notification.SystemNotificationChannels.NETWORK_STATUS).setSmallIcon(android.R.drawable.stat_sys_download_anim4).setWhen(0L).setOngoing(false).setAutoCancel(true).setColor(context.getColor(android.R.color.system_notification_accent_color)).setDefaults(0).setTicker(string + " (" + string2 + ")").setContentTitle(string).setContentText(string2).build();
    }

    private void logEvent(com.android.server.location.gnss.GnssVisibilityControl.NfwNotification nfwNotification, boolean z) {
        com.android.internal.util.FrameworkStatsLog.write(131, nfwNotification.mProxyAppPackageName, nfwNotification.mProtocolStack, nfwNotification.mOtherProtocolStackName, nfwNotification.mRequestor, nfwNotification.mRequestorId, nfwNotification.mResponseType, nfwNotification.mInEmergencyMode, nfwNotification.mIsCachedLocation, z);
    }

    private void runOnHandler(java.lang.Runnable runnable) {
        this.mWakeLock.acquire(60000L);
        if (!this.mHandler.post(runEventAndReleaseWakeLock(runnable))) {
            this.mWakeLock.release();
        }
    }

    private java.lang.Runnable runEventAndReleaseWakeLock(final java.lang.Runnable runnable) {
        return new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssVisibilityControl.this.lambda$runEventAndReleaseWakeLock$6(runnable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runEventAndReleaseWakeLock$6(java.lang.Runnable runnable) {
        try {
            runnable.run();
        } finally {
            this.mWakeLock.release();
        }
    }
}
