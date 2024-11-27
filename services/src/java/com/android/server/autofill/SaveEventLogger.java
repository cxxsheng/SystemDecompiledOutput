package com.android.server.autofill;

/* loaded from: classes.dex */
public final class SaveEventLogger {
    public static final int NO_SAVE_REASON_DATASET_MATCH = 7;
    public static final int NO_SAVE_REASON_FIELD_VALIDATION_FAILED = 6;
    public static final int NO_SAVE_REASON_HAS_EMPTY_REQUIRED = 4;
    public static final int NO_SAVE_REASON_NONE = 1;
    public static final int NO_SAVE_REASON_NO_SAVE_INFO = 2;
    public static final int NO_SAVE_REASON_NO_VALUE_CHANGED = 5;
    public static final int NO_SAVE_REASON_SESSION_DESTROYED = 9;
    public static final int NO_SAVE_REASON_UNKNOWN = 0;
    public static final int NO_SAVE_REASON_WITH_DELAY_SAVE_FLAG = 3;
    public static final int NO_SAVE_REASON_WITH_DONT_SAVE_ON_FINISH_FLAG = 8;
    public static final int SAVE_UI_SHOWN_REASON_OPTIONAL_ID_CHANGE = 2;
    public static final int SAVE_UI_SHOWN_REASON_REQUIRED_ID_CHANGE = 1;
    public static final int SAVE_UI_SHOWN_REASON_TRIGGER_ID_SET = 3;
    public static final int SAVE_UI_SHOWN_REASON_UNKNOWN = 0;
    private static final java.lang.String TAG = "SaveEventLogger";
    private java.util.Optional<com.android.server.autofill.SaveEventLogger.SaveEventInternal> mEventInternal = java.util.Optional.of(new com.android.server.autofill.SaveEventLogger.SaveEventInternal());
    private final int mSessionId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SaveUiNotShownReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SaveUiShownReason {
    }

    private SaveEventLogger(int i) {
        this.mSessionId = i;
    }

    public static com.android.server.autofill.SaveEventLogger forSessionId(int i) {
        return new com.android.server.autofill.SaveEventLogger(i);
    }

    public void maybeSetRequestId(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mRequestId = i;
            }
        });
    }

    public void maybeSetAppPackageUid(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mAppPackageUid = i;
            }
        });
    }

    public void maybeSetSaveUiTriggerIds(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mSaveUiTriggerIds = i;
            }
        });
    }

    public void maybeSetFlag(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.SaveEventLogger.lambda$maybeSetFlag$3(i, (com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$maybeSetFlag$3(int i, com.android.server.autofill.SaveEventLogger.SaveEventInternal saveEventInternal) {
        saveEventInternal.mFlag = i;
    }

    public void maybeSetIsNewField(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mIsNewField = z;
            }
        });
    }

    public void maybeSetSaveUiShownReason(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mSaveUiShownReason = i;
            }
        });
    }

    public void maybeSetSaveUiNotShownReason(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mSaveUiNotShownReason = i;
            }
        });
    }

    public void maybeSetSaveButtonClicked(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mSaveButtonClicked = z;
            }
        });
    }

    public void maybeSetCancelButtonClicked(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mCancelButtonClicked = z;
            }
        });
    }

    public void maybeSetDialogDismissed(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mDialogDismissed = z;
            }
        });
    }

    public void maybeSetIsSaved(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mIsSaved = z;
            }
        });
    }

    public void maybeSetLatencySaveUiDisplayMillis(final long j) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mLatencySaveUiDisplayMillis = j;
            }
        });
    }

    public void maybeSetLatencySaveRequestMillis(final long j) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mLatencySaveRequestMillis = j;
            }
        });
    }

    public void maybeSetLatencySaveFinishMillis(final long j) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mLatencySaveFinishMillis = j;
            }
        });
    }

    public void maybeSetIsFrameworkCreatedSaveInfo(final boolean z) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SaveEventLogger.SaveEventInternal) obj).mIsFrameworkCreatedSaveInfo = z;
            }
        });
    }

    public void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            android.util.Slog.w(TAG, "Shouldn't be logging AutofillSaveEventReported again for same event");
            return;
        }
        com.android.server.autofill.SaveEventLogger.SaveEventInternal saveEventInternal = this.mEventInternal.get();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Log AutofillSaveEventReported: requestId=" + saveEventInternal.mRequestId + " sessionId=" + this.mSessionId + " mAppPackageUid=" + saveEventInternal.mAppPackageUid + " mSaveUiTriggerIds=" + saveEventInternal.mSaveUiTriggerIds + " mFlag=" + saveEventInternal.mFlag + " mIsNewField=" + saveEventInternal.mIsNewField + " mSaveUiShownReason=" + saveEventInternal.mSaveUiShownReason + " mSaveUiNotShownReason=" + saveEventInternal.mSaveUiNotShownReason + " mSaveButtonClicked=" + saveEventInternal.mSaveButtonClicked + " mCancelButtonClicked=" + saveEventInternal.mCancelButtonClicked + " mDialogDismissed=" + saveEventInternal.mDialogDismissed + " mIsSaved=" + saveEventInternal.mIsSaved + " mLatencySaveUiDisplayMillis=" + saveEventInternal.mLatencySaveUiDisplayMillis + " mLatencySaveRequestMillis=" + saveEventInternal.mLatencySaveRequestMillis + " mLatencySaveFinishMillis=" + saveEventInternal.mLatencySaveFinishMillis + " mIsFrameworkCreatedSaveInfo=" + saveEventInternal.mIsFrameworkCreatedSaveInfo);
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.AUTOFILL_SAVE_EVENT_REPORTED, saveEventInternal.mRequestId, this.mSessionId, saveEventInternal.mAppPackageUid, saveEventInternal.mSaveUiTriggerIds, saveEventInternal.mFlag, saveEventInternal.mIsNewField, saveEventInternal.mSaveUiShownReason, saveEventInternal.mSaveUiNotShownReason, saveEventInternal.mSaveButtonClicked, saveEventInternal.mCancelButtonClicked, saveEventInternal.mDialogDismissed, saveEventInternal.mIsSaved, saveEventInternal.mLatencySaveUiDisplayMillis, saveEventInternal.mLatencySaveRequestMillis, saveEventInternal.mLatencySaveFinishMillis, saveEventInternal.mIsFrameworkCreatedSaveInfo);
        this.mEventInternal = java.util.Optional.empty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SaveEventInternal {
        int mRequestId;
        int mAppPackageUid = -1;
        int mSaveUiTriggerIds = -1;
        long mFlag = -1;
        boolean mIsNewField = false;
        int mSaveUiShownReason = 0;
        int mSaveUiNotShownReason = 0;
        boolean mSaveButtonClicked = false;
        boolean mCancelButtonClicked = false;
        boolean mDialogDismissed = false;
        boolean mIsSaved = false;
        long mLatencySaveUiDisplayMillis = 0;
        long mLatencySaveRequestMillis = 0;
        long mLatencySaveFinishMillis = 0;
        boolean mIsFrameworkCreatedSaveInfo = false;

        SaveEventInternal() {
        }
    }
}
