package android.widget;

/* loaded from: classes4.dex */
public class SearchView extends android.widget.LinearLayout implements android.view.CollapsibleActionView {
    private static final boolean DBG = false;
    private static final java.lang.String IME_OPTION_NO_MICROPHONE = "nm";
    private static final java.lang.String LOG_TAG = "SearchView";
    private android.os.Bundle mAppSearchData;
    private boolean mClearingFocus;
    private final android.widget.ImageView mCloseButton;
    private final android.widget.ImageView mCollapsedIcon;
    private int mCollapsedImeOptions;
    private final java.lang.CharSequence mDefaultQueryHint;
    private final android.view.View mDropDownAnchor;
    private boolean mExpandedInActionView;
    private final android.widget.ImageView mGoButton;
    private boolean mIconified;
    private boolean mIconifiedByDefault;
    private int mMaxWidth;
    private java.lang.CharSequence mOldQueryText;
    private final android.view.View.OnClickListener mOnClickListener;
    private android.widget.SearchView.OnCloseListener mOnCloseListener;
    private final android.widget.TextView.OnEditorActionListener mOnEditorActionListener;
    private final android.widget.AdapterView.OnItemClickListener mOnItemClickListener;
    private final android.widget.AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private android.widget.SearchView.OnQueryTextListener mOnQueryChangeListener;
    private android.view.View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private android.view.View.OnClickListener mOnSearchClickListener;
    private android.widget.SearchView.OnSuggestionListener mOnSuggestionListener;
    private final java.util.WeakHashMap<java.lang.String, android.graphics.drawable.Drawable.ConstantState> mOutsideDrawablesCache;
    private java.lang.CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private java.lang.Runnable mReleaseCursorRunnable;
    private final android.widget.ImageView mSearchButton;
    private final android.view.View mSearchEditFrame;
    private final android.graphics.drawable.Drawable mSearchHintIcon;
    private final android.view.View mSearchPlate;
    private final android.widget.SearchView.SearchAutoComplete mSearchSrcTextView;
    private android.graphics.Rect mSearchSrcTextViewBounds;
    private android.graphics.Rect mSearchSrtTextViewBoundsExpanded;
    private android.app.SearchableInfo mSearchable;
    private final android.view.View mSubmitArea;
    private boolean mSubmitButtonEnabled;
    private final int mSuggestionCommitIconResId;
    private final int mSuggestionRowLayout;
    private android.widget.CursorAdapter mSuggestionsAdapter;
    private int[] mTemp;
    private int[] mTemp2;
    android.view.View.OnKeyListener mTextKeyListener;
    private android.text.TextWatcher mTextWatcher;
    private android.widget.SearchView.UpdatableTouchDelegate mTouchDelegate;
    private java.lang.Runnable mUpdateDrawableStateRunnable;
    private java.lang.CharSequence mUserQuery;
    private final android.content.Intent mVoiceAppSearchIntent;
    private final android.widget.ImageView mVoiceButton;
    private boolean mVoiceButtonEnabled;
    private final android.content.Intent mVoiceWebSearchIntent;

    public interface OnCloseListener {
        boolean onClose();
    }

    public interface OnQueryTextListener {
        boolean onQueryTextChange(java.lang.String str);

        boolean onQueryTextSubmit(java.lang.String str);
    }

    public interface OnSuggestionListener {
        boolean onSuggestionClick(int i);

        boolean onSuggestionSelect(int i);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.SearchView> {
        private int mIconifiedByDefaultId;
        private int mIconifiedId;
        private int mMaxWidthId;
        private boolean mPropertiesMapped = false;
        private int mQueryHintId;
        private int mQueryId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mIconifiedId = propertyMapper.mapBoolean("iconified", 0);
            this.mIconifiedByDefaultId = propertyMapper.mapBoolean("iconifiedByDefault", 16843514);
            this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
            this.mQueryId = propertyMapper.mapObject("query", 0);
            this.mQueryHintId = propertyMapper.mapObject("queryHint", 16843608);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.SearchView searchView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mIconifiedId, searchView.isIconified());
            propertyReader.readBoolean(this.mIconifiedByDefaultId, searchView.isIconifiedByDefault());
            propertyReader.readInt(this.mMaxWidthId, searchView.getMaxWidth());
            propertyReader.readObject(this.mQueryId, searchView.getQuery());
            propertyReader.readObject(this.mQueryHintId, searchView.getQueryHint());
        }
    }

    public SearchView(android.content.Context context) {
        this(context, null);
    }

    public SearchView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843904);
    }

    public SearchView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SearchView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSearchSrcTextViewBounds = new android.graphics.Rect();
        this.mSearchSrtTextViewBoundsExpanded = new android.graphics.Rect();
        this.mTemp = new int[2];
        this.mTemp2 = new int[2];
        this.mUpdateDrawableStateRunnable = new java.lang.Runnable() { // from class: android.widget.SearchView.1
            @Override // java.lang.Runnable
            public void run() {
                android.widget.SearchView.this.updateFocusedState();
            }
        };
        this.mReleaseCursorRunnable = new java.lang.Runnable() { // from class: android.widget.SearchView.2
            @Override // java.lang.Runnable
            public void run() {
                if (android.widget.SearchView.this.mSuggestionsAdapter != null && (android.widget.SearchView.this.mSuggestionsAdapter instanceof android.widget.SuggestionsAdapter)) {
                    android.widget.SearchView.this.mSuggestionsAdapter.changeCursor(null);
                }
            }
        };
        this.mOutsideDrawablesCache = new java.util.WeakHashMap<>();
        this.mOnClickListener = new android.view.View.OnClickListener() { // from class: android.widget.SearchView.5
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                if (view == android.widget.SearchView.this.mSearchButton) {
                    android.widget.SearchView.this.onSearchClicked();
                    return;
                }
                if (view == android.widget.SearchView.this.mCloseButton) {
                    android.widget.SearchView.this.onCloseClicked();
                    return;
                }
                if (view == android.widget.SearchView.this.mGoButton) {
                    android.widget.SearchView.this.onSubmitQuery();
                } else if (view == android.widget.SearchView.this.mVoiceButton) {
                    android.widget.SearchView.this.onVoiceClicked();
                } else if (view == android.widget.SearchView.this.mSearchSrcTextView) {
                    android.widget.SearchView.this.forceSuggestionQuery();
                }
            }
        };
        this.mTextKeyListener = new android.view.View.OnKeyListener() { // from class: android.widget.SearchView.6
            @Override // android.view.View.OnKeyListener
            public boolean onKey(android.view.View view, int i3, android.view.KeyEvent keyEvent) {
                android.app.SearchableInfo.ActionKeyInfo findActionKey;
                if (android.widget.SearchView.this.mSearchable == null) {
                    return false;
                }
                if (android.widget.SearchView.this.mSearchSrcTextView.isPopupShowing() && android.widget.SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                    return android.widget.SearchView.this.onSuggestionsKey(view, i3, keyEvent);
                }
                if (!android.widget.SearchView.this.mSearchSrcTextView.isEmpty() && keyEvent.hasNoModifiers()) {
                    if (keyEvent.getAction() == 1 && (i3 == 66 || i3 == 160)) {
                        view.cancelLongPress();
                        android.widget.SearchView.this.launchQuerySearch(0, null, android.widget.SearchView.this.mSearchSrcTextView.getText().toString());
                        return true;
                    }
                    if (keyEvent.getAction() == 0 && (findActionKey = android.widget.SearchView.this.mSearchable.findActionKey(i3)) != null && findActionKey.getQueryActionMsg() != null) {
                        android.widget.SearchView.this.launchQuerySearch(i3, findActionKey.getQueryActionMsg(), android.widget.SearchView.this.mSearchSrcTextView.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        };
        this.mOnEditorActionListener = new android.widget.TextView.OnEditorActionListener() { // from class: android.widget.SearchView.7
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(android.widget.TextView textView, int i3, android.view.KeyEvent keyEvent) {
                android.widget.SearchView.this.onSubmitQuery();
                return true;
            }
        };
        this.mOnItemClickListener = new android.widget.AdapterView.OnItemClickListener() { // from class: android.widget.SearchView.8
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i3, long j) {
                android.widget.SearchView.this.onItemClicked(i3, 0, null);
            }
        };
        this.mOnItemSelectedListener = new android.widget.AdapterView.OnItemSelectedListener() { // from class: android.widget.SearchView.9
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i3, long j) {
                android.widget.SearchView.this.onItemSelected(i3);
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
            }
        };
        this.mTextWatcher = new android.text.TextWatcher() { // from class: android.widget.SearchView.10
            @Override // android.text.TextWatcher
            public void beforeTextChanged(java.lang.CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(java.lang.CharSequence charSequence, int i3, int i4, int i5) {
                android.widget.SearchView.this.onTextChanged(charSequence);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(android.text.Editable editable) {
            }
        };
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.SearchView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.SearchView, attributeSet, obtainStyledAttributes, i, i2);
        ((android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(obtainStyledAttributes.getResourceId(0, com.android.internal.R.layout.search_view), (android.view.ViewGroup) this, true);
        this.mSearchSrcTextView = (android.widget.SearchView.SearchAutoComplete) findViewById(com.android.internal.R.id.search_src_text);
        this.mSearchSrcTextView.setSearchView(this);
        this.mSearchEditFrame = findViewById(com.android.internal.R.id.search_edit_frame);
        this.mSearchPlate = findViewById(com.android.internal.R.id.search_plate);
        this.mSubmitArea = findViewById(com.android.internal.R.id.submit_area);
        this.mSearchButton = (android.widget.ImageView) findViewById(com.android.internal.R.id.search_button);
        this.mGoButton = (android.widget.ImageView) findViewById(com.android.internal.R.id.search_go_btn);
        this.mCloseButton = (android.widget.ImageView) findViewById(com.android.internal.R.id.search_close_btn);
        this.mVoiceButton = (android.widget.ImageView) findViewById(com.android.internal.R.id.search_voice_btn);
        this.mCollapsedIcon = (android.widget.ImageView) findViewById(com.android.internal.R.id.search_mag_icon);
        this.mSearchPlate.setBackground(obtainStyledAttributes.getDrawable(12));
        this.mSubmitArea.setBackground(obtainStyledAttributes.getDrawable(13));
        this.mSearchButton.lambda$setImageURIAsync$2(obtainStyledAttributes.getDrawable(8));
        this.mGoButton.lambda$setImageURIAsync$2(obtainStyledAttributes.getDrawable(7));
        this.mCloseButton.lambda$setImageURIAsync$2(obtainStyledAttributes.getDrawable(6));
        this.mVoiceButton.lambda$setImageURIAsync$2(obtainStyledAttributes.getDrawable(9));
        this.mCollapsedIcon.lambda$setImageURIAsync$2(obtainStyledAttributes.getDrawable(8));
        if (obtainStyledAttributes.hasValueOrEmpty(14)) {
            this.mSearchHintIcon = obtainStyledAttributes.getDrawable(14);
        } else {
            this.mSearchHintIcon = obtainStyledAttributes.getDrawable(8);
        }
        this.mSuggestionRowLayout = obtainStyledAttributes.getResourceId(11, com.android.internal.R.layout.search_dropdown_item_icons_2line);
        this.mSuggestionCommitIconResId = obtainStyledAttributes.getResourceId(10, 0);
        this.mSearchButton.setOnClickListener(this.mOnClickListener);
        this.mCloseButton.setOnClickListener(this.mOnClickListener);
        this.mGoButton.setOnClickListener(this.mOnClickListener);
        this.mVoiceButton.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.addTextChangedListener(this.mTextWatcher);
        this.mSearchSrcTextView.setOnEditorActionListener(this.mOnEditorActionListener);
        this.mSearchSrcTextView.setOnItemClickListener(this.mOnItemClickListener);
        this.mSearchSrcTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
        this.mSearchSrcTextView.setOnKeyListener(this.mTextKeyListener);
        this.mSearchSrcTextView.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() { // from class: android.widget.SearchView.3
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(android.view.View view, boolean z) {
                if (android.widget.SearchView.this.mOnQueryTextFocusChangeListener != null) {
                    android.widget.SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange(android.widget.SearchView.this, z);
                }
            }
        });
        setIconifiedByDefault(obtainStyledAttributes.getBoolean(4, true));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, -1);
        if (dimensionPixelSize != -1) {
            setMaxWidth(dimensionPixelSize);
        }
        this.mDefaultQueryHint = obtainStyledAttributes.getText(15);
        this.mQueryHint = obtainStyledAttributes.getText(5);
        int i3 = obtainStyledAttributes.getInt(3, -1);
        if (i3 != -1) {
            setImeOptions(i3);
        }
        int i4 = obtainStyledAttributes.getInt(2, -1);
        if (i4 != -1) {
            setInputType(i4);
        }
        if (getFocusable() == 16) {
            setFocusable(1);
        }
        obtainStyledAttributes.recycle();
        this.mVoiceWebSearchIntent = new android.content.Intent(android.speech.RecognizerIntent.ACTION_WEB_SEARCH);
        this.mVoiceWebSearchIntent.addFlags(268435456);
        this.mVoiceWebSearchIntent.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        this.mVoiceAppSearchIntent = new android.content.Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        this.mVoiceAppSearchIntent.addFlags(268435456);
        this.mDropDownAnchor = findViewById(this.mSearchSrcTextView.getDropDownAnchor());
        if (this.mDropDownAnchor != null) {
            this.mDropDownAnchor.addOnLayoutChangeListener(new android.view.View.OnLayoutChangeListener() { // from class: android.widget.SearchView.4
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(android.view.View view, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
                    android.widget.SearchView.this.adjustDropDownSizeAndPosition();
                }
            });
        }
        updateViewsVisibility(this.mIconifiedByDefault);
        updateQueryHint();
    }

    int getSuggestionRowLayout() {
        return this.mSuggestionRowLayout;
    }

    int getSuggestionCommitIconResId() {
        return this.mSuggestionCommitIconResId;
    }

    public void setSearchableInfo(android.app.SearchableInfo searchableInfo) {
        this.mSearchable = searchableInfo;
        if (this.mSearchable != null) {
            updateSearchAutoComplete();
            updateQueryHint();
        }
        this.mVoiceButtonEnabled = hasVoiceSearch();
        if (this.mVoiceButtonEnabled) {
            this.mSearchSrcTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
        }
        updateViewsVisibility(isIconified());
    }

    public void setAppSearchData(android.os.Bundle bundle) {
        this.mAppSearchData = bundle;
    }

    public void setImeOptions(int i) {
        this.mSearchSrcTextView.setImeOptions(i);
    }

    public int getImeOptions() {
        return this.mSearchSrcTextView.getImeOptions();
    }

    public void setInputType(int i) {
        this.mSearchSrcTextView.setInputType(i);
    }

    public int getInputType() {
        return this.mSearchSrcTextView.getInputType();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean requestFocus(int i, android.graphics.Rect rect) {
        if (this.mClearingFocus || !isFocusable()) {
            return false;
        }
        if (!isIconified()) {
            if (i == 1) {
                android.view.View focusSearch = focusSearch(1);
                if (focusSearch != null) {
                    return focusSearch.requestFocus(1, rect);
                }
                return false;
            }
            boolean requestFocus = this.mSearchSrcTextView.requestFocus(i, rect);
            if (requestFocus) {
                updateViewsVisibility(false);
            }
            return requestFocus;
        }
        return super.requestFocus(i, rect);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void clearFocus() {
        this.mClearingFocus = true;
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mSearchSrcTextView.setImeVisibility(false);
        this.mClearingFocus = false;
    }

    public void setOnQueryTextListener(android.widget.SearchView.OnQueryTextListener onQueryTextListener) {
        this.mOnQueryChangeListener = onQueryTextListener;
    }

    public void setOnCloseListener(android.widget.SearchView.OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener;
    }

    public void setOnQueryTextFocusChangeListener(android.view.View.OnFocusChangeListener onFocusChangeListener) {
        this.mOnQueryTextFocusChangeListener = onFocusChangeListener;
    }

    public void setOnSuggestionListener(android.widget.SearchView.OnSuggestionListener onSuggestionListener) {
        this.mOnSuggestionListener = onSuggestionListener;
    }

    public void setOnSearchClickListener(android.view.View.OnClickListener onClickListener) {
        this.mOnSearchClickListener = onClickListener;
    }

    public java.lang.CharSequence getQuery() {
        return this.mSearchSrcTextView.getText();
    }

    public void setQuery(java.lang.CharSequence charSequence, boolean z) {
        this.mSearchSrcTextView.lambda$setTextAsync$0(charSequence);
        if (charSequence != null) {
            this.mSearchSrcTextView.setSelection(this.mSearchSrcTextView.length());
            this.mUserQuery = charSequence;
        }
        if (z && !android.text.TextUtils.isEmpty(charSequence)) {
            onSubmitQuery();
        }
    }

    public void setQueryHint(java.lang.CharSequence charSequence) {
        this.mQueryHint = charSequence;
        updateQueryHint();
    }

    public java.lang.CharSequence getQueryHint() {
        if (this.mQueryHint != null) {
            return this.mQueryHint;
        }
        if (this.mSearchable != null && this.mSearchable.getHintId() != 0) {
            return getContext().getText(this.mSearchable.getHintId());
        }
        return this.mDefaultQueryHint;
    }

    public void setIconifiedByDefault(boolean z) {
        if (this.mIconifiedByDefault == z) {
            return;
        }
        this.mIconifiedByDefault = z;
        updateViewsVisibility(z);
        updateQueryHint();
    }

    @java.lang.Deprecated
    public boolean isIconfiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public boolean isIconifiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public void setIconified(boolean z) {
        if (z) {
            onCloseClicked();
        } else {
            onSearchClicked();
        }
    }

    public boolean isIconified() {
        return this.mIconified;
    }

    public void setSubmitButtonEnabled(boolean z) {
        this.mSubmitButtonEnabled = z;
        updateViewsVisibility(isIconified());
    }

    public boolean isSubmitButtonEnabled() {
        return this.mSubmitButtonEnabled;
    }

    public void setQueryRefinementEnabled(boolean z) {
        this.mQueryRefinement = z;
        if (this.mSuggestionsAdapter instanceof android.widget.SuggestionsAdapter) {
            ((android.widget.SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(z ? 2 : 1);
        }
    }

    public boolean isQueryRefinementEnabled() {
        return this.mQueryRefinement;
    }

    public void setSuggestionsAdapter(android.widget.CursorAdapter cursorAdapter) {
        this.mSuggestionsAdapter = cursorAdapter;
        this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
    }

    public android.widget.CursorAdapter getSuggestionsAdapter() {
        return this.mSuggestionsAdapter;
    }

    public void setMaxWidth(int i) {
        this.mMaxWidth = i;
        requestLayout();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (isIconified()) {
            super.onMeasure(i, i2);
            return;
        }
        int mode = android.view.View.MeasureSpec.getMode(i);
        int size = android.view.View.MeasureSpec.getSize(i);
        switch (mode) {
            case Integer.MIN_VALUE:
                if (this.mMaxWidth > 0) {
                    size = java.lang.Math.min(this.mMaxWidth, size);
                    break;
                } else {
                    size = java.lang.Math.min(getPreferredWidth(), size);
                    break;
                }
            case 0:
                if (this.mMaxWidth <= 0) {
                    size = getPreferredWidth();
                    break;
                } else {
                    size = this.mMaxWidth;
                    break;
                }
            case 1073741824:
                if (this.mMaxWidth > 0) {
                    size = java.lang.Math.min(this.mMaxWidth, size);
                    break;
                }
                break;
        }
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        switch (mode2) {
            case Integer.MIN_VALUE:
                size2 = java.lang.Math.min(getPreferredHeight(), size2);
                break;
            case 0:
                size2 = getPreferredHeight();
                break;
        }
        super.onMeasure(android.view.View.MeasureSpec.makeMeasureSpec(size, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            getChildBoundsWithinSearchView(this.mSearchSrcTextView, this.mSearchSrcTextViewBounds);
            this.mSearchSrtTextViewBoundsExpanded.set(this.mSearchSrcTextViewBounds.left, 0, this.mSearchSrcTextViewBounds.right, i4 - i2);
            if (this.mTouchDelegate == null) {
                this.mTouchDelegate = new android.widget.SearchView.UpdatableTouchDelegate(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds, this.mSearchSrcTextView);
                setTouchDelegate(this.mTouchDelegate);
            } else {
                this.mTouchDelegate.setBounds(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds);
            }
        }
    }

    private void getChildBoundsWithinSearchView(android.view.View view, android.graphics.Rect rect) {
        view.getLocationInWindow(this.mTemp);
        getLocationInWindow(this.mTemp2);
        int i = this.mTemp[1] - this.mTemp2[1];
        int i2 = this.mTemp[0] - this.mTemp2[0];
        rect.set(i2, i, view.getWidth() + i2, view.getHeight() + i);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(com.android.internal.R.dimen.search_view_preferred_width);
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(com.android.internal.R.dimen.search_view_preferred_height);
    }

    private void updateViewsVisibility(boolean z) {
        this.mIconified = z;
        int i = 0;
        int i2 = z ? 0 : 8;
        boolean z2 = !android.text.TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        this.mSearchButton.setVisibility(i2);
        updateSubmitButton(z2);
        this.mSearchEditFrame.setVisibility(z ? 8 : 0);
        if (this.mCollapsedIcon.getDrawable() == null || this.mIconifiedByDefault) {
            i = 8;
        }
        this.mCollapsedIcon.setVisibility(i);
        updateCloseButton();
        updateVoiceButton(!z2);
        updateSubmitArea();
    }

    private boolean hasVoiceSearch() {
        android.content.Intent intent;
        if (this.mSearchable != null && this.mSearchable.getVoiceSearchEnabled()) {
            if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
                intent = this.mVoiceWebSearchIntent;
            } else if (!this.mSearchable.getVoiceSearchLaunchRecognizer()) {
                intent = null;
            } else {
                intent = this.mVoiceAppSearchIntent;
            }
            return (intent == null || getContext().getPackageManager().resolveActivity(intent, 65536) == null) ? false : true;
        }
        return false;
    }

    private boolean isSubmitAreaEnabled() {
        return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !isIconified();
    }

    private void updateSubmitButton(boolean z) {
        int i;
        if (this.mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus() && (z || !this.mVoiceButtonEnabled)) {
            i = 0;
        } else {
            i = 8;
        }
        this.mGoButton.setVisibility(i);
    }

    private void updateSubmitArea() {
        int i;
        if (isSubmitAreaEnabled() && (this.mGoButton.getVisibility() == 0 || this.mVoiceButton.getVisibility() == 0)) {
            i = 0;
        } else {
            i = 8;
        }
        this.mSubmitArea.setVisibility(i);
    }

    private void updateCloseButton() {
        boolean z = true;
        boolean z2 = !android.text.TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        if (!z2 && (!this.mIconifiedByDefault || this.mExpandedInActionView)) {
            z = false;
        }
        this.mCloseButton.setVisibility(z ? 0 : 8);
        android.graphics.drawable.Drawable drawable = this.mCloseButton.getDrawable();
        if (drawable != null) {
            drawable.setState(z2 ? ENABLED_STATE_SET : EMPTY_STATE_SET);
        }
    }

    private void postUpdateFocusedState() {
        post(this.mUpdateDrawableStateRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFocusedState() {
        int[] iArr = this.mSearchSrcTextView.hasFocus() ? FOCUSED_STATE_SET : EMPTY_STATE_SET;
        android.graphics.drawable.Drawable background = this.mSearchPlate.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        android.graphics.drawable.Drawable background2 = this.mSubmitArea.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.mUpdateDrawableStateRunnable);
        post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    void onQueryRefine(java.lang.CharSequence charSequence) {
        setQuery(charSequence);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (this.mSearchable == null) {
            return false;
        }
        android.app.SearchableInfo.ActionKeyInfo findActionKey = this.mSearchable.findActionKey(i);
        if (findActionKey != null && findActionKey.getQueryActionMsg() != null) {
            launchQuerySearch(i, findActionKey.getQueryActionMsg(), this.mSearchSrcTextView.getText().toString());
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onSuggestionsKey(android.view.View view, int i, android.view.KeyEvent keyEvent) {
        android.app.SearchableInfo.ActionKeyInfo findActionKey;
        int listSelection;
        java.lang.String actionKeyMessage;
        if (this.mSearchable != null && this.mSuggestionsAdapter != null && keyEvent.getAction() == 0 && keyEvent.hasNoModifiers()) {
            if (i == 66 || i == 84 || i == 61) {
                return onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
            }
            if (i == 21 || i == 22) {
                this.mSearchSrcTextView.setSelection(i == 21 ? 0 : this.mSearchSrcTextView.length());
                this.mSearchSrcTextView.setListSelection(0);
                this.mSearchSrcTextView.clearListSelection();
                this.mSearchSrcTextView.ensureImeVisible(true);
                return true;
            }
            if ((i != 19 || this.mSearchSrcTextView.getListSelection() != 0) && (findActionKey = this.mSearchable.findActionKey(i)) != null && ((findActionKey.getSuggestActionMsg() != null || findActionKey.getSuggestActionMsgColumn() != null) && (listSelection = this.mSearchSrcTextView.getListSelection()) != -1)) {
                android.database.Cursor cursor = this.mSuggestionsAdapter.getCursor();
                if (cursor.moveToPosition(listSelection) && (actionKeyMessage = getActionKeyMessage(cursor, findActionKey)) != null && actionKeyMessage.length() > 0) {
                    return onItemClicked(listSelection, i, actionKeyMessage);
                }
            }
        }
        return false;
    }

    private static java.lang.String getActionKeyMessage(android.database.Cursor cursor, android.app.SearchableInfo.ActionKeyInfo actionKeyInfo) {
        java.lang.String str;
        java.lang.String suggestActionMsgColumn = actionKeyInfo.getSuggestActionMsgColumn();
        if (suggestActionMsgColumn == null) {
            str = null;
        } else {
            str = android.widget.SuggestionsAdapter.getColumnString(cursor, suggestActionMsgColumn);
        }
        if (str == null) {
            return actionKeyInfo.getSuggestActionMsg();
        }
        return str;
    }

    private java.lang.CharSequence getDecoratedHint(java.lang.CharSequence charSequence) {
        if (!this.mIconifiedByDefault || this.mSearchHintIcon == null) {
            return charSequence;
        }
        int textSize = (int) (this.mSearchSrcTextView.getTextSize() * 1.25d);
        this.mSearchHintIcon.setBounds(0, 0, textSize, textSize);
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder("   ");
        spannableStringBuilder.setSpan(new android.text.style.ImageSpan(this.mSearchHintIcon), 1, 2, 33);
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private void updateQueryHint() {
        java.lang.CharSequence queryHint = getQueryHint();
        android.widget.SearchView.SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        if (queryHint == null) {
            queryHint = "";
        }
        searchAutoComplete.setHint(getDecoratedHint(queryHint));
    }

    private void updateSearchAutoComplete() {
        this.mSearchSrcTextView.setDropDownAnimationStyle(0);
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
        int inputType = this.mSearchable.getInputType();
        if ((inputType & 15) == 1) {
            inputType &= -65537;
            if (this.mSearchable.getSuggestAuthority() != null) {
                inputType = inputType | 65536 | 524288;
            }
        }
        this.mSearchSrcTextView.setInputType(inputType);
        if (this.mSuggestionsAdapter != null) {
            this.mSuggestionsAdapter.changeCursor(null);
        }
        if (this.mSearchable.getSuggestAuthority() != null) {
            this.mSuggestionsAdapter = new android.widget.SuggestionsAdapter(getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
            this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
            ((android.widget.SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(this.mQueryRefinement ? 2 : 1);
        }
    }

    private void updateVoiceButton(boolean z) {
        int i = 8;
        if (this.mVoiceButtonEnabled && !isIconified() && z) {
            this.mGoButton.setVisibility(8);
            i = 0;
        }
        this.mVoiceButton.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTextChanged(java.lang.CharSequence charSequence) {
        android.text.Editable text = this.mSearchSrcTextView.getText();
        this.mUserQuery = text;
        boolean z = !android.text.TextUtils.isEmpty(text);
        updateSubmitButton(z);
        updateVoiceButton(!z);
        updateCloseButton();
        updateSubmitArea();
        if (this.mOnQueryChangeListener != null && !android.text.TextUtils.equals(charSequence, this.mOldQueryText)) {
            this.mOnQueryChangeListener.onQueryTextChange(charSequence.toString());
        }
        this.mOldQueryText = charSequence.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubmitQuery() {
        android.text.Editable text = this.mSearchSrcTextView.getText();
        if (text != null && android.text.TextUtils.getTrimmedLength(text) > 0) {
            if (this.mOnQueryChangeListener == null || !this.mOnQueryChangeListener.onQueryTextSubmit(text.toString())) {
                if (this.mSearchable != null) {
                    launchQuerySearch(0, null, text.toString());
                }
                this.mSearchSrcTextView.setImeVisibility(false);
                dismissSuggestions();
            }
        }
    }

    private void dismissSuggestions() {
        this.mSearchSrcTextView.dismissDropDown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCloseClicked() {
        if (android.text.TextUtils.isEmpty(this.mSearchSrcTextView.getText())) {
            if (this.mIconifiedByDefault) {
                if (this.mOnCloseListener == null || !this.mOnCloseListener.onClose()) {
                    clearFocus();
                    updateViewsVisibility(true);
                    return;
                }
                return;
            }
            return;
        }
        this.mSearchSrcTextView.lambda$setTextAsync$0("");
        this.mSearchSrcTextView.requestFocus();
        this.mSearchSrcTextView.setImeVisibility(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSearchClicked() {
        updateViewsVisibility(false);
        this.mSearchSrcTextView.requestFocus();
        this.mSearchSrcTextView.setImeVisibility(true);
        if (this.mOnSearchClickListener != null) {
            this.mOnSearchClickListener.onClick(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVoiceClicked() {
        if (this.mSearchable == null) {
            return;
        }
        android.app.SearchableInfo searchableInfo = this.mSearchable;
        try {
            if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                getContext().startActivity(createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchableInfo));
            } else if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                getContext().startActivity(createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchableInfo));
            }
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Log.w(LOG_TAG, "Could not find voice search activity");
        }
    }

    void onTextFocusChanged() {
        updateViewsVisibility(isIconified());
        postUpdateFocusedState();
        if (this.mSearchSrcTextView.hasFocus()) {
            forceSuggestionQuery();
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        postUpdateFocusedState();
    }

    @Override // android.view.CollapsibleActionView
    public void onActionViewCollapsed() {
        setQuery("", false);
        clearFocus();
        updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    @Override // android.view.CollapsibleActionView
    public void onActionViewExpanded() {
        if (this.mExpandedInActionView) {
            return;
        }
        this.mExpandedInActionView = true;
        this.mCollapsedImeOptions = this.mSearchSrcTextView.getImeOptions();
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions | 33554432);
        this.mSearchSrcTextView.lambda$setTextAsync$0("");
        setIconified(false);
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.SearchView.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.SearchView.SavedState>() { // from class: android.widget.SearchView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.SearchView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.SearchView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.SearchView.SavedState[] newArray(int i) {
                return new android.widget.SearchView.SavedState[i];
            }
        };
        boolean isIconified;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.isIconified = ((java.lang.Boolean) parcel.readValue(null)).booleanValue();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(java.lang.Boolean.valueOf(this.isIconified));
        }

        public java.lang.String toString() {
            return "SearchView.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " isIconified=" + this.isIconified + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.widget.SearchView.SavedState savedState = new android.widget.SearchView.SavedState(super.onSaveInstanceState());
        savedState.isIconified = isIconified();
        return savedState;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.widget.SearchView.SavedState savedState = (android.widget.SearchView.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        updateViewsVisibility(savedState.isIconified);
        requestLayout();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.SearchView.class.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustDropDownSizeAndPosition() {
        int i;
        int i2;
        if (this.mDropDownAnchor.getWidth() > 1) {
            android.content.res.Resources resources = getContext().getResources();
            int paddingLeft = this.mSearchPlate.getPaddingLeft();
            android.graphics.Rect rect = new android.graphics.Rect();
            boolean isLayoutRtl = isLayoutRtl();
            if (this.mIconifiedByDefault) {
                i = resources.getDimensionPixelSize(com.android.internal.R.dimen.dropdownitem_icon_width) + resources.getDimensionPixelSize(com.android.internal.R.dimen.dropdownitem_text_padding_left);
            } else {
                i = 0;
            }
            this.mSearchSrcTextView.getDropDownBackground().getPadding(rect);
            if (isLayoutRtl) {
                i2 = -rect.left;
            } else {
                i2 = paddingLeft - (rect.left + i);
            }
            this.mSearchSrcTextView.setDropDownHorizontalOffset(i2);
            this.mSearchSrcTextView.setDropDownWidth((((this.mDropDownAnchor.getWidth() + rect.left) + rect.right) + i) - paddingLeft);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onItemClicked(int i, int i2, java.lang.String str) {
        if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionClick(i)) {
            return false;
        }
        launchSuggestion(i, 0, null);
        this.mSearchSrcTextView.setImeVisibility(false);
        dismissSuggestions();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onItemSelected(int i) {
        if (this.mOnSuggestionListener == null || !this.mOnSuggestionListener.onSuggestionSelect(i)) {
            rewriteQueryFromSuggestion(i);
            return true;
        }
        return false;
    }

    private void rewriteQueryFromSuggestion(int i) {
        android.text.Editable text = this.mSearchSrcTextView.getText();
        android.database.Cursor cursor = this.mSuggestionsAdapter.getCursor();
        if (cursor == null) {
            return;
        }
        if (cursor.moveToPosition(i)) {
            java.lang.CharSequence convertToString = this.mSuggestionsAdapter.convertToString(cursor);
            if (convertToString != null) {
                setQuery(convertToString);
                return;
            } else {
                setQuery(text);
                return;
            }
        }
        setQuery(text);
    }

    private boolean launchSuggestion(int i, int i2, java.lang.String str) {
        android.database.Cursor cursor = this.mSuggestionsAdapter.getCursor();
        if (cursor != null && cursor.moveToPosition(i)) {
            launchIntent(createIntentFromSuggestion(cursor, i2, str));
            return true;
        }
        return false;
    }

    private void launchIntent(android.content.Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            getContext().startActivity(intent);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(LOG_TAG, "Failed launch activity: " + intent, e);
        }
    }

    private void setQuery(java.lang.CharSequence charSequence) {
        this.mSearchSrcTextView.setText(charSequence, true);
        this.mSearchSrcTextView.setSelection(android.text.TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchQuerySearch(int i, java.lang.String str, java.lang.String str2) {
        getContext().startActivity(createIntent(android.content.Intent.ACTION_SEARCH, null, null, str2, i, str));
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

    private android.content.Intent createVoiceWebSearchIntent(android.content.Intent intent, android.app.SearchableInfo searchableInfo) {
        android.content.Intent intent2 = new android.content.Intent(intent);
        android.content.ComponentName searchActivity = searchableInfo.getSearchActivity();
        intent2.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
        return intent2;
    }

    private android.content.Intent createVoiceAppSearchIntent(android.content.Intent intent, android.app.SearchableInfo searchableInfo) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        int i;
        android.content.ComponentName searchActivity = searchableInfo.getSearchActivity();
        android.content.Intent intent2 = new android.content.Intent(android.content.Intent.ACTION_SEARCH);
        intent2.setComponent(searchActivity);
        android.app.PendingIntent activity = android.app.PendingIntent.getActivity(getContext(), 0, intent2, 1107296256);
        android.os.Bundle bundle = new android.os.Bundle();
        if (this.mAppSearchData != null) {
            bundle.putParcelable(android.app.SearchManager.APP_DATA, this.mAppSearchData);
        }
        android.content.Intent intent3 = new android.content.Intent(intent);
        android.content.res.Resources resources = getResources();
        if (searchableInfo.getVoiceLanguageModeId() == 0) {
            str = android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM;
        } else {
            str = resources.getString(searchableInfo.getVoiceLanguageModeId());
        }
        if (searchableInfo.getVoicePromptTextId() == 0) {
            str2 = null;
        } else {
            str2 = resources.getString(searchableInfo.getVoicePromptTextId());
        }
        if (searchableInfo.getVoiceLanguageId() == 0) {
            str3 = null;
        } else {
            str3 = resources.getString(searchableInfo.getVoiceLanguageId());
        }
        if (searchableInfo.getVoiceMaxResults() == 0) {
            i = 1;
        } else {
            i = searchableInfo.getVoiceMaxResults();
        }
        intent3.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, str);
        intent3.putExtra(android.speech.RecognizerIntent.EXTRA_PROMPT, str2);
        intent3.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE, str3);
        intent3.putExtra(android.speech.RecognizerIntent.EXTRA_MAX_RESULTS, i);
        intent3.putExtra("calling_package", searchActivity != null ? searchActivity.flattenToShortString() : null);
        intent3.putExtra(android.speech.RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT, activity);
        intent3.putExtra(android.speech.RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT_BUNDLE, bundle);
        return intent3;
    }

    private android.content.Intent createIntentFromSuggestion(android.database.Cursor cursor, int i, java.lang.String str) {
        int i2;
        java.lang.String str2;
        java.lang.String columnString;
        try {
            java.lang.String columnString2 = android.widget.SuggestionsAdapter.getColumnString(cursor, android.app.SearchManager.SUGGEST_COLUMN_INTENT_ACTION);
            if (columnString2 == null) {
                columnString2 = this.mSearchable.getSuggestIntentAction();
            }
            if (columnString2 != null) {
                str2 = columnString2;
            } else {
                str2 = android.content.Intent.ACTION_SEARCH;
            }
            java.lang.String columnString3 = android.widget.SuggestionsAdapter.getColumnString(cursor, android.app.SearchManager.SUGGEST_COLUMN_INTENT_DATA);
            if (columnString3 == null) {
                columnString3 = this.mSearchable.getSuggestIntentData();
            }
            if (columnString3 != null && (columnString = android.widget.SuggestionsAdapter.getColumnString(cursor, android.app.SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID)) != null) {
                columnString3 = columnString3 + "/" + android.net.Uri.encode(columnString);
            }
            return createIntent(str2, columnString3 == null ? null : android.net.Uri.parse(columnString3), android.widget.SuggestionsAdapter.getColumnString(cursor, android.app.SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA), android.widget.SuggestionsAdapter.getColumnString(cursor, android.app.SearchManager.SUGGEST_COLUMN_QUERY), i, str);
        } catch (java.lang.RuntimeException e) {
            try {
                i2 = cursor.getPosition();
            } catch (java.lang.RuntimeException e2) {
                i2 = -1;
            }
            android.util.Log.w(LOG_TAG, "Search suggestions cursor at row " + i2 + " returned exception.", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceSuggestionQuery() {
        this.mSearchSrcTextView.doBeforeTextChanged();
        this.mSearchSrcTextView.doAfterTextChanged();
    }

    static boolean isLandscapeMode(android.content.Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private static class UpdatableTouchDelegate extends android.view.TouchDelegate {
        private final android.graphics.Rect mActualBounds;
        private boolean mDelegateTargeted;
        private final android.view.View mDelegateView;
        private final int mSlop;
        private final android.graphics.Rect mSlopBounds;
        private final android.graphics.Rect mTargetBounds;

        public UpdatableTouchDelegate(android.graphics.Rect rect, android.graphics.Rect rect2, android.view.View view) {
            super(rect, view);
            this.mSlop = android.view.ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            this.mTargetBounds = new android.graphics.Rect();
            this.mSlopBounds = new android.graphics.Rect();
            this.mActualBounds = new android.graphics.Rect();
            setBounds(rect, rect2);
            this.mDelegateView = view;
        }

        public void setBounds(android.graphics.Rect rect, android.graphics.Rect rect2) {
            this.mTargetBounds.set(rect);
            this.mSlopBounds.set(rect);
            this.mSlopBounds.inset(-this.mSlop, -this.mSlop);
            this.mActualBounds.set(rect2);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.view.TouchDelegate
        public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
            boolean z;
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            boolean z2 = true;
            switch (motionEvent.getAction()) {
                case 0:
                    if (this.mTargetBounds.contains(x, y)) {
                        this.mDelegateTargeted = true;
                        z = true;
                        break;
                    }
                    z = true;
                    z2 = false;
                    break;
                case 1:
                case 2:
                    boolean z3 = this.mDelegateTargeted;
                    if (z3 && !this.mSlopBounds.contains(x, y)) {
                        z2 = z3;
                        z = false;
                        break;
                    } else {
                        z2 = z3;
                        z = true;
                        break;
                    }
                    break;
                case 3:
                    boolean z4 = this.mDelegateTargeted;
                    this.mDelegateTargeted = false;
                    z2 = z4;
                    z = true;
                    break;
                default:
                    z = true;
                    z2 = false;
                    break;
            }
            if (!z2) {
                return false;
            }
            if (z && !this.mActualBounds.contains(x, y)) {
                motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
            } else {
                motionEvent.setLocation(x - this.mActualBounds.left, y - this.mActualBounds.top);
            }
            return this.mDelegateView.dispatchTouchEvent(motionEvent);
        }
    }

    public static class SearchAutoComplete extends android.widget.AutoCompleteTextView {
        private boolean mHasPendingShowSoftInputRequest;
        final java.lang.Runnable mRunShowSoftInputIfNecessary;
        private android.widget.SearchView mSearchView;
        private int mThreshold;

        public SearchAutoComplete(android.content.Context context) {
            super(context);
            this.mRunShowSoftInputIfNecessary = new java.lang.Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.SearchView.SearchAutoComplete.this.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        public SearchAutoComplete(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mRunShowSoftInputIfNecessary = new java.lang.Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.SearchView.SearchAutoComplete.this.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        public SearchAutoComplete(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.mRunShowSoftInputIfNecessary = new java.lang.Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.SearchView.SearchAutoComplete.this.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        public SearchAutoComplete(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mRunShowSoftInputIfNecessary = new java.lang.Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.SearchView.SearchAutoComplete.this.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        @Override // android.view.View
        protected void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) android.util.TypedValue.applyDimension(1, getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        void setSearchView(android.widget.SearchView searchView) {
            this.mSearchView = searchView;
        }

        @Override // android.widget.AutoCompleteTextView
        public void setThreshold(int i) {
            super.setThreshold(i);
            this.mThreshold = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            return android.text.TextUtils.getTrimmedLength(getText()) == 0;
        }

        @Override // android.widget.AutoCompleteTextView
        protected void replaceText(java.lang.CharSequence charSequence) {
        }

        @Override // android.widget.AutoCompleteTextView
        public void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (z && this.mSearchView.hasFocus() && getVisibility() == 0) {
                this.mHasPendingShowSoftInputRequest = true;
                if (android.widget.SearchView.isLandscapeMode(getContext())) {
                    ensureImeVisible(true);
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
            super.onFocusChanged(z, i, rect);
            this.mSearchView.onTextFocusChanged();
        }

        @Override // android.widget.AutoCompleteTextView
        public boolean enoughToFilter() {
            return this.mThreshold <= 0 || super.enoughToFilter();
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public boolean onKeyPreIme(int i, android.view.KeyEvent keyEvent) {
            boolean onKeyPreIme = super.onKeyPreIme(i, keyEvent);
            if (onKeyPreIme && i == 4 && keyEvent.getAction() == 1) {
                setImeVisibility(false);
            }
            return onKeyPreIme;
        }

        private int getSearchViewTextMinWidthDp() {
            android.content.res.Configuration configuration = getResources().getConfiguration();
            int i = configuration.screenWidthDp;
            int i2 = configuration.screenHeightDp;
            int i3 = configuration.orientation;
            if (i >= 960 && i2 >= 720 && i3 == 2) {
                return 256;
            }
            if (i >= 600) {
                return 192;
            }
            if (i >= 640 && i2 >= 480) {
                return 192;
            }
            return 160;
        }

        @Override // android.widget.TextView, android.view.View
        public android.view.inputmethod.InputConnection onCreateInputConnection(android.view.inputmethod.EditorInfo editorInfo) {
            android.view.inputmethod.InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.mHasPendingShowSoftInputRequest) {
                removeCallbacks(this.mRunShowSoftInputIfNecessary);
                post(this.mRunShowSoftInputIfNecessary);
            }
            return onCreateInputConnection;
        }

        @Override // android.view.View
        public boolean checkInputConnectionProxy(android.view.View view) {
            return view == this.mSearchView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: showSoftInputIfNecessary, reason: merged with bridge method [inline-methods] */
        public void lambda$new$0() {
            if (this.mHasPendingShowSoftInputRequest) {
                ((android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class)).showSoftInput(this, 0);
                this.mHasPendingShowSoftInputRequest = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setImeVisibility(boolean z) {
            android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
            if (!z) {
                this.mHasPendingShowSoftInputRequest = false;
                removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else {
                if (inputMethodManager.hasActiveInputConnection(this)) {
                    this.mHasPendingShowSoftInputRequest = false;
                    removeCallbacks(this.mRunShowSoftInputIfNecessary);
                    inputMethodManager.showSoftInput(this, 0);
                    return;
                }
                this.mHasPendingShowSoftInputRequest = true;
            }
        }
    }
}
