package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class Text implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.Text> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.Text>() { // from class: android.app.smartspace.uitemplatedata.Text.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.Text createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.Text(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.Text[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.Text[i];
        }
    };
    private final int mMaxLines;
    private final java.lang.CharSequence mText;
    private final android.text.TextUtils.TruncateAt mTruncateAtType;

    Text(android.os.Parcel parcel) {
        this.mText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mTruncateAtType = android.text.TextUtils.TruncateAt.valueOf(parcel.readString());
        this.mMaxLines = parcel.readInt();
    }

    private Text(java.lang.CharSequence charSequence, android.text.TextUtils.TruncateAt truncateAt, int i) {
        this.mText = charSequence;
        this.mTruncateAtType = truncateAt;
        this.mMaxLines = i;
    }

    public java.lang.CharSequence getText() {
        return this.mText;
    }

    public android.text.TextUtils.TruncateAt getTruncateAtType() {
        return this.mTruncateAtType;
    }

    public int getMaxLines() {
        return this.mMaxLines;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.smartspace.uitemplatedata.Text)) {
            return false;
        }
        android.app.smartspace.uitemplatedata.Text text = (android.app.smartspace.uitemplatedata.Text) obj;
        return this.mTruncateAtType == text.mTruncateAtType && android.app.smartspace.SmartspaceUtils.isEqual(this.mText, text.mText) && this.mMaxLines == text.mMaxLines;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mText, this.mTruncateAtType, java.lang.Integer.valueOf(this.mMaxLines));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.text.TextUtils.writeToParcel(this.mText, parcel, i);
        parcel.writeString(this.mTruncateAtType.name());
        parcel.writeInt(this.mMaxLines);
    }

    public java.lang.String toString() {
        return "Text{mText=" + ((java.lang.Object) this.mText) + ", mTruncateAtType=" + this.mTruncateAtType + ", mMaxLines=" + this.mMaxLines + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private final java.lang.CharSequence mText;
        private android.text.TextUtils.TruncateAt mTruncateAtType = android.text.TextUtils.TruncateAt.END;
        private int mMaxLines = 1;

        public Builder(java.lang.CharSequence charSequence) {
            this.mText = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence);
        }

        public android.app.smartspace.uitemplatedata.Text.Builder setTruncateAtType(android.text.TextUtils.TruncateAt truncateAt) {
            this.mTruncateAtType = (android.text.TextUtils.TruncateAt) java.util.Objects.requireNonNull(truncateAt);
            return this;
        }

        public android.app.smartspace.uitemplatedata.Text.Builder setMaxLines(int i) {
            this.mMaxLines = i;
            return this;
        }

        public android.app.smartspace.uitemplatedata.Text build() {
            return new android.app.smartspace.uitemplatedata.Text(this.mText, this.mTruncateAtType, this.mMaxLines);
        }
    }
}
