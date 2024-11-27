package com.android.internal.app.chooser;

/* loaded from: classes4.dex */
public class MultiDisplayResolveInfo extends com.android.internal.app.chooser.DisplayResolveInfo {
    final com.android.internal.app.chooser.DisplayResolveInfo mBaseInfo;
    private int mSelected;
    java.util.ArrayList<com.android.internal.app.chooser.DisplayResolveInfo> mTargetInfos;

    public MultiDisplayResolveInfo(java.lang.String str, com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        super(displayResolveInfo);
        this.mTargetInfos = new java.util.ArrayList<>();
        this.mSelected = -1;
        this.mBaseInfo = displayResolveInfo;
        this.mTargetInfos.add(displayResolveInfo);
    }

    @Override // com.android.internal.app.chooser.DisplayResolveInfo, com.android.internal.app.chooser.TargetInfo
    public java.lang.CharSequence getExtendedInfo() {
        return null;
    }

    public void addTarget(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        this.mTargetInfos.add(displayResolveInfo);
    }

    public java.util.ArrayList<com.android.internal.app.chooser.DisplayResolveInfo> getTargets() {
        return this.mTargetInfos;
    }

    public void setSelected(int i) {
        this.mSelected = i;
    }

    public com.android.internal.app.chooser.DisplayResolveInfo getSelectedTarget() {
        if (hasSelected()) {
            return this.mTargetInfos.get(this.mSelected);
        }
        return null;
    }

    public boolean hasSelected() {
        return this.mSelected >= 0;
    }

    @Override // com.android.internal.app.chooser.DisplayResolveInfo, com.android.internal.app.chooser.TargetInfo
    public boolean start(android.app.Activity activity, android.os.Bundle bundle) {
        return this.mTargetInfos.get(this.mSelected).start(activity, bundle);
    }

    @Override // com.android.internal.app.chooser.DisplayResolveInfo, com.android.internal.app.chooser.TargetInfo
    public boolean startAsCaller(com.android.internal.app.ResolverActivity resolverActivity, android.os.Bundle bundle, int i) {
        return this.mTargetInfos.get(this.mSelected).startAsCaller(resolverActivity, bundle, i);
    }

    @Override // com.android.internal.app.chooser.DisplayResolveInfo, com.android.internal.app.chooser.TargetInfo
    public boolean startAsUser(android.app.Activity activity, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        return this.mTargetInfos.get(this.mSelected).startAsUser(activity, bundle, userHandle);
    }
}
