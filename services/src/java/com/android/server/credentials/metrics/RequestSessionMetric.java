package com.android.server.credentials.metrics;

/* loaded from: classes.dex */
public class RequestSessionMetric {
    private static final java.lang.String TAG = "RequestSessionMetric";

    @android.annotation.NonNull
    protected final com.android.server.credentials.metrics.CandidateAggregateMetric mCandidateAggregateMetric;
    protected final com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric mChosenProviderFinalPhaseMetric;
    protected final com.android.server.credentials.metrics.InitialPhaseMetric mInitialPhaseMetric;
    private final int mSessionIdTrackTwo;
    protected int mSequenceCounter = 0;
    protected java.util.List<com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric> mCandidateBrowsingPhaseMetric = new java.util.ArrayList();

    public RequestSessionMetric(int i, int i2) {
        this.mSessionIdTrackTwo = i2;
        this.mInitialPhaseMetric = new com.android.server.credentials.metrics.InitialPhaseMetric(i);
        this.mCandidateAggregateMetric = new com.android.server.credentials.metrics.CandidateAggregateMetric(i);
        this.mChosenProviderFinalPhaseMetric = new com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric(i, i2);
    }

    public int returnIncrementSequence() {
        int i = this.mSequenceCounter + 1;
        this.mSequenceCounter = i;
        return i;
    }

    public com.android.server.credentials.metrics.InitialPhaseMetric getInitialPhaseMetric() {
        return this.mInitialPhaseMetric;
    }

    public com.android.server.credentials.metrics.CandidateAggregateMetric getCandidateAggregateMetric() {
        return this.mCandidateAggregateMetric;
    }

    public void collectInitialPhaseMetricInfo(long j, int i, int i2) {
        try {
            this.mInitialPhaseMetric.setCredentialServiceStartedTimeNanoseconds(j);
            this.mInitialPhaseMetric.setCallerUid(i);
            this.mInitialPhaseMetric.setApiName(i2);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error collecting initial phase metric start info: " + e);
        }
    }

    public void collectUiReturnedFinalPhase(boolean z) {
        try {
            this.mChosenProviderFinalPhaseMetric.setUiReturned(z);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error collecting ui end time metric: " + e);
        }
    }

    public void collectUiCallStartTime(long j) {
        try {
            this.mChosenProviderFinalPhaseMetric.setUiCallStartTimeNanoseconds(j);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error collecting ui start metric: " + e);
        }
    }

    public void collectUiResponseData(boolean z, long j) {
        try {
            this.mChosenProviderFinalPhaseMetric.setUiReturned(z);
            this.mChosenProviderFinalPhaseMetric.setUiCallEndTimeNanoseconds(j);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error collecting ui response metric: " + e);
        }
    }

    public void collectChosenProviderStatus(int i) {
        try {
            this.mChosenProviderFinalPhaseMetric.setChosenProviderStatus(i);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error setting chosen provider status metric: " + e);
        }
    }

    public void collectCreateFlowInitialMetricInfo(boolean z, android.credentials.CreateCredentialRequest createCredentialRequest) {
        try {
            this.mInitialPhaseMetric.setOriginSpecified(z);
            this.mInitialPhaseMetric.setRequestCounts(java.util.Map.of(com.android.server.credentials.MetricUtilities.generateMetricKey(createCredentialRequest.getType(), 20), 1));
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error collecting create flow metric: " + e);
        }
    }

    private java.util.Map<java.lang.String, java.lang.Integer> getRequestCountMap(android.credentials.GetCredentialRequest getCredentialRequest) {
        final java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        try {
            getCredentialRequest.getCredentialOptions().forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.metrics.RequestSessionMetric$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.credentials.metrics.RequestSessionMetric.lambda$getRequestCountMap$0(linkedHashMap, (android.credentials.CredentialOption) obj);
                }
            });
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during get request count map metric logging: " + e);
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getRequestCountMap$0(java.util.Map map, android.credentials.CredentialOption credentialOption) {
        java.lang.String generateMetricKey = com.android.server.credentials.MetricUtilities.generateMetricKey(credentialOption.getType(), 20);
        map.put(generateMetricKey, java.lang.Integer.valueOf(((java.lang.Integer) map.getOrDefault(generateMetricKey, 0)).intValue() + 1));
    }

    public void collectGetFlowInitialMetricInfo(android.credentials.GetCredentialRequest getCredentialRequest) {
        try {
            this.mInitialPhaseMetric.setOriginSpecified(getCredentialRequest.getOrigin() != null);
            this.mInitialPhaseMetric.setRequestCounts(getRequestCountMap(getCredentialRequest));
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error collecting get flow initial metric: " + e);
        }
    }

    public void collectMetricPerBrowsingSelect(android.credentials.selection.UserSelectionDialogResult userSelectionDialogResult, com.android.server.credentials.metrics.CandidatePhaseMetric candidatePhaseMetric) {
        try {
            com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric candidateBrowsingPhaseMetric = new com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric();
            candidateBrowsingPhaseMetric.setEntryEnum(com.android.server.credentials.metrics.EntryEnum.getMetricCodeFromString(userSelectionDialogResult.getEntryKey()));
            candidateBrowsingPhaseMetric.setProviderUid(candidatePhaseMetric.getCandidateUid());
            this.mCandidateBrowsingPhaseMetric.add(candidateBrowsingPhaseMetric);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error collecting browsing metric: " + e);
        }
    }

    public void setHasExceptionFinalPhase(boolean z) {
        try {
            this.mChosenProviderFinalPhaseMetric.setHasException(z);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error setting final exception metric: " + e);
        }
    }

    public void collectFrameworkException(java.lang.String str) {
        try {
            this.mChosenProviderFinalPhaseMetric.setFrameworkException(com.android.server.credentials.MetricUtilities.generateMetricKey(str, 30));
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unexpected error during metric logging: " + e);
        }
    }

    public void collectFinalPhaseProviderMetricStatus(boolean z, com.android.server.credentials.metrics.ProviderStatusForMetrics providerStatusForMetrics) {
        try {
            this.mChosenProviderFinalPhaseMetric.setHasException(z);
            this.mChosenProviderFinalPhaseMetric.setChosenProviderStatus(providerStatusForMetrics.getMetricCode());
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during final phase provider status metric logging: " + e);
        }
    }

    public void updateMetricsOnResponseReceived(java.util.Map<java.lang.String, com.android.server.credentials.ProviderSession> map, android.content.ComponentName componentName, boolean z) {
        try {
            com.android.server.credentials.ProviderSession providerSession = map.get(componentName.flattenToString());
            if (providerSession != null) {
                collectChosenMetricViaCandidateTransfer(providerSession.getProviderSessionMetric().getCandidatePhasePerProviderMetric(), z);
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Exception upon candidate to chosen metric transfer: " + e);
        }
    }

    public void collectChosenMetricViaCandidateTransfer(com.android.server.credentials.metrics.CandidatePhaseMetric candidatePhaseMetric, boolean z) {
        try {
            this.mChosenProviderFinalPhaseMetric.setChosenUid(candidatePhaseMetric.getCandidateUid());
            this.mChosenProviderFinalPhaseMetric.setPrimary(z);
            this.mChosenProviderFinalPhaseMetric.setQueryPhaseLatencyMicroseconds(candidatePhaseMetric.getQueryLatencyMicroseconds());
            this.mChosenProviderFinalPhaseMetric.setServiceBeganTimeNanoseconds(candidatePhaseMetric.getServiceBeganTimeNanoseconds());
            this.mChosenProviderFinalPhaseMetric.setQueryStartTimeNanoseconds(candidatePhaseMetric.getStartQueryTimeNanoseconds());
            this.mChosenProviderFinalPhaseMetric.setQueryEndTimeNanoseconds(candidatePhaseMetric.getQueryFinishTimeNanoseconds());
            this.mChosenProviderFinalPhaseMetric.setResponseCollective(candidatePhaseMetric.getResponseCollective());
            this.mChosenProviderFinalPhaseMetric.setFinalFinishTimeNanoseconds(java.lang.System.nanoTime());
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during metric candidate to final transfer: " + e);
        }
    }

    public void logFailureOrUserCancel(boolean z) {
        try {
            if (z) {
                setHasExceptionFinalPhase(false);
                logApiCalledAtFinish(com.android.server.credentials.metrics.ApiStatus.USER_CANCELED.getMetricCode());
            } else {
                logApiCalledAtFinish(com.android.server.credentials.metrics.ApiStatus.FAILURE.getMetricCode());
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during final metric failure emit: " + e);
        }
    }

    public void logCandidatePhaseMetrics(java.util.Map<java.lang.String, com.android.server.credentials.ProviderSession> map) {
        try {
            int i = this.mSequenceCounter + 1;
            this.mSequenceCounter = i;
            com.android.server.credentials.MetricUtilities.logApiCalledCandidatePhase(map, i, this.mInitialPhaseMetric);
            if (this.mInitialPhaseMetric.getApiName() != com.android.server.credentials.metrics.ApiName.GET_CREDENTIAL.getMetricCode() && this.mInitialPhaseMetric.getApiName() != com.android.server.credentials.metrics.ApiName.GET_CREDENTIAL_VIA_REGISTRY.getMetricCode()) {
                return;
            }
            com.android.server.credentials.MetricUtilities.logApiCalledCandidateGetMetric(map, this.mSequenceCounter);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during candidate metric emit: " + e);
        }
    }

    public void logCandidateAggregateMetrics(java.util.Map<java.lang.String, com.android.server.credentials.ProviderSession> map) {
        try {
            this.mCandidateAggregateMetric.collectAverages(map);
            com.android.server.credentials.metrics.CandidateAggregateMetric candidateAggregateMetric = this.mCandidateAggregateMetric;
            int i = this.mSequenceCounter + 1;
            this.mSequenceCounter = i;
            com.android.server.credentials.MetricUtilities.logApiCalledAggregateCandidate(candidateAggregateMetric, i);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during aggregate candidate logging " + e);
        }
    }

    public void logAuthEntry(com.android.server.credentials.metrics.BrowsedAuthenticationMetric browsedAuthenticationMetric) {
        try {
            if (browsedAuthenticationMetric.getProviderUid() == -1) {
                android.util.Slog.v(TAG, "An authentication entry was not clicked");
                return;
            }
            int i = this.mSequenceCounter + 1;
            this.mSequenceCounter = i;
            com.android.server.credentials.MetricUtilities.logApiCalledAuthenticationMetric(browsedAuthenticationMetric, i);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during auth entry metric emit: " + e);
        }
    }

    public void logApiCalledAtFinish(int i) {
        try {
            com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric = this.mChosenProviderFinalPhaseMetric;
            java.util.List<com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric> list = this.mCandidateBrowsingPhaseMetric;
            int i2 = this.mSequenceCounter + 1;
            this.mSequenceCounter = i2;
            com.android.server.credentials.MetricUtilities.logApiCalledFinalPhase(chosenProviderFinalPhaseMetric, list, i, i2);
            com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric2 = this.mChosenProviderFinalPhaseMetric;
            java.util.List<com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric> list2 = this.mCandidateBrowsingPhaseMetric;
            int i3 = this.mSequenceCounter + 1;
            this.mSequenceCounter = i3;
            com.android.server.credentials.MetricUtilities.logApiCalledNoUidFinal(chosenProviderFinalPhaseMetric2, list2, i, i3);
        } catch (java.lang.Exception e) {
            android.util.Slog.i(TAG, "Unexpected error during final metric emit: " + e);
        }
    }

    public int getSessionIdTrackTwo() {
        return this.mSessionIdTrackTwo;
    }
}
