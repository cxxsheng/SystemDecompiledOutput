package android.view;

/* loaded from: classes4.dex */
public final class DisplayShape implements android.os.Parcelable {
    private final int mDisplayHeight;
    public final java.lang.String mDisplayShapeSpec;
    private final int mDisplayWidth;
    private final int mOffsetX;
    private final int mOffsetY;
    private final float mPhysicalPixelDisplaySizeRatio;
    private final int mRotation;
    private final float mScale;
    public static final android.view.DisplayShape NONE = new android.view.DisplayShape("", 0, 0, 0.0f, 0);
    public static final android.os.Parcelable.Creator<android.view.DisplayShape> CREATOR = new android.os.Parcelable.Creator<android.view.DisplayShape>() { // from class: android.view.DisplayShape.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.DisplayShape createFromParcel(android.os.Parcel parcel) {
            return new android.view.DisplayShape(parcel.readString8(), parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readFloat());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.DisplayShape[] newArray(int i) {
            return new android.view.DisplayShape[i];
        }
    };

    private DisplayShape(java.lang.String str, int i, int i2, float f, int i3) {
        this(str, i, i2, f, i3, 0, 0, 1.0f);
    }

    private DisplayShape(java.lang.String str, int i, int i2, float f, int i3, int i4, int i5, float f2) {
        this.mDisplayShapeSpec = str;
        this.mDisplayWidth = i;
        this.mDisplayHeight = i2;
        this.mPhysicalPixelDisplaySizeRatio = f;
        this.mRotation = i3;
        this.mOffsetX = i4;
        this.mOffsetY = i5;
        this.mScale = f2;
    }

    public static android.view.DisplayShape fromResources(android.content.res.Resources resources, java.lang.String str, int i, int i2, int i3, int i4) {
        boolean builtInDisplayIsRound = android.view.RoundedCorners.getBuiltInDisplayIsRound(resources, str);
        java.lang.String specString = getSpecString(resources, str);
        if (specString == null || specString.isEmpty()) {
            return createDefaultDisplayShape(i3, i4, builtInDisplayIsRound);
        }
        return fromSpecString(specString, android.util.DisplayUtils.getPhysicalPixelDisplaySizeRatio(i, i2, i3, i4), i3, i4);
    }

    public static android.view.DisplayShape createDefaultDisplayShape(int i, int i2, boolean z) {
        return fromSpecString(createDefaultSpecString(i, i2, z), 1.0f, i, i2);
    }

    public static android.view.DisplayShape fromSpecString(java.lang.String str, float f, int i, int i2) {
        return android.view.DisplayShape.Cache.getDisplayShape(str, f, i, i2);
    }

    private static java.lang.String createDefaultSpecString(int i, int i2, boolean z) {
        if (!z) {
            return "M0,0 L" + i + ",0 L" + i + "," + i2 + " L0," + i2 + " Z";
        }
        float f = i / 2.0f;
        float f2 = i2 / 2.0f;
        return "M0," + f2 + " A" + f + "," + f2 + " 0 1,1 " + i + "," + f2 + " A" + f + "," + f2 + " 0 1,1 0," + f2 + " Z";
    }

    public static java.lang.String getSpecString(android.content.res.Resources resources, java.lang.String str) {
        java.lang.String string;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_displayShapeArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            string = obtainTypedArray.getString(displayUniqueIdConfigIndex);
        } else {
            string = resources.getString(com.android.internal.R.string.config_mainDisplayShape);
        }
        obtainTypedArray.recycle();
        return string;
    }

    public android.view.DisplayShape setRotation(int i) {
        return new android.view.DisplayShape(this.mDisplayShapeSpec, this.mDisplayWidth, this.mDisplayHeight, this.mPhysicalPixelDisplaySizeRatio, i, this.mOffsetX, this.mOffsetY, this.mScale);
    }

    public android.view.DisplayShape setOffset(int i, int i2) {
        return new android.view.DisplayShape(this.mDisplayShapeSpec, this.mDisplayWidth, this.mDisplayHeight, this.mPhysicalPixelDisplaySizeRatio, this.mRotation, i, i2, this.mScale);
    }

    public android.view.DisplayShape setScale(float f) {
        return new android.view.DisplayShape(this.mDisplayShapeSpec, this.mDisplayWidth, this.mDisplayHeight, this.mPhysicalPixelDisplaySizeRatio, this.mRotation, this.mOffsetX, this.mOffsetY, f);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mDisplayShapeSpec, java.lang.Integer.valueOf(this.mDisplayWidth), java.lang.Integer.valueOf(this.mDisplayHeight), java.lang.Float.valueOf(this.mPhysicalPixelDisplaySizeRatio), java.lang.Integer.valueOf(this.mRotation), java.lang.Integer.valueOf(this.mOffsetX), java.lang.Integer.valueOf(this.mOffsetY), java.lang.Float.valueOf(this.mScale));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.view.DisplayShape)) {
            return false;
        }
        android.view.DisplayShape displayShape = (android.view.DisplayShape) obj;
        return java.util.Objects.equals(this.mDisplayShapeSpec, displayShape.mDisplayShapeSpec) && this.mDisplayWidth == displayShape.mDisplayWidth && this.mDisplayHeight == displayShape.mDisplayHeight && this.mPhysicalPixelDisplaySizeRatio == displayShape.mPhysicalPixelDisplaySizeRatio && this.mRotation == displayShape.mRotation && this.mOffsetX == displayShape.mOffsetX && this.mOffsetY == displayShape.mOffsetY && this.mScale == displayShape.mScale;
    }

    public java.lang.String toString() {
        return "DisplayShape{ spec=" + this.mDisplayShapeSpec.hashCode() + " displayWidth=" + this.mDisplayWidth + " displayHeight=" + this.mDisplayHeight + " physicalPixelDisplaySizeRatio=" + this.mPhysicalPixelDisplaySizeRatio + " rotation=" + this.mRotation + " offsetX=" + this.mOffsetX + " offsetY=" + this.mOffsetY + " scale=" + this.mScale + "}";
    }

    public android.graphics.Path getPath() {
        return android.view.DisplayShape.Cache.getPath(this);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mDisplayShapeSpec);
        parcel.writeInt(this.mDisplayWidth);
        parcel.writeInt(this.mDisplayHeight);
        parcel.writeFloat(this.mPhysicalPixelDisplaySizeRatio);
        parcel.writeInt(this.mRotation);
        parcel.writeInt(this.mOffsetX);
        parcel.writeInt(this.mOffsetY);
        parcel.writeFloat(this.mScale);
    }

    private static final class Cache {
        private static final java.lang.Object CACHE_LOCK = new java.lang.Object();
        private static android.view.DisplayShape sCacheForPath;
        private static int sCachedDisplayHeight;
        private static android.view.DisplayShape sCachedDisplayShape;
        private static int sCachedDisplayWidth;
        private static android.graphics.Path sCachedPath;
        private static float sCachedPhysicalPixelDisplaySizeRatio;
        private static java.lang.String sCachedSpec;

        private Cache() {
        }

        static android.view.DisplayShape getDisplayShape(java.lang.String str, float f, int i, int i2) {
            synchronized (CACHE_LOCK) {
                if (str.equals(sCachedSpec) && sCachedDisplayWidth == i && sCachedDisplayHeight == i2 && sCachedPhysicalPixelDisplaySizeRatio == f) {
                    return sCachedDisplayShape;
                }
                android.view.DisplayShape displayShape = new android.view.DisplayShape(str, i, i2, f, 0);
                synchronized (CACHE_LOCK) {
                    sCachedSpec = str;
                    sCachedDisplayWidth = i;
                    sCachedDisplayHeight = i2;
                    sCachedPhysicalPixelDisplaySizeRatio = f;
                    sCachedDisplayShape = displayShape;
                }
                return displayShape;
            }
        }

        static android.graphics.Path getPath(android.view.DisplayShape displayShape) {
            synchronized (CACHE_LOCK) {
                if (displayShape.equals(sCacheForPath)) {
                    return sCachedPath;
                }
                android.graphics.Path createPathFromPathData = android.util.PathParser.createPathFromPathData(displayShape.mDisplayShapeSpec);
                if (!createPathFromPathData.isEmpty()) {
                    android.graphics.Matrix matrix = new android.graphics.Matrix();
                    if (displayShape.mRotation != 0) {
                        android.util.RotationUtils.transformPhysicalToLogicalCoordinates(displayShape.mRotation, displayShape.mDisplayWidth, displayShape.mDisplayHeight, matrix);
                    }
                    if (displayShape.mPhysicalPixelDisplaySizeRatio != 1.0f) {
                        matrix.preScale(displayShape.mPhysicalPixelDisplaySizeRatio, displayShape.mPhysicalPixelDisplaySizeRatio);
                    }
                    if (displayShape.mOffsetX != 0 || displayShape.mOffsetY != 0) {
                        matrix.postTranslate(displayShape.mOffsetX, displayShape.mOffsetY);
                    }
                    if (displayShape.mScale != 1.0f) {
                        matrix.postScale(displayShape.mScale, displayShape.mScale);
                    }
                    createPathFromPathData.transform(matrix);
                }
                synchronized (CACHE_LOCK) {
                    sCacheForPath = displayShape;
                    sCachedPath = createPathFromPathData;
                }
                return createPathFromPathData;
            }
        }
    }
}
