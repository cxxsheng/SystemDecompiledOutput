package android.window;

/* loaded from: classes4.dex */
public class WindowContextInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.WindowContextInfo> CREATOR = new android.os.Parcelable.Creator<android.window.WindowContextInfo>() { // from class: android.window.WindowContextInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.WindowContextInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.WindowContextInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.WindowContextInfo[] newArray(int i) {
            return new android.window.WindowContextInfo[i];
        }
    };
    private final android.content.res.Configuration mConfiguration;
    private final int mDisplayId;

    public WindowContextInfo(android.content.res.Configuration configuration, int i) {
        this.mConfiguration = (android.content.res.Configuration) java.util.Objects.requireNonNull(configuration);
        this.mDisplayId = i;
    }

    public android.content.res.Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeInt(this.mDisplayId);
    }

    private WindowContextInfo(android.os.Parcel parcel) {
        this.mConfiguration = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
        this.mDisplayId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.window.WindowContextInfo windowContextInfo = (android.window.WindowContextInfo) obj;
        if (java.util.Objects.equals(this.mConfiguration, windowContextInfo.mConfiguration) && this.mDisplayId == windowContextInfo.mDisplayId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((527 + java.util.Objects.hashCode(this.mConfiguration)) * 31) + this.mDisplayId;
    }

    public java.lang.String toString() {
        return "WindowContextInfo{config=" + this.mConfiguration + ", displayId=" + this.mDisplayId + "}";
    }
}
