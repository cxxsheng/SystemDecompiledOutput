package android.hardware.gnss;

/* loaded from: classes2.dex */
public class GnssLocation implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.gnss.GnssLocation> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.GnssLocation>() { // from class: android.hardware.gnss.GnssLocation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssLocation createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.GnssLocation gnssLocation = new android.hardware.gnss.GnssLocation();
            gnssLocation.readFromParcel(parcel);
            return gnssLocation;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssLocation[] newArray(int i) {
            return new android.hardware.gnss.GnssLocation[i];
        }
    };
    public static final int HAS_ALTITUDE = 2;
    public static final int HAS_BEARING = 8;
    public static final int HAS_BEARING_ACCURACY = 128;
    public static final int HAS_HORIZONTAL_ACCURACY = 16;
    public static final int HAS_LAT_LONG = 1;
    public static final int HAS_SPEED = 4;
    public static final int HAS_SPEED_ACCURACY = 64;
    public static final int HAS_VERTICAL_ACCURACY = 32;
    public android.hardware.gnss.ElapsedRealtime elapsedRealtime;
    public int gnssLocationFlags = 0;
    public double latitudeDegrees = 0.0d;
    public double longitudeDegrees = 0.0d;
    public double altitudeMeters = 0.0d;
    public double speedMetersPerSec = 0.0d;
    public double bearingDegrees = 0.0d;
    public double horizontalAccuracyMeters = 0.0d;
    public double verticalAccuracyMeters = 0.0d;
    public double speedAccuracyMetersPerSecond = 0.0d;
    public double bearingAccuracyDegrees = 0.0d;
    public long timestampMillis = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.gnssLocationFlags);
        parcel.writeDouble(this.latitudeDegrees);
        parcel.writeDouble(this.longitudeDegrees);
        parcel.writeDouble(this.altitudeMeters);
        parcel.writeDouble(this.speedMetersPerSec);
        parcel.writeDouble(this.bearingDegrees);
        parcel.writeDouble(this.horizontalAccuracyMeters);
        parcel.writeDouble(this.verticalAccuracyMeters);
        parcel.writeDouble(this.speedAccuracyMetersPerSecond);
        parcel.writeDouble(this.bearingAccuracyDegrees);
        parcel.writeLong(this.timestampMillis);
        parcel.writeTypedObject(this.elapsedRealtime, i);
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
            this.gnssLocationFlags = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.latitudeDegrees = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.longitudeDegrees = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.altitudeMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.speedMetersPerSec = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.bearingDegrees = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.horizontalAccuracyMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.verticalAccuracyMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.speedAccuracyMetersPerSecond = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.bearingAccuracyDegrees = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timestampMillis = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.elapsedRealtime = (android.hardware.gnss.ElapsedRealtime) parcel.readTypedObject(android.hardware.gnss.ElapsedRealtime.CREATOR);
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
        return describeContents(this.elapsedRealtime) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
