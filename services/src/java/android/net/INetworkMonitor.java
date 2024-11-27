package android.net;

/* loaded from: classes.dex */
public interface INetworkMonitor extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$INetworkMonitor".replace('$', '.');
    public static final java.lang.String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int NETWORK_TEST_RESULT_INVALID = 1;
    public static final int NETWORK_TEST_RESULT_PARTIAL_CONNECTIVITY = 2;
    public static final int NETWORK_TEST_RESULT_VALID = 0;
    public static final int NETWORK_VALIDATION_PROBE_DNS = 4;
    public static final int NETWORK_VALIDATION_PROBE_FALLBACK = 32;
    public static final int NETWORK_VALIDATION_PROBE_HTTP = 8;
    public static final int NETWORK_VALIDATION_PROBE_HTTPS = 16;
    public static final int NETWORK_VALIDATION_PROBE_PRIVDNS = 64;
    public static final int NETWORK_VALIDATION_RESULT_PARTIAL = 2;
    public static final int NETWORK_VALIDATION_RESULT_SKIPPED = 4;
    public static final int NETWORK_VALIDATION_RESULT_VALID = 1;
    public static final int VERSION = 21;

    void forceReevaluation(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void launchCaptivePortalApp() throws android.os.RemoteException;

    void notifyCaptivePortalAppFinished(int i) throws android.os.RemoteException;

    void notifyDnsResponse(int i) throws android.os.RemoteException;

    void notifyLinkPropertiesChanged(android.net.LinkProperties linkProperties) throws android.os.RemoteException;

    void notifyNetworkCapabilitiesChanged(android.net.NetworkCapabilities networkCapabilities) throws android.os.RemoteException;

    void notifyNetworkConnected(android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities) throws android.os.RemoteException;

    void notifyNetworkConnectedParcel(android.net.networkstack.aidl.NetworkMonitorParameters networkMonitorParameters) throws android.os.RemoteException;

    void notifyNetworkDisconnected() throws android.os.RemoteException;

    void notifyPrivateDnsChanged(android.net.PrivateDnsConfigParcel privateDnsConfigParcel) throws android.os.RemoteException;

    void setAcceptPartialConnectivity() throws android.os.RemoteException;

    void start() throws android.os.RemoteException;

    public static class Default implements android.net.INetworkMonitor {
        @Override // android.net.INetworkMonitor
        public void start() throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void launchCaptivePortalApp() throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void notifyCaptivePortalAppFinished(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void setAcceptPartialConnectivity() throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void forceReevaluation(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void notifyPrivateDnsChanged(android.net.PrivateDnsConfigParcel privateDnsConfigParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void notifyDnsResponse(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void notifyNetworkConnected(android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void notifyNetworkDisconnected() throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void notifyLinkPropertiesChanged(android.net.LinkProperties linkProperties) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void notifyNetworkCapabilitiesChanged(android.net.NetworkCapabilities networkCapabilities) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public void notifyNetworkConnectedParcel(android.net.networkstack.aidl.NetworkMonitorParameters networkMonitorParameters) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkMonitor
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.INetworkMonitor
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetworkMonitor {
        static final int TRANSACTION_forceReevaluation = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_launchCaptivePortalApp = 2;
        static final int TRANSACTION_notifyCaptivePortalAppFinished = 3;
        static final int TRANSACTION_notifyDnsResponse = 7;
        static final int TRANSACTION_notifyLinkPropertiesChanged = 10;
        static final int TRANSACTION_notifyNetworkCapabilitiesChanged = 11;
        static final int TRANSACTION_notifyNetworkConnected = 8;
        static final int TRANSACTION_notifyNetworkConnectedParcel = 12;
        static final int TRANSACTION_notifyNetworkDisconnected = 9;
        static final int TRANSACTION_notifyPrivateDnsChanged = 6;
        static final int TRANSACTION_setAcceptPartialConnectivity = 4;
        static final int TRANSACTION_start = 1;

        public Stub() {
            attachInterface(this, android.net.INetworkMonitor.DESCRIPTOR);
        }

        public static android.net.INetworkMonitor asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.INetworkMonitor.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetworkMonitor)) {
                return (android.net.INetworkMonitor) queryLocalInterface;
            }
            return new android.net.INetworkMonitor.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.INetworkMonitor.DESCRIPTOR;
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
                    start();
                    return true;
                case 2:
                    launchCaptivePortalApp();
                    return true;
                case 3:
                    notifyCaptivePortalAppFinished(parcel.readInt());
                    return true;
                case 4:
                    setAcceptPartialConnectivity();
                    return true;
                case 5:
                    forceReevaluation(parcel.readInt());
                    return true;
                case 6:
                    notifyPrivateDnsChanged((android.net.PrivateDnsConfigParcel) parcel.readTypedObject(android.net.PrivateDnsConfigParcel.CREATOR));
                    return true;
                case 7:
                    notifyDnsResponse(parcel.readInt());
                    return true;
                case 8:
                    notifyNetworkConnected((android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR), (android.net.NetworkCapabilities) parcel.readTypedObject(android.net.NetworkCapabilities.CREATOR));
                    return true;
                case 9:
                    notifyNetworkDisconnected();
                    return true;
                case 10:
                    notifyLinkPropertiesChanged((android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR));
                    return true;
                case 11:
                    notifyNetworkCapabilitiesChanged((android.net.NetworkCapabilities) parcel.readTypedObject(android.net.NetworkCapabilities.CREATOR));
                    return true;
                case 12:
                    notifyNetworkConnectedParcel((android.net.networkstack.aidl.NetworkMonitorParameters) parcel.readTypedObject(android.net.networkstack.aidl.NetworkMonitorParameters.CREATOR));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.INetworkMonitor {
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
                return android.net.INetworkMonitor.DESCRIPTOR;
            }

            @Override // android.net.INetworkMonitor
            public void start() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method start is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void launchCaptivePortalApp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method launchCaptivePortalApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyCaptivePortalAppFinished(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyCaptivePortalAppFinished is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void setAcceptPartialConnectivity() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setAcceptPartialConnectivity is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void forceReevaluation(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method forceReevaluation is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyPrivateDnsChanged(android.net.PrivateDnsConfigParcel privateDnsConfigParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(privateDnsConfigParcel, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyPrivateDnsChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyDnsResponse(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyDnsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyNetworkConnected(android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    obtain.writeTypedObject(networkCapabilities, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyNetworkConnected is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyNetworkDisconnected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyNetworkDisconnected is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyLinkPropertiesChanged(android.net.LinkProperties linkProperties) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyLinkPropertiesChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyNetworkCapabilitiesChanged(android.net.NetworkCapabilities networkCapabilities) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(networkCapabilities, 0);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyNetworkCapabilitiesChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyNetworkConnectedParcel(android.net.networkstack.aidl.NetworkMonitorParameters networkMonitorParameters) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(networkMonitorParameters, 0);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyNetworkConnectedParcel is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
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

            @Override // android.net.INetworkMonitor
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.INetworkMonitor.DESCRIPTOR);
                            this.mRemote.transact(android.net.INetworkMonitor.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
