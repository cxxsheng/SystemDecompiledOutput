package android.media;

/* loaded from: classes2.dex */
public class AudioPort {
    public static final int ROLE_NONE = 0;
    public static final int ROLE_SINK = 2;
    public static final int ROLE_SOURCE = 1;
    private static final java.lang.String TAG = "AudioPort";
    public static final int TYPE_DEVICE = 1;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_SESSION = 3;
    public static final int TYPE_SUBMIX = 2;
    private android.media.AudioPortConfig mActiveConfig;
    private final int[] mChannelIndexMasks;
    private final int[] mChannelMasks;
    private final java.util.List<android.media.AudioDescriptor> mDescriptors;
    private final int[] mFormats;
    private final android.media.AudioGain[] mGains;
    android.media.AudioHandle mHandle;
    private final java.lang.String mName;
    private final java.util.List<android.media.AudioProfile> mProfiles;
    protected final int mRole;
    private final int[] mSamplingRates;

    AudioPort(android.media.AudioHandle audioHandle, int i, java.lang.String str, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, android.media.AudioGain[] audioGainArr) {
        this.mHandle = audioHandle;
        this.mRole = i;
        this.mName = str;
        this.mSamplingRates = iArr;
        this.mChannelMasks = iArr2;
        this.mChannelIndexMasks = iArr3;
        this.mFormats = iArr4;
        this.mGains = audioGainArr;
        this.mProfiles = new java.util.ArrayList();
        if (this.mFormats != null) {
            for (int i2 : this.mFormats) {
                this.mProfiles.add(new android.media.AudioProfile(i2, iArr, iArr2, iArr3, 0));
            }
        }
        this.mDescriptors = new java.util.ArrayList();
    }

    AudioPort(android.media.AudioHandle audioHandle, int i, java.lang.String str, java.util.List<android.media.AudioProfile> list, android.media.AudioGain[] audioGainArr, java.util.List<android.media.AudioDescriptor> list2) {
        this.mHandle = audioHandle;
        this.mRole = i;
        this.mName = str;
        this.mProfiles = list;
        this.mDescriptors = list2;
        this.mGains = audioGainArr;
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.HashSet hashSet2 = new java.util.HashSet();
        java.util.HashSet hashSet3 = new java.util.HashSet();
        java.util.HashSet hashSet4 = new java.util.HashSet();
        for (android.media.AudioProfile audioProfile : list) {
            hashSet.add(java.lang.Integer.valueOf(audioProfile.getFormat()));
            hashSet2.addAll((java.util.Collection) java.util.Arrays.stream(audioProfile.getSampleRates()).boxed().collect(java.util.stream.Collectors.toList()));
            hashSet3.addAll((java.util.Collection) java.util.Arrays.stream(audioProfile.getChannelMasks()).boxed().collect(java.util.stream.Collectors.toList()));
            hashSet4.addAll((java.util.Collection) java.util.Arrays.stream(audioProfile.getChannelIndexMasks()).boxed().collect(java.util.stream.Collectors.toList()));
        }
        this.mSamplingRates = hashSet2.stream().mapToInt(new android.media.AudioPort$$ExternalSyntheticLambda0()).toArray();
        this.mChannelMasks = hashSet3.stream().mapToInt(new android.media.AudioPort$$ExternalSyntheticLambda0()).toArray();
        this.mChannelIndexMasks = hashSet4.stream().mapToInt(new android.media.AudioPort$$ExternalSyntheticLambda0()).toArray();
        this.mFormats = hashSet.stream().mapToInt(new android.media.AudioPort$$ExternalSyntheticLambda0()).toArray();
    }

    android.media.AudioHandle handle() {
        return this.mHandle;
    }

    public int id() {
        return this.mHandle.id();
    }

    public int role() {
        return this.mRole;
    }

    public java.lang.String name() {
        return this.mName;
    }

    public int[] samplingRates() {
        return this.mSamplingRates;
    }

    public int[] channelMasks() {
        return this.mChannelMasks;
    }

    public int[] channelIndexMasks() {
        return this.mChannelIndexMasks;
    }

    public int[] formats() {
        return this.mFormats;
    }

    public java.util.List<android.media.AudioProfile> profiles() {
        return this.mProfiles;
    }

    public java.util.List<android.media.AudioDescriptor> audioDescriptors() {
        return this.mDescriptors;
    }

    public android.media.AudioGain[] gains() {
        return this.mGains;
    }

    android.media.AudioGain gain(int i) {
        if (i < 0 || i >= this.mGains.length) {
            return null;
        }
        return this.mGains[i];
    }

    public android.media.AudioPortConfig buildConfig(int i, int i2, int i3, android.media.AudioGainConfig audioGainConfig) {
        return new android.media.AudioPortConfig(this, i, i2, i3, audioGainConfig);
    }

    public android.media.AudioPortConfig activeConfig() {
        return this.mActiveConfig;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.media.AudioPort)) {
            return false;
        }
        return this.mHandle.equals(((android.media.AudioPort) obj).handle());
    }

    public int hashCode() {
        return this.mHandle.hashCode();
    }

    public java.lang.String toString() {
        java.lang.String num = java.lang.Integer.toString(this.mRole);
        switch (this.mRole) {
            case 0:
                num = android.security.keystore.KeyProperties.DIGEST_NONE;
                break;
            case 1:
                num = "SOURCE";
                break;
            case 2:
                num = "SINK";
                break;
        }
        return "{mHandle: " + this.mHandle + ", mRole: " + num + "}";
    }
}
