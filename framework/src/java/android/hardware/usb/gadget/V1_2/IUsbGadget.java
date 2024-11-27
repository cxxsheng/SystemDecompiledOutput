package android.hardware.usb.gadget.V1_2;

/* loaded from: classes2.dex */
public interface IUsbGadget extends android.hardware.usb.gadget.V1_1.IUsbGadget {
    public static final java.lang.String kInterfaceName = "android.hardware.usb.gadget@1.2::IUsbGadget";

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    void getUsbSpeed(android.hardware.usb.gadget.V1_2.IUsbGadgetCallback iUsbGadgetCallback) throws android.os.RemoteException;

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    @Override // android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.usb.gadget.V1_2.IUsbGadget asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.usb.gadget.V1_2.IUsbGadget)) {
            return (android.hardware.usb.gadget.V1_2.IUsbGadget) queryLocalInterface;
        }
        android.hardware.usb.gadget.V1_2.IUsbGadget.Proxy proxy = new android.hardware.usb.gadget.V1_2.IUsbGadget.Proxy(iHwBinder);
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

    static android.hardware.usb.gadget.V1_2.IUsbGadget castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.usb.gadget.V1_2.IUsbGadget getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.usb.gadget.V1_2.IUsbGadget getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.usb.gadget.V1_2.IUsbGadget getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.usb.gadget.V1_2.IUsbGadget getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class Proxy implements android.hardware.usb.gadget.V1_2.IUsbGadget {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.usb.gadget@1.2::IUsbGadget]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.usb.gadget.V1_0.IUsbGadget
        public void setCurrentUsbFunctions(long j, android.hardware.usb.gadget.V1_0.IUsbGadgetCallback iUsbGadgetCallback, long j2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.usb.gadget.V1_0.IUsbGadget.kInterfaceName);
            hwParcel.writeInt64(j);
            hwParcel.writeStrongBinder(iUsbGadgetCallback == null ? null : iUsbGadgetCallback.asBinder());
            hwParcel.writeInt64(j2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.usb.gadget.V1_0.IUsbGadget
        public void getCurrentUsbFunctions(android.hardware.usb.gadget.V1_0.IUsbGadgetCallback iUsbGadgetCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.usb.gadget.V1_0.IUsbGadget.kInterfaceName);
            hwParcel.writeStrongBinder(iUsbGadgetCallback == null ? null : iUsbGadgetCallback.asBinder());
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.usb.gadget.V1_1.IUsbGadget
        public int reset() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.usb.gadget.V1_1.IUsbGadget.kInterfaceName);
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

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget
        public void getUsbSpeed(android.hardware.usb.gadget.V1_2.IUsbGadgetCallback iUsbGadgetCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.usb.gadget.V1_2.IUsbGadget.kInterfaceName);
            hwParcel.writeStrongBinder(iUsbGadgetCallback == null ? null : iUsbGadgetCallback.asBinder());
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.usb.gadget.V1_2.IUsbGadget {
        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.usb.gadget.V1_2.IUsbGadget.kInterfaceName, android.hardware.usb.gadget.V1_1.IUsbGadget.kInterfaceName, android.hardware.usb.gadget.V1_0.IUsbGadget.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.usb.gadget.V1_2.IUsbGadget.kInterfaceName;
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{com.android.internal.midi.MidiConstants.STATUS_SONG_POSITION, -88, 126, 86, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, 65, 69, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, 11, 59, 58, 9, 125, -70, 44, -52, com.android.internal.telephony.GsmAlphabet.GSM_EXTENDED_ESCAPE, 15, -4, 47, -72, 126, -116, -104, 74, -33, -27, -97, 71, -75, -20, -9}, new byte[]{119, 70, -3, -95, -5, -7, -57, -63, 50, -70, -25, 1, -52, 90, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, 9, -28, -11, -25, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -24, 6, 88, 17, 4, 89, 117, -18, -122, android.hardware.biometrics.face.AcquiredInfo.DARK_GLASSES_DETECTED, 109}, new byte[]{-115, -33, -89, 84, 39, 114, -52, 123, -54, android.hardware.biometrics.face.AcquiredInfo.DARK_GLASSES_DETECTED, -105, 43, 45, -123, 98, 100, -17, -93, android.hardware.biometrics.face.AcquiredInfo.DARK_GLASSES_DETECTED, 20, -65, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, -104, -82, -73, -62, 7, -99, -107, 1, -108, -53}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.usb.gadget.V1_2.IUsbGadget, android.hardware.usb.gadget.V1_1.IUsbGadget, android.hardware.usb.gadget.V1_0.IUsbGadget, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.usb.gadget.V1_2.IUsbGadget.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.usb.gadget.V1_0.IUsbGadget.kInterfaceName);
                    setCurrentUsbFunctions(hwParcel.readInt64(), android.hardware.usb.gadget.V1_0.IUsbGadgetCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readInt64());
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.usb.gadget.V1_0.IUsbGadget.kInterfaceName);
                    getCurrentUsbFunctions(android.hardware.usb.gadget.V1_0.IUsbGadgetCallback.asInterface(hwParcel.readStrongBinder()));
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.usb.gadget.V1_1.IUsbGadget.kInterfaceName);
                    int reset = reset();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(reset);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.usb.gadget.V1_2.IUsbGadget.kInterfaceName);
                    getUsbSpeed(android.hardware.usb.gadget.V1_2.IUsbGadgetCallback.asInterface(hwParcel.readStrongBinder()));
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
