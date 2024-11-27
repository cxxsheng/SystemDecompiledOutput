package android.hardware.gnss.visibility_control.V1_0;

/* loaded from: classes2.dex */
public interface IGnssVisibilityControlCallback extends android.internal.hidl.base.V1_0.IBase {
    public static final java.lang.String kInterfaceName = "android.hardware.gnss.visibility_control@1.0::IGnssVisibilityControlCallback";

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

    boolean isInEmergencySession() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    void nfwNotifyCb(android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification nfwNotification) throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback)) {
            return (android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback) queryLocalInterface;
        }
        android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.Proxy proxy = new android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.Proxy(iHwBinder);
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

    static android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class NfwProtocolStack {
        public static final byte CTRL_PLANE = 0;
        public static final byte IMS = 10;
        public static final byte OTHER_PROTOCOL_STACK = 100;
        public static final byte SIM = 11;
        public static final byte SUPL = 1;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return "CTRL_PLANE";
            }
            if (b == 1) {
                return "SUPL";
            }
            if (b == 10) {
                return "IMS";
            }
            if (b == 11) {
                return "SIM";
            }
            if (b == 100) {
                return "OTHER_PROTOCOL_STACK";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("CTRL_PLANE");
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("SUPL");
                b2 = (byte) 1;
            }
            if ((b & 10) == 10) {
                arrayList.add("IMS");
                b2 = (byte) (b2 | 10);
            }
            if ((b & 11) == 11) {
                arrayList.add("SIM");
                b2 = (byte) (b2 | 11);
            }
            if ((b & 100) == 100) {
                arrayList.add("OTHER_PROTOCOL_STACK");
                b2 = (byte) (b2 | 100);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class NfwRequestor {
        public static final byte AUTOMOBILE_CLIENT = 20;
        public static final byte CARRIER = 0;
        public static final byte GNSS_CHIPSET_VENDOR = 12;
        public static final byte MODEM_CHIPSET_VENDOR = 11;
        public static final byte OEM = 10;
        public static final byte OTHER_CHIPSET_VENDOR = 13;
        public static final byte OTHER_REQUESTOR = 100;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return "CARRIER";
            }
            if (b == 10) {
                return "OEM";
            }
            if (b == 11) {
                return "MODEM_CHIPSET_VENDOR";
            }
            if (b == 12) {
                return "GNSS_CHIPSET_VENDOR";
            }
            if (b == 13) {
                return "OTHER_CHIPSET_VENDOR";
            }
            if (b == 20) {
                return "AUTOMOBILE_CLIENT";
            }
            if (b == 100) {
                return "OTHER_REQUESTOR";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("CARRIER");
            if ((b & 10) != 10) {
                b2 = 0;
            } else {
                arrayList.add("OEM");
                b2 = (byte) 10;
            }
            if ((b & 11) == 11) {
                arrayList.add("MODEM_CHIPSET_VENDOR");
                b2 = (byte) (b2 | 11);
            }
            if ((b & 12) == 12) {
                arrayList.add("GNSS_CHIPSET_VENDOR");
                b2 = (byte) (b2 | 12);
            }
            if ((b & 13) == 13) {
                arrayList.add("OTHER_CHIPSET_VENDOR");
                b2 = (byte) (b2 | 13);
            }
            if ((b & 20) == 20) {
                arrayList.add("AUTOMOBILE_CLIENT");
                b2 = (byte) (b2 | 20);
            }
            if ((b & 100) == 100) {
                arrayList.add("OTHER_REQUESTOR");
                b2 = (byte) (b2 | 100);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class NfwResponseType {
        public static final byte ACCEPTED_LOCATION_PROVIDED = 2;
        public static final byte ACCEPTED_NO_LOCATION_PROVIDED = 1;
        public static final byte REJECTED = 0;

        public static final java.lang.String toString(byte b) {
            if (b == 0) {
                return "REJECTED";
            }
            if (b == 1) {
                return "ACCEPTED_NO_LOCATION_PROVIDED";
            }
            if (b == 2) {
                return "ACCEPTED_LOCATION_PROVIDED";
            }
            return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
        }

        public static final java.lang.String dumpBitfield(byte b) {
            byte b2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add("REJECTED");
            if ((b & 1) != 1) {
                b2 = 0;
            } else {
                arrayList.add("ACCEPTED_NO_LOCATION_PROVIDED");
                b2 = (byte) 1;
            }
            if ((b & 2) == 2) {
                arrayList.add("ACCEPTED_LOCATION_PROVIDED");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return java.lang.String.join(" | ", arrayList);
        }
    }

    public static final class NfwNotification {
        public java.lang.String proxyAppPackageName = new java.lang.String();
        public byte protocolStack = 0;
        public java.lang.String otherProtocolStackName = new java.lang.String();
        public byte requestor = 0;
        public java.lang.String requestorId = new java.lang.String();
        public byte responseType = 0;
        public boolean inEmergencyMode = false;
        public boolean isCachedLocation = false;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification.class) {
                return false;
            }
            android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification nfwNotification = (android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification) obj;
            if (android.os.HidlSupport.deepEquals(this.proxyAppPackageName, nfwNotification.proxyAppPackageName) && this.protocolStack == nfwNotification.protocolStack && android.os.HidlSupport.deepEquals(this.otherProtocolStackName, nfwNotification.otherProtocolStackName) && this.requestor == nfwNotification.requestor && android.os.HidlSupport.deepEquals(this.requestorId, nfwNotification.requestorId) && this.responseType == nfwNotification.responseType && this.inEmergencyMode == nfwNotification.inEmergencyMode && this.isCachedLocation == nfwNotification.isCachedLocation) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.proxyAppPackageName)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.protocolStack))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.otherProtocolStackName)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.requestor))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.requestorId)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.responseType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.inEmergencyMode))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isCachedLocation))));
        }

        public final java.lang.String toString() {
            return "{.proxyAppPackageName = " + this.proxyAppPackageName + ", .protocolStack = " + android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwProtocolStack.toString(this.protocolStack) + ", .otherProtocolStackName = " + this.otherProtocolStackName + ", .requestor = " + android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwRequestor.toString(this.requestor) + ", .requestorId = " + this.requestorId + ", .responseType = " + android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwResponseType.toString(this.responseType) + ", .inEmergencyMode = " + this.inEmergencyMode + ", .isCachedLocation = " + this.isCachedLocation + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification nfwNotification = new android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification();
                nfwNotification.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
                arrayList.add(nfwNotification);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            long j2 = j + 0;
            this.proxyAppPackageName = hwBlob.getString(j2);
            hwParcel.readEmbeddedBuffer(this.proxyAppPackageName.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
            this.protocolStack = hwBlob.getInt8(j + 16);
            long j3 = j + 24;
            this.otherProtocolStackName = hwBlob.getString(j3);
            hwParcel.readEmbeddedBuffer(this.otherProtocolStackName.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
            this.requestor = hwBlob.getInt8(j + 40);
            long j4 = j + 48;
            this.requestorId = hwBlob.getString(j4);
            hwParcel.readEmbeddedBuffer(this.requestorId.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
            this.responseType = hwBlob.getInt8(j + 64);
            this.inEmergencyMode = hwBlob.getBool(j + 65);
            this.isCachedLocation = hwBlob.getBool(j + 66);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(72);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification> arrayList) {
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
            hwBlob.putString(0 + j, this.proxyAppPackageName);
            hwBlob.putInt8(16 + j, this.protocolStack);
            hwBlob.putString(24 + j, this.otherProtocolStackName);
            hwBlob.putInt8(40 + j, this.requestor);
            hwBlob.putString(48 + j, this.requestorId);
            hwBlob.putInt8(64 + j, this.responseType);
            hwBlob.putBool(65 + j, this.inEmergencyMode);
            hwBlob.putBool(j + 66, this.isCachedLocation);
        }
    }

    public static final class Proxy implements android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.gnss.visibility_control@1.0::IGnssVisibilityControlCallback]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback
        public void nfwNotifyCb(android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification nfwNotification) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.kInterfaceName);
            nfwNotification.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback
        public boolean isInEmergencySession() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback {
        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{51, -90, -78, 12, 67, -81, 0, -3, -5, 48, 93, -8, -111, -68, 89, 17, com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE, 109, -102, -111, 48, -71, android.hardware.biometrics.face.AcquiredInfo.TILT_TOO_EXTREME, 117, -106, 73, -109, 46, 90, 74, 110, 109}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.kInterfaceName);
                    android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification nfwNotification = new android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.NfwNotification();
                    nfwNotification.readFromParcel(hwParcel);
                    nfwNotifyCb(nfwNotification);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback.kInterfaceName);
                    boolean isInEmergencySession = isInEmergencySession();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(isInEmergencySession);
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
