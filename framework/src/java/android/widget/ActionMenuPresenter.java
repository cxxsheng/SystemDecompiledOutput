package android.widget;

/* loaded from: classes4.dex */
public class ActionMenuPresenter extends com.android.internal.view.menu.BaseMenuPresenter implements android.view.ActionProvider.SubUiVisibilityListener {
    private static final boolean ACTIONBAR_ANIMATIONS_ENABLED = false;
    private static final int ITEM_ANIMATION_DURATION = 150;
    private final android.util.SparseBooleanArray mActionButtonGroups;
    private android.widget.ActionMenuPresenter.ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private android.view.View.OnAttachStateChangeListener mAttachStateChangeListener;
    private boolean mExpandedActionViewsExclusive;
    private android.view.ViewTreeObserver.OnPreDrawListener mItemAnimationPreDrawListener;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    int mOpenSubMenuId;
    private android.widget.ActionMenuPresenter.OverflowMenuButton mOverflowButton;
    private android.widget.ActionMenuPresenter.OverflowPopup mOverflowPopup;
    private android.graphics.drawable.Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private android.widget.ActionMenuPresenter.ActionMenuPopupCallback mPopupCallback;
    final android.widget.ActionMenuPresenter.PopupPresenterCallback mPopupPresenterCallback;
    private android.util.SparseArray<android.widget.ActionMenuPresenter.MenuItemLayoutInfo> mPostLayoutItems;
    private android.widget.ActionMenuPresenter.OpenOverflowRunnable mPostedOpenRunnable;
    private android.util.SparseArray<android.widget.ActionMenuPresenter.MenuItemLayoutInfo> mPreLayoutItems;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private java.util.List<android.widget.ActionMenuPresenter.ItemAnimationInfo> mRunningItemAnimations;
    private boolean mStrictWidthLimit;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    public ActionMenuPresenter(android.content.Context context) {
        super(context, com.android.internal.R.layout.action_menu_layout, com.android.internal.R.layout.action_menu_item_layout);
        this.mActionButtonGroups = new android.util.SparseBooleanArray();
        this.mPopupPresenterCallback = new android.widget.ActionMenuPresenter.PopupPresenterCallback();
        this.mPreLayoutItems = new android.util.SparseArray<>();
        this.mPostLayoutItems = new android.util.SparseArray<>();
        this.mRunningItemAnimations = new java.util.ArrayList();
        this.mItemAnimationPreDrawListener = new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: android.widget.ActionMenuPresenter.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                android.widget.ActionMenuPresenter.this.computeMenuItemAnimationInfo(false);
                ((android.view.View) android.widget.ActionMenuPresenter.this.mMenuView).getViewTreeObserver().removeOnPreDrawListener(this);
                android.widget.ActionMenuPresenter.this.runItemAnimations();
                return true;
            }
        };
        this.mAttachStateChangeListener = new android.view.View.OnAttachStateChangeListener() { // from class: android.widget.ActionMenuPresenter.2
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(android.view.View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(android.view.View view) {
                ((android.view.View) android.widget.ActionMenuPresenter.this.mMenuView).getViewTreeObserver().removeOnPreDrawListener(android.widget.ActionMenuPresenter.this.mItemAnimationPreDrawListener);
                android.widget.ActionMenuPresenter.this.mPreLayoutItems.clear();
                android.widget.ActionMenuPresenter.this.mPostLayoutItems.clear();
            }
        };
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void initForMenu(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        android.content.res.Resources resources = context.getResources();
        com.android.internal.view.ActionBarPolicy actionBarPolicy = com.android.internal.view.ActionBarPolicy.get(context);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = actionBarPolicy.showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = actionBarPolicy.getEmbeddedMenuWidthLimit();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = actionBarPolicy.getMaxActionButtons();
        }
        int i = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new android.widget.ActionMenuPresenter.OverflowMenuButton(this.mSystemContext);
                if (this.mPendingOverflowIconSet) {
                    this.mOverflowButton.lambda$setImageURIAsync$2(this.mPendingOverflowIcon);
                    this.mPendingOverflowIcon = null;
                    this.mPendingOverflowIconSet = false;
                }
                int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
                this.mOverflowButton.measure(makeMeasureSpec, makeMeasureSpec);
            }
            i -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = i;
        this.mMinCellSize = (int) (resources.getDisplayMetrics().density * 56.0f);
    }

    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        if (!this.mMaxItemsSet) {
            this.mMaxItems = com.android.internal.view.ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    public void setWidthLimit(int i, boolean z) {
        this.mWidthLimit = i;
        this.mStrictWidthLimit = z;
        this.mWidthLimitSet = true;
    }

    public void setReserveOverflow(boolean z) {
        this.mReserveOverflow = z;
        this.mReserveOverflowSet = true;
    }

    public void setItemLimit(int i) {
        this.mMaxItems = i;
        this.mMaxItemsSet = true;
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        this.mExpandedActionViewsExclusive = z;
    }

    public void setOverflowIcon(android.graphics.drawable.Drawable drawable) {
        if (this.mOverflowButton != null) {
            this.mOverflowButton.lambda$setImageURIAsync$2(drawable);
        } else {
            this.mPendingOverflowIconSet = true;
            this.mPendingOverflowIcon = drawable;
        }
    }

    public android.graphics.drawable.Drawable getOverflowIcon() {
        if (this.mOverflowButton != null) {
            return this.mOverflowButton.getDrawable();
        }
        if (this.mPendingOverflowIconSet) {
            return this.mPendingOverflowIcon;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public com.android.internal.view.menu.MenuView getMenuView(android.view.ViewGroup viewGroup) {
        java.lang.Object obj = this.mMenuView;
        com.android.internal.view.menu.MenuView menuView = super.getMenuView(viewGroup);
        if (obj != menuView) {
            ((android.widget.ActionMenuView) menuView).setPresenter(this);
            if (obj != null) {
                ((android.view.View) obj).removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            }
            ((android.view.View) menuView).addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
        return menuView;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public android.view.View getItemView(com.android.internal.view.menu.MenuItemImpl menuItemImpl, android.view.View view, android.view.ViewGroup viewGroup) {
        android.view.View actionView = menuItemImpl.getActionView();
        if (actionView == null || menuItemImpl.hasCollapsibleActionView()) {
            actionView = super.getItemView(menuItemImpl, view, viewGroup);
        }
        actionView.setVisibility(menuItemImpl.isActionViewExpanded() ? 8 : 0);
        android.widget.ActionMenuView actionMenuView = (android.widget.ActionMenuView) viewGroup;
        android.view.ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public void bindItemView(com.android.internal.view.menu.MenuItemImpl menuItemImpl, com.android.internal.view.menu.MenuView.ItemView itemView) {
        itemView.initialize(menuItemImpl, 0);
        com.android.internal.view.menu.ActionMenuItemView actionMenuItemView = (com.android.internal.view.menu.ActionMenuItemView) itemView;
        actionMenuItemView.setItemInvoker((android.widget.ActionMenuView) this.mMenuView);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new android.widget.ActionMenuPresenter.ActionMenuPopupCallback();
        }
        actionMenuItemView.setPopupCallback(this.mPopupCallback);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public boolean shouldIncludeItem(int i, com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void computeMenuItemAnimationInfo(boolean z) {
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mMenuView;
        int childCount = viewGroup.getChildCount();
        android.util.SparseArray<android.widget.ActionMenuPresenter.MenuItemLayoutInfo> sparseArray = z ? this.mPreLayoutItems : this.mPostLayoutItems;
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = viewGroup.getChildAt(i);
            int id = childAt.getId();
            if (id > 0 && childAt.getWidth() != 0 && childAt.getHeight() != 0) {
                sparseArray.put(id, new android.widget.ActionMenuPresenter.MenuItemLayoutInfo(childAt, z));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runItemAnimations() {
        android.animation.PropertyValuesHolder propertyValuesHolder;
        android.animation.ObjectAnimator ofPropertyValuesHolder;
        int i = 0;
        while (true) {
            float f = 1.0f;
            if (i >= this.mPreLayoutItems.size()) {
                break;
            }
            int keyAt = this.mPreLayoutItems.keyAt(i);
            final android.widget.ActionMenuPresenter.MenuItemLayoutInfo menuItemLayoutInfo = this.mPreLayoutItems.get(keyAt);
            int indexOfKey = this.mPostLayoutItems.indexOfKey(keyAt);
            if (indexOfKey >= 0) {
                android.widget.ActionMenuPresenter.MenuItemLayoutInfo valueAt = this.mPostLayoutItems.valueAt(indexOfKey);
                if (menuItemLayoutInfo.left == valueAt.left) {
                    propertyValuesHolder = null;
                } else {
                    propertyValuesHolder = android.animation.PropertyValuesHolder.ofFloat(android.view.View.TRANSLATION_X, menuItemLayoutInfo.left - valueAt.left, 0.0f);
                }
                android.animation.PropertyValuesHolder ofFloat = menuItemLayoutInfo.top != valueAt.top ? android.animation.PropertyValuesHolder.ofFloat(android.view.View.TRANSLATION_Y, menuItemLayoutInfo.top - valueAt.top, 0.0f) : null;
                if (propertyValuesHolder != null || ofFloat != null) {
                    for (int i2 = 0; i2 < this.mRunningItemAnimations.size(); i2++) {
                        android.widget.ActionMenuPresenter.ItemAnimationInfo itemAnimationInfo = this.mRunningItemAnimations.get(i2);
                        if (itemAnimationInfo.id == keyAt && itemAnimationInfo.animType == 0) {
                            itemAnimationInfo.animator.cancel();
                        }
                    }
                    if (propertyValuesHolder != null) {
                        if (ofFloat != null) {
                            ofPropertyValuesHolder = android.animation.ObjectAnimator.ofPropertyValuesHolder(valueAt.view, propertyValuesHolder, ofFloat);
                        } else {
                            ofPropertyValuesHolder = android.animation.ObjectAnimator.ofPropertyValuesHolder(valueAt.view, propertyValuesHolder);
                        }
                    } else {
                        ofPropertyValuesHolder = android.animation.ObjectAnimator.ofPropertyValuesHolder(valueAt.view, ofFloat);
                    }
                    ofPropertyValuesHolder.setDuration(150L);
                    ofPropertyValuesHolder.start();
                    this.mRunningItemAnimations.add(new android.widget.ActionMenuPresenter.ItemAnimationInfo(keyAt, valueAt, ofPropertyValuesHolder, 0));
                    ofPropertyValuesHolder.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.widget.ActionMenuPresenter.3
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(android.animation.Animator animator) {
                            for (int i3 = 0; i3 < android.widget.ActionMenuPresenter.this.mRunningItemAnimations.size(); i3++) {
                                if (((android.widget.ActionMenuPresenter.ItemAnimationInfo) android.widget.ActionMenuPresenter.this.mRunningItemAnimations.get(i3)).animator == animator) {
                                    android.widget.ActionMenuPresenter.this.mRunningItemAnimations.remove(i3);
                                    return;
                                }
                            }
                        }
                    });
                }
                this.mPostLayoutItems.remove(keyAt);
            } else {
                for (int i3 = 0; i3 < this.mRunningItemAnimations.size(); i3++) {
                    android.widget.ActionMenuPresenter.ItemAnimationInfo itemAnimationInfo2 = this.mRunningItemAnimations.get(i3);
                    if (itemAnimationInfo2.id == keyAt && itemAnimationInfo2.animType == 1) {
                        f = itemAnimationInfo2.menuItemLayoutInfo.view.getAlpha();
                        itemAnimationInfo2.animator.cancel();
                    }
                }
                android.animation.ObjectAnimator ofFloat2 = android.animation.ObjectAnimator.ofFloat(menuItemLayoutInfo.view, android.view.View.ALPHA, f, 0.0f);
                ((android.view.ViewGroup) this.mMenuView).getOverlay().add(menuItemLayoutInfo.view);
                ofFloat2.setDuration(150L);
                ofFloat2.start();
                this.mRunningItemAnimations.add(new android.widget.ActionMenuPresenter.ItemAnimationInfo(keyAt, menuItemLayoutInfo, ofFloat2, 2));
                ofFloat2.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.widget.ActionMenuPresenter.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(android.animation.Animator animator) {
                        int i4 = 0;
                        while (true) {
                            if (i4 >= android.widget.ActionMenuPresenter.this.mRunningItemAnimations.size()) {
                                break;
                            }
                            if (((android.widget.ActionMenuPresenter.ItemAnimationInfo) android.widget.ActionMenuPresenter.this.mRunningItemAnimations.get(i4)).animator != animator) {
                                i4++;
                            } else {
                                android.widget.ActionMenuPresenter.this.mRunningItemAnimations.remove(i4);
                                break;
                            }
                        }
                        ((android.view.ViewGroup) android.widget.ActionMenuPresenter.this.mMenuView).getOverlay().remove(menuItemLayoutInfo.view);
                    }
                });
            }
            i++;
        }
        for (int i4 = 0; i4 < this.mPostLayoutItems.size(); i4++) {
            int keyAt2 = this.mPostLayoutItems.keyAt(i4);
            int indexOfKey2 = this.mPostLayoutItems.indexOfKey(keyAt2);
            if (indexOfKey2 >= 0) {
                android.widget.ActionMenuPresenter.MenuItemLayoutInfo valueAt2 = this.mPostLayoutItems.valueAt(indexOfKey2);
                float f2 = 0.0f;
                for (int i5 = 0; i5 < this.mRunningItemAnimations.size(); i5++) {
                    android.widget.ActionMenuPresenter.ItemAnimationInfo itemAnimationInfo3 = this.mRunningItemAnimations.get(i5);
                    if (itemAnimationInfo3.id == keyAt2 && itemAnimationInfo3.animType == 2) {
                        f2 = itemAnimationInfo3.menuItemLayoutInfo.view.getAlpha();
                        itemAnimationInfo3.animator.cancel();
                    }
                }
                android.animation.ObjectAnimator ofFloat3 = android.animation.ObjectAnimator.ofFloat(valueAt2.view, android.view.View.ALPHA, f2, 1.0f);
                ofFloat3.start();
                ofFloat3.setDuration(150L);
                this.mRunningItemAnimations.add(new android.widget.ActionMenuPresenter.ItemAnimationInfo(keyAt2, valueAt2, ofFloat3, 1));
                ofFloat3.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.widget.ActionMenuPresenter.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(android.animation.Animator animator) {
                        for (int i6 = 0; i6 < android.widget.ActionMenuPresenter.this.mRunningItemAnimations.size(); i6++) {
                            if (((android.widget.ActionMenuPresenter.ItemAnimationInfo) android.widget.ActionMenuPresenter.this.mRunningItemAnimations.get(i6)).animator == animator) {
                                android.widget.ActionMenuPresenter.this.mRunningItemAnimations.remove(i6);
                                return;
                            }
                        }
                    }
                });
            }
        }
        this.mPreLayoutItems.clear();
        this.mPostLayoutItems.clear();
    }

    private void setupItemAnimations() {
        computeMenuItemAnimationInfo(true);
        ((android.view.View) this.mMenuView).getViewTreeObserver().addOnPreDrawListener(this.mItemAnimationPreDrawListener);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        super.updateMenuView(z);
        ((android.view.View) this.mMenuView).requestLayout();
        boolean z2 = false;
        if (this.mMenu != null) {
            java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> actionItems = this.mMenu.getActionItems();
            int size = actionItems.size();
            for (int i = 0; i < size; i++) {
                android.view.ActionProvider actionProvider = actionItems.get(i).getActionProvider();
                if (actionProvider != null) {
                    actionProvider.setSubUiVisibilityListener(this);
                }
            }
        }
        java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> nonActionItems = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        if (this.mReserveOverflow && nonActionItems != null) {
            int size2 = nonActionItems.size();
            if (size2 == 1) {
                z2 = !nonActionItems.get(0).isActionViewExpanded();
            } else if (size2 > 0) {
                z2 = true;
            }
        }
        if (z2) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new android.widget.ActionMenuPresenter.OverflowMenuButton(this.mSystemContext);
            }
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mOverflowButton.getParent();
            if (viewGroup != this.mMenuView) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.mOverflowButton);
                }
                android.widget.ActionMenuView actionMenuView = (android.widget.ActionMenuView) this.mMenuView;
                actionMenuView.addView(this.mOverflowButton, actionMenuView.generateOverflowButtonLayoutParams());
            }
        } else if (this.mOverflowButton != null && this.mOverflowButton.getParent() == this.mMenuView) {
            ((android.view.ViewGroup) this.mMenuView).removeView(this.mOverflowButton);
        }
        ((android.widget.ActionMenuView) this.mMenuView).setOverflowReserved(this.mReserveOverflow);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public boolean filterLeftoverView(android.view.ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) == this.mOverflowButton) {
            return false;
        }
        return super.filterLeftoverView(viewGroup, i);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(com.android.internal.view.menu.SubMenuBuilder subMenuBuilder) {
        boolean z = false;
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        com.android.internal.view.menu.SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
        while (subMenuBuilder2.getParentMenu() != this.mMenu) {
            subMenuBuilder2 = (com.android.internal.view.menu.SubMenuBuilder) subMenuBuilder2.getParentMenu();
        }
        android.view.View findViewForItem = findViewForItem(subMenuBuilder2.getItem());
        if (findViewForItem == null) {
            return false;
        }
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        int size = subMenuBuilder.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            android.view.MenuItem item = subMenuBuilder.getItem(i);
            if (!item.isVisible() || item.getIcon() == null) {
                i++;
            } else {
                z = true;
                break;
            }
        }
        this.mActionButtonPopup = new android.widget.ActionMenuPresenter.ActionButtonSubmenu(this.mContext, subMenuBuilder, findViewForItem);
        this.mActionButtonPopup.setForceShowIcon(z);
        this.mActionButtonPopup.show();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private android.view.View findViewForItem(android.view.MenuItem menuItem) {
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof com.android.internal.view.menu.MenuView.ItemView) && ((com.android.internal.view.menu.MenuView.ItemView) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    public boolean showOverflowMenu() {
        if (this.mReserveOverflow && !isOverflowMenuShowing() && this.mMenu != null && this.mMenuView != null && this.mPostedOpenRunnable == null && !this.mMenu.getNonActionItems().isEmpty()) {
            this.mPostedOpenRunnable = new android.widget.ActionMenuPresenter.OpenOverflowRunnable(new android.widget.ActionMenuPresenter.OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true));
            ((android.view.View) this.mMenuView).post(this.mPostedOpenRunnable);
            super.onSubMenuSelected(null);
            return true;
        }
        return false;
    }

    public boolean hideOverflowMenu() {
        if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
            ((android.view.View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
            this.mPostedOpenRunnable = null;
            return true;
        }
        android.widget.ActionMenuPresenter.OverflowPopup overflowPopup = this.mOverflowPopup;
        if (overflowPopup != null) {
            overflowPopup.dismiss();
            return true;
        }
        return false;
    }

    public boolean dismissPopupMenus() {
        return hideOverflowMenu() | hideSubMenus();
    }

    public boolean hideSubMenus() {
        if (this.mActionButtonPopup != null) {
            this.mActionButtonPopup.dismiss();
            return true;
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        return this.mOverflowPopup != null && this.mOverflowPopup.isShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPostedOpenRunnable != null || isOverflowMenuShowing();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public boolean flagActionItems() {
        java.util.ArrayList<com.android.internal.view.menu.MenuItemImpl> arrayList;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        boolean z2;
        android.widget.ActionMenuPresenter actionMenuPresenter = this;
        android.view.View view = null;
        int i6 = 0;
        if (actionMenuPresenter.mMenu != null) {
            arrayList = actionMenuPresenter.mMenu.getVisibleItems();
            i = arrayList.size();
        } else {
            arrayList = null;
            i = 0;
        }
        int i7 = actionMenuPresenter.mMaxItems;
        int i8 = actionMenuPresenter.mActionItemWidthLimit;
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) actionMenuPresenter.mMenuView;
        boolean z3 = false;
        int i9 = 0;
        int i10 = 0;
        for (int i11 = 0; i11 < i; i11++) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl = arrayList.get(i11);
            if (menuItemImpl.requiresActionButton()) {
                i9++;
            } else if (menuItemImpl.requestsActionButton()) {
                i10++;
            } else {
                z3 = true;
            }
            if (actionMenuPresenter.mExpandedActionViewsExclusive && menuItemImpl.isActionViewExpanded()) {
                i7 = 0;
            }
        }
        if (actionMenuPresenter.mReserveOverflow && (z3 || i10 + i9 > i7)) {
            i7--;
        }
        int i12 = i7 - i9;
        android.util.SparseBooleanArray sparseBooleanArray = actionMenuPresenter.mActionButtonGroups;
        sparseBooleanArray.clear();
        if (!actionMenuPresenter.mStrictWidthLimit) {
            i2 = 0;
            i3 = 0;
        } else {
            i2 = i8 / actionMenuPresenter.mMinCellSize;
            i3 = actionMenuPresenter.mMinCellSize + ((i8 % actionMenuPresenter.mMinCellSize) / i2);
        }
        int i13 = 0;
        int i14 = 0;
        while (i13 < i) {
            com.android.internal.view.menu.MenuItemImpl menuItemImpl2 = arrayList.get(i13);
            if (menuItemImpl2.requiresActionButton()) {
                android.view.View itemView = actionMenuPresenter.getItemView(menuItemImpl2, view, viewGroup);
                if (actionMenuPresenter.mStrictWidthLimit) {
                    i2 -= android.widget.ActionMenuView.measureChildForCells(itemView, i3, i2, makeMeasureSpec, i6);
                } else {
                    itemView.measure(makeMeasureSpec, makeMeasureSpec);
                }
                int measuredWidth = itemView.getMeasuredWidth();
                i8 -= measuredWidth;
                if (i14 == 0) {
                    i14 = measuredWidth;
                }
                int groupId = menuItemImpl2.getGroupId();
                if (groupId == 0) {
                    z2 = true;
                } else {
                    z2 = true;
                    sparseBooleanArray.put(groupId, true);
                }
                menuItemImpl2.setIsActionButton(z2);
                i5 = i6;
                i4 = i;
            } else if (menuItemImpl2.requestsActionButton()) {
                int groupId2 = menuItemImpl2.getGroupId();
                boolean z4 = sparseBooleanArray.get(groupId2);
                boolean z5 = (i12 > 0 || z4) && i8 > 0 && (!actionMenuPresenter.mStrictWidthLimit || i2 > 0);
                if (z5) {
                    boolean z6 = z5;
                    i4 = i;
                    android.view.View itemView2 = actionMenuPresenter.getItemView(menuItemImpl2, null, viewGroup);
                    if (actionMenuPresenter.mStrictWidthLimit) {
                        int measureChildForCells = android.widget.ActionMenuView.measureChildForCells(itemView2, i3, i2, makeMeasureSpec, 0);
                        i2 -= measureChildForCells;
                        if (measureChildForCells == 0) {
                            z6 = false;
                        }
                        z = z6;
                    } else {
                        itemView2.measure(makeMeasureSpec, makeMeasureSpec);
                        z = z6;
                    }
                    int measuredWidth2 = itemView2.getMeasuredWidth();
                    i8 -= measuredWidth2;
                    if (i14 == 0) {
                        i14 = measuredWidth2;
                    }
                    if (actionMenuPresenter.mStrictWidthLimit) {
                        z5 = z & (i8 >= 0);
                    } else {
                        z5 = z & (i8 + i14 > 0);
                    }
                } else {
                    i4 = i;
                }
                if (z5 && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true);
                } else if (z4) {
                    sparseBooleanArray.put(groupId2, false);
                    for (int i15 = 0; i15 < i13; i15++) {
                        com.android.internal.view.menu.MenuItemImpl menuItemImpl3 = arrayList.get(i15);
                        if (menuItemImpl3.getGroupId() == groupId2) {
                            if (menuItemImpl3.isActionButton()) {
                                i12++;
                            }
                            menuItemImpl3.setIsActionButton(false);
                        }
                    }
                }
                if (z5) {
                    i12--;
                }
                menuItemImpl2.setIsActionButton(z5);
                i5 = 0;
            } else {
                i4 = i;
                i5 = 0;
                menuItemImpl2.setIsActionButton(false);
            }
            i13++;
            i6 = i5;
            i = i4;
            view = null;
            actionMenuPresenter = this;
        }
        return true;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        dismissPopupMenus();
        super.onCloseMenu(menuBuilder, z);
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public android.os.Parcelable onSaveInstanceState() {
        android.widget.ActionMenuPresenter.SavedState savedState = new android.widget.ActionMenuPresenter.SavedState();
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return savedState;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.view.MenuItem findItem;
        android.widget.ActionMenuPresenter.SavedState savedState = (android.widget.ActionMenuPresenter.SavedState) parcelable;
        if (savedState.openSubMenuId > 0 && (findItem = this.mMenu.findItem(savedState.openSubMenuId)) != null) {
            onSubMenuSelected((com.android.internal.view.menu.SubMenuBuilder) findItem.getSubMenu());
        }
    }

    @Override // android.view.ActionProvider.SubUiVisibilityListener
    public void onSubUiVisibilityChanged(boolean z) {
        if (z) {
            super.onSubMenuSelected(null);
        } else if (this.mMenu != null) {
            this.mMenu.close(false);
        }
    }

    public void setMenuView(android.widget.ActionMenuView actionMenuView) {
        if (actionMenuView != this.mMenuView) {
            if (this.mMenuView != null) {
                ((android.view.View) this.mMenuView).removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            }
            this.mMenuView = actionMenuView;
            actionMenuView.initialize(this.mMenu);
            actionMenuView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
    }

    private static class SavedState implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.widget.ActionMenuPresenter.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.ActionMenuPresenter.SavedState>() { // from class: android.widget.ActionMenuPresenter.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ActionMenuPresenter.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.ActionMenuPresenter.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ActionMenuPresenter.SavedState[] newArray(int i) {
                return new android.widget.ActionMenuPresenter.SavedState[i];
            }
        };
        public int openSubMenuId;

        SavedState() {
        }

        SavedState(android.os.Parcel parcel) {
            this.openSubMenuId = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.openSubMenuId);
        }
    }

    private class OverflowMenuButton extends android.widget.ImageButton implements android.widget.ActionMenuView.ActionMenuChildView {
        public OverflowMenuButton(android.content.Context context) {
            super(context, null, 16843510);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            setOnTouchListener(new android.widget.ForwardingListener(this) { // from class: android.widget.ActionMenuPresenter.OverflowMenuButton.1
                @Override // android.widget.ForwardingListener
                public com.android.internal.view.menu.ShowableListMenu getPopup() {
                    if (android.widget.ActionMenuPresenter.this.mOverflowPopup == null) {
                        return null;
                    }
                    return android.widget.ActionMenuPresenter.this.mOverflowPopup.getPopup();
                }

                @Override // android.widget.ForwardingListener
                public boolean onForwardingStarted() {
                    android.widget.ActionMenuPresenter.this.showOverflowMenu();
                    return true;
                }

                @Override // android.widget.ForwardingListener
                public boolean onForwardingStopped() {
                    if (android.widget.ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                        return false;
                    }
                    android.widget.ActionMenuPresenter.this.hideOverflowMenu();
                    return true;
                }
            });
        }

        @Override // android.view.View
        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            android.widget.ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }

        @Override // android.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerBefore() {
            return false;
        }

        @Override // android.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerAfter() {
            return false;
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
            accessibilityNodeInfo.setCanOpenPopup(true);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.widget.ImageView, android.view.View
        public boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            android.graphics.drawable.Drawable drawable = getDrawable();
            android.graphics.drawable.Drawable background = getBackground();
            if (drawable != null && background != null) {
                int width = getWidth();
                int height = getHeight();
                int max = java.lang.Math.max(width, height) / 2;
                int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                background.setHotspotBounds(paddingLeft - max, paddingTop - max, paddingLeft + max, paddingTop + max);
            }
            return frame;
        }
    }

    private class OverflowPopup extends com.android.internal.view.menu.MenuPopupHelper {
        public OverflowPopup(android.content.Context context, com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.View view, boolean z) {
            super(context, menuBuilder, view, z, 16843844);
            setGravity(android.view.Gravity.END);
            setPresenterCallback(android.widget.ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override // com.android.internal.view.menu.MenuPopupHelper
        protected void onDismiss() {
            if (android.widget.ActionMenuPresenter.this.mMenu != null) {
                android.widget.ActionMenuPresenter.this.mMenu.close();
            }
            android.widget.ActionMenuPresenter.this.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    private class ActionButtonSubmenu extends com.android.internal.view.menu.MenuPopupHelper {
        public ActionButtonSubmenu(android.content.Context context, com.android.internal.view.menu.SubMenuBuilder subMenuBuilder, android.view.View view) {
            super(context, subMenuBuilder, view, false, 16843844);
            if (!((com.android.internal.view.menu.MenuItemImpl) subMenuBuilder.getItem()).isActionButton()) {
                setAnchorView(android.widget.ActionMenuPresenter.this.mOverflowButton == null ? (android.view.View) android.widget.ActionMenuPresenter.this.mMenuView : android.widget.ActionMenuPresenter.this.mOverflowButton);
            }
            setPresenterCallback(android.widget.ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override // com.android.internal.view.menu.MenuPopupHelper
        protected void onDismiss() {
            android.widget.ActionMenuPresenter.this.mActionButtonPopup = null;
            android.widget.ActionMenuPresenter.this.mOpenSubMenuId = 0;
            super.onDismiss();
        }
    }

    private class PopupPresenterCallback implements com.android.internal.view.menu.MenuPresenter.Callback {
        private PopupPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            android.widget.ActionMenuPresenter.this.mOpenSubMenuId = ((com.android.internal.view.menu.SubMenuBuilder) menuBuilder).getItem().getItemId();
            com.android.internal.view.menu.MenuPresenter.Callback callback = android.widget.ActionMenuPresenter.this.getCallback();
            if (callback != null) {
                return callback.onOpenSubMenu(menuBuilder);
            }
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof com.android.internal.view.menu.SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            com.android.internal.view.menu.MenuPresenter.Callback callback = android.widget.ActionMenuPresenter.this.getCallback();
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }
    }

    private class OpenOverflowRunnable implements java.lang.Runnable {
        private android.widget.ActionMenuPresenter.OverflowPopup mPopup;

        public OpenOverflowRunnable(android.widget.ActionMenuPresenter.OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (android.widget.ActionMenuPresenter.this.mMenu != null) {
                android.widget.ActionMenuPresenter.this.mMenu.changeMenuMode();
            }
            android.view.View view = (android.view.View) android.widget.ActionMenuPresenter.this.mMenuView;
            if (view != null && view.getWindowToken() != null && this.mPopup.tryShow()) {
                android.widget.ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
            }
            android.widget.ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    private class ActionMenuPopupCallback extends com.android.internal.view.menu.ActionMenuItemView.PopupCallback {
        private ActionMenuPopupCallback() {
        }

        @Override // com.android.internal.view.menu.ActionMenuItemView.PopupCallback
        public com.android.internal.view.menu.ShowableListMenu getPopup() {
            if (android.widget.ActionMenuPresenter.this.mActionButtonPopup != null) {
                return android.widget.ActionMenuPresenter.this.mActionButtonPopup.getPopup();
            }
            return null;
        }
    }

    private static class MenuItemLayoutInfo {
        int left;
        int top;
        android.view.View view;

        MenuItemLayoutInfo(android.view.View view, boolean z) {
            this.left = view.getLeft();
            this.top = view.getTop();
            if (z) {
                this.left = (int) (this.left + view.getTranslationX());
                this.top = (int) (this.top + view.getTranslationY());
            }
            this.view = view;
        }
    }

    private static class ItemAnimationInfo {
        static final int FADE_IN = 1;
        static final int FADE_OUT = 2;
        static final int MOVE = 0;
        int animType;
        android.animation.Animator animator;
        int id;
        android.widget.ActionMenuPresenter.MenuItemLayoutInfo menuItemLayoutInfo;

        ItemAnimationInfo(int i, android.widget.ActionMenuPresenter.MenuItemLayoutInfo menuItemLayoutInfo, android.animation.Animator animator, int i2) {
            this.id = i;
            this.menuItemLayoutInfo = menuItemLayoutInfo;
            this.animator = animator;
            this.animType = i2;
        }
    }
}
