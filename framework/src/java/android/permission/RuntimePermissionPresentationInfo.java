package android.permission;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RuntimePermissionPresentationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.permission.RuntimePermissionPresentationInfo> CREATOR = new android.os.Parcelable.Creator<android.permission.RuntimePermissionPresentationInfo>() { // from class: android.permission.RuntimePermissionPresentationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.permission.RuntimePermissionPresentationInfo createFromParcel(android.os.Parcel parcel) {
            java.lang.CharSequence readCharSequence = parcel.readCharSequence();
            int readInt = parcel.readInt();
            return new android.permission.RuntimePermissionPresentationInfo(readCharSequence, (readInt & 1) != 0, (readInt & 2) != 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.permission.RuntimePermissionPresentationInfo[] newArray(int i) {
            return new android.permission.RuntimePermissionPresentationInfo[i];
        }
    };
    private static final int FLAG_GRANTED = 1;
    private static final int FLAG_STANDARD = 2;
    private final int mFlags;
    private final java.lang.CharSequence mLabel;

    public RuntimePermissionPresentationInfo(java.lang.CharSequence charSequence, boolean z, boolean z2) {
        int i;
        com.android.internal.util.Preconditions.checkNotNull(charSequence);
        this.mLabel = charSequence;
        if (!z) {
            i = 0;
        } else {
            i = 1;
        }
        this.mFlags = z2 ? i | 2 : i;
    }

    public boolean isGranted() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isStandard() {
        return (this.mFlags & 2) != 0;
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeInt(this.mFlags);
    }
}
