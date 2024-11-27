package android.app.contentsuggestions;

/* loaded from: classes.dex */
public interface IClassificationsCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.contentsuggestions.IClassificationsCallback";

    void onContentClassificationsAvailable(int i, java.util.List<android.app.contentsuggestions.ContentClassification> list) throws android.os.RemoteException;

    public static class Default implements android.app.contentsuggestions.IClassificationsCallback {
        @Override // android.app.contentsuggestions.IClassificationsCallback
        public void onContentClassificationsAvailable(int i, java.util.List<android.app.contentsuggestions.ContentClassification> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.contentsuggestions.IClassificationsCallback {
        static final int TRANSACTION_onContentClassificationsAvailable = 1;

        public Stub() {
            attachInterface(this, android.app.contentsuggestions.IClassificationsCallback.DESCRIPTOR);
        }

        public static android.app.contentsuggestions.IClassificationsCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.contentsuggestions.IClassificationsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.contentsuggestions.IClassificationsCallback)) {
                return (android.app.contentsuggestions.IClassificationsCallback) queryLocalInterface;
            }
            return new android.app.contentsuggestions.IClassificationsCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onContentClassificationsAvailable";
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
                parcel.enforceInterface(android.app.contentsuggestions.IClassificationsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.contentsuggestions.IClassificationsCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.app.contentsuggestions.ContentClassification.CREATOR);
                    parcel.enforceNoDataAvail();
                    onContentClassificationsAvailable(readInt, createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.contentsuggestions.IClassificationsCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.contentsuggestions.IClassificationsCallback.DESCRIPTOR;
            }

            @Override // android.app.contentsuggestions.IClassificationsCallback
            public void onContentClassificationsAvailable(int i, java.util.List<android.app.contentsuggestions.ContentClassification> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.contentsuggestions.IClassificationsCallback.DESCRIPTOR);
                    obtain.writeInt(i);
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
