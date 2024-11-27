package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
public final class NavigationBarInflaterView extends android.widget.FrameLayout {
    private static final java.lang.String ABSOLUTE_SUFFIX = "A";
    private static final java.lang.String ABSOLUTE_VERTICAL_CENTERED_SUFFIX = "C";
    public static final java.lang.String BACK = "back";
    public static final java.lang.String BUTTON_SEPARATOR = ",";
    public static final java.lang.String CLIPBOARD = "clipboard";
    private static final java.lang.String CONFIG_NAV_BAR_LAYOUT_HANDLE = "back[70AC];home_handle;ime_switcher[70AC]";
    public static final java.lang.String CONTEXTUAL = "contextual";
    public static final java.lang.String GRAVITY_SEPARATOR = ";";
    public static final java.lang.String HOME = "home";
    public static final java.lang.String HOME_HANDLE = "home_handle";
    public static final java.lang.String IME_SWITCHER = "ime_switcher";
    public static final java.lang.String KEY = "key";
    public static final java.lang.String KEY_CODE_END = ")";
    public static final java.lang.String KEY_CODE_START = "(";
    public static final java.lang.String KEY_IMAGE_DELIM = ":";
    public static final java.lang.String LEFT = "left";
    public static final java.lang.String MENU_IME_ROTATE = "menu_ime";
    public static final java.lang.String NAVSPACE = "space";
    public static final java.lang.String NAV_BAR_LEFT = "sysui_nav_bar_left";
    public static final java.lang.String NAV_BAR_RIGHT = "sysui_nav_bar_right";
    public static final java.lang.String NAV_BAR_VIEWS = "sysui_nav_bar";
    public static final java.lang.String RECENT = "recent";
    public static final java.lang.String RIGHT = "right";
    public static final java.lang.String SIZE_MOD_END = "]";
    public static final java.lang.String SIZE_MOD_START = "[";
    private static final java.lang.String TAG = "NavBarInflater";
    private static final java.lang.String WEIGHT_CENTERED_SUFFIX = "WC";
    private static final java.lang.String WEIGHT_SUFFIX = "W";
    private boolean mAlternativeOrder;
    android.util.SparseArray<android.inputmethodservice.navigationbar.ButtonDispatcher> mButtonDispatchers;
    protected android.widget.FrameLayout mHorizontal;
    protected android.view.LayoutInflater mLandscapeInflater;
    private android.view.View mLastLandscape;
    private android.view.View mLastPortrait;
    protected android.view.LayoutInflater mLayoutInflater;

    public NavigationBarInflaterView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        createInflaters();
    }

    void createInflaters() {
        this.mLayoutInflater = android.view.LayoutInflater.from(this.mContext);
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        configuration.setTo(this.mContext.getResources().getConfiguration());
        configuration.orientation = 2;
        this.mLandscapeInflater = android.view.LayoutInflater.from(this.mContext.createConfigurationContext(configuration));
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflateChildren();
        clearViews();
        inflateLayout(getDefaultLayout());
    }

    private void inflateChildren() {
        removeAllViews();
        this.mHorizontal = (android.widget.FrameLayout) this.mLayoutInflater.inflate(com.android.internal.R.layout.input_method_navigation_layout, (android.view.ViewGroup) this, false);
        addView(this.mHorizontal);
        updateAlternativeOrder();
    }

    java.lang.String getDefaultLayout() {
        return CONFIG_NAV_BAR_LAYOUT_HANDLE;
    }

    void setButtonDispatchers(android.util.SparseArray<android.inputmethodservice.navigationbar.ButtonDispatcher> sparseArray) {
        this.mButtonDispatchers = sparseArray;
        for (int i = 0; i < sparseArray.size(); i++) {
            initiallyFill(sparseArray.valueAt(i));
        }
    }

    void updateButtonDispatchersCurrentView() {
        if (this.mButtonDispatchers != null) {
            android.widget.FrameLayout frameLayout = this.mHorizontal;
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                this.mButtonDispatchers.valueAt(i).setCurrentView(frameLayout);
            }
        }
    }

    void setAlternativeOrder(boolean z) {
        if (z != this.mAlternativeOrder) {
            this.mAlternativeOrder = z;
            updateAlternativeOrder();
        }
    }

    private void updateAlternativeOrder() {
        updateAlternativeOrder(this.mHorizontal.findViewById(com.android.internal.R.id.input_method_nav_ends_group));
        updateAlternativeOrder(this.mHorizontal.findViewById(com.android.internal.R.id.input_method_nav_center_group));
    }

    private void updateAlternativeOrder(android.view.View view) {
        if (view instanceof android.inputmethodservice.navigationbar.ReverseLinearLayout) {
            ((android.inputmethodservice.navigationbar.ReverseLinearLayout) view).setAlternativeOrder(this.mAlternativeOrder);
        }
    }

    private void initiallyFill(android.inputmethodservice.navigationbar.ButtonDispatcher buttonDispatcher) {
        addAll(buttonDispatcher, (android.view.ViewGroup) this.mHorizontal.findViewById(com.android.internal.R.id.input_method_nav_ends_group));
        addAll(buttonDispatcher, (android.view.ViewGroup) this.mHorizontal.findViewById(com.android.internal.R.id.input_method_nav_center_group));
    }

    private void addAll(android.inputmethodservice.navigationbar.ButtonDispatcher buttonDispatcher, android.view.ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i).getId() == buttonDispatcher.getId()) {
                buttonDispatcher.addView(viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof android.view.ViewGroup) {
                addAll(buttonDispatcher, (android.view.ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }

    protected void inflateLayout(java.lang.String str) {
        if (str == null) {
            str = getDefaultLayout();
        }
        java.lang.String[] split = str.split(GRAVITY_SEPARATOR, 3);
        if (split.length != 3) {
            android.util.Log.d(TAG, "Invalid layout.");
            split = getDefaultLayout().split(GRAVITY_SEPARATOR, 3);
        }
        java.lang.String[] split2 = split[0].split(",");
        java.lang.String[] split3 = split[1].split(",");
        java.lang.String[] split4 = split[2].split(",");
        inflateButtons(split2, (android.view.ViewGroup) this.mHorizontal.findViewById(com.android.internal.R.id.input_method_nav_ends_group), false, true);
        inflateButtons(split3, (android.view.ViewGroup) this.mHorizontal.findViewById(com.android.internal.R.id.input_method_nav_center_group), false, false);
        addGravitySpacer((android.widget.LinearLayout) this.mHorizontal.findViewById(com.android.internal.R.id.input_method_nav_ends_group));
        inflateButtons(split4, (android.view.ViewGroup) this.mHorizontal.findViewById(com.android.internal.R.id.input_method_nav_ends_group), false, false);
        updateButtonDispatchersCurrentView();
    }

    private void addGravitySpacer(android.widget.LinearLayout linearLayout) {
        linearLayout.addView(new android.widget.Space(this.mContext), new android.widget.LinearLayout.LayoutParams(0, 0, 1.0f));
    }

    private void inflateButtons(java.lang.String[] strArr, android.view.ViewGroup viewGroup, boolean z, boolean z2) {
        for (java.lang.String str : strArr) {
            inflateButton(str, viewGroup, z, z2);
        }
    }

    private android.view.ViewGroup.LayoutParams copy(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof android.widget.LinearLayout.LayoutParams) {
            return new android.widget.LinearLayout.LayoutParams(layoutParams.width, layoutParams.height, ((android.widget.LinearLayout.LayoutParams) layoutParams).weight);
        }
        return new android.widget.FrameLayout.LayoutParams(layoutParams.width, layoutParams.height);
    }

    protected android.view.View inflateButton(java.lang.String str, android.view.ViewGroup viewGroup, boolean z, boolean z2) {
        android.view.View view;
        android.view.View createView = createView(str, viewGroup, z ? this.mLandscapeInflater : this.mLayoutInflater);
        if (createView == null) {
            return null;
        }
        android.view.View applySize = applySize(createView, str, z, z2);
        viewGroup.addView(applySize);
        addToDispatchers(applySize);
        android.view.View view2 = z ? this.mLastLandscape : this.mLastPortrait;
        if (!(applySize instanceof android.inputmethodservice.navigationbar.ReverseLinearLayout.ReverseRelativeLayout)) {
            view = applySize;
        } else {
            view = ((android.inputmethodservice.navigationbar.ReverseLinearLayout.ReverseRelativeLayout) applySize).getChildAt(0);
        }
        if (view2 != null) {
            view.setAccessibilityTraversalAfter(view2.getId());
        }
        if (z) {
            this.mLastLandscape = view;
        } else {
            this.mLastPortrait = view;
        }
        return applySize;
    }

    private android.view.View applySize(android.view.View view, java.lang.String str, boolean z, boolean z2) {
        int i;
        java.lang.String extractSize = extractSize(str);
        if (extractSize == null) {
            return view;
        }
        if (extractSize.contains("W") || extractSize.contains("A")) {
            android.inputmethodservice.navigationbar.ReverseLinearLayout.ReverseRelativeLayout reverseRelativeLayout = new android.inputmethodservice.navigationbar.ReverseLinearLayout.ReverseRelativeLayout(this.mContext);
            android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(view.getLayoutParams());
            if (z) {
                i = z2 ? 48 : 80;
            } else {
                i = z2 ? android.view.Gravity.START : android.view.Gravity.END;
            }
            if (extractSize.endsWith(WEIGHT_CENTERED_SUFFIX)) {
                i = 17;
            } else if (extractSize.endsWith("C")) {
                i = 16;
            }
            reverseRelativeLayout.setDefaultGravity(i);
            reverseRelativeLayout.setGravity(i);
            reverseRelativeLayout.addView(view, layoutParams);
            if (extractSize.contains("W")) {
                reverseRelativeLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(0, -1, java.lang.Float.parseFloat(extractSize.substring(0, extractSize.indexOf("W")))));
            } else {
                reverseRelativeLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams((int) convertDpToPx(this.mContext, java.lang.Float.parseFloat(extractSize.substring(0, extractSize.indexOf("A")))), -1));
            }
            reverseRelativeLayout.setClipChildren(false);
            reverseRelativeLayout.setClipToPadding(false);
            return reverseRelativeLayout;
        }
        float parseFloat = java.lang.Float.parseFloat(extractSize);
        view.getLayoutParams().width = (int) (r8.width * parseFloat);
        return view;
    }

    android.view.View createView(java.lang.String str, android.view.ViewGroup viewGroup, android.view.LayoutInflater layoutInflater) {
        java.lang.String extractButton = extractButton(str);
        if (LEFT.equals(extractButton)) {
            extractButton = extractButton(NAVSPACE);
        } else if (RIGHT.equals(extractButton)) {
            extractButton = extractButton(MENU_IME_ROTATE);
        }
        if (!"home".equals(extractButton)) {
            if (BACK.equals(extractButton)) {
                return layoutInflater.inflate(com.android.internal.R.layout.input_method_nav_back, viewGroup, false);
            }
            if (!RECENT.equals(extractButton) && !MENU_IME_ROTATE.equals(extractButton) && !NAVSPACE.equals(extractButton) && !"clipboard".equals(extractButton) && !CONTEXTUAL.equals(extractButton)) {
                if (HOME_HANDLE.equals(extractButton)) {
                    return layoutInflater.inflate(com.android.internal.R.layout.input_method_nav_home_handle, viewGroup, false);
                }
                if (IME_SWITCHER.equals(extractButton)) {
                    return layoutInflater.inflate(com.android.internal.R.layout.input_method_nav_ime_switcher, viewGroup, false);
                }
                extractButton.startsWith("key");
            }
        }
        return null;
    }

    private static java.lang.String extractSize(java.lang.String str) {
        if (!str.contains(SIZE_MOD_START)) {
            return null;
        }
        return str.substring(str.indexOf(SIZE_MOD_START) + 1, str.indexOf(SIZE_MOD_END));
    }

    private static java.lang.String extractButton(java.lang.String str) {
        if (!str.contains(SIZE_MOD_START)) {
            return str;
        }
        return str.substring(0, str.indexOf(SIZE_MOD_START));
    }

    private void addToDispatchers(android.view.View view) {
        if (this.mButtonDispatchers != null) {
            int indexOfKey = this.mButtonDispatchers.indexOfKey(view.getId());
            if (indexOfKey >= 0) {
                this.mButtonDispatchers.valueAt(indexOfKey).addView(view);
            }
            if (view instanceof android.view.ViewGroup) {
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    addToDispatchers(viewGroup.getChildAt(i));
                }
            }
        }
    }

    private void clearViews() {
        if (this.mButtonDispatchers != null) {
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                this.mButtonDispatchers.valueAt(i).clear();
            }
        }
        clearAllChildren((android.view.ViewGroup) this.mHorizontal.findViewById(com.android.internal.R.id.input_method_nav_buttons));
    }

    private void clearAllChildren(android.view.ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ((android.view.ViewGroup) viewGroup.getChildAt(i)).removeAllViews();
        }
    }

    private static float convertDpToPx(android.content.Context context, float f) {
        return f * context.getResources().getDisplayMetrics().density;
    }
}
