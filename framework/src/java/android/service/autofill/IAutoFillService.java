package android.service.autofill;

/* loaded from: classes3.dex */
public interface IAutoFillService extends android.os.IInterface {
    void onConnectedStateChanged(boolean z) throws android.os.RemoteException;

    void onConvertCredentialRequest(android.service.autofill.ConvertCredentialRequest convertCredentialRequest, android.service.autofill.IConvertCredentialCallback iConvertCredentialCallback) throws android.os.RemoteException;

    void onFillCredentialRequest(android.service.autofill.FillRequest fillRequest, android.service.autofill.IFillCallback iFillCallback, android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) throws android.os.RemoteException;

    void onFillRequest(android.service.autofill.FillRequest fillRequest, android.service.autofill.IFillCallback iFillCallback) throws android.os.RemoteException;

    void onSaveRequest(android.service.autofill.SaveRequest saveRequest, android.service.autofill.ISaveCallback iSaveCallback) throws android.os.RemoteException;

    void onSavedPasswordCountRequest(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    public static class Default implements android.service.autofill.IAutoFillService {
        @Override // android.service.autofill.IAutoFillService
        public void onConnectedStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillRequest(android.service.autofill.FillRequest fillRequest, android.service.autofill.IFillCallback iFillCallback) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillCredentialRequest(android.service.autofill.FillRequest fillRequest, android.service.autofill.IFillCallback iFillCallback, android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSaveRequest(android.service.autofill.SaveRequest saveRequest, android.service.autofill.ISaveCallback iSaveCallback) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSavedPasswordCountRequest(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IAutoFillService
        public void onConvertCredentialRequest(android.service.autofill.ConvertCredentialRequest convertCredentialRequest, android.service.autofill.IConvertCredentialCallback iConvertCredentialCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.autofill.IAutoFillService {
        public static final java.lang.String DESCRIPTOR = "android.service.autofill.IAutoFillService";
        static final int TRANSACTION_onConnectedStateChanged = 1;
        static final int TRANSACTION_onConvertCredentialRequest = 6;
        static final int TRANSACTION_onFillCredentialRequest = 3;
        static final int TRANSACTION_onFillRequest = 2;
        static final int TRANSACTION_onSaveRequest = 4;
        static final int TRANSACTION_onSavedPasswordCountRequest = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.autofill.IAutoFillService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.autofill.IAutoFillService)) {
                return (android.service.autofill.IAutoFillService) queryLocalInterface;
            }
            return new android.service.autofill.IAutoFillService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnectedStateChanged";
                case 2:
                    return "onFillRequest";
                case 3:
                    return "onFillCredentialRequest";
                case 4:
                    return "onSaveRequest";
                case 5:
                    return "onSavedPasswordCountRequest";
                case 6:
                    return "onConvertCredentialRequest";
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConnectedStateChanged(readBoolean);
                    return true;
                case 2:
                    android.service.autofill.FillRequest fillRequest = (android.service.autofill.FillRequest) parcel.readTypedObject(android.service.autofill.FillRequest.CREATOR);
                    android.service.autofill.IFillCallback asInterface = android.service.autofill.IFillCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onFillRequest(fillRequest, asInterface);
                    return true;
                case 3:
                    android.service.autofill.FillRequest fillRequest2 = (android.service.autofill.FillRequest) parcel.readTypedObject(android.service.autofill.FillRequest.CREATOR);
                    android.service.autofill.IFillCallback asInterface2 = android.service.autofill.IFillCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.view.autofill.IAutoFillManagerClient asInterface3 = android.view.autofill.IAutoFillManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onFillCredentialRequest(fillRequest2, asInterface2, asInterface3);
                    return true;
                case 4:
                    android.service.autofill.SaveRequest saveRequest = (android.service.autofill.SaveRequest) parcel.readTypedObject(android.service.autofill.SaveRequest.CREATOR);
                    android.service.autofill.ISaveCallback asInterface4 = android.service.autofill.ISaveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSaveRequest(saveRequest, asInterface4);
                    return true;
                case 5:
                    com.android.internal.os.IResultReceiver asInterface5 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSavedPasswordCountRequest(asInterface5);
                    return true;
                case 6:
                    android.service.autofill.ConvertCredentialRequest convertCredentialRequest = (android.service.autofill.ConvertCredentialRequest) parcel.readTypedObject(android.service.autofill.ConvertCredentialRequest.CREATOR);
                    android.service.autofill.IConvertCredentialCallback asInterface6 = android.service.autofill.IConvertCredentialCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onConvertCredentialRequest(convertCredentialRequest, asInterface6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.autofill.IAutoFillService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.autofill.IAutoFillService.Stub.DESCRIPTOR;
            }

            @Override // android.service.autofill.IAutoFillService
            public void onConnectedStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IAutoFillService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onFillRequest(android.service.autofill.FillRequest fillRequest, android.service.autofill.IFillCallback iFillCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IAutoFillService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fillRequest, 0);
                    obtain.writeStrongInterface(iFillCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onFillCredentialRequest(android.service.autofill.FillRequest fillRequest, android.service.autofill.IFillCallback iFillCallback, android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IAutoFillService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fillRequest, 0);
                    obtain.writeStrongInterface(iFillCallback);
                    obtain.writeStrongInterface(iAutoFillManagerClient);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSaveRequest(android.service.autofill.SaveRequest saveRequest, android.service.autofill.ISaveCallback iSaveCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IAutoFillService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(saveRequest, 0);
                    obtain.writeStrongInterface(iSaveCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onSavedPasswordCountRequest(com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IAutoFillService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IAutoFillService
            public void onConvertCredentialRequest(android.service.autofill.ConvertCredentialRequest convertCredentialRequest, android.service.autofill.IConvertCredentialCallback iConvertCredentialCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IAutoFillService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(convertCredentialRequest, 0);
                    obtain.writeStrongInterface(iConvertCredentialCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
