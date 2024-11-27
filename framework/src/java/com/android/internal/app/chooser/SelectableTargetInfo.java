package com.android.internal.app.chooser;

/* loaded from: classes4.dex */
public final class SelectableTargetInfo implements com.android.internal.app.chooser.ChooserTargetInfo {
    private static final java.lang.String TAG = "SelectableTargetInfo";
    private final android.content.pm.ResolveInfo mBackupResolveInfo;
    private java.lang.CharSequence mBadgeContentDescription;
    private android.graphics.drawable.Drawable mBadgeIcon;
    private final android.service.chooser.ChooserTarget mChooserTarget;
    private final android.content.Context mContext;
    private android.graphics.drawable.Drawable mDisplayIcon;
    private final java.lang.String mDisplayLabel;
    private final int mFillInFlags;
    private final android.content.Intent mFillInIntent;
    private final boolean mIsPinned;
    private boolean mIsSuspended;
    private final float mModifiedScore;
    private final android.content.pm.PackageManager mPm;
    private final com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator mSelectableTargetInfoCommunicator;
    private android.content.pm.ShortcutInfo mShortcutInfo;
    private final com.android.internal.app.chooser.DisplayResolveInfo mSourceInfo;

    public interface SelectableTargetInfoCommunicator {
        android.content.Intent getReferrerFillInIntent();

        android.content.Intent getTargetIntent();

        com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter makePresentationGetter(android.content.pm.ActivityInfo activityInfo);
    }

    public SelectableTargetInfo(android.content.Context context, com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo, android.service.chooser.ChooserTarget chooserTarget, float f, com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator selectableTargetInfoCommunicator, android.content.pm.ShortcutInfo shortcutInfo) {
        boolean z;
        android.content.pm.ResolveInfo resolveInfo;
        android.content.pm.ActivityInfo activityInfo;
        this.mBadgeIcon = null;
        this.mIsSuspended = false;
        this.mContext = context;
        this.mSourceInfo = displayResolveInfo;
        this.mChooserTarget = chooserTarget;
        this.mModifiedScore = f;
        this.mPm = this.mContext.getPackageManager();
        this.mSelectableTargetInfoCommunicator = selectableTargetInfoCommunicator;
        this.mShortcutInfo = shortcutInfo;
        if (shortcutInfo == null || !shortcutInfo.isPinned()) {
            z = false;
        } else {
            z = true;
        }
        this.mIsPinned = z;
        if (displayResolveInfo != null && (resolveInfo = displayResolveInfo.getResolveInfo()) != null && (activityInfo = resolveInfo.activityInfo) != null && activityInfo.applicationInfo != null) {
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            this.mBadgeIcon = packageManager.getApplicationIcon(activityInfo.applicationInfo);
            this.mBadgeContentDescription = packageManager.getApplicationLabel(activityInfo.applicationInfo);
            this.mIsSuspended = (activityInfo.applicationInfo.flags & 1073741824) != 0;
        }
        if (displayResolveInfo == null) {
            this.mBackupResolveInfo = this.mContext.getPackageManager().resolveActivity(getResolvedIntent(), 0);
        } else {
            this.mBackupResolveInfo = null;
        }
        this.mFillInIntent = null;
        this.mFillInFlags = 0;
        this.mDisplayLabel = sanitizeDisplayLabel(chooserTarget.getTitle());
    }

    private SelectableTargetInfo(com.android.internal.app.chooser.SelectableTargetInfo selectableTargetInfo, android.content.Intent intent, int i) {
        this.mBadgeIcon = null;
        this.mIsSuspended = false;
        this.mContext = selectableTargetInfo.mContext;
        this.mPm = selectableTargetInfo.mPm;
        this.mSelectableTargetInfoCommunicator = selectableTargetInfo.mSelectableTargetInfoCommunicator;
        this.mSourceInfo = selectableTargetInfo.mSourceInfo;
        this.mBackupResolveInfo = selectableTargetInfo.mBackupResolveInfo;
        this.mChooserTarget = selectableTargetInfo.mChooserTarget;
        this.mBadgeIcon = selectableTargetInfo.mBadgeIcon;
        this.mBadgeContentDescription = selectableTargetInfo.mBadgeContentDescription;
        synchronized (selectableTargetInfo) {
            this.mShortcutInfo = selectableTargetInfo.mShortcutInfo;
            this.mDisplayIcon = selectableTargetInfo.mDisplayIcon;
        }
        this.mFillInIntent = intent;
        this.mFillInFlags = i;
        this.mModifiedScore = selectableTargetInfo.mModifiedScore;
        this.mIsPinned = selectableTargetInfo.mIsPinned;
        this.mDisplayLabel = sanitizeDisplayLabel(this.mChooserTarget.getTitle());
    }

    private java.lang.String sanitizeDisplayLabel(java.lang.CharSequence charSequence) {
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(charSequence);
        spannableStringBuilder.clearSpans();
        return spannableStringBuilder.toString();
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isSuspended() {
        return this.mIsSuspended;
    }

    public com.android.internal.app.chooser.DisplayResolveInfo getDisplayResolveInfo() {
        return this.mSourceInfo;
    }

    public boolean loadIcon() {
        android.content.pm.ShortcutInfo shortcutInfo;
        android.graphics.drawable.Drawable drawable;
        synchronized (this) {
            shortcutInfo = this.mShortcutInfo;
            drawable = this.mDisplayIcon;
        }
        boolean z = drawable == null && shortcutInfo != null;
        if (z) {
            android.graphics.drawable.Drawable chooserTargetIconDrawable = getChooserTargetIconDrawable(this.mChooserTarget, shortcutInfo);
            synchronized (this) {
                this.mDisplayIcon = chooserTargetIconDrawable;
                this.mShortcutInfo = null;
            }
        }
        return z;
    }

    private android.graphics.drawable.Drawable getChooserTargetIconDrawable(android.service.chooser.ChooserTarget chooserTarget, android.content.pm.ShortcutInfo shortcutInfo) {
        android.graphics.drawable.Drawable drawable;
        android.content.pm.ActivityInfo activityInfo;
        android.graphics.drawable.Icon icon = chooserTarget.getIcon();
        if (icon != null) {
            drawable = icon.loadDrawable(this.mContext);
        } else if (shortcutInfo == null) {
            drawable = null;
        } else {
            drawable = ((android.content.pm.LauncherApps) this.mContext.getSystemService(android.content.Context.LAUNCHER_APPS_SERVICE)).getShortcutIconDrawable(shortcutInfo, 0);
        }
        if (drawable == null) {
            return null;
        }
        try {
            activityInfo = this.mPm.getActivityInfo(chooserTarget.getComponentName(), 0);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Could not find activity associated with ChooserTarget");
            activityInfo = null;
        }
        if (activityInfo == null) {
            return null;
        }
        android.graphics.Bitmap iconBitmap = this.mSelectableTargetInfoCommunicator.makePresentationGetter(activityInfo).getIconBitmap(null);
        com.android.internal.app.SimpleIconFactory obtain = com.android.internal.app.SimpleIconFactory.obtain(this.mContext);
        android.graphics.Bitmap createAppBadgedIconBitmap = obtain.createAppBadgedIconBitmap(drawable, iconBitmap);
        obtain.recycle();
        return new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), createAppBadgedIconBitmap);
    }

    @Override // com.android.internal.app.chooser.ChooserTargetInfo
    public float getModifiedScore() {
        return this.mModifiedScore;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public android.content.Intent getResolvedIntent() {
        if (this.mSourceInfo != null) {
            return this.mSourceInfo.getResolvedIntent();
        }
        android.content.Intent intent = new android.content.Intent(this.mSelectableTargetInfoCommunicator.getTargetIntent());
        intent.setComponent(this.mChooserTarget.getComponentName());
        intent.putExtras(this.mChooserTarget.getIntentExtras());
        return intent;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public android.content.ComponentName getResolvedComponentName() {
        if (this.mSourceInfo != null) {
            return this.mSourceInfo.getResolvedComponentName();
        }
        if (this.mBackupResolveInfo != null) {
            return new android.content.ComponentName(this.mBackupResolveInfo.activityInfo.packageName, this.mBackupResolveInfo.activityInfo.name);
        }
        return null;
    }

    private android.content.Intent getBaseIntentToSend() {
        android.content.Intent resolvedIntent = getResolvedIntent();
        if (resolvedIntent == null) {
            android.util.Log.e(TAG, "ChooserTargetInfo: no base intent available to send");
            return resolvedIntent;
        }
        android.content.Intent intent = new android.content.Intent(resolvedIntent);
        if (this.mFillInIntent != null) {
            intent.fillIn(this.mFillInIntent, this.mFillInFlags);
        }
        intent.fillIn(this.mSelectableTargetInfoCommunicator.getReferrerFillInIntent(), 0);
        return intent;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean start(android.app.Activity activity, android.os.Bundle bundle) {
        throw new java.lang.RuntimeException("ChooserTargets should be started as caller.");
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsCaller(com.android.internal.app.ResolverActivity resolverActivity, android.os.Bundle bundle, int i) {
        android.content.Intent baseIntentToSend = getBaseIntentToSend();
        boolean z = false;
        if (baseIntentToSend == null) {
            return false;
        }
        baseIntentToSend.setComponent(this.mChooserTarget.getComponentName());
        baseIntentToSend.putExtras(this.mChooserTarget.getIntentExtras());
        com.android.internal.app.chooser.TargetInfo.prepareIntentForCrossProfileLaunch(baseIntentToSend, i);
        if (this.mSourceInfo != null && this.mSourceInfo.getResolvedComponentName().getPackageName().equals(this.mChooserTarget.getComponentName().getPackageName())) {
            z = true;
        }
        resolverActivity.startActivityAsCaller(baseIntentToSend, bundle, z, i);
        return true;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsUser(android.app.Activity activity, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        throw new java.lang.RuntimeException("ChooserTargets should be started as caller.");
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public android.content.pm.ResolveInfo getResolveInfo() {
        return this.mSourceInfo != null ? this.mSourceInfo.getResolveInfo() : this.mBackupResolveInfo;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public java.lang.CharSequence getDisplayLabel() {
        return this.mDisplayLabel;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public java.lang.CharSequence getExtendedInfo() {
        return null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public synchronized android.graphics.drawable.Drawable getDisplayIcon(android.content.Context context) {
        return this.mDisplayIcon;
    }

    public synchronized boolean hasDisplayIcon() {
        return this.mDisplayIcon != null;
    }

    @Override // com.android.internal.app.chooser.ChooserTargetInfo
    public android.service.chooser.ChooserTarget getChooserTarget() {
        return this.mChooserTarget;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public com.android.internal.app.chooser.TargetInfo cloneFilledIn(android.content.Intent intent, int i) {
        return new com.android.internal.app.chooser.SelectableTargetInfo(this, intent, i);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public java.util.List<android.content.Intent> getAllSourceIntents() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (this.mSourceInfo != null) {
            arrayList.add(this.mSourceInfo.getAllSourceIntents().get(0));
        }
        return arrayList;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isPinned() {
        return this.mIsPinned;
    }
}
