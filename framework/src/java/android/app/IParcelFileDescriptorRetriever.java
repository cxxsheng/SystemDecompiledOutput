package android.app;

/* loaded from: classes.dex */
public interface IParcelFileDescriptorRetriever extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IParcelFileDescriptorRetriever";

    android.os.ParcelFileDescriptor getPfd() throws android.os.RemoteException;

    public static class Default implements android.app.IParcelFileDescriptorRetriever {
        @Override // android.app.IParcelFileDescriptorRetriever
        public android.os.ParcelFileDescriptor getPfd() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IParcelFileDescriptorRetriever {
        static final int TRANSACTION_getPfd = 1;

        public Stub() {
            attachInterface(this, android.app.IParcelFileDescriptorRetriever.DESCRIPTOR);
        }

        public static android.app.IParcelFileDescriptorRetriever asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IParcelFileDescriptorRetriever.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IParcelFileDescriptorRetriever)) {
                return (android.app.IParcelFileDescriptorRetriever) queryLocalInterface;
            }
            return new android.app.IParcelFileDescriptorRetriever.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPfd";
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
                parcel.enforceInterface(android.app.IParcelFileDescriptorRetriever.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IParcelFileDescriptorRetriever.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelFileDescriptor pfd = getPfd();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pfd, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IParcelFileDescriptorRetriever {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IParcelFileDescriptorRetriever.DESCRIPTOR;
            }

            @Override // android.app.IParcelFileDescriptorRetriever
            public android.os.ParcelFileDescriptor getPfd() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IParcelFileDescriptorRetriever.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
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
