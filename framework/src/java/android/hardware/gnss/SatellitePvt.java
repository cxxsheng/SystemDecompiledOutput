package android.hardware.gnss;

/* loaded from: classes2.dex */
public class SatellitePvt implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.gnss.SatellitePvt> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.SatellitePvt>() { // from class: android.hardware.gnss.SatellitePvt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.SatellitePvt createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.SatellitePvt satellitePvt = new android.hardware.gnss.SatellitePvt();
            satellitePvt.readFromParcel(parcel);
            return satellitePvt;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.SatellitePvt[] newArray(int i) {
            return new android.hardware.gnss.SatellitePvt[i];
        }
    };
    public static final int HAS_IONO = 2;
    public static final int HAS_POSITION_VELOCITY_CLOCK_INFO = 1;
    public static final int HAS_TROPO = 4;
    public android.hardware.gnss.SatelliteClockInfo satClockInfo;
    public android.hardware.gnss.SatellitePositionEcef satPosEcef;
    public android.hardware.gnss.SatelliteVelocityEcef satVelEcef;
    public int flags = 0;
    public double ionoDelayMeters = 0.0d;
    public double tropoDelayMeters = 0.0d;
    public long timeOfClockSeconds = 0;
    public int issueOfDataClock = 0;
    public long timeOfEphemerisSeconds = 0;
    public int issueOfDataEphemeris = 0;
    public int ephemerisSource = 3;

    public @interface SatelliteEphemerisSource {
        public static final int DEMODULATED = 0;
        public static final int OTHER = 3;
        public static final int SERVER_LONG_TERM = 2;
        public static final int SERVER_NORMAL = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.flags);
        parcel.writeTypedObject(this.satPosEcef, i);
        parcel.writeTypedObject(this.satVelEcef, i);
        parcel.writeTypedObject(this.satClockInfo, i);
        parcel.writeDouble(this.ionoDelayMeters);
        parcel.writeDouble(this.tropoDelayMeters);
        parcel.writeLong(this.timeOfClockSeconds);
        parcel.writeInt(this.issueOfDataClock);
        parcel.writeLong(this.timeOfEphemerisSeconds);
        parcel.writeInt(this.issueOfDataEphemeris);
        parcel.writeInt(this.ephemerisSource);
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
            this.flags = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.satPosEcef = (android.hardware.gnss.SatellitePositionEcef) parcel.readTypedObject(android.hardware.gnss.SatellitePositionEcef.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.satVelEcef = (android.hardware.gnss.SatelliteVelocityEcef) parcel.readTypedObject(android.hardware.gnss.SatelliteVelocityEcef.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.satClockInfo = (android.hardware.gnss.SatelliteClockInfo) parcel.readTypedObject(android.hardware.gnss.SatelliteClockInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ionoDelayMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.tropoDelayMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timeOfClockSeconds = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.issueOfDataClock = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timeOfEphemerisSeconds = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.issueOfDataEphemeris = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.ephemerisSource = parcel.readInt();
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
        return describeContents(this.satPosEcef) | 0 | describeContents(this.satVelEcef) | describeContents(this.satClockInfo);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
