package com.android.server.stats.pull;

/* loaded from: classes2.dex */
final class SettingsStatsUtil {
    private static final com.android.server.stats.pull.SettingsStatsUtil.FlagsData[] GLOBAL_SETTINGS = {new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("GlobalFeature__boolean_whitelist", 1), new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("GlobalFeature__integer_whitelist", 2), new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("GlobalFeature__float_whitelist", 3), new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("GlobalFeature__string_whitelist", 4)};
    private static final com.android.server.stats.pull.SettingsStatsUtil.FlagsData[] SECURE_SETTINGS = {new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("SecureFeature__boolean_whitelist", 1), new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("SecureFeature__integer_whitelist", 2), new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("SecureFeature__float_whitelist", 3), new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("SecureFeature__string_whitelist", 4)};
    private static final com.android.server.stats.pull.SettingsStatsUtil.FlagsData[] SYSTEM_SETTINGS = {new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("SystemFeature__boolean_whitelist", 1), new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("SystemFeature__integer_whitelist", 2), new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("SystemFeature__float_whitelist", 3), new com.android.server.stats.pull.SettingsStatsUtil.FlagsData("SystemFeature__string_whitelist", 4)};
    private static final java.lang.String TAG = "SettingsStatsUtil";

    SettingsStatsUtil() {
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    static java.util.List<android.util.StatsEvent> logGlobalSettings(android.content.Context context, int i, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.ContentResolver contentResolver = context.getContentResolver();
        for (com.android.server.stats.pull.SettingsStatsUtil.FlagsData flagsData : GLOBAL_SETTINGS) {
            com.android.service.nano.StringListParamProto list = getList(flagsData.mFlagName);
            if (list != null) {
                for (java.lang.String str : list.element) {
                    arrayList.add(createStatsEvent(i, str, android.provider.Settings.Global.getStringForUser(contentResolver, str, i2), i2, flagsData.mDataType));
                }
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    static java.util.List<android.util.StatsEvent> logSystemSettings(android.content.Context context, int i, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.ContentResolver contentResolver = context.getContentResolver();
        for (com.android.server.stats.pull.SettingsStatsUtil.FlagsData flagsData : SYSTEM_SETTINGS) {
            com.android.service.nano.StringListParamProto list = getList(flagsData.mFlagName);
            if (list != null) {
                for (java.lang.String str : list.element) {
                    arrayList.add(createStatsEvent(i, str, android.provider.Settings.System.getStringForUser(contentResolver, str, i2), i2, flagsData.mDataType));
                }
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    static java.util.List<android.util.StatsEvent> logSecureSettings(android.content.Context context, int i, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.ContentResolver contentResolver = context.getContentResolver();
        for (com.android.server.stats.pull.SettingsStatsUtil.FlagsData flagsData : SECURE_SETTINGS) {
            com.android.service.nano.StringListParamProto list = getList(flagsData.mFlagName);
            if (list != null) {
                for (java.lang.String str : list.element) {
                    arrayList.add(createStatsEvent(i, str, android.provider.Settings.Secure.getStringForUser(contentResolver, str, i2), i2, flagsData.mDataType));
                }
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static com.android.service.nano.StringListParamProto getList(java.lang.String str) {
        java.lang.String property = android.provider.DeviceConfig.getProperty("settings_stats", str);
        if (android.text.TextUtils.isEmpty(property)) {
            return null;
        }
        try {
            return com.android.service.nano.StringListParamProto.parseFrom(android.util.Base64.decode(property, 3));
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error parsing string list proto", e);
            return null;
        }
    }

    @android.annotation.NonNull
    private static android.util.StatsEvent createStatsEvent(int i, java.lang.String str, java.lang.String str2, int i2, int i3) {
        int i4;
        android.util.StatsEvent.Builder writeString = android.util.StatsEvent.newBuilder().setAtomId(i).writeString(str);
        boolean isEmpty = android.text.TextUtils.isEmpty(str2);
        boolean z = false;
        float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        if (isEmpty) {
            writeString.writeInt(0).writeBoolean(false).writeInt(0).writeFloat(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE).writeString("").writeInt(i2);
        } else {
            switch (i3) {
                case 1:
                    boolean equals = "1".equals(str2);
                    str2 = "";
                    z = equals;
                    i4 = 0;
                    break;
                case 2:
                    try {
                        i4 = java.lang.Integer.parseInt(str2);
                        str2 = "";
                        break;
                    } catch (java.lang.NumberFormatException e) {
                        android.util.Slog.w(TAG, "Can not parse value to float: " + str2);
                        break;
                    }
                case 3:
                    try {
                        f = java.lang.Float.parseFloat(str2);
                        i4 = 0;
                        str2 = "";
                        break;
                    } catch (java.lang.NumberFormatException e2) {
                        android.util.Slog.w(TAG, "Can not parse value to float: " + str2);
                        break;
                    }
                case 4:
                    i4 = 0;
                    break;
                default:
                    android.util.Slog.w(TAG, "Unexpected value type " + i3);
                    i4 = 0;
                    str2 = "";
                    break;
            }
            writeString.writeInt(i3).writeBoolean(z).writeInt(i4).writeFloat(f).writeString(str2).writeInt(i2);
        }
        return writeString.build();
    }

    static final class FlagsData {
        int mDataType;
        java.lang.String mFlagName;

        FlagsData(java.lang.String str, int i) {
            this.mFlagName = str;
            this.mDataType = i;
        }
    }
}
