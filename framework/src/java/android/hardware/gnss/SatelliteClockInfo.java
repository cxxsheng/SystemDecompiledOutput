package android.hardware.gnss;

/* loaded from: classes2.dex */
public class SatelliteClockInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.gnss.SatelliteClockInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.SatelliteClockInfo>() { // from class: android.hardware.gnss.SatelliteClockInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.SatelliteClockInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.SatelliteClockInfo satelliteClockInfo = new android.hardware.gnss.SatelliteClockInfo();
            satelliteClockInfo.readFromParcel(parcel);
            return satelliteClockInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.SatelliteClockInfo[] newArray(int i) {
            return new android.hardware.gnss.SatelliteClockInfo[i];
        }
    };
    public double satHardwareCodeBiasMeters = 0.0d;
    public double satTimeCorrectionMeters = 0.0d;
    public double satClkDriftMps = 0.0d;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.satHardwareCodeBiasMeters);
        parcel.writeDouble(this.satTimeCorrectionMeters);
        parcel.writeDouble(this.satClkDriftMps);
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
            this.satHardwareCodeBiasMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.satTimeCorrectionMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.satClkDriftMps = parcel.readDouble();
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
