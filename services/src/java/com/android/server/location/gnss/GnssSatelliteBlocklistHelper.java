package com.android.server.location.gnss;

/* loaded from: classes2.dex */
class GnssSatelliteBlocklistHelper {
    private static final java.lang.String BLOCKLIST_DELIMITER = ",";
    private static final java.lang.String TAG = "GnssBlocklistHelper";
    private final com.android.server.location.gnss.GnssSatelliteBlocklistHelper.GnssSatelliteBlocklistCallback mCallback;
    private final android.content.Context mContext;

    interface GnssSatelliteBlocklistCallback {
        void onUpdateSatelliteBlocklist(int[] iArr, int[] iArr2);
    }

    GnssSatelliteBlocklistHelper(android.content.Context context, android.os.Looper looper, com.android.server.location.gnss.GnssSatelliteBlocklistHelper.GnssSatelliteBlocklistCallback gnssSatelliteBlocklistCallback) {
        this.mContext = context;
        this.mCallback = gnssSatelliteBlocklistCallback;
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("gnss_satellite_blocklist"), true, new android.database.ContentObserver(new android.os.Handler(looper)) { // from class: com.android.server.location.gnss.GnssSatelliteBlocklistHelper.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.location.gnss.GnssSatelliteBlocklistHelper.this.updateSatelliteBlocklist();
            }
        }, -1);
    }

    void updateSatelliteBlocklist() {
        java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), "gnss_satellite_blocklist");
        if (string == null) {
            string = "";
        }
        android.util.Log.i(TAG, java.lang.String.format("Update GNSS satellite blocklist: %s", string));
        try {
            java.util.List<java.lang.Integer> parseSatelliteBlocklist = parseSatelliteBlocklist(string);
            if (parseSatelliteBlocklist.size() % 2 != 0) {
                android.util.Log.e(TAG, "blocklist string has odd number of values.Aborting updateSatelliteBlocklist");
                return;
            }
            int size = parseSatelliteBlocklist.size() / 2;
            int[] iArr = new int[size];
            int[] iArr2 = new int[size];
            for (int i = 0; i < size; i++) {
                int i2 = i * 2;
                iArr[i] = parseSatelliteBlocklist.get(i2).intValue();
                iArr2[i] = parseSatelliteBlocklist.get(i2 + 1).intValue();
            }
            this.mCallback.onUpdateSatelliteBlocklist(iArr, iArr2);
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.e(TAG, "Exception thrown when parsing blocklist string.", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.util.List<java.lang.Integer> parseSatelliteBlocklist(java.lang.String str) throws java.lang.NumberFormatException {
        java.lang.String[] split = str.split(BLOCKLIST_DELIMITER);
        java.util.ArrayList arrayList = new java.util.ArrayList(split.length);
        for (java.lang.String str2 : split) {
            java.lang.String trim = str2.trim();
            if (!"".equals(trim)) {
                int parseInt = java.lang.Integer.parseInt(trim);
                if (parseInt < 0) {
                    throw new java.lang.NumberFormatException("Negative value is invalid.");
                }
                arrayList.add(java.lang.Integer.valueOf(parseInt));
            }
        }
        return arrayList;
    }
}
