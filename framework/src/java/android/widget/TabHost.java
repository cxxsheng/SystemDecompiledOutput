package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class TabHost extends android.widget.FrameLayout implements android.view.ViewTreeObserver.OnTouchModeChangeListener {
    private static final int TABWIDGET_LOCATION_BOTTOM = 3;
    private static final int TABWIDGET_LOCATION_LEFT = 0;
    private static final int TABWIDGET_LOCATION_RIGHT = 2;
    private static final int TABWIDGET_LOCATION_TOP = 1;
    protected int mCurrentTab;
    private android.view.View mCurrentView;
    protected android.app.LocalActivityManager mLocalActivityManager;
    private android.widget.TabHost.OnTabChangeListener mOnTabChangeListener;
    private android.widget.FrameLayout mTabContent;
    private android.view.View.OnKeyListener mTabKeyListener;
    private int mTabLayoutId;
    private java.util.List<android.widget.TabHost.TabSpec> mTabSpecs;
    private android.widget.TabWidget mTabWidget;

    private interface ContentStrategy {
        android.view.View getContentView();

        void tabClosed();
    }

    private interface IndicatorStrategy {
        android.view.View createIndicatorView();
    }

    public interface OnTabChangeListener {
        void onTabChanged(java.lang.String str);
    }

    public interface TabContentFactory {
        android.view.View createTabContent(java.lang.String str);
    }

    public TabHost(android.content.Context context) {
        super(context);
        this.mTabSpecs = new java.util.ArrayList(2);
        this.mCurrentTab = -1;
        this.mCurrentView = null;
        this.mLocalActivityManager = null;
        initTabHost();
    }

    public TabHost(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842883);
    }

    public TabHost(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TabHost(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet);
        this.mTabSpecs = new java.util.ArrayList(2);
        this.mCurrentTab = -1;
        this.mCurrentView = null;
        this.mLocalActivityManager = null;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TabWidget, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.TabWidget, attributeSet, obtainStyledAttributes, i, i2);
        this.mTabLayoutId = obtainStyledAttributes.getResourceId(4, 0);
        obtainStyledAttributes.recycle();
        if (this.mTabLayoutId == 0) {
            this.mTabLayoutId = com.android.internal.R.layout.tab_indicator_holo;
        }
        initTabHost();
    }

    private void initTabHost() {
        setFocusableInTouchMode(true);
        setDescendantFocusability(262144);
        this.mCurrentTab = -1;
        this.mCurrentView = null;
    }

    public android.widget.TabHost.TabSpec newTabSpec(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("tag must be non-null");
        }
        return new android.widget.TabHost.TabSpec(str);
    }

    public void setup() {
        this.mTabWidget = (android.widget.TabWidget) findViewById(16908307);
        if (this.mTabWidget == null) {
            throw new java.lang.RuntimeException("Your TabHost must have a TabWidget whose id attribute is 'android.R.id.tabs'");
        }
        this.mTabKeyListener = new android.view.View.OnKeyListener() { // from class: android.widget.TabHost.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(android.view.View view, int i, android.view.KeyEvent keyEvent) {
                if (android.view.KeyEvent.isModifierKey(i)) {
                    return false;
                }
                switch (i) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 61:
                    case 62:
                    case 66:
                        return false;
                    default:
                        android.widget.TabHost.this.mTabContent.requestFocus(2);
                        return android.widget.TabHost.this.mTabContent.dispatchKeyEvent(keyEvent);
                }
            }
        };
        this.mTabWidget.setTabSelectionListener(new android.widget.TabWidget.OnTabSelectionChanged() { // from class: android.widget.TabHost.2
            @Override // android.widget.TabWidget.OnTabSelectionChanged
            public void onTabSelectionChanged(int i, boolean z) {
                android.widget.TabHost.this.setCurrentTab(i);
                if (z) {
                    android.widget.TabHost.this.mTabContent.requestFocus(2);
                }
            }
        });
        this.mTabContent = (android.widget.FrameLayout) findViewById(16908305);
        if (this.mTabContent == null) {
            throw new java.lang.RuntimeException("Your TabHost must have a FrameLayout whose id attribute is 'android.R.id.tabcontent'");
        }
    }

    @Override // android.view.View
    public void sendAccessibilityEventInternal(int i) {
    }

    public void setup(android.app.LocalActivityManager localActivityManager) {
        setup();
        this.mLocalActivityManager = localActivityManager;
    }

    @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
    public void onTouchModeChanged(boolean z) {
    }

    public void addTab(android.widget.TabHost.TabSpec tabSpec) {
        if (tabSpec.mIndicatorStrategy == null) {
            throw new java.lang.IllegalArgumentException("you must specify a way to create the tab indicator.");
        }
        if (tabSpec.mContentStrategy == null) {
            throw new java.lang.IllegalArgumentException("you must specify a way to create the tab content");
        }
        android.view.View createIndicatorView = tabSpec.mIndicatorStrategy.createIndicatorView();
        createIndicatorView.setOnKeyListener(this.mTabKeyListener);
        if (tabSpec.mIndicatorStrategy instanceof android.widget.TabHost.ViewIndicatorStrategy) {
            this.mTabWidget.setStripEnabled(false);
        }
        this.mTabWidget.addView(createIndicatorView);
        this.mTabSpecs.add(tabSpec);
        if (this.mCurrentTab == -1) {
            setCurrentTab(0);
        }
    }

    public void clearAllTabs() {
        this.mTabWidget.removeAllViews();
        initTabHost();
        this.mTabContent.removeAllViews();
        this.mTabSpecs.clear();
        requestLayout();
        invalidate();
    }

    public android.widget.TabWidget getTabWidget() {
        return this.mTabWidget;
    }

    public int getCurrentTab() {
        return this.mCurrentTab;
    }

    public java.lang.String getCurrentTabTag() {
        if (this.mCurrentTab >= 0 && this.mCurrentTab < this.mTabSpecs.size()) {
            return this.mTabSpecs.get(this.mCurrentTab).getTag();
        }
        return null;
    }

    public android.view.View getCurrentTabView() {
        if (this.mCurrentTab >= 0 && this.mCurrentTab < this.mTabSpecs.size()) {
            return this.mTabWidget.getChildTabViewAt(this.mCurrentTab);
        }
        return null;
    }

    public android.view.View getCurrentView() {
        return this.mCurrentView;
    }

    public void setCurrentTabByTag(java.lang.String str) {
        int size = this.mTabSpecs.size();
        for (int i = 0; i < size; i++) {
            if (this.mTabSpecs.get(i).getTag().equals(str)) {
                setCurrentTab(i);
                return;
            }
        }
    }

    public android.widget.FrameLayout getTabContentView() {
        return this.mTabContent;
    }

    private int getTabWidgetLocation() {
        switch (this.mTabWidget.getOrientation()) {
            case 1:
                return this.mTabContent.getLeft() < this.mTabWidget.getLeft() ? 2 : 0;
            default:
                return this.mTabContent.getTop() < this.mTabWidget.getTop() ? 3 : 1;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        int i;
        int i2;
        int i3;
        boolean dispatchKeyEvent = super.dispatchKeyEvent(keyEvent);
        if (!dispatchKeyEvent && keyEvent.getAction() == 0 && this.mCurrentView != null && this.mCurrentView.isRootNamespace() && this.mCurrentView.hasFocus()) {
            switch (getTabWidgetLocation()) {
                case 0:
                    i = 21;
                    i2 = 17;
                    i3 = 1;
                    break;
                case 1:
                default:
                    i = 19;
                    i2 = 33;
                    i3 = 2;
                    break;
                case 2:
                    i = 22;
                    i2 = 66;
                    i3 = 3;
                    break;
                case 3:
                    i = 20;
                    i2 = 130;
                    i3 = 4;
                    break;
            }
            if (keyEvent.getKeyCode() == i && this.mCurrentView.findFocus().focusSearch(i2) == null) {
                this.mTabWidget.getChildTabViewAt(this.mCurrentTab).requestFocus();
                playSoundEffect(i3);
                return true;
            }
        }
        return dispatchKeyEvent;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchWindowFocusChanged(boolean z) {
        if (this.mCurrentView != null) {
            this.mCurrentView.dispatchWindowFocusChanged(z);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.TabHost.class.getName();
    }

    public void setCurrentTab(int i) {
        if (i < 0 || i >= this.mTabSpecs.size() || i == this.mCurrentTab) {
            return;
        }
        if (this.mCurrentTab != -1) {
            this.mTabSpecs.get(this.mCurrentTab).mContentStrategy.tabClosed();
        }
        this.mCurrentTab = i;
        android.widget.TabHost.TabSpec tabSpec = this.mTabSpecs.get(i);
        this.mTabWidget.focusCurrentTab(this.mCurrentTab);
        this.mCurrentView = tabSpec.mContentStrategy.getContentView();
        if (this.mCurrentView.getParent() == null) {
            this.mTabContent.addView(this.mCurrentView, new android.view.ViewGroup.LayoutParams(-1, -1));
        }
        if (!this.mTabWidget.hasFocus()) {
            this.mCurrentView.requestFocus();
        }
        invokeOnTabChangeListener();
    }

    public void setOnTabChangedListener(android.widget.TabHost.OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
    }

    private void invokeOnTabChangeListener() {
        if (this.mOnTabChangeListener != null) {
            this.mOnTabChangeListener.onTabChanged(getCurrentTabTag());
        }
    }

    public class TabSpec {
        private android.widget.TabHost.ContentStrategy mContentStrategy;
        private android.widget.TabHost.IndicatorStrategy mIndicatorStrategy;
        private final java.lang.String mTag;

        private TabSpec(java.lang.String str) {
            this.mTag = str;
        }

        public android.widget.TabHost.TabSpec setIndicator(java.lang.CharSequence charSequence) {
            this.mIndicatorStrategy = new android.widget.TabHost.LabelIndicatorStrategy(charSequence);
            return this;
        }

        public android.widget.TabHost.TabSpec setIndicator(java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable) {
            this.mIndicatorStrategy = new android.widget.TabHost.LabelAndIconIndicatorStrategy(charSequence, drawable);
            return this;
        }

        public android.widget.TabHost.TabSpec setIndicator(android.view.View view) {
            this.mIndicatorStrategy = new android.widget.TabHost.ViewIndicatorStrategy(view);
            return this;
        }

        public android.widget.TabHost.TabSpec setContent(int i) {
            this.mContentStrategy = new android.widget.TabHost.ViewIdContentStrategy(i);
            return this;
        }

        public android.widget.TabHost.TabSpec setContent(android.widget.TabHost.TabContentFactory tabContentFactory) {
            this.mContentStrategy = android.widget.TabHost.this.new FactoryContentStrategy(this.mTag, tabContentFactory);
            return this;
        }

        public android.widget.TabHost.TabSpec setContent(android.content.Intent intent) {
            this.mContentStrategy = new android.widget.TabHost.IntentContentStrategy(this.mTag, intent);
            return this;
        }

        public java.lang.String getTag() {
            return this.mTag;
        }
    }

    private class LabelIndicatorStrategy implements android.widget.TabHost.IndicatorStrategy {
        private final java.lang.CharSequence mLabel;

        private LabelIndicatorStrategy(java.lang.CharSequence charSequence) {
            this.mLabel = charSequence;
        }

        @Override // android.widget.TabHost.IndicatorStrategy
        public android.view.View createIndicatorView() {
            android.content.Context context = android.widget.TabHost.this.getContext();
            android.view.View inflate = ((android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(android.widget.TabHost.this.mTabLayoutId, (android.view.ViewGroup) android.widget.TabHost.this.mTabWidget, false);
            android.widget.TextView textView = (android.widget.TextView) inflate.findViewById(16908310);
            textView.lambda$setTextAsync$0(this.mLabel);
            if (context.getApplicationInfo().targetSdkVersion <= 4) {
                inflate.setBackgroundResource(com.android.internal.R.drawable.tab_indicator_v4);
                textView.setTextColor(context.getColorStateList(com.android.internal.R.color.tab_indicator_text_v4));
            }
            return inflate;
        }
    }

    private class LabelAndIconIndicatorStrategy implements android.widget.TabHost.IndicatorStrategy {
        private final android.graphics.drawable.Drawable mIcon;
        private final java.lang.CharSequence mLabel;

        private LabelAndIconIndicatorStrategy(java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable) {
            this.mLabel = charSequence;
            this.mIcon = drawable;
        }

        @Override // android.widget.TabHost.IndicatorStrategy
        public android.view.View createIndicatorView() {
            android.content.Context context = android.widget.TabHost.this.getContext();
            android.view.View inflate = ((android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(android.widget.TabHost.this.mTabLayoutId, (android.view.ViewGroup) android.widget.TabHost.this.mTabWidget, false);
            android.widget.TextView textView = (android.widget.TextView) inflate.findViewById(16908310);
            android.widget.ImageView imageView = (android.widget.ImageView) inflate.findViewById(16908294);
            boolean z = true;
            if ((imageView.getVisibility() == 8) && !android.text.TextUtils.isEmpty(this.mLabel)) {
                z = false;
            }
            textView.lambda$setTextAsync$0(this.mLabel);
            if (z && this.mIcon != null) {
                imageView.lambda$setImageURIAsync$2(this.mIcon);
                imageView.setVisibility(0);
            }
            if (context.getApplicationInfo().targetSdkVersion <= 4) {
                inflate.setBackgroundResource(com.android.internal.R.drawable.tab_indicator_v4);
                textView.setTextColor(context.getColorStateList(com.android.internal.R.color.tab_indicator_text_v4));
            }
            return inflate;
        }
    }

    private class ViewIndicatorStrategy implements android.widget.TabHost.IndicatorStrategy {
        private final android.view.View mView;

        private ViewIndicatorStrategy(android.view.View view) {
            this.mView = view;
        }

        @Override // android.widget.TabHost.IndicatorStrategy
        public android.view.View createIndicatorView() {
            return this.mView;
        }
    }

    private class ViewIdContentStrategy implements android.widget.TabHost.ContentStrategy {
        private final android.view.View mView;

        private ViewIdContentStrategy(int i) {
            this.mView = android.widget.TabHost.this.mTabContent.findViewById(i);
            if (this.mView != null) {
                this.mView.setVisibility(8);
                return;
            }
            throw new java.lang.RuntimeException("Could not create tab content because could not find view with id " + i);
        }

        @Override // android.widget.TabHost.ContentStrategy
        public android.view.View getContentView() {
            this.mView.setVisibility(0);
            return this.mView;
        }

        @Override // android.widget.TabHost.ContentStrategy
        public void tabClosed() {
            this.mView.setVisibility(8);
        }
    }

    private class FactoryContentStrategy implements android.widget.TabHost.ContentStrategy {
        private android.widget.TabHost.TabContentFactory mFactory;
        private android.view.View mTabContent;
        private final java.lang.CharSequence mTag;

        public FactoryContentStrategy(java.lang.CharSequence charSequence, android.widget.TabHost.TabContentFactory tabContentFactory) {
            this.mTag = charSequence;
            this.mFactory = tabContentFactory;
        }

        @Override // android.widget.TabHost.ContentStrategy
        public android.view.View getContentView() {
            if (this.mTabContent == null) {
                this.mTabContent = this.mFactory.createTabContent(this.mTag.toString());
            }
            this.mTabContent.setVisibility(0);
            return this.mTabContent;
        }

        @Override // android.widget.TabHost.ContentStrategy
        public void tabClosed() {
            this.mTabContent.setVisibility(8);
        }
    }

    private class IntentContentStrategy implements android.widget.TabHost.ContentStrategy {
        private final android.content.Intent mIntent;
        private android.view.View mLaunchedView;
        private final java.lang.String mTag;

        private IntentContentStrategy(java.lang.String str, android.content.Intent intent) {
            this.mTag = str;
            this.mIntent = intent;
        }

        @Override // android.widget.TabHost.ContentStrategy
        public android.view.View getContentView() {
            if (android.widget.TabHost.this.mLocalActivityManager == null) {
                throw new java.lang.IllegalStateException("Did you forget to call 'public void setup(LocalActivityManager activityGroup)'?");
            }
            android.view.Window startActivity = android.widget.TabHost.this.mLocalActivityManager.startActivity(this.mTag, this.mIntent);
            android.view.View decorView = startActivity != null ? startActivity.getDecorView() : null;
            if (this.mLaunchedView != decorView && this.mLaunchedView != null && this.mLaunchedView.getParent() != null) {
                android.widget.TabHost.this.mTabContent.removeView(this.mLaunchedView);
            }
            this.mLaunchedView = decorView;
            if (this.mLaunchedView != null) {
                this.mLaunchedView.setVisibility(0);
                this.mLaunchedView.setFocusableInTouchMode(true);
                ((android.view.ViewGroup) this.mLaunchedView).setDescendantFocusability(262144);
            }
            return this.mLaunchedView;
        }

        @Override // android.widget.TabHost.ContentStrategy
        public void tabClosed() {
            if (this.mLaunchedView != null) {
                this.mLaunchedView.setVisibility(8);
            }
        }
    }
}
