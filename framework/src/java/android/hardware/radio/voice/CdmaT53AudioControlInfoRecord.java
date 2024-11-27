package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class CdmaT53AudioControlInfoRecord implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaT53AudioControlInfoRecord> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaT53AudioControlInfoRecord>() { // from class: android.hardware.radio.voice.CdmaT53AudioControlInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaT53AudioControlInfoRecord createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.CdmaT53AudioControlInfoRecord cdmaT53AudioControlInfoRecord = new android.hardware.radio.voice.CdmaT53AudioControlInfoRecord();
            cdmaT53AudioControlInfoRecord.readFromParcel(parcel);
            return cdmaT53AudioControlInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaT53AudioControlInfoRecord[] newArray(int i) {
            return new android.hardware.radio.voice.CdmaT53AudioControlInfoRecord[i];
        }
    };
    public byte upLink = 0;
    public byte downLink = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.upLink);
        parcel.writeByte(this.downLink);
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
            this.upLink = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.downLink = parcel.readByte();
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
        stringJoiner.add("upLink: " + ((int) this.upLink));
        stringJoiner.add("downLink: " + ((int) this.downLink));
        return "CdmaT53AudioControlInfoRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
