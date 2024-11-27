package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class RelativeLayout extends android.view.ViewGroup {
    public static final int ABOVE = 2;
    public static final int ALIGN_BASELINE = 4;
    public static final int ALIGN_BOTTOM = 8;
    public static final int ALIGN_END = 19;
    public static final int ALIGN_LEFT = 5;
    public static final int ALIGN_PARENT_BOTTOM = 12;
    public static final int ALIGN_PARENT_END = 21;
    public static final int ALIGN_PARENT_LEFT = 9;
    public static final int ALIGN_PARENT_RIGHT = 11;
    public static final int ALIGN_PARENT_START = 20;
    public static final int ALIGN_PARENT_TOP = 10;
    public static final int ALIGN_RIGHT = 7;
    public static final int ALIGN_START = 18;
    public static final int ALIGN_TOP = 6;
    public static final int BELOW = 3;
    public static final int CENTER_HORIZONTAL = 14;
    public static final int CENTER_IN_PARENT = 13;
    public static final int CENTER_VERTICAL = 15;
    private static final int DEFAULT_WIDTH = 65536;
    public static final int END_OF = 17;
    public static final int LEFT_OF = 0;
    public static final int RIGHT_OF = 1;
    public static final int START_OF = 16;
    public static final int TRUE = -1;
    private static final int VALUE_NOT_SET = Integer.MIN_VALUE;
    private static final int VERB_COUNT = 22;
    private boolean mAllowBrokenMeasureSpecs;
    private android.view.View mBaselineView;
    private final android.graphics.Rect mContentBounds;
    private boolean mDirtyHierarchy;
    private final android.widget.RelativeLayout.DependencyGraph mGraph;
    private int mGravity;
    private int mIgnoreGravity;
    private boolean mMeasureVerticalWithPaddingMargin;
    private final android.graphics.Rect mSelfBounds;
    private android.view.View[] mSortedHorizontalChildren;
    private android.view.View[] mSortedVerticalChildren;
    private java.util.SortedSet<android.view.View> mTopToBottomLeftToRightSet;
    private static final int[] RULES_VERTICAL = {2, 3, 4, 6, 8};
    private static final int[] RULES_HORIZONTAL = {0, 1, 5, 7, 16, 17, 18, 19};

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.RelativeLayout> {
        private int mGravityId;
        private int mIgnoreGravityId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mIgnoreGravityId = propertyMapper.mapInt("ignoreGravity", 16843263);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.RelativeLayout relativeLayout, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readGravity(this.mGravityId, relativeLayout.getGravity());
            propertyReader.readInt(this.mIgnoreGravityId, relativeLayout.getIgnoreGravity());
        }
    }

    public RelativeLayout(android.content.Context context) {
        this(context, null);
    }

    public RelativeLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RelativeLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RelativeLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mBaselineView = null;
        this.mGravity = 8388659;
        this.mContentBounds = new android.graphics.Rect();
        this.mSelfBounds = new android.graphics.Rect();
        this.mTopToBottomLeftToRightSet = null;
        this.mGraph = new android.widget.RelativeLayout.DependencyGraph();
        this.mAllowBrokenMeasureSpecs = false;
        this.mMeasureVerticalWithPaddingMargin = false;
        initFromAttributes(context, attributeSet, i, i2);
        queryCompatibilityModes(context);
    }

    private void initFromAttributes(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.RelativeLayout, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.RelativeLayout, attributeSet, obtainStyledAttributes, i, i2);
        this.mIgnoreGravity = obtainStyledAttributes.getResourceId(1, -1);
        this.mGravity = obtainStyledAttributes.getInt(0, this.mGravity);
        obtainStyledAttributes.recycle();
    }

    private void queryCompatibilityModes(android.content.Context context) {
        int i = context.getApplicationInfo().targetSdkVersion;
        this.mAllowBrokenMeasureSpecs = i <= 17;
        this.mMeasureVerticalWithPaddingMargin = i >= 18;
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @android.view.RemotableViewMethod
    public void setIgnoreGravity(int i) {
        this.mIgnoreGravity = i;
    }

    public int getIgnoreGravity() {
        return this.mIgnoreGravity;
    }

    public int getGravity() {
        return this.mGravity;
    }

    @android.view.RemotableViewMethod
    public void setGravity(int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= android.view.Gravity.START;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    @android.view.RemotableViewMethod
    public void setHorizontalGravity(int i) {
        int i2 = i & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((8388615 & this.mGravity) != i2) {
            this.mGravity = i2 | (this.mGravity & (-8388616));
            requestLayout();
        }
    }

    @android.view.RemotableViewMethod
    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        if ((this.mGravity & 112) != i2) {
            this.mGravity = i2 | (this.mGravity & (-113));
            requestLayout();
        }
    }

    @Override // android.view.View
    public int getBaseline() {
        return this.mBaselineView != null ? this.mBaselineView.getBaseline() : super.getBaseline();
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        super.requestLayout();
        this.mDirtyHierarchy = true;
    }

    private void sortChildren() {
        int childCount = getChildCount();
        if (this.mSortedVerticalChildren == null || this.mSortedVerticalChildren.length != childCount) {
            this.mSortedVerticalChildren = new android.view.View[childCount];
        }
        if (this.mSortedHorizontalChildren == null || this.mSortedHorizontalChildren.length != childCount) {
            this.mSortedHorizontalChildren = new android.view.View[childCount];
        }
        android.widget.RelativeLayout.DependencyGraph dependencyGraph = this.mGraph;
        dependencyGraph.clear();
        for (int i = 0; i < childCount; i++) {
            dependencyGraph.add(getChildAt(i));
        }
        dependencyGraph.getSortedViews(this.mSortedVerticalChildren, RULES_VERTICAL);
        dependencyGraph.getSortedViews(this.mSortedHorizontalChildren, RULES_HORIZONTAL);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        android.view.View findViewById;
        int i5;
        int i6;
        int i7;
        android.view.View view;
        int i8;
        int i9;
        int i10 = 0;
        if (this.mDirtyHierarchy) {
            this.mDirtyHierarchy = false;
            sortChildren();
        }
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        if (mode == 0) {
            size = -1;
        }
        if (mode2 == 0) {
            size2 = -1;
        }
        if (mode != 1073741824) {
            i3 = 0;
        } else {
            i3 = size;
        }
        if (mode2 != 1073741824) {
            i4 = 0;
        } else {
            i4 = size2;
        }
        int i11 = this.mGravity & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        boolean z = (i11 == 8388611 || i11 == 0) ? false : true;
        int i12 = this.mGravity & 112;
        boolean z2 = (i12 == 48 || i12 == 0) ? false : true;
        if ((z || z2) && this.mIgnoreGravity != -1) {
            findViewById = findViewById(this.mIgnoreGravity);
        } else {
            findViewById = null;
        }
        boolean z3 = mode != 1073741824;
        boolean z4 = mode2 != 1073741824;
        int layoutDirection = getLayoutDirection();
        if (isLayoutRtl() && size == -1) {
            size = 65536;
        }
        android.view.View[] viewArr = this.mSortedHorizontalChildren;
        int length = viewArr.length;
        boolean z5 = false;
        while (i10 < length) {
            android.view.View view2 = viewArr[i10];
            android.view.View[] viewArr2 = viewArr;
            if (view2.getVisibility() != 8) {
                android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) view2.getLayoutParams();
                applyHorizontalSizeRules(layoutParams, size, layoutParams.getRules(layoutDirection));
                measureChildHorizontal(view2, layoutParams, size, size2);
                if (positionChildHorizontal(view2, layoutParams, size, z3)) {
                    z5 = true;
                }
            }
            i10++;
            viewArr = viewArr2;
        }
        android.view.View[] viewArr3 = this.mSortedVerticalChildren;
        int length2 = viewArr3.length;
        int i13 = getContext().getApplicationInfo().targetSdkVersion;
        int i14 = Integer.MIN_VALUE;
        int i15 = Integer.MIN_VALUE;
        int i16 = Integer.MAX_VALUE;
        int i17 = Integer.MAX_VALUE;
        int i18 = 0;
        boolean z6 = false;
        while (i18 < length2) {
            int i19 = layoutDirection;
            android.view.View view3 = viewArr3[i18];
            android.view.View[] viewArr4 = viewArr3;
            int i20 = length2;
            if (view3.getVisibility() == 8) {
                i9 = size2;
            } else {
                android.widget.RelativeLayout.LayoutParams layoutParams2 = (android.widget.RelativeLayout.LayoutParams) view3.getLayoutParams();
                applyVerticalSizeRules(layoutParams2, size2, view3.getBaseline());
                measureChild(view3, layoutParams2, size, size2);
                if (positionChildVertical(view3, layoutParams2, size2, z4)) {
                    z6 = true;
                }
                if (!z3) {
                    i9 = size2;
                } else if (isLayoutRtl()) {
                    if (i13 < 19) {
                        i3 = java.lang.Math.max(i3, size - layoutParams2.mLeft);
                        i9 = size2;
                    } else {
                        i9 = size2;
                        i3 = java.lang.Math.max(i3, (size - layoutParams2.mLeft) + layoutParams2.leftMargin);
                    }
                } else {
                    i9 = size2;
                    if (i13 < 19) {
                        i3 = java.lang.Math.max(i3, layoutParams2.mRight);
                    } else {
                        i3 = java.lang.Math.max(i3, layoutParams2.mRight + layoutParams2.rightMargin);
                    }
                }
                if (z4) {
                    if (i13 < 19) {
                        i4 = java.lang.Math.max(i4, layoutParams2.mBottom);
                    } else {
                        i4 = java.lang.Math.max(i4, layoutParams2.mBottom + layoutParams2.bottomMargin);
                    }
                }
                if (view3 != findViewById || z2) {
                    i17 = java.lang.Math.min(i17, layoutParams2.mLeft - layoutParams2.leftMargin);
                    i16 = java.lang.Math.min(i16, layoutParams2.mTop - layoutParams2.topMargin);
                }
                if (view3 != findViewById || z) {
                    int max = java.lang.Math.max(i14, layoutParams2.mRight + layoutParams2.rightMargin);
                    i15 = java.lang.Math.max(i15, layoutParams2.mBottom + layoutParams2.bottomMargin);
                    i14 = max;
                }
            }
            i18++;
            layoutDirection = i19;
            viewArr3 = viewArr4;
            length2 = i20;
            size2 = i9;
        }
        android.view.View[] viewArr5 = viewArr3;
        int i21 = length2;
        int i22 = layoutDirection;
        int i23 = i15;
        int i24 = i16;
        int i25 = i17;
        android.widget.RelativeLayout.LayoutParams layoutParams3 = null;
        int i26 = 0;
        android.view.View view4 = null;
        while (true) {
            i5 = size;
            i6 = i21;
            if (i26 >= i6) {
                break;
            }
            android.view.View view5 = viewArr5[i26];
            android.view.View view6 = findViewById;
            int i27 = i23;
            if (view5.getVisibility() != 8) {
                android.widget.RelativeLayout.LayoutParams layoutParams4 = (android.widget.RelativeLayout.LayoutParams) view5.getLayoutParams();
                if (view4 == null || layoutParams3 == null || compareLayoutPosition(layoutParams4, layoutParams3) < 0) {
                    layoutParams3 = layoutParams4;
                    view4 = view5;
                }
            }
            i26++;
            i21 = i6;
            i23 = i27;
            size = i5;
            findViewById = view6;
        }
        int i28 = i23;
        android.view.View view7 = findViewById;
        this.mBaselineView = view4;
        if (!z3) {
            i7 = i22;
        } else {
            int i29 = i3 + this.mPaddingRight;
            if (this.mLayoutParams != null && this.mLayoutParams.width >= 0) {
                i29 = java.lang.Math.max(i29, this.mLayoutParams.width);
            }
            i3 = resolveSize(java.lang.Math.max(i29, getSuggestedMinimumWidth()), i);
            if (!z5) {
                i7 = i22;
            } else {
                int i30 = 0;
                while (i30 < i6) {
                    android.view.View view8 = viewArr5[i30];
                    if (view8.getVisibility() == 8) {
                        i8 = i22;
                    } else {
                        android.widget.RelativeLayout.LayoutParams layoutParams5 = (android.widget.RelativeLayout.LayoutParams) view8.getLayoutParams();
                        i8 = i22;
                        int[] rules = layoutParams5.getRules(i8);
                        if (rules[13] != 0 || rules[14] != 0) {
                            centerHorizontal(view8, layoutParams5, i3);
                        } else if (rules[11] != 0) {
                            int measuredWidth = view8.getMeasuredWidth();
                            layoutParams5.mLeft = (i3 - this.mPaddingRight) - measuredWidth;
                            layoutParams5.mRight = layoutParams5.mLeft + measuredWidth;
                        }
                    }
                    i30++;
                    i22 = i8;
                }
                i7 = i22;
            }
        }
        if (z4) {
            int i31 = i4 + this.mPaddingBottom;
            if (this.mLayoutParams != null && this.mLayoutParams.height >= 0) {
                i31 = java.lang.Math.max(i31, this.mLayoutParams.height);
            }
            i4 = resolveSize(java.lang.Math.max(i31, getSuggestedMinimumHeight()), i2);
            if (z6) {
                for (int i32 = 0; i32 < i6; i32++) {
                    android.view.View view9 = viewArr5[i32];
                    if (view9.getVisibility() != 8) {
                        android.widget.RelativeLayout.LayoutParams layoutParams6 = (android.widget.RelativeLayout.LayoutParams) view9.getLayoutParams();
                        int[] rules2 = layoutParams6.getRules(i7);
                        if (rules2[13] != 0 || rules2[15] != 0) {
                            centerVertical(view9, layoutParams6, i4);
                        } else if (rules2[12] != 0) {
                            int measuredHeight = view9.getMeasuredHeight();
                            layoutParams6.mTop = (i4 - this.mPaddingBottom) - measuredHeight;
                            layoutParams6.mBottom = layoutParams6.mTop + measuredHeight;
                        }
                    }
                }
            }
        }
        if (z || z2) {
            android.graphics.Rect rect = this.mSelfBounds;
            rect.set(this.mPaddingLeft, this.mPaddingTop, i3 - this.mPaddingRight, i4 - this.mPaddingBottom);
            android.graphics.Rect rect2 = this.mContentBounds;
            android.view.Gravity.apply(this.mGravity, i14 - i25, i28 - i24, rect, rect2, i7);
            int i33 = rect2.left - i25;
            int i34 = rect2.top - i24;
            if (i33 != 0 || i34 != 0) {
                int i35 = 0;
                while (i35 < i6) {
                    android.view.View view10 = viewArr5[i35];
                    if (view10.getVisibility() != 8) {
                        view = view7;
                        if (view10 != view) {
                            android.widget.RelativeLayout.LayoutParams layoutParams7 = (android.widget.RelativeLayout.LayoutParams) view10.getLayoutParams();
                            if (z) {
                                layoutParams7.mLeft += i33;
                                layoutParams7.mRight += i33;
                            }
                            if (z2) {
                                layoutParams7.mTop += i34;
                                layoutParams7.mBottom += i34;
                            }
                        }
                    } else {
                        view = view7;
                    }
                    i35++;
                    view7 = view;
                }
            }
        }
        if (isLayoutRtl()) {
            int i36 = i5 - i3;
            for (int i37 = 0; i37 < i6; i37++) {
                android.view.View view11 = viewArr5[i37];
                if (view11.getVisibility() != 8) {
                    android.widget.RelativeLayout.LayoutParams layoutParams8 = (android.widget.RelativeLayout.LayoutParams) view11.getLayoutParams();
                    layoutParams8.mLeft -= i36;
                    layoutParams8.mRight -= i36;
                }
            }
        }
        setMeasuredDimension(i3, i4);
    }

    private int compareLayoutPosition(android.widget.RelativeLayout.LayoutParams layoutParams, android.widget.RelativeLayout.LayoutParams layoutParams2) {
        int i = layoutParams.mTop - layoutParams2.mTop;
        if (i != 0) {
            return i;
        }
        return layoutParams.mLeft - layoutParams2.mLeft;
    }

    private void measureChild(android.view.View view, android.widget.RelativeLayout.LayoutParams layoutParams, int i, int i2) {
        view.measure(getChildMeasureSpec(layoutParams.mLeft, layoutParams.mRight, layoutParams.width, layoutParams.leftMargin, layoutParams.rightMargin, this.mPaddingLeft, this.mPaddingRight, i), getChildMeasureSpec(layoutParams.mTop, layoutParams.mBottom, layoutParams.height, layoutParams.topMargin, layoutParams.bottomMargin, this.mPaddingTop, this.mPaddingBottom, i2));
    }

    private void measureChildHorizontal(android.view.View view, android.widget.RelativeLayout.LayoutParams layoutParams, int i, int i2) {
        int max;
        int makeMeasureSpec;
        int childMeasureSpec = getChildMeasureSpec(layoutParams.mLeft, layoutParams.mRight, layoutParams.width, layoutParams.leftMargin, layoutParams.rightMargin, this.mPaddingLeft, this.mPaddingRight, i);
        int i3 = 1073741824;
        if (i2 < 0 && !this.mAllowBrokenMeasureSpecs) {
            if (layoutParams.height >= 0) {
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824);
            } else {
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
            }
        } else {
            if (this.mMeasureVerticalWithPaddingMargin) {
                max = java.lang.Math.max(0, (((i2 - this.mPaddingTop) - this.mPaddingBottom) - layoutParams.topMargin) - layoutParams.bottomMargin);
            } else {
                max = java.lang.Math.max(0, i2);
            }
            if (layoutParams.height != -1) {
                i3 = Integer.MIN_VALUE;
            }
            makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(max, i3);
        }
        view.measure(childMeasureSpec, makeMeasureSpec);
    }

    private int getChildMeasureSpec(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9;
        int i10;
        int i11 = 0;
        boolean z = i8 < 0;
        int i12 = 1073741824;
        if (z && !this.mAllowBrokenMeasureSpecs) {
            if (i != Integer.MIN_VALUE && i2 != Integer.MIN_VALUE) {
                i3 = java.lang.Math.max(0, i2 - i);
                i11 = 1073741824;
            } else if (i3 >= 0) {
                i11 = 1073741824;
            } else {
                i3 = 0;
            }
            return android.view.View.MeasureSpec.makeMeasureSpec(i3, i11);
        }
        if (i != Integer.MIN_VALUE) {
            i9 = i;
        } else {
            i9 = i6 + i4;
        }
        if (i2 != Integer.MIN_VALUE) {
            i10 = i2;
        } else {
            i10 = (i8 - i7) - i5;
        }
        int i13 = i10 - i9;
        if (i != Integer.MIN_VALUE && i2 != Integer.MIN_VALUE) {
            if (z) {
                i12 = 0;
            }
            i3 = java.lang.Math.max(0, i13);
            i11 = i12;
        } else if (i3 >= 0) {
            if (i13 >= 0) {
                i3 = java.lang.Math.min(i13, i3);
                i11 = 1073741824;
            } else {
                i11 = 1073741824;
            }
        } else if (i3 == -1) {
            if (z) {
                i12 = 0;
            }
            i3 = java.lang.Math.max(0, i13);
            i11 = i12;
        } else if (i3 != -2) {
            i3 = 0;
        } else if (i13 >= 0) {
            i3 = i13;
            i11 = Integer.MIN_VALUE;
        } else {
            i3 = 0;
        }
        return android.view.View.MeasureSpec.makeMeasureSpec(i3, i11);
    }

    private boolean positionChildHorizontal(android.view.View view, android.widget.RelativeLayout.LayoutParams layoutParams, int i, boolean z) {
        int[] rules = layoutParams.getRules(getLayoutDirection());
        if (layoutParams.mLeft == Integer.MIN_VALUE && layoutParams.mRight != Integer.MIN_VALUE) {
            layoutParams.mLeft = layoutParams.mRight - view.getMeasuredWidth();
        } else if (layoutParams.mLeft != Integer.MIN_VALUE && layoutParams.mRight == Integer.MIN_VALUE) {
            layoutParams.mRight = layoutParams.mLeft + view.getMeasuredWidth();
        } else if (layoutParams.mLeft == Integer.MIN_VALUE && layoutParams.mRight == Integer.MIN_VALUE) {
            if (rules[13] != 0 || rules[14] != 0) {
                if (!z) {
                    centerHorizontal(view, layoutParams, i);
                } else {
                    positionAtEdge(view, layoutParams, i);
                }
                return true;
            }
            positionAtEdge(view, layoutParams, i);
        }
        return rules[21] != 0;
    }

    private void positionAtEdge(android.view.View view, android.widget.RelativeLayout.LayoutParams layoutParams, int i) {
        if (isLayoutRtl()) {
            layoutParams.mRight = (i - this.mPaddingRight) - layoutParams.rightMargin;
            layoutParams.mLeft = layoutParams.mRight - view.getMeasuredWidth();
        } else {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
            layoutParams.mRight = layoutParams.mLeft + view.getMeasuredWidth();
        }
    }

    private boolean positionChildVertical(android.view.View view, android.widget.RelativeLayout.LayoutParams layoutParams, int i, boolean z) {
        int[] rules = layoutParams.getRules();
        if (layoutParams.mTop == Integer.MIN_VALUE && layoutParams.mBottom != Integer.MIN_VALUE) {
            layoutParams.mTop = layoutParams.mBottom - view.getMeasuredHeight();
        } else if (layoutParams.mTop != Integer.MIN_VALUE && layoutParams.mBottom == Integer.MIN_VALUE) {
            layoutParams.mBottom = layoutParams.mTop + view.getMeasuredHeight();
        } else if (layoutParams.mTop == Integer.MIN_VALUE && layoutParams.mBottom == Integer.MIN_VALUE) {
            if (rules[13] != 0 || rules[15] != 0) {
                if (!z) {
                    centerVertical(view, layoutParams, i);
                } else {
                    layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
                    layoutParams.mBottom = layoutParams.mTop + view.getMeasuredHeight();
                }
                return true;
            }
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
            layoutParams.mBottom = layoutParams.mTop + view.getMeasuredHeight();
        }
        return rules[12] != 0;
    }

    private void applyHorizontalSizeRules(android.widget.RelativeLayout.LayoutParams layoutParams, int i, int[] iArr) {
        layoutParams.mLeft = Integer.MIN_VALUE;
        layoutParams.mRight = Integer.MIN_VALUE;
        android.widget.RelativeLayout.LayoutParams relatedViewParams = getRelatedViewParams(iArr, 0);
        if (relatedViewParams != null) {
            layoutParams.mRight = relatedViewParams.mLeft - (relatedViewParams.leftMargin + layoutParams.rightMargin);
        } else if (layoutParams.alignWithParent && iArr[0] != 0 && i >= 0) {
            layoutParams.mRight = (i - this.mPaddingRight) - layoutParams.rightMargin;
        }
        android.widget.RelativeLayout.LayoutParams relatedViewParams2 = getRelatedViewParams(iArr, 1);
        if (relatedViewParams2 != null) {
            layoutParams.mLeft = relatedViewParams2.mRight + relatedViewParams2.rightMargin + layoutParams.leftMargin;
        } else if (layoutParams.alignWithParent && iArr[1] != 0) {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
        }
        android.widget.RelativeLayout.LayoutParams relatedViewParams3 = getRelatedViewParams(iArr, 5);
        if (relatedViewParams3 != null) {
            layoutParams.mLeft = relatedViewParams3.mLeft + layoutParams.leftMargin;
        } else if (layoutParams.alignWithParent && iArr[5] != 0) {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
        }
        android.widget.RelativeLayout.LayoutParams relatedViewParams4 = getRelatedViewParams(iArr, 7);
        if (relatedViewParams4 != null) {
            layoutParams.mRight = relatedViewParams4.mRight - layoutParams.rightMargin;
        } else if (layoutParams.alignWithParent && iArr[7] != 0 && i >= 0) {
            layoutParams.mRight = (i - this.mPaddingRight) - layoutParams.rightMargin;
        }
        if (iArr[9] != 0) {
            layoutParams.mLeft = this.mPaddingLeft + layoutParams.leftMargin;
        }
        if (iArr[11] != 0 && i >= 0) {
            layoutParams.mRight = (i - this.mPaddingRight) - layoutParams.rightMargin;
        }
    }

    private void applyVerticalSizeRules(android.widget.RelativeLayout.LayoutParams layoutParams, int i, int i2) {
        int[] rules = layoutParams.getRules();
        int relatedViewBaselineOffset = getRelatedViewBaselineOffset(rules);
        if (relatedViewBaselineOffset != -1) {
            if (i2 != -1) {
                relatedViewBaselineOffset -= i2;
            }
            layoutParams.mTop = relatedViewBaselineOffset;
            layoutParams.mBottom = Integer.MIN_VALUE;
            return;
        }
        layoutParams.mTop = Integer.MIN_VALUE;
        layoutParams.mBottom = Integer.MIN_VALUE;
        android.widget.RelativeLayout.LayoutParams relatedViewParams = getRelatedViewParams(rules, 2);
        if (relatedViewParams != null) {
            layoutParams.mBottom = relatedViewParams.mTop - (relatedViewParams.topMargin + layoutParams.bottomMargin);
        } else if (layoutParams.alignWithParent && rules[2] != 0 && i >= 0) {
            layoutParams.mBottom = (i - this.mPaddingBottom) - layoutParams.bottomMargin;
        }
        android.widget.RelativeLayout.LayoutParams relatedViewParams2 = getRelatedViewParams(rules, 3);
        if (relatedViewParams2 != null) {
            layoutParams.mTop = relatedViewParams2.mBottom + relatedViewParams2.bottomMargin + layoutParams.topMargin;
        } else if (layoutParams.alignWithParent && rules[3] != 0) {
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
        }
        android.widget.RelativeLayout.LayoutParams relatedViewParams3 = getRelatedViewParams(rules, 6);
        if (relatedViewParams3 != null) {
            layoutParams.mTop = relatedViewParams3.mTop + layoutParams.topMargin;
        } else if (layoutParams.alignWithParent && rules[6] != 0) {
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
        }
        android.widget.RelativeLayout.LayoutParams relatedViewParams4 = getRelatedViewParams(rules, 8);
        if (relatedViewParams4 != null) {
            layoutParams.mBottom = relatedViewParams4.mBottom - layoutParams.bottomMargin;
        } else if (layoutParams.alignWithParent && rules[8] != 0 && i >= 0) {
            layoutParams.mBottom = (i - this.mPaddingBottom) - layoutParams.bottomMargin;
        }
        if (rules[10] != 0) {
            layoutParams.mTop = this.mPaddingTop + layoutParams.topMargin;
        }
        if (rules[12] != 0 && i >= 0) {
            layoutParams.mBottom = (i - this.mPaddingBottom) - layoutParams.bottomMargin;
        }
    }

    private android.view.View getRelatedView(int[] iArr, int i) {
        android.widget.RelativeLayout.DependencyGraph.Node node;
        int i2 = iArr[i];
        if (i2 == 0 || (node = (android.widget.RelativeLayout.DependencyGraph.Node) this.mGraph.mKeyNodes.get(i2)) == null) {
            return null;
        }
        android.view.View view = node.view;
        while (view.getVisibility() == 8) {
            android.widget.RelativeLayout.DependencyGraph.Node node2 = (android.widget.RelativeLayout.DependencyGraph.Node) this.mGraph.mKeyNodes.get(((android.widget.RelativeLayout.LayoutParams) view.getLayoutParams()).getRules(view.getLayoutDirection())[i]);
            if (node2 == null || view == node2.view) {
                return null;
            }
            view = node2.view;
        }
        return view;
    }

    private android.widget.RelativeLayout.LayoutParams getRelatedViewParams(int[] iArr, int i) {
        android.view.View relatedView = getRelatedView(iArr, i);
        if (relatedView != null && (relatedView.getLayoutParams() instanceof android.widget.RelativeLayout.LayoutParams)) {
            return (android.widget.RelativeLayout.LayoutParams) relatedView.getLayoutParams();
        }
        return null;
    }

    private int getRelatedViewBaselineOffset(int[] iArr) {
        int baseline;
        android.view.View relatedView = getRelatedView(iArr, 4);
        if (relatedView == null || (baseline = relatedView.getBaseline()) == -1 || !(relatedView.getLayoutParams() instanceof android.widget.RelativeLayout.LayoutParams)) {
            return -1;
        }
        return ((android.widget.RelativeLayout.LayoutParams) relatedView.getLayoutParams()).mTop + baseline;
    }

    private static void centerHorizontal(android.view.View view, android.widget.RelativeLayout.LayoutParams layoutParams, int i) {
        int measuredWidth = view.getMeasuredWidth();
        int i2 = (i - measuredWidth) / 2;
        layoutParams.mLeft = i2;
        layoutParams.mRight = i2 + measuredWidth;
    }

    private static void centerVertical(android.view.View view, android.widget.RelativeLayout.LayoutParams layoutParams, int i) {
        int measuredHeight = view.getMeasuredHeight();
        int i2 = (i - measuredHeight) / 2;
        layoutParams.mTop = i2;
        layoutParams.mBottom = i2 + measuredHeight;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            android.view.View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) childAt.getLayoutParams();
                childAt.layout(layoutParams.mLeft, layoutParams.mTop, layoutParams.mRight, layoutParams.mBottom);
            }
        }
    }

    @Override // android.view.ViewGroup
    public android.widget.RelativeLayout.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.RelativeLayout.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new android.widget.RelativeLayout.LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.widget.RelativeLayout.LayoutParams;
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (sPreserveMarginParamsInLayoutParamConversion) {
            if (layoutParams instanceof android.widget.RelativeLayout.LayoutParams) {
                return new android.widget.RelativeLayout.LayoutParams((android.widget.RelativeLayout.LayoutParams) layoutParams);
            }
            if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                return new android.widget.RelativeLayout.LayoutParams((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            }
        }
        return new android.widget.RelativeLayout.LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (this.mTopToBottomLeftToRightSet == null) {
            this.mTopToBottomLeftToRightSet = new java.util.TreeSet(new android.widget.RelativeLayout.TopToBottomLeftToRightComparator());
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mTopToBottomLeftToRightSet.add(getChildAt(i));
        }
        for (android.view.View view : this.mTopToBottomLeftToRightSet) {
            if (view.getVisibility() == 0 && view.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                this.mTopToBottomLeftToRightSet.clear();
                return true;
            }
        }
        this.mTopToBottomLeftToRightSet.clear();
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.RelativeLayout.class.getName();
    }

    private class TopToBottomLeftToRightComparator implements java.util.Comparator<android.view.View> {
        private TopToBottomLeftToRightComparator() {
        }

        @Override // java.util.Comparator
        public int compare(android.view.View view, android.view.View view2) {
            int top = view.getTop() - view2.getTop();
            if (top != 0) {
                return top;
            }
            int left = view.getLeft() - view2.getLeft();
            if (left != 0) {
                return left;
            }
            int height = view.getHeight() - view2.getHeight();
            if (height != 0) {
                return height;
            }
            int width = view.getWidth() - view2.getWidth();
            if (width != 0) {
                return width;
            }
            return 0;
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public boolean alignWithParent;
        private int mBottom;
        private int[] mInitialRules;
        private boolean mIsRtlCompatibilityMode;
        private int mLeft;
        private boolean mNeedsLayoutResolution;
        private int mRight;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT, indexMapping = {@android.view.ViewDebug.IntToString(from = 2, to = "above"), @android.view.ViewDebug.IntToString(from = 4, to = "alignBaseline"), @android.view.ViewDebug.IntToString(from = 8, to = "alignBottom"), @android.view.ViewDebug.IntToString(from = 5, to = "alignLeft"), @android.view.ViewDebug.IntToString(from = 12, to = "alignParentBottom"), @android.view.ViewDebug.IntToString(from = 9, to = "alignParentLeft"), @android.view.ViewDebug.IntToString(from = 11, to = "alignParentRight"), @android.view.ViewDebug.IntToString(from = 10, to = "alignParentTop"), @android.view.ViewDebug.IntToString(from = 7, to = "alignRight"), @android.view.ViewDebug.IntToString(from = 6, to = "alignTop"), @android.view.ViewDebug.IntToString(from = 3, to = "below"), @android.view.ViewDebug.IntToString(from = 14, to = "centerHorizontal"), @android.view.ViewDebug.IntToString(from = 13, to = "center"), @android.view.ViewDebug.IntToString(from = 15, to = "centerVertical"), @android.view.ViewDebug.IntToString(from = 0, to = "leftOf"), @android.view.ViewDebug.IntToString(from = 1, to = "rightOf"), @android.view.ViewDebug.IntToString(from = 18, to = "alignStart"), @android.view.ViewDebug.IntToString(from = 19, to = "alignEnd"), @android.view.ViewDebug.IntToString(from = 20, to = "alignParentStart"), @android.view.ViewDebug.IntToString(from = 21, to = "alignParentEnd"), @android.view.ViewDebug.IntToString(from = 16, to = "startOf"), @android.view.ViewDebug.IntToString(from = 17, to = "endOf")}, mapping = {@android.view.ViewDebug.IntToString(from = -1, to = "true"), @android.view.ViewDebug.IntToString(from = 0, to = "false/NO_ID")}, resolveId = true)
        private int[] mRules;
        private boolean mRulesChanged;
        private int mTop;

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            boolean z;
            this.mRules = new int[22];
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.RelativeLayout_Layout);
            if (context.getApplicationInfo().targetSdkVersion >= 17 && context.getApplicationInfo().hasRtlSupport()) {
                z = false;
            } else {
                z = true;
            }
            this.mIsRtlCompatibilityMode = z;
            int[] iArr = this.mRules;
            int[] iArr2 = this.mInitialRules;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                switch (index) {
                    case 0:
                        iArr[0] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 1:
                        iArr[1] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 2:
                        iArr[2] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 3:
                        iArr[3] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 4:
                        iArr[4] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 5:
                        iArr[5] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 6:
                        iArr[6] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 7:
                        iArr[7] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 8:
                        iArr[8] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 9:
                        iArr[9] = obtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 10:
                        iArr[10] = obtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 11:
                        iArr[11] = obtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 12:
                        iArr[12] = obtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 13:
                        iArr[13] = obtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 14:
                        iArr[14] = obtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 15:
                        iArr[15] = obtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 16:
                        this.alignWithParent = obtainStyledAttributes.getBoolean(index, false);
                        break;
                    case 17:
                        iArr[16] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 18:
                        iArr[17] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 19:
                        iArr[18] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 20:
                        iArr[19] = obtainStyledAttributes.getResourceId(index, 0);
                        break;
                    case 21:
                        iArr[20] = obtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                    case 22:
                        iArr[21] = obtainStyledAttributes.getBoolean(index, false) ? -1 : 0;
                        break;
                }
            }
            this.mRulesChanged = true;
            java.lang.System.arraycopy(iArr, 0, iArr2, 0, 22);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mRules = new int[22];
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mRules = new int[22];
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mRules = new int[22];
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
        }

        public LayoutParams(android.widget.RelativeLayout.LayoutParams layoutParams) {
            super((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            this.mRules = new int[22];
            this.mInitialRules = new int[22];
            this.mRulesChanged = false;
            this.mIsRtlCompatibilityMode = false;
            this.mIsRtlCompatibilityMode = layoutParams.mIsRtlCompatibilityMode;
            this.mRulesChanged = layoutParams.mRulesChanged;
            this.alignWithParent = layoutParams.alignWithParent;
            java.lang.System.arraycopy(layoutParams.mRules, 0, this.mRules, 0, 22);
            java.lang.System.arraycopy(layoutParams.mInitialRules, 0, this.mInitialRules, 0, 22);
        }

        @Override // android.view.ViewGroup.LayoutParams
        public java.lang.String debug(java.lang.String str) {
            return str + "ViewGroup.LayoutParams={ width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " }";
        }

        public void addRule(int i) {
            addRule(i, -1);
        }

        public void addRule(int i, int i2) {
            if (!this.mNeedsLayoutResolution && isRelativeRule(i) && this.mInitialRules[i] != 0 && i2 == 0) {
                this.mNeedsLayoutResolution = true;
            }
            this.mRules[i] = i2;
            this.mInitialRules[i] = i2;
            this.mRulesChanged = true;
        }

        public void removeRule(int i) {
            addRule(i, 0);
        }

        public int getRule(int i) {
            return this.mRules[i];
        }

        private boolean hasRelativeRules() {
            return (this.mInitialRules[16] == 0 && this.mInitialRules[17] == 0 && this.mInitialRules[18] == 0 && this.mInitialRules[19] == 0 && this.mInitialRules[20] == 0 && this.mInitialRules[21] == 0) ? false : true;
        }

        private boolean isRelativeRule(int i) {
            return i == 16 || i == 17 || i == 18 || i == 19 || i == 20 || i == 21;
        }

        private void resolveRules(int i) {
            char c = i == 1 ? (char) 1 : (char) 0;
            java.lang.System.arraycopy(this.mInitialRules, 0, this.mRules, 0, 22);
            if (this.mIsRtlCompatibilityMode) {
                if (this.mRules[18] != 0) {
                    if (this.mRules[5] == 0) {
                        this.mRules[5] = this.mRules[18];
                    }
                    this.mRules[18] = 0;
                }
                if (this.mRules[19] != 0) {
                    if (this.mRules[7] == 0) {
                        this.mRules[7] = this.mRules[19];
                    }
                    this.mRules[19] = 0;
                }
                if (this.mRules[16] != 0) {
                    if (this.mRules[0] == 0) {
                        this.mRules[0] = this.mRules[16];
                    }
                    this.mRules[16] = 0;
                }
                if (this.mRules[17] != 0) {
                    if (this.mRules[1] == 0) {
                        this.mRules[1] = this.mRules[17];
                    }
                    this.mRules[17] = 0;
                }
                if (this.mRules[20] != 0) {
                    if (this.mRules[9] == 0) {
                        this.mRules[9] = this.mRules[20];
                    }
                    this.mRules[20] = 0;
                }
                if (this.mRules[21] != 0) {
                    if (this.mRules[11] == 0) {
                        this.mRules[11] = this.mRules[21];
                    }
                    this.mRules[21] = 0;
                }
            } else {
                if ((this.mRules[18] != 0 || this.mRules[19] != 0) && (this.mRules[5] != 0 || this.mRules[7] != 0)) {
                    this.mRules[5] = 0;
                    this.mRules[7] = 0;
                }
                if (this.mRules[18] != 0) {
                    this.mRules[c != 0 ? (char) 7 : (char) 5] = this.mRules[18];
                    this.mRules[18] = 0;
                }
                if (this.mRules[19] != 0) {
                    this.mRules[c != 0 ? (char) 5 : (char) 7] = this.mRules[19];
                    this.mRules[19] = 0;
                }
                if ((this.mRules[16] != 0 || this.mRules[17] != 0) && (this.mRules[0] != 0 || this.mRules[1] != 0)) {
                    this.mRules[0] = 0;
                    this.mRules[1] = 0;
                }
                if (this.mRules[16] != 0) {
                    this.mRules[c] = this.mRules[16];
                    this.mRules[16] = 0;
                }
                if (this.mRules[17] != 0) {
                    this.mRules[c ^ 1] = this.mRules[17];
                    this.mRules[17] = 0;
                }
                if ((this.mRules[20] != 0 || this.mRules[21] != 0) && (this.mRules[9] != 0 || this.mRules[11] != 0)) {
                    this.mRules[9] = 0;
                    this.mRules[11] = 0;
                }
                if (this.mRules[20] != 0) {
                    this.mRules[c != 0 ? (char) 11 : '\t'] = this.mRules[20];
                    this.mRules[20] = 0;
                }
                if (this.mRules[21] != 0) {
                    this.mRules[c != 0 ? '\t' : (char) 11] = this.mRules[21];
                    this.mRules[21] = 0;
                }
            }
            this.mRulesChanged = false;
            this.mNeedsLayoutResolution = false;
        }

        public int[] getRules(int i) {
            resolveLayoutDirection(i);
            return this.mRules;
        }

        public int[] getRules() {
            return this.mRules;
        }

        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        public void resolveLayoutDirection(int i) {
            if (shouldResolveLayoutDirection(i)) {
                resolveRules(i);
            }
            super.resolveLayoutDirection(i);
        }

        private boolean shouldResolveLayoutDirection(int i) {
            return (this.mNeedsLayoutResolution || hasRelativeRules()) && (this.mRulesChanged || i != getLayoutDirection());
        }

        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:alignWithParent", this.alignWithParent);
        }

        public static final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.RelativeLayout.LayoutParams> {
            private int mAboveId;
            private int mAlignBaselineId;
            private int mAlignBottomId;
            private int mAlignEndId;
            private int mAlignLeftId;
            private int mAlignParentBottomId;
            private int mAlignParentEndId;
            private int mAlignParentLeftId;
            private int mAlignParentRightId;
            private int mAlignParentStartId;
            private int mAlignParentTopId;
            private int mAlignRightId;
            private int mAlignStartId;
            private int mAlignTopId;
            private int mAlignWithParentIfMissingId;
            private int mBelowId;
            private int mCenterHorizontalId;
            private int mCenterInParentId;
            private int mCenterVerticalId;
            private boolean mPropertiesMapped;
            private int mToEndOfId;
            private int mToLeftOfId;
            private int mToRightOfId;
            private int mToStartOfId;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
                this.mPropertiesMapped = true;
                this.mAboveId = propertyMapper.mapResourceId("layout_above", 16843140);
                this.mAlignBaselineId = propertyMapper.mapResourceId("layout_alignBaseline", 16843142);
                this.mAlignBottomId = propertyMapper.mapResourceId("layout_alignBottom", 16843146);
                this.mAlignEndId = propertyMapper.mapResourceId("layout_alignEnd", 16843706);
                this.mAlignLeftId = propertyMapper.mapResourceId("layout_alignLeft", 16843143);
                this.mAlignParentBottomId = propertyMapper.mapBoolean("layout_alignParentBottom", 16843150);
                this.mAlignParentEndId = propertyMapper.mapBoolean("layout_alignParentEnd", 16843708);
                this.mAlignParentLeftId = propertyMapper.mapBoolean("layout_alignParentLeft", 16843147);
                this.mAlignParentRightId = propertyMapper.mapBoolean("layout_alignParentRight", 16843149);
                this.mAlignParentStartId = propertyMapper.mapBoolean("layout_alignParentStart", 16843707);
                this.mAlignParentTopId = propertyMapper.mapBoolean("layout_alignParentTop", 16843148);
                this.mAlignRightId = propertyMapper.mapResourceId("layout_alignRight", 16843145);
                this.mAlignStartId = propertyMapper.mapResourceId("layout_alignStart", 16843705);
                this.mAlignTopId = propertyMapper.mapResourceId("layout_alignTop", 16843144);
                this.mAlignWithParentIfMissingId = propertyMapper.mapBoolean("layout_alignWithParentIfMissing", 16843154);
                this.mBelowId = propertyMapper.mapResourceId("layout_below", 16843141);
                this.mCenterHorizontalId = propertyMapper.mapBoolean("layout_centerHorizontal", 16843152);
                this.mCenterInParentId = propertyMapper.mapBoolean("layout_centerInParent", 16843151);
                this.mCenterVerticalId = propertyMapper.mapBoolean("layout_centerVertical", 16843153);
                this.mToEndOfId = propertyMapper.mapResourceId("layout_toEndOf", 16843704);
                this.mToLeftOfId = propertyMapper.mapResourceId("layout_toLeftOf", 16843138);
                this.mToRightOfId = propertyMapper.mapResourceId("layout_toRightOf", 16843139);
                this.mToStartOfId = propertyMapper.mapResourceId("layout_toStartOf", 16843703);
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(android.widget.RelativeLayout.LayoutParams layoutParams, android.view.inspector.PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
                }
                int[] rules = layoutParams.getRules();
                propertyReader.readResourceId(this.mAboveId, rules[2]);
                propertyReader.readResourceId(this.mAlignBaselineId, rules[4]);
                propertyReader.readResourceId(this.mAlignBottomId, rules[8]);
                propertyReader.readResourceId(this.mAlignEndId, rules[19]);
                propertyReader.readResourceId(this.mAlignLeftId, rules[5]);
                propertyReader.readBoolean(this.mAlignParentBottomId, rules[12] == -1);
                propertyReader.readBoolean(this.mAlignParentEndId, rules[21] == -1);
                propertyReader.readBoolean(this.mAlignParentLeftId, rules[9] == -1);
                propertyReader.readBoolean(this.mAlignParentRightId, rules[11] == -1);
                propertyReader.readBoolean(this.mAlignParentStartId, rules[20] == -1);
                propertyReader.readBoolean(this.mAlignParentTopId, rules[10] == -1);
                propertyReader.readResourceId(this.mAlignRightId, rules[7]);
                propertyReader.readResourceId(this.mAlignStartId, rules[18]);
                propertyReader.readResourceId(this.mAlignTopId, rules[6]);
                propertyReader.readBoolean(this.mAlignWithParentIfMissingId, layoutParams.alignWithParent);
                propertyReader.readResourceId(this.mBelowId, rules[3]);
                propertyReader.readBoolean(this.mCenterHorizontalId, rules[14] == -1);
                propertyReader.readBoolean(this.mCenterInParentId, rules[13] == -1);
                propertyReader.readBoolean(this.mCenterVerticalId, rules[15] == -1);
                propertyReader.readResourceId(this.mToEndOfId, rules[17]);
                propertyReader.readResourceId(this.mToLeftOfId, rules[0]);
                propertyReader.readResourceId(this.mToRightOfId, rules[1]);
                propertyReader.readResourceId(this.mToStartOfId, rules[16]);
            }
        }
    }

    private static class DependencyGraph {
        private android.util.SparseArray<android.widget.RelativeLayout.DependencyGraph.Node> mKeyNodes;
        private java.util.ArrayList<android.widget.RelativeLayout.DependencyGraph.Node> mNodes;
        private java.util.ArrayDeque<android.widget.RelativeLayout.DependencyGraph.Node> mRoots;

        private DependencyGraph() {
            this.mNodes = new java.util.ArrayList<>();
            this.mKeyNodes = new android.util.SparseArray<>();
            this.mRoots = new java.util.ArrayDeque<>();
        }

        void clear() {
            java.util.ArrayList<android.widget.RelativeLayout.DependencyGraph.Node> arrayList = this.mNodes;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                arrayList.get(i).release();
            }
            arrayList.clear();
            this.mKeyNodes.clear();
            this.mRoots.clear();
        }

        void add(android.view.View view) {
            int id = view.getId();
            android.widget.RelativeLayout.DependencyGraph.Node acquire = android.widget.RelativeLayout.DependencyGraph.Node.acquire(view);
            if (id != -1) {
                this.mKeyNodes.put(id, acquire);
            }
            this.mNodes.add(acquire);
        }

        void getSortedViews(android.view.View[] viewArr, int... iArr) {
            java.util.ArrayDeque<android.widget.RelativeLayout.DependencyGraph.Node> findRoots = findRoots(iArr);
            int i = 0;
            while (true) {
                android.widget.RelativeLayout.DependencyGraph.Node pollLast = findRoots.pollLast();
                if (pollLast == null) {
                    break;
                }
                android.view.View view = pollLast.view;
                int id = view.getId();
                int i2 = i + 1;
                viewArr[i] = view;
                android.util.ArrayMap<android.widget.RelativeLayout.DependencyGraph.Node, android.widget.RelativeLayout.DependencyGraph> arrayMap = pollLast.dependents;
                int size = arrayMap.size();
                for (int i3 = 0; i3 < size; i3++) {
                    android.widget.RelativeLayout.DependencyGraph.Node keyAt = arrayMap.keyAt(i3);
                    android.util.SparseArray<android.widget.RelativeLayout.DependencyGraph.Node> sparseArray = keyAt.dependencies;
                    sparseArray.remove(id);
                    if (sparseArray.size() == 0) {
                        findRoots.add(keyAt);
                    }
                }
                i = i2;
            }
            if (i < viewArr.length) {
                throw new java.lang.IllegalStateException("Circular dependencies cannot exist in RelativeLayout");
            }
        }

        private java.util.ArrayDeque<android.widget.RelativeLayout.DependencyGraph.Node> findRoots(int[] iArr) {
            android.widget.RelativeLayout.DependencyGraph.Node node;
            android.util.SparseArray<android.widget.RelativeLayout.DependencyGraph.Node> sparseArray = this.mKeyNodes;
            java.util.ArrayList<android.widget.RelativeLayout.DependencyGraph.Node> arrayList = this.mNodes;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                android.widget.RelativeLayout.DependencyGraph.Node node2 = arrayList.get(i);
                node2.dependents.clear();
                node2.dependencies.clear();
            }
            for (int i2 = 0; i2 < size; i2++) {
                android.widget.RelativeLayout.DependencyGraph.Node node3 = arrayList.get(i2);
                int[] iArr2 = ((android.widget.RelativeLayout.LayoutParams) node3.view.getLayoutParams()).mRules;
                for (int i3 : iArr) {
                    int i4 = iArr2[i3];
                    if ((i4 > 0 || android.content.res.ResourceId.isValid(i4)) && (node = sparseArray.get(i4)) != null && node != node3) {
                        node.dependents.put(node3, this);
                        node3.dependencies.put(i4, node);
                    }
                }
            }
            java.util.ArrayDeque<android.widget.RelativeLayout.DependencyGraph.Node> arrayDeque = this.mRoots;
            arrayDeque.clear();
            for (int i5 = 0; i5 < size; i5++) {
                android.widget.RelativeLayout.DependencyGraph.Node node4 = arrayList.get(i5);
                if (node4.dependencies.size() == 0) {
                    arrayDeque.addLast(node4);
                }
            }
            return arrayDeque;
        }

        static class Node {
            private static final int POOL_LIMIT = 100;
            private static final android.util.Pools.SynchronizedPool<android.widget.RelativeLayout.DependencyGraph.Node> sPool = new android.util.Pools.SynchronizedPool<>(100);
            android.view.View view;
            final android.util.ArrayMap<android.widget.RelativeLayout.DependencyGraph.Node, android.widget.RelativeLayout.DependencyGraph> dependents = new android.util.ArrayMap<>();
            final android.util.SparseArray<android.widget.RelativeLayout.DependencyGraph.Node> dependencies = new android.util.SparseArray<>();

            Node() {
            }

            static android.widget.RelativeLayout.DependencyGraph.Node acquire(android.view.View view) {
                android.widget.RelativeLayout.DependencyGraph.Node acquire = sPool.acquire();
                if (acquire == null) {
                    acquire = new android.widget.RelativeLayout.DependencyGraph.Node();
                }
                acquire.view = view;
                return acquire;
            }

            void release() {
                this.view = null;
                this.dependents.clear();
                this.dependencies.clear();
                sPool.release(this);
            }
        }
    }
}
