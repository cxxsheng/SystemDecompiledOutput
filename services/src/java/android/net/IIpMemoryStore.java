package android.net;

/* loaded from: classes.dex */
public interface IIpMemoryStore extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$IIpMemoryStore".replace('$', '.');
    public static final java.lang.String HASH = "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
    public static final int VERSION = 10;

    void delete(java.lang.String str, boolean z, android.net.ipmemorystore.IOnStatusAndCountListener iOnStatusAndCountListener) throws android.os.RemoteException;

    void deleteCluster(java.lang.String str, boolean z, android.net.ipmemorystore.IOnStatusAndCountListener iOnStatusAndCountListener) throws android.os.RemoteException;

    void factoryReset() throws android.os.RemoteException;

    void findL2Key(android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable, android.net.ipmemorystore.IOnL2KeyResponseListener iOnL2KeyResponseListener) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void isSameNetwork(java.lang.String str, java.lang.String str2, android.net.ipmemorystore.IOnSameL3NetworkResponseListener iOnSameL3NetworkResponseListener) throws android.os.RemoteException;

    void retrieveBlob(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.ipmemorystore.IOnBlobRetrievedListener iOnBlobRetrievedListener) throws android.os.RemoteException;

    void retrieveNetworkAttributes(java.lang.String str, android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener iOnNetworkAttributesRetrievedListener) throws android.os.RemoteException;

    void storeBlob(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.ipmemorystore.Blob blob, android.net.ipmemorystore.IOnStatusListener iOnStatusListener) throws android.os.RemoteException;

    void storeNetworkAttributes(java.lang.String str, android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable, android.net.ipmemorystore.IOnStatusListener iOnStatusListener) throws android.os.RemoteException;

    public static class Default implements android.net.IIpMemoryStore {
        @Override // android.net.IIpMemoryStore
        public void storeNetworkAttributes(java.lang.String str, android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable, android.net.ipmemorystore.IOnStatusListener iOnStatusListener) throws android.os.RemoteException {
        }

        @Override // android.net.IIpMemoryStore
        public void storeBlob(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.ipmemorystore.Blob blob, android.net.ipmemorystore.IOnStatusListener iOnStatusListener) throws android.os.RemoteException {
        }

        @Override // android.net.IIpMemoryStore
        public void findL2Key(android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable, android.net.ipmemorystore.IOnL2KeyResponseListener iOnL2KeyResponseListener) throws android.os.RemoteException {
        }

        @Override // android.net.IIpMemoryStore
        public void isSameNetwork(java.lang.String str, java.lang.String str2, android.net.ipmemorystore.IOnSameL3NetworkResponseListener iOnSameL3NetworkResponseListener) throws android.os.RemoteException {
        }

        @Override // android.net.IIpMemoryStore
        public void retrieveNetworkAttributes(java.lang.String str, android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener iOnNetworkAttributesRetrievedListener) throws android.os.RemoteException {
        }

        @Override // android.net.IIpMemoryStore
        public void retrieveBlob(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.ipmemorystore.IOnBlobRetrievedListener iOnBlobRetrievedListener) throws android.os.RemoteException {
        }

        @Override // android.net.IIpMemoryStore
        public void factoryReset() throws android.os.RemoteException {
        }

        @Override // android.net.IIpMemoryStore
        public void delete(java.lang.String str, boolean z, android.net.ipmemorystore.IOnStatusAndCountListener iOnStatusAndCountListener) throws android.os.RemoteException {
        }

        @Override // android.net.IIpMemoryStore
        public void deleteCluster(java.lang.String str, boolean z, android.net.ipmemorystore.IOnStatusAndCountListener iOnStatusAndCountListener) throws android.os.RemoteException {
        }

        @Override // android.net.IIpMemoryStore
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.IIpMemoryStore
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.IIpMemoryStore {
        static final int TRANSACTION_delete = 8;
        static final int TRANSACTION_deleteCluster = 9;
        static final int TRANSACTION_factoryReset = 7;
        static final int TRANSACTION_findL2Key = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_isSameNetwork = 4;
        static final int TRANSACTION_retrieveBlob = 6;
        static final int TRANSACTION_retrieveNetworkAttributes = 5;
        static final int TRANSACTION_storeBlob = 2;
        static final int TRANSACTION_storeNetworkAttributes = 1;

        public Stub() {
            attachInterface(this, android.net.IIpMemoryStore.DESCRIPTOR);
        }

        public static android.net.IIpMemoryStore asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.IIpMemoryStore.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.IIpMemoryStore)) {
                return (android.net.IIpMemoryStore) queryLocalInterface;
            }
            return new android.net.IIpMemoryStore.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.IIpMemoryStore.DESCRIPTOR;
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
                    storeNetworkAttributes(parcel.readString(), (android.net.ipmemorystore.NetworkAttributesParcelable) parcel.readTypedObject(android.net.ipmemorystore.NetworkAttributesParcelable.CREATOR), android.net.ipmemorystore.IOnStatusListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 2:
                    storeBlob(parcel.readString(), parcel.readString(), parcel.readString(), (android.net.ipmemorystore.Blob) parcel.readTypedObject(android.net.ipmemorystore.Blob.CREATOR), android.net.ipmemorystore.IOnStatusListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 3:
                    findL2Key((android.net.ipmemorystore.NetworkAttributesParcelable) parcel.readTypedObject(android.net.ipmemorystore.NetworkAttributesParcelable.CREATOR), android.net.ipmemorystore.IOnL2KeyResponseListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 4:
                    isSameNetwork(parcel.readString(), parcel.readString(), android.net.ipmemorystore.IOnSameL3NetworkResponseListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 5:
                    retrieveNetworkAttributes(parcel.readString(), android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 6:
                    retrieveBlob(parcel.readString(), parcel.readString(), parcel.readString(), android.net.ipmemorystore.IOnBlobRetrievedListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 7:
                    factoryReset();
                    return true;
                case 8:
                    delete(parcel.readString(), parcel.readBoolean(), android.net.ipmemorystore.IOnStatusAndCountListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 9:
                    deleteCluster(parcel.readString(), parcel.readBoolean(), android.net.ipmemorystore.IOnStatusAndCountListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.IIpMemoryStore {
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
                return android.net.IIpMemoryStore.DESCRIPTOR;
            }

            @Override // android.net.IIpMemoryStore
            public void storeNetworkAttributes(java.lang.String str, android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable, android.net.ipmemorystore.IOnStatusListener iOnStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(networkAttributesParcelable, 0);
                    obtain.writeStrongInterface(iOnStatusListener);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method storeNetworkAttributes is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpMemoryStore
            public void storeBlob(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.ipmemorystore.Blob blob, android.net.ipmemorystore.IOnStatusListener iOnStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(blob, 0);
                    obtain.writeStrongInterface(iOnStatusListener);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method storeBlob is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpMemoryStore
            public void findL2Key(android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable, android.net.ipmemorystore.IOnL2KeyResponseListener iOnL2KeyResponseListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                    obtain.writeTypedObject(networkAttributesParcelable, 0);
                    obtain.writeStrongInterface(iOnL2KeyResponseListener);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method findL2Key is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpMemoryStore
            public void isSameNetwork(java.lang.String str, java.lang.String str2, android.net.ipmemorystore.IOnSameL3NetworkResponseListener iOnSameL3NetworkResponseListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iOnSameL3NetworkResponseListener);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isSameNetwork is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpMemoryStore
            public void retrieveNetworkAttributes(java.lang.String str, android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener iOnNetworkAttributesRetrievedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iOnNetworkAttributesRetrievedListener);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method retrieveNetworkAttributes is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpMemoryStore
            public void retrieveBlob(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.ipmemorystore.IOnBlobRetrievedListener iOnBlobRetrievedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iOnBlobRetrievedListener);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method retrieveBlob is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpMemoryStore
            public void factoryReset() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method factoryReset is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpMemoryStore
            public void delete(java.lang.String str, boolean z, android.net.ipmemorystore.IOnStatusAndCountListener iOnStatusAndCountListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iOnStatusAndCountListener);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method delete is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpMemoryStore
            public void deleteCluster(java.lang.String str, boolean z, android.net.ipmemorystore.IOnStatusAndCountListener iOnStatusAndCountListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iOnStatusAndCountListener);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method deleteCluster is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpMemoryStore
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
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

            @Override // android.net.IIpMemoryStore
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.IIpMemoryStore.DESCRIPTOR);
                            this.mRemote.transact(android.net.IIpMemoryStore.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
