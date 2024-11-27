package android.content.om;

/* loaded from: classes.dex */
public final class OverlayIdentifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.om.OverlayIdentifier> CREATOR = new android.os.Parcelable.Creator<android.content.om.OverlayIdentifier>() { // from class: android.content.om.OverlayIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.om.OverlayIdentifier[] newArray(int i) {
            return new android.content.om.OverlayIdentifier[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.om.OverlayIdentifier createFromParcel(android.os.Parcel parcel) {
            return new android.content.om.OverlayIdentifier(parcel);
        }
    };
    private final java.lang.String mOverlayName;
    private final java.lang.String mPackageName;

    public OverlayIdentifier(java.lang.String str, java.lang.String str2) {
        this.mPackageName = str;
        this.mOverlayName = str2;
    }

    public OverlayIdentifier(java.lang.String str) {
        this.mPackageName = str;
        this.mOverlayName = null;
    }

    public java.lang.String toString() {
        return this.mOverlayName == null ? this.mPackageName : this.mPackageName + ":" + this.mOverlayName;
    }

    public static android.content.om.OverlayIdentifier fromString(java.lang.String str) {
        java.lang.String[] split = str.split(":", 2);
        if (split.length == 2) {
            return new android.content.om.OverlayIdentifier(split[0], split[1]);
        }
        return new android.content.om.OverlayIdentifier(split[0]);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getOverlayName() {
        return this.mOverlayName;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.om.OverlayIdentifier overlayIdentifier = (android.content.om.OverlayIdentifier) obj;
        if (java.util.Objects.equals(this.mPackageName, overlayIdentifier.mPackageName) && java.util.Objects.equals(this.mOverlayName, overlayIdentifier.mOverlayName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((java.util.Objects.hashCode(this.mPackageName) + 31) * 31) + java.util.Objects.hashCode(this.mOverlayName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mPackageName != null ? (byte) 1 : (byte) 0;
        if (this.mOverlayName != null) {
            b = (byte) (b | 2);
        }
        parcel.writeByte(b);
        if (this.mPackageName != null) {
            parcel.writeString(this.mPackageName);
        }
        if (this.mOverlayName != null) {
            parcel.writeString(this.mOverlayName);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    OverlayIdentifier(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        java.lang.String readString = (readByte & 1) == 0 ? null : parcel.readString();
        java.lang.String readString2 = (readByte & 2) != 0 ? parcel.readString() : null;
        this.mPackageName = readString;
        this.mOverlayName = readString2;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
