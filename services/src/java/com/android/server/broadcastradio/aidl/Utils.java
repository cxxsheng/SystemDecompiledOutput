package com.android.server.broadcastradio.aidl;

/* loaded from: classes.dex */
final class Utils {

    public enum FrequencyBand {
        UNKNOWN,
        FM,
        AM_LW,
        AM_MW,
        AM_SW
    }

    private Utils() {
        throw new java.lang.UnsupportedOperationException("Utils class is noninstantiable");
    }

    static com.android.server.broadcastradio.aidl.Utils.FrequencyBand getBand(int i) {
        return i < 30 ? com.android.server.broadcastradio.aidl.Utils.FrequencyBand.UNKNOWN : i < 500 ? com.android.server.broadcastradio.aidl.Utils.FrequencyBand.AM_LW : i < 1705 ? com.android.server.broadcastradio.aidl.Utils.FrequencyBand.AM_MW : i < 30000 ? com.android.server.broadcastradio.aidl.Utils.FrequencyBand.AM_SW : i < 60000 ? com.android.server.broadcastradio.aidl.Utils.FrequencyBand.UNKNOWN : i < 110000 ? com.android.server.broadcastradio.aidl.Utils.FrequencyBand.FM : com.android.server.broadcastradio.aidl.Utils.FrequencyBand.UNKNOWN;
    }
}
