package android.companion;

/* loaded from: classes.dex */
public interface IAssociationRequestCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.IAssociationRequestCallback";

    void onAssociationCreated(android.companion.AssociationInfo associationInfo) throws android.os.RemoteException;

    void onAssociationPending(android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void onFailure(java.lang.CharSequence charSequence) throws android.os.RemoteException;

    public static class Default implements android.companion.IAssociationRequestCallback {
        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationPending(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationCreated(android.companion.AssociationInfo associationInfo) throws android.os.RemoteException {
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onFailure(java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.IAssociationRequestCallback {
        static final int TRANSACTION_onAssociationCreated = 2;
        static final int TRANSACTION_onAssociationPending = 1;
        static final int TRANSACTION_onFailure = 3;

        public Stub() {
            attachInterface(this, android.companion.IAssociationRequestCallback.DESCRIPTOR);
        }

        public static android.companion.IAssociationRequestCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.IAssociationRequestCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.IAssociationRequestCallback)) {
                return (android.companion.IAssociationRequestCallback) queryLocalInterface;
            }
            return new android.companion.IAssociationRequestCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAssociationPending";
                case 2:
                    return "onAssociationCreated";
                case 3:
                    return "onFailure";
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
                parcel.enforceInterface(android.companion.IAssociationRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.IAssociationRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAssociationPending(pendingIntent);
                    return true;
                case 2:
                    android.companion.AssociationInfo associationInfo = (android.companion.AssociationInfo) parcel.readTypedObject(android.companion.AssociationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAssociationCreated(associationInfo);
                    return true;
                case 3:
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    onFailure(charSequence);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.IAssociationRequestCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.IAssociationRequestCallback.DESCRIPTOR;
            }

            @Override // android.companion.IAssociationRequestCallback
            public void onAssociationPending(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.IAssociationRequestCallback.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.IAssociationRequestCallback
            public void onAssociationCreated(android.companion.AssociationInfo associationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.IAssociationRequestCallback.DESCRIPTOR);
                    obtain.writeTypedObject(associationInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.IAssociationRequestCallback
            public void onFailure(java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.IAssociationRequestCallback.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
