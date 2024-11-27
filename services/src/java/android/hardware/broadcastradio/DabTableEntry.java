package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class DabTableEntry implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.DabTableEntry> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.DabTableEntry>() { // from class: android.hardware.broadcastradio.DabTableEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.DabTableEntry createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.DabTableEntry dabTableEntry = new android.hardware.broadcastradio.DabTableEntry();
            dabTableEntry.readFromParcel(parcel);
            return dabTableEntry;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.DabTableEntry[] newArray(int i) {
            return new android.hardware.broadcastradio.DabTableEntry[i];
        }
    };
    public int frequencyKhz = 0;
    public java.lang.String label;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.label);
        parcel.writeInt(this.frequencyKhz);
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
            this.label = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.frequencyKhz = parcel.readInt();
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
        stringJoiner.add("label: " + java.util.Objects.toString(this.label));
        stringJoiner.add("frequencyKhz: " + this.frequencyKhz);
        return "DabTableEntry" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.DabTableEntry)) {
            return false;
        }
        android.hardware.broadcastradio.DabTableEntry dabTableEntry = (android.hardware.broadcastradio.DabTableEntry) obj;
        if (java.util.Objects.deepEquals(this.label, dabTableEntry.label) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.frequencyKhz), java.lang.Integer.valueOf(dabTableEntry.frequencyKhz))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.label, java.lang.Integer.valueOf(this.frequencyKhz)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
