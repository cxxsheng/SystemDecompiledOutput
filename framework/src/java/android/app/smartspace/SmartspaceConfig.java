package android.app.smartspace;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SmartspaceConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.smartspace.SmartspaceConfig> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.SmartspaceConfig>() { // from class: android.app.smartspace.SmartspaceConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceConfig createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.SmartspaceConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceConfig[] newArray(int i) {
            return new android.app.smartspace.SmartspaceConfig[i];
        }
    };
    private final android.os.Bundle mExtras;
    private java.lang.String mPackageName;
    private final int mSmartspaceTargetCount;
    private final java.lang.String mUiSurface;

    private SmartspaceConfig(java.lang.String str, int i, java.lang.String str2, android.os.Bundle bundle) {
        this.mUiSurface = str;
        this.mSmartspaceTargetCount = i;
        this.mPackageName = str2;
        this.mExtras = bundle;
    }

    private SmartspaceConfig(android.os.Parcel parcel) {
        this.mUiSurface = parcel.readString();
        this.mSmartspaceTargetCount = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public int getSmartspaceTargetCount() {
        return this.mSmartspaceTargetCount;
    }

    public java.lang.String getUiSurface() {
        return this.mUiSurface;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mUiSurface);
        parcel.writeInt(this.mSmartspaceTargetCount);
        parcel.writeString(this.mPackageName);
        parcel.writeBundle(this.mExtras);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.smartspace.SmartspaceConfig smartspaceConfig = (android.app.smartspace.SmartspaceConfig) obj;
        if (this.mSmartspaceTargetCount == smartspaceConfig.mSmartspaceTargetCount && java.util.Objects.equals(this.mUiSurface, smartspaceConfig.mUiSurface) && java.util.Objects.equals(this.mPackageName, smartspaceConfig.mPackageName) && java.util.Objects.equals(this.mExtras, smartspaceConfig.mExtras)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSmartspaceTargetCount), this.mUiSurface, this.mPackageName, this.mExtras);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private final java.lang.String mPackageName;
        private final java.lang.String mUiSurface;
        private int mSmartspaceTargetCount = 5;
        private android.os.Bundle mExtras = android.os.Bundle.EMPTY;

        @android.annotation.SystemApi
        public Builder(android.content.Context context, java.lang.String str) {
            this.mPackageName = context.getPackageName();
            this.mUiSurface = str;
        }

        public android.app.smartspace.SmartspaceConfig.Builder setSmartspaceTargetCount(int i) {
            this.mSmartspaceTargetCount = i;
            return this;
        }

        public android.app.smartspace.SmartspaceConfig.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.app.smartspace.SmartspaceConfig build() {
            return new android.app.smartspace.SmartspaceConfig(this.mUiSurface, this.mSmartspaceTargetCount, this.mPackageName, this.mExtras);
        }
    }
}
