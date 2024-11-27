package android.media.musicrecognition;

/* loaded from: classes2.dex */
public interface IMusicRecognitionManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionManager";

    void beginRecognition(android.media.musicrecognition.RecognitionRequest recognitionRequest, android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.media.musicrecognition.IMusicRecognitionManager {
        @Override // android.media.musicrecognition.IMusicRecognitionManager
        public void beginRecognition(android.media.musicrecognition.RecognitionRequest recognitionRequest, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.musicrecognition.IMusicRecognitionManager {
        static final int TRANSACTION_beginRecognition = 1;

        public Stub() {
            attachInterface(this, android.media.musicrecognition.IMusicRecognitionManager.DESCRIPTOR);
        }

        public static android.media.musicrecognition.IMusicRecognitionManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.musicrecognition.IMusicRecognitionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.musicrecognition.IMusicRecognitionManager)) {
                return (android.media.musicrecognition.IMusicRecognitionManager) queryLocalInterface;
            }
            return new android.media.musicrecognition.IMusicRecognitionManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "beginRecognition";
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
                parcel.enforceInterface(android.media.musicrecognition.IMusicRecognitionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.musicrecognition.IMusicRecognitionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.musicrecognition.RecognitionRequest recognitionRequest = (android.media.musicrecognition.RecognitionRequest) parcel.readTypedObject(android.media.musicrecognition.RecognitionRequest.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    beginRecognition(recognitionRequest, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.musicrecognition.IMusicRecognitionManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.musicrecognition.IMusicRecognitionManager.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionManager
            public void beginRecognition(android.media.musicrecognition.RecognitionRequest recognitionRequest, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.musicrecognition.IMusicRecognitionManager.DESCRIPTOR);
                    obtain.writeTypedObject(recognitionRequest, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
