package android.accounts;

/* loaded from: classes.dex */
public interface IAccountManager extends android.os.IInterface {
    boolean accountAuthenticated(android.accounts.Account account) throws android.os.RemoteException;

    void addAccount(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle) throws android.os.RemoteException;

    void addAccountAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    boolean addAccountExplicitly(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.lang.String str2) throws android.os.RemoteException;

    boolean addAccountExplicitlyWithVisibility(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.lang.String str2) throws android.os.RemoteException;

    void addSharedAccountsFromParentUser(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void clearPassword(android.accounts.Account account) throws android.os.RemoteException;

    void confirmCredentialsAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, android.os.Bundle bundle, boolean z, int i) throws android.os.RemoteException;

    void copyAccountToUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, int i, int i2) throws android.os.RemoteException;

    android.content.IntentSender createRequestAccountAccessIntentSenderAsUser(android.accounts.Account account, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void editProperties(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, boolean z) throws android.os.RemoteException;

    void finishSessionAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.os.Bundle bundle, boolean z, android.os.Bundle bundle2, int i) throws android.os.RemoteException;

    void getAccountByTypeAndFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws android.os.RemoteException;

    int getAccountVisibility(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException;

    java.util.Map getAccountsAndVisibilityForPackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.accounts.Account[] getAccountsAsUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void getAccountsByFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws android.os.RemoteException;

    android.accounts.Account[] getAccountsByTypeForPackage(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    android.accounts.Account[] getAccountsForPackage(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void getAuthToken(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str, boolean z, boolean z2, android.os.Bundle bundle) throws android.os.RemoteException;

    void getAuthTokenLabel(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.accounts.AuthenticatorDescription[] getAuthenticatorTypes(int i) throws android.os.RemoteException;

    java.util.Map getPackagesAndVisibilityForAccount(android.accounts.Account account) throws android.os.RemoteException;

    java.lang.String getPassword(android.accounts.Account account) throws android.os.RemoteException;

    java.lang.String getPreviousName(android.accounts.Account account) throws android.os.RemoteException;

    java.lang.String getUserData(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException;

    boolean hasAccountAccess(android.accounts.Account account, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void hasFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String[] strArr, int i, java.lang.String str) throws android.os.RemoteException;

    void invalidateAuthToken(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void isCredentialsUpdateSuggested(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException;

    void onAccountAccessed(java.lang.String str) throws android.os.RemoteException;

    java.lang.String peekAuthToken(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException;

    void registerAccountListener(java.lang.String[] strArr, java.lang.String str) throws android.os.RemoteException;

    void removeAccountAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, boolean z, int i) throws android.os.RemoteException;

    boolean removeAccountExplicitly(android.accounts.Account account) throws android.os.RemoteException;

    void renameAccount(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException;

    boolean setAccountVisibility(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException;

    void setAuthToken(android.accounts.Account account, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setPassword(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException;

    void setUserData(android.accounts.Account account, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean someUserHasAccount(android.accounts.Account account) throws android.os.RemoteException;

    void startAddAccountSession(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle) throws android.os.RemoteException;

    void startUpdateCredentialsSession(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str, boolean z, android.os.Bundle bundle) throws android.os.RemoteException;

    void unregisterAccountListener(java.lang.String[] strArr, java.lang.String str) throws android.os.RemoteException;

    void updateAppPermission(android.accounts.Account account, java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void updateCredentials(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str, boolean z, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.accounts.IAccountManager {
        @Override // android.accounts.IAccountManager
        public java.lang.String getPassword(android.accounts.Account account) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public java.lang.String getUserData(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public android.accounts.AuthenticatorDescription[] getAuthenticatorTypes(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public android.accounts.Account[] getAccountsForPackage(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public android.accounts.Account[] getAccountsByTypeForPackage(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public android.accounts.Account[] getAccountsAsUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void hasFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String[] strArr, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void getAccountByTypeAndFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void getAccountsByFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean addAccountExplicitly(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void removeAccountAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean removeAccountExplicitly(android.accounts.Account account) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void copyAccountToUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void invalidateAuthToken(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public java.lang.String peekAuthToken(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void setAuthToken(android.accounts.Account account, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void setPassword(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void clearPassword(android.accounts.Account account) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void setUserData(android.accounts.Account account, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void updateAppPermission(android.accounts.Account account, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void getAuthToken(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str, boolean z, boolean z2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void addAccount(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void addAccountAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void updateCredentials(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void editProperties(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void confirmCredentialsAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, android.os.Bundle bundle, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean accountAuthenticated(android.accounts.Account account) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void getAuthTokenLabel(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void addSharedAccountsFromParentUser(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void renameAccount(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public java.lang.String getPreviousName(android.accounts.Account account) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void startAddAccountSession(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void startUpdateCredentialsSession(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void finishSessionAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.os.Bundle bundle, boolean z, android.os.Bundle bundle2, int i) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean someUserHasAccount(android.accounts.Account account) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void isCredentialsUpdateSuggested(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public java.util.Map getPackagesAndVisibilityForAccount(android.accounts.Account account) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public boolean addAccountExplicitlyWithVisibility(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public boolean setAccountVisibility(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public int getAccountVisibility(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.accounts.IAccountManager
        public java.util.Map getAccountsAndVisibilityForPackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void registerAccountListener(java.lang.String[] strArr, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void unregisterAccountListener(java.lang.String[] strArr, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean hasAccountAccess(android.accounts.Account account, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public android.content.IntentSender createRequestAccountAccessIntentSenderAsUser(android.accounts.Account account, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void onAccountAccessed(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.accounts.IAccountManager {
        public static final java.lang.String DESCRIPTOR = "android.accounts.IAccountManager";
        static final int TRANSACTION_accountAuthenticated = 27;
        static final int TRANSACTION_addAccount = 22;
        static final int TRANSACTION_addAccountAsUser = 23;
        static final int TRANSACTION_addAccountExplicitly = 10;
        static final int TRANSACTION_addAccountExplicitlyWithVisibility = 38;
        static final int TRANSACTION_addSharedAccountsFromParentUser = 29;
        static final int TRANSACTION_clearPassword = 18;
        static final int TRANSACTION_confirmCredentialsAsUser = 26;
        static final int TRANSACTION_copyAccountToUser = 13;
        static final int TRANSACTION_createRequestAccountAccessIntentSenderAsUser = 45;
        static final int TRANSACTION_editProperties = 25;
        static final int TRANSACTION_finishSessionAsUser = 34;
        static final int TRANSACTION_getAccountByTypeAndFeatures = 8;
        static final int TRANSACTION_getAccountVisibility = 40;
        static final int TRANSACTION_getAccountsAndVisibilityForPackage = 41;
        static final int TRANSACTION_getAccountsAsUser = 6;
        static final int TRANSACTION_getAccountsByFeatures = 9;
        static final int TRANSACTION_getAccountsByTypeForPackage = 5;
        static final int TRANSACTION_getAccountsForPackage = 4;
        static final int TRANSACTION_getAuthToken = 21;
        static final int TRANSACTION_getAuthTokenLabel = 28;
        static final int TRANSACTION_getAuthenticatorTypes = 3;
        static final int TRANSACTION_getPackagesAndVisibilityForAccount = 37;
        static final int TRANSACTION_getPassword = 1;
        static final int TRANSACTION_getPreviousName = 31;
        static final int TRANSACTION_getUserData = 2;
        static final int TRANSACTION_hasAccountAccess = 44;
        static final int TRANSACTION_hasFeatures = 7;
        static final int TRANSACTION_invalidateAuthToken = 14;
        static final int TRANSACTION_isCredentialsUpdateSuggested = 36;
        static final int TRANSACTION_onAccountAccessed = 46;
        static final int TRANSACTION_peekAuthToken = 15;
        static final int TRANSACTION_registerAccountListener = 42;
        static final int TRANSACTION_removeAccountAsUser = 11;
        static final int TRANSACTION_removeAccountExplicitly = 12;
        static final int TRANSACTION_renameAccount = 30;
        static final int TRANSACTION_setAccountVisibility = 39;
        static final int TRANSACTION_setAuthToken = 16;
        static final int TRANSACTION_setPassword = 17;
        static final int TRANSACTION_setUserData = 19;
        static final int TRANSACTION_someUserHasAccount = 35;
        static final int TRANSACTION_startAddAccountSession = 32;
        static final int TRANSACTION_startUpdateCredentialsSession = 33;
        static final int TRANSACTION_unregisterAccountListener = 43;
        static final int TRANSACTION_updateAppPermission = 20;
        static final int TRANSACTION_updateCredentials = 24;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.accounts.IAccountManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.accounts.IAccountManager)) {
                return (android.accounts.IAccountManager) queryLocalInterface;
            }
            return new android.accounts.IAccountManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPassword";
                case 2:
                    return "getUserData";
                case 3:
                    return "getAuthenticatorTypes";
                case 4:
                    return "getAccountsForPackage";
                case 5:
                    return "getAccountsByTypeForPackage";
                case 6:
                    return "getAccountsAsUser";
                case 7:
                    return "hasFeatures";
                case 8:
                    return "getAccountByTypeAndFeatures";
                case 9:
                    return "getAccountsByFeatures";
                case 10:
                    return "addAccountExplicitly";
                case 11:
                    return "removeAccountAsUser";
                case 12:
                    return "removeAccountExplicitly";
                case 13:
                    return "copyAccountToUser";
                case 14:
                    return "invalidateAuthToken";
                case 15:
                    return "peekAuthToken";
                case 16:
                    return "setAuthToken";
                case 17:
                    return "setPassword";
                case 18:
                    return "clearPassword";
                case 19:
                    return "setUserData";
                case 20:
                    return "updateAppPermission";
                case 21:
                    return "getAuthToken";
                case 22:
                    return "addAccount";
                case 23:
                    return "addAccountAsUser";
                case 24:
                    return "updateCredentials";
                case 25:
                    return "editProperties";
                case 26:
                    return "confirmCredentialsAsUser";
                case 27:
                    return "accountAuthenticated";
                case 28:
                    return "getAuthTokenLabel";
                case 29:
                    return "addSharedAccountsFromParentUser";
                case 30:
                    return "renameAccount";
                case 31:
                    return "getPreviousName";
                case 32:
                    return "startAddAccountSession";
                case 33:
                    return "startUpdateCredentialsSession";
                case 34:
                    return "finishSessionAsUser";
                case 35:
                    return "someUserHasAccount";
                case 36:
                    return "isCredentialsUpdateSuggested";
                case 37:
                    return "getPackagesAndVisibilityForAccount";
                case 38:
                    return "addAccountExplicitlyWithVisibility";
                case 39:
                    return "setAccountVisibility";
                case 40:
                    return "getAccountVisibility";
                case 41:
                    return "getAccountsAndVisibilityForPackage";
                case 42:
                    return "registerAccountListener";
                case 43:
                    return "unregisterAccountListener";
                case 44:
                    return "hasAccountAccess";
                case 45:
                    return "createRequestAccountAccessIntentSenderAsUser";
                case 46:
                    return "onAccountAccessed";
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
                    android.accounts.Account account = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String password = getPassword(account);
                    parcel2.writeNoException();
                    parcel2.writeString(password);
                    return true;
                case 2:
                    android.accounts.Account account2 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String userData = getUserData(account2, readString);
                    parcel2.writeNoException();
                    parcel2.writeString(userData);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.accounts.AuthenticatorDescription[] authenticatorTypes = getAuthenticatorTypes(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(authenticatorTypes, 1);
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.accounts.Account[] accountsForPackage = getAccountsForPackage(readString2, readInt2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(accountsForPackage, 1);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.accounts.Account[] accountsByTypeForPackage = getAccountsByTypeForPackage(readString4, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(accountsByTypeForPackage, 1);
                    return true;
                case 6:
                    java.lang.String readString7 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.accounts.Account[] accountsAsUser = getAccountsAsUser(readString7, readInt3, readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(accountsAsUser, 1);
                    return true;
                case 7:
                    android.accounts.IAccountManagerResponse asInterface = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account3 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int readInt4 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    hasFeatures(asInterface, account3, createStringArray, readInt4, readString9);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.accounts.IAccountManagerResponse asInterface2 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAccountByTypeAndFeatures(asInterface2, readString10, createStringArray2, readString11);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.accounts.IAccountManagerResponse asInterface3 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAccountsByFeatures(asInterface3, readString12, createStringArray3, readString13);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.accounts.Account account4 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString14 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addAccountExplicitly = addAccountExplicitly(account4, readString14, bundle, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAccountExplicitly);
                    return true;
                case 11:
                    android.accounts.IAccountManagerResponse asInterface4 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account5 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeAccountAsUser(asInterface4, account5, readBoolean, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.accounts.Account account6 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeAccountExplicitly = removeAccountExplicitly(account6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAccountExplicitly);
                    return true;
                case 13:
                    android.accounts.IAccountManagerResponse asInterface5 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account7 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    copyAccountToUser(asInterface5, account7, readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    invalidateAuthToken(readString16, readString17);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.accounts.Account account8 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String peekAuthToken = peekAuthToken(account8, readString18);
                    parcel2.writeNoException();
                    parcel2.writeString(peekAuthToken);
                    return true;
                case 16:
                    android.accounts.Account account9 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString19 = parcel.readString();
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAuthToken(account9, readString19, readString20);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.accounts.Account account10 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPassword(account10, readString21);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.accounts.Account account11 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    clearPassword(account11);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.accounts.Account account12 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString22 = parcel.readString();
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserData(account12, readString22, readString23);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.accounts.Account account13 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString24 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateAppPermission(account13, readString24, readInt8, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.accounts.IAccountManagerResponse asInterface6 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account14 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString25 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAuthToken(asInterface6, account14, readString25, readBoolean3, readBoolean4, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    android.accounts.IAccountManagerResponse asInterface7 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString26 = parcel.readString();
                    java.lang.String readString27 = parcel.readString();
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    boolean readBoolean5 = parcel.readBoolean();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccount(asInterface7, readString26, readString27, createStringArray4, readBoolean5, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    android.accounts.IAccountManagerResponse asInterface8 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString28 = parcel.readString();
                    java.lang.String readString29 = parcel.readString();
                    java.lang.String[] createStringArray5 = parcel.createStringArray();
                    boolean readBoolean6 = parcel.readBoolean();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addAccountAsUser(asInterface8, readString28, readString29, createStringArray5, readBoolean6, bundle4, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.accounts.IAccountManagerResponse asInterface9 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account15 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString30 = parcel.readString();
                    boolean readBoolean7 = parcel.readBoolean();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCredentials(asInterface9, account15, readString30, readBoolean7, bundle5);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    android.accounts.IAccountManagerResponse asInterface10 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString31 = parcel.readString();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    editProperties(asInterface10, readString31, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    android.accounts.IAccountManagerResponse asInterface11 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account16 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    confirmCredentialsAsUser(asInterface11, account16, bundle6, readBoolean9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    android.accounts.Account account17 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean accountAuthenticated = accountAuthenticated(account17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accountAuthenticated);
                    return true;
                case 28:
                    android.accounts.IAccountManagerResponse asInterface12 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString32 = parcel.readString();
                    java.lang.String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAuthTokenLabel(asInterface12, readString32, readString33);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    java.lang.String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSharedAccountsFromParentUser(readInt11, readInt12, readString34);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.accounts.IAccountManagerResponse asInterface13 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account18 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    renameAccount(asInterface13, account18, readString35);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.accounts.Account account19 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String previousName = getPreviousName(account19);
                    parcel2.writeNoException();
                    parcel2.writeString(previousName);
                    return true;
                case 32:
                    android.accounts.IAccountManagerResponse asInterface14 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString36 = parcel.readString();
                    java.lang.String readString37 = parcel.readString();
                    java.lang.String[] createStringArray6 = parcel.createStringArray();
                    boolean readBoolean10 = parcel.readBoolean();
                    android.os.Bundle bundle7 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startAddAccountSession(asInterface14, readString36, readString37, createStringArray6, readBoolean10, bundle7);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    android.accounts.IAccountManagerResponse asInterface15 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account20 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString38 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    android.os.Bundle bundle8 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startUpdateCredentialsSession(asInterface15, account20, readString38, readBoolean11, bundle8);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    android.accounts.IAccountManagerResponse asInterface16 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle9 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    android.os.Bundle bundle10 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSessionAsUser(asInterface16, bundle9, readBoolean12, bundle10, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    android.accounts.Account account21 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean someUserHasAccount = someUserHasAccount(account21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(someUserHasAccount);
                    return true;
                case 36:
                    android.accounts.IAccountManagerResponse asInterface17 = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.accounts.Account account22 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    isCredentialsUpdateSuggested(asInterface17, account22, readString39);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    android.accounts.Account account23 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.Map packagesAndVisibilityForAccount = getPackagesAndVisibilityForAccount(account23);
                    parcel2.writeNoException();
                    parcel2.writeMap(packagesAndVisibilityForAccount);
                    return true;
                case 38:
                    android.accounts.Account account24 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString40 = parcel.readString();
                    android.os.Bundle bundle11 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.util.HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
                    java.lang.String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addAccountExplicitlyWithVisibility = addAccountExplicitlyWithVisibility(account24, readString40, bundle11, readHashMap, readString41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAccountExplicitlyWithVisibility);
                    return true;
                case 39:
                    android.accounts.Account account25 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString42 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean accountVisibility = setAccountVisibility(account25, readString42, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accountVisibility);
                    return true;
                case 40:
                    android.accounts.Account account26 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int accountVisibility2 = getAccountVisibility(account26, readString43);
                    parcel2.writeNoException();
                    parcel2.writeInt(accountVisibility2);
                    return true;
                case 41:
                    java.lang.String readString44 = parcel.readString();
                    java.lang.String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.Map accountsAndVisibilityForPackage = getAccountsAndVisibilityForPackage(readString44, readString45);
                    parcel2.writeNoException();
                    parcel2.writeMap(accountsAndVisibilityForPackage);
                    return true;
                case 42:
                    java.lang.String[] createStringArray7 = parcel.createStringArray();
                    java.lang.String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerAccountListener(createStringArray7, readString46);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    java.lang.String[] createStringArray8 = parcel.createStringArray();
                    java.lang.String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAccountListener(createStringArray8, readString47);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    android.accounts.Account account27 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString48 = parcel.readString();
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasAccountAccess = hasAccountAccess(account27, readString48, userHandle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasAccountAccess);
                    return true;
                case 45:
                    android.accounts.Account account28 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString49 = parcel.readString();
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.IntentSender createRequestAccountAccessIntentSenderAsUser = createRequestAccountAccessIntentSenderAsUser(account28, readString49, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createRequestAccountAccessIntentSenderAsUser, 1);
                    return true;
                case 46:
                    java.lang.String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAccountAccessed(readString50);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.accounts.IAccountManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.accounts.IAccountManager.Stub.DESCRIPTOR;
            }

            @Override // android.accounts.IAccountManager
            public java.lang.String getPassword(android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public java.lang.String getUserData(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public android.accounts.AuthenticatorDescription[] getAuthenticatorTypes(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.accounts.AuthenticatorDescription[]) obtain2.createTypedArray(android.accounts.AuthenticatorDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public android.accounts.Account[] getAccountsForPackage(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.accounts.Account[]) obtain2.createTypedArray(android.accounts.Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public android.accounts.Account[] getAccountsByTypeForPackage(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.accounts.Account[]) obtain2.createTypedArray(android.accounts.Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public android.accounts.Account[] getAccountsAsUser(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.accounts.Account[]) obtain2.createTypedArray(android.accounts.Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void hasFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String[] strArr, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAccountByTypeAndFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAccountsByFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean addAccountExplicitly(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void removeAccountAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean removeAccountExplicitly(android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void copyAccountToUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void invalidateAuthToken(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public java.lang.String peekAuthToken(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void setAuthToken(android.accounts.Account account, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void setPassword(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void clearPassword(android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void setUserData(android.accounts.Account account, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void updateAppPermission(android.accounts.Account account, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAuthToken(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str, boolean z, boolean z2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addAccount(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addAccountAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void updateCredentials(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void editProperties(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void confirmCredentialsAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, android.os.Bundle bundle, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean accountAuthenticated(android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAuthTokenLabel(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addSharedAccountsFromParentUser(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void renameAccount(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public java.lang.String getPreviousName(android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void startAddAccountSession(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void startUpdateCredentialsSession(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str, boolean z, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void finishSessionAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.os.Bundle bundle, boolean z, android.os.Bundle bundle2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle2, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean someUserHasAccount(android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void isCredentialsUpdateSuggested(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccountManagerResponse);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public java.util.Map getPackagesAndVisibilityForAccount(android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean addAccountExplicitlyWithVisibility(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeMap(map);
                    obtain.writeString(str2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean setAccountVisibility(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public int getAccountVisibility(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public java.util.Map getAccountsAndVisibilityForPackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void registerAccountListener(java.lang.String[] strArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void unregisterAccountListener(java.lang.String[] strArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean hasAccountAccess(android.accounts.Account account, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public android.content.IntentSender createRequestAccountAccessIntentSenderAsUser(android.accounts.Account account, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.IntentSender) obtain2.readTypedObject(android.content.IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void onAccountAccessed(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 45;
        }
    }
}
