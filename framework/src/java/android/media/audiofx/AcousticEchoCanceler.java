package android.media.audiofx;

/* loaded from: classes2.dex */
public class AcousticEchoCanceler extends android.media.audiofx.AudioEffect {
    private static final java.lang.String TAG = "AcousticEchoCanceler";

    public static boolean isAvailable() {
        return android.media.audiofx.AudioEffect.isEffectTypeAvailable(android.media.audiofx.AudioEffect.EFFECT_TYPE_AEC);
    }

    public static android.media.audiofx.AcousticEchoCanceler create(int i) {
        try {
            return new android.media.audiofx.AcousticEchoCanceler(i);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "not implemented on this device" + ((java.lang.Object) null));
            return null;
        } catch (java.lang.UnsupportedOperationException e2) {
            android.util.Log.w(TAG, "not enough resources");
            return null;
        } catch (java.lang.RuntimeException e3) {
            android.util.Log.w(TAG, "not enough memory");
            return null;
        }
    }

    private AcousticEchoCanceler(int i) throws java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        super(EFFECT_TYPE_AEC, EFFECT_TYPE_NULL, 0, i);
    }
}
