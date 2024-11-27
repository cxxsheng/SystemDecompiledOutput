package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class CdmaT53ClirInfoRecord implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaT53ClirInfoRecord> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaT53ClirInfoRecord>() { // from class: android.hardware.radio.voice.CdmaT53ClirInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaT53ClirInfoRecord createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.CdmaT53ClirInfoRecord cdmaT53ClirInfoRecord = new android.hardware.radio.voice.CdmaT53ClirInfoRecord();
            cdmaT53ClirInfoRecord.readFromParcel(parcel);
            return cdmaT53ClirInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaT53ClirInfoRecord[] newArray(int i) {
            return new android.hardware.radio.voice.CdmaT53ClirInfoRecord[i];
        }
    };
    public byte cause = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.cause);
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
            } else {
                this.cause = parcel.readByte();
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
        stringJoiner.add("cause: " + ((int) this.cause));
        return "CdmaT53ClirInfoRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
