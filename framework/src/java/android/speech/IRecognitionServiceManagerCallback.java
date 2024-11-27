package android.speech;

/* loaded from: classes3.dex */
public interface IRecognitionServiceManagerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.speech.IRecognitionServiceManagerCallback";

    void onError(int i) throws android.os.RemoteException;

    void onSuccess(android.speech.IRecognitionService iRecognitionService) throws android.os.RemoteException;

    public static class Default implements android.speech.IRecognitionServiceManagerCallback {
        @Override // android.speech.IRecognitionServiceManagerCallback
        public void onSuccess(android.speech.IRecognitionService iRecognitionService) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionServiceManagerCallback
        public void onError(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.speech.IRecognitionServiceManagerCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, android.speech.IRecognitionServiceManagerCallback.DESCRIPTOR);
        }

        public static android.speech.IRecognitionServiceManagerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.speech.IRecognitionServiceManagerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.speech.IRecognitionServiceManagerCallback)) {
                return (android.speech.IRecognitionServiceManagerCallback) queryLocalInterface;
            }
            return new android.speech.IRecognitionServiceManagerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSuccess";
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
                parcel.enforceInterface(android.speech.IRecognitionServiceManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.speech.IRecognitionServiceManagerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.speech.IRecognitionService asInterface = android.speech.IRecognitionService.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSuccess(asInterface);
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

        private static class Proxy implements android.speech.IRecognitionServiceManagerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.speech.IRecognitionServiceManagerCallback.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionServiceManagerCallback
            public void onSuccess(android.speech.IRecognitionService iRecognitionService) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionServiceManagerCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecognitionService);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionServiceManagerCallback
            public void onError(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionServiceManagerCallback.DESCRIPTOR);
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
