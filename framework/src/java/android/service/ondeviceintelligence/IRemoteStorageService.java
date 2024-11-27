package android.service.ondeviceintelligence;

/* loaded from: classes3.dex */
public interface IRemoteStorageService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.ondeviceintelligence.IRemoteStorageService";

    void getReadOnlyFeatureFileDescriptorMap(android.app.ondeviceintelligence.Feature feature, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void getReadOnlyFileDescriptor(java.lang.String str, com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) throws android.os.RemoteException;

    public static class Default implements android.service.ondeviceintelligence.IRemoteStorageService {
        @Override // android.service.ondeviceintelligence.IRemoteStorageService
        public void getReadOnlyFileDescriptor(java.lang.String str, com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IRemoteStorageService
        public void getReadOnlyFeatureFileDescriptorMap(android.app.ondeviceintelligence.Feature feature, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.ondeviceintelligence.IRemoteStorageService {
        static final int TRANSACTION_getReadOnlyFeatureFileDescriptorMap = 2;
        static final int TRANSACTION_getReadOnlyFileDescriptor = 1;

        public Stub() {
            attachInterface(this, android.service.ondeviceintelligence.IRemoteStorageService.DESCRIPTOR);
        }

        public static android.service.ondeviceintelligence.IRemoteStorageService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.ondeviceintelligence.IRemoteStorageService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.ondeviceintelligence.IRemoteStorageService)) {
                return (android.service.ondeviceintelligence.IRemoteStorageService) queryLocalInterface;
            }
            return new android.service.ondeviceintelligence.IRemoteStorageService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getReadOnlyFileDescriptor";
                case 2:
                    return "getReadOnlyFeatureFileDescriptorMap";
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
                parcel.enforceInterface(android.service.ondeviceintelligence.IRemoteStorageService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.ondeviceintelligence.IRemoteStorageService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getReadOnlyFileDescriptor(readString, androidFuture);
                    return true;
                case 2:
                    android.app.ondeviceintelligence.Feature feature = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getReadOnlyFeatureFileDescriptorMap(feature, remoteCallback);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.ondeviceintelligence.IRemoteStorageService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.ondeviceintelligence.IRemoteStorageService.DESCRIPTOR;
            }

            @Override // android.service.ondeviceintelligence.IRemoteStorageService
            public void getReadOnlyFileDescriptor(java.lang.String str, com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IRemoteStorageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IRemoteStorageService
            public void getReadOnlyFeatureFileDescriptorMap(android.app.ondeviceintelligence.Feature feature, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IRemoteStorageService.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
