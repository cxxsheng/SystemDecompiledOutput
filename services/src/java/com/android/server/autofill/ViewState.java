package com.android.server.autofill;

/* loaded from: classes.dex */
final class ViewState {
    public static final int STATE_AUTOFILLED = 4;
    public static final int STATE_AUTOFILLED_ONCE = 2048;
    public static final int STATE_AUTOFILL_FAILED = 1024;
    public static final int STATE_CHANGED = 8;
    public static final int STATE_CHAR_REMOVED = 16384;
    public static final int STATE_FILLABLE = 2;
    public static final int STATE_FILL_DIALOG_SHOWN = 131072;
    public static final int STATE_IGNORED = 128;
    public static final int STATE_INITIAL = 1;
    public static final int STATE_INLINE_DISABLED = 32768;
    public static final int STATE_INLINE_SHOWN = 8192;
    public static final int STATE_PENDING_CREATE_INLINE_REQUEST = 65536;
    public static final int STATE_RESTARTED_SESSION = 256;
    public static final int STATE_STARTED_PARTITION = 32;
    public static final int STATE_STARTED_SESSION = 16;
    public static final int STATE_TRIGGERED_AUGMENTED_AUTOFILL = 4096;
    public static final int STATE_URL_BAR = 512;
    public static final int STATE_WAITING_DATASET_AUTH = 64;
    private static final java.lang.String TAG = "ViewState";
    public final android.view.autofill.AutofillId id;
    private android.view.autofill.AutofillValue mAutofilledValue;
    private android.view.autofill.AutofillValue mCurrentValue;
    private java.lang.String mDatasetId;
    private final boolean mIsPrimaryCredential;
    private final com.android.server.autofill.ViewState.Listener mListener;
    private android.service.autofill.FillResponse mPrimaryFillResponse;
    private android.view.autofill.AutofillValue mSanitizedValue;
    private android.service.autofill.FillResponse mSecondaryFillResponse;
    private int mState;
    private android.graphics.Rect mVirtualBounds;

    interface Listener {
        void onFillReady(@android.annotation.NonNull android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.Nullable android.view.autofill.AutofillValue autofillValue, int i);
    }

    ViewState(android.view.autofill.AutofillId autofillId, com.android.server.autofill.ViewState.Listener listener, int i, boolean z) {
        this.id = autofillId;
        this.mListener = listener;
        this.mState = i;
        this.mIsPrimaryCredential = z;
    }

    @android.annotation.Nullable
    android.graphics.Rect getVirtualBounds() {
        return this.mVirtualBounds;
    }

    @android.annotation.Nullable
    android.view.autofill.AutofillValue getCurrentValue() {
        return this.mCurrentValue;
    }

    void setCurrentValue(android.view.autofill.AutofillValue autofillValue) {
        this.mCurrentValue = autofillValue;
    }

    @android.annotation.Nullable
    android.view.autofill.AutofillValue getAutofilledValue() {
        return this.mAutofilledValue;
    }

    void setAutofilledValue(@android.annotation.Nullable android.view.autofill.AutofillValue autofillValue) {
        this.mAutofilledValue = autofillValue;
    }

    @android.annotation.Nullable
    android.view.autofill.AutofillValue getSanitizedValue() {
        return this.mSanitizedValue;
    }

    void setSanitizedValue(@android.annotation.Nullable android.view.autofill.AutofillValue autofillValue) {
        this.mSanitizedValue = autofillValue;
    }

    @android.annotation.Nullable
    android.service.autofill.FillResponse getResponse() {
        return this.mPrimaryFillResponse;
    }

    @android.annotation.Nullable
    android.service.autofill.FillResponse getSecondaryResponse() {
        return this.mSecondaryFillResponse;
    }

    void setResponse(android.service.autofill.FillResponse fillResponse) {
        setResponse(fillResponse, true);
    }

    void setResponse(@android.annotation.Nullable android.service.autofill.FillResponse fillResponse, boolean z) {
        if (z) {
            this.mPrimaryFillResponse = fillResponse;
        } else {
            this.mSecondaryFillResponse = fillResponse;
        }
    }

    int getState() {
        return this.mState;
    }

    java.lang.String getStateAsString() {
        return getStateAsString(this.mState);
    }

    static java.lang.String getStateAsString(int i) {
        return android.util.DebugUtils.flagsToString(com.android.server.autofill.ViewState.class, "STATE_", i);
    }

    void setState(int i) {
        if (this.mState == 1) {
            this.mState = i;
        } else {
            this.mState |= i;
        }
        if (i == 4) {
            this.mState |= 2048;
        }
    }

    void resetState(int i) {
        this.mState = (~i) & this.mState;
    }

    @android.annotation.Nullable
    java.lang.String getDatasetId() {
        return this.mDatasetId;
    }

    void setDatasetId(java.lang.String str) {
        this.mDatasetId = str;
    }

    void update(@android.annotation.Nullable android.view.autofill.AutofillValue autofillValue, @android.annotation.Nullable android.graphics.Rect rect, int i) {
        if (autofillValue != null) {
            this.mCurrentValue = autofillValue;
        }
        if (rect != null) {
            this.mVirtualBounds = rect;
        }
        maybeCallOnFillReady(i);
    }

    void maybeCallOnFillReady(int i) {
        if ((this.mState & 4) != 0 && (i & 1) == 0) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "Ignoring UI for " + this.id + " on " + getStateAsString());
                return;
            }
            return;
        }
        android.service.autofill.FillResponse fillResponse = requestingPrimaryResponse(i) ? this.mPrimaryFillResponse : this.mSecondaryFillResponse;
        if (fillResponse != null) {
            if (fillResponse.getDatasets() != null || fillResponse.getAuthentication() != null) {
                this.mListener.onFillReady(fillResponse, this.id, this.mCurrentValue, i);
            }
        }
    }

    private boolean requestingPrimaryResponse(int i) {
        return this.mIsPrimaryCredential ? (i & 2048) != 0 : (i & 2048) == 0;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ViewState: [id=");
        sb.append(this.id);
        if (this.mDatasetId != null) {
            sb.append(", datasetId:");
            sb.append(this.mDatasetId);
        }
        sb.append(", state:");
        sb.append(getStateAsString());
        if (this.mCurrentValue != null) {
            sb.append(", currentValue:");
            sb.append(this.mCurrentValue);
        }
        if (this.mAutofilledValue != null) {
            sb.append(", autofilledValue:");
            sb.append(this.mAutofilledValue);
        }
        if (this.mSanitizedValue != null) {
            sb.append(", sanitizedValue:");
            sb.append(this.mSanitizedValue);
        }
        if (this.mVirtualBounds != null) {
            sb.append(", virtualBounds:");
            sb.append(this.mVirtualBounds);
        }
        sb.append("]");
        return sb.toString();
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("id:");
        printWriter.println(this.id);
        if (this.mDatasetId != null) {
            printWriter.print(str);
            printWriter.print("datasetId:");
            printWriter.println(this.mDatasetId);
        }
        printWriter.print(str);
        printWriter.print("state:");
        printWriter.println(getStateAsString());
        printWriter.print(str);
        printWriter.print("is primary credential:");
        printWriter.println(this.mIsPrimaryCredential);
        if (this.mPrimaryFillResponse != null) {
            printWriter.print(str);
            printWriter.print("primary response id:");
            printWriter.println(this.mPrimaryFillResponse.getRequestId());
        }
        if (this.mSecondaryFillResponse != null) {
            printWriter.print(str);
            printWriter.print("secondary response id:");
            printWriter.println(this.mSecondaryFillResponse.getRequestId());
        }
        if (this.mCurrentValue != null) {
            printWriter.print(str);
            printWriter.print("currentValue:");
            printWriter.println(this.mCurrentValue);
        }
        if (this.mAutofilledValue != null) {
            printWriter.print(str);
            printWriter.print("autofilledValue:");
            printWriter.println(this.mAutofilledValue);
        }
        if (this.mSanitizedValue != null) {
            printWriter.print(str);
            printWriter.print("sanitizedValue:");
            printWriter.println(this.mSanitizedValue);
        }
        if (this.mVirtualBounds != null) {
            printWriter.print(str);
            printWriter.print("virtualBounds:");
            printWriter.println(this.mVirtualBounds);
        }
    }
}
