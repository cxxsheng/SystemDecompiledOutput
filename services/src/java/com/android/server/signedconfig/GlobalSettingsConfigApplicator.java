package com.android.server.signedconfig;

/* loaded from: classes2.dex */
class GlobalSettingsConfigApplicator {
    private static final java.util.Set<java.lang.String> ALLOWED_KEYS = java.util.Collections.unmodifiableSet(new android.util.ArraySet(java.util.Arrays.asList("hidden_api_policy", "hidden_api_blacklist_exemptions")));
    private static final java.util.Map<java.lang.String, java.lang.String> HIDDEN_API_POLICY_KEY_MAP = makeMap("DEFAULT", java.lang.String.valueOf(-1), "DISABLED", java.lang.String.valueOf(0), "JUST_WARN", java.lang.String.valueOf(1), "ENABLED", java.lang.String.valueOf(2));
    private static final java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> KEY_VALUE_MAPPERS = makeMap("hidden_api_policy", HIDDEN_API_POLICY_KEY_MAP);
    private static final java.lang.String TAG = "SignedConfig";
    private final android.content.Context mContext;
    private final com.android.server.signedconfig.SignedConfigEvent mEvent;
    private final java.lang.String mSourcePackage;
    private final com.android.server.signedconfig.SignatureVerifier mVerifier;

    /* JADX WARN: Multi-variable type inference failed */
    private static <K, V> java.util.Map<K, V> makeMap(java.lang.Object... objArr) {
        if (objArr.length % 2 != 0) {
            throw new java.lang.IllegalArgumentException();
        }
        int length = objArr.length / 2;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(length);
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            arrayMap.put(objArr[i2], objArr[i2 + 1]);
        }
        return java.util.Collections.unmodifiableMap(arrayMap);
    }

    GlobalSettingsConfigApplicator(android.content.Context context, java.lang.String str, com.android.server.signedconfig.SignedConfigEvent signedConfigEvent) {
        this.mContext = context;
        this.mSourcePackage = str;
        this.mEvent = signedConfigEvent;
        this.mVerifier = new com.android.server.signedconfig.SignatureVerifier(this.mEvent);
    }

    private boolean checkSignature(java.lang.String str, java.lang.String str2) {
        try {
            return this.mVerifier.verifySignature(str, str2);
        } catch (java.security.GeneralSecurityException e) {
            android.util.Slog.e(TAG, "Failed to verify signature", e);
            this.mEvent.status = 4;
            return false;
        }
    }

    private int getCurrentConfigVersion() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "signed_config_version", 0);
    }

    private void updateCurrentConfig(int i, java.util.Map<java.lang.String, java.lang.String> map) {
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
            android.provider.Settings.Global.putString(this.mContext.getContentResolver(), entry.getKey(), entry.getValue());
        }
        android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "signed_config_version", i);
    }

    void applyConfig(java.lang.String str, java.lang.String str2) {
        if (!checkSignature(str, str2)) {
            android.util.Slog.e(TAG, "Signature check on global settings in package " + this.mSourcePackage + " failed; ignoring");
            return;
        }
        try {
            com.android.server.signedconfig.SignedConfig parse = com.android.server.signedconfig.SignedConfig.parse(str, ALLOWED_KEYS, KEY_VALUE_MAPPERS);
            this.mEvent.version = parse.version;
            int currentConfigVersion = getCurrentConfigVersion();
            if (currentConfigVersion >= parse.version) {
                android.util.Slog.i(TAG, "Global settings from package " + this.mSourcePackage + " is older than existing: " + parse.version + "<=" + currentConfigVersion);
                this.mEvent.status = 6;
                return;
            }
            android.util.Slog.i(TAG, "Got new global settings from package " + this.mSourcePackage + ": version " + parse.version + " replacing existing version " + currentConfigVersion);
            com.android.server.signedconfig.SignedConfig.PerSdkConfig matchingConfig = parse.getMatchingConfig(android.os.Build.VERSION.SDK_INT);
            if (matchingConfig == null) {
                android.util.Slog.i(TAG, "Settings is not applicable to current SDK version; ignoring");
                this.mEvent.status = 8;
                return;
            }
            android.util.Slog.i(TAG, "Updating global settings to version " + parse.version);
            updateCurrentConfig(parse.version, matchingConfig.values);
            this.mEvent.status = 1;
        } catch (com.android.server.signedconfig.InvalidConfigException e) {
            android.util.Slog.e(TAG, "Failed to parse global settings from package " + this.mSourcePackage, e);
            this.mEvent.status = 5;
        }
    }
}
