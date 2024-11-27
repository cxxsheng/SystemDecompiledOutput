package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class CdmaNumberInfoRecord implements android.os.Parcelable {
    public static final int CDMA_NUMBER_INFO_BUFFER_LENGTH = 81;
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaNumberInfoRecord> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaNumberInfoRecord>() { // from class: android.hardware.radio.voice.CdmaNumberInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaNumberInfoRecord createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.CdmaNumberInfoRecord cdmaNumberInfoRecord = new android.hardware.radio.voice.CdmaNumberInfoRecord();
            cdmaNumberInfoRecord.readFromParcel(parcel);
            return cdmaNumberInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaNumberInfoRecord[] newArray(int i) {
            return new android.hardware.radio.voice.CdmaNumberInfoRecord[i];
        }
    };
    public java.lang.String number;
    public byte numberType = 0;
    public byte numberPlan = 0;
    public byte pi = 0;
    public byte si = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.number);
        parcel.writeByte(this.numberType);
        parcel.writeByte(this.numberPlan);
        parcel.writeByte(this.pi);
        parcel.writeByte(this.si);
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
            this.number = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.numberType = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.numberPlan = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pi = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.si = parcel.readByte();
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
        stringJoiner.add("number: " + java.util.Objects.toString(this.number));
        stringJoiner.add("numberType: " + ((int) this.numberType));
        stringJoiner.add("numberPlan: " + ((int) this.numberPlan));
        stringJoiner.add("pi: " + ((int) this.pi));
        stringJoiner.add("si: " + ((int) this.si));
        return "CdmaNumberInfoRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
