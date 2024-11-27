package com.android.internal.app.chooser;

/* loaded from: classes4.dex */
public interface TargetInfo {
    com.android.internal.app.chooser.TargetInfo cloneFilledIn(android.content.Intent intent, int i);

    java.util.List<android.content.Intent> getAllSourceIntents();

    android.graphics.drawable.Drawable getDisplayIcon(android.content.Context context);

    java.lang.CharSequence getDisplayLabel();

    java.lang.CharSequence getExtendedInfo();

    android.content.pm.ResolveInfo getResolveInfo();

    android.content.ComponentName getResolvedComponentName();

    android.content.Intent getResolvedIntent();

    boolean isPinned();

    boolean isSuspended();

    boolean start(android.app.Activity activity, android.os.Bundle bundle);

    boolean startAsCaller(com.android.internal.app.ResolverActivity resolverActivity, android.os.Bundle bundle, int i);

    boolean startAsUser(android.app.Activity activity, android.os.Bundle bundle, android.os.UserHandle userHandle);

    static void prepareIntentForCrossProfileLaunch(android.content.Intent intent, int i) {
        int myUserId = android.os.UserHandle.myUserId();
        if (i != myUserId) {
            intent.fixUris(myUserId);
        }
    }
}
