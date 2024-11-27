package com.android.server;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes.dex */
public class NetworkScorerAppManager {
    private final android.content.Context mContext;
    private final com.android.server.NetworkScorerAppManager.SettingsFacade mSettingsFacade;
    private static final java.lang.String TAG = "NetworkScorerAppManager";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final boolean VERBOSE = android.util.Log.isLoggable(TAG, 2);

    public NetworkScorerAppManager(android.content.Context context) {
        this(context, new com.android.server.NetworkScorerAppManager.SettingsFacade());
    }

    @com.android.internal.annotations.VisibleForTesting
    public NetworkScorerAppManager(android.content.Context context, com.android.server.NetworkScorerAppManager.SettingsFacade settingsFacade) {
        this.mContext = context;
        this.mSettingsFacade = settingsFacade;
    }

    @com.android.internal.annotations.VisibleForTesting
    public java.util.List<android.net.NetworkScorerAppData> getAllValidScorers() {
        if (VERBOSE) {
            android.util.Log.v(TAG, "getAllValidScorers()");
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.Intent intent = new android.content.Intent("android.net.action.RECOMMEND_NETWORKS");
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 128);
        if (queryIntentServices == null || queryIntentServices.isEmpty()) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Found 0 Services able to handle " + intent);
            }
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < queryIntentServices.size(); i++) {
            android.content.pm.ServiceInfo serviceInfo = queryIntentServices.get(i).serviceInfo;
            if (hasPermissions(serviceInfo.applicationInfo.uid, serviceInfo.packageName)) {
                if (VERBOSE) {
                    android.util.Log.v(TAG, serviceInfo.packageName + " is a valid scorer/recommender.");
                }
                arrayList.add(new android.net.NetworkScorerAppData(serviceInfo.applicationInfo.uid, new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name), getRecommendationServiceLabel(serviceInfo, packageManager), findUseOpenWifiNetworksActivity(serviceInfo), getNetworkAvailableNotificationChannelId(serviceInfo)));
            } else if (VERBOSE) {
                android.util.Log.v(TAG, serviceInfo.packageName + " is NOT a valid scorer/recommender.");
            }
        }
        return arrayList;
    }

    @android.annotation.Nullable
    private java.lang.String getRecommendationServiceLabel(android.content.pm.ServiceInfo serviceInfo, android.content.pm.PackageManager packageManager) {
        if (serviceInfo.metaData != null) {
            java.lang.String string = serviceInfo.metaData.getString("android.net.scoring.recommendation_service_label");
            if (!android.text.TextUtils.isEmpty(string)) {
                return string;
            }
        }
        java.lang.CharSequence loadLabel = serviceInfo.loadLabel(packageManager);
        if (loadLabel == null) {
            return null;
        }
        return loadLabel.toString();
    }

    @android.annotation.Nullable
    private android.content.ComponentName findUseOpenWifiNetworksActivity(android.content.pm.ServiceInfo serviceInfo) {
        if (serviceInfo.metaData == null) {
            if (DEBUG) {
                android.util.Log.d(TAG, "No metadata found on " + serviceInfo.getComponentName());
            }
            return null;
        }
        java.lang.String string = serviceInfo.metaData.getString("android.net.wifi.use_open_wifi_package");
        if (android.text.TextUtils.isEmpty(string)) {
            if (DEBUG) {
                android.util.Log.d(TAG, "No use_open_wifi_package metadata found on " + serviceInfo.getComponentName());
            }
            return null;
        }
        android.content.Intent intent = new android.content.Intent("android.net.scoring.CUSTOM_ENABLE").setPackage(string);
        android.content.pm.ResolveInfo resolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 0);
        if (VERBOSE) {
            android.util.Log.d(TAG, "Resolved " + intent + " to " + resolveActivity);
        }
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            return null;
        }
        return resolveActivity.activityInfo.getComponentName();
    }

    @android.annotation.Nullable
    private static java.lang.String getNetworkAvailableNotificationChannelId(android.content.pm.ServiceInfo serviceInfo) {
        if (serviceInfo.metaData == null) {
            if (DEBUG) {
                android.util.Log.d(TAG, "No metadata found on " + serviceInfo.getComponentName());
                return null;
            }
            return null;
        }
        return serviceInfo.metaData.getString("android.net.wifi.notification_channel_id_network_available");
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public android.net.NetworkScorerAppData getActiveScorer() {
        if (getNetworkRecommendationsEnabledSetting() == -1) {
            return null;
        }
        return getScorer(getNetworkRecommendationsPackage());
    }

    private android.net.NetworkScorerAppData getScorer(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        java.util.List<android.net.NetworkScorerAppData> allValidScorers = getAllValidScorers();
        for (int i = 0; i < allValidScorers.size(); i++) {
            android.net.NetworkScorerAppData networkScorerAppData = allValidScorers.get(i);
            if (networkScorerAppData.getRecommendationServicePackageName().equals(str)) {
                return networkScorerAppData;
            }
        }
        return null;
    }

    private boolean hasPermissions(int i, java.lang.String str) {
        return hasScoreNetworksPermission(str) && canAccessLocation(i, str);
    }

    private boolean hasScoreNetworksPermission(java.lang.String str) {
        return this.mContext.getPackageManager().checkPermission("android.permission.SCORE_NETWORKS", str) == 0;
    }

    private boolean canAccessLocation(int i, java.lang.String str) {
        return isLocationModeEnabled() && android.content.PermissionChecker.checkPermissionForPreflight(this.mContext, "android.permission.ACCESS_COARSE_LOCATION", -1, i, str) == 0;
    }

    private boolean isLocationModeEnabled() {
        return this.mSettingsFacade.getSecureInt(this.mContext, "location_mode", 0) != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean setActiveScorer(java.lang.String str) {
        java.lang.String networkRecommendationsPackage = getNetworkRecommendationsPackage();
        if (android.text.TextUtils.equals(networkRecommendationsPackage, str)) {
            return true;
        }
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Log.i(TAG, "Network scorer forced off, was: " + networkRecommendationsPackage);
            setNetworkRecommendationsPackage(null);
            setNetworkRecommendationsEnabledSetting(-1);
            return true;
        }
        if (getScorer(str) != null) {
            android.util.Log.i(TAG, "Changing network scorer from " + networkRecommendationsPackage + " to " + str);
            setNetworkRecommendationsPackage(str);
            setNetworkRecommendationsEnabledSetting(1);
            return true;
        }
        android.util.Log.w(TAG, "Requested network scorer is not valid: " + str);
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void updateState() {
        if (getNetworkRecommendationsEnabledSetting() == -1) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Recommendations forced off.");
                return;
            }
            return;
        }
        java.lang.String networkRecommendationsPackage = getNetworkRecommendationsPackage();
        int i = 1;
        if (getScorer(networkRecommendationsPackage) != null) {
            if (VERBOSE) {
                android.util.Log.v(TAG, networkRecommendationsPackage + " is the active scorer.");
            }
            setNetworkRecommendationsEnabledSetting(1);
            return;
        }
        java.lang.String defaultPackageSetting = getDefaultPackageSetting();
        if (!android.text.TextUtils.equals(networkRecommendationsPackage, defaultPackageSetting) && getScorer(defaultPackageSetting) != null) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Defaulting the network recommendations app to: " + defaultPackageSetting);
            }
            setNetworkRecommendationsPackage(defaultPackageSetting);
        } else {
            i = 0;
        }
        setNetworkRecommendationsEnabledSetting(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void migrateNetworkScorerAppSettingIfNeeded() {
        android.net.NetworkScorerAppData activeScorer;
        java.lang.String string = this.mSettingsFacade.getString(this.mContext, "network_scorer_app");
        if (android.text.TextUtils.isEmpty(string) || (activeScorer = getActiveScorer()) == null) {
            return;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "Migrating Settings.Global.NETWORK_SCORER_APP (" + string + ")...");
        }
        android.content.ComponentName enableUseOpenWifiActivity = activeScorer.getEnableUseOpenWifiActivity();
        if (android.text.TextUtils.isEmpty(this.mSettingsFacade.getString(this.mContext, "use_open_wifi_package")) && enableUseOpenWifiActivity != null && string.equals(enableUseOpenWifiActivity.getPackageName())) {
            this.mSettingsFacade.putString(this.mContext, "use_open_wifi_package", string);
            if (DEBUG) {
                android.util.Log.d(TAG, "Settings.Global.USE_OPEN_WIFI_PACKAGE set to '" + string + "'.");
            }
        }
        this.mSettingsFacade.putString(this.mContext, "network_scorer_app", null);
        if (DEBUG) {
            android.util.Log.d(TAG, "Settings.Global.NETWORK_SCORER_APP migration complete.");
            android.util.Log.d(TAG, "Settings.Global.USE_OPEN_WIFI_PACKAGE is: '" + this.mSettingsFacade.getString(this.mContext, "use_open_wifi_package") + "'.");
        }
    }

    private java.lang.String getDefaultPackageSetting() {
        return this.mContext.getResources().getString(android.R.string.config_defaultFieldClassificationService);
    }

    private java.lang.String getNetworkRecommendationsPackage() {
        return this.mSettingsFacade.getString(this.mContext, "network_recommendations_package");
    }

    private void setNetworkRecommendationsPackage(java.lang.String str) {
        this.mSettingsFacade.putString(this.mContext, "network_recommendations_package", str);
        if (VERBOSE) {
            android.util.Log.d(TAG, "network_recommendations_package set to " + str);
        }
    }

    private int getNetworkRecommendationsEnabledSetting() {
        return this.mSettingsFacade.getInt(this.mContext, "network_recommendations_enabled", 0);
    }

    private void setNetworkRecommendationsEnabledSetting(int i) {
        this.mSettingsFacade.putInt(this.mContext, "network_recommendations_enabled", i);
        if (VERBOSE) {
            android.util.Log.d(TAG, "network_recommendations_enabled set to " + i);
        }
    }

    public static class SettingsFacade {
        public boolean putString(android.content.Context context, java.lang.String str, java.lang.String str2) {
            return android.provider.Settings.Global.putString(context.getContentResolver(), str, str2);
        }

        public java.lang.String getString(android.content.Context context, java.lang.String str) {
            return android.provider.Settings.Global.getString(context.getContentResolver(), str);
        }

        public boolean putInt(android.content.Context context, java.lang.String str, int i) {
            return android.provider.Settings.Global.putInt(context.getContentResolver(), str, i);
        }

        public int getInt(android.content.Context context, java.lang.String str, int i) {
            return android.provider.Settings.Global.getInt(context.getContentResolver(), str, i);
        }

        public int getSecureInt(android.content.Context context, java.lang.String str, int i) {
            android.content.ContentResolver contentResolver = context.getContentResolver();
            return android.provider.Settings.Secure.getIntForUser(contentResolver, str, i, contentResolver.getUserId());
        }
    }
}
