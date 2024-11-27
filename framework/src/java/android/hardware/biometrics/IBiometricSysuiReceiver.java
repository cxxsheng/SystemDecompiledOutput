package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface IBiometricSysuiReceiver extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.biometrics.IBiometricSysuiReceiver";

    void onDeviceCredentialPressed() throws android.os.RemoteException;

    void onDialogAnimatedIn(boolean z) throws android.os.RemoteException;

    void onDialogDismissed(int i, byte[] bArr) throws android.os.RemoteException;

    void onStartFingerprintNow() throws android.os.RemoteException;

    void onSystemEvent(int i) throws android.os.RemoteException;

    void onTryAgainPressed() throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.IBiometricSysuiReceiver {
        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onDialogDismissed(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onTryAgainPressed() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onDeviceCredentialPressed() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onSystemEvent(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onDialogAnimatedIn(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onStartFingerprintNow() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.IBiometricSysuiReceiver {
        static final int TRANSACTION_onDeviceCredentialPressed = 3;
        static final int TRANSACTION_onDialogAnimatedIn = 5;
        static final int TRANSACTION_onDialogDismissed = 1;
        static final int TRANSACTION_onStartFingerprintNow = 6;
        static final int TRANSACTION_onSystemEvent = 4;
        static final int TRANSACTION_onTryAgainPressed = 2;

        public Stub() {
            attachInterface(this, android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
        }

        public static android.hardware.biometrics.IBiometricSysuiReceiver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.IBiometricSysuiReceiver)) {
                return (android.hardware.biometrics.IBiometricSysuiReceiver) queryLocalInterface;
            }
            return new android.hardware.biometrics.IBiometricSysuiReceiver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDialogDismissed";
                case 2:
                    return "onTryAgainPressed";
                case 3:
                    return "onDeviceCredentialPressed";
                case 4:
                    return "onSystemEvent";
                case 5:
                    return "onDialogAnimatedIn";
                case 6:
                    return "onStartFingerprintNow";
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
                parcel.enforceInterface(android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onDialogDismissed(readInt, createByteArray);
                    return true;
                case 2:
                    onTryAgainPressed();
                    return true;
                case 3:
                    onDeviceCredentialPressed();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSystemEvent(readInt2);
                    return true;
                case 5:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onDialogAnimatedIn(readBoolean);
                    return true;
                case 6:
                    onStartFingerprintNow();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.IBiometricSysuiReceiver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onDialogDismissed(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onTryAgainPressed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onDeviceCredentialPressed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onSystemEvent(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onDialogAnimatedIn(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onStartFingerprintNow() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricSysuiReceiver.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
