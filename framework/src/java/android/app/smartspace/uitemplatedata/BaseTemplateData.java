package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class BaseTemplateData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.BaseTemplateData> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.BaseTemplateData>() { // from class: android.app.smartspace.uitemplatedata.BaseTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.BaseTemplateData createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.BaseTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.BaseTemplateData[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.BaseTemplateData[i];
        }
    };
    private final int mLayoutWeight;
    private final android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mPrimaryItem;
    private final android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mSubtitleItem;
    private final android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mSubtitleSupplementalItem;
    private final android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mSupplementalAlarmItem;
    private final android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mSupplementalLineItem;
    private final int mTemplateType;

    BaseTemplateData(android.os.Parcel parcel) {
        this.mTemplateType = parcel.readInt();
        this.mPrimaryItem = (android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo) parcel.readTypedObject(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.CREATOR);
        this.mSubtitleItem = (android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo) parcel.readTypedObject(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.CREATOR);
        this.mSubtitleSupplementalItem = (android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo) parcel.readTypedObject(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.CREATOR);
        this.mSupplementalLineItem = (android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo) parcel.readTypedObject(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.CREATOR);
        this.mSupplementalAlarmItem = (android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo) parcel.readTypedObject(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.CREATOR);
        this.mLayoutWeight = parcel.readInt();
    }

    BaseTemplateData(int i, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo2, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo3, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo4, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo5, int i2) {
        this.mTemplateType = i;
        this.mPrimaryItem = subItemInfo;
        this.mSubtitleItem = subItemInfo2;
        this.mSubtitleSupplementalItem = subItemInfo3;
        this.mSupplementalLineItem = subItemInfo4;
        this.mSupplementalAlarmItem = subItemInfo5;
        this.mLayoutWeight = i2;
    }

    public int getTemplateType() {
        return this.mTemplateType;
    }

    public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getPrimaryItem() {
        return this.mPrimaryItem;
    }

    public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getSubtitleItem() {
        return this.mSubtitleItem;
    }

    public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getSubtitleSupplementalItem() {
        return this.mSubtitleSupplementalItem;
    }

    public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getSupplementalLineItem() {
        return this.mSupplementalLineItem;
    }

    public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getSupplementalAlarmItem() {
        return this.mSupplementalAlarmItem;
    }

    public int getLayoutWeight() {
        return this.mLayoutWeight;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mTemplateType);
        parcel.writeTypedObject(this.mPrimaryItem, i);
        parcel.writeTypedObject(this.mSubtitleItem, i);
        parcel.writeTypedObject(this.mSubtitleSupplementalItem, i);
        parcel.writeTypedObject(this.mSupplementalLineItem, i);
        parcel.writeTypedObject(this.mSupplementalAlarmItem, i);
        parcel.writeInt(this.mLayoutWeight);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.smartspace.uitemplatedata.BaseTemplateData)) {
            return false;
        }
        android.app.smartspace.uitemplatedata.BaseTemplateData baseTemplateData = (android.app.smartspace.uitemplatedata.BaseTemplateData) obj;
        return this.mTemplateType == baseTemplateData.mTemplateType && this.mLayoutWeight == baseTemplateData.mLayoutWeight && java.util.Objects.equals(this.mPrimaryItem, baseTemplateData.mPrimaryItem) && java.util.Objects.equals(this.mSubtitleItem, baseTemplateData.mSubtitleItem) && java.util.Objects.equals(this.mSubtitleSupplementalItem, baseTemplateData.mSubtitleSupplementalItem) && java.util.Objects.equals(this.mSupplementalLineItem, baseTemplateData.mSupplementalLineItem) && java.util.Objects.equals(this.mSupplementalAlarmItem, baseTemplateData.mSupplementalAlarmItem);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mTemplateType), this.mPrimaryItem, this.mSubtitleItem, this.mSubtitleSupplementalItem, this.mSupplementalLineItem, this.mSupplementalAlarmItem, java.lang.Integer.valueOf(this.mLayoutWeight));
    }

    public java.lang.String toString() {
        return "BaseTemplateData{mTemplateType=" + this.mTemplateType + ", mPrimaryItem=" + this.mPrimaryItem + ", mSubtitleItem=" + this.mSubtitleItem + ", mSubtitleSupplementalItem=" + this.mSubtitleSupplementalItem + ", mSupplementalLineItem=" + this.mSupplementalLineItem + ", mSupplementalAlarmItem=" + this.mSupplementalAlarmItem + ", mLayoutWeight=" + this.mLayoutWeight + '}';
    }

    @android.annotation.SystemApi
    public static class Builder {
        private int mLayoutWeight = 0;
        private android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mPrimaryItem;
        private android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mSubtitleItem;
        private android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mSubtitleSupplementalItem;
        private android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mSupplementalAlarmItem;
        private android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo mSupplementalLineItem;
        private final int mTemplateType;

        public Builder(int i) {
            this.mTemplateType = i;
        }

        int getTemplateType() {
            return this.mTemplateType;
        }

        android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getPrimaryItem() {
            return this.mPrimaryItem;
        }

        android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getSubtitleItem() {
            return this.mSubtitleItem;
        }

        android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getSubtitleSupplemtnalItem() {
            return this.mSubtitleSupplementalItem;
        }

        android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getSupplementalLineItem() {
            return this.mSupplementalLineItem;
        }

        android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo getSupplementalAlarmItem() {
            return this.mSupplementalAlarmItem;
        }

        int getLayoutWeight() {
            return this.mLayoutWeight;
        }

        public android.app.smartspace.uitemplatedata.BaseTemplateData.Builder setPrimaryItem(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo) {
            this.mPrimaryItem = subItemInfo;
            return this;
        }

        public android.app.smartspace.uitemplatedata.BaseTemplateData.Builder setSubtitleItem(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo) {
            this.mSubtitleItem = subItemInfo;
            return this;
        }

        public android.app.smartspace.uitemplatedata.BaseTemplateData.Builder setSubtitleSupplementalItem(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo) {
            this.mSubtitleSupplementalItem = subItemInfo;
            return this;
        }

        public android.app.smartspace.uitemplatedata.BaseTemplateData.Builder setSupplementalLineItem(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo) {
            this.mSupplementalLineItem = subItemInfo;
            return this;
        }

        public android.app.smartspace.uitemplatedata.BaseTemplateData.Builder setSupplementalAlarmItem(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo) {
            this.mSupplementalAlarmItem = subItemInfo;
            return this;
        }

        public android.app.smartspace.uitemplatedata.BaseTemplateData.Builder setLayoutWeight(int i) {
            this.mLayoutWeight = i;
            return this;
        }

        public android.app.smartspace.uitemplatedata.BaseTemplateData build() {
            return new android.app.smartspace.uitemplatedata.BaseTemplateData(this.mTemplateType, this.mPrimaryItem, this.mSubtitleItem, this.mSubtitleSupplementalItem, this.mSupplementalLineItem, this.mSupplementalAlarmItem, this.mLayoutWeight);
        }
    }

    public static final class SubItemInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo>() { // from class: android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo[] newArray(int i) {
                return new android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo[i];
            }
        };
        private final android.app.smartspace.uitemplatedata.Icon mIcon;
        private final android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo mLoggingInfo;
        private final android.app.smartspace.uitemplatedata.TapAction mTapAction;
        private final android.app.smartspace.uitemplatedata.Text mText;

        SubItemInfo(android.os.Parcel parcel) {
            this.mText = (android.app.smartspace.uitemplatedata.Text) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Text.CREATOR);
            this.mIcon = (android.app.smartspace.uitemplatedata.Icon) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Icon.CREATOR);
            this.mTapAction = (android.app.smartspace.uitemplatedata.TapAction) parcel.readTypedObject(android.app.smartspace.uitemplatedata.TapAction.CREATOR);
            this.mLoggingInfo = (android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo) parcel.readTypedObject(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo.CREATOR);
        }

        private SubItemInfo(android.app.smartspace.uitemplatedata.Text text, android.app.smartspace.uitemplatedata.Icon icon, android.app.smartspace.uitemplatedata.TapAction tapAction, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo subItemLoggingInfo) {
            this.mText = text;
            this.mIcon = icon;
            this.mTapAction = tapAction;
            this.mLoggingInfo = subItemLoggingInfo;
        }

        public android.app.smartspace.uitemplatedata.Text getText() {
            return this.mText;
        }

        public android.app.smartspace.uitemplatedata.Icon getIcon() {
            return this.mIcon;
        }

        public android.app.smartspace.uitemplatedata.TapAction getTapAction() {
            return this.mTapAction;
        }

        public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo getLoggingInfo() {
            return this.mLoggingInfo;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedObject(this.mText, i);
            parcel.writeTypedObject(this.mIcon, i);
            parcel.writeTypedObject(this.mTapAction, i);
            parcel.writeTypedObject(this.mLoggingInfo, i);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo)) {
                return false;
            }
            android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo = (android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo) obj;
            return android.app.smartspace.SmartspaceUtils.isEqual(this.mText, subItemInfo.mText) && java.util.Objects.equals(this.mIcon, subItemInfo.mIcon) && java.util.Objects.equals(this.mTapAction, subItemInfo.mTapAction) && java.util.Objects.equals(this.mLoggingInfo, subItemInfo.mLoggingInfo);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mText, this.mIcon, this.mTapAction, this.mLoggingInfo);
        }

        public java.lang.String toString() {
            return "SubItemInfo{mText=" + this.mText + ", mIcon=" + this.mIcon + ", mTapAction=" + this.mTapAction + ", mLoggingInfo=" + this.mLoggingInfo + '}';
        }

        @android.annotation.SystemApi
        public static final class Builder {
            private android.app.smartspace.uitemplatedata.Icon mIcon;
            private android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo mLoggingInfo;
            private android.app.smartspace.uitemplatedata.TapAction mTapAction;
            private android.app.smartspace.uitemplatedata.Text mText;

            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.Builder setText(android.app.smartspace.uitemplatedata.Text text) {
                this.mText = text;
                return this;
            }

            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.Builder setIcon(android.app.smartspace.uitemplatedata.Icon icon) {
                this.mIcon = icon;
                return this;
            }

            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.Builder setTapAction(android.app.smartspace.uitemplatedata.TapAction tapAction) {
                this.mTapAction = tapAction;
                return this;
            }

            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo.Builder setLoggingInfo(android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo subItemLoggingInfo) {
                this.mLoggingInfo = subItemLoggingInfo;
                return this;
            }

            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo build() {
                if (android.app.smartspace.SmartspaceUtils.isEmpty(this.mText) && this.mIcon == null && this.mTapAction == null && this.mLoggingInfo == null) {
                    throw new java.lang.IllegalStateException("SubItem data is empty");
                }
                return new android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo(this.mText, this.mIcon, this.mTapAction, this.mLoggingInfo);
            }
        }
    }

    public static final class SubItemLoggingInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo>() { // from class: android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo[] newArray(int i) {
                return new android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo[i];
            }
        };
        private final int mFeatureType;
        private final int mInstanceId;
        private final java.lang.CharSequence mPackageName;

        SubItemLoggingInfo(android.os.Parcel parcel) {
            this.mInstanceId = parcel.readInt();
            this.mFeatureType = parcel.readInt();
            this.mPackageName = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }

        private SubItemLoggingInfo(int i, int i2, java.lang.CharSequence charSequence) {
            this.mInstanceId = i;
            this.mFeatureType = i2;
            this.mPackageName = charSequence;
        }

        public int getInstanceId() {
            return this.mInstanceId;
        }

        public int getFeatureType() {
            return this.mFeatureType;
        }

        public java.lang.CharSequence getPackageName() {
            return this.mPackageName;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mInstanceId);
            parcel.writeInt(this.mFeatureType);
            android.text.TextUtils.writeToParcel(this.mPackageName, parcel, i);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo)) {
                return false;
            }
            android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo subItemLoggingInfo = (android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo) obj;
            return this.mInstanceId == subItemLoggingInfo.mInstanceId && this.mFeatureType == subItemLoggingInfo.mFeatureType && android.app.smartspace.SmartspaceUtils.isEqual(this.mPackageName, subItemLoggingInfo.mPackageName);
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mInstanceId), java.lang.Integer.valueOf(this.mFeatureType), this.mPackageName);
        }

        public java.lang.String toString() {
            return "SubItemLoggingInfo{mInstanceId=" + this.mInstanceId + ", mFeatureType=" + this.mFeatureType + ", mPackageName=" + ((java.lang.Object) this.mPackageName) + '}';
        }

        @android.annotation.SystemApi
        public static final class Builder {
            private final int mFeatureType;
            private final int mInstanceId;
            private java.lang.CharSequence mPackageName;

            public Builder(int i, int i2) {
                this.mInstanceId = i;
                this.mFeatureType = i2;
            }

            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo.Builder setPackageName(java.lang.CharSequence charSequence) {
                this.mPackageName = charSequence;
                return this;
            }

            public android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo build() {
                return new android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemLoggingInfo(this.mInstanceId, this.mFeatureType, this.mPackageName);
            }
        }
    }
}
