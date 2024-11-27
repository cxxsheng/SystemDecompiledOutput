package android.security.identity;

/* loaded from: classes3.dex */
public interface ICredentialStore extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.identity.ICredentialStore";
    public static final int ERROR_ALREADY_PERSONALIZED = 2;
    public static final int ERROR_AUTHENTICATION_KEY_NOT_FOUND = 9;
    public static final int ERROR_CIPHER_SUITE_NOT_SUPPORTED = 4;
    public static final int ERROR_DOCUMENT_TYPE_NOT_SUPPORTED = 8;
    public static final int ERROR_EPHEMERAL_PUBLIC_KEY_NOT_FOUND = 5;
    public static final int ERROR_GENERIC = 1;
    public static final int ERROR_INVALID_ITEMS_REQUEST_MESSAGE = 10;
    public static final int ERROR_INVALID_READER_SIGNATURE = 7;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_SUPPORTED = 12;
    public static final int ERROR_NO_AUTHENTICATION_KEY_AVAILABLE = 6;
    public static final int ERROR_NO_SUCH_CREDENTIAL = 3;
    public static final int ERROR_SESSION_TRANSCRIPT_MISMATCH = 11;

    android.security.identity.IWritableCredential createCredential(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.security.identity.ISession createPresentationSession(int i) throws android.os.RemoteException;

    android.security.identity.ICredential getCredentialByName(java.lang.String str, int i) throws android.os.RemoteException;

    android.security.identity.SecurityHardwareInfoParcel getSecurityHardwareInfo() throws android.os.RemoteException;

    public static class Default implements android.security.identity.ICredentialStore {
        @Override // android.security.identity.ICredentialStore
        public android.security.identity.SecurityHardwareInfoParcel getSecurityHardwareInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredentialStore
        public android.security.identity.IWritableCredential createCredential(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredentialStore
        public android.security.identity.ICredential getCredentialByName(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredentialStore
        public android.security.identity.ISession createPresentationSession(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.identity.ICredentialStore {
        static final int TRANSACTION_createCredential = 2;
        static final int TRANSACTION_createPresentationSession = 4;
        static final int TRANSACTION_getCredentialByName = 3;
        static final int TRANSACTION_getSecurityHardwareInfo = 1;

        public Stub() {
            attachInterface(this, android.security.identity.ICredentialStore.DESCRIPTOR);
        }

        public static android.security.identity.ICredentialStore asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.identity.ICredentialStore.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.identity.ICredentialStore)) {
                return (android.security.identity.ICredentialStore) queryLocalInterface;
            }
            return new android.security.identity.ICredentialStore.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSecurityHardwareInfo";
                case 2:
                    return "createCredential";
                case 3:
                    return "getCredentialByName";
                case 4:
                    return "createPresentationSession";
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
                parcel.enforceInterface(android.security.identity.ICredentialStore.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.identity.ICredentialStore.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.security.identity.SecurityHardwareInfoParcel securityHardwareInfo = getSecurityHardwareInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(securityHardwareInfo, 1);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.security.identity.IWritableCredential createCredential = createCredential(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createCredential);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.security.identity.ICredential credentialByName = getCredentialByName(readString3, readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(credentialByName);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.security.identity.ISession createPresentationSession = createPresentationSession(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createPresentationSession);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.identity.ICredentialStore {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.identity.ICredentialStore.DESCRIPTOR;
            }

            @Override // android.security.identity.ICredentialStore
            public android.security.identity.SecurityHardwareInfoParcel getSecurityHardwareInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredentialStore.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.security.identity.SecurityHardwareInfoParcel) obtain2.readTypedObject(android.security.identity.SecurityHardwareInfoParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredentialStore
            public android.security.identity.IWritableCredential createCredential(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredentialStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.security.identity.IWritableCredential.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredentialStore
            public android.security.identity.ICredential getCredentialByName(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredentialStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.security.identity.ICredential.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredentialStore
            public android.security.identity.ISession createPresentationSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredentialStore.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.security.identity.ISession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
