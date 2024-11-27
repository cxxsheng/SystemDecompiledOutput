package android.speech;

/* loaded from: classes3.dex */
public interface IRecognitionService extends android.os.IInterface {
    void cancel(android.speech.IRecognitionListener iRecognitionListener, boolean z) throws android.os.RemoteException;

    void checkRecognitionSupport(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IRecognitionSupportCallback iRecognitionSupportCallback) throws android.os.RemoteException;

    void startListening(android.content.Intent intent, android.speech.IRecognitionListener iRecognitionListener, android.content.AttributionSource attributionSource) throws android.os.RemoteException;

    void stopListening(android.speech.IRecognitionListener iRecognitionListener) throws android.os.RemoteException;

    void triggerModelDownload(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IModelDownloadListener iModelDownloadListener) throws android.os.RemoteException;

    public static class Default implements android.speech.IRecognitionService {
        @Override // android.speech.IRecognitionService
        public void startListening(android.content.Intent intent, android.speech.IRecognitionListener iRecognitionListener, android.content.AttributionSource attributionSource) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void stopListening(android.speech.IRecognitionListener iRecognitionListener) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void cancel(android.speech.IRecognitionListener iRecognitionListener, boolean z) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void checkRecognitionSupport(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IRecognitionSupportCallback iRecognitionSupportCallback) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void triggerModelDownload(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IModelDownloadListener iModelDownloadListener) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.speech.IRecognitionService {
        public static final java.lang.String DESCRIPTOR = "android.speech.IRecognitionService";
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_checkRecognitionSupport = 4;
        static final int TRANSACTION_startListening = 1;
        static final int TRANSACTION_stopListening = 2;
        static final int TRANSACTION_triggerModelDownload = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.speech.IRecognitionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.speech.IRecognitionService)) {
                return (android.speech.IRecognitionService) queryLocalInterface;
            }
            return new android.speech.IRecognitionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startListening";
                case 2:
                    return "stopListening";
                case 3:
                    return "cancel";
                case 4:
                    return "checkRecognitionSupport";
                case 5:
                    return "triggerModelDownload";
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
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.speech.IRecognitionListener asInterface = android.speech.IRecognitionListener.Stub.asInterface(parcel.readStrongBinder());
                    android.content.AttributionSource attributionSource = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    startListening(intent, asInterface, attributionSource);
                    return true;
                case 2:
                    android.speech.IRecognitionListener asInterface2 = android.speech.IRecognitionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopListening(asInterface2);
                    return true;
                case 3:
                    android.speech.IRecognitionListener asInterface3 = android.speech.IRecognitionListener.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    cancel(asInterface3, readBoolean);
                    return true;
                case 4:
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.content.AttributionSource attributionSource2 = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    android.speech.IRecognitionSupportCallback asInterface4 = android.speech.IRecognitionSupportCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    checkRecognitionSupport(intent2, attributionSource2, asInterface4);
                    return true;
                case 5:
                    android.content.Intent intent3 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.content.AttributionSource attributionSource3 = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    android.speech.IModelDownloadListener asInterface5 = android.speech.IModelDownloadListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    triggerModelDownload(intent3, attributionSource3, asInterface5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.speech.IRecognitionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.speech.IRecognitionService.Stub.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionService
            public void startListening(android.content.Intent intent, android.speech.IRecognitionListener iRecognitionListener, android.content.AttributionSource attributionSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongInterface(iRecognitionListener);
                    obtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void stopListening(android.speech.IRecognitionListener iRecognitionListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecognitionListener);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void cancel(android.speech.IRecognitionListener iRecognitionListener, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecognitionListener);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void checkRecognitionSupport(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IRecognitionSupportCallback iRecognitionSupportCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeStrongInterface(iRecognitionSupportCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void triggerModelDownload(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IModelDownloadListener iModelDownloadListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeStrongInterface(iModelDownloadListener);
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
