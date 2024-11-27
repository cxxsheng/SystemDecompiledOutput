package com.android.internal.app.chooser;

/* loaded from: classes4.dex */
public class DisplayResolveInfo implements com.android.internal.app.chooser.TargetInfo, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.app.chooser.DisplayResolveInfo> CREATOR = new android.os.Parcelable.Creator<com.android.internal.app.chooser.DisplayResolveInfo>() { // from class: com.android.internal.app.chooser.DisplayResolveInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.app.chooser.DisplayResolveInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.app.chooser.DisplayResolveInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.app.chooser.DisplayResolveInfo[] newArray(int i) {
            return new com.android.internal.app.chooser.DisplayResolveInfo[i];
        }
    };
    private android.graphics.drawable.Drawable mDisplayIcon;
    private java.lang.CharSequence mDisplayLabel;
    private java.lang.CharSequence mExtendedInfo;
    private boolean mIsSuspended;
    private boolean mPinned;
    private final android.content.pm.ResolveInfo mResolveInfo;
    private com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter mResolveInfoPresentationGetter;
    private final android.content.Intent mResolvedIntent;
    private final java.util.List<android.content.Intent> mSourceIntents;

    public DisplayResolveInfo(android.content.Intent intent, android.content.pm.ResolveInfo resolveInfo, android.content.Intent intent2, com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter) {
        this(intent, resolveInfo, null, null, intent2, resolveInfoPresentationGetter);
    }

    public DisplayResolveInfo(android.content.Intent intent, android.content.pm.ResolveInfo resolveInfo, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.content.Intent intent2, com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter) {
        this.mSourceIntents = new java.util.ArrayList();
        this.mPinned = false;
        this.mSourceIntents.add(intent);
        this.mResolveInfo = resolveInfo;
        this.mDisplayLabel = charSequence;
        this.mExtendedInfo = charSequence2;
        this.mResolveInfoPresentationGetter = resolveInfoPresentationGetter;
        android.content.Intent intent3 = new android.content.Intent(intent2);
        intent3.addFlags(50331648);
        android.content.pm.ActivityInfo activityInfo = this.mResolveInfo.activityInfo;
        intent3.setComponent(new android.content.ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
        this.mIsSuspended = (activityInfo.applicationInfo.flags & 1073741824) != 0;
        this.mResolvedIntent = intent3;
    }

    private DisplayResolveInfo(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo, android.content.Intent intent, int i, com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter) {
        this.mSourceIntents = new java.util.ArrayList();
        this.mPinned = false;
        this.mSourceIntents.addAll(displayResolveInfo.getAllSourceIntents());
        this.mResolveInfo = displayResolveInfo.mResolveInfo;
        this.mDisplayLabel = displayResolveInfo.mDisplayLabel;
        this.mDisplayIcon = displayResolveInfo.mDisplayIcon;
        this.mExtendedInfo = displayResolveInfo.mExtendedInfo;
        this.mResolvedIntent = new android.content.Intent(displayResolveInfo.mResolvedIntent);
        this.mResolvedIntent.fillIn(intent, i);
        this.mResolveInfoPresentationGetter = resolveInfoPresentationGetter;
    }

    DisplayResolveInfo(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        this.mSourceIntents = new java.util.ArrayList();
        this.mPinned = false;
        this.mSourceIntents.addAll(displayResolveInfo.getAllSourceIntents());
        this.mResolveInfo = displayResolveInfo.mResolveInfo;
        this.mDisplayLabel = displayResolveInfo.mDisplayLabel;
        this.mDisplayIcon = displayResolveInfo.mDisplayIcon;
        this.mExtendedInfo = displayResolveInfo.mExtendedInfo;
        this.mResolvedIntent = displayResolveInfo.mResolvedIntent;
        this.mResolveInfoPresentationGetter = displayResolveInfo.mResolveInfoPresentationGetter;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public android.content.pm.ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public java.lang.CharSequence getDisplayLabel() {
        if (this.mDisplayLabel == null && this.mResolveInfoPresentationGetter != null) {
            this.mDisplayLabel = this.mResolveInfoPresentationGetter.getLabel();
            this.mExtendedInfo = this.mResolveInfoPresentationGetter.getSubLabel();
        }
        return this.mDisplayLabel;
    }

    public boolean hasDisplayLabel() {
        return this.mDisplayLabel != null;
    }

    public void setDisplayLabel(java.lang.CharSequence charSequence) {
        this.mDisplayLabel = charSequence;
    }

    public void setExtendedInfo(java.lang.CharSequence charSequence) {
        this.mExtendedInfo = charSequence;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public android.graphics.drawable.Drawable getDisplayIcon(android.content.Context context) {
        return this.mDisplayIcon;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public com.android.internal.app.chooser.TargetInfo cloneFilledIn(android.content.Intent intent, int i) {
        return new com.android.internal.app.chooser.DisplayResolveInfo(this, intent, i, this.mResolveInfoPresentationGetter);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public java.util.List<android.content.Intent> getAllSourceIntents() {
        return this.mSourceIntents;
    }

    public void addAlternateSourceIntent(android.content.Intent intent) {
        this.mSourceIntents.add(intent);
    }

    public void setDisplayIcon(android.graphics.drawable.Drawable drawable) {
        this.mDisplayIcon = drawable;
    }

    public boolean hasDisplayIcon() {
        return this.mDisplayIcon != null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public java.lang.CharSequence getExtendedInfo() {
        return this.mExtendedInfo;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public android.content.Intent getResolvedIntent() {
        return this.mResolvedIntent;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public android.content.ComponentName getResolvedComponentName() {
        return new android.content.ComponentName(this.mResolveInfo.activityInfo.packageName, this.mResolveInfo.activityInfo.name);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean start(android.app.Activity activity, android.os.Bundle bundle) {
        activity.startActivity(this.mResolvedIntent, bundle);
        return true;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsCaller(com.android.internal.app.ResolverActivity resolverActivity, android.os.Bundle bundle, int i) {
        com.android.internal.app.chooser.TargetInfo.prepareIntentForCrossProfileLaunch(this.mResolvedIntent, i);
        resolverActivity.startActivityAsCaller(this.mResolvedIntent, bundle, false, i);
        return true;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsUser(android.app.Activity activity, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        com.android.internal.app.chooser.TargetInfo.prepareIntentForCrossProfileLaunch(this.mResolvedIntent, userHandle.getIdentifier());
        activity.startActivityAsUser(this.mResolvedIntent, bundle, userHandle);
        return false;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isSuspended() {
        return this.mIsSuspended;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isPinned() {
        return this.mPinned;
    }

    public void setPinned(boolean z) {
        this.mPinned = z;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mDisplayLabel);
        parcel.writeCharSequence(this.mExtendedInfo);
        parcel.writeParcelable(this.mResolvedIntent, 0);
        parcel.writeTypedList(this.mSourceIntents);
        parcel.writeBoolean(this.mIsSuspended);
        parcel.writeBoolean(this.mPinned);
        parcel.writeParcelable(this.mResolveInfo, 0);
    }

    private DisplayResolveInfo(android.os.Parcel parcel) {
        this.mSourceIntents = new java.util.ArrayList();
        this.mPinned = false;
        this.mDisplayLabel = parcel.readCharSequence();
        this.mExtendedInfo = parcel.readCharSequence();
        this.mResolvedIntent = (android.content.Intent) parcel.readParcelable(null, android.content.Intent.class);
        parcel.readTypedList(this.mSourceIntents, android.content.Intent.CREATOR);
        this.mIsSuspended = parcel.readBoolean();
        this.mPinned = parcel.readBoolean();
        this.mResolveInfo = (android.content.pm.ResolveInfo) parcel.readParcelable(null, android.content.pm.ResolveInfo.class);
    }
}
