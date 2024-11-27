package android.hardware.configstore.V1_0;

/* loaded from: classes.dex */
public interface ISurfaceFlingerConfigs extends android.hidl.base.V1_0.IBase {
    public static final java.lang.String kInterfaceName = "android.hardware.configstore@1.0::ISurfaceFlingerConfigs";

    @Override // android.hidl.base.V1_0.IBase
    android.os.IHwBinder asBinder();

    @Override // android.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    android.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalBool hasHDRDisplay() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalBool hasSyncFramework() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalBool hasWideColorDisplay() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalInt64 maxFrameBufferAcquiredBuffers() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalUInt64 maxVirtualDisplaySize() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalInt64 presentTimeOffsetFromVSyncNs() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalBool startGraphicsAllocatorService() throws android.os.RemoteException;

    @Override // android.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalBool useContextPriority() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalBool useHwcForRGBtoYUV() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalBool useVrFlinger() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalInt64 vsyncEventPhaseOffsetNs() throws android.os.RemoteException;

    android.hardware.configstore.V1_0.OptionalInt64 vsyncSfEventPhaseOffsetNs() throws android.os.RemoteException;

    static android.hardware.configstore.V1_0.ISurfaceFlingerConfigs asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.configstore.V1_0.ISurfaceFlingerConfigs)) {
            return (android.hardware.configstore.V1_0.ISurfaceFlingerConfigs) queryLocalInterface;
        }
        android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.Proxy proxy = new android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.Proxy(iHwBinder);
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

    static android.hardware.configstore.V1_0.ISurfaceFlingerConfigs castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.configstore.V1_0.ISurfaceFlingerConfigs getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.configstore.V1_0.ISurfaceFlingerConfigs getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.configstore.V1_0.ISurfaceFlingerConfigs getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.configstore.V1_0.ISurfaceFlingerConfigs getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class Proxy implements android.hardware.configstore.V1_0.ISurfaceFlingerConfigs {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            java.util.Objects.requireNonNull(iHwBinder);
            this.mRemote = iHwBinder;
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.configstore@1.0::ISurfaceFlingerConfigs]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalInt64 vsyncEventPhaseOffsetNs() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalInt64 optionalInt64 = new android.hardware.configstore.V1_0.OptionalInt64();
                optionalInt64.readFromParcel(hwParcel2);
                return optionalInt64;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalInt64 vsyncSfEventPhaseOffsetNs() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalInt64 optionalInt64 = new android.hardware.configstore.V1_0.OptionalInt64();
                optionalInt64.readFromParcel(hwParcel2);
                return optionalInt64;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalBool useContextPriority() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalBool optionalBool = new android.hardware.configstore.V1_0.OptionalBool();
                optionalBool.readFromParcel(hwParcel2);
                return optionalBool;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalBool hasWideColorDisplay() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalBool optionalBool = new android.hardware.configstore.V1_0.OptionalBool();
                optionalBool.readFromParcel(hwParcel2);
                return optionalBool;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalBool hasHDRDisplay() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalBool optionalBool = new android.hardware.configstore.V1_0.OptionalBool();
                optionalBool.readFromParcel(hwParcel2);
                return optionalBool;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalInt64 presentTimeOffsetFromVSyncNs() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalInt64 optionalInt64 = new android.hardware.configstore.V1_0.OptionalInt64();
                optionalInt64.readFromParcel(hwParcel2);
                return optionalInt64;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalBool useHwcForRGBtoYUV() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalBool optionalBool = new android.hardware.configstore.V1_0.OptionalBool();
                optionalBool.readFromParcel(hwParcel2);
                return optionalBool;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalUInt64 maxVirtualDisplaySize() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalUInt64 optionalUInt64 = new android.hardware.configstore.V1_0.OptionalUInt64();
                optionalUInt64.readFromParcel(hwParcel2);
                return optionalUInt64;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalBool hasSyncFramework() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalBool optionalBool = new android.hardware.configstore.V1_0.OptionalBool();
                optionalBool.readFromParcel(hwParcel2);
                return optionalBool;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalBool useVrFlinger() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalBool optionalBool = new android.hardware.configstore.V1_0.OptionalBool();
                optionalBool.readFromParcel(hwParcel2);
                return optionalBool;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalInt64 maxFrameBufferAcquiredBuffers() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalInt64 optionalInt64 = new android.hardware.configstore.V1_0.OptionalInt64();
                optionalInt64.readFromParcel(hwParcel2);
                return optionalInt64;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs
        public android.hardware.configstore.V1_0.OptionalBool startGraphicsAllocatorService() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hardware.configstore.V1_0.OptionalBool optionalBool = new android.hardware.configstore.V1_0.OptionalBool();
                optionalBool.readFromParcel(hwParcel2);
                return optionalBool;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
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

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
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

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public java.lang.String interfaceDescriptor() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
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

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
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

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public void ping() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public android.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.hidl.base.V1_0.DebugInfo debugInfo = new android.hidl.base.V1_0.DebugInfo();
                debugInfo.readFromParcel(hwParcel2);
                return debugInfo;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.configstore.V1_0.ISurfaceFlingerConfigs {
        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName, android.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName;
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{-38, 51, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_PHYSICAL, 68, 3, -1, 93, 96, -13, 71, 55, 17, -111, 123, -103, 72, -26, 72, 74, 66, 96, -75, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, 122, -51, -81, -79, 17, 25, 58, -99, -30}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_PHYSICAL, -17, 5, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, -13, -51, 105, 87, 19, -109, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public final android.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.hidl.base.V1_0.DebugInfo debugInfo = new android.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.hardware.configstore.V1_0.ISurfaceFlingerConfigs, android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName.equals(str)) {
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

        public void onTransact(int i, android.os.HwParcel hwParcel, android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalInt64 vsyncEventPhaseOffsetNs = vsyncEventPhaseOffsetNs();
                    hwParcel2.writeStatus(0);
                    vsyncEventPhaseOffsetNs.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalInt64 vsyncSfEventPhaseOffsetNs = vsyncSfEventPhaseOffsetNs();
                    hwParcel2.writeStatus(0);
                    vsyncSfEventPhaseOffsetNs.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalBool useContextPriority = useContextPriority();
                    hwParcel2.writeStatus(0);
                    useContextPriority.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalBool hasWideColorDisplay = hasWideColorDisplay();
                    hwParcel2.writeStatus(0);
                    hasWideColorDisplay.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalBool hasHDRDisplay = hasHDRDisplay();
                    hwParcel2.writeStatus(0);
                    hasHDRDisplay.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalInt64 presentTimeOffsetFromVSyncNs = presentTimeOffsetFromVSyncNs();
                    hwParcel2.writeStatus(0);
                    presentTimeOffsetFromVSyncNs.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalBool useHwcForRGBtoYUV = useHwcForRGBtoYUV();
                    hwParcel2.writeStatus(0);
                    useHwcForRGBtoYUV.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalUInt64 maxVirtualDisplaySize = maxVirtualDisplaySize();
                    hwParcel2.writeStatus(0);
                    maxVirtualDisplaySize.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalBool hasSyncFramework = hasSyncFramework();
                    hwParcel2.writeStatus(0);
                    hasSyncFramework.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalBool useVrFlinger = useVrFlinger();
                    hwParcel2.writeStatus(0);
                    useVrFlinger.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalInt64 maxFrameBufferAcquiredBuffers = maxFrameBufferAcquiredBuffers();
                    hwParcel2.writeStatus(0);
                    maxFrameBufferAcquiredBuffers.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.configstore.V1_0.ISurfaceFlingerConfigs.kInterfaceName);
                    android.hardware.configstore.V1_0.OptionalBool startGraphicsAllocatorService = startGraphicsAllocatorService();
                    hwParcel2.writeStatus(0);
                    startGraphicsAllocatorService.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 256067662:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    java.util.ArrayList<java.lang.String> interfaceChain = interfaceChain();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(interfaceChain);
                    hwParcel2.send();
                    return;
                case 256131655:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 256136003:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    java.lang.String interfaceDescriptor = interfaceDescriptor();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(interfaceDescriptor);
                    hwParcel2.send();
                    return;
                case 256398152:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
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
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    setHALInstrumentation();
                    return;
                case 256660548:
                default:
                    return;
                case 256921159:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    ping();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 257049926:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    android.hidl.base.V1_0.DebugInfo debugInfo = getDebugInfo();
                    hwParcel2.writeStatus(0);
                    debugInfo.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 257120595:
                    hwParcel.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
            }
        }
    }
}
