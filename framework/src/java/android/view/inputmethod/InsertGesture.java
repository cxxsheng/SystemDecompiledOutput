package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InsertGesture extends android.view.inputmethod.HandwritingGesture implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InsertGesture> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InsertGesture>() { // from class: android.view.inputmethod.InsertGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InsertGesture createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.InsertGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InsertGesture[] newArray(int i) {
            return new android.view.inputmethod.InsertGesture[i];
        }
    };
    private android.graphics.PointF mPoint;
    private java.lang.String mTextToInsert;

    private InsertGesture(java.lang.String str, android.graphics.PointF pointF, java.lang.String str2) {
        this.mType = 2;
        this.mPoint = pointF;
        this.mTextToInsert = str;
        this.mFallbackText = str2;
    }

    private InsertGesture(android.os.Parcel parcel) {
        this.mType = 2;
        this.mFallbackText = parcel.readString8();
        this.mTextToInsert = parcel.readString8();
        this.mPoint = (android.graphics.PointF) parcel.readTypedObject(android.graphics.PointF.CREATOR);
    }

    public java.lang.String getTextToInsert() {
        return this.mTextToInsert;
    }

    public android.graphics.PointF getInsertionPoint() {
        return this.mPoint;
    }

    public static final class Builder {
        private java.lang.String mFallbackText;
        private android.graphics.PointF mPoint;
        private java.lang.String mText;

        public android.view.inputmethod.InsertGesture.Builder setTextToInsert(java.lang.String str) {
            this.mText = str;
            return this;
        }

        public android.view.inputmethod.InsertGesture.Builder setInsertionPoint(android.graphics.PointF pointF) {
            this.mPoint = pointF;
            return this;
        }

        public android.view.inputmethod.InsertGesture.Builder setFallbackText(java.lang.String str) {
            this.mFallbackText = str;
            return this;
        }

        public android.view.inputmethod.InsertGesture build() {
            if (this.mPoint == null) {
                throw new java.lang.IllegalArgumentException("Insertion point must be set.");
            }
            if (this.mText == null) {
                throw new java.lang.IllegalArgumentException("Text to insert must be set.");
            }
            return new android.view.inputmethod.InsertGesture(this.mText, this.mPoint, this.mFallbackText);
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPoint, this.mTextToInsert, this.mFallbackText);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.view.inputmethod.InsertGesture)) {
            return false;
        }
        android.view.inputmethod.InsertGesture insertGesture = (android.view.inputmethod.InsertGesture) obj;
        if (java.util.Objects.equals(this.mFallbackText, insertGesture.mFallbackText) && java.util.Objects.equals(this.mTextToInsert, insertGesture.mTextToInsert)) {
            return java.util.Objects.equals(this.mPoint, insertGesture.mPoint);
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
        parcel.writeString8(this.mTextToInsert);
        parcel.writeTypedObject(this.mPoint, i);
    }
}
