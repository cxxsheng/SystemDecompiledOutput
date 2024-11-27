package android.hardware.soundtrigger3;

/* loaded from: classes2.dex */
public interface ISoundTriggerHwCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$soundtrigger3$ISoundTriggerHwCallback".replace('$', '.');
    public static final java.lang.String HASH = "6b24e60ad261e3ff56106efd86ce6aa7ef5621b0";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void modelUnloaded(int i) throws android.os.RemoteException;

    void phraseRecognitionCallback(int i, android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent) throws android.os.RemoteException;

    void recognitionCallback(int i, android.media.soundtrigger.RecognitionEvent recognitionEvent) throws android.os.RemoteException;

    public static class Default implements android.hardware.soundtrigger3.ISoundTriggerHwCallback {
        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public void modelUnloaded(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public void phraseRecognitionCallback(int i, android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public void recognitionCallback(int i, android.media.soundtrigger.RecognitionEvent recognitionEvent) throws android.os.RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.soundtrigger3.ISoundTriggerHwCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_modelUnloaded = 1;
        static final int TRANSACTION_phraseRecognitionCallback = 2;
        static final int TRANSACTION_recognitionCallback = 3;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.soundtrigger3.ISoundTriggerHwCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.soundtrigger3.ISoundTriggerHwCallback)) {
                return (android.hardware.soundtrigger3.ISoundTriggerHwCallback) queryLocalInterface;
            }
            return new android.hardware.soundtrigger3.ISoundTriggerHwCallback.Stub.Proxy(iBinder);
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    modelUnloaded(readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent = (android.media.soundtrigger.PhraseRecognitionEvent) parcel.readTypedObject(android.media.soundtrigger.PhraseRecognitionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    phraseRecognitionCallback(readInt2, phraseRecognitionEvent);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    android.media.soundtrigger.RecognitionEvent recognitionEvent = (android.media.soundtrigger.RecognitionEvent) parcel.readTypedObject(android.media.soundtrigger.RecognitionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    recognitionCallback(readInt3, recognitionEvent);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.soundtrigger3.ISoundTriggerHwCallback {
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

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public void modelUnloaded(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method modelUnloaded is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public void phraseRecognitionCallback(int i, android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(phraseRecognitionEvent, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method phraseRecognitionCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public void recognitionCallback(int i, android.media.soundtrigger.RecognitionEvent recognitionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(recognitionEvent, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method recognitionCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
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

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
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
