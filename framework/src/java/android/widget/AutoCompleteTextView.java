package android.widget;

/* loaded from: classes4.dex */
public class AutoCompleteTextView extends android.widget.EditText implements android.widget.Filter.FilterListener {
    static final boolean DEBUG = false;
    static final int EXPAND_MAX = 3;
    static final java.lang.String TAG = "AutoCompleteTextView";
    private android.widget.ListAdapter mAdapter;
    private android.widget.AutoCompleteTextView.MyWatcher mAutoCompleteTextWatcher;
    private final android.window.OnBackInvokedCallback mBackCallback;
    private boolean mBackCallbackRegistered;
    private boolean mBlockCompletion;
    private int mDropDownAnchorId;
    private boolean mDropDownDismissedOnCompletion;
    private android.widget.Filter mFilter;
    private int mHintResource;
    private java.lang.CharSequence mHintText;
    private android.widget.TextView mHintView;
    private android.widget.AdapterView.OnItemClickListener mItemClickListener;
    private android.widget.AdapterView.OnItemSelectedListener mItemSelectedListener;
    private int mLastKeyCode;
    private android.widget.AutoCompleteTextView.PopupDataSetObserver mObserver;
    private final android.widget.AutoCompleteTextView.PassThroughClickListener mPassThroughClickListener;
    private final android.widget.ListPopupWindow mPopup;
    private boolean mPopupCanBeUpdated;
    private final android.content.Context mPopupContext;
    private int mThreshold;
    private android.widget.AutoCompleteTextView.Validator mValidator;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InputMethodMode {
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    public interface Validator {
        java.lang.CharSequence fixText(java.lang.CharSequence charSequence);

        boolean isValid(java.lang.CharSequence charSequence);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.AutoCompleteTextView> {
        private int mCompletionHintId;
        private int mCompletionThresholdId;
        private int mDropDownHeightId;
        private int mDropDownHorizontalOffsetId;
        private int mDropDownVerticalOffsetId;
        private int mDropDownWidthId;
        private int mPopupBackgroundId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mCompletionHintId = propertyMapper.mapObject("completionHint", 16843122);
            this.mCompletionThresholdId = propertyMapper.mapInt("completionThreshold", 16843124);
            this.mDropDownHeightId = propertyMapper.mapInt("dropDownHeight", 16843395);
            this.mDropDownHorizontalOffsetId = propertyMapper.mapInt("dropDownHorizontalOffset", 16843436);
            this.mDropDownVerticalOffsetId = propertyMapper.mapInt("dropDownVerticalOffset", 16843437);
            this.mDropDownWidthId = propertyMapper.mapInt("dropDownWidth", 16843362);
            this.mPopupBackgroundId = propertyMapper.mapObject("popupBackground", 16843126);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.AutoCompleteTextView autoCompleteTextView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mCompletionHintId, autoCompleteTextView.getCompletionHint());
            propertyReader.readInt(this.mCompletionThresholdId, autoCompleteTextView.getThreshold());
            propertyReader.readInt(this.mDropDownHeightId, autoCompleteTextView.getDropDownHeight());
            propertyReader.readInt(this.mDropDownHorizontalOffsetId, autoCompleteTextView.getDropDownHorizontalOffset());
            propertyReader.readInt(this.mDropDownVerticalOffsetId, autoCompleteTextView.getDropDownVerticalOffset());
            propertyReader.readInt(this.mDropDownWidthId, autoCompleteTextView.getDropDownWidth());
            propertyReader.readObject(this.mPopupBackgroundId, autoCompleteTextView.getDropDownBackground());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (isPopupShowing()) {
            dismissDropDown();
        }
    }

    public AutoCompleteTextView(android.content.Context context) {
        this(context, null);
    }

    public AutoCompleteTextView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842859);
    }

    public AutoCompleteTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AutoCompleteTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, null);
    }

    public AutoCompleteTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2, android.content.res.Resources.Theme theme) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray typedArray;
        this.mDropDownDismissedOnCompletion = true;
        this.mLastKeyCode = 0;
        this.mValidator = null;
        this.mPopupCanBeUpdated = true;
        this.mBackCallback = new android.window.OnBackInvokedCallback() { // from class: android.widget.AutoCompleteTextView$$ExternalSyntheticLambda0
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                android.widget.AutoCompleteTextView.this.lambda$new$0();
            }
        };
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AutoCompleteTextView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.AutoCompleteTextView, attributeSet, obtainStyledAttributes, i, i2);
        if (theme == null) {
            int resourceId = obtainStyledAttributes.getResourceId(8, 0);
            if (resourceId != 0) {
                this.mPopupContext = new android.view.ContextThemeWrapper(context, resourceId);
            } else {
                this.mPopupContext = context;
            }
        } else {
            this.mPopupContext = new android.view.ContextThemeWrapper(context, theme);
        }
        if (this.mPopupContext != context) {
            android.content.res.TypedArray obtainStyledAttributes2 = this.mPopupContext.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AutoCompleteTextView, i, i2);
            saveAttributeDataForStyleable(context, com.android.internal.R.styleable.AutoCompleteTextView, attributeSet, obtainStyledAttributes, i, i2);
            typedArray = obtainStyledAttributes2;
        } else {
            typedArray = obtainStyledAttributes;
        }
        android.graphics.drawable.Drawable drawable = typedArray.getDrawable(3);
        int layoutDimension = typedArray.getLayoutDimension(5, -2);
        int layoutDimension2 = typedArray.getLayoutDimension(7, -2);
        int resourceId2 = typedArray.getResourceId(1, com.android.internal.R.layout.simple_dropdown_hint);
        java.lang.CharSequence text = typedArray.getText(0);
        if (typedArray != obtainStyledAttributes) {
            typedArray.recycle();
        }
        this.mPopup = new android.widget.ListPopupWindow(this.mPopupContext, attributeSet, i, i2);
        this.mPopup.setOnDismissListener(new android.widget.PopupWindow.OnDismissListener() { // from class: android.widget.AutoCompleteTextView$$ExternalSyntheticLambda1
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                android.widget.AutoCompleteTextView.this.lambda$new$1();
            }
        });
        this.mPopup.setSoftInputMode(16);
        this.mPopup.setPromptPosition(1);
        this.mPopup.setListSelector(drawable);
        this.mPopup.setOnItemClickListener(new android.widget.AutoCompleteTextView.DropDownItemClickListener());
        this.mPopup.setWidth(layoutDimension);
        this.mPopup.setHeight(layoutDimension2);
        this.mHintResource = resourceId2;
        setCompletionHint(text);
        this.mDropDownAnchorId = obtainStyledAttributes.getResourceId(6, -1);
        this.mThreshold = obtainStyledAttributes.getInt(2, 2);
        obtainStyledAttributes.recycle();
        int inputType = getInputType();
        if ((inputType & 15) == 1) {
            setRawInputType(inputType | 65536);
        }
        setFocusable(true);
        this.mAutoCompleteTextWatcher = new android.widget.AutoCompleteTextView.MyWatcher();
        addTextChangedListener(this.mAutoCompleteTextWatcher);
        this.mPassThroughClickListener = new android.widget.AutoCompleteTextView.PassThroughClickListener();
        super.setOnClickListener(this.mPassThroughClickListener);
    }

    @Override // android.view.View
    public void setOnClickListener(android.view.View.OnClickListener onClickListener) {
        this.mPassThroughClickListener.mWrapped = onClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClickImpl() {
        if (isPopupShowing()) {
            ensureImeVisible(true);
        }
    }

    public void setCompletionHint(java.lang.CharSequence charSequence) {
        this.mHintText = charSequence;
        if (charSequence == null) {
            this.mPopup.setPromptView(null);
            this.mHintView = null;
        } else {
            if (this.mHintView == null) {
                android.widget.TextView textView = (android.widget.TextView) android.view.LayoutInflater.from(this.mPopupContext).inflate(this.mHintResource, (android.view.ViewGroup) null).findViewById(16908308);
                textView.lambda$setTextAsync$0(this.mHintText);
                this.mHintView = textView;
                this.mPopup.setPromptView(textView);
                return;
            }
            this.mHintView.lambda$setTextAsync$0(charSequence);
        }
    }

    public java.lang.CharSequence getCompletionHint() {
        return this.mHintText;
    }

    public int getDropDownWidth() {
        return this.mPopup.getWidth();
    }

    public void setDropDownWidth(int i) {
        this.mPopup.setWidth(i);
    }

    public int getDropDownHeight() {
        return this.mPopup.getHeight();
    }

    public void setDropDownHeight(int i) {
        this.mPopup.setHeight(i);
    }

    public int getDropDownAnchor() {
        return this.mDropDownAnchorId;
    }

    public void setDropDownAnchor(int i) {
        this.mDropDownAnchorId = i;
        this.mPopup.setAnchorView(null);
    }

    public android.graphics.drawable.Drawable getDropDownBackground() {
        return this.mPopup.getBackground();
    }

    public void setDropDownBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        this.mPopup.setBackgroundDrawable(drawable);
    }

    public void setDropDownBackgroundResource(int i) {
        this.mPopup.setBackgroundDrawable(getContext().getDrawable(i));
    }

    public void setDropDownVerticalOffset(int i) {
        this.mPopup.setVerticalOffset(i);
    }

    public int getDropDownVerticalOffset() {
        return this.mPopup.getVerticalOffset();
    }

    public void setDropDownHorizontalOffset(int i) {
        this.mPopup.setHorizontalOffset(i);
    }

    public int getDropDownHorizontalOffset() {
        return this.mPopup.getHorizontalOffset();
    }

    public void setDropDownAnimationStyle(int i) {
        this.mPopup.setAnimationStyle(i);
    }

    public int getDropDownAnimationStyle() {
        return this.mPopup.getAnimationStyle();
    }

    public boolean isDropDownAlwaysVisible() {
        return this.mPopup.isDropDownAlwaysVisible();
    }

    public void setDropDownAlwaysVisible(boolean z) {
        this.mPopup.setDropDownAlwaysVisible(z);
    }

    public boolean isDropDownDismissedOnCompletion() {
        return this.mDropDownDismissedOnCompletion;
    }

    public void setDropDownDismissedOnCompletion(boolean z) {
        this.mDropDownDismissedOnCompletion = z;
    }

    public int getThreshold() {
        return this.mThreshold;
    }

    public void setThreshold(int i) {
        if (i <= 0) {
            i = 1;
        }
        this.mThreshold = i;
    }

    public void setOnItemClickListener(android.widget.AdapterView.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mItemSelectedListener = onItemSelectedListener;
    }

    @java.lang.Deprecated
    public android.widget.AdapterView.OnItemClickListener getItemClickListener() {
        return this.mItemClickListener;
    }

    @java.lang.Deprecated
    public android.widget.AdapterView.OnItemSelectedListener getItemSelectedListener() {
        return this.mItemSelectedListener;
    }

    public android.widget.AdapterView.OnItemClickListener getOnItemClickListener() {
        return this.mItemClickListener;
    }

    public android.widget.AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
        return this.mItemSelectedListener;
    }

    public void setOnDismissListener(final android.widget.AutoCompleteTextView.OnDismissListener onDismissListener) {
        android.widget.PopupWindow.OnDismissListener onDismissListener2;
        if (onDismissListener == null) {
            onDismissListener2 = null;
        } else {
            onDismissListener2 = new android.widget.PopupWindow.OnDismissListener() { // from class: android.widget.AutoCompleteTextView.1
                @Override // android.widget.PopupWindow.OnDismissListener
                public void onDismiss() {
                    onDismissListener.onDismiss();
                    android.widget.AutoCompleteTextView.this.lambda$new$1();
                }
            };
        }
        this.mPopup.setOnDismissListener(onDismissListener2);
    }

    public android.widget.ListAdapter getAdapter() {
        return this.mAdapter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends android.widget.ListAdapter & android.widget.Filterable> void setAdapter(T t) {
        if (this.mObserver == null) {
            this.mObserver = new android.widget.AutoCompleteTextView.PopupDataSetObserver();
        } else if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = t;
        if (this.mAdapter != null) {
            this.mFilter = ((android.widget.Filterable) this.mAdapter).getFilter();
            t.registerDataSetObserver(this.mObserver);
        } else {
            this.mFilter = null;
        }
        this.mPopup.setAdapter(this.mAdapter);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyPreIme(int i, android.view.KeyEvent keyEvent) {
        if ((i == 4 || i == 111) && isPopupShowing() && !this.mPopup.isDropDownAlwaysVisible()) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                android.view.KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                if (keyDispatcherState != null) {
                    keyDispatcherState.startTracking(keyEvent, this);
                }
                return true;
            }
            if (keyEvent.getAction() == 1) {
                android.view.KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                if (keyDispatcherState2 != null) {
                    keyDispatcherState2.handleUpEvent(keyEvent);
                }
                if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    dismissDropDown();
                    return true;
                }
            }
        }
        return super.onKeyPreIme(i, keyEvent);
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (this.mPopup.onKeyUp(i, keyEvent)) {
            switch (i) {
                case 23:
                case 61:
                case 66:
                case 160:
                    if (keyEvent.hasNoModifiers()) {
                        performCompletion();
                        break;
                    }
                    break;
            }
            return true;
        }
        if (isPopupShowing() && i == 61 && keyEvent.hasNoModifiers()) {
            performCompletion();
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (this.mPopup.onKeyDown(i, keyEvent)) {
            return true;
        }
        if (!isPopupShowing()) {
            switch (i) {
                case 20:
                    if (keyEvent.hasNoModifiers()) {
                        performValidation();
                        break;
                    }
                    break;
            }
        }
        if (isPopupShowing() && i == 61 && keyEvent.hasNoModifiers()) {
            return true;
        }
        this.mLastKeyCode = i;
        boolean onKeyDown = super.onKeyDown(i, keyEvent);
        this.mLastKeyCode = 0;
        if (onKeyDown && isPopupShowing()) {
            clearListSelection();
        }
        return onKeyDown;
    }

    public boolean enoughToFilter() {
        return getText().length() >= this.mThreshold;
    }

    private class MyWatcher implements android.text.TextWatcher {
        private boolean mOpenBefore;

        private MyWatcher() {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
            if (android.widget.AutoCompleteTextView.this.mBlockCompletion) {
                return;
            }
            this.mOpenBefore = android.widget.AutoCompleteTextView.this.isPopupShowing();
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(android.text.Editable editable) {
            if (android.widget.AutoCompleteTextView.this.mBlockCompletion) {
                return;
            }
            if (!this.mOpenBefore || android.widget.AutoCompleteTextView.this.isPopupShowing()) {
                android.widget.AutoCompleteTextView.this.refreshAutoCompleteResults();
            }
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    void doBeforeTextChanged() {
        this.mAutoCompleteTextWatcher.beforeTextChanged(null, 0, 0, 0);
    }

    void doAfterTextChanged() {
        this.mAutoCompleteTextWatcher.afterTextChanged(null);
    }

    public final void refreshAutoCompleteResults() {
        if (enoughToFilter()) {
            if (this.mFilter != null) {
                this.mPopupCanBeUpdated = true;
                performFiltering(getText(), this.mLastKeyCode);
                return;
            }
            return;
        }
        if (!this.mPopup.isDropDownAlwaysVisible()) {
            dismissDropDown();
        }
        if (this.mFilter != null) {
            this.mFilter.filter(null);
        }
    }

    public boolean isPopupShowing() {
        return this.mPopup.isShowing();
    }

    protected java.lang.CharSequence convertSelectionToString(java.lang.Object obj) {
        return this.mFilter.convertResultToString(obj);
    }

    public void clearListSelection() {
        this.mPopup.clearListSelection();
    }

    public void setListSelection(int i) {
        this.mPopup.setSelection(i);
    }

    public int getListSelection() {
        return this.mPopup.getSelectedItemPosition();
    }

    protected void performFiltering(java.lang.CharSequence charSequence, int i) {
        this.mFilter.filter(charSequence, this);
    }

    public void performCompletion() {
        performCompletion(null, -1, -1L);
    }

    @Override // android.widget.TextView
    public void onCommitCompletion(android.view.inputmethod.CompletionInfo completionInfo) {
        if (isPopupShowing()) {
            this.mPopup.performItemClick(completionInfo.getPosition());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performCompletion(android.view.View view, int i, long j) {
        java.lang.Object item;
        android.view.View view2;
        int selectedItemPosition;
        long selectedItemId;
        if (isPopupShowing()) {
            if (i < 0) {
                item = this.mPopup.getSelectedItem();
            } else {
                item = this.mAdapter.getItem(i);
            }
            if (item == null) {
                android.util.Log.w(TAG, "performCompletion: no selected item");
                return;
            }
            this.mBlockCompletion = true;
            replaceText(convertSelectionToString(item));
            this.mBlockCompletion = false;
            if (this.mItemClickListener != null) {
                android.widget.ListPopupWindow listPopupWindow = this.mPopup;
                if (view == null || i < 0) {
                    android.view.View selectedView = listPopupWindow.getSelectedView();
                    view2 = selectedView;
                    selectedItemPosition = listPopupWindow.getSelectedItemPosition();
                    selectedItemId = listPopupWindow.getSelectedItemId();
                } else {
                    view2 = view;
                    selectedItemPosition = i;
                    selectedItemId = j;
                }
                this.mItemClickListener.onItemClick(listPopupWindow.getListView(), view2, selectedItemPosition, selectedItemId);
            }
        }
        if (this.mDropDownDismissedOnCompletion && !this.mPopup.isDropDownAlwaysVisible()) {
            dismissDropDown();
        }
    }

    public boolean isPerformingCompletion() {
        return this.mBlockCompletion;
    }

    public void setText(java.lang.CharSequence charSequence, boolean z) {
        if (z) {
            lambda$setTextAsync$0(charSequence);
            return;
        }
        this.mBlockCompletion = true;
        lambda$setTextAsync$0(charSequence);
        this.mBlockCompletion = false;
    }

    protected void replaceText(java.lang.CharSequence charSequence) {
        clearComposingText();
        lambda$setTextAsync$0(charSequence);
        android.text.Editable text = getText();
        android.text.Selection.setSelection(text, text.length());
    }

    @Override // android.widget.Filter.FilterListener
    public void onFilterComplete(int i) {
        updateDropDownForFilter(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDropDownForFilter(int i) {
        if (getWindowVisibility() == 8) {
            return;
        }
        boolean isDropDownAlwaysVisible = this.mPopup.isDropDownAlwaysVisible();
        boolean enoughToFilter = enoughToFilter();
        if ((i > 0 || isDropDownAlwaysVisible) && enoughToFilter) {
            if (hasFocus() && hasWindowFocus() && this.mPopupCanBeUpdated) {
                showDropDown();
                return;
            }
            return;
        }
        if (!isDropDownAlwaysVisible && isPopupShowing()) {
            dismissDropDown();
            this.mPopupCanBeUpdated = true;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (!z && !this.mPopup.isDropDownAlwaysVisible()) {
            dismissDropDown();
        }
    }

    @Override // android.view.View
    protected void onDisplayHint(int i) {
        super.onDisplayHint(i);
        switch (i) {
            case 4:
                if (!this.mPopup.isDropDownAlwaysVisible()) {
                    dismissDropDown();
                    break;
                }
                break;
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (isTemporarilyDetached()) {
            return;
        }
        if (!z) {
            performValidation();
        }
        if (!z && !this.mPopup.isDropDownAlwaysVisible()) {
            dismissDropDown();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        dismissDropDown();
        super.onDetachedFromWindow();
    }

    public void dismissDropDown() {
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.displayCompletions(this, null);
        }
        this.mPopup.dismiss();
        this.mPopupCanBeUpdated = false;
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (isPopupShowing()) {
            showDropDown();
        }
        return frame;
    }

    public void showDropDownAfterLayout() {
        this.mPopup.postShow();
    }

    public void ensureImeVisible(boolean z) {
        this.mPopup.setInputMethodMode(z ? 1 : 2);
        if (this.mPopup.isDropDownAlwaysVisible() || (this.mFilter != null && enoughToFilter())) {
            showDropDown();
        }
    }

    public boolean isInputMethodNotNeeded() {
        return this.mPopup.getInputMethodMode() == 2;
    }

    public int getInputMethodMode() {
        return this.mPopup.getInputMethodMode();
    }

    public void setInputMethodMode(int i) {
        this.mPopup.setInputMethodMode(i);
    }

    public void showDropDown() {
        buildImeCompletions();
        if (this.mPopup.getAnchorView() == null) {
            if (this.mDropDownAnchorId != -1) {
                this.mPopup.setAnchorView(getRootView().findViewById(this.mDropDownAnchorId));
            } else {
                this.mPopup.setAnchorView(this);
            }
        }
        if (!isPopupShowing()) {
            this.mPopup.setInputMethodMode(1);
            this.mPopup.setListItemExpandMax(3);
        }
        this.mPopup.show();
        if (!this.mPopup.isDropDownAlwaysVisible()) {
            registerOnBackInvokedCallback();
        }
        this.mPopup.getListView().setOverScrollMode(0);
    }

    public void setForceIgnoreOutsideTouch(boolean z) {
        this.mPopup.setForceIgnoreOutsideTouch(z);
    }

    private void buildImeCompletions() {
        android.view.inputmethod.InputMethodManager inputMethodManager;
        android.widget.ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null && (inputMethodManager = (android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class)) != null) {
            int min = java.lang.Math.min(listAdapter.getCount(), 20);
            android.view.inputmethod.CompletionInfo[] completionInfoArr = new android.view.inputmethod.CompletionInfo[min];
            int i = 0;
            for (int i2 = 0; i2 < min; i2++) {
                if (listAdapter.isEnabled(i2)) {
                    completionInfoArr[i] = new android.view.inputmethod.CompletionInfo(listAdapter.getItemId(i2), i, convertSelectionToString(listAdapter.getItem(i2)));
                    i++;
                }
            }
            if (i != min) {
                android.view.inputmethod.CompletionInfo[] completionInfoArr2 = new android.view.inputmethod.CompletionInfo[i];
                java.lang.System.arraycopy(completionInfoArr, 0, completionInfoArr2, 0, i);
                completionInfoArr = completionInfoArr2;
            }
            inputMethodManager.displayCompletions(this, completionInfoArr);
        }
    }

    public void setValidator(android.widget.AutoCompleteTextView.Validator validator) {
        this.mValidator = validator;
    }

    public android.widget.AutoCompleteTextView.Validator getValidator() {
        return this.mValidator;
    }

    public void performValidation() {
        if (this.mValidator == null) {
            return;
        }
        android.text.Editable text = getText();
        if (!android.text.TextUtils.isEmpty(text) && !this.mValidator.isValid(text)) {
            lambda$setTextAsync$0(this.mValidator.fixText(text));
        }
    }

    protected android.widget.Filter getFilter() {
        return this.mFilter;
    }

    @Override // android.widget.EditText, android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.AutoCompleteTextView.class.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unregisterOnBackInvokedCallback, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1() {
        android.window.OnBackInvokedDispatcher findOnBackInvokedDispatcher;
        if (!this.mBackCallbackRegistered || (findOnBackInvokedDispatcher = findOnBackInvokedDispatcher()) == null) {
            return;
        }
        if (android.window.WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mPopupContext)) {
            findOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.mBackCallback);
        }
        this.mBackCallbackRegistered = false;
    }

    private void registerOnBackInvokedCallback() {
        android.window.OnBackInvokedDispatcher findOnBackInvokedDispatcher;
        if (this.mBackCallbackRegistered || (findOnBackInvokedDispatcher = findOnBackInvokedDispatcher()) == null) {
            return;
        }
        if (android.window.WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mPopupContext)) {
            findOnBackInvokedDispatcher.registerOnBackInvokedCallback(1000000, this.mBackCallback);
        }
        this.mBackCallbackRegistered = true;
    }

    private class DropDownItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        private DropDownItemClickListener() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
            android.widget.AutoCompleteTextView.this.performCompletion(view, i, j);
        }
    }

    private class PassThroughClickListener implements android.view.View.OnClickListener {
        private android.view.View.OnClickListener mWrapped;

        private PassThroughClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.widget.AutoCompleteTextView.this.onClickImpl();
            if (this.mWrapped != null) {
                this.mWrapped.onClick(view);
            }
        }
    }

    private static class PopupDataSetObserver extends android.database.DataSetObserver {
        private final java.lang.ref.WeakReference<android.widget.AutoCompleteTextView> mViewReference;
        private final java.lang.Runnable updateRunnable;

        private PopupDataSetObserver(android.widget.AutoCompleteTextView autoCompleteTextView) {
            this.updateRunnable = new java.lang.Runnable() { // from class: android.widget.AutoCompleteTextView.PopupDataSetObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    android.widget.ListAdapter listAdapter;
                    android.widget.AutoCompleteTextView autoCompleteTextView2 = (android.widget.AutoCompleteTextView) android.widget.AutoCompleteTextView.PopupDataSetObserver.this.mViewReference.get();
                    if (autoCompleteTextView2 == null || (listAdapter = autoCompleteTextView2.mAdapter) == null) {
                        return;
                    }
                    autoCompleteTextView2.updateDropDownForFilter(listAdapter.getCount());
                }
            };
            this.mViewReference = new java.lang.ref.WeakReference<>(autoCompleteTextView);
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            android.widget.AutoCompleteTextView autoCompleteTextView = this.mViewReference.get();
            if (autoCompleteTextView != null && autoCompleteTextView.mAdapter != null) {
                autoCompleteTextView.post(this.updateRunnable);
            }
        }
    }
}
