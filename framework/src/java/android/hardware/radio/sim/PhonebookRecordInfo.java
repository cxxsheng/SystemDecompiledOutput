package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public class PhonebookRecordInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.sim.PhonebookRecordInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.sim.PhonebookRecordInfo>() { // from class: android.hardware.radio.sim.PhonebookRecordInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.PhonebookRecordInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.sim.PhonebookRecordInfo phonebookRecordInfo = new android.hardware.radio.sim.PhonebookRecordInfo();
            phonebookRecordInfo.readFromParcel(parcel);
            return phonebookRecordInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.PhonebookRecordInfo[] newArray(int i) {
            return new android.hardware.radio.sim.PhonebookRecordInfo[i];
        }
    };
    public java.lang.String[] additionalNumbers;
    public java.lang.String[] emails;
    public java.lang.String name;
    public java.lang.String number;
    public int recordId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.recordId);
        parcel.writeString(this.name);
        parcel.writeString(this.number);
        parcel.writeStringArray(this.emails);
        parcel.writeStringArray(this.additionalNumbers);
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
            this.recordId = parcel.readInt();
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
            this.number = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.emails = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.additionalNumbers = parcel.createStringArray();
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
        stringJoiner.add("recordId: " + this.recordId);
        stringJoiner.add("name: " + java.util.Objects.toString(this.name));
        stringJoiner.add("number: " + java.util.Objects.toString(this.number));
        stringJoiner.add("emails: " + java.util.Arrays.toString(this.emails));
        stringJoiner.add("additionalNumbers: " + java.util.Arrays.toString(this.additionalNumbers));
        return "PhonebookRecordInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
