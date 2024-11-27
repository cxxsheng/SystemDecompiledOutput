package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvInputManagerCallback extends android.os.IInterface {
    void onCurrentTunedInfosUpdated(java.util.List<android.media.tv.TunedInfo> list) throws android.os.RemoteException;

    void onInputAdded(java.lang.String str) throws android.os.RemoteException;

    void onInputRemoved(java.lang.String str) throws android.os.RemoteException;

    void onInputStateChanged(java.lang.String str, int i) throws android.os.RemoteException;

    void onInputUpdated(java.lang.String str) throws android.os.RemoteException;

    void onTvInputInfoUpdated(android.media.tv.TvInputInfo tvInputInfo) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvInputManagerCallback {
        @Override // android.media.tv.ITvInputManagerCallback
        public void onInputAdded(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onInputRemoved(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onInputUpdated(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onInputStateChanged(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onTvInputInfoUpdated(android.media.tv.TvInputInfo tvInputInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onCurrentTunedInfosUpdated(java.util.List<android.media.tv.TunedInfo> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvInputManagerCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvInputManagerCallback";
        static final int TRANSACTION_onCurrentTunedInfosUpdated = 6;
        static final int TRANSACTION_onInputAdded = 1;
        static final int TRANSACTION_onInputRemoved = 2;
        static final int TRANSACTION_onInputStateChanged = 4;
        static final int TRANSACTION_onInputUpdated = 3;
        static final int TRANSACTION_onTvInputInfoUpdated = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvInputManagerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvInputManagerCallback)) {
                return (android.media.tv.ITvInputManagerCallback) queryLocalInterface;
            }
            return new android.media.tv.ITvInputManagerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInputAdded";
                case 2:
                    return "onInputRemoved";
                case 3:
                    return "onInputUpdated";
                case 4:
                    return "onInputStateChanged";
                case 5:
                    return "onTvInputInfoUpdated";
                case 6:
                    return "onCurrentTunedInfosUpdated";
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
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInputAdded(readString);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInputRemoved(readString2);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInputUpdated(readString3);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onInputStateChanged(readString4, readInt);
                    return true;
                case 5:
                    android.media.tv.TvInputInfo tvInputInfo = (android.media.tv.TvInputInfo) parcel.readTypedObject(android.media.tv.TvInputInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvInputInfoUpdated(tvInputInfo);
                    return true;
                case 6:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.tv.TunedInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCurrentTunedInfosUpdated(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvInputManagerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvInputManagerCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputAdded(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManagerCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputRemoved(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManagerCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputUpdated(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManagerCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputStateChanged(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManagerCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onTvInputInfoUpdated(android.media.tv.TvInputInfo tvInputInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManagerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onCurrentTunedInfosUpdated(java.util.List<android.media.tv.TunedInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManagerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
