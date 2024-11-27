package android.service.search;

/* loaded from: classes3.dex */
public interface ISearchUiService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.search.ISearchUiService";

    void onCreateSearchSession(android.app.search.SearchContext searchContext, android.app.search.SearchSessionId searchSessionId) throws android.os.RemoteException;

    void onDestroy(android.app.search.SearchSessionId searchSessionId) throws android.os.RemoteException;

    void onNotifyEvent(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.SearchTargetEvent searchTargetEvent) throws android.os.RemoteException;

    void onQuery(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException;

    void onRegisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException;

    void onUnregisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException;

    public static class Default implements android.service.search.ISearchUiService {
        @Override // android.service.search.ISearchUiService
        public void onCreateSearchSession(android.app.search.SearchContext searchContext, android.app.search.SearchSessionId searchSessionId) throws android.os.RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onQuery(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onNotifyEvent(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.SearchTargetEvent searchTargetEvent) throws android.os.RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onRegisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onUnregisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
        }

        @Override // android.service.search.ISearchUiService
        public void onDestroy(android.app.search.SearchSessionId searchSessionId) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.search.ISearchUiService {
        static final int TRANSACTION_onCreateSearchSession = 1;
        static final int TRANSACTION_onDestroy = 6;
        static final int TRANSACTION_onNotifyEvent = 3;
        static final int TRANSACTION_onQuery = 2;
        static final int TRANSACTION_onRegisterEmptyQueryResultUpdateCallback = 4;
        static final int TRANSACTION_onUnregisterEmptyQueryResultUpdateCallback = 5;

        public Stub() {
            attachInterface(this, android.service.search.ISearchUiService.DESCRIPTOR);
        }

        public static android.service.search.ISearchUiService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.search.ISearchUiService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.search.ISearchUiService)) {
                return (android.service.search.ISearchUiService) queryLocalInterface;
            }
            return new android.service.search.ISearchUiService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCreateSearchSession";
                case 2:
                    return "onQuery";
                case 3:
                    return "onNotifyEvent";
                case 4:
                    return "onRegisterEmptyQueryResultUpdateCallback";
                case 5:
                    return "onUnregisterEmptyQueryResultUpdateCallback";
                case 6:
                    return "onDestroy";
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
                parcel.enforceInterface(android.service.search.ISearchUiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.search.ISearchUiService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.search.SearchContext searchContext = (android.app.search.SearchContext) parcel.readTypedObject(android.app.search.SearchContext.CREATOR);
                    android.app.search.SearchSessionId searchSessionId = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCreateSearchSession(searchContext, searchSessionId);
                    return true;
                case 2:
                    android.app.search.SearchSessionId searchSessionId2 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    android.app.search.Query query = (android.app.search.Query) parcel.readTypedObject(android.app.search.Query.CREATOR);
                    android.app.search.ISearchCallback asInterface = android.app.search.ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onQuery(searchSessionId2, query, asInterface);
                    return true;
                case 3:
                    android.app.search.SearchSessionId searchSessionId3 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    android.app.search.Query query2 = (android.app.search.Query) parcel.readTypedObject(android.app.search.Query.CREATOR);
                    android.app.search.SearchTargetEvent searchTargetEvent = (android.app.search.SearchTargetEvent) parcel.readTypedObject(android.app.search.SearchTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotifyEvent(searchSessionId3, query2, searchTargetEvent);
                    return true;
                case 4:
                    android.app.search.SearchSessionId searchSessionId4 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    android.app.search.ISearchCallback asInterface2 = android.app.search.ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRegisterEmptyQueryResultUpdateCallback(searchSessionId4, asInterface2);
                    return true;
                case 5:
                    android.app.search.SearchSessionId searchSessionId5 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    android.app.search.ISearchCallback asInterface3 = android.app.search.ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onUnregisterEmptyQueryResultUpdateCallback(searchSessionId5, asInterface3);
                    return true;
                case 6:
                    android.app.search.SearchSessionId searchSessionId6 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroy(searchSessionId6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.search.ISearchUiService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.search.ISearchUiService.DESCRIPTOR;
            }

            @Override // android.service.search.ISearchUiService
            public void onCreateSearchSession(android.app.search.SearchContext searchContext, android.app.search.SearchSessionId searchSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.search.ISearchUiService.DESCRIPTOR);
                    obtain.writeTypedObject(searchContext, 0);
                    obtain.writeTypedObject(searchSessionId, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onQuery(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.search.ISearchUiService.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
                    obtain.writeTypedObject(query, 0);
                    obtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onNotifyEvent(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.SearchTargetEvent searchTargetEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.search.ISearchUiService.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
                    obtain.writeTypedObject(query, 0);
                    obtain.writeTypedObject(searchTargetEvent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onRegisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.search.ISearchUiService.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
                    obtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onUnregisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.search.ISearchUiService.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
                    obtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.search.ISearchUiService
            public void onDestroy(android.app.search.SearchSessionId searchSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.search.ISearchUiService.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
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
