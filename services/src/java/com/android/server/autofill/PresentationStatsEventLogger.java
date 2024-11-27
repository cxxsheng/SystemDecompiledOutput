package com.android.server.autofill;

/* loaded from: classes.dex */
public final class PresentationStatsEventLogger {
    public static final int AUTHENTICATION_RESULT_FAILURE = 2;
    public static final int AUTHENTICATION_RESULT_SUCCESS = 1;
    public static final int AUTHENTICATION_RESULT_UNKNOWN = 0;
    public static final int AUTHENTICATION_TYPE_DATASET_AUTHENTICATION = 1;
    public static final int AUTHENTICATION_TYPE_FULL_AUTHENTICATION = 2;
    public static final int AUTHENTICATION_TYPE_UNKNOWN = 0;
    public static final int DETECTION_PREFER_AUTOFILL_PROVIDER = 1;
    public static final int DETECTION_PREFER_PCC = 2;
    public static final int DETECTION_PREFER_UNKNOWN = 0;
    public static final int NOT_SHOWN_REASON_ACTIVITY_FINISHED = 4;
    public static final int NOT_SHOWN_REASON_ANY_SHOWN = 1;
    public static final int NOT_SHOWN_REASON_NO_FOCUS = 8;
    public static final int NOT_SHOWN_REASON_REQUEST_FAILED = 7;
    public static final int NOT_SHOWN_REASON_REQUEST_TIMEOUT = 5;
    public static final int NOT_SHOWN_REASON_SESSION_COMMITTED_PREMATURELY = 6;
    public static final int NOT_SHOWN_REASON_UNKNOWN = 0;
    public static final int NOT_SHOWN_REASON_VIEW_CHANGED = 3;
    public static final int NOT_SHOWN_REASON_VIEW_FOCUSED_BEFORE_FILL_DIALOG_RESPONSE = 9;
    public static final int NOT_SHOWN_REASON_VIEW_FOCUS_CHANGED = 2;
    public static final int PICK_REASON_NO_PCC = 1;
    public static final int PICK_REASON_PCC_DETECTION_ONLY = 4;
    public static final int PICK_REASON_PCC_DETECTION_PREFERRED_WITH_PROVIDER = 5;
    public static final int PICK_REASON_PROVIDER_DETECTION_ONLY = 2;
    public static final int PICK_REASON_PROVIDER_DETECTION_PREFERRED_WITH_PCC = 3;
    public static final int PICK_REASON_UNKNOWN = 0;
    private static final java.lang.String TAG = "PresentationStatsEventLogger";
    private final int mCallingAppUid;
    private java.util.Optional<com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal> mEventInternal = java.util.Optional.empty();
    private final int mSessionId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthenticationResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthenticationType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DatasetPickedReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DetectionPreference {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NotShownReason {
    }

    private PresentationStatsEventLogger(int i, int i2) {
        this.mSessionId = i;
        this.mCallingAppUid = i2;
    }

    public static com.android.server.autofill.PresentationStatsEventLogger createPresentationLog(int i, int i2) {
        return new com.android.server.autofill.PresentationStatsEventLogger(i, i2);
    }

    public void startNewEvent() {
        if (this.mEventInternal.isPresent()) {
            android.util.Slog.e(TAG, "Failed to start new event because already have active event.");
        } else {
            this.mEventInternal = java.util.Optional.of(new com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal());
        }
    }

    public void maybeSetRequestId(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda29
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mRequestId = i;
            }
        });
    }

    public void maybeSetIsCredentialRequest(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda24
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mIsCredentialRequest = z;
            }
        });
    }

    public void maybeSetWebviewRequestedCredential(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mWebviewRequestedCredential = z;
            }
        });
    }

    public void maybeSetNoPresentationEventReason(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.PresentationStatsEventLogger.lambda$maybeSetNoPresentationEventReason$3(i, (com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetNoPresentationEventReason$3(int i, com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal) {
        if (presentationStatsEventInternal.mCountShown == 0) {
            presentationStatsEventInternal.mNoPresentationReason = i;
        }
    }

    public void maybeSetNoPresentationEventReasonIfNoReasonExists(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.PresentationStatsEventLogger.lambda$maybeSetNoPresentationEventReasonIfNoReasonExists$4(i, (com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetNoPresentationEventReasonIfNoReasonExists$4(int i, com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal) {
        if (presentationStatsEventInternal.mCountShown == 0 && presentationStatsEventInternal.mNoPresentationReason == 0) {
            presentationStatsEventInternal.mNoPresentationReason = i;
        }
    }

    public void maybeSetAvailableCount(@android.annotation.Nullable final java.util.List<android.service.autofill.Dataset> list, final android.view.autofill.AutofillId autofillId) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.PresentationStatsEventLogger.lambda$maybeSetAvailableCount$5(list, autofillId, (com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetAvailableCount$5(java.util.List list, android.view.autofill.AutofillId autofillId, com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal) {
        com.android.server.autofill.PresentationStatsEventLogger.CountContainer datasetCountForAutofillId = getDatasetCountForAutofillId(list, autofillId);
        presentationStatsEventInternal.mAvailableCount = datasetCountForAutofillId.mAvailableCount;
        presentationStatsEventInternal.mAvailablePccCount = datasetCountForAutofillId.mAvailablePccCount;
        presentationStatsEventInternal.mAvailablePccOnlyCount = datasetCountForAutofillId.mAvailablePccOnlyCount;
        presentationStatsEventInternal.mIsDatasetAvailable = datasetCountForAutofillId.mAvailableCount > 0;
    }

    public void maybeSetCountShown(@android.annotation.Nullable final java.util.List<android.service.autofill.Dataset> list, final android.view.autofill.AutofillId autofillId) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.PresentationStatsEventLogger.lambda$maybeSetCountShown$6(list, autofillId, (com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetCountShown$6(java.util.List list, android.view.autofill.AutofillId autofillId, com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal) {
        com.android.server.autofill.PresentationStatsEventLogger.CountContainer datasetCountForAutofillId = getDatasetCountForAutofillId(list, autofillId);
        presentationStatsEventInternal.mCountShown = datasetCountForAutofillId.mAvailableCount;
        if (datasetCountForAutofillId.mAvailableCount > 0) {
            presentationStatsEventInternal.mNoPresentationReason = 1;
        }
    }

    private static com.android.server.autofill.PresentationStatsEventLogger.CountContainer getDatasetCountForAutofillId(@android.annotation.Nullable java.util.List<android.service.autofill.Dataset> list, android.view.autofill.AutofillId autofillId) {
        com.android.server.autofill.PresentationStatsEventLogger.CountContainer countContainer = new com.android.server.autofill.PresentationStatsEventLogger.CountContainer();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                android.service.autofill.Dataset dataset = list.get(i);
                if (dataset != null && dataset.getFieldIds() != null && dataset.getFieldIds().contains(autofillId)) {
                    countContainer.mAvailableCount++;
                    if (dataset.getEligibleReason() == 4) {
                        countContainer.mAvailablePccOnlyCount++;
                        countContainer.mAvailablePccCount++;
                    } else if (dataset.getEligibleReason() == 5) {
                        countContainer.mAvailablePccCount++;
                    }
                }
            }
        }
        return countContainer;
    }

    private static class CountContainer {
        int mAvailableCount;
        int mAvailablePccCount;
        int mAvailablePccOnlyCount;

        CountContainer() {
            this.mAvailableCount = 0;
            this.mAvailablePccCount = 0;
            this.mAvailablePccOnlyCount = 0;
        }

        CountContainer(int i, int i2, int i3) {
            this.mAvailableCount = 0;
            this.mAvailablePccCount = 0;
            this.mAvailablePccOnlyCount = 0;
            this.mAvailableCount = i;
            this.mAvailablePccCount = i2;
            this.mAvailablePccOnlyCount = i3;
        }
    }

    public void maybeSetCountFilteredUserTyping(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mCountFilteredUserTyping = i;
            }
        });
    }

    public void maybeSetCountNotShownImePresentationNotDrawn(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mCountNotShownImePresentationNotDrawn = i;
            }
        });
    }

    public void maybeSetCountNotShownImeUserNotSeen(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mCountNotShownImeUserNotSeen = i;
            }
        });
    }

    public void maybeSetDisplayPresentationType(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.PresentationStatsEventLogger.lambda$maybeSetDisplayPresentationType$10(i, (com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetDisplayPresentationType$10(int i, com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal) {
        presentationStatsEventInternal.mDisplayPresentationType = getDisplayPresentationType(i);
    }

    public void maybeSetFillRequestSentTimestampMs(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mFillRequestSentTimestampMs = i;
            }
        });
    }

    public void maybeSetFillResponseReceivedTimestampMs(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mFillResponseReceivedTimestampMs = i;
            }
        });
    }

    public void maybeSetSuggestionSentTimestampMs(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mSuggestionSentTimestampMs = i;
            }
        });
    }

    public void maybeSetSuggestionPresentedTimestampMs(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mSuggestionPresentedTimestampMs = i;
            }
        });
    }

    public void maybeSetSelectedDatasetId(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda28
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mSelectedDatasetId = i;
            }
        });
    }

    public void maybeSetDialogDismissed(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mDialogDismissed = z;
            }
        });
    }

    public void maybeSetNegativeCtaButtonClicked(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mNegativeCtaButtonClicked = z;
            }
        });
    }

    public void maybeSetPositiveCtaButtonClicked(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda26
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mPositiveCtaButtonClicked = z;
            }
        });
    }

    public void maybeSetInlinePresentationAndSuggestionHostUid(final android.content.Context context, final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.PresentationStatsEventLogger.lambda$maybeSetInlinePresentationAndSuggestionHostUid$19(context, i, (com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetInlinePresentationAndSuggestionHostUid$19(android.content.Context context, int i, com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal) {
        presentationStatsEventInternal.mDisplayPresentationType = 2;
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), "default_input_method", i);
        if (android.text.TextUtils.isEmpty(stringForUser)) {
            android.util.Slog.w(TAG, "No default IME found");
            return;
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(stringForUser);
        if (unflattenFromString == null) {
            android.util.Slog.w(TAG, "No default IME found");
            return;
        }
        java.lang.String packageName = unflattenFromString.getPackageName();
        try {
            presentationStatsEventInternal.mInlineSuggestionHostUid = context.getPackageManager().getApplicationInfoAsUser(packageName, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L), i).uid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, "Couldn't find packageName: " + packageName);
        }
    }

    public void maybeSetAutofillServiceUid(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mAutofillServiceUid = i;
            }
        });
    }

    public void maybeSetIsNewRequest(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mIsRequestTriggered = z;
            }
        });
    }

    public void maybeSetAuthenticationType(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mAuthenticationType = i;
            }
        });
    }

    public void maybeSetAuthenticationResult(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mAuthenticationResult = i;
            }
        });
    }

    public void maybeSetLatencyAuthenticationUiDisplayMillis(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mLatencyAuthenticationUiDisplayMillis = i;
            }
        });
    }

    public void maybeSetLatencyDatasetDisplayMillis(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mLatencyDatasetDisplayMillis = i;
            }
        });
    }

    public void maybeSetAvailablePccCount(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mAvailablePccCount = i;
            }
        });
    }

    public void maybeSetAvailablePccOnlyCount(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda30
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mAvailablePccOnlyCount = i;
            }
        });
    }

    public void maybeSetSelectedDatasetPickReason(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.PresentationStatsEventLogger.this.lambda$maybeSetSelectedDatasetPickReason$28(i, (com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeSetSelectedDatasetPickReason$28(int i, com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal) {
        presentationStatsEventInternal.mSelectedDatasetPickedReason = convertDatasetPickReason(i);
    }

    public void maybeSetDetectionPreference(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mDetectionPreference = i;
            }
        });
    }

    private int convertDatasetPickReason(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return i;
            default:
                return 0;
        }
    }

    public void maybeSetFieldClassificationRequestId(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal) obj).mFieldClassificationRequestId = i;
            }
        });
    }

    public void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            android.util.Slog.w(TAG, "Shouldn't be logging AutofillPresentationEventReported again for same event");
            return;
        }
        com.android.server.autofill.PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal = this.mEventInternal.get();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Log AutofillPresentationEventReported: requestId=" + presentationStatsEventInternal.mRequestId + " sessionId=" + this.mSessionId + " mNoPresentationEventReason=" + presentationStatsEventInternal.mNoPresentationReason + " mAvailableCount=" + presentationStatsEventInternal.mAvailableCount + " mCountShown=" + presentationStatsEventInternal.mCountShown + " mCountFilteredUserTyping=" + presentationStatsEventInternal.mCountFilteredUserTyping + " mCountNotShownImePresentationNotDrawn=" + presentationStatsEventInternal.mCountNotShownImePresentationNotDrawn + " mCountNotShownImeUserNotSeen=" + presentationStatsEventInternal.mCountNotShownImeUserNotSeen + " mDisplayPresentationType=" + presentationStatsEventInternal.mDisplayPresentationType + " mAutofillServiceUid=" + presentationStatsEventInternal.mAutofillServiceUid + " mInlineSuggestionHostUid=" + presentationStatsEventInternal.mInlineSuggestionHostUid + " mIsRequestTriggered=" + presentationStatsEventInternal.mIsRequestTriggered + " mFillRequestSentTimestampMs=" + presentationStatsEventInternal.mFillRequestSentTimestampMs + " mFillResponseReceivedTimestampMs=" + presentationStatsEventInternal.mFillResponseReceivedTimestampMs + " mSuggestionSentTimestampMs=" + presentationStatsEventInternal.mSuggestionSentTimestampMs + " mSuggestionPresentedTimestampMs=" + presentationStatsEventInternal.mSuggestionPresentedTimestampMs + " mSelectedDatasetId=" + presentationStatsEventInternal.mSelectedDatasetId + " mDialogDismissed=" + presentationStatsEventInternal.mDialogDismissed + " mNegativeCtaButtonClicked=" + presentationStatsEventInternal.mNegativeCtaButtonClicked + " mPositiveCtaButtonClicked=" + presentationStatsEventInternal.mPositiveCtaButtonClicked + " mAuthenticationType=" + presentationStatsEventInternal.mAuthenticationType + " mAuthenticationResult=" + presentationStatsEventInternal.mAuthenticationResult + " mLatencyAuthenticationUiDisplayMillis=" + presentationStatsEventInternal.mLatencyAuthenticationUiDisplayMillis + " mLatencyDatasetDisplayMillis=" + presentationStatsEventInternal.mLatencyDatasetDisplayMillis + " mAvailablePccCount=" + presentationStatsEventInternal.mAvailablePccCount + " mAvailablePccOnlyCount=" + presentationStatsEventInternal.mAvailablePccOnlyCount + " mSelectedDatasetPickedReason=" + presentationStatsEventInternal.mSelectedDatasetPickedReason + " mDetectionPreference=" + presentationStatsEventInternal.mDetectionPreference + " mFieldClassificationRequestId=" + presentationStatsEventInternal.mFieldClassificationRequestId + " mAppPackageUid=" + this.mCallingAppUid + " mIsCredentialRequest=" + presentationStatsEventInternal.mIsCredentialRequest + " mWebviewRequestedCredential=" + presentationStatsEventInternal.mWebviewRequestedCredential);
        }
        if (!presentationStatsEventInternal.mIsDatasetAvailable) {
            this.mEventInternal = java.util.Optional.empty();
        } else {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.AUTOFILL_PRESENTATION_EVENT_REPORTED, presentationStatsEventInternal.mRequestId, this.mSessionId, presentationStatsEventInternal.mNoPresentationReason, presentationStatsEventInternal.mAvailableCount, presentationStatsEventInternal.mCountShown, presentationStatsEventInternal.mCountFilteredUserTyping, presentationStatsEventInternal.mCountNotShownImePresentationNotDrawn, presentationStatsEventInternal.mCountNotShownImeUserNotSeen, presentationStatsEventInternal.mDisplayPresentationType, presentationStatsEventInternal.mAutofillServiceUid, presentationStatsEventInternal.mInlineSuggestionHostUid, presentationStatsEventInternal.mIsRequestTriggered, presentationStatsEventInternal.mFillRequestSentTimestampMs, presentationStatsEventInternal.mFillResponseReceivedTimestampMs, presentationStatsEventInternal.mSuggestionSentTimestampMs, presentationStatsEventInternal.mSuggestionPresentedTimestampMs, presentationStatsEventInternal.mSelectedDatasetId, presentationStatsEventInternal.mDialogDismissed, presentationStatsEventInternal.mNegativeCtaButtonClicked, presentationStatsEventInternal.mPositiveCtaButtonClicked, presentationStatsEventInternal.mAuthenticationType, presentationStatsEventInternal.mAuthenticationResult, presentationStatsEventInternal.mLatencyAuthenticationUiDisplayMillis, presentationStatsEventInternal.mLatencyDatasetDisplayMillis, presentationStatsEventInternal.mAvailablePccCount, presentationStatsEventInternal.mAvailablePccOnlyCount, presentationStatsEventInternal.mSelectedDatasetPickedReason, presentationStatsEventInternal.mDetectionPreference, presentationStatsEventInternal.mFieldClassificationRequestId, this.mCallingAppUid, presentationStatsEventInternal.mIsCredentialRequest, presentationStatsEventInternal.mWebviewRequestedCredential);
            this.mEventInternal = java.util.Optional.empty();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class PresentationStatsEventInternal {
        int mAvailableCount;
        int mCountFilteredUserTyping;
        int mCountNotShownImePresentationNotDrawn;
        int mCountNotShownImeUserNotSeen;
        int mCountShown;
        int mFillRequestSentTimestampMs;
        int mFillResponseReceivedTimestampMs;
        boolean mIsDatasetAvailable;
        boolean mIsRequestTriggered;
        int mRequestId;
        int mSuggestionPresentedTimestampMs;
        int mSuggestionSentTimestampMs;
        int mNoPresentationReason = 0;
        int mDisplayPresentationType = 0;
        int mAutofillServiceUid = -1;
        int mInlineSuggestionHostUid = -1;
        int mSelectedDatasetId = -1;
        boolean mDialogDismissed = false;
        boolean mNegativeCtaButtonClicked = false;
        boolean mPositiveCtaButtonClicked = false;
        int mAuthenticationType = 0;
        int mAuthenticationResult = 0;
        int mLatencyAuthenticationUiDisplayMillis = -1;
        int mLatencyDatasetDisplayMillis = -1;
        int mAvailablePccCount = -1;
        int mAvailablePccOnlyCount = -1;
        int mSelectedDatasetPickedReason = 0;
        int mDetectionPreference = 0;
        int mFieldClassificationRequestId = -1;
        boolean mIsCredentialRequest = false;
        boolean mWebviewRequestedCredential = false;

        PresentationStatsEventInternal() {
        }
    }

    static int getNoPresentationEventReason(int i) {
        switch (i) {
            case 1:
                return 4;
            case 2:
                return 6;
            case 3:
            default:
                return 0;
            case 4:
                return 3;
        }
    }

    private static int getDisplayPresentationType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }
}
