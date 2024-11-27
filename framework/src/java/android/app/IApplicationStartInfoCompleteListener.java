package android.app;

/* loaded from: classes.dex */
public interface IApplicationStartInfoCompleteListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IApplicationStartInfoCompleteListener";

    void onApplicationStartInfoComplete(android.app.ApplicationStartInfo applicationStartInfo) throws android.os.RemoteException;

    public static class Default implements android.app.IApplicationStartInfoCompleteListener {
        @Override // android.app.IApplicationStartInfoCompleteListener
        public void onApplicationStartInfoComplete(android.app.ApplicationStartInfo applicationStartInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IApplicationStartInfoCompleteListener {
        static final int TRANSACTION_onApplicationStartInfoComplete = 1;

        public Stub() {
            attachInterface(this, android.app.IApplicationStartInfoCompleteListener.DESCRIPTOR);
        }

        public static android.app.IApplicationStartInfoCompleteListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IApplicationStartInfoCompleteListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IApplicationStartInfoCompleteListener)) {
                return (android.app.IApplicationStartInfoCompleteListener) queryLocalInterface;
            }
            return new android.app.IApplicationStartInfoCompleteListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onApplicationStartInfoComplete";
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
                parcel.enforceInterface(android.app.IApplicationStartInfoCompleteListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IApplicationStartInfoCompleteListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.ApplicationStartInfo applicationStartInfo = (android.app.ApplicationStartInfo) parcel.readTypedObject(android.app.ApplicationStartInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onApplicationStartInfoComplete(applicationStartInfo);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IApplicationStartInfoCompleteListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IApplicationStartInfoCompleteListener.DESCRIPTOR;
            }

            @Override // android.app.IApplicationStartInfoCompleteListener
            public void onApplicationStartInfoComplete(android.app.ApplicationStartInfo applicationStartInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IApplicationStartInfoCompleteListener.DESCRIPTOR);
                    obtain.writeTypedObject(applicationStartInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
