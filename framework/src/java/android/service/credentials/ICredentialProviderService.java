package android.service.credentials;

/* loaded from: classes3.dex */
public interface ICredentialProviderService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.credentials.ICredentialProviderService";

    void onBeginCreateCredential(android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest, android.service.credentials.IBeginCreateCredentialCallback iBeginCreateCredentialCallback) throws android.os.RemoteException;

    void onBeginGetCredential(android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest, android.service.credentials.IBeginGetCredentialCallback iBeginGetCredentialCallback) throws android.os.RemoteException;

    void onClearCredentialState(android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.service.credentials.IClearCredentialStateCallback iClearCredentialStateCallback) throws android.os.RemoteException;

    public static class Default implements android.service.credentials.ICredentialProviderService {
        @Override // android.service.credentials.ICredentialProviderService
        public void onBeginGetCredential(android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest, android.service.credentials.IBeginGetCredentialCallback iBeginGetCredentialCallback) throws android.os.RemoteException {
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onBeginCreateCredential(android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest, android.service.credentials.IBeginCreateCredentialCallback iBeginCreateCredentialCallback) throws android.os.RemoteException {
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onClearCredentialState(android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.service.credentials.IClearCredentialStateCallback iClearCredentialStateCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.credentials.ICredentialProviderService {
        static final int TRANSACTION_onBeginCreateCredential = 2;
        static final int TRANSACTION_onBeginGetCredential = 1;
        static final int TRANSACTION_onClearCredentialState = 3;

        public Stub() {
            attachInterface(this, android.service.credentials.ICredentialProviderService.DESCRIPTOR);
        }

        public static android.service.credentials.ICredentialProviderService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.credentials.ICredentialProviderService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.credentials.ICredentialProviderService)) {
                return (android.service.credentials.ICredentialProviderService) queryLocalInterface;
            }
            return new android.service.credentials.ICredentialProviderService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onBeginGetCredential";
                case 2:
                    return "onBeginCreateCredential";
                case 3:
                    return "onClearCredentialState";
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
                parcel.enforceInterface(android.service.credentials.ICredentialProviderService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.credentials.ICredentialProviderService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest = (android.service.credentials.BeginGetCredentialRequest) parcel.readTypedObject(android.service.credentials.BeginGetCredentialRequest.CREATOR);
                    android.service.credentials.IBeginGetCredentialCallback asInterface = android.service.credentials.IBeginGetCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onBeginGetCredential(beginGetCredentialRequest, asInterface);
                    return true;
                case 2:
                    android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest = (android.service.credentials.BeginCreateCredentialRequest) parcel.readTypedObject(android.service.credentials.BeginCreateCredentialRequest.CREATOR);
                    android.service.credentials.IBeginCreateCredentialCallback asInterface2 = android.service.credentials.IBeginCreateCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onBeginCreateCredential(beginCreateCredentialRequest, asInterface2);
                    return true;
                case 3:
                    android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest = (android.service.credentials.ClearCredentialStateRequest) parcel.readTypedObject(android.service.credentials.ClearCredentialStateRequest.CREATOR);
                    android.service.credentials.IClearCredentialStateCallback asInterface3 = android.service.credentials.IClearCredentialStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onClearCredentialState(clearCredentialStateRequest, asInterface3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.credentials.ICredentialProviderService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.credentials.ICredentialProviderService.DESCRIPTOR;
            }

            @Override // android.service.credentials.ICredentialProviderService
            public void onBeginGetCredential(android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest, android.service.credentials.IBeginGetCredentialCallback iBeginGetCredentialCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.credentials.ICredentialProviderService.DESCRIPTOR);
                    obtain.writeTypedObject(beginGetCredentialRequest, 0);
                    obtain.writeStrongInterface(iBeginGetCredentialCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.credentials.ICredentialProviderService
            public void onBeginCreateCredential(android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest, android.service.credentials.IBeginCreateCredentialCallback iBeginCreateCredentialCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.credentials.ICredentialProviderService.DESCRIPTOR);
                    obtain.writeTypedObject(beginCreateCredentialRequest, 0);
                    obtain.writeStrongInterface(iBeginCreateCredentialCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.credentials.ICredentialProviderService
            public void onClearCredentialState(android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.service.credentials.IClearCredentialStateCallback iClearCredentialStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.credentials.ICredentialProviderService.DESCRIPTOR);
                    obtain.writeTypedObject(clearCredentialStateRequest, 0);
                    obtain.writeStrongInterface(iClearCredentialStateCallback);
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
