package android.security.identity;

/* loaded from: classes3.dex */
public interface ISession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.identity.ISession";

    long getAuthChallenge() throws android.os.RemoteException;

    android.security.identity.ICredential getCredentialForPresentation(java.lang.String str) throws android.os.RemoteException;

    byte[] getEphemeralKeyPair() throws android.os.RemoteException;

    void setReaderEphemeralPublicKey(byte[] bArr) throws android.os.RemoteException;

    void setSessionTranscript(byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.security.identity.ISession {
        @Override // android.security.identity.ISession
        public byte[] getEphemeralKeyPair() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ISession
        public long getAuthChallenge() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.security.identity.ISession
        public void setReaderEphemeralPublicKey(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.security.identity.ISession
        public void setSessionTranscript(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.security.identity.ISession
        public android.security.identity.ICredential getCredentialForPresentation(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.identity.ISession {
        static final int TRANSACTION_getAuthChallenge = 2;
        static final int TRANSACTION_getCredentialForPresentation = 5;
        static final int TRANSACTION_getEphemeralKeyPair = 1;
        static final int TRANSACTION_setReaderEphemeralPublicKey = 3;
        static final int TRANSACTION_setSessionTranscript = 4;

        public Stub() {
            attachInterface(this, android.security.identity.ISession.DESCRIPTOR);
        }

        public static android.security.identity.ISession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.identity.ISession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.identity.ISession)) {
                return (android.security.identity.ISession) queryLocalInterface;
            }
            return new android.security.identity.ISession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getEphemeralKeyPair";
                case 2:
                    return "getAuthChallenge";
                case 3:
                    return "setReaderEphemeralPublicKey";
                case 4:
                    return "setSessionTranscript";
                case 5:
                    return "getCredentialForPresentation";
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
                parcel.enforceInterface(android.security.identity.ISession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.identity.ISession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte[] ephemeralKeyPair = getEphemeralKeyPair();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(ephemeralKeyPair);
                    return true;
                case 2:
                    long authChallenge = getAuthChallenge();
                    parcel2.writeNoException();
                    parcel2.writeLong(authChallenge);
                    return true;
                case 3:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setReaderEphemeralPublicKey(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setSessionTranscript(createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.security.identity.ICredential credentialForPresentation = getCredentialForPresentation(readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(credentialForPresentation);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.identity.ISession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.identity.ISession.DESCRIPTOR;
            }

            @Override // android.security.identity.ISession
            public byte[] getEphemeralKeyPair() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ISession.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public long getAuthChallenge() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ISession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public void setReaderEphemeralPublicKey(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ISession.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public void setSessionTranscript(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ISession.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public android.security.identity.ICredential getCredentialForPresentation(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ISession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.security.identity.ICredential.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
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
