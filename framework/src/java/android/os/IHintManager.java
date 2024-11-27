package android.os;

/* loaded from: classes3.dex */
public interface IHintManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IHintManager";

    android.os.IHintSession createHintSession(android.os.IBinder iBinder, int[] iArr, long j) throws android.os.RemoteException;

    long getHintSessionPreferredRate() throws android.os.RemoteException;

    int[] getHintSessionThreadIds(android.os.IHintSession iHintSession) throws android.os.RemoteException;

    void setHintSessionThreads(android.os.IHintSession iHintSession, int[] iArr) throws android.os.RemoteException;

    public static class Default implements android.os.IHintManager {
        @Override // android.os.IHintManager
        public android.os.IHintSession createHintSession(android.os.IBinder iBinder, int[] iArr, long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public long getHintSessionPreferredRate() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IHintManager
        public void setHintSessionThreads(android.os.IHintSession iHintSession, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.os.IHintManager
        public int[] getHintSessionThreadIds(android.os.IHintSession iHintSession) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IHintManager {
        static final int TRANSACTION_createHintSession = 1;
        static final int TRANSACTION_getHintSessionPreferredRate = 2;
        static final int TRANSACTION_getHintSessionThreadIds = 4;
        static final int TRANSACTION_setHintSessionThreads = 3;

        public Stub() {
            attachInterface(this, android.os.IHintManager.DESCRIPTOR);
        }

        public static android.os.IHintManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IHintManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IHintManager)) {
                return (android.os.IHintManager) queryLocalInterface;
            }
            return new android.os.IHintManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createHintSession";
                case 2:
                    return "getHintSessionPreferredRate";
                case 3:
                    return "setHintSessionThreads";
                case 4:
                    return "getHintSessionThreadIds";
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
                parcel.enforceInterface(android.os.IHintManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IHintManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int[] createIntArray = parcel.createIntArray();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.os.IHintSession createHintSession = createHintSession(readStrongBinder, createIntArray, readLong);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createHintSession);
                    return true;
                case 2:
                    long hintSessionPreferredRate = getHintSessionPreferredRate();
                    parcel2.writeNoException();
                    parcel2.writeLong(hintSessionPreferredRate);
                    return true;
                case 3:
                    android.os.IHintSession asInterface = android.os.IHintSession.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setHintSessionThreads(asInterface, createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.os.IHintSession asInterface2 = android.os.IHintSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int[] hintSessionThreadIds = getHintSessionThreadIds(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(hintSessionThreadIds);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IHintManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IHintManager.DESCRIPTOR;
            }

            @Override // android.os.IHintManager
            public android.os.IHintSession createHintSession(android.os.IBinder iBinder, int[] iArr, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IHintManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeIntArray(iArr);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.IHintSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public long getHintSessionPreferredRate() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IHintManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public void setHintSessionThreads(android.os.IHintSession iHintSession, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IHintManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iHintSession);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IHintManager
            public int[] getHintSessionThreadIds(android.os.IHintSession iHintSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IHintManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iHintSession);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
