package android.hardware.soundtrigger;

/* loaded from: classes2.dex */
public interface IRecognitionStatusCallback extends android.os.IInterface {
    void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException;

    void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) throws android.os.RemoteException;

    void onModuleDied() throws android.os.RemoteException;

    void onPauseFailed(int i) throws android.os.RemoteException;

    void onPreempted() throws android.os.RemoteException;

    void onRecognitionPaused() throws android.os.RemoteException;

    void onRecognitionResumed() throws android.os.RemoteException;

    void onResumeFailed(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.soundtrigger.IRecognitionStatusCallback {
        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onRecognitionPaused() throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onRecognitionResumed() throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onPreempted() throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onModuleDied() throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onResumeFailed(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onPauseFailed(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.soundtrigger.IRecognitionStatusCallback {
        public static final java.lang.String DESCRIPTOR = "android.hardware.soundtrigger.IRecognitionStatusCallback";
        static final int TRANSACTION_onGenericSoundTriggerDetected = 2;
        static final int TRANSACTION_onKeyphraseDetected = 1;
        static final int TRANSACTION_onModuleDied = 6;
        static final int TRANSACTION_onPauseFailed = 8;
        static final int TRANSACTION_onPreempted = 5;
        static final int TRANSACTION_onRecognitionPaused = 3;
        static final int TRANSACTION_onRecognitionResumed = 4;
        static final int TRANSACTION_onResumeFailed = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.soundtrigger.IRecognitionStatusCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.soundtrigger.IRecognitionStatusCallback)) {
                return (android.hardware.soundtrigger.IRecognitionStatusCallback) queryLocalInterface;
            }
            return new android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onKeyphraseDetected";
                case 2:
                    return "onGenericSoundTriggerDetected";
                case 3:
                    return "onRecognitionPaused";
                case 4:
                    return "onRecognitionResumed";
                case 5:
                    return "onPreempted";
                case 6:
                    return "onModuleDied";
                case 7:
                    return "onResumeFailed";
                case 8:
                    return "onPauseFailed";
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
                    android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent = (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKeyphraseDetected(keyphraseRecognitionEvent);
                    return true;
                case 2:
                    android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent = (android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent) parcel.readTypedObject(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGenericSoundTriggerDetected(genericRecognitionEvent);
                    return true;
                case 3:
                    onRecognitionPaused();
                    return true;
                case 4:
                    onRecognitionResumed();
                    return true;
                case 5:
                    onPreempted();
                    return true;
                case 6:
                    onModuleDied();
                    return true;
                case 7:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onResumeFailed(readInt);
                    return true;
                case 8:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPauseFailed(readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.soundtrigger.IRecognitionStatusCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyphraseRecognitionEvent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(genericRecognitionEvent, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onRecognitionPaused() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onRecognitionResumed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onPreempted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onModuleDied() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onResumeFailed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
            public void onPauseFailed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
