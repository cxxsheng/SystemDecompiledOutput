package android.service.wearable;

/* loaded from: classes3.dex */
public interface IWearableSensingService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.wearable.IWearableSensingService";

    void killProcess() throws android.os.RemoteException;

    void onValidatedByHotwordDetectionService() throws android.os.RemoteException;

    void provideData(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void provideDataStream(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void provideSecureConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void registerDataRequestObserver(int i, android.os.RemoteCallback remoteCallback, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException;

    void startDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException;

    void startHotwordRecognition(android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException;

    void stopActiveHotwordAudio() throws android.os.RemoteException;

    void stopDetection(java.lang.String str) throws android.os.RemoteException;

    void stopHotwordRecognition(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void unregisterDataRequestObserver(int i, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    public static class Default implements android.service.wearable.IWearableSensingService {
        @Override // android.service.wearable.IWearableSensingService
        public void provideSecureConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideDataStream(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideData(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void registerDataRequestObserver(int i, android.os.RemoteCallback remoteCallback, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void unregisterDataRequestObserver(int i, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startHotwordRecognition(android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopHotwordRecognition(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void onValidatedByHotwordDetectionService() throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopActiveHotwordAudio() throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopDetection(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void killProcess() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.wearable.IWearableSensingService {
        static final int TRANSACTION_killProcess = 13;
        static final int TRANSACTION_onValidatedByHotwordDetectionService = 8;
        static final int TRANSACTION_provideData = 3;
        static final int TRANSACTION_provideDataStream = 2;
        static final int TRANSACTION_provideSecureConnection = 1;
        static final int TRANSACTION_queryServiceStatus = 12;
        static final int TRANSACTION_registerDataRequestObserver = 4;
        static final int TRANSACTION_startDetection = 10;
        static final int TRANSACTION_startHotwordRecognition = 6;
        static final int TRANSACTION_stopActiveHotwordAudio = 9;
        static final int TRANSACTION_stopDetection = 11;
        static final int TRANSACTION_stopHotwordRecognition = 7;
        static final int TRANSACTION_unregisterDataRequestObserver = 5;

        public Stub() {
            attachInterface(this, android.service.wearable.IWearableSensingService.DESCRIPTOR);
        }

        public static android.service.wearable.IWearableSensingService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.wearable.IWearableSensingService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.wearable.IWearableSensingService)) {
                return (android.service.wearable.IWearableSensingService) queryLocalInterface;
            }
            return new android.service.wearable.IWearableSensingService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "provideSecureConnection";
                case 2:
                    return "provideDataStream";
                case 3:
                    return "provideData";
                case 4:
                    return "registerDataRequestObserver";
                case 5:
                    return "unregisterDataRequestObserver";
                case 6:
                    return "startHotwordRecognition";
                case 7:
                    return "stopHotwordRecognition";
                case 8:
                    return "onValidatedByHotwordDetectionService";
                case 9:
                    return "stopActiveHotwordAudio";
                case 10:
                    return "startDetection";
                case 11:
                    return "stopDetection";
                case 12:
                    return "queryServiceStatus";
                case 13:
                    return "killProcess";
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
                parcel.enforceInterface(android.service.wearable.IWearableSensingService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideSecureConnection(parcelFileDescriptor, remoteCallback);
                    return true;
                case 2:
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideDataStream(parcelFileDescriptor2, remoteCallback2);
                    return true;
                case 3:
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    android.os.SharedMemory sharedMemory = (android.os.SharedMemory) parcel.readTypedObject(android.os.SharedMemory.CREATOR);
                    android.os.RemoteCallback remoteCallback3 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideData(persistableBundle, sharedMemory, remoteCallback3);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    android.os.RemoteCallback remoteCallback4 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    int readInt2 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.os.RemoteCallback remoteCallback5 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerDataRequestObserver(readInt, remoteCallback4, readInt2, readString, remoteCallback5);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    android.os.RemoteCallback remoteCallback6 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterDataRequestObserver(readInt3, readInt4, readString2, remoteCallback6);
                    return true;
                case 6:
                    android.os.RemoteCallback remoteCallback7 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    android.os.RemoteCallback remoteCallback8 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    startHotwordRecognition(remoteCallback7, remoteCallback8);
                    return true;
                case 7:
                    android.os.RemoteCallback remoteCallback9 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopHotwordRecognition(remoteCallback9);
                    return true;
                case 8:
                    onValidatedByHotwordDetectionService();
                    return true;
                case 9:
                    stopActiveHotwordAudio();
                    return true;
                case 10:
                    android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest = (android.app.ambientcontext.AmbientContextEventRequest) parcel.readTypedObject(android.app.ambientcontext.AmbientContextEventRequest.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    android.os.RemoteCallback remoteCallback10 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    android.os.RemoteCallback remoteCallback11 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    startDetection(ambientContextEventRequest, readString3, remoteCallback10, remoteCallback11);
                    return true;
                case 11:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopDetection(readString4);
                    return true;
                case 12:
                    int[] createIntArray = parcel.createIntArray();
                    java.lang.String readString5 = parcel.readString();
                    android.os.RemoteCallback remoteCallback12 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryServiceStatus(createIntArray, readString5, remoteCallback12);
                    return true;
                case 13:
                    killProcess();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.wearable.IWearableSensingService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.wearable.IWearableSensingService.DESCRIPTOR;
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideSecureConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideDataStream(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideData(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void registerDataRequestObserver(int i, android.os.RemoteCallback remoteCallback, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void unregisterDataRequestObserver(int i, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void startHotwordRecognition(android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void stopHotwordRecognition(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void onValidatedByHotwordDetectionService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void stopActiveHotwordAudio() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void startDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(ambientContextEventRequest, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void stopDetection(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void killProcess() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wearable.IWearableSensingService.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
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
