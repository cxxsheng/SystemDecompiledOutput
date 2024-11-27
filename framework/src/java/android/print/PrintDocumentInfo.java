package android.print;

/* loaded from: classes3.dex */
public final class PrintDocumentInfo implements android.os.Parcelable {
    public static final int CONTENT_TYPE_DOCUMENT = 0;
    public static final int CONTENT_TYPE_PHOTO = 1;
    public static final int CONTENT_TYPE_UNKNOWN = -1;
    public static final android.os.Parcelable.Creator<android.print.PrintDocumentInfo> CREATOR = new android.os.Parcelable.Creator<android.print.PrintDocumentInfo>() { // from class: android.print.PrintDocumentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrintDocumentInfo createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrintDocumentInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrintDocumentInfo[] newArray(int i) {
            return new android.print.PrintDocumentInfo[i];
        }
    };
    public static final int PAGE_COUNT_UNKNOWN = -1;
    private int mContentType;
    private long mDataSize;
    private java.lang.String mName;
    private int mPageCount;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ContentType {
    }

    private PrintDocumentInfo() {
    }

    private PrintDocumentInfo(android.print.PrintDocumentInfo printDocumentInfo) {
        this.mName = printDocumentInfo.mName;
        this.mPageCount = printDocumentInfo.mPageCount;
        this.mContentType = printDocumentInfo.mContentType;
        this.mDataSize = printDocumentInfo.mDataSize;
    }

    private PrintDocumentInfo(android.os.Parcel parcel) {
        this.mName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(parcel.readString());
        this.mPageCount = parcel.readInt();
        com.android.internal.util.Preconditions.checkArgument(this.mPageCount == -1 || this.mPageCount > 0);
        this.mContentType = parcel.readInt();
        this.mDataSize = com.android.internal.util.Preconditions.checkArgumentNonnegative(parcel.readLong());
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getPageCount() {
        return this.mPageCount;
    }

    public int getContentType() {
        return this.mContentType;
    }

    public long getDataSize() {
        return this.mDataSize;
    }

    public void setDataSize(long j) {
        this.mDataSize = j;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mPageCount);
        parcel.writeInt(this.mContentType);
        parcel.writeLong(this.mDataSize);
    }

    public int hashCode() {
        return (((((((((this.mName != null ? this.mName.hashCode() : 0) + 31) * 31) + this.mContentType) * 31) + this.mPageCount) * 31) + ((int) this.mDataSize)) * 31) + ((int) (this.mDataSize >> 32));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.print.PrintDocumentInfo printDocumentInfo = (android.print.PrintDocumentInfo) obj;
        if (android.text.TextUtils.equals(this.mName, printDocumentInfo.mName) && this.mContentType == printDocumentInfo.mContentType && this.mPageCount == printDocumentInfo.mPageCount && this.mDataSize == printDocumentInfo.mDataSize) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("PrintDocumentInfo{");
        sb.append("name=").append(this.mName);
        sb.append(", pageCount=").append(this.mPageCount);
        sb.append(", contentType=").append(contentTypeToString(this.mContentType));
        sb.append(", dataSize=").append(this.mDataSize);
        sb.append("}");
        return sb.toString();
    }

    private java.lang.String contentTypeToString(int i) {
        switch (i) {
            case 0:
                return "CONTENT_TYPE_DOCUMENT";
            case 1:
                return "CONTENT_TYPE_PHOTO";
            default:
                return "CONTENT_TYPE_UNKNOWN";
        }
    }

    public static final class Builder {
        private final android.print.PrintDocumentInfo mPrototype;

        public Builder(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("name cannot be empty");
            }
            this.mPrototype = new android.print.PrintDocumentInfo();
            this.mPrototype.mName = str;
        }

        public android.print.PrintDocumentInfo.Builder setPageCount(int i) {
            if (i < 0 && i != -1) {
                throw new java.lang.IllegalArgumentException("pageCount must be greater than or equal to zero or DocumentInfo#PAGE_COUNT_UNKNOWN");
            }
            this.mPrototype.mPageCount = i;
            return this;
        }

        public android.print.PrintDocumentInfo.Builder setContentType(int i) {
            this.mPrototype.mContentType = i;
            return this;
        }

        public android.print.PrintDocumentInfo build() {
            if (this.mPrototype.mPageCount == 0) {
                this.mPrototype.mPageCount = -1;
            }
            return new android.print.PrintDocumentInfo();
        }
    }
}
