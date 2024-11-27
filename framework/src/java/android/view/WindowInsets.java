package android.view;

/* loaded from: classes4.dex */
public final class WindowInsets {
    public static final android.view.WindowInsets CONSUMED = new android.view.WindowInsets(createCompatTypeMap(null), createCompatTypeMap(null), createCompatVisibilityMap(createCompatTypeMap(null)), false, 0, 0, null, null, null, null, android.view.WindowInsets.Type.systemBars(), false, null, null, 0, 0);
    private final boolean mCompatIgnoreVisibility;
    private final int mCompatInsetsTypes;
    private final android.view.DisplayCutout mDisplayCutout;
    private final boolean mDisplayCutoutConsumed;
    private final android.view.DisplayShape mDisplayShape;
    private final int mForceConsumingTypes;
    private final int mFrameHeight;
    private final int mFrameWidth;
    private final boolean mIsRound;
    private final android.view.PrivacyIndicatorBounds mPrivacyIndicatorBounds;
    private final android.view.RoundedCorners mRoundedCorners;
    private final boolean mStableInsetsConsumed;
    private final int mSuppressScrimTypes;
    private final boolean mSystemWindowInsetsConsumed;
    private android.graphics.Rect mTempRect;
    private final android.graphics.Rect[][] mTypeBoundingRectsMap;
    private final android.graphics.Insets[] mTypeInsetsMap;
    private final android.graphics.Rect[][] mTypeMaxBoundingRectsMap;
    private final android.graphics.Insets[] mTypeMaxInsetsMap;
    private final boolean[] mTypeVisibilityMap;

    public WindowInsets(android.graphics.Insets[] insetsArr, android.graphics.Insets[] insetsArr2, boolean[] zArr, boolean z, int i, int i2, android.view.DisplayCutout displayCutout, android.view.RoundedCorners roundedCorners, android.view.PrivacyIndicatorBounds privacyIndicatorBounds, android.view.DisplayShape displayShape, int i3, boolean z2, android.graphics.Rect[][] rectArr, android.graphics.Rect[][] rectArr2, int i4, int i5) {
        android.graphics.Insets[] insetsArr3;
        android.graphics.Insets[] insetsArr4;
        android.graphics.Rect[][] rectArr3;
        android.graphics.Rect[][] rectArr4;
        this.mSystemWindowInsetsConsumed = insetsArr == null;
        if (this.mSystemWindowInsetsConsumed) {
            insetsArr3 = new android.graphics.Insets[10];
        } else {
            insetsArr3 = (android.graphics.Insets[]) insetsArr.clone();
        }
        this.mTypeInsetsMap = insetsArr3;
        this.mStableInsetsConsumed = insetsArr2 == null;
        if (this.mStableInsetsConsumed) {
            insetsArr4 = new android.graphics.Insets[10];
        } else {
            insetsArr4 = (android.graphics.Insets[]) insetsArr2.clone();
        }
        this.mTypeMaxInsetsMap = insetsArr4;
        this.mTypeVisibilityMap = zArr;
        this.mIsRound = z;
        this.mForceConsumingTypes = i;
        this.mSuppressScrimTypes = i2;
        this.mCompatInsetsTypes = i3;
        this.mCompatIgnoreVisibility = z2;
        this.mDisplayCutoutConsumed = displayCutout == null;
        this.mDisplayCutout = (this.mDisplayCutoutConsumed || displayCutout.isEmpty()) ? null : displayCutout;
        this.mRoundedCorners = roundedCorners;
        this.mPrivacyIndicatorBounds = privacyIndicatorBounds;
        this.mDisplayShape = displayShape;
        if (this.mSystemWindowInsetsConsumed || rectArr == null) {
            rectArr3 = new android.graphics.Rect[10][];
        } else {
            rectArr3 = (android.graphics.Rect[][]) rectArr.clone();
        }
        this.mTypeBoundingRectsMap = rectArr3;
        if (this.mStableInsetsConsumed || rectArr2 == null) {
            rectArr4 = new android.graphics.Rect[10][];
        } else {
            rectArr4 = (android.graphics.Rect[][]) rectArr2.clone();
        }
        this.mTypeMaxBoundingRectsMap = rectArr4;
        this.mFrameWidth = i4;
        this.mFrameHeight = i5;
    }

    public WindowInsets(android.view.WindowInsets windowInsets) {
        this(windowInsets.mSystemWindowInsetsConsumed ? null : windowInsets.mTypeInsetsMap, windowInsets.mStableInsetsConsumed ? null : windowInsets.mTypeMaxInsetsMap, windowInsets.mTypeVisibilityMap, windowInsets.mIsRound, windowInsets.mForceConsumingTypes, windowInsets.mSuppressScrimTypes, displayCutoutCopyConstructorArgument(windowInsets), windowInsets.mRoundedCorners, windowInsets.mPrivacyIndicatorBounds, windowInsets.mDisplayShape, windowInsets.mCompatInsetsTypes, windowInsets.mCompatIgnoreVisibility, windowInsets.mSystemWindowInsetsConsumed ? null : windowInsets.mTypeBoundingRectsMap, windowInsets.mStableInsetsConsumed ? null : windowInsets.mTypeMaxBoundingRectsMap, windowInsets.mFrameWidth, windowInsets.mFrameHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.DisplayCutout displayCutoutCopyConstructorArgument(android.view.WindowInsets windowInsets) {
        if (windowInsets.mDisplayCutoutConsumed) {
            return null;
        }
        if (windowInsets.mDisplayCutout == null) {
            return android.view.DisplayCutout.NO_CUTOUT;
        }
        return windowInsets.mDisplayCutout;
    }

    static android.graphics.Insets getInsets(android.graphics.Insets[] insetsArr, int i) {
        android.graphics.Insets insets;
        android.graphics.Insets insets2 = null;
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0 && (insets = insetsArr[android.view.WindowInsets.Type.indexOf(i2)]) != null) {
                if (insets2 == null) {
                    insets2 = insets;
                } else {
                    insets2 = android.graphics.Insets.max(insets2, insets);
                }
            }
        }
        return insets2 == null ? android.graphics.Insets.NONE : insets2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setInsets(android.graphics.Insets[] insetsArr, int i, android.graphics.Insets insets) {
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0) {
                insetsArr[android.view.WindowInsets.Type.indexOf(i2)] = insets;
            }
        }
    }

    public WindowInsets(android.graphics.Rect rect) {
        this(createCompatTypeMap(rect), null, new boolean[10], false, 0, 0, null, null, null, null, android.view.WindowInsets.Type.systemBars(), false, new android.graphics.Rect[10][], null, 0, 0);
    }

    public static android.graphics.Insets[] createCompatTypeMap(android.graphics.Rect rect) {
        if (rect == null) {
            return null;
        }
        android.graphics.Insets[] insetsArr = new android.graphics.Insets[10];
        assignCompatInsets(insetsArr, rect);
        return insetsArr;
    }

    public static void assignCompatInsets(android.graphics.Insets[] insetsArr, android.graphics.Rect rect) {
        insetsArr[android.view.WindowInsets.Type.indexOf(1)] = android.graphics.Insets.of(0, rect.top, 0, 0);
        insetsArr[android.view.WindowInsets.Type.indexOf(2)] = android.graphics.Insets.of(rect.left, 0, rect.right, rect.bottom);
    }

    private static boolean[] createCompatVisibilityMap(android.graphics.Insets[] insetsArr) {
        boolean[] zArr = new boolean[10];
        if (insetsArr == null) {
            return zArr;
        }
        for (int i = 1; i <= 512; i <<= 1) {
            int indexOf = android.view.WindowInsets.Type.indexOf(i);
            if (!android.graphics.Insets.NONE.equals(insetsArr[indexOf])) {
                zArr[indexOf] = true;
            }
        }
        return zArr;
    }

    @java.lang.Deprecated
    public android.graphics.Rect getSystemWindowInsetsAsRect() {
        if (this.mTempRect == null) {
            this.mTempRect = new android.graphics.Rect();
        }
        android.graphics.Insets systemWindowInsets = getSystemWindowInsets();
        this.mTempRect.set(systemWindowInsets.left, systemWindowInsets.top, systemWindowInsets.right, systemWindowInsets.bottom);
        return this.mTempRect;
    }

    @java.lang.Deprecated
    public android.graphics.Insets getSystemWindowInsets() {
        android.graphics.Insets insets;
        if (this.mCompatIgnoreVisibility) {
            insets = getInsetsIgnoringVisibility(this.mCompatInsetsTypes & (~android.view.WindowInsets.Type.ime()));
        } else {
            insets = getInsets(this.mCompatInsetsTypes);
        }
        if ((this.mCompatInsetsTypes & android.view.WindowInsets.Type.ime()) != 0 && this.mCompatIgnoreVisibility) {
            return android.graphics.Insets.max(insets, getInsets(android.view.WindowInsets.Type.ime()));
        }
        return insets;
    }

    public android.graphics.Insets getInsets(int i) {
        return getInsets(this.mTypeInsetsMap, i);
    }

    public android.graphics.Insets getInsetsIgnoringVisibility(int i) {
        if ((i & 8) != 0) {
            throw new java.lang.IllegalArgumentException("Unable to query the maximum insets for IME");
        }
        return getInsets(this.mTypeMaxInsetsMap, i);
    }

    public boolean isVisible(int i) {
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0 && !this.mTypeVisibilityMap[android.view.WindowInsets.Type.indexOf(i2)]) {
                return false;
            }
        }
        return true;
    }

    @java.lang.Deprecated
    public int getSystemWindowInsetLeft() {
        return getSystemWindowInsets().left;
    }

    @java.lang.Deprecated
    public int getSystemWindowInsetTop() {
        return getSystemWindowInsets().top;
    }

    @java.lang.Deprecated
    public int getSystemWindowInsetRight() {
        return getSystemWindowInsets().right;
    }

    @java.lang.Deprecated
    public int getSystemWindowInsetBottom() {
        return getSystemWindowInsets().bottom;
    }

    @java.lang.Deprecated
    public boolean hasSystemWindowInsets() {
        return !getSystemWindowInsets().equals(android.graphics.Insets.NONE);
    }

    public boolean hasInsets() {
        return (getInsets(this.mTypeInsetsMap, android.view.WindowInsets.Type.all()).equals(android.graphics.Insets.NONE) && getInsets(this.mTypeMaxInsetsMap, android.view.WindowInsets.Type.all()).equals(android.graphics.Insets.NONE) && this.mDisplayCutout == null && this.mRoundedCorners == null) ? false : true;
    }

    public java.util.List<android.graphics.Rect> getBoundingRects(int i) {
        return getBoundingRects(this.mTypeBoundingRectsMap, i);
    }

    public java.util.List<android.graphics.Rect> getBoundingRectsIgnoringVisibility(int i) {
        if ((i & 8) != 0) {
            throw new java.lang.IllegalArgumentException("Unable to query the bounding rects for IME");
        }
        return getBoundingRects(this.mTypeMaxBoundingRectsMap, i);
    }

    private java.util.List<android.graphics.Rect> getBoundingRects(android.graphics.Rect[][] rectArr, int i) {
        android.graphics.Rect[] rectArr2;
        android.graphics.Rect[] rectArr3 = null;
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0 && (rectArr2 = rectArr[android.view.WindowInsets.Type.indexOf(i2)]) != null) {
                if (rectArr3 == null) {
                    rectArr3 = rectArr2;
                } else {
                    android.graphics.Rect[] rectArr4 = new android.graphics.Rect[rectArr3.length + rectArr2.length];
                    java.lang.System.arraycopy(rectArr3, 0, rectArr4, 0, rectArr3.length);
                    java.lang.System.arraycopy(rectArr2, 0, rectArr4, rectArr3.length, rectArr2.length);
                    rectArr3 = rectArr4;
                }
            }
        }
        if (rectArr3 == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Arrays.asList(rectArr3);
    }

    public android.view.DisplayCutout getDisplayCutout() {
        return this.mDisplayCutout;
    }

    public android.view.RoundedCorner getRoundedCorner(int i) {
        if (this.mRoundedCorners == null) {
            return null;
        }
        return this.mRoundedCorners.getRoundedCorner(i);
    }

    public android.graphics.Rect getPrivacyIndicatorBounds() {
        if (this.mPrivacyIndicatorBounds == null) {
            return null;
        }
        return this.mPrivacyIndicatorBounds.getStaticPrivacyIndicatorBounds();
    }

    public android.view.DisplayShape getDisplayShape() {
        return this.mDisplayShape;
    }

    @java.lang.Deprecated
    public android.view.WindowInsets consumeDisplayCutout() {
        return new android.view.WindowInsets(this.mSystemWindowInsetsConsumed ? null : this.mTypeInsetsMap, this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap, this.mTypeVisibilityMap, this.mIsRound, this.mForceConsumingTypes, this.mSuppressScrimTypes, null, this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mDisplayShape, this.mCompatInsetsTypes, this.mCompatIgnoreVisibility, this.mSystemWindowInsetsConsumed ? null : this.mTypeBoundingRectsMap, this.mStableInsetsConsumed ? null : this.mTypeMaxBoundingRectsMap, this.mFrameWidth, this.mFrameHeight);
    }

    public boolean isConsumed() {
        return this.mSystemWindowInsetsConsumed && this.mStableInsetsConsumed && this.mDisplayCutoutConsumed;
    }

    public boolean isRound() {
        return this.mIsRound;
    }

    @java.lang.Deprecated
    public android.view.WindowInsets consumeSystemWindowInsets() {
        return new android.view.WindowInsets(null, null, this.mTypeVisibilityMap, this.mIsRound, this.mForceConsumingTypes, this.mSuppressScrimTypes, (this.mCompatInsetsTypes & android.view.WindowInsets.Type.displayCutout()) != 0 ? null : displayCutoutCopyConstructorArgument(this), this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mDisplayShape, this.mCompatInsetsTypes, this.mCompatIgnoreVisibility, null, null, this.mFrameWidth, this.mFrameHeight);
    }

    @java.lang.Deprecated
    public android.view.WindowInsets replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        if (this.mSystemWindowInsetsConsumed) {
            return this;
        }
        return new android.view.WindowInsets.Builder(this).setSystemWindowInsets(android.graphics.Insets.of(i, i2, i3, i4)).build();
    }

    @java.lang.Deprecated
    public android.view.WindowInsets replaceSystemWindowInsets(android.graphics.Rect rect) {
        return replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
    }

    @java.lang.Deprecated
    public android.graphics.Insets getStableInsets() {
        return getInsets(this.mTypeMaxInsetsMap, android.view.WindowInsets.Type.systemBars());
    }

    @java.lang.Deprecated
    public int getStableInsetTop() {
        return getStableInsets().top;
    }

    @java.lang.Deprecated
    public int getStableInsetLeft() {
        return getStableInsets().left;
    }

    @java.lang.Deprecated
    public int getStableInsetRight() {
        return getStableInsets().right;
    }

    @java.lang.Deprecated
    public int getStableInsetBottom() {
        return getStableInsets().bottom;
    }

    @java.lang.Deprecated
    public boolean hasStableInsets() {
        return !getStableInsets().equals(android.graphics.Insets.NONE);
    }

    @java.lang.Deprecated
    public android.graphics.Insets getSystemGestureInsets() {
        return getInsets(this.mTypeInsetsMap, 16);
    }

    @java.lang.Deprecated
    public android.graphics.Insets getMandatorySystemGestureInsets() {
        return getInsets(this.mTypeInsetsMap, 32);
    }

    @java.lang.Deprecated
    public android.graphics.Insets getTappableElementInsets() {
        return getInsets(this.mTypeInsetsMap, 64);
    }

    @java.lang.Deprecated
    public android.view.WindowInsets consumeStableInsets() {
        return this;
    }

    public int getForceConsumingTypes() {
        return this.mForceConsumingTypes;
    }

    public int getSuppressScrimTypes() {
        return this.mSuppressScrimTypes;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("WindowInsets{\n    ");
        for (int i = 0; i < 10; i++) {
            android.graphics.Insets insets = this.mTypeInsetsMap[i];
            android.graphics.Insets insets2 = this.mTypeMaxInsetsMap[i];
            boolean z = this.mTypeVisibilityMap[i];
            if (!android.graphics.Insets.NONE.equals(insets) || !android.graphics.Insets.NONE.equals(insets2) || z) {
                sb.append(android.view.WindowInsets.Type.toString(1 << i)).append("=").append(insets).append(" max=").append(insets2).append(" vis=").append(z).append(" boundingRects=").append(java.util.Arrays.toString(this.mTypeBoundingRectsMap[i])).append(" maxBoundingRects=").append(java.util.Arrays.toString(this.mTypeMaxBoundingRectsMap[i])).append("\n    ");
            }
        }
        sb.append(this.mDisplayCutout != null ? "cutout=" + this.mDisplayCutout : "");
        sb.append("\n    ");
        sb.append(this.mRoundedCorners != null ? "roundedCorners=" + this.mRoundedCorners : "");
        sb.append("\n    ");
        sb.append(this.mPrivacyIndicatorBounds != null ? "privacyIndicatorBounds=" + this.mPrivacyIndicatorBounds : "");
        sb.append("\n    ");
        sb.append(this.mDisplayShape != null ? "displayShape=" + this.mDisplayShape : "");
        sb.append("\n    ");
        sb.append("forceConsumingTypes=" + android.view.WindowInsets.Type.toString(this.mForceConsumingTypes));
        sb.append("\n    ");
        sb.append("suppressScrimTypes=" + android.view.WindowInsets.Type.toString(this.mSuppressScrimTypes));
        sb.append("\n    ");
        sb.append("compatInsetsTypes=" + android.view.WindowInsets.Type.toString(this.mCompatInsetsTypes));
        sb.append("\n    ");
        sb.append("compatIgnoreVisibility=" + this.mCompatIgnoreVisibility);
        sb.append("\n    ");
        sb.append("systemWindowInsetsConsumed=" + this.mSystemWindowInsetsConsumed);
        sb.append("\n    ");
        sb.append("stableInsetsConsumed=" + this.mStableInsetsConsumed);
        sb.append("\n    ");
        sb.append("displayCutoutConsumed=" + this.mDisplayCutoutConsumed);
        sb.append("\n    ");
        sb.append(isRound() ? "round" : "");
        sb.append("\n    ");
        sb.append("frameWidth=" + this.mFrameWidth);
        sb.append("\n    ");
        sb.append("frameHeight=" + this.mFrameHeight);
        sb.append("}");
        return sb.toString();
    }

    @java.lang.Deprecated
    public android.view.WindowInsets inset(android.graphics.Rect rect) {
        return inset(rect.left, rect.top, rect.right, rect.bottom);
    }

    public android.view.WindowInsets inset(android.graphics.Insets insets) {
        java.util.Objects.requireNonNull(insets);
        return inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    public android.view.WindowInsets inset(int i, int i2, int i3, int i4) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i2);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i3);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i4);
        return insetUnchecked(i, i2, i3, i4);
    }

    public android.util.Size getFrame() {
        return new android.util.Size(this.mFrameWidth, this.mFrameHeight);
    }

    public android.view.WindowInsets insetUnchecked(int i, int i2, int i3, int i4) {
        android.graphics.Insets[] insetInsets;
        android.graphics.Insets[] insetInsets2;
        android.view.DisplayCutout inset;
        android.view.RoundedCorners inset2;
        android.view.PrivacyIndicatorBounds inset3;
        boolean z;
        boolean z2;
        int i5;
        android.view.DisplayShape displayShape;
        int i6;
        int i7;
        android.graphics.Rect[][] insetBoundingRects;
        android.graphics.Rect[][] insetBoundingRects2;
        if (this.mSystemWindowInsetsConsumed) {
            insetInsets = null;
        } else {
            insetInsets = insetInsets(this.mTypeInsetsMap, i, i2, i3, i4);
        }
        if (this.mStableInsetsConsumed) {
            insetInsets2 = null;
        } else {
            insetInsets2 = insetInsets(this.mTypeMaxInsetsMap, i, i2, i3, i4);
        }
        boolean[] zArr = this.mTypeVisibilityMap;
        boolean z3 = this.mIsRound;
        int i8 = this.mForceConsumingTypes;
        int i9 = this.mSuppressScrimTypes;
        if (this.mDisplayCutoutConsumed) {
            inset = null;
        } else if (this.mDisplayCutout == null) {
            inset = android.view.DisplayCutout.NO_CUTOUT;
        } else {
            inset = this.mDisplayCutout.inset(i, i2, i3, i4);
        }
        if (this.mRoundedCorners == null) {
            inset2 = android.view.RoundedCorners.NO_ROUNDED_CORNERS;
        } else {
            inset2 = this.mRoundedCorners.inset(i, i2, i3, i4);
        }
        if (this.mPrivacyIndicatorBounds == null) {
            inset3 = null;
        } else {
            inset3 = this.mPrivacyIndicatorBounds.inset(i, i2, i3, i4);
        }
        android.view.DisplayShape displayShape2 = this.mDisplayShape;
        int i10 = this.mCompatInsetsTypes;
        boolean z4 = this.mCompatIgnoreVisibility;
        if (!this.mSystemWindowInsetsConsumed) {
            z = z3;
            z2 = z4;
            i5 = i10;
            displayShape = displayShape2;
            i6 = i9;
            i7 = i8;
            insetBoundingRects = insetBoundingRects(this.mTypeBoundingRectsMap, i, i2, i3, i4, this.mFrameWidth, this.mFrameHeight);
        } else {
            z2 = z4;
            i5 = i10;
            displayShape = displayShape2;
            i6 = i9;
            i7 = i8;
            z = z3;
            insetBoundingRects = null;
        }
        if (!this.mStableInsetsConsumed) {
            insetBoundingRects2 = insetBoundingRects(this.mTypeMaxBoundingRectsMap, i, i2, i3, i4, this.mFrameWidth, this.mFrameHeight);
        } else {
            insetBoundingRects2 = null;
        }
        return new android.view.WindowInsets(insetInsets, insetInsets2, zArr, z, i7, i6, inset, inset2, inset3, displayShape, i5, z2, insetBoundingRects, insetBoundingRects2, java.lang.Math.max(0, (this.mFrameWidth - i) - i3), java.lang.Math.max(0, (this.mFrameHeight - i2) - i4));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.view.WindowInsets)) {
            return false;
        }
        android.view.WindowInsets windowInsets = (android.view.WindowInsets) obj;
        if (this.mIsRound == windowInsets.mIsRound && this.mForceConsumingTypes == windowInsets.mForceConsumingTypes && this.mSuppressScrimTypes == windowInsets.mSuppressScrimTypes && this.mSystemWindowInsetsConsumed == windowInsets.mSystemWindowInsetsConsumed && this.mStableInsetsConsumed == windowInsets.mStableInsetsConsumed && this.mDisplayCutoutConsumed == windowInsets.mDisplayCutoutConsumed && java.util.Arrays.equals(this.mTypeInsetsMap, windowInsets.mTypeInsetsMap) && java.util.Arrays.equals(this.mTypeMaxInsetsMap, windowInsets.mTypeMaxInsetsMap) && java.util.Arrays.equals(this.mTypeVisibilityMap, windowInsets.mTypeVisibilityMap) && java.util.Objects.equals(this.mDisplayCutout, windowInsets.mDisplayCutout) && java.util.Objects.equals(this.mRoundedCorners, windowInsets.mRoundedCorners) && java.util.Objects.equals(this.mPrivacyIndicatorBounds, windowInsets.mPrivacyIndicatorBounds) && java.util.Objects.equals(this.mDisplayShape, windowInsets.mDisplayShape) && java.util.Arrays.deepEquals(this.mTypeBoundingRectsMap, windowInsets.mTypeBoundingRectsMap) && java.util.Arrays.deepEquals(this.mTypeMaxBoundingRectsMap, windowInsets.mTypeMaxBoundingRectsMap) && this.mFrameWidth == windowInsets.mFrameWidth && this.mFrameHeight == windowInsets.mFrameHeight) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTypeInsetsMap)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTypeMaxInsetsMap)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTypeVisibilityMap)), java.lang.Boolean.valueOf(this.mIsRound), this.mDisplayCutout, this.mRoundedCorners, java.lang.Integer.valueOf(this.mForceConsumingTypes), java.lang.Integer.valueOf(this.mSuppressScrimTypes), java.lang.Boolean.valueOf(this.mSystemWindowInsetsConsumed), java.lang.Boolean.valueOf(this.mStableInsetsConsumed), java.lang.Boolean.valueOf(this.mDisplayCutoutConsumed), this.mPrivacyIndicatorBounds, this.mDisplayShape, java.lang.Integer.valueOf(java.util.Arrays.deepHashCode(this.mTypeBoundingRectsMap)), java.lang.Integer.valueOf(java.util.Arrays.deepHashCode(this.mTypeMaxBoundingRectsMap)), java.lang.Integer.valueOf(this.mFrameWidth), java.lang.Integer.valueOf(this.mFrameHeight));
    }

    private static android.graphics.Insets[] insetInsets(android.graphics.Insets[] insetsArr, int i, int i2, int i3, int i4) {
        android.graphics.Insets insetInsets;
        boolean z = false;
        for (int i5 = 0; i5 < 10; i5++) {
            android.graphics.Insets insets = insetsArr[i5];
            if (insets != null && (insetInsets = insetInsets(insets, i, i2, i3, i4)) != insets) {
                if (!z) {
                    insetsArr = (android.graphics.Insets[]) insetsArr.clone();
                    z = true;
                }
                insetsArr[i5] = insetInsets;
            }
        }
        return insetsArr;
    }

    static android.graphics.Insets insetInsets(android.graphics.Insets insets, int i, int i2, int i3, int i4) {
        int max = java.lang.Math.max(0, insets.left - i);
        int max2 = java.lang.Math.max(0, insets.top - i2);
        int max3 = java.lang.Math.max(0, insets.right - i3);
        int max4 = java.lang.Math.max(0, insets.bottom - i4);
        if (max == i && max2 == i2 && max3 == i3 && max4 == i4) {
            return insets;
        }
        return android.graphics.Insets.of(max, max2, max3, max4);
    }

    static android.graphics.Rect[][] insetBoundingRects(android.graphics.Rect[][] rectArr, int i, int i2, int i3, int i4, int i5, int i6) {
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return rectArr;
        }
        android.graphics.Rect[][] rectArr2 = rectArr;
        boolean z = false;
        for (int i7 = 0; i7 < 10; i7++) {
            android.graphics.Rect[] rectArr3 = rectArr2[i7];
            if (rectArr3 != null) {
                android.graphics.Rect[] insetBoundingRects = insetBoundingRects(rectArr3, i, i2, i3, i4, i5, i6);
                if (!java.util.Arrays.equals(insetBoundingRects, rectArr3)) {
                    if (!z) {
                        rectArr2 = (android.graphics.Rect[][]) rectArr2.clone();
                        z = true;
                    }
                    rectArr2[i7] = insetBoundingRects;
                }
            }
        }
        return rectArr2;
    }

    static android.graphics.Rect[] insetBoundingRects(android.graphics.Rect[] rectArr, int i, int i2, int i3, int i4, int i5, int i6) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.graphics.Rect rect : rectArr) {
            android.graphics.Rect insetRect = insetRect(rect, i, i2, i3, i4, i5, i6);
            if (insetRect != null) {
                arrayList.add(insetRect);
            }
        }
        return (android.graphics.Rect[]) arrayList.toArray(new android.graphics.Rect[0]);
    }

    private static android.graphics.Rect insetRect(android.graphics.Rect rect, int i, int i2, int i3, int i4, int i5, int i6) {
        if (rect == null) {
            return null;
        }
        android.graphics.Rect rect2 = new android.graphics.Rect(i, i2, i5 - i3, i6 - i4);
        android.graphics.Rect rect3 = new android.graphics.Rect();
        if (!rect3.setIntersect(rect2, rect)) {
            return null;
        }
        rect3.offset(-i, -i2);
        return rect3;
    }

    boolean isSystemWindowInsetsConsumed() {
        return this.mSystemWindowInsetsConsumed;
    }

    public static final class Builder {
        private android.view.DisplayCutout mDisplayCutout;
        private android.view.DisplayShape mDisplayShape;
        private int mForceConsumingTypes;
        private int mFrameHeight;
        private int mFrameWidth;
        private boolean mIsRound;
        private android.view.PrivacyIndicatorBounds mPrivacyIndicatorBounds;
        private android.view.RoundedCorners mRoundedCorners;
        private boolean mStableInsetsConsumed;
        private int mSuppressScrimTypes;
        private boolean mSystemInsetsConsumed;
        private final android.graphics.Rect[][] mTypeBoundingRectsMap;
        private final android.graphics.Insets[] mTypeInsetsMap;
        private final android.graphics.Rect[][] mTypeMaxBoundingRectsMap;
        private final android.graphics.Insets[] mTypeMaxInsetsMap;
        private final boolean[] mTypeVisibilityMap;

        public Builder() {
            this.mSystemInsetsConsumed = true;
            this.mStableInsetsConsumed = true;
            this.mRoundedCorners = android.view.RoundedCorners.NO_ROUNDED_CORNERS;
            this.mDisplayShape = android.view.DisplayShape.NONE;
            this.mPrivacyIndicatorBounds = new android.view.PrivacyIndicatorBounds();
            this.mTypeInsetsMap = new android.graphics.Insets[10];
            this.mTypeMaxInsetsMap = new android.graphics.Insets[10];
            this.mTypeVisibilityMap = new boolean[10];
            this.mTypeBoundingRectsMap = new android.graphics.Rect[10][];
            this.mTypeMaxBoundingRectsMap = new android.graphics.Rect[10][];
        }

        public Builder(android.view.WindowInsets windowInsets) {
            this.mSystemInsetsConsumed = true;
            this.mStableInsetsConsumed = true;
            this.mRoundedCorners = android.view.RoundedCorners.NO_ROUNDED_CORNERS;
            this.mDisplayShape = android.view.DisplayShape.NONE;
            this.mPrivacyIndicatorBounds = new android.view.PrivacyIndicatorBounds();
            this.mTypeInsetsMap = (android.graphics.Insets[]) windowInsets.mTypeInsetsMap.clone();
            this.mTypeMaxInsetsMap = (android.graphics.Insets[]) windowInsets.mTypeMaxInsetsMap.clone();
            this.mTypeVisibilityMap = (boolean[]) windowInsets.mTypeVisibilityMap.clone();
            this.mSystemInsetsConsumed = windowInsets.mSystemWindowInsetsConsumed;
            this.mStableInsetsConsumed = windowInsets.mStableInsetsConsumed;
            this.mDisplayCutout = android.view.WindowInsets.displayCutoutCopyConstructorArgument(windowInsets);
            this.mRoundedCorners = windowInsets.mRoundedCorners;
            this.mIsRound = windowInsets.mIsRound;
            this.mForceConsumingTypes = windowInsets.mForceConsumingTypes;
            this.mSuppressScrimTypes = windowInsets.mSuppressScrimTypes;
            this.mPrivacyIndicatorBounds = windowInsets.mPrivacyIndicatorBounds;
            this.mDisplayShape = windowInsets.mDisplayShape;
            this.mTypeBoundingRectsMap = (android.graphics.Rect[][]) windowInsets.mTypeBoundingRectsMap.clone();
            this.mTypeMaxBoundingRectsMap = (android.graphics.Rect[][]) windowInsets.mTypeMaxBoundingRectsMap.clone();
            this.mFrameWidth = windowInsets.mFrameWidth;
            this.mFrameHeight = windowInsets.mFrameHeight;
        }

        @java.lang.Deprecated
        public android.view.WindowInsets.Builder setSystemWindowInsets(android.graphics.Insets insets) {
            com.android.internal.util.Preconditions.checkNotNull(insets);
            android.view.WindowInsets.assignCompatInsets(this.mTypeInsetsMap, insets.toRect());
            this.mSystemInsetsConsumed = false;
            return this;
        }

        @java.lang.Deprecated
        public android.view.WindowInsets.Builder setSystemGestureInsets(android.graphics.Insets insets) {
            android.view.WindowInsets.setInsets(this.mTypeInsetsMap, 16, insets);
            return this;
        }

        @java.lang.Deprecated
        public android.view.WindowInsets.Builder setMandatorySystemGestureInsets(android.graphics.Insets insets) {
            android.view.WindowInsets.setInsets(this.mTypeInsetsMap, 32, insets);
            return this;
        }

        @java.lang.Deprecated
        public android.view.WindowInsets.Builder setTappableElementInsets(android.graphics.Insets insets) {
            android.view.WindowInsets.setInsets(this.mTypeInsetsMap, 64, insets);
            return this;
        }

        public android.view.WindowInsets.Builder setInsets(int i, android.graphics.Insets insets) {
            com.android.internal.util.Preconditions.checkNotNull(insets);
            android.view.WindowInsets.setInsets(this.mTypeInsetsMap, i, insets);
            this.mSystemInsetsConsumed = false;
            return this;
        }

        public android.view.WindowInsets.Builder setInsetsIgnoringVisibility(int i, android.graphics.Insets insets) throws java.lang.IllegalArgumentException {
            if (i == 8) {
                throw new java.lang.IllegalArgumentException("Maximum inset not available for IME");
            }
            com.android.internal.util.Preconditions.checkNotNull(insets);
            android.view.WindowInsets.setInsets(this.mTypeMaxInsetsMap, i, insets);
            this.mStableInsetsConsumed = false;
            return this;
        }

        public android.view.WindowInsets.Builder setVisible(int i, boolean z) {
            for (int i2 = 1; i2 <= 512; i2 <<= 1) {
                if ((i & i2) != 0) {
                    this.mTypeVisibilityMap[android.view.WindowInsets.Type.indexOf(i2)] = z;
                }
            }
            return this;
        }

        @java.lang.Deprecated
        public android.view.WindowInsets.Builder setStableInsets(android.graphics.Insets insets) {
            com.android.internal.util.Preconditions.checkNotNull(insets);
            android.view.WindowInsets.assignCompatInsets(this.mTypeMaxInsetsMap, insets.toRect());
            this.mStableInsetsConsumed = false;
            return this;
        }

        public android.view.WindowInsets.Builder setDisplayCutout(android.view.DisplayCutout displayCutout) {
            if (displayCutout == null) {
                displayCutout = android.view.DisplayCutout.NO_CUTOUT;
            }
            this.mDisplayCutout = displayCutout;
            if (!this.mDisplayCutout.isEmpty()) {
                android.graphics.Insets of = android.graphics.Insets.of(this.mDisplayCutout.getSafeInsets());
                int indexOf = android.view.WindowInsets.Type.indexOf(128);
                this.mTypeInsetsMap[indexOf] = of;
                this.mTypeMaxInsetsMap[indexOf] = of;
                this.mTypeVisibilityMap[indexOf] = true;
            }
            return this;
        }

        public android.view.WindowInsets.Builder setRoundedCorners(android.view.RoundedCorners roundedCorners) {
            if (roundedCorners == null) {
                roundedCorners = android.view.RoundedCorners.NO_ROUNDED_CORNERS;
            }
            this.mRoundedCorners = roundedCorners;
            return this;
        }

        public android.view.WindowInsets.Builder setRoundedCorner(int i, android.view.RoundedCorner roundedCorner) {
            this.mRoundedCorners.setRoundedCorner(i, roundedCorner);
            return this;
        }

        public android.view.WindowInsets.Builder setPrivacyIndicatorBounds(android.view.PrivacyIndicatorBounds privacyIndicatorBounds) {
            this.mPrivacyIndicatorBounds = privacyIndicatorBounds;
            return this;
        }

        public android.view.WindowInsets.Builder setPrivacyIndicatorBounds(android.graphics.Rect rect) {
            this.mPrivacyIndicatorBounds = new android.view.PrivacyIndicatorBounds(new android.graphics.Rect[]{rect, rect, rect, rect}, 0);
            return this;
        }

        public android.view.WindowInsets.Builder setDisplayShape(android.view.DisplayShape displayShape) {
            this.mDisplayShape = displayShape;
            return this;
        }

        public android.view.WindowInsets.Builder setRound(boolean z) {
            this.mIsRound = z;
            return this;
        }

        public android.view.WindowInsets.Builder setAlwaysConsumeSystemBars(boolean z) {
            return this;
        }

        public android.view.WindowInsets.Builder setForceConsumingTypes(int i) {
            this.mForceConsumingTypes = i;
            return this;
        }

        public android.view.WindowInsets.Builder setSuppressScrimTypes(int i) {
            this.mSuppressScrimTypes = i;
            return this;
        }

        public android.view.WindowInsets.Builder setBoundingRects(int i, java.util.List<android.graphics.Rect> list) {
            for (int i2 = 1; i2 <= 512; i2 <<= 1) {
                if ((i & i2) != 0) {
                    this.mTypeBoundingRectsMap[android.view.WindowInsets.Type.indexOf(i2)] = (android.graphics.Rect[]) list.toArray(new android.graphics.Rect[0]);
                }
            }
            return this;
        }

        public android.view.WindowInsets.Builder setBoundingRectsIgnoringVisibility(int i, java.util.List<android.graphics.Rect> list) {
            if (i == 8) {
                throw new java.lang.IllegalArgumentException("Maximum bounding rects not available for IME");
            }
            for (int i2 = 1; i2 <= 512; i2 <<= 1) {
                if ((i & i2) != 0) {
                    this.mTypeMaxBoundingRectsMap[android.view.WindowInsets.Type.indexOf(i2)] = (android.graphics.Rect[]) list.toArray(new android.graphics.Rect[0]);
                }
            }
            return this;
        }

        public android.view.WindowInsets.Builder setFrame(int i, int i2) {
            this.mFrameWidth = i;
            this.mFrameHeight = i2;
            return this;
        }

        public android.view.WindowInsets build() {
            return new android.view.WindowInsets(this.mSystemInsetsConsumed ? null : this.mTypeInsetsMap, this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap, this.mTypeVisibilityMap, this.mIsRound, this.mForceConsumingTypes, this.mSuppressScrimTypes, this.mDisplayCutout, this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mDisplayShape, android.view.WindowInsets.Type.systemBars(), false, this.mSystemInsetsConsumed ? null : this.mTypeBoundingRectsMap, this.mStableInsetsConsumed ? null : this.mTypeMaxBoundingRectsMap, this.mFrameWidth, this.mFrameHeight);
        }
    }

    public static final class Type {
        static final int CAPTION_BAR = 4;
        static final int DEFAULT_VISIBLE = -9;
        static final int DISPLAY_CUTOUT = 128;
        static final int FIRST = 1;
        static final int IME = 8;
        static final int LAST = 512;
        static final int MANDATORY_SYSTEM_GESTURES = 32;
        static final int NAVIGATION_BARS = 2;
        static final int SIZE = 10;
        static final int STATUS_BARS = 1;
        static final int SYSTEM_GESTURES = 16;
        static final int SYSTEM_OVERLAYS = 512;
        static final int TAPPABLE_ELEMENT = 64;
        static final int WINDOW_DECOR = 256;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface InsetsType {
        }

        static int indexOf(int i) {
            switch (i) {
                case 1:
                    return 0;
                case 2:
                    return 1;
                case 4:
                    return 2;
                case 8:
                    return 3;
                case 16:
                    return 4;
                case 32:
                    return 5;
                case 64:
                    return 6;
                case 128:
                    return 7;
                case 256:
                    return 8;
                case 512:
                    return 9;
                default:
                    throw new java.lang.IllegalArgumentException("type needs to be >= FIRST and <= LAST, type=" + i);
            }
        }

        public static java.lang.String toString(int i) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if ((i & 1) != 0) {
                sb.append("statusBars ");
            }
            if ((i & 2) != 0) {
                sb.append("navigationBars ");
            }
            if ((i & 4) != 0) {
                sb.append("captionBar ");
            }
            if ((i & 8) != 0) {
                sb.append("ime ");
            }
            if ((i & 16) != 0) {
                sb.append("systemGestures ");
            }
            if ((i & 32) != 0) {
                sb.append("mandatorySystemGestures ");
            }
            if ((i & 64) != 0) {
                sb.append("tappableElement ");
            }
            if ((i & 128) != 0) {
                sb.append("displayCutout ");
            }
            if ((i & 256) != 0) {
                sb.append("windowDecor ");
            }
            if ((i & 512) != 0) {
                sb.append("systemOverlays ");
            }
            if (sb.length() > 0) {
                sb.delete(sb.length() - 1, sb.length());
            }
            return sb.toString();
        }

        private Type() {
        }

        public static int statusBars() {
            return 1;
        }

        public static int navigationBars() {
            return 2;
        }

        public static int captionBar() {
            return 4;
        }

        public static int ime() {
            return 8;
        }

        public static int systemGestures() {
            return 16;
        }

        public static int mandatorySystemGestures() {
            return 32;
        }

        public static int tappableElement() {
            return 64;
        }

        public static int displayCutout() {
            return 128;
        }

        public static int systemOverlays() {
            return 512;
        }

        public static int systemBars() {
            return 519;
        }

        public static int defaultVisible() {
            return -9;
        }

        public static int all() {
            return -1;
        }

        public static boolean hasCompatSystemBars(int i) {
            return (i & 3) != 0;
        }
    }

    public static final class Side {
        public static final int BOTTOM = 8;
        public static final int LEFT = 1;
        public static final int RIGHT = 4;
        public static final int TOP = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface InsetsSide {
        }

        private Side() {
        }

        public static int all() {
            return 15;
        }
    }
}
