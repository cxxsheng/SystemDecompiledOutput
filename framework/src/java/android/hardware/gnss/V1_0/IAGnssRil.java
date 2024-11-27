package android.hardware.gnss.V1_0;

/* loaded from: classes2.dex */
public interface IAGnssRil extends android.internal.hidl.base.V1_0.IBase {
    public static final java.lang.String kInterfaceName = "android.hardware.gnss@1.0::IAGnssRil";

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

    void setCallback(android.hardware.gnss.V1_0.IAGnssRilCallback iAGnssRilCallback) throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    void setRefLocation(android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation aGnssRefLocation) throws android.os.RemoteException;

    boolean setSetId(byte b, java.lang.String str) throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    boolean updateNetworkAvailability(boolean z, java.lang.String str) throws android.os.RemoteException;

    boolean updateNetworkState(boolean z, byte b, boolean z2) throws android.os.RemoteException;

    static android.hardware.gnss.V1_0.IAGnssRil asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.V1_0.IAGnssRil)) {
            return (android.hardware.gnss.V1_0.IAGnssRil) queryLocalInterface;
        }
        android.hardware.gnss.V1_0.IAGnssRil.Proxy proxy = new android.hardware.gnss.V1_0.IAGnssRil.Proxy(iHwBinder);
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

    static android.hardware.gnss.V1_0.IAGnssRil castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.gnss.V1_0.IAGnssRil getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.gnss.V1_0.IAGnssRil getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V1_0.IAGnssRil getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V1_0.IAGnssRil getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class SetIDType {
        public static final byte IMSI = 1;
        public static final byte MSISDM = 2;
        public static final byte NONE = 0;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            }
            if (b == 1) {
                return "IMSI";
            }
            if (b == 2) {
                return "MSISDM";
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
                arrayList.add("IMSI");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("MSISDM");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class NetworkType {
        public static final byte DUN = 4;
        public static final byte HIPRI = 5;
        public static final byte MMS = 2;
        public static final byte MOBILE = 0;
        public static final byte SUPL = 3;
        public static final byte WIFI = 1;
        public static final byte WIMAX = 6;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return "MOBILE";
            }
            if (b == 1) {
                return "WIFI";
            }
            if (b == 2) {
                return "MMS";
            }
            if (b == 3) {
                return "SUPL";
            }
            if (b == 4) {
                return "DUN";
            }
            if (b == 5) {
                return "HIPRI";
            }
            if (b == 6) {
                return "WIMAX";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("MOBILE");
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("WIFI");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("MMS");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 3) == 3) {
                arrayList.add("SUPL");
                b2 = (byte) (b2 | 3);
            }
            if ((b & 4) == 4) {
                arrayList.add("DUN");
                b2 = (byte) (b2 | 4);
            }
            if ((b & 5) == 5) {
                arrayList.add("HIPRI");
                b2 = (byte) (b2 | 5);
            }
            if ((b & 6) == 6) {
                arrayList.add("WIMAX");
                b2 = (byte) (b2 | 6);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class AGnssRefLocationType {
        public static final byte GSM_CELLID = 1;
        public static final byte LTE_CELLID = 4;
        public static final byte UMTS_CELLID = 2;

        public static final java.lang.String toString(byte b) {
            if (b == 1) {
                return "GSM_CELLID";
            }
            if (b == 2) {
                return "UMTS_CELLID";
            }
            if (b == 4) {
                return "LTE_CELLID";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("GSM_CELLID");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("UMTS_CELLID");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 4) == 4) {
                arrayList.add("LTE_CELLID");
                b2 = (byte) (b2 | 4);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class AGnssRefLocationCellID {
        public byte type = 0;
        public short mcc = 0;
        public short mnc = 0;
        public short lac = 0;
        public int cid = 0;
        public short tac = 0;
        public short pcid = 0;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID aGnssRefLocationCellID = (android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID) obj;
            if (this.type == aGnssRefLocationCellID.type && this.mcc == aGnssRefLocationCellID.mcc && this.mnc == aGnssRefLocationCellID.mnc && this.lac == aGnssRefLocationCellID.lac && this.cid == aGnssRefLocationCellID.cid && this.tac == aGnssRefLocationCellID.tac && this.pcid == aGnssRefLocationCellID.pcid) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.mcc))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.mnc))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.lac))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.tac))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.pcid))));
        }

        public final java.lang.String toString() {
            return "{.type = " + android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationType.toString(this.type) + ", .mcc = " + ((int) this.mcc) + ", .mnc = " + ((int) this.mnc) + ", .lac = " + ((int) this.lac) + ", .cid = " + this.cid + ", .tac = " + ((int) this.tac) + ", .pcid = " + ((int) this.pcid) + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID aGnssRefLocationCellID = new android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID();
                aGnssRefLocationCellID.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
                arrayList.add(aGnssRefLocationCellID);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.type = hwBlob.getInt8(0 + j);
            this.mcc = hwBlob.getInt16(2 + j);
            this.mnc = hwBlob.getInt16(4 + j);
            this.lac = hwBlob.getInt16(6 + j);
            this.cid = hwBlob.getInt32(8 + j);
            this.tac = hwBlob.getInt16(12 + j);
            this.pcid = hwBlob.getInt16(j + 14);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID> arrayList) {
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
            hwBlob.putInt8(0 + j, this.type);
            hwBlob.putInt16(2 + j, this.mcc);
            hwBlob.putInt16(4 + j, this.mnc);
            hwBlob.putInt16(6 + j, this.lac);
            hwBlob.putInt32(8 + j, this.cid);
            hwBlob.putInt16(12 + j, this.tac);
            hwBlob.putInt16(j + 14, this.pcid);
        }
    }

    public static final class AGnssRefLocation {
        public byte type = 0;
        public android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID cellID = new android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationCellID();

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation.class) {
                return false;
            }
            android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation aGnssRefLocation = (android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation) obj;
            if (this.type == aGnssRefLocation.type && android.os.HidlSupport.deepEquals(this.cellID, aGnssRefLocation.cellID)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellID)));
        }

        public final java.lang.String toString() {
            return "{.type = " + android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocationType.toString(this.type) + ", .cellID = " + this.cellID + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation aGnssRefLocation = new android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation();
                aGnssRefLocation.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
                arrayList.add(aGnssRefLocation);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.type = hwBlob.getInt8(0 + j);
            this.cellID.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(20);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation> arrayList) {
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
            hwBlob.putInt8(0 + j, this.type);
            this.cellID.writeEmbeddedToBlob(hwBlob, j + 4);
        }
    }

    public static final class Proxy implements android.hardware.gnss.V1_0.IAGnssRil {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.gnss@1.0::IAGnssRil]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public void setCallback(android.hardware.gnss.V1_0.IAGnssRilCallback iAGnssRilCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            hwParcel.writeStrongBinder(iAGnssRilCallback == null ? null : iAGnssRilCallback.asBinder());
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public void setRefLocation(android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation aGnssRefLocation) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            aGnssRefLocation.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public boolean setSetId(byte b, java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            hwParcel.writeInt8(b);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public boolean updateNetworkState(boolean z, byte b, boolean z2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            hwParcel.writeBool(z);
            hwParcel.writeInt8(b);
            hwParcel.writeBool(z2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public boolean updateNetworkAvailability(boolean z, java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            hwParcel.writeBool(z);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.gnss.V1_0.IAGnssRil {
        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{-47, 110, 106, 53, -101, -26, -106, 62, -89, 83, -41, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -114, -124, -20, com.android.internal.midi.MidiConstants.STATUS_SONG_POSITION, -71, 48, 82, 9, 121, 56, -109, -116, 77, 54, -41, -92, 126, -94, -30, -82}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    setCallback(android.hardware.gnss.V1_0.IAGnssRilCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation aGnssRefLocation = new android.hardware.gnss.V1_0.IAGnssRil.AGnssRefLocation();
                    aGnssRefLocation.readFromParcel(hwParcel);
                    setRefLocation(aGnssRefLocation);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    boolean setId = setSetId(hwParcel.readInt8(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(setId);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    boolean updateNetworkState = updateNetworkState(hwParcel.readBool(), hwParcel.readInt8(), hwParcel.readBool());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(updateNetworkState);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    boolean updateNetworkAvailability = updateNetworkAvailability(hwParcel.readBool(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(updateNetworkAvailability);
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
