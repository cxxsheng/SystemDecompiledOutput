package com.android.internal.telephony.euicc;

/* loaded from: classes5.dex */
public interface ISwitchToProfileCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.euicc.ISwitchToProfileCallback";

    void onComplete(int i, android.service.euicc.EuiccProfileInfo euiccProfileInfo) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.euicc.ISwitchToProfileCallback {
        @Override // com.android.internal.telephony.euicc.ISwitchToProfileCallback
        public void onComplete(int i, android.service.euicc.EuiccProfileInfo euiccProfileInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.euicc.ISwitchToProfileCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.euicc.ISwitchToProfileCallback.DESCRIPTOR);
        }

        public static com.android.internal.telephony.euicc.ISwitchToProfileCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.euicc.ISwitchToProfileCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.euicc.ISwitchToProfileCallback)) {
                return (com.android.internal.telephony.euicc.ISwitchToProfileCallback) queryLocalInterface;
            }
            return new com.android.internal.telephony.euicc.ISwitchToProfileCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(com.android.internal.telephony.euicc.ISwitchToProfileCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.euicc.ISwitchToProfileCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.service.euicc.EuiccProfileInfo euiccProfileInfo = (android.service.euicc.EuiccProfileInfo) parcel.readTypedObject(android.service.euicc.EuiccProfileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onComplete(readInt, euiccProfileInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.euicc.ISwitchToProfileCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.euicc.ISwitchToProfileCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.ISwitchToProfileCallback
            public void onComplete(int i, android.service.euicc.EuiccProfileInfo euiccProfileInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.ISwitchToProfileCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(euiccProfileInfo, 0);
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
