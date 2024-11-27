package android.hardware.gnss.V1_0;

/* loaded from: classes2.dex */
public interface IGnssCallback extends android.internal.hidl.base.V1_0.IBase {
    public static final java.lang.String kInterfaceName = "android.hardware.gnss@1.0::IGnssCallback";

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    void gnssAcquireWakelockCb() throws android.os.RemoteException;

    void gnssLocationCb(android.hardware.gnss.V1_0.GnssLocation gnssLocation) throws android.os.RemoteException;

    void gnssNmeaCb(long j, java.lang.String str) throws android.os.RemoteException;

    void gnssReleaseWakelockCb() throws android.os.RemoteException;

    void gnssRequestTimeCb() throws android.os.RemoteException;

    void gnssSetCapabilitesCb(int i) throws android.os.RemoteException;

    void gnssSetSystemInfoCb(android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo gnssSystemInfo) throws android.os.RemoteException;

    void gnssStatusCb(byte b) throws android.os.RemoteException;

    void gnssSvStatusCb(android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus gnssSvStatus) throws android.os.RemoteException;

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

    static android.hardware.gnss.V1_0.IGnssCallback asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.V1_0.IGnssCallback)) {
            return (android.hardware.gnss.V1_0.IGnssCallback) queryLocalInterface;
        }
        android.hardware.gnss.V1_0.IGnssCallback.Proxy proxy = new android.hardware.gnss.V1_0.IGnssCallback.Proxy(iHwBinder);
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

    static android.hardware.gnss.V1_0.IGnssCallback castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.gnss.V1_0.IGnssCallback getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.gnss.V1_0.IGnssCallback getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V1_0.IGnssCallback getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V1_0.IGnssCallback getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class Capabilities {
        public static final int GEOFENCING = 32;
        public static final int MEASUREMENTS = 64;
        public static final int MSA = 4;
        public static final int MSB = 2;
        public static final int NAV_MESSAGES = 128;
        public static final int ON_DEMAND_TIME = 16;
        public static final int SCHEDULING = 1;
        public static final int SINGLE_SHOT = 8;

        public static final java.lang.String toString(int i) {
            if (i == 1) {
                return "SCHEDULING";
            }
            if (i == 2) {
                return "MSB";
            }
            if (i == 4) {
                return "MSA";
            }
            if (i == 8) {
                return "SINGLE_SHOT";
            }
            if (i == 16) {
                return "ON_DEMAND_TIME";
            }
            if (i == 32) {
                return "GEOFENCING";
            }
            if (i == 64) {
                return "MEASUREMENTS";
            }
            if (i == 128) {
                return "NAV_MESSAGES";
            }
            return "0x" + java.lang.Integer.toHexString(i);
        }

        public static final java.lang.String dumpBitfield(int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int i2 = 1;
            if ((i & 1) != 1) {
                i2 = 0;
            } else {
                arrayList.add("SCHEDULING");
            }
            if ((i & 2) == 2) {
                arrayList.add("MSB");
                i2 |= 2;
            }
            if ((i & 4) == 4) {
                arrayList.add("MSA");
                i2 |= 4;
            }
            if ((i & 8) == 8) {
                arrayList.add("SINGLE_SHOT");
                i2 |= 8;
            }
            if ((i & 16) == 16) {
                arrayList.add("ON_DEMAND_TIME");
                i2 |= 16;
            }
            if ((i & 32) == 32) {
                arrayList.add("GEOFENCING");
                i2 |= 32;
            }
            if ((i & 64) == 64) {
                arrayList.add("MEASUREMENTS");
                i2 |= 64;
            }
            if ((i & 128) == 128) {
                arrayList.add("NAV_MESSAGES");
                i2 |= 128;
            }
            if (i != i2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class GnssStatusValue {
        public static final byte ENGINE_OFF = 4;
        public static final byte ENGINE_ON = 3;
        public static final byte NONE = 0;
        public static final byte SESSION_BEGIN = 1;
        public static final byte SESSION_END = 2;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            }
            if (b == 1) {
                return "SESSION_BEGIN";
            }
            if (b == 2) {
                return "SESSION_END";
            }
            if (b == 3) {
                return "ENGINE_ON";
            }
            if (b == 4) {
                return "ENGINE_OFF";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(android.security.keystore.KeyProperties.DIGEST_NONE);
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("SESSION_BEGIN");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("SESSION_END");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 3) == 3) {
                arrayList.add("ENGINE_ON");
                b2 = (byte) (b2 | 3);
            }
            if ((b & 4) == 4) {
                arrayList.add("ENGINE_OFF");
                b2 = (byte) (b2 | 4);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class GnssSvFlags {
        public static final byte HAS_ALMANAC_DATA = 2;
        public static final byte HAS_CARRIER_FREQUENCY = 8;
        public static final byte HAS_EPHEMERIS_DATA = 1;
        public static final byte NONE = 0;
        public static final byte USED_IN_FIX = 4;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            }
            if (b == 1) {
                return "HAS_EPHEMERIS_DATA";
            }
            if (b == 2) {
                return "HAS_ALMANAC_DATA";
            }
            if (b == 4) {
                return "USED_IN_FIX";
            }
            if (b == 8) {
                return "HAS_CARRIER_FREQUENCY";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(android.security.keystore.KeyProperties.DIGEST_NONE);
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("HAS_EPHEMERIS_DATA");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("HAS_ALMANAC_DATA");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 4) == 4) {
                arrayList.add("USED_IN_FIX");
                b2 = (byte) (b2 | 4);
            }
            if ((b & 8) == 8) {
                arrayList.add("HAS_CARRIER_FREQUENCY");
                b2 = (byte) (b2 | 8);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class GnssSvInfo {
        public byte svFlag;
        public short svid = 0;
        public byte constellation = 0;
        public float cN0Dbhz = 0.0f;
        public float elevationDegrees = 0.0f;
        public float azimuthDegrees = 0.0f;
        public float carrierFrequencyHz = 0.0f;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo gnssSvInfo = (android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo) obj;
            if (this.svid == gnssSvInfo.svid && this.constellation == gnssSvInfo.constellation && this.cN0Dbhz == gnssSvInfo.cN0Dbhz && this.elevationDegrees == gnssSvInfo.elevationDegrees && this.azimuthDegrees == gnssSvInfo.azimuthDegrees && this.carrierFrequencyHz == gnssSvInfo.carrierFrequencyHz && android.os.HidlSupport.deepEquals(java.lang.Byte.valueOf(this.svFlag), java.lang.Byte.valueOf(gnssSvInfo.svFlag))) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.svid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.constellation))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.cN0Dbhz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.elevationDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.azimuthDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.carrierFrequencyHz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.svFlag))));
        }

        public final java.lang.String toString() {
            return "{.svid = " + ((int) this.svid) + ", .constellation = " + android.hardware.gnss.V1_0.GnssConstellationType.toString(this.constellation) + ", .cN0Dbhz = " + this.cN0Dbhz + ", .elevationDegrees = " + this.elevationDegrees + ", .azimuthDegrees = " + this.azimuthDegrees + ", .carrierFrequencyHz = " + this.carrierFrequencyHz + ", .svFlag = " + android.hardware.gnss.V1_0.IGnssCallback.GnssSvFlags.dumpBitfield(this.svFlag) + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo gnssSvInfo = new android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo();
                gnssSvInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
                arrayList.add(gnssSvInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.svid = hwBlob.getInt16(0 + j);
            this.constellation = hwBlob.getInt8(2 + j);
            this.cN0Dbhz = hwBlob.getFloat(4 + j);
            this.elevationDegrees = hwBlob.getFloat(8 + j);
            this.azimuthDegrees = hwBlob.getFloat(12 + j);
            this.carrierFrequencyHz = hwBlob.getFloat(16 + j);
            this.svFlag = hwBlob.getInt8(j + 20);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(24);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt16(0 + j, this.svid);
            hwBlob.putInt8(2 + j, this.constellation);
            hwBlob.putFloat(4 + j, this.cN0Dbhz);
            hwBlob.putFloat(8 + j, this.elevationDegrees);
            hwBlob.putFloat(12 + j, this.azimuthDegrees);
            hwBlob.putFloat(16 + j, this.carrierFrequencyHz);
            hwBlob.putInt8(j + 20, this.svFlag);
        }
    }

    public static final class GnssSvStatus {
        public int numSvs = 0;
        public android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo[] gnssSvList = new android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo[64];

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus gnssSvStatus = (android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus) obj;
            if (this.numSvs == gnssSvStatus.numSvs && android.os.HidlSupport.deepEquals(this.gnssSvList, gnssSvStatus.gnssSvList)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.numSvs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.gnssSvList)));
        }

        public final java.lang.String toString() {
            return "{.numSvs = " + this.numSvs + ", .gnssSvList = " + java.util.Arrays.toString(this.gnssSvList) + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(1540L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus gnssSvStatus = new android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus();
                gnssSvStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE);
                arrayList.add(gnssSvStatus);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.numSvs = hwBlob.getInt32(0 + j);
            long j2 = j + 4;
            for (int i = 0; i < 64; i++) {
                this.gnssSvList[i] = new android.hardware.gnss.V1_0.IGnssCallback.GnssSvInfo();
                this.gnssSvList[i].readEmbeddedFromParcel(hwParcel, hwBlob, j2);
                j2 += 24;
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt32(0 + j, this.numSvs);
            long j2 = j + 4;
            for (int i = 0; i < 64; i++) {
                this.gnssSvList[i].writeEmbeddedToBlob(hwBlob, j2);
                j2 += 24;
            }
        }
    }

    public static final class GnssSystemInfo {
        public short yearOfHw = 0;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && obj.getClass() == android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo.class && this.yearOfHw == ((android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo) obj).yearOfHw) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.yearOfHw))));
        }

        public final java.lang.String toString() {
            return "{.yearOfHw = " + ((int) this.yearOfHw) + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 2, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo gnssSystemInfo = new android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo();
                gnssSystemInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 2);
                arrayList.add(gnssSystemInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.yearOfHw = hwBlob.getInt16(j + 0);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(2);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 2);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt16(j + 0, this.yearOfHw);
        }
    }

    public static final class Proxy implements android.hardware.gnss.V1_0.IGnssCallback {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.gnss@1.0::IGnssCallback]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssLocationCb(android.hardware.gnss.V1_0.GnssLocation gnssLocation) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            gnssLocation.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssStatusCb(byte b) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            hwParcel.writeInt8(b);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssSvStatusCb(android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus gnssSvStatus) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            gnssSvStatus.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssNmeaCb(long j, java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            hwParcel.writeInt64(j);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssSetCapabilitesCb(int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssAcquireWakelockCb() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssReleaseWakelockCb() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssRequestTimeCb() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssSetSystemInfoCb(android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo gnssSystemInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            gnssSystemInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.gnss.V1_0.IGnssCallback {
        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{-94, -5, -39, 116, Byte.MAX_VALUE, -69, -100, -21, -116, 16, com.android.internal.midi.MidiConstants.STATUS_NOTE_ON, -75, -94, 65, 56, 49, 34, 70, 80, 45, 90, -16, 101, 74, -116, 43, 96, 58, -101, -11, 33, -4}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    android.hardware.gnss.V1_0.GnssLocation gnssLocation = new android.hardware.gnss.V1_0.GnssLocation();
                    gnssLocation.readFromParcel(hwParcel);
                    gnssLocationCb(gnssLocation);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssStatusCb(hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus gnssSvStatus = new android.hardware.gnss.V1_0.IGnssCallback.GnssSvStatus();
                    gnssSvStatus.readFromParcel(hwParcel);
                    gnssSvStatusCb(gnssSvStatus);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssNmeaCb(hwParcel.readInt64(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssSetCapabilitesCb(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssAcquireWakelockCb();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssReleaseWakelockCb();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssRequestTimeCb();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo gnssSystemInfo = new android.hardware.gnss.V1_0.IGnssCallback.GnssSystemInfo();
                    gnssSystemInfo.readFromParcel(hwParcel);
                    gnssSetSystemInfoCb(gnssSystemInfo);
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
