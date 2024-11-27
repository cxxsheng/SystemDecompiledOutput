package android.net.ip;

/* loaded from: classes.dex */
public interface IIpClientCallbacks extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$ip$IIpClientCallbacks".replace('$', '.');
    public static final int DTIM_MULTIPLIER_RESET = 0;
    public static final java.lang.String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int VERSION = 21;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void installPacketFilter(byte[] bArr) throws android.os.RemoteException;

    void onIpClientCreated(android.net.ip.IIpClient iIpClient) throws android.os.RemoteException;

    void onLinkPropertiesChange(android.net.LinkProperties linkProperties) throws android.os.RemoteException;

    void onNewDhcpResults(android.net.DhcpResultsParcelable dhcpResultsParcelable) throws android.os.RemoteException;

    void onPostDhcpAction() throws android.os.RemoteException;

    void onPreDhcpAction() throws android.os.RemoteException;

    void onPreconnectionStart(java.util.List<android.net.Layer2PacketParcelable> list) throws android.os.RemoteException;

    void onProvisioningFailure(android.net.LinkProperties linkProperties) throws android.os.RemoteException;

    void onProvisioningSuccess(android.net.LinkProperties linkProperties) throws android.os.RemoteException;

    void onQuit() throws android.os.RemoteException;

    void onReachabilityFailure(android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable reachabilityLossInfoParcelable) throws android.os.RemoteException;

    void onReachabilityLost(java.lang.String str) throws android.os.RemoteException;

    void setFallbackMulticastFilter(boolean z) throws android.os.RemoteException;

    void setMaxDtimMultiplier(int i) throws android.os.RemoteException;

    void setNeighborDiscoveryOffload(boolean z) throws android.os.RemoteException;

    void startReadPacketFilter() throws android.os.RemoteException;

    public static class Default implements android.net.ip.IIpClientCallbacks {
        @Override // android.net.ip.IIpClientCallbacks
        public void onIpClientCreated(android.net.ip.IIpClient iIpClient) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onPreDhcpAction() throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onPostDhcpAction() throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onNewDhcpResults(android.net.DhcpResultsParcelable dhcpResultsParcelable) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onProvisioningSuccess(android.net.LinkProperties linkProperties) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onProvisioningFailure(android.net.LinkProperties linkProperties) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onLinkPropertiesChange(android.net.LinkProperties linkProperties) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onReachabilityLost(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onQuit() throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void installPacketFilter(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void startReadPacketFilter() throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void setFallbackMulticastFilter(boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void setNeighborDiscoveryOffload(boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onPreconnectionStart(java.util.List<android.net.Layer2PacketParcelable> list) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onReachabilityFailure(android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable reachabilityLossInfoParcelable) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void setMaxDtimMultiplier(int i) throws android.os.RemoteException {
        }

        @Override // android.net.ip.IIpClientCallbacks
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.ip.IIpClientCallbacks
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.ip.IIpClientCallbacks {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_installPacketFilter = 10;
        static final int TRANSACTION_onIpClientCreated = 1;
        static final int TRANSACTION_onLinkPropertiesChange = 7;
        static final int TRANSACTION_onNewDhcpResults = 4;
        static final int TRANSACTION_onPostDhcpAction = 3;
        static final int TRANSACTION_onPreDhcpAction = 2;
        static final int TRANSACTION_onPreconnectionStart = 14;
        static final int TRANSACTION_onProvisioningFailure = 6;
        static final int TRANSACTION_onProvisioningSuccess = 5;
        static final int TRANSACTION_onQuit = 9;
        static final int TRANSACTION_onReachabilityFailure = 15;
        static final int TRANSACTION_onReachabilityLost = 8;
        static final int TRANSACTION_setFallbackMulticastFilter = 12;
        static final int TRANSACTION_setMaxDtimMultiplier = 16;
        static final int TRANSACTION_setNeighborDiscoveryOffload = 13;
        static final int TRANSACTION_startReadPacketFilter = 11;

        public Stub() {
            attachInterface(this, android.net.ip.IIpClientCallbacks.DESCRIPTOR);
        }

        public static android.net.ip.IIpClientCallbacks asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.ip.IIpClientCallbacks)) {
                return (android.net.ip.IIpClientCallbacks) queryLocalInterface;
            }
            return new android.net.ip.IIpClientCallbacks.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.ip.IIpClientCallbacks.DESCRIPTOR;
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
                    onIpClientCreated(android.net.ip.IIpClient.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 2:
                    onPreDhcpAction();
                    return true;
                case 3:
                    onPostDhcpAction();
                    return true;
                case 4:
                    onNewDhcpResults((android.net.DhcpResultsParcelable) parcel.readTypedObject(android.net.DhcpResultsParcelable.CREATOR));
                    return true;
                case 5:
                    onProvisioningSuccess((android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR));
                    return true;
                case 6:
                    onProvisioningFailure((android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR));
                    return true;
                case 7:
                    onLinkPropertiesChange((android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR));
                    return true;
                case 8:
                    onReachabilityLost(parcel.readString());
                    return true;
                case 9:
                    onQuit();
                    return true;
                case 10:
                    installPacketFilter(parcel.createByteArray());
                    return true;
                case 11:
                    startReadPacketFilter();
                    return true;
                case 12:
                    setFallbackMulticastFilter(parcel.readBoolean());
                    return true;
                case 13:
                    setNeighborDiscoveryOffload(parcel.readBoolean());
                    return true;
                case 14:
                    onPreconnectionStart(parcel.createTypedArrayList(android.net.Layer2PacketParcelable.CREATOR));
                    return true;
                case 15:
                    onReachabilityFailure((android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable) parcel.readTypedObject(android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable.CREATOR));
                    return true;
                case 16:
                    setMaxDtimMultiplier(parcel.readInt());
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.ip.IIpClientCallbacks {
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
                return android.net.ip.IIpClientCallbacks.DESCRIPTOR;
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onIpClientCreated(android.net.ip.IIpClient iIpClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeStrongInterface(iIpClient);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onIpClientCreated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onPreDhcpAction() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onPreDhcpAction is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onPostDhcpAction() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onPostDhcpAction is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onNewDhcpResults(android.net.DhcpResultsParcelable dhcpResultsParcelable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(dhcpResultsParcelable, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onNewDhcpResults is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onProvisioningSuccess(android.net.LinkProperties linkProperties) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onProvisioningSuccess is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onProvisioningFailure(android.net.LinkProperties linkProperties) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onProvisioningFailure is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onLinkPropertiesChange(android.net.LinkProperties linkProperties) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onLinkPropertiesChange is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onReachabilityLost(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onReachabilityLost is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onQuit() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onQuit is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void installPacketFilter(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method installPacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void startReadPacketFilter() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startReadPacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void setFallbackMulticastFilter(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setFallbackMulticastFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void setNeighborDiscoveryOffload(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNeighborDiscoveryOffload is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onPreconnectionStart(java.util.List<android.net.Layer2PacketParcelable> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    android.net.ip.IIpClientCallbacks._Parcel.writeTypedList(obtain, list, 0);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onPreconnectionStart is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void onReachabilityFailure(android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable reachabilityLossInfoParcelable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(reachabilityLossInfoParcelable, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onReachabilityFailure is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public void setMaxDtimMultiplier(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setMaxDtimMultiplier is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
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

            @Override // android.net.ip.IIpClientCallbacks
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.ip.IIpClientCallbacks.DESCRIPTOR);
                            this.mRemote.transact(android.net.ip.IIpClientCallbacks.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends android.os.Parcelable> void writeTypedList(android.os.Parcel parcel, java.util.List<T> list, int i) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeTypedObject(list.get(i2), i);
            }
        }
    }
}
