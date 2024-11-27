package android.view;

/* loaded from: classes4.dex */
public class InsetsFrameProvider implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.InsetsFrameProvider> CREATOR = new android.os.Parcelable.Creator<android.view.InsetsFrameProvider>() { // from class: android.view.InsetsFrameProvider.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InsetsFrameProvider createFromParcel(android.os.Parcel parcel) {
            return new android.view.InsetsFrameProvider(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InsetsFrameProvider[] newArray(int i) {
            return new android.view.InsetsFrameProvider[i];
        }
    };
    public static final int SOURCE_ARBITRARY_RECTANGLE = 3;
    public static final int SOURCE_CONTAINER_BOUNDS = 1;
    public static final int SOURCE_DISPLAY = 0;
    public static final int SOURCE_FRAME = 2;
    private android.graphics.Rect mArbitraryRectangle;
    private android.graphics.Rect[] mBoundingRects;
    private int mFlags;
    private final int mId;
    private android.graphics.Insets mInsetsSize;
    private android.view.InsetsFrameProvider.InsetsSizeOverride[] mInsetsSizeOverrides;
    private android.graphics.Insets mMinimalInsetsSizeInDisplayCutoutSafe;
    private int mSource;

    public InsetsFrameProvider(java.lang.Object obj, int i, int i2) {
        this.mSource = 2;
        this.mInsetsSize = null;
        this.mInsetsSizeOverrides = null;
        this.mMinimalInsetsSizeInDisplayCutoutSafe = null;
        this.mBoundingRects = null;
        this.mId = android.view.InsetsSource.createId(obj, i, i2);
    }

    public int getId() {
        return this.mId;
    }

    public int getIndex() {
        return android.view.InsetsSource.getIndex(this.mId);
    }

    public int getType() {
        return android.view.InsetsSource.getType(this.mId);
    }

    public android.view.InsetsFrameProvider setSource(int i) {
        this.mSource = i;
        return this;
    }

    public int getSource() {
        return this.mSource;
    }

    public android.view.InsetsFrameProvider setFlags(int i, int i2) {
        this.mFlags = (i & i2) | (this.mFlags & (~i2));
        return this;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean hasFlags(int i) {
        return (this.mFlags & i) == i;
    }

    public android.view.InsetsFrameProvider setInsetsSize(android.graphics.Insets insets) {
        this.mInsetsSize = insets;
        return this;
    }

    public android.graphics.Insets getInsetsSize() {
        return this.mInsetsSize;
    }

    public android.view.InsetsFrameProvider setArbitraryRectangle(android.graphics.Rect rect) {
        this.mArbitraryRectangle = new android.graphics.Rect(rect);
        return this;
    }

    public android.graphics.Rect getArbitraryRectangle() {
        return this.mArbitraryRectangle;
    }

    public android.view.InsetsFrameProvider setInsetsSizeOverrides(android.view.InsetsFrameProvider.InsetsSizeOverride[] insetsSizeOverrideArr) {
        this.mInsetsSizeOverrides = insetsSizeOverrideArr;
        return this;
    }

    public android.view.InsetsFrameProvider.InsetsSizeOverride[] getInsetsSizeOverrides() {
        return this.mInsetsSizeOverrides;
    }

    public android.view.InsetsFrameProvider setMinimalInsetsSizeInDisplayCutoutSafe(android.graphics.Insets insets) {
        this.mMinimalInsetsSizeInDisplayCutoutSafe = insets;
        return this;
    }

    public android.graphics.Insets getMinimalInsetsSizeInDisplayCutoutSafe() {
        return this.mMinimalInsetsSizeInDisplayCutoutSafe;
    }

    public android.view.InsetsFrameProvider setBoundingRects(android.graphics.Rect[] rectArr) {
        this.mBoundingRects = rectArr == null ? null : (android.graphics.Rect[]) rectArr.clone();
        return this;
    }

    public android.graphics.Rect[] getBoundingRects() {
        return this.mBoundingRects;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("InsetsFrameProvider: {");
        sb.append("id=#").append(java.lang.Integer.toHexString(this.mId));
        sb.append(", index=").append(getIndex());
        sb.append(", type=").append(android.view.WindowInsets.Type.toString(getType()));
        sb.append(", source=").append(sourceToString(this.mSource));
        sb.append(", flags=[").append(android.view.InsetsSource.flagsToString(this.mFlags)).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        if (this.mInsetsSize != null) {
            sb.append(", insetsSize=").append(this.mInsetsSize);
        }
        if (this.mInsetsSizeOverrides != null) {
            sb.append(", insetsSizeOverrides=").append(java.util.Arrays.toString(this.mInsetsSizeOverrides));
        }
        if (this.mArbitraryRectangle != null) {
            sb.append(", mArbitraryRectangle=").append(this.mArbitraryRectangle.toShortString());
        }
        if (this.mMinimalInsetsSizeInDisplayCutoutSafe != null) {
            sb.append(", mMinimalInsetsSizeInDisplayCutoutSafe=").append(this.mMinimalInsetsSizeInDisplayCutoutSafe);
        }
        if (this.mBoundingRects != null) {
            sb.append(", mBoundingRects=").append(java.util.Arrays.toString(this.mBoundingRects));
        }
        sb.append("}");
        return sb.toString();
    }

    private static java.lang.String sourceToString(int i) {
        switch (i) {
            case 0:
                return "DISPLAY";
            case 1:
                return "CONTAINER_BOUNDS";
            case 2:
                return "FRAME";
            case 3:
                return "ARBITRARY_RECTANGLE";
            default:
                return android.app.admin.DevicePolicyResources.UNDEFINED;
        }
    }

    public InsetsFrameProvider(android.os.Parcel parcel) {
        this.mSource = 2;
        this.mInsetsSize = null;
        this.mInsetsSizeOverrides = null;
        this.mMinimalInsetsSizeInDisplayCutoutSafe = null;
        this.mBoundingRects = null;
        this.mId = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mInsetsSize = (android.graphics.Insets) parcel.readTypedObject(android.graphics.Insets.CREATOR);
        this.mInsetsSizeOverrides = (android.view.InsetsFrameProvider.InsetsSizeOverride[]) parcel.createTypedArray(android.view.InsetsFrameProvider.InsetsSizeOverride.CREATOR);
        this.mArbitraryRectangle = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.mMinimalInsetsSizeInDisplayCutoutSafe = (android.graphics.Insets) parcel.readTypedObject(android.graphics.Insets.CREATOR);
        this.mBoundingRects = (android.graphics.Rect[]) parcel.createTypedArray(android.graphics.Rect.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mFlags);
        parcel.writeTypedObject(this.mInsetsSize, i);
        parcel.writeTypedArray(this.mInsetsSizeOverrides, i);
        parcel.writeTypedObject(this.mArbitraryRectangle, i);
        parcel.writeTypedObject(this.mMinimalInsetsSizeInDisplayCutoutSafe, i);
        parcel.writeTypedArray(this.mBoundingRects, i);
    }

    public boolean idEquals(android.view.InsetsFrameProvider insetsFrameProvider) {
        return this.mId == insetsFrameProvider.mId;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.InsetsFrameProvider insetsFrameProvider = (android.view.InsetsFrameProvider) obj;
        if (this.mId == insetsFrameProvider.mId && this.mSource == insetsFrameProvider.mSource && this.mFlags == insetsFrameProvider.mFlags && java.util.Objects.equals(this.mInsetsSize, insetsFrameProvider.mInsetsSize) && java.util.Arrays.equals(this.mInsetsSizeOverrides, insetsFrameProvider.mInsetsSizeOverrides) && java.util.Objects.equals(this.mArbitraryRectangle, insetsFrameProvider.mArbitraryRectangle) && java.util.Objects.equals(this.mMinimalInsetsSizeInDisplayCutoutSafe, insetsFrameProvider.mMinimalInsetsSizeInDisplayCutoutSafe) && java.util.Arrays.equals(this.mBoundingRects, insetsFrameProvider.mBoundingRects)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), java.lang.Integer.valueOf(this.mSource), java.lang.Integer.valueOf(this.mFlags), this.mInsetsSize, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mInsetsSizeOverrides)), this.mArbitraryRectangle, this.mMinimalInsetsSizeInDisplayCutoutSafe, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mBoundingRects)));
    }

    public static class InsetsSizeOverride implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.InsetsFrameProvider.InsetsSizeOverride> CREATOR = new android.os.Parcelable.Creator<android.view.InsetsFrameProvider.InsetsSizeOverride>() { // from class: android.view.InsetsFrameProvider.InsetsSizeOverride.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.InsetsFrameProvider.InsetsSizeOverride createFromParcel(android.os.Parcel parcel) {
                return new android.view.InsetsFrameProvider.InsetsSizeOverride(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.InsetsFrameProvider.InsetsSizeOverride[] newArray(int i) {
                return new android.view.InsetsFrameProvider.InsetsSizeOverride[i];
            }
        };
        private final android.graphics.Insets mInsetsSize;
        private final int mWindowType;

        protected InsetsSizeOverride(android.os.Parcel parcel) {
            this.mWindowType = parcel.readInt();
            this.mInsetsSize = (android.graphics.Insets) parcel.readTypedObject(android.graphics.Insets.CREATOR);
        }

        public InsetsSizeOverride(int i, android.graphics.Insets insets) {
            this.mWindowType = i;
            this.mInsetsSize = insets;
        }

        public int getWindowType() {
            return this.mWindowType;
        }

        public android.graphics.Insets getInsetsSize() {
            return this.mInsetsSize;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mWindowType);
            parcel.writeTypedObject(this.mInsetsSize, i);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(32);
            sb.append("TypedInsetsSize: {");
            sb.append("windowType=").append(android.view.ViewDebug.intToString(android.view.WindowManager.LayoutParams.class, "type", this.mWindowType));
            sb.append(", insetsSize=").append(this.mInsetsSize);
            sb.append("}");
            return sb.toString();
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mWindowType), this.mInsetsSize);
        }
    }
}
