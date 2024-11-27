package android.service.contentcapture;

/* loaded from: classes3.dex */
public interface IContentCaptureService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.contentcapture.IContentCaptureService";

    void onActivityEvent(android.service.contentcapture.ActivityEvent activityEvent) throws android.os.RemoteException;

    void onActivitySnapshot(int i, android.service.contentcapture.SnapshotData snapshotData) throws android.os.RemoteException;

    void onConnected(android.os.IBinder iBinder, boolean z, boolean z2) throws android.os.RemoteException;

    void onDataRemovalRequest(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) throws android.os.RemoteException;

    void onDataShared(android.view.contentcapture.DataShareRequest dataShareRequest, android.service.contentcapture.IDataShareCallback iDataShareCallback) throws android.os.RemoteException;

    void onDisconnected() throws android.os.RemoteException;

    void onSessionFinished(int i) throws android.os.RemoteException;

    void onSessionStarted(android.view.contentcapture.ContentCaptureContext contentCaptureContext, int i, int i2, com.android.internal.os.IResultReceiver iResultReceiver, int i3) throws android.os.RemoteException;

    public static class Default implements android.service.contentcapture.IContentCaptureService {
        @Override // android.service.contentcapture.IContentCaptureService
        public void onConnected(android.os.IBinder iBinder, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDisconnected() throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionStarted(android.view.contentcapture.ContentCaptureContext contentCaptureContext, int i, int i2, com.android.internal.os.IResultReceiver iResultReceiver, int i3) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionFinished(int i) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivitySnapshot(int i, android.service.contentcapture.SnapshotData snapshotData) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataRemovalRequest(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataShared(android.view.contentcapture.DataShareRequest dataShareRequest, android.service.contentcapture.IDataShareCallback iDataShareCallback) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivityEvent(android.service.contentcapture.ActivityEvent activityEvent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.contentcapture.IContentCaptureService {
        static final int TRANSACTION_onActivityEvent = 8;
        static final int TRANSACTION_onActivitySnapshot = 5;
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDataRemovalRequest = 6;
        static final int TRANSACTION_onDataShared = 7;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onSessionFinished = 4;
        static final int TRANSACTION_onSessionStarted = 3;

        public Stub() {
            attachInterface(this, android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
        }

        public static android.service.contentcapture.IContentCaptureService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.contentcapture.IContentCaptureService)) {
                return (android.service.contentcapture.IContentCaptureService) queryLocalInterface;
            }
            return new android.service.contentcapture.IContentCaptureService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnected";
                case 2:
                    return "onDisconnected";
                case 3:
                    return "onSessionStarted";
                case 4:
                    return "onSessionFinished";
                case 5:
                    return "onActivitySnapshot";
                case 6:
                    return "onDataRemovalRequest";
                case 7:
                    return "onDataShared";
                case 8:
                    return "onActivityEvent";
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
                parcel.enforceInterface(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConnected(readStrongBinder, readBoolean, readBoolean2);
                    return true;
                case 2:
                    onDisconnected();
                    return true;
                case 3:
                    android.view.contentcapture.ContentCaptureContext contentCaptureContext = (android.view.contentcapture.ContentCaptureContext) parcel.readTypedObject(android.view.contentcapture.ContentCaptureContext.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    com.android.internal.os.IResultReceiver asInterface = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionStarted(contentCaptureContext, readInt, readInt2, asInterface, readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionFinished(readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.service.contentcapture.SnapshotData snapshotData = (android.service.contentcapture.SnapshotData) parcel.readTypedObject(android.service.contentcapture.SnapshotData.CREATOR);
                    parcel.enforceNoDataAvail();
                    onActivitySnapshot(readInt5, snapshotData);
                    return true;
                case 6:
                    android.view.contentcapture.DataRemovalRequest dataRemovalRequest = (android.view.contentcapture.DataRemovalRequest) parcel.readTypedObject(android.view.contentcapture.DataRemovalRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDataRemovalRequest(dataRemovalRequest);
                    return true;
                case 7:
                    android.view.contentcapture.DataShareRequest dataShareRequest = (android.view.contentcapture.DataShareRequest) parcel.readTypedObject(android.view.contentcapture.DataShareRequest.CREATOR);
                    android.service.contentcapture.IDataShareCallback asInterface2 = android.service.contentcapture.IDataShareCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onDataShared(dataShareRequest, asInterface2);
                    return true;
                case 8:
                    android.service.contentcapture.ActivityEvent activityEvent = (android.service.contentcapture.ActivityEvent) parcel.readTypedObject(android.service.contentcapture.ActivityEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onActivityEvent(activityEvent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.contentcapture.IContentCaptureService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.contentcapture.IContentCaptureService.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onConnected(android.os.IBinder iBinder, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onDisconnected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onSessionStarted(android.view.contentcapture.ContentCaptureContext contentCaptureContext, int i, int i2, com.android.internal.os.IResultReceiver iResultReceiver, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
                    obtain.writeTypedObject(contentCaptureContext, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onSessionFinished(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onActivitySnapshot(int i, android.service.contentcapture.SnapshotData snapshotData) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(snapshotData, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onDataRemovalRequest(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
                    obtain.writeTypedObject(dataRemovalRequest, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onDataShared(android.view.contentcapture.DataShareRequest dataShareRequest, android.service.contentcapture.IDataShareCallback iDataShareCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
                    obtain.writeTypedObject(dataShareRequest, 0);
                    obtain.writeStrongInterface(iDataShareCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onActivityEvent(android.service.contentcapture.ActivityEvent activityEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentCaptureService.DESCRIPTOR);
                    obtain.writeTypedObject(activityEvent, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
