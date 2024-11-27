package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
class PrivacyUtils {
    private static final boolean DEBUG = false;
    private static final java.lang.String ENCODER_ID_PREFIX = "watchlist_encoder:";
    private static final double PROB_F = 0.469d;
    private static final double PROB_P = 0.28d;
    private static final double PROB_Q = 1.0d;
    private static final java.lang.String TAG = "PrivacyUtils";

    private PrivacyUtils() {
    }

    @com.android.internal.annotations.VisibleForTesting
    static android.privacy.DifferentialPrivacyEncoder createInsecureDPEncoderForTest(java.lang.String str) {
        return android.privacy.internal.longitudinalreporting.LongitudinalReportingEncoder.createInsecureEncoderForTest(createLongitudinalReportingConfig(str));
    }

    @com.android.internal.annotations.VisibleForTesting
    static android.privacy.DifferentialPrivacyEncoder createSecureDPEncoder(byte[] bArr, java.lang.String str) {
        return android.privacy.internal.longitudinalreporting.LongitudinalReportingEncoder.createEncoder(createLongitudinalReportingConfig(str), bArr);
    }

    private static android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig createLongitudinalReportingConfig(java.lang.String str) {
        return new android.privacy.internal.longitudinalreporting.LongitudinalReportingConfig(ENCODER_ID_PREFIX + str, PROB_F, PROB_P, PROB_Q);
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.util.Map<java.lang.String, java.lang.Boolean> createDpEncodedReportMap(boolean z, byte[] bArr, java.util.List<java.lang.String> list, com.android.server.net.watchlist.WatchlistReportDbHelper.AggregatedResult aggregatedResult) {
        android.privacy.DifferentialPrivacyEncoder createInsecureDPEncoderForTest;
        int size = list.size();
        java.util.HashMap hashMap = new java.util.HashMap(size);
        for (int i = 0; i < size; i++) {
            java.lang.String str = list.get(i);
            if (z) {
                createInsecureDPEncoderForTest = createSecureDPEncoder(bArr, str);
            } else {
                createInsecureDPEncoderForTest = createInsecureDPEncoderForTest(str);
            }
            boolean z2 = true;
            if ((createInsecureDPEncoderForTest.encodeBoolean(aggregatedResult.appDigestList.contains(str))[0] & 1) != 1) {
                z2 = false;
            }
            hashMap.put(str, java.lang.Boolean.valueOf(z2));
        }
        return hashMap;
    }
}
