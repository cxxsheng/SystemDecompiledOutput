package android.hardware.gnss.V1_0;

/* loaded from: classes2.dex */
public interface IGnssMeasurementCallback extends android.internal.hidl.base.V1_0.IBase {
    public static final java.lang.String kInterfaceName = "android.hardware.gnss@1.0::IGnssMeasurementCallback";

    void GnssMeasurementCb(android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData gnssData) throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.gnss.V1_0.IGnssMeasurementCallback asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.V1_0.IGnssMeasurementCallback)) {
            return (android.hardware.gnss.V1_0.IGnssMeasurementCallback) queryLocalInterface;
        }
        android.hardware.gnss.V1_0.IGnssMeasurementCallback.Proxy proxy = new android.hardware.gnss.V1_0.IGnssMeasurementCallback.Proxy(iHwBinder);
        try {
            java.util.Iterator<java.lang.String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (android.os.RemoteException e) {
        }
        return null;
    }

    static android.hardware.gnss.V1_0.IGnssMeasurementCallback castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.gnss.V1_0.IGnssMeasurementCallback getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.gnss.V1_0.IGnssMeasurementCallback getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V1_0.IGnssMeasurementCallback getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V1_0.IGnssMeasurementCallback getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class GnssClockFlags {
        public static final short HAS_BIAS = 8;
        public static final short HAS_BIAS_UNCERTAINTY = 16;
        public static final short HAS_DRIFT = 32;
        public static final short HAS_DRIFT_UNCERTAINTY = 64;
        public static final short HAS_FULL_BIAS = 4;
        public static final short HAS_LEAP_SECOND = 1;
        public static final short HAS_TIME_UNCERTAINTY = 2;

        public static final java.lang.String toString(short s) {
            if (s == 1) {
                return "HAS_LEAP_SECOND";
            }
            if (s == 2) {
                return "HAS_TIME_UNCERTAINTY";
            }
            if (s == 4) {
                return "HAS_FULL_BIAS";
            }
            if (s == 8) {
                return "HAS_BIAS";
            }
            if (s == 16) {
                return "HAS_BIAS_UNCERTAINTY";
            }
            if (s == 32) {
                return "HAS_DRIFT";
            }
            if (s == 64) {
                return "HAS_DRIFT_UNCERTAINTY";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt(s));
        }

        public static final java.lang.String dumpBitfield(short s) {
            short s2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if ((s & 1) != 1) {
                s2 = 0;
            } else {
                arrayList.add("HAS_LEAP_SECOND");
                s2 = (short) 1;
            }
            if ((s & 2) == 2) {
                arrayList.add("HAS_TIME_UNCERTAINTY");
                s2 = (short) (s2 | 2);
            }
            if ((s & 4) == 4) {
                arrayList.add("HAS_FULL_BIAS");
                s2 = (short) (s2 | 4);
            }
            if ((s & 8) == 8) {
                arrayList.add("HAS_BIAS");
                s2 = (short) (s2 | 8);
            }
            if ((s & 16) == 16) {
                arrayList.add("HAS_BIAS_UNCERTAINTY");
                s2 = (short) (s2 | 16);
            }
            if ((s & 32) == 32) {
                arrayList.add("HAS_DRIFT");
                s2 = (short) (s2 | 32);
            }
            if ((s & 64) == 64) {
                arrayList.add("HAS_DRIFT_UNCERTAINTY");
                s2 = (short) (s2 | 64);
            }
            if (s != s2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt((short) (s & (~s2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class GnssMeasurementFlags {
        public static final int HAS_AUTOMATIC_GAIN_CONTROL = 8192;
        public static final int HAS_CARRIER_CYCLES = 1024;
        public static final int HAS_CARRIER_FREQUENCY = 512;
        public static final int HAS_CARRIER_PHASE = 2048;
        public static final int HAS_CARRIER_PHASE_UNCERTAINTY = 4096;
        public static final int HAS_SNR = 1;

        public static final java.lang.String toString(int i) {
            if (i == 1) {
                return "HAS_SNR";
            }
            if (i == 512) {
                return "HAS_CARRIER_FREQUENCY";
            }
            if (i == 1024) {
                return "HAS_CARRIER_CYCLES";
            }
            if (i == 2048) {
                return "HAS_CARRIER_PHASE";
            }
            if (i == 4096) {
                return "HAS_CARRIER_PHASE_UNCERTAINTY";
            }
            if (i == 8192) {
                return "HAS_AUTOMATIC_GAIN_CONTROL";
            }
            return "0x" + java.lang.Integer.toHexString(i);
        }

        public static final java.lang.String dumpBitfield(int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int i2 = 1;
            if ((i & 1) != 1) {
                i2 = 0;
            } else {
                arrayList.add("HAS_SNR");
            }
            if ((i & 512) == 512) {
                arrayList.add("HAS_CARRIER_FREQUENCY");
                i2 |= 512;
            }
            if ((i & 1024) == 1024) {
                arrayList.add("HAS_CARRIER_CYCLES");
                i2 |= 1024;
            }
            if ((i & 2048) == 2048) {
                arrayList.add("HAS_CARRIER_PHASE");
                i2 |= 2048;
            }
            if ((i & 4096) == 4096) {
                arrayList.add("HAS_CARRIER_PHASE_UNCERTAINTY");
                i2 |= 4096;
            }
            if ((i & 8192) == 8192) {
                arrayList.add("HAS_AUTOMATIC_GAIN_CONTROL");
                i2 |= 8192;
            }
            if (i != i2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class GnssMultipathIndicator {
        public static final byte INDICATIOR_NOT_PRESENT = 2;
        public static final byte INDICATOR_PRESENT = 1;
        public static final byte INDICATOR_UNKNOWN = 0;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return "INDICATOR_UNKNOWN";
            }
            if (b == 1) {
                return "INDICATOR_PRESENT";
            }
            if (b == 2) {
                return "INDICATIOR_NOT_PRESENT";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("INDICATOR_UNKNOWN");
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("INDICATOR_PRESENT");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("INDICATIOR_NOT_PRESENT");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class GnssMeasurementState {
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

        public static final java.lang.String toString(int i) {
            if (i == 0) {
                return "STATE_UNKNOWN";
            }
            if (i == 1) {
                return "STATE_CODE_LOCK";
            }
            if (i == 2) {
                return "STATE_BIT_SYNC";
            }
            if (i == 4) {
                return "STATE_SUBFRAME_SYNC";
            }
            if (i == 8) {
                return "STATE_TOW_DECODED";
            }
            if (i == 16) {
                return "STATE_MSEC_AMBIGUOUS";
            }
            if (i == 32) {
                return "STATE_SYMBOL_SYNC";
            }
            if (i == 64) {
                return "STATE_GLO_STRING_SYNC";
            }
            if (i == 128) {
                return "STATE_GLO_TOD_DECODED";
            }
            if (i == 256) {
                return "STATE_BDS_D2_BIT_SYNC";
            }
            if (i == 512) {
                return "STATE_BDS_D2_SUBFRAME_SYNC";
            }
            if (i == 1024) {
                return "STATE_GAL_E1BC_CODE_LOCK";
            }
            if (i == 2048) {
                return "STATE_GAL_E1C_2ND_CODE_LOCK";
            }
            if (i == 4096) {
                return "STATE_GAL_E1B_PAGE_SYNC";
            }
            if (i == 8192) {
                return "STATE_SBAS_SYNC";
            }
            if (i == 16384) {
                return "STATE_TOW_KNOWN";
            }
            if (i == 32768) {
                return "STATE_GLO_TOD_KNOWN";
            }
            return "0x" + java.lang.Integer.toHexString(i);
        }

        public static final java.lang.String dumpBitfield(int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("STATE_UNKNOWN");
            int i2 = 1;
            if ((i & 1) != 1) {
                i2 = 0;
            } else {
                arrayList.add("STATE_CODE_LOCK");
            }
            if ((i & 2) == 2) {
                arrayList.add("STATE_BIT_SYNC");
                i2 |= 2;
            }
            if ((i & 4) == 4) {
                arrayList.add("STATE_SUBFRAME_SYNC");
                i2 |= 4;
            }
            if ((i & 8) == 8) {
                arrayList.add("STATE_TOW_DECODED");
                i2 |= 8;
            }
            if ((i & 16) == 16) {
                arrayList.add("STATE_MSEC_AMBIGUOUS");
                i2 |= 16;
            }
            if ((i & 32) == 32) {
                arrayList.add("STATE_SYMBOL_SYNC");
                i2 |= 32;
            }
            if ((i & 64) == 64) {
                arrayList.add("STATE_GLO_STRING_SYNC");
                i2 |= 64;
            }
            if ((i & 128) == 128) {
                arrayList.add("STATE_GLO_TOD_DECODED");
                i2 |= 128;
            }
            if ((i & 256) == 256) {
                arrayList.add("STATE_BDS_D2_BIT_SYNC");
                i2 |= 256;
            }
            if ((i & 512) == 512) {
                arrayList.add("STATE_BDS_D2_SUBFRAME_SYNC");
                i2 |= 512;
            }
            if ((i & 1024) == 1024) {
                arrayList.add("STATE_GAL_E1BC_CODE_LOCK");
                i2 |= 1024;
            }
            if ((i & 2048) == 2048) {
                arrayList.add("STATE_GAL_E1C_2ND_CODE_LOCK");
                i2 |= 2048;
            }
            if ((i & 4096) == 4096) {
                arrayList.add("STATE_GAL_E1B_PAGE_SYNC");
                i2 |= 4096;
            }
            if ((i & 8192) == 8192) {
                arrayList.add("STATE_SBAS_SYNC");
                i2 |= 8192;
            }
            if ((i & 16384) == 16384) {
                arrayList.add("STATE_TOW_KNOWN");
                i2 |= 16384;
            }
            if ((i & 32768) == 32768) {
                arrayList.add("STATE_GLO_TOD_KNOWN");
                i2 |= 32768;
            }
            if (i != i2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class GnssAccumulatedDeltaRangeState {
        public static final short ADR_STATE_CYCLE_SLIP = 4;
        public static final short ADR_STATE_RESET = 2;
        public static final short ADR_STATE_UNKNOWN = 0;
        public static final short ADR_STATE_VALID = 1;

        public static final java.lang.String toString(short s) {
            if (s == 0) {
                return "ADR_STATE_UNKNOWN";
            }
            if (s == 1) {
                return "ADR_STATE_VALID";
            }
            if (s == 2) {
                return "ADR_STATE_RESET";
            }
            if (s == 4) {
                return "ADR_STATE_CYCLE_SLIP";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt(s));
        }

        public static final java.lang.String dumpBitfield(short s) {
            short s2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("ADR_STATE_UNKNOWN");
            if ((s & 1) != 1) {
                s2 = 0;
            } else {
                arrayList.add("ADR_STATE_VALID");
                s2 = (short) 1;
            }
            if ((s & 2) == 2) {
                arrayList.add("ADR_STATE_RESET");
                s2 = (short) (s2 | 2);
            }
            if ((s & 4) == 4) {
                arrayList.add("ADR_STATE_CYCLE_SLIP");
                s2 = (short) (s2 | 4);
            }
            if (s != s2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt((short) (s & (~s2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class GnssClock {
        public short gnssClockFlags;
        public short leapSecond = 0;
        public long timeNs = 0;
        public double timeUncertaintyNs = 0.0d;
        public long fullBiasNs = 0;
        public double biasNs = 0.0d;
        public double biasUncertaintyNs = 0.0d;
        public double driftNsps = 0.0d;
        public double driftUncertaintyNsps = 0.0d;
        public int hwClockDiscontinuityCount = 0;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock gnssClock = (android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock) obj;
            if (android.os.HidlSupport.deepEquals(java.lang.Short.valueOf(this.gnssClockFlags), java.lang.Short.valueOf(gnssClock.gnssClockFlags)) && this.leapSecond == gnssClock.leapSecond && this.timeNs == gnssClock.timeNs && this.timeUncertaintyNs == gnssClock.timeUncertaintyNs && this.fullBiasNs == gnssClock.fullBiasNs && this.biasNs == gnssClock.biasNs && this.biasUncertaintyNs == gnssClock.biasUncertaintyNs && this.driftNsps == gnssClock.driftNsps && this.driftUncertaintyNsps == gnssClock.driftUncertaintyNsps && this.hwClockDiscontinuityCount == gnssClock.hwClockDiscontinuityCount) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.gnssClockFlags))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.leapSecond))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.timeNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.timeUncertaintyNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.fullBiasNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.biasNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.biasUncertaintyNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.driftNsps))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.driftUncertaintyNsps))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.hwClockDiscontinuityCount))));
        }

        public final java.lang.String toString() {
            return "{.gnssClockFlags = " + android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClockFlags.dumpBitfield(this.gnssClockFlags) + ", .leapSecond = " + ((int) this.leapSecond) + ", .timeNs = " + this.timeNs + ", .timeUncertaintyNs = " + this.timeUncertaintyNs + ", .fullBiasNs = " + this.fullBiasNs + ", .biasNs = " + this.biasNs + ", .biasUncertaintyNs = " + this.biasUncertaintyNs + ", .driftNsps = " + this.driftNsps + ", .driftUncertaintyNsps = " + this.driftUncertaintyNsps + ", .hwClockDiscontinuityCount = " + this.hwClockDiscontinuityCount + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock gnssClock = new android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock();
                gnssClock.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
                arrayList.add(gnssClock);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.gnssClockFlags = hwBlob.getInt16(0 + j);
            this.leapSecond = hwBlob.getInt16(2 + j);
            this.timeNs = hwBlob.getInt64(8 + j);
            this.timeUncertaintyNs = hwBlob.getDouble(16 + j);
            this.fullBiasNs = hwBlob.getInt64(24 + j);
            this.biasNs = hwBlob.getDouble(32 + j);
            this.biasUncertaintyNs = hwBlob.getDouble(40 + j);
            this.driftNsps = hwBlob.getDouble(48 + j);
            this.driftUncertaintyNsps = hwBlob.getDouble(56 + j);
            this.hwClockDiscontinuityCount = hwBlob.getInt32(j + 64);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(72);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 72);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt16(0 + j, this.gnssClockFlags);
            hwBlob.putInt16(2 + j, this.leapSecond);
            hwBlob.putInt64(8 + j, this.timeNs);
            hwBlob.putDouble(16 + j, this.timeUncertaintyNs);
            hwBlob.putInt64(24 + j, this.fullBiasNs);
            hwBlob.putDouble(32 + j, this.biasNs);
            hwBlob.putDouble(40 + j, this.biasUncertaintyNs);
            hwBlob.putDouble(48 + j, this.driftNsps);
            hwBlob.putDouble(56 + j, this.driftUncertaintyNsps);
            hwBlob.putInt32(j + 64, this.hwClockDiscontinuityCount);
        }
    }

    public static final class GnssMeasurement {
        public short accumulatedDeltaRangeState;
        public int flags;
        public int state;
        public short svid = 0;
        public byte constellation = 0;
        public double timeOffsetNs = 0.0d;
        public long receivedSvTimeInNs = 0;
        public long receivedSvTimeUncertaintyInNs = 0;
        public double cN0DbHz = 0.0d;
        public double pseudorangeRateMps = 0.0d;
        public double pseudorangeRateUncertaintyMps = 0.0d;
        public double accumulatedDeltaRangeM = 0.0d;
        public double accumulatedDeltaRangeUncertaintyM = 0.0d;
        public float carrierFrequencyHz = 0.0f;
        public long carrierCycles = 0;
        public double carrierPhase = 0.0d;
        public double carrierPhaseUncertainty = 0.0d;
        public byte multipathIndicator = 0;
        public double snrDb = 0.0d;
        public double agcLevelDb = 0.0d;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement gnssMeasurement = (android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement) obj;
            if (android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.flags), java.lang.Integer.valueOf(gnssMeasurement.flags)) && this.svid == gnssMeasurement.svid && this.constellation == gnssMeasurement.constellation && this.timeOffsetNs == gnssMeasurement.timeOffsetNs && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.state), java.lang.Integer.valueOf(gnssMeasurement.state)) && this.receivedSvTimeInNs == gnssMeasurement.receivedSvTimeInNs && this.receivedSvTimeUncertaintyInNs == gnssMeasurement.receivedSvTimeUncertaintyInNs && this.cN0DbHz == gnssMeasurement.cN0DbHz && this.pseudorangeRateMps == gnssMeasurement.pseudorangeRateMps && this.pseudorangeRateUncertaintyMps == gnssMeasurement.pseudorangeRateUncertaintyMps && android.os.HidlSupport.deepEquals(java.lang.Short.valueOf(this.accumulatedDeltaRangeState), java.lang.Short.valueOf(gnssMeasurement.accumulatedDeltaRangeState)) && this.accumulatedDeltaRangeM == gnssMeasurement.accumulatedDeltaRangeM && this.accumulatedDeltaRangeUncertaintyM == gnssMeasurement.accumulatedDeltaRangeUncertaintyM && this.carrierFrequencyHz == gnssMeasurement.carrierFrequencyHz && this.carrierCycles == gnssMeasurement.carrierCycles && this.carrierPhase == gnssMeasurement.carrierPhase && this.carrierPhaseUncertainty == gnssMeasurement.carrierPhaseUncertainty && this.multipathIndicator == gnssMeasurement.multipathIndicator && this.snrDb == gnssMeasurement.snrDb && this.agcLevelDb == gnssMeasurement.agcLevelDb) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.flags))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.svid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.constellation))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.timeOffsetNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.state))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.receivedSvTimeInNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.receivedSvTimeUncertaintyInNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.cN0DbHz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.pseudorangeRateMps))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.pseudorangeRateUncertaintyMps))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.accumulatedDeltaRangeState))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.accumulatedDeltaRangeM))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.accumulatedDeltaRangeUncertaintyM))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.carrierFrequencyHz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.carrierCycles))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.carrierPhase))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.carrierPhaseUncertainty))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.multipathIndicator))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.snrDb))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.agcLevelDb))));
        }

        public final java.lang.String toString() {
            return "{.flags = " + android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurementFlags.dumpBitfield(this.flags) + ", .svid = " + ((int) this.svid) + ", .constellation = " + android.hardware.gnss.V1_0.GnssConstellationType.toString(this.constellation) + ", .timeOffsetNs = " + this.timeOffsetNs + ", .state = " + android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurementState.dumpBitfield(this.state) + ", .receivedSvTimeInNs = " + this.receivedSvTimeInNs + ", .receivedSvTimeUncertaintyInNs = " + this.receivedSvTimeUncertaintyInNs + ", .cN0DbHz = " + this.cN0DbHz + ", .pseudorangeRateMps = " + this.pseudorangeRateMps + ", .pseudorangeRateUncertaintyMps = " + this.pseudorangeRateUncertaintyMps + ", .accumulatedDeltaRangeState = " + android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssAccumulatedDeltaRangeState.dumpBitfield(this.accumulatedDeltaRangeState) + ", .accumulatedDeltaRangeM = " + this.accumulatedDeltaRangeM + ", .accumulatedDeltaRangeUncertaintyM = " + this.accumulatedDeltaRangeUncertaintyM + ", .carrierFrequencyHz = " + this.carrierFrequencyHz + ", .carrierCycles = " + this.carrierCycles + ", .carrierPhase = " + this.carrierPhase + ", .carrierPhaseUncertainty = " + this.carrierPhaseUncertainty + ", .multipathIndicator = " + android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMultipathIndicator.toString(this.multipathIndicator) + ", .snrDb = " + this.snrDb + ", .agcLevelDb = " + this.agcLevelDb + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(144L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 144, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement gnssMeasurement = new android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement();
                gnssMeasurement.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 144);
                arrayList.add(gnssMeasurement);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.flags = hwBlob.getInt32(0 + j);
            this.svid = hwBlob.getInt16(4 + j);
            this.constellation = hwBlob.getInt8(6 + j);
            this.timeOffsetNs = hwBlob.getDouble(8 + j);
            this.state = hwBlob.getInt32(16 + j);
            this.receivedSvTimeInNs = hwBlob.getInt64(24 + j);
            this.receivedSvTimeUncertaintyInNs = hwBlob.getInt64(32 + j);
            this.cN0DbHz = hwBlob.getDouble(40 + j);
            this.pseudorangeRateMps = hwBlob.getDouble(48 + j);
            this.pseudorangeRateUncertaintyMps = hwBlob.getDouble(56 + j);
            this.accumulatedDeltaRangeState = hwBlob.getInt16(64 + j);
            this.accumulatedDeltaRangeM = hwBlob.getDouble(72 + j);
            this.accumulatedDeltaRangeUncertaintyM = hwBlob.getDouble(80 + j);
            this.carrierFrequencyHz = hwBlob.getFloat(88 + j);
            this.carrierCycles = hwBlob.getInt64(96 + j);
            this.carrierPhase = hwBlob.getDouble(104 + j);
            this.carrierPhaseUncertainty = hwBlob.getDouble(112 + j);
            this.multipathIndicator = hwBlob.getInt8(120 + j);
            this.snrDb = hwBlob.getDouble(128 + j);
            this.agcLevelDb = hwBlob.getDouble(j + 136);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(144);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 144);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 144);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt32(0 + j, this.flags);
            hwBlob.putInt16(4 + j, this.svid);
            hwBlob.putInt8(6 + j, this.constellation);
            hwBlob.putDouble(8 + j, this.timeOffsetNs);
            hwBlob.putInt32(16 + j, this.state);
            hwBlob.putInt64(24 + j, this.receivedSvTimeInNs);
            hwBlob.putInt64(32 + j, this.receivedSvTimeUncertaintyInNs);
            hwBlob.putDouble(40 + j, this.cN0DbHz);
            hwBlob.putDouble(48 + j, this.pseudorangeRateMps);
            hwBlob.putDouble(56 + j, this.pseudorangeRateUncertaintyMps);
            hwBlob.putInt16(64 + j, this.accumulatedDeltaRangeState);
            hwBlob.putDouble(72 + j, this.accumulatedDeltaRangeM);
            hwBlob.putDouble(80 + j, this.accumulatedDeltaRangeUncertaintyM);
            hwBlob.putFloat(88 + j, this.carrierFrequencyHz);
            hwBlob.putInt64(96 + j, this.carrierCycles);
            hwBlob.putDouble(104 + j, this.carrierPhase);
            hwBlob.putDouble(112 + j, this.carrierPhaseUncertainty);
            hwBlob.putInt8(120 + j, this.multipathIndicator);
            hwBlob.putDouble(128 + j, this.snrDb);
            hwBlob.putDouble(j + 136, this.agcLevelDb);
        }
    }

    public static final class GnssData {
        public int measurementCount = 0;
        public android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement[] measurements = new android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement[64];
        public android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock clock = new android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData gnssData = (android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData) obj;
            if (this.measurementCount == gnssData.measurementCount && android.os.HidlSupport.deepEquals(this.measurements, gnssData.measurements) && android.os.HidlSupport.deepEquals(this.clock, gnssData.clock)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.measurementCount))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.measurements)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.clock)));
        }

        public final java.lang.String toString() {
            return "{.measurementCount = " + this.measurementCount + ", .measurements = " + java.util.Arrays.toString(this.measurements) + ", .clock = " + this.clock + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(9296L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 9296, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData gnssData = new android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData();
                gnssData.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 9296);
                arrayList.add(gnssData);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.measurementCount = hwBlob.getInt32(0 + j);
            long j2 = 8 + j;
            for (int i = 0; i < 64; i++) {
                this.measurements[i] = new android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssMeasurement();
                this.measurements[i].readEmbeddedFromParcel(hwParcel, hwBlob, j2);
                j2 += 144;
            }
            this.clock.readEmbeddedFromParcel(hwParcel, hwBlob, j + 9224);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(9296);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 9296);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 9296);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt32(0 + j, this.measurementCount);
            long j2 = 8 + j;
            for (int i = 0; i < 64; i++) {
                this.measurements[i].writeEmbeddedToBlob(hwBlob, j2);
                j2 += 144;
            }
            this.clock.writeEmbeddedToBlob(hwBlob, j + 9224);
        }
    }

    public static final class Proxy implements android.hardware.gnss.V1_0.IGnssMeasurementCallback {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.gnss@1.0::IGnssMeasurementCallback]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback
        public void GnssMeasurementCb(android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData gnssData) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName);
            gnssData.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public java.lang.String interfaceDescriptor() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                java.util.ArrayList<byte[]> arrayList = new java.util.ArrayList<>();
                android.os.HwBlob readBuffer = hwParcel2.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                android.os.HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public void ping() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
                debugInfo.readFromParcel(hwParcel2);
                return debugInfo;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.gnss.V1_0.IGnssMeasurementCallback {
        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{-67, -92, -110, -20, 64, 33, -47, 56, 105, -34, 114, -67, 111, -116, android.hardware.biometrics.face.AcquiredInfo.START, -59, -125, 123, 120, -42, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, 107, -115, 83, -114, -2, -59, 50, 5, 115, -91, -20}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName.equals(str)) {
                return this;
            }
            return null;
        }

        public void registerAsService(java.lang.String str) throws android.os.RemoteException {
            registerService(str);
        }

        public java.lang.String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        @Override // android.os.HwBinder
        public void onTransact(int i, android.os.HwParcel hwParcel, android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData gnssData = new android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssData();
                    gnssData.readFromParcel(hwParcel);
                    GnssMeasurementCb(gnssData);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 256067662:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    java.util.ArrayList<java.lang.String> interfaceChain = interfaceChain();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(interfaceChain);
                    hwParcel2.send();
                    return;
                case 256131655:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 256136003:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    java.lang.String interfaceDescriptor = interfaceDescriptor();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(interfaceDescriptor);
                    hwParcel2.send();
                    return;
                case 256398152:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    java.util.ArrayList<byte[]> hashChain = getHashChain();
                    hwParcel2.writeStatus(0);
                    android.os.HwBlob hwBlob = new android.os.HwBlob(16);
                    int size = hashChain.size();
                    hwBlob.putInt32(8L, size);
                    hwBlob.putBool(12L, false);
                    android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
                    for (int i3 = 0; i3 < size; i3++) {
                        long j = i3 * 32;
                        byte[] bArr = hashChain.get(i3);
                        if (bArr == null || bArr.length != 32) {
                            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
                        }
                        hwBlob2.putInt8Array(j, bArr);
                    }
                    hwBlob.putBlob(0L, hwBlob2);
                    hwParcel2.writeBuffer(hwBlob);
                    hwParcel2.send();
                    return;
                case 256462420:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    setHALInstrumentation();
                    return;
                case 256660548:
                default:
                    return;
                case 256921159:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    ping();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 257049926:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    android.internal.hidl.base.V1_0.DebugInfo debugInfo = getDebugInfo();
                    hwParcel2.writeStatus(0);
                    debugInfo.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 257120595:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
            }
        }
    }
}
