package android.app.ondeviceintelligence;

/* loaded from: classes.dex */
public interface IOnDeviceIntelligenceManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ondeviceintelligence.IOnDeviceIntelligenceManager";

    void getFeature(int i, android.app.ondeviceintelligence.IFeatureCallback iFeatureCallback) throws android.os.RemoteException;

    void getFeatureDetails(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.IFeatureDetailsCallback iFeatureDetailsCallback) throws android.os.RemoteException;

    void getVersion(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void listFeatures(android.app.ondeviceintelligence.IListFeaturesCallback iListFeaturesCallback) throws android.os.RemoteException;

    void processRequest(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IResponseCallback iResponseCallback) throws android.os.RemoteException;

    void processRequestStreaming(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IStreamingResponseCallback iStreamingResponseCallback) throws android.os.RemoteException;

    void requestFeatureDownload(android.app.ondeviceintelligence.Feature feature, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IDownloadCallback iDownloadCallback) throws android.os.RemoteException;

    void requestTokenCount(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.ITokenCountCallback iTokenCountCallback) throws android.os.RemoteException;

    public static class Default implements android.app.ondeviceintelligence.IOnDeviceIntelligenceManager {
        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void getVersion(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void getFeature(int i, android.app.ondeviceintelligence.IFeatureCallback iFeatureCallback) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void listFeatures(android.app.ondeviceintelligence.IListFeaturesCallback iListFeaturesCallback) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void getFeatureDetails(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.IFeatureDetailsCallback iFeatureDetailsCallback) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void requestFeatureDownload(android.app.ondeviceintelligence.Feature feature, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IDownloadCallback iDownloadCallback) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void requestTokenCount(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.ITokenCountCallback iTokenCountCallback) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void processRequest(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IResponseCallback iResponseCallback) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void processRequestStreaming(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IStreamingResponseCallback iStreamingResponseCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ondeviceintelligence.IOnDeviceIntelligenceManager {
        static final int TRANSACTION_getFeature = 3;
        static final int TRANSACTION_getFeatureDetails = 5;
        static final int TRANSACTION_getVersion = 2;
        static final int TRANSACTION_listFeatures = 4;
        static final int TRANSACTION_processRequest = 8;
        static final int TRANSACTION_processRequestStreaming = 9;
        static final int TRANSACTION_requestFeatureDownload = 6;
        static final int TRANSACTION_requestTokenCount = 7;

        public Stub() {
            attachInterface(this, android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
        }

        public static android.app.ondeviceintelligence.IOnDeviceIntelligenceManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ondeviceintelligence.IOnDeviceIntelligenceManager)) {
                return (android.app.ondeviceintelligence.IOnDeviceIntelligenceManager) queryLocalInterface;
            }
            return new android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 2:
                    return "getVersion";
                case 3:
                    return "getFeature";
                case 4:
                    return "listFeatures";
                case 5:
                    return "getFeatureDetails";
                case 6:
                    return "requestFeatureDownload";
                case 7:
                    return "requestTokenCount";
                case 8:
                    return "processRequest";
                case 9:
                    return "processRequestStreaming";
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
                parcel.enforceInterface(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 2:
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getVersion(remoteCallback);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    android.app.ondeviceintelligence.IFeatureCallback asInterface = android.app.ondeviceintelligence.IFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getFeature(readInt, asInterface);
                    return true;
                case 4:
                    android.app.ondeviceintelligence.IListFeaturesCallback asInterface2 = android.app.ondeviceintelligence.IListFeaturesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    listFeatures(asInterface2);
                    return true;
                case 5:
                    android.app.ondeviceintelligence.Feature feature = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.app.ondeviceintelligence.IFeatureDetailsCallback asInterface3 = android.app.ondeviceintelligence.IFeatureDetailsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getFeatureDetails(feature, asInterface3);
                    return true;
                case 6:
                    android.app.ondeviceintelligence.Feature feature2 = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.os.ICancellationSignal asInterface4 = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IDownloadCallback asInterface5 = android.app.ondeviceintelligence.IDownloadCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestFeatureDownload(feature2, asInterface4, asInterface5);
                    return true;
                case 7:
                    android.app.ondeviceintelligence.Feature feature3 = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.app.ondeviceintelligence.Content content = (android.app.ondeviceintelligence.Content) parcel.readTypedObject(android.app.ondeviceintelligence.Content.CREATOR);
                    android.os.ICancellationSignal asInterface6 = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.ITokenCountCallback asInterface7 = android.app.ondeviceintelligence.ITokenCountCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestTokenCount(feature3, content, asInterface6, asInterface7);
                    return true;
                case 8:
                    android.app.ondeviceintelligence.Feature feature4 = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.app.ondeviceintelligence.Content content2 = (android.app.ondeviceintelligence.Content) parcel.readTypedObject(android.app.ondeviceintelligence.Content.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.os.ICancellationSignal asInterface8 = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IProcessingSignal asInterface9 = android.app.ondeviceintelligence.IProcessingSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IResponseCallback asInterface10 = android.app.ondeviceintelligence.IResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    processRequest(feature4, content2, readInt2, asInterface8, asInterface9, asInterface10);
                    return true;
                case 9:
                    android.app.ondeviceintelligence.Feature feature5 = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.app.ondeviceintelligence.Content content3 = (android.app.ondeviceintelligence.Content) parcel.readTypedObject(android.app.ondeviceintelligence.Content.CREATOR);
                    int readInt3 = parcel.readInt();
                    android.os.ICancellationSignal asInterface11 = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IProcessingSignal asInterface12 = android.app.ondeviceintelligence.IProcessingSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IStreamingResponseCallback asInterface13 = android.app.ondeviceintelligence.IStreamingResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    processRequestStreaming(feature5, content3, readInt3, asInterface11, asInterface12, asInterface13);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ondeviceintelligence.IOnDeviceIntelligenceManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void getVersion(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void getFeature(int i, android.app.ondeviceintelligence.IFeatureCallback iFeatureCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFeatureCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void listFeatures(android.app.ondeviceintelligence.IListFeaturesCallback iListFeaturesCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iListFeaturesCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void getFeatureDetails(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.IFeatureDetailsCallback iFeatureDetailsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeStrongInterface(iFeatureDetailsCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void requestFeatureDownload(android.app.ondeviceintelligence.Feature feature, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IDownloadCallback iDownloadCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeStrongInterface(iDownloadCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void requestTokenCount(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.ITokenCountCallback iTokenCountCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeTypedObject(content, 0);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeStrongInterface(iTokenCountCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void processRequest(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IResponseCallback iResponseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeTypedObject(content, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeStrongInterface(iProcessingSignal);
                    obtain.writeStrongInterface(iResponseCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void processRequestStreaming(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IStreamingResponseCallback iStreamingResponseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeTypedObject(content, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeStrongInterface(iProcessingSignal);
                    obtain.writeStrongInterface(iStreamingResponseCallback);
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
