package android.hardware.gnss.V2_1;

/* loaded from: classes2.dex */
public interface IGnssAntennaInfoCallback extends android.internal.hidl.base.V1_0.IBase {
    public static final java.lang.String kInterfaceName = "android.hardware.gnss@2.1::IGnssAntennaInfoCallback";

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    void gnssAntennaInfoCb(java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo> arrayList) throws android.os.RemoteException;

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

    static android.hardware.gnss.V2_1.IGnssAntennaInfoCallback asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.V2_1.IGnssAntennaInfoCallback)) {
            return (android.hardware.gnss.V2_1.IGnssAntennaInfoCallback) queryLocalInterface;
        }
        android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Proxy proxy = new android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Proxy(iHwBinder);
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

    static android.hardware.gnss.V2_1.IGnssAntennaInfoCallback castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.gnss.V2_1.IGnssAntennaInfoCallback getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.gnss.V2_1.IGnssAntennaInfoCallback getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V2_1.IGnssAntennaInfoCallback getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V2_1.IGnssAntennaInfoCallback getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class Row {
        public java.util.ArrayList<java.lang.Double> row = new java.util.ArrayList<>();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && obj.getClass() == android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row.class && android.os.HidlSupport.deepEquals(this.row, ((android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row) obj).row)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.row)));
        }

        public final java.lang.String toString() {
            return "{.row = " + this.row + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row row = new android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row();
                row.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
                arrayList.add(row);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            long j2 = j + 0;
            int int32 = hwBlob.getInt32(8 + j2);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, hwBlob.handle(), j2 + 0, true);
            this.row.clear();
            for (int i = 0; i < int32; i++) {
                this.row.add(java.lang.Double.valueOf(readEmbeddedBuffer.getDouble(i * 8)));
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row> arrayList) {
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
            int size = this.row.size();
            long j2 = j + 0;
            hwBlob.putInt32(8 + j2, size);
            hwBlob.putBool(12 + j2, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 8);
            for (int i = 0; i < size; i++) {
                hwBlob2.putDouble(i * 8, this.row.get(i).doubleValue());
            }
            hwBlob.putBlob(j2 + 0, hwBlob2);
        }
    }

    public static final class Coord {
        public double x = 0.0d;
        public double xUncertainty = 0.0d;
        public double y = 0.0d;
        public double yUncertainty = 0.0d;
        public double z = 0.0d;
        public double zUncertainty = 0.0d;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord.class) {
                return false;
            }
            android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord coord = (android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord) obj;
            if (this.x == coord.x && this.xUncertainty == coord.xUncertainty && this.y == coord.y && this.yUncertainty == coord.yUncertainty && this.z == coord.z && this.zUncertainty == coord.zUncertainty) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.x))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.xUncertainty))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.y))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.yUncertainty))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.z))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.zUncertainty))));
        }

        public final java.lang.String toString() {
            return "{.x = " + this.x + ", .xUncertainty = " + this.xUncertainty + ", .y = " + this.y + ", .yUncertainty = " + this.yUncertainty + ", .z = " + this.z + ", .zUncertainty = " + this.zUncertainty + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord coord = new android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord();
                coord.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
                arrayList.add(coord);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.x = hwBlob.getDouble(0 + j);
            this.xUncertainty = hwBlob.getDouble(8 + j);
            this.y = hwBlob.getDouble(16 + j);
            this.yUncertainty = hwBlob.getDouble(24 + j);
            this.z = hwBlob.getDouble(32 + j);
            this.zUncertainty = hwBlob.getDouble(j + 40);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(48);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putDouble(0 + j, this.x);
            hwBlob.putDouble(8 + j, this.xUncertainty);
            hwBlob.putDouble(16 + j, this.y);
            hwBlob.putDouble(24 + j, this.yUncertainty);
            hwBlob.putDouble(32 + j, this.z);
            hwBlob.putDouble(j + 40, this.zUncertainty);
        }
    }

    public static final class GnssAntennaInfo {
        public double carrierFrequencyMHz = 0.0d;
        public android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord phaseCenterOffsetCoordinateMillimeters = new android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Coord();
        public java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row> phaseCenterVariationCorrectionMillimeters = new java.util.ArrayList<>();
        public java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row> phaseCenterVariationCorrectionUncertaintyMillimeters = new java.util.ArrayList<>();
        public java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row> signalGainCorrectionDbi = new java.util.ArrayList<>();
        public java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row> signalGainCorrectionUncertaintyDbi = new java.util.ArrayList<>();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo.class) {
                return false;
            }
            android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo gnssAntennaInfo = (android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo) obj;
            if (this.carrierFrequencyMHz == gnssAntennaInfo.carrierFrequencyMHz && android.os.HidlSupport.deepEquals(this.phaseCenterOffsetCoordinateMillimeters, gnssAntennaInfo.phaseCenterOffsetCoordinateMillimeters) && android.os.HidlSupport.deepEquals(this.phaseCenterVariationCorrectionMillimeters, gnssAntennaInfo.phaseCenterVariationCorrectionMillimeters) && android.os.HidlSupport.deepEquals(this.phaseCenterVariationCorrectionUncertaintyMillimeters, gnssAntennaInfo.phaseCenterVariationCorrectionUncertaintyMillimeters) && android.os.HidlSupport.deepEquals(this.signalGainCorrectionDbi, gnssAntennaInfo.signalGainCorrectionDbi) && android.os.HidlSupport.deepEquals(this.signalGainCorrectionUncertaintyDbi, gnssAntennaInfo.signalGainCorrectionUncertaintyDbi)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.carrierFrequencyMHz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.phaseCenterOffsetCoordinateMillimeters)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.phaseCenterVariationCorrectionMillimeters)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.phaseCenterVariationCorrectionUncertaintyMillimeters)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalGainCorrectionDbi)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalGainCorrectionUncertaintyDbi)));
        }

        public final java.lang.String toString() {
            return "{.carrierFrequencyMHz = " + this.carrierFrequencyMHz + ", .phaseCenterOffsetCoordinateMillimeters = " + this.phaseCenterOffsetCoordinateMillimeters + ", .phaseCenterVariationCorrectionMillimeters = " + this.phaseCenterVariationCorrectionMillimeters + ", .phaseCenterVariationCorrectionUncertaintyMillimeters = " + this.phaseCenterVariationCorrectionUncertaintyMillimeters + ", .signalGainCorrectionDbi = " + this.signalGainCorrectionDbi + ", .signalGainCorrectionUncertaintyDbi = " + this.signalGainCorrectionUncertaintyDbi + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo gnssAntennaInfo = new android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo();
                gnssAntennaInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
                arrayList.add(gnssAntennaInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.carrierFrequencyMHz = hwBlob.getDouble(j + 0);
            this.phaseCenterOffsetCoordinateMillimeters.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
            long j2 = j + 56;
            int int32 = hwBlob.getInt32(j2 + 8);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
            this.phaseCenterVariationCorrectionMillimeters.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row row = new android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row();
                row.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
                this.phaseCenterVariationCorrectionMillimeters.add(row);
            }
            long j3 = j + 72;
            int int322 = hwBlob.getInt32(j3 + 8);
            android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j3 + 0, true);
            this.phaseCenterVariationCorrectionUncertaintyMillimeters.clear();
            for (int i2 = 0; i2 < int322; i2++) {
                android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row row2 = new android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row();
                row2.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 16);
                this.phaseCenterVariationCorrectionUncertaintyMillimeters.add(row2);
            }
            long j4 = j + 88;
            int int323 = hwBlob.getInt32(j4 + 8);
            android.os.HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 16, hwBlob.handle(), j4 + 0, true);
            this.signalGainCorrectionDbi.clear();
            for (int i3 = 0; i3 < int323; i3++) {
                android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row row3 = new android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row();
                row3.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer3, i3 * 16);
                this.signalGainCorrectionDbi.add(row3);
            }
            long j5 = j + 104;
            int int324 = hwBlob.getInt32(8 + j5);
            android.os.HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 16, hwBlob.handle(), j5 + 0, true);
            this.signalGainCorrectionUncertaintyDbi.clear();
            for (int i4 = 0; i4 < int324; i4++) {
                android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row row4 = new android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.Row();
                row4.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer4, i4 * 16);
                this.signalGainCorrectionUncertaintyDbi.add(row4);
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(120);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 120);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putDouble(j + 0, this.carrierFrequencyMHz);
            this.phaseCenterOffsetCoordinateMillimeters.writeEmbeddedToBlob(hwBlob, j + 8);
            int size = this.phaseCenterVariationCorrectionMillimeters.size();
            long j2 = j + 56;
            hwBlob.putInt32(j2 + 8, size);
            hwBlob.putBool(j2 + 12, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
            for (int i = 0; i < size; i++) {
                this.phaseCenterVariationCorrectionMillimeters.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
            }
            hwBlob.putBlob(j2 + 0, hwBlob2);
            int size2 = this.phaseCenterVariationCorrectionUncertaintyMillimeters.size();
            long j3 = j + 72;
            hwBlob.putInt32(j3 + 8, size2);
            hwBlob.putBool(j3 + 12, false);
            android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
            for (int i2 = 0; i2 < size2; i2++) {
                this.phaseCenterVariationCorrectionUncertaintyMillimeters.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 16);
            }
            hwBlob.putBlob(j3 + 0, hwBlob3);
            int size3 = this.signalGainCorrectionDbi.size();
            long j4 = j + 88;
            hwBlob.putInt32(j4 + 8, size3);
            hwBlob.putBool(j4 + 12, false);
            android.os.HwBlob hwBlob4 = new android.os.HwBlob(size3 * 16);
            for (int i3 = 0; i3 < size3; i3++) {
                this.signalGainCorrectionDbi.get(i3).writeEmbeddedToBlob(hwBlob4, i3 * 16);
            }
            hwBlob.putBlob(j4 + 0, hwBlob4);
            int size4 = this.signalGainCorrectionUncertaintyDbi.size();
            long j5 = j + 104;
            hwBlob.putInt32(8 + j5, size4);
            hwBlob.putBool(j5 + 12, false);
            android.os.HwBlob hwBlob5 = new android.os.HwBlob(size4 * 16);
            for (int i4 = 0; i4 < size4; i4++) {
                this.signalGainCorrectionUncertaintyDbi.get(i4).writeEmbeddedToBlob(hwBlob5, i4 * 16);
            }
            hwBlob.putBlob(j5 + 0, hwBlob5);
        }
    }

    public static final class Proxy implements android.hardware.gnss.V2_1.IGnssAntennaInfoCallback {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.gnss@2.1::IGnssAntennaInfoCallback]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback
        public void gnssAntennaInfoCb(java.util.ArrayList<android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.kInterfaceName);
            android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.gnss.V2_1.IGnssAntennaInfoCallback {
        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{11, -61, -19, -105, -53, -61, -10, -85, -56, -100, 104, -12, -23, -12, -47, 36, -7, -9, 35, 67, android.hardware.biometrics.face.AcquiredInfo.DARK_GLASSES_DETECTED, -105, -36, -120, -62, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, 108, -12, -46, -83, 71, -18}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V2_1.IGnssAntennaInfoCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.kInterfaceName);
                    gnssAntennaInfoCb(android.hardware.gnss.V2_1.IGnssAntennaInfoCallback.GnssAntennaInfo.readVectorFromParcel(hwParcel));
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
