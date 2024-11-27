package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class FailureResult {
    public static final int ERROR_CODE_CANCELED_AND_LAUNCHED_SETTINGS = 2;
    public static final int ERROR_CODE_DIALOG_CANCELED_BY_USER = 1;
    public static final int ERROR_CODE_UI_FAILURE = 0;
    private final int mErrorCode;
    private final java.lang.String mErrorMessage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    public static void sendFailureResult(android.os.ResultReceiver resultReceiver, android.credentials.selection.FailureResult failureResult) {
        android.credentials.selection.FailureDialogResult failureDialogResult = failureResult.toFailureDialogResult();
        android.os.Bundle bundle = new android.os.Bundle();
        android.credentials.selection.FailureDialogResult.addToBundle(failureDialogResult, bundle);
        resultReceiver.send(failureResult.errorCodeToResultCode(), bundle);
    }

    public FailureResult(int i, java.lang.String str) {
        this.mErrorCode = i;
        this.mErrorMessage = str;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public java.lang.String getErrorMessage() {
        return this.mErrorMessage;
    }

    android.credentials.selection.FailureDialogResult toFailureDialogResult() {
        return new android.credentials.selection.FailureDialogResult((android.os.IBinder) null, this.mErrorMessage);
    }

    int errorCodeToResultCode() {
        switch (this.mErrorCode) {
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                return 3;
        }
    }
}
