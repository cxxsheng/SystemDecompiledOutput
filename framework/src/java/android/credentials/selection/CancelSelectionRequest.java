package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CancelSelectionRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.CancelSelectionRequest> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.CancelSelectionRequest>() { // from class: android.credentials.selection.CancelSelectionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.CancelSelectionRequest createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.CancelSelectionRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.CancelSelectionRequest[] newArray(int i) {
            return new android.credentials.selection.CancelSelectionRequest[i];
        }
    };
    public static final java.lang.String EXTRA_CANCEL_UI_REQUEST = "android.credentials.selection.extra.CANCEL_UI_REQUEST";
    private final java.lang.String mPackageName;
    private final boolean mShouldShowCancellationExplanation;
    private final android.os.IBinder mToken;

    public android.os.IBinder getToken() {
        return this.mToken;
    }

    public android.credentials.selection.RequestToken getRequestToken() {
        return new android.credentials.selection.RequestToken(this.mToken);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public boolean shouldShowCancellationExplanation() {
        return this.mShouldShowCancellationExplanation;
    }

    public CancelSelectionRequest(android.credentials.selection.RequestToken requestToken, boolean z, java.lang.String str) {
        this.mToken = requestToken.getToken();
        this.mShouldShowCancellationExplanation = z;
        this.mPackageName = str;
    }

    private CancelSelectionRequest(android.os.Parcel parcel) {
        this.mToken = parcel.readStrongBinder();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mToken);
        this.mShouldShowCancellationExplanation = parcel.readBoolean();
        this.mPackageName = parcel.readString8();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mToken);
        parcel.writeBoolean(this.mShouldShowCancellationExplanation);
        parcel.writeString8(this.mPackageName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
