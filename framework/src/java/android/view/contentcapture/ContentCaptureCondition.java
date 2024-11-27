package android.view.contentcapture;

/* loaded from: classes4.dex */
public final class ContentCaptureCondition implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.contentcapture.ContentCaptureCondition> CREATOR = new android.os.Parcelable.Creator<android.view.contentcapture.ContentCaptureCondition>() { // from class: android.view.contentcapture.ContentCaptureCondition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.ContentCaptureCondition createFromParcel(android.os.Parcel parcel) {
            return new android.view.contentcapture.ContentCaptureCondition((android.content.LocusId) parcel.readParcelable(null, android.content.LocusId.class), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.ContentCaptureCondition[] newArray(int i) {
            return new android.view.contentcapture.ContentCaptureCondition[i];
        }
    };
    public static final int FLAG_IS_REGEX = 2;
    private final int mFlags;
    private final android.content.LocusId mLocusId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Flags {
    }

    public ContentCaptureCondition(android.content.LocusId locusId, int i) {
        this.mLocusId = (android.content.LocusId) java.util.Objects.requireNonNull(locusId);
        this.mFlags = i;
    }

    public android.content.LocusId getLocusId() {
        return this.mLocusId;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int hashCode() {
        return ((this.mFlags + 31) * 31) + (this.mLocusId == null ? 0 : this.mLocusId.hashCode());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.contentcapture.ContentCaptureCondition contentCaptureCondition = (android.view.contentcapture.ContentCaptureCondition) obj;
        if (this.mFlags != contentCaptureCondition.mFlags) {
            return false;
        }
        if (this.mLocusId == null) {
            if (contentCaptureCondition.mLocusId != null) {
                return false;
            }
        } else if (!this.mLocusId.equals(contentCaptureCondition.mLocusId)) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mLocusId.toString());
        if (this.mFlags != 0) {
            sb.append(" (").append(android.util.DebugUtils.flagsToString(android.view.contentcapture.ContentCaptureCondition.class, "FLAG_", this.mFlags)).append(')');
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mLocusId, i);
        parcel.writeInt(this.mFlags);
    }
}
