package android.view;

/* loaded from: classes4.dex */
public class CutoutSpecification {
    private static final java.lang.String BIND_LEFT_CUTOUT_MARKER = "@bind_left_cutout";
    private static final java.lang.String BIND_RIGHT_CUTOUT_MARKER = "@bind_right_cutout";
    private static final java.lang.String BOTTOM_MARKER = "@bottom";
    private static final java.lang.String CENTER_VERTICAL_MARKER = "@center_vertical";
    private static final java.lang.String CUTOUT_MARKER = "@cutout";
    private static final boolean DEBUG = false;
    private static final java.lang.String DP_MARKER = "@dp";
    private static final java.lang.String LEFT_MARKER = "@left";
    private static final char MARKER_START_CHAR = '@';
    private static final int MINIMAL_ACCEPTABLE_PATH_LENGTH = "H1V1Z".length();
    private static final java.lang.String RIGHT_MARKER = "@right";
    private static final java.lang.String TAG = "CutoutSpecification";
    private final android.graphics.Rect mBottomBound;
    private android.graphics.Insets mInsets;
    private final android.graphics.Rect mLeftBound;
    private final android.graphics.Path mPath;
    private final android.graphics.Rect mRightBound;
    private final android.graphics.Rect mTopBound;

    private CutoutSpecification(android.view.CutoutSpecification.Parser parser) {
        this.mPath = parser.mPath;
        this.mLeftBound = parser.mLeftBound;
        this.mTopBound = parser.mTopBound;
        this.mRightBound = parser.mRightBound;
        this.mBottomBound = parser.mBottomBound;
        this.mInsets = parser.mInsets;
        applyPhysicalPixelDisplaySizeRatio(parser.mPhysicalPixelDisplaySizeRatio);
    }

    private void applyPhysicalPixelDisplaySizeRatio(float f) {
        if (f == 1.0f) {
            return;
        }
        if (this.mPath != null && !this.mPath.isEmpty()) {
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            matrix.postScale(f, f);
            this.mPath.transform(matrix);
        }
        scaleBounds(this.mLeftBound, f);
        scaleBounds(this.mTopBound, f);
        scaleBounds(this.mRightBound, f);
        scaleBounds(this.mBottomBound, f);
        this.mInsets = scaleInsets(this.mInsets, f);
    }

    private void scaleBounds(android.graphics.Rect rect, float f) {
        if (rect != null && !rect.isEmpty()) {
            rect.scale(f);
        }
    }

    private android.graphics.Insets scaleInsets(android.graphics.Insets insets, float f) {
        return android.graphics.Insets.of((int) ((insets.left * f) + 0.5f), (int) ((insets.top * f) + 0.5f), (int) ((insets.right * f) + 0.5f), (int) ((insets.bottom * f) + 0.5f));
    }

    public android.graphics.Path getPath() {
        return this.mPath;
    }

    public android.graphics.Rect getLeftBound() {
        return this.mLeftBound;
    }

    public android.graphics.Rect getTopBound() {
        return this.mTopBound;
    }

    public android.graphics.Rect getRightBound() {
        return this.mRightBound;
    }

    public android.graphics.Rect getBottomBound() {
        return this.mBottomBound;
    }

    public android.graphics.Rect getSafeInset() {
        return this.mInsets.toRect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int decideWhichEdge(boolean z, boolean z2, boolean z3) {
        return z ? z2 ? z3 ? 48 : 80 : z3 ? 3 : 5 : z2 ? z3 ? 3 : 5 : z3 ? 48 : 80;
    }

    public static class Parser {
        private boolean mBindBottomCutout;
        private boolean mBindLeftCutout;
        private boolean mBindRightCutout;
        private android.graphics.Rect mBottomBound;
        private boolean mInDp;
        private android.graphics.Insets mInsets;
        private boolean mIsCloserToStartSide;
        private final boolean mIsShortEdgeOnTop;
        private boolean mIsTouchShortEdgeEnd;
        private boolean mIsTouchShortEdgeStart;
        private android.graphics.Rect mLeftBound;
        private final android.graphics.Matrix mMatrix;
        private android.graphics.Path mPath;
        private final int mPhysicalDisplayHeight;
        private final int mPhysicalDisplayWidth;
        private final float mPhysicalPixelDisplaySizeRatio;
        private boolean mPositionFromBottom;
        private boolean mPositionFromCenterVertical;
        private boolean mPositionFromLeft;
        private boolean mPositionFromRight;
        private android.graphics.Rect mRightBound;
        private int mSafeInsetBottom;
        private int mSafeInsetLeft;
        private int mSafeInsetRight;
        private int mSafeInsetTop;
        private final float mStableDensity;
        private final android.graphics.Rect mTmpRect;
        private final android.graphics.RectF mTmpRectF;
        private android.graphics.Rect mTopBound;

        public Parser(float f, int i, int i2) {
            this(f, i, i2, 1.0f);
        }

        Parser(float f, int i, int i2, float f2) {
            this.mTmpRect = new android.graphics.Rect();
            this.mTmpRectF = new android.graphics.RectF();
            this.mPositionFromLeft = false;
            this.mPositionFromRight = false;
            this.mPositionFromBottom = false;
            this.mPositionFromCenterVertical = false;
            this.mBindLeftCutout = false;
            this.mBindRightCutout = false;
            this.mBindBottomCutout = false;
            this.mStableDensity = f;
            this.mPhysicalDisplayWidth = i;
            this.mPhysicalDisplayHeight = i2;
            this.mPhysicalPixelDisplaySizeRatio = f2;
            this.mMatrix = new android.graphics.Matrix();
            this.mIsShortEdgeOnTop = this.mPhysicalDisplayWidth < this.mPhysicalDisplayHeight;
        }

        private void computeBoundsRectAndAddToRegion(android.graphics.Path path, android.graphics.Region region, android.graphics.Rect rect) {
            this.mTmpRectF.setEmpty();
            path.computeBounds(this.mTmpRectF, false);
            this.mTmpRectF.round(rect);
            region.op(rect, android.graphics.Region.Op.UNION);
        }

        private void resetStatus(java.lang.StringBuilder sb) {
            sb.setLength(0);
            this.mPositionFromBottom = false;
            this.mPositionFromLeft = false;
            this.mPositionFromRight = false;
            this.mPositionFromCenterVertical = false;
            this.mBindLeftCutout = false;
            this.mBindRightCutout = false;
            this.mBindBottomCutout = false;
        }

        private void translateMatrix() {
            float f;
            float f2 = 0.0f;
            if (this.mPositionFromRight) {
                f = this.mPhysicalDisplayWidth;
            } else if (this.mPositionFromLeft) {
                f = 0.0f;
            } else {
                f = this.mPhysicalDisplayWidth / 2.0f;
            }
            if (this.mPositionFromBottom) {
                f2 = this.mPhysicalDisplayHeight;
            } else if (this.mPositionFromCenterVertical) {
                f2 = this.mPhysicalDisplayHeight / 2.0f;
            }
            this.mMatrix.reset();
            if (this.mInDp) {
                this.mMatrix.postScale(this.mStableDensity, this.mStableDensity);
            }
            this.mMatrix.postTranslate(f, f2);
        }

        private int computeSafeInsets(int i, android.graphics.Rect rect) {
            if (i == 3 && rect.right > 0 && rect.right < this.mPhysicalDisplayWidth) {
                return rect.right;
            }
            if (i == 48 && rect.bottom > 0 && rect.bottom < this.mPhysicalDisplayHeight) {
                return rect.bottom;
            }
            if (i == 5 && rect.left > 0 && rect.left < this.mPhysicalDisplayWidth) {
                return this.mPhysicalDisplayWidth - rect.left;
            }
            if (i == 80 && rect.top > 0 && rect.top < this.mPhysicalDisplayHeight) {
                return this.mPhysicalDisplayHeight - rect.top;
            }
            return 0;
        }

        private void setSafeInset(int i, int i2) {
            if (i == 3) {
                this.mSafeInsetLeft = i2;
                return;
            }
            if (i == 48) {
                this.mSafeInsetTop = i2;
            } else if (i == 5) {
                this.mSafeInsetRight = i2;
            } else if (i == 80) {
                this.mSafeInsetBottom = i2;
            }
        }

        private int getSafeInset(int i) {
            if (i == 3) {
                return this.mSafeInsetLeft;
            }
            if (i == 48) {
                return this.mSafeInsetTop;
            }
            if (i == 5) {
                return this.mSafeInsetRight;
            }
            if (i == 80) {
                return this.mSafeInsetBottom;
            }
            return 0;
        }

        private android.graphics.Rect onSetEdgeCutout(boolean z, boolean z2, android.graphics.Rect rect) {
            int decideWhichEdge;
            if (z2) {
                decideWhichEdge = android.view.CutoutSpecification.decideWhichEdge(this.mIsShortEdgeOnTop, true, z);
            } else if (this.mIsTouchShortEdgeStart && this.mIsTouchShortEdgeEnd) {
                decideWhichEdge = android.view.CutoutSpecification.decideWhichEdge(this.mIsShortEdgeOnTop, false, z);
            } else {
                decideWhichEdge = (this.mIsTouchShortEdgeStart || this.mIsTouchShortEdgeEnd) ? android.view.CutoutSpecification.decideWhichEdge(this.mIsShortEdgeOnTop, true, this.mIsCloserToStartSide) : android.view.CutoutSpecification.decideWhichEdge(this.mIsShortEdgeOnTop, z2, z);
            }
            int safeInset = getSafeInset(decideWhichEdge);
            int computeSafeInsets = computeSafeInsets(decideWhichEdge, rect);
            if (safeInset < computeSafeInsets) {
                setSafeInset(decideWhichEdge, computeSafeInsets);
            }
            return new android.graphics.Rect(rect);
        }

        private void setEdgeCutout(android.graphics.Path path) {
            if (this.mBindRightCutout && this.mRightBound == null) {
                this.mRightBound = onSetEdgeCutout(false, !this.mIsShortEdgeOnTop, this.mTmpRect);
            } else if (this.mBindLeftCutout && this.mLeftBound == null) {
                this.mLeftBound = onSetEdgeCutout(true, !this.mIsShortEdgeOnTop, this.mTmpRect);
            } else if (this.mBindBottomCutout && this.mBottomBound == null) {
                this.mBottomBound = onSetEdgeCutout(false, this.mIsShortEdgeOnTop, this.mTmpRect);
            } else if (!this.mBindBottomCutout && !this.mBindLeftCutout && !this.mBindRightCutout && this.mTopBound == null) {
                this.mTopBound = onSetEdgeCutout(true, this.mIsShortEdgeOnTop, this.mTmpRect);
            } else {
                return;
            }
            if (this.mPath != null) {
                this.mPath.addPath(path);
            } else {
                this.mPath = path;
            }
        }

        private void parseSvgPathSpec(android.graphics.Region region, java.lang.String str) {
            if (android.text.TextUtils.length(str) < android.view.CutoutSpecification.MINIMAL_ACCEPTABLE_PATH_LENGTH) {
                android.util.Log.e(android.view.CutoutSpecification.TAG, "According to SVG definition, it shouldn't happen");
                return;
            }
            translateMatrix();
            android.graphics.Path createPathFromPathData = android.util.PathParser.createPathFromPathData(str);
            createPathFromPathData.transform(this.mMatrix);
            computeBoundsRectAndAddToRegion(createPathFromPathData, region, this.mTmpRect);
            if (this.mTmpRect.isEmpty()) {
                return;
            }
            if (this.mIsShortEdgeOnTop) {
                this.mIsTouchShortEdgeStart = this.mTmpRect.top <= 0;
                this.mIsTouchShortEdgeEnd = this.mTmpRect.bottom >= this.mPhysicalDisplayHeight;
                this.mIsCloserToStartSide = this.mTmpRect.centerY() < this.mPhysicalDisplayHeight / 2;
            } else {
                this.mIsTouchShortEdgeStart = this.mTmpRect.left <= 0;
                this.mIsTouchShortEdgeEnd = this.mTmpRect.right >= this.mPhysicalDisplayWidth;
                this.mIsCloserToStartSide = this.mTmpRect.centerX() < this.mPhysicalDisplayWidth / 2;
            }
            setEdgeCutout(createPathFromPathData);
        }

        private void parseSpecWithoutDp(java.lang.String str) {
            android.graphics.Region obtain = android.graphics.Region.obtain();
            java.lang.StringBuilder sb = null;
            int i = 0;
            while (true) {
                int indexOf = str.indexOf(64, i);
                if (indexOf == -1) {
                    break;
                }
                if (sb == null) {
                    sb = new java.lang.StringBuilder(str.length());
                }
                sb.append((java.lang.CharSequence) str, i, indexOf);
                if (str.startsWith(android.view.CutoutSpecification.LEFT_MARKER, indexOf)) {
                    if (!this.mPositionFromRight) {
                        this.mPositionFromLeft = true;
                    }
                    i = indexOf + android.view.CutoutSpecification.LEFT_MARKER.length();
                } else if (str.startsWith(android.view.CutoutSpecification.RIGHT_MARKER, indexOf)) {
                    if (!this.mPositionFromLeft) {
                        this.mPositionFromRight = true;
                    }
                    i = indexOf + android.view.CutoutSpecification.RIGHT_MARKER.length();
                } else if (str.startsWith(android.view.CutoutSpecification.BOTTOM_MARKER, indexOf)) {
                    parseSvgPathSpec(obtain, sb.toString());
                    int length = indexOf + android.view.CutoutSpecification.BOTTOM_MARKER.length();
                    resetStatus(sb);
                    this.mBindBottomCutout = true;
                    this.mPositionFromBottom = true;
                    i = length;
                } else if (str.startsWith(android.view.CutoutSpecification.CENTER_VERTICAL_MARKER, indexOf)) {
                    parseSvgPathSpec(obtain, sb.toString());
                    int length2 = indexOf + android.view.CutoutSpecification.CENTER_VERTICAL_MARKER.length();
                    resetStatus(sb);
                    this.mPositionFromCenterVertical = true;
                    i = length2;
                } else if (str.startsWith(android.view.CutoutSpecification.CUTOUT_MARKER, indexOf)) {
                    parseSvgPathSpec(obtain, sb.toString());
                    int length3 = indexOf + android.view.CutoutSpecification.CUTOUT_MARKER.length();
                    resetStatus(sb);
                    i = length3;
                } else if (str.startsWith(android.view.CutoutSpecification.BIND_LEFT_CUTOUT_MARKER, indexOf)) {
                    this.mBindBottomCutout = false;
                    this.mBindRightCutout = false;
                    this.mBindLeftCutout = true;
                    i = indexOf + android.view.CutoutSpecification.BIND_LEFT_CUTOUT_MARKER.length();
                } else if (str.startsWith(android.view.CutoutSpecification.BIND_RIGHT_CUTOUT_MARKER, indexOf)) {
                    this.mBindBottomCutout = false;
                    this.mBindLeftCutout = false;
                    this.mBindRightCutout = true;
                    i = indexOf + android.view.CutoutSpecification.BIND_RIGHT_CUTOUT_MARKER.length();
                } else {
                    i = indexOf + 1;
                }
            }
            if (sb == null) {
                parseSvgPathSpec(obtain, str);
            } else {
                sb.append((java.lang.CharSequence) str, i, str.length());
                parseSvgPathSpec(obtain, sb.toString());
            }
            obtain.recycle();
        }

        public android.view.CutoutSpecification parse(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            int lastIndexOf = str.lastIndexOf(android.view.CutoutSpecification.DP_MARKER);
            this.mInDp = lastIndexOf != -1;
            if (lastIndexOf != -1) {
                str = str.substring(0, lastIndexOf) + str.substring(lastIndexOf + android.view.CutoutSpecification.DP_MARKER.length());
            }
            parseSpecWithoutDp(str);
            this.mInsets = android.graphics.Insets.of(this.mSafeInsetLeft, this.mSafeInsetTop, this.mSafeInsetRight, this.mSafeInsetBottom);
            return new android.view.CutoutSpecification(this);
        }
    }
}
