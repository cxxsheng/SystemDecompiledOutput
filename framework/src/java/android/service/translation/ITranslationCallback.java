package android.service.translation;

/* loaded from: classes3.dex */
public interface ITranslationCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.translation.ITranslationCallback";

    void onTranslationResponse(android.view.translation.TranslationResponse translationResponse) throws android.os.RemoteException;

    public static class Default implements android.service.translation.ITranslationCallback {
        @Override // android.service.translation.ITranslationCallback
        public void onTranslationResponse(android.view.translation.TranslationResponse translationResponse) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.translation.ITranslationCallback {
        static final int TRANSACTION_onTranslationResponse = 1;

        public Stub() {
            attachInterface(this, android.service.translation.ITranslationCallback.DESCRIPTOR);
        }

        public static android.service.translation.ITranslationCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.translation.ITranslationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.translation.ITranslationCallback)) {
                return (android.service.translation.ITranslationCallback) queryLocalInterface;
            }
            return new android.service.translation.ITranslationCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTranslationResponse";
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
                parcel.enforceInterface(android.service.translation.ITranslationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.translation.ITranslationCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.translation.TranslationResponse translationResponse = (android.view.translation.TranslationResponse) parcel.readTypedObject(android.view.translation.TranslationResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTranslationResponse(translationResponse);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.translation.ITranslationCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.translation.ITranslationCallback.DESCRIPTOR;
            }

            @Override // android.service.translation.ITranslationCallback
            public void onTranslationResponse(android.view.translation.TranslationResponse translationResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.translation.ITranslationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(translationResponse, 0);
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
