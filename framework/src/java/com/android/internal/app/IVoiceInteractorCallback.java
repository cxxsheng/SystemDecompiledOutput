package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IVoiceInteractorCallback extends android.os.IInterface {
    void deliverAbortVoiceResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, android.os.Bundle bundle) throws android.os.RemoteException;

    void deliverCancel(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest) throws android.os.RemoteException;

    void deliverCommandResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.os.Bundle bundle) throws android.os.RemoteException;

    void deliverCompleteVoiceResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, android.os.Bundle bundle) throws android.os.RemoteException;

    void deliverConfirmationResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.os.Bundle bundle) throws android.os.RemoteException;

    void deliverPickOptionResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) throws android.os.RemoteException;

    void destroy() throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IVoiceInteractorCallback {
        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverConfirmationResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverPickOptionResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverCompleteVoiceResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverAbortVoiceResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverCommandResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverCancel(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void destroy() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IVoiceInteractorCallback {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IVoiceInteractorCallback";
        static final int TRANSACTION_deliverAbortVoiceResult = 4;
        static final int TRANSACTION_deliverCancel = 6;
        static final int TRANSACTION_deliverCommandResult = 5;
        static final int TRANSACTION_deliverCompleteVoiceResult = 3;
        static final int TRANSACTION_deliverConfirmationResult = 1;
        static final int TRANSACTION_deliverPickOptionResult = 2;
        static final int TRANSACTION_destroy = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.app.IVoiceInteractorCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IVoiceInteractorCallback)) {
                return (com.android.internal.app.IVoiceInteractorCallback) queryLocalInterface;
            }
            return new com.android.internal.app.IVoiceInteractorCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "deliverConfirmationResult";
                case 2:
                    return "deliverPickOptionResult";
                case 3:
                    return "deliverCompleteVoiceResult";
                case 4:
                    return "deliverAbortVoiceResult";
                case 5:
                    return "deliverCommandResult";
                case 6:
                    return "deliverCancel";
                case 7:
                    return "destroy";
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
                    com.android.internal.app.IVoiceInteractorRequest asInterface = com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverConfirmationResult(asInterface, readBoolean, bundle);
                    return true;
                case 2:
                    com.android.internal.app.IVoiceInteractorRequest asInterface2 = com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean2 = parcel.readBoolean();
                    android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr = (android.app.VoiceInteractor.PickOptionRequest.Option[]) parcel.createTypedArray(android.app.VoiceInteractor.PickOptionRequest.Option.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverPickOptionResult(asInterface2, readBoolean2, optionArr, bundle2);
                    return true;
                case 3:
                    com.android.internal.app.IVoiceInteractorRequest asInterface3 = com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverCompleteVoiceResult(asInterface3, bundle3);
                    return true;
                case 4:
                    com.android.internal.app.IVoiceInteractorRequest asInterface4 = com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverAbortVoiceResult(asInterface4, bundle4);
                    return true;
                case 5:
                    com.android.internal.app.IVoiceInteractorRequest asInterface5 = com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean3 = parcel.readBoolean();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverCommandResult(asInterface5, readBoolean3, bundle5);
                    return true;
                case 6:
                    com.android.internal.app.IVoiceInteractorRequest asInterface6 = com.android.internal.app.IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deliverCancel(asInterface6);
                    return true;
                case 7:
                    destroy();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IVoiceInteractorCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IVoiceInteractorCallback.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverConfirmationResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractorCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverPickOptionResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractorCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeBoolean(z);
                    obtain.writeTypedArray(optionArr, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverCompleteVoiceResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractorCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverAbortVoiceResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractorCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverCommandResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractorCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverCancel(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractorCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void destroy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractorCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
