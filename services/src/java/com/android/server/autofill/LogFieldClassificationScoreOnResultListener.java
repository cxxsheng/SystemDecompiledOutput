package com.android.server.autofill;

/* loaded from: classes.dex */
class LogFieldClassificationScoreOnResultListener implements android.os.RemoteCallback.OnResultListener {
    private static final java.lang.String TAG = "LogFieldClassificationScoreOnResultListener";
    private final android.view.autofill.AutofillId[] mAutofillIds;
    private final java.lang.String[] mCategoryIds;
    private final int mCommitReason;
    private final java.util.ArrayList<android.service.autofill.FieldClassification> mDetectedFieldClassifications;
    private final java.util.ArrayList<android.view.autofill.AutofillId> mDetectedFieldIds;
    private final int mSaveDialogNotShowReason;
    private com.android.server.autofill.Session mSession;
    private final java.lang.String[] mUserValues;
    private final int mViewsSize;

    LogFieldClassificationScoreOnResultListener(com.android.server.autofill.Session session, int i, int i2, int i3, android.view.autofill.AutofillId[] autofillIdArr, java.lang.String[] strArr, java.lang.String[] strArr2, java.util.ArrayList<android.view.autofill.AutofillId> arrayList, java.util.ArrayList<android.service.autofill.FieldClassification> arrayList2) {
        this.mSession = session;
        this.mSaveDialogNotShowReason = i;
        this.mCommitReason = i2;
        this.mViewsSize = i3;
        this.mAutofillIds = autofillIdArr;
        this.mUserValues = strArr;
        this.mCategoryIds = strArr2;
        this.mDetectedFieldIds = arrayList;
        this.mDetectedFieldClassifications = arrayList2;
    }

    public void onResult(@android.annotation.Nullable android.os.Bundle bundle) {
        com.android.server.autofill.Session session = this.mSession;
        if (session == null) {
            android.util.Slog.wtf(TAG, "session is null when calling onResult()");
        } else {
            session.handleLogFieldClassificationScore(bundle, this.mSaveDialogNotShowReason, this.mCommitReason, this.mViewsSize, this.mAutofillIds, this.mUserValues, this.mCategoryIds, this.mDetectedFieldIds, this.mDetectedFieldClassifications);
            this.mSession = null;
        }
    }
}
