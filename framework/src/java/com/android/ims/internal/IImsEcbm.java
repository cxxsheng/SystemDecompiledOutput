package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsEcbm extends android.os.IInterface {
    void exitEmergencyCallbackMode() throws android.os.RemoteException;

    void setListener(com.android.ims.internal.IImsEcbmListener iImsEcbmListener) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsEcbm {
        @Override // com.android.ims.internal.IImsEcbm
        public void setListener(com.android.ims.internal.IImsEcbmListener iImsEcbmListener) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsEcbm
        public void exitEmergencyCallbackMode() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsEcbm {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsEcbm";
        static final int TRANSACTION_exitEmergencyCallbackMode = 2;
        static final int TRANSACTION_setListener = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsEcbm asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsEcbm)) {
                return (com.android.ims.internal.IImsEcbm) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsEcbm.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setListener";
                case 2:
                    return "exitEmergencyCallbackMode";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.ims.internal.IImsEcbmListener asInterface = com.android.ims.internal.IImsEcbmListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    exitEmergencyCallbackMode();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsEcbm {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsEcbm.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsEcbm
            public void setListener(com.android.ims.internal.IImsEcbmListener iImsEcbmListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsEcbm.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsEcbmListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsEcbm
            public void exitEmergencyCallbackMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsEcbm.Stub.DESCRIPTOR);
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
