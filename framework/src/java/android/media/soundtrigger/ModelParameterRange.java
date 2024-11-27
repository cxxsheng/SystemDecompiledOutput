package android.media.soundtrigger;

/* loaded from: classes2.dex */
public class ModelParameterRange implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger.ModelParameterRange> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger.ModelParameterRange>() { // from class: android.media.soundtrigger.ModelParameterRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.ModelParameterRange createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger.ModelParameterRange modelParameterRange = new android.media.soundtrigger.ModelParameterRange();
            modelParameterRange.readFromParcel(parcel);
            return modelParameterRange;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.ModelParameterRange[] newArray(int i) {
            return new android.media.soundtrigger.ModelParameterRange[i];
        }
    };
    public int minInclusive = 0;
    public int maxInclusive = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.minInclusive);
        parcel.writeInt(this.maxInclusive);
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
            this.minInclusive = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.maxInclusive = parcel.readInt();
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
        stringJoiner.add("minInclusive: " + this.minInclusive);
        stringJoiner.add("maxInclusive: " + this.maxInclusive);
        return "ModelParameterRange" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger.ModelParameterRange)) {
            return false;
        }
        android.media.soundtrigger.ModelParameterRange modelParameterRange = (android.media.soundtrigger.ModelParameterRange) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.minInclusive), java.lang.Integer.valueOf(modelParameterRange.minInclusive)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxInclusive), java.lang.Integer.valueOf(modelParameterRange.maxInclusive))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.minInclusive), java.lang.Integer.valueOf(this.maxInclusive)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
