package android.media.audiofx;

/* loaded from: classes2.dex */
public class Virtualizer extends android.media.audiofx.AudioEffect {
    private static final boolean DEBUG = false;
    public static final int PARAM_FORCE_VIRTUALIZATION_MODE = 3;
    public static final int PARAM_STRENGTH = 1;
    public static final int PARAM_STRENGTH_SUPPORTED = 0;
    public static final int PARAM_VIRTUALIZATION_MODE = 4;
    public static final int PARAM_VIRTUAL_SPEAKER_ANGLES = 2;
    private static final java.lang.String TAG = "Virtualizer";
    public static final int VIRTUALIZATION_MODE_AUTO = 1;
    public static final int VIRTUALIZATION_MODE_BINAURAL = 2;
    public static final int VIRTUALIZATION_MODE_OFF = 0;
    public static final int VIRTUALIZATION_MODE_TRANSAURAL = 3;
    private android.media.audiofx.Virtualizer.BaseParameterListener mBaseParamListener;
    private android.media.audiofx.Virtualizer.OnParameterChangeListener mParamListener;
    private final java.lang.Object mParamListenerLock;
    private boolean mStrengthSupported;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ForceVirtualizationMode {
    }

    public interface OnParameterChangeListener {
        void onParameterChange(android.media.audiofx.Virtualizer virtualizer, int i, int i2, short s);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VirtualizationMode {
    }

    public Virtualizer(int i, int i2) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        super(EFFECT_TYPE_VIRTUALIZER, EFFECT_TYPE_NULL, i, i2);
        this.mStrengthSupported = false;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new java.lang.Object();
        if (i2 == 0) {
            android.util.Log.w(TAG, "WARNING: attaching a Virtualizer to global output mix is deprecated!");
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

    private boolean getAnglesInt(int i, int i2, int[] iArr) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        int i3;
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Virtualizer: illegal CHANNEL_INVALID channel mask");
        }
        if (i == 1) {
            i = 12;
        }
        int channelCountFromOutChannelMask = android.media.AudioFormat.channelCountFromOutChannelMask(i);
        if (iArr != null && iArr.length < (i3 = channelCountFromOutChannelMask * 3)) {
            android.util.Log.e(TAG, "Size of array for angles cannot accomodate number of channels in mask (" + channelCountFromOutChannelMask + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            throw new java.lang.IllegalArgumentException("Virtualizer: array for channel / angle pairs is too small: is " + iArr.length + ", should be " + i3);
        }
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(12);
        allocate.order(java.nio.ByteOrder.nativeOrder());
        allocate.putInt(2);
        allocate.putInt(android.media.AudioFormat.convertChannelOutMaskToNativeMask(i));
        allocate.putInt(android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(i2));
        byte[] bArr = new byte[channelCountFromOutChannelMask * 4 * 3];
        int parameter = getParameter(allocate.array(), bArr);
        if (parameter >= 0) {
            if (iArr != null) {
                java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
                wrap.order(java.nio.ByteOrder.nativeOrder());
                for (int i4 = 0; i4 < channelCountFromOutChannelMask; i4++) {
                    int i5 = i4 * 3;
                    int i6 = i4 * 4 * 3;
                    iArr[i5] = android.media.AudioFormat.convertNativeChannelMaskToOutMask(wrap.getInt(i6));
                    iArr[i5 + 1] = wrap.getInt(i6 + 4);
                    iArr[i5 + 2] = wrap.getInt(i6 + 8);
                }
            }
            return true;
        }
        if (parameter == -4) {
            return false;
        }
        checkStatus(parameter);
        android.util.Log.e(TAG, "unexpected status code " + parameter + " after getParameter(PARAM_VIRTUAL_SPEAKER_ANGLES)");
        return false;
    }

    private static int getDeviceForModeQuery(int i) throws java.lang.IllegalArgumentException {
        switch (i) {
            case 2:
                return 4;
            case 3:
                return 2;
            default:
                throw new java.lang.IllegalArgumentException("Virtualizer: illegal virtualization mode " + i);
        }
    }

    private static int getDeviceForModeForce(int i) throws java.lang.IllegalArgumentException {
        if (i == 1) {
            return 0;
        }
        return getDeviceForModeQuery(i);
    }

    private static int deviceToMode(int i) {
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 7:
            case 22:
                return 2;
            case 2:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 19:
                return 3;
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            default:
                return 0;
        }
    }

    public boolean canVirtualize(int i, int i2) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        return getAnglesInt(i, getDeviceForModeQuery(i2), null);
    }

    public boolean getSpeakerAngles(int i, int i2, int[] iArr) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        if (iArr == null) {
            throw new java.lang.IllegalArgumentException("Virtualizer: illegal null channel / angle array");
        }
        return getAnglesInt(i, getDeviceForModeQuery(i2), iArr);
    }

    public boolean forceVirtualizationMode(int i) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        int parameter = setParameter(3, android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(getDeviceForModeForce(i)));
        if (parameter >= 0) {
            return true;
        }
        if (parameter == -4) {
            return false;
        }
        checkStatus(parameter);
        android.util.Log.e(TAG, "unexpected status code " + parameter + " after setParameter(PARAM_FORCE_VIRTUALIZATION_MODE)");
        return false;
    }

    public int getVirtualizationMode() throws java.lang.IllegalStateException, java.lang.UnsupportedOperationException {
        int[] iArr = new int[1];
        int parameter = getParameter(4, iArr);
        if (parameter >= 0) {
            return deviceToMode(android.media.AudioDeviceInfo.convertInternalDeviceToDeviceType(iArr[0]));
        }
        if (parameter == -4) {
            return 0;
        }
        checkStatus(parameter);
        android.util.Log.e(TAG, "unexpected status code " + parameter + " after getParameter(PARAM_VIRTUALIZATION_MODE)");
        return 0;
    }

    private class BaseParameterListener implements android.media.audiofx.AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(android.media.audiofx.AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            android.media.audiofx.Virtualizer.OnParameterChangeListener onParameterChangeListener;
            int i2;
            short s;
            synchronized (android.media.audiofx.Virtualizer.this.mParamListenerLock) {
                if (android.media.audiofx.Virtualizer.this.mParamListener == null) {
                    onParameterChangeListener = null;
                } else {
                    onParameterChangeListener = android.media.audiofx.Virtualizer.this.mParamListener;
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
                    onParameterChangeListener.onParameterChange(android.media.audiofx.Virtualizer.this, i, i2, s);
                }
            }
        }
    }

    public void setParameterListener(android.media.audiofx.Virtualizer.OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                this.mParamListener = onParameterChangeListener;
                this.mBaseParamListener = new android.media.audiofx.Virtualizer.BaseParameterListener();
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
            if (!nextToken.equals(android.media.audiofx.Virtualizer.TAG)) {
                throw new java.lang.IllegalArgumentException("invalid settings for Virtualizer: " + nextToken);
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
            return new java.lang.String("Virtualizer;strength=" + java.lang.Short.toString(this.strength));
        }
    }

    public android.media.audiofx.Virtualizer.Settings getProperties() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        android.media.audiofx.Virtualizer.Settings settings = new android.media.audiofx.Virtualizer.Settings();
        short[] sArr = new short[1];
        checkStatus(getParameter(1, sArr));
        settings.strength = sArr[0];
        return settings;
    }

    public void setProperties(android.media.audiofx.Virtualizer.Settings settings) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
        checkStatus(setParameter(1, settings.strength));
    }
}
