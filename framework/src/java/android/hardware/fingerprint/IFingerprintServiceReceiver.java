package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public interface IFingerprintServiceReceiver extends android.os.IInterface {
    void onAcquired(int i, int i2) throws android.os.RemoteException;

    void onAuthenticationFailed() throws android.os.RemoteException;

    void onAuthenticationSucceeded(android.hardware.fingerprint.Fingerprint fingerprint, int i, boolean z) throws android.os.RemoteException;

    void onChallengeGenerated(int i, int i2, long j) throws android.os.RemoteException;

    void onEnrollResult(android.hardware.fingerprint.Fingerprint fingerprint, int i) throws android.os.RemoteException;

    void onError(int i, int i2) throws android.os.RemoteException;

    void onFingerprintDetected(int i, int i2, boolean z) throws android.os.RemoteException;

    void onRemoved(android.hardware.fingerprint.Fingerprint fingerprint, int i) throws android.os.RemoteException;

    void onUdfpsOverlayShown() throws android.os.RemoteException;

    void onUdfpsPointerDown(int i) throws android.os.RemoteException;

    void onUdfpsPointerUp(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.fingerprint.IFingerprintServiceReceiver {
        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(android.hardware.fingerprint.Fingerprint fingerprint, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(android.hardware.fingerprint.Fingerprint fingerprint, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onFingerprintDetected(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed() throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(android.hardware.fingerprint.Fingerprint fingerprint, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onChallengeGenerated(int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerDown(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerUp(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsOverlayShown() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.fingerprint.IFingerprintServiceReceiver {
        public static final java.lang.String DESCRIPTOR = "android.hardware.fingerprint.IFingerprintServiceReceiver";
        static final int TRANSACTION_onAcquired = 2;
        static final int TRANSACTION_onAuthenticationFailed = 5;
        static final int TRANSACTION_onAuthenticationSucceeded = 3;
        static final int TRANSACTION_onChallengeGenerated = 8;
        static final int TRANSACTION_onEnrollResult = 1;
        static final int TRANSACTION_onError = 6;
        static final int TRANSACTION_onFingerprintDetected = 4;
        static final int TRANSACTION_onRemoved = 7;
        static final int TRANSACTION_onUdfpsOverlayShown = 11;
        static final int TRANSACTION_onUdfpsPointerDown = 9;
        static final int TRANSACTION_onUdfpsPointerUp = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.fingerprint.IFingerprintServiceReceiver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.fingerprint.IFingerprintServiceReceiver)) {
                return (android.hardware.fingerprint.IFingerprintServiceReceiver) queryLocalInterface;
            }
            return new android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEnrollResult";
                case 2:
                    return "onAcquired";
                case 3:
                    return "onAuthenticationSucceeded";
                case 4:
                    return "onFingerprintDetected";
                case 5:
                    return "onAuthenticationFailed";
                case 6:
                    return "onError";
                case 7:
                    return "onRemoved";
                case 8:
                    return "onChallengeGenerated";
                case 9:
                    return "onUdfpsPointerDown";
                case 10:
                    return "onUdfpsPointerUp";
                case 11:
                    return "onUdfpsOverlayShown";
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
                    android.hardware.fingerprint.Fingerprint fingerprint = (android.hardware.fingerprint.Fingerprint) parcel.readTypedObject(android.hardware.fingerprint.Fingerprint.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollResult(fingerprint, readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAcquired(readInt2, readInt3);
                    return true;
                case 3:
                    android.hardware.fingerprint.Fingerprint fingerprint2 = (android.hardware.fingerprint.Fingerprint) parcel.readTypedObject(android.hardware.fingerprint.Fingerprint.CREATOR);
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(fingerprint2, readInt4, readBoolean);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFingerprintDetected(readInt5, readInt6, readBoolean2);
                    return true;
                case 5:
                    onAuthenticationFailed();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt7, readInt8);
                    return true;
                case 7:
                    android.hardware.fingerprint.Fingerprint fingerprint3 = (android.hardware.fingerprint.Fingerprint) parcel.readTypedObject(android.hardware.fingerprint.Fingerprint.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoved(fingerprint3, readInt9);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChallengeGenerated(readInt10, readInt11, readLong);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUdfpsPointerDown(readInt12);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUdfpsPointerUp(readInt13);
                    return true;
                case 11:
                    onUdfpsOverlayShown();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.fingerprint.IFingerprintServiceReceiver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onEnrollResult(android.hardware.fingerprint.Fingerprint fingerprint, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fingerprint, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onAcquired(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onAuthenticationSucceeded(android.hardware.fingerprint.Fingerprint fingerprint, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fingerprint, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onFingerprintDetected(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onAuthenticationFailed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onError(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onRemoved(android.hardware.fingerprint.Fingerprint fingerprint, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fingerprint, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onChallengeGenerated(int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onUdfpsPointerDown(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onUdfpsPointerUp(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onUdfpsOverlayShown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
