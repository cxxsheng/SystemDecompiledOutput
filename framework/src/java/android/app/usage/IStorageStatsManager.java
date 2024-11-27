package android.app.usage;

/* loaded from: classes.dex */
public interface IStorageStatsManager extends android.os.IInterface {
    long getCacheBytes(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    long getCacheQuotaBytes(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    long getFreeBytes(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    long getTotalBytes(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isQuotaSupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isReservedSupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryCratesForPackage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryCratesForUid(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryCratesForUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    android.app.usage.ExternalStorageStats queryExternalStatsForUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    android.app.usage.StorageStats queryStatsForPackage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException;

    android.app.usage.StorageStats queryStatsForUid(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    android.app.usage.StorageStats queryStatsForUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements android.app.usage.IStorageStatsManager {
        @Override // android.app.usage.IStorageStatsManager
        public boolean isQuotaSupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.usage.IStorageStatsManager
        public boolean isReservedSupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.usage.IStorageStatsManager
        public long getTotalBytes(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IStorageStatsManager
        public long getFreeBytes(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IStorageStatsManager
        public long getCacheBytes(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IStorageStatsManager
        public long getCacheQuotaBytes(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IStorageStatsManager
        public android.app.usage.StorageStats queryStatsForPackage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public android.app.usage.StorageStats queryStatsForUid(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public android.app.usage.StorageStats queryStatsForUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public android.app.usage.ExternalStorageStats queryExternalStatsForUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public android.content.pm.ParceledListSlice queryCratesForPackage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public android.content.pm.ParceledListSlice queryCratesForUid(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public android.content.pm.ParceledListSlice queryCratesForUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.usage.IStorageStatsManager {
        public static final java.lang.String DESCRIPTOR = "android.app.usage.IStorageStatsManager";
        static final int TRANSACTION_getCacheBytes = 5;
        static final int TRANSACTION_getCacheQuotaBytes = 6;
        static final int TRANSACTION_getFreeBytes = 4;
        static final int TRANSACTION_getTotalBytes = 3;
        static final int TRANSACTION_isQuotaSupported = 1;
        static final int TRANSACTION_isReservedSupported = 2;
        static final int TRANSACTION_queryCratesForPackage = 11;
        static final int TRANSACTION_queryCratesForUid = 12;
        static final int TRANSACTION_queryCratesForUser = 13;
        static final int TRANSACTION_queryExternalStatsForUser = 10;
        static final int TRANSACTION_queryStatsForPackage = 7;
        static final int TRANSACTION_queryStatsForUid = 8;
        static final int TRANSACTION_queryStatsForUser = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.usage.IStorageStatsManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.usage.IStorageStatsManager)) {
                return (android.app.usage.IStorageStatsManager) queryLocalInterface;
            }
            return new android.app.usage.IStorageStatsManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isQuotaSupported";
                case 2:
                    return "isReservedSupported";
                case 3:
                    return "getTotalBytes";
                case 4:
                    return "getFreeBytes";
                case 5:
                    return "getCacheBytes";
                case 6:
                    return "getCacheQuotaBytes";
                case 7:
                    return "queryStatsForPackage";
                case 8:
                    return "queryStatsForUid";
                case 9:
                    return "queryStatsForUser";
                case 10:
                    return "queryExternalStatsForUser";
                case 11:
                    return "queryCratesForPackage";
                case 12:
                    return "queryCratesForUid";
                case 13:
                    return "queryCratesForUser";
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
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isQuotaSupported = isQuotaSupported(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isQuotaSupported);
                    return true;
                case 2:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isReservedSupported = isReservedSupported(readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isReservedSupported);
                    return true;
                case 3:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long totalBytes = getTotalBytes(readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeLong(totalBytes);
                    return true;
                case 4:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long freeBytes = getFreeBytes(readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeLong(freeBytes);
                    return true;
                case 5:
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long cacheBytes = getCacheBytes(readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeLong(cacheBytes);
                    return true;
                case 6:
                    java.lang.String readString11 = parcel.readString();
                    int readInt = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long cacheQuotaBytes = getCacheQuotaBytes(readString11, readInt, readString12);
                    parcel2.writeNoException();
                    parcel2.writeLong(cacheQuotaBytes);
                    return true;
                case 7:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.usage.StorageStats queryStatsForPackage = queryStatsForPackage(readString13, readString14, readInt2, readString15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryStatsForPackage, 1);
                    return true;
                case 8:
                    java.lang.String readString16 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.usage.StorageStats queryStatsForUid = queryStatsForUid(readString16, readInt3, readString17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryStatsForUid, 1);
                    return true;
                case 9:
                    java.lang.String readString18 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.usage.StorageStats queryStatsForUser = queryStatsForUser(readString18, readInt4, readString19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryStatsForUser, 1);
                    return true;
                case 10:
                    java.lang.String readString20 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.usage.ExternalStorageStats queryExternalStatsForUser = queryExternalStatsForUser(readString20, readInt5, readString21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryExternalStatsForUser, 1);
                    return true;
                case 11:
                    java.lang.String readString22 = parcel.readString();
                    java.lang.String readString23 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryCratesForPackage = queryCratesForPackage(readString22, readString23, readInt6, readString24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryCratesForPackage, 1);
                    return true;
                case 12:
                    java.lang.String readString25 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryCratesForUid = queryCratesForUid(readString25, readInt7, readString26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryCratesForUid, 1);
                    return true;
                case 13:
                    java.lang.String readString27 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryCratesForUser = queryCratesForUser(readString27, readInt8, readString28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryCratesForUser, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.usage.IStorageStatsManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.usage.IStorageStatsManager
            public boolean isQuotaSupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public boolean isReservedSupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getTotalBytes(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getFreeBytes(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getCacheBytes(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getCacheQuotaBytes(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public android.app.usage.StorageStats queryStatsForPackage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.StorageStats) obtain2.readTypedObject(android.app.usage.StorageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public android.app.usage.StorageStats queryStatsForUid(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.StorageStats) obtain2.readTypedObject(android.app.usage.StorageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public android.app.usage.StorageStats queryStatsForUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.StorageStats) obtain2.readTypedObject(android.app.usage.StorageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public android.app.usage.ExternalStorageStats queryExternalStatsForUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.ExternalStorageStats) obtain2.readTypedObject(android.app.usage.ExternalStorageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public android.content.pm.ParceledListSlice queryCratesForPackage(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public android.content.pm.ParceledListSlice queryCratesForUid(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public android.content.pm.ParceledListSlice queryCratesForUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IStorageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
