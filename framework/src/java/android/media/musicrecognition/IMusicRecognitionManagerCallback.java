package android.media.musicrecognition;

/* loaded from: classes2.dex */
public interface IMusicRecognitionManagerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionManagerCallback";

    void onAudioStreamClosed() throws android.os.RemoteException;

    void onRecognitionFailed(int i) throws android.os.RemoteException;

    void onRecognitionSucceeded(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.media.musicrecognition.IMusicRecognitionManagerCallback {
        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onRecognitionSucceeded(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onRecognitionFailed(int i) throws android.os.RemoteException {
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onAudioStreamClosed() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.musicrecognition.IMusicRecognitionManagerCallback {
        static final int TRANSACTION_onAudioStreamClosed = 3;
        static final int TRANSACTION_onRecognitionFailed = 2;
        static final int TRANSACTION_onRecognitionSucceeded = 1;

        public Stub() {
            attachInterface(this, android.media.musicrecognition.IMusicRecognitionManagerCallback.DESCRIPTOR);
        }

        public static android.media.musicrecognition.IMusicRecognitionManagerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.musicrecognition.IMusicRecognitionManagerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.musicrecognition.IMusicRecognitionManagerCallback)) {
                return (android.media.musicrecognition.IMusicRecognitionManagerCallback) queryLocalInterface;
            }
            return new android.media.musicrecognition.IMusicRecognitionManagerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRecognitionSucceeded";
                case 2:
                    return "onRecognitionFailed";
                case 3:
                    return "onAudioStreamClosed";
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
                parcel.enforceInterface(android.media.musicrecognition.IMusicRecognitionManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.musicrecognition.IMusicRecognitionManagerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.MediaMetadata mediaMetadata = (android.media.MediaMetadata) parcel.readTypedObject(android.media.MediaMetadata.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRecognitionSucceeded(mediaMetadata, bundle);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecognitionFailed(readInt);
                    return true;
                case 3:
                    onAudioStreamClosed();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.musicrecognition.IMusicRecognitionManagerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.musicrecognition.IMusicRecognitionManagerCallback.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
            public void onRecognitionSucceeded(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.musicrecognition.IMusicRecognitionManagerCallback.DESCRIPTOR);
                    obtain.writeTypedObject(mediaMetadata, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
            public void onRecognitionFailed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.musicrecognition.IMusicRecognitionManagerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
            public void onAudioStreamClosed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.musicrecognition.IMusicRecognitionManagerCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
