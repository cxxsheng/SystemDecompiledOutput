package android.app;

/* loaded from: classes.dex */
public interface IForegroundServiceObserver extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IForegroundServiceObserver";

    void onForegroundStateChanged(android.os.IBinder iBinder, java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    public static class Default implements android.app.IForegroundServiceObserver {
        @Override // android.app.IForegroundServiceObserver
        public void onForegroundStateChanged(android.os.IBinder iBinder, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IForegroundServiceObserver {
        static final int TRANSACTION_onForegroundStateChanged = 1;

        public Stub() {
            attachInterface(this, android.app.IForegroundServiceObserver.DESCRIPTOR);
        }

        public static android.app.IForegroundServiceObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IForegroundServiceObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IForegroundServiceObserver)) {
                return (android.app.IForegroundServiceObserver) queryLocalInterface;
            }
            return new android.app.IForegroundServiceObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onForegroundStateChanged";
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
                parcel.enforceInterface(android.app.IForegroundServiceObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IForegroundServiceObserver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onForegroundStateChanged(readStrongBinder, readString, readInt, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IForegroundServiceObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IForegroundServiceObserver.DESCRIPTOR;
            }

            @Override // android.app.IForegroundServiceObserver
            public void onForegroundStateChanged(android.os.IBinder iBinder, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IForegroundServiceObserver.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
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
