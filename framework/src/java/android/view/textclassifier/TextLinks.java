package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class TextLinks implements android.os.Parcelable {
    public static final int APPLY_STRATEGY_IGNORE = 0;
    public static final int APPLY_STRATEGY_REPLACE = 1;
    public static final android.os.Parcelable.Creator<android.view.textclassifier.TextLinks> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextLinks>() { // from class: android.view.textclassifier.TextLinks.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextLinks createFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.TextLinks(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextLinks[] newArray(int i) {
            return new android.view.textclassifier.TextLinks[i];
        }
    };
    public static final int STATUS_DIFFERENT_TEXT = 3;
    public static final int STATUS_LINKS_APPLIED = 0;
    public static final int STATUS_NO_LINKS_APPLIED = 2;
    public static final int STATUS_NO_LINKS_FOUND = 1;
    public static final int STATUS_UNSUPPORTED_CHARACTER = 4;
    private final android.os.Bundle mExtras;
    private final java.lang.String mFullText;
    private final java.util.List<android.view.textclassifier.TextLinks.TextLink> mLinks;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ApplyStrategy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    private TextLinks(java.lang.String str, java.util.ArrayList<android.view.textclassifier.TextLinks.TextLink> arrayList, android.os.Bundle bundle) {
        this.mFullText = str;
        this.mLinks = java.util.Collections.unmodifiableList(arrayList);
        this.mExtras = bundle;
    }

    public java.lang.CharSequence getText() {
        return this.mFullText;
    }

    public java.util.Collection<android.view.textclassifier.TextLinks.TextLink> getLinks() {
        return this.mLinks;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public int apply(android.text.Spannable spannable, int i, java.util.function.Function<android.view.textclassifier.TextLinks.TextLink, android.view.textclassifier.TextLinks.TextLinkSpan> function) {
        java.util.Objects.requireNonNull(spannable);
        return new android.view.textclassifier.TextLinksParams.Builder().setApplyStrategy(i).setSpanFactory(function).build().apply(spannable, this);
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "TextLinks{fullText=%s, links=%s}", this.mFullText, this.mLinks);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mFullText);
        parcel.writeTypedList(this.mLinks);
        parcel.writeBundle(this.mExtras);
    }

    private TextLinks(android.os.Parcel parcel) {
        this.mFullText = parcel.readString();
        this.mLinks = parcel.createTypedArrayList(android.view.textclassifier.TextLinks.TextLink.CREATOR);
        this.mExtras = parcel.readBundle();
    }

    public static final class TextLink implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextLinks.TextLink> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextLinks.TextLink>() { // from class: android.view.textclassifier.TextLinks.TextLink.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextLinks.TextLink createFromParcel(android.os.Parcel parcel) {
                return android.view.textclassifier.TextLinks.TextLink.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextLinks.TextLink[] newArray(int i) {
                return new android.view.textclassifier.TextLinks.TextLink[i];
            }
        };
        private final int mEnd;
        private final android.view.textclassifier.EntityConfidence mEntityScores;
        private final android.os.Bundle mExtras;
        private final int mStart;
        private final android.text.style.URLSpan mUrlSpan;

        private TextLink(int i, int i2, android.view.textclassifier.EntityConfidence entityConfidence, android.os.Bundle bundle, android.text.style.URLSpan uRLSpan) {
            java.util.Objects.requireNonNull(entityConfidence);
            com.android.internal.util.Preconditions.checkArgument(!entityConfidence.getEntities().isEmpty());
            com.android.internal.util.Preconditions.checkArgument(i <= i2);
            java.util.Objects.requireNonNull(bundle);
            this.mStart = i;
            this.mEnd = i2;
            this.mEntityScores = entityConfidence;
            this.mUrlSpan = uRLSpan;
            this.mExtras = bundle;
        }

        public int getStart() {
            return this.mStart;
        }

        public int getEnd() {
            return this.mEnd;
        }

        public int getEntityCount() {
            return this.mEntityScores.getEntities().size();
        }

        public java.lang.String getEntity(int i) {
            return this.mEntityScores.getEntities().get(i);
        }

        public float getConfidenceScore(java.lang.String str) {
            return this.mEntityScores.getConfidenceScore(str);
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public java.lang.String toString() {
            return java.lang.String.format(java.util.Locale.US, "TextLink{start=%s, end=%s, entityScores=%s, urlSpan=%s}", java.lang.Integer.valueOf(this.mStart), java.lang.Integer.valueOf(this.mEnd), this.mEntityScores, this.mUrlSpan);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            this.mEntityScores.writeToParcel(parcel, i);
            parcel.writeInt(this.mStart);
            parcel.writeInt(this.mEnd);
            parcel.writeBundle(this.mExtras);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.view.textclassifier.TextLinks.TextLink readFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.TextLinks.TextLink(parcel.readInt(), parcel.readInt(), android.view.textclassifier.EntityConfidence.CREATOR.createFromParcel(parcel), parcel.readBundle(), null);
        }
    }

    public static final class Request implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextLinks.Request> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextLinks.Request>() { // from class: android.view.textclassifier.TextLinks.Request.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextLinks.Request createFromParcel(android.os.Parcel parcel) {
                return android.view.textclassifier.TextLinks.Request.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextLinks.Request[] newArray(int i) {
                return new android.view.textclassifier.TextLinks.Request[i];
            }
        };
        private final android.os.LocaleList mDefaultLocales;
        private final android.view.textclassifier.TextClassifier.EntityConfig mEntityConfig;
        private final android.os.Bundle mExtras;
        private final boolean mLegacyFallback;
        private final java.time.ZonedDateTime mReferenceTime;
        private android.view.textclassifier.SystemTextClassifierMetadata mSystemTcMetadata;
        private final java.lang.CharSequence mText;

        private Request(java.lang.CharSequence charSequence, android.os.LocaleList localeList, android.view.textclassifier.TextClassifier.EntityConfig entityConfig, boolean z, java.time.ZonedDateTime zonedDateTime, android.os.Bundle bundle) {
            this.mText = charSequence;
            this.mDefaultLocales = localeList;
            this.mEntityConfig = entityConfig;
            this.mLegacyFallback = z;
            this.mReferenceTime = zonedDateTime;
            this.mExtras = bundle;
        }

        public java.lang.CharSequence getText() {
            return this.mText;
        }

        public android.os.LocaleList getDefaultLocales() {
            return this.mDefaultLocales;
        }

        public android.view.textclassifier.TextClassifier.EntityConfig getEntityConfig() {
            return this.mEntityConfig;
        }

        public boolean isLegacyFallback() {
            return this.mLegacyFallback;
        }

        public java.time.ZonedDateTime getReferenceTime() {
            return this.mReferenceTime;
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
            return this.mExtras;
        }

        public static final class Builder {
            private android.os.LocaleList mDefaultLocales;
            private android.view.textclassifier.TextClassifier.EntityConfig mEntityConfig;
            private android.os.Bundle mExtras;
            private boolean mLegacyFallback = true;
            private java.time.ZonedDateTime mReferenceTime;
            private final java.lang.CharSequence mText;

            public Builder(java.lang.CharSequence charSequence) {
                this.mText = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence);
            }

            public android.view.textclassifier.TextLinks.Request.Builder setDefaultLocales(android.os.LocaleList localeList) {
                this.mDefaultLocales = localeList;
                return this;
            }

            public android.view.textclassifier.TextLinks.Request.Builder setEntityConfig(android.view.textclassifier.TextClassifier.EntityConfig entityConfig) {
                this.mEntityConfig = entityConfig;
                return this;
            }

            public android.view.textclassifier.TextLinks.Request.Builder setLegacyFallback(boolean z) {
                this.mLegacyFallback = z;
                return this;
            }

            public android.view.textclassifier.TextLinks.Request.Builder setExtras(android.os.Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public android.view.textclassifier.TextLinks.Request.Builder setReferenceTime(java.time.ZonedDateTime zonedDateTime) {
                this.mReferenceTime = zonedDateTime;
                return this;
            }

            public android.view.textclassifier.TextLinks.Request build() {
                return new android.view.textclassifier.TextLinks.Request(this.mText, this.mDefaultLocales, this.mEntityConfig, this.mLegacyFallback, this.mReferenceTime, this.mExtras == null ? android.os.Bundle.EMPTY : this.mExtras);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mText.toString());
            parcel.writeParcelable(this.mDefaultLocales, i);
            parcel.writeParcelable(this.mEntityConfig, i);
            parcel.writeBundle(this.mExtras);
            parcel.writeString(this.mReferenceTime == null ? null : this.mReferenceTime.toString());
            parcel.writeParcelable(this.mSystemTcMetadata, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.view.textclassifier.TextLinks.Request readFromParcel(android.os.Parcel parcel) {
            java.lang.String readString = parcel.readString();
            android.os.LocaleList localeList = (android.os.LocaleList) parcel.readParcelable(null, android.os.LocaleList.class);
            android.view.textclassifier.TextClassifier.EntityConfig entityConfig = (android.view.textclassifier.TextClassifier.EntityConfig) parcel.readParcelable(null, android.view.textclassifier.TextClassifier.EntityConfig.class);
            android.os.Bundle readBundle = parcel.readBundle();
            java.lang.String readString2 = parcel.readString();
            java.time.ZonedDateTime parse = readString2 == null ? null : java.time.ZonedDateTime.parse(readString2);
            android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata = (android.view.textclassifier.SystemTextClassifierMetadata) parcel.readParcelable(null, android.view.textclassifier.SystemTextClassifierMetadata.class);
            android.view.textclassifier.TextLinks.Request request = new android.view.textclassifier.TextLinks.Request(readString, localeList, entityConfig, true, parse, readBundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }
    }

    public static class TextLinkSpan extends android.text.style.ClickableSpan {
        public static final int INVOCATION_METHOD_KEYBOARD = 1;
        public static final int INVOCATION_METHOD_TOUCH = 0;
        public static final int INVOCATION_METHOD_UNSPECIFIED = -1;
        private final android.view.textclassifier.TextLinks.TextLink mTextLink;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface InvocationMethod {
        }

        public TextLinkSpan(android.view.textclassifier.TextLinks.TextLink textLink) {
            this.mTextLink = textLink;
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(android.view.View view) {
            onClick(view, -1);
        }

        public final void onClick(android.view.View view, int i) {
            if (view instanceof android.widget.TextView) {
                android.widget.TextView textView = (android.widget.TextView) view;
                if (android.view.textclassifier.TextClassificationManager.getSettings(textView.getContext()).isSmartLinkifyEnabled()) {
                    switch (i) {
                        case 0:
                            textView.requestActionMode(this);
                            break;
                        default:
                            textView.handleClick(this);
                            break;
                    }
                }
                if (this.mTextLink.mUrlSpan != null) {
                    this.mTextLink.mUrlSpan.onClick(textView);
                } else {
                    textView.handleClick(this);
                }
            }
        }

        public final android.view.textclassifier.TextLinks.TextLink getTextLink() {
            return this.mTextLink;
        }

        public final java.lang.String getUrl() {
            if (this.mTextLink.mUrlSpan != null) {
                return this.mTextLink.mUrlSpan.getURL();
            }
            return null;
        }
    }

    public static final class Builder {
        private android.os.Bundle mExtras;
        private final java.lang.String mFullText;
        private final java.util.ArrayList<android.view.textclassifier.TextLinks.TextLink> mLinks = new java.util.ArrayList<>();

        public Builder(java.lang.String str) {
            this.mFullText = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        public android.view.textclassifier.TextLinks.Builder addLink(int i, int i2, java.util.Map<java.lang.String, java.lang.Float> map) {
            return addLink(i, i2, map, android.os.Bundle.EMPTY, null);
        }

        public android.view.textclassifier.TextLinks.Builder addLink(int i, int i2, java.util.Map<java.lang.String, java.lang.Float> map, android.os.Bundle bundle) {
            return addLink(i, i2, map, bundle, null);
        }

        android.view.textclassifier.TextLinks.Builder addLink(int i, int i2, java.util.Map<java.lang.String, java.lang.Float> map, android.text.style.URLSpan uRLSpan) {
            return addLink(i, i2, map, android.os.Bundle.EMPTY, uRLSpan);
        }

        private android.view.textclassifier.TextLinks.Builder addLink(int i, int i2, java.util.Map<java.lang.String, java.lang.Float> map, android.os.Bundle bundle, android.text.style.URLSpan uRLSpan) {
            this.mLinks.add(new android.view.textclassifier.TextLinks.TextLink(i, i2, new android.view.textclassifier.EntityConfidence(map), bundle, uRLSpan));
            return this;
        }

        public android.view.textclassifier.TextLinks.Builder clearTextLinks() {
            this.mLinks.clear();
            return this;
        }

        public android.view.textclassifier.TextLinks.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.view.textclassifier.TextLinks build() {
            return new android.view.textclassifier.TextLinks(this.mFullText, this.mLinks, this.mExtras == null ? android.os.Bundle.EMPTY : this.mExtras);
        }
    }
}
