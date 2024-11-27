package android.hardware.gnss.V1_0;

/* loaded from: classes2.dex */
public interface IGnssDebug extends android.internal.hidl.base.V1_0.IBase {
    public static final java.lang.String kInterfaceName = "android.hardware.gnss@1.0::IGnssDebug";

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    android.hardware.gnss.V1_0.IGnssDebug.DebugData getDebugData() throws android.os.RemoteException;

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

    static android.hardware.gnss.V1_0.IGnssDebug asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.V1_0.IGnssDebug)) {
            return (android.hardware.gnss.V1_0.IGnssDebug) queryLocalInterface;
        }
        android.hardware.gnss.V1_0.IGnssDebug.Proxy proxy = new android.hardware.gnss.V1_0.IGnssDebug.Proxy(iHwBinder);
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

    static android.hardware.gnss.V1_0.IGnssDebug castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.gnss.V1_0.IGnssDebug getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.gnss.V1_0.IGnssDebug getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V1_0.IGnssDebug getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V1_0.IGnssDebug getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class SatelliteEphemerisType {
        public static final byte ALMANAC_ONLY = 1;
        public static final byte EPHEMERIS = 0;
        public static final byte NOT_AVAILABLE = 2;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return "EPHEMERIS";
            }
            if (b == 1) {
                return "ALMANAC_ONLY";
            }
            if (b == 2) {
                return "NOT_AVAILABLE";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("EPHEMERIS");
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("ALMANAC_ONLY");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("NOT_AVAILABLE");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class SatelliteEphemerisSource {
        public static final byte DEMODULATED = 0;
        public static final byte OTHER = 3;
        public static final byte OTHER_SERVER_PROVIDED = 2;
        public static final byte SUPL_PROVIDED = 1;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return "DEMODULATED";
            }
            if (b == 1) {
                return "SUPL_PROVIDED";
            }
            if (b == 2) {
                return "OTHER_SERVER_PROVIDED";
            }
            if (b == 3) {
                return "OTHER";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("DEMODULATED");
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("SUPL_PROVIDED");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("OTHER_SERVER_PROVIDED");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 3) == 3) {
                arrayList.add("OTHER");
                b2 = (byte) (b2 | 3);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class SatelliteEphemerisHealth {
        public static final byte BAD = 1;
        public static final byte GOOD = 0;
        public static final byte UNKNOWN = 2;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return "GOOD";
            }
            if (b == 1) {
                return "BAD";
            }
            if (b == 2) {
                return "UNKNOWN";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("GOOD");
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("BAD");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("UNKNOWN");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class PositionDebug {
        public boolean valid = false;
        public double latitudeDegrees = 0.0d;
        public double longitudeDegrees = 0.0d;
        public float altitudeMeters = 0.0f;
        public float speedMetersPerSec = 0.0f;
        public float bearingDegrees = 0.0f;
        public double horizontalAccuracyMeters = 0.0d;
        public double verticalAccuracyMeters = 0.0d;
        public double speedAccuracyMetersPerSecond = 0.0d;
        public double bearingAccuracyDegrees = 0.0d;
        public float ageSeconds = 0.0f;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IGnssDebug.PositionDebug.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IGnssDebug.PositionDebug positionDebug = (android.hardware.gnss.V1_0.IGnssDebug.PositionDebug) obj;
            if (this.valid == positionDebug.valid && this.latitudeDegrees == positionDebug.latitudeDegrees && this.longitudeDegrees == positionDebug.longitudeDegrees && this.altitudeMeters == positionDebug.altitudeMeters && this.speedMetersPerSec == positionDebug.speedMetersPerSec && this.bearingDegrees == positionDebug.bearingDegrees && this.horizontalAccuracyMeters == positionDebug.horizontalAccuracyMeters && this.verticalAccuracyMeters == positionDebug.verticalAccuracyMeters && this.speedAccuracyMetersPerSecond == positionDebug.speedAccuracyMetersPerSecond && this.bearingAccuracyDegrees == positionDebug.bearingAccuracyDegrees && this.ageSeconds == positionDebug.ageSeconds) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.valid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.latitudeDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.longitudeDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.altitudeMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.speedMetersPerSec))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.bearingDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.horizontalAccuracyMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.verticalAccuracyMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.speedAccuracyMetersPerSecond))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.bearingAccuracyDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.ageSeconds))));
        }

        public final java.lang.String toString() {
            return "{.valid = " + this.valid + ", .latitudeDegrees = " + this.latitudeDegrees + ", .longitudeDegrees = " + this.longitudeDegrees + ", .altitudeMeters = " + this.altitudeMeters + ", .speedMetersPerSec = " + this.speedMetersPerSec + ", .bearingDegrees = " + this.bearingDegrees + ", .horizontalAccuracyMeters = " + this.horizontalAccuracyMeters + ", .verticalAccuracyMeters = " + this.verticalAccuracyMeters + ", .speedAccuracyMetersPerSecond = " + this.speedAccuracyMetersPerSecond + ", .bearingAccuracyDegrees = " + this.bearingAccuracyDegrees + ", .ageSeconds = " + this.ageSeconds + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.PositionDebug> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.PositionDebug> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssDebug.PositionDebug positionDebug = new android.hardware.gnss.V1_0.IGnssDebug.PositionDebug();
                positionDebug.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 80);
                arrayList.add(positionDebug);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.valid = hwBlob.getBool(0 + j);
            this.latitudeDegrees = hwBlob.getDouble(8 + j);
            this.longitudeDegrees = hwBlob.getDouble(16 + j);
            this.altitudeMeters = hwBlob.getFloat(24 + j);
            this.speedMetersPerSec = hwBlob.getFloat(28 + j);
            this.bearingDegrees = hwBlob.getFloat(32 + j);
            this.horizontalAccuracyMeters = hwBlob.getDouble(40 + j);
            this.verticalAccuracyMeters = hwBlob.getDouble(48 + j);
            this.speedAccuracyMetersPerSecond = hwBlob.getDouble(56 + j);
            this.bearingAccuracyDegrees = hwBlob.getDouble(64 + j);
            this.ageSeconds = hwBlob.getFloat(j + 72);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(80);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.PositionDebug> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 80);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putBool(0 + j, this.valid);
            hwBlob.putDouble(8 + j, this.latitudeDegrees);
            hwBlob.putDouble(16 + j, this.longitudeDegrees);
            hwBlob.putFloat(24 + j, this.altitudeMeters);
            hwBlob.putFloat(28 + j, this.speedMetersPerSec);
            hwBlob.putFloat(32 + j, this.bearingDegrees);
            hwBlob.putDouble(40 + j, this.horizontalAccuracyMeters);
            hwBlob.putDouble(48 + j, this.verticalAccuracyMeters);
            hwBlob.putDouble(56 + j, this.speedAccuracyMetersPerSecond);
            hwBlob.putDouble(64 + j, this.bearingAccuracyDegrees);
            hwBlob.putFloat(j + 72, this.ageSeconds);
        }
    }

    public static final class TimeDebug {
        public long timeEstimate = 0;
        public float timeUncertaintyNs = 0.0f;
        public float frequencyUncertaintyNsPerSec = 0.0f;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IGnssDebug.TimeDebug.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IGnssDebug.TimeDebug timeDebug = (android.hardware.gnss.V1_0.IGnssDebug.TimeDebug) obj;
            if (this.timeEstimate == timeDebug.timeEstimate && this.timeUncertaintyNs == timeDebug.timeUncertaintyNs && this.frequencyUncertaintyNsPerSec == timeDebug.frequencyUncertaintyNsPerSec) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.timeEstimate))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.timeUncertaintyNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.frequencyUncertaintyNsPerSec))));
        }

        public final java.lang.String toString() {
            return "{.timeEstimate = " + this.timeEstimate + ", .timeUncertaintyNs = " + this.timeUncertaintyNs + ", .frequencyUncertaintyNsPerSec = " + this.frequencyUncertaintyNsPerSec + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.TimeDebug> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.TimeDebug> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssDebug.TimeDebug timeDebug = new android.hardware.gnss.V1_0.IGnssDebug.TimeDebug();
                timeDebug.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
                arrayList.add(timeDebug);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.timeEstimate = hwBlob.getInt64(0 + j);
            this.timeUncertaintyNs = hwBlob.getFloat(8 + j);
            this.frequencyUncertaintyNsPerSec = hwBlob.getFloat(j + 12);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.TimeDebug> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt64(0 + j, this.timeEstimate);
            hwBlob.putFloat(8 + j, this.timeUncertaintyNs);
            hwBlob.putFloat(j + 12, this.frequencyUncertaintyNsPerSec);
        }
    }

    public static final class SatelliteData {
        public short svid = 0;
        public byte constellation = 0;
        public byte ephemerisType = 0;
        public byte ephemerisSource = 0;
        public byte ephemerisHealth = 0;
        public float ephemerisAgeSeconds = 0.0f;
        public boolean serverPredictionIsAvailable = false;
        public float serverPredictionAgeSeconds = 0.0f;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IGnssDebug.SatelliteData.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IGnssDebug.SatelliteData satelliteData = (android.hardware.gnss.V1_0.IGnssDebug.SatelliteData) obj;
            if (this.svid == satelliteData.svid && this.constellation == satelliteData.constellation && this.ephemerisType == satelliteData.ephemerisType && this.ephemerisSource == satelliteData.ephemerisSource && this.ephemerisHealth == satelliteData.ephemerisHealth && this.ephemerisAgeSeconds == satelliteData.ephemerisAgeSeconds && this.serverPredictionIsAvailable == satelliteData.serverPredictionIsAvailable && this.serverPredictionAgeSeconds == satelliteData.serverPredictionAgeSeconds) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.svid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.constellation))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.ephemerisType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.ephemerisSource))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.ephemerisHealth))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.ephemerisAgeSeconds))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.serverPredictionIsAvailable))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.serverPredictionAgeSeconds))));
        }

        public final java.lang.String toString() {
            return "{.svid = " + ((int) this.svid) + ", .constellation = " + android.hardware.gnss.V1_0.GnssConstellationType.toString(this.constellation) + ", .ephemerisType = " + android.hardware.gnss.V1_0.IGnssDebug.SatelliteEphemerisType.toString(this.ephemerisType) + ", .ephemerisSource = " + android.hardware.gnss.V1_0.IGnssDebug.SatelliteEphemerisSource.toString(this.ephemerisSource) + ", .ephemerisHealth = " + android.hardware.gnss.V1_0.IGnssDebug.SatelliteEphemerisHealth.toString(this.ephemerisHealth) + ", .ephemerisAgeSeconds = " + this.ephemerisAgeSeconds + ", .serverPredictionIsAvailable = " + this.serverPredictionIsAvailable + ", .serverPredictionAgeSeconds = " + this.serverPredictionAgeSeconds + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.SatelliteData> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.SatelliteData> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssDebug.SatelliteData satelliteData = new android.hardware.gnss.V1_0.IGnssDebug.SatelliteData();
                satelliteData.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
                arrayList.add(satelliteData);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.svid = hwBlob.getInt16(0 + j);
            this.constellation = hwBlob.getInt8(2 + j);
            this.ephemerisType = hwBlob.getInt8(3 + j);
            this.ephemerisSource = hwBlob.getInt8(4 + j);
            this.ephemerisHealth = hwBlob.getInt8(5 + j);
            this.ephemerisAgeSeconds = hwBlob.getFloat(8 + j);
            this.serverPredictionIsAvailable = hwBlob.getBool(12 + j);
            this.serverPredictionAgeSeconds = hwBlob.getFloat(j + 16);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(20);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.SatelliteData> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 20);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt16(0 + j, this.svid);
            hwBlob.putInt8(2 + j, this.constellation);
            hwBlob.putInt8(3 + j, this.ephemerisType);
            hwBlob.putInt8(4 + j, this.ephemerisSource);
            hwBlob.putInt8(5 + j, this.ephemerisHealth);
            hwBlob.putFloat(8 + j, this.ephemerisAgeSeconds);
            hwBlob.putBool(12 + j, this.serverPredictionIsAvailable);
            hwBlob.putFloat(j + 16, this.serverPredictionAgeSeconds);
        }
    }

    public static final class DebugData {
        public android.hardware.gnss.V1_0.IGnssDebug.PositionDebug position = new android.hardware.gnss.V1_0.IGnssDebug.PositionDebug();
        public android.hardware.gnss.V1_0.IGnssDebug.TimeDebug time = new android.hardware.gnss.V1_0.IGnssDebug.TimeDebug();
        public java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.SatelliteData> satelliteDataArray = new java.util.ArrayList<>();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IGnssDebug.DebugData.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IGnssDebug.DebugData debugData = (android.hardware.gnss.V1_0.IGnssDebug.DebugData) obj;
            if (android.os.HidlSupport.deepEquals(this.position, debugData.position) && android.os.HidlSupport.deepEquals(this.time, debugData.time) && android.os.HidlSupport.deepEquals(this.satelliteDataArray, debugData.satelliteDataArray)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.position)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.time)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.satelliteDataArray)));
        }

        public final java.lang.String toString() {
            return "{.position = " + this.position + ", .time = " + this.time + ", .satelliteDataArray = " + this.satelliteDataArray + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.DebugData> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.DebugData> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssDebug.DebugData debugData = new android.hardware.gnss.V1_0.IGnssDebug.DebugData();
                debugData.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
                arrayList.add(debugData);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.position.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
            this.time.readEmbeddedFromParcel(hwParcel, hwBlob, j + 80);
            long j2 = j + 96;
            int int32 = hwBlob.getInt32(8 + j2);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, hwBlob.handle(), j2 + 0, true);
            this.satelliteDataArray.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IGnssDebug.SatelliteData satelliteData = new android.hardware.gnss.V1_0.IGnssDebug.SatelliteData();
                satelliteData.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
                this.satelliteDataArray.add(satelliteData);
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(112);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IGnssDebug.DebugData> arrayList) {
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
            this.position.writeEmbeddedToBlob(hwBlob, j + 0);
            this.time.writeEmbeddedToBlob(hwBlob, 80 + j);
            int size = this.satelliteDataArray.size();
            long j2 = j + 96;
            hwBlob.putInt32(8 + j2, size);
            hwBlob.putBool(12 + j2, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 20);
            for (int i = 0; i < size; i++) {
                this.satelliteDataArray.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
            }
            hwBlob.putBlob(j2 + 0, hwBlob2);
        }
    }

    public static final class Proxy implements android.hardware.gnss.V1_0.IGnssDebug {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.gnss@1.0::IGnssDebug]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug
        public android.hardware.gnss.V1_0.IGnssDebug.DebugData getDebugData() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssDebug.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.gnss.V1_0.IGnssDebug.DebugData debugData = new android.hardware.gnss.V1_0.IGnssDebug.DebugData();
                debugData.readFromParcel(hwParcel2);
                return debugData;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.gnss.V1_0.IGnssDebug {
        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.gnss.V1_0.IGnssDebug.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.gnss.V1_0.IGnssDebug.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{69, 66, android.hardware.biometrics.face.AcquiredInfo.TILT_TOO_EXTREME, 43, -106, -5, com.android.internal.midi.MidiConstants.STATUS_SONG_POSITION, 113, 1, -53, -126, 34, -70, -5, 118, -25, -56, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 50, -39, 119, -35, 16, 88, -19, -40, -27, -120, 28, -91, 117, 47}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.gnss.V1_0.IGnssDebug.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssDebug.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssDebug.DebugData debugData = getDebugData();
                    hwParcel2.writeStatus(0);
                    debugData.writeToParcel(hwParcel2);
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
