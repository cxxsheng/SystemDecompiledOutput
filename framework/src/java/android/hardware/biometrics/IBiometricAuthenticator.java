package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface IBiometricAuthenticator extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.biometrics.IBiometricAuthenticator";

    void cancelAuthenticationFromService(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException;

    android.hardware.biometrics.ITestSession createTestSession(android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException;

    byte[] dumpSensorServiceStateProto(boolean z) throws android.os.RemoteException;

    long getAuthenticatorId(int i) throws android.os.RemoteException;

    int getLockoutModeForUser(int i) throws android.os.RemoteException;

    android.hardware.biometrics.SensorPropertiesInternal getSensorProperties(java.lang.String str) throws android.os.RemoteException;

    boolean hasEnrolledTemplates(int i, java.lang.String str) throws android.os.RemoteException;

    void invalidateAuthenticatorId(int i, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException;

    boolean isHardwareDetected(java.lang.String str) throws android.os.RemoteException;

    void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, java.lang.String str, long j2, int i2, boolean z2, boolean z3) throws android.os.RemoteException;

    void resetLockout(android.os.IBinder iBinder, java.lang.String str, int i, byte[] bArr) throws android.os.RemoteException;

    void startPreparedClient(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.IBiometricAuthenticator {
        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public android.hardware.biometrics.ITestSession createTestSession(android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public android.hardware.biometrics.SensorPropertiesInternal getSensorProperties(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public byte[] dumpSensorServiceStateProto(boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, java.lang.String str, long j2, int i2, boolean z2, boolean z3) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void startPreparedClient(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void cancelAuthenticationFromService(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public boolean isHardwareDetected(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public boolean hasEnrolledTemplates(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public int getLockoutModeForUser(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void invalidateAuthenticatorId(int i, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public long getAuthenticatorId(int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void resetLockout(android.os.IBinder iBinder, java.lang.String str, int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.IBiometricAuthenticator {
        static final int TRANSACTION_cancelAuthenticationFromService = 6;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_dumpSensorServiceStateProto = 3;
        static final int TRANSACTION_getAuthenticatorId = 11;
        static final int TRANSACTION_getLockoutModeForUser = 9;
        static final int TRANSACTION_getSensorProperties = 2;
        static final int TRANSACTION_hasEnrolledTemplates = 8;
        static final int TRANSACTION_invalidateAuthenticatorId = 10;
        static final int TRANSACTION_isHardwareDetected = 7;
        static final int TRANSACTION_prepareForAuthentication = 4;
        static final int TRANSACTION_resetLockout = 12;
        static final int TRANSACTION_startPreparedClient = 5;

        public Stub() {
            attachInterface(this, android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
        }

        public static android.hardware.biometrics.IBiometricAuthenticator asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.IBiometricAuthenticator)) {
                return (android.hardware.biometrics.IBiometricAuthenticator) queryLocalInterface;
            }
            return new android.hardware.biometrics.IBiometricAuthenticator.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createTestSession";
                case 2:
                    return "getSensorProperties";
                case 3:
                    return "dumpSensorServiceStateProto";
                case 4:
                    return "prepareForAuthentication";
                case 5:
                    return "startPreparedClient";
                case 6:
                    return "cancelAuthenticationFromService";
                case 7:
                    return "isHardwareDetected";
                case 8:
                    return "hasEnrolledTemplates";
                case 9:
                    return "getLockoutModeForUser";
                case 10:
                    return "invalidateAuthenticatorId";
                case 11:
                    return "getAuthenticatorId";
                case 12:
                    return "resetLockout";
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
                parcel.enforceInterface(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.biometrics.ITestSessionCallback asInterface = android.hardware.biometrics.ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.ITestSession createTestSession = createTestSession(asInterface, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createTestSession);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.SensorPropertiesInternal sensorProperties = getSensorProperties(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sensorProperties, 1);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] dumpSensorServiceStateProto = dumpSensorServiceStateProto(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(dumpSensorServiceStateProto);
                    return true;
                case 4:
                    boolean readBoolean2 = parcel.readBoolean();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    android.hardware.biometrics.IBiometricSensorReceiver asInterface2 = android.hardware.biometrics.IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString3 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(readBoolean2, readStrongBinder, readLong, readInt, asInterface2, readString3, readLong2, readInt2, readBoolean3, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPreparedClient(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthenticationFromService(readStrongBinder2, readString4, readLong3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHardwareDetected = isHardwareDetected(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareDetected);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledTemplates = hasEnrolledTemplates(readInt4, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledTemplates);
                    return true;
                case 9:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockoutModeForUser = getLockoutModeForUser(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockoutModeForUser);
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    android.hardware.biometrics.IInvalidationCallback asInterface3 = android.hardware.biometrics.IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorId(readInt6, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 12:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    java.lang.String readString7 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockout(readStrongBinder3, readString7, readInt8, createByteArray);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.IBiometricAuthenticator {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public android.hardware.biometrics.ITestSession createTestSession(android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeStrongInterface(iTestSessionCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.biometrics.ITestSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public android.hardware.biometrics.SensorPropertiesInternal getSensorProperties(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.biometrics.SensorPropertiesInternal) obtain2.readTypedObject(android.hardware.biometrics.SensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public byte[] dumpSensorServiceStateProto(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, java.lang.String str, long j2, int i2, boolean z2, boolean z3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBiometricSensorReceiver);
                    obtain.writeString(str);
                    obtain.writeLong(j2);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void startPreparedClient(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void cancelAuthenticationFromService(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public boolean isHardwareDetected(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public boolean hasEnrolledTemplates(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public int getLockoutModeForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void invalidateAuthenticatorId(int i, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public long getAuthenticatorId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void resetLockout(android.os.IBinder iBinder, java.lang.String str, int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
