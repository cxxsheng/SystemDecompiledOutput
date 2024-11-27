package android.service.euicc;

/* loaded from: classes3.dex */
public interface IEuiccServiceDumpResultCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.euicc.IEuiccServiceDumpResultCallback";

    void onComplete(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.service.euicc.IEuiccServiceDumpResultCallback {
        @Override // android.service.euicc.IEuiccServiceDumpResultCallback
        public void onComplete(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.euicc.IEuiccServiceDumpResultCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, android.service.euicc.IEuiccServiceDumpResultCallback.DESCRIPTOR);
        }

        public static android.service.euicc.IEuiccServiceDumpResultCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.euicc.IEuiccServiceDumpResultCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.euicc.IEuiccServiceDumpResultCallback)) {
                return (android.service.euicc.IEuiccServiceDumpResultCallback) queryLocalInterface;
            }
            return new android.service.euicc.IEuiccServiceDumpResultCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(android.service.euicc.IEuiccServiceDumpResultCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.euicc.IEuiccServiceDumpResultCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onComplete(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.euicc.IEuiccServiceDumpResultCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.euicc.IEuiccServiceDumpResultCallback.DESCRIPTOR;
            }

            @Override // android.service.euicc.IEuiccServiceDumpResultCallback
            public void onComplete(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccServiceDumpResultCallback.DESCRIPTOR);
                    obtain.writeString(str);
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
