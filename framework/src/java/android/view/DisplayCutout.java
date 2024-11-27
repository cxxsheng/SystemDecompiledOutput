package android.view;

/* loaded from: classes4.dex */
public final class DisplayCutout {
    public static final int BOUNDS_POSITION_BOTTOM = 3;
    public static final int BOUNDS_POSITION_LEFT = 0;
    public static final int BOUNDS_POSITION_LENGTH = 4;
    public static final int BOUNDS_POSITION_RIGHT = 2;
    public static final int BOUNDS_POSITION_TOP = 1;
    public static final java.lang.String EMULATION_OVERLAY_CATEGORY = "com.android.internal.display_cutout_emulation";
    private static final int INVALID_SIDE_OVERRIDE = -1;
    private static final java.lang.String SIDE_STRING_BOTTOM = "bottom";
    private static final java.lang.String SIDE_STRING_LEFT = "left";
    private static final java.lang.String SIDE_STRING_RIGHT = "right";
    private static final java.lang.String SIDE_STRING_TOP = "top";
    private static final java.lang.String TAG = "DisplayCutout";
    private static android.graphics.Path sCachedCutoutPath;
    private static android.view.DisplayCutout.CutoutPathParserInfo sCachedCutoutPathParserInfo;
    private static float sCachedDensity;
    private static int sCachedDisplayHeight;
    private static int sCachedDisplayWidth;
    private static float sCachedPhysicalPixelDisplaySizeRatio;
    private static int[] sCachedSideOverrides;
    private static java.lang.String sCachedSpec;
    private static android.graphics.Insets sCachedWaterfallInsets;
    private final android.view.DisplayCutout.Bounds mBounds;
    private final android.view.DisplayCutout.CutoutPathParserInfo mCutoutPathParserInfo;
    private final android.graphics.Rect mSafeInsets;
    private int[] mSideOverrides;
    private final android.graphics.Insets mWaterfallInsets;
    private static final android.graphics.Rect ZERO_RECT = new android.graphics.Rect();
    private static final android.view.DisplayCutout.CutoutPathParserInfo EMPTY_PARSER_INFO = new android.view.DisplayCutout.CutoutPathParserInfo(0, 0, 0, 0, 0.0f, "", 0, 0.0f, 0.0f);
    public static final android.view.DisplayCutout NO_CUTOUT = new android.view.DisplayCutout(ZERO_RECT, android.graphics.Insets.NONE, ZERO_RECT, ZERO_RECT, ZERO_RECT, ZERO_RECT, EMPTY_PARSER_INFO, false);
    private static final android.util.Pair<android.graphics.Path, android.view.DisplayCutout> NULL_PAIR = new android.util.Pair<>(null, null);
    private static final java.lang.Object CACHE_LOCK = new java.lang.Object();
    private static android.util.Pair<android.graphics.Path, android.view.DisplayCutout> sCachedCutout = NULL_PAIR;
    static final int[] INVALID_OVERRIDES = {-1, -1, -1, -1};

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BoundsPosition {
    }

    private static class Bounds {
        private final android.graphics.Rect[] mRects;

        private Bounds(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4, boolean z) {
            this.mRects = new android.graphics.Rect[4];
            this.mRects[0] = android.view.DisplayCutout.getCopyOrRef(rect, z);
            this.mRects[1] = android.view.DisplayCutout.getCopyOrRef(rect2, z);
            this.mRects[2] = android.view.DisplayCutout.getCopyOrRef(rect3, z);
            this.mRects[3] = android.view.DisplayCutout.getCopyOrRef(rect4, z);
        }

        private Bounds(android.graphics.Rect[] rectArr, boolean z) {
            if (rectArr.length != 4) {
                throw new java.lang.IllegalArgumentException("rects must have exactly 4 elements: rects=" + java.util.Arrays.toString(rectArr));
            }
            int i = 0;
            if (z) {
                this.mRects = new android.graphics.Rect[4];
                while (i < 4) {
                    this.mRects[i] = new android.graphics.Rect(rectArr[i]);
                    i++;
                }
                return;
            }
            int length = rectArr.length;
            while (i < length) {
                if (rectArr[i] != null) {
                    i++;
                } else {
                    throw new java.lang.IllegalArgumentException("rects must have non-null elements: rects=" + java.util.Arrays.toString(rectArr));
                }
            }
            this.mRects = rectArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            for (android.graphics.Rect rect : this.mRects) {
                if (!rect.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.graphics.Rect getRect(int i) {
            return new android.graphics.Rect(this.mRects[i]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.graphics.Rect[] getRects() {
            android.graphics.Rect[] rectArr = new android.graphics.Rect[4];
            for (int i = 0; i < 4; i++) {
                rectArr[i] = new android.graphics.Rect(this.mRects[i]);
            }
            return rectArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void scale(float f) {
            for (int i = 0; i < 4; i++) {
                this.mRects[i].scale(f);
            }
        }

        public int hashCode() {
            int i = 0;
            for (android.graphics.Rect rect : this.mRects) {
                i = (i * 48271) + rect.hashCode();
            }
            return i;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof android.view.DisplayCutout.Bounds) {
                return java.util.Arrays.deepEquals(this.mRects, ((android.view.DisplayCutout.Bounds) obj).mRects);
            }
            return false;
        }

        public java.lang.String toString() {
            return "Bounds=" + java.util.Arrays.toString(this.mRects);
        }
    }

    public static class CutoutPathParserInfo {
        private final java.lang.String mCutoutSpec;
        private final float mDensity;
        private final int mDisplayHeight;
        private final int mDisplayWidth;
        private final int mPhysicalDisplayHeight;
        private final int mPhysicalDisplayWidth;
        private final float mPhysicalPixelDisplaySizeRatio;
        private final int mRotation;
        private final float mScale;

        public CutoutPathParserInfo(int i, int i2, int i3, int i4, float f, java.lang.String str, int i5, float f2, float f3) {
            this.mDisplayWidth = i;
            this.mDisplayHeight = i2;
            this.mPhysicalDisplayWidth = i3;
            this.mPhysicalDisplayHeight = i4;
            this.mDensity = f;
            this.mCutoutSpec = str == null ? "" : str;
            this.mRotation = i5;
            this.mScale = f2;
            this.mPhysicalPixelDisplaySizeRatio = f3;
        }

        public CutoutPathParserInfo(android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo) {
            this.mDisplayWidth = cutoutPathParserInfo.mDisplayWidth;
            this.mDisplayHeight = cutoutPathParserInfo.mDisplayHeight;
            this.mPhysicalDisplayWidth = cutoutPathParserInfo.mPhysicalDisplayWidth;
            this.mPhysicalDisplayHeight = cutoutPathParserInfo.mPhysicalDisplayHeight;
            this.mDensity = cutoutPathParserInfo.mDensity;
            this.mCutoutSpec = cutoutPathParserInfo.mCutoutSpec;
            this.mRotation = cutoutPathParserInfo.mRotation;
            this.mScale = cutoutPathParserInfo.mScale;
            this.mPhysicalPixelDisplaySizeRatio = cutoutPathParserInfo.mPhysicalPixelDisplaySizeRatio;
        }

        public int getDisplayWidth() {
            return this.mDisplayWidth;
        }

        public int getDisplayHeight() {
            return this.mDisplayHeight;
        }

        public int getPhysicalDisplayWidth() {
            return this.mPhysicalDisplayWidth;
        }

        public int getPhysicalDisplayHeight() {
            return this.mPhysicalDisplayHeight;
        }

        public float getDensity() {
            return this.mDensity;
        }

        public java.lang.String getCutoutSpec() {
            return this.mCutoutSpec;
        }

        public int getRotation() {
            return this.mRotation;
        }

        public float getScale() {
            return this.mScale;
        }

        public float getPhysicalPixelDisplaySizeRatio() {
            return this.mPhysicalPixelDisplaySizeRatio;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasCutout() {
            return !this.mCutoutSpec.isEmpty();
        }

        public int hashCode() {
            return ((((((((((((((((0 + java.lang.Integer.hashCode(this.mDisplayWidth)) * 48271) + java.lang.Integer.hashCode(this.mDisplayHeight)) * 48271) + java.lang.Float.hashCode(this.mDensity)) * 48271) + this.mCutoutSpec.hashCode()) * 48271) + java.lang.Integer.hashCode(this.mRotation)) * 48271) + java.lang.Float.hashCode(this.mScale)) * 48271) + java.lang.Float.hashCode(this.mPhysicalPixelDisplaySizeRatio)) * 48271) + java.lang.Integer.hashCode(this.mPhysicalDisplayWidth)) * 48271) + java.lang.Integer.hashCode(this.mPhysicalDisplayHeight);
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.view.DisplayCutout.CutoutPathParserInfo)) {
                return false;
            }
            android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo = (android.view.DisplayCutout.CutoutPathParserInfo) obj;
            return this.mDisplayWidth == cutoutPathParserInfo.mDisplayWidth && this.mDisplayHeight == cutoutPathParserInfo.mDisplayHeight && this.mPhysicalDisplayWidth == cutoutPathParserInfo.mPhysicalDisplayWidth && this.mPhysicalDisplayHeight == cutoutPathParserInfo.mPhysicalDisplayHeight && this.mDensity == cutoutPathParserInfo.mDensity && this.mCutoutSpec.equals(cutoutPathParserInfo.mCutoutSpec) && this.mRotation == cutoutPathParserInfo.mRotation && this.mScale == cutoutPathParserInfo.mScale && this.mPhysicalPixelDisplaySizeRatio == cutoutPathParserInfo.mPhysicalPixelDisplaySizeRatio;
        }

        public java.lang.String toString() {
            return "CutoutPathParserInfo{displayWidth=" + this.mDisplayWidth + " displayHeight=" + this.mDisplayHeight + " physicalDisplayWidth=" + this.mPhysicalDisplayWidth + " physicalDisplayHeight=" + this.mPhysicalDisplayHeight + " density={" + this.mDensity + "} cutoutSpec={" + this.mCutoutSpec + "} rotation={" + this.mRotation + "} scale={" + this.mScale + "} physicalPixelDisplaySizeRatio={" + this.mPhysicalPixelDisplaySizeRatio + "}}";
        }
    }

    public DisplayCutout(android.graphics.Insets insets, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4) {
        this(getCopyOrRef(insets.toRect(), true), android.graphics.Insets.NONE, new android.view.DisplayCutout.Bounds(rect, rect2, rect3, rect4, true), (android.view.DisplayCutout.CutoutPathParserInfo) null, (int[]) null);
    }

    public DisplayCutout(android.graphics.Insets insets, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4, android.graphics.Insets insets2, android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo) {
        this(getCopyOrRef(insets.toRect(), true), insets2, new android.view.DisplayCutout.Bounds(rect, rect2, rect3, rect4, true), cutoutPathParserInfo, (int[]) null);
    }

    public DisplayCutout(android.graphics.Insets insets, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4, android.graphics.Insets insets2, android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo, int[] iArr) {
        this(insets.toRect(), insets2, new android.view.DisplayCutout.Bounds(rect, rect2, rect3, rect4, true), cutoutPathParserInfo, iArr);
    }

    public DisplayCutout(android.graphics.Insets insets, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4, android.graphics.Insets insets2) {
        this(getCopyOrRef(insets.toRect(), true), insets2, new android.view.DisplayCutout.Bounds(rect, rect2, rect3, rect4, true), (android.view.DisplayCutout.CutoutPathParserInfo) null, (int[]) null);
    }

    @java.lang.Deprecated
    public DisplayCutout(android.graphics.Rect rect, java.util.List<android.graphics.Rect> list) {
        this(getCopyOrRef(rect, true), android.graphics.Insets.NONE, new android.view.DisplayCutout.Bounds(extractBoundsFromList(rect, list), true), (android.view.DisplayCutout.CutoutPathParserInfo) null, (int[]) null);
    }

    private DisplayCutout(android.graphics.Rect rect, android.graphics.Insets insets, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4, android.graphics.Rect rect5, android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo, boolean z) {
        this(getCopyOrRef(rect, z), insets, new android.view.DisplayCutout.Bounds(rect2, rect3, rect4, rect5, z), cutoutPathParserInfo, (int[]) null);
    }

    private DisplayCutout(android.graphics.Rect rect, android.graphics.Insets insets, android.graphics.Rect[] rectArr, android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo, boolean z) {
        this(getCopyOrRef(rect, z), insets, new android.view.DisplayCutout.Bounds(rectArr, z), cutoutPathParserInfo, (int[]) null);
    }

    private DisplayCutout(android.graphics.Rect rect, android.graphics.Insets insets, android.view.DisplayCutout.Bounds bounds, android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo) {
        this(rect, insets, bounds, cutoutPathParserInfo, (int[]) null);
    }

    private DisplayCutout(android.graphics.Rect rect, android.graphics.Insets insets, android.view.DisplayCutout.Bounds bounds, android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo, int[] iArr) {
        this.mSafeInsets = rect;
        this.mWaterfallInsets = insets == null ? android.graphics.Insets.NONE : insets;
        this.mBounds = bounds;
        this.mCutoutPathParserInfo = cutoutPathParserInfo == null ? EMPTY_PARSER_INFO : cutoutPathParserInfo;
        this.mSideOverrides = iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.Rect getCopyOrRef(android.graphics.Rect rect, boolean z) {
        if (rect == null) {
            return ZERO_RECT;
        }
        if (z) {
            return new android.graphics.Rect(rect);
        }
        return rect;
    }

    public android.graphics.Insets getWaterfallInsets() {
        return this.mWaterfallInsets;
    }

    public static android.graphics.Rect[] extractBoundsFromList(android.graphics.Rect rect, java.util.List<android.graphics.Rect> list) {
        android.graphics.Rect[] rectArr = new android.graphics.Rect[4];
        for (int i = 0; i < 4; i++) {
            rectArr[i] = ZERO_RECT;
        }
        if (rect != null && list != null) {
            boolean z = rect.top > 0 || rect.bottom > 0;
            for (android.graphics.Rect rect2 : list) {
                if (z) {
                    if (rect2.top == 0) {
                        rectArr[1] = rect2;
                    } else {
                        rectArr[3] = rect2;
                    }
                } else if (rect2.left == 0) {
                    rectArr[0] = rect2;
                } else {
                    rectArr[2] = rect2;
                }
            }
        }
        return rectArr;
    }

    public boolean isBoundsEmpty() {
        return this.mBounds.isEmpty();
    }

    public boolean isEmpty() {
        return this.mSafeInsets.equals(ZERO_RECT);
    }

    public int getSafeInsetTop() {
        return this.mSafeInsets.top;
    }

    public int getSafeInsetBottom() {
        return this.mSafeInsets.bottom;
    }

    public int getSafeInsetLeft() {
        return this.mSafeInsets.left;
    }

    public int getSafeInsetRight() {
        return this.mSafeInsets.right;
    }

    public android.graphics.Rect getSafeInsets() {
        return new android.graphics.Rect(this.mSafeInsets);
    }

    public java.util.List<android.graphics.Rect> getBoundingRects() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.graphics.Rect rect : getBoundingRectsAll()) {
            if (!rect.isEmpty()) {
                arrayList.add(new android.graphics.Rect(rect));
            }
        }
        return arrayList;
    }

    public android.graphics.Rect[] getBoundingRectsAll() {
        return this.mBounds.getRects();
    }

    public android.graphics.Rect getBoundingRectLeft() {
        return this.mBounds.getRect(0);
    }

    public android.graphics.Rect getBoundingRectTop() {
        return this.mBounds.getRect(1);
    }

    public android.graphics.Rect getBoundingRectRight() {
        return this.mBounds.getRect(2);
    }

    public android.graphics.Rect getBoundingRectBottom() {
        return this.mBounds.getRect(3);
    }

    public android.graphics.Path getCutoutPath() {
        if (!this.mCutoutPathParserInfo.hasCutout()) {
            return null;
        }
        synchronized (CACHE_LOCK) {
            if (this.mCutoutPathParserInfo.equals(sCachedCutoutPathParserInfo)) {
                return sCachedCutoutPath;
            }
            android.graphics.Path path = new android.view.CutoutSpecification.Parser(this.mCutoutPathParserInfo.getDensity(), this.mCutoutPathParserInfo.getPhysicalDisplayWidth(), this.mCutoutPathParserInfo.getPhysicalDisplayHeight(), this.mCutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio()).parse(this.mCutoutPathParserInfo.getCutoutSpec()).getPath();
            if (path == null || path.isEmpty()) {
                return null;
            }
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            if (this.mCutoutPathParserInfo.getRotation() != 0) {
                android.util.RotationUtils.transformPhysicalToLogicalCoordinates(this.mCutoutPathParserInfo.getRotation(), this.mCutoutPathParserInfo.getDisplayWidth(), this.mCutoutPathParserInfo.getDisplayHeight(), matrix);
            }
            matrix.postScale(this.mCutoutPathParserInfo.getScale(), this.mCutoutPathParserInfo.getScale());
            path.transform(matrix);
            synchronized (CACHE_LOCK) {
                sCachedCutoutPathParserInfo = new android.view.DisplayCutout.CutoutPathParserInfo(this.mCutoutPathParserInfo);
                sCachedCutoutPath = path;
            }
            return path;
        }
    }

    public android.view.DisplayCutout.CutoutPathParserInfo getCutoutPathParserInfo() {
        return this.mCutoutPathParserInfo;
    }

    public int hashCode() {
        return ((((((((0 + this.mSafeInsets.hashCode()) * 48271) + this.mBounds.hashCode()) * 48271) + this.mWaterfallInsets.hashCode()) * 48271) + this.mCutoutPathParserInfo.hashCode()) * 48271) + java.util.Arrays.hashCode(this.mSideOverrides);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.view.DisplayCutout)) {
            return false;
        }
        android.view.DisplayCutout displayCutout = (android.view.DisplayCutout) obj;
        return this.mSafeInsets.equals(displayCutout.mSafeInsets) && this.mBounds.equals(displayCutout.mBounds) && this.mWaterfallInsets.equals(displayCutout.mWaterfallInsets) && this.mCutoutPathParserInfo.equals(displayCutout.mCutoutPathParserInfo) && java.util.Arrays.equals(this.mSideOverrides, displayCutout.mSideOverrides);
    }

    public java.lang.String toString() {
        return "DisplayCutout{insets=" + this.mSafeInsets + " waterfall=" + this.mWaterfallInsets + " boundingRect={" + this.mBounds + "} cutoutPathParserInfo={" + this.mCutoutPathParserInfo + "} sideOverrides=" + sideOverridesToString(this.mSideOverrides) + "}";
    }

    private static java.lang.String sideOverridesToString(int[] iArr) {
        if (iArr == null) {
            return "null";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{");
        int length = iArr.length;
        if (length != 4) {
            sb.append("length=").append(iArr.length).append(". ");
        }
        boolean z = false;
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            if (i2 != -1) {
                if (z) {
                    sb.append(", ");
                }
                sb.append(android.view.Surface.rotationToString(i)).append(": ");
                switch (i2) {
                    case 0:
                        sb.append("left");
                        break;
                    case 1:
                        sb.append(SIDE_STRING_TOP);
                        break;
                    case 2:
                        sb.append("right");
                        break;
                    case 3:
                        sb.append(SIDE_STRING_BOTTOM);
                        break;
                }
                z = true;
            }
        }
        return sb.append("}").toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        this.mSafeInsets.dumpDebug(protoOutputStream, 1146756268033L);
        this.mBounds.getRect(0).dumpDebug(protoOutputStream, 1146756268035L);
        this.mBounds.getRect(1).dumpDebug(protoOutputStream, 1146756268036L);
        this.mBounds.getRect(2).dumpDebug(protoOutputStream, 1146756268037L);
        this.mBounds.getRect(3).dumpDebug(protoOutputStream, 1146756268038L);
        this.mWaterfallInsets.toRect().dumpDebug(protoOutputStream, 1146756268039L);
        if (this.mSideOverrides != null) {
            for (int i : this.mSideOverrides) {
                protoOutputStream.write(android.view.DisplayCutoutProto.SIDE_OVERRIDES, i);
            }
        }
        protoOutputStream.end(start);
    }

    public android.view.DisplayCutout inset(int i, int i2, int i3, int i4) {
        if ((i == 0 && i2 == 0 && i3 == 0 && i4 == 0) || (isBoundsEmpty() && this.mWaterfallInsets.equals(android.graphics.Insets.NONE))) {
            return this;
        }
        android.graphics.Rect insetInsets = insetInsets(i, i2, i3, i4, new android.graphics.Rect(this.mSafeInsets));
        if (i == 0 && i2 == 0 && this.mSafeInsets.equals(insetInsets)) {
            return this;
        }
        android.graphics.Rect insetInsets2 = insetInsets(i, i2, i3, i4, this.mWaterfallInsets.toRect());
        android.graphics.Rect[] rects = this.mBounds.getRects();
        for (int i5 = 0; i5 < rects.length; i5++) {
            if (!rects[i5].equals(ZERO_RECT)) {
                rects[i5].offset(-i, -i2);
            }
        }
        return new android.view.DisplayCutout(insetInsets, android.graphics.Insets.of(insetInsets2), rects, this.mCutoutPathParserInfo, false);
    }

    private android.graphics.Rect insetInsets(int i, int i2, int i3, int i4, android.graphics.Rect rect) {
        if (i2 > 0 || rect.top > 0) {
            rect.top = atLeastZero(rect.top - i2);
        }
        if (i4 > 0 || rect.bottom > 0) {
            rect.bottom = atLeastZero(rect.bottom - i4);
        }
        if (i > 0 || rect.left > 0) {
            rect.left = atLeastZero(rect.left - i);
        }
        if (i3 > 0 || rect.right > 0) {
            rect.right = atLeastZero(rect.right - i3);
        }
        return rect;
    }

    public android.view.DisplayCutout replaceSafeInsets(android.graphics.Rect rect) {
        return new android.view.DisplayCutout(new android.graphics.Rect(rect), this.mWaterfallInsets, this.mBounds, this.mCutoutPathParserInfo, this.mSideOverrides);
    }

    private static int atLeastZero(int i) {
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public static android.view.DisplayCutout fromBoundingRect(int i, int i2, int i3, int i4, int i5) {
        android.graphics.Rect[] rectArr = new android.graphics.Rect[4];
        int i6 = 0;
        while (i6 < 4) {
            rectArr[i6] = i5 == i6 ? new android.graphics.Rect(i, i2, i3, i4) : new android.graphics.Rect();
            i6++;
        }
        return new android.view.DisplayCutout(ZERO_RECT, android.graphics.Insets.NONE, rectArr, (android.view.DisplayCutout.CutoutPathParserInfo) null, false);
    }

    public static android.view.DisplayCutout constructDisplayCutout(android.graphics.Rect[] rectArr, android.graphics.Insets insets, android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo) {
        return new android.view.DisplayCutout(ZERO_RECT, insets, rectArr, cutoutPathParserInfo, false);
    }

    public static android.view.DisplayCutout fromBounds(android.graphics.Rect[] rectArr) {
        return new android.view.DisplayCutout(ZERO_RECT, android.graphics.Insets.NONE, rectArr, (android.view.DisplayCutout.CutoutPathParserInfo) null, false);
    }

    private static java.lang.String getDisplayCutoutPath(android.content.res.Resources resources, java.lang.String str) {
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        java.lang.String[] stringArray = resources.getStringArray(com.android.internal.R.array.config_displayCutoutPathArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < stringArray.length) {
            return stringArray[displayUniqueIdConfigIndex];
        }
        return resources.getString(com.android.internal.R.string.config_mainBuiltInDisplayCutout);
    }

    private static java.lang.String getDisplayCutoutApproximationRect(android.content.res.Resources resources, java.lang.String str) {
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        java.lang.String[] stringArray = resources.getStringArray(com.android.internal.R.array.config_displayCutoutApproximationRectArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < stringArray.length) {
            return stringArray[displayUniqueIdConfigIndex];
        }
        return resources.getString(com.android.internal.R.string.config_mainBuiltInDisplayCutoutRectApproximation);
    }

    public static boolean getMaskBuiltInDisplayCutout(android.content.res.Resources resources, java.lang.String str) {
        boolean z;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_maskBuiltInDisplayCutoutArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            z = obtainTypedArray.getBoolean(displayUniqueIdConfigIndex, false);
        } else {
            z = resources.getBoolean(com.android.internal.R.bool.config_maskMainBuiltInDisplayCutout);
        }
        obtainTypedArray.recycle();
        return z;
    }

    public static boolean getFillBuiltInDisplayCutout(android.content.res.Resources resources, java.lang.String str) {
        boolean z;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_fillBuiltInDisplayCutoutArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            z = obtainTypedArray.getBoolean(displayUniqueIdConfigIndex, false);
        } else {
            z = resources.getBoolean(com.android.internal.R.bool.config_fillMainBuiltInDisplayCutout);
        }
        obtainTypedArray.recycle();
        return z;
    }

    private static android.graphics.Insets getWaterfallInsets(android.content.res.Resources resources, java.lang.String str) {
        int i;
        android.graphics.Insets loadWaterfallInset;
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_waterfallCutoutArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            i = obtainTypedArray.getResourceId(displayUniqueIdConfigIndex, 0);
        } else {
            i = 0;
        }
        if (i != 0) {
            android.content.res.TypedArray obtainTypedArray2 = resources.obtainTypedArray(i);
            loadWaterfallInset = android.graphics.Insets.of(obtainTypedArray2.getDimensionPixelSize(0, 0), obtainTypedArray2.getDimensionPixelSize(1, 0), obtainTypedArray2.getDimensionPixelSize(2, 0), obtainTypedArray2.getDimensionPixelSize(3, 0));
            obtainTypedArray2.recycle();
        } else {
            loadWaterfallInset = loadWaterfallInset(resources);
        }
        obtainTypedArray.recycle();
        return loadWaterfallInset;
    }

    private static int[] getDisplayCutoutSideOverrides(android.content.res.Resources resources, java.lang.String str) throws java.lang.IllegalArgumentException {
        int i;
        int[] intArray;
        if (!com.android.window.flags.Flags.movableCutoutConfiguration()) {
            return null;
        }
        int displayUniqueIdConfigIndex = android.util.DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(com.android.internal.R.array.config_displayCutoutSideOverrideArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            i = obtainTypedArray.getResourceId(displayUniqueIdConfigIndex, 0);
        } else {
            i = 0;
        }
        if (i != 0) {
            intArray = obtainTypedArray.getResources().getIntArray(i);
        } else {
            intArray = resources.getIntArray(com.android.internal.R.array.config_mainBuiltInDisplayCutoutSideOverride);
        }
        obtainTypedArray.recycle();
        if (intArray.length == 0) {
            return INVALID_OVERRIDES;
        }
        if (intArray.length != 4) {
            throw new java.lang.IllegalArgumentException("Invalid side override definition, exact 4 overrides required: " + java.util.Arrays.toString(intArray));
        }
        for (int i2 = 0; i2 <= 3; i2++) {
            if (intArray[i2] < 0 || intArray[i2] >= 4) {
                throw new java.lang.IllegalArgumentException("Invalid side override definition: " + java.util.Arrays.toString(intArray));
            }
        }
        return intArray;
    }

    public static android.view.DisplayCutout fromResourcesRectApproximation(android.content.res.Resources resources, java.lang.String str, int i, int i2, int i3, int i4) {
        return pathAndDisplayCutoutFromSpec(getDisplayCutoutPath(resources, str), getDisplayCutoutApproximationRect(resources, str), i, i2, i3, i4, android.util.DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f, getWaterfallInsets(resources, str), getDisplayCutoutSideOverrides(resources, str)).second;
    }

    public static android.view.DisplayCutout fromSpec(java.lang.String str, int i, int i2, float f, android.graphics.Insets insets, int[] iArr) {
        return pathAndDisplayCutoutFromSpec(str, null, i, i2, i, i2, f, insets, iArr).second;
    }

    private static android.util.Pair<android.graphics.Path, android.view.DisplayCutout> pathAndDisplayCutoutFromSpec(java.lang.String str, java.lang.String str2, int i, int i2, int i3, int i4, float f, android.graphics.Insets insets, int[] iArr) {
        java.lang.String str3 = str2 != null ? str2 : str;
        if (android.text.TextUtils.isEmpty(str3) && insets.equals(android.graphics.Insets.NONE)) {
            return NULL_PAIR;
        }
        float physicalPixelDisplaySizeRatio = android.util.DisplayUtils.getPhysicalPixelDisplaySizeRatio(i, i2, i3, i4);
        synchronized (CACHE_LOCK) {
            if (str3.equals(sCachedSpec) && sCachedDisplayWidth == i3 && sCachedDisplayHeight == i4 && sCachedDensity == f && insets.equals(sCachedWaterfallInsets) && sCachedPhysicalPixelDisplaySizeRatio == physicalPixelDisplaySizeRatio && java.util.Arrays.equals(sCachedSideOverrides, iArr)) {
                return sCachedCutout;
            }
            java.lang.String trim = str3.trim();
            android.view.CutoutSpecification parse = new android.view.CutoutSpecification.Parser(f, i, i2, physicalPixelDisplaySizeRatio).parse(trim);
            android.graphics.Rect safeInset = parse.getSafeInset();
            android.graphics.Rect leftBound = parse.getLeftBound();
            android.graphics.Rect topBound = parse.getTopBound();
            android.graphics.Rect rightBound = parse.getRightBound();
            android.graphics.Rect bottomBound = parse.getBottomBound();
            if (!insets.equals(android.graphics.Insets.NONE)) {
                safeInset.set(java.lang.Math.max(insets.left, safeInset.left), java.lang.Math.max(insets.top, safeInset.top), java.lang.Math.max(insets.right, safeInset.right), java.lang.Math.max(insets.bottom, safeInset.bottom));
            }
            android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo = new android.view.DisplayCutout.CutoutPathParserInfo(i3, i4, i, i2, f, str.trim(), 0, 1.0f, physicalPixelDisplaySizeRatio);
            int sideOverride = getSideOverride(iArr, 0);
            android.graphics.Rect[] rects = new android.view.DisplayCutout.Bounds(leftBound, topBound, rightBound, bottomBound, false).getRects();
            int rotationToOverride = getRotationToOverride(sideOverride, rects, 0);
            if (rotationToOverride != 0) {
                java.util.Collections.rotate(java.util.Arrays.asList(rects), rotationToOverride);
            }
            android.util.Pair<android.graphics.Path, android.view.DisplayCutout> pair = new android.util.Pair<>(parse.getPath(), new android.view.DisplayCutout(computeSafeInsets(i3, i4, insets, rects), insets, new android.view.DisplayCutout.Bounds(rects[0], rects[1], rects[2], rects[3], false), cutoutPathParserInfo, iArr));
            synchronized (CACHE_LOCK) {
                sCachedSpec = trim;
                sCachedDisplayWidth = i3;
                sCachedDisplayHeight = i4;
                sCachedDensity = f;
                sCachedCutout = pair;
                sCachedWaterfallInsets = insets;
                sCachedPhysicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
                sCachedSideOverrides = iArr;
            }
            return pair;
        }
    }

    private static android.graphics.Insets loadWaterfallInset(android.content.res.Resources resources) {
        return android.graphics.Insets.of(resources.getDimensionPixelSize(com.android.internal.R.dimen.waterfall_display_left_edge_size), resources.getDimensionPixelSize(com.android.internal.R.dimen.waterfall_display_top_edge_size), resources.getDimensionPixelSize(com.android.internal.R.dimen.waterfall_display_right_edge_size), resources.getDimensionPixelSize(com.android.internal.R.dimen.waterfall_display_bottom_edge_size));
    }

    public android.view.DisplayCutout getRotated(int i, int i2, int i3, int i4) {
        if (this == NO_CUTOUT) {
            return NO_CUTOUT;
        }
        int deltaRotation = android.util.RotationUtils.deltaRotation(i3, i4);
        if (deltaRotation == 0) {
            return this;
        }
        android.graphics.Insets rotateInsets = android.util.RotationUtils.rotateInsets(getWaterfallInsets(), deltaRotation);
        android.graphics.Rect[] boundingRectsAll = getBoundingRectsAll();
        int i5 = i;
        android.graphics.Rect rect = new android.graphics.Rect(0, 0, i5, i2);
        for (int i6 = 0; i6 < boundingRectsAll.length; i6++) {
            if (!boundingRectsAll[i6].isEmpty()) {
                android.util.RotationUtils.rotateBounds(boundingRectsAll[i6], rect, deltaRotation);
            }
        }
        java.util.Collections.rotate(java.util.Arrays.asList(boundingRectsAll), getRotationToOverride(getSideOverride(this.mSideOverrides, i4), boundingRectsAll, -deltaRotation));
        android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo = getCutoutPathParserInfo();
        android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo2 = new android.view.DisplayCutout.CutoutPathParserInfo(cutoutPathParserInfo.getDisplayWidth(), cutoutPathParserInfo.getDisplayHeight(), cutoutPathParserInfo.getPhysicalDisplayWidth(), cutoutPathParserInfo.getPhysicalDisplayHeight(), cutoutPathParserInfo.getDensity(), cutoutPathParserInfo.getCutoutSpec(), i4, cutoutPathParserInfo.getScale(), cutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio());
        boolean z = deltaRotation % 2 != 0;
        int i7 = z ? i2 : i5;
        if (!z) {
            i5 = i2;
        }
        android.view.DisplayCutout constructDisplayCutout = constructDisplayCutout(boundingRectsAll, rotateInsets, cutoutPathParserInfo2);
        android.graphics.Rect computeSafeInsets = computeSafeInsets(i7, i5, constructDisplayCutout);
        constructDisplayCutout.mSideOverrides = this.mSideOverrides;
        return constructDisplayCutout.replaceSafeInsets(computeSafeInsets);
    }

    private static int getSideOverride(int[] iArr, int i) {
        if (iArr == null || iArr.length != 4) {
            return -1;
        }
        return iArr[i];
    }

    private static int getRotationToOverride(int i, android.graphics.Rect[] rectArr, int i2) {
        if (i == -1) {
            return i2;
        }
        int i3 = -1;
        for (int i4 = 0; i4 <= 3; i4++) {
            if (!rectArr[i4].isEmpty()) {
                if (i3 != -1) {
                    return i2;
                }
                i3 = i4;
            }
        }
        if (i3 == -1) {
            return i2;
        }
        int i5 = i - i3;
        if (i5 < 0) {
            return i5 + 4;
        }
        return i5;
    }

    public static android.graphics.Rect computeSafeInsets(int i, int i2, android.view.DisplayCutout displayCutout) {
        return computeSafeInsets(i, i2, displayCutout.getWaterfallInsets(), displayCutout.getBoundingRectsAll());
    }

    private static android.graphics.Rect computeSafeInsets(int i, int i2, android.graphics.Insets insets, android.graphics.Rect[] rectArr) {
        if (i == i2) {
            throw new java.lang.UnsupportedOperationException("not implemented: display=" + i + "x" + i2 + " bounding rects=" + java.util.Arrays.toString(rectArr));
        }
        return new android.graphics.Rect(java.lang.Math.max(insets.left, findCutoutInsetForSide(i, i2, rectArr[0], 3)), java.lang.Math.max(insets.top, findCutoutInsetForSide(i, i2, rectArr[1], 48)), java.lang.Math.max(insets.right, findCutoutInsetForSide(i, i2, rectArr[2], 5)), java.lang.Math.max(insets.bottom, findCutoutInsetForSide(i, i2, rectArr[3], 80)));
    }

    private static int findCutoutInsetForSide(int i, int i2, android.graphics.Rect rect, int i3) {
        if (rect.isEmpty()) {
            return 0;
        }
        switch (i3) {
            case 3:
                return java.lang.Math.max(0, rect.right);
            case 5:
                return java.lang.Math.max(0, i - rect.left);
            case 48:
                return java.lang.Math.max(0, rect.bottom);
            case 80:
                return java.lang.Math.max(0, i2 - rect.top);
            default:
                throw new java.lang.IllegalArgumentException("unknown gravity: " + i3);
        }
    }

    public static final class ParcelableWrapper implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.DisplayCutout.ParcelableWrapper> CREATOR = new android.os.Parcelable.Creator<android.view.DisplayCutout.ParcelableWrapper>() { // from class: android.view.DisplayCutout.ParcelableWrapper.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.DisplayCutout.ParcelableWrapper createFromParcel(android.os.Parcel parcel) {
                return new android.view.DisplayCutout.ParcelableWrapper(android.view.DisplayCutout.ParcelableWrapper.readCutoutFromParcel(parcel));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.DisplayCutout.ParcelableWrapper[] newArray(int i) {
                return new android.view.DisplayCutout.ParcelableWrapper[i];
            }
        };
        private android.view.DisplayCutout mInner;

        public ParcelableWrapper() {
            this(android.view.DisplayCutout.NO_CUTOUT);
        }

        public ParcelableWrapper(android.view.DisplayCutout displayCutout) {
            this.mInner = displayCutout;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            writeCutoutToParcel(this.mInner, parcel, i);
        }

        public static void writeCutoutToParcel(android.view.DisplayCutout displayCutout, android.os.Parcel parcel, int i) {
            if (displayCutout == null) {
                parcel.writeInt(-1);
                return;
            }
            if (displayCutout == android.view.DisplayCutout.NO_CUTOUT) {
                parcel.writeInt(0);
                return;
            }
            parcel.writeInt(1);
            parcel.writeTypedObject(displayCutout.mSafeInsets, i);
            parcel.writeTypedArray(displayCutout.mBounds.getRects(), i);
            parcel.writeTypedObject(displayCutout.mWaterfallInsets, i);
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getDisplayWidth());
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getDisplayHeight());
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getPhysicalDisplayWidth());
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getPhysicalDisplayHeight());
            parcel.writeFloat(displayCutout.mCutoutPathParserInfo.getDensity());
            parcel.writeString(displayCutout.mCutoutPathParserInfo.getCutoutSpec());
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getRotation());
            parcel.writeFloat(displayCutout.mCutoutPathParserInfo.getScale());
            parcel.writeFloat(displayCutout.mCutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio());
            parcel.writeIntArray(displayCutout.mSideOverrides);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.mInner = readCutoutFromParcel(parcel);
        }

        public static android.view.DisplayCutout readCutoutFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                return null;
            }
            if (readInt != 0) {
                android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                android.graphics.Rect[] rectArr = new android.graphics.Rect[4];
                parcel.readTypedArray(rectArr, android.graphics.Rect.CREATOR);
                return new android.view.DisplayCutout(rect, (android.graphics.Insets) parcel.readTypedObject(android.graphics.Insets.CREATOR), new android.view.DisplayCutout.Bounds(rectArr, false), new android.view.DisplayCutout.CutoutPathParserInfo(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readString(), parcel.readInt(), parcel.readFloat(), parcel.readFloat()), parcel.createIntArray());
            }
            return android.view.DisplayCutout.NO_CUTOUT;
        }

        public android.view.DisplayCutout get() {
            return this.mInner;
        }

        public void set(android.view.DisplayCutout.ParcelableWrapper parcelableWrapper) {
            this.mInner = parcelableWrapper.get();
        }

        public void set(android.view.DisplayCutout displayCutout) {
            this.mInner = displayCutout;
        }

        public void scale(float f) {
            android.graphics.Rect safeInsets = this.mInner.getSafeInsets();
            safeInsets.scale(f);
            android.view.DisplayCutout.Bounds bounds = new android.view.DisplayCutout.Bounds(this.mInner.mBounds.mRects, true);
            bounds.scale(f);
            android.graphics.Rect rect = this.mInner.mWaterfallInsets.toRect();
            rect.scale(f);
            this.mInner = new android.view.DisplayCutout(safeInsets, android.graphics.Insets.of(rect), bounds, new android.view.DisplayCutout.CutoutPathParserInfo(this.mInner.mCutoutPathParserInfo.getDisplayWidth(), this.mInner.mCutoutPathParserInfo.getDisplayHeight(), this.mInner.mCutoutPathParserInfo.getPhysicalDisplayWidth(), this.mInner.mCutoutPathParserInfo.getPhysicalDisplayHeight(), this.mInner.mCutoutPathParserInfo.getDensity(), this.mInner.mCutoutPathParserInfo.getCutoutSpec(), this.mInner.mCutoutPathParserInfo.getRotation(), f, this.mInner.mCutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio()), this.mInner.mSideOverrides);
        }

        public int hashCode() {
            return this.mInner.hashCode();
        }

        public boolean equals(java.lang.Object obj) {
            return (obj instanceof android.view.DisplayCutout.ParcelableWrapper) && this.mInner.equals(((android.view.DisplayCutout.ParcelableWrapper) obj).mInner);
        }

        public java.lang.String toString() {
            return java.lang.String.valueOf(this.mInner);
        }
    }

    public static final class Builder {
        private android.graphics.Path mCutoutPath;
        private android.graphics.Insets mSafeInsets = android.graphics.Insets.NONE;
        private android.graphics.Insets mWaterfallInsets = android.graphics.Insets.NONE;
        private final android.graphics.Rect mBoundingRectLeft = new android.graphics.Rect();
        private final android.graphics.Rect mBoundingRectTop = new android.graphics.Rect();
        private final android.graphics.Rect mBoundingRectRight = new android.graphics.Rect();
        private final android.graphics.Rect mBoundingRectBottom = new android.graphics.Rect();

        public android.view.DisplayCutout build() {
            android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo;
            if (this.mCutoutPath != null) {
                android.view.DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo2 = new android.view.DisplayCutout.CutoutPathParserInfo(0, 0, 0, 0, 0.0f, "test", 0, 1.0f, 1.0f);
                synchronized (android.view.DisplayCutout.CACHE_LOCK) {
                    android.view.DisplayCutout.sCachedCutoutPathParserInfo = cutoutPathParserInfo2;
                    android.view.DisplayCutout.sCachedCutoutPath = this.mCutoutPath;
                }
                cutoutPathParserInfo = cutoutPathParserInfo2;
            } else {
                cutoutPathParserInfo = null;
            }
            return new android.view.DisplayCutout(this.mSafeInsets.toRect(), this.mWaterfallInsets, this.mBoundingRectLeft, this.mBoundingRectTop, this.mBoundingRectRight, this.mBoundingRectBottom, cutoutPathParserInfo, false);
        }

        public android.view.DisplayCutout.Builder setSafeInsets(android.graphics.Insets insets) {
            this.mSafeInsets = insets;
            return this;
        }

        public android.view.DisplayCutout.Builder setWaterfallInsets(android.graphics.Insets insets) {
            this.mWaterfallInsets = insets;
            return this;
        }

        public android.view.DisplayCutout.Builder setBoundingRectLeft(android.graphics.Rect rect) {
            this.mBoundingRectLeft.set(rect);
            return this;
        }

        public android.view.DisplayCutout.Builder setBoundingRectTop(android.graphics.Rect rect) {
            this.mBoundingRectTop.set(rect);
            return this;
        }

        public android.view.DisplayCutout.Builder setBoundingRectRight(android.graphics.Rect rect) {
            this.mBoundingRectRight.set(rect);
            return this;
        }

        public android.view.DisplayCutout.Builder setBoundingRectBottom(android.graphics.Rect rect) {
            this.mBoundingRectBottom.set(rect);
            return this;
        }

        public android.view.DisplayCutout.Builder setCutoutPath(android.graphics.Path path) {
            this.mCutoutPath = path;
            return this;
        }
    }
}
