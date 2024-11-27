package com.android.server.signedconfig;

/* loaded from: classes2.dex */
public class SignedConfig {
    private static final java.lang.String CONFIG_KEY_MAX_SDK = "max_sdk";
    private static final java.lang.String CONFIG_KEY_MIN_SDK = "min_sdk";
    private static final java.lang.String CONFIG_KEY_VALUES = "values";
    private static final java.lang.String KEY_CONFIG = "config";
    private static final java.lang.String KEY_VERSION = "version";
    public final java.util.List<com.android.server.signedconfig.SignedConfig.PerSdkConfig> perSdkConfig;
    public final int version;

    public static class PerSdkConfig {
        public final int maxSdk;
        public final int minSdk;
        public final java.util.Map<java.lang.String, java.lang.String> values;

        public PerSdkConfig(int i, int i2, java.util.Map<java.lang.String, java.lang.String> map) {
            this.minSdk = i;
            this.maxSdk = i2;
            this.values = java.util.Collections.unmodifiableMap(map);
        }
    }

    public SignedConfig(int i, java.util.List<com.android.server.signedconfig.SignedConfig.PerSdkConfig> list) {
        this.version = i;
        this.perSdkConfig = java.util.Collections.unmodifiableList(list);
    }

    public com.android.server.signedconfig.SignedConfig.PerSdkConfig getMatchingConfig(int i) {
        for (com.android.server.signedconfig.SignedConfig.PerSdkConfig perSdkConfig : this.perSdkConfig) {
            if (perSdkConfig.minSdk <= i && i <= perSdkConfig.maxSdk) {
                return perSdkConfig;
            }
        }
        return null;
    }

    public static com.android.server.signedconfig.SignedConfig parse(java.lang.String str, java.util.Set<java.lang.String> set, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> map) throws com.android.server.signedconfig.InvalidConfigException {
        try {
            org.json.JSONObject jSONObject = new org.json.JSONObject(str);
            int i = jSONObject.getInt(KEY_VERSION);
            org.json.JSONArray jSONArray = jSONObject.getJSONArray(KEY_CONFIG);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                arrayList.add(parsePerSdkConfig(jSONArray.getJSONObject(i2), set, map));
            }
            return new com.android.server.signedconfig.SignedConfig(i, arrayList);
        } catch (org.json.JSONException e) {
            throw new com.android.server.signedconfig.InvalidConfigException("Could not parse JSON", e);
        }
    }

    private static java.lang.CharSequence quoted(java.lang.Object obj) {
        if (obj == null) {
            return "null";
        }
        return "\"" + obj + "\"";
    }

    @com.android.internal.annotations.VisibleForTesting
    static com.android.server.signedconfig.SignedConfig.PerSdkConfig parsePerSdkConfig(org.json.JSONObject jSONObject, java.util.Set<java.lang.String> set, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> map) throws org.json.JSONException, com.android.server.signedconfig.InvalidConfigException {
        java.lang.String str;
        int i = jSONObject.getInt(CONFIG_KEY_MIN_SDK);
        int i2 = jSONObject.getInt(CONFIG_KEY_MAX_SDK);
        org.json.JSONObject jSONObject2 = jSONObject.getJSONObject(CONFIG_KEY_VALUES);
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.lang.String str2 : jSONObject2.keySet()) {
            java.lang.Object obj = jSONObject2.get(str2);
            if (obj == org.json.JSONObject.NULL || obj == null) {
                str = null;
            } else {
                str = obj.toString();
            }
            if (!set.contains(str2)) {
                throw new com.android.server.signedconfig.InvalidConfigException("Config key " + str2 + " is not allowed");
            }
            if (map.containsKey(str2)) {
                java.util.Map<java.lang.String, java.lang.String> map2 = map.get(str2);
                if (!map2.containsKey(str)) {
                    throw new com.android.server.signedconfig.InvalidConfigException("Config key " + str2 + " contains unsupported value " + ((java.lang.Object) quoted(str)));
                }
                str = map2.get(str);
            }
            hashMap.put(str2, str);
        }
        return new com.android.server.signedconfig.SignedConfig.PerSdkConfig(i, i2, hashMap);
    }
}
