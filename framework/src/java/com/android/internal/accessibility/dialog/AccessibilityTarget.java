package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
public abstract class AccessibilityTarget implements com.android.internal.accessibility.dialog.TargetOperations, com.android.internal.accessibility.dialog.OnTargetSelectedListener, com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener {
    private android.content.ComponentName mComponentName;
    private android.content.Context mContext;
    private int mFragmentType;
    private android.graphics.drawable.Drawable mIcon;
    private java.lang.String mId;
    private java.lang.String mKey;
    private java.lang.CharSequence mLabel;
    private boolean mShortcutEnabled;
    private int mShortcutType;
    private java.lang.CharSequence mStateDescription;
    private int mUid;

    public AccessibilityTarget(android.content.Context context, int i, int i2, boolean z, java.lang.String str, int i3, java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable, java.lang.String str2) {
        this.mContext = context;
        this.mShortcutType = i;
        this.mFragmentType = i2;
        this.mShortcutEnabled = z;
        this.mId = str;
        this.mUid = i3;
        this.mComponentName = android.content.ComponentName.unflattenFromString(str);
        this.mLabel = charSequence;
        this.mIcon = drawable;
        this.mKey = str2;
    }

    @Override // com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(com.android.internal.accessibility.dialog.TargetAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mCheckBoxView.setEnabled(true);
        viewHolder.mIconView.setEnabled(true);
        viewHolder.mLabelView.setEnabled(true);
        viewHolder.mStatusView.setEnabled(true);
        boolean z = i == 1;
        viewHolder.mCheckBoxView.setChecked(z && isShortcutEnabled());
        viewHolder.mCheckBoxView.setVisibility(z ? 0 : 8);
        viewHolder.mIconView.setImageDrawable(getIcon());
        viewHolder.mLabelView.lambda$setTextAsync$0(getLabel());
        viewHolder.mStatusView.setVisibility(8);
    }

    @Override // com.android.internal.accessibility.dialog.OnTargetSelectedListener
    public void onSelected() {
        android.view.accessibility.AccessibilityManager accessibilityManager = (android.view.accessibility.AccessibilityManager) getContext().getSystemService(android.view.accessibility.AccessibilityManager.class);
        switch (getShortcutType()) {
            case 0:
                accessibilityManager.notifyAccessibilityButtonClicked(getContext().getDisplayId(), getId());
                return;
            case 1:
                accessibilityManager.performAccessibilityShortcut(getId());
                return;
            default:
                throw new java.lang.IllegalStateException("Unexpected shortcut type");
        }
    }

    @Override // com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener
    public void onCheckedChanged(boolean z) {
        setShortcutEnabled(z);
        if (z) {
            com.android.internal.accessibility.util.ShortcutUtils.optInValueToSettings(getContext(), com.android.internal.accessibility.util.ShortcutUtils.convertToUserType(getShortcutType()), getId());
        } else {
            com.android.internal.accessibility.util.ShortcutUtils.optOutValueFromSettings(getContext(), com.android.internal.accessibility.util.ShortcutUtils.convertToUserType(getShortcutType()), getId());
        }
    }

    public void setStateDescription(java.lang.CharSequence charSequence) {
        this.mStateDescription = charSequence;
    }

    public java.lang.CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    public void setShortcutEnabled(boolean z) {
        this.mShortcutEnabled = z;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    public int getShortcutType() {
        return this.mShortcutType;
    }

    public int getFragmentType() {
        return this.mFragmentType;
    }

    public boolean isShortcutEnabled() {
        return this.mShortcutEnabled;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public int getUid() {
        return this.mUid;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public android.graphics.drawable.Drawable getIcon() {
        return this.mIcon;
    }

    public java.lang.String getKey() {
        return this.mKey;
    }
}
