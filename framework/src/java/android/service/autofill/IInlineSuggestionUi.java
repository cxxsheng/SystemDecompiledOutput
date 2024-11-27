package android.service.autofill;

/* loaded from: classes3.dex */
public interface IInlineSuggestionUi extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.autofill.IInlineSuggestionUi";

    void getSurfacePackage(android.service.autofill.ISurfacePackageResultCallback iSurfacePackageResultCallback) throws android.os.RemoteException;

    void releaseSurfaceControlViewHost() throws android.os.RemoteException;

    public static class Default implements android.service.autofill.IInlineSuggestionUi {
        @Override // android.service.autofill.IInlineSuggestionUi
        public void getSurfacePackage(android.service.autofill.ISurfacePackageResultCallback iSurfacePackageResultCallback) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUi
        public void releaseSurfaceControlViewHost() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.autofill.IInlineSuggestionUi {
        static final int TRANSACTION_getSurfacePackage = 1;
        static final int TRANSACTION_releaseSurfaceControlViewHost = 2;

        public Stub() {
            attachInterface(this, android.service.autofill.IInlineSuggestionUi.DESCRIPTOR);
        }

        public static android.service.autofill.IInlineSuggestionUi asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.autofill.IInlineSuggestionUi.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.autofill.IInlineSuggestionUi)) {
                return (android.service.autofill.IInlineSuggestionUi) queryLocalInterface;
            }
            return new android.service.autofill.IInlineSuggestionUi.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSurfacePackage";
                case 2:
                    return "releaseSurfaceControlViewHost";
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
                parcel.enforceInterface(android.service.autofill.IInlineSuggestionUi.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.autofill.IInlineSuggestionUi.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.autofill.ISurfacePackageResultCallback asInterface = android.service.autofill.ISurfacePackageResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getSurfacePackage(asInterface);
                    return true;
                case 2:
                    releaseSurfaceControlViewHost();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.autofill.IInlineSuggestionUi {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.autofill.IInlineSuggestionUi.DESCRIPTOR;
            }

            @Override // android.service.autofill.IInlineSuggestionUi
            public void getSurfacePackage(android.service.autofill.ISurfacePackageResultCallback iSurfacePackageResultCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionUi.DESCRIPTOR);
                    obtain.writeStrongInterface(iSurfacePackageResultCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUi
            public void releaseSurfaceControlViewHost() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IInlineSuggestionUi.DESCRIPTOR);
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
