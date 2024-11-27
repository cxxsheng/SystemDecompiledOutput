package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public interface IQuickAccessWalletServiceCallbacks extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks";

    void onGetWalletCardsFailure(android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError) throws android.os.RemoteException;

    void onGetWalletCardsSuccess(android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse) throws android.os.RemoteException;

    void onTargetActivityPendingIntentReceived(android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void onWalletServiceEvent(android.service.quickaccesswallet.WalletServiceEvent walletServiceEvent) throws android.os.RemoteException;

    public static class Default implements android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks {
        @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGetWalletCardsSuccess(android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse) throws android.os.RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGetWalletCardsFailure(android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError) throws android.os.RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onWalletServiceEvent(android.service.quickaccesswallet.WalletServiceEvent walletServiceEvent) throws android.os.RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onTargetActivityPendingIntentReceived(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks {
        static final int TRANSACTION_onGetWalletCardsFailure = 2;
        static final int TRANSACTION_onGetWalletCardsSuccess = 1;
        static final int TRANSACTION_onTargetActivityPendingIntentReceived = 4;
        static final int TRANSACTION_onWalletServiceEvent = 3;

        public Stub() {
            attachInterface(this, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
        }

        public static android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks)) {
                return (android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks) queryLocalInterface;
            }
            return new android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onGetWalletCardsSuccess";
                case 2:
                    return "onGetWalletCardsFailure";
                case 3:
                    return "onWalletServiceEvent";
                case 4:
                    return "onTargetActivityPendingIntentReceived";
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
                parcel.enforceInterface(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse = (android.service.quickaccesswallet.GetWalletCardsResponse) parcel.readTypedObject(android.service.quickaccesswallet.GetWalletCardsResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGetWalletCardsSuccess(getWalletCardsResponse);
                    return true;
                case 2:
                    android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError = (android.service.quickaccesswallet.GetWalletCardsError) parcel.readTypedObject(android.service.quickaccesswallet.GetWalletCardsError.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGetWalletCardsFailure(getWalletCardsError);
                    return true;
                case 3:
                    android.service.quickaccesswallet.WalletServiceEvent walletServiceEvent = (android.service.quickaccesswallet.WalletServiceEvent) parcel.readTypedObject(android.service.quickaccesswallet.WalletServiceEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onWalletServiceEvent(walletServiceEvent);
                    return true;
                case 4:
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTargetActivityPendingIntentReceived(pendingIntent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.DESCRIPTOR;
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
            public void onGetWalletCardsSuccess(android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(getWalletCardsResponse, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
            public void onGetWalletCardsFailure(android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(getWalletCardsError, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
            public void onWalletServiceEvent(android.service.quickaccesswallet.WalletServiceEvent walletServiceEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(walletServiceEvent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
            public void onTargetActivityPendingIntentReceived(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
