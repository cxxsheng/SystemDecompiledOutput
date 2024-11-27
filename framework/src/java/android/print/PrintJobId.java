package android.print;

/* loaded from: classes3.dex */
public final class PrintJobId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.print.PrintJobId> CREATOR = new android.os.Parcelable.Creator<android.print.PrintJobId>() { // from class: android.print.PrintJobId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrintJobId createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrintJobId((java.lang.String) com.android.internal.util.Preconditions.checkNotNull(parcel.readString()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrintJobId[] newArray(int i) {
            return new android.print.PrintJobId[i];
        }
    };
    private final java.lang.String mValue;

    public PrintJobId() {
        this(java.util.UUID.randomUUID().toString());
    }

    public PrintJobId(java.lang.String str) {
        this.mValue = str;
    }

    public int hashCode() {
        return 31 + this.mValue.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mValue.equals(((android.print.PrintJobId) obj).mValue)) {
            return true;
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mValue);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String flattenToString() {
        return this.mValue;
    }

    public static android.print.PrintJobId unflattenFromString(java.lang.String str) {
        return new android.print.PrintJobId(str);
    }
}
