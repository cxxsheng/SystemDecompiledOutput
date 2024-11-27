package android.accounts;

/* loaded from: classes.dex */
public abstract class AbstractAccountAuthenticator {
    private static final java.lang.String KEY_ACCOUNT = "android.accounts.AbstractAccountAuthenticator.KEY_ACCOUNT";
    private static final java.lang.String KEY_AUTH_TOKEN_TYPE = "android.accounts.AbstractAccountAuthenticato.KEY_AUTH_TOKEN_TYPE";
    public static final java.lang.String KEY_CUSTOM_TOKEN_EXPIRY = "android.accounts.expiry";
    private static final java.lang.String KEY_OPTIONS = "android.accounts.AbstractAccountAuthenticator.KEY_OPTIONS";
    private static final java.lang.String KEY_REQUIRED_FEATURES = "android.accounts.AbstractAccountAuthenticator.KEY_REQUIRED_FEATURES";
    private static final java.lang.String TAG = "AccountAuthenticator";
    private android.accounts.AbstractAccountAuthenticator.Transport mTransport = new android.accounts.AbstractAccountAuthenticator.Transport();

    public abstract android.os.Bundle addAccount(android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.os.Bundle bundle) throws android.accounts.NetworkErrorException;

    public abstract android.os.Bundle confirmCredentials(android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.accounts.NetworkErrorException;

    public abstract android.os.Bundle editProperties(android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, java.lang.String str);

    public abstract android.os.Bundle getAuthToken(android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.accounts.NetworkErrorException;

    public abstract java.lang.String getAuthTokenLabel(java.lang.String str);

    public abstract android.os.Bundle hasFeatures(android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, android.accounts.Account account, java.lang.String[] strArr) throws android.accounts.NetworkErrorException;

    public abstract android.os.Bundle updateCredentials(android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.accounts.NetworkErrorException;

    public AbstractAccountAuthenticator(android.content.Context context) {
    }

    private class Transport extends android.accounts.IAccountAuthenticator.Stub {
        private Transport() {
        }

        @Override // android.accounts.IAccountAuthenticator
        public void addAccount(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException {
            super.addAccount_enforcePermission();
            if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "addAccount: accountType " + str + ", authTokenType " + str2 + ", features " + (strArr == null ? "[]" : java.util.Arrays.toString(strArr)));
            }
            try {
                android.os.Bundle addAccount = android.accounts.AbstractAccountAuthenticator.this.addAccount(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str, str2, strArr, bundle);
                if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                    if (addAccount != null) {
                        addAccount.keySet();
                    }
                    android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "addAccount: result " + android.accounts.AccountManager.sanitizeResult(addAccount));
                }
                if (addAccount != null) {
                    iAccountAuthenticatorResponse.onResult(addAccount);
                } else {
                    iAccountAuthenticatorResponse.onError(5, "null bundle returned");
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "addAccount", str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void confirmCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException {
            super.confirmCredentials_enforcePermission();
            if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "confirmCredentials: " + account);
            }
            try {
                android.os.Bundle confirmCredentials = android.accounts.AbstractAccountAuthenticator.this.confirmCredentials(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, bundle);
                if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                    if (confirmCredentials != null) {
                        confirmCredentials.keySet();
                    }
                    android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "confirmCredentials: result " + android.accounts.AccountManager.sanitizeResult(confirmCredentials));
                }
                if (confirmCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(confirmCredentials);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "confirmCredentials", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAuthTokenLabel(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str) throws android.os.RemoteException {
            super.getAuthTokenLabel_enforcePermission();
            if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "getAuthTokenLabel: authTokenType " + str);
            }
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putString(android.accounts.AccountManager.KEY_AUTH_TOKEN_LABEL, android.accounts.AbstractAccountAuthenticator.this.getAuthTokenLabel(str));
                if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                    bundle.keySet();
                    android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "getAuthTokenLabel: result " + android.accounts.AccountManager.sanitizeResult(bundle));
                }
                iAccountAuthenticatorResponse.onResult(bundle);
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAuthTokenLabel", str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAuthToken(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
            super.getAuthToken_enforcePermission();
            if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "getAuthToken: " + account + ", authTokenType " + str);
            }
            try {
                android.os.Bundle authToken = android.accounts.AbstractAccountAuthenticator.this.getAuthToken(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                    if (authToken != null) {
                        authToken.keySet();
                    }
                    android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "getAuthToken: result " + android.accounts.AccountManager.sanitizeResult(authToken));
                }
                if (authToken != null) {
                    iAccountAuthenticatorResponse.onResult(authToken);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAuthToken", account.toString() + "," + str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void updateCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
            super.updateCredentials_enforcePermission();
            if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "updateCredentials: " + account + ", authTokenType " + str);
            }
            try {
                android.os.Bundle updateCredentials = android.accounts.AbstractAccountAuthenticator.this.updateCredentials(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                    if (updateCredentials != null) {
                        updateCredentials.keySet();
                    }
                    android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "updateCredentials: result " + android.accounts.AccountManager.sanitizeResult(updateCredentials));
                }
                if (updateCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(updateCredentials);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "updateCredentials", account.toString() + "," + str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void editProperties(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str) throws android.os.RemoteException {
            super.editProperties_enforcePermission();
            try {
                android.os.Bundle editProperties = android.accounts.AbstractAccountAuthenticator.this.editProperties(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str);
                if (editProperties != null) {
                    iAccountAuthenticatorResponse.onResult(editProperties);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "editProperties", str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void hasFeatures(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String[] strArr) throws android.os.RemoteException {
            super.hasFeatures_enforcePermission();
            try {
                android.os.Bundle hasFeatures = android.accounts.AbstractAccountAuthenticator.this.hasFeatures(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, strArr);
                if (hasFeatures != null) {
                    iAccountAuthenticatorResponse.onResult(hasFeatures);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "hasFeatures", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAccountRemovalAllowed(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account) throws android.os.RemoteException {
            super.getAccountRemovalAllowed_enforcePermission();
            try {
                android.os.Bundle accountRemovalAllowed = android.accounts.AbstractAccountAuthenticator.this.getAccountRemovalAllowed(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account);
                if (accountRemovalAllowed != null) {
                    iAccountAuthenticatorResponse.onResult(accountRemovalAllowed);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAccountRemovalAllowed", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void getAccountCredentialsForCloning(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account) throws android.os.RemoteException {
            super.getAccountCredentialsForCloning_enforcePermission();
            try {
                android.os.Bundle accountCredentialsForCloning = android.accounts.AbstractAccountAuthenticator.this.getAccountCredentialsForCloning(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account);
                if (accountCredentialsForCloning != null) {
                    iAccountAuthenticatorResponse.onResult(accountCredentialsForCloning);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAccountCredentialsForCloning", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void addAccountFromCredentials(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException {
            super.addAccountFromCredentials_enforcePermission();
            try {
                android.os.Bundle addAccountFromCredentials = android.accounts.AbstractAccountAuthenticator.this.addAccountFromCredentials(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, bundle);
                if (addAccountFromCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(addAccountFromCredentials);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "addAccountFromCredentials", account.toString(), e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void startAddAccountSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException {
            super.startAddAccountSession_enforcePermission();
            if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "startAddAccountSession: accountType " + str + ", authTokenType " + str2 + ", features " + (strArr == null ? "[]" : java.util.Arrays.toString(strArr)));
            }
            try {
                android.os.Bundle startAddAccountSession = android.accounts.AbstractAccountAuthenticator.this.startAddAccountSession(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str, str2, strArr, bundle);
                if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                    if (startAddAccountSession != null) {
                        startAddAccountSession.keySet();
                    }
                    android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "startAddAccountSession: result " + android.accounts.AccountManager.sanitizeResult(startAddAccountSession));
                }
                if (startAddAccountSession != null) {
                    iAccountAuthenticatorResponse.onResult(startAddAccountSession);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "startAddAccountSession", str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void startUpdateCredentialsSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
            super.startUpdateCredentialsSession_enforcePermission();
            if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "startUpdateCredentialsSession: " + account + ", authTokenType " + str);
            }
            try {
                android.os.Bundle startUpdateCredentialsSession = android.accounts.AbstractAccountAuthenticator.this.startUpdateCredentialsSession(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                    if (startUpdateCredentialsSession != null) {
                        startUpdateCredentialsSession.keySet();
                    }
                    android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "startUpdateCredentialsSession: result " + android.accounts.AccountManager.sanitizeResult(startUpdateCredentialsSession));
                }
                if (startUpdateCredentialsSession != null) {
                    iAccountAuthenticatorResponse.onResult(startUpdateCredentialsSession);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "startUpdateCredentialsSession", account.toString() + "," + str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void finishSession(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
            super.finishSession_enforcePermission();
            if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "finishSession: accountType " + str);
            }
            try {
                android.os.Bundle finishSession = android.accounts.AbstractAccountAuthenticator.this.finishSession(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str, bundle);
                if (finishSession != null) {
                    finishSession.keySet();
                }
                if (android.util.Log.isLoggable(android.accounts.AbstractAccountAuthenticator.TAG, 2)) {
                    android.util.Log.v(android.accounts.AbstractAccountAuthenticator.TAG, "finishSession: result " + android.accounts.AccountManager.sanitizeResult(finishSession));
                }
                if (finishSession != null) {
                    iAccountAuthenticatorResponse.onResult(finishSession);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "finishSession", str, e);
            }
        }

        @Override // android.accounts.IAccountAuthenticator
        public void isCredentialsUpdateSuggested(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
            super.isCredentialsUpdateSuggested_enforcePermission();
            try {
                android.os.Bundle isCredentialsUpdateSuggested = android.accounts.AbstractAccountAuthenticator.this.isCredentialsUpdateSuggested(new android.accounts.AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str);
                if (isCredentialsUpdateSuggested != null) {
                    iAccountAuthenticatorResponse.onResult(isCredentialsUpdateSuggested);
                }
            } catch (java.lang.Exception e) {
                android.accounts.AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "isCredentialsUpdateSuggested", account.toString(), e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleException(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse, java.lang.String str, java.lang.String str2, java.lang.Exception exc) throws android.os.RemoteException {
        if (exc instanceof android.accounts.NetworkErrorException) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + str2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, exc);
            }
            iAccountAuthenticatorResponse.onError(3, exc.getMessage());
        } else if (exc instanceof java.lang.UnsupportedOperationException) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + str2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, exc);
            }
            iAccountAuthenticatorResponse.onError(6, str + " not supported");
        } else if (exc instanceof java.lang.IllegalArgumentException) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + str2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, exc);
            }
            iAccountAuthenticatorResponse.onError(7, str + " not supported");
        } else {
            android.util.Log.w(TAG, str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + str2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, exc);
            iAccountAuthenticatorResponse.onError(1, str + " failed");
        }
    }

    public final android.os.IBinder getIBinder() {
        return this.mTransport.asBinder();
    }

    public android.os.Bundle getAccountRemovalAllowed(android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, android.accounts.Account account) throws android.accounts.NetworkErrorException {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT, true);
        return bundle;
    }

    public android.os.Bundle getAccountCredentialsForCloning(final android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, android.accounts.Account account) throws android.accounts.NetworkErrorException {
        new java.lang.Thread(new java.lang.Runnable() { // from class: android.accounts.AbstractAccountAuthenticator.1
            @Override // java.lang.Runnable
            public void run() {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT, false);
                accountAuthenticatorResponse.onResult(bundle);
            }
        }).start();
        return null;
    }

    public android.os.Bundle addAccountFromCredentials(final android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, android.accounts.Account account, android.os.Bundle bundle) throws android.accounts.NetworkErrorException {
        new java.lang.Thread(new java.lang.Runnable() { // from class: android.accounts.AbstractAccountAuthenticator.2
            @Override // java.lang.Runnable
            public void run() {
                android.os.Bundle bundle2 = new android.os.Bundle();
                bundle2.putBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT, false);
                accountAuthenticatorResponse.onResult(bundle2);
            }
        }).start();
        return null;
    }

    public android.os.Bundle startAddAccountSession(final android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, java.lang.String str, final java.lang.String str2, final java.lang.String[] strArr, final android.os.Bundle bundle) throws android.accounts.NetworkErrorException {
        new java.lang.Thread(new java.lang.Runnable() { // from class: android.accounts.AbstractAccountAuthenticator.3
            @Override // java.lang.Runnable
            public void run() {
                android.os.Bundle bundle2 = new android.os.Bundle();
                bundle2.putString(android.accounts.AbstractAccountAuthenticator.KEY_AUTH_TOKEN_TYPE, str2);
                bundle2.putStringArray(android.accounts.AbstractAccountAuthenticator.KEY_REQUIRED_FEATURES, strArr);
                bundle2.putBundle(android.accounts.AbstractAccountAuthenticator.KEY_OPTIONS, bundle);
                android.os.Bundle bundle3 = new android.os.Bundle();
                bundle3.putBundle(android.accounts.AccountManager.KEY_ACCOUNT_SESSION_BUNDLE, bundle2);
                accountAuthenticatorResponse.onResult(bundle3);
            }
        }).start();
        return null;
    }

    public android.os.Bundle startUpdateCredentialsSession(final android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, final android.accounts.Account account, final java.lang.String str, final android.os.Bundle bundle) throws android.accounts.NetworkErrorException {
        new java.lang.Thread(new java.lang.Runnable() { // from class: android.accounts.AbstractAccountAuthenticator.4
            @Override // java.lang.Runnable
            public void run() {
                android.os.Bundle bundle2 = new android.os.Bundle();
                bundle2.putString(android.accounts.AbstractAccountAuthenticator.KEY_AUTH_TOKEN_TYPE, str);
                bundle2.putParcelable(android.accounts.AbstractAccountAuthenticator.KEY_ACCOUNT, account);
                bundle2.putBundle(android.accounts.AbstractAccountAuthenticator.KEY_OPTIONS, bundle);
                android.os.Bundle bundle3 = new android.os.Bundle();
                bundle3.putBundle(android.accounts.AccountManager.KEY_ACCOUNT_SESSION_BUNDLE, bundle2);
                accountAuthenticatorResponse.onResult(bundle3);
            }
        }).start();
        return null;
    }

    public android.os.Bundle finishSession(android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, java.lang.String str, android.os.Bundle bundle) throws android.accounts.NetworkErrorException {
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Log.e(TAG, "Account type cannot be empty.");
            android.os.Bundle bundle2 = new android.os.Bundle();
            bundle2.putInt("errorCode", 7);
            bundle2.putString(android.accounts.AccountManager.KEY_ERROR_MESSAGE, "accountType cannot be empty.");
            return bundle2;
        }
        if (bundle == null) {
            android.util.Log.e(TAG, "Session bundle cannot be null.");
            android.os.Bundle bundle3 = new android.os.Bundle();
            bundle3.putInt("errorCode", 7);
            bundle3.putString(android.accounts.AccountManager.KEY_ERROR_MESSAGE, "sessionBundle cannot be null.");
            return bundle3;
        }
        if (!bundle.containsKey(KEY_AUTH_TOKEN_TYPE)) {
            android.os.Bundle bundle4 = new android.os.Bundle();
            bundle4.putInt("errorCode", 6);
            bundle4.putString(android.accounts.AccountManager.KEY_ERROR_MESSAGE, "Authenticator must override finishSession if startAddAccountSession or startUpdateCredentialsSession is overridden.");
            accountAuthenticatorResponse.onResult(bundle4);
            return bundle4;
        }
        java.lang.String string = bundle.getString(KEY_AUTH_TOKEN_TYPE);
        android.os.Bundle bundle5 = bundle.getBundle(KEY_OPTIONS);
        java.lang.String[] stringArray = bundle.getStringArray(KEY_REQUIRED_FEATURES);
        android.accounts.Account account = (android.accounts.Account) bundle.getParcelable(KEY_ACCOUNT, android.accounts.Account.class);
        boolean containsKey = bundle.containsKey(KEY_ACCOUNT);
        android.os.Bundle bundle6 = new android.os.Bundle(bundle);
        bundle6.remove(KEY_AUTH_TOKEN_TYPE);
        bundle6.remove(KEY_REQUIRED_FEATURES);
        bundle6.remove(KEY_OPTIONS);
        bundle6.remove(KEY_ACCOUNT);
        if (bundle5 != null) {
            bundle5.putAll(bundle6);
            bundle6 = bundle5;
        }
        if (containsKey) {
            return updateCredentials(accountAuthenticatorResponse, account, string, bundle5);
        }
        return addAccount(accountAuthenticatorResponse, str, string, stringArray, bundle6);
    }

    public android.os.Bundle isCredentialsUpdateSuggested(android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, android.accounts.Account account, java.lang.String str) throws android.accounts.NetworkErrorException {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT, false);
        return bundle;
    }
}
