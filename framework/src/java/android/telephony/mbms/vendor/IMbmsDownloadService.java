package android.telephony.mbms.vendor;

/* loaded from: classes3.dex */
public interface IMbmsDownloadService extends android.os.IInterface {
    int addProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadProgressListener iDownloadProgressListener) throws android.os.RemoteException;

    int addServiceAnnouncement(int i, byte[] bArr) throws android.os.RemoteException;

    int addStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadStatusListener iDownloadStatusListener) throws android.os.RemoteException;

    int cancelDownload(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException;

    void dispose(int i) throws android.os.RemoteException;

    int download(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException;

    int initialize(int i, android.telephony.mbms.IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws android.os.RemoteException;

    java.util.List<android.telephony.mbms.DownloadRequest> listPendingDownloads(int i) throws android.os.RemoteException;

    int removeProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadProgressListener iDownloadProgressListener) throws android.os.RemoteException;

    int removeStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadStatusListener iDownloadStatusListener) throws android.os.RemoteException;

    int requestDownloadState(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo) throws android.os.RemoteException;

    int requestUpdateFileServices(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    int resetDownloadKnowledge(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException;

    int setTempFileRootDirectory(int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.telephony.mbms.vendor.IMbmsDownloadService {
        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int initialize(int i, android.telephony.mbms.IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int requestUpdateFileServices(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int setTempFileRootDirectory(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int addServiceAnnouncement(int i, byte[] bArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int download(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int addStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadStatusListener iDownloadStatusListener) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int removeStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadStatusListener iDownloadStatusListener) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int addProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadProgressListener iDownloadProgressListener) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int removeProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadProgressListener iDownloadProgressListener) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public java.util.List<android.telephony.mbms.DownloadRequest> listPendingDownloads(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int cancelDownload(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int requestDownloadState(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int resetDownloadKnowledge(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public void dispose(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.mbms.vendor.IMbmsDownloadService {
        public static final java.lang.String DESCRIPTOR = "android.telephony.mbms.vendor.IMbmsDownloadService";
        static final int TRANSACTION_addProgressListener = 8;
        static final int TRANSACTION_addServiceAnnouncement = 4;
        static final int TRANSACTION_addStatusListener = 6;
        static final int TRANSACTION_cancelDownload = 11;
        static final int TRANSACTION_dispose = 14;
        static final int TRANSACTION_download = 5;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_listPendingDownloads = 10;
        static final int TRANSACTION_removeProgressListener = 9;
        static final int TRANSACTION_removeStatusListener = 7;
        static final int TRANSACTION_requestDownloadState = 12;
        static final int TRANSACTION_requestUpdateFileServices = 2;
        static final int TRANSACTION_resetDownloadKnowledge = 13;
        static final int TRANSACTION_setTempFileRootDirectory = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.telephony.mbms.vendor.IMbmsDownloadService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.mbms.vendor.IMbmsDownloadService)) {
                return (android.telephony.mbms.vendor.IMbmsDownloadService) queryLocalInterface;
            }
            return new android.telephony.mbms.vendor.IMbmsDownloadService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.content.ContentResolver.SYNC_EXTRAS_INITIALIZE;
                case 2:
                    return "requestUpdateFileServices";
                case 3:
                    return "setTempFileRootDirectory";
                case 4:
                    return "addServiceAnnouncement";
                case 5:
                    return "download";
                case 6:
                    return "addStatusListener";
                case 7:
                    return "removeStatusListener";
                case 8:
                    return "addProgressListener";
                case 9:
                    return "removeProgressListener";
                case 10:
                    return "listPendingDownloads";
                case 11:
                    return "cancelDownload";
                case 12:
                    return "requestDownloadState";
                case 13:
                    return "resetDownloadKnowledge";
                case 14:
                    return "dispose";
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
                    int readInt = parcel.readInt();
                    android.telephony.mbms.IMbmsDownloadSessionCallback asInterface = android.telephony.mbms.IMbmsDownloadSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int initialize = initialize(readInt, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialize);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int requestUpdateFileServices = requestUpdateFileServices(readInt2, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestUpdateFileServices);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int tempFileRootDirectory = setTempFileRootDirectory(readInt3, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(tempFileRootDirectory);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int addServiceAnnouncement = addServiceAnnouncement(readInt4, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(addServiceAnnouncement);
                    return true;
                case 5:
                    android.telephony.mbms.DownloadRequest downloadRequest = (android.telephony.mbms.DownloadRequest) parcel.readTypedObject(android.telephony.mbms.DownloadRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    int download = download(downloadRequest);
                    parcel2.writeNoException();
                    parcel2.writeInt(download);
                    return true;
                case 6:
                    android.telephony.mbms.DownloadRequest downloadRequest2 = (android.telephony.mbms.DownloadRequest) parcel.readTypedObject(android.telephony.mbms.DownloadRequest.CREATOR);
                    android.telephony.mbms.IDownloadStatusListener asInterface2 = android.telephony.mbms.IDownloadStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int addStatusListener = addStatusListener(downloadRequest2, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(addStatusListener);
                    return true;
                case 7:
                    android.telephony.mbms.DownloadRequest downloadRequest3 = (android.telephony.mbms.DownloadRequest) parcel.readTypedObject(android.telephony.mbms.DownloadRequest.CREATOR);
                    android.telephony.mbms.IDownloadStatusListener asInterface3 = android.telephony.mbms.IDownloadStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int removeStatusListener = removeStatusListener(downloadRequest3, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeStatusListener);
                    return true;
                case 8:
                    android.telephony.mbms.DownloadRequest downloadRequest4 = (android.telephony.mbms.DownloadRequest) parcel.readTypedObject(android.telephony.mbms.DownloadRequest.CREATOR);
                    android.telephony.mbms.IDownloadProgressListener asInterface4 = android.telephony.mbms.IDownloadProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int addProgressListener = addProgressListener(downloadRequest4, asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(addProgressListener);
                    return true;
                case 9:
                    android.telephony.mbms.DownloadRequest downloadRequest5 = (android.telephony.mbms.DownloadRequest) parcel.readTypedObject(android.telephony.mbms.DownloadRequest.CREATOR);
                    android.telephony.mbms.IDownloadProgressListener asInterface5 = android.telephony.mbms.IDownloadProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int removeProgressListener = removeProgressListener(downloadRequest5, asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeProgressListener);
                    return true;
                case 10:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.mbms.DownloadRequest> listPendingDownloads = listPendingDownloads(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listPendingDownloads, 1);
                    return true;
                case 11:
                    android.telephony.mbms.DownloadRequest downloadRequest6 = (android.telephony.mbms.DownloadRequest) parcel.readTypedObject(android.telephony.mbms.DownloadRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    int cancelDownload = cancelDownload(downloadRequest6);
                    parcel2.writeNoException();
                    parcel2.writeInt(cancelDownload);
                    return true;
                case 12:
                    android.telephony.mbms.DownloadRequest downloadRequest7 = (android.telephony.mbms.DownloadRequest) parcel.readTypedObject(android.telephony.mbms.DownloadRequest.CREATOR);
                    android.telephony.mbms.FileInfo fileInfo = (android.telephony.mbms.FileInfo) parcel.readTypedObject(android.telephony.mbms.FileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int requestDownloadState = requestDownloadState(downloadRequest7, fileInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestDownloadState);
                    return true;
                case 13:
                    android.telephony.mbms.DownloadRequest downloadRequest8 = (android.telephony.mbms.DownloadRequest) parcel.readTypedObject(android.telephony.mbms.DownloadRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    int resetDownloadKnowledge = resetDownloadKnowledge(downloadRequest8);
                    parcel2.writeNoException();
                    parcel2.writeInt(resetDownloadKnowledge);
                    return true;
                case 14:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispose(readInt6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.mbms.vendor.IMbmsDownloadService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int initialize(int i, android.telephony.mbms.IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iMbmsDownloadSessionCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int requestUpdateFileServices(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int setTempFileRootDirectory(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int addServiceAnnouncement(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int download(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int addStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadStatusListener iDownloadStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    obtain.writeStrongInterface(iDownloadStatusListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int removeStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadStatusListener iDownloadStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    obtain.writeStrongInterface(iDownloadStatusListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int addProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadProgressListener iDownloadProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    obtain.writeStrongInterface(iDownloadProgressListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int removeProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadProgressListener iDownloadProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    obtain.writeStrongInterface(iDownloadProgressListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public java.util.List<android.telephony.mbms.DownloadRequest> listPendingDownloads(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.mbms.DownloadRequest.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int cancelDownload(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int requestDownloadState(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    obtain.writeTypedObject(fileInfo, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int resetDownloadKnowledge(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public void dispose(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsDownloadService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
