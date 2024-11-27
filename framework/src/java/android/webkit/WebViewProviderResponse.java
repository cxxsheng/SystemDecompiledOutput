package android.webkit;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes4.dex */
public final class WebViewProviderResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.webkit.WebViewProviderResponse> CREATOR = new android.os.Parcelable.Creator<android.webkit.WebViewProviderResponse>() { // from class: android.webkit.WebViewProviderResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.webkit.WebViewProviderResponse createFromParcel(android.os.Parcel parcel) {
            return new android.webkit.WebViewProviderResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.webkit.WebViewProviderResponse[] newArray(int i) {
            return new android.webkit.WebViewProviderResponse[i];
        }
    };
    public static final int STATUS_FAILED_LISTING_WEBVIEW_PACKAGES = 4;
    public static final int STATUS_FAILED_WAITING_FOR_RELRO = 3;
    public static final int STATUS_SUCCESS = 0;
    public final android.content.pm.PackageInfo packageInfo;
    public final int status;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface WebViewProviderStatus {
    }

    public WebViewProviderResponse(android.content.pm.PackageInfo packageInfo, int i) {
        this.packageInfo = packageInfo;
        this.status = i;
    }

    private WebViewProviderResponse(android.os.Parcel parcel) {
        this.packageInfo = (android.content.pm.PackageInfo) parcel.readTypedObject(android.content.pm.PackageInfo.CREATOR);
        this.status = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.packageInfo, i);
        parcel.writeInt(this.status);
    }
}
