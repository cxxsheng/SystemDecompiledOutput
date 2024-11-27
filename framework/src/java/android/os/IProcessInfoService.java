package android.os;

/* loaded from: classes3.dex */
public interface IProcessInfoService extends android.os.IInterface {
    void getProcessStatesAndOomScoresFromPids(int[] iArr, int[] iArr2, int[] iArr3) throws android.os.RemoteException;

    void getProcessStatesFromPids(int[] iArr, int[] iArr2) throws android.os.RemoteException;

    public static class Default implements android.os.IProcessInfoService {
        @Override // android.os.IProcessInfoService
        public void getProcessStatesFromPids(int[] iArr, int[] iArr2) throws android.os.RemoteException {
        }

        @Override // android.os.IProcessInfoService
        public void getProcessStatesAndOomScoresFromPids(int[] iArr, int[] iArr2, int[] iArr3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IProcessInfoService {
        public static final java.lang.String DESCRIPTOR = "android.os.IProcessInfoService";
        static final int TRANSACTION_getProcessStatesAndOomScoresFromPids = 2;
        static final int TRANSACTION_getProcessStatesFromPids = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IProcessInfoService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IProcessInfoService)) {
                return (android.os.IProcessInfoService) queryLocalInterface;
            }
            return new android.os.IProcessInfoService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getProcessStatesFromPids";
                case 2:
                    return "getProcessStatesAndOomScoresFromPids";
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
            int[] iArr;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            int[] iArr2 = null;
            switch (i) {
                case 1:
                    int[] createIntArray = parcel.createIntArray();
                    int readInt = parcel.readInt();
                    if (readInt >= 0) {
                        iArr2 = new int[readInt];
                    }
                    parcel.enforceNoDataAvail();
                    getProcessStatesFromPids(createIntArray, iArr2);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArr2);
                    return true;
                case 2:
                    int[] createIntArray2 = parcel.createIntArray();
                    int readInt2 = parcel.readInt();
                    if (readInt2 < 0) {
                        iArr = null;
                    } else {
                        iArr = new int[readInt2];
                    }
                    int readInt3 = parcel.readInt();
                    if (readInt3 >= 0) {
                        iArr2 = new int[readInt3];
                    }
                    parcel.enforceNoDataAvail();
                    getProcessStatesAndOomScoresFromPids(createIntArray2, iArr, iArr2);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArr);
                    parcel2.writeIntArray(iArr2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IProcessInfoService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IProcessInfoService.Stub.DESCRIPTOR;
            }

            @Override // android.os.IProcessInfoService
            public void getProcessStatesFromPids(int[] iArr, int[] iArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IProcessInfoService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(iArr2.length);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readIntArray(iArr2);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IProcessInfoService
            public void getProcessStatesAndOomScoresFromPids(int[] iArr, int[] iArr2, int[] iArr3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IProcessInfoService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(iArr2.length);
                    obtain.writeInt(iArr3.length);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readIntArray(iArr2);
                    obtain2.readIntArray(iArr3);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
