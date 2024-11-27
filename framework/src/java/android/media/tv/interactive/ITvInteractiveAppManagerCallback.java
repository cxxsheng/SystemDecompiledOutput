package android.media.tv.interactive;

/* loaded from: classes2.dex */
public interface ITvInteractiveAppManagerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppManagerCallback";

    void onInteractiveAppServiceAdded(java.lang.String str) throws android.os.RemoteException;

    void onInteractiveAppServiceRemoved(java.lang.String str) throws android.os.RemoteException;

    void onInteractiveAppServiceUpdated(java.lang.String str) throws android.os.RemoteException;

    void onStateChanged(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void onTvInteractiveAppServiceInfoUpdated(android.media.tv.interactive.TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) throws android.os.RemoteException;

    public static class Default implements android.media.tv.interactive.ITvInteractiveAppManagerCallback {
        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onInteractiveAppServiceAdded(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onInteractiveAppServiceRemoved(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onInteractiveAppServiceUpdated(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onTvInteractiveAppServiceInfoUpdated(android.media.tv.interactive.TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
        public void onStateChanged(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.interactive.ITvInteractiveAppManagerCallback {
        static final int TRANSACTION_onInteractiveAppServiceAdded = 1;
        static final int TRANSACTION_onInteractiveAppServiceRemoved = 2;
        static final int TRANSACTION_onInteractiveAppServiceUpdated = 3;
        static final int TRANSACTION_onStateChanged = 5;
        static final int TRANSACTION_onTvInteractiveAppServiceInfoUpdated = 4;

        public Stub() {
            attachInterface(this, android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR);
        }

        public static android.media.tv.interactive.ITvInteractiveAppManagerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.interactive.ITvInteractiveAppManagerCallback)) {
                return (android.media.tv.interactive.ITvInteractiveAppManagerCallback) queryLocalInterface;
            }
            return new android.media.tv.interactive.ITvInteractiveAppManagerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInteractiveAppServiceAdded";
                case 2:
                    return "onInteractiveAppServiceRemoved";
                case 3:
                    return "onInteractiveAppServiceUpdated";
                case 4:
                    return "onTvInteractiveAppServiceInfoUpdated";
                case 5:
                    return "onStateChanged";
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
                parcel.enforceInterface(android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInteractiveAppServiceAdded(readString);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInteractiveAppServiceRemoved(readString2);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInteractiveAppServiceUpdated(readString3);
                    return true;
                case 4:
                    android.media.tv.interactive.TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo = (android.media.tv.interactive.TvInteractiveAppServiceInfo) parcel.readTypedObject(android.media.tv.interactive.TvInteractiveAppServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvInteractiveAppServiceInfoUpdated(tvInteractiveAppServiceInfo);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStateChanged(readString4, readInt, readInt2, readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.interactive.ITvInteractiveAppManagerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceAdded(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceRemoved(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceUpdated(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onTvInteractiveAppServiceInfoUpdated(android.media.tv.interactive.TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    obtain.writeTypedObject(tvInteractiveAppServiceInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onStateChanged(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
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
