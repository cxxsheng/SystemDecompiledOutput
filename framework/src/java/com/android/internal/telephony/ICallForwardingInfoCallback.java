package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface ICallForwardingInfoCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ICallForwardingInfoCallback";

    void onCallForwardingInfoAvailable(android.telephony.CallForwardingInfo callForwardingInfo) throws android.os.RemoteException;

    void onError(int i) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.ICallForwardingInfoCallback {
        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onCallForwardingInfoAvailable(android.telephony.CallForwardingInfo callForwardingInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onError(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ICallForwardingInfoCallback {
        static final int TRANSACTION_onCallForwardingInfoAvailable = 1;
        static final int TRANSACTION_onError = 2;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.ICallForwardingInfoCallback.DESCRIPTOR);
        }

        public static com.android.internal.telephony.ICallForwardingInfoCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.ICallForwardingInfoCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.ICallForwardingInfoCallback)) {
                return (com.android.internal.telephony.ICallForwardingInfoCallback) queryLocalInterface;
            }
            return new com.android.internal.telephony.ICallForwardingInfoCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCallForwardingInfoAvailable";
                case 2:
                    return "onError";
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
                parcel.enforceInterface(com.android.internal.telephony.ICallForwardingInfoCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.ICallForwardingInfoCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.CallForwardingInfo callForwardingInfo = (android.telephony.CallForwardingInfo) parcel.readTypedObject(android.telephony.CallForwardingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallForwardingInfoAvailable(callForwardingInfo);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.ICallForwardingInfoCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.ICallForwardingInfoCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ICallForwardingInfoCallback
            public void onCallForwardingInfoAvailable(android.telephony.CallForwardingInfo callForwardingInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICallForwardingInfoCallback.DESCRIPTOR);
                    obtain.writeTypedObject(callForwardingInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICallForwardingInfoCallback
            public void onError(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ICallForwardingInfoCallback.DESCRIPTOR);
                    obtain.writeInt(i);
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
