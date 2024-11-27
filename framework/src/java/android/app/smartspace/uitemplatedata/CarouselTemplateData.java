package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CarouselTemplateData extends android.app.smartspace.uitemplatedata.BaseTemplateData {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.CarouselTemplateData> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.CarouselTemplateData>() { // from class: android.app.smartspace.uitemplatedata.CarouselTemplateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.CarouselTemplateData createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.CarouselTemplateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.CarouselTemplateData[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.CarouselTemplateData[i];
        }
    };
    private final android.app.smartspace.uitemplatedata.TapAction mCarouselAction;
    private final java.util.List<android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem> mCarouselItems;

    CarouselTemplateData(android.os.Parcel parcel) {
        super(parcel);
        this.mCarouselItems = parcel.createTypedArrayList(android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem.CREATOR);
        this.mCarouselAction = (android.app.smartspace.uitemplatedata.TapAction) parcel.readTypedObject(android.app.smartspace.uitemplatedata.TapAction.CREATOR);
    }

    private CarouselTemplateData(int i, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo2, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo3, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo4, android.app.smartspace.uitemplatedata.BaseTemplateData.SubItemInfo subItemInfo5, int i2, java.util.List<android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem> list, android.app.smartspace.uitemplatedata.TapAction tapAction) {
        super(i, subItemInfo, subItemInfo2, subItemInfo3, subItemInfo4, subItemInfo5, i2);
        this.mCarouselItems = list;
        this.mCarouselAction = tapAction;
    }

    public java.util.List<android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem> getCarouselItems() {
        return this.mCarouselItems;
    }

    public android.app.smartspace.uitemplatedata.TapAction getCarouselAction() {
        return this.mCarouselAction;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mCarouselItems);
        parcel.writeTypedObject(this.mCarouselAction, i);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.smartspace.uitemplatedata.CarouselTemplateData) || !super.equals(obj)) {
            return false;
        }
        android.app.smartspace.uitemplatedata.CarouselTemplateData carouselTemplateData = (android.app.smartspace.uitemplatedata.CarouselTemplateData) obj;
        return this.mCarouselItems.equals(carouselTemplateData.mCarouselItems) && java.util.Objects.equals(this.mCarouselAction, carouselTemplateData.mCarouselAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mCarouselItems, this.mCarouselAction);
    }

    @Override // android.app.smartspace.uitemplatedata.BaseTemplateData
    public java.lang.String toString() {
        return super.toString() + " + SmartspaceCarouselUiTemplateData{mCarouselItems=" + this.mCarouselItems + ", mCarouselActions=" + this.mCarouselAction + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder extends android.app.smartspace.uitemplatedata.BaseTemplateData.Builder {
        private android.app.smartspace.uitemplatedata.TapAction mCarouselAction;
        private final java.util.List<android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem> mCarouselItems;

        public Builder(java.util.List<android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem> list) {
            super(4);
            this.mCarouselItems = (java.util.List) java.util.Objects.requireNonNull(list);
        }

        public android.app.smartspace.uitemplatedata.CarouselTemplateData.Builder setCarouselAction(android.app.smartspace.uitemplatedata.TapAction tapAction) {
            this.mCarouselAction = tapAction;
            return this;
        }

        @Override // android.app.smartspace.uitemplatedata.BaseTemplateData.Builder
        public android.app.smartspace.uitemplatedata.CarouselTemplateData build() {
            if (this.mCarouselItems.isEmpty()) {
                throw new java.lang.IllegalStateException("Carousel data is empty");
            }
            return new android.app.smartspace.uitemplatedata.CarouselTemplateData(getTemplateType(), getPrimaryItem(), getSubtitleItem(), getSubtitleSupplemtnalItem(), getSupplementalLineItem(), getSupplementalAlarmItem(), getLayoutWeight(), this.mCarouselItems, this.mCarouselAction);
        }
    }

    public static final class CarouselItem implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem>() { // from class: android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem createFromParcel(android.os.Parcel parcel) {
                return new android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem[] newArray(int i) {
                return new android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem[i];
            }
        };
        private final android.app.smartspace.uitemplatedata.Icon mImage;
        private final android.app.smartspace.uitemplatedata.Text mLowerText;
        private final android.app.smartspace.uitemplatedata.TapAction mTapAction;
        private final android.app.smartspace.uitemplatedata.Text mUpperText;

        CarouselItem(android.os.Parcel parcel) {
            this.mUpperText = (android.app.smartspace.uitemplatedata.Text) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Text.CREATOR);
            this.mImage = (android.app.smartspace.uitemplatedata.Icon) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Icon.CREATOR);
            this.mLowerText = (android.app.smartspace.uitemplatedata.Text) parcel.readTypedObject(android.app.smartspace.uitemplatedata.Text.CREATOR);
            this.mTapAction = (android.app.smartspace.uitemplatedata.TapAction) parcel.readTypedObject(android.app.smartspace.uitemplatedata.TapAction.CREATOR);
        }

        private CarouselItem(android.app.smartspace.uitemplatedata.Text text, android.app.smartspace.uitemplatedata.Icon icon, android.app.smartspace.uitemplatedata.Text text2, android.app.smartspace.uitemplatedata.TapAction tapAction) {
            this.mUpperText = text;
            this.mImage = icon;
            this.mLowerText = text2;
            this.mTapAction = tapAction;
        }

        public android.app.smartspace.uitemplatedata.Text getUpperText() {
            return this.mUpperText;
        }

        public android.app.smartspace.uitemplatedata.Icon getImage() {
            return this.mImage;
        }

        public android.app.smartspace.uitemplatedata.Text getLowerText() {
            return this.mLowerText;
        }

        public android.app.smartspace.uitemplatedata.TapAction getTapAction() {
            return this.mTapAction;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedObject(this.mUpperText, i);
            parcel.writeTypedObject(this.mImage, i);
            parcel.writeTypedObject(this.mLowerText, i);
            parcel.writeTypedObject(this.mTapAction, i);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem)) {
                return false;
            }
            android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem carouselItem = (android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem) obj;
            return android.app.smartspace.SmartspaceUtils.isEqual(this.mUpperText, carouselItem.mUpperText) && java.util.Objects.equals(this.mImage, carouselItem.mImage) && android.app.smartspace.SmartspaceUtils.isEqual(this.mLowerText, carouselItem.mLowerText) && java.util.Objects.equals(this.mTapAction, carouselItem.mTapAction);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mUpperText, this.mImage, this.mLowerText, this.mTapAction);
        }

        public java.lang.String toString() {
            return "CarouselItem{mUpperText=" + this.mUpperText + ", mImage=" + this.mImage + ", mLowerText=" + this.mLowerText + ", mTapAction=" + this.mTapAction + '}';
        }

        @android.annotation.SystemApi
        public static final class Builder {
            private android.app.smartspace.uitemplatedata.Icon mImage;
            private android.app.smartspace.uitemplatedata.Text mLowerText;
            private android.app.smartspace.uitemplatedata.TapAction mTapAction;
            private android.app.smartspace.uitemplatedata.Text mUpperText;

            public android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem.Builder setUpperText(android.app.smartspace.uitemplatedata.Text text) {
                this.mUpperText = text;
                return this;
            }

            public android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem.Builder setImage(android.app.smartspace.uitemplatedata.Icon icon) {
                this.mImage = icon;
                return this;
            }

            public android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem.Builder setLowerText(android.app.smartspace.uitemplatedata.Text text) {
                this.mLowerText = text;
                return this;
            }

            public android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem.Builder setTapAction(android.app.smartspace.uitemplatedata.TapAction tapAction) {
                this.mTapAction = tapAction;
                return this;
            }

            public android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem build() {
                if (android.app.smartspace.SmartspaceUtils.isEmpty(this.mUpperText) && this.mImage == null && android.app.smartspace.SmartspaceUtils.isEmpty(this.mLowerText)) {
                    throw new java.lang.IllegalStateException("Carousel data is empty");
                }
                return new android.app.smartspace.uitemplatedata.CarouselTemplateData.CarouselItem(this.mUpperText, this.mImage, this.mLowerText, this.mTapAction);
            }
        }
    }
}
