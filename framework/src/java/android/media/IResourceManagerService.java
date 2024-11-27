package android.media;

/* loaded from: classes2.dex */
public interface IResourceManagerService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IResourceManagerService";
    public static final java.lang.String kPolicySupportsMultipleSecureCodecs = "supports-multiple-secure-codecs";
    public static final java.lang.String kPolicySupportsSecureWithNonSecureCodec = "supports-secure-with-non-secure-codec";

    void addResource(android.media.ClientInfoParcel clientInfoParcel, android.media.IResourceManagerClient iResourceManagerClient, android.media.MediaResourceParcel[] mediaResourceParcelArr) throws android.os.RemoteException;

    void config(android.media.MediaResourcePolicyParcel[] mediaResourcePolicyParcelArr) throws android.os.RemoteException;

    void markClientForPendingRemoval(android.media.ClientInfoParcel clientInfoParcel) throws android.os.RemoteException;

    void notifyClientConfigChanged(android.media.ClientConfigParcel clientConfigParcel) throws android.os.RemoteException;

    void notifyClientCreated(android.media.ClientInfoParcel clientInfoParcel) throws android.os.RemoteException;

    void notifyClientStarted(android.media.ClientConfigParcel clientConfigParcel) throws android.os.RemoteException;

    void notifyClientStopped(android.media.ClientConfigParcel clientConfigParcel) throws android.os.RemoteException;

    void overridePid(int i, int i2) throws android.os.RemoteException;

    void overrideProcessInfo(android.media.IResourceManagerClient iResourceManagerClient, int i, int i2, int i3) throws android.os.RemoteException;

    boolean reclaimResource(android.media.ClientInfoParcel clientInfoParcel, android.media.MediaResourceParcel[] mediaResourceParcelArr) throws android.os.RemoteException;

    void reclaimResourcesFromClientsPendingRemoval(int i) throws android.os.RemoteException;

    void removeClient(android.media.ClientInfoParcel clientInfoParcel) throws android.os.RemoteException;

    void removeResource(android.media.ClientInfoParcel clientInfoParcel, android.media.MediaResourceParcel[] mediaResourceParcelArr) throws android.os.RemoteException;

    public static class Default implements android.media.IResourceManagerService {
        @Override // android.media.IResourceManagerService
        public void config(android.media.MediaResourcePolicyParcel[] mediaResourcePolicyParcelArr) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void addResource(android.media.ClientInfoParcel clientInfoParcel, android.media.IResourceManagerClient iResourceManagerClient, android.media.MediaResourceParcel[] mediaResourceParcelArr) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void removeResource(android.media.ClientInfoParcel clientInfoParcel, android.media.MediaResourceParcel[] mediaResourceParcelArr) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void removeClient(android.media.ClientInfoParcel clientInfoParcel) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public boolean reclaimResource(android.media.ClientInfoParcel clientInfoParcel, android.media.MediaResourceParcel[] mediaResourceParcelArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IResourceManagerService
        public void overridePid(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void overrideProcessInfo(android.media.IResourceManagerClient iResourceManagerClient, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void markClientForPendingRemoval(android.media.ClientInfoParcel clientInfoParcel) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void reclaimResourcesFromClientsPendingRemoval(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void notifyClientCreated(android.media.ClientInfoParcel clientInfoParcel) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void notifyClientStarted(android.media.ClientConfigParcel clientConfigParcel) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void notifyClientStopped(android.media.ClientConfigParcel clientConfigParcel) throws android.os.RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void notifyClientConfigChanged(android.media.ClientConfigParcel clientConfigParcel) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IResourceManagerService {
        static final int TRANSACTION_addResource = 2;
        static final int TRANSACTION_config = 1;
        static final int TRANSACTION_markClientForPendingRemoval = 8;
        static final int TRANSACTION_notifyClientConfigChanged = 13;
        static final int TRANSACTION_notifyClientCreated = 10;
        static final int TRANSACTION_notifyClientStarted = 11;
        static final int TRANSACTION_notifyClientStopped = 12;
        static final int TRANSACTION_overridePid = 6;
        static final int TRANSACTION_overrideProcessInfo = 7;
        static final int TRANSACTION_reclaimResource = 5;
        static final int TRANSACTION_reclaimResourcesFromClientsPendingRemoval = 9;
        static final int TRANSACTION_removeClient = 4;
        static final int TRANSACTION_removeResource = 3;

        public Stub() {
            attachInterface(this, android.media.IResourceManagerService.DESCRIPTOR);
        }

        public static android.media.IResourceManagerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IResourceManagerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IResourceManagerService)) {
                return (android.media.IResourceManagerService) queryLocalInterface;
            }
            return new android.media.IResourceManagerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "config";
                case 2:
                    return "addResource";
                case 3:
                    return "removeResource";
                case 4:
                    return "removeClient";
                case 5:
                    return "reclaimResource";
                case 6:
                    return "overridePid";
                case 7:
                    return "overrideProcessInfo";
                case 8:
                    return "markClientForPendingRemoval";
                case 9:
                    return "reclaimResourcesFromClientsPendingRemoval";
                case 10:
                    return "notifyClientCreated";
                case 11:
                    return "notifyClientStarted";
                case 12:
                    return "notifyClientStopped";
                case 13:
                    return "notifyClientConfigChanged";
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
                parcel.enforceInterface(android.media.IResourceManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IResourceManagerService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.MediaResourcePolicyParcel[] mediaResourcePolicyParcelArr = (android.media.MediaResourcePolicyParcel[]) parcel.createTypedArray(android.media.MediaResourcePolicyParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    config(mediaResourcePolicyParcelArr);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.media.ClientInfoParcel clientInfoParcel = (android.media.ClientInfoParcel) parcel.readTypedObject(android.media.ClientInfoParcel.CREATOR);
                    android.media.IResourceManagerClient asInterface = android.media.IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    android.media.MediaResourceParcel[] mediaResourceParcelArr = (android.media.MediaResourceParcel[]) parcel.createTypedArray(android.media.MediaResourceParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    addResource(clientInfoParcel, asInterface, mediaResourceParcelArr);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.media.ClientInfoParcel clientInfoParcel2 = (android.media.ClientInfoParcel) parcel.readTypedObject(android.media.ClientInfoParcel.CREATOR);
                    android.media.MediaResourceParcel[] mediaResourceParcelArr2 = (android.media.MediaResourceParcel[]) parcel.createTypedArray(android.media.MediaResourceParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeResource(clientInfoParcel2, mediaResourceParcelArr2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.media.ClientInfoParcel clientInfoParcel3 = (android.media.ClientInfoParcel) parcel.readTypedObject(android.media.ClientInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeClient(clientInfoParcel3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.media.ClientInfoParcel clientInfoParcel4 = (android.media.ClientInfoParcel) parcel.readTypedObject(android.media.ClientInfoParcel.CREATOR);
                    android.media.MediaResourceParcel[] mediaResourceParcelArr3 = (android.media.MediaResourceParcel[]) parcel.createTypedArray(android.media.MediaResourceParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean reclaimResource = reclaimResource(clientInfoParcel4, mediaResourceParcelArr3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reclaimResource);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePid(readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.media.IResourceManagerClient asInterface2 = android.media.IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideProcessInfo(asInterface2, readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.media.ClientInfoParcel clientInfoParcel5 = (android.media.ClientInfoParcel) parcel.readTypedObject(android.media.ClientInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    markClientForPendingRemoval(clientInfoParcel5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reclaimResourcesFromClientsPendingRemoval(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.media.ClientInfoParcel clientInfoParcel6 = (android.media.ClientInfoParcel) parcel.readTypedObject(android.media.ClientInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyClientCreated(clientInfoParcel6);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.media.ClientConfigParcel clientConfigParcel = (android.media.ClientConfigParcel) parcel.readTypedObject(android.media.ClientConfigParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyClientStarted(clientConfigParcel);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.media.ClientConfigParcel clientConfigParcel2 = (android.media.ClientConfigParcel) parcel.readTypedObject(android.media.ClientConfigParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyClientStopped(clientConfigParcel2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.media.ClientConfigParcel clientConfigParcel3 = (android.media.ClientConfigParcel) parcel.readTypedObject(android.media.ClientConfigParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyClientConfigChanged(clientConfigParcel3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IResourceManagerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IResourceManagerService.DESCRIPTOR;
            }

            @Override // android.media.IResourceManagerService
            public void config(android.media.MediaResourcePolicyParcel[] mediaResourcePolicyParcelArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedArray(mediaResourcePolicyParcelArr, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void addResource(android.media.ClientInfoParcel clientInfoParcel, android.media.IResourceManagerClient iResourceManagerClient, android.media.MediaResourceParcel[] mediaResourceParcelArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    obtain.writeStrongInterface(iResourceManagerClient);
                    obtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void removeResource(android.media.ClientInfoParcel clientInfoParcel, android.media.MediaResourceParcel[] mediaResourceParcelArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    obtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void removeClient(android.media.ClientInfoParcel clientInfoParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public boolean reclaimResource(android.media.ClientInfoParcel clientInfoParcel, android.media.MediaResourceParcel[] mediaResourceParcelArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    obtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void overridePid(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void overrideProcessInfo(android.media.IResourceManagerClient iResourceManagerClient, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResourceManagerClient);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void markClientForPendingRemoval(android.media.ClientInfoParcel clientInfoParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void reclaimResourcesFromClientsPendingRemoval(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientCreated(android.media.ClientInfoParcel clientInfoParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientStarted(android.media.ClientConfigParcel clientConfigParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientConfigParcel, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientStopped(android.media.ClientConfigParcel clientConfigParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientConfigParcel, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientConfigChanged(android.media.ClientConfigParcel clientConfigParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientConfigParcel, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
