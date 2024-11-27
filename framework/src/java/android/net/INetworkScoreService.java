package android.net;

/* loaded from: classes2.dex */
public interface INetworkScoreService extends android.os.IInterface {
    boolean clearScores() throws android.os.RemoteException;

    void disableScoring() throws android.os.RemoteException;

    android.net.NetworkScorerAppData getActiveScorer() throws android.os.RemoteException;

    java.lang.String getActiveScorerPackage() throws android.os.RemoteException;

    java.util.List<android.net.NetworkScorerAppData> getAllValidScorers() throws android.os.RemoteException;

    boolean isCallerActiveScorer(int i) throws android.os.RemoteException;

    void registerNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache, int i2) throws android.os.RemoteException;

    boolean requestScores(android.net.NetworkKey[] networkKeyArr) throws android.os.RemoteException;

    boolean setActiveScorer(java.lang.String str) throws android.os.RemoteException;

    void unregisterNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache) throws android.os.RemoteException;

    boolean updateScores(android.net.ScoredNetwork[] scoredNetworkArr) throws android.os.RemoteException;

    public static class Default implements android.net.INetworkScoreService {
        @Override // android.net.INetworkScoreService
        public boolean updateScores(android.net.ScoredNetwork[] scoredNetworkArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetworkScoreService
        public boolean clearScores() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetworkScoreService
        public boolean setActiveScorer(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetworkScoreService
        public void disableScoring() throws android.os.RemoteException {
        }

        @Override // android.net.INetworkScoreService
        public void registerNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkScoreService
        public void unregisterNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkScoreService
        public boolean requestScores(android.net.NetworkKey[] networkKeyArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetworkScoreService
        public boolean isCallerActiveScorer(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetworkScoreService
        public java.lang.String getActiveScorerPackage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetworkScoreService
        public android.net.NetworkScorerAppData getActiveScorer() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetworkScoreService
        public java.util.List<android.net.NetworkScorerAppData> getAllValidScorers() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetworkScoreService {
        public static final java.lang.String DESCRIPTOR = "android.net.INetworkScoreService";
        static final int TRANSACTION_clearScores = 2;
        static final int TRANSACTION_disableScoring = 4;
        static final int TRANSACTION_getActiveScorer = 10;
        static final int TRANSACTION_getActiveScorerPackage = 9;
        static final int TRANSACTION_getAllValidScorers = 11;
        static final int TRANSACTION_isCallerActiveScorer = 8;
        static final int TRANSACTION_registerNetworkScoreCache = 5;
        static final int TRANSACTION_requestScores = 7;
        static final int TRANSACTION_setActiveScorer = 3;
        static final int TRANSACTION_unregisterNetworkScoreCache = 6;
        static final int TRANSACTION_updateScores = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.net.INetworkScoreService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetworkScoreService)) {
                return (android.net.INetworkScoreService) queryLocalInterface;
            }
            return new android.net.INetworkScoreService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateScores";
                case 2:
                    return "clearScores";
                case 3:
                    return "setActiveScorer";
                case 4:
                    return "disableScoring";
                case 5:
                    return "registerNetworkScoreCache";
                case 6:
                    return "unregisterNetworkScoreCache";
                case 7:
                    return "requestScores";
                case 8:
                    return "isCallerActiveScorer";
                case 9:
                    return "getActiveScorerPackage";
                case 10:
                    return "getActiveScorer";
                case 11:
                    return "getAllValidScorers";
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
                    android.net.ScoredNetwork[] scoredNetworkArr = (android.net.ScoredNetwork[]) parcel.createTypedArray(android.net.ScoredNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateScores = updateScores(scoredNetworkArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateScores);
                    return true;
                case 2:
                    boolean clearScores = clearScores();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearScores);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean activeScorer = setActiveScorer(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activeScorer);
                    return true;
                case 4:
                    disableScoring();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    android.net.INetworkScoreCache asInterface = android.net.INetworkScoreCache.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerNetworkScoreCache(readInt, asInterface, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    android.net.INetworkScoreCache asInterface2 = android.net.INetworkScoreCache.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterNetworkScoreCache(readInt3, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.net.NetworkKey[] networkKeyArr = (android.net.NetworkKey[]) parcel.createTypedArray(android.net.NetworkKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestScores = requestScores(networkKeyArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestScores);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCallerActiveScorer = isCallerActiveScorer(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerActiveScorer);
                    return true;
                case 9:
                    java.lang.String activeScorerPackage = getActiveScorerPackage();
                    parcel2.writeNoException();
                    parcel2.writeString(activeScorerPackage);
                    return true;
                case 10:
                    android.net.NetworkScorerAppData activeScorer2 = getActiveScorer();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeScorer2, 1);
                    return true;
                case 11:
                    java.util.List<android.net.NetworkScorerAppData> allValidScorers = getAllValidScorers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allValidScorers, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.INetworkScoreService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.INetworkScoreService.Stub.DESCRIPTOR;
            }

            @Override // android.net.INetworkScoreService
            public boolean updateScores(android.net.ScoredNetwork[] scoredNetworkArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(scoredNetworkArr, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public boolean clearScores() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public boolean setActiveScorer(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public void disableScoring() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public void registerNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNetworkScoreCache);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public void unregisterNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNetworkScoreCache);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public boolean requestScores(android.net.NetworkKey[] networkKeyArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(networkKeyArr, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public boolean isCallerActiveScorer(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public java.lang.String getActiveScorerPackage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public android.net.NetworkScorerAppData getActiveScorer() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.NetworkScorerAppData) obtain2.readTypedObject(android.net.NetworkScorerAppData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkScoreService
            public java.util.List<android.net.NetworkScorerAppData> getAllValidScorers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkScoreService.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.net.NetworkScorerAppData.CREATOR);
                } finally {
                    obtain2.recycle();
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
