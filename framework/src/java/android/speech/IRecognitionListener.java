package android.speech;

/* loaded from: classes3.dex */
public interface IRecognitionListener extends android.os.IInterface {
    void onBeginningOfSpeech() throws android.os.RemoteException;

    void onBufferReceived(byte[] bArr) throws android.os.RemoteException;

    void onEndOfSegmentedSession() throws android.os.RemoteException;

    void onEndOfSpeech() throws android.os.RemoteException;

    void onError(int i) throws android.os.RemoteException;

    void onEvent(int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void onLanguageDetection(android.os.Bundle bundle) throws android.os.RemoteException;

    void onPartialResults(android.os.Bundle bundle) throws android.os.RemoteException;

    void onReadyForSpeech(android.os.Bundle bundle) throws android.os.RemoteException;

    void onResults(android.os.Bundle bundle) throws android.os.RemoteException;

    void onRmsChanged(float f) throws android.os.RemoteException;

    void onSegmentResults(android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.speech.IRecognitionListener {
        @Override // android.speech.IRecognitionListener
        public void onReadyForSpeech(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onBeginningOfSpeech() throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onRmsChanged(float f) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onBufferReceived(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onEndOfSpeech() throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onError(int i) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onResults(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onPartialResults(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onSegmentResults(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onEndOfSegmentedSession() throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onLanguageDetection(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionListener
        public void onEvent(int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.speech.IRecognitionListener {
        public static final java.lang.String DESCRIPTOR = "android.speech.IRecognitionListener";
        static final int TRANSACTION_onBeginningOfSpeech = 2;
        static final int TRANSACTION_onBufferReceived = 4;
        static final int TRANSACTION_onEndOfSegmentedSession = 10;
        static final int TRANSACTION_onEndOfSpeech = 5;
        static final int TRANSACTION_onError = 6;
        static final int TRANSACTION_onEvent = 12;
        static final int TRANSACTION_onLanguageDetection = 11;
        static final int TRANSACTION_onPartialResults = 8;
        static final int TRANSACTION_onReadyForSpeech = 1;
        static final int TRANSACTION_onResults = 7;
        static final int TRANSACTION_onRmsChanged = 3;
        static final int TRANSACTION_onSegmentResults = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.speech.IRecognitionListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.speech.IRecognitionListener)) {
                return (android.speech.IRecognitionListener) queryLocalInterface;
            }
            return new android.speech.IRecognitionListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onReadyForSpeech";
                case 2:
                    return "onBeginningOfSpeech";
                case 3:
                    return "onRmsChanged";
                case 4:
                    return "onBufferReceived";
                case 5:
                    return "onEndOfSpeech";
                case 6:
                    return "onError";
                case 7:
                    return "onResults";
                case 8:
                    return "onPartialResults";
                case 9:
                    return "onSegmentResults";
                case 10:
                    return "onEndOfSegmentedSession";
                case 11:
                    return "onLanguageDetection";
                case 12:
                    return "onEvent";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onReadyForSpeech(bundle);
                    return true;
                case 2:
                    onBeginningOfSpeech();
                    return true;
                case 3:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onRmsChanged(readFloat);
                    return true;
                case 4:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onBufferReceived(createByteArray);
                    return true;
                case 5:
                    onEndOfSpeech();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt);
                    return true;
                case 7:
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResults(bundle2);
                    return true;
                case 8:
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPartialResults(bundle3);
                    return true;
                case 9:
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSegmentResults(bundle4);
                    return true;
                case 10:
                    onEndOfSegmentedSession();
                    return true;
                case 11:
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onLanguageDetection(bundle5);
                    return true;
                case 12:
                    int readInt2 = parcel.readInt();
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEvent(readInt2, bundle6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.speech.IRecognitionListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.speech.IRecognitionListener.Stub.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionListener
            public void onReadyForSpeech(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onBeginningOfSpeech() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onRmsChanged(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onBufferReceived(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onEndOfSpeech() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onError(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onResults(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onPartialResults(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onSegmentResults(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onEndOfSegmentedSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onLanguageDetection(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionListener
            public void onEvent(int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
