package com.android.internal.widget;

/* loaded from: classes5.dex */
public class ScrollingTabContainerView extends android.widget.HorizontalScrollView implements android.widget.AdapterView.OnItemClickListener {
    private static final int FADE_DURATION = 200;
    private static final java.lang.String TAG = "ScrollingTabContainerView";
    private static final android.animation.TimeInterpolator sAlphaInterpolator = new android.view.animation.DecelerateInterpolator();
    private boolean mAllowCollapse;
    private int mContentHeight;
    int mMaxTabWidth;
    private int mSelectedTabIndex;
    int mStackedTabMaxWidth;
    private com.android.internal.widget.ScrollingTabContainerView.TabClickListener mTabClickListener;
    private android.widget.LinearLayout mTabLayout;
    java.lang.Runnable mTabSelector;
    private android.widget.Spinner mTabSpinner;
    protected final com.android.internal.widget.ScrollingTabContainerView.VisibilityAnimListener mVisAnimListener;
    protected android.animation.Animator mVisibilityAnim;

    public ScrollingTabContainerView(android.content.Context context) {
        super(context);
        this.mVisAnimListener = new com.android.internal.widget.ScrollingTabContainerView.VisibilityAnimListener();
        setHorizontalScrollBarEnabled(false);
        com.android.internal.view.ActionBarPolicy actionBarPolicy = com.android.internal.view.ActionBarPolicy.get(context);
        setContentHeight(actionBarPolicy.getTabContainerHeight());
        this.mStackedTabMaxWidth = actionBarPolicy.getStackedTabMaxWidth();
        this.mTabLayout = createTabLayout();
        addView(this.mTabLayout, new android.view.ViewGroup.LayoutParams(-2, -1));
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int mode = android.view.View.MeasureSpec.getMode(i);
        boolean z = mode == 1073741824;
        setFillViewport(z);
        int childCount = this.mTabLayout.getChildCount();
        if (childCount > 1 && (mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            if (childCount <= 2) {
                this.mMaxTabWidth = android.view.View.MeasureSpec.getSize(i) / 2;
            } else {
                this.mMaxTabWidth = (int) (android.view.View.MeasureSpec.getSize(i) * 0.4f);
            }
            this.mMaxTabWidth = java.lang.Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
        } else {
            this.mMaxTabWidth = -1;
        }
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
        if (!z && this.mAllowCollapse) {
            this.mTabLayout.measure(0, makeMeasureSpec);
            if (this.mTabLayout.getMeasuredWidth() > android.view.View.MeasureSpec.getSize(i)) {
                performCollapse();
            } else {
                performExpand();
            }
        } else {
            performExpand();
        }
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i, makeMeasureSpec);
        int measuredWidth2 = getMeasuredWidth();
        if (z && measuredWidth != measuredWidth2) {
            setTabSelected(this.mSelectedTabIndex);
        }
    }

    private boolean isCollapsed() {
        return this.mTabSpinner != null && this.mTabSpinner.getParent() == this;
    }

    public void setAllowCollapse(boolean z) {
        this.mAllowCollapse = z;
    }

    private void performCollapse() {
        if (isCollapsed()) {
            return;
        }
        if (this.mTabSpinner == null) {
            this.mTabSpinner = createSpinner();
        }
        removeView(this.mTabLayout);
        addView(this.mTabSpinner, new android.view.ViewGroup.LayoutParams(-2, -1));
        if (this.mTabSpinner.getAdapter() == null) {
            com.android.internal.widget.ScrollingTabContainerView.TabAdapter tabAdapter = new com.android.internal.widget.ScrollingTabContainerView.TabAdapter(this.mContext);
            tabAdapter.setDropDownViewContext(this.mTabSpinner.getPopupContext());
            this.mTabSpinner.setAdapter((android.widget.SpinnerAdapter) tabAdapter);
        }
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector);
            this.mTabSelector = null;
        }
        this.mTabSpinner.setSelection(this.mSelectedTabIndex);
    }

    private boolean performExpand() {
        if (!isCollapsed()) {
            return false;
        }
        removeView(this.mTabSpinner);
        addView(this.mTabLayout, new android.view.ViewGroup.LayoutParams(-2, -1));
        setTabSelected(this.mTabSpinner.getSelectedItemPosition());
        return false;
    }

    public void setTabSelected(int i) {
        this.mSelectedTabIndex = i;
        int childCount = this.mTabLayout.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            android.view.View childAt = this.mTabLayout.getChildAt(i2);
            boolean z = i2 == i;
            childAt.setSelected(z);
            if (z) {
                animateToTab(i);
            }
            i2++;
        }
        if (this.mTabSpinner != null && i >= 0) {
            this.mTabSpinner.setSelection(i);
        }
    }

    public void setContentHeight(int i) {
        this.mContentHeight = i;
        requestLayout();
    }

    private android.widget.LinearLayout createTabLayout() {
        android.widget.LinearLayout linearLayout = new android.widget.LinearLayout(getContext(), null, 16843508);
        linearLayout.setMeasureWithLargestChildEnabled(true);
        linearLayout.setGravity(17);
        linearLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-2, -1));
        return linearLayout;
    }

    private android.widget.Spinner createSpinner() {
        android.widget.Spinner spinner = new android.widget.Spinner(getContext(), null, 16843479);
        spinner.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-2, -1));
        spinner.setOnItemClickListenerInt(this);
        return spinner;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        com.android.internal.view.ActionBarPolicy actionBarPolicy = com.android.internal.view.ActionBarPolicy.get(getContext());
        setContentHeight(actionBarPolicy.getTabContainerHeight());
        this.mStackedTabMaxWidth = actionBarPolicy.getStackedTabMaxWidth();
    }

    public void animateToVisibility(int i) {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if (i == 0) {
            if (getVisibility() != 0) {
                setAlpha(0.0f);
            }
            android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this, "alpha", 1.0f);
            ofFloat.setDuration(200L);
            ofFloat.setInterpolator(sAlphaInterpolator);
            ofFloat.addListener(this.mVisAnimListener.withFinalVisibility(i));
            ofFloat.start();
            return;
        }
        android.animation.ObjectAnimator ofFloat2 = android.animation.ObjectAnimator.ofFloat(this, "alpha", 0.0f);
        ofFloat2.setDuration(200L);
        ofFloat2.setInterpolator(sAlphaInterpolator);
        ofFloat2.addListener(this.mVisAnimListener.withFinalVisibility(i));
        ofFloat2.start();
    }

    public void animateToTab(int i) {
        final android.view.View childAt = this.mTabLayout.getChildAt(i);
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector);
        }
        this.mTabSelector = new java.lang.Runnable() { // from class: com.android.internal.widget.ScrollingTabContainerView.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.widget.ScrollingTabContainerView.this.smoothScrollTo(childAt.getLeft() - ((com.android.internal.widget.ScrollingTabContainerView.this.getWidth() - childAt.getWidth()) / 2), 0);
                com.android.internal.widget.ScrollingTabContainerView.this.mTabSelector = null;
            }
        };
        post(this.mTabSelector);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mTabSelector != null) {
            post(this.mTabSelector);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.internal.widget.ScrollingTabContainerView.TabView createTabView(android.content.Context context, android.app.ActionBar.Tab tab, boolean z) {
        com.android.internal.widget.ScrollingTabContainerView.TabView tabView = new com.android.internal.widget.ScrollingTabContainerView.TabView(context, tab, z);
        if (z) {
            tabView.setBackgroundDrawable(null);
            tabView.setLayoutParams(new android.widget.AbsListView.LayoutParams(-1, this.mContentHeight));
        } else {
            tabView.setFocusable(true);
            if (this.mTabClickListener == null) {
                this.mTabClickListener = new com.android.internal.widget.ScrollingTabContainerView.TabClickListener();
            }
            tabView.setOnClickListener(this.mTabClickListener);
        }
        return tabView;
    }

    public void addTab(android.app.ActionBar.Tab tab, boolean z) {
        com.android.internal.widget.ScrollingTabContainerView.TabView createTabView = createTabView(this.mContext, tab, false);
        this.mTabLayout.addView(createTabView, new android.widget.LinearLayout.LayoutParams(0, -1, 1.0f));
        if (this.mTabSpinner != null) {
            ((com.android.internal.widget.ScrollingTabContainerView.TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (z) {
            createTabView.setSelected(true);
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void addTab(android.app.ActionBar.Tab tab, int i, boolean z) {
        com.android.internal.widget.ScrollingTabContainerView.TabView createTabView = createTabView(this.mContext, tab, false);
        this.mTabLayout.addView(createTabView, i, new android.widget.LinearLayout.LayoutParams(0, -1, 1.0f));
        if (this.mTabSpinner != null) {
            ((com.android.internal.widget.ScrollingTabContainerView.TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (z) {
            createTabView.setSelected(true);
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void updateTab(int i) {
        ((com.android.internal.widget.ScrollingTabContainerView.TabView) this.mTabLayout.getChildAt(i)).update();
        if (this.mTabSpinner != null) {
            ((com.android.internal.widget.ScrollingTabContainerView.TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void removeTabAt(int i) {
        this.mTabLayout.removeViewAt(i);
        if (this.mTabSpinner != null) {
            ((com.android.internal.widget.ScrollingTabContainerView.TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void removeAllTabs() {
        this.mTabLayout.removeAllViews();
        if (this.mTabSpinner != null) {
            ((com.android.internal.widget.ScrollingTabContainerView.TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
        ((com.android.internal.widget.ScrollingTabContainerView.TabView) view).getTab().select();
    }

    private class TabView extends android.widget.LinearLayout {
        private android.view.View mCustomView;
        private android.widget.ImageView mIconView;
        private android.app.ActionBar.Tab mTab;
        private android.widget.TextView mTextView;

        public TabView(android.content.Context context, android.app.ActionBar.Tab tab, boolean z) {
            super(context, null, 16843507);
            this.mTab = tab;
            if (z) {
                setGravity(8388627);
            }
            update();
        }

        public void bindTab(android.app.ActionBar.Tab tab) {
            this.mTab = tab;
            update();
        }

        @Override // android.view.View
        public void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (z2 && z) {
                sendAccessibilityEvent(4);
            }
        }

        @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
        public java.lang.CharSequence getAccessibilityClassName() {
            return android.app.ActionBar.Tab.class.getName();
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (com.android.internal.widget.ScrollingTabContainerView.this.mMaxTabWidth > 0 && getMeasuredWidth() > com.android.internal.widget.ScrollingTabContainerView.this.mMaxTabWidth) {
                super.onMeasure(android.view.View.MeasureSpec.makeMeasureSpec(com.android.internal.widget.ScrollingTabContainerView.this.mMaxTabWidth, 1073741824), i2);
            }
        }

        public void update() {
            android.app.ActionBar.Tab tab = this.mTab;
            android.view.View customView = tab.getCustomView();
            if (customView != null) {
                android.view.ViewParent parent = customView.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((android.view.ViewGroup) parent).removeView(customView);
                    }
                    addView(customView);
                }
                this.mCustomView = customView;
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(8);
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                    return;
                }
                return;
            }
            if (this.mCustomView != null) {
                removeView(this.mCustomView);
                this.mCustomView = null;
            }
            android.graphics.drawable.Drawable icon = tab.getIcon();
            java.lang.CharSequence text = tab.getText();
            if (icon != null) {
                if (this.mIconView == null) {
                    android.widget.ImageView imageView = new android.widget.ImageView(getContext());
                    android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    imageView.setLayoutParams(layoutParams);
                    addView(imageView, 0);
                    this.mIconView = imageView;
                }
                this.mIconView.setImageDrawable(icon);
                this.mIconView.setVisibility(0);
            } else if (this.mIconView != null) {
                this.mIconView.setVisibility(8);
                this.mIconView.setImageDrawable(null);
            }
            boolean z = !android.text.TextUtils.isEmpty(text);
            if (z) {
                if (this.mTextView == null) {
                    android.widget.TextView textView = new android.widget.TextView(getContext(), null, 16843509);
                    textView.setEllipsize(android.text.TextUtils.TruncateAt.END);
                    android.widget.LinearLayout.LayoutParams layoutParams2 = new android.widget.LinearLayout.LayoutParams(-2, -2);
                    layoutParams2.gravity = 16;
                    textView.setLayoutParams(layoutParams2);
                    addView(textView);
                    this.mTextView = textView;
                }
                this.mTextView.lambda$setTextAsync$0(text);
                this.mTextView.setVisibility(0);
            } else if (this.mTextView != null) {
                this.mTextView.setVisibility(8);
                this.mTextView.lambda$setTextAsync$0((java.lang.CharSequence) null);
            }
            if (this.mIconView != null) {
                this.mIconView.setContentDescription(tab.getContentDescription());
            }
            setTooltipText(z ? null : tab.getContentDescription());
        }

        public android.app.ActionBar.Tab getTab() {
            return this.mTab;
        }
    }

    private class TabAdapter extends android.widget.BaseAdapter {
        private android.content.Context mDropDownContext;

        public TabAdapter(android.content.Context context) {
            setDropDownViewContext(context);
        }

        public void setDropDownViewContext(android.content.Context context) {
            this.mDropDownContext = context;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return com.android.internal.widget.ScrollingTabContainerView.this.mTabLayout.getChildCount();
        }

        @Override // android.widget.Adapter
        public java.lang.Object getItem(int i) {
            return ((com.android.internal.widget.ScrollingTabContainerView.TabView) com.android.internal.widget.ScrollingTabContainerView.this.mTabLayout.getChildAt(i)).getTab();
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            if (view == null) {
                return com.android.internal.widget.ScrollingTabContainerView.this.createTabView(com.android.internal.widget.ScrollingTabContainerView.this.mContext, (android.app.ActionBar.Tab) getItem(i), true);
            }
            ((com.android.internal.widget.ScrollingTabContainerView.TabView) view).bindTab((android.app.ActionBar.Tab) getItem(i));
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
        public android.view.View getDropDownView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            if (view == null) {
                return com.android.internal.widget.ScrollingTabContainerView.this.createTabView(this.mDropDownContext, (android.app.ActionBar.Tab) getItem(i), true);
            }
            ((com.android.internal.widget.ScrollingTabContainerView.TabView) view).bindTab((android.app.ActionBar.Tab) getItem(i));
            return view;
        }
    }

    private class TabClickListener implements android.view.View.OnClickListener {
        private TabClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            ((com.android.internal.widget.ScrollingTabContainerView.TabView) view).getTab().select();
            int childCount = com.android.internal.widget.ScrollingTabContainerView.this.mTabLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = com.android.internal.widget.ScrollingTabContainerView.this.mTabLayout.getChildAt(i);
                childAt.setSelected(childAt == view);
            }
        }
    }

    protected class VisibilityAnimListener implements android.animation.Animator.AnimatorListener {
        private boolean mCanceled = false;
        private int mFinalVisibility;

        protected VisibilityAnimListener() {
        }

        public com.android.internal.widget.ScrollingTabContainerView.VisibilityAnimListener withFinalVisibility(int i) {
            this.mFinalVisibility = i;
            return this;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            com.android.internal.widget.ScrollingTabContainerView.this.setVisibility(0);
            com.android.internal.widget.ScrollingTabContainerView.this.mVisibilityAnim = animator;
            this.mCanceled = false;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            if (this.mCanceled) {
                return;
            }
            com.android.internal.widget.ScrollingTabContainerView.this.mVisibilityAnim = null;
            com.android.internal.widget.ScrollingTabContainerView.this.setVisibility(this.mFinalVisibility);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }
    }
}
