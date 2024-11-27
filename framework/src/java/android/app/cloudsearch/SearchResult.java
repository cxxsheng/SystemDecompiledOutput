package android.app.cloudsearch;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.cloudsearch.SearchResult> CREATOR = new android.os.Parcelable.Creator<android.app.cloudsearch.SearchResult>() { // from class: android.app.cloudsearch.SearchResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.cloudsearch.SearchResult createFromParcel(android.os.Parcel parcel) {
            return new android.app.cloudsearch.SearchResult();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.cloudsearch.SearchResult[] newArray(int i) {
            return new android.app.cloudsearch.SearchResult[i];
        }
    };
    public static final java.lang.String EXTRAINFO_ACTION_APP_CARD = "android.app.cloudsearch.ACTION_APP_CARD";
    public static final java.lang.String EXTRAINFO_ACTION_BUTTON_IMAGE_PREREGISTERING = "android.app.cloudsearch.ACTION_BUTTON_IMAGE";
    public static final java.lang.String EXTRAINFO_ACTION_BUTTON_TEXT_PREREGISTERING = "android.app.cloudsearch.ACTION_BUTTON_TEXT";
    public static final java.lang.String EXTRAINFO_ACTION_INSTALL_BUTTON = "android.app.cloudsearch.ACTION_INSTALL_BUTTON";
    public static final java.lang.String EXTRAINFO_APP_BADGES = "android.app.cloudsearch.APP_BADGES";
    public static final java.lang.String EXTRAINFO_APP_CONTAINS_ADS_DISCLAIMER = "android.app.cloudsearch.APP_CONTAINS_ADS_DISCLAIMER";
    public static final java.lang.String EXTRAINFO_APP_CONTAINS_IAP_DISCLAIMER = "android.app.cloudsearch.APP_CONTAINS_IAP_DISCLAIMER";
    public static final java.lang.String EXTRAINFO_APP_DEVELOPER_NAME = "android.app.cloudsearch.APP_DEVELOPER_NAME";
    public static final java.lang.String EXTRAINFO_APP_DOMAIN_URL = "android.app.cloudsearch.APP_DOMAIN_URL";
    public static final java.lang.String EXTRAINFO_APP_IARC = "android.app.cloudsearch.APP_IARC";
    public static final java.lang.String EXTRAINFO_APP_ICON = "android.app.cloudsearch.APP_ICON";
    public static final java.lang.String EXTRAINFO_APP_INSTALL_COUNT = "android.app.cloudsearch.APP_INSTALL_COUNT";
    public static final java.lang.String EXTRAINFO_APP_PACKAGE_NAME = "android.app.cloudsearch.APP_PACKAGE_NAME";
    public static final java.lang.String EXTRAINFO_APP_REVIEW_COUNT = "android.app.cloudsearch.APP_REVIEW_COUNT";
    public static final java.lang.String EXTRAINFO_APP_SIZE_BYTES = "android.app.cloudsearch.APP_SIZE_BYTES";
    public static final java.lang.String EXTRAINFO_APP_STAR_RATING = "android.app.cloudsearch.APP_STAR_RATING";
    public static final java.lang.String EXTRAINFO_LONG_DESCRIPTION = "android.app.cloudsearch.LONG_DESCRIPTION";
    public static final java.lang.String EXTRAINFO_SCREENSHOTS = "android.app.cloudsearch.SCREENSHOTS";
    public static final java.lang.String EXTRAINFO_SHORT_DESCRIPTION = "android.app.cloudsearch.SHORT_DESCRIPTION";
    public static final java.lang.String EXTRAINFO_WEB_ICON = "android.app.cloudsearch.WEB_ICON";
    public static final java.lang.String EXTRAINFO_WEB_URL = "android.app.cloudsearch.WEB_URL";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SearchResultExtraInfoKey {
    }

    private SearchResult() {
    }

    public java.lang.String getTitle() {
        return "";
    }

    public java.lang.String getSnippet() {
        return "";
    }

    public float getScore() {
        return 0.0f;
    }

    public android.os.Bundle getExtraInfos() {
        return android.os.Bundle.EMPTY;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        @android.annotation.SystemApi
        public Builder(java.lang.String str, android.os.Bundle bundle) {
        }

        public android.app.cloudsearch.SearchResult.Builder setTitle(java.lang.String str) {
            return this;
        }

        public android.app.cloudsearch.SearchResult.Builder setSnippet(java.lang.String str) {
            return this;
        }

        public android.app.cloudsearch.SearchResult.Builder setScore(float f) {
            return this;
        }

        public android.app.cloudsearch.SearchResult.Builder setExtraInfos(android.os.Bundle bundle) {
            return this;
        }

        public android.app.cloudsearch.SearchResult build() {
            return new android.app.cloudsearch.SearchResult();
        }
    }
}
