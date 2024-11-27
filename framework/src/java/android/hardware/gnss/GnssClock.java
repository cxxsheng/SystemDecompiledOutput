package android.hardware.gnss;

/* loaded from: classes2.dex */
public class GnssClock implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.gnss.GnssClock> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.GnssClock>() { // from class: android.hardware.gnss.GnssClock.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssClock createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.GnssClock gnssClock = new android.hardware.gnss.GnssClock();
            gnssClock.readFromParcel(parcel);
            return gnssClock;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssClock[] newArray(int i) {
            return new android.hardware.gnss.GnssClock[i];
        }
    };
    public static final int HAS_BIAS = 8;
    public static final int HAS_BIAS_UNCERTAINTY = 16;
    public static final int HAS_DRIFT = 32;
    public static final int HAS_DRIFT_UNCERTAINTY = 64;
    public static final int HAS_FULL_BIAS = 4;
    public static final int HAS_LEAP_SECOND = 1;
    public static final int HAS_TIME_UNCERTAINTY = 2;
    public android.hardware.gnss.GnssSignalType referenceSignalTypeForIsb;
    public int gnssClockFlags = 0;
    public int leapSecond = 0;
    public long timeNs = 0;
    public double timeUncertaintyNs = 0.0d;
    public long fullBiasNs = 0;
    public double biasNs = 0.0d;
    public double biasUncertaintyNs = 0.0d;
    public double driftNsps = 0.0d;
    public double driftUncertaintyNsps = 0.0d;
    public int hwClockDiscontinuityCount = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.gnssClockFlags);
        parcel.writeInt(this.leapSecond);
        parcel.writeLong(this.timeNs);
        parcel.writeDouble(this.timeUncertaintyNs);
        parcel.writeLong(this.fullBiasNs);
        parcel.writeDouble(this.biasNs);
        parcel.writeDouble(this.biasUncertaintyNs);
        parcel.writeDouble(this.driftNsps);
        parcel.writeDouble(this.driftUncertaintyNsps);
        parcel.writeInt(this.hwClockDiscontinuityCount);
        parcel.writeTypedObject(this.referenceSignalTypeForIsb, i);
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
            this.gnssClockFlags = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.leapSecond = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timeNs = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timeUncertaintyNs = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.fullBiasNs = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.biasNs = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.biasUncertaintyNs = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.driftNsps = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.driftUncertaintyNsps = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.hwClockDiscontinuityCount = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.referenceSignalTypeForIsb = (android.hardware.gnss.GnssSignalType) parcel.readTypedObject(android.hardware.gnss.GnssSignalType.CREATOR);
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
        return describeContents(this.referenceSignalTypeForIsb) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
