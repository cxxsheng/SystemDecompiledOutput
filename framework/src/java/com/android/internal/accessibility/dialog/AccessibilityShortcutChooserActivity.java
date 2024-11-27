package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
public class AccessibilityShortcutChooserActivity extends android.app.Activity {
    private static final java.lang.String KEY_ACCESSIBILITY_SHORTCUT_MENU_MODE = "accessibility_shortcut_menu_mode";
    private android.app.AlertDialog mMenuDialog;
    private android.app.Dialog mPermissionDialog;
    private com.android.internal.accessibility.dialog.ShortcutTargetAdapter mTargetAdapter;
    private final int mShortcutType = 1;
    private final java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> mTargets = new java.util.ArrayList();

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        if (!getTheme().obtainStyledAttributes(android.R.styleable.Theme).getBoolean(38, false)) {
            requestWindowFeature(1);
        }
        this.mTargets.addAll(com.android.internal.accessibility.dialog.AccessibilityTargetHelper.getTargets(this, 1));
        this.mTargetAdapter = new com.android.internal.accessibility.dialog.ShortcutTargetAdapter(this.mTargets);
        this.mMenuDialog = createMenuDialog();
        this.mMenuDialog.setOnShowListener(new android.content.DialogInterface.OnShowListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(android.content.DialogInterface dialogInterface) {
                com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$onCreate$0(dialogInterface);
            }
        });
        this.mMenuDialog.show();
        if (bundle != null && bundle.getInt(KEY_ACCESSIBILITY_SHORTCUT_MENU_MODE, 0) == 1) {
            onEditButtonClicked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(android.content.DialogInterface dialogInterface) {
        updateDialogListeners();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mMenuDialog.setOnDismissListener(null);
        this.mMenuDialog.dismiss();
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(KEY_ACCESSIBILITY_SHORTCUT_MENU_MODE, this.mTargetAdapter.getShortcutMenuMode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
        com.android.internal.accessibility.dialog.AccessibilityTarget accessibilityTarget = this.mTargets.get(i);
        if (((accessibilityTarget instanceof com.android.internal.accessibility.dialog.AccessibilityServiceTarget) || (accessibilityTarget instanceof com.android.internal.accessibility.dialog.AccessibilityActivityTarget)) && sendRestrictedDialogIntentIfNeeded(accessibilityTarget)) {
            return;
        }
        accessibilityTarget.onSelected();
        this.mMenuDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetChecked(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
        com.android.internal.accessibility.dialog.AccessibilityTarget accessibilityTarget = this.mTargets.get(i);
        if (android.view.accessibility.Flags.cleanupAccessibilityWarningDialog()) {
            if (accessibilityTarget instanceof com.android.internal.accessibility.dialog.AccessibilityServiceTarget) {
                com.android.internal.accessibility.dialog.AccessibilityServiceTarget accessibilityServiceTarget = (com.android.internal.accessibility.dialog.AccessibilityServiceTarget) accessibilityTarget;
                if (sendRestrictedDialogIntentIfNeeded(accessibilityTarget)) {
                    return;
                }
                if (((android.view.accessibility.AccessibilityManager) getSystemService(android.view.accessibility.AccessibilityManager.class)).isAccessibilityServiceWarningRequired(accessibilityServiceTarget.getAccessibilityServiceInfo())) {
                    showPermissionDialogIfNeeded(this, accessibilityServiceTarget, i, this.mTargetAdapter);
                    return;
                }
            }
            if (accessibilityTarget instanceof com.android.internal.accessibility.dialog.AccessibilityActivityTarget) {
                com.android.internal.accessibility.dialog.AccessibilityActivityTarget accessibilityActivityTarget = (com.android.internal.accessibility.dialog.AccessibilityActivityTarget) accessibilityTarget;
                if (!accessibilityActivityTarget.isShortcutEnabled() && sendRestrictedDialogIntentIfNeeded(accessibilityActivityTarget)) {
                    return;
                }
            }
        } else if (!accessibilityTarget.isShortcutEnabled()) {
            boolean z = accessibilityTarget instanceof com.android.internal.accessibility.dialog.AccessibilityServiceTarget;
            if ((z || (accessibilityTarget instanceof com.android.internal.accessibility.dialog.AccessibilityActivityTarget)) && sendRestrictedDialogIntentIfNeeded(accessibilityTarget)) {
                return;
            }
            if (z) {
                showPermissionDialogIfNeeded(this, (com.android.internal.accessibility.dialog.AccessibilityServiceTarget) accessibilityTarget, i, this.mTargetAdapter);
                return;
            }
        }
        accessibilityTarget.onCheckedChanged(!accessibilityTarget.isShortcutEnabled());
        this.mTargetAdapter.notifyDataSetChanged();
    }

    private boolean sendRestrictedDialogIntentIfNeeded(com.android.internal.accessibility.dialog.AccessibilityTarget accessibilityTarget) {
        if (com.android.internal.accessibility.dialog.AccessibilityTargetHelper.isAccessibilityTargetAllowed(this, accessibilityTarget.getComponentName().getPackageName(), accessibilityTarget.getUid())) {
            return false;
        }
        com.android.internal.accessibility.dialog.AccessibilityTargetHelper.sendRestrictedDialogIntent(this, accessibilityTarget.getComponentName().getPackageName(), accessibilityTarget.getUid());
        return true;
    }

    private void showPermissionDialogIfNeeded(final android.content.Context context, final com.android.internal.accessibility.dialog.AccessibilityServiceTarget accessibilityServiceTarget, final int i, final com.android.internal.accessibility.dialog.ShortcutTargetAdapter shortcutTargetAdapter) {
        if (this.mPermissionDialog != null) {
            return;
        }
        if (android.view.accessibility.Flags.cleanupAccessibilityWarningDialog()) {
            this.mPermissionDialog = com.android.internal.accessibility.dialog.AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(context, accessibilityServiceTarget.getAccessibilityServiceInfo(), new android.view.View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view) {
                    com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$showPermissionDialogIfNeeded$1(accessibilityServiceTarget, shortcutTargetAdapter, view);
                }
            }, new android.view.View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view) {
                    com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$showPermissionDialogIfNeeded$2(accessibilityServiceTarget, view);
                }
            }, new android.view.View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view) {
                    com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$showPermissionDialogIfNeeded$3(i, context, accessibilityServiceTarget, shortcutTargetAdapter, view);
                }
            });
            this.mPermissionDialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(android.content.DialogInterface dialogInterface) {
                    com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$showPermissionDialogIfNeeded$4(dialogInterface);
                }
            });
        } else {
            this.mPermissionDialog = new android.app.AlertDialog.Builder(context).setView(com.android.internal.accessibility.dialog.AccessibilityTargetHelper.createEnableDialogContentView(context, accessibilityServiceTarget, new android.view.View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view) {
                    com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$showPermissionDialogIfNeeded$5(shortcutTargetAdapter, view);
                }
            }, new android.view.View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda11
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view) {
                    com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$showPermissionDialogIfNeeded$6(view);
                }
            })).setOnDismissListener(new android.content.DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda12
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(android.content.DialogInterface dialogInterface) {
                    com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$showPermissionDialogIfNeeded$7(dialogInterface);
                }
            }).create();
        }
        this.mPermissionDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$1(com.android.internal.accessibility.dialog.AccessibilityServiceTarget accessibilityServiceTarget, com.android.internal.accessibility.dialog.ShortcutTargetAdapter shortcutTargetAdapter, android.view.View view) {
        accessibilityServiceTarget.onCheckedChanged(true);
        shortcutTargetAdapter.notifyDataSetChanged();
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$2(com.android.internal.accessibility.dialog.AccessibilityServiceTarget accessibilityServiceTarget, android.view.View view) {
        accessibilityServiceTarget.onCheckedChanged(false);
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$3(int i, android.content.Context context, com.android.internal.accessibility.dialog.AccessibilityServiceTarget accessibilityServiceTarget, com.android.internal.accessibility.dialog.ShortcutTargetAdapter shortcutTargetAdapter, android.view.View view) {
        this.mTargets.remove(i);
        context.getPackageManager().getPackageInstaller().uninstall(accessibilityServiceTarget.getComponentName().getPackageName(), (android.content.IntentSender) null);
        shortcutTargetAdapter.notifyDataSetChanged();
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$4(android.content.DialogInterface dialogInterface) {
        this.mPermissionDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$5(com.android.internal.accessibility.dialog.ShortcutTargetAdapter shortcutTargetAdapter, android.view.View view) {
        this.mPermissionDialog.dismiss();
        shortcutTargetAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$6(android.view.View view) {
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$7(android.content.DialogInterface dialogInterface) {
        this.mPermissionDialog = null;
    }

    private void onDoneButtonClicked() {
        this.mTargets.clear();
        this.mTargets.addAll(com.android.internal.accessibility.dialog.AccessibilityTargetHelper.getTargets(this, 1));
        if (this.mTargets.isEmpty()) {
            this.mMenuDialog.dismiss();
            return;
        }
        this.mTargetAdapter.setShortcutMenuMode(0);
        this.mTargetAdapter.notifyDataSetChanged();
        this.mMenuDialog.getButton(-1).lambda$setTextAsync$0(getString(com.android.internal.R.string.edit_accessibility_shortcut_menu_button));
        updateDialogListeners();
    }

    private void onEditButtonClicked() {
        this.mTargets.clear();
        this.mTargets.addAll(com.android.internal.accessibility.dialog.AccessibilityTargetHelper.getInstalledTargets(this, 1));
        this.mTargetAdapter.setShortcutMenuMode(1);
        this.mTargetAdapter.notifyDataSetChanged();
        this.mMenuDialog.getButton(-1).lambda$setTextAsync$0(getString(com.android.internal.R.string.done_accessibility_shortcut_menu_button));
        updateDialogListeners();
    }

    private void updateDialogListeners() {
        boolean z = this.mTargetAdapter.getShortcutMenuMode() == 1;
        this.mMenuDialog.setTitle(getString(z ? com.android.internal.R.string.accessibility_edit_shortcut_menu_volume_title : com.android.internal.R.string.accessibility_select_shortcut_menu_title));
        this.mMenuDialog.getButton(-1).setOnClickListener(z ? new android.view.View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$updateDialogListeners$8(view);
            }
        } : new android.view.View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$updateDialogListeners$9(view);
            }
        });
        this.mMenuDialog.getListView().setOnItemClickListener(z ? new android.widget.AdapterView.OnItemClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda2
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
                com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.onTargetChecked(adapterView, view, i, j);
            }
        } : new android.widget.AdapterView.OnItemClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda3
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
                com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.onTargetSelected(adapterView, view, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogListeners$8(android.view.View view) {
        onDoneButtonClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogListeners$9(android.view.View view) {
        onEditButtonClicked();
    }

    public android.app.AlertDialog getMenuDialog() {
        return this.mMenuDialog;
    }

    public android.app.Dialog getPermissionDialog() {
        return this.mPermissionDialog;
    }

    private android.app.AlertDialog createMenuDialog() {
        android.app.KeyguardManager keyguardManager;
        android.app.AlertDialog.Builder onDismissListener = new android.app.AlertDialog.Builder(this).setTitle(getString(com.android.internal.R.string.accessibility_select_shortcut_menu_title)).setAdapter(this.mTargetAdapter, null).setOnDismissListener(new android.content.DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(android.content.DialogInterface dialogInterface) {
                com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.this.lambda$createMenuDialog$10(dialogInterface);
            }
        });
        boolean isUserSetupCompleted = com.android.internal.accessibility.util.AccessibilityUtils.isUserSetupCompleted(this);
        boolean z = false;
        if (android.view.accessibility.Flags.allowShortcutChooserOnLockscreen() && (keyguardManager = (android.app.KeyguardManager) getSystemService(android.app.KeyguardManager.class)) != null && keyguardManager.isKeyguardLocked()) {
            z = true;
            isUserSetupCompleted = false;
        }
        if (isUserSetupCompleted) {
            onDismissListener.setPositiveButton(getString(com.android.internal.R.string.edit_accessibility_shortcut_menu_button), (android.content.DialogInterface.OnClickListener) null);
        }
        android.app.AlertDialog create = onDismissListener.create();
        if (z) {
            create.getWindow().addFlags(524288);
        }
        return create;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createMenuDialog$10(android.content.DialogInterface dialogInterface) {
        finish();
    }
}
