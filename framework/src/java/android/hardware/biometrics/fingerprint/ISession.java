package android.hardware.biometrics.fingerprint;

/* loaded from: classes.dex */
public interface ISession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$biometrics$fingerprint$ISession".replace('$', '.');
    public static final java.lang.String HASH = "637371b53fb7faf9bd43aa51b72c23852d6e6d96";
    public static final int VERSION = 3;

    android.hardware.biometrics.common.ICancellationSignal authenticate(long j) throws android.os.RemoteException;

    android.hardware.biometrics.common.ICancellationSignal authenticateWithContext(long j, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException;

    void close() throws android.os.RemoteException;

    android.hardware.biometrics.common.ICancellationSignal detectInteraction() throws android.os.RemoteException;

    android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException;

    android.hardware.biometrics.common.ICancellationSignal enroll(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException;

    android.hardware.biometrics.common.ICancellationSignal enrollWithContext(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException;

    void enumerateEnrollments() throws android.os.RemoteException;

    void generateChallenge() throws android.os.RemoteException;

    void getAuthenticatorId() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void invalidateAuthenticatorId() throws android.os.RemoteException;

    void onContextChanged(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException;

    void onPointerCancelWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException;

    @java.lang.Deprecated
    void onPointerDown(int i, int i2, int i3, float f, float f2) throws android.os.RemoteException;

    void onPointerDownWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException;

    @java.lang.Deprecated
    void onPointerUp(int i) throws android.os.RemoteException;

    void onPointerUpWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException;

    void onUiReady() throws android.os.RemoteException;

    void removeEnrollments(int[] iArr) throws android.os.RemoteException;

    void resetLockout(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException;

    void revokeChallenge(long j) throws android.os.RemoteException;

    void setIgnoreDisplayTouches(boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.fingerprint.ISession {
        @Override // android.hardware.biometrics.fingerprint.ISession
        public void generateChallenge() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void revokeChallenge(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public android.hardware.biometrics.common.ICancellationSignal enroll(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public android.hardware.biometrics.common.ICancellationSignal authenticate(long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public android.hardware.biometrics.common.ICancellationSignal detectInteraction() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void enumerateEnrollments() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void removeEnrollments(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void getAuthenticatorId() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void invalidateAuthenticatorId() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void resetLockout(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void close() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerDown(int i, int i2, int i3, float f, float f2) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerUp(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onUiReady() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public android.hardware.biometrics.common.ICancellationSignal authenticateWithContext(long j, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public android.hardware.biometrics.common.ICancellationSignal enrollWithContext(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerDownWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerUpWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onContextChanged(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerCancelWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void setIgnoreDisplayTouches(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.fingerprint.ISession {
        static final int TRANSACTION_authenticate = 4;
        static final int TRANSACTION_authenticateWithContext = 15;
        static final int TRANSACTION_close = 11;
        static final int TRANSACTION_detectInteraction = 5;
        static final int TRANSACTION_detectInteractionWithContext = 17;
        static final int TRANSACTION_enroll = 3;
        static final int TRANSACTION_enrollWithContext = 16;
        static final int TRANSACTION_enumerateEnrollments = 6;
        static final int TRANSACTION_generateChallenge = 1;
        static final int TRANSACTION_getAuthenticatorId = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_invalidateAuthenticatorId = 9;
        static final int TRANSACTION_onContextChanged = 20;
        static final int TRANSACTION_onPointerCancelWithContext = 21;
        static final int TRANSACTION_onPointerDown = 12;
        static final int TRANSACTION_onPointerDownWithContext = 18;
        static final int TRANSACTION_onPointerUp = 13;
        static final int TRANSACTION_onPointerUpWithContext = 19;
        static final int TRANSACTION_onUiReady = 14;
        static final int TRANSACTION_removeEnrollments = 7;
        static final int TRANSACTION_resetLockout = 10;
        static final int TRANSACTION_revokeChallenge = 2;
        static final int TRANSACTION_setIgnoreDisplayTouches = 22;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.biometrics.fingerprint.ISession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.fingerprint.ISession)) {
                return (android.hardware.biometrics.fingerprint.ISession) queryLocalInterface;
            }
            return new android.hardware.biometrics.fingerprint.ISession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "generateChallenge";
                case 2:
                    return "revokeChallenge";
                case 3:
                    return "enroll";
                case 4:
                    return "authenticate";
                case 5:
                    return "detectInteraction";
                case 6:
                    return "enumerateEnrollments";
                case 7:
                    return "removeEnrollments";
                case 8:
                    return "getAuthenticatorId";
                case 9:
                    return "invalidateAuthenticatorId";
                case 10:
                    return "resetLockout";
                case 11:
                    return "close";
                case 12:
                    return "onPointerDown";
                case 13:
                    return "onPointerUp";
                case 14:
                    return "onUiReady";
                case 15:
                    return "authenticateWithContext";
                case 16:
                    return "enrollWithContext";
                case 17:
                    return "detectInteractionWithContext";
                case 18:
                    return "onPointerDownWithContext";
                case 19:
                    return "onPointerUpWithContext";
                case 20:
                    return "onContextChanged";
                case 21:
                    return "onPointerCancelWithContext";
                case 22:
                    return "setIgnoreDisplayTouches";
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
                    generateChallenge();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    revokeChallenge(readLong);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.keymaster.HardwareAuthToken hardwareAuthToken = (android.hardware.keymaster.HardwareAuthToken) parcel.readTypedObject(android.hardware.keymaster.HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal enroll = enroll(hardwareAuthToken);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(enroll);
                    return true;
                case 4:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal authenticate = authenticate(readLong2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(authenticate);
                    return true;
                case 5:
                    android.hardware.biometrics.common.ICancellationSignal detectInteraction = detectInteraction();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(detectInteraction);
                    return true;
                case 6:
                    enumerateEnrollments();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeEnrollments(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    getAuthenticatorId();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    invalidateAuthenticatorId();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.keymaster.HardwareAuthToken hardwareAuthToken2 = (android.hardware.keymaster.HardwareAuthToken) parcel.readTypedObject(android.hardware.keymaster.HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetLockout(hardwareAuthToken2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onPointerDown(readInt, readInt2, readInt3, readFloat, readFloat2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPointerUp(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    onUiReady();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long readLong3 = parcel.readLong();
                    android.hardware.biometrics.common.OperationContext operationContext = (android.hardware.biometrics.common.OperationContext) parcel.readTypedObject(android.hardware.biometrics.common.OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal authenticateWithContext = authenticateWithContext(readLong3, operationContext);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(authenticateWithContext);
                    return true;
                case 16:
                    android.hardware.keymaster.HardwareAuthToken hardwareAuthToken3 = (android.hardware.keymaster.HardwareAuthToken) parcel.readTypedObject(android.hardware.keymaster.HardwareAuthToken.CREATOR);
                    android.hardware.biometrics.common.OperationContext operationContext2 = (android.hardware.biometrics.common.OperationContext) parcel.readTypedObject(android.hardware.biometrics.common.OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal enrollWithContext = enrollWithContext(hardwareAuthToken3, operationContext2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(enrollWithContext);
                    return true;
                case 17:
                    android.hardware.biometrics.common.OperationContext operationContext3 = (android.hardware.biometrics.common.OperationContext) parcel.readTypedObject(android.hardware.biometrics.common.OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext = detectInteractionWithContext(operationContext3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(detectInteractionWithContext);
                    return true;
                case 18:
                    android.hardware.biometrics.fingerprint.PointerContext pointerContext = (android.hardware.biometrics.fingerprint.PointerContext) parcel.readTypedObject(android.hardware.biometrics.fingerprint.PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerDownWithContext(pointerContext);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.hardware.biometrics.fingerprint.PointerContext pointerContext2 = (android.hardware.biometrics.fingerprint.PointerContext) parcel.readTypedObject(android.hardware.biometrics.fingerprint.PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerUpWithContext(pointerContext2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.hardware.biometrics.common.OperationContext operationContext4 = (android.hardware.biometrics.common.OperationContext) parcel.readTypedObject(android.hardware.biometrics.common.OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onContextChanged(operationContext4);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.hardware.biometrics.fingerprint.PointerContext pointerContext3 = (android.hardware.biometrics.fingerprint.PointerContext) parcel.readTypedObject(android.hardware.biometrics.fingerprint.PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerCancelWithContext(pointerContext3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIgnoreDisplayTouches(readBoolean);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.fingerprint.ISession {
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

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void generateChallenge() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method generateChallenge is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void revokeChallenge(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method revokeChallenge is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public android.hardware.biometrics.common.ICancellationSignal enroll(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enroll is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public android.hardware.biometrics.common.ICancellationSignal authenticate(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method authenticate is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public android.hardware.biometrics.common.ICancellationSignal detectInteraction() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method detectInteraction is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void enumerateEnrollments() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enumerateEnrollments is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void removeEnrollments(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method removeEnrollments is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void getAuthenticatorId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getAuthenticatorId is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void invalidateAuthenticatorId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method invalidateAuthenticatorId is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void resetLockout(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method resetLockout is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerDown(int i, int i2, int i3, float f, float f2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onPointerDown is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerUp(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onPointerUp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onUiReady() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onUiReady is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public android.hardware.biometrics.common.ICancellationSignal authenticateWithContext(long j, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method authenticateWithContext is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public android.hardware.biometrics.common.ICancellationSignal enrollWithContext(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enrollWithContext is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method detectInteractionWithContext is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerDownWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(pointerContext, 0);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onPointerDownWithContext is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerUpWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(pointerContext, 0);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onPointerUpWithContext is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onContextChanged(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(20, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onContextChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerCancelWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(pointerContext, 0);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onPointerCancelWithContext is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void setIgnoreDisplayTouches(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(22, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setIgnoreDisplayTouches is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
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

            @Override // android.hardware.biometrics.fingerprint.ISession
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
