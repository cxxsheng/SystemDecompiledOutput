package android.app.admin;

/* loaded from: classes.dex */
public final class UnsafeStateException extends java.lang.IllegalStateException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.UnsafeStateException> CREATOR = new android.os.Parcelable.Creator<android.app.admin.UnsafeStateException>() { // from class: android.app.admin.UnsafeStateException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.UnsafeStateException createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.UnsafeStateException(parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.UnsafeStateException[] newArray(int i) {
            return new android.app.admin.UnsafeStateException[i];
        }
    };
    private final int mOperation;
    private final int mReason;

    public UnsafeStateException(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgument(android.app.admin.DevicePolicyManager.isValidOperationSafetyReason(i2), "invalid reason %d", java.lang.Integer.valueOf(i2));
        this.mOperation = i;
        this.mReason = i2;
    }

    public int getOperation() {
        return this.mOperation;
    }

    public java.util.List<java.lang.Integer> getReasons() {
        return java.util.Arrays.asList(java.lang.Integer.valueOf(this.mReason));
    }

    @Override // java.lang.Throwable
    public java.lang.String getMessage() {
        return android.app.admin.DevicePolicyManager.operationSafetyReasonToString(this.mReason);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mOperation);
        parcel.writeInt(this.mReason);
    }
}
