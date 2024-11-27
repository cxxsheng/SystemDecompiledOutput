package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioFormatDescription implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioFormatDescription> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioFormatDescription>() { // from class: android.media.audio.common.AudioFormatDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioFormatDescription createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioFormatDescription audioFormatDescription = new android.media.audio.common.AudioFormatDescription();
            audioFormatDescription.readFromParcel(parcel);
            return audioFormatDescription;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioFormatDescription[] newArray(int i) {
            return new android.media.audio.common.AudioFormatDescription[i];
        }
    };
    public java.lang.String encoding;
    public byte type = 0;
    public byte pcm = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.type);
        parcel.writeByte(this.pcm);
        parcel.writeString(this.encoding);
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
            this.type = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pcm = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.encoding = parcel.readString();
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
        stringJoiner.add("type: " + ((int) this.type));
        stringJoiner.add("pcm: " + ((int) this.pcm));
        stringJoiner.add("encoding: " + java.util.Objects.toString(this.encoding));
        return "AudioFormatDescription" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioFormatDescription)) {
            return false;
        }
        android.media.audio.common.AudioFormatDescription audioFormatDescription = (android.media.audio.common.AudioFormatDescription) obj;
        if (java.util.Objects.deepEquals(java.lang.Byte.valueOf(this.type), java.lang.Byte.valueOf(audioFormatDescription.type)) && java.util.Objects.deepEquals(java.lang.Byte.valueOf(this.pcm), java.lang.Byte.valueOf(audioFormatDescription.pcm)) && java.util.Objects.deepEquals(this.encoding, audioFormatDescription.encoding)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Byte.valueOf(this.type), java.lang.Byte.valueOf(this.pcm), this.encoding).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
