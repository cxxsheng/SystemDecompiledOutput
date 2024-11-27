package android.widget;

/* loaded from: classes4.dex */
public class Editor {
    private static final int ACTION_MODE_MENU_ITEM_ORDER_ASSIST = 0;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_AUTOFILL = 10;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_COPY = 5;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_CUT = 4;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_PASTE = 6;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_PASTE_AS_PLAIN_TEXT = 11;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_REPLACE = 9;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_SECONDARY_ASSIST_ACTIONS_START = 50;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_SELECT_ALL = 8;
    private static final int ACTION_MODE_MENU_ITEM_ORDER_SHARE = 7;
    static final int BLINK = 500;
    private static final int CONTEXT_MENU_GROUP_CLIPBOARD = 2;
    private static final int CONTEXT_MENU_GROUP_MISC = 3;
    private static final int CONTEXT_MENU_GROUP_UNDO_REDO = 1;
    private static final int CONTEXT_MENU_ITEM_ORDER_REPLACE = 11;
    private static final int CURSOR_START_FLOAT_DISTANCE_PX = 20;
    private static final boolean DEBUG_UNDO = false;
    private static final int DELAY_BEFORE_HANDLE_FADES_OUT = 4000;
    private static final int DRAG_SHADOW_MAX_TEXT_LENGTH = 20;
    static final int EXTRACT_NOTHING = -2;
    static final int EXTRACT_UNKNOWN = -1;
    private static final int FLAG_MISSPELLED_OR_GRAMMAR_ERROR = 10;
    private static final boolean FLAG_USE_MAGNIFIER = true;
    public static final int HANDLE_TYPE_SELECTION_END = 1;
    public static final int HANDLE_TYPE_SELECTION_START = 0;
    private static final int LINE_CHANGE_SLOP_MAX_DP = 45;
    private static final int LINE_CHANGE_SLOP_MIN_DP = 8;
    private static final int MAX_LINE_HEIGHT_FOR_MAGNIFIER = 32;
    private static final int MIN_LINE_HEIGHT_FOR_MAGNIFIER = 20;
    private static final int RECENT_CUT_COPY_DURATION_MS = 15000;
    private static final java.lang.String TAG = "Editor";
    private static final java.lang.String UNDO_OWNER_TAG = "Editor";
    private static final int UNSET_LINE = -1;
    private static final int UNSET_X_VALUE = -1;
    private final android.widget.Editor.AccessibilitySmartActions mA11ySmartActions;
    private boolean mBackCallbackRegistered;
    private android.widget.Editor.Blink mBlink;
    private float mContextMenuAnchorX;
    private float mContextMenuAnchorY;
    private android.widget.Editor.CorrectionHighlighter mCorrectionHighlighter;
    boolean mCreatedWithASelection;
    private float mCursorDragDirectionMinXYRatio;
    android.view.ActionMode.Callback mCustomInsertionActionModeCallback;
    android.view.ActionMode.Callback mCustomSelectionActionModeCallback;
    boolean mDiscardNextActionUp;
    private boolean mDrawCursorOnMagnifier;
    java.lang.CharSequence mError;
    private android.widget.Editor.ErrorPopup mErrorPopup;
    boolean mErrorWasChanged;
    private boolean mFlagCursorDragFromAnywhereEnabled;
    private boolean mFlagInsertionHandleGesturesEnabled;
    boolean mFrozenWithFocus;
    private final boolean mHapticTextHandleEnabled;
    boolean mIgnoreActionUpEvent;
    boolean mInBatchEditControllers;
    android.widget.Editor.InputContentType mInputContentType;
    android.widget.Editor.InputMethodState mInputMethodState;
    private android.widget.Editor.InsertModeController mInsertModeController;
    private java.lang.Runnable mInsertionActionModeRunnable;
    private boolean mInsertionControllerEnabled;
    android.widget.Editor.InsertionPointCursorController mInsertionPointCursorController;
    boolean mIsBeingLongClicked;
    boolean mIsBeingLongClickedByAccessibility;
    android.text.method.KeyListener mKeyListener;
    private int mLastButtonState;
    private int mLineChangeSlopMax;
    private int mLineChangeSlopMin;
    private final float mLineSlopRatio;
    private android.widget.Editor.MagnifierMotionAnimator mMagnifierAnimator;
    private int mMaxLineHeightForMagnifier;
    private int mMinLineHeightForMagnifier;
    private final boolean mNewMagnifierEnabled;
    private android.widget.Editor.PositionListener mPositionListener;
    private boolean mPreserveSelection;
    final android.widget.Editor.ProcessTextIntentActionsHandler mProcessTextIntentActionsHandler;
    private boolean mRenderCursorRegardlessTiming;
    private boolean mRequestingLinkActionMode;
    private boolean mRestartActionModeOnNextRefresh;
    boolean mSelectAllOnFocus;
    android.graphics.drawable.Drawable mSelectHandleCenter;
    android.graphics.drawable.Drawable mSelectHandleLeft;
    android.graphics.drawable.Drawable mSelectHandleRight;
    private android.widget.SelectionActionModeHelper mSelectionActionModeHelper;
    private boolean mSelectionControllerEnabled;
    android.widget.Editor.SelectionModifierCursorController mSelectionModifierCursorController;
    boolean mSelectionMoved;
    private long mShowCursor;
    private boolean mShowErrorAfterAttach;
    private java.lang.Runnable mShowSuggestionRunnable;
    private android.widget.Editor.SpanController mSpanController;
    android.widget.SpellChecker mSpellChecker;
    android.text.style.SuggestionRangeSpan mSuggestionRangeSpan;
    private android.widget.Editor.SuggestionsPopupWindow mSuggestionsPopupWindow;
    private android.graphics.Rect mTempRect;
    private android.view.ActionMode mTextActionMode;
    boolean mTextIsSelectable;
    private android.widget.Editor.TextRenderNode[] mTextRenderNodes;
    private final android.widget.TextView mTextView;
    boolean mTouchFocusSelected;
    private boolean mUpdateWordIteratorText;
    private boolean mUseNewContextMenu;
    private android.text.method.WordIterator mWordIterator;
    private android.text.method.WordIterator mWordIteratorWithText;
    private final android.widget.TextViewOnReceiveContentListener mDefaultOnReceiveContentListener = new android.widget.TextViewOnReceiveContentListener();
    private final android.content.UndoManager mUndoManager = new android.content.UndoManager();
    private android.content.UndoOwner mUndoOwner = this.mUndoManager.getOwner("Editor", this);
    final android.widget.Editor.UndoInputFilter mUndoInputFilter = new android.widget.Editor.UndoInputFilter(this);
    boolean mAllowUndo = true;
    private final com.android.internal.logging.MetricsLogger mMetricsLogger = new com.android.internal.logging.MetricsLogger();
    private final android.window.OnBackInvokedCallback mBackCallback = new android.window.OnBackInvokedCallback() { // from class: android.widget.Editor$$ExternalSyntheticLambda0
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            android.widget.Editor.this.lambda$startActionModeInternal$0();
        }
    };
    private final java.lang.Runnable mUpdateMagnifierRunnable = new java.lang.Runnable() { // from class: android.widget.Editor.1
        @Override // java.lang.Runnable
        public void run() {
            android.widget.Editor.this.mMagnifierAnimator.update();
        }
    };
    private final android.view.ViewTreeObserver.OnDrawListener mMagnifierOnDrawListener = new android.view.ViewTreeObserver.OnDrawListener() { // from class: android.widget.Editor.2
        @Override // android.view.ViewTreeObserver.OnDrawListener
        public void onDraw() {
            if (android.widget.Editor.this.mMagnifierAnimator != null) {
                android.widget.Editor.this.mTextView.post(android.widget.Editor.this.mUpdateMagnifierRunnable);
            }
        }
    };
    private boolean mHasPendingRestartInputForSetText = false;
    int mInputType = 0;
    boolean mCursorVisible = true;
    boolean mShowSoftInputOnFocus = true;
    android.graphics.drawable.Drawable mDrawableForCursor = null;
    private final android.widget.EditorTouchState mTouchState = new android.widget.EditorTouchState();
    private final android.widget.Editor.CursorAnchorInfoNotifier mCursorAnchorInfoNotifier = new android.widget.Editor.CursorAnchorInfoNotifier();
    private final java.lang.Runnable mShowFloatingToolbar = new java.lang.Runnable() { // from class: android.widget.Editor.3
        @Override // java.lang.Runnable
        public void run() {
            if (android.widget.Editor.this.mTextActionMode != null) {
                android.widget.Editor.this.mTextActionMode.hide(0L);
            }
        }
    };
    boolean mIsInsertionActionModeStartPending = false;
    private final android.widget.Editor.SuggestionHelper mSuggestionHelper = new android.widget.Editor.SuggestionHelper();
    private float mInitialZoom = 1.0f;
    private final android.view.MenuItem.OnMenuItemClickListener mOnContextMenuItemClickListener = new android.view.MenuItem.OnMenuItemClickListener() { // from class: android.widget.Editor.5
        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(android.view.MenuItem menuItem) {
            if (android.widget.Editor.this.mProcessTextIntentActionsHandler.performMenuItemAction(menuItem)) {
                return true;
            }
            return android.widget.Editor.this.mTextView.onTextContextMenuItem(menuItem.getItemId());
        }
    };

    private interface CursorController extends android.view.ViewTreeObserver.OnTouchModeChangeListener {
        void hide();

        boolean isActive();

        boolean isCursorBeingModified();

        void onDetached();

        void show();
    }

    private interface EasyEditDeleteListener {
        void onDeleteClick(android.text.style.EasyEditSpan easyEditSpan);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HandleType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface MagnifierHandleTrigger {
        public static final int INSERTION = 0;
        public static final int SELECTION_END = 2;
        public static final int SELECTION_START = 1;
    }

    @interface TextActionMode {
        public static final int INSERTION = 1;
        public static final int SELECTION = 0;
        public static final int TEXT_LINK = 2;
    }

    private interface TextViewPositionListener {
        void updatePosition(int i, int i2, boolean z, boolean z2);
    }

    private static class TextRenderNode {
        boolean isDirty = true;
        boolean needsToBeShifted = true;
        android.graphics.RenderNode renderNode;

        public TextRenderNode(java.lang.String str) {
            this.renderNode = android.graphics.RenderNode.create(str, null);
        }

        boolean needsRecord() {
            return this.isDirty || !this.renderNode.hasDisplayList();
        }
    }

    Editor(android.widget.TextView textView) {
        boolean z;
        boolean z2;
        boolean z3;
        this.mTextView = textView;
        this.mTextView.setFilters(this.mTextView.getFilters());
        this.mProcessTextIntentActionsHandler = new android.widget.Editor.ProcessTextIntentActionsHandler();
        this.mA11ySmartActions = new android.widget.Editor.AccessibilitySmartActions(this.mTextView);
        this.mHapticTextHandleEnabled = this.mTextView.getContext().getResources().getBoolean(com.android.internal.R.bool.config_enableHapticTextHandle);
        if (android.app.AppGlobals.getIntCoreSetting(android.widget.WidgetFlags.KEY_ENABLE_CURSOR_DRAG_FROM_ANYWHERE, 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mFlagCursorDragFromAnywhereEnabled = z;
        this.mCursorDragDirectionMinXYRatio = android.widget.EditorTouchState.getXYRatio(android.app.AppGlobals.getIntCoreSetting(android.widget.WidgetFlags.KEY_CURSOR_DRAG_MIN_ANGLE_FROM_VERTICAL, 45));
        if (android.app.AppGlobals.getIntCoreSetting(android.widget.WidgetFlags.KEY_ENABLE_INSERTION_HANDLE_GESTURES, 0) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mFlagInsertionHandleGesturesEnabled = z2;
        if (android.app.AppGlobals.getIntCoreSetting(android.widget.WidgetFlags.KEY_ENABLE_NEW_MAGNIFIER, 0) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.mNewMagnifierEnabled = z3;
        this.mLineSlopRatio = android.app.AppGlobals.getFloatCoreSetting(android.widget.WidgetFlags.KEY_LINE_SLOP_RATIO, 0.5f);
        this.mUseNewContextMenu = android.app.AppGlobals.getIntCoreSetting(android.text.TextFlags.KEY_ENABLE_NEW_CONTEXT_MENU, 1) != 0;
        this.mLineChangeSlopMax = (int) android.util.TypedValue.applyDimension(1, 45.0f, this.mTextView.getContext().getResources().getDisplayMetrics());
        this.mLineChangeSlopMin = (int) android.util.TypedValue.applyDimension(1, 8.0f, this.mTextView.getContext().getResources().getDisplayMetrics());
    }

    public boolean getFlagCursorDragFromAnywhereEnabled() {
        return this.mFlagCursorDragFromAnywhereEnabled;
    }

    public void setFlagCursorDragFromAnywhereEnabled(boolean z) {
        this.mFlagCursorDragFromAnywhereEnabled = z;
    }

    public void setCursorDragMinAngleFromVertical(int i) {
        this.mCursorDragDirectionMinXYRatio = android.widget.EditorTouchState.getXYRatio(i);
    }

    public boolean getFlagInsertionHandleGesturesEnabled() {
        return this.mFlagInsertionHandleGesturesEnabled;
    }

    public void setFlagInsertionHandleGesturesEnabled(boolean z) {
        this.mFlagInsertionHandleGesturesEnabled = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.widget.Editor.MagnifierMotionAnimator getMagnifierAnimator() {
        android.widget.Magnifier.Builder createBuilderWithOldMagnifierDefaults;
        if (this.mMagnifierAnimator == null) {
            if (this.mNewMagnifierEnabled) {
                createBuilderWithOldMagnifierDefaults = createBuilderWithInlineMagnifierDefaults();
            } else {
                createBuilderWithOldMagnifierDefaults = android.widget.Magnifier.createBuilderWithOldMagnifierDefaults(this.mTextView);
            }
            this.mMagnifierAnimator = new android.widget.Editor.MagnifierMotionAnimator(createBuilderWithOldMagnifierDefaults.build());
        }
        return this.mMagnifierAnimator;
    }

    private android.widget.Magnifier.Builder createBuilderWithInlineMagnifierDefaults() {
        android.widget.Magnifier.Builder builder = new android.widget.Magnifier.Builder(this.mTextView);
        float f = 1.5f;
        float floatCoreSetting = android.app.AppGlobals.getFloatCoreSetting(android.widget.WidgetFlags.KEY_MAGNIFIER_ZOOM_FACTOR, 1.5f);
        float f2 = 5.5f;
        float floatCoreSetting2 = android.app.AppGlobals.getFloatCoreSetting(android.widget.WidgetFlags.KEY_MAGNIFIER_ASPECT_RATIO, 5.5f);
        if (floatCoreSetting >= 1.2f && floatCoreSetting <= 1.8f) {
            f = floatCoreSetting;
        }
        if (floatCoreSetting2 >= 3.0f && floatCoreSetting2 <= 8.0f) {
            f2 = floatCoreSetting2;
        }
        this.mInitialZoom = f;
        this.mMinLineHeightForMagnifier = (int) android.util.TypedValue.applyDimension(1, 20.0f, this.mTextView.getContext().getResources().getDisplayMetrics());
        this.mMaxLineHeightForMagnifier = (int) android.util.TypedValue.applyDimension(1, 32.0f, this.mTextView.getContext().getResources().getDisplayMetrics());
        android.text.Layout layout = this.mTextView.getLayout();
        int lineForOffset = layout.getLineForOffset(this.mTextView.getSelectionStartTransformed());
        int lineBottom = layout.getLineBottom(lineForOffset, false) - layout.getLineTop(lineForOffset);
        int max = (int) (f2 * java.lang.Math.max(lineBottom, this.mMinLineHeightForMagnifier));
        builder.setFishEyeStyle().setSize(max, (int) (lineBottom * f)).setSourceSize(max, lineBottom).setElevation(0.0f).setInitialZoom(f).setClippingEnabled(false);
        android.content.res.TypedArray obtainStyledAttributes = this.mTextView.getContext().obtainStyledAttributes(null, com.android.internal.R.styleable.Magnifier, com.android.internal.R.attr.magnifierStyle, 0);
        builder.setDefaultSourceToMagnifierOffset(obtainStyledAttributes.getDimensionPixelSize(3, 0), obtainStyledAttributes.getDimensionPixelSize(4, 0));
        obtainStyledAttributes.recycle();
        return builder.setSourceBounds(1, 0, 1, 0);
    }

    android.os.ParcelableParcel saveInstanceState() {
        android.os.ParcelableParcel parcelableParcel = new android.os.ParcelableParcel(getClass().getClassLoader());
        android.os.Parcel parcel = parcelableParcel.getParcel();
        this.mUndoManager.saveInstanceState(parcel);
        this.mUndoInputFilter.saveInstanceState(parcel);
        return parcelableParcel;
    }

    void restoreInstanceState(android.os.ParcelableParcel parcelableParcel) {
        android.os.Parcel parcel = parcelableParcel.getParcel();
        this.mUndoManager.restoreInstanceState(parcel, parcelableParcel.getClassLoader());
        this.mUndoInputFilter.restoreInstanceState(parcel);
        this.mUndoOwner = this.mUndoManager.getOwner("Editor", this);
    }

    public android.widget.TextViewOnReceiveContentListener getDefaultOnReceiveContentListener() {
        return this.mDefaultOnReceiveContentListener;
    }

    void forgetUndoRedo() {
        android.content.UndoOwner[] undoOwnerArr = {this.mUndoOwner};
        this.mUndoManager.forgetUndos(undoOwnerArr, -1);
        this.mUndoManager.forgetRedos(undoOwnerArr, -1);
    }

    boolean canUndo() {
        return this.mAllowUndo && this.mUndoManager.countUndos(new android.content.UndoOwner[]{this.mUndoOwner}) > 0;
    }

    boolean canRedo() {
        return this.mAllowUndo && this.mUndoManager.countRedos(new android.content.UndoOwner[]{this.mUndoOwner}) > 0;
    }

    void undo() {
        if (!this.mAllowUndo) {
            return;
        }
        this.mUndoManager.undo(new android.content.UndoOwner[]{this.mUndoOwner}, 1);
    }

    void redo() {
        if (!this.mAllowUndo) {
            return;
        }
        this.mUndoManager.redo(new android.content.UndoOwner[]{this.mUndoOwner}, 1);
    }

    void replace() {
        if (this.mSuggestionsPopupWindow == null) {
            this.mSuggestionsPopupWindow = new android.widget.Editor.SuggestionsPopupWindow();
        }
        hideCursorAndSpanControllers();
        this.mSuggestionsPopupWindow.show();
        android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), (this.mTextView.getSelectionStart() + this.mTextView.getSelectionEnd()) / 2);
    }

    void onAttachedToWindow() {
        if (this.mShowErrorAfterAttach) {
            showError();
            this.mShowErrorAfterAttach = false;
        }
        android.view.ViewTreeObserver viewTreeObserver = this.mTextView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            if (this.mInsertionPointCursorController != null) {
                viewTreeObserver.addOnTouchModeChangeListener(this.mInsertionPointCursorController);
            }
            if (this.mSelectionModifierCursorController != null) {
                this.mSelectionModifierCursorController.resetTouchOffsets();
                viewTreeObserver.addOnTouchModeChangeListener(this.mSelectionModifierCursorController);
            }
            viewTreeObserver.addOnDrawListener(this.mMagnifierOnDrawListener);
        }
        updateSpellCheckSpans(0, this.mTextView.getText().length(), true);
        if (this.mTextView.hasSelection()) {
            refreshTextActionMode();
        }
        getPositionListener().addSubscriber(this.mCursorAnchorInfoNotifier, true);
        resumeBlink();
    }

    void onDetachedFromWindow() {
        getPositionListener().removeSubscriber(this.mCursorAnchorInfoNotifier);
        if (this.mError != null) {
            hideError();
        }
        suspendBlink();
        if (this.mInsertionPointCursorController != null) {
            this.mInsertionPointCursorController.onDetached();
        }
        if (this.mSelectionModifierCursorController != null) {
            this.mSelectionModifierCursorController.onDetached();
        }
        if (this.mShowSuggestionRunnable != null) {
            this.mTextView.removeCallbacks(this.mShowSuggestionRunnable);
        }
        if (this.mInsertionActionModeRunnable != null) {
            this.mTextView.removeCallbacks(this.mInsertionActionModeRunnable);
        }
        this.mTextView.removeCallbacks(this.mShowFloatingToolbar);
        discardTextDisplayLists();
        if (this.mSpellChecker != null) {
            this.mSpellChecker.closeSession();
            this.mSpellChecker = null;
        }
        android.view.ViewTreeObserver viewTreeObserver = this.mTextView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnDrawListener(this.mMagnifierOnDrawListener);
        }
        hideCursorAndSpanControllers();
        stopTextActionModeWithPreservingSelection();
        this.mDefaultOnReceiveContentListener.clearInputConnectionInfo();
        unregisterOnBackInvokedCallback();
    }

    private void unregisterOnBackInvokedCallback() {
        android.view.ViewRootImpl viewRootImpl;
        if (this.mBackCallbackRegistered && (viewRootImpl = getTextView().getViewRootImpl()) != null && viewRootImpl.getOnBackInvokedDispatcher().isOnBackInvokedCallbackEnabled()) {
            viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mBackCallback);
            this.mBackCallbackRegistered = false;
        }
    }

    private void registerOnBackInvokedCallback() {
        android.view.ViewRootImpl viewRootImpl;
        if (!this.mBackCallbackRegistered && (viewRootImpl = this.mTextView.getViewRootImpl()) != null && viewRootImpl.getOnBackInvokedDispatcher().isOnBackInvokedCallbackEnabled()) {
            viewRootImpl.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mBackCallback);
            this.mBackCallbackRegistered = true;
        }
    }

    private void discardTextDisplayLists() {
        if (this.mTextRenderNodes != null) {
            for (int i = 0; i < this.mTextRenderNodes.length; i++) {
                android.graphics.RenderNode renderNode = this.mTextRenderNodes[i] != null ? this.mTextRenderNodes[i].renderNode : null;
                if (renderNode != null && renderNode.hasDisplayList()) {
                    renderNode.discardDisplayList();
                }
            }
        }
    }

    private void showError() {
        if (this.mTextView.getWindowToken() == null) {
            this.mShowErrorAfterAttach = true;
            return;
        }
        if (this.mErrorPopup == null) {
            android.widget.TextView textView = (android.widget.TextView) android.view.LayoutInflater.from(this.mTextView.getContext()).inflate(com.android.internal.R.layout.textview_hint, (android.view.ViewGroup) null);
            float f = this.mTextView.getResources().getDisplayMetrics().density;
            this.mErrorPopup = new android.widget.Editor.ErrorPopup(textView, (int) ((200.0f * f) + 0.5f), (int) ((f * 50.0f) + 0.5f));
            this.mErrorPopup.setFocusable(false);
            this.mErrorPopup.setInputMethodMode(1);
        }
        android.widget.TextView textView2 = (android.widget.TextView) this.mErrorPopup.getContentView();
        chooseSize(this.mErrorPopup, this.mError, textView2);
        textView2.lambda$setTextAsync$0(this.mError);
        this.mErrorPopup.showAsDropDown(this.mTextView, getErrorX(), getErrorY(), 51);
        this.mErrorPopup.fixDirection(this.mErrorPopup.isAboveAnchor());
    }

    public void setError(java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable) {
        this.mError = android.text.TextUtils.stringOrSpannedString(charSequence);
        this.mErrorWasChanged = true;
        if (this.mError == null) {
            setErrorIcon(null);
            if (this.mErrorPopup != null) {
                if (this.mErrorPopup.isShowing()) {
                    this.mErrorPopup.dismiss();
                }
                this.mErrorPopup = null;
            }
            this.mShowErrorAfterAttach = false;
            return;
        }
        setErrorIcon(drawable);
        if (this.mTextView.isFocused()) {
            showError();
        }
    }

    private void setErrorIcon(android.graphics.drawable.Drawable drawable) {
        android.widget.TextView.Drawables drawables = this.mTextView.mDrawables;
        if (drawables == null) {
            android.widget.TextView textView = this.mTextView;
            android.widget.TextView.Drawables drawables2 = new android.widget.TextView.Drawables(this.mTextView.getContext());
            textView.mDrawables = drawables2;
            drawables = drawables2;
        }
        drawables.setErrorDrawable(drawable, this.mTextView);
        this.mTextView.resetResolvedDrawables();
        this.mTextView.invalidate();
        this.mTextView.requestLayout();
    }

    private void hideError() {
        if (this.mErrorPopup != null && this.mErrorPopup.isShowing()) {
            this.mErrorPopup.dismiss();
        }
        this.mShowErrorAfterAttach = false;
    }

    private int getErrorX() {
        float f = this.mTextView.getResources().getDisplayMetrics().density;
        android.widget.TextView.Drawables drawables = this.mTextView.mDrawables;
        switch (this.mTextView.getLayoutDirection()) {
            case 1:
                return this.mTextView.getPaddingLeft() + (((drawables != null ? drawables.mDrawableSizeLeft : 0) / 2) - ((int) ((f * 25.0f) + 0.5f)));
            default:
                return ((this.mTextView.getWidth() - this.mErrorPopup.getWidth()) - this.mTextView.getPaddingRight()) + ((-(drawables != null ? drawables.mDrawableSizeRight : 0)) / 2) + ((int) ((f * 25.0f) + 0.5f));
        }
    }

    private int getErrorY() {
        int compoundPaddingTop = this.mTextView.getCompoundPaddingTop();
        int bottom = ((this.mTextView.getBottom() - this.mTextView.getTop()) - this.mTextView.getCompoundPaddingBottom()) - compoundPaddingTop;
        android.widget.TextView.Drawables drawables = this.mTextView.mDrawables;
        int i = 0;
        switch (this.mTextView.getLayoutDirection()) {
            case 1:
                if (drawables != null) {
                    i = drawables.mDrawableHeightLeft;
                    break;
                }
                break;
            default:
                if (drawables != null) {
                    i = drawables.mDrawableHeightRight;
                    break;
                }
                break;
        }
        return (((compoundPaddingTop + ((bottom - i) / 2)) + i) - this.mTextView.getHeight()) - ((int) ((this.mTextView.getResources().getDisplayMetrics().density * 2.0f) + 0.5f));
    }

    void createInputContentTypeIfNeeded() {
        if (this.mInputContentType == null) {
            this.mInputContentType = new android.widget.Editor.InputContentType();
        }
    }

    void createInputMethodStateIfNeeded() {
        if (this.mInputMethodState == null) {
            this.mInputMethodState = new android.widget.Editor.InputMethodState();
        }
    }

    private boolean isCursorVisible() {
        return this.mCursorVisible && this.mTextView.isTextEditable();
    }

    boolean shouldRenderCursor() {
        if (isCursorVisible()) {
            return this.mRenderCursorRegardlessTiming || (android.os.SystemClock.uptimeMillis() - this.mShowCursor) % 1000 < 500;
        }
        return false;
    }

    void prepareCursorControllers() {
        boolean z;
        android.view.ViewGroup.LayoutParams layoutParams = this.mTextView.getRootView().getLayoutParams();
        if (!(layoutParams instanceof android.view.WindowManager.LayoutParams)) {
            z = false;
        } else {
            android.view.WindowManager.LayoutParams layoutParams2 = (android.view.WindowManager.LayoutParams) layoutParams;
            z = layoutParams2.type < 1000 || layoutParams2.type > 1999;
        }
        boolean z2 = z && this.mTextView.getLayout() != null;
        this.mInsertionControllerEnabled = z2 && (this.mDrawCursorOnMagnifier || isCursorVisible());
        this.mSelectionControllerEnabled = z2 && this.mTextView.textCanBeSelected();
        if (!this.mInsertionControllerEnabled) {
            hideInsertionPointCursorController();
            if (this.mInsertionPointCursorController != null) {
                this.mInsertionPointCursorController.onDetached();
                this.mInsertionPointCursorController = null;
            }
        }
        if (!this.mSelectionControllerEnabled) {
            lambda$startActionModeInternal$0();
            if (this.mSelectionModifierCursorController != null) {
                this.mSelectionModifierCursorController.onDetached();
                this.mSelectionModifierCursorController = null;
            }
        }
    }

    void hideInsertionPointCursorController() {
        if (this.mInsertionPointCursorController != null) {
            this.mInsertionPointCursorController.hide();
        }
    }

    void hideCursorAndSpanControllers() {
        hideCursorControllers();
        hideSpanControllers();
    }

    private void hideSpanControllers() {
        if (this.mSpanController != null) {
            this.mSpanController.hide();
        }
    }

    private void hideCursorControllers() {
        if (this.mSuggestionsPopupWindow != null && (this.mTextView.isInExtractedMode() || !this.mSuggestionsPopupWindow.isShowingUp())) {
            this.mSuggestionsPopupWindow.hide();
        }
        hideInsertionPointCursorController();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSpellCheckSpans(int i, int i2, boolean z) {
        this.mTextView.removeAdjacentSuggestionSpans(i);
        this.mTextView.removeAdjacentSuggestionSpans(i2);
        if (this.mTextView.isTextEditable() && this.mTextView.isSuggestionsEnabled() && !this.mTextView.isInExtractedMode()) {
            android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
            if (inputMethodManager != null && inputMethodManager.isInputMethodSuppressingSpellChecker()) {
                return;
            }
            if (this.mSpellChecker == null && z) {
                this.mSpellChecker = new android.widget.SpellChecker(this.mTextView);
            }
            if (this.mSpellChecker != null) {
                this.mSpellChecker.spellCheck(i, i2);
            }
        }
    }

    void onScreenStateChanged(int i) {
        switch (i) {
            case 0:
                suspendBlink();
                break;
            case 1:
                resumeBlink();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void suspendBlink() {
        if (this.mBlink != null) {
            this.mBlink.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resumeBlink() {
        if (this.mBlink != null) {
            this.mBlink.uncancel();
        }
        makeBlink();
    }

    void adjustInputType(boolean z, boolean z2, boolean z3, boolean z4) {
        if ((this.mInputType & 15) == 1) {
            if (z || z2) {
                this.mInputType = (this.mInputType & (-4081)) | 128;
            }
            if (z3) {
                this.mInputType = (this.mInputType & (-4081)) | 224;
                return;
            }
            return;
        }
        if ((this.mInputType & 15) == 2 && z4) {
            this.mInputType = (this.mInputType & (-4081)) | 16;
        }
    }

    private void chooseSize(android.widget.PopupWindow popupWindow, java.lang.CharSequence charSequence, android.widget.TextView textView) {
        int paddingLeft = textView.getPaddingLeft() + textView.getPaddingRight();
        int paddingTop = textView.getPaddingTop() + textView.getPaddingBottom();
        android.text.StaticLayout build = android.text.StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), textView.getPaint(), this.mTextView.getResources().getDimensionPixelSize(com.android.internal.R.dimen.textview_error_popup_default_width)).setUseLineSpacingFromFallbacks(textView.isFallbackLineSpacingForStaticLayout()).build();
        float f = 0.0f;
        for (int i = 0; i < build.getLineCount(); i++) {
            f = java.lang.Math.max(f, build.getLineWidth(i));
        }
        popupWindow.setWidth(paddingLeft + ((int) java.lang.Math.ceil(f)));
        popupWindow.setHeight(paddingTop + build.getHeight());
    }

    void setFrame() {
        if (this.mErrorPopup != null) {
            chooseSize(this.mErrorPopup, this.mError, (android.widget.TextView) this.mErrorPopup.getContentView());
            this.mErrorPopup.update(this.mTextView, getErrorX(), getErrorY(), this.mErrorPopup.getWidth(), this.mErrorPopup.getHeight());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWordStart(int i) {
        int prevWordBeginningOnTwoWordsBoundary;
        if (getWordIteratorWithText().isOnPunctuation(getWordIteratorWithText().prevBoundary(i))) {
            prevWordBeginningOnTwoWordsBoundary = getWordIteratorWithText().getPunctuationBeginning(i);
        } else {
            prevWordBeginningOnTwoWordsBoundary = getWordIteratorWithText().getPrevWordBeginningOnTwoWordsBoundary(i);
        }
        if (prevWordBeginningOnTwoWordsBoundary == -1) {
            return i;
        }
        return prevWordBeginningOnTwoWordsBoundary;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWordEnd(int i) {
        int nextWordEndOnTwoWordBoundary;
        if (getWordIteratorWithText().isAfterPunctuation(getWordIteratorWithText().nextBoundary(i))) {
            nextWordEndOnTwoWordBoundary = getWordIteratorWithText().getPunctuationEnd(i);
        } else {
            nextWordEndOnTwoWordBoundary = getWordIteratorWithText().getNextWordEndOnTwoWordBoundary(i);
        }
        if (nextWordEndOnTwoWordBoundary == -1) {
            return i;
        }
        return nextWordEndOnTwoWordBoundary;
    }

    private boolean needsToSelectAllToSelectWordOrParagraph() {
        if (this.mTextView.hasPasswordTransformationMethod()) {
            return true;
        }
        int inputType = this.mTextView.getInputType();
        int i = inputType & 15;
        int i2 = inputType & android.text.InputType.TYPE_MASK_VARIATION;
        return i == 2 || i == 3 || i == 4 || i2 == 16 || i2 == 32 || i2 == 208 || i2 == 176;
    }

    boolean selectCurrentWord() {
        int i;
        int i2;
        if (!this.mTextView.canSelectText()) {
            return false;
        }
        if (needsToSelectAllToSelectWordOrParagraph()) {
            return this.mTextView.selectAllText();
        }
        long lastTouchOffsets = getLastTouchOffsets();
        int unpackRangeStartFromLong = android.text.TextUtils.unpackRangeStartFromLong(lastTouchOffsets);
        int unpackRangeEndFromLong = android.text.TextUtils.unpackRangeEndFromLong(lastTouchOffsets);
        if (unpackRangeStartFromLong < 0 || unpackRangeStartFromLong > this.mTextView.getText().length() || unpackRangeEndFromLong < 0 || unpackRangeEndFromLong > this.mTextView.getText().length()) {
            return false;
        }
        android.text.style.URLSpan[] uRLSpanArr = (android.text.style.URLSpan[]) ((android.text.Spanned) this.mTextView.getText()).getSpans(unpackRangeStartFromLong, unpackRangeEndFromLong, android.text.style.URLSpan.class);
        if (uRLSpanArr.length >= 1) {
            android.text.style.URLSpan uRLSpan = uRLSpanArr[0];
            i = ((android.text.Spanned) this.mTextView.getText()).getSpanStart(uRLSpan);
            i2 = ((android.text.Spanned) this.mTextView.getText()).getSpanEnd(uRLSpan);
        } else {
            android.text.method.WordIterator wordIterator = getWordIterator();
            wordIterator.setCharSequence(this.mTextView.getText(), unpackRangeStartFromLong, unpackRangeEndFromLong);
            int beginning = wordIterator.getBeginning(unpackRangeStartFromLong);
            int end = wordIterator.getEnd(unpackRangeEndFromLong);
            if (beginning == -1 || end == -1 || beginning == end) {
                long charClusterRange = getCharClusterRange(unpackRangeStartFromLong);
                int unpackRangeStartFromLong2 = android.text.TextUtils.unpackRangeStartFromLong(charClusterRange);
                int unpackRangeEndFromLong2 = android.text.TextUtils.unpackRangeEndFromLong(charClusterRange);
                i = unpackRangeStartFromLong2;
                i2 = unpackRangeEndFromLong2;
            } else {
                i2 = end;
                i = beginning;
            }
        }
        android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), i, i2);
        return i2 > i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean selectCurrentParagraph() {
        if (!this.mTextView.canSelectText()) {
            return false;
        }
        if (needsToSelectAllToSelectWordOrParagraph()) {
            return this.mTextView.selectAllText();
        }
        long lastTouchOffsets = getLastTouchOffsets();
        long paragraphsRange = getParagraphsRange(android.text.TextUtils.unpackRangeStartFromLong(lastTouchOffsets), android.text.TextUtils.unpackRangeEndFromLong(lastTouchOffsets));
        int unpackRangeStartFromLong = android.text.TextUtils.unpackRangeStartFromLong(paragraphsRange);
        int unpackRangeEndFromLong = android.text.TextUtils.unpackRangeEndFromLong(paragraphsRange);
        if (unpackRangeStartFromLong >= unpackRangeEndFromLong) {
            return false;
        }
        android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), unpackRangeStartFromLong, unpackRangeEndFromLong);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getParagraphsRange(int i, int i2) {
        int originalToTransformed = this.mTextView.originalToTransformed(i, 1);
        int originalToTransformed2 = this.mTextView.originalToTransformed(i2, 1);
        android.text.Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return android.text.TextUtils.packRangeInLong(-1, -1);
        }
        java.lang.CharSequence text = layout.getText();
        int lineForOffset = layout.getLineForOffset(originalToTransformed);
        while (lineForOffset > 0 && text.charAt(layout.getLineEnd(lineForOffset - 1) - 1) != '\n') {
            lineForOffset--;
        }
        int lineForOffset2 = layout.getLineForOffset(originalToTransformed2);
        while (lineForOffset2 < layout.getLineCount() - 1 && text.charAt(layout.getLineEnd(lineForOffset2) - 1) != '\n') {
            lineForOffset2++;
        }
        return android.text.TextUtils.packRangeInLong(this.mTextView.transformedToOriginal(layout.getLineStart(lineForOffset), 1), this.mTextView.transformedToOriginal(layout.getLineEnd(lineForOffset2), 1));
    }

    void onLocaleChanged() {
        this.mWordIterator = null;
        this.mWordIteratorWithText = null;
    }

    public android.text.method.WordIterator getWordIterator() {
        if (this.mWordIterator == null) {
            this.mWordIterator = new android.text.method.WordIterator(this.mTextView.getTextServicesLocale());
        }
        return this.mWordIterator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.text.method.WordIterator getWordIteratorWithText() {
        if (this.mWordIteratorWithText == null) {
            this.mWordIteratorWithText = new android.text.method.WordIterator(this.mTextView.getTextServicesLocale());
            this.mUpdateWordIteratorText = true;
        }
        if (this.mUpdateWordIteratorText) {
            java.lang.CharSequence text = this.mTextView.getText();
            this.mWordIteratorWithText.setCharSequence(text, 0, text.length());
            this.mUpdateWordIteratorText = false;
        }
        return this.mWordIteratorWithText;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNextCursorOffset(int i, boolean z) {
        int offsetToRightOf;
        android.text.Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return i;
        }
        int originalToTransformed = this.mTextView.originalToTransformed(i, 1);
        if (z == layout.isRtlCharAt(originalToTransformed)) {
            offsetToRightOf = layout.getOffsetToLeftOf(originalToTransformed);
        } else {
            offsetToRightOf = layout.getOffsetToRightOf(originalToTransformed);
        }
        return this.mTextView.transformedToOriginal(offsetToRightOf, 1);
    }

    private long getCharClusterRange(int i) {
        if (i < this.mTextView.getText().length()) {
            int nextCursorOffset = getNextCursorOffset(i, true);
            return android.text.TextUtils.packRangeInLong(getNextCursorOffset(nextCursorOffset, false), nextCursorOffset);
        }
        if (i - 1 >= 0) {
            int nextCursorOffset2 = getNextCursorOffset(i, false);
            return android.text.TextUtils.packRangeInLong(nextCursorOffset2, getNextCursorOffset(nextCursorOffset2, true));
        }
        return android.text.TextUtils.packRangeInLong(i, i);
    }

    private boolean touchPositionIsInSelection() {
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        if (selectionStart == selectionEnd) {
            return false;
        }
        if (selectionStart > selectionEnd) {
            android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), selectionEnd, selectionStart);
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        android.widget.Editor.SelectionModifierCursorController selectionController = getSelectionController();
        return selectionController.getMinTouchOffset() >= selectionStart && selectionController.getMaxTouchOffset() < selectionEnd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.widget.Editor.PositionListener getPositionListener() {
        if (this.mPositionListener == null) {
            this.mPositionListener = new android.widget.Editor.PositionListener();
        }
        return this.mPositionListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOffsetVisible(int i) {
        android.text.Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return false;
        }
        int lineBottom = layout.getLineBottom(layout.getLineForOffset(this.mTextView.originalToTransformed(i, 1)));
        return this.mTextView.isPositionVisible(((int) layout.getPrimaryHorizontal(r4)) + this.mTextView.viewportToContentHorizontalOffset(), lineBottom + this.mTextView.viewportToContentVerticalOffset());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPositionOnText(float f, float f2) {
        android.text.Layout layout = this.mTextView.getLayout();
        if (layout == null) {
            return false;
        }
        int lineAtCoordinate = this.mTextView.getLineAtCoordinate(f2);
        float convertToLocalHorizontalCoordinate = this.mTextView.convertToLocalHorizontalCoordinate(f);
        if (convertToLocalHorizontalCoordinate < layout.getLineLeft(lineAtCoordinate) || convertToLocalHorizontalCoordinate > layout.getLineRight(lineAtCoordinate)) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDragAndDrop() {
        getSelectionActionModeHelper().onSelectionDrag();
        if (this.mTextView.isInExtractedMode()) {
            return;
        }
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        this.mTextView.startDragAndDrop(android.content.ClipData.newPlainText(null, this.mTextView.getTransformedText(selectionStart, selectionEnd)), getTextThumbnailBuilder(selectionStart, selectionEnd), new android.widget.Editor.DragLocalState(this.mTextView, selectionStart, selectionEnd), 256);
        lambda$startActionModeInternal$0();
        if (hasSelectionController()) {
            getSelectionController().resetTouchOffsets();
        }
    }

    public boolean performLongClick(boolean z) {
        if (this.mIsBeingLongClickedByAccessibility) {
            if (!z) {
                toggleInsertionActionMode();
            }
            return true;
        }
        if (!z && !isPositionOnText(this.mTouchState.getLastDownX(), this.mTouchState.getLastDownY()) && !this.mTouchState.isOnHandle() && this.mInsertionControllerEnabled) {
            android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), this.mTextView.getOffsetForPosition(this.mTouchState.getLastDownX(), this.mTouchState.getLastDownY()));
            getInsertionController().show();
            this.mIsInsertionActionModeStartPending = true;
            com.android.internal.logging.MetricsLogger.action(this.mTextView.getContext(), 629, 0);
            z = true;
        }
        if (!z && this.mTextActionMode != null) {
            if (touchPositionIsInSelection()) {
                startDragAndDrop();
                com.android.internal.logging.MetricsLogger.action(this.mTextView.getContext(), 629, 2);
            } else {
                lambda$startActionModeInternal$0();
                selectCurrentWordAndStartDrag();
                com.android.internal.logging.MetricsLogger.action(this.mTextView.getContext(), 629, 1);
            }
            z = true;
        }
        if (!z && (z = selectCurrentWordAndStartDrag())) {
            com.android.internal.logging.MetricsLogger.action(this.mTextView.getContext(), 629, 1);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleInsertionActionMode() {
        if (this.mTextActionMode != null) {
            lambda$startActionModeInternal$0();
        } else {
            startInsertionActionMode();
        }
    }

    float getLastUpPositionX() {
        return this.mTouchState.getLastUpX();
    }

    float getLastUpPositionY() {
        return this.mTouchState.getLastUpY();
    }

    private long getLastTouchOffsets() {
        android.widget.Editor.SelectionModifierCursorController selectionController = getSelectionController();
        return android.text.TextUtils.packRangeInLong(selectionController.getMinTouchOffset(), selectionController.getMaxTouchOffset());
    }

    void onFocusChanged(boolean z, int i) {
        this.mShowCursor = android.os.SystemClock.uptimeMillis();
        ensureEndedBatchEdit();
        if (z) {
            int selectionStart = this.mTextView.getSelectionStart();
            int selectionEnd = this.mTextView.getSelectionEnd();
            this.mCreatedWithASelection = this.mFrozenWithFocus && this.mTextView.hasSelection() && !(this.mSelectAllOnFocus && selectionStart == 0 && selectionEnd == this.mTextView.getText().length());
            if (!this.mFrozenWithFocus || selectionStart < 0 || selectionEnd < 0) {
                int lastTapPosition = getLastTapPosition();
                if (lastTapPosition >= 0) {
                    android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), lastTapPosition);
                }
                android.text.method.MovementMethod movementMethod = this.mTextView.getMovementMethod();
                if (movementMethod != null) {
                    movementMethod.onTakeFocus(this.mTextView, (android.text.Spannable) this.mTextView.getText(), i);
                }
                if ((this.mTextView.isInExtractedMode() || this.mSelectionMoved) && selectionStart >= 0 && selectionEnd >= 0) {
                    android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
                }
                if (this.mSelectAllOnFocus) {
                    this.mTextView.selectAllText();
                }
                this.mTouchFocusSelected = true;
            }
            this.mFrozenWithFocus = false;
            this.mSelectionMoved = false;
            if (this.mError != null) {
                showError();
            }
            makeBlink();
            return;
        }
        if (this.mError != null) {
            hideError();
        }
        this.mTextView.onEndBatchEdit();
        if (this.mTextView.isInExtractedMode()) {
            hideCursorAndSpanControllers();
            stopTextActionModeWithPreservingSelection();
        } else {
            hideCursorAndSpanControllers();
            if (this.mTextView.isTemporarilyDetached()) {
                stopTextActionModeWithPreservingSelection();
            } else {
                lambda$startActionModeInternal$0();
            }
            downgradeEasyCorrectionSpans();
        }
        if (this.mSelectionModifierCursorController != null) {
            this.mSelectionModifierCursorController.resetTouchOffsets();
        }
        if (this.mInsertModeController != null) {
            this.mInsertModeController.exitInsertMode();
        }
        ensureNoSelectionIfNonSelectable();
    }

    private void ensureNoSelectionIfNonSelectable() {
        if (!this.mTextView.textCanBeSelected() && this.mTextView.hasSelection()) {
            android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), this.mTextView.length(), this.mTextView.length());
        }
    }

    private void downgradeEasyCorrectionSpans() {
        java.lang.CharSequence text = this.mTextView.getText();
        if (text instanceof android.text.Spannable) {
            android.text.Spannable spannable = (android.text.Spannable) text;
            android.text.style.SuggestionSpan[] suggestionSpanArr = (android.text.style.SuggestionSpan[]) spannable.getSpans(0, spannable.length(), android.text.style.SuggestionSpan.class);
            for (int i = 0; i < suggestionSpanArr.length; i++) {
                int flags = suggestionSpanArr[i].getFlags();
                if ((flags & 1) != 0 && (flags & 10) == 0) {
                    suggestionSpanArr[i].setFlags(flags & (-2));
                }
            }
        }
    }

    void sendOnTextChanged(int i, int i2, int i3) {
        getSelectionActionModeHelper().onTextChanged(i, i2 + i);
        updateSpellCheckSpans(i, i3 + i, false);
        this.mUpdateWordIteratorText = true;
        hideCursorControllers();
        if (this.mSelectionModifierCursorController != null) {
            this.mSelectionModifierCursorController.resetTouchOffsets();
        }
        lambda$startActionModeInternal$0();
    }

    private int getLastTapPosition() {
        int minTouchOffset;
        if (this.mSelectionModifierCursorController != null && (minTouchOffset = this.mSelectionModifierCursorController.getMinTouchOffset()) >= 0) {
            if (minTouchOffset > this.mTextView.getText().length()) {
                return this.mTextView.getText().length();
            }
            return minTouchOffset;
        }
        return -1;
    }

    void onWindowFocusChanged(boolean z) {
        if (z) {
            resumeBlink();
            if (this.mTextView.hasSelection() && !extractedTextModeWillBeStarted()) {
                refreshTextActionMode();
                return;
            }
            return;
        }
        suspendBlink();
        if (this.mInputContentType != null) {
            this.mInputContentType.enterDown = false;
        }
        hideCursorAndSpanControllers();
        stopTextActionModeWithPreservingSelection();
        if (this.mSuggestionsPopupWindow != null) {
            this.mSuggestionsPopupWindow.onParentLostFocus();
        }
        ensureEndedBatchEdit();
        ensureNoSelectionIfNonSelectable();
    }

    private boolean shouldFilterOutTouchEvent(android.view.MotionEvent motionEvent) {
        if (!motionEvent.isFromSource(8194)) {
            return false;
        }
        boolean z = ((this.mLastButtonState ^ motionEvent.getButtonState()) & 1) != 0;
        int actionMasked = motionEvent.getActionMasked();
        if ((actionMasked == 0 || actionMasked == 1) && !z) {
            return true;
        }
        return actionMasked == 2 && !motionEvent.isButtonPressed(1);
    }

    public void onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean shouldFilterOutTouchEvent = shouldFilterOutTouchEvent(motionEvent);
        this.mLastButtonState = motionEvent.getButtonState();
        if (shouldFilterOutTouchEvent) {
            if (motionEvent.getActionMasked() == 1) {
                this.mDiscardNextActionUp = true;
                return;
            }
            return;
        }
        this.mTouchState.update(motionEvent, android.view.ViewConfiguration.get(this.mTextView.getContext()));
        updateFloatingToolbarVisibility(motionEvent);
        if (hasInsertionController()) {
            getInsertionController().onTouchEvent(motionEvent);
        }
        if (hasSelectionController()) {
            getSelectionController().onTouchEvent(motionEvent);
        }
        if (this.mShowSuggestionRunnable != null) {
            this.mTextView.removeCallbacks(this.mShowSuggestionRunnable);
            this.mShowSuggestionRunnable = null;
        }
        if (motionEvent.getActionMasked() == 0) {
            this.mTouchFocusSelected = false;
            this.mIgnoreActionUpEvent = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFloatingToolbarVisibility(android.view.MotionEvent motionEvent) {
        if (this.mTextActionMode != null) {
            switch (motionEvent.getActionMasked()) {
                case 1:
                case 3:
                    showFloatingToolbar();
                    break;
                case 2:
                    hideFloatingToolbar(-1);
                    break;
            }
        }
    }

    void hideFloatingToolbar(int i) {
        if (this.mTextActionMode != null) {
            this.mTextView.removeCallbacks(this.mShowFloatingToolbar);
            this.mTextActionMode.hide(i);
        }
    }

    private void showFloatingToolbar() {
        if (this.mTextActionMode != null && this.mTextView.showUIForTouchScreen()) {
            this.mTextView.postDelayed(this.mShowFloatingToolbar, android.view.ViewConfiguration.getDoubleTapTimeout());
            invalidateActionModeAsync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.inputmethod.InputMethodManager getInputMethodManager() {
        return (android.view.inputmethod.InputMethodManager) this.mTextView.getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
    }

    public void beginBatchEdit() {
        this.mInBatchEditControllers = true;
        android.widget.Editor.InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState != null) {
            int i = inputMethodState.mBatchEditNesting + 1;
            inputMethodState.mBatchEditNesting = i;
            if (i == 1) {
                inputMethodState.mCursorChanged = false;
                inputMethodState.mChangedDelta = 0;
                if (inputMethodState.mContentChanged) {
                    inputMethodState.mChangedStart = 0;
                    inputMethodState.mChangedEnd = this.mTextView.getText().length();
                } else {
                    inputMethodState.mChangedStart = -1;
                    inputMethodState.mChangedEnd = -1;
                    inputMethodState.mContentChanged = false;
                }
                this.mUndoInputFilter.beginBatchEdit();
                this.mTextView.onBeginBatchEdit();
            }
        }
    }

    public void endBatchEdit() {
        this.mInBatchEditControllers = false;
        android.widget.Editor.InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState != null) {
            int i = inputMethodState.mBatchEditNesting - 1;
            inputMethodState.mBatchEditNesting = i;
            if (i == 0) {
                finishBatchEdit(inputMethodState);
            }
        }
    }

    void ensureEndedBatchEdit() {
        android.widget.Editor.InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState != null && inputMethodState.mBatchEditNesting != 0) {
            inputMethodState.mBatchEditNesting = 0;
            finishBatchEdit(inputMethodState);
        }
    }

    void finishBatchEdit(android.widget.Editor.InputMethodState inputMethodState) {
        this.mTextView.onEndBatchEdit();
        this.mUndoInputFilter.endBatchEdit();
        if (inputMethodState.mContentChanged || inputMethodState.mSelectionModeChanged) {
            this.mTextView.updateAfterEdit();
            reportExtractedText();
        } else if (inputMethodState.mCursorChanged) {
            this.mTextView.invalidateCursor();
        }
        sendUpdateSelection();
        if (this.mTextActionMode != null) {
            android.widget.Editor.CursorController selectionController = this.mTextView.hasSelection() ? getSelectionController() : getInsertionController();
            if (selectionController != null && !selectionController.isActive() && !selectionController.isCursorBeingModified() && this.mTextView.showUIForTouchScreen()) {
                selectionController.show();
            }
        }
    }

    void scheduleRestartInputForSetText() {
        this.mHasPendingRestartInputForSetText = true;
    }

    void maybeFireScheduledRestartInputForSetText() {
        if (this.mHasPendingRestartInputForSetText) {
            android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
            if (inputMethodManager != null) {
                inputMethodManager.invalidateInput(this.mTextView);
            }
            this.mHasPendingRestartInputForSetText = false;
        }
    }

    boolean extractText(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, android.view.inputmethod.ExtractedText extractedText) {
        return extractTextInternal(extractedTextRequest, -1, -1, -1, extractedText);
    }

    private boolean extractTextInternal(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i, int i2, int i3, android.view.inputmethod.ExtractedText extractedText) {
        java.lang.CharSequence text;
        if (extractedTextRequest == null || extractedText == null || (text = this.mTextView.getText()) == null) {
            return false;
        }
        if (i != -2) {
            int length = text.length();
            if (i < 0) {
                extractedText.partialEndOffset = -1;
                extractedText.partialStartOffset = -1;
                i = 0;
            } else {
                int i4 = i2 + i3;
                if (text instanceof android.text.Spanned) {
                    android.text.Spanned spanned = (android.text.Spanned) text;
                    java.lang.Object[] spans = spanned.getSpans(i, i4, android.text.ParcelableSpan.class);
                    int length2 = spans.length;
                    while (length2 > 0) {
                        length2--;
                        int spanStart = spanned.getSpanStart(spans[length2]);
                        if (spanStart < i) {
                            i = spanStart;
                        }
                        int spanEnd = spanned.getSpanEnd(spans[length2]);
                        if (spanEnd > i4) {
                            i4 = spanEnd;
                        }
                    }
                }
                extractedText.partialStartOffset = i;
                extractedText.partialEndOffset = i4 - i3;
                if (i > length) {
                    i = length;
                } else if (i < 0) {
                    i = 0;
                }
                if (i4 <= length) {
                    length = i4 < 0 ? 0 : i4;
                }
            }
            if ((extractedTextRequest.flags & 1) != 0) {
                extractedText.text = text.subSequence(i, length);
            } else {
                extractedText.text = android.text.TextUtils.substring(text, i, length);
            }
        } else {
            extractedText.partialStartOffset = 0;
            extractedText.partialEndOffset = 0;
            extractedText.text = "";
        }
        extractedText.flags = 0;
        if (android.text.method.MetaKeyKeyListener.getMetaState(text, 2048) != 0) {
            extractedText.flags |= 2;
        }
        if (this.mTextView.isSingleLine()) {
            extractedText.flags |= 1;
        }
        extractedText.startOffset = 0;
        extractedText.selectionStart = this.mTextView.getSelectionStart();
        extractedText.selectionEnd = this.mTextView.getSelectionEnd();
        extractedText.hint = this.mTextView.getHint();
        return true;
    }

    boolean reportExtractedText() {
        android.view.inputmethod.InputMethodManager inputMethodManager;
        android.widget.Editor.InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState == null) {
            return false;
        }
        boolean z = inputMethodState.mContentChanged;
        if (!z && !inputMethodState.mSelectionModeChanged) {
            return false;
        }
        inputMethodState.mContentChanged = false;
        inputMethodState.mSelectionModeChanged = false;
        android.view.inputmethod.ExtractedTextRequest extractedTextRequest = inputMethodState.mExtractedTextRequest;
        if (extractedTextRequest == null || (inputMethodManager = getInputMethodManager()) == null) {
            return false;
        }
        if (inputMethodState.mChangedStart < 0 && !z) {
            inputMethodState.mChangedStart = -2;
        }
        if (!extractTextInternal(extractedTextRequest, inputMethodState.mChangedStart, inputMethodState.mChangedEnd, inputMethodState.mChangedDelta, inputMethodState.mExtractedText)) {
            return false;
        }
        inputMethodManager.updateExtractedText(this.mTextView, extractedTextRequest.token, inputMethodState.mExtractedText);
        inputMethodState.mChangedStart = -1;
        inputMethodState.mChangedEnd = -1;
        inputMethodState.mChangedDelta = 0;
        inputMethodState.mContentChanged = false;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdateSelection() {
        android.view.inputmethod.InputMethodManager inputMethodManager;
        int i;
        int i2;
        if (this.mInputMethodState != null && this.mInputMethodState.mBatchEditNesting <= 0 && !this.mHasPendingRestartInputForSetText && (inputMethodManager = getInputMethodManager()) != null) {
            int selectionStart = this.mTextView.getSelectionStart();
            int selectionEnd = this.mTextView.getSelectionEnd();
            if (!(this.mTextView.getText() instanceof android.text.Spannable)) {
                i = -1;
                i2 = -1;
            } else {
                android.text.Spannable spannable = (android.text.Spannable) this.mTextView.getText();
                int composingSpanStart = com.android.internal.inputmethod.EditableInputConnection.getComposingSpanStart(spannable);
                i2 = com.android.internal.inputmethod.EditableInputConnection.getComposingSpanEnd(spannable);
                i = composingSpanStart;
            }
            inputMethodManager.updateSelection(this.mTextView, selectionStart, selectionEnd, i, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void onDraw(android.graphics.Canvas canvas, android.text.Layout layout, java.util.List<android.graphics.Path> list, java.util.List<android.graphics.Paint> list2, android.graphics.Path path, android.graphics.Paint paint, int i) {
        android.graphics.Path path2;
        android.graphics.Path path3;
        android.view.inputmethod.InputMethodManager inputMethodManager;
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        android.widget.Editor.InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState != null && inputMethodState.mBatchEditNesting == 0 && ((inputMethodState.mContentChanged || inputMethodState.mSelectionModeChanged) && (inputMethodManager = getInputMethodManager()) != null && inputMethodManager.hasActiveInputConnection(this.mTextView))) {
            reportExtractedText();
        }
        if (this.mCorrectionHighlighter != null) {
            this.mCorrectionHighlighter.draw(canvas, i);
        }
        if (path != null && selectionStart == selectionEnd && this.mDrawableForCursor != null && !this.mTextView.hasGesturePreviewHighlight()) {
            drawCursor(canvas, i);
            path2 = null;
        } else {
            path2 = path;
        }
        if (this.mSelectionActionModeHelper != null) {
            this.mSelectionActionModeHelper.onDraw(canvas);
            if (this.mSelectionActionModeHelper.isDrawingHighlight()) {
                path3 = null;
                if (this.mInsertModeController != null) {
                    this.mInsertModeController.onDraw(canvas);
                }
                if (!this.mTextView.canHaveDisplayList() && canvas.isHardwareAccelerated()) {
                    drawHardwareAccelerated(canvas, layout, list, list2, path3, paint, i);
                    return;
                } else {
                    layout.draw(canvas, list, list2, path3, paint, i);
                }
            }
        }
        path3 = path2;
        if (this.mInsertModeController != null) {
        }
        if (!this.mTextView.canHaveDisplayList()) {
        }
        layout.draw(canvas, list, list2, path3, paint, i);
    }

    private void drawHardwareAccelerated(android.graphics.Canvas canvas, android.text.Layout layout, java.util.List<android.graphics.Path> list, java.util.List<android.graphics.Paint> list2, android.graphics.Path path, android.graphics.Paint paint, int i) {
        int i2;
        android.util.ArraySet<java.lang.Integer> arraySet;
        int i3;
        int[] iArr;
        int i4;
        int i5;
        android.text.DynamicLayout dynamicLayout;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z;
        long lineRangeForDraw = layout.getLineRangeForDraw(canvas);
        int unpackRangeStartFromLong = android.text.TextUtils.unpackRangeStartFromLong(lineRangeForDraw);
        int unpackRangeEndFromLong = android.text.TextUtils.unpackRangeEndFromLong(lineRangeForDraw);
        if (unpackRangeEndFromLong < 0) {
            return;
        }
        int i10 = 0;
        boolean z2 = true;
        boolean z3 = com.android.graphics.hwui.flags.Flags.highContrastTextSmallTextRect() && canvas.isHighContrastTextEnabled();
        if (!z3) {
            layout.drawWithoutText(canvas, list, list2, path, paint, i, unpackRangeStartFromLong, unpackRangeEndFromLong);
        } else {
            layout.drawBackground(canvas, unpackRangeStartFromLong, unpackRangeEndFromLong);
        }
        if (layout instanceof android.text.DynamicLayout) {
            if (this.mTextRenderNodes == null) {
                this.mTextRenderNodes = (android.widget.Editor.TextRenderNode[]) com.android.internal.util.ArrayUtils.emptyArray(android.widget.Editor.TextRenderNode.class);
            }
            android.text.DynamicLayout dynamicLayout2 = (android.text.DynamicLayout) layout;
            int[] blockEndLines = dynamicLayout2.getBlockEndLines();
            int[] blockIndices = dynamicLayout2.getBlockIndices();
            int numberOfBlocks = dynamicLayout2.getNumberOfBlocks();
            int indexFirstChangedBlock = dynamicLayout2.getIndexFirstChangedBlock();
            android.util.ArraySet<java.lang.Integer> blocksAlwaysNeedToBeRedrawn = dynamicLayout2.getBlocksAlwaysNeedToBeRedrawn();
            int i11 = -1;
            if (blocksAlwaysNeedToBeRedrawn != null) {
                for (int i12 = 0; i12 < blocksAlwaysNeedToBeRedrawn.size(); i12++) {
                    int blockIndex = dynamicLayout2.getBlockIndex(blocksAlwaysNeedToBeRedrawn.valueAt(i12).intValue());
                    if (blockIndex != -1 && this.mTextRenderNodes[blockIndex] != null) {
                        this.mTextRenderNodes[blockIndex].needsToBeShifted = true;
                    }
                }
            }
            int binarySearch = java.util.Arrays.binarySearch(blockEndLines, 0, numberOfBlocks, unpackRangeStartFromLong);
            if (binarySearch < 0) {
                binarySearch = -(binarySearch + 1);
            }
            int min = java.lang.Math.min(indexFirstChangedBlock, binarySearch);
            int i13 = 0;
            while (true) {
                if (min >= numberOfBlocks) {
                    arraySet = blocksAlwaysNeedToBeRedrawn;
                    i3 = numberOfBlocks;
                    iArr = blockEndLines;
                    i4 = i10;
                    i5 = unpackRangeStartFromLong;
                    dynamicLayout = dynamicLayout2;
                    i6 = i3;
                    break;
                }
                int i14 = blockIndices[min];
                if (min >= indexFirstChangedBlock && i14 != i11 && this.mTextRenderNodes[i14] != null) {
                    this.mTextRenderNodes[i14].needsToBeShifted = z2;
                }
                if (blockEndLines[min] < unpackRangeStartFromLong) {
                    i8 = min;
                    arraySet = blocksAlwaysNeedToBeRedrawn;
                    i9 = indexFirstChangedBlock;
                    i3 = numberOfBlocks;
                    iArr = blockEndLines;
                    z = z2;
                    i4 = i10;
                    i5 = unpackRangeStartFromLong;
                    dynamicLayout = dynamicLayout2;
                } else {
                    i8 = min;
                    arraySet = blocksAlwaysNeedToBeRedrawn;
                    i9 = indexFirstChangedBlock;
                    i3 = numberOfBlocks;
                    iArr = blockEndLines;
                    i5 = unpackRangeStartFromLong;
                    dynamicLayout = dynamicLayout2;
                    z = z2;
                    i4 = i10;
                    i13 = drawHardwareAcceleratedInner(canvas, layout, path, paint, i, blockEndLines, blockIndices, i8, i3, i13);
                    if (iArr[i8] >= unpackRangeEndFromLong) {
                        i6 = java.lang.Math.max(i9, i8 + 1);
                        break;
                    }
                }
                min = i8 + 1;
                blocksAlwaysNeedToBeRedrawn = arraySet;
                indexFirstChangedBlock = i9;
                dynamicLayout2 = dynamicLayout;
                numberOfBlocks = i3;
                blockEndLines = iArr;
                unpackRangeStartFromLong = i5;
                z2 = z;
                i10 = i4;
                i11 = -1;
            }
            if (arraySet != null) {
                int i15 = i4;
                while (i15 < arraySet.size()) {
                    int intValue = arraySet.valueAt(i15).intValue();
                    int blockIndex2 = dynamicLayout.getBlockIndex(intValue);
                    if (blockIndex2 == -1 || this.mTextRenderNodes[blockIndex2] == null || this.mTextRenderNodes[blockIndex2].needsToBeShifted) {
                        i7 = i15;
                        i13 = drawHardwareAcceleratedInner(canvas, layout, path, paint, i, iArr, blockIndices, intValue, i3, i13);
                    } else {
                        i7 = i15;
                    }
                    i15 = i7 + 1;
                }
            }
            dynamicLayout.setIndexFirstChangedBlock(i6);
            i2 = i5;
        } else {
            i2 = unpackRangeStartFromLong;
            layout.drawText(canvas, i2, unpackRangeEndFromLong);
        }
        if (z3) {
            layout.drawHighlights(canvas, list, list2, path, paint, i, i2, unpackRangeEndFromLong);
        }
    }

    private int drawHardwareAcceleratedInner(android.graphics.Canvas canvas, android.text.Layout layout, android.graphics.Path path, android.graphics.Paint paint, int i, int[] iArr, int[] iArr2, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = iArr[i2];
        int i8 = iArr2[i2];
        if (i8 == -1) {
            i8 = getAvailableDisplayListIndex(iArr2, i3, i4);
            iArr2[i2] = i8;
            if (this.mTextRenderNodes[i8] != null) {
                this.mTextRenderNodes[i8].isDirty = true;
            }
            i5 = i8 + 1;
        } else {
            i5 = i4;
        }
        if (this.mTextRenderNodes[i8] == null) {
            this.mTextRenderNodes[i8] = new android.widget.Editor.TextRenderNode("Text " + i8);
        }
        boolean needsRecord = this.mTextRenderNodes[i8].needsRecord();
        android.graphics.RenderNode renderNode = this.mTextRenderNodes[i8].renderNode;
        if (this.mTextRenderNodes[i8].needsToBeShifted || needsRecord) {
            int i9 = i2 == 0 ? 0 : iArr[i2 - 1] + 1;
            int lineTop = layout.getLineTop(i9);
            int lineBottom = layout.getLineBottom(i7);
            int width = this.mTextView.getWidth();
            if (!this.mTextView.getHorizontallyScrolling()) {
                i6 = 0;
            } else {
                float f = Float.MAX_VALUE;
                float f2 = Float.MIN_VALUE;
                for (int i10 = i9; i10 <= i7; i10++) {
                    f = java.lang.Math.min(f, layout.getLineLeft(i10));
                    f2 = java.lang.Math.max(f2, layout.getLineRight(i10));
                }
                int i11 = (int) (f2 + 0.5f);
                i6 = (int) f;
                width = i11;
            }
            if (needsRecord) {
                android.graphics.RecordingCanvas beginRecording = renderNode.beginRecording(width - i6, lineBottom - lineTop);
                try {
                    beginRecording.translate(-i6, -lineTop);
                    layout.drawText(beginRecording, i9, i7);
                    this.mTextRenderNodes[i8].isDirty = false;
                } finally {
                    renderNode.endRecording();
                    renderNode.setClipToBounds(false);
                }
            }
            renderNode.setLeftTopRightBottom(i6, lineTop, width, lineBottom);
            this.mTextRenderNodes[i8].needsToBeShifted = false;
        }
        ((android.graphics.RecordingCanvas) canvas).drawRenderNode(renderNode);
        return i5;
    }

    private int getAvailableDisplayListIndex(int[] iArr, int i, int i2) {
        int length = this.mTextRenderNodes.length;
        while (i2 < length) {
            boolean z = false;
            int i3 = 0;
            while (true) {
                if (i3 >= i) {
                    break;
                }
                if (iArr[i3] != i2) {
                    i3++;
                } else {
                    z = true;
                    break;
                }
            }
            if (z) {
                i2++;
            } else {
                return i2;
            }
        }
        this.mTextRenderNodes = (android.widget.Editor.TextRenderNode[]) com.android.internal.util.GrowingArrayUtils.append(this.mTextRenderNodes, length, (java.lang.Object) null);
        return length;
    }

    private void drawCursor(android.graphics.Canvas canvas, int i) {
        boolean z = i != 0;
        if (z) {
            canvas.translate(0.0f, i);
        }
        if (this.mDrawableForCursor != null) {
            this.mDrawableForCursor.draw(canvas);
        }
        if (z) {
            canvas.translate(0.0f, -i);
        }
    }

    void invalidateHandlesAndActionMode() {
        if (this.mSelectionModifierCursorController != null) {
            this.mSelectionModifierCursorController.invalidateHandles();
        }
        if (this.mInsertionPointCursorController != null) {
            this.mInsertionPointCursorController.invalidateHandle();
        }
        if (this.mTextActionMode != null) {
            invalidateActionMode();
        }
    }

    void invalidateTextDisplayList(android.text.Layout layout, int i, int i2) {
        if (this.mTextRenderNodes != null && (layout instanceof android.text.DynamicLayout)) {
            if (com.android.text.flags.Flags.insertModeCrashWhenDelete() && this.mTextView.isOffsetMappingAvailable()) {
                invalidateTextDisplayList();
                return;
            }
            int i3 = 0;
            int originalToTransformed = this.mTextView.originalToTransformed(i, 0);
            int originalToTransformed2 = this.mTextView.originalToTransformed(i2, 0);
            int lineForOffset = layout.getLineForOffset(originalToTransformed);
            int lineForOffset2 = layout.getLineForOffset(originalToTransformed2);
            android.text.DynamicLayout dynamicLayout = (android.text.DynamicLayout) layout;
            int[] blockEndLines = dynamicLayout.getBlockEndLines();
            int[] blockIndices = dynamicLayout.getBlockIndices();
            int numberOfBlocks = dynamicLayout.getNumberOfBlocks();
            while (i3 < numberOfBlocks && blockEndLines[i3] < lineForOffset) {
                i3++;
            }
            while (i3 < numberOfBlocks) {
                int i4 = blockIndices[i3];
                if (i4 != -1) {
                    this.mTextRenderNodes[i4].isDirty = true;
                }
                if (blockEndLines[i3] < lineForOffset2) {
                    i3++;
                } else {
                    return;
                }
            }
        }
    }

    void invalidateTextDisplayList() {
        if (this.mTextRenderNodes != null) {
            for (int i = 0; i < this.mTextRenderNodes.length; i++) {
                if (this.mTextRenderNodes[i] != null) {
                    this.mTextRenderNodes[i].isDirty = true;
                }
            }
        }
    }

    void updateCursorPosition() {
        loadCursorDrawable();
        if (this.mDrawableForCursor == null) {
            return;
        }
        android.text.Layout layout = this.mTextView.getLayout();
        int originalToTransformed = this.mTextView.originalToTransformed(this.mTextView.getSelectionStart(), 1);
        int lineForOffset = layout.getLineForOffset(originalToTransformed);
        updateCursorPosition(layout.getLineTop(lineForOffset), layout.getLineBottom(lineForOffset, false), layout.getPrimaryHorizontal(originalToTransformed, layout.shouldClampCursor(lineForOffset)));
    }

    void refreshTextActionMode() {
        if (extractedTextModeWillBeStarted()) {
            this.mRestartActionModeOnNextRefresh = false;
            return;
        }
        boolean hasSelection = this.mTextView.hasSelection();
        android.widget.Editor.SelectionModifierCursorController selectionController = getSelectionController();
        android.widget.Editor.InsertionPointCursorController insertionController = getInsertionController();
        if ((selectionController != null && selectionController.isCursorBeingModified()) || (insertionController != null && insertionController.isCursorBeingModified())) {
            this.mRestartActionModeOnNextRefresh = false;
            return;
        }
        if (hasSelection) {
            hideInsertionPointCursorController();
            if (this.mTextActionMode == null) {
                if (this.mRestartActionModeOnNextRefresh) {
                    startSelectionActionModeAsync(false);
                }
            } else if (selectionController == null || !selectionController.isActive()) {
                stopTextActionModeWithPreservingSelection();
                startSelectionActionModeAsync(false);
            } else {
                this.mTextActionMode.invalidateContentRect();
            }
        } else if (insertionController == null || !insertionController.isActive()) {
            lambda$startActionModeInternal$0();
        } else if (this.mTextActionMode != null) {
            this.mTextActionMode.invalidateContentRect();
        }
        this.mRestartActionModeOnNextRefresh = false;
    }

    void startInsertionActionMode() {
        if (this.mInsertionActionModeRunnable != null) {
            this.mTextView.removeCallbacks(this.mInsertionActionModeRunnable);
        }
        if (extractedTextModeWillBeStarted()) {
            return;
        }
        lambda$startActionModeInternal$0();
        this.mTextActionMode = this.mTextView.startActionMode(new android.widget.Editor.TextActionModeCallback(1), 1);
        registerOnBackInvokedCallback();
        if (this.mTextActionMode != null && getInsertionController() != null) {
            getInsertionController().show();
        }
    }

    android.widget.TextView getTextView() {
        return this.mTextView;
    }

    android.view.ActionMode getTextActionMode() {
        return this.mTextActionMode;
    }

    void setRestartActionModeOnNextRefresh(boolean z) {
        this.mRestartActionModeOnNextRefresh = z;
    }

    void startSelectionActionModeAsync(boolean z) {
        getSelectionActionModeHelper().startSelectionActionModeAsync(z);
    }

    void startLinkActionModeAsync(int i, int i2) {
        if (!(this.mTextView.getText() instanceof android.text.Spannable)) {
            return;
        }
        lambda$startActionModeInternal$0();
        this.mRequestingLinkActionMode = true;
        getSelectionActionModeHelper().startLinkActionModeAsync(i, i2);
    }

    void invalidateActionModeAsync() {
        getSelectionActionModeHelper().invalidateActionModeAsync();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateActionMode() {
        if (this.mTextActionMode != null) {
            this.mTextActionMode.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.widget.SelectionActionModeHelper getSelectionActionModeHelper() {
        if (this.mSelectionActionModeHelper == null) {
            this.mSelectionActionModeHelper = new android.widget.SelectionActionModeHelper(this);
        }
        return this.mSelectionActionModeHelper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean selectCurrentWordAndStartDrag() {
        if (this.mInsertionActionModeRunnable != null) {
            this.mTextView.removeCallbacks(this.mInsertionActionModeRunnable);
        }
        if (extractedTextModeWillBeStarted() || !checkField()) {
            return false;
        }
        if (!this.mTextView.hasSelection() && !selectCurrentWord()) {
            return false;
        }
        stopTextActionModeWithPreservingSelection();
        getSelectionController().enterDrag(2);
        return true;
    }

    boolean checkField() {
        if (!this.mTextView.canSelectText() || !this.mTextView.requestFocus()) {
            android.util.Log.w("TextView", "TextView does not support text selection. Selection cancelled.");
            return false;
        }
        return true;
    }

    boolean startActionModeInternal(int i) {
        android.view.inputmethod.InputMethodManager inputMethodManager;
        if (extractedTextModeWillBeStarted()) {
            return false;
        }
        if (this.mTextActionMode != null) {
            invalidateActionMode();
            return false;
        }
        if ((i != 2 && (!checkField() || !this.mTextView.hasSelection())) || !this.mTextView.showUIForTouchScreen()) {
            return false;
        }
        this.mTextActionMode = this.mTextView.startActionMode(new android.widget.Editor.TextActionModeCallback(i), 1);
        registerOnBackInvokedCallback();
        boolean z = this.mTextView.isTextEditable() || this.mTextView.isTextSelectable();
        if (i == 2 && !z && (this.mTextActionMode instanceof com.android.internal.view.FloatingActionMode)) {
            ((com.android.internal.view.FloatingActionMode) this.mTextActionMode).setOutsideTouchable(true, new android.widget.PopupWindow.OnDismissListener() { // from class: android.widget.Editor$$ExternalSyntheticLambda1
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    android.widget.Editor.this.lambda$startActionModeInternal$0();
                }
            });
        }
        boolean z2 = this.mTextActionMode != null;
        if (z2 && this.mTextView.isTextEditable() && !this.mTextView.isTextSelectable() && this.mShowSoftInputOnFocus && (inputMethodManager = getInputMethodManager()) != null) {
            inputMethodManager.showSoftInput(this.mTextView, 0, null);
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean extractedTextModeWillBeStarted() {
        android.view.inputmethod.InputMethodManager inputMethodManager;
        return (this.mTextView.isInExtractedMode() || (inputMethodManager = getInputMethodManager()) == null || !inputMethodManager.isFullscreenMode()) ? false : true;
    }

    boolean shouldOfferToShowSuggestions() {
        java.lang.CharSequence text = this.mTextView.getText();
        if (!(text instanceof android.text.Spannable)) {
            return false;
        }
        android.text.Spannable spannable = (android.text.Spannable) text;
        int selectionStart = this.mTextView.getSelectionStart();
        int selectionEnd = this.mTextView.getSelectionEnd();
        android.text.style.SuggestionSpan[] suggestionSpanArr = (android.text.style.SuggestionSpan[]) spannable.getSpans(selectionStart, selectionEnd, android.text.style.SuggestionSpan.class);
        if (suggestionSpanArr.length == 0) {
            return false;
        }
        if (selectionStart == selectionEnd) {
            for (android.text.style.SuggestionSpan suggestionSpan : suggestionSpanArr) {
                if (suggestionSpan.getSuggestions().length > 0) {
                    return true;
                }
            }
            return false;
        }
        int length = this.mTextView.getText().length();
        int length2 = this.mTextView.getText().length();
        boolean z = false;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < suggestionSpanArr.length; i3++) {
            int spanStart = spannable.getSpanStart(suggestionSpanArr[i3]);
            int spanEnd = spannable.getSpanEnd(suggestionSpanArr[i3]);
            length = java.lang.Math.min(length, spanStart);
            i = java.lang.Math.max(i, spanEnd);
            if (selectionStart >= spanStart && selectionStart <= spanEnd) {
                z = z || suggestionSpanArr[i3].getSuggestions().length > 0;
                length2 = java.lang.Math.min(length2, spanStart);
                i2 = java.lang.Math.max(i2, spanEnd);
            }
        }
        return z && length2 < i2 && length >= length2 && i <= i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCursorInsideEasyCorrectionSpan() {
        for (android.text.style.SuggestionSpan suggestionSpan : (android.text.style.SuggestionSpan[]) ((android.text.Spannable) this.mTextView.getText()).getSpans(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), android.text.style.SuggestionSpan.class)) {
            if ((suggestionSpan.getFlags() & 1) != 0) {
                return true;
            }
        }
        return false;
    }

    void onTouchUpEvent(android.view.MotionEvent motionEvent) {
        if (getSelectionActionModeHelper().resetSelection(getTextView().getOffsetForPosition(motionEvent.getX(), motionEvent.getY()))) {
            return;
        }
        boolean z = this.mSelectAllOnFocus && this.mTextView.didTouchFocusSelect();
        hideCursorAndSpanControllers();
        lambda$startActionModeInternal$0();
        java.lang.CharSequence text = this.mTextView.getText();
        if (!z && text.length() > 0) {
            int offsetForPosition = this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
            boolean z2 = !this.mRequestingLinkActionMode;
            if (z2) {
                android.text.Selection.setSelection((android.text.Spannable) text, offsetForPosition);
                if (this.mSpellChecker != null) {
                    this.mSpellChecker.onSelectionChanged();
                }
            }
            if (!extractedTextModeWillBeStarted()) {
                if (isCursorInsideEasyCorrectionSpan()) {
                    if (this.mInsertionActionModeRunnable != null) {
                        this.mTextView.removeCallbacks(this.mInsertionActionModeRunnable);
                    }
                    this.mShowSuggestionRunnable = new java.lang.Runnable() { // from class: android.widget.Editor$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.widget.Editor.this.replace();
                        }
                    };
                    this.mTextView.postDelayed(this.mShowSuggestionRunnable, android.view.ViewConfiguration.getDoubleTapTimeout());
                    return;
                }
                if (hasInsertionController()) {
                    if (z2 && this.mTextView.showUIForTouchScreen()) {
                        getInsertionController().show();
                    } else {
                        getInsertionController().hide();
                    }
                }
            }
        }
    }

    final void onTextOperationUserChanged() {
        if (this.mSpellChecker != null) {
            this.mSpellChecker.resetSession();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: stopTextActionMode, reason: merged with bridge method [inline-methods] */
    public void lambda$startActionModeInternal$0() {
        if (this.mTextActionMode != null) {
            this.mTextActionMode.finish();
        }
        unregisterOnBackInvokedCallback();
    }

    void stopTextActionModeWithPreservingSelection() {
        if (this.mTextActionMode != null) {
            this.mRestartActionModeOnNextRefresh = true;
        }
        this.mPreserveSelection = true;
        lambda$startActionModeInternal$0();
        this.mPreserveSelection = false;
    }

    boolean hasInsertionController() {
        return this.mInsertionControllerEnabled;
    }

    boolean hasSelectionController() {
        return this.mSelectionControllerEnabled;
    }

    public android.widget.Editor.InsertionPointCursorController getInsertionController() {
        if (!this.mInsertionControllerEnabled) {
            return null;
        }
        if (this.mInsertionPointCursorController == null) {
            this.mInsertionPointCursorController = new android.widget.Editor.InsertionPointCursorController();
            this.mTextView.getViewTreeObserver().addOnTouchModeChangeListener(this.mInsertionPointCursorController);
        }
        return this.mInsertionPointCursorController;
    }

    public android.widget.Editor.SelectionModifierCursorController getSelectionController() {
        if (!this.mSelectionControllerEnabled) {
            return null;
        }
        if (this.mSelectionModifierCursorController == null) {
            this.mSelectionModifierCursorController = new android.widget.Editor.SelectionModifierCursorController();
            this.mTextView.getViewTreeObserver().addOnTouchModeChangeListener(this.mSelectionModifierCursorController);
        }
        return this.mSelectionModifierCursorController;
    }

    public android.graphics.drawable.Drawable getCursorDrawable() {
        return this.mDrawableForCursor;
    }

    private void updateCursorPosition(int i, int i2, float f) {
        loadCursorDrawable();
        int clampHorizontalPosition = clampHorizontalPosition(this.mDrawableForCursor, f);
        this.mDrawableForCursor.setBounds(clampHorizontalPosition, i - this.mTempRect.top, this.mDrawableForCursor.getIntrinsicWidth() + clampHorizontalPosition, i2 + this.mTempRect.bottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int clampHorizontalPosition(android.graphics.drawable.Drawable drawable, float f) {
        int i;
        float max = java.lang.Math.max(0.5f, f - 0.5f);
        if (this.mTempRect == null) {
            this.mTempRect = new android.graphics.Rect();
        }
        if (drawable != null) {
            drawable.getPadding(this.mTempRect);
            i = drawable.getIntrinsicWidth();
        } else {
            this.mTempRect.setEmpty();
            i = 0;
        }
        int scrollX = this.mTextView.getScrollX();
        float f2 = max - scrollX;
        int width = (this.mTextView.getWidth() - this.mTextView.getCompoundPaddingLeft()) - this.mTextView.getCompoundPaddingRight();
        float f3 = width;
        if (f2 >= f3 - 1.0f) {
            return (width + scrollX) - (i - this.mTempRect.right);
        }
        if (java.lang.Math.abs(f2) <= 1.0f || (android.text.TextUtils.isEmpty(this.mTextView.getText()) && 1048576 - scrollX <= f3 + 1.0f && max <= 1.0f)) {
            return scrollX - this.mTempRect.left;
        }
        return ((int) max) - this.mTempRect.left;
    }

    public void onCommitCorrection(android.view.inputmethod.CorrectionInfo correctionInfo) {
        if (this.mCorrectionHighlighter == null) {
            this.mCorrectionHighlighter = new android.widget.Editor.CorrectionHighlighter();
        } else {
            this.mCorrectionHighlighter.invalidate(false);
        }
        this.mCorrectionHighlighter.highlight(correctionInfo);
        this.mUndoInputFilter.freezeLastEdit();
    }

    void onScrollChanged() {
        if (this.mPositionListener != null) {
            this.mPositionListener.onScrollChanged();
        }
        if (this.mTextActionMode != null) {
            this.mTextActionMode.invalidateContentRect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldBlink() {
        int selectionStart;
        int selectionEnd;
        return isCursorVisible() && this.mTextView.isFocused() && this.mTextView.getWindowVisibility() == 0 && (selectionStart = this.mTextView.getSelectionStart()) >= 0 && (selectionEnd = this.mTextView.getSelectionEnd()) >= 0 && selectionStart == selectionEnd;
    }

    void makeBlink() {
        if (shouldBlink()) {
            this.mShowCursor = android.os.SystemClock.uptimeMillis();
            if (this.mBlink == null) {
                this.mBlink = new android.widget.Editor.Blink();
            }
            this.mBlink.uncancel();
            this.mTextView.removeCallbacks(this.mBlink);
            this.mTextView.postDelayed(this.mBlink, 500L);
            return;
        }
        if (this.mBlink != null) {
            this.mTextView.removeCallbacks(this.mBlink);
        }
    }

    public boolean isBlinking() {
        if (this.mBlink == null) {
            return false;
        }
        return !this.mBlink.mCancelled;
    }

    private class Blink implements java.lang.Runnable {
        private boolean mCancelled;

        private Blink() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mCancelled) {
                return;
            }
            android.widget.Editor.this.mTextView.removeCallbacks(this);
            if (android.widget.Editor.this.shouldBlink()) {
                if (android.widget.Editor.this.mTextView.getLayout() != null) {
                    android.widget.Editor.this.mTextView.invalidateCursorPath();
                }
                android.widget.Editor.this.mTextView.postDelayed(this, 500L);
            }
        }

        void cancel() {
            if (!this.mCancelled) {
                android.widget.Editor.this.mTextView.removeCallbacks(this);
                this.mCancelled = true;
            }
        }

        void uncancel() {
            this.mCancelled = false;
        }
    }

    private android.view.View.DragShadowBuilder getTextThumbnailBuilder(int i, int i2) {
        android.widget.TextView textView = (android.widget.TextView) android.view.View.inflate(this.mTextView.getContext(), com.android.internal.R.layout.text_drag_thumbnail, null);
        if (textView == null) {
            throw new java.lang.IllegalArgumentException("Unable to inflate text drag thumbnail");
        }
        if (i2 - i > 20) {
            i2 = android.text.TextUtils.unpackRangeEndFromLong(getCharClusterRange(i + 20));
        }
        textView.lambda$setTextAsync$0(this.mTextView.getTransformedText(i, i2));
        textView.setTextColor(this.mTextView.getTextColors());
        textView.setTextAppearance(16);
        textView.setGravity(17);
        textView.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
        textView.measure(makeMeasureSpec, makeMeasureSpec);
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        textView.invalidate();
        return new android.view.View.DragShadowBuilder(textView);
    }

    private static class DragLocalState {
        public int end;
        public android.widget.TextView sourceTextView;
        public int start;

        public DragLocalState(android.widget.TextView textView, int i, int i2) {
            this.sourceTextView = textView;
            this.start = i;
            this.end = i2;
        }
    }

    void onDrop(android.view.DragEvent dragEvent) {
        android.widget.Editor.DragLocalState dragLocalState;
        int offsetForPosition = this.mTextView.getOffsetForPosition(dragEvent.getX(), dragEvent.getY());
        java.lang.Object localState = dragEvent.getLocalState();
        if (!(localState instanceof android.widget.Editor.DragLocalState)) {
            dragLocalState = null;
        } else {
            dragLocalState = (android.widget.Editor.DragLocalState) localState;
        }
        boolean z = dragLocalState != null && dragLocalState.sourceTextView == this.mTextView;
        if (z && offsetForPosition >= dragLocalState.start && offsetForPosition < dragLocalState.end) {
            return;
        }
        android.view.DragAndDropPermissions obtain = android.view.DragAndDropPermissions.obtain(dragEvent);
        if (obtain != null) {
            obtain.takeTransient();
        }
        this.mTextView.beginBatchEdit();
        this.mUndoInputFilter.freezeLastEdit();
        try {
            int length = this.mTextView.getText().length();
            android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), offsetForPosition);
            this.mTextView.performReceiveContent(new android.view.ContentInfo.Builder(dragEvent.getClipData(), 3).setDragAndDropPermissions(obtain).build());
            if (z) {
                deleteSourceAfterLocalDrop(dragLocalState, offsetForPosition, length);
            }
        } finally {
            this.mTextView.endBatchEdit();
            this.mUndoInputFilter.freezeLastEdit();
        }
    }

    private void deleteSourceAfterLocalDrop(android.widget.Editor.DragLocalState dragLocalState, int i, int i2) {
        int i3 = dragLocalState.start;
        int i4 = dragLocalState.end;
        if (i <= i3) {
            int length = this.mTextView.getText().length() - i2;
            i3 += length;
            i4 += length;
        }
        this.mTextView.deleteText_internal(i3, i4);
        int max = java.lang.Math.max(0, i3 - 1);
        int min = java.lang.Math.min(this.mTextView.getText().length(), i3 + 1);
        int i5 = max + 1;
        if (min > i5) {
            java.lang.CharSequence transformedText = this.mTextView.getTransformedText(max, min);
            if (java.lang.Character.isSpaceChar(transformedText.charAt(0)) && java.lang.Character.isSpaceChar(transformedText.charAt(1))) {
                this.mTextView.deleteText_internal(max, i5);
            }
        }
    }

    public void addSpanWatchers(android.text.Spannable spannable) {
        int length = spannable.length();
        if (this.mKeyListener != null) {
            spannable.setSpan(this.mKeyListener, 0, length, 18);
        }
        if (this.mSpanController == null) {
            this.mSpanController = new android.widget.Editor.SpanController();
        }
        spannable.setSpan(this.mSpanController, 0, length, 18);
    }

    void setContextMenuAnchor(float f, float f2) {
        this.mContextMenuAnchorX = f;
        this.mContextMenuAnchorY = f2;
    }

    private void setAssistContextMenuItems(android.view.Menu menu) {
        if (getSelectionActionModeHelper().getTextClassification() == null) {
            return;
        }
        final android.widget.Editor.AssistantCallbackHelper assistantCallbackHelper = new android.widget.Editor.AssistantCallbackHelper(getSelectionActionModeHelper());
        assistantCallbackHelper.updateAssistMenuItems(menu, new android.view.MenuItem.OnMenuItemClickListener() { // from class: android.widget.Editor$$ExternalSyntheticLambda3
            @Override // android.view.MenuItem.OnMenuItemClickListener
            public final boolean onMenuItemClick(android.view.MenuItem menuItem) {
                boolean lambda$setAssistContextMenuItems$1;
                lambda$setAssistContextMenuItems$1 = android.widget.Editor.this.lambda$setAssistContextMenuItems$1(assistantCallbackHelper, menuItem);
                return lambda$setAssistContextMenuItems$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setAssistContextMenuItems$1(android.widget.Editor.AssistantCallbackHelper assistantCallbackHelper, android.view.MenuItem menuItem) {
        getSelectionActionModeHelper().onSelectionAction(menuItem.getItemId(), menuItem.getTitle().toString());
        if (this.mProcessTextIntentActionsHandler.performMenuItemAction(menuItem)) {
            return true;
        }
        if (menuItem.getGroupId() == 16908353 && assistantCallbackHelper.onAssistMenuItemClicked(menuItem)) {
            return true;
        }
        return this.mTextView.onTextContextMenuItem(menuItem.getItemId());
    }

    public void onCreateContextMenu(android.view.ContextMenu contextMenu) {
        int offsetForPosition;
        int i;
        if (this.mIsBeingLongClicked || java.lang.Float.isNaN(this.mContextMenuAnchorX) || java.lang.Float.isNaN(this.mContextMenuAnchorY) || (offsetForPosition = this.mTextView.getOffsetForPosition(this.mContextMenuAnchorX, this.mContextMenuAnchorY)) == -1) {
            return;
        }
        stopTextActionModeWithPreservingSelection();
        if (this.mTextView.canSelectText()) {
            if (!(this.mTextView.hasSelection() && offsetForPosition >= this.mTextView.getSelectionStart() && offsetForPosition <= this.mTextView.getSelectionEnd())) {
                android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), offsetForPosition);
                lambda$startActionModeInternal$0();
            }
        }
        int i2 = 11;
        if (shouldOfferToShowSuggestions()) {
            android.widget.Editor.SuggestionInfo[] suggestionInfoArr = new android.widget.Editor.SuggestionInfo[5];
            int i3 = 0;
            while (true) {
                if (i3 >= 5) {
                    break;
                }
                suggestionInfoArr[i3] = new android.widget.Editor.SuggestionInfo();
                i3++;
            }
            android.view.SubMenu addSubMenu = contextMenu.addSubMenu(0, 0, 11, com.android.internal.R.string.replace);
            int suggestionInfo = this.mSuggestionHelper.getSuggestionInfo(suggestionInfoArr, null);
            for (int i4 = 0; i4 < suggestionInfo; i4++) {
                final android.widget.Editor.SuggestionInfo suggestionInfo2 = suggestionInfoArr[i4];
                addSubMenu.add(0, 0, i4, suggestionInfo2.mText).setOnMenuItemClickListener(new android.view.MenuItem.OnMenuItemClickListener() { // from class: android.widget.Editor.4
                    @Override // android.view.MenuItem.OnMenuItemClickListener
                    public boolean onMenuItemClick(android.view.MenuItem menuItem) {
                        android.widget.Editor.this.replaceWithSuggestion(suggestionInfo2);
                        return true;
                    }
                });
            }
        }
        if (this.mUseNewContextMenu) {
            contextMenu.setOptionalIconsVisible(true);
            contextMenu.setGroupDividerEnabled(true);
            setAssistContextMenuItems(contextMenu);
            contextMenu.setQwertyMode(this.mTextView.getResources().getConfiguration().keyboard == 2);
            i = 9;
            i2 = 7;
        } else {
            i = 7;
        }
        android.content.res.TypedArray obtainStyledAttributes = this.mTextView.getContext().obtainStyledAttributes(new int[]{com.android.internal.R.attr.actionModeUndoDrawable, com.android.internal.R.attr.actionModeRedoDrawable, 16843537, 16843538, 16843539, 16843646, 16843897});
        contextMenu.add(1, 16908338, 2, com.android.internal.R.string.undo).setAlphabeticShortcut(android.text.format.DateFormat.TIME_ZONE).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(0)).setEnabled(this.mTextView.canUndo());
        contextMenu.add(1, 16908339, 3, com.android.internal.R.string.redo).setAlphabeticShortcut(android.text.format.DateFormat.TIME_ZONE, 4097).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(1)).setEnabled(this.mTextView.canRedo());
        contextMenu.add(2, 16908320, 4, 17039363).setAlphabeticShortcut(com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty.TARGET_X).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(2)).setEnabled(this.mTextView.canCut());
        contextMenu.add(2, 16908321, 5, 17039361).setAlphabeticShortcut('c').setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setIcon(obtainStyledAttributes.getDrawable(3)).setEnabled(this.mTextView.canCopy());
        contextMenu.add(2, 16908322, 6, 17039371).setAlphabeticShortcut('v').setEnabled(this.mTextView.canPaste()).setIcon(obtainStyledAttributes.getDrawable(4)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        contextMenu.add(2, 16908337, i2, 17039385).setAlphabeticShortcut('v', 4097).setEnabled(this.mTextView.canPasteAsPlainText()).setIcon(obtainStyledAttributes.getDrawable(4)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        contextMenu.add(2, 16908319, 8, 17039373).setAlphabeticShortcut(android.text.format.DateFormat.AM_PM).setEnabled(this.mTextView.canSelectAllText()).setIcon(obtainStyledAttributes.getDrawable(5)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        contextMenu.add(3, 16908341, i, com.android.internal.R.string.share).setEnabled(this.mTextView.canShare()).setIcon(obtainStyledAttributes.getDrawable(6)).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        contextMenu.add(3, 16908355, 10, 17039386).setEnabled(this.mTextView.canRequestAutofill()).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
        this.mPreserveSelection = true;
        obtainStyledAttributes.recycle();
        adjustIconSpacing(contextMenu);
    }

    public void adjustIconSpacing(android.view.ContextMenu contextMenu) {
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < contextMenu.size(); i3++) {
            android.graphics.drawable.Drawable icon = contextMenu.getItem(i3).getIcon();
            if (icon != null) {
                i = java.lang.Math.max(i, icon.getIntrinsicWidth());
                i2 = java.lang.Math.max(i2, icon.getIntrinsicHeight());
            }
        }
        if (i < 0 || i2 < 0) {
            return;
        }
        android.graphics.drawable.GradientDrawable gradientDrawable = new android.graphics.drawable.GradientDrawable();
        gradientDrawable.setSize(i, i2);
        for (int i4 = 0; i4 < contextMenu.size(); i4++) {
            android.view.MenuItem item = contextMenu.getItem(i4);
            if (item.getIcon() == null) {
                item.setIcon(gradientDrawable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.text.style.SuggestionSpan findEquivalentSuggestionSpan(android.widget.Editor.SuggestionSpanInfo suggestionSpanInfo) {
        android.text.Editable editable = (android.text.Editable) this.mTextView.getText();
        if (editable.getSpanStart(suggestionSpanInfo.mSuggestionSpan) >= 0) {
            return suggestionSpanInfo.mSuggestionSpan;
        }
        for (android.text.style.SuggestionSpan suggestionSpan : (android.text.style.SuggestionSpan[]) editable.getSpans(suggestionSpanInfo.mSpanStart, suggestionSpanInfo.mSpanEnd, android.text.style.SuggestionSpan.class)) {
            if (editable.getSpanStart(suggestionSpan) == suggestionSpanInfo.mSpanStart && editable.getSpanEnd(suggestionSpan) == suggestionSpanInfo.mSpanEnd && suggestionSpan.equals(suggestionSpanInfo.mSuggestionSpan)) {
                return suggestionSpan;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceWithSuggestion(android.widget.Editor.SuggestionInfo suggestionInfo) {
        android.text.style.SuggestionSpan findEquivalentSuggestionSpan = findEquivalentSuggestionSpan(suggestionInfo.mSuggestionSpanInfo);
        if (findEquivalentSuggestionSpan == null) {
            return;
        }
        android.text.Editable editable = (android.text.Editable) this.mTextView.getText();
        int spanStart = editable.getSpanStart(findEquivalentSuggestionSpan);
        int spanEnd = editable.getSpanEnd(findEquivalentSuggestionSpan);
        if (spanStart < 0 || spanEnd <= spanStart) {
            return;
        }
        java.lang.String substring = android.text.TextUtils.substring(editable, spanStart, spanEnd);
        android.text.style.SuggestionSpan[] suggestionSpanArr = (android.text.style.SuggestionSpan[]) editable.getSpans(spanStart, spanEnd, android.text.style.SuggestionSpan.class);
        int length = suggestionSpanArr.length;
        int[] iArr = new int[length];
        int[] iArr2 = new int[length];
        int[] iArr3 = new int[length];
        for (int i = 0; i < length; i++) {
            android.text.style.SuggestionSpan suggestionSpan = suggestionSpanArr[i];
            iArr[i] = editable.getSpanStart(suggestionSpan);
            iArr2[i] = editable.getSpanEnd(suggestionSpan);
            iArr3[i] = editable.getSpanFlags(suggestionSpan);
            int flags = suggestionSpan.getFlags();
            if ((flags & 10) != 0) {
                suggestionSpan.setFlags(flags & (-3) & (-9) & (-2));
            }
        }
        java.lang.String charSequence = suggestionInfo.mText.subSequence(suggestionInfo.mSuggestionStart, suggestionInfo.mSuggestionEnd).toString();
        this.mTextView.replaceText_internal(spanStart, spanEnd, charSequence);
        findEquivalentSuggestionSpan.getSuggestions()[suggestionInfo.mSuggestionIndex] = substring;
        int length2 = charSequence.length() - (spanEnd - spanStart);
        for (int i2 = 0; i2 < length; i2++) {
            if (iArr[i2] <= spanStart && iArr2[i2] >= spanEnd) {
                this.mTextView.setSpan_internal(suggestionSpanArr[i2], iArr[i2], iArr2[i2] + length2, iArr3[i2]);
            }
        }
        int i3 = spanEnd + length2;
        this.mTextView.setCursorPosition_internal(i3, i3);
    }

    private class SpanController implements android.text.SpanWatcher {
        private static final int DISPLAY_TIMEOUT_MS = 3000;
        private java.lang.Runnable mHidePopup;
        private android.widget.Editor.EasyEditPopupWindow mPopupWindow;

        private SpanController() {
        }

        private boolean isNonIntermediateSelectionSpan(android.text.Spannable spannable, java.lang.Object obj) {
            return (android.text.Selection.SELECTION_START == obj || android.text.Selection.SELECTION_END == obj) && (spannable.getSpanFlags(obj) & 512) == 0;
        }

        @Override // android.text.SpanWatcher
        public void onSpanAdded(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
            if (isNonIntermediateSelectionSpan(spannable, obj)) {
                android.widget.Editor.this.sendUpdateSelection();
                return;
            }
            if (obj instanceof android.text.style.EasyEditSpan) {
                if (this.mPopupWindow == null) {
                    this.mPopupWindow = new android.widget.Editor.EasyEditPopupWindow();
                    this.mHidePopup = new java.lang.Runnable() { // from class: android.widget.Editor.SpanController.1
                        @Override // java.lang.Runnable
                        public void run() {
                            android.widget.Editor.SpanController.this.hide();
                        }
                    };
                }
                if (this.mPopupWindow.mEasyEditSpan != null) {
                    this.mPopupWindow.mEasyEditSpan.setDeleteEnabled(false);
                }
                this.mPopupWindow.setEasyEditSpan((android.text.style.EasyEditSpan) obj);
                this.mPopupWindow.setOnDeleteListener(new android.widget.Editor.EasyEditDeleteListener() { // from class: android.widget.Editor.SpanController.2
                    @Override // android.widget.Editor.EasyEditDeleteListener
                    public void onDeleteClick(android.text.style.EasyEditSpan easyEditSpan) {
                        android.text.Editable editable = (android.text.Editable) android.widget.Editor.this.mTextView.getText();
                        int spanStart = editable.getSpanStart(easyEditSpan);
                        int spanEnd = editable.getSpanEnd(easyEditSpan);
                        if (spanStart >= 0 && spanEnd >= 0) {
                            android.widget.Editor.SpanController.this.sendEasySpanNotification(1, easyEditSpan);
                            android.widget.Editor.this.mTextView.deleteText_internal(spanStart, spanEnd);
                        }
                        editable.removeSpan(easyEditSpan);
                    }
                });
                if (android.widget.Editor.this.mTextView.getWindowVisibility() != 0 || android.widget.Editor.this.mTextView.getLayout() == null || android.widget.Editor.this.extractedTextModeWillBeStarted()) {
                    return;
                }
                this.mPopupWindow.show();
                android.widget.Editor.this.mTextView.removeCallbacks(this.mHidePopup);
                android.widget.Editor.this.mTextView.postDelayed(this.mHidePopup, 3000L);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanRemoved(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
            if (isNonIntermediateSelectionSpan(spannable, obj)) {
                android.widget.Editor.this.sendUpdateSelection();
            } else if (this.mPopupWindow != null && obj == this.mPopupWindow.mEasyEditSpan) {
                hide();
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanChanged(android.text.Spannable spannable, java.lang.Object obj, int i, int i2, int i3, int i4) {
            if (isNonIntermediateSelectionSpan(spannable, obj)) {
                android.widget.Editor.this.sendUpdateSelection();
            } else if (this.mPopupWindow != null && (obj instanceof android.text.style.EasyEditSpan)) {
                android.text.style.EasyEditSpan easyEditSpan = (android.text.style.EasyEditSpan) obj;
                sendEasySpanNotification(2, easyEditSpan);
                spannable.removeSpan(easyEditSpan);
            }
        }

        public void hide() {
            if (this.mPopupWindow != null) {
                this.mPopupWindow.hide();
                android.widget.Editor.this.mTextView.removeCallbacks(this.mHidePopup);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendEasySpanNotification(int i, android.text.style.EasyEditSpan easyEditSpan) {
            try {
                android.app.PendingIntent pendingIntent = easyEditSpan.getPendingIntent();
                if (pendingIntent != null) {
                    android.content.Intent intent = new android.content.Intent();
                    intent.putExtra(android.text.style.EasyEditSpan.EXTRA_TEXT_CHANGED_TYPE, i);
                    pendingIntent.send(android.widget.Editor.this.mTextView.getContext(), 0, intent);
                }
            } catch (android.app.PendingIntent.CanceledException e) {
                android.util.Log.w("Editor", "PendingIntent for notification cannot be sent", e);
            }
        }
    }

    private class EasyEditPopupWindow extends android.widget.Editor.PinnedPopupWindow implements android.view.View.OnClickListener {
        private static final int POPUP_TEXT_LAYOUT = 17367352;
        private android.widget.TextView mDeleteTextView;
        private android.text.style.EasyEditSpan mEasyEditSpan;
        private android.widget.Editor.EasyEditDeleteListener mOnDeleteListener;

        private EasyEditPopupWindow() {
            super();
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void createPopupWindow() {
            this.mPopupWindow = new android.widget.PopupWindow(android.widget.Editor.this.mTextView.getContext(), (android.util.AttributeSet) null, 16843464);
            this.mPopupWindow.setInputMethodMode(2);
            this.mPopupWindow.setClippingEnabled(true);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void initContentView() {
            android.widget.LinearLayout linearLayout = new android.widget.LinearLayout(android.widget.Editor.this.mTextView.getContext());
            linearLayout.setOrientation(0);
            this.mContentView = linearLayout;
            this.mContentView.setBackgroundResource(com.android.internal.R.drawable.text_edit_side_paste_window);
            android.view.LayoutInflater layoutInflater = (android.view.LayoutInflater) android.widget.Editor.this.mTextView.getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            android.view.ViewGroup.LayoutParams layoutParams = new android.view.ViewGroup.LayoutParams(-2, -2);
            this.mDeleteTextView = (android.widget.TextView) layoutInflater.inflate(17367352, (android.view.ViewGroup) null);
            this.mDeleteTextView.setLayoutParams(layoutParams);
            this.mDeleteTextView.setText(com.android.internal.R.string.delete);
            this.mDeleteTextView.setOnClickListener(this);
            this.mContentView.addView(this.mDeleteTextView);
        }

        public void setEasyEditSpan(android.text.style.EasyEditSpan easyEditSpan) {
            this.mEasyEditSpan = easyEditSpan;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOnDeleteListener(android.widget.Editor.EasyEditDeleteListener easyEditDeleteListener) {
            this.mOnDeleteListener = easyEditDeleteListener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (view == this.mDeleteTextView && this.mEasyEditSpan != null && this.mEasyEditSpan.isDeleteEnabled() && this.mOnDeleteListener != null) {
                this.mOnDeleteListener.onDeleteClick(this.mEasyEditSpan);
            }
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        public void hide() {
            if (this.mEasyEditSpan != null) {
                this.mEasyEditSpan.setDeleteEnabled(false);
            }
            this.mOnDeleteListener = null;
            super.hide();
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getTextOffset() {
            return ((android.text.Editable) android.widget.Editor.this.mTextView.getText()).getSpanEnd(this.mEasyEditSpan);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getVerticalLocalPosition(int i) {
            return android.widget.Editor.this.mTextView.getLayout().getLineBottom(i, false);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int clipVertically(int i) {
            return i;
        }
    }

    private class PositionListener implements android.view.ViewTreeObserver.OnPreDrawListener {
        private static final int MAXIMUM_NUMBER_OF_LISTENERS = 7;
        private boolean[] mCanMove;
        private int mNumberOfListeners;
        private boolean mPositionHasChanged;
        private android.widget.Editor.TextViewPositionListener[] mPositionListeners;
        private int mPositionX;
        private int mPositionXOnScreen;
        private int mPositionY;
        private int mPositionYOnScreen;
        private boolean mScrollHasChanged;
        final int[] mTempCoords;

        private PositionListener() {
            this.mPositionListeners = new android.widget.Editor.TextViewPositionListener[7];
            this.mCanMove = new boolean[7];
            this.mPositionHasChanged = true;
            this.mTempCoords = new int[2];
        }

        public void addSubscriber(android.widget.Editor.TextViewPositionListener textViewPositionListener, boolean z) {
            if (this.mNumberOfListeners == 0) {
                updatePosition();
                android.widget.Editor.this.mTextView.getViewTreeObserver().addOnPreDrawListener(this);
            }
            int i = -1;
            for (int i2 = 0; i2 < 7; i2++) {
                android.widget.Editor.TextViewPositionListener textViewPositionListener2 = this.mPositionListeners[i2];
                if (textViewPositionListener2 == textViewPositionListener) {
                    return;
                }
                if (i < 0 && textViewPositionListener2 == null) {
                    i = i2;
                }
            }
            this.mPositionListeners[i] = textViewPositionListener;
            this.mCanMove[i] = z;
            this.mNumberOfListeners++;
        }

        public void removeSubscriber(android.widget.Editor.TextViewPositionListener textViewPositionListener) {
            int i = 0;
            while (true) {
                if (i >= 7) {
                    break;
                }
                if (this.mPositionListeners[i] != textViewPositionListener) {
                    i++;
                } else {
                    this.mPositionListeners[i] = null;
                    this.mNumberOfListeners--;
                    break;
                }
            }
            if (this.mNumberOfListeners == 0) {
                android.widget.Editor.this.mTextView.getViewTreeObserver().removeOnPreDrawListener(this);
            }
        }

        public int getPositionX() {
            return this.mPositionX;
        }

        public int getPositionY() {
            return this.mPositionY;
        }

        public int getPositionXOnScreen() {
            return this.mPositionXOnScreen;
        }

        public int getPositionYOnScreen() {
            return this.mPositionYOnScreen;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            android.widget.Editor.TextViewPositionListener textViewPositionListener;
            updatePosition();
            for (int i = 0; i < 7; i++) {
                if ((this.mPositionHasChanged || this.mScrollHasChanged || this.mCanMove[i]) && (textViewPositionListener = this.mPositionListeners[i]) != null) {
                    textViewPositionListener.updatePosition(this.mPositionX, this.mPositionY, this.mPositionHasChanged, this.mScrollHasChanged);
                }
            }
            this.mScrollHasChanged = false;
            return true;
        }

        private void updatePosition() {
            android.widget.Editor.this.mTextView.getLocationInWindow(this.mTempCoords);
            this.mPositionHasChanged = (this.mTempCoords[0] == this.mPositionX && this.mTempCoords[1] == this.mPositionY) ? false : true;
            this.mPositionX = this.mTempCoords[0];
            this.mPositionY = this.mTempCoords[1];
            android.widget.Editor.this.mTextView.getLocationOnScreen(this.mTempCoords);
            this.mPositionXOnScreen = this.mTempCoords[0];
            this.mPositionYOnScreen = this.mTempCoords[1];
        }

        public void onScrollChanged() {
            this.mScrollHasChanged = true;
        }
    }

    private abstract class PinnedPopupWindow implements android.widget.Editor.TextViewPositionListener {
        int mClippingLimitLeft;
        int mClippingLimitRight;
        protected android.view.ViewGroup mContentView;
        protected android.widget.PopupWindow mPopupWindow;
        int mPositionX;
        int mPositionY;

        protected abstract int clipVertically(int i);

        protected abstract void createPopupWindow();

        protected abstract int getTextOffset();

        protected abstract int getVerticalLocalPosition(int i);

        protected abstract void initContentView();

        protected void setUp() {
        }

        public PinnedPopupWindow() {
            setUp();
            createPopupWindow();
            this.mPopupWindow.setWindowLayoutType(1005);
            this.mPopupWindow.setWidth(-2);
            this.mPopupWindow.setHeight(-2);
            initContentView();
            this.mContentView.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
            this.mPopupWindow.setContentView(this.mContentView);
        }

        public void show() {
            android.widget.Editor.this.getPositionListener().addSubscriber(this, false);
            computeLocalPosition();
            android.widget.Editor.PositionListener positionListener = android.widget.Editor.this.getPositionListener();
            updatePosition(positionListener.getPositionX(), positionListener.getPositionY());
        }

        protected void measureContent() {
            android.util.DisplayMetrics displayMetrics = android.widget.Editor.this.mTextView.getResources().getDisplayMetrics();
            this.mContentView.measure(android.view.View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE), android.view.View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE));
        }

        private void computeLocalPosition() {
            measureContent();
            int measuredWidth = this.mContentView.getMeasuredWidth();
            int originalToTransformed = android.widget.Editor.this.mTextView.originalToTransformed(getTextOffset(), 1);
            android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
            this.mPositionX = (int) (layout.getPrimaryHorizontal(originalToTransformed) - (measuredWidth / 2.0f));
            this.mPositionX += android.widget.Editor.this.mTextView.viewportToContentHorizontalOffset();
            this.mPositionY = getVerticalLocalPosition(layout.getLineForOffset(originalToTransformed));
            this.mPositionY += android.widget.Editor.this.mTextView.viewportToContentVerticalOffset();
        }

        private void updatePosition(int i, int i2) {
            int i3 = i + this.mPositionX;
            int clipVertically = clipVertically(i2 + this.mPositionY);
            android.util.DisplayMetrics displayMetrics = android.widget.Editor.this.mTextView.getResources().getDisplayMetrics();
            int max = java.lang.Math.max(-this.mClippingLimitLeft, java.lang.Math.min((displayMetrics.widthPixels - this.mContentView.getMeasuredWidth()) + this.mClippingLimitRight, i3));
            if (isShowing()) {
                this.mPopupWindow.update(max, clipVertically, -1, -1);
            } else {
                this.mPopupWindow.showAtLocation(android.widget.Editor.this.mTextView, 0, max, clipVertically);
            }
        }

        public void hide() {
            if (!isShowing()) {
                return;
            }
            this.mPopupWindow.dismiss();
            android.widget.Editor.this.getPositionListener().removeSubscriber(this);
        }

        @Override // android.widget.Editor.TextViewPositionListener
        public void updatePosition(int i, int i2, boolean z, boolean z2) {
            if (isShowing() && android.widget.Editor.this.isOffsetVisible(getTextOffset())) {
                if (z2) {
                    computeLocalPosition();
                }
                updatePosition(i, i2);
                return;
            }
            hide();
        }

        public boolean isShowing() {
            return this.mPopupWindow.isShowing();
        }
    }

    private static final class SuggestionInfo {
        int mSuggestionEnd;
        int mSuggestionIndex;
        final android.widget.Editor.SuggestionSpanInfo mSuggestionSpanInfo;
        int mSuggestionStart;
        final android.text.SpannableStringBuilder mText;

        private SuggestionInfo() {
            this.mSuggestionSpanInfo = new android.widget.Editor.SuggestionSpanInfo();
            this.mText = new android.text.SpannableStringBuilder();
        }

        void clear() {
            this.mSuggestionSpanInfo.clear();
            this.mText.clear();
        }

        void setSpanInfo(android.text.style.SuggestionSpan suggestionSpan, int i, int i2) {
            this.mSuggestionSpanInfo.mSuggestionSpan = suggestionSpan;
            this.mSuggestionSpanInfo.mSpanStart = i;
            this.mSuggestionSpanInfo.mSpanEnd = i2;
        }
    }

    private static final class SuggestionSpanInfo {
        int mSpanEnd;
        int mSpanStart;
        android.text.style.SuggestionSpan mSuggestionSpan;

        private SuggestionSpanInfo() {
        }

        void clear() {
            this.mSuggestionSpan = null;
        }
    }

    private class SuggestionHelper {
        private final java.util.HashMap<android.text.style.SuggestionSpan, java.lang.Integer> mSpansLengths;
        private final java.util.Comparator<android.text.style.SuggestionSpan> mSuggestionSpanComparator;

        private SuggestionHelper() {
            this.mSuggestionSpanComparator = new android.widget.Editor.SuggestionHelper.SuggestionSpanComparator();
            this.mSpansLengths = new java.util.HashMap<>();
        }

        private class SuggestionSpanComparator implements java.util.Comparator<android.text.style.SuggestionSpan> {
            private SuggestionSpanComparator() {
            }

            @Override // java.util.Comparator
            public int compare(android.text.style.SuggestionSpan suggestionSpan, android.text.style.SuggestionSpan suggestionSpan2) {
                int flags = suggestionSpan.getFlags();
                int flags2 = suggestionSpan2.getFlags();
                if (flags != flags2) {
                    int compareFlag = compareFlag(1, flags, flags2);
                    if (compareFlag != 0) {
                        return compareFlag;
                    }
                    int compareFlag2 = compareFlag(2, flags, flags2);
                    if (compareFlag2 != 0) {
                        return compareFlag2;
                    }
                    int compareFlag3 = compareFlag(8, flags, flags2);
                    if (compareFlag3 != 0) {
                        return compareFlag3;
                    }
                }
                return ((java.lang.Integer) android.widget.Editor.SuggestionHelper.this.mSpansLengths.get(suggestionSpan)).intValue() - ((java.lang.Integer) android.widget.Editor.SuggestionHelper.this.mSpansLengths.get(suggestionSpan2)).intValue();
            }

            private int compareFlag(int i, int i2, int i3) {
                boolean z = (i2 & i) != 0;
                if (z == ((i & i3) != 0)) {
                    return 0;
                }
                return z ? -1 : 1;
            }
        }

        private android.text.style.SuggestionSpan[] getSortedSuggestionSpans() {
            int selectionStart = android.widget.Editor.this.mTextView.getSelectionStart();
            android.text.Spannable spannable = (android.text.Spannable) android.widget.Editor.this.mTextView.getText();
            android.text.style.SuggestionSpan[] suggestionSpanArr = (android.text.style.SuggestionSpan[]) spannable.getSpans(selectionStart, selectionStart, android.text.style.SuggestionSpan.class);
            this.mSpansLengths.clear();
            for (android.text.style.SuggestionSpan suggestionSpan : suggestionSpanArr) {
                this.mSpansLengths.put(suggestionSpan, java.lang.Integer.valueOf(spannable.getSpanEnd(suggestionSpan) - spannable.getSpanStart(suggestionSpan)));
            }
            java.util.Arrays.sort(suggestionSpanArr, this.mSuggestionSpanComparator);
            this.mSpansLengths.clear();
            return suggestionSpanArr;
        }

        public int getSuggestionInfo(android.widget.Editor.SuggestionInfo[] suggestionInfoArr, android.widget.Editor.SuggestionSpanInfo suggestionSpanInfo) {
            android.widget.Editor.SuggestionSpanInfo suggestionSpanInfo2 = suggestionSpanInfo;
            android.text.Spannable spannable = (android.text.Spannable) android.widget.Editor.this.mTextView.getText();
            android.text.style.SuggestionSpan[] sortedSuggestionSpans = getSortedSuggestionSpans();
            int i = 0;
            if (sortedSuggestionSpans.length == 0) {
                return 0;
            }
            int length = sortedSuggestionSpans.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                android.text.style.SuggestionSpan suggestionSpan = sortedSuggestionSpans[i2];
                int spanStart = spannable.getSpanStart(suggestionSpan);
                int spanEnd = spannable.getSpanEnd(suggestionSpan);
                if (suggestionSpanInfo2 != null && (suggestionSpan.getFlags() & 10) != 0) {
                    suggestionSpanInfo2.mSuggestionSpan = suggestionSpan;
                    suggestionSpanInfo2.mSpanStart = spanStart;
                    suggestionSpanInfo2.mSpanEnd = spanEnd;
                }
                java.lang.String[] suggestions = suggestionSpan.getSuggestions();
                int length2 = suggestions.length;
                for (int i4 = i; i4 < length2; i4++) {
                    java.lang.String str = suggestions[i4];
                    int i5 = i;
                    while (true) {
                        if (i5 < i3) {
                            android.widget.Editor.SuggestionInfo suggestionInfo = suggestionInfoArr[i5];
                            if (suggestionInfo.mText.toString().equals(str)) {
                                int i6 = suggestionInfo.mSuggestionSpanInfo.mSpanStart;
                                int i7 = suggestionInfo.mSuggestionSpanInfo.mSpanEnd;
                                if (spanStart == i6 && spanEnd == i7) {
                                    i = 0;
                                    break;
                                }
                            }
                            i5++;
                        } else {
                            android.widget.Editor.SuggestionInfo suggestionInfo2 = suggestionInfoArr[i3];
                            suggestionInfo2.setSpanInfo(suggestionSpan, spanStart, spanEnd);
                            suggestionInfo2.mSuggestionIndex = i4;
                            i = 0;
                            suggestionInfo2.mSuggestionStart = 0;
                            suggestionInfo2.mSuggestionEnd = str.length();
                            suggestionInfo2.mText.replace(0, suggestionInfo2.mText.length(), (java.lang.CharSequence) str);
                            i3++;
                            if (i3 >= suggestionInfoArr.length) {
                                return i3;
                            }
                        }
                    }
                }
                i2++;
                suggestionSpanInfo2 = suggestionSpanInfo;
            }
            return i3;
        }
    }

    private final class SuggestionsPopupWindow extends android.widget.Editor.PinnedPopupWindow implements android.widget.AdapterView.OnItemClickListener {
        private static final int MAX_NUMBER_SUGGESTIONS = 5;
        private static final java.lang.String USER_DICTIONARY_EXTRA_LOCALE = "locale";
        private static final java.lang.String USER_DICTIONARY_EXTRA_WORD = "word";
        private android.widget.TextView mAddToDictionaryButton;
        private int mContainerMarginTop;
        private int mContainerMarginWidth;
        private android.widget.LinearLayout mContainerView;
        private android.content.Context mContext;
        private boolean mCursorWasVisibleBeforeSuggestions;
        private android.widget.TextView mDeleteButton;
        private android.text.style.TextAppearanceSpan mHighlightSpan;
        private boolean mIsShowingUp;
        private final android.widget.Editor.SuggestionSpanInfo mMisspelledSpanInfo;
        private int mNumberOfSuggestions;
        private android.widget.Editor.SuggestionInfo[] mSuggestionInfos;
        private android.widget.ListView mSuggestionListView;
        private android.widget.Editor.SuggestionsPopupWindow.SuggestionAdapter mSuggestionsAdapter;

        private class CustomPopupWindow extends android.widget.PopupWindow {
            private CustomPopupWindow() {
            }

            @Override // android.widget.PopupWindow
            public void dismiss() {
                if (!isShowing()) {
                    return;
                }
                super.dismiss();
                android.widget.Editor.this.getPositionListener().removeSubscriber(android.widget.Editor.SuggestionsPopupWindow.this);
                ((android.text.Spannable) android.widget.Editor.this.mTextView.getText()).removeSpan(android.widget.Editor.this.mSuggestionRangeSpan);
                android.widget.Editor.this.mTextView.setCursorVisible(android.widget.Editor.SuggestionsPopupWindow.this.mCursorWasVisibleBeforeSuggestions);
                if (android.widget.Editor.this.hasInsertionController() && !android.widget.Editor.this.extractedTextModeWillBeStarted()) {
                    android.widget.Editor.this.getInsertionController().show();
                }
            }
        }

        public SuggestionsPopupWindow() {
            super();
            this.mIsShowingUp = false;
            this.mMisspelledSpanInfo = new android.widget.Editor.SuggestionSpanInfo();
            this.mCursorWasVisibleBeforeSuggestions = android.widget.Editor.this.mTextView.isCursorVisibleFromAttr();
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void setUp() {
            this.mContext = applyDefaultTheme(android.widget.Editor.this.mTextView.getContext());
            this.mHighlightSpan = new android.text.style.TextAppearanceSpan(this.mContext, android.widget.Editor.this.mTextView.mTextEditSuggestionHighlightStyle);
        }

        private android.content.Context applyDefaultTheme(android.content.Context context) {
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16844176});
            int i = obtainStyledAttributes.getBoolean(0, true) ? 16974410 : 16974411;
            obtainStyledAttributes.recycle();
            return new android.view.ContextThemeWrapper(context, i);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void createPopupWindow() {
            this.mPopupWindow = new android.widget.Editor.SuggestionsPopupWindow.CustomPopupWindow();
            this.mPopupWindow.setInputMethodMode(2);
            this.mPopupWindow.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0));
            this.mPopupWindow.setFocusable(true);
            this.mPopupWindow.setClippingEnabled(false);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void initContentView() {
            byte b = 0;
            this.mContentView = (android.view.ViewGroup) ((android.view.LayoutInflater) this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(android.widget.Editor.this.mTextView.mTextEditSuggestionContainerLayout, (android.view.ViewGroup) null);
            this.mContainerView = (android.widget.LinearLayout) this.mContentView.findViewById(com.android.internal.R.id.suggestionWindowContainer);
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) this.mContainerView.getLayoutParams();
            this.mContainerMarginWidth = marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            this.mContainerMarginTop = marginLayoutParams.topMargin;
            this.mClippingLimitLeft = marginLayoutParams.leftMargin;
            this.mClippingLimitRight = marginLayoutParams.rightMargin;
            this.mSuggestionListView = (android.widget.ListView) this.mContentView.findViewById(com.android.internal.R.id.suggestionContainer);
            this.mSuggestionsAdapter = new android.widget.Editor.SuggestionsPopupWindow.SuggestionAdapter();
            this.mSuggestionListView.setAdapter((android.widget.ListAdapter) this.mSuggestionsAdapter);
            this.mSuggestionListView.setOnItemClickListener(this);
            this.mSuggestionInfos = new android.widget.Editor.SuggestionInfo[5];
            for (int i = 0; i < this.mSuggestionInfos.length; i++) {
                this.mSuggestionInfos[i] = new android.widget.Editor.SuggestionInfo();
            }
            this.mAddToDictionaryButton = (android.widget.TextView) this.mContentView.findViewById(com.android.internal.R.id.addToDictionaryButton);
            this.mAddToDictionaryButton.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.widget.Editor.SuggestionsPopupWindow.1
                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View view) {
                    android.text.style.SuggestionSpan findEquivalentSuggestionSpan = android.widget.Editor.this.findEquivalentSuggestionSpan(android.widget.Editor.SuggestionsPopupWindow.this.mMisspelledSpanInfo);
                    if (findEquivalentSuggestionSpan == null) {
                        return;
                    }
                    android.text.Editable editable = (android.text.Editable) android.widget.Editor.this.mTextView.getText();
                    int spanStart = editable.getSpanStart(findEquivalentSuggestionSpan);
                    int spanEnd = editable.getSpanEnd(findEquivalentSuggestionSpan);
                    if (spanStart < 0 || spanEnd <= spanStart) {
                        return;
                    }
                    java.lang.String substring = android.text.TextUtils.substring(editable, spanStart, spanEnd);
                    android.content.Intent intent = new android.content.Intent(android.provider.Settings.ACTION_USER_DICTIONARY_INSERT);
                    intent.putExtra("word", substring);
                    intent.putExtra("locale", android.widget.Editor.this.mTextView.getTextServicesLocale().toString());
                    intent.setFlags(intent.getFlags() | 268435456);
                    android.widget.Editor.this.mTextView.startActivityAsTextOperationUserIfNecessary(intent);
                    editable.removeSpan(android.widget.Editor.SuggestionsPopupWindow.this.mMisspelledSpanInfo.mSuggestionSpan);
                    android.text.Selection.setSelection(editable, spanEnd);
                    android.widget.Editor.this.updateSpellCheckSpans(spanStart, spanEnd, false);
                    android.widget.Editor.SuggestionsPopupWindow.this.hideWithCleanUp();
                }
            });
            this.mDeleteButton = (android.widget.TextView) this.mContentView.findViewById(com.android.internal.R.id.deleteButton);
            this.mDeleteButton.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.widget.Editor.SuggestionsPopupWindow.2
                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View view) {
                    android.text.Editable editable = (android.text.Editable) android.widget.Editor.this.mTextView.getText();
                    int spanStart = editable.getSpanStart(android.widget.Editor.this.mSuggestionRangeSpan);
                    int spanEnd = editable.getSpanEnd(android.widget.Editor.this.mSuggestionRangeSpan);
                    if (spanStart >= 0 && spanEnd > spanStart) {
                        if (spanEnd < editable.length() && java.lang.Character.isSpaceChar(editable.charAt(spanEnd)) && (spanStart == 0 || java.lang.Character.isSpaceChar(editable.charAt(spanStart - 1)))) {
                            spanEnd++;
                        }
                        android.widget.Editor.this.mTextView.deleteText_internal(spanStart, spanEnd);
                    }
                    android.widget.Editor.SuggestionsPopupWindow.this.hideWithCleanUp();
                }
            });
        }

        public boolean isShowingUp() {
            return this.mIsShowingUp;
        }

        public void onParentLostFocus() {
            this.mIsShowingUp = false;
        }

        private class SuggestionAdapter extends android.widget.BaseAdapter {
            private android.view.LayoutInflater mInflater;

            private SuggestionAdapter() {
                this.mInflater = (android.view.LayoutInflater) android.widget.Editor.SuggestionsPopupWindow.this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            }

            @Override // android.widget.Adapter
            public int getCount() {
                return android.widget.Editor.SuggestionsPopupWindow.this.mNumberOfSuggestions;
            }

            @Override // android.widget.Adapter
            public java.lang.Object getItem(int i) {
                return android.widget.Editor.SuggestionsPopupWindow.this.mSuggestionInfos[i];
            }

            @Override // android.widget.Adapter
            public long getItemId(int i) {
                return i;
            }

            @Override // android.widget.Adapter
            public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
                android.widget.TextView textView = (android.widget.TextView) view;
                if (textView == null) {
                    textView = (android.widget.TextView) this.mInflater.inflate(android.widget.Editor.this.mTextView.mTextEditSuggestionItemLayout, viewGroup, false);
                }
                textView.lambda$setTextAsync$0(android.widget.Editor.SuggestionsPopupWindow.this.mSuggestionInfos[i].mText);
                return textView;
            }
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        public void show() {
            if (!(android.widget.Editor.this.mTextView.getText() instanceof android.text.Editable) || android.widget.Editor.this.extractedTextModeWillBeStarted()) {
                return;
            }
            if (updateSuggestions()) {
                this.mCursorWasVisibleBeforeSuggestions = android.widget.Editor.this.mTextView.isCursorVisibleFromAttr();
                android.widget.Editor.this.mTextView.setCursorVisible(false);
                this.mIsShowingUp = true;
                super.show();
            }
            this.mSuggestionListView.setVisibility(this.mNumberOfSuggestions == 0 ? 8 : 0);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected void measureContent() {
            android.util.DisplayMetrics displayMetrics = android.widget.Editor.this.mTextView.getResources().getDisplayMetrics();
            int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE);
            int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE);
            android.view.View view = null;
            int i = 0;
            for (int i2 = 0; i2 < this.mNumberOfSuggestions; i2++) {
                view = this.mSuggestionsAdapter.getView(i2, view, this.mContentView);
                view.getLayoutParams().width = -2;
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                i = java.lang.Math.max(i, view.getMeasuredWidth());
            }
            if (this.mAddToDictionaryButton.getVisibility() != 8) {
                this.mAddToDictionaryButton.measure(makeMeasureSpec, makeMeasureSpec2);
                i = java.lang.Math.max(i, this.mAddToDictionaryButton.getMeasuredWidth());
            }
            this.mDeleteButton.measure(makeMeasureSpec, makeMeasureSpec2);
            int max = java.lang.Math.max(i, this.mDeleteButton.getMeasuredWidth()) + this.mContainerView.getPaddingLeft() + this.mContainerView.getPaddingRight() + this.mContainerMarginWidth;
            this.mContentView.measure(android.view.View.MeasureSpec.makeMeasureSpec(max, 1073741824), makeMeasureSpec2);
            android.graphics.drawable.Drawable background = this.mPopupWindow.getBackground();
            if (background != null) {
                if (android.widget.Editor.this.mTempRect == null) {
                    android.widget.Editor.this.mTempRect = new android.graphics.Rect();
                }
                background.getPadding(android.widget.Editor.this.mTempRect);
                max += android.widget.Editor.this.mTempRect.left + android.widget.Editor.this.mTempRect.right;
            }
            this.mPopupWindow.setWidth(max);
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getTextOffset() {
            return (android.widget.Editor.this.mTextView.getSelectionStart() + android.widget.Editor.this.mTextView.getSelectionStart()) / 2;
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int getVerticalLocalPosition(int i) {
            return android.widget.Editor.this.mTextView.getLayout().getLineBottom(i, false) - this.mContainerMarginTop;
        }

        @Override // android.widget.Editor.PinnedPopupWindow
        protected int clipVertically(int i) {
            return java.lang.Math.min(i, android.widget.Editor.this.mTextView.getResources().getDisplayMetrics().heightPixels - this.mContentView.getMeasuredHeight());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void hideWithCleanUp() {
            for (android.widget.Editor.SuggestionInfo suggestionInfo : this.mSuggestionInfos) {
                suggestionInfo.clear();
            }
            this.mMisspelledSpanInfo.clear();
            hide();
        }

        private boolean updateSuggestions() {
            int i;
            int underlineColor;
            android.text.Spannable spannable = (android.text.Spannable) android.widget.Editor.this.mTextView.getText();
            this.mNumberOfSuggestions = android.widget.Editor.this.mSuggestionHelper.getSuggestionInfo(this.mSuggestionInfos, this.mMisspelledSpanInfo);
            if (this.mNumberOfSuggestions == 0 && this.mMisspelledSpanInfo.mSuggestionSpan == null) {
                return false;
            }
            int length = android.widget.Editor.this.mTextView.getText().length();
            int i2 = 0;
            for (int i3 = 0; i3 < this.mNumberOfSuggestions; i3++) {
                android.widget.Editor.SuggestionSpanInfo suggestionSpanInfo = this.mSuggestionInfos[i3].mSuggestionSpanInfo;
                length = java.lang.Math.min(length, suggestionSpanInfo.mSpanStart);
                i2 = java.lang.Math.max(i2, suggestionSpanInfo.mSpanEnd);
            }
            if (this.mMisspelledSpanInfo.mSuggestionSpan != null) {
                length = java.lang.Math.min(length, this.mMisspelledSpanInfo.mSpanStart);
                i2 = java.lang.Math.max(i2, this.mMisspelledSpanInfo.mSpanEnd);
            }
            for (int i4 = 0; i4 < this.mNumberOfSuggestions; i4++) {
                highlightTextDifferences(this.mSuggestionInfos[i4], length, i2);
            }
            if (this.mMisspelledSpanInfo.mSuggestionSpan != null && this.mMisspelledSpanInfo.mSpanStart >= 0 && this.mMisspelledSpanInfo.mSpanEnd > this.mMisspelledSpanInfo.mSpanStart) {
                i = 0;
            } else {
                i = 8;
            }
            this.mAddToDictionaryButton.setVisibility(i);
            if (android.widget.Editor.this.mSuggestionRangeSpan == null) {
                android.widget.Editor.this.mSuggestionRangeSpan = new android.text.style.SuggestionRangeSpan();
            }
            if (this.mNumberOfSuggestions != 0) {
                underlineColor = this.mSuggestionInfos[0].mSuggestionSpanInfo.mSuggestionSpan.getUnderlineColor();
            } else {
                underlineColor = this.mMisspelledSpanInfo.mSuggestionSpan.getUnderlineColor();
            }
            if (underlineColor == 0) {
                android.widget.Editor.this.mSuggestionRangeSpan.setBackgroundColor(android.widget.Editor.this.mTextView.mHighlightColor);
            } else {
                android.widget.Editor.this.mSuggestionRangeSpan.setBackgroundColor((underlineColor & 16777215) + (((int) (android.graphics.Color.alpha(underlineColor) * 0.4f)) << 24));
            }
            boolean isVisibleToAccessibility = android.widget.Editor.this.mTextView.isVisibleToAccessibility();
            android.text.SpannedString spannedString = isVisibleToAccessibility ? new android.text.SpannedString(spannable, true) : null;
            spannable.setSpan(android.widget.Editor.this.mSuggestionRangeSpan, length, i2, 33);
            if (isVisibleToAccessibility) {
                android.widget.Editor.this.mTextView.sendAccessibilityEventTypeViewTextChanged(spannedString, length, i2);
            }
            this.mSuggestionsAdapter.notifyDataSetChanged();
            return true;
        }

        private void highlightTextDifferences(android.widget.Editor.SuggestionInfo suggestionInfo, int i, int i2) {
            android.text.Spannable spannable = (android.text.Spannable) android.widget.Editor.this.mTextView.getText();
            int i3 = suggestionInfo.mSuggestionSpanInfo.mSpanStart;
            int i4 = suggestionInfo.mSuggestionSpanInfo.mSpanEnd;
            suggestionInfo.mSuggestionStart = i3 - i;
            suggestionInfo.mSuggestionEnd = suggestionInfo.mSuggestionStart + suggestionInfo.mText.length();
            suggestionInfo.mText.setSpan(this.mHighlightSpan, 0, suggestionInfo.mText.length(), 33);
            java.lang.String obj = spannable.toString();
            suggestionInfo.mText.insert(0, (java.lang.CharSequence) obj.substring(i, i3));
            suggestionInfo.mText.append((java.lang.CharSequence) obj.substring(i4, i2));
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            android.widget.Editor.this.replaceWithSuggestion(this.mSuggestionInfos[i]);
            hideWithCleanUp();
        }
    }

    public class AssistantCallbackHelper {
        private final java.util.Map<android.view.MenuItem, android.view.View.OnClickListener> mAssistClickHandlers = new java.util.HashMap();
        private final android.widget.SelectionActionModeHelper mHelper;
        private android.view.textclassifier.TextClassification mPrevTextClassification;

        public AssistantCallbackHelper(android.widget.SelectionActionModeHelper selectionActionModeHelper) {
            this.mHelper = selectionActionModeHelper;
        }

        public void clearCallbackHandlers() {
            this.mAssistClickHandlers.clear();
        }

        public android.view.View.OnClickListener getOnClickListener(android.view.MenuItem menuItem) {
            return this.mAssistClickHandlers.get(menuItem);
        }

        public void updateAssistMenuItems(android.view.Menu menu, android.view.MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            android.view.textclassifier.TextClassification textClassification = this.mHelper.getTextClassification();
            if (this.mPrevTextClassification == textClassification) {
                return;
            }
            clearAssistMenuItems(menu);
            if (textClassification == null || !shouldEnableAssistMenuItems()) {
                return;
            }
            if (!textClassification.getActions().isEmpty()) {
                addAssistMenuItem(menu, textClassification.getActions().get(0), 16908353, 0, 2, onMenuItemClickListener).setIntent(textClassification.getIntent());
            } else if (hasLegacyAssistItem(textClassification)) {
                android.view.MenuItem intent = menu.add(16908353, 16908353, 0, textClassification.getLabel()).setIcon(textClassification.getIcon()).setIntent(textClassification.getIntent());
                intent.setShowAsAction(2);
                this.mAssistClickHandlers.put(intent, android.view.textclassifier.TextClassification.createIntentOnClickListener(android.view.textclassifier.TextClassification.createPendingIntent(android.widget.Editor.this.mTextView.getContext(), textClassification.getIntent(), createAssistMenuItemPendingIntentRequestCode())));
            }
            int size = textClassification.getActions().size();
            for (int i = 1; i < size; i++) {
                addAssistMenuItem(menu, textClassification.getActions().get(i), 0, (i + 50) - 1, 0, onMenuItemClickListener);
            }
            this.mPrevTextClassification = textClassification;
        }

        private android.view.MenuItem addAssistMenuItem(android.view.Menu menu, android.app.RemoteAction remoteAction, int i, int i2, int i3, android.view.MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            android.view.MenuItem contentDescription = menu.add(16908353, i, i2, remoteAction.getTitle()).setContentDescription(remoteAction.getContentDescription());
            if (remoteAction.shouldShowIcon()) {
                contentDescription.setIcon(remoteAction.getIcon().loadDrawable(android.widget.Editor.this.mTextView.getContext()));
            }
            contentDescription.setShowAsAction(i3);
            this.mAssistClickHandlers.put(contentDescription, android.view.textclassifier.TextClassification.createIntentOnClickListener(remoteAction.getActionIntent()));
            android.widget.Editor.this.mA11ySmartActions.addAction(remoteAction);
            if (onMenuItemClickListener != null) {
                contentDescription.setOnMenuItemClickListener(onMenuItemClickListener);
            }
            return contentDescription;
        }

        private void clearAssistMenuItems(android.view.Menu menu) {
            int i = 0;
            while (i < menu.size()) {
                android.view.MenuItem item = menu.getItem(i);
                if (item.getGroupId() == 16908353) {
                    menu.removeItem(item.getItemId());
                } else {
                    i++;
                }
            }
            android.widget.Editor.this.mA11ySmartActions.reset();
        }

        private boolean hasLegacyAssistItem(android.view.textclassifier.TextClassification textClassification) {
            return ((textClassification.getIcon() == null && android.text.TextUtils.isEmpty(textClassification.getLabel())) || (textClassification.getIntent() == null && textClassification.getOnClickListener() == null)) ? false : true;
        }

        private boolean shouldEnableAssistMenuItems() {
            return android.widget.Editor.this.mTextView.isDeviceProvisioned() && android.view.textclassifier.TextClassificationManager.getSettings(android.widget.Editor.this.mTextView.getContext()).isSmartTextShareEnabled();
        }

        private int createAssistMenuItemPendingIntentRequestCode() {
            if (android.widget.Editor.this.mTextView.hasSelection()) {
                return android.widget.Editor.this.mTextView.getText().subSequence(android.widget.Editor.this.mTextView.getSelectionStart(), android.widget.Editor.this.mTextView.getSelectionEnd()).hashCode();
            }
            return 0;
        }

        public boolean onAssistMenuItemClicked(android.view.MenuItem menuItem) {
            android.content.Intent intent;
            com.android.internal.util.Preconditions.checkArgument(menuItem.getGroupId() == 16908353);
            android.view.textclassifier.TextClassification textClassification = android.widget.Editor.this.getSelectionActionModeHelper().getTextClassification();
            if (!shouldEnableAssistMenuItems() || textClassification == null) {
                return true;
            }
            android.view.View.OnClickListener onClickListener = getOnClickListener(menuItem);
            if (onClickListener == null && (intent = menuItem.getIntent()) != null) {
                onClickListener = android.view.textclassifier.TextClassification.createIntentOnClickListener(android.view.textclassifier.TextClassification.createPendingIntent(android.widget.Editor.this.mTextView.getContext(), intent, createAssistMenuItemPendingIntentRequestCode()));
            }
            if (onClickListener != null) {
                onClickListener.onClick(android.widget.Editor.this.mTextView);
                android.widget.Editor.this.lambda$startActionModeInternal$0();
            }
            return true;
        }
    }

    private class TextActionModeCallback extends android.view.ActionMode.Callback2 {
        private final int mHandleHeight;
        private final boolean mHasSelection;
        private final android.widget.Editor.AssistantCallbackHelper mHelper;
        private final android.graphics.Path mSelectionPath = new android.graphics.Path();
        private final android.graphics.RectF mSelectionBounds = new android.graphics.RectF();

        TextActionModeCallback(int i) {
            this.mHelper = android.widget.Editor.this.new AssistantCallbackHelper(android.widget.Editor.this.getSelectionActionModeHelper());
            this.mHasSelection = i == 0 || (android.widget.Editor.this.mTextIsSelectable && i == 2);
            if (this.mHasSelection) {
                android.widget.Editor.SelectionModifierCursorController selectionController = android.widget.Editor.this.getSelectionController();
                if (selectionController.mStartHandle == null) {
                    android.widget.Editor.this.loadHandleDrawables(false);
                    selectionController.initHandles();
                    selectionController.hide();
                }
                this.mHandleHeight = java.lang.Math.max(android.widget.Editor.this.mSelectHandleLeft.getMinimumHeight(), android.widget.Editor.this.mSelectHandleRight.getMinimumHeight());
                return;
            }
            android.widget.Editor.InsertionPointCursorController insertionController = android.widget.Editor.this.getInsertionController();
            if (insertionController != null) {
                insertionController.getHandle();
                this.mHandleHeight = android.widget.Editor.this.mSelectHandleCenter.getMinimumHeight();
            } else {
                this.mHandleHeight = 0;
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
            this.mHelper.clearCallbackHandlers();
            actionMode.setTitle((java.lang.CharSequence) null);
            actionMode.setSubtitle((java.lang.CharSequence) null);
            actionMode.setTitleOptionalHint(true);
            populateMenuWithItems(menu);
            android.view.ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null && !customCallback.onCreateActionMode(actionMode, menu)) {
                android.text.Selection.setSelection((android.text.Spannable) android.widget.Editor.this.mTextView.getText(), android.widget.Editor.this.mTextView.getSelectionEnd());
                return false;
            }
            if (android.widget.Editor.this.mTextView.canProcessText()) {
                android.widget.Editor.this.mProcessTextIntentActionsHandler.onInitializeMenu(menu);
            }
            if (this.mHasSelection && !android.widget.Editor.this.mTextView.hasTransientState()) {
                android.widget.Editor.this.mTextView.setHasTransientState(true);
            }
            return true;
        }

        private android.view.ActionMode.Callback getCustomCallback() {
            if (this.mHasSelection) {
                return android.widget.Editor.this.mCustomSelectionActionModeCallback;
            }
            return android.widget.Editor.this.mCustomInsertionActionModeCallback;
        }

        private void populateMenuWithItems(android.view.Menu menu) {
            java.lang.String selectedText;
            if (android.widget.Editor.this.mTextView.canCut()) {
                menu.add(0, 16908320, 4, 17039363).setAlphabeticShortcut(com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty.TARGET_X).setShowAsAction(2);
            }
            if (android.widget.Editor.this.mTextView.canCopy()) {
                menu.add(0, 16908321, 5, 17039361).setAlphabeticShortcut('c').setShowAsAction(2);
            }
            if (android.widget.Editor.this.mTextView.canPaste()) {
                menu.add(0, 16908322, 6, 17039371).setAlphabeticShortcut('v').setShowAsAction(2);
            }
            if (android.widget.Editor.this.mTextView.canShare()) {
                menu.add(0, 16908341, 7, com.android.internal.R.string.share).setShowAsAction(1);
            }
            if (android.widget.Editor.this.mTextView.canRequestAutofill() && ((selectedText = android.widget.Editor.this.mTextView.getSelectedText()) == null || selectedText.isEmpty())) {
                menu.add(0, 16908355, 10, 17039386).setShowAsAction(0);
            }
            if (android.widget.Editor.this.mTextView.canPasteAsPlainText()) {
                menu.add(0, 16908337, 11, 17039385).setShowAsAction(1);
            }
            updateSelectAllItem(menu);
            updateReplaceItem(menu);
            this.mHelper.updateAssistMenuItems(menu, null);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
            updateSelectAllItem(menu);
            updateReplaceItem(menu);
            this.mHelper.updateAssistMenuItems(menu, null);
            android.view.ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null) {
                return customCallback.onPrepareActionMode(actionMode, menu);
            }
            return true;
        }

        private void updateSelectAllItem(android.view.Menu menu) {
            boolean canSelectAllText = android.widget.Editor.this.mTextView.canSelectAllText();
            boolean z = menu.findItem(16908319) != null;
            if (canSelectAllText && !z) {
                menu.add(0, 16908319, 8, 17039373).setShowAsAction(1);
            } else if (!canSelectAllText && z) {
                menu.removeItem(16908319);
            }
        }

        private void updateReplaceItem(android.view.Menu menu) {
            boolean z = android.widget.Editor.this.mTextView.isSuggestionsEnabled() && android.widget.Editor.this.shouldOfferToShowSuggestions();
            boolean z2 = menu.findItem(16908340) != null;
            if (z && !z2) {
                menu.add(0, 16908340, 9, com.android.internal.R.string.replace).setShowAsAction(1);
            } else if (!z && z2) {
                menu.removeItem(16908340);
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(android.view.ActionMode actionMode, android.view.MenuItem menuItem) {
            android.widget.Editor.this.getSelectionActionModeHelper().onSelectionAction(menuItem.getItemId(), menuItem.getTitle().toString());
            if (android.widget.Editor.this.mProcessTextIntentActionsHandler.performMenuItemAction(menuItem)) {
                return true;
            }
            android.view.ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null && customCallback.onActionItemClicked(actionMode, menuItem)) {
                return true;
            }
            if (menuItem.getGroupId() == 16908353 && this.mHelper.onAssistMenuItemClicked(menuItem)) {
                return true;
            }
            return android.widget.Editor.this.mTextView.onTextContextMenuItem(menuItem.getItemId());
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(android.view.ActionMode actionMode) {
            android.widget.Editor.this.getSelectionActionModeHelper().onDestroyActionMode();
            android.widget.Editor.this.mTextActionMode = null;
            android.view.ActionMode.Callback customCallback = getCustomCallback();
            if (customCallback != null) {
                customCallback.onDestroyActionMode(actionMode);
            }
            if (!android.widget.Editor.this.mPreserveSelection) {
                android.text.Selection.setSelection((android.text.Spannable) android.widget.Editor.this.mTextView.getText(), android.widget.Editor.this.mTextView.getSelectionEnd());
            }
            if (android.widget.Editor.this.mSelectionModifierCursorController != null) {
                android.widget.Editor.this.mSelectionModifierCursorController.hide();
            }
            this.mHelper.clearCallbackHandlers();
            android.widget.Editor.this.mRequestingLinkActionMode = false;
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(android.view.ActionMode actionMode, android.view.View view, android.graphics.Rect rect) {
            if (!view.equals(android.widget.Editor.this.mTextView) || android.widget.Editor.this.mTextView.getLayout() == null) {
                super.onGetContentRect(actionMode, view, rect);
                return;
            }
            int selectionStartTransformed = android.widget.Editor.this.mTextView.getSelectionStartTransformed();
            int selectionEndTransformed = android.widget.Editor.this.mTextView.getSelectionEndTransformed();
            android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
            if (selectionStartTransformed != selectionEndTransformed) {
                this.mSelectionPath.reset();
                layout.getSelectionPath(selectionStartTransformed, selectionEndTransformed, this.mSelectionPath);
                this.mSelectionPath.computeBounds(this.mSelectionBounds, true);
                this.mSelectionBounds.bottom += this.mHandleHeight;
            } else {
                int lineForOffset = layout.getLineForOffset(selectionStartTransformed);
                float clampHorizontalPosition = android.widget.Editor.this.clampHorizontalPosition(null, layout.getPrimaryHorizontal(selectionEndTransformed));
                this.mSelectionBounds.set(clampHorizontalPosition, layout.getLineTop(lineForOffset), clampHorizontalPosition, layout.getLineBottom(lineForOffset) + this.mHandleHeight);
            }
            float viewportToContentHorizontalOffset = android.widget.Editor.this.mTextView.viewportToContentHorizontalOffset();
            float viewportToContentVerticalOffset = android.widget.Editor.this.mTextView.viewportToContentVerticalOffset();
            rect.set((int) java.lang.Math.floor(this.mSelectionBounds.left + viewportToContentHorizontalOffset), (int) java.lang.Math.floor(this.mSelectionBounds.top + viewportToContentVerticalOffset), (int) java.lang.Math.ceil(this.mSelectionBounds.right + viewportToContentHorizontalOffset), (int) java.lang.Math.ceil(this.mSelectionBounds.bottom + viewportToContentVerticalOffset));
        }
    }

    private final class CursorAnchorInfoNotifier implements android.widget.Editor.TextViewPositionListener {
        final android.view.inputmethod.CursorAnchorInfo.Builder mCursorAnchorInfoBuilder;
        final android.graphics.Matrix mViewToScreenMatrix;

        private CursorAnchorInfoNotifier() {
            this.mCursorAnchorInfoBuilder = new android.view.inputmethod.CursorAnchorInfo.Builder();
            this.mViewToScreenMatrix = new android.graphics.Matrix();
        }

        @Override // android.widget.Editor.TextViewPositionListener
        public void updatePosition(int i, int i2, boolean z, boolean z2) {
            android.view.inputmethod.InputMethodManager inputMethodManager;
            android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo;
            android.widget.Editor.InputMethodState inputMethodState = android.widget.Editor.this.mInputMethodState;
            if (inputMethodState != null && inputMethodState.mBatchEditNesting <= 0 && (inputMethodManager = android.widget.Editor.this.getInputMethodManager()) != null && inputMethodManager.hasActiveInputConnection(android.widget.Editor.this.mTextView) && (inputMethodState.mUpdateCursorAnchorInfoMode & 3) != 0 && (cursorAnchorInfo = android.widget.Editor.this.mTextView.getCursorAnchorInfo(inputMethodState.mUpdateCursorAnchorInfoFilter, this.mCursorAnchorInfoBuilder, this.mViewToScreenMatrix)) != null) {
                inputMethodManager.updateCursorAnchorInfo(android.widget.Editor.this.mTextView, cursorAnchorInfo);
                android.widget.Editor.this.mInputMethodState.mUpdateCursorAnchorInfoMode &= -2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MagnifierMotionAnimator {
        private static final long DURATION = 100;
        private float mAnimationCurrentX;
        private float mAnimationCurrentY;
        private float mAnimationStartX;
        private float mAnimationStartY;
        private final android.animation.ValueAnimator mAnimator;
        private float mLastX;
        private float mLastY;
        private final android.widget.Magnifier mMagnifier;
        private boolean mMagnifierIsShowing;

        private MagnifierMotionAnimator(android.widget.Magnifier magnifier) {
            this.mMagnifier = magnifier;
            this.mAnimator = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator.setDuration(DURATION);
            this.mAnimator.setInterpolator(new android.view.animation.LinearInterpolator());
            this.mAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.Editor$MagnifierMotionAnimator$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                    android.widget.Editor.MagnifierMotionAnimator.this.lambda$new$0(valueAnimator);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(android.animation.ValueAnimator valueAnimator) {
            this.mAnimationCurrentX = this.mAnimationStartX + ((this.mLastX - this.mAnimationStartX) * valueAnimator.getAnimatedFraction());
            this.mAnimationCurrentY = this.mAnimationStartY + ((this.mLastY - this.mAnimationStartY) * valueAnimator.getAnimatedFraction());
            this.mMagnifier.show(this.mAnimationCurrentX, this.mAnimationCurrentY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void show(float f, float f2) {
            if (this.mMagnifierIsShowing && f2 != this.mLastY) {
                if (this.mAnimator.isRunning()) {
                    this.mAnimator.cancel();
                    this.mAnimationStartX = this.mAnimationCurrentX;
                    this.mAnimationStartY = this.mAnimationCurrentY;
                } else {
                    this.mAnimationStartX = this.mLastX;
                    this.mAnimationStartY = this.mLastY;
                }
                this.mAnimator.start();
            } else if (!this.mAnimator.isRunning()) {
                this.mMagnifier.show(f, f2);
            }
            this.mLastX = f;
            this.mLastY = f2;
            this.mMagnifierIsShowing = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void update() {
            this.mMagnifier.update();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dismiss() {
            this.mMagnifier.dismiss();
            this.mAnimator.cancel();
            this.mMagnifierIsShowing = false;
        }
    }

    public abstract class HandleView extends android.view.View implements android.widget.Editor.TextViewPositionListener {
        private static final int HISTORY_SIZE = 5;
        private static final int TOUCH_UP_FILTER_DELAY_AFTER = 150;
        private static final int TOUCH_UP_FILTER_DELAY_BEFORE = 350;
        private final android.widget.PopupWindow mContainer;
        private float mCurrentDragInitialTouchRawX;
        protected android.graphics.drawable.Drawable mDrawable;
        protected android.graphics.drawable.Drawable mDrawableLtr;
        protected android.graphics.drawable.Drawable mDrawableRtl;
        protected int mHorizontalGravity;
        protected int mHotspotX;
        private final int mIdealFingerToCursorOffset;
        private final float mIdealVerticalOffset;
        private boolean mIsDragging;
        private int mLastParentX;
        private int mLastParentXOnScreen;
        private int mLastParentY;
        private int mLastParentYOnScreen;
        private int mMinSize;
        private int mNumberPreviousOffsets;
        private boolean mPositionHasChanged;
        private int mPositionX;
        private int mPositionY;
        protected int mPrevLine;
        protected int mPreviousLineTouched;
        protected int mPreviousOffset;
        private int mPreviousOffsetIndex;
        private final int[] mPreviousOffsets;
        private final long[] mPreviousOffsetsTimes;
        private float mTextViewScaleX;
        private float mTextViewScaleY;
        private float mTouchOffsetY;
        private float mTouchToWindowOffsetX;
        private float mTouchToWindowOffsetY;

        public abstract int getCurrentCursorOffset();

        protected abstract int getHorizontalGravity(boolean z);

        protected abstract int getHotspotX(android.graphics.drawable.Drawable drawable, boolean z);

        protected abstract int getMagnifierHandleTrigger();

        protected abstract void updatePosition(float f, float f2, boolean z);

        protected abstract void updateSelection(int i);

        private HandleView(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, int i) {
            super(android.widget.Editor.this.mTextView.getContext());
            this.mPreviousOffset = -1;
            this.mPositionHasChanged = true;
            this.mPrevLine = -1;
            this.mPreviousLineTouched = -1;
            this.mCurrentDragInitialTouchRawX = -1.0f;
            this.mPreviousOffsetsTimes = new long[5];
            this.mPreviousOffsets = new int[5];
            this.mPreviousOffsetIndex = 0;
            this.mNumberPreviousOffsets = 0;
            setId(i);
            this.mContainer = new android.widget.PopupWindow(android.widget.Editor.this.mTextView.getContext(), (android.util.AttributeSet) null, 16843464);
            this.mContainer.setSplitTouchEnabled(true);
            this.mContainer.setClippingEnabled(false);
            this.mContainer.setWindowLayoutType(1002);
            this.mContainer.setWidth(-2);
            this.mContainer.setHeight(-2);
            this.mContainer.setContentView(this);
            setDrawables(drawable, drawable2);
            this.mMinSize = android.widget.Editor.this.mTextView.getContext().getResources().getDimensionPixelSize(com.android.internal.R.dimen.text_handle_min_size);
            float preferredHeight = getPreferredHeight();
            this.mTouchOffsetY = (-0.3f) * preferredHeight;
            int intCoreSetting = android.app.AppGlobals.getIntCoreSetting(android.widget.WidgetFlags.KEY_FINGER_TO_CURSOR_DISTANCE, -1);
            if (intCoreSetting < 0 || intCoreSetting > 100) {
                this.mIdealVerticalOffset = preferredHeight * 0.7f;
                this.mIdealFingerToCursorOffset = (int) (this.mIdealVerticalOffset - this.mTouchOffsetY);
            } else {
                this.mIdealFingerToCursorOffset = (int) android.util.TypedValue.applyDimension(1, intCoreSetting, android.widget.Editor.this.mTextView.getContext().getResources().getDisplayMetrics());
                this.mIdealVerticalOffset = this.mIdealFingerToCursorOffset + this.mTouchOffsetY;
            }
        }

        public float getIdealVerticalOffset() {
            return this.mIdealVerticalOffset;
        }

        final int getIdealFingerToCursorOffset() {
            return this.mIdealFingerToCursorOffset;
        }

        void setDrawables(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2) {
            this.mDrawableLtr = drawable;
            this.mDrawableRtl = drawable2;
            updateDrawable(true);
        }

        protected void updateDrawable(boolean z) {
            android.text.Layout layout;
            if ((!z && this.mIsDragging) || (layout = android.widget.Editor.this.mTextView.getLayout()) == null) {
                return;
            }
            int currentCursorOffset = getCurrentCursorOffset();
            boolean isAtRtlRun = isAtRtlRun(layout, currentCursorOffset);
            android.graphics.drawable.Drawable drawable = this.mDrawable;
            this.mDrawable = isAtRtlRun ? this.mDrawableRtl : this.mDrawableLtr;
            this.mHotspotX = getHotspotX(this.mDrawable, isAtRtlRun);
            this.mHorizontalGravity = getHorizontalGravity(isAtRtlRun);
            if (drawable != this.mDrawable && isShowing()) {
                this.mPositionX = ((getCursorHorizontalPosition(layout, currentCursorOffset) - this.mHotspotX) - getHorizontalOffset()) + getCursorOffset();
                this.mPositionX += android.widget.Editor.this.mTextView.viewportToContentHorizontalOffset();
                this.mPositionHasChanged = true;
                updatePosition(this.mLastParentX, this.mLastParentY, false, false);
                postInvalidate();
            }
        }

        private void startTouchUpFilter(int i) {
            this.mNumberPreviousOffsets = 0;
            addPositionToTouchUpFilter(i);
        }

        private void addPositionToTouchUpFilter(int i) {
            this.mPreviousOffsetIndex = (this.mPreviousOffsetIndex + 1) % 5;
            this.mPreviousOffsets[this.mPreviousOffsetIndex] = i;
            this.mPreviousOffsetsTimes[this.mPreviousOffsetIndex] = android.os.SystemClock.uptimeMillis();
            this.mNumberPreviousOffsets++;
        }

        private void filterOnTouchUp(boolean z) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int i = this.mPreviousOffsetIndex;
            int min = java.lang.Math.min(this.mNumberPreviousOffsets, 5);
            int i2 = 0;
            while (i2 < min && uptimeMillis - this.mPreviousOffsetsTimes[i] < 150) {
                i2++;
                i = ((this.mPreviousOffsetIndex - i2) + 5) % 5;
            }
            if (i2 > 0 && i2 < min && uptimeMillis - this.mPreviousOffsetsTimes[i] > 350) {
                positionAtCursorOffset(this.mPreviousOffsets[i], false, z);
            }
        }

        public boolean offsetHasBeenChanged() {
            return this.mNumberPreviousOffsets > 1;
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            setMeasuredDimension(getPreferredWidth(), getPreferredHeight());
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            if (isShowing()) {
                positionAtCursorOffset(getCurrentCursorOffset(), true, false);
            }
        }

        protected final int getPreferredWidth() {
            return java.lang.Math.max(this.mDrawable.getIntrinsicWidth(), this.mMinSize);
        }

        protected final int getPreferredHeight() {
            return java.lang.Math.max(this.mDrawable.getIntrinsicHeight(), this.mMinSize);
        }

        public void show() {
            if (isShowing()) {
                return;
            }
            android.widget.Editor.this.getPositionListener().addSubscriber(this, true);
            this.mPreviousOffset = -1;
            positionAtCursorOffset(getCurrentCursorOffset(), false, false);
        }

        protected void dismiss() {
            this.mIsDragging = false;
            this.mContainer.dismiss();
            onDetached();
        }

        public void hide() {
            dismiss();
            android.widget.Editor.this.getPositionListener().removeSubscriber(this);
        }

        public boolean isShowing() {
            return this.mContainer.isShowing();
        }

        private boolean shouldShow() {
            if (this.mIsDragging) {
                return true;
            }
            if (android.widget.Editor.this.mTextView.isInBatchEditMode()) {
                return false;
            }
            return android.widget.Editor.this.mTextView.isPositionVisible(this.mPositionX + this.mHotspotX + getHorizontalOffset(), this.mPositionY);
        }

        private void setVisible(boolean z) {
            this.mContainer.getContentView().setVisibility(z ? 0 : 4);
        }

        protected boolean isAtRtlRun(android.text.Layout layout, int i) {
            return layout.isRtlCharAt(android.widget.Editor.this.mTextView.originalToTransformed(i, 1));
        }

        public float getHorizontal(android.text.Layout layout, int i) {
            return layout.getPrimaryHorizontal(android.widget.Editor.this.mTextView.originalToTransformed(i, 1));
        }

        public int getLineForOffset(android.text.Layout layout, int i) {
            return layout.getLineForOffset(android.widget.Editor.this.mTextView.originalToTransformed(i, 1));
        }

        protected int getOffsetAtCoordinate(android.text.Layout layout, int i, float f) {
            return android.widget.Editor.this.mTextView.getOffsetAtCoordinate(i, f);
        }

        protected void positionAtCursorOffset(int i, boolean z, boolean z2) {
            android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
            if (layout == null) {
                android.widget.Editor.this.prepareCursorControllers();
                return;
            }
            boolean z3 = i != this.mPreviousOffset;
            if (z3 || z) {
                if (z3) {
                    updateSelection(i);
                    if (z2 && android.widget.Editor.this.mHapticTextHandleEnabled) {
                        android.widget.Editor.this.mTextView.performHapticFeedback(9);
                    }
                    addPositionToTouchUpFilter(i);
                }
                int lineForOffset = getLineForOffset(layout, i);
                this.mPrevLine = lineForOffset;
                this.mPositionX = ((getCursorHorizontalPosition(layout, i) - this.mHotspotX) - getHorizontalOffset()) + getCursorOffset();
                this.mPositionY = layout.getLineBottom(lineForOffset, false);
                this.mPositionX += android.widget.Editor.this.mTextView.viewportToContentHorizontalOffset();
                this.mPositionY += android.widget.Editor.this.mTextView.viewportToContentVerticalOffset();
                this.mPreviousOffset = i;
                this.mPositionHasChanged = true;
            }
        }

        int getCursorHorizontalPosition(android.text.Layout layout, int i) {
            return (int) (getHorizontal(layout, i) - 0.5f);
        }

        @Override // android.widget.Editor.TextViewPositionListener
        public void updatePosition(int i, int i2, boolean z, boolean z2) {
            positionAtCursorOffset(getCurrentCursorOffset(), z2, false);
            if (z || this.mPositionHasChanged) {
                if (this.mIsDragging) {
                    if (i != this.mLastParentX || i2 != this.mLastParentY) {
                        this.mTouchToWindowOffsetX += i - this.mLastParentX;
                        this.mTouchToWindowOffsetY += i2 - this.mLastParentY;
                        this.mLastParentX = i;
                        this.mLastParentY = i2;
                    }
                    onHandleMoved();
                }
                if (shouldShow()) {
                    int[] iArr = {this.mPositionX + this.mHotspotX + getHorizontalOffset(), this.mPositionY};
                    android.widget.Editor.this.mTextView.transformFromViewToWindowSpace(iArr);
                    iArr[0] = iArr[0] - (this.mHotspotX + getHorizontalOffset());
                    if (isShowing()) {
                        this.mContainer.update(iArr[0], iArr[1], -1, -1);
                    } else {
                        this.mContainer.showAtLocation(android.widget.Editor.this.mTextView, 0, iArr[0], iArr[1]);
                    }
                } else if (isShowing()) {
                    dismiss();
                }
                this.mPositionHasChanged = false;
            }
        }

        @Override // android.view.View
        protected void onDraw(android.graphics.Canvas canvas) {
            int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
            int horizontalOffset = getHorizontalOffset();
            this.mDrawable.setBounds(horizontalOffset, 0, intrinsicWidth + horizontalOffset, this.mDrawable.getIntrinsicHeight());
            this.mDrawable.draw(canvas);
        }

        private int getHorizontalOffset() {
            int preferredWidth = getPreferredWidth();
            int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
            switch (this.mHorizontalGravity) {
                case 3:
                    return 0;
                case 4:
                default:
                    return (preferredWidth - intrinsicWidth) / 2;
                case 5:
                    return preferredWidth - intrinsicWidth;
            }
        }

        protected int getCursorOffset() {
            return 0;
        }

        private boolean tooLargeTextForMagnifier() {
            if (android.widget.Editor.this.mNewMagnifierEnabled) {
                android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
                int lineForOffset = getLineForOffset(layout, getCurrentCursorOffset());
                return layout.getLineBottom(lineForOffset, false) - layout.getLineTop(lineForOffset) >= android.widget.Editor.this.mMaxLineHeightForMagnifier;
            }
            float round = java.lang.Math.round(android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getHeight() / android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getZoom());
            android.graphics.Paint.FontMetrics fontMetrics = android.widget.Editor.this.mTextView.getPaint().getFontMetrics();
            return (fontMetrics.descent - fontMetrics.ascent) * this.mTextViewScaleY > round;
        }

        private boolean checkForTransforms() {
            if (android.widget.Editor.this.mMagnifierAnimator.mMagnifierIsShowing) {
                return true;
            }
            if (android.widget.Editor.this.mTextView.getRotation() != 0.0f || android.widget.Editor.this.mTextView.getRotationX() != 0.0f || android.widget.Editor.this.mTextView.getRotationY() != 0.0f) {
                return false;
            }
            this.mTextViewScaleX = android.widget.Editor.this.mTextView.getScaleX();
            this.mTextViewScaleY = android.widget.Editor.this.mTextView.getScaleY();
            for (android.view.ViewParent parent = android.widget.Editor.this.mTextView.getParent(); parent != null; parent = parent.getParent()) {
                if (parent instanceof android.view.View) {
                    android.view.View view = (android.view.View) parent;
                    if (view.getRotation() != 0.0f || view.getRotationX() != 0.0f || view.getRotationY() != 0.0f) {
                        return false;
                    }
                    this.mTextViewScaleX *= view.getScaleX();
                    this.mTextViewScaleY *= view.getScaleY();
                }
            }
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x00c9  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x019c  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00de  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x0135  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x018d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private boolean obtainMagnifierShowCoordinates(android.view.MotionEvent motionEvent, android.graphics.PointF pointF) {
            int selectionStart;
            int i;
            boolean z;
            float lineLeft;
            float lineRight;
            float f;
            float f2;
            float round;
            int magnifierHandleTrigger = getMagnifierHandleTrigger();
            switch (magnifierHandleTrigger) {
                case 0:
                    selectionStart = android.widget.Editor.this.mTextView.getSelectionStart();
                    i = -1;
                    break;
                case 1:
                    selectionStart = android.widget.Editor.this.mTextView.getSelectionStart();
                    i = android.widget.Editor.this.mTextView.getSelectionEnd();
                    break;
                case 2:
                    selectionStart = android.widget.Editor.this.mTextView.getSelectionEnd();
                    i = android.widget.Editor.this.mTextView.getSelectionStart();
                    break;
                default:
                    selectionStart = -1;
                    i = -1;
                    break;
            }
            if (selectionStart == -1) {
                return false;
            }
            if (motionEvent.getActionMasked() == 0) {
                this.mCurrentDragInitialTouchRawX = motionEvent.getRawX();
            } else if (motionEvent.getActionMasked() == 1) {
                this.mCurrentDragInitialTouchRawX = -1.0f;
            }
            android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
            int lineForOffset = getLineForOffset(layout, selectionStart);
            boolean z2 = i != -1 && lineForOffset == getLineForOffset(layout, selectionStart);
            if (z2) {
                if ((selectionStart < i) != (getHorizontal(android.widget.Editor.this.mTextView.getLayout(), selectionStart) < getHorizontal(android.widget.Editor.this.mTextView.getLayout(), i))) {
                    z = true;
                    android.widget.Editor.this.mTextView.getLocationOnScreen(new int[2]);
                    float rawX = motionEvent.getRawX() - r10[0];
                    if (android.widget.Editor.this.mNewMagnifierEnabled) {
                        float totalPaddingLeft = android.widget.Editor.this.mTextView.getTotalPaddingLeft() - android.widget.Editor.this.mTextView.getScrollX();
                        float totalPaddingLeft2 = android.widget.Editor.this.mTextView.getTotalPaddingLeft() - android.widget.Editor.this.mTextView.getScrollX();
                        if (z2) {
                            if ((magnifierHandleTrigger == 2) ^ z) {
                                lineLeft = totalPaddingLeft + getHorizontal(android.widget.Editor.this.mTextView.getLayout(), i);
                                if (z2) {
                                    if ((magnifierHandleTrigger == 1) ^ z) {
                                        lineRight = totalPaddingLeft2 + getHorizontal(android.widget.Editor.this.mTextView.getLayout(), i);
                                        f = lineLeft * this.mTextViewScaleX;
                                        f2 = this.mTextViewScaleX * lineRight;
                                        round = java.lang.Math.round(android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getWidth() / android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getZoom()) / 2.0f;
                                        if (rawX >= f - round || rawX > round + f2) {
                                            return false;
                                        }
                                    }
                                }
                                lineRight = totalPaddingLeft2 + android.widget.Editor.this.mTextView.getLayout().getLineRight(lineForOffset);
                                f = lineLeft * this.mTextViewScaleX;
                                f2 = this.mTextViewScaleX * lineRight;
                                round = java.lang.Math.round(android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getWidth() / android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getZoom()) / 2.0f;
                                if (rawX >= f - round) {
                                }
                                return false;
                            }
                        }
                        lineLeft = totalPaddingLeft + android.widget.Editor.this.mTextView.getLayout().getLineLeft(lineForOffset);
                        if (z2) {
                        }
                        lineRight = totalPaddingLeft2 + android.widget.Editor.this.mTextView.getLayout().getLineRight(lineForOffset);
                        f = lineLeft * this.mTextViewScaleX;
                        f2 = this.mTextViewScaleX * lineRight;
                        round = java.lang.Math.round(android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getWidth() / android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getZoom()) / 2.0f;
                        if (rawX >= f - round) {
                        }
                        return false;
                    }
                    f2 = android.widget.Editor.this.mTextView.getWidth();
                    f = 0.0f;
                    if (rawX < 0.0f || rawX > f2) {
                        return false;
                    }
                    if (this.mTextViewScaleX != 1.0f) {
                        rawX = (((motionEvent.getRawX() - this.mCurrentDragInitialTouchRawX) * this.mTextViewScaleX) + this.mCurrentDragInitialTouchRawX) - r10[0];
                    }
                    pointF.x = java.lang.Math.max(f, java.lang.Math.min(f2, rawX));
                    pointF.y = ((((android.widget.Editor.this.mTextView.getLayout().getLineTop(lineForOffset) + android.widget.Editor.this.mTextView.getLayout().getLineBottom(lineForOffset, false)) / 2.0f) + android.widget.Editor.this.mTextView.getTotalPaddingTop()) - android.widget.Editor.this.mTextView.getScrollY()) * this.mTextViewScaleY;
                    return true;
                }
            }
            z = false;
            android.widget.Editor.this.mTextView.getLocationOnScreen(new int[2]);
            float rawX2 = motionEvent.getRawX() - r10[0];
            if (android.widget.Editor.this.mNewMagnifierEnabled) {
            }
            if (this.mTextViewScaleX != 1.0f) {
            }
            pointF.x = java.lang.Math.max(f, java.lang.Math.min(f2, rawX2));
            pointF.y = ((((android.widget.Editor.this.mTextView.getLayout().getLineTop(lineForOffset) + android.widget.Editor.this.mTextView.getLayout().getLineBottom(lineForOffset, false)) / 2.0f) + android.widget.Editor.this.mTextView.getTotalPaddingTop()) - android.widget.Editor.this.mTextView.getScrollY()) * this.mTextViewScaleY;
            return true;
        }

        private boolean handleOverlapsMagnifier(android.widget.Editor.HandleView handleView, android.graphics.Rect rect) {
            android.widget.PopupWindow popupWindow = handleView.mContainer;
            if (!popupWindow.hasDecorView()) {
                return false;
            }
            return android.graphics.Rect.intersects(new android.graphics.Rect(popupWindow.getDecorViewLayoutParams().x, popupWindow.getDecorViewLayoutParams().y, popupWindow.getDecorViewLayoutParams().x + popupWindow.getContentView().getWidth(), popupWindow.getDecorViewLayoutParams().y + popupWindow.getContentView().getHeight()), rect);
        }

        private android.widget.Editor.HandleView getOtherSelectionHandle() {
            android.widget.Editor.SelectionModifierCursorController selectionController = android.widget.Editor.this.getSelectionController();
            if (selectionController == null || !selectionController.isActive()) {
                return null;
            }
            if (selectionController.mStartHandle != this) {
                return selectionController.mStartHandle;
            }
            return selectionController.mEndHandle;
        }

        private void updateHandlesVisibility() {
            android.graphics.Point position = android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getPosition();
            if (position == null) {
                return;
            }
            setVisible((handleOverlapsMagnifier(this, new android.graphics.Rect(position.x, position.y, position.x + android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getWidth(), position.y + android.widget.Editor.this.mMagnifierAnimator.mMagnifier.getHeight())) || android.widget.Editor.this.mDrawCursorOnMagnifier) ? false : true);
            android.widget.Editor.HandleView otherSelectionHandle = getOtherSelectionHandle();
            if (otherSelectionHandle != null) {
                otherSelectionHandle.setVisible(!handleOverlapsMagnifier(otherSelectionHandle, r1));
            }
        }

        protected final void updateMagnifier(android.view.MotionEvent motionEvent) {
            if (android.widget.Editor.this.getMagnifierAnimator() == null) {
                return;
            }
            android.graphics.PointF pointF = new android.graphics.PointF();
            if (checkForTransforms() && !tooLargeTextForMagnifier() && obtainMagnifierShowCoordinates(motionEvent, pointF) && android.widget.Editor.this.mTextView.showUIForTouchScreen()) {
                android.widget.Editor.this.mRenderCursorRegardlessTiming = true;
                android.widget.Editor.this.mTextView.invalidateCursorPath();
                android.widget.Editor.this.suspendBlink();
                if (android.widget.Editor.this.mNewMagnifierEnabled) {
                    android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
                    int lineForOffset = getLineForOffset(layout, getCurrentCursorOffset());
                    android.widget.Editor.this.mDrawCursorOnMagnifier = pointF.x < ((float) ((((int) layout.getLineLeft(lineForOffset)) + (android.widget.Editor.this.mTextView.getTotalPaddingLeft() - android.widget.Editor.this.mTextView.getScrollX())) + (-20))) || pointF.x > ((float) ((((int) layout.getLineRight(lineForOffset)) + (android.widget.Editor.this.mTextView.getTotalPaddingLeft() - android.widget.Editor.this.mTextView.getScrollX())) + 20));
                    android.widget.Editor.this.mMagnifierAnimator.mMagnifier.setDrawCursor(android.widget.Editor.this.mDrawCursorOnMagnifier, android.widget.Editor.this.mDrawableForCursor);
                    boolean z = android.widget.Editor.this.mCursorVisible;
                    android.widget.Editor.this.mCursorVisible = true ^ android.widget.Editor.this.mDrawCursorOnMagnifier;
                    if (android.widget.Editor.this.mCursorVisible && !z) {
                        android.widget.Editor.this.updateCursorPosition();
                    }
                    int lineBottom = layout.getLineBottom(lineForOffset, false) - layout.getLineTop(lineForOffset);
                    float f = android.widget.Editor.this.mInitialZoom;
                    if (lineBottom < android.widget.Editor.this.mMinLineHeightForMagnifier) {
                        f = (f * android.widget.Editor.this.mMinLineHeightForMagnifier) / lineBottom;
                    }
                    android.widget.Editor.this.mMagnifierAnimator.mMagnifier.updateSourceFactors(lineBottom, f);
                    android.widget.Editor.this.mMagnifierAnimator.mMagnifier.show(pointF.x, pointF.y);
                } else {
                    android.widget.Editor.this.mMagnifierAnimator.show(pointF.x, pointF.y);
                }
                updateHandlesVisibility();
                return;
            }
            dismissMagnifier();
        }

        protected final void dismissMagnifier() {
            if (android.widget.Editor.this.mMagnifierAnimator != null) {
                android.widget.Editor.this.mMagnifierAnimator.dismiss();
                android.widget.Editor.this.mRenderCursorRegardlessTiming = false;
                android.widget.Editor.this.mDrawCursorOnMagnifier = false;
                if (!android.widget.Editor.this.mCursorVisible) {
                    android.widget.Editor.this.mCursorVisible = true;
                    android.widget.Editor.this.mTextView.invalidate();
                }
                android.widget.Editor.this.resumeBlink();
                setVisible(true);
                android.widget.Editor.HandleView otherSelectionHandle = getOtherSelectionHandle();
                if (otherSelectionHandle != null) {
                    otherSelectionHandle.setVisible(true);
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x00d2, code lost:
        
            return true;
         */
        @Override // android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
            float min;
            android.widget.Editor.this.updateFloatingToolbarVisibility(motionEvent);
            switch (motionEvent.getActionMasked()) {
                case 0:
                    startTouchUpFilter(getCurrentCursorOffset());
                    android.widget.Editor.PositionListener positionListener = android.widget.Editor.this.getPositionListener();
                    this.mLastParentX = positionListener.getPositionX();
                    this.mLastParentY = positionListener.getPositionY();
                    this.mLastParentXOnScreen = positionListener.getPositionXOnScreen();
                    this.mLastParentYOnScreen = positionListener.getPositionYOnScreen();
                    float rawX = (motionEvent.getRawX() - this.mLastParentXOnScreen) + this.mLastParentX;
                    float rawY = (motionEvent.getRawY() - this.mLastParentYOnScreen) + this.mLastParentY;
                    this.mTouchToWindowOffsetX = rawX - this.mPositionX;
                    this.mTouchToWindowOffsetY = rawY - this.mPositionY;
                    this.mIsDragging = true;
                    this.mPreviousLineTouched = -1;
                    break;
                case 1:
                    filterOnTouchUp(motionEvent.isFromSource(4098));
                    this.mIsDragging = false;
                    updateDrawable(false);
                    break;
                case 2:
                    float rawX2 = (motionEvent.getRawX() - this.mLastParentXOnScreen) + this.mLastParentX;
                    float rawY2 = (motionEvent.getRawY() - this.mLastParentYOnScreen) + this.mLastParentY;
                    float f = this.mTouchToWindowOffsetY - this.mLastParentY;
                    float f2 = (rawY2 - this.mPositionY) - this.mLastParentY;
                    if (f < this.mIdealVerticalOffset) {
                        min = java.lang.Math.max(java.lang.Math.min(f2, this.mIdealVerticalOffset), f);
                    } else {
                        min = java.lang.Math.min(java.lang.Math.max(f2, this.mIdealVerticalOffset), f);
                    }
                    this.mTouchToWindowOffsetY = min + this.mLastParentY;
                    updatePosition((rawX2 - this.mTouchToWindowOffsetX) + this.mHotspotX + getHorizontalOffset(), (rawY2 - this.mTouchToWindowOffsetY) + this.mTouchOffsetY, motionEvent.isFromSource(4098));
                    break;
                case 3:
                    this.mIsDragging = false;
                    updateDrawable(false);
                    break;
            }
        }

        public boolean isDragging() {
            return this.mIsDragging;
        }

        void onHandleMoved() {
        }

        public void onDetached() {
            dismissMagnifier();
        }

        @Override // android.view.View
        protected void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            setSystemGestureExclusionRects(java.util.Collections.singletonList(new android.graphics.Rect(0, 0, i, i2)));
        }
    }

    private class InsertionHandleView extends android.widget.Editor.HandleView {
        private final int mDeltaHeight;
        private final int mDrawableOpacity;
        private java.lang.Runnable mHider;
        private boolean mIsInActionMode;
        private boolean mIsTouchDown;
        private float mLastDownRawX;
        private float mLastDownRawY;
        private long mLastUpTime;
        private boolean mOffsetChanged;
        private int mOffsetDown;
        private boolean mPendingDismissOnUp;
        private float mTouchDownX;
        private float mTouchDownY;

        InsertionHandleView(android.graphics.drawable.Drawable drawable) {
            super(drawable, drawable, com.android.internal.R.id.insertion_handle);
            int i = 0;
            this.mIsTouchDown = false;
            this.mPendingDismissOnUp = false;
            int i2 = 255;
            if (android.widget.Editor.this.mFlagInsertionHandleGesturesEnabled) {
                i = 25;
                int intCoreSetting = android.app.AppGlobals.getIntCoreSetting(android.widget.WidgetFlags.KEY_INSERTION_HANDLE_DELTA_HEIGHT, 25);
                int i3 = 50;
                int intCoreSetting2 = android.app.AppGlobals.getIntCoreSetting(android.widget.WidgetFlags.KEY_INSERTION_HANDLE_OPACITY, 50);
                if (intCoreSetting >= -25 && intCoreSetting <= 50) {
                    i = intCoreSetting;
                }
                if (intCoreSetting2 >= 10 && intCoreSetting2 <= 100) {
                    i3 = intCoreSetting2;
                }
                i2 = (i3 * 255) / 100;
            }
            this.mDeltaHeight = i;
            this.mDrawableOpacity = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void hideAfterDelay() {
            if (this.mHider == null) {
                this.mHider = new java.lang.Runnable() { // from class: android.widget.Editor.InsertionHandleView.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.widget.Editor.InsertionHandleView.this.hide();
                    }
                };
            } else {
                removeHiderCallback();
            }
            android.widget.Editor.this.mTextView.postDelayed(this.mHider, 4000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeHiderCallback() {
            if (this.mHider != null) {
                android.widget.Editor.this.mTextView.removeCallbacks(this.mHider);
            }
        }

        @Override // android.widget.Editor.HandleView
        protected int getHotspotX(android.graphics.drawable.Drawable drawable, boolean z) {
            return drawable.getIntrinsicWidth() / 2;
        }

        @Override // android.widget.Editor.HandleView
        protected int getHorizontalGravity(boolean z) {
            return 1;
        }

        @Override // android.widget.Editor.HandleView
        protected int getCursorOffset() {
            int cursorOffset = super.getCursorOffset();
            if (android.widget.Editor.this.mDrawableForCursor != null) {
                android.widget.Editor.this.mDrawableForCursor.getPadding(android.widget.Editor.this.mTempRect);
                return cursorOffset + (((android.widget.Editor.this.mDrawableForCursor.getIntrinsicWidth() - android.widget.Editor.this.mTempRect.left) - android.widget.Editor.this.mTempRect.right) / 2);
            }
            return cursorOffset;
        }

        @Override // android.widget.Editor.HandleView
        int getCursorHorizontalPosition(android.text.Layout layout, int i) {
            if (android.widget.Editor.this.mDrawableForCursor != null) {
                return android.widget.Editor.this.clampHorizontalPosition(android.widget.Editor.this.mDrawableForCursor, getHorizontal(layout, i)) + android.widget.Editor.this.mTempRect.left;
            }
            return super.getCursorHorizontalPosition(layout, i);
        }

        @Override // android.widget.Editor.HandleView, android.view.View
        protected void onMeasure(int i, int i2) {
            if (android.widget.Editor.this.mFlagInsertionHandleGesturesEnabled) {
                setMeasuredDimension(getPreferredWidth(), java.lang.Math.max(getPreferredHeight() + this.mDeltaHeight, this.mDrawable.getIntrinsicHeight()));
            } else {
                super.onMeasure(i, i2);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x008b, code lost:
        
            return r0;
         */
        @Override // android.widget.Editor.HandleView, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
            if (!android.widget.Editor.this.mTextView.isFromPrimePointer(motionEvent, true)) {
                return true;
            }
            if (android.widget.Editor.this.mFlagInsertionHandleGesturesEnabled && android.widget.Editor.this.mFlagCursorDragFromAnywhereEnabled) {
                return touchThrough(motionEvent);
            }
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            switch (motionEvent.getActionMasked()) {
                case 0:
                    this.mLastDownRawX = motionEvent.getRawX();
                    this.mLastDownRawY = motionEvent.getRawY();
                    updateMagnifier(motionEvent);
                    break;
                case 1:
                    if (!offsetHasBeenChanged()) {
                        if (android.widget.EditorTouchState.isDistanceWithin(this.mLastDownRawX, this.mLastDownRawY, motionEvent.getRawX(), motionEvent.getRawY(), android.view.ViewConfiguration.get(android.widget.Editor.this.mTextView.getContext()).getScaledTouchSlop())) {
                            android.widget.Editor.this.toggleInsertionActionMode();
                        }
                    } else if (android.widget.Editor.this.mTextActionMode != null) {
                        android.widget.Editor.this.mTextActionMode.invalidateContentRect();
                    }
                    hideAfterDelay();
                    dismissMagnifier();
                    break;
                case 2:
                    updateMagnifier(motionEvent);
                    break;
                case 3:
                    hideAfterDelay();
                    dismissMagnifier();
                    break;
            }
        }

        private boolean touchThrough(android.view.MotionEvent motionEvent) {
            int selectionStart;
            int actionMasked = motionEvent.getActionMasked();
            switch (actionMasked) {
                case 0:
                    this.mIsTouchDown = true;
                    this.mOffsetChanged = false;
                    this.mOffsetDown = android.widget.Editor.this.mTextView.getSelectionStart();
                    this.mTouchDownX = motionEvent.getX();
                    this.mTouchDownY = motionEvent.getY();
                    this.mIsInActionMode = android.widget.Editor.this.mTextActionMode != null;
                    if (motionEvent.getEventTime() - this.mLastUpTime < android.view.ViewConfiguration.getDoubleTapTimeout()) {
                        android.widget.Editor.this.lambda$startActionModeInternal$0();
                    }
                    android.widget.Editor.this.mTouchState.setIsOnHandle(true);
                    break;
                case 1:
                    this.mLastUpTime = motionEvent.getEventTime();
                    break;
            }
            boolean onTouchEvent = android.widget.Editor.this.mTextView.onTouchEvent(transformEventForTouchThrough(motionEvent));
            if (actionMasked == 1 || actionMasked == 3) {
                this.mIsTouchDown = false;
                if (this.mPendingDismissOnUp) {
                    dismiss();
                }
                android.widget.Editor.this.mTouchState.setIsOnHandle(false);
            }
            if (!this.mOffsetChanged && ((selectionStart = android.widget.Editor.this.mTextView.getSelectionStart()) != android.widget.Editor.this.mTextView.getSelectionEnd() || this.mOffsetDown != selectionStart)) {
                this.mOffsetChanged = true;
            }
            if (!this.mOffsetChanged && actionMasked == 1) {
                if (this.mIsInActionMode) {
                    android.widget.Editor.this.lambda$startActionModeInternal$0();
                } else {
                    android.widget.Editor.this.startInsertionActionMode();
                }
            }
            return onTouchEvent;
        }

        private android.view.MotionEvent transformEventForTouchThrough(android.view.MotionEvent motionEvent) {
            android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
            int lineForOffset = getLineForOffset(layout, getCurrentCursorOffset());
            int lineBottom = layout.getLineBottom(lineForOffset, false) - layout.getLineTop(lineForOffset);
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            matrix.setTranslate(((motionEvent.getRawX() - motionEvent.getX()) + (getMeasuredWidth() >> 1)) - this.mTouchDownX, ((motionEvent.getRawY() - motionEvent.getY()) - (lineBottom >> 1)) - this.mTouchDownY);
            motionEvent.transform(matrix);
            android.widget.Editor.this.mTextView.toLocalMotionEvent(motionEvent);
            return motionEvent;
        }

        @Override // android.widget.Editor.HandleView
        public boolean isShowing() {
            if (this.mPendingDismissOnUp) {
                return false;
            }
            return super.isShowing();
        }

        @Override // android.widget.Editor.HandleView
        public void show() {
            super.show();
            this.mPendingDismissOnUp = false;
            this.mDrawable.setAlpha(this.mDrawableOpacity);
        }

        @Override // android.widget.Editor.HandleView
        public void dismiss() {
            if (this.mIsTouchDown) {
                this.mPendingDismissOnUp = true;
                this.mDrawable.setAlpha(0);
            } else {
                super.dismiss();
                this.mPendingDismissOnUp = false;
            }
        }

        @Override // android.widget.Editor.HandleView
        protected void updateDrawable(boolean z) {
            super.updateDrawable(z);
            this.mDrawable.setAlpha(this.mDrawableOpacity);
        }

        @Override // android.widget.Editor.HandleView
        public int getCurrentCursorOffset() {
            return android.widget.Editor.this.mTextView.getSelectionStart();
        }

        @Override // android.widget.Editor.HandleView
        public void updateSelection(int i) {
            android.text.Selection.setSelection((android.text.Spannable) android.widget.Editor.this.mTextView.getText(), i);
        }

        @Override // android.widget.Editor.HandleView
        protected void updatePosition(float f, float f2, boolean z) {
            android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
            int i = -1;
            if (layout != null) {
                if (this.mPreviousLineTouched == -1) {
                    this.mPreviousLineTouched = android.widget.Editor.this.mTextView.getLineAtCoordinate(f2);
                }
                int currentLineAdjustedForSlop = android.widget.Editor.this.getCurrentLineAdjustedForSlop(layout, this.mPreviousLineTouched, f2);
                i = getOffsetAtCoordinate(layout, currentLineAdjustedForSlop, f);
                this.mPreviousLineTouched = currentLineAdjustedForSlop;
            }
            positionAtCursorOffset(i, false, z);
            if (android.widget.Editor.this.mTextActionMode != null) {
                android.widget.Editor.this.invalidateActionMode();
            }
        }

        @Override // android.widget.Editor.HandleView
        void onHandleMoved() {
            super.onHandleMoved();
            removeHiderCallback();
        }

        @Override // android.widget.Editor.HandleView
        public void onDetached() {
            super.onDetached();
            removeHiderCallback();
        }

        @Override // android.widget.Editor.HandleView
        protected int getMagnifierHandleTrigger() {
            return 0;
        }
    }

    public final class SelectionHandleView extends android.widget.Editor.HandleView {
        private final int mHandleType;
        private boolean mInWord;
        private boolean mLanguageDirectionChanged;
        private float mPrevX;
        private final float mTextViewEdgeSlop;
        private final int[] mTextViewLocation;
        private float mTouchWordDelta;

        public SelectionHandleView(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, int i, int i2) {
            super(drawable, drawable2, i);
            this.mInWord = false;
            this.mLanguageDirectionChanged = false;
            this.mTextViewLocation = new int[2];
            this.mHandleType = i2;
            this.mTextViewEdgeSlop = android.view.ViewConfiguration.get(android.widget.Editor.this.mTextView.getContext()).getScaledTouchSlop() * 4;
        }

        private boolean isStartHandle() {
            return this.mHandleType == 0;
        }

        @Override // android.widget.Editor.HandleView
        protected int getHotspotX(android.graphics.drawable.Drawable drawable, boolean z) {
            if (z == isStartHandle()) {
                return drawable.getIntrinsicWidth() / 4;
            }
            return (drawable.getIntrinsicWidth() * 3) / 4;
        }

        @Override // android.widget.Editor.HandleView
        protected int getHorizontalGravity(boolean z) {
            return z == isStartHandle() ? 3 : 5;
        }

        @Override // android.widget.Editor.HandleView
        public int getCurrentCursorOffset() {
            return isStartHandle() ? android.widget.Editor.this.mTextView.getSelectionStart() : android.widget.Editor.this.mTextView.getSelectionEnd();
        }

        @Override // android.widget.Editor.HandleView
        protected void updateSelection(int i) {
            if (isStartHandle()) {
                android.text.Selection.setSelection((android.text.Spannable) android.widget.Editor.this.mTextView.getText(), i, android.widget.Editor.this.mTextView.getSelectionEnd());
            } else {
                android.text.Selection.setSelection((android.text.Spannable) android.widget.Editor.this.mTextView.getText(), android.widget.Editor.this.mTextView.getSelectionStart(), i);
            }
            updateDrawable(false);
            if (android.widget.Editor.this.mTextActionMode != null) {
                android.widget.Editor.this.invalidateActionMode();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:68:0x0128, code lost:
        
            if (r16.this$0.mTextView.canScrollHorizontally(r11 ? -1 : 1) != false) goto L72;
         */
        @Override // android.widget.Editor.HandleView
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        protected void updatePosition(float f, float f2, boolean z) {
            boolean z2;
            boolean z3;
            boolean z4;
            int i;
            int offsetToLeftOf;
            android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
            if (layout == null) {
                positionAndAdjustForCrossingHandles(android.widget.Editor.this.mTextView.getOffsetForPosition(f, f2), z);
                return;
            }
            if (this.mPreviousLineTouched == -1) {
                this.mPreviousLineTouched = android.widget.Editor.this.mTextView.getLineAtCoordinate(f2);
            }
            int selectionEnd = isStartHandle() ? android.widget.Editor.this.mTextView.getSelectionEnd() : android.widget.Editor.this.mTextView.getSelectionStart();
            int currentLineAdjustedForSlop = android.widget.Editor.this.getCurrentLineAdjustedForSlop(layout, this.mPreviousLineTouched, f2);
            int offsetAtCoordinate = getOffsetAtCoordinate(layout, currentLineAdjustedForSlop, f);
            if ((isStartHandle() && offsetAtCoordinate >= selectionEnd) || (!isStartHandle() && offsetAtCoordinate <= selectionEnd)) {
                currentLineAdjustedForSlop = getLineForOffset(layout, selectionEnd);
                offsetAtCoordinate = getOffsetAtCoordinate(layout, currentLineAdjustedForSlop, f);
            }
            int wordEnd = android.widget.Editor.this.getWordEnd(offsetAtCoordinate);
            int wordStart = android.widget.Editor.this.getWordStart(offsetAtCoordinate);
            if (this.mPrevX == -1.0f) {
                this.mPrevX = f;
            }
            int currentCursorOffset = getCurrentCursorOffset();
            boolean isAtRtlRun = isAtRtlRun(layout, currentCursorOffset);
            boolean isAtRtlRun2 = isAtRtlRun(layout, offsetAtCoordinate);
            boolean z5 = true;
            if (layout.isLevelBoundary(android.widget.Editor.this.mTextView.originalToTransformed(offsetAtCoordinate, 1)) || ((isAtRtlRun && !isAtRtlRun2) || (!isAtRtlRun && isAtRtlRun2))) {
                this.mLanguageDirectionChanged = true;
                this.mTouchWordDelta = 0.0f;
                positionAndAdjustForCrossingHandles(offsetAtCoordinate, z);
                return;
            }
            boolean z6 = false;
            if (this.mLanguageDirectionChanged) {
                positionAndAdjustForCrossingHandles(offsetAtCoordinate, z);
                this.mTouchWordDelta = 0.0f;
                this.mLanguageDirectionChanged = false;
                return;
            }
            float f3 = f - this.mPrevX;
            if (isStartHandle()) {
                z2 = currentLineAdjustedForSlop < this.mPreviousLineTouched;
            } else {
                z2 = currentLineAdjustedForSlop > this.mPreviousLineTouched;
            }
            if (isAtRtlRun2 == isStartHandle()) {
                z3 = (f3 > 0.0f) | z2;
            } else {
                z3 = (f3 < 0.0f) | z2;
            }
            if (android.widget.Editor.this.mTextView.getHorizontallyScrolling() && positionNearEdgeOfScrollingView(f, isAtRtlRun2)) {
                if (!isStartHandle() || android.widget.Editor.this.mTextView.getScrollX() == 0) {
                    if (!isStartHandle()) {
                    }
                }
                if ((z3 && ((isStartHandle() && offsetAtCoordinate < currentCursorOffset) || (!isStartHandle() && offsetAtCoordinate > currentCursorOffset))) || !z3) {
                    this.mTouchWordDelta = 0.0f;
                    if (isAtRtlRun2 == isStartHandle()) {
                        offsetToLeftOf = layout.getOffsetToRightOf(this.mPreviousOffset);
                    } else {
                        offsetToLeftOf = layout.getOffsetToLeftOf(this.mPreviousOffset);
                    }
                    positionAndAdjustForCrossingHandles(offsetToLeftOf, z);
                    return;
                }
            }
            if (z3) {
                int i2 = isStartHandle() ? wordStart : wordEnd;
                if ((!this.mInWord || (!isStartHandle() ? currentLineAdjustedForSlop > this.mPrevLine : currentLineAdjustedForSlop < this.mPrevLine)) && isAtRtlRun2 == isAtRtlRun(layout, i2)) {
                    z6 = true;
                }
                if (!z6) {
                    wordEnd = offsetAtCoordinate;
                } else {
                    if (getLineForOffset(layout, i2) != currentLineAdjustedForSlop) {
                        i2 = isStartHandle() ? layout.getLineStart(currentLineAdjustedForSlop) : layout.getLineEnd(currentLineAdjustedForSlop);
                    }
                    if (isStartHandle()) {
                        i = wordEnd - ((wordEnd - i2) / 2);
                    } else {
                        i = ((i2 - wordStart) / 2) + wordStart;
                    }
                    if (isStartHandle() && (offsetAtCoordinate <= i || currentLineAdjustedForSlop < this.mPrevLine)) {
                        wordEnd = wordStart;
                    } else if (isStartHandle() || (offsetAtCoordinate < i && currentLineAdjustedForSlop <= this.mPrevLine)) {
                        wordEnd = this.mPreviousOffset;
                    }
                }
                if ((isStartHandle() && wordEnd < offsetAtCoordinate) || (!isStartHandle() && wordEnd > offsetAtCoordinate)) {
                    this.mTouchWordDelta = android.widget.Editor.this.mTextView.convertToLocalHorizontalCoordinate(f) - getHorizontal(layout, wordEnd);
                } else {
                    this.mTouchWordDelta = 0.0f;
                }
                offsetAtCoordinate = wordEnd;
            } else {
                int offsetAtCoordinate2 = getOffsetAtCoordinate(layout, currentLineAdjustedForSlop, f - this.mTouchWordDelta);
                if (isStartHandle()) {
                    z4 = offsetAtCoordinate2 > this.mPreviousOffset || currentLineAdjustedForSlop > this.mPrevLine;
                } else {
                    z4 = offsetAtCoordinate2 < this.mPreviousOffset || currentLineAdjustedForSlop < this.mPrevLine;
                }
                if (z4) {
                    if (currentLineAdjustedForSlop != this.mPrevLine) {
                        if (isStartHandle()) {
                            wordEnd = wordStart;
                        }
                        if ((isStartHandle() && wordEnd < offsetAtCoordinate) || (!isStartHandle() && wordEnd > offsetAtCoordinate)) {
                            this.mTouchWordDelta = android.widget.Editor.this.mTextView.convertToLocalHorizontalCoordinate(f) - getHorizontal(layout, wordEnd);
                        } else {
                            this.mTouchWordDelta = 0.0f;
                        }
                        offsetAtCoordinate = wordEnd;
                    } else {
                        offsetAtCoordinate = offsetAtCoordinate2;
                    }
                } else {
                    if ((isStartHandle() && offsetAtCoordinate2 < this.mPreviousOffset) || (!isStartHandle() && offsetAtCoordinate2 > this.mPreviousOffset)) {
                        this.mTouchWordDelta = android.widget.Editor.this.mTextView.convertToLocalHorizontalCoordinate(f) - getHorizontal(layout, this.mPreviousOffset);
                    }
                    z5 = false;
                }
            }
            if (z5) {
                this.mPreviousLineTouched = currentLineAdjustedForSlop;
                positionAndAdjustForCrossingHandles(offsetAtCoordinate, z);
            }
            this.mPrevX = f;
        }

        @Override // android.widget.Editor.HandleView
        protected void positionAtCursorOffset(int i, boolean z, boolean z2) {
            super.positionAtCursorOffset(i, z, z2);
            this.mInWord = (i == -1 || android.widget.Editor.this.getWordIteratorWithText().isBoundary(i)) ? false : true;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:10:0x002d, code lost:
        
            return r0;
         */
        @Override // android.widget.Editor.HandleView, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
            if (!android.widget.Editor.this.mTextView.isFromPrimePointer(motionEvent, true)) {
                return true;
            }
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            switch (motionEvent.getActionMasked()) {
                case 0:
                    this.mTouchWordDelta = 0.0f;
                    this.mPrevX = -1.0f;
                    updateMagnifier(motionEvent);
                    break;
                case 1:
                case 3:
                    dismissMagnifier();
                    break;
                case 2:
                    updateMagnifier(motionEvent);
                    break;
            }
        }

        private void positionAndAdjustForCrossingHandles(int i, boolean z) {
            int unpackRangeEndFromLong;
            int selectionEnd = isStartHandle() ? android.widget.Editor.this.mTextView.getSelectionEnd() : android.widget.Editor.this.mTextView.getSelectionStart();
            if ((isStartHandle() && i >= selectionEnd) || (!isStartHandle() && i <= selectionEnd)) {
                this.mTouchWordDelta = 0.0f;
                android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
                if (layout != null && i != selectionEnd) {
                    float horizontal = getHorizontal(layout, i);
                    float horizontal2 = getHorizontal(layout, selectionEnd, !isStartHandle());
                    float horizontal3 = getHorizontal(layout, this.mPreviousOffset);
                    if ((horizontal3 < horizontal2 && horizontal < horizontal2) || (horizontal3 > horizontal2 && horizontal > horizontal2)) {
                        int currentCursorOffset = getCurrentCursorOffset();
                        if (!isStartHandle()) {
                            currentCursorOffset = java.lang.Math.max(currentCursorOffset - 1, 0);
                        }
                        long runRange = layout.getRunRange(android.widget.Editor.this.mTextView.originalToTransformed(currentCursorOffset, 1));
                        if (isStartHandle()) {
                            unpackRangeEndFromLong = android.text.TextUtils.unpackRangeStartFromLong(runRange);
                        } else {
                            unpackRangeEndFromLong = android.text.TextUtils.unpackRangeEndFromLong(runRange);
                        }
                        positionAtCursorOffset(android.widget.Editor.this.mTextView.transformedToOriginal(unpackRangeEndFromLong, 1), false, z);
                        return;
                    }
                }
                i = android.widget.Editor.this.getNextCursorOffset(selectionEnd, !isStartHandle());
            }
            positionAtCursorOffset(i, false, z);
        }

        private boolean positionNearEdgeOfScrollingView(float f, boolean z) {
            android.widget.Editor.this.mTextView.getLocationOnScreen(this.mTextViewLocation);
            return z == isStartHandle() ? f > ((float) ((this.mTextViewLocation[0] + android.widget.Editor.this.mTextView.getWidth()) - android.widget.Editor.this.mTextView.getPaddingRight())) - this.mTextViewEdgeSlop : f < ((float) (this.mTextViewLocation[0] + android.widget.Editor.this.mTextView.getPaddingLeft())) + this.mTextViewEdgeSlop;
        }

        @Override // android.widget.Editor.HandleView
        protected boolean isAtRtlRun(android.text.Layout layout, int i) {
            int transformedToOriginal = android.widget.Editor.this.mTextView.transformedToOriginal(i, 0);
            if (!isStartHandle()) {
                transformedToOriginal = java.lang.Math.max(transformedToOriginal - 1, 0);
            }
            return layout.isRtlCharAt(transformedToOriginal);
        }

        @Override // android.widget.Editor.HandleView
        public float getHorizontal(android.text.Layout layout, int i) {
            return getHorizontal(layout, i, isStartHandle());
        }

        private float getHorizontal(android.text.Layout layout, int i, boolean z) {
            int originalToTransformed = android.widget.Editor.this.mTextView.originalToTransformed(i, 1);
            if (layout.isRtlCharAt(z ? originalToTransformed : java.lang.Math.max(originalToTransformed - 1, 0)) != (layout.getParagraphDirection(layout.getLineForOffset(originalToTransformed)) == -1)) {
                return layout.getSecondaryHorizontal(originalToTransformed);
            }
            return layout.getPrimaryHorizontal(originalToTransformed);
        }

        @Override // android.widget.Editor.HandleView
        protected int getOffsetAtCoordinate(android.text.Layout layout, int i, float f) {
            float convertToLocalHorizontalCoordinate = android.widget.Editor.this.mTextView.convertToLocalHorizontalCoordinate(f);
            int offsetForHorizontal = layout.getOffsetForHorizontal(i, convertToLocalHorizontalCoordinate, true);
            if (!layout.isLevelBoundary(offsetForHorizontal)) {
                return android.widget.Editor.this.mTextView.transformedToOriginal(offsetForHorizontal, 1);
            }
            boolean z = false;
            int offsetForHorizontal2 = layout.getOffsetForHorizontal(i, convertToLocalHorizontalCoordinate, false);
            int originalToTransformed = android.widget.Editor.this.mTextView.originalToTransformed(getCurrentCursorOffset(), 1);
            int abs = java.lang.Math.abs(offsetForHorizontal - originalToTransformed);
            int abs2 = java.lang.Math.abs(offsetForHorizontal2 - originalToTransformed);
            if (abs >= abs2) {
                if (abs > abs2) {
                    offsetForHorizontal = offsetForHorizontal2;
                } else {
                    if (!isStartHandle()) {
                        originalToTransformed = java.lang.Math.max(originalToTransformed - 1, 0);
                    }
                    boolean isRtlCharAt = layout.isRtlCharAt(originalToTransformed);
                    if (layout.getParagraphDirection(i) == -1) {
                        z = true;
                    }
                    if (isRtlCharAt != z) {
                        offsetForHorizontal = offsetForHorizontal2;
                    }
                }
            }
            return android.widget.Editor.this.mTextView.transformedToOriginal(offsetForHorizontal, 1);
        }

        @Override // android.widget.Editor.HandleView
        protected int getMagnifierHandleTrigger() {
            if (isStartHandle()) {
                return 1;
            }
            return 2;
        }
    }

    public void setLineChangeSlopMinMaxForTesting(int i, int i2) {
        this.mLineChangeSlopMin = i;
        this.mLineChangeSlopMax = i2;
    }

    public int getCurrentLineAdjustedForSlop(android.text.Layout layout, int i, float f) {
        int lineAtCoordinate = this.mTextView.getLineAtCoordinate(f);
        if (layout == null || i >= layout.getLineCount() || layout.getLineCount() <= 0 || i < 0) {
            return lineAtCoordinate;
        }
        if (java.lang.Math.abs(lineAtCoordinate - i) >= 2) {
            return lineAtCoordinate;
        }
        int lineHeight = this.mTextView.getLineHeight();
        int max = java.lang.Math.max(0, java.lang.Math.max(this.mLineChangeSlopMin, java.lang.Math.min(this.mLineChangeSlopMax, ((int) (this.mLineSlopRatio * lineHeight)) + lineHeight)) - lineHeight);
        float viewportToContentVerticalOffset = this.mTextView.viewportToContentVerticalOffset();
        if (lineAtCoordinate > i && f >= layout.getLineBottom(i) + max + viewportToContentVerticalOffset) {
            return lineAtCoordinate;
        }
        if (lineAtCoordinate < i && f <= (layout.getLineTop(i) - max) + viewportToContentVerticalOffset) {
            return lineAtCoordinate;
        }
        return i;
    }

    void loadCursorDrawable() {
        if (this.mDrawableForCursor == null) {
            this.mDrawableForCursor = this.mTextView.getTextCursorDrawable();
        }
    }

    public class InsertionPointCursorController implements android.widget.Editor.CursorController {
        private android.widget.Editor.InsertionHandleView mHandle;
        private boolean mIsDraggingCursor;
        private boolean mIsTouchSnappedToHandleDuringDrag;
        private int mPrevLineDuringDrag;

        public InsertionPointCursorController() {
        }

        public void onTouchEvent(android.view.MotionEvent motionEvent) {
            if (android.widget.Editor.this.hasSelectionController() && android.widget.Editor.this.getSelectionController().isCursorBeingModified()) {
            }
            switch (motionEvent.getActionMasked()) {
                case 1:
                case 3:
                    if (this.mIsDraggingCursor) {
                        endCursorDrag(motionEvent);
                        break;
                    }
                    break;
                case 2:
                    if (!motionEvent.isFromSource(8194)) {
                        if (!android.widget.Editor.this.mTextView.isAutoHandwritingEnabled() || !isFromStylus(motionEvent)) {
                            if (this.mIsDraggingCursor) {
                                performCursorDrag(motionEvent);
                                break;
                            } else if (android.widget.Editor.this.mFlagCursorDragFromAnywhereEnabled && android.widget.Editor.this.mTextView.getLayout() != null && android.widget.Editor.this.mTextView.isFocused() && android.widget.Editor.this.mTouchState.isMovedEnoughForDrag()) {
                                if (android.widget.Editor.this.mTouchState.getInitialDragDirectionXYRatio() > android.widget.Editor.this.mCursorDragDirectionMinXYRatio || android.widget.Editor.this.mTouchState.isOnHandle()) {
                                    startCursorDrag(motionEvent);
                                    break;
                                }
                            }
                        }
                    }
                    break;
            }
        }

        private boolean isFromStylus(android.view.MotionEvent motionEvent) {
            return motionEvent.getToolType(motionEvent.getActionIndex()) == 2;
        }

        private void positionCursorDuringDrag(android.view.MotionEvent motionEvent) {
            this.mPrevLineDuringDrag = getLineDuringDrag(motionEvent);
            int offsetAtCoordinate = android.widget.Editor.this.mTextView.getOffsetAtCoordinate(this.mPrevLineDuringDrag, motionEvent.getX());
            int selectionStart = android.widget.Editor.this.mTextView.getSelectionStart();
            int selectionEnd = android.widget.Editor.this.mTextView.getSelectionEnd();
            if (offsetAtCoordinate == selectionStart && offsetAtCoordinate == selectionEnd) {
                return;
            }
            android.text.Selection.setSelection((android.text.Spannable) android.widget.Editor.this.mTextView.getText(), offsetAtCoordinate);
            android.widget.Editor.this.updateCursorPosition();
            if (android.widget.Editor.this.mHapticTextHandleEnabled) {
                android.widget.Editor.this.mTextView.performHapticFeedback(9);
            }
        }

        private int getLineDuringDrag(android.view.MotionEvent motionEvent) {
            float y;
            android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
            if (this.mPrevLineDuringDrag == -1) {
                return android.widget.Editor.this.getCurrentLineAdjustedForSlop(layout, this.mPrevLineDuringDrag, motionEvent.getY());
            }
            if (android.widget.Editor.this.mTouchState.isOnHandle()) {
                y = motionEvent.getRawY() - android.widget.Editor.this.mTextView.getLocationOnScreen()[1];
            } else {
                y = motionEvent.getY();
            }
            int currentLineAdjustedForSlop = android.widget.Editor.this.getCurrentLineAdjustedForSlop(layout, this.mPrevLineDuringDrag, y - getHandle().getIdealFingerToCursorOffset());
            if (this.mIsTouchSnappedToHandleDuringDrag) {
                return currentLineAdjustedForSlop;
            }
            if (currentLineAdjustedForSlop < this.mPrevLineDuringDrag) {
                return java.lang.Math.min(this.mPrevLineDuringDrag, android.widget.Editor.this.getCurrentLineAdjustedForSlop(layout, this.mPrevLineDuringDrag, y));
            }
            this.mIsTouchSnappedToHandleDuringDrag = true;
            return currentLineAdjustedForSlop;
        }

        private void startCursorDrag(android.view.MotionEvent motionEvent) {
            this.mIsDraggingCursor = true;
            this.mIsTouchSnappedToHandleDuringDrag = false;
            this.mPrevLineDuringDrag = -1;
            android.widget.Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(true);
            android.widget.Editor.this.mTextView.cancelLongPress();
            positionCursorDuringDrag(motionEvent);
            show();
            getHandle().removeHiderCallback();
            getHandle().updateMagnifier(motionEvent);
        }

        private void performCursorDrag(android.view.MotionEvent motionEvent) {
            positionCursorDuringDrag(motionEvent);
            getHandle().updateMagnifier(motionEvent);
        }

        private void endCursorDrag(android.view.MotionEvent motionEvent) {
            this.mIsDraggingCursor = false;
            this.mIsTouchSnappedToHandleDuringDrag = false;
            this.mPrevLineDuringDrag = -1;
            getHandle().dismissMagnifier();
            getHandle().hideAfterDelay();
            android.widget.Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(false);
        }

        @Override // android.widget.Editor.CursorController
        public void show() {
            getHandle().show();
            long uptimeMillis = android.os.SystemClock.uptimeMillis() - android.widget.TextView.sLastCutCopyOrTextChangedTime;
            if (android.widget.Editor.this.mInsertionActionModeRunnable != null && (this.mIsDraggingCursor || android.widget.Editor.this.mTouchState.isMultiTap() || android.widget.Editor.this.isCursorInsideEasyCorrectionSpan())) {
                android.widget.Editor.this.mTextView.removeCallbacks(android.widget.Editor.this.mInsertionActionModeRunnable);
            }
            if (!this.mIsDraggingCursor && !android.widget.Editor.this.mTouchState.isMultiTap() && !android.widget.Editor.this.isCursorInsideEasyCorrectionSpan() && uptimeMillis < 15000 && android.widget.Editor.this.mTextActionMode == null) {
                if (android.widget.Editor.this.mInsertionActionModeRunnable == null) {
                    android.widget.Editor.this.mInsertionActionModeRunnable = new java.lang.Runnable() { // from class: android.widget.Editor.InsertionPointCursorController.1
                        @Override // java.lang.Runnable
                        public void run() {
                            android.widget.Editor.this.startInsertionActionMode();
                        }
                    };
                }
                android.widget.Editor.this.mTextView.postDelayed(android.widget.Editor.this.mInsertionActionModeRunnable, android.view.ViewConfiguration.getDoubleTapTimeout() + 1);
            }
            if (!this.mIsDraggingCursor) {
                getHandle().hideAfterDelay();
            }
            if (android.widget.Editor.this.mSelectionModifierCursorController != null) {
                android.widget.Editor.this.mSelectionModifierCursorController.hide();
            }
        }

        @Override // android.widget.Editor.CursorController
        public void hide() {
            if (this.mHandle != null) {
                this.mHandle.hide();
            }
        }

        @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
        public void onTouchModeChanged(boolean z) {
            if (!z) {
                hide();
            }
        }

        public android.widget.Editor.InsertionHandleView getHandle() {
            if (this.mHandle == null) {
                android.widget.Editor.this.loadHandleDrawables(false);
                this.mHandle = android.widget.Editor.this.new InsertionHandleView(android.widget.Editor.this.mSelectHandleCenter);
            }
            return this.mHandle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadHandleDrawable() {
            if (this.mHandle == null) {
                return;
            }
            this.mHandle.setDrawables(android.widget.Editor.this.mSelectHandleCenter, android.widget.Editor.this.mSelectHandleCenter);
        }

        @Override // android.widget.Editor.CursorController
        public void onDetached() {
            android.widget.Editor.this.mTextView.getViewTreeObserver().removeOnTouchModeChangeListener(this);
            if (this.mHandle != null) {
                this.mHandle.onDetached();
            }
        }

        @Override // android.widget.Editor.CursorController
        public boolean isCursorBeingModified() {
            return this.mIsDraggingCursor || (this.mHandle != null && this.mHandle.isDragging());
        }

        @Override // android.widget.Editor.CursorController
        public boolean isActive() {
            return this.mHandle != null && this.mHandle.isShowing();
        }

        public void invalidateHandle() {
            if (this.mHandle != null) {
                this.mHandle.invalidate();
            }
        }
    }

    public class SelectionModifierCursorController implements android.widget.Editor.CursorController {
        private static final int DRAG_ACCELERATOR_MODE_CHARACTER = 1;
        private static final int DRAG_ACCELERATOR_MODE_INACTIVE = 0;
        private static final int DRAG_ACCELERATOR_MODE_PARAGRAPH = 3;
        private static final int DRAG_ACCELERATOR_MODE_WORD = 2;
        private android.widget.Editor.SelectionHandleView mEndHandle;
        private boolean mGestureStayedInTapRegion;
        private boolean mHaventMovedEnoughToStartDrag;
        private int mMaxTouchOffset;
        private int mMinTouchOffset;
        private android.widget.Editor.SelectionHandleView mStartHandle;
        private int mStartOffset = -1;
        private int mLineSelectionIsOn = -1;
        private boolean mSwitchedLines = false;
        private int mDragAcceleratorMode = 0;

        SelectionModifierCursorController() {
            resetTouchOffsets();
        }

        @Override // android.widget.Editor.CursorController
        public void show() {
            if (android.widget.Editor.this.mTextView.isInBatchEditMode()) {
                return;
            }
            android.widget.Editor.this.loadHandleDrawables(false);
            initHandles();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initHandles() {
            if (this.mStartHandle == null) {
                this.mStartHandle = android.widget.Editor.this.new SelectionHandleView(android.widget.Editor.this.mSelectHandleLeft, android.widget.Editor.this.mSelectHandleRight, com.android.internal.R.id.selection_start_handle, 0);
            }
            if (this.mEndHandle == null) {
                this.mEndHandle = android.widget.Editor.this.new SelectionHandleView(android.widget.Editor.this.mSelectHandleRight, android.widget.Editor.this.mSelectHandleLeft, com.android.internal.R.id.selection_end_handle, 1);
            }
            this.mStartHandle.show();
            this.mEndHandle.show();
            android.widget.Editor.this.hideInsertionPointCursorController();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadHandleDrawables() {
            if (this.mStartHandle == null) {
                return;
            }
            this.mStartHandle.setDrawables(android.widget.Editor.this.mSelectHandleLeft, android.widget.Editor.this.mSelectHandleRight);
            this.mEndHandle.setDrawables(android.widget.Editor.this.mSelectHandleRight, android.widget.Editor.this.mSelectHandleLeft);
        }

        @Override // android.widget.Editor.CursorController
        public void hide() {
            if (this.mStartHandle != null) {
                this.mStartHandle.hide();
            }
            if (this.mEndHandle != null) {
                this.mEndHandle.hide();
            }
        }

        public void enterDrag(int i) {
            show();
            this.mDragAcceleratorMode = i;
            this.mStartOffset = android.widget.Editor.this.mTextView.getOffsetForPosition(android.widget.Editor.this.mTouchState.getLastDownX(), android.widget.Editor.this.mTouchState.getLastDownY());
            this.mLineSelectionIsOn = android.widget.Editor.this.mTextView.getLineAtCoordinate(android.widget.Editor.this.mTouchState.getLastDownY());
            hide();
            android.widget.Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(true);
            android.widget.Editor.this.mTextView.cancelLongPress();
        }

        public void onTouchEvent(android.view.MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            boolean isFromSource = motionEvent.isFromSource(8194);
            switch (motionEvent.getActionMasked()) {
                case 0:
                    if (android.widget.Editor.this.extractedTextModeWillBeStarted()) {
                        hide();
                        break;
                    } else {
                        int offsetForPosition = android.widget.Editor.this.mTextView.getOffsetForPosition(x, y);
                        this.mMaxTouchOffset = offsetForPosition;
                        this.mMinTouchOffset = offsetForPosition;
                        if (this.mGestureStayedInTapRegion && android.widget.Editor.this.mTouchState.isMultiTapInSameArea() && (isFromSource || android.widget.Editor.this.isPositionOnText(x, y) || android.widget.Editor.this.mTouchState.isOnHandle())) {
                            if (android.widget.Editor.this.mTouchState.isDoubleTap()) {
                                android.widget.Editor.this.selectCurrentWordAndStartDrag();
                            } else if (android.widget.Editor.this.mTouchState.isTripleClick()) {
                                selectCurrentParagraphAndStartDrag();
                            }
                            android.widget.Editor.this.mDiscardNextActionUp = true;
                        }
                        this.mGestureStayedInTapRegion = true;
                        this.mHaventMovedEnoughToStartDrag = true;
                        break;
                    }
                    break;
                case 1:
                    if (this.mEndHandle != null) {
                        this.mEndHandle.dismissMagnifier();
                    }
                    if (isDragAcceleratorActive()) {
                        updateSelection(motionEvent);
                        android.widget.Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(false);
                        resetDragAcceleratorState();
                        if (android.widget.Editor.this.mTextView.hasSelection()) {
                            android.widget.Editor.this.startSelectionActionModeAsync(this.mHaventMovedEnoughToStartDrag);
                            break;
                        }
                    }
                    break;
                case 2:
                    if (this.mGestureStayedInTapRegion) {
                        this.mGestureStayedInTapRegion = android.widget.EditorTouchState.isDistanceWithin(android.widget.Editor.this.mTouchState.getLastDownX(), android.widget.Editor.this.mTouchState.getLastDownY(), x, y, android.view.ViewConfiguration.get(android.widget.Editor.this.mTextView.getContext()).getScaledDoubleTapTouchSlop());
                    }
                    if (this.mHaventMovedEnoughToStartDrag) {
                        this.mHaventMovedEnoughToStartDrag = !android.widget.Editor.this.mTouchState.isMovedEnoughForDrag();
                    }
                    if (isFromSource && !isDragAcceleratorActive()) {
                        int offsetForPosition2 = android.widget.Editor.this.mTextView.getOffsetForPosition(x, y);
                        if (android.widget.Editor.this.mTextView.hasSelection() && ((!this.mHaventMovedEnoughToStartDrag || this.mStartOffset != offsetForPosition2) && offsetForPosition2 >= android.widget.Editor.this.mTextView.getSelectionStart() && offsetForPosition2 <= android.widget.Editor.this.mTextView.getSelectionEnd())) {
                            android.widget.Editor.this.startDragAndDrop();
                            break;
                        } else if (this.mStartOffset != offsetForPosition2) {
                            android.widget.Editor.this.lambda$startActionModeInternal$0();
                            enterDrag(1);
                            android.widget.Editor.this.mDiscardNextActionUp = true;
                            this.mHaventMovedEnoughToStartDrag = false;
                        }
                    }
                    if (this.mStartHandle == null || !this.mStartHandle.isShowing()) {
                        updateSelection(motionEvent);
                        if (android.widget.Editor.this.mTextView.hasSelection() && this.mEndHandle != null && isDragAcceleratorActive()) {
                            this.mEndHandle.updateMagnifier(motionEvent);
                            break;
                        }
                    }
                    break;
                case 5:
                case 6:
                    if (android.widget.Editor.this.mTextView.getContext().getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)) {
                        updateMinAndMaxOffsets(motionEvent);
                        break;
                    }
                    break;
            }
        }

        private void updateSelection(android.view.MotionEvent motionEvent) {
            if (android.widget.Editor.this.mTextView.getLayout() != null) {
                switch (this.mDragAcceleratorMode) {
                    case 1:
                        updateCharacterBasedSelection(motionEvent);
                        break;
                    case 2:
                        updateWordBasedSelection(motionEvent);
                        break;
                    case 3:
                        updateParagraphBasedSelection(motionEvent);
                        break;
                }
            }
        }

        private boolean selectCurrentParagraphAndStartDrag() {
            if (android.widget.Editor.this.mInsertionActionModeRunnable != null) {
                android.widget.Editor.this.mTextView.removeCallbacks(android.widget.Editor.this.mInsertionActionModeRunnable);
            }
            android.widget.Editor.this.lambda$startActionModeInternal$0();
            if (!android.widget.Editor.this.selectCurrentParagraph()) {
                return false;
            }
            enterDrag(3);
            return true;
        }

        private void updateCharacterBasedSelection(android.view.MotionEvent motionEvent) {
            updateSelectionInternal(this.mStartOffset, android.widget.Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY()), motionEvent.isFromSource(4098));
        }

        private void updateWordBasedSelection(android.view.MotionEvent motionEvent) {
            int currentLineAdjustedForSlop;
            float f;
            int wordStart;
            int wordEnd;
            if (this.mHaventMovedEnoughToStartDrag) {
                return;
            }
            boolean isFromSource = motionEvent.isFromSource(8194);
            android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(android.widget.Editor.this.mTextView.getContext());
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (isFromSource) {
                currentLineAdjustedForSlop = android.widget.Editor.this.mTextView.getLineAtCoordinate(y);
            } else {
                if (this.mSwitchedLines) {
                    int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
                    if (this.mStartHandle != null) {
                        f = this.mStartHandle.getIdealVerticalOffset();
                    } else {
                        f = scaledTouchSlop;
                    }
                    y -= f;
                }
                currentLineAdjustedForSlop = android.widget.Editor.this.getCurrentLineAdjustedForSlop(android.widget.Editor.this.mTextView.getLayout(), this.mLineSelectionIsOn, y);
                if (!this.mSwitchedLines && currentLineAdjustedForSlop != this.mLineSelectionIsOn) {
                    this.mSwitchedLines = true;
                    return;
                }
            }
            int offsetAtCoordinate = android.widget.Editor.this.mTextView.getOffsetAtCoordinate(currentLineAdjustedForSlop, x);
            if (this.mStartOffset < offsetAtCoordinate) {
                wordStart = android.widget.Editor.this.getWordEnd(offsetAtCoordinate);
                wordEnd = android.widget.Editor.this.getWordStart(this.mStartOffset);
            } else {
                wordStart = android.widget.Editor.this.getWordStart(offsetAtCoordinate);
                wordEnd = android.widget.Editor.this.getWordEnd(this.mStartOffset);
                if (wordEnd == wordStart) {
                    wordStart = android.widget.Editor.this.getNextCursorOffset(wordStart, false);
                }
            }
            this.mLineSelectionIsOn = currentLineAdjustedForSlop;
            updateSelectionInternal(wordEnd, wordStart, motionEvent.isFromSource(4098));
        }

        private void updateParagraphBasedSelection(android.view.MotionEvent motionEvent) {
            int offsetForPosition = android.widget.Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
            long paragraphsRange = android.widget.Editor.this.getParagraphsRange(java.lang.Math.min(offsetForPosition, this.mStartOffset), java.lang.Math.max(offsetForPosition, this.mStartOffset));
            updateSelectionInternal(android.text.TextUtils.unpackRangeStartFromLong(paragraphsRange), android.text.TextUtils.unpackRangeEndFromLong(paragraphsRange), motionEvent.isFromSource(4098));
        }

        private void updateSelectionInternal(int i, int i2, boolean z) {
            boolean z2 = z && android.widget.Editor.this.mHapticTextHandleEnabled && !(android.widget.Editor.this.mTextView.getSelectionStart() == i && android.widget.Editor.this.mTextView.getSelectionEnd() == i2);
            android.text.Selection.setSelection((android.text.Spannable) android.widget.Editor.this.mTextView.getText(), i, i2);
            if (z2) {
                android.widget.Editor.this.mTextView.performHapticFeedback(9);
            }
        }

        private void updateMinAndMaxOffsets(android.view.MotionEvent motionEvent) {
            int pointerCount = motionEvent.getPointerCount();
            for (int i = 0; i < pointerCount; i++) {
                int offsetForPosition = android.widget.Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(i), motionEvent.getY(i));
                if (offsetForPosition < this.mMinTouchOffset) {
                    this.mMinTouchOffset = offsetForPosition;
                }
                if (offsetForPosition > this.mMaxTouchOffset) {
                    this.mMaxTouchOffset = offsetForPosition;
                }
            }
        }

        public int getMinTouchOffset() {
            return this.mMinTouchOffset;
        }

        public int getMaxTouchOffset() {
            return this.mMaxTouchOffset;
        }

        public void resetTouchOffsets() {
            this.mMaxTouchOffset = -1;
            this.mMinTouchOffset = -1;
            resetDragAcceleratorState();
        }

        private void resetDragAcceleratorState() {
            this.mStartOffset = -1;
            this.mDragAcceleratorMode = 0;
            this.mSwitchedLines = false;
            int selectionStart = android.widget.Editor.this.mTextView.getSelectionStart();
            int selectionEnd = android.widget.Editor.this.mTextView.getSelectionEnd();
            if (selectionStart < 0 || selectionEnd < 0) {
                android.text.Selection.removeSelection((android.text.Spannable) android.widget.Editor.this.mTextView.getText());
            } else if (selectionStart > selectionEnd) {
                android.text.Selection.setSelection((android.text.Spannable) android.widget.Editor.this.mTextView.getText(), selectionEnd, selectionStart);
            }
        }

        public boolean isSelectionStartDragged() {
            return this.mStartHandle != null && this.mStartHandle.isDragging();
        }

        @Override // android.widget.Editor.CursorController
        public boolean isCursorBeingModified() {
            return isDragAcceleratorActive() || isSelectionStartDragged() || (this.mEndHandle != null && this.mEndHandle.isDragging());
        }

        public boolean isDragAcceleratorActive() {
            return this.mDragAcceleratorMode != 0;
        }

        @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
        public void onTouchModeChanged(boolean z) {
            if (!z) {
                hide();
            }
        }

        @Override // android.widget.Editor.CursorController
        public void onDetached() {
            android.widget.Editor.this.mTextView.getViewTreeObserver().removeOnTouchModeChangeListener(this);
            if (this.mStartHandle != null) {
                this.mStartHandle.onDetached();
            }
            if (this.mEndHandle != null) {
                this.mEndHandle.onDetached();
            }
        }

        @Override // android.widget.Editor.CursorController
        public boolean isActive() {
            return this.mStartHandle != null && this.mStartHandle.isShowing();
        }

        public void invalidateHandles() {
            if (this.mStartHandle != null) {
                this.mStartHandle.invalidate();
            }
            if (this.mEndHandle != null) {
                this.mEndHandle.invalidate();
            }
        }
    }

    void loadHandleDrawables(boolean z) {
        if (this.mSelectHandleCenter == null || z) {
            this.mSelectHandleCenter = this.mTextView.getTextSelectHandle();
            if (hasInsertionController()) {
                getInsertionController().reloadHandleDrawable();
            }
        }
        if (this.mSelectHandleLeft == null || this.mSelectHandleRight == null || z) {
            this.mSelectHandleLeft = this.mTextView.getTextSelectHandleLeft();
            this.mSelectHandleRight = this.mTextView.getTextSelectHandleRight();
            if (hasSelectionController()) {
                getSelectionController().reloadHandleDrawables();
            }
        }
    }

    private class CorrectionHighlighter {
        private static final int FADE_OUT_DURATION = 400;
        private int mEnd;
        private long mFadingStartTime;
        private int mStart;
        private android.graphics.RectF mTempRectF;
        private final android.graphics.Path mPath = new android.graphics.Path();
        private final android.graphics.Paint mPaint = new android.graphics.Paint(1);

        public CorrectionHighlighter() {
            this.mPaint.setCompatibilityScaling(android.widget.Editor.this.mTextView.getResources().getCompatibilityInfo().applicationScale);
            this.mPaint.setStyle(android.graphics.Paint.Style.FILL);
        }

        public void highlight(android.view.inputmethod.CorrectionInfo correctionInfo) {
            this.mStart = correctionInfo.getOffset();
            this.mEnd = this.mStart + correctionInfo.getNewText().length();
            this.mFadingStartTime = android.os.SystemClock.uptimeMillis();
            if (this.mStart < 0 || this.mEnd < 0) {
                stopAnimation();
            }
        }

        public void draw(android.graphics.Canvas canvas, int i) {
            if (updatePath() && updatePaint()) {
                if (i != 0) {
                    canvas.translate(0.0f, i);
                }
                canvas.drawPath(this.mPath, this.mPaint);
                if (i != 0) {
                    canvas.translate(0.0f, -i);
                }
                invalidate(true);
                return;
            }
            stopAnimation();
            invalidate(false);
        }

        private boolean updatePaint() {
            long uptimeMillis = android.os.SystemClock.uptimeMillis() - this.mFadingStartTime;
            if (uptimeMillis > 400) {
                return false;
            }
            this.mPaint.setColor((android.widget.Editor.this.mTextView.mHighlightColor & 16777215) + (((int) (android.graphics.Color.alpha(android.widget.Editor.this.mTextView.mHighlightColor) * (1.0f - (uptimeMillis / 400.0f)))) << 24));
            return true;
        }

        private boolean updatePath() {
            android.text.Layout layout = android.widget.Editor.this.mTextView.getLayout();
            if (layout == null) {
                return false;
            }
            int length = android.widget.Editor.this.mTextView.getText().length();
            int min = java.lang.Math.min(length, this.mStart);
            int min2 = java.lang.Math.min(length, this.mEnd);
            this.mPath.reset();
            layout.getSelectionPath(android.widget.Editor.this.mTextView.originalToTransformed(min, 0), android.widget.Editor.this.mTextView.originalToTransformed(min2, 0), this.mPath);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void invalidate(boolean z) {
            if (android.widget.Editor.this.mTextView.getLayout() == null) {
                return;
            }
            if (this.mTempRectF == null) {
                this.mTempRectF = new android.graphics.RectF();
            }
            this.mPath.computeBounds(this.mTempRectF, false);
            int compoundPaddingLeft = android.widget.Editor.this.mTextView.getCompoundPaddingLeft();
            int extendedPaddingTop = android.widget.Editor.this.mTextView.getExtendedPaddingTop() + android.widget.Editor.this.mTextView.getVerticalOffset(true);
            if (z) {
                android.widget.Editor.this.mTextView.postInvalidateOnAnimation(((int) this.mTempRectF.left) + compoundPaddingLeft, ((int) this.mTempRectF.top) + extendedPaddingTop, compoundPaddingLeft + ((int) this.mTempRectF.right), extendedPaddingTop + ((int) this.mTempRectF.bottom));
            } else {
                android.widget.Editor.this.mTextView.postInvalidate((int) this.mTempRectF.left, (int) this.mTempRectF.top, (int) this.mTempRectF.right, (int) this.mTempRectF.bottom);
            }
        }

        private void stopAnimation() {
            android.widget.Editor.this.mCorrectionHighlighter = null;
        }
    }

    private static class ErrorPopup extends android.widget.PopupWindow {
        private boolean mAbove;
        private int mPopupInlineErrorAboveBackgroundId;
        private int mPopupInlineErrorBackgroundId;
        private final android.widget.TextView mView;

        ErrorPopup(android.widget.TextView textView, int i, int i2) {
            super(textView, i, i2);
            this.mAbove = false;
            this.mPopupInlineErrorBackgroundId = 0;
            this.mPopupInlineErrorAboveBackgroundId = 0;
            this.mView = textView;
            this.mPopupInlineErrorBackgroundId = getResourceId(this.mPopupInlineErrorBackgroundId, 313);
            this.mView.setBackgroundResource(this.mPopupInlineErrorBackgroundId);
        }

        void fixDirection(boolean z) {
            this.mAbove = z;
            if (z) {
                this.mPopupInlineErrorAboveBackgroundId = getResourceId(this.mPopupInlineErrorAboveBackgroundId, 312);
            } else {
                this.mPopupInlineErrorBackgroundId = getResourceId(this.mPopupInlineErrorBackgroundId, 313);
            }
            this.mView.setBackgroundResource(z ? this.mPopupInlineErrorAboveBackgroundId : this.mPopupInlineErrorBackgroundId);
        }

        private int getResourceId(int i, int i2) {
            if (i == 0) {
                android.content.res.TypedArray obtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(android.R.styleable.Theme);
                int resourceId = obtainStyledAttributes.getResourceId(i2, 0);
                obtainStyledAttributes.recycle();
                return resourceId;
            }
            return i;
        }

        @Override // android.widget.PopupWindow
        public void update(int i, int i2, int i3, int i4, boolean z) {
            super.update(i, i2, i3, i4, z);
            boolean isAboveAnchor = isAboveAnchor();
            if (isAboveAnchor != this.mAbove) {
                fixDirection(isAboveAnchor);
            }
        }
    }

    static class InputContentType {
        boolean enterDown;
        android.os.Bundle extras;
        int imeActionId;
        java.lang.CharSequence imeActionLabel;
        android.os.LocaleList imeHintLocales;
        int imeOptions = 0;
        android.widget.TextView.OnEditorActionListener onEditorActionListener;
        java.lang.String privateImeOptions;

        InputContentType() {
        }
    }

    static class InputMethodState {
        int mBatchEditNesting;
        int mChangedDelta;
        int mChangedEnd;
        int mChangedStart;
        boolean mContentChanged;
        boolean mCursorChanged;
        final android.view.inputmethod.ExtractedText mExtractedText = new android.view.inputmethod.ExtractedText();
        android.view.inputmethod.ExtractedTextRequest mExtractedTextRequest;
        boolean mSelectionModeChanged;
        int mUpdateCursorAnchorInfoFilter;
        int mUpdateCursorAnchorInfoMode;

        InputMethodState() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidRange(java.lang.CharSequence charSequence, int i, int i2) {
        return i >= 0 && i <= i2 && i2 <= charSequence.length();
    }

    public static class UndoInputFilter implements android.text.InputFilter {
        private static final int MERGE_EDIT_MODE_FORCE_MERGE = 0;
        private static final int MERGE_EDIT_MODE_NEVER_MERGE = 1;
        private static final int MERGE_EDIT_MODE_NORMAL = 2;
        private final android.widget.Editor mEditor;
        private boolean mExpanding;
        private boolean mHasComposition;
        private boolean mIsUserEdit;
        private boolean mPreviousOperationWasInSameBatchEdit;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        private @interface MergeMode {
        }

        public UndoInputFilter(android.widget.Editor editor) {
            this.mEditor = editor;
        }

        public void saveInstanceState(android.os.Parcel parcel) {
            parcel.writeInt(this.mIsUserEdit ? 1 : 0);
            parcel.writeInt(this.mHasComposition ? 1 : 0);
            parcel.writeInt(this.mExpanding ? 1 : 0);
            parcel.writeInt(this.mPreviousOperationWasInSameBatchEdit ? 1 : 0);
        }

        public void restoreInstanceState(android.os.Parcel parcel) {
            this.mIsUserEdit = parcel.readInt() != 0;
            this.mHasComposition = parcel.readInt() != 0;
            this.mExpanding = parcel.readInt() != 0;
            this.mPreviousOperationWasInSameBatchEdit = parcel.readInt() != 0;
        }

        public void beginBatchEdit() {
            this.mIsUserEdit = true;
        }

        public void endBatchEdit() {
            this.mIsUserEdit = false;
            this.mPreviousOperationWasInSameBatchEdit = false;
        }

        @Override // android.text.InputFilter
        public java.lang.CharSequence filter(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4) {
            boolean z;
            if (!canUndoEdit(charSequence, i, i2, spanned, i3, i4)) {
                return null;
            }
            boolean z2 = this.mHasComposition;
            this.mHasComposition = isComposition(charSequence);
            boolean z3 = this.mExpanding;
            int i5 = i2 - i;
            int i6 = i4 - i3;
            if (i5 != i6) {
                this.mExpanding = i5 > i6;
                if (z2 && this.mExpanding != z3) {
                    z = true;
                    handleEdit(charSequence, i, i2, spanned, i3, i4, z);
                    return null;
                }
            }
            z = false;
            handleEdit(charSequence, i, i2, spanned, i3, i4, z);
            return null;
        }

        void freezeLastEdit() {
            this.mEditor.mUndoManager.beginUpdate("Edit text");
            android.widget.Editor.EditOperation lastEdit = getLastEdit();
            if (lastEdit != null) {
                lastEdit.mFrozen = true;
            }
            this.mEditor.mUndoManager.endUpdate();
        }

        private void handleEdit(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4, boolean z) {
            int i5;
            if (isInTextWatcher() || this.mPreviousOperationWasInSameBatchEdit) {
                i5 = 0;
            } else if (z) {
                i5 = 1;
            } else {
                i5 = 2;
            }
            java.lang.String substring = android.text.TextUtils.substring(charSequence, i, i2);
            android.widget.Editor.EditOperation editOperation = new android.widget.Editor.EditOperation(this.mEditor, android.text.TextUtils.substring(spanned, i3, i4), i3, substring, this.mHasComposition);
            if (this.mHasComposition && android.text.TextUtils.equals(editOperation.mNewText, editOperation.mOldText)) {
                return;
            }
            recordEdit(editOperation, i5);
        }

        private android.widget.Editor.EditOperation getLastEdit() {
            return (android.widget.Editor.EditOperation) this.mEditor.mUndoManager.getLastOperation(android.widget.Editor.EditOperation.class, this.mEditor.mUndoOwner, 1);
        }

        private void recordEdit(android.widget.Editor.EditOperation editOperation, int i) {
            android.content.UndoManager undoManager = this.mEditor.mUndoManager;
            undoManager.beginUpdate("Edit text");
            android.widget.Editor.EditOperation lastEdit = getLastEdit();
            if (lastEdit == null) {
                undoManager.addOperation(editOperation, 0);
            } else if (i == 0) {
                lastEdit.forceMergeWith(editOperation);
            } else if (!this.mIsUserEdit) {
                undoManager.commitState(this.mEditor.mUndoOwner);
                undoManager.addOperation(editOperation, 0);
            } else if (i != 2 || !lastEdit.mergeWith(editOperation)) {
                undoManager.commitState(this.mEditor.mUndoOwner);
                undoManager.addOperation(editOperation, 0);
            }
            this.mPreviousOperationWasInSameBatchEdit = this.mIsUserEdit;
            undoManager.endUpdate();
        }

        private boolean canUndoEdit(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4) {
            if (this.mEditor.mAllowUndo && !this.mEditor.mUndoManager.isInUndo() && android.widget.Editor.isValidRange(charSequence, i, i2) && android.widget.Editor.isValidRange(spanned, i3, i4)) {
                return (i == i2 && i3 == i4) ? false : true;
            }
            return false;
        }

        private static boolean isComposition(java.lang.CharSequence charSequence) {
            if (!(charSequence instanceof android.text.Spannable)) {
                return false;
            }
            android.text.Spannable spannable = (android.text.Spannable) charSequence;
            return com.android.internal.inputmethod.EditableInputConnection.getComposingSpanStart(spannable) < com.android.internal.inputmethod.EditableInputConnection.getComposingSpanEnd(spannable);
        }

        private boolean isInTextWatcher() {
            java.lang.CharSequence text = this.mEditor.mTextView.getText();
            return (text instanceof android.text.SpannableStringBuilder) && ((android.text.SpannableStringBuilder) text).getTextWatcherDepth() > 0;
        }
    }

    public static class EditOperation extends android.content.UndoOperation<android.widget.Editor> {
        public static final android.os.Parcelable.ClassLoaderCreator<android.widget.Editor.EditOperation> CREATOR = new android.os.Parcelable.ClassLoaderCreator<android.widget.Editor.EditOperation>() { // from class: android.widget.Editor.EditOperation.1
            @Override // android.os.Parcelable.Creator
            public android.widget.Editor.EditOperation createFromParcel(android.os.Parcel parcel) {
                return new android.widget.Editor.EditOperation(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public android.widget.Editor.EditOperation createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
                return new android.widget.Editor.EditOperation(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public android.widget.Editor.EditOperation[] newArray(int i) {
                return new android.widget.Editor.EditOperation[i];
            }
        };
        private static final int TYPE_DELETE = 1;
        private static final int TYPE_INSERT = 0;
        private static final int TYPE_REPLACE = 2;
        private boolean mFrozen;
        private boolean mIsComposition;
        private int mNewCursorPos;
        private java.lang.String mNewText;
        private int mOldCursorPos;
        private java.lang.String mOldText;
        private int mStart;
        private int mType;

        public EditOperation(android.widget.Editor editor, java.lang.String str, int i, java.lang.String str2, boolean z) {
            super(editor.mUndoOwner);
            this.mOldText = str;
            this.mNewText = str2;
            if (this.mNewText.length() > 0 && this.mOldText.length() == 0) {
                this.mType = 0;
            } else if (this.mNewText.length() == 0 && this.mOldText.length() > 0) {
                this.mType = 1;
            } else {
                this.mType = 2;
            }
            this.mStart = i;
            this.mOldCursorPos = editor.mTextView.getSelectionStart();
            this.mNewCursorPos = i + this.mNewText.length();
            this.mIsComposition = z;
        }

        public EditOperation(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mType = parcel.readInt();
            this.mOldText = parcel.readString();
            this.mNewText = parcel.readString();
            this.mStart = parcel.readInt();
            this.mOldCursorPos = parcel.readInt();
            this.mNewCursorPos = parcel.readInt();
            this.mFrozen = parcel.readInt() == 1;
            this.mIsComposition = parcel.readInt() == 1;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeString(this.mOldText);
            parcel.writeString(this.mNewText);
            parcel.writeInt(this.mStart);
            parcel.writeInt(this.mOldCursorPos);
            parcel.writeInt(this.mNewCursorPos);
            parcel.writeInt(this.mFrozen ? 1 : 0);
            parcel.writeInt(this.mIsComposition ? 1 : 0);
        }

        private int getNewTextEnd() {
            return this.mStart + this.mNewText.length();
        }

        private int getOldTextEnd() {
            return this.mStart + this.mOldText.length();
        }

        @Override // android.content.UndoOperation
        public void commit() {
        }

        @Override // android.content.UndoOperation
        public void undo() {
            modifyText((android.text.Editable) getOwnerData().mTextView.getText(), this.mStart, getNewTextEnd(), this.mOldText, this.mStart, this.mOldCursorPos);
        }

        @Override // android.content.UndoOperation
        public void redo() {
            modifyText((android.text.Editable) getOwnerData().mTextView.getText(), this.mStart, getOldTextEnd(), this.mNewText, this.mStart, this.mNewCursorPos);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean mergeWith(android.widget.Editor.EditOperation editOperation) {
            if (this.mFrozen) {
                return false;
            }
            switch (this.mType) {
            }
            return false;
        }

        private boolean mergeInsertWith(android.widget.Editor.EditOperation editOperation) {
            if (editOperation.mType == 0) {
                if (getNewTextEnd() != editOperation.mStart) {
                    return false;
                }
                this.mNewText += editOperation.mNewText;
                this.mNewCursorPos = editOperation.mNewCursorPos;
                this.mFrozen = editOperation.mFrozen;
                this.mIsComposition = editOperation.mIsComposition;
                return true;
            }
            if (!this.mIsComposition || editOperation.mType != 2 || this.mStart > editOperation.mStart || getNewTextEnd() < editOperation.getOldTextEnd()) {
                return false;
            }
            this.mNewText = this.mNewText.substring(0, editOperation.mStart - this.mStart) + editOperation.mNewText + this.mNewText.substring(editOperation.getOldTextEnd() - this.mStart, this.mNewText.length());
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
            return true;
        }

        private boolean mergeDeleteWith(android.widget.Editor.EditOperation editOperation) {
            if (editOperation.mType != 1 || this.mStart != editOperation.getOldTextEnd()) {
                return false;
            }
            this.mStart = editOperation.mStart;
            this.mOldText = editOperation.mOldText + this.mOldText;
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
            return true;
        }

        private boolean mergeReplaceWith(android.widget.Editor.EditOperation editOperation) {
            if (editOperation.mType == 0 && getNewTextEnd() == editOperation.mStart) {
                this.mNewText += editOperation.mNewText;
                this.mNewCursorPos = editOperation.mNewCursorPos;
                return true;
            }
            if (!this.mIsComposition) {
                return false;
            }
            if (editOperation.mType == 1 && this.mStart <= editOperation.mStart && getNewTextEnd() >= editOperation.getOldTextEnd()) {
                this.mNewText = this.mNewText.substring(0, editOperation.mStart - this.mStart) + this.mNewText.substring(editOperation.getOldTextEnd() - this.mStart, this.mNewText.length());
                if (this.mNewText.isEmpty()) {
                    this.mType = 1;
                }
                this.mNewCursorPos = editOperation.mNewCursorPos;
                this.mIsComposition = editOperation.mIsComposition;
                return true;
            }
            if (editOperation.mType != 2 || this.mStart != editOperation.mStart || !android.text.TextUtils.equals(this.mNewText, editOperation.mOldText)) {
                return false;
            }
            this.mNewText = editOperation.mNewText;
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
            return true;
        }

        public void forceMergeWith(android.widget.Editor.EditOperation editOperation) {
            if (mergeWith(editOperation)) {
                return;
            }
            android.text.Editable editable = (android.text.Editable) getOwnerData().mTextView.getText();
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(editable.toString());
            modifyText(spannableStringBuilder, this.mStart, getNewTextEnd(), this.mOldText, this.mStart, this.mOldCursorPos);
            android.text.SpannableStringBuilder spannableStringBuilder2 = new android.text.SpannableStringBuilder(editable.toString());
            modifyText(spannableStringBuilder2, editOperation.mStart, editOperation.getOldTextEnd(), editOperation.mNewText, editOperation.mStart, editOperation.mNewCursorPos);
            this.mType = 2;
            this.mNewText = spannableStringBuilder2.toString();
            this.mOldText = spannableStringBuilder.toString();
            this.mStart = 0;
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
        }

        private static void modifyText(android.text.Editable editable, int i, int i2, java.lang.CharSequence charSequence, int i3, int i4) {
            if (android.widget.Editor.isValidRange(editable, i, i2) && i3 <= editable.length() - (i2 - i)) {
                if (i != i2) {
                    editable.delete(i, i2);
                }
                if (charSequence.length() != 0) {
                    editable.insert(i3, charSequence);
                }
            }
            if (i4 >= 0 && i4 <= editable.length()) {
                android.text.Selection.setSelection(editable, i4);
            }
        }

        private java.lang.String getTypeString() {
            switch (this.mType) {
                case 0:
                    return "insert";
                case 1:
                    return "delete";
                case 2:
                    return "replace";
                default:
                    return "";
            }
        }

        public java.lang.String toString() {
            return "[mType=" + getTypeString() + ", mOldText=" + this.mOldText + ", mNewText=" + this.mNewText + ", mStart=" + this.mStart + ", mOldCursorPos=" + this.mOldCursorPos + ", mNewCursorPos=" + this.mNewCursorPos + ", mFrozen=" + this.mFrozen + ", mIsComposition=" + this.mIsComposition + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    static final class ProcessTextIntentActionsHandler {
        private final android.util.SparseArray<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> mAccessibilityActions;
        private final android.util.SparseArray<android.content.Intent> mAccessibilityIntents;
        private final android.content.Context mContext;
        private final android.widget.Editor mEditor;
        private final android.content.pm.PackageManager mPackageManager;
        private final java.lang.String mPackageName;
        private final java.util.List<android.content.pm.ResolveInfo> mSupportedActivities;
        private final android.widget.TextView mTextView;

        private ProcessTextIntentActionsHandler(android.widget.Editor editor) {
            this.mAccessibilityIntents = new android.util.SparseArray<>();
            this.mAccessibilityActions = new android.util.SparseArray<>();
            this.mSupportedActivities = new java.util.ArrayList();
            this.mEditor = (android.widget.Editor) java.util.Objects.requireNonNull(editor);
            this.mTextView = (android.widget.TextView) java.util.Objects.requireNonNull(this.mEditor.mTextView);
            this.mContext = (android.content.Context) java.util.Objects.requireNonNull(this.mTextView.getContext());
            this.mPackageManager = (android.content.pm.PackageManager) java.util.Objects.requireNonNull(this.mContext.getPackageManager());
            this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(this.mContext.getPackageName());
        }

        public void onInitializeMenu(android.view.Menu menu) {
            loadSupportedActivities();
            int size = this.mSupportedActivities.size();
            for (int i = 0; i < size; i++) {
                android.content.pm.ResolveInfo resolveInfo = this.mSupportedActivities.get(i);
                menu.add(0, 0, i + 100, getLabel(resolveInfo)).setIntent(createProcessTextIntentForResolveInfo(resolveInfo)).setShowAsAction(0);
            }
        }

        public boolean performMenuItemAction(android.view.MenuItem menuItem) {
            return fireIntent(menuItem.getIntent());
        }

        public void initializeAccessibilityActions() {
            this.mAccessibilityIntents.clear();
            this.mAccessibilityActions.clear();
            loadSupportedActivities();
            int i = 0;
            for (android.content.pm.ResolveInfo resolveInfo : this.mSupportedActivities) {
                int i2 = i + 1;
                int i3 = i + 268435712;
                this.mAccessibilityActions.put(i3, new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(i3, getLabel(resolveInfo)));
                this.mAccessibilityIntents.put(i3, createProcessTextIntentForResolveInfo(resolveInfo));
                i = i2;
            }
        }

        public void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            for (int i = 0; i < this.mAccessibilityActions.size(); i++) {
                accessibilityNodeInfo.addAction(this.mAccessibilityActions.valueAt(i));
            }
        }

        public boolean performAccessibilityAction(int i) {
            return fireIntent(this.mAccessibilityIntents.get(i));
        }

        private boolean fireIntent(android.content.Intent intent) {
            if (intent != null && android.content.Intent.ACTION_PROCESS_TEXT.equals(intent.getAction())) {
                intent.putExtra(android.content.Intent.EXTRA_PROCESS_TEXT, (java.lang.String) android.text.TextUtils.trimToParcelableSize(this.mTextView.getSelectedText()));
                this.mEditor.mPreserveSelection = true;
                this.mTextView.startActivityForResult(intent, 100);
                return true;
            }
            return false;
        }

        private void loadSupportedActivities() {
            this.mSupportedActivities.clear();
            if (!this.mContext.canStartActivityForResult()) {
                return;
            }
            for (android.content.pm.ResolveInfo resolveInfo : this.mTextView.getContext().getPackageManager().queryIntentActivities(createProcessTextIntent(), 0)) {
                if (isSupportedActivity(resolveInfo)) {
                    this.mSupportedActivities.add(resolveInfo);
                }
            }
        }

        private boolean isSupportedActivity(android.content.pm.ResolveInfo resolveInfo) {
            return this.mPackageName.equals(resolveInfo.activityInfo.packageName) || (resolveInfo.activityInfo.exported && (resolveInfo.activityInfo.permission == null || this.mContext.checkSelfPermission(resolveInfo.activityInfo.permission) == 0));
        }

        private android.content.Intent createProcessTextIntentForResolveInfo(android.content.pm.ResolveInfo resolveInfo) {
            return createProcessTextIntent().putExtra(android.content.Intent.EXTRA_PROCESS_TEXT_READONLY, !this.mTextView.isTextEditable()).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        }

        private android.content.Intent createProcessTextIntent() {
            return new android.content.Intent().setAction(android.content.Intent.ACTION_PROCESS_TEXT).setType("text/plain");
        }

        private java.lang.CharSequence getLabel(android.content.pm.ResolveInfo resolveInfo) {
            return resolveInfo.loadLabel(this.mPackageManager);
        }
    }

    private static final class AccessibilitySmartActions {
        private final android.util.SparseArray<android.util.Pair<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction, android.app.RemoteAction>> mActions;
        private final android.widget.TextView mTextView;

        private AccessibilitySmartActions(android.widget.TextView textView) {
            this.mActions = new android.util.SparseArray<>();
            this.mTextView = (android.widget.TextView) java.util.Objects.requireNonNull(textView);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAction(android.app.RemoteAction remoteAction) {
            int size = this.mActions.size() + 268439552;
            this.mActions.put(size, new android.util.Pair<>(new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(size, remoteAction.getTitle()), remoteAction));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mActions.clear();
        }

        void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            for (int i = 0; i < this.mActions.size(); i++) {
                accessibilityNodeInfo.addAction(this.mActions.valueAt(i).first);
            }
        }

        boolean performAccessibilityAction(int i) {
            android.util.Pair<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction, android.app.RemoteAction> pair = this.mActions.get(i);
            if (pair != null) {
                android.view.textclassifier.TextClassification.createIntentOnClickListener(pair.second.getActionIntent()).onClick(this.mTextView);
                return true;
            }
            return false;
        }
    }

    private static final class InsertModeController {
        private final android.widget.TextView mTextView;
        private boolean mUpdatingTransformationMethod;
        private boolean mIsInsertModeActive = false;
        private android.text.method.InsertModeTransformationMethod mInsertModeTransformationMethod = null;
        private final android.graphics.Paint mHighlightPaint = new android.graphics.Paint();
        private final android.graphics.Path mHighlightPath = new android.graphics.Path();

        InsertModeController(android.widget.TextView textView) {
            this.mTextView = (android.widget.TextView) java.util.Objects.requireNonNull(textView);
            this.mHighlightPaint.setColor(com.android.internal.graphics.ColorUtils.setAlphaComponent(this.mTextView.getTextColors().getDefaultColor(), (int) (android.graphics.Color.alpha(r3) * 0.2f)));
        }

        boolean enterInsertMode(int i) {
            if (this.mIsInsertModeActive) {
                return false;
            }
            android.text.method.TransformationMethod transformationMethod = this.mTextView.getTransformationMethod();
            if (transformationMethod instanceof android.text.method.OffsetMapping) {
                return false;
            }
            this.mInsertModeTransformationMethod = new android.text.method.InsertModeTransformationMethod(i, this.mTextView.isSingleLine(), transformationMethod);
            setTransformationMethod(this.mInsertModeTransformationMethod, true);
            android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), i);
            this.mIsInsertModeActive = true;
            return true;
        }

        void exitInsertMode() {
            exitInsertMode(true);
        }

        void exitInsertMode(boolean z) {
            if (this.mIsInsertModeActive) {
                if (this.mInsertModeTransformationMethod == null || this.mInsertModeTransformationMethod != this.mTextView.getTransformationMethod()) {
                    this.mIsInsertModeActive = false;
                    return;
                }
                int selectionStart = this.mTextView.getSelectionStart();
                int selectionEnd = this.mTextView.getSelectionEnd();
                setTransformationMethod(this.mInsertModeTransformationMethod.getOldTransformationMethod(), z);
                android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
                this.mIsInsertModeActive = false;
            }
        }

        void onDraw(android.graphics.Canvas canvas) {
            android.text.Layout layout;
            if (this.mIsInsertModeActive) {
                java.lang.CharSequence transformed = this.mTextView.getTransformed();
                if (!(transformed instanceof android.text.method.InsertModeTransformationMethod.TransformedText) || (layout = this.mTextView.getLayout()) == null) {
                    return;
                }
                android.text.method.InsertModeTransformationMethod.TransformedText transformedText = (android.text.method.InsertModeTransformationMethod.TransformedText) transformed;
                layout.getSelectionPath(transformedText.getHighlightStart(), transformedText.getHighlightEnd(), this.mHighlightPath);
                canvas.drawPath(this.mHighlightPath, this.mHighlightPaint);
            }
        }

        private void setTransformationMethod(android.text.method.TransformationMethod transformationMethod, boolean z) {
            this.mUpdatingTransformationMethod = true;
            this.mTextView.setTransformationMethodInternal(transformationMethod, z);
            this.mUpdatingTransformationMethod = false;
        }

        void beforeSetText() {
            if (this.mUpdatingTransformationMethod) {
                return;
            }
            exitInsertMode(false);
        }

        void updateTransformationMethod(android.text.method.TransformationMethod transformationMethod) {
            if (!this.mIsInsertModeActive) {
                setTransformationMethod(transformationMethod, true);
                return;
            }
            int selectionStart = this.mTextView.getSelectionStart();
            int selectionEnd = this.mTextView.getSelectionEnd();
            this.mInsertModeTransformationMethod = this.mInsertModeTransformationMethod.update(transformationMethod, this.mTextView.isSingleLine());
            setTransformationMethod(this.mInsertModeTransformationMethod, true);
            android.text.Selection.setSelection((android.text.Spannable) this.mTextView.getText(), selectionStart, selectionEnd);
        }
    }

    boolean enterInsertMode(int i) {
        if (this.mInsertModeController == null) {
            if (this.mTextView == null) {
                return false;
            }
            this.mInsertModeController = new android.widget.Editor.InsertModeController(this.mTextView);
        }
        return this.mInsertModeController.enterInsertMode(i);
    }

    void exitInsertMode() {
        if (this.mInsertModeController == null) {
            return;
        }
        this.mInsertModeController.exitInsertMode();
    }

    void setTransformationMethod(android.text.method.TransformationMethod transformationMethod) {
        if (this.mInsertModeController == null) {
            this.mTextView.setTransformationMethodInternal(transformationMethod, true);
        } else {
            this.mInsertModeController.updateTransformationMethod(transformationMethod);
        }
    }

    void beforeSetText() {
        if (this.mInsertModeController == null) {
            return;
        }
        this.mInsertModeController.beforeSetText();
    }

    void onInitializeSmartActionsAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mA11ySmartActions.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    boolean performSmartActionsAccessibilityAction(int i) {
        return this.mA11ySmartActions.performAccessibilityAction(i);
    }

    static void logCursor(java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        if (str2 != null) {
            android.util.Log.d("Editor", str + ": " + java.lang.String.format(str2, objArr));
        } else {
            android.util.Log.d("Editor", str);
        }
    }
}
