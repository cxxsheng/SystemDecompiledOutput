package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class CdmaSignalInfoRecord implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaSignalInfoRecord> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaSignalInfoRecord>() { // from class: android.hardware.radio.voice.CdmaSignalInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaSignalInfoRecord createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.CdmaSignalInfoRecord cdmaSignalInfoRecord = new android.hardware.radio.voice.CdmaSignalInfoRecord();
            cdmaSignalInfoRecord.readFromParcel(parcel);
            return cdmaSignalInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaSignalInfoRecord[] newArray(int i) {
            return new android.hardware.radio.voice.CdmaSignalInfoRecord[i];
        }
    };
    public boolean isPresent = false;
    public byte signalType = 0;
    public byte alertPitch = 0;
    public byte signal = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.isPresent);
        parcel.writeByte(this.signalType);
        parcel.writeByte(this.alertPitch);
        parcel.writeByte(this.signal);
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
            this.isPresent = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.signalType = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.alertPitch = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.signal = parcel.readByte();
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
        stringJoiner.add("isPresent: " + this.isPresent);
        stringJoiner.add("signalType: " + ((int) this.signalType));
        stringJoiner.add("alertPitch: " + ((int) this.alertPitch));
        stringJoiner.add("signal: " + ((int) this.signal));
        return "CdmaSignalInfoRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
