package android.hardware.gnss.V2_0;

/* loaded from: classes2.dex */
public interface IGnss extends android.hardware.gnss.V1_1.IGnss {
    public static final java.lang.String kInterfaceName = "android.hardware.gnss@2.0::IGnss";

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    android.hardware.gnss.V2_0.IAGnssRil getExtensionAGnssRil_2_0() throws android.os.RemoteException;

    android.hardware.gnss.V2_0.IAGnss getExtensionAGnss_2_0() throws android.os.RemoteException;

    android.hardware.gnss.V2_0.IGnssBatching getExtensionGnssBatching_2_0() throws android.os.RemoteException;

    android.hardware.gnss.V2_0.IGnssConfiguration getExtensionGnssConfiguration_2_0() throws android.os.RemoteException;

    android.hardware.gnss.V2_0.IGnssDebug getExtensionGnssDebug_2_0() throws android.os.RemoteException;

    android.hardware.gnss.V2_0.IGnssMeasurement getExtensionGnssMeasurement_2_0() throws android.os.RemoteException;

    android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrections getExtensionMeasurementCorrections() throws android.os.RemoteException;

    android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControl getExtensionVisibilityControl() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    boolean injectBestLocation_2_0(android.hardware.gnss.V2_0.GnssLocation gnssLocation) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    boolean setCallback_2_0(android.hardware.gnss.V2_0.IGnssCallback iGnssCallback) throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.gnss.V2_0.IGnss asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.V2_0.IGnss)) {
            return (android.hardware.gnss.V2_0.IGnss) queryLocalInterface;
        }
        android.hardware.gnss.V2_0.IGnss.Proxy proxy = new android.hardware.gnss.V2_0.IGnss.Proxy(iHwBinder);
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

    static android.hardware.gnss.V2_0.IGnss castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.gnss.V2_0.IGnss getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.gnss.V2_0.IGnss getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V2_0.IGnss getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.gnss.V2_0.IGnss getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class Proxy implements android.hardware.gnss.V2_0.IGnss {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.gnss@2.0::IGnss]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean setCallback(android.hardware.gnss.V1_0.IGnssCallback iGnssCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeStrongBinder(iGnssCallback == null ? null : iGnssCallback.asBinder());
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

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean start() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
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

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean stop() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
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

        @Override // android.hardware.gnss.V1_0.IGnss
        public void cleanup() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean injectTime(long j, long j2, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeInt64(j);
            hwParcel.writeInt64(j2);
            hwParcel.writeInt32(i);
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

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean injectLocation(double d, double d2, float f) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeDouble(d);
            hwParcel.writeDouble(d2);
            hwParcel.writeFloat(f);
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

        @Override // android.hardware.gnss.V1_0.IGnss
        public void deleteAidingData(short s) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeInt16(s);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean setPositionMode(byte b, int i, int i2, int i3, int i4) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeInt8(b);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            hwParcel.writeInt32(i4);
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

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IAGnssRil getExtensionAGnssRil() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IAGnssRil.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssGeofencing getExtensionGnssGeofencing() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssGeofencing.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IAGnss getExtensionAGnss() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IAGnss.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssNi getExtensionGnssNi() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssNi.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssMeasurement getExtensionGnssMeasurement() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssMeasurement.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssNavigationMessage getExtensionGnssNavigationMessage() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssNavigationMessage.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssXtra getExtensionXtra() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssXtra.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssConfiguration getExtensionGnssConfiguration() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssConfiguration.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssDebug getExtensionGnssDebug() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssDebug.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssBatching getExtensionGnssBatching() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssBatching.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss
        public boolean setCallback_1_1(android.hardware.gnss.V1_1.IGnssCallback iGnssCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
            hwParcel.writeStrongBinder(iGnssCallback == null ? null : iGnssCallback.asBinder());
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss
        public boolean setPositionMode_1_1(byte b, int i, int i2, int i3, int i4, boolean z) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
            hwParcel.writeInt8(b);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            hwParcel.writeInt32(i4);
            hwParcel.writeBool(z);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss
        public android.hardware.gnss.V1_1.IGnssConfiguration getExtensionGnssConfiguration_1_1() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_1.IGnssConfiguration.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss
        public android.hardware.gnss.V1_1.IGnssMeasurement getExtensionGnssMeasurement_1_1() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_1.IGnssMeasurement.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss
        public boolean injectBestLocation(android.hardware.gnss.V1_0.GnssLocation gnssLocation) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
            gnssLocation.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public boolean setCallback_2_0(android.hardware.gnss.V2_0.IGnssCallback iGnssCallback) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            hwParcel.writeStrongBinder(iGnssCallback == null ? null : iGnssCallback.asBinder());
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public android.hardware.gnss.V2_0.IGnssConfiguration getExtensionGnssConfiguration_2_0() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V2_0.IGnssConfiguration.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public android.hardware.gnss.V2_0.IGnssDebug getExtensionGnssDebug_2_0() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V2_0.IGnssDebug.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public android.hardware.gnss.V2_0.IAGnss getExtensionAGnss_2_0() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V2_0.IAGnss.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public android.hardware.gnss.V2_0.IAGnssRil getExtensionAGnssRil_2_0() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V2_0.IAGnssRil.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public android.hardware.gnss.V2_0.IGnssMeasurement getExtensionGnssMeasurement_2_0() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V2_0.IGnssMeasurement.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrections getExtensionMeasurementCorrections() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrections.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControl getExtensionVisibilityControl() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(31, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControl.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public android.hardware.gnss.V2_0.IGnssBatching getExtensionGnssBatching_2_0() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(32, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V2_0.IGnssBatching.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss
        public boolean injectBestLocation_2_0(android.hardware.gnss.V2_0.GnssLocation gnssLocation) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
            gnssLocation.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(33, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.gnss.V2_0.IGnss {
        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.gnss.V2_0.IGnss.kInterfaceName, android.hardware.gnss.V1_1.IGnss.kInterfaceName, android.hardware.gnss.V1_0.IGnss.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.gnss.V2_0.IGnss.kInterfaceName;
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{-11, 96, 95, 72, -62, -5, -97, 35, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, android.hardware.biometrics.face.AcquiredInfo.START, -35, -109, 43, -9, 48, -82, -107, 64, -12, -7, -117, 91, 122, -30, -78, 105, -105, 95, 69, 47, 109, 115}, new byte[]{-75, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, -12, -63, -67, 109, -25, android.hardware.biometrics.face.AcquiredInfo.MOUTH_COVERING_DETECTED, -114, 113, -41, 15, 87, -51, -85, com.android.internal.midi.MidiConstants.STATUS_NOTE_ON, 74, com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE, 36, -95, 47, 61, -18, 62, 33, 115, 119, 10, 69, -125, -68, -62}, new byte[]{-19, -26, -105, 16, -61, -87, 92, 44, -66, -127, -114, 108, -117, -73, 44, 120, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, -126, 63, -84, -27, -4, 33, -63, 119, 49, -78, 111, 65, -39, 77, 101}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V2_0.IGnss, android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.gnss.V2_0.IGnss.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean callback = setCallback(android.hardware.gnss.V1_0.IGnssCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(callback);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean start = start();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(start);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean stop = stop();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(stop);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    cleanup();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean injectTime = injectTime(hwParcel.readInt64(), hwParcel.readInt64(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(injectTime);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean injectLocation = injectLocation(hwParcel.readDouble(), hwParcel.readDouble(), hwParcel.readFloat());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(injectLocation);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    deleteAidingData(hwParcel.readInt16());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean positionMode = setPositionMode(hwParcel.readInt8(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(positionMode);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IAGnssRil extensionAGnssRil = getExtensionAGnssRil();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionAGnssRil != null ? extensionAGnssRil.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssGeofencing extensionGnssGeofencing = getExtensionGnssGeofencing();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssGeofencing != null ? extensionGnssGeofencing.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IAGnss extensionAGnss = getExtensionAGnss();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionAGnss != null ? extensionAGnss.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssNi extensionGnssNi = getExtensionGnssNi();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssNi != null ? extensionGnssNi.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 13:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssMeasurement extensionGnssMeasurement = getExtensionGnssMeasurement();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssMeasurement != null ? extensionGnssMeasurement.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 14:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssNavigationMessage extensionGnssNavigationMessage = getExtensionGnssNavigationMessage();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssNavigationMessage != null ? extensionGnssNavigationMessage.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 15:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssXtra extensionXtra = getExtensionXtra();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionXtra != null ? extensionXtra.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 16:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssConfiguration extensionGnssConfiguration = getExtensionGnssConfiguration();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssConfiguration != null ? extensionGnssConfiguration.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 17:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssDebug extensionGnssDebug = getExtensionGnssDebug();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssDebug != null ? extensionGnssDebug.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 18:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssBatching extensionGnssBatching = getExtensionGnssBatching();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssBatching != null ? extensionGnssBatching.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 19:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
                    boolean callback_1_1 = setCallback_1_1(android.hardware.gnss.V1_1.IGnssCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(callback_1_1);
                    hwParcel2.send();
                    return;
                case 20:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
                    boolean positionMode_1_1 = setPositionMode_1_1(hwParcel.readInt8(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readBool());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(positionMode_1_1);
                    hwParcel2.send();
                    return;
                case 21:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_1.IGnssConfiguration extensionGnssConfiguration_1_1 = getExtensionGnssConfiguration_1_1();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssConfiguration_1_1 != null ? extensionGnssConfiguration_1_1.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 22:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_1.IGnssMeasurement extensionGnssMeasurement_1_1 = getExtensionGnssMeasurement_1_1();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssMeasurement_1_1 != null ? extensionGnssMeasurement_1_1.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 23:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.GnssLocation gnssLocation = new android.hardware.gnss.V1_0.GnssLocation();
                    gnssLocation.readFromParcel(hwParcel);
                    boolean injectBestLocation = injectBestLocation(gnssLocation);
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(injectBestLocation);
                    hwParcel2.send();
                    return;
                case 24:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    boolean callback_2_0 = setCallback_2_0(android.hardware.gnss.V2_0.IGnssCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(callback_2_0);
                    hwParcel2.send();
                    return;
                case 25:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V2_0.IGnssConfiguration extensionGnssConfiguration_2_0 = getExtensionGnssConfiguration_2_0();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssConfiguration_2_0 != null ? extensionGnssConfiguration_2_0.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 26:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V2_0.IGnssDebug extensionGnssDebug_2_0 = getExtensionGnssDebug_2_0();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssDebug_2_0 != null ? extensionGnssDebug_2_0.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 27:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V2_0.IAGnss extensionAGnss_2_0 = getExtensionAGnss_2_0();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionAGnss_2_0 != null ? extensionAGnss_2_0.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 28:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V2_0.IAGnssRil extensionAGnssRil_2_0 = getExtensionAGnssRil_2_0();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionAGnssRil_2_0 != null ? extensionAGnssRil_2_0.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 29:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V2_0.IGnssMeasurement extensionGnssMeasurement_2_0 = getExtensionGnssMeasurement_2_0();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssMeasurement_2_0 != null ? extensionGnssMeasurement_2_0.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 30:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrections extensionMeasurementCorrections = getExtensionMeasurementCorrections();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionMeasurementCorrections != null ? extensionMeasurementCorrections.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 31:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControl extensionVisibilityControl = getExtensionVisibilityControl();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionVisibilityControl != null ? extensionVisibilityControl.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 32:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V2_0.IGnssBatching extensionGnssBatching_2_0 = getExtensionGnssBatching_2_0();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssBatching_2_0 != null ? extensionGnssBatching_2_0.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 33:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V2_0.GnssLocation gnssLocation2 = new android.hardware.gnss.V2_0.GnssLocation();
                    gnssLocation2.readFromParcel(hwParcel);
                    boolean injectBestLocation_2_0 = injectBestLocation_2_0(gnssLocation2);
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(injectBestLocation_2_0);
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
