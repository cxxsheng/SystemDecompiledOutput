package android.app.slice;

/* loaded from: classes.dex */
public final class SliceSpec implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.slice.SliceSpec> CREATOR = new android.os.Parcelable.Creator<android.app.slice.SliceSpec>() { // from class: android.app.slice.SliceSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.slice.SliceSpec createFromParcel(android.os.Parcel parcel) {
            return new android.app.slice.SliceSpec(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.slice.SliceSpec[] newArray(int i) {
            return new android.app.slice.SliceSpec[i];
        }
    };
    private final int mRevision;
    private final java.lang.String mType;

    public SliceSpec(java.lang.String str, int i) {
        this.mType = str;
        this.mRevision = i;
    }

    public SliceSpec(android.os.Parcel parcel) {
        this.mType = parcel.readString();
        this.mRevision = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mType);
        parcel.writeInt(this.mRevision);
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public int getRevision() {
        return this.mRevision;
    }

    public boolean canRender(android.app.slice.SliceSpec sliceSpec) {
        return this.mType.equals(sliceSpec.mType) && this.mRevision >= sliceSpec.mRevision;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.app.slice.SliceSpec)) {
            return false;
        }
        android.app.slice.SliceSpec sliceSpec = (android.app.slice.SliceSpec) obj;
        return this.mType.equals(sliceSpec.mType) && this.mRevision == sliceSpec.mRevision;
    }

    public java.lang.String toString() {
        return java.lang.String.format("SliceSpec{%s,%d}", this.mType, java.lang.Integer.valueOf(this.mRevision));
    }
}
