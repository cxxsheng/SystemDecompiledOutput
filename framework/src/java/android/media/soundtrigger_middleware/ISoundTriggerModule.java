package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public interface ISoundTriggerModule extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.soundtrigger_middleware.ISoundTriggerModule";

    void detach() throws android.os.RemoteException;

    void forceRecognitionEvent(int i) throws android.os.RemoteException;

    int getModelParameter(int i, int i2) throws android.os.RemoteException;

    int loadModel(android.media.soundtrigger.SoundModel soundModel) throws android.os.RemoteException;

    int loadPhraseModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel) throws android.os.RemoteException;

    android.media.soundtrigger.ModelParameterRange queryModelParameterSupport(int i, int i2) throws android.os.RemoteException;

    void setModelParameter(int i, int i2, int i3) throws android.os.RemoteException;

    android.os.IBinder startRecognition(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException;

    void stopRecognition(int i) throws android.os.RemoteException;

    void unloadModel(int i) throws android.os.RemoteException;

    public static class Default implements android.media.soundtrigger_middleware.ISoundTriggerModule {
        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public int loadModel(android.media.soundtrigger.SoundModel soundModel) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public int loadPhraseModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void unloadModel(int i) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public android.os.IBinder startRecognition(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void stopRecognition(int i) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void forceRecognitionEvent(int i) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void setModelParameter(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public int getModelParameter(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public android.media.soundtrigger.ModelParameterRange queryModelParameterSupport(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
        public void detach() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.soundtrigger_middleware.ISoundTriggerModule {
        static final int TRANSACTION_detach = 10;
        static final int TRANSACTION_forceRecognitionEvent = 6;
        static final int TRANSACTION_getModelParameter = 8;
        static final int TRANSACTION_loadModel = 1;
        static final int TRANSACTION_loadPhraseModel = 2;
        static final int TRANSACTION_queryModelParameterSupport = 9;
        static final int TRANSACTION_setModelParameter = 7;
        static final int TRANSACTION_startRecognition = 4;
        static final int TRANSACTION_stopRecognition = 5;
        static final int TRANSACTION_unloadModel = 3;

        public Stub() {
            attachInterface(this, android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
        }

        public static android.media.soundtrigger_middleware.ISoundTriggerModule asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.soundtrigger_middleware.ISoundTriggerModule)) {
                return (android.media.soundtrigger_middleware.ISoundTriggerModule) queryLocalInterface;
            }
            return new android.media.soundtrigger_middleware.ISoundTriggerModule.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.soundtrigger.SoundModel soundModel = (android.media.soundtrigger.SoundModel) parcel.readTypedObject(android.media.soundtrigger.SoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int loadModel = loadModel(soundModel);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadModel);
                    return true;
                case 2:
                    android.media.soundtrigger.PhraseSoundModel phraseSoundModel = (android.media.soundtrigger.PhraseSoundModel) parcel.readTypedObject(android.media.soundtrigger.PhraseSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int loadPhraseModel = loadPhraseModel(phraseSoundModel);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadPhraseModel);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unloadModel(readInt);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    android.media.soundtrigger.RecognitionConfig recognitionConfig = (android.media.soundtrigger.RecognitionConfig) parcel.readTypedObject(android.media.soundtrigger.RecognitionConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.IBinder startRecognition = startRecognition(readInt2, recognitionConfig);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(startRecognition);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopRecognition(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRecognitionEvent(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setModelParameter(readInt5, readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int modelParameter = getModelParameter(readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(modelParameter);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.soundtrigger.ModelParameterRange queryModelParameterSupport = queryModelParameterSupport(readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryModelParameterSupport, 1);
                    return true;
                case 10:
                    detach();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.soundtrigger_middleware.ISoundTriggerModule {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public int loadModel(android.media.soundtrigger.SoundModel soundModel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeTypedObject(soundModel, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public int loadPhraseModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeTypedObject(phraseSoundModel, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void unloadModel(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public android.os.IBinder startRecognition(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void stopRecognition(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void forceRecognitionEvent(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void setModelParameter(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public int getModelParameter(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public android.media.soundtrigger.ModelParameterRange queryModelParameterSupport(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.soundtrigger.ModelParameterRange) obtain2.readTypedObject(android.media.soundtrigger.ModelParameterRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerModule
            public void detach() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerModule.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
