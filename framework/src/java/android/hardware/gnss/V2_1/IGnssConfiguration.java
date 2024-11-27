package android.hardware.gnss.V2_1;

/* loaded from: classes2.dex */
public interface IGnssConfiguration extends android.hardware.gnss.V2_0.IGnssConfiguration {
    public static final java.lang.String kInterfaceName = "android.hardware.gnss@2.1::IGnssConfiguration";

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    boolean setBlacklist_2_1(java.util.ArrayList<android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource> arrayList) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.gnss.V2_1.IGnssConfiguration asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.V2_1.IGnssConfiguration)) {
            return (android.hardware.gnss.V2_1.IGnssConfiguration) queryLocalInterface;
        }
        android.hardware.gnss.V2_1.IGnssConfiguration.Proxy proxy = new android.hardware.gnss.V2_1.IGnssConfiguration.Proxy(iHwBinder);
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

    static android.hardware.gnss.V2_1.IGnssConfiguration castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.gnss.V2_1.IGnssConfiguration getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.gnss.V2_1.IGnssConfiguration getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V2_1.IGnssConfiguration getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V2_1.IGnssConfiguration getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class BlacklistedSource {
        public byte constellation = 0;
        public short svid = 0;

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource.class) {
                return false;
            }
            android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource blacklistedSource = (android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource) obj;
            if (this.constellation == blacklistedSource.constellation && this.svid == blacklistedSource.svid) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.constellation))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.svid))));
        }

        public final java.lang.String toString() {
            return "{.constellation = " + android.hardware.gnss.V2_0.GnssConstellationType.toString(this.constellation) + ", .svid = " + ((int) this.svid) + "}";
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(4L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource blacklistedSource = new android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource();
                blacklistedSource.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 4);
                arrayList.add(blacklistedSource);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.constellation = hwBlob.getInt8(0 + j);
            this.svid = hwBlob.getInt16(j + 2);
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(4);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 4);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt8(0 + j, this.constellation);
            hwBlob.putInt16(j + 2, this.svid);
        }
    }

    public static final class Proxy implements android.hardware.gnss.V2_1.IGnssConfiguration {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.gnss@2.1::IGnssConfiguration]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssConfiguration
        public boolean setSuplEs(boolean z) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
            hwParcel.writeBool(z);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssConfiguration
        public boolean setSuplVersion(int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
            hwParcel.writeInt32(i);
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

        @Override // android.hardware.gnss.V1_0.IGnssConfiguration
        public boolean setSuplMode(byte b) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
            hwParcel.writeInt8(b);
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

        @Override // android.hardware.gnss.V1_0.IGnssConfiguration
        public boolean setGpsLock(byte b) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
            hwParcel.writeInt8(b);
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

        @Override // android.hardware.gnss.V1_0.IGnssConfiguration
        public boolean setLppProfile(byte b) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
            hwParcel.writeInt8(b);
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

        @Override // android.hardware.gnss.V1_0.IGnssConfiguration
        public boolean setGlonassPositioningProtocol(byte b) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
            hwParcel.writeInt8(b);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssConfiguration
        public boolean setEmergencySuplPdn(boolean z) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
            hwParcel.writeBool(z);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnssConfiguration
        public boolean setBlacklist(java.util.ArrayList<android.hardware.gnss.V1_1.IGnssConfiguration.BlacklistedSource> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnssConfiguration.kInterfaceName);
            android.hardware.gnss.V1_1.IGnssConfiguration.BlacklistedSource.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnssConfiguration
        public boolean setEsExtensionSec(int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnssConfiguration.kInterfaceName);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration
        public boolean setBlacklist_2_1(java.util.ArrayList<android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_1.IGnssConfiguration.kInterfaceName);
            android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.gnss.V2_1.IGnssConfiguration {
        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.gnss.V2_1.IGnssConfiguration.kInterfaceName, android.hardware.gnss.V2_0.IGnssConfiguration.kInterfaceName, android.hardware.gnss.V1_1.IGnssConfiguration.kInterfaceName, android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.gnss.V2_1.IGnssConfiguration.kInterfaceName;
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{115, 125, 117, 0, android.hardware.biometrics.face.AcquiredInfo.VENDOR, 115, -113, 7, 83, -47, 59, com.android.internal.midi.MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, android.hardware.biometrics.face.AcquiredInfo.MOUTH_COVERING_DETECTED, 51, 16, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, 31, 41, 75, -118, -24, 11, 63, -42, 62, -86, 34, 126, -99, -100, 102}, new byte[]{-20, -55, 102, -58, -117, -35, -67, -107, -56, -38, -25, -126, -72, 66, 4, -49, 1, -57, 87, 52, 103, 94, -121, 105, -106, 63, 59, 81, 6, -20, android.hardware.biometrics.face.AcquiredInfo.TILT_TOO_EXTREME, -117}, new byte[]{60, 81, -125, -41, 80, 96, 16, -66, 87, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, -9, 72, -29, 100, 15, -62, -34, -47, -70, -107, 87, -124, -74, 37, 107, -92, 39, -12, -61, -103, 89, 28}, new byte[]{-5, -110, -30, -76, 15, -114, -99, 73, 78, -113, -45, -76, -84, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, 73, -102, 50, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, 52, 46, 124, -1, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, 7, 20, -61, -69, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, 102, 11, 110, 121}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V2_1.IGnssConfiguration, android.hardware.gnss.V2_0.IGnssConfiguration, android.hardware.gnss.V1_1.IGnssConfiguration, android.hardware.gnss.V1_0.IGnssConfiguration, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.gnss.V2_1.IGnssConfiguration.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
                    boolean suplEs = setSuplEs(hwParcel.readBool());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(suplEs);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
                    boolean suplVersion = setSuplVersion(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(suplVersion);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
                    boolean suplMode = setSuplMode(hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(suplMode);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
                    boolean gpsLock = setGpsLock(hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(gpsLock);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
                    boolean lppProfile = setLppProfile(hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(lppProfile);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
                    boolean glonassPositioningProtocol = setGlonassPositioningProtocol(hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(glonassPositioningProtocol);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssConfiguration.kInterfaceName);
                    boolean emergencySuplPdn = setEmergencySuplPdn(hwParcel.readBool());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(emergencySuplPdn);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnssConfiguration.kInterfaceName);
                    boolean blacklist = setBlacklist(android.hardware.gnss.V1_1.IGnssConfiguration.BlacklistedSource.readVectorFromParcel(hwParcel));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(blacklist);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnssConfiguration.kInterfaceName);
                    boolean esExtensionSec = setEsExtensionSec(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(esExtensionSec);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_1.IGnssConfiguration.kInterfaceName);
                    boolean blacklist_2_1 = setBlacklist_2_1(android.hardware.gnss.V2_1.IGnssConfiguration.BlacklistedSource.readVectorFromParcel(hwParcel));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(blacklist_2_1);
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
