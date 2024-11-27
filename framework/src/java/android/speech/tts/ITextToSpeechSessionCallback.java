package android.speech.tts;

/* loaded from: classes3.dex */
public interface ITextToSpeechSessionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.speech.tts.ITextToSpeechSessionCallback";

    void onConnected(android.speech.tts.ITextToSpeechSession iTextToSpeechSession, android.os.IBinder iBinder) throws android.os.RemoteException;

    void onDisconnected() throws android.os.RemoteException;

    void onError(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.speech.tts.ITextToSpeechSessionCallback {
        @Override // android.speech.tts.ITextToSpeechSessionCallback
        public void onConnected(android.speech.tts.ITextToSpeechSession iTextToSpeechSession, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechSessionCallback
        public void onDisconnected() throws android.os.RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechSessionCallback
        public void onError(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.speech.tts.ITextToSpeechSessionCallback {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onError = 3;

        public Stub() {
            attachInterface(this, android.speech.tts.ITextToSpeechSessionCallback.DESCRIPTOR);
        }

        public static android.speech.tts.ITextToSpeechSessionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.speech.tts.ITextToSpeechSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.speech.tts.ITextToSpeechSessionCallback)) {
                return (android.speech.tts.ITextToSpeechSessionCallback) queryLocalInterface;
            }
            return new android.speech.tts.ITextToSpeechSessionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnected";
                case 2:
                    return "onDisconnected";
                case 3:
                    return "onError";
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
                parcel.enforceInterface(android.speech.tts.ITextToSpeechSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.speech.tts.ITextToSpeechSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.speech.tts.ITextToSpeechSession asInterface = android.speech.tts.ITextToSpeechSession.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onConnected(asInterface, readStrongBinder);
                    return true;
                case 2:
                    onDisconnected();
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onError(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.speech.tts.ITextToSpeechSessionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.speech.tts.ITextToSpeechSessionCallback.DESCRIPTOR;
            }

            @Override // android.speech.tts.ITextToSpeechSessionCallback
            public void onConnected(android.speech.tts.ITextToSpeechSession iTextToSpeechSession, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechSessionCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iTextToSpeechSession);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechSessionCallback
            public void onDisconnected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechSessionCallback
            public void onError(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
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
