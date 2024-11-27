package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SubListTemplateData extends android.app.smartspace.uitemplatedata.BaseTemplateData {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.SubListTemplateData> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.SubListTemplateData>() { // from class: android.app.smartspace.uitemplatedata.SubListTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.SubListTemplateData createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.SubListTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.SubListTemplateData[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.SubListTemplateData[i];
        }
    };
    private final android.app.smartspace.uitemplatedata.TapAction mSubListAction;
    private final android.app.smartspace.uitemplatedata.Icon mSubListIcon;
    private final java.util.List<android.app.smartspace.uitemplatedata.Text> mSubListTexts;

    SubListTemplateData(android.os.Parcel parcel) {
        super(parcel);
        this.mSubListIcon = (android.app.smartspace.uitemplatedata.Icon) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Icon.CREATOR);
        this.mSubListTexts = parcel.createTypedArrayList(android.app.smartspace.uitemplatedata.Text.CREATOR);
        this.mSubListAction = (android.app.smartspace.uitemplatedata.TapAction) parcel.readTypedObject(android.app.smartspace.uitemplatedata.TapAction.CREATOR);
    }

    private SubListTemplateData(int i, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo2, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo3, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo4, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo5, int i2, android.app.smartspace.uitemplatedata.Icon icon, java.util.List<android.app.smartspace.uitemplatedata.Text> list, android.app.smartspace.uitemplatedata.TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mSubListIcon = icon;
        this.mSubListTexts = list;
        this.mSubListAction = tapAction;
    }

    public android.app.smartspace.uitemplatedata.Icon getSubListIcon() {
        return this.mSubListIcon;
    }

    public java.util.List<android.app.smartspace.uitemplatedata.Text> getSubListTexts() {
        return this.mSubListTexts;
    }

    public android.app.smartspace.uitemplatedata.TapAction getSubListAction() {
        return this.mSubListAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mSubListIcon, i);
        parcel.writeTypedList(this.mSubListTexts);
        parcel.writeTypedObject(this.mSubListAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.smartspace.uitemplatedata.SubListTemplateData) || !super.equals(obj)) {
            return false;
        }
        android.app.smartspace.uitemplatedata.SubListTemplateData subListTemplateData = (android.app.smartspace.uitemplatedata.SubListTemplateData) obj;
        return java.util.Objects.equals(this.mSubListIcon, subListTemplateData.mSubListIcon) && java.util.Objects.equals(this.mSubListTexts, subListTemplateData.mSubListTexts) && java.util.Objects.equals(this.mSubListAction, subListTemplateData.mSubListAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mSubListIcon, this.mSubListTexts, this.mSubListAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public java.lang.String toString() {
        return super.toString() + " + SmartspaceSubListUiTemplateData{mSubListIcon=" + this.mSubListIcon + ", mSubListTexts=" + this.mSubListTexts + ", mSubListAction=" + this.mSubListAction + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder extends android.app.smartspace.uitemplatedata.BaseTemplateData.Builder {
        private android.app.smartspace.uitemplatedata.TapAction mSubListAction;
        private android.app.smartspace.uitemplatedata.Icon mSubListIcon;
        private final java.util.List<android.app.smartspace.uitemplatedata.Text> mSubListTexts;

        public Builder(java.util.List<android.app.smartspace.uitemplatedata.Text> list) {
            super(3);
            this.mSubListTexts = (java.util.List) java.util.Objects.requireNonNull(list);
        }

        public android.app.smartspace.uitemplatedata.SubListTemplateData.Builder setSubListIcon(android.app.smartspace.uitemplatedata.Icon icon) {
            this.mSubListIcon = icon;
            return this;
        }

        public android.app.smartspace.uitemplatedata.SubListTemplateData.Builder setSubListAction(android.app.smartspace.uitemplatedata.TapAction tapAction) {
            this.mSubListAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public android.app.smartspace.uitemplatedata.SubListTemplateData build() {
            return new android.app.smartspace.uitemplatedata.SubListTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mSubListIcon, this.mSubListTexts, this.mSubListAction);
        }
    }
}
