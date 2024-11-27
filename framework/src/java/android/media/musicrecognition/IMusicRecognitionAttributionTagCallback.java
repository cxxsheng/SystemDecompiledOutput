package android.media.musicrecognition;

/* loaded from: classes2.dex */
public interface IMusicRecognitionAttributionTagCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionAttributionTagCallback";

    void onAttributionTag(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.media.musicrecognition.IMusicRecognitionAttributionTagCallback {
        @Override // android.media.musicrecognition.IMusicRecognitionAttributionTagCallback
        public void onAttributionTag(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.musicrecognition.IMusicRecognitionAttributionTagCallback {
        static final int TRANSACTION_onAttributionTag = 1;

        public Stub() {
            attachInterface(this, android.media.musicrecognition.IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
        }

        public static android.media.musicrecognition.IMusicRecognitionAttributionTagCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.musicrecognition.IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.musicrecognition.IMusicRecognitionAttributionTagCallback)) {
                return (android.media.musicrecognition.IMusicRecognitionAttributionTagCallback) queryLocalInterface;
            }
            return new android.media.musicrecognition.IMusicRecognitionAttributionTagCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAttributionTag";
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
                parcel.enforceInterface(android.media.musicrecognition.IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.musicrecognition.IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAttributionTag(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.musicrecognition.IMusicRecognitionAttributionTagCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.musicrecognition.IMusicRecognitionAttributionTagCallback.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionAttributionTagCallback
            public void onAttributionTag(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.musicrecognition.IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
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
