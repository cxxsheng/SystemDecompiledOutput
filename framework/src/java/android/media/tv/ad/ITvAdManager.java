package android.media.tv.ad;

/* loaded from: classes2.dex */
public interface ITvAdManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.ad.ITvAdManager";

    void createMediaView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) throws android.os.RemoteException;

    void createSession(android.media.tv.ad.ITvAdClient iTvAdClient, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) throws android.os.RemoteException;

    java.util.List<android.media.tv.ad.TvAdServiceInfo> getTvAdServiceList(int i) throws android.os.RemoteException;

    void notifyError(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void notifyTvInputSessionData(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException;

    void registerCallback(android.media.tv.ad.ITvAdManagerCallback iTvAdManagerCallback, int i) throws android.os.RemoteException;

    void relayoutMediaView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException;

    void releaseSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void removeMediaView(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void resetAdService(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void sendCurrentChannelUri(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException;

    void sendCurrentTvInputId(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void sendCurrentVideoBounds(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException;

    void sendSigningResult(android.os.IBinder iBinder, java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException;

    void sendTrackInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException;

    void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) throws android.os.RemoteException;

    void startAdService(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void stopAdService(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void unregisterCallback(android.media.tv.ad.ITvAdManagerCallback iTvAdManagerCallback, int i) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ad.ITvAdManager {
        @Override // android.media.tv.ad.ITvAdManager
        public java.util.List<android.media.tv.ad.TvAdServiceInfo> getTvAdServiceList(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void createSession(android.media.tv.ad.ITvAdClient iTvAdClient, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void releaseSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void startAdService(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void stopAdService(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void resetAdService(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendCurrentVideoBounds(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendCurrentChannelUri(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendTrackInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendCurrentTvInputId(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendSigningResult(android.os.IBinder iBinder, java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void notifyError(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void registerCallback(android.media.tv.ad.ITvAdManagerCallback iTvAdManagerCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void unregisterCallback(android.media.tv.ad.ITvAdManagerCallback iTvAdManagerCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void createMediaView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void relayoutMediaView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void removeMediaView(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void notifyTvInputSessionData(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ad.ITvAdManager {
        static final int TRANSACTION_createMediaView = 19;
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_dispatchSurfaceChanged = 9;
        static final int TRANSACTION_getTvAdServiceList = 1;
        static final int TRANSACTION_notifyError = 15;
        static final int TRANSACTION_notifyTvInputSessionData = 22;
        static final int TRANSACTION_notifyTvMessage = 16;
        static final int TRANSACTION_registerCallback = 17;
        static final int TRANSACTION_relayoutMediaView = 20;
        static final int TRANSACTION_releaseSession = 4;
        static final int TRANSACTION_removeMediaView = 21;
        static final int TRANSACTION_resetAdService = 7;
        static final int TRANSACTION_sendAppLinkCommand = 2;
        static final int TRANSACTION_sendCurrentChannelUri = 11;
        static final int TRANSACTION_sendCurrentTvInputId = 13;
        static final int TRANSACTION_sendCurrentVideoBounds = 10;
        static final int TRANSACTION_sendSigningResult = 14;
        static final int TRANSACTION_sendTrackInfoList = 12;
        static final int TRANSACTION_setSurface = 8;
        static final int TRANSACTION_startAdService = 5;
        static final int TRANSACTION_stopAdService = 6;
        static final int TRANSACTION_unregisterCallback = 18;

        public Stub() {
            attachInterface(this, android.media.tv.ad.ITvAdManager.DESCRIPTOR);
        }

        public static android.media.tv.ad.ITvAdManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ad.ITvAdManager)) {
                return (android.media.tv.ad.ITvAdManager) queryLocalInterface;
            }
            return new android.media.tv.ad.ITvAdManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTvAdServiceList";
                case 2:
                    return "sendAppLinkCommand";
                case 3:
                    return "createSession";
                case 4:
                    return "releaseSession";
                case 5:
                    return "startAdService";
                case 6:
                    return "stopAdService";
                case 7:
                    return "resetAdService";
                case 8:
                    return "setSurface";
                case 9:
                    return "dispatchSurfaceChanged";
                case 10:
                    return "sendCurrentVideoBounds";
                case 11:
                    return "sendCurrentChannelUri";
                case 12:
                    return "sendTrackInfoList";
                case 13:
                    return "sendCurrentTvInputId";
                case 14:
                    return "sendSigningResult";
                case 15:
                    return "notifyError";
                case 16:
                    return "notifyTvMessage";
                case 17:
                    return "registerCallback";
                case 18:
                    return "unregisterCallback";
                case 19:
                    return "createMediaView";
                case 20:
                    return "relayoutMediaView";
                case 21:
                    return "removeMediaView";
                case 22:
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
                parcel.enforceInterface(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.tv.ad.TvAdServiceInfo> tvAdServiceList = getTvAdServiceList(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvAdServiceList, 1);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAppLinkCommand(readString, bundle, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.media.tv.ad.ITvAdClient asInterface = android.media.tv.ad.ITvAdClient.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(asInterface, readString2, readString3, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSession(readStrongBinder, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startAdService(readStrongBinder2, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopAdService(readStrongBinder3, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetAdService(readStrongBinder4, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSurface(readStrongBinder5, surface, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(readStrongBinder6, readInt10, readInt11, readInt12, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentVideoBounds(readStrongBinder7, rect, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelUri(readStrongBinder8, uri, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.tv.TvTrackInfo.CREATOR);
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTrackInfoList(readStrongBinder9, createTypedArrayList, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentTvInputId(readStrongBinder10, readString4, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    java.lang.String readString5 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSigningResult(readStrongBinder11, readString5, createByteArray, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    java.lang.String readString6 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyError(readStrongBinder12, readString6, bundle2, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt20 = parcel.readInt();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(readStrongBinder13, readInt20, bundle3, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.media.tv.ad.ITvAdManagerCallback asInterface2 = android.media.tv.ad.ITvAdManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface2, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.media.tv.ad.ITvAdManagerCallback asInterface3 = android.media.tv.ad.ITvAdManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface3, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createMediaView(readStrongBinder14, readStrongBinder15, rect2, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    android.graphics.Rect rect3 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutMediaView(readStrongBinder16, rect3, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMediaView(readStrongBinder17, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    java.lang.String readString7 = parcel.readString();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvInputSessionData(readStrongBinder18, readString7, bundle4, readInt27);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ad.ITvAdManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ad.ITvAdManager.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdManager
            public java.util.List<android.media.tv.ad.TvAdServiceInfo> getTvAdServiceList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.tv.ad.TvAdServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void createSession(android.media.tv.ad.ITvAdClient iTvAdClient, java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdClient);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void releaseSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void startAdService(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void stopAdService(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void resetAdService(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentVideoBounds(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentChannelUri(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendTrackInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentTvInputId(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendSigningResult(android.os.IBinder iBinder, java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyError(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void registerCallback(android.media.tv.ad.ITvAdManagerCallback iTvAdManagerCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void unregisterCallback(android.media.tv.ad.ITvAdManagerCallback iTvAdManagerCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void createMediaView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void relayoutMediaView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void removeMediaView(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyTvInputSessionData(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }
    }
}
