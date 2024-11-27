package android.apphibernation;

/* loaded from: classes.dex */
public interface IAppHibernationService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.apphibernation.IAppHibernationService";

    java.util.List<java.lang.String> getHibernatingPackagesForUser(int i) throws android.os.RemoteException;

    java.util.Map<java.lang.String, android.apphibernation.HibernationStats> getHibernationStatsForUser(java.util.List<java.lang.String> list, int i) throws android.os.RemoteException;

    boolean isHibernatingForUser(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isHibernatingGlobally(java.lang.String str) throws android.os.RemoteException;

    boolean isOatArtifactDeletionEnabled() throws android.os.RemoteException;

    void setHibernatingForUser(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setHibernatingGlobally(java.lang.String str, boolean z) throws android.os.RemoteException;

    public static class Default implements android.apphibernation.IAppHibernationService {
        @Override // android.apphibernation.IAppHibernationService
        public boolean isHibernatingForUser(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.apphibernation.IAppHibernationService
        public void setHibernatingForUser(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.apphibernation.IAppHibernationService
        public boolean isHibernatingGlobally(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.apphibernation.IAppHibernationService
        public void setHibernatingGlobally(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.apphibernation.IAppHibernationService
        public java.util.List<java.lang.String> getHibernatingPackagesForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.apphibernation.IAppHibernationService
        public java.util.Map<java.lang.String, android.apphibernation.HibernationStats> getHibernationStatsForUser(java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.apphibernation.IAppHibernationService
        public boolean isOatArtifactDeletionEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.apphibernation.IAppHibernationService {
        static final int TRANSACTION_getHibernatingPackagesForUser = 5;
        static final int TRANSACTION_getHibernationStatsForUser = 6;
        static final int TRANSACTION_isHibernatingForUser = 1;
        static final int TRANSACTION_isHibernatingGlobally = 3;
        static final int TRANSACTION_isOatArtifactDeletionEnabled = 7;
        static final int TRANSACTION_setHibernatingForUser = 2;
        static final int TRANSACTION_setHibernatingGlobally = 4;

        public Stub() {
            attachInterface(this, android.apphibernation.IAppHibernationService.DESCRIPTOR);
        }

        public static android.apphibernation.IAppHibernationService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.apphibernation.IAppHibernationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.apphibernation.IAppHibernationService)) {
                return (android.apphibernation.IAppHibernationService) queryLocalInterface;
            }
            return new android.apphibernation.IAppHibernationService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isHibernatingForUser";
                case 2:
                    return "setHibernatingForUser";
                case 3:
                    return "isHibernatingGlobally";
                case 4:
                    return "setHibernatingGlobally";
                case 5:
                    return "getHibernatingPackagesForUser";
                case 6:
                    return "getHibernationStatsForUser";
                case 7:
                    return "isOatArtifactDeletionEnabled";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, final android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.apphibernation.IAppHibernationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.apphibernation.IAppHibernationService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isHibernatingForUser = isHibernatingForUser(readString, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHibernatingForUser);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHibernatingForUser(readString2, readInt2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHibernatingGlobally = isHibernatingGlobally(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHibernatingGlobally);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHibernatingGlobally(readString4, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> hibernatingPackagesForUser = getHibernatingPackagesForUser(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(hibernatingPackagesForUser);
                    return true;
                case 6:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.Map<java.lang.String, android.apphibernation.HibernationStats> hibernationStatsForUser = getHibernationStatsForUser(createStringArrayList, readInt4);
                    parcel2.writeNoException();
                    if (hibernationStatsForUser == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(hibernationStatsForUser.size());
                        hibernationStatsForUser.forEach(new java.util.function.BiConsumer() { // from class: android.apphibernation.IAppHibernationService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.apphibernation.IAppHibernationService.Stub.lambda$onTransact$0(android.os.Parcel.this, (java.lang.String) obj, (android.apphibernation.HibernationStats) obj2);
                            }
                        });
                    }
                    return true;
                case 7:
                    boolean isOatArtifactDeletionEnabled = isOatArtifactDeletionEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOatArtifactDeletionEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(android.os.Parcel parcel, java.lang.String str, android.apphibernation.HibernationStats hibernationStats) {
            parcel.writeString(str);
            parcel.writeTypedObject(hibernationStats, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.apphibernation.IAppHibernationService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.apphibernation.IAppHibernationService.DESCRIPTOR;
            }

            @Override // android.apphibernation.IAppHibernationService
            public boolean isHibernatingForUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apphibernation.IAppHibernationService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public void setHibernatingForUser(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apphibernation.IAppHibernationService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public boolean isHibernatingGlobally(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apphibernation.IAppHibernationService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public void setHibernatingGlobally(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apphibernation.IAppHibernationService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public java.util.List<java.lang.String> getHibernatingPackagesForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apphibernation.IAppHibernationService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public java.util.Map<java.lang.String, android.apphibernation.HibernationStats> getHibernationStatsForUser(java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                final android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apphibernation.IAppHibernationService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final java.util.HashMap hashMap = readInt < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt).forEach(new java.util.function.IntConsumer() { // from class: android.apphibernation.IAppHibernationService$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            hashMap.put(r0.readString(), (android.apphibernation.HibernationStats) android.os.Parcel.this.readTypedObject(android.apphibernation.HibernationStats.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.apphibernation.IAppHibernationService
            public boolean isOatArtifactDeletionEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.apphibernation.IAppHibernationService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
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
