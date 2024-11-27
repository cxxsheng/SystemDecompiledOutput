package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface ITransportSelectorCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ITransportSelectorCallback";

    void onCreated(com.android.internal.telephony.IDomainSelector iDomainSelector) throws android.os.RemoteException;

    void onSelectionTerminated(int i) throws android.os.RemoteException;

    void onWlanSelected(boolean z) throws android.os.RemoteException;

    void onWwanSelectedAsync(com.android.internal.telephony.ITransportSelectorResultCallback iTransportSelectorResultCallback) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.ITransportSelectorCallback {
        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onCreated(com.android.internal.telephony.IDomainSelector iDomainSelector) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onWlanSelected(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onWwanSelectedAsync(com.android.internal.telephony.ITransportSelectorResultCallback iTransportSelectorResultCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITransportSelectorCallback
        public void onSelectionTerminated(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ITransportSelectorCallback {
        static final int TRANSACTION_onCreated = 1;
        static final int TRANSACTION_onSelectionTerminated = 4;
        static final int TRANSACTION_onWlanSelected = 2;
        static final int TRANSACTION_onWwanSelectedAsync = 3;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.ITransportSelectorCallback.DESCRIPTOR);
        }

        public static com.android.internal.telephony.ITransportSelectorCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.ITransportSelectorCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.ITransportSelectorCallback)) {
                return (com.android.internal.telephony.ITransportSelectorCallback) queryLocalInterface;
            }
            return new com.android.internal.telephony.ITransportSelectorCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCreated";
                case 2:
                    return "onWlanSelected";
                case 3:
                    return "onWwanSelectedAsync";
                case 4:
                    return "onSelectionTerminated";
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
                parcel.enforceInterface(com.android.internal.telephony.ITransportSelectorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.ITransportSelectorCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.telephony.IDomainSelector asInterface = com.android.internal.telephony.IDomainSelector.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onCreated(asInterface);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onWlanSelected(readBoolean);
                    return true;
                case 3:
                    com.android.internal.telephony.ITransportSelectorResultCallback asInterface2 = com.android.internal.telephony.ITransportSelectorResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onWwanSelectedAsync(asInterface2);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSelectionTerminated(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.ITransportSelectorCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.ITransportSelectorCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onCreated(com.android.internal.telephony.IDomainSelector iDomainSelector) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITransportSelectorCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iDomainSelector);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onWlanSelected(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITransportSelectorCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onWwanSelectedAsync(com.android.internal.telephony.ITransportSelectorResultCallback iTransportSelectorResultCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITransportSelectorCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportSelectorResultCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITransportSelectorCallback
            public void onSelectionTerminated(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITransportSelectorCallback.DESCRIPTOR);
                    obtain.writeInt(i);
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
