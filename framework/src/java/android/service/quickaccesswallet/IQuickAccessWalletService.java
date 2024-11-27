package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public interface IQuickAccessWalletService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.quickaccesswallet.IQuickAccessWalletService";

    void onTargetActivityIntentRequested(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws android.os.RemoteException;

    void onWalletCardSelected(android.service.quickaccesswallet.SelectWalletCardRequest selectWalletCardRequest) throws android.os.RemoteException;

    void onWalletCardsRequested(android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws android.os.RemoteException;

    void onWalletDismissed() throws android.os.RemoteException;

    void registerWalletServiceEventListener(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws android.os.RemoteException;

    void unregisterWalletServiceEventListener(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest) throws android.os.RemoteException;

    public static class Default implements android.service.quickaccesswallet.IQuickAccessWalletService {
        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletCardsRequested(android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletCardSelected(android.service.quickaccesswallet.SelectWalletCardRequest selectWalletCardRequest) throws android.os.RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletDismissed() throws android.os.RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void registerWalletServiceEventListener(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void unregisterWalletServiceEventListener(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest) throws android.os.RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onTargetActivityIntentRequested(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.quickaccesswallet.IQuickAccessWalletService {
        static final int TRANSACTION_onTargetActivityIntentRequested = 6;
        static final int TRANSACTION_onWalletCardSelected = 2;
        static final int TRANSACTION_onWalletCardsRequested = 1;
        static final int TRANSACTION_onWalletDismissed = 3;
        static final int TRANSACTION_registerWalletServiceEventListener = 4;
        static final int TRANSACTION_unregisterWalletServiceEventListener = 5;

        public Stub() {
            attachInterface(this, android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
        }

        public static android.service.quickaccesswallet.IQuickAccessWalletService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.quickaccesswallet.IQuickAccessWalletService)) {
                return (android.service.quickaccesswallet.IQuickAccessWalletService) queryLocalInterface;
            }
            return new android.service.quickaccesswallet.IQuickAccessWalletService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onWalletCardsRequested";
                case 2:
                    return "onWalletCardSelected";
                case 3:
                    return "onWalletDismissed";
                case 4:
                    return "registerWalletServiceEventListener";
                case 5:
                    return "unregisterWalletServiceEventListener";
                case 6:
                    return "onTargetActivityIntentRequested";
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
                parcel.enforceInterface(android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest = (android.service.quickaccesswallet.GetWalletCardsRequest) parcel.readTypedObject(android.service.quickaccesswallet.GetWalletCardsRequest.CREATOR);
                    android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks asInterface = android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onWalletCardsRequested(getWalletCardsRequest, asInterface);
                    return true;
                case 2:
                    android.service.quickaccesswallet.SelectWalletCardRequest selectWalletCardRequest = (android.service.quickaccesswallet.SelectWalletCardRequest) parcel.readTypedObject(android.service.quickaccesswallet.SelectWalletCardRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    onWalletCardSelected(selectWalletCardRequest);
                    return true;
                case 3:
                    onWalletDismissed();
                    return true;
                case 4:
                    android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest = (android.service.quickaccesswallet.WalletServiceEventListenerRequest) parcel.readTypedObject(android.service.quickaccesswallet.WalletServiceEventListenerRequest.CREATOR);
                    android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks asInterface2 = android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerWalletServiceEventListener(walletServiceEventListenerRequest, asInterface2);
                    return true;
                case 5:
                    android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest2 = (android.service.quickaccesswallet.WalletServiceEventListenerRequest) parcel.readTypedObject(android.service.quickaccesswallet.WalletServiceEventListenerRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterWalletServiceEventListener(walletServiceEventListenerRequest2);
                    return true;
                case 6:
                    android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks asInterface3 = android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onTargetActivityIntentRequested(asInterface3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.quickaccesswallet.IQuickAccessWalletService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR;
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void onWalletCardsRequested(android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
                    obtain.writeTypedObject(getWalletCardsRequest, 0);
                    obtain.writeStrongInterface(iQuickAccessWalletServiceCallbacks);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void onWalletCardSelected(android.service.quickaccesswallet.SelectWalletCardRequest selectWalletCardRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
                    obtain.writeTypedObject(selectWalletCardRequest, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void onWalletDismissed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void registerWalletServiceEventListener(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
                    obtain.writeTypedObject(walletServiceEventListenerRequest, 0);
                    obtain.writeStrongInterface(iQuickAccessWalletServiceCallbacks);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void unregisterWalletServiceEventListener(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
                    obtain.writeTypedObject(walletServiceEventListenerRequest, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void onTargetActivityIntentRequested(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletService.DESCRIPTOR);
                    obtain.writeStrongInterface(iQuickAccessWalletServiceCallbacks);
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
