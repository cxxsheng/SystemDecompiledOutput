package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
class DomainVerificationSettings {

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationCollector mCollector;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> mPendingPkgStates = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> mRestoredPkgStates = new android.util.ArrayMap<>();
    private final java.lang.Object mLock = new java.lang.Object();

    public DomainVerificationSettings(@android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationCollector domainVerificationCollector) {
        this.mCollector = domainVerificationCollector;
    }

    public void writeSettings(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationStateMap<com.android.server.pm.verify.domain.models.DomainVerificationPkgState> domainVerificationStateMap, @android.annotation.NonNull java.util.function.Function<java.lang.String, java.lang.String> function) {
    }

    public void writeSettings(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationStateMap<com.android.server.pm.verify.domain.models.DomainVerificationPkgState> domainVerificationStateMap, int i, @android.annotation.NonNull java.util.function.Function<java.lang.String, java.lang.String> function) throws java.io.IOException {
        synchronized (this.mLock) {
            com.android.server.pm.verify.domain.DomainVerificationPersistence.writeToXml(typedXmlSerializer, domainVerificationStateMap, this.mPendingPkgStates, this.mRestoredPkgStates, i, function);
        }
    }

    public void readSettings(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationStateMap<com.android.server.pm.verify.domain.models.DomainVerificationPkgState> domainVerificationStateMap, @android.annotation.NonNull com.android.server.pm.Computer computer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.pm.verify.domain.DomainVerificationPersistence.ReadResult readFromXml = com.android.server.pm.verify.domain.DomainVerificationPersistence.readFromXml(typedXmlPullParser);
        android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> arrayMap = readFromXml.active;
        android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> arrayMap2 = readFromXml.restored;
        synchronized (this.mLock) {
            try {
                int size = arrayMap.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.pm.verify.domain.models.DomainVerificationPkgState valueAt = arrayMap.valueAt(i);
                    java.lang.String packageName = valueAt.getPackageName();
                    com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = domainVerificationStateMap.get(packageName);
                    if (domainVerificationPkgState != null) {
                        if (!domainVerificationPkgState.getId().equals(valueAt.getId())) {
                            mergePkgState(domainVerificationPkgState, valueAt, computer);
                        }
                    } else {
                        this.mPendingPkgStates.put(packageName, valueAt);
                    }
                }
                int size2 = arrayMap2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    com.android.server.pm.verify.domain.models.DomainVerificationPkgState valueAt2 = arrayMap2.valueAt(i2);
                    this.mRestoredPkgStates.put(valueAt2.getPackageName(), valueAt2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void restoreSettings(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationStateMap<com.android.server.pm.verify.domain.models.DomainVerificationPkgState> domainVerificationStateMap, @android.annotation.NonNull com.android.server.pm.Computer computer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.pm.verify.domain.DomainVerificationPersistence.ReadResult readFromXml = com.android.server.pm.verify.domain.DomainVerificationPersistence.readFromXml(typedXmlPullParser);
        android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> arrayMap = readFromXml.restored;
        arrayMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends com.android.server.pm.verify.domain.models.DomainVerificationPkgState>) readFromXml.active);
        synchronized (this.mLock) {
            for (int i = 0; i < arrayMap.size(); i++) {
                try {
                    com.android.server.pm.verify.domain.models.DomainVerificationPkgState valueAt = arrayMap.valueAt(i);
                    java.lang.String packageName = valueAt.getPackageName();
                    com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = domainVerificationStateMap.get(packageName);
                    if (domainVerificationPkgState == null) {
                        domainVerificationPkgState = this.mPendingPkgStates.get(packageName);
                    }
                    if (domainVerificationPkgState == null) {
                        domainVerificationPkgState = this.mRestoredPkgStates.get(packageName);
                    }
                    if (domainVerificationPkgState != null) {
                        mergePkgState(domainVerificationPkgState, valueAt, computer);
                    } else {
                        android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = valueAt.getStateMap();
                        for (int size = stateMap.size() - 1; size >= 0; size--) {
                            java.lang.Integer valueAt2 = stateMap.valueAt(size);
                            if (valueAt2 != null) {
                                int intValue = valueAt2.intValue();
                                if (intValue == 1 || intValue == 5) {
                                    stateMap.setValueAt(size, 5);
                                } else {
                                    stateMap.removeAt(size);
                                }
                            }
                        }
                        this.mRestoredPkgStates.put(packageName, valueAt);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public void mergePkgState(@android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState2, @android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.lang.Integer num;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(domainVerificationPkgState.getPackageName());
        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
        java.util.Set emptySet = pkg == null ? java.util.Collections.emptySet() : this.mCollector.collectValidAutoVerifyDomains(pkg);
        android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap = domainVerificationPkgState.getStateMap();
        android.util.ArrayMap<java.lang.String, java.lang.Integer> stateMap2 = domainVerificationPkgState2.getStateMap();
        int size = stateMap2.size();
        for (int i = 0; i < size; i++) {
            java.lang.String keyAt = stateMap2.keyAt(i);
            java.lang.Integer valueAt = stateMap2.valueAt(i);
            if (emptySet.contains(keyAt) && (((num = stateMap.get(keyAt)) == null || num.intValue() == 0) && (valueAt.intValue() == 1 || valueAt.intValue() == 5))) {
                stateMap.put(keyAt, 5);
            }
        }
        android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> userStates = domainVerificationPkgState.getUserStates();
        android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> userStates2 = domainVerificationPkgState2.getUserStates();
        int size2 = userStates2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            int keyAt2 = userStates2.keyAt(i2);
            com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState valueAt2 = userStates2.valueAt(i2);
            if (valueAt2 != null) {
                android.util.ArraySet<java.lang.String> enabledHosts = valueAt2.getEnabledHosts();
                com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState domainVerificationInternalUserState = userStates.get(keyAt2);
                boolean isLinkHandlingAllowed = valueAt2.isLinkHandlingAllowed();
                if (domainVerificationInternalUserState == null) {
                    userStates.put(keyAt2, new com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState(keyAt2, enabledHosts, isLinkHandlingAllowed));
                } else {
                    domainVerificationInternalUserState.addHosts(enabledHosts).setLinkHandlingAllowed(isLinkHandlingAllowed);
                }
            }
        }
    }

    public void removePackage(@android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            this.mPendingPkgStates.remove(str);
            this.mRestoredPkgStates.remove(str);
        }
    }

    public void removePackageForUser(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState = this.mPendingPkgStates.get(str);
                if (domainVerificationPkgState != null) {
                    domainVerificationPkgState.removeUser(i);
                }
                com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState2 = this.mRestoredPkgStates.get(str);
                if (domainVerificationPkgState2 != null) {
                    domainVerificationPkgState2.removeUser(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeUser(int i) {
        synchronized (this.mLock) {
            try {
                int size = this.mPendingPkgStates.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mPendingPkgStates.valueAt(i2).removeUser(i);
                }
                int size2 = this.mRestoredPkgStates.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    this.mRestoredPkgStates.valueAt(i3).removeUser(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public com.android.server.pm.verify.domain.models.DomainVerificationPkgState removePendingState(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.verify.domain.models.DomainVerificationPkgState remove;
        synchronized (this.mLock) {
            remove = this.mPendingPkgStates.remove(str);
        }
        return remove;
    }

    @android.annotation.Nullable
    public com.android.server.pm.verify.domain.models.DomainVerificationPkgState removeRestoredState(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.verify.domain.models.DomainVerificationPkgState remove;
        synchronized (this.mLock) {
            remove = this.mRestoredPkgStates.remove(str);
        }
        return remove;
    }
}
