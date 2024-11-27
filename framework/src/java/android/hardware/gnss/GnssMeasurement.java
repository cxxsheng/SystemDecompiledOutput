package android.hardware.gnss;

/* loaded from: classes2.dex */
public class GnssMeasurement implements android.os.Parcelable {
    public static final int ADR_STATE_CYCLE_SLIP = 4;
    public static final int ADR_STATE_HALF_CYCLE_RESOLVED = 8;
    public static final int ADR_STATE_RESET = 2;
    public static final int ADR_STATE_UNKNOWN = 0;
    public static final int ADR_STATE_VALID = 1;
    public static final android.os.Parcelable.Creator<android.hardware.gnss.GnssMeasurement> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.GnssMeasurement>() { // from class: android.hardware.gnss.GnssMeasurement.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssMeasurement createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.GnssMeasurement gnssMeasurement = new android.hardware.gnss.GnssMeasurement();
            gnssMeasurement.readFromParcel(parcel);
            return gnssMeasurement;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.GnssMeasurement[] newArray(int i) {
            return new android.hardware.gnss.GnssMeasurement[i];
        }
    };
    public static final int HAS_AUTOMATIC_GAIN_CONTROL = 8192;
    public static final int HAS_CARRIER_CYCLES = 1024;
    public static final int HAS_CARRIER_FREQUENCY = 512;
    public static final int HAS_CARRIER_PHASE = 2048;
    public static final int HAS_CARRIER_PHASE_UNCERTAINTY = 4096;
    public static final int HAS_CORRELATION_VECTOR = 2097152;
    public static final int HAS_FULL_ISB = 65536;
    public static final int HAS_FULL_ISB_UNCERTAINTY = 131072;
    public static final int HAS_SATELLITE_ISB = 262144;
    public static final int HAS_SATELLITE_ISB_UNCERTAINTY = 524288;
    public static final int HAS_SATELLITE_PVT = 1048576;
    public static final int HAS_SNR = 1;
    public static final int STATE_2ND_CODE_LOCK = 65536;
    public static final int STATE_BDS_D2_BIT_SYNC = 256;
    public static final int STATE_BDS_D2_SUBFRAME_SYNC = 512;
    public static final int STATE_BIT_SYNC = 2;
    public static final int STATE_CODE_LOCK = 1;
    public static final int STATE_GAL_E1BC_CODE_LOCK = 1024;
    public static final int STATE_GAL_E1B_PAGE_SYNC = 4096;
    public static final int STATE_GAL_E1C_2ND_CODE_LOCK = 2048;
    public static final int STATE_GLO_STRING_SYNC = 64;
    public static final int STATE_GLO_TOD_DECODED = 128;
    public static final int STATE_GLO_TOD_KNOWN = 32768;
    public static final int STATE_MSEC_AMBIGUOUS = 16;
    public static final int STATE_SBAS_SYNC = 8192;
    public static final int STATE_SUBFRAME_SYNC = 4;
    public static final int STATE_SYMBOL_SYNC = 32;
    public static final int STATE_TOW_DECODED = 8;
    public static final int STATE_TOW_KNOWN = 16384;
    public static final int STATE_UNKNOWN = 0;
    public android.hardware.gnss.CorrelationVector[] correlationVectors;
    public android.hardware.gnss.SatellitePvt satellitePvt;
    public android.hardware.gnss.GnssSignalType signalType;
    public int flags = 0;
    public int svid = 0;
    public double timeOffsetNs = 0.0d;
    public int state = 0;
    public long receivedSvTimeInNs = 0;
    public long receivedSvTimeUncertaintyInNs = 0;
    public double antennaCN0DbHz = 0.0d;
    public double basebandCN0DbHz = 0.0d;
    public double pseudorangeRateMps = 0.0d;
    public double pseudorangeRateUncertaintyMps = 0.0d;
    public int accumulatedDeltaRangeState = 0;
    public double accumulatedDeltaRangeM = 0.0d;
    public double accumulatedDeltaRangeUncertaintyM = 0.0d;
    public long carrierCycles = 0;
    public double carrierPhase = 0.0d;
    public double carrierPhaseUncertainty = 0.0d;
    public int multipathIndicator = 0;
    public double snrDb = 0.0d;
    public double agcLevelDb = 0.0d;
    public double fullInterSignalBiasNs = 0.0d;
    public double fullInterSignalBiasUncertaintyNs = 0.0d;
    public double satelliteInterSignalBiasNs = 0.0d;
    public double satelliteInterSignalBiasUncertaintyNs = 0.0d;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.svid);
        parcel.writeTypedObject(this.signalType, i);
        parcel.writeDouble(this.timeOffsetNs);
        parcel.writeInt(this.state);
        parcel.writeLong(this.receivedSvTimeInNs);
        parcel.writeLong(this.receivedSvTimeUncertaintyInNs);
        parcel.writeDouble(this.antennaCN0DbHz);
        parcel.writeDouble(this.basebandCN0DbHz);
        parcel.writeDouble(this.pseudorangeRateMps);
        parcel.writeDouble(this.pseudorangeRateUncertaintyMps);
        parcel.writeInt(this.accumulatedDeltaRangeState);
        parcel.writeDouble(this.accumulatedDeltaRangeM);
        parcel.writeDouble(this.accumulatedDeltaRangeUncertaintyM);
        parcel.writeLong(this.carrierCycles);
        parcel.writeDouble(this.carrierPhase);
        parcel.writeDouble(this.carrierPhaseUncertainty);
        parcel.writeInt(this.multipathIndicator);
        parcel.writeDouble(this.snrDb);
        parcel.writeDouble(this.agcLevelDb);
        parcel.writeDouble(this.fullInterSignalBiasNs);
        parcel.writeDouble(this.fullInterSignalBiasUncertaintyNs);
        parcel.writeDouble(this.satelliteInterSignalBiasNs);
        parcel.writeDouble(this.satelliteInterSignalBiasUncertaintyNs);
        parcel.writeTypedObject(this.satellitePvt, i);
        parcel.writeTypedArray(this.correlationVectors, i);
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
            this.svid = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.signalType = (android.hardware.gnss.GnssSignalType) parcel.readTypedObject(android.hardware.gnss.GnssSignalType.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timeOffsetNs = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.state = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.receivedSvTimeInNs = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.receivedSvTimeUncertaintyInNs = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.antennaCN0DbHz = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.basebandCN0DbHz = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pseudorangeRateMps = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pseudorangeRateUncertaintyMps = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.accumulatedDeltaRangeState = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.accumulatedDeltaRangeM = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.accumulatedDeltaRangeUncertaintyM = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.carrierCycles = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.carrierPhase = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.carrierPhaseUncertainty = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.multipathIndicator = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.snrDb = parcel.readDouble();
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
            this.fullInterSignalBiasNs = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.fullInterSignalBiasUncertaintyNs = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.satelliteInterSignalBiasNs = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.satelliteInterSignalBiasUncertaintyNs = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.satellitePvt = (android.hardware.gnss.SatellitePvt) parcel.readTypedObject(android.hardware.gnss.SatellitePvt.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.correlationVectors = (android.hardware.gnss.CorrelationVector[]) parcel.createTypedArray(android.hardware.gnss.CorrelationVector.CREATOR);
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
        return describeContents(this.signalType) | 0 | describeContents(this.satellitePvt) | describeContents(this.correlationVectors);
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
}
