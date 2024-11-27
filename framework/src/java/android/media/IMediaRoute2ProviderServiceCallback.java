package android.media;

/* loaded from: classes2.dex */
public interface IMediaRoute2ProviderServiceCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IMediaRoute2ProviderServiceCallback";

    void notifyProviderUpdated(android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) throws android.os.RemoteException;

    void notifyRequestFailed(long j, int i) throws android.os.RemoteException;

    void notifySessionCreated(long j, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException;

    void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException;

    void notifySessionsUpdated(java.util.List<android.media.RoutingSessionInfo> list) throws android.os.RemoteException;

    public static class Default implements android.media.IMediaRoute2ProviderServiceCallback {
        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifyProviderUpdated(android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifySessionCreated(long j, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifySessionsUpdated(java.util.List<android.media.RoutingSessionInfo> list) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderServiceCallback
        public void notifyRequestFailed(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IMediaRoute2ProviderServiceCallback {
        static final int TRANSACTION_notifyProviderUpdated = 1;
        static final int TRANSACTION_notifyRequestFailed = 5;
        static final int TRANSACTION_notifySessionCreated = 2;
        static final int TRANSACTION_notifySessionReleased = 4;
        static final int TRANSACTION_notifySessionsUpdated = 3;

        public Stub() {
            attachInterface(this, android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
        }

        public static android.media.IMediaRoute2ProviderServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IMediaRoute2ProviderServiceCallback)) {
                return (android.media.IMediaRoute2ProviderServiceCallback) queryLocalInterface;
            }
            return new android.media.IMediaRoute2ProviderServiceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyProviderUpdated";
                case 2:
                    return "notifySessionCreated";
                case 3:
                    return "notifySessionsUpdated";
                case 4:
                    return "notifySessionReleased";
                case 5:
                    return "notifyRequestFailed";
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
                parcel.enforceInterface(android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo = (android.media.MediaRoute2ProviderInfo) parcel.readTypedObject(android.media.MediaRoute2ProviderInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyProviderUpdated(mediaRoute2ProviderInfo);
                    return true;
                case 2:
                    long readLong = parcel.readLong();
                    android.media.RoutingSessionInfo routingSessionInfo = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionCreated(readLong, routingSessionInfo);
                    return true;
                case 3:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionsUpdated(createTypedArrayList);
                    return true;
                case 4:
                    android.media.RoutingSessionInfo routingSessionInfo2 = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionReleased(routingSessionInfo2);
                    return true;
                case 5:
                    long readLong2 = parcel.readLong();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRequestFailed(readLong2, readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IMediaRoute2ProviderServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR;
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifyProviderUpdated(android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(mediaRoute2ProviderInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifySessionCreated(long j, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifySessionsUpdated(java.util.List<android.media.RoutingSessionInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderServiceCallback
            public void notifyRequestFailed(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderServiceCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
