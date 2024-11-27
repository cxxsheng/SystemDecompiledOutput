package android.hardware.soundtrigger3;

/* loaded from: classes2.dex */
public interface ISoundTriggerHw extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$soundtrigger3$ISoundTriggerHw".replace('$', '.');
    public static final java.lang.String HASH = "6b24e60ad261e3ff56106efd86ce6aa7ef5621b0";
    public static final int VERSION = 2;

    void forceRecognitionEvent(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    int getParameter(int i, int i2) throws android.os.RemoteException;

    android.media.soundtrigger.Properties getProperties() throws android.os.RemoteException;

    int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws android.os.RemoteException;

    int loadSoundModel(android.media.soundtrigger.SoundModel soundModel, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws android.os.RemoteException;

    android.media.soundtrigger.ModelParameterRange queryParameter(int i, int i2) throws android.os.RemoteException;

    void registerGlobalCallback(android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) throws android.os.RemoteException;

    void setParameter(int i, int i2, int i3) throws android.os.RemoteException;

    void startRecognition(int i, int i2, int i3, android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException;

    void stopRecognition(int i) throws android.os.RemoteException;

    void unloadSoundModel(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.soundtrigger3.ISoundTriggerHw {
        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public android.media.soundtrigger.Properties getProperties() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void registerGlobalCallback(android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public int loadSoundModel(android.media.soundtrigger.SoundModel soundModel, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void unloadSoundModel(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void startRecognition(int i, int i2, int i3, android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void stopRecognition(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void forceRecognitionEvent(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public android.media.soundtrigger.ModelParameterRange queryParameter(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public int getParameter(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public void setParameter(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHw
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.soundtrigger3.ISoundTriggerHw {
        static final int TRANSACTION_forceRecognitionEvent = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getParameter = 10;
        static final int TRANSACTION_getProperties = 1;
        static final int TRANSACTION_loadPhraseSoundModel = 4;
        static final int TRANSACTION_loadSoundModel = 3;
        static final int TRANSACTION_queryParameter = 9;
        static final int TRANSACTION_registerGlobalCallback = 2;
        static final int TRANSACTION_setParameter = 11;
        static final int TRANSACTION_startRecognition = 6;
        static final int TRANSACTION_stopRecognition = 7;
        static final int TRANSACTION_unloadSoundModel = 5;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.soundtrigger3.ISoundTriggerHw asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.soundtrigger3.ISoundTriggerHw)) {
                return (android.hardware.soundtrigger3.ISoundTriggerHw) queryLocalInterface;
            }
            return new android.hardware.soundtrigger3.ISoundTriggerHw.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    android.media.soundtrigger.Properties properties = getProperties();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(properties, 1);
                    return true;
                case 2:
                    android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback asInterface = android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerGlobalCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.media.soundtrigger.SoundModel soundModel = (android.media.soundtrigger.SoundModel) parcel.readTypedObject(android.media.soundtrigger.SoundModel.CREATOR);
                    android.hardware.soundtrigger3.ISoundTriggerHwCallback asInterface2 = android.hardware.soundtrigger3.ISoundTriggerHwCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int loadSoundModel = loadSoundModel(soundModel, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadSoundModel);
                    return true;
                case 4:
                    android.media.soundtrigger.PhraseSoundModel phraseSoundModel = (android.media.soundtrigger.PhraseSoundModel) parcel.readTypedObject(android.media.soundtrigger.PhraseSoundModel.CREATOR);
                    android.hardware.soundtrigger3.ISoundTriggerHwCallback asInterface3 = android.hardware.soundtrigger3.ISoundTriggerHwCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int loadPhraseSoundModel = loadPhraseSoundModel(phraseSoundModel, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadPhraseSoundModel);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unloadSoundModel(readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    android.media.soundtrigger.RecognitionConfig recognitionConfig = (android.media.soundtrigger.RecognitionConfig) parcel.readTypedObject(android.media.soundtrigger.RecognitionConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    startRecognition(readInt2, readInt3, readInt4, recognitionConfig);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopRecognition(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRecognitionEvent(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.soundtrigger.ModelParameterRange queryParameter = queryParameter(readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryParameter, 1);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter = getParameter(readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter);
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setParameter(readInt11, readInt12, readInt13);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.soundtrigger3.ISoundTriggerHw {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public android.media.soundtrigger.Properties getProperties() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getProperties is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.media.soundtrigger.Properties) obtain2.readTypedObject(android.media.soundtrigger.Properties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void registerGlobalCallback(android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSoundTriggerHwGlobalCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method registerGlobalCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int loadSoundModel(android.media.soundtrigger.SoundModel soundModel, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(soundModel, 0);
                    obtain.writeStrongInterface(iSoundTriggerHwCallback);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method loadSoundModel is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(phraseSoundModel, 0);
                    obtain.writeStrongInterface(iSoundTriggerHwCallback);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method loadPhraseSoundModel is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void unloadSoundModel(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method unloadSoundModel is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void startRecognition(int i, int i2, int i3, android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method startRecognition is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void stopRecognition(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method stopRecognition is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void forceRecognitionEvent(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method forceRecognitionEvent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public android.media.soundtrigger.ModelParameterRange queryParameter(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method queryParameter is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.media.soundtrigger.ModelParameterRange) obtain2.readTypedObject(android.media.soundtrigger.ModelParameterRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int getParameter(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getParameter is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public void setParameter(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setParameter is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHw
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
