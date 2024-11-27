package com.android.internal.app;

/* loaded from: classes4.dex */
public interface ISoundTriggerSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.app.ISoundTriggerSession";

    void deleteSoundModel(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException;

    int getModelState(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException;

    android.hardware.soundtrigger.SoundTrigger.ModuleProperties getModuleProperties() throws android.os.RemoteException;

    int getParameter(android.os.ParcelUuid parcelUuid, int i) throws android.os.RemoteException;

    android.hardware.soundtrigger.SoundTrigger.GenericSoundModel getSoundModel(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException;

    boolean isRecognitionActive(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException;

    int loadGenericSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) throws android.os.RemoteException;

    int loadKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws android.os.RemoteException;

    android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(android.os.ParcelUuid parcelUuid, int i) throws android.os.RemoteException;

    int setParameter(android.os.ParcelUuid parcelUuid, int i, int i2) throws android.os.RemoteException;

    int startRecognition(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws android.os.RemoteException;

    int startRecognitionForService(android.os.ParcelUuid parcelUuid, android.os.Bundle bundle, android.content.ComponentName componentName, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException;

    int stopRecognition(android.os.ParcelUuid parcelUuid, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback) throws android.os.RemoteException;

    int stopRecognitionForService(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException;

    int unloadSoundModel(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException;

    void updateSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.ISoundTriggerSession {
        @Override // com.android.internal.app.ISoundTriggerSession
        public android.hardware.soundtrigger.SoundTrigger.GenericSoundModel getSoundModel(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public void updateSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public void deleteSoundModel(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int startRecognition(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int stopRecognition(android.os.ParcelUuid parcelUuid, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int loadGenericSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int loadKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int startRecognitionForService(android.os.ParcelUuid parcelUuid, android.os.Bundle bundle, android.content.ComponentName componentName, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int stopRecognitionForService(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int unloadSoundModel(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public boolean isRecognitionActive(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int getModelState(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public android.hardware.soundtrigger.SoundTrigger.ModuleProperties getModuleProperties() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int setParameter(android.os.ParcelUuid parcelUuid, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public int getParameter(android.os.ParcelUuid parcelUuid, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.ISoundTriggerSession
        public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(android.os.ParcelUuid parcelUuid, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.ISoundTriggerSession {
        static final int TRANSACTION_deleteSoundModel = 3;
        static final int TRANSACTION_getModelState = 12;
        static final int TRANSACTION_getModuleProperties = 13;
        static final int TRANSACTION_getParameter = 15;
        static final int TRANSACTION_getSoundModel = 1;
        static final int TRANSACTION_isRecognitionActive = 11;
        static final int TRANSACTION_loadGenericSoundModel = 6;
        static final int TRANSACTION_loadKeyphraseSoundModel = 7;
        static final int TRANSACTION_queryParameter = 16;
        static final int TRANSACTION_setParameter = 14;
        static final int TRANSACTION_startRecognition = 4;
        static final int TRANSACTION_startRecognitionForService = 8;
        static final int TRANSACTION_stopRecognition = 5;
        static final int TRANSACTION_stopRecognitionForService = 9;
        static final int TRANSACTION_unloadSoundModel = 10;
        static final int TRANSACTION_updateSoundModel = 2;

        public Stub() {
            attachInterface(this, com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
        }

        public static com.android.internal.app.ISoundTriggerSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.ISoundTriggerSession)) {
                return (com.android.internal.app.ISoundTriggerSession) queryLocalInterface;
            }
            return new com.android.internal.app.ISoundTriggerSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSoundModel";
                case 2:
                    return "updateSoundModel";
                case 3:
                    return "deleteSoundModel";
                case 4:
                    return "startRecognition";
                case 5:
                    return "stopRecognition";
                case 6:
                    return "loadGenericSoundModel";
                case 7:
                    return "loadKeyphraseSoundModel";
                case 8:
                    return "startRecognitionForService";
                case 9:
                    return "stopRecognitionForService";
                case 10:
                    return "unloadSoundModel";
                case 11:
                    return "isRecognitionActive";
                case 12:
                    return "getModelState";
                case 13:
                    return "getModuleProperties";
                case 14:
                    return "setParameter";
                case 15:
                    return "getParameter";
                case 16:
                    return "queryParameter";
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
                parcel.enforceInterface(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelUuid parcelUuid = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.soundtrigger.SoundTrigger.GenericSoundModel soundModel = getSoundModel(parcelUuid);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(soundModel, 1);
                    return true;
                case 2:
                    android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel = (android.hardware.soundtrigger.SoundTrigger.GenericSoundModel) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSoundModel(genericSoundModel);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.os.ParcelUuid parcelUuid2 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteSoundModel(parcelUuid2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel2 = (android.hardware.soundtrigger.SoundTrigger.GenericSoundModel) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel.CREATOR);
                    android.hardware.soundtrigger.IRecognitionStatusCallback asInterface = android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig = (android.hardware.soundtrigger.SoundTrigger.RecognitionConfig) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.RecognitionConfig.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startRecognition = startRecognition(genericSoundModel2, asInterface, recognitionConfig, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(startRecognition);
                    return true;
                case 5:
                    android.os.ParcelUuid parcelUuid3 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    android.hardware.soundtrigger.IRecognitionStatusCallback asInterface2 = android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopRecognition = stopRecognition(parcelUuid3, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopRecognition);
                    return true;
                case 6:
                    android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel3 = (android.hardware.soundtrigger.SoundTrigger.GenericSoundModel) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int loadGenericSoundModel = loadGenericSoundModel(genericSoundModel3);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadGenericSoundModel);
                    return true;
                case 7:
                    android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = (android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int loadKeyphraseSoundModel = loadKeyphraseSoundModel(keyphraseSoundModel);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadKeyphraseSoundModel);
                    return true;
                case 8:
                    android.os.ParcelUuid parcelUuid4 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig2 = (android.hardware.soundtrigger.SoundTrigger.RecognitionConfig) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.RecognitionConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startRecognitionForService = startRecognitionForService(parcelUuid4, bundle, componentName, recognitionConfig2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startRecognitionForService);
                    return true;
                case 9:
                    android.os.ParcelUuid parcelUuid5 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    int stopRecognitionForService = stopRecognitionForService(parcelUuid5);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopRecognitionForService);
                    return true;
                case 10:
                    android.os.ParcelUuid parcelUuid6 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    int unloadSoundModel = unloadSoundModel(parcelUuid6);
                    parcel2.writeNoException();
                    parcel2.writeInt(unloadSoundModel);
                    return true;
                case 11:
                    android.os.ParcelUuid parcelUuid7 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRecognitionActive = isRecognitionActive(parcelUuid7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRecognitionActive);
                    return true;
                case 12:
                    android.os.ParcelUuid parcelUuid8 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    parcel.enforceNoDataAvail();
                    int modelState = getModelState(parcelUuid8);
                    parcel2.writeNoException();
                    parcel2.writeInt(modelState);
                    return true;
                case 13:
                    android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties = getModuleProperties();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(moduleProperties, 1);
                    return true;
                case 14:
                    android.os.ParcelUuid parcelUuid9 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter = setParameter(parcelUuid9, readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter);
                    return true;
                case 15:
                    android.os.ParcelUuid parcelUuid10 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter2 = getParameter(parcelUuid10, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter2);
                    return true;
                case 16:
                    android.os.ParcelUuid parcelUuid11 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter = queryParameter(parcelUuid11, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryParameter, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.ISoundTriggerSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.ISoundTriggerSession.DESCRIPTOR;
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public android.hardware.soundtrigger.SoundTrigger.GenericSoundModel getSoundModel(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.soundtrigger.SoundTrigger.GenericSoundModel) obtain2.readTypedObject(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public void updateSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(genericSoundModel, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public void deleteSoundModel(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int startRecognition(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(genericSoundModel, 0);
                    obtain.writeStrongInterface(iRecognitionStatusCallback);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int stopRecognition(android.os.ParcelUuid parcelUuid, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeStrongInterface(iRecognitionStatusCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int loadGenericSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(genericSoundModel, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int loadKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(keyphraseSoundModel, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int startRecognitionForService(android.os.ParcelUuid parcelUuid, android.os.Bundle bundle, android.content.ComponentName componentName, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int stopRecognitionForService(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int unloadSoundModel(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public boolean isRecognitionActive(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int getModelState(android.os.ParcelUuid parcelUuid) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public android.hardware.soundtrigger.SoundTrigger.ModuleProperties getModuleProperties() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.soundtrigger.SoundTrigger.ModuleProperties) obtain2.readTypedObject(android.hardware.soundtrigger.SoundTrigger.ModuleProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int setParameter(android.os.ParcelUuid parcelUuid, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public int getParameter(android.os.ParcelUuid parcelUuid, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ISoundTriggerSession
            public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(android.os.ParcelUuid parcelUuid, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ISoundTriggerSession.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.soundtrigger.SoundTrigger.ModelParamRange) obtain2.readTypedObject(android.hardware.soundtrigger.SoundTrigger.ModelParamRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }
    }
}
