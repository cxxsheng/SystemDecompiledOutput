package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class HeadToHeadTemplateData extends android.app.smartspace.uitemplatedata.BaseTemplateData {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.HeadToHeadTemplateData> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.HeadToHeadTemplateData>() { // from class: android.app.smartspace.uitemplatedata.HeadToHeadTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.HeadToHeadTemplateData createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.HeadToHeadTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.HeadToHeadTemplateData[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.HeadToHeadTemplateData[i];
        }
    };
    private final android.app.smartspace.uitemplatedata.TapAction mHeadToHeadAction;
    private final android.app.smartspace.uitemplatedata.Icon mHeadToHeadFirstCompetitorIcon;
    private final android.app.smartspace.uitemplatedata.Text mHeadToHeadFirstCompetitorText;
    private final android.app.smartspace.uitemplatedata.Icon mHeadToHeadSecondCompetitorIcon;
    private final android.app.smartspace.uitemplatedata.Text mHeadToHeadSecondCompetitorText;
    private final android.app.smartspace.uitemplatedata.Text mHeadToHeadTitle;

    HeadToHeadTemplateData(android.os.Parcel parcel) {
        super(parcel);
        this.mHeadToHeadTitle = (android.app.smartspace.uitemplatedata.Text) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Text.CREATOR);
        this.mHeadToHeadFirstCompetitorIcon = (android.app.smartspace.uitemplatedata.Icon) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Icon.CREATOR);
        this.mHeadToHeadSecondCompetitorIcon = (android.app.smartspace.uitemplatedata.Icon) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Icon.CREATOR);
        this.mHeadToHeadFirstCompetitorText = (android.app.smartspace.uitemplatedata.Text) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Text.CREATOR);
        this.mHeadToHeadSecondCompetitorText = (android.app.smartspace.uitemplatedata.Text) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Text.CREATOR);
        this.mHeadToHeadAction = (android.app.smartspace.uitemplatedata.TapAction) parcel.readTypedObject(android.app.smartspace.uitemplatedata.TapAction.CREATOR);
    }

    private HeadToHeadTemplateData(int i, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo2, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo3, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo4, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo5, int i2, android.app.smartspace.uitemplatedata.Text text, android.app.smartspace.uitemplatedata.Icon icon, android.app.smartspace.uitemplatedata.Icon icon2, android.app.smartspace.uitemplatedata.Text text2, android.app.smartspace.uitemplatedata.Text text3, android.app.smartspace.uitemplatedata.TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mHeadToHeadTitle = text;
        this.mHeadToHeadFirstCompetitorIcon = icon;
        this.mHeadToHeadSecondCompetitorIcon = icon2;
        this.mHeadToHeadFirstCompetitorText = text2;
        this.mHeadToHeadSecondCompetitorText = text3;
        this.mHeadToHeadAction = tapAction;
    }

    public android.app.smartspace.uitemplatedata.Text getHeadToHeadTitle() {
        return this.mHeadToHeadTitle;
    }

    public android.app.smartspace.uitemplatedata.Icon getHeadToHeadFirstCompetitorIcon() {
        return this.mHeadToHeadFirstCompetitorIcon;
    }

    public android.app.smartspace.uitemplatedata.Icon getHeadToHeadSecondCompetitorIcon() {
        return this.mHeadToHeadSecondCompetitorIcon;
    }

    public android.app.smartspace.uitemplatedata.Text getHeadToHeadFirstCompetitorText() {
        return this.mHeadToHeadFirstCompetitorText;
    }

    public android.app.smartspace.uitemplatedata.Text getHeadToHeadSecondCompetitorText() {
        return this.mHeadToHeadSecondCompetitorText;
    }

    public android.app.smartspace.uitemplatedata.TapAction getHeadToHeadAction() {
        return this.mHeadToHeadAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mHeadToHeadTitle, i);
        parcel.writeTypedObject(this.mHeadToHeadFirstCompetitorIcon, i);
        parcel.writeTypedObject(this.mHeadToHeadSecondCompetitorIcon, i);
        parcel.writeTypedObject(this.mHeadToHeadFirstCompetitorText, i);
        parcel.writeTypedObject(this.mHeadToHeadSecondCompetitorText, i);
        parcel.writeTypedObject(this.mHeadToHeadAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.smartspace.uitemplatedata.HeadToHeadTemplateData) || !super.equals(obj)) {
            return false;
        }
        android.app.smartspace.uitemplatedata.HeadToHeadTemplateData headToHeadTemplateData = (android.app.smartspace.uitemplatedata.HeadToHeadTemplateData) obj;
        return android.app.smartspace.SmartspaceUtils.isEqual(this.mHeadToHeadTitle, headToHeadTemplateData.mHeadToHeadTitle) && java.util.Objects.equals(this.mHeadToHeadFirstCompetitorIcon, headToHeadTemplateData.mHeadToHeadFirstCompetitorIcon) && java.util.Objects.equals(this.mHeadToHeadSecondCompetitorIcon, headToHeadTemplateData.mHeadToHeadSecondCompetitorIcon) && android.app.smartspace.SmartspaceUtils.isEqual(this.mHeadToHeadFirstCompetitorText, headToHeadTemplateData.mHeadToHeadFirstCompetitorText) && android.app.smartspace.SmartspaceUtils.isEqual(this.mHeadToHeadSecondCompetitorText, headToHeadTemplateData.mHeadToHeadSecondCompetitorText) && java.util.Objects.equals(this.mHeadToHeadAction, headToHeadTemplateData.mHeadToHeadAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mHeadToHeadTitle, this.mHeadToHeadFirstCompetitorIcon, this.mHeadToHeadSecondCompetitorIcon, this.mHeadToHeadFirstCompetitorText, this.mHeadToHeadSecondCompetitorText, this.mHeadToHeadAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public java.lang.String toString() {
        return super.toString() + " + SmartspaceHeadToHeadUiTemplateData{mH2HTitle=" + this.mHeadToHeadTitle + ", mH2HFirstCompetitorIcon=" + this.mHeadToHeadFirstCompetitorIcon + ", mH2HSecondCompetitorIcon=" + this.mHeadToHeadSecondCompetitorIcon + ", mH2HFirstCompetitorText=" + this.mHeadToHeadFirstCompetitorText + ", mH2HSecondCompetitorText=" + this.mHeadToHeadSecondCompetitorText + ", mH2HAction=" + this.mHeadToHeadAction + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder extends android.app.smartspace.uitemplatedata.BaseTemplateData.Builder {
        private android.app.smartspace.uitemplatedata.TapAction mHeadToHeadAction;
        private android.app.smartspace.uitemplatedata.Icon mHeadToHeadFirstCompetitorIcon;
        private android.app.smartspace.uitemplatedata.Text mHeadToHeadFirstCompetitorText;
        private android.app.smartspace.uitemplatedata.Icon mHeadToHeadSecondCompetitorIcon;
        private android.app.smartspace.uitemplatedata.Text mHeadToHeadSecondCompetitorText;
        private android.app.smartspace.uitemplatedata.Text mHeadToHeadTitle;

        public Builder() {
            super(5);
        }

        public android.app.smartspace.uitemplatedata.HeadToHeadTemplateData.Builder setHeadToHeadTitle(android.app.smartspace.uitemplatedata.Text text) {
            this.mHeadToHeadTitle = text;
            return this;
        }

        public android.app.smartspace.uitemplatedata.HeadToHeadTemplateData.Builder setHeadToHeadFirstCompetitorIcon(android.app.smartspace.uitemplatedata.Icon icon) {
            this.mHeadToHeadFirstCompetitorIcon = icon;
            return this;
        }

        public android.app.smartspace.uitemplatedata.HeadToHeadTemplateData.Builder setHeadToHeadSecondCompetitorIcon(android.app.smartspace.uitemplatedata.Icon icon) {
            this.mHeadToHeadSecondCompetitorIcon = icon;
            return this;
        }

        public android.app.smartspace.uitemplatedata.HeadToHeadTemplateData.Builder setHeadToHeadFirstCompetitorText(android.app.smartspace.uitemplatedata.Text text) {
            this.mHeadToHeadFirstCompetitorText = text;
            return this;
        }

        public android.app.smartspace.uitemplatedata.HeadToHeadTemplateData.Builder setHeadToHeadSecondCompetitorText(android.app.smartspace.uitemplatedata.Text text) {
            this.mHeadToHeadSecondCompetitorText = text;
            return this;
        }

        public android.app.smartspace.uitemplatedata.HeadToHeadTemplateData.Builder setHeadToHeadAction(android.app.smartspace.uitemplatedata.TapAction tapAction) {
            this.mHeadToHeadAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public android.app.smartspace.uitemplatedata.HeadToHeadTemplateData build() {
            return new android.app.smartspace.uitemplatedata.HeadToHeadTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mHeadToHeadTitle, this.mHeadToHeadFirstCompetitorIcon, this.mHeadToHeadSecondCompetitorIcon, this.mHeadToHeadFirstCompetitorText, this.mHeadToHeadSecondCompetitorText, this.mHeadToHeadAction);
        }
    }
}
