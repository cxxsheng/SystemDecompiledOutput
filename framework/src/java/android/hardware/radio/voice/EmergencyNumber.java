package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public class EmergencyNumber implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.voice.EmergencyNumber> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.voice.EmergencyNumber>() { // from class: android.hardware.radio.voice.EmergencyNumber.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.EmergencyNumber createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.voice.EmergencyNumber emergencyNumber = new android.hardware.radio.voice.EmergencyNumber();
            emergencyNumber.readFromParcel(parcel);
            return emergencyNumber;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.voice.EmergencyNumber[] newArray(int i) {
            return new android.hardware.radio.voice.EmergencyNumber[i];
        }
    };
    public static final int SOURCE_DEFAULT = 8;
    public static final int SOURCE_MODEM_CONFIG = 4;
    public static final int SOURCE_NETWORK_SIGNALING = 1;
    public static final int SOURCE_SIM = 2;
    public java.lang.String mcc;
    public java.lang.String mnc;
    public java.lang.String number;
    public java.lang.String[] urns;
    public int categories = 0;
    public int sources = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.number);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeInt(this.categories);
        parcel.writeStringArray(this.urns);
        parcel.writeInt(this.sources);
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
            this.mcc = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.mnc = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.categories = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.urns = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.sources = parcel.readInt();
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
        stringJoiner.add("mcc: " + java.util.Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + java.util.Objects.toString(this.mnc));
        stringJoiner.add("categories: " + this.categories);
        stringJoiner.add("urns: " + java.util.Arrays.toString(this.urns));
        stringJoiner.add("sources: " + this.sources);
        return "EmergencyNumber" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
