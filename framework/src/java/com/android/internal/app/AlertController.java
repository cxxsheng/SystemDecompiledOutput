package com.android.internal.app;

/* loaded from: classes4.dex */
public class AlertController {
    public static final int MICRO = 1;
    private android.widget.ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private android.widget.Button mButtonNegative;
    private android.os.Message mButtonNegativeMessage;
    private java.lang.CharSequence mButtonNegativeText;
    private android.widget.Button mButtonNeutral;
    private android.os.Message mButtonNeutralMessage;
    private java.lang.CharSequence mButtonNeutralText;
    private int mButtonPanelSideLayout;
    private android.widget.Button mButtonPositive;
    private android.os.Message mButtonPositiveMessage;
    private java.lang.CharSequence mButtonPositiveText;
    private final android.content.Context mContext;
    private android.view.View mCustomTitleView;
    private final android.content.DialogInterface mDialogInterface;
    private boolean mForceInverseBackground;
    private android.os.Handler mHandler;
    private android.graphics.drawable.Drawable mIcon;
    private android.widget.ImageView mIconView;
    private int mListItemLayout;
    private int mListLayout;
    protected android.widget.ListView mListView;
    protected java.lang.CharSequence mMessage;
    private java.lang.Integer mMessageHyphenationFrequency;
    private android.text.method.MovementMethod mMessageMovementMethod;
    protected android.widget.TextView mMessageView;
    private int mMultiChoiceItemLayout;
    protected android.widget.ScrollView mScrollView;
    private boolean mShowTitle;
    private int mSingleChoiceItemLayout;
    private java.lang.CharSequence mTitle;
    private android.widget.TextView mTitleView;
    private android.view.View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private int mViewSpacingTop;
    protected final android.view.Window mWindow;
    private boolean mViewSpacingSpecified = false;
    private int mIconId = 0;
    private int mCheckedItem = -1;
    private int mButtonPanelLayoutHint = 0;
    private final android.view.View.OnClickListener mButtonHandler = new android.view.View.OnClickListener() { // from class: com.android.internal.app.AlertController.1
        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.os.Message message;
            if (view == com.android.internal.app.AlertController.this.mButtonPositive && com.android.internal.app.AlertController.this.mButtonPositiveMessage != null) {
                message = android.os.Message.obtain(com.android.internal.app.AlertController.this.mButtonPositiveMessage);
            } else if (view == com.android.internal.app.AlertController.this.mButtonNegative && com.android.internal.app.AlertController.this.mButtonNegativeMessage != null) {
                message = android.os.Message.obtain(com.android.internal.app.AlertController.this.mButtonNegativeMessage);
            } else if (view == com.android.internal.app.AlertController.this.mButtonNeutral && com.android.internal.app.AlertController.this.mButtonNeutralMessage != null) {
                message = android.os.Message.obtain(com.android.internal.app.AlertController.this.mButtonNeutralMessage);
            } else {
                message = null;
            }
            if (message != null) {
                message.sendToTarget();
            }
            com.android.internal.app.AlertController.this.mHandler.obtainMessage(1, com.android.internal.app.AlertController.this.mDialogInterface).sendToTarget();
        }
    };

    private static final class ButtonHandler extends android.os.Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private java.lang.ref.WeakReference<android.content.DialogInterface> mDialog;

        public ButtonHandler(android.content.DialogInterface dialogInterface) {
            this.mDialog = new java.lang.ref.WeakReference<>(dialogInterface);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case -3:
                case -2:
                case -1:
                    ((android.content.DialogInterface.OnClickListener) message.obj).onClick(this.mDialog.get(), message.what);
                    break;
                case 1:
                    ((android.content.DialogInterface) message.obj).dismiss();
                    break;
            }
        }
    }

    private static boolean shouldCenterSingleButton(android.content.Context context) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        context.getTheme().resolveAttribute(com.android.internal.R.attr.alertDialogCenterButtons, typedValue, true);
        return typedValue.data != 0;
    }

    public static final com.android.internal.app.AlertController create(android.content.Context context, android.content.DialogInterface dialogInterface, android.view.Window window) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, com.android.internal.R.styleable.AlertDialog, 16842845, 16974371);
        int i = obtainStyledAttributes.getInt(12, 0);
        obtainStyledAttributes.recycle();
        switch (i) {
            case 1:
                return new com.android.internal.app.MicroAlertController(context, dialogInterface, window);
            default:
                return new com.android.internal.app.AlertController(context, dialogInterface, window);
        }
    }

    protected AlertController(android.content.Context context, android.content.DialogInterface dialogInterface, android.view.Window window) {
        this.mContext = context;
        this.mDialogInterface = dialogInterface;
        this.mWindow = window;
        this.mHandler = new com.android.internal.app.AlertController.ButtonHandler(dialogInterface);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, com.android.internal.R.styleable.AlertDialog, 16842845, 0);
        this.mAlertDialogLayout = obtainStyledAttributes.getResourceId(10, com.android.internal.R.layout.alert_dialog);
        this.mButtonPanelSideLayout = obtainStyledAttributes.getResourceId(11, 0);
        this.mListLayout = obtainStyledAttributes.getResourceId(15, com.android.internal.R.layout.select_dialog);
        this.mMultiChoiceItemLayout = obtainStyledAttributes.getResourceId(16, 17367059);
        this.mSingleChoiceItemLayout = obtainStyledAttributes.getResourceId(21, 17367058);
        this.mListItemLayout = obtainStyledAttributes.getResourceId(14, 17367057);
        this.mShowTitle = obtainStyledAttributes.getBoolean(20, true);
        obtainStyledAttributes.recycle();
        window.requestFeature(1);
    }

    static boolean canTextInput(android.view.View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof android.view.ViewGroup)) {
            return false;
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (canTextInput(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    public void installContent(com.android.internal.app.AlertController.AlertParams alertParams) {
        alertParams.apply(this);
        installContent();
    }

    public void installContent() {
        this.mWindow.setContentView(selectContentView());
        setupView();
    }

    private int selectContentView() {
        if (this.mButtonPanelSideLayout == 0) {
            return this.mAlertDialogLayout;
        }
        if (this.mButtonPanelLayoutHint == 1) {
            return this.mButtonPanelSideLayout;
        }
        return this.mAlertDialogLayout;
    }

    public void setTitle(java.lang.CharSequence charSequence) {
        this.mTitle = charSequence;
        if (this.mTitleView != null) {
            this.mTitleView.lambda$setTextAsync$0(charSequence);
        }
        this.mWindow.setTitle(charSequence);
    }

    public void setCustomTitle(android.view.View view) {
        this.mCustomTitleView = view;
    }

    public void setMessage(java.lang.CharSequence charSequence) {
        this.mMessage = charSequence;
        if (this.mMessageView != null) {
            this.mMessageView.lambda$setTextAsync$0(charSequence);
        }
    }

    public void setMessageMovementMethod(android.text.method.MovementMethod movementMethod) {
        this.mMessageMovementMethod = movementMethod;
        if (this.mMessageView != null) {
            this.mMessageView.setMovementMethod(movementMethod);
        }
    }

    public void setMessageHyphenationFrequency(int i) {
        this.mMessageHyphenationFrequency = java.lang.Integer.valueOf(i);
        if (this.mMessageView != null) {
            this.mMessageView.setHyphenationFrequency(i);
        }
    }

    public void setView(int i) {
        this.mView = null;
        this.mViewLayoutResId = i;
        this.mViewSpacingSpecified = false;
    }

    public void setView(android.view.View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(android.view.View view, int i, int i2, int i3, int i4) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = i;
        this.mViewSpacingTop = i2;
        this.mViewSpacingRight = i3;
        this.mViewSpacingBottom = i4;
    }

    public void setButtonPanelLayoutHint(int i) {
        this.mButtonPanelLayoutHint = i;
    }

    public void setButton(int i, java.lang.CharSequence charSequence, android.content.DialogInterface.OnClickListener onClickListener, android.os.Message message) {
        if (message == null && onClickListener != null) {
            message = this.mHandler.obtainMessage(i, onClickListener);
        }
        switch (i) {
            case -3:
                this.mButtonNeutralText = charSequence;
                this.mButtonNeutralMessage = message;
                return;
            case -2:
                this.mButtonNegativeText = charSequence;
                this.mButtonNegativeMessage = message;
                return;
            case -1:
                this.mButtonPositiveText = charSequence;
                this.mButtonPositiveMessage = message;
                return;
            default:
                throw new java.lang.IllegalArgumentException("Button does not exist");
        }
    }

    public void setIcon(int i) {
        this.mIcon = null;
        this.mIconId = i;
        if (this.mIconView != null) {
            if (i != 0) {
                this.mIconView.setVisibility(0);
                this.mIconView.setImageResource(this.mIconId);
            } else {
                this.mIconView.setVisibility(8);
            }
        }
    }

    public void setIcon(android.graphics.drawable.Drawable drawable) {
        this.mIcon = drawable;
        this.mIconId = 0;
        if (this.mIconView != null) {
            if (drawable != null) {
                this.mIconView.setVisibility(0);
                this.mIconView.setImageDrawable(drawable);
            } else {
                this.mIconView.setVisibility(8);
            }
        }
    }

    public int getIconAttributeResId(int i) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        this.mContext.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId;
    }

    public void setInverseBackgroundForced(boolean z) {
        this.mForceInverseBackground = z;
    }

    public android.widget.ListView getListView() {
        return this.mListView;
    }

    public android.widget.Button getButton(int i) {
        switch (i) {
            case -3:
                return this.mButtonNeutral;
            case -2:
                return this.mButtonNegative;
            case -1:
                return this.mButtonPositive;
            default:
                return null;
        }
    }

    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    private android.view.ViewGroup resolvePanel(android.view.View view, android.view.View view2) {
        if (view == null) {
            if (view2 instanceof android.view.ViewStub) {
                view2 = ((android.view.ViewStub) view2).inflate();
            }
            return (android.view.ViewGroup) view2;
        }
        if (view2 != null) {
            android.view.ViewParent parent = view2.getParent();
            if (parent instanceof android.view.ViewGroup) {
                ((android.view.ViewGroup) parent).removeView(view2);
            }
        }
        if (view instanceof android.view.ViewStub) {
            view = ((android.view.ViewStub) view).inflate();
        }
        return (android.view.ViewGroup) view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupView() {
        android.view.View findViewById;
        android.view.View view;
        android.view.View findViewById2;
        android.view.View findViewById3 = this.mWindow.findViewById(com.android.internal.R.id.parentPanel);
        android.view.View findViewById4 = findViewById3.findViewById(com.android.internal.R.id.topPanel);
        android.view.View findViewById5 = findViewById3.findViewById(com.android.internal.R.id.contentPanel);
        android.view.View findViewById6 = findViewById3.findViewById(com.android.internal.R.id.buttonPanel);
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) findViewById3.findViewById(com.android.internal.R.id.customPanel);
        setupCustomContent(viewGroup);
        android.view.View findViewById7 = viewGroup.findViewById(com.android.internal.R.id.topPanel);
        android.view.View findViewById8 = viewGroup.findViewById(com.android.internal.R.id.contentPanel);
        android.view.View findViewById9 = viewGroup.findViewById(com.android.internal.R.id.buttonPanel);
        android.view.ViewGroup resolvePanel = resolvePanel(findViewById7, findViewById4);
        android.view.ViewGroup resolvePanel2 = resolvePanel(findViewById8, findViewById5);
        android.view.ViewGroup resolvePanel3 = resolvePanel(findViewById9, findViewById6);
        setupContent(resolvePanel2);
        setupButtons(resolvePanel3);
        setupTitle(resolvePanel);
        boolean z = (viewGroup == null || viewGroup.getVisibility() == 8) ? false : true;
        boolean z2 = (resolvePanel == null || resolvePanel.getVisibility() == 8) ? 0 : 1;
        boolean z3 = (resolvePanel3 == null || resolvePanel3.getVisibility() == 8) ? false : true;
        if (!findViewById3.isInTouchMode()) {
            if (!requestFocusForContent(z ? viewGroup : resolvePanel2)) {
                requestFocusForDefaultButton();
            }
        }
        if (!z3) {
            if (resolvePanel2 != null && (findViewById2 = resolvePanel2.findViewById(com.android.internal.R.id.textSpacerNoButtons)) != null) {
                findViewById2.setVisibility(0);
            }
            this.mWindow.setCloseOnTouchOutsideIfNotSet(true);
        }
        if (z2 != 0) {
            if (this.mScrollView != null) {
                this.mScrollView.setClipToPadding(true);
            }
            if (this.mMessage != null || this.mListView != null || z) {
                if (z) {
                    view = null;
                } else {
                    view = resolvePanel.findViewById(com.android.internal.R.id.titleDividerNoCustom);
                }
                if (view == null) {
                    view = resolvePanel.findViewById(com.android.internal.R.id.titleDivider);
                }
            } else {
                view = resolvePanel.findViewById(com.android.internal.R.id.titleDividerTop);
            }
            if (view != null) {
                view.setVisibility(0);
            }
        } else if (resolvePanel2 != null && (findViewById = resolvePanel2.findViewById(com.android.internal.R.id.textSpacerNoTitle)) != null) {
            findViewById.setVisibility(0);
        }
        if (this.mListView instanceof com.android.internal.app.AlertController.RecycleListView) {
            ((com.android.internal.app.AlertController.RecycleListView) this.mListView).setHasDecor(z2, z3);
        }
        if (!z) {
            android.view.View view2 = this.mListView != null ? this.mListView : this.mScrollView;
            if (view2 != null) {
                view2.setScrollIndicators((z3 ? 2 : 0) | z2, 3);
            }
        }
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, com.android.internal.R.styleable.AlertDialog, 16842845, 0);
        setBackground(obtainStyledAttributes, resolvePanel, resolvePanel2, viewGroup, resolvePanel3, z2, z, z3);
        obtainStyledAttributes.recycle();
    }

    private boolean requestFocusForContent(android.view.View view) {
        if (view != null && view.requestFocus()) {
            return true;
        }
        if (this.mListView == null) {
            return false;
        }
        this.mListView.setSelection(0);
        return true;
    }

    private void requestFocusForDefaultButton() {
        if (this.mButtonPositive.getVisibility() == 0) {
            this.mButtonPositive.requestFocus();
        } else if (this.mButtonNegative.getVisibility() == 0) {
            this.mButtonNegative.requestFocus();
        } else if (this.mButtonNeutral.getVisibility() == 0) {
            this.mButtonNeutral.requestFocus();
        }
    }

    private void setupCustomContent(android.view.ViewGroup viewGroup) {
        android.view.View view;
        if (this.mView != null) {
            view = this.mView;
        } else if (this.mViewLayoutResId != 0) {
            view = android.view.LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false);
        } else {
            view = null;
        }
        boolean z = view != null;
        if (!z || !canTextInput(view)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (z) {
            android.widget.FrameLayout frameLayout = (android.widget.FrameLayout) this.mWindow.findViewById(16908331);
            frameLayout.addView(view, new android.view.ViewGroup.LayoutParams(-1, -1));
            if (this.mViewSpacingSpecified) {
                frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            }
            if (this.mListView != null) {
                ((android.widget.LinearLayout.LayoutParams) viewGroup.getLayoutParams()).weight = 0.0f;
                return;
            }
            return;
        }
        viewGroup.setVisibility(8);
    }

    protected void setupTitle(android.view.ViewGroup viewGroup) {
        if (this.mCustomTitleView != null && this.mShowTitle) {
            viewGroup.addView(this.mCustomTitleView, 0, new android.view.ViewGroup.LayoutParams(-1, -2));
            this.mWindow.findViewById(com.android.internal.R.id.title_template).setVisibility(8);
            return;
        }
        this.mIconView = (android.widget.ImageView) this.mWindow.findViewById(16908294);
        if ((!android.text.TextUtils.isEmpty(this.mTitle)) && this.mShowTitle) {
            this.mTitleView = (android.widget.TextView) this.mWindow.findViewById(com.android.internal.R.id.alertTitle);
            this.mTitleView.lambda$setTextAsync$0(this.mTitle);
            if (this.mIconId != 0) {
                this.mIconView.setImageResource(this.mIconId);
                return;
            } else if (this.mIcon != null) {
                this.mIconView.setImageDrawable(this.mIcon);
                return;
            } else {
                this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
                this.mIconView.setVisibility(8);
                return;
            }
        }
        this.mWindow.findViewById(com.android.internal.R.id.title_template).setVisibility(8);
        this.mIconView.setVisibility(8);
        viewGroup.setVisibility(8);
    }

    protected void setupContent(android.view.ViewGroup viewGroup) {
        this.mScrollView = (android.widget.ScrollView) viewGroup.findViewById(com.android.internal.R.id.scrollView);
        this.mScrollView.setFocusable(false);
        this.mMessageView = (android.widget.TextView) viewGroup.findViewById(16908299);
        if (this.mMessageView == null) {
            return;
        }
        if (this.mMessage != null) {
            this.mMessageView.lambda$setTextAsync$0(this.mMessage);
            if (this.mMessageMovementMethod != null) {
                this.mMessageView.setMovementMethod(this.mMessageMovementMethod);
            }
            if (this.mMessageHyphenationFrequency != null) {
                this.mMessageView.setHyphenationFrequency(this.mMessageHyphenationFrequency.intValue());
                return;
            }
            return;
        }
        this.mMessageView.setVisibility(8);
        this.mScrollView.removeView(this.mMessageView);
        if (this.mListView != null) {
            android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) this.mScrollView.getParent();
            int indexOfChild = viewGroup2.indexOfChild(this.mScrollView);
            viewGroup2.removeViewAt(indexOfChild);
            viewGroup2.addView(this.mListView, indexOfChild, new android.view.ViewGroup.LayoutParams(-1, -1));
            return;
        }
        viewGroup.setVisibility(8);
    }

    private static void manageScrollIndicators(android.view.View view, android.view.View view2, android.view.View view3) {
        if (view2 != null) {
            view2.setVisibility(view.canScrollVertically(-1) ? 0 : 4);
        }
        if (view3 != null) {
            view3.setVisibility(view.canScrollVertically(1) ? 0 : 4);
        }
    }

    protected void setupButtons(android.view.ViewGroup viewGroup) {
        int i;
        this.mButtonPositive = (android.widget.Button) viewGroup.findViewById(16908313);
        this.mButtonPositive.setOnClickListener(this.mButtonHandler);
        if (android.text.TextUtils.isEmpty(this.mButtonPositiveText)) {
            this.mButtonPositive.setVisibility(8);
            i = 0;
        } else {
            this.mButtonPositive.lambda$setTextAsync$0(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            i = 1;
        }
        this.mButtonNegative = (android.widget.Button) viewGroup.findViewById(16908314);
        this.mButtonNegative.setOnClickListener(this.mButtonHandler);
        if (android.text.TextUtils.isEmpty(this.mButtonNegativeText)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.lambda$setTextAsync$0(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            i |= 2;
        }
        this.mButtonNeutral = (android.widget.Button) viewGroup.findViewById(16908315);
        this.mButtonNeutral.setOnClickListener(this.mButtonHandler);
        if (android.text.TextUtils.isEmpty(this.mButtonNeutralText)) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.lambda$setTextAsync$0(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            i |= 4;
        }
        if (shouldCenterSingleButton(this.mContext)) {
            if (i == 1) {
                centerButton(this.mButtonPositive);
            } else if (i == 2) {
                centerButton(this.mButtonNegative);
            } else if (i == 4) {
                centerButton(this.mButtonNeutral);
            }
        }
        if (!(i != 0)) {
            viewGroup.setVisibility(8);
        }
    }

    private void centerButton(android.widget.Button button) {
        android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
        android.view.View findViewById = this.mWindow.findViewById(com.android.internal.R.id.leftSpacer);
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
        android.view.View findViewById2 = this.mWindow.findViewById(com.android.internal.R.id.rightSpacer);
        if (findViewById2 != null) {
            findViewById2.setVisibility(0);
        }
    }

    private void setBackground(android.content.res.TypedArray typedArray, android.view.View view, android.view.View view2, android.view.View view3, android.view.View view4, boolean z, boolean z2, boolean z3) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        if (!typedArray.getBoolean(17, true)) {
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
            i8 = 0;
            i9 = 0;
        } else {
            i = com.android.internal.R.drawable.popup_full_dark;
            i2 = com.android.internal.R.drawable.popup_top_dark;
            i3 = com.android.internal.R.drawable.popup_center_dark;
            i4 = com.android.internal.R.drawable.popup_bottom_dark;
            i5 = com.android.internal.R.drawable.popup_full_bright;
            i6 = com.android.internal.R.drawable.popup_top_bright;
            i7 = com.android.internal.R.drawable.popup_center_bright;
            i8 = com.android.internal.R.drawable.popup_bottom_bright;
            i9 = com.android.internal.R.drawable.popup_bottom_medium;
        }
        int resourceId = typedArray.getResourceId(5, i6);
        int resourceId2 = typedArray.getResourceId(1, i2);
        int resourceId3 = typedArray.getResourceId(6, i7);
        int resourceId4 = typedArray.getResourceId(2, i3);
        android.view.View[] viewArr = new android.view.View[4];
        boolean[] zArr = new boolean[4];
        if (!z) {
            i10 = 0;
        } else {
            viewArr[0] = view;
            zArr[0] = false;
            i10 = 1;
        }
        viewArr[i10] = view2.getVisibility() == 8 ? null : view2;
        zArr[i10] = this.mListView != null;
        int i12 = i10 + 1;
        if (z2) {
            viewArr[i12] = view3;
            zArr[i12] = this.mForceInverseBackground;
            i12++;
        }
        if (z3) {
            viewArr[i12] = view4;
            zArr[i12] = true;
        }
        android.view.View view5 = null;
        int i13 = 0;
        boolean z4 = false;
        boolean z5 = false;
        while (i13 < 4) {
            android.view.View view6 = viewArr[i13];
            if (view6 == null) {
                i11 = resourceId2;
            } else {
                if (view5 == null) {
                    i11 = resourceId2;
                } else {
                    if (!z4) {
                        i11 = resourceId2;
                        if (z5) {
                            resourceId2 = resourceId;
                        }
                        view5.setBackgroundResource(resourceId2);
                    } else {
                        i11 = resourceId2;
                        view5.setBackgroundResource(z5 ? resourceId3 : resourceId4);
                    }
                    z4 = true;
                }
                z5 = zArr[i13];
                view5 = view6;
            }
            i13++;
            resourceId2 = i11;
        }
        if (view5 != null) {
            if (z4) {
                int resourceId5 = typedArray.getResourceId(7, i8);
                int resourceId6 = typedArray.getResourceId(8, i9);
                int resourceId7 = typedArray.getResourceId(3, i4);
                if (!z5) {
                    resourceId5 = resourceId7;
                } else if (z3) {
                    resourceId5 = resourceId6;
                }
                view5.setBackgroundResource(resourceId5);
            } else {
                int resourceId8 = typedArray.getResourceId(4, i5);
                int resourceId9 = typedArray.getResourceId(0, i);
                if (!z5) {
                    resourceId8 = resourceId9;
                }
                view5.setBackgroundResource(resourceId8);
            }
        }
        android.widget.ListView listView = this.mListView;
        if (listView != null && this.mAdapter != null) {
            listView.setAdapter(this.mAdapter);
            int i14 = this.mCheckedItem;
            if (i14 > -1) {
                listView.setItemChecked(i14, true);
                listView.setSelectionFromTop(i14, typedArray.getDimensionPixelSize(19, 0));
            }
        }
    }

    public static class RecycleListView extends android.widget.ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;
        boolean mRecycleOnMeasure;

        public RecycleListView(android.content.Context context) {
            this(context, null);
        }

        public RecycleListView(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mRecycleOnMeasure = true;
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.RecycleListView);
            this.mPaddingBottomNoButtons = obtainStyledAttributes.getDimensionPixelOffset(0, -1);
            this.mPaddingTopNoTitle = obtainStyledAttributes.getDimensionPixelOffset(1, -1);
        }

        public void setHasDecor(boolean z, boolean z2) {
            if (!z2 || !z) {
                setPadding(getPaddingLeft(), z ? getPaddingTop() : this.mPaddingTopNoTitle, getPaddingRight(), z2 ? getPaddingBottom() : this.mPaddingBottomNoButtons);
            }
        }

        @Override // android.widget.ListView
        protected boolean recycleOnMeasure() {
            return this.mRecycleOnMeasure;
        }
    }

    public static class AlertParams {
        public android.widget.ListAdapter mAdapter;
        public boolean[] mCheckedItems;
        public final android.content.Context mContext;
        public android.database.Cursor mCursor;
        public android.view.View mCustomTitleView;
        public boolean mForceInverseBackground;
        public android.graphics.drawable.Drawable mIcon;
        public final android.view.LayoutInflater mInflater;
        public java.lang.String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public java.lang.CharSequence[] mItems;
        public java.lang.String mLabelColumn;
        public java.lang.CharSequence mMessage;
        public android.content.DialogInterface.OnClickListener mNegativeButtonListener;
        public java.lang.CharSequence mNegativeButtonText;
        public android.content.DialogInterface.OnClickListener mNeutralButtonListener;
        public java.lang.CharSequence mNeutralButtonText;
        public android.content.DialogInterface.OnCancelListener mOnCancelListener;
        public android.content.DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public android.content.DialogInterface.OnClickListener mOnClickListener;
        public android.content.DialogInterface.OnDismissListener mOnDismissListener;
        public android.widget.AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        public android.content.DialogInterface.OnKeyListener mOnKeyListener;
        public com.android.internal.app.AlertController.AlertParams.OnPrepareListViewListener mOnPrepareListViewListener;
        public android.content.DialogInterface.OnClickListener mPositiveButtonListener;
        public java.lang.CharSequence mPositiveButtonText;
        public java.lang.CharSequence mTitle;
        public android.view.View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public int mViewSpacingTop;
        public int mIconId = 0;
        public int mIconAttrId = 0;
        public boolean mViewSpacingSpecified = false;
        public int mCheckedItem = -1;
        public boolean mRecycleOnMeasure = true;
        public boolean mCancelable = true;

        public interface OnPrepareListViewListener {
            void onPrepareListView(android.widget.ListView listView);
        }

        public AlertParams(android.content.Context context) {
            this.mContext = context;
            this.mInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        }

        public void apply(com.android.internal.app.AlertController alertController) {
            if (this.mCustomTitleView != null) {
                alertController.setCustomTitle(this.mCustomTitleView);
            } else {
                if (this.mTitle != null) {
                    alertController.setTitle(this.mTitle);
                }
                if (this.mIcon != null) {
                    alertController.setIcon(this.mIcon);
                }
                if (this.mIconId != 0) {
                    alertController.setIcon(this.mIconId);
                }
                if (this.mIconAttrId != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(this.mIconAttrId));
                }
            }
            if (this.mMessage != null) {
                alertController.setMessage(this.mMessage);
            }
            if (this.mPositiveButtonText != null) {
                alertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null);
            }
            if (this.mNegativeButtonText != null) {
                alertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null);
            }
            if (this.mNeutralButtonText != null) {
                alertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null);
            }
            if (this.mForceInverseBackground) {
                alertController.setInverseBackgroundForced(true);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                createListView(alertController);
            }
            if (this.mView != null) {
                if (this.mViewSpacingSpecified) {
                    alertController.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                    return;
                } else {
                    alertController.setView(this.mView);
                    return;
                }
            }
            if (this.mViewLayoutResId != 0) {
                alertController.setView(this.mViewLayoutResId);
            }
        }

        private void createListView(final com.android.internal.app.AlertController alertController) {
            int i;
            android.widget.ListAdapter checkedItemAdapter;
            final com.android.internal.app.AlertController.RecycleListView recycleListView = (com.android.internal.app.AlertController.RecycleListView) this.mInflater.inflate(alertController.mListLayout, (android.view.ViewGroup) null);
            if (this.mIsMultiChoice) {
                if (this.mCursor == null) {
                    checkedItemAdapter = new android.widget.ArrayAdapter<java.lang.CharSequence>(this.mContext, alertController.mMultiChoiceItemLayout, 16908308, this.mItems) { // from class: com.android.internal.app.AlertController.AlertParams.1
                        @Override // android.widget.ArrayAdapter, android.widget.Adapter
                        public android.view.View getView(int i2, android.view.View view, android.view.ViewGroup viewGroup) {
                            android.view.View view2 = super.getView(i2, view, viewGroup);
                            if (com.android.internal.app.AlertController.AlertParams.this.mCheckedItems != null && com.android.internal.app.AlertController.AlertParams.this.mCheckedItems[i2]) {
                                recycleListView.setItemChecked(i2, true);
                            }
                            return view2;
                        }
                    };
                } else {
                    checkedItemAdapter = new android.widget.CursorAdapter(this.mContext, this.mCursor, false) { // from class: com.android.internal.app.AlertController.AlertParams.2
                        private final int mIsCheckedIndex;
                        private final int mLabelIndex;

                        {
                            android.database.Cursor cursor = getCursor();
                            this.mLabelIndex = cursor.getColumnIndexOrThrow(com.android.internal.app.AlertController.AlertParams.this.mLabelColumn);
                            this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(com.android.internal.app.AlertController.AlertParams.this.mIsCheckedColumn);
                        }

                        @Override // android.widget.CursorAdapter
                        public void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor) {
                            ((android.widget.CheckedTextView) view.findViewById(16908308)).lambda$setTextAsync$0(cursor.getString(this.mLabelIndex));
                            recycleListView.setItemChecked(cursor.getPosition(), cursor.getInt(this.mIsCheckedIndex) == 1);
                        }

                        @Override // android.widget.CursorAdapter
                        public android.view.View newView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup viewGroup) {
                            return com.android.internal.app.AlertController.AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, viewGroup, false);
                        }
                    };
                }
            } else {
                if (this.mIsSingleChoice) {
                    i = alertController.mSingleChoiceItemLayout;
                } else {
                    i = alertController.mListItemLayout;
                }
                if (this.mCursor != null) {
                    checkedItemAdapter = new android.widget.SimpleCursorAdapter(this.mContext, i, this.mCursor, new java.lang.String[]{this.mLabelColumn}, new int[]{16908308});
                } else if (this.mAdapter != null) {
                    checkedItemAdapter = this.mAdapter;
                } else {
                    checkedItemAdapter = new com.android.internal.app.AlertController.CheckedItemAdapter(this.mContext, i, 16908308, this.mItems);
                }
            }
            if (this.mOnPrepareListViewListener != null) {
                this.mOnPrepareListViewListener.onPrepareListView(recycleListView);
            }
            alertController.mAdapter = checkedItemAdapter;
            alertController.mCheckedItem = this.mCheckedItem;
            if (this.mOnClickListener != null) {
                recycleListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: com.android.internal.app.AlertController.AlertParams.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i2, long j) {
                        com.android.internal.app.AlertController.AlertParams.this.mOnClickListener.onClick(alertController.mDialogInterface, i2);
                        if (!com.android.internal.app.AlertController.AlertParams.this.mIsSingleChoice) {
                            alertController.mDialogInterface.dismiss();
                        }
                    }
                });
            } else if (this.mOnCheckboxClickListener != null) {
                recycleListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: com.android.internal.app.AlertController.AlertParams.4
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i2, long j) {
                        if (com.android.internal.app.AlertController.AlertParams.this.mCheckedItems != null) {
                            com.android.internal.app.AlertController.AlertParams.this.mCheckedItems[i2] = recycleListView.isItemChecked(i2);
                        }
                        com.android.internal.app.AlertController.AlertParams.this.mOnCheckboxClickListener.onClick(alertController.mDialogInterface, i2, recycleListView.isItemChecked(i2));
                    }
                });
            }
            if (this.mOnItemSelectedListener != null) {
                recycleListView.setOnItemSelectedListener(this.mOnItemSelectedListener);
            }
            if (this.mIsSingleChoice) {
                recycleListView.setChoiceMode(1);
            } else if (this.mIsMultiChoice) {
                recycleListView.setChoiceMode(2);
            }
            recycleListView.mRecycleOnMeasure = this.mRecycleOnMeasure;
            alertController.mListView = recycleListView;
        }
    }

    private static class CheckedItemAdapter extends android.widget.ArrayAdapter<java.lang.CharSequence> {
        public CheckedItemAdapter(android.content.Context context, int i, int i2, java.lang.CharSequence[] charSequenceArr) {
            super(context, i, i2, charSequenceArr);
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }
    }
}
