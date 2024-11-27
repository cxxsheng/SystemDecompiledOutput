package android.app;

/* loaded from: classes.dex */
public interface IOnProjectionStateChangedListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IOnProjectionStateChangedListener";

    void onProjectionStateChanged(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    public static class Default implements android.app.IOnProjectionStateChangedListener {
        @Override // android.app.IOnProjectionStateChangedListener
        public void onProjectionStateChanged(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IOnProjectionStateChangedListener {
        static final int TRANSACTION_onProjectionStateChanged = 1;

        public Stub() {
            attachInterface(this, android.app.IOnProjectionStateChangedListener.DESCRIPTOR);
        }

        public static android.app.IOnProjectionStateChangedListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IOnProjectionStateChangedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IOnProjectionStateChangedListener)) {
                return (android.app.IOnProjectionStateChangedListener) queryLocalInterface;
            }
            return new android.app.IOnProjectionStateChangedListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onProjectionStateChanged";
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
                parcel.enforceInterface(android.app.IOnProjectionStateChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IOnProjectionStateChangedListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    onProjectionStateChanged(readInt, createStringArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IOnProjectionStateChangedListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IOnProjectionStateChangedListener.DESCRIPTOR;
            }

            @Override // android.app.IOnProjectionStateChangedListener
            public void onProjectionStateChanged(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IOnProjectionStateChangedListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
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
