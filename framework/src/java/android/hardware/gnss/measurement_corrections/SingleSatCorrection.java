package android.hardware.gnss.measurement_corrections;

/* loaded from: classes2.dex */
public class SingleSatCorrection implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.gnss.measurement_corrections.SingleSatCorrection> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.measurement_corrections.SingleSatCorrection>() { // from class: android.hardware.gnss.measurement_corrections.SingleSatCorrection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.measurement_corrections.SingleSatCorrection createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.measurement_corrections.SingleSatCorrection singleSatCorrection = new android.hardware.gnss.measurement_corrections.SingleSatCorrection();
            singleSatCorrection.readFromParcel(parcel);
            return singleSatCorrection;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.measurement_corrections.SingleSatCorrection[] newArray(int i) {
            return new android.hardware.gnss.measurement_corrections.SingleSatCorrection[i];
        }
    };
    public static final int SINGLE_SAT_CORRECTION_HAS_COMBINED_ATTENUATION = 16;
    public static final int SINGLE_SAT_CORRECTION_HAS_COMBINED_EXCESS_PATH_LENGTH = 2;
    public static final int SINGLE_SAT_CORRECTION_HAS_COMBINED_EXCESS_PATH_LENGTH_UNC = 4;
    public static final int SINGLE_SAT_CORRECTION_HAS_SAT_IS_LOS_PROBABILITY = 1;
    public int constellation;
    public android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo[] excessPathInfos;
    public int singleSatCorrectionFlags = 0;
    public int svid = 0;
    public long carrierFrequencyHz = 0;
    public float probSatIsLos = 0.0f;
    public float combinedExcessPathLengthMeters = 0.0f;
    public float combinedExcessPathLengthUncertaintyMeters = 0.0f;
    public float combinedAttenuationDb = 0.0f;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.singleSatCorrectionFlags);
        parcel.writeInt(this.constellation);
        parcel.writeInt(this.svid);
        parcel.writeLong(this.carrierFrequencyHz);
        parcel.writeFloat(this.probSatIsLos);
        parcel.writeFloat(this.combinedExcessPathLengthMeters);
        parcel.writeFloat(this.combinedExcessPathLengthUncertaintyMeters);
        parcel.writeFloat(this.combinedAttenuationDb);
        parcel.writeTypedArray(this.excessPathInfos, i);
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
            this.singleSatCorrectionFlags = parcel.readInt();
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
            this.svid = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.carrierFrequencyHz = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.probSatIsLos = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.combinedExcessPathLengthMeters = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.combinedExcessPathLengthUncertaintyMeters = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.combinedAttenuationDb = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.excessPathInfos = (android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo[]) parcel.createTypedArray(android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo.CREATOR);
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
        return describeContents(this.excessPathInfos) | 0;
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

    public static class ExcessPathInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo>() { // from class: android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo excessPathInfo = new android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo();
                excessPathInfo.readFromParcel(parcel);
                return excessPathInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo[] newArray(int i) {
                return new android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo[i];
            }
        };
        public static final int EXCESS_PATH_INFO_HAS_ATTENUATION = 8;
        public static final int EXCESS_PATH_INFO_HAS_EXCESS_PATH_LENGTH = 1;
        public static final int EXCESS_PATH_INFO_HAS_EXCESS_PATH_LENGTH_UNC = 2;
        public static final int EXCESS_PATH_INFO_HAS_REFLECTING_PLANE = 4;
        public android.hardware.gnss.measurement_corrections.ReflectingPlane reflectingPlane;
        public int excessPathInfoFlags = 0;
        public float excessPathLengthMeters = 0.0f;
        public float excessPathLengthUncertaintyMeters = 0.0f;
        public float attenuationDb = 0.0f;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.excessPathInfoFlags);
            parcel.writeFloat(this.excessPathLengthMeters);
            parcel.writeFloat(this.excessPathLengthUncertaintyMeters);
            parcel.writeTypedObject(this.reflectingPlane, i);
            parcel.writeFloat(this.attenuationDb);
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
                this.excessPathInfoFlags = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.excessPathLengthMeters = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.excessPathLengthUncertaintyMeters = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.reflectingPlane = (android.hardware.gnss.measurement_corrections.ReflectingPlane) parcel.readTypedObject(android.hardware.gnss.measurement_corrections.ReflectingPlane.CREATOR);
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.attenuationDb = parcel.readFloat();
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
            return describeContents(this.reflectingPlane) | 0;
        }

        private int describeContents(java.lang.Object obj) {
            if (obj == null || !(obj instanceof android.os.Parcelable)) {
                return 0;
            }
            return ((android.os.Parcelable) obj).describeContents();
        }
    }
}
