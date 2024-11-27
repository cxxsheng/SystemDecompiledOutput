package android.service.ondeviceintelligence;

/* loaded from: classes3.dex */
public interface IOnDeviceTrustedInferenceService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService";

    void processRequest(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IResponseCallback iResponseCallback) throws android.os.RemoteException;

    void processRequestStreaming(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IStreamingResponseCallback iStreamingResponseCallback) throws android.os.RemoteException;

    void registerRemoteStorageService(android.service.ondeviceintelligence.IRemoteStorageService iRemoteStorageService) throws android.os.RemoteException;

    void requestTokenCount(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.ITokenCountCallback iTokenCountCallback) throws android.os.RemoteException;

    void updateProcessingState(android.os.Bundle bundle, android.service.ondeviceintelligence.IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) throws android.os.RemoteException;

    public static class Default implements android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService {
        @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
        public void registerRemoteStorageService(android.service.ondeviceintelligence.IRemoteStorageService iRemoteStorageService) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
        public void requestTokenCount(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.ITokenCountCallback iTokenCountCallback) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
        public void processRequest(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IResponseCallback iResponseCallback) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
        public void processRequestStreaming(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IStreamingResponseCallback iStreamingResponseCallback) throws android.os.RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
        public void updateProcessingState(android.os.Bundle bundle, android.service.ondeviceintelligence.IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService {
        static final int TRANSACTION_processRequest = 3;
        static final int TRANSACTION_processRequestStreaming = 4;
        static final int TRANSACTION_registerRemoteStorageService = 1;
        static final int TRANSACTION_requestTokenCount = 2;
        static final int TRANSACTION_updateProcessingState = 5;

        public Stub() {
            attachInterface(this, android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR);
        }

        public static android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService)) {
                return (android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService) queryLocalInterface;
            }
            return new android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerRemoteStorageService";
                case 2:
                    return "requestTokenCount";
                case 3:
                    return "processRequest";
                case 4:
                    return "processRequestStreaming";
                case 5:
                    return "updateProcessingState";
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
                parcel.enforceInterface(android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.ondeviceintelligence.IRemoteStorageService asInterface = android.service.ondeviceintelligence.IRemoteStorageService.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteStorageService(asInterface);
                    return true;
                case 2:
                    android.app.ondeviceintelligence.Feature feature = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.app.ondeviceintelligence.Content content = (android.app.ondeviceintelligence.Content) parcel.readTypedObject(android.app.ondeviceintelligence.Content.CREATOR);
                    android.os.ICancellationSignal asInterface2 = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.ITokenCountCallback asInterface3 = android.app.ondeviceintelligence.ITokenCountCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestTokenCount(feature, content, asInterface2, asInterface3);
                    return true;
                case 3:
                    android.app.ondeviceintelligence.Feature feature2 = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.app.ondeviceintelligence.Content content2 = (android.app.ondeviceintelligence.Content) parcel.readTypedObject(android.app.ondeviceintelligence.Content.CREATOR);
                    int readInt = parcel.readInt();
                    android.os.ICancellationSignal asInterface4 = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IProcessingSignal asInterface5 = android.app.ondeviceintelligence.IProcessingSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IResponseCallback asInterface6 = android.app.ondeviceintelligence.IResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    processRequest(feature2, content2, readInt, asInterface4, asInterface5, asInterface6);
                    return true;
                case 4:
                    android.app.ondeviceintelligence.Feature feature3 = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    android.app.ondeviceintelligence.Content content3 = (android.app.ondeviceintelligence.Content) parcel.readTypedObject(android.app.ondeviceintelligence.Content.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.os.ICancellationSignal asInterface7 = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IProcessingSignal asInterface8 = android.app.ondeviceintelligence.IProcessingSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.app.ondeviceintelligence.IStreamingResponseCallback asInterface9 = android.app.ondeviceintelligence.IStreamingResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    processRequestStreaming(feature3, content3, readInt2, asInterface7, asInterface8, asInterface9);
                    return true;
                case 5:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.service.ondeviceintelligence.IProcessingUpdateStatusCallback asInterface10 = android.service.ondeviceintelligence.IProcessingUpdateStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateProcessingState(bundle, asInterface10);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR;
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
            public void registerRemoteStorageService(android.service.ondeviceintelligence.IRemoteStorageService iRemoteStorageService) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteStorageService);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
            public void requestTokenCount(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.ITokenCountCallback iTokenCountCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeTypedObject(content, 0);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeStrongInterface(iTokenCountCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
            public void processRequest(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IResponseCallback iResponseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeTypedObject(content, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeStrongInterface(iProcessingSignal);
                    obtain.writeStrongInterface(iResponseCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
            public void processRequestStreaming(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IStreamingResponseCallback iStreamingResponseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    obtain.writeTypedObject(content, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeStrongInterface(iProcessingSignal);
                    obtain.writeStrongInterface(iStreamingResponseCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
            public void updateProcessingState(android.os.Bundle bundle, android.service.ondeviceintelligence.IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iProcessingUpdateStatusCallback);
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
