package com.android.internal.telephony.euicc;

/* loaded from: classes5.dex */
public interface IAuthenticateServerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.euicc.IAuthenticateServerCallback";

    void onComplete(int i, byte[] bArr) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.euicc.IAuthenticateServerCallback {
        @Override // com.android.internal.telephony.euicc.IAuthenticateServerCallback
        public void onComplete(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.euicc.IAuthenticateServerCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.euicc.IAuthenticateServerCallback.DESCRIPTOR);
        }

        public static com.android.internal.telephony.euicc.IAuthenticateServerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.euicc.IAuthenticateServerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.euicc.IAuthenticateServerCallback)) {
                return (com.android.internal.telephony.euicc.IAuthenticateServerCallback) queryLocalInterface;
            }
            return new com.android.internal.telephony.euicc.IAuthenticateServerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onComplete";
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
                parcel.enforceInterface(com.android.internal.telephony.euicc.IAuthenticateServerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.euicc.IAuthenticateServerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onComplete(readInt, createByteArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.euicc.IAuthenticateServerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.euicc.IAuthenticateServerCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IAuthenticateServerCallback
            public void onComplete(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IAuthenticateServerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
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