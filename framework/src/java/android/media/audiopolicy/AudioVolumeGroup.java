package android.media.audiopolicy;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class AudioVolumeGroup implements android.os.Parcelable {
    public static final int DEFAULT_VOLUME_GROUP = -1;
    private static final java.lang.String TAG = "AudioVolumeGroup";
    private static java.util.List<android.media.audiopolicy.AudioVolumeGroup> sAudioVolumeGroups;
    private final android.media.AudioAttributes[] mAudioAttributes;
    private int mId;
    private int[] mLegacyStreamTypes;
    private final java.lang.String mName;
    private static final java.lang.Object sLock = new java.lang.Object();
    public static final android.os.Parcelable.Creator<android.media.audiopolicy.AudioVolumeGroup> CREATOR = new android.os.Parcelable.Creator<android.media.audiopolicy.AudioVolumeGroup>() { // from class: android.media.audiopolicy.AudioVolumeGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioVolumeGroup createFromParcel(android.os.Parcel parcel) {
            com.android.internal.util.Preconditions.checkNotNull(parcel, "in Parcel must not be null");
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.media.AudioAttributes[] audioAttributesArr = new android.media.AudioAttributes[readInt2];
            for (int i = 0; i < readInt2; i++) {
                audioAttributesArr[i] = android.media.AudioAttributes.CREATOR.createFromParcel(parcel);
            }
            int readInt3 = parcel.readInt();
            int[] iArr = new int[readInt3];
            for (int i2 = 0; i2 < readInt3; i2++) {
                iArr[i2] = parcel.readInt();
            }
            return new android.media.audiopolicy.AudioVolumeGroup(readString, readInt, audioAttributesArr, iArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioVolumeGroup[] newArray(int i) {
            return new android.media.audiopolicy.AudioVolumeGroup[i];
        }
    };

    private static native int native_list_audio_volume_groups(java.util.ArrayList<android.media.audiopolicy.AudioVolumeGroup> arrayList);

    public static java.util.List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() {
        if (sAudioVolumeGroups == null) {
            synchronized (sLock) {
                if (sAudioVolumeGroups == null) {
                    sAudioVolumeGroups = initializeAudioVolumeGroups();
                }
            }
        }
        return sAudioVolumeGroups;
    }

    private static java.util.List<android.media.audiopolicy.AudioVolumeGroup> initializeAudioVolumeGroups() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (native_list_audio_volume_groups(arrayList) != 0) {
            android.util.Log.w(TAG, ": listAudioVolumeGroups failed");
        }
        return arrayList;
    }

    AudioVolumeGroup(java.lang.String str, int i, android.media.AudioAttributes[] audioAttributesArr, int[] iArr) {
        com.android.internal.util.Preconditions.checkNotNull(str, "name must not be null");
        com.android.internal.util.Preconditions.checkNotNull(audioAttributesArr, "audioAttributes must not be null");
        com.android.internal.util.Preconditions.checkNotNull(iArr, "legacyStreamTypes must not be null");
        this.mName = str;
        this.mId = i;
        this.mAudioAttributes = audioAttributesArr;
        this.mLegacyStreamTypes = iArr;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.audiopolicy.AudioVolumeGroup audioVolumeGroup = (android.media.audiopolicy.AudioVolumeGroup) obj;
        if (this.mName.equals(audioVolumeGroup.mName) && this.mId == audioVolumeGroup.mId && java.util.Arrays.equals(this.mAudioAttributes, audioVolumeGroup.mAudioAttributes)) {
            return true;
        }
        return false;
    }

    public java.util.List<android.media.AudioAttributes> getAudioAttributes() {
        return java.util.Arrays.asList(this.mAudioAttributes);
    }

    public int[] getLegacyStreamTypes() {
        return this.mLegacyStreamTypes;
    }

    public java.lang.String name() {
        return this.mName;
    }

    public int getId() {
        return this.mId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mAudioAttributes.length);
        for (android.media.AudioAttributes audioAttributes : this.mAudioAttributes) {
            audioAttributes.writeToParcel(parcel, i | 1);
        }
        parcel.writeInt(this.mLegacyStreamTypes.length);
        for (int i2 : this.mLegacyStreamTypes) {
            parcel.writeInt(i2);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("\n Name: ");
        sb.append(this.mName);
        sb.append(" Id: ");
        sb.append(java.lang.Integer.toString(this.mId));
        sb.append("\n     Supported Audio Attributes:");
        for (android.media.AudioAttributes audioAttributes : this.mAudioAttributes) {
            sb.append("\n       -");
            sb.append(audioAttributes.toString());
        }
        sb.append("\n     Supported Legacy Stream Types: { ");
        for (int i : this.mLegacyStreamTypes) {
            sb.append(java.lang.Integer.toString(i));
            sb.append(" ");
        }
        sb.append("}");
        return sb.toString();
    }
}
