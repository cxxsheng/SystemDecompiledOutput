package android.print;

/* loaded from: classes3.dex */
public final class PrinterId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.print.PrinterId> CREATOR = new android.os.Parcelable.Creator<android.print.PrinterId>() { // from class: android.print.PrinterId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrinterId createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrinterId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrinterId[] newArray(int i) {
            return new android.print.PrinterId[i];
        }
    };
    private final java.lang.String mLocalId;
    private final android.content.ComponentName mServiceName;

    public PrinterId(android.content.ComponentName componentName, java.lang.String str) {
        this.mServiceName = componentName;
        this.mLocalId = str;
    }

    private PrinterId(android.os.Parcel parcel) {
        this.mServiceName = (android.content.ComponentName) com.android.internal.util.Preconditions.checkNotNull((android.content.ComponentName) parcel.readParcelable(null, android.content.ComponentName.class));
        this.mLocalId = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(parcel.readString());
    }

    public android.content.ComponentName getServiceName() {
        return this.mServiceName;
    }

    public java.lang.String getLocalId() {
        return this.mLocalId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mServiceName, i);
        parcel.writeString(this.mLocalId);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.print.PrinterId printerId = (android.print.PrinterId) obj;
        if (this.mServiceName.equals(printerId.mServiceName) && this.mLocalId.equals(printerId.mLocalId)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.mServiceName.hashCode() + 31) * 31) + this.mLocalId.hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("PrinterId{");
        sb.append("serviceName=").append(this.mServiceName.flattenToString());
        sb.append(", localId=").append(this.mLocalId);
        sb.append('}');
        return sb.toString();
    }
}
