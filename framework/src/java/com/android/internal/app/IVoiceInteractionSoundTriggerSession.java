package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IVoiceInteractionSoundTriggerSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionSoundTriggerSession";

    void detach() throws android.os.RemoteException;

    android.hardware.soundtrigger.SoundTrigger.ModuleProperties getDspModuleProperties() throws android.os.RemoteException;

    int getParameter(int i, int i2) throws android.os.RemoteException;

    android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(int i, int i2) throws android.os.RemoteException;

    int setParameter(int i, int i2, int i3) throws android.os.RemoteException;

    int startRecognition(int i, java.lang.String str, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws android.os.RemoteException;

    int stopRecognition(int i, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IVoiceInteractionSoundTriggerSession {
        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public android.hardware.soundtrigger.SoundTrigger.ModuleProperties getDspModuleProperties() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public int startRecognition(int i, java.lang.String str, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public int stopRecognition(int i, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public int setParameter(int i, int i2, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public int getParameter(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
        public void detach() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IVoiceInteractionSoundTriggerSession {
        static final int TRANSACTION_detach = 7;
        static final int TRANSACTION_getDspModuleProperties = 1;
        static final int TRANSACTION_getParameter = 5;
        static final int TRANSACTION_queryParameter = 6;
        static final int TRANSACTION_setParameter = 4;
        static final int TRANSACTION_startRecognition = 2;
        static final int TRANSACTION_stopRecognition = 3;

        public Stub() {
            attachInterface(this, com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
        }

        public static com.android.internal.app.IVoiceInteractionSoundTriggerSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IVoiceInteractionSoundTriggerSession)) {
                return (com.android.internal.app.IVoiceInteractionSoundTriggerSession) queryLocalInterface;
            }
            return new com.android.internal.app.IVoiceInteractionSoundTriggerSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDspModuleProperties";
                case 2:
                    return "startRecognition";
                case 3:
                    return "stopRecognition";
                case 4:
                    return "setParameter";
                case 5:
                    return "getParameter";
                case 6:
                    return "queryParameter";
                case 7:
                    return "detach";
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
                parcel.enforceInterface(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.soundtrigger.SoundTrigger.ModuleProperties dspModuleProperties = getDspModuleProperties();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dspModuleProperties, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    com.android.internal.app.IHotwordRecognitionStatusCallback asInterface = com.android.internal.app.IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig = (android.hardware.soundtrigger.SoundTrigger.RecognitionConfig) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.RecognitionConfig.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startRecognition = startRecognition(readInt, readString, asInterface, recognitionConfig, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(startRecognition);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    com.android.internal.app.IHotwordRecognitionStatusCallback asInterface2 = com.android.internal.app.IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopRecognition = stopRecognition(readInt2, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopRecognition);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter = setParameter(readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int parameter2 = getParameter(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(parameter2);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter = queryParameter(readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryParameter, 1);
                    return true;
                case 7:
                    detach();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IVoiceInteractionSoundTriggerSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public android.hardware.soundtrigger.SoundTrigger.ModuleProperties getDspModuleProperties() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.soundtrigger.SoundTrigger.ModuleProperties) obtain2.readTypedObject(android.hardware.soundtrigger.SoundTrigger.ModuleProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public int startRecognition(int i, java.lang.String str, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public int stopRecognition(int i, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public int setParameter(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public int getParameter(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.soundtrigger.SoundTrigger.ModelParamRange) obtain2.readTypedObject(android.hardware.soundtrigger.SoundTrigger.ModelParamRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionSoundTriggerSession
            public void detach() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IVoiceInteractionSoundTriggerSession.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
