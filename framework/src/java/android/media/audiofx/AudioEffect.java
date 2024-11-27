package android.media.audiofx;

/* loaded from: classes2.dex */
public class AudioEffect {
    public static final java.lang.String ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION = "android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION";
    public static final java.lang.String ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL = "android.media.action.DISPLAY_AUDIO_EFFECT_CONTROL_PANEL";
    public static final java.lang.String ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION = "android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION";
    public static final int ALREADY_EXISTS = -2;
    public static final int CONTENT_TYPE_GAME = 2;
    public static final int CONTENT_TYPE_MOVIE = 1;
    public static final int CONTENT_TYPE_MUSIC = 0;
    public static final int CONTENT_TYPE_VOICE = 3;
    public static final java.lang.String EFFECT_AUXILIARY = "Auxiliary";
    public static final java.lang.String EFFECT_INSERT = "Insert";
    public static final java.lang.String EFFECT_POST_PROCESSING = "Post Processing";
    public static final java.lang.String EFFECT_PRE_PROCESSING = "Pre Processing";
    public static final java.util.UUID EFFECT_TYPE_AEC;
    public static final java.util.UUID EFFECT_TYPE_AGC;
    public static final java.util.UUID EFFECT_TYPE_BASS_BOOST;
    public static final java.util.UUID EFFECT_TYPE_DYNAMICS_PROCESSING;
    public static final java.util.UUID EFFECT_TYPE_ENV_REVERB;
    public static final java.util.UUID EFFECT_TYPE_EQUALIZER;
    public static final java.util.UUID EFFECT_TYPE_HAPTIC_GENERATOR;
    public static final java.util.UUID EFFECT_TYPE_LOUDNESS_ENHANCER;
    public static final java.util.UUID EFFECT_TYPE_NS;
    public static final java.util.UUID EFFECT_TYPE_NULL;
    public static final java.util.UUID EFFECT_TYPE_PRESET_REVERB;
    public static final java.util.UUID EFFECT_TYPE_VIRTUALIZER;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -4;
    public static final int ERROR_DEAD_OBJECT = -7;
    public static final int ERROR_INVALID_OPERATION = -5;
    public static final int ERROR_NO_INIT = -3;
    public static final int ERROR_NO_MEMORY = -6;
    public static final java.lang.String EXTRA_AUDIO_SESSION = "android.media.extra.AUDIO_SESSION";
    public static final java.lang.String EXTRA_CONTENT_TYPE = "android.media.extra.CONTENT_TYPE";
    public static final java.lang.String EXTRA_PACKAGE_NAME = "android.media.extra.PACKAGE_NAME";
    public static final int NATIVE_EVENT_CONTROL_STATUS = 0;
    public static final int NATIVE_EVENT_ENABLED_STATUS = 1;
    public static final int NATIVE_EVENT_PARAMETER_CHANGED = 2;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_UNINITIALIZED = 0;
    public static final int SUCCESS = 0;
    private static final java.lang.String TAG = "AudioEffect-JAVA";
    private android.media.audiofx.AudioEffect.OnControlStatusChangeListener mControlChangeStatusListener;
    private android.media.audiofx.AudioEffect.Descriptor mDescriptor;
    private android.media.audiofx.AudioEffect.OnEnableStatusChangeListener mEnableStatusChangeListener;
    private int mId;
    private long mJniData;
    public final java.lang.Object mListenerLock;
    private long mNativeAudioEffect;
    public android.media.audiofx.AudioEffect.NativeEventHandler mNativeEventHandler;
    private android.media.audiofx.AudioEffect.OnParameterChangeListener mParameterChangeListener;
    private int mState;
    private final java.lang.Object mStateLock;

    public interface OnControlStatusChangeListener {
        void onControlStatusChange(android.media.audiofx.AudioEffect audioEffect, boolean z);
    }

    public interface OnEnableStatusChangeListener {
        void onEnableStatusChange(android.media.audiofx.AudioEffect audioEffect, boolean z);
    }

    public interface OnParameterChangeListener {
        void onParameterChange(android.media.audiofx.AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2);
    }

    private final native int native_command(int i, int i2, byte[] bArr, int i3, byte[] bArr2);

    private final native void native_finalize();

    private final native boolean native_getEnabled();

    private final native int native_getParameter(int i, byte[] bArr, int i2, byte[] bArr2);

    private final native boolean native_hasControl();

    private static final native void native_init();

    private static native java.lang.Object[] native_query_effects();

    private static native java.lang.Object[] native_query_pre_processing(int i);

    private final native void native_release();

    private final native int native_setEnabled(boolean z);

    private final native int native_setParameter(int i, byte[] bArr, int i2, byte[] bArr2);

    private final native int native_setup(java.lang.Object obj, java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3, int[] iArr, java.lang.Object[] objArr, android.os.Parcel parcel, boolean z);

    static {
        java.lang.System.loadLibrary("audioeffect_jni");
        native_init();
        EFFECT_TYPE_ENV_REVERB = java.util.UUID.fromString("c2e5d5f0-94bd-4763-9cac-4e234d06839e");
        EFFECT_TYPE_PRESET_REVERB = java.util.UUID.fromString("47382d60-ddd8-11db-bf3a-0002a5d5c51b");
        EFFECT_TYPE_EQUALIZER = java.util.UUID.fromString("0bed4300-ddd6-11db-8f34-0002a5d5c51b");
        EFFECT_TYPE_BASS_BOOST = java.util.UUID.fromString("0634f220-ddd4-11db-a0fc-0002a5d5c51b");
        EFFECT_TYPE_VIRTUALIZER = java.util.UUID.fromString("37cc2c00-dddd-11db-8577-0002a5d5c51b");
        EFFECT_TYPE_AGC = java.util.UUID.fromString("0a8abfe0-654c-11e0-ba26-0002a5d5c51b");
        EFFECT_TYPE_AEC = java.util.UUID.fromString("7b491460-8d4d-11e0-bd61-0002a5d5c51b");
        EFFECT_TYPE_NS = java.util.UUID.fromString("58b4b260-8e06-11e0-aa8e-0002a5d5c51b");
        EFFECT_TYPE_LOUDNESS_ENHANCER = java.util.UUID.fromString("fe3199be-aed0-413f-87bb-11260eb63cf1");
        EFFECT_TYPE_DYNAMICS_PROCESSING = java.util.UUID.fromString("7261676f-6d75-7369-6364-28e2fd3ac39e");
        EFFECT_TYPE_HAPTIC_GENERATOR = java.util.UUID.fromString("1411e6d6-aecd-4021-a1cf-a6aceb0d71e5");
        EFFECT_TYPE_NULL = java.util.UUID.fromString("ec7178ec-e5e1-4432-a3f4-4657e6795210");
    }

    public static class Descriptor {
        public java.lang.String connectMode;
        public java.lang.String implementor;
        public java.lang.String name;
        public java.util.UUID type;
        public java.util.UUID uuid;

        public Descriptor() {
        }

        public Descriptor(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
            this.type = java.util.UUID.fromString(str);
            this.uuid = java.util.UUID.fromString(str2);
            this.connectMode = str3;
            this.name = str4;
            this.implementor = str5;
        }

        public Descriptor(android.os.Parcel parcel) {
            this.type = java.util.UUID.fromString(parcel.readString());
            this.uuid = java.util.UUID.fromString(parcel.readString());
            this.connectMode = parcel.readString();
            this.name = parcel.readString();
            this.implementor = parcel.readString();
        }

        public int hashCode() {
            return java.util.Objects.hash(this.type, this.uuid, this.connectMode, this.name, this.implementor);
        }

        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeString(this.type.toString());
            parcel.writeString(this.uuid.toString());
            parcel.writeString(this.connectMode);
            parcel.writeString(this.name);
            parcel.writeString(this.implementor);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof android.media.audiofx.AudioEffect.Descriptor)) {
                return false;
            }
            android.media.audiofx.AudioEffect.Descriptor descriptor = (android.media.audiofx.AudioEffect.Descriptor) obj;
            if (this.type.equals(descriptor.type) && this.uuid.equals(descriptor.uuid) && this.connectMode.equals(descriptor.connectMode) && this.name.equals(descriptor.name) && this.implementor.equals(descriptor.implementor)) {
                return true;
            }
            return false;
        }
    }

    public AudioEffect(java.util.UUID uuid, java.util.UUID uuid2, int i, int i2) throws java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        this(uuid, uuid2, i, i2, null);
    }

    @android.annotation.SystemApi
    public AudioEffect(java.util.UUID uuid, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        this(EFFECT_TYPE_NULL, (java.util.UUID) java.util.Objects.requireNonNull(uuid), 0, -2, (android.media.AudioDeviceAttributes) java.util.Objects.requireNonNull(audioDeviceAttributes));
    }

    private AudioEffect(java.util.UUID uuid, java.util.UUID uuid2, int i, int i2, android.media.AudioDeviceAttributes audioDeviceAttributes) throws java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        this(uuid, uuid2, i, i2, audioDeviceAttributes, false);
    }

    private AudioEffect(java.util.UUID uuid, java.util.UUID uuid2, int i, int i2, android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) throws java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException {
        java.lang.String str;
        int i3;
        int convertDeviceTypeToInternalInputDevice;
        this.mState = 0;
        this.mStateLock = new java.lang.Object();
        this.mEnableStatusChangeListener = null;
        this.mControlChangeStatusListener = null;
        this.mParameterChangeListener = null;
        this.mListenerLock = new java.lang.Object();
        this.mNativeEventHandler = null;
        int[] iArr = new int[1];
        android.media.audiofx.AudioEffect.Descriptor[] descriptorArr = new android.media.audiofx.AudioEffect.Descriptor[1];
        if (audioDeviceAttributes == null) {
            str = "";
            i3 = 0;
        } else {
            if (audioDeviceAttributes.getRole() == 2) {
                convertDeviceTypeToInternalInputDevice = android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(audioDeviceAttributes.getType());
            } else {
                convertDeviceTypeToInternalInputDevice = android.media.AudioDeviceInfo.convertDeviceTypeToInternalInputDevice(audioDeviceAttributes.getType(), audioDeviceAttributes.getAddress());
            }
            i3 = convertDeviceTypeToInternalInputDevice;
            str = audioDeviceAttributes.getAddress();
        }
        android.content.AttributionSource.ScopedParcelState asScopedParcelState = android.content.AttributionSource.myAttributionSource().asScopedParcelState();
        try {
            int native_setup = native_setup(new java.lang.ref.WeakReference(this), uuid.toString(), uuid2.toString(), i, i2, i3, str, iArr, descriptorArr, asScopedParcelState.getParcel(), z);
            if (asScopedParcelState != null) {
                asScopedParcelState.close();
            }
            if (native_setup == 0 || native_setup == -2) {
                this.mId = iArr[0];
                this.mDescriptor = descriptorArr[0];
                if (!z) {
                    synchronized (this.mStateLock) {
                        this.mState = 1;
                    }
                    return;
                }
                return;
            }
            android.util.Log.e(TAG, "Error code " + native_setup + " when initializing AudioEffect.");
            switch (native_setup) {
                case -5:
                    throw new java.lang.UnsupportedOperationException("Effect library not loaded");
                case -4:
                    throw new java.lang.IllegalArgumentException("Effect type: " + uuid + " not supported.");
                default:
                    throw new java.lang.RuntimeException("Cannot initialize effect engine for type: " + uuid + " Error: " + native_setup);
            }
        } finally {
        }
    }

    @android.annotation.SystemApi
    public static boolean isEffectSupportedForDevice(java.util.UUID uuid, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        try {
            new android.media.audiofx.AudioEffect(EFFECT_TYPE_NULL, (java.util.UUID) java.util.Objects.requireNonNull(uuid), 0, -2, (android.media.AudioDeviceAttributes) java.util.Objects.requireNonNull(audioDeviceAttributes), true).release();
            return true;
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    public void release() {
        synchronized (this.mStateLock) {
            native_release();
            this.mState = 0;
        }
    }

    protected void finalize() {
        native_finalize();
    }

    public android.media.audiofx.AudioEffect.Descriptor getDescriptor() throws java.lang.IllegalStateException {
        checkState("getDescriptor()");
        return this.mDescriptor;
    }

    public static android.media.audiofx.AudioEffect.Descriptor[] queryEffects() {
        return (android.media.audiofx.AudioEffect.Descriptor[]) native_query_effects();
    }

    public static android.media.audiofx.AudioEffect.Descriptor[] queryPreProcessings(int i) {
        return (android.media.audiofx.AudioEffect.Descriptor[]) native_query_pre_processing(i);
    }

    public static boolean isEffectTypeAvailable(java.util.UUID uuid) {
        android.media.audiofx.AudioEffect.Descriptor[] queryEffects = queryEffects();
        if (queryEffects == null) {
            return false;
        }
        for (android.media.audiofx.AudioEffect.Descriptor descriptor : queryEffects) {
            if (descriptor.type.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public int setEnabled(boolean z) throws java.lang.IllegalStateException {
        checkState("setEnabled()");
        return native_setEnabled(z);
    }

    public int setParameter(byte[] bArr, byte[] bArr2) throws java.lang.IllegalStateException {
        checkState("setParameter()");
        return native_setParameter(bArr.length, bArr, bArr2.length, bArr2);
    }

    public int setParameter(int i, int i2) throws java.lang.IllegalStateException {
        return setParameter(intToByteArray(i), intToByteArray(i2));
    }

    public int setParameter(int i, short s) throws java.lang.IllegalStateException {
        return setParameter(intToByteArray(i), shortToByteArray(s));
    }

    public int setParameter(int i, byte[] bArr) throws java.lang.IllegalStateException {
        return setParameter(intToByteArray(i), bArr);
    }

    public int setParameter(int[] iArr, int[] iArr2) throws java.lang.IllegalStateException {
        if (iArr.length > 2 || iArr2.length > 2) {
            return -4;
        }
        byte[] intToByteArray = intToByteArray(iArr[0]);
        if (iArr.length > 1) {
            intToByteArray = concatArrays(intToByteArray, intToByteArray(iArr[1]));
        }
        byte[] intToByteArray2 = intToByteArray(iArr2[0]);
        if (iArr2.length > 1) {
            intToByteArray2 = concatArrays(intToByteArray2, intToByteArray(iArr2[1]));
        }
        return setParameter(intToByteArray, intToByteArray2);
    }

    public int setParameter(int[] iArr, short[] sArr) throws java.lang.IllegalStateException {
        if (iArr.length > 2 || sArr.length > 2) {
            return -4;
        }
        byte[] intToByteArray = intToByteArray(iArr[0]);
        if (iArr.length > 1) {
            intToByteArray = concatArrays(intToByteArray, intToByteArray(iArr[1]));
        }
        byte[] shortToByteArray = shortToByteArray(sArr[0]);
        if (sArr.length > 1) {
            shortToByteArray = concatArrays(shortToByteArray, shortToByteArray(sArr[1]));
        }
        return setParameter(intToByteArray, shortToByteArray);
    }

    public int setParameter(int[] iArr, byte[] bArr) throws java.lang.IllegalStateException {
        if (iArr.length > 2) {
            return -4;
        }
        byte[] intToByteArray = intToByteArray(iArr[0]);
        if (iArr.length > 1) {
            intToByteArray = concatArrays(intToByteArray, intToByteArray(iArr[1]));
        }
        return setParameter(intToByteArray, bArr);
    }

    public int getParameter(byte[] bArr, byte[] bArr2) throws java.lang.IllegalStateException {
        checkState("getParameter()");
        return native_getParameter(bArr.length, bArr, bArr2.length, bArr2);
    }

    public int getParameter(int i, byte[] bArr) throws java.lang.IllegalStateException {
        return getParameter(intToByteArray(i), bArr);
    }

    public int getParameter(int i, int[] iArr) throws java.lang.IllegalStateException {
        if (iArr.length > 2) {
            return -4;
        }
        byte[] intToByteArray = intToByteArray(i);
        byte[] bArr = new byte[iArr.length * 4];
        int parameter = getParameter(intToByteArray, bArr);
        if (parameter == 4 || parameter == 8) {
            iArr[0] = byteArrayToInt(bArr);
            if (parameter == 8) {
                iArr[1] = byteArrayToInt(bArr, 4);
            }
            return parameter / 4;
        }
        return -1;
    }

    public int getParameter(int i, short[] sArr) throws java.lang.IllegalStateException {
        if (sArr.length > 2) {
            return -4;
        }
        byte[] intToByteArray = intToByteArray(i);
        byte[] bArr = new byte[sArr.length * 2];
        int parameter = getParameter(intToByteArray, bArr);
        if (parameter == 2 || parameter == 4) {
            sArr[0] = byteArrayToShort(bArr);
            if (parameter == 4) {
                sArr[1] = byteArrayToShort(bArr, 2);
            }
            return parameter / 2;
        }
        return -1;
    }

    public int getParameter(int[] iArr, int[] iArr2) throws java.lang.IllegalStateException {
        if (iArr.length > 2 || iArr2.length > 2) {
            return -4;
        }
        byte[] intToByteArray = intToByteArray(iArr[0]);
        if (iArr.length > 1) {
            intToByteArray = concatArrays(intToByteArray, intToByteArray(iArr[1]));
        }
        byte[] bArr = new byte[iArr2.length * 4];
        int parameter = getParameter(intToByteArray, bArr);
        if (parameter == 4 || parameter == 8) {
            iArr2[0] = byteArrayToInt(bArr);
            if (parameter == 8) {
                iArr2[1] = byteArrayToInt(bArr, 4);
            }
            return parameter / 4;
        }
        return -1;
    }

    public int getParameter(int[] iArr, short[] sArr) throws java.lang.IllegalStateException {
        if (iArr.length > 2 || sArr.length > 2) {
            return -4;
        }
        byte[] intToByteArray = intToByteArray(iArr[0]);
        if (iArr.length > 1) {
            intToByteArray = concatArrays(intToByteArray, intToByteArray(iArr[1]));
        }
        byte[] bArr = new byte[sArr.length * 2];
        int parameter = getParameter(intToByteArray, bArr);
        if (parameter == 2 || parameter == 4) {
            sArr[0] = byteArrayToShort(bArr);
            if (parameter == 4) {
                sArr[1] = byteArrayToShort(bArr, 2);
            }
            return parameter / 2;
        }
        return -1;
    }

    public int getParameter(int[] iArr, byte[] bArr) throws java.lang.IllegalStateException {
        if (iArr.length > 2) {
            return -4;
        }
        byte[] intToByteArray = intToByteArray(iArr[0]);
        if (iArr.length > 1) {
            intToByteArray = concatArrays(intToByteArray, intToByteArray(iArr[1]));
        }
        return getParameter(intToByteArray, bArr);
    }

    public int command(int i, byte[] bArr, byte[] bArr2) throws java.lang.IllegalStateException {
        checkState("command()");
        return native_command(i, bArr.length, bArr, bArr2.length, bArr2);
    }

    public int getId() throws java.lang.IllegalStateException {
        checkState("getId()");
        return this.mId;
    }

    public boolean getEnabled() throws java.lang.IllegalStateException {
        checkState("getEnabled()");
        return native_getEnabled();
    }

    public boolean hasControl() throws java.lang.IllegalStateException {
        checkState("hasControl()");
        return native_hasControl();
    }

    public void setEnableStatusListener(android.media.audiofx.AudioEffect.OnEnableStatusChangeListener onEnableStatusChangeListener) {
        synchronized (this.mListenerLock) {
            this.mEnableStatusChangeListener = onEnableStatusChangeListener;
        }
        if (onEnableStatusChangeListener != null && this.mNativeEventHandler == null) {
            createNativeEventHandler();
        }
    }

    public void setControlStatusListener(android.media.audiofx.AudioEffect.OnControlStatusChangeListener onControlStatusChangeListener) {
        synchronized (this.mListenerLock) {
            this.mControlChangeStatusListener = onControlStatusChangeListener;
        }
        if (onControlStatusChangeListener != null && this.mNativeEventHandler == null) {
            createNativeEventHandler();
        }
    }

    public void setParameterListener(android.media.audiofx.AudioEffect.OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mListenerLock) {
            this.mParameterChangeListener = onParameterChangeListener;
        }
        if (onParameterChangeListener != null && this.mNativeEventHandler == null) {
            createNativeEventHandler();
        }
    }

    private void createNativeEventHandler() {
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            this.mNativeEventHandler = new android.media.audiofx.AudioEffect.NativeEventHandler(this, myLooper);
            return;
        }
        android.os.Looper mainLooper = android.os.Looper.getMainLooper();
        if (mainLooper != null) {
            this.mNativeEventHandler = new android.media.audiofx.AudioEffect.NativeEventHandler(this, mainLooper);
        } else {
            this.mNativeEventHandler = null;
        }
    }

    private class NativeEventHandler extends android.os.Handler {
        private android.media.audiofx.AudioEffect mAudioEffect;

        public NativeEventHandler(android.media.audiofx.AudioEffect audioEffect, android.os.Looper looper) {
            super(looper);
            this.mAudioEffect = audioEffect;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.media.audiofx.AudioEffect.OnControlStatusChangeListener onControlStatusChangeListener;
            android.media.audiofx.AudioEffect.OnEnableStatusChangeListener onEnableStatusChangeListener;
            android.media.audiofx.AudioEffect.OnParameterChangeListener onParameterChangeListener;
            if (this.mAudioEffect == null) {
                return;
            }
            switch (message.what) {
                case 0:
                    synchronized (android.media.audiofx.AudioEffect.this.mListenerLock) {
                        onControlStatusChangeListener = this.mAudioEffect.mControlChangeStatusListener;
                    }
                    if (onControlStatusChangeListener != null) {
                        onControlStatusChangeListener.onControlStatusChange(this.mAudioEffect, message.arg1 != 0);
                        return;
                    }
                    return;
                case 1:
                    synchronized (android.media.audiofx.AudioEffect.this.mListenerLock) {
                        onEnableStatusChangeListener = this.mAudioEffect.mEnableStatusChangeListener;
                    }
                    if (onEnableStatusChangeListener != null) {
                        onEnableStatusChangeListener.onEnableStatusChange(this.mAudioEffect, message.arg1 != 0);
                        return;
                    }
                    return;
                case 2:
                    synchronized (android.media.audiofx.AudioEffect.this.mListenerLock) {
                        onParameterChangeListener = this.mAudioEffect.mParameterChangeListener;
                    }
                    if (onParameterChangeListener != null) {
                        int i = message.arg1;
                        byte[] bArr = (byte[]) message.obj;
                        int byteArrayToInt = android.media.audiofx.AudioEffect.byteArrayToInt(bArr, 0);
                        int byteArrayToInt2 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr, 4);
                        int byteArrayToInt3 = android.media.audiofx.AudioEffect.byteArrayToInt(bArr, 8);
                        byte[] bArr2 = new byte[byteArrayToInt2];
                        byte[] bArr3 = new byte[byteArrayToInt3];
                        java.lang.System.arraycopy(bArr, 12, bArr2, 0, byteArrayToInt2);
                        java.lang.System.arraycopy(bArr, i, bArr3, 0, byteArrayToInt3);
                        onParameterChangeListener.onParameterChange(this.mAudioEffect, byteArrayToInt, bArr2, bArr3);
                        return;
                    }
                    return;
                default:
                    android.util.Log.e(android.media.audiofx.AudioEffect.TAG, "handleMessage() Unknown event type: " + message.what);
                    return;
            }
        }
    }

    private static void postEventFromNative(java.lang.Object obj, int i, int i2, int i3, java.lang.Object obj2) {
        android.media.audiofx.AudioEffect audioEffect = (android.media.audiofx.AudioEffect) ((java.lang.ref.WeakReference) obj).get();
        if (audioEffect != null && audioEffect.mNativeEventHandler != null) {
            audioEffect.mNativeEventHandler.sendMessage(audioEffect.mNativeEventHandler.obtainMessage(i, i2, i3, obj2));
        }
    }

    public void checkState(java.lang.String str) throws java.lang.IllegalStateException {
        synchronized (this.mStateLock) {
            if (this.mState != 1) {
                throw new java.lang.IllegalStateException(str + " called on uninitialized AudioEffect.");
            }
        }
    }

    public void checkStatus(int i) {
        if (isError(i)) {
            switch (i) {
                case -5:
                    throw new java.lang.UnsupportedOperationException("AudioEffect: invalid parameter operation");
                case -4:
                    throw new java.lang.IllegalArgumentException("AudioEffect: bad parameter value");
                default:
                    throw new java.lang.RuntimeException("AudioEffect: set/get parameter error");
            }
        }
    }

    public static boolean isError(int i) {
        return i < 0;
    }

    public static int byteArrayToInt(byte[] bArr) {
        return byteArrayToInt(bArr, 0);
    }

    public static int byteArrayToInt(byte[] bArr, int i) {
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        wrap.order(java.nio.ByteOrder.nativeOrder());
        return wrap.getInt(i);
    }

    public static byte[] intToByteArray(int i) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(4);
        allocate.order(java.nio.ByteOrder.nativeOrder());
        allocate.putInt(i);
        return allocate.array();
    }

    public static short byteArrayToShort(byte[] bArr) {
        return byteArrayToShort(bArr, 0);
    }

    public static short byteArrayToShort(byte[] bArr, int i) {
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        wrap.order(java.nio.ByteOrder.nativeOrder());
        return wrap.getShort(i);
    }

    public static byte[] shortToByteArray(short s) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(2);
        allocate.order(java.nio.ByteOrder.nativeOrder());
        allocate.putShort(s);
        return allocate.array();
    }

    public static float byteArrayToFloat(byte[] bArr) {
        return byteArrayToFloat(bArr, 0);
    }

    public static float byteArrayToFloat(byte[] bArr, int i) {
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        wrap.order(java.nio.ByteOrder.nativeOrder());
        return wrap.getFloat(i);
    }

    public static byte[] floatToByteArray(float f) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(4);
        allocate.order(java.nio.ByteOrder.nativeOrder());
        allocate.putFloat(f);
        return allocate.array();
    }

    public static byte[] concatArrays(byte[]... bArr) {
        int i = 0;
        for (byte[] bArr2 : bArr) {
            i += bArr2.length;
        }
        byte[] bArr3 = new byte[i];
        int i2 = 0;
        for (byte[] bArr4 : bArr) {
            java.lang.System.arraycopy(bArr4, 0, bArr3, i2, bArr4.length);
            i2 += bArr4.length;
        }
        return bArr3;
    }
}
