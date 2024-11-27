package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class TextClassificationContext implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassificationContext> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassificationContext>() { // from class: android.view.textclassifier.TextClassificationContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextClassificationContext createFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.TextClassificationContext(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextClassificationContext[] newArray(int i) {
            return new android.view.textclassifier.TextClassificationContext[i];
        }
    };
    private java.lang.String mPackageName;
    private android.view.textclassifier.SystemTextClassifierMetadata mSystemTcMetadata;
    private final java.lang.String mWidgetType;
    private final java.lang.String mWidgetVersion;

    private TextClassificationContext(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mWidgetType = (java.lang.String) java.util.Objects.requireNonNull(str2);
        this.mWidgetVersion = str3;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    void setSystemTextClassifierMetadata(android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata) {
        this.mSystemTcMetadata = systemTextClassifierMetadata;
    }

    public android.view.textclassifier.SystemTextClassifierMetadata getSystemTextClassifierMetadata() {
        return this.mSystemTcMetadata;
    }

    public java.lang.String getWidgetType() {
        return this.mWidgetType;
    }

    public java.lang.String getWidgetVersion() {
        return this.mWidgetVersion;
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "TextClassificationContext{packageName=%s, widgetType=%s, widgetVersion=%s, systemTcMetadata=%s}", this.mPackageName, this.mWidgetType, this.mWidgetVersion, this.mSystemTcMetadata);
    }

    public static final class Builder {
        private final java.lang.String mPackageName;
        private final java.lang.String mWidgetType;
        private java.lang.String mWidgetVersion;

        public Builder(java.lang.String str, java.lang.String str2) {
            this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mWidgetType = (java.lang.String) java.util.Objects.requireNonNull(str2);
        }

        public android.view.textclassifier.TextClassificationContext.Builder setWidgetVersion(java.lang.String str) {
            this.mWidgetVersion = str;
            return this;
        }

        public android.view.textclassifier.TextClassificationContext build() {
            return new android.view.textclassifier.TextClassificationContext(this.mPackageName, this.mWidgetType, this.mWidgetVersion);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mWidgetType);
        parcel.writeString(this.mWidgetVersion);
        parcel.writeParcelable(this.mSystemTcMetadata, i);
    }

    private TextClassificationContext(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mWidgetType = parcel.readString();
        this.mWidgetVersion = parcel.readString();
        this.mSystemTcMetadata = (android.view.textclassifier.SystemTextClassifierMetadata) parcel.readParcelable(null, android.view.textclassifier.SystemTextClassifierMetadata.class);
    }
}
