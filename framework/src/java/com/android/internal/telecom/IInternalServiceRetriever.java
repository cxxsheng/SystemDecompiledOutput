package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface IInternalServiceRetriever extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IInternalServiceRetriever";

    com.android.internal.telecom.IDeviceIdleControllerAdapter getDeviceIdleController() throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.IInternalServiceRetriever {
        @Override // com.android.internal.telecom.IInternalServiceRetriever
        public com.android.internal.telecom.IDeviceIdleControllerAdapter getDeviceIdleController() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IInternalServiceRetriever {
        static final int TRANSACTION_getDeviceIdleController = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.IInternalServiceRetriever.DESCRIPTOR);
        }

        public static com.android.internal.telecom.IInternalServiceRetriever asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.IInternalServiceRetriever.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.IInternalServiceRetriever)) {
                return (com.android.internal.telecom.IInternalServiceRetriever) queryLocalInterface;
            }
            return new com.android.internal.telecom.IInternalServiceRetriever.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDeviceIdleController";
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
                parcel.enforceInterface(com.android.internal.telecom.IInternalServiceRetriever.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.IInternalServiceRetriever.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.telecom.IDeviceIdleControllerAdapter deviceIdleController = getDeviceIdleController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(deviceIdleController);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.IInternalServiceRetriever {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.IInternalServiceRetriever.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IInternalServiceRetriever
            public com.android.internal.telecom.IDeviceIdleControllerAdapter getDeviceIdleController() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IInternalServiceRetriever.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.telecom.IDeviceIdleControllerAdapter.Stub.asInterface(obtain2.readStrongBinder());
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
