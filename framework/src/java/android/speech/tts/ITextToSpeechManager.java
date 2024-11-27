package android.speech.tts;

/* loaded from: classes3.dex */
public interface ITextToSpeechManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.speech.tts.ITextToSpeechManager";

    void createSession(java.lang.String str, android.speech.tts.ITextToSpeechSessionCallback iTextToSpeechSessionCallback) throws android.os.RemoteException;

    public static class Default implements android.speech.tts.ITextToSpeechManager {
        @Override // android.speech.tts.ITextToSpeechManager
        public void createSession(java.lang.String str, android.speech.tts.ITextToSpeechSessionCallback iTextToSpeechSessionCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.speech.tts.ITextToSpeechManager {
        static final int TRANSACTION_createSession = 1;

        public Stub() {
            attachInterface(this, android.speech.tts.ITextToSpeechManager.DESCRIPTOR);
        }

        public static android.speech.tts.ITextToSpeechManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.speech.tts.ITextToSpeechManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.speech.tts.ITextToSpeechManager)) {
                return (android.speech.tts.ITextToSpeechManager) queryLocalInterface;
            }
            return new android.speech.tts.ITextToSpeechManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSession";
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
                parcel.enforceInterface(android.speech.tts.ITextToSpeechManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.speech.tts.ITextToSpeechManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.speech.tts.ITextToSpeechSessionCallback asInterface = android.speech.tts.ITextToSpeechSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createSession(readString, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.speech.tts.ITextToSpeechManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.speech.tts.ITextToSpeechManager.DESCRIPTOR;
            }

            @Override // android.speech.tts.ITextToSpeechManager
            public void createSession(java.lang.String str, android.speech.tts.ITextToSpeechSessionCallback iTextToSpeechSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.tts.ITextToSpeechManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iTextToSpeechSessionCallback);
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
