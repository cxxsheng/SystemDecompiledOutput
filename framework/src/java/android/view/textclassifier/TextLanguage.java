package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class TextLanguage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textclassifier.TextLanguage> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextLanguage>() { // from class: android.view.textclassifier.TextLanguage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextLanguage createFromParcel(android.os.Parcel parcel) {
            return android.view.textclassifier.TextLanguage.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextLanguage[] newArray(int i) {
            return new android.view.textclassifier.TextLanguage[i];
        }
    };
    static final android.view.textclassifier.TextLanguage EMPTY = new android.view.textclassifier.TextLanguage.Builder().build();
    private final android.os.Bundle mBundle;
    private final android.view.textclassifier.EntityConfidence mEntityConfidence;
    private final java.lang.String mId;

    private TextLanguage(java.lang.String str, android.view.textclassifier.EntityConfidence entityConfidence, android.os.Bundle bundle) {
        this.mId = str;
        this.mEntityConfidence = entityConfidence;
        this.mBundle = bundle;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public int getLocaleHypothesisCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public android.icu.util.ULocale getLocale(int i) {
        return android.icu.util.ULocale.forLanguageTag(this.mEntityConfidence.getEntities().get(i));
    }

    public float getConfidenceScore(android.icu.util.ULocale uLocale) {
        return this.mEntityConfidence.getConfidenceScore(uLocale.toLanguageTag());
    }

    public android.os.Bundle getExtras() {
        return this.mBundle;
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "TextLanguage {id=%s, locales=%s, bundle=%s}", this.mId, this.mEntityConfidence, this.mBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        this.mEntityConfidence.writeToParcel(parcel, i);
        parcel.writeBundle(this.mBundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.textclassifier.TextLanguage readFromParcel(android.os.Parcel parcel) {
        return new android.view.textclassifier.TextLanguage(parcel.readString(), android.view.textclassifier.EntityConfidence.CREATOR.createFromParcel(parcel), parcel.readBundle());
    }

    public static final class Builder {
        private android.os.Bundle mBundle;
        private final java.util.Map<java.lang.String, java.lang.Float> mEntityConfidenceMap = new android.util.ArrayMap();
        private java.lang.String mId;

        public android.view.textclassifier.TextLanguage.Builder putLocale(android.icu.util.ULocale uLocale, float f) {
            java.util.Objects.requireNonNull(uLocale);
            this.mEntityConfidenceMap.put(uLocale.toLanguageTag(), java.lang.Float.valueOf(f));
            return this;
        }

        public android.view.textclassifier.TextLanguage.Builder setId(java.lang.String str) {
            this.mId = str;
            return this;
        }

        public android.view.textclassifier.TextLanguage.Builder setExtras(android.os.Bundle bundle) {
            this.mBundle = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
            return this;
        }

        public android.view.textclassifier.TextLanguage build() {
            this.mBundle = this.mBundle == null ? android.os.Bundle.EMPTY : this.mBundle;
            return new android.view.textclassifier.TextLanguage(this.mId, new android.view.textclassifier.EntityConfidence(this.mEntityConfidenceMap), this.mBundle);
        }
    }

    public static final class Request implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextLanguage.Request> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextLanguage.Request>() { // from class: android.view.textclassifier.TextLanguage.Request.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextLanguage.Request createFromParcel(android.os.Parcel parcel) {
                return android.view.textclassifier.TextLanguage.Request.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextLanguage.Request[] newArray(int i) {
                return new android.view.textclassifier.TextLanguage.Request[i];
            }
        };
        private final android.os.Bundle mExtra;
        private android.view.textclassifier.SystemTextClassifierMetadata mSystemTcMetadata;
        private final java.lang.CharSequence mText;

        private Request(java.lang.CharSequence charSequence, android.os.Bundle bundle) {
            this.mText = charSequence;
            this.mExtra = bundle;
        }

        public java.lang.CharSequence getText() {
            return this.mText;
        }

        public java.lang.String getCallingPackageName() {
            if (this.mSystemTcMetadata != null) {
                return this.mSystemTcMetadata.getCallingPackageName();
            }
            return null;
        }

        public void setSystemTextClassifierMetadata(android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata) {
            this.mSystemTcMetadata = systemTextClassifierMetadata;
        }

        public android.view.textclassifier.SystemTextClassifierMetadata getSystemTextClassifierMetadata() {
            return this.mSystemTcMetadata;
        }

        public android.os.Bundle getExtras() {
            return this.mExtra;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeCharSequence(this.mText);
            parcel.writeBundle(this.mExtra);
            parcel.writeParcelable(this.mSystemTcMetadata, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.view.textclassifier.TextLanguage.Request readFromParcel(android.os.Parcel parcel) {
            java.lang.CharSequence readCharSequence = parcel.readCharSequence();
            android.os.Bundle readBundle = parcel.readBundle();
            android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata = (android.view.textclassifier.SystemTextClassifierMetadata) parcel.readParcelable(null, android.view.textclassifier.SystemTextClassifierMetadata.class);
            android.view.textclassifier.TextLanguage.Request request = new android.view.textclassifier.TextLanguage.Request(readCharSequence, readBundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }

        public static final class Builder {
            private android.os.Bundle mBundle;
            private final java.lang.CharSequence mText;

            public Builder(java.lang.CharSequence charSequence) {
                this.mText = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence);
            }

            public android.view.textclassifier.TextLanguage.Request.Builder setExtras(android.os.Bundle bundle) {
                this.mBundle = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
                return this;
            }

            public android.view.textclassifier.TextLanguage.Request build() {
                return new android.view.textclassifier.TextLanguage.Request(this.mText.toString(), this.mBundle == null ? android.os.Bundle.EMPTY : this.mBundle);
            }
        }
    }
}
