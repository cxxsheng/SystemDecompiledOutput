package android.media.musicrecognition;

/* loaded from: classes2.dex */
public interface IMusicRecognitionService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionService";

    void getAttributionTag(android.media.musicrecognition.IMusicRecognitionAttributionTagCallback iMusicRecognitionAttributionTagCallback) throws android.os.RemoteException;

    void onAudioStreamStarted(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.media.musicrecognition.IMusicRecognitionServiceCallback iMusicRecognitionServiceCallback) throws android.os.RemoteException;

    public static class Default implements android.media.musicrecognition.IMusicRecognitionService {
        @Override // android.media.musicrecognition.IMusicRecognitionService
        public void onAudioStreamStarted(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.media.musicrecognition.IMusicRecognitionServiceCallback iMusicRecognitionServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.media.musicrecognition.IMusicRecognitionService
        public void getAttributionTag(android.media.musicrecognition.IMusicRecognitionAttributionTagCallback iMusicRecognitionAttributionTagCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.musicrecognition.IMusicRecognitionService {
        static final int TRANSACTION_getAttributionTag = 2;
        static final int TRANSACTION_onAudioStreamStarted = 1;

        public Stub() {
            attachInterface(this, android.media.musicrecognition.IMusicRecognitionService.DESCRIPTOR);
        }

        public static android.media.musicrecognition.IMusicRecognitionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.musicrecognition.IMusicRecognitionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.musicrecognition.IMusicRecognitionService)) {
                return (android.media.musicrecognition.IMusicRecognitionService) queryLocalInterface;
            }
            return new android.media.musicrecognition.IMusicRecognitionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAudioStreamStarted";
                case 2:
                    return "getAttributionTag";
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
                parcel.enforceInterface(android.media.musicrecognition.IMusicRecognitionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.musicrecognition.IMusicRecognitionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.media.AudioFormat audioFormat = (android.media.AudioFormat) parcel.readTypedObject(android.media.AudioFormat.CREATOR);
                    android.media.musicrecognition.IMusicRecognitionServiceCallback asInterface = android.media.musicrecognition.IMusicRecognitionServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onAudioStreamStarted(parcelFileDescriptor, audioFormat, asInterface);
                    return true;
                case 2:
                    android.media.musicrecognition.IMusicRecognitionAttributionTagCallback asInterface2 = android.media.musicrecognition.IMusicRecognitionAttributionTagCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAttributionTag(asInterface2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.musicrecognition.IMusicRecognitionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.musicrecognition.IMusicRecognitionService.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionService
            public void onAudioStreamStarted(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.media.musicrecognition.IMusicRecognitionServiceCallback iMusicRecognitionServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.musicrecognition.IMusicRecognitionService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(audioFormat, 0);
                    obtain.writeStrongInterface(iMusicRecognitionServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.musicrecognition.IMusicRecognitionService
            public void getAttributionTag(android.media.musicrecognition.IMusicRecognitionAttributionTagCallback iMusicRecognitionAttributionTagCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.musicrecognition.IMusicRecognitionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMusicRecognitionAttributionTagCallback);
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
