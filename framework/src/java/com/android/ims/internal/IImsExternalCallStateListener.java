package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsExternalCallStateListener extends android.os.IInterface {
    void onImsExternalCallStateUpdate(java.util.List<android.telephony.ims.ImsExternalCallState> list) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsExternalCallStateListener {
        @Override // com.android.ims.internal.IImsExternalCallStateListener
        public void onImsExternalCallStateUpdate(java.util.List<android.telephony.ims.ImsExternalCallState> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsExternalCallStateListener {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsExternalCallStateListener";
        static final int TRANSACTION_onImsExternalCallStateUpdate = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsExternalCallStateListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsExternalCallStateListener)) {
                return (com.android.ims.internal.IImsExternalCallStateListener) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsExternalCallStateListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onImsExternalCallStateUpdate";
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
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.ims.ImsExternalCallState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImsExternalCallStateUpdate(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsExternalCallStateListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsExternalCallStateListener.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsExternalCallStateListener
            public void onImsExternalCallStateUpdate(java.util.List<android.telephony.ims.ImsExternalCallState> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsExternalCallStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
