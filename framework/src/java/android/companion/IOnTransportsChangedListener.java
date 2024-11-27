package android.companion;

/* loaded from: classes.dex */
public interface IOnTransportsChangedListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.IOnTransportsChangedListener";

    void onTransportsChanged(java.util.List<android.companion.AssociationInfo> list) throws android.os.RemoteException;

    public static class Default implements android.companion.IOnTransportsChangedListener {
        @Override // android.companion.IOnTransportsChangedListener
        public void onTransportsChanged(java.util.List<android.companion.AssociationInfo> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.IOnTransportsChangedListener {
        static final int TRANSACTION_onTransportsChanged = 1;

        public Stub() {
            attachInterface(this, android.companion.IOnTransportsChangedListener.DESCRIPTOR);
        }

        public static android.companion.IOnTransportsChangedListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.IOnTransportsChangedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.IOnTransportsChangedListener)) {
                return (android.companion.IOnTransportsChangedListener) queryLocalInterface;
            }
            return new android.companion.IOnTransportsChangedListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTransportsChanged";
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
                parcel.enforceInterface(android.companion.IOnTransportsChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.IOnTransportsChangedListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.companion.AssociationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTransportsChanged(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.IOnTransportsChangedListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.IOnTransportsChangedListener.DESCRIPTOR;
            }

            @Override // android.companion.IOnTransportsChangedListener
            public void onTransportsChanged(java.util.List<android.companion.AssociationInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.IOnTransportsChangedListener.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
