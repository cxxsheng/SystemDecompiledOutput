package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
public class AccessibilityButtonChooserActivity extends android.app.Activity {
    private final java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> mTargets = new java.util.ArrayList();

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        int i;
        int i2;
        super.onCreate(bundle);
        setContentView(com.android.internal.R.layout.accessibility_button_chooser);
        com.android.internal.widget.ResolverDrawerLayout resolverDrawerLayout = (com.android.internal.widget.ResolverDrawerLayout) findViewById(com.android.internal.R.id.contentPanel);
        if (resolverDrawerLayout != null) {
            resolverDrawerLayout.setOnDismissedListener(new com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity$$ExternalSyntheticLambda0
                @Override // com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener
                public final void onDismissed() {
                    com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity.this.finish();
                }
            });
        }
        java.lang.String string = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_BUTTON_TARGET_COMPONENT);
        boolean isTouchExplorationEnabled = ((android.view.accessibility.AccessibilityManager) getSystemService(android.view.accessibility.AccessibilityManager.class)).isTouchExplorationEnabled();
        boolean z = 2 == getResources().getInteger(com.android.internal.R.integer.config_navBarInteractionMode);
        if (z) {
            android.widget.TextView textView = (android.widget.TextView) findViewById(com.android.internal.R.id.accessibility_button_prompt_prologue);
            if (isTouchExplorationEnabled) {
                i2 = com.android.internal.R.string.accessibility_gesture_3finger_prompt_text;
            } else {
                i2 = com.android.internal.R.string.accessibility_gesture_prompt_text;
            }
            textView.setText(i2);
        }
        if (android.text.TextUtils.isEmpty(string)) {
            android.widget.TextView textView2 = (android.widget.TextView) findViewById(com.android.internal.R.id.accessibility_button_prompt);
            if (z) {
                if (isTouchExplorationEnabled) {
                    i = com.android.internal.R.string.accessibility_gesture_3finger_instructional_text;
                } else {
                    i = com.android.internal.R.string.accessibility_gesture_instructional_text;
                }
                textView2.setText(i);
            }
            textView2.setVisibility(0);
        }
        this.mTargets.addAll(com.android.internal.accessibility.dialog.AccessibilityTargetHelper.getTargets(this, 0));
        android.widget.GridView gridView = (android.widget.GridView) findViewById(com.android.internal.R.id.accessibility_button_chooser_grid);
        gridView.setAdapter((android.widget.ListAdapter) new com.android.internal.accessibility.dialog.ButtonTargetAdapter(this.mTargets));
        gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i3, long j) {
                com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity.this.lambda$onCreate$0(adapterView, view, i3, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
        java.lang.String id = this.mTargets.get(i).getId();
        if (id.equals("com.android.server.accessibility.MagnificationController")) {
            id = com.android.internal.accessibility.AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToString();
        }
        com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityButtonLongPressStatus(android.content.ComponentName.unflattenFromString(id));
        android.provider.Settings.Secure.putString(getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_BUTTON_TARGET_COMPONENT, this.mTargets.get(i).getId());
        finish();
    }
}
