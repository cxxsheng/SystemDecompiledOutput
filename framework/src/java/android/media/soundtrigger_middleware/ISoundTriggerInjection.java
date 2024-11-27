package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public interface ISoundTriggerInjection extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.soundtrigger_middleware.ISoundTriggerInjection";
    public static final java.lang.String FAKE_HAL_ARCH = "injection";

    void onClientAttached(android.os.IBinder iBinder, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException;

    void onClientDetached(android.os.IBinder iBinder) throws android.os.RemoteException;

    void onFrameworkDetached(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException;

    void onParamSet(int i, int i2, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) throws android.os.RemoteException;

    void onPreempted() throws android.os.RemoteException;

    void onRecognitionStarted(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig, android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) throws android.os.RemoteException;

    void onRecognitionStopped(android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent) throws android.os.RemoteException;

    void onRestarted(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException;

    void onSoundModelLoaded(android.media.soundtrigger.SoundModel soundModel, android.media.soundtrigger.Phrase[] phraseArr, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException;

    void onSoundModelUnloaded(android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) throws android.os.RemoteException;

    void registerGlobalEventInjection(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException;

    public static class Default implements android.media.soundtrigger_middleware.ISoundTriggerInjection {
        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void registerGlobalEventInjection(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRestarted(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onFrameworkDetached(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientAttached(android.os.IBinder iBinder, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientDetached(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelLoaded(android.media.soundtrigger.SoundModel soundModel, android.media.soundtrigger.Phrase[] phraseArr, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onParamSet(int i, int i2, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStarted(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig, android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStopped(android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelUnloaded(android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onPreempted() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.soundtrigger_middleware.ISoundTriggerInjection {
        static final int TRANSACTION_onClientAttached = 4;
        static final int TRANSACTION_onClientDetached = 5;
        static final int TRANSACTION_onFrameworkDetached = 3;
        static final int TRANSACTION_onParamSet = 7;
        static final int TRANSACTION_onPreempted = 11;
        static final int TRANSACTION_onRecognitionStarted = 8;
        static final int TRANSACTION_onRecognitionStopped = 9;
        static final int TRANSACTION_onRestarted = 2;
        static final int TRANSACTION_onSoundModelLoaded = 6;
        static final int TRANSACTION_onSoundModelUnloaded = 10;
        static final int TRANSACTION_registerGlobalEventInjection = 1;

        public Stub() {
            attachInterface(this, android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
        }

        public static android.media.soundtrigger_middleware.ISoundTriggerInjection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.soundtrigger_middleware.ISoundTriggerInjection)) {
                return (android.media.soundtrigger_middleware.ISoundTriggerInjection) queryLocalInterface;
            }
            return new android.media.soundtrigger_middleware.ISoundTriggerInjection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.soundtrigger_middleware.IInjectGlobalEvent asInterface = android.media.soundtrigger_middleware.IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerGlobalEventInjection(asInterface);
                    return true;
                case 2:
                    android.media.soundtrigger_middleware.IInjectGlobalEvent asInterface2 = android.media.soundtrigger_middleware.IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRestarted(asInterface2);
                    return true;
                case 3:
                    android.media.soundtrigger_middleware.IInjectGlobalEvent asInterface3 = android.media.soundtrigger_middleware.IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onFrameworkDetached(asInterface3);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.media.soundtrigger_middleware.IInjectGlobalEvent asInterface4 = android.media.soundtrigger_middleware.IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onClientAttached(readStrongBinder, asInterface4);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onClientDetached(readStrongBinder2);
                    return true;
                case 6:
                    android.media.soundtrigger.SoundModel soundModel = (android.media.soundtrigger.SoundModel) parcel.readTypedObject(android.media.soundtrigger.SoundModel.CREATOR);
                    android.media.soundtrigger.Phrase[] phraseArr = (android.media.soundtrigger.Phrase[]) parcel.createTypedArray(android.media.soundtrigger.Phrase.CREATOR);
                    android.media.soundtrigger_middleware.IInjectModelEvent asInterface5 = android.media.soundtrigger_middleware.IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    android.media.soundtrigger_middleware.IInjectGlobalEvent asInterface6 = android.media.soundtrigger_middleware.IInjectGlobalEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSoundModelLoaded(soundModel, phraseArr, asInterface5, asInterface6);
                    return true;
                case 7:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.media.soundtrigger_middleware.IInjectModelEvent asInterface7 = android.media.soundtrigger_middleware.IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onParamSet(readInt, readInt2, asInterface7);
                    return true;
                case 8:
                    int readInt3 = parcel.readInt();
                    android.media.soundtrigger.RecognitionConfig recognitionConfig = (android.media.soundtrigger.RecognitionConfig) parcel.readTypedObject(android.media.soundtrigger.RecognitionConfig.CREATOR);
                    android.media.soundtrigger_middleware.IInjectRecognitionEvent asInterface8 = android.media.soundtrigger_middleware.IInjectRecognitionEvent.Stub.asInterface(parcel.readStrongBinder());
                    android.media.soundtrigger_middleware.IInjectModelEvent asInterface9 = android.media.soundtrigger_middleware.IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRecognitionStarted(readInt3, recognitionConfig, asInterface8, asInterface9);
                    return true;
                case 9:
                    android.media.soundtrigger_middleware.IInjectRecognitionEvent asInterface10 = android.media.soundtrigger_middleware.IInjectRecognitionEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRecognitionStopped(asInterface10);
                    return true;
                case 10:
                    android.media.soundtrigger_middleware.IInjectModelEvent asInterface11 = android.media.soundtrigger_middleware.IInjectModelEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSoundModelUnloaded(asInterface11);
                    return true;
                case 11:
                    onPreempted();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.soundtrigger_middleware.ISoundTriggerInjection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void registerGlobalEventInjection(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onRestarted(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onFrameworkDetached(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onClientAttached(android.os.IBinder iBinder, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onClientDetached(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onSoundModelLoaded(android.media.soundtrigger.SoundModel soundModel, android.media.soundtrigger.Phrase[] phraseArr, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeTypedObject(soundModel, 0);
                    obtain.writeTypedArray(phraseArr, 0);
                    obtain.writeStrongInterface(iInjectModelEvent);
                    obtain.writeStrongInterface(iInjectGlobalEvent);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onParamSet(int i, int i2, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInjectModelEvent);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onRecognitionStarted(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig, android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(recognitionConfig, 0);
                    obtain.writeStrongInterface(iInjectRecognitionEvent);
                    obtain.writeStrongInterface(iInjectModelEvent);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onRecognitionStopped(android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectRecognitionEvent);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onSoundModelUnloaded(android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iInjectModelEvent);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
            public void onPreempted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.ISoundTriggerInjection.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
