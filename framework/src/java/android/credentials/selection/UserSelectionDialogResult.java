package android.credentials.selection;

/* loaded from: classes.dex */
public final class UserSelectionDialogResult extends android.credentials.selection.BaseDialogResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.UserSelectionDialogResult> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.UserSelectionDialogResult>() { // from class: android.credentials.selection.UserSelectionDialogResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.UserSelectionDialogResult createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.UserSelectionDialogResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.UserSelectionDialogResult[] newArray(int i) {
            return new android.credentials.selection.UserSelectionDialogResult[i];
        }
    };
    private static final java.lang.String EXTRA_USER_SELECTION_RESULT = "android.credentials.selection.extra.USER_SELECTION_RESULT";
    private final java.lang.String mEntryKey;
    private final java.lang.String mEntrySubkey;
    private final java.lang.String mProviderId;
    private android.credentials.selection.ProviderPendingIntentResponse mProviderPendingIntentResponse;

    public static android.credentials.selection.UserSelectionDialogResult fromResultData(android.os.Bundle bundle) {
        return (android.credentials.selection.UserSelectionDialogResult) bundle.getParcelable(EXTRA_USER_SELECTION_RESULT, android.credentials.selection.UserSelectionDialogResult.class);
    }

    public static void addToBundle(android.credentials.selection.UserSelectionDialogResult userSelectionDialogResult, android.os.Bundle bundle) {
        bundle.putParcelable(EXTRA_USER_SELECTION_RESULT, userSelectionDialogResult);
    }

    public UserSelectionDialogResult(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        super(iBinder);
        this.mProviderId = str;
        this.mEntryKey = str2;
        this.mEntrySubkey = str3;
    }

    public UserSelectionDialogResult(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, java.lang.String str3, android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        super(iBinder);
        this.mProviderId = str;
        this.mEntryKey = str2;
        this.mEntrySubkey = str3;
        this.mProviderPendingIntentResponse = providerPendingIntentResponse;
    }

    public java.lang.String getProviderId() {
        return this.mProviderId;
    }

    public java.lang.String getEntryKey() {
        return this.mEntryKey;
    }

    public java.lang.String getEntrySubkey() {
        return this.mEntrySubkey;
    }

    public android.credentials.selection.ProviderPendingIntentResponse getPendingIntentProviderResponse() {
        return this.mProviderPendingIntentResponse;
    }

    private UserSelectionDialogResult(android.os.Parcel parcel) {
        super(parcel);
        java.lang.String readString8 = parcel.readString8();
        java.lang.String readString82 = parcel.readString8();
        java.lang.String readString83 = parcel.readString8();
        this.mProviderId = readString8;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mProviderId);
        this.mEntryKey = readString82;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEntryKey);
        this.mEntrySubkey = readString83;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEntrySubkey);
        this.mProviderPendingIntentResponse = (android.credentials.selection.ProviderPendingIntentResponse) parcel.readTypedObject(android.credentials.selection.ProviderPendingIntentResponse.CREATOR);
    }

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.mProviderId);
        parcel.writeString8(this.mEntryKey);
        parcel.writeString8(this.mEntrySubkey);
        parcel.writeTypedObject(this.mProviderPendingIntentResponse, i);
    }

    @Override // android.credentials.selection.BaseDialogResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
