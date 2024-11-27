package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioHalCapCriterion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioHalCapCriterion> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioHalCapCriterion>() { // from class: android.media.audio.common.AudioHalCapCriterion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalCapCriterion createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioHalCapCriterion audioHalCapCriterion = new android.media.audio.common.AudioHalCapCriterion();
            audioHalCapCriterion.readFromParcel(parcel);
            return audioHalCapCriterion;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalCapCriterion[] newArray(int i) {
            return new android.media.audio.common.AudioHalCapCriterion[i];
        }
    };
    public java.lang.String criterionTypeName;
    public java.lang.String defaultLiteralValue;
    public java.lang.String name;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeString(this.criterionTypeName);
        parcel.writeString(this.defaultLiteralValue);
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
            this.criterionTypeName = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.defaultLiteralValue = parcel.readString();
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
        stringJoiner.add("criterionTypeName: " + java.util.Objects.toString(this.criterionTypeName));
        stringJoiner.add("defaultLiteralValue: " + java.util.Objects.toString(this.defaultLiteralValue));
        return "AudioHalCapCriterion" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioHalCapCriterion)) {
            return false;
        }
        android.media.audio.common.AudioHalCapCriterion audioHalCapCriterion = (android.media.audio.common.AudioHalCapCriterion) obj;
        if (java.util.Objects.deepEquals(this.name, audioHalCapCriterion.name) && java.util.Objects.deepEquals(this.criterionTypeName, audioHalCapCriterion.criterionTypeName) && java.util.Objects.deepEquals(this.defaultLiteralValue, audioHalCapCriterion.defaultLiteralValue)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.name, this.criterionTypeName, this.defaultLiteralValue).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
