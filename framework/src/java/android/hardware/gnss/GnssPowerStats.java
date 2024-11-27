package android.hardware.gnss;

/* loaded from: classes2.dex */
public class GnssPowerStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.gnss.GnssPowerStats> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.GnssPowerStats>() { // from class: android.hardware.gnss.GnssPowerStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssPowerStats createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.GnssPowerStats gnssPowerStats = new android.hardware.gnss.GnssPowerStats();
            gnssPowerStats.readFromParcel(parcel);
            return gnssPowerStats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssPowerStats[] newArray(int i) {
            return new android.hardware.gnss.GnssPowerStats[i];
        }
    };
    public android.hardware.gnss.ElapsedRealtime elapsedRealtime;
    public double[] otherModesEnergyMilliJoule;
    public double totalEnergyMilliJoule = 0.0d;
    public double singlebandTrackingModeEnergyMilliJoule = 0.0d;
    public double multibandTrackingModeEnergyMilliJoule = 0.0d;
    public double singlebandAcquisitionModeEnergyMilliJoule = 0.0d;
    public double multibandAcquisitionModeEnergyMilliJoule = 0.0d;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.elapsedRealtime, i);
        parcel.writeDouble(this.totalEnergyMilliJoule);
        parcel.writeDouble(this.singlebandTrackingModeEnergyMilliJoule);
        parcel.writeDouble(this.multibandTrackingModeEnergyMilliJoule);
        parcel.writeDouble(this.singlebandAcquisitionModeEnergyMilliJoule);
        parcel.writeDouble(this.multibandAcquisitionModeEnergyMilliJoule);
        parcel.writeDoubleArray(this.otherModesEnergyMilliJoule);
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
            this.elapsedRealtime = (android.hardware.gnss.ElapsedRealtime) parcel.readTypedObject(android.hardware.gnss.ElapsedRealtime.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.totalEnergyMilliJoule = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.singlebandTrackingModeEnergyMilliJoule = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.multibandTrackingModeEnergyMilliJoule = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.singlebandAcquisitionModeEnergyMilliJoule = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.multibandAcquisitionModeEnergyMilliJoule = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.otherModesEnergyMilliJoule = parcel.createDoubleArray();
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
