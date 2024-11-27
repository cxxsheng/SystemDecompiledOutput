package android.media;

/* loaded from: classes2.dex */
public interface IMediaRouter2 extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IMediaRouter2";

    void notifyRouterRegistered(java.util.List<android.media.MediaRoute2Info> list, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException;

    void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) throws android.os.RemoteException;

    void notifySessionCreated(int i, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException;

    void notifySessionInfoChanged(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException;

    void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException;

    void requestCreateSessionByManager(long j, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.media.IMediaRouter2 {
        @Override // android.media.IMediaRouter2
        public void notifyRouterRegistered(java.util.List<android.media.MediaRoute2Info> list, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionCreated(int i, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionInfoChanged(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2
        public void requestCreateSessionByManager(long j, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IMediaRouter2 {
        static final int TRANSACTION_notifyRouterRegistered = 1;
        static final int TRANSACTION_notifyRoutesUpdated = 2;
        static final int TRANSACTION_notifySessionCreated = 3;
        static final int TRANSACTION_notifySessionInfoChanged = 4;
        static final int TRANSACTION_notifySessionReleased = 5;
        static final int TRANSACTION_requestCreateSessionByManager = 6;

        public Stub() {
            attachInterface(this, android.media.IMediaRouter2.DESCRIPTOR);
        }

        public static android.media.IMediaRouter2 asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IMediaRouter2.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IMediaRouter2)) {
                return (android.media.IMediaRouter2) queryLocalInterface;
            }
            return new android.media.IMediaRouter2.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyRouterRegistered";
                case 2:
                    return "notifyRoutesUpdated";
                case 3:
                    return "notifySessionCreated";
                case 4:
                    return "notifySessionInfoChanged";
                case 5:
                    return "notifySessionReleased";
                case 6:
                    return "requestCreateSessionByManager";
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
                parcel.enforceInterface(android.media.IMediaRouter2.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IMediaRouter2.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.MediaRoute2Info.CREATOR);
                    android.media.RoutingSessionInfo routingSessionInfo = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyRouterRegistered(createTypedArrayList, routingSessionInfo);
                    return true;
                case 2:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.media.MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyRoutesUpdated(createTypedArrayList2);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    android.media.RoutingSessionInfo routingSessionInfo2 = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionCreated(readInt, routingSessionInfo2);
                    return true;
                case 4:
                    android.media.RoutingSessionInfo routingSessionInfo3 = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionInfoChanged(routingSessionInfo3);
                    return true;
                case 5:
                    android.media.RoutingSessionInfo routingSessionInfo4 = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionReleased(routingSessionInfo4);
                    return true;
                case 6:
                    long readLong = parcel.readLong();
                    android.media.RoutingSessionInfo routingSessionInfo5 = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    android.media.MediaRoute2Info mediaRoute2Info = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestCreateSessionByManager(readLong, routingSessionInfo5, mediaRoute2Info, userHandle, readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IMediaRouter2 {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IMediaRouter2.DESCRIPTOR;
            }

            @Override // android.media.IMediaRouter2
            public void notifyRouterRegistered(java.util.List<android.media.MediaRoute2Info> list, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void notifySessionCreated(int i, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void notifySessionInfoChanged(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2.DESCRIPTOR);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2.DESCRIPTOR);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2
            public void requestCreateSessionByManager(long j, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
