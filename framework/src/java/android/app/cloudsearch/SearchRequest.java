package android.app.cloudsearch;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchRequest implements android.os.Parcelable {
    public static final java.lang.String CONSTRAINT_IS_PRESUBMIT_SUGGESTION = "android.app.cloudsearch.IS_PRESUBMIT_SUGGESTION";
    public static final java.lang.String CONSTRAINT_SEARCH_PROVIDER_FILTER = "android.app.cloudsearch.SEARCH_PROVIDER_FILTER";
    public static final android.os.Parcelable.Creator<android.app.cloudsearch.SearchRequest> CREATOR = new android.os.Parcelable.Creator<android.app.cloudsearch.SearchRequest>() { // from class: android.app.cloudsearch.SearchRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.cloudsearch.SearchRequest createFromParcel(android.os.Parcel parcel) {
            return new android.app.cloudsearch.SearchRequest();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.cloudsearch.SearchRequest[] newArray(int i) {
            return new android.app.cloudsearch.SearchRequest[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SearchConstraintKey {
    }

    private SearchRequest() {
    }

    public java.lang.String getQuery() {
        return "";
    }

    public int getResultOffset() {
        return 0;
    }

    public int getResultNumber() {
        return 0;
    }

    public float getMaxLatencyMillis() {
        return 0.0f;
    }

    public android.os.Bundle getSearchConstraints() {
        return android.os.Bundle.EMPTY;
    }

    public java.lang.String getCallerPackageName() {
        return "";
    }

    public java.lang.String getRequestId() {
        return "";
    }

    public void setCallerPackageName(java.lang.String str) {
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

    public java.lang.String toString() {
        return "";
    }

    public int hashCode() {
        return 0;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        @android.annotation.SystemApi
        public Builder(java.lang.String str) {
        }

        public android.app.cloudsearch.SearchRequest.Builder setQuery(java.lang.String str) {
            return this;
        }

        public android.app.cloudsearch.SearchRequest.Builder setResultOffset(int i) {
            return this;
        }

        public android.app.cloudsearch.SearchRequest.Builder setResultNumber(int i) {
            return this;
        }

        public android.app.cloudsearch.SearchRequest.Builder setMaxLatencyMillis(float f) {
            return this;
        }

        public android.app.cloudsearch.SearchRequest.Builder setSearchConstraints(android.os.Bundle bundle) {
            return this;
        }

        public android.app.cloudsearch.SearchRequest.Builder setCallerPackageName(java.lang.String str) {
            return this;
        }

        public android.app.cloudsearch.SearchRequest build() {
            return new android.app.cloudsearch.SearchRequest();
        }
    }
}
