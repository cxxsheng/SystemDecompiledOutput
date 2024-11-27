package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class AmFmBandRange implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.AmFmBandRange> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.AmFmBandRange>() { // from class: android.hardware.broadcastradio.AmFmBandRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.AmFmBandRange createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.AmFmBandRange amFmBandRange = new android.hardware.broadcastradio.AmFmBandRange();
            amFmBandRange.readFromParcel(parcel);
            return amFmBandRange;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.AmFmBandRange[] newArray(int i) {
            return new android.hardware.broadcastradio.AmFmBandRange[i];
        }
    };
    public int lowerBound = 0;
    public int upperBound = 0;
    public int spacing = 0;
    public int seekSpacing = 0;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.lowerBound);
        parcel.writeInt(this.upperBound);
        parcel.writeInt(this.spacing);
        parcel.writeInt(this.seekSpacing);
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
            this.lowerBound = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.upperBound = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.spacing = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.seekSpacing = parcel.readInt();
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
        stringJoiner.add("lowerBound: " + this.lowerBound);
        stringJoiner.add("upperBound: " + this.upperBound);
        stringJoiner.add("spacing: " + this.spacing);
        stringJoiner.add("seekSpacing: " + this.seekSpacing);
        return "AmFmBandRange" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.AmFmBandRange)) {
            return false;
        }
        android.hardware.broadcastradio.AmFmBandRange amFmBandRange = (android.hardware.broadcastradio.AmFmBandRange) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.lowerBound), java.lang.Integer.valueOf(amFmBandRange.lowerBound)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.upperBound), java.lang.Integer.valueOf(amFmBandRange.upperBound)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.spacing), java.lang.Integer.valueOf(amFmBandRange.spacing)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.seekSpacing), java.lang.Integer.valueOf(amFmBandRange.seekSpacing))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.lowerBound), java.lang.Integer.valueOf(this.upperBound), java.lang.Integer.valueOf(this.spacing), java.lang.Integer.valueOf(this.seekSpacing)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
