package com.android.server.credentials.metrics;

/* loaded from: classes.dex */
public enum EntryEnum {
    UNKNOWN(0),
    ACTION_ENTRY(1),
    CREDENTIAL_ENTRY(2),
    REMOTE_ENTRY(3),
    AUTHENTICATION_ENTRY(4);

    private static final java.lang.String TAG = "EntryEnum";
    private final int mInnerMetricCode;
    private static final java.util.Map<java.lang.String, java.lang.Integer> sKeyToEntryCode = java.util.Map.ofEntries(new java.util.AbstractMap.SimpleEntry(com.android.server.credentials.ProviderGetSession.ACTION_ENTRY_KEY, java.lang.Integer.valueOf(ACTION_ENTRY.mInnerMetricCode)), new java.util.AbstractMap.SimpleEntry(com.android.server.credentials.ProviderGetSession.AUTHENTICATION_ACTION_ENTRY_KEY, java.lang.Integer.valueOf(AUTHENTICATION_ENTRY.mInnerMetricCode)), new java.util.AbstractMap.SimpleEntry(com.android.server.credentials.ProviderGetSession.REMOTE_ENTRY_KEY, java.lang.Integer.valueOf(REMOTE_ENTRY.mInnerMetricCode)), new java.util.AbstractMap.SimpleEntry(com.android.server.credentials.ProviderGetSession.CREDENTIAL_ENTRY_KEY, java.lang.Integer.valueOf(CREDENTIAL_ENTRY.mInnerMetricCode)), new java.util.AbstractMap.SimpleEntry(com.android.server.credentials.ProviderCreateSession.SAVE_ENTRY_KEY, java.lang.Integer.valueOf(CREDENTIAL_ENTRY.mInnerMetricCode)));

    EntryEnum(int i) {
        this.mInnerMetricCode = i;
    }

    public int getMetricCode() {
        return this.mInnerMetricCode;
    }

    public static int getMetricCodeFromString(java.lang.String str) {
        if (!sKeyToEntryCode.containsKey(str)) {
            android.util.Slog.i(TAG, "Attempted to use an unsupported string key entry type");
            return UNKNOWN.mInnerMetricCode;
        }
        return sKeyToEntryCode.get(str).intValue();
    }
}
