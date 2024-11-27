package android.net.ip;

/* loaded from: classes.dex */
public interface IIpClient extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$ip$IIpClient".replace('$', '.');
    public static final java.lang.String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int HOSTNAME_SETTING_DO_NOT_SEND = 2;
    public static final int HOSTNAME_SETTING_SEND = 1;
    public static final int HOSTNAME_SETTING_UNSET = 0;
    public static final int PROV_IPV4_DHCP = 2;
    public static final int PROV_IPV4_DISABLED = 0;
    public static final int PROV_IPV4_STATIC = 1;
    public static final int PROV_IPV6_DISABLED = 0;
    public static final int PROV_IPV6_LINKLOCAL = 2;
    public static final int PROV_IPV6_SLAAC = 1;
    public static final int VERSION = 21;

    void addKeepalivePacketFilter(int i, android.net.TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable) throws android.os.RemoteException;

    void addNattKeepalivePacketFilter(int i, android.net.NattKeepalivePacketDataParcelable nattKeepalivePacketDataParcelable) throws android.os.RemoteException;

    void completedPreDhcpAction() throws android.os.RemoteException;

    void confirmConfiguration() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void notifyPreconnectionComplete(boolean z) throws android.os.RemoteException;

    void readPacketFilterComplete(byte[] bArr) throws android.os.RemoteException;

    void removeKeepalivePacketFilter(int i) throws android.os.RemoteException;

    void setHttpProxy(android.net.ProxyInfo proxyInfo) throws android.os.RemoteException;

    void setL2KeyAndGroupHint(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setMulticastFilter(boolean z) throws android.os.RemoteException;

    void setTcpBufferSizes(java.lang.String str) throws android.os.RemoteException;

    void shutdown() throws android.os.RemoteException;

    void startProvisioning(android.net.ProvisioningConfigurationParcelable provisioningConfigurationParcelable) throws android.os.RemoteException;

    void stop() throws android.os.RemoteException;

    void updateApfCapabilities(android.net.apf.ApfCapabilities apfCapabilities) throws android.os.RemoteException;

    void updateLayer2Information(android.net.Layer2InformationParcelable layer2InformationParcelable) throws android.os.RemoteException;

    public static class Default implements android.net.ip.IIpClient {
        @Override // android.net.ip.IIpClient
        public void completedPreDhcpAction() throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void confirmConfiguration() throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void readPacketFilterComplete(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void shutdown() throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void startProvisioning(android.net.ProvisioningConfigurationParcelable provisioningConfigurationParcelable) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void stop() throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void setTcpBufferSizes(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void setHttpProxy(android.net.ProxyInfo proxyInfo) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void setMulticastFilter(boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void addKeepalivePacketFilter(int i, android.net.TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void removeKeepalivePacketFilter(int i) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void setL2KeyAndGroupHint(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void addNattKeepalivePacketFilter(int i, android.net.NattKeepalivePacketDataParcelable nattKeepalivePacketDataParcelable) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void notifyPreconnectionComplete(boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void updateLayer2Information(android.net.Layer2InformationParcelable layer2InformationParcelable) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public void updateApfCapabilities(android.net.apf.ApfCapabilities apfCapabilities) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClient
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.ip.IIpClient
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.ip.IIpClient {
        static final int TRANSACTION_addKeepalivePacketFilter = 10;
        static final int TRANSACTION_addNattKeepalivePacketFilter = 13;
        static final int TRANSACTION_completedPreDhcpAction = 1;
        static final int TRANSACTION_confirmConfiguration = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_notifyPreconnectionComplete = 14;
        static final int TRANSACTION_readPacketFilterComplete = 3;
        static final int TRANSACTION_removeKeepalivePacketFilter = 11;
        static final int TRANSACTION_setHttpProxy = 8;
        static final int TRANSACTION_setL2KeyAndGroupHint = 12;
        static final int TRANSACTION_setMulticastFilter = 9;
        static final int TRANSACTION_setTcpBufferSizes = 7;
        static final int TRANSACTION_shutdown = 4;
        static final int TRANSACTION_startProvisioning = 5;
        static final int TRANSACTION_stop = 6;
        static final int TRANSACTION_updateApfCapabilities = 16;
        static final int TRANSACTION_updateLayer2Information = 15;

        public Stub() {
            attachInterface(this, android.net.ip.IIpClient.DESCRIPTOR);
        }

        public static android.net.ip.IIpClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.ip.IIpClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.ip.IIpClient)) {
                return (android.net.ip.IIpClient) queryLocalInterface;
            }
            return new android.net.ip.IIpClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.ip.IIpClient.DESCRIPTOR;
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
                    completedPreDhcpAction();
                    return true;
                case 2:
                    confirmConfiguration();
                    return true;
                case 3:
                    readPacketFilterComplete(parcel.createByteArray());
                    return true;
                case 4:
                    shutdown();
                    return true;
                case 5:
                    startProvisioning((android.net.ProvisioningConfigurationParcelable) parcel.readTypedObject(android.net.ProvisioningConfigurationParcelable.CREATOR));
                    return true;
                case 6:
                    stop();
                    return true;
                case 7:
                    setTcpBufferSizes(parcel.readString());
                    return true;
                case 8:
                    setHttpProxy((android.net.ProxyInfo) parcel.readTypedObject(android.net.ProxyInfo.CREATOR));
                    return true;
                case 9:
                    setMulticastFilter(parcel.readBoolean());
                    return true;
                case 10:
                    addKeepalivePacketFilter(parcel.readInt(), (android.net.TcpKeepalivePacketDataParcelable) parcel.readTypedObject(android.net.TcpKeepalivePacketDataParcelable.CREATOR));
                    return true;
                case 11:
                    removeKeepalivePacketFilter(parcel.readInt());
                    return true;
                case 12:
                    setL2KeyAndGroupHint(parcel.readString(), parcel.readString());
                    return true;
                case 13:
                    addNattKeepalivePacketFilter(parcel.readInt(), (android.net.NattKeepalivePacketDataParcelable) parcel.readTypedObject(android.net.NattKeepalivePacketDataParcelable.CREATOR));
                    return true;
                case 14:
                    notifyPreconnectionComplete(parcel.readBoolean());
                    return true;
                case 15:
                    updateLayer2Information((android.net.Layer2InformationParcelable) parcel.readTypedObject(android.net.Layer2InformationParcelable.CREATOR));
                    return true;
                case 16:
                    updateApfCapabilities((android.net.apf.ApfCapabilities) parcel.readTypedObject(android.net.apf.ApfCapabilities.CREATOR));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.ip.IIpClient {
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
                return android.net.ip.IIpClient.DESCRIPTOR;
            }

            @Override // android.net.ip.IIpClient
            public void completedPreDhcpAction() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method completedPreDhcpAction is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void confirmConfiguration() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method confirmConfiguration is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void readPacketFilterComplete(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method readPacketFilterComplete is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void shutdown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method shutdown is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void startProvisioning(android.net.ProvisioningConfigurationParcelable provisioningConfigurationParcelable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeTypedObject(provisioningConfigurationParcelable, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startProvisioning is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void stop() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stop is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void setTcpBufferSizes(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setTcpBufferSizes is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void setHttpProxy(android.net.ProxyInfo proxyInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeTypedObject(proxyInfo, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setHttpProxy is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void setMulticastFilter(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setMulticastFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void addKeepalivePacketFilter(int i, android.net.TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(tcpKeepalivePacketDataParcelable, 0);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method addKeepalivePacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void removeKeepalivePacketFilter(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method removeKeepalivePacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void setL2KeyAndGroupHint(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setL2KeyAndGroupHint is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void addNattKeepalivePacketFilter(int i, android.net.NattKeepalivePacketDataParcelable nattKeepalivePacketDataParcelable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(nattKeepalivePacketDataParcelable, 0);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method addNattKeepalivePacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void notifyPreconnectionComplete(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyPreconnectionComplete is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void updateLayer2Information(android.net.Layer2InformationParcelable layer2InformationParcelable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeTypedObject(layer2InformationParcelable, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method updateLayer2Information is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public void updateApfCapabilities(android.net.apf.ApfCapabilities apfCapabilities) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                    obtain.writeTypedObject(apfCapabilities, 0);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method updateApfCapabilities is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
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

            @Override // android.net.ip.IIpClient
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.ip.IIpClient.DESCRIPTOR);
                            this.mRemote.transact(android.net.ip.IIpClient.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
