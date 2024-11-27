package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IInputContentUriToken extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IInputContentUriToken";

    void release() throws android.os.RemoteException;

    void take() throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IInputContentUriToken {
        @Override // com.android.internal.inputmethod.IInputContentUriToken
        public void take() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputContentUriToken
        public void release() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IInputContentUriToken {
        static final int TRANSACTION_release = 2;
        static final int TRANSACTION_take = 1;

        public Stub() {
            attachInterface(this, com.android.internal.inputmethod.IInputContentUriToken.DESCRIPTOR);
        }

        public static com.android.internal.inputmethod.IInputContentUriToken asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IInputContentUriToken.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IInputContentUriToken)) {
                return (com.android.internal.inputmethod.IInputContentUriToken) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IInputContentUriToken.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "take";
                case 2:
                    return "release";
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
                parcel.enforceInterface(com.android.internal.inputmethod.IInputContentUriToken.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IInputContentUriToken.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    take();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    release();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IInputContentUriToken {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IInputContentUriToken.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputContentUriToken
            public void take() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputContentUriToken.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputContentUriToken
            public void release() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputContentUriToken.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
