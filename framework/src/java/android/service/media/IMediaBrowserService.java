package android.service.media;

/* loaded from: classes3.dex */
public interface IMediaBrowserService extends android.os.IInterface {
    void addSubscription(java.lang.String str, android.os.IBinder iBinder, android.os.Bundle bundle, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException;

    void addSubscriptionDeprecated(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException;

    void connect(java.lang.String str, android.os.Bundle bundle, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException;

    void disconnect(android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException;

    void getMediaItem(java.lang.String str, android.os.ResultReceiver resultReceiver, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException;

    void removeSubscription(java.lang.String str, android.os.IBinder iBinder, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException;

    void removeSubscriptionDeprecated(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException;

    public static class Default implements android.service.media.IMediaBrowserService {
        @Override // android.service.media.IMediaBrowserService
        public void connect(java.lang.String str, android.os.Bundle bundle, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void disconnect(android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void addSubscriptionDeprecated(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscriptionDeprecated(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void getMediaItem(java.lang.String str, android.os.ResultReceiver resultReceiver, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void addSubscription(java.lang.String str, android.os.IBinder iBinder, android.os.Bundle bundle, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscription(java.lang.String str, android.os.IBinder iBinder, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.media.IMediaBrowserService {
        public static final java.lang.String DESCRIPTOR = "android.service.media.IMediaBrowserService";
        static final int TRANSACTION_addSubscription = 6;
        static final int TRANSACTION_addSubscriptionDeprecated = 3;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_getMediaItem = 5;
        static final int TRANSACTION_removeSubscription = 7;
        static final int TRANSACTION_removeSubscriptionDeprecated = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.media.IMediaBrowserService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.media.IMediaBrowserService)) {
                return (android.service.media.IMediaBrowserService) queryLocalInterface;
            }
            return new android.service.media.IMediaBrowserService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.media.MediaMetrics.Value.CONNECT;
                case 2:
                    return android.media.MediaMetrics.Value.DISCONNECT;
                case 3:
                    return "addSubscriptionDeprecated";
                case 4:
                    return "removeSubscriptionDeprecated";
                case 5:
                    return "getMediaItem";
                case 6:
                    return "addSubscription";
                case 7:
                    return "removeSubscription";
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
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.service.media.IMediaBrowserServiceCallbacks asInterface = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connect(readString, bundle, asInterface);
                    return true;
                case 2:
                    android.service.media.IMediaBrowserServiceCallbacks asInterface2 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    disconnect(asInterface2);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    android.service.media.IMediaBrowserServiceCallbacks asInterface3 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addSubscriptionDeprecated(readString2, asInterface3);
                    return true;
                case 4:
                    java.lang.String readString3 = parcel.readString();
                    android.service.media.IMediaBrowserServiceCallbacks asInterface4 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSubscriptionDeprecated(readString3, asInterface4);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    android.service.media.IMediaBrowserServiceCallbacks asInterface5 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getMediaItem(readString4, resultReceiver, asInterface5);
                    return true;
                case 6:
                    java.lang.String readString5 = parcel.readString();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.service.media.IMediaBrowserServiceCallbacks asInterface6 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addSubscription(readString5, readStrongBinder, bundle2, asInterface6);
                    return true;
                case 7:
                    java.lang.String readString6 = parcel.readString();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.service.media.IMediaBrowserServiceCallbacks asInterface7 = android.service.media.IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSubscription(readString6, readStrongBinder2, asInterface7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.media.IMediaBrowserService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.media.IMediaBrowserService.Stub.DESCRIPTOR;
            }

            @Override // android.service.media.IMediaBrowserService
            public void connect(java.lang.String str, android.os.Bundle bundle, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void disconnect(android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void addSubscriptionDeprecated(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void removeSubscriptionDeprecated(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void getMediaItem(java.lang.String str, android.os.ResultReceiver resultReceiver, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void addSubscription(java.lang.String str, android.os.IBinder iBinder, android.os.Bundle bundle, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void removeSubscription(java.lang.String str, android.os.IBinder iBinder, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
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
