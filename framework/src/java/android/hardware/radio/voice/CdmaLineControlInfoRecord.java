package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class CdmaLineControlInfoRecord implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaLineControlInfoRecord> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaLineControlInfoRecord>() { // from class: android.hardware.radio.voice.CdmaLineControlInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaLineControlInfoRecord createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.CdmaLineControlInfoRecord cdmaLineControlInfoRecord = new android.hardware.radio.voice.CdmaLineControlInfoRecord();
            cdmaLineControlInfoRecord.readFromParcel(parcel);
            return cdmaLineControlInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaLineControlInfoRecord[] newArray(int i) {
            return new android.hardware.radio.voice.CdmaLineControlInfoRecord[i];
        }
    };
    public byte lineCtrlPolarityIncluded = 0;
    public byte lineCtrlToggle = 0;
    public byte lineCtrlReverse = 0;
    public byte lineCtrlPowerDenial = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.lineCtrlPolarityIncluded);
        parcel.writeByte(this.lineCtrlToggle);
        parcel.writeByte(this.lineCtrlReverse);
        parcel.writeByte(this.lineCtrlPowerDenial);
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
            this.lineCtrlPolarityIncluded = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.lineCtrlToggle = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.lineCtrlReverse = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.lineCtrlPowerDenial = parcel.readByte();
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
        stringJoiner.add("lineCtrlPolarityIncluded: " + ((int) this.lineCtrlPolarityIncluded));
        stringJoiner.add("lineCtrlToggle: " + ((int) this.lineCtrlToggle));
        stringJoiner.add("lineCtrlReverse: " + ((int) this.lineCtrlReverse));
        stringJoiner.add("lineCtrlPowerDenial: " + ((int) this.lineCtrlPowerDenial));
        return "CdmaLineControlInfoRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
