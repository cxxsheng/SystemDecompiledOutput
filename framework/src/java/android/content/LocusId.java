package android.content;

/* loaded from: classes.dex */
public final class LocusId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.LocusId> CREATOR = new android.os.Parcelable.Creator<android.content.LocusId>() { // from class: android.content.LocusId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.LocusId createFromParcel(android.os.Parcel parcel) {
            return new android.content.LocusId(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.LocusId[] newArray(int i) {
            return new android.content.LocusId[i];
        }
    };
    private final java.lang.String mId;

    public LocusId(java.lang.String str) {
        this.mId = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "id cannot be empty");
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public int hashCode() {
        return 31 + (this.mId == null ? 0 : this.mId.hashCode());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.LocusId locusId = (android.content.LocusId) obj;
        if (this.mId == null) {
            if (locusId.mId != null) {
                return false;
            }
        } else if (!this.mId.equals(locusId.mId)) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        return "LocusId[" + getSanitizedId() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.print("id:");
        printWriter.println(getSanitizedId());
    }

    private java.lang.String getSanitizedId() {
        return this.mId.length() + "_chars";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
    }
}
