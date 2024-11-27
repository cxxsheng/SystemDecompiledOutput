package android.view.contentcapture;

/* loaded from: classes4.dex */
public final class ContentCaptureSessionId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.contentcapture.ContentCaptureSessionId> CREATOR = new android.os.Parcelable.Creator<android.view.contentcapture.ContentCaptureSessionId>() { // from class: android.view.contentcapture.ContentCaptureSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.ContentCaptureSessionId createFromParcel(android.os.Parcel parcel) {
            return new android.view.contentcapture.ContentCaptureSessionId(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.ContentCaptureSessionId[] newArray(int i) {
            return new android.view.contentcapture.ContentCaptureSessionId[i];
        }
    };
    private final int mValue;

    public ContentCaptureSessionId(int i) {
        this.mValue = i;
    }

    public int getValue() {
        return this.mValue;
    }

    public int hashCode() {
        return 31 + this.mValue;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mValue == ((android.view.contentcapture.ContentCaptureSessionId) obj).mValue) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        return java.lang.Integer.toString(this.mValue);
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.print(this.mValue);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mValue);
    }
}
