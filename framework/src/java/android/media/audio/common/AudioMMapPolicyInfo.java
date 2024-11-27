package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioMMapPolicyInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioMMapPolicyInfo> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioMMapPolicyInfo>() { // from class: android.media.audio.common.AudioMMapPolicyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioMMapPolicyInfo createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioMMapPolicyInfo audioMMapPolicyInfo = new android.media.audio.common.AudioMMapPolicyInfo();
            audioMMapPolicyInfo.readFromParcel(parcel);
            return audioMMapPolicyInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioMMapPolicyInfo[] newArray(int i) {
            return new android.media.audio.common.AudioMMapPolicyInfo[i];
        }
    };
    public android.media.audio.common.AudioDevice device;
    public int mmapPolicy = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.device, i);
        parcel.writeInt(this.mmapPolicy);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.device = (android.media.audio.common.AudioDevice) parcel.readTypedObject(android.media.audio.common.AudioDevice.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.mmapPolicy = parcel.readInt();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("device: " + java.util.Objects.toString(this.device));
        stringJoiner.add("mmapPolicy: " + this.mmapPolicy);
        return "AudioMMapPolicyInfo" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioMMapPolicyInfo)) {
            return false;
        }
        android.media.audio.common.AudioMMapPolicyInfo audioMMapPolicyInfo = (android.media.audio.common.AudioMMapPolicyInfo) obj;
        if (java.util.Objects.deepEquals(this.device, audioMMapPolicyInfo.device) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.mmapPolicy), java.lang.Integer.valueOf(audioMMapPolicyInfo.mmapPolicy))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.device, java.lang.Integer.valueOf(this.mmapPolicy)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.device) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
