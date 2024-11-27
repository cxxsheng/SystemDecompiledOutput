package android.media;

/* loaded from: classes2.dex */
public final class MediaCodecList {
    public static final int ALL_CODECS = 1;
    public static final int REGULAR_CODECS = 0;
    private static final java.lang.String TAG = "MediaCodecList";
    private static android.media.MediaCodecInfo[] sAllCodecInfos;
    private static java.util.Map<java.lang.String, java.lang.Object> sGlobalSettings;
    private static java.lang.Object sInitLock = new java.lang.Object();
    private static android.media.MediaCodecInfo[] sRegularCodecInfos;
    private android.media.MediaCodecInfo[] mCodecInfos;

    static final native int findCodecByName(java.lang.String str);

    static final native int getAttributes(int i);

    static final native java.lang.String getCanonicalName(int i);

    static final native android.media.MediaCodecInfo.CodecCapabilities getCodecCapabilities(int i, java.lang.String str);

    static final native java.lang.String getCodecName(int i);

    static final native java.lang.String[] getSupportedTypes(int i);

    private static final native int native_getCodecCount();

    static final native java.util.Map<java.lang.String, java.lang.Object> native_getGlobalSettings();

    private static final native void native_init();

    public static final int getCodecCount() {
        initCodecList();
        return sRegularCodecInfos.length;
    }

    public static final android.media.MediaCodecInfo getCodecInfoAt(int i) {
        initCodecList();
        if (i < 0 || i > sRegularCodecInfos.length) {
            throw new java.lang.IllegalArgumentException();
        }
        return sRegularCodecInfos[i];
    }

    static final java.util.Map<java.lang.String, java.lang.Object> getGlobalSettings() {
        synchronized (sInitLock) {
            if (sGlobalSettings == null) {
                sGlobalSettings = native_getGlobalSettings();
            }
        }
        return sGlobalSettings;
    }

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }

    private static final void initCodecList() {
        synchronized (sInitLock) {
            if (sRegularCodecInfos == null) {
                int native_getCodecCount = native_getCodecCount();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                for (int i = 0; i < native_getCodecCount; i++) {
                    try {
                        android.media.MediaCodecInfo newCodecInfoAt = getNewCodecInfoAt(i);
                        arrayList2.add(newCodecInfoAt);
                        android.media.MediaCodecInfo makeRegular = newCodecInfoAt.makeRegular();
                        if (makeRegular != null) {
                            arrayList.add(makeRegular);
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(TAG, "Could not get codec capabilities", e);
                    }
                }
                sRegularCodecInfos = (android.media.MediaCodecInfo[]) arrayList.toArray(new android.media.MediaCodecInfo[arrayList.size()]);
                sAllCodecInfos = (android.media.MediaCodecInfo[]) arrayList2.toArray(new android.media.MediaCodecInfo[arrayList2.size()]);
            }
        }
    }

    private static android.media.MediaCodecInfo getNewCodecInfoAt(int i) {
        java.lang.String[] supportedTypes = getSupportedTypes(i);
        android.media.MediaCodecInfo.CodecCapabilities[] codecCapabilitiesArr = new android.media.MediaCodecInfo.CodecCapabilities[supportedTypes.length];
        int length = supportedTypes.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            codecCapabilitiesArr[i3] = getCodecCapabilities(i, supportedTypes[i2]);
            i2++;
            i3++;
        }
        return new android.media.MediaCodecInfo(getCodecName(i), getCanonicalName(i), getAttributes(i), codecCapabilitiesArr);
    }

    public static android.media.MediaCodecInfo getInfoFor(java.lang.String str) {
        initCodecList();
        return sAllCodecInfos[findCodecByName(str)];
    }

    private MediaCodecList() {
        this(0);
    }

    public MediaCodecList(int i) {
        initCodecList();
        if (i == 0) {
            this.mCodecInfos = sRegularCodecInfos;
        } else {
            this.mCodecInfos = sAllCodecInfos;
        }
    }

    public final android.media.MediaCodecInfo[] getCodecInfos() {
        return (android.media.MediaCodecInfo[]) java.util.Arrays.copyOf(this.mCodecInfos, this.mCodecInfos.length);
    }

    public final java.lang.String findDecoderForFormat(android.media.MediaFormat mediaFormat) {
        return findCodecForFormat(false, mediaFormat);
    }

    public final java.lang.String findEncoderForFormat(android.media.MediaFormat mediaFormat) {
        return findCodecForFormat(true, mediaFormat);
    }

    private java.lang.String findCodecForFormat(boolean z, android.media.MediaFormat mediaFormat) {
        java.lang.String string = mediaFormat.getString(android.media.MediaFormat.KEY_MIME);
        for (android.media.MediaCodecInfo mediaCodecInfo : this.mCodecInfos) {
            if (mediaCodecInfo.isEncoder() == z) {
                try {
                    android.media.MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(string);
                    if (capabilitiesForType != null && capabilitiesForType.isFormatSupported(mediaFormat)) {
                        return mediaCodecInfo.getName();
                    }
                } catch (java.lang.IllegalArgumentException e) {
                }
            }
        }
        return null;
    }
}
