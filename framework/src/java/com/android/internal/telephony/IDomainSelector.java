package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface IDomainSelector extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.IDomainSelector";

    void finishSelection() throws android.os.RemoteException;

    void reselectDomain(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.IDomainSelector {
        @Override // com.android.internal.telephony.IDomainSelector
        public void reselectDomain(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IDomainSelector
        public void finishSelection() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.IDomainSelector {
        static final int TRANSACTION_finishSelection = 2;
        static final int TRANSACTION_reselectDomain = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.IDomainSelector.DESCRIPTOR);
        }

        public static com.android.internal.telephony.IDomainSelector asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.IDomainSelector.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.IDomainSelector)) {
                return (com.android.internal.telephony.IDomainSelector) queryLocalInterface;
            }
            return new com.android.internal.telephony.IDomainSelector.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reselectDomain";
                case 2:
                    return "finishSelection";
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
                parcel.enforceInterface(com.android.internal.telephony.IDomainSelector.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.IDomainSelector.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes = (android.telephony.DomainSelectionService.SelectionAttributes) parcel.readTypedObject(android.telephony.DomainSelectionService.SelectionAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    reselectDomain(selectionAttributes);
                    return true;
                case 2:
                    finishSelection();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.IDomainSelector {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.IDomainSelector.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void reselectDomain(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IDomainSelector.DESCRIPTOR);
                    obtain.writeTypedObject(selectionAttributes, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void finishSelection() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IDomainSelector.DESCRIPTOR);
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
