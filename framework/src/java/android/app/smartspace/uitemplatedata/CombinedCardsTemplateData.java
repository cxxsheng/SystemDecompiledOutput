package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CombinedCardsTemplateData extends android.app.smartspace.uitemplatedata.BaseTemplateData {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.CombinedCardsTemplateData> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.CombinedCardsTemplateData>() { // from class: android.app.smartspace.uitemplatedata.CombinedCardsTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.CombinedCardsTemplateData createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.CombinedCardsTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.CombinedCardsTemplateData[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.CombinedCardsTemplateData[i];
        }
    };
    private final java.util.List<android.app.smartspace.uitemplatedata.BaseTemplateData> mCombinedCardDataList;

    CombinedCardsTemplateData(android.os.Parcel parcel) {
        super(parcel);
        this.mCombinedCardDataList = parcel.createTypedArrayList(android.app.smartspace.uitemplatedata.BaseTemplateData.CREATOR);
    }

    private CombinedCardsTemplateData(int i, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo2, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo3, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo4, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo5, int i2, java.util.List<android.app.smartspace.uitemplatedata.BaseTemplateData> list) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mCombinedCardDataList = list;
    }

    public java.util.List<android.app.smartspace.uitemplatedata.BaseTemplateData> getCombinedCardDataList() {
        return this.mCombinedCardDataList;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mCombinedCardDataList);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof android.app.smartspace.uitemplatedata.CombinedCardsTemplateData) && super.equals(obj)) {
            return this.mCombinedCardDataList.equals(((android.app.smartspace.uitemplatedata.CombinedCardsTemplateData) obj).mCombinedCardDataList);
        }
        return false;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mCombinedCardDataList);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public java.lang.String toString() {
        return super.toString() + " + SmartspaceCombinedCardsUiTemplateData{mCombinedCardDataList=" + this.mCombinedCardDataList + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder extends android.app.smartspace.uitemplatedata.BaseTemplateData.Builder {
        private final java.util.List<android.app.smartspace.uitemplatedata.BaseTemplateData> mCombinedCardDataList;

        public Builder(java.util.List<android.app.smartspace.uitemplatedata.BaseTemplateData> list) {
            super(6);
            this.mCombinedCardDataList = (java.util.List) java.util.Objects.requireNonNull(list);
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public android.app.smartspace.uitemplatedata.CombinedCardsTemplateData build() {
            if (this.mCombinedCardDataList == null) {
                throw new java.lang.IllegalStateException("Please assign a value to all @NonNull args.");
            }
            return new android.app.smartspace.uitemplatedata.CombinedCardsTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mCombinedCardDataList);
        }
    }
}
