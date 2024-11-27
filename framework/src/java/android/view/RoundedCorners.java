package android.view;

/* loaded from: classes4.dex */
public class RoundedCorners implements android.os.Parcelable {
    public static final int ROUNDED_CORNER_POSITION_LENGTH = 4;
    private static int sCachedDisplayHeight;
    private static int sCachedDisplayWidth;
    private static float sCachedPhysicalPixelDisplaySizeRatio;
    private static android.util.Pair<java.lang.Integer, java.lang.Integer> sCachedRadii;
    private static android.view.RoundedCorners sCachedRoundedCorners;
    public final android.view.RoundedCorner[] mRoundedCorners;
    public static final android.view.RoundedCorners NO_ROUNDED_CORNERS = new android.view.RoundedCorners(new android.view.RoundedCorner(0), new android.view.RoundedCorner(1), new android.view.RoundedCorner(2), new android.view.RoundedCorner(3));
    private static final java.lang.Object CACHE_LOCK = new java.lang.Object();
    public static final android.os.Parcelable.Creator<android.view.RoundedCorners> CREATOR = new android.os.Parcelable.Creator<android.view.RoundedCorners>() { // from class: android.view.RoundedCorners.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RoundedCorners createFromParcel(android.os.Parcel parcel) {
            if (parcel.readInt() == 0) {
                return android.view.RoundedCorners.NO_ROUNDED_CORNERS;
            }
            android.view.RoundedCorner[] roundedCornerArr = new android.view.RoundedCorner[4];
            parcel.readTypedArray(roundedCornerArr, android.view.RoundedCorner.CREATOR);
            return new android.view.RoundedCorners(roundedCornerArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RoundedCorners[] newArray(int i) {
            return new android.view.RoundedCorners[i];
        }
    };

    public RoundedCorners(android.view.RoundedCorner[] roundedCornerArr) {
        this.mRoundedCorners = roundedCornerArr;
    }

    public RoundedCorners(android.view.RoundedCorner roundedCorner, android.view.RoundedCorner roundedCorner2, android.view.RoundedCorner roundedCorner3, android.view.RoundedCorner roundedCorner4) {
        this.mRoundedCorners = new android.view.RoundedCorner[4];
        this.mRoundedCorners[0] = roundedCorner;
        this.mRoundedCorners[1] = roundedCorner2;
        this.mRoundedCorners[2] = roundedCorner3;
        this.mRoundedCorners[3] = roundedCorner4;
    }

    public RoundedCorners(android.view.RoundedCorners roundedCorners) {
        this.mRoundedCorners = new android.view.RoundedCorner[4];
        for (int i = 0; i < 4; i++) {
            this.mRoundedCorners[i] = new android.view.RoundedCorner(roundedCorners.mRoundedCorners[i]);
        }
    }

    public static android.view.RoundedCorners fromResources(android.content.res.Resources resources, java.lang.String str, int i, int i2, int i3, int i4) {
        return fromRadii(loadRoundedCornerRadii(resources, str), i, i2, i3, i4);
    }

    public static android.view.RoundedCorners fromRadii(android.util.Pair<java.lang.Integer, java.lang.Integer> pair, int i, int i2) {
        return fromRadii(pair, i, i2, i, i2);
    }

    private static android.view.RoundedCorners fromRadii(android.util.Pair<java.lang.Integer, java.lang.Integer> pair, int i, int i2, int i3, int i4) {
        if (pair == null) {
            return null;
        }
        float physicalPixelDisplaySizeRatio = android.util.DisplayUtils.getPhysicalPixelDisplaySizeRatio(i, i2, i3, i4);
        synchronized (CACHE_LOCK) {
            if (pair.equals(sCachedRadii) && sCachedDisplayWidth == i3 && sCachedDisplayHeight == i4 && sCachedPhysicalPixelDisplaySizeRatio == physicalPixelDisplaySizeRatio) {
                return sCachedRoundedCorners;
            }
            android.view.RoundedCorner[] roundedCornerArr = new android.view.RoundedCorner[4];
            int i5 = 0;
            int intValue = pair.first.intValue() > 0 ? pair.first.intValue() : 0;
            int intValue2 = pair.second.intValue() > 0 ? pair.second.intValue() : 0;
            if (physicalPixelDisplaySizeRatio != 1.0f) {
                intValue = (int) ((intValue * physicalPixelDisplaySizeRatio) + 0.5d);
                intValue2 = (int) ((intValue2 * physicalPixelDisplaySizeRatio) + 0.5d);
            }
            while (i5 < 4) {
                roundedCornerArr[i5] = createRoundedCorner(i5, i5 <= 1 ? intValue : intValue2, i3, i4);
                i5++;
            }
            android.view.RoundedCorners roundedCorners = new android.view.RoundedCorners(roundedCornerArr);
            synchronized (CACHE_LOCK) {
                sCachedDisplayWidth = i3;
                sCachedDisplayHeight = i4;
                sCachedRadii = pair;
                sCachedRoundedCorners = roundedCorners;
                sCachedPhysicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
            }
            return roundedCorners;
        }
    }

    private static android.util.Pair<java.lang.Integer, java.lang.Integer> loadRoundedCornerRadii(android.content.res.Resources resources, java.lang.String str) {
        int roundedCornerRadius = getRoundedCornerRadius(resources, str);
        int roundedCornerTopRadius = getRoundedCornerTopRadius(resources, str);
        int roundedCornerBottomRadius = getRoundedCornerBottomRadius(resources, str);
        if (roundedCornerRadius == 0 && roundedCornerTopRadius == 0 && roundedCornerBottomRadius == 0) {
            return null;
        }
        if (roundedCornerTopRadius <= 0) {
            roundedCornerTopRadius = roundedCornerRadius;
        }
        java.lang.Integer valueOf = java.lang.Integer.valueOf(roundedCornerTopRadius);
        if (roundedCornerBottomRadius > 0) {
            roundedCornerRadius = roundedCornerBottomRadius;
        }
        return new android.util.Pair<>(valueOf, java.lang.Integer.valueOf(roundedCornerRadius));
    }

    public static int getRoundedCornerRadius(android.content.res.Resources resources, java.lang.String str) {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_roundedCornerRadiusArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            dimensionPixelSize = obtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.rounded_corner_radius);
        }
        obtainTypedArray.recycle();
        if (dimensionPixelSize == 0 && resources.getConfiguration().isScreenRound()) {
            return resources.getDisplayMetrics().widthPixels / 2;
        }
        return dimensionPixelSize;
    }

    public static int getRoundedCornerTopRadius(android.content.res.Resources resources, java.lang.String str) {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_roundedCornerTopRadiusArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            dimensionPixelSize = obtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.rounded_corner_radius_top);
        }
        obtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static int getRoundedCornerBottomRadius(android.content.res.Resources resources, java.lang.String str) {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_roundedCornerBottomRadiusArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            dimensionPixelSize = obtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.rounded_corner_radius_bottom);
        }
        obtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static int getRoundedCornerRadiusAdjustment(android.content.res.Resources resources, java.lang.String str) {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_roundedCornerRadiusAdjustmentArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            dimensionPixelSize = obtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.rounded_corner_radius_adjustment);
        }
        obtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static int getRoundedCornerRadiusTopAdjustment(android.content.res.Resources resources, java.lang.String str) {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_roundedCornerTopRadiusAdjustmentArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            dimensionPixelSize = obtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.rounded_corner_radius_top_adjustment);
        }
        obtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static int getRoundedCornerRadiusBottomAdjustment(android.content.res.Resources resources, java.lang.String str) {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_roundedCornerBottomRadiusAdjustmentArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            dimensionPixelSize = obtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.rounded_corner_radius_bottom_adjustment);
        }
        obtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static boolean getBuiltInDisplayIsRound(android.content.res.Resources resources, java.lang.String str) {
        boolean z;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_builtInDisplayIsRoundArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            z = obtainTypedArray.getBoolean(displayUniqueIdConfigIndex, false);
        } else {
            z = resources.getBoolean(com.android.internal.R.bool.config_mainBuiltInDisplayIsRound);
        }
        obtainTypedArray.recycle();
        return z;
    }

    public android.view.RoundedCorners insetWithFrame(android.graphics.Rect rect, android.graphics.Rect rect2) {
        int i;
        int i2;
        int i3;
        android.view.RoundedCorner[] roundedCornerArr;
        android.view.RoundedCorners roundedCorners = this;
        int i4 = rect.left - rect2.left;
        int i5 = rect.top - rect2.top;
        int i6 = rect2.right - rect.right;
        int i7 = rect2.bottom - rect.bottom;
        int i8 = 4;
        android.view.RoundedCorner[] roundedCornerArr2 = new android.view.RoundedCorner[4];
        int i9 = 0;
        while (i9 < i8) {
            if (roundedCorners.mRoundedCorners[i9].isEmpty()) {
                roundedCornerArr2[i9] = new android.view.RoundedCorner(i9);
                i3 = i9;
                roundedCornerArr = roundedCornerArr2;
            } else {
                int radius = roundedCorners.mRoundedCorners[i9].getRadius();
                switch (i9) {
                    case 0:
                        i = radius;
                        i2 = i;
                        break;
                    case 1:
                        i = rect2.width() - radius;
                        i2 = radius;
                        break;
                    case 2:
                        i = rect2.width() - radius;
                        i2 = rect2.height() - radius;
                        break;
                    case 3:
                        i2 = rect2.height() - radius;
                        i = radius;
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("The position is not one of the RoundedCornerPosition =" + i9);
                }
                i3 = i9;
                roundedCornerArr = roundedCornerArr2;
                roundedCornerArr[i3] = insetRoundedCorner(i9, radius, i, i2, i4, i5, i6, i7);
            }
            i9 = i3 + 1;
            roundedCornerArr2 = roundedCornerArr;
            i8 = 4;
            roundedCorners = this;
        }
        return new android.view.RoundedCorners(roundedCornerArr2);
    }

    public android.view.RoundedCorners inset(int i, int i2, int i3, int i4) {
        android.view.RoundedCorner[] roundedCornerArr = new android.view.RoundedCorner[4];
        for (int i5 = 0; i5 < 4; i5++) {
            roundedCornerArr[i5] = insetRoundedCorner(i5, this.mRoundedCorners[i5].getRadius(), this.mRoundedCorners[i5].getCenter().x, this.mRoundedCorners[i5].getCenter().y, i, i2, i3, i4);
        }
        return new android.view.RoundedCorners(roundedCornerArr);
    }

    private android.view.RoundedCorner insetRoundedCorner(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (this.mRoundedCorners[i].isEmpty()) {
            return new android.view.RoundedCorner(i);
        }
        boolean z = true;
        switch (i) {
            case 0:
                if (i2 <= i6 || i2 <= i5) {
                    z = false;
                    break;
                }
            case 1:
                if (i2 <= i6 || i2 <= i7) {
                    z = false;
                    break;
                }
            case 2:
                if (i2 <= i8 || i2 <= i7) {
                    z = false;
                    break;
                }
            case 3:
                if (i2 <= i8 || i2 <= i5) {
                    z = false;
                    break;
                }
            default:
                throw new java.lang.IllegalArgumentException("The position is not one of the RoundedCornerPosition =" + i);
        }
        return new android.view.RoundedCorner(i, i2, z ? i3 - i5 : 0, z ? i4 - i6 : 0);
    }

    public android.view.RoundedCorner getRoundedCorner(int i) {
        if (this.mRoundedCorners[i].isEmpty()) {
            return null;
        }
        return new android.view.RoundedCorner(this.mRoundedCorners[i]);
    }

    public void setRoundedCorner(int i, android.view.RoundedCorner roundedCorner) {
        android.view.RoundedCorner[] roundedCornerArr = this.mRoundedCorners;
        if (roundedCorner == null) {
            roundedCorner = new android.view.RoundedCorner(i);
        }
        roundedCornerArr[i] = roundedCorner;
    }

    public android.view.RoundedCorner[] getAllRoundedCorners() {
        android.view.RoundedCorner[] roundedCornerArr = new android.view.RoundedCorner[4];
        for (int i = 0; i < 4; i++) {
            roundedCornerArr[i] = new android.view.RoundedCorner(roundedCornerArr[i]);
        }
        return roundedCornerArr;
    }

    public android.view.RoundedCorners scale(float f) {
        if (f == 1.0f) {
            return this;
        }
        android.view.RoundedCorner[] roundedCornerArr = new android.view.RoundedCorner[4];
        for (int i = 0; i < 4; i++) {
            android.view.RoundedCorner roundedCorner = this.mRoundedCorners[i];
            roundedCornerArr[i] = new android.view.RoundedCorner(i, (int) (roundedCorner.getRadius() * f), (int) (roundedCorner.getCenter().x * f), (int) (roundedCorner.getCenter().y * f));
        }
        return new android.view.RoundedCorners(roundedCornerArr);
    }

    public android.view.RoundedCorners rotate(int i, int i2, int i3) {
        if (i == 0) {
            return this;
        }
        boolean z = true;
        if (i != 1 && i != 3) {
            z = false;
        }
        android.view.RoundedCorner[] roundedCornerArr = new android.view.RoundedCorner[4];
        for (int i4 = 0; i4 < this.mRoundedCorners.length; i4++) {
            int rotatedIndex = getRotatedIndex(i4, i);
            roundedCornerArr[rotatedIndex] = createRoundedCorner(rotatedIndex, this.mRoundedCorners[i4].getRadius(), z ? i3 : i2, z ? i2 : i3);
        }
        return new android.view.RoundedCorners(roundedCornerArr);
    }

    private static android.view.RoundedCorner createRoundedCorner(int i, int i2, int i3, int i4) {
        switch (i) {
            case 0:
                return new android.view.RoundedCorner(0, i2, i2 > 0 ? i2 : 0, i2 > 0 ? i2 : 0);
            case 1:
                return new android.view.RoundedCorner(1, i2, i2 > 0 ? i3 - i2 : 0, i2 > 0 ? i2 : 0);
            case 2:
                return new android.view.RoundedCorner(2, i2, i2 > 0 ? i3 - i2 : 0, i2 > 0 ? i4 - i2 : 0);
            case 3:
                return new android.view.RoundedCorner(3, i2, i2 > 0 ? i2 : 0, i2 > 0 ? i4 - i2 : 0);
            default:
                throw new java.lang.IllegalArgumentException("The position is not one of the RoundedCornerPosition =" + i);
        }
    }

    private static int getRotatedIndex(int i, int i2) {
        return ((i - i2) + 4) % 4;
    }

    public int hashCode() {
        int i = 0;
        for (android.view.RoundedCorner roundedCorner : this.mRoundedCorners) {
            i = (i * 31) + roundedCorner.hashCode();
        }
        return i;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof android.view.RoundedCorners) {
            return java.util.Arrays.deepEquals(this.mRoundedCorners, ((android.view.RoundedCorners) obj).mRoundedCorners);
        }
        return false;
    }

    public java.lang.String toString() {
        return "RoundedCorners{" + java.util.Arrays.toString(this.mRoundedCorners) + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (equals(NO_ROUNDED_CORNERS)) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeTypedArray(this.mRoundedCorners, i);
        }
    }
}
