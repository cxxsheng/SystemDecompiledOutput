package com.android.internal.notification;

/* loaded from: classes4.dex */
public final class NotificationAccessConfirmationActivityContract {
    public static final java.lang.String EXTRA_COMPONENT_NAME = "component_name";
    public static final java.lang.String EXTRA_USER_ID = "user_id";

    public static android.content.Intent launcherIntent(android.content.Context context, int i, android.content.ComponentName componentName) {
        return new android.content.Intent().setComponent(android.content.ComponentName.unflattenFromString(context.getString(com.android.internal.R.string.config_notificationAccessConfirmationActivity))).putExtra("user_id", i).putExtra(EXTRA_COMPONENT_NAME, componentName);
    }
}
