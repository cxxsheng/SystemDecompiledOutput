package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SubImageTemplateData extends android.app.smartspace.uitemplatedata.BaseTemplateData {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.SubImageTemplateData> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.SubImageTemplateData>() { // from class: android.app.smartspace.uitemplatedata.SubImageTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.SubImageTemplateData createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.SubImageTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.SubImageTemplateData[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.SubImageTemplateData[i];
        }
    };
    private final android.app.smartspace.uitemplatedata.TapAction mSubImageAction;
    private final java.util.List<android.app.smartspace.uitemplatedata.Text> mSubImageTexts;
    private final java.util.List<android.app.smartspace.uitemplatedata.Icon> mSubImages;

    SubImageTemplateData(android.os.Parcel parcel) {
        super(parcel);
        this.mSubImageTexts = parcel.createTypedArrayList(android.app.smartspace.uitemplatedata.Text.CREATOR);
        this.mSubImages = parcel.createTypedArrayList(android.app.smartspace.uitemplatedata.Icon.CREATOR);
        this.mSubImageAction = (android.app.smartspace.uitemplatedata.TapAction) parcel.readTypedObject(android.app.smartspace.uitemplatedata.TapAction.CREATOR);
    }

    private SubImageTemplateData(int i, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo2, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo3, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo4, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo5, int i2, java.util.List<android.app.smartspace.uitemplatedata.Text> list, java.util.List<android.app.smartspace.uitemplatedata.Icon> list2, android.app.smartspace.uitemplatedata.TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mSubImageTexts = list;
        this.mSubImages = list2;
        this.mSubImageAction = tapAction;
    }

    public java.util.List<android.app.smartspace.uitemplatedata.Text> getSubImageTexts() {
        return this.mSubImageTexts;
    }

    public java.util.List<android.app.smartspace.uitemplatedata.Icon> getSubImages() {
        return this.mSubImages;
    }

    public android.app.smartspace.uitemplatedata.TapAction getSubImageAction() {
        return this.mSubImageAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mSubImageTexts);
        parcel.writeTypedList(this.mSubImages);
        parcel.writeTypedObject(this.mSubImageAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.smartspace.uitemplatedata.SubImageTemplateData) || !super.equals(obj)) {
            return false;
        }
        android.app.smartspace.uitemplatedata.SubImageTemplateData subImageTemplateData = (android.app.smartspace.uitemplatedata.SubImageTemplateData) obj;
        return java.util.Objects.equals(this.mSubImageTexts, subImageTemplateData.mSubImageTexts) && java.util.Objects.equals(this.mSubImages, subImageTemplateData.mSubImages) && java.util.Objects.equals(this.mSubImageAction, subImageTemplateData.mSubImageAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mSubImageTexts, this.mSubImages, this.mSubImageAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public java.lang.String toString() {
        return super.toString() + " + SmartspaceSubImageUiTemplateData{mSubImageTexts=" + this.mSubImageTexts + ", mSubImages=" + this.mSubImages + ", mSubImageAction=" + this.mSubImageAction + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder extends android.app.smartspace.uitemplatedata.BaseTemplateData.Builder {
        private android.app.smartspace.uitemplatedata.TapAction mSubImageAction;
        private final java.util.List<android.app.smartspace.uitemplatedata.Text> mSubImageTexts;
        private final java.util.List<android.app.smartspace.uitemplatedata.Icon> mSubImages;

        public Builder(java.util.List<android.app.smartspace.uitemplatedata.Text> list, java.util.List<android.app.smartspace.uitemplatedata.Icon> list2) {
            super(2);
            this.mSubImageTexts = (java.util.List) java.util.Objects.requireNonNull(list);
            this.mSubImages = (java.util.List) java.util.Objects.requireNonNull(list2);
        }

        public android.app.smartspace.uitemplatedata.SubImageTemplateData.Builder setSubImageAction(android.app.smartspace.uitemplatedata.TapAction tapAction) {
            this.mSubImageAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public android.app.smartspace.uitemplatedata.SubImageTemplateData build() {
            return new android.app.smartspace.uitemplatedata.SubImageTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mSubImageTexts, this.mSubImages, this.mSubImageAction);
        }
    }
}
