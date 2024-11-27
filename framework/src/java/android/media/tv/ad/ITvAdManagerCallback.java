package android.media.tv.ad;

/* loaded from: classes2.dex */
public interface ITvAdManagerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.ad.ITvAdManagerCallback";

    void onAdServiceAdded(java.lang.String str) throws android.os.RemoteException;

    void onAdServiceRemoved(java.lang.String str) throws android.os.RemoteException;

    void onAdServiceUpdated(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ad.ITvAdManagerCallback {
        @Override // android.media.tv.ad.ITvAdManagerCallback
        public void onAdServiceAdded(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManagerCallback
        public void onAdServiceRemoved(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManagerCallback
        public void onAdServiceUpdated(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ad.ITvAdManagerCallback {
        static final int TRANSACTION_onAdServiceAdded = 1;
        static final int TRANSACTION_onAdServiceRemoved = 2;
        static final int TRANSACTION_onAdServiceUpdated = 3;

        public Stub() {
            attachInterface(this, android.media.tv.ad.ITvAdManagerCallback.DESCRIPTOR);
        }

        public static android.media.tv.ad.ITvAdManagerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.ad.ITvAdManagerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ad.ITvAdManagerCallback)) {
                return (android.media.tv.ad.ITvAdManagerCallback) queryLocalInterface;
            }
            return new android.media.tv.ad.ITvAdManagerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAdServiceAdded";
                case 2:
                    return "onAdServiceRemoved";
                case 3:
                    return "onAdServiceUpdated";
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
                parcel.enforceInterface(android.media.tv.ad.ITvAdManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.ad.ITvAdManagerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAdServiceAdded(readString);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAdServiceRemoved(readString2);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAdServiceUpdated(readString3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ad.ITvAdManagerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ad.ITvAdManagerCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceAdded(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceRemoved(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceUpdated(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
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
