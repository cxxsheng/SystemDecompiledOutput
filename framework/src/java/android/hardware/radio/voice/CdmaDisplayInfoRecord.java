package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class CdmaDisplayInfoRecord implements android.os.Parcelable {
    public static final int CDMA_ALPHA_INFO_BUFFER_LENGTH = 64;
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaDisplayInfoRecord> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.CdmaDisplayInfoRecord>() { // from class: android.hardware.radio.voice.CdmaDisplayInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaDisplayInfoRecord createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.CdmaDisplayInfoRecord cdmaDisplayInfoRecord = new android.hardware.radio.voice.CdmaDisplayInfoRecord();
            cdmaDisplayInfoRecord.readFromParcel(parcel);
            return cdmaDisplayInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.CdmaDisplayInfoRecord[] newArray(int i) {
            return new android.hardware.radio.voice.CdmaDisplayInfoRecord[i];
        }
    };
    public java.lang.String alphaBuf;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.alphaBuf);
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
                this.alphaBuf = parcel.readString();
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
        stringJoiner.add("alphaBuf: " + java.util.Objects.toString(this.alphaBuf));
        return "CdmaDisplayInfoRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
