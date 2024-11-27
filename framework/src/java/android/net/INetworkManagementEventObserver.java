package android.net;

/* loaded from: classes2.dex */
public interface INetworkManagementEventObserver extends android.os.IInterface {
    void addressRemoved(java.lang.String str, android.net.LinkAddress linkAddress) throws android.os.RemoteException;

    void addressUpdated(java.lang.String str, android.net.LinkAddress linkAddress) throws android.os.RemoteException;

    void interfaceAdded(java.lang.String str) throws android.os.RemoteException;

    void interfaceClassDataActivityChanged(int i, boolean z, long j, int i2) throws android.os.RemoteException;

    void interfaceDnsServerInfo(java.lang.String str, long j, java.lang.String[] strArr) throws android.os.RemoteException;

    void interfaceLinkStateChanged(java.lang.String str, boolean z) throws android.os.RemoteException;

    void interfaceRemoved(java.lang.String str) throws android.os.RemoteException;

    void interfaceStatusChanged(java.lang.String str, boolean z) throws android.os.RemoteException;

    void limitReached(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void routeRemoved(android.net.RouteInfo routeInfo) throws android.os.RemoteException;

    void routeUpdated(android.net.RouteInfo routeInfo) throws android.os.RemoteException;

    public static class Default implements android.net.INetworkManagementEventObserver {
        @Override // android.net.INetworkManagementEventObserver
        public void interfaceStatusChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceLinkStateChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceAdded(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceRemoved(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void addressUpdated(java.lang.String str, android.net.LinkAddress linkAddress) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void addressRemoved(java.lang.String str, android.net.LinkAddress linkAddress) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void limitReached(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceClassDataActivityChanged(int i, boolean z, long j, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void interfaceDnsServerInfo(java.lang.String str, long j, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void routeUpdated(android.net.RouteInfo routeInfo) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkManagementEventObserver
        public void routeRemoved(android.net.RouteInfo routeInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetworkManagementEventObserver {
        public static final java.lang.String DESCRIPTOR = "android.net.INetworkManagementEventObserver";
        static final int TRANSACTION_addressRemoved = 6;
        static final int TRANSACTION_addressUpdated = 5;
        static final int TRANSACTION_interfaceAdded = 3;
        static final int TRANSACTION_interfaceClassDataActivityChanged = 8;
        static final int TRANSACTION_interfaceDnsServerInfo = 9;
        static final int TRANSACTION_interfaceLinkStateChanged = 2;
        static final int TRANSACTION_interfaceRemoved = 4;
        static final int TRANSACTION_interfaceStatusChanged = 1;
        static final int TRANSACTION_limitReached = 7;
        static final int TRANSACTION_routeRemoved = 11;
        static final int TRANSACTION_routeUpdated = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.net.INetworkManagementEventObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetworkManagementEventObserver)) {
                return (android.net.INetworkManagementEventObserver) queryLocalInterface;
            }
            return new android.net.INetworkManagementEventObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "interfaceStatusChanged";
                case 2:
                    return "interfaceLinkStateChanged";
                case 3:
                    return "interfaceAdded";
                case 4:
                    return "interfaceRemoved";
                case 5:
                    return "addressUpdated";
                case 6:
                    return "addressRemoved";
                case 7:
                    return "limitReached";
                case 8:
                    return "interfaceClassDataActivityChanged";
                case 9:
                    return "interfaceDnsServerInfo";
                case 10:
                    return "routeUpdated";
                case 11:
                    return "routeRemoved";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    interfaceStatusChanged(readString, readBoolean);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    interfaceLinkStateChanged(readString2, readBoolean2);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    interfaceAdded(readString3);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    interfaceRemoved(readString4);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    android.net.LinkAddress linkAddress = (android.net.LinkAddress) parcel.readTypedObject(android.net.LinkAddress.CREATOR);
                    parcel.enforceNoDataAvail();
                    addressUpdated(readString5, linkAddress);
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    android.net.LinkAddress linkAddress2 = (android.net.LinkAddress) parcel.readTypedObject(android.net.LinkAddress.CREATOR);
                    parcel.enforceNoDataAvail();
                    addressRemoved(readString6, linkAddress2);
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    limitReached(readString7, readString8);
                    return true;
                case 8:
                    int readInt = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    interfaceClassDataActivityChanged(readInt, readBoolean3, readLong, readInt2);
                    return true;
                case 9:
                    java.lang.String readString9 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    interfaceDnsServerInfo(readString9, readLong2, createStringArray);
                    return true;
                case 10:
                    android.net.RouteInfo routeInfo = (android.net.RouteInfo) parcel.readTypedObject(android.net.RouteInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    routeUpdated(routeInfo);
                    return true;
                case 11:
                    android.net.RouteInfo routeInfo2 = (android.net.RouteInfo) parcel.readTypedObject(android.net.RouteInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    routeRemoved(routeInfo2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.INetworkManagementEventObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR;
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceStatusChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceLinkStateChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceAdded(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceRemoved(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void addressUpdated(java.lang.String str, android.net.LinkAddress linkAddress) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(linkAddress, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void addressRemoved(java.lang.String str, android.net.LinkAddress linkAddress) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(linkAddress, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void limitReached(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceClassDataActivityChanged(int i, boolean z, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void interfaceDnsServerInfo(java.lang.String str, long j, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void routeUpdated(android.net.RouteInfo routeInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(routeInfo, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkManagementEventObserver
            public void routeRemoved(android.net.RouteInfo routeInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkManagementEventObserver.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(routeInfo, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
