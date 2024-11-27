package com.android.server.uri;

/* loaded from: classes2.dex */
public class UriGrantsManagerService extends android.app.IUriGrantsManager.Stub implements com.android.server.uri.UriMetricsHelper.PersistentUriGrantsProvider {
    private static final java.lang.String ATTR_CREATED_TIME = "createdTime";
    private static final java.lang.String ATTR_MODE_FLAGS = "modeFlags";
    private static final java.lang.String ATTR_PREFIX = "prefix";
    private static final java.lang.String ATTR_SOURCE_PKG = "sourcePkg";
    private static final java.lang.String ATTR_SOURCE_USER_ID = "sourceUserId";
    private static final java.lang.String ATTR_TARGET_PKG = "targetPkg";
    private static final java.lang.String ATTR_TARGET_USER_ID = "targetUserId";
    private static final java.lang.String ATTR_URI = "uri";
    private static final java.lang.String ATTR_USER_HANDLE = "userHandle";
    private static final boolean DEBUG = false;
    private static final boolean ENABLE_DYNAMIC_PERMISSIONS = true;
    private static final int MAX_PERSISTED_URI_GRANTS = 512;
    private static final java.lang.String TAG = "UriGrantsManagerService";
    private static final java.lang.String TAG_URI_GRANT = "uri-grant";
    private static final java.lang.String TAG_URI_GRANTS = "uri-grants";
    android.app.ActivityManagerInternal mAmInternal;
    private final android.util.AtomicFile mGrantFile;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission>> mGrantedUriPermissions;
    private final com.android.server.uri.UriGrantsManagerService.H mH;
    private final java.lang.Object mLock;
    com.android.server.uri.UriMetricsHelper mMetricsHelper;
    android.content.pm.PackageManagerInternal mPmInternal;

    private UriGrantsManagerService() {
        this(com.android.server.SystemServiceManager.ensureSystemDir(), TAG_URI_GRANTS);
    }

    private UriGrantsManagerService(java.io.File file, java.lang.String str) {
        this.mLock = new java.lang.Object();
        this.mGrantedUriPermissions = new android.util.SparseArray<>();
        this.mH = new com.android.server.uri.UriGrantsManagerService.H(com.android.server.IoThread.get().getLooper());
        java.io.File file2 = new java.io.File(file, "urigrants.xml");
        this.mGrantFile = str != null ? new android.util.AtomicFile(file2, str) : new android.util.AtomicFile(file2);
    }

    @com.android.internal.annotations.VisibleForTesting
    static com.android.server.uri.UriGrantsManagerService createForTest(java.io.File file) {
        com.android.server.uri.UriGrantsManagerService uriGrantsManagerService = new com.android.server.uri.UriGrantsManagerService(file, null) { // from class: com.android.server.uri.UriGrantsManagerService.1
            @Override // com.android.server.uri.UriGrantsManagerService
            @com.android.internal.annotations.VisibleForTesting
            protected int checkUidPermission(java.lang.String str, int i) {
                return -1;
            }

            @Override // com.android.server.uri.UriGrantsManagerService
            @com.android.internal.annotations.VisibleForTesting
            protected int checkComponentPermission(java.lang.String str, int i, int i2, boolean z) {
                return -1;
            }
        };
        uriGrantsManagerService.mAmInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        uriGrantsManagerService.mPmInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        return uriGrantsManagerService;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.uri.UriGrantsManagerInternal getLocalService() {
        return new com.android.server.uri.UriGrantsManagerService.LocalService();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void start() {
        com.android.server.LocalServices.addService(com.android.server.uri.UriGrantsManagerInternal.class, new com.android.server.uri.UriGrantsManagerService.LocalService());
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private final android.content.Context mContext;
        private final com.android.server.uri.UriGrantsManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mContext = context;
            this.mService = new com.android.server.uri.UriGrantsManagerService();
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("uri_grants", this.mService);
            this.mService.mMetricsHelper = new com.android.server.uri.UriMetricsHelper(this.mContext, this.mService);
            this.mService.start();
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 500) {
                this.mService.mAmInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
                this.mService.mPmInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
                this.mService.mMetricsHelper.registerPuller();
            }
        }

        public com.android.server.uri.UriGrantsManagerService getService() {
            return this.mService;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int checkUidPermission(java.lang.String str, int i) {
        try {
            return android.app.AppGlobals.getPackageManager().checkUidPermission(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int checkComponentPermission(java.lang.String str, int i, int i2, boolean z) {
        return android.app.ActivityManager.checkComponentPermission(str, i, i2, z);
    }

    public void grantUriPermissionFromOwner(android.os.IBinder iBinder, int i, java.lang.String str, android.net.Uri uri, int i2, int i3, int i4) {
        grantUriPermissionFromOwnerUnlocked(iBinder, i, str, uri, i2, i3, i4);
    }

    private void grantUriPermissionFromOwnerUnlocked(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.net.Uri uri, int i2, int i3, int i4) {
        int handleIncomingUser = this.mAmInternal.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i4, false, 2, "grantUriPermissionFromOwner", (java.lang.String) null);
        com.android.server.uri.UriPermissionOwner fromExternalToken = com.android.server.uri.UriPermissionOwner.fromExternalToken(iBinder);
        if (fromExternalToken == null) {
            throw new java.lang.IllegalArgumentException("Unknown owner: " + iBinder);
        }
        if (i != android.os.Binder.getCallingUid() && android.os.Binder.getCallingUid() != android.os.Process.myUid()) {
            throw new java.lang.SecurityException("nice try");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("null target");
        }
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("null uri");
        }
        grantUriPermissionUnlocked(i, str, new com.android.server.uri.GrantUri(i3, uri, i2), i2, fromExternalToken, handleIncomingUser);
    }

    public android.content.pm.ParceledListSlice<android.content.UriPermission> getUriPermissions(java.lang.String str, boolean z, boolean z2) {
        enforceNotIsolatedCaller("getUriPermissions");
        java.util.Objects.requireNonNull(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        int callingUid = android.os.Binder.getCallingUid();
        if (((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageUid(str, 786432L, android.os.UserHandle.getUserId(callingUid)) != callingUid) {
            throw new java.lang.SecurityException("Package " + str + " does not belong to calling UID " + callingUid);
        }
        java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
        synchronized (this.mLock) {
            try {
                if (z) {
                    android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> arrayMap = this.mGrantedUriPermissions.get(callingUid);
                    if (arrayMap == null) {
                        android.util.Slog.w(TAG, "No permission grants found for " + str);
                    } else {
                        for (int i = 0; i < arrayMap.size(); i++) {
                            com.android.server.uri.UriPermission valueAt = arrayMap.valueAt(i);
                            if (str.equals(valueAt.targetPkg) && (!z2 || valueAt.persistedModeFlags != 0)) {
                                newArrayList.add(valueAt.buildPersistedPublicApiObject());
                            }
                        }
                    }
                } else {
                    int size = this.mGrantedUriPermissions.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> valueAt2 = this.mGrantedUriPermissions.valueAt(i2);
                        for (int i3 = 0; i3 < valueAt2.size(); i3++) {
                            com.android.server.uri.UriPermission valueAt3 = valueAt2.valueAt(i3);
                            if (str.equals(valueAt3.sourcePkg) && (!z2 || valueAt3.persistedModeFlags != 0)) {
                                newArrayList.add(valueAt3.buildPersistedPublicApiObject());
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return new android.content.pm.ParceledListSlice<>(newArrayList);
    }

    public android.content.pm.ParceledListSlice<android.app.GrantedUriPermission> getGrantedUriPermissions(@android.annotation.Nullable java.lang.String str, int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.GET_APP_GRANTED_URI_PERMISSIONS", "getGrantedUriPermissions");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                int size = this.mGrantedUriPermissions.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> valueAt = this.mGrantedUriPermissions.valueAt(i2);
                    for (int i3 = 0; i3 < valueAt.size(); i3++) {
                        com.android.server.uri.UriPermission valueAt2 = valueAt.valueAt(i3);
                        if ((str == null || str.equals(valueAt2.targetPkg)) && valueAt2.targetUserId == i && valueAt2.persistedModeFlags != 0) {
                            arrayList.add(valueAt2.buildGrantedUriPermission());
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    public void takePersistableUriPermission(android.net.Uri uri, int i, @android.annotation.Nullable java.lang.String str, int i2) {
        int callingUid;
        if (str != null) {
            this.mAmInternal.enforceCallingPermission("android.permission.FORCE_PERSISTABLE_URI_PERMISSIONS", "takePersistableUriPermission");
            callingUid = this.mPmInternal.getPackageUid(str, 0L, i2);
        } else {
            enforceNotIsolatedCaller("takePersistableUriPermission");
            callingUid = android.os.Binder.getCallingUid();
        }
        com.android.internal.util.Preconditions.checkFlagsArgument(i, 3);
        synchronized (this.mLock) {
            try {
                com.android.server.uri.UriPermission findUriPermissionLocked = findUriPermissionLocked(callingUid, new com.android.server.uri.GrantUri(i2, uri, 0));
                com.android.server.uri.UriPermission findUriPermissionLocked2 = findUriPermissionLocked(callingUid, new com.android.server.uri.GrantUri(i2, uri, 128));
                boolean z = true;
                boolean z2 = findUriPermissionLocked != null && (findUriPermissionLocked.persistableModeFlags & i) == i;
                if (findUriPermissionLocked2 == null || (findUriPermissionLocked2.persistableModeFlags & i) != i) {
                    z = false;
                }
                if (!z2 && !z) {
                    throw new java.lang.SecurityException("No persistable permission grants found for UID " + callingUid + " and Uri " + uri.toSafeString());
                }
                boolean takePersistableModes = z2 ? false | findUriPermissionLocked.takePersistableModes(i) : false;
                if (z) {
                    takePersistableModes |= findUriPermissionLocked2.takePersistableModes(i);
                }
                if (maybePrunePersistedUriGrantsLocked(callingUid) | takePersistableModes) {
                    schedulePersistUriGrants();
                }
            } finally {
            }
        }
    }

    public void clearGrantedUriPermissions(java.lang.String str, int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.CLEAR_APP_GRANTED_URI_PERMISSIONS", "clearGrantedUriPermissions");
        synchronized (this.mLock) {
            removeUriPermissionsForPackageLocked(str, i, true, true);
        }
    }

    public void releasePersistableUriPermission(android.net.Uri uri, int i, @android.annotation.Nullable java.lang.String str, int i2) {
        int callingUid;
        if (str != null) {
            this.mAmInternal.enforceCallingPermission("android.permission.FORCE_PERSISTABLE_URI_PERMISSIONS", "releasePersistableUriPermission");
            callingUid = this.mPmInternal.getPackageUid(str, 0L, i2);
        } else {
            enforceNotIsolatedCaller("releasePersistableUriPermission");
            callingUid = android.os.Binder.getCallingUid();
        }
        com.android.internal.util.Preconditions.checkFlagsArgument(i, 3);
        synchronized (this.mLock) {
            try {
                boolean z = false;
                com.android.server.uri.UriPermission findUriPermissionLocked = findUriPermissionLocked(callingUid, new com.android.server.uri.GrantUri(i2, uri, 0));
                com.android.server.uri.UriPermission findUriPermissionLocked2 = findUriPermissionLocked(callingUid, new com.android.server.uri.GrantUri(i2, uri, 128));
                if (findUriPermissionLocked == null && findUriPermissionLocked2 == null && str == null) {
                    throw new java.lang.SecurityException("No permission grants found for UID " + callingUid + " and Uri " + uri.toSafeString());
                }
                if (findUriPermissionLocked != null) {
                    z = false | findUriPermissionLocked.releasePersistableModes(i);
                    removeUriPermissionIfNeededLocked(findUriPermissionLocked);
                }
                if (findUriPermissionLocked2 != null) {
                    z |= findUriPermissionLocked2.releasePersistableModes(i);
                    removeUriPermissionIfNeededLocked(findUriPermissionLocked2);
                }
                if (z) {
                    schedulePersistUriGrants();
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeUriPermissionsForPackageLocked(java.lang.String str, int i, boolean z, boolean z2) {
        if (i == -1 && str == null) {
            throw new java.lang.IllegalArgumentException("Must narrow by either package or user");
        }
        int size = this.mGrantedUriPermissions.size();
        int i2 = 0;
        boolean z3 = false;
        while (i2 < size) {
            int keyAt = this.mGrantedUriPermissions.keyAt(i2);
            android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> valueAt = this.mGrantedUriPermissions.valueAt(i2);
            if (i == -1 || i == android.os.UserHandle.getUserId(keyAt)) {
                java.util.Iterator<com.android.server.uri.UriPermission> it = valueAt.values().iterator();
                while (it.hasNext()) {
                    com.android.server.uri.UriPermission next = it.next();
                    if (str == null || ((!z2 && next.sourcePkg.equals(str)) || next.targetPkg.equals(str))) {
                        if (!"downloads".equals(next.uri.uri.getAuthority()) || z) {
                            z3 |= next.revokeModes(z ? -1 : -65, true);
                            if (next.modeFlags == 0) {
                                it.remove();
                            }
                        }
                    }
                }
                if (valueAt.isEmpty()) {
                    this.mGrantedUriPermissions.remove(keyAt);
                    size--;
                    i2--;
                }
            }
            i2++;
        }
        if (z3) {
            schedulePersistUriGrants();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean checkAuthorityGrantsLocked(int i, android.content.pm.ProviderInfo providerInfo, int i2, boolean z) {
        android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> arrayMap = this.mGrantedUriPermissions.get(i);
        if (arrayMap != null) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                com.android.server.uri.GrantUri keyAt = arrayMap.keyAt(size);
                if ((keyAt.sourceUserId == i2 || !z) && matchesProvider(keyAt.uri, providerInfo)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean matchesProvider(android.net.Uri uri, android.content.pm.ProviderInfo providerInfo) {
        java.lang.String authority = uri.getAuthority();
        java.lang.String str = providerInfo.authority;
        if (str.indexOf(59) == -1) {
            return str.equals(authority);
        }
        for (java.lang.String str2 : str.split(";")) {
            if (str2.equals(authority)) {
                return true;
            }
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean maybePrunePersistedUriGrantsLocked(int i) {
        android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> arrayMap = this.mGrantedUriPermissions.get(i);
        if (arrayMap == null || arrayMap.size() < 512) {
            return false;
        }
        java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
        for (com.android.server.uri.UriPermission uriPermission : arrayMap.values()) {
            if (uriPermission.persistedModeFlags != 0) {
                newArrayList.add(uriPermission);
            }
        }
        int size = newArrayList.size() - 512;
        if (size <= 0) {
            return false;
        }
        java.util.Collections.sort(newArrayList, new com.android.server.uri.UriPermission.PersistedTimeComparator());
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.uri.UriPermission uriPermission2 = (com.android.server.uri.UriPermission) newArrayList.get(i2);
            uriPermission2.releasePersistableModes(-1);
            removeUriPermissionIfNeededLocked(uriPermission2);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.uri.NeededUriGrants checkGrantUriPermissionFromIntentUnlocked(int i, java.lang.String str, android.content.Intent intent, int i2, com.android.server.uri.NeededUriGrants neededUriGrants, int i3, java.lang.Integer num) {
        int i4;
        int i5;
        int i6;
        com.android.server.uri.NeededUriGrants neededUriGrants2;
        com.android.server.uri.NeededUriGrants neededUriGrants3;
        com.android.server.uri.NeededUriGrants neededUriGrants4 = neededUriGrants;
        if (str == null) {
            throw new java.lang.NullPointerException(ATTR_TARGET_PKG);
        }
        if (intent == null) {
            return null;
        }
        int contentUserHint = intent.getContentUserHint();
        if (contentUserHint != -2) {
            i4 = contentUserHint;
        } else {
            i4 = android.os.UserHandle.getUserId(i);
        }
        if (android.security.Flags.contentUriPermissionApis()) {
            enforceRequireContentUriPermissionFromCallerOnIntentExtraStream(intent, i4, i2, i, num);
        }
        android.net.Uri data = intent.getData();
        android.content.ClipData clipData = intent.getClipData();
        if (data == null && clipData == null) {
            return null;
        }
        if (neededUriGrants4 != null) {
            i5 = neededUriGrants4.targetUid;
        } else {
            int packageUid = this.mPmInternal.getPackageUid(str, 268435456L, i3);
            if (packageUid < 0) {
                return null;
            }
            i5 = packageUid;
        }
        if (data != null) {
            com.android.server.uri.GrantUri resolve = com.android.server.uri.GrantUri.resolve(i4, data, i2);
            if (android.security.Flags.contentUriPermissionApis()) {
                enforceRequireContentUriPermissionFromCaller(num, resolve, i);
            }
            i5 = checkGrantUriPermissionUnlocked(i, str, resolve, i2, i5);
            if (i5 > 0) {
                if (neededUriGrants4 != null) {
                    neededUriGrants3 = neededUriGrants4;
                } else {
                    neededUriGrants3 = new com.android.server.uri.NeededUriGrants(str, i5, i2);
                }
                neededUriGrants3.uris.add(resolve);
                neededUriGrants4 = neededUriGrants3;
            }
        }
        if (clipData != null) {
            int i7 = 0;
            int i8 = i5;
            com.android.server.uri.NeededUriGrants neededUriGrants5 = neededUriGrants4;
            while (i7 < clipData.getItemCount()) {
                android.net.Uri uri = clipData.getItemAt(i7).getUri();
                if (uri != null) {
                    com.android.server.uri.GrantUri resolve2 = com.android.server.uri.GrantUri.resolve(i4, uri, i2);
                    if (android.security.Flags.contentUriPermissionApis()) {
                        enforceRequireContentUriPermissionFromCaller(num, resolve2, i);
                    }
                    int checkGrantUriPermissionUnlocked = checkGrantUriPermissionUnlocked(i, str, resolve2, i2, i8);
                    if (checkGrantUriPermissionUnlocked > 0) {
                        if (neededUriGrants5 != null) {
                            neededUriGrants2 = neededUriGrants5;
                        } else {
                            neededUriGrants2 = new com.android.server.uri.NeededUriGrants(str, checkGrantUriPermissionUnlocked, i2);
                        }
                        neededUriGrants2.uris.add(resolve2);
                        neededUriGrants5 = neededUriGrants2;
                    }
                    i8 = checkGrantUriPermissionUnlocked;
                    i6 = i7;
                } else {
                    android.content.Intent intent2 = clipData.getItemAt(i7).getIntent();
                    if (intent2 == null) {
                        i6 = i7;
                    } else {
                        i6 = i7;
                        com.android.server.uri.NeededUriGrants checkGrantUriPermissionFromIntentUnlocked = checkGrantUriPermissionFromIntentUnlocked(i, str, intent2, i2, neededUriGrants5, i3, num);
                        if (checkGrantUriPermissionFromIntentUnlocked != null) {
                            neededUriGrants5 = checkGrantUriPermissionFromIntentUnlocked;
                        }
                    }
                }
                i7 = i6 + 1;
            }
            return neededUriGrants5;
        }
        return neededUriGrants4;
    }

    private void enforceRequireContentUriPermissionFromCaller(java.lang.Integer num, com.android.server.uri.GrantUri grantUri, int i) {
        if (num == null || num.intValue() == 0 || !com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(grantUri.uri.getScheme())) {
            return;
        }
        boolean z = false;
        boolean z2 = !android.content.pm.ActivityInfo.isRequiredContentUriPermissionRead(num.intValue()) || checkContentUriPermissionFullUnlocked(grantUri, i, 1);
        boolean z3 = !android.content.pm.ActivityInfo.isRequiredContentUriPermissionWrite(num.intValue()) || checkContentUriPermissionFullUnlocked(grantUri, i, 2);
        if (num.intValue() != 3 ? !(!z2 || !z3) : !(!z2 && !z3)) {
            z = true;
        }
        if (!z) {
            throw new java.lang.SecurityException("You can't launch this activity because you don't have the required " + android.content.pm.ActivityInfo.requiredContentUriPermissionToShortString(num.intValue()) + " access to " + grantUri.uri);
        }
    }

    private void enforceRequireContentUriPermissionFromCallerOnIntentExtraStream(android.content.Intent intent, int i, int i2, int i3, java.lang.Integer num) {
        try {
            android.net.Uri uri = (android.net.Uri) intent.getParcelableExtra("android.intent.extra.STREAM", android.net.Uri.class);
            if (uri != null) {
                enforceRequireContentUriPermissionFromCaller(num, com.android.server.uri.GrantUri.resolve(i, uri, i2), i3);
            }
        } catch (android.os.BadParcelableException e) {
            android.util.Slog.w(TAG, "Failed to unparcel an URI in EXTRA_STREAM, skipping requireContentUriPermissionFromCaller: " + e);
        }
        try {
            java.util.ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("android.intent.extra.STREAM", android.net.Uri.class);
            if (parcelableArrayListExtra != null) {
                for (int size = parcelableArrayListExtra.size() - 1; size >= 0; size--) {
                    enforceRequireContentUriPermissionFromCaller(num, com.android.server.uri.GrantUri.resolve(i, (android.net.Uri) parcelableArrayListExtra.get(size), i2), i3);
                }
            }
        } catch (android.os.BadParcelableException e2) {
            android.util.Slog.w(TAG, "Failed to unparcel an ArrayList of URIs in EXTRA_STREAM, skipping requireContentUriPermissionFromCaller: " + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void readGrantedUriPermissionsLocked() {
        java.lang.String str;
        java.io.FileInputStream fileInputStream;
        long j;
        int attributeInt;
        android.content.pm.ProviderInfo providerInfo;
        java.lang.String str2 = "Failed reading Uri grants";
        java.lang.String str3 = TAG;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.io.FileInputStream fileInputStream2 = null;
        java.lang.String str4 = null;
        try {
            try {
                try {
                } catch (java.io.FileNotFoundException e) {
                    fileInputStream2 = null;
                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                    e = e2;
                    str = "Failed reading Uri grants";
                    fileInputStream2 = null;
                } catch (java.lang.Throwable th) {
                    th = th;
                    fileInputStream2 = null;
                }
            } catch (java.io.IOException e3) {
                e = e3;
            }
            try {
                java.io.FileInputStream openRead = this.mGrantFile.openRead();
                try {
                    try {
                        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                        while (true) {
                            int next = resolvePullParser.next();
                            if (next == 1) {
                                libcore.io.IoUtils.closeQuietly(openRead);
                                return;
                            }
                            java.lang.String name = resolvePullParser.getName();
                            if (next != 2) {
                                str = str2;
                                j = currentTimeMillis;
                                fileInputStream = openRead;
                            } else if (TAG_URI_GRANT.equals(name)) {
                                int attributeInt2 = resolvePullParser.getAttributeInt(str4, ATTR_USER_HANDLE, com.android.server.am.ProcessList.INVALID_ADJ);
                                if (attributeInt2 != -10000) {
                                    attributeInt = attributeInt2;
                                } else {
                                    attributeInt2 = resolvePullParser.getAttributeInt(str4, ATTR_SOURCE_USER_ID);
                                    attributeInt = resolvePullParser.getAttributeInt(str4, ATTR_TARGET_USER_ID);
                                }
                                java.lang.String attributeValue = resolvePullParser.getAttributeValue(str4, ATTR_SOURCE_PKG);
                                java.lang.String attributeValue2 = resolvePullParser.getAttributeValue(str4, ATTR_TARGET_PKG);
                                android.net.Uri parse = android.net.Uri.parse(resolvePullParser.getAttributeValue(str4, ATTR_URI));
                                boolean attributeBoolean = resolvePullParser.getAttributeBoolean(str4, ATTR_PREFIX, false);
                                int attributeInt3 = resolvePullParser.getAttributeInt(str4, ATTR_MODE_FLAGS);
                                str = str2;
                                java.lang.String str5 = str3;
                                try {
                                    try {
                                        long attributeLong = resolvePullParser.getAttributeLong(str4, ATTR_CREATED_TIME, currentTimeMillis);
                                        j = currentTimeMillis;
                                        providerInfo = getProviderInfo(parse.getAuthority(), attributeInt2, 786432, 1000);
                                        if (providerInfo != null) {
                                            try {
                                                if (attributeValue.equals(providerInfo.packageName)) {
                                                    fileInputStream = openRead;
                                                    try {
                                                        try {
                                                            try {
                                                                int packageUid = this.mPmInternal.getPackageUid(attributeValue2, 8192L, attributeInt);
                                                                if (packageUid != -1) {
                                                                    findOrCreateUriPermissionLocked(attributeValue, attributeValue2, packageUid, new com.android.server.uri.GrantUri(attributeInt2, parse, attributeBoolean ? 128 : 0)).initPersistedModes(attributeInt3, attributeLong);
                                                                    this.mPmInternal.grantImplicitAccess(attributeInt, null, android.os.UserHandle.getAppId(packageUid), providerInfo.applicationInfo.uid, false, true);
                                                                }
                                                                str3 = str5;
                                                            } catch (java.io.FileNotFoundException e4) {
                                                                fileInputStream2 = fileInputStream;
                                                                libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                                            } catch (java.lang.Throwable th2) {
                                                                th = th2;
                                                                fileInputStream2 = fileInputStream;
                                                                libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                                                throw th;
                                                            }
                                                        } catch (java.io.IOException e5) {
                                                            e = e5;
                                                            str2 = str;
                                                            str3 = str5;
                                                            fileInputStream2 = fileInputStream;
                                                            android.util.Slog.wtf(str3, str2, e);
                                                            libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                                        }
                                                    } catch (org.xmlpull.v1.XmlPullParserException e6) {
                                                        e = e6;
                                                        str3 = str5;
                                                        fileInputStream2 = fileInputStream;
                                                        android.util.Slog.wtf(str3, str, e);
                                                        libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                                    }
                                                }
                                            } catch (java.io.IOException e7) {
                                                e = e7;
                                                fileInputStream = openRead;
                                            }
                                        }
                                        fileInputStream = openRead;
                                    } catch (org.xmlpull.v1.XmlPullParserException e8) {
                                        e = e8;
                                        fileInputStream = openRead;
                                    }
                                    try {
                                        str3 = str5;
                                        try {
                                            android.util.Slog.w(str3, "Persisted grant for " + parse + " had source " + attributeValue + " but instead found " + providerInfo);
                                        } catch (java.io.IOException e9) {
                                            e = e9;
                                            str2 = str;
                                            fileInputStream2 = fileInputStream;
                                            android.util.Slog.wtf(str3, str2, e);
                                            libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                        } catch (org.xmlpull.v1.XmlPullParserException e10) {
                                            e = e10;
                                            fileInputStream2 = fileInputStream;
                                            android.util.Slog.wtf(str3, str, e);
                                            libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                        }
                                    } catch (java.io.IOException e11) {
                                        e = e11;
                                        str3 = str5;
                                        str2 = str;
                                        fileInputStream2 = fileInputStream;
                                        android.util.Slog.wtf(str3, str2, e);
                                        libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                    }
                                } catch (java.io.IOException e12) {
                                    e = e12;
                                    fileInputStream = openRead;
                                }
                            } else {
                                str = str2;
                                j = currentTimeMillis;
                                fileInputStream = openRead;
                            }
                            str2 = str;
                            currentTimeMillis = j;
                            openRead = fileInputStream;
                            str4 = null;
                        }
                    } catch (java.io.FileNotFoundException e13) {
                        fileInputStream = openRead;
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        fileInputStream = openRead;
                    }
                } catch (java.io.IOException e14) {
                    e = e14;
                    fileInputStream = openRead;
                } catch (org.xmlpull.v1.XmlPullParserException e15) {
                    e = e15;
                    str = str2;
                    fileInputStream = openRead;
                }
            } catch (java.io.IOException e16) {
                e = e16;
                fileInputStream2 = null;
                android.util.Slog.wtf(str3, str2, e);
                libcore.io.IoUtils.closeQuietly(fileInputStream2);
            }
        } catch (java.lang.Throwable th4) {
            th = th4;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.uri.UriPermission findOrCreateUriPermissionLocked(java.lang.String str, java.lang.String str2, int i, com.android.server.uri.GrantUri grantUri) {
        android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> arrayMap = this.mGrantedUriPermissions.get(i);
        if (arrayMap == null) {
            arrayMap = com.google.android.collect.Maps.newArrayMap();
            this.mGrantedUriPermissions.put(i, arrayMap);
        }
        com.android.server.uri.UriPermission uriPermission = arrayMap.get(grantUri);
        if (uriPermission == null) {
            com.android.server.uri.UriPermission uriPermission2 = new com.android.server.uri.UriPermission(str, str2, i, grantUri);
            arrayMap.put(grantUri, uriPermission2);
            return uriPermission2;
        }
        return uriPermission;
    }

    private void grantUriPermissionUnchecked(int i, java.lang.String str, com.android.server.uri.GrantUri grantUri, int i2, com.android.server.uri.UriPermissionOwner uriPermissionOwner) {
        com.android.server.uri.UriPermission findOrCreateUriPermissionLocked;
        if (!android.content.Intent.isAccessUriMode(i2)) {
            return;
        }
        android.content.pm.ProviderInfo providerInfo = getProviderInfo(grantUri.uri.getAuthority(), grantUri.sourceUserId, 268435456, 1000);
        if (providerInfo == null) {
            android.util.Slog.w(TAG, "No content provider found for grant: " + grantUri.toSafeString());
            return;
        }
        synchronized (this.mLock) {
            findOrCreateUriPermissionLocked = findOrCreateUriPermissionLocked(providerInfo.packageName, str, i, grantUri);
        }
        findOrCreateUriPermissionLocked.grantModes(i2, uriPermissionOwner);
        this.mPmInternal.grantImplicitAccess(android.os.UserHandle.getUserId(i), null, android.os.UserHandle.getAppId(i), providerInfo.applicationInfo.uid, false, (i2 & 64) != 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void grantUriPermissionUncheckedFromIntent(com.android.server.uri.NeededUriGrants neededUriGrants, com.android.server.uri.UriPermissionOwner uriPermissionOwner) {
        if (neededUriGrants == null) {
            return;
        }
        int size = neededUriGrants.uris.size();
        for (int i = 0; i < size; i++) {
            grantUriPermissionUnchecked(neededUriGrants.targetUid, neededUriGrants.targetPkg, neededUriGrants.uris.valueAt(i), neededUriGrants.flags, uriPermissionOwner);
        }
    }

    private void grantUriPermissionUnlocked(int i, java.lang.String str, com.android.server.uri.GrantUri grantUri, int i2, com.android.server.uri.UriPermissionOwner uriPermissionOwner, int i3) {
        if (str == null) {
            throw new java.lang.NullPointerException(ATTR_TARGET_PKG);
        }
        int checkGrantUriPermissionUnlocked = checkGrantUriPermissionUnlocked(i, str, grantUri, i2, this.mPmInternal.getPackageUid(str, 268435456L, i3));
        if (checkGrantUriPermissionUnlocked < 0) {
            return;
        }
        grantUriPermissionUnchecked(checkGrantUriPermissionUnlocked, str, grantUri, i2, uriPermissionOwner);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void revokeUriPermission(java.lang.String str, int i, com.android.server.uri.GrantUri grantUri, int i2) {
        android.content.pm.ProviderInfo providerInfo = getProviderInfo(grantUri.uri.getAuthority(), grantUri.sourceUserId, 786432, i);
        if (providerInfo == null) {
            android.util.Slog.w(TAG, "No content provider found for permission revoke: " + grantUri.toSafeString());
            return;
        }
        boolean checkHoldingPermissionsUnlocked = checkHoldingPermissionsUnlocked(providerInfo, grantUri, i, i2);
        synchronized (this.mLock) {
            revokeUriPermissionLocked(str, i, grantUri, i2, checkHoldingPermissionsUnlocked);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void revokeUriPermissionLocked(java.lang.String str, int i, com.android.server.uri.GrantUri grantUri, int i2, boolean z) {
        if (z) {
            boolean z2 = false;
            for (int size = this.mGrantedUriPermissions.size() - 1; size >= 0; size--) {
                this.mGrantedUriPermissions.keyAt(size);
                android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> valueAt = this.mGrantedUriPermissions.valueAt(size);
                for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                    com.android.server.uri.UriPermission valueAt2 = valueAt.valueAt(size2);
                    if ((str == null || str.equals(valueAt2.targetPkg)) && valueAt2.uri.sourceUserId == grantUri.sourceUserId && valueAt2.uri.uri.isPathPrefixMatch(grantUri.uri)) {
                        z2 |= valueAt2.revokeModes(i2 | 64, str == null);
                        if (valueAt2.modeFlags == 0) {
                            valueAt.removeAt(size2);
                        }
                    }
                }
                if (valueAt.isEmpty()) {
                    this.mGrantedUriPermissions.removeAt(size);
                }
            }
            if (z2) {
                schedulePersistUriGrants();
                return;
            }
            return;
        }
        android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> arrayMap = this.mGrantedUriPermissions.get(i);
        if (arrayMap != null) {
            boolean z3 = false;
            for (int size3 = arrayMap.size() - 1; size3 >= 0; size3--) {
                com.android.server.uri.UriPermission valueAt3 = arrayMap.valueAt(size3);
                if ((str == null || str.equals(valueAt3.targetPkg)) && valueAt3.uri.sourceUserId == grantUri.sourceUserId && valueAt3.uri.uri.isPathPrefixMatch(grantUri.uri)) {
                    z3 |= valueAt3.revokeModes(i2 | 64, false);
                    if (valueAt3.modeFlags == 0) {
                        arrayMap.removeAt(size3);
                    }
                }
            }
            if (arrayMap.isEmpty()) {
                this.mGrantedUriPermissions.remove(i);
            }
            if (z3) {
                schedulePersistUriGrants();
            }
        }
    }

    private boolean checkHoldingPermissionsUnlocked(android.content.pm.ProviderInfo providerInfo, com.android.server.uri.GrantUri grantUri, int i, int i2) {
        if (android.os.UserHandle.getUserId(i) != grantUri.sourceUserId && checkComponentPermission("android.permission.INTERACT_ACROSS_USERS", i, -1, true) != 0) {
            return false;
        }
        return checkHoldingPermissionsInternalUnlocked(providerInfo, grantUri, i, i2, true);
    }

    private boolean checkHoldingPermissionsInternalUnlocked(android.content.pm.ProviderInfo providerInfo, com.android.server.uri.GrantUri grantUri, int i, int i2, boolean z) {
        boolean z2;
        java.lang.String writePermission;
        java.lang.String readPermission;
        if (java.lang.Thread.holdsLock(this.mLock)) {
            throw new java.lang.IllegalStateException("Must never hold local mLock");
        }
        if (providerInfo.applicationInfo.uid == i) {
            return true;
        }
        if (!providerInfo.exported) {
            return false;
        }
        boolean z3 = (i2 & 1) == 0;
        boolean z4 = (i2 & 2) == 0;
        if (!z3 && providerInfo.readPermission != null && z && checkUidPermission(providerInfo.readPermission, i) == 0) {
            z3 = true;
        }
        if (!z4 && providerInfo.writePermission != null && z && checkUidPermission(providerInfo.writePermission, i) == 0) {
            z4 = true;
        }
        boolean z5 = providerInfo.readPermission == null;
        boolean z6 = providerInfo.writePermission == null;
        android.content.pm.PathPermission[] pathPermissionArr = providerInfo.pathPermissions;
        if (pathPermissionArr != null) {
            java.lang.String path = grantUri.uri.getPath();
            int length = pathPermissionArr.length;
            while (length > 0 && (!z3 || !z4)) {
                length--;
                android.content.pm.PathPermission pathPermission = pathPermissionArr[length];
                if (pathPermission.match(path)) {
                    if (!z3 && (readPermission = pathPermission.getReadPermission()) != null) {
                        if (z && checkUidPermission(readPermission, i) == 0) {
                            z3 = true;
                        } else {
                            z5 = false;
                        }
                    }
                    if (!z4 && (writePermission = pathPermission.getWritePermission()) != null) {
                        if (z && checkUidPermission(writePermission, i) == 0) {
                            z4 = true;
                        } else {
                            z6 = false;
                        }
                    }
                }
            }
        }
        if (z5) {
            z3 = true;
        }
        if (z6) {
            z4 = true;
        }
        if (providerInfo.forceUriPermissions) {
            int userId = android.os.UserHandle.getUserId(providerInfo.applicationInfo.uid);
            if (userId == android.os.UserHandle.getUserId(i)) {
                z2 = this.mAmInternal.checkContentProviderUriPermission(grantUri.uri, userId, i, i2) == 0;
            } else {
                z2 = false;
            }
        } else {
            z2 = true;
        }
        return z3 && z4 && z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeUriPermissionIfNeededLocked(com.android.server.uri.UriPermission uriPermission) {
        android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> arrayMap;
        if (uriPermission.modeFlags != 0 || (arrayMap = this.mGrantedUriPermissions.get(uriPermission.targetUid)) == null) {
            return;
        }
        arrayMap.remove(uriPermission.uri);
        if (arrayMap.isEmpty()) {
            this.mGrantedUriPermissions.remove(uriPermission.targetUid);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.uri.UriPermission findUriPermissionLocked(int i, com.android.server.uri.GrantUri grantUri) {
        android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> arrayMap = this.mGrantedUriPermissions.get(i);
        if (arrayMap != null) {
            return arrayMap.get(grantUri);
        }
        return null;
    }

    private void schedulePersistUriGrants() {
        if (!this.mH.hasMessages(1)) {
            this.mH.sendMessageDelayed(this.mH.obtainMessage(1), com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceNotIsolatedCaller(java.lang.String str) {
        if (android.os.UserHandle.isIsolated(android.os.Binder.getCallingUid())) {
            throw new java.lang.SecurityException("Isolated process not allowed to call " + str);
        }
    }

    private android.content.pm.ProviderInfo getProviderInfo(java.lang.String str, int i, int i2, int i3) {
        return this.mPmInternal.resolveContentProvider(str, i2 | 2048, i, i3);
    }

    private int checkGrantUriPermissionUnlocked(int i, java.lang.String str, com.android.server.uri.GrantUri grantUri, int i2, int i3) {
        int i4;
        boolean z;
        boolean checkUriPermissionLocked;
        if (!isContentUriWithAccessModeFlags(grantUri, i2, "grant URI permission")) {
            return -1;
        }
        int appId = android.os.UserHandle.getAppId(i);
        if ((appId != 1000 && appId != 0) || "com.android.settings.files".equals(grantUri.uri.getAuthority()) || "com.android.settings.module_licenses".equals(grantUri.uri.getAuthority())) {
            android.content.pm.ProviderInfo providerInfo = getProviderInfo(grantUri.uri.getAuthority(), grantUri.sourceUserId, 268435456, i);
            if (providerInfo == null) {
                android.util.Slog.w(TAG, "No content provider found for permission check: " + grantUri.uri.toSafeString());
                return -1;
            }
            if (i3 < 0 && str != null) {
                int packageUid = this.mPmInternal.getPackageUid(str, 268435456L, android.os.UserHandle.getUserId(i));
                if (packageUid < 0) {
                    return -1;
                }
                i4 = packageUid;
            } else {
                i4 = i3;
            }
            boolean z2 = true;
            if (i4 >= 0) {
                if (checkHoldingPermissionsUnlocked(providerInfo, grantUri, i4, i2)) {
                    z = true;
                }
                z = false;
            } else {
                boolean z3 = providerInfo.exported;
                int i5 = i2 & 1;
                if (i5 != 0 && providerInfo.readPermission != null) {
                    z3 = false;
                }
                int i6 = i2 & 2;
                if (i6 != 0 && providerInfo.writePermission != null) {
                    z3 = false;
                }
                if (providerInfo.pathPermissions != null) {
                    int length = providerInfo.pathPermissions.length;
                    int i7 = 0;
                    while (true) {
                        if (i7 >= length) {
                            break;
                        }
                        if (providerInfo.pathPermissions[i7] == null || !providerInfo.pathPermissions[i7].match(grantUri.uri.getPath())) {
                            i7++;
                        } else {
                            if (i5 != 0 && providerInfo.pathPermissions[i7].getReadPermission() != null) {
                                z3 = false;
                            }
                            if (i6 != 0 && providerInfo.pathPermissions[i7].getWritePermission() != null) {
                                z3 = false;
                            }
                        }
                    }
                }
                if (z3) {
                    z = true;
                }
                z = false;
            }
            if (providerInfo.forceUriPermissions) {
                z = false;
            }
            boolean z4 = (i2 & 192) == 0;
            if (z4 && z) {
                this.mPmInternal.grantImplicitAccess(android.os.UserHandle.getUserId(i4), null, android.os.UserHandle.getAppId(i4), providerInfo.applicationInfo.uid, false);
                return -1;
            }
            boolean z5 = i4 >= 0 && android.os.UserHandle.getUserId(i4) != grantUri.sourceUserId && checkHoldingPermissionsInternalUnlocked(providerInfo, grantUri, i, i2, false);
            boolean z6 = providerInfo.grantUriPermissions;
            if (com.android.internal.util.ArrayUtils.isEmpty(providerInfo.uriPermissionPatterns)) {
                z2 = z6;
            } else {
                int length2 = providerInfo.uriPermissionPatterns.length;
                int i8 = 0;
                while (true) {
                    if (i8 >= length2) {
                        z2 = false;
                        break;
                    }
                    if (providerInfo.uriPermissionPatterns[i8] != null && providerInfo.uriPermissionPatterns[i8].match(grantUri.uri.getPath())) {
                        break;
                    }
                    i8++;
                }
            }
            if (!z2) {
                if (z5) {
                    if (!z4) {
                        throw new java.lang.SecurityException("Provider " + providerInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + providerInfo.name + " does not allow granting of advanced Uri permissions (uri " + grantUri + ")");
                    }
                } else {
                    throw new java.lang.SecurityException("Provider " + providerInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + providerInfo.name + " does not allow granting of Uri permissions (uri " + grantUri + ")");
                }
            }
            if (!checkHoldingPermissionsUnlocked(providerInfo, grantUri, i, i2)) {
                synchronized (this.mLock) {
                    checkUriPermissionLocked = checkUriPermissionLocked(grantUri, i, i2);
                }
                if (!checkUriPermissionLocked) {
                    if ("android.permission.MANAGE_DOCUMENTS".equals(providerInfo.readPermission)) {
                        throw new java.lang.SecurityException("UID " + i + " does not have permission to " + grantUri + "; you could obtain access using ACTION_OPEN_DOCUMENT or related APIs");
                    }
                    throw new java.lang.SecurityException("UID " + i + " does not have permission to " + grantUri);
                }
            }
            return i4;
        }
        android.util.Slog.w(TAG, "For security reasons, the system cannot issue a Uri permission grant to " + grantUri + "; use startActivityAsCaller() instead");
        return -1;
    }

    private boolean isContentUriWithAccessModeFlags(com.android.server.uri.GrantUri grantUri, int i, java.lang.String str) {
        return android.content.Intent.isAccessUriMode(i) && com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(grantUri.uri.getScheme());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkContentUriPermissionFullUnlocked(com.android.server.uri.GrantUri grantUri, int i, int i2) {
        boolean checkUriPermissionLocked;
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Uid must be positive for the content URI permission check of " + grantUri.uri.toSafeString());
        }
        if (!isContentUriWithAccessModeFlags(grantUri, i2, "check content URI permission")) {
            throw new java.lang.IllegalArgumentException("The URI must be a content URI and the mode flags must be at least read and/or write for the content URI permission check of " + grantUri.uri.toSafeString());
        }
        int appId = android.os.UserHandle.getAppId(i);
        if (appId == 1000 || appId == 0) {
            return true;
        }
        android.content.pm.ProviderInfo providerInfo = getProviderInfo(grantUri.uri.getAuthority(), grantUri.sourceUserId, 268435456, i);
        if (providerInfo == null) {
            android.util.Slog.w(TAG, "No content provider found for content URI permission check: " + grantUri.uri.toSafeString());
            return false;
        }
        if (checkHoldingPermissionsUnlocked(providerInfo, grantUri, i, i2)) {
            return true;
        }
        synchronized (this.mLock) {
            checkUriPermissionLocked = checkUriPermissionLocked(grantUri, i, i2);
        }
        return checkUriPermissionLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int checkGrantUriPermissionUnlocked(int i, java.lang.String str, android.net.Uri uri, int i2, int i3) {
        return checkGrantUriPermissionUnlocked(i, str, new com.android.server.uri.GrantUri(i3, uri, i2), i2, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean checkUriPermissionLocked(com.android.server.uri.GrantUri grantUri, int i, int i2) {
        int i3 = (i2 & 64) != 0 ? 3 : 1;
        if (i == 0) {
            return true;
        }
        android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> arrayMap = this.mGrantedUriPermissions.get(i);
        if (arrayMap == null) {
            return false;
        }
        com.android.server.uri.UriPermission uriPermission = arrayMap.get(grantUri);
        if (uriPermission != null && uriPermission.getStrength(i2) >= i3) {
            return true;
        }
        int size = arrayMap.size();
        for (int i4 = 0; i4 < size; i4++) {
            com.android.server.uri.UriPermission valueAt = arrayMap.valueAt(i4);
            if (valueAt.uri.prefix && grantUri.uri.isPathPrefixMatch(valueAt.uri.uri) && valueAt.getStrength(i2) >= i3) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS_FULL")
    public int checkGrantUriPermission_ignoreNonSystem(int i, java.lang.String str, android.net.Uri uri, int i2, int i3) {
        if (!isCallerIsSystemOrPrivileged()) {
            return -1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return checkGrantUriPermissionUnlocked(i, str, uri, i2, i3);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean isCallerIsSystemOrPrivileged() {
        int callingUid = android.os.Binder.getCallingUid();
        return callingUid == 1000 || callingUid == 0 || checkComponentPermission("android.permission.INTERACT_ACROSS_USERS_FULL", callingUid, -1, true) == 0;
    }

    @Override // com.android.server.uri.UriMetricsHelper.PersistentUriGrantsProvider
    public java.util.ArrayList<com.android.server.uri.UriPermission> providePersistentUriGrants() {
        java.util.ArrayList<com.android.server.uri.UriPermission> arrayList = new java.util.ArrayList<>();
        synchronized (this.mLock) {
            try {
                int size = this.mGrantedUriPermissions.size();
                for (int i = 0; i < size; i++) {
                    android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> valueAt = this.mGrantedUriPermissions.valueAt(i);
                    int size2 = valueAt.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        com.android.server.uri.UriPermission valueAt2 = valueAt.valueAt(i2);
                        if (valueAt2.persistedModeFlags != 0) {
                            arrayList.add(valueAt2);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeGrantedUriPermissions() {
        int i;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
        synchronized (this.mLock) {
            try {
                int size = this.mGrantedUriPermissions.size();
                i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    android.util.ArrayMap<com.android.server.uri.GrantUri, com.android.server.uri.UriPermission> valueAt = this.mGrantedUriPermissions.valueAt(i2);
                    int size2 = valueAt.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        com.android.server.uri.UriPermission valueAt2 = valueAt.valueAt(i3);
                        if (valueAt2.persistedModeFlags != 0) {
                            i++;
                            newArrayList.add(valueAt2.snapshot());
                        }
                    }
                }
            } finally {
            }
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.io.FileOutputStream startWrite = this.mGrantFile.startWrite(uptimeMillis);
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, TAG_URI_GRANTS);
                java.util.Iterator it = newArrayList.iterator();
                while (it.hasNext()) {
                    com.android.server.uri.UriPermission.Snapshot snapshot = (com.android.server.uri.UriPermission.Snapshot) it.next();
                    resolveSerializer.startTag((java.lang.String) null, TAG_URI_GRANT);
                    resolveSerializer.attributeInt((java.lang.String) null, ATTR_SOURCE_USER_ID, snapshot.uri.sourceUserId);
                    resolveSerializer.attributeInt((java.lang.String) null, ATTR_TARGET_USER_ID, snapshot.targetUserId);
                    resolveSerializer.attributeInterned((java.lang.String) null, ATTR_SOURCE_PKG, snapshot.sourcePkg);
                    resolveSerializer.attributeInterned((java.lang.String) null, ATTR_TARGET_PKG, snapshot.targetPkg);
                    resolveSerializer.attribute((java.lang.String) null, ATTR_URI, java.lang.String.valueOf(snapshot.uri.uri));
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(resolveSerializer, ATTR_PREFIX, snapshot.uri.prefix);
                    resolveSerializer.attributeInt((java.lang.String) null, ATTR_MODE_FLAGS, snapshot.persistedModeFlags);
                    resolveSerializer.attributeLong((java.lang.String) null, ATTR_CREATED_TIME, snapshot.persistedCreateTime);
                    resolveSerializer.endTag((java.lang.String) null, TAG_URI_GRANT);
                }
                resolveSerializer.endTag((java.lang.String) null, TAG_URI_GRANTS);
                resolveSerializer.endDocument();
                this.mGrantFile.finishWrite(startWrite);
            } catch (java.io.IOException e) {
                fileOutputStream = startWrite;
                if (fileOutputStream != null) {
                    this.mGrantFile.failWrite(fileOutputStream);
                }
                this.mMetricsHelper.reportPersistentUriFlushed(i);
            }
        } catch (java.io.IOException e2) {
        }
        this.mMetricsHelper.reportPersistentUriFlushed(i);
    }

    final class H extends android.os.Handler {
        static final int PERSIST_URI_GRANTS_MSG = 1;

        public H(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.uri.UriGrantsManagerService.this.writeGrantedUriPermissions();
                    break;
            }
        }
    }

    private final class LocalService implements com.android.server.uri.UriGrantsManagerInternal {
        private LocalService() {
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public void removeUriPermissionIfNeeded(com.android.server.uri.UriPermission uriPermission) {
            synchronized (com.android.server.uri.UriGrantsManagerService.this.mLock) {
                com.android.server.uri.UriGrantsManagerService.this.removeUriPermissionIfNeededLocked(uriPermission);
            }
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public void revokeUriPermission(java.lang.String str, int i, com.android.server.uri.GrantUri grantUri, int i2) {
            com.android.server.uri.UriGrantsManagerService.this.revokeUriPermission(str, i, grantUri, i2);
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public boolean checkUriPermission(com.android.server.uri.GrantUri grantUri, int i, int i2, boolean z) {
            boolean checkUriPermissionLocked;
            if (z) {
                return com.android.server.uri.UriGrantsManagerService.this.checkContentUriPermissionFullUnlocked(grantUri, i, i2);
            }
            synchronized (com.android.server.uri.UriGrantsManagerService.this.mLock) {
                checkUriPermissionLocked = com.android.server.uri.UriGrantsManagerService.this.checkUriPermissionLocked(grantUri, i, i2);
            }
            return checkUriPermissionLocked;
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public int checkGrantUriPermission(int i, java.lang.String str, android.net.Uri uri, int i2, int i3) {
            com.android.server.uri.UriGrantsManagerService.this.enforceNotIsolatedCaller("checkGrantUriPermission");
            return com.android.server.uri.UriGrantsManagerService.this.checkGrantUriPermissionUnlocked(i, str, uri, i2, i3);
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public com.android.server.uri.NeededUriGrants checkGrantUriPermissionFromIntent(android.content.Intent intent, int i, java.lang.String str, int i2) {
            return internalCheckGrantUriPermissionFromIntent(intent, i, str, i2, null);
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public com.android.server.uri.NeededUriGrants checkGrantUriPermissionFromIntent(android.content.Intent intent, int i, java.lang.String str, int i2, int i3) {
            return internalCheckGrantUriPermissionFromIntent(intent, i, str, i2, java.lang.Integer.valueOf(i3));
        }

        private com.android.server.uri.NeededUriGrants internalCheckGrantUriPermissionFromIntent(android.content.Intent intent, int i, java.lang.String str, int i2, @android.annotation.Nullable java.lang.Integer num) {
            return com.android.server.uri.UriGrantsManagerService.this.checkGrantUriPermissionFromIntentUnlocked(i, str, intent, intent != null ? intent.getFlags() : 0, null, i2, num);
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public void grantUriPermissionUncheckedFromIntent(com.android.server.uri.NeededUriGrants neededUriGrants, com.android.server.uri.UriPermissionOwner uriPermissionOwner) {
            com.android.server.uri.UriGrantsManagerService.this.grantUriPermissionUncheckedFromIntent(neededUriGrants, uriPermissionOwner);
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public void onSystemReady() {
            synchronized (com.android.server.uri.UriGrantsManagerService.this.mLock) {
                com.android.server.uri.UriGrantsManagerService.this.readGrantedUriPermissionsLocked();
            }
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public android.os.IBinder newUriPermissionOwner(java.lang.String str) {
            com.android.server.uri.UriGrantsManagerService.this.enforceNotIsolatedCaller("newUriPermissionOwner");
            return new com.android.server.uri.UriPermissionOwner(this, str).getExternalToken();
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public void removeUriPermissionsForPackage(java.lang.String str, int i, boolean z, boolean z2) {
            synchronized (com.android.server.uri.UriGrantsManagerService.this.mLock) {
                com.android.server.uri.UriGrantsManagerService.this.removeUriPermissionsForPackageLocked(str, i, z, z2);
            }
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public void revokeUriPermissionFromOwner(android.os.IBinder iBinder, android.net.Uri uri, int i, int i2) {
            revokeUriPermissionFromOwner(iBinder, uri, i, i2, null, -1);
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public void revokeUriPermissionFromOwner(android.os.IBinder iBinder, android.net.Uri uri, int i, int i2, java.lang.String str, int i3) {
            com.android.server.uri.UriPermissionOwner fromExternalToken = com.android.server.uri.UriPermissionOwner.fromExternalToken(iBinder);
            if (fromExternalToken == null) {
                throw new java.lang.IllegalArgumentException("Unknown owner: " + iBinder);
            }
            fromExternalToken.removeUriPermission(uri == null ? null : new com.android.server.uri.GrantUri(i2, uri, i), i, str, i3);
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public boolean checkAuthorityGrants(int i, android.content.pm.ProviderInfo providerInfo, int i2, boolean z) {
            boolean checkAuthorityGrantsLocked;
            synchronized (com.android.server.uri.UriGrantsManagerService.this.mLock) {
                checkAuthorityGrantsLocked = com.android.server.uri.UriGrantsManagerService.this.checkAuthorityGrantsLocked(i, providerInfo, i2, z);
            }
            return checkAuthorityGrantsLocked;
        }

        @Override // com.android.server.uri.UriGrantsManagerInternal
        public void dump(java.io.PrintWriter printWriter, boolean z, java.lang.String str) {
            int i;
            synchronized (com.android.server.uri.UriGrantsManagerService.this.mLock) {
                try {
                    int i2 = 0;
                    if (com.android.server.uri.UriGrantsManagerService.this.mGrantedUriPermissions.size() > 0) {
                        if (str == null) {
                            i = -2;
                        } else {
                            i = com.android.server.uri.UriGrantsManagerService.this.mPmInternal.getPackageUid(str, 4194304L, 0);
                        }
                        int i3 = 0;
                        boolean z2 = false;
                        boolean z3 = false;
                        while (i2 < com.android.server.uri.UriGrantsManagerService.this.mGrantedUriPermissions.size()) {
                            int keyAt = com.android.server.uri.UriGrantsManagerService.this.mGrantedUriPermissions.keyAt(i2);
                            if (i < -1 || android.os.UserHandle.getAppId(keyAt) == i) {
                                android.util.ArrayMap arrayMap = (android.util.ArrayMap) com.android.server.uri.UriGrantsManagerService.this.mGrantedUriPermissions.valueAt(i2);
                                if (!z2) {
                                    if (z3) {
                                        printWriter.println();
                                    }
                                    printWriter.println("  Granted Uri Permissions:");
                                    i3 = 1;
                                    z2 = true;
                                    z3 = true;
                                }
                                printWriter.print("  * UID ");
                                printWriter.print(keyAt);
                                printWriter.println(" holds:");
                                for (com.android.server.uri.UriPermission uriPermission : arrayMap.values()) {
                                    printWriter.print("    ");
                                    printWriter.println(uriPermission);
                                    if (z) {
                                        uriPermission.dump(printWriter, "      ");
                                    }
                                }
                            }
                            i2++;
                        }
                        i2 = i3;
                    }
                    if (i2 == 0) {
                        printWriter.println("  (nothing)");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
