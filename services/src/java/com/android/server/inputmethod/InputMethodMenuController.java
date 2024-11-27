package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class InputMethodMenuController {
    private static final java.lang.String TAG = com.android.server.inputmethod.InputMethodMenuController.class.getSimpleName();
    private android.app.AlertDialog.Builder mDialogBuilder;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private com.android.server.inputmethod.InputMethodDialogWindowContext mDialogWindowContext;
    private android.view.inputmethod.InputMethodInfo[] mIms;
    private final com.android.server.inputmethod.InputMethodManagerService mService;
    private boolean mShowImeWithHardKeyboard;
    private int[] mSubtypeIds;
    private android.app.AlertDialog mSwitchingDialog;
    private android.view.View mSwitchingDialogTitleView;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);

    InputMethodMenuController(com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService) {
        this.mService = inputMethodManagerService;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void showInputMethodMenuLocked(boolean z, int i, java.lang.String str, int i2, @android.annotation.NonNull java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> list) {
        int i3;
        android.view.inputmethod.InputMethodSubtype currentInputMethodSubtypeLocked;
        final int currentImeUserIdLocked = this.mService.getCurrentImeUserIdLocked();
        if (list.isEmpty()) {
            return;
        }
        hideInputMethodMenuLocked();
        int i4 = i2;
        if (i4 == -1 && (currentInputMethodSubtypeLocked = this.mService.getCurrentInputMethodSubtypeLocked()) != null) {
            i4 = com.android.server.inputmethod.SubtypeUtils.getSubtypeIdFromHashCode(this.mService.queryInputMethodForCurrentUserLocked(this.mService.getSelectedMethodIdLocked()), currentInputMethodSubtypeLocked.hashCode());
        }
        int size = list.size();
        this.mIms = new android.view.inputmethod.InputMethodInfo[size];
        this.mSubtypeIds = new int[size];
        int i5 = 0;
        for (int i6 = 0; i6 < size; i6++) {
            com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem = list.get(i6);
            this.mIms[i6] = imeSubtypeListItem.mImi;
            this.mSubtypeIds[i6] = imeSubtypeListItem.mSubtypeId;
            if (this.mIms[i6].getId().equals(str) && ((i3 = this.mSubtypeIds[i6]) == -1 || ((i4 == -1 && i3 == 0) || i3 == i4))) {
                i5 = i6;
            }
        }
        if (this.mDialogWindowContext == null) {
            this.mDialogWindowContext = new com.android.server.inputmethod.InputMethodDialogWindowContext();
        }
        android.content.Context context = this.mDialogWindowContext.get(i);
        this.mDialogBuilder = new android.app.AlertDialog.Builder(context);
        this.mDialogBuilder.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() { // from class: com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(android.content.DialogInterface dialogInterface) {
                com.android.server.inputmethod.InputMethodMenuController.this.lambda$showInputMethodMenuLocked$0(dialogInterface);
            }
        });
        android.content.Context context2 = this.mDialogBuilder.getContext();
        android.content.res.TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(null, com.android.internal.R.styleable.DialogPreference, android.R.attr.alertDialogStyle, 0);
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(2);
        obtainStyledAttributes.recycle();
        this.mDialogBuilder.setIcon(drawable);
        android.view.View inflate = ((android.view.LayoutInflater) context2.getSystemService(android.view.LayoutInflater.class)).inflate(android.R.layout.input_method_switch_dialog_title, (android.view.ViewGroup) null);
        this.mDialogBuilder.setCustomTitle(inflate);
        this.mSwitchingDialogTitleView = inflate;
        this.mSwitchingDialogTitleView.findViewById(android.R.id.hard_keyboard_section).setVisibility(this.mWindowManagerInternal.isHardKeyboardAvailable() ? 0 : 8);
        android.widget.Switch r3 = (android.widget.Switch) this.mSwitchingDialogTitleView.findViewById(android.R.id.hard_keyboard_switch);
        r3.setChecked(this.mShowImeWithHardKeyboard);
        r3.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() { // from class: com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(android.widget.CompoundButton compoundButton, boolean z2) {
                com.android.server.inputmethod.InputMethodMenuController.this.lambda$showInputMethodMenuLocked$1(currentImeUserIdLocked, compoundButton, z2);
            }
        });
        final com.android.server.inputmethod.InputMethodMenuController.ImeSubtypeListAdapter imeSubtypeListAdapter = new com.android.server.inputmethod.InputMethodMenuController.ImeSubtypeListAdapter(context2, android.R.layout.input_method_switch_item, list, i5);
        this.mDialogBuilder.setSingleChoiceItems(imeSubtypeListAdapter, i5, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.inputmethod.InputMethodMenuController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(android.content.DialogInterface dialogInterface, int i7) {
                com.android.server.inputmethod.InputMethodMenuController.this.lambda$showInputMethodMenuLocked$2(imeSubtypeListAdapter, dialogInterface, i7);
            }
        });
        this.mSwitchingDialog = this.mDialogBuilder.create();
        this.mSwitchingDialog.setCanceledOnTouchOutside(true);
        android.view.Window window = this.mSwitchingDialog.getWindow();
        android.view.WindowManager.LayoutParams attributes = window.getAttributes();
        window.setType(2012);
        window.setHideOverlayWindows(true);
        attributes.token = context.getWindowContextToken();
        attributes.privateFlags |= 16;
        attributes.setTitle("Select input method");
        window.setAttributes(attributes);
        this.mService.updateSystemUiLocked();
        this.mService.sendOnNavButtonFlagsChangedLocked();
        this.mSwitchingDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showInputMethodMenuLocked$0(android.content.DialogInterface dialogInterface) {
        hideInputMethodMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showInputMethodMenuLocked$1(int i, android.widget.CompoundButton compoundButton, boolean z) {
        com.android.server.inputmethod.SecureSettingsWrapper.putBoolean("show_ime_with_hard_keyboard", z, i);
        hideInputMethodMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showInputMethodMenuLocked$2(com.android.server.inputmethod.InputMethodMenuController.ImeSubtypeListAdapter imeSubtypeListAdapter, android.content.DialogInterface dialogInterface, int i) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (this.mIms == null || this.mIms.length <= i || this.mSubtypeIds == null || this.mSubtypeIds.length <= i) {
                    return;
                }
                android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mIms[i];
                int i2 = this.mSubtypeIds[i];
                imeSubtypeListAdapter.mCheckedItem = i;
                imeSubtypeListAdapter.notifyDataSetChanged();
                if (inputMethodInfo != null) {
                    if (i2 < 0 || i2 >= inputMethodInfo.getSubtypeCount()) {
                        i2 = -1;
                    }
                    this.mService.setInputMethodLocked(inputMethodInfo.getId(), i2);
                }
                hideInputMethodMenuLocked();
            } finally {
            }
        }
    }

    void updateKeyboardFromSettingsLocked() {
        this.mShowImeWithHardKeyboard = com.android.server.inputmethod.SecureSettingsWrapper.getBoolean("show_ime_with_hard_keyboard", false, this.mService.getCurrentImeUserIdLocked());
        if (this.mSwitchingDialog != null && this.mSwitchingDialogTitleView != null && this.mSwitchingDialog.isShowing()) {
            ((android.widget.Switch) this.mSwitchingDialogTitleView.findViewById(android.R.id.hard_keyboard_switch)).setChecked(this.mShowImeWithHardKeyboard);
        }
    }

    void hideInputMethodMenu() {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            hideInputMethodMenuLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void hideInputMethodMenuLocked() {
        if (this.mSwitchingDialog != null) {
            this.mSwitchingDialog.dismiss();
            this.mSwitchingDialog = null;
            this.mSwitchingDialogTitleView = null;
            this.mService.updateSystemUiLocked();
            this.mService.sendOnNavButtonFlagsChangedLocked();
            this.mDialogBuilder = null;
            this.mIms = null;
        }
    }

    android.app.AlertDialog getSwitchingDialogLocked() {
        return this.mSwitchingDialog;
    }

    boolean getShowImeWithHardKeyboard() {
        return this.mShowImeWithHardKeyboard;
    }

    boolean isisInputMethodPickerShownForTestLocked() {
        if (this.mSwitchingDialog == null) {
            return false;
        }
        return this.mSwitchingDialog.isShowing();
    }

    void handleHardKeyboardStatusChange(boolean z) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (this.mSwitchingDialog != null && this.mSwitchingDialogTitleView != null && this.mSwitchingDialog.isShowing()) {
                    this.mSwitchingDialogTitleView.findViewById(android.R.id.hard_keyboard_section).setVisibility(z ? 0 : 8);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ImeSubtypeListAdapter extends android.widget.ArrayAdapter<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> {
        public int mCheckedItem;
        private final android.view.LayoutInflater mInflater;
        private final java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> mItemsList;
        private final int mTextViewResourceId;

        private ImeSubtypeListAdapter(android.content.Context context, int i, java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> list, int i2) {
            super(context, i, list);
            this.mTextViewResourceId = i;
            this.mItemsList = list;
            this.mCheckedItem = i2;
            this.mInflater = android.view.LayoutInflater.from(context);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(this.mTextViewResourceId, (android.view.ViewGroup) null);
            }
            if (i < 0 || i >= this.mItemsList.size()) {
                return view;
            }
            com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem = this.mItemsList.get(i);
            java.lang.CharSequence charSequence = imeSubtypeListItem.mImeName;
            java.lang.CharSequence charSequence2 = imeSubtypeListItem.mSubtypeName;
            android.widget.TextView textView = (android.widget.TextView) view.findViewById(android.R.id.text1);
            android.widget.TextView textView2 = (android.widget.TextView) view.findViewById(android.R.id.text2);
            if (android.text.TextUtils.isEmpty(charSequence2)) {
                textView.setText(charSequence);
                textView2.setVisibility(8);
            } else {
                textView.setText(charSequence2);
                textView2.setText(charSequence);
                textView2.setVisibility(0);
            }
            ((android.widget.RadioButton) view.findViewById(android.R.id.radial)).setChecked(i == this.mCheckedItem);
            return view;
        }
    }
}
