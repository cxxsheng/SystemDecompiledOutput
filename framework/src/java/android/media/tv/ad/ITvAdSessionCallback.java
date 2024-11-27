package android.media.tv.ad;

/* loaded from: classes2.dex */
public interface ITvAdSessionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.ad.ITvAdSessionCallback";

    void onLayoutSurface(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void onRequestCurrentChannelUri() throws android.os.RemoteException;

    void onRequestCurrentTvInputId() throws android.os.RemoteException;

    void onRequestCurrentVideoBounds() throws android.os.RemoteException;

    void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) throws android.os.RemoteException;

    void onRequestTrackInfoList() throws android.os.RemoteException;

    void onSessionCreated(android.media.tv.ad.ITvAdSession iTvAdSession) throws android.os.RemoteException;

    void onTvAdSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ad.ITvAdSessionCallback {
        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onSessionCreated(android.media.tv.ad.ITvAdSession iTvAdSession) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onLayoutSurface(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestCurrentVideoBounds() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestCurrentChannelUri() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestTrackInfoList() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestCurrentTvInputId() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onTvAdSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ad.ITvAdSessionCallback {
        static final int TRANSACTION_onLayoutSurface = 2;
        static final int TRANSACTION_onRequestCurrentChannelUri = 4;
        static final int TRANSACTION_onRequestCurrentTvInputId = 6;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 3;
        static final int TRANSACTION_onRequestSigning = 7;
        static final int TRANSACTION_onRequestTrackInfoList = 5;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onTvAdSessionData = 8;

        public Stub() {
            attachInterface(this, android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
        }

        public static android.media.tv.ad.ITvAdSessionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ad.ITvAdSessionCallback)) {
                return (android.media.tv.ad.ITvAdSessionCallback) queryLocalInterface;
            }
            return new android.media.tv.ad.ITvAdSessionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onLayoutSurface";
                case 3:
                    return "onRequestCurrentVideoBounds";
                case 4:
                    return "onRequestCurrentChannelUri";
                case 5:
                    return "onRequestTrackInfoList";
                case 6:
                    return "onRequestCurrentTvInputId";
                case 7:
                    return "onRequestSigning";
                case 8:
                    return "onTvAdSessionData";
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
                parcel.enforceInterface(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.tv.ad.ITvAdSession asInterface = android.media.tv.ad.ITvAdSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSessionCreated(asInterface);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt, readInt2, readInt3, readInt4);
                    return true;
                case 3:
                    onRequestCurrentVideoBounds();
                    return true;
                case 4:
                    onRequestCurrentChannelUri();
                    return true;
                case 5:
                    onRequestTrackInfoList();
                    return true;
                case 6:
                    onRequestCurrentTvInputId();
                    return true;
                case 7:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(readString, readString2, readString3, createByteArray);
                    return true;
                case 8:
                    java.lang.String readString4 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvAdSessionData(readString4, bundle);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ad.ITvAdSessionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onSessionCreated(android.media.tv.ad.ITvAdSession iTvAdSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdSession);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onLayoutSurface(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentVideoBounds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentChannelUri() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestTrackInfoList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentTvInputId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onTvAdSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
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
