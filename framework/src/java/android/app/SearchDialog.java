package android.app;

/* loaded from: classes.dex */
public class SearchDialog extends android.app.Dialog {
    private static final boolean DBG = false;
    private static final java.lang.String IME_OPTION_NO_MICROPHONE = "nm";
    private static final java.lang.String INSTANCE_KEY_APPDATA = "data";
    private static final java.lang.String INSTANCE_KEY_COMPONENT = "comp";
    private static final java.lang.String INSTANCE_KEY_USER_QUERY = "uQry";
    private static final java.lang.String LOG_TAG = "SearchDialog";
    private static final int SEARCH_PLATE_LEFT_PADDING_NON_GLOBAL = 7;
    private android.content.Context mActivityContext;
    private android.widget.ImageView mAppIcon;
    private android.os.Bundle mAppSearchData;
    private android.widget.TextView mBadgeLabel;
    private android.view.View mCloseSearch;
    private android.content.BroadcastReceiver mConfChangeListener;
    private android.content.ComponentName mLaunchComponent;
    private final android.widget.SearchView.OnCloseListener mOnCloseListener;
    private final android.widget.SearchView.OnQueryTextListener mOnQueryChangeListener;
    private final android.widget.SearchView.OnSuggestionListener mOnSuggestionSelectionListener;
    private android.widget.AutoCompleteTextView mSearchAutoComplete;
    private int mSearchAutoCompleteImeOptions;
    private android.view.View mSearchPlate;
    private android.widget.SearchView mSearchView;
    private android.app.SearchableInfo mSearchable;
    private java.lang.String mUserQuery;
    private final android.content.Intent mVoiceAppSearchIntent;
    private final android.content.Intent mVoiceWebSearchIntent;
    private android.graphics.drawable.Drawable mWorkingSpinner;

    static int resolveDialogTheme(android.content.Context context) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        context.getTheme().resolveAttribute(com.android.internal.R.attr.searchDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    public SearchDialog(android.content.Context context, android.app.SearchManager searchManager) {
        super(context, resolveDialogTheme(context));
        this.mConfChangeListener = new android.content.BroadcastReceiver() { // from class: android.app.SearchDialog.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (intent.getAction().equals(android.content.Intent.ACTION_CONFIGURATION_CHANGED)) {
                    android.app.SearchDialog.this.onConfigurationChanged();
                }
            }
        };
        this.mOnCloseListener = new android.widget.SearchView.OnCloseListener() { // from class: android.app.SearchDialog.3
            @Override // android.widget.SearchView.OnCloseListener
            public boolean onClose() {
                return android.app.SearchDialog.this.onClosePressed();
            }
        };
        this.mOnQueryChangeListener = new android.widget.SearchView.OnQueryTextListener() { // from class: android.app.SearchDialog.4
            @Override // android.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(java.lang.String str) {
                android.app.SearchDialog.this.dismiss();
                return false;
            }

            @Override // android.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(java.lang.String str) {
                return false;
            }
        };
        this.mOnSuggestionSelectionListener = new android.widget.SearchView.OnSuggestionListener() { // from class: android.app.SearchDialog.5
            @Override // android.widget.SearchView.OnSuggestionListener
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override // android.widget.SearchView.OnSuggestionListener
            public boolean onSuggestionClick(int i) {
                android.app.SearchDialog.this.dismiss();
                return false;
            }
        };
        this.mVoiceWebSearchIntent = new android.content.Intent(android.speech.RecognizerIntent.ACTION_WEB_SEARCH);
        this.mVoiceWebSearchIntent.addFlags(268435456);
        this.mVoiceWebSearchIntent.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        this.mVoiceAppSearchIntent = new android.content.Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        this.mVoiceAppSearchIntent.addFlags(268435456);
    }

    @Override // android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.view.Window window = getWindow();
        android.view.WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.height = -1;
        attributes.gravity = 55;
        attributes.softInputMode = 16;
        window.setAttributes(attributes);
        setCanceledOnTouchOutside(true);
    }

    private void createContentView() {
        setContentView(com.android.internal.R.layout.search_bar);
        this.mSearchView = (android.widget.SearchView) findViewById(com.android.internal.R.id.search_view);
        this.mSearchView.setIconified(false);
        this.mSearchView.setOnCloseListener(this.mOnCloseListener);
        this.mSearchView.setOnQueryTextListener(this.mOnQueryChangeListener);
        this.mSearchView.setOnSuggestionListener(this.mOnSuggestionSelectionListener);
        this.mSearchView.onActionViewExpanded();
        this.mCloseSearch = findViewById(16908327);
        this.mCloseSearch.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.app.SearchDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.app.SearchDialog.this.dismiss();
            }
        });
        this.mBadgeLabel = (android.widget.TextView) this.mSearchView.findViewById(com.android.internal.R.id.search_badge);
        this.mSearchAutoComplete = (android.widget.AutoCompleteTextView) this.mSearchView.findViewById(com.android.internal.R.id.search_src_text);
        this.mAppIcon = (android.widget.ImageView) findViewById(com.android.internal.R.id.search_app_icon);
        this.mSearchPlate = this.mSearchView.findViewById(com.android.internal.R.id.search_plate);
        this.mWorkingSpinner = getContext().getDrawable(com.android.internal.R.drawable.search_spinner);
        setWorking(false);
        this.mBadgeLabel.setVisibility(8);
        this.mSearchAutoCompleteImeOptions = this.mSearchAutoComplete.getImeOptions();
    }

    public boolean show(java.lang.String str, boolean z, android.content.ComponentName componentName, android.os.Bundle bundle) {
        boolean doShow = doShow(str, z, componentName, bundle);
        if (doShow) {
            this.mSearchAutoComplete.showDropDownAfterLayout();
        }
        return doShow;
    }

    private boolean doShow(java.lang.String str, boolean z, android.content.ComponentName componentName, android.os.Bundle bundle) {
        if (!show(componentName, bundle)) {
            return false;
        }
        setUserQuery(str);
        if (z) {
            this.mSearchAutoComplete.selectAll();
            return true;
        }
        return true;
    }

    private boolean show(android.content.ComponentName componentName, android.os.Bundle bundle) {
        this.mSearchable = ((android.app.SearchManager) this.mContext.getSystemService("search")).getSearchableInfo(componentName);
        if (this.mSearchable == null) {
            return false;
        }
        this.mLaunchComponent = componentName;
        this.mAppSearchData = bundle;
        this.mActivityContext = this.mSearchable.getActivityContext(getContext());
        if (!isShowing()) {
            createContentView();
            this.mSearchView.setSearchableInfo(this.mSearchable);
            this.mSearchView.setAppSearchData(this.mAppSearchData);
            show();
        }
        updateUI();
        return true;
    }

    @Override // android.app.Dialog
    public void onStart() {
        super.onStart();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(android.content.Intent.ACTION_CONFIGURATION_CHANGED);
        getContext().registerReceiver(this.mConfChangeListener, intentFilter);
    }

    @Override // android.app.Dialog
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(this.mConfChangeListener);
        this.mLaunchComponent = null;
        this.mAppSearchData = null;
        this.mSearchable = null;
        this.mUserQuery = null;
    }

    public void setWorking(boolean z) {
        this.mWorkingSpinner.setAlpha(z ? 255 : 0);
        this.mWorkingSpinner.setVisible(z, false);
        this.mWorkingSpinner.invalidateSelf();
    }

    @Override // android.app.Dialog
    public android.os.Bundle onSaveInstanceState() {
        if (!isShowing()) {
            return null;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(INSTANCE_KEY_COMPONENT, this.mLaunchComponent);
        bundle.putBundle("data", this.mAppSearchData);
        bundle.putString(INSTANCE_KEY_USER_QUERY, this.mUserQuery);
        return bundle;
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(android.os.Bundle bundle) {
        if (bundle == null) {
            return;
        }
        doShow(bundle.getString(INSTANCE_KEY_USER_QUERY), false, (android.content.ComponentName) bundle.getParcelable(INSTANCE_KEY_COMPONENT, android.content.ComponentName.class), bundle.getBundle("data"));
    }

    public void onConfigurationChanged() {
        if (this.mSearchable != null && isShowing()) {
            updateSearchAppIcon();
            updateSearchBadge();
            if (isLandscapeMode(getContext())) {
                this.mSearchAutoComplete.setInputMethodMode(1);
                if (this.mSearchAutoComplete.isDropDownAlwaysVisible() || enoughToFilter()) {
                    this.mSearchAutoComplete.showDropDown();
                }
            }
        }
    }

    static boolean isLandscapeMode(android.content.Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private boolean enoughToFilter() {
        android.widget.Filterable filterable = (android.widget.Filterable) this.mSearchAutoComplete.getAdapter();
        if (filterable == null || filterable.getFilter() == null) {
            return false;
        }
        return this.mSearchAutoComplete.enoughToFilter();
    }

    private void updateUI() {
        if (this.mSearchable != null) {
            this.mDecor.setVisibility(0);
            updateSearchAutoComplete();
            updateSearchAppIcon();
            updateSearchBadge();
            int inputType = this.mSearchable.getInputType();
            if ((inputType & 15) == 1) {
                inputType &= -65537;
                if (this.mSearchable.getSuggestAuthority() != null) {
                    inputType |= 65536;
                }
            }
            this.mSearchAutoComplete.setInputType(inputType);
            this.mSearchAutoCompleteImeOptions = this.mSearchable.getImeOptions();
            this.mSearchAutoComplete.setImeOptions(this.mSearchAutoCompleteImeOptions);
            if (this.mSearchable.getVoiceSearchEnabled()) {
                this.mSearchAutoComplete.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
            } else {
                this.mSearchAutoComplete.setPrivateImeOptions(null);
            }
        }
    }

    private void updateSearchAutoComplete() {
        this.mSearchAutoComplete.setDropDownDismissedOnCompletion(false);
        this.mSearchAutoComplete.setForceIgnoreOutsideTouch(false);
    }

    private void updateSearchAppIcon() {
        android.graphics.drawable.Drawable defaultActivityIcon;
        android.content.pm.PackageManager packageManager = getContext().getPackageManager();
        try {
            defaultActivityIcon = packageManager.getApplicationIcon(packageManager.getActivityInfo(this.mLaunchComponent, 0).applicationInfo);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            defaultActivityIcon = packageManager.getDefaultActivityIcon();
            android.util.Log.w(LOG_TAG, this.mLaunchComponent + " not found, using generic app icon");
        }
        this.mAppIcon.lambda$setImageURIAsync$2(defaultActivityIcon);
        this.mAppIcon.setVisibility(0);
        this.mSearchPlate.setPadding(7, this.mSearchPlate.getPaddingTop(), this.mSearchPlate.getPaddingRight(), this.mSearchPlate.getPaddingBottom());
    }

    private void updateSearchBadge() {
        int i;
        android.graphics.drawable.Drawable drawable;
        java.lang.String str;
        if (this.mSearchable.useBadgeIcon()) {
            drawable = this.mActivityContext.getDrawable(this.mSearchable.getIconId());
            i = 0;
            str = null;
        } else if (!this.mSearchable.useBadgeLabel()) {
            i = 8;
            drawable = null;
            str = null;
        } else {
            i = 0;
            str = this.mActivityContext.getResources().getText(this.mSearchable.getLabelId()).toString();
            drawable = null;
        }
        this.mBadgeLabel.setCompoundDrawablesWithIntrinsicBounds(drawable, (android.graphics.drawable.Drawable) null, (android.graphics.drawable.Drawable) null, (android.graphics.drawable.Drawable) null);
        this.mBadgeLabel.lambda$setTextAsync$0(str);
        this.mBadgeLabel.setVisibility(i);
    }

    @Override // android.app.Dialog
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (!this.mSearchAutoComplete.isPopupShowing() && isOutOfBounds(this.mSearchPlate, motionEvent)) {
            cancel();
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    private boolean isOutOfBounds(android.view.View view, android.view.MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int scaledWindowTouchSlop = android.view.ViewConfiguration.get(this.mContext).getScaledWindowTouchSlop();
        int i = -scaledWindowTouchSlop;
        return x < i || y < i || x > view.getWidth() + scaledWindowTouchSlop || y > view.getHeight() + scaledWindowTouchSlop;
    }

    @Override // android.app.Dialog
    public void hide() {
        if (isShowing()) {
            android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
            super.hide();
        }
    }

    public void launchQuerySearch() {
        launchQuerySearch(0, null);
    }

    protected void launchQuerySearch(int i, java.lang.String str) {
        launchIntent(createIntent(android.content.Intent.ACTION_SEARCH, null, null, this.mSearchAutoComplete.getText().toString(), i, str));
    }

    private void launchIntent(android.content.Intent intent) {
        if (intent == null) {
            return;
        }
        android.util.Log.d(LOG_TAG, "launching " + intent);
        try {
            getContext().startActivity(intent);
            dismiss();
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(LOG_TAG, "Failed launch activity: " + intent, e);
        }
    }

    public void setListSelection(int i) {
        this.mSearchAutoComplete.setListSelection(i);
    }

    private android.content.Intent createIntent(java.lang.String str, android.net.Uri uri, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) {
        android.content.Intent intent = new android.content.Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra(android.app.SearchManager.USER_QUERY, this.mUserQuery);
        if (str3 != null) {
            intent.putExtra("query", str3);
        }
        if (str2 != null) {
            intent.putExtra(android.app.SearchManager.EXTRA_DATA_KEY, str2);
        }
        if (this.mAppSearchData != null) {
            intent.putExtra(android.app.SearchManager.APP_DATA, this.mAppSearchData);
        }
        if (i != 0) {
            intent.putExtra(android.app.SearchManager.ACTION_KEY, i);
            intent.putExtra(android.app.SearchManager.ACTION_MSG, str4);
        }
        intent.setComponent(this.mSearchable.getSearchActivity());
        return intent;
    }

    public static class SearchBar extends android.widget.LinearLayout {
        public SearchBar(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public SearchBar(android.content.Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback, int i) {
            if (i != 0) {
                return super.startActionModeForChild(view, callback, i);
            }
            return null;
        }
    }

    private boolean isEmpty(android.widget.AutoCompleteTextView autoCompleteTextView) {
        return android.text.TextUtils.getTrimmedLength(autoCompleteTextView.getText()) == 0;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager != null && inputMethodManager.isFullscreenMode() && inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0)) {
            return;
        }
        cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onClosePressed() {
        if (isEmpty(this.mSearchAutoComplete)) {
            dismiss();
            return true;
        }
        return false;
    }

    private void setUserQuery(java.lang.String str) {
        if (str == null) {
            str = "";
        }
        this.mUserQuery = str;
        this.mSearchAutoComplete.lambda$setTextAsync$0(str);
        this.mSearchAutoComplete.setSelection(str.length());
    }
}
