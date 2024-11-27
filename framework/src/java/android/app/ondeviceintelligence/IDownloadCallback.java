package android.app.ondeviceintelligence;

/* loaded from: classes.dex */
public interface IDownloadCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ondeviceintelligence.IDownloadCallback";

    void onDownloadCompleted(android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    void onDownloadFailed(int i, java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    void onDownloadProgress(long j) throws android.os.RemoteException;

    void onDownloadStarted(long j) throws android.os.RemoteException;

    public static class Default implements android.app.ondeviceintelligence.IDownloadCallback {
        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadStarted(long j) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadProgress(long j) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadFailed(int i, java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadCompleted(android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ondeviceintelligence.IDownloadCallback {
        static final int TRANSACTION_onDownloadCompleted = 5;
        static final int TRANSACTION_onDownloadFailed = 4;
        static final int TRANSACTION_onDownloadProgress = 3;
        static final int TRANSACTION_onDownloadStarted = 2;

        public Stub() {
            attachInterface(this, android.app.ondeviceintelligence.IDownloadCallback.DESCRIPTOR);
        }

        public static android.app.ondeviceintelligence.IDownloadCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ondeviceintelligence.IDownloadCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ondeviceintelligence.IDownloadCallback)) {
                return (android.app.ondeviceintelligence.IDownloadCallback) queryLocalInterface;
            }
            return new android.app.ondeviceintelligence.IDownloadCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 2:
                    return "onDownloadStarted";
                case 3:
                    return "onDownloadProgress";
                case 4:
                    return "onDownloadFailed";
                case 5:
                    return "onDownloadCompleted";
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
                parcel.enforceInterface(android.app.ondeviceintelligence.IDownloadCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ondeviceintelligence.IDownloadCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 2:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onDownloadStarted(readLong);
                    return true;
                case 3:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onDownloadProgress(readLong2);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDownloadFailed(readInt, readString, persistableBundle);
                    return true;
                case 5:
                    android.os.PersistableBundle persistableBundle2 = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDownloadCompleted(persistableBundle2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ondeviceintelligence.IDownloadCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ondeviceintelligence.IDownloadCallback.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IDownloadCallback
            public void onDownloadStarted(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IDownloadCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IDownloadCallback
            public void onDownloadProgress(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IDownloadCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IDownloadCallback
            public void onDownloadFailed(int i, java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IDownloadCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IDownloadCallback
            public void onDownloadCompleted(android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IDownloadCallback.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
