package com.android.internal.telephony.euicc;

/* loaded from: classes5.dex */
public interface IGetRulesAuthTableCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.euicc.IGetRulesAuthTableCallback";

    void onComplete(int i, android.telephony.euicc.EuiccRulesAuthTable euiccRulesAuthTable) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.euicc.IGetRulesAuthTableCallback {
        @Override // com.android.internal.telephony.euicc.IGetRulesAuthTableCallback
        public void onComplete(int i, android.telephony.euicc.EuiccRulesAuthTable euiccRulesAuthTable) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.euicc.IGetRulesAuthTableCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.euicc.IGetRulesAuthTableCallback.DESCRIPTOR);
        }

        public static com.android.internal.telephony.euicc.IGetRulesAuthTableCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.euicc.IGetRulesAuthTableCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.euicc.IGetRulesAuthTableCallback)) {
                return (com.android.internal.telephony.euicc.IGetRulesAuthTableCallback) queryLocalInterface;
            }
            return new com.android.internal.telephony.euicc.IGetRulesAuthTableCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onComplete";
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
                parcel.enforceInterface(com.android.internal.telephony.euicc.IGetRulesAuthTableCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.euicc.IGetRulesAuthTableCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.telephony.euicc.EuiccRulesAuthTable euiccRulesAuthTable = (android.telephony.euicc.EuiccRulesAuthTable) parcel.readTypedObject(android.telephony.euicc.EuiccRulesAuthTable.CREATOR);
                    parcel.enforceNoDataAvail();
                    onComplete(readInt, euiccRulesAuthTable);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.euicc.IGetRulesAuthTableCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.euicc.IGetRulesAuthTableCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IGetRulesAuthTableCallback
            public void onComplete(int i, android.telephony.euicc.EuiccRulesAuthTable euiccRulesAuthTable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IGetRulesAuthTableCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(euiccRulesAuthTable, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
