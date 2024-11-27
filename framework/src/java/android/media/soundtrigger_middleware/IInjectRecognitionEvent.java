package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public interface IInjectRecognitionEvent extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.soundtrigger_middleware.IInjectRecognitionEvent";

    void triggerAbortRecognition() throws android.os.RemoteException;

    void triggerRecognitionEvent(byte[] bArr, android.media.soundtrigger.PhraseRecognitionExtra[] phraseRecognitionExtraArr) throws android.os.RemoteException;

    public static class Default implements android.media.soundtrigger_middleware.IInjectRecognitionEvent {
        @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
        public void triggerRecognitionEvent(byte[] bArr, android.media.soundtrigger.PhraseRecognitionExtra[] phraseRecognitionExtraArr) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
        public void triggerAbortRecognition() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.soundtrigger_middleware.IInjectRecognitionEvent {
        static final int TRANSACTION_triggerAbortRecognition = 2;
        static final int TRANSACTION_triggerRecognitionEvent = 1;

        public Stub() {
            attachInterface(this, android.media.soundtrigger_middleware.IInjectRecognitionEvent.DESCRIPTOR);
        }

        public static android.media.soundtrigger_middleware.IInjectRecognitionEvent asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.soundtrigger_middleware.IInjectRecognitionEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.soundtrigger_middleware.IInjectRecognitionEvent)) {
                return (android.media.soundtrigger_middleware.IInjectRecognitionEvent) queryLocalInterface;
            }
            return new android.media.soundtrigger_middleware.IInjectRecognitionEvent.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.soundtrigger_middleware.IInjectRecognitionEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.soundtrigger_middleware.IInjectRecognitionEvent.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte[] createByteArray = parcel.createByteArray();
                    android.media.soundtrigger.PhraseRecognitionExtra[] phraseRecognitionExtraArr = (android.media.soundtrigger.PhraseRecognitionExtra[]) parcel.createTypedArray(android.media.soundtrigger.PhraseRecognitionExtra.CREATOR);
                    parcel.enforceNoDataAvail();
                    triggerRecognitionEvent(createByteArray, phraseRecognitionExtraArr);
                    return true;
                case 2:
                    triggerAbortRecognition();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.soundtrigger_middleware.IInjectRecognitionEvent {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.soundtrigger_middleware.IInjectRecognitionEvent.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
            public void triggerRecognitionEvent(byte[] bArr, android.media.soundtrigger.PhraseRecognitionExtra[] phraseRecognitionExtraArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.IInjectRecognitionEvent.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedArray(phraseRecognitionExtraArr, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
            public void triggerAbortRecognition() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.IInjectRecognitionEvent.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
