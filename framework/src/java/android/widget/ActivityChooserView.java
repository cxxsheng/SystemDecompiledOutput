package android.widget;

/* loaded from: classes4.dex */
public class ActivityChooserView extends android.view.ViewGroup implements android.widget.ActivityChooserModel.ActivityChooserModelClient {
    private static final java.lang.String LOG_TAG = "ActivityChooserView";
    private final android.widget.LinearLayout mActivityChooserContent;
    private final android.graphics.drawable.Drawable mActivityChooserContentBackground;
    private final android.widget.ActivityChooserView.ActivityChooserViewAdapter mAdapter;
    private final android.widget.ActivityChooserView.Callbacks mCallbacks;
    private int mDefaultActionButtonContentDescription;
    private final android.widget.FrameLayout mDefaultActivityButton;
    private final android.widget.ImageView mDefaultActivityButtonImage;
    private final android.widget.FrameLayout mExpandActivityOverflowButton;
    private final android.widget.ImageView mExpandActivityOverflowButtonImage;
    private int mInitialActivityCount;
    private boolean mIsAttachedToWindow;
    private boolean mIsSelectingDefaultActivity;
    private final int mListPopupMaxWidth;
    private android.widget.ListPopupWindow mListPopupWindow;
    private final android.database.DataSetObserver mModelDataSetOberver;
    private android.widget.PopupWindow.OnDismissListener mOnDismissListener;
    private final android.view.ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    android.view.ActionProvider mProvider;

    public ActivityChooserView(android.content.Context context) {
        this(context, null);
    }

    public ActivityChooserView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityChooserView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ActivityChooserView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mModelDataSetOberver = new android.database.DataSetObserver() { // from class: android.widget.ActivityChooserView.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                super.onChanged();
                android.widget.ActivityChooserView.this.mAdapter.notifyDataSetChanged();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                super.onInvalidated();
                android.widget.ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
            }
        };
        this.mOnGlobalLayoutListener = new android.view.ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.widget.ActivityChooserView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (android.widget.ActivityChooserView.this.isShowingPopup()) {
                    if (!android.widget.ActivityChooserView.this.isShown()) {
                        android.widget.ActivityChooserView.this.getListPopupWindow().dismiss();
                        return;
                    }
                    android.widget.ActivityChooserView.this.getListPopupWindow().show();
                    if (android.widget.ActivityChooserView.this.mProvider != null) {
                        android.widget.ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
                    }
                }
            }
        };
        this.mInitialActivityCount = 4;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ActivityChooserView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ActivityChooserView, attributeSet, obtainStyledAttributes, i, i2);
        this.mInitialActivityCount = obtainStyledAttributes.getInt(1, 4);
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        android.view.LayoutInflater.from(this.mContext).inflate(com.android.internal.R.layout.activity_chooser_view, (android.view.ViewGroup) this, true);
        this.mCallbacks = new android.widget.ActivityChooserView.Callbacks();
        this.mActivityChooserContent = (android.widget.LinearLayout) findViewById(com.android.internal.R.id.activity_chooser_view_content);
        this.mActivityChooserContentBackground = this.mActivityChooserContent.getBackground();
        this.mDefaultActivityButton = (android.widget.FrameLayout) findViewById(com.android.internal.R.id.default_activity_button);
        this.mDefaultActivityButton.setOnClickListener(this.mCallbacks);
        this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
        this.mDefaultActivityButtonImage = (android.widget.ImageView) this.mDefaultActivityButton.findViewById(com.android.internal.R.id.image);
        android.widget.FrameLayout frameLayout = (android.widget.FrameLayout) findViewById(com.android.internal.R.id.expand_activities_button);
        frameLayout.setOnClickListener(this.mCallbacks);
        frameLayout.setAccessibilityDelegate(new android.view.View.AccessibilityDelegate() { // from class: android.widget.ActivityChooserView.3
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setCanOpenPopup(true);
            }
        });
        frameLayout.setOnTouchListener(new android.widget.ForwardingListener(frameLayout) { // from class: android.widget.ActivityChooserView.4
            @Override // android.widget.ForwardingListener
            public com.android.internal.view.menu.ShowableListMenu getPopup() {
                return android.widget.ActivityChooserView.this.getListPopupWindow();
            }

            @Override // android.widget.ForwardingListener
            protected boolean onForwardingStarted() {
                android.widget.ActivityChooserView.this.showPopup();
                return true;
            }

            @Override // android.widget.ForwardingListener
            protected boolean onForwardingStopped() {
                android.widget.ActivityChooserView.this.dismissPopup();
                return true;
            }
        });
        this.mExpandActivityOverflowButton = frameLayout;
        this.mExpandActivityOverflowButtonImage = (android.widget.ImageView) frameLayout.findViewById(com.android.internal.R.id.image);
        this.mExpandActivityOverflowButtonImage.lambda$setImageURIAsync$2(drawable);
        this.mAdapter = new android.widget.ActivityChooserView.ActivityChooserViewAdapter();
        this.mAdapter.registerDataSetObserver(new android.database.DataSetObserver() { // from class: android.widget.ActivityChooserView.5
            @Override // android.database.DataSetObserver
            public void onChanged() {
                super.onChanged();
                android.widget.ActivityChooserView.this.updateAppearance();
            }
        });
        android.content.res.Resources resources = context.getResources();
        this.mListPopupMaxWidth = java.lang.Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(com.android.internal.R.dimen.config_prefDialogWidth));
    }

    @Override // android.widget.ActivityChooserModel.ActivityChooserModelClient
    public void setActivityChooserModel(android.widget.ActivityChooserModel activityChooserModel) {
        this.mAdapter.setDataModel(activityChooserModel);
        if (isShowingPopup()) {
            dismissPopup();
            showPopup();
        }
    }

    public void setExpandActivityOverflowButtonDrawable(android.graphics.drawable.Drawable drawable) {
        this.mExpandActivityOverflowButtonImage.lambda$setImageURIAsync$2(drawable);
    }

    public void setExpandActivityOverflowButtonContentDescription(int i) {
        this.mExpandActivityOverflowButtonImage.setContentDescription(this.mContext.getString(i));
    }

    public void setProvider(android.view.ActionProvider actionProvider) {
        this.mProvider = actionProvider;
    }

    public boolean showPopup() {
        if (isShowingPopup() || !this.mIsAttachedToWindow) {
            return false;
        }
        this.mIsSelectingDefaultActivity = false;
        showPopupUnchecked(this.mInitialActivityCount);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [boolean, int] */
    public void showPopupUnchecked(int i) {
        if (this.mAdapter.getDataModel() == null) {
            throw new java.lang.IllegalStateException("No data model. Did you call #setDataModel?");
        }
        getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        ?? r0 = this.mDefaultActivityButton.getVisibility() == 0 ? 1 : 0;
        int activityCount = this.mAdapter.getActivityCount();
        if (i != Integer.MAX_VALUE && activityCount > i + r0) {
            this.mAdapter.setShowFooterView(true);
            this.mAdapter.setMaxActivityCount(i - 1);
        } else {
            this.mAdapter.setShowFooterView(false);
            this.mAdapter.setMaxActivityCount(i);
        }
        android.widget.ListPopupWindow listPopupWindow = getListPopupWindow();
        if (!listPopupWindow.isShowing()) {
            if (this.mIsSelectingDefaultActivity || r0 == 0) {
                this.mAdapter.setShowDefaultActivity(true, r0);
            } else {
                this.mAdapter.setShowDefaultActivity(false, false);
            }
            listPopupWindow.setContentWidth(java.lang.Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth));
            listPopupWindow.show();
            if (this.mProvider != null) {
                this.mProvider.subUiVisibilityChanged(true);
            }
            listPopupWindow.getListView().setContentDescription(this.mContext.getString(com.android.internal.R.string.activitychooserview_choose_application));
            listPopupWindow.getListView().setSelector(new android.graphics.drawable.ColorDrawable(0));
        }
    }

    public boolean dismissPopup() {
        if (isShowingPopup()) {
            getListPopupWindow().dismiss();
            android.view.ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
                return true;
            }
            return true;
        }
        return true;
    }

    public boolean isShowingPopup() {
        return getListPopupWindow().isShowing();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        android.widget.ActivityChooserModel dataModel = this.mAdapter.getDataModel();
        if (dataModel != null) {
            dataModel.registerObserver(this.mModelDataSetOberver);
        }
        this.mIsAttachedToWindow = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        android.widget.ActivityChooserModel dataModel = this.mAdapter.getDataModel();
        if (dataModel != null) {
            dataModel.unregisterObserver(this.mModelDataSetOberver);
        }
        android.view.ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        }
        if (isShowingPopup()) {
            dismissPopup();
        }
        this.mIsAttachedToWindow = false;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        android.widget.LinearLayout linearLayout = this.mActivityChooserContent;
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            i2 = android.view.View.MeasureSpec.makeMeasureSpec(android.view.View.MeasureSpec.getSize(i2), 1073741824);
        }
        measureChild(linearLayout, i, i2);
        setMeasuredDimension(linearLayout.getMeasuredWidth(), linearLayout.getMeasuredHeight());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mActivityChooserContent.layout(0, 0, i3 - i, i4 - i2);
        if (!isShowingPopup()) {
            dismissPopup();
        }
    }

    public android.widget.ActivityChooserModel getDataModel() {
        return this.mAdapter.getDataModel();
    }

    public void setOnDismissListener(android.widget.PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setInitialActivityCount(int i) {
        this.mInitialActivityCount = i;
    }

    public void setDefaultActionButtonContentDescription(int i) {
        this.mDefaultActionButtonContentDescription = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.widget.ListPopupWindow getListPopupWindow() {
        if (this.mListPopupWindow == null) {
            this.mListPopupWindow = new android.widget.ListPopupWindow(getContext());
            this.mListPopupWindow.setAdapter(this.mAdapter);
            this.mListPopupWindow.setAnchorView(this);
            this.mListPopupWindow.setModal(true);
            this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
            this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
        }
        return this.mListPopupWindow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppearance() {
        if (this.mAdapter.getCount() > 0) {
            this.mExpandActivityOverflowButton.setEnabled(true);
        } else {
            this.mExpandActivityOverflowButton.setEnabled(false);
        }
        int activityCount = this.mAdapter.getActivityCount();
        int historySize = this.mAdapter.getHistorySize();
        if (activityCount == 1 || (activityCount > 1 && historySize > 0)) {
            this.mDefaultActivityButton.setVisibility(0);
            android.content.pm.ResolveInfo defaultActivity = this.mAdapter.getDefaultActivity();
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            this.mDefaultActivityButtonImage.lambda$setImageURIAsync$2(defaultActivity.loadIcon(packageManager));
            if (this.mDefaultActionButtonContentDescription != 0) {
                this.mDefaultActivityButton.setContentDescription(this.mContext.getString(this.mDefaultActionButtonContentDescription, defaultActivity.loadLabel(packageManager)));
            }
        } else {
            this.mDefaultActivityButton.setVisibility(8);
        }
        if (this.mDefaultActivityButton.getVisibility() == 0) {
            this.mActivityChooserContent.setBackground(this.mActivityChooserContentBackground);
        } else {
            this.mActivityChooserContent.setBackground(null);
        }
    }

    private class Callbacks implements android.widget.AdapterView.OnItemClickListener, android.view.View.OnClickListener, android.view.View.OnLongClickListener, android.widget.PopupWindow.OnDismissListener {
        private Callbacks() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
            switch (((android.widget.ActivityChooserView.ActivityChooserViewAdapter) adapterView.getAdapter()).getItemViewType(i)) {
                case 0:
                    android.widget.ActivityChooserView.this.dismissPopup();
                    if (android.widget.ActivityChooserView.this.mIsSelectingDefaultActivity) {
                        if (i > 0) {
                            android.widget.ActivityChooserView.this.mAdapter.getDataModel().setDefaultActivity(i);
                            return;
                        }
                        return;
                    }
                    if (!android.widget.ActivityChooserView.this.mAdapter.getShowDefaultActivity()) {
                        i++;
                    }
                    android.content.Intent chooseActivity = android.widget.ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(i);
                    if (chooseActivity != null) {
                        chooseActivity.addFlags(524288);
                        startActivity(chooseActivity, android.widget.ActivityChooserView.this.mAdapter.getDataModel().getActivity(i));
                        return;
                    }
                    return;
                case 1:
                    android.widget.ActivityChooserView.this.showPopupUnchecked(Integer.MAX_VALUE);
                    return;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (view != android.widget.ActivityChooserView.this.mDefaultActivityButton) {
                if (view == android.widget.ActivityChooserView.this.mExpandActivityOverflowButton) {
                    android.widget.ActivityChooserView.this.mIsSelectingDefaultActivity = false;
                    android.widget.ActivityChooserView.this.showPopupUnchecked(android.widget.ActivityChooserView.this.mInitialActivityCount);
                    return;
                }
                throw new java.lang.IllegalArgumentException();
            }
            android.widget.ActivityChooserView.this.dismissPopup();
            android.content.pm.ResolveInfo defaultActivity = android.widget.ActivityChooserView.this.mAdapter.getDefaultActivity();
            android.content.Intent chooseActivity = android.widget.ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(android.widget.ActivityChooserView.this.mAdapter.getDataModel().getActivityIndex(defaultActivity));
            if (chooseActivity != null) {
                chooseActivity.addFlags(524288);
                startActivity(chooseActivity, defaultActivity);
            }
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(android.view.View view) {
            if (view == android.widget.ActivityChooserView.this.mDefaultActivityButton) {
                if (android.widget.ActivityChooserView.this.mAdapter.getCount() > 0) {
                    android.widget.ActivityChooserView.this.mIsSelectingDefaultActivity = true;
                    android.widget.ActivityChooserView.this.showPopupUnchecked(android.widget.ActivityChooserView.this.mInitialActivityCount);
                }
                return true;
            }
            throw new java.lang.IllegalArgumentException();
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            notifyOnDismissListener();
            if (android.widget.ActivityChooserView.this.mProvider != null) {
                android.widget.ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
            }
        }

        private void notifyOnDismissListener() {
            if (android.widget.ActivityChooserView.this.mOnDismissListener != null) {
                android.widget.ActivityChooserView.this.mOnDismissListener.onDismiss();
            }
        }

        private void startActivity(android.content.Intent intent, android.content.pm.ResolveInfo resolveInfo) {
            try {
                android.widget.ActivityChooserView.this.mContext.startActivity(intent);
            } catch (java.lang.RuntimeException e) {
                java.lang.String string = android.widget.ActivityChooserView.this.mContext.getString(com.android.internal.R.string.activitychooserview_choose_application_error, resolveInfo.loadLabel(android.widget.ActivityChooserView.this.mContext.getPackageManager()));
                android.util.Log.e(android.widget.ActivityChooserView.LOG_TAG, string);
                android.widget.Toast.makeText(android.widget.ActivityChooserView.this.mContext, string, 0).show();
            }
        }
    }

    private class ActivityChooserViewAdapter extends android.widget.BaseAdapter {
        private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
        private static final int ITEM_VIEW_TYPE_COUNT = 3;
        private static final int ITEM_VIEW_TYPE_FOOTER = 1;
        public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
        public static final int MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE;
        private android.widget.ActivityChooserModel mDataModel;
        private boolean mHighlightDefaultActivity;
        private int mMaxActivityCount;
        private boolean mShowDefaultActivity;
        private boolean mShowFooterView;

        private ActivityChooserViewAdapter() {
            this.mMaxActivityCount = 4;
        }

        public void setDataModel(android.widget.ActivityChooserModel activityChooserModel) {
            android.widget.ActivityChooserModel dataModel = android.widget.ActivityChooserView.this.mAdapter.getDataModel();
            if (dataModel != null && android.widget.ActivityChooserView.this.isShown()) {
                dataModel.unregisterObserver(android.widget.ActivityChooserView.this.mModelDataSetOberver);
            }
            this.mDataModel = activityChooserModel;
            if (activityChooserModel != null && android.widget.ActivityChooserView.this.isShown()) {
                activityChooserModel.registerObserver(android.widget.ActivityChooserView.this.mModelDataSetOberver);
            }
            notifyDataSetChanged();
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getItemViewType(int i) {
            return (this.mShowFooterView && i == getCount() - 1) ? 1 : 0;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getViewTypeCount() {
            return 3;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            int activityCount = this.mDataModel.getActivityCount();
            if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                activityCount--;
            }
            int min = java.lang.Math.min(activityCount, this.mMaxActivityCount);
            if (this.mShowFooterView) {
                return min + 1;
            }
            return min;
        }

        @Override // android.widget.Adapter
        public java.lang.Object getItem(int i) {
            switch (getItemViewType(i)) {
                case 0:
                    if (!this.mShowDefaultActivity && this.mDataModel.getDefaultActivity() != null) {
                        i++;
                    }
                    return this.mDataModel.getActivity(i);
                case 1:
                    return null;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            switch (getItemViewType(i)) {
                case 0:
                    if (view == null || view.getId() != 16909211) {
                        view = android.view.LayoutInflater.from(android.widget.ActivityChooserView.this.getContext()).inflate(com.android.internal.R.layout.activity_chooser_view_list_item, viewGroup, false);
                    }
                    android.content.pm.PackageManager packageManager = android.widget.ActivityChooserView.this.mContext.getPackageManager();
                    android.widget.ImageView imageView = (android.widget.ImageView) view.findViewById(16908294);
                    android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) getItem(i);
                    imageView.lambda$setImageURIAsync$2(resolveInfo.loadIcon(packageManager));
                    ((android.widget.TextView) view.findViewById(16908310)).lambda$setTextAsync$0(resolveInfo.loadLabel(packageManager));
                    if (this.mShowDefaultActivity && i == 0 && this.mHighlightDefaultActivity) {
                        view.setActivated(true);
                    } else {
                        view.setActivated(false);
                    }
                    return view;
                case 1:
                    if (view == null || view.getId() != 1) {
                        android.view.View inflate = android.view.LayoutInflater.from(android.widget.ActivityChooserView.this.getContext()).inflate(com.android.internal.R.layout.activity_chooser_view_list_item, viewGroup, false);
                        inflate.setId(1);
                        ((android.widget.TextView) inflate.findViewById(16908310)).lambda$setTextAsync$0(android.widget.ActivityChooserView.this.mContext.getString(com.android.internal.R.string.activity_chooser_view_see_all));
                        return inflate;
                    }
                    return view;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
        }

        public int measureContentWidth() {
            int i = this.mMaxActivityCount;
            this.mMaxActivityCount = Integer.MAX_VALUE;
            int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
            int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
            int count = getCount();
            int i2 = 0;
            android.view.View view = null;
            for (int i3 = 0; i3 < count; i3++) {
                view = getView(i3, view, null);
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                i2 = java.lang.Math.max(i2, view.getMeasuredWidth());
            }
            this.mMaxActivityCount = i;
            return i2;
        }

        public void setMaxActivityCount(int i) {
            if (this.mMaxActivityCount != i) {
                this.mMaxActivityCount = i;
                notifyDataSetChanged();
            }
        }

        public android.content.pm.ResolveInfo getDefaultActivity() {
            return this.mDataModel.getDefaultActivity();
        }

        public void setShowFooterView(boolean z) {
            if (this.mShowFooterView != z) {
                this.mShowFooterView = z;
                notifyDataSetChanged();
            }
        }

        public int getActivityCount() {
            return this.mDataModel.getActivityCount();
        }

        public int getHistorySize() {
            return this.mDataModel.getHistorySize();
        }

        public android.widget.ActivityChooserModel getDataModel() {
            return this.mDataModel;
        }

        public void setShowDefaultActivity(boolean z, boolean z2) {
            if (this.mShowDefaultActivity != z || this.mHighlightDefaultActivity != z2) {
                this.mShowDefaultActivity = z;
                this.mHighlightDefaultActivity = z2;
                notifyDataSetChanged();
            }
        }

        public boolean getShowDefaultActivity() {
            return this.mShowDefaultActivity;
        }
    }
}
