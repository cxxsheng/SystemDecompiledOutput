package com.android.server.credentials;

/* loaded from: classes.dex */
public class CredentialDescriptionRegistry {
    private static final int MAX_ALLOWED_CREDENTIAL_DESCRIPTIONS = 128;
    private static final int MAX_ALLOWED_ENTRIES_PER_PROVIDER = 16;

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static final android.util.SparseArray<com.android.server.credentials.CredentialDescriptionRegistry> sCredentialDescriptionSessionPerUser = new android.util.SparseArray<>();
    private static final java.util.concurrent.locks.ReentrantLock sLock = new java.util.concurrent.locks.ReentrantLock();
    private java.util.Map<java.lang.String, java.util.Set<android.credentials.CredentialDescription>> mCredentialDescriptions = new java.util.HashMap();
    private int mTotalDescriptionCount = 0;

    public static final class FilterResult {
        final java.util.List<android.service.credentials.CredentialEntry> mCredentialEntries;
        final java.util.Set<java.lang.String> mElementKeys;
        final java.lang.String mPackageName;

        @com.android.internal.annotations.VisibleForTesting
        FilterResult(java.lang.String str, java.util.Set<java.lang.String> set, java.util.List<android.service.credentials.CredentialEntry> list) {
            this.mPackageName = str;
            this.mElementKeys = set;
            this.mCredentialEntries = list;
        }
    }

    @com.android.internal.annotations.GuardedBy({"sLock"})
    public static com.android.server.credentials.CredentialDescriptionRegistry forUser(int i) {
        sLock.lock();
        try {
            com.android.server.credentials.CredentialDescriptionRegistry credentialDescriptionRegistry = sCredentialDescriptionSessionPerUser.get(i, null);
            if (credentialDescriptionRegistry == null) {
                credentialDescriptionRegistry = new com.android.server.credentials.CredentialDescriptionRegistry();
                sCredentialDescriptionSessionPerUser.put(i, credentialDescriptionRegistry);
            }
            return credentialDescriptionRegistry;
        } finally {
            sLock.unlock();
        }
    }

    @com.android.internal.annotations.GuardedBy({"sLock"})
    public static void clearUserSession(int i) {
        sLock.lock();
        try {
            sCredentialDescriptionSessionPerUser.remove(i);
        } finally {
            sLock.unlock();
        }
    }

    @com.android.internal.annotations.GuardedBy({"sLock"})
    @com.android.internal.annotations.VisibleForTesting
    static void clearAllSessions() {
        sLock.lock();
        try {
            sCredentialDescriptionSessionPerUser.clear();
        } finally {
            sLock.unlock();
        }
    }

    @com.android.internal.annotations.GuardedBy({"sLock"})
    @com.android.internal.annotations.VisibleForTesting
    static void setSession(int i, com.android.server.credentials.CredentialDescriptionRegistry credentialDescriptionRegistry) {
        sLock.lock();
        try {
            sCredentialDescriptionSessionPerUser.put(i, credentialDescriptionRegistry);
        } finally {
            sLock.unlock();
        }
    }

    private CredentialDescriptionRegistry() {
    }

    public void executeRegisterRequest(android.credentials.RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, java.lang.String str) {
        if (!this.mCredentialDescriptions.containsKey(str)) {
            this.mCredentialDescriptions.put(str, new java.util.HashSet());
        }
        if (this.mTotalDescriptionCount <= 128 && this.mCredentialDescriptions.get(str).size() <= 16) {
            java.util.Set<android.credentials.CredentialDescription> credentialDescriptions = registerCredentialDescriptionRequest.getCredentialDescriptions();
            int size = this.mCredentialDescriptions.get(str).size();
            this.mCredentialDescriptions.get(str).addAll(credentialDescriptions);
            this.mTotalDescriptionCount += this.mCredentialDescriptions.get(str).size() - size;
        }
    }

    public void executeUnregisterRequest(android.credentials.UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, java.lang.String str) {
        if (this.mCredentialDescriptions.containsKey(str)) {
            int size = this.mCredentialDescriptions.get(str).size();
            this.mCredentialDescriptions.get(str).removeAll(unregisterCredentialDescriptionRequest.getCredentialDescriptions());
            this.mTotalDescriptionCount -= size - this.mCredentialDescriptions.get(str).size();
        }
    }

    public java.util.Set<com.android.server.credentials.CredentialDescriptionRegistry.FilterResult> getFilteredResultForProvider(java.lang.String str, java.util.Set<java.lang.String> set) {
        java.util.HashSet hashSet = new java.util.HashSet();
        if (!this.mCredentialDescriptions.containsKey(str)) {
            return hashSet;
        }
        for (android.credentials.CredentialDescription credentialDescription : this.mCredentialDescriptions.get(str)) {
            if (checkForMatch(credentialDescription.getSupportedElementKeys(), set)) {
                hashSet.add(new com.android.server.credentials.CredentialDescriptionRegistry.FilterResult(str, credentialDescription.getSupportedElementKeys(), credentialDescription.getCredentialEntries()));
            }
        }
        return hashSet;
    }

    public java.util.Set<com.android.server.credentials.CredentialDescriptionRegistry.FilterResult> getMatchingProviders(java.util.Set<java.util.Set<java.lang.String>> set) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.lang.String str : this.mCredentialDescriptions.keySet()) {
            for (android.credentials.CredentialDescription credentialDescription : this.mCredentialDescriptions.get(str)) {
                if (canProviderSatisfyAny(credentialDescription.getSupportedElementKeys(), set)) {
                    hashSet.add(new com.android.server.credentials.CredentialDescriptionRegistry.FilterResult(str, credentialDescription.getSupportedElementKeys(), credentialDescription.getCredentialEntries()));
                }
            }
        }
        return hashSet;
    }

    void evictProviderWithPackageName(java.lang.String str) {
        if (this.mCredentialDescriptions.containsKey(str)) {
            this.mCredentialDescriptions.remove(str);
        }
    }

    private static boolean canProviderSatisfyAny(java.util.Set<java.lang.String> set, java.util.Set<java.util.Set<java.lang.String>> set2) {
        java.util.Iterator<java.util.Set<java.lang.String>> it = set2.iterator();
        while (it.hasNext()) {
            if (set.containsAll(it.next())) {
                return true;
            }
        }
        return false;
    }

    static boolean checkForMatch(java.util.Set<java.lang.String> set, java.util.Set<java.lang.String> set2) {
        return set.containsAll(set2);
    }
}
