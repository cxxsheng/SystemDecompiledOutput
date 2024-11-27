package android.webkit;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public final class WebViewProviderInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.webkit.WebViewProviderInfo> CREATOR = new android.os.Parcelable.Creator<android.webkit.WebViewProviderInfo>() { // from class: android.webkit.WebViewProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.webkit.WebViewProviderInfo createFromParcel(android.os.Parcel parcel) {
            return new android.webkit.WebViewProviderInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.webkit.WebViewProviderInfo[] newArray(int i) {
            return new android.webkit.WebViewProviderInfo[i];
        }
    };
    public final boolean availableByDefault;
    public final java.lang.String description;
    public final boolean isFallback;
    public final java.lang.String packageName;
    public final android.content.pm.Signature[] signatures;

    public WebViewProviderInfo(java.lang.String str, java.lang.String str2, boolean z, boolean z2, java.lang.String[] strArr) {
        this.packageName = str;
        this.description = str2;
        this.availableByDefault = z;
        this.isFallback = z2;
        if (strArr == null) {
            this.signatures = new android.content.pm.Signature[0];
            return;
        }
        this.signatures = new android.content.pm.Signature[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            this.signatures[i] = new android.content.pm.Signature(android.util.Base64.decode(strArr[i], 0));
        }
    }

    private WebViewProviderInfo(android.os.Parcel parcel) {
        this.packageName = parcel.readString();
        this.description = parcel.readString();
        this.availableByDefault = parcel.readInt() > 0;
        this.isFallback = parcel.readInt() > 0;
        this.signatures = (android.content.pm.Signature[]) parcel.createTypedArray(android.content.pm.Signature.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.description);
        parcel.writeInt(this.availableByDefault ? 1 : 0);
        parcel.writeInt(this.isFallback ? 1 : 0);
        parcel.writeTypedArray(this.signatures, 0);
    }
}
