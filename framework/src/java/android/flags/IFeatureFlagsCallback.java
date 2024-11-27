package android.flags;

/* loaded from: classes.dex */
public interface IFeatureFlagsCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.flags.IFeatureFlagsCallback";

    void onFlagChange(android.flags.SyncableFlag syncableFlag) throws android.os.RemoteException;

    public static class Default implements android.flags.IFeatureFlagsCallback {
        @Override // android.flags.IFeatureFlagsCallback
        public void onFlagChange(android.flags.SyncableFlag syncableFlag) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.flags.IFeatureFlagsCallback {
        static final int TRANSACTION_onFlagChange = 1;

        public Stub() {
            attachInterface(this, android.flags.IFeatureFlagsCallback.DESCRIPTOR);
        }

        public static android.flags.IFeatureFlagsCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.flags.IFeatureFlagsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.flags.IFeatureFlagsCallback)) {
                return (android.flags.IFeatureFlagsCallback) queryLocalInterface;
            }
            return new android.flags.IFeatureFlagsCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onFlagChange";
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
                parcel.enforceInterface(android.flags.IFeatureFlagsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.flags.IFeatureFlagsCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.flags.SyncableFlag syncableFlag = (android.flags.SyncableFlag) parcel.readTypedObject(android.flags.SyncableFlag.CREATOR);
                    parcel.enforceNoDataAvail();
                    onFlagChange(syncableFlag);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.flags.IFeatureFlagsCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.flags.IFeatureFlagsCallback.DESCRIPTOR;
            }

            @Override // android.flags.IFeatureFlagsCallback
            public void onFlagChange(android.flags.SyncableFlag syncableFlag) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.flags.IFeatureFlagsCallback.DESCRIPTOR);
                    obtain.writeTypedObject(syncableFlag, 0);
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
