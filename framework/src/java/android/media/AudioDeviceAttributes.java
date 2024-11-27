package android.media;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class AudioDeviceAttributes implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioDeviceAttributes> CREATOR = new android.os.Parcelable.Creator<android.media.AudioDeviceAttributes>() { // from class: android.media.AudioDeviceAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioDeviceAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioDeviceAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioDeviceAttributes[] newArray(int i) {
            return new android.media.AudioDeviceAttributes[i];
        }
    };
    public static final int ROLE_INPUT = 1;
    public static final int ROLE_OUTPUT = 2;
    private java.lang.String mAddress;
    private final java.util.List<android.media.AudioDescriptor> mAudioDescriptors;
    private final java.util.List<android.media.AudioProfile> mAudioProfiles;
    private final java.lang.String mName;
    private final int mNativeType;
    private final int mRole;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Role {
    }

    @android.annotation.SystemApi
    public AudioDeviceAttributes(android.media.AudioDeviceInfo audioDeviceInfo) {
        java.util.Objects.requireNonNull(audioDeviceInfo);
        this.mRole = audioDeviceInfo.isSink() ? 2 : 1;
        this.mType = audioDeviceInfo.getType();
        this.mAddress = audioDeviceInfo.getAddress();
        this.mName = java.lang.String.valueOf(audioDeviceInfo.getProductName());
        this.mNativeType = audioDeviceInfo.getInternalType();
        this.mAudioProfiles = audioDeviceInfo.getAudioProfiles();
        this.mAudioDescriptors = audioDeviceInfo.getAudioDescriptors();
    }

    @android.annotation.SystemApi
    public AudioDeviceAttributes(int i, int i2, java.lang.String str) {
        this(i, i2, str, "", new java.util.ArrayList(), new java.util.ArrayList());
    }

    @android.annotation.SystemApi
    public AudioDeviceAttributes(int i, int i2, java.lang.String str, java.lang.String str2, java.util.List<android.media.AudioProfile> list, java.util.List<android.media.AudioDescriptor> list2) {
        java.util.Objects.requireNonNull(str);
        if (i != 2 && i != 1) {
            throw new java.lang.IllegalArgumentException("Invalid role " + i);
        }
        if (i == 2) {
            android.media.AudioDeviceInfo.enforceValidAudioDeviceTypeOut(i2);
            this.mNativeType = android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(i2);
        } else if (i == 1) {
            android.media.AudioDeviceInfo.enforceValidAudioDeviceTypeIn(i2);
            this.mNativeType = android.media.AudioDeviceInfo.convertDeviceTypeToInternalInputDevice(i2, str);
        } else {
            this.mNativeType = 0;
        }
        this.mRole = i;
        this.mType = i2;
        this.mAddress = str;
        this.mName = str2;
        this.mAudioProfiles = list;
        this.mAudioDescriptors = list2;
    }

    public AudioDeviceAttributes(int i, java.lang.String str) {
        this(i, str, "");
    }

    public AudioDeviceAttributes(int i, java.lang.String str, java.lang.String str2) {
        this.mRole = android.media.AudioSystem.isInputDevice(i) ? 1 : 2;
        this.mType = android.media.AudioDeviceInfo.convertInternalDeviceToDeviceType(i);
        this.mAddress = str;
        this.mName = str2;
        this.mNativeType = i;
        this.mAudioProfiles = new java.util.ArrayList();
        this.mAudioDescriptors = new java.util.ArrayList();
    }

    public AudioDeviceAttributes(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        this.mRole = audioDeviceAttributes.getRole();
        this.mType = audioDeviceAttributes.getType();
        this.mAddress = audioDeviceAttributes.getAddress();
        this.mName = audioDeviceAttributes.getName();
        this.mNativeType = audioDeviceAttributes.getInternalType();
        this.mAudioProfiles = audioDeviceAttributes.getAudioProfiles();
        this.mAudioDescriptors = audioDeviceAttributes.getAudioDescriptors();
    }

    @android.annotation.SystemApi
    public int getRole() {
        return this.mRole;
    }

    @android.annotation.SystemApi
    public int getType() {
        return this.mType;
    }

    @android.annotation.SystemApi
    public java.lang.String getAddress() {
        return this.mAddress;
    }

    public void setAddress(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        this.mAddress = str;
    }

    @android.annotation.SystemApi
    public java.lang.String getName() {
        return this.mName;
    }

    public int getInternalType() {
        return this.mNativeType;
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.AudioProfile> getAudioProfiles() {
        return this.mAudioProfiles;
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.AudioDescriptor> getAudioDescriptors() {
        return this.mAudioDescriptors;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRole), java.lang.Integer.valueOf(this.mType), this.mAddress, this.mName, this.mAudioProfiles, this.mAudioDescriptors);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.AudioDeviceAttributes audioDeviceAttributes = (android.media.AudioDeviceAttributes) obj;
        if (this.mRole == audioDeviceAttributes.mRole && this.mType == audioDeviceAttributes.mType && this.mAddress.equals(audioDeviceAttributes.mAddress) && this.mName.equals(audioDeviceAttributes.mName) && this.mAudioProfiles.equals(audioDeviceAttributes.mAudioProfiles) && this.mAudioDescriptors.equals(audioDeviceAttributes.mAudioDescriptors)) {
            return true;
        }
        return false;
    }

    public boolean equalTypeAddress(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.AudioDeviceAttributes audioDeviceAttributes = (android.media.AudioDeviceAttributes) obj;
        if (this.mRole == audioDeviceAttributes.mRole && this.mType == audioDeviceAttributes.mType && this.mAddress.equals(audioDeviceAttributes.mAddress)) {
            return true;
        }
        return false;
    }

    public static java.lang.String roleToString(int i) {
        return i == 2 ? "output" : "input";
    }

    public java.lang.String toString() {
        return new java.lang.String("AudioDeviceAttributes: role:" + roleToString(this.mRole) + " type:" + (this.mRole == 2 ? android.media.AudioSystem.getOutputDeviceName(this.mNativeType) : android.media.AudioSystem.getInputDeviceName(this.mNativeType)) + " addr:" + android.media.Utils.anonymizeBluetoothAddress(this.mNativeType, this.mAddress) + " name:" + this.mName + " profiles:" + this.mAudioProfiles.toString() + " descriptors:" + this.mAudioDescriptors.toString());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRole);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mAddress);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mNativeType);
        parcel.writeParcelableArray((android.media.AudioProfile[]) this.mAudioProfiles.toArray(new android.media.AudioProfile[this.mAudioProfiles.size()]), i);
        parcel.writeParcelableArray((android.media.AudioDescriptor[]) this.mAudioDescriptors.toArray(new android.media.AudioDescriptor[this.mAudioDescriptors.size()]), i);
    }

    private AudioDeviceAttributes(android.os.Parcel parcel) {
        this.mRole = parcel.readInt();
        this.mType = parcel.readInt();
        this.mAddress = parcel.readString();
        this.mName = parcel.readString();
        this.mNativeType = parcel.readInt();
        this.mAudioProfiles = new java.util.ArrayList(java.util.Arrays.asList((android.media.AudioProfile[]) parcel.readParcelableArray(android.media.AudioProfile.class.getClassLoader(), android.media.AudioProfile.class)));
        this.mAudioDescriptors = new java.util.ArrayList(java.util.Arrays.asList((android.media.AudioDescriptor[]) parcel.readParcelableArray(android.media.AudioDescriptor.class.getClassLoader(), android.media.AudioDescriptor.class)));
    }
}
