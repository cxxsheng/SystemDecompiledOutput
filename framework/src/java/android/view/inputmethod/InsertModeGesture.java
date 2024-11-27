package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InsertModeGesture extends android.view.inputmethod.CancellableHandwritingGesture implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InsertModeGesture> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InsertModeGesture>() { // from class: android.view.inputmethod.InsertModeGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InsertModeGesture createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.InsertModeGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InsertModeGesture[] newArray(int i) {
            return new android.view.inputmethod.InsertModeGesture[i];
        }
    };
    private android.graphics.PointF mPoint;

    private InsertModeGesture(android.graphics.PointF pointF, java.lang.String str, android.os.CancellationSignal cancellationSignal) {
        this.mType = 128;
        this.mPoint = pointF;
        this.mFallbackText = str;
        this.mCancellationSignal = cancellationSignal;
    }

    private InsertModeGesture(android.os.Parcel parcel) {
        this.mType = 128;
        this.mFallbackText = parcel.readString8();
        this.mPoint = (android.graphics.PointF) parcel.readTypedObject(android.graphics.PointF.CREATOR);
        this.mCancellationSignalToken = parcel.readStrongBinder();
    }

    @Override // android.view.inputmethod.CancellableHandwritingGesture
    public android.os.CancellationSignal getCancellationSignal() {
        return this.mCancellationSignal;
    }

    public android.graphics.PointF getInsertionPoint() {
        return this.mPoint;
    }

    public static final class Builder {
        private android.os.CancellationSignal mCancellationSignal;
        private java.lang.String mFallbackText;
        private android.graphics.PointF mPoint;

        public android.view.inputmethod.InsertModeGesture.Builder setInsertionPoint(android.graphics.PointF pointF) {
            this.mPoint = pointF;
            return this;
        }

        public android.view.inputmethod.InsertModeGesture.Builder setCancellationSignal(android.os.CancellationSignal cancellationSignal) {
            this.mCancellationSignal = cancellationSignal;
            return this;
        }

        public android.view.inputmethod.InsertModeGesture.Builder setFallbackText(java.lang.String str) {
            this.mFallbackText = str;
            return this;
        }

        public android.view.inputmethod.InsertModeGesture build() {
            if (this.mPoint == null) {
                throw new java.lang.IllegalArgumentException("Insertion point must be set.");
            }
            if (this.mCancellationSignal == null) {
                throw new java.lang.IllegalArgumentException("CancellationSignal must be set.");
            }
            return new android.view.inputmethod.InsertModeGesture(this.mPoint, this.mFallbackText, this.mCancellationSignal);
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPoint, this.mFallbackText);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.view.inputmethod.InsertModeGesture)) {
            return false;
        }
        android.view.inputmethod.InsertModeGesture insertModeGesture = (android.view.inputmethod.InsertModeGesture) obj;
        if (java.util.Objects.equals(this.mFallbackText, insertModeGesture.mFallbackText)) {
            return java.util.Objects.equals(this.mPoint, insertModeGesture.mPoint);
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
        parcel.writeTypedObject(this.mPoint, i);
        parcel.writeStrongBinder(android.os.CancellationSignalBeamer.Sender.beamFromScope(this.mCancellationSignal));
    }
}
