package android.credentials.selection;

/* loaded from: classes.dex */
public class BaseDialogResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.BaseDialogResult> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.BaseDialogResult>() { // from class: android.credentials.selection.BaseDialogResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.BaseDialogResult createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.BaseDialogResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.BaseDialogResult[] newArray(int i) {
            return new android.credentials.selection.BaseDialogResult[i];
        }
    };
    private static final java.lang.String EXTRA_BASE_RESULT = "android.credentials.selection.extra.BASE_RESULT";
    public static final int RESULT_CODE_CANCELED_AND_LAUNCHED_SETTINGS = 1;
    public static final int RESULT_CODE_DATA_PARSING_FAILURE = 3;
    public static final int RESULT_CODE_DIALOG_COMPLETE_WITH_SELECTION = 2;
    public static final int RESULT_CODE_DIALOG_USER_CANCELED = 0;

    @java.lang.Deprecated
    private final android.os.IBinder mRequestToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    public static android.credentials.selection.BaseDialogResult fromResultData(android.os.Bundle bundle) {
        return (android.credentials.selection.BaseDialogResult) bundle.getParcelable(EXTRA_BASE_RESULT, android.credentials.selection.BaseDialogResult.class);
    }

    public static void addToBundle(android.credentials.selection.BaseDialogResult baseDialogResult, android.os.Bundle bundle) {
        bundle.putParcelable(EXTRA_BASE_RESULT, baseDialogResult);
    }

    public BaseDialogResult(android.os.IBinder iBinder) {
        this.mRequestToken = iBinder;
    }

    @java.lang.Deprecated
    public android.os.IBinder getRequestToken() {
        return this.mRequestToken;
    }

    protected BaseDialogResult(android.os.Parcel parcel) {
        this.mRequestToken = parcel.readStrongBinder();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mRequestToken);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
