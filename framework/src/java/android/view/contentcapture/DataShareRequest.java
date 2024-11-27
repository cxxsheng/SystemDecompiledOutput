package android.view.contentcapture;

/* loaded from: classes4.dex */
public final class DataShareRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.contentcapture.DataShareRequest> CREATOR = new android.os.Parcelable.Creator<android.view.contentcapture.DataShareRequest>() { // from class: android.view.contentcapture.DataShareRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.DataShareRequest[] newArray(int i) {
            return new android.view.contentcapture.DataShareRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.DataShareRequest createFromParcel(android.os.Parcel parcel) {
            return new android.view.contentcapture.DataShareRequest(parcel);
        }
    };
    private final android.content.LocusId mLocusId;
    private final java.lang.String mMimeType;
    private final java.lang.String mPackageName;

    public DataShareRequest(android.content.LocusId locusId, java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        this.mPackageName = android.app.ActivityThread.currentActivityThread().getApplication().getPackageName();
        this.mLocusId = locusId;
        this.mMimeType = str;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.content.LocusId getLocusId() {
        return this.mLocusId;
    }

    public java.lang.String getMimeType() {
        return this.mMimeType;
    }

    public java.lang.String toString() {
        return "DataShareRequest { packageName = " + this.mPackageName + ", locusId = " + this.mLocusId + ", mimeType = " + this.mMimeType + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.contentcapture.DataShareRequest dataShareRequest = (android.view.contentcapture.DataShareRequest) obj;
        if (java.util.Objects.equals(this.mPackageName, dataShareRequest.mPackageName) && java.util.Objects.equals(this.mLocusId, dataShareRequest.mLocusId) && java.util.Objects.equals(this.mMimeType, dataShareRequest.mMimeType)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mPackageName) + 31) * 31) + java.util.Objects.hashCode(this.mLocusId)) * 31) + java.util.Objects.hashCode(this.mMimeType);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mLocusId != null ? (byte) 2 : (byte) 0);
        parcel.writeString(this.mPackageName);
        if (this.mLocusId != null) {
            parcel.writeTypedObject(this.mLocusId, i);
        }
        parcel.writeString(this.mMimeType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    DataShareRequest(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        java.lang.String readString = parcel.readString();
        android.content.LocusId locusId = (readByte & 2) == 0 ? null : (android.content.LocusId) parcel.readTypedObject(android.content.LocusId.CREATOR);
        java.lang.String readString2 = parcel.readString();
        this.mPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mLocusId = locusId;
        this.mMimeType = readString2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMimeType);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
