package android.hardware.gnss.V2_0;

/* loaded from: classes2.dex */
public interface IGnssMeasurementCallback extends android.hardware.gnss.V1_1.IGnssMeasurementCallback {
    public static final java.lang.String kInterfaceName = "android.hardware.gnss@2.0::IGnssMeasurementCallback";

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    void gnssMeasurementCb_2_0(android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData gnssData) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.gnss.V2_0.IGnssMeasurementCallback asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.V2_0.IGnssMeasurementCallback)) {
            return (android.hardware.gnss.V2_0.IGnssMeasurementCallback) queryLocalInterface;
        }
        android.hardware.gnss.V2_0.IGnssMeasurementCallback.Proxy proxy = new android.hardware.gnss.V2_0.IGnssMeasurementCallback.Proxy(iHwBinder);
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

    static android.hardware.gnss.V2_0.IGnssMeasurementCallback castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.gnss.V2_0.IGnssMeasurementCallback getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.gnss.V2_0.IGnssMeasurementCallback getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V2_0.IGnssMeasurementCallback getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V2_0.IGnssMeasurementCallback getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class GnssMeasurementState {
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
            if (i == 65536) {
                return "STATE_2ND_CODE_LOCK";
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
            if ((i & 65536) == 65536) {
                arrayList.add("STATE_2ND_CODE_LOCK");
                i2 |= 65536;
            }
            if (i != i2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class GnssMeasurement {
        public int state;
        public android.hardware.gnss.V1_1.IGnssMeasurementCallback.GnssMeasurement v1_1 = new android.hardware.gnss.V1_1.IGnssMeasurementCallback.GnssMeasurement();
        public java.lang.String codeType = new java.lang.String();
        public byte constellation = 0;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement.class) {
                return false;
            }
            android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement gnssMeasurement = (android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement) obj;
            if (android.os.HidlSupport.deepEquals(this.v1_1, gnssMeasurement.v1_1) && android.os.HidlSupport.deepEquals(this.codeType, gnssMeasurement.codeType) && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.state), java.lang.Integer.valueOf(gnssMeasurement.state)) && this.constellation == gnssMeasurement.constellation) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.v1_1)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.codeType)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.state))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.constellation))));
        }

        public final java.lang.String toString() {
            return "{.v1_1 = " + this.v1_1 + ", .codeType = " + this.codeType + ", .state = " + android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurementState.dumpBitfield(this.state) + ", .constellation = " + android.hardware.gnss.V2_0.GnssConstellationType.toString(this.constellation) + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(176L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 176, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement gnssMeasurement = new android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement();
                gnssMeasurement.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 176);
                arrayList.add(gnssMeasurement);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.v1_1.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
            long j2 = j + 152;
            this.codeType = hwBlob.getString(j2);
            hwParcel.readEmbeddedBuffer(this.codeType.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
            this.state = hwBlob.getInt32(j + 168);
            this.constellation = hwBlob.getInt8(j + 172);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(176);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 176);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 176);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            this.v1_1.writeEmbeddedToBlob(hwBlob, 0 + j);
            hwBlob.putString(152 + j, this.codeType);
            hwBlob.putInt32(168 + j, this.state);
            hwBlob.putInt8(j + 172, this.constellation);
        }
    }

    public static final class GnssData {
        public java.util.ArrayList<android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement> measurements = new java.util.ArrayList<>();
        public android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock clock = new android.hardware.gnss.V1_0.IGnssMeasurementCallback.GnssClock();
        public android.hardware.gnss.V2_0.ElapsedRealtime elapsedRealtime = new android.hardware.gnss.V2_0.ElapsedRealtime();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData.class) {
                return false;
            }
            android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData gnssData = (android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData) obj;
            if (android.os.HidlSupport.deepEquals(this.measurements, gnssData.measurements) && android.os.HidlSupport.deepEquals(this.clock, gnssData.clock) && android.os.HidlSupport.deepEquals(this.elapsedRealtime, gnssData.elapsedRealtime)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.measurements)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.clock)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.elapsedRealtime)));
        }

        public final java.lang.String toString() {
            return "{.measurements = " + this.measurements + ", .clock = " + this.clock + ", .elapsedRealtime = " + this.elapsedRealtime + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData gnssData = new android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData();
                gnssData.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
                arrayList.add(gnssData);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            long j2 = j + 0;
            int int32 = hwBlob.getInt32(8 + j2);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 176, hwBlob.handle(), j2 + 0, true);
            this.measurements.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement gnssMeasurement = new android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssMeasurement();
                gnssMeasurement.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 176);
                this.measurements.add(gnssMeasurement);
            }
            this.clock.readEmbeddedFromParcel(hwParcel, hwBlob, j + 16);
            this.elapsedRealtime.readEmbeddedFromParcel(hwParcel, hwBlob, j + 88);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(112);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 112);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            int size = this.measurements.size();
            long j2 = j + 0;
            hwBlob.putInt32(8 + j2, size);
            hwBlob.putBool(12 + j2, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 176);
            for (int i = 0; i < size; i++) {
                this.measurements.get(i).writeEmbeddedToBlob(hwBlob2, i * 176);
            }
            hwBlob.putBlob(j2 + 0, hwBlob2);
            this.clock.writeEmbeddedToBlob(hwBlob, 16 + j);
            this.elapsedRealtime.writeEmbeddedToBlob(hwBlob, j + 88);
        }
    }

    public static final class Proxy implements android.hardware.gnss.V2_0.IGnssMeasurementCallback {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.gnss@2.0::IGnssMeasurementCallback]@Proxy";
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback
        public void gnssMeasurementCb(android.hardware.gnss.V1_1.IGnssMeasurementCallback.GnssData gnssData) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnssMeasurementCallback.kInterfaceName);
            gnssData.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback
        public void gnssMeasurementCb_2_0(android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData gnssData) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnssMeasurementCallback.kInterfaceName);
            gnssData.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.gnss.V2_0.IGnssMeasurementCallback {
        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.gnss.V2_0.IGnssMeasurementCallback.kInterfaceName, android.hardware.gnss.V1_1.IGnssMeasurementCallback.kInterfaceName, android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.gnss.V2_0.IGnssMeasurementCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{-35, 108, -39, -37, -92, -3, -23, -102, com.android.internal.telephony.GsmAlphabet.GSM_EXTENDED_ESCAPE, -61, -53, android.hardware.biometrics.face.AcquiredInfo.VENDOR, 40, -40, 35, 9, -11, 9, -90, -26, -31, -103, 62, 80, 66, -33, -91, -1, -28, -81, 84, 66}, new byte[]{122, -30, 2, 86, 98, -29, 14, 105, 10, 63, -6, 28, 101, -52, -105, 44, 98, -105, -90, -122, 56, android.hardware.biometrics.face.AcquiredInfo.VENDOR, 64, 85, -61, 60, -65, 61, 46, 75, -67, -36}, new byte[]{-67, -92, -110, -20, 64, 33, -47, 56, 105, -34, 114, -67, 111, -116, android.hardware.biometrics.face.AcquiredInfo.START, -59, -125, 123, 120, -42, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, 107, -115, 83, -114, -2, -59, 50, 5, 115, -91, -20}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.gnss.V2_0.IGnssMeasurementCallback.kInterfaceName.equals(str)) {
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
                case 2:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnssMeasurementCallback.kInterfaceName);
                    android.hardware.gnss.V1_1.IGnssMeasurementCallback.GnssData gnssData2 = new android.hardware.gnss.V1_1.IGnssMeasurementCallback.GnssData();
                    gnssData2.readFromParcel(hwParcel);
                    gnssMeasurementCb(gnssData2);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnssMeasurementCallback.kInterfaceName);
                    android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData gnssData3 = new android.hardware.gnss.V2_0.IGnssMeasurementCallback.GnssData();
                    gnssData3.readFromParcel(hwParcel);
                    gnssMeasurementCb_2_0(gnssData3);
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
