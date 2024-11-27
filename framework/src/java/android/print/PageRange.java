package android.print;

/* loaded from: classes3.dex */
public final class PageRange implements android.os.Parcelable {
    public static final android.print.PageRange ALL_PAGES = new android.print.PageRange(0, Integer.MAX_VALUE);
    public static final android.print.PageRange[] ALL_PAGES_ARRAY = {ALL_PAGES};
    public static final android.os.Parcelable.Creator<android.print.PageRange> CREATOR = new android.os.Parcelable.Creator<android.print.PageRange>() { // from class: android.print.PageRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PageRange createFromParcel(android.os.Parcel parcel) {
            return new android.print.PageRange(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PageRange[] newArray(int i) {
            return new android.print.PageRange[i];
        }
    };
    private final int mEnd;
    private final int mStart;

    public PageRange(int i, int i2) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("start cannot be less than zero.");
        }
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("end cannot be less than zero.");
        }
        if (i > i2) {
            throw new java.lang.IllegalArgumentException("start must be lesser than end.");
        }
        this.mStart = i;
        this.mEnd = i2;
    }

    private PageRange(android.os.Parcel parcel) {
        this(parcel.readInt(), parcel.readInt());
    }

    public int getStart() {
        return this.mStart;
    }

    public int getEnd() {
        return this.mEnd;
    }

    public boolean contains(int i) {
        return i >= this.mStart && i <= this.mEnd;
    }

    public int getSize() {
        return (this.mEnd - this.mStart) + 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStart);
        parcel.writeInt(this.mEnd);
    }

    public int hashCode() {
        return ((this.mEnd + 31) * 31) + this.mStart;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.print.PageRange pageRange = (android.print.PageRange) obj;
        if (this.mEnd == pageRange.mEnd && this.mStart == pageRange.mStart) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        if (this.mStart == 0 && this.mEnd == Integer.MAX_VALUE) {
            return "PageRange[<all pages>]";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("PageRange[").append(this.mStart).append(" - ").append(this.mEnd).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
