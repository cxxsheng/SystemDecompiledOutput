package com.android.internal.app.chooser;

/* loaded from: classes4.dex */
public abstract class NotSelectableTargetInfo implements com.android.internal.app.chooser.ChooserTargetInfo {
    @Override // com.android.internal.app.chooser.TargetInfo
    public android.content.Intent getResolvedIntent() {
        return null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public android.content.ComponentName getResolvedComponentName() {
        return null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean start(android.app.Activity activity, android.os.Bundle bundle) {
        return false;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsCaller(com.android.internal.app.ResolverActivity resolverActivity, android.os.Bundle bundle, int i) {
        return false;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsUser(android.app.Activity activity, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        return false;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public android.content.pm.ResolveInfo getResolveInfo() {
        return null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public java.lang.CharSequence getDisplayLabel() {
        return null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public java.lang.CharSequence getExtendedInfo() {
        return null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public com.android.internal.app.chooser.TargetInfo cloneFilledIn(android.content.Intent intent, int i) {
        return null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public java.util.List<android.content.Intent> getAllSourceIntents() {
        return null;
    }

    @Override // com.android.internal.app.chooser.ChooserTargetInfo
    public float getModifiedScore() {
        return -0.1f;
    }

    @Override // com.android.internal.app.chooser.ChooserTargetInfo
    public android.service.chooser.ChooserTarget getChooserTarget() {
        return null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isSuspended() {
        return false;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isPinned() {
        return false;
    }
}
