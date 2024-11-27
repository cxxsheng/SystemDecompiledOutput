package android.os;

/* loaded from: classes3.dex */
public interface IIdmap2 extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IIdmap2";

    int acquireFabricatedOverlayIterator() throws android.os.RemoteException;

    android.os.FabricatedOverlayInfo createFabricatedOverlay(android.os.FabricatedOverlayInternal fabricatedOverlayInternal) throws android.os.RemoteException;

    java.lang.String createIdmap(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, boolean z, int i2) throws android.os.RemoteException;

    boolean deleteFabricatedOverlay(java.lang.String str) throws android.os.RemoteException;

    java.lang.String dumpIdmap(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getIdmapPath(java.lang.String str, int i) throws android.os.RemoteException;

    java.util.List<android.os.FabricatedOverlayInfo> nextFabricatedOverlayInfos(int i) throws android.os.RemoteException;

    void releaseFabricatedOverlayIterator(int i) throws android.os.RemoteException;

    boolean removeIdmap(java.lang.String str, int i) throws android.os.RemoteException;

    boolean verifyIdmap(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, boolean z, int i2) throws android.os.RemoteException;

    public static class Default implements android.os.IIdmap2 {
        @Override // android.os.IIdmap2
        public java.lang.String getIdmapPath(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IIdmap2
        public boolean removeIdmap(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IIdmap2
        public boolean verifyIdmap(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, boolean z, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IIdmap2
        public java.lang.String createIdmap(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, boolean z, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IIdmap2
        public android.os.FabricatedOverlayInfo createFabricatedOverlay(android.os.FabricatedOverlayInternal fabricatedOverlayInternal) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IIdmap2
        public boolean deleteFabricatedOverlay(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IIdmap2
        public int acquireFabricatedOverlayIterator() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IIdmap2
        public void releaseFabricatedOverlayIterator(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IIdmap2
        public java.util.List<android.os.FabricatedOverlayInfo> nextFabricatedOverlayInfos(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IIdmap2
        public java.lang.String dumpIdmap(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IIdmap2 {
        static final int TRANSACTION_acquireFabricatedOverlayIterator = 7;
        static final int TRANSACTION_createFabricatedOverlay = 5;
        static final int TRANSACTION_createIdmap = 4;
        static final int TRANSACTION_deleteFabricatedOverlay = 6;
        static final int TRANSACTION_dumpIdmap = 10;
        static final int TRANSACTION_getIdmapPath = 1;
        static final int TRANSACTION_nextFabricatedOverlayInfos = 9;
        static final int TRANSACTION_releaseFabricatedOverlayIterator = 8;
        static final int TRANSACTION_removeIdmap = 2;
        static final int TRANSACTION_verifyIdmap = 3;

        public Stub() {
            attachInterface(this, android.os.IIdmap2.DESCRIPTOR);
        }

        public static android.os.IIdmap2 asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IIdmap2.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IIdmap2)) {
                return (android.os.IIdmap2) queryLocalInterface;
            }
            return new android.os.IIdmap2.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getIdmapPath";
                case 2:
                    return "removeIdmap";
                case 3:
                    return "verifyIdmap";
                case 4:
                    return "createIdmap";
                case 5:
                    return "createFabricatedOverlay";
                case 6:
                    return "deleteFabricatedOverlay";
                case 7:
                    return "acquireFabricatedOverlayIterator";
                case 8:
                    return "releaseFabricatedOverlayIterator";
                case 9:
                    return "nextFabricatedOverlayInfos";
                case 10:
                    return "dumpIdmap";
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
                parcel.enforceInterface(android.os.IIdmap2.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IIdmap2.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String idmapPath = getIdmapPath(readString, readInt);
                    parcel2.writeNoException();
                    parcel2.writeString(idmapPath);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeIdmap = removeIdmap(readString2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeIdmap);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean verifyIdmap = verifyIdmap(readString3, readString4, readString5, readInt3, readBoolean, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(verifyIdmap);
                    return true;
                case 4:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String createIdmap = createIdmap(readString6, readString7, readString8, readInt5, readBoolean2, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeString(createIdmap);
                    return true;
                case 5:
                    android.os.FabricatedOverlayInternal fabricatedOverlayInternal = (android.os.FabricatedOverlayInternal) parcel.readTypedObject(android.os.FabricatedOverlayInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.FabricatedOverlayInfo createFabricatedOverlay = createFabricatedOverlay(fabricatedOverlayInternal);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createFabricatedOverlay, 1);
                    return true;
                case 6:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteFabricatedOverlay = deleteFabricatedOverlay(readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteFabricatedOverlay);
                    return true;
                case 7:
                    int acquireFabricatedOverlayIterator = acquireFabricatedOverlayIterator();
                    parcel2.writeNoException();
                    parcel2.writeInt(acquireFabricatedOverlayIterator);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseFabricatedOverlayIterator(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.FabricatedOverlayInfo> nextFabricatedOverlayInfos = nextFabricatedOverlayInfos(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(nextFabricatedOverlayInfos, 1);
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String dumpIdmap = dumpIdmap(readString10);
                    parcel2.writeNoException();
                    parcel2.writeString(dumpIdmap);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IIdmap2 {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IIdmap2.DESCRIPTOR;
            }

            @Override // android.os.IIdmap2
            public java.lang.String getIdmapPath(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public boolean removeIdmap(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public boolean verifyIdmap(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public java.lang.String createIdmap(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public android.os.FabricatedOverlayInfo createFabricatedOverlay(android.os.FabricatedOverlayInternal fabricatedOverlayInternal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    obtain.writeTypedObject(fabricatedOverlayInternal, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.FabricatedOverlayInfo) obtain2.readTypedObject(android.os.FabricatedOverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public boolean deleteFabricatedOverlay(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public int acquireFabricatedOverlayIterator() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public void releaseFabricatedOverlayIterator(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public java.util.List<android.os.FabricatedOverlayInfo> nextFabricatedOverlayInfos(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.FabricatedOverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public java.lang.String dumpIdmap(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
