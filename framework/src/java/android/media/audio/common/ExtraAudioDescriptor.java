package android.media.audio.common;

/* loaded from: classes2.dex */
public class ExtraAudioDescriptor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.ExtraAudioDescriptor> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.ExtraAudioDescriptor>() { // from class: android.media.audio.common.ExtraAudioDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.ExtraAudioDescriptor createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.ExtraAudioDescriptor extraAudioDescriptor = new android.media.audio.common.ExtraAudioDescriptor();
            extraAudioDescriptor.readFromParcel(parcel);
            return extraAudioDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.ExtraAudioDescriptor[] newArray(int i) {
            return new android.media.audio.common.ExtraAudioDescriptor[i];
        }
    };
    public byte[] audioDescriptor;
    public int standard = 0;
    public int encapsulationType = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.standard);
        parcel.writeByteArray(this.audioDescriptor);
        parcel.writeInt(this.encapsulationType);
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
            this.standard = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.audioDescriptor = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.encapsulationType = parcel.readInt();
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
        stringJoiner.add("standard: " + this.standard);
        stringJoiner.add("audioDescriptor: " + java.util.Arrays.toString(this.audioDescriptor));
        stringJoiner.add("encapsulationType: " + this.encapsulationType);
        return "ExtraAudioDescriptor" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.ExtraAudioDescriptor)) {
            return false;
        }
        android.media.audio.common.ExtraAudioDescriptor extraAudioDescriptor = (android.media.audio.common.ExtraAudioDescriptor) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.standard), java.lang.Integer.valueOf(extraAudioDescriptor.standard)) && java.util.Objects.deepEquals(this.audioDescriptor, extraAudioDescriptor.audioDescriptor) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.encapsulationType), java.lang.Integer.valueOf(extraAudioDescriptor.encapsulationType))) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.standard), this.audioDescriptor, java.lang.Integer.valueOf(this.encapsulationType)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
