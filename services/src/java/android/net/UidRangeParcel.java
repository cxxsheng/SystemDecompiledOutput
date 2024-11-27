package android.net;

/* loaded from: classes.dex */
public class UidRangeParcel implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.UidRangeParcel> CREATOR = new android.os.Parcelable.Creator<android.net.UidRangeParcel>() { // from class: android.net.UidRangeParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.UidRangeParcel createFromParcel(android.os.Parcel parcel) {
            return android.net.UidRangeParcel.internalCreateFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.UidRangeParcel[] newArray(int i) {
            return new android.net.UidRangeParcel[i];
        }
    };
    public final int start;
    public final int stop;

    public static final class Builder {
        private int start = 0;
        private int stop = 0;

        public android.net.UidRangeParcel.Builder setStart(int i) {
            this.start = i;
            return this;
        }

        public android.net.UidRangeParcel.Builder setStop(int i) {
            this.stop = i;
            return this;
        }

        public android.net.UidRangeParcel build() {
            return new android.net.UidRangeParcel(this.start, this.stop);
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.start);
        parcel.writeInt(this.stop);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public UidRangeParcel(int i, int i2) {
        this.start = i;
        this.stop = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.net.UidRangeParcel internalCreateFromParcel(android.os.Parcel parcel) {
        int i;
        android.net.UidRangeParcel.Builder builder = new android.net.UidRangeParcel.Builder();
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
        } finally {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                android.os.BadParcelableException badParcelableException = new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            return builder.build();
        }
        if (readInt < 4) {
            throw new android.os.BadParcelableException("Parcelable too small");
        }
        builder.build();
        if (parcel.dataPosition() - dataPosition >= readInt) {
            builder.build();
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
        } else {
            builder.setStart(parcel.readInt());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                builder.build();
                if (dataPosition > i) {
                    throw new android.os.BadParcelableException(r4);
                }
            } else {
                builder.setStop(parcel.readInt());
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
            }
        }
        parcel.setDataPosition(dataPosition + readInt);
        return builder.build();
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("start: " + this.start);
        stringJoiner.add("stop: " + this.stop);
        return "UidRangeParcel" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.net.UidRangeParcel)) {
            return false;
        }
        android.net.UidRangeParcel uidRangeParcel = (android.net.UidRangeParcel) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.start), java.lang.Integer.valueOf(uidRangeParcel.start)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.stop), java.lang.Integer.valueOf(uidRangeParcel.stop))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.start), java.lang.Integer.valueOf(this.stop)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
