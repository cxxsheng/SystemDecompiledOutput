package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public interface ISoundTriggerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.soundtrigger_middleware.ISoundTriggerCallback";

    void onModelUnloaded(int i) throws android.os.RemoteException;

    void onModuleDied() throws android.os.RemoteException;

    void onPhraseRecognition(int i, android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws android.os.RemoteException;

    void onRecognition(int i, android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys, int i2) throws android.os.RemoteException;

    void onResourcesAvailable() throws android.os.RemoteException;

    public static class Default implements android.media.soundtrigger_middleware.ISoundTriggerCallback {
        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onRecognition(int i, android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onPhraseRecognition(int i, android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onResourcesAvailable() throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onModelUnloaded(int i) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onModuleDied() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.soundtrigger_middleware.ISoundTriggerCallback {
        static final int TRANSACTION_onModelUnloaded = 4;
        static final int TRANSACTION_onModuleDied = 5;
        static final int TRANSACTION_onPhraseRecognition = 2;
        static final int TRANSACTION_onRecognition = 1;
        static final int TRANSACTION_onResourcesAvailable = 3;

        public Stub() {
            attachInterface(this, android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR);
        }

        public static android.media.soundtrigger_middleware.ISoundTriggerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.soundtrigger_middleware.ISoundTriggerCallback)) {
                return (android.media.soundtrigger_middleware.ISoundTriggerCallback) queryLocalInterface;
            }
            return new android.media.soundtrigger_middleware.ISoundTriggerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys = (android.media.soundtrigger_middleware.RecognitionEventSys) parcel.readTypedObject(android.media.soundtrigger_middleware.RecognitionEventSys.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecognition(readInt, recognitionEventSys, readInt2);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys = (android.media.soundtrigger_middleware.PhraseRecognitionEventSys) parcel.readTypedObject(android.media.soundtrigger_middleware.PhraseRecognitionEventSys.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPhraseRecognition(readInt3, phraseRecognitionEventSys, readInt4);
                    return true;
                case 3:
                    onResourcesAvailable();
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onModelUnloaded(readInt5);
                    return true;
                case 5:
                    onModuleDied();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.soundtrigger_middleware.ISoundTriggerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onRecognition(int i, android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(recognitionEventSys, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onPhraseRecognition(int i, android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(phraseRecognitionEventSys, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onResourcesAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onModelUnloaded(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
            public void onModuleDied() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
