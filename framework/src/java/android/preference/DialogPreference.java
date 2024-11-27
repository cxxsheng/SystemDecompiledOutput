package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class DialogPreference extends android.preference.Preference implements android.content.DialogInterface.OnClickListener, android.content.DialogInterface.OnDismissListener, android.preference.PreferenceManager.OnActivityDestroyListener {
    private android.app.AlertDialog.Builder mBuilder;
    private android.app.Dialog mDialog;
    private android.graphics.drawable.Drawable mDialogIcon;
    private int mDialogLayoutResId;
    private java.lang.CharSequence mDialogMessage;
    private java.lang.CharSequence mDialogTitle;
    private final java.lang.Runnable mDismissRunnable;
    private java.lang.CharSequence mNegativeButtonText;
    private java.lang.CharSequence mPositiveButtonText;
    private int mWhichButtonClicked;

    public DialogPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDismissRunnable = new java.lang.Runnable() { // from class: android.preference.DialogPreference.1
            @Override // java.lang.Runnable
            public void run() {
                android.preference.DialogPreference.this.mDialog.dismiss();
            }
        };
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.DialogPreference, i, i2);
        this.mDialogTitle = obtainStyledAttributes.getString(0);
        if (this.mDialogTitle == null) {
            this.mDialogTitle = getTitle();
        }
        this.mDialogMessage = obtainStyledAttributes.getString(1);
        this.mDialogIcon = obtainStyledAttributes.getDrawable(2);
        this.mPositiveButtonText = obtainStyledAttributes.getString(3);
        this.mNegativeButtonText = obtainStyledAttributes.getString(4);
        this.mDialogLayoutResId = obtainStyledAttributes.getResourceId(5, this.mDialogLayoutResId);
        obtainStyledAttributes.recycle();
    }

    public DialogPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DialogPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public DialogPreference(android.content.Context context) {
        this(context, null);
    }

    public void setDialogTitle(java.lang.CharSequence charSequence) {
        this.mDialogTitle = charSequence;
    }

    public void setDialogTitle(int i) {
        setDialogTitle(getContext().getString(i));
    }

    public java.lang.CharSequence getDialogTitle() {
        return this.mDialogTitle;
    }

    public void setDialogMessage(java.lang.CharSequence charSequence) {
        this.mDialogMessage = charSequence;
    }

    public void setDialogMessage(int i) {
        setDialogMessage(getContext().getString(i));
    }

    public java.lang.CharSequence getDialogMessage() {
        return this.mDialogMessage;
    }

    public void setDialogIcon(android.graphics.drawable.Drawable drawable) {
        this.mDialogIcon = drawable;
    }

    public void setDialogIcon(int i) {
        this.mDialogIcon = getContext().getDrawable(i);
    }

    public android.graphics.drawable.Drawable getDialogIcon() {
        return this.mDialogIcon;
    }

    public void setPositiveButtonText(java.lang.CharSequence charSequence) {
        this.mPositiveButtonText = charSequence;
    }

    public void setPositiveButtonText(int i) {
        setPositiveButtonText(getContext().getString(i));
    }

    public java.lang.CharSequence getPositiveButtonText() {
        return this.mPositiveButtonText;
    }

    public void setNegativeButtonText(java.lang.CharSequence charSequence) {
        this.mNegativeButtonText = charSequence;
    }

    public void setNegativeButtonText(int i) {
        setNegativeButtonText(getContext().getString(i));
    }

    public java.lang.CharSequence getNegativeButtonText() {
        return this.mNegativeButtonText;
    }

    public void setDialogLayoutResource(int i) {
        this.mDialogLayoutResId = i;
    }

    public int getDialogLayoutResource() {
        return this.mDialogLayoutResId;
    }

    protected void onPrepareDialogBuilder(android.app.AlertDialog.Builder builder) {
    }

    @Override // android.preference.Preference
    protected void onClick() {
        if (this.mDialog == null || !this.mDialog.isShowing()) {
            showDialog(null);
        }
    }

    protected void showDialog(android.os.Bundle bundle) {
        android.content.Context context = getContext();
        this.mWhichButtonClicked = -2;
        this.mBuilder = new android.app.AlertDialog.Builder(context).setTitle(this.mDialogTitle).setIcon(this.mDialogIcon).setPositiveButton(this.mPositiveButtonText, this).setNegativeButton(this.mNegativeButtonText, this);
        android.view.View onCreateDialogView = onCreateDialogView();
        if (onCreateDialogView != null) {
            onBindDialogView(onCreateDialogView);
            this.mBuilder.setView(onCreateDialogView);
        } else {
            this.mBuilder.setMessage(this.mDialogMessage);
        }
        onPrepareDialogBuilder(this.mBuilder);
        getPreferenceManager().registerOnActivityDestroyListener(this);
        android.app.AlertDialog create = this.mBuilder.create();
        this.mDialog = create;
        if (bundle != null) {
            create.onRestoreInstanceState(bundle);
        }
        create.setOnShowListener(new android.content.DialogInterface.OnShowListener() { // from class: android.preference.DialogPreference.2
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(android.content.DialogInterface dialogInterface) {
                android.preference.DialogPreference.this.removeDismissCallbacks();
            }
        });
        create.setOnDismissListener(this);
        create.show();
    }

    private android.view.View getDecorView() {
        if (this.mDialog != null && this.mDialog.getWindow() != null) {
            return this.mDialog.getWindow().getDecorView();
        }
        return null;
    }

    void postDismiss() {
        removeDismissCallbacks();
        android.view.View decorView = getDecorView();
        if (decorView != null) {
            decorView.post(this.mDismissRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeDismissCallbacks() {
        android.view.View decorView = getDecorView();
        if (decorView != null) {
            decorView.removeCallbacks(this.mDismissRunnable);
        }
    }

    protected android.view.View onCreateDialogView() {
        if (this.mDialogLayoutResId == 0) {
            return null;
        }
        return android.view.LayoutInflater.from(this.mBuilder.getContext()).inflate(this.mDialogLayoutResId, (android.view.ViewGroup) null);
    }

    protected void onBindDialogView(android.view.View view) {
        int i;
        android.view.View findViewById = view.findViewById(16908299);
        if (findViewById != null) {
            java.lang.CharSequence dialogMessage = getDialogMessage();
            if (android.text.TextUtils.isEmpty(dialogMessage)) {
                i = 8;
            } else {
                if (findViewById instanceof android.widget.TextView) {
                    ((android.widget.TextView) findViewById).lambda$setTextAsync$0(dialogMessage);
                }
                i = 0;
            }
            if (findViewById.getVisibility() != i) {
                findViewById.setVisibility(i);
            }
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        this.mWhichButtonClicked = i;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        removeDismissCallbacks();
        getPreferenceManager().unregisterOnActivityDestroyListener(this);
        this.mDialog = null;
        onDialogClosed(this.mWhichButtonClicked == -1);
    }

    protected void onDialogClosed(boolean z) {
    }

    public android.app.Dialog getDialog() {
        return this.mDialog;
    }

    @Override // android.preference.PreferenceManager.OnActivityDestroyListener
    public void onActivityDestroy() {
        if (this.mDialog == null || !this.mDialog.isShowing()) {
            return;
        }
        this.mDialog.dismiss();
    }

    @Override // android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (this.mDialog == null || !this.mDialog.isShowing()) {
            return onSaveInstanceState;
        }
        android.preference.DialogPreference.SavedState savedState = new android.preference.DialogPreference.SavedState(onSaveInstanceState);
        savedState.isDialogShowing = true;
        savedState.dialogBundle = this.mDialog.onSaveInstanceState();
        return savedState;
    }

    @Override // android.preference.Preference
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(android.preference.DialogPreference.SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.preference.DialogPreference.SavedState savedState = (android.preference.DialogPreference.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.isDialogShowing) {
            showDialog(savedState.dialogBundle);
        }
    }

    private static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.preference.DialogPreference.SavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.DialogPreference.SavedState>() { // from class: android.preference.DialogPreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.DialogPreference.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.DialogPreference.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.DialogPreference.SavedState[] newArray(int i) {
                return new android.preference.DialogPreference.SavedState[i];
            }
        };
        android.os.Bundle dialogBundle;
        boolean isDialogShowing;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.isDialogShowing = parcel.readInt() == 1;
            this.dialogBundle = parcel.readBundle();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isDialogShowing ? 1 : 0);
            parcel.writeBundle(this.dialogBundle);
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }
    }
}
