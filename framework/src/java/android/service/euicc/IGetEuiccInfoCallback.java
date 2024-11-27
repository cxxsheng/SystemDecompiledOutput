package android.service.euicc;

/* loaded from: classes3.dex */
public interface IGetEuiccInfoCallback extends android.os.IInterface {
    void onSuccess(android.telephony.euicc.EuiccInfo euiccInfo) throws android.os.RemoteException;

    public static class Default implements android.service.euicc.IGetEuiccInfoCallback {
        @Override // android.service.euicc.IGetEuiccInfoCallback
        public void onSuccess(android.telephony.euicc.EuiccInfo euiccInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.euicc.IGetEuiccInfoCallback {
        public static final java.lang.String DESCRIPTOR = "android.service.euicc.IGetEuiccInfoCallback";
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.euicc.IGetEuiccInfoCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.euicc.IGetEuiccInfoCallback)) {
                return (android.service.euicc.IGetEuiccInfoCallback) queryLocalInterface;
            }
            return new android.service.euicc.IGetEuiccInfoCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSuccess";
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
                    android.telephony.euicc.EuiccInfo euiccInfo = (android.telephony.euicc.EuiccInfo) parcel.readTypedObject(android.telephony.euicc.EuiccInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(euiccInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.euicc.IGetEuiccInfoCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.euicc.IGetEuiccInfoCallback.Stub.DESCRIPTOR;
            }

            @Override // android.service.euicc.IGetEuiccInfoCallback
            public void onSuccess(android.telephony.euicc.EuiccInfo euiccInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IGetEuiccInfoCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(euiccInfo, 0);
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
