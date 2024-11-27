package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class ProgramIdentifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramIdentifier> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramIdentifier>() { // from class: android.hardware.broadcastradio.ProgramIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramIdentifier createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.ProgramIdentifier programIdentifier = new android.hardware.broadcastradio.ProgramIdentifier();
            programIdentifier.readFromParcel(parcel);
            return programIdentifier;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramIdentifier[] newArray(int i) {
            return new android.hardware.broadcastradio.ProgramIdentifier[i];
        }
    };
    public int type = 0;
    public long value = 0;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeLong(this.value);
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
            this.type = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.value = parcel.readLong();
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
        stringJoiner.add("type: " + android.hardware.broadcastradio.IdentifierType$$.toString(this.type));
        stringJoiner.add("value: " + this.value);
        return "ProgramIdentifier" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.ProgramIdentifier)) {
            return false;
        }
        android.hardware.broadcastradio.ProgramIdentifier programIdentifier = (android.hardware.broadcastradio.ProgramIdentifier) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.type), java.lang.Integer.valueOf(programIdentifier.type)) && java.util.Objects.deepEquals(java.lang.Long.valueOf(this.value), java.lang.Long.valueOf(programIdentifier.value))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.type), java.lang.Long.valueOf(this.value)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
