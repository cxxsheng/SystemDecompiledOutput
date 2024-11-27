package android.media;

/* loaded from: classes2.dex */
public interface IMediaRouter2Manager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IMediaRouter2Manager";

    void notifyDiscoveryPreferenceChanged(java.lang.String str, android.media.RouteDiscoveryPreference routeDiscoveryPreference) throws android.os.RemoteException;

    void notifyRequestFailed(int i, int i2) throws android.os.RemoteException;

    void notifyRouteListingPreferenceChange(java.lang.String str, android.media.RouteListingPreference routeListingPreference) throws android.os.RemoteException;

    void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) throws android.os.RemoteException;

    void notifySessionCreated(int i, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException;

    void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException;

    void notifySessionUpdated(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException;

    public static class Default implements android.media.IMediaRouter2Manager {
        @Override // android.media.IMediaRouter2Manager
        public void notifySessionCreated(int i, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionUpdated(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyDiscoveryPreferenceChanged(java.lang.String str, android.media.RouteDiscoveryPreference routeDiscoveryPreference) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRouteListingPreferenceChange(java.lang.String str, android.media.RouteListingPreference routeListingPreference) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRequestFailed(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IMediaRouter2Manager {
        static final int TRANSACTION_notifyDiscoveryPreferenceChanged = 4;
        static final int TRANSACTION_notifyRequestFailed = 7;
        static final int TRANSACTION_notifyRouteListingPreferenceChange = 5;
        static final int TRANSACTION_notifyRoutesUpdated = 6;
        static final int TRANSACTION_notifySessionCreated = 1;
        static final int TRANSACTION_notifySessionReleased = 3;
        static final int TRANSACTION_notifySessionUpdated = 2;

        public Stub() {
            attachInterface(this, android.media.IMediaRouter2Manager.DESCRIPTOR);
        }

        public static android.media.IMediaRouter2Manager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IMediaRouter2Manager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IMediaRouter2Manager)) {
                return (android.media.IMediaRouter2Manager) queryLocalInterface;
            }
            return new android.media.IMediaRouter2Manager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifySessionCreated";
                case 2:
                    return "notifySessionUpdated";
                case 3:
                    return "notifySessionReleased";
                case 4:
                    return "notifyDiscoveryPreferenceChanged";
                case 5:
                    return "notifyRouteListingPreferenceChange";
                case 6:
                    return "notifyRoutesUpdated";
                case 7:
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
                parcel.enforceInterface(android.media.IMediaRouter2Manager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IMediaRouter2Manager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.media.RoutingSessionInfo routingSessionInfo = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionCreated(readInt, routingSessionInfo);
                    return true;
                case 2:
                    android.media.RoutingSessionInfo routingSessionInfo2 = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionUpdated(routingSessionInfo2);
                    return true;
                case 3:
                    android.media.RoutingSessionInfo routingSessionInfo3 = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySessionReleased(routingSessionInfo3);
                    return true;
                case 4:
                    java.lang.String readString = parcel.readString();
                    android.media.RouteDiscoveryPreference routeDiscoveryPreference = (android.media.RouteDiscoveryPreference) parcel.readTypedObject(android.media.RouteDiscoveryPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDiscoveryPreferenceChanged(readString, routeDiscoveryPreference);
                    return true;
                case 5:
                    java.lang.String readString2 = parcel.readString();
                    android.media.RouteListingPreference routeListingPreference = (android.media.RouteListingPreference) parcel.readTypedObject(android.media.RouteListingPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyRouteListingPreferenceChange(readString2, routeListingPreference);
                    return true;
                case 6:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyRoutesUpdated(createTypedArrayList);
                    return true;
                case 7:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRequestFailed(readInt2, readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IMediaRouter2Manager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IMediaRouter2Manager.DESCRIPTOR;
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionCreated(int i, android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2Manager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionUpdated(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2Manager.DESCRIPTOR);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2Manager.DESCRIPTOR);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyDiscoveryPreferenceChanged(java.lang.String str, android.media.RouteDiscoveryPreference routeDiscoveryPreference) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2Manager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(routeDiscoveryPreference, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRouteListingPreferenceChange(java.lang.String str, android.media.RouteListingPreference routeListingPreference) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2Manager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(routeListingPreference, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2Manager.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRequestFailed(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouter2Manager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
