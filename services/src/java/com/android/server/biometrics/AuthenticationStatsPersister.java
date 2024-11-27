package com.android.server.biometrics;

/* loaded from: classes.dex */
public class AuthenticationStatsPersister {
    private static final java.lang.String ENROLLMENT_NOTIFICATIONS = "enrollment_notifications";
    private static final java.lang.String FACE_ATTEMPTS = "face_attempts";
    private static final java.lang.String FACE_REJECTIONS = "face_rejections";
    private static final java.lang.String FILE_NAME = "authentication_stats";
    private static final java.lang.String FINGERPRINT_ATTEMPTS = "fingerprint_attempts";
    private static final java.lang.String FINGERPRINT_REJECTIONS = "fingerprint_rejections";
    private static final java.lang.String KEY = "frr_stats";
    private static final java.lang.String TAG = "AuthenticationStatsPersister";
    private static final java.lang.String THRESHOLD_KEY = "frr_threshold";
    private static final java.lang.String USER_ID = "user_id";

    @android.annotation.NonNull
    private final android.content.SharedPreferences mSharedPreferences;

    AuthenticationStatsPersister(@android.annotation.NonNull android.content.Context context) {
        this.mSharedPreferences = context.getSharedPreferences(new java.io.File(android.os.Environment.getDataSystemDirectory(), FILE_NAME), 0);
    }

    public java.util.List<com.android.server.biometrics.AuthenticationStats> getAllFrrStats(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : readFrrStats()) {
            try {
                org.json.JSONObject jSONObject = new org.json.JSONObject(str);
                if (i == 4) {
                    arrayList.add(new com.android.server.biometrics.AuthenticationStats(getIntValue(jSONObject, USER_ID, com.android.server.am.ProcessList.INVALID_ADJ), getIntValue(jSONObject, FACE_ATTEMPTS), getIntValue(jSONObject, FACE_REJECTIONS), getIntValue(jSONObject, ENROLLMENT_NOTIFICATIONS), i));
                } else if (i == 1) {
                    arrayList.add(new com.android.server.biometrics.AuthenticationStats(getIntValue(jSONObject, USER_ID, com.android.server.am.ProcessList.INVALID_ADJ), getIntValue(jSONObject, FINGERPRINT_ATTEMPTS), getIntValue(jSONObject, FINGERPRINT_REJECTIONS), getIntValue(jSONObject, ENROLLMENT_NOTIFICATIONS), i));
                }
            } catch (org.json.JSONException e) {
                android.util.Slog.w(TAG, java.lang.String.format("Unable to resolve authentication stats JSON: %s", str));
            }
        }
        return arrayList;
    }

    public void removeFrrStats(int i) {
        try {
            java.util.HashSet hashSet = new java.util.HashSet(readFrrStats());
            java.util.Iterator it = hashSet.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (getValue(new org.json.JSONObject((java.lang.String) it.next()), USER_ID).equals(java.lang.String.valueOf(i))) {
                    it.remove();
                    break;
                }
            }
            this.mSharedPreferences.edit().putStringSet(KEY, hashSet).apply();
        } catch (org.json.JSONException e) {
        }
    }

    public void persistFrrStats(int i, int i2, int i3, int i4, int i5) {
        org.json.JSONObject jSONObject;
        org.json.JSONObject jSONObject2;
        try {
            java.util.HashSet hashSet = new java.util.HashSet(readFrrStats());
            java.util.Iterator it = hashSet.iterator();
            while (true) {
                if (!it.hasNext()) {
                    jSONObject = null;
                    break;
                }
                jSONObject = new org.json.JSONObject((java.lang.String) it.next());
                if (getValue(jSONObject, USER_ID).equals(java.lang.String.valueOf(i))) {
                    it.remove();
                    break;
                }
            }
            if (jSONObject != null) {
                jSONObject2 = jSONObject;
            } else {
                jSONObject2 = new org.json.JSONObject().put(USER_ID, i);
            }
            hashSet.add(buildFrrStats(jSONObject2, i2, i3, i4, i5));
            android.util.Slog.d(TAG, "frrStatsSet to persist: " + hashSet);
            this.mSharedPreferences.edit().putStringSet(KEY, hashSet).apply();
        } catch (org.json.JSONException e) {
            android.util.Slog.e(TAG, "Unable to persist authentication stats");
        }
    }

    public void persistFrrThreshold(float f) {
        this.mSharedPreferences.edit().putFloat(THRESHOLD_KEY, f).apply();
    }

    private java.util.Set<java.lang.String> readFrrStats() {
        return this.mSharedPreferences.getStringSet(KEY, java.util.Set.of());
    }

    private java.lang.String buildFrrStats(org.json.JSONObject jSONObject, int i, int i2, int i3, int i4) throws org.json.JSONException {
        if (i4 == 4) {
            return jSONObject.put(FACE_ATTEMPTS, i).put(FACE_REJECTIONS, i2).put(ENROLLMENT_NOTIFICATIONS, i3).toString();
        }
        if (i4 == 1) {
            return jSONObject.put(FINGERPRINT_ATTEMPTS, i).put(FINGERPRINT_REJECTIONS, i2).put(ENROLLMENT_NOTIFICATIONS, i3).toString();
        }
        return jSONObject.toString();
    }

    private java.lang.String getValue(org.json.JSONObject jSONObject, java.lang.String str) throws org.json.JSONException {
        return jSONObject.has(str) ? jSONObject.getString(str) : "";
    }

    private int getIntValue(org.json.JSONObject jSONObject, java.lang.String str) throws org.json.JSONException {
        return getIntValue(jSONObject, str, 0);
    }

    private int getIntValue(org.json.JSONObject jSONObject, java.lang.String str, int i) throws org.json.JSONException {
        return jSONObject.has(str) ? jSONObject.getInt(str) : i;
    }
}
