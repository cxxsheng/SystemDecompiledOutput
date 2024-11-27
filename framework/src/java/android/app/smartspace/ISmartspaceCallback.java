package android.app.smartspace;

/* loaded from: classes.dex */
public interface ISmartspaceCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.smartspace.ISmartspaceCallback";

    void onResult(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    public static class Default implements android.app.smartspace.ISmartspaceCallback {
        @Override // android.app.smartspace.ISmartspaceCallback
        public void onResult(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.smartspace.ISmartspaceCallback {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, android.app.smartspace.ISmartspaceCallback.DESCRIPTOR);
        }

        public static android.app.smartspace.ISmartspaceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.smartspace.ISmartspaceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.smartspace.ISmartspaceCallback)) {
                return (android.app.smartspace.ISmartspaceCallback) queryLocalInterface;
            }
            return new android.app.smartspace.ISmartspaceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onResult";
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
                parcel.enforceInterface(android.app.smartspace.ISmartspaceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.smartspace.ISmartspaceCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResult(parceledListSlice);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.smartspace.ISmartspaceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.smartspace.ISmartspaceCallback.DESCRIPTOR;
            }

            @Override // android.app.smartspace.ISmartspaceCallback
            public void onResult(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.smartspace.ISmartspaceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
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
