package android.media.tv.tuner;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class TunerVersionChecker {
    private static final java.lang.String TAG = "TunerVersionChecker";
    public static final int TUNER_VERSION_1_0 = 65536;
    public static final int TUNER_VERSION_1_1 = 65537;
    public static final int TUNER_VERSION_2_0 = 131072;
    public static final int TUNER_VERSION_3_0 = 196608;
    public static final int TUNER_VERSION_UNKNOWN = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TunerVersion {
    }

    private TunerVersionChecker() {
    }

    public static int getTunerVersion() {
        return android.media.tv.tuner.Tuner.getTunerVersion();
    }

    public static boolean supportTunerVersion(int i) {
        return isHigherOrEqualVersionTo(i) && getMajorVersion(i) == getMajorVersion(android.media.tv.tuner.Tuner.getTunerVersion());
    }

    public static boolean isHigherOrEqualVersionTo(int i) {
        return android.media.tv.tuner.Tuner.getTunerVersion() >= i;
    }

    public static int getMajorVersion(int i) {
        return (i & (-65536)) >>> 16;
    }

    public static int getMinorVersion(int i) {
        return i & 65535;
    }

    public static boolean checkHigherOrEqualVersionTo(int i, java.lang.String str) {
        if (!isHigherOrEqualVersionTo(i)) {
            android.util.Log.e(TAG, "Current Tuner version " + getMajorVersion(android.media.tv.tuner.Tuner.getTunerVersion()) + android.media.MediaMetrics.SEPARATOR + getMinorVersion(android.media.tv.tuner.Tuner.getTunerVersion()) + " does not support " + str + android.media.MediaMetrics.SEPARATOR);
            return false;
        }
        return true;
    }

    public static boolean checkSupportVersion(int i, java.lang.String str) {
        if (!supportTunerVersion(i)) {
            android.util.Log.e(TAG, "Current Tuner version " + getMajorVersion(android.media.tv.tuner.Tuner.getTunerVersion()) + android.media.MediaMetrics.SEPARATOR + getMinorVersion(android.media.tv.tuner.Tuner.getTunerVersion()) + " does not support " + str + android.media.MediaMetrics.SEPARATOR);
            return false;
        }
        return true;
    }
}
