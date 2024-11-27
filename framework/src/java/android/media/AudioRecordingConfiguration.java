package android.media;

/* loaded from: classes2.dex */
public final class AudioRecordingConfiguration implements android.os.Parcelable {
    private final android.media.audiofx.AudioEffect.Descriptor[] mClientEffects;
    private final android.media.AudioFormat mClientFormat;
    private final java.lang.String mClientPackageName;
    private final int mClientPortId;
    private final int mClientSessionId;
    private boolean mClientSilenced;
    private final int mClientSource;
    private final int mClientUid;
    private final android.media.audiofx.AudioEffect.Descriptor[] mDeviceEffects;
    private final android.media.AudioFormat mDeviceFormat;
    private final int mDeviceSource;
    private final int mPatchHandle;
    private static final java.lang.String TAG = new java.lang.String("AudioRecordingConfiguration");
    public static final android.os.Parcelable.Creator<android.media.AudioRecordingConfiguration> CREATOR = new android.os.Parcelable.Creator<android.media.AudioRecordingConfiguration>() { // from class: android.media.AudioRecordingConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioRecordingConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioRecordingConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioRecordingConfiguration[] newArray(int i) {
            return new android.media.AudioRecordingConfiguration[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioSource {
    }

    public AudioRecordingConfiguration(int i, int i2, int i3, android.media.AudioFormat audioFormat, android.media.AudioFormat audioFormat2, int i4, java.lang.String str, int i5, boolean z, int i6, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr2) {
        this.mClientUid = i;
        this.mClientSessionId = i2;
        this.mClientSource = i3;
        this.mClientFormat = audioFormat;
        this.mDeviceFormat = audioFormat2;
        this.mPatchHandle = i4;
        this.mClientPackageName = str;
        this.mClientPortId = i5;
        this.mClientSilenced = z;
        this.mDeviceSource = i6;
        this.mClientEffects = descriptorArr;
        this.mDeviceEffects = descriptorArr2;
    }

    public AudioRecordingConfiguration(int i, int i2, int i3, android.media.AudioFormat audioFormat, android.media.AudioFormat audioFormat2, int i4, java.lang.String str) {
        this(i, i2, i3, audioFormat, audioFormat2, i4, str, 0, false, 0, new android.media.audiofx.AudioEffect.Descriptor[0], new android.media.audiofx.AudioEffect.Descriptor[0]);
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("  " + toLogFriendlyString(this));
    }

    public static java.lang.String toLogFriendlyString(android.media.AudioRecordingConfiguration audioRecordingConfiguration) {
        java.lang.String str = new java.lang.String();
        for (android.media.audiofx.AudioEffect.Descriptor descriptor : audioRecordingConfiguration.mClientEffects) {
            str = str + "'" + descriptor.name + "' ";
        }
        java.lang.String str2 = new java.lang.String();
        for (android.media.audiofx.AudioEffect.Descriptor descriptor2 : audioRecordingConfiguration.mDeviceEffects) {
            str2 = str2 + "'" + descriptor2.name + "' ";
        }
        return new java.lang.String("session:" + audioRecordingConfiguration.mClientSessionId + " -- source client=" + android.media.MediaRecorder.toLogFriendlyAudioSource(audioRecordingConfiguration.mClientSource) + ", dev=" + audioRecordingConfiguration.mDeviceFormat.toLogFriendlyString() + " -- uid:" + audioRecordingConfiguration.mClientUid + " -- patch:" + audioRecordingConfiguration.mPatchHandle + " -- pack:" + audioRecordingConfiguration.mClientPackageName + " -- format client=" + audioRecordingConfiguration.mClientFormat.toLogFriendlyString() + ", dev=" + audioRecordingConfiguration.mDeviceFormat.toLogFriendlyString() + " -- silenced:" + audioRecordingConfiguration.mClientSilenced + " -- effects client=" + str + ", dev=" + str2);
    }

    public static android.media.AudioRecordingConfiguration anonymizedCopy(android.media.AudioRecordingConfiguration audioRecordingConfiguration) {
        return new android.media.AudioRecordingConfiguration(-1, audioRecordingConfiguration.mClientSessionId, audioRecordingConfiguration.mClientSource, audioRecordingConfiguration.mClientFormat, audioRecordingConfiguration.mDeviceFormat, audioRecordingConfiguration.mPatchHandle, "", audioRecordingConfiguration.mClientPortId, audioRecordingConfiguration.mClientSilenced, audioRecordingConfiguration.mDeviceSource, audioRecordingConfiguration.mClientEffects, audioRecordingConfiguration.mDeviceEffects);
    }

    public int getClientAudioSource() {
        return this.mClientSource;
    }

    public int getClientAudioSessionId() {
        return this.mClientSessionId;
    }

    public android.media.AudioFormat getFormat() {
        return this.mDeviceFormat;
    }

    public android.media.AudioFormat getClientFormat() {
        return this.mClientFormat;
    }

    public java.lang.String getClientPackageName() {
        return this.mClientPackageName;
    }

    @android.annotation.SystemApi
    public int getClientUid() {
        if (this.mClientUid == -1) {
            throw new java.lang.SecurityException("MODIFY_AUDIO_ROUTING permission is missing");
        }
        return this.mClientUid;
    }

    public android.media.AudioDeviceInfo getAudioDevice() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (android.media.AudioManager.listAudioPatches(arrayList) != 0) {
            android.util.Log.e(TAG, "Error retrieving list of audio patches");
            return null;
        }
        int i = 0;
        while (true) {
            if (i >= arrayList.size()) {
                break;
            }
            android.media.AudioPatch audioPatch = (android.media.AudioPatch) arrayList.get(i);
            if (audioPatch.id() != this.mPatchHandle) {
                i++;
            } else {
                android.media.AudioPortConfig[] sources = audioPatch.sources();
                if (sources != null && sources.length > 0) {
                    return android.media.AudioManager.getDeviceForPortId(sources[0].port().id(), 1);
                }
            }
        }
        android.util.Log.e(TAG, "Couldn't find device for recording, did recording end already?");
        return null;
    }

    public int getClientPortId() {
        return this.mClientPortId;
    }

    public boolean isClientSilenced() {
        return this.mClientSilenced;
    }

    public int getAudioSource() {
        return this.mDeviceSource;
    }

    public java.util.List<android.media.audiofx.AudioEffect.Descriptor> getClientEffects() {
        return new java.util.ArrayList(java.util.Arrays.asList(this.mClientEffects));
    }

    public java.util.List<android.media.audiofx.AudioEffect.Descriptor> getEffects() {
        return new java.util.ArrayList(java.util.Arrays.asList(this.mDeviceEffects));
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mClientSessionId), java.lang.Integer.valueOf(this.mClientSource));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mClientSessionId);
        parcel.writeInt(this.mClientSource);
        this.mClientFormat.writeToParcel(parcel, 0);
        this.mDeviceFormat.writeToParcel(parcel, 0);
        parcel.writeInt(this.mPatchHandle);
        parcel.writeString(this.mClientPackageName);
        parcel.writeInt(this.mClientUid);
        parcel.writeInt(this.mClientPortId);
        parcel.writeBoolean(this.mClientSilenced);
        parcel.writeInt(this.mDeviceSource);
        parcel.writeInt(this.mClientEffects.length);
        for (int i2 = 0; i2 < this.mClientEffects.length; i2++) {
            this.mClientEffects[i2].writeToParcel(parcel);
        }
        parcel.writeInt(this.mDeviceEffects.length);
        for (int i3 = 0; i3 < this.mDeviceEffects.length; i3++) {
            this.mDeviceEffects[i3].writeToParcel(parcel);
        }
    }

    private AudioRecordingConfiguration(android.os.Parcel parcel) {
        this.mClientSessionId = parcel.readInt();
        this.mClientSource = parcel.readInt();
        this.mClientFormat = android.media.AudioFormat.CREATOR.createFromParcel(parcel);
        this.mDeviceFormat = android.media.AudioFormat.CREATOR.createFromParcel(parcel);
        this.mPatchHandle = parcel.readInt();
        this.mClientPackageName = parcel.readString();
        this.mClientUid = parcel.readInt();
        this.mClientPortId = parcel.readInt();
        this.mClientSilenced = parcel.readBoolean();
        this.mDeviceSource = parcel.readInt();
        this.mClientEffects = new android.media.audiofx.AudioEffect.Descriptor[parcel.readInt()];
        for (int i = 0; i < this.mClientEffects.length; i++) {
            this.mClientEffects[i] = new android.media.audiofx.AudioEffect.Descriptor(parcel);
        }
        this.mDeviceEffects = new android.media.audiofx.AudioEffect.Descriptor[parcel.readInt()];
        for (int i2 = 0; i2 < this.mDeviceEffects.length; i2++) {
            this.mDeviceEffects[i2] = new android.media.audiofx.AudioEffect.Descriptor(parcel);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.AudioRecordingConfiguration)) {
            return false;
        }
        android.media.AudioRecordingConfiguration audioRecordingConfiguration = (android.media.AudioRecordingConfiguration) obj;
        if (this.mClientUid == audioRecordingConfiguration.mClientUid && this.mClientSessionId == audioRecordingConfiguration.mClientSessionId && this.mClientSource == audioRecordingConfiguration.mClientSource && this.mPatchHandle == audioRecordingConfiguration.mPatchHandle && this.mClientFormat.equals(audioRecordingConfiguration.mClientFormat) && this.mDeviceFormat.equals(audioRecordingConfiguration.mDeviceFormat) && this.mClientPackageName.equals(audioRecordingConfiguration.mClientPackageName) && this.mClientPortId == audioRecordingConfiguration.mClientPortId && this.mClientSilenced == audioRecordingConfiguration.mClientSilenced && this.mDeviceSource == audioRecordingConfiguration.mDeviceSource && java.util.Arrays.equals(this.mClientEffects, audioRecordingConfiguration.mClientEffects) && java.util.Arrays.equals(this.mDeviceEffects, audioRecordingConfiguration.mDeviceEffects)) {
            return true;
        }
        return false;
    }
}
