package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class DeleteRangeGesture extends android.view.inputmethod.PreviewableHandwritingGesture implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.DeleteRangeGesture> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.DeleteRangeGesture>() { // from class: android.view.inputmethod.DeleteRangeGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.DeleteRangeGesture createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.DeleteRangeGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.DeleteRangeGesture[] newArray(int i) {
            return new android.view.inputmethod.DeleteRangeGesture[i];
        }
    };
    private android.graphics.RectF mEndArea;
    private int mGranularity;
    private android.graphics.RectF mStartArea;

    private DeleteRangeGesture(int i, android.graphics.RectF rectF, android.graphics.RectF rectF2, java.lang.String str) {
        this.mType = 64;
        this.mStartArea = rectF;
        this.mEndArea = rectF2;
        this.mGranularity = i;
        this.mFallbackText = str;
    }

    private DeleteRangeGesture(android.os.Parcel parcel) {
        this.mType = 64;
        this.mFallbackText = parcel.readString8();
        this.mGranularity = parcel.readInt();
        this.mStartArea = (android.graphics.RectF) parcel.readTypedObject(android.graphics.RectF.CREATOR);
        this.mEndArea = (android.graphics.RectF) parcel.readTypedObject(android.graphics.RectF.CREATOR);
    }

    public int getGranularity() {
        return this.mGranularity;
    }

    public android.graphics.RectF getDeletionStartArea() {
        return this.mStartArea;
    }

    public android.graphics.RectF getDeletionEndArea() {
        return this.mEndArea;
    }

    public static final class Builder {
        private android.graphics.RectF mEndArea;
        private java.lang.String mFallbackText;
        private int mGranularity;
        private android.graphics.RectF mStartArea;

        public android.view.inputmethod.DeleteRangeGesture.Builder setGranularity(int i) {
            this.mGranularity = i;
            return this;
        }

        public android.view.inputmethod.DeleteRangeGesture.Builder setDeletionStartArea(android.graphics.RectF rectF) {
            this.mStartArea = rectF;
            return this;
        }

        public android.view.inputmethod.DeleteRangeGesture.Builder setDeletionEndArea(android.graphics.RectF rectF) {
            this.mEndArea = rectF;
            return this;
        }

        public android.view.inputmethod.DeleteRangeGesture.Builder setFallbackText(java.lang.String str) {
            this.mFallbackText = str;
            return this;
        }

        public android.view.inputmethod.DeleteRangeGesture build() {
            if (this.mStartArea == null || this.mStartArea.isEmpty() || this.mEndArea == null || this.mEndArea.isEmpty()) {
                throw new java.lang.IllegalArgumentException("Deletion area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new java.lang.IllegalArgumentException("Deletion granularity must be set.");
            }
            return new android.view.inputmethod.DeleteRangeGesture(this.mGranularity, this.mStartArea, this.mEndArea, this.mFallbackText);
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mGranularity), this.mStartArea, this.mEndArea, this.mFallbackText);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.view.inputmethod.DeleteRangeGesture)) {
            return false;
        }
        android.view.inputmethod.DeleteRangeGesture deleteRangeGesture = (android.view.inputmethod.DeleteRangeGesture) obj;
        if (this.mGranularity == deleteRangeGesture.mGranularity && java.util.Objects.equals(this.mFallbackText, deleteRangeGesture.mFallbackText) && java.util.Objects.equals(this.mStartArea, deleteRangeGesture.mStartArea)) {
            return java.util.Objects.equals(this.mEndArea, deleteRangeGesture.mEndArea);
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
        parcel.writeTypedObject(this.mStartArea, i);
        parcel.writeTypedObject(this.mEndArea, i);
    }
}
