package android.media.audiofx;

/* loaded from: classes2.dex */
public class Equalizer extends android.media.audiofx.AudioEffect {
    public static final int PARAM_BAND_FREQ_RANGE = 4;
    public static final int PARAM_BAND_LEVEL = 2;
    public static final int PARAM_CENTER_FREQ = 3;
    public static final int PARAM_CURRENT_PRESET = 6;
    public static final int PARAM_GET_BAND = 5;
    public static final int PARAM_GET_NUM_OF_PRESETS = 7;
    public static final int PARAM_GET_PRESET_NAME = 8;
    public static final int PARAM_LEVEL_RANGE = 1;
    public static final int PARAM_NUM_BANDS = 0;
    private static final int PARAM_PROPERTIES = 9;
    public static final int PARAM_STRING_SIZE_MAX = 32;
    private static final java.lang.String TAG = "Equalizer";
    private android.media.audiofx.Equalizer.BaseParameterListener mBaseParamListener;
    private short mNumBands;
    private int mNumPresets;
    private android.media.audiofx.Equalizer.OnParameterChangeListener mParamListener;
    private final java.lang.Object mParamListenerLock;
    private java.lang.String[] mPresetNames;

    public interface OnParameterChangeListener {
        void onParameterChange(android.media.audiofx.Equalizer equalizer, int i, int i2, int i3, int i4);
    }

    public Equalizer(int i, int i2) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        super(EFFECT_TYPE_EQUALIZER, EFFECT_TYPE_NULL, i, i2);
        this.mNumBands = (short) 0;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new java.lang.Object();
        if (i2 == 0) {
            android.util.Log.w(TAG, "WARNING: attaching an Equalizer to global output mix is deprecated!");
        }
        getNumberOfBands();
        this.mNumPresets = getNumberOfPresets();
        if (this.mNumPresets != 0) {
            this.mPresetNames = new java.lang.String[this.mNumPresets];
            byte[] bArr = new byte[32];
            for (int i3 = 0; i3 < this.mNumPresets; i3++) {
                checkStatus(getParameter(new int[]{8, i3}, bArr));
                int i4 = 0;
                while (bArr[i4] != 0) {
                    i4++;
                }
                try {
                    this.mPresetNames[i3] = new java.lang.String(bArr, 0, i4, "ISO-8859-1");
                } catch (java.io.UnsupportedEncodingException e) {
                    android.util.Log.e(TAG, "preset name decode error");
                }
            }
        }
    }

    public short getNumberOfBands() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        if (this.mNumBands != 0) {
            return this.mNumBands;
        }
        short[] sArr = new short[1];
        checkStatus(getParameter(new int[]{0}, sArr));
        this.mNumBands = sArr[0];
        return this.mNumBands;
    }

    public short[] getBandLevelRange() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        short[] sArr = new short[2];
        checkStatus(getParameter(1, sArr));
        return sArr;
    }

    public void setBandLevel(short s, short s2) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(new int[]{2, s}, new short[]{s2}));
    }

    public short getBandLevel(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        short[] sArr = new short[1];
        checkStatus(getParameter(new int[]{2, s}, sArr));
        return sArr[0];
    }

    public int getCenterFreq(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        int[] iArr = new int[1];
        checkStatus(getParameter(new int[]{3, s}, iArr));
        return iArr[0];
    }

    public int[] getBandFreqRange(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        int[] iArr = new int[2];
        checkStatus(getParameter(new int[]{4, s}, iArr));
        return iArr;
    }

    public short getBand(int i) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        short[] sArr = new short[1];
        checkStatus(getParameter(new int[]{5, i}, sArr));
        return sArr[0];
    }

    public short getCurrentPreset() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        short[] sArr = new short[1];
        checkStatus(getParameter(6, sArr));
        return sArr[0];
    }

    public void usePreset(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(6, s));
    }

    public short getNumberOfPresets() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        short[] sArr = new short[1];
        checkStatus(getParameter(7, sArr));
        return sArr[0];
    }

    public java.lang.String getPresetName(short s) {
        if (s >= 0 && s < this.mNumPresets) {
            return this.mPresetNames[s];
        }
        return "";
    }

    private class BaseParameterListener implements android.media.audiofx.AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(android.media.audiofx.AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            android.media.audiofx.Equalizer.OnParameterChangeListener onParameterChangeListener;
            int i2;
            int i3;
            int i4;
            synchronized (android.media.audiofx.Equalizer.this.mParamListenerLock) {
                if (android.media.audiofx.Equalizer.this.mParamListener == null) {
                    onParameterChangeListener = null;
                } else {
                    onParameterChangeListener = android.media.audiofx.Equalizer.this.mParamListener;
                }
            }
            if (onParameterChangeListener != null) {
                if (bArr.length < 4) {
                    i2 = -1;
                    i3 = -1;
                } else {
                    int byteArrayToInt = android.media.audiofx.AudioEffect.byteArrayToInt(bArr, 0);
                    if (bArr.length < 8) {
                        i2 = byteArrayToInt;
                        i3 = -1;
                    } else {
                        i2 = byteArrayToInt;
                        i3 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr, 4);
                    }
                }
                if (bArr2.length == 2) {
                    i4 = android.media.audiofx.AudioEffect.byteArrayToShort(bArr2, 0);
                } else if (bArr2.length != 4) {
                    i4 = -1;
                } else {
                    i4 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr2, 0);
                }
                if (i2 != -1 && i4 != -1) {
                    onParameterChangeListener.onParameterChange(android.media.audiofx.Equalizer.this, i, i2, i3, i4);
                }
            }
        }
    }

    public void setParameterListener(android.media.audiofx.Equalizer.OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                this.mParamListener = onParameterChangeListener;
                this.mBaseParamListener = new android.media.audiofx.Equalizer.BaseParameterListener();
                super.setParameterListener(this.mBaseParamListener);
            }
        }
    }

    public static class Settings {
        public short[] bandLevels;
        public short curPreset;
        public short numBands;

        public Settings() {
            this.numBands = (short) 0;
            this.bandLevels = null;
        }

        public Settings(java.lang.String str) {
            int i = 0;
            this.numBands = (short) 0;
            this.bandLevels = null;
            java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, "=;");
            stringTokenizer.countTokens();
            if (stringTokenizer.countTokens() < 5) {
                throw new java.lang.IllegalArgumentException("settings: " + str);
            }
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals(android.media.audiofx.Equalizer.TAG)) {
                throw new java.lang.IllegalArgumentException("invalid settings for Equalizer: " + nextToken);
            }
            try {
                java.lang.String nextToken2 = stringTokenizer.nextToken();
                if (!nextToken2.equals("curPreset")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken2);
                }
                this.curPreset = java.lang.Short.parseShort(stringTokenizer.nextToken());
                java.lang.String nextToken3 = stringTokenizer.nextToken();
                if (!nextToken3.equals("numBands")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken3);
                }
                this.numBands = java.lang.Short.parseShort(stringTokenizer.nextToken());
                if (stringTokenizer.countTokens() != this.numBands * 2) {
                    throw new java.lang.IllegalArgumentException("settings: " + str);
                }
                this.bandLevels = new short[this.numBands];
                while (i < this.numBands) {
                    java.lang.String nextToken4 = stringTokenizer.nextToken();
                    int i2 = i + 1;
                    if (!nextToken4.equals("band" + i2 + "Level")) {
                        throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken4);
                    }
                    this.bandLevels[i] = java.lang.Short.parseShort(stringTokenizer.nextToken());
                    i = i2;
                }
            } catch (java.lang.NumberFormatException e) {
                throw new java.lang.IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public java.lang.String toString() {
            java.lang.String str = new java.lang.String("Equalizer;curPreset=" + java.lang.Short.toString(this.curPreset) + ";numBands=" + java.lang.Short.toString(this.numBands));
            int i = 0;
            while (i < this.numBands) {
                int i2 = i + 1;
                str = str.concat(";band" + i2 + "Level=" + java.lang.Short.toString(this.bandLevels[i]));
                i = i2;
            }
            return str;
        }
    }

    public android.media.audiofx.Equalizer.Settings getProperties() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[(this.mNumBands * 2) + 4];
        checkStatus(getParameter(9, bArr));
        android.media.audiofx.Equalizer.Settings settings = new android.media.audiofx.Equalizer.Settings();
        settings.curPreset = byteArrayToShort(bArr, 0);
        settings.numBands = byteArrayToShort(bArr, 2);
        settings.bandLevels = new short[this.mNumBands];
        for (int i = 0; i < this.mNumBands; i++) {
            settings.bandLevels[i] = byteArrayToShort(bArr, (i * 2) + 4);
        }
        return settings;
    }

    public void setProperties(android.media.audiofx.Equalizer.Settings settings) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        if (settings.numBands != settings.bandLevels.length || settings.numBands != this.mNumBands) {
            throw new java.lang.IllegalArgumentException("settings invalid band count: " + ((int) settings.numBands));
        }
        byte[] concatArrays = concatArrays(shortToByteArray(settings.curPreset), shortToByteArray(this.mNumBands));
        for (int i = 0; i < this.mNumBands; i++) {
            concatArrays = concatArrays(concatArrays, shortToByteArray(settings.bandLevels[i]));
        }
        checkStatus(setParameter(9, concatArrays));
    }
}
