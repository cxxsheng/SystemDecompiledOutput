package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface ITelecomLoader extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.ITelecomLoader";

    com.android.internal.telecom.ITelecomService createTelecomService(com.android.internal.telecom.IInternalServiceRetriever iInternalServiceRetriever) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.ITelecomLoader {
        @Override // com.android.internal.telecom.ITelecomLoader
        public com.android.internal.telecom.ITelecomService createTelecomService(com.android.internal.telecom.IInternalServiceRetriever iInternalServiceRetriever) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.ITelecomLoader {
        static final int TRANSACTION_createTelecomService = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telecom.ITelecomLoader.DESCRIPTOR);
        }

        public static com.android.internal.telecom.ITelecomLoader asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telecom.ITelecomLoader.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.ITelecomLoader)) {
                return (com.android.internal.telecom.ITelecomLoader) queryLocalInterface;
            }
            return new com.android.internal.telecom.ITelecomLoader.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createTelecomService";
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
                parcel.enforceInterface(com.android.internal.telecom.ITelecomLoader.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telecom.ITelecomLoader.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.telecom.IInternalServiceRetriever asInterface = com.android.internal.telecom.IInternalServiceRetriever.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    com.android.internal.telecom.ITelecomService createTelecomService = createTelecomService(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createTelecomService);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.ITelecomLoader {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.ITelecomLoader.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ITelecomLoader
            public com.android.internal.telecom.ITelecomService createTelecomService(com.android.internal.telecom.IInternalServiceRetriever iInternalServiceRetriever) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomLoader.DESCRIPTOR);
                    obtain.writeStrongInterface(iInternalServiceRetriever);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.telecom.ITelecomService.Stub.asInterface(obtain2.readStrongBinder());
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
