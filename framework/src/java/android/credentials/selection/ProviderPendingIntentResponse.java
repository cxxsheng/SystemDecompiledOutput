package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class ProviderPendingIntentResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.ProviderPendingIntentResponse> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.ProviderPendingIntentResponse>() { // from class: android.credentials.selection.ProviderPendingIntentResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.ProviderPendingIntentResponse createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.ProviderPendingIntentResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.ProviderPendingIntentResponse[] newArray(int i) {
            return new android.credentials.selection.ProviderPendingIntentResponse[i];
        }
    };
    private final int mResultCode;
    private final android.content.Intent mResultData;

    public ProviderPendingIntentResponse(int i, android.content.Intent intent) {
        this.mResultCode = i;
        this.mResultData = intent;
    }

    private ProviderPendingIntentResponse(android.os.Parcel parcel) {
        this.mResultCode = parcel.readInt();
        this.mResultData = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResultCode);
        parcel.writeTypedObject(this.mResultData, i);
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public android.content.Intent getResultData() {
        return this.mResultData;
    }
}
