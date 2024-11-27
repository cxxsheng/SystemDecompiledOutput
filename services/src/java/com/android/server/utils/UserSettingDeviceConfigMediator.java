package com.android.server.utils;

/* loaded from: classes2.dex */
public abstract class UserSettingDeviceConfigMediator {
    private static final java.lang.String TAG = com.android.server.utils.UserSettingDeviceConfigMediator.class.getSimpleName();

    @android.annotation.Nullable
    protected android.provider.DeviceConfig.Properties mProperties;

    @android.annotation.NonNull
    protected final android.util.KeyValueListParser mSettingsParser;

    public abstract boolean getBoolean(@android.annotation.NonNull java.lang.String str, boolean z);

    public abstract float getFloat(@android.annotation.NonNull java.lang.String str, float f);

    public abstract int getInt(@android.annotation.NonNull java.lang.String str, int i);

    public abstract long getLong(@android.annotation.NonNull java.lang.String str, long j);

    public abstract java.lang.String getString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2);

    protected UserSettingDeviceConfigMediator(char c) {
        this.mSettingsParser = new android.util.KeyValueListParser(c);
    }

    public void setSettingsString(@android.annotation.Nullable java.lang.String str) {
        this.mSettingsParser.setString(str);
    }

    public void setDeviceConfigProperties(@android.annotation.Nullable android.provider.DeviceConfig.Properties properties) {
        this.mProperties = properties;
    }

    public static class SettingsOverridesAllMediator extends com.android.server.utils.UserSettingDeviceConfigMediator {
        public SettingsOverridesAllMediator(char c) {
            super(c);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public boolean getBoolean(@android.annotation.NonNull java.lang.String str, boolean z) {
            if (this.mSettingsParser.size() == 0) {
                return this.mProperties == null ? z : this.mProperties.getBoolean(str, z);
            }
            return this.mSettingsParser.getBoolean(str, z);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public float getFloat(@android.annotation.NonNull java.lang.String str, float f) {
            if (this.mSettingsParser.size() == 0) {
                return this.mProperties == null ? f : this.mProperties.getFloat(str, f);
            }
            return this.mSettingsParser.getFloat(str, f);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public int getInt(@android.annotation.NonNull java.lang.String str, int i) {
            if (this.mSettingsParser.size() == 0) {
                return this.mProperties == null ? i : this.mProperties.getInt(str, i);
            }
            return this.mSettingsParser.getInt(str, i);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public long getLong(@android.annotation.NonNull java.lang.String str, long j) {
            if (this.mSettingsParser.size() == 0) {
                return this.mProperties == null ? j : this.mProperties.getLong(str, j);
            }
            return this.mSettingsParser.getLong(str, j);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public java.lang.String getString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
            if (this.mSettingsParser.size() == 0) {
                return this.mProperties == null ? str2 : this.mProperties.getString(str, str2);
            }
            return this.mSettingsParser.getString(str, str2);
        }
    }

    public static class SettingsOverridesIndividualMediator extends com.android.server.utils.UserSettingDeviceConfigMediator {
        public SettingsOverridesIndividualMediator(char c) {
            super(c);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public boolean getBoolean(@android.annotation.NonNull java.lang.String str, boolean z) {
            android.util.KeyValueListParser keyValueListParser = this.mSettingsParser;
            if (this.mProperties != null) {
                z = this.mProperties.getBoolean(str, z);
            }
            return keyValueListParser.getBoolean(str, z);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public float getFloat(@android.annotation.NonNull java.lang.String str, float f) {
            android.util.KeyValueListParser keyValueListParser = this.mSettingsParser;
            if (this.mProperties != null) {
                f = this.mProperties.getFloat(str, f);
            }
            return keyValueListParser.getFloat(str, f);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public int getInt(@android.annotation.NonNull java.lang.String str, int i) {
            android.util.KeyValueListParser keyValueListParser = this.mSettingsParser;
            if (this.mProperties != null) {
                i = this.mProperties.getInt(str, i);
            }
            return keyValueListParser.getInt(str, i);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public long getLong(@android.annotation.NonNull java.lang.String str, long j) {
            android.util.KeyValueListParser keyValueListParser = this.mSettingsParser;
            if (this.mProperties != null) {
                j = this.mProperties.getLong(str, j);
            }
            return keyValueListParser.getLong(str, j);
        }

        @Override // com.android.server.utils.UserSettingDeviceConfigMediator
        public java.lang.String getString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
            android.util.KeyValueListParser keyValueListParser = this.mSettingsParser;
            if (this.mProperties != null) {
                str2 = this.mProperties.getString(str, str2);
            }
            return keyValueListParser.getString(str, str2);
        }
    }
}
