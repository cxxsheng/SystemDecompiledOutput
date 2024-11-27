package com.android.server.autofill;

/* loaded from: classes.dex */
public final class FillResponseEventLogger {
    public static final int AUTHENTICATION_RESULT_FAILURE = 2;
    public static final int AUTHENTICATION_RESULT_SUCCESS = 1;
    public static final int AUTHENTICATION_RESULT_UNKNOWN = 0;
    public static final int AUTHENTICATION_TYPE_DATASET_AHTHENTICATION = 1;
    public static final int AUTHENTICATION_TYPE_FULL_AHTHENTICATION = 2;
    public static final int AUTHENTICATION_TYPE_UNKNOWN = 0;
    public static final int AVAILABLE_COUNT_WHEN_FILL_REQUEST_FAILED_OR_TIMEOUT = -1;
    public static final int DETECTION_PREFER_AUTOFILL_PROVIDER = 1;
    public static final int DETECTION_PREFER_PCC = 2;
    public static final int DETECTION_PREFER_UNKNOWN = 0;
    public static final int DISPLAY_PRESENTATION_TYPE_DIALOG = 3;
    public static final int DISPLAY_PRESENTATION_TYPE_INLINE = 2;
    public static final int DISPLAY_PRESENTATION_TYPE_MENU = 1;
    public static final int DISPLAY_PRESENTATION_TYPE_UNKNOWN = 0;
    public static final int HAVE_SAVE_TRIGGER_ID = 1;
    public static final int RESPONSE_STATUS_CANCELLED = 3;
    public static final int RESPONSE_STATUS_FAILURE = 1;
    public static final int RESPONSE_STATUS_SESSION_DESTROYED = 5;
    public static final int RESPONSE_STATUS_SUCCESS = 2;
    public static final int RESPONSE_STATUS_TIMEOUT = 4;
    public static final int RESPONSE_STATUS_UNKNOWN = 0;
    private static final java.lang.String TAG = "FillResponseEventLogger";
    private static final long UNINITIALIZED_TIMESTAMP = -1;
    private final int mSessionId;
    private long startResponseProcessingTimestamp = -1;
    private java.util.Optional<com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal> mEventInternal = java.util.Optional.empty();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthenticationResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthenticationType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DetectionPreference {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisplayPresentationType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResponseStatus {
    }

    private FillResponseEventLogger(int i) {
        this.mSessionId = i;
    }

    public static com.android.server.autofill.FillResponseEventLogger forSessionId(int i) {
        return new com.android.server.autofill.FillResponseEventLogger(i);
    }

    public void startLogForNewResponse() {
        if (!this.mEventInternal.isEmpty()) {
            android.util.Slog.w(TAG, "FillResponseEventLogger is not empty before starting for a new request");
        }
        this.mEventInternal = java.util.Optional.of(new com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal());
    }

    public void maybeSetRequestId(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mRequestId = i;
            }
        });
    }

    public void maybeSetAppPackageUid(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mAppPackageUid = i;
            }
        });
    }

    public void maybeSetDisplayPresentationType(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mDisplayPresentationType = i;
            }
        });
    }

    public void maybeSetAvailableCount(@android.annotation.Nullable final java.util.List<android.service.autofill.Dataset> list, final android.view.autofill.AutofillId autofillId) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.FillResponseEventLogger.lambda$maybeSetAvailableCount$3(list, autofillId, (com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetAvailableCount$3(java.util.List list, android.view.autofill.AutofillId autofillId, com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal fillResponseEventInternal) {
        fillResponseEventInternal.mAvailableCount = getDatasetCountForAutofillId(list, autofillId);
    }

    public void maybeSetAvailableCount(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mAvailableCount = i;
            }
        });
    }

    public void maybeSetTotalDatasetsProvided(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.FillResponseEventLogger.lambda$maybeSetTotalDatasetsProvided$5(i, (com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetTotalDatasetsProvided$5(int i, com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal fillResponseEventInternal) {
        if (fillResponseEventInternal.mTotalDatasetsProvided == -1) {
            fillResponseEventInternal.mTotalDatasetsProvided = i;
        }
    }

    private static int getDatasetCountForAutofillId(@android.annotation.Nullable java.util.List<android.service.autofill.Dataset> list, android.view.autofill.AutofillId autofillId) {
        if (list == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            android.service.autofill.Dataset dataset = list.get(i2);
            if (dataset != null && dataset.getFieldIds() != null && dataset.getFieldIds().contains(autofillId)) {
                i++;
            }
        }
        return i;
    }

    public void maybeSetSaveUiTriggerIds(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mSaveUiTriggerIds = i;
            }
        });
    }

    public void maybeSetLatencyFillResponseReceivedMillis(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mLatencyFillResponseReceivedMillis = i;
            }
        });
    }

    public void maybeSetAuthenticationType(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mAuthenticationType = i;
            }
        });
    }

    public void maybeSetAuthenticationResult(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mAuthenticationResult = i;
            }
        });
    }

    public void maybeSetAuthenticationFailureReason(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mAuthenticationFailureReason = i;
            }
        });
    }

    public void maybeSetLatencyAuthenticationUiDisplayMillis(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mLatencyAuthenticationUiDisplayMillis = i;
            }
        });
    }

    public void maybeSetLatencyDatasetDisplayMillis(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mLatencyDatasetDisplayMillis = i;
            }
        });
    }

    public void maybeSetResponseStatus(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mResponseStatus = i;
            }
        });
    }

    public void startResponseProcessingTime() {
        this.startResponseProcessingTimestamp = android.os.SystemClock.elapsedRealtime();
    }

    public void maybeSetLatencyResponseProcessingMillis() {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.FillResponseEventLogger.this.lambda$maybeSetLatencyResponseProcessingMillis$14((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeSetLatencyResponseProcessingMillis$14(com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal fillResponseEventInternal) {
        if (this.startResponseProcessingTimestamp == -1 && com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "uninitialized startResponseProcessingTimestamp");
        }
        fillResponseEventInternal.mLatencyResponseProcessingMillis = android.os.SystemClock.elapsedRealtime() - this.startResponseProcessingTimestamp;
    }

    public void maybeSetAvailablePccCount(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mAvailablePccCount = i;
            }
        });
    }

    public void maybeSetAvailablePccOnlyCount(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mAvailablePccOnlyCount = i;
            }
        });
    }

    public void maybeSetDatasetsCountAfterPotentialPccFiltering(@android.annotation.Nullable final java.util.List<android.service.autofill.Dataset> list) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.FillResponseEventLogger.lambda$maybeSetDatasetsCountAfterPotentialPccFiltering$17(list, (com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetDatasetsCountAfterPotentialPccFiltering$17(java.util.List list, com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal fillResponseEventInternal) {
        int i;
        int i2;
        int i3 = 0;
        if (list == null) {
            i = 0;
            i2 = 0;
        } else {
            i = list.size();
            int i4 = 0;
            i2 = 0;
            while (i3 < list.size()) {
                android.service.autofill.Dataset dataset = (android.service.autofill.Dataset) list.get(i3);
                if (dataset != null) {
                    if (dataset.getEligibleReason() == 4) {
                        i4++;
                        i2++;
                    } else if (dataset.getEligibleReason() == 5) {
                        i2++;
                    }
                }
                i3++;
            }
            i3 = i4;
        }
        fillResponseEventInternal.mAvailablePccOnlyCount = i3;
        fillResponseEventInternal.mAvailablePccCount = i2;
        fillResponseEventInternal.mAvailableCount = i;
    }

    public void maybeSetDetectionPreference(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillResponseEventLogger$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal) obj).mDetectionPref = i;
            }
        });
    }

    public void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            android.util.Slog.w(TAG, "Shouldn't be logging AutofillFillRequestReported again for same event");
            return;
        }
        com.android.server.autofill.FillResponseEventLogger.FillResponseEventInternal fillResponseEventInternal = this.mEventInternal.get();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Log AutofillFillResponseReported: requestId=" + fillResponseEventInternal.mRequestId + " sessionId=" + this.mSessionId + " mAppPackageUid=" + fillResponseEventInternal.mAppPackageUid + " mDisplayPresentationType=" + fillResponseEventInternal.mDisplayPresentationType + " mAvailableCount=" + fillResponseEventInternal.mAvailableCount + " mSaveUiTriggerIds=" + fillResponseEventInternal.mSaveUiTriggerIds + " mLatencyFillResponseReceivedMillis=" + fillResponseEventInternal.mLatencyFillResponseReceivedMillis + " mAuthenticationType=" + fillResponseEventInternal.mAuthenticationType + " mAuthenticationResult=" + fillResponseEventInternal.mAuthenticationResult + " mAuthenticationFailureReason=" + fillResponseEventInternal.mAuthenticationFailureReason + " mLatencyAuthenticationUiDisplayMillis=" + fillResponseEventInternal.mLatencyAuthenticationUiDisplayMillis + " mLatencyDatasetDisplayMillis=" + fillResponseEventInternal.mLatencyDatasetDisplayMillis + " mResponseStatus=" + fillResponseEventInternal.mResponseStatus + " mLatencyResponseProcessingMillis=" + fillResponseEventInternal.mLatencyResponseProcessingMillis + " mAvailablePccCount=" + fillResponseEventInternal.mAvailablePccCount + " mAvailablePccOnlyCount=" + fillResponseEventInternal.mAvailablePccOnlyCount + " mTotalDatasetsProvided=" + fillResponseEventInternal.mTotalDatasetsProvided + " mDetectionPref=" + fillResponseEventInternal.mDetectionPref);
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.AUTOFILL_FILL_RESPONSE_REPORTED, fillResponseEventInternal.mRequestId, this.mSessionId, fillResponseEventInternal.mAppPackageUid, fillResponseEventInternal.mDisplayPresentationType, fillResponseEventInternal.mAvailableCount, fillResponseEventInternal.mSaveUiTriggerIds, fillResponseEventInternal.mLatencyFillResponseReceivedMillis, fillResponseEventInternal.mAuthenticationType, fillResponseEventInternal.mAuthenticationResult, fillResponseEventInternal.mAuthenticationFailureReason, fillResponseEventInternal.mLatencyAuthenticationUiDisplayMillis, fillResponseEventInternal.mLatencyDatasetDisplayMillis, fillResponseEventInternal.mResponseStatus, fillResponseEventInternal.mLatencyResponseProcessingMillis, fillResponseEventInternal.mAvailablePccCount, fillResponseEventInternal.mAvailablePccOnlyCount, fillResponseEventInternal.mTotalDatasetsProvided, fillResponseEventInternal.mDetectionPref);
        this.mEventInternal = java.util.Optional.empty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FillResponseEventInternal {
        int mRequestId = -1;
        int mAppPackageUid = -1;
        int mDisplayPresentationType = 0;
        int mAvailableCount = 0;
        int mSaveUiTriggerIds = -1;
        int mLatencyFillResponseReceivedMillis = -1;
        int mAuthenticationType = 0;
        int mAuthenticationResult = 0;
        int mAuthenticationFailureReason = -1;
        int mLatencyAuthenticationUiDisplayMillis = -1;
        int mLatencyDatasetDisplayMillis = -1;
        int mResponseStatus = 0;
        long mLatencyResponseProcessingMillis = -1;
        int mAvailablePccCount = -1;
        int mAvailablePccOnlyCount = -1;
        int mTotalDatasetsProvided = -1;
        int mDetectionPref = 0;

        FillResponseEventInternal() {
        }
    }
}
