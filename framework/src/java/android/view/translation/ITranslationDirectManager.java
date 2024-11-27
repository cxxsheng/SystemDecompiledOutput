package android.view.translation;

/* loaded from: classes4.dex */
public interface ITranslationDirectManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.translation.ITranslationDirectManager";

    void onFinishTranslationSession(int i) throws android.os.RemoteException;

    void onTranslationRequest(android.view.translation.TranslationRequest translationRequest, int i, android.os.ICancellationSignal iCancellationSignal, android.service.translation.ITranslationCallback iTranslationCallback) throws android.os.RemoteException;

    public static class Default implements android.view.translation.ITranslationDirectManager {
        @Override // android.view.translation.ITranslationDirectManager
        public void onTranslationRequest(android.view.translation.TranslationRequest translationRequest, int i, android.os.ICancellationSignal iCancellationSignal, android.service.translation.ITranslationCallback iTranslationCallback) throws android.os.RemoteException {
        }

        @Override // android.view.translation.ITranslationDirectManager
        public void onFinishTranslationSession(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.translation.ITranslationDirectManager {
        static final int TRANSACTION_onFinishTranslationSession = 2;
        static final int TRANSACTION_onTranslationRequest = 1;

        public Stub() {
            attachInterface(this, android.view.translation.ITranslationDirectManager.DESCRIPTOR);
        }

        public static android.view.translation.ITranslationDirectManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.translation.ITranslationDirectManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.translation.ITranslationDirectManager)) {
                return (android.view.translation.ITranslationDirectManager) queryLocalInterface;
            }
            return new android.view.translation.ITranslationDirectManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTranslationRequest";
                case 2:
                    return "onFinishTranslationSession";
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
                parcel.enforceInterface(android.view.translation.ITranslationDirectManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.translation.ITranslationDirectManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.translation.TranslationRequest translationRequest = (android.view.translation.TranslationRequest) parcel.readTypedObject(android.view.translation.TranslationRequest.CREATOR);
                    int readInt = parcel.readInt();
                    android.os.ICancellationSignal asInterface = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    android.service.translation.ITranslationCallback asInterface2 = android.service.translation.ITranslationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onTranslationRequest(translationRequest, readInt, asInterface, asInterface2);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFinishTranslationSession(readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.translation.ITranslationDirectManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.translation.ITranslationDirectManager.DESCRIPTOR;
            }

            @Override // android.view.translation.ITranslationDirectManager
            public void onTranslationRequest(android.view.translation.TranslationRequest translationRequest, int i, android.os.ICancellationSignal iCancellationSignal, android.service.translation.ITranslationCallback iTranslationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationDirectManager.DESCRIPTOR);
                    obtain.writeTypedObject(translationRequest, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeStrongInterface(iTranslationCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationDirectManager
            public void onFinishTranslationSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationDirectManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
