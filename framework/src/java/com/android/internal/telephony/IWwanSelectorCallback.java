package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface IWwanSelectorCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.IWwanSelectorCallback";

    void onCancel() throws android.os.RemoteException;

    void onDomainSelected(int i, boolean z) throws android.os.RemoteException;

    void onRequestEmergencyNetworkScan(int[] iArr, int i, boolean z, com.android.internal.telephony.IWwanSelectorResultCallback iWwanSelectorResultCallback) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.IWwanSelectorCallback {
        @Override // com.android.internal.telephony.IWwanSelectorCallback
        public void onRequestEmergencyNetworkScan(int[] iArr, int i, boolean z, com.android.internal.telephony.IWwanSelectorResultCallback iWwanSelectorResultCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IWwanSelectorCallback
        public void onDomainSelected(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IWwanSelectorCallback
        public void onCancel() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.IWwanSelectorCallback {
        static final int TRANSACTION_onCancel = 3;
        static final int TRANSACTION_onDomainSelected = 2;
        static final int TRANSACTION_onRequestEmergencyNetworkScan = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.IWwanSelectorCallback.DESCRIPTOR);
        }

        public static com.android.internal.telephony.IWwanSelectorCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.IWwanSelectorCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.IWwanSelectorCallback)) {
                return (com.android.internal.telephony.IWwanSelectorCallback) queryLocalInterface;
            }
            return new com.android.internal.telephony.IWwanSelectorCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRequestEmergencyNetworkScan";
                case 2:
                    return "onDomainSelected";
                case 3:
                    return "onCancel";
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
                parcel.enforceInterface(com.android.internal.telephony.IWwanSelectorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.IWwanSelectorCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] createIntArray = parcel.createIntArray();
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    com.android.internal.telephony.IWwanSelectorResultCallback asInterface = com.android.internal.telephony.IWwanSelectorResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRequestEmergencyNetworkScan(createIntArray, readInt, readBoolean, asInterface);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onDomainSelected(readInt2, readBoolean2);
                    return true;
                case 3:
                    onCancel();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.IWwanSelectorCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.IWwanSelectorCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onRequestEmergencyNetworkScan(int[] iArr, int i, boolean z, com.android.internal.telephony.IWwanSelectorResultCallback iWwanSelectorResultCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IWwanSelectorCallback.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iWwanSelectorResultCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onDomainSelected(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IWwanSelectorCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWwanSelectorCallback
            public void onCancel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IWwanSelectorCallback.DESCRIPTOR);
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
