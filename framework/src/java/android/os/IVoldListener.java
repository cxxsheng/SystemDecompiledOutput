package android.os;

/* loaded from: classes3.dex */
public interface IVoldListener extends android.os.IInterface {
    void onDiskCreated(java.lang.String str, int i) throws android.os.RemoteException;

    void onDiskDestroyed(java.lang.String str) throws android.os.RemoteException;

    void onDiskMetadataChanged(java.lang.String str, long j, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void onDiskScanned(java.lang.String str) throws android.os.RemoteException;

    void onVolumeCreated(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2) throws android.os.RemoteException;

    void onVolumeDestroyed(java.lang.String str) throws android.os.RemoteException;

    void onVolumeInternalPathChanged(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void onVolumeMetadataChanged(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException;

    void onVolumePathChanged(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void onVolumeStateChanged(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.os.IVoldListener {
        @Override // android.os.IVoldListener
        public void onDiskCreated(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onDiskScanned(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onDiskMetadataChanged(java.lang.String str, long j, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onDiskDestroyed(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeCreated(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeStateChanged(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeMetadataChanged(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumePathChanged(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeInternalPathChanged(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeDestroyed(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IVoldListener {
        public static final java.lang.String DESCRIPTOR = "android.os.IVoldListener";
        static final int TRANSACTION_onDiskCreated = 1;
        static final int TRANSACTION_onDiskDestroyed = 4;
        static final int TRANSACTION_onDiskMetadataChanged = 3;
        static final int TRANSACTION_onDiskScanned = 2;
        static final int TRANSACTION_onVolumeCreated = 5;
        static final int TRANSACTION_onVolumeDestroyed = 10;
        static final int TRANSACTION_onVolumeInternalPathChanged = 9;
        static final int TRANSACTION_onVolumeMetadataChanged = 7;
        static final int TRANSACTION_onVolumePathChanged = 8;
        static final int TRANSACTION_onVolumeStateChanged = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IVoldListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IVoldListener)) {
                return (android.os.IVoldListener) queryLocalInterface;
            }
            return new android.os.IVoldListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDiskCreated";
                case 2:
                    return "onDiskScanned";
                case 3:
                    return "onDiskMetadataChanged";
                case 4:
                    return "onDiskDestroyed";
                case 5:
                    return "onVolumeCreated";
                case 6:
                    return "onVolumeStateChanged";
                case 7:
                    return "onVolumeMetadataChanged";
                case 8:
                    return "onVolumePathChanged";
                case 9:
                    return "onVolumeInternalPathChanged";
                case 10:
                    return "onVolumeDestroyed";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDiskCreated(readString, readInt);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDiskScanned(readString2);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    long readLong = parcel.readLong();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDiskMetadataChanged(readString3, readLong, readString4, readString5);
                    return true;
                case 4:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDiskDestroyed(readString6);
                    return true;
                case 5:
                    java.lang.String readString7 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVolumeCreated(readString7, readInt2, readString8, readString9, readInt3);
                    return true;
                case 6:
                    java.lang.String readString10 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVolumeStateChanged(readString10, readInt4, readInt5);
                    return true;
                case 7:
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumeMetadataChanged(readString11, readString12, readString13, readString14);
                    return true;
                case 8:
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumePathChanged(readString15, readString16);
                    return true;
                case 9:
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumeInternalPathChanged(readString17, readString18);
                    return true;
                case 10:
                    java.lang.String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumeDestroyed(readString19);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IVoldListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IVoldListener.Stub.DESCRIPTOR;
            }

            @Override // android.os.IVoldListener
            public void onDiskCreated(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onDiskScanned(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onDiskMetadataChanged(java.lang.String str, long j, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onDiskDestroyed(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeCreated(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeStateChanged(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeMetadataChanged(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumePathChanged(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeInternalPathChanged(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeDestroyed(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVoldListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
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
