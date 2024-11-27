package com.android.server.slice;

/* loaded from: classes2.dex */
public class SliceManagerService extends android.app.slice.ISliceManager.Stub {
    private static final java.lang.String TAG = "SliceManagerService";
    private final android.app.AppOpsManager mAppOps;
    private final android.app.usage.UsageStatsManagerInternal mAppUsageStats;
    private final com.android.internal.app.AssistUtils mAssistUtils;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.slice.SliceManagerService.PackageMatchingCache> mAssistantLookup;
    private java.lang.String mCachedDefaultHome;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.slice.SliceManagerService.PackageMatchingCache> mHomeLookup;
    private final java.lang.Object mLock;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final com.android.server.slice.SlicePermissionManager mPermissions;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.net.Uri, com.android.server.slice.PinnedSliceState> mPinnedSlicesByUri;
    private final android.content.BroadcastReceiver mReceiver;
    private com.android.server.slice.SliceManagerService.RoleObserver mRoleObserver;

    public SliceManagerService(android.content.Context context) {
        this(context, createHandler().getLooper());
    }

    @com.android.internal.annotations.VisibleForTesting
    SliceManagerService(android.content.Context context, android.os.Looper looper) {
        this.mLock = new java.lang.Object();
        this.mPinnedSlicesByUri = new android.util.ArrayMap<>();
        this.mAssistantLookup = new android.util.SparseArray<>();
        this.mHomeLookup = new android.util.SparseArray<>();
        this.mCachedDefaultHome = null;
        this.mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.slice.SliceManagerService.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                char c;
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                if (intExtra == -10000) {
                    android.util.Slog.w(com.android.server.slice.SliceManagerService.TAG, "Intent broadcast does not contain user handle: " + intent);
                }
                android.net.Uri data = intent.getData();
                java.lang.String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                if (schemeSpecificPart == null) {
                    android.util.Slog.w(com.android.server.slice.SliceManagerService.TAG, "Intent broadcast does not contain package name: " + intent);
                    return;
                }
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case 267468725:
                        if (action.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 525384130:
                        if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            com.android.server.slice.SliceManagerService.this.mPermissions.removePkg(schemeSpecificPart, intExtra);
                            break;
                        }
                        break;
                    case 1:
                        com.android.server.slice.SliceManagerService.this.mPermissions.removePkg(schemeSpecificPart, intExtra);
                        break;
                }
            }
        };
        this.mContext = context;
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        java.util.Objects.requireNonNull(packageManagerInternal);
        this.mPackageManagerInternal = packageManagerInternal;
        this.mAppOps = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mAssistUtils = new com.android.internal.app.AssistUtils(context);
        this.mHandler = new android.os.Handler(looper);
        this.mAppUsageStats = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        this.mPermissions = new com.android.server.slice.SlicePermissionManager(this.mContext, looper);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mRoleObserver = new com.android.server.slice.SliceManagerService.RoleObserver();
        this.mContext.registerReceiverAsUser(this.mReceiver, android.os.UserHandle.ALL, intentFilter, null, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void systemReady() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUnlockUser(int i) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStopUser(final int i) {
        synchronized (this.mLock) {
            this.mPinnedSlicesByUri.values().removeIf(new java.util.function.Predicate() { // from class: com.android.server.slice.SliceManagerService$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onStopUser$0;
                    lambda$onStopUser$0 = com.android.server.slice.SliceManagerService.lambda$onStopUser$0(i, (com.android.server.slice.PinnedSliceState) obj);
                    return lambda$onStopUser$0;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onStopUser$0(int i, com.android.server.slice.PinnedSliceState pinnedSliceState) {
        return android.content.ContentProvider.getUserIdFromUri(pinnedSliceState.getUri()) == i;
    }

    public android.net.Uri[] getPinnedSlices(java.lang.String str) {
        verifyCaller(str);
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                for (com.android.server.slice.PinnedSliceState pinnedSliceState : this.mPinnedSlicesByUri.values()) {
                    if (java.util.Objects.equals(str, pinnedSliceState.getPkg())) {
                        android.net.Uri uri = pinnedSliceState.getUri();
                        if (android.content.ContentProvider.getUserIdFromUri(uri, identifier) == identifier) {
                            arrayList.add(android.content.ContentProvider.getUriWithoutUserId(uri));
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return (android.net.Uri[]) arrayList.toArray(new android.net.Uri[arrayList.size()]);
    }

    public void pinSlice(final java.lang.String str, android.net.Uri uri, android.app.slice.SliceSpec[] sliceSpecArr, android.os.IBinder iBinder) throws android.os.RemoteException {
        verifyCaller(str);
        enforceAccess(str, uri);
        final int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        android.net.Uri maybeAddUserId = android.content.ContentProvider.maybeAddUserId(uri, identifier);
        final java.lang.String providerPkg = getProviderPkg(maybeAddUserId, identifier);
        getOrCreatePinnedSlice(maybeAddUserId, providerPkg).pin(str, sliceSpecArr, iBinder);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.slice.SliceManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.slice.SliceManagerService.this.lambda$pinSlice$1(providerPkg, str, identifier);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pinSlice$1(java.lang.String str, java.lang.String str2, int i) {
        if (str != null && !java.util.Objects.equals(str2, str)) {
            this.mAppUsageStats.reportEvent(str, i, (isAssistant(str2, i) || isDefaultHomeApp(str2, i)) ? 13 : 14);
        }
    }

    public void unpinSlice(java.lang.String str, android.net.Uri uri, android.os.IBinder iBinder) throws android.os.RemoteException {
        verifyCaller(str);
        enforceAccess(str, uri);
        android.net.Uri maybeAddUserId = android.content.ContentProvider.maybeAddUserId(uri, android.os.Binder.getCallingUserHandle().getIdentifier());
        try {
            com.android.server.slice.PinnedSliceState pinnedSlice = getPinnedSlice(maybeAddUserId);
            if (pinnedSlice != null && pinnedSlice.unpin(str, iBinder)) {
                removePinnedSlice(maybeAddUserId);
            }
        } catch (java.lang.IllegalStateException e) {
            android.util.Slog.w(TAG, e.getMessage());
        }
    }

    public boolean hasSliceAccess(java.lang.String str) throws android.os.RemoteException {
        verifyCaller(str);
        return hasFullSliceAccess(str, android.os.Binder.getCallingUserHandle().getIdentifier());
    }

    public android.app.slice.SliceSpec[] getPinnedSpecs(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException {
        verifyCaller(str);
        enforceAccess(str, uri);
        return getPinnedSlice(android.content.ContentProvider.maybeAddUserId(uri, android.os.Binder.getCallingUserHandle().getIdentifier())).getSpecs();
    }

    public void grantSlicePermission(java.lang.String str, java.lang.String str2, android.net.Uri uri) throws android.os.RemoteException {
        verifyCaller(str);
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        enforceOwner(str, uri, identifier);
        this.mPermissions.grantSliceAccess(str2, identifier, str, identifier, uri);
    }

    public void revokeSlicePermission(java.lang.String str, java.lang.String str2, android.net.Uri uri) throws android.os.RemoteException {
        verifyCaller(str);
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        enforceOwner(str, uri, identifier);
        this.mPermissions.revokeSliceAccess(str2, identifier, str, identifier, uri);
    }

    public int checkSlicePermission(android.net.Uri uri, java.lang.String str, int i, int i2, java.lang.String[] strArr) {
        return checkSlicePermissionInternal(uri, str, null, i, i2, strArr);
    }

    private int checkSlicePermissionInternal(android.net.Uri uri, java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String[] strArr) {
        int userId = android.os.UserHandle.getUserId(i2);
        if (str2 == null) {
            java.lang.String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i2);
            int length = packagesForUid.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = i3;
                if (checkSlicePermissionInternal(uri, str, packagesForUid[i3], i, i2, strArr) == 0) {
                    return 0;
                }
                i3 = i4 + 1;
            }
            return -1;
        }
        if (hasFullSliceAccess(str2, userId) || this.mPermissions.hasPermission(str2, userId, uri)) {
            return 0;
        }
        if (strArr != null && str != null) {
            enforceOwner(str, uri, userId);
            verifyCaller(str);
            for (java.lang.String str3 : strArr) {
                if (this.mContext.checkPermission(str3, i, i2) == 0) {
                    int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(uri, userId);
                    this.mPermissions.grantSliceAccess(str2, userId, getProviderPkg(uri, userIdFromUri), userIdFromUri, uri);
                    return 0;
                }
            }
        }
        return -1;
    }

    public void grantPermissionFromUser(android.net.Uri uri, java.lang.String str, java.lang.String str2, boolean z) {
        verifyCaller(str2);
        getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_SLICE_PERMISSIONS", "Slice granting requires MANAGE_SLICE_PERMISSIONS");
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        if (z) {
            this.mPermissions.grantFullAccess(str, identifier);
        } else {
            android.net.Uri build = uri.buildUpon().path("").build();
            int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(build, identifier);
            this.mPermissions.grantSliceAccess(str, identifier, getProviderPkg(build, userIdFromUri), userIdFromUri, build);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.getContentResolver().notifyChange(uri, null);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public byte[] getBackupPayload(int i) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Caller must be system");
        }
        if (i != 0) {
            android.util.Slog.w(TAG, "getBackupPayload: cannot backup policy for user " + i);
            return null;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            org.xmlpull.v1.XmlSerializer newSerializer = org.xmlpull.v1.XmlPullParserFactory.newInstance().newSerializer();
            newSerializer.setOutput(byteArrayOutputStream, android.util.Xml.Encoding.UTF_8.name());
            this.mPermissions.writeBackup(newSerializer);
            newSerializer.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.w(TAG, "getBackupPayload: error writing payload for user " + i, e);
            return null;
        }
    }

    public void applyRestore(byte[] bArr, int i) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Caller must be system");
        }
        if (bArr == null) {
            android.util.Slog.w(TAG, "applyRestore: no payload to restore for user " + i);
            return;
        }
        if (i != 0) {
            android.util.Slog.w(TAG, "applyRestore: cannot restore policy for user " + i);
            return;
        }
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
        try {
            org.xmlpull.v1.XmlPullParser newPullParser = org.xmlpull.v1.XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(byteArrayInputStream, android.util.Xml.Encoding.UTF_8.name());
            this.mPermissions.readRestore(newPullParser);
        } catch (java.io.IOException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.w(TAG, "applyRestore: error reading payload", e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.slice.SliceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    private void enforceOwner(java.lang.String str, android.net.Uri uri, int i) {
        if (!java.util.Objects.equals(getProviderPkg(uri, i), str) || str == null) {
            throw new java.lang.SecurityException("Caller must own " + uri);
        }
    }

    protected void removePinnedSlice(android.net.Uri uri) {
        synchronized (this.mLock) {
            this.mPinnedSlicesByUri.remove(uri).destroy();
        }
    }

    private com.android.server.slice.PinnedSliceState getPinnedSlice(android.net.Uri uri) {
        com.android.server.slice.PinnedSliceState pinnedSliceState;
        synchronized (this.mLock) {
            try {
                pinnedSliceState = this.mPinnedSlicesByUri.get(uri);
                if (pinnedSliceState == null) {
                    throw new java.lang.IllegalStateException(java.lang.String.format("Slice %s not pinned", uri.toString()));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return pinnedSliceState;
    }

    private com.android.server.slice.PinnedSliceState getOrCreatePinnedSlice(android.net.Uri uri, java.lang.String str) {
        com.android.server.slice.PinnedSliceState pinnedSliceState;
        synchronized (this.mLock) {
            try {
                pinnedSliceState = this.mPinnedSlicesByUri.get(uri);
                if (pinnedSliceState == null) {
                    pinnedSliceState = createPinnedSlice(uri, str);
                    this.mPinnedSlicesByUri.put(uri, pinnedSliceState);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return pinnedSliceState;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.slice.PinnedSliceState createPinnedSlice(android.net.Uri uri, java.lang.String str) {
        return new com.android.server.slice.PinnedSliceState(this, uri, str);
    }

    public java.lang.Object getLock() {
        return this.mLock;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    public android.os.Handler getHandler() {
        return this.mHandler;
    }

    protected int checkAccess(java.lang.String str, android.net.Uri uri, int i, int i2) {
        return checkSlicePermissionInternal(uri, null, str, i2, i, null);
    }

    private java.lang.String getProviderPkg(android.net.Uri uri, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.pm.ProviderInfo resolveContentProviderAsUser = this.mContext.getPackageManager().resolveContentProviderAsUser(android.content.ContentProvider.getUriWithoutUserId(uri).getAuthority(), 0, android.content.ContentProvider.getUserIdFromUri(uri, i));
            return resolveContentProviderAsUser == null ? null : resolveContentProviderAsUser.packageName;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void enforceCrossUser(java.lang.String str, android.net.Uri uri) {
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        if (android.content.ContentProvider.getUserIdFromUri(uri, identifier) != identifier) {
            getContext().enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "Slice interaction across users requires INTERACT_ACROSS_USERS_FULL");
        }
    }

    private void enforceAccess(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
        if (checkAccess(str, uri, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid()) != 0 && !java.util.Objects.equals(str, getProviderPkg(uri, android.content.ContentProvider.getUserIdFromUri(uri, android.os.Binder.getCallingUserHandle().getIdentifier())))) {
            throw new java.lang.SecurityException("Access to slice " + uri + " is required");
        }
        enforceCrossUser(str, uri);
    }

    private void verifyCaller(java.lang.String str) {
        this.mAppOps.checkPackage(android.os.Binder.getCallingUid(), str);
    }

    private boolean hasFullSliceAccess(java.lang.String str, int i) {
        boolean z;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!isDefaultHomeApp(str, i) && !isAssistant(str, i)) {
                if (!isGrantedFullAccess(str, i)) {
                    z = false;
                    return z;
                }
            }
            z = true;
            return z;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean isAssistant(java.lang.String str, int i) {
        return getAssistantMatcher(i).matches(str);
    }

    private boolean isDefaultHomeApp(java.lang.String str, int i) {
        return getHomeMatcher(i).matches(str);
    }

    private com.android.server.slice.SliceManagerService.PackageMatchingCache getAssistantMatcher(final int i) {
        com.android.server.slice.SliceManagerService.PackageMatchingCache packageMatchingCache = this.mAssistantLookup.get(i);
        if (packageMatchingCache == null) {
            com.android.server.slice.SliceManagerService.PackageMatchingCache packageMatchingCache2 = new com.android.server.slice.SliceManagerService.PackageMatchingCache(new java.util.function.Supplier() { // from class: com.android.server.slice.SliceManagerService$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getAssistantMatcher$2;
                    lambda$getAssistantMatcher$2 = com.android.server.slice.SliceManagerService.this.lambda$getAssistantMatcher$2(i);
                    return lambda$getAssistantMatcher$2;
                }
            });
            this.mAssistantLookup.put(i, packageMatchingCache2);
            return packageMatchingCache2;
        }
        return packageMatchingCache;
    }

    private com.android.server.slice.SliceManagerService.PackageMatchingCache getHomeMatcher(final int i) {
        com.android.server.slice.SliceManagerService.PackageMatchingCache packageMatchingCache = this.mHomeLookup.get(i);
        if (packageMatchingCache == null) {
            com.android.server.slice.SliceManagerService.PackageMatchingCache packageMatchingCache2 = new com.android.server.slice.SliceManagerService.PackageMatchingCache(new java.util.function.Supplier() { // from class: com.android.server.slice.SliceManagerService$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getHomeMatcher$3;
                    lambda$getHomeMatcher$3 = com.android.server.slice.SliceManagerService.this.lambda$getHomeMatcher$3(i);
                    return lambda$getHomeMatcher$3;
                }
            });
            this.mHomeLookup.put(i, packageMatchingCache2);
            return packageMatchingCache2;
        }
        return packageMatchingCache;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getAssistant, reason: merged with bridge method [inline-methods] */
    public java.lang.String lambda$getAssistantMatcher$2(int i) {
        android.content.ComponentName assistComponentForUser = this.mAssistUtils.getAssistComponentForUser(i);
        if (assistComponentForUser == null) {
            return null;
        }
        return assistComponentForUser.getPackageName();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @com.android.internal.annotations.VisibleForTesting
    /* renamed from: getDefaultHome, reason: merged with bridge method [inline-methods] */
    public java.lang.String lambda$getHomeMatcher$3(int i) {
        if (this.mCachedDefaultHome != null) {
            return this.mCachedDefaultHome;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.content.ComponentName homeActivitiesAsUser = this.mPackageManagerInternal.getHomeActivitiesAsUser(arrayList, i);
            this.mCachedDefaultHome = homeActivitiesAsUser != null ? homeActivitiesAsUser.getPackageName() : null;
            if (homeActivitiesAsUser == null) {
                int size = arrayList.size();
                int i2 = Integer.MIN_VALUE;
                for (int i3 = 0; i3 < size; i3++) {
                    android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) arrayList.get(i3);
                    if (resolveInfo.activityInfo.applicationInfo.isSystemApp() && resolveInfo.priority >= i2) {
                        homeActivitiesAsUser = resolveInfo.activityInfo.getComponentName();
                        i2 = resolveInfo.priority;
                    }
                }
            }
            java.lang.String packageName = homeActivitiesAsUser != null ? homeActivitiesAsUser.getPackageName() : null;
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return packageName;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void invalidateCachedDefaultHome() {
        this.mCachedDefaultHome = null;
    }

    class RoleObserver implements android.app.role.OnRoleHoldersChangedListener {
        private final java.util.concurrent.Executor mExecutor;
        private android.app.role.RoleManager mRm;

        RoleObserver() {
            this.mExecutor = com.android.server.slice.SliceManagerService.this.mContext.getMainExecutor();
            register();
        }

        public void register() {
            this.mRm = (android.app.role.RoleManager) com.android.server.slice.SliceManagerService.this.mContext.getSystemService(android.app.role.RoleManager.class);
            if (this.mRm != null) {
                this.mRm.addOnRoleHoldersChangedListenerAsUser(this.mExecutor, this, android.os.UserHandle.ALL);
                com.android.server.slice.SliceManagerService.this.invalidateCachedDefaultHome();
            }
        }

        public void onRoleHoldersChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
            if ("android.app.role.HOME".equals(str)) {
                com.android.server.slice.SliceManagerService.this.invalidateCachedDefaultHome();
            }
        }
    }

    private boolean isGrantedFullAccess(java.lang.String str, int i) {
        return this.mPermissions.hasFullAccess(str, i);
    }

    private static com.android.server.ServiceThread createHandler() {
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG, 10, true);
        serviceThread.start();
        return serviceThread;
    }

    public java.lang.String[] getAllPackagesGranted(java.lang.String str) {
        java.lang.String providerPkg = getProviderPkg(new android.net.Uri.Builder().scheme(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT).authority(str).build(), 0);
        return providerPkg == null ? new java.lang.String[0] : this.mPermissions.getAllPackagesGranted(providerPkg);
    }

    static class PackageMatchingCache {
        private java.lang.String mCurrentPkg;
        private final java.util.function.Supplier<java.lang.String> mPkgSource;

        public PackageMatchingCache(java.util.function.Supplier<java.lang.String> supplier) {
            this.mPkgSource = supplier;
        }

        public boolean matches(java.lang.String str) {
            if (str == null) {
                return false;
            }
            if (java.util.Objects.equals(str, this.mCurrentPkg)) {
                return true;
            }
            this.mCurrentPkg = this.mPkgSource.get();
            return java.util.Objects.equals(str, this.mCurrentPkg);
        }
    }

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.slice.SliceManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mService = new com.android.server.slice.SliceManagerService(getContext());
            publishBinderService("slice", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mService.systemReady();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onUnlockUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onStopUser(targetUser.getUserIdentifier());
        }
    }

    private class SliceGrant {
        private final java.lang.String mPkg;
        private final android.net.Uri mUri;
        private final int mUserId;

        public SliceGrant(android.net.Uri uri, java.lang.String str, int i) {
            this.mUri = uri;
            this.mPkg = str;
            this.mUserId = i;
        }

        public int hashCode() {
            return this.mUri.hashCode() + this.mPkg.hashCode();
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.slice.SliceManagerService.SliceGrant)) {
                return false;
            }
            com.android.server.slice.SliceManagerService.SliceGrant sliceGrant = (com.android.server.slice.SliceManagerService.SliceGrant) obj;
            return java.util.Objects.equals(sliceGrant.mUri, this.mUri) && java.util.Objects.equals(sliceGrant.mPkg, this.mPkg) && sliceGrant.mUserId == this.mUserId;
        }
    }
}
