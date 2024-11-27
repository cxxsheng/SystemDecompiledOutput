package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class PreferenceFrameLayout extends android.widget.FrameLayout {
    private static final int DEFAULT_BORDER_BOTTOM = 0;
    private static final int DEFAULT_BORDER_LEFT = 0;
    private static final int DEFAULT_BORDER_RIGHT = 0;
    private static final int DEFAULT_BORDER_TOP = 0;
    private final int mBorderBottom;
    private final int mBorderLeft;
    private final int mBorderRight;
    private final int mBorderTop;
    private boolean mPaddingApplied;

    public PreferenceFrameLayout(android.content.Context context) {
        this(context, null);
    }

    public PreferenceFrameLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, com.android.internal.R.attr.preferenceFrameLayoutStyle);
    }

    public PreferenceFrameLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PreferenceFrameLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.PreferenceFrameLayout, i, i2);
        int i3 = (int) ((context.getResources().getDisplayMetrics().density * 0.0f) + 0.5f);
        this.mBorderTop = obtainStyledAttributes.getDimensionPixelSize(3, i3);
        this.mBorderBottom = obtainStyledAttributes.getDimensionPixelSize(0, i3);
        this.mBorderLeft = obtainStyledAttributes.getDimensionPixelSize(1, i3);
        this.mBorderRight = obtainStyledAttributes.getDimensionPixelSize(2, i3);
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public android.preference.PreferenceFrameLayout.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.preference.PreferenceFrameLayout.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        android.preference.PreferenceFrameLayout.LayoutParams layoutParams = view.getLayoutParams() instanceof android.preference.PreferenceFrameLayout.LayoutParams ? (android.preference.PreferenceFrameLayout.LayoutParams) view.getLayoutParams() : null;
        if (layoutParams != null && layoutParams.removeBorders) {
            if (this.mPaddingApplied) {
                paddingTop -= this.mBorderTop;
                paddingBottom -= this.mBorderBottom;
                paddingLeft -= this.mBorderLeft;
                paddingRight -= this.mBorderRight;
                this.mPaddingApplied = false;
            }
        } else if (!this.mPaddingApplied) {
            paddingTop += this.mBorderTop;
            paddingBottom += this.mBorderBottom;
            paddingLeft += this.mBorderLeft;
            paddingRight += this.mBorderRight;
            this.mPaddingApplied = true;
        }
        int paddingTop2 = getPaddingTop();
        int paddingBottom2 = getPaddingBottom();
        int paddingLeft2 = getPaddingLeft();
        int paddingRight2 = getPaddingRight();
        if (paddingTop2 != paddingTop || paddingBottom2 != paddingBottom || paddingLeft2 != paddingLeft || paddingRight2 != paddingRight) {
            setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        super.addView(view);
    }

    public static class LayoutParams extends android.widget.FrameLayout.LayoutParams {
        public boolean removeBorders;

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.removeBorders = false;
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.PreferenceFrameLayout_Layout);
            this.removeBorders = obtainStyledAttributes.getBoolean(0, false);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.removeBorders = false;
        }
    }
}
