package android.app.cloudsearch;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.cloudsearch.SearchResponse> CREATOR = new android.os.Parcelable.Creator<android.app.cloudsearch.SearchResponse>() { // from class: android.app.cloudsearch.SearchResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.cloudsearch.SearchResponse createFromParcel(android.os.Parcel parcel) {
            return new android.app.cloudsearch.SearchResponse();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.cloudsearch.SearchResponse[] newArray(int i) {
            return new android.app.cloudsearch.SearchResponse[i];
        }
    };
    public static final int SEARCH_STATUS_NO_INTERNET = 2;
    public static final int SEARCH_STATUS_OK = 0;
    public static final int SEARCH_STATUS_TIME_OUT = 1;
    public static final int SEARCH_STATUS_UNKNOWN = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SearchStatusCode {
    }

    private SearchResponse() {
    }

    public int getStatusCode() {
        return -1;
    }

    public java.lang.String getSource() {
        return "";
    }

    public java.util.List<android.app.cloudsearch.SearchResult> getSearchResults() {
        return new java.util.ArrayList();
    }

    public void setSource(java.lang.String str) {
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
        public Builder(int i) {
        }

        public android.app.cloudsearch.SearchResponse.Builder setStatusCode(int i) {
            return this;
        }

        public android.app.cloudsearch.SearchResponse.Builder setSource(java.lang.String str) {
            return this;
        }

        public android.app.cloudsearch.SearchResponse.Builder setSearchResults(java.util.List<android.app.cloudsearch.SearchResult> list) {
            return this;
        }

        public android.app.cloudsearch.SearchResponse build() {
            return new android.app.cloudsearch.SearchResponse();
        }
    }
}
