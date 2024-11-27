package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface IBiometricContextListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.biometrics.IBiometricContextListener";

    public @interface FoldState {
        public static final int FULLY_CLOSED = 3;
        public static final int FULLY_OPENED = 2;
        public static final int HALF_OPENED = 1;
        public static final int UNKNOWN = 0;
    }

    void onDisplayStateChanged(int i) throws android.os.RemoteException;

    void onFoldChanged(int i) throws android.os.RemoteException;

    void onHardwareIgnoreTouchesChanged(boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.IBiometricContextListener {
        @Override // android.hardware.biometrics.IBiometricContextListener
        public void onFoldChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricContextListener
        public void onDisplayStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricContextListener
        public void onHardwareIgnoreTouchesChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.IBiometricContextListener {
        static final int TRANSACTION_onDisplayStateChanged = 2;
        static final int TRANSACTION_onFoldChanged = 1;
        static final int TRANSACTION_onHardwareIgnoreTouchesChanged = 3;

        public Stub() {
            attachInterface(this, android.hardware.biometrics.IBiometricContextListener.DESCRIPTOR);
        }

        public static android.hardware.biometrics.IBiometricContextListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.biometrics.IBiometricContextListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.IBiometricContextListener)) {
                return (android.hardware.biometrics.IBiometricContextListener) queryLocalInterface;
            }
            return new android.hardware.biometrics.IBiometricContextListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onFoldChanged";
                case 2:
                    return "onDisplayStateChanged";
                case 3:
                    return "onHardwareIgnoreTouchesChanged";
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
                parcel.enforceInterface(android.hardware.biometrics.IBiometricContextListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.biometrics.IBiometricContextListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFoldChanged(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayStateChanged(readInt2);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onHardwareIgnoreTouchesChanged(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.IBiometricContextListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.biometrics.IBiometricContextListener.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricContextListener
            public void onFoldChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricContextListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricContextListener
            public void onDisplayStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricContextListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricContextListener
            public void onHardwareIgnoreTouchesChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricContextListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
