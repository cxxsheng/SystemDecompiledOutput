package android.service.ondeviceintelligence;

/* loaded from: classes3.dex */
public interface IOnDeviceIntelligenceService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.ondeviceintelligence.IOnDeviceIntelligenceService";

    void getFeature(int i, android.app.ondeviceintelligence.IFeatureCallback iFeatureCallback) throws android.os.RemoteException;

    void getFeatureDetails(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.IFeatureDetailsCallback iFeatureDetailsCallback) throws android.os.RemoteException;

    void getReadOnlyFeatureFileDescriptorMap(android.app.ondeviceintelligence.Feature feature, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void getReadOnlyFileDescriptor(java.lang.String str, com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) throws android.os.RemoteException;

    void getVersion(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void listFeatures(android.app.ondeviceintelligence.IListFeaturesCallback iListFeaturesCallback) throws android.os.RemoteException;

    void registerRemoteServices(android.service.ondeviceintelligence.IRemoteProcessingService iRemoteProcessingService) throws android.os.RemoteException;

    void requestFeatureDownload(android.app.ondeviceintelligence.Feature feature, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IDownloadCallback iDownloadCallback) throws android.os.RemoteException;

    public static class Default implements android.service.ondeviceintelligence.IOnDeviceIntelligenceService {
        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getVersion(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getFeature(int i, android.app.ondeviceintelligence.IFeatureCallback iFeatureCallback) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void listFeatures(android.app.ondeviceintelligence.IListFeaturesCallback iListFeaturesCallback) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getFeatureDetails(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.IFeatureDetailsCallback iFeatureDetailsCallback) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getReadOnlyFileDescriptor(java.lang.String str, com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getReadOnlyFeatureFileDescriptorMap(android.app.ondeviceintelligence.Feature feature, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void requestFeatureDownload(android.app.ondeviceintelligence.Feature feature, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IDownloadCallback iDownloadCallback) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void registerRemoteServices(android.service.ondeviceintelligence.IRemoteProcessingService iRemoteProcessingService) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.ondeviceintelligence.IOnDeviceIntelligenceService {
        static final int TRANSACTION_getFeature = 2;
        static final int TRANSACTION_getFeatureDetails = 4;
        static final int TRANSACTION_getReadOnlyFeatureFileDescriptorMap = 6;
        static final int TRANSACTION_getReadOnlyFileDescriptor = 5;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_listFeatures = 3;
        static final int TRANSACTION_registerRemoteServices = 8;
        static final int TRANSACTION_requestFeatureDownload = 7;

        public Stub() {
            attachInterface(this, android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
        }

        public static android.service.ondeviceintelligence.IOnDeviceIntelligenceService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.ondeviceintelligence.IOnDeviceIntelligenceService)) {
                return (android.service.ondeviceintelligence.IOnDeviceIntelligenceService) queryLocalInterface;
            }
            return new android.service.ondeviceintelligence.IOnDeviceIntelligenceService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVersion";
                case 2:
                    return "getFeature";
                case 3:
                    return "listFeatures";
                case 4:
                    return "getFeatureDetails";
                case 5:
                    return "getReadOnlyFileDescriptor";
                case 6:
                    return "getReadOnlyFeatureFileDescriptorMap";
                case 7:
                    return "requestFeatureDownload";
                case 8:
                    return "registerRemoteServices";
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
                parcel.enforceInterface(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getVersion(remoteCallback);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    android.app.ondeviceintelligence.IFeatureCallback asInterface = android.app.ondeviceintelligence.IFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getFeature(readInt, asInterface);
                    return true;
                case 3:
                    android.app.ondeviceintelligence.IListFeaturesCallback asInterface2 = android.app.ondeviceintelligence.IListFeaturesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    listFeatures(asInterface2);
                    return true;
                case 4:
                    android.app.ondeviceintelligence.Feature feature = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.app.ondeviceintelligence.IFeatureDetailsCallback asInterface3 = android.app.ondeviceintelligence.IFeatureDetailsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getFeatureDetails(feature, asInterface3);
                    return true;
                case 5:
                    java.lang.String readString = parcel.readString();
                    com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getReadOnlyFileDescriptor(readString, androidFuture);
                    return true;
                case 6:
                    android.app.ondeviceintelligence.Feature feature2 = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getReadOnlyFeatureFileDescriptorMap(feature2, remoteCallback2);
                    return true;
                case 7:
                    android.app.ondeviceintelligence.Feature feature3 = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.os.ICancellationSignal asInterface4 = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IDownloadCallback asInterface5 = android.app.ondeviceintelligence.IDownloadCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestFeatureDownload(feature3, asInterface4, asInterface5);
                    return true;
                case 8:
                    android.service.ondeviceintelligence.IRemoteProcessingService asInterface6 = android.service.ondeviceintelligence.IRemoteProcessingService.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteServices(asInterface6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.ondeviceintelligence.IOnDeviceIntelligenceService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR;
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getVersion(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getFeature(int i, android.app.ondeviceintelligence.IFeatureCallback iFeatureCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFeatureCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void listFeatures(android.app.ondeviceintelligence.IListFeaturesCallback iListFeaturesCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
                    obtain.writeStrongInterface(iListFeaturesCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getFeatureDetails(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.IFeatureDetailsCallback iFeatureDetailsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeStrongInterface(iFeatureDetailsCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getReadOnlyFileDescriptor(java.lang.String str, com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getReadOnlyFeatureFileDescriptorMap(android.app.ondeviceintelligence.Feature feature, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void requestFeatureDownload(android.app.ondeviceintelligence.Feature feature, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IDownloadCallback iDownloadCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeStrongInterface(iDownloadCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void registerRemoteServices(android.service.ondeviceintelligence.IRemoteProcessingService iRemoteProcessingService) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceIntelligenceService.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteProcessingService);
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
