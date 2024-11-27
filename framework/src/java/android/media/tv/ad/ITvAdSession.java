package android.media.tv.ad;

/* loaded from: classes2.dex */
public interface ITvAdSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.ad.ITvAdSession";

    void createMediaView(android.os.IBinder iBinder, android.graphics.Rect rect) throws android.os.RemoteException;

    void dispatchSurfaceChanged(int i, int i2, int i3) throws android.os.RemoteException;

    void notifyError(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void notifyTvInputSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void notifyTvMessage(int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void relayoutMediaView(android.graphics.Rect rect) throws android.os.RemoteException;

    void release() throws android.os.RemoteException;

    void removeMediaView() throws android.os.RemoteException;

    void resetAdService() throws android.os.RemoteException;

    void sendCurrentChannelUri(android.net.Uri uri) throws android.os.RemoteException;

    void sendCurrentTvInputId(java.lang.String str) throws android.os.RemoteException;

    void sendCurrentVideoBounds(android.graphics.Rect rect) throws android.os.RemoteException;

    void sendSigningResult(java.lang.String str, byte[] bArr) throws android.os.RemoteException;

    void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) throws android.os.RemoteException;

    void setSurface(android.view.Surface surface) throws android.os.RemoteException;

    void startAdService() throws android.os.RemoteException;

    void stopAdService() throws android.os.RemoteException;

    public static class Default implements android.media.tv.ad.ITvAdSession {
        @Override // android.media.tv.ad.ITvAdSession
        public void release() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void startAdService() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void stopAdService() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void resetAdService() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void setSurface(android.view.Surface surface) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void dispatchSurfaceChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendCurrentVideoBounds(android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendCurrentChannelUri(android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendCurrentTvInputId(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendSigningResult(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void notifyError(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void notifyTvMessage(int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void createMediaView(android.os.IBinder iBinder, android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void relayoutMediaView(android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void removeMediaView() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void notifyTvInputSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ad.ITvAdSession {
        static final int TRANSACTION_createMediaView = 14;
        static final int TRANSACTION_dispatchSurfaceChanged = 6;
        static final int TRANSACTION_notifyError = 12;
        static final int TRANSACTION_notifyTvInputSessionData = 17;
        static final int TRANSACTION_notifyTvMessage = 13;
        static final int TRANSACTION_relayoutMediaView = 15;
        static final int TRANSACTION_release = 1;
        static final int TRANSACTION_removeMediaView = 16;
        static final int TRANSACTION_resetAdService = 4;
        static final int TRANSACTION_sendCurrentChannelUri = 8;
        static final int TRANSACTION_sendCurrentTvInputId = 10;
        static final int TRANSACTION_sendCurrentVideoBounds = 7;
        static final int TRANSACTION_sendSigningResult = 11;
        static final int TRANSACTION_sendTrackInfoList = 9;
        static final int TRANSACTION_setSurface = 5;
        static final int TRANSACTION_startAdService = 2;
        static final int TRANSACTION_stopAdService = 3;

        public Stub() {
            attachInterface(this, android.media.tv.ad.ITvAdSession.DESCRIPTOR);
        }

        public static android.media.tv.ad.ITvAdSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ad.ITvAdSession)) {
                return (android.media.tv.ad.ITvAdSession) queryLocalInterface;
            }
            return new android.media.tv.ad.ITvAdSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "release";
                case 2:
                    return "startAdService";
                case 3:
                    return "stopAdService";
                case 4:
                    return "resetAdService";
                case 5:
                    return "setSurface";
                case 6:
                    return "dispatchSurfaceChanged";
                case 7:
                    return "sendCurrentVideoBounds";
                case 8:
                    return "sendCurrentChannelUri";
                case 9:
                    return "sendTrackInfoList";
                case 10:
                    return "sendCurrentTvInputId";
                case 11:
                    return "sendSigningResult";
                case 12:
                    return "notifyError";
                case 13:
                    return "notifyTvMessage";
                case 14:
                    return "createMediaView";
                case 15:
                    return "relayoutMediaView";
                case 16:
                    return "removeMediaView";
                case 17:
                    return "notifyTvInputSessionData";
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
                parcel.enforceInterface(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    release();
                    return true;
                case 2:
                    startAdService();
                    return true;
                case 3:
                    stopAdService();
                    return true;
                case 4:
                    resetAdService();
                    return true;
                case 5:
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSurface(surface);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(readInt, readInt2, readInt3);
                    return true;
                case 7:
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCurrentVideoBounds(rect);
                    return true;
                case 8:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelUri(uri);
                    return true;
                case 9:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.tv.TvTrackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendTrackInfoList(createTypedArrayList);
                    return true;
                case 10:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendCurrentTvInputId(readString);
                    return true;
                case 11:
                    java.lang.String readString2 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSigningResult(readString2, createByteArray);
                    return true;
                case 12:
                    java.lang.String readString3 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyError(readString3, bundle);
                    return true;
                case 13:
                    int readInt4 = parcel.readInt();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(readInt4, bundle2);
                    return true;
                case 14:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    createMediaView(readStrongBinder, rect2);
                    return true;
                case 15:
                    android.graphics.Rect rect3 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    relayoutMediaView(rect3);
                    return true;
                case 16:
                    removeMediaView();
                    return true;
                case 17:
                    java.lang.String readString4 = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTvInputSessionData(readString4, bundle3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ad.ITvAdSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ad.ITvAdSession.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void release() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void startAdService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void stopAdService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void resetAdService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void setSurface(android.view.Surface surface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void dispatchSurfaceChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendCurrentVideoBounds(android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendCurrentChannelUri(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendCurrentTvInputId(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendSigningResult(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void notifyError(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void notifyTvMessage(int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void createMediaView(android.os.IBinder iBinder, android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void relayoutMediaView(android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void removeMediaView() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void notifyTvInputSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }
    }
}
