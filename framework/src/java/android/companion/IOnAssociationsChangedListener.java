package android.companion;

/* loaded from: classes.dex */
public interface IOnAssociationsChangedListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.IOnAssociationsChangedListener";

    void onAssociationsChanged(java.util.List<android.companion.AssociationInfo> list) throws android.os.RemoteException;

    public static class Default implements android.companion.IOnAssociationsChangedListener {
        @Override // android.companion.IOnAssociationsChangedListener
        public void onAssociationsChanged(java.util.List<android.companion.AssociationInfo> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.IOnAssociationsChangedListener {
        static final int TRANSACTION_onAssociationsChanged = 1;

        public Stub() {
            attachInterface(this, android.companion.IOnAssociationsChangedListener.DESCRIPTOR);
        }

        public static android.companion.IOnAssociationsChangedListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.IOnAssociationsChangedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.IOnAssociationsChangedListener)) {
                return (android.companion.IOnAssociationsChangedListener) queryLocalInterface;
            }
            return new android.companion.IOnAssociationsChangedListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAssociationsChanged";
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
                parcel.enforceInterface(android.companion.IOnAssociationsChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.IOnAssociationsChangedListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.companion.AssociationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAssociationsChanged(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.IOnAssociationsChangedListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.IOnAssociationsChangedListener.DESCRIPTOR;
            }

            @Override // android.companion.IOnAssociationsChangedListener
            public void onAssociationsChanged(java.util.List<android.companion.AssociationInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.IOnAssociationsChangedListener.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
