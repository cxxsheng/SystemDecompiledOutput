package android.accounts;

/* loaded from: classes.dex */
public interface IAccountAuthenticator extends android.os.IInterface {
    void addAccount(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException;

    void addAccountFromCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException;

    void confirmCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException;

    void editProperties(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str) throws android.os.RemoteException;

    void finishSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void getAccountCredentialsForCloning(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account) throws android.os.RemoteException;

    void getAccountRemovalAllowed(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account) throws android.os.RemoteException;

    void getAuthToken(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void getAuthTokenLabel(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str) throws android.os.RemoteException;

    void hasFeatures(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String[] strArr) throws android.os.RemoteException;

    void isCredentialsUpdateSuggested(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException;

    void startAddAccountSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException;

    void startUpdateCredentialsSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void updateCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.accounts.IAccountAuthenticator {
        @Override // android.accounts.IAccountAuthenticator
        public void addAccount(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void confirmCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAuthToken(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAuthTokenLabel(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void updateCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void editProperties(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void hasFeatures(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAccountRemovalAllowed(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAccountCredentialsForCloning(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void addAccountFromCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void startAddAccountSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void startUpdateCredentialsSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void finishSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void isCredentialsUpdateSuggested(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.accounts.IAccountAuthenticator {
        public static final java.lang.String DESCRIPTOR = "android.accounts.IAccountAuthenticator";
        static final int TRANSACTION_addAccount = 1;
        static final int TRANSACTION_addAccountFromCredentials = 10;
        static final int TRANSACTION_confirmCredentials = 2;
        static final int TRANSACTION_editProperties = 6;
        static final int TRANSACTION_finishSession = 13;
        static final int TRANSACTION_getAccountCredentialsForCloning = 9;
        static final int TRANSACTION_getAccountRemovalAllowed = 8;
        static final int TRANSACTION_getAuthToken = 3;
        static final int TRANSACTION_getAuthTokenLabel = 4;
        static final int TRANSACTION_hasFeatures = 7;
        static final int TRANSACTION_isCredentialsUpdateSuggested = 14;
        static final int TRANSACTION_startAddAccountSession = 11;
        static final int TRANSACTION_startUpdateCredentialsSession = 12;
        static final int TRANSACTION_updateCredentials = 5;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.accounts.IAccountAuthenticator asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.accounts.IAccountAuthenticator)) {
                return (android.accounts.IAccountAuthenticator) queryLocalInterface;
            }
            return new android.accounts.IAccountAuthenticator.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addAccount";
                case 2:
                    return "confirmCredentials";
                case 3:
                    return "getAuthToken";
                case 4:
                    return "getAuthTokenLabel";
                case 5:
                    return "updateCredentials";
                case 6:
                    return "editProperties";
                case 7:
                    return "hasFeatures";
                case 8:
                    return "getAccountRemovalAllowed";
                case 9:
                    return "getAccountCredentialsForCloning";
                case 10:
                    return "addAccountFromCredentials";
                case 11:
                    return "startAddAccountSession";
                case 12:
                    return "startUpdateCredentialsSession";
                case 13:
                    return "finishSession";
                case 14:
                    return "isCredentialsUpdateSuggested";
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
                    android.accounts.IAccountAuthenticatorResponse asInterface = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccount(asInterface, readString, readString2, createStringArray, bundle);
                    return true;
                case 2:
                    android.accounts.IAccountAuthenticatorResponse asInterface2 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    confirmCredentials(asInterface2, account, bundle2);
                    return true;
                case 3:
                    android.accounts.IAccountAuthenticatorResponse asInterface3 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account2 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAuthToken(asInterface3, account2, readString3, bundle3);
                    return true;
                case 4:
                    android.accounts.IAccountAuthenticatorResponse asInterface4 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAuthTokenLabel(asInterface4, readString4);
                    return true;
                case 5:
                    android.accounts.IAccountAuthenticatorResponse asInterface5 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account3 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString5 = parcel.readString();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCredentials(asInterface5, account3, readString5, bundle4);
                    return true;
                case 6:
                    android.accounts.IAccountAuthenticatorResponse asInterface6 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    editProperties(asInterface6, readString6);
                    return true;
                case 7:
                    android.accounts.IAccountAuthenticatorResponse asInterface7 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account4 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    hasFeatures(asInterface7, account4, createStringArray2);
                    return true;
                case 8:
                    android.accounts.IAccountAuthenticatorResponse asInterface8 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account5 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAccountRemovalAllowed(asInterface8, account5);
                    return true;
                case 9:
                    android.accounts.IAccountAuthenticatorResponse asInterface9 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account6 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAccountCredentialsForCloning(asInterface9, account6);
                    return true;
                case 10:
                    android.accounts.IAccountAuthenticatorResponse asInterface10 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account7 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccountFromCredentials(asInterface10, account7, bundle5);
                    return true;
                case 11:
                    android.accounts.IAccountAuthenticatorResponse asInterface11 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startAddAccountSession(asInterface11, readString7, readString8, createStringArray3, bundle6);
                    return true;
                case 12:
                    android.accounts.IAccountAuthenticatorResponse asInterface12 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account8 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString9 = parcel.readString();
                    android.os.Bundle bundle7 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startUpdateCredentialsSession(asInterface12, account8, readString9, bundle7);
                    return true;
                case 13:
                    android.accounts.IAccountAuthenticatorResponse asInterface13 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString10 = parcel.readString();
                    android.os.Bundle bundle8 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishSession(asInterface13, readString10, bundle8);
                    return true;
                case 14:
                    android.accounts.IAccountAuthenticatorResponse asInterface14 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account9 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    isCredentialsUpdateSuggested(asInterface14, account9, readString11);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.accounts.IAccountAuthenticator {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR;
            }

            @Override // android.accounts.IAccountAuthenticator
            public void addAccount(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void confirmCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void getAuthToken(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void getAuthTokenLabel(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void updateCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void editProperties(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void hasFeatures(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void getAccountRemovalAllowed(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void getAccountCredentialsForCloning(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void addAccountFromCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void startAddAccountSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void startUpdateCredentialsSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void finishSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticator
            public void isCredentialsUpdateSuggested(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticator.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void addAccount_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void confirmCredentials_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void getAuthToken_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void getAuthTokenLabel_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void updateCredentials_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void editProperties_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void hasFeatures_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void getAccountRemovalAllowed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void getAccountCredentialsForCloning_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void addAccountFromCredentials_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void startAddAccountSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void startUpdateCredentialsSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void finishSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        protected void isCredentialsUpdateSuggested_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCOUNT_MANAGER, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
