package com.android.server.vcn.repackaged.util;

/* loaded from: classes5.dex */
public class MtuUtils {
    private static final java.util.Map<java.lang.Integer, java.lang.Integer> AUTHCRYPT_ALGORITHM_OVERHEAD;
    private static final java.util.Map<java.lang.Integer, java.lang.Integer> AUTH_ALGORITHM_OVERHEAD;
    private static final java.util.Map<java.lang.Integer, java.lang.Integer> CRYPT_ALGORITHM_OVERHEAD;
    private static final int GENERIC_ESP_OVERHEAD_MAX_V4 = 78;
    private static final int GENERIC_ESP_OVERHEAD_MAX_V6 = 50;
    private static final java.lang.String TAG = com.android.server.vcn.repackaged.util.MtuUtils.class.getSimpleName();

    static {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        arrayMap.put(0, 0);
        arrayMap.put(2, 12);
        arrayMap.put(5, 12);
        arrayMap.put(12, 32);
        arrayMap.put(13, 48);
        arrayMap.put(14, 64);
        arrayMap.put(8, 12);
        AUTH_ALGORITHM_OVERHEAD = java.util.Collections.unmodifiableMap(arrayMap);
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        arrayMap2.put(3, 15);
        arrayMap2.put(12, 31);
        arrayMap2.put(13, 11);
        CRYPT_ALGORITHM_OVERHEAD = java.util.Collections.unmodifiableMap(arrayMap2);
        android.util.ArrayMap arrayMap3 = new android.util.ArrayMap();
        arrayMap3.put(18, 19);
        arrayMap3.put(19, 23);
        arrayMap3.put(20, 27);
        arrayMap3.put(28, 27);
        AUTHCRYPT_ALGORITHM_OVERHEAD = java.util.Collections.unmodifiableMap(arrayMap3);
    }

    public static int getMtu(java.util.List<android.net.ipsec.ike.ChildSaProposal> list, int i, int i2, boolean z) {
        if (i2 <= 0) {
            return 1280;
        }
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (android.net.ipsec.ike.ChildSaProposal childSaProposal : list) {
            java.util.Iterator<android.util.Pair<java.lang.Integer, java.lang.Integer>> it = childSaProposal.getEncryptionAlgorithms().iterator();
            while (it.hasNext()) {
                int intValue = it.next().first.intValue();
                if (AUTHCRYPT_ALGORITHM_OVERHEAD.containsKey(java.lang.Integer.valueOf(intValue))) {
                    i3 = java.lang.Math.max(i3, AUTHCRYPT_ALGORITHM_OVERHEAD.get(java.lang.Integer.valueOf(intValue)).intValue());
                } else if (CRYPT_ALGORITHM_OVERHEAD.containsKey(java.lang.Integer.valueOf(intValue))) {
                    i4 = java.lang.Math.max(i4, CRYPT_ALGORITHM_OVERHEAD.get(java.lang.Integer.valueOf(intValue)).intValue());
                } else {
                    android.util.Slog.wtf(TAG, "Unknown encryption algorithm requested: " + intValue);
                    return 1280;
                }
            }
            java.util.Iterator<java.lang.Integer> it2 = childSaProposal.getIntegrityAlgorithms().iterator();
            while (it2.hasNext()) {
                int intValue2 = it2.next().intValue();
                if (AUTH_ALGORITHM_OVERHEAD.containsKey(java.lang.Integer.valueOf(intValue2))) {
                    i5 = java.lang.Math.max(i5, AUTH_ALGORITHM_OVERHEAD.get(java.lang.Integer.valueOf(intValue2)).intValue());
                } else {
                    android.util.Slog.wtf(TAG, "Unknown integrity algorithm requested: " + intValue2);
                    return 1280;
                }
            }
        }
        int i6 = z ? 78 : 50;
        return java.lang.Math.min(java.lang.Math.min(i, (i2 - i3) - i6), ((i2 - i4) - i5) - i6);
    }
}
