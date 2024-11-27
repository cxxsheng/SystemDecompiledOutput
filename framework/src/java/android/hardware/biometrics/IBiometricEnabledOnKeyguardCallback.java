package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface IBiometricEnabledOnKeyguardCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback";

    void onChanged(boolean z, int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback {
        @Override // android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback
        public void onChanged(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback {
        static final int TRANSACTION_onChanged = 1;

        public Stub() {
            attachInterface(this, android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
        }

        public static android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback)) {
                return (android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback) queryLocalInterface;
            }
            return new android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onChanged";
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
                parcel.enforceInterface(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onChanged(readBoolean, readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback
            public void onChanged(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
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
