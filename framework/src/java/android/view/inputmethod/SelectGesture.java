package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class SelectGesture extends android.view.inputmethod.PreviewableHandwritingGesture implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.SelectGesture> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.SelectGesture>() { // from class: android.view.inputmethod.SelectGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.SelectGesture createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.SelectGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.SelectGesture[] newArray(int i) {
            return new android.view.inputmethod.SelectGesture[i];
        }
    };
    private android.graphics.RectF mArea;
    private int mGranularity;

    private SelectGesture(int i, android.graphics.RectF rectF, java.lang.String str) {
        this.mType = 1;
        this.mArea = rectF;
        this.mGranularity = i;
        this.mFallbackText = str;
    }

    private SelectGesture(android.os.Parcel parcel) {
        this.mType = 1;
        this.mFallbackText = parcel.readString8();
        this.mGranularity = parcel.readInt();
        this.mArea = (android.graphics.RectF) parcel.readTypedObject(android.graphics.RectF.CREATOR);
    }

    public int getGranularity() {
        return this.mGranularity;
    }

    public android.graphics.RectF getSelectionArea() {
        return this.mArea;
    }

    public static final class Builder {
        private android.graphics.RectF mArea;
        private java.lang.String mFallbackText;
        private int mGranularity;

        public android.view.inputmethod.SelectGesture.Builder setGranularity(int i) {
            this.mGranularity = i;
            return this;
        }

        public android.view.inputmethod.SelectGesture.Builder setSelectionArea(android.graphics.RectF rectF) {
            this.mArea = rectF;
            return this;
        }

        public android.view.inputmethod.SelectGesture.Builder setFallbackText(java.lang.String str) {
            this.mFallbackText = str;
            return this;
        }

        public android.view.inputmethod.SelectGesture build() {
            if (this.mArea == null || this.mArea.isEmpty()) {
                throw new java.lang.IllegalArgumentException("Selection area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new java.lang.IllegalArgumentException("Selection granularity must be set.");
            }
            return new android.view.inputmethod.SelectGesture(this.mGranularity, this.mArea, this.mFallbackText);
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mGranularity), this.mArea, this.mFallbackText);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.view.inputmethod.SelectGesture)) {
            return false;
        }
        android.view.inputmethod.SelectGesture selectGesture = (android.view.inputmethod.SelectGesture) obj;
        if (this.mGranularity == selectGesture.mGranularity && java.util.Objects.equals(this.mFallbackText, selectGesture.mFallbackText)) {
            return java.util.Objects.equals(this.mArea, selectGesture.mArea);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mFallbackText);
        parcel.writeInt(this.mGranularity);
        parcel.writeTypedObject(this.mArea, i);
    }
}
