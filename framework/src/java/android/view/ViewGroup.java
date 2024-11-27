package android.view;

/* loaded from: classes4.dex */
public abstract class ViewGroup extends android.view.View implements android.view.ViewParent, android.view.ViewManager {
    private static final int ARRAY_CAPACITY_INCREMENT = 12;
    private static final int ARRAY_INITIAL_CAPACITY = 12;
    private static final int CHILD_LEFT_INDEX = 0;
    private static final int CHILD_TOP_INDEX = 1;
    protected static final int CLIP_TO_PADDING_MASK = 34;
    private static final boolean DBG = false;
    private static final int FLAG_ADD_STATES_FROM_CHILDREN = 8192;

    @java.lang.Deprecated
    private static final int FLAG_ALWAYS_DRAWN_WITH_CACHE = 16384;

    @java.lang.Deprecated
    private static final int FLAG_ANIMATION_CACHE = 64;
    static final int FLAG_ANIMATION_DONE = 16;

    @java.lang.Deprecated
    private static final int FLAG_CHILDREN_DRAWN_WITH_CACHE = 32768;
    static final int FLAG_CLEAR_TRANSFORMATION = 256;
    static final int FLAG_CLIP_CHILDREN = 1;
    private static final int FLAG_CLIP_TO_PADDING = 2;
    protected static final int FLAG_DISALLOW_INTERCEPT = 524288;
    static final int FLAG_INVALIDATE_REQUIRED = 4;
    static final int FLAG_IS_TRANSITION_GROUP = 16777216;
    static final int FLAG_IS_TRANSITION_GROUP_SET = 33554432;
    private static final int FLAG_LAYOUT_MODE_WAS_EXPLICITLY_SET = 8388608;
    private static final int FLAG_MASK_FOCUSABILITY = 393216;
    private static final int FLAG_NOTIFY_ANIMATION_LISTENER = 512;
    private static final int FLAG_NOTIFY_CHILDREN_ON_DRAWABLE_STATE_CHANGE = 65536;
    static final int FLAG_OPTIMIZE_INVALIDATE = 128;
    private static final int FLAG_PADDING_NOT_NULL = 32;
    private static final int FLAG_PREVENT_DISPATCH_ATTACHED_TO_WINDOW = 4194304;
    private static final int FLAG_RUN_ANIMATION = 8;
    private static final int FLAG_SHOW_CONTEXT_MENU_WITH_COORDS = 536870912;
    private static final int FLAG_SPLIT_MOTION_EVENTS = 2097152;
    private static final int FLAG_START_ACTION_MODE_FOR_CHILD_IS_NOT_TYPED = 268435456;
    private static final int FLAG_START_ACTION_MODE_FOR_CHILD_IS_TYPED = 134217728;
    protected static final int FLAG_SUPPORT_STATIC_TRANSFORMATIONS = 2048;
    static final int FLAG_TOUCHSCREEN_BLOCKS_FOCUS = 67108864;
    protected static final int FLAG_USE_CHILD_DRAWING_ORDER = 1024;
    public static final int FOCUS_AFTER_DESCENDANTS = 262144;
    public static final int FOCUS_BEFORE_DESCENDANTS = 131072;
    public static final int FOCUS_BLOCK_DESCENDANTS = 393216;
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;
    private static final int LAYOUT_MODE_UNDEFINED = -1;

    @java.lang.Deprecated
    public static final int PERSISTENT_ALL_CACHES = 3;

    @java.lang.Deprecated
    public static final int PERSISTENT_ANIMATION_CACHE = 1;

    @java.lang.Deprecated
    public static final int PERSISTENT_NO_CACHE = 0;

    @java.lang.Deprecated
    public static final int PERSISTENT_SCROLLING_CACHE = 2;
    private static final java.lang.String TAG = "ViewGroup";
    private static float[] sDebugLines;
    private android.view.animation.Animation.AnimationListener mAnimationListener;
    android.graphics.Paint mCachePaint;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    private int mChildCountWithTransientState;
    private android.view.animation.Transformation mChildTransformation;
    int mChildUnhandledKeyListeners;
    private android.view.View[] mChildren;
    private int mChildrenCount;
    private java.util.HashSet<android.view.View> mChildrenInterestedInDrag;
    private android.view.View mCurrentDragChild;
    private android.view.DragEvent mCurrentDragStartEvent;
    private android.view.View mDefaultFocus;
    protected java.util.ArrayList<android.view.View> mDisappearingChildren;
    private android.view.ViewGroup.HoverTarget mFirstHoverTarget;
    private android.view.ViewGroup.TouchTarget mFirstTouchTarget;
    private android.view.View mFocused;
    android.view.View mFocusedInCluster;

    @android.view.ViewDebug.ExportedProperty(flagMapping = {@android.view.ViewDebug.FlagToString(equals = 1, mask = 1, name = "CLIP_CHILDREN"), @android.view.ViewDebug.FlagToString(equals = 2, mask = 2, name = "CLIP_TO_PADDING"), @android.view.ViewDebug.FlagToString(equals = 32, mask = 32, name = "PADDING_NOT_NULL")}, formatToHexString = true)
    protected int mGroupFlags;
    private boolean mHoveredSelf;
    private int mInsetsAnimationDispatchMode;
    android.graphics.RectF mInvalidateRegion;
    android.view.animation.Transformation mInvalidationTransformation;
    private boolean mIsInterestedInDrag;

    @android.view.ViewDebug.ExportedProperty(category = "events")
    private int mLastTouchDownIndex;

    @android.view.ViewDebug.ExportedProperty(category = "events")
    private long mLastTouchDownTime;

    @android.view.ViewDebug.ExportedProperty(category = "events")
    private float mLastTouchDownX;

    @android.view.ViewDebug.ExportedProperty(category = "events")
    private float mLastTouchDownY;
    private android.view.animation.LayoutAnimationController mLayoutAnimationController;
    private boolean mLayoutCalledWhileSuppressed;
    private int mLayoutMode;
    private android.animation.LayoutTransition.TransitionListener mLayoutTransitionListener;
    private android.graphics.PointF mLocalPoint;
    private int mNestedScrollAxes;
    protected android.view.ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
    protected int mPersistentDrawingCache;
    private java.util.ArrayList<android.view.View> mPreSortedChildren;
    boolean mSuppressLayout;
    private int[] mTempLocation;
    private android.graphics.Point mTempPoint;
    private float[] mTempPosition;
    private android.graphics.Rect mTempRect;
    private android.view.View mTooltipHoverTarget;
    private boolean mTooltipHoveredSelf;
    private android.util.IntArray mTransientIndices;
    private java.util.List<android.view.View> mTransientViews;
    private android.animation.LayoutTransition mTransition;
    private java.util.ArrayList<android.view.View> mTransitioningViews;
    private java.util.ArrayList<android.view.View> mVisibilityChangingChildren;
    private static final int[] DESCENDANT_FOCUSABILITY_FLAGS = {131072, 262144, 393216};
    public static int LAYOUT_MODE_DEFAULT = 0;
    private static final android.view.ActionMode SENTINEL_ACTION_MODE = new android.view.ActionMode() { // from class: android.view.ViewGroup.1
        @Override // android.view.ActionMode
        public void setTitle(java.lang.CharSequence charSequence) {
        }

        @Override // android.view.ActionMode
        public void setTitle(int i) {
        }

        @Override // android.view.ActionMode
        public void setSubtitle(java.lang.CharSequence charSequence) {
        }

        @Override // android.view.ActionMode
        public void setSubtitle(int i) {
        }

        @Override // android.view.ActionMode
        public void setCustomView(android.view.View view) {
        }

        @Override // android.view.ActionMode
        public void invalidate() {
        }

        @Override // android.view.ActionMode
        public void finish() {
        }

        @Override // android.view.ActionMode
        public android.view.Menu getMenu() {
            return null;
        }

        @Override // android.view.ActionMode
        public java.lang.CharSequence getTitle() {
            return null;
        }

        @Override // android.view.ActionMode
        public java.lang.CharSequence getSubtitle() {
            return null;
        }

        @Override // android.view.ActionMode
        public android.view.View getCustomView() {
            return null;
        }

        @Override // android.view.ActionMode
        public android.view.MenuInflater getMenuInflater() {
            return null;
        }
    };

    public interface OnHierarchyChangeListener {
        void onChildViewAdded(android.view.View view, android.view.View view2);

        void onChildViewRemoved(android.view.View view, android.view.View view2);
    }

    @Override // android.view.View
    protected abstract void onLayout(boolean z, int i, int i2, int i3, int i4);

    public static class LayoutParams {

        @java.lang.Deprecated
        public static final int FILL_PARENT = -1;
        public static final int MATCH_PARENT = -1;
        public static final int WRAP_CONTENT = -2;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT, mapping = {@android.view.ViewDebug.IntToString(from = -1, to = "MATCH_PARENT"), @android.view.ViewDebug.IntToString(from = -2, to = "WRAP_CONTENT")})
        public int height;
        public android.view.animation.LayoutAnimationController.AnimationParameters layoutAnimationParameters;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT, mapping = {@android.view.ViewDebug.IntToString(from = -1, to = "MATCH_PARENT"), @android.view.ViewDebug.IntToString(from = -2, to = "WRAP_CONTENT")})
        public int width;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.view.ViewGroup.LayoutParams> {
            private int mLayout_heightId;
            private int mLayout_widthId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
                android.util.SparseArray sparseArray = new android.util.SparseArray();
                sparseArray.put(-2, "wrap_content");
                sparseArray.put(-1, "match_parent");
                java.util.Objects.requireNonNull(sparseArray);
                this.mLayout_heightId = propertyMapper.mapIntEnum("layout_height", 16842997, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
                android.util.SparseArray sparseArray2 = new android.util.SparseArray();
                sparseArray2.put(-2, "wrap_content");
                sparseArray2.put(-1, "match_parent");
                java.util.Objects.requireNonNull(sparseArray2);
                this.mLayout_widthId = propertyMapper.mapIntEnum("layout_width", 16842996, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(android.view.ViewGroup.LayoutParams layoutParams, android.view.inspector.PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readIntEnum(this.mLayout_heightId, layoutParams.height);
                propertyReader.readIntEnum(this.mLayout_widthId, layoutParams.width);
            }
        }

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ViewGroup_Layout);
            setBaseAttributes(obtainStyledAttributes, 0, 1);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            this.width = layoutParams.width;
            this.height = layoutParams.height;
        }

        LayoutParams() {
        }

        protected void setBaseAttributes(android.content.res.TypedArray typedArray, int i, int i2) {
            this.width = typedArray.getLayoutDimension(i, "layout_width");
            this.height = typedArray.getLayoutDimension(i2, "layout_height");
        }

        public void resolveLayoutDirection(int i) {
        }

        public java.lang.String debug(java.lang.String str) {
            return str + "ViewGroup.LayoutParams={ width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " }";
        }

        public void onDebugDraw(android.view.View view, android.graphics.Canvas canvas, android.graphics.Paint paint) {
        }

        protected static java.lang.String sizeToString(int i) {
            if (i == -2) {
                return "wrap-content";
            }
            if (i == -1) {
                return "match-parent";
            }
            return java.lang.String.valueOf(i);
        }

        void encode(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            viewHierarchyEncoder.beginObject(this);
            encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.endObject();
        }

        protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            viewHierarchyEncoder.addProperty("width", this.width);
            viewHierarchyEncoder.addProperty("height", this.height);
        }
    }

    public static class MarginLayoutParams extends android.view.ViewGroup.LayoutParams {
        public static final int DEFAULT_MARGIN_RELATIVE = Integer.MIN_VALUE;
        private static final int DEFAULT_MARGIN_RESOLVED = 0;
        private static final int LAYOUT_DIRECTION_MASK = 3;
        private static final int LEFT_MARGIN_UNDEFINED_MASK = 4;
        private static final int NEED_RESOLUTION_MASK = 32;
        private static final int RIGHT_MARGIN_UNDEFINED_MASK = 8;
        private static final int RTL_COMPATIBILITY_MODE_MASK = 16;
        private static final int UNDEFINED_MARGIN = Integer.MIN_VALUE;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public int bottomMargin;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        private int endMargin;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public int leftMargin;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT, flagMapping = {@android.view.ViewDebug.FlagToString(equals = 3, mask = 3, name = "LAYOUT_DIRECTION"), @android.view.ViewDebug.FlagToString(equals = 4, mask = 4, name = "LEFT_MARGIN_UNDEFINED_MASK"), @android.view.ViewDebug.FlagToString(equals = 8, mask = 8, name = "RIGHT_MARGIN_UNDEFINED_MASK"), @android.view.ViewDebug.FlagToString(equals = 16, mask = 16, name = "RTL_COMPATIBILITY_MODE_MASK"), @android.view.ViewDebug.FlagToString(equals = 32, mask = 32, name = "NEED_RESOLUTION_MASK")}, formatToHexString = true)
        byte mMarginFlags;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public int rightMargin;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        private int startMargin;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public int topMargin;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.view.ViewGroup.MarginLayoutParams> {
            private int mLayout_marginBottomId;
            private int mLayout_marginLeftId;
            private int mLayout_marginRightId;
            private int mLayout_marginTopId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
                this.mLayout_marginBottomId = propertyMapper.mapInt("layout_marginBottom", 16843002);
                this.mLayout_marginLeftId = propertyMapper.mapInt("layout_marginLeft", 16842999);
                this.mLayout_marginRightId = propertyMapper.mapInt("layout_marginRight", 16843001);
                this.mLayout_marginTopId = propertyMapper.mapInt("layout_marginTop", 16843000);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(android.view.ViewGroup.MarginLayoutParams marginLayoutParams, android.view.inspector.PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readInt(this.mLayout_marginBottomId, marginLayoutParams.bottomMargin);
                propertyReader.readInt(this.mLayout_marginLeftId, marginLayoutParams.leftMargin);
                propertyReader.readInt(this.mLayout_marginRightId, marginLayoutParams.rightMargin);
                propertyReader.readInt(this.mLayout_marginTopId, marginLayoutParams.topMargin);
            }
        }

        public MarginLayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            this.startMargin = Integer.MIN_VALUE;
            this.endMargin = Integer.MIN_VALUE;
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ViewGroup_MarginLayout);
            setBaseAttributes(obtainStyledAttributes, 0, 1);
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, -1);
            if (dimensionPixelSize >= 0) {
                this.leftMargin = dimensionPixelSize;
                this.topMargin = dimensionPixelSize;
                this.rightMargin = dimensionPixelSize;
                this.bottomMargin = dimensionPixelSize;
            } else {
                int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(9, -1);
                int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(10, -1);
                if (dimensionPixelSize2 < 0) {
                    this.leftMargin = obtainStyledAttributes.getDimensionPixelSize(3, Integer.MIN_VALUE);
                    if (this.leftMargin == Integer.MIN_VALUE) {
                        this.mMarginFlags = (byte) (this.mMarginFlags | 4);
                        this.leftMargin = 0;
                    }
                    this.rightMargin = obtainStyledAttributes.getDimensionPixelSize(5, Integer.MIN_VALUE);
                    if (this.rightMargin == Integer.MIN_VALUE) {
                        this.mMarginFlags = (byte) (this.mMarginFlags | 8);
                        this.rightMargin = 0;
                    }
                } else {
                    this.leftMargin = dimensionPixelSize2;
                    this.rightMargin = dimensionPixelSize2;
                }
                this.startMargin = obtainStyledAttributes.getDimensionPixelSize(7, Integer.MIN_VALUE);
                this.endMargin = obtainStyledAttributes.getDimensionPixelSize(8, Integer.MIN_VALUE);
                if (dimensionPixelSize3 >= 0) {
                    this.topMargin = dimensionPixelSize3;
                    this.bottomMargin = dimensionPixelSize3;
                } else {
                    this.topMargin = obtainStyledAttributes.getDimensionPixelSize(4, 0);
                    this.bottomMargin = obtainStyledAttributes.getDimensionPixelSize(6, 0);
                }
                if (isMarginRelative()) {
                    this.mMarginFlags = (byte) (this.mMarginFlags | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
                }
            }
            boolean hasRtlSupport = context.getApplicationInfo().hasRtlSupport();
            if (context.getApplicationInfo().targetSdkVersion < 17 || !hasRtlSupport) {
                this.mMarginFlags = (byte) (this.mMarginFlags | 16);
            }
            this.mMarginFlags = (byte) (this.mMarginFlags | 0);
            obtainStyledAttributes.recycle();
        }

        public MarginLayoutParams(int i, int i2) {
            super(i, i2);
            this.startMargin = Integer.MIN_VALUE;
            this.endMargin = Integer.MIN_VALUE;
            this.mMarginFlags = (byte) (this.mMarginFlags | 4);
            this.mMarginFlags = (byte) (this.mMarginFlags | 8);
            this.mMarginFlags = (byte) (this.mMarginFlags & (-33));
            this.mMarginFlags = (byte) (this.mMarginFlags & (-17));
        }

        public MarginLayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.startMargin = Integer.MIN_VALUE;
            this.endMargin = Integer.MIN_VALUE;
            this.width = marginLayoutParams.width;
            this.height = marginLayoutParams.height;
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
            this.startMargin = marginLayoutParams.startMargin;
            this.endMargin = marginLayoutParams.endMargin;
            this.mMarginFlags = marginLayoutParams.mMarginFlags;
        }

        public MarginLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.startMargin = Integer.MIN_VALUE;
            this.endMargin = Integer.MIN_VALUE;
            this.mMarginFlags = (byte) (this.mMarginFlags | 4);
            this.mMarginFlags = (byte) (this.mMarginFlags | 8);
            this.mMarginFlags = (byte) (this.mMarginFlags & (-33));
            this.mMarginFlags = (byte) (this.mMarginFlags & (-17));
        }

        public final void copyMarginsFrom(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
            this.startMargin = marginLayoutParams.startMargin;
            this.endMargin = marginLayoutParams.endMargin;
            this.mMarginFlags = marginLayoutParams.mMarginFlags;
        }

        public void setMargins(int i, int i2, int i3, int i4) {
            this.leftMargin = i;
            this.topMargin = i2;
            this.rightMargin = i3;
            this.bottomMargin = i4;
            this.mMarginFlags = (byte) (this.mMarginFlags & (-5));
            this.mMarginFlags = (byte) (this.mMarginFlags & (-9));
            if (isMarginRelative()) {
                this.mMarginFlags = (byte) (this.mMarginFlags | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
            } else {
                this.mMarginFlags = (byte) (this.mMarginFlags & (-33));
            }
        }

        public void setMarginsRelative(int i, int i2, int i3, int i4) {
            this.startMargin = i;
            this.topMargin = i2;
            this.endMargin = i3;
            this.bottomMargin = i4;
            this.mMarginFlags = (byte) (this.mMarginFlags | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }

        public void setMarginStart(int i) {
            this.startMargin = i;
            this.mMarginFlags = (byte) (this.mMarginFlags | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }

        public int getMarginStart() {
            if (this.startMargin != Integer.MIN_VALUE) {
                return this.startMargin;
            }
            if ((this.mMarginFlags & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) == 32) {
                doResolveMargins();
            }
            switch (this.mMarginFlags & 3) {
                case 1:
                    return this.rightMargin;
                default:
                    return this.leftMargin;
            }
        }

        public void setMarginEnd(int i) {
            this.endMargin = i;
            this.mMarginFlags = (byte) (this.mMarginFlags | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }

        public int getMarginEnd() {
            if (this.endMargin != Integer.MIN_VALUE) {
                return this.endMargin;
            }
            if ((this.mMarginFlags & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) == 32) {
                doResolveMargins();
            }
            switch (this.mMarginFlags & 3) {
                case 1:
                    return this.leftMargin;
                default:
                    return this.rightMargin;
            }
        }

        public boolean isMarginRelative() {
            return (this.startMargin == Integer.MIN_VALUE && this.endMargin == Integer.MIN_VALUE) ? false : true;
        }

        public void setLayoutDirection(int i) {
            if ((i == 0 || i == 1) && i != (this.mMarginFlags & 3)) {
                this.mMarginFlags = (byte) (this.mMarginFlags & (-4));
                this.mMarginFlags = (byte) ((i & 3) | this.mMarginFlags);
                if (isMarginRelative()) {
                    this.mMarginFlags = (byte) (this.mMarginFlags | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
                } else {
                    this.mMarginFlags = (byte) (this.mMarginFlags & (-33));
                }
            }
        }

        public int getLayoutDirection() {
            return this.mMarginFlags & 3;
        }

        @Override // android.view.ViewGroup.LayoutParams
        public void resolveLayoutDirection(int i) {
            setLayoutDirection(i);
            if (!isMarginRelative() || (this.mMarginFlags & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) != 32) {
                return;
            }
            doResolveMargins();
        }

        private void doResolveMargins() {
            if ((this.mMarginFlags & 16) == 16) {
                if ((this.mMarginFlags & 4) == 4 && this.startMargin > Integer.MIN_VALUE) {
                    this.leftMargin = this.startMargin;
                }
                if ((this.mMarginFlags & 8) == 8 && this.endMargin > Integer.MIN_VALUE) {
                    this.rightMargin = this.endMargin;
                }
            } else {
                switch (this.mMarginFlags & 3) {
                    case 1:
                        this.leftMargin = this.endMargin > Integer.MIN_VALUE ? this.endMargin : 0;
                        this.rightMargin = this.startMargin > Integer.MIN_VALUE ? this.startMargin : 0;
                        break;
                    default:
                        this.leftMargin = this.startMargin > Integer.MIN_VALUE ? this.startMargin : 0;
                        this.rightMargin = this.endMargin > Integer.MIN_VALUE ? this.endMargin : 0;
                        break;
                }
            }
            this.mMarginFlags = (byte) (this.mMarginFlags & (-33));
        }

        public boolean isLayoutRtl() {
            return (this.mMarginFlags & 3) == 1;
        }

        @Override // android.view.ViewGroup.LayoutParams
        public void onDebugDraw(android.view.View view, android.graphics.Canvas canvas, android.graphics.Paint paint) {
            android.graphics.Insets opticalInsets = android.view.View.isLayoutModeOptical(view.mParent) ? view.getOpticalInsets() : android.graphics.Insets.NONE;
            android.view.ViewGroup.fillDifference(canvas, view.getLeft() + opticalInsets.left, view.getTop() + opticalInsets.top, view.getRight() - opticalInsets.right, view.getBottom() - opticalInsets.bottom, this.leftMargin, this.topMargin, this.rightMargin, this.bottomMargin, paint);
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("leftMargin", this.leftMargin);
            viewHierarchyEncoder.addProperty("topMargin", this.topMargin);
            viewHierarchyEncoder.addProperty("rightMargin", this.rightMargin);
            viewHierarchyEncoder.addProperty("bottomMargin", this.bottomMargin);
            viewHierarchyEncoder.addProperty("startMargin", this.startMargin);
            viewHierarchyEncoder.addProperty("endMargin", this.endMargin);
        }
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.view.ViewGroup> {
        private int mAddStatesFromChildrenId;
        private int mAlwaysDrawnWithCacheId;
        private int mAnimationCacheId;
        private int mClipChildrenId;
        private int mClipToPaddingId;
        private int mDescendantFocusabilityId;
        private int mLayoutAnimationId;
        private int mLayoutModeId;
        private int mPersistentDrawingCacheId;
        private boolean mPropertiesMapped = false;
        private int mSplitMotionEventsId;
        private int mTouchscreenBlocksFocusId;
        private int mTransitionGroupId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mAddStatesFromChildrenId = propertyMapper.mapBoolean("addStatesFromChildren", 16842992);
            this.mAlwaysDrawnWithCacheId = propertyMapper.mapBoolean("alwaysDrawnWithCache", 16842991);
            this.mAnimationCacheId = propertyMapper.mapBoolean("animationCache", 16842989);
            this.mClipChildrenId = propertyMapper.mapBoolean("clipChildren", 16842986);
            this.mClipToPaddingId = propertyMapper.mapBoolean("clipToPadding", 16842987);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(131072, "beforeDescendants");
            sparseArray.put(262144, "afterDescendants");
            sparseArray.put(393216, "blocksDescendants");
            java.util.Objects.requireNonNull(sparseArray);
            this.mDescendantFocusabilityId = propertyMapper.mapIntEnum("descendantFocusability", 16842993, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mLayoutAnimationId = propertyMapper.mapObject("layoutAnimation", 16842988);
            android.util.SparseArray sparseArray2 = new android.util.SparseArray();
            sparseArray2.put(0, "clipBounds");
            sparseArray2.put(1, "opticalBounds");
            java.util.Objects.requireNonNull(sparseArray2);
            this.mLayoutModeId = propertyMapper.mapIntEnum("layoutMode", 16843738, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
            android.util.SparseArray sparseArray3 = new android.util.SparseArray();
            sparseArray3.put(0, "none");
            sparseArray3.put(1, "animation");
            sparseArray3.put(2, "scrolling");
            sparseArray3.put(3, "all");
            java.util.Objects.requireNonNull(sparseArray3);
            this.mPersistentDrawingCacheId = propertyMapper.mapIntEnum("persistentDrawingCache", 16842990, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray3));
            this.mSplitMotionEventsId = propertyMapper.mapBoolean("splitMotionEvents", 16843503);
            this.mTouchscreenBlocksFocusId = propertyMapper.mapBoolean("touchscreenBlocksFocus", 16843919);
            this.mTransitionGroupId = propertyMapper.mapBoolean("transitionGroup", 16843777);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.view.ViewGroup viewGroup, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAddStatesFromChildrenId, viewGroup.addStatesFromChildren());
            propertyReader.readBoolean(this.mAlwaysDrawnWithCacheId, viewGroup.isAlwaysDrawnWithCacheEnabled());
            propertyReader.readBoolean(this.mAnimationCacheId, viewGroup.isAnimationCacheEnabled());
            propertyReader.readBoolean(this.mClipChildrenId, viewGroup.getClipChildren());
            propertyReader.readBoolean(this.mClipToPaddingId, viewGroup.getClipToPadding());
            propertyReader.readIntEnum(this.mDescendantFocusabilityId, viewGroup.getDescendantFocusability());
            propertyReader.readObject(this.mLayoutAnimationId, viewGroup.getLayoutAnimation());
            propertyReader.readIntEnum(this.mLayoutModeId, viewGroup.getLayoutMode());
            propertyReader.readIntEnum(this.mPersistentDrawingCacheId, viewGroup.getPersistentDrawingCache());
            propertyReader.readBoolean(this.mSplitMotionEventsId, viewGroup.isMotionEventSplittingEnabled());
            propertyReader.readBoolean(this.mTouchscreenBlocksFocusId, viewGroup.getTouchscreenBlocksFocus());
            propertyReader.readBoolean(this.mTransitionGroupId, viewGroup.isTransitionGroup());
        }
    }

    public ViewGroup(android.content.Context context) {
        this(context, null);
    }

    public ViewGroup(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewGroup(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ViewGroup(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLastTouchDownIndex = -1;
        this.mLayoutMode = -1;
        this.mSuppressLayout = false;
        this.mLayoutCalledWhileSuppressed = false;
        this.mChildCountWithTransientState = 0;
        this.mTransientIndices = null;
        this.mTransientViews = null;
        this.mChildUnhandledKeyListeners = 0;
        this.mInsetsAnimationDispatchMode = 1;
        this.mLayoutTransitionListener = new android.animation.LayoutTransition.TransitionListener() { // from class: android.view.ViewGroup.4
            @Override // android.animation.LayoutTransition.TransitionListener
            public void startTransition(android.animation.LayoutTransition layoutTransition, android.view.ViewGroup viewGroup, android.view.View view, int i3) {
                if (i3 == 3) {
                    android.view.ViewGroup.this.startViewTransition(view);
                }
            }

            @Override // android.animation.LayoutTransition.TransitionListener
            public void endTransition(android.animation.LayoutTransition layoutTransition, android.view.ViewGroup viewGroup, android.view.View view, int i3) {
                if (android.view.ViewGroup.this.mLayoutCalledWhileSuppressed && !layoutTransition.isChangingLayout()) {
                    android.view.ViewGroup.this.requestLayout();
                    android.view.ViewGroup.this.mLayoutCalledWhileSuppressed = false;
                }
                if (i3 == 3 && android.view.ViewGroup.this.mTransitioningViews != null) {
                    android.view.ViewGroup.this.endViewTransition(view);
                }
            }
        };
        initViewGroup();
        initFromAttributes(context, attributeSet, i, i2);
    }

    private void initViewGroup() {
        if (!isShowingLayoutBounds()) {
            setFlags(128, 128);
        }
        this.mGroupFlags |= 1;
        this.mGroupFlags |= 2;
        this.mGroupFlags |= 16;
        this.mGroupFlags |= 64;
        this.mGroupFlags |= 16384;
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 11) {
            this.mGroupFlags |= 2097152;
        }
        setDescendantFocusability(131072);
        this.mChildren = new android.view.View[12];
        this.mChildrenCount = 0;
        this.mPersistentDrawingCache = 2;
    }

    private void initFromAttributes(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ViewGroup, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ViewGroup, attributeSet, obtainStyledAttributes, i, i2);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i3 = 0; i3 < indexCount; i3++) {
            int index = obtainStyledAttributes.getIndex(i3);
            switch (index) {
                case 0:
                    setClipChildren(obtainStyledAttributes.getBoolean(index, true));
                    break;
                case 1:
                    setClipToPadding(obtainStyledAttributes.getBoolean(index, true));
                    break;
                case 2:
                    int resourceId = obtainStyledAttributes.getResourceId(index, -1);
                    if (resourceId > 0) {
                        setLayoutAnimation(android.view.animation.AnimationUtils.loadLayoutAnimation(this.mContext, resourceId));
                        break;
                    } else {
                        break;
                    }
                case 3:
                    setAnimationCacheEnabled(obtainStyledAttributes.getBoolean(index, true));
                    break;
                case 4:
                    setPersistentDrawingCache(obtainStyledAttributes.getInt(index, 2));
                    break;
                case 5:
                    setAlwaysDrawnWithCacheEnabled(obtainStyledAttributes.getBoolean(index, true));
                    break;
                case 6:
                    setAddStatesFromChildren(obtainStyledAttributes.getBoolean(index, false));
                    break;
                case 7:
                    setDescendantFocusability(DESCENDANT_FOCUSABILITY_FLAGS[obtainStyledAttributes.getInt(index, 0)]);
                    break;
                case 8:
                    setMotionEventSplittingEnabled(obtainStyledAttributes.getBoolean(index, false));
                    break;
                case 9:
                    if (obtainStyledAttributes.getBoolean(index, false)) {
                        setLayoutTransition(new android.animation.LayoutTransition());
                        break;
                    } else {
                        break;
                    }
                case 10:
                    setLayoutMode(obtainStyledAttributes.getInt(index, -1));
                    break;
                case 11:
                    setTransitionGroup(obtainStyledAttributes.getBoolean(index, false));
                    break;
                case 12:
                    setTouchscreenBlocksFocus(obtainStyledAttributes.getBoolean(index, false));
                    break;
            }
        }
        obtainStyledAttributes.recycle();
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus", mapping = {@android.view.ViewDebug.IntToString(from = 131072, to = "FOCUS_BEFORE_DESCENDANTS"), @android.view.ViewDebug.IntToString(from = 262144, to = "FOCUS_AFTER_DESCENDANTS"), @android.view.ViewDebug.IntToString(from = 393216, to = "FOCUS_BLOCK_DESCENDANTS")})
    public int getDescendantFocusability() {
        return this.mGroupFlags & 393216;
    }

    public void setDescendantFocusability(int i) {
        switch (i) {
            case 131072:
            case 262144:
            case 393216:
                this.mGroupFlags &= -393217;
                this.mGroupFlags = (i & 393216) | this.mGroupFlags;
                return;
            default:
                throw new java.lang.IllegalArgumentException("must be one of FOCUS_BEFORE_DESCENDANTS, FOCUS_AFTER_DESCENDANTS, FOCUS_BLOCK_DESCENDANTS");
        }
    }

    @Override // android.view.View
    void handleFocusGainInternal(int i, android.graphics.Rect rect) {
        if (this.mFocused != null) {
            this.mFocused.unFocus(this);
            this.mFocused = null;
            this.mFocusedInCluster = null;
        }
        super.handleFocusGainInternal(i, rect);
    }

    @Override // android.view.ViewParent
    public void requestChildFocus(android.view.View view, android.view.View view2) {
        if (getDescendantFocusability() == 393216) {
            return;
        }
        super.unFocus(view2);
        if (this.mFocused != view) {
            if (this.mFocused != null) {
                this.mFocused.unFocus(view2);
            }
            this.mFocused = view;
        }
        if (this.mParent != null) {
            this.mParent.requestChildFocus(this, view2);
        }
    }

    void setDefaultFocus(android.view.View view) {
        if (this.mDefaultFocus != null && this.mDefaultFocus.isFocusedByDefault()) {
            return;
        }
        this.mDefaultFocus = view;
        if (this.mParent instanceof android.view.ViewGroup) {
            ((android.view.ViewGroup) this.mParent).setDefaultFocus(this);
        }
    }

    void clearDefaultFocus(android.view.View view) {
        if (this.mDefaultFocus != view && this.mDefaultFocus != null && this.mDefaultFocus.isFocusedByDefault()) {
            return;
        }
        this.mDefaultFocus = null;
        for (int i = 0; i < this.mChildrenCount; i++) {
            android.view.View view2 = this.mChildren[i];
            if (view2.isFocusedByDefault()) {
                this.mDefaultFocus = view2;
                return;
            }
            if (this.mDefaultFocus == null && view2.hasDefaultFocus()) {
                this.mDefaultFocus = view2;
            }
        }
        if (this.mParent instanceof android.view.ViewGroup) {
            ((android.view.ViewGroup) this.mParent).clearDefaultFocus(this);
        }
    }

    @Override // android.view.View
    boolean hasDefaultFocus() {
        return this.mDefaultFocus != null || super.hasDefaultFocus();
    }

    void clearFocusedInCluster(android.view.View view) {
        if (this.mFocusedInCluster != view) {
            return;
        }
        clearFocusedInCluster();
    }

    void clearFocusedInCluster() {
        android.view.View findKeyboardNavigationCluster = findKeyboardNavigationCluster();
        android.view.ViewParent viewParent = this;
        do {
            ((android.view.ViewGroup) viewParent).mFocusedInCluster = null;
            if (viewParent != findKeyboardNavigationCluster) {
                viewParent = viewParent.getParent();
            } else {
                return;
            }
        } while (viewParent instanceof android.view.ViewGroup);
    }

    @Override // android.view.ViewParent
    public void focusableViewAvailable(android.view.View view) {
        if (this.mParent != null && getDescendantFocusability() != 393216 && (this.mViewFlags & 12) == 0) {
            if (isFocusableInTouchMode() || !shouldBlockFocusForTouchscreen()) {
                if (!isFocused() || getDescendantFocusability() == 262144) {
                    this.mParent.focusableViewAvailable(view);
                }
            }
        }
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(android.view.View view) {
        return (isShowingContextMenuWithCoords() || this.mParent == null || !this.mParent.showContextMenuForChild(view)) ? false : true;
    }

    public final boolean isShowingContextMenuWithCoords() {
        return (this.mGroupFlags & 536870912) != 0;
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(android.view.View view, float f, float f2) {
        try {
            this.mGroupFlags |= 536870912;
            if (showContextMenuForChild(view)) {
                return true;
            }
            this.mGroupFlags = (-536870913) & this.mGroupFlags;
            return this.mParent != null && this.mParent.showContextMenuForChild(view, f, f2);
        } finally {
            this.mGroupFlags &= -536870913;
        }
    }

    @Override // android.view.ViewParent
    public android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback) {
        if ((this.mGroupFlags & 134217728) == 0) {
            try {
                this.mGroupFlags |= 268435456;
                return startActionModeForChild(view, callback, 0);
            } finally {
                this.mGroupFlags &= -268435457;
            }
        }
        return SENTINEL_ACTION_MODE;
    }

    public android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback, int i) {
        if ((this.mGroupFlags & 268435456) == 0 && i == 0) {
            try {
                this.mGroupFlags |= 134217728;
                android.view.ActionMode startActionModeForChild = startActionModeForChild(view, callback);
                this.mGroupFlags = (-134217729) & this.mGroupFlags;
                if (startActionModeForChild != SENTINEL_ACTION_MODE) {
                    return startActionModeForChild;
                }
            } catch (java.lang.Throwable th) {
                this.mGroupFlags &= -134217729;
                throw th;
            }
        }
        if (this.mParent != null) {
            try {
                return this.mParent.startActionModeForChild(view, callback, i);
            } catch (java.lang.AbstractMethodError e) {
                return this.mParent.startActionModeForChild(view, callback);
            }
        }
        return null;
    }

    @Override // android.view.View
    public boolean dispatchActivityResult(java.lang.String str, int i, int i2, android.content.Intent intent) {
        if (super.dispatchActivityResult(str, i, i2, intent)) {
            return true;
        }
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            if (getChildAt(i3).dispatchActivityResult(str, i, i2, intent)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewParent
    public android.view.View focusSearch(android.view.View view, int i) {
        if (isRootNamespace()) {
            return android.view.FocusFinder.getInstance().findNextFocus(this, view, i);
        }
        if (this.mParent != null) {
            return this.mParent.focusSearch(view, i);
        }
        return null;
    }

    @Override // android.view.ViewParent
    public boolean requestChildRectangleOnScreen(android.view.View view, android.graphics.Rect rect, boolean z) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean requestSendAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        android.view.ViewParent viewParent = this.mParent;
        if (viewParent == null || !onRequestSendAccessibilityEvent(view, accessibilityEvent)) {
            return false;
        }
        return viewParent.requestSendAccessibilityEvent(this, accessibilityEvent);
    }

    public boolean onRequestSendAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.onRequestSendAccessibilityEvent(this, view, accessibilityEvent);
        }
        return onRequestSendAccessibilityEventInternal(view, accessibilityEvent);
    }

    public boolean onRequestSendAccessibilityEventInternal(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        return true;
    }

    @Override // android.view.ViewParent
    public void childHasTransientStateChanged(android.view.View view, boolean z) {
        boolean hasTransientState = hasTransientState();
        if (z) {
            this.mChildCountWithTransientState++;
        } else {
            this.mChildCountWithTransientState--;
        }
        boolean hasTransientState2 = hasTransientState();
        if (this.mParent != null && hasTransientState != hasTransientState2) {
            try {
                this.mParent.childHasTransientStateChanged(this, hasTransientState2);
            } catch (java.lang.AbstractMethodError e) {
                android.util.Log.e(TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
            }
        }
    }

    @Override // android.view.View
    public boolean hasTransientState() {
        return this.mChildCountWithTransientState > 0 || super.hasTransientState();
    }

    @Override // android.view.View
    public boolean dispatchUnhandledMove(android.view.View view, int i) {
        return this.mFocused != null && this.mFocused.dispatchUnhandledMove(view, i);
    }

    @Override // android.view.ViewParent
    public void clearChildFocus(android.view.View view) {
        this.mFocused = null;
        if (this.mParent != null) {
            this.mParent.clearChildFocus(this);
        }
    }

    @Override // android.view.View
    public void clearFocus() {
        if (this.mFocused == null) {
            super.clearFocus();
            return;
        }
        android.view.View view = this.mFocused;
        this.mFocused = null;
        view.clearFocus();
    }

    @Override // android.view.View
    void unFocus(android.view.View view) {
        if (this.mFocused == null) {
            super.unFocus(view);
        } else {
            this.mFocused.unFocus(view);
            this.mFocused = null;
        }
    }

    public android.view.View getFocusedChild() {
        return this.mFocused;
    }

    android.view.View getDeepestFocusedChild() {
        android.view.View view = this;
        while (view != null) {
            if (view.isFocused()) {
                return view;
            }
            view = view instanceof android.view.ViewGroup ? ((android.view.ViewGroup) view).getFocusedChild() : null;
        }
        return null;
    }

    @Override // android.view.View
    public boolean hasFocus() {
        return ((this.mPrivateFlags & 2) == 0 && this.mFocused == null) ? false : true;
    }

    @Override // android.view.View
    public android.view.View findFocus() {
        if (isFocused()) {
            return this;
        }
        if (this.mFocused != null) {
            return this.mFocused.findFocus();
        }
        return null;
    }

    @Override // android.view.View
    boolean hasFocusable(boolean z, boolean z2) {
        if ((this.mViewFlags & 12) != 0) {
            return false;
        }
        if ((z || getFocusable() != 16) && isFocusable()) {
            return true;
        }
        if (getDescendantFocusability() != 393216) {
            return hasFocusableChild(z2);
        }
        return false;
    }

    boolean hasFocusableChild(boolean z) {
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view = viewArr[i2];
            if (!z || !view.hasExplicitFocusable()) {
                if (!z && view.hasFocusable()) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void addFocusables(java.util.ArrayList<android.view.View> arrayList, int i, int i2) {
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        boolean shouldBlockFocusForTouchscreen = shouldBlockFocusForTouchscreen();
        boolean z = isFocusableInTouchMode() || !shouldBlockFocusForTouchscreen;
        if (descendantFocusability == 393216) {
            if (z) {
                super.addFocusables(arrayList, i, i2);
                return;
            }
            return;
        }
        if (shouldBlockFocusForTouchscreen) {
            i2 |= 1;
        }
        if (descendantFocusability == 131072 && z) {
            super.addFocusables(arrayList, i, i2);
        }
        android.view.View[] viewArr = new android.view.View[this.mChildrenCount];
        int i3 = 0;
        for (int i4 = 0; i4 < this.mChildrenCount; i4++) {
            android.view.View view = this.mChildren[i4];
            if ((view.mViewFlags & 12) == 0) {
                viewArr[i3] = view;
                i3++;
            }
        }
        android.view.FocusFinder.sort(viewArr, 0, i3, this, isLayoutRtl());
        for (int i5 = 0; i5 < i3; i5++) {
            viewArr[i5].addFocusables(arrayList, i, i2);
        }
        if (descendantFocusability == 262144 && z && size == arrayList.size()) {
            super.addFocusables(arrayList, i, i2);
        }
    }

    @Override // android.view.View
    public void addKeyboardNavigationClusters(java.util.Collection<android.view.View> collection, int i) {
        int size = collection.size();
        if (isKeyboardNavigationCluster()) {
            boolean touchscreenBlocksFocus = getTouchscreenBlocksFocus();
            try {
                setTouchscreenBlocksFocusNoRefocus(false);
                super.addKeyboardNavigationClusters(collection, i);
            } finally {
                setTouchscreenBlocksFocusNoRefocus(touchscreenBlocksFocus);
            }
        } else {
            super.addKeyboardNavigationClusters(collection, i);
        }
        if (size != collection.size() || getDescendantFocusability() == 393216) {
            return;
        }
        android.view.View[] viewArr = new android.view.View[this.mChildrenCount];
        int i2 = 0;
        for (int i3 = 0; i3 < this.mChildrenCount; i3++) {
            android.view.View view = this.mChildren[i3];
            if ((view.mViewFlags & 12) == 0) {
                viewArr[i2] = view;
                i2++;
            }
        }
        android.view.FocusFinder.sort(viewArr, 0, i2, this, isLayoutRtl());
        for (int i4 = 0; i4 < i2; i4++) {
            viewArr[i4].addKeyboardNavigationClusters(collection, i);
        }
    }

    public void setTouchscreenBlocksFocus(boolean z) {
        android.view.View focusSearch;
        if (z) {
            this.mGroupFlags |= 67108864;
            if (hasFocus() && !isKeyboardNavigationCluster() && !getDeepestFocusedChild().isFocusableInTouchMode() && (focusSearch = focusSearch(2)) != null) {
                focusSearch.requestFocus();
                return;
            }
            return;
        }
        this.mGroupFlags &= -67108865;
    }

    private void setTouchscreenBlocksFocusNoRefocus(boolean z) {
        if (z) {
            this.mGroupFlags |= 67108864;
        } else {
            this.mGroupFlags &= -67108865;
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus")
    public boolean getTouchscreenBlocksFocus() {
        return (this.mGroupFlags & 67108864) != 0;
    }

    boolean shouldBlockFocusForTouchscreen() {
        return getTouchscreenBlocksFocus() && this.mContext.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_TOUCHSCREEN) && (!isKeyboardNavigationCluster() || (!hasFocus() && findKeyboardNavigationCluster() == this));
    }

    @Override // android.view.View
    public void findViewsWithText(java.util.ArrayList<android.view.View> arrayList, java.lang.CharSequence charSequence, int i) {
        super.findViewsWithText(arrayList, charSequence, i);
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            android.view.View view = viewArr[i3];
            if ((view.mViewFlags & 12) == 0 && (view.mPrivateFlags & 8) == 0) {
                view.findViewsWithText(arrayList, charSequence, i);
            }
        }
    }

    @Override // android.view.View
    public android.view.View findViewByAccessibilityIdTraversal(int i) {
        android.view.View findViewByAccessibilityIdTraversal = super.findViewByAccessibilityIdTraversal(i);
        if (findViewByAccessibilityIdTraversal != null) {
            return findViewByAccessibilityIdTraversal;
        }
        if (getAccessibilityNodeProvider() != null) {
            return null;
        }
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            android.view.View findViewByAccessibilityIdTraversal2 = viewArr[i3].findViewByAccessibilityIdTraversal(i);
            if (findViewByAccessibilityIdTraversal2 != null) {
                return findViewByAccessibilityIdTraversal2;
            }
        }
        return null;
    }

    @Override // android.view.View
    public android.view.View findViewByAutofillIdTraversal(int i) {
        android.view.View findViewByAutofillIdTraversal = super.findViewByAutofillIdTraversal(i);
        if (findViewByAutofillIdTraversal != null) {
            return findViewByAutofillIdTraversal;
        }
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            android.view.View findViewByAutofillIdTraversal2 = viewArr[i3].findViewByAutofillIdTraversal(i);
            if (findViewByAutofillIdTraversal2 != null) {
                return findViewByAutofillIdTraversal2;
            }
        }
        return null;
    }

    @Override // android.view.View
    public void dispatchWindowFocusChanged(boolean z) {
        super.dispatchWindowFocusChanged(z);
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchWindowFocusChanged(z);
        }
    }

    @Override // android.view.View
    public void addTouchables(java.util.ArrayList<android.view.View> arrayList) {
        super.addTouchables(arrayList);
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view = viewArr[i2];
            if ((view.mViewFlags & 12) == 0) {
                view.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.View
    public void makeOptionalFitsSystemWindows() {
        super.makeOptionalFitsSystemWindows();
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].makeOptionalFitsSystemWindows();
        }
    }

    @Override // android.view.View
    public void makeFrameworkOptionalFitsSystemWindows() {
        super.makeFrameworkOptionalFitsSystemWindows();
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].makeFrameworkOptionalFitsSystemWindows();
        }
    }

    @Override // android.view.View
    public void dispatchDisplayHint(int i) {
        super.dispatchDisplayHint(i);
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchDisplayHint(i);
        }
    }

    protected void onChildVisibilityChanged(android.view.View view, int i, int i2) {
        if (this.mTransition != null) {
            if (i2 == 0) {
                this.mTransition.showChild(this, view, i);
            } else {
                this.mTransition.hideChild(this, view, i2);
                if (this.mTransitioningViews != null && this.mTransitioningViews.contains(view)) {
                    if (this.mVisibilityChangingChildren == null) {
                        this.mVisibilityChangingChildren = new java.util.ArrayList<>();
                    }
                    this.mVisibilityChangingChildren.add(view);
                    addDisappearingView(view);
                }
            }
        }
        if (i2 == 0 && this.mCurrentDragStartEvent != null && !this.mChildrenInterestedInDrag.contains(view)) {
            notifyChildOfDragStart(view);
        }
    }

    @Override // android.view.View
    protected void dispatchVisibilityChanged(android.view.View view, int i) {
        super.dispatchVisibilityChanged(view, i);
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchVisibilityChanged(view, i);
        }
    }

    @Override // android.view.View
    public void dispatchWindowVisibilityChanged(int i) {
        super.dispatchWindowVisibilityChanged(i);
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchWindowVisibilityChanged(i);
        }
    }

    @Override // android.view.View
    boolean dispatchVisibilityAggregated(boolean z) {
        boolean dispatchVisibilityAggregated = super.dispatchVisibilityAggregated(z);
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (viewArr[i2].getVisibility() == 0) {
                viewArr[i2].dispatchVisibilityAggregated(dispatchVisibilityAggregated);
            }
        }
        return dispatchVisibilityAggregated;
    }

    @Override // android.view.View
    public void dispatchConfigurationChanged(android.content.res.Configuration configuration) {
        super.dispatchConfigurationChanged(configuration);
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchConfigurationChanged(configuration);
        }
    }

    @Override // android.view.ViewParent
    public void recomputeViewAttributes(android.view.View view) {
        android.view.ViewParent viewParent;
        if (this.mAttachInfo == null || this.mAttachInfo.mRecomputeGlobalAttributes || (viewParent = this.mParent) == null) {
            return;
        }
        viewParent.recomputeViewAttributes(this);
    }

    @Override // android.view.View
    void dispatchCollectViewAttributes(android.view.View.AttachInfo attachInfo, int i) {
        if ((i & 12) == 0) {
            super.dispatchCollectViewAttributes(attachInfo, i);
            int i2 = this.mChildrenCount;
            android.view.View[] viewArr = this.mChildren;
            for (int i3 = 0; i3 < i2; i3++) {
                android.view.View view = viewArr[i3];
                view.dispatchCollectViewAttributes(attachInfo, (view.mViewFlags & 12) | i);
            }
        }
    }

    @Override // android.view.ViewParent
    public void bringChildToFront(android.view.View view) {
        int indexOfChild = indexOfChild(view);
        if (indexOfChild >= 0) {
            removeFromArray(indexOfChild);
            addInArray(view, this.mChildrenCount);
            view.mParent = this;
            requestLayout();
            invalidate();
        }
    }

    private android.graphics.PointF getLocalPoint() {
        if (this.mLocalPoint == null) {
            this.mLocalPoint = new android.graphics.PointF();
        }
        return this.mLocalPoint;
    }

    @Override // android.view.View
    boolean dispatchDragEnterExitInPreN(android.view.DragEvent dragEvent) {
        if (dragEvent.mAction == 6 && this.mCurrentDragChild != null) {
            this.mCurrentDragChild.dispatchDragEnterExitInPreN(dragEvent);
            this.mCurrentDragChild = null;
        }
        return this.mIsInterestedInDrag && super.dispatchDragEnterExitInPreN(dragEvent);
    }

    @Override // android.view.View
    public boolean dispatchDragEvent(android.view.DragEvent dragEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        float f = dragEvent.mX;
        float f2 = dragEvent.mY;
        android.content.ClipData clipData = dragEvent.mClipData;
        android.graphics.PointF localPoint = getLocalPoint();
        switch (dragEvent.mAction) {
            case 1:
                this.mCurrentDragChild = null;
                this.mCurrentDragStartEvent = android.view.DragEvent.obtain(dragEvent);
                if (this.mChildrenInterestedInDrag == null) {
                    this.mChildrenInterestedInDrag = new java.util.HashSet<>();
                } else {
                    this.mChildrenInterestedInDrag.clear();
                }
                int i = this.mChildrenCount;
                android.view.View[] viewArr = this.mChildren;
                boolean z4 = false;
                for (int i2 = 0; i2 < i; i2++) {
                    android.view.View view = viewArr[i2];
                    view.mPrivateFlags2 &= -4;
                    if (view.getVisibility() == 0 && notifyChildOfDragStart(viewArr[i2])) {
                        z4 = true;
                    }
                }
                this.mIsInterestedInDrag = super.dispatchDragEvent(dragEvent);
                z = this.mIsInterestedInDrag ? true : z4;
                if (!z) {
                    this.mCurrentDragStartEvent.recycle();
                    this.mCurrentDragStartEvent = null;
                }
                return z;
            case 2:
            case 3:
                android.view.View findFrontmostDroppableChildAt = findFrontmostDroppableChildAt(dragEvent.mX, dragEvent.mY, localPoint);
                if (findFrontmostDroppableChildAt != this.mCurrentDragChild) {
                    if (sCascadedDragDrop) {
                        int i3 = dragEvent.mAction;
                        dragEvent.mX = 0.0f;
                        dragEvent.mY = 0.0f;
                        dragEvent.mClipData = null;
                        if (this.mCurrentDragChild != null) {
                            dragEvent.mAction = 6;
                            this.mCurrentDragChild.dispatchDragEnterExitInPreN(dragEvent);
                        }
                        if (findFrontmostDroppableChildAt != null) {
                            dragEvent.mAction = 5;
                            findFrontmostDroppableChildAt.dispatchDragEnterExitInPreN(dragEvent);
                        }
                        dragEvent.mAction = i3;
                        dragEvent.mX = f;
                        dragEvent.mY = f2;
                        dragEvent.mClipData = clipData;
                    }
                    this.mCurrentDragChild = findFrontmostDroppableChildAt;
                }
                if (findFrontmostDroppableChildAt == null && this.mIsInterestedInDrag) {
                    findFrontmostDroppableChildAt = this;
                }
                if (findFrontmostDroppableChildAt == null) {
                    return false;
                }
                if (findFrontmostDroppableChildAt != this) {
                    dragEvent.mX = localPoint.x;
                    dragEvent.mY = localPoint.y;
                    boolean dispatchDragEvent = findFrontmostDroppableChildAt.dispatchDragEvent(dragEvent);
                    dragEvent.mX = f;
                    dragEvent.mY = f2;
                    if (this.mIsInterestedInDrag) {
                        if (sCascadedDragDrop) {
                            z2 = dispatchDragEvent;
                        } else {
                            z2 = dragEvent.mEventHandlerWasCalled;
                        }
                        if (!z2) {
                            return super.dispatchDragEvent(dragEvent);
                        }
                        return dispatchDragEvent;
                    }
                    return dispatchDragEvent;
                }
                return super.dispatchDragEvent(dragEvent);
            case 4:
                java.util.HashSet<android.view.View> hashSet = this.mChildrenInterestedInDrag;
                if (hashSet == null) {
                    z3 = false;
                } else {
                    java.util.Iterator<android.view.View> it = hashSet.iterator();
                    z3 = false;
                    while (it.hasNext()) {
                        if (it.next().dispatchDragEvent(dragEvent)) {
                            z3 = true;
                        }
                    }
                    hashSet.clear();
                }
                if (this.mCurrentDragStartEvent != null) {
                    this.mCurrentDragStartEvent.recycle();
                    this.mCurrentDragStartEvent = null;
                }
                if (!this.mIsInterestedInDrag) {
                    return z3;
                }
                z = super.dispatchDragEvent(dragEvent) ? true : z3;
                this.mIsInterestedInDrag = false;
                return z;
            default:
                return false;
        }
    }

    android.view.View findFrontmostDroppableChildAt(float f, float f2, android.graphics.PointF pointF) {
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            android.view.View view = viewArr[i2];
            if (view.canAcceptDrag() && isTransformedTouchPointInView(f, f2, view, pointF)) {
                return view;
            }
        }
        return null;
    }

    boolean notifyChildOfDragStart(android.view.View view) {
        float f = this.mCurrentDragStartEvent.mX;
        float f2 = this.mCurrentDragStartEvent.mY;
        float[] tempLocationF = getTempLocationF();
        tempLocationF[0] = f;
        tempLocationF[1] = f2;
        transformPointToViewLocal(tempLocationF, view);
        this.mCurrentDragStartEvent.mX = tempLocationF[0];
        this.mCurrentDragStartEvent.mY = tempLocationF[1];
        boolean dispatchDragEvent = view.dispatchDragEvent(this.mCurrentDragStartEvent);
        this.mCurrentDragStartEvent.mX = f;
        this.mCurrentDragStartEvent.mY = f2;
        this.mCurrentDragStartEvent.mEventHandlerWasCalled = false;
        if (dispatchDragEvent) {
            this.mChildrenInterestedInDrag.add(view);
            if (!view.canAcceptDrag()) {
                view.mPrivateFlags2 |= 1;
                view.refreshDrawableState();
            }
        }
        return dispatchDragEvent;
    }

    @Override // android.view.View
    @java.lang.Deprecated
    public void dispatchWindowSystemUiVisiblityChanged(int i) {
        super.dispatchWindowSystemUiVisiblityChanged(i);
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchWindowSystemUiVisiblityChanged(i);
        }
    }

    @Override // android.view.View
    @java.lang.Deprecated
    public void dispatchSystemUiVisibilityChanged(int i) {
        super.dispatchSystemUiVisibilityChanged(i);
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchSystemUiVisibilityChanged(i);
        }
    }

    @Override // android.view.View
    boolean updateLocalSystemUiVisibility(int i, int i2) {
        boolean updateLocalSystemUiVisibility = super.updateLocalSystemUiVisibility(i, i2);
        int i3 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i4 = 0; i4 < i3; i4++) {
            updateLocalSystemUiVisibility |= viewArr[i4].updateLocalSystemUiVisibility(i, i2);
        }
        return updateLocalSystemUiVisibility;
    }

    @Override // android.view.View
    public boolean dispatchKeyEventPreIme(android.view.KeyEvent keyEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchKeyEventPreIme(keyEvent);
        }
        if (this.mFocused != null && (this.mFocused.mPrivateFlags & 16) == 16) {
            return this.mFocused.dispatchKeyEventPreIme(keyEvent);
        }
        return false;
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onKeyEvent(keyEvent, 1);
        }
        if ((this.mPrivateFlags & 18) == 18) {
            if (super.dispatchKeyEvent(keyEvent)) {
                return true;
            }
        } else if (this.mFocused != null && (this.mFocused.mPrivateFlags & 16) == 16 && this.mFocused.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(keyEvent, 1);
            return false;
        }
        return false;
    }

    @Override // android.view.View
    public boolean dispatchKeyShortcutEvent(android.view.KeyEvent keyEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchKeyShortcutEvent(keyEvent);
        }
        if (this.mFocused != null && (this.mFocused.mPrivateFlags & 16) == 16) {
            return this.mFocused.dispatchKeyShortcutEvent(keyEvent);
        }
        return false;
    }

    @Override // android.view.View
    public boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTrackballEvent(motionEvent, 1);
        }
        if ((this.mPrivateFlags & 18) == 18) {
            if (super.dispatchTrackballEvent(motionEvent)) {
                return true;
            }
        } else if (this.mFocused != null && (this.mFocused.mPrivateFlags & 16) == 16 && this.mFocused.dispatchTrackballEvent(motionEvent)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 1);
            return false;
        }
        return false;
    }

    @Override // android.view.View
    public boolean dispatchCapturedPointerEvent(android.view.MotionEvent motionEvent) {
        return (this.mPrivateFlags & 18) == 18 ? super.dispatchCapturedPointerEvent(motionEvent) : this.mFocused != null && (this.mFocused.mPrivateFlags & 16) == 16 && this.mFocused.dispatchCapturedPointerEvent(motionEvent);
    }

    @Override // android.view.View
    public void dispatchPointerCaptureChanged(boolean z) {
        exitHoverTargets();
        super.dispatchPointerCaptureChanged(z);
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchPointerCaptureChanged(z);
        }
    }

    @Override // android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        android.view.PointerIcon dispatchResolvePointerIcon;
        float xDispatchLocation = motionEvent.getXDispatchLocation(i);
        float yDispatchLocation = motionEvent.getYDispatchLocation(i);
        if (isOnScrollbarThumb(xDispatchLocation, yDispatchLocation) || isDraggingScrollBar()) {
            return null;
        }
        int i2 = this.mChildrenCount;
        if (i2 != 0) {
            java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
            boolean z = buildOrderedChildList == null && isChildrenDrawingOrderEnabled();
            android.view.View[] viewArr = this.mChildren;
            for (int i3 = i2 - 1; i3 >= 0; i3--) {
                android.view.View andVerifyPreorderedView = getAndVerifyPreorderedView(buildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i2, i3, z));
                if (andVerifyPreorderedView.canReceivePointerEvents() && isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, null) && (dispatchResolvePointerIcon = dispatchResolvePointerIcon(motionEvent, i, andVerifyPreorderedView)) != null) {
                    if (buildOrderedChildList != null) {
                        buildOrderedChildList.clear();
                    }
                    return dispatchResolvePointerIcon;
                }
            }
            if (buildOrderedChildList != null) {
                buildOrderedChildList.clear();
            }
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    private android.view.PointerIcon dispatchResolvePointerIcon(android.view.MotionEvent motionEvent, int i, android.view.View view) {
        if (!view.hasIdentityMatrix()) {
            android.view.MotionEvent transformedMotionEvent = getTransformedMotionEvent(motionEvent, view);
            android.view.PointerIcon onResolvePointerIcon = view.onResolvePointerIcon(transformedMotionEvent, i);
            transformedMotionEvent.recycle();
            return onResolvePointerIcon;
        }
        float f = this.mScrollX - view.mLeft;
        float f2 = this.mScrollY - view.mTop;
        motionEvent.offsetLocation(f, f2);
        android.view.PointerIcon onResolvePointerIcon2 = view.onResolvePointerIcon(motionEvent, i);
        motionEvent.offsetLocation(-f, -f2);
        return onResolvePointerIcon2;
    }

    private int getAndVerifyPreorderedIndex(int i, int i2, boolean z) {
        if (z && (i2 = getChildDrawingOrder(i, i2)) >= i) {
            throw new java.lang.IndexOutOfBoundsException("getChildDrawingOrder() returned invalid index " + i2 + " (child count is " + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0134  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected boolean dispatchHoverEvent(android.view.MotionEvent motionEvent) {
        android.view.MotionEvent motionEvent2;
        boolean z;
        boolean z2;
        android.view.MotionEvent motionEvent3;
        boolean z3;
        int action = motionEvent.getAction();
        boolean onInterceptHoverEvent = onInterceptHoverEvent(motionEvent);
        motionEvent.setAction(action);
        android.view.ViewGroup.HoverTarget hoverTarget = this.mFirstHoverTarget;
        android.graphics.PointF pointF = null;
        this.mFirstHoverTarget = null;
        if (!onInterceptHoverEvent && action != 10) {
            float xDispatchLocation = motionEvent.getXDispatchLocation(0);
            float yDispatchLocation = motionEvent.getYDispatchLocation(0);
            int i = this.mChildrenCount;
            if (i != 0) {
                java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
                boolean z4 = buildOrderedChildList == null && isChildrenDrawingOrderEnabled();
                android.view.View[] viewArr = this.mChildren;
                motionEvent2 = motionEvent;
                android.view.ViewGroup.HoverTarget hoverTarget2 = null;
                z = false;
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    android.view.View andVerifyPreorderedView = getAndVerifyPreorderedView(buildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z4));
                    if (andVerifyPreorderedView.canReceivePointerEvents() && isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, pointF)) {
                        android.view.ViewGroup.HoverTarget hoverTarget3 = hoverTarget;
                        android.view.ViewGroup.HoverTarget hoverTarget4 = pointF;
                        while (true) {
                            if (hoverTarget3 == null) {
                                hoverTarget3 = android.view.ViewGroup.HoverTarget.obtain(andVerifyPreorderedView);
                                z3 = false;
                                break;
                            }
                            if (hoverTarget3.child == andVerifyPreorderedView) {
                                if (hoverTarget4 != 0) {
                                    hoverTarget4.next = hoverTarget3.next;
                                } else {
                                    hoverTarget = hoverTarget3.next;
                                }
                                pointF = null;
                                hoverTarget3.next = null;
                                z3 = true;
                            } else {
                                pointF = null;
                                android.view.ViewGroup.HoverTarget hoverTarget5 = hoverTarget3;
                                hoverTarget3 = hoverTarget3.next;
                                hoverTarget4 = hoverTarget5;
                            }
                        }
                        if (hoverTarget2 != null) {
                            hoverTarget2.next = hoverTarget3;
                        } else {
                            this.mFirstHoverTarget = hoverTarget3;
                        }
                        if (action == 9) {
                            if (!z3) {
                                z |= dispatchTransformedGenericPointerEvent(motionEvent, andVerifyPreorderedView);
                            }
                        } else if (action == 7) {
                            if (!z3) {
                                android.view.MotionEvent obtainMotionEventNoHistoryOrSelf = obtainMotionEventNoHistoryOrSelf(motionEvent2);
                                obtainMotionEventNoHistoryOrSelf.setAction(9);
                                boolean dispatchTransformedGenericPointerEvent = z | dispatchTransformedGenericPointerEvent(obtainMotionEventNoHistoryOrSelf, andVerifyPreorderedView);
                                obtainMotionEventNoHistoryOrSelf.setAction(action);
                                motionEvent2 = obtainMotionEventNoHistoryOrSelf;
                                z = dispatchTransformedGenericPointerEvent(obtainMotionEventNoHistoryOrSelf, andVerifyPreorderedView) | dispatchTransformedGenericPointerEvent;
                            } else {
                                z |= dispatchTransformedGenericPointerEvent(motionEvent, andVerifyPreorderedView);
                            }
                        }
                        if (z) {
                            break;
                        }
                        hoverTarget2 = hoverTarget3;
                    }
                }
                if (buildOrderedChildList != null) {
                    buildOrderedChildList.clear();
                }
                while (hoverTarget != null) {
                    android.view.View view = hoverTarget.child;
                    if (action == 10) {
                        z |= dispatchTransformedGenericPointerEvent(motionEvent, view);
                    } else {
                        if (action == 7) {
                            boolean isHoverExitPending = motionEvent.isHoverExitPending();
                            motionEvent.setHoverExitPending(true);
                            dispatchTransformedGenericPointerEvent(motionEvent, view);
                            motionEvent.setHoverExitPending(isHoverExitPending);
                        }
                        android.view.MotionEvent obtainMotionEventNoHistoryOrSelf2 = obtainMotionEventNoHistoryOrSelf(motionEvent2);
                        obtainMotionEventNoHistoryOrSelf2.setAction(10);
                        dispatchTransformedGenericPointerEvent(obtainMotionEventNoHistoryOrSelf2, view);
                        obtainMotionEventNoHistoryOrSelf2.setAction(action);
                        motionEvent2 = obtainMotionEventNoHistoryOrSelf2;
                    }
                    android.view.ViewGroup.HoverTarget hoverTarget6 = hoverTarget.next;
                    hoverTarget.recycle();
                    hoverTarget = hoverTarget6;
                }
                z2 = (!z || action == 10 || motionEvent.isHoverExitPending()) ? false : true;
                if (z2 != this.mHoveredSelf) {
                    if (!z2) {
                        motionEvent3 = motionEvent2;
                    } else {
                        z |= super.dispatchHoverEvent(motionEvent);
                        motionEvent3 = motionEvent2;
                    }
                } else {
                    if (this.mHoveredSelf) {
                        if (action == 10) {
                            z |= super.dispatchHoverEvent(motionEvent);
                        } else {
                            if (action == 7) {
                                super.dispatchHoverEvent(motionEvent);
                            }
                            android.view.MotionEvent obtainMotionEventNoHistoryOrSelf3 = obtainMotionEventNoHistoryOrSelf(motionEvent2);
                            obtainMotionEventNoHistoryOrSelf3.setAction(10);
                            super.dispatchHoverEvent(obtainMotionEventNoHistoryOrSelf3);
                            obtainMotionEventNoHistoryOrSelf3.setAction(action);
                            motionEvent2 = obtainMotionEventNoHistoryOrSelf3;
                        }
                        this.mHoveredSelf = false;
                    }
                    if (z2) {
                        if (action == 9) {
                            z |= super.dispatchHoverEvent(motionEvent);
                            this.mHoveredSelf = true;
                            motionEvent3 = motionEvent2;
                        } else if (action == 7) {
                            motionEvent3 = obtainMotionEventNoHistoryOrSelf(motionEvent2);
                            motionEvent3.setAction(9);
                            boolean dispatchHoverEvent = z | super.dispatchHoverEvent(motionEvent3);
                            motionEvent3.setAction(action);
                            z = dispatchHoverEvent | super.dispatchHoverEvent(motionEvent3);
                            this.mHoveredSelf = true;
                        }
                    }
                    motionEvent3 = motionEvent2;
                }
                if (motionEvent3 != motionEvent) {
                    motionEvent3.recycle();
                }
                return z;
            }
        }
        motionEvent2 = motionEvent;
        z = false;
        while (hoverTarget != null) {
        }
        if (!z) {
        }
        if (z2 != this.mHoveredSelf) {
        }
        if (motionEvent3 != motionEvent) {
        }
        return z;
    }

    private void exitHoverTargets() {
        if (this.mHoveredSelf || this.mFirstHoverTarget != null) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 10, 0.0f, 0.0f, 0);
            obtain.setSource(4098);
            dispatchHoverEvent(obtain);
            obtain.recycle();
        }
    }

    private void cancelHoverTarget(android.view.View view) {
        android.view.ViewGroup.HoverTarget hoverTarget = this.mFirstHoverTarget;
        android.view.ViewGroup.HoverTarget hoverTarget2 = null;
        while (hoverTarget != null) {
            android.view.ViewGroup.HoverTarget hoverTarget3 = hoverTarget.next;
            if (hoverTarget.child == view) {
                if (hoverTarget2 == null) {
                    this.mFirstHoverTarget = hoverTarget3;
                } else {
                    hoverTarget2.next = hoverTarget3;
                }
                hoverTarget.recycle();
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 10, 0.0f, 0.0f, 0);
                obtain.setSource(4098);
                view.dispatchHoverEvent(obtain);
                obtain.recycle();
                return;
            }
            hoverTarget2 = hoverTarget;
            hoverTarget = hoverTarget3;
        }
    }

    @Override // android.view.View
    boolean dispatchTooltipHoverEvent(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        android.view.View view = null;
        switch (action) {
            case 7:
                int i = this.mChildrenCount;
                if (i != 0) {
                    float xDispatchLocation = motionEvent.getXDispatchLocation(0);
                    float yDispatchLocation = motionEvent.getYDispatchLocation(0);
                    java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
                    boolean z = buildOrderedChildList == null && isChildrenDrawingOrderEnabled();
                    android.view.View[] viewArr = this.mChildren;
                    int i2 = i - 1;
                    while (true) {
                        if (i2 >= 0) {
                            android.view.View andVerifyPreorderedView = getAndVerifyPreorderedView(buildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z));
                            if (!andVerifyPreorderedView.canReceivePointerEvents() || !isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, null) || !dispatchTooltipHoverEvent(motionEvent, andVerifyPreorderedView)) {
                                i2--;
                            } else {
                                view = andVerifyPreorderedView;
                            }
                        }
                    }
                    if (buildOrderedChildList != null) {
                        buildOrderedChildList.clear();
                    }
                }
                if (this.mTooltipHoverTarget != view) {
                    if (this.mTooltipHoverTarget != null) {
                        motionEvent.setAction(10);
                        this.mTooltipHoverTarget.dispatchTooltipHoverEvent(motionEvent);
                        motionEvent.setAction(action);
                    }
                    this.mTooltipHoverTarget = view;
                }
                if (this.mTooltipHoverTarget != null) {
                    if (this.mTooltipHoveredSelf) {
                        this.mTooltipHoveredSelf = false;
                        motionEvent.setAction(10);
                        super.dispatchTooltipHoverEvent(motionEvent);
                        motionEvent.setAction(action);
                    }
                    return true;
                }
                this.mTooltipHoveredSelf = super.dispatchTooltipHoverEvent(motionEvent);
                return this.mTooltipHoveredSelf;
            case 10:
                if (this.mTooltipHoverTarget != null) {
                    this.mTooltipHoverTarget.dispatchTooltipHoverEvent(motionEvent);
                    this.mTooltipHoverTarget = null;
                } else if (this.mTooltipHoveredSelf) {
                    super.dispatchTooltipHoverEvent(motionEvent);
                    this.mTooltipHoveredSelf = false;
                }
            case 8:
            case 9:
            default:
                return false;
        }
    }

    private boolean dispatchTooltipHoverEvent(android.view.MotionEvent motionEvent, android.view.View view) {
        if (!view.hasIdentityMatrix()) {
            android.view.MotionEvent transformedMotionEvent = getTransformedMotionEvent(motionEvent, view);
            boolean dispatchTooltipHoverEvent = view.dispatchTooltipHoverEvent(transformedMotionEvent);
            transformedMotionEvent.recycle();
            return dispatchTooltipHoverEvent;
        }
        float f = this.mScrollX - view.mLeft;
        float f2 = this.mScrollY - view.mTop;
        motionEvent.offsetLocation(f, f2);
        boolean dispatchTooltipHoverEvent2 = view.dispatchTooltipHoverEvent(motionEvent);
        motionEvent.offsetLocation(-f, -f2);
        return dispatchTooltipHoverEvent2;
    }

    private void exitTooltipHoverTargets() {
        if (this.mTooltipHoveredSelf || this.mTooltipHoverTarget != null) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 10, 0.0f, 0.0f, 0);
            obtain.setSource(4098);
            dispatchTooltipHoverEvent(obtain);
            obtain.recycle();
        }
    }

    @Override // android.view.View
    protected boolean hasHoveredChild() {
        return this.mFirstHoverTarget != null;
    }

    @Override // android.view.View
    protected boolean pointInHoveredChild(android.view.MotionEvent motionEvent) {
        if (this.mFirstHoverTarget != null) {
            return isTransformedTouchPointInView(motionEvent.getXDispatchLocation(0), motionEvent.getYDispatchLocation(0), this.mFirstHoverTarget.child, null);
        }
        return false;
    }

    @Override // android.view.View
    public void addChildrenForAccessibility(java.util.ArrayList<android.view.View> arrayList) {
        if (getAccessibilityNodeProvider() != null) {
            return;
        }
        android.view.ViewGroup.ChildListForAccessibility obtain = android.view.ViewGroup.ChildListForAccessibility.obtain(this, true);
        try {
            int childCount = obtain.getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = obtain.getChildAt(i);
                if ((childAt.mViewFlags & 12) == 0) {
                    if (childAt.includeForAccessibility()) {
                        arrayList.add(childAt);
                    } else {
                        childAt.addChildrenForAccessibility(arrayList);
                    }
                }
            }
        } finally {
            obtain.recycle();
        }
    }

    public boolean onInterceptHoverEvent(android.view.MotionEvent motionEvent) {
        if (motionEvent.isFromSource(8194)) {
            int action = motionEvent.getAction();
            float xDispatchLocation = motionEvent.getXDispatchLocation(0);
            float yDispatchLocation = motionEvent.getYDispatchLocation(0);
            if ((action == 7 || action == 9) && isOnScrollbar(xDispatchLocation, yDispatchLocation)) {
                return true;
            }
        }
        return false;
    }

    private static android.view.MotionEvent obtainMotionEventNoHistoryOrSelf(android.view.MotionEvent motionEvent) {
        if (motionEvent.getHistorySize() == 0) {
            return motionEvent;
        }
        return android.view.MotionEvent.obtainNoHistory(motionEvent);
    }

    @Override // android.view.View
    protected boolean dispatchGenericPointerEvent(android.view.MotionEvent motionEvent) {
        int i = this.mChildrenCount;
        if (i != 0) {
            boolean z = false;
            float xDispatchLocation = motionEvent.getXDispatchLocation(0);
            float yDispatchLocation = motionEvent.getYDispatchLocation(0);
            java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
            if (buildOrderedChildList == null && isChildrenDrawingOrderEnabled()) {
                z = true;
            }
            android.view.View[] viewArr = this.mChildren;
            for (int i2 = i - 1; i2 >= 0; i2--) {
                android.view.View andVerifyPreorderedView = getAndVerifyPreorderedView(buildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z));
                if (andVerifyPreorderedView.canReceivePointerEvents() && isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, null) && dispatchTransformedGenericPointerEvent(motionEvent, andVerifyPreorderedView)) {
                    if (buildOrderedChildList != null) {
                        buildOrderedChildList.clear();
                    }
                    return true;
                }
            }
            if (buildOrderedChildList != null) {
                buildOrderedChildList.clear();
            }
        }
        return super.dispatchGenericPointerEvent(motionEvent);
    }

    @Override // android.view.View
    protected boolean dispatchGenericFocusedEvent(android.view.MotionEvent motionEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchGenericFocusedEvent(motionEvent);
        }
        if (this.mFocused != null && (this.mFocused.mPrivateFlags & 16) == 16) {
            return this.mFocused.dispatchGenericMotionEvent(motionEvent);
        }
        return false;
    }

    private boolean dispatchTransformedGenericPointerEvent(android.view.MotionEvent motionEvent, android.view.View view) {
        if (!view.hasIdentityMatrix()) {
            android.view.MotionEvent transformedMotionEvent = getTransformedMotionEvent(motionEvent, view);
            boolean dispatchGenericMotionEvent = view.dispatchGenericMotionEvent(transformedMotionEvent);
            transformedMotionEvent.recycle();
            return dispatchGenericMotionEvent;
        }
        float f = this.mScrollX - view.mLeft;
        float f2 = this.mScrollY - view.mTop;
        motionEvent.offsetLocation(f, f2);
        boolean dispatchGenericMotionEvent2 = view.dispatchGenericMotionEvent(motionEvent);
        motionEvent.offsetLocation(-f, -f2);
        return dispatchGenericMotionEvent2;
    }

    private android.view.MotionEvent getTransformedMotionEvent(android.view.MotionEvent motionEvent, android.view.View view) {
        float f = this.mScrollX - view.mLeft;
        float f2 = this.mScrollY - view.mTop;
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(motionEvent);
        obtain.offsetLocation(f, f2);
        if (!view.hasIdentityMatrix()) {
            obtain.transform(view.getInverseMatrix());
        }
        return obtain;
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x019e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        int i;
        boolean z2;
        android.view.ViewGroup.TouchTarget touchTarget;
        int i2;
        android.view.ViewGroup.TouchTarget touchTarget2;
        int i3;
        int i4;
        boolean z3;
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTouchEvent(motionEvent, 1);
        }
        boolean z4 = false;
        if (motionEvent.isTargetAccessibilityFocus() && isAccessibilityFocusedViewOrHost()) {
            motionEvent.setTargetAccessibilityFocus(false);
        }
        if (onFilterTouchEventForSecurity(motionEvent)) {
            int action = motionEvent.getAction();
            int i5 = action & 255;
            if (i5 == 0) {
                cancelAndClearTouchTargets(motionEvent);
                resetTouchState();
            }
            if (i5 == 0 || this.mFirstTouchTarget != null) {
                if (!((this.mGroupFlags & 524288) != 0)) {
                    z = onInterceptTouchEvent(motionEvent);
                    motionEvent.setAction(action);
                } else {
                    z = false;
                }
            } else {
                z = true;
            }
            if (z || this.mFirstTouchTarget != null) {
                motionEvent.setTargetAccessibilityFocus(false);
            }
            boolean z5 = resetCancelNextUpFlag(this) || i5 == 3;
            boolean z6 = ((this.mGroupFlags & 2097152) == 0 || (motionEvent.getSource() == 8194)) ? false : true;
            if (z5 || z) {
                i = i5;
            } else {
                android.view.View findChildWithAccessibilityFocus = motionEvent.isTargetAccessibilityFocus() ? findChildWithAccessibilityFocus() : null;
                if (i5 == 0 || ((z6 && i5 == 5) || i5 == 7)) {
                    int actionIndex = motionEvent.getActionIndex();
                    int pointerId = z6 ? 1 << motionEvent.getPointerId(actionIndex) : -1;
                    removePointersFromTouchTargets(pointerId);
                    int i6 = this.mChildrenCount;
                    if (i6 == 0) {
                        i = i5;
                        z2 = false;
                        touchTarget2 = null;
                    } else {
                        float xDispatchLocation = motionEvent.getXDispatchLocation(actionIndex);
                        float yDispatchLocation = motionEvent.getYDispatchLocation(actionIndex);
                        java.util.ArrayList<android.view.View> buildTouchDispatchChildList = buildTouchDispatchChildList();
                        boolean z7 = buildTouchDispatchChildList == null && isChildrenDrawingOrderEnabled();
                        android.view.View[] viewArr = this.mChildren;
                        int i7 = i6 - 1;
                        touchTarget2 = null;
                        while (true) {
                            if (i7 < 0) {
                                i = i5;
                                z4 = false;
                                z2 = false;
                                break;
                            }
                            int andVerifyPreorderedIndex = getAndVerifyPreorderedIndex(i6, i7, z7);
                            boolean z8 = z7;
                            android.view.View andVerifyPreorderedView = getAndVerifyPreorderedView(buildTouchDispatchChildList, viewArr, andVerifyPreorderedIndex);
                            if (findChildWithAccessibilityFocus != null) {
                                if (findChildWithAccessibilityFocus != andVerifyPreorderedView) {
                                    i3 = i7;
                                    i4 = i5;
                                    i7 = i3 - 1;
                                    z7 = z8;
                                    i5 = i4;
                                } else {
                                    i7 = i6;
                                    findChildWithAccessibilityFocus = null;
                                }
                            }
                            if (!andVerifyPreorderedView.canReceivePointerEvents()) {
                                i3 = i7;
                                i4 = i5;
                                z3 = false;
                            } else {
                                i3 = i7;
                                if (isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, null)) {
                                    android.view.ViewGroup.TouchTarget touchTarget3 = getTouchTarget(andVerifyPreorderedView);
                                    if (touchTarget3 != null) {
                                        touchTarget3.pointerIdBits |= pointerId;
                                        touchTarget2 = touchTarget3;
                                        i = i5;
                                        z4 = false;
                                        z2 = false;
                                        break;
                                    }
                                    resetCancelNextUpFlag(andVerifyPreorderedView);
                                    touchTarget2 = touchTarget3;
                                    if (dispatchTransformedTouchEvent(motionEvent, false, andVerifyPreorderedView, pointerId)) {
                                        i = i5;
                                        this.mLastTouchDownTime = motionEvent.getDownTime();
                                        if (buildTouchDispatchChildList != null) {
                                            int i8 = 0;
                                            while (true) {
                                                if (i8 >= i6) {
                                                    break;
                                                }
                                                if (viewArr[andVerifyPreorderedIndex] != this.mChildren[i8]) {
                                                    i8++;
                                                } else {
                                                    this.mLastTouchDownIndex = i8;
                                                    break;
                                                }
                                            }
                                        } else {
                                            this.mLastTouchDownIndex = andVerifyPreorderedIndex;
                                        }
                                        this.mLastTouchDownX = xDispatchLocation;
                                        this.mLastTouchDownY = yDispatchLocation;
                                        touchTarget2 = addTouchTarget(andVerifyPreorderedView, pointerId);
                                        z4 = false;
                                        z2 = true;
                                    } else {
                                        i4 = i5;
                                        motionEvent.setTargetAccessibilityFocus(false);
                                        i7 = i3 - 1;
                                        z7 = z8;
                                        i5 = i4;
                                    }
                                } else {
                                    i4 = i5;
                                    z3 = false;
                                }
                            }
                            motionEvent.setTargetAccessibilityFocus(z3);
                            i7 = i3 - 1;
                            z7 = z8;
                            i5 = i4;
                        }
                        if (buildTouchDispatchChildList != null) {
                            buildTouchDispatchChildList.clear();
                        }
                    }
                    if (touchTarget2 == null && this.mFirstTouchTarget != null) {
                        touchTarget = this.mFirstTouchTarget;
                        while (touchTarget.next != null) {
                            touchTarget = touchTarget.next;
                        }
                        touchTarget.pointerIdBits |= pointerId;
                    } else {
                        touchTarget = touchTarget2;
                    }
                    if (this.mFirstTouchTarget != null) {
                        z4 = dispatchTransformedTouchEvent(motionEvent, z5, null, -1);
                    } else {
                        android.view.ViewGroup.TouchTarget touchTarget4 = this.mFirstTouchTarget;
                        android.view.ViewGroup.TouchTarget touchTarget5 = null;
                        boolean z9 = z4;
                        while (touchTarget4 != null) {
                            android.view.ViewGroup.TouchTarget touchTarget6 = touchTarget4.next;
                            if (z2 && touchTarget4 == touchTarget) {
                                z9 = true;
                            } else {
                                boolean z10 = (resetCancelNextUpFlag(touchTarget4.child) || z) ? true : z4;
                                if (dispatchTransformedTouchEvent(motionEvent, z10, touchTarget4.child, touchTarget4.pointerIdBits)) {
                                    z9 = true;
                                }
                                if (z10) {
                                    if (touchTarget5 == null) {
                                        this.mFirstTouchTarget = touchTarget6;
                                    } else {
                                        touchTarget5.next = touchTarget6;
                                    }
                                    touchTarget4.recycle();
                                    touchTarget4 = touchTarget6;
                                }
                            }
                            touchTarget5 = touchTarget4;
                            touchTarget4 = touchTarget6;
                        }
                        z4 = z9;
                    }
                    if (!z5 || (i2 = i) == 1 || i2 == 7) {
                        resetTouchState();
                    } else if (z6 && i2 == 6) {
                        removePointersFromTouchTargets(1 << motionEvent.getPointerId(motionEvent.getActionIndex()));
                    }
                } else {
                    i = i5;
                }
            }
            z2 = false;
            touchTarget = null;
            if (this.mFirstTouchTarget != null) {
            }
            if (!z5) {
            }
            resetTouchState();
        }
        if (!z4 && this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 1);
        }
        return z4;
    }

    public java.util.ArrayList<android.view.View> buildTouchDispatchChildList() {
        return buildOrderedChildList();
    }

    private android.view.View findChildWithAccessibilityFocus() {
        android.view.View accessibilityFocusedHost;
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null || (accessibilityFocusedHost = viewRootImpl.getAccessibilityFocusedHost()) == null) {
            return null;
        }
        android.view.ViewParent parent = accessibilityFocusedHost.getParent();
        while (parent instanceof android.view.View) {
            if (parent == this) {
                return accessibilityFocusedHost;
            }
            accessibilityFocusedHost = parent;
            parent = accessibilityFocusedHost.getParent();
        }
        return null;
    }

    private void resetTouchState() {
        clearTouchTargets();
        resetCancelNextUpFlag(this);
        this.mGroupFlags &= -524289;
        this.mNestedScrollAxes = 0;
    }

    private static boolean resetCancelNextUpFlag(android.view.View view) {
        if ((view.mPrivateFlags & 67108864) != 0) {
            view.mPrivateFlags &= -67108865;
            return true;
        }
        return false;
    }

    private void clearTouchTargets() {
        android.view.ViewGroup.TouchTarget touchTarget = this.mFirstTouchTarget;
        if (touchTarget == null) {
            return;
        }
        while (true) {
            android.view.ViewGroup.TouchTarget touchTarget2 = touchTarget.next;
            touchTarget.recycle();
            if (touchTarget2 != null) {
                touchTarget = touchTarget2;
            } else {
                this.mFirstTouchTarget = null;
                return;
            }
        }
    }

    private void cancelAndClearTouchTargets(android.view.MotionEvent motionEvent) {
        boolean z;
        if (this.mFirstTouchTarget != null) {
            if (motionEvent != null) {
                z = false;
            } else {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                motionEvent = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                motionEvent.setSource(4098);
                z = true;
            }
            for (android.view.ViewGroup.TouchTarget touchTarget = this.mFirstTouchTarget; touchTarget != null; touchTarget = touchTarget.next) {
                resetCancelNextUpFlag(touchTarget.child);
                dispatchTransformedTouchEvent(motionEvent, true, touchTarget.child, touchTarget.pointerIdBits);
            }
            clearTouchTargets();
            if (z) {
                motionEvent.recycle();
            }
        }
    }

    private android.view.ViewGroup.TouchTarget getTouchTarget(android.view.View view) {
        for (android.view.ViewGroup.TouchTarget touchTarget = this.mFirstTouchTarget; touchTarget != null; touchTarget = touchTarget.next) {
            if (touchTarget.child == view) {
                return touchTarget;
            }
        }
        return null;
    }

    private android.view.ViewGroup.TouchTarget addTouchTarget(android.view.View view, int i) {
        android.view.ViewGroup.TouchTarget obtain = android.view.ViewGroup.TouchTarget.obtain(view, i);
        obtain.next = this.mFirstTouchTarget;
        this.mFirstTouchTarget = obtain;
        return obtain;
    }

    private void removePointersFromTouchTargets(int i) {
        android.view.ViewGroup.TouchTarget touchTarget = this.mFirstTouchTarget;
        android.view.ViewGroup.TouchTarget touchTarget2 = null;
        while (touchTarget != null) {
            android.view.ViewGroup.TouchTarget touchTarget3 = touchTarget.next;
            if ((touchTarget.pointerIdBits & i) != 0) {
                touchTarget.pointerIdBits &= ~i;
                if (touchTarget.pointerIdBits == 0) {
                    if (touchTarget2 == null) {
                        this.mFirstTouchTarget = touchTarget3;
                    } else {
                        touchTarget2.next = touchTarget3;
                    }
                    touchTarget.recycle();
                    touchTarget = touchTarget3;
                }
            }
            touchTarget2 = touchTarget;
            touchTarget = touchTarget3;
        }
    }

    private void cancelTouchTarget(android.view.View view) {
        android.view.ViewGroup.TouchTarget touchTarget = this.mFirstTouchTarget;
        android.view.ViewGroup.TouchTarget touchTarget2 = null;
        while (touchTarget != null) {
            android.view.ViewGroup.TouchTarget touchTarget3 = touchTarget.next;
            if (touchTarget.child == view) {
                if (touchTarget2 == null) {
                    this.mFirstTouchTarget = touchTarget3;
                } else {
                    touchTarget2.next = touchTarget3;
                }
                touchTarget.recycle();
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                obtain.setSource(4098);
                view.dispatchTouchEvent(obtain);
                obtain.recycle();
                return;
            }
            touchTarget2 = touchTarget;
            touchTarget = touchTarget3;
        }
    }

    private android.graphics.Rect getTempRect() {
        if (this.mTempRect == null) {
            this.mTempRect = new android.graphics.Rect();
        }
        return this.mTempRect;
    }

    private float[] getTempLocationF() {
        if (this.mTempPosition == null) {
            this.mTempPosition = new float[2];
        }
        return this.mTempPosition;
    }

    private android.graphics.Point getTempPoint() {
        if (this.mTempPoint == null) {
            this.mTempPoint = new android.graphics.Point();
        }
        return this.mTempPoint;
    }

    protected boolean isTransformedTouchPointInView(float f, float f2, android.view.View view, android.graphics.PointF pointF) {
        float[] tempLocationF = getTempLocationF();
        tempLocationF[0] = f;
        tempLocationF[1] = f2;
        transformPointToViewLocal(tempLocationF, view);
        boolean pointInView = view.pointInView(tempLocationF[0], tempLocationF[1]);
        if (pointInView && pointF != null) {
            pointF.set(tempLocationF[0], tempLocationF[1]);
        }
        return pointInView;
    }

    public void transformPointToViewLocal(float[] fArr, android.view.View view) {
        fArr[0] = fArr[0] + (this.mScrollX - view.mLeft);
        fArr[1] = fArr[1] + (this.mScrollY - view.mTop);
        if (!view.hasIdentityMatrix()) {
            view.getInverseMatrix().mapPoints(fArr);
        }
    }

    private boolean dispatchTransformedTouchEvent(android.view.MotionEvent motionEvent, boolean z, android.view.View view, int i) {
        boolean dispatchTouchEvent;
        android.view.MotionEvent split;
        boolean dispatchTouchEvent2;
        int action = motionEvent.getAction();
        if (z || action == 3) {
            motionEvent.setAction(3);
            if (view == null) {
                dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
            } else {
                dispatchTouchEvent = view.dispatchTouchEvent(motionEvent);
            }
            motionEvent.setAction(action);
            return dispatchTouchEvent;
        }
        int pointerIdBits = motionEvent.getPointerIdBits();
        int i2 = i & pointerIdBits;
        if (i2 == 0) {
            return false;
        }
        if (i2 == pointerIdBits) {
            if (view == null || view.hasIdentityMatrix()) {
                if (view == null) {
                    return super.dispatchTouchEvent(motionEvent);
                }
                float f = this.mScrollX - view.mLeft;
                float f2 = this.mScrollY - view.mTop;
                motionEvent.offsetLocation(f, f2);
                boolean dispatchTouchEvent3 = view.dispatchTouchEvent(motionEvent);
                motionEvent.offsetLocation(-f, -f2);
                return dispatchTouchEvent3;
            }
            split = android.view.MotionEvent.obtain(motionEvent);
        } else {
            split = motionEvent.split(i2);
        }
        if (view == null) {
            dispatchTouchEvent2 = super.dispatchTouchEvent(split);
        } else {
            split.offsetLocation(this.mScrollX - view.mLeft, this.mScrollY - view.mTop);
            if (!view.hasIdentityMatrix()) {
                split.transform(view.getInverseMatrix());
            }
            dispatchTouchEvent2 = view.dispatchTouchEvent(split);
        }
        split.recycle();
        return dispatchTouchEvent2;
    }

    public void setMotionEventSplittingEnabled(boolean z) {
        if (z) {
            this.mGroupFlags |= 2097152;
        } else {
            this.mGroupFlags &= -2097153;
        }
    }

    public boolean isMotionEventSplittingEnabled() {
        return (this.mGroupFlags & 2097152) == 2097152;
    }

    public boolean isTransitionGroup() {
        if ((this.mGroupFlags & 33554432) != 0) {
            return (this.mGroupFlags & 16777216) != 0;
        }
        android.view.ViewOutlineProvider outlineProvider = getOutlineProvider();
        return (getBackground() == null && getTransitionName() == null && (outlineProvider == null || outlineProvider == android.view.ViewOutlineProvider.BACKGROUND)) ? false : true;
    }

    public void setTransitionGroup(boolean z) {
        this.mGroupFlags |= 33554432;
        if (z) {
            this.mGroupFlags |= 16777216;
        } else {
            this.mGroupFlags &= -16777217;
        }
    }

    @Override // android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (z == ((this.mGroupFlags & 524288) != 0)) {
            return;
        }
        if (z) {
            this.mGroupFlags |= 524288;
        } else {
            this.mGroupFlags &= -524289;
        }
        if (this.mParent != null) {
            this.mParent.requestDisallowInterceptTouchEvent(z);
        }
    }

    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        return motionEvent.isFromSource(8194) && motionEvent.getAction() == 0 && motionEvent.isButtonPressed(1) && isOnScrollbarThumb(motionEvent.getXDispatchLocation(0), motionEvent.getYDispatchLocation(0));
    }

    @Override // android.view.View
    public boolean requestFocus(int i, android.graphics.Rect rect) {
        boolean onRequestFocusInDescendants;
        int descendantFocusability = getDescendantFocusability();
        switch (descendantFocusability) {
            case 131072:
                boolean requestFocus = super.requestFocus(i, rect);
                if (!requestFocus) {
                    onRequestFocusInDescendants = onRequestFocusInDescendants(i, rect);
                    break;
                } else {
                    onRequestFocusInDescendants = requestFocus;
                    break;
                }
            case 262144:
                boolean onRequestFocusInDescendants2 = onRequestFocusInDescendants(i, rect);
                if (!onRequestFocusInDescendants2) {
                    onRequestFocusInDescendants = super.requestFocus(i, rect);
                    break;
                } else {
                    onRequestFocusInDescendants = onRequestFocusInDescendants2;
                    break;
                }
            case 393216:
                onRequestFocusInDescendants = super.requestFocus(i, rect);
                break;
            default:
                throw new java.lang.IllegalStateException("descendant focusability must be one of FOCUS_BEFORE_DESCENDANTS, FOCUS_AFTER_DESCENDANTS, FOCUS_BLOCK_DESCENDANTS but is " + descendantFocusability);
        }
        if (onRequestFocusInDescendants && !isLayoutValid() && (this.mPrivateFlags & 1) == 0) {
            this.mPrivateFlags |= 1;
        }
        return onRequestFocusInDescendants;
    }

    protected boolean onRequestFocusInDescendants(int i, android.graphics.Rect rect) {
        int i2;
        int i3;
        int i4;
        int i5 = this.mChildrenCount;
        if ((i & 2) != 0) {
            i3 = i5;
            i2 = 0;
            i4 = 1;
        } else {
            i2 = i5 - 1;
            i3 = -1;
            i4 = -1;
        }
        android.view.View[] viewArr = this.mChildren;
        while (i2 != i3) {
            android.view.View view = viewArr[i2];
            if ((view.mViewFlags & 12) == 0 && view.requestFocus(i, rect)) {
                return true;
            }
            i2 += i4;
        }
        return false;
    }

    @Override // android.view.View
    public boolean restoreDefaultFocus() {
        if (this.mDefaultFocus != null && getDescendantFocusability() != 393216 && (this.mDefaultFocus.mViewFlags & 12) == 0 && this.mDefaultFocus.restoreDefaultFocus()) {
            return true;
        }
        return super.restoreDefaultFocus();
    }

    @Override // android.view.View
    public boolean restoreFocusInCluster(int i) {
        if (isKeyboardNavigationCluster()) {
            boolean touchscreenBlocksFocus = getTouchscreenBlocksFocus();
            try {
                setTouchscreenBlocksFocusNoRefocus(false);
                return restoreFocusInClusterInternal(i);
            } finally {
                setTouchscreenBlocksFocusNoRefocus(touchscreenBlocksFocus);
            }
        }
        return restoreFocusInClusterInternal(i);
    }

    private boolean restoreFocusInClusterInternal(int i) {
        if (this.mFocusedInCluster != null && getDescendantFocusability() != 393216 && (this.mFocusedInCluster.mViewFlags & 12) == 0 && this.mFocusedInCluster.restoreFocusInCluster(i)) {
            return true;
        }
        return super.restoreFocusInCluster(i);
    }

    @Override // android.view.View
    public boolean restoreFocusNotInCluster() {
        if (this.mFocusedInCluster != null) {
            return restoreFocusInCluster(130);
        }
        if (isKeyboardNavigationCluster() || (this.mViewFlags & 12) != 0) {
            return false;
        }
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability == 393216) {
            return super.requestFocus(130, null);
        }
        if (descendantFocusability == 131072 && super.requestFocus(130, null)) {
            return true;
        }
        for (int i = 0; i < this.mChildrenCount; i++) {
            android.view.View view = this.mChildren[i];
            if (!view.isKeyboardNavigationCluster() && view.restoreFocusNotInCluster()) {
                return true;
            }
        }
        if (descendantFocusability != 262144 || hasFocusableChild(false)) {
            return false;
        }
        return super.requestFocus(130, null);
    }

    @Override // android.view.View
    public void dispatchStartTemporaryDetach() {
        super.dispatchStartTemporaryDetach();
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchStartTemporaryDetach();
        }
    }

    @Override // android.view.View
    public void dispatchFinishTemporaryDetach() {
        super.dispatchFinishTemporaryDetach();
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchFinishTemporaryDetach();
        }
    }

    @Override // android.view.View
    void dispatchAttachedToWindow(android.view.View.AttachInfo attachInfo, int i) {
        this.mGroupFlags |= 4194304;
        super.dispatchAttachedToWindow(attachInfo, i);
        this.mGroupFlags &= -4194305;
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            android.view.View view = viewArr[i3];
            view.dispatchAttachedToWindow(attachInfo, combineVisibility(i, view.getVisibility()));
        }
        int size = this.mTransientIndices == null ? 0 : this.mTransientIndices.size();
        for (int i4 = 0; i4 < size; i4++) {
            android.view.View view2 = this.mTransientViews.get(i4);
            view2.dispatchAttachedToWindow(attachInfo, combineVisibility(i, view2.getVisibility()));
        }
    }

    @Override // android.view.View
    void dispatchScreenStateChanged(int i) {
        super.dispatchScreenStateChanged(i);
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchScreenStateChanged(i);
        }
    }

    @Override // android.view.View
    void dispatchMovedToDisplay(android.view.Display display, android.content.res.Configuration configuration) {
        super.dispatchMovedToDisplay(display, configuration);
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchMovedToDisplay(display, configuration);
        }
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        boolean dispatchPopulateAccessibilityEvent;
        boolean dispatchPopulateAccessibilityEventInternal;
        if (includeForAccessibility(false) && (dispatchPopulateAccessibilityEventInternal = super.dispatchPopulateAccessibilityEventInternal(accessibilityEvent))) {
            return dispatchPopulateAccessibilityEventInternal;
        }
        android.view.ViewGroup.ChildListForAccessibility obtain = android.view.ViewGroup.ChildListForAccessibility.obtain(this, true);
        try {
            int childCount = obtain.getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = obtain.getChildAt(i);
                if ((childAt.mViewFlags & 12) == 0 && (dispatchPopulateAccessibilityEvent = childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent))) {
                    return dispatchPopulateAccessibilityEvent;
                }
            }
            return false;
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.view.View
    public void dispatchProvideStructure(android.view.ViewStructure viewStructure) {
        int i;
        int i2;
        super.dispatchProvideStructure(viewStructure);
        if (isAssistBlocked() || viewStructure.getChildCount() != 0 || (i = this.mChildrenCount) <= 0) {
            return;
        }
        if (!isLaidOut()) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("View", "dispatchProvideStructure(): not laid out, ignoring " + i + " children of " + getAccessibilityViewId());
                return;
            }
            return;
        }
        viewStructure.setChildCount(i);
        java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
        java.util.ArrayList arrayList = buildOrderedChildList != null ? new java.util.ArrayList(buildOrderedChildList) : null;
        boolean z = arrayList == null && isChildrenDrawingOrderEnabled();
        for (int i3 = 0; i3 < i; i3++) {
            try {
                i2 = getAndVerifyPreorderedIndex(i, i3, z);
            } catch (java.lang.IndexOutOfBoundsException e) {
                if (this.mContext.getApplicationInfo().targetSdkVersion < 23) {
                    android.util.Log.w(TAG, "Bad getChildDrawingOrder while collecting assist @ " + i3 + " of " + i, e);
                    if (i3 <= 0) {
                        z = false;
                        i2 = i3;
                    } else {
                        int[] iArr = new int[i];
                        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
                        for (int i4 = 0; i4 < i3; i4++) {
                            iArr[i4] = getChildDrawingOrder(i, i4);
                            sparseBooleanArray.put(iArr[i4], true);
                        }
                        int i5 = 0;
                        for (int i6 = i3; i6 < i; i6++) {
                            while (sparseBooleanArray.get(i5, false)) {
                                i5++;
                            }
                            iArr[i6] = i5;
                            i5++;
                        }
                        java.util.ArrayList arrayList2 = new java.util.ArrayList(i);
                        for (int i7 = 0; i7 < i; i7++) {
                            arrayList2.add(this.mChildren[iArr[i7]]);
                        }
                        arrayList = arrayList2;
                        i2 = i3;
                        z = false;
                    }
                } else {
                    throw e;
                }
            }
            getAndVerifyPreorderedView(arrayList, this.mChildren, i2).dispatchProvideStructure(viewStructure.newChild(i3));
        }
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    @Override // android.view.View
    public void dispatchProvideAutofillStructure(android.view.ViewStructure viewStructure, int i) {
        super.dispatchProvideAutofillStructure(viewStructure, i);
        if (viewStructure.getChildCount() != 0) {
            return;
        }
        if (!isLaidOut()) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v("View", "dispatchProvideAutofillStructure(): not laid out, ignoring " + this.mChildrenCount + " children of " + getAutofillId());
                return;
            }
            return;
        }
        android.view.ViewGroup.ChildListForAutoFillOrContentCapture childrenForAutofill = getChildrenForAutofill(i);
        int size = childrenForAutofill.size();
        viewStructure.setChildCount(size);
        for (int i2 = 0; i2 < size; i2++) {
            childrenForAutofill.get(i2).dispatchProvideAutofillStructure(viewStructure.newChild(i2), i);
        }
        childrenForAutofill.recycle();
    }

    @Override // android.view.View
    public void dispatchProvideContentCaptureStructure() {
        super.dispatchProvideContentCaptureStructure();
        if (isLaidOut()) {
            android.view.ViewGroup.ChildListForAutoFillOrContentCapture childrenForContentCapture = getChildrenForContentCapture();
            int size = childrenForContentCapture.size();
            for (int i = 0; i < size; i++) {
                childrenForContentCapture.get(i).dispatchProvideContentCaptureStructure();
            }
            childrenForContentCapture.recycle();
        }
    }

    private android.view.ViewGroup.ChildListForAutoFillOrContentCapture getChildrenForAutofill(int i) {
        android.view.ViewGroup.ChildListForAutoFillOrContentCapture obtain = android.view.ViewGroup.ChildListForAutoFillOrContentCapture.obtain();
        populateChildrenForAutofill(obtain, i);
        return obtain;
    }

    private android.view.autofill.AutofillManager getAutofillManager() {
        return (android.view.autofill.AutofillManager) this.mContext.getSystemService(android.view.autofill.AutofillManager.class);
    }

    private boolean shouldIncludeAllChildrenViewWithAutofillTypeNotNone(android.view.autofill.AutofillManager autofillManager) {
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.shouldIncludeAllChildrenViewsWithAutofillTypeNotNoneInAssistStructure();
    }

    private boolean shouldIncludeAllChildrenViews(android.view.autofill.AutofillManager autofillManager) {
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.shouldIncludeAllChildrenViewInAssistStructure();
    }

    private boolean shouldAlwaysIncludeWebview(android.view.autofill.AutofillManager autofillManager) {
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.shouldAlwaysIncludeWebviewInAssistStructure();
    }

    private void populateChildrenForAutofill(java.util.ArrayList<android.view.View> arrayList, int i) {
        int i2 = this.mChildrenCount;
        if (i2 <= 0) {
            return;
        }
        java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
        boolean z = buildOrderedChildList == null && isChildrenDrawingOrderEnabled();
        android.view.autofill.AutofillManager autofillManager = getAutofillManager();
        for (int i3 = 0; i3 < i2; i3++) {
            int andVerifyPreorderedIndex = getAndVerifyPreorderedIndex(i2, i3, z);
            android.view.View view = buildOrderedChildList == null ? this.mChildren[andVerifyPreorderedIndex] : buildOrderedChildList.get(andVerifyPreorderedIndex);
            if ((i & 1) != 0 || view.isImportantForAutofill() || (((view instanceof android.webkit.WebView) && shouldAlwaysIncludeWebview(autofillManager)) || ((view.isMatchingAutofillableHeuristics() && !view.isActivityDeniedForAutofillForUnimportantView()) || ((shouldIncludeAllChildrenViewWithAutofillTypeNotNone(autofillManager) && view.getAutofillType() != 0) || shouldIncludeAllChildrenViews(autofillManager) || (android.service.autofill.Flags.includeInvisibleViewGroupInAssistStructure() && (view instanceof android.view.ViewGroup) && view.getVisibility() != 0))))) {
                arrayList.add(view);
            } else if (view instanceof android.view.ViewGroup) {
                ((android.view.ViewGroup) view).populateChildrenForAutofill(arrayList, i);
            }
        }
    }

    private android.view.ViewGroup.ChildListForAutoFillOrContentCapture getChildrenForContentCapture() {
        android.view.ViewGroup.ChildListForAutoFillOrContentCapture obtain = android.view.ViewGroup.ChildListForAutoFillOrContentCapture.obtain();
        populateChildrenForContentCapture(obtain);
        return obtain;
    }

    private void populateChildrenForContentCapture(java.util.ArrayList<android.view.View> arrayList) {
        int i = this.mChildrenCount;
        if (i <= 0) {
            return;
        }
        java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
        boolean z = buildOrderedChildList == null && isChildrenDrawingOrderEnabled();
        for (int i2 = 0; i2 < i; i2++) {
            int andVerifyPreorderedIndex = getAndVerifyPreorderedIndex(i, i2, z);
            android.view.View view = buildOrderedChildList == null ? this.mChildren[andVerifyPreorderedIndex] : buildOrderedChildList.get(andVerifyPreorderedIndex);
            if (view.isImportantForContentCapture()) {
                arrayList.add(view);
            } else if (view instanceof android.view.ViewGroup) {
                ((android.view.ViewGroup) view).populateChildrenForContentCapture(arrayList);
            }
        }
    }

    private static android.view.View getAndVerifyPreorderedView(java.util.ArrayList<android.view.View> arrayList, android.view.View[] viewArr, int i) {
        if (arrayList != null) {
            android.view.View view = arrayList.get(i);
            if (view == null) {
                throw new java.lang.RuntimeException("Invalid preorderedList contained null child at index " + i);
            }
            return view;
        }
        return viewArr[i];
    }

    @Override // android.view.View
    public void resetSubtreeAutofillIds() {
        super.resetSubtreeAutofillIds();
        android.view.View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].resetSubtreeAutofillIds();
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (getAccessibilityNodeProvider() != null) {
            return;
        }
        if (this.mAttachInfo != null) {
            java.util.ArrayList<android.view.View> arrayList = this.mAttachInfo.mTempArrayList;
            arrayList.clear();
            addChildrenForAccessibility(arrayList);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                accessibilityNodeInfo.addChildUnchecked(arrayList.get(i));
            }
            arrayList.clear();
        }
        accessibilityNodeInfo.setAvailableExtraData(java.util.Collections.singletonList(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY));
    }

    @Override // android.view.View
    public void addExtraDataToAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, java.lang.String str, android.os.Bundle bundle) {
        if (str.equals(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY)) {
            android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo obtain = android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo.obtain();
            obtain.setLayoutSize(getLayoutParams().width, getLayoutParams().height);
            accessibilityNodeInfo.setExtraRenderingInfo(obtain);
        }
    }

    @Override // android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.view.ViewGroup.class.getName();
    }

    @Override // android.view.ViewParent
    public void notifySubtreeAccessibilityStateChanged(android.view.View view, android.view.View view2, int i) {
        if (getAccessibilityLiveRegion() != 0) {
            notifyViewAccessibilityStateChangedIfNeeded(1);
        } else if (this.mParent != null) {
            try {
                this.mParent.notifySubtreeAccessibilityStateChanged(this, view2, i);
            } catch (java.lang.AbstractMethodError e) {
                android.util.Log.e("View", this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
            }
        }
    }

    @Override // android.view.View
    public void notifySubtreeAccessibilityStateChangedIfNeeded() {
        if (!android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled() || this.mAttachInfo == null) {
            return;
        }
        if (getImportantForAccessibility() != 4 && !isImportantForAccessibility() && getChildCount() > 0) {
            java.lang.Object parentForAccessibility = getParentForAccessibility();
            if (parentForAccessibility instanceof android.view.View) {
                ((android.view.View) parentForAccessibility).notifySubtreeAccessibilityStateChangedIfNeeded();
                return;
            }
        }
        super.notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    @Override // android.view.View
    void resetSubtreeAccessibilityStateChanged() {
        super.resetSubtreeAccessibilityStateChanged();
        android.view.View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].resetSubtreeAccessibilityStateChanged();
        }
    }

    int getNumChildrenForAccessibility() {
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            android.view.View childAt = getChildAt(i2);
            if (childAt.includeForAccessibility()) {
                i++;
            } else if (childAt instanceof android.view.ViewGroup) {
                i += ((android.view.ViewGroup) childAt).getNumChildrenForAccessibility();
            }
        }
        return i;
    }

    @Override // android.view.ViewParent
    public boolean onNestedPrePerformAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle) {
        return false;
    }

    @Override // android.view.View
    void calculateAccessibilityDataSensitive() {
        super.calculateAccessibilityDataSensitive();
        for (int i = 0; i < this.mChildrenCount; i++) {
            this.mChildren[i].calculateAccessibilityDataSensitive();
        }
    }

    @Override // android.view.View
    void dispatchDetachedFromWindow() {
        cancelAndClearTouchTargets(null);
        exitHoverTargets();
        exitTooltipHoverTargets();
        this.mLayoutCalledWhileSuppressed = false;
        this.mChildrenInterestedInDrag = null;
        this.mIsInterestedInDrag = false;
        if (this.mCurrentDragStartEvent != null) {
            this.mCurrentDragStartEvent.recycle();
            this.mCurrentDragStartEvent = null;
        }
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchDetachedFromWindow();
        }
        clearDisappearingChildren();
        int size = this.mTransientViews == null ? 0 : this.mTransientIndices.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mTransientViews.get(i3).dispatchDetachedFromWindow();
        }
        super.dispatchDetachedFromWindow();
    }

    @Override // android.view.View
    protected void internalSetPadding(int i, int i2, int i3, int i4) {
        super.internalSetPadding(i, i2, i3, i4);
        if ((this.mPaddingLeft | this.mPaddingTop | this.mPaddingRight | this.mPaddingBottom) != 0) {
            this.mGroupFlags |= 32;
        } else {
            this.mGroupFlags &= -33;
        }
    }

    @Override // android.view.View
    protected void dispatchSaveInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        super.dispatchSaveInstanceState(sparseArray);
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view = viewArr[i2];
            if ((view.mViewFlags & 536870912) != 536870912) {
                view.dispatchSaveInstanceState(sparseArray);
            }
        }
    }

    protected void dispatchFreezeSelfOnly(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        super.dispatchSaveInstanceState(sparseArray);
    }

    @Override // android.view.View
    protected void dispatchRestoreInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view = viewArr[i2];
            if ((view.mViewFlags & 536870912) != 536870912) {
                view.dispatchRestoreInstanceState(sparseArray);
            }
        }
    }

    protected void dispatchThawSelfOnly(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @java.lang.Deprecated
    public void setChildrenDrawingCacheEnabled(boolean z) {
        if (z || (this.mPersistentDrawingCache & 3) != 3) {
            android.view.View[] viewArr = this.mChildren;
            int i = this.mChildrenCount;
            for (int i2 = 0; i2 < i; i2++) {
                viewArr[i2].setDrawingCacheEnabled(z);
            }
        }
    }

    @Override // android.view.View
    public android.graphics.Bitmap createSnapshot(android.view.ViewDebug.CanvasProvider canvasProvider, boolean z) {
        int[] iArr;
        int i = this.mChildrenCount;
        int i2 = 0;
        if (z) {
            iArr = new int[i];
            for (int i3 = 0; i3 < i; i3++) {
                android.view.View childAt = getChildAt(i3);
                iArr[i3] = childAt.getVisibility();
                if (iArr[i3] == 0) {
                    childAt.mViewFlags = (childAt.mViewFlags & (-13)) | 4;
                }
            }
        } else {
            iArr = null;
        }
        try {
            return super.createSnapshot(canvasProvider, z);
        } finally {
            if (z) {
                while (i2 < i) {
                    android.view.View childAt2 = getChildAt(i2);
                    childAt2.mViewFlags = (childAt2.mViewFlags & (-13)) | (iArr[i2] & 12);
                    i2++;
                }
            }
        }
    }

    boolean isLayoutModeOptical() {
        return this.mLayoutMode == 1;
    }

    @Override // android.view.View
    android.graphics.Insets computeOpticalInsets() {
        if (isLayoutModeOptical()) {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < this.mChildrenCount; i5++) {
                android.view.View childAt = getChildAt(i5);
                if (childAt.getVisibility() == 0) {
                    android.graphics.Insets opticalInsets = childAt.getOpticalInsets();
                    i = java.lang.Math.max(i, opticalInsets.left);
                    i2 = java.lang.Math.max(i2, opticalInsets.top);
                    i3 = java.lang.Math.max(i3, opticalInsets.right);
                    i4 = java.lang.Math.max(i4, opticalInsets.bottom);
                }
            }
            return android.graphics.Insets.of(i, i2, i3, i4);
        }
        return android.graphics.Insets.NONE;
    }

    private static void fillRect(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4) {
        if (i != i3 && i2 != i4) {
            if (i > i3) {
                i3 = i;
                i = i3;
            }
            if (i2 > i4) {
                i4 = i2;
                i2 = i4;
            }
            canvas.drawRect(i, i2, i3, i4, paint);
        }
    }

    private static int sign(int i) {
        return i >= 0 ? 1 : -1;
    }

    private static void drawCorner(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4, int i5) {
        fillRect(canvas, paint, i, i2, i + i3, i2 + (sign(i4) * i5));
        fillRect(canvas, paint, i, i2, i + (i5 * sign(i3)), i2 + i4);
    }

    private static void drawRectCorners(android.graphics.Canvas canvas, int i, int i2, int i3, int i4, android.graphics.Paint paint, int i5, int i6) {
        drawCorner(canvas, paint, i, i2, i5, i5, i6);
        int i7 = -i5;
        drawCorner(canvas, paint, i, i4, i5, i7, i6);
        drawCorner(canvas, paint, i3, i2, i7, i5, i6);
        drawCorner(canvas, paint, i3, i4, i7, i7, i6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void fillDifference(android.graphics.Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, android.graphics.Paint paint) {
        int i9 = i - i5;
        int i10 = i3 + i7;
        fillRect(canvas, paint, i9, i2 - i6, i10, i2);
        fillRect(canvas, paint, i9, i2, i, i4);
        fillRect(canvas, paint, i3, i2, i10, i4);
        fillRect(canvas, paint, i9, i4, i10, i4 + i8);
    }

    protected void onDebugDrawMargins(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        for (int i = 0; i < getChildCount(); i++) {
            android.view.View childAt = getChildAt(i);
            childAt.getLayoutParams().onDebugDraw(childAt, canvas, paint);
        }
    }

    protected void onDebugDraw(android.graphics.Canvas canvas) {
        android.graphics.Paint debugPaint = getDebugPaint();
        debugPaint.setColor(-65536);
        debugPaint.setStyle(android.graphics.Paint.Style.STROKE);
        for (int i = 0; i < getChildCount(); i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                android.graphics.Insets opticalInsets = childAt.getOpticalInsets();
                drawRect(canvas, debugPaint, childAt.getLeft() + opticalInsets.left, childAt.getTop() + opticalInsets.top, (childAt.getRight() - opticalInsets.right) - 1, (childAt.getBottom() - opticalInsets.bottom) - 1);
            }
        }
        debugPaint.setColor(android.graphics.Color.argb(63, 255, 0, 255));
        debugPaint.setStyle(android.graphics.Paint.Style.FILL);
        onDebugDrawMargins(canvas, debugPaint);
        debugPaint.setColor(DEBUG_CORNERS_COLOR);
        debugPaint.setStyle(android.graphics.Paint.Style.FILL);
        int dipsToPixels = dipsToPixels(8);
        int dipsToPixels2 = dipsToPixels(1);
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            android.view.View childAt2 = getChildAt(i2);
            if (childAt2.getVisibility() != 8) {
                drawRectCorners(canvas, childAt2.getLeft(), childAt2.getTop(), childAt2.getRight(), childAt2.getBottom(), debugPaint, dipsToPixels, dipsToPixels2);
            }
        }
    }

    @Override // android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        int i;
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        int i3 = this.mGroupFlags;
        if ((i3 & 8) != 0 && canAnimate()) {
            for (int i4 = 0; i4 < i2; i4++) {
                android.view.View view = viewArr[i4];
                if ((view.mViewFlags & 12) == 0) {
                    attachLayoutAnimationParameters(view, view.getLayoutParams(), i4, i2);
                    bindLayoutAnimation(view);
                }
            }
            android.view.animation.LayoutAnimationController layoutAnimationController = this.mLayoutAnimationController;
            if (layoutAnimationController.willOverlap()) {
                this.mGroupFlags |= 128;
            }
            layoutAnimationController.start();
            this.mGroupFlags &= -9;
            this.mGroupFlags &= -17;
            if (this.mAnimationListener != null) {
                this.mAnimationListener.onAnimationStart(layoutAnimationController.getAnimation());
            }
        }
        boolean z = (i3 & 34) == 34;
        if (!z) {
            i = 0;
        } else {
            i = canvas.save(2);
            canvas.clipRect(this.mScrollX + this.mPaddingLeft, this.mScrollY + this.mPaddingTop, ((this.mScrollX + this.mRight) - this.mLeft) - this.mPaddingRight, ((this.mScrollY + this.mBottom) - this.mTop) - this.mPaddingBottom);
        }
        this.mPrivateFlags &= -65;
        this.mGroupFlags &= -5;
        long drawingTime = getDrawingTime();
        canvas.enableZ();
        int size = this.mTransientIndices == null ? 0 : this.mTransientIndices.size();
        int i5 = size != 0 ? 0 : -1;
        java.util.ArrayList<android.view.View> buildOrderedChildList = drawsWithRenderNode(canvas) ? null : buildOrderedChildList();
        boolean z2 = buildOrderedChildList == null && isChildrenDrawingOrderEnabled();
        int i6 = i5;
        boolean z3 = false;
        for (int i7 = 0; i7 < i2; i7++) {
            while (i6 >= 0 && this.mTransientIndices.get(i6) == i7) {
                android.view.View view2 = this.mTransientViews.get(i6);
                if ((view2.mViewFlags & 12) == 0 || view2.getAnimation() != null) {
                    z3 = drawChild(canvas, view2, drawingTime) | z3;
                }
                i6++;
                if (i6 >= size) {
                    i6 = -1;
                }
            }
            android.view.View andVerifyPreorderedView = getAndVerifyPreorderedView(buildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i2, i7, z2));
            if ((andVerifyPreorderedView.mViewFlags & 12) == 0 || andVerifyPreorderedView.getAnimation() != null) {
                z3 = drawChild(canvas, andVerifyPreorderedView, drawingTime) | z3;
            }
        }
        while (i6 >= 0) {
            android.view.View view3 = this.mTransientViews.get(i6);
            if ((view3.mViewFlags & 12) == 0 || view3.getAnimation() != null) {
                z3 = drawChild(canvas, view3, drawingTime) | z3;
            }
            i6++;
            if (i6 >= size) {
                break;
            }
        }
        if (buildOrderedChildList != null) {
            buildOrderedChildList.clear();
        }
        if (this.mDisappearingChildren != null) {
            java.util.ArrayList<android.view.View> arrayList = this.mDisappearingChildren;
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                z3 |= drawChild(canvas, arrayList.get(size2), drawingTime);
            }
        }
        canvas.disableZ();
        if (isShowingLayoutBounds()) {
            onDebugDraw(canvas);
        }
        if (z) {
            canvas.restoreToCount(i);
        }
        int i8 = this.mGroupFlags;
        if ((i8 & 4) == 4) {
            invalidate(true);
        }
        if ((i8 & 16) == 0 && (i8 & 512) == 0 && this.mLayoutAnimationController.isDone() && !z3) {
            this.mGroupFlags |= 512;
            post(new java.lang.Runnable() { // from class: android.view.ViewGroup.2
                @Override // java.lang.Runnable
                public void run() {
                    android.view.ViewGroup.this.notifyAnimationListener();
                }
            });
        }
    }

    @Override // android.view.View
    public android.view.ViewGroupOverlay getOverlay() {
        if (this.mOverlay == null) {
            this.mOverlay = new android.view.ViewGroupOverlay(this.mContext, this);
        }
        return (android.view.ViewGroupOverlay) this.mOverlay;
    }

    protected int getChildDrawingOrder(int i, int i2) {
        return i2;
    }

    public final int getChildDrawingOrder(int i) {
        return getChildDrawingOrder(getChildCount(), i);
    }

    private boolean hasChildWithZ() {
        for (int i = 0; i < this.mChildrenCount; i++) {
            if (this.mChildren[i].getZ() != 0.0f) {
                return true;
            }
        }
        return false;
    }

    java.util.ArrayList<android.view.View> buildOrderedChildList() {
        int i = this.mChildrenCount;
        if (i <= 1 || !hasChildWithZ()) {
            return null;
        }
        if (this.mPreSortedChildren == null) {
            this.mPreSortedChildren = new java.util.ArrayList<>(i);
        } else {
            this.mPreSortedChildren.clear();
            this.mPreSortedChildren.ensureCapacity(i);
        }
        boolean isChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view = this.mChildren[getAndVerifyPreorderedIndex(i, i2, isChildrenDrawingOrderEnabled)];
            float z = view.getZ();
            int i3 = i2;
            while (i3 > 0 && this.mPreSortedChildren.get(i3 - 1).getZ() > z) {
                i3--;
            }
            this.mPreSortedChildren.add(i3, view);
        }
        return this.mPreSortedChildren;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAnimationListener() {
        this.mGroupFlags &= -513;
        this.mGroupFlags |= 16;
        if (this.mAnimationListener != null) {
            post(new java.lang.Runnable() { // from class: android.view.ViewGroup.3
                @Override // java.lang.Runnable
                public void run() {
                    android.view.ViewGroup.this.mAnimationListener.onAnimationEnd(android.view.ViewGroup.this.mLayoutAnimationController.getAnimation());
                }
            });
        }
        invalidate(true);
    }

    @Override // android.view.View
    protected void dispatchGetDisplayList() {
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view = viewArr[i2];
            if ((view.mViewFlags & 12) == 0 || view.getAnimation() != null) {
                recreateChildDisplayList(view);
            }
        }
        int size = this.mTransientViews == null ? 0 : this.mTransientIndices.size();
        for (int i3 = 0; i3 < size; i3++) {
            android.view.View view2 = this.mTransientViews.get(i3);
            if ((view2.mViewFlags & 12) == 0 || view2.getAnimation() != null) {
                recreateChildDisplayList(view2);
            }
        }
        if (this.mOverlay != null) {
            recreateChildDisplayList(this.mOverlay.getOverlayView());
        }
        if (this.mDisappearingChildren != null) {
            java.util.ArrayList<android.view.View> arrayList = this.mDisappearingChildren;
            int size2 = arrayList.size();
            for (int i4 = 0; i4 < size2; i4++) {
                recreateChildDisplayList(arrayList.get(i4));
            }
        }
    }

    private void recreateChildDisplayList(android.view.View view) {
        view.mRecreateDisplayList = (view.mPrivateFlags & Integer.MIN_VALUE) != 0;
        view.mPrivateFlags &= Integer.MAX_VALUE;
        view.updateDisplayListIfDirty();
        view.mRecreateDisplayList = false;
    }

    protected boolean drawChild(android.graphics.Canvas canvas, android.view.View view, long j) {
        return view.draw(canvas, this, j);
    }

    @Override // android.view.View
    void getScrollIndicatorBounds(android.graphics.Rect rect) {
        super.getScrollIndicatorBounds(rect);
        if ((this.mGroupFlags & 34) == 34) {
            rect.left += this.mPaddingLeft;
            rect.right -= this.mPaddingRight;
            rect.top += this.mPaddingTop;
            rect.bottom -= this.mPaddingBottom;
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public boolean getClipChildren() {
        return (this.mGroupFlags & 1) != 0;
    }

    public void setClipChildren(boolean z) {
        if (z != ((this.mGroupFlags & 1) == 1)) {
            setBooleanFlag(1, z);
            for (int i = 0; i < this.mChildrenCount; i++) {
                android.view.View childAt = getChildAt(i);
                if (childAt.mRenderNode != null) {
                    childAt.mRenderNode.setClipToBounds(z);
                }
            }
            invalidate(true);
        }
    }

    public void setClipToPadding(boolean z) {
        if (hasBooleanFlag(2) != z) {
            setBooleanFlag(2, z);
            invalidate(true);
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public boolean getClipToPadding() {
        return hasBooleanFlag(2);
    }

    @Override // android.view.View
    public void dispatchSetSelected(boolean z) {
        android.view.View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].setSelected(z);
        }
    }

    @Override // android.view.View
    public void dispatchSetActivated(boolean z) {
        android.view.View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].setActivated(z);
        }
    }

    @Override // android.view.View
    protected void dispatchSetPressed(boolean z) {
        android.view.View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view = viewArr[i2];
            if (!z || (!view.isClickable() && !view.isLongClickable())) {
                view.setPressed(z);
            }
        }
    }

    @Override // android.view.View
    public void dispatchDrawableHotspotChanged(float f, float f2) {
        int i = this.mChildrenCount;
        if (i == 0) {
            return;
        }
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view = viewArr[i2];
            boolean z = (view.isClickable() || view.isLongClickable()) ? false : true;
            boolean z2 = (view.mViewFlags & 4194304) != 0;
            if (z || z2) {
                float[] tempLocationF = getTempLocationF();
                tempLocationF[0] = f;
                tempLocationF[1] = f2;
                transformPointToViewLocal(tempLocationF, view);
                view.drawableHotspotChanged(tempLocationF[0], tempLocationF[1]);
            }
        }
    }

    @Override // android.view.View
    void dispatchCancelPendingInputEvents() {
        super.dispatchCancelPendingInputEvents();
        android.view.View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchCancelPendingInputEvents();
        }
    }

    protected void setStaticTransformationsEnabled(boolean z) {
        setBooleanFlag(2048, z);
    }

    protected boolean getChildStaticTransformation(android.view.View view, android.view.animation.Transformation transformation) {
        return false;
    }

    android.view.animation.Transformation getChildTransformation() {
        if (this.mChildTransformation == null) {
            this.mChildTransformation = new android.view.animation.Transformation();
        }
        return this.mChildTransformation;
    }

    @Override // android.view.View
    protected <T extends android.view.View> T findViewTraversal(int i) {
        T t;
        if (i == this.mID) {
            return this;
        }
        android.view.View[] viewArr = this.mChildren;
        int i2 = this.mChildrenCount;
        for (int i3 = 0; i3 < i2; i3++) {
            android.view.View view = viewArr[i3];
            if ((view.mPrivateFlags & 8) == 0 && (t = (T) view.findViewById(i)) != null) {
                return t;
            }
        }
        return null;
    }

    @Override // android.view.View
    protected <T extends android.view.View> T findViewWithTagTraversal(java.lang.Object obj) {
        T t;
        if (obj != null && obj.equals(this.mTag)) {
            return this;
        }
        android.view.View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view = viewArr[i2];
            if ((view.mPrivateFlags & 8) == 0 && (t = (T) view.findViewWithTag(obj)) != null) {
                return t;
            }
        }
        return null;
    }

    @Override // android.view.View
    protected <T extends android.view.View> T findViewByPredicateTraversal(java.util.function.Predicate<android.view.View> predicate, android.view.View view) {
        T t;
        if (predicate.test(this)) {
            return this;
        }
        android.view.View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            android.view.View view2 = viewArr[i2];
            if (view2 != view && (view2.mPrivateFlags & 8) == 0 && (t = (T) view2.findViewByPredicate(predicate)) != null) {
                return t;
            }
        }
        return null;
    }

    public void addTransientView(android.view.View view, int i) {
        if (i < 0 || view == null) {
            return;
        }
        if (view.mParent != null) {
            throw new java.lang.IllegalStateException("The specified view already has a parent " + view.mParent);
        }
        if (this.mTransientIndices == null) {
            this.mTransientIndices = new android.util.IntArray();
            this.mTransientViews = new java.util.ArrayList();
        }
        int size = this.mTransientIndices.size();
        if (size > 0) {
            int i2 = 0;
            while (i2 < size && i >= this.mTransientIndices.get(i2)) {
                i2++;
            }
            this.mTransientIndices.add(i2, i);
            this.mTransientViews.add(i2, view);
        } else {
            this.mTransientIndices.add(i);
            this.mTransientViews.add(view);
        }
        view.mParent = this;
        if (this.mAttachInfo != null) {
            view.dispatchAttachedToWindow(this.mAttachInfo, this.mViewFlags & 12);
        }
        invalidate(true);
    }

    public void removeTransientView(android.view.View view) {
        if (this.mTransientViews == null) {
            return;
        }
        int size = this.mTransientViews.size();
        for (int i = 0; i < size; i++) {
            if (view == this.mTransientViews.get(i)) {
                this.mTransientViews.remove(i);
                this.mTransientIndices.remove(i);
                view.mParent = null;
                if (view.mAttachInfo != null) {
                    view.dispatchDetachedFromWindow();
                }
                invalidate(true);
                return;
            }
        }
    }

    public int getTransientViewCount() {
        if (this.mTransientIndices == null) {
            return 0;
        }
        return this.mTransientIndices.size();
    }

    public int getTransientViewIndex(int i) {
        if (i < 0 || this.mTransientIndices == null || i >= this.mTransientIndices.size()) {
            return -1;
        }
        return this.mTransientIndices.get(i);
    }

    public android.view.View getTransientView(int i) {
        if (this.mTransientViews == null || i >= this.mTransientViews.size()) {
            return null;
        }
        return this.mTransientViews.get(i);
    }

    public void addView(android.view.View view) {
        addView(view, -1);
    }

    public void addView(android.view.View view, int i) {
        if (view == null) {
            throw new java.lang.IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null && (layoutParams = generateDefaultLayoutParams()) == null) {
            throw new java.lang.IllegalArgumentException("generateDefaultLayoutParams() cannot return null  ");
        }
        addView(view, i, layoutParams);
    }

    public void addView(android.view.View view, int i, int i2) {
        android.view.ViewGroup.LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
        generateDefaultLayoutParams.width = i;
        generateDefaultLayoutParams.height = i2;
        addView(view, -1, generateDefaultLayoutParams);
    }

    public void addView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        addView(view, -1, layoutParams);
    }

    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            throw new java.lang.IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }
        requestLayout();
        invalidate(true);
        addViewInner(view, i, layoutParams, false);
    }

    @Override // android.view.ViewManager
    public void updateViewLayout(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            throw new java.lang.IllegalArgumentException("Invalid LayoutParams supplied to " + this);
        }
        if (view.mParent != this) {
            throw new java.lang.IllegalArgumentException("Given view not a child of " + this);
        }
        view.setLayoutParams(layoutParams);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null;
    }

    public void setOnHierarchyChangeListener(android.view.ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    void dispatchViewAdded(android.view.View view) {
        onViewAdded(view);
        if (this.mOnHierarchyChangeListener != null) {
            this.mOnHierarchyChangeListener.onChildViewAdded(this, view);
        }
    }

    public void onViewAdded(android.view.View view) {
    }

    void dispatchViewRemoved(android.view.View view) {
        onViewRemoved(view);
        if (this.mOnHierarchyChangeListener != null) {
            this.mOnHierarchyChangeListener.onChildViewRemoved(this, view);
        }
    }

    public void onViewRemoved(android.view.View view) {
    }

    private void clearCachedLayoutMode() {
        if (!hasBooleanFlag(8388608)) {
            this.mLayoutMode = -1;
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        clearCachedLayoutMode();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearCachedLayoutMode();
    }

    @Override // android.view.View
    protected void destroyHardwareResources() {
        super.destroyHardwareResources();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).destroyHardwareResources();
        }
    }

    protected boolean addViewInLayout(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        return addViewInLayout(view, i, layoutParams, false);
    }

    protected boolean addViewInLayout(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams, boolean z) {
        if (view == null) {
            throw new java.lang.IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }
        view.mParent = null;
        addViewInner(view, i, layoutParams, z);
        view.mPrivateFlags = (view.mPrivateFlags & (-2097153)) | 32;
        return true;
    }

    protected void cleanupLayoutState(android.view.View view) {
        view.mPrivateFlags &= -4097;
    }

    private void addViewInner(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams, boolean z) {
        if (this.mTransition != null) {
            this.mTransition.cancel(3);
        }
        if (view.getParent() != null) {
            throw new java.lang.IllegalStateException("The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        if (this.mTransition != null) {
            this.mTransition.addChild(this, view);
        }
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        if (z) {
            view.mLayoutParams = layoutParams;
        } else {
            view.setLayoutParams(layoutParams);
        }
        if (i < 0) {
            i = this.mChildrenCount;
        }
        addInArray(view, i);
        if (z) {
            view.assignParent(this);
        } else {
            view.mParent = this;
        }
        if (view.hasUnhandledKeyListener()) {
            incrementChildUnhandledKeyListeners();
        }
        if (view.hasFocus()) {
            requestChildFocus(view, view.findFocus());
        }
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && (this.mGroupFlags & 4194304) == 0) {
            boolean z2 = attachInfo.mKeepScreenOn;
            attachInfo.mKeepScreenOn = false;
            view.dispatchAttachedToWindow(this.mAttachInfo, this.mViewFlags & 12);
            if (attachInfo.mKeepScreenOn) {
                needGlobalAttributesUpdate(true);
            }
            attachInfo.mKeepScreenOn = z2;
        }
        if (view.isLayoutDirectionInherited()) {
            view.resetRtlProperties();
        }
        dispatchViewAdded(view);
        if ((view.mViewFlags & 4194304) == 4194304) {
            this.mGroupFlags |= 65536;
        }
        if (view.hasTransientState()) {
            childHasTransientStateChanged(view, true);
        }
        if (view.getVisibility() != 8) {
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
        if (this.mTransientIndices != null) {
            int size = this.mTransientIndices.size();
            for (int i2 = 0; i2 < size; i2++) {
                int i3 = this.mTransientIndices.get(i2);
                if (i <= i3) {
                    this.mTransientIndices.set(i2, i3 + 1);
                }
            }
        }
        if (this.mCurrentDragStartEvent != null && view.getVisibility() == 0) {
            notifyChildOfDragStart(view);
        }
        if (view.hasDefaultFocus()) {
            setDefaultFocus(view);
        }
        touchAccessibilityNodeProviderIfNeeded(view);
    }

    private void touchAccessibilityNodeProviderIfNeeded(android.view.View view) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            view.getAccessibilityNodeProvider();
        }
    }

    private void addInArray(android.view.View view, int i) {
        android.view.View[] viewArr = this.mChildren;
        int i2 = this.mChildrenCount;
        int length = viewArr.length;
        if (i == i2) {
            if (length == i2) {
                this.mChildren = new android.view.View[length + 12];
                java.lang.System.arraycopy(viewArr, 0, this.mChildren, 0, length);
                viewArr = this.mChildren;
            }
            int i3 = this.mChildrenCount;
            this.mChildrenCount = i3 + 1;
            viewArr[i3] = view;
            return;
        }
        if (i < i2) {
            if (length == i2) {
                this.mChildren = new android.view.View[length + 12];
                java.lang.System.arraycopy(viewArr, 0, this.mChildren, 0, i);
                java.lang.System.arraycopy(viewArr, i, this.mChildren, i + 1, i2 - i);
                viewArr = this.mChildren;
            } else {
                java.lang.System.arraycopy(viewArr, i, viewArr, i + 1, i2 - i);
            }
            viewArr[i] = view;
            this.mChildrenCount++;
            if (this.mLastTouchDownIndex >= i) {
                this.mLastTouchDownIndex++;
                return;
            }
            return;
        }
        throw new java.lang.IndexOutOfBoundsException("index=" + i + " count=" + i2);
    }

    private void removeFromArray(int i) {
        android.view.View[] viewArr = this.mChildren;
        if (this.mTransitioningViews == null || !this.mTransitioningViews.contains(viewArr[i])) {
            viewArr[i].mParent = null;
        }
        int i2 = this.mChildrenCount;
        if (i == i2 - 1) {
            int i3 = this.mChildrenCount - 1;
            this.mChildrenCount = i3;
            viewArr[i3] = null;
        } else if (i >= 0 && i < i2) {
            java.lang.System.arraycopy(viewArr, i + 1, viewArr, i, (i2 - i) - 1);
            int i4 = this.mChildrenCount - 1;
            this.mChildrenCount = i4;
            viewArr[i4] = null;
        } else {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (this.mLastTouchDownIndex == i) {
            this.mLastTouchDownTime = 0L;
            this.mLastTouchDownIndex = -1;
        } else if (this.mLastTouchDownIndex > i) {
            this.mLastTouchDownIndex--;
        }
    }

    private void removeFromArray(int i, int i2) {
        android.view.View[] viewArr = this.mChildren;
        int i3 = this.mChildrenCount;
        int max = java.lang.Math.max(0, i);
        int min = java.lang.Math.min(i3, i2 + max);
        if (max == min) {
            return;
        }
        if (min == i3) {
            for (int i4 = max; i4 < min; i4++) {
                viewArr[i4].mParent = null;
                viewArr[i4] = null;
            }
        } else {
            for (int i5 = max; i5 < min; i5++) {
                viewArr[i5].mParent = null;
            }
            java.lang.System.arraycopy(viewArr, min, viewArr, max, i3 - min);
            for (int i6 = i3 - (min - max); i6 < i3; i6++) {
                viewArr[i6] = null;
            }
        }
        this.mChildrenCount -= min - max;
    }

    private void bindLayoutAnimation(android.view.View view) {
        view.setAnimation(this.mLayoutAnimationController.getAnimationForView(view));
    }

    protected void attachLayoutAnimationParameters(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams, int i, int i2) {
        android.view.animation.LayoutAnimationController.AnimationParameters animationParameters = layoutParams.layoutAnimationParameters;
        if (animationParameters == null) {
            animationParameters = new android.view.animation.LayoutAnimationController.AnimationParameters();
            layoutParams.layoutAnimationParameters = animationParameters;
        }
        animationParameters.count = i2;
        animationParameters.index = i;
    }

    @Override // android.view.ViewManager
    public void removeView(android.view.View view) {
        if (removeViewInternal(view)) {
            requestLayout();
            invalidate(true);
        }
    }

    public void removeViewInLayout(android.view.View view) {
        removeViewInternal(view);
    }

    public void removeViewsInLayout(int i, int i2) {
        removeViewsInternal(i, i2);
    }

    public void removeViewAt(int i) {
        removeViewInternal(i, getChildAt(i));
        requestLayout();
        invalidate(true);
    }

    public void removeViews(int i, int i2) {
        removeViewsInternal(i, i2);
        requestLayout();
        invalidate(true);
    }

    private boolean removeViewInternal(android.view.View view) {
        int indexOfChild = indexOfChild(view);
        if (indexOfChild >= 0) {
            removeViewInternal(indexOfChild, view);
            return true;
        }
        return false;
    }

    private void removeViewInternal(int i, android.view.View view) {
        boolean z;
        if (this.mTransition != null) {
            this.mTransition.removeChild(this, view);
        }
        if (view != this.mFocused) {
            z = false;
        } else {
            view.unFocus(null);
            z = true;
        }
        if (view == this.mFocusedInCluster) {
            clearFocusedInCluster(view);
        }
        view.clearAccessibilityFocus();
        cancelTouchTarget(view);
        cancelHoverTarget(view);
        if (view.getAnimation() != null || (this.mTransitioningViews != null && this.mTransitioningViews.contains(view))) {
            addDisappearingView(view);
        } else if (view.mAttachInfo != null) {
            view.dispatchDetachedFromWindow();
        }
        if (view.hasTransientState()) {
            childHasTransientStateChanged(view, false);
        }
        needGlobalAttributesUpdate(false);
        removeFromArray(i);
        if (view.hasUnhandledKeyListener()) {
            decrementChildUnhandledKeyListeners();
        }
        if (view == this.mDefaultFocus) {
            clearDefaultFocus(view);
        }
        if (z) {
            clearChildFocus(view);
            if (!rootViewRequestFocus()) {
                notifyGlobalFocusCleared(this);
            }
        }
        dispatchViewRemoved(view);
        if (view.getVisibility() != 8) {
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
        int size = this.mTransientIndices == null ? 0 : this.mTransientIndices.size();
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = this.mTransientIndices.get(i2);
            if (i < i3) {
                this.mTransientIndices.set(i2, i3 - 1);
            }
        }
        if (this.mCurrentDragStartEvent != null) {
            this.mChildrenInterestedInDrag.remove(view);
        }
    }

    public void setLayoutTransition(android.animation.LayoutTransition layoutTransition) {
        if (this.mTransition != null) {
            android.animation.LayoutTransition layoutTransition2 = this.mTransition;
            layoutTransition2.cancel();
            layoutTransition2.removeTransitionListener(this.mLayoutTransitionListener);
        }
        this.mTransition = layoutTransition;
        if (this.mTransition != null) {
            this.mTransition.addTransitionListener(this.mLayoutTransitionListener);
        }
    }

    public android.animation.LayoutTransition getLayoutTransition() {
        return this.mTransition;
    }

    private void removeViewsInternal(int i, int i2) {
        int i3 = i + i2;
        if (i < 0 || i2 < 0 || i3 > this.mChildrenCount) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        android.view.View view = this.mFocused;
        boolean z = this.mAttachInfo != null;
        android.view.View[] viewArr = this.mChildren;
        boolean z2 = false;
        android.view.View view2 = null;
        for (int i4 = i; i4 < i3; i4++) {
            android.view.View view3 = viewArr[i4];
            if (this.mTransition != null) {
                this.mTransition.removeChild(this, view3);
            }
            if (view3 == view) {
                view3.unFocus(null);
                z2 = true;
            }
            if (view3 == this.mDefaultFocus) {
                view2 = view3;
            }
            if (view3 == this.mFocusedInCluster) {
                clearFocusedInCluster(view3);
            }
            view3.clearAccessibilityFocus();
            cancelTouchTarget(view3);
            cancelHoverTarget(view3);
            if (view3.getAnimation() != null || (this.mTransitioningViews != null && this.mTransitioningViews.contains(view3))) {
                addDisappearingView(view3);
            } else if (z) {
                view3.dispatchDetachedFromWindow();
            }
            if (view3.hasTransientState()) {
                childHasTransientStateChanged(view3, false);
            }
            needGlobalAttributesUpdate(false);
            dispatchViewRemoved(view3);
        }
        removeFromArray(i, i2);
        if (view2 != null) {
            clearDefaultFocus(view2);
        }
        if (z2) {
            clearChildFocus(view);
            if (!rootViewRequestFocus()) {
                notifyGlobalFocusCleared(view);
            }
        }
    }

    public void removeAllViews() {
        removeAllViewsInLayout();
        requestLayout();
        invalidate(true);
    }

    public void removeAllViewsInLayout() {
        int i = this.mChildrenCount;
        if (i <= 0) {
            return;
        }
        android.view.View[] viewArr = this.mChildren;
        this.mChildrenCount = 0;
        android.view.View view = this.mFocused;
        boolean z = this.mAttachInfo != null;
        needGlobalAttributesUpdate(false);
        boolean z2 = false;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            android.view.View view2 = viewArr[i2];
            if (this.mTransition != null) {
                this.mTransition.removeChild(this, view2);
            }
            if (view2 == view) {
                view2.unFocus(null);
                z2 = true;
            }
            view2.clearAccessibilityFocus();
            cancelTouchTarget(view2);
            cancelHoverTarget(view2);
            if (view2.getAnimation() != null || (this.mTransitioningViews != null && this.mTransitioningViews.contains(view2))) {
                addDisappearingView(view2);
            } else if (z) {
                view2.dispatchDetachedFromWindow();
            }
            if (view2.hasTransientState()) {
                childHasTransientStateChanged(view2, false);
            }
            dispatchViewRemoved(view2);
            view2.mParent = null;
            viewArr[i2] = null;
        }
        if (this.mDefaultFocus != null) {
            clearDefaultFocus(this.mDefaultFocus);
        }
        if (this.mFocusedInCluster != null) {
            clearFocusedInCluster(this.mFocusedInCluster);
        }
        if (z2) {
            clearChildFocus(view);
            if (!rootViewRequestFocus()) {
                notifyGlobalFocusCleared(view);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void removeDetachedView(android.view.View view, boolean z) {
        if (this.mTransition != null) {
            this.mTransition.removeChild(this, view);
        }
        if (view == this.mFocused) {
            view.clearFocus();
        }
        if (view == this.mDefaultFocus) {
            clearDefaultFocus(view);
        }
        if (view == this.mFocusedInCluster) {
            clearFocusedInCluster(view);
        }
        view.clearAccessibilityFocus();
        cancelTouchTarget(view);
        cancelHoverTarget(view);
        if ((z && view.getAnimation() != null) || (this.mTransitioningViews != null && this.mTransitioningViews.contains(view))) {
            addDisappearingView(view);
        } else if (view.mAttachInfo != null) {
            view.dispatchDetachedFromWindow();
        }
        if (view.hasTransientState()) {
            childHasTransientStateChanged(view, false);
        }
        dispatchViewRemoved(view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void attachViewToParent(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        view.mLayoutParams = layoutParams;
        if (i < 0) {
            i = this.mChildrenCount;
        }
        addInArray(view, i);
        view.mParent = this;
        view.mPrivateFlags = (view.mPrivateFlags & (-2097153) & (-32769)) | 32 | Integer.MIN_VALUE;
        boolean z = false;
        view.setDetached(false);
        this.mPrivateFlags = Integer.MIN_VALUE | this.mPrivateFlags;
        if (view.hasFocus()) {
            requestChildFocus(view, view.findFocus());
        }
        if (isAttachedToWindow() && getWindowVisibility() == 0 && isShown()) {
            z = true;
        }
        dispatchVisibilityAggregated(z);
        notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    protected void detachViewFromParent(android.view.View view) {
        view.setDetached(true);
        removeFromArray(indexOfChild(view));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void detachViewFromParent(int i) {
        if (i >= 0 && i < this.mChildrenCount) {
            this.mChildren[i].setDetached(true);
        }
        removeFromArray(i);
    }

    protected void detachViewsFromParent(int i, int i2) {
        int max = java.lang.Math.max(0, i);
        int min = java.lang.Math.min(this.mChildrenCount, max + i2);
        for (int i3 = max; i3 < min; i3++) {
            this.mChildren[i3].setDetached(true);
        }
        removeFromArray(max, i2);
    }

    protected void detachAllViewsFromParent() {
        int i = this.mChildrenCount;
        if (i <= 0) {
            return;
        }
        android.view.View[] viewArr = this.mChildren;
        this.mChildrenCount = 0;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            viewArr[i2].mParent = null;
            viewArr[i2].setDetached(true);
            viewArr[i2] = null;
        }
    }

    @Override // android.view.ViewParent
    public void onDescendantInvalidated(android.view.View view, android.view.View view2) {
        this.mPrivateFlags |= view2.mPrivateFlags & 64;
        if ((view2.mPrivateFlags & (-2097153)) != 0) {
            this.mPrivateFlags = (this.mPrivateFlags & (-2097153)) | 2097152;
            this.mPrivateFlags &= -32769;
        }
        if (this.mLayerType == 1) {
            this.mPrivateFlags |= -2145386496;
            view2 = this;
        }
        if (this.mParent != null) {
            this.mParent.onDescendantInvalidated(this, view2);
        }
    }

    @Override // android.view.ViewParent
    @java.lang.Deprecated
    public final void invalidateChild(android.view.View view, android.graphics.Rect rect) {
        android.view.View view2;
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && attachInfo.mHardwareAccelerated) {
            onDescendantInvalidated(view, view);
            return;
        }
        if (attachInfo != null) {
            boolean z = (view.mPrivateFlags & 64) != 0;
            android.graphics.Matrix matrix = view.getMatrix();
            if (view.mLayerType != 0) {
                this.mPrivateFlags |= Integer.MIN_VALUE;
                this.mPrivateFlags &= -32769;
            }
            int[] iArr = attachInfo.mInvalidateChildLocation;
            iArr[0] = view.mLeft;
            iArr[1] = view.mTop;
            if (!matrix.isIdentity() || (this.mGroupFlags & 2048) != 0) {
                android.graphics.RectF rectF = attachInfo.mTmpTransformRect;
                rectF.set(rect);
                if ((this.mGroupFlags & 2048) != 0) {
                    android.view.animation.Transformation transformation = attachInfo.mTmpTransformation;
                    if (getChildStaticTransformation(view, transformation)) {
                        android.graphics.Matrix matrix2 = attachInfo.mTmpMatrix;
                        matrix2.set(transformation.getMatrix());
                        if (!matrix.isIdentity()) {
                            matrix2.preConcat(matrix);
                        }
                        matrix = matrix2;
                    }
                }
                matrix.mapRect(rectF);
                rect.set((int) java.lang.Math.floor(rectF.left), (int) java.lang.Math.floor(rectF.top), (int) java.lang.Math.ceil(rectF.right), (int) java.lang.Math.ceil(rectF.bottom));
            }
            android.view.ViewParent viewParent = this;
            do {
                if (!(viewParent instanceof android.view.View)) {
                    view2 = null;
                } else {
                    view2 = (android.view.View) viewParent;
                }
                if (z) {
                    if (view2 != null) {
                        view2.mPrivateFlags |= 64;
                    } else if (viewParent instanceof android.view.ViewRootImpl) {
                        ((android.view.ViewRootImpl) viewParent).mIsAnimating = true;
                    }
                }
                if (view2 != null && (view2.mPrivateFlags & 2097152) != 2097152) {
                    view2.mPrivateFlags = (view2.mPrivateFlags & (-2097153)) | 2097152;
                }
                viewParent = viewParent.invalidateChildInParent(iArr, rect);
                if (view2 != null) {
                    android.graphics.Matrix matrix3 = view2.getMatrix();
                    if (!matrix3.isIdentity()) {
                        android.graphics.RectF rectF2 = attachInfo.mTmpTransformRect;
                        rectF2.set(rect);
                        matrix3.mapRect(rectF2);
                        rect.set((int) java.lang.Math.floor(rectF2.left), (int) java.lang.Math.floor(rectF2.top), (int) java.lang.Math.ceil(rectF2.right), (int) java.lang.Math.ceil(rectF2.bottom));
                    }
                }
            } while (viewParent != null);
        }
    }

    @Override // android.view.ViewParent
    @java.lang.Deprecated
    public android.view.ViewParent invalidateChildInParent(int[] iArr, android.graphics.Rect rect) {
        if ((this.mPrivateFlags & 32800) != 0) {
            if ((this.mGroupFlags & 144) != 128) {
                rect.offset(iArr[0] - this.mScrollX, iArr[1] - this.mScrollY);
                if ((this.mGroupFlags & 1) == 0) {
                    rect.union(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
                }
                int i = this.mLeft;
                int i2 = this.mTop;
                if ((this.mGroupFlags & 1) == 1 && !rect.intersect(0, 0, this.mRight - i, this.mBottom - i2)) {
                    rect.setEmpty();
                }
                iArr[0] = i;
                iArr[1] = i2;
            } else {
                if ((this.mGroupFlags & 1) == 1) {
                    rect.set(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
                } else {
                    rect.union(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
                }
                iArr[0] = this.mLeft;
                iArr[1] = this.mTop;
                this.mPrivateFlags &= -33;
            }
            this.mPrivateFlags &= -32769;
            if (this.mLayerType != 0) {
                this.mPrivateFlags |= Integer.MIN_VALUE;
            }
            return this.mParent;
        }
        return null;
    }

    public final void offsetDescendantRectToMyCoords(android.view.View view, android.graphics.Rect rect) {
        offsetRectBetweenParentAndChild(view, rect, true, false);
    }

    public final void offsetRectIntoDescendantCoords(android.view.View view, android.graphics.Rect rect) {
        offsetRectBetweenParentAndChild(view, rect, false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0063, code lost:
    
        if (r8 == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0065, code lost:
    
        r7.offset(r6.mLeft - r6.mScrollX, r6.mTop - r6.mScrollY);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0073, code lost:
    
        r7.offset(r6.mScrollX - r6.mLeft, r6.mScrollY - r6.mTop);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0080, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void offsetRectBetweenParentAndChild(android.view.View view, android.graphics.Rect rect, boolean z, boolean z2) {
        if (view == this) {
            return;
        }
        java.lang.Object obj = view.mParent;
        while (obj != null && (obj instanceof android.view.View) && obj != this) {
            if (z) {
                rect.offset(view.mLeft - view.mScrollX, view.mTop - view.mScrollY);
                if (z2) {
                    android.view.View view2 = (android.view.View) obj;
                    if (!rect.intersect(0, 0, view2.mRight - view2.mLeft, view2.mBottom - view2.mTop)) {
                        rect.setEmpty();
                    }
                }
            } else {
                if (z2) {
                    android.view.View view3 = (android.view.View) obj;
                    if (!rect.intersect(0, 0, view3.mRight - view3.mLeft, view3.mBottom - view3.mTop)) {
                        rect.setEmpty();
                    }
                }
                rect.offset(view.mScrollX - view.mLeft, view.mScrollY - view.mTop);
            }
            view = (android.view.View) obj;
            obj = view.mParent;
        }
        throw new java.lang.IllegalArgumentException("parameter must be a descendant of this view");
    }

    public void offsetChildrenTopAndBottom(int i) {
        int i2 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        boolean z = false;
        for (int i3 = 0; i3 < i2; i3++) {
            android.view.View view = viewArr[i3];
            view.mTop += i;
            view.mBottom += i;
            if (view.mRenderNode != null) {
                view.mRenderNode.offsetTopAndBottom(i);
                z = true;
            }
        }
        if (z) {
            invalidateViewProperty(false, false);
        }
        notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    @Override // android.view.ViewParent
    public boolean getChildVisibleRect(android.view.View view, android.graphics.Rect rect, android.graphics.Point point) {
        return getChildVisibleRect(view, rect, point, false);
    }

    public boolean getChildVisibleRect(android.view.View view, android.graphics.Rect rect, android.graphics.Point point, boolean z) {
        android.graphics.RectF rectF = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformRect : new android.graphics.RectF();
        rectF.set(rect);
        if (!view.hasIdentityMatrix()) {
            view.getMatrix().mapRect(rectF);
        }
        int i = view.mLeft - this.mScrollX;
        int i2 = view.mTop - this.mScrollY;
        rectF.offset(i, i2);
        boolean z2 = true;
        if (point != null) {
            if (!view.hasIdentityMatrix()) {
                float[] fArr = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformLocation : new float[2];
                fArr[0] = point.x;
                fArr[1] = point.y;
                view.getMatrix().mapPoints(fArr);
                point.x = java.lang.Math.round(fArr[0]);
                point.y = java.lang.Math.round(fArr[1]);
            }
            point.x += i;
            point.y += i2;
        }
        int i3 = this.mRight - this.mLeft;
        int i4 = this.mBottom - this.mTop;
        if (this.mParent == null || ((this.mParent instanceof android.view.ViewGroup) && ((android.view.ViewGroup) this.mParent).getClipChildren())) {
            z2 = rectF.intersect(0.0f, 0.0f, i3, i4);
        }
        if ((z || z2) && (this.mGroupFlags & 34) == 34) {
            z2 = rectF.intersect(this.mPaddingLeft, this.mPaddingTop, i3 - this.mPaddingRight, i4 - this.mPaddingBottom);
        }
        if ((z || z2) && this.mClipBounds != null) {
            z2 = rectF.intersect(this.mClipBounds.left, this.mClipBounds.top, this.mClipBounds.right, this.mClipBounds.bottom);
        }
        rect.set((int) java.lang.Math.floor(rectF.left), (int) java.lang.Math.floor(rectF.top), (int) java.lang.Math.ceil(rectF.right), (int) java.lang.Math.ceil(rectF.bottom));
        if ((z || z2) && this.mParent != null) {
            if (this.mParent instanceof android.view.ViewGroup) {
                return ((android.view.ViewGroup) this.mParent).getChildVisibleRect(this, rect, point, z);
            }
            return this.mParent.getChildVisibleRect(this, rect, point);
        }
        return z2;
    }

    @Override // android.view.View
    public final void layout(int i, int i2, int i3, int i4) {
        if (!this.mSuppressLayout && (this.mTransition == null || !this.mTransition.isChangingLayout())) {
            if (this.mTransition != null) {
                this.mTransition.layoutChange(this);
            }
            super.layout(i, i2, i3, i4);
            return;
        }
        this.mLayoutCalledWhileSuppressed = true;
    }

    protected boolean canAnimate() {
        return this.mLayoutAnimationController != null;
    }

    public void startLayoutAnimation() {
        if (this.mLayoutAnimationController != null) {
            this.mGroupFlags |= 8;
            requestLayout();
        }
    }

    public void scheduleLayoutAnimation() {
        this.mGroupFlags |= 8;
    }

    public void setLayoutAnimation(android.view.animation.LayoutAnimationController layoutAnimationController) {
        this.mLayoutAnimationController = layoutAnimationController;
        if (this.mLayoutAnimationController != null) {
            this.mGroupFlags |= 8;
        }
    }

    public android.view.animation.LayoutAnimationController getLayoutAnimation() {
        return this.mLayoutAnimationController;
    }

    @java.lang.Deprecated
    public boolean isAnimationCacheEnabled() {
        return (this.mGroupFlags & 64) == 64;
    }

    @java.lang.Deprecated
    public void setAnimationCacheEnabled(boolean z) {
        setBooleanFlag(64, z);
    }

    @java.lang.Deprecated
    public boolean isAlwaysDrawnWithCacheEnabled() {
        return (this.mGroupFlags & 16384) == 16384;
    }

    @java.lang.Deprecated
    public void setAlwaysDrawnWithCacheEnabled(boolean z) {
        setBooleanFlag(16384, z);
    }

    @java.lang.Deprecated
    protected boolean isChildrenDrawnWithCacheEnabled() {
        return (this.mGroupFlags & 32768) == 32768;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @java.lang.Deprecated
    public void setChildrenDrawnWithCacheEnabled(boolean z) {
        setBooleanFlag(32768, z);
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    protected boolean isChildrenDrawingOrderEnabled() {
        return (this.mGroupFlags & 1024) == 1024;
    }

    protected void setChildrenDrawingOrderEnabled(boolean z) {
        setBooleanFlag(1024, z);
    }

    private boolean hasBooleanFlag(int i) {
        return (this.mGroupFlags & i) == i;
    }

    private void setBooleanFlag(int i, boolean z) {
        if (z) {
            this.mGroupFlags = i | this.mGroupFlags;
        } else {
            this.mGroupFlags = (~i) & this.mGroupFlags;
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing", mapping = {@android.view.ViewDebug.IntToString(from = 0, to = android.security.keystore.KeyProperties.DIGEST_NONE), @android.view.ViewDebug.IntToString(from = 1, to = "ANIMATION"), @android.view.ViewDebug.IntToString(from = 2, to = "SCROLLING"), @android.view.ViewDebug.IntToString(from = 3, to = "ALL")})
    @java.lang.Deprecated
    public int getPersistentDrawingCache() {
        return this.mPersistentDrawingCache;
    }

    @java.lang.Deprecated
    public void setPersistentDrawingCache(int i) {
        this.mPersistentDrawingCache = i & 3;
    }

    private void setLayoutMode(int i, boolean z) {
        this.mLayoutMode = i;
        setBooleanFlag(8388608, z);
    }

    @Override // android.view.View
    void invalidateInheritedLayoutMode(int i) {
        if (this.mLayoutMode == -1 || this.mLayoutMode == i || hasBooleanFlag(8388608)) {
            return;
        }
        setLayoutMode(-1, false);
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            getChildAt(i2).invalidateInheritedLayoutMode(i);
        }
    }

    public int getLayoutMode() {
        if (this.mLayoutMode == -1) {
            setLayoutMode(this.mParent instanceof android.view.ViewGroup ? ((android.view.ViewGroup) this.mParent).getLayoutMode() : LAYOUT_MODE_DEFAULT, false);
        }
        return this.mLayoutMode;
    }

    public void setLayoutMode(int i) {
        if (this.mLayoutMode != i) {
            invalidateInheritedLayoutMode(i);
            setLayoutMode(i, i != -1);
            requestLayout();
        }
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.view.ViewGroup.LayoutParams(getContext(), attributeSet);
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams;
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new android.view.ViewGroup.LayoutParams(-2, -2);
    }

    @Override // android.view.View
    protected void debug(int i) {
        super.debug(i);
        if (this.mFocused != null) {
            android.util.Log.d("View", debugIndent(i) + "mFocused");
            this.mFocused.debug(i + 1);
        }
        if (this.mDefaultFocus != null) {
            android.util.Log.d("View", debugIndent(i) + "mDefaultFocus");
            this.mDefaultFocus.debug(i + 1);
        }
        if (this.mFocusedInCluster != null) {
            android.util.Log.d("View", debugIndent(i) + "mFocusedInCluster");
            this.mFocusedInCluster.debug(i + 1);
        }
        if (this.mChildrenCount != 0) {
            android.util.Log.d("View", debugIndent(i) + "{");
        }
        int i2 = this.mChildrenCount;
        for (int i3 = 0; i3 < i2; i3++) {
            this.mChildren[i3].debug(i + 1);
        }
        if (this.mChildrenCount != 0) {
            android.util.Log.d("View", debugIndent(i) + "}");
        }
    }

    public int indexOfChild(android.view.View view) {
        int i = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (viewArr[i2] == view) {
                return i2;
            }
        }
        return -1;
    }

    public int getChildCount() {
        return this.mChildrenCount;
    }

    public android.view.View getChildAt(int i) {
        if (i < 0 || i >= this.mChildrenCount) {
            return null;
        }
        return this.mChildren[i];
    }

    protected void measureChildren(int i, int i2) {
        int i3 = this.mChildrenCount;
        android.view.View[] viewArr = this.mChildren;
        for (int i4 = 0; i4 < i3; i4++) {
            android.view.View view = viewArr[i4];
            if ((view.mViewFlags & 12) != 8) {
                measureChild(view, i, i2);
            }
        }
    }

    protected void measureChild(android.view.View view, int i, int i2) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight, layoutParams.width), getChildMeasureSpec(i2, this.mPaddingTop + this.mPaddingBottom, layoutParams.height));
    }

    protected void measureChildWithMargins(android.view.View view, int i, int i2, int i3, int i4) {
        android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), getChildMeasureSpec(i3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getChildMeasureSpec(int i, int i2, int i3) {
        int mode = android.view.View.MeasureSpec.getMode(i);
        int size = android.view.View.MeasureSpec.getSize(i) - i2;
        int i4 = 0;
        int max = java.lang.Math.max(0, size);
        switch (mode) {
            case Integer.MIN_VALUE:
                if (i3 >= 0) {
                    i4 = 1073741824;
                    break;
                } else if (i3 == -1) {
                    i3 = max;
                    i4 = Integer.MIN_VALUE;
                    break;
                } else {
                    if (i3 == -2) {
                        i3 = max;
                        i4 = Integer.MIN_VALUE;
                        break;
                    }
                    i3 = 0;
                    break;
                }
            case 0:
                if (i3 >= 0) {
                    i4 = 1073741824;
                    break;
                } else if (i3 == -1) {
                    if (!android.view.View.sUseZeroUnspecifiedMeasureSpec) {
                        i3 = max;
                        break;
                    } else {
                        i3 = 0;
                        break;
                    }
                } else {
                    if (i3 == -2) {
                        if (!android.view.View.sUseZeroUnspecifiedMeasureSpec) {
                            i3 = max;
                            break;
                        } else {
                            i3 = 0;
                            break;
                        }
                    }
                    i3 = 0;
                    break;
                }
            case 1073741824:
                if (i3 >= 0) {
                    i4 = 1073741824;
                    break;
                } else if (i3 == -1) {
                    i3 = max;
                    i4 = 1073741824;
                    break;
                } else {
                    if (i3 == -2) {
                        i3 = max;
                        i4 = Integer.MIN_VALUE;
                        break;
                    }
                    i3 = 0;
                    break;
                }
            default:
                i3 = 0;
                break;
        }
        return android.view.View.MeasureSpec.makeMeasureSpec(i3, i4);
    }

    public void clearDisappearingChildren() {
        java.util.ArrayList<android.view.View> arrayList = this.mDisappearingChildren;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                android.view.View view = arrayList.get(i);
                if (view.mAttachInfo != null) {
                    view.dispatchDetachedFromWindow();
                }
                view.clearAnimation();
            }
            arrayList.clear();
            invalidate();
        }
    }

    private void addDisappearingView(android.view.View view) {
        java.util.ArrayList<android.view.View> arrayList = this.mDisappearingChildren;
        if (arrayList == null) {
            arrayList = new java.util.ArrayList<>();
            this.mDisappearingChildren = arrayList;
        }
        arrayList.add(view);
    }

    void finishAnimatingView(android.view.View view, android.view.animation.Animation animation) {
        java.util.ArrayList<android.view.View> arrayList = this.mDisappearingChildren;
        if (arrayList != null && arrayList.contains(view)) {
            arrayList.remove(view);
            if (view.mAttachInfo != null) {
                view.dispatchDetachedFromWindow();
            }
            view.clearAnimation();
            this.mGroupFlags |= 4;
        }
        if (animation != null && !animation.getFillAfter()) {
            view.clearAnimation();
        }
        if ((view.mPrivateFlags & 65536) == 65536) {
            view.onAnimationEnd();
            view.mPrivateFlags &= -65537;
            this.mGroupFlags |= 4;
        }
    }

    boolean isViewTransitioning(android.view.View view) {
        return this.mTransitioningViews != null && this.mTransitioningViews.contains(view);
    }

    public void startViewTransition(android.view.View view) {
        if (view.mParent == this) {
            if (this.mTransitioningViews == null) {
                this.mTransitioningViews = new java.util.ArrayList<>();
            }
            this.mTransitioningViews.add(view);
        }
    }

    public void endViewTransition(android.view.View view) {
        if (this.mTransitioningViews != null) {
            this.mTransitioningViews.remove(view);
            java.util.ArrayList<android.view.View> arrayList = this.mDisappearingChildren;
            if (arrayList != null && arrayList.contains(view)) {
                arrayList.remove(view);
                if (this.mVisibilityChangingChildren != null && this.mVisibilityChangingChildren.contains(view)) {
                    this.mVisibilityChangingChildren.remove(view);
                } else {
                    if (view.mAttachInfo != null) {
                        view.dispatchDetachedFromWindow();
                    }
                    if (view.mParent != null) {
                        view.mParent = null;
                    }
                }
                invalidate();
            }
        }
    }

    public void suppressLayout(boolean z) {
        this.mSuppressLayout = z;
        if (!z && this.mLayoutCalledWhileSuppressed) {
            requestLayout();
            this.mLayoutCalledWhileSuppressed = false;
        }
    }

    public boolean isLayoutSuppressed() {
        return this.mSuppressLayout;
    }

    @Override // android.view.View
    public boolean gatherTransparentRegion(android.graphics.Region region) {
        boolean z;
        boolean z2 = (this.mPrivateFlags & 512) == 0;
        if (z2 && region == null) {
            return true;
        }
        super.gatherTransparentRegion(region);
        int i = this.mChildrenCount;
        if (i <= 0) {
            z = true;
        } else {
            java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
            boolean z3 = buildOrderedChildList == null && isChildrenDrawingOrderEnabled();
            android.view.View[] viewArr = this.mChildren;
            z = true;
            for (int i2 = 0; i2 < i; i2++) {
                android.view.View andVerifyPreorderedView = getAndVerifyPreorderedView(buildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z3));
                if (((andVerifyPreorderedView.mViewFlags & 12) == 0 || andVerifyPreorderedView.getAnimation() != null) && !andVerifyPreorderedView.gatherTransparentRegion(region)) {
                    z = false;
                }
            }
            if (buildOrderedChildList != null) {
                buildOrderedChildList.clear();
            }
        }
        return z2 || z;
    }

    @Override // android.view.ViewParent
    public void requestTransparentRegion(android.view.View view) {
        if (view != null) {
            view.mPrivateFlags |= 512;
            if (this.mParent != null) {
                this.mParent.requestTransparentRegion(this);
            }
        }
    }

    @Override // android.view.ViewParent
    public void subtractObscuredTouchableRegion(android.graphics.Region region, android.view.View view) {
        int i = this.mChildrenCount;
        java.util.ArrayList<android.view.View> buildTouchDispatchChildList = buildTouchDispatchChildList();
        boolean z = buildTouchDispatchChildList == null && isChildrenDrawingOrderEnabled();
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            android.view.View andVerifyPreorderedView = getAndVerifyPreorderedView(buildTouchDispatchChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z));
            if (andVerifyPreorderedView == view) {
                break;
            }
            if (andVerifyPreorderedView.canReceivePointerEvents()) {
                applyOpToRegionByBounds(region, andVerifyPreorderedView, android.graphics.Region.Op.DIFFERENCE);
            }
        }
        applyOpToRegionByBounds(region, this, android.graphics.Region.Op.INTERSECT);
        android.view.ViewParent parent = getParent();
        if (parent != null) {
            parent.subtractObscuredTouchableRegion(region, this);
        }
    }

    @Override // android.view.ViewParent
    public boolean getChildLocalHitRegion(android.view.View view, android.graphics.Region region, android.graphics.Matrix matrix, boolean z) {
        if (!view.hasIdentityMatrix()) {
            matrix.preConcat(view.getInverseMatrix());
        }
        matrix.preTranslate(-(view.mLeft - this.mScrollX), -(view.mTop - this.mScrollY));
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        android.graphics.RectF rectF = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformRect : new android.graphics.RectF();
        rectF.set(0.0f, 0.0f, i, i2);
        matrix.mapRect(rectF);
        boolean op = region.op(java.lang.Math.round(rectF.left), java.lang.Math.round(rectF.top), java.lang.Math.round(rectF.right), java.lang.Math.round(rectF.bottom), android.graphics.Region.Op.INTERSECT);
        boolean z2 = true;
        if (z) {
            android.view.ViewGroup.HoverTarget hoverTarget = this.mFirstHoverTarget;
            while (true) {
                if (hoverTarget == null) {
                    z2 = false;
                    break;
                }
                android.view.ViewGroup.HoverTarget hoverTarget2 = hoverTarget.next;
                if (hoverTarget.child == view) {
                    break;
                }
                hoverTarget = hoverTarget2;
            }
            if (!z2 && this.mFirstHoverTarget != null) {
                android.view.ViewGroup.HoverTarget hoverTarget3 = this.mFirstHoverTarget;
                java.util.ArrayList<android.view.View> buildTouchDispatchChildList = buildTouchDispatchChildList();
                while (op && hoverTarget3 != null) {
                    android.view.ViewGroup.HoverTarget hoverTarget4 = hoverTarget3.next;
                    if (!isOnTop(view, hoverTarget3.child, buildTouchDispatchChildList)) {
                        rectF.set(r1.mLeft, r1.mTop, r1.mRight, r1.mBottom);
                        matrix.mapRect(rectF);
                        op = region.op(java.lang.Math.round(rectF.left), java.lang.Math.round(rectF.top), java.lang.Math.round(rectF.right), java.lang.Math.round(rectF.bottom), android.graphics.Region.Op.DIFFERENCE);
                    }
                    hoverTarget3 = hoverTarget4;
                }
                if (buildTouchDispatchChildList != null) {
                    buildTouchDispatchChildList.clear();
                }
            }
        } else {
            android.view.ViewGroup.TouchTarget touchTarget = this.mFirstTouchTarget;
            while (true) {
                if (touchTarget == null) {
                    z2 = false;
                    break;
                }
                android.view.ViewGroup.TouchTarget touchTarget2 = touchTarget.next;
                if (touchTarget.child == view) {
                    break;
                }
                touchTarget = touchTarget2;
            }
            if (!z2 && this.mFirstTouchTarget != null) {
                android.view.ViewGroup.TouchTarget touchTarget3 = this.mFirstTouchTarget;
                java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
                while (op && touchTarget3 != null) {
                    android.view.ViewGroup.TouchTarget touchTarget4 = touchTarget3.next;
                    if (!isOnTop(view, touchTarget3.child, buildOrderedChildList)) {
                        rectF.set(r1.mLeft, r1.mTop, r1.mRight, r1.mBottom);
                        matrix.mapRect(rectF);
                        op = region.op(java.lang.Math.round(rectF.left), java.lang.Math.round(rectF.top), java.lang.Math.round(rectF.right), java.lang.Math.round(rectF.bottom), android.graphics.Region.Op.DIFFERENCE);
                    }
                    touchTarget3 = touchTarget4;
                }
                if (buildOrderedChildList != null) {
                    buildOrderedChildList.clear();
                }
            }
        }
        if (op && this.mParent != null) {
            return this.mParent.getChildLocalHitRegion(this, region, matrix, z);
        }
        return op;
    }

    private boolean isOnTop(android.view.View view, android.view.View view2, java.util.ArrayList<android.view.View> arrayList) {
        int i = this.mChildrenCount;
        boolean z = arrayList == null && isChildrenDrawingOrderEnabled();
        android.view.View[] viewArr = this.mChildren;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            android.view.View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayList, viewArr, getAndVerifyPreorderedIndex(i, i2, z));
            if (andVerifyPreorderedView == view) {
                return true;
            }
            if (andVerifyPreorderedView == view2) {
                return false;
            }
        }
        return false;
    }

    private static void applyOpToRegionByBounds(android.graphics.Region region, android.view.View view, android.graphics.Region.Op op) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        region.op(i, i2, i + view.getWidth(), i2 + view.getHeight(), op);
    }

    @Override // android.view.View
    public android.view.WindowInsets dispatchApplyWindowInsets(android.view.WindowInsets windowInsets) {
        android.view.WindowInsets dispatchApplyWindowInsets = super.dispatchApplyWindowInsets(windowInsets);
        if (dispatchApplyWindowInsets.isConsumed()) {
            return dispatchApplyWindowInsets;
        }
        if (android.view.View.sBrokenInsetsDispatch) {
            return brokenDispatchApplyWindowInsets(dispatchApplyWindowInsets);
        }
        return newDispatchApplyWindowInsets(dispatchApplyWindowInsets);
    }

    private android.view.WindowInsets brokenDispatchApplyWindowInsets(android.view.WindowInsets windowInsets) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            windowInsets = getChildAt(i).dispatchApplyWindowInsets(windowInsets);
            if (windowInsets.isConsumed()) {
                break;
            }
        }
        return windowInsets;
    }

    private android.view.WindowInsets newDispatchApplyWindowInsets(android.view.WindowInsets windowInsets) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchApplyWindowInsets(windowInsets);
        }
        return windowInsets;
    }

    @Override // android.view.View
    public void setWindowInsetsAnimationCallback(android.view.WindowInsetsAnimation.Callback callback) {
        int i;
        super.setWindowInsetsAnimationCallback(callback);
        if (callback != null) {
            i = callback.getDispatchMode();
        } else {
            i = 1;
        }
        this.mInsetsAnimationDispatchMode = i;
    }

    @Override // android.view.View
    public boolean hasWindowInsetsAnimationCallback() {
        if (super.hasWindowInsetsAnimationCallback()) {
            return true;
        }
        if (((this.mViewFlags & 2048) != 0 || isFrameworkOptionalFitsSystemWindows()) && this.mAttachInfo != null && this.mAttachInfo.mContentOnApplyWindowInsetsListener != null && (getWindowSystemUiVisibility() & 1536) == 0) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).hasWindowInsetsAnimationCallback()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void dispatchWindowInsetsAnimationPrepare(android.view.WindowInsetsAnimation windowInsetsAnimation) {
        super.dispatchWindowInsetsAnimationPrepare(windowInsetsAnimation);
        if (((this.mViewFlags & 2048) != 0 || isFrameworkOptionalFitsSystemWindows()) && this.mAttachInfo != null && getListenerInfo().mWindowInsetsAnimationCallback == null && this.mAttachInfo.mContentOnApplyWindowInsetsListener != null && (getWindowSystemUiVisibility() & 1536) == 0) {
            this.mInsetsAnimationDispatchMode = 0;
        } else {
            if (this.mInsetsAnimationDispatchMode == 0) {
                return;
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).dispatchWindowInsetsAnimationPrepare(windowInsetsAnimation);
            }
        }
    }

    @Override // android.view.View
    public android.view.WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(android.view.WindowInsetsAnimation windowInsetsAnimation, android.view.WindowInsetsAnimation.Bounds bounds) {
        android.view.WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart = super.dispatchWindowInsetsAnimationStart(windowInsetsAnimation, bounds);
        if (this.mInsetsAnimationDispatchMode == 0) {
            return dispatchWindowInsetsAnimationStart;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchWindowInsetsAnimationStart(windowInsetsAnimation, dispatchWindowInsetsAnimationStart);
        }
        return dispatchWindowInsetsAnimationStart;
    }

    @Override // android.view.View
    public android.view.WindowInsets dispatchWindowInsetsAnimationProgress(android.view.WindowInsets windowInsets, java.util.List<android.view.WindowInsetsAnimation> list) {
        android.view.WindowInsets dispatchWindowInsetsAnimationProgress = super.dispatchWindowInsetsAnimationProgress(windowInsets, list);
        if (this.mInsetsAnimationDispatchMode == 0) {
            return dispatchWindowInsetsAnimationProgress;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchWindowInsetsAnimationProgress(dispatchWindowInsetsAnimationProgress, list);
        }
        return dispatchWindowInsetsAnimationProgress;
    }

    @Override // android.view.View
    public void dispatchWindowInsetsAnimationEnd(android.view.WindowInsetsAnimation windowInsetsAnimation) {
        super.dispatchWindowInsetsAnimationEnd(windowInsetsAnimation);
        if (this.mInsetsAnimationDispatchMode == 0) {
            return;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchWindowInsetsAnimationEnd(windowInsetsAnimation);
        }
    }

    @Override // android.view.View
    public void dispatchScrollCaptureSearch(android.graphics.Rect rect, android.graphics.Point point, java.util.function.Consumer<android.view.ScrollCaptureTarget> consumer) {
        boolean z;
        if (getClipToPadding() && !rect.intersect(this.mPaddingLeft, this.mPaddingTop, (this.mRight - this.mLeft) - this.mPaddingRight, (this.mBottom - this.mTop) - this.mPaddingBottom)) {
            return;
        }
        super.dispatchScrollCaptureSearch(rect, point, consumer);
        if ((getScrollCaptureHint() & 4) != 0) {
            return;
        }
        android.graphics.Rect tempRect = getTempRect();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                tempRect.set(rect);
                android.graphics.Point tempPoint = getTempPoint();
                tempPoint.set(point.x, point.y);
                int i2 = childAt.mLeft - this.mScrollX;
                int i3 = childAt.mTop - this.mScrollY;
                tempRect.offset(-i2, -i3);
                tempPoint.offset(i2, i3);
                if (getClipChildren()) {
                    z = tempRect.intersect(0, 0, childAt.getWidth(), childAt.getHeight());
                } else {
                    z = true;
                }
                if (z) {
                    childAt.dispatchScrollCaptureSearch(tempRect, tempPoint, consumer);
                }
            }
        }
    }

    public android.view.animation.Animation.AnimationListener getLayoutAnimationListener() {
        return this.mAnimationListener;
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if ((this.mGroupFlags & 65536) != 0) {
            if ((this.mGroupFlags & 8192) != 0) {
                throw new java.lang.IllegalStateException("addStateFromChildren cannot be enabled if a child has duplicateParentState set to true");
            }
            android.view.View[] viewArr = this.mChildren;
            int i = this.mChildrenCount;
            for (int i2 = 0; i2 < i; i2++) {
                android.view.View view = viewArr[i2];
                if ((view.mViewFlags & 4194304) != 0) {
                    view.refreshDrawableState();
                }
            }
        }
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        android.view.View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].jumpDrawablesToCurrentState();
        }
    }

    @Override // android.view.View
    protected int[] onCreateDrawableState(int i) {
        if ((this.mGroupFlags & 8192) == 0) {
            return super.onCreateDrawableState(i);
        }
        int childCount = getChildCount();
        int i2 = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            int[] drawableState = getChildAt(i3).getDrawableState();
            if (drawableState != null) {
                i2 += drawableState.length;
            }
        }
        int[] onCreateDrawableState = super.onCreateDrawableState(i + i2);
        for (int i4 = 0; i4 < childCount; i4++) {
            int[] drawableState2 = getChildAt(i4).getDrawableState();
            if (drawableState2 != null) {
                onCreateDrawableState = mergeDrawableStates(onCreateDrawableState, drawableState2);
            }
        }
        return onCreateDrawableState;
    }

    public void setAddStatesFromChildren(boolean z) {
        if (z) {
            this.mGroupFlags |= 8192;
        } else {
            this.mGroupFlags &= -8193;
        }
        refreshDrawableState();
    }

    public boolean addStatesFromChildren() {
        return (this.mGroupFlags & 8192) != 0;
    }

    @Override // android.view.ViewParent
    public void childDrawableStateChanged(android.view.View view) {
        if ((this.mGroupFlags & 8192) != 0) {
            refreshDrawableState();
        }
    }

    public void setLayoutAnimationListener(android.view.animation.Animation.AnimationListener animationListener) {
        this.mAnimationListener = animationListener;
    }

    public void requestTransitionStart(android.animation.LayoutTransition layoutTransition) {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestTransitionStart(layoutTransition);
        }
    }

    @Override // android.view.View
    public boolean resolveRtlPropertiesIfNeeded() {
        boolean resolveRtlPropertiesIfNeeded = super.resolveRtlPropertiesIfNeeded();
        if (resolveRtlPropertiesIfNeeded) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = getChildAt(i);
                if (childAt.isLayoutDirectionInherited()) {
                    childAt.resolveRtlPropertiesIfNeeded();
                }
            }
        }
        return resolveRtlPropertiesIfNeeded;
    }

    @Override // android.view.View
    public boolean resolveLayoutDirection() {
        boolean resolveLayoutDirection = super.resolveLayoutDirection();
        if (resolveLayoutDirection) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = getChildAt(i);
                if (childAt.isLayoutDirectionInherited()) {
                    childAt.resolveLayoutDirection();
                }
            }
        }
        return resolveLayoutDirection;
    }

    @Override // android.view.View
    public boolean resolveTextDirection() {
        boolean resolveTextDirection = super.resolveTextDirection();
        if (resolveTextDirection) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = getChildAt(i);
                if (childAt.isTextDirectionInherited()) {
                    childAt.resolveTextDirection();
                }
            }
        }
        return resolveTextDirection;
    }

    @Override // android.view.View
    public boolean resolveTextAlignment() {
        boolean resolveTextAlignment = super.resolveTextAlignment();
        if (resolveTextAlignment) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = getChildAt(i);
                if (childAt.isTextAlignmentInherited()) {
                    childAt.resolveTextAlignment();
                }
            }
        }
        return resolveTextAlignment;
    }

    @Override // android.view.View
    public void resolvePadding() {
        super.resolvePadding();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited() && !childAt.isPaddingResolved()) {
                childAt.resolvePadding();
            }
        }
    }

    @Override // android.view.View
    protected void resolveDrawables() {
        super.resolveDrawables();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited() && !childAt.areDrawablesResolved()) {
                childAt.resolveDrawables();
            }
        }
    }

    @Override // android.view.View
    public void resolveLayoutParams() {
        super.resolveLayoutParams();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).resolveLayoutParams();
        }
    }

    @Override // android.view.View
    public void resetResolvedLayoutDirection() {
        super.resetResolvedLayoutDirection();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited()) {
                childAt.resetResolvedLayoutDirection();
            }
        }
    }

    @Override // android.view.View
    public void resetResolvedTextDirection() {
        super.resetResolvedTextDirection();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.isTextDirectionInherited()) {
                childAt.resetResolvedTextDirection();
            }
        }
    }

    @Override // android.view.View
    public void resetResolvedTextAlignment() {
        super.resetResolvedTextAlignment();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.isTextAlignmentInherited()) {
                childAt.resetResolvedTextAlignment();
            }
        }
    }

    @Override // android.view.View
    public void resetResolvedPadding() {
        super.resetResolvedPadding();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited()) {
                childAt.resetResolvedPadding();
            }
        }
    }

    @Override // android.view.View
    protected void resetResolvedDrawables() {
        super.resetResolvedDrawables();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited()) {
                childAt.resetResolvedDrawables();
            }
        }
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean onStartNestedScroll(android.view.View view, android.view.View view2, int i) {
        return false;
    }

    @Override // android.view.ViewParent
    public void onNestedScrollAccepted(android.view.View view, android.view.View view2, int i) {
        this.mNestedScrollAxes = i;
    }

    @Override // android.view.ViewParent
    public void onStopNestedScroll(android.view.View view) {
        stopNestedScroll();
        this.mNestedScrollAxes = 0;
    }

    @Override // android.view.ViewParent
    public void onNestedScroll(android.view.View view, int i, int i2, int i3, int i4) {
        dispatchNestedScroll(i, i2, i3, i4, null);
    }

    @Override // android.view.ViewParent
    public void onNestedPreScroll(android.view.View view, int i, int i2, int[] iArr) {
        dispatchNestedPreScroll(i, i2, iArr, null);
    }

    @Override // android.view.ViewParent
    public boolean onNestedFling(android.view.View view, float f, float f2, boolean z) {
        return dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.ViewParent
    public boolean onNestedPreFling(android.view.View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollAxes;
    }

    protected void onSetLayoutParams(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        requestLayout();
    }

    @Override // android.view.View
    public void captureTransitioningViews(java.util.List<android.view.View> list) {
        if (getVisibility() != 0) {
            return;
        }
        if (isTransitionGroup()) {
            list.add(this);
            return;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).captureTransitioningViews(list);
        }
    }

    @Override // android.view.View
    public void findNamedViews(java.util.Map<java.lang.String, android.view.View> map) {
        if (getVisibility() != 0 && this.mGhostView == null) {
            return;
        }
        super.findNamedViews(map);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).findNamedViews(map);
        }
    }

    @Override // android.view.View
    boolean hasUnhandledKeyListener() {
        return this.mChildUnhandledKeyListeners > 0 || super.hasUnhandledKeyListener();
    }

    void incrementChildUnhandledKeyListeners() {
        this.mChildUnhandledKeyListeners++;
        if (this.mChildUnhandledKeyListeners == 1 && (this.mParent instanceof android.view.ViewGroup)) {
            ((android.view.ViewGroup) this.mParent).incrementChildUnhandledKeyListeners();
        }
    }

    void decrementChildUnhandledKeyListeners() {
        this.mChildUnhandledKeyListeners--;
        if (this.mChildUnhandledKeyListeners == 0 && (this.mParent instanceof android.view.ViewGroup)) {
            ((android.view.ViewGroup) this.mParent).decrementChildUnhandledKeyListeners();
        }
    }

    @Override // android.view.View
    android.view.View dispatchUnhandledKeyEvent(android.view.KeyEvent keyEvent) {
        if (!hasUnhandledKeyListener()) {
            return null;
        }
        java.util.ArrayList<android.view.View> buildOrderedChildList = buildOrderedChildList();
        if (buildOrderedChildList != null) {
            try {
                for (int size = buildOrderedChildList.size() - 1; size >= 0; size--) {
                    android.view.View dispatchUnhandledKeyEvent = buildOrderedChildList.get(size).dispatchUnhandledKeyEvent(keyEvent);
                    if (dispatchUnhandledKeyEvent != null) {
                        return dispatchUnhandledKeyEvent;
                    }
                }
            } finally {
                buildOrderedChildList.clear();
            }
        } else {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                android.view.View dispatchUnhandledKeyEvent2 = getChildAt(childCount).dispatchUnhandledKeyEvent(keyEvent);
                if (dispatchUnhandledKeyEvent2 != null) {
                    return dispatchUnhandledKeyEvent2;
                }
            }
        }
        if (onUnhandledKeyEvent(keyEvent)) {
            return this;
        }
        return null;
    }

    private static final class TouchTarget {
        public static final int ALL_POINTER_IDS = -1;
        private static final int MAX_RECYCLED = 32;
        private static android.view.ViewGroup.TouchTarget sRecycleBin;
        private static final java.lang.Object sRecycleLock = new java.lang.Object[0];
        private static int sRecycledCount;
        public android.view.View child;
        public android.view.ViewGroup.TouchTarget next;
        public int pointerIdBits;

        private TouchTarget() {
        }

        public static android.view.ViewGroup.TouchTarget obtain(android.view.View view, int i) {
            android.view.ViewGroup.TouchTarget touchTarget;
            if (view == null) {
                throw new java.lang.IllegalArgumentException("child must be non-null");
            }
            synchronized (sRecycleLock) {
                if (sRecycleBin == null) {
                    touchTarget = new android.view.ViewGroup.TouchTarget();
                } else {
                    touchTarget = sRecycleBin;
                    sRecycleBin = touchTarget.next;
                    sRecycledCount--;
                    touchTarget.next = null;
                }
            }
            touchTarget.child = view;
            touchTarget.pointerIdBits = i;
            return touchTarget;
        }

        public void recycle() {
            if (this.child == null) {
                throw new java.lang.IllegalStateException("already recycled once");
            }
            synchronized (sRecycleLock) {
                if (sRecycledCount < 32) {
                    this.next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount++;
                } else {
                    this.next = null;
                }
                this.child = null;
            }
        }
    }

    private static final class HoverTarget {
        private static final int MAX_RECYCLED = 32;
        private static android.view.ViewGroup.HoverTarget sRecycleBin;
        private static final java.lang.Object sRecycleLock = new java.lang.Object[0];
        private static int sRecycledCount;
        public android.view.View child;
        public android.view.ViewGroup.HoverTarget next;

        private HoverTarget() {
        }

        public static android.view.ViewGroup.HoverTarget obtain(android.view.View view) {
            android.view.ViewGroup.HoverTarget hoverTarget;
            if (view == null) {
                throw new java.lang.IllegalArgumentException("child must be non-null");
            }
            synchronized (sRecycleLock) {
                if (sRecycleBin == null) {
                    hoverTarget = new android.view.ViewGroup.HoverTarget();
                } else {
                    hoverTarget = sRecycleBin;
                    sRecycleBin = hoverTarget.next;
                    sRecycledCount--;
                    hoverTarget.next = null;
                }
            }
            hoverTarget.child = view;
            return hoverTarget;
        }

        public void recycle() {
            if (this.child == null) {
                throw new java.lang.IllegalStateException("already recycled once");
            }
            synchronized (sRecycleLock) {
                if (sRecycledCount < 32) {
                    this.next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount++;
                } else {
                    this.next = null;
                }
                this.child = null;
            }
        }
    }

    private static class ChildListForAutoFillOrContentCapture extends java.util.ArrayList<android.view.View> {
        private static final int MAX_POOL_SIZE = 32;
        private static final android.util.Pools.SimplePool<android.view.ViewGroup.ChildListForAutoFillOrContentCapture> sPool = new android.util.Pools.SimplePool<>(32);

        private ChildListForAutoFillOrContentCapture() {
        }

        public static android.view.ViewGroup.ChildListForAutoFillOrContentCapture obtain() {
            android.view.ViewGroup.ChildListForAutoFillOrContentCapture acquire = sPool.acquire();
            if (acquire == null) {
                return new android.view.ViewGroup.ChildListForAutoFillOrContentCapture();
            }
            return acquire;
        }

        public void recycle() {
            clear();
            sPool.release(this);
        }
    }

    static class ChildListForAccessibility {
        private static final int MAX_POOL_SIZE = 32;
        private static final android.util.Pools.SynchronizedPool<android.view.ViewGroup.ChildListForAccessibility> sPool = new android.util.Pools.SynchronizedPool<>(32);
        private final java.util.ArrayList<android.view.View> mChildren = new java.util.ArrayList<>();
        private final java.util.ArrayList<android.view.ViewGroup.ViewLocationHolder> mHolders = new java.util.ArrayList<>();

        ChildListForAccessibility() {
        }

        public static android.view.ViewGroup.ChildListForAccessibility obtain(android.view.ViewGroup viewGroup, boolean z) {
            android.view.ViewGroup.ChildListForAccessibility acquire = sPool.acquire();
            if (acquire == null) {
                acquire = new android.view.ViewGroup.ChildListForAccessibility();
            }
            acquire.init(viewGroup, z);
            return acquire;
        }

        public void recycle() {
            clear();
            sPool.release(this);
        }

        public int getChildCount() {
            return this.mChildren.size();
        }

        public android.view.View getChildAt(int i) {
            return this.mChildren.get(i);
        }

        private void init(android.view.ViewGroup viewGroup, boolean z) {
            java.util.ArrayList<android.view.View> arrayList = this.mChildren;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                arrayList.add(viewGroup.getChildAt(i));
            }
            if (z) {
                java.util.ArrayList<android.view.ViewGroup.ViewLocationHolder> arrayList2 = this.mHolders;
                for (int i2 = 0; i2 < childCount; i2++) {
                    arrayList2.add(android.view.ViewGroup.ViewLocationHolder.obtain(viewGroup, arrayList.get(i2)));
                }
                sort(arrayList2);
                for (int i3 = 0; i3 < childCount; i3++) {
                    android.view.ViewGroup.ViewLocationHolder viewLocationHolder = arrayList2.get(i3);
                    arrayList.set(i3, viewLocationHolder.mView);
                    viewLocationHolder.recycle();
                }
                arrayList2.clear();
            }
        }

        private void sort(java.util.ArrayList<android.view.ViewGroup.ViewLocationHolder> arrayList) {
            try {
                android.view.ViewGroup.ViewLocationHolder.setComparisonStrategy(1);
                java.util.Collections.sort(arrayList);
            } catch (java.lang.IllegalArgumentException e) {
                android.view.ViewGroup.ViewLocationHolder.setComparisonStrategy(2);
                java.util.Collections.sort(arrayList);
            }
        }

        private void clear() {
            this.mChildren.clear();
        }
    }

    static class ViewLocationHolder implements java.lang.Comparable<android.view.ViewGroup.ViewLocationHolder> {
        public static final int COMPARISON_STRATEGY_LOCATION = 2;
        public static final int COMPARISON_STRATEGY_STRIPE = 1;
        private static final int MAX_POOL_SIZE = 32;
        private int mLayoutDirection;
        private final android.graphics.Rect mLocation = new android.graphics.Rect();
        private android.view.ViewGroup mRoot;
        public android.view.View mView;
        private static final android.util.Pools.SynchronizedPool<android.view.ViewGroup.ViewLocationHolder> sPool = new android.util.Pools.SynchronizedPool<>(32);
        private static int sComparisonStrategy = 1;

        ViewLocationHolder() {
        }

        public static android.view.ViewGroup.ViewLocationHolder obtain(android.view.ViewGroup viewGroup, android.view.View view) {
            android.view.ViewGroup.ViewLocationHolder acquire = sPool.acquire();
            if (acquire == null) {
                acquire = new android.view.ViewGroup.ViewLocationHolder();
            }
            acquire.init(viewGroup, view);
            return acquire;
        }

        public static void setComparisonStrategy(int i) {
            sComparisonStrategy = i;
        }

        public void recycle() {
            clear();
            sPool.release(this);
        }

        @Override // java.lang.Comparable
        public int compareTo(android.view.ViewGroup.ViewLocationHolder viewLocationHolder) {
            if (viewLocationHolder == null) {
                return 1;
            }
            int compareBoundsOfTree = compareBoundsOfTree(this, viewLocationHolder);
            if (compareBoundsOfTree != 0) {
                return compareBoundsOfTree;
            }
            return this.mView.getAccessibilityViewId() - viewLocationHolder.mView.getAccessibilityViewId();
        }

        private static int compareBoundsOfTree(android.view.ViewGroup.ViewLocationHolder viewLocationHolder, android.view.ViewGroup.ViewLocationHolder viewLocationHolder2) {
            if (sComparisonStrategy == 1) {
                if (viewLocationHolder.mLocation.bottom - viewLocationHolder2.mLocation.top <= 0) {
                    return -1;
                }
                if (viewLocationHolder.mLocation.top - viewLocationHolder2.mLocation.bottom >= 0) {
                    return 1;
                }
            }
            if (viewLocationHolder.mLayoutDirection == 0) {
                int i = viewLocationHolder.mLocation.left - viewLocationHolder2.mLocation.left;
                if (i != 0) {
                    return i;
                }
            } else {
                int i2 = viewLocationHolder.mLocation.right - viewLocationHolder2.mLocation.right;
                if (i2 != 0) {
                    return -i2;
                }
            }
            int i3 = viewLocationHolder.mLocation.top - viewLocationHolder2.mLocation.top;
            if (i3 != 0) {
                return i3;
            }
            int height = viewLocationHolder.mLocation.height() - viewLocationHolder2.mLocation.height();
            if (height != 0) {
                return -height;
            }
            int width = viewLocationHolder.mLocation.width() - viewLocationHolder2.mLocation.width();
            if (width != 0) {
                return -width;
            }
            final android.graphics.Rect rect = new android.graphics.Rect();
            final android.graphics.Rect rect2 = new android.graphics.Rect();
            final android.graphics.Rect rect3 = new android.graphics.Rect();
            viewLocationHolder.mView.getBoundsOnScreen(rect, true);
            viewLocationHolder2.mView.getBoundsOnScreen(rect2, true);
            android.view.View findViewByPredicateTraversal = viewLocationHolder.mView.findViewByPredicateTraversal(new java.util.function.Predicate() { // from class: android.view.ViewGroup$ViewLocationHolder$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.view.ViewGroup.ViewLocationHolder.lambda$compareBoundsOfTree$0(android.graphics.Rect.this, rect, (android.view.View) obj);
                }
            }, null);
            android.view.View findViewByPredicateTraversal2 = viewLocationHolder2.mView.findViewByPredicateTraversal(new java.util.function.Predicate() { // from class: android.view.ViewGroup$ViewLocationHolder$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.view.ViewGroup.ViewLocationHolder.lambda$compareBoundsOfTree$1(android.graphics.Rect.this, rect2, (android.view.View) obj);
                }
            }, null);
            if (findViewByPredicateTraversal != null && findViewByPredicateTraversal2 != null) {
                return compareBoundsOfTree(obtain(viewLocationHolder.mRoot, findViewByPredicateTraversal), obtain(viewLocationHolder.mRoot, findViewByPredicateTraversal2));
            }
            if (findViewByPredicateTraversal != null) {
                return 1;
            }
            return findViewByPredicateTraversal2 != null ? -1 : 0;
        }

        static /* synthetic */ boolean lambda$compareBoundsOfTree$0(android.graphics.Rect rect, android.graphics.Rect rect2, android.view.View view) {
            view.getBoundsOnScreen(rect, true);
            return !rect.equals(rect2);
        }

        static /* synthetic */ boolean lambda$compareBoundsOfTree$1(android.graphics.Rect rect, android.graphics.Rect rect2, android.view.View view) {
            view.getBoundsOnScreen(rect, true);
            return !rect.equals(rect2);
        }

        private void init(android.view.ViewGroup viewGroup, android.view.View view) {
            android.graphics.Rect rect = this.mLocation;
            view.getDrawingRect(rect);
            viewGroup.offsetDescendantRectToMyCoords(view, rect);
            this.mView = view;
            this.mRoot = viewGroup;
            this.mLayoutDirection = viewGroup.getLayoutDirection();
        }

        private void clear() {
            this.mView = null;
            this.mRoot = null;
            this.mLocation.set(0, 0, 0, 0);
        }
    }

    private static void drawRect(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4) {
        if (sDebugLines == null) {
            sDebugLines = new float[16];
        }
        float f = i;
        sDebugLines[0] = f;
        float f2 = i2;
        sDebugLines[1] = f2;
        float f3 = i3;
        sDebugLines[2] = f3;
        sDebugLines[3] = f2;
        sDebugLines[4] = f3;
        sDebugLines[5] = f2;
        sDebugLines[6] = f3;
        float f4 = i4;
        sDebugLines[7] = f4;
        sDebugLines[8] = f3;
        sDebugLines[9] = f4;
        sDebugLines[10] = f;
        sDebugLines[11] = f4;
        sDebugLines[12] = f;
        sDebugLines[13] = f4;
        sDebugLines[14] = f;
        sDebugLines[15] = f2;
        canvas.drawLines(sDebugLines, paint);
    }

    @Override // android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("focus:descendantFocusability", getDescendantFocusability());
        viewHierarchyEncoder.addProperty("drawing:clipChildren", getClipChildren());
        viewHierarchyEncoder.addProperty("drawing:clipToPadding", getClipToPadding());
        viewHierarchyEncoder.addProperty("drawing:childrenDrawingOrderEnabled", isChildrenDrawingOrderEnabled());
        viewHierarchyEncoder.addProperty("drawing:persistentDrawingCache", getPersistentDrawingCache());
        int childCount = getChildCount();
        viewHierarchyEncoder.addProperty("meta:__childCount__", (short) childCount);
        for (int i = 0; i < childCount; i++) {
            viewHierarchyEncoder.addPropertyKey("meta:__child__" + i);
            getChildAt(i).encode(viewHierarchyEncoder);
        }
    }

    @Override // android.view.ViewParent
    public final void onDescendantUnbufferedRequested() {
        int i;
        int i2 = 0;
        if (this.mFocused == null) {
            i = 0;
        } else {
            i = this.mFocused.mUnbufferedInputSource & (-3);
        }
        this.mUnbufferedInputSource = i;
        while (true) {
            if (i2 >= this.mChildrenCount) {
                break;
            }
            if ((this.mChildren[i2].mUnbufferedInputSource & 2) == 0) {
                i2++;
            } else {
                this.mUnbufferedInputSource |= 2;
                break;
            }
        }
        if (this.mParent != null) {
            this.mParent.onDescendantUnbufferedRequested();
        }
    }

    @Override // android.view.View
    public void dispatchCreateViewTranslationRequest(java.util.Map<android.view.autofill.AutofillId, long[]> map, int[] iArr, android.view.translation.TranslationCapability translationCapability, java.util.List<android.view.translation.ViewTranslationRequest> list) {
        super.dispatchCreateViewTranslationRequest(map, iArr, translationCapability, list);
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchCreateViewTranslationRequest(map, iArr, translationCapability, list);
        }
    }

    @Override // android.view.ViewParent
    public android.window.OnBackInvokedDispatcher findOnBackInvokedDispatcherForChild(android.view.View view, android.view.View view2) {
        android.view.ViewParent parent = getParent();
        if (parent != null) {
            return parent.findOnBackInvokedDispatcherForChild(this, view2);
        }
        return null;
    }
}
