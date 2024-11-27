package android.media.audiofx;

/* loaded from: classes2.dex */
public class EnvironmentalReverb extends android.media.audiofx.AudioEffect {
    public static final int PARAM_DECAY_HF_RATIO = 3;
    public static final int PARAM_DECAY_TIME = 2;
    public static final int PARAM_DENSITY = 9;
    public static final int PARAM_DIFFUSION = 8;
    private static final int PARAM_PROPERTIES = 10;
    public static final int PARAM_REFLECTIONS_DELAY = 5;
    public static final int PARAM_REFLECTIONS_LEVEL = 4;
    public static final int PARAM_REVERB_DELAY = 7;
    public static final int PARAM_REVERB_LEVEL = 6;
    public static final int PARAM_ROOM_HF_LEVEL = 1;
    public static final int PARAM_ROOM_LEVEL = 0;
    private static int PROPERTY_SIZE = 26;
    private static final java.lang.String TAG = "EnvironmentalReverb";
    private android.media.audiofx.EnvironmentalReverb.BaseParameterListener mBaseParamListener;
    private android.media.audiofx.EnvironmentalReverb.OnParameterChangeListener mParamListener;
    private final java.lang.Object mParamListenerLock;

    public interface OnParameterChangeListener {
        void onParameterChange(android.media.audiofx.EnvironmentalReverb environmentalReverb, int i, int i2, int i3);
    }

    public EnvironmentalReverb(int i, int i2) throws java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        super(EFFECT_TYPE_ENV_REVERB, EFFECT_TYPE_NULL, i, i2);
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new java.lang.Object();
    }

    public void setRoomLevel(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(0, shortToByteArray(s)));
    }

    public short getRoomLevel() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(0, bArr));
        return byteArrayToShort(bArr);
    }

    public void setRoomHFLevel(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(1, shortToByteArray(s)));
    }

    public short getRoomHFLevel() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(1, bArr));
        return byteArrayToShort(bArr);
    }

    public void setDecayTime(int i) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(2, intToByteArray(i)));
    }

    public int getDecayTime() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[4];
        checkStatus(getParameter(2, bArr));
        return byteArrayToInt(bArr);
    }

    public void setDecayHFRatio(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(3, shortToByteArray(s)));
    }

    public short getDecayHFRatio() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(3, bArr));
        return byteArrayToShort(bArr);
    }

    public void setReflectionsLevel(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(4, shortToByteArray(s)));
    }

    public short getReflectionsLevel() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(4, bArr));
        return byteArrayToShort(bArr);
    }

    public void setReflectionsDelay(int i) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(5, intToByteArray(i)));
    }

    public int getReflectionsDelay() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[4];
        checkStatus(getParameter(5, bArr));
        return byteArrayToInt(bArr);
    }

    public void setReverbLevel(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(6, shortToByteArray(s)));
    }

    public short getReverbLevel() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(6, bArr));
        return byteArrayToShort(bArr);
    }

    public void setReverbDelay(int i) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(7, intToByteArray(i)));
    }

    public int getReverbDelay() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[4];
        checkStatus(getParameter(7, bArr));
        return byteArrayToInt(bArr);
    }

    public void setDiffusion(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(8, shortToByteArray(s)));
    }

    public short getDiffusion() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(8, bArr));
        return byteArrayToShort(bArr);
    }

    public void setDensity(short s) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(9, shortToByteArray(s)));
    }

    public short getDensity() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(9, bArr));
        return byteArrayToShort(bArr);
    }

    private class BaseParameterListener implements android.media.audiofx.AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(android.media.audiofx.AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            android.media.audiofx.EnvironmentalReverb.OnParameterChangeListener onParameterChangeListener;
            int i2;
            int i3;
            synchronized (android.media.audiofx.EnvironmentalReverb.this.mParamListenerLock) {
                if (android.media.audiofx.EnvironmentalReverb.this.mParamListener == null) {
                    onParameterChangeListener = null;
                } else {
                    onParameterChangeListener = android.media.audiofx.EnvironmentalReverb.this.mParamListener;
                }
            }
            if (onParameterChangeListener != null) {
                if (bArr.length != 4) {
                    i2 = -1;
                } else {
                    i2 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr, 0);
                }
                if (bArr2.length == 2) {
                    i3 = android.media.audiofx.AudioEffect.byteArrayToShort(bArr2, 0);
                } else if (bArr2.length != 4) {
                    i3 = -1;
                } else {
                    i3 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr2, 0);
                }
                if (i2 != -1 && i3 != -1) {
                    onParameterChangeListener.onParameterChange(android.media.audiofx.EnvironmentalReverb.this, i, i2, i3);
                }
            }
        }
    }

    public void setParameterListener(android.media.audiofx.EnvironmentalReverb.OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                this.mParamListener = onParameterChangeListener;
                this.mBaseParamListener = new android.media.audiofx.EnvironmentalReverb.BaseParameterListener();
                super.setParameterListener(this.mBaseParamListener);
            }
        }
    }

    public static class Settings {
        public short decayHFRatio;
        public int decayTime;
        public short density;
        public short diffusion;
        public int reflectionsDelay;
        public short reflectionsLevel;
        public int reverbDelay;
        public short reverbLevel;
        public short roomHFLevel;
        public short roomLevel;

        public Settings() {
        }

        public Settings(java.lang.String str) {
            java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, "=;");
            stringTokenizer.countTokens();
            if (stringTokenizer.countTokens() != 21) {
                throw new java.lang.IllegalArgumentException("settings: " + str);
            }
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals(android.media.audiofx.EnvironmentalReverb.TAG)) {
                throw new java.lang.IllegalArgumentException("invalid settings for EnvironmentalReverb: " + nextToken);
            }
            try {
                java.lang.String nextToken2 = stringTokenizer.nextToken();
                if (!nextToken2.equals("roomLevel")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken2);
                }
                this.roomLevel = java.lang.Short.parseShort(stringTokenizer.nextToken());
                java.lang.String nextToken3 = stringTokenizer.nextToken();
                if (!nextToken3.equals("roomHFLevel")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken3);
                }
                this.roomHFLevel = java.lang.Short.parseShort(stringTokenizer.nextToken());
                java.lang.String nextToken4 = stringTokenizer.nextToken();
                if (!nextToken4.equals("decayTime")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken4);
                }
                this.decayTime = java.lang.Integer.parseInt(stringTokenizer.nextToken());
                java.lang.String nextToken5 = stringTokenizer.nextToken();
                if (!nextToken5.equals("decayHFRatio")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken5);
                }
                this.decayHFRatio = java.lang.Short.parseShort(stringTokenizer.nextToken());
                java.lang.String nextToken6 = stringTokenizer.nextToken();
                if (!nextToken6.equals("reflectionsLevel")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken6);
                }
                this.reflectionsLevel = java.lang.Short.parseShort(stringTokenizer.nextToken());
                java.lang.String nextToken7 = stringTokenizer.nextToken();
                if (!nextToken7.equals("reflectionsDelay")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken7);
                }
                this.reflectionsDelay = java.lang.Integer.parseInt(stringTokenizer.nextToken());
                java.lang.String nextToken8 = stringTokenizer.nextToken();
                if (!nextToken8.equals("reverbLevel")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken8);
                }
                this.reverbLevel = java.lang.Short.parseShort(stringTokenizer.nextToken());
                java.lang.String nextToken9 = stringTokenizer.nextToken();
                if (!nextToken9.equals("reverbDelay")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken9);
                }
                this.reverbDelay = java.lang.Integer.parseInt(stringTokenizer.nextToken());
                java.lang.String nextToken10 = stringTokenizer.nextToken();
                if (!nextToken10.equals("diffusion")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken10);
                }
                this.diffusion = java.lang.Short.parseShort(stringTokenizer.nextToken());
                java.lang.String nextToken11 = stringTokenizer.nextToken();
                if (!nextToken11.equals("density")) {
                    throw new java.lang.IllegalArgumentException("invalid key name: " + nextToken11);
                }
                this.density = java.lang.Short.parseShort(stringTokenizer.nextToken());
            } catch (java.lang.NumberFormatException e) {
                throw new java.lang.IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public java.lang.String toString() {
            return new java.lang.String("EnvironmentalReverb;roomLevel=" + java.lang.Short.toString(this.roomLevel) + ";roomHFLevel=" + java.lang.Short.toString(this.roomHFLevel) + ";decayTime=" + java.lang.Integer.toString(this.decayTime) + ";decayHFRatio=" + java.lang.Short.toString(this.decayHFRatio) + ";reflectionsLevel=" + java.lang.Short.toString(this.reflectionsLevel) + ";reflectionsDelay=" + java.lang.Integer.toString(this.reflectionsDelay) + ";reverbLevel=" + java.lang.Short.toString(this.reverbLevel) + ";reverbDelay=" + java.lang.Integer.toString(this.reverbDelay) + ";diffusion=" + java.lang.Short.toString(this.diffusion) + ";density=" + java.lang.Short.toString(this.density));
        }
    }

    public android.media.audiofx.EnvironmentalReverb.Settings getProperties() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        byte[] bArr = new byte[PROPERTY_SIZE];
        checkStatus(getParameter(10, bArr));
        android.media.audiofx.EnvironmentalReverb.Settings settings = new android.media.audiofx.EnvironmentalReverb.Settings();
        settings.roomLevel = byteArrayToShort(bArr, 0);
        settings.roomHFLevel = byteArrayToShort(bArr, 2);
        settings.decayTime = byteArrayToInt(bArr, 4);
        settings.decayHFRatio = byteArrayToShort(bArr, 8);
        settings.reflectionsLevel = byteArrayToShort(bArr, 10);
        settings.reflectionsDelay = byteArrayToInt(bArr, 12);
        settings.reverbLevel = byteArrayToShort(bArr, 16);
        settings.reverbDelay = byteArrayToInt(bArr, 18);
        settings.diffusion = byteArrayToShort(bArr, 22);
        settings.density = byteArrayToShort(bArr, 24);
        return settings;
    }

    public void setProperties(android.media.audiofx.EnvironmentalReverb.Settings settings) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(10, concatArrays(shortToByteArray(settings.roomLevel), shortToByteArray(settings.roomHFLevel), intToByteArray(settings.decayTime), shortToByteArray(settings.decayHFRatio), shortToByteArray(settings.reflectionsLevel), intToByteArray(settings.reflectionsDelay), shortToByteArray(settings.reverbLevel), intToByteArray(settings.reverbDelay), shortToByteArray(settings.diffusion), shortToByteArray(settings.density))));
    }
}
