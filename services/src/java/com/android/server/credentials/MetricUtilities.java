package com.android.server.credentials;

/* loaded from: classes.dex */
public class MetricUtilities {
    public static final int DEFAULT_INT_32 = -1;
    public static final java.lang.String DEFAULT_STRING = "";
    public static final int DELTA_EXCEPTION_CUT = 30;
    public static final int DELTA_RESPONSES_CUT = 20;
    private static final boolean LOG_FLAG = true;
    public static final int MIN_EMIT_WAIT_TIME_MS = 10;
    private static final java.lang.String TAG = "MetricUtilities";
    public static final int UNIT = 1;
    public static final java.lang.String USER_CANCELED_SUBSTRING = "TYPE_USER_CANCELED";
    public static final int ZERO = 0;
    public static final int[] DEFAULT_REPEATED_INT_32 = new int[0];
    public static final java.lang.String[] DEFAULT_REPEATED_STR = new java.lang.String[0];
    public static final boolean[] DEFAULT_REPEATED_BOOL = new boolean[0];

    protected static int getPackageUid(android.content.Context context, android.content.ComponentName componentName) {
        try {
            return context.getPackageManager().getApplicationInfo(componentName.getPackageName(), android.content.pm.PackageManager.ApplicationInfoFlags.of(0L)).uid;
        } catch (java.lang.Throwable th) {
            android.util.Slog.i(TAG, "Couldn't find required uid");
            return -1;
        }
    }

    public static int getHighlyUniqueInteger() {
        return new java.security.SecureRandom().nextInt();
    }

    protected static int getMetricTimestampDifferenceMicroseconds(long j, long j2) {
        long j3 = j - j2;
        if (j3 > 2147483647L) {
            android.util.Slog.i(TAG, "Input timestamps are too far apart and unsupported, falling back to default int");
            return -1;
        }
        if (j < j2) {
            android.util.Slog.i(TAG, "The timestamps aren't in expected order, falling back to default int");
            return -1;
        }
        return (int) (j3 / 1000);
    }

    public static java.lang.String generateMetricKey(java.lang.String str, int i) {
        return str.substring(str.length() - i);
    }

    public static void logApiCalledFinalPhase(com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric, java.util.List<com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric> list, int i, int i2) {
        try {
            int size = list.size();
            int[] iArr = new int[size];
            int[] iArr2 = new int[size];
            int i3 = 0;
            for (com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric candidateBrowsingPhaseMetric : list) {
                iArr[i3] = candidateBrowsingPhaseMetric.getEntryEnum();
                iArr2[i3] = candidateBrowsingPhaseMetric.getProviderUid();
                i3++;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CREDENTIAL_MANAGER_FINAL_PHASE_REPORTED, chosenProviderFinalPhaseMetric.getSessionIdProvider(), i2, chosenProviderFinalPhaseMetric.isUiReturned(), chosenProviderFinalPhaseMetric.getChosenUid(), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getQueryStartTimeNanoseconds()), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getQueryEndTimeNanoseconds()), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getUiCallStartTimeNanoseconds()), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getUiCallEndTimeNanoseconds()), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getFinalFinishTimeNanoseconds()), chosenProviderFinalPhaseMetric.getChosenProviderStatus(), chosenProviderFinalPhaseMetric.isHasException(), DEFAULT_REPEATED_INT_32, -1, -1, -1, -1, -1, iArr, iArr2, i, chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueEntries(), chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueEntryCounts(), chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueResponseStrings(), chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueResponseCounts(), chosenProviderFinalPhaseMetric.getFrameworkException(), chosenProviderFinalPhaseMetric.isPrimary());
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unexpected error during final provider uid emit: " + e);
        }
    }

    public static void logApiCalledAuthenticationMetric(com.android.server.credentials.metrics.BrowsedAuthenticationMetric browsedAuthenticationMetric, int i) {
        try {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CREDENTIAL_MANAGER_AUTH_CLICK_REPORTED, browsedAuthenticationMetric.getSessionIdProvider(), i, browsedAuthenticationMetric.getProviderUid(), browsedAuthenticationMetric.getAuthEntryCollective().getUniqueResponseStrings(), browsedAuthenticationMetric.getAuthEntryCollective().getUniqueResponseCounts(), browsedAuthenticationMetric.getAuthEntryCollective().getUniqueEntries(), browsedAuthenticationMetric.getAuthEntryCollective().getUniqueEntryCounts(), browsedAuthenticationMetric.getFrameworkException(), browsedAuthenticationMetric.isHasException(), browsedAuthenticationMetric.getProviderStatus(), browsedAuthenticationMetric.isAuthReturned());
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unexpected error during candidate auth metric logging: " + e);
        }
    }

    public static void logApiCalledCandidateGetMetric(java.util.Map<java.lang.String, com.android.server.credentials.ProviderSession> map, int i) {
        try {
            java.util.Iterator<com.android.server.credentials.ProviderSession> it = map.values().iterator();
            while (it.hasNext()) {
                com.android.server.credentials.metrics.CandidatePhaseMetric candidatePhasePerProviderMetric = it.next().getProviderSessionMetric().getCandidatePhasePerProviderMetric();
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CREDENTIAL_MANAGER_GET_REPORTED, candidatePhasePerProviderMetric.getSessionIdProvider(), i, candidatePhasePerProviderMetric.getCandidateUid(), candidatePhasePerProviderMetric.getResponseCollective().getUniqueResponseStrings(), candidatePhasePerProviderMetric.getResponseCollective().getUniqueResponseCounts());
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unexpected error during candidate get metric logging: " + e);
        }
    }

    public static void logApiCalledCandidatePhase(java.util.Map<java.lang.String, com.android.server.credentials.ProviderSession> map, int i, com.android.server.credentials.metrics.InitialPhaseMetric initialPhaseMetric) {
        try {
            java.util.Collection<com.android.server.credentials.ProviderSession> values = map.values();
            int size = values.size();
            int[] iArr = new int[size];
            int[] iArr2 = new int[size];
            int[] iArr3 = new int[size];
            int[] iArr4 = new int[size];
            boolean[] zArr = new boolean[size];
            int[] iArr5 = new int[size];
            int[] iArr6 = new int[size];
            int[] iArr7 = new int[size];
            int[] iArr8 = new int[size];
            int[] iArr9 = new int[size];
            int[] iArr10 = new int[size];
            java.lang.String[] strArr = new java.lang.String[size];
            boolean[] zArr2 = new boolean[size];
            java.util.Iterator<com.android.server.credentials.ProviderSession> it = values.iterator();
            int i2 = 0;
            boolean z = false;
            int i3 = -1;
            while (it.hasNext()) {
                com.android.server.credentials.metrics.CandidatePhaseMetric candidatePhasePerProviderMetric = it.next().mProviderSessionMetric.getCandidatePhasePerProviderMetric();
                java.util.Iterator<com.android.server.credentials.ProviderSession> it2 = it;
                if (i3 == -1) {
                    i3 = candidatePhasePerProviderMetric.getSessionIdProvider();
                }
                if (!z) {
                    z = candidatePhasePerProviderMetric.isQueryReturned();
                }
                iArr[i2] = candidatePhasePerProviderMetric.getCandidateUid();
                iArr2[i2] = candidatePhasePerProviderMetric.getTimestampFromReferenceStartMicroseconds(candidatePhasePerProviderMetric.getStartQueryTimeNanoseconds());
                iArr3[i2] = candidatePhasePerProviderMetric.getTimestampFromReferenceStartMicroseconds(candidatePhasePerProviderMetric.getQueryFinishTimeNanoseconds());
                iArr4[i2] = candidatePhasePerProviderMetric.getProviderQueryStatus();
                zArr[i2] = candidatePhasePerProviderMetric.isHasException();
                iArr5[i2] = candidatePhasePerProviderMetric.getResponseCollective().getNumEntriesTotal();
                iArr6[i2] = candidatePhasePerProviderMetric.getResponseCollective().getCountForEntry(com.android.server.credentials.metrics.EntryEnum.CREDENTIAL_ENTRY);
                iArr7[i2] = candidatePhasePerProviderMetric.getResponseCollective().getUniqueResponseStrings().length;
                iArr8[i2] = candidatePhasePerProviderMetric.getResponseCollective().getCountForEntry(com.android.server.credentials.metrics.EntryEnum.ACTION_ENTRY);
                iArr9[i2] = candidatePhasePerProviderMetric.getResponseCollective().getCountForEntry(com.android.server.credentials.metrics.EntryEnum.AUTHENTICATION_ENTRY);
                iArr10[i2] = candidatePhasePerProviderMetric.getResponseCollective().getCountForEntry(com.android.server.credentials.metrics.EntryEnum.REMOTE_ENTRY);
                strArr[i2] = candidatePhasePerProviderMetric.getFrameworkException();
                zArr2[i2] = candidatePhasePerProviderMetric.isPrimary();
                i2++;
                it = it2;
                i3 = i3;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CREDENTIAL_MANAGER_CANDIDATE_PHASE_REPORTED, i3, i, z, iArr, iArr2, iArr3, iArr4, zArr, iArr5, iArr8, iArr6, iArr7, iArr10, iArr9, strArr, initialPhaseMetric.isOriginSpecified(), initialPhaseMetric.getUniqueRequestStrings(), initialPhaseMetric.getUniqueRequestCounts(), initialPhaseMetric.getApiName(), zArr2);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unexpected error during candidate provider uid metric emit: " + e);
        }
    }

    public static void logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName apiName, com.android.server.credentials.metrics.ApiStatus apiStatus, int i) {
        try {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CREDENTIAL_MANAGER_APIV2_CALLED, apiName.getMetricCode(), i, apiStatus.getMetricCode());
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unexpected error during simple v2 metric logging: " + e);
        }
    }

    public static void logApiCalledInitialPhase(com.android.server.credentials.metrics.InitialPhaseMetric initialPhaseMetric, int i) {
        try {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CREDENTIAL_MANAGER_INIT_PHASE_REPORTED, initialPhaseMetric.getApiName(), initialPhaseMetric.getCallerUid(), initialPhaseMetric.getSessionIdCaller(), i, initialPhaseMetric.getCredentialServiceStartedTimeNanoseconds(), initialPhaseMetric.getCountRequestClassType(), initialPhaseMetric.getUniqueRequestStrings(), initialPhaseMetric.getUniqueRequestCounts(), initialPhaseMetric.isOriginSpecified(), initialPhaseMetric.getAutofillSessionId(), initialPhaseMetric.getAutofillRequestId());
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unexpected error during initial metric emit: " + e);
        }
    }

    public static void logApiCalledAggregateCandidate(com.android.server.credentials.metrics.CandidateAggregateMetric candidateAggregateMetric, int i) {
        try {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CREDENTIAL_MANAGER_TOTAL_REPORTED, candidateAggregateMetric.getSessionIdProvider(), i, candidateAggregateMetric.isQueryReturned(), candidateAggregateMetric.getNumProviders(), getMetricTimestampDifferenceMicroseconds(candidateAggregateMetric.getMinProviderTimestampNanoseconds(), candidateAggregateMetric.getServiceBeganTimeNanoseconds()), getMetricTimestampDifferenceMicroseconds(candidateAggregateMetric.getMaxProviderTimestampNanoseconds(), candidateAggregateMetric.getServiceBeganTimeNanoseconds()), candidateAggregateMetric.getAggregateCollectiveQuery().getUniqueResponseStrings(), candidateAggregateMetric.getAggregateCollectiveQuery().getUniqueResponseCounts(), candidateAggregateMetric.getAggregateCollectiveQuery().getUniqueEntries(), candidateAggregateMetric.getAggregateCollectiveQuery().getUniqueEntryCounts(), candidateAggregateMetric.getTotalQueryFailures(), candidateAggregateMetric.getUniqueExceptionStringsQuery(), candidateAggregateMetric.getUniqueExceptionCountsQuery(), candidateAggregateMetric.getAggregateCollectiveAuth().getUniqueResponseStrings(), candidateAggregateMetric.getAggregateCollectiveAuth().getUniqueResponseCounts(), candidateAggregateMetric.getAggregateCollectiveAuth().getUniqueEntries(), candidateAggregateMetric.getAggregateCollectiveAuth().getUniqueEntryCounts(), candidateAggregateMetric.getTotalAuthFailures(), candidateAggregateMetric.getUniqueExceptionStringsAuth(), candidateAggregateMetric.getUniqueExceptionCountsAuth(), candidateAggregateMetric.getNumAuthEntriesTapped(), candidateAggregateMetric.isAuthReturned());
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unexpected error during total candidate metric logging: " + e);
        }
    }

    public static void logApiCalledNoUidFinal(com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric, java.util.List<com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric> list, int i, int i2) {
        try {
            int size = list.size();
            int[] iArr = new int[size];
            int[] iArr2 = new int[size];
            java.util.Iterator<com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric> it = list.iterator();
            int i3 = 0;
            while (it.hasNext()) {
                iArr[i3] = it.next().getEntryEnum();
                iArr2[i3] = -1;
                i3++;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CREDENTIAL_MANAGER_FINALNOUID_REPORTED, chosenProviderFinalPhaseMetric.getSessionIdCaller(), i2, chosenProviderFinalPhaseMetric.isUiReturned(), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getQueryStartTimeNanoseconds()), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getQueryEndTimeNanoseconds()), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getUiCallStartTimeNanoseconds()), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getUiCallEndTimeNanoseconds()), chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(chosenProviderFinalPhaseMetric.getFinalFinishTimeNanoseconds()), chosenProviderFinalPhaseMetric.getChosenProviderStatus(), chosenProviderFinalPhaseMetric.isHasException(), chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueEntries(), chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueEntryCounts(), chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueResponseStrings(), chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueResponseCounts(), chosenProviderFinalPhaseMetric.getFrameworkException(), iArr, iArr2, i, chosenProviderFinalPhaseMetric.isPrimary());
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unexpected error during final no uid metric logging: " + e);
        }
    }
}
