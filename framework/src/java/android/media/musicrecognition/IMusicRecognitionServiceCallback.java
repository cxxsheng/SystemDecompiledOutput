package android.media.musicrecognition;

/* loaded from: classes2.dex */
public interface IMusicRecognitionServiceCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionServiceCallback";

    void onRecognitionFailed(int i) throws android.os.RemoteException;

    void onRecognitionSucceeded(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.media.musicrecognition.IMusicRecognitionServiceCallback {
        @Override // android.media.musicrecognition.IMusicRecognitionServiceCallback
        public void onRecognitionSucceeded(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.musicrecognition.IMusicRecognitionServiceCallback
        public void onRecognitionFailed(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.musicrecognition.IMusicRecognitionServiceCallback {
        static final int TRANSACTION_onRecognitionFailed = 2;
        static final int TRANSACTION_onRecognitionSucceeded = 1;

        public Stub() {
            attachInterface(this, android.media.musicrecognition.IMusicRecognitionServiceCallback.DESCRIPTOR);
        }

        public static android.media.musicrecognition.IMusicRecognitionServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.musicrecognition.IMusicRecognitionServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.musicrecognition.IMusicRecognitionServiceCallback)) {
                return (android.media.musicrecognition.IMusicRecognitionServiceCallback) queryLocalInterface;
            }
            return new android.media.musicrecognition.IMusicRecognitionServiceCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(android.media.musicrecognition.IMusicRecognitionServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.musicrecognition.IMusicRecognitionServiceCallback.DESCRIPTOR);
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
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.musicrecognition.IMusicRecognitionServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.musicrecognition.IMusicRecognitionServiceCallback.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionServiceCallback
            public void onRecognitionSucceeded(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.musicrecognition.IMusicRecognitionServiceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(mediaMetadata, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.musicrecognition.IMusicRecognitionServiceCallback
            public void onRecognitionFailed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.musicrecognition.IMusicRecognitionServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
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
