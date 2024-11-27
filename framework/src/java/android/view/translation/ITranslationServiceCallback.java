package android.view.translation;

/* loaded from: classes4.dex */
public interface ITranslationServiceCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.translation.ITranslationServiceCallback";

    void updateTranslationCapability(android.view.translation.TranslationCapability translationCapability) throws android.os.RemoteException;

    public static class Default implements android.view.translation.ITranslationServiceCallback {
        @Override // android.view.translation.ITranslationServiceCallback
        public void updateTranslationCapability(android.view.translation.TranslationCapability translationCapability) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.translation.ITranslationServiceCallback {
        static final int TRANSACTION_updateTranslationCapability = 1;

        public Stub() {
            attachInterface(this, android.view.translation.ITranslationServiceCallback.DESCRIPTOR);
        }

        public static android.view.translation.ITranslationServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.translation.ITranslationServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.translation.ITranslationServiceCallback)) {
                return (android.view.translation.ITranslationServiceCallback) queryLocalInterface;
            }
            return new android.view.translation.ITranslationServiceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateTranslationCapability";
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
                parcel.enforceInterface(android.view.translation.ITranslationServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.translation.ITranslationServiceCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.translation.TranslationCapability translationCapability = (android.view.translation.TranslationCapability) parcel.readTypedObject(android.view.translation.TranslationCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateTranslationCapability(translationCapability);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.translation.ITranslationServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.translation.ITranslationServiceCallback.DESCRIPTOR;
            }

            @Override // android.view.translation.ITranslationServiceCallback
            public void updateTranslationCapability(android.view.translation.TranslationCapability translationCapability) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.translation.ITranslationServiceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(translationCapability, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
