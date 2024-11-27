package android.app.search;

/* loaded from: classes.dex */
public interface ISearchUiManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.search.ISearchUiManager";

    void createSearchSession(android.app.search.SearchContext searchContext, android.app.search.SearchSessionId searchSessionId, android.os.IBinder iBinder) throws android.os.RemoteException;

    void destroySearchSession(android.app.search.SearchSessionId searchSessionId) throws android.os.RemoteException;

    void notifyEvent(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.SearchTargetEvent searchTargetEvent) throws android.os.RemoteException;

    void query(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException;

    void registerEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException;

    void unregisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException;

    public static class Default implements android.app.search.ISearchUiManager {
        @Override // android.app.search.ISearchUiManager
        public void createSearchSession(android.app.search.SearchContext searchContext, android.app.search.SearchSessionId searchSessionId, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void query(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void notifyEvent(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.SearchTargetEvent searchTargetEvent) throws android.os.RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void registerEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void unregisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
        }

        @Override // android.app.search.ISearchUiManager
        public void destroySearchSession(android.app.search.SearchSessionId searchSessionId) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.search.ISearchUiManager {
        static final int TRANSACTION_createSearchSession = 1;
        static final int TRANSACTION_destroySearchSession = 6;
        static final int TRANSACTION_notifyEvent = 3;
        static final int TRANSACTION_query = 2;
        static final int TRANSACTION_registerEmptyQueryResultUpdateCallback = 4;
        static final int TRANSACTION_unregisterEmptyQueryResultUpdateCallback = 5;

        public Stub() {
            attachInterface(this, android.app.search.ISearchUiManager.DESCRIPTOR);
        }

        public static android.app.search.ISearchUiManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.search.ISearchUiManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.search.ISearchUiManager)) {
                return (android.app.search.ISearchUiManager) queryLocalInterface;
            }
            return new android.app.search.ISearchUiManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSearchSession";
                case 2:
                    return "query";
                case 3:
                    return "notifyEvent";
                case 4:
                    return "registerEmptyQueryResultUpdateCallback";
                case 5:
                    return "unregisterEmptyQueryResultUpdateCallback";
                case 6:
                    return "destroySearchSession";
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
                parcel.enforceInterface(android.app.search.ISearchUiManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.search.ISearchUiManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.search.SearchContext searchContext = (android.app.search.SearchContext) parcel.readTypedObject(android.app.search.SearchContext.CREATOR);
                    android.app.search.SearchSessionId searchSessionId = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createSearchSession(searchContext, searchSessionId, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.app.search.SearchSessionId searchSessionId2 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    android.app.search.Query query = (android.app.search.Query) parcel.readTypedObject(android.app.search.Query.CREATOR);
                    android.app.search.ISearchCallback asInterface = android.app.search.ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    query(searchSessionId2, query, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.app.search.SearchSessionId searchSessionId3 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    android.app.search.Query query2 = (android.app.search.Query) parcel.readTypedObject(android.app.search.Query.CREATOR);
                    android.app.search.SearchTargetEvent searchTargetEvent = (android.app.search.SearchTargetEvent) parcel.readTypedObject(android.app.search.SearchTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyEvent(searchSessionId3, query2, searchTargetEvent);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.app.search.SearchSessionId searchSessionId4 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    android.app.search.ISearchCallback asInterface2 = android.app.search.ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEmptyQueryResultUpdateCallback(searchSessionId4, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.app.search.SearchSessionId searchSessionId5 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    android.app.search.ISearchCallback asInterface3 = android.app.search.ISearchCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterEmptyQueryResultUpdateCallback(searchSessionId5, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.app.search.SearchSessionId searchSessionId6 = (android.app.search.SearchSessionId) parcel.readTypedObject(android.app.search.SearchSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    destroySearchSession(searchSessionId6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.search.ISearchUiManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.search.ISearchUiManager.DESCRIPTOR;
            }

            @Override // android.app.search.ISearchUiManager
            public void createSearchSession(android.app.search.SearchContext searchContext, android.app.search.SearchSessionId searchSessionId, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.search.ISearchUiManager.DESCRIPTOR);
                    obtain.writeTypedObject(searchContext, 0);
                    obtain.writeTypedObject(searchSessionId, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void query(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.search.ISearchUiManager.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
                    obtain.writeTypedObject(query, 0);
                    obtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void notifyEvent(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.SearchTargetEvent searchTargetEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.search.ISearchUiManager.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
                    obtain.writeTypedObject(query, 0);
                    obtain.writeTypedObject(searchTargetEvent, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void registerEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.search.ISearchUiManager.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
                    obtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void unregisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.search.ISearchUiManager.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
                    obtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.search.ISearchUiManager
            public void destroySearchSession(android.app.search.SearchSessionId searchSessionId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.search.ISearchUiManager.DESCRIPTOR);
                    obtain.writeTypedObject(searchSessionId, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
