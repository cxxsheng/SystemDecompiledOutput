package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public final class IconMenuItemView extends android.widget.TextView implements com.android.internal.view.menu.MenuView.ItemView {
    private static final int NO_ALPHA = 255;
    private static java.lang.String sPrependShortcutLabel;
    private float mDisabledAlpha;
    private android.graphics.drawable.Drawable mIcon;
    private com.android.internal.view.menu.IconMenuView mIconMenuView;
    private com.android.internal.view.menu.MenuItemImpl mItemData;
    private com.android.internal.view.menu.MenuBuilder.ItemInvoker mItemInvoker;
    private android.graphics.Rect mPositionIconAvailable;
    private android.graphics.Rect mPositionIconOutput;
    private java.lang.String mShortcutCaption;
    private boolean mShortcutCaptionMode;
    private int mTextAppearance;
    private android.content.Context mTextAppearanceContext;

    public IconMenuItemView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPositionIconAvailable = new android.graphics.Rect();
        this.mPositionIconOutput = new android.graphics.Rect();
        if (sPrependShortcutLabel == null) {
            sPrependShortcutLabel = getResources().getString(com.android.internal.R.string.prepend_shortcut_label);
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.MenuView, i, i2);
        this.mDisabledAlpha = obtainStyledAttributes.getFloat(6, 0.8f);
        this.mTextAppearance = obtainStyledAttributes.getResourceId(1, -1);
        this.mTextAppearanceContext = context;
        obtainStyledAttributes.recycle();
    }

    public IconMenuItemView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public IconMenuItemView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    void initialize(java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable) {
        setClickable(true);
        setFocusable(true);
        if (this.mTextAppearance != -1) {
            setTextAppearance(this.mTextAppearanceContext, this.mTextAppearance);
        }
        setTitle(charSequence);
        setIcon(drawable);
        if (this.mItemData != null) {
            java.lang.CharSequence contentDescription = this.mItemData.getContentDescription();
            if (android.text.TextUtils.isEmpty(contentDescription)) {
                setContentDescription(charSequence);
            } else {
                setContentDescription(contentDescription);
            }
            setTooltipText(this.mItemData.getTooltipText());
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void initialize(com.android.internal.view.menu.MenuItemImpl menuItemImpl, int i) {
        this.mItemData = menuItemImpl;
        initialize(menuItemImpl.getTitleForItemView(this), menuItemImpl.getIcon());
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        setEnabled(menuItemImpl.isEnabled());
    }

    public void setItemData(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        this.mItemData = menuItemImpl;
    }

    @Override // android.view.View
    public boolean performClick() {
        if (super.performClick()) {
            return true;
        }
        if (this.mItemInvoker == null || !this.mItemInvoker.invokeItem(this.mItemData)) {
            return false;
        }
        playSoundEffect(0);
        return true;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setTitle(java.lang.CharSequence charSequence) {
        if (this.mShortcutCaptionMode) {
            setCaptionMode(true);
        } else if (charSequence != null) {
            lambda$setTextAsync$0(charSequence);
        }
    }

    void setCaptionMode(boolean z) {
        if (this.mItemData == null) {
            return;
        }
        this.mShortcutCaptionMode = z && this.mItemData.shouldShowShortcut();
        java.lang.CharSequence titleForItemView = this.mItemData.getTitleForItemView(this);
        if (this.mShortcutCaptionMode) {
            if (this.mShortcutCaption == null) {
                this.mShortcutCaption = this.mItemData.getShortcutLabel();
            }
            titleForItemView = this.mShortcutCaption;
        }
        lambda$setTextAsync$0(titleForItemView);
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setIcon(android.graphics.drawable.Drawable drawable) {
        this.mIcon = drawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            setCompoundDrawables(null, drawable, null, null);
            setGravity(81);
            requestLayout();
            return;
        }
        setCompoundDrawables(null, null, null, null);
        setGravity(17);
    }

    public void setItemInvoker(com.android.internal.view.menu.MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    @android.view.ViewDebug.CapturedViewProperty(retrieveReturn = true)
    public com.android.internal.view.menu.MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (this.mIconMenuView != null) {
            this.mIconMenuView.markStaleChildren();
        }
    }

    void setIconMenuView(com.android.internal.view.menu.IconMenuView iconMenuView) {
        this.mIconMenuView = iconMenuView;
    }

    @Override // android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mItemData != null && this.mIcon != null) {
            this.mIcon.setAlpha(!this.mItemData.isEnabled() && (isPressed() || !isFocused()) ? (int) (this.mDisabledAlpha * 255.0f) : 255);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        positionIcon();
    }

    @Override // android.widget.TextView
    protected void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        setLayoutParams(getTextAppropriateLayoutParams());
    }

    com.android.internal.view.menu.IconMenuView.LayoutParams getTextAppropriateLayoutParams() {
        com.android.internal.view.menu.IconMenuView.LayoutParams layoutParams = (com.android.internal.view.menu.IconMenuView.LayoutParams) getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new com.android.internal.view.menu.IconMenuView.LayoutParams(-1, -1);
        }
        layoutParams.desiredWidth = (int) android.text.Layout.getDesiredWidth(getText(), 0, getText().length(), getPaint(), getTextDirectionHeuristic());
        return layoutParams;
    }

    private void positionIcon() {
        if (this.mIcon == null) {
            return;
        }
        android.graphics.Rect rect = this.mPositionIconOutput;
        getLineBounds(0, rect);
        this.mPositionIconAvailable.set(0, 0, getWidth(), rect.top);
        android.view.Gravity.apply(8388627, this.mIcon.getIntrinsicWidth(), this.mIcon.getIntrinsicHeight(), this.mPositionIconAvailable, this.mPositionIconOutput, getLayoutDirection());
        this.mIcon.setBounds(this.mPositionIconOutput);
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setCheckable(boolean z) {
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setChecked(boolean z) {
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setShortcut(boolean z, char c) {
        if (this.mShortcutCaptionMode) {
            this.mShortcutCaption = null;
            setCaptionMode(true);
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return true;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }
}
