package android.hardware.gnss;

/* loaded from: classes2.dex */
public class GnssData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.gnss.GnssData> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.GnssData>() { // from class: android.hardware.gnss.GnssData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssData createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.GnssData gnssData = new android.hardware.gnss.GnssData();
            gnssData.readFromParcel(parcel);
            return gnssData;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssData[] newArray(int i) {
            return new android.hardware.gnss.GnssData[i];
        }
    };
    public android.hardware.gnss.GnssClock clock;
    public android.hardware.gnss.ElapsedRealtime elapsedRealtime;
    public android.hardware.gnss.GnssData.GnssAgc[] gnssAgcs = new android.hardware.gnss.GnssData.GnssAgc[0];
    public android.hardware.gnss.GnssMeasurement[] measurements;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.measurements, i);
        parcel.writeTypedObject(this.clock, i);
        parcel.writeTypedObject(this.elapsedRealtime, i);
        parcel.writeTypedArray(this.gnssAgcs, i);
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
            this.measurements = (android.hardware.gnss.GnssMeasurement[]) parcel.createTypedArray(android.hardware.gnss.GnssMeasurement.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.clock = (android.hardware.gnss.GnssClock) parcel.readTypedObject(android.hardware.gnss.GnssClock.CREATOR);
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
            } else {
                this.gnssAgcs = (android.hardware.gnss.GnssData.GnssAgc[]) parcel.createTypedArray(android.hardware.gnss.GnssData.GnssAgc.CREATOR);
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
        return describeContents(this.measurements) | 0 | describeContents(this.clock) | describeContents(this.elapsedRealtime) | describeContents(this.gnssAgcs);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }

    public static class GnssAgc implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.GnssData.GnssAgc> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.GnssData.GnssAgc>() { // from class: android.hardware.gnss.GnssData.GnssAgc.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.GnssData.GnssAgc createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.GnssData.GnssAgc gnssAgc = new android.hardware.gnss.GnssData.GnssAgc();
                gnssAgc.readFromParcel(parcel);
                return gnssAgc;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.GnssData.GnssAgc[] newArray(int i) {
                return new android.hardware.gnss.GnssData.GnssAgc[i];
            }
        };
        public double agcLevelDb = 0.0d;
        public int constellation = 0;
        public long carrierFrequencyHz = 0;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeDouble(this.agcLevelDb);
            parcel.writeInt(this.constellation);
            parcel.writeLong(this.carrierFrequencyHz);
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
                this.agcLevelDb = parcel.readDouble();
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
                } else {
                    this.carrierFrequencyHz = parcel.readLong();
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
}
