package android.companion;

/* loaded from: classes.dex */
public interface ICompanionDeviceDiscoveryService extends android.os.IInterface {
    void onAssociationCreated() throws android.os.RemoteException;

    void startDiscovery(android.companion.AssociationRequest associationRequest, java.lang.String str, android.companion.IAssociationRequestCallback iAssociationRequestCallback, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException;

    public static class Default implements android.companion.ICompanionDeviceDiscoveryService {
        @Override // android.companion.ICompanionDeviceDiscoveryService
        public void startDiscovery(android.companion.AssociationRequest associationRequest, java.lang.String str, android.companion.IAssociationRequestCallback iAssociationRequestCallback, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceDiscoveryService
        public void onAssociationCreated() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.ICompanionDeviceDiscoveryService {
        public static final java.lang.String DESCRIPTOR = "android.companion.ICompanionDeviceDiscoveryService";
        static final int TRANSACTION_onAssociationCreated = 2;
        static final int TRANSACTION_startDiscovery = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.companion.ICompanionDeviceDiscoveryService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.ICompanionDeviceDiscoveryService)) {
                return (android.companion.ICompanionDeviceDiscoveryService) queryLocalInterface;
            }
            return new android.companion.ICompanionDeviceDiscoveryService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startDiscovery";
                case 2:
                    return "onAssociationCreated";
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
                    android.companion.AssociationRequest associationRequest = (android.companion.AssociationRequest) parcel.readTypedObject(android.companion.AssociationRequest.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.companion.IAssociationRequestCallback asInterface = android.companion.IAssociationRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    startDiscovery(associationRequest, readString, asInterface, androidFuture);
                    return true;
                case 2:
                    onAssociationCreated();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.ICompanionDeviceDiscoveryService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.ICompanionDeviceDiscoveryService.Stub.DESCRIPTOR;
            }

            @Override // android.companion.ICompanionDeviceDiscoveryService
            public void startDiscovery(android.companion.AssociationRequest associationRequest, java.lang.String str, android.companion.IAssociationRequestCallback iAssociationRequestCallback, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceDiscoveryService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(associationRequest, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAssociationRequestCallback);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceDiscoveryService
            public void onAssociationCreated() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceDiscoveryService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
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
