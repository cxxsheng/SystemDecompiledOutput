package android.app;

/* loaded from: classes.dex */
public interface IUidFrozenStateChangedCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IUidFrozenStateChangedCallback";

    void onUidFrozenStateChanged(int[] iArr, int[] iArr2) throws android.os.RemoteException;

    public static class Default implements android.app.IUidFrozenStateChangedCallback {
        @Override // android.app.IUidFrozenStateChangedCallback
        public void onUidFrozenStateChanged(int[] iArr, int[] iArr2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IUidFrozenStateChangedCallback {
        static final int TRANSACTION_onUidFrozenStateChanged = 1;

        public Stub() {
            attachInterface(this, android.app.IUidFrozenStateChangedCallback.DESCRIPTOR);
        }

        public static android.app.IUidFrozenStateChangedCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IUidFrozenStateChangedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IUidFrozenStateChangedCallback)) {
                return (android.app.IUidFrozenStateChangedCallback) queryLocalInterface;
            }
            return new android.app.IUidFrozenStateChangedCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUidFrozenStateChanged";
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
                parcel.enforceInterface(android.app.IUidFrozenStateChangedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IUidFrozenStateChangedCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onUidFrozenStateChanged(createIntArray, createIntArray2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IUidFrozenStateChangedCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IUidFrozenStateChangedCallback.DESCRIPTOR;
            }

            @Override // android.app.IUidFrozenStateChangedCallback
            public void onUidFrozenStateChanged(int[] iArr, int[] iArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IUidFrozenStateChangedCallback.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
