package com.android.server.autofill;

/* loaded from: classes.dex */
public final class FieldClassificationEventLogger {
    public static final int STATUS_CANCELLED = 3;
    public static final int STATUS_FAIL = 2;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN = 0;
    private static final java.lang.String TAG = "FieldClassificationEventLogger";
    private java.util.Optional<com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal> mEventInternal = java.util.Optional.empty();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FieldClassificationStatus {
    }

    private FieldClassificationEventLogger() {
    }

    public static com.android.server.autofill.FieldClassificationEventLogger createLogger() {
        return new com.android.server.autofill.FieldClassificationEventLogger();
    }

    public void startNewLogForRequest() {
        if (!this.mEventInternal.isEmpty()) {
            android.util.Slog.w(TAG, "FieldClassificationEventLogger is not empty before starting for a new request");
        }
        this.mEventInternal = java.util.Optional.of(new com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal());
    }

    public void maybeSetLatencyMillis(final long j) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mLatencyClassificationRequestMillis = j;
            }
        });
    }

    public void maybeSetCountClassifications(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mCountClassifications = i;
            }
        });
    }

    public void maybeSetSessionId(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mSessionId = i;
            }
        });
    }

    public void maybeSetRequestId(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mRequestId = i;
            }
        });
    }

    public void maybeSetNextFillRequestId(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mNextFillRequestId = i;
            }
        });
    }

    public void maybeSetAppPackageUid(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mAppPackageUid = i;
            }
        });
    }

    public void maybeSetRequestStatus(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mStatus = i;
            }
        });
    }

    public void maybeSetSessionGc(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mIsSessionGc = z;
            }
        });
    }

    public void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            android.util.Slog.w(TAG, "Shouldn't be logging AutofillFieldClassificationEventInternal again for same event");
            return;
        }
        com.android.server.autofill.FieldClassificationEventLogger.FieldClassificationEventInternal fieldClassificationEventInternal = this.mEventInternal.get();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Log AutofillFieldClassificationEventReported: mLatencyClassificationRequestMillis=" + fieldClassificationEventInternal.mLatencyClassificationRequestMillis + " mCountClassifications=" + fieldClassificationEventInternal.mCountClassifications + " mSessionId=" + fieldClassificationEventInternal.mSessionId + " mRequestId=" + fieldClassificationEventInternal.mRequestId + " mNextFillRequestId=" + fieldClassificationEventInternal.mNextFillRequestId + " mAppPackageUid=" + fieldClassificationEventInternal.mAppPackageUid + " mStatus=" + fieldClassificationEventInternal.mStatus + " mIsSessionGc=" + fieldClassificationEventInternal.mIsSessionGc);
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.AUTOFILL_FIELD_CLASSIFICATION_EVENT_REPORTED, fieldClassificationEventInternal.mLatencyClassificationRequestMillis, fieldClassificationEventInternal.mCountClassifications, fieldClassificationEventInternal.mSessionId, fieldClassificationEventInternal.mRequestId, fieldClassificationEventInternal.mNextFillRequestId, fieldClassificationEventInternal.mAppPackageUid, fieldClassificationEventInternal.mStatus, fieldClassificationEventInternal.mIsSessionGc);
        this.mEventInternal = java.util.Optional.empty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FieldClassificationEventInternal {
        boolean mIsSessionGc;
        int mStatus;
        long mLatencyClassificationRequestMillis = -1;
        int mCountClassifications = -1;
        int mSessionId = -1;
        int mRequestId = -1;
        int mNextFillRequestId = -1;
        int mAppPackageUid = -1;

        FieldClassificationEventInternal() {
        }
    }
}
