package android.media.audiofx;

/* loaded from: classes2.dex */
public class NoiseSuppressor extends android.media.audiofx.AudioEffect {
    private static final java.lang.String TAG = "NoiseSuppressor";

    public static boolean isAvailable() {
        return android.media.audiofx.AudioEffect.isEffectTypeAvailable(android.media.audiofx.AudioEffect.EFFECT_TYPE_NS);
    }

    public static android.media.audiofx.NoiseSuppressor create(int i) {
        try {
            return new android.media.audiofx.NoiseSuppressor(i);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "not implemented on this device " + ((java.lang.Object) null));
            return null;
        } catch (java.lang.UnsupportedOperationException e2) {
            android.util.Log.w(TAG, "not enough resources");
            return null;
        } catch (java.lang.RuntimeException e3) {
            android.util.Log.w(TAG, "not enough memory");
            return null;
        }
    }

    private NoiseSuppressor(int i) throws java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        super(EFFECT_TYPE_NS, EFFECT_TYPE_NULL, 0, i);
    }
}
