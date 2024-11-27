package android.os;

/* loaded from: classes3.dex */
public interface IUserRestrictionsListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IUserRestrictionsListener";

    void onUserRestrictionsChanged(int i, android.os.Bundle bundle, android.os.Bundle bundle2) throws android.os.RemoteException;

    public static class Default implements android.os.IUserRestrictionsListener {
        @Override // android.os.IUserRestrictionsListener
        public void onUserRestrictionsChanged(int i, android.os.Bundle bundle, android.os.Bundle bundle2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IUserRestrictionsListener {
        static final int TRANSACTION_onUserRestrictionsChanged = 1;

        public Stub() {
            attachInterface(this, android.os.IUserRestrictionsListener.DESCRIPTOR);
        }

        public static android.os.IUserRestrictionsListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IUserRestrictionsListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IUserRestrictionsListener)) {
                return (android.os.IUserRestrictionsListener) queryLocalInterface;
            }
            return new android.os.IUserRestrictionsListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUserRestrictionsChanged";
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
                parcel.enforceInterface(android.os.IUserRestrictionsListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IUserRestrictionsListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUserRestrictionsChanged(readInt, bundle, bundle2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IUserRestrictionsListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IUserRestrictionsListener.DESCRIPTOR;
            }

            @Override // android.os.IUserRestrictionsListener
            public void onUserRestrictionsChanged(int i, android.os.Bundle bundle, android.os.Bundle bundle2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IUserRestrictionsListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(bundle2, 0);
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
