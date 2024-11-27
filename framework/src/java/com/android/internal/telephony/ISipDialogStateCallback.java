package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface ISipDialogStateCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ISipDialogStateCallback";

    void onActiveSipDialogsChanged(java.util.List<android.telephony.ims.SipDialogState> list) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.ISipDialogStateCallback {
        @Override // com.android.internal.telephony.ISipDialogStateCallback
        public void onActiveSipDialogsChanged(java.util.List<android.telephony.ims.SipDialogState> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ISipDialogStateCallback {
        static final int TRANSACTION_onActiveSipDialogsChanged = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.ISipDialogStateCallback.DESCRIPTOR);
        }

        public static com.android.internal.telephony.ISipDialogStateCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.ISipDialogStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.ISipDialogStateCallback)) {
                return (com.android.internal.telephony.ISipDialogStateCallback) queryLocalInterface;
            }
            return new com.android.internal.telephony.ISipDialogStateCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onActiveSipDialogsChanged";
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
                parcel.enforceInterface(com.android.internal.telephony.ISipDialogStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.ISipDialogStateCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.ims.SipDialogState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onActiveSipDialogsChanged(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.ISipDialogStateCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.ISipDialogStateCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISipDialogStateCallback
            public void onActiveSipDialogsChanged(java.util.List<android.telephony.ims.SipDialogState> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISipDialogStateCallback.DESCRIPTOR);
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
