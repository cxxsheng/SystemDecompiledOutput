package android.service.voice;

/* loaded from: classes3.dex */
public interface ISandboxedDetectionService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.voice.ISandboxedDetectionService";

    void detectFromDspSource(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.media.AudioFormat audioFormat, long j, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws android.os.RemoteException;

    void detectFromMicrophoneSource(android.os.ParcelFileDescriptor parcelFileDescriptor, int i, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws android.os.RemoteException;

    void detectWithVisualSignals(android.service.voice.IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) throws android.os.RemoteException;

    void ping(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    void registerRemoteStorageService(android.service.voice.IDetectorSessionStorageService iDetectorSessionStorageService) throws android.os.RemoteException;

    void stopDetection() throws android.os.RemoteException;

    void updateAudioFlinger(android.os.IBinder iBinder) throws android.os.RemoteException;

    void updateContentCaptureManager(android.view.contentcapture.IContentCaptureManager iContentCaptureManager, android.content.ContentCaptureOptions contentCaptureOptions) throws android.os.RemoteException;

    void updateRecognitionServiceManager(android.speech.IRecognitionServiceManager iRecognitionServiceManager) throws android.os.RemoteException;

    void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    public static class Default implements android.service.voice.ISandboxedDetectionService {
        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromDspSource(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.media.AudioFormat audioFormat, long j, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromMicrophoneSource(android.os.ParcelFileDescriptor parcelFileDescriptor, int i, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectWithVisualSignals(android.service.voice.IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateAudioFlinger(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateContentCaptureManager(android.view.contentcapture.IContentCaptureManager iContentCaptureManager, android.content.ContentCaptureOptions contentCaptureOptions) throws android.os.RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateRecognitionServiceManager(android.speech.IRecognitionServiceManager iRecognitionServiceManager) throws android.os.RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void ping(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void stopDetection() throws android.os.RemoteException {
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void registerRemoteStorageService(android.service.voice.IDetectorSessionStorageService iDetectorSessionStorageService) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.voice.ISandboxedDetectionService {
        static final int TRANSACTION_detectFromDspSource = 1;
        static final int TRANSACTION_detectFromMicrophoneSource = 2;
        static final int TRANSACTION_detectWithVisualSignals = 3;
        static final int TRANSACTION_ping = 8;
        static final int TRANSACTION_registerRemoteStorageService = 10;
        static final int TRANSACTION_stopDetection = 9;
        static final int TRANSACTION_updateAudioFlinger = 5;
        static final int TRANSACTION_updateContentCaptureManager = 6;
        static final int TRANSACTION_updateRecognitionServiceManager = 7;
        static final int TRANSACTION_updateState = 4;

        public Stub() {
            attachInterface(this, android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
        }

        public static android.service.voice.ISandboxedDetectionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.voice.ISandboxedDetectionService)) {
                return (android.service.voice.ISandboxedDetectionService) queryLocalInterface;
            }
            return new android.service.voice.ISandboxedDetectionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "detectFromDspSource";
                case 2:
                    return "detectFromMicrophoneSource";
                case 3:
                    return "detectWithVisualSignals";
                case 4:
                    return "updateState";
                case 5:
                    return "updateAudioFlinger";
                case 6:
                    return "updateContentCaptureManager";
                case 7:
                    return "updateRecognitionServiceManager";
                case 8:
                    return "ping";
                case 9:
                    return "stopDetection";
                case 10:
                    return "registerRemoteStorageService";
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
                parcel.enforceInterface(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent = (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    android.media.AudioFormat audioFormat = (android.media.AudioFormat) parcel.readTypedObject(android.media.AudioFormat.CREATOR);
                    long readLong = parcel.readLong();
                    android.service.voice.IDspHotwordDetectionCallback asInterface = android.service.voice.IDspHotwordDetectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    detectFromDspSource(keyphraseRecognitionEvent, audioFormat, readLong, asInterface);
                    return true;
                case 2:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    int readInt = parcel.readInt();
                    android.media.AudioFormat audioFormat2 = (android.media.AudioFormat) parcel.readTypedObject(android.media.AudioFormat.CREATOR);
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    android.service.voice.IDspHotwordDetectionCallback asInterface2 = android.service.voice.IDspHotwordDetectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    detectFromMicrophoneSource(parcelFileDescriptor, readInt, audioFormat2, persistableBundle, asInterface2);
                    return true;
                case 3:
                    android.service.voice.IDetectorSessionVisualQueryDetectionCallback asInterface3 = android.service.voice.IDetectorSessionVisualQueryDetectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    detectWithVisualSignals(asInterface3);
                    return true;
                case 4:
                    android.os.PersistableBundle persistableBundle2 = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    android.os.SharedMemory sharedMemory = (android.os.SharedMemory) parcel.readTypedObject(android.os.SharedMemory.CREATOR);
                    android.os.IRemoteCallback asInterface4 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateState(persistableBundle2, sharedMemory, asInterface4);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateAudioFlinger(readStrongBinder);
                    return true;
                case 6:
                    android.view.contentcapture.IContentCaptureManager asInterface5 = android.view.contentcapture.IContentCaptureManager.Stub.asInterface(parcel.readStrongBinder());
                    android.content.ContentCaptureOptions contentCaptureOptions = (android.content.ContentCaptureOptions) parcel.readTypedObject(android.content.ContentCaptureOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateContentCaptureManager(asInterface5, contentCaptureOptions);
                    return true;
                case 7:
                    android.speech.IRecognitionServiceManager asInterface6 = android.speech.IRecognitionServiceManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateRecognitionServiceManager(asInterface6);
                    return true;
                case 8:
                    android.os.IRemoteCallback asInterface7 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ping(asInterface7);
                    return true;
                case 9:
                    stopDetection();
                    return true;
                case 10:
                    android.service.voice.IDetectorSessionStorageService asInterface8 = android.service.voice.IDetectorSessionStorageService.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteStorageService(asInterface8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.voice.ISandboxedDetectionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.voice.ISandboxedDetectionService.DESCRIPTOR;
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void detectFromDspSource(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.media.AudioFormat audioFormat, long j, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    obtain.writeTypedObject(keyphraseRecognitionEvent, 0);
                    obtain.writeTypedObject(audioFormat, 0);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iDspHotwordDetectionCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void detectFromMicrophoneSource(android.os.ParcelFileDescriptor parcelFileDescriptor, int i, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioFormat, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeStrongInterface(iDspHotwordDetectionCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void detectWithVisualSignals(android.service.voice.IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iDetectorSessionVisualQueryDetectionCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void updateAudioFlinger(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void updateContentCaptureManager(android.view.contentcapture.IContentCaptureManager iContentCaptureManager, android.content.ContentCaptureOptions contentCaptureOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iContentCaptureManager);
                    obtain.writeTypedObject(contentCaptureOptions, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void updateRecognitionServiceManager(android.speech.IRecognitionServiceManager iRecognitionServiceManager) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecognitionServiceManager);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void ping(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void stopDetection() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.ISandboxedDetectionService
            public void registerRemoteStorageService(android.service.voice.IDetectorSessionStorageService iDetectorSessionStorageService) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.voice.ISandboxedDetectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iDetectorSessionStorageService);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
