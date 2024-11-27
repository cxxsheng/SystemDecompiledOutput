package android.telephony.mbms;

/* loaded from: classes3.dex */
public interface IDownloadProgressListener extends android.os.IInterface {
    void onProgressUpdated(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo, int i, int i2, int i3, int i4) throws android.os.RemoteException;

    public static class Default implements android.telephony.mbms.IDownloadProgressListener {
        @Override // android.telephony.mbms.IDownloadProgressListener
        public void onProgressUpdated(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo, int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.mbms.IDownloadProgressListener {
        public static final java.lang.String DESCRIPTOR = "android.telephony.mbms.IDownloadProgressListener";
        static final int TRANSACTION_onProgressUpdated = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.telephony.mbms.IDownloadProgressListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.mbms.IDownloadProgressListener)) {
                return (android.telephony.mbms.IDownloadProgressListener) queryLocalInterface;
            }
            return new android.telephony.mbms.IDownloadProgressListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onProgressUpdated";
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
                    android.telephony.mbms.DownloadRequest downloadRequest = (android.telephony.mbms.DownloadRequest) parcel.readTypedObject(android.telephony.mbms.DownloadRequest.CREATOR);
                    android.telephony.mbms.FileInfo fileInfo = (android.telephony.mbms.FileInfo) parcel.readTypedObject(android.telephony.mbms.FileInfo.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onProgressUpdated(downloadRequest, fileInfo, readInt, readInt2, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.mbms.IDownloadProgressListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.mbms.IDownloadProgressListener.Stub.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.IDownloadProgressListener
            public void onProgressUpdated(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo, int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.IDownloadProgressListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    obtain.writeTypedObject(fileInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
