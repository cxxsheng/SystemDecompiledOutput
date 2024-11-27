package com.android.server.autofill;

/* loaded from: classes.dex */
public final class FillRequestEventLogger {
    private static final java.lang.String TAG = "FillRequestEventLogger";
    public static final int TRIGGER_REASON_EXPLICITLY_REQUESTED = 1;
    public static final int TRIGGER_REASON_NORMAL_TRIGGER = 4;
    public static final int TRIGGER_REASON_PRE_TRIGGER = 3;
    public static final int TRIGGER_REASON_RETRIGGER = 2;
    public static final int TRIGGER_REASON_SERVED_FROM_CACHED_RESPONSE = 5;
    public static final int TRIGGER_REASON_UNKNOWN = 0;
    private java.util.Optional<com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal> mEventInternal = java.util.Optional.empty();
    private final int mSessionId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TriggerReason {
    }

    private FillRequestEventLogger(int i) {
        this.mSessionId = i;
    }

    public static com.android.server.autofill.FillRequestEventLogger forSessionId(int i) {
        return new com.android.server.autofill.FillRequestEventLogger(i);
    }

    public void startLogForNewRequest() {
        if (!this.mEventInternal.isEmpty()) {
            android.util.Slog.w(TAG, "FillRequestEventLogger is not empty before starting for a new request");
        }
        this.mEventInternal = java.util.Optional.of(new com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal());
    }

    public void maybeSetRequestId(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj).mRequestId = i;
            }
        });
    }

    public void maybeSetAutofillServiceUid(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj).mAutofillServiceUid = i;
            }
        });
    }

    public void maybeSetInlineSuggestionHostUid(final android.content.Context context, final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.FillRequestEventLogger.lambda$maybeSetInlineSuggestionHostUid$2(context, i, (com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetInlineSuggestionHostUid$2(android.content.Context context, int i, com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal fillRequestEventInternal) {
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
            fillRequestEventInternal.mInlineSuggestionHostUid = context.getPackageManager().getApplicationInfoAsUser(packageName, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L), i).uid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, "Couldn't find packageName: " + packageName);
        }
    }

    public void maybeSetFlags(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj).mFlags = i;
            }
        });
    }

    public void maybeSetRequestTriggerReason(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj).mRequestTriggerReason = i;
            }
        });
    }

    public void maybeSetIsAugmented(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj).mIsAugmented = z;
            }
        });
    }

    public void maybeSetIsClientSuggestionFallback(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj).mIsClientSuggestionFallback = z;
            }
        });
    }

    public void maybeSetIsFillDialogEligible(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj).mIsFillDialogEligible = z;
            }
        });
    }

    public void maybeSetLatencyFillRequestSentMillis(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj).mLatencyFillRequestSentMillis = i;
            }
        });
    }

    public void maybeSetAppPackageUid(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal) obj).mAppPackageUid = i;
            }
        });
    }

    public void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            android.util.Slog.w(TAG, "Shouldn't be logging AutofillFillRequestReported again for same event");
            return;
        }
        com.android.server.autofill.FillRequestEventLogger.FillRequestEventInternal fillRequestEventInternal = this.mEventInternal.get();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Log AutofillFillRequestReported: requestId=" + fillRequestEventInternal.mRequestId + " sessionId=" + this.mSessionId + " mAutofillServiceUid=" + fillRequestEventInternal.mAutofillServiceUid + " mInlineSuggestionHostUid=" + fillRequestEventInternal.mInlineSuggestionHostUid + " mIsAugmented=" + fillRequestEventInternal.mIsAugmented + " mIsClientSuggestionFallback=" + fillRequestEventInternal.mIsClientSuggestionFallback + " mIsFillDialogEligible=" + fillRequestEventInternal.mIsFillDialogEligible + " mRequestTriggerReason=" + fillRequestEventInternal.mRequestTriggerReason + " mFlags=" + fillRequestEventInternal.mFlags + " mLatencyFillRequestSentMillis=" + fillRequestEventInternal.mLatencyFillRequestSentMillis + " mAppPackageUid=" + fillRequestEventInternal.mAppPackageUid);
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.AUTOFILL_FILL_REQUEST_REPORTED, fillRequestEventInternal.mRequestId, this.mSessionId, fillRequestEventInternal.mAutofillServiceUid, fillRequestEventInternal.mInlineSuggestionHostUid, fillRequestEventInternal.mIsAugmented, fillRequestEventInternal.mIsClientSuggestionFallback, fillRequestEventInternal.mIsFillDialogEligible, fillRequestEventInternal.mRequestTriggerReason, fillRequestEventInternal.mFlags, fillRequestEventInternal.mLatencyFillRequestSentMillis, fillRequestEventInternal.mAppPackageUid);
        this.mEventInternal = java.util.Optional.empty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FillRequestEventInternal {
        int mRequestId;
        int mAppPackageUid = -1;
        int mAutofillServiceUid = -1;
        int mInlineSuggestionHostUid = -1;
        boolean mIsAugmented = false;
        boolean mIsClientSuggestionFallback = false;
        boolean mIsFillDialogEligible = false;
        int mRequestTriggerReason = 0;
        int mFlags = -1;
        int mLatencyFillRequestSentMillis = -1;

        FillRequestEventInternal() {
        }
    }
}
