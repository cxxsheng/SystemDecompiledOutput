package android.hardware.gnss;

/* loaded from: classes2.dex */
public class GnssSignalType implements android.os.Parcelable {
    public static final java.lang.String CODE_TYPE_A = "A";
    public static final java.lang.String CODE_TYPE_B = "B";
    public static final java.lang.String CODE_TYPE_C = "C";
    public static final java.lang.String CODE_TYPE_D = "D";
    public static final java.lang.String CODE_TYPE_I = "I";
    public static final java.lang.String CODE_TYPE_L = "L";
    public static final java.lang.String CODE_TYPE_M = "M";
    public static final java.lang.String CODE_TYPE_N = "N";
    public static final java.lang.String CODE_TYPE_P = "P";
    public static final java.lang.String CODE_TYPE_Q = "Q";
    public static final java.lang.String CODE_TYPE_S = "S";
    public static final java.lang.String CODE_TYPE_UNKNOWN = "UNKNOWN";
    public static final java.lang.String CODE_TYPE_W = "W";
    public static final java.lang.String CODE_TYPE_X = "X";
    public static final java.lang.String CODE_TYPE_Y = "Y";
    public static final java.lang.String CODE_TYPE_Z = "Z";
    public static final android.os.Parcelable.Creator<android.hardware.gnss.GnssSignalType> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.GnssSignalType>() { // from class: android.hardware.gnss.GnssSignalType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssSignalType createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.GnssSignalType gnssSignalType = new android.hardware.gnss.GnssSignalType();
            gnssSignalType.readFromParcel(parcel);
            return gnssSignalType;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssSignalType[] newArray(int i) {
            return new android.hardware.gnss.GnssSignalType[i];
        }
    };
    public java.lang.String codeType;
    public int constellation = 0;
    public double carrierFrequencyHz = 0.0d;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.constellation);
        parcel.writeDouble(this.carrierFrequencyHz);
        parcel.writeString(this.codeType);
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
            this.constellation = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.carrierFrequencyHz = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.codeType = parcel.readString();
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
