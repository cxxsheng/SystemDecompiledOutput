package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class FragmentBreadCrumbs extends android.view.ViewGroup implements android.app.FragmentManager.OnBackStackChangedListener {
    private static final int DEFAULT_GRAVITY = 8388627;
    android.app.Activity mActivity;
    android.widget.LinearLayout mContainer;
    private int mGravity;
    android.view.LayoutInflater mInflater;
    private int mLayoutResId;
    int mMaxVisible;
    private android.app.FragmentBreadCrumbs.OnBreadCrumbClickListener mOnBreadCrumbClickListener;
    private android.view.View.OnClickListener mOnClickListener;
    private android.view.View.OnClickListener mParentClickListener;
    android.app.BackStackRecord mParentEntry;
    private int mTextColor;
    android.app.BackStackRecord mTopEntry;

    @java.lang.Deprecated
    public interface OnBreadCrumbClickListener {
        boolean onBreadCrumbClick(android.app.FragmentManager.BackStackEntry backStackEntry, int i);
    }

    public FragmentBreadCrumbs(android.content.Context context) {
        this(context, null);
    }

    public FragmentBreadCrumbs(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, com.android.internal.R.attr.fragmentBreadCrumbsStyle);
    }

    public FragmentBreadCrumbs(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public FragmentBreadCrumbs(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxVisible = -1;
        this.mOnClickListener = new android.view.View.OnClickListener() { // from class: android.app.FragmentBreadCrumbs.1
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                if (view.getTag() instanceof android.app.FragmentManager.BackStackEntry) {
                    android.app.FragmentManager.BackStackEntry backStackEntry = (android.app.FragmentManager.BackStackEntry) view.getTag();
                    if (backStackEntry == android.app.FragmentBreadCrumbs.this.mParentEntry) {
                        if (android.app.FragmentBreadCrumbs.this.mParentClickListener != null) {
                            android.app.FragmentBreadCrumbs.this.mParentClickListener.onClick(view);
                            return;
                        }
                        return;
                    }
                    if (android.app.FragmentBreadCrumbs.this.mOnBreadCrumbClickListener != null) {
                        if (android.app.FragmentBreadCrumbs.this.mOnBreadCrumbClickListener.onBreadCrumbClick(backStackEntry == android.app.FragmentBreadCrumbs.this.mTopEntry ? null : backStackEntry, 0)) {
                            return;
                        }
                    }
                    if (backStackEntry == android.app.FragmentBreadCrumbs.this.mTopEntry) {
                        android.app.FragmentBreadCrumbs.this.mActivity.getFragmentManager().popBackStack();
                    } else {
                        android.app.FragmentBreadCrumbs.this.mActivity.getFragmentManager().popBackStack(backStackEntry.getId(), 0);
                    }
                }
            }
        };
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.FragmentBreadCrumbs, i, i2);
        this.mGravity = obtainStyledAttributes.getInt(0, DEFAULT_GRAVITY);
        this.mLayoutResId = obtainStyledAttributes.getResourceId(2, com.android.internal.R.layout.fragment_bread_crumb_item);
        this.mTextColor = obtainStyledAttributes.getColor(1, 0);
        obtainStyledAttributes.recycle();
    }

    public void setActivity(android.app.Activity activity) {
        this.mActivity = activity;
        this.mInflater = (android.view.LayoutInflater) activity.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        this.mContainer = (android.widget.LinearLayout) this.mInflater.inflate(com.android.internal.R.layout.fragment_bread_crumbs, (android.view.ViewGroup) this, false);
        addView(this.mContainer);
        activity.getFragmentManager().addOnBackStackChangedListener(this);
        updateCrumbs();
        setLayoutTransition(new android.animation.LayoutTransition());
    }

    public void setMaxVisible(int i) {
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("visibleCrumbs must be greater than zero");
        }
        this.mMaxVisible = i;
    }

    public void setParentTitle(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.view.View.OnClickListener onClickListener) {
        this.mParentEntry = createBackStackEntry(charSequence, charSequence2);
        this.mParentClickListener = onClickListener;
        updateCrumbs();
    }

    public void setOnBreadCrumbClickListener(android.app.FragmentBreadCrumbs.OnBreadCrumbClickListener onBreadCrumbClickListener) {
        this.mOnBreadCrumbClickListener = onBreadCrumbClickListener;
    }

    private android.app.BackStackRecord createBackStackEntry(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        if (charSequence == null) {
            return null;
        }
        android.app.BackStackRecord backStackRecord = new android.app.BackStackRecord((android.app.FragmentManagerImpl) this.mActivity.getFragmentManager());
        backStackRecord.setBreadCrumbTitle(charSequence);
        backStackRecord.setBreadCrumbShortTitle(charSequence2);
        return backStackRecord;
    }

    public void setTitle(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        this.mTopEntry = createBackStackEntry(charSequence, charSequence2);
        updateCrumbs();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth;
        int measuredWidth2;
        if (getChildCount() == 0) {
            return;
        }
        android.view.View childAt = getChildAt(0);
        int i5 = this.mPaddingTop;
        int measuredHeight = (this.mPaddingTop + childAt.getMeasuredHeight()) - this.mPaddingBottom;
        switch (android.view.Gravity.getAbsoluteGravity(this.mGravity & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK, getLayoutDirection())) {
            case 1:
                measuredWidth = (((this.mRight - this.mLeft) - childAt.getMeasuredWidth()) / 2) + this.mPaddingLeft;
                measuredWidth2 = childAt.getMeasuredWidth() + measuredWidth;
                break;
            case 5:
                measuredWidth2 = (this.mRight - this.mLeft) - this.mPaddingRight;
                measuredWidth = measuredWidth2 - childAt.getMeasuredWidth();
                break;
            default:
                measuredWidth = this.mPaddingLeft;
                measuredWidth2 = childAt.getMeasuredWidth() + measuredWidth;
                break;
        }
        if (measuredWidth < this.mPaddingLeft) {
            measuredWidth = this.mPaddingLeft;
        }
        if (measuredWidth2 > (this.mRight - this.mLeft) - this.mPaddingRight) {
            measuredWidth2 = (this.mRight - this.mLeft) - this.mPaddingRight;
        }
        childAt.layout(measuredWidth, i5, measuredWidth2, measuredHeight);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            android.view.View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                i3 = java.lang.Math.max(i3, childAt.getMeasuredWidth());
                i4 = java.lang.Math.max(i4, childAt.getMeasuredHeight());
                i5 = combineMeasuredStates(i5, childAt.getMeasuredState());
            }
        }
        setMeasuredDimension(resolveSizeAndState(java.lang.Math.max(i3 + this.mPaddingLeft + this.mPaddingRight, getSuggestedMinimumWidth()), i, i5), resolveSizeAndState(java.lang.Math.max(i4 + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()), i2, i5 << 16));
    }

    @Override // android.app.FragmentManager.OnBackStackChangedListener
    public void onBackStackChanged() {
        updateCrumbs();
    }

    private int getPreEntryCount() {
        return (this.mTopEntry != null ? 1 : 0) + (this.mParentEntry == null ? 0 : 1);
    }

    private android.app.FragmentManager.BackStackEntry getPreEntry(int i) {
        if (this.mParentEntry != null) {
            return i == 0 ? this.mParentEntry : this.mTopEntry;
        }
        return this.mTopEntry;
    }

    void updateCrumbs() {
        int i;
        int i2;
        android.app.FragmentManager.BackStackEntry backStackEntryAt;
        android.app.FragmentManager fragmentManager = this.mActivity.getFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        int preEntryCount = getPreEntryCount();
        int childCount = this.mContainer.getChildCount();
        int i3 = 0;
        while (true) {
            i = backStackEntryCount + preEntryCount;
            if (i3 >= i) {
                break;
            }
            if (i3 < preEntryCount) {
                backStackEntryAt = getPreEntry(i3);
            } else {
                backStackEntryAt = fragmentManager.getBackStackEntryAt(i3 - preEntryCount);
            }
            if (i3 < childCount && this.mContainer.getChildAt(i3).getTag() != backStackEntryAt) {
                for (int i4 = i3; i4 < childCount; i4++) {
                    this.mContainer.removeViewAt(i3);
                }
                childCount = i3;
            }
            if (i3 >= childCount) {
                android.view.View inflate = this.mInflater.inflate(this.mLayoutResId, (android.view.ViewGroup) this, false);
                android.widget.TextView textView = (android.widget.TextView) inflate.findViewById(16908310);
                textView.lambda$setTextAsync$0(backStackEntryAt.getBreadCrumbTitle());
                textView.setTag(backStackEntryAt);
                textView.setTextColor(this.mTextColor);
                if (i3 == 0) {
                    inflate.findViewById(com.android.internal.R.id.left_icon).setVisibility(8);
                }
                this.mContainer.addView(inflate);
                textView.setOnClickListener(this.mOnClickListener);
            }
            i3++;
        }
        int childCount2 = this.mContainer.getChildCount();
        while (childCount2 > i) {
            this.mContainer.removeViewAt(childCount2 - 1);
            childCount2--;
        }
        int i5 = 0;
        while (i5 < childCount2) {
            android.view.View childAt = this.mContainer.getChildAt(i5);
            childAt.findViewById(16908310).setEnabled(i5 < childCount2 + (-1));
            if (this.mMaxVisible > 0) {
                childAt.setVisibility(i5 < childCount2 - this.mMaxVisible ? 8 : 0);
                android.view.View findViewById = childAt.findViewById(com.android.internal.R.id.left_icon);
                if (i5 > childCount2 - this.mMaxVisible && i5 != 0) {
                    i2 = 0;
                } else {
                    i2 = 8;
                }
                findViewById.setVisibility(i2);
            }
            i5++;
        }
    }
}
