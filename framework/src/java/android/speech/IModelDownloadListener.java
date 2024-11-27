package android.speech;

/* loaded from: classes3.dex */
public interface IModelDownloadListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.speech.IModelDownloadListener";

    void onError(int i) throws android.os.RemoteException;

    void onProgress(int i) throws android.os.RemoteException;

    void onScheduled() throws android.os.RemoteException;

    void onSuccess() throws android.os.RemoteException;

    public static class Default implements android.speech.IModelDownloadListener {
        @Override // android.speech.IModelDownloadListener
        public void onProgress(int i) throws android.os.RemoteException {
        }

        @Override // android.speech.IModelDownloadListener
        public void onSuccess() throws android.os.RemoteException {
        }

        @Override // android.speech.IModelDownloadListener
        public void onScheduled() throws android.os.RemoteException {
        }

        @Override // android.speech.IModelDownloadListener
        public void onError(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.speech.IModelDownloadListener {
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onProgress = 1;
        static final int TRANSACTION_onScheduled = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, android.speech.IModelDownloadListener.DESCRIPTOR);
        }

        public static android.speech.IModelDownloadListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.speech.IModelDownloadListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.speech.IModelDownloadListener)) {
                return (android.speech.IModelDownloadListener) queryLocalInterface;
            }
            return new android.speech.IModelDownloadListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onProgress";
                case 2:
                    return "onSuccess";
                case 3:
                    return "onScheduled";
                case 4:
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
                parcel.enforceInterface(android.speech.IModelDownloadListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.speech.IModelDownloadListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onProgress(readInt);
                    return true;
                case 2:
                    onSuccess();
                    return true;
                case 3:
                    onScheduled();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.speech.IModelDownloadListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.speech.IModelDownloadListener.DESCRIPTOR;
            }

            @Override // android.speech.IModelDownloadListener
            public void onProgress(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IModelDownloadListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IModelDownloadListener
            public void onSuccess() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IModelDownloadListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IModelDownloadListener
            public void onScheduled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IModelDownloadListener.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IModelDownloadListener
            public void onError(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IModelDownloadListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
