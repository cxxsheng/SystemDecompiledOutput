package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class EditorBoundsInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.EditorBoundsInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.EditorBoundsInfo>() { // from class: android.view.inputmethod.EditorBoundsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.EditorBoundsInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.EditorBoundsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.EditorBoundsInfo[] newArray(int i) {
            return new android.view.inputmethod.EditorBoundsInfo[i];
        }
    };
    private final android.graphics.RectF mEditorBounds;
    private final android.graphics.RectF mHandwritingBounds;
    private final int mHashCode;

    private EditorBoundsInfo(android.os.Parcel parcel) {
        this.mHashCode = parcel.readInt();
        this.mEditorBounds = (android.graphics.RectF) parcel.readTypedObject(android.graphics.RectF.CREATOR);
        this.mHandwritingBounds = (android.graphics.RectF) parcel.readTypedObject(android.graphics.RectF.CREATOR);
    }

    public android.graphics.RectF getEditorBounds() {
        return this.mEditorBounds;
    }

    public android.graphics.RectF getHandwritingBounds() {
        return this.mHandwritingBounds;
    }

    public int hashCode() {
        return this.mHashCode;
    }

    public java.lang.String toString() {
        return "EditorBoundsInfo{mEditorBounds=" + this.mEditorBounds + " mHandwritingBounds=" + this.mHandwritingBounds + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.view.inputmethod.EditorBoundsInfo)) {
            return false;
        }
        android.view.inputmethod.EditorBoundsInfo editorBoundsInfo = (android.view.inputmethod.EditorBoundsInfo) obj;
        return java.util.Objects.equals(editorBoundsInfo.mEditorBounds, this.mEditorBounds) && java.util.Objects.equals(editorBoundsInfo.mHandwritingBounds, this.mHandwritingBounds);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mHashCode);
        parcel.writeTypedObject(this.mEditorBounds, i);
        parcel.writeTypedObject(this.mHandwritingBounds, i);
    }

    public static final class Builder {
        private android.graphics.RectF mEditorBounds = null;
        private android.graphics.RectF mHandwritingBounds = null;

        public android.view.inputmethod.EditorBoundsInfo.Builder setEditorBounds(android.graphics.RectF rectF) {
            this.mEditorBounds = rectF;
            return this;
        }

        public android.view.inputmethod.EditorBoundsInfo.Builder setHandwritingBounds(android.graphics.RectF rectF) {
            this.mHandwritingBounds = rectF;
            return this;
        }

        public android.view.inputmethod.EditorBoundsInfo build() {
            return new android.view.inputmethod.EditorBoundsInfo(this);
        }
    }

    private EditorBoundsInfo(android.view.inputmethod.EditorBoundsInfo.Builder builder) {
        this.mEditorBounds = builder.mEditorBounds;
        this.mHandwritingBounds = builder.mHandwritingBounds;
        this.mHashCode = (java.util.Objects.hashCode(this.mEditorBounds) * 31) + java.util.Objects.hashCode(this.mHandwritingBounds);
    }
}
