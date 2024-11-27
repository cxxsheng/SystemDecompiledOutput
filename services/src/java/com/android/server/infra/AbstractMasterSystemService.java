package com.android.server.infra;

/* loaded from: classes2.dex */
public abstract class AbstractMasterSystemService<M extends com.android.server.infra.AbstractMasterSystemService<M, S>, S extends com.android.server.infra.AbstractPerUserSystemService<S, M>> extends com.android.server.SystemService {
    public static final int PACKAGE_RESTART_POLICY_NO_REFRESH = 16;
    public static final int PACKAGE_RESTART_POLICY_REFRESH_EAGER = 64;
    public static final int PACKAGE_RESTART_POLICY_REFRESH_LAZY = 32;
    public static final int PACKAGE_UPDATE_POLICY_NO_REFRESH = 1;
    public static final int PACKAGE_UPDATE_POLICY_REFRESH_EAGER = 4;
    public static final int PACKAGE_UPDATE_POLICY_REFRESH_LAZY = 2;
    public boolean debug;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean mAllowInstantService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private final android.util.SparseBooleanArray mDisabledByUserRestriction;
    protected final java.lang.Object mLock;

    @android.annotation.Nullable
    protected final com.android.server.infra.ServiceNameResolver mServiceNameResolver;
    private final int mServicePackagePolicyFlags;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.List<S>> mServicesCacheList;
    protected final java.lang.String mTag;

    @android.annotation.Nullable
    private com.android.server.pm.UserManagerInternal mUm;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<java.lang.String> mUpdatingPackageNames;
    public boolean verbose;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServicePackagePolicyFlags {
    }

    public interface Visitor<S> {
        void visit(@android.annotation.NonNull S s);
    }

    @android.annotation.Nullable
    protected abstract S newServiceLocked(int i, boolean z);

    protected AbstractMasterSystemService(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable com.android.server.infra.ServiceNameResolver serviceNameResolver, @android.annotation.Nullable java.lang.String str) {
        this(context, serviceNameResolver, str, 34);
    }

    protected AbstractMasterSystemService(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable com.android.server.infra.ServiceNameResolver serviceNameResolver, @android.annotation.Nullable final java.lang.String str, int i) {
        super(context);
        this.mTag = getClass().getSimpleName();
        this.mLock = new java.lang.Object();
        this.verbose = false;
        this.debug = false;
        this.mServicesCacheList = new android.util.SparseArray<>();
        i = (i & 7) == 0 ? i | 2 : i;
        this.mServicePackagePolicyFlags = (i & 112) == 0 ? i | 32 : i;
        this.mServiceNameResolver = serviceNameResolver;
        if (this.mServiceNameResolver != null) {
            this.mServiceNameResolver.setOnTemporaryServiceNameChangedCallback(new com.android.server.infra.ServiceNameResolver.NameResolverListener() { // from class: com.android.server.infra.AbstractMasterSystemService$$ExternalSyntheticLambda0
                @Override // com.android.server.infra.ServiceNameResolver.NameResolverListener
                public final void onNameResolved(int i2, java.lang.String str2, boolean z) {
                    com.android.server.infra.AbstractMasterSystemService.this.onServiceNameChanged(i2, str2, z);
                }
            });
        }
        if (str == null) {
            this.mDisabledByUserRestriction = null;
        } else {
            this.mDisabledByUserRestriction = new android.util.SparseBooleanArray();
            com.android.server.pm.UserManagerInternal userManagerInternal = getUserManagerInternal();
            java.util.List<android.content.pm.UserInfo> supportedUsers = getSupportedUsers();
            for (int i2 = 0; i2 < supportedUsers.size(); i2++) {
                int i3 = supportedUsers.get(i2).id;
                boolean userRestriction = userManagerInternal.getUserRestriction(i3, str);
                if (userRestriction) {
                    android.util.Slog.i(this.mTag, "Disabling by restrictions user " + i3);
                    this.mDisabledByUserRestriction.put(i3, userRestriction);
                }
            }
            userManagerInternal.addUserRestrictionsListener(new com.android.server.pm.UserManagerInternal.UserRestrictionsListener() { // from class: com.android.server.infra.AbstractMasterSystemService$$ExternalSyntheticLambda1
                @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
                public final void onUserRestrictionsChanged(int i4, android.os.Bundle bundle, android.os.Bundle bundle2) {
                    com.android.server.infra.AbstractMasterSystemService.this.lambda$new$0(str, i4, bundle, bundle2);
                }
            });
        }
        startTrackingPackageChanges();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.lang.String str, int i, android.os.Bundle bundle, android.os.Bundle bundle2) {
        boolean z = bundle.getBoolean(str, false);
        synchronized (this.mLock) {
            try {
                if (this.mDisabledByUserRestriction.get(i) == z && this.debug) {
                    android.util.Slog.d(this.mTag, "Restriction did not change for user " + i);
                    return;
                }
                android.util.Slog.i(this.mTag, "Updating for user " + i + ": disabled=" + z);
                this.mDisabledByUserRestriction.put(i, z);
                updateCachedServiceLocked(i, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 600) {
            new com.android.server.infra.AbstractMasterSystemService.SettingsObserver(com.android.internal.os.BackgroundThread.getHandler());
        }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            updateCachedServiceLocked(targetUser.getUserIdentifier());
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            removeCachedServiceListLocked(targetUser.getUserIdentifier());
        }
    }

    public final boolean getAllowInstantService() {
        boolean z;
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            z = this.mAllowInstantService;
        }
        return z;
    }

    public final boolean isBindInstantServiceAllowed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mAllowInstantService;
        }
        return z;
    }

    public final void setAllowInstantService(boolean z) {
        android.util.Slog.i(this.mTag, "setAllowInstantService(): " + z);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            this.mAllowInstantService = z;
        }
    }

    public final void setTemporaryService(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        android.util.Slog.i(this.mTag, "setTemporaryService(" + i + ") to " + str + " for " + i2 + "ms");
        if (this.mServiceNameResolver == null) {
            return;
        }
        enforceCallingPermissionForManagement();
        java.util.Objects.requireNonNull(str);
        int maximumTemporaryServiceDurationMs = getMaximumTemporaryServiceDurationMs();
        if (i2 > maximumTemporaryServiceDurationMs) {
            throw new java.lang.IllegalArgumentException("Max duration is " + maximumTemporaryServiceDurationMs + " (called with " + i2 + ")");
        }
        synchronized (this.mLock) {
            try {
                S peekServiceForUserLocked = peekServiceForUserLocked(i);
                if (peekServiceForUserLocked != null) {
                    peekServiceForUserLocked.removeSelfFromCache();
                }
                this.mServiceNameResolver.setTemporaryService(i, str, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final void setTemporaryServices(int i, @android.annotation.NonNull java.lang.String[] strArr, int i2) {
        android.util.Slog.i(this.mTag, "setTemporaryService(" + i + ") to " + java.util.Arrays.toString(strArr) + " for " + i2 + "ms");
        if (this.mServiceNameResolver == null) {
            return;
        }
        enforceCallingPermissionForManagement();
        java.util.Objects.requireNonNull(strArr);
        int maximumTemporaryServiceDurationMs = getMaximumTemporaryServiceDurationMs();
        if (i2 > maximumTemporaryServiceDurationMs) {
            throw new java.lang.IllegalArgumentException("Max duration is " + maximumTemporaryServiceDurationMs + " (called with " + i2 + ")");
        }
        synchronized (this.mLock) {
            try {
                S peekServiceForUserLocked = peekServiceForUserLocked(i);
                if (peekServiceForUserLocked != null) {
                    peekServiceForUserLocked.removeSelfFromCache();
                }
                this.mServiceNameResolver.setTemporaryServices(i, strArr, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setDefaultServiceEnabled(int i, boolean z) {
        android.util.Slog.i(this.mTag, "setDefaultServiceEnabled() for userId " + i + ": " + z);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            try {
                if (this.mServiceNameResolver == null) {
                    return false;
                }
                if (!this.mServiceNameResolver.setDefaultServiceEnabled(i, z)) {
                    if (this.verbose) {
                        android.util.Slog.v(this.mTag, "setDefaultServiceEnabled(" + i + "): already " + z);
                    }
                    return false;
                }
                S peekServiceForUserLocked = peekServiceForUserLocked(i);
                if (peekServiceForUserLocked != null) {
                    peekServiceForUserLocked.removeSelfFromCache();
                }
                updateCachedServiceLocked(i);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isDefaultServiceEnabled(int i) {
        boolean isDefaultServiceEnabled;
        enforceCallingPermissionForManagement();
        if (this.mServiceNameResolver == null) {
            return false;
        }
        synchronized (this.mLock) {
            isDefaultServiceEnabled = this.mServiceNameResolver.isDefaultServiceEnabled(i);
        }
        return isDefaultServiceEnabled;
    }

    protected int getMaximumTemporaryServiceDurationMs() {
        throw new java.lang.UnsupportedOperationException("Not implemented by " + getClass());
    }

    public final void resetTemporaryService(int i) {
        android.util.Slog.i(this.mTag, "resetTemporaryService(): " + i);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            try {
                S serviceForUserLocked = getServiceForUserLocked(i);
                if (serviceForUserLocked != null) {
                    serviceForUserLocked.resetTemporaryServiceLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void enforceCallingPermissionForManagement() {
        throw new java.lang.UnsupportedOperationException("Not implemented by " + getClass());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    protected java.util.List<S> newServiceListLocked(int i, boolean z, java.lang.String[] strArr) {
        throw new java.lang.UnsupportedOperationException("newServiceListLocked not implemented. ");
    }

    protected void registerForExtraSettingsChanges(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull android.database.ContentObserver contentObserver) {
    }

    protected void onSettingsChanged(int i, @android.annotation.NonNull java.lang.String str) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public S getServiceForUserLocked(int i) {
        java.util.List<S> serviceListForUserLocked = getServiceListForUserLocked(i);
        if (serviceListForUserLocked == null || serviceListForUserLocked.size() == 0) {
            return null;
        }
        return serviceListForUserLocked.get(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected java.util.List<S> getServiceListForUserLocked(int i) {
        java.util.List arrayList;
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, null, null);
        java.util.List<S> list = this.mServicesCacheList.get(handleIncomingUser);
        if (list == null || list.size() == 0) {
            boolean isDisabledLocked = isDisabledLocked(i);
            if (this.mServiceNameResolver != null && this.mServiceNameResolver.isConfiguredInMultipleMode()) {
                arrayList = newServiceListLocked(handleIncomingUser, isDisabledLocked, this.mServiceNameResolver.getServiceNameList(i));
            } else {
                arrayList = new java.util.ArrayList();
                arrayList.add(newServiceLocked(handleIncomingUser, isDisabledLocked));
            }
            if (!isDisabledLocked) {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    onServiceEnabledLocked((com.android.server.infra.AbstractPerUserSystemService) arrayList.get(i2), handleIncomingUser);
                }
            }
            this.mServicesCacheList.put(i, arrayList);
            return arrayList;
        }
        return list;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    protected S peekServiceForUserLocked(int i) {
        java.util.List<S> peekServiceListForUserLocked = peekServiceListForUserLocked(i);
        if (peekServiceListForUserLocked == null || peekServiceListForUserLocked.size() == 0) {
            return null;
        }
        return peekServiceListForUserLocked.get(0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    protected java.util.List<S> peekServiceListForUserLocked(int i) {
        return this.mServicesCacheList.get(android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, null, null));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void updateCachedServiceLocked(int i) {
        updateCachedServiceListLocked(i, isDisabledLocked(i));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean isDisabledLocked(int i) {
        return this.mDisabledByUserRestriction != null && this.mDisabledByUserRestriction.get(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected S updateCachedServiceLocked(int i, boolean z) {
        S serviceForUserLocked = getServiceForUserLocked(i);
        updateCachedServiceListLocked(i, z);
        return serviceForUserLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected java.util.List<S> updateCachedServiceListLocked(int i, boolean z) {
        if (this.mServiceNameResolver != null && this.mServiceNameResolver.isConfiguredInMultipleMode()) {
            return updateCachedServiceListMultiModeLocked(i, z);
        }
        java.util.List<S> serviceListForUserLocked = getServiceListForUserLocked(i);
        if (serviceListForUserLocked == null) {
            return null;
        }
        for (int i2 = 0; i2 < serviceListForUserLocked.size(); i2++) {
            S s = serviceListForUserLocked.get(i2);
            if (s != null) {
                synchronized (s.mLock) {
                    try {
                        s.updateLocked(z);
                        if (!s.isEnabledLocked()) {
                            removeCachedServiceListLocked(i);
                        } else {
                            onServiceEnabledLocked(serviceListForUserLocked.get(i2), i);
                        }
                    } finally {
                    }
                }
            }
        }
        return serviceListForUserLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<S> updateCachedServiceListMultiModeLocked(int i, boolean z) {
        java.util.List<S> serviceListForUserLocked;
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, null, null);
        new java.util.ArrayList();
        synchronized (this.mLock) {
            removeCachedServiceListLocked(handleIncomingUser);
            serviceListForUserLocked = getServiceListForUserLocked(i);
        }
        return serviceListForUserLocked;
    }

    @android.annotation.Nullable
    protected java.lang.String getServiceSettingsProperty() {
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void onServiceEnabledLocked(@android.annotation.NonNull S s, int i) {
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final java.util.List<S> removeCachedServiceListLocked(int i) {
        java.util.List<S> peekServiceListForUserLocked = peekServiceListForUserLocked(i);
        if (peekServiceListForUserLocked != null) {
            this.mServicesCacheList.delete(i);
            for (int i2 = 0; i2 < peekServiceListForUserLocked.size(); i2++) {
                onServiceRemoved(peekServiceListForUserLocked.get(i2), i);
            }
        }
        return peekServiceListForUserLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void onServicePackageUpdatingLocked(int i) {
        if (this.verbose) {
            android.util.Slog.v(this.mTag, "onServicePackageUpdatingLocked(" + i + ")");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void onServicePackageUpdatedLocked(int i) {
        if (this.verbose) {
            android.util.Slog.v(this.mTag, "onServicePackageUpdated(" + i + ")");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void onServicePackageDataClearedLocked(int i) {
        if (this.verbose) {
            android.util.Slog.v(this.mTag, "onServicePackageDataCleared(" + i + ")");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void onServicePackageRestartedLocked(int i) {
        if (this.verbose) {
            android.util.Slog.v(this.mTag, "onServicePackageRestarted(" + i + ")");
        }
    }

    protected void onServiceRemoved(@android.annotation.NonNull S s, int i) {
    }

    protected void onServiceNameChanged(int i, @android.annotation.Nullable java.lang.String str, boolean z) {
        synchronized (this.mLock) {
            updateCachedServiceListLocked(i, isDisabledLocked(i));
        }
    }

    protected void onServiceNameListChanged(int i, @android.annotation.Nullable java.lang.String[] strArr, boolean z) {
        synchronized (this.mLock) {
            updateCachedServiceListLocked(i, isDisabledLocked(i));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void visitServicesLocked(@android.annotation.NonNull com.android.server.infra.AbstractMasterSystemService.Visitor<S> visitor) {
        int size = this.mServicesCacheList.size();
        for (int i = 0; i < size; i++) {
            java.util.List<S> valueAt = this.mServicesCacheList.valueAt(i);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                visitor.visit(valueAt.get(i2));
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void clearCacheLocked() {
        this.mServicesCacheList.clear();
    }

    @android.annotation.NonNull
    protected com.android.server.pm.UserManagerInternal getUserManagerInternal() {
        if (this.mUm == null) {
            if (this.verbose) {
                android.util.Slog.v(this.mTag, "lazy-loading UserManagerInternal");
            }
            this.mUm = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        }
        return this.mUm;
    }

    @android.annotation.NonNull
    protected java.util.List<android.content.pm.UserInfo> getSupportedUsers() {
        android.content.pm.UserInfo[] userInfos = getUserManagerInternal().getUserInfos();
        java.util.ArrayList arrayList = new java.util.ArrayList(userInfos.length);
        for (android.content.pm.UserInfo userInfo : userInfos) {
            if (isUserSupported(new com.android.server.SystemService.TargetUser(userInfo))) {
                arrayList.add(userInfo);
            }
        }
        return arrayList;
    }

    protected void assertCalledByPackageOwner(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        int callingUid = android.os.Binder.getCallingUid();
        java.lang.String[] packagesForUid = getContext().getPackageManager().getPackagesForUid(callingUid);
        if (packagesForUid != null) {
            for (java.lang.String str2 : packagesForUid) {
                if (str.equals(str2)) {
                    return;
                }
            }
        }
        throw new java.lang.SecurityException("UID " + callingUid + " does not own " + str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void dumpLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        boolean z = this.debug;
        boolean z2 = this.verbose;
        try {
            this.verbose = true;
            this.debug = true;
            int size = this.mServicesCacheList.size();
            printWriter.print(str);
            printWriter.print("Debug: ");
            printWriter.print(z);
            printWriter.print(" Verbose: ");
            printWriter.println(z2);
            printWriter.print("Package policy flags: ");
            printWriter.println(this.mServicePackagePolicyFlags);
            if (this.mUpdatingPackageNames != null) {
                printWriter.print("Packages being updated: ");
                printWriter.println(this.mUpdatingPackageNames);
            }
            dumpSupportedUsers(printWriter, str);
            if (this.mServiceNameResolver != null) {
                printWriter.print(str);
                printWriter.print("Name resolver: ");
                this.mServiceNameResolver.dumpShort(printWriter);
                printWriter.println();
                java.util.List<android.content.pm.UserInfo> supportedUsers = getSupportedUsers();
                for (int i = 0; i < supportedUsers.size(); i++) {
                    int i2 = supportedUsers.get(i).id;
                    printWriter.print("    ");
                    printWriter.print(i2);
                    printWriter.print(": ");
                    this.mServiceNameResolver.dumpShort(printWriter, i2);
                    printWriter.println();
                }
            }
            printWriter.print(str);
            printWriter.print("Users disabled by restriction: ");
            printWriter.println(this.mDisabledByUserRestriction);
            printWriter.print(str);
            printWriter.print("Allow instant service: ");
            printWriter.println(this.mAllowInstantService);
            java.lang.String serviceSettingsProperty = getServiceSettingsProperty();
            if (serviceSettingsProperty != null) {
                printWriter.print(str);
                printWriter.print("Settings property: ");
                printWriter.println(serviceSettingsProperty);
            }
            printWriter.print(str);
            printWriter.print("Cached services: ");
            if (size == 0) {
                printWriter.println("none");
            } else {
                printWriter.println(size);
                for (int i3 = 0; i3 < size; i3++) {
                    printWriter.print(str);
                    printWriter.print("Service at ");
                    printWriter.print(i3);
                    printWriter.println(": ");
                    java.util.List<S> valueAt = this.mServicesCacheList.valueAt(i3);
                    for (int i4 = 0; i4 < valueAt.size(); i4++) {
                        S s = valueAt.get(i4);
                        synchronized (s.mLock) {
                            s.dumpLocked("    ", printWriter);
                        }
                    }
                    printWriter.println();
                }
            }
            this.debug = z;
            this.verbose = z2;
        } catch (java.lang.Throwable th) {
            this.debug = z;
            this.verbose = z2;
            throw th;
        }
    }

    /* renamed from: com.android.server.infra.AbstractMasterSystemService$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.content.PackageMonitor {
        AnonymousClass1() {
        }

        public void onPackageUpdateStarted(@android.annotation.NonNull java.lang.String str, int i) {
            if (com.android.server.infra.AbstractMasterSystemService.this.verbose) {
                android.util.Slog.v(com.android.server.infra.AbstractMasterSystemService.this.mTag, "onPackageUpdateStarted(): " + str);
            }
            java.lang.String activeServicePackageNameLocked = getActiveServicePackageNameLocked();
            if (str.equals(activeServicePackageNameLocked)) {
                int changingUserId = getChangingUserId();
                synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                    try {
                        if (com.android.server.infra.AbstractMasterSystemService.this.mUpdatingPackageNames == null) {
                            com.android.server.infra.AbstractMasterSystemService.this.mUpdatingPackageNames = new android.util.SparseArray(com.android.server.infra.AbstractMasterSystemService.this.mServicesCacheList.size());
                        }
                        com.android.server.infra.AbstractMasterSystemService.this.mUpdatingPackageNames.put(changingUserId, str);
                        com.android.server.infra.AbstractMasterSystemService.this.onServicePackageUpdatingLocked(changingUserId);
                        if ((com.android.server.infra.AbstractMasterSystemService.this.mServicePackagePolicyFlags & 1) != 0) {
                            if (com.android.server.infra.AbstractMasterSystemService.this.debug) {
                                android.util.Slog.d(com.android.server.infra.AbstractMasterSystemService.this.mTag, "Holding service for user " + changingUserId + " while package " + activeServicePackageNameLocked + " is being updated");
                            }
                        } else {
                            if (com.android.server.infra.AbstractMasterSystemService.this.debug) {
                                android.util.Slog.d(com.android.server.infra.AbstractMasterSystemService.this.mTag, "Removing service for user " + changingUserId + " because package " + activeServicePackageNameLocked + " is being updated");
                            }
                            com.android.server.infra.AbstractMasterSystemService.this.removeCachedServiceListLocked(changingUserId);
                            if ((com.android.server.infra.AbstractMasterSystemService.this.mServicePackagePolicyFlags & 4) != 0) {
                                if (com.android.server.infra.AbstractMasterSystemService.this.debug) {
                                    android.util.Slog.d(com.android.server.infra.AbstractMasterSystemService.this.mTag, "Eagerly recreating service for user " + changingUserId);
                                }
                                com.android.server.infra.AbstractMasterSystemService.this.getServiceForUserLocked(changingUserId);
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public void onPackageUpdateFinished(@android.annotation.NonNull java.lang.String str, int i) {
            if (com.android.server.infra.AbstractMasterSystemService.this.verbose) {
                android.util.Slog.v(com.android.server.infra.AbstractMasterSystemService.this.mTag, "onPackageUpdateFinished(): " + str);
            }
            int changingUserId = getChangingUserId();
            synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                try {
                    if (str.equals(com.android.server.infra.AbstractMasterSystemService.this.mUpdatingPackageNames == null ? null : (java.lang.String) com.android.server.infra.AbstractMasterSystemService.this.mUpdatingPackageNames.get(changingUserId))) {
                        if (com.android.server.infra.AbstractMasterSystemService.this.mUpdatingPackageNames != null) {
                            com.android.server.infra.AbstractMasterSystemService.this.mUpdatingPackageNames.remove(changingUserId);
                            if (com.android.server.infra.AbstractMasterSystemService.this.mUpdatingPackageNames.size() == 0) {
                                com.android.server.infra.AbstractMasterSystemService.this.mUpdatingPackageNames = null;
                            }
                        }
                        com.android.server.infra.AbstractMasterSystemService.this.onServicePackageUpdatedLocked(changingUserId);
                    } else {
                        handlePackageUpdateLocked(str);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onPackageRemoved(java.lang.String str, int i) {
            android.content.ComponentName serviceComponentName;
            if (com.android.server.infra.AbstractMasterSystemService.this.mServiceNameResolver != null && com.android.server.infra.AbstractMasterSystemService.this.mServiceNameResolver.isConfiguredInMultipleMode()) {
                int changingUserId = getChangingUserId();
                synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                    com.android.server.infra.AbstractMasterSystemService.this.handlePackageRemovedMultiModeLocked(str, changingUserId);
                }
                return;
            }
            synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                try {
                    int changingUserId2 = getChangingUserId();
                    com.android.server.infra.AbstractPerUserSystemService peekServiceForUserLocked = com.android.server.infra.AbstractMasterSystemService.this.peekServiceForUserLocked(changingUserId2);
                    if (peekServiceForUserLocked != null && (serviceComponentName = peekServiceForUserLocked.getServiceComponentName()) != null && str.equals(serviceComponentName.getPackageName())) {
                        handleActiveServiceRemoved(changingUserId2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z) {
            synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                try {
                    java.lang.String activeServicePackageNameLocked = getActiveServicePackageNameLocked();
                    for (java.lang.String str : strArr) {
                        if (str.equals(activeServicePackageNameLocked)) {
                            if (!z) {
                                return true;
                            }
                            java.lang.String action = intent.getAction();
                            int changingUserId = getChangingUserId();
                            if ("android.intent.action.PACKAGE_RESTARTED".equals(action)) {
                                handleActiveServiceRestartedLocked(activeServicePackageNameLocked, changingUserId);
                            } else {
                                com.android.server.infra.AbstractMasterSystemService.this.removeCachedServiceListLocked(changingUserId);
                            }
                        } else {
                            handlePackageUpdateLocked(str);
                        }
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onPackageDataCleared(java.lang.String str, int i) {
            android.content.ComponentName serviceComponentName;
            if (com.android.server.infra.AbstractMasterSystemService.this.verbose) {
                android.util.Slog.v(com.android.server.infra.AbstractMasterSystemService.this.mTag, "onPackageDataCleared(): " + str);
            }
            int changingUserId = getChangingUserId();
            if (com.android.server.infra.AbstractMasterSystemService.this.mServiceNameResolver != null && com.android.server.infra.AbstractMasterSystemService.this.mServiceNameResolver.isConfiguredInMultipleMode()) {
                synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                    com.android.server.infra.AbstractMasterSystemService.this.onServicePackageDataClearedMultiModeLocked(str, changingUserId);
                }
                return;
            }
            synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                try {
                    com.android.server.infra.AbstractPerUserSystemService peekServiceForUserLocked = com.android.server.infra.AbstractMasterSystemService.this.peekServiceForUserLocked(changingUserId);
                    if (peekServiceForUserLocked != null && (serviceComponentName = peekServiceForUserLocked.getServiceComponentName()) != null && str.equals(serviceComponentName.getPackageName())) {
                        com.android.server.infra.AbstractMasterSystemService.this.onServicePackageDataClearedLocked(changingUserId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void handleActiveServiceRemoved(int i) {
            synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                com.android.server.infra.AbstractMasterSystemService.this.removeCachedServiceListLocked(i);
            }
            java.lang.String serviceSettingsProperty = com.android.server.infra.AbstractMasterSystemService.this.getServiceSettingsProperty();
            if (serviceSettingsProperty != null) {
                android.provider.Settings.Secure.putStringForUser(com.android.server.infra.AbstractMasterSystemService.this.getContext().getContentResolver(), serviceSettingsProperty, null, i);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void handleActiveServiceRestartedLocked(java.lang.String str, int i) {
            if ((com.android.server.infra.AbstractMasterSystemService.this.mServicePackagePolicyFlags & 16) != 0) {
                if (com.android.server.infra.AbstractMasterSystemService.this.debug) {
                    android.util.Slog.d(com.android.server.infra.AbstractMasterSystemService.this.mTag, "Holding service for user " + i + " while package " + str + " is being restarted");
                }
            } else {
                if (com.android.server.infra.AbstractMasterSystemService.this.debug) {
                    android.util.Slog.d(com.android.server.infra.AbstractMasterSystemService.this.mTag, "Removing service for user " + i + " because package " + str + " is being restarted");
                }
                com.android.server.infra.AbstractMasterSystemService.this.removeCachedServiceListLocked(i);
                if ((com.android.server.infra.AbstractMasterSystemService.this.mServicePackagePolicyFlags & 64) != 0) {
                    if (com.android.server.infra.AbstractMasterSystemService.this.debug) {
                        android.util.Slog.d(com.android.server.infra.AbstractMasterSystemService.this.mTag, "Eagerly recreating service for user " + i);
                    }
                    com.android.server.infra.AbstractMasterSystemService.this.updateCachedServiceLocked(i);
                }
            }
            com.android.server.infra.AbstractMasterSystemService.this.onServicePackageRestartedLocked(i);
        }

        public void onPackageModified(java.lang.String str) {
            synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                try {
                    if (com.android.server.infra.AbstractMasterSystemService.this.verbose) {
                        android.util.Slog.v(com.android.server.infra.AbstractMasterSystemService.this.mTag, "onPackageModified(): " + str);
                    }
                    if (com.android.server.infra.AbstractMasterSystemService.this.mServiceNameResolver == null) {
                        return;
                    }
                    int changingUserId = getChangingUserId();
                    java.lang.String[] defaultServiceNameList = com.android.server.infra.AbstractMasterSystemService.this.mServiceNameResolver.getDefaultServiceNameList(changingUserId);
                    if (defaultServiceNameList != null) {
                        for (java.lang.String str2 : defaultServiceNameList) {
                            peekAndUpdateCachedServiceLocked(str, changingUserId, str2);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void peekAndUpdateCachedServiceLocked(java.lang.String str, int i, java.lang.String str2) {
            android.content.ComponentName unflattenFromString;
            com.android.server.infra.AbstractPerUserSystemService peekServiceForUserLocked;
            if (str2 != null && (unflattenFromString = android.content.ComponentName.unflattenFromString(str2)) != null && unflattenFromString.getPackageName().equals(str) && (peekServiceForUserLocked = com.android.server.infra.AbstractMasterSystemService.this.peekServiceForUserLocked(i)) != null && peekServiceForUserLocked.getServiceComponentName() == null) {
                if (com.android.server.infra.AbstractMasterSystemService.this.verbose) {
                    android.util.Slog.v(com.android.server.infra.AbstractMasterSystemService.this.mTag, "update cached");
                }
                com.android.server.infra.AbstractMasterSystemService.this.updateCachedServiceLocked(i);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private java.lang.String getActiveServicePackageNameLocked() {
            android.content.ComponentName serviceComponentName;
            com.android.server.infra.AbstractPerUserSystemService peekServiceForUserLocked = com.android.server.infra.AbstractMasterSystemService.this.peekServiceForUserLocked(getChangingUserId());
            if (peekServiceForUserLocked == null || (serviceComponentName = peekServiceForUserLocked.getServiceComponentName()) == null) {
                return null;
            }
            return serviceComponentName.getPackageName();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void handlePackageUpdateLocked(final java.lang.String str) {
            com.android.server.infra.AbstractMasterSystemService.this.visitServicesLocked(new com.android.server.infra.AbstractMasterSystemService.Visitor() { // from class: com.android.server.infra.AbstractMasterSystemService$1$$ExternalSyntheticLambda0
                @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
                public final void visit(java.lang.Object obj) {
                    ((com.android.server.infra.AbstractPerUserSystemService) obj).handlePackageUpdateLocked(str);
                }
            });
        }
    }

    private void startTrackingPackageChanges() {
        new com.android.server.infra.AbstractMasterSystemService.AnonymousClass1().register(getContext(), (android.os.Looper) null, android.os.UserHandle.ALL, true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void onServicePackageDataClearedMultiModeLocked(java.lang.String str, int i) {
        if (this.verbose) {
            android.util.Slog.v(this.mTag, "onServicePackageDataClearedMultiModeLocked(" + i + ")");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void handlePackageRemovedMultiModeLocked(java.lang.String str, int i) {
        if (this.verbose) {
            android.util.Slog.v(this.mTag, "handlePackageRemovedMultiModeLocked(" + i + ")");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void removeServiceFromCache(@android.annotation.NonNull S s, int i) {
        if (this.mServicesCacheList.get(i) != null) {
            this.mServicesCacheList.get(i).remove(s);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void removeServiceFromMultiModeSettings(java.lang.String str, int i) {
        if (getServiceSettingsProperty() == null || this.mServiceNameResolver == null || !this.mServiceNameResolver.isConfiguredInMultipleMode()) {
            if (this.verbose) {
                android.util.Slog.v(this.mTag, "removeServiceFromSettings not implemented  for single backend implementation");
                return;
            }
            return;
        }
        java.lang.String[] serviceNameList = this.mServiceNameResolver.getServiceNameList(i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str2 : serviceNameList) {
            if (!str2.equals(str)) {
                arrayList.add(str2);
            }
        }
        this.mServiceNameResolver.setServiceNameList(arrayList, i);
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
            android.content.ContentResolver contentResolver = com.android.server.infra.AbstractMasterSystemService.this.getContext().getContentResolver();
            java.lang.String serviceSettingsProperty = com.android.server.infra.AbstractMasterSystemService.this.getServiceSettingsProperty();
            if (serviceSettingsProperty != null) {
                contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(serviceSettingsProperty), false, this, -1);
            }
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("user_setup_complete"), false, this, -1);
            com.android.server.infra.AbstractMasterSystemService.this.registerForExtraSettingsChanges(contentResolver, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            if (com.android.server.infra.AbstractMasterSystemService.this.verbose) {
                android.util.Slog.v(com.android.server.infra.AbstractMasterSystemService.this.mTag, "onChange(): uri=" + uri + ", userId=" + i);
            }
            java.lang.String lastPathSegment = uri.getLastPathSegment();
            if (lastPathSegment == null) {
                return;
            }
            if (lastPathSegment.equals(com.android.server.infra.AbstractMasterSystemService.this.getServiceSettingsProperty()) || lastPathSegment.equals("user_setup_complete")) {
                synchronized (com.android.server.infra.AbstractMasterSystemService.this.mLock) {
                    com.android.server.infra.AbstractMasterSystemService.this.updateCachedServiceLocked(i);
                }
                return;
            }
            com.android.server.infra.AbstractMasterSystemService.this.onSettingsChanged(i, lastPathSegment);
        }
    }
}
