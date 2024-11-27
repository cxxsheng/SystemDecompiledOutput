package com.android.internal.telephony.euicc;

/* loaded from: classes5.dex */
public interface IListNotificationsCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.euicc.IListNotificationsCallback";

    void onComplete(int i, android.telephony.euicc.EuiccNotification[] euiccNotificationArr) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.euicc.IListNotificationsCallback {
        @Override // com.android.internal.telephony.euicc.IListNotificationsCallback
        public void onComplete(int i, android.telephony.euicc.EuiccNotification[] euiccNotificationArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.euicc.IListNotificationsCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.euicc.IListNotificationsCallback.DESCRIPTOR);
        }

        public static com.android.internal.telephony.euicc.IListNotificationsCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.euicc.IListNotificationsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.euicc.IListNotificationsCallback)) {
                return (com.android.internal.telephony.euicc.IListNotificationsCallback) queryLocalInterface;
            }
            return new com.android.internal.telephony.euicc.IListNotificationsCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(com.android.internal.telephony.euicc.IListNotificationsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.euicc.IListNotificationsCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.telephony.euicc.EuiccNotification[] euiccNotificationArr = (android.telephony.euicc.EuiccNotification[]) parcel.createTypedArray(android.telephony.euicc.EuiccNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    onComplete(readInt, euiccNotificationArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.euicc.IListNotificationsCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.euicc.IListNotificationsCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IListNotificationsCallback
            public void onComplete(int i, android.telephony.euicc.EuiccNotification[] euiccNotificationArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IListNotificationsCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(euiccNotificationArr, 0);
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
