package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class HdmiCecConfig {
    private static final java.lang.String CONFIG_FILE = "cec_config.xml";
    private static final java.lang.String ETC_DIR = "etc";
    private static final java.lang.String SHARED_PREFS_DIR = "shared_prefs";
    private static final java.lang.String SHARED_PREFS_NAME = "cec_config.xml";
    private static final int STORAGE_GLOBAL_SETTINGS = 1;
    private static final int STORAGE_SHARED_PREFS = 2;
    private static final int STORAGE_SYSPROPS = 0;
    private static final java.lang.String TAG = "HdmiCecConfig";
    private static final java.lang.String VALUE_TYPE_INT = "int";
    private static final java.lang.String VALUE_TYPE_STRING = "string";

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<com.android.server.hdmi.HdmiCecConfig.Setting, android.util.ArrayMap<com.android.server.hdmi.HdmiCecConfig.SettingChangeListener, java.util.concurrent.Executor>> mSettingChangeListeners;
    private java.util.LinkedHashMap<java.lang.String, com.android.server.hdmi.HdmiCecConfig.Setting> mSettings;

    @android.annotation.NonNull
    private final com.android.server.hdmi.HdmiCecConfig.StorageAdapter mStorageAdapter;

    public interface SettingChangeListener {
        void onChange(@android.annotation.NonNull java.lang.String str);
    }

    private @interface Storage {
    }

    private @interface ValueType {
    }

    public static class VerificationException extends java.lang.RuntimeException {
        public VerificationException(java.lang.String str) {
            super(str);
        }
    }

    public static class StorageAdapter {

        @android.annotation.NonNull
        private final android.content.Context mContext;

        @android.annotation.NonNull
        private final android.content.SharedPreferences mSharedPrefs;

        StorageAdapter(@android.annotation.NonNull android.content.Context context) {
            this.mContext = context;
            this.mSharedPrefs = this.mContext.createDeviceProtectedStorageContext().getSharedPreferences(new java.io.File(new java.io.File(android.os.Environment.getDataSystemDirectory(), com.android.server.hdmi.HdmiCecConfig.SHARED_PREFS_DIR), "cec_config.xml"), 0);
        }

        public java.lang.String retrieveSystemProperty(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            return android.os.SystemProperties.get(str, str2);
        }

        public void storeSystemProperty(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            android.os.SystemProperties.set(str, str2);
        }

        public java.lang.String retrieveGlobalSetting(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), str);
            return string != null ? string : str2;
        }

        public void storeGlobalSetting(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            android.provider.Settings.Global.putString(this.mContext.getContentResolver(), str, str2);
        }

        public java.lang.String retrieveSharedPref(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            return this.mSharedPrefs.getString(str, str2);
        }

        public void storeSharedPref(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            this.mSharedPrefs.edit().putString(str, str2).apply();
        }
    }

    private class Value {
        private final java.lang.Integer mIntValue;
        private final java.lang.String mStringValue;

        Value(@android.annotation.NonNull java.lang.String str) {
            this.mStringValue = str;
            this.mIntValue = null;
        }

        Value(@android.annotation.NonNull java.lang.Integer num) {
            this.mStringValue = null;
            this.mIntValue = num;
        }

        java.lang.String getStringValue() {
            return this.mStringValue;
        }

        java.lang.Integer getIntValue() {
            return this.mIntValue;
        }
    }

    protected class Setting {

        @android.annotation.NonNull
        private final android.content.Context mContext;

        @android.annotation.NonNull
        private final java.lang.String mName;
        private final boolean mUserConfigurable;
        private com.android.server.hdmi.HdmiCecConfig.Value mDefaultValue = null;
        private java.util.List<com.android.server.hdmi.HdmiCecConfig.Value> mAllowedValues = new java.util.ArrayList();

        Setting(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, int i) {
            this.mContext = context;
            this.mName = str;
            this.mUserConfigurable = this.mContext.getResources().getBoolean(i);
        }

        public java.lang.String getName() {
            return this.mName;
        }

        @com.android.server.hdmi.HdmiCecConfig.ValueType
        public java.lang.String getValueType() {
            if (getDefaultValue().getStringValue() != null) {
                return com.android.server.hdmi.HdmiCecConfig.VALUE_TYPE_STRING;
            }
            return com.android.server.hdmi.HdmiCecConfig.VALUE_TYPE_INT;
        }

        public com.android.server.hdmi.HdmiCecConfig.Value getDefaultValue() {
            if (this.mDefaultValue == null) {
                throw new com.android.server.hdmi.HdmiCecConfig.VerificationException("Invalid CEC setup for '" + getName() + "' setting. Setting has no default value.");
            }
            return this.mDefaultValue;
        }

        public boolean getUserConfigurable() {
            return this.mUserConfigurable;
        }

        private void registerValue(@android.annotation.NonNull com.android.server.hdmi.HdmiCecConfig.Value value, int i, int i2) {
            if (this.mContext.getResources().getBoolean(i)) {
                this.mAllowedValues.add(value);
                if (this.mContext.getResources().getBoolean(i2)) {
                    if (this.mDefaultValue != null) {
                        android.util.Slog.e(com.android.server.hdmi.HdmiCecConfig.TAG, "Failed to set '" + value + "' as a default for '" + getName() + "': Setting already has a default ('" + this.mDefaultValue + "').");
                        return;
                    }
                    this.mDefaultValue = value;
                }
            }
        }

        public void registerValue(@android.annotation.NonNull java.lang.String str, int i, int i2) {
            registerValue(com.android.server.hdmi.HdmiCecConfig.this.new Value(str), i, i2);
        }

        public void registerValue(int i, int i2, int i3) {
            registerValue(com.android.server.hdmi.HdmiCecConfig.this.new Value(java.lang.Integer.valueOf(i)), i2, i3);
        }

        public java.util.List<com.android.server.hdmi.HdmiCecConfig.Value> getAllowedValues() {
            return this.mAllowedValues;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    HdmiCecConfig(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.hdmi.HdmiCecConfig.StorageAdapter storageAdapter) {
        this.mLock = new java.lang.Object();
        this.mSettingChangeListeners = new android.util.ArrayMap<>();
        this.mSettings = new java.util.LinkedHashMap<>();
        this.mContext = context;
        this.mStorageAdapter = storageAdapter;
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting = registerSetting("hdmi_cec_enabled", android.R.bool.config_cecHdmiCecEnabled_userConfigurable);
        registerSetting.registerValue(1, android.R.bool.config_cecHdmiCecControlEnabled_allowed, android.R.bool.config_cecHdmiCecControlEnabled_default);
        registerSetting.registerValue(0, android.R.bool.config_cecHdmiCecControlDisabled_allowed, android.R.bool.config_cecHdmiCecControlDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting2 = registerSetting("hdmi_cec_version", android.R.bool.config_cecHdmiCecVersion_userConfigurable);
        registerSetting2.registerValue(5, android.R.bool.config_cecHdmiCecVersion14b_allowed, android.R.bool.config_cecHdmiCecVersion14b_default);
        registerSetting2.registerValue(6, android.R.bool.config_cecHdmiCecVersion20_allowed, android.R.bool.config_cecHdmiCecVersion20_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting3 = registerSetting("routing_control", android.R.bool.config_cecRoutingControl_userConfigurable);
        registerSetting3.registerValue(1, android.R.bool.config_cecRoutingControlEnabled_allowed, android.R.bool.config_cecRoutingControlEnabled_default);
        registerSetting3.registerValue(0, android.R.bool.config_cecRoutingControlDisabled_allowed, android.R.bool.config_cecRoutingControlDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting4 = registerSetting("soundbar_mode", android.R.bool.config_cecSoundbarMode_userConfigurable);
        registerSetting4.registerValue(1, android.R.bool.config_cecSoundbarModeEnabled_allowed, android.R.bool.config_cecSoundbarModeEnabled_default);
        registerSetting4.registerValue(0, android.R.bool.config_cecSoundbarModeDisabled_allowed, android.R.bool.config_cecSoundbarModeDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting5 = registerSetting("power_control_mode", android.R.bool.config_cecPowerControlMode_userConfigurable);
        registerSetting5.registerValue("to_tv", android.R.bool.config_cecPowerControlModeTv_allowed, android.R.bool.config_cecPowerControlModeTv_default);
        registerSetting5.registerValue("broadcast", android.R.bool.config_cecPowerControlModeBroadcast_allowed, android.R.bool.config_cecPowerControlModeBroadcast_default);
        registerSetting5.registerValue("none", android.R.bool.config_cecPowerControlModeNone_allowed, android.R.bool.config_cecPowerControlModeNone_default);
        registerSetting5.registerValue("to_tv_and_audio_system", android.R.bool.config_cecPowerControlModeTvAndAudioSystem_allowed, android.R.bool.config_cecPowerControlModeTvAndAudioSystem_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting6 = registerSetting("power_state_change_on_active_source_lost", android.R.bool.config_cecPowerStateChangeOnActiveSourceLost_userConfigurable);
        registerSetting6.registerValue("none", android.R.bool.config_cecPowerStateChangeOnActiveSourceLostNone_allowed, android.R.bool.config_cecPowerStateChangeOnActiveSourceLostNone_default);
        registerSetting6.registerValue("standby_now", android.R.bool.config_cecPowerStateChangeOnActiveSourceLostStandbyNow_allowed, android.R.bool.config_cecPowerStateChangeOnActiveSourceLostStandbyNow_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting7 = registerSetting("system_audio_control", android.R.bool.config_cecSystemAudioControl_userConfigurable);
        registerSetting7.registerValue(1, android.R.bool.config_cecSystemAudioControlEnabled_allowed, android.R.bool.config_cecSystemAudioControlEnabled_default);
        registerSetting7.registerValue(0, android.R.bool.config_cecSystemAudioControlDisabled_allowed, android.R.bool.config_cecSystemAudioControlDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting8 = registerSetting("system_audio_mode_muting", android.R.bool.config_cecSystemAudioModeMuting_userConfigurable);
        registerSetting8.registerValue(1, android.R.bool.config_cecSystemAudioModeMutingEnabled_allowed, android.R.bool.config_cecSystemAudioModeMutingEnabled_default);
        registerSetting8.registerValue(0, android.R.bool.config_cecSystemAudioModeMutingDisabled_allowed, android.R.bool.config_cecSystemAudioModeMutingDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting9 = registerSetting("volume_control_enabled", android.R.bool.config_cecVolumeControlMode_userConfigurable);
        registerSetting9.registerValue(1, android.R.bool.config_cecVolumeControlModeEnabled_allowed, android.R.bool.config_cecVolumeControlModeEnabled_default);
        registerSetting9.registerValue(0, android.R.bool.config_cecVolumeControlModeDisabled_allowed, android.R.bool.config_cecVolumeControlModeDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting10 = registerSetting("tv_wake_on_one_touch_play", android.R.bool.config_cecTvWakeOnOneTouchPlay_userConfigurable);
        registerSetting10.registerValue(1, android.R.bool.config_cecTvWakeOnOneTouchPlayEnabled_allowed, android.R.bool.config_cecTvWakeOnOneTouchPlayEnabled_default);
        registerSetting10.registerValue(0, android.R.bool.config_cecTvWakeOnOneTouchPlayDisabled_allowed, android.R.bool.config_cecTvWakeOnOneTouchPlayDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting11 = registerSetting("tv_send_standby_on_sleep", android.R.bool.config_cecTvSendStandbyOnSleep_userConfigurable);
        registerSetting11.registerValue(1, android.R.bool.config_cecTvSendStandbyOnSleepEnabled_allowed, android.R.bool.config_cecTvSendStandbyOnSleepEnabled_default);
        registerSetting11.registerValue(0, android.R.bool.config_cecTvSendStandbyOnSleepDisabled_allowed, android.R.bool.config_cecTvSendStandbyOnSleepDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting12 = registerSetting("set_menu_language", android.R.bool.config_cecSetMenuLanguage_userConfigurable);
        registerSetting12.registerValue(1, android.R.bool.config_cecSetMenuLanguageEnabled_allowed, android.R.bool.config_cecSetMenuLanguageEnabled_default);
        registerSetting12.registerValue(0, android.R.bool.config_cecSetMenuLanguageDisabled_allowed, android.R.bool.config_cecSetMenuLanguageDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting13 = registerSetting("rc_profile_tv", android.R.bool.config_cecRcProfileTv_userConfigurable);
        registerSetting13.registerValue(0, android.R.bool.config_cecRcProfileTvNone_allowed, android.R.bool.config_cecRcProfileTvNone_default);
        registerSetting13.registerValue(2, android.R.bool.config_cecRcProfileTvOne_allowed, android.R.bool.config_cecRcProfileTvOne_default);
        registerSetting13.registerValue(6, android.R.bool.config_cecRcProfileTvTwo_allowed, android.R.bool.config_cecRcProfileTvTwo_default);
        registerSetting13.registerValue(10, android.R.bool.config_cecRcProfileTvThree_allowed, android.R.bool.config_cecRcProfileTvThree_default);
        registerSetting13.registerValue(14, android.R.bool.config_cecRcProfileTvFour_allowed, android.R.bool.config_cecRcProfileTvFour_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting14 = registerSetting("rc_profile_source_handles_root_menu", android.R.bool.config_cecRcProfileSourceRootMenu_userConfigurable);
        registerSetting14.registerValue(1, android.R.bool.config_cecRcProfileSourceRootMenuHandled_allowed, android.R.bool.config_cecRcProfileSourceRootMenuHandled_default);
        registerSetting14.registerValue(0, android.R.bool.config_cecRcProfileSourceRootMenuNotHandled_allowed, android.R.bool.config_cecRcProfileSourceRootMenuNotHandled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting15 = registerSetting("rc_profile_source_handles_setup_menu", android.R.bool.config_cecRcProfileSourceSetupMenu_userConfigurable);
        registerSetting15.registerValue(1, android.R.bool.config_cecRcProfileSourceSetupMenuHandled_allowed, android.R.bool.config_cecRcProfileSourceSetupMenuHandled_default);
        registerSetting15.registerValue(0, android.R.bool.config_cecRcProfileSourceSetupMenuNotHandled_allowed, android.R.bool.config_cecRcProfileSourceSetupMenuNotHandled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting16 = registerSetting("rc_profile_source_handles_contents_menu", android.R.bool.config_cecRcProfileSourceContentsMenu_userConfigurable);
        registerSetting16.registerValue(1, android.R.bool.config_cecRcProfileSourceContentsMenuHandled_allowed, android.R.bool.config_cecRcProfileSourceContentsMenuHandled_default);
        registerSetting16.registerValue(0, android.R.bool.config_cecRcProfileSourceContentsMenuNotHandled_allowed, android.R.bool.config_cecRcProfileSourceContentsMenuNotHandled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting17 = registerSetting("rc_profile_source_handles_top_menu", android.R.bool.config_cecRcProfileSourceTopMenu_userConfigurable);
        registerSetting17.registerValue(1, android.R.bool.config_cecRcProfileSourceTopMenuHandled_allowed, android.R.bool.config_cecRcProfileSourceTopMenuHandled_default);
        registerSetting17.registerValue(0, android.R.bool.config_cecRcProfileSourceTopMenuNotHandled_allowed, android.R.bool.config_cecRcProfileSourceTopMenuNotHandled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting18 = registerSetting("rc_profile_source_handles_media_context_sensitive_menu", android.R.bool.config_cecRcProfileSourceMediaContextSensitiveMenu_userConfigurable);
        registerSetting18.registerValue(1, android.R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuHandled_allowed, android.R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuHandled_default);
        registerSetting18.registerValue(0, android.R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuNotHandled_allowed, android.R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuNotHandled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting19 = registerSetting("query_sad_lpcm", android.R.bool.config_cecQuerySadLpcm_userConfigurable);
        registerSetting19.registerValue(1, android.R.bool.config_cecQuerySadLpcmEnabled_allowed, android.R.bool.config_cecQuerySadLpcmEnabled_default);
        registerSetting19.registerValue(0, android.R.bool.config_cecQuerySadLpcmDisabled_allowed, android.R.bool.config_cecQuerySadLpcmDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting20 = registerSetting("query_sad_dd", android.R.bool.config_cecQuerySadDd_userConfigurable);
        registerSetting20.registerValue(1, android.R.bool.config_cecQuerySadDdEnabled_allowed, android.R.bool.config_cecQuerySadDdEnabled_default);
        registerSetting20.registerValue(0, android.R.bool.config_cecQuerySadDdDisabled_allowed, android.R.bool.config_cecQuerySadDdDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting21 = registerSetting("query_sad_mpeg1", android.R.bool.config_cecQuerySadMpeg1_userConfigurable);
        registerSetting21.registerValue(1, android.R.bool.config_cecQuerySadMpeg1Enabled_allowed, android.R.bool.config_cecQuerySadMpeg1Enabled_default);
        registerSetting21.registerValue(0, android.R.bool.config_cecQuerySadMpeg1Disabled_allowed, android.R.bool.config_cecQuerySadMpeg1Disabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting22 = registerSetting("query_sad_mp3", android.R.bool.config_cecQuerySadMp3_userConfigurable);
        registerSetting22.registerValue(1, android.R.bool.config_cecQuerySadMp3Enabled_allowed, android.R.bool.config_cecQuerySadMp3Enabled_default);
        registerSetting22.registerValue(0, android.R.bool.config_cecQuerySadMp3Disabled_allowed, android.R.bool.config_cecQuerySadMp3Disabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting23 = registerSetting("query_sad_mpeg2", android.R.bool.config_cecQuerySadMpeg2_userConfigurable);
        registerSetting23.registerValue(1, android.R.bool.config_cecQuerySadMpeg2Enabled_allowed, android.R.bool.config_cecQuerySadMpeg2Enabled_default);
        registerSetting23.registerValue(0, android.R.bool.config_cecQuerySadMpeg2Disabled_allowed, android.R.bool.config_cecQuerySadMpeg2Disabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting24 = registerSetting("query_sad_aac", android.R.bool.config_cecQuerySadAac_userConfigurable);
        registerSetting24.registerValue(1, android.R.bool.config_cecQuerySadAacEnabled_allowed, android.R.bool.config_cecQuerySadAacEnabled_default);
        registerSetting24.registerValue(0, android.R.bool.config_cecQuerySadAacDisabled_allowed, android.R.bool.config_cecQuerySadAacDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting25 = registerSetting("query_sad_dts", android.R.bool.config_cecQuerySadDts_userConfigurable);
        registerSetting25.registerValue(1, android.R.bool.config_cecQuerySadDtsEnabled_allowed, android.R.bool.config_cecQuerySadDtsEnabled_default);
        registerSetting25.registerValue(0, android.R.bool.config_cecQuerySadDtsDisabled_allowed, android.R.bool.config_cecQuerySadDtsDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting26 = registerSetting("query_sad_atrac", android.R.bool.config_cecQuerySadAtrac_userConfigurable);
        registerSetting26.registerValue(1, android.R.bool.config_cecQuerySadAtracEnabled_allowed, android.R.bool.config_cecQuerySadAtracEnabled_default);
        registerSetting26.registerValue(0, android.R.bool.config_cecQuerySadAtracDisabled_allowed, android.R.bool.config_cecQuerySadAtracDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting27 = registerSetting("query_sad_onebitaudio", android.R.bool.config_cecQuerySadOnebitaudio_userConfigurable);
        registerSetting27.registerValue(1, android.R.bool.config_cecQuerySadOnebitaudioEnabled_allowed, android.R.bool.config_cecQuerySadOnebitaudioEnabled_default);
        registerSetting27.registerValue(0, android.R.bool.config_cecQuerySadOnebitaudioDisabled_allowed, android.R.bool.config_cecQuerySadOnebitaudioDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting28 = registerSetting("query_sad_ddp", android.R.bool.config_cecQuerySadDdp_userConfigurable);
        registerSetting28.registerValue(1, android.R.bool.config_cecQuerySadDdpEnabled_allowed, android.R.bool.config_cecQuerySadDdpEnabled_default);
        registerSetting28.registerValue(0, android.R.bool.config_cecQuerySadDdpDisabled_allowed, android.R.bool.config_cecQuerySadDdpDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting29 = registerSetting("query_sad_dtshd", android.R.bool.config_cecQuerySadDtshd_userConfigurable);
        registerSetting29.registerValue(1, android.R.bool.config_cecQuerySadDtshdEnabled_allowed, android.R.bool.config_cecQuerySadDtshdEnabled_default);
        registerSetting29.registerValue(0, android.R.bool.config_cecQuerySadDtshdDisabled_allowed, android.R.bool.config_cecQuerySadDtshdDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting30 = registerSetting("query_sad_truehd", android.R.bool.config_cecQuerySadTruehd_userConfigurable);
        registerSetting30.registerValue(1, android.R.bool.config_cecQuerySadTruehdEnabled_allowed, android.R.bool.config_cecQuerySadTruehdEnabled_default);
        registerSetting30.registerValue(0, android.R.bool.config_cecQuerySadTruehdDisabled_allowed, android.R.bool.config_cecQuerySadTruehdDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting31 = registerSetting("query_sad_dst", android.R.bool.config_cecQuerySadDst_userConfigurable);
        registerSetting31.registerValue(1, android.R.bool.config_cecQuerySadDstEnabled_allowed, android.R.bool.config_cecQuerySadDstEnabled_default);
        registerSetting31.registerValue(0, android.R.bool.config_cecQuerySadDstDisabled_allowed, android.R.bool.config_cecQuerySadDstDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting32 = registerSetting("query_sad_wmapro", android.R.bool.config_cecQuerySadWmapro_userConfigurable);
        registerSetting32.registerValue(1, android.R.bool.config_cecQuerySadWmaproEnabled_allowed, android.R.bool.config_cecQuerySadWmaproEnabled_default);
        registerSetting32.registerValue(0, android.R.bool.config_cecQuerySadWmaproDisabled_allowed, android.R.bool.config_cecQuerySadWmaproDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting33 = registerSetting("query_sad_max", android.R.bool.config_cecQuerySadMax_userConfigurable);
        registerSetting33.registerValue(1, android.R.bool.config_cecQuerySadMaxEnabled_allowed, android.R.bool.config_cecQuerySadMaxEnabled_default);
        registerSetting33.registerValue(0, android.R.bool.config_cecQuerySadMaxDisabled_allowed, android.R.bool.config_cecQuerySadMaxDisabled_default);
        com.android.server.hdmi.HdmiCecConfig.Setting registerSetting34 = registerSetting("earc_enabled", android.R.bool.config_eap_sim_based_auth_supported);
        registerSetting34.registerValue(1, android.R.bool.config_earcFeatureDisabled_default, android.R.bool.config_earcFeatureEnabled_allowed);
        registerSetting34.registerValue(0, android.R.bool.config_earcEnabled_userConfigurable, android.R.bool.config_earcFeatureDisabled_allowed);
        verifySettings();
    }

    HdmiCecConfig(@android.annotation.NonNull android.content.Context context) {
        this(context, new com.android.server.hdmi.HdmiCecConfig.StorageAdapter(context));
    }

    private com.android.server.hdmi.HdmiCecConfig.Setting registerSetting(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = new com.android.server.hdmi.HdmiCecConfig.Setting(this.mContext, str, i);
        this.mSettings.put(str, setting);
        return setting;
    }

    private void verifySettings() {
        for (com.android.server.hdmi.HdmiCecConfig.Setting setting : this.mSettings.values()) {
            setting.getDefaultValue();
            getStorage(setting);
            getStorageKey(setting);
        }
    }

    @android.annotation.Nullable
    private com.android.server.hdmi.HdmiCecConfig.Setting getSetting(@android.annotation.NonNull java.lang.String str) {
        if (this.mSettings.containsKey(str)) {
            return this.mSettings.get(str);
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @com.android.server.hdmi.HdmiCecConfig.Storage
    private int getStorage(@android.annotation.NonNull com.android.server.hdmi.HdmiCecConfig.Setting setting) {
        char c;
        java.lang.String name = setting.getName();
        switch (name.hashCode()) {
            case -2072577869:
                if (name.equals("hdmi_cec_version")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1788790343:
                if (name.equals("system_audio_mode_muting")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1618836197:
                if (name.equals("set_menu_language")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1275604441:
                if (name.equals("rc_profile_source_handles_media_context_sensitive_menu")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -1253675651:
                if (name.equals("rc_profile_source_handles_top_menu")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -1188289112:
                if (name.equals("rc_profile_source_handles_root_menu")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1157203295:
                if (name.equals("query_sad_atrac")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -1154431553:
                if (name.equals("query_sad_dtshd")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -1146252564:
                if (name.equals("query_sad_mpeg1")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1146252563:
                if (name.equals("query_sad_mpeg2")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -1081575217:
                if (name.equals("earc_enabled")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -971363478:
                if (name.equals("query_sad_truehd")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -910325648:
                if (name.equals("rc_profile_source_handles_contents_menu")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -890678558:
                if (name.equals("query_sad_wmapro")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -412334364:
                if (name.equals("routing_control")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -314100402:
                if (name.equals("query_sad_lpcm")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -293445547:
                if (name.equals("rc_profile_source_handles_setup_menu")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -219770232:
                if (name.equals("power_state_change_on_active_source_lost")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -25374657:
                if (name.equals("power_control_mode")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 18371678:
                if (name.equals("soundbar_mode")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 73184058:
                if (name.equals("volume_control_enabled")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 261187356:
                if (name.equals("hdmi_cec_enabled")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 791759782:
                if (name.equals("rc_profile_tv")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 799280879:
                if (name.equals("query_sad_onebitaudio")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 1577324768:
                if (name.equals("query_sad_dd")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1629183631:
                if (name.equals("tv_wake_on_one_touch_play")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1652424675:
                if (name.equals("query_sad_aac")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1652427664:
                if (name.equals("query_sad_ddp")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 1652428133:
                if (name.equals("query_sad_dst")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 1652428163:
                if (name.equals("query_sad_dts")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 1652436228:
                if (name.equals("query_sad_max")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 1652436624:
                if (name.equals("query_sad_mp3")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 2055627683:
                if (name.equals("tv_send_standby_on_sleep")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 2118236132:
                if (name.equals("system_audio_control")) {
                    c = 7;
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
                return 2;
            case 1:
                return 2;
            case 2:
                return 2;
            case 3:
                return 2;
            case 4:
                return 2;
            case 5:
                return 2;
            case 6:
                return 2;
            case 7:
                return 2;
            case '\b':
                return 2;
            case '\t':
                return 2;
            case '\n':
                return 2;
            case 11:
                return 2;
            case '\f':
                return 2;
            case '\r':
                return 2;
            case 14:
                return 2;
            case 15:
                return 2;
            case 16:
                return 2;
            case 17:
                return 2;
            case 18:
                return 2;
            case 19:
                return 2;
            case 20:
                return 2;
            case 21:
                return 2;
            case 22:
                return 2;
            case 23:
                return 2;
            case 24:
                return 2;
            case 25:
                return 2;
            case 26:
                return 2;
            case 27:
                return 2;
            case 28:
                return 2;
            case 29:
                return 2;
            case 30:
                return 2;
            case 31:
                return 2;
            case ' ':
                return 2;
            case '!':
                return 2;
            default:
                throw new com.android.server.hdmi.HdmiCecConfig.VerificationException("Invalid CEC setting '" + setting.getName() + "' storage.");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private java.lang.String getStorageKey(@android.annotation.NonNull com.android.server.hdmi.HdmiCecConfig.Setting setting) {
        char c;
        java.lang.String name = setting.getName();
        switch (name.hashCode()) {
            case -2072577869:
                if (name.equals("hdmi_cec_version")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1788790343:
                if (name.equals("system_audio_mode_muting")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1618836197:
                if (name.equals("set_menu_language")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1275604441:
                if (name.equals("rc_profile_source_handles_media_context_sensitive_menu")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -1253675651:
                if (name.equals("rc_profile_source_handles_top_menu")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -1188289112:
                if (name.equals("rc_profile_source_handles_root_menu")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1157203295:
                if (name.equals("query_sad_atrac")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -1154431553:
                if (name.equals("query_sad_dtshd")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -1146252564:
                if (name.equals("query_sad_mpeg1")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1146252563:
                if (name.equals("query_sad_mpeg2")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -1081575217:
                if (name.equals("earc_enabled")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -971363478:
                if (name.equals("query_sad_truehd")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -910325648:
                if (name.equals("rc_profile_source_handles_contents_menu")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -890678558:
                if (name.equals("query_sad_wmapro")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -412334364:
                if (name.equals("routing_control")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -314100402:
                if (name.equals("query_sad_lpcm")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -293445547:
                if (name.equals("rc_profile_source_handles_setup_menu")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -219770232:
                if (name.equals("power_state_change_on_active_source_lost")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -25374657:
                if (name.equals("power_control_mode")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 18371678:
                if (name.equals("soundbar_mode")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 73184058:
                if (name.equals("volume_control_enabled")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 261187356:
                if (name.equals("hdmi_cec_enabled")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 791759782:
                if (name.equals("rc_profile_tv")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 799280879:
                if (name.equals("query_sad_onebitaudio")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 1577324768:
                if (name.equals("query_sad_dd")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1629183631:
                if (name.equals("tv_wake_on_one_touch_play")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1652424675:
                if (name.equals("query_sad_aac")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1652427664:
                if (name.equals("query_sad_ddp")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 1652428133:
                if (name.equals("query_sad_dst")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 1652428163:
                if (name.equals("query_sad_dts")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 1652436228:
                if (name.equals("query_sad_max")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 1652436624:
                if (name.equals("query_sad_mp3")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 2055627683:
                if (name.equals("tv_send_standby_on_sleep")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 2118236132:
                if (name.equals("system_audio_control")) {
                    c = 7;
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
                return setting.getName();
            case 1:
                return setting.getName();
            case 2:
                return setting.getName();
            case 3:
                return setting.getName();
            case 4:
                return setting.getName();
            case 5:
                return setting.getName();
            case 6:
                return setting.getName();
            case 7:
                return setting.getName();
            case '\b':
                return setting.getName();
            case '\t':
                return setting.getName();
            case '\n':
                return setting.getName();
            case 11:
                return setting.getName();
            case '\f':
                return setting.getName();
            case '\r':
                return setting.getName();
            case 14:
                return setting.getName();
            case 15:
                return setting.getName();
            case 16:
                return setting.getName();
            case 17:
                return setting.getName();
            case 18:
                return setting.getName();
            case 19:
                return setting.getName();
            case 20:
                return setting.getName();
            case 21:
                return setting.getName();
            case 22:
                return setting.getName();
            case 23:
                return setting.getName();
            case 24:
                return setting.getName();
            case 25:
                return setting.getName();
            case 26:
                return setting.getName();
            case 27:
                return setting.getName();
            case 28:
                return setting.getName();
            case 29:
                return setting.getName();
            case 30:
                return setting.getName();
            case 31:
                return setting.getName();
            case ' ':
                return setting.getName();
            case '!':
                return setting.getName();
            default:
                throw new com.android.server.hdmi.HdmiCecConfig.VerificationException("Invalid CEC setting '" + setting.getName() + "' storage key.");
        }
    }

    protected java.lang.String retrieveValue(@android.annotation.NonNull com.android.server.hdmi.HdmiCecConfig.Setting setting, @android.annotation.NonNull java.lang.String str) {
        int storage = getStorage(setting);
        java.lang.String storageKey = getStorageKey(setting);
        if (storage == 0) {
            com.android.server.hdmi.HdmiLogger.debug("Reading '" + storageKey + "' sysprop.", new java.lang.Object[0]);
            return this.mStorageAdapter.retrieveSystemProperty(storageKey, str);
        }
        if (storage == 1) {
            com.android.server.hdmi.HdmiLogger.debug("Reading '" + storageKey + "' global setting.", new java.lang.Object[0]);
            return this.mStorageAdapter.retrieveGlobalSetting(storageKey, str);
        }
        if (storage == 2) {
            com.android.server.hdmi.HdmiLogger.debug("Reading '" + storageKey + "' shared preference.", new java.lang.Object[0]);
            return this.mStorageAdapter.retrieveSharedPref(storageKey, str);
        }
        return null;
    }

    protected void storeValue(@android.annotation.NonNull com.android.server.hdmi.HdmiCecConfig.Setting setting, @android.annotation.NonNull java.lang.String str) {
        int storage = getStorage(setting);
        java.lang.String storageKey = getStorageKey(setting);
        if (storage == 0) {
            com.android.server.hdmi.HdmiLogger.debug("Setting '" + storageKey + "' sysprop.", new java.lang.Object[0]);
            this.mStorageAdapter.storeSystemProperty(storageKey, str);
            return;
        }
        if (storage == 1) {
            com.android.server.hdmi.HdmiLogger.debug("Setting '" + storageKey + "' global setting.", new java.lang.Object[0]);
            this.mStorageAdapter.storeGlobalSetting(storageKey, str);
            return;
        }
        if (storage == 2) {
            com.android.server.hdmi.HdmiLogger.debug("Setting '" + storageKey + "' shared pref.", new java.lang.Object[0]);
            this.mStorageAdapter.storeSharedPref(storageKey, str);
            notifySettingChanged(setting);
        }
    }

    protected void notifySettingChanged(@android.annotation.NonNull final com.android.server.hdmi.HdmiCecConfig.Setting setting) {
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<com.android.server.hdmi.HdmiCecConfig.SettingChangeListener, java.util.concurrent.Executor> arrayMap = this.mSettingChangeListeners.get(setting);
                if (arrayMap == null) {
                    return;
                }
                for (java.util.Map.Entry<com.android.server.hdmi.HdmiCecConfig.SettingChangeListener, java.util.concurrent.Executor> entry : arrayMap.entrySet()) {
                    final com.android.server.hdmi.HdmiCecConfig.SettingChangeListener key = entry.getKey();
                    entry.getValue().execute(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecConfig.1
                        @Override // java.lang.Runnable
                        public void run() {
                            key.onChange(setting.getName());
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void registerChangeListener(@android.annotation.NonNull java.lang.String str, com.android.server.hdmi.HdmiCecConfig.SettingChangeListener settingChangeListener) {
        registerChangeListener(str, settingChangeListener, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR);
    }

    public void registerChangeListener(@android.annotation.NonNull java.lang.String str, com.android.server.hdmi.HdmiCecConfig.SettingChangeListener settingChangeListener, java.util.concurrent.Executor executor) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        int storage = getStorage(setting);
        if (storage != 1 && storage != 2) {
            throw new java.lang.IllegalArgumentException("Change listeners for setting '" + str + "' not supported.");
        }
        synchronized (this.mLock) {
            try {
                if (!this.mSettingChangeListeners.containsKey(setting)) {
                    this.mSettingChangeListeners.put(setting, new android.util.ArrayMap<>());
                }
                this.mSettingChangeListeners.get(setting).put(settingChangeListener, executor);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeChangeListener(@android.annotation.NonNull java.lang.String str, com.android.server.hdmi.HdmiCecConfig.SettingChangeListener settingChangeListener) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        synchronized (this.mLock) {
            try {
                if (this.mSettingChangeListeners.containsKey(setting)) {
                    android.util.ArrayMap<com.android.server.hdmi.HdmiCecConfig.SettingChangeListener, java.util.concurrent.Executor> arrayMap = this.mSettingChangeListeners.get(setting);
                    arrayMap.remove(settingChangeListener);
                    if (arrayMap.isEmpty()) {
                        this.mSettingChangeListeners.remove(setting);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<java.lang.String> getAllSettings() {
        return new java.util.ArrayList(this.mSettings.keySet());
    }

    public java.util.List<java.lang.String> getUserSettings() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.android.server.hdmi.HdmiCecConfig.Setting setting : this.mSettings.values()) {
            if (setting.getUserConfigurable()) {
                arrayList.add(setting.getName());
            }
        }
        return arrayList;
    }

    public boolean isStringValueType(@android.annotation.NonNull java.lang.String str) {
        if (getSetting(str) == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        return getSetting(str).getValueType().equals(VALUE_TYPE_STRING);
    }

    public boolean isIntValueType(@android.annotation.NonNull java.lang.String str) {
        if (getSetting(str) == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        return getSetting(str).getValueType().equals(VALUE_TYPE_INT);
    }

    public java.util.List<java.lang.String> getAllowedStringValues(@android.annotation.NonNull java.lang.String str) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        if (!setting.getValueType().equals(VALUE_TYPE_STRING)) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.server.hdmi.HdmiCecConfig.Value> it = setting.getAllowedValues().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getStringValue());
        }
        return arrayList;
    }

    public java.util.List<java.lang.Integer> getAllowedIntValues(@android.annotation.NonNull java.lang.String str) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        if (!setting.getValueType().equals(VALUE_TYPE_INT)) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.server.hdmi.HdmiCecConfig.Value> it = setting.getAllowedValues().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getIntValue());
        }
        return arrayList;
    }

    public java.lang.String getDefaultStringValue(@android.annotation.NonNull java.lang.String str) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        if (!setting.getValueType().equals(VALUE_TYPE_STRING)) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
        }
        return getSetting(str).getDefaultValue().getStringValue();
    }

    public int getDefaultIntValue(@android.annotation.NonNull java.lang.String str) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        if (!setting.getValueType().equals(VALUE_TYPE_INT)) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
        }
        return getSetting(str).getDefaultValue().getIntValue().intValue();
    }

    public java.lang.String getStringValue(@android.annotation.NonNull java.lang.String str) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        if (!setting.getValueType().equals(VALUE_TYPE_STRING)) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
        }
        com.android.server.hdmi.HdmiLogger.debug("Getting CEC setting value '" + str + "'.", new java.lang.Object[0]);
        return retrieveValue(setting, setting.getDefaultValue().getStringValue());
    }

    public int getIntValue(@android.annotation.NonNull java.lang.String str) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        if (!setting.getValueType().equals(VALUE_TYPE_INT)) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' is not a int-type setting.");
        }
        com.android.server.hdmi.HdmiLogger.debug("Getting CEC setting value '" + str + "'.", new java.lang.Object[0]);
        return java.lang.Integer.parseInt(retrieveValue(setting, java.lang.Integer.toString(setting.getDefaultValue().getIntValue().intValue())));
    }

    public void setStringValue(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        if (!setting.getUserConfigurable()) {
            throw new java.lang.IllegalArgumentException("Updating CEC setting '" + str + "' prohibited.");
        }
        if (!setting.getValueType().equals(VALUE_TYPE_STRING)) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
        }
        if (!getAllowedStringValues(str).contains(str2)) {
            throw new java.lang.IllegalArgumentException("Invalid CEC setting '" + str + "' value: '" + str2 + "'.");
        }
        com.android.server.hdmi.HdmiLogger.debug("Updating CEC setting '" + str + "' to '" + str2 + "'.", new java.lang.Object[0]);
        storeValue(setting, str2);
    }

    public void setIntValue(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.hdmi.HdmiCecConfig.Setting setting = getSetting(str);
        if (setting == null) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' does not exist.");
        }
        if (!setting.getUserConfigurable()) {
            throw new java.lang.IllegalArgumentException("Updating CEC setting '" + str + "' prohibited.");
        }
        if (!setting.getValueType().equals(VALUE_TYPE_INT)) {
            throw new java.lang.IllegalArgumentException("Setting '" + str + "' is not a int-type setting.");
        }
        if (!getAllowedIntValues(str).contains(java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException("Invalid CEC setting '" + str + "' value: '" + i + "'.");
        }
        com.android.server.hdmi.HdmiLogger.debug("Updating CEC setting '" + str + "' to '" + i + "'.", new java.lang.Object[0]);
        storeValue(setting, java.lang.Integer.toString(i));
    }
}
