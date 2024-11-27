package android.media.audiofx;

/* loaded from: classes2.dex */
public class PresetReverb extends android.media.audiofx.AudioEffect {
    public static final int PARAM_PRESET = 0;
    public static final short PRESET_LARGEHALL = 5;
    public static final short PRESET_LARGEROOM = 3;
    public static final short PRESET_MEDIUMHALL = 4;
    public static final short PRESET_MEDIUMROOM = 2;
    public static final short PRESET_NONE = 0;
    public static final short PRESET_PLATE = 6;
    public static final short PRESET_SMALLROOM = 1;
    private static final java.lang.String TAG = "PresetReverb";
    private android.media.audiofx.PresetReverb.BaseParameterListener mBaseParamListener;
    private android.media.audiofx.PresetReverb.OnParameterChangeListener mParamListener;
    private final java.lang.Object mParamListenerLock;

    public interface OnParameterChangeListener {
        void onParameterChange(android.media.audiofx.PresetReverb presetReverb, int i, int i2, short s);
    }

    public PresetReverb(int i, int i2) throws java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        super(EFFECT_TYPE_PRESET_REVERB, EFFECT_TYPE_NULL, i, i2);
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new java.lang.Object();
    }

    public void setPreset(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(0, s));
    }

    public short getPreset() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        short[] sArr = new short[1];
        checkStatus(getParameter(0, sArr));
        return sArr[0];
    }

    private class BaseParameterListener implements android.media.audiofx.AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(android.media.audiofx.AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            android.media.audiofx.PresetReverb.OnParameterChangeListener onParameterChangeListener;
            int i2;
            short s;
            synchronized (android.media.audiofx.PresetReverb.this.mParamListenerLock) {
                if (android.media.audiofx.PresetReverb.this.mParamListener == null) {
                    onParameterChangeListener = null;
                } else {
                    onParameterChangeListener = android.media.audiofx.PresetReverb.this.mParamListener;
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
                    onParameterChangeListener.onParameterChange(android.media.audiofx.PresetReverb.this, i, i2, s);
                }
            }
        }
    }

    public void setParameterListener(android.media.audiofx.PresetReverb.OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                this.mParamListener = onParameterChangeListener;
                this.mBaseParamListener = new android.media.audiofx.PresetReverb.BaseParameterListener();
                super.setParameterListener(this.mBaseParamListener);
            }
        }
    }

    public static class Settings {
        public short preset;

        public Settings() {
        }

        public Settings(java.lang.String str) {
            java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, "=;");
            stringTokenizer.countTokens();
            if (stringTokenizer.countTokens() != 3) {
                throw new java.lang.IllegalArgumentException("settings: " + str);
            }
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals(android.media.audiofx.PresetReverb.TAG)) {
                throw new java.lang.IllegalArgumentException("invalid settings for PresetReverb: " + nextToken);
            }
            try {
                java.lang.String nextToken2 = stringTokenizer.nextToken();
                if (!nextToken2.equals("preset")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken2);
                }
                this.preset = java.lang.Short.parseShort(stringTokenizer.nextToken());
            } catch (java.lang.NumberFormatException e) {
                throw new java.lang.IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public java.lang.String toString() {
            return new java.lang.String("PresetReverb;preset=" + java.lang.Short.toString(this.preset));
        }
    }

    public android.media.audiofx.PresetReverb.Settings getProperties() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        android.media.audiofx.PresetReverb.Settings settings = new android.media.audiofx.PresetReverb.Settings();
        short[] sArr = new short[1];
        checkStatus(getParameter(0, sArr));
        settings.preset = sArr[0];
        return settings;
    }

    public void setProperties(android.media.audiofx.PresetReverb.Settings settings) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(0, settings.preset));
    }
}
