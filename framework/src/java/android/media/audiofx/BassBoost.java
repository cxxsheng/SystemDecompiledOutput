package android.media.audiofx;

/* loaded from: classes2.dex */
public class BassBoost extends android.media.audiofx.AudioEffect {
    public static final int PARAM_STRENGTH = 1;
    public static final int PARAM_STRENGTH_SUPPORTED = 0;
    private static final java.lang.String TAG = "BassBoost";
    private android.media.audiofx.BassBoost.BaseParameterListener mBaseParamListener;
    private android.media.audiofx.BassBoost.OnParameterChangeListener mParamListener;
    private final java.lang.Object mParamListenerLock;
    private boolean mStrengthSupported;

    public interface OnParameterChangeListener {
        void onParameterChange(android.media.audiofx.BassBoost bassBoost, int i, int i2, short s);
    }

    public BassBoost(int i, int i2) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        super(EFFECT_TYPE_BASS_BOOST, EFFECT_TYPE_NULL, i, i2);
        this.mStrengthSupported = false;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new java.lang.Object();
        if (i2 == 0) {
            android.util.Log.w(TAG, "WARNING: attaching a BassBoost to global output mix is deprecated!");
        }
        int[] iArr = new int[1];
        checkStatus(getParameter(0, iArr));
        this.mStrengthSupported = iArr[0] != 0;
    }

    public boolean getStrengthSupported() {
        return this.mStrengthSupported;
    }

    public void setStrength(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(1, s));
    }

    public short getRoundedStrength() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        short[] sArr = new short[1];
        checkStatus(getParameter(1, sArr));
        return sArr[0];
    }

    private class BaseParameterListener implements android.media.audiofx.AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(android.media.audiofx.AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            android.media.audiofx.BassBoost.OnParameterChangeListener onParameterChangeListener;
            int i2;
            short s;
            synchronized (android.media.audiofx.BassBoost.this.mParamListenerLock) {
                if (android.media.audiofx.BassBoost.this.mParamListener == null) {
                    onParameterChangeListener = null;
                } else {
                    onParameterChangeListener = android.media.audiofx.BassBoost.this.mParamListener;
                }
            }
            if (onParameterChangeListener != null) {
                if (bArr.length != 4) {
                    i2 = -1;
                } else {
                    i2 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr, 0);
                }
                if (bArr2.length != 2) {
                    s = -1;
                } else {
                    s = android.media.audiofx.AudioEffect.byteArrayToShort(bArr2, 0);
                }
                if (i2 != -1 && s != -1) {
                    onParameterChangeListener.onParameterChange(android.media.audiofx.BassBoost.this, i, i2, s);
                }
            }
        }
    }

    public void setParameterListener(android.media.audiofx.BassBoost.OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                this.mParamListener = onParameterChangeListener;
                this.mBaseParamListener = new android.media.audiofx.BassBoost.BaseParameterListener();
                super.setParameterListener(this.mBaseParamListener);
            }
        }
    }

    public static class Settings {
        public short strength;

        public Settings() {
        }

        public Settings(java.lang.String str) {
            java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, "=;");
            stringTokenizer.countTokens();
            if (stringTokenizer.countTokens() != 3) {
                throw new java.lang.IllegalArgumentException("settings: " + str);
            }
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals(android.media.audiofx.BassBoost.TAG)) {
                throw new java.lang.IllegalArgumentException("invalid settings for BassBoost: " + nextToken);
            }
            try {
                java.lang.String nextToken2 = stringTokenizer.nextToken();
                if (!nextToken2.equals("strength")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken2);
                }
                this.strength = java.lang.Short.parseShort(stringTokenizer.nextToken());
            } catch (java.lang.NumberFormatException e) {
                throw new java.lang.IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public java.lang.String toString() {
            return new java.lang.String("BassBoost;strength=" + java.lang.Short.toString(this.strength));
        }
    }

    public android.media.audiofx.BassBoost.Settings getProperties() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        android.media.audiofx.BassBoost.Settings settings = new android.media.audiofx.BassBoost.Settings();
        short[] sArr = new short[1];
        checkStatus(getParameter(1, sArr));
        settings.strength = sArr[0];
        return settings;
    }

    public void setProperties(android.media.audiofx.BassBoost.Settings settings) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(1, settings.strength));
    }
}
