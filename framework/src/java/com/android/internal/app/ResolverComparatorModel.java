package com.android.internal.app;

/* loaded from: classes4.dex */
interface ResolverComparatorModel {
    java.util.Comparator<android.content.pm.ResolveInfo> getComparator();

    float getScore(com.android.internal.app.chooser.TargetInfo targetInfo);

    void notifyOnTargetSelected(com.android.internal.app.chooser.TargetInfo targetInfo);
}
