package android.internal.hidl.manager.V1_2;

/* loaded from: classes2.dex */
public interface IServiceManager extends android.internal.hidl.manager.V1_1.IServiceManager {
    public static final java.lang.String kInterfaceName = "android.hidl.manager@1.2::IServiceManager";

    boolean addWithChain(java.lang.String str, android.internal.hidl.base.V1_0.IBase iBase, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    java.util.ArrayList<java.lang.String> listManifestByInterface(java.lang.String str) throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    boolean registerClientCallback(java.lang.String str, java.lang.String str2, android.internal.hidl.base.V1_0.IBase iBase, android.internal.hidl.manager.V1_2.IClientCallback iClientCallback) throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    boolean tryUnregister(java.lang.String str, java.lang.String str2, android.internal.hidl.base.V1_0.IBase iBase) throws android.os.RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    boolean unregisterClientCallback(android.internal.hidl.base.V1_0.IBase iBase, android.internal.hidl.manager.V1_2.IClientCallback iClientCallback) throws android.os.RemoteException;

    static android.internal.hidl.manager.V1_2.IServiceManager asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.internal.hidl.manager.V1_2.IServiceManager)) {
            return (android.internal.hidl.manager.V1_2.IServiceManager) queryLocalInterface;
        }
        android.internal.hidl.manager.V1_2.IServiceManager.Proxy proxy = new android.internal.hidl.manager.V1_2.IServiceManager.Proxy(iHwBinder);
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

    static android.internal.hidl.manager.V1_2.IServiceManager castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.internal.hidl.manager.V1_2.IServiceManager getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.internal.hidl.manager.V1_2.IServiceManager getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.internal.hidl.manager.V1_2.IServiceManager getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.internal.hidl.manager.V1_2.IServiceManager getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class Proxy implements android.internal.hidl.manager.V1_2.IServiceManager {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hidl.manager@1.2::IServiceManager]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public android.internal.hidl.base.V1_0.IBase get(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.internal.hidl.base.V1_0.IBase.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public boolean add(java.lang.String str, android.internal.hidl.base.V1_0.IBase iBase) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public byte getTransport(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt8();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public java.util.ArrayList<java.lang.String> list() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public java.util.ArrayList<java.lang.String> listByInterface(java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public boolean registerForNotifications(java.lang.String str, java.lang.String str2, android.internal.hidl.manager.V1_0.IServiceNotification iServiceNotification) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeStrongBinder(iServiceNotification == null ? null : iServiceNotification.asBinder());
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public java.util.ArrayList<android.internal.hidl.manager.V1_0.IServiceManager.InstanceDebugInfo> debugDump() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.internal.hidl.manager.V1_0.IServiceManager.InstanceDebugInfo.readVectorFromParcel(hwParcel2);
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public void registerPassthroughClient(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_1.IServiceManager
        public boolean unregisterForNotifications(java.lang.String str, java.lang.String str2, android.internal.hidl.manager.V1_0.IServiceNotification iServiceNotification) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_1.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeStrongBinder(iServiceNotification == null ? null : iServiceNotification.asBinder());
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public boolean registerClientCallback(java.lang.String str, java.lang.String str2, android.internal.hidl.base.V1_0.IBase iBase, android.internal.hidl.manager.V1_2.IClientCallback iClientCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
            hwParcel.writeStrongBinder(iClientCallback != null ? iClientCallback.asBinder() : null);
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public boolean unregisterClientCallback(android.internal.hidl.base.V1_0.IBase iBase, android.internal.hidl.manager.V1_2.IClientCallback iClientCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
            hwParcel.writeStrongBinder(iClientCallback != null ? iClientCallback.asBinder() : null);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public boolean addWithChain(java.lang.String str, android.internal.hidl.base.V1_0.IBase iBase, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
            hwParcel.writeStringVector(arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public java.util.ArrayList<java.lang.String> listManifestByInterface(java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public boolean tryUnregister(java.lang.String str, java.lang.String str2, android.internal.hidl.base.V1_0.IBase iBase) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.internal.hidl.manager.V1_2.IServiceManager {
        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName, android.internal.hidl.manager.V1_1.IServiceManager.kInterfaceName, android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName;
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{111, 58, -118, 63, -44, -65, -67, 2, -28, -26, 28, 115, 45, 45, -10, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, -1, 105, 67, 74, 30, -40, 60, -38, 51, 115, 3, -83, -58, -41, 20, -33}, new byte[]{11, -108, -36, -121, 111, 116, -98, -46, 74, -104, -10, 28, 65, -44, 106, -41, 90, 39, 81, 17, 99, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, -106, -118, 8, 66, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -93, 60, 104, 78, -10}, new byte[]{-123, 57, 79, -118, 13, android.hardware.biometrics.face.AcquiredInfo.START, -25, -5, 46, -28, 92, 82, -47, -5, -117, -113, -45, -63, 60, 51, 62, 99, -57, -116, 74, -95, -1, -122, -124, 12, -10, -36}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    android.internal.hidl.base.V1_0.IBase iBase = get(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(iBase == null ? null : iBase.asBinder());
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    boolean add = add(hwParcel.readString(), android.internal.hidl.base.V1_0.IBase.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(add);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    byte transport = getTransport(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt8(transport);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    java.util.ArrayList<java.lang.String> list = list();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(list);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    java.util.ArrayList<java.lang.String> listByInterface = listByInterface(hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(listByInterface);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    boolean registerForNotifications = registerForNotifications(hwParcel.readString(), hwParcel.readString(), android.internal.hidl.manager.V1_0.IServiceNotification.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(registerForNotifications);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    java.util.ArrayList<android.internal.hidl.manager.V1_0.IServiceManager.InstanceDebugInfo> debugDump = debugDump();
                    hwParcel2.writeStatus(0);
                    android.internal.hidl.manager.V1_0.IServiceManager.InstanceDebugInfo.writeVectorToParcel(hwParcel2, debugDump);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    registerPassthroughClient(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_1.IServiceManager.kInterfaceName);
                    boolean unregisterForNotifications = unregisterForNotifications(hwParcel.readString(), hwParcel.readString(), android.internal.hidl.manager.V1_0.IServiceNotification.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(unregisterForNotifications);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
                    boolean registerClientCallback = registerClientCallback(hwParcel.readString(), hwParcel.readString(), android.internal.hidl.base.V1_0.IBase.asInterface(hwParcel.readStrongBinder()), android.internal.hidl.manager.V1_2.IClientCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(registerClientCallback);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
                    boolean unregisterClientCallback = unregisterClientCallback(android.internal.hidl.base.V1_0.IBase.asInterface(hwParcel.readStrongBinder()), android.internal.hidl.manager.V1_2.IClientCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(unregisterClientCallback);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
                    boolean addWithChain = addWithChain(hwParcel.readString(), android.internal.hidl.base.V1_0.IBase.asInterface(hwParcel.readStrongBinder()), hwParcel.readStringVector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(addWithChain);
                    hwParcel2.send();
                    return;
                case 13:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
                    java.util.ArrayList<java.lang.String> listManifestByInterface = listManifestByInterface(hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(listManifestByInterface);
                    hwParcel2.send();
                    return;
                case 14:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName);
                    boolean tryUnregister = tryUnregister(hwParcel.readString(), hwParcel.readString(), android.internal.hidl.base.V1_0.IBase.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(tryUnregister);
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
