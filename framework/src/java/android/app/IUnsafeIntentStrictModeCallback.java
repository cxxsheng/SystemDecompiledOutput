package android.app;

/* loaded from: classes.dex */
public interface IUnsafeIntentStrictModeCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IUnsafeIntentStrictModeCallback";

    void onImplicitIntentMatchedInternalComponent(android.content.Intent intent) throws android.os.RemoteException;

    public static class Default implements android.app.IUnsafeIntentStrictModeCallback {
        @Override // android.app.IUnsafeIntentStrictModeCallback
        public void onImplicitIntentMatchedInternalComponent(android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IUnsafeIntentStrictModeCallback {
        static final int TRANSACTION_onImplicitIntentMatchedInternalComponent = 1;

        public Stub() {
            attachInterface(this, android.app.IUnsafeIntentStrictModeCallback.DESCRIPTOR);
        }

        public static android.app.IUnsafeIntentStrictModeCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IUnsafeIntentStrictModeCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IUnsafeIntentStrictModeCallback)) {
                return (android.app.IUnsafeIntentStrictModeCallback) queryLocalInterface;
            }
            return new android.app.IUnsafeIntentStrictModeCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onImplicitIntentMatchedInternalComponent";
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
                parcel.enforceInterface(android.app.IUnsafeIntentStrictModeCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IUnsafeIntentStrictModeCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImplicitIntentMatchedInternalComponent(intent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IUnsafeIntentStrictModeCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IUnsafeIntentStrictModeCallback.DESCRIPTOR;
            }

            @Override // android.app.IUnsafeIntentStrictModeCallback
            public void onImplicitIntentMatchedInternalComponent(android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IUnsafeIntentStrictModeCallback.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
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
