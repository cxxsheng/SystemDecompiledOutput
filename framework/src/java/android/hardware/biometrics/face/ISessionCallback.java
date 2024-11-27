package android.hardware.biometrics.face;

/* loaded from: classes.dex */
public interface ISessionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$biometrics$face$ISessionCallback".replace('$', '.');
    public static final java.lang.String HASH = "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
    public static final int VERSION = 4;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onAuthenticationFailed() throws android.os.RemoteException;

    void onAuthenticationFrame(android.hardware.biometrics.face.AuthenticationFrame authenticationFrame) throws android.os.RemoteException;

    void onAuthenticationSucceeded(int i, android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException;

    void onAuthenticatorIdInvalidated(long j) throws android.os.RemoteException;

    void onAuthenticatorIdRetrieved(long j) throws android.os.RemoteException;

    void onChallengeGenerated(long j) throws android.os.RemoteException;

    void onChallengeRevoked(long j) throws android.os.RemoteException;

    void onEnrollmentFrame(android.hardware.biometrics.face.EnrollmentFrame enrollmentFrame) throws android.os.RemoteException;

    void onEnrollmentProgress(int i, int i2) throws android.os.RemoteException;

    void onEnrollmentsEnumerated(int[] iArr) throws android.os.RemoteException;

    void onEnrollmentsRemoved(int[] iArr) throws android.os.RemoteException;

    void onError(byte b, int i) throws android.os.RemoteException;

    void onFeatureSet(byte b) throws android.os.RemoteException;

    void onFeaturesRetrieved(byte[] bArr) throws android.os.RemoteException;

    void onInteractionDetected() throws android.os.RemoteException;

    void onLockoutCleared() throws android.os.RemoteException;

    void onLockoutPermanent() throws android.os.RemoteException;

    void onLockoutTimed(long j) throws android.os.RemoteException;

    void onSessionClosed() throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.face.ISessionCallback {
        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onChallengeGenerated(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onChallengeRevoked(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticationFrame(android.hardware.biometrics.face.AuthenticationFrame authenticationFrame) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentFrame(android.hardware.biometrics.face.EnrollmentFrame enrollmentFrame) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onError(byte b, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentProgress(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticationSucceeded(int i, android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticationFailed() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onLockoutTimed(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onLockoutPermanent() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onLockoutCleared() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onInteractionDetected() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentsEnumerated(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onFeaturesRetrieved(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onFeatureSet(byte b) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentsRemoved(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticatorIdRetrieved(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticatorIdInvalidated(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onSessionClosed() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.face.ISessionCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onAuthenticationFailed = 8;
        static final int TRANSACTION_onAuthenticationFrame = 3;
        static final int TRANSACTION_onAuthenticationSucceeded = 7;
        static final int TRANSACTION_onAuthenticatorIdInvalidated = 18;
        static final int TRANSACTION_onAuthenticatorIdRetrieved = 17;
        static final int TRANSACTION_onChallengeGenerated = 1;
        static final int TRANSACTION_onChallengeRevoked = 2;
        static final int TRANSACTION_onEnrollmentFrame = 4;
        static final int TRANSACTION_onEnrollmentProgress = 6;
        static final int TRANSACTION_onEnrollmentsEnumerated = 13;
        static final int TRANSACTION_onEnrollmentsRemoved = 16;
        static final int TRANSACTION_onError = 5;
        static final int TRANSACTION_onFeatureSet = 15;
        static final int TRANSACTION_onFeaturesRetrieved = 14;
        static final int TRANSACTION_onInteractionDetected = 12;
        static final int TRANSACTION_onLockoutCleared = 11;
        static final int TRANSACTION_onLockoutPermanent = 10;
        static final int TRANSACTION_onLockoutTimed = 9;
        static final int TRANSACTION_onSessionClosed = 19;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.biometrics.face.ISessionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.face.ISessionCallback)) {
                return (android.hardware.biometrics.face.ISessionCallback) queryLocalInterface;
            }
            return new android.hardware.biometrics.face.ISessionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onChallengeGenerated";
                case 2:
                    return "onChallengeRevoked";
                case 3:
                    return "onAuthenticationFrame";
                case 4:
                    return "onEnrollmentFrame";
                case 5:
                    return "onError";
                case 6:
                    return "onEnrollmentProgress";
                case 7:
                    return "onAuthenticationSucceeded";
                case 8:
                    return "onAuthenticationFailed";
                case 9:
                    return "onLockoutTimed";
                case 10:
                    return "onLockoutPermanent";
                case 11:
                    return "onLockoutCleared";
                case 12:
                    return "onInteractionDetected";
                case 13:
                    return "onEnrollmentsEnumerated";
                case 14:
                    return "onFeaturesRetrieved";
                case 15:
                    return "onFeatureSet";
                case 16:
                    return "onEnrollmentsRemoved";
                case 17:
                    return "onAuthenticatorIdRetrieved";
                case 18:
                    return "onAuthenticatorIdInvalidated";
                case 19:
                    return "onSessionClosed";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChallengeGenerated(readLong);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChallengeRevoked(readLong2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.biometrics.face.AuthenticationFrame authenticationFrame = (android.hardware.biometrics.face.AuthenticationFrame) parcel.readTypedObject(android.hardware.biometrics.face.AuthenticationFrame.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationFrame(authenticationFrame);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.hardware.biometrics.face.EnrollmentFrame enrollmentFrame = (android.hardware.biometrics.face.EnrollmentFrame) parcel.readTypedObject(android.hardware.biometrics.face.EnrollmentFrame.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEnrollmentFrame(enrollmentFrame);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    byte readByte = parcel.readByte();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readByte, readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollmentProgress(readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    android.hardware.keymaster.HardwareAuthToken hardwareAuthToken = (android.hardware.keymaster.HardwareAuthToken) parcel.readTypedObject(android.hardware.keymaster.HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(readInt4, hardwareAuthToken);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    onAuthenticationFailed();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onLockoutTimed(readLong3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    onLockoutPermanent();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    onLockoutCleared();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    onInteractionDetected();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onEnrollmentsEnumerated(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onFeaturesRetrieved(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    onFeatureSet(readByte2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onEnrollmentsRemoved(createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onAuthenticatorIdRetrieved(readLong4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onAuthenticatorIdInvalidated(readLong5);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    onSessionClosed();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.face.ISessionCallback {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onChallengeGenerated(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onChallengeGenerated is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onChallengeRevoked(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onChallengeRevoked is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticationFrame(android.hardware.biometrics.face.AuthenticationFrame authenticationFrame) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(authenticationFrame, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onAuthenticationFrame is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onEnrollmentFrame(android.hardware.biometrics.face.EnrollmentFrame enrollmentFrame) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(enrollmentFrame, 0);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onEnrollmentFrame is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onError(byte b, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onError is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onEnrollmentProgress(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onEnrollmentProgress is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticationSucceeded(int i, android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onAuthenticationSucceeded is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticationFailed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onAuthenticationFailed is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onLockoutTimed(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onLockoutTimed is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onLockoutPermanent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onLockoutPermanent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onLockoutCleared() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onLockoutCleared is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onInteractionDetected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onInteractionDetected is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onEnrollmentsEnumerated(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onEnrollmentsEnumerated is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onFeaturesRetrieved(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onFeaturesRetrieved is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onFeatureSet(byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onFeatureSet is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onEnrollmentsRemoved(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onEnrollmentsRemoved is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticatorIdRetrieved(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onAuthenticatorIdRetrieved is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticatorIdInvalidated(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onAuthenticatorIdInvalidated is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onSessionClosed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onSessionClosed is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
