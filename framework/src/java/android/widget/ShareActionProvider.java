package android.widget;

/* loaded from: classes4.dex */
public class ShareActionProvider extends android.view.ActionProvider {
    private static final int DEFAULT_INITIAL_ACTIVITY_COUNT = 4;
    public static final java.lang.String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
    private final android.content.Context mContext;
    private int mMaxShownActivityCount;
    private android.widget.ActivityChooserModel.OnChooseActivityListener mOnChooseActivityListener;
    private final android.widget.ShareActionProvider.ShareMenuItemOnMenuItemClickListener mOnMenuItemClickListener;
    private android.widget.ShareActionProvider.OnShareTargetSelectedListener mOnShareTargetSelectedListener;
    private java.lang.String mShareHistoryFileName;

    public interface OnShareTargetSelectedListener {
        boolean onShareTargetSelected(android.widget.ShareActionProvider shareActionProvider, android.content.Intent intent);
    }

    public ShareActionProvider(android.content.Context context) {
        super(context);
        this.mMaxShownActivityCount = 4;
        this.mOnMenuItemClickListener = new android.widget.ShareActionProvider.ShareMenuItemOnMenuItemClickListener();
        this.mShareHistoryFileName = DEFAULT_SHARE_HISTORY_FILE_NAME;
        this.mContext = context;
    }

    public void setOnShareTargetSelectedListener(android.widget.ShareActionProvider.OnShareTargetSelectedListener onShareTargetSelectedListener) {
        this.mOnShareTargetSelectedListener = onShareTargetSelectedListener;
        setActivityChooserPolicyIfNeeded();
    }

    @Override // android.view.ActionProvider
    public android.view.View onCreateActionView() {
        android.widget.ActivityChooserView activityChooserView = new android.widget.ActivityChooserView(this.mContext);
        if (!activityChooserView.isInEditMode()) {
            activityChooserView.setActivityChooserModel(android.widget.ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName));
        }
        android.util.TypedValue typedValue = new android.util.TypedValue();
        this.mContext.getTheme().resolveAttribute(16843897, typedValue, true);
        activityChooserView.setExpandActivityOverflowButtonDrawable(this.mContext.getDrawable(typedValue.resourceId));
        activityChooserView.setProvider(this);
        activityChooserView.setDefaultActionButtonContentDescription(com.android.internal.R.string.shareactionprovider_share_with_application);
        activityChooserView.setExpandActivityOverflowButtonContentDescription(com.android.internal.R.string.shareactionprovider_share_with);
        return activityChooserView;
    }

    @Override // android.view.ActionProvider
    public boolean hasSubMenu() {
        return true;
    }

    @Override // android.view.ActionProvider
    public void onPrepareSubMenu(android.view.SubMenu subMenu) {
        subMenu.clear();
        android.widget.ActivityChooserModel activityChooserModel = android.widget.ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        int activityCount = activityChooserModel.getActivityCount();
        int min = java.lang.Math.min(activityCount, this.mMaxShownActivityCount);
        for (int i = 0; i < min; i++) {
            android.content.pm.ResolveInfo activity = activityChooserModel.getActivity(i);
            subMenu.add(0, i, i, activity.loadLabel(packageManager)).setIcon(activity.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        }
        if (min < activityCount) {
            android.view.SubMenu addSubMenu = subMenu.addSubMenu(0, min, min, this.mContext.getString(com.android.internal.R.string.activity_chooser_view_see_all));
            for (int i2 = 0; i2 < activityCount; i2++) {
                android.content.pm.ResolveInfo activity2 = activityChooserModel.getActivity(i2);
                addSubMenu.add(0, i2, i2, activity2.loadLabel(packageManager)).setIcon(activity2.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
            }
        }
    }

    public void setShareHistoryFileName(java.lang.String str) {
        this.mShareHistoryFileName = str;
        setActivityChooserPolicyIfNeeded();
    }

    public void setShareIntent(android.content.Intent intent) {
        if (intent != null) {
            java.lang.String action = intent.getAction();
            if (android.content.Intent.ACTION_SEND.equals(action) || android.content.Intent.ACTION_SEND_MULTIPLE.equals(action)) {
                intent.addFlags(134742016);
            }
        }
        android.widget.ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setIntent(intent);
    }

    private class ShareMenuItemOnMenuItemClickListener implements android.view.MenuItem.OnMenuItemClickListener {
        private ShareMenuItemOnMenuItemClickListener() {
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(android.view.MenuItem menuItem) {
            android.content.Intent chooseActivity = android.widget.ActivityChooserModel.get(android.widget.ShareActionProvider.this.mContext, android.widget.ShareActionProvider.this.mShareHistoryFileName).chooseActivity(menuItem.getItemId());
            if (chooseActivity != null) {
                java.lang.String action = chooseActivity.getAction();
                if (android.content.Intent.ACTION_SEND.equals(action) || android.content.Intent.ACTION_SEND_MULTIPLE.equals(action)) {
                    chooseActivity.addFlags(134742016);
                }
                android.widget.ShareActionProvider.this.mContext.startActivity(chooseActivity);
                return true;
            }
            return true;
        }
    }

    private void setActivityChooserPolicyIfNeeded() {
        if (this.mOnShareTargetSelectedListener == null) {
            return;
        }
        if (this.mOnChooseActivityListener == null) {
            this.mOnChooseActivityListener = new android.widget.ShareActionProvider.ShareActivityChooserModelPolicy();
        }
        android.widget.ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setOnChooseActivityListener(this.mOnChooseActivityListener);
    }

    private class ShareActivityChooserModelPolicy implements android.widget.ActivityChooserModel.OnChooseActivityListener {
        private ShareActivityChooserModelPolicy() {
        }

        @Override // android.widget.ActivityChooserModel.OnChooseActivityListener
        public boolean onChooseActivity(android.widget.ActivityChooserModel activityChooserModel, android.content.Intent intent) {
            if (android.widget.ShareActionProvider.this.mOnShareTargetSelectedListener != null) {
                android.widget.ShareActionProvider.this.mOnShareTargetSelectedListener.onShareTargetSelected(android.widget.ShareActionProvider.this, intent);
                return false;
            }
            return false;
        }
    }
}
