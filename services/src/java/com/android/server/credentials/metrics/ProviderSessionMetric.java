package com.android.server.credentials.metrics;

/* loaded from: classes.dex */
public class ProviderSessionMetric {
    private static final java.lang.String TAG = "ProviderSessionMetric";

    @android.annotation.NonNull
    protected final java.util.List<com.android.server.credentials.metrics.BrowsedAuthenticationMetric> mBrowsedAuthenticationMetric = new java.util.ArrayList();

    @android.annotation.NonNull
    protected final com.android.server.credentials.metrics.CandidatePhaseMetric mCandidatePhasePerProviderMetric;

    public ProviderSessionMetric(int i) {
        this.mCandidatePhasePerProviderMetric = new com.android.server.credentials.metrics.CandidatePhaseMetric(i);
        this.mBrowsedAuthenticationMetric.add(new com.android.server.credentials.metrics.BrowsedAuthenticationMetric(i));
    }

    public com.android.server.credentials.metrics.CandidatePhaseMetric getCandidatePhasePerProviderMetric() {
        return this.mCandidatePhasePerProviderMetric;
    }

    public java.util.List<com.android.server.credentials.metrics.BrowsedAuthenticationMetric> getBrowsedAuthenticationMetric() {
        return this.mBrowsedAuthenticationMetric;
    }

    public void collectCandidateExceptionStatus(boolean z) {
        try {
            this.mCandidatePhasePerProviderMetric.setHasException(z);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Error while setting candidate metric exception " + e);
        }
    }

    public void collectAuthenticationExceptionStatus(boolean z) {
        try {
            getUsedAuthenticationMetric().setHasException(z);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Error while setting authentication metric exception " + e);
        }
    }

    public void collectCandidateFrameworkException(java.lang.String str) {
        try {
            this.mCandidatePhasePerProviderMetric.setFrameworkException(str);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during candidate exception metric logging: " + e);
        }
    }

    private void collectAuthEntryUpdate(boolean z, boolean z2, int i) {
        com.android.server.credentials.metrics.BrowsedAuthenticationMetric usedAuthenticationMetric = getUsedAuthenticationMetric();
        usedAuthenticationMetric.setProviderUid(i);
        if (z) {
            usedAuthenticationMetric.setAuthReturned(false);
            usedAuthenticationMetric.setProviderStatus(com.android.server.credentials.metrics.ProviderStatusForMetrics.QUERY_FAILURE.getMetricCode());
        } else if (z2) {
            usedAuthenticationMetric.setAuthReturned(true);
            usedAuthenticationMetric.setProviderStatus(com.android.server.credentials.metrics.ProviderStatusForMetrics.QUERY_SUCCESS.getMetricCode());
        }
    }

    private com.android.server.credentials.metrics.BrowsedAuthenticationMetric getUsedAuthenticationMetric() {
        return this.mBrowsedAuthenticationMetric.get(this.mBrowsedAuthenticationMetric.size() - 1);
    }

    public void collectCandidateMetricUpdate(boolean z, boolean z2, int i, boolean z3, boolean z4) {
        try {
            if (z3) {
                collectAuthEntryUpdate(z, z2, i);
                return;
            }
            this.mCandidatePhasePerProviderMetric.setPrimary(z4);
            this.mCandidatePhasePerProviderMetric.setCandidateUid(i);
            this.mCandidatePhasePerProviderMetric.setQueryFinishTimeNanoseconds(java.lang.System.nanoTime());
            if (z) {
                this.mCandidatePhasePerProviderMetric.setQueryReturned(false);
                this.mCandidatePhasePerProviderMetric.setProviderQueryStatus(com.android.server.credentials.metrics.ProviderStatusForMetrics.QUERY_FAILURE.getMetricCode());
            } else if (z2) {
                this.mCandidatePhasePerProviderMetric.setQueryReturned(true);
                this.mCandidatePhasePerProviderMetric.setProviderQueryStatus(com.android.server.credentials.metrics.ProviderStatusForMetrics.QUERY_SUCCESS.getMetricCode());
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during candidate update metric logging: " + e);
        }
    }

    public void collectCandidateMetricSetupViaInitialMetric(com.android.server.credentials.metrics.InitialPhaseMetric initialPhaseMetric) {
        try {
            this.mCandidatePhasePerProviderMetric.setServiceBeganTimeNanoseconds(initialPhaseMetric.getCredentialServiceStartedTimeNanoseconds());
            this.mCandidatePhasePerProviderMetric.setStartQueryTimeNanoseconds(java.lang.System.nanoTime());
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during candidate setup metric logging: " + e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <R> void collectCandidateEntryMetrics(R r, boolean z, @android.annotation.Nullable com.android.server.credentials.metrics.InitialPhaseMetric initialPhaseMetric) {
        try {
            if (r instanceof android.service.credentials.BeginGetCredentialResponse) {
                beginGetCredentialResponseCollectionCandidateEntryMetrics((android.service.credentials.BeginGetCredentialResponse) r, z);
            } else if (!(r instanceof android.service.credentials.BeginCreateCredentialResponse)) {
                android.util.Slog.i(TAG, "Your response type is unsupported for candidate metric logging");
            } else {
                beginCreateCredentialResponseCollectionCandidateEntryMetrics((android.service.credentials.BeginCreateCredentialResponse) r, initialPhaseMetric);
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during candidate entry metric logging: " + e);
        }
    }

    public void collectCandidateEntryMetrics(java.util.List<android.service.credentials.CredentialEntry> list) {
        int size = list.size();
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        final java.util.LinkedHashMap linkedHashMap2 = new java.util.LinkedHashMap();
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.REMOTE_ENTRY, 0);
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.CREDENTIAL_ENTRY, java.lang.Integer.valueOf(size));
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.ACTION_ENTRY, 0);
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.AUTHENTICATION_ENTRY, 0);
        list.forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.metrics.ProviderSessionMetric$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.credentials.metrics.ProviderSessionMetric.lambda$collectCandidateEntryMetrics$0(linkedHashMap2, (android.service.credentials.CredentialEntry) obj);
            }
        });
        this.mCandidatePhasePerProviderMetric.setResponseCollective(new com.android.server.credentials.metrics.shared.ResponseCollective(linkedHashMap2, linkedHashMap));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$collectCandidateEntryMetrics$0(java.util.Map map, android.service.credentials.CredentialEntry credentialEntry) {
        java.lang.String generateMetricKey = com.android.server.credentials.MetricUtilities.generateMetricKey(credentialEntry.getType(), 20);
        map.put(generateMetricKey, java.lang.Integer.valueOf(((java.lang.Integer) map.getOrDefault(generateMetricKey, 0)).intValue() + 1));
    }

    public void createAuthenticationBrowsingMetric() {
        this.mBrowsedAuthenticationMetric.add(new com.android.server.credentials.metrics.BrowsedAuthenticationMetric(this.mCandidatePhasePerProviderMetric.getSessionIdProvider()));
    }

    private void beginCreateCredentialResponseCollectionCandidateEntryMetrics(android.service.credentials.BeginCreateCredentialResponse beginCreateCredentialResponse, com.android.server.credentials.metrics.InitialPhaseMetric initialPhaseMetric) {
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        java.util.List<android.service.credentials.CreateEntry> createEntries = beginCreateCredentialResponse.getCreateEntries();
        int i = beginCreateCredentialResponse.getRemoteCreateEntry() == null ? 0 : 1;
        int size = createEntries.size();
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.REMOTE_ENTRY, java.lang.Integer.valueOf(i));
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.CREDENTIAL_ENTRY, java.lang.Integer.valueOf(size));
        java.util.LinkedHashMap linkedHashMap2 = new java.util.LinkedHashMap();
        java.lang.String[] uniqueRequestStrings = initialPhaseMetric == null ? new java.lang.String[0] : initialPhaseMetric.getUniqueRequestStrings();
        if (uniqueRequestStrings.length > 0) {
            linkedHashMap2.put(uniqueRequestStrings[0], java.lang.Integer.valueOf(initialPhaseMetric.getUniqueRequestCounts()[0]));
        }
        this.mCandidatePhasePerProviderMetric.setResponseCollective(new com.android.server.credentials.metrics.shared.ResponseCollective(linkedHashMap2, linkedHashMap));
    }

    private void beginGetCredentialResponseCollectionCandidateEntryMetrics(android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse, boolean z) {
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        final java.util.LinkedHashMap linkedHashMap2 = new java.util.LinkedHashMap();
        int size = beginGetCredentialResponse.getCredentialEntries().size();
        int size2 = beginGetCredentialResponse.getActions().size();
        int size3 = beginGetCredentialResponse.getAuthenticationActions().size();
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.REMOTE_ENTRY, java.lang.Integer.valueOf(beginGetCredentialResponse.getRemoteCredentialEntry() != null ? 0 : 1));
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.CREDENTIAL_ENTRY, java.lang.Integer.valueOf(size));
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.ACTION_ENTRY, java.lang.Integer.valueOf(size2));
        linkedHashMap.put(com.android.server.credentials.metrics.EntryEnum.AUTHENTICATION_ENTRY, java.lang.Integer.valueOf(size3));
        beginGetCredentialResponse.getCredentialEntries().forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.metrics.ProviderSessionMetric$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.credentials.metrics.ProviderSessionMetric.lambda$beginGetCredentialResponseCollectionCandidateEntryMetrics$1(linkedHashMap2, (android.service.credentials.CredentialEntry) obj);
            }
        });
        com.android.server.credentials.metrics.shared.ResponseCollective responseCollective = new com.android.server.credentials.metrics.shared.ResponseCollective(linkedHashMap2, linkedHashMap);
        if (!z) {
            this.mCandidatePhasePerProviderMetric.setResponseCollective(responseCollective);
        } else {
            this.mBrowsedAuthenticationMetric.get(this.mBrowsedAuthenticationMetric.size() - 1).setAuthEntryCollective(responseCollective);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$beginGetCredentialResponseCollectionCandidateEntryMetrics$1(java.util.Map map, android.service.credentials.CredentialEntry credentialEntry) {
        java.lang.String generateMetricKey = com.android.server.credentials.MetricUtilities.generateMetricKey(credentialEntry.getType(), 20);
        map.put(generateMetricKey, java.lang.Integer.valueOf(((java.lang.Integer) map.getOrDefault(generateMetricKey, 0)).intValue() + 1));
    }
}
