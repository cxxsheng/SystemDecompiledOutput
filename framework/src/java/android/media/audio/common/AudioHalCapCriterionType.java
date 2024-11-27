package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioHalCapCriterionType implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioHalCapCriterionType> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioHalCapCriterionType>() { // from class: android.media.audio.common.AudioHalCapCriterionType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalCapCriterionType createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioHalCapCriterionType audioHalCapCriterionType = new android.media.audio.common.AudioHalCapCriterionType();
            audioHalCapCriterionType.readFromParcel(parcel);
            return audioHalCapCriterionType;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalCapCriterionType[] newArray(int i) {
            return new android.media.audio.common.AudioHalCapCriterionType[i];
        }
    };
    public boolean isInclusive = false;
    public java.lang.String name;
    public java.lang.String[] values;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeBoolean(this.isInclusive);
        parcel.writeStringArray(this.values);
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
            this.name = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.isInclusive = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.values = parcel.createStringArray();
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
        stringJoiner.add("name: " + java.util.Objects.toString(this.name));
        stringJoiner.add("isInclusive: " + this.isInclusive);
        stringJoiner.add("values: " + java.util.Arrays.toString(this.values));
        return "AudioHalCapCriterionType" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioHalCapCriterionType)) {
            return false;
        }
        android.media.audio.common.AudioHalCapCriterionType audioHalCapCriterionType = (android.media.audio.common.AudioHalCapCriterionType) obj;
        if (java.util.Objects.deepEquals(this.name, audioHalCapCriterionType.name) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.isInclusive), java.lang.Boolean.valueOf(audioHalCapCriterionType.isInclusive)) && java.util.Objects.deepEquals(this.values, audioHalCapCriterionType.values)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.name, java.lang.Boolean.valueOf(this.isInclusive), this.values).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
