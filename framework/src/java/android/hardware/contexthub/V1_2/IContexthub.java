package android.hardware.contexthub.V1_2;

/* loaded from: classes2.dex */
public interface IContexthub extends android.hardware.contexthub.V1_1.IContexthub {
    public static final java.lang.String kInterfaceName = "android.hardware.contexthub@1.2::IContexthub";

    @java.lang.FunctionalInterface
    public interface getHubs_1_2Callback {
        void onValues(java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHub> arrayList, java.util.ArrayList<java.lang.String> arrayList2);
    }

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    void getHubs_1_2(android.hardware.contexthub.V1_2.IContexthub.getHubs_1_2Callback gethubs_1_2callback) throws android.os.RemoteException;

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    void onSettingChanged_1_2(byte b, byte b2) throws android.os.RemoteException;

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    int registerCallback_1_2(int i, android.hardware.contexthub.V1_2.IContexthubCallback iContexthubCallback) throws android.os.RemoteException;

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.contexthub.V1_2.IContexthub asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.contexthub.V1_2.IContexthub)) {
            return (android.hardware.contexthub.V1_2.IContexthub) queryLocalInterface;
        }
        android.hardware.contexthub.V1_2.IContexthub.Proxy proxy = new android.hardware.contexthub.V1_2.IContexthub.Proxy(iHwBinder);
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

    static android.hardware.contexthub.V1_2.IContexthub castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.contexthub.V1_2.IContexthub getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.contexthub.V1_2.IContexthub getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.contexthub.V1_2.IContexthub getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.contexthub.V1_2.IContexthub getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class Proxy implements android.hardware.contexthub.V1_2.IContexthub {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.contexthub@1.2::IContexthub]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHub> getHubs() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.contexthub.V1_0.ContextHub.readVectorFromParcel(hwParcel2);
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int registerCallback(int i, android.hardware.contexthub.V1_0.IContexthubCallback iContexthubCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeStrongBinder(iContexthubCallback == null ? null : iContexthubCallback.asBinder());
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int sendMessageToHub(int i, android.hardware.contexthub.V1_0.ContextHubMsg contextHubMsg) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            contextHubMsg.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int loadNanoApp(int i, android.hardware.contexthub.V1_0.NanoAppBinary nanoAppBinary, int i2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            nanoAppBinary.writeToParcel(hwParcel);
            hwParcel.writeInt32(i2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int unloadNanoApp(int i, long j, int i2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int enableNanoApp(int i, long j, int i2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int disableNanoApp(int i, long j, int i2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int queryApps(int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub
        public void onSettingChanged(byte b, byte b2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_1.IContexthub.kInterfaceName);
            hwParcel.writeInt8(b);
            hwParcel.writeInt8(b2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub
        public void getHubs_1_2(android.hardware.contexthub.V1_2.IContexthub.getHubs_1_2Callback gethubs_1_2callback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_2.IContexthub.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                gethubs_1_2callback.onValues(android.hardware.contexthub.V1_0.ContextHub.readVectorFromParcel(hwParcel2), hwParcel2.readStringVector());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub
        public int registerCallback_1_2(int i, android.hardware.contexthub.V1_2.IContexthubCallback iContexthubCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_2.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeStrongBinder(iContexthubCallback == null ? null : iContexthubCallback.asBinder());
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub
        public void onSettingChanged_1_2(byte b, byte b2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_2.IContexthub.kInterfaceName);
            hwParcel.writeInt8(b);
            hwParcel.writeInt8(b2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.contexthub.V1_2.IContexthub {
        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.contexthub.V1_2.IContexthub.kInterfaceName, android.hardware.contexthub.V1_1.IContexthub.kInterfaceName, android.hardware.contexthub.V1_0.IContexthub.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.contexthub.V1_2.IContexthub.kInterfaceName;
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{53, 0, -45, -60, -30, -44, -98, -18, -46, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, 35, -109, 48, -95, 102, -66, -78, -37, 45, 80, 113, -72, 77, -100, 115, -117, 4, -116, 45, 84, -93, -39}, new byte[]{-125, 81, -52, 1, -18, -44, com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE, -76, 72, 45, -107, 114, -75, -57, -35, -3, android.hardware.biometrics.face.AcquiredInfo.VENDOR, -121, 77, -114, -37, 81, -42, 118, 29, 52, -127, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, -4, -111, -35, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED}, new byte[]{com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 66, 82, 45, -86, 75, 95, Byte.MAX_VALUE, -44, com.android.internal.midi.MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -95, -101, -51, -83, -71, 60, 121, -95, com.android.internal.midi.MidiConstants.STATUS_CONTROL_CHANGE, 76, 9, -17, 44, -104, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -93, -88, -108, 16, 50, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -11}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.contexthub.V1_2.IContexthub, android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.contexthub.V1_2.IContexthub.kInterfaceName.equals(str)) {
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
        public void onTransact(int i, android.os.HwParcel hwParcel, final android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHub> hubs = getHubs();
                    hwParcel2.writeStatus(0);
                    android.hardware.contexthub.V1_0.ContextHub.writeVectorToParcel(hwParcel2, hubs);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int registerCallback = registerCallback(hwParcel.readInt32(), android.hardware.contexthub.V1_0.IContexthubCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(registerCallback);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int readInt32 = hwParcel.readInt32();
                    android.hardware.contexthub.V1_0.ContextHubMsg contextHubMsg = new android.hardware.contexthub.V1_0.ContextHubMsg();
                    contextHubMsg.readFromParcel(hwParcel);
                    int sendMessageToHub = sendMessageToHub(readInt32, contextHubMsg);
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sendMessageToHub);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int readInt322 = hwParcel.readInt32();
                    android.hardware.contexthub.V1_0.NanoAppBinary nanoAppBinary = new android.hardware.contexthub.V1_0.NanoAppBinary();
                    nanoAppBinary.readFromParcel(hwParcel);
                    int loadNanoApp = loadNanoApp(readInt322, nanoAppBinary, hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(loadNanoApp);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int unloadNanoApp = unloadNanoApp(hwParcel.readInt32(), hwParcel.readInt64(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(unloadNanoApp);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int enableNanoApp = enableNanoApp(hwParcel.readInt32(), hwParcel.readInt64(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(enableNanoApp);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int disableNanoApp = disableNanoApp(hwParcel.readInt32(), hwParcel.readInt64(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(disableNanoApp);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int queryApps = queryApps(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(queryApps);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_1.IContexthub.kInterfaceName);
                    onSettingChanged(hwParcel.readInt8(), hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_2.IContexthub.kInterfaceName);
                    getHubs_1_2(new android.hardware.contexthub.V1_2.IContexthub.getHubs_1_2Callback() { // from class: android.hardware.contexthub.V1_2.IContexthub.Stub.1
                        @Override // android.hardware.contexthub.V1_2.IContexthub.getHubs_1_2Callback
                        public void onValues(java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHub> arrayList, java.util.ArrayList<java.lang.String> arrayList2) {
                            hwParcel2.writeStatus(0);
                            android.hardware.contexthub.V1_0.ContextHub.writeVectorToParcel(hwParcel2, arrayList);
                            hwParcel2.writeStringVector(arrayList2);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_2.IContexthub.kInterfaceName);
                    int registerCallback_1_2 = registerCallback_1_2(hwParcel.readInt32(), android.hardware.contexthub.V1_2.IContexthubCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(registerCallback_1_2);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_2.IContexthub.kInterfaceName);
                    onSettingChanged_1_2(hwParcel.readInt8(), hwParcel.readInt8());
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
