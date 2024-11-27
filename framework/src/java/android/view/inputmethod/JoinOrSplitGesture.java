package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class JoinOrSplitGesture extends android.view.inputmethod.HandwritingGesture implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.JoinOrSplitGesture> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.JoinOrSplitGesture>() { // from class: android.view.inputmethod.JoinOrSplitGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.JoinOrSplitGesture createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.JoinOrSplitGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.JoinOrSplitGesture[] newArray(int i) {
            return new android.view.inputmethod.JoinOrSplitGesture[i];
        }
    };
    private final android.graphics.PointF mPoint;

    private JoinOrSplitGesture(android.graphics.PointF pointF, java.lang.String str) {
        this.mType = 16;
        this.mPoint = pointF;
        this.mFallbackText = str;
    }

    private JoinOrSplitGesture(android.os.Parcel parcel) {
        this.mType = 16;
        this.mPoint = (android.graphics.PointF) parcel.readTypedObject(android.graphics.PointF.CREATOR);
        this.mFallbackText = parcel.readString8();
    }

    public android.graphics.PointF getJoinOrSplitPoint() {
        return this.mPoint;
    }

    public static final class Builder {
        private java.lang.String mFallbackText;
        private android.graphics.PointF mPoint;

        public android.view.inputmethod.JoinOrSplitGesture.Builder setJoinOrSplitPoint(android.graphics.PointF pointF) {
            this.mPoint = pointF;
            return this;
        }

        public android.view.inputmethod.JoinOrSplitGesture.Builder setFallbackText(java.lang.String str) {
            this.mFallbackText = str;
            return this;
        }

        public android.view.inputmethod.JoinOrSplitGesture build() {
            if (this.mPoint == null) {
                throw new java.lang.IllegalArgumentException("Point must be set.");
            }
            return new android.view.inputmethod.JoinOrSplitGesture(this.mPoint, this.mFallbackText);
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPoint, this.mFallbackText);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.view.inputmethod.JoinOrSplitGesture)) {
            return false;
        }
        android.view.inputmethod.JoinOrSplitGesture joinOrSplitGesture = (android.view.inputmethod.JoinOrSplitGesture) obj;
        return java.util.Objects.equals(this.mPoint, joinOrSplitGesture.mPoint) && java.util.Objects.equals(this.mFallbackText, joinOrSplitGesture.mFallbackText);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mPoint, i);
        parcel.writeString8(this.mFallbackText);
    }
}
