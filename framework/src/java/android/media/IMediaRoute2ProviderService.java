package android.media;

/* loaded from: classes2.dex */
public interface IMediaRoute2ProviderService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IMediaRoute2ProviderService";

    void deselectRoute(long j, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void releaseSession(long j, java.lang.String str) throws android.os.RemoteException;

    void requestCreateSession(long j, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void selectRoute(long j, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setCallback(android.media.IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) throws android.os.RemoteException;

    void setRouteVolume(long j, java.lang.String str, int i) throws android.os.RemoteException;

    void setSessionVolume(long j, java.lang.String str, int i) throws android.os.RemoteException;

    void transferToRoute(long j, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void updateDiscoveryPreference(android.media.RouteDiscoveryPreference routeDiscoveryPreference) throws android.os.RemoteException;

    public static class Default implements android.media.IMediaRoute2ProviderService {
        @Override // android.media.IMediaRoute2ProviderService
        public void setCallback(android.media.IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void updateDiscoveryPreference(android.media.RouteDiscoveryPreference routeDiscoveryPreference) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setRouteVolume(long j, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void requestCreateSession(long j, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void selectRoute(long j, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void deselectRoute(long j, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void transferToRoute(long j, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setSessionVolume(long j, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void releaseSession(long j, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IMediaRoute2ProviderService {
        static final int TRANSACTION_deselectRoute = 6;
        static final int TRANSACTION_releaseSession = 9;
        static final int TRANSACTION_requestCreateSession = 4;
        static final int TRANSACTION_selectRoute = 5;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setRouteVolume = 3;
        static final int TRANSACTION_setSessionVolume = 8;
        static final int TRANSACTION_transferToRoute = 7;
        static final int TRANSACTION_updateDiscoveryPreference = 2;

        public Stub() {
            attachInterface(this, android.media.IMediaRoute2ProviderService.DESCRIPTOR);
        }

        public static android.media.IMediaRoute2ProviderService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IMediaRoute2ProviderService)) {
                return (android.media.IMediaRoute2ProviderService) queryLocalInterface;
            }
            return new android.media.IMediaRoute2ProviderService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCallback";
                case 2:
                    return "updateDiscoveryPreference";
                case 3:
                    return "setRouteVolume";
                case 4:
                    return "requestCreateSession";
                case 5:
                    return "selectRoute";
                case 6:
                    return "deselectRoute";
                case 7:
                    return "transferToRoute";
                case 8:
                    return "setSessionVolume";
                case 9:
                    return "releaseSession";
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
                parcel.enforceInterface(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.IMediaRoute2ProviderServiceCallback asInterface = android.media.IMediaRoute2ProviderServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    return true;
                case 2:
                    android.media.RouteDiscoveryPreference routeDiscoveryPreference = (android.media.RouteDiscoveryPreference) parcel.readTypedObject(android.media.RouteDiscoveryPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateDiscoveryPreference(routeDiscoveryPreference);
                    return true;
                case 3:
                    long readLong = parcel.readLong();
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRouteVolume(readLong, readString, readInt);
                    return true;
                case 4:
                    long readLong2 = parcel.readLong();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSession(readLong2, readString2, readString3, bundle);
                    return true;
                case 5:
                    long readLong3 = parcel.readLong();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    selectRoute(readLong3, readString4, readString5);
                    return true;
                case 6:
                    long readLong4 = parcel.readLong();
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deselectRoute(readLong4, readString6, readString7);
                    return true;
                case 7:
                    long readLong5 = parcel.readLong();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transferToRoute(readLong5, readString8, readString9);
                    return true;
                case 8:
                    long readLong6 = parcel.readLong();
                    java.lang.String readString10 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionVolume(readLong6, readString10, readInt2);
                    return true;
                case 9:
                    long readLong7 = parcel.readLong();
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseSession(readLong7, readString11);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IMediaRoute2ProviderService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IMediaRoute2ProviderService.DESCRIPTOR;
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void setCallback(android.media.IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRoute2ProviderServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void updateDiscoveryPreference(android.media.RouteDiscoveryPreference routeDiscoveryPreference) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeTypedObject(routeDiscoveryPreference, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void setRouteVolume(long j, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void requestCreateSession(long j, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void selectRoute(long j, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void deselectRoute(long j, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void transferToRoute(long j, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void setSessionVolume(long j, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRoute2ProviderService
            public void releaseSession(long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRoute2ProviderService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
