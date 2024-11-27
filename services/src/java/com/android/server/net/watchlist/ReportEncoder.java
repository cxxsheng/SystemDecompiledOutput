package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
class ReportEncoder {
    private static final int REPORT_VERSION = 1;
    private static final java.lang.String TAG = "ReportEncoder";
    private static final int WATCHLIST_HASH_SIZE = 32;

    ReportEncoder() {
    }

    @android.annotation.Nullable
    static byte[] encodeWatchlistReport(com.android.server.net.watchlist.WatchlistConfig watchlistConfig, byte[] bArr, java.util.List<java.lang.String> list, com.android.server.net.watchlist.WatchlistReportDbHelper.AggregatedResult aggregatedResult) {
        return serializeReport(watchlistConfig, com.android.server.net.watchlist.PrivacyUtils.createDpEncodedReportMap(watchlistConfig.isConfigSecure(), bArr, list, aggregatedResult));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static byte[] serializeReport(com.android.server.net.watchlist.WatchlistConfig watchlistConfig, java.util.Map<java.lang.String, java.lang.Boolean> map) {
        byte[] watchlistConfigHash = watchlistConfig.getWatchlistConfigHash();
        if (watchlistConfigHash == null) {
            android.util.Log.e(TAG, "No watchlist hash");
            return null;
        }
        if (watchlistConfigHash.length != 32) {
            android.util.Log.e(TAG, "Unexpected hash length");
            return null;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(byteArrayOutputStream);
        protoOutputStream.write(1120986464257L, 1);
        protoOutputStream.write(1138166333442L, com.android.internal.util.HexDump.toHexString(watchlistConfigHash));
        for (java.util.Map.Entry<java.lang.String, java.lang.Boolean> entry : map.entrySet()) {
            java.lang.String key = entry.getKey();
            com.android.internal.util.HexDump.hexStringToByteArray(key);
            boolean booleanValue = entry.getValue().booleanValue();
            long start = protoOutputStream.start(2246267895811L);
            protoOutputStream.write(1138166333441L, key);
            protoOutputStream.write(1133871366146L, booleanValue);
            protoOutputStream.end(start);
        }
        protoOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}
