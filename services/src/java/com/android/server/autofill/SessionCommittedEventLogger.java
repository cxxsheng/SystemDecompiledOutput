package com.android.server.autofill;

/* loaded from: classes.dex */
public final class SessionCommittedEventLogger {
    private static final java.lang.String TAG = "SessionCommittedEventLogger";
    private java.util.Optional<com.android.server.autofill.SessionCommittedEventLogger.SessionCommittedEventInternal> mEventInternal = java.util.Optional.of(new com.android.server.autofill.SessionCommittedEventLogger.SessionCommittedEventInternal());
    private final int mSessionId;

    private SessionCommittedEventLogger(int i) {
        this.mSessionId = i;
    }

    public static com.android.server.autofill.SessionCommittedEventLogger forSessionId(int i) {
        return new com.android.server.autofill.SessionCommittedEventLogger(i);
    }

    public void maybeSetComponentPackageUid(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SessionCommittedEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SessionCommittedEventLogger.SessionCommittedEventInternal) obj).mComponentPackageUid = i;
            }
        });
    }

    public void maybeSetRequestCount(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SessionCommittedEventLogger$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SessionCommittedEventLogger.SessionCommittedEventInternal) obj).mRequestCount = i;
            }
        });
    }

    public void maybeSetCommitReason(final int i) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SessionCommittedEventLogger$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SessionCommittedEventLogger.SessionCommittedEventInternal) obj).mCommitReason = i;
            }
        });
    }

    public void maybeSetSessionDurationMillis(final long j) {
        this.mEventInternal.ifPresent(new java.util.function.Consumer() { // from class: com.android.server.autofill.SessionCommittedEventLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.autofill.SessionCommittedEventLogger.SessionCommittedEventInternal) obj).mSessionDurationMillis = j;
            }
        });
    }

    public void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            android.util.Slog.w(TAG, "Shouldn't be logging AutofillSessionCommitted again for same session.");
            return;
        }
        com.android.server.autofill.SessionCommittedEventLogger.SessionCommittedEventInternal sessionCommittedEventInternal = this.mEventInternal.get();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Log AutofillSessionCommitted: sessionId=" + this.mSessionId + " mComponentPackageUid=" + sessionCommittedEventInternal.mComponentPackageUid + " mRequestCount=" + sessionCommittedEventInternal.mRequestCount + " mCommitReason=" + sessionCommittedEventInternal.mCommitReason + " mSessionDurationMillis=" + sessionCommittedEventInternal.mSessionDurationMillis);
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.AUTOFILL_SESSION_COMMITTED, this.mSessionId, sessionCommittedEventInternal.mComponentPackageUid, sessionCommittedEventInternal.mRequestCount, sessionCommittedEventInternal.mCommitReason, sessionCommittedEventInternal.mSessionDurationMillis);
        this.mEventInternal = java.util.Optional.empty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SessionCommittedEventInternal {
        int mComponentPackageUid = -1;
        int mRequestCount = 0;
        int mCommitReason = 0;
        long mSessionDurationMillis = 0;

        SessionCommittedEventInternal() {
        }
    }
}
