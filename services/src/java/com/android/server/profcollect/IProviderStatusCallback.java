package com.android.server.profcollect;

/* loaded from: classes2.dex */
public interface IProviderStatusCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.server.profcollect.IProviderStatusCallback";

    void onProviderReady() throws android.os.RemoteException;

    public static class Default implements com.android.server.profcollect.IProviderStatusCallback {
        @Override // com.android.server.profcollect.IProviderStatusCallback
        public void onProviderReady() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.server.profcollect.IProviderStatusCallback {
        static final int TRANSACTION_onProviderReady = 1;

        public Stub() {
            attachInterface(this, com.android.server.profcollect.IProviderStatusCallback.DESCRIPTOR);
        }

        public static com.android.server.profcollect.IProviderStatusCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.server.profcollect.IProviderStatusCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.server.profcollect.IProviderStatusCallback)) {
                return (com.android.server.profcollect.IProviderStatusCallback) queryLocalInterface;
            }
            return new com.android.server.profcollect.IProviderStatusCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(com.android.server.profcollect.IProviderStatusCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.server.profcollect.IProviderStatusCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onProviderReady();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.server.profcollect.IProviderStatusCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.server.profcollect.IProviderStatusCallback.DESCRIPTOR;
            }

            @Override // com.android.server.profcollect.IProviderStatusCallback
            public void onProviderReady() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.server.profcollect.IProviderStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
