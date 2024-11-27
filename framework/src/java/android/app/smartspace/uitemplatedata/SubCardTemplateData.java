package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SubCardTemplateData extends android.app.smartspace.uitemplatedata.BaseTemplateData {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.SubCardTemplateData> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.SubCardTemplateData>() { // from class: android.app.smartspace.uitemplatedata.SubCardTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.SubCardTemplateData createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.SubCardTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.SubCardTemplateData[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.SubCardTemplateData[i];
        }
    };
    private final android.app.smartspace.uitemplatedata.TapAction mSubCardAction;
    private final android.app.smartspace.uitemplatedata.Icon mSubCardIcon;
    private final android.app.smartspace.uitemplatedata.Text mSubCardText;

    SubCardTemplateData(android.os.Parcel parcel) {
        super(parcel);
        this.mSubCardIcon = (android.app.smartspace.uitemplatedata.Icon) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Icon.CREATOR);
        this.mSubCardText = (android.app.smartspace.uitemplatedata.Text) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Text.CREATOR);
        this.mSubCardAction = (android.app.smartspace.uitemplatedata.TapAction) parcel.readTypedObject(android.app.smartspace.uitemplatedata.TapAction.CREATOR);
    }

    private SubCardTemplateData(int i, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo2, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo3, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo4, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo5, int i2, android.app.smartspace.uitemplatedata.Icon icon, android.app.smartspace.uitemplatedata.Text text, android.app.smartspace.uitemplatedata.TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mSubCardIcon = icon;
        this.mSubCardText = text;
        this.mSubCardAction = tapAction;
    }

    public android.app.smartspace.uitemplatedata.Icon getSubCardIcon() {
        return this.mSubCardIcon;
    }

    public android.app.smartspace.uitemplatedata.Text getSubCardText() {
        return this.mSubCardText;
    }

    public android.app.smartspace.uitemplatedata.TapAction getSubCardAction() {
        return this.mSubCardAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mSubCardIcon, i);
        parcel.writeTypedObject(this.mSubCardText, i);
        parcel.writeTypedObject(this.mSubCardAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.smartspace.uitemplatedata.SubCardTemplateData) || !super.equals(obj)) {
            return false;
        }
        android.app.smartspace.uitemplatedata.SubCardTemplateData subCardTemplateData = (android.app.smartspace.uitemplatedata.SubCardTemplateData) obj;
        return this.mSubCardIcon.equals(subCardTemplateData.mSubCardIcon) && android.app.smartspace.SmartspaceUtils.isEqual(this.mSubCardText, subCardTemplateData.mSubCardText) && java.util.Objects.equals(this.mSubCardAction, subCardTemplateData.mSubCardAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mSubCardIcon, this.mSubCardText, this.mSubCardAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public java.lang.String toString() {
        return super.toString() + " + SmartspaceSubCardUiTemplateData{mSubCardIcon=" + this.mSubCardIcon + ", mSubCardText=" + this.mSubCardText + ", mSubCardAction=" + this.mSubCardAction + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder extends android.app.smartspace.uitemplatedata.BaseTemplateData.Builder {
        private android.app.smartspace.uitemplatedata.TapAction mSubCardAction;
        private final android.app.smartspace.uitemplatedata.Icon mSubCardIcon;
        private android.app.smartspace.uitemplatedata.Text mSubCardText;

        public Builder(android.app.smartspace.uitemplatedata.Icon icon) {
            super(7);
            this.mSubCardIcon = (android.app.smartspace.uitemplatedata.Icon) java.util.Objects.requireNonNull(icon);
        }

        public android.app.smartspace.uitemplatedata.SubCardTemplateData.Builder setSubCardText(android.app.smartspace.uitemplatedata.Text text) {
            this.mSubCardText = text;
            return this;
        }

        public android.app.smartspace.uitemplatedata.SubCardTemplateData.Builder setSubCardAction(android.app.smartspace.uitemplatedata.TapAction tapAction) {
            this.mSubCardAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public android.app.smartspace.uitemplatedata.SubCardTemplateData build() {
            return new android.app.smartspace.uitemplatedata.SubCardTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mSubCardIcon, this.mSubCardText, this.mSubCardAction);
        }
    }
}
