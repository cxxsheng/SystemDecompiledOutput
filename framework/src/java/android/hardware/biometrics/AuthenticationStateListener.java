package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface AuthenticationStateListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.biometrics.AuthenticationStateListener";

    void onAuthenticationAcquired(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i, int i2) throws android.os.RemoteException;

    void onAuthenticationFailed(int i, int i2) throws android.os.RemoteException;

    void onAuthenticationStarted(int i) throws android.os.RemoteException;

    void onAuthenticationStopped() throws android.os.RemoteException;

    void onAuthenticationSucceeded(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.AuthenticationStateListener {
        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationStarted(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationStopped() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationSucceeded(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationFailed(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationAcquired(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.AuthenticationStateListener {
        static final int TRANSACTION_onAuthenticationAcquired = 5;
        static final int TRANSACTION_onAuthenticationFailed = 4;
        static final int TRANSACTION_onAuthenticationStarted = 1;
        static final int TRANSACTION_onAuthenticationStopped = 2;
        static final int TRANSACTION_onAuthenticationSucceeded = 3;

        public Stub() {
            attachInterface(this, android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR);
        }

        public static android.hardware.biometrics.AuthenticationStateListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.AuthenticationStateListener)) {
                return (android.hardware.biometrics.AuthenticationStateListener) queryLocalInterface;
            }
            return new android.hardware.biometrics.AuthenticationStateListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAuthenticationStarted";
                case 2:
                    return "onAuthenticationStopped";
                case 3:
                    return "onAuthenticationSucceeded";
                case 4:
                    return "onAuthenticationFailed";
                case 5:
                    return "onAuthenticationAcquired";
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
                parcel.enforceInterface(android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAuthenticationStarted(readInt);
                    return true;
                case 2:
                    onAuthenticationStopped();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(readInt2, readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAuthenticationFailed(readInt4, readInt5);
                    return true;
                case 5:
                    android.hardware.biometrics.BiometricSourceType biometricSourceType = (android.hardware.biometrics.BiometricSourceType) parcel.readTypedObject(android.hardware.biometrics.BiometricSourceType.CREATOR);
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAuthenticationAcquired(biometricSourceType, readInt6, readInt7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.AuthenticationStateListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationStarted(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationStopped() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationSucceeded(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationFailed(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationAcquired(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeTypedObject(biometricSourceType, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
