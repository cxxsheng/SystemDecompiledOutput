package android.accounts;

/* loaded from: classes.dex */
public class AccountManager {
    public static final java.lang.String ACCOUNT_ACCESS_TOKEN_TYPE = "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE";
    public static final java.lang.String ACTION_ACCOUNT_REMOVED = "android.accounts.action.ACCOUNT_REMOVED";
    public static final java.lang.String ACTION_AUTHENTICATOR_INTENT = "android.accounts.AccountAuthenticator";
    public static final java.lang.String ACTION_VISIBLE_ACCOUNTS_CHANGED = "android.accounts.action.VISIBLE_ACCOUNTS_CHANGED";
    public static final java.lang.String AUTHENTICATOR_ATTRIBUTES_NAME = "account-authenticator";
    public static final java.lang.String AUTHENTICATOR_META_DATA_NAME = "android.accounts.AccountAuthenticator";
    public static final int CACHE_ACCOUNTS_DATA_SIZE = 4;
    public static final java.lang.String CACHE_KEY_ACCOUNTS_DATA_PROPERTY = "cache_key.system_server.accounts_data";
    public static final java.lang.String CACHE_KEY_USER_DATA_PROPERTY = "cache_key.system_server.account_user_data";
    public static final int CACHE_USER_DATA_SIZE = 4;
    public static final int ERROR_CODE_BAD_ARGUMENTS = 7;
    public static final int ERROR_CODE_BAD_AUTHENTICATION = 9;
    public static final int ERROR_CODE_BAD_REQUEST = 8;
    public static final int ERROR_CODE_CANCELED = 4;
    public static final int ERROR_CODE_INVALID_RESPONSE = 5;
    public static final int ERROR_CODE_MANAGEMENT_DISABLED_FOR_ACCOUNT_TYPE = 101;
    public static final int ERROR_CODE_NETWORK_ERROR = 3;
    public static final int ERROR_CODE_REMOTE_EXCEPTION = 1;
    public static final int ERROR_CODE_UNSUPPORTED_OPERATION = 6;
    public static final int ERROR_CODE_USER_RESTRICTED = 100;
    public static final java.lang.String KEY_ACCOUNTS = "accounts";
    public static final java.lang.String KEY_ACCOUNT_ACCESS_ID = "accountAccessId";
    public static final java.lang.String KEY_ACCOUNT_AUTHENTICATOR_RESPONSE = "accountAuthenticatorResponse";
    public static final java.lang.String KEY_ACCOUNT_MANAGER_RESPONSE = "accountManagerResponse";
    public static final java.lang.String KEY_ACCOUNT_NAME = "authAccount";
    public static final java.lang.String KEY_ACCOUNT_SESSION_BUNDLE = "accountSessionBundle";
    public static final java.lang.String KEY_ACCOUNT_STATUS_TOKEN = "accountStatusToken";
    public static final java.lang.String KEY_ACCOUNT_TYPE = "accountType";
    public static final java.lang.String KEY_ANDROID_PACKAGE_NAME = "androidPackageName";
    public static final java.lang.String KEY_AUTHENTICATOR_TYPES = "authenticator_types";
    public static final java.lang.String KEY_AUTHTOKEN = "authtoken";
    public static final java.lang.String KEY_AUTH_FAILED_MESSAGE = "authFailedMessage";
    public static final java.lang.String KEY_AUTH_TOKEN_LABEL = "authTokenLabelKey";
    public static final java.lang.String KEY_BOOLEAN_RESULT = "booleanResult";
    public static final java.lang.String KEY_CALLER_PID = "callerPid";
    public static final java.lang.String KEY_CALLER_UID = "callerUid";
    public static final java.lang.String KEY_ERROR_CODE = "errorCode";
    public static final java.lang.String KEY_ERROR_MESSAGE = "errorMessage";
    public static final java.lang.String KEY_INTENT = "intent";
    public static final java.lang.String KEY_LAST_AUTHENTICATED_TIME = "lastAuthenticatedTime";
    public static final java.lang.String KEY_NOTIFY_ON_FAILURE = "notifyOnAuthFailure";
    public static final java.lang.String KEY_PASSWORD = "password";
    public static final java.lang.String KEY_USERDATA = "userdata";
    public static final java.lang.String LOGIN_ACCOUNTS_CHANGED_ACTION = "android.accounts.LOGIN_ACCOUNTS_CHANGED";
    public static final java.lang.String PACKAGE_NAME_KEY_LEGACY_NOT_VISIBLE = "android:accounts:key_legacy_not_visible";
    public static final java.lang.String PACKAGE_NAME_KEY_LEGACY_VISIBLE = "android:accounts:key_legacy_visible";
    private static final java.lang.String TAG = "AccountManager";
    public static final int VISIBILITY_NOT_VISIBLE = 3;
    public static final int VISIBILITY_UNDEFINED = 0;
    public static final int VISIBILITY_USER_MANAGED_NOT_VISIBLE = 4;
    public static final int VISIBILITY_USER_MANAGED_VISIBLE = 2;
    public static final int VISIBILITY_VISIBLE = 1;
    android.app.PropertyInvalidatedCache<android.content.pm.UserPackage, android.accounts.Account[]> mAccountsForUserCache;
    private final android.content.Context mContext;
    private final android.os.Handler mMainHandler;
    private final android.accounts.IAccountManager mService;
    android.app.PropertyInvalidatedCache<android.accounts.AccountManager.AccountKeyData, java.lang.String> mUserDataCache;
    private final java.util.HashMap<android.accounts.OnAccountsUpdateListener, android.os.Handler> mAccountsUpdatedListeners = com.google.android.collect.Maps.newHashMap();
    private final java.util.HashMap<android.accounts.OnAccountsUpdateListener, java.util.Set<java.lang.String>> mAccountsUpdatedListenersTypes = com.google.android.collect.Maps.newHashMap();
    private final android.content.BroadcastReceiver mAccountsChangedBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: android.accounts.AccountManager.20
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.accounts.Account[] accounts = android.accounts.AccountManager.this.getAccounts();
            synchronized (android.accounts.AccountManager.this.mAccountsUpdatedListeners) {
                for (java.util.Map.Entry entry : android.accounts.AccountManager.this.mAccountsUpdatedListeners.entrySet()) {
                    android.accounts.AccountManager.this.postToHandler((android.os.Handler) entry.getValue(), (android.accounts.OnAccountsUpdateListener) entry.getKey(), accounts);
                }
            }
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AccountVisibility {
    }

    private static final class AccountKeyData {
        public final android.accounts.Account account;
        public final java.lang.String key;

        public AccountKeyData(android.accounts.Account account, java.lang.String str) {
            this.account = account;
            this.key = str;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            android.accounts.AccountManager.AccountKeyData accountKeyData = (android.accounts.AccountManager.AccountKeyData) obj;
            if (!accountKeyData.account.equals(this.account) || !accountKeyData.key.equals(this.key)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.account, this.key);
        }
    }

    public AccountManager(android.content.Context context, android.accounts.IAccountManager iAccountManager) {
        int i = 4;
        this.mAccountsForUserCache = new android.app.PropertyInvalidatedCache<android.content.pm.UserPackage, android.accounts.Account[]>(i, CACHE_KEY_ACCOUNTS_DATA_PROPERTY) { // from class: android.accounts.AccountManager.1
            @Override // android.app.PropertyInvalidatedCache
            public android.accounts.Account[] recompute(android.content.pm.UserPackage userPackage) {
                try {
                    return android.accounts.AccountManager.this.mService.getAccountsAsUser(null, userPackage.userId, userPackage.packageName);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean bypass(android.content.pm.UserPackage userPackage) {
                return userPackage.userId < 0;
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean resultEquals(android.accounts.Account[] accountArr, android.accounts.Account[] accountArr2) {
                if (accountArr == accountArr2) {
                    return true;
                }
                if (accountArr == null || accountArr2 == null) {
                    return false;
                }
                return java.util.Arrays.equals(accountArr, accountArr2);
            }
        };
        this.mUserDataCache = new android.app.PropertyInvalidatedCache<android.accounts.AccountManager.AccountKeyData, java.lang.String>(i, CACHE_KEY_USER_DATA_PROPERTY) { // from class: android.accounts.AccountManager.2
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.String recompute(android.accounts.AccountManager.AccountKeyData accountKeyData) {
                android.accounts.Account account = accountKeyData.account;
                java.lang.String str = accountKeyData.key;
                if (account == null) {
                    throw new java.lang.IllegalArgumentException("account is null");
                }
                if (str == null) {
                    throw new java.lang.IllegalArgumentException("key is null");
                }
                try {
                    return android.accounts.AccountManager.this.mService.getUserData(account, str);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mContext = context;
        this.mService = iAccountManager;
        this.mMainHandler = new android.os.Handler(this.mContext.getMainLooper());
    }

    public AccountManager(android.content.Context context, android.accounts.IAccountManager iAccountManager, android.os.Handler handler) {
        int i = 4;
        this.mAccountsForUserCache = new android.app.PropertyInvalidatedCache<android.content.pm.UserPackage, android.accounts.Account[]>(i, CACHE_KEY_ACCOUNTS_DATA_PROPERTY) { // from class: android.accounts.AccountManager.1
            @Override // android.app.PropertyInvalidatedCache
            public android.accounts.Account[] recompute(android.content.pm.UserPackage userPackage) {
                try {
                    return android.accounts.AccountManager.this.mService.getAccountsAsUser(null, userPackage.userId, userPackage.packageName);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean bypass(android.content.pm.UserPackage userPackage) {
                return userPackage.userId < 0;
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean resultEquals(android.accounts.Account[] accountArr, android.accounts.Account[] accountArr2) {
                if (accountArr == accountArr2) {
                    return true;
                }
                if (accountArr == null || accountArr2 == null) {
                    return false;
                }
                return java.util.Arrays.equals(accountArr, accountArr2);
            }
        };
        this.mUserDataCache = new android.app.PropertyInvalidatedCache<android.accounts.AccountManager.AccountKeyData, java.lang.String>(i, CACHE_KEY_USER_DATA_PROPERTY) { // from class: android.accounts.AccountManager.2
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.String recompute(android.accounts.AccountManager.AccountKeyData accountKeyData) {
                android.accounts.Account account = accountKeyData.account;
                java.lang.String str = accountKeyData.key;
                if (account == null) {
                    throw new java.lang.IllegalArgumentException("account is null");
                }
                if (str == null) {
                    throw new java.lang.IllegalArgumentException("key is null");
                }
                try {
                    return android.accounts.AccountManager.this.mService.getUserData(account, str);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mContext = context;
        this.mService = iAccountManager;
        this.mMainHandler = handler;
    }

    public static android.os.Bundle sanitizeResult(android.os.Bundle bundle) {
        if (bundle != null && bundle.containsKey(KEY_AUTHTOKEN) && !android.text.TextUtils.isEmpty(bundle.getString(KEY_AUTHTOKEN))) {
            android.os.Bundle bundle2 = new android.os.Bundle(bundle);
            bundle2.putString(KEY_AUTHTOKEN, "<omitted for logging purposes>");
            return bundle2;
        }
        return bundle;
    }

    public static android.accounts.AccountManager get(android.content.Context context) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("context is null");
        }
        return (android.accounts.AccountManager) context.getSystemService("account");
    }

    public java.lang.String getPassword(android.accounts.Account account) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            return this.mService.getPassword(account);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getUserData(android.accounts.Account account, java.lang.String str) {
        return this.mUserDataCache.query(new android.accounts.AccountManager.AccountKeyData(account, str));
    }

    public android.accounts.AuthenticatorDescription[] getAuthenticatorTypes() {
        return getAuthenticatorTypesAsUser(this.mContext.getUserId());
    }

    public android.accounts.AuthenticatorDescription[] getAuthenticatorTypesAsUser(int i) {
        try {
            return this.mService.getAuthenticatorTypes(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.accounts.Account[] getAccounts() {
        return getAccountsAsUser(this.mContext.getUserId());
    }

    public android.accounts.Account[] getAccountsAsUser(int i) {
        return this.mAccountsForUserCache.query(android.content.pm.UserPackage.of(i, this.mContext.getOpPackageName()));
    }

    public android.accounts.Account[] getAccountsForPackage(java.lang.String str, int i) {
        try {
            return this.mService.getAccountsForPackage(str, i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.accounts.Account[] getAccountsByTypeForPackage(java.lang.String str, java.lang.String str2) {
        try {
            return this.mService.getAccountsByTypeForPackage(str, str2, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.accounts.Account[] getAccountsByType(java.lang.String str) {
        return getAccountsByTypeAsUser(str, this.mContext.getUser());
    }

    public android.accounts.Account[] getAccountsByTypeAsUser(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.getAccountsAsUser(str, userHandle.getIdentifier(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateAppPermission(android.accounts.Account account, java.lang.String str, int i, boolean z) {
        try {
            this.mService.updateAppPermission(account, str, i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.accounts.AccountManagerFuture<java.lang.String> getAuthTokenLabel(final java.lang.String str, final java.lang.String str2, android.accounts.AccountManagerCallback<java.lang.String> accountManagerCallback, android.os.Handler handler) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("accountType is null");
        }
        if (str2 == null) {
            throw new java.lang.IllegalArgumentException("authTokenType is null");
        }
        return new android.accounts.AccountManager.Future2Task<java.lang.String>(handler, accountManagerCallback) { // from class: android.accounts.AccountManager.3
            @Override // android.accounts.AccountManager.BaseFutureTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.getAuthTokenLabel(this.mResponse, str, str2);
            }

            @Override // android.accounts.AccountManager.BaseFutureTask
            public java.lang.String bundleToResult(android.os.Bundle bundle) throws android.accounts.AuthenticatorException {
                if (!bundle.containsKey(android.accounts.AccountManager.KEY_AUTH_TOKEN_LABEL)) {
                    throw new android.accounts.AuthenticatorException("no result in response");
                }
                return bundle.getString(android.accounts.AccountManager.KEY_AUTH_TOKEN_LABEL);
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<java.lang.Boolean> hasFeatures(android.accounts.Account account, java.lang.String[] strArr, android.accounts.AccountManagerCallback<java.lang.Boolean> accountManagerCallback, android.os.Handler handler) {
        return hasFeaturesAsUser(account, strArr, accountManagerCallback, handler, this.mContext.getUserId());
    }

    private android.accounts.AccountManagerFuture<java.lang.Boolean> hasFeaturesAsUser(final android.accounts.Account account, final java.lang.String[] strArr, android.accounts.AccountManagerCallback<java.lang.Boolean> accountManagerCallback, android.os.Handler handler, final int i) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (strArr == null) {
            throw new java.lang.IllegalArgumentException("features is null");
        }
        return new android.accounts.AccountManager.Future2Task<java.lang.Boolean>(handler, accountManagerCallback) { // from class: android.accounts.AccountManager.4
            @Override // android.accounts.AccountManager.BaseFutureTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.hasFeatures(this.mResponse, account, strArr, i, android.accounts.AccountManager.this.mContext.getOpPackageName());
            }

            @Override // android.accounts.AccountManager.BaseFutureTask
            public java.lang.Boolean bundleToResult(android.os.Bundle bundle) throws android.accounts.AuthenticatorException {
                if (!bundle.containsKey(android.accounts.AccountManager.KEY_BOOLEAN_RESULT)) {
                    throw new android.accounts.AuthenticatorException("no result in response");
                }
                return java.lang.Boolean.valueOf(bundle.getBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT));
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<android.accounts.Account[]> getAccountsByTypeAndFeatures(final java.lang.String str, final java.lang.String[] strArr, android.accounts.AccountManagerCallback<android.accounts.Account[]> accountManagerCallback, android.os.Handler handler) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("type is null");
        }
        return new android.accounts.AccountManager.Future2Task<android.accounts.Account[]>(handler, accountManagerCallback) { // from class: android.accounts.AccountManager.5
            @Override // android.accounts.AccountManager.BaseFutureTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.getAccountsByFeatures(this.mResponse, str, strArr, android.accounts.AccountManager.this.mContext.getOpPackageName());
            }

            @Override // android.accounts.AccountManager.BaseFutureTask
            public android.accounts.Account[] bundleToResult(android.os.Bundle bundle) throws android.accounts.AuthenticatorException {
                if (!bundle.containsKey(android.accounts.AccountManager.KEY_ACCOUNTS)) {
                    throw new android.accounts.AuthenticatorException("no result in response");
                }
                android.os.Parcelable[] parcelableArray = bundle.getParcelableArray(android.accounts.AccountManager.KEY_ACCOUNTS);
                android.accounts.Account[] accountArr = new android.accounts.Account[parcelableArray.length];
                for (int i = 0; i < parcelableArray.length; i++) {
                    accountArr[i] = (android.accounts.Account) parcelableArray[i];
                }
                return accountArr;
            }
        }.start();
    }

    public boolean addAccountExplicitly(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            return this.mService.addAccountExplicitly(account, str, bundle, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addAccountExplicitly(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.util.Map<java.lang.String, java.lang.Integer> map) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            return this.mService.addAccountExplicitlyWithVisibility(account, str, bundle, map, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Map<java.lang.String, java.lang.Integer> getPackagesAndVisibilityForAccount(android.accounts.Account account) {
        try {
            if (account == null) {
                throw new java.lang.IllegalArgumentException("account is null");
            }
            return this.mService.getPackagesAndVisibilityForAccount(account);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Map<android.accounts.Account, java.lang.Integer> getAccountsAndVisibilityForPackage(java.lang.String str, java.lang.String str2) {
        try {
            return this.mService.getAccountsAndVisibilityForPackage(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAccountVisibility(android.accounts.Account account, java.lang.String str, int i) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            return this.mService.setAccountVisibility(account, str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAccountVisibility(android.accounts.Account account, java.lang.String str) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            return this.mService.getAccountVisibility(account, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean notifyAccountAuthenticated(android.accounts.Account account) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            return this.mService.accountAuthenticated(account);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.accounts.AccountManagerFuture<android.accounts.Account> renameAccount(final android.accounts.Account account, final java.lang.String str, android.accounts.AccountManagerCallback<android.accounts.Account> accountManagerCallback, android.os.Handler handler) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null.");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("newName is empty or null.");
        }
        return new android.accounts.AccountManager.Future2Task<android.accounts.Account>(handler, accountManagerCallback) { // from class: android.accounts.AccountManager.6
            @Override // android.accounts.AccountManager.BaseFutureTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.renameAccount(this.mResponse, account, str);
            }

            @Override // android.accounts.AccountManager.BaseFutureTask
            public android.accounts.Account bundleToResult(android.os.Bundle bundle) throws android.accounts.AuthenticatorException {
                return new android.accounts.Account(bundle.getString(android.accounts.AccountManager.KEY_ACCOUNT_NAME), bundle.getString("accountType"), bundle.getString(android.accounts.AccountManager.KEY_ACCOUNT_ACCESS_ID));
            }
        }.start();
    }

    public java.lang.String getPreviousName(android.accounts.Account account) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            return this.mService.getPreviousName(account);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.accounts.AccountManagerFuture<java.lang.Boolean> removeAccount(android.accounts.Account account, android.accounts.AccountManagerCallback<java.lang.Boolean> accountManagerCallback, android.os.Handler handler) {
        return removeAccountAsUser(account, accountManagerCallback, handler, this.mContext.getUser());
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> removeAccount(android.accounts.Account account, android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        return removeAccountAsUser(account, activity, accountManagerCallback, handler, this.mContext.getUser());
    }

    @java.lang.Deprecated
    public android.accounts.AccountManagerFuture<java.lang.Boolean> removeAccountAsUser(final android.accounts.Account account, android.accounts.AccountManagerCallback<java.lang.Boolean> accountManagerCallback, android.os.Handler handler, final android.os.UserHandle userHandle) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (userHandle == null) {
            throw new java.lang.IllegalArgumentException("userHandle is null");
        }
        return new android.accounts.AccountManager.Future2Task<java.lang.Boolean>(handler, accountManagerCallback) { // from class: android.accounts.AccountManager.7
            @Override // android.accounts.AccountManager.BaseFutureTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.removeAccountAsUser(this.mResponse, account, false, userHandle.getIdentifier());
            }

            @Override // android.accounts.AccountManager.BaseFutureTask
            public java.lang.Boolean bundleToResult(android.os.Bundle bundle) throws android.accounts.AuthenticatorException {
                if (!bundle.containsKey(android.accounts.AccountManager.KEY_BOOLEAN_RESULT)) {
                    throw new android.accounts.AuthenticatorException("no result in response");
                }
                return java.lang.Boolean.valueOf(bundle.getBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT));
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> removeAccountAsUser(final android.accounts.Account account, final android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler, final android.os.UserHandle userHandle) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (userHandle == null) {
            throw new java.lang.IllegalArgumentException("userHandle is null");
        }
        return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.8
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.removeAccountAsUser(this.mResponse, account, activity != null, userHandle.getIdentifier());
            }
        }.start();
    }

    public boolean removeAccountExplicitly(android.accounts.Account account) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            return this.mService.removeAccountExplicitly(account);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void invalidateAuthToken(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("accountType is null");
        }
        if (str2 != null) {
            try {
                this.mService.invalidateAuthToken(str, str2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.lang.String peekAuthToken(android.accounts.Account account, java.lang.String str) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("authTokenType is null");
        }
        try {
            return this.mService.peekAuthToken(account, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPassword(android.accounts.Account account, java.lang.String str) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            this.mService.setPassword(account, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearPassword(android.accounts.Account account) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        try {
            this.mService.clearPassword(account);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserData(android.accounts.Account account, java.lang.String str, java.lang.String str2) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("key is null");
        }
        try {
            this.mService.setUserData(account, str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAuthToken(android.accounts.Account account, java.lang.String str, java.lang.String str2) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("authTokenType is null");
        }
        try {
            this.mService.setAuthToken(account, str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String blockingGetAuthToken(android.accounts.Account account, java.lang.String str, boolean z) throws android.accounts.OperationCanceledException, java.io.IOException, android.accounts.AuthenticatorException {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("authTokenType is null");
        }
        android.os.Bundle result = getAuthToken(account, str, z, null, null).getResult();
        if (result == null) {
            android.util.Log.e(TAG, "blockingGetAuthToken: null was returned from getResult() for " + account + ", authTokenType " + str);
            return null;
        }
        return result.getString(KEY_AUTHTOKEN);
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> getAuthToken(final android.accounts.Account account, final java.lang.String str, android.os.Bundle bundle, android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("authTokenType is null");
        }
        final android.os.Bundle bundle2 = new android.os.Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.9
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.getAuthToken(this.mResponse, account, str, false, true, bundle2);
            }
        }.start();
    }

    @java.lang.Deprecated
    public android.accounts.AccountManagerFuture<android.os.Bundle> getAuthToken(android.accounts.Account account, java.lang.String str, boolean z, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        return getAuthToken(account, str, (android.os.Bundle) null, z, accountManagerCallback, handler);
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> getAuthToken(final android.accounts.Account account, final java.lang.String str, android.os.Bundle bundle, final boolean z, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("authTokenType is null");
        }
        final android.os.Bundle bundle2 = new android.os.Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        return new android.accounts.AccountManager.AmsTask(null, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.10
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.getAuthToken(this.mResponse, account, str, z, false, bundle2);
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> addAccount(final java.lang.String str, final java.lang.String str2, final java.lang.String[] strArr, android.os.Bundle bundle, final android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        if (android.os.Process.myUserHandle().equals(this.mContext.getUser())) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("accountType is null");
            }
            final android.os.Bundle bundle2 = new android.os.Bundle();
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
            return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.11
                @Override // android.accounts.AccountManager.AmsTask
                public void doWork() throws android.os.RemoteException {
                    android.accounts.AccountManager.this.mService.addAccount(this.mResponse, str, str2, strArr, activity != null, bundle2);
                }
            }.start();
        }
        return addAccountAsUser(str, str2, strArr, bundle, activity, accountManagerCallback, handler, this.mContext.getUser());
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> addAccountAsUser(final java.lang.String str, final java.lang.String str2, final java.lang.String[] strArr, android.os.Bundle bundle, final android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler, final android.os.UserHandle userHandle) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("accountType is null");
        }
        if (userHandle == null) {
            throw new java.lang.IllegalArgumentException("userHandle is null");
        }
        final android.os.Bundle bundle2 = new android.os.Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.12
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.addAccountAsUser(this.mResponse, str, str2, strArr, activity != null, bundle2, userHandle.getIdentifier());
            }
        }.start();
    }

    public void addSharedAccountsFromParentUser(android.os.UserHandle userHandle, android.os.UserHandle userHandle2) {
        try {
            this.mService.addSharedAccountsFromParentUser(userHandle.getIdentifier(), userHandle2.getIdentifier(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.accounts.AccountManagerFuture<java.lang.Boolean> copyAccountToUser(final android.accounts.Account account, final android.os.UserHandle userHandle, final android.os.UserHandle userHandle2, android.accounts.AccountManagerCallback<java.lang.Boolean> accountManagerCallback, android.os.Handler handler) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (userHandle2 == null || userHandle == null) {
            throw new java.lang.IllegalArgumentException("fromUser and toUser cannot be null");
        }
        return new android.accounts.AccountManager.Future2Task<java.lang.Boolean>(handler, accountManagerCallback) { // from class: android.accounts.AccountManager.13
            @Override // android.accounts.AccountManager.BaseFutureTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.copyAccountToUser(this.mResponse, account, userHandle.getIdentifier(), userHandle2.getIdentifier());
            }

            @Override // android.accounts.AccountManager.BaseFutureTask
            public java.lang.Boolean bundleToResult(android.os.Bundle bundle) throws android.accounts.AuthenticatorException {
                if (!bundle.containsKey(android.accounts.AccountManager.KEY_BOOLEAN_RESULT)) {
                    throw new android.accounts.AuthenticatorException("no result in response");
                }
                return java.lang.Boolean.valueOf(bundle.getBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT));
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> confirmCredentials(android.accounts.Account account, android.os.Bundle bundle, android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        return confirmCredentialsAsUser(account, bundle, activity, accountManagerCallback, handler, this.mContext.getUser());
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> confirmCredentialsAsUser(final android.accounts.Account account, final android.os.Bundle bundle, final android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler, android.os.UserHandle userHandle) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        final int identifier = userHandle.getIdentifier();
        return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.14
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.confirmCredentialsAsUser(this.mResponse, account, bundle, activity != null, identifier);
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> updateCredentials(final android.accounts.Account account, final java.lang.String str, final android.os.Bundle bundle, final android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.15
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.updateCredentials(this.mResponse, account, str, activity != null, bundle);
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> editProperties(final java.lang.String str, final android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("accountType is null");
        }
        return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.16
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.editProperties(this.mResponse, str, activity != null);
            }
        }.start();
    }

    public boolean someUserHasAccount(android.accounts.Account account) {
        try {
            return this.mService.someUserHasAccount(account);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureNotOnMainThread() {
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null && myLooper == this.mContext.getMainLooper()) {
            java.lang.IllegalStateException illegalStateException = new java.lang.IllegalStateException("calling this from your main thread can lead to deadlock");
            android.util.Log.e(TAG, "calling this from your main thread can lead to deadlock and/or ANRs", illegalStateException);
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 8) {
                throw illegalStateException;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postToHandler(android.os.Handler handler, final android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, final android.accounts.AccountManagerFuture<android.os.Bundle> accountManagerFuture) {
        if (handler == null) {
            handler = this.mMainHandler;
        }
        handler.post(new java.lang.Runnable() { // from class: android.accounts.AccountManager.17
            @Override // java.lang.Runnable
            public void run() {
                accountManagerCallback.run(accountManagerFuture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postToHandler(android.os.Handler handler, final android.accounts.OnAccountsUpdateListener onAccountsUpdateListener, android.accounts.Account[] accountArr) {
        int length = accountArr.length;
        final android.accounts.Account[] accountArr2 = new android.accounts.Account[length];
        java.lang.System.arraycopy(accountArr, 0, accountArr2, 0, length);
        if (handler == null) {
            handler = this.mMainHandler;
        }
        handler.post(new java.lang.Runnable() { // from class: android.accounts.AccountManager.18
            @Override // java.lang.Runnable
            public void run() {
                synchronized (android.accounts.AccountManager.this.mAccountsUpdatedListeners) {
                    try {
                        if (android.accounts.AccountManager.this.mAccountsUpdatedListeners.containsKey(onAccountsUpdateListener)) {
                            java.util.Set set = (java.util.Set) android.accounts.AccountManager.this.mAccountsUpdatedListenersTypes.get(onAccountsUpdateListener);
                            if (set != null) {
                                java.util.ArrayList arrayList = new java.util.ArrayList();
                                for (android.accounts.Account account : accountArr2) {
                                    if (set.contains(account.type)) {
                                        arrayList.add(account);
                                    }
                                }
                                onAccountsUpdateListener.onAccountsUpdated((android.accounts.Account[]) arrayList.toArray(new android.accounts.Account[arrayList.size()]));
                            } else {
                                onAccountsUpdateListener.onAccountsUpdated(accountArr2);
                            }
                        }
                    } catch (android.database.SQLException e) {
                        android.util.Log.e(android.accounts.AccountManager.TAG, "Can't update accounts", e);
                    }
                }
            }
        });
    }

    private abstract class AmsTask extends java.util.concurrent.FutureTask<android.os.Bundle> implements android.accounts.AccountManagerFuture<android.os.Bundle> {
        final android.app.Activity mActivity;
        final android.accounts.AccountManagerCallback<android.os.Bundle> mCallback;
        final android.os.Handler mHandler;
        final android.accounts.IAccountManagerResponse mResponse;

        public abstract void doWork() throws android.os.RemoteException;

        public AmsTask(android.app.Activity activity, android.os.Handler handler, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback) {
            super(new java.util.concurrent.Callable<android.os.Bundle>() { // from class: android.accounts.AccountManager.AmsTask.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public android.os.Bundle call() throws java.lang.Exception {
                    throw new java.lang.IllegalStateException("this should never be called");
                }
            });
            this.mHandler = handler;
            this.mCallback = accountManagerCallback;
            this.mActivity = activity;
            this.mResponse = new android.accounts.AccountManager.AmsTask.Response();
        }

        public final android.accounts.AccountManagerFuture<android.os.Bundle> start() {
            try {
                doWork();
            } catch (android.os.RemoteException e) {
                setException(e);
            }
            return this;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.util.concurrent.FutureTask
        public void set(android.os.Bundle bundle) {
            if (bundle == null) {
                android.util.Log.e(android.accounts.AccountManager.TAG, "the bundle must not be null", new java.lang.Exception());
            }
            super.set((android.accounts.AccountManager.AmsTask) bundle);
        }

        private android.os.Bundle internalGetResult(java.lang.Long l, java.util.concurrent.TimeUnit timeUnit) throws android.accounts.OperationCanceledException, java.io.IOException, android.accounts.AuthenticatorException {
            if (!isDone()) {
                android.accounts.AccountManager.this.ensureNotOnMainThread();
            }
            try {
                try {
                    try {
                        return l == null ? get() : get(l.longValue(), timeUnit);
                    } catch (java.lang.InterruptedException | java.util.concurrent.CancellationException | java.util.concurrent.TimeoutException e) {
                        throw new android.accounts.OperationCanceledException(e);
                    }
                } catch (java.util.concurrent.ExecutionException e2) {
                    java.lang.Throwable cause = e2.getCause();
                    if (cause instanceof java.io.IOException) {
                        throw ((java.io.IOException) cause);
                    }
                    if (cause instanceof java.lang.UnsupportedOperationException) {
                        throw new android.accounts.AuthenticatorException(cause);
                    }
                    if (cause instanceof android.accounts.AuthenticatorException) {
                        throw ((android.accounts.AuthenticatorException) cause);
                    }
                    if (cause instanceof java.lang.RuntimeException) {
                        throw ((java.lang.RuntimeException) cause);
                    }
                    if (cause instanceof java.lang.Error) {
                        throw ((java.lang.Error) cause);
                    }
                    throw new java.lang.IllegalStateException(cause);
                }
            } finally {
                cancel(true);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.accounts.AccountManagerFuture
        public android.os.Bundle getResult() throws android.accounts.OperationCanceledException, java.io.IOException, android.accounts.AuthenticatorException {
            return internalGetResult(null, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.accounts.AccountManagerFuture
        public android.os.Bundle getResult(long j, java.util.concurrent.TimeUnit timeUnit) throws android.accounts.OperationCanceledException, java.io.IOException, android.accounts.AuthenticatorException {
            return internalGetResult(java.lang.Long.valueOf(j), timeUnit);
        }

        @Override // java.util.concurrent.FutureTask
        protected void done() {
            if (this.mCallback != null) {
                android.accounts.AccountManager.this.postToHandler(this.mHandler, this.mCallback, this);
            }
        }

        private class Response extends android.accounts.IAccountManagerResponse.Stub {
            private Response() {
            }

            @Override // android.accounts.IAccountManagerResponse
            public void onResult(android.os.Bundle bundle) {
                if (bundle == null) {
                    onError(5, "null bundle returned");
                    return;
                }
                android.content.Intent intent = (android.content.Intent) bundle.getParcelable("intent", android.content.Intent.class);
                if (intent != null && android.accounts.AccountManager.AmsTask.this.mActivity != null) {
                    android.accounts.AccountManager.AmsTask.this.mActivity.startActivity(intent);
                } else {
                    if (bundle.getBoolean("retry")) {
                        try {
                            android.accounts.AccountManager.AmsTask.this.doWork();
                            return;
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                    android.accounts.AccountManager.AmsTask.this.set(bundle);
                }
            }

            @Override // android.accounts.IAccountManagerResponse
            public void onError(int i, java.lang.String str) {
                if (i != 4 && i != 100 && i != 101) {
                    android.accounts.AccountManager.AmsTask.this.setException(android.accounts.AccountManager.this.convertErrorToException(i, str));
                } else {
                    android.accounts.AccountManager.AmsTask.this.cancel(true);
                }
            }
        }
    }

    private abstract class BaseFutureTask<T> extends java.util.concurrent.FutureTask<T> {
        final android.os.Handler mHandler;
        public final android.accounts.IAccountManagerResponse mResponse;

        public abstract T bundleToResult(android.os.Bundle bundle) throws android.accounts.AuthenticatorException;

        public abstract void doWork() throws android.os.RemoteException;

        public BaseFutureTask(android.os.Handler handler) {
            super(new java.util.concurrent.Callable<T>() { // from class: android.accounts.AccountManager.BaseFutureTask.1
                @Override // java.util.concurrent.Callable
                public T call() throws java.lang.Exception {
                    throw new java.lang.IllegalStateException("this should never be called");
                }
            });
            this.mHandler = handler;
            this.mResponse = new android.accounts.AccountManager.BaseFutureTask.Response();
        }

        protected void postRunnableToHandler(java.lang.Runnable runnable) {
            (this.mHandler == null ? android.accounts.AccountManager.this.mMainHandler : this.mHandler).post(runnable);
        }

        protected void startTask() {
            try {
                doWork();
            } catch (android.os.RemoteException e) {
                setException(e);
            }
        }

        protected class Response extends android.accounts.IAccountManagerResponse.Stub {
            protected Response() {
            }

            @Override // android.accounts.IAccountManagerResponse
            public void onResult(android.os.Bundle bundle) {
                try {
                    java.lang.Object bundleToResult = android.accounts.AccountManager.BaseFutureTask.this.bundleToResult(bundle);
                    if (bundleToResult != null) {
                        android.accounts.AccountManager.BaseFutureTask.this.set(bundleToResult);
                    }
                } catch (android.accounts.AuthenticatorException | java.lang.ClassCastException e) {
                    onError(5, "no result in response");
                }
            }

            @Override // android.accounts.IAccountManagerResponse
            public void onError(int i, java.lang.String str) {
                if (i != 4 && i != 100 && i != 101) {
                    android.accounts.AccountManager.BaseFutureTask.this.setException(android.accounts.AccountManager.this.convertErrorToException(i, str));
                } else {
                    android.accounts.AccountManager.BaseFutureTask.this.cancel(true);
                }
            }
        }
    }

    private abstract class Future2Task<T> extends android.accounts.AccountManager.BaseFutureTask<T> implements android.accounts.AccountManagerFuture<T> {
        final android.accounts.AccountManagerCallback<T> mCallback;

        public Future2Task(android.os.Handler handler, android.accounts.AccountManagerCallback<T> accountManagerCallback) {
            super(handler);
            this.mCallback = accountManagerCallback;
        }

        @Override // java.util.concurrent.FutureTask
        protected void done() {
            if (this.mCallback != null) {
                postRunnableToHandler(new java.lang.Runnable() { // from class: android.accounts.AccountManager.Future2Task.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.accounts.AccountManager.Future2Task.this.mCallback.run(android.accounts.AccountManager.Future2Task.this);
                    }
                });
            }
        }

        public android.accounts.AccountManager.Future2Task<T> start() {
            startTask();
            return this;
        }

        private T internalGetResult(java.lang.Long l, java.util.concurrent.TimeUnit timeUnit) throws android.accounts.OperationCanceledException, java.io.IOException, android.accounts.AuthenticatorException {
            if (!isDone()) {
                android.accounts.AccountManager.this.ensureNotOnMainThread();
            }
            try {
                try {
                    return l == null ? (T) get() : (T) get(l.longValue(), timeUnit);
                } catch (java.lang.InterruptedException e) {
                    cancel(true);
                    throw new android.accounts.OperationCanceledException();
                } catch (java.util.concurrent.CancellationException e2) {
                    cancel(true);
                    throw new android.accounts.OperationCanceledException();
                } catch (java.util.concurrent.ExecutionException e3) {
                    java.lang.Throwable cause = e3.getCause();
                    if (cause instanceof java.io.IOException) {
                        throw ((java.io.IOException) cause);
                    }
                    if (cause instanceof java.lang.UnsupportedOperationException) {
                        throw new android.accounts.AuthenticatorException(cause);
                    }
                    if (cause instanceof android.accounts.AuthenticatorException) {
                        throw ((android.accounts.AuthenticatorException) cause);
                    }
                    if (cause instanceof java.lang.RuntimeException) {
                        throw ((java.lang.RuntimeException) cause);
                    }
                    if (cause instanceof java.lang.Error) {
                        throw ((java.lang.Error) cause);
                    }
                    throw new java.lang.IllegalStateException(cause);
                } catch (java.util.concurrent.TimeoutException e4) {
                    cancel(true);
                    throw new android.accounts.OperationCanceledException();
                }
            } finally {
                cancel(true);
            }
        }

        @Override // android.accounts.AccountManagerFuture
        public T getResult() throws android.accounts.OperationCanceledException, java.io.IOException, android.accounts.AuthenticatorException {
            return internalGetResult(null, null);
        }

        @Override // android.accounts.AccountManagerFuture
        public T getResult(long j, java.util.concurrent.TimeUnit timeUnit) throws android.accounts.OperationCanceledException, java.io.IOException, android.accounts.AuthenticatorException {
            return internalGetResult(java.lang.Long.valueOf(j), timeUnit);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.Exception convertErrorToException(int i, java.lang.String str) {
        if (i == 3) {
            return new java.io.IOException(str);
        }
        if (i == 6) {
            return new java.lang.UnsupportedOperationException(str);
        }
        if (i == 5) {
            return new android.accounts.AuthenticatorException(str);
        }
        if (i == 7) {
            return new java.lang.IllegalArgumentException(str);
        }
        return new android.accounts.AuthenticatorException(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAccountByTypeAndFeatures(final java.lang.String str, final java.lang.String[] strArr, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        new android.accounts.AccountManager.AmsTask(null, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.19
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.getAccountByTypeAndFeatures(this.mResponse, str, strArr, android.accounts.AccountManager.this.mContext.getOpPackageName());
            }
        }.start();
    }

    private class GetAuthTokenByTypeAndFeaturesTask extends android.accounts.AccountManager.AmsTask implements android.accounts.AccountManagerCallback<android.os.Bundle> {
        final java.lang.String mAccountType;
        final android.os.Bundle mAddAccountOptions;
        final java.lang.String mAuthTokenType;
        final java.lang.String[] mFeatures;
        volatile android.accounts.AccountManagerFuture<android.os.Bundle> mFuture;
        final android.os.Bundle mLoginOptions;
        final android.accounts.AccountManagerCallback<android.os.Bundle> mMyCallback;
        private volatile int mNumAccounts;

        GetAuthTokenByTypeAndFeaturesTask(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.app.Activity activity, android.os.Bundle bundle, android.os.Bundle bundle2, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
            super(activity, handler, accountManagerCallback);
            this.mFuture = null;
            this.mNumAccounts = 0;
            if (str == null) {
                throw new java.lang.IllegalArgumentException("account type is null");
            }
            this.mAccountType = str;
            this.mAuthTokenType = str2;
            this.mFeatures = strArr;
            this.mAddAccountOptions = bundle;
            this.mLoginOptions = bundle2;
            this.mMyCallback = this;
        }

        @Override // android.accounts.AccountManager.AmsTask
        public void doWork() throws android.os.RemoteException {
            android.accounts.AccountManager.this.getAccountByTypeAndFeatures(this.mAccountType, this.mFeatures, new android.accounts.AccountManagerCallback<android.os.Bundle>() { // from class: android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.1
                @Override // android.accounts.AccountManagerCallback
                public void run(android.accounts.AccountManagerFuture<android.os.Bundle> accountManagerFuture) {
                    try {
                        android.os.Bundle result = accountManagerFuture.getResult();
                        java.lang.String string = result.getString(android.accounts.AccountManager.KEY_ACCOUNT_NAME);
                        java.lang.String string2 = result.getString("accountType");
                        if (string == null) {
                            if (android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mActivity != null) {
                                android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mFuture = android.accounts.AccountManager.this.addAccount(android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mAccountType, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mFeatures, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mAddAccountOptions, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mActivity, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                                return;
                            }
                            android.os.Bundle bundle = new android.os.Bundle();
                            bundle.putString(android.accounts.AccountManager.KEY_ACCOUNT_NAME, null);
                            bundle.putString("accountType", null);
                            bundle.putString(android.accounts.AccountManager.KEY_AUTHTOKEN, null);
                            bundle.putBinder(android.accounts.AccountManager.KEY_ACCOUNT_ACCESS_ID, null);
                            try {
                                android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mResponse.onResult(bundle);
                                return;
                            } catch (android.os.RemoteException e) {
                                return;
                            }
                        }
                        android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mNumAccounts = 1;
                        android.accounts.Account account = new android.accounts.Account(string, string2);
                        if (android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mActivity == null) {
                            android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mFuture = android.accounts.AccountManager.this.getAuthToken(account, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, false, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                        } else {
                            android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mFuture = android.accounts.AccountManager.this.getAuthToken(account, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mAuthTokenType, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mLoginOptions, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mActivity, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mMyCallback, android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.mHandler);
                        }
                    } catch (android.accounts.AuthenticatorException e2) {
                        android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.setException(e2);
                    } catch (android.accounts.OperationCanceledException e3) {
                        android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.setException(e3);
                    } catch (java.io.IOException e4) {
                        android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask.this.setException(e4);
                    }
                }
            }, this.mHandler);
        }

        @Override // android.accounts.AccountManagerCallback
        public void run(android.accounts.AccountManagerFuture<android.os.Bundle> accountManagerFuture) {
            try {
                android.os.Bundle result = accountManagerFuture.getResult();
                if (this.mNumAccounts == 0) {
                    java.lang.String string = result.getString(android.accounts.AccountManager.KEY_ACCOUNT_NAME);
                    java.lang.String string2 = result.getString("accountType");
                    if (!android.text.TextUtils.isEmpty(string) && !android.text.TextUtils.isEmpty(string2)) {
                        android.accounts.Account account = new android.accounts.Account(string, string2, result.getString(android.accounts.AccountManager.KEY_ACCOUNT_ACCESS_ID));
                        this.mNumAccounts = 1;
                        android.accounts.AccountManager.this.getAuthToken(account, this.mAuthTokenType, (android.os.Bundle) null, this.mActivity, this.mMyCallback, this.mHandler);
                        return;
                    }
                    setException(new android.accounts.AuthenticatorException("account not in result"));
                    return;
                }
                set(result);
            } catch (android.accounts.AuthenticatorException e) {
                setException(e);
            } catch (android.accounts.OperationCanceledException e2) {
                cancel(true);
            } catch (java.io.IOException e3) {
                setException(e3);
            }
        }
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> getAuthTokenByFeatures(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, android.app.Activity activity, android.os.Bundle bundle, android.os.Bundle bundle2, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("account type is null");
        }
        if (str2 == null) {
            throw new java.lang.IllegalArgumentException("authTokenType is null");
        }
        android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask getAuthTokenByTypeAndFeaturesTask = new android.accounts.AccountManager.GetAuthTokenByTypeAndFeaturesTask(str, str2, strArr, activity, bundle, bundle2, accountManagerCallback, handler);
        getAuthTokenByTypeAndFeaturesTask.start();
        return getAuthTokenByTypeAndFeaturesTask;
    }

    @java.lang.Deprecated
    public static android.content.Intent newChooseAccountIntent(android.accounts.Account account, java.util.ArrayList<android.accounts.Account> arrayList, java.lang.String[] strArr, boolean z, java.lang.String str, java.lang.String str2, java.lang.String[] strArr2, android.os.Bundle bundle) {
        return newChooseAccountIntent(account, arrayList, strArr, str, str2, strArr2, bundle);
    }

    public static android.content.Intent newChooseAccountIntent(android.accounts.Account account, java.util.List<android.accounts.Account> list, java.lang.String[] strArr, java.lang.String str, java.lang.String str2, java.lang.String[] strArr2, android.os.Bundle bundle) {
        android.content.Intent intent = new android.content.Intent();
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(android.content.res.Resources.getSystem().getString(com.android.internal.R.string.config_chooseTypeAndAccountActivity));
        intent.setClassName(unflattenFromString.getPackageName(), unflattenFromString.getClassName());
        intent.putExtra(android.accounts.ChooseTypeAndAccountActivity.EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST, list == null ? null : new java.util.ArrayList(list));
        intent.putExtra(android.accounts.ChooseTypeAndAccountActivity.EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY, strArr);
        intent.putExtra(android.accounts.ChooseTypeAndAccountActivity.EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE, bundle);
        intent.putExtra(android.accounts.ChooseTypeAndAccountActivity.EXTRA_SELECTED_ACCOUNT, account);
        intent.putExtra(android.accounts.ChooseTypeAndAccountActivity.EXTRA_DESCRIPTION_TEXT_OVERRIDE, str);
        intent.putExtra("authTokenType", str2);
        intent.putExtra(android.accounts.ChooseTypeAndAccountActivity.EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY, strArr2);
        return intent;
    }

    public void addOnAccountsUpdatedListener(android.accounts.OnAccountsUpdateListener onAccountsUpdateListener, android.os.Handler handler, boolean z) {
        addOnAccountsUpdatedListener(onAccountsUpdateListener, handler, z, null);
    }

    public void addOnAccountsUpdatedListener(android.accounts.OnAccountsUpdateListener onAccountsUpdateListener, android.os.Handler handler, boolean z, java.lang.String[] strArr) {
        if (onAccountsUpdateListener == null) {
            throw new java.lang.IllegalArgumentException("the listener is null");
        }
        synchronized (this.mAccountsUpdatedListeners) {
            if (this.mAccountsUpdatedListeners.containsKey(onAccountsUpdateListener)) {
                throw new java.lang.IllegalStateException("this listener is already added");
            }
            boolean isEmpty = this.mAccountsUpdatedListeners.isEmpty();
            this.mAccountsUpdatedListeners.put(onAccountsUpdateListener, handler);
            if (strArr != null) {
                this.mAccountsUpdatedListenersTypes.put(onAccountsUpdateListener, new java.util.HashSet(java.util.Arrays.asList(strArr)));
            } else {
                this.mAccountsUpdatedListenersTypes.put(onAccountsUpdateListener, null);
            }
            if (isEmpty) {
                android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                intentFilter.addAction(ACTION_VISIBLE_ACCOUNTS_CHANGED);
                intentFilter.addAction(android.content.Intent.ACTION_DEVICE_STORAGE_OK);
                this.mContext.registerReceiver(this.mAccountsChangedBroadcastReceiver, intentFilter);
            }
            try {
                this.mService.registerAccountListener(strArr, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (z) {
            postToHandler(handler, onAccountsUpdateListener, getAccounts());
        }
    }

    public void removeOnAccountsUpdatedListener(android.accounts.OnAccountsUpdateListener onAccountsUpdateListener) {
        java.lang.String[] strArr;
        if (onAccountsUpdateListener == null) {
            throw new java.lang.IllegalArgumentException("listener is null");
        }
        synchronized (this.mAccountsUpdatedListeners) {
            if (!this.mAccountsUpdatedListeners.containsKey(onAccountsUpdateListener)) {
                android.util.Log.e(TAG, "Listener was not previously added");
                return;
            }
            java.util.Set<java.lang.String> set = this.mAccountsUpdatedListenersTypes.get(onAccountsUpdateListener);
            if (set != null) {
                strArr = (java.lang.String[]) set.toArray(new java.lang.String[set.size()]);
            } else {
                strArr = null;
            }
            this.mAccountsUpdatedListeners.remove(onAccountsUpdateListener);
            this.mAccountsUpdatedListenersTypes.remove(onAccountsUpdateListener);
            if (this.mAccountsUpdatedListeners.isEmpty()) {
                this.mContext.unregisterReceiver(this.mAccountsChangedBroadcastReceiver);
            }
            try {
                this.mService.unregisterAccountListener(strArr, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> startAddAccountSession(final java.lang.String str, final java.lang.String str2, final java.lang.String[] strArr, android.os.Bundle bundle, final android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("accountType is null");
        }
        final android.os.Bundle bundle2 = new android.os.Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.21
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.startAddAccountSession(this.mResponse, str, str2, strArr, activity != null, bundle2);
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> startUpdateCredentialsSession(final android.accounts.Account account, final java.lang.String str, android.os.Bundle bundle, final android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        final android.os.Bundle bundle2 = new android.os.Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.22
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.startUpdateCredentialsSession(this.mResponse, account, str, activity != null, bundle2);
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<android.os.Bundle> finishSession(android.os.Bundle bundle, android.app.Activity activity, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        return finishSessionAsUser(bundle, activity, this.mContext.getUser(), accountManagerCallback, handler);
    }

    @android.annotation.SystemApi
    public android.accounts.AccountManagerFuture<android.os.Bundle> finishSessionAsUser(final android.os.Bundle bundle, final android.app.Activity activity, final android.os.UserHandle userHandle, android.accounts.AccountManagerCallback<android.os.Bundle> accountManagerCallback, android.os.Handler handler) {
        if (bundle == null) {
            throw new java.lang.IllegalArgumentException("sessionBundle is null");
        }
        final android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.mContext.getPackageName());
        return new android.accounts.AccountManager.AmsTask(activity, handler, accountManagerCallback) { // from class: android.accounts.AccountManager.23
            @Override // android.accounts.AccountManager.AmsTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.finishSessionAsUser(this.mResponse, bundle, activity != null, bundle2, userHandle.getIdentifier());
            }
        }.start();
    }

    public android.accounts.AccountManagerFuture<java.lang.Boolean> isCredentialsUpdateSuggested(final android.accounts.Account account, final java.lang.String str, android.accounts.AccountManagerCallback<java.lang.Boolean> accountManagerCallback, android.os.Handler handler) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("status token is empty");
        }
        return new android.accounts.AccountManager.Future2Task<java.lang.Boolean>(handler, accountManagerCallback) { // from class: android.accounts.AccountManager.24
            @Override // android.accounts.AccountManager.BaseFutureTask
            public void doWork() throws android.os.RemoteException {
                android.accounts.AccountManager.this.mService.isCredentialsUpdateSuggested(this.mResponse, account, str);
            }

            @Override // android.accounts.AccountManager.BaseFutureTask
            public java.lang.Boolean bundleToResult(android.os.Bundle bundle) throws android.accounts.AuthenticatorException {
                if (!bundle.containsKey(android.accounts.AccountManager.KEY_BOOLEAN_RESULT)) {
                    throw new android.accounts.AuthenticatorException("no result in response");
                }
                return java.lang.Boolean.valueOf(bundle.getBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT));
            }
        }.start();
    }

    public boolean hasAccountAccess(android.accounts.Account account, java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.hasAccountAccess(account, str, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.IntentSender createRequestAccountAccessIntentSenderAsUser(android.accounts.Account account, java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.createRequestAccountAccessIntentSenderAsUser(account, str, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void invalidateLocalAccountsDataCaches() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_ACCOUNTS_DATA_PROPERTY);
    }

    public void disableLocalAccountCaches() {
        this.mAccountsForUserCache.disableLocal();
    }

    public static void invalidateLocalAccountUserDataCaches() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_USER_DATA_PROPERTY);
    }

    public void disableLocalUserInfoCaches() {
        this.mUserDataCache.disableLocal();
    }
}
