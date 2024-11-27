package android.credentials.selection;

/* loaded from: classes.dex */
public final class FailureDialogResult extends android.credentials.selection.BaseDialogResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.FailureDialogResult> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.FailureDialogResult>() { // from class: android.credentials.selection.FailureDialogResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.FailureDialogResult createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.FailureDialogResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.FailureDialogResult[] newArray(int i) {
            return new android.credentials.selection.FailureDialogResult[i];
        }
    };
    private static final java.lang.String EXTRA_FAILURE_RESULT = "android.credentials.selection.extra.FAILURE_RESULT";
    private final java.lang.String mErrorMessage;

    public static android.credentials.selection.FailureDialogResult fromResultData(android.os.Bundle bundle) {
        return (android.credentials.selection.FailureDialogResult) bundle.getParcelable(EXTRA_FAILURE_RESULT, android.credentials.selection.FailureDialogResult.class);
    }

    public static void addToBundle(android.credentials.selection.FailureDialogResult failureDialogResult, android.os.Bundle bundle) {
        bundle.putParcelable(EXTRA_FAILURE_RESULT, failureDialogResult);
    }

    public FailureDialogResult(android.os.IBinder iBinder, java.lang.String str) {
        super(iBinder);
        this.mErrorMessage = str;
    }

    public java.lang.String getErrorMessage() {
        return this.mErrorMessage;
    }

    private FailureDialogResult(android.os.Parcel parcel) {
        super(parcel);
        this.mErrorMessage = parcel.readString8();
    }

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.mErrorMessage);
    }

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
