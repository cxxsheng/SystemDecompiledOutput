package com.android.server.accounts;

/* loaded from: classes.dex */
public class AccountManagerService extends android.accounts.IAccountManager.Stub implements android.content.pm.RegisteredServicesCacheListener<android.accounts.AuthenticatorDescription> {
    private static final android.accounts.Account[] EMPTY_ACCOUNT_ARRAY;
    private static final long ENFORCE_PACKAGE_VISIBILITY_FILTERING = 154726397;
    private static final int MESSAGE_COPY_SHARED_ACCOUNT = 4;
    private static final int MESSAGE_TIMED_OUT = 3;
    private static final java.lang.String PRE_N_DATABASE_NAME = "accounts.db";
    private static final int SIGNATURE_CHECK_MATCH = 1;
    private static final int SIGNATURE_CHECK_MISMATCH = 0;
    private static final int SIGNATURE_CHECK_UID_MATCH = 2;
    private static final java.lang.String TAG = "AccountManagerService";
    private static final int TIMEOUT_DELAY_MS = 900000;
    private static java.util.concurrent.atomic.AtomicReference<com.android.server.accounts.AccountManagerService> sThis;
    private final android.app.AppOpsManager mAppOpsManager;
    private final com.android.server.accounts.IAccountAuthenticatorCache mAuthenticatorCache;
    final android.content.Context mContext;
    final com.android.server.accounts.AccountManagerService.MessageHandler mHandler;
    private final com.android.server.accounts.AccountManagerService.Injector mInjector;
    private final android.content.pm.PackageManager mPackageManager;
    private android.os.UserManager mUserManager;
    private static final android.os.Bundle ACCOUNTS_CHANGED_OPTIONS = new android.app.BroadcastOptions().setDeliveryGroupPolicy(1).toBundle();
    private static final android.content.Intent ACCOUNTS_CHANGED_INTENT = new android.content.Intent("android.accounts.LOGIN_ACCOUNTS_CHANGED");
    private final java.util.LinkedHashMap<java.lang.String, com.android.server.accounts.AccountManagerService.Session> mSessions = new java.util.LinkedHashMap<>();
    private final android.util.SparseArray<com.android.server.accounts.AccountManagerService.UserAccounts> mUsers = new android.util.SparseArray<>();
    private final android.util.SparseBooleanArray mLocalUnlockedUsers = new android.util.SparseBooleanArray();
    private final java.text.SimpleDateFormat mDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private java.util.concurrent.CopyOnWriteArrayList<android.accounts.AccountManagerInternal.OnAppPermissionChangeListener> mAppPermissionChangeListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.accounts.AccountManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mService = new com.android.server.accounts.AccountManagerService(new com.android.server.accounts.AccountManagerService.Injector(getContext()));
            publishBinderService("account", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onUnlockUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            android.util.Slog.i(com.android.server.accounts.AccountManagerService.TAG, "onUserStopped " + targetUser);
            this.mService.purgeUserData(targetUser.getUserIdentifier());
        }
    }

    static {
        ACCOUNTS_CHANGED_INTENT.setFlags(android.hardware.audio.common.V2_0.AudioFormat.HE_AAC_V1);
        sThis = new java.util.concurrent.atomic.AtomicReference<>();
        EMPTY_ACCOUNT_ARRAY = new android.accounts.Account[0];
    }

    static class UserAccounts {
        final com.android.server.accounts.AccountsDb accountsDb;
        private final int userId;
        private final java.util.HashMap<android.util.Pair<android.util.Pair<android.accounts.Account, java.lang.String>, java.lang.Integer>, com.android.server.accounts.AccountManagerService.NotificationId> credentialsPermissionNotificationIds = new java.util.HashMap<>();
        private final java.util.HashMap<android.accounts.Account, com.android.server.accounts.AccountManagerService.NotificationId> signinRequiredNotificationIds = new java.util.HashMap<>();
        final java.lang.Object cacheLock = new java.lang.Object();
        final java.lang.Object dbLock = new java.lang.Object();
        final java.util.HashMap<java.lang.String, android.accounts.Account[]> accountCache = new java.util.LinkedHashMap();
        private final java.util.Map<android.accounts.Account, java.util.Map<java.lang.String, java.lang.String>> userDataCache = new java.util.HashMap();
        private final java.util.Map<android.accounts.Account, java.util.Map<java.lang.String, java.lang.String>> authTokenCache = new java.util.HashMap();
        private final com.android.server.accounts.TokenCache accountTokenCaches = new com.android.server.accounts.TokenCache();
        private final java.util.Map<android.accounts.Account, java.util.Map<java.lang.String, java.lang.Integer>> visibilityCache = new java.util.HashMap();
        private final java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Integer>> mReceiversForType = new java.util.HashMap();
        private final java.util.HashMap<android.accounts.Account, java.util.concurrent.atomic.AtomicReference<java.lang.String>> previousNameCache = new java.util.HashMap<>();

        UserAccounts(android.content.Context context, int i, java.io.File file, java.io.File file2) {
            this.userId = i;
            synchronized (this.dbLock) {
                synchronized (this.cacheLock) {
                    this.accountsDb = com.android.server.accounts.AccountsDb.create(context, i, file, file2);
                }
            }
        }
    }

    public static com.android.server.accounts.AccountManagerService getSingleton() {
        return sThis.get();
    }

    public AccountManagerService(com.android.server.accounts.AccountManagerService.Injector injector) {
        this.mInjector = injector;
        this.mContext = injector.getContext();
        this.mPackageManager = this.mContext.getPackageManager();
        this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mHandler = new com.android.server.accounts.AccountManagerService.MessageHandler(injector.getMessageHandlerLooper());
        this.mAuthenticatorCache = this.mInjector.getAccountAuthenticatorCache();
        this.mAuthenticatorCache.setListener(this, this.mHandler);
        sThis.set(this);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.accounts.AccountManagerService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    final java.lang.String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    com.android.server.accounts.AccountManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.accounts.AccountManagerService.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            com.android.server.accounts.AccountManagerService.this.purgeOldGrantsAll();
                            com.android.server.accounts.AccountManagerService.this.removeVisibilityValuesForPackage(schemeSpecificPart);
                        }
                    });
                }
            }
        }, intentFilter);
        injector.addLocalService(new com.android.server.accounts.AccountManagerService.AccountManagerInternalImpl());
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.accounts.AccountManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                int intExtra;
                if (!"android.intent.action.USER_REMOVED".equals(intent.getAction()) || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1)) < 1) {
                    return;
                }
                android.util.Slog.i(com.android.server.accounts.AccountManagerService.TAG, "User " + intExtra + " removed");
                com.android.server.accounts.AccountManagerService.this.purgeUserData(intExtra);
            }
        }, android.os.UserHandle.ALL, intentFilter2, null, null);
        new com.android.internal.content.PackageMonitor() { // from class: com.android.server.accounts.AccountManagerService.3
            public void onPackageAdded(java.lang.String str, int i) {
                try {
                    com.android.server.accounts.AccountManagerService.this.cancelAccountAccessRequestNotificationIfNeeded(i, true, com.android.server.accounts.AccountManagerService.this.getUserAccounts(android.os.UserHandle.getUserId(i)));
                } catch (android.database.sqlite.SQLiteCantOpenDatabaseException e) {
                    android.util.Log.w(com.android.server.accounts.AccountManagerService.TAG, "Can't read accounts database", e);
                }
            }

            public void onPackageUpdateFinished(java.lang.String str, int i) {
                try {
                    com.android.server.accounts.AccountManagerService.this.cancelAccountAccessRequestNotificationIfNeeded(i, true, com.android.server.accounts.AccountManagerService.this.getUserAccounts(android.os.UserHandle.getUserId(i)));
                } catch (android.database.sqlite.SQLiteCantOpenDatabaseException e) {
                    android.util.Log.w(com.android.server.accounts.AccountManagerService.TAG, "Can't read accounts database", e);
                }
            }
        }.register(this.mContext, this.mHandler.getLooper(), android.os.UserHandle.ALL, true);
        this.mAppOpsManager.startWatchingMode(62, (java.lang.String) null, (android.app.AppOpsManager.OnOpChangedListener) new android.app.AppOpsManager.OnOpChangedInternalListener() { // from class: com.android.server.accounts.AccountManagerService.4
            public void onOpChanged(int i, java.lang.String str) {
                try {
                    int currentUser = android.app.ActivityManager.getCurrentUser();
                    int packageUidAsUser = com.android.server.accounts.AccountManagerService.this.mPackageManager.getPackageUidAsUser(str, currentUser);
                    if (com.android.server.accounts.AccountManagerService.this.mAppOpsManager.checkOpNoThrow(62, packageUidAsUser, str) == 0) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            com.android.server.accounts.AccountManagerService.this.cancelAccountAccessRequestNotificationIfNeeded(str, packageUidAsUser, true, com.android.server.accounts.AccountManagerService.this.getUserAccounts(currentUser));
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                } catch (android.database.sqlite.SQLiteCantOpenDatabaseException e2) {
                    android.util.Log.w(com.android.server.accounts.AccountManagerService.TAG, "Can't read accounts database", e2);
                }
            }
        });
        this.mPackageManager.addOnPermissionsChangeListener(new android.content.pm.PackageManager.OnPermissionsChangedListener() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda5
            public final void onPermissionsChanged(int i) {
                com.android.server.accounts.AccountManagerService.this.lambda$new$0(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i) {
        android.accounts.Account[] accountArr;
        android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
        java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
        if (packagesForUid != null) {
            int userId = android.os.UserHandle.getUserId(i);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.accounts.Account[] accountArr2 = null;
                for (java.lang.String str : packagesForUid) {
                    if (this.mPackageManager.checkPermission("android.permission.GET_ACCOUNTS", str) == 0) {
                        if (accountArr2 == null) {
                            android.accounts.Account[] accountsOrEmptyArray = getAccountsOrEmptyArray(null, userId, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                            if (com.android.internal.util.ArrayUtils.isEmpty(accountsOrEmptyArray)) {
                                return;
                            } else {
                                accountArr = accountsOrEmptyArray;
                            }
                        } else {
                            accountArr = accountArr2;
                        }
                        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(android.os.UserHandle.getUserId(i));
                        int length = accountArr.length;
                        int i2 = 0;
                        while (i2 < length) {
                            int i3 = i2;
                            int i4 = length;
                            android.accounts.Account[] accountArr3 = accountArr;
                            java.lang.String str2 = str;
                            cancelAccountAccessRequestNotificationIfNeeded(accountArr[i2], i, str, true, userAccounts);
                            i2 = i3 + 1;
                            str = str2;
                            length = i4;
                            accountArr = accountArr3;
                        }
                        accountArr2 = accountArr;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    boolean getBindInstantServiceAllowed(int i) {
        return this.mAuthenticatorCache.getBindInstantServiceAllowed(i);
    }

    void setBindInstantServiceAllowed(int i, boolean z) {
        this.mAuthenticatorCache.setBindInstantServiceAllowed(i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAccountAccessRequestNotificationIfNeeded(int i, boolean z, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        for (android.accounts.Account account : getAccountsOrEmptyArray(null, android.os.UserHandle.getUserId(i), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
            cancelAccountAccessRequestNotificationIfNeeded(account, i, z, userAccounts);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAccountAccessRequestNotificationIfNeeded(java.lang.String str, int i, boolean z, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        for (android.accounts.Account account : getAccountsOrEmptyArray(null, android.os.UserHandle.getUserId(i), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
            cancelAccountAccessRequestNotificationIfNeeded(account, i, str, z, userAccounts);
        }
    }

    private void cancelAccountAccessRequestNotificationIfNeeded(android.accounts.Account account, int i, boolean z, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
        if (packagesForUid != null) {
            for (java.lang.String str : packagesForUid) {
                cancelAccountAccessRequestNotificationIfNeeded(account, i, str, z, userAccounts);
            }
        }
    }

    private void cancelAccountAccessRequestNotificationIfNeeded(android.accounts.Account account, int i, java.lang.String str, boolean z, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        if (!z || hasAccountAccess(account, str, android.os.UserHandle.getUserHandleForUid(i))) {
            cancelNotification(getCredentialPermissionNotificationId(account, "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", i, userAccounts), userAccounts);
        }
    }

    public boolean addAccountExplicitlyWithVisibility(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.lang.String str2) {
        android.os.Bundle.setDefusable(bundle, true);
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        java.util.Objects.requireNonNull(account, "account cannot be null");
        android.util.Log.v(TAG, "addAccountExplicitly: caller's uid=" + callingUid + ", pid=" + android.os.Binder.getCallingPid() + ", packageName=" + str2 + ", accountType=" + account.type);
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid=%s, package=%s cannot explicitly add accounts of type: %s", java.lang.Integer.valueOf(callingUid), str2, account.type));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return addAccountInternal(getUserAccounts(callingUserId), account, str, bundle, callingUid, map, str2);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.util.Map<android.accounts.Account, java.lang.Integer> getAccountsAndVisibilityForPackage(java.lang.String str, java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        boolean isSameApp = android.os.UserHandle.isSameApp(callingUid, 1000);
        java.util.List<java.lang.String> typesForCaller = getTypesForCaller(callingUid, callingUserId, isSameApp);
        if ((str2 != null && !typesForCaller.contains(str2)) || (str2 == null && !isSameApp)) {
            throw new java.lang.SecurityException("getAccountsAndVisibilityForPackage() called from unauthorized uid " + callingUid + " with packageName=" + str);
        }
        if (str2 != null) {
            typesForCaller = new java.util.ArrayList<>();
            typesForCaller.add(str2);
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return getAccountsAndVisibilityForPackage(str, typesForCaller, java.lang.Integer.valueOf(callingUid), getUserAccounts(callingUserId));
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private java.util.Map<android.accounts.Account, java.lang.Integer> getAccountsAndVisibilityForPackage(java.lang.String str, java.util.List<java.lang.String> list, java.lang.Integer num, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        if (!canCallerAccessPackage(str, num.intValue(), userAccounts.userId)) {
            android.util.Log.w(TAG, "getAccountsAndVisibilityForPackage#Package not found " + str);
            return new java.util.LinkedHashMap();
        }
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        for (java.lang.String str2 : list) {
            synchronized (userAccounts.dbLock) {
                synchronized (userAccounts.cacheLock) {
                    try {
                        android.accounts.Account[] accountArr = userAccounts.accountCache.get(str2);
                        if (accountArr != null) {
                            for (android.accounts.Account account : accountArr) {
                                linkedHashMap.put(account, resolveAccountVisibility(account, str, userAccounts));
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        return filterSharedAccounts(userAccounts, linkedHashMap, num.intValue(), str);
    }

    public java.util.Map<java.lang.String, java.lang.Integer> getPackagesAndVisibilityForAccount(android.accounts.Account account) {
        java.util.Map<java.lang.String, java.lang.Integer> packagesAndVisibilityForAccountLocked;
        java.util.Objects.requireNonNull(account, "account cannot be null");
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId) && !isSystemUid(callingUid)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot get secrets for account %s", java.lang.Integer.valueOf(callingUid), account));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(callingUserId);
            synchronized (userAccounts.dbLock) {
                synchronized (userAccounts.cacheLock) {
                    packagesAndVisibilityForAccountLocked = getPackagesAndVisibilityForAccountLocked(account, userAccounts);
                }
            }
            return packagesAndVisibilityForAccountLocked;
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, java.lang.Integer> getPackagesAndVisibilityForAccountLocked(android.accounts.Account account, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        java.util.Map<java.lang.String, java.lang.Integer> map = (java.util.Map) userAccounts.visibilityCache.get(account);
        if (map == null) {
            android.util.Log.d(TAG, "Visibility was not initialized");
            java.util.HashMap hashMap = new java.util.HashMap();
            userAccounts.visibilityCache.put(account, hashMap);
            android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
            return hashMap;
        }
        return map;
    }

    public int getAccountVisibility(android.accounts.Account account, java.lang.String str) {
        java.util.Objects.requireNonNull(account, "account cannot be null");
        java.util.Objects.requireNonNull(str, "packageName cannot be null");
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId) && !isSystemUid(callingUid)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot get secrets for accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(callingUserId);
            if ("android:accounts:key_legacy_visible".equals(str)) {
                int accountVisibilityFromCache = getAccountVisibilityFromCache(account, str, userAccounts);
                if (accountVisibilityFromCache != 0) {
                    return accountVisibilityFromCache;
                }
                android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
                return 2;
            }
            if (!"android:accounts:key_legacy_not_visible".equals(str)) {
                if (canCallerAccessPackage(str, callingUid, userAccounts.userId)) {
                    return resolveAccountVisibility(account, str, userAccounts).intValue();
                }
                android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
                return 3;
            }
            int accountVisibilityFromCache2 = getAccountVisibilityFromCache(account, str, userAccounts);
            if (accountVisibilityFromCache2 != 0) {
                return accountVisibilityFromCache2;
            }
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            return 4;
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private int getAccountVisibilityFromCache(android.accounts.Account account, java.lang.String str, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        int intValue;
        synchronized (userAccounts.cacheLock) {
            try {
                java.lang.Integer num = getPackagesAndVisibilityForAccountLocked(account, userAccounts).get(str);
                intValue = num != null ? num.intValue() : 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return intValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.Integer resolveAccountVisibility(android.accounts.Account account, @android.annotation.NonNull java.lang.String str, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        java.util.Objects.requireNonNull(str, "packageName cannot be null");
        try {
            long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
            try {
                int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str, userAccounts.userId);
                if (android.os.UserHandle.isSameApp(packageUidAsUser, 1000)) {
                    return 1;
                }
                int checkPackageSignature = checkPackageSignature(account.type, packageUidAsUser, userAccounts.userId);
                int i = 2;
                if (checkPackageSignature == 2) {
                    return 1;
                }
                int accountVisibilityFromCache = getAccountVisibilityFromCache(account, str, userAccounts);
                if (accountVisibilityFromCache != 0) {
                    return java.lang.Integer.valueOf(accountVisibilityFromCache);
                }
                boolean isPermittedForPackage = isPermittedForPackage(str, userAccounts.userId, "android.permission.GET_ACCOUNTS_PRIVILEGED");
                if (isProfileOwner(packageUidAsUser)) {
                    return 1;
                }
                boolean isPreOApplication = isPreOApplication(str);
                if (checkPackageSignature != 0 || ((isPreOApplication && checkGetAccountsPermission(str, userAccounts.userId)) || ((checkReadContactsPermission(str, userAccounts.userId) && accountTypeManagesContacts(account.type, userAccounts.userId)) || isPermittedForPackage))) {
                    int accountVisibilityFromCache2 = getAccountVisibilityFromCache(account, "android:accounts:key_legacy_visible", userAccounts);
                    if (accountVisibilityFromCache2 != 0) {
                        i = accountVisibilityFromCache2;
                    }
                } else {
                    i = getAccountVisibilityFromCache(account, "android:accounts:key_legacy_not_visible", userAccounts);
                    if (i == 0) {
                        i = 4;
                    }
                }
                return java.lang.Integer.valueOf(i);
            } finally {
                android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "resolveAccountVisibility#Package not found " + e.getMessage());
            return 3;
        }
    }

    private boolean isPreOApplication(java.lang.String str) {
        try {
            long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
            try {
                android.content.pm.ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str, 0);
                if (applicationInfo != null && applicationInfo.targetSdkVersion >= 26) {
                    return false;
                }
                return true;
            } finally {
                android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "isPreOApplication#Package not found " + e.getMessage());
            return true;
        }
    }

    public boolean setAccountVisibility(android.accounts.Account account, java.lang.String str, int i) {
        java.util.Objects.requireNonNull(account, "account cannot be null");
        java.util.Objects.requireNonNull(str, "packageName cannot be null");
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId) && !isSystemUid(callingUid)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot get secrets for accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return setAccountVisibility(account, str, i, true, getUserAccounts(callingUserId), callingUid);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean isVisible(int i) {
        return i == 1 || i == 2;
    }

    private boolean setAccountVisibility(android.accounts.Account account, java.lang.String str, int i, boolean z, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, int i2) {
        java.util.Map<java.lang.String, java.lang.Integer> emptyMap;
        java.util.List<java.lang.String> emptyList;
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    if (z) {
                        if (isSpecialPackageKey(str)) {
                            emptyMap = getRequestingPackages(account, userAccounts);
                            emptyList = getAccountRemovedReceivers(account, userAccounts);
                        } else {
                            if (!canCallerAccessPackage(str, i2, userAccounts.userId)) {
                                return false;
                            }
                            emptyMap = new java.util.HashMap<>();
                            emptyMap.put(str, resolveAccountVisibility(account, str, userAccounts));
                            emptyList = new java.util.ArrayList<>();
                            if (shouldNotifyPackageOnAccountRemoval(account, str, userAccounts)) {
                                emptyList.add(str);
                            }
                        }
                    } else {
                        if (!isSpecialPackageKey(str) && !canCallerAccessPackage(str, i2, userAccounts.userId)) {
                            return false;
                        }
                        emptyMap = java.util.Collections.emptyMap();
                        emptyList = java.util.Collections.emptyList();
                    }
                    if (!updateAccountVisibilityLocked(account, str, i, userAccounts)) {
                        return false;
                    }
                    if (z) {
                        android.util.Log.i(TAG, "Notifying visibility changed for package=" + str);
                        for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : emptyMap.entrySet()) {
                            if (isVisible(entry.getValue().intValue()) != isVisible(resolveAccountVisibility(account, str, userAccounts).intValue())) {
                                notifyPackage(entry.getKey(), userAccounts);
                            }
                        }
                        java.util.Iterator<java.lang.String> it = emptyList.iterator();
                        while (it.hasNext()) {
                            sendAccountRemovedBroadcast(account, it.next(), userAccounts.userId, "setAccountVisibility");
                        }
                        sendAccountsChangedBroadcast(userAccounts.userId, account.type, "setAccountVisibility");
                    }
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private boolean updateAccountVisibilityLocked(android.accounts.Account account, java.lang.String str, int i, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
        if (findDeAccountId < 0) {
            return false;
        }
        android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
        try {
            if (!userAccounts.accountsDb.setAccountVisibility(findDeAccountId, str, i)) {
                return false;
            }
            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
            getPackagesAndVisibilityForAccountLocked(account, userAccounts).put(str, java.lang.Integer.valueOf(i));
            android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
            return true;
        } finally {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    public void registerAccountListener(java.lang.String[] strArr, java.lang.String str) {
        this.mAppOpsManager.checkPackage(android.os.Binder.getCallingUid(), str);
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            registerAccountListener(strArr, str, getUserAccounts(callingUserId));
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void registerAccountListener(java.lang.String[] strArr, java.lang.String str, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        synchronized (userAccounts.mReceiversForType) {
            if (strArr == null) {
                try {
                    strArr = new java.lang.String[]{null};
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            for (java.lang.String str2 : strArr) {
                java.util.Map map = (java.util.Map) userAccounts.mReceiversForType.get(str2);
                if (map == null) {
                    map = new java.util.HashMap();
                    userAccounts.mReceiversForType.put(str2, map);
                }
                java.lang.Integer num = (java.lang.Integer) map.get(str);
                int i = 1;
                if (num != null) {
                    i = 1 + num.intValue();
                }
                map.put(str, java.lang.Integer.valueOf(i));
            }
        }
    }

    public void unregisterAccountListener(java.lang.String[] strArr, java.lang.String str) {
        this.mAppOpsManager.checkPackage(android.os.Binder.getCallingUid(), str);
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            unregisterAccountListener(strArr, str, getUserAccounts(callingUserId));
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void unregisterAccountListener(java.lang.String[] strArr, java.lang.String str, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        synchronized (userAccounts.mReceiversForType) {
            if (strArr == null) {
                try {
                    strArr = new java.lang.String[]{null};
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            for (java.lang.String str2 : strArr) {
                java.util.Map map = (java.util.Map) userAccounts.mReceiversForType.get(str2);
                if (map == null || map.get(str) == null) {
                    throw new java.lang.IllegalArgumentException("attempt to unregister wrong receiver");
                }
                java.lang.Integer num = (java.lang.Integer) map.get(str);
                if (num.intValue() == 1) {
                    map.remove(str);
                } else {
                    map.put(str, java.lang.Integer.valueOf(num.intValue() - 1));
                }
            }
        }
    }

    private void sendNotificationAccountUpdated(android.accounts.Account account, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : getRequestingPackages(account, userAccounts).entrySet()) {
            if (entry.getValue().intValue() != 3 && entry.getValue().intValue() != 4) {
                notifyPackage(entry.getKey(), userAccounts);
            }
        }
    }

    private void notifyPackage(java.lang.String str, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        android.util.Log.i(TAG, "notifying package=" + str + " for userId=" + userAccounts.userId + ", sending broadcast of android.accounts.action.VISIBLE_ACCOUNTS_CHANGED");
        android.content.Intent intent = new android.content.Intent("android.accounts.action.VISIBLE_ACCOUNTS_CHANGED");
        intent.setPackage(str);
        intent.setFlags(1073741824);
        this.mContext.sendBroadcastAsUser(intent, new android.os.UserHandle(userAccounts.userId));
    }

    private java.util.Map<java.lang.String, java.lang.Integer> getRequestingPackages(android.accounts.Account account, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        java.util.HashSet<java.lang.String> hashSet = new java.util.HashSet();
        synchronized (userAccounts.mReceiversForType) {
            try {
                java.lang.String[] strArr = {account.type, null};
                for (int i = 0; i < 2; i++) {
                    java.util.Map map = (java.util.Map) userAccounts.mReceiversForType.get(strArr[i]);
                    if (map != null) {
                        hashSet.addAll(map.keySet());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.lang.String str : hashSet) {
            hashMap.put(str, resolveAccountVisibility(account, str, userAccounts));
        }
        return hashMap;
    }

    private java.util.List<java.lang.String> getAccountRemovedReceivers(android.accounts.Account account, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        android.content.Intent intent = new android.content.Intent("android.accounts.action.ACCOUNT_REMOVED");
        intent.setFlags(16777216);
        java.util.List queryBroadcastReceiversAsUser = this.mPackageManager.queryBroadcastReceiversAsUser(intent, 0, userAccounts.userId);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (queryBroadcastReceiversAsUser == null) {
            return arrayList;
        }
        java.util.Iterator it = queryBroadcastReceiversAsUser.iterator();
        while (it.hasNext()) {
            java.lang.String str = ((android.content.pm.ResolveInfo) it.next()).activityInfo.applicationInfo.packageName;
            int intValue = resolveAccountVisibility(account, str, userAccounts).intValue();
            if (intValue == 1 || intValue == 2) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private boolean shouldNotifyPackageOnAccountRemoval(android.accounts.Account account, java.lang.String str, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        int intValue = resolveAccountVisibility(account, str, userAccounts).intValue();
        if (intValue != 1 && intValue != 2) {
            return false;
        }
        android.content.Intent intent = new android.content.Intent("android.accounts.action.ACCOUNT_REMOVED");
        intent.setFlags(16777216);
        intent.setPackage(str);
        java.util.List queryBroadcastReceiversAsUser = this.mPackageManager.queryBroadcastReceiversAsUser(intent, 0, userAccounts.userId);
        return queryBroadcastReceiversAsUser != null && queryBroadcastReceiversAsUser.size() > 0;
    }

    private boolean isSpecialPackageKey(java.lang.String str) {
        return "android:accounts:key_legacy_visible".equals(str) || "android:accounts:key_legacy_not_visible".equals(str);
    }

    private void sendAccountsChangedBroadcast(int i, java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        java.util.Objects.requireNonNull(str2, "useCase can't be null");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("the accountType= ");
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(" changed with useCase=");
        sb.append(str2);
        sb.append(" for userId=");
        sb.append(i);
        sb.append(", sending broadcast of ");
        sb.append(ACCOUNTS_CHANGED_INTENT.getAction());
        android.util.Log.i(TAG, sb.toString());
        this.mContext.sendBroadcastAsUser(ACCOUNTS_CHANGED_INTENT, new android.os.UserHandle(i), null, ACCOUNTS_CHANGED_OPTIONS);
    }

    private void sendAccountRemovedBroadcast(android.accounts.Account account, java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        java.util.Objects.requireNonNull(str2, "useCase can't be null");
        android.util.Log.i(TAG, "the account with type=" + account.type + " removed while useCase=" + str2 + " for userId=" + i + ", sending broadcast of android.accounts.action.ACCOUNT_REMOVED");
        android.content.Intent intent = new android.content.Intent("android.accounts.action.ACCOUNT_REMOVED");
        intent.setFlags(16777216);
        intent.setPackage(str);
        intent.putExtra("authAccount", account.name);
        intent.putExtra("accountType", account.type);
        this.mContext.sendBroadcastAsUser(intent, new android.os.UserHandle(i));
    }

    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (java.lang.RuntimeException e) {
            if (!(e instanceof java.lang.SecurityException) && !(e instanceof java.lang.IllegalArgumentException)) {
                android.util.Slog.wtf(TAG, "Account Manager Crash", e);
            }
            throw e;
        }
    }

    private android.os.UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = android.os.UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }

    public void validateAccounts(int i) {
        validateAccountsInternal(getUserAccounts(i), true);
    }

    private void validateAccountsInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, boolean z) {
        boolean z2;
        java.util.Iterator<java.util.Map.Entry<java.lang.Long, android.accounts.Account>> it;
        if (android.util.Log.isLoggable(TAG, 3)) {
            android.util.Log.d(TAG, "validateAccountsInternal " + userAccounts.userId + " isCeDatabaseAttached=" + userAccounts.accountsDb.isCeDatabaseAttached() + " userLocked=" + this.mLocalUnlockedUsers.get(userAccounts.userId));
        }
        if (z) {
            this.mAuthenticatorCache.invalidateCache(userAccounts.userId);
        }
        java.util.HashMap<java.lang.String, java.lang.Integer> authenticatorTypeAndUIDForUser = getAuthenticatorTypeAndUIDForUser(this.mAuthenticatorCache, userAccounts.userId);
        boolean isLocalUnlockedUser = isLocalUnlockedUser(userAccounts.userId);
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    com.android.server.accounts.AccountsDb accountsDb = userAccounts.accountsDb;
                    java.util.Map<java.lang.String, java.lang.Integer> findMetaAuthUid = accountsDb.findMetaAuthUid();
                    java.util.HashSet newHashSet = com.google.android.collect.Sets.newHashSet();
                    android.util.SparseBooleanArray sparseBooleanArray = null;
                    for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : findMetaAuthUid.entrySet()) {
                        java.lang.String key = entry.getKey();
                        int intValue = entry.getValue().intValue();
                        java.lang.Integer num = authenticatorTypeAndUIDForUser.get(key);
                        if (num == null || intValue != num.intValue()) {
                            if (sparseBooleanArray == null) {
                                sparseBooleanArray = getUidsOfInstalledOrUpdatedPackagesAsUser(userAccounts.userId);
                            }
                            if (!sparseBooleanArray.get(intValue)) {
                                newHashSet.add(key);
                                accountsDb.deleteMetaByAuthTypeAndUid(key, intValue);
                            }
                        } else {
                            authenticatorTypeAndUIDForUser.remove(key);
                        }
                    }
                    for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry2 : authenticatorTypeAndUIDForUser.entrySet()) {
                        accountsDb.insertOrReplaceMetaAuthTypeAndUid(entry2.getKey(), entry2.getValue().intValue());
                    }
                    java.util.Map<java.lang.Long, android.accounts.Account> findAllDeAccounts = accountsDb.findAllDeAccounts();
                    try {
                        userAccounts.accountCache.clear();
                        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
                        java.util.Iterator<java.util.Map.Entry<java.lang.Long, android.accounts.Account>> it2 = findAllDeAccounts.entrySet().iterator();
                        boolean z3 = false;
                        while (it2.hasNext()) {
                            try {
                                java.util.Map.Entry<java.lang.Long, android.accounts.Account> next = it2.next();
                                long longValue = next.getKey().longValue();
                                android.accounts.Account value = next.getValue();
                                if (newHashSet.contains(value.type)) {
                                    android.util.Slog.w(TAG, "deleting account " + value.toSafeString() + " because type " + value.type + "'s registered authenticator no longer exist.");
                                    java.util.Map<java.lang.String, java.lang.Integer> requestingPackages = getRequestingPackages(value, userAccounts);
                                    java.util.List<java.lang.String> accountRemovedReceivers = getAccountRemovedReceivers(value, userAccounts);
                                    accountsDb.beginTransaction();
                                    try {
                                        accountsDb.deleteDeAccount(longValue);
                                        if (isLocalUnlockedUser) {
                                            accountsDb.deleteCeAccount(longValue);
                                        }
                                        accountsDb.setTransactionSuccessful();
                                        accountsDb.endTransaction();
                                        try {
                                            android.util.Log.i(TAG, "validateAccountsInternal#Deleted UserId=" + userAccounts.userId + ", AccountId=" + longValue);
                                            it = it2;
                                            logRecord(com.android.server.accounts.AccountsDb.DEBUG_ACTION_AUTHENTICATOR_REMOVE, "accounts", longValue, userAccounts);
                                            userAccounts.userDataCache.remove(value);
                                            userAccounts.authTokenCache.remove(value);
                                            userAccounts.accountTokenCaches.remove(value);
                                            userAccounts.visibilityCache.remove(value);
                                            for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry3 : requestingPackages.entrySet()) {
                                                if (isVisible(entry3.getValue().intValue())) {
                                                    notifyPackage(entry3.getKey(), userAccounts);
                                                }
                                            }
                                            java.util.Iterator<java.lang.String> it3 = accountRemovedReceivers.iterator();
                                            while (it3.hasNext()) {
                                                sendAccountRemovedBroadcast(value, it3.next(), userAccounts.userId, "validateAccounts");
                                            }
                                            z3 = true;
                                        } catch (java.lang.Throwable th) {
                                            th = th;
                                            z2 = true;
                                            if (z2) {
                                                sendAccountsChangedBroadcast(userAccounts.userId, "ambiguous", "validateAccounts");
                                            }
                                            throw th;
                                        }
                                    } finally {
                                        accountsDb.endTransaction();
                                    }
                                } else {
                                    it = it2;
                                    java.util.ArrayList arrayList = (java.util.ArrayList) linkedHashMap.get(value.type);
                                    if (arrayList == null) {
                                        arrayList = new java.util.ArrayList();
                                        linkedHashMap.put(value.type, arrayList);
                                    }
                                    arrayList.add(value.name);
                                }
                                it2 = it;
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                z2 = z3;
                            }
                        }
                        for (java.util.Map.Entry entry4 : linkedHashMap.entrySet()) {
                            java.lang.String str = (java.lang.String) entry4.getKey();
                            java.util.ArrayList arrayList2 = (java.util.ArrayList) entry4.getValue();
                            int size = arrayList2.size();
                            android.accounts.Account[] accountArr = new android.accounts.Account[size];
                            for (int i = 0; i < size; i++) {
                                accountArr[i] = new android.accounts.Account((java.lang.String) arrayList2.get(i), str, java.util.UUID.randomUUID().toString());
                            }
                            userAccounts.accountCache.put(str, accountArr);
                        }
                        userAccounts.visibilityCache.putAll(accountsDb.findAllVisibilityValues());
                        android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
                        if (z3) {
                            sendAccountsChangedBroadcast(userAccounts.userId, "ambiguous", "validateAccounts");
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        z2 = false;
                    }
                } finally {
                }
            }
        }
    }

    private android.util.SparseBooleanArray getUidsOfInstalledOrUpdatedPackagesAsUser(int i) {
        java.util.List<android.content.pm.PackageInfo> installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(8192, i);
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray(installedPackagesAsUser.size());
        for (android.content.pm.PackageInfo packageInfo : installedPackagesAsUser) {
            if (packageInfo.applicationInfo != null && (packageInfo.applicationInfo.flags & 8388608) != 0) {
                sparseBooleanArray.put(packageInfo.applicationInfo.uid, true);
            }
        }
        return sparseBooleanArray;
    }

    static java.util.HashMap<java.lang.String, java.lang.Integer> getAuthenticatorTypeAndUIDForUser(android.content.Context context, int i) {
        return getAuthenticatorTypeAndUIDForUser(new com.android.server.accounts.AccountAuthenticatorCache(context), i);
    }

    private static java.util.HashMap<java.lang.String, java.lang.Integer> getAuthenticatorTypeAndUIDForUser(com.android.server.accounts.IAccountAuthenticatorCache iAccountAuthenticatorCache, int i) {
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        for (android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription> serviceInfo : iAccountAuthenticatorCache.getAllServices(i)) {
            linkedHashMap.put(((android.accounts.AuthenticatorDescription) serviceInfo.type).type, java.lang.Integer.valueOf(serviceInfo.uid));
        }
        return linkedHashMap;
    }

    private com.android.server.accounts.AccountManagerService.UserAccounts getUserAccountsForCaller() {
        return getUserAccounts(android.os.UserHandle.getCallingUserId());
    }

    protected com.android.server.accounts.AccountManagerService.UserAccounts getUserAccounts(int i) {
        try {
            return getUserAccountsNotChecked(i);
        } catch (java.lang.RuntimeException e) {
            if (!this.mPackageManager.hasSystemFeature("android.hardware.type.automotive")) {
                throw e;
            }
            android.util.Slog.wtf(TAG, "Removing user " + i + " due to exception (" + e + ") reading its account database");
            if (i == android.app.ActivityManager.getCurrentUser() && i != 0) {
                android.util.Slog.i(TAG, "Switching to system user first");
                try {
                    android.app.ActivityManager.getService().switchUser(0);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(TAG, "Could not switch to 0: " + e2);
                }
            }
            if (!getUserManager().removeUserEvenWhenDisallowed(i)) {
                android.util.Slog.e(TAG, "could not remove user " + i);
                throw e;
            }
            throw e;
        }
    }

    private com.android.server.accounts.AccountManagerService.UserAccounts getUserAccountsNotChecked(int i) {
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts;
        boolean z;
        synchronized (this.mUsers) {
            try {
                userAccounts = this.mUsers.get(i);
                if (userAccounts != null) {
                    z = false;
                } else {
                    com.android.server.accounts.AccountManagerService.UserAccounts userAccounts2 = new com.android.server.accounts.AccountManagerService.UserAccounts(this.mContext, i, new java.io.File(this.mInjector.getPreNDatabaseName(i)), new java.io.File(this.mInjector.getDeDatabaseName(i)));
                    this.mUsers.append(i, userAccounts2);
                    purgeOldGrants(userAccounts2);
                    android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
                    z = true;
                    userAccounts = userAccounts2;
                }
                if (!userAccounts.accountsDb.isCeDatabaseAttached() && this.mLocalUnlockedUsers.get(i)) {
                    android.util.Log.i(TAG, "User " + i + " is unlocked - opening CE database");
                    synchronized (userAccounts.dbLock) {
                        synchronized (userAccounts.cacheLock) {
                            userAccounts.accountsDb.attachCeDatabase(new java.io.File(this.mInjector.getCeDatabaseName(i)));
                        }
                    }
                    syncDeCeAccountsLocked(userAccounts);
                }
                if (z) {
                    validateAccountsInternal(userAccounts, true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return userAccounts;
    }

    private void syncDeCeAccountsLocked(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        com.android.internal.util.Preconditions.checkState(java.lang.Thread.holdsLock(this.mUsers), "mUsers lock must be held");
        java.util.List<android.accounts.Account> findCeAccountsNotInDe = userAccounts.accountsDb.findCeAccountsNotInDe();
        if (!findCeAccountsNotInDe.isEmpty()) {
            android.util.Slog.i(TAG, findCeAccountsNotInDe.size() + " accounts were previously deleted while user " + userAccounts.userId + " was locked. Removing accounts from CE tables");
            logRecord(userAccounts, com.android.server.accounts.AccountsDb.DEBUG_ACTION_SYNC_DE_CE_ACCOUNTS, "accounts");
            java.util.Iterator<android.accounts.Account> it = findCeAccountsNotInDe.iterator();
            while (it.hasNext()) {
                removeAccountInternal(userAccounts, it.next(), 1000);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void purgeOldGrantsAll() {
        synchronized (this.mUsers) {
            for (int i = 0; i < this.mUsers.size(); i++) {
                try {
                    purgeOldGrants(this.mUsers.valueAt(i));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void purgeOldGrants(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    try {
                        java.util.Iterator<java.lang.Integer> it = userAccounts.accountsDb.findAllUidGrants().iterator();
                        while (it.hasNext()) {
                            int intValue = it.next().intValue();
                            if (!(this.mPackageManager.getPackagesForUid(intValue) != null)) {
                                android.util.Log.d(TAG, "deleting grants for UID " + intValue + " because its package is no longer installed");
                                userAccounts.accountsDb.deleteGrantsByUid(intValue);
                            }
                        }
                    } catch (android.database.sqlite.SQLiteCantOpenDatabaseException e) {
                        android.util.Log.w(TAG, "Could not delete grants for user = " + userAccounts.userId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeVisibilityValuesForPackage(java.lang.String str) {
        if (isSpecialPackageKey(str)) {
            return;
        }
        synchronized (this.mUsers) {
            int size = this.mUsers.size();
            for (int i = 0; i < size; i++) {
                com.android.server.accounts.AccountManagerService.UserAccounts valueAt = this.mUsers.valueAt(i);
                try {
                    this.mPackageManager.getPackageUidAsUser(str, valueAt.userId);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    try {
                        valueAt.accountsDb.deleteAccountVisibilityForPackage(str);
                        synchronized (valueAt.dbLock) {
                            synchronized (valueAt.cacheLock) {
                                try {
                                    java.util.Iterator it = valueAt.visibilityCache.keySet().iterator();
                                    while (it.hasNext()) {
                                        getPackagesAndVisibilityForAccountLocked((android.accounts.Account) it.next(), valueAt).remove(str);
                                    }
                                    android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
                                } finally {
                                }
                            }
                        }
                    } catch (android.database.sqlite.SQLiteCantOpenDatabaseException e2) {
                        android.util.Log.w(TAG, "Could not delete account visibility for user = " + valueAt.userId, e2);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void purgeUserData(int i) {
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts;
        synchronized (this.mUsers) {
            userAccounts = this.mUsers.get(i);
            this.mUsers.remove(i);
            this.mLocalUnlockedUsers.delete(i);
            android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
        }
        if (userAccounts != null) {
            synchronized (userAccounts.dbLock) {
                synchronized (userAccounts.cacheLock) {
                    userAccounts.accountsDb.closeDebugStatement();
                    userAccounts.accountsDb.close();
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onUserUnlocked(android.content.Intent intent) {
        onUnlockUser(intent.getIntExtra("android.intent.extra.user_handle", -1));
    }

    void onUnlockUser(final int i) {
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "onUserUnlocked " + i);
        }
        synchronized (this.mUsers) {
            this.mLocalUnlockedUsers.put(i, true);
        }
        if (i < 1) {
            return;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accounts.AccountManagerService.this.lambda$onUnlockUser$1(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: syncSharedAccounts, reason: merged with bridge method [inline-methods] */
    public void lambda$onUnlockUser$1(int i) {
        android.accounts.Account[] sharedAccountsAsUser = getSharedAccountsAsUser(i);
        if (sharedAccountsAsUser == null || sharedAccountsAsUser.length == 0) {
            return;
        }
        android.accounts.Account[] accountsAsUser = getAccountsAsUser(null, i, this.mContext.getOpPackageName());
        for (android.accounts.Account account : sharedAccountsAsUser) {
            if (!com.android.internal.util.ArrayUtils.contains(accountsAsUser, account)) {
                copyAccountToUser(null, account, 0, i);
            }
        }
    }

    public void onServiceChanged(android.accounts.AuthenticatorDescription authenticatorDescription, int i, boolean z) {
        if (getUserManager().getUserInfo(i) == null) {
            android.util.Log.w(TAG, "onServiceChanged: ignore removed user " + i);
            return;
        }
        validateAccountsInternal(getUserAccounts(i), false);
    }

    public java.lang.String getPassword(android.accounts.Account account) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "getPassword: " + account + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid());
        }
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot get secrets for accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return readPasswordInternal(getUserAccounts(callingUserId), account);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private java.lang.String readPasswordInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account) {
        java.lang.String findAccountPasswordByNameAndType;
        if (account == null) {
            return null;
        }
        if (!isLocalUnlockedUser(userAccounts.userId)) {
            android.util.Log.w(TAG, "Password is not available - user " + userAccounts.userId + " data is locked");
            return null;
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                findAccountPasswordByNameAndType = userAccounts.accountsDb.findAccountPasswordByNameAndType(account.name, account.type);
            }
        }
        return findAccountPasswordByNameAndType;
    }

    public java.lang.String getPreviousName(android.accounts.Account account) {
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "getPreviousName: " + account + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid());
        }
        java.util.Objects.requireNonNull(account, "account cannot be null");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return readPreviousNameInternal(getUserAccounts(callingUserId), account);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private java.lang.String readPreviousNameInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account) {
        if (account == null) {
            return null;
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                java.util.concurrent.atomic.AtomicReference atomicReference = (java.util.concurrent.atomic.AtomicReference) userAccounts.previousNameCache.get(account);
                if (atomicReference == null) {
                    java.lang.String findDeAccountPreviousName = userAccounts.accountsDb.findDeAccountPreviousName(account);
                    userAccounts.previousNameCache.put(account, new java.util.concurrent.atomic.AtomicReference(findDeAccountPreviousName));
                    return findDeAccountPreviousName;
                }
                return (java.lang.String) atomicReference.get();
            }
        }
    }

    public java.lang.String getUserData(android.accounts.Account account, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, java.lang.String.format("getUserData( account: %s, key: %s, callerUid: %s, pid: %s", account, str, java.lang.Integer.valueOf(callingUid), java.lang.Integer.valueOf(android.os.Binder.getCallingPid())));
        }
        java.util.Objects.requireNonNull(account, "account cannot be null");
        java.util.Objects.requireNonNull(str, "key cannot be null");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot get user data for accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        if (!isLocalUnlockedUser(callingUserId)) {
            android.util.Log.w(TAG, "User " + callingUserId + " data is locked. callingUid " + callingUid);
            return null;
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(callingUserId);
            if (accountExistsCache(userAccounts, account)) {
                return readUserDataInternal(userAccounts, account, str);
            }
            return null;
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.accounts.AuthenticatorDescription[] getAuthenticatorTypes(int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "getAuthenticatorTypes: for user id " + i + " caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        if (isCrossUser(callingUid, i)) {
            throw new java.lang.SecurityException(java.lang.String.format("User %s tying to get authenticator types for %s", java.lang.Integer.valueOf(android.os.UserHandle.getCallingUserId()), java.lang.Integer.valueOf(i)));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return getAuthenticatorTypesInternal(i, callingUid);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private android.accounts.AuthenticatorDescription[] getAuthenticatorTypesInternal(int i, int i2) {
        this.mAuthenticatorCache.updateServices(i);
        java.util.Collection<android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription>> allServices = this.mAuthenticatorCache.getAllServices(i);
        java.util.ArrayList arrayList = new java.util.ArrayList(allServices.size());
        for (android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription> serviceInfo : allServices) {
            if (canCallerAccessPackage(((android.accounts.AuthenticatorDescription) serviceInfo.type).packageName, i2, i)) {
                arrayList.add((android.accounts.AuthenticatorDescription) serviceInfo.type);
            }
        }
        return (android.accounts.AuthenticatorDescription[]) arrayList.toArray(new android.accounts.AuthenticatorDescription[arrayList.size()]);
    }

    private boolean isCrossUser(int i, int i2) {
        return (i2 == android.os.UserHandle.getCallingUserId() || i == 1000 || this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0) ? false : true;
    }

    public boolean addAccountExplicitly(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.lang.String str2) {
        return addAccountExplicitlyWithVisibility(account, str, bundle, null, str2);
    }

    public void copyAccountToUser(final android.accounts.IAccountManagerResponse iAccountManagerResponse, final android.accounts.Account account, final int i, int i2) {
        if (isCrossUser(android.os.Binder.getCallingUid(), -1)) {
            throw new java.lang.SecurityException("Calling copyAccountToUser requires android.permission.INTERACT_ACROSS_USERS_FULL");
        }
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(i);
        final com.android.server.accounts.AccountManagerService.UserAccounts userAccounts2 = getUserAccounts(i2);
        if (userAccounts == null || userAccounts2 == null) {
            if (iAccountManagerResponse != null) {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putBoolean("booleanResult", false);
                try {
                    iAccountManagerResponse.onResult(bundle);
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Failed to report error back to the client." + e);
                    return;
                }
            }
            return;
        }
        android.util.Slog.d(TAG, "Copying account " + account.toSafeString() + " from user " + i + " to user " + i2);
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            new com.android.server.accounts.AccountManagerService.Session(userAccounts, iAccountManagerResponse, account.type, false, false, account.name, false) { // from class: com.android.server.accounts.AccountManagerService.5
                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    return super.toDebugString(j) + ", getAccountCredentialsForClone, " + account.type;
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    this.mAuthenticator.getAccountCredentialsForCloning(this, account);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public void onResult(android.os.Bundle bundle2) {
                    android.os.Bundle.setDefusable(bundle2, true);
                    if (bundle2 != null && bundle2.getBoolean("booleanResult", false)) {
                        com.android.server.accounts.AccountManagerService.this.completeCloningAccount(iAccountManagerResponse, bundle2, account, userAccounts2, i);
                    } else {
                        super.onResult(bundle2);
                    }
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean accountAuthenticated(android.accounts.Account account) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, java.lang.String.format("accountAuthenticated( account: %s, callerUid: %s)", account, java.lang.Integer.valueOf(callingUid)));
        }
        java.util.Objects.requireNonNull(account, "account cannot be null");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot notify authentication for accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        if (!canUserModifyAccounts(callingUserId, callingUid) || !canUserModifyAccountsForType(callingUserId, account.type, callingUid)) {
            return false;
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            getUserAccounts(callingUserId);
            return updateLastAuthenticatedTime(account);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateLastAuthenticatedTime(android.accounts.Account account) {
        boolean updateAccountLastAuthenticatedTime;
        com.android.server.accounts.AccountManagerService.UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        synchronized (userAccountsForCaller.dbLock) {
            synchronized (userAccountsForCaller.cacheLock) {
                updateAccountLastAuthenticatedTime = userAccountsForCaller.accountsDb.updateAccountLastAuthenticatedTime(account);
            }
        }
        return updateAccountLastAuthenticatedTime;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeCloningAccount(android.accounts.IAccountManagerResponse iAccountManagerResponse, final android.os.Bundle bundle, final android.accounts.Account account, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, final int i) {
        android.os.Bundle.setDefusable(bundle, true);
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            new com.android.server.accounts.AccountManagerService.Session(userAccounts, iAccountManagerResponse, account.type, false, false, account.name, false) { // from class: com.android.server.accounts.AccountManagerService.6
                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    return super.toDebugString(j) + ", getAccountCredentialsForClone, " + account.type;
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    for (android.accounts.Account account2 : com.android.server.accounts.AccountManagerService.this.getAccounts(i, com.android.server.accounts.AccountManagerService.this.mContext.getOpPackageName())) {
                        if (account2.equals(account)) {
                            this.mAuthenticator.addAccountFromCredentials(this, account, bundle);
                            return;
                        }
                    }
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public void onResult(android.os.Bundle bundle2) {
                    android.os.Bundle.setDefusable(bundle2, true);
                    super.onResult(bundle2);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public void onError(int i2, java.lang.String str) {
                    super.onError(i2, str);
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean addAccountInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, int i, java.util.Map<java.lang.String, java.lang.Integer> map, java.lang.String str2) {
        long j;
        android.os.Bundle.setDefusable(bundle, true);
        if (account == null) {
            return false;
        }
        if (account.name != null && account.name.length() > 200) {
            android.util.Log.w(TAG, "Account cannot be added - Name longer than 200 chars");
            return false;
        }
        if (account.type != null && account.type.length() > 200) {
            android.util.Log.w(TAG, "Account cannot be added - Name longer than 200 chars");
            return false;
        }
        if (!isLocalUnlockedUser(userAccounts.userId)) {
            android.util.Log.w(TAG, "Account " + account.toSafeString() + " cannot be added - user " + userAccounts.userId + " is locked. callingUid=" + i);
            return false;
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                userAccounts.accountsDb.beginTransaction();
                try {
                    if (userAccounts.accountsDb.findCeAccountId(account) >= 0) {
                        android.util.Log.w(TAG, "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping since the account already exists");
                        return false;
                    }
                    if (userAccounts.accountsDb.findAllDeAccounts().size() > 100) {
                        android.util.Log.w(TAG, "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping since more than 100 accounts on device exist");
                        return false;
                    }
                    long insertCeAccount = userAccounts.accountsDb.insertCeAccount(account, str);
                    if (insertCeAccount < 0) {
                        android.util.Log.w(TAG, "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping the DB insert failed");
                        return false;
                    }
                    if (userAccounts.accountsDb.insertDeAccount(account, insertCeAccount) < 0) {
                        android.util.Log.w(TAG, "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping the DB insert failed");
                        return false;
                    }
                    if (bundle != null) {
                        for (java.lang.String str3 : bundle.keySet()) {
                            if (userAccounts.accountsDb.insertExtra(insertCeAccount, str3, bundle.getString(str3)) < 0) {
                                android.util.Log.w(TAG, "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping since insertExtra failed for key " + str3);
                                return false;
                            }
                            android.accounts.AccountManager.invalidateLocalAccountUserDataCaches();
                        }
                    }
                    if (map != null) {
                        for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : map.entrySet()) {
                            setAccountVisibility(account, entry.getKey(), entry.getValue().intValue(), false, userAccounts, i);
                            insertCeAccount = insertCeAccount;
                        }
                        j = insertCeAccount;
                    } else {
                        j = insertCeAccount;
                    }
                    userAccounts.accountsDb.setTransactionSuccessful();
                    logRecord(com.android.server.accounts.AccountsDb.DEBUG_ACTION_ACCOUNT_ADD, "accounts", j, userAccounts, i);
                    insertAccountIntoCacheLocked(userAccounts, account);
                    if (getUserManager().getUserInfo(userAccounts.userId).canHaveProfile()) {
                        addAccountToLinkedRestrictedUsers(account, userAccounts.userId);
                    }
                    sendNotificationAccountUpdated(account, userAccounts);
                    android.util.Log.i(TAG, "callingUid=" + i + ", userId=" + userAccounts.userId + " added account");
                    sendAccountsChangedBroadcast(userAccounts.userId, account.type, "addAccount");
                    logAddAccountExplicitlyMetrics(str2, account.type, map);
                    return true;
                } finally {
                    userAccounts.accountsDb.endTransaction();
                }
            }
        }
    }

    private void logAddAccountExplicitlyMetrics(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.util.Map<java.lang.String, java.lang.Integer> map) {
        android.app.admin.DevicePolicyEventLogger.createEvent(203).setStrings(android.text.TextUtils.emptyIfNull(str2), android.text.TextUtils.emptyIfNull(str), findPackagesPerVisibility(map)).write();
    }

    private java.lang.String[] findPackagesPerVisibility(@android.annotation.Nullable java.util.Map<java.lang.String, java.lang.Integer> map) {
        java.util.HashMap hashMap = new java.util.HashMap();
        if (map != null) {
            for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : map.entrySet()) {
                if (!hashMap.containsKey(entry.getValue())) {
                    hashMap.put(entry.getValue(), new java.util.HashSet());
                }
                hashMap.get(entry.getValue()).add(entry.getKey());
            }
        }
        return new java.lang.String[]{getPackagesForVisibilityStr(0, hashMap), getPackagesForVisibilityStr(1, hashMap), getPackagesForVisibilityStr(2, hashMap), getPackagesForVisibilityStr(3, hashMap), getPackagesForVisibilityStr(4, hashMap)};
    }

    private java.lang.String getPackagesForVisibilityStr(int i, java.util.Map<java.lang.Integer, java.util.Set<java.lang.String>> map) {
        java.lang.String str;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(i);
        sb.append(":");
        if (map.containsKey(java.lang.Integer.valueOf(i))) {
            str = android.text.TextUtils.join(",", map.get(java.lang.Integer.valueOf(i)));
        } else {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLocalUnlockedUser(int i) {
        boolean z;
        synchronized (this.mUsers) {
            z = this.mLocalUnlockedUsers.get(i);
        }
        return z;
    }

    private void addAccountToLinkedRestrictedUsers(android.accounts.Account account, int i) {
        for (android.content.pm.UserInfo userInfo : getUserManager().getUsers()) {
            if (userInfo.isRestricted() && i == userInfo.restrictedProfileParentId) {
                addSharedAccountAsUser(account, userInfo.id);
                if (isLocalUnlockedUser(userInfo.id)) {
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(4, i, userInfo.id, account));
                }
            }
        }
    }

    public void hasFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String[] strArr, int i, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        this.mAppOpsManager.checkPackage(callingUid, str);
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "hasFeatures: " + account + ", response " + iAccountManagerResponse + ", features " + java.util.Arrays.toString(strArr) + ", caller's uid " + callingUid + ", userId " + i + ", pid " + android.os.Binder.getCallingPid());
        }
        com.android.internal.util.Preconditions.checkArgument(account != null, "account cannot be null");
        com.android.internal.util.Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        com.android.internal.util.Preconditions.checkArgument(strArr != null, "features cannot be null");
        if (i != android.os.UserHandle.getCallingUserId() && callingUid != 1000 && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
            throw new java.lang.SecurityException("User " + android.os.UserHandle.getCallingUserId() + " trying to check account features for " + i);
        }
        checkReadAccountsPermitted(callingUid, account.type, i, str);
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            new com.android.server.accounts.AccountManagerService.TestFeaturesSession(getUserAccounts(i), iAccountManagerResponse, account, strArr).bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private class TestFeaturesSession extends com.android.server.accounts.AccountManagerService.Session {
        private final android.accounts.Account mAccount;
        private final java.lang.String[] mFeatures;

        public TestFeaturesSession(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String[] strArr) {
            super(com.android.server.accounts.AccountManagerService.this, userAccounts, iAccountManagerResponse, account.type, false, true, account.name, false);
            this.mFeatures = strArr;
            this.mAccount = account;
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void run() throws android.os.RemoteException {
            try {
                this.mAuthenticator.hasFeatures(this, this.mAccount, this.mFeatures);
            } catch (android.os.RemoteException e) {
                onError(1, "remote exception");
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void onResult(android.os.Bundle bundle) {
            android.os.Bundle.setDefusable(bundle, true);
            android.accounts.IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    if (bundle == null) {
                        responseAndClose.onError(5, "null bundle");
                        return;
                    }
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                    }
                    android.os.Bundle bundle2 = new android.os.Bundle();
                    bundle2.putBoolean("booleanResult", bundle.getBoolean("booleanResult", false));
                    responseAndClose.onResult(bundle2);
                } catch (android.os.RemoteException e) {
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "failure while notifying response", e);
                    }
                }
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        protected java.lang.String toDebugString(long j) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(super.toDebugString(j));
            sb.append(", hasFeatures, ");
            sb.append(this.mAccount);
            sb.append(", ");
            sb.append(this.mFeatures != null ? android.text.TextUtils.join(",", this.mFeatures) : null);
            return sb.toString();
        }
    }

    public void renameAccount(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "renameAccount: " + account + " -> " + str + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (str != null && str.length() > 200) {
            android.util.Log.e(TAG, "renameAccount failed - account name longer than 200");
            throw new java.lang.IllegalArgumentException("account name longer than 200");
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot rename accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(callingUserId);
            android.util.Log.i(TAG, "callingUid=" + callingUid + ", userId=" + userAccounts.userId + " performing rename account");
            android.accounts.Account renameAccountInternal = renameAccountInternal(userAccounts, account, str);
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString("authAccount", renameAccountInternal.name);
            bundle.putString("accountType", renameAccountInternal.type);
            bundle.putString("accountAccessId", renameAccountInternal.getAccessId());
            try {
                iAccountManagerResponse.onResult(bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, e.getMessage());
            }
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private android.accounts.Account renameAccountInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str) {
        cancelNotification(getSigninRequiredNotificationId(userAccounts, account), userAccounts);
        synchronized (userAccounts.credentialsPermissionNotificationIds) {
            try {
                for (android.util.Pair pair : userAccounts.credentialsPermissionNotificationIds.keySet()) {
                    if (account.equals(((android.util.Pair) pair.first).first)) {
                        cancelNotification((com.android.server.accounts.AccountManagerService.NotificationId) userAccounts.credentialsPermissionNotificationIds.get(pair), userAccounts);
                    }
                }
            } finally {
            }
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                java.util.List<java.lang.String> accountRemovedReceivers = getAccountRemovedReceivers(account, userAccounts);
                userAccounts.accountsDb.beginTransaction();
                android.accounts.Account account2 = new android.accounts.Account(str, account.type);
                try {
                    if (userAccounts.accountsDb.findCeAccountId(account2) >= 0) {
                        android.util.Log.e(TAG, "renameAccount failed - account with new name already exists");
                        return null;
                    }
                    long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                    if (findDeAccountId < 0) {
                        android.util.Log.e(TAG, "renameAccount failed - old account does not exist");
                        return null;
                    }
                    userAccounts.accountsDb.renameCeAccount(findDeAccountId, str);
                    if (!userAccounts.accountsDb.renameDeAccount(findDeAccountId, str, account.name)) {
                        android.util.Log.e(TAG, "renameAccount failed");
                        return null;
                    }
                    userAccounts.accountsDb.setTransactionSuccessful();
                    userAccounts.accountsDb.endTransaction();
                    android.accounts.Account insertAccountIntoCacheLocked = insertAccountIntoCacheLocked(userAccounts, account2);
                    java.util.Map map = (java.util.Map) userAccounts.userDataCache.get(account);
                    java.util.Map map2 = (java.util.Map) userAccounts.authTokenCache.get(account);
                    java.util.Map map3 = (java.util.Map) userAccounts.visibilityCache.get(account);
                    removeAccountFromCacheLocked(userAccounts, account);
                    userAccounts.userDataCache.put(insertAccountIntoCacheLocked, map);
                    userAccounts.authTokenCache.put(insertAccountIntoCacheLocked, map2);
                    userAccounts.visibilityCache.put(insertAccountIntoCacheLocked, map3);
                    userAccounts.previousNameCache.put(insertAccountIntoCacheLocked, new java.util.concurrent.atomic.AtomicReference(account.name));
                    int i = userAccounts.userId;
                    if (canHaveProfile(i)) {
                        for (android.content.pm.UserInfo userInfo : getUserManager().getAliveUsers()) {
                            if (userInfo.isRestricted() && userInfo.restrictedProfileParentId == i) {
                                renameSharedAccountAsUser(account, str, userInfo.id);
                            }
                        }
                    }
                    sendNotificationAccountUpdated(insertAccountIntoCacheLocked, userAccounts);
                    sendAccountsChangedBroadcast(userAccounts.userId, account.type, "renameAccount");
                    java.util.Iterator<java.lang.String> it = accountRemovedReceivers.iterator();
                    while (it.hasNext()) {
                        sendAccountRemovedBroadcast(account, it.next(), userAccounts.userId, "renameAccount");
                    }
                    android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
                    android.accounts.AccountManager.invalidateLocalAccountUserDataCaches();
                    return insertAccountIntoCacheLocked;
                } finally {
                    userAccounts.accountsDb.endTransaction();
                }
            }
        }
    }

    private boolean canHaveProfile(int i) {
        android.content.pm.UserInfo userInfo = getUserManager().getUserInfo(i);
        return userInfo != null && userInfo.canHaveProfile();
    }

    public void removeAccountAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, boolean z, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "removeAccount: " + account + ", response " + iAccountManagerResponse + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid() + ", for user id " + i);
        }
        com.android.internal.util.Preconditions.checkArgument(account != null, "account cannot be null");
        com.android.internal.util.Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        if (isCrossUser(callingUid, i)) {
            throw new java.lang.SecurityException(java.lang.String.format("User %s tying remove account for %s", java.lang.Integer.valueOf(android.os.UserHandle.getCallingUserId()), java.lang.Integer.valueOf(i)));
        }
        if (!isAccountManagedByCaller(account.type, callingUid, android.os.UserHandle.of(i).getIdentifier()) && !isSystemUid(callingUid) && !isProfileOwner(callingUid)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot remove accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        if (!canUserModifyAccounts(i, callingUid)) {
            try {
                iAccountManagerResponse.onError(100, "User cannot modify accounts");
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        if (!canUserModifyAccountsForType(i, account.type, callingUid)) {
            try {
                iAccountManagerResponse.onError(101, "User cannot modify accounts of this type (policy).");
                return;
            } catch (android.os.RemoteException e2) {
                android.util.Log.w(TAG, "RemoteException while removing account", e2);
                return;
            }
        }
        if (isFirstAccountRemovalDisabled(account)) {
            try {
                iAccountManagerResponse.onError(101, "User cannot remove the first " + account.type + " account on the device.");
                return;
            } catch (android.os.RemoteException e3) {
                android.util.Log.w(TAG, "RemoteException while removing account", e3);
                return;
            }
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(i);
        cancelNotification(getSigninRequiredNotificationId(userAccounts, account), userAccounts);
        synchronized (userAccounts.credentialsPermissionNotificationIds) {
            try {
                for (android.util.Pair pair : userAccounts.credentialsPermissionNotificationIds.keySet()) {
                    if (account.equals(((android.util.Pair) pair.first).first)) {
                        cancelNotification((com.android.server.accounts.AccountManagerService.NotificationId) userAccounts.credentialsPermissionNotificationIds.get(pair), userAccounts);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        logRecord(com.android.server.accounts.AccountsDb.DEBUG_ACTION_CALLED_ACCOUNT_REMOVE, "accounts", userAccounts.accountsDb.findDeAccountId(account), userAccounts, callingUid);
        try {
            new com.android.server.accounts.AccountManagerService.RemoveAccountSession(userAccounts, iAccountManagerResponse, account, z).bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean removeAccountExplicitly(android.accounts.Account account) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "removeAccountExplicitly: " + account + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        if (account == null) {
            android.util.Log.e(TAG, "account is null");
            return false;
        }
        if (!isAccountManagedByCaller(account.type, callingUid, identifier)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot explicitly remove accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        if (isFirstAccountRemovalDisabled(account)) {
            android.util.Log.e(TAG, "Cannot remove the first " + account.type + " account on the device.");
            return false;
        }
        com.android.server.accounts.AccountManagerService.UserAccounts userAccountsForCaller = getUserAccountsForCaller();
        logRecord(com.android.server.accounts.AccountsDb.DEBUG_ACTION_CALLED_ACCOUNT_REMOVE, "accounts", userAccountsForCaller.accountsDb.findDeAccountId(account), userAccountsForCaller, callingUid);
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return removeAccountInternal(userAccountsForCaller, account, callingUid);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private class RemoveAccountSession extends com.android.server.accounts.AccountManagerService.Session {
        final android.accounts.Account mAccount;

        public RemoveAccountSession(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account account, boolean z) {
            super(com.android.server.accounts.AccountManagerService.this, userAccounts, iAccountManagerResponse, account.type, z, true, account.name, false);
            this.mAccount = account;
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        protected java.lang.String toDebugString(long j) {
            return super.toDebugString(j) + ", removeAccount, account " + this.mAccount;
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void run() throws android.os.RemoteException {
            this.mAuthenticator.getAccountRemovalAllowed(this, this.mAccount);
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void onResult(android.os.Bundle bundle) {
            android.os.Bundle.setDefusable(bundle, true);
            if (bundle != null && bundle.containsKey("booleanResult") && !bundle.containsKey("intent")) {
                if (bundle.getBoolean("booleanResult")) {
                    com.android.server.accounts.AccountManagerService.this.removeAccountInternal(this.mAccounts, this.mAccount, android.accounts.IAccountAuthenticatorResponse.Stub.getCallingUid());
                }
                android.accounts.IAccountManagerResponse responseAndClose = getResponseAndClose();
                if (responseAndClose != null) {
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                    }
                    try {
                        responseAndClose.onResult(bundle);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.accounts.AccountManagerService.TAG, "Error calling onResult()", e);
                    }
                }
            }
            super.onResult(bundle);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void removeAccountInternal(android.accounts.Account account) {
        removeAccountInternal(getUserAccountsForCaller(), account, android.accounts.IAccountManager.Stub.getCallingUid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00be, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01ce, code lost:
    
        throw r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean removeAccountInternal(final com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, final android.accounts.Account account, int i) {
        boolean z;
        boolean isLocalUnlockedUser = isLocalUnlockedUser(userAccounts.userId);
        if (!isLocalUnlockedUser) {
            android.util.Slog.i(TAG, "Removing account " + account.toSafeString() + " while user " + userAccounts.userId + " is still locked. CE data will be removed later");
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    java.util.Map<java.lang.String, java.lang.Integer> requestingPackages = getRequestingPackages(account, userAccounts);
                    java.util.List<java.lang.String> accountRemovedReceivers = getAccountRemovedReceivers(account, userAccounts);
                    userAccounts.accountsDb.beginTransaction();
                    long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                    if (findDeAccountId < 0) {
                        z = false;
                    } else {
                        z = userAccounts.accountsDb.deleteDeAccount(findDeAccountId);
                    }
                    if (isLocalUnlockedUser) {
                        long findCeAccountId = userAccounts.accountsDb.findCeAccountId(account);
                        if (findCeAccountId >= 0) {
                            userAccounts.accountsDb.deleteCeAccount(findCeAccountId);
                        }
                    }
                    userAccounts.accountsDb.setTransactionSuccessful();
                    userAccounts.accountsDb.endTransaction();
                    if (z) {
                        removeAccountFromCacheLocked(userAccounts, account);
                        for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : requestingPackages.entrySet()) {
                            if (entry.getValue().intValue() != 1 && entry.getValue().intValue() != 2) {
                            }
                            notifyPackage(entry.getKey(), userAccounts);
                        }
                        android.util.Log.i(TAG, "callingUid=" + i + ", userId=" + userAccounts.userId + " removed account");
                        sendAccountsChangedBroadcast(userAccounts.userId, account.type, "removeAccount");
                        java.util.Iterator<java.lang.String> it = accountRemovedReceivers.iterator();
                        while (it.hasNext()) {
                            sendAccountRemovedBroadcast(account, it.next(), userAccounts.userId, "removeAccount");
                        }
                        logRecord(isLocalUnlockedUser ? com.android.server.accounts.AccountsDb.DEBUG_ACTION_ACCOUNT_REMOVE : com.android.server.accounts.AccountsDb.DEBUG_ACTION_ACCOUNT_REMOVE_DE, "accounts", findDeAccountId, userAccounts);
                    }
                } finally {
                    userAccounts.accountsDb.endTransaction();
                }
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int i2 = userAccounts.userId;
            if (canHaveProfile(i2)) {
                for (android.content.pm.UserInfo userInfo : getUserManager().getAliveUsers()) {
                    if (userInfo.isRestricted() && i2 == userInfo.restrictedProfileParentId) {
                        removeSharedAccountAsUser(account, userInfo.id, i);
                    }
                }
            }
            if (z) {
                synchronized (userAccounts.credentialsPermissionNotificationIds) {
                    try {
                        for (android.util.Pair pair : userAccounts.credentialsPermissionNotificationIds.keySet()) {
                            if (account.equals(((android.util.Pair) pair.first).first) && "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE".equals(((android.util.Pair) pair.first).second)) {
                                final int intValue = ((java.lang.Integer) pair.second).intValue();
                                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        com.android.server.accounts.AccountManagerService.this.lambda$removeAccountInternal$2(account, intValue, userAccounts);
                                    }
                                });
                            }
                        }
                    } finally {
                    }
                }
            }
            android.accounts.AccountManager.invalidateLocalAccountUserDataCaches();
            return z;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeAccountInternal$2(android.accounts.Account account, int i, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        cancelAccountAccessRequestNotificationIfNeeded(account, i, false, userAccounts);
    }

    public void invalidateAuthToken(java.lang.String str, java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        java.util.Objects.requireNonNull(str, "accountType cannot be null");
        java.util.Objects.requireNonNull(str2, "authToken cannot be null");
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "invalidateAuthToken: accountType " + str + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(callingUserId);
            synchronized (userAccounts.dbLock) {
                userAccounts.accountsDb.beginTransaction();
                try {
                    java.util.List<android.util.Pair<android.accounts.Account, java.lang.String>> invalidateAuthTokenLocked = invalidateAuthTokenLocked(userAccounts, str, str2);
                    userAccounts.accountsDb.setTransactionSuccessful();
                    userAccounts.accountsDb.endTransaction();
                    synchronized (userAccounts.cacheLock) {
                        try {
                            for (android.util.Pair<android.accounts.Account, java.lang.String> pair : invalidateAuthTokenLocked) {
                                writeAuthTokenIntoCacheLocked(userAccounts, (android.accounts.Account) pair.first, (java.lang.String) pair.second, null);
                            }
                            userAccounts.accountTokenCaches.remove(str, str2);
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    userAccounts.accountsDb.endTransaction();
                    throw th2;
                }
            }
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private java.util.List<android.util.Pair<android.accounts.Account, java.lang.String>> invalidateAuthTokenLocked(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, java.lang.String str, java.lang.String str2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.database.Cursor findAuthtokenForAllAccounts = userAccounts.accountsDb.findAuthtokenForAllAccounts(str, str2);
        while (findAuthtokenForAllAccounts.moveToNext()) {
            try {
                java.lang.String string = findAuthtokenForAllAccounts.getString(0);
                java.lang.String string2 = findAuthtokenForAllAccounts.getString(1);
                java.lang.String string3 = findAuthtokenForAllAccounts.getString(2);
                userAccounts.accountsDb.deleteAuthToken(string);
                arrayList.add(android.util.Pair.create(new android.accounts.Account(string2, str), string3));
            } finally {
                findAuthtokenForAllAccounts.close();
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveCachedToken(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str, byte[] bArr, java.lang.String str2, java.lang.String str3, long j) {
        if (account == null || str2 == null || str == null || bArr == null) {
            return;
        }
        cancelNotification(getSigninRequiredNotificationId(userAccounts, account), userAccounts);
        synchronized (userAccounts.cacheLock) {
            userAccounts.accountTokenCaches.put(account, str3, str2, str, bArr, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean saveAuthTokenToDatabase(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str, java.lang.String str2) {
        if (account == null || str == null) {
            return false;
        }
        cancelNotification(getSigninRequiredNotificationId(userAccounts, account), userAccounts);
        synchronized (userAccounts.dbLock) {
            userAccounts.accountsDb.beginTransaction();
            try {
                long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                if (findDeAccountId < 0) {
                    userAccounts.accountsDb.endTransaction();
                    return false;
                }
                userAccounts.accountsDb.deleteAuthtokensByAccountIdAndType(findDeAccountId, str);
                if (userAccounts.accountsDb.insertAuthToken(findDeAccountId, str, str2) < 0) {
                    userAccounts.accountsDb.endTransaction();
                    return false;
                }
                userAccounts.accountsDb.setTransactionSuccessful();
                userAccounts.accountsDb.endTransaction();
                synchronized (userAccounts.cacheLock) {
                    writeAuthTokenIntoCacheLocked(userAccounts, account, str, str2);
                }
                return true;
            } catch (java.lang.Throwable th) {
                userAccounts.accountsDb.endTransaction();
                throw th;
            }
        }
    }

    public java.lang.String peekAuthToken(android.accounts.Account account, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "peekAuthToken: " + account + ", authTokenType " + str + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        java.util.Objects.requireNonNull(account, "account cannot be null");
        java.util.Objects.requireNonNull(str, "authTokenType cannot be null");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot peek the authtokens associated with accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        if (!isLocalUnlockedUser(callingUserId)) {
            android.util.Log.w(TAG, "Authtoken not available - user " + callingUserId + " data is locked. callingUid " + callingUid);
            return null;
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return readAuthTokenInternal(getUserAccounts(callingUserId), account, str);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setAuthToken(android.accounts.Account account, java.lang.String str, java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "setAuthToken: " + account + ", authTokenType " + str + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        java.util.Objects.requireNonNull(account, "account cannot be null");
        java.util.Objects.requireNonNull(str, "authTokenType cannot be null");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot set auth tokens associated with accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            saveAuthTokenToDatabase(getUserAccounts(callingUserId), account, str, str2);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setPassword(android.accounts.Account account, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "setAuthToken: " + account + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        java.util.Objects.requireNonNull(account, "account cannot be null");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot set secrets for accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            setPasswordInternal(getUserAccounts(callingUserId), account, str, callingUid);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void setPasswordInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str, int i) {
        java.lang.String str2;
        if (account == null) {
            return;
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    userAccounts.accountsDb.beginTransaction();
                    boolean z = false;
                    try {
                        long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                        if (findDeAccountId >= 0) {
                            userAccounts.accountsDb.updateCeAccountPassword(findDeAccountId, str);
                            userAccounts.accountsDb.deleteAuthTokensByAccountId(findDeAccountId);
                            userAccounts.authTokenCache.remove(account);
                            userAccounts.accountTokenCaches.remove(account);
                            userAccounts.accountsDb.setTransactionSuccessful();
                            z = true;
                            if (str == null || str.length() == 0) {
                                str2 = com.android.server.accounts.AccountsDb.DEBUG_ACTION_CLEAR_PASSWORD;
                            } else {
                                str2 = com.android.server.accounts.AccountsDb.DEBUG_ACTION_SET_PASSWORD;
                            }
                            logRecord(str2, "accounts", findDeAccountId, userAccounts, i);
                        }
                        userAccounts.accountsDb.endTransaction();
                        if (z) {
                            sendNotificationAccountUpdated(account, userAccounts);
                            android.util.Log.i(TAG, "callingUid=" + i + " changed password");
                            sendAccountsChangedBroadcast(userAccounts.userId, account.type, "setPassword");
                        }
                    } catch (java.lang.Throwable th) {
                        userAccounts.accountsDb.endTransaction();
                        if (z) {
                            sendNotificationAccountUpdated(account, userAccounts);
                            android.util.Log.i(TAG, "callingUid=" + i + " changed password");
                            sendAccountsChangedBroadcast(userAccounts.userId, account.type, "setPassword");
                        }
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }
    }

    public void clearPassword(android.accounts.Account account) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "clearPassword: " + account + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        java.util.Objects.requireNonNull(account, "account cannot be null");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot clear passwords for accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            setPasswordInternal(getUserAccounts(callingUserId), account, null, callingUid);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setUserData(android.accounts.Account account, java.lang.String str, java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "setUserData: " + account + ", key " + str + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("key is null");
        }
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(account.type, callingUid, callingUserId)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot set user data for accounts of type: %s", java.lang.Integer.valueOf(callingUid), account.type));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(callingUserId);
            if (!accountExistsCache(userAccounts, account)) {
                return;
            }
            setUserdataInternal(userAccounts, account, str, str2);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean accountExistsCache(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account) {
        synchronized (userAccounts.cacheLock) {
            try {
                if (userAccounts.accountCache.containsKey(account.type)) {
                    for (android.accounts.Account account2 : userAccounts.accountCache.get(account.type)) {
                        if (account2.name.equals(account.name)) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setUserdataInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str, java.lang.String str2) {
        synchronized (userAccounts.dbLock) {
            userAccounts.accountsDb.beginTransaction();
            try {
                long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                if (findDeAccountId < 0) {
                    return;
                }
                long findExtrasIdByAccountId = userAccounts.accountsDb.findExtrasIdByAccountId(findDeAccountId, str);
                if (findExtrasIdByAccountId < 0) {
                    if (userAccounts.accountsDb.insertExtra(findDeAccountId, str, str2) < 0) {
                        return;
                    }
                } else if (!userAccounts.accountsDb.updateExtra(findExtrasIdByAccountId, str2)) {
                    return;
                }
                userAccounts.accountsDb.setTransactionSuccessful();
                userAccounts.accountsDb.endTransaction();
                synchronized (userAccounts.cacheLock) {
                    writeUserDataIntoCacheLocked(userAccounts, account, str, str2);
                    android.accounts.AccountManager.invalidateLocalAccountUserDataCaches();
                }
            } finally {
                userAccounts.accountsDb.endTransaction();
            }
        }
    }

    private void onResult(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.os.Bundle bundle) {
        if (bundle == null) {
            android.util.Log.e(TAG, "the result is unexpectedly null", new java.lang.Exception());
        }
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, getClass().getSimpleName() + " calling onResult() on response " + iAccountManagerResponse);
        }
        try {
            iAccountManagerResponse.onResult(bundle);
        } catch (android.os.RemoteException e) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "failure while notifying response", e);
            }
        }
    }

    public void getAuthTokenLabel(android.accounts.IAccountManagerResponse iAccountManagerResponse, final java.lang.String str, final java.lang.String str2) throws android.os.RemoteException {
        com.android.internal.util.Preconditions.checkArgument(str != null, "accountType cannot be null");
        com.android.internal.util.Preconditions.checkArgument(str2 != null, "authTokenType cannot be null");
        int callingUid = android.accounts.IAccountManager.Stub.getCallingUid();
        android.accounts.IAccountManager.Stub.clearCallingIdentity();
        if (android.os.UserHandle.getAppId(callingUid) != 1000) {
            throw new java.lang.SecurityException("can only call from system");
        }
        int userId = android.os.UserHandle.getUserId(callingUid);
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            new com.android.server.accounts.AccountManagerService.Session(getUserAccounts(userId), iAccountManagerResponse, str, false, false, null, false) { // from class: com.android.server.accounts.AccountManagerService.7
                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    return super.toDebugString(j) + ", getAuthTokenLabel, " + str + ", authTokenType " + str2;
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    this.mAuthenticator.getAuthTokenLabel(this, str2);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public void onResult(android.os.Bundle bundle) {
                    android.os.Bundle.setDefusable(bundle, true);
                    if (bundle != null) {
                        java.lang.String string = bundle.getString("authTokenLabelKey");
                        android.os.Bundle bundle2 = new android.os.Bundle();
                        bundle2.putString("authTokenLabelKey", string);
                        super.onResult(bundle2);
                        return;
                    }
                    super.onResult(bundle);
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getAuthToken(android.accounts.IAccountManagerResponse iAccountManagerResponse, final android.accounts.Account account, final java.lang.String str, final boolean z, boolean z2, final android.os.Bundle bundle) {
        java.lang.String str2;
        int i;
        java.lang.String str3;
        int i2;
        java.lang.String readAuthTokenInternal;
        android.os.Bundle.setDefusable(bundle, true);
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "getAuthToken: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", notifyOnAuthFailure " + z + ", expectActivityLaunch " + z2 + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid());
        }
        com.android.internal.util.Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        try {
            if (account == null) {
                android.util.Slog.w(TAG, "getAuthToken called with null account");
                iAccountManagerResponse.onError(7, "account is null");
                return;
            }
            if (str == null) {
                android.util.Slog.w(TAG, "getAuthToken called with null authTokenType");
                iAccountManagerResponse.onError(7, "authTokenType is null");
                return;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                final com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(callingUserId);
                android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription> serviceInfo = this.mAuthenticatorCache.getServiceInfo(android.accounts.AuthenticatorDescription.newKey(account.type), userAccounts.userId);
                boolean z3 = serviceInfo != null && ((android.accounts.AuthenticatorDescription) serviceInfo.type).customTokens;
                int callingUid = android.os.Binder.getCallingUid();
                boolean z4 = z3 || permissionIsGranted(account, str, callingUid, callingUserId);
                java.lang.String string = bundle.getString("androidPackageName");
                clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(callingUid);
                    if (string == null || packagesForUid == null) {
                        str2 = string;
                        i = callingUid;
                    } else {
                        if (com.android.internal.util.ArrayUtils.contains(packagesForUid, string)) {
                            bundle.putInt("callerUid", callingUid);
                            bundle.putInt("callerPid", android.os.Binder.getCallingPid());
                            if (z) {
                                bundle.putBoolean("notifyOnAuthFailure", true);
                            }
                            long clearCallingIdentity2 = android.accounts.IAccountManager.Stub.clearCallingIdentity();
                            try {
                                final byte[] calculatePackageSignatureDigest = calculatePackageSignatureDigest(string, callingUserId);
                                if (!z3 && z4 && (readAuthTokenInternal = readAuthTokenInternal(userAccounts, account, str)) != null) {
                                    logGetAuthTokenMetrics(string, account.type);
                                    android.os.Bundle bundle2 = new android.os.Bundle();
                                    bundle2.putString("authtoken", readAuthTokenInternal);
                                    bundle2.putString("authAccount", account.name);
                                    bundle2.putString("accountType", account.type);
                                    onResult(iAccountManagerResponse, bundle2);
                                    return;
                                }
                                if (z3) {
                                    i2 = callingUid;
                                    str3 = string;
                                    com.android.server.accounts.TokenCache.Value readCachedTokenInternal = readCachedTokenInternal(userAccounts, account, str, string, calculatePackageSignatureDigest);
                                    if (readCachedTokenInternal != null) {
                                        logGetAuthTokenMetrics(str3, account.type);
                                        if (android.util.Log.isLoggable(TAG, 2)) {
                                            android.util.Log.v(TAG, "getAuthToken: cache hit ofr custom token authenticator.");
                                        }
                                        android.os.Bundle bundle3 = new android.os.Bundle();
                                        bundle3.putString("authtoken", readCachedTokenInternal.token);
                                        bundle3.putLong("android.accounts.expiry", readCachedTokenInternal.expiryEpochMillis);
                                        bundle3.putString("authAccount", account.name);
                                        bundle3.putString("accountType", account.type);
                                        onResult(iAccountManagerResponse, bundle3);
                                        return;
                                    }
                                } else {
                                    str3 = string;
                                    i2 = callingUid;
                                }
                                final int i3 = i2;
                                final java.lang.String str4 = str3;
                                final boolean z5 = z4;
                                final boolean z6 = z3;
                                new com.android.server.accounts.AccountManagerService.Session(userAccounts, iAccountManagerResponse, account.type, z2, false, account.name, false) { // from class: com.android.server.accounts.AccountManagerService.8
                                    @Override // com.android.server.accounts.AccountManagerService.Session
                                    protected java.lang.String toDebugString(long j) {
                                        if (bundle != null) {
                                            bundle.keySet();
                                        }
                                        return super.toDebugString(j) + ", getAuthToken, " + account.toSafeString() + ", authTokenType " + str + ", loginOptions " + bundle + ", notifyOnAuthFailure " + z;
                                    }

                                    @Override // com.android.server.accounts.AccountManagerService.Session
                                    public void run() throws android.os.RemoteException {
                                        if (!z5) {
                                            this.mAuthenticator.getAuthTokenLabel(this, str);
                                        } else {
                                            this.mAuthenticator.getAuthToken(this, account, str, bundle);
                                            com.android.server.accounts.AccountManagerService.this.logGetAuthTokenMetrics(str4, account.type);
                                        }
                                    }

                                    @Override // com.android.server.accounts.AccountManagerService.Session
                                    public void onResult(android.os.Bundle bundle4) {
                                        android.os.Bundle.setDefusable(bundle4, true);
                                        if (bundle4 != null) {
                                            if (bundle4.containsKey("authTokenLabelKey")) {
                                                android.content.Intent newGrantCredentialsPermissionIntent = com.android.server.accounts.AccountManagerService.this.newGrantCredentialsPermissionIntent(account, null, i3, new android.accounts.AccountAuthenticatorResponse((android.accounts.IAccountAuthenticatorResponse) this), str, true);
                                                this.mCanStartAccountManagerActivity = true;
                                                android.os.Bundle bundle5 = new android.os.Bundle();
                                                bundle5.putParcelable("intent", newGrantCredentialsPermissionIntent);
                                                onResult(bundle5);
                                                return;
                                            }
                                            java.lang.String string2 = bundle4.getString("authtoken");
                                            if (string2 != null) {
                                                java.lang.String string3 = bundle4.getString("authAccount");
                                                java.lang.String string4 = bundle4.getString("accountType");
                                                if (android.text.TextUtils.isEmpty(string4) || android.text.TextUtils.isEmpty(string3)) {
                                                    onError(5, "the type and name should not be empty");
                                                    return;
                                                }
                                                android.accounts.Account account2 = new android.accounts.Account(string3, string4);
                                                if (!z6) {
                                                    com.android.server.accounts.AccountManagerService.this.saveAuthTokenToDatabase(this.mAccounts, account2, str, string2);
                                                }
                                                long j = bundle4.getLong("android.accounts.expiry", 0L);
                                                if (z6 && j > java.lang.System.currentTimeMillis()) {
                                                    com.android.server.accounts.AccountManagerService.this.saveCachedToken(this.mAccounts, account, str4, calculatePackageSignatureDigest, str, string2, j);
                                                }
                                            }
                                            android.content.Intent intent = (android.content.Intent) bundle4.getParcelable("intent", android.content.Intent.class);
                                            if (intent != null && z && !z6) {
                                                if (!checkKeyIntent(android.os.Binder.getCallingUid(), bundle4)) {
                                                    onError(5, "invalid intent in bundle returned");
                                                    return;
                                                }
                                                com.android.server.accounts.AccountManagerService.this.doNotification(this.mAccounts, account, bundle4.getString("authFailedMessage"), intent, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, userAccounts.userId);
                                            }
                                        }
                                        super.onResult(bundle4);
                                    }
                                }.bind();
                                return;
                            } finally {
                                android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity2);
                            }
                        }
                        str2 = string;
                        i = callingUid;
                    }
                    throw new java.lang.SecurityException(java.lang.String.format("Uid %s is attempting to illegally masquerade as package %s!", java.lang.Integer.valueOf(i), str2));
                } finally {
                }
            } finally {
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to report error back to the client." + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logGetAuthTokenMetrics(java.lang.String str, java.lang.String str2) {
        android.app.admin.DevicePolicyEventLogger.createEvent(204).setStrings(new java.lang.String[]{android.text.TextUtils.emptyIfNull(str), android.text.TextUtils.emptyIfNull(str2)}).write();
    }

    private byte[] calculatePackageSignatureDigest(java.lang.String str, int i) {
        java.security.MessageDigest messageDigest;
        try {
            messageDigest = java.security.MessageDigest.getInstance("SHA-256");
            for (android.content.pm.Signature signature : this.mPackageManager.getPackageInfoAsUser(str, 64, i).signatures) {
                messageDigest.update(signature.toByteArray());
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "Could not find packageinfo for: " + str);
            messageDigest = null;
        } catch (java.security.NoSuchAlgorithmException e2) {
            android.util.Log.wtf(TAG, "SHA-256 should be available", e2);
            messageDigest = null;
        }
        if (messageDigest == null) {
            return null;
        }
        return messageDigest.digest();
    }

    private void createNoCredentialsPermissionNotification(android.accounts.Account account, android.content.Intent intent, java.lang.String str, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        java.lang.String str2;
        int i = userAccounts.userId;
        int intExtra = intent.getIntExtra(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, -1);
        java.lang.String stringExtra = intent.getStringExtra("authTokenType");
        java.lang.String string = this.mContext.getString(android.R.string.permgroupdesc_calllog, getApplicationLabel(str, i), account.name);
        int indexOf = string.indexOf(10);
        if (indexOf <= 0) {
            str2 = "";
        } else {
            java.lang.String substring = string.substring(0, indexOf);
            str2 = string.substring(indexOf + 1);
            string = substring;
        }
        android.os.UserHandle of = android.os.UserHandle.of(i);
        android.content.Context contextForUser = getContextForUser(of);
        installNotification(getCredentialPermissionNotificationId(account, stringExtra, intExtra, userAccounts), new android.app.Notification.Builder(contextForUser, com.android.internal.notification.SystemNotificationChannels.ACCOUNT).setSmallIcon(android.R.drawable.stat_sys_warning).setWhen(0L).setColor(contextForUser.getColor(android.R.color.system_notification_accent_color)).setContentTitle(string).setContentText(str2).setContentIntent(android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF, null, of)).build(), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, of.getIdentifier());
    }

    private java.lang.String getApplicationLabel(java.lang.String str, int i) {
        try {
            return this.mPackageManager.getApplicationLabel(this.mPackageManager.getApplicationInfoAsUser(str, 0, i)).toString();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.Intent newGrantCredentialsPermissionIntent(android.accounts.Account account, java.lang.String str, int i, android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse, java.lang.String str2, boolean z) {
        android.content.Intent intent = new android.content.Intent(this.mContext, (java.lang.Class<?>) android.accounts.GrantCredentialsPermissionActivity.class);
        if (z) {
            intent.setFlags(268435456);
        }
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(android.os.UserHandle.getUserId(i));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(getCredentialPermissionNotificationId(account, str2, i, userAccounts).mTag);
        if (str == null) {
            str = "";
        }
        sb.append(str);
        intent.addCategory(sb.toString());
        intent.putExtra("account", account);
        intent.putExtra("authTokenType", str2);
        intent.putExtra("response", accountAuthenticatorResponse);
        intent.putExtra(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, i);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.accounts.AccountManagerService.NotificationId getCredentialPermissionNotificationId(android.accounts.Account account, java.lang.String str, int i, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        com.android.server.accounts.AccountManagerService.NotificationId notificationId;
        synchronized (userAccounts.credentialsPermissionNotificationIds) {
            try {
                android.util.Pair pair = new android.util.Pair(new android.util.Pair(account, str), java.lang.Integer.valueOf(i));
                notificationId = (com.android.server.accounts.AccountManagerService.NotificationId) userAccounts.credentialsPermissionNotificationIds.get(pair);
                if (notificationId == null) {
                    notificationId = new com.android.server.accounts.AccountManagerService.NotificationId("AccountManagerService:38:" + account.hashCode() + ":" + str.hashCode() + ":" + i, 38);
                    userAccounts.credentialsPermissionNotificationIds.put(pair, notificationId);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return notificationId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.accounts.AccountManagerService.NotificationId getSigninRequiredNotificationId(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account) {
        com.android.server.accounts.AccountManagerService.NotificationId notificationId;
        synchronized (userAccounts.signinRequiredNotificationIds) {
            try {
                notificationId = (com.android.server.accounts.AccountManagerService.NotificationId) userAccounts.signinRequiredNotificationIds.get(account);
                if (notificationId == null) {
                    com.android.server.accounts.AccountManagerService.NotificationId notificationId2 = new com.android.server.accounts.AccountManagerService.NotificationId("AccountManagerService:37:" + account.hashCode(), 37);
                    userAccounts.signinRequiredNotificationIds.put(account, notificationId2);
                    notificationId = notificationId2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return notificationId;
    }

    public void addAccount(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle) {
        android.os.Bundle.setDefusable(bundle, true);
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "addAccount: accountType " + str + ", response " + iAccountManagerResponse + ", authTokenType " + str2 + ", requiredFeatures " + java.util.Arrays.toString(strArr) + ", expectActivityLaunch " + z + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new java.lang.IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("accountType is null");
        }
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        if (!canUserModifyAccounts(userId, callingUid)) {
            try {
                iAccountManagerResponse.onError(100, "User is not allowed to add an account!");
            } catch (android.os.RemoteException e) {
            }
            showCantAddAccount(100, userId);
        } else if (!canUserModifyAccountsForType(userId, str, callingUid)) {
            try {
                iAccountManagerResponse.onError(101, "User cannot modify accounts of this type (policy).");
            } catch (android.os.RemoteException e2) {
            }
            showCantAddAccount(101, userId);
        } else {
            addAccountAndLogMetrics(iAccountManagerResponse, str, str2, strArr, z, bundle, userId);
        }
    }

    public void addAccountAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z, android.os.Bundle bundle, int i) {
        boolean z2;
        boolean z3 = true;
        android.os.Bundle.setDefusable(bundle, true);
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "addAccount: accountType " + str + ", response " + iAccountManagerResponse + ", authTokenType " + str2 + ", requiredFeatures " + java.util.Arrays.toString(strArr) + ", expectActivityLaunch " + z + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid() + ", for user id " + i);
        }
        if (iAccountManagerResponse != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        com.android.internal.util.Preconditions.checkArgument(z2, "response cannot be null");
        if (str == null) {
            z3 = false;
        }
        com.android.internal.util.Preconditions.checkArgument(z3, "accountType cannot be null");
        if (isCrossUser(callingUid, i)) {
            throw new java.lang.SecurityException(java.lang.String.format("User %s trying to add account for %s", java.lang.Integer.valueOf(android.os.UserHandle.getCallingUserId()), java.lang.Integer.valueOf(i)));
        }
        if (!canUserModifyAccounts(i, callingUid)) {
            try {
                iAccountManagerResponse.onError(100, "User is not allowed to add an account!");
            } catch (android.os.RemoteException e) {
            }
            showCantAddAccount(100, i);
        } else if (!canUserModifyAccountsForType(i, str, callingUid)) {
            try {
                iAccountManagerResponse.onError(101, "User cannot modify accounts of this type (policy).");
            } catch (android.os.RemoteException e2) {
            }
            showCantAddAccount(101, i);
        } else {
            addAccountAndLogMetrics(iAccountManagerResponse, str, str2, strArr, z, bundle, i);
        }
    }

    private void addAccountAndLogMetrics(android.accounts.IAccountManagerResponse iAccountManagerResponse, final java.lang.String str, final java.lang.String str2, final java.lang.String[] strArr, boolean z, android.os.Bundle bundle, int i) {
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        final android.os.Bundle bundle2 = bundle == null ? new android.os.Bundle() : bundle;
        bundle2.putInt("callerUid", callingUid);
        bundle2.putInt("callerPid", callingPid);
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(i);
            logRecordWithUid(userAccounts, com.android.server.accounts.AccountsDb.DEBUG_ACTION_CALLED_ACCOUNT_ADD, "accounts", callingUid);
            new com.android.server.accounts.AccountManagerService.Session(userAccounts, iAccountManagerResponse, str, z, true, null, false, true) { // from class: com.android.server.accounts.AccountManagerService.9
                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    this.mAuthenticator.addAccount(this, this.mAccountType, str2, strArr, bundle2);
                    com.android.server.accounts.AccountManagerService.this.logAddAccountMetrics(bundle2.getString("androidPackageName"), str, strArr, str2);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    java.lang.String str3;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(super.toDebugString(j));
                    sb.append(", addAccount, accountType ");
                    sb.append(str);
                    sb.append(", requiredFeatures ");
                    if (strArr != null) {
                        str3 = android.text.TextUtils.join(",", strArr);
                    } else {
                        str3 = null;
                    }
                    sb.append(str3);
                    return sb.toString();
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logAddAccountMetrics(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, java.lang.String str3) {
        java.lang.String join;
        android.app.admin.DevicePolicyEventLogger createEvent = android.app.admin.DevicePolicyEventLogger.createEvent(202);
        java.lang.String emptyIfNull = android.text.TextUtils.emptyIfNull(str2);
        java.lang.String emptyIfNull2 = android.text.TextUtils.emptyIfNull(str);
        java.lang.String emptyIfNull3 = android.text.TextUtils.emptyIfNull(str3);
        if (strArr == null) {
            join = "";
        } else {
            join = android.text.TextUtils.join(";", strArr);
        }
        createEvent.setStrings(new java.lang.String[]{emptyIfNull, emptyIfNull2, emptyIfNull3, join}).write();
    }

    public void startAddAccountSession(android.accounts.IAccountManagerResponse iAccountManagerResponse, final java.lang.String str, final java.lang.String str2, final java.lang.String[] strArr, boolean z, android.os.Bundle bundle) {
        android.os.Bundle bundle2 = bundle;
        android.os.Bundle.setDefusable(bundle2, true);
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "startAddAccountSession: accountType " + str + ", response " + iAccountManagerResponse + ", authTokenType " + str2 + ", requiredFeatures " + java.util.Arrays.toString(strArr) + ", expectActivityLaunch " + z + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid());
        }
        com.android.internal.util.Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        com.android.internal.util.Preconditions.checkArgument(str != null, "accountType cannot be null");
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        if (!canUserModifyAccounts(userId, callingUid)) {
            try {
                iAccountManagerResponse.onError(100, "User is not allowed to add an account!");
            } catch (android.os.RemoteException e) {
            }
            showCantAddAccount(100, userId);
            return;
        }
        if (!canUserModifyAccountsForType(userId, str, callingUid)) {
            try {
                iAccountManagerResponse.onError(101, "User cannot modify accounts of this type (policy).");
            } catch (android.os.RemoteException e2) {
            }
            showCantAddAccount(101, userId);
            return;
        }
        int callingPid = android.os.Binder.getCallingPid();
        if (bundle2 == null) {
            bundle2 = new android.os.Bundle();
        }
        final android.os.Bundle bundle3 = bundle2;
        bundle3.putInt("callerUid", callingUid);
        bundle3.putInt("callerPid", callingPid);
        final java.lang.String string = bundle3.getString("androidPackageName");
        boolean checkPermissionAndNote = checkPermissionAndNote(string, callingUid, "android.permission.GET_PASSWORD");
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(userId);
            logRecordWithUid(userAccounts, com.android.server.accounts.AccountsDb.DEBUG_ACTION_CALLED_START_ACCOUNT_ADD, "accounts", callingUid);
            new com.android.server.accounts.AccountManagerService.StartAccountSession(userAccounts, iAccountManagerResponse, str, z, null, false, true, checkPermissionAndNote) { // from class: com.android.server.accounts.AccountManagerService.10
                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    this.mAuthenticator.startAddAccountSession(this, this.mAccountType, str2, strArr, bundle3);
                    com.android.server.accounts.AccountManagerService.this.logAddAccountMetrics(string, str, strArr, str2);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(super.toDebugString(j));
                    sb.append(", startAddAccountSession, accountType ");
                    sb.append(str);
                    sb.append(", requiredFeatures ");
                    sb.append(strArr != null ? android.text.TextUtils.join(",", strArr) : "null");
                    return sb.toString();
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private abstract class StartAccountSession extends com.android.server.accounts.AccountManagerService.Session {
        private final boolean mIsPasswordForwardingAllowed;

        public StartAccountSession(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, boolean z, java.lang.String str2, boolean z2, boolean z3, boolean z4) {
            super(userAccounts, iAccountManagerResponse, str, z, true, str2, z2, z3);
            this.mIsPasswordForwardingAllowed = z4;
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void onResult(android.os.Bundle bundle) {
            android.accounts.IAccountManagerResponse responseAndClose;
            android.os.Bundle.setDefusable(bundle, true);
            this.mNumResults++;
            if (bundle != null && !checkKeyIntent(android.os.Binder.getCallingUid(), bundle)) {
                onError(5, "invalid intent in bundle returned");
                return;
            }
            if (this.mExpectActivityLaunch && bundle != null && bundle.containsKey("intent")) {
                responseAndClose = this.mResponse;
            } else {
                responseAndClose = getResponseAndClose();
            }
            if (responseAndClose == null) {
                return;
            }
            if (bundle == null) {
                if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                    android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, getClass().getSimpleName() + " calling onError() on response " + responseAndClose);
                }
                com.android.server.accounts.AccountManagerService.this.sendErrorResponse(responseAndClose, 5, "null bundle returned");
                return;
            }
            if (bundle.getInt("errorCode", -1) > 0) {
                com.android.server.accounts.AccountManagerService.this.sendErrorResponse(responseAndClose, bundle.getInt("errorCode"), bundle.getString("errorMessage"));
                return;
            }
            if (!this.mIsPasswordForwardingAllowed) {
                bundle.remove(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PASSWORD);
            }
            bundle.remove("authtoken");
            if (!checkKeyIntent(android.os.Binder.getCallingUid(), bundle)) {
                onError(5, "invalid intent in bundle returned");
                return;
            }
            if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
            }
            android.os.Bundle bundle2 = bundle.getBundle("accountSessionBundle");
            if (bundle2 != null) {
                java.lang.String string = bundle2.getString("accountType");
                if (android.text.TextUtils.isEmpty(string) || !this.mAccountType.equalsIgnoreCase(string)) {
                    android.util.Log.w(com.android.server.accounts.AccountManagerService.TAG, "Account type in session bundle doesn't match request.");
                }
                bundle2.putString("accountType", this.mAccountType);
                try {
                    bundle.putBundle("accountSessionBundle", com.android.server.accounts.CryptoHelper.getInstance().encryptBundle(bundle2));
                } catch (java.security.GeneralSecurityException e) {
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 3)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "Failed to encrypt session bundle!", e);
                    }
                    com.android.server.accounts.AccountManagerService.this.sendErrorResponse(responseAndClose, 5, "failed to encrypt session bundle");
                    return;
                }
            }
            com.android.server.accounts.AccountManagerService.this.sendResponse(responseAndClose, bundle);
        }
    }

    public void finishSessionAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, @android.annotation.NonNull android.os.Bundle bundle, boolean z, android.os.Bundle bundle2, int i) {
        android.os.Bundle.setDefusable(bundle, true);
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "finishSession: response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + callingUid + ", caller's user id " + android.os.UserHandle.getCallingUserId() + ", pid " + android.os.Binder.getCallingPid() + ", for user id " + i);
        }
        com.android.internal.util.Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        if (bundle == null || bundle.size() == 0) {
            throw new java.lang.IllegalArgumentException("sessionBundle is empty");
        }
        if (isCrossUser(callingUid, i)) {
            throw new java.lang.SecurityException(java.lang.String.format("User %s trying to finish session for %s without cross user permission", java.lang.Integer.valueOf(android.os.UserHandle.getCallingUserId()), java.lang.Integer.valueOf(i)));
        }
        if (!canUserModifyAccounts(i, callingUid)) {
            sendErrorResponse(iAccountManagerResponse, 100, "User is not allowed to add an account!");
            showCantAddAccount(100, i);
            return;
        }
        int callingPid = android.os.Binder.getCallingPid();
        try {
            final android.os.Bundle decryptBundle = com.android.server.accounts.CryptoHelper.getInstance().decryptBundle(bundle);
            if (decryptBundle == null) {
                sendErrorResponse(iAccountManagerResponse, 8, "failed to decrypt session bundle");
                return;
            }
            final java.lang.String string = decryptBundle.getString("accountType");
            if (android.text.TextUtils.isEmpty(string)) {
                sendErrorResponse(iAccountManagerResponse, 7, "accountType is empty");
                return;
            }
            if (bundle2 != null) {
                decryptBundle.putAll(bundle2);
            }
            decryptBundle.putInt("callerUid", callingUid);
            decryptBundle.putInt("callerPid", callingPid);
            if (!canUserModifyAccountsForType(i, string, callingUid)) {
                sendErrorResponse(iAccountManagerResponse, 101, "User cannot modify accounts of this type (policy).");
                showCantAddAccount(101, i);
                return;
            }
            long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
            try {
                com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(i);
                logRecordWithUid(userAccounts, com.android.server.accounts.AccountsDb.DEBUG_ACTION_CALLED_ACCOUNT_SESSION_FINISH, "accounts", callingUid);
                new com.android.server.accounts.AccountManagerService.Session(userAccounts, iAccountManagerResponse, string, z, true, null, false, true) { // from class: com.android.server.accounts.AccountManagerService.11
                    @Override // com.android.server.accounts.AccountManagerService.Session
                    public void run() throws android.os.RemoteException {
                        this.mAuthenticator.finishSession(this, this.mAccountType, decryptBundle);
                    }

                    @Override // com.android.server.accounts.AccountManagerService.Session
                    protected java.lang.String toDebugString(long j) {
                        return super.toDebugString(j) + ", finishSession, accountType " + string;
                    }
                }.bind();
            } finally {
                android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (java.security.GeneralSecurityException e) {
            if (android.util.Log.isLoggable(TAG, 3)) {
                android.util.Log.v(TAG, "Failed to decrypt session bundle!", e);
            }
            sendErrorResponse(iAccountManagerResponse, 8, "failed to decrypt session bundle");
        }
    }

    private void showCantAddAccount(int i, int i2) {
        android.content.Intent intent;
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal == null) {
            intent = getDefaultCantAddAccountIntent(i);
        } else if (i == 100) {
            intent = devicePolicyManagerInternal.createUserRestrictionSupportIntent(i2, "no_modify_accounts");
        } else if (i != 101) {
            intent = null;
        } else {
            intent = devicePolicyManagerInternal.createShowAdminSupportIntent(i2, false);
        }
        if (intent == null) {
            intent = getDefaultCantAddAccountIntent(i);
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            this.mContext.startActivityAsUser(intent, new android.os.UserHandle(i2));
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private android.content.Intent getDefaultCantAddAccountIntent(int i) {
        android.content.Intent intent = new android.content.Intent(this.mContext, (java.lang.Class<?>) android.accounts.CantAddAccountActivity.class);
        intent.putExtra("android.accounts.extra.ERROR_CODE", i);
        intent.addFlags(268435456);
        return intent;
    }

    public void confirmCredentialsAsUser(android.accounts.IAccountManagerResponse iAccountManagerResponse, final android.accounts.Account account, final android.os.Bundle bundle, boolean z, int i) {
        android.os.Bundle.setDefusable(bundle, true);
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "confirmCredentials: " + account + ", response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        if (isCrossUser(callingUid, i)) {
            throw new java.lang.SecurityException(java.lang.String.format("User %s trying to confirm account credentials for %s", java.lang.Integer.valueOf(android.os.UserHandle.getCallingUserId()), java.lang.Integer.valueOf(i)));
        }
        if (iAccountManagerResponse == null) {
            throw new java.lang.IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            new com.android.server.accounts.AccountManagerService.Session(getUserAccounts(i), iAccountManagerResponse, account.type, z, true, account.name, true, true) { // from class: com.android.server.accounts.AccountManagerService.12
                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    this.mAuthenticator.confirmCredentials(this, account, bundle);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    return super.toDebugString(j) + ", confirmCredentials, " + account.toSafeString();
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateCredentials(android.accounts.IAccountManagerResponse iAccountManagerResponse, final android.accounts.Account account, final java.lang.String str, boolean z, final android.os.Bundle bundle) {
        android.os.Bundle.setDefusable(bundle, true);
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "updateCredentials: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", expectActivityLaunch " + z + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new java.lang.IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            new com.android.server.accounts.AccountManagerService.Session(getUserAccounts(callingUserId), iAccountManagerResponse, account.type, z, true, account.name, false, true) { // from class: com.android.server.accounts.AccountManagerService.13
                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    this.mAuthenticator.updateCredentials(this, account, str, bundle);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    if (bundle != null) {
                        bundle.keySet();
                    }
                    return super.toDebugString(j) + ", updateCredentials, " + account.toSafeString() + ", authTokenType " + str + ", loginOptions " + bundle;
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void startUpdateCredentialsSession(android.accounts.IAccountManagerResponse iAccountManagerResponse, final android.accounts.Account account, final java.lang.String str, boolean z, final android.os.Bundle bundle) {
        android.os.Bundle.setDefusable(bundle, true);
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "startUpdateCredentialsSession: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", expectActivityLaunch " + z + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new java.lang.IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        boolean checkPermissionAndNote = checkPermissionAndNote(bundle.getString("androidPackageName"), callingUid, "android.permission.GET_PASSWORD");
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            new com.android.server.accounts.AccountManagerService.StartAccountSession(getUserAccounts(callingUserId), iAccountManagerResponse, account.type, z, account.name, false, true, checkPermissionAndNote) { // from class: com.android.server.accounts.AccountManagerService.14
                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    this.mAuthenticator.startUpdateCredentialsSession(this, account, str, bundle);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    if (bundle != null) {
                        bundle.keySet();
                    }
                    return super.toDebugString(j) + ", startUpdateCredentialsSession, " + account.toSafeString() + ", authTokenType " + str + ", loginOptions " + bundle;
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void isCredentialsUpdateSuggested(android.accounts.IAccountManagerResponse iAccountManagerResponse, final android.accounts.Account account, final java.lang.String str) {
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "isCredentialsUpdateSuggested: " + account + ", response " + iAccountManagerResponse + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new java.lang.IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account is null");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("status token is empty");
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            new com.android.server.accounts.AccountManagerService.Session(getUserAccounts(callingUserId), iAccountManagerResponse, account.type, false, false, account.name, false) { // from class: com.android.server.accounts.AccountManagerService.15
                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    return super.toDebugString(j) + ", isCredentialsUpdateSuggested, " + account.toSafeString();
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    this.mAuthenticator.isCredentialsUpdateSuggested(this, account, str);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public void onResult(android.os.Bundle bundle) {
                    android.os.Bundle.setDefusable(bundle, true);
                    android.accounts.IAccountManagerResponse responseAndClose = getResponseAndClose();
                    if (responseAndClose == null) {
                        return;
                    }
                    if (bundle == null) {
                        com.android.server.accounts.AccountManagerService.this.sendErrorResponse(responseAndClose, 5, "null bundle");
                        return;
                    }
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                    }
                    if (bundle.getInt("errorCode", -1) > 0) {
                        com.android.server.accounts.AccountManagerService.this.sendErrorResponse(responseAndClose, bundle.getInt("errorCode"), bundle.getString("errorMessage"));
                    } else {
                        if (!bundle.containsKey("booleanResult")) {
                            com.android.server.accounts.AccountManagerService.this.sendErrorResponse(responseAndClose, 5, "no result in response");
                            return;
                        }
                        android.os.Bundle bundle2 = new android.os.Bundle();
                        bundle2.putBoolean("booleanResult", bundle.getBoolean("booleanResult", false));
                        com.android.server.accounts.AccountManagerService.this.sendResponse(responseAndClose, bundle2);
                    }
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void editProperties(android.accounts.IAccountManagerResponse iAccountManagerResponse, final java.lang.String str, boolean z) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "editProperties: accountType " + str + ", response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new java.lang.IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("accountType is null");
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(str, callingUid, callingUserId) && !isSystemUid(callingUid)) {
            throw new java.lang.SecurityException(java.lang.String.format("uid %s cannot edit authenticator properites for account type: %s", java.lang.Integer.valueOf(callingUid), str));
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            new com.android.server.accounts.AccountManagerService.Session(getUserAccounts(callingUserId), iAccountManagerResponse, str, z, true, null, false) { // from class: com.android.server.accounts.AccountManagerService.16
                @Override // com.android.server.accounts.AccountManagerService.Session
                public void run() throws android.os.RemoteException {
                    this.mAuthenticator.editProperties(this, this.mAccountType);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                protected java.lang.String toDebugString(long j) {
                    return super.toDebugString(j) + ", editProperties, accountType " + str;
                }
            }.bind();
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean hasAccountAccess(@android.annotation.NonNull android.accounts.Account account, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        if (android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) != 1000) {
            throw new java.lang.SecurityException("Can be called only by system UID");
        }
        java.util.Objects.requireNonNull(account, "account cannot be null");
        java.util.Objects.requireNonNull(str, "packageName cannot be null");
        java.util.Objects.requireNonNull(userHandle, "userHandle cannot be null");
        int identifier = userHandle.getIdentifier();
        com.android.internal.util.Preconditions.checkArgumentInRange(identifier, 0, Integer.MAX_VALUE, "user must be concrete");
        try {
            return hasAccountAccess(account, str, this.mPackageManager.getPackageUidAsUser(str, identifier));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "hasAccountAccess#Package not found " + e.getMessage());
            return false;
        }
    }

    private java.lang.String getPackageNameForUid(int i) {
        int i2;
        java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
        if (com.android.internal.util.ArrayUtils.isEmpty(packagesForUid)) {
            return null;
        }
        java.lang.String str = packagesForUid[0];
        if (packagesForUid.length == 1) {
            return str;
        }
        int i3 = Integer.MAX_VALUE;
        for (java.lang.String str2 : packagesForUid) {
            try {
                android.content.pm.ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str2, 0);
                if (applicationInfo != null && (i2 = applicationInfo.targetSdkVersion) < i3) {
                    str = str2;
                    i3 = i2;
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasAccountAccess(@android.annotation.NonNull android.accounts.Account account, @android.annotation.Nullable java.lang.String str, int i) {
        if (str == null && (str = getPackageNameForUid(i)) == null) {
            return false;
        }
        if (permissionIsGranted(account, null, i, android.os.UserHandle.getUserId(i))) {
            return true;
        }
        int intValue = resolveAccountVisibility(account, str, getUserAccounts(android.os.UserHandle.getUserId(i))).intValue();
        return intValue == 1 || intValue == 2;
    }

    public android.content.IntentSender createRequestAccountAccessIntentSenderAsUser(@android.annotation.NonNull android.accounts.Account account, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        if (android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) != 1000) {
            throw new java.lang.SecurityException("Can be called only by system UID");
        }
        java.util.Objects.requireNonNull(account, "account cannot be null");
        java.util.Objects.requireNonNull(str, "packageName cannot be null");
        java.util.Objects.requireNonNull(userHandle, "userHandle cannot be null");
        int identifier = userHandle.getIdentifier();
        com.android.internal.util.Preconditions.checkArgumentInRange(identifier, 0, Integer.MAX_VALUE, "user must be concrete");
        try {
            android.content.Intent newRequestAccountAccessIntent = newRequestAccountAccessIntent(account, str, this.mPackageManager.getPackageUidAsUser(str, identifier), null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.app.PendingIntent.getActivityAsUser(this.mContext, 0, newRequestAccountAccessIntent, 1409286144, null, new android.os.UserHandle(identifier)).getIntentSender();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Unknown package " + str);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.Intent newRequestAccountAccessIntent(final android.accounts.Account account, java.lang.String str, final int i, final android.os.RemoteCallback remoteCallback) {
        return newGrantCredentialsPermissionIntent(account, str, i, new android.accounts.AccountAuthenticatorResponse((android.accounts.IAccountAuthenticatorResponse) new android.accounts.IAccountAuthenticatorResponse.Stub() { // from class: com.android.server.accounts.AccountManagerService.17
            public void onResult(android.os.Bundle bundle) throws android.os.RemoteException {
                handleAuthenticatorResponse(true);
            }

            public void onRequestContinued() {
            }

            public void onError(int i2, java.lang.String str2) throws android.os.RemoteException {
                handleAuthenticatorResponse(false);
            }

            private void handleAuthenticatorResponse(boolean z) throws android.os.RemoteException {
                com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = com.android.server.accounts.AccountManagerService.this.getUserAccounts(android.os.UserHandle.getUserId(i));
                com.android.server.accounts.AccountManagerService.this.cancelNotification(com.android.server.accounts.AccountManagerService.this.getCredentialPermissionNotificationId(account, "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", i, userAccounts), userAccounts);
                if (remoteCallback != null) {
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putBoolean("booleanResult", z);
                    remoteCallback.sendResult(bundle);
                }
            }
        }), "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", false);
    }

    public boolean someUserHasAccount(@android.annotation.NonNull android.accounts.Account account) {
        if (!android.os.UserHandle.isSameApp(1000, android.os.Binder.getCallingUid())) {
            throw new java.lang.SecurityException("Only system can check for accounts across users");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.accounts.AccountAndUser[] allAccountsForSystemProcess = getAllAccountsForSystemProcess();
            for (int length = allAccountsForSystemProcess.length - 1; length >= 0; length--) {
                if (allAccountsForSystemProcess[length].account.equals(account)) {
                    return true;
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private class GetAccountsByTypeAndFeatureSession extends com.android.server.accounts.AccountManagerService.Session {
        private volatile android.accounts.Account[] mAccountsOfType;
        private volatile java.util.ArrayList<android.accounts.Account> mAccountsWithFeatures;
        private final int mCallingUid;
        private volatile int mCurrentAccount;
        private final java.lang.String[] mFeatures;
        private final boolean mIncludeManagedNotVisible;
        private final java.lang.String mPackageName;

        public GetAccountsByTypeAndFeatureSession(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String[] strArr, int i, java.lang.String str2, boolean z) {
            super(com.android.server.accounts.AccountManagerService.this, userAccounts, iAccountManagerResponse, str, false, true, null, false);
            this.mAccountsOfType = null;
            this.mAccountsWithFeatures = null;
            this.mCurrentAccount = 0;
            this.mCallingUid = i;
            this.mFeatures = strArr;
            this.mPackageName = str2;
            this.mIncludeManagedNotVisible = z;
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void run() throws android.os.RemoteException {
            this.mAccountsOfType = com.android.server.accounts.AccountManagerService.this.getAccountsFromCache(this.mAccounts, this.mAccountType, this.mCallingUid, this.mPackageName, this.mIncludeManagedNotVisible);
            this.mAccountsWithFeatures = new java.util.ArrayList<>(this.mAccountsOfType.length);
            this.mCurrentAccount = 0;
            checkAccount();
        }

        public void checkAccount() {
            if (this.mCurrentAccount >= this.mAccountsOfType.length) {
                sendResult();
                return;
            }
            android.accounts.IAccountAuthenticator iAccountAuthenticator = this.mAuthenticator;
            if (iAccountAuthenticator == null) {
                if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                    android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "checkAccount: aborting session since we are no longer connected to the authenticator, " + toDebugString());
                    return;
                }
                return;
            }
            try {
                iAccountAuthenticator.hasFeatures(this, this.mAccountsOfType[this.mCurrentAccount], this.mFeatures);
            } catch (android.os.RemoteException e) {
                onError(1, "remote exception");
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void onResult(android.os.Bundle bundle) {
            android.os.Bundle.setDefusable(bundle, true);
            this.mNumResults++;
            if (bundle == null) {
                onError(5, "null bundle");
                return;
            }
            if (bundle.getBoolean("booleanResult", false)) {
                this.mAccountsWithFeatures.add(this.mAccountsOfType[this.mCurrentAccount]);
            }
            this.mCurrentAccount++;
            checkAccount();
        }

        public void sendResult() {
            android.accounts.IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    int size = this.mAccountsWithFeatures.size();
                    android.accounts.Account[] accountArr = new android.accounts.Account[size];
                    for (int i = 0; i < size; i++) {
                        accountArr[i] = this.mAccountsWithFeatures.get(i);
                    }
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                    }
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putParcelableArray("accounts", accountArr);
                    responseAndClose.onResult(bundle);
                } catch (android.os.RemoteException e) {
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "failure while notifying response", e);
                    }
                }
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        protected java.lang.String toDebugString(long j) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(super.toDebugString(j));
            sb.append(", getAccountsByTypeAndFeatures, ");
            sb.append(this.mFeatures != null ? android.text.TextUtils.join(",", this.mFeatures) : null);
            return sb.toString();
        }
    }

    @android.annotation.NonNull
    public android.accounts.Account[] getAccounts(int i, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        this.mAppOpsManager.checkPackage(callingUid, str);
        java.util.List<java.lang.String> typesVisibleToCaller = getTypesVisibleToCaller(callingUid, i, str);
        if (typesVisibleToCaller.isEmpty()) {
            return EMPTY_ACCOUNT_ARRAY;
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return getAccountsInternal(getUserAccounts(i), callingUid, str, typesVisibleToCaller, false);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    public android.accounts.AccountAndUser[] getRunningAccountsForSystem() {
        try {
            return getAccountsForSystem(android.app.ActivityManager.getService().getRunningUserIds());
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.NonNull
    public android.accounts.AccountAndUser[] getAllAccountsForSystemProcess() {
        java.util.List aliveUsers = getUserManager().getAliveUsers();
        int size = aliveUsers.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((android.content.pm.UserInfo) aliveUsers.get(i)).id;
        }
        return getAccountsForSystem(iArr);
    }

    @android.annotation.NonNull
    private android.accounts.AccountAndUser[] getAccountsForSystem(int[] iArr) {
        java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
        for (int i : iArr) {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(i);
            if (userAccounts != null) {
                for (android.accounts.Account account : getAccountsFromCache(userAccounts, null, android.os.Binder.getCallingUid(), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, false)) {
                    newArrayList.add(new android.accounts.AccountAndUser(account, i));
                }
            }
        }
        return (android.accounts.AccountAndUser[]) newArrayList.toArray(new android.accounts.AccountAndUser[newArrayList.size()]);
    }

    @android.annotation.NonNull
    public android.accounts.Account[] getAccountsAsUser(java.lang.String str, int i, java.lang.String str2) {
        this.mAppOpsManager.checkPackage(android.os.Binder.getCallingUid(), str2);
        return getAccountsAsUserForPackage(str, i, str2, -1, str2, false);
    }

    @android.annotation.NonNull
    private android.accounts.Account[] getAccountsOrEmptyArray(java.lang.String str, int i, java.lang.String str2) {
        try {
            return getAccountsAsUser(str, i, str2);
        } catch (android.database.sqlite.SQLiteCantOpenDatabaseException e) {
            android.util.Log.w(TAG, "Could not get accounts for user " + i, e);
            return new android.accounts.Account[0];
        }
    }

    @android.annotation.NonNull
    private android.accounts.Account[] getAccountsAsUserForPackage(java.lang.String str, int i, java.lang.String str2, int i2, java.lang.String str3, boolean z) {
        int callingUid = android.os.Binder.getCallingUid();
        if (i != android.os.UserHandle.getCallingUserId() && callingUid != 1000 && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
            throw new java.lang.SecurityException("User " + android.os.UserHandle.getCallingUserId() + " trying to get account for " + i);
        }
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "getAccounts: accountType " + str + ", caller's uid " + android.os.Binder.getCallingUid() + ", pid " + android.os.Binder.getCallingPid());
        }
        java.util.List<java.lang.String> typesManagedByCaller = getTypesManagedByCaller(callingUid, android.os.UserHandle.getUserId(callingUid));
        if (i2 == -1 || (!android.os.UserHandle.isSameApp(callingUid, 1000) && (str == null || !typesManagedByCaller.contains(str)))) {
            str2 = str3;
            i2 = callingUid;
        }
        java.util.List<java.lang.String> typesVisibleToCaller = getTypesVisibleToCaller(i2, i, str2);
        if (typesVisibleToCaller.isEmpty() || (str != null && !typesVisibleToCaller.contains(str))) {
            return EMPTY_ACCOUNT_ARRAY;
        }
        if (typesVisibleToCaller.contains(str)) {
            typesVisibleToCaller = new java.util.ArrayList<>();
            typesVisibleToCaller.add(str);
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            return getAccountsInternal(getUserAccounts(i), i2, str2, typesVisibleToCaller, z);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    private android.accounts.Account[] getAccountsInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, int i, java.lang.String str, java.util.List<java.lang.String> list, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            android.accounts.Account[] accountsFromCache = getAccountsFromCache(userAccounts, it.next(), i, str, z);
            if (accountsFromCache != null) {
                arrayList.addAll(java.util.Arrays.asList(accountsFromCache));
            }
        }
        android.accounts.Account[] accountArr = new android.accounts.Account[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            accountArr[i2] = (android.accounts.Account) arrayList.get(i2);
        }
        return accountArr;
    }

    public void addSharedAccountsFromParentUser(int i, int i2, java.lang.String str) {
        checkManageOrCreateUsersPermission("addSharedAccountsFromParentUser");
        for (android.accounts.Account account : getAccountsOrEmptyArray(null, i, str)) {
            addSharedAccountAsUser(account, i2);
        }
    }

    private boolean addSharedAccountAsUser(android.accounts.Account account, int i) {
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(handleIncomingUser(i));
        userAccounts.accountsDb.deleteSharedAccount(account);
        long insertSharedAccount = userAccounts.accountsDb.insertSharedAccount(account);
        if (insertSharedAccount < 0) {
            android.util.Log.w(TAG, "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping the DB insert failed");
            return false;
        }
        logRecord(com.android.server.accounts.AccountsDb.DEBUG_ACTION_ACCOUNT_ADD, "shared_accounts", insertSharedAccount, userAccounts);
        return true;
    }

    public boolean renameSharedAccountAsUser(android.accounts.Account account, java.lang.String str, int i) {
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(handleIncomingUser(i));
        long findSharedAccountId = userAccounts.accountsDb.findSharedAccountId(account);
        int renameSharedAccount = userAccounts.accountsDb.renameSharedAccount(account, str);
        if (renameSharedAccount > 0) {
            logRecord(com.android.server.accounts.AccountsDb.DEBUG_ACTION_ACCOUNT_RENAME, "shared_accounts", findSharedAccountId, userAccounts, android.accounts.IAccountManager.Stub.getCallingUid());
            renameAccountInternal(userAccounts, account, str);
        }
        return renameSharedAccount > 0;
    }

    public boolean removeSharedAccountAsUser(android.accounts.Account account, int i) {
        return removeSharedAccountAsUser(account, i, android.accounts.IAccountManager.Stub.getCallingUid());
    }

    private boolean removeSharedAccountAsUser(android.accounts.Account account, int i, int i2) {
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(handleIncomingUser(i));
        long findSharedAccountId = userAccounts.accountsDb.findSharedAccountId(account);
        boolean deleteSharedAccount = userAccounts.accountsDb.deleteSharedAccount(account);
        if (deleteSharedAccount) {
            logRecord(com.android.server.accounts.AccountsDb.DEBUG_ACTION_ACCOUNT_REMOVE, "shared_accounts", findSharedAccountId, userAccounts, i2);
            removeAccountInternal(userAccounts, account, i2);
        }
        return deleteSharedAccount;
    }

    public android.accounts.Account[] getSharedAccountsAsUser(int i) {
        android.accounts.Account[] accountArr;
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(handleIncomingUser(i));
        synchronized (userAccounts.dbLock) {
            java.util.List<android.accounts.Account> sharedAccounts = userAccounts.accountsDb.getSharedAccounts();
            accountArr = new android.accounts.Account[sharedAccounts.size()];
            sharedAccounts.toArray(accountArr);
        }
        return accountArr;
    }

    @android.annotation.NonNull
    public android.accounts.Account[] getAccountsForPackage(java.lang.String str, int i, java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        if (!android.os.UserHandle.isSameApp(callingUid, 1000)) {
            throw new java.lang.SecurityException("getAccountsForPackage() called from unauthorized uid " + callingUid + " with uid=" + i);
        }
        return getAccountsAsUserForPackage(null, android.os.UserHandle.getCallingUserId(), str, i, str2, true);
    }

    @android.annotation.NonNull
    public android.accounts.Account[] getAccountsByTypeForPackage(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mAppOpsManager.checkPackage(callingUid, str3);
        try {
            int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str2, callingUserId);
            if (!android.os.UserHandle.isSameApp(callingUid, 1000) && str != null && !isAccountManagedByCaller(str, callingUid, callingUserId)) {
                return EMPTY_ACCOUNT_ARRAY;
            }
            if (!android.os.UserHandle.isSameApp(callingUid, 1000) && str == null) {
                return getAccountsAsUserForPackage(str, callingUserId, str2, packageUidAsUser, str3, false);
            }
            return getAccountsAsUserForPackage(str, callingUserId, str2, packageUidAsUser, str3, true);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Couldn't determine the packageUid for " + str2 + e);
            return EMPTY_ACCOUNT_ARRAY;
        }
    }

    private boolean needToStartChooseAccountActivity(android.accounts.Account[] accountArr, java.lang.String str) {
        if (accountArr.length < 1) {
            return false;
        }
        return accountArr.length > 1 || resolveAccountVisibility(accountArr[0], str, getUserAccounts(android.os.UserHandle.getCallingUserId())).intValue() == 4;
    }

    private void startChooseAccountActivityWithAccounts(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account[] accountArr, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(this.mContext, (java.lang.Class<?>) android.accounts.ChooseAccountActivity.class);
        intent.putExtra("accounts", accountArr);
        intent.putExtra("accountManagerResponse", (android.os.Parcelable) new android.accounts.AccountManagerResponse(iAccountManagerResponse));
        intent.putExtra("androidPackageName", str);
        this.mContext.startActivityAsUser(intent, android.os.UserHandle.of(android.os.UserHandle.getCallingUserId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleGetAccountsResult(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.accounts.Account[] accountArr, java.lang.String str) {
        if (needToStartChooseAccountActivity(accountArr, str)) {
            startChooseAccountActivityWithAccounts(iAccountManagerResponse, accountArr, str);
            return;
        }
        if (accountArr.length == 1) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString("authAccount", accountArr[0].name);
            bundle.putString("accountType", accountArr[0].type);
            onResult(iAccountManagerResponse, bundle);
            return;
        }
        onResult(iAccountManagerResponse, new android.os.Bundle());
    }

    public void getAccountByTypeAndFeatures(final android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String[] strArr, final java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        this.mAppOpsManager.checkPackage(callingUid, str2);
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "getAccount: accountType " + str + ", response " + iAccountManagerResponse + ", features " + java.util.Arrays.toString(strArr) + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new java.lang.IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("accountType is null");
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(callingUserId);
            if (com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
                handleGetAccountsResult(iAccountManagerResponse, getAccountsFromCache(userAccounts, str, callingUid, str2, true), str2);
            } else {
                new com.android.server.accounts.AccountManagerService.GetAccountsByTypeAndFeatureSession(userAccounts, new android.accounts.IAccountManagerResponse.Stub() { // from class: com.android.server.accounts.AccountManagerService.18
                    public void onResult(android.os.Bundle bundle) throws android.os.RemoteException {
                        android.os.Parcelable[] parcelableArray = bundle.getParcelableArray("accounts");
                        android.accounts.Account[] accountArr = new android.accounts.Account[parcelableArray.length];
                        for (int i = 0; i < parcelableArray.length; i++) {
                            accountArr[i] = (android.accounts.Account) parcelableArray[i];
                        }
                        com.android.server.accounts.AccountManagerService.this.handleGetAccountsResult(iAccountManagerResponse, accountArr, str2);
                    }

                    public void onError(int i, java.lang.String str3) throws android.os.RemoteException {
                    }
                }, str, strArr, callingUid, str2, true).bind();
            }
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getAccountsByFeatures(android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, java.lang.String[] strArr, java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        this.mAppOpsManager.checkPackage(callingUid, str2);
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "getAccounts: accountType " + str + ", response " + iAccountManagerResponse + ", features " + java.util.Arrays.toString(strArr) + ", caller's uid " + callingUid + ", pid " + android.os.Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new java.lang.IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("accountType is null");
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!getTypesVisibleToCaller(callingUid, callingUserId, str2).contains(str)) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelableArray("accounts", EMPTY_ACCOUNT_ARRAY);
            try {
                iAccountManagerResponse.onResult(bundle);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Cannot respond to caller do to exception.", e);
                return;
            }
        }
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(callingUserId);
            if (strArr != null && strArr.length != 0) {
                new com.android.server.accounts.AccountManagerService.GetAccountsByTypeAndFeatureSession(userAccounts, iAccountManagerResponse, str, strArr, callingUid, str2, false).bind();
                return;
            }
            android.accounts.Account[] accountsFromCache = getAccountsFromCache(userAccounts, str, callingUid, str2, false);
            android.os.Bundle bundle2 = new android.os.Bundle();
            bundle2.putParcelableArray("accounts", accountsFromCache);
            onResult(iAccountManagerResponse, bundle2);
        } finally {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onAccountAccessed(java.lang.String str) throws android.os.RemoteException {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.os.UserHandle.getAppId(callingUid) == 1000) {
            return;
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            for (android.accounts.Account account : getAccounts(callingUserId, this.mContext.getOpPackageName())) {
                if (java.util.Objects.equals(account.getAccessId(), str) && !hasAccountAccess(account, (java.lang.String) null, callingUid)) {
                    updateAppPermission(account, "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", callingUid, true);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.accounts.AccountManagerServiceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    private abstract class Session extends android.accounts.IAccountAuthenticatorResponse.Stub implements android.os.IBinder.DeathRecipient, android.content.ServiceConnection {
        final java.lang.String mAccountName;
        final java.lang.String mAccountType;
        protected final com.android.server.accounts.AccountManagerService.UserAccounts mAccounts;
        final boolean mAuthDetailsRequired;
        android.accounts.IAccountAuthenticator mAuthenticator;
        protected boolean mCanStartAccountManagerActivity;
        final long mCreationTime;
        final boolean mExpectActivityLaunch;
        private int mNumErrors;
        private int mNumRequestContinued;
        public int mNumResults;
        android.accounts.IAccountManagerResponse mResponse;
        private final java.lang.Object mSessionLock;
        private final boolean mStripAuthTokenFromResult;
        final boolean mUpdateLastAuthenticatedTime;

        public abstract void run() throws android.os.RemoteException;

        public Session(com.android.server.accounts.AccountManagerService accountManagerService, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, boolean z, boolean z2, java.lang.String str2, boolean z3) {
            this(userAccounts, iAccountManagerResponse, str, z, z2, str2, z3, false);
        }

        public Session(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.IAccountManagerResponse iAccountManagerResponse, java.lang.String str, boolean z, boolean z2, java.lang.String str2, boolean z3, boolean z4) {
            this.mSessionLock = new java.lang.Object();
            this.mNumResults = 0;
            this.mNumRequestContinued = 0;
            this.mNumErrors = 0;
            this.mAuthenticator = null;
            this.mCanStartAccountManagerActivity = false;
            if (str == null) {
                throw new java.lang.IllegalArgumentException("accountType is null");
            }
            this.mAccounts = userAccounts;
            this.mStripAuthTokenFromResult = z2;
            this.mAccountType = str;
            this.mExpectActivityLaunch = z;
            this.mCreationTime = android.os.SystemClock.elapsedRealtime();
            this.mAccountName = str2;
            this.mAuthDetailsRequired = z3;
            this.mUpdateLastAuthenticatedTime = z4;
            synchronized (com.android.server.accounts.AccountManagerService.this.mSessions) {
                com.android.server.accounts.AccountManagerService.this.mSessions.put(toString(), this);
            }
            scheduleTimeout();
            if (iAccountManagerResponse != null) {
                try {
                    iAccountManagerResponse.asBinder().linkToDeath(this, 0);
                    this.mResponse = iAccountManagerResponse;
                } catch (android.os.RemoteException e) {
                    binderDied();
                }
            }
        }

        android.accounts.IAccountManagerResponse getResponseAndClose() {
            if (this.mResponse == null) {
                close();
                return null;
            }
            android.accounts.IAccountManagerResponse iAccountManagerResponse = this.mResponse;
            close();
            return iAccountManagerResponse;
        }

        protected boolean checkKeyIntent(int i, android.os.Bundle bundle) {
            if (!checkKeyIntentParceledCorrectly(bundle)) {
                android.util.EventLog.writeEvent(1397638484, "250588548", java.lang.Integer.valueOf(i), "");
                return false;
            }
            android.content.Intent intent = (android.content.Intent) bundle.getParcelable("intent", android.content.Intent.class);
            if (intent == null) {
                return true;
            }
            if (intent.getClipData() == null) {
                intent.setClipData(android.content.ClipData.newPlainText(null, null));
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.pm.ResolveInfo resolveActivityAsUser = com.android.server.accounts.AccountManagerService.this.mContext.getPackageManager().resolveActivityAsUser(intent, 0, this.mAccounts.userId);
                if (resolveActivityAsUser == null) {
                    return false;
                }
                android.content.pm.ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
                int i2 = activityInfo.applicationInfo.uid;
                android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
                if (isExportedSystemActivity(activityInfo) || packageManagerInternal.hasSignatureCapability(i2, i, 16)) {
                    return true;
                }
                android.util.Log.e(com.android.server.accounts.AccountManagerService.TAG, java.lang.String.format("KEY_INTENT resolved to an Activity (%s) in a package (%s) that does not share a signature with the supplying authenticator (%s).", activityInfo.name, activityInfo.packageName, this.mAccountType));
                return false;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private boolean checkKeyIntentParceledCorrectly(android.os.Bundle bundle) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            obtain.writeBundle(bundle);
            obtain.setDataPosition(0);
            android.os.Bundle readBundle = obtain.readBundle();
            obtain.recycle();
            android.content.Intent intent = (android.content.Intent) bundle.getParcelable("intent", android.content.Intent.class);
            if (intent != null && intent.getClass() != android.content.Intent.class) {
                return false;
            }
            android.content.Intent intent2 = (android.content.Intent) readBundle.getParcelable("intent", android.content.Intent.class);
            if (intent == null) {
                if (intent2 != null) {
                    return false;
                }
                return true;
            }
            if (!intent.filterEquals(intent2) || intent.getSelector() != intent2.getSelector() || (intent2.getFlags() & 195) != 0) {
                return false;
            }
            return true;
        }

        private boolean isExportedSystemActivity(android.content.pm.ActivityInfo activityInfo) {
            java.lang.String str = activityInfo.name;
            if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(activityInfo.packageName)) {
                return (this.mCanStartAccountManagerActivity && android.accounts.GrantCredentialsPermissionActivity.class.getName().equals(str)) || android.accounts.CantAddAccountActivity.class.getName().equals(str);
            }
            return false;
        }

        private void close() {
            synchronized (com.android.server.accounts.AccountManagerService.this.mSessions) {
                try {
                    if (com.android.server.accounts.AccountManagerService.this.mSessions.remove(toString()) == null) {
                        return;
                    }
                    if (this.mResponse != null) {
                        this.mResponse.asBinder().unlinkToDeath(this, 0);
                        this.mResponse = null;
                    }
                    cancelTimeout();
                    unbind();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mResponse = null;
            close();
        }

        protected java.lang.String toDebugString() {
            return toDebugString(android.os.SystemClock.elapsedRealtime());
        }

        protected java.lang.String toDebugString(long j) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Session: expectLaunch ");
            sb.append(this.mExpectActivityLaunch);
            sb.append(", connected ");
            sb.append(this.mAuthenticator != null);
            sb.append(", stats (");
            sb.append(this.mNumResults);
            sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            sb.append(this.mNumRequestContinued);
            sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            sb.append(this.mNumErrors);
            sb.append("), lifetime ");
            sb.append((j - this.mCreationTime) / 1000.0d);
            return sb.toString();
        }

        void bind() {
            if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "initiating bind to authenticator type " + this.mAccountType);
            }
            if (!bindToAuthenticator(this.mAccountType)) {
                android.util.Log.w(com.android.server.accounts.AccountManagerService.TAG, "bind attempt failed for " + toDebugString());
                onError(1, "bind failure");
            }
        }

        private void unbind() {
            synchronized (this.mSessionLock) {
                try {
                    if (this.mAuthenticator != null) {
                        this.mAuthenticator = null;
                        com.android.server.accounts.AccountManagerService.this.mContext.unbindService(this);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void scheduleTimeout() {
            com.android.server.accounts.AccountManagerService.this.mHandler.sendMessageDelayed(com.android.server.accounts.AccountManagerService.this.mHandler.obtainMessage(3, this), 900000L);
        }

        public void cancelTimeout() {
            com.android.server.accounts.AccountManagerService.this.mHandler.removeMessages(3, this);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (this.mSessionLock) {
                this.mAuthenticator = android.accounts.IAccountAuthenticator.Stub.asInterface(iBinder);
                try {
                    run();
                } catch (android.os.RemoteException e) {
                    onError(1, "remote exception");
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.accounts.IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    responseAndClose.onError(1, "disconnected");
                } catch (android.os.RemoteException e) {
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "Session.onServiceDisconnected: caught RemoteException while responding", e);
                    }
                }
            }
        }

        public void onTimedOut() {
            android.accounts.IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "Session.onTimedOut");
            }
            if (responseAndClose != null) {
                try {
                    responseAndClose.onError(1, "timeout");
                } catch (android.os.RemoteException e) {
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "Session.onTimedOut: caught RemoteException while responding", e);
                    }
                }
            }
        }

        public void onResult(android.os.Bundle bundle) {
            android.accounts.IAccountManagerResponse responseAndClose;
            long j;
            boolean z = true;
            android.os.Bundle.setDefusable(bundle, true);
            this.mNumResults++;
            if (bundle != null) {
                boolean z2 = bundle.getBoolean("booleanResult", false);
                boolean z3 = bundle.containsKey("authAccount") && bundle.containsKey("accountType");
                if (!this.mUpdateLastAuthenticatedTime || (!z2 && !z3)) {
                    z = false;
                }
                if (z || this.mAuthDetailsRequired) {
                    boolean isAccountPresentForCaller = com.android.server.accounts.AccountManagerService.this.isAccountPresentForCaller(this.mAccountName, this.mAccountType);
                    if (z && isAccountPresentForCaller) {
                        com.android.server.accounts.AccountManagerService.this.updateLastAuthenticatedTime(new android.accounts.Account(this.mAccountName, this.mAccountType));
                    }
                    if (this.mAuthDetailsRequired) {
                        if (!isAccountPresentForCaller) {
                            j = -1;
                        } else {
                            j = this.mAccounts.accountsDb.findAccountLastAuthenticatedTime(new android.accounts.Account(this.mAccountName, this.mAccountType));
                        }
                        bundle.putLong("lastAuthenticatedTime", j);
                    }
                }
            }
            if (bundle != null && !checkKeyIntent(android.os.Binder.getCallingUid(), bundle)) {
                onError(5, "invalid intent in bundle returned");
                return;
            }
            if (bundle != null && !android.text.TextUtils.isEmpty(bundle.getString("authtoken"))) {
                java.lang.String string = bundle.getString("authAccount");
                java.lang.String string2 = bundle.getString("accountType");
                if (!android.text.TextUtils.isEmpty(string) && !android.text.TextUtils.isEmpty(string2)) {
                    com.android.server.accounts.AccountManagerService.this.cancelNotification(com.android.server.accounts.AccountManagerService.this.getSigninRequiredNotificationId(this.mAccounts, new android.accounts.Account(string, string2)), this.mAccounts);
                }
            }
            if (this.mExpectActivityLaunch && bundle != null && bundle.containsKey("intent")) {
                responseAndClose = this.mResponse;
            } else {
                responseAndClose = getResponseAndClose();
            }
            if (responseAndClose != null) {
                try {
                    if (bundle == null) {
                        if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                            android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, getClass().getSimpleName() + " calling onError() on response " + responseAndClose);
                        }
                        responseAndClose.onError(5, "null bundle returned");
                        return;
                    }
                    if (this.mStripAuthTokenFromResult) {
                        bundle.remove("authtoken");
                        if (!checkKeyIntent(android.os.Binder.getCallingUid(), bundle)) {
                            onError(5, "invalid intent in bundle returned");
                            return;
                        }
                    }
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                    }
                    if (bundle.getInt("errorCode", -1) > 0) {
                        responseAndClose.onError(bundle.getInt("errorCode"), bundle.getString("errorMessage"));
                    } else {
                        responseAndClose.onResult(bundle);
                    }
                } catch (android.os.RemoteException e) {
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "failure while notifying response", e);
                    }
                }
            }
        }

        public void onRequestContinued() {
            this.mNumRequestContinued++;
        }

        public void onError(int i, java.lang.String str) {
            this.mNumErrors++;
            android.accounts.IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                    android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, getClass().getSimpleName() + " calling onError() on response " + responseAndClose);
                }
                try {
                    responseAndClose.onError(i, str);
                    return;
                } catch (android.os.RemoteException e) {
                    if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                        android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "Session.onError: caught RemoteException while responding", e);
                        return;
                    }
                    return;
                }
            }
            if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "Session.onError: already closed");
            }
        }

        private boolean bindToAuthenticator(java.lang.String str) {
            long j;
            android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription> serviceInfo = com.android.server.accounts.AccountManagerService.this.mAuthenticatorCache.getServiceInfo(android.accounts.AuthenticatorDescription.newKey(str), this.mAccounts.userId);
            if (serviceInfo == null) {
                android.util.Log.w(com.android.server.accounts.AccountManagerService.TAG, "there is no authenticator for " + str + ", bailing out");
                return false;
            }
            if (!com.android.server.accounts.AccountManagerService.this.isLocalUnlockedUser(this.mAccounts.userId) && !serviceInfo.componentInfo.directBootAware) {
                android.util.Slog.w(com.android.server.accounts.AccountManagerService.TAG, "Blocking binding to authenticator " + serviceInfo.componentName + " which isn't encryption aware");
                return false;
            }
            android.content.Intent intent = new android.content.Intent();
            intent.setAction("android.accounts.AccountAuthenticator");
            intent.setComponent(serviceInfo.componentName);
            if (android.util.Log.isLoggable(com.android.server.accounts.AccountManagerService.TAG, 2)) {
                android.util.Log.v(com.android.server.accounts.AccountManagerService.TAG, "performing bindService to " + serviceInfo.componentName);
            }
            if (!com.android.server.accounts.AccountManagerService.this.mAuthenticatorCache.getBindInstantServiceAllowed(this.mAccounts.userId)) {
                j = 1;
            } else {
                j = 4194305;
            }
            if (!com.android.server.accounts.AccountManagerService.this.mContext.bindServiceAsUser(intent, this, android.content.Context.BindServiceFlags.of(j), android.os.UserHandle.of(this.mAccounts.userId))) {
                android.util.Log.w(com.android.server.accounts.AccountManagerService.TAG, "bindService to " + serviceInfo.componentName + " failed");
                com.android.server.accounts.AccountManagerService.this.mContext.unbindService(this);
                return false;
            }
            return true;
        }
    }

    class MessageHandler extends android.os.Handler {
        MessageHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 3:
                    ((com.android.server.accounts.AccountManagerService.Session) message.obj).onTimedOut();
                    return;
                case 4:
                    com.android.server.accounts.AccountManagerService.this.copyAccountToUser(null, (android.accounts.Account) message.obj, message.arg1, message.arg2);
                    return;
                default:
                    throw new java.lang.IllegalStateException("unhandled message: " + message.what);
            }
        }
    }

    private void logRecord(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, java.lang.String str, java.lang.String str2) {
        logRecord(str, str2, -1L, userAccounts);
    }

    private void logRecordWithUid(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, java.lang.String str, java.lang.String str2, int i) {
        logRecord(str, str2, -1L, userAccounts, i);
    }

    private void logRecord(java.lang.String str, java.lang.String str2, long j, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        logRecord(str, str2, j, userAccounts, android.accounts.IAccountManager.Stub.getCallingUid());
    }

    private void logRecord(java.lang.String str, java.lang.String str2, long j, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, int i) {
        long reserveDebugDbInsertionPoint = userAccounts.accountsDb.reserveDebugDbInsertionPoint();
        if (reserveDebugDbInsertionPoint != -1) {
            this.mHandler.post(new java.lang.Runnable(str, str2, j, userAccounts, i, reserveDebugDbInsertionPoint) { // from class: com.android.server.accounts.AccountManagerService.1LogRecordTask
                private final long accountId;
                private final java.lang.String action;
                private final int callingUid;
                private final java.lang.String tableName;
                private final com.android.server.accounts.AccountManagerService.UserAccounts userAccount;
                private final long userDebugDbInsertionPoint;

                {
                    this.action = str;
                    this.tableName = str2;
                    this.accountId = j;
                    this.userAccount = userAccounts;
                    this.callingUid = i;
                    this.userDebugDbInsertionPoint = reserveDebugDbInsertionPoint;
                }

                @Override // java.lang.Runnable
                public void run() {
                    synchronized (this.userAccount.accountsDb.mDebugStatementLock) {
                        try {
                            android.database.sqlite.SQLiteStatement statementForLogging = this.userAccount.accountsDb.getStatementForLogging();
                            if (statementForLogging == null) {
                                return;
                            }
                            statementForLogging.bindLong(1, this.accountId);
                            statementForLogging.bindString(2, this.action);
                            statementForLogging.bindString(3, com.android.server.accounts.AccountManagerService.this.mDateFormat.format(new java.util.Date()));
                            statementForLogging.bindLong(4, this.callingUid);
                            statementForLogging.bindString(5, this.tableName);
                            statementForLogging.bindLong(6, this.userDebugDbInsertionPoint);
                            try {
                                try {
                                    statementForLogging.execute();
                                } finally {
                                    statementForLogging.clearBindings();
                                }
                            } catch (android.database.sqlite.SQLiteFullException | java.lang.IllegalStateException e) {
                                android.util.Slog.w(com.android.server.accounts.AccountManagerService.TAG, "Failed to insert a log record. accountId=" + this.accountId + " action=" + this.action + " tableName=" + this.tableName + " Error: " + e);
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }
    }

    public android.os.IBinder onBind(android.content.Intent intent) {
        return asBinder();
    }

    private static boolean scanArgs(java.lang.String[] strArr, java.lang.String str) {
        if (strArr != null) {
            for (java.lang.String str2 : strArr) {
                if (str.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            boolean z = scanArgs(strArr, "--checkin") || scanArgs(strArr, "-c");
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            for (android.content.pm.UserInfo userInfo : getUserManager().getUsers()) {
                indentingPrintWriter.println("User " + userInfo + ":");
                indentingPrintWriter.increaseIndent();
                dumpUser(getUserAccounts(userInfo.id), fileDescriptor, indentingPrintWriter, strArr, z);
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    private void dumpUser(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
        boolean isLocalUnlockedUser;
        if (z) {
            synchronized (userAccounts.dbLock) {
                userAccounts.accountsDb.dumpDeAccountsTable(printWriter);
            }
            return;
        }
        android.accounts.Account[] accountsFromCache = getAccountsFromCache(userAccounts, null, 1000, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, false);
        printWriter.println("Accounts: " + accountsFromCache.length);
        for (android.accounts.Account account : accountsFromCache) {
            printWriter.println("  " + account.toString());
        }
        printWriter.println();
        synchronized (userAccounts.dbLock) {
            userAccounts.accountsDb.dumpDebugTable(printWriter);
        }
        printWriter.println();
        synchronized (this.mSessions) {
            try {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                printWriter.println("Active Sessions: " + this.mSessions.size());
                java.util.Iterator<com.android.server.accounts.AccountManagerService.Session> it = this.mSessions.values().iterator();
                while (it.hasNext()) {
                    printWriter.println("  " + it.next().toDebugString(elapsedRealtime));
                }
            } finally {
            }
        }
        printWriter.println();
        this.mAuthenticatorCache.dump(fileDescriptor, printWriter, strArr, userAccounts.userId);
        synchronized (this.mUsers) {
            isLocalUnlockedUser = isLocalUnlockedUser(userAccounts.userId);
        }
        if (!isLocalUnlockedUser) {
            return;
        }
        printWriter.println();
        synchronized (userAccounts.dbLock) {
            try {
                java.util.Map<android.accounts.Account, java.util.Map<java.lang.String, java.lang.Integer>> findAllVisibilityValues = userAccounts.accountsDb.findAllVisibilityValues();
                printWriter.println("Account visibility:");
                for (android.accounts.Account account2 : findAllVisibilityValues.keySet()) {
                    printWriter.println("  " + account2.name);
                    for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : findAllVisibilityValues.get(account2).entrySet()) {
                        printWriter.println("    " + entry.getKey() + ", " + entry.getValue());
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doNotification(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.CharSequence charSequence, android.content.Intent intent, java.lang.String str, int i) {
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "doNotification: " + ((java.lang.Object) charSequence) + " intent:" + intent);
            }
            if (intent.getComponent() != null && android.accounts.GrantCredentialsPermissionActivity.class.getName().equals(intent.getComponent().getClassName())) {
                createNoCredentialsPermissionNotification(account, intent, str, userAccounts);
            } else {
                android.content.Context contextForUser = getContextForUser(new android.os.UserHandle(i));
                com.android.server.accounts.AccountManagerService.NotificationId signinRequiredNotificationId = getSigninRequiredNotificationId(userAccounts, account);
                intent.addCategory(signinRequiredNotificationId.mTag);
                installNotification(signinRequiredNotificationId, new android.app.Notification.Builder(contextForUser, com.android.internal.notification.SystemNotificationChannels.ACCOUNT).setWhen(0L).setSmallIcon(android.R.drawable.stat_sys_warning).setColor(contextForUser.getColor(android.R.color.system_notification_accent_color)).setContentTitle(java.lang.String.format(contextForUser.getText(android.R.string.notification_channel_security).toString(), account.name)).setContentText(charSequence).setContentIntent(android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF, null, new android.os.UserHandle(i))).build(), str, i);
            }
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void installNotification(com.android.server.accounts.AccountManagerService.NotificationId notificationId, android.app.Notification notification, java.lang.String str, int i) {
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            try {
                this.mInjector.getNotificationManager().enqueueNotificationWithTag(str, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, notificationId.mTag, notificationId.mId, notification, i);
            } catch (android.os.RemoteException e) {
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelNotification(com.android.server.accounts.AccountManagerService.NotificationId notificationId, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        cancelNotification(notificationId, this.mContext.getPackageName(), userAccounts);
    }

    private void cancelNotification(com.android.server.accounts.AccountManagerService.NotificationId notificationId, java.lang.String str, com.android.server.accounts.AccountManagerService.UserAccounts userAccounts) {
        long clearCallingIdentity = android.accounts.IAccountManager.Stub.clearCallingIdentity();
        try {
            this.mInjector.getNotificationManager().cancelNotificationWithTag(str, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, notificationId.mTag, notificationId.mId, android.os.UserHandle.of(userAccounts.userId).getIdentifier());
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.accounts.IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
    }

    private boolean isPermittedForPackage(java.lang.String str, int i, java.lang.String... strArr) {
        int permissionToOpCode;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str, i);
            android.content.pm.IPackageManager packageManager = android.app.ActivityThread.getPackageManager();
            for (java.lang.String str2 : strArr) {
                if (packageManager.checkPermission(str2, str, i) == 0 && ((permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(str2)) == -1 || this.mAppOpsManager.checkOpNoThrow(permissionToOpCode, packageUidAsUser, str) == 0)) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
    }

    private boolean checkPermissionAndNote(java.lang.String str, int i, java.lang.String... strArr) {
        for (java.lang.String str2 : strArr) {
            if (this.mContext.checkCallingOrSelfPermission(str2) == 0) {
                if (android.util.Log.isLoggable(TAG, 2)) {
                    android.util.Log.v(TAG, "  caller uid " + i + " has " + str2);
                }
                int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(str2);
                if (permissionToOpCode == -1 || this.mAppOpsManager.noteOpNoThrow(permissionToOpCode, i, str) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int handleIncomingUser(int i) {
        try {
            return android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, true, true, "", (java.lang.String) null);
        } catch (android.os.RemoteException e) {
            return i;
        }
    }

    private boolean isPrivileged(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid == null) {
                android.util.Log.d(TAG, "No packages for callingUid " + i);
                return false;
            }
            for (java.lang.String str : packagesForUid) {
                try {
                    android.content.pm.PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 0);
                    if (packageInfo != null && (packageInfo.applicationInfo.privateFlags & 8) != 0) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Log.w(TAG, "isPrivileged#Package not found " + e.getMessage());
                }
            }
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean permissionIsGranted(android.accounts.Account account, java.lang.String str, int i, int i2) {
        if (android.os.UserHandle.getAppId(i) == 1000) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "Access to " + account + " granted calling uid is system");
            }
            return true;
        }
        if (isPrivileged(i)) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "Access to " + account + " granted calling uid " + i + " privileged");
            }
            return true;
        }
        if (account != null && isAccountManagedByCaller(account.type, i, i2)) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "Access to " + account + " granted calling uid " + i + " manages the account");
            }
            return true;
        }
        if (account != null && hasExplicitlyGrantedPermission(account, str, i)) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "Access to " + account + " granted calling uid " + i + " user granted access");
            }
            return true;
        }
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "Access to " + account + " not granted for uid " + i);
            return false;
        }
        return false;
    }

    private boolean isAccountVisibleToCaller(java.lang.String str, int i, int i2, java.lang.String str2) {
        if (str == null) {
            return false;
        }
        return getTypesVisibleToCaller(i, i2, str2).contains(str);
    }

    private boolean checkGetAccountsPermission(java.lang.String str, int i) {
        return isPermittedForPackage(str, i, "android.permission.GET_ACCOUNTS", "android.permission.GET_ACCOUNTS_PRIVILEGED");
    }

    private boolean checkReadContactsPermission(java.lang.String str, int i) {
        return isPermittedForPackage(str, i, "android.permission.READ_CONTACTS");
    }

    private boolean accountTypeManagesContacts(java.lang.String str, int i) {
        if (str == null) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.Collection<android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription>> allServices = this.mAuthenticatorCache.getAllServices(i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            for (android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription> serviceInfo : allServices) {
                if (str.equals(((android.accounts.AuthenticatorDescription) serviceInfo.type).type)) {
                    return isPermittedForPackage(((android.accounts.AuthenticatorDescription) serviceInfo.type).packageName, i, "android.permission.WRITE_CONTACTS");
                }
            }
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private int checkPackageSignature(java.lang.String str, int i, int i2) {
        if (str == null) {
            return 0;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.Collection<android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription>> allServices = this.mAuthenticatorCache.getAllServices(i2);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            for (android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription> serviceInfo : allServices) {
                if (str.equals(((android.accounts.AuthenticatorDescription) serviceInfo.type).type)) {
                    if (serviceInfo.uid == i) {
                        return 2;
                    }
                    if (packageManagerInternal.hasSignatureCapability(serviceInfo.uid, i, 16)) {
                        return 1;
                    }
                }
            }
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean isAccountManagedByCaller(java.lang.String str, int i, int i2) {
        if (str == null) {
            return false;
        }
        return getTypesManagedByCaller(i, i2).contains(str);
    }

    private java.util.List<java.lang.String> getTypesVisibleToCaller(int i, int i2, java.lang.String str) {
        return getTypesForCaller(i, i2, true);
    }

    private java.util.List<java.lang.String> getTypesManagedByCaller(int i, int i2) {
        return getTypesForCaller(i, i2, false);
    }

    private java.util.List<java.lang.String> getTypesForCaller(int i, int i2, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.Collection<android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription>> allServices = this.mAuthenticatorCache.getAllServices(i2);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            for (android.content.pm.RegisteredServicesCache.ServiceInfo<android.accounts.AuthenticatorDescription> serviceInfo : allServices) {
                if (z || packageManagerInternal.hasSignatureCapability(serviceInfo.uid, i, 16)) {
                    arrayList.add(((android.accounts.AuthenticatorDescription) serviceInfo.type).type);
                }
            }
            return arrayList;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAccountPresentForCaller(java.lang.String str, java.lang.String str2) {
        if (getUserAccountsForCaller().accountCache.containsKey(str2)) {
            for (android.accounts.Account account : getUserAccountsForCaller().accountCache.get(str2)) {
                if (account.name.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void checkManageUsersPermission(java.lang.String str) {
        if (android.app.ActivityManager.checkComponentPermission("android.permission.MANAGE_USERS", android.os.Binder.getCallingUid(), -1, true) != 0) {
            throw new java.lang.SecurityException("You need MANAGE_USERS permission to: " + str);
        }
    }

    private static void checkManageOrCreateUsersPermission(java.lang.String str) {
        if (android.app.ActivityManager.checkComponentPermission("android.permission.MANAGE_USERS", android.os.Binder.getCallingUid(), -1, true) != 0 && android.app.ActivityManager.checkComponentPermission("android.permission.CREATE_USERS", android.os.Binder.getCallingUid(), -1, true) != 0) {
            throw new java.lang.SecurityException("You need MANAGE_USERS or CREATE_USERS permission to: " + str);
        }
    }

    private boolean hasExplicitlyGrantedPermission(android.accounts.Account account, java.lang.String str, int i) {
        long findMatchingGrantsCountAnyToken;
        if (android.os.UserHandle.getAppId(i) == 1000) {
            return true;
        }
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(android.os.UserHandle.getUserId(i));
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    if (str != null) {
                        findMatchingGrantsCountAnyToken = userAccounts.accountsDb.findMatchingGrantsCount(i, str, account);
                    } else {
                        findMatchingGrantsCountAnyToken = userAccounts.accountsDb.findMatchingGrantsCountAnyToken(i, account);
                    }
                    boolean z = findMatchingGrantsCountAnyToken > 0;
                    if (z || !android.app.ActivityManager.isRunningInTestHarness()) {
                        return z;
                    }
                    android.util.Log.d(TAG, "no credentials permission for usage of " + account.toSafeString() + ", " + str + " by uid " + i + " but ignoring since device is in test harness.");
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private boolean isSystemUid(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid != null) {
                for (java.lang.String str : packagesForUid) {
                    try {
                        android.content.pm.PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 0);
                        if (packageInfo != null && (packageInfo.applicationInfo.flags & 1) != 0) {
                            return true;
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Log.w(TAG, java.lang.String.format("Could not find package [%s]", str), e);
                    }
                }
            } else {
                android.util.Log.w(TAG, "No known packages with uid " + i);
            }
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void checkReadAccountsPermitted(int i, java.lang.String str, int i2, java.lang.String str2) {
        if (!isAccountVisibleToCaller(str, i, i2, str2)) {
            java.lang.String format = java.lang.String.format("caller uid %s cannot access %s accounts", java.lang.Integer.valueOf(i), str);
            android.util.Log.w(TAG, "  " + format);
            throw new java.lang.SecurityException(format);
        }
    }

    private boolean canUserModifyAccounts(int i, int i2) {
        return isProfileOwner(i2) || !getUserManager().getUserRestrictions(new android.os.UserHandle(i)).getBoolean("no_modify_accounts");
    }

    private boolean canUserModifyAccountsForType(final int i, final java.lang.String str, final int i2) {
        return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda4
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$canUserModifyAccountsForType$3;
                lambda$canUserModifyAccountsForType$3 = com.android.server.accounts.AccountManagerService.this.lambda$canUserModifyAccountsForType$3(i2, i, str);
                return lambda$canUserModifyAccountsForType$3;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$canUserModifyAccountsForType$3(int i, int i2, java.lang.String str) throws java.lang.Exception {
        java.lang.String[] accountTypesWithManagementDisabledAsUser;
        if (isProfileOwner(i) || (accountTypesWithManagementDisabledAsUser = ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService("device_policy")).getAccountTypesWithManagementDisabledAsUser(i2)) == null) {
            return true;
        }
        for (java.lang.String str2 : accountTypesWithManagementDisabledAsUser) {
            if (str2.equals(str)) {
                return false;
            }
        }
        return true;
    }

    private boolean isProfileOwner(int i) {
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        return devicePolicyManagerInternal != null && (devicePolicyManagerInternal.isActiveProfileOwner(i) || devicePolicyManagerInternal.isActiveDeviceOwner(i));
    }

    private boolean canCallerAccessPackage(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        if (!android.app.compat.CompatChanges.isChangeEnabled(ENFORCE_PACKAGE_VISIBILITY_FILTERING, i)) {
            return packageManagerInternal.getPackageUid(str, 0L, i2) != -1;
        }
        boolean z = !packageManagerInternal.filterAppAccess(str, i, i2);
        if (!z && android.util.Log.isLoggable(TAG, 3)) {
            android.util.Log.d(TAG, "Package " + str + " is not visible to caller " + i + " for user " + i2);
        }
        return z;
    }

    public void updateAppPermission(android.accounts.Account account, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        if (android.os.UserHandle.getAppId(android.accounts.IAccountManager.Stub.getCallingUid()) != 1000) {
            throw new java.lang.SecurityException();
        }
        if (z) {
            grantAppPermission(account, str, i);
        } else {
            revokeAppPermission(account, str, i);
        }
    }

    void grantAppPermission(final android.accounts.Account account, java.lang.String str, final int i) {
        if (account == null || str == null) {
            android.util.Log.e(TAG, "grantAppPermission: called with invalid arguments", new java.lang.Exception());
            return;
        }
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(android.os.UserHandle.getUserId(i));
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                    if (findDeAccountId >= 0) {
                        userAccounts.accountsDb.insertGrant(findDeAccountId, str, i);
                    }
                    cancelNotification(getCredentialPermissionNotificationId(account, str, i, userAccounts), userAccounts);
                    cancelAccountAccessRequestNotificationIfNeeded(account, i, true, userAccounts);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        java.util.Iterator<android.accounts.AccountManagerInternal.OnAppPermissionChangeListener> it = this.mAppPermissionChangeListeners.iterator();
        while (it.hasNext()) {
            final android.accounts.AccountManagerInternal.OnAppPermissionChangeListener next = it.next();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    next.onAppPermissionChanged(account, i);
                }
            });
        }
    }

    private void revokeAppPermission(final android.accounts.Account account, java.lang.String str, final int i) {
        if (account == null || str == null) {
            android.util.Log.e(TAG, "revokeAppPermission: called with invalid arguments", new java.lang.Exception());
            return;
        }
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = getUserAccounts(android.os.UserHandle.getUserId(i));
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                userAccounts.accountsDb.beginTransaction();
                try {
                    long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                    if (findDeAccountId >= 0) {
                        userAccounts.accountsDb.deleteGrantsByAccountIdAuthTokenTypeAndUid(findDeAccountId, str, i);
                        userAccounts.accountsDb.setTransactionSuccessful();
                    }
                    userAccounts.accountsDb.endTransaction();
                    cancelNotification(getCredentialPermissionNotificationId(account, str, i, userAccounts), userAccounts);
                } catch (java.lang.Throwable th) {
                    userAccounts.accountsDb.endTransaction();
                    throw th;
                }
            }
        }
        java.util.Iterator<android.accounts.AccountManagerInternal.OnAppPermissionChangeListener> it = this.mAppPermissionChangeListeners.iterator();
        while (it.hasNext()) {
            final android.accounts.AccountManagerInternal.OnAppPermissionChangeListener next = it.next();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    next.onAppPermissionChanged(account, i);
                }
            });
        }
    }

    private void removeAccountFromCacheLocked(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account) {
        android.accounts.Account[] accountArr = userAccounts.accountCache.get(account.type);
        if (accountArr != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.accounts.Account account2 : accountArr) {
                if (!account2.equals(account)) {
                    arrayList.add(account2);
                }
            }
            if (arrayList.isEmpty()) {
                userAccounts.accountCache.remove(account.type);
            } else {
                userAccounts.accountCache.put(account.type, (android.accounts.Account[]) arrayList.toArray(new android.accounts.Account[arrayList.size()]));
            }
        }
        userAccounts.userDataCache.remove(account);
        userAccounts.authTokenCache.remove(account);
        userAccounts.previousNameCache.remove(account);
        userAccounts.visibilityCache.remove(account);
        android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
    }

    private android.accounts.Account insertAccountIntoCacheLocked(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account) {
        android.accounts.Account[] accountArr = userAccounts.accountCache.get(account.type);
        int length = accountArr != null ? accountArr.length : 0;
        android.accounts.Account[] accountArr2 = new android.accounts.Account[length + 1];
        if (accountArr != null) {
            java.lang.System.arraycopy(accountArr, 0, accountArr2, 0, length);
        }
        accountArr2[length] = new android.accounts.Account(account, account.getAccessId() != null ? account.getAccessId() : java.util.UUID.randomUUID().toString());
        userAccounts.accountCache.put(account.type, accountArr2);
        android.accounts.AccountManager.invalidateLocalAccountsDataCaches();
        return accountArr2[length];
    }

    @android.annotation.NonNull
    private android.accounts.Account[] filterAccounts(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account[] accountArr, int i, @android.annotation.Nullable java.lang.String str, boolean z) {
        java.lang.String str2;
        if (str != null) {
            str2 = str;
        } else {
            str2 = getPackageNameForUid(i);
        }
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        for (android.accounts.Account account : accountArr) {
            int intValue = resolveAccountVisibility(account, str2, userAccounts).intValue();
            if (intValue == 1 || intValue == 2 || (z && intValue == 4)) {
                linkedHashMap.put(account, java.lang.Integer.valueOf(intValue));
            }
        }
        java.util.Map<android.accounts.Account, java.lang.Integer> filterSharedAccounts = filterSharedAccounts(userAccounts, linkedHashMap, i, str);
        return (android.accounts.Account[]) filterSharedAccounts.keySet().toArray(new android.accounts.Account[filterSharedAccounts.size()]);
    }

    @android.annotation.NonNull
    private java.util.Map<android.accounts.Account, java.lang.Integer> filterSharedAccounts(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, @android.annotation.NonNull java.util.Map<android.accounts.Account, java.lang.Integer> map, int i, @android.annotation.Nullable java.lang.String str) {
        boolean z;
        if (getUserManager() == null || userAccounts == null || userAccounts.userId < 0 || i == 1000) {
            return map;
        }
        android.content.pm.UserInfo userInfo = getUserManager().getUserInfo(userAccounts.userId);
        if (userInfo != null && userInfo.isRestricted()) {
            java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid == null) {
                packagesForUid = new java.lang.String[0];
            }
            java.lang.String string = this.mContext.getResources().getString(android.R.string.concurrent_display_notification_thermal_title);
            for (java.lang.String str2 : packagesForUid) {
                if (string.contains(";" + str2 + ";")) {
                    return map;
                }
            }
            android.accounts.Account[] sharedAccountsAsUser = getSharedAccountsAsUser(userAccounts.userId);
            if (com.android.internal.util.ArrayUtils.isEmpty(sharedAccountsAsUser)) {
                return map;
            }
            java.lang.String str3 = "";
            try {
                if (str != null) {
                    android.content.pm.PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 0);
                    if (packageInfo != null && packageInfo.restrictedAccountType != null) {
                        str3 = packageInfo.restrictedAccountType;
                    }
                } else {
                    int length = packagesForUid.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        android.content.pm.PackageInfo packageInfo2 = this.mPackageManager.getPackageInfo(packagesForUid[i2], 0);
                        if (packageInfo2 == null || packageInfo2.restrictedAccountType == null) {
                            i2++;
                        } else {
                            str3 = packageInfo2.restrictedAccountType;
                            break;
                        }
                    }
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.w(TAG, "filterSharedAccounts#Package not found " + e.getMessage());
            }
            java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
            for (java.util.Map.Entry<android.accounts.Account, java.lang.Integer> entry : map.entrySet()) {
                android.accounts.Account key = entry.getKey();
                if (key.type.equals(str3)) {
                    linkedHashMap.put(key, entry.getValue());
                } else {
                    int length2 = sharedAccountsAsUser.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length2) {
                            z = false;
                            break;
                        }
                        if (!sharedAccountsAsUser[i3].equals(key)) {
                            i3++;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        linkedHashMap.put(key, entry.getValue());
                    }
                }
            }
            return linkedHashMap;
        }
        return map;
    }

    @android.annotation.NonNull
    protected android.accounts.Account[] getAccountsFromCache(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, boolean z) {
        android.accounts.Account[] accountArr;
        com.android.internal.util.Preconditions.checkState(!java.lang.Thread.holdsLock(userAccounts.cacheLock), "Method should not be called with cacheLock");
        if (str != null) {
            synchronized (userAccounts.cacheLock) {
                accountArr = userAccounts.accountCache.get(str);
            }
            if (accountArr == null) {
                return EMPTY_ACCOUNT_ARRAY;
            }
            return filterAccounts(userAccounts, (android.accounts.Account[]) java.util.Arrays.copyOf(accountArr, accountArr.length), i, str2, z);
        }
        synchronized (userAccounts.cacheLock) {
            try {
                java.util.Iterator<android.accounts.Account[]> it = userAccounts.accountCache.values().iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    i2 += it.next().length;
                }
                if (i2 == 0) {
                    return EMPTY_ACCOUNT_ARRAY;
                }
                android.accounts.Account[] accountArr2 = new android.accounts.Account[i2];
                int i3 = 0;
                for (android.accounts.Account[] accountArr3 : userAccounts.accountCache.values()) {
                    java.lang.System.arraycopy(accountArr3, 0, accountArr2, i3, accountArr3.length);
                    i3 += accountArr3.length;
                }
                return filterAccounts(userAccounts, accountArr2, i, str2, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void writeUserDataIntoCacheLocked(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str, java.lang.String str2) {
        java.util.Map<java.lang.String, java.lang.String> map = (java.util.Map) userAccounts.userDataCache.get(account);
        if (map == null) {
            map = userAccounts.accountsDb.findUserExtrasForAccount(account);
            userAccounts.userDataCache.put(account, map);
        }
        if (str2 == null) {
            map.remove(str);
        } else {
            map.put(str, str2);
        }
    }

    protected com.android.server.accounts.TokenCache.Value readCachedTokenInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str, java.lang.String str2, byte[] bArr) {
        com.android.server.accounts.TokenCache.Value value;
        synchronized (userAccounts.cacheLock) {
            value = userAccounts.accountTokenCaches.get(account, str, str2, bArr);
        }
        return value;
    }

    protected void writeAuthTokenIntoCacheLocked(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str, java.lang.String str2) {
        java.util.Map<java.lang.String, java.lang.String> map = (java.util.Map) userAccounts.authTokenCache.get(account);
        if (map == null) {
            map = userAccounts.accountsDb.findAuthTokensByAccount(account);
            userAccounts.authTokenCache.put(account, map);
        }
        if (str2 == null) {
            map.remove(str);
        } else {
            map.put(str, str2);
        }
    }

    protected java.lang.String readAuthTokenInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str) {
        java.lang.String str2;
        synchronized (userAccounts.cacheLock) {
            try {
                java.util.Map map = (java.util.Map) userAccounts.authTokenCache.get(account);
                if (map != null) {
                    return (java.lang.String) map.get(str);
                }
                synchronized (userAccounts.dbLock) {
                    synchronized (userAccounts.cacheLock) {
                        try {
                            java.util.Map<java.lang.String, java.lang.String> map2 = (java.util.Map) userAccounts.authTokenCache.get(account);
                            if (map2 == null) {
                                map2 = userAccounts.accountsDb.findAuthTokensByAccount(account);
                                userAccounts.authTokenCache.put(account, map2);
                            }
                            str2 = map2.get(str);
                        } finally {
                        }
                    }
                }
                return str2;
            } finally {
            }
        }
    }

    private java.lang.String readUserDataInternal(com.android.server.accounts.AccountManagerService.UserAccounts userAccounts, android.accounts.Account account, java.lang.String str) {
        java.util.Map<java.lang.String, java.lang.String> map;
        java.util.Map<java.lang.String, java.lang.String> map2;
        synchronized (userAccounts.cacheLock) {
            map = (java.util.Map) userAccounts.userDataCache.get(account);
        }
        if (map == null) {
            synchronized (userAccounts.dbLock) {
                synchronized (userAccounts.cacheLock) {
                    try {
                        map2 = (java.util.Map) userAccounts.userDataCache.get(account);
                        if (map2 == null) {
                            map2 = userAccounts.accountsDb.findUserExtrasForAccount(account);
                            userAccounts.userDataCache.put(account, map2);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            map = map2;
        }
        return map.get(str);
    }

    private android.content.Context getContextForUser(android.os.UserHandle userHandle) {
        try {
            return this.mContext.createPackageContextAsUser(this.mContext.getPackageName(), 0, userHandle);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return this.mContext;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendResponse(android.accounts.IAccountManagerResponse iAccountManagerResponse, android.os.Bundle bundle) {
        try {
            iAccountManagerResponse.onResult(bundle);
        } catch (android.os.RemoteException e) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "failure while notifying response", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorResponse(android.accounts.IAccountManagerResponse iAccountManagerResponse, int i, java.lang.String str) {
        try {
            iAccountManagerResponse.onError(i, str);
        } catch (android.os.RemoteException e) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "failure while notifying response", e);
            }
        }
    }

    private boolean isFirstAccountRemovalDisabled(android.accounts.Account account) {
        if (android.os.UserHandle.getCallingUserId() != 0 || this.mContext.getResources().getBoolean(android.R.bool.config_canRemoveFirstAccount) || android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "allow_primary_gaia_account_removal_for_tests", 0, 0) != 0) {
            return false;
        }
        java.lang.String string = this.mContext.getResources().getString(android.R.string.concurrent_display_notification_name);
        if (string.isEmpty() || !string.equals(account.type)) {
            return false;
        }
        android.accounts.Account[] accountsFromCache = getAccountsFromCache(getUserAccounts(0), string, 1000, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, false);
        return accountsFromCache.length > 0 && accountsFromCache[0].equals(account);
    }

    private final class AccountManagerInternalImpl extends android.accounts.AccountManagerInternal {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private com.android.server.accounts.AccountManagerBackupHelper mBackupHelper;
        private final java.lang.Object mLock;

        private AccountManagerInternalImpl() {
            this.mLock = new java.lang.Object();
        }

        public void requestAccountAccess(@android.annotation.NonNull android.accounts.Account account, @android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.os.RemoteCallback remoteCallback) {
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts;
            if (account == null) {
                android.util.Slog.w(com.android.server.accounts.AccountManagerService.TAG, "account cannot be null");
                return;
            }
            if (str == null) {
                android.util.Slog.w(com.android.server.accounts.AccountManagerService.TAG, "packageName cannot be null");
                return;
            }
            if (i < 0) {
                android.util.Slog.w(com.android.server.accounts.AccountManagerService.TAG, "user id must be concrete");
                return;
            }
            if (remoteCallback == null) {
                android.util.Slog.w(com.android.server.accounts.AccountManagerService.TAG, "callback cannot be null");
                return;
            }
            if (com.android.server.accounts.AccountManagerService.this.resolveAccountVisibility(account, str, com.android.server.accounts.AccountManagerService.this.getUserAccounts(i)).intValue() == 3) {
                android.util.Slog.w(com.android.server.accounts.AccountManagerService.TAG, "requestAccountAccess: account is hidden");
                return;
            }
            if (com.android.server.accounts.AccountManagerService.this.hasAccountAccess(account, str, new android.os.UserHandle(i))) {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putBoolean("booleanResult", true);
                remoteCallback.sendResult(bundle);
                return;
            }
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    int packageUidAsUser = com.android.server.accounts.AccountManagerService.this.mPackageManager.getPackageUidAsUser(str, i);
                    android.content.Intent newRequestAccountAccessIntent = com.android.server.accounts.AccountManagerService.this.newRequestAccountAccessIntent(account, str, packageUidAsUser, remoteCallback);
                    synchronized (com.android.server.accounts.AccountManagerService.this.mUsers) {
                        userAccounts = (com.android.server.accounts.AccountManagerService.UserAccounts) com.android.server.accounts.AccountManagerService.this.mUsers.get(i);
                    }
                    com.android.internal.notification.SystemNotificationChannels.createAccountChannelForPackage(str, packageUidAsUser, com.android.server.accounts.AccountManagerService.this.mContext);
                    com.android.server.accounts.AccountManagerService.this.doNotification(userAccounts, account, null, newRequestAccountAccessIntent, str, i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(com.android.server.accounts.AccountManagerService.TAG, "Unknown package " + str);
            }
        }

        public void addOnAppPermissionChangeListener(android.accounts.AccountManagerInternal.OnAppPermissionChangeListener onAppPermissionChangeListener) {
            com.android.server.accounts.AccountManagerService.this.mAppPermissionChangeListeners.add(onAppPermissionChangeListener);
        }

        public boolean hasAccountAccess(@android.annotation.NonNull android.accounts.Account account, int i) {
            return com.android.server.accounts.AccountManagerService.this.hasAccountAccess(account, (java.lang.String) null, i);
        }

        public byte[] backupAccountAccessPermissions(int i) {
            byte[] backupAccountAccessPermissions;
            synchronized (this.mLock) {
                try {
                    if (this.mBackupHelper == null) {
                        this.mBackupHelper = new com.android.server.accounts.AccountManagerBackupHelper(com.android.server.accounts.AccountManagerService.this, this);
                    }
                    backupAccountAccessPermissions = this.mBackupHelper.backupAccountAccessPermissions(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return backupAccountAccessPermissions;
        }

        public void restoreAccountAccessPermissions(byte[] bArr, int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mBackupHelper == null) {
                        this.mBackupHelper = new com.android.server.accounts.AccountManagerBackupHelper(com.android.server.accounts.AccountManagerService.this, this);
                    }
                    this.mBackupHelper.restoreAccountAccessPermissions(bArr, i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        private final android.content.Context mContext;

        public Injector(android.content.Context context) {
            this.mContext = context;
        }

        android.os.Looper getMessageHandlerLooper() {
            com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(com.android.server.accounts.AccountManagerService.TAG, -2, true);
            serviceThread.start();
            return serviceThread.getLooper();
        }

        android.content.Context getContext() {
            return this.mContext;
        }

        void addLocalService(android.accounts.AccountManagerInternal accountManagerInternal) {
            com.android.server.LocalServices.addService(android.accounts.AccountManagerInternal.class, accountManagerInternal);
        }

        java.lang.String getDeDatabaseName(int i) {
            return new java.io.File(android.os.Environment.getDataSystemDeDirectory(i), "accounts_de.db").getPath();
        }

        java.lang.String getCeDatabaseName(int i) {
            return new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), "accounts_ce.db").getPath();
        }

        java.lang.String getPreNDatabaseName(int i) {
            java.io.File dataSystemDirectory = android.os.Environment.getDataSystemDirectory();
            java.io.File file = new java.io.File(android.os.Environment.getUserSystemDirectory(i), com.android.server.accounts.AccountManagerService.PRE_N_DATABASE_NAME);
            if (i == 0) {
                java.io.File file2 = new java.io.File(dataSystemDirectory, com.android.server.accounts.AccountManagerService.PRE_N_DATABASE_NAME);
                if (file2.exists() && !file.exists()) {
                    java.io.File userSystemDirectory = android.os.Environment.getUserSystemDirectory(i);
                    if (!userSystemDirectory.exists() && !userSystemDirectory.mkdirs()) {
                        throw new java.lang.IllegalStateException("User dir cannot be created: " + userSystemDirectory);
                    }
                    if (!file2.renameTo(file)) {
                        throw new java.lang.IllegalStateException("User dir cannot be migrated: " + file);
                    }
                }
            }
            return file.getPath();
        }

        com.android.server.accounts.IAccountAuthenticatorCache getAccountAuthenticatorCache() {
            return new com.android.server.accounts.AccountAuthenticatorCache(this.mContext);
        }

        android.app.INotificationManager getNotificationManager() {
            return android.app.NotificationManager.getService();
        }
    }

    private static class NotificationId {
        private final int mId;
        final java.lang.String mTag;

        NotificationId(java.lang.String str, int i) {
            this.mTag = str;
            this.mId = i;
        }
    }
}
