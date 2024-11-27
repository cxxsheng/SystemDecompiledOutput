package com.android.internal.app.chooser;

/* loaded from: classes4.dex */
public interface ChooserTargetInfo extends com.android.internal.app.chooser.TargetInfo {
    android.service.chooser.ChooserTarget getChooserTarget();

    float getModifiedScore();

    default boolean isSimilar(com.android.internal.app.chooser.ChooserTargetInfo chooserTargetInfo) {
        if (chooserTargetInfo == null) {
            return false;
        }
        android.service.chooser.ChooserTarget chooserTarget = getChooserTarget();
        android.service.chooser.ChooserTarget chooserTarget2 = chooserTargetInfo.getChooserTarget();
        if (chooserTarget == null || chooserTarget2 == null || !chooserTarget.getComponentName().equals(chooserTarget2.getComponentName()) || !android.text.TextUtils.equals(getDisplayLabel(), chooserTargetInfo.getDisplayLabel()) || !android.text.TextUtils.equals(getExtendedInfo(), chooserTargetInfo.getExtendedInfo())) {
            return false;
        }
        return true;
    }
}
