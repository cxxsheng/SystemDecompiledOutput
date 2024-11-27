package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioHalAttributesGroup implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioHalAttributesGroup> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioHalAttributesGroup>() { // from class: android.media.audio.common.AudioHalAttributesGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalAttributesGroup createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioHalAttributesGroup audioHalAttributesGroup = new android.media.audio.common.AudioHalAttributesGroup();
            audioHalAttributesGroup.readFromParcel(parcel);
            return audioHalAttributesGroup;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalAttributesGroup[] newArray(int i) {
            return new android.media.audio.common.AudioHalAttributesGroup[i];
        }
    };
    public android.media.audio.common.AudioAttributes[] attributes;
    public int streamType = -2;
    public java.lang.String volumeGroupName;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.streamType);
        parcel.writeString(this.volumeGroupName);
        parcel.writeTypedArray(this.attributes, i);
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
            this.streamType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.volumeGroupName = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.attributes = (android.media.audio.common.AudioAttributes[]) parcel.createTypedArray(android.media.audio.common.AudioAttributes.CREATOR);
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
        stringJoiner.add("streamType: " + this.streamType);
        stringJoiner.add("volumeGroupName: " + java.util.Objects.toString(this.volumeGroupName));
        stringJoiner.add("attributes: " + java.util.Arrays.toString(this.attributes));
        return "AudioHalAttributesGroup" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioHalAttributesGroup)) {
            return false;
        }
        android.media.audio.common.AudioHalAttributesGroup audioHalAttributesGroup = (android.media.audio.common.AudioHalAttributesGroup) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.streamType), java.lang.Integer.valueOf(audioHalAttributesGroup.streamType)) && java.util.Objects.deepEquals(this.volumeGroupName, audioHalAttributesGroup.volumeGroupName) && java.util.Objects.deepEquals(this.attributes, audioHalAttributesGroup.attributes)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.streamType), this.volumeGroupName, this.attributes).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.attributes) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
