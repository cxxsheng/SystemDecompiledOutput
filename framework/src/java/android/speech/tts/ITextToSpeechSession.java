package android.speech.tts;

/* loaded from: classes3.dex */
public interface ITextToSpeechSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.speech.tts.ITextToSpeechSession";

    void disconnect() throws android.os.RemoteException;

    public static class Default implements android.speech.tts.ITextToSpeechSession {
        @Override // android.speech.tts.ITextToSpeechSession
        public void disconnect() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.speech.tts.ITextToSpeechSession {
        static final int TRANSACTION_disconnect = 1;

        public Stub() {
            attachInterface(this, android.speech.tts.ITextToSpeechSession.DESCRIPTOR);
        }

        public static android.speech.tts.ITextToSpeechSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.speech.tts.ITextToSpeechSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.speech.tts.ITextToSpeechSession)) {
                return (android.speech.tts.ITextToSpeechSession) queryLocalInterface;
            }
            return new android.speech.tts.ITextToSpeechSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.media.MediaMetrics.Value.DISCONNECT;
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
                parcel.enforceInterface(android.speech.tts.ITextToSpeechSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.speech.tts.ITextToSpeechSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    disconnect();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.speech.tts.ITextToSpeechSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.speech.tts.ITextToSpeechSession.DESCRIPTOR;
            }

            @Override // android.speech.tts.ITextToSpeechSession
            public void disconnect() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechSession.DESCRIPTOR);
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
