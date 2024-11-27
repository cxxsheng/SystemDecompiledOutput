package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public class PhonebookCapacity implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.sim.PhonebookCapacity> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.sim.PhonebookCapacity>() { // from class: android.hardware.radio.sim.PhonebookCapacity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.PhonebookCapacity createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.sim.PhonebookCapacity phonebookCapacity = new android.hardware.radio.sim.PhonebookCapacity();
            phonebookCapacity.readFromParcel(parcel);
            return phonebookCapacity;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.PhonebookCapacity[] newArray(int i) {
            return new android.hardware.radio.sim.PhonebookCapacity[i];
        }
    };
    public int maxAdnRecords = 0;
    public int usedAdnRecords = 0;
    public int maxEmailRecords = 0;
    public int usedEmailRecords = 0;
    public int maxAdditionalNumberRecords = 0;
    public int usedAdditionalNumberRecords = 0;
    public int maxNameLen = 0;
    public int maxNumberLen = 0;
    public int maxEmailLen = 0;
    public int maxAdditionalNumberLen = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.maxAdnRecords);
        parcel.writeInt(this.usedAdnRecords);
        parcel.writeInt(this.maxEmailRecords);
        parcel.writeInt(this.usedEmailRecords);
        parcel.writeInt(this.maxAdditionalNumberRecords);
        parcel.writeInt(this.usedAdditionalNumberRecords);
        parcel.writeInt(this.maxNameLen);
        parcel.writeInt(this.maxNumberLen);
        parcel.writeInt(this.maxEmailLen);
        parcel.writeInt(this.maxAdditionalNumberLen);
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
            this.maxAdnRecords = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.usedAdnRecords = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxEmailRecords = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.usedEmailRecords = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxAdditionalNumberRecords = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.usedAdditionalNumberRecords = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxNameLen = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxNumberLen = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxEmailLen = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.maxAdditionalNumberLen = parcel.readInt();
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
        stringJoiner.add("maxAdnRecords: " + this.maxAdnRecords);
        stringJoiner.add("usedAdnRecords: " + this.usedAdnRecords);
        stringJoiner.add("maxEmailRecords: " + this.maxEmailRecords);
        stringJoiner.add("usedEmailRecords: " + this.usedEmailRecords);
        stringJoiner.add("maxAdditionalNumberRecords: " + this.maxAdditionalNumberRecords);
        stringJoiner.add("usedAdditionalNumberRecords: " + this.usedAdditionalNumberRecords);
        stringJoiner.add("maxNameLen: " + this.maxNameLen);
        stringJoiner.add("maxNumberLen: " + this.maxNumberLen);
        stringJoiner.add("maxEmailLen: " + this.maxEmailLen);
        stringJoiner.add("maxAdditionalNumberLen: " + this.maxAdditionalNumberLen);
        return "PhonebookCapacity" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
