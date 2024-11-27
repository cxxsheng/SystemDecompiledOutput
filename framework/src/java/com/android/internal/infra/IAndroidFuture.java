package com.android.internal.infra;

/* loaded from: classes4.dex */
public interface IAndroidFuture extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.infra.IAndroidFuture";

    void complete(com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    public static class Default implements com.android.internal.infra.IAndroidFuture {
        @Override // com.android.internal.infra.IAndroidFuture
        public void complete(com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.infra.IAndroidFuture {
        static final int TRANSACTION_complete = 1;

        public Stub() {
            attachInterface(this, com.android.internal.infra.IAndroidFuture.DESCRIPTOR);
        }

        public static com.android.internal.infra.IAndroidFuture asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.infra.IAndroidFuture.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.infra.IAndroidFuture)) {
                return (com.android.internal.infra.IAndroidFuture) queryLocalInterface;
            }
            return new com.android.internal.infra.IAndroidFuture.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "complete";
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
                parcel.enforceInterface(com.android.internal.infra.IAndroidFuture.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.infra.IAndroidFuture.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    complete(androidFuture);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.infra.IAndroidFuture {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.infra.IAndroidFuture.DESCRIPTOR;
            }

            @Override // com.android.internal.infra.IAndroidFuture
            public void complete(com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.infra.IAndroidFuture.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
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
