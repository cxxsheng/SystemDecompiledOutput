package android.net.ipmemorystore;

/* loaded from: classes.dex */
public interface IOnBlobRetrievedListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$ipmemorystore$IOnBlobRetrievedListener".replace('$', '.');
    public static final java.lang.String HASH = "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
    public static final int VERSION = 10;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onBlobRetrieved(android.net.ipmemorystore.StatusParcelable statusParcelable, java.lang.String str, java.lang.String str2, android.net.ipmemorystore.Blob blob) throws android.os.RemoteException;

    public static class Default implements android.net.ipmemorystore.IOnBlobRetrievedListener {
        @Override // android.net.ipmemorystore.IOnBlobRetrievedListener
        public void onBlobRetrieved(android.net.ipmemorystore.StatusParcelable statusParcelable, java.lang.String str, java.lang.String str2, android.net.ipmemorystore.Blob blob) throws android.os.RemoteException {
        }

        @Override // android.net.ipmemorystore.IOnBlobRetrievedListener
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.ipmemorystore.IOnBlobRetrievedListener
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.ipmemorystore.IOnBlobRetrievedListener {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onBlobRetrieved = 1;

        public Stub() {
            attachInterface(this, android.net.ipmemorystore.IOnBlobRetrievedListener.DESCRIPTOR);
        }

        public static android.net.ipmemorystore.IOnBlobRetrievedListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.ipmemorystore.IOnBlobRetrievedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.ipmemorystore.IOnBlobRetrievedListener)) {
                return (android.net.ipmemorystore.IOnBlobRetrievedListener) queryLocalInterface;
            }
            return new android.net.ipmemorystore.IOnBlobRetrievedListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.ipmemorystore.IOnBlobRetrievedListener.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    onBlobRetrieved((android.net.ipmemorystore.StatusParcelable) parcel.readTypedObject(android.net.ipmemorystore.StatusParcelable.CREATOR), parcel.readString(), parcel.readString(), (android.net.ipmemorystore.Blob) parcel.readTypedObject(android.net.ipmemorystore.Blob.CREATOR));
                    break;
            }
            return true;
        }

        private static class Proxy implements android.net.ipmemorystore.IOnBlobRetrievedListener {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.ipmemorystore.IOnBlobRetrievedListener.DESCRIPTOR;
            }

            @Override // android.net.ipmemorystore.IOnBlobRetrievedListener
            public void onBlobRetrieved(android.net.ipmemorystore.StatusParcelable statusParcelable, java.lang.String str, java.lang.String str2, android.net.ipmemorystore.Blob blob) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.ipmemorystore.IOnBlobRetrievedListener.DESCRIPTOR);
                    obtain.writeTypedObject(statusParcelable, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(blob, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onBlobRetrieved is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ipmemorystore.IOnBlobRetrievedListener
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.ipmemorystore.IOnBlobRetrievedListener.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.ipmemorystore.IOnBlobRetrievedListener
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.ipmemorystore.IOnBlobRetrievedListener.DESCRIPTOR);
                            this.mRemote.transact(android.net.ipmemorystore.IOnBlobRetrievedListener.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (java.lang.Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}