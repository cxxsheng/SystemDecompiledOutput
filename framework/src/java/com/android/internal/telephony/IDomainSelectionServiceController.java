package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface IDomainSelectionServiceController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.IDomainSelectionServiceController";

    void selectDomain(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes, com.android.internal.telephony.ITransportSelectorCallback iTransportSelectorCallback) throws android.os.RemoteException;

    void updateBarringInfo(int i, int i2, android.telephony.BarringInfo barringInfo) throws android.os.RemoteException;

    void updateServiceState(int i, int i2, android.telephony.ServiceState serviceState) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.IDomainSelectionServiceController {
        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void selectDomain(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes, com.android.internal.telephony.ITransportSelectorCallback iTransportSelectorCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateServiceState(int i, int i2, android.telephony.ServiceState serviceState) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateBarringInfo(int i, int i2, android.telephony.BarringInfo barringInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.IDomainSelectionServiceController {
        static final int TRANSACTION_selectDomain = 1;
        static final int TRANSACTION_updateBarringInfo = 3;
        static final int TRANSACTION_updateServiceState = 2;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.IDomainSelectionServiceController.DESCRIPTOR);
        }

        public static com.android.internal.telephony.IDomainSelectionServiceController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.IDomainSelectionServiceController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.IDomainSelectionServiceController)) {
                return (com.android.internal.telephony.IDomainSelectionServiceController) queryLocalInterface;
            }
            return new com.android.internal.telephony.IDomainSelectionServiceController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "selectDomain";
                case 2:
                    return "updateServiceState";
                case 3:
                    return "updateBarringInfo";
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
                parcel.enforceInterface(com.android.internal.telephony.IDomainSelectionServiceController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.IDomainSelectionServiceController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes = (android.telephony.DomainSelectionService.SelectionAttributes) parcel.readTypedObject(android.telephony.DomainSelectionService.SelectionAttributes.CREATOR);
                    com.android.internal.telephony.ITransportSelectorCallback asInterface = com.android.internal.telephony.ITransportSelectorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    selectDomain(selectionAttributes, asInterface);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.telephony.ServiceState serviceState = (android.telephony.ServiceState) parcel.readTypedObject(android.telephony.ServiceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateServiceState(readInt, readInt2, serviceState);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    android.telephony.BarringInfo barringInfo = (android.telephony.BarringInfo) parcel.readTypedObject(android.telephony.BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateBarringInfo(readInt3, readInt4, barringInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.IDomainSelectionServiceController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.IDomainSelectionServiceController.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IDomainSelectionServiceController
            public void selectDomain(android.telephony.DomainSelectionService.SelectionAttributes selectionAttributes, com.android.internal.telephony.ITransportSelectorCallback iTransportSelectorCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IDomainSelectionServiceController.DESCRIPTOR);
                    obtain.writeTypedObject(selectionAttributes, 0);
                    obtain.writeStrongInterface(iTransportSelectorCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IDomainSelectionServiceController
            public void updateServiceState(int i, int i2, android.telephony.ServiceState serviceState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IDomainSelectionServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(serviceState, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IDomainSelectionServiceController
            public void updateBarringInfo(int i, int i2, android.telephony.BarringInfo barringInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IDomainSelectionServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(barringInfo, 0);
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
