package com.nvidia;

/* loaded from: classes5.dex */
public class NvAppProfileService {
    private static final java.lang.String APP_START_ACTION = "com.nvidia.NvAppProfileService.action.APP_START";
    private static final java.lang.String APP_START_TARGET_PACKAGE = "com.nvidia.stats";
    private static final java.lang.String FEATURE_FAN_ON_DEVICE = "nvidia.feature.fan_on_device";
    private static final java.lang.String FEATURE_POWER_BUDGET_CONTROL = "nvidia.feature.power_budget_control";
    private static final java.lang.String NvPowerModeProperty = "persist.vendor.sys.NV_POWER_MODE";
    private static final java.lang.String TAG = "NvAppProfileService";
    private final com.nvidia.NvAppProfiles mAppProfile;
    private final android.content.Context mContext;
    private boolean mInitAppProfiles = false;
    private boolean mFanCapEnabled = false;
    private boolean mPbcEnabled = false;

    public NvAppProfileService(android.content.Context context) {
        android.content.Context applicationContext = context.getApplicationContext();
        if (applicationContext == null) {
            this.mContext = context;
        } else {
            this.mContext = applicationContext;
        }
        this.mAppProfile = new com.nvidia.NvAppProfiles(this.mContext);
    }

    private static java.lang.String getPackageName(java.lang.String str) {
        int indexOf = str.indexOf(47);
        if (indexOf < 0) {
            android.util.Log.e(TAG, "appName does not contain '/'. The packageName cannot be extracted from appName!");
            return null;
        }
        return str.substring(0, indexOf);
    }

    public boolean canForceHwUi(java.lang.String str) {
        if (str == null) {
            return false;
        }
        java.lang.String packageName = getPackageName(str);
        if (packageName == null && this.mAppProfile.getApplicationProfile(packageName, com.nvidia.profilemanager.NvAppProfileSettingId.FORCE_HW_UI) <= 0) {
            return false;
        }
        return true;
    }

    public boolean getAppProfileFRCEnable(java.lang.String str) {
        return str != null && this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.VIDEO_FRC_ENABLE) == 1;
    }

    public boolean getAppProfileCreateSecureDecoder(java.lang.String str) {
        return str != null && this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.VIDEO_SECURE_DECODE) == 1;
    }

    public boolean getAppProfileTSFilterEnable(java.lang.String str) {
        return str != null && this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.VIDEO_TS_FILTERING) == 1;
    }

    public boolean getAppProfileNvidiaCertification(java.lang.String str) {
        return str != null && this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.NVIDIA_VIDEO_CERTIFICATION_ENABLED) == 1;
    }

    public boolean getAppProfileDisableApp(java.lang.String str) {
        return str != null && this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.DISABLE_APP) == 1;
    }

    private int getAppProfileCpuScalingMinFreq(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.SCALING_MIN_FREQ);
    }

    private int getAppProfileCpuCoreBias(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.CORE_BIAS);
    }

    private int getAppProfileGpuScaling(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.GPU_SCALING);
    }

    private int getAppProfileCpuMaxNormalFreq(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.CPU_FREQ_BIAS);
    }

    private int getAppProfileCpuMaxNormalFreqPercent(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.MAX_CPU_FREQ_PCT);
    }

    private int getAppProfileCpuMinCore(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.MIN_CPU_CORES);
    }

    private int getAppProfileCpuMaxCore(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.MAX_CPU_CORES);
    }

    private int getAppProfileGpuCbusCapLevel(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.GPU_CORE_CAP);
    }

    private int getAppProfileEdpMode(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.EDP_MODE);
    }

    private int getAppProfilePbcPwr(java.lang.String str) {
        if (this.mPbcEnabled) {
            return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.PBC_PWR_LIMIT);
        }
        return -1;
    }

    private int getAppProfileFanCap(java.lang.String str) {
        if (this.mFanCapEnabled) {
            return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.FAN_PWM_CAP);
        }
        return -1;
    }

    private int getAppProfileVoltTempMode(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.VOLT_TEMP_MODE);
    }

    private int getAppProfileAggresivePrismEnable(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.AGGRESSIVE_PRISM_ENABLE);
    }

    private int getAppProfileDevicePowerMode(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.SYSTEM_POWER_MODE);
    }

    public java.lang.String getAppProfileRegionEnableList(java.lang.String str) {
        return this.mAppProfile.getApplicationProfileString(str, com.nvidia.profilemanager.NvAppProfileSettingId.SET_REGION_LIST);
    }

    public int getAppProfileNvidiaBBCApps(java.lang.String str) {
        return this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.BBC_APPS);
    }

    public java.util.List<java.lang.String> getAppProfileForceQueryAppList() {
        java.lang.String applicationProfileString = this.mAppProfile.getApplicationProfileString("com.nvidia.shield.force_query_app_list", com.nvidia.profilemanager.NvAppProfileSettingId.FORCE_QUERY_PACKAGES);
        if (android.text.TextUtils.isEmpty(applicationProfileString)) {
            return null;
        }
        return java.util.Arrays.asList(applicationProfileString.split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR));
    }

    public java.util.List<java.lang.String> getAppProfileAutoMediaScanPackages() {
        java.lang.String applicationProfileString = this.mAppProfile.getApplicationProfileString("com.nvidia.shield.auto_media_scan_packages", com.nvidia.profilemanager.NvAppProfileSettingId.AUTO_MEDIA_SCAN_PACKAGES);
        if (android.text.TextUtils.isEmpty(applicationProfileString)) {
            return null;
        }
        return java.util.Arrays.asList(applicationProfileString.split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR));
    }

    private int retrievePowerMode() {
        java.lang.String str = android.os.SystemProperties.get("persist.vendor.sys.NV_POWER_MODE");
        if (str != null) {
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    private void setGpuModeSetting(java.lang.String str) {
        this.mAppProfile.getApplicationProfile(str, com.nvidia.profilemanager.NvAppProfileSettingId.GPU_MODESET_ENABLE);
    }

    public void setAppProfile(java.lang.String str) {
        if (!this.mInitAppProfiles) {
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            this.mPbcEnabled = packageManager.hasSystemFeature(FEATURE_POWER_BUDGET_CONTROL);
            this.mFanCapEnabled = packageManager.hasSystemFeature(FEATURE_FAN_ON_DEVICE);
            android.util.Log.w(TAG, "Enabled");
            this.mInitAppProfiles = true;
        }
        this.mAppProfile.powerHint(str);
        android.content.Intent intent = new android.content.Intent(APP_START_ACTION);
        intent.setPackage(APP_START_TARGET_PACKAGE);
        intent.putExtra("AppStartId", str);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.CURRENT, "nvidia.permission.READ_APP_START_INFO");
    }
}
