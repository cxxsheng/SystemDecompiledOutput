package android.app;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class Vr2dDisplayProperties implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.Vr2dDisplayProperties> CREATOR = new android.os.Parcelable.Creator<android.app.Vr2dDisplayProperties>() { // from class: android.app.Vr2dDisplayProperties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.Vr2dDisplayProperties createFromParcel(android.os.Parcel parcel) {
            return new android.app.Vr2dDisplayProperties(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.Vr2dDisplayProperties[] newArray(int i) {
            return new android.app.Vr2dDisplayProperties[i];
        }
    };
    public static final int FLAG_VIRTUAL_DISPLAY_ENABLED = 1;
    private final int mAddedFlags;
    private final int mDpi;
    private final int mHeight;
    private final int mRemovedFlags;
    private final int mWidth;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Vr2dDisplayFlag {
    }

    public Vr2dDisplayProperties(int i, int i2, int i3) {
        this(i, i2, i3, 0, 0);
    }

    private Vr2dDisplayProperties(int i, int i2, int i3, int i4, int i5) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mDpi = i3;
        this.mAddedFlags = i4;
        this.mRemovedFlags = i5;
    }

    public int hashCode() {
        return (((getWidth() * 31) + getHeight()) * 31) + getDpi();
    }

    public java.lang.String toString() {
        return "Vr2dDisplayProperties{mWidth=" + this.mWidth + ", mHeight=" + this.mHeight + ", mDpi=" + this.mDpi + ", flags=" + toReadableFlags(this.mAddedFlags) + ", removed_flags=" + toReadableFlags(this.mRemovedFlags) + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.Vr2dDisplayProperties vr2dDisplayProperties = (android.app.Vr2dDisplayProperties) obj;
        if (getAddedFlags() == vr2dDisplayProperties.getAddedFlags() && getRemovedFlags() == vr2dDisplayProperties.getRemovedFlags() && getWidth() == vr2dDisplayProperties.getWidth() && getHeight() == vr2dDisplayProperties.getHeight() && getDpi() == vr2dDisplayProperties.getDpi()) {
            return true;
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mDpi);
        parcel.writeInt(this.mAddedFlags);
        parcel.writeInt(this.mRemovedFlags);
    }

    private Vr2dDisplayProperties(android.os.Parcel parcel) {
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mDpi = parcel.readInt();
        this.mAddedFlags = parcel.readInt();
        this.mRemovedFlags = parcel.readInt();
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + toString());
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getDpi() {
        return this.mDpi;
    }

    public int getAddedFlags() {
        return this.mAddedFlags;
    }

    public int getRemovedFlags() {
        return this.mRemovedFlags;
    }

    private static java.lang.String toReadableFlags(int i) {
        return ((i & 1) == 1 ? "{enabled" : "{") + "}";
    }

    public static final class Builder {
        private int mAddedFlags = 0;
        private int mRemovedFlags = 0;
        private int mWidth = -1;
        private int mHeight = -1;
        private int mDpi = -1;

        public android.app.Vr2dDisplayProperties.Builder setDimensions(int i, int i2, int i3) {
            this.mWidth = i;
            this.mHeight = i2;
            this.mDpi = i3;
            return this;
        }

        public android.app.Vr2dDisplayProperties.Builder setEnabled(boolean z) {
            if (z) {
                addFlags(1);
            } else {
                removeFlags(1);
            }
            return this;
        }

        public android.app.Vr2dDisplayProperties.Builder addFlags(int i) {
            this.mAddedFlags |= i;
            this.mRemovedFlags = (~i) & this.mRemovedFlags;
            return this;
        }

        public android.app.Vr2dDisplayProperties.Builder removeFlags(int i) {
            this.mRemovedFlags |= i;
            this.mAddedFlags = (~i) & this.mAddedFlags;
            return this;
        }

        public android.app.Vr2dDisplayProperties build() {
            return new android.app.Vr2dDisplayProperties(this.mWidth, this.mHeight, this.mDpi, this.mAddedFlags, this.mRemovedFlags);
        }
    }
}
