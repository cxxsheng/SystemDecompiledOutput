package android.media.audiofx;

/* loaded from: classes2.dex */
public class LoudnessEnhancer extends android.media.audiofx.AudioEffect {
    public static final int PARAM_TARGET_GAIN_MB = 0;
    private static final java.lang.String TAG = "LoudnessEnhancer";
    private android.media.audiofx.LoudnessEnhancer.BaseParameterListener mBaseParamListener;
    private android.media.audiofx.LoudnessEnhancer.OnParameterChangeListener mParamListener;
    private final java.lang.Object mParamListenerLock;

    public interface OnParameterChangeListener {
        void onParameterChange(android.media.audiofx.LoudnessEnhancer loudnessEnhancer, int i, int i2);
    }

    public LoudnessEnhancer(int i) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        super(EFFECT_TYPE_LOUDNESS_ENHANCER, EFFECT_TYPE_NULL, 0, i);
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new java.lang.Object();
        if (i == 0) {
            android.util.Log.w(TAG, "WARNING: attaching a LoudnessEnhancer to global output mix is deprecated!");
        }
    }

    public LoudnessEnhancer(int i, int i2) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        super(EFFECT_TYPE_LOUDNESS_ENHANCER, EFFECT_TYPE_NULL, i, i2);
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new java.lang.Object();
        if (i2 == 0) {
            android.util.Log.w(TAG, "WARNING: attaching a LoudnessEnhancer to global output mix is deprecated!");
        }
    }

    public void setTargetGain(int i) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(0, i));
    }

    public float getTargetGain() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(getParameter(0, new int[1]));
        return r0[0];
    }

    private class BaseParameterListener implements android.media.audiofx.AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(android.media.audiofx.AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            android.media.audiofx.LoudnessEnhancer.OnParameterChangeListener onParameterChangeListener;
            int i2;
            int i3;
            if (i != 0) {
                return;
            }
            synchronized (android.media.audiofx.LoudnessEnhancer.this.mParamListenerLock) {
                if (android.media.audiofx.LoudnessEnhancer.this.mParamListener == null) {
                    onParameterChangeListener = null;
                } else {
                    onParameterChangeListener = android.media.audiofx.LoudnessEnhancer.this.mParamListener;
                }
            }
            if (onParameterChangeListener != null) {
                if (bArr.length != 4) {
                    i2 = -1;
                } else {
                    i2 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr, 0);
                }
                if (bArr2.length != 4) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    i3 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr2, 0);
                }
                if (i2 != -1 && i3 != Integer.MIN_VALUE) {
                    onParameterChangeListener.onParameterChange(android.media.audiofx.LoudnessEnhancer.this, i2, i3);
                }
            }
        }
    }

    public void setParameterListener(android.media.audiofx.LoudnessEnhancer.OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                this.mBaseParamListener = new android.media.audiofx.LoudnessEnhancer.BaseParameterListener();
                super.setParameterListener(this.mBaseParamListener);
            }
            this.mParamListener = onParameterChangeListener;
        }
    }

    public static class Settings {
        public int targetGainmB;

        public Settings() {
        }

        public Settings(java.lang.String str) {
            java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, "=;");
            if (stringTokenizer.countTokens() != 3) {
                throw new java.lang.IllegalArgumentException("settings: " + str);
            }
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals(android.media.audiofx.LoudnessEnhancer.TAG)) {
                throw new java.lang.IllegalArgumentException("invalid settings for LoudnessEnhancer: " + nextToken);
            }
            try {
                java.lang.String nextToken2 = stringTokenizer.nextToken();
                if (!nextToken2.equals("targetGainmB")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken2);
                }
                this.targetGainmB = java.lang.Integer.parseInt(stringTokenizer.nextToken());
            } catch (java.lang.NumberFormatException e) {
                throw new java.lang.IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public java.lang.String toString() {
            return new java.lang.String("LoudnessEnhancer;targetGainmB=" + java.lang.Integer.toString(this.targetGainmB));
        }
    }

    public android.media.audiofx.LoudnessEnhancer.Settings getProperties() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        android.media.audiofx.LoudnessEnhancer.Settings settings = new android.media.audiofx.LoudnessEnhancer.Settings();
        int[] iArr = new int[1];
        checkStatus(getParameter(0, iArr));
        settings.targetGainmB = iArr[0];
        return settings;
    }

    public void setProperties(android.media.audiofx.LoudnessEnhancer.Settings settings) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(0, settings.targetGainmB));
    }
}
