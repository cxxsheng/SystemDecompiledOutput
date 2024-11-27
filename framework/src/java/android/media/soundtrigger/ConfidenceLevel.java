package android.media.soundtrigger;

/* loaded from: classes2.dex */
public class ConfidenceLevel implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger.ConfidenceLevel> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger.ConfidenceLevel>() { // from class: android.media.soundtrigger.ConfidenceLevel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.ConfidenceLevel createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger.ConfidenceLevel confidenceLevel = new android.media.soundtrigger.ConfidenceLevel();
            confidenceLevel.readFromParcel(parcel);
            return confidenceLevel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.ConfidenceLevel[] newArray(int i) {
            return new android.media.soundtrigger.ConfidenceLevel[i];
        }
    };
    public int userId = 0;
    public int levelPercent = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.levelPercent);
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
            this.userId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.levelPercent = parcel.readInt();
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
        stringJoiner.add("userId: " + this.userId);
        stringJoiner.add("levelPercent: " + this.levelPercent);
        return "ConfidenceLevel" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger.ConfidenceLevel)) {
            return false;
        }
        android.media.soundtrigger.ConfidenceLevel confidenceLevel = (android.media.soundtrigger.ConfidenceLevel) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.userId), java.lang.Integer.valueOf(confidenceLevel.userId)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.levelPercent), java.lang.Integer.valueOf(confidenceLevel.levelPercent))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.userId), java.lang.Integer.valueOf(this.levelPercent)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
