package android.app;

/* loaded from: classes.dex */
public class AlertDialog extends android.app.Dialog implements android.content.DialogInterface {
    public static final int LAYOUT_HINT_NONE = 0;
    public static final int LAYOUT_HINT_SIDE = 1;

    @java.lang.Deprecated
    public static final int THEME_DEVICE_DEFAULT_DARK = 4;

    @java.lang.Deprecated
    public static final int THEME_DEVICE_DEFAULT_LIGHT = 5;

    @java.lang.Deprecated
    public static final int THEME_HOLO_DARK = 2;

    @java.lang.Deprecated
    public static final int THEME_HOLO_LIGHT = 3;

    @java.lang.Deprecated
    public static final int THEME_TRADITIONAL = 1;
    private com.android.internal.app.AlertController mAlert;

    protected AlertDialog(android.content.Context context) {
        this(context, 0);
    }

    protected AlertDialog(android.content.Context context, boolean z, android.content.DialogInterface.OnCancelListener onCancelListener) {
        this(context, 0);
        setCancelable(z);
        setOnCancelListener(onCancelListener);
    }

    protected AlertDialog(android.content.Context context, int i) {
        this(context, i, true);
    }

    AlertDialog(android.content.Context context, int i, boolean z) {
        super(context, z ? resolveDialogTheme(context, i) : 0, z);
        this.mWindow.alwaysReadCloseOnTouchAttr();
        this.mAlert = com.android.internal.app.AlertController.create(getContext(), this, getWindow());
    }

    static int resolveDialogTheme(android.content.Context context, int i) {
        if (i == 1) {
            return com.android.internal.R.style.Theme_Dialog_Alert;
        }
        if (i == 2) {
            return com.android.internal.R.style.Theme_Holo_Dialog_Alert;
        }
        if (i == 3) {
            return com.android.internal.R.style.Theme_Holo_Light_Dialog_Alert;
        }
        if (i == 4) {
            return 16974545;
        }
        if (i == 5) {
            return 16974546;
        }
        if (android.content.res.ResourceId.isValid(i)) {
            return i;
        }
        android.util.TypedValue typedValue = new android.util.TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        return typedValue.resourceId;
    }

    public android.widget.Button getButton(int i) {
        return this.mAlert.getButton(i);
    }

    public android.widget.ListView getListView() {
        return this.mAlert.getListView();
    }

    @Override // android.app.Dialog
    public void setTitle(java.lang.CharSequence charSequence) {
        super.setTitle(charSequence);
        this.mAlert.setTitle(charSequence);
    }

    public void setCustomTitle(android.view.View view) {
        this.mAlert.setCustomTitle(view);
    }

    public void setMessage(java.lang.CharSequence charSequence) {
        this.mAlert.setMessage(charSequence);
    }

    public void setMessageMovementMethod(android.text.method.MovementMethod movementMethod) {
        this.mAlert.setMessageMovementMethod(movementMethod);
    }

    public void setMessageHyphenationFrequency(int i) {
        this.mAlert.setMessageHyphenationFrequency(i);
    }

    public void setView(android.view.View view) {
        this.mAlert.setView(view);
    }

    public void setView(android.view.View view, int i, int i2, int i3, int i4) {
        this.mAlert.setView(view, i, i2, i3, i4);
    }

    void setButtonPanelLayoutHint(int i) {
        this.mAlert.setButtonPanelLayoutHint(i);
    }

    public void setButton(int i, java.lang.CharSequence charSequence, android.os.Message message) {
        this.mAlert.setButton(i, charSequence, null, message);
    }

    public void setButton(int i, java.lang.CharSequence charSequence, android.content.DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(i, charSequence, onClickListener, null);
    }

    @java.lang.Deprecated
    public void setButton(java.lang.CharSequence charSequence, android.os.Message message) {
        setButton(-1, charSequence, message);
    }

    @java.lang.Deprecated
    public void setButton2(java.lang.CharSequence charSequence, android.os.Message message) {
        setButton(-2, charSequence, message);
    }

    @java.lang.Deprecated
    public void setButton3(java.lang.CharSequence charSequence, android.os.Message message) {
        setButton(-3, charSequence, message);
    }

    @java.lang.Deprecated
    public void setButton(java.lang.CharSequence charSequence, android.content.DialogInterface.OnClickListener onClickListener) {
        setButton(-1, charSequence, onClickListener);
    }

    @java.lang.Deprecated
    public void setButton2(java.lang.CharSequence charSequence, android.content.DialogInterface.OnClickListener onClickListener) {
        setButton(-2, charSequence, onClickListener);
    }

    @java.lang.Deprecated
    public void setButton3(java.lang.CharSequence charSequence, android.content.DialogInterface.OnClickListener onClickListener) {
        setButton(-3, charSequence, onClickListener);
    }

    public void setIcon(int i) {
        this.mAlert.setIcon(i);
    }

    public void setIcon(android.graphics.drawable.Drawable drawable) {
        this.mAlert.setIcon(drawable);
    }

    public void setIconAttribute(int i) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        this.mContext.getTheme().resolveAttribute(i, typedValue, true);
        this.mAlert.setIcon(typedValue.resourceId);
    }

    public void setInverseBackgroundForced(boolean z) {
        this.mAlert.setInverseBackgroundForced(z);
    }

    @Override // android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert.installContent();
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    public static class Builder {
        private final com.android.internal.app.AlertController.AlertParams P;

        public Builder(android.content.Context context) {
            this(context, android.app.AlertDialog.resolveDialogTheme(context, 0));
        }

        public Builder(android.content.Context context, int i) {
            this.P = new com.android.internal.app.AlertController.AlertParams(new android.view.ContextThemeWrapper(context, android.app.AlertDialog.resolveDialogTheme(context, i)));
        }

        public android.content.Context getContext() {
            return this.P.mContext;
        }

        public android.app.AlertDialog.Builder setTitle(int i) {
            this.P.mTitle = this.P.mContext.getText(i);
            return this;
        }

        public android.app.AlertDialog.Builder setTitle(java.lang.CharSequence charSequence) {
            this.P.mTitle = charSequence;
            return this;
        }

        public android.app.AlertDialog.Builder setCustomTitle(android.view.View view) {
            this.P.mCustomTitleView = view;
            return this;
        }

        public android.app.AlertDialog.Builder setMessage(int i) {
            this.P.mMessage = this.P.mContext.getText(i);
            return this;
        }

        public android.app.AlertDialog.Builder setMessage(java.lang.CharSequence charSequence) {
            this.P.mMessage = charSequence;
            return this;
        }

        public android.app.AlertDialog.Builder setIcon(int i) {
            this.P.mIconId = i;
            return this;
        }

        public android.app.AlertDialog.Builder setIcon(android.graphics.drawable.Drawable drawable) {
            this.P.mIcon = drawable;
            return this;
        }

        public android.app.AlertDialog.Builder setIconAttribute(int i) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            this.P.mContext.getTheme().resolveAttribute(i, typedValue, true);
            this.P.mIconId = typedValue.resourceId;
            return this;
        }

        public android.app.AlertDialog.Builder setPositiveButton(int i, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = this.P.mContext.getText(i);
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setPositiveButton(java.lang.CharSequence charSequence, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = charSequence;
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setNegativeButton(int i, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = this.P.mContext.getText(i);
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setNegativeButton(java.lang.CharSequence charSequence, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = charSequence;
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setNeutralButton(int i, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonText = this.P.mContext.getText(i);
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setNeutralButton(java.lang.CharSequence charSequence, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonText = charSequence;
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setCancelable(boolean z) {
            this.P.mCancelable = z;
            return this;
        }

        public android.app.AlertDialog.Builder setOnCancelListener(android.content.DialogInterface.OnCancelListener onCancelListener) {
            this.P.mOnCancelListener = onCancelListener;
            return this;
        }

        public android.app.AlertDialog.Builder setOnDismissListener(android.content.DialogInterface.OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener;
            return this;
        }

        public android.app.AlertDialog.Builder setOnKeyListener(android.content.DialogInterface.OnKeyListener onKeyListener) {
            this.P.mOnKeyListener = onKeyListener;
            return this;
        }

        public android.app.AlertDialog.Builder setItems(int i, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(i);
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setItems(java.lang.CharSequence[] charSequenceArr, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = charSequenceArr;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setAdapter(android.widget.ListAdapter listAdapter, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = listAdapter;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setCursor(android.database.Cursor cursor, android.content.DialogInterface.OnClickListener onClickListener, java.lang.String str) {
            this.P.mCursor = cursor;
            this.P.mLabelColumn = str;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public android.app.AlertDialog.Builder setMultiChoiceItems(int i, boolean[] zArr, android.content.DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(i);
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.P.mCheckedItems = zArr;
            this.P.mIsMultiChoice = true;
            return this;
        }

        public android.app.AlertDialog.Builder setMultiChoiceItems(java.lang.CharSequence[] charSequenceArr, boolean[] zArr, android.content.DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mItems = charSequenceArr;
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.P.mCheckedItems = zArr;
            this.P.mIsMultiChoice = true;
            return this;
        }

        public android.app.AlertDialog.Builder setMultiChoiceItems(android.database.Cursor cursor, java.lang.String str, java.lang.String str2, android.content.DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mCursor = cursor;
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.P.mIsCheckedColumn = str;
            this.P.mLabelColumn = str2;
            this.P.mIsMultiChoice = true;
            return this;
        }

        public android.app.AlertDialog.Builder setSingleChoiceItems(int i, int i2, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(i);
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = i2;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public android.app.AlertDialog.Builder setSingleChoiceItems(android.database.Cursor cursor, int i, java.lang.String str, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mCursor = cursor;
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = i;
            this.P.mLabelColumn = str;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public android.app.AlertDialog.Builder setSingleChoiceItems(java.lang.CharSequence[] charSequenceArr, int i, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = charSequenceArr;
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = i;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public android.app.AlertDialog.Builder setSingleChoiceItems(android.widget.ListAdapter listAdapter, int i, android.content.DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = listAdapter;
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = i;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public android.app.AlertDialog.Builder setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener) {
            this.P.mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        public android.app.AlertDialog.Builder setView(int i) {
            this.P.mView = null;
            this.P.mViewLayoutResId = i;
            this.P.mViewSpacingSpecified = false;
            return this;
        }

        public android.app.AlertDialog.Builder setView(android.view.View view) {
            this.P.mView = view;
            this.P.mViewLayoutResId = 0;
            this.P.mViewSpacingSpecified = false;
            return this;
        }

        @java.lang.Deprecated
        public android.app.AlertDialog.Builder setView(android.view.View view, int i, int i2, int i3, int i4) {
            this.P.mView = view;
            this.P.mViewLayoutResId = 0;
            this.P.mViewSpacingSpecified = true;
            this.P.mViewSpacingLeft = i;
            this.P.mViewSpacingTop = i2;
            this.P.mViewSpacingRight = i3;
            this.P.mViewSpacingBottom = i4;
            return this;
        }

        @java.lang.Deprecated
        public android.app.AlertDialog.Builder setInverseBackgroundForced(boolean z) {
            this.P.mForceInverseBackground = z;
            return this;
        }

        public android.app.AlertDialog.Builder setRecycleOnMeasureEnabled(boolean z) {
            this.P.mRecycleOnMeasure = z;
            return this;
        }

        public android.app.AlertDialog create() {
            android.app.AlertDialog alertDialog = new android.app.AlertDialog(this.P.mContext, 0, false);
            this.P.apply(alertDialog.mAlert);
            alertDialog.setCancelable(this.P.mCancelable);
            if (this.P.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(this.P.mOnCancelListener);
            alertDialog.setOnDismissListener(this.P.mOnDismissListener);
            if (this.P.mOnKeyListener != null) {
                alertDialog.setOnKeyListener(this.P.mOnKeyListener);
            }
            return alertDialog;
        }

        public android.app.AlertDialog show() {
            android.app.AlertDialog create = create();
            create.show();
            return create;
        }
    }
}
