package android.media.tv.ad;

/* loaded from: classes2.dex */
public interface ITvAdClient extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.ad.ITvAdClient";

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException;

    void onRequestCurrentChannelUri(int i) throws android.os.RemoteException;

    void onRequestCurrentTvInputId(int i) throws android.os.RemoteException;

    void onRequestCurrentVideoBounds(int i) throws android.os.RemoteException;

    void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, int i) throws android.os.RemoteException;

    void onRequestTrackInfoList(int i) throws android.os.RemoteException;

    void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) throws android.os.RemoteException;

    void onSessionReleased(int i) throws android.os.RemoteException;

    void onTvAdSessionData(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ad.ITvAdClient {
        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionReleased(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentVideoBounds(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentChannelUri(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestTrackInfoList(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentTvInputId(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onTvAdSessionData(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ad.ITvAdClient {
        static final int TRANSACTION_onLayoutSurface = 3;
        static final int TRANSACTION_onRequestCurrentChannelUri = 5;
        static final int TRANSACTION_onRequestCurrentTvInputId = 7;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 4;
        static final int TRANSACTION_onRequestSigning = 8;
        static final int TRANSACTION_onRequestTrackInfoList = 6;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onTvAdSessionData = 9;

        public Stub() {
            attachInterface(this, android.media.tv.ad.ITvAdClient.DESCRIPTOR);
        }

        public static android.media.tv.ad.ITvAdClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ad.ITvAdClient)) {
                return (android.media.tv.ad.ITvAdClient) queryLocalInterface;
            }
            return new android.media.tv.ad.ITvAdClient.Stub.Proxy(iBinder);
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
                    return "onSessionReleased";
                case 3:
                    return "onLayoutSurface";
                case 4:
                    return "onRequestCurrentVideoBounds";
                case 5:
                    return "onRequestCurrentChannelUri";
                case 6:
                    return "onRequestTrackInfoList";
                case 7:
                    return "onRequestCurrentTvInputId";
                case 8:
                    return "onRequestSigning";
                case 9:
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
                parcel.enforceInterface(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.view.InputChannel inputChannel = (android.view.InputChannel) parcel.readTypedObject(android.view.InputChannel.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(readString, readStrongBinder, inputChannel, readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionReleased(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt3, readInt4, readInt5, readInt6, readInt7);
                    return true;
                case 4:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentVideoBounds(readInt8);
                    return true;
                case 5:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentChannelUri(readInt9);
                    return true;
                case 6:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTrackInfoList(readInt10);
                    return true;
                case 7:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentTvInputId(readInt11);
                    return true;
                case 8:
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(readString2, readString3, readString4, createByteArray, readInt12);
                    return true;
                case 9:
                    java.lang.String readString5 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTvAdSessionData(readString5, bundle, readInt13);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ad.ITvAdClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ad.ITvAdClient.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onSessionReleased(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentVideoBounds(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentChannelUri(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestTrackInfoList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentTvInputId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onTvAdSessionData(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ad.ITvAdClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
