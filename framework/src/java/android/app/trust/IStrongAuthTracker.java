package android.app.trust;

/* loaded from: classes.dex */
public interface IStrongAuthTracker extends android.os.IInterface {
    void onIsNonStrongBiometricAllowedChanged(boolean z, int i) throws android.os.RemoteException;

    void onStrongAuthRequiredChanged(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.app.trust.IStrongAuthTracker {
        @Override // android.app.trust.IStrongAuthTracker
        public void onStrongAuthRequiredChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.trust.IStrongAuthTracker
        public void onIsNonStrongBiometricAllowedChanged(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.trust.IStrongAuthTracker {
        public static final java.lang.String DESCRIPTOR = "android.app.trust.IStrongAuthTracker";
        static final int TRANSACTION_onIsNonStrongBiometricAllowedChanged = 2;
        static final int TRANSACTION_onStrongAuthRequiredChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.trust.IStrongAuthTracker asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.trust.IStrongAuthTracker)) {
                return (android.app.trust.IStrongAuthTracker) queryLocalInterface;
            }
            return new android.app.trust.IStrongAuthTracker.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStrongAuthRequiredChanged";
                case 2:
                    return "onIsNonStrongBiometricAllowedChanged";
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStrongAuthRequiredChanged(readInt, readInt2);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onIsNonStrongBiometricAllowedChanged(readBoolean, readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.trust.IStrongAuthTracker {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.trust.IStrongAuthTracker.Stub.DESCRIPTOR;
            }

            @Override // android.app.trust.IStrongAuthTracker
            public void onStrongAuthRequiredChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.trust.IStrongAuthTracker.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.IStrongAuthTracker
            public void onIsNonStrongBiometricAllowedChanged(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.trust.IStrongAuthTracker.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
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
