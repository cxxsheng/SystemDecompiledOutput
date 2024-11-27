package com.android.server.pm.verify.domain;

@android.annotation.SuppressLint({"MissingPermission"})
/* loaded from: classes2.dex */
public class DomainVerificationService extends com.android.server.SystemService implements com.android.server.pm.verify.domain.DomainVerificationManagerInternal, com.android.server.pm.verify.domain.DomainVerificationShell.Callback {
    public static final boolean DEBUG_APPROVAL = false;
    private static final long SETTINGS_API_V2 = 178111421;
    private static final java.lang.String TAG = "DomainVerificationService";

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.pm.verify.domain.models.DomainVerificationStateMap<com.android.server.pm.verify.domain.models.DomainVerificationPkgState> mAttachedPkgStates;
    private boolean mCanSendBroadcasts;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationCollector mCollector;

    @android.annotation.NonNull
    private com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection mConnection;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationDebug mDebug;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationEnforcer mEnforcer;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationLegacySettings mLegacySettings;
    private final java.lang.Object mLock;

    @android.annotation.NonNull
    private final com.android.server.compat.PlatformCompat mPlatformCompat;

    @android.annotation.NonNull
    private com.android.server.pm.verify.domain.proxy.DomainVerificationProxy mProxy;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationSettings mSettings;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationShell mShell;

    @android.annotation.NonNull
    private final android.content.pm.verify.domain.IDomainVerificationManager.Stub mStub;

    @android.annotation.NonNull
    private final com.android.server.SystemConfig mSystemConfig;

    public DomainVerificationService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.SystemConfig systemConfig, @android.annotation.NonNull com.android.server.compat.PlatformCompat platformCompat) {
        super(context);
        this.mAttachedPkgStates = new com.android.server.pm.verify.domain.models.DomainVerificationStateMap<>();
        this.mLock = new java.lang.Object();
        this.mStub = new com.android.server.pm.verify.domain.DomainVerificationManagerStub(this);
        this.mProxy = new com.android.server.pm.verify.domain.proxy.DomainVerificationProxyUnavailable();
        this.mSystemConfig = systemConfig;
        this.mPlatformCompat = platformCompat;
        this.mCollector = new com.android.server.pm.verify.domain.DomainVerificationCollector(platformCompat, systemConfig);
        this.mSettings = new com.android.server.pm.verify.domain.DomainVerificationSettings(this.mCollector);
        this.mEnforcer = new com.android.server.pm.verify.domain.DomainVerificationEnforcer(context);
        this.mDebug = new com.android.server.pm.verify.domain.DomainVerificationDebug(this.mCollector);
        this.mShell = new com.android.server.pm.verify.domain.DomainVerificationShell(this);
        this.mLegacySettings = new com.android.server.pm.verify.domain.DomainVerificationLegacySettings();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("domain_verification", this.mStub);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void setConnection(@android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection connection) {
        this.mConnection = connection;
        this.mEnforcer.setCallback(this.mConnection);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    @android.annotation.NonNull
    public com.android.server.pm.verify.domain.proxy.DomainVerificationProxy getProxy() {
        return this.mProxy;
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        super.onBootPhase(i);
        if (!hasRealVerifier()) {
        }
        switch (i) {
            case 550:
                this.mCanSendBroadcasts = true;
                break;
            case 1000:
                verifyPackages(null, false);
                break;
        }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocked(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        super.onUserUnlocked(targetUser);
        verifyPackages(null, false);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void setProxy(@android.annotation.NonNull com.android.server.pm.verify.domain.proxy.DomainVerificationProxy domainVerificationProxy) {
        this.mProxy = domainVerificationProxy;
    }

    @android.annotation.RequiresPermission("android.permission.DOMAIN_VERIFICATION_AGENT")
    public void setUriRelativeFilterGroups(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.Bundle bundle) throws android.content.pm.PackageManager.NameNotFoundException {
        getContext().enforceCallingOrSelfPermission("android.permission.DOMAIN_VERIFICATION_AGENT", "Caller " + this.mConnection.getCallingUid() + " does not hold android.permission.DOMAIN_VERIFICATION_AGENT");
        if (bundle.isEmpty()) {
            return;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                if (domainVerificationPkgState == null) {
                    throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
                }
                android.util.ArrayMap<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> uriRelativeFilterGroupMap = domainVerificationPkgState.getUriRelativeFilterGroupMap();
                for (java.lang.String str2 : bundle.keySet()) {
                    uriRelativeFilterGroupMap.put(str2, android.content.UriRelativeFilterGroup.parcelsToGroups(bundle.getParcelableArrayList(str2, android.content.UriRelativeFilterGroupParcel.class)));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public android.os.Bundle getUriRelativeFilterGroups(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<java.lang.String> list) {
        android.os.Bundle bundle = new android.os.Bundle();
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                if (domainVerificationPkgState != null) {
                    android.util.ArrayMap<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> uriRelativeFilterGroupMap = domainVerificationPkgState.getUriRelativeFilterGroupMap();
                    for (int i = 0; i < list.size(); i++) {
                        bundle.putParcelableList(list.get(i), android.content.UriRelativeFilterGroup.groupsToParcels(uriRelativeFilterGroupMap.get(list.get(i))));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return bundle;
    }

    @android.annotation.NonNull
    private java.util.List<android.content.UriRelativeFilterGroup> getUriRelativeFilterGroups(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        java.util.List<android.content.UriRelativeFilterGroup> emptyList = java.util.Collections.emptyList();
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                if (domainVerificationPkgState != null) {
                    emptyList = domainVerificationPkgState.getUriRelativeFilterGroupMap().getOrDefault(str2, java.util.Collections.emptyList());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return emptyList;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> queryValidVerificationPackageNames() {
        this.mEnforcer.assertApprovedVerifier(this.mConnection.getCallingUid(), this.mProxy);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                int size = this.mAttachedPkgStates.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.pm.verify.domain.models.DomainVerificationPkgState valueAt = this.mAttachedPkgStates.valueAt(i);
                    if (valueAt.isHasAutoVerifyDomains()) {
                        arrayList.add(valueAt.getPackageName());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    @android.annotation.Nullable
    public java.util.UUID getDomainVerificationInfoId(@android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                if (domainVerificationPkgState == null) {
                    return null;
                }
                return domainVerificationPkgState.getId();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    @android.annotation.Nullable
    public android.content.pm.verify.domain.DomainVerificationInfo getDomainVerificationInfo(@android.annotation.NonNull java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        this.mEnforcer.assertApprovedQuerent(this.mConnection.getCallingUid(), this.mProxy);
        com.android.server.pm.Computer snapshot = this.mConnection.snapshot();
        synchronized (this.mLock) {
            try {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshot.getPackageStateInternal(str);
                com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
                if (pkg == null) {
                    throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
                }
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                if (domainVerificationPkgState == null) {
                    return null;
                }
                android.util.ArrayMap arrayMap = new android.util.ArrayMap(domainVerificationPkgState.getStateMap());
                android.util.ArraySet<java.lang.String> collectValidAutoVerifyDomains = this.mCollector.collectValidAutoVerifyDomains(pkg);
                if (collectValidAutoVerifyDomains.isEmpty()) {
                    return null;
                }
                int size = collectValidAutoVerifyDomains.size();
                for (int i = 0; i < size; i++) {
                    arrayMap.putIfAbsent(collectValidAutoVerifyDomains.valueAt(i), 0);
                }
                int size2 = arrayMap.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    arrayMap.setValueAt(i2, java.lang.Integer.valueOf(android.content.pm.verify.domain.DomainVerificationState.convertToInfoState(((java.lang.Integer) arrayMap.valueAt(i2)).intValue())));
                }
                return new android.content.pm.verify.domain.DomainVerificationInfo(domainVerificationPkgState.getId(), str, arrayMap);
            } finally {
            }
        }
    }

    @android.content.pm.verify.domain.DomainVerificationManager.Error
    public int setDomainVerificationStatus(@android.annotation.NonNull java.util.UUID uuid, @android.annotation.NonNull java.util.Set<java.lang.String> set, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        if (i < 1024 && i != 1) {
            throw new java.lang.IllegalArgumentException("Caller is not allowed to set state code " + i);
        }
        return setDomainVerificationStatusInternal(this.mConnection.getCallingUid(), uuid, set, i);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    @android.content.pm.verify.domain.DomainVerificationManager.Error
    public int setDomainVerificationStatusInternal(int i, @android.annotation.NonNull java.util.UUID uuid, @android.annotation.NonNull java.util.Set<java.lang.String> set, int i2) throws android.content.pm.PackageManager.NameNotFoundException {
        this.mEnforcer.assertApprovedVerifier(i, this.mProxy);
        com.android.server.pm.Computer snapshot = this.mConnection.snapshot();
        synchronized (this.mLock) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult andValidateAttachedLocked = getAndValidateAttachedLocked(uuid, set, true, i, null, snapshot);
                if (andValidateAttachedLocked.isError()) {
                    return andValidateAttachedLocked.getErrorCode();
                }
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState pkgState = andValidateAttachedLocked.getPkgState();
                android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = pkgState.getStateMap();
                for (java.lang.String str : set) {
                    java.lang.Integer num = stateMap.get(str);
                    if (num == null || (num.intValue() != i2 && android.content.pm.verify.domain.DomainVerificationState.isModifiable(num.intValue()))) {
                        if (android.content.pm.verify.domain.DomainVerificationState.isVerified(i2) && (num == null || !android.content.pm.verify.domain.DomainVerificationState.isVerified(num.intValue()))) {
                            arrayList.add(str);
                        }
                        stateMap.put(str, java.lang.Integer.valueOf(i2));
                    }
                }
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    removeUserStatesForDomain(pkgState, (java.lang.String) arrayList.get(i3));
                }
                this.mConnection.scheduleWriteSettings();
                return 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    public void setDomainVerificationStatusInternal(@android.annotation.Nullable java.lang.String str, int i, @android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet) throws android.content.pm.PackageManager.NameNotFoundException {
        android.util.ArraySet arraySet2;
        this.mEnforcer.assertInternal(this.mConnection.getCallingUid());
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                int i2 = 0;
                if (str == null) {
                    com.android.server.pm.Computer snapshot = this.mConnection.snapshot();
                    synchronized (this.mLock) {
                        try {
                            android.util.ArraySet<java.lang.String> arraySet3 = new android.util.ArraySet<>();
                            int size = this.mAttachedPkgStates.size();
                            while (i2 < size) {
                                com.android.server.pm.verify.domain.models.DomainVerificationPkgState valueAt = this.mAttachedPkgStates.valueAt(i2);
                                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshot.getPackageStateInternal(valueAt.getPackageName());
                                if (packageStateInternal != null && packageStateInternal.getPkg() != null) {
                                    com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal.getPkg();
                                    arraySet3.clear();
                                    android.util.ArraySet<java.lang.String> collectValidAutoVerifyDomains = this.mCollector.collectValidAutoVerifyDomains(pkg);
                                    if (arraySet == null) {
                                        arraySet3.addAll((android.util.ArraySet<? extends java.lang.String>) collectValidAutoVerifyDomains);
                                    } else {
                                        arraySet3.addAll((android.util.ArraySet<? extends java.lang.String>) arraySet);
                                        arraySet3.retainAll(collectValidAutoVerifyDomains);
                                    }
                                    setDomainVerificationStatusInternal(valueAt, i, arraySet3);
                                }
                                i2++;
                            }
                        } finally {
                        }
                    }
                } else {
                    com.android.server.pm.Computer snapshot2 = this.mConnection.snapshot();
                    synchronized (this.mLock) {
                        try {
                            com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                            if (domainVerificationPkgState == null) {
                                throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
                            }
                            com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = snapshot2.getPackageStateInternal(str);
                            if (packageStateInternal2 == null || packageStateInternal2.getPkg() == null) {
                                throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
                            }
                            com.android.server.pm.pkg.AndroidPackage pkg2 = packageStateInternal2.getPkg();
                            if (arraySet == null) {
                                arraySet = this.mCollector.collectValidAutoVerifyDomains(pkg2);
                            } else {
                                arraySet.retainAll(this.mCollector.collectValidAutoVerifyDomains(pkg2));
                            }
                            if (!android.content.pm.verify.domain.DomainVerificationState.isVerified(i)) {
                                arraySet2 = null;
                            } else {
                                arraySet2 = new android.util.ArraySet();
                                android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = domainVerificationPkgState.getStateMap();
                                int size2 = arraySet.size();
                                for (int i3 = 0; i3 < size2; i3++) {
                                    java.lang.String valueAt2 = arraySet.valueAt(i3);
                                    java.lang.Integer num = stateMap.get(valueAt2);
                                    if (num == null || !android.content.pm.verify.domain.DomainVerificationState.isVerified(num.intValue())) {
                                        arraySet2.add(valueAt2);
                                    }
                                }
                            }
                            setDomainVerificationStatusInternal(domainVerificationPkgState, i, arraySet);
                            if (arraySet2 != null) {
                                int size3 = arraySet2.size();
                                while (i2 < size3) {
                                    removeUserStatesForDomain(domainVerificationPkgState, (java.lang.String) arraySet2.valueAt(i2));
                                    i2++;
                                }
                            }
                        } finally {
                        }
                    }
                }
                this.mConnection.scheduleWriteSettings();
                return;
            default:
                throw new java.lang.IllegalArgumentException("State must be one of NO_RESPONSE, SUCCESS, APPROVED, or DENIED");
        }
    }

    private void setDomainVerificationStatusInternal(@android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, int i, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
        android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = domainVerificationPkgState.getStateMap();
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            stateMap.put(arraySet.valueAt(i2), java.lang.Integer.valueOf(i));
        }
    }

    private void removeUserStatesForDomain(@android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, @android.annotation.NonNull java.lang.String str) {
        android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> userStates = domainVerificationPkgState.getUserStates();
        synchronized (this.mLock) {
            try {
                int size = this.mAttachedPkgStates.size();
                for (int i = 0; i < size; i++) {
                    android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> userStates2 = this.mAttachedPkgStates.valueAt(i).getUserStates();
                    int size2 = userStates2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState domainVerificationInternalUserState = userStates.get(userStates2.keyAt(i2));
                        if (domainVerificationInternalUserState == null || domainVerificationInternalUserState.isLinkHandlingAllowed()) {
                            userStates2.valueAt(i2).removeHost(str);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setDomainVerificationLinkHandlingAllowed(@android.annotation.NonNull java.lang.String str, boolean z, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        if (!this.mEnforcer.assertApprovedUserSelector(this.mConnection.getCallingUid(), this.mConnection.getCallingUserId(), str, i)) {
            throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
        }
        synchronized (this.mLock) {
            com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
            if (domainVerificationPkgState == null) {
                throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
            }
            domainVerificationPkgState.getOrCreateUserState(i).setLinkHandlingAllowed(z);
        }
        this.mConnection.scheduleWriteSettings();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    public void setDomainVerificationLinkHandlingAllowedInternal(@android.annotation.Nullable java.lang.String str, boolean z, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        this.mEnforcer.assertInternal(this.mConnection.getCallingUid());
        if (str == null) {
            synchronized (this.mLock) {
                try {
                    int size = this.mAttachedPkgStates.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.server.pm.verify.domain.models.DomainVerificationPkgState valueAt = this.mAttachedPkgStates.valueAt(i2);
                        if (i == -1) {
                            for (int i3 : this.mConnection.getAllUserIds()) {
                                valueAt.getOrCreateUserState(i3).setLinkHandlingAllowed(z);
                            }
                        } else {
                            valueAt.getOrCreateUserState(i).setLinkHandlingAllowed(z);
                        }
                    }
                } finally {
                }
            }
        } else {
            synchronized (this.mLock) {
                try {
                    com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                    if (domainVerificationPkgState == null) {
                        throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
                    }
                    if (i == -1) {
                        for (int i4 : this.mConnection.getAllUserIds()) {
                            domainVerificationPkgState.getOrCreateUserState(i4).setLinkHandlingAllowed(z);
                        }
                    } else {
                        domainVerificationPkgState.getOrCreateUserState(i).setLinkHandlingAllowed(z);
                    }
                } finally {
                }
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    @android.content.pm.verify.domain.DomainVerificationManager.Error
    public int setDomainVerificationUserSelection(@android.annotation.NonNull java.util.UUID uuid, @android.annotation.NonNull java.util.Set<java.lang.String> set, boolean z, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        int revokeOtherUserSelectionsLocked;
        int callingUid = this.mConnection.getCallingUid();
        if (!this.mEnforcer.assertApprovedUserSelector(callingUid, this.mConnection.getCallingUserId(), null, i)) {
            return 1;
        }
        com.android.server.pm.Computer snapshot = this.mConnection.snapshot();
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult andValidateAttachedLocked = getAndValidateAttachedLocked(uuid, set, false, callingUid, java.lang.Integer.valueOf(i), snapshot);
                if (andValidateAttachedLocked.isError()) {
                    return andValidateAttachedLocked.getErrorCode();
                }
                com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState orCreateUserState = andValidateAttachedLocked.getPkgState().getOrCreateUserState(i);
                if (z && (revokeOtherUserSelectionsLocked = revokeOtherUserSelectionsLocked(orCreateUserState, i, set, snapshot)) != 0) {
                    return revokeOtherUserSelectionsLocked;
                }
                if (z) {
                    orCreateUserState.addHosts(set);
                } else {
                    orCreateUserState.removeHosts(set);
                }
                this.mConnection.scheduleWriteSettings();
                return 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    public void setDomainVerificationUserSelectionInternal(int i, @android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet) throws android.content.pm.PackageManager.NameNotFoundException {
        this.mEnforcer.assertInternal(this.mConnection.getCallingUid());
        com.android.server.pm.Computer snapshot = this.mConnection.snapshot();
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                if (domainVerificationPkgState == null) {
                    throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
                }
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshot.getPackageStateInternal(str);
                com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
                if (pkg == null) {
                    throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
                }
                if (arraySet == null) {
                    arraySet = this.mCollector.collectAllWebDomains(pkg);
                }
                arraySet.retainAll(this.mCollector.collectAllWebDomains(pkg));
                if (i == -1) {
                    for (int i2 : this.mConnection.getAllUserIds()) {
                        com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState orCreateUserState = domainVerificationPkgState.getOrCreateUserState(i2);
                        revokeOtherUserSelectionsLocked(orCreateUserState, i2, arraySet, snapshot);
                        if (z) {
                            orCreateUserState.addHosts((java.util.Set<java.lang.String>) arraySet);
                        } else {
                            orCreateUserState.removeHosts((java.util.Set<java.lang.String>) arraySet);
                        }
                    }
                } else {
                    com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState orCreateUserState2 = domainVerificationPkgState.getOrCreateUserState(i);
                    revokeOtherUserSelectionsLocked(orCreateUserState2, i, arraySet, snapshot);
                    if (z) {
                        orCreateUserState2.addHosts((java.util.Set<java.lang.String>) arraySet);
                    } else {
                        orCreateUserState2.removeHosts((java.util.Set<java.lang.String>) arraySet);
                    }
                }
            } finally {
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int revokeOtherUserSelectionsLocked(@android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState domainVerificationInternalUserState, int i, @android.annotation.NonNull java.util.Set<java.lang.String> set, @android.annotation.NonNull com.android.server.pm.Computer computer) {
        com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState userState;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (java.lang.String str : set) {
            if (!domainVerificationInternalUserState.getEnabledHosts().contains(str)) {
                android.util.Pair<java.util.List<java.lang.String>, java.lang.Integer> approvedPackagesLocked = getApprovedPackagesLocked(str, i, 1, computer);
                if (((java.lang.Integer) approvedPackagesLocked.second).intValue() > 3) {
                    return 3;
                }
                arrayMap.put(str, (java.util.List) approvedPackagesLocked.first);
            }
        }
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            java.lang.String str2 = (java.lang.String) arrayMap.keyAt(i2);
            java.util.List list = (java.util.List) arrayMap.valueAt(i2);
            int size2 = list.size();
            for (int i3 = 0; i3 < size2; i3++) {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get((java.lang.String) list.get(i3));
                if (domainVerificationPkgState != null && (userState = domainVerificationPkgState.getUserState(i)) != null) {
                    userState.removeHost(str2);
                }
            }
        }
        return 0;
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    @android.annotation.Nullable
    public android.content.pm.verify.domain.DomainVerificationUserState getDomainVerificationUserState(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        boolean z;
        if (!this.mEnforcer.assertApprovedUserStateQuerent(this.mConnection.getCallingUid(), this.mConnection.getCallingUserId(), str, i)) {
            throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
        }
        com.android.server.pm.Computer snapshot = this.mConnection.snapshot();
        synchronized (this.mLock) {
            try {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshot.getPackageStateInternal(str);
                com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
                if (pkg == null) {
                    throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
                }
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                if (domainVerificationPkgState == null) {
                    return null;
                }
                android.util.ArraySet<java.lang.String> collectAllWebDomains = this.mCollector.collectAllWebDomains(pkg);
                int size = collectAllWebDomains.size();
                android.util.ArrayMap arrayMap = new android.util.ArrayMap(size);
                android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = domainVerificationPkgState.getStateMap();
                com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState userState = domainVerificationPkgState.getUserState(i);
                java.util.Set emptySet = userState == null ? java.util.Collections.emptySet() : userState.getEnabledHosts();
                int i2 = 0;
                while (true) {
                    int i3 = 1;
                    if (i2 >= size) {
                        break;
                    }
                    java.lang.String valueAt = collectAllWebDomains.valueAt(i2);
                    java.lang.Integer num = stateMap.get(valueAt);
                    if (num != null && android.content.pm.verify.domain.DomainVerificationState.isVerified(num.intValue())) {
                        i3 = 2;
                    } else if (!emptySet.contains(valueAt)) {
                        i3 = 0;
                    }
                    arrayMap.put(valueAt, java.lang.Integer.valueOf(i3));
                    i2++;
                }
                if (userState != null && !userState.isLinkHandlingAllowed()) {
                    z = false;
                    return new android.content.pm.verify.domain.DomainVerificationUserState(domainVerificationPkgState.getId(), str, android.os.UserHandle.of(i), z, arrayMap);
                }
                z = true;
                return new android.content.pm.verify.domain.DomainVerificationUserState(domainVerificationPkgState.getId(), str, android.os.UserHandle.of(i), z, arrayMap);
            } finally {
            }
        }
    }

    @android.annotation.NonNull
    public java.util.List<android.content.pm.verify.domain.DomainOwner> getOwnersForDomain(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str);
        this.mEnforcer.assertOwnerQuerent(this.mConnection.getCallingUid(), this.mConnection.getCallingUserId(), i);
        android.util.SparseArray<java.util.List<java.lang.String>> ownersForDomainInternal = getOwnersForDomainInternal(str, false, i, this.mConnection.snapshot());
        if (ownersForDomainInternal.size() == 0) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = ownersForDomainInternal.size();
        for (int i2 = 0; i2 < size; i2++) {
            boolean z = ownersForDomainInternal.keyAt(i2) <= 3;
            java.util.List<java.lang.String> valueAt = ownersForDomainInternal.valueAt(i2);
            int size2 = valueAt.size();
            for (int i3 = 0; i3 < size2; i3++) {
                arrayList.add(new android.content.pm.verify.domain.DomainOwner(valueAt.get(i3), z));
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private android.util.SparseArray<java.util.List<java.lang.String>> getOwnersForDomainInternal(@android.annotation.NonNull java.lang.String str, boolean z, final int i, @android.annotation.NonNull final com.android.server.pm.Computer computer) {
        int i2;
        android.util.SparseArray<java.util.List<java.lang.String>> sparseArray = new android.util.SparseArray<>();
        synchronized (this.mLock) {
            try {
                int size = this.mAttachedPkgStates.size();
                for (int i3 = 0; i3 < size; i3++) {
                    java.lang.String packageName = this.mAttachedPkgStates.valueAt(i3).getPackageName();
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(packageName);
                    if (packageStateInternal != null) {
                        int approvalLevelForDomain = approvalLevelForDomain(packageStateInternal, str, z, i, false, str);
                        if (z || approvalLevelForDomain > 0) {
                            java.util.List<java.lang.String> list = sparseArray.get(approvalLevelForDomain);
                            if (list == null) {
                                list = new java.util.ArrayList<>();
                                sparseArray.put(approvalLevelForDomain, list);
                            }
                            list.add(packageName);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        int size2 = sparseArray.size();
        if (size2 == 0) {
            return sparseArray;
        }
        for (i2 = 0; i2 < size2; i2++) {
            sparseArray.valueAt(i2).sort(new java.util.Comparator() { // from class: com.android.server.pm.verify.domain.DomainVerificationService$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$getOwnersForDomainInternal$0;
                    lambda$getOwnersForDomainInternal$0 = com.android.server.pm.verify.domain.DomainVerificationService.lambda$getOwnersForDomainInternal$0(com.android.server.pm.Computer.this, i, (java.lang.String) obj, (java.lang.String) obj2);
                    return lambda$getOwnersForDomainInternal$0;
                }
            });
        }
        return sparseArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$getOwnersForDomainInternal$0(com.android.server.pm.Computer computer, int i, java.lang.String str, java.lang.String str2) {
        long firstInstallTimeMillis;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = computer.getPackageStateInternal(str2);
        long j = -1;
        if (packageStateInternal == null) {
            firstInstallTimeMillis = -1;
        } else {
            firstInstallTimeMillis = packageStateInternal.getUserStateOrDefault(i).getFirstInstallTimeMillis();
        }
        if (packageStateInternal2 != null) {
            j = packageStateInternal2.getUserStateOrDefault(i).getFirstInstallTimeMillis();
        }
        if (firstInstallTimeMillis != j) {
            return (int) (firstInstallTimeMillis - j);
        }
        return str.compareToIgnoreCase(str2);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    @android.annotation.NonNull
    public java.util.UUID generateNewId() {
        return java.util.UUID.randomUUID();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void migrateState(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal2, @android.annotation.Nullable android.content.pm.verify.domain.DomainSet domainSet) {
        java.lang.String packageName = packageStateInternal2.getPackageName();
        synchronized (this.mLock) {
            try {
                java.util.UUID domainSetId = packageStateInternal.getDomainSetId();
                java.util.UUID domainSetId2 = packageStateInternal2.getDomainSetId();
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState remove = this.mAttachedPkgStates.remove(domainSetId);
                com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
                com.android.server.pm.pkg.AndroidPackage pkg2 = packageStateInternal2.getPkg();
                android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap = new android.util.ArrayMap<>();
                android.util.SparseArray sparseArray = new android.util.SparseArray();
                if (remove == null || pkg == null || pkg2 == null) {
                    android.util.Slog.wtf(TAG, "Invalid state nullability old state = " + remove + ", old pkgSetting = " + packageStateInternal + ", new pkgSetting = " + packageStateInternal2 + ", old pkg = " + pkg + ", new pkg = " + pkg2, new java.lang.Exception());
                    this.mAttachedPkgStates.put(packageName, domainSetId2, new com.android.server.pm.verify.domain.models.DomainVerificationPkgState(packageName, domainSetId2, true, arrayMap, sparseArray, null));
                    return;
                }
                android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = remove.getStateMap();
                android.util.ArrayMap<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> uriRelativeFilterGroupMap = remove.getUriRelativeFilterGroupMap();
                android.util.ArraySet<java.lang.String> collectValidAutoVerifyDomains = this.mCollector.collectValidAutoVerifyDomains(pkg2);
                int size = collectValidAutoVerifyDomains.size();
                for (int i = 0; i < size; i++) {
                    java.lang.String valueAt = collectValidAutoVerifyDomains.valueAt(i);
                    java.lang.Integer num = stateMap.get(valueAt);
                    if (num != null) {
                        int intValue = num.intValue();
                        if (android.content.pm.verify.domain.DomainVerificationState.shouldMigrate(intValue)) {
                            arrayMap.put(valueAt, java.lang.Integer.valueOf(intValue));
                        }
                    }
                }
                android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> userStates = remove.getUserStates();
                int size2 = userStates.size();
                if (size2 > 0) {
                    android.util.ArraySet<java.lang.String> collectAllWebDomains = this.mCollector.collectAllWebDomains(pkg2);
                    int i2 = 0;
                    while (i2 < size2) {
                        int keyAt = userStates.keyAt(i2);
                        com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState valueAt2 = userStates.valueAt(i2);
                        int i3 = size2;
                        android.util.ArraySet arraySet = new android.util.ArraySet((android.util.ArraySet) valueAt2.getEnabledHosts());
                        arraySet.retainAll(collectAllWebDomains);
                        sparseArray.put(keyAt, new com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState(keyAt, arraySet, valueAt2.isLinkHandlingAllowed()));
                        i2++;
                        userStates = userStates;
                        size2 = i3;
                    }
                }
                boolean z = size > 0;
                boolean z2 = z && applyImmutableState(packageStateInternal2, arrayMap, collectValidAutoVerifyDomains);
                applyPreVerifiedState(arrayMap, collectValidAutoVerifyDomains, domainSet);
                this.mAttachedPkgStates.put(packageName, domainSetId2, new com.android.server.pm.verify.domain.models.DomainVerificationPkgState(packageName, domainSetId2, z, arrayMap, sparseArray, null, uriRelativeFilterGroupMap));
                if (z2) {
                    sendBroadcast(packageName);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void addPackage(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.Nullable android.content.pm.verify.domain.DomainSet domainSet) {
        boolean z;
        com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState;
        java.util.UUID domainSetId = packageStateInternal.getDomainSetId();
        java.lang.String packageName = packageStateInternal.getPackageName();
        com.android.server.pm.verify.domain.models.DomainVerificationPkgState removePendingState = this.mSettings.removePendingState(packageName);
        android.util.ArraySet<java.lang.String> arraySet = null;
        if (removePendingState != null) {
            z = false;
        } else {
            removePendingState = this.mSettings.removeRestoredState(packageName);
            if (removePendingState != null && !java.util.Objects.equals(removePendingState.getBackupSignatureHash(), android.util.PackageUtils.computeSignaturesSha256Digest(packageStateInternal.getSigningDetails().getSignatures()))) {
                removePendingState = null;
                z = true;
            } else {
                z = true;
            }
        }
        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal.getPkg();
        android.util.ArraySet<java.lang.String> collectValidAutoVerifyDomains = this.mCollector.collectValidAutoVerifyDomains(pkg);
        boolean z2 = !collectValidAutoVerifyDomains.isEmpty();
        boolean z3 = removePendingState != null;
        if (z3) {
            domainVerificationPkgState = new com.android.server.pm.verify.domain.models.DomainVerificationPkgState(removePendingState, domainSetId, z2);
            domainVerificationPkgState.getStateMap().retainAll(collectValidAutoVerifyDomains);
            android.util.ArraySet<java.lang.String> collectAllWebDomains = this.mCollector.collectAllWebDomains(pkg);
            android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> userStates = domainVerificationPkgState.getUserStates();
            int size = userStates.size();
            for (int i = 0; i < size; i++) {
                userStates.valueAt(i).retainHosts(collectAllWebDomains);
            }
        } else {
            domainVerificationPkgState = new com.android.server.pm.verify.domain.models.DomainVerificationPkgState(packageName, domainSetId, z2);
        }
        if (applyImmutableState(packageStateInternal, domainVerificationPkgState.getStateMap(), collectValidAutoVerifyDomains) && !z3) {
            android.util.SparseIntArray userStates2 = this.mLegacySettings.getUserStates(packageName);
            int size2 = userStates2 == null ? 0 : userStates2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                int keyAt = userStates2.keyAt(i2);
                if (userStates2.valueAt(i2) == 2) {
                    if (arraySet == null) {
                        arraySet = this.mCollector.collectAllWebDomains(pkg);
                    }
                    domainVerificationPkgState.getOrCreateUserState(keyAt).addHosts(arraySet);
                }
            }
            android.content.pm.IntentFilterVerificationInfo remove = this.mLegacySettings.remove(packageName);
            if (remove != null && remove.getStatus() == 2) {
                android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = domainVerificationPkgState.getStateMap();
                int size3 = collectValidAutoVerifyDomains.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    stateMap.put(collectValidAutoVerifyDomains.valueAt(i3), 4);
                }
            }
            applyPreVerifiedState(domainVerificationPkgState.getStateMap(), collectValidAutoVerifyDomains, domainSet);
        }
        synchronized (this.mLock) {
            this.mAttachedPkgStates.put(packageName, domainSetId, domainVerificationPkgState);
        }
        if (z && z2) {
            sendBroadcast(packageName);
        }
    }

    private void applyPreVerifiedState(android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, android.util.ArraySet<java.lang.String> arraySet, android.content.pm.verify.domain.DomainSet domainSet) {
        if (domainSet != null && !arraySet.isEmpty()) {
            for (java.lang.String str : domainSet.getDomains()) {
                if (arraySet.contains(str) && !arrayMap.containsKey(str)) {
                    arrayMap.put(str, 8);
                }
            }
        }
    }

    private boolean applyImmutableState(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
        if (packageStateInternal.isSystem() && this.mSystemConfig.getLinkedApps().contains(packageStateInternal.getPackageName())) {
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                arrayMap.put(arraySet.valueAt(i), 7);
            }
            return false;
        }
        for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
            if (arrayMap.valueAt(size2).intValue() == 7) {
                arrayMap.removeAt(size2);
            }
        }
        return true;
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void writeSettings(final com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z, int i) throws java.io.IOException {
        java.util.function.Function<java.lang.String, java.lang.String> function;
        synchronized (this.mLock) {
            if (!z) {
                function = null;
            } else {
                try {
                    function = new java.util.function.Function() { // from class: com.android.server.pm.verify.domain.DomainVerificationService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            java.lang.String lambda$writeSettings$1;
                            lambda$writeSettings$1 = com.android.server.pm.verify.domain.DomainVerificationService.lambda$writeSettings$1(com.android.server.pm.Computer.this, (java.lang.String) obj);
                            return lambda$writeSettings$1;
                        }
                    };
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mSettings.writeSettings(typedXmlSerializer, this.mAttachedPkgStates, i, function);
        }
        this.mLegacySettings.writeSettings(typedXmlSerializer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$writeSettings$1(com.android.server.pm.Computer computer, java.lang.String str) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return null;
        }
        return android.util.PackageUtils.computeSignaturesSha256Digest(packageStateInternal.getSigningDetails().getSignatures());
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void readSettings(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        synchronized (this.mLock) {
            this.mSettings.readSettings(typedXmlPullParser, this.mAttachedPkgStates, computer);
        }
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void readLegacySettings(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        this.mLegacySettings.readSettings(typedXmlPullParser);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void restoreSettings(com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        synchronized (this.mLock) {
            this.mSettings.restoreSettings(typedXmlPullParser, this.mAttachedPkgStates, computer);
        }
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void addLegacySetting(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.IntentFilterVerificationInfo intentFilterVerificationInfo) {
        this.mLegacySettings.add(str, intentFilterVerificationInfo);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public boolean setLegacyUserState(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        if (!this.mEnforcer.callerIsLegacyUserSelector(this.mConnection.getCallingUid(), this.mConnection.getCallingUserId(), str, i)) {
            return false;
        }
        this.mLegacySettings.add(str, i, i2);
        this.mConnection.scheduleWriteSettings();
        return true;
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public int getLegacyState(@android.annotation.NonNull java.lang.String str, int i) {
        if (!this.mEnforcer.callerIsLegacyUserQuerent(this.mConnection.getCallingUid(), this.mConnection.getCallingUserId(), str, i)) {
            return 0;
        }
        return this.mLegacySettings.getUserState(str, i);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void clearPackage(@android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            this.mAttachedPkgStates.remove(str);
            this.mSettings.removePackage(str);
        }
        this.mConnection.scheduleWriteSettings();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void clearPackageForUser(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                if (domainVerificationPkgState != null) {
                    domainVerificationPkgState.removeUser(i);
                }
                this.mSettings.removePackageForUser(str, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void clearUser(int i) {
        synchronized (this.mLock) {
            try {
                int size = this.mAttachedPkgStates.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mAttachedPkgStates.valueAt(i2).removeUser(i);
                }
                this.mSettings.removeUser(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public boolean runMessage(int i, java.lang.Object obj) {
        return this.mProxy.runMessage(i, obj);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    public void printState(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num) throws android.content.pm.PackageManager.NameNotFoundException {
        printState(this.mConnection.snapshot(), indentingPrintWriter, str, num);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public void printState(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num) throws android.content.pm.PackageManager.NameNotFoundException {
        this.mEnforcer.assertApprovedQuerent(this.mConnection.getCallingUid(), this.mProxy);
        synchronized (this.mLock) {
            this.mDebug.printState(indentingPrintWriter, str, num, computer, this.mAttachedPkgStates);
        }
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    public void printOwnersForPackage(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num) throws android.content.pm.PackageManager.NameNotFoundException {
        this.mEnforcer.assertApprovedQuerent(this.mConnection.getCallingUid(), this.mProxy);
        com.android.server.pm.Computer snapshot = this.mConnection.snapshot();
        synchronized (this.mLock) {
            if (str == null) {
                int size = this.mAttachedPkgStates.size();
                for (int i = 0; i < size; i++) {
                    try {
                        printOwnersForPackage(indentingPrintWriter, this.mAttachedPkgStates.valueAt(i).getPackageName(), num, snapshot);
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    }
                }
            } else {
                printOwnersForPackage(indentingPrintWriter, str, num, snapshot);
            }
        }
    }

    private void printOwnersForPackage(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Integer num, @android.annotation.NonNull com.android.server.pm.Computer computer) throws android.content.pm.PackageManager.NameNotFoundException {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
        if (pkg == null) {
            throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(str);
        }
        android.util.ArraySet<java.lang.String> collectAllWebDomains = this.mCollector.collectAllWebDomains(pkg);
        int size = collectAllWebDomains.size();
        if (size == 0) {
            return;
        }
        indentingPrintWriter.println(str + ":");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < size; i++) {
            printOwnersForDomain(indentingPrintWriter, collectAllWebDomains.valueAt(i), num, computer);
        }
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    public void printOwnersForDomains(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.Nullable java.lang.Integer num) {
        this.mEnforcer.assertApprovedQuerent(this.mConnection.getCallingUid(), this.mProxy);
        com.android.server.pm.Computer snapshot = this.mConnection.snapshot();
        synchronized (this.mLock) {
            try {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    printOwnersForDomain(indentingPrintWriter, list.get(i), num, snapshot);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void printOwnersForDomain(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Integer num, @android.annotation.NonNull com.android.server.pm.Computer computer) {
        android.util.SparseArray<android.util.SparseArray<java.util.List<java.lang.String>>> sparseArray = new android.util.SparseArray<>();
        if (num == null || num.intValue() == -1) {
            for (int i : this.mConnection.getAllUserIds()) {
                sparseArray.put(i, getOwnersForDomainInternal(str, true, i, computer));
            }
        } else {
            sparseArray.put(num.intValue(), getOwnersForDomainInternal(str, true, num.intValue(), computer));
        }
        this.mDebug.printOwners(indentingPrintWriter, str, sparseArray);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    @android.annotation.NonNull
    public com.android.server.pm.verify.domain.DomainVerificationShell getShell() {
        return this.mShell;
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    @android.annotation.NonNull
    public com.android.server.pm.verify.domain.DomainVerificationCollector getCollector() {
        return this.mCollector;
    }

    private void sendBroadcast(@android.annotation.NonNull java.lang.String str) {
        sendBroadcast(java.util.Collections.singleton(str));
    }

    private void sendBroadcast(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        if (!this.mCanSendBroadcasts) {
            return;
        }
        this.mProxy.sendBroadcastForPackages(set);
    }

    private boolean hasRealVerifier() {
        return !(this.mProxy instanceof com.android.server.pm.verify.domain.proxy.DomainVerificationProxyUnavailable);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult getAndValidateAttachedLocked(@android.annotation.NonNull java.util.UUID uuid, @android.annotation.NonNull java.util.Set<java.lang.String> set, boolean z, int i, @android.annotation.Nullable java.lang.Integer num, @android.annotation.NonNull com.android.server.pm.Computer computer) throws android.content.pm.PackageManager.NameNotFoundException {
        android.util.ArraySet<java.lang.String> collectAllWebDomains;
        if (uuid == null) {
            throw new java.lang.IllegalArgumentException("domainSetId cannot be null");
        }
        com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(uuid);
        if (domainVerificationPkgState == null) {
            return com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult.error(1);
        }
        java.lang.String packageName = domainVerificationPkgState.getPackageName();
        if (num != null && this.mConnection.filterAppAccess(packageName, i, num.intValue())) {
            return com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult.error(1);
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(packageName);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
            throw com.android.server.pm.verify.domain.DomainVerificationUtils.throwPackageUnavailable(packageName);
        }
        if (com.android.internal.util.CollectionUtils.isEmpty(set)) {
            throw new java.lang.IllegalArgumentException("Provided domain set cannot be empty");
        }
        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal.getPkg();
        if (z) {
            collectAllWebDomains = this.mCollector.collectValidAutoVerifyDomains(pkg);
        } else {
            collectAllWebDomains = this.mCollector.collectAllWebDomains(pkg);
        }
        if (set.retainAll(collectAllWebDomains)) {
            return com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult.error(2);
        }
        return com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult.success(domainVerificationPkgState);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    public void verifyPackages(@android.annotation.Nullable java.util.List<java.lang.String> list, boolean z) {
        this.mEnforcer.assertInternal(this.mConnection.getCallingUid());
        java.util.Set<java.lang.String> arraySet = new android.util.ArraySet<>();
        int i = 0;
        if (list == null) {
            synchronized (this.mLock) {
                try {
                    int size = this.mAttachedPkgStates.size();
                    while (i < size) {
                        addIfShouldBroadcastLocked(arraySet, this.mAttachedPkgStates.valueAt(i), z);
                        i++;
                    }
                } finally {
                }
            }
        } else {
            synchronized (this.mLock) {
                try {
                    int size2 = list.size();
                    while (i < size2) {
                        com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(list.get(i));
                        if (domainVerificationPkgState != null) {
                            addIfShouldBroadcastLocked(arraySet, domainVerificationPkgState, z);
                        }
                        i++;
                    }
                } finally {
                }
            }
        }
        if (!arraySet.isEmpty()) {
            sendBroadcast(arraySet);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addIfShouldBroadcastLocked(@android.annotation.NonNull java.util.Collection<java.lang.String> collection, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, boolean z) {
        if ((z && domainVerificationPkgState.isHasAutoVerifyDomains()) || shouldReBroadcastPackage(domainVerificationPkgState)) {
            collection.add(domainVerificationPkgState.getPackageName());
        }
    }

    private boolean shouldReBroadcastPackage(com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState) {
        if (!domainVerificationPkgState.isHasAutoVerifyDomains()) {
            return false;
        }
        android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = domainVerificationPkgState.getStateMap();
        int size = stateMap.size();
        for (int i = 0; i < size; i++) {
            if (!android.content.pm.verify.domain.DomainVerificationState.isDefault(stateMap.valueAt(i).intValue())) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    public void clearDomainVerificationState(@android.annotation.Nullable java.util.List<java.lang.String> list) {
        this.mEnforcer.assertInternal(this.mConnection.getCallingUid());
        com.android.server.pm.Computer snapshot = this.mConnection.snapshot();
        synchronized (this.mLock) {
            int i = 0;
            try {
                if (list == null) {
                    int size = this.mAttachedPkgStates.size();
                    while (i < size) {
                        com.android.server.pm.verify.domain.models.DomainVerificationPkgState valueAt = this.mAttachedPkgStates.valueAt(i);
                        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshot.getPackageStateInternal(valueAt.getPackageName());
                        if (packageStateInternal != null && packageStateInternal.getPkg() != null) {
                            resetDomainState(valueAt.getStateMap(), packageStateInternal);
                        }
                        i++;
                    }
                } else {
                    int size2 = list.size();
                    while (i < size2) {
                        java.lang.String str = list.get(i);
                        com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(str);
                        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = snapshot.getPackageStateInternal(str);
                        if (packageStateInternal2 != null && packageStateInternal2.getPkg() != null) {
                            resetDomainState(domainVerificationPkgState.getStateMap(), packageStateInternal2);
                        }
                        i++;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    private void resetDomainState(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        boolean z;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            java.lang.Integer valueAt = arrayMap.valueAt(size);
            switch (valueAt.intValue()) {
                case 1:
                case 5:
                    z = true;
                    break;
                default:
                    if (valueAt.intValue() >= 1024) {
                        z = true;
                        break;
                    } else {
                        z = false;
                        break;
                    }
            }
            if (z) {
                arrayMap.removeAt(size);
            }
        }
        applyImmutableState(packageStateInternal, arrayMap, this.mCollector.collectValidAutoVerifyDomains(packageStateInternal.getPkg()));
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationShell.Callback
    public void clearUserStates(@android.annotation.Nullable java.util.List<java.lang.String> list, int i) {
        this.mEnforcer.assertInternal(this.mConnection.getCallingUid());
        synchronized (this.mLock) {
            int i2 = 0;
            try {
                if (list == null) {
                    int size = this.mAttachedPkgStates.size();
                    while (i2 < size) {
                        com.android.server.pm.verify.domain.models.DomainVerificationPkgState valueAt = this.mAttachedPkgStates.valueAt(i2);
                        if (i == -1) {
                            valueAt.removeAllUsers();
                        } else {
                            valueAt.removeUser(i);
                        }
                        i2++;
                    }
                } else {
                    int size2 = list.size();
                    while (i2 < size2) {
                        com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(list.get(i2));
                        if (i == -1) {
                            domainVerificationPkgState.removeAllUsers();
                        } else {
                            domainVerificationPkgState.removeUser(i);
                        }
                        i2++;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    @android.annotation.NonNull
    public android.util.Pair<java.util.List<android.content.pm.ResolveInfo>, java.lang.Integer> filterToApprovedApp(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull java.util.List<android.content.pm.ResolveInfo> list, int i, @android.annotation.NonNull java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        android.util.ArrayMap<android.content.pm.ResolveInfo, java.lang.Integer> arrayMap = new android.util.ArrayMap<>();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.ResolveInfo resolveInfo = list.get(i2);
            if (resolveInfo.isAutoResolutionAllowed()) {
                arrayMap.put(resolveInfo, null);
            }
        }
        int fillMapWithApprovalLevels = fillMapWithApprovalLevels(arrayMap, intent.getData(), i, function);
        if (fillMapWithApprovalLevels <= 0) {
            return android.util.Pair.create(java.util.Collections.emptyList(), java.lang.Integer.valueOf(fillMapWithApprovalLevels));
        }
        for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
            if (arrayMap.valueAt(size2).intValue() != fillMapWithApprovalLevels) {
                arrayMap.removeAt(size2);
            }
        }
        if (fillMapWithApprovalLevels != 1) {
            filterToLastFirstInstalled(arrayMap, function);
        }
        int size3 = arrayMap.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size3);
        for (int i3 = 0; i3 < size3; i3++) {
            arrayList.add(arrayMap.keyAt(i3));
        }
        if (fillMapWithApprovalLevels != 1) {
            filterToLastDeclared(arrayList, function);
        }
        return android.util.Pair.create(arrayList, java.lang.Integer.valueOf(fillMapWithApprovalLevels));
    }

    private boolean matchUriRelativeFilterGroups(android.net.Uri uri, java.lang.String str) {
        if (uri.getHost() == null) {
            return false;
        }
        java.util.List<android.content.UriRelativeFilterGroup> uriRelativeFilterGroups = getUriRelativeFilterGroups(str, uri.getHost());
        if (uriRelativeFilterGroups.isEmpty()) {
            return true;
        }
        return android.content.UriRelativeFilterGroup.matchGroupsToUri(uriRelativeFilterGroups, uri);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003b, code lost:
    
        if (matchUriRelativeFilterGroups(r18, r13) != false) goto L15;
     */
    @com.android.server.pm.verify.domain.DomainVerificationManagerInternal.ApprovalLevel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int fillMapWithApprovalLevels(@android.annotation.NonNull android.util.ArrayMap<android.content.pm.ResolveInfo, java.lang.Integer> arrayMap, @android.annotation.NonNull android.net.Uri uri, int i, @android.annotation.NonNull java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        int size = arrayMap.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (arrayMap.valueAt(i3) == null) {
                java.lang.String str = arrayMap.keyAt(i3).getComponentInfo().packageName;
                com.android.server.pm.pkg.PackageStateInternal apply = function.apply(str);
                if (apply != null) {
                    if (android.content.pm.Flags.relativeReferenceIntentFilters()) {
                    }
                    int approvalLevelForDomain = approvalLevelForDomain(apply, uri.getHost(), false, i, false, uri.getHost());
                    i2 = java.lang.Math.max(i2, approvalLevelForDomain);
                    fillInfoMapForSamePackage(arrayMap, str, approvalLevelForDomain);
                }
                fillInfoMapForSamePackage(arrayMap, str, 0);
            }
        }
        return i2;
    }

    private void fillInfoMapForSamePackage(@android.annotation.NonNull android.util.ArrayMap<android.content.pm.ResolveInfo, java.lang.Integer> arrayMap, @android.annotation.NonNull java.lang.String str, @com.android.server.pm.verify.domain.DomainVerificationManagerInternal.ApprovalLevel int i) {
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (java.util.Objects.equals(str, arrayMap.keyAt(i2).getComponentInfo().packageName)) {
                arrayMap.setValueAt(i2, java.lang.Integer.valueOf(i));
            }
        }
    }

    @android.annotation.NonNull
    private void filterToLastFirstInstalled(@android.annotation.NonNull android.util.ArrayMap<android.content.pm.ResolveInfo, java.lang.Integer> arrayMap, @android.annotation.NonNull java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        int size = arrayMap.size();
        java.lang.String str = null;
        long j = Long.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            java.lang.String str2 = arrayMap.keyAt(i).getComponentInfo().packageName;
            com.android.server.pm.pkg.PackageStateInternal apply = function.apply(str2);
            if (apply != null) {
                long earliestFirstInstallTime = com.android.server.pm.pkg.PackageStateUtils.getEarliestFirstInstallTime(apply.getUserStates());
                if (earliestFirstInstallTime > j) {
                    str = str2;
                    j = earliestFirstInstallTime;
                }
            }
        }
        for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
            if (!java.util.Objects.equals(str, arrayMap.keyAt(size2).getComponentInfo().packageName)) {
                arrayMap.removeAt(size2);
            }
        }
    }

    @android.annotation.NonNull
    private void filterToLastDeclared(@android.annotation.NonNull java.util.List<android.content.pm.ResolveInfo> list, @android.annotation.NonNull java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        for (int i = 0; i < list.size(); i++) {
            android.content.pm.ResolveInfo resolveInfo = list.get(i);
            java.lang.String str = resolveInfo.getComponentInfo().packageName;
            com.android.server.pm.pkg.PackageStateInternal apply = function.apply(str);
            com.android.server.pm.pkg.AndroidPackage pkg = apply == null ? null : apply.getPkg();
            if (pkg != null) {
                int indexOfIntentFilterEntry = indexOfIntentFilterEntry(pkg, resolveInfo);
                int size = list.size();
                while (true) {
                    size--;
                    if (size < i + 1) {
                        break;
                    }
                    android.content.pm.ResolveInfo resolveInfo2 = list.get(size);
                    if (java.util.Objects.equals(str, resolveInfo2.getComponentInfo().packageName)) {
                        int indexOfIntentFilterEntry2 = indexOfIntentFilterEntry(pkg, resolveInfo2);
                        if (indexOfIntentFilterEntry2 > indexOfIntentFilterEntry) {
                            resolveInfo = resolveInfo2;
                            indexOfIntentFilterEntry = indexOfIntentFilterEntry2;
                        }
                        list.remove(size);
                    }
                }
                list.set(i, resolveInfo);
            }
        }
    }

    private int indexOfIntentFilterEntry(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull android.content.pm.ResolveInfo resolveInfo) {
        java.util.List activities = androidPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            if (java.util.Objects.equals(((com.android.internal.pm.pkg.component.ParsedActivity) activities.get(i)).getComponentName(), resolveInfo.getComponentInfo().getComponentName())) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal
    public int approvalLevelForDomain(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull android.content.Intent intent, long j, int i) {
        java.lang.String packageName = packageStateInternal.getPackageName();
        boolean z = (intent.getFlags() & 8) != 0;
        if (!com.android.server.pm.verify.domain.DomainVerificationUtils.isDomainVerificationIntent(intent, j)) {
            if (z) {
                debugApproval(packageName, intent, i, false, "not valid intent");
            }
            return 0;
        }
        int approvalLevelForDomain = approvalLevelForDomain(packageStateInternal, intent.getData().getHost(), false, i, z, intent);
        if (z) {
            android.util.Slog.d("DomainVerificationServiceApproval", "Final approval level for " + packageStateInternal.getPackageName() + " for host " + intent.getData().getHost() + " is " + approvalLevelForDomain);
        }
        return approvalLevelForDomain;
    }

    private int approvalLevelForDomain(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull java.lang.String str, boolean z, int i, boolean z2, @android.annotation.NonNull java.lang.Object obj) {
        int approvalLevelForDomainInternal = approvalLevelForDomainInternal(packageStateInternal, str, z, i, z2, obj);
        if (z && approvalLevelForDomainInternal == 0) {
            com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
            if (!userStateOrDefault.isInstalled()) {
                return -4;
            }
            com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
            if (pkg != null) {
                if (!com.android.server.pm.pkg.PackageUserStateUtils.isPackageEnabled(userStateOrDefault, pkg)) {
                    return -3;
                }
                if (this.mCollector.containsAutoVerifyDomain(packageStateInternal.getPkg(), str)) {
                    return -1;
                }
            }
        }
        return approvalLevelForDomainInternal;
    }

    private int approvalLevelForDomainInternal(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull java.lang.String str, boolean z, int i, boolean z2, @android.annotation.NonNull java.lang.Object obj) {
        java.lang.String packageName = packageStateInternal.getPackageName();
        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal.getPkg();
        if (pkg != null && z && !this.mCollector.containsWebDomain(pkg, str)) {
            if (z2) {
                debugApproval(packageName, obj, i, false, "domain not declared");
                return -2;
            }
            return -2;
        }
        com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal = packageStateInternal.getUserStates().get(i);
        if (packageUserStateInternal == null) {
            if (z2) {
                debugApproval(packageName, obj, i, false, "PackageUserState unavailable");
            }
            return 0;
        }
        if (!packageUserStateInternal.isInstalled()) {
            if (z2) {
                debugApproval(packageName, obj, i, false, "package not installed for user");
            }
            return 0;
        }
        if (!com.android.server.pm.pkg.PackageUserStateUtils.isPackageEnabled(packageUserStateInternal, pkg)) {
            if (z2) {
                debugApproval(packageName, obj, i, false, "package not enabled for user");
            }
            return 0;
        }
        if (packageUserStateInternal.isSuspended()) {
            if (z2) {
                debugApproval(packageName, obj, i, false, "package suspended for user");
            }
            return 0;
        }
        if (pkg != null && !com.android.server.pm.verify.domain.DomainVerificationUtils.isChangeEnabled(this.mPlatformCompat, pkg, SETTINGS_API_V2)) {
            switch (this.mLegacySettings.getUserState(packageName, i)) {
                case 1:
                case 4:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 0;
            }
        }
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mAttachedPkgStates.get(packageName);
                if (domainVerificationPkgState == null) {
                    if (z2) {
                        debugApproval(packageName, obj, i, false, "pkgState unavailable");
                    }
                    return 0;
                }
                com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState userState = domainVerificationPkgState.getUserState(i);
                if (userState != null && !userState.isLinkHandlingAllowed()) {
                    if (z2) {
                        debugApproval(packageName, obj, i, false, "link handling not allowed");
                    }
                    return 0;
                }
                if (pkg != null && packageStateInternal.getUserStateOrDefault(i).isInstantApp() && this.mCollector.collectValidAutoVerifyDomains(pkg).contains(str)) {
                    return 5;
                }
                android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = domainVerificationPkgState.getStateMap();
                java.lang.Integer num = stateMap.get(str);
                if (num != null && android.content.pm.verify.domain.DomainVerificationState.isVerified(num.intValue())) {
                    if (z2) {
                        debugApproval(packageName, obj, i, true, "host verified exactly");
                    }
                    return 4;
                }
                int size = stateMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (android.content.pm.verify.domain.DomainVerificationState.isVerified(stateMap.valueAt(i2).intValue())) {
                        java.lang.String keyAt = stateMap.keyAt(i2);
                        if (keyAt.startsWith("*.") && str.endsWith(keyAt.substring(2))) {
                            if (z2) {
                                debugApproval(packageName, obj, i, true, "host verified by wildcard");
                            }
                            return 4;
                        }
                    }
                }
                if (userState == null) {
                    if (z2) {
                        debugApproval(packageName, obj, i, false, "userState unavailable");
                    }
                    return 0;
                }
                android.util.ArraySet<java.lang.String> enabledHosts = userState.getEnabledHosts();
                if (enabledHosts.contains(str)) {
                    if (z2) {
                        debugApproval(packageName, obj, i, true, "host enabled by user exactly");
                    }
                    return 3;
                }
                int size2 = enabledHosts.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    java.lang.String valueAt = enabledHosts.valueAt(i3);
                    if (valueAt.startsWith("*.") && str.endsWith(valueAt.substring(2))) {
                        if (z2) {
                            debugApproval(packageName, obj, i, true, "host enabled by user through wildcard");
                        }
                        return 3;
                    }
                }
                if (z2) {
                    debugApproval(packageName, obj, i, false, "not approved");
                }
                return 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.Pair<java.util.List<java.lang.String>, java.lang.Integer> getApprovedPackagesLocked(@android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.NonNull com.android.server.pm.Computer computer) {
        int approvalLevelForDomain;
        boolean z = i2 < 0;
        java.util.List emptyList = java.util.Collections.emptyList();
        int size = this.mAttachedPkgStates.size();
        java.util.List list = emptyList;
        int i3 = i2;
        for (int i4 = 0; i4 < size; i4++) {
            java.lang.String packageName = this.mAttachedPkgStates.valueAt(i4).getPackageName();
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(packageName);
            if (packageStateInternal != null && (approvalLevelForDomain = approvalLevelForDomain(packageStateInternal, str, z, i, false, str)) >= i2) {
                if (approvalLevelForDomain > i3) {
                    list.clear();
                    i3 = approvalLevelForDomain;
                    list = com.android.internal.util.CollectionUtils.add(list, packageName);
                } else if (approvalLevelForDomain == i3) {
                    list = com.android.internal.util.CollectionUtils.add(list, packageName);
                }
            }
        }
        if (list.isEmpty()) {
            return android.util.Pair.create(list, 0);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size2 = list.size();
        long j = Long.MIN_VALUE;
        for (int i5 = 0; i5 < size2; i5++) {
            java.lang.String str2 = (java.lang.String) list.get(i5);
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = computer.getPackageStateInternal(str2);
            if (packageStateInternal2 != null) {
                long firstInstallTimeMillis = packageStateInternal2.getUserStateOrDefault(i).getFirstInstallTimeMillis();
                if (firstInstallTimeMillis > j) {
                    arrayList.clear();
                    arrayList.add(str2);
                    j = firstInstallTimeMillis;
                } else if (firstInstallTimeMillis == j) {
                    arrayList.add(str2);
                }
            }
        }
        return android.util.Pair.create(arrayList, java.lang.Integer.valueOf(i3));
    }

    private void debugApproval(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.Object obj, int i, boolean z, @android.annotation.NonNull java.lang.String str2) {
        android.util.Slog.d("DomainVerificationServiceApproval", str + " was " + (z ? "approved" : "denied") + " for " + obj + " for user " + i + ": " + str2);
    }

    private static class GetAttachedResult {
        private final int mErrorCode;

        @android.annotation.Nullable
        private final com.android.server.pm.verify.domain.models.DomainVerificationPkgState mPkgState;

        GetAttachedResult(@android.annotation.Nullable com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, int i) {
            this.mPkgState = domainVerificationPkgState;
            this.mErrorCode = i;
        }

        @android.annotation.NonNull
        static com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult error(@android.content.pm.verify.domain.DomainVerificationManager.Error int i) {
            return new com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult(null, i);
        }

        @android.annotation.NonNull
        static com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult success(@android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState) {
            return new com.android.server.pm.verify.domain.DomainVerificationService.GetAttachedResult(domainVerificationPkgState, 0);
        }

        @android.annotation.NonNull
        com.android.server.pm.verify.domain.models.DomainVerificationPkgState getPkgState() {
            return this.mPkgState;
        }

        boolean isError() {
            return this.mErrorCode != 0;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }
}
