package android.net;

/* loaded from: classes.dex */
public interface INetworkMonitorCallbacks extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$INetworkMonitorCallbacks".replace('$', '.');
    public static final java.lang.String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int VERSION = 21;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void hideProvisioningNotification() throws android.os.RemoteException;

    void notifyCaptivePortalDataChanged(android.net.CaptivePortalData captivePortalData) throws android.os.RemoteException;

    void notifyDataStallSuspected(android.net.DataStallReportParcelable dataStallReportParcelable) throws android.os.RemoteException;

    void notifyNetworkTested(int i, java.lang.String str) throws android.os.RemoteException;

    void notifyNetworkTestedWithExtras(android.net.NetworkTestResultParcelable networkTestResultParcelable) throws android.os.RemoteException;

    void notifyPrivateDnsConfigResolved(android.net.PrivateDnsConfigParcel privateDnsConfigParcel) throws android.os.RemoteException;

    void notifyProbeStatusChanged(int i, int i2) throws android.os.RemoteException;

    void onNetworkMonitorCreated(android.net.INetworkMonitor iNetworkMonitor) throws android.os.RemoteException;

    void showProvisioningNotification(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements android.net.INetworkMonitorCallbacks {
        @Override // android.net.INetworkMonitorCallbacks
        public void onNetworkMonitorCreated(android.net.INetworkMonitor iNetworkMonitor) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyNetworkTested(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyPrivateDnsConfigResolved(android.net.PrivateDnsConfigParcel privateDnsConfigParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitorCallbacks
        public void showProvisioningNotification(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitorCallbacks
        public void hideProvisioningNotification() throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyProbeStatusChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyNetworkTestedWithExtras(android.net.NetworkTestResultParcelable networkTestResultParcelable) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyDataStallSuspected(android.net.DataStallReportParcelable dataStallReportParcelable) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitorCallbacks
        public void notifyCaptivePortalDataChanged(android.net.CaptivePortalData captivePortalData) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitorCallbacks
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.INetworkMonitorCallbacks
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetworkMonitorCallbacks {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_hideProvisioningNotification = 5;
        static final int TRANSACTION_notifyCaptivePortalDataChanged = 9;
        static final int TRANSACTION_notifyDataStallSuspected = 8;
        static final int TRANSACTION_notifyNetworkTested = 2;
        static final int TRANSACTION_notifyNetworkTestedWithExtras = 7;
        static final int TRANSACTION_notifyPrivateDnsConfigResolved = 3;
        static final int TRANSACTION_notifyProbeStatusChanged = 6;
        static final int TRANSACTION_onNetworkMonitorCreated = 1;
        static final int TRANSACTION_showProvisioningNotification = 4;

        public Stub() {
            attachInterface(this, android.net.INetworkMonitorCallbacks.DESCRIPTOR);
        }

        public static android.net.INetworkMonitorCallbacks asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetworkMonitorCallbacks)) {
                return (android.net.INetworkMonitorCallbacks) queryLocalInterface;
            }
            return new android.net.INetworkMonitorCallbacks.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.INetworkMonitorCallbacks.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    onNetworkMonitorCreated(android.net.INetworkMonitor.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 2:
                    notifyNetworkTested(parcel.readInt(), parcel.readString());
                    return true;
                case 3:
                    notifyPrivateDnsConfigResolved((android.net.PrivateDnsConfigParcel) parcel.readTypedObject(android.net.PrivateDnsConfigParcel.CREATOR));
                    return true;
                case 4:
                    showProvisioningNotification(parcel.readString(), parcel.readString());
                    return true;
                case 5:
                    hideProvisioningNotification();
                    return true;
                case 6:
                    notifyProbeStatusChanged(parcel.readInt(), parcel.readInt());
                    return true;
                case 7:
                    notifyNetworkTestedWithExtras((android.net.NetworkTestResultParcelable) parcel.readTypedObject(android.net.NetworkTestResultParcelable.CREATOR));
                    return true;
                case 8:
                    notifyDataStallSuspected((android.net.DataStallReportParcelable) parcel.readTypedObject(android.net.DataStallReportParcelable.CREATOR));
                    return true;
                case 9:
                    notifyCaptivePortalDataChanged((android.net.CaptivePortalData) parcel.readTypedObject(android.net.CaptivePortalData.CREATOR));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.INetworkMonitorCallbacks {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.INetworkMonitorCallbacks.DESCRIPTOR;
            }

            @Override // android.net.INetworkMonitorCallbacks
            public void onNetworkMonitorCreated(android.net.INetworkMonitor iNetworkMonitor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkMonitor);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onNetworkMonitorCreated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public void notifyNetworkTested(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyNetworkTested is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public void notifyPrivateDnsConfigResolved(android.net.PrivateDnsConfigParcel privateDnsConfigParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(privateDnsConfigParcel, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyPrivateDnsConfigResolved is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public void showProvisioningNotification(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method showProvisioningNotification is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public void hideProvisioningNotification() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method hideProvisioningNotification is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public void notifyProbeStatusChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyProbeStatusChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public void notifyNetworkTestedWithExtras(android.net.NetworkTestResultParcelable networkTestResultParcelable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(networkTestResultParcelable, 0);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyNetworkTestedWithExtras is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public void notifyDataStallSuspected(android.net.DataStallReportParcelable dataStallReportParcelable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(dataStallReportParcelable, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyDataStallSuspected is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public void notifyCaptivePortalDataChanged(android.net.CaptivePortalData captivePortalData) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(captivePortalData, 0);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyCaptivePortalDataChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitorCallbacks
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.INetworkMonitorCallbacks
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.INetworkMonitorCallbacks.DESCRIPTOR);
                            this.mRemote.transact(android.net.INetworkMonitorCallbacks.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (java.lang.Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
