package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class DevicePolicyEngine {
    private static final java.lang.String CELLULAR_2G_USER_RESTRICTION_ID = android.app.admin.DevicePolicyIdentifiers.getIdentifierForUserRestriction("no_cellular_2g");
    private static final int DEFAULT_POLICY_SIZE_LIMIT = -1;
    static final java.lang.String DEVICE_LOCK_CONTROLLER_ROLE = "android.app.role.SYSTEM_FINANCED_DEVICE_CONTROLLER";
    static final java.lang.String TAG = "DevicePolicyEngine";
    private final android.util.SparseArray<java.util.HashMap<com.android.server.devicepolicy.EnforcingAdmin, java.lang.Integer>> mAdminPolicySize;
    private final android.content.Context mContext;
    private final com.android.server.devicepolicy.DeviceAdminServiceController mDeviceAdminServiceController;
    private final android.util.SparseArray<java.util.Set<com.android.server.devicepolicy.EnforcingAdmin>> mEnforcingAdmins;
    private final java.util.Map<android.app.admin.PolicyKey, com.android.server.devicepolicy.PolicyState<?>> mGlobalPolicies;
    private final android.util.SparseArray<java.util.Map<android.app.admin.PolicyKey, com.android.server.devicepolicy.PolicyState<?>>> mLocalPolicies;
    private final java.lang.Object mLock;
    private int mPolicySizeLimit = -1;
    private final android.os.UserManager mUserManager;

    DevicePolicyEngine(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.devicepolicy.DeviceAdminServiceController deviceAdminServiceController, @android.annotation.NonNull java.lang.Object obj) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(deviceAdminServiceController);
        this.mDeviceAdminServiceController = deviceAdminServiceController;
        java.util.Objects.requireNonNull(obj);
        this.mLock = obj;
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        this.mLocalPolicies = new android.util.SparseArray<>();
        this.mGlobalPolicies = new java.util.HashMap();
        this.mEnforcingAdmins = new android.util.SparseArray<>();
        this.mAdminPolicySize = new android.util.SparseArray<>();
    }

    private void maybeForceEnforcementRefreshLocked(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<?> policyDefinition) {
        try {
            if (shouldForceEnforcementRefresh(policyDefinition)) {
                forceEnforcementRefreshLocked(policyDefinition);
            }
        } catch (java.lang.Throwable th) {
            android.util.Log.e(TAG, "Exception throw during maybeForceEnforcementRefreshLocked", th);
        }
    }

    private boolean shouldForceEnforcementRefresh(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<?> policyDefinition) {
        android.app.admin.PolicyKey policyKey;
        if (policyDefinition == null || (policyKey = policyDefinition.getPolicyKey()) == null || !(policyKey instanceof android.app.admin.UserRestrictionPolicyKey)) {
            return false;
        }
        return true;
    }

    private void forceEnforcementRefreshLocked(final com.android.server.devicepolicy.PolicyDefinition<java.lang.Boolean> policyDefinition) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda5
            public final void runOrThrow() {
                com.android.server.devicepolicy.DevicePolicyEngine.this.lambda$forceEnforcementRefreshLocked$0(policyDefinition);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forceEnforcementRefreshLocked$0(com.android.server.devicepolicy.PolicyDefinition policyDefinition) throws java.lang.Exception {
        android.app.admin.PolicyValue booleanPolicyValue = new android.app.admin.BooleanPolicyValue(false);
        try {
            booleanPolicyValue = getGlobalPolicyStateLocked(policyDefinition).getCurrentResolvedPolicy();
        } catch (java.lang.IllegalArgumentException e) {
        }
        enforcePolicy(policyDefinition, booleanPolicyValue, -1);
        for (android.content.pm.UserInfo userInfo : this.mUserManager.getUsers()) {
            android.app.admin.PolicyValue booleanPolicyValue2 = new android.app.admin.BooleanPolicyValue(false);
            try {
                booleanPolicyValue2 = getLocalPolicyStateLocked(policyDefinition, userInfo.id).getCurrentResolvedPolicy();
            } catch (java.lang.IllegalArgumentException e2) {
            }
            enforcePolicy(policyDefinition, booleanPolicyValue2, userInfo.id);
        }
    }

    <V> void setLocalPolicy(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, @android.annotation.Nullable android.app.admin.PolicyValue<V> policyValue, int i, boolean z) {
        boolean addPolicy;
        java.util.Objects.requireNonNull(policyDefinition);
        java.util.Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                com.android.server.devicepolicy.PolicyState<V> localPolicyStateLocked = getLocalPolicyStateLocked(policyDefinition, i);
                android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled();
                if (policyDefinition.isNonCoexistablePolicy()) {
                    setNonCoexistableLocalPolicyLocked(policyDefinition, localPolicyStateLocked, enforcingAdmin, policyValue, i, z);
                    return;
                }
                if (hasGlobalPolicyLocked(policyDefinition)) {
                    addPolicy = localPolicyStateLocked.addPolicy(enforcingAdmin, policyValue, getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins());
                } else {
                    addPolicy = localPolicyStateLocked.addPolicy(enforcingAdmin, policyValue);
                }
                if (!z) {
                    maybeForceEnforcementRefreshLocked(policyDefinition);
                    if (addPolicy) {
                        onLocalPolicyChangedLocked(policyDefinition, enforcingAdmin, i);
                    }
                    boolean equals = java.util.Objects.equals(localPolicyStateLocked.getCurrentResolvedPolicy(), policyValue);
                    int i2 = 0;
                    if (!equals && policyDefinition.getPolicyKey().getIdentifier().equals("userControlDisabledPackages")) {
                        android.app.admin.PolicyValue<V> currentResolvedPolicy = localPolicyStateLocked.getCurrentResolvedPolicy();
                        equals = (currentResolvedPolicy == null || policyValue == null || !((java.util.Set) currentResolvedPolicy.getValue()).containsAll((java.util.Collection) policyValue.getValue())) ? false : true;
                    }
                    if (!equals) {
                        i2 = 1;
                    }
                    sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, i2, i);
                }
                updateDeviceAdminServiceOnPolicyAddLocked(enforcingAdmin);
                write();
                applyToInheritableProfiles(policyDefinition, enforcingAdmin, policyValue, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private <V> void setNonCoexistableLocalPolicyLocked(com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, com.android.server.devicepolicy.PolicyState<V> policyState, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, @android.annotation.Nullable android.app.admin.PolicyValue<V> policyValue, int i, boolean z) {
        if (policyValue == null) {
            policyState.removePolicy(enforcingAdmin);
        } else {
            policyState.addPolicy(enforcingAdmin, policyValue);
        }
        if (!z) {
            enforcePolicy(policyDefinition, policyValue, i);
        }
        if (policyState.getPoliciesSetByAdmins().isEmpty()) {
            removeLocalPolicyStateLocked(policyDefinition, i);
        }
        updateDeviceAdminServiceOnPolicyAddLocked(enforcingAdmin);
        write();
    }

    <V> void setLocalPolicy(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, @android.annotation.NonNull android.app.admin.PolicyValue<V> policyValue, int i) {
        setLocalPolicy(policyDefinition, enforcingAdmin, policyValue, i, false);
    }

    <V> void removeLocalPolicy(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, int i) {
        boolean removePolicy;
        java.util.Objects.requireNonNull(policyDefinition);
        java.util.Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                maybeForceEnforcementRefreshLocked(policyDefinition);
                if (hasLocalPolicyLocked(policyDefinition, i)) {
                    com.android.server.devicepolicy.PolicyState<V> localPolicyStateLocked = getLocalPolicyStateLocked(policyDefinition, i);
                    android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled();
                    if (policyDefinition.isNonCoexistablePolicy()) {
                        setNonCoexistableLocalPolicyLocked(policyDefinition, localPolicyStateLocked, enforcingAdmin, null, i, false);
                        return;
                    }
                    if (hasGlobalPolicyLocked(policyDefinition)) {
                        removePolicy = localPolicyStateLocked.removePolicy(enforcingAdmin, getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins());
                    } else {
                        removePolicy = localPolicyStateLocked.removePolicy(enforcingAdmin);
                    }
                    if (removePolicy) {
                        onLocalPolicyChangedLocked(policyDefinition, enforcingAdmin, i);
                    }
                    sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 2, i);
                    if (localPolicyStateLocked.getPoliciesSetByAdmins().isEmpty()) {
                        removeLocalPolicyStateLocked(policyDefinition, i);
                    }
                    updateDeviceAdminServiceOnPolicyRemoveLocked(enforcingAdmin);
                    write();
                    applyToInheritableProfiles(policyDefinition, enforcingAdmin, null, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private <V> void applyToInheritableProfiles(final com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, final com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, final android.app.admin.PolicyValue<V> policyValue, final int i) {
        if (policyDefinition.isInheritable()) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda3
                public final void runOrThrow() {
                    com.android.server.devicepolicy.DevicePolicyEngine.this.lambda$applyToInheritableProfiles$1(i, policyValue, policyDefinition, enforcingAdmin);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyToInheritableProfiles$1(int i, android.app.admin.PolicyValue policyValue, com.android.server.devicepolicy.PolicyDefinition policyDefinition, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) throws java.lang.Exception {
        for (android.content.pm.UserInfo userInfo : this.mUserManager.getProfiles(i)) {
            int identifier = userInfo.getUserHandle().getIdentifier();
            if (isProfileOfUser(identifier, i) && isInheritDevicePolicyFromParent(userInfo)) {
                if (policyValue != null) {
                    setLocalPolicy(policyDefinition, enforcingAdmin, policyValue, identifier);
                } else {
                    removeLocalPolicy(policyDefinition, enforcingAdmin, identifier);
                }
            }
        }
    }

    private boolean isProfileOfUser(int i, int i2) {
        android.content.pm.UserInfo profileParent = this.mUserManager.getProfileParent(i);
        return (i == i2 || profileParent == null || profileParent.getUserHandle().getIdentifier() != i2) ? false : true;
    }

    private boolean isInheritDevicePolicyFromParent(android.content.pm.UserInfo userInfo) {
        return this.mUserManager.getUserProperties(userInfo.getUserHandle()) != null && this.mUserManager.getUserProperties(userInfo.getUserHandle()).getInheritDevicePolicy() == 1;
    }

    private <V> void onLocalPolicyChangedLocked(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, int i) {
        com.android.server.devicepolicy.PolicyState<V> localPolicyStateLocked = getLocalPolicyStateLocked(policyDefinition, i);
        enforcePolicy(policyDefinition, localPolicyStateLocked.getCurrentResolvedPolicy(), i);
        sendPolicyChangedToAdminsLocked(localPolicyStateLocked, enforcingAdmin, policyDefinition, i);
        if (hasGlobalPolicyLocked(policyDefinition)) {
            sendPolicyChangedToAdminsLocked(getGlobalPolicyStateLocked(policyDefinition), enforcingAdmin, policyDefinition, i);
        }
        sendDevicePolicyChangedToSystem(i);
    }

    <V> void setGlobalPolicy(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, @android.annotation.NonNull android.app.admin.PolicyValue<V> policyValue) {
        setGlobalPolicy(policyDefinition, enforcingAdmin, policyValue, false);
    }

    <V> void setGlobalPolicy(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, @android.annotation.NonNull android.app.admin.PolicyValue<V> policyValue, boolean z) {
        java.util.Objects.requireNonNull(policyDefinition);
        java.util.Objects.requireNonNull(enforcingAdmin);
        java.util.Objects.requireNonNull(policyValue);
        synchronized (this.mLock) {
            try {
                com.android.server.devicepolicy.PolicyState<V> globalPolicyStateLocked = getGlobalPolicyStateLocked(policyDefinition);
                android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled();
                if (checkFor2gFailure(policyDefinition, enforcingAdmin)) {
                    android.util.Log.i(TAG, "Device does not support capabilities required to disable 2g. Not setting global policy state.");
                    return;
                }
                boolean addPolicy = globalPolicyStateLocked.addPolicy(enforcingAdmin, policyValue);
                boolean applyGlobalPolicyOnUsersWithLocalPoliciesLocked = applyGlobalPolicyOnUsersWithLocalPoliciesLocked(policyDefinition, enforcingAdmin, policyValue, z);
                if (!z) {
                    maybeForceEnforcementRefreshLocked(policyDefinition);
                    if (addPolicy) {
                        onGlobalPolicyChangedLocked(policyDefinition, enforcingAdmin);
                    }
                    boolean equals = java.util.Objects.equals(globalPolicyStateLocked.getCurrentResolvedPolicy(), policyValue);
                    int i = 0;
                    if (!equals && policyDefinition.getPolicyKey().getIdentifier().equals("userControlDisabledPackages")) {
                        android.app.admin.PolicyValue<V> currentResolvedPolicy = globalPolicyStateLocked.getCurrentResolvedPolicy();
                        equals = currentResolvedPolicy != null && ((java.util.Set) currentResolvedPolicy.getValue()).containsAll((java.util.Collection) policyValue.getValue());
                    }
                    if (!(equals && applyGlobalPolicyOnUsersWithLocalPoliciesLocked)) {
                        i = 1;
                    }
                    sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, i, -1);
                }
                updateDeviceAdminServiceOnPolicyAddLocked(enforcingAdmin);
                write();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    <V> void removeGlobalPolicy(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        java.util.Objects.requireNonNull(policyDefinition);
        java.util.Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                com.android.server.devicepolicy.PolicyState<V> globalPolicyStateLocked = getGlobalPolicyStateLocked(policyDefinition);
                android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled();
                boolean removePolicy = globalPolicyStateLocked.removePolicy(enforcingAdmin);
                maybeForceEnforcementRefreshLocked(policyDefinition);
                if (removePolicy) {
                    onGlobalPolicyChangedLocked(policyDefinition, enforcingAdmin);
                }
                applyGlobalPolicyOnUsersWithLocalPoliciesLocked(policyDefinition, enforcingAdmin, null, false);
                sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 2, -1);
                if (globalPolicyStateLocked.getPoliciesSetByAdmins().isEmpty()) {
                    removeGlobalPolicyStateLocked(policyDefinition);
                }
                updateDeviceAdminServiceOnPolicyRemoveLocked(enforcingAdmin);
                write();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private <V> void onGlobalPolicyChangedLocked(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        com.android.server.devicepolicy.PolicyState<V> globalPolicyStateLocked = getGlobalPolicyStateLocked(policyDefinition);
        enforcePolicy(policyDefinition, globalPolicyStateLocked.getCurrentResolvedPolicy(), -1);
        sendPolicyChangedToAdminsLocked(globalPolicyStateLocked, enforcingAdmin, policyDefinition, -1);
        sendDevicePolicyChangedToSystem(-1);
    }

    private <V> boolean applyGlobalPolicyOnUsersWithLocalPoliciesLocked(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, @android.annotation.Nullable android.app.admin.PolicyValue<V> policyValue, boolean z) {
        if (policyDefinition.isGlobalOnlyPolicy()) {
            return true;
        }
        boolean z2 = true;
        for (int i = 0; i < this.mLocalPolicies.size(); i++) {
            int keyAt = this.mLocalPolicies.keyAt(i);
            if (hasLocalPolicyLocked(policyDefinition, keyAt)) {
                com.android.server.devicepolicy.PolicyState<V> localPolicyStateLocked = getLocalPolicyStateLocked(policyDefinition, keyAt);
                if (localPolicyStateLocked.resolvePolicy(getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins()) && !z) {
                    enforcePolicy(policyDefinition, localPolicyStateLocked.getCurrentResolvedPolicy(), keyAt);
                    sendPolicyChangedToAdminsLocked(localPolicyStateLocked, enforcingAdmin, policyDefinition, keyAt);
                }
                if (policyDefinition.getPolicyKey().getIdentifier().equals("userControlDisabledPackages")) {
                    if (!java.util.Objects.equals(policyValue, localPolicyStateLocked.getCurrentResolvedPolicy())) {
                        android.app.admin.PolicyValue<V> currentResolvedPolicy = localPolicyStateLocked.getCurrentResolvedPolicy();
                        z2 &= (currentResolvedPolicy == null || policyValue == null || !((java.util.Set) currentResolvedPolicy.getValue()).containsAll((java.util.Collection) policyValue.getValue())) ? false : true;
                    }
                } else {
                    z2 &= java.util.Objects.equals(policyValue, localPolicyStateLocked.getCurrentResolvedPolicy());
                }
            }
        }
        return z2;
    }

    @android.annotation.Nullable
    <V> V getResolvedPolicy(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, int i) {
        V v;
        android.app.admin.PolicyValue<V> policyValue;
        java.util.Objects.requireNonNull(policyDefinition);
        synchronized (this.mLock) {
            try {
                v = null;
                if (hasLocalPolicyLocked(policyDefinition, i)) {
                    policyValue = getLocalPolicyStateLocked(policyDefinition, i).getCurrentResolvedPolicy();
                } else if (!hasGlobalPolicyLocked(policyDefinition)) {
                    policyValue = null;
                } else {
                    policyValue = getGlobalPolicyStateLocked(policyDefinition).getCurrentResolvedPolicy();
                }
                if (policyValue != null) {
                    v = (V) policyValue.getValue();
                }
            } finally {
            }
        }
        return v;
    }

    @android.annotation.Nullable
    <V> V getLocalPolicySetByAdmin(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, int i) {
        java.util.Objects.requireNonNull(policyDefinition);
        java.util.Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                V v = null;
                if (!hasLocalPolicyLocked(policyDefinition, i)) {
                    return null;
                }
                android.app.admin.PolicyValue<V> policyValue = getLocalPolicyStateLocked(policyDefinition, i).getPoliciesSetByAdmins().get(enforcingAdmin);
                if (policyValue != null) {
                    v = (V) policyValue.getValue();
                }
                return v;
            } finally {
            }
        }
    }

    @android.annotation.Nullable
    <V> V getGlobalPolicySetByAdmin(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        java.util.Objects.requireNonNull(policyDefinition);
        java.util.Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                V v = null;
                if (!hasGlobalPolicyLocked(policyDefinition)) {
                    return null;
                }
                android.app.admin.PolicyValue<V> policyValue = getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins().get(enforcingAdmin);
                if (policyValue != null) {
                    v = (V) policyValue.getValue();
                }
                return v;
            } finally {
            }
        }
    }

    @android.annotation.NonNull
    <V> java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> getLocalPoliciesSetByAdmins(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, int i) {
        java.util.Objects.requireNonNull(policyDefinition);
        synchronized (this.mLock) {
            try {
                if (!hasLocalPolicyLocked(policyDefinition, i)) {
                    return new java.util.LinkedHashMap<>();
                }
                return getLocalPolicyStateLocked(policyDefinition, i).getPoliciesSetByAdmins();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    <V> java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> getGlobalPoliciesSetByAdmins(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition) {
        java.util.Objects.requireNonNull(policyDefinition);
        synchronized (this.mLock) {
            try {
                if (!hasGlobalPolicyLocked(policyDefinition)) {
                    return new java.util.LinkedHashMap<>();
                }
                return getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    <V> java.util.Set<android.app.admin.PolicyKey> getLocalPolicyKeysSetByAdmin(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, int i) {
        java.util.Objects.requireNonNull(policyDefinition);
        java.util.Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                if (policyDefinition.isGlobalOnlyPolicy() || !this.mLocalPolicies.contains(i)) {
                    return java.util.Set.of();
                }
                java.util.HashSet hashSet = new java.util.HashSet();
                for (android.app.admin.PolicyKey policyKey : this.mLocalPolicies.get(i).keySet()) {
                    if (policyKey.hasSameIdentifierAs(policyDefinition.getPolicyKey()) && this.mLocalPolicies.get(i).get(policyKey).getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
                        hashSet.add(policyKey);
                    }
                }
                return hashSet;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    <V> java.util.Set<android.app.admin.PolicyKey> getLocalPolicyKeysSetByAllAdmins(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, int i) {
        java.util.Objects.requireNonNull(policyDefinition);
        synchronized (this.mLock) {
            try {
                if (policyDefinition.isGlobalOnlyPolicy() || !this.mLocalPolicies.contains(i)) {
                    return java.util.Set.of();
                }
                java.util.HashSet hashSet = new java.util.HashSet();
                for (android.app.admin.PolicyKey policyKey : this.mLocalPolicies.get(i).keySet()) {
                    if (policyKey.hasSameIdentifierAs(policyDefinition.getPolicyKey())) {
                        hashSet.add(policyKey);
                    }
                }
                return hashSet;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    java.util.Set<android.app.admin.UserRestrictionPolicyKey> getUserRestrictionPolicyKeysForAdmin(@android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, int i) {
        java.util.Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                if (i == -1) {
                    return getUserRestrictionPolicyKeysForAdminLocked(this.mGlobalPolicies, enforcingAdmin);
                }
                if (!this.mLocalPolicies.contains(i)) {
                    return java.util.Set.of();
                }
                return getUserRestrictionPolicyKeysForAdminLocked(this.mLocalPolicies.get(i), enforcingAdmin);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    <V> void transferPolicies(com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin2) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator it = new java.util.HashSet(this.mGlobalPolicies.keySet()).iterator();
                while (it.hasNext()) {
                    com.android.server.devicepolicy.PolicyState<?> policyState = this.mGlobalPolicies.get((android.app.admin.PolicyKey) it.next());
                    if (policyState.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
                        setGlobalPolicy(policyState.getPolicyDefinition(), enforcingAdmin2, policyState.getPoliciesSetByAdmins().get(enforcingAdmin));
                    }
                }
                for (int i = 0; i < this.mLocalPolicies.size(); i++) {
                    int keyAt = this.mLocalPolicies.keyAt(i);
                    java.util.Iterator it2 = new java.util.HashSet(this.mLocalPolicies.get(keyAt).keySet()).iterator();
                    while (it2.hasNext()) {
                        com.android.server.devicepolicy.PolicyState<?> policyState2 = this.mLocalPolicies.get(keyAt).get((android.app.admin.PolicyKey) it2.next());
                        if (policyState2.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
                            setLocalPolicy(policyState2.getPolicyDefinition(), enforcingAdmin2, policyState2.getPoliciesSetByAdmins().get(enforcingAdmin), keyAt);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        removePoliciesForAdmin(enforcingAdmin);
    }

    private java.util.Set<android.app.admin.UserRestrictionPolicyKey> getUserRestrictionPolicyKeysForAdminLocked(java.util.Map<android.app.admin.PolicyKey, com.android.server.devicepolicy.PolicyState<?>> map, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        android.app.admin.PolicyValue<?> policyValue;
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator<android.app.admin.PolicyKey> it = map.keySet().iterator();
        while (it.hasNext()) {
            android.app.admin.UserRestrictionPolicyKey userRestrictionPolicyKey = (android.app.admin.PolicyKey) it.next();
            if (map.get(userRestrictionPolicyKey).getPolicyDefinition().isUserRestrictionPolicy() && (policyValue = map.get(userRestrictionPolicyKey).getPoliciesSetByAdmins().get(enforcingAdmin)) != null && ((java.lang.Boolean) policyValue.getValue()).booleanValue()) {
                hashSet.add(userRestrictionPolicyKey);
            }
        }
        return hashSet;
    }

    private <V> boolean hasLocalPolicyLocked(com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, int i) {
        if (!policyDefinition.isGlobalOnlyPolicy() && this.mLocalPolicies.contains(i) && this.mLocalPolicies.get(i).containsKey(policyDefinition.getPolicyKey())) {
            return !this.mLocalPolicies.get(i).get(policyDefinition.getPolicyKey()).getPoliciesSetByAdmins().isEmpty();
        }
        return false;
    }

    private <V> boolean hasGlobalPolicyLocked(com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition) {
        if (!policyDefinition.isLocalOnlyPolicy() && this.mGlobalPolicies.containsKey(policyDefinition.getPolicyKey())) {
            return !this.mGlobalPolicies.get(policyDefinition.getPolicyKey()).getPoliciesSetByAdmins().isEmpty();
        }
        return false;
    }

    @android.annotation.NonNull
    private <V> com.android.server.devicepolicy.PolicyState<V> getLocalPolicyStateLocked(com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, int i) {
        if (policyDefinition.isGlobalOnlyPolicy()) {
            throw new java.lang.IllegalArgumentException(policyDefinition.getPolicyKey() + " is a global only policy.");
        }
        if (!this.mLocalPolicies.contains(i)) {
            this.mLocalPolicies.put(i, new java.util.HashMap());
        }
        if (!this.mLocalPolicies.get(i).containsKey(policyDefinition.getPolicyKey())) {
            this.mLocalPolicies.get(i).put(policyDefinition.getPolicyKey(), new com.android.server.devicepolicy.PolicyState<>(policyDefinition));
        }
        return getPolicyStateLocked(this.mLocalPolicies.get(i), policyDefinition);
    }

    private <V> void removeLocalPolicyStateLocked(com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, int i) {
        if (!this.mLocalPolicies.contains(i)) {
            return;
        }
        this.mLocalPolicies.get(i).remove(policyDefinition.getPolicyKey());
    }

    @android.annotation.NonNull
    private <V> com.android.server.devicepolicy.PolicyState<V> getGlobalPolicyStateLocked(com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition) {
        if (policyDefinition.isLocalOnlyPolicy()) {
            throw new java.lang.IllegalArgumentException(policyDefinition.getPolicyKey() + " is a local only policy.");
        }
        if (!this.mGlobalPolicies.containsKey(policyDefinition.getPolicyKey())) {
            this.mGlobalPolicies.put(policyDefinition.getPolicyKey(), new com.android.server.devicepolicy.PolicyState<>(policyDefinition));
        }
        return getPolicyStateLocked(this.mGlobalPolicies, policyDefinition);
    }

    private <V> void removeGlobalPolicyStateLocked(com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition) {
        this.mGlobalPolicies.remove(policyDefinition.getPolicyKey());
    }

    private static <V> com.android.server.devicepolicy.PolicyState<V> getPolicyStateLocked(java.util.Map<android.app.admin.PolicyKey, com.android.server.devicepolicy.PolicyState<?>> map, com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition) {
        try {
            return (com.android.server.devicepolicy.PolicyState) map.get(policyDefinition.getPolicyKey());
        } catch (java.lang.ClassCastException e) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <V> void enforcePolicy(com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.Nullable android.app.admin.PolicyValue<V> policyValue, int i) {
        policyDefinition.enforcePolicy(policyValue == null ? null : policyValue.getValue(), this.mContext, i);
    }

    private void sendDevicePolicyChangedToSystem(final int i) {
        final android.content.Intent intent = new android.content.Intent("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intent.setFlags(1073741824);
        final android.os.Bundle bundle = new android.app.BroadcastOptions().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle();
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.devicepolicy.DevicePolicyEngine.this.lambda$sendDevicePolicyChangedToSystem$2(intent, i, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendDevicePolicyChangedToSystem$2(android.content.Intent intent, int i, android.os.Bundle bundle) throws java.lang.Exception {
        this.mContext.sendBroadcastAsUser(intent, new android.os.UserHandle(i), null, bundle);
    }

    private <V> void sendPolicyResultToAdmin(final com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, final com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, final int i, final int i2) {
        final android.content.Intent intent = new android.content.Intent("android.app.admin.action.DEVICE_POLICY_SET_RESULT");
        intent.setPackage(enforcingAdmin.getPackageName());
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.devicepolicy.DevicePolicyEngine.this.lambda$sendPolicyResultToAdmin$3(intent, enforcingAdmin, policyDefinition, i2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPolicyResultToAdmin$3(android.content.Intent intent, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, com.android.server.devicepolicy.PolicyDefinition policyDefinition, int i, int i2) throws java.lang.Exception {
        java.util.List queryBroadcastReceiversAsUser = this.mContext.getPackageManager().queryBroadcastReceiversAsUser(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(2L), enforcingAdmin.getUserId());
        if (queryBroadcastReceiversAsUser.isEmpty()) {
            android.util.Log.i(TAG, "Couldn't find any receivers that handle ACTION_DEVICE_POLICY_SET_RESULT in package " + enforcingAdmin.getPackageName());
            return;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        policyDefinition.getPolicyKey().writeToBundle(bundle);
        bundle.putInt("android.app.admin.extra.POLICY_TARGET_USER_ID", getTargetUser(enforcingAdmin.getUserId(), i));
        bundle.putInt("android.app.admin.extra.POLICY_UPDATE_RESULT_KEY", i2);
        intent.putExtras(bundle);
        maybeSendIntentToAdminReceivers(intent, android.os.UserHandle.of(enforcingAdmin.getUserId()), queryBroadcastReceiversAsUser);
    }

    private <V> void sendPolicyChangedToAdminsLocked(com.android.server.devicepolicy.PolicyState<V> policyState, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, int i) {
        for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin2 : policyState.getPoliciesSetByAdmins().keySet()) {
            if (!enforcingAdmin2.equals(enforcingAdmin)) {
                maybeSendOnPolicyChanged(enforcingAdmin2, policyDefinition, java.util.Objects.equals(policyState.getPoliciesSetByAdmins().get(enforcingAdmin2), policyState.getCurrentResolvedPolicy()) ? 0 : 1, i);
            }
        }
    }

    private <V> void maybeSendOnPolicyChanged(final com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, final com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, final int i, final int i2) {
        final android.content.Intent intent = new android.content.Intent("android.app.admin.action.DEVICE_POLICY_CHANGED");
        intent.setPackage(enforcingAdmin.getPackageName());
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda6
            public final void runOrThrow() {
                com.android.server.devicepolicy.DevicePolicyEngine.this.lambda$maybeSendOnPolicyChanged$4(intent, enforcingAdmin, policyDefinition, i2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeSendOnPolicyChanged$4(android.content.Intent intent, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, com.android.server.devicepolicy.PolicyDefinition policyDefinition, int i, int i2) throws java.lang.Exception {
        java.util.List queryBroadcastReceiversAsUser = this.mContext.getPackageManager().queryBroadcastReceiversAsUser(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(2L), enforcingAdmin.getUserId());
        if (queryBroadcastReceiversAsUser.isEmpty()) {
            android.util.Log.i(TAG, "Couldn't find any receivers that handle ACTION_DEVICE_POLICY_CHANGED in package " + enforcingAdmin.getPackageName());
            return;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        policyDefinition.getPolicyKey().writeToBundle(bundle);
        bundle.putInt("android.app.admin.extra.POLICY_TARGET_USER_ID", getTargetUser(enforcingAdmin.getUserId(), i));
        bundle.putInt("android.app.admin.extra.POLICY_UPDATE_RESULT_KEY", i2);
        intent.putExtras(bundle);
        intent.addFlags(268435456);
        maybeSendIntentToAdminReceivers(intent, android.os.UserHandle.of(enforcingAdmin.getUserId()), queryBroadcastReceiversAsUser);
    }

    private void maybeSendIntentToAdminReceivers(android.content.Intent intent, android.os.UserHandle userHandle, java.util.List<android.content.pm.ResolveInfo> list) {
        for (android.content.pm.ResolveInfo resolveInfo : list) {
            if ("android.permission.BIND_DEVICE_ADMIN".equals(resolveInfo.activityInfo.permission)) {
                this.mContext.sendBroadcastAsUser(intent, userHandle);
            } else {
                android.util.Log.w(TAG, "Receiver " + resolveInfo.activityInfo + " is not protected by BIND_DEVICE_ADMIN permission!");
            }
        }
    }

    private int getTargetUser(int i, int i2) {
        if (i2 == -1) {
            return -3;
        }
        if (i == i2) {
            return -1;
        }
        if (getProfileParentId(i) != i2) {
            return -3;
        }
        return -2;
    }

    private int getProfileParentId(final int i) {
        return ((java.lang.Integer) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda2
            public final java.lang.Object getOrThrow() {
                java.lang.Integer lambda$getProfileParentId$5;
                lambda$getProfileParentId$5 = com.android.server.devicepolicy.DevicePolicyEngine.this.lambda$getProfileParentId$5(i);
                return lambda$getProfileParentId$5;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getProfileParentId$5(int i) throws java.lang.Exception {
        android.content.pm.UserInfo profileParent = this.mUserManager.getProfileParent(i);
        if (profileParent != null) {
            i = profileParent.id;
        }
        return java.lang.Integer.valueOf(i);
    }

    private void updateDeviceAdminsServicesForUser(int i, boolean z, @android.annotation.NonNull java.lang.String str) {
        if (!z) {
            this.mDeviceAdminServiceController.stopServicesForUser(i, str);
            return;
        }
        for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin : getEnforcingAdminsOnUser(i)) {
            if (!enforcingAdmin.hasAuthority("enterprise")) {
                this.mDeviceAdminServiceController.startServiceForAdmin(enforcingAdmin.getPackageName(), i, str);
            }
        }
    }

    void handleStartUser(int i) {
        updateDeviceAdminsServicesForUser(i, true, "start-user");
    }

    void handleUnlockUser(int i) {
        updateDeviceAdminsServicesForUser(i, true, "unlock-user");
    }

    void handleStopUser(int i) {
        updateDeviceAdminsServicesForUser(i, false, "stop-user");
    }

    void handlePackageChanged(@android.annotation.Nullable final java.lang.String str, final int i, @android.annotation.Nullable final java.lang.String str2) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda7
            public final void runOrThrow() {
                com.android.server.devicepolicy.DevicePolicyEngine.this.lambda$handlePackageChanged$6(i, str2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handlePackageChanged$6(int i, java.lang.String str, java.lang.String str2) throws java.lang.Exception {
        java.util.Set<com.android.server.devicepolicy.EnforcingAdmin> enforcingAdminsOnUser = getEnforcingAdminsOnUser(i);
        if (str != null) {
            for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin : enforcingAdminsOnUser) {
                if (str.equals(enforcingAdmin.getPackageName())) {
                    removePoliciesForAdmin(enforcingAdmin);
                    return;
                }
            }
        }
        for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin2 : enforcingAdminsOnUser) {
            if (str2 == null || str2.equals(enforcingAdmin2.getPackageName())) {
                if (!isPackageInstalled(enforcingAdmin2.getPackageName(), i)) {
                    com.android.server.utils.Slogf.i(TAG, java.lang.String.format("Admin package %s not found for user %d, removing admin policies", enforcingAdmin2.getPackageName(), java.lang.Integer.valueOf(i)));
                    removePoliciesForAdmin(enforcingAdmin2);
                    return;
                }
            }
        }
        if (str2 != null) {
            updateDeviceAdminServiceOnPackageChanged(str2, i);
            removePersistentPreferredActivityPoliciesForPackage(str2, i);
        }
    }

    private void removePersistentPreferredActivityPoliciesForPackage(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Iterator<android.app.admin.PolicyKey> it = getLocalPolicyKeysSetByAllAdmins(com.android.server.devicepolicy.PolicyDefinition.GENERIC_PERSISTENT_PREFERRED_ACTIVITY, i).iterator();
        while (it.hasNext()) {
            android.app.admin.IntentFilterPolicyKey intentFilterPolicyKey = (android.app.admin.PolicyKey) it.next();
            if (!(intentFilterPolicyKey instanceof android.app.admin.IntentFilterPolicyKey)) {
                throw new java.lang.IllegalStateException("PolicyKey for PERSISTENT_PREFERRED_ACTIVITY is not of type IntentFilterPolicyKey");
            }
            android.content.IntentFilter intentFilter = intentFilterPolicyKey.getIntentFilter();
            java.util.Objects.requireNonNull(intentFilter);
            com.android.server.devicepolicy.PolicyDefinition<android.content.ComponentName> PERSISTENT_PREFERRED_ACTIVITY = com.android.server.devicepolicy.PolicyDefinition.PERSISTENT_PREFERRED_ACTIVITY(intentFilter);
            java.util.LinkedHashMap localPoliciesSetByAdmins = getLocalPoliciesSetByAdmins(PERSISTENT_PREFERRED_ACTIVITY, i);
            android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
            for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin : localPoliciesSetByAdmins.keySet()) {
                if (((android.app.admin.PolicyValue) localPoliciesSetByAdmins.get(enforcingAdmin)).getValue() != null && ((android.content.ComponentName) ((android.app.admin.PolicyValue) localPoliciesSetByAdmins.get(enforcingAdmin)).getValue()).getPackageName().equals(str)) {
                    try {
                        if (packageManager.getPackageInfo(str, 0L, i) != null && packageManager.getActivityInfo((android.content.ComponentName) ((android.app.admin.PolicyValue) localPoliciesSetByAdmins.get(enforcingAdmin)).getValue(), 0L, i) != null) {
                        }
                        com.android.server.utils.Slogf.e(TAG, java.lang.String.format("Persistent preferred activity in package %s not found for user %d, removing policy for admin", str, java.lang.Integer.valueOf(i)));
                        removeLocalPolicy(PERSISTENT_PREFERRED_ACTIVITY, enforcingAdmin, i);
                    } catch (android.os.RemoteException e) {
                        com.android.server.utils.Slogf.wtf(TAG, "Error handling package changes", e);
                    }
                }
            }
        }
    }

    private boolean isPackageInstalled(java.lang.String str, int i) {
        try {
            return android.app.AppGlobals.getPackageManager().getPackageInfo(str, 0L, i) != null;
        } catch (android.os.RemoteException e) {
            com.android.server.utils.Slogf.wtf(TAG, "Error handling package changes", e);
            return true;
        }
    }

    void handleUserRemoved(int i) {
        removeLocalPoliciesForUser(i);
        removePoliciesForAdminsOnUser(i);
    }

    void handleUserCreated(android.content.pm.UserInfo userInfo) {
        enforcePoliciesOnInheritableProfilesIfApplicable(userInfo);
    }

    void handleRoleChanged(@android.annotation.NonNull java.lang.String str, int i) {
        if (!DEVICE_LOCK_CONTROLLER_ROLE.equals(str)) {
            return;
        }
        java.lang.String roleAuthorityOf = com.android.server.devicepolicy.EnforcingAdmin.getRoleAuthorityOf(str);
        for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin : getEnforcingAdminsOnUser(i)) {
            if (enforcingAdmin.hasAuthority(roleAuthorityOf)) {
                enforcingAdmin.reloadRoleAuthorities();
                if (!enforcingAdmin.hasAuthority(roleAuthorityOf)) {
                    removePoliciesForAdmin(enforcingAdmin);
                }
            }
        }
    }

    private void enforcePoliciesOnInheritableProfilesIfApplicable(final android.content.pm.UserInfo userInfo) {
        if (!userInfo.isProfile()) {
            return;
        }
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                com.android.server.devicepolicy.DevicePolicyEngine.this.lambda$enforcePoliciesOnInheritableProfilesIfApplicable$7(userInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enforcePoliciesOnInheritableProfilesIfApplicable$7(android.content.pm.UserInfo userInfo) throws java.lang.Exception {
        int i;
        android.content.pm.UserInfo profileParent;
        android.content.pm.UserProperties userProperties = this.mUserManager.getUserProperties(userInfo.getUserHandle());
        if (userProperties == null || userProperties.getInheritDevicePolicy() != 1 || (profileParent = this.mUserManager.getProfileParent((i = userInfo.id))) == null || profileParent.getUserHandle().getIdentifier() == i) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mLocalPolicies.contains(profileParent.getUserHandle().getIdentifier())) {
                    java.util.Iterator<java.util.Map.Entry<android.app.admin.PolicyKey, com.android.server.devicepolicy.PolicyState<?>>> it = this.mLocalPolicies.get(profileParent.getUserHandle().getIdentifier()).entrySet().iterator();
                    while (it.hasNext()) {
                        enforcePolicyOnUserLocked(i, it.next().getValue());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private <V> void enforcePolicyOnUserLocked(int i, com.android.server.devicepolicy.PolicyState<V> policyState) {
        if (!policyState.getPolicyDefinition().isInheritable()) {
            return;
        }
        for (java.util.Map.Entry<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> entry : policyState.getPoliciesSetByAdmins().entrySet()) {
            setLocalPolicy(policyState.getPolicyDefinition(), entry.getKey(), entry.getValue(), i);
        }
    }

    @android.annotation.NonNull
    android.app.admin.DevicePolicyState getDevicePolicyState() {
        android.app.admin.DevicePolicyState devicePolicyState;
        synchronized (this.mLock) {
            try {
                java.util.HashMap hashMap = new java.util.HashMap();
                for (int i = 0; i < this.mLocalPolicies.size(); i++) {
                    android.os.UserHandle of = android.os.UserHandle.of(this.mLocalPolicies.keyAt(i));
                    hashMap.put(of, new java.util.HashMap());
                    for (android.app.admin.PolicyKey policyKey : this.mLocalPolicies.valueAt(i).keySet()) {
                        ((java.util.Map) hashMap.get(of)).put(policyKey, this.mLocalPolicies.valueAt(i).get(policyKey).getParcelablePolicyState());
                    }
                }
                if (!this.mGlobalPolicies.isEmpty()) {
                    hashMap.put(android.os.UserHandle.ALL, new java.util.HashMap());
                    for (android.app.admin.PolicyKey policyKey2 : this.mGlobalPolicies.keySet()) {
                        ((java.util.Map) hashMap.get(android.os.UserHandle.ALL)).put(policyKey2, this.mGlobalPolicies.get(policyKey2).getParcelablePolicyState());
                    }
                }
                devicePolicyState = new android.app.admin.DevicePolicyState(hashMap);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return devicePolicyState;
    }

    void removePoliciesForAdmin(com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator it = new java.util.HashSet(this.mGlobalPolicies.keySet()).iterator();
                while (it.hasNext()) {
                    com.android.server.devicepolicy.PolicyState<?> policyState = this.mGlobalPolicies.get((android.app.admin.PolicyKey) it.next());
                    if (policyState.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
                        removeGlobalPolicy(policyState.getPolicyDefinition(), enforcingAdmin);
                    }
                }
                for (int i = 0; i < this.mLocalPolicies.size(); i++) {
                    java.util.Iterator it2 = new java.util.HashSet(this.mLocalPolicies.get(this.mLocalPolicies.keyAt(i)).keySet()).iterator();
                    while (it2.hasNext()) {
                        com.android.server.devicepolicy.PolicyState<?> policyState2 = this.mLocalPolicies.get(this.mLocalPolicies.keyAt(i)).get((android.app.admin.PolicyKey) it2.next());
                        if (policyState2.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
                            removeLocalPolicy(policyState2.getPolicyDefinition(), enforcingAdmin, this.mLocalPolicies.keyAt(i));
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void removeLocalPoliciesForUser(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mLocalPolicies.contains(i)) {
                    java.util.Iterator it = new java.util.HashSet(this.mLocalPolicies.get(i).keySet()).iterator();
                    while (it.hasNext()) {
                        com.android.server.devicepolicy.PolicyState<?> policyState = this.mLocalPolicies.get(i).get((android.app.admin.PolicyKey) it.next());
                        java.util.Iterator it2 = new java.util.HashSet(policyState.getPoliciesSetByAdmins().keySet()).iterator();
                        while (it2.hasNext()) {
                            removeLocalPolicy(policyState.getPolicyDefinition(), (com.android.server.devicepolicy.EnforcingAdmin) it2.next(), i);
                        }
                    }
                    this.mLocalPolicies.remove(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void removePoliciesForAdminsOnUser(int i) {
        java.util.Iterator<com.android.server.devicepolicy.EnforcingAdmin> it = getEnforcingAdminsOnUser(i).iterator();
        while (it.hasNext()) {
            removePoliciesForAdmin(it.next());
        }
    }

    private void updateDeviceAdminServiceOnPackageChanged(@android.annotation.NonNull java.lang.String str, int i) {
        for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin : getEnforcingAdminsOnUser(i)) {
            if (!enforcingAdmin.hasAuthority("enterprise") && str.equals(enforcingAdmin.getPackageName())) {
                this.mDeviceAdminServiceController.startServiceForAdmin(str, i, "package-broadcast");
            }
        }
    }

    private void updateDeviceAdminServiceOnPolicyAddLocked(@android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        int userId = enforcingAdmin.getUserId();
        if (this.mEnforcingAdmins.contains(userId) && this.mEnforcingAdmins.get(userId).contains(enforcingAdmin)) {
            return;
        }
        if (!this.mEnforcingAdmins.contains(enforcingAdmin.getUserId())) {
            this.mEnforcingAdmins.put(enforcingAdmin.getUserId(), new java.util.HashSet());
        }
        this.mEnforcingAdmins.get(enforcingAdmin.getUserId()).add(enforcingAdmin);
        if (enforcingAdmin.hasAuthority("enterprise")) {
            return;
        }
        this.mDeviceAdminServiceController.startServiceForAdmin(enforcingAdmin.getPackageName(), userId, "policy-added");
    }

    private void updateDeviceAdminServiceOnPolicyRemoveLocked(@android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        if (doesAdminHavePoliciesLocked(enforcingAdmin)) {
            return;
        }
        int userId = enforcingAdmin.getUserId();
        if (this.mEnforcingAdmins.contains(userId)) {
            this.mEnforcingAdmins.get(userId).remove(enforcingAdmin);
            if (this.mEnforcingAdmins.get(userId).isEmpty()) {
                this.mEnforcingAdmins.remove(enforcingAdmin.getUserId());
            }
        }
        if (enforcingAdmin.hasAuthority("enterprise")) {
            return;
        }
        this.mDeviceAdminServiceController.stopServiceForAdmin(enforcingAdmin.getPackageName(), userId, "policy-removed");
    }

    private boolean doesAdminHavePoliciesLocked(@android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        java.util.Iterator<android.app.admin.PolicyKey> it = this.mGlobalPolicies.keySet().iterator();
        while (it.hasNext()) {
            if (this.mGlobalPolicies.get(it.next()).getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
                return true;
            }
        }
        for (int i = 0; i < this.mLocalPolicies.size(); i++) {
            java.util.Iterator<android.app.admin.PolicyKey> it2 = this.mLocalPolicies.get(this.mLocalPolicies.keyAt(i)).keySet().iterator();
            while (it2.hasNext()) {
                if (this.mLocalPolicies.get(this.mLocalPolicies.keyAt(i)).get(it2.next()).getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
                    return true;
                }
            }
        }
        return false;
    }

    @android.annotation.NonNull
    private java.util.Set<com.android.server.devicepolicy.EnforcingAdmin> getEnforcingAdminsOnUser(int i) {
        java.util.Set<com.android.server.devicepolicy.EnforcingAdmin> emptySet;
        synchronized (this.mLock) {
            try {
                emptySet = this.mEnforcingAdmins.contains(i) ? this.mEnforcingAdmins.get(i) : java.util.Collections.emptySet();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return emptySet;
    }

    private static <V> int sizeOf(android.app.admin.PolicyValue<V> policyValue) {
        try {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            obtain.writeParcelable(policyValue, 0);
            obtain.setDataPosition(0);
            return obtain.marshall().length;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error calculating size of policy: " + e);
            return 0;
        }
    }

    private <V> boolean handleAdminPolicySizeLimit(com.android.server.devicepolicy.PolicyState<V> policyState, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, android.app.admin.PolicyValue<V> policyValue, com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, int i) {
        int i2;
        int i3;
        if (this.mAdminPolicySize.contains(enforcingAdmin.getUserId()) && this.mAdminPolicySize.get(enforcingAdmin.getUserId()).containsKey(enforcingAdmin)) {
            i2 = this.mAdminPolicySize.get(enforcingAdmin.getUserId()).get(enforcingAdmin).intValue();
        } else {
            i2 = 0;
        }
        if (!policyState.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
            i3 = 0;
        } else {
            i3 = sizeOf(policyState.getPoliciesSetByAdmins().get(enforcingAdmin));
        }
        int sizeOf = sizeOf(policyValue);
        if (this.mPolicySizeLimit == -1 || (i2 + sizeOf) - i3 < this.mPolicySizeLimit) {
            increasePolicySizeForAdmin(enforcingAdmin, sizeOf - i3);
            return true;
        }
        android.util.Log.w(TAG, "Admin " + enforcingAdmin + "reached max allowed storage limit.");
        sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 3, i);
        return false;
    }

    private <V> void increasePolicySizeForAdmin(com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, int i) {
        if (!this.mAdminPolicySize.contains(enforcingAdmin.getUserId())) {
            this.mAdminPolicySize.put(enforcingAdmin.getUserId(), new java.util.HashMap<>());
        }
        if (!this.mAdminPolicySize.get(enforcingAdmin.getUserId()).containsKey(enforcingAdmin)) {
            this.mAdminPolicySize.get(enforcingAdmin.getUserId()).put(enforcingAdmin, 0);
        }
        this.mAdminPolicySize.get(enforcingAdmin.getUserId()).put(enforcingAdmin, java.lang.Integer.valueOf(this.mAdminPolicySize.get(enforcingAdmin.getUserId()).get(enforcingAdmin).intValue() + i));
    }

    private <V> void decreasePolicySizeForAdmin(com.android.server.devicepolicy.PolicyState<V> policyState, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        if (policyState.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
            this.mAdminPolicySize.get(enforcingAdmin.getUserId()).put(enforcingAdmin, java.lang.Integer.valueOf(this.mAdminPolicySize.get(enforcingAdmin.getUserId()).get(enforcingAdmin).intValue() - sizeOf(policyState.getPoliciesSetByAdmins().get(enforcingAdmin))));
        }
        if (this.mAdminPolicySize.get(enforcingAdmin.getUserId()).get(enforcingAdmin).intValue() <= 0) {
            this.mAdminPolicySize.get(enforcingAdmin.getUserId()).remove(enforcingAdmin);
        }
        if (this.mAdminPolicySize.get(enforcingAdmin.getUserId()).isEmpty()) {
            this.mAdminPolicySize.remove(enforcingAdmin.getUserId());
        }
    }

    void setMaxPolicyStorageLimit(int i) {
        if (i < -1 && i != -1) {
            throw new java.lang.IllegalArgumentException("Can't set a size limit less than the minimum allowed size.");
        }
        this.mPolicySizeLimit = i;
    }

    int getMaxPolicyStorageLimit() {
        return this.mPolicySizeLimit;
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Local Policies: ");
                for (int i = 0; i < this.mLocalPolicies.size(); i++) {
                    java.util.Iterator<android.app.admin.PolicyKey> it = this.mLocalPolicies.get(this.mLocalPolicies.keyAt(i)).keySet().iterator();
                    while (it.hasNext()) {
                        indentingPrintWriter.println(this.mLocalPolicies.get(this.mLocalPolicies.keyAt(i)).get(it.next()));
                    }
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Global Policies: ");
                java.util.Iterator<android.app.admin.PolicyKey> it2 = this.mGlobalPolicies.keySet().iterator();
                while (it2.hasNext()) {
                    indentingPrintWriter.println(this.mGlobalPolicies.get(it2.next()));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void write() {
        synchronized (this.mLock) {
            android.util.Log.d(TAG, "Writing device policies to file.");
            new com.android.server.devicepolicy.DevicePolicyEngine.DevicePoliciesReaderWriter().writeToFileLocked();
        }
    }

    void load() {
        android.util.Log.d(TAG, "Reading device policies from file.");
        synchronized (this.mLock) {
            clear();
            new com.android.server.devicepolicy.DevicePolicyEngine.DevicePoliciesReaderWriter().readFromFileLocked();
        }
    }

    <V> void reapplyAllPoliciesLocked() {
        java.util.Iterator<android.app.admin.PolicyKey> it = this.mGlobalPolicies.keySet().iterator();
        while (it.hasNext()) {
            com.android.server.devicepolicy.PolicyState<?> policyState = this.mGlobalPolicies.get(it.next());
            enforcePolicy(policyState.getPolicyDefinition(), policyState.getCurrentResolvedPolicy(), -1);
        }
        for (int i = 0; i < this.mLocalPolicies.size(); i++) {
            int keyAt = this.mLocalPolicies.keyAt(i);
            java.util.Iterator<android.app.admin.PolicyKey> it2 = this.mLocalPolicies.get(keyAt).keySet().iterator();
            while (it2.hasNext()) {
                com.android.server.devicepolicy.PolicyState<?> policyState2 = this.mLocalPolicies.get(keyAt).get(it2.next());
                enforcePolicy(policyState2.getPolicyDefinition(), policyState2.getCurrentResolvedPolicy(), keyAt);
            }
        }
    }

    void clearAllPolicies() {
        clear();
        write();
    }

    private void clear() {
        synchronized (this.mLock) {
            this.mGlobalPolicies.clear();
            this.mLocalPolicies.clear();
            this.mEnforcingAdmins.clear();
            this.mAdminPolicySize.clear();
        }
    }

    private <V> boolean checkFor2gFailure(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        boolean z;
        if (!policyDefinition.getPolicyKey().getIdentifier().equals(CELLULAR_2G_USER_RESTRICTION_ID)) {
            return false;
        }
        try {
            z = ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).isRadioInterfaceCapabilitySupported("CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK");
        } catch (java.lang.IllegalStateException e) {
            z = false;
        }
        if (z) {
            return false;
        }
        sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 4, -1);
        return true;
    }

    private class DevicePoliciesReaderWriter {
        private static final java.lang.String ATTR_POLICY_SUM_SIZE = "size";
        private static final java.lang.String ATTR_USER_ID = "user-id";
        private static final java.lang.String DEVICE_POLICIES_XML = "device_policy_state.xml";
        private static final java.lang.String TAG_ENFORCING_ADMIN = "enforcing-admin";
        private static final java.lang.String TAG_ENFORCING_ADMINS_ENTRY = "enforcing-admins-entry";
        private static final java.lang.String TAG_ENFORCING_ADMIN_AND_SIZE = "enforcing-admin-and-size";
        private static final java.lang.String TAG_GLOBAL_POLICY_ENTRY = "global-policy-entry";
        private static final java.lang.String TAG_LOCAL_POLICY_ENTRY = "local-policy-entry";
        private static final java.lang.String TAG_MAX_POLICY_SIZE_LIMIT = "max-policy-size-limit";
        private static final java.lang.String TAG_POLICY_KEY_ENTRY = "policy-key-entry";
        private static final java.lang.String TAG_POLICY_STATE_ENTRY = "policy-state-entry";
        private static final java.lang.String TAG_POLICY_SUM_SIZE = "policy-sum-size";
        private final java.io.File mFile;

        private DevicePoliciesReaderWriter() {
            this.mFile = new java.io.File(android.os.Environment.getDataSystemDirectory(), DEVICE_POLICIES_XML);
        }

        void writeToFileLocked() {
            java.io.FileOutputStream fileOutputStream;
            java.io.IOException e;
            android.util.Log.d(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Writing to " + this.mFile);
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mFile);
            try {
                fileOutputStream = atomicFile.startWrite();
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    writeInner(resolveSerializer);
                    resolveSerializer.endDocument();
                    resolveSerializer.flush();
                    atomicFile.finishWrite(fileOutputStream);
                } catch (java.io.IOException e2) {
                    e = e2;
                    android.util.Log.e(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Exception when writing", e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                }
            } catch (java.io.IOException e3) {
                fileOutputStream = null;
                e = e3;
            }
        }

        void writeInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            writeLocalPoliciesInner(typedXmlSerializer);
            writeGlobalPoliciesInner(typedXmlSerializer);
            writeEnforcingAdminsInner(typedXmlSerializer);
            writeEnforcingAdminSizeInner(typedXmlSerializer);
            writeMaxPolicySizeInner(typedXmlSerializer);
        }

        private void writeLocalPoliciesInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            if (com.android.server.devicepolicy.DevicePolicyEngine.this.mLocalPolicies != null) {
                for (int i = 0; i < com.android.server.devicepolicy.DevicePolicyEngine.this.mLocalPolicies.size(); i++) {
                    int keyAt = com.android.server.devicepolicy.DevicePolicyEngine.this.mLocalPolicies.keyAt(i);
                    for (java.util.Map.Entry entry : ((java.util.Map) com.android.server.devicepolicy.DevicePolicyEngine.this.mLocalPolicies.get(keyAt)).entrySet()) {
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_LOCAL_POLICY_ENTRY);
                        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_USER_ID, keyAt);
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_POLICY_KEY_ENTRY);
                        ((android.app.admin.PolicyKey) entry.getKey()).saveToXml(typedXmlSerializer);
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_POLICY_KEY_ENTRY);
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_POLICY_STATE_ENTRY);
                        ((com.android.server.devicepolicy.PolicyState) entry.getValue()).saveToXml(typedXmlSerializer);
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_POLICY_STATE_ENTRY);
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_LOCAL_POLICY_ENTRY);
                    }
                }
            }
        }

        private void writeGlobalPoliciesInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            if (com.android.server.devicepolicy.DevicePolicyEngine.this.mGlobalPolicies != null) {
                for (java.util.Map.Entry entry : com.android.server.devicepolicy.DevicePolicyEngine.this.mGlobalPolicies.entrySet()) {
                    typedXmlSerializer.startTag((java.lang.String) null, TAG_GLOBAL_POLICY_ENTRY);
                    typedXmlSerializer.startTag((java.lang.String) null, TAG_POLICY_KEY_ENTRY);
                    ((android.app.admin.PolicyKey) entry.getKey()).saveToXml(typedXmlSerializer);
                    typedXmlSerializer.endTag((java.lang.String) null, TAG_POLICY_KEY_ENTRY);
                    typedXmlSerializer.startTag((java.lang.String) null, TAG_POLICY_STATE_ENTRY);
                    ((com.android.server.devicepolicy.PolicyState) entry.getValue()).saveToXml(typedXmlSerializer);
                    typedXmlSerializer.endTag((java.lang.String) null, TAG_POLICY_STATE_ENTRY);
                    typedXmlSerializer.endTag((java.lang.String) null, TAG_GLOBAL_POLICY_ENTRY);
                }
            }
        }

        private void writeEnforcingAdminsInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            if (com.android.server.devicepolicy.DevicePolicyEngine.this.mEnforcingAdmins != null) {
                for (int i = 0; i < com.android.server.devicepolicy.DevicePolicyEngine.this.mEnforcingAdmins.size(); i++) {
                    for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin : (java.util.Set) com.android.server.devicepolicy.DevicePolicyEngine.this.mEnforcingAdmins.get(com.android.server.devicepolicy.DevicePolicyEngine.this.mEnforcingAdmins.keyAt(i))) {
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_ENFORCING_ADMINS_ENTRY);
                        enforcingAdmin.saveToXml(typedXmlSerializer);
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_ENFORCING_ADMINS_ENTRY);
                    }
                }
            }
        }

        private void writeEnforcingAdminSizeInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled();
        }

        private void writeMaxPolicySizeInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled();
        }

        void readFromFileLocked() {
            if (!this.mFile.exists()) {
                android.util.Log.d(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "" + this.mFile + " doesn't exist");
                return;
            }
            android.util.Log.d(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Reading from " + this.mFile);
            java.io.FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = new android.util.AtomicFile(this.mFile).openRead();
                    readInner(android.util.Xml.resolvePullParser(fileInputStream));
                } catch (java.io.IOException | java.lang.ClassNotFoundException | org.xmlpull.v1.XmlPullParserException e) {
                    com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Error parsing resources file", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void readInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, java.lang.ClassNotFoundException {
            char c;
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -1900677631:
                        if (name.equals(TAG_GLOBAL_POLICY_ENTRY)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1329955015:
                        if (name.equals(TAG_LOCAL_POLICY_ENTRY)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -949666205:
                        if (name.equals(TAG_ENFORCING_ADMIN_AND_SIZE)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 134595137:
                        if (name.equals(TAG_MAX_POLICY_SIZE_LIMIT)) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1016501079:
                        if (name.equals(TAG_ENFORCING_ADMINS_ENTRY)) {
                            c = 2;
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
                        readLocalPoliciesInner(typedXmlPullParser);
                        break;
                    case 1:
                        readGlobalPoliciesInner(typedXmlPullParser);
                        break;
                    case 2:
                        readEnforcingAdminsInner(typedXmlPullParser);
                        break;
                    case 3:
                        readEnforcingAdminAndSizeInner(typedXmlPullParser);
                        break;
                    case 4:
                        readMaxPolicySizeInner(typedXmlPullParser);
                        break;
                    default:
                        com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Unknown tag " + name);
                        break;
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void readLocalPoliciesInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            char c;
            android.app.admin.PolicyKey policyKey = null;
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_USER_ID);
            int depth = typedXmlPullParser.getDepth();
            java.lang.Object obj = null;
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case 1439131817:
                        if (name.equals(TAG_POLICY_KEY_ENTRY)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1917578267:
                        if (name.equals(TAG_POLICY_STATE_ENTRY)) {
                            c = 1;
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
                        policyKey = com.android.server.devicepolicy.PolicyDefinition.readPolicyKeyFromXml(typedXmlPullParser);
                        break;
                    case 1:
                        obj = com.android.server.devicepolicy.PolicyState.readFromXml(typedXmlPullParser);
                        break;
                    default:
                        com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Unknown tag for local policy entry" + name);
                        break;
                }
            }
            if (policyKey != null && obj != null) {
                if (!com.android.server.devicepolicy.DevicePolicyEngine.this.mLocalPolicies.contains(attributeInt)) {
                    com.android.server.devicepolicy.DevicePolicyEngine.this.mLocalPolicies.put(attributeInt, new java.util.HashMap());
                }
                ((java.util.Map) com.android.server.devicepolicy.DevicePolicyEngine.this.mLocalPolicies.get(attributeInt)).put(policyKey, obj);
                return;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Error parsing local policy, policyKey is ");
            if (policyKey == null) {
                policyKey = "null";
            }
            sb.append(policyKey);
            sb.append(", and policyState is ");
            if (obj == null) {
                obj = "null";
            }
            sb.append(obj);
            sb.append(".");
            com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, sb.toString());
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void readGlobalPoliciesInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            char c;
            int depth = typedXmlPullParser.getDepth();
            android.app.admin.PolicyKey policyKey = null;
            java.lang.Object obj = null;
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case 1439131817:
                        if (name.equals(TAG_POLICY_KEY_ENTRY)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1917578267:
                        if (name.equals(TAG_POLICY_STATE_ENTRY)) {
                            c = 1;
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
                        policyKey = com.android.server.devicepolicy.PolicyDefinition.readPolicyKeyFromXml(typedXmlPullParser);
                        break;
                    case 1:
                        obj = com.android.server.devicepolicy.PolicyState.readFromXml(typedXmlPullParser);
                        break;
                    default:
                        com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Unknown tag for local policy entry" + name);
                        break;
                }
            }
            if (policyKey != null && obj != null) {
                com.android.server.devicepolicy.DevicePolicyEngine.this.mGlobalPolicies.put(policyKey, obj);
                return;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Error parsing global policy, policyKey is ");
            if (policyKey == null) {
                policyKey = "null";
            }
            sb.append(policyKey);
            sb.append(", and policyState is ");
            if (obj == null) {
                obj = "null";
            }
            sb.append(obj);
            sb.append(".");
            com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, sb.toString());
        }

        private void readEnforcingAdminsInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
            com.android.server.devicepolicy.EnforcingAdmin readFromXml = com.android.server.devicepolicy.EnforcingAdmin.readFromXml(typedXmlPullParser);
            if (readFromXml == null) {
                com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Error parsing enforcingAdmins, EnforcingAdmin is null.");
                return;
            }
            if (!com.android.server.devicepolicy.DevicePolicyEngine.this.mEnforcingAdmins.contains(readFromXml.getUserId())) {
                com.android.server.devicepolicy.DevicePolicyEngine.this.mEnforcingAdmins.put(readFromXml.getUserId(), new java.util.HashSet());
            }
            ((java.util.Set) com.android.server.devicepolicy.DevicePolicyEngine.this.mEnforcingAdmins.get(readFromXml.getUserId())).add(readFromXml);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void readEnforcingAdminAndSizeInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            char c;
            int depth = typedXmlPullParser.getDepth();
            com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin = null;
            int i = 0;
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -1290014687:
                        if (name.equals(TAG_ENFORCING_ADMIN)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1249111938:
                        if (name.equals(TAG_POLICY_SUM_SIZE)) {
                            c = 1;
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
                        enforcingAdmin = com.android.server.devicepolicy.EnforcingAdmin.readFromXml(typedXmlPullParser);
                        break;
                    case 1:
                        i = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_POLICY_SUM_SIZE);
                        break;
                    default:
                        com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Unknown tag " + name);
                        break;
                }
            }
            if (enforcingAdmin == null) {
                com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Error parsing enforcingAdmins, EnforcingAdmin is null.");
                return;
            }
            if (i <= 0) {
                com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.DevicePolicyEngine.TAG, "Error parsing policy size, size is " + i);
                return;
            }
            if (!com.android.server.devicepolicy.DevicePolicyEngine.this.mAdminPolicySize.contains(enforcingAdmin.getUserId())) {
                com.android.server.devicepolicy.DevicePolicyEngine.this.mAdminPolicySize.put(enforcingAdmin.getUserId(), new java.util.HashMap());
            }
            ((java.util.HashMap) com.android.server.devicepolicy.DevicePolicyEngine.this.mAdminPolicySize.get(enforcingAdmin.getUserId())).put(enforcingAdmin, java.lang.Integer.valueOf(i));
        }

        private void readMaxPolicySizeInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled();
        }
    }
}
