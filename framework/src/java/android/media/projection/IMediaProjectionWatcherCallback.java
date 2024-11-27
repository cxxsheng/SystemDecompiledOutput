package android.media.projection;

/* loaded from: classes2.dex */
public interface IMediaProjectionWatcherCallback extends android.os.IInterface {
    void onRecordingSessionSet(android.media.projection.MediaProjectionInfo mediaProjectionInfo, android.view.ContentRecordingSession contentRecordingSession) throws android.os.RemoteException;

    void onStart(android.media.projection.MediaProjectionInfo mediaProjectionInfo) throws android.os.RemoteException;

    void onStop(android.media.projection.MediaProjectionInfo mediaProjectionInfo) throws android.os.RemoteException;

    public static class Default implements android.media.projection.IMediaProjectionWatcherCallback {
        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStart(android.media.projection.MediaProjectionInfo mediaProjectionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStop(android.media.projection.MediaProjectionInfo mediaProjectionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onRecordingSessionSet(android.media.projection.MediaProjectionInfo mediaProjectionInfo, android.view.ContentRecordingSession contentRecordingSession) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.projection.IMediaProjectionWatcherCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.projection.IMediaProjectionWatcherCallback";
        static final int TRANSACTION_onRecordingSessionSet = 3;
        static final int TRANSACTION_onStart = 1;
        static final int TRANSACTION_onStop = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.projection.IMediaProjectionWatcherCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.projection.IMediaProjectionWatcherCallback)) {
                return (android.media.projection.IMediaProjectionWatcherCallback) queryLocalInterface;
            }
            return new android.media.projection.IMediaProjectionWatcherCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStart";
                case 2:
                    return "onStop";
                case 3:
                    return "onRecordingSessionSet";
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
                    android.media.projection.MediaProjectionInfo mediaProjectionInfo = (android.media.projection.MediaProjectionInfo) parcel.readTypedObject(android.media.projection.MediaProjectionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onStart(mediaProjectionInfo);
                    return true;
                case 2:
                    android.media.projection.MediaProjectionInfo mediaProjectionInfo2 = (android.media.projection.MediaProjectionInfo) parcel.readTypedObject(android.media.projection.MediaProjectionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onStop(mediaProjectionInfo2);
                    return true;
                case 3:
                    android.media.projection.MediaProjectionInfo mediaProjectionInfo3 = (android.media.projection.MediaProjectionInfo) parcel.readTypedObject(android.media.projection.MediaProjectionInfo.CREATOR);
                    android.view.ContentRecordingSession contentRecordingSession = (android.view.ContentRecordingSession) parcel.readTypedObject(android.view.ContentRecordingSession.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRecordingSessionSet(mediaProjectionInfo3, contentRecordingSession);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.projection.IMediaProjectionWatcherCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.projection.IMediaProjectionWatcherCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onStart(android.media.projection.MediaProjectionInfo mediaProjectionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionWatcherCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(mediaProjectionInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onStop(android.media.projection.MediaProjectionInfo mediaProjectionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionWatcherCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(mediaProjectionInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onRecordingSessionSet(android.media.projection.MediaProjectionInfo mediaProjectionInfo, android.view.ContentRecordingSession contentRecordingSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionWatcherCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(mediaProjectionInfo, 0);
                    obtain.writeTypedObject(contentRecordingSession, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
