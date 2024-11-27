package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class TextSelection implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textclassifier.TextSelection> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextSelection>() { // from class: android.view.textclassifier.TextSelection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextSelection createFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.TextSelection(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextSelection[] newArray(int i) {
            return new android.view.textclassifier.TextSelection[i];
        }
    };
    private final int mEndIndex;
    private final android.view.textclassifier.EntityConfidence mEntityConfidence;
    private final android.os.Bundle mExtras;
    private final java.lang.String mId;
    private final int mStartIndex;
    private final android.view.textclassifier.TextClassification mTextClassification;

    private TextSelection(int i, int i2, java.util.Map<java.lang.String, java.lang.Float> map, java.lang.String str, android.view.textclassifier.TextClassification textClassification, android.os.Bundle bundle) {
        this.mStartIndex = i;
        this.mEndIndex = i2;
        this.mEntityConfidence = new android.view.textclassifier.EntityConfidence(map);
        this.mId = str;
        this.mTextClassification = textClassification;
        this.mExtras = bundle;
    }

    public int getSelectionStartIndex() {
        return this.mStartIndex;
    }

    public int getSelectionEndIndex() {
        return this.mEndIndex;
    }

    public int getEntityCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public java.lang.String getEntity(int i) {
        return this.mEntityConfidence.getEntities().get(i);
    }

    public float getConfidenceScore(java.lang.String str) {
        return this.mEntityConfidence.getConfidenceScore(str);
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public android.view.textclassifier.TextClassification getTextClassification() {
        return this.mTextClassification;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.view.textclassifier.TextSelection.Builder toBuilder() {
        return new android.view.textclassifier.TextSelection.Builder(this.mStartIndex, this.mEndIndex).setId(this.mId).setEntityConfidence(this.mEntityConfidence).setTextClassification(this.mTextClassification).setExtras(this.mExtras);
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "TextSelection {id=%s, startIndex=%d, endIndex=%d, entities=%s}", this.mId, java.lang.Integer.valueOf(this.mStartIndex), java.lang.Integer.valueOf(this.mEndIndex), this.mEntityConfidence);
    }

    public static final class Builder {
        private final int mEndIndex;
        private final java.util.Map<java.lang.String, java.lang.Float> mEntityConfidence = new android.util.ArrayMap();
        private android.os.Bundle mExtras;
        private java.lang.String mId;
        private final int mStartIndex;
        private android.view.textclassifier.TextClassification mTextClassification;

        public Builder(int i, int i2) {
            com.android.internal.util.Preconditions.checkArgument(i >= 0);
            com.android.internal.util.Preconditions.checkArgument(i2 > i);
            this.mStartIndex = i;
            this.mEndIndex = i2;
        }

        public android.view.textclassifier.TextSelection.Builder setEntityType(java.lang.String str, float f) {
            java.util.Objects.requireNonNull(str);
            this.mEntityConfidence.put(str, java.lang.Float.valueOf(f));
            return this;
        }

        android.view.textclassifier.TextSelection.Builder setEntityConfidence(android.view.textclassifier.EntityConfidence entityConfidence) {
            this.mEntityConfidence.clear();
            this.mEntityConfidence.putAll(entityConfidence.toMap());
            return this;
        }

        public android.view.textclassifier.TextSelection.Builder setId(java.lang.String str) {
            this.mId = str;
            return this;
        }

        public android.view.textclassifier.TextSelection.Builder setTextClassification(android.view.textclassifier.TextClassification textClassification) {
            this.mTextClassification = textClassification;
            return this;
        }

        public android.view.textclassifier.TextSelection.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.view.textclassifier.TextSelection build() {
            return new android.view.textclassifier.TextSelection(this.mStartIndex, this.mEndIndex, this.mEntityConfidence, this.mId, this.mTextClassification, this.mExtras == null ? android.os.Bundle.EMPTY : this.mExtras);
        }
    }

    public static final class Request implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextSelection.Request> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextSelection.Request>() { // from class: android.view.textclassifier.TextSelection.Request.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextSelection.Request createFromParcel(android.os.Parcel parcel) {
                return android.view.textclassifier.TextSelection.Request.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextSelection.Request[] newArray(int i) {
                return new android.view.textclassifier.TextSelection.Request[i];
            }
        };
        private final boolean mDarkLaunchAllowed;
        private final android.os.LocaleList mDefaultLocales;
        private final int mEndIndex;
        private final android.os.Bundle mExtras;
        private final boolean mIncludeTextClassification;
        private final int mStartIndex;
        private android.view.textclassifier.SystemTextClassifierMetadata mSystemTcMetadata;
        private final java.lang.CharSequence mText;

        private Request(java.lang.CharSequence charSequence, int i, int i2, android.os.LocaleList localeList, boolean z, boolean z2, android.os.Bundle bundle) {
            this.mText = charSequence;
            this.mStartIndex = i;
            this.mEndIndex = i2;
            this.mDefaultLocales = localeList;
            this.mDarkLaunchAllowed = z;
            this.mIncludeTextClassification = z2;
            this.mExtras = bundle;
        }

        public java.lang.CharSequence getText() {
            return this.mText;
        }

        public int getStartIndex() {
            return this.mStartIndex;
        }

        public int getEndIndex() {
            return this.mEndIndex;
        }

        public boolean isDarkLaunchAllowed() {
            return this.mDarkLaunchAllowed;
        }

        public android.os.LocaleList getDefaultLocales() {
            return this.mDefaultLocales;
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

        public boolean shouldIncludeTextClassification() {
            return this.mIncludeTextClassification;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private boolean mDarkLaunchAllowed;
            private android.os.LocaleList mDefaultLocales;
            private final int mEndIndex;
            private android.os.Bundle mExtras;
            private boolean mIncludeTextClassification;
            private final int mStartIndex;
            private final java.lang.CharSequence mText;

            public Builder(java.lang.CharSequence charSequence, int i, int i2) {
                android.view.textclassifier.TextClassifier.Utils.checkArgument(charSequence, i, i2);
                this.mText = charSequence;
                this.mStartIndex = i;
                this.mEndIndex = i2;
            }

            public android.view.textclassifier.TextSelection.Request.Builder setDefaultLocales(android.os.LocaleList localeList) {
                this.mDefaultLocales = localeList;
                return this;
            }

            public android.view.textclassifier.TextSelection.Request.Builder setDarkLaunchAllowed(boolean z) {
                this.mDarkLaunchAllowed = z;
                return this;
            }

            public android.view.textclassifier.TextSelection.Request.Builder setIncludeTextClassification(boolean z) {
                this.mIncludeTextClassification = z;
                return this;
            }

            public android.view.textclassifier.TextSelection.Request.Builder setExtras(android.os.Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public android.view.textclassifier.TextSelection.Request build() {
                return new android.view.textclassifier.TextSelection.Request(new android.text.SpannedString(this.mText), this.mStartIndex, this.mEndIndex, this.mDefaultLocales, this.mDarkLaunchAllowed, this.mIncludeTextClassification, this.mExtras == null ? android.os.Bundle.EMPTY : this.mExtras);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeCharSequence(this.mText);
            parcel.writeInt(this.mStartIndex);
            parcel.writeInt(this.mEndIndex);
            parcel.writeParcelable(this.mDefaultLocales, i);
            parcel.writeBundle(this.mExtras);
            parcel.writeParcelable(this.mSystemTcMetadata, i);
            parcel.writeBoolean(this.mIncludeTextClassification);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.view.textclassifier.TextSelection.Request readFromParcel(android.os.Parcel parcel) {
            java.lang.CharSequence readCharSequence = parcel.readCharSequence();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.os.LocaleList localeList = (android.os.LocaleList) parcel.readParcelable(null, android.os.LocaleList.class);
            android.os.Bundle readBundle = parcel.readBundle();
            android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata = (android.view.textclassifier.SystemTextClassifierMetadata) parcel.readParcelable(null, android.view.textclassifier.SystemTextClassifierMetadata.class);
            android.view.textclassifier.TextSelection.Request request = new android.view.textclassifier.TextSelection.Request(readCharSequence, readInt, readInt2, localeList, false, parcel.readBoolean(), readBundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStartIndex);
        parcel.writeInt(this.mEndIndex);
        this.mEntityConfidence.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeBundle(this.mExtras);
        parcel.writeParcelable(this.mTextClassification, i);
    }

    private TextSelection(android.os.Parcel parcel) {
        this.mStartIndex = parcel.readInt();
        this.mEndIndex = parcel.readInt();
        this.mEntityConfidence = android.view.textclassifier.EntityConfidence.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mExtras = parcel.readBundle();
        this.mTextClassification = (android.view.textclassifier.TextClassification) parcel.readParcelable(android.view.textclassifier.TextClassification.class.getClassLoader(), android.view.textclassifier.TextClassification.class);
    }
}
