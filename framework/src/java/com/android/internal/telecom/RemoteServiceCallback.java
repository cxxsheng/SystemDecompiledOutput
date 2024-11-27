package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface RemoteServiceCallback extends android.os.IInterface {
    void onError() throws android.os.RemoteException;

    void onResult(java.util.List<android.content.ComponentName> list, java.util.List<android.os.IBinder> list2) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.RemoteServiceCallback {
        @Override // com.android.internal.telecom.RemoteServiceCallback
        public void onError() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.RemoteServiceCallback
        public void onResult(java.util.List<android.content.ComponentName> list, java.util.List<android.os.IBinder> list2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.RemoteServiceCallback {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.RemoteServiceCallback";
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onResult = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telecom.RemoteServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.RemoteServiceCallback)) {
                return (com.android.internal.telecom.RemoteServiceCallback) queryLocalInterface;
            }
            return new com.android.internal.telecom.RemoteServiceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onError";
                case 2:
                    return "onResult";
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
                    onError();
                    return true;
                case 2:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.content.ComponentName.CREATOR);
                    java.util.ArrayList<android.os.IBinder> createBinderArrayList = parcel.createBinderArrayList();
                    parcel.enforceNoDataAvail();
                    onResult(createTypedArrayList, createBinderArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.RemoteServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.RemoteServiceCallback.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.RemoteServiceCallback
            public void onError() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.RemoteServiceCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.RemoteServiceCallback
            public void onResult(java.util.List<android.content.ComponentName> list, java.util.List<android.os.IBinder> list2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.RemoteServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeBinderList(list2);
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
