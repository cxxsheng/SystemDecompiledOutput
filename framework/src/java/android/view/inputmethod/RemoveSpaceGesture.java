package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class RemoveSpaceGesture extends android.view.inputmethod.HandwritingGesture implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.RemoveSpaceGesture> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.RemoveSpaceGesture>() { // from class: android.view.inputmethod.RemoveSpaceGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.RemoveSpaceGesture createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.RemoveSpaceGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.RemoveSpaceGesture[] newArray(int i) {
            return new android.view.inputmethod.RemoveSpaceGesture[i];
        }
    };
    private final android.graphics.PointF mEndPoint;
    private final android.graphics.PointF mStartPoint;

    private RemoveSpaceGesture(android.graphics.PointF pointF, android.graphics.PointF pointF2, java.lang.String str) {
        this.mType = 8;
        this.mStartPoint = pointF;
        this.mEndPoint = pointF2;
        this.mFallbackText = str;
    }

    private RemoveSpaceGesture(android.os.Parcel parcel) {
        this.mType = 8;
        this.mStartPoint = (android.graphics.PointF) parcel.readTypedObject(android.graphics.PointF.CREATOR);
        this.mEndPoint = (android.graphics.PointF) parcel.readTypedObject(android.graphics.PointF.CREATOR);
        this.mFallbackText = parcel.readString8();
    }

    public android.graphics.PointF getStartPoint() {
        return this.mStartPoint;
    }

    public android.graphics.PointF getEndPoint() {
        return this.mEndPoint;
    }

    public static final class Builder {
        private android.graphics.PointF mEndPoint;
        private java.lang.String mFallbackText;
        private android.graphics.PointF mStartPoint;

        public android.view.inputmethod.RemoveSpaceGesture.Builder setPoints(android.graphics.PointF pointF, android.graphics.PointF pointF2) {
            this.mStartPoint = pointF;
            this.mEndPoint = pointF2;
            return this;
        }

        public android.view.inputmethod.RemoveSpaceGesture.Builder setFallbackText(java.lang.String str) {
            this.mFallbackText = str;
            return this;
        }

        public android.view.inputmethod.RemoveSpaceGesture build() {
            if (this.mStartPoint == null || this.mEndPoint == null) {
                throw new java.lang.IllegalArgumentException("Start and end points must be set.");
            }
            return new android.view.inputmethod.RemoveSpaceGesture(this.mStartPoint, this.mEndPoint, this.mFallbackText);
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mStartPoint, this.mEndPoint, this.mFallbackText);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.view.inputmethod.RemoveSpaceGesture)) {
            return false;
        }
        android.view.inputmethod.RemoveSpaceGesture removeSpaceGesture = (android.view.inputmethod.RemoveSpaceGesture) obj;
        return java.util.Objects.equals(this.mStartPoint, removeSpaceGesture.mStartPoint) && java.util.Objects.equals(this.mEndPoint, removeSpaceGesture.mEndPoint) && java.util.Objects.equals(this.mFallbackText, removeSpaceGesture.mFallbackText);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mStartPoint, i);
        parcel.writeTypedObject(this.mEndPoint, i);
        parcel.writeString8(this.mFallbackText);
    }
}
