package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class DeleteGesture extends android.view.inputmethod.PreviewableHandwritingGesture implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.DeleteGesture> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.DeleteGesture>() { // from class: android.view.inputmethod.DeleteGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.DeleteGesture createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.DeleteGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.DeleteGesture[] newArray(int i) {
            return new android.view.inputmethod.DeleteGesture[i];
        }
    };
    private android.graphics.RectF mArea;
    private int mGranularity;

    private DeleteGesture(int i, android.graphics.RectF rectF, java.lang.String str) {
        this.mType = 4;
        this.mArea = rectF;
        this.mGranularity = i;
        this.mFallbackText = str;
    }

    private DeleteGesture(android.os.Parcel parcel) {
        this.mType = 4;
        this.mFallbackText = parcel.readString8();
        this.mGranularity = parcel.readInt();
        this.mArea = (android.graphics.RectF) parcel.readTypedObject(android.graphics.RectF.CREATOR);
    }

    public int getGranularity() {
        return this.mGranularity;
    }

    public android.graphics.RectF getDeletionArea() {
        return this.mArea;
    }

    public static final class Builder {
        private android.graphics.RectF mArea;
        private java.lang.String mFallbackText;
        private int mGranularity;

        public android.view.inputmethod.DeleteGesture.Builder setGranularity(int i) {
            this.mGranularity = i;
            return this;
        }

        public android.view.inputmethod.DeleteGesture.Builder setDeletionArea(android.graphics.RectF rectF) {
            this.mArea = rectF;
            return this;
        }

        public android.view.inputmethod.DeleteGesture.Builder setFallbackText(java.lang.String str) {
            this.mFallbackText = str;
            return this;
        }

        public android.view.inputmethod.DeleteGesture build() {
            if (this.mArea == null || this.mArea.isEmpty()) {
                throw new java.lang.IllegalArgumentException("Deletion area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new java.lang.IllegalArgumentException("Deletion granularity must be set.");
            }
            return new android.view.inputmethod.DeleteGesture(this.mGranularity, this.mArea, this.mFallbackText);
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mArea, java.lang.Integer.valueOf(this.mGranularity), this.mFallbackText);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.view.inputmethod.DeleteGesture)) {
            return false;
        }
        android.view.inputmethod.DeleteGesture deleteGesture = (android.view.inputmethod.DeleteGesture) obj;
        if (this.mGranularity == deleteGesture.mGranularity && java.util.Objects.equals(this.mFallbackText, deleteGesture.mFallbackText)) {
            return java.util.Objects.equals(this.mArea, deleteGesture.mArea);
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
