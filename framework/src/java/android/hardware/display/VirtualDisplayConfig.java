package android.hardware.display;

/* loaded from: classes2.dex */
public final class VirtualDisplayConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.display.VirtualDisplayConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.VirtualDisplayConfig>() { // from class: android.hardware.display.VirtualDisplayConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.VirtualDisplayConfig[] newArray(int i) {
            return new android.hardware.display.VirtualDisplayConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.VirtualDisplayConfig createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.VirtualDisplayConfig(parcel);
        }
    };
    private final int mDensityDpi;
    private android.util.ArraySet<java.lang.String> mDisplayCategories;
    private final int mDisplayIdToMirror;
    private final int mFlags;
    private final int mHeight;
    private final boolean mIsHomeSupported;
    private final java.lang.String mName;
    private final float mRequestedRefreshRate;
    private final android.view.Surface mSurface;
    private final java.lang.String mUniqueId;
    private final int mWidth;
    private final boolean mWindowManagerMirroringEnabled;

    private VirtualDisplayConfig(java.lang.String str, int i, int i2, int i3, int i4, android.view.Surface surface, java.lang.String str2, int i5, boolean z, android.util.ArraySet<java.lang.String> arraySet, float f, boolean z2) {
        this.mDisplayCategories = null;
        this.mName = str;
        this.mWidth = i;
        this.mHeight = i2;
        this.mDensityDpi = i3;
        this.mFlags = i4;
        this.mSurface = surface;
        this.mUniqueId = str2;
        this.mDisplayIdToMirror = i5;
        this.mWindowManagerMirroringEnabled = z;
        this.mDisplayCategories = arraySet;
        this.mRequestedRefreshRate = f;
        this.mIsHomeSupported = z2;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getDensityDpi() {
        return this.mDensityDpi;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public android.view.Surface getSurface() {
        return this.mSurface;
    }

    public java.lang.String getUniqueId() {
        return this.mUniqueId;
    }

    public int getDisplayIdToMirror() {
        return this.mDisplayIdToMirror;
    }

    public boolean isWindowManagerMirroringEnabled() {
        return this.mWindowManagerMirroringEnabled;
    }

    @android.annotation.SystemApi
    public boolean isHomeSupported() {
        return android.companion.virtual.flags.Flags.vdmCustomHome() && this.mIsHomeSupported;
    }

    public java.util.Set<java.lang.String> getDisplayCategories() {
        return java.util.Collections.unmodifiableSet(this.mDisplayCategories);
    }

    public float getRequestedRefreshRate() {
        return this.mRequestedRefreshRate;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mName);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mDensityDpi);
        parcel.writeInt(this.mFlags);
        parcel.writeTypedObject(this.mSurface, i);
        parcel.writeString8(this.mUniqueId);
        parcel.writeInt(this.mDisplayIdToMirror);
        parcel.writeBoolean(this.mWindowManagerMirroringEnabled);
        parcel.writeArraySet(this.mDisplayCategories);
        parcel.writeFloat(this.mRequestedRefreshRate);
        parcel.writeBoolean(this.mIsHomeSupported);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.display.VirtualDisplayConfig)) {
            return false;
        }
        android.hardware.display.VirtualDisplayConfig virtualDisplayConfig = (android.hardware.display.VirtualDisplayConfig) obj;
        return java.util.Objects.equals(this.mName, virtualDisplayConfig.mName) && this.mWidth == virtualDisplayConfig.mWidth && this.mHeight == virtualDisplayConfig.mHeight && this.mDensityDpi == virtualDisplayConfig.mDensityDpi && this.mFlags == virtualDisplayConfig.mFlags && java.util.Objects.equals(this.mSurface, virtualDisplayConfig.mSurface) && java.util.Objects.equals(this.mUniqueId, virtualDisplayConfig.mUniqueId) && this.mDisplayIdToMirror == virtualDisplayConfig.mDisplayIdToMirror && this.mWindowManagerMirroringEnabled == virtualDisplayConfig.mWindowManagerMirroringEnabled && java.util.Objects.equals(this.mDisplayCategories, virtualDisplayConfig.mDisplayCategories) && this.mRequestedRefreshRate == virtualDisplayConfig.mRequestedRefreshRate && this.mIsHomeSupported == virtualDisplayConfig.mIsHomeSupported;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mName, java.lang.Integer.valueOf(this.mWidth), java.lang.Integer.valueOf(this.mHeight), java.lang.Integer.valueOf(this.mDensityDpi), java.lang.Integer.valueOf(this.mFlags), this.mSurface, this.mUniqueId, java.lang.Integer.valueOf(this.mDisplayIdToMirror), java.lang.Boolean.valueOf(this.mWindowManagerMirroringEnabled), this.mDisplayCategories, java.lang.Float.valueOf(this.mRequestedRefreshRate), java.lang.Boolean.valueOf(this.mIsHomeSupported));
    }

    public java.lang.String toString() {
        return "VirtualDisplayConfig( mName=" + this.mName + " mHeight=" + this.mHeight + " mWidth=" + this.mWidth + " mDensityDpi=" + this.mDensityDpi + " mFlags=" + this.mFlags + " mSurface=" + this.mSurface + " mUniqueId=" + this.mUniqueId + " mDisplayIdToMirror=" + this.mDisplayIdToMirror + " mWindowManagerMirroringEnabled=" + this.mWindowManagerMirroringEnabled + " mDisplayCategories=" + this.mDisplayCategories + " mRequestedRefreshRate=" + this.mRequestedRefreshRate + " mIsHomeSupported=" + this.mIsHomeSupported + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    private VirtualDisplayConfig(android.os.Parcel parcel) {
        this.mDisplayCategories = null;
        this.mName = parcel.readString8();
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mDensityDpi = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mSurface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
        this.mUniqueId = parcel.readString8();
        this.mDisplayIdToMirror = parcel.readInt();
        this.mWindowManagerMirroringEnabled = parcel.readBoolean();
        this.mDisplayCategories = parcel.readArraySet(null);
        this.mRequestedRefreshRate = parcel.readFloat();
        this.mIsHomeSupported = parcel.readBoolean();
    }

    public static final class Builder {
        private final int mDensityDpi;
        private final int mHeight;
        private final java.lang.String mName;
        private final int mWidth;
        private int mFlags = 0;
        private android.view.Surface mSurface = null;
        private java.lang.String mUniqueId = null;
        private int mDisplayIdToMirror = 0;
        private boolean mWindowManagerMirroringEnabled = false;
        private android.util.ArraySet<java.lang.String> mDisplayCategories = new android.util.ArraySet<>();
        private float mRequestedRefreshRate = 0.0f;
        private boolean mIsHomeSupported = false;

        public Builder(java.lang.String str, int i, int i2, int i3) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Virtual display name is required");
            }
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("Virtual display width must be positive");
            }
            if (i2 <= 0) {
                throw new java.lang.IllegalArgumentException("Virtual display height must be positive");
            }
            if (i3 <= 0) {
                throw new java.lang.IllegalArgumentException("Virtual display density must be positive");
            }
            this.mName = str;
            this.mWidth = i;
            this.mHeight = i2;
            this.mDensityDpi = i3;
        }

        public android.hardware.display.VirtualDisplayConfig.Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public android.hardware.display.VirtualDisplayConfig.Builder setSurface(android.view.Surface surface) {
            this.mSurface = surface;
            return this;
        }

        public android.hardware.display.VirtualDisplayConfig.Builder setUniqueId(java.lang.String str) {
            this.mUniqueId = str;
            return this;
        }

        public android.hardware.display.VirtualDisplayConfig.Builder setDisplayIdToMirror(int i) {
            this.mDisplayIdToMirror = i;
            return this;
        }

        public android.hardware.display.VirtualDisplayConfig.Builder setWindowManagerMirroringEnabled(boolean z) {
            this.mWindowManagerMirroringEnabled = z;
            return this;
        }

        public android.hardware.display.VirtualDisplayConfig.Builder setDisplayCategories(java.util.Set<java.lang.String> set) {
            this.mDisplayCategories.clear();
            this.mDisplayCategories.addAll((java.util.Collection<? extends java.lang.String>) java.util.Objects.requireNonNull(set));
            return this;
        }

        public android.hardware.display.VirtualDisplayConfig.Builder addDisplayCategory(java.lang.String str) {
            this.mDisplayCategories.add((java.lang.String) java.util.Objects.requireNonNull(str));
            return this;
        }

        public android.hardware.display.VirtualDisplayConfig.Builder setRequestedRefreshRate(float f) {
            if (f < 0.0f) {
                throw new java.lang.IllegalArgumentException("Virtual display requested refresh rate must be non-negative");
            }
            this.mRequestedRefreshRate = f;
            return this;
        }

        @android.annotation.SystemApi
        public android.hardware.display.VirtualDisplayConfig.Builder setHomeSupported(boolean z) {
            this.mIsHomeSupported = z;
            return this;
        }

        public android.hardware.display.VirtualDisplayConfig build() {
            return new android.hardware.display.VirtualDisplayConfig(this.mName, this.mWidth, this.mHeight, this.mDensityDpi, this.mFlags, this.mSurface, this.mUniqueId, this.mDisplayIdToMirror, this.mWindowManagerMirroringEnabled, this.mDisplayCategories, this.mRequestedRefreshRate, this.mIsHomeSupported);
        }
    }
}
