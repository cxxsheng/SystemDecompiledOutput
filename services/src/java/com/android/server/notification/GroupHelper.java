package com.android.server.notification;

/* loaded from: classes2.dex */
public class GroupHelper {
    private static final int ALL_CHILDREN_FLAG = 16;
    private static final int ANY_CHILDREN_FLAGS = 34;
    protected static final java.lang.String AUTOGROUP_KEY = "ranker_group";
    protected static final int BASE_FLAGS = 1792;
    protected static final int FLAG_INVALID = -1;
    private static final java.lang.String TAG = "GroupHelper";
    private final int mAutoGroupAtCount;
    private final com.android.server.notification.GroupHelper.Callback mCallback;
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;

    @com.android.internal.annotations.GuardedBy({"mUngroupedNotifications"})
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, com.android.server.notification.GroupHelper.NotificationAttributes>> mUngroupedNotifications = new android.util.ArrayMap<>();

    protected interface Callback {
        void addAutoGroup(java.lang.String str);

        void addAutoGroupSummary(int i, java.lang.String str, java.lang.String str2, com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes);

        void removeAutoGroup(java.lang.String str);

        void removeAutoGroupSummary(int i, java.lang.String str);

        void updateAutogroupSummary(int i, java.lang.String str, com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes);
    }

    public GroupHelper(android.content.Context context, android.content.pm.PackageManager packageManager, int i, com.android.server.notification.GroupHelper.Callback callback) {
        this.mAutoGroupAtCount = i;
        this.mCallback = callback;
        this.mContext = context;
        this.mPackageManager = packageManager;
    }

    private java.lang.String generatePackageKey(int i, java.lang.String str) {
        return i + "|" + str;
    }

    @com.android.internal.annotations.GuardedBy({"mUngroupedNotifications"})
    @com.android.internal.annotations.VisibleForTesting
    protected int getAutogroupSummaryFlags(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, com.android.server.notification.GroupHelper.NotificationAttributes> arrayMap) {
        boolean z = arrayMap.size() > 0;
        int i = 0;
        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            if (!hasAnyFlag(arrayMap.valueAt(i2).flags, 16)) {
                z = false;
            }
            if (hasAnyFlag(arrayMap.valueAt(i2).flags, 34)) {
                i |= arrayMap.valueAt(i2).flags & 34;
            }
        }
        return (z ? 16 : 0) | 1792 | i;
    }

    private boolean hasAnyFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public void onNotificationPosted(android.service.notification.StatusBarNotification statusBarNotification, boolean z) {
        try {
            if (!statusBarNotification.isAppGroup()) {
                maybeGroup(statusBarNotification, z);
            } else {
                maybeUngroup(statusBarNotification, false, statusBarNotification.getUserId());
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failure processing new notification", e);
        }
    }

    public void onNotificationRemoved(android.service.notification.StatusBarNotification statusBarNotification) {
        try {
            maybeUngroup(statusBarNotification, true, statusBarNotification.getUserId());
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error processing canceled notification", e);
        }
    }

    private void maybeGroup(android.service.notification.StatusBarNotification statusBarNotification, boolean z) {
        int autogroupSummaryFlags;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        synchronized (this.mUngroupedNotifications) {
            java.lang.String generatePackageKey = generatePackageKey(statusBarNotification.getUserId(), statusBarNotification.getPackageName());
            android.util.ArrayMap<java.lang.String, com.android.server.notification.GroupHelper.NotificationAttributes> orDefault = this.mUngroupedNotifications.getOrDefault(generatePackageKey, new android.util.ArrayMap<>());
            orDefault.put(statusBarNotification.getKey(), new com.android.server.notification.GroupHelper.NotificationAttributes(statusBarNotification.getNotification().flags, statusBarNotification.getNotification().getSmallIcon(), statusBarNotification.getNotification().color, statusBarNotification.getNotification().visibility));
            this.mUngroupedNotifications.put(generatePackageKey, orDefault);
            if (orDefault.size() >= this.mAutoGroupAtCount || z) {
                autogroupSummaryFlags = getAutogroupSummaryFlags(orDefault);
                arrayList.addAll(orDefault.keySet());
                arrayList2.addAll(orDefault.values());
            } else {
                autogroupSummaryFlags = 0;
            }
        }
        if (arrayList.size() > 0) {
            if (z) {
                com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes = new com.android.server.notification.GroupHelper.NotificationAttributes(autogroupSummaryFlags, statusBarNotification.getNotification().getSmallIcon(), statusBarNotification.getNotification().color, 0);
                com.android.server.notification.Flags.autogroupSummaryIconUpdate();
                this.mCallback.updateAutogroupSummary(statusBarNotification.getUserId(), statusBarNotification.getPackageName(), notificationAttributes);
            } else {
                android.graphics.drawable.Icon smallIcon = statusBarNotification.getNotification().getSmallIcon();
                int i = statusBarNotification.getNotification().color;
                com.android.server.notification.Flags.autogroupSummaryIconUpdate();
                this.mCallback.addAutoGroupSummary(statusBarNotification.getUserId(), statusBarNotification.getPackageName(), statusBarNotification.getKey(), new com.android.server.notification.GroupHelper.NotificationAttributes(autogroupSummaryFlags, smallIcon, i, 0));
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mCallback.addAutoGroup((java.lang.String) it.next());
            }
        }
    }

    private void maybeUngroup(android.service.notification.StatusBarNotification statusBarNotification, boolean z, int i) {
        boolean z2;
        boolean z3;
        boolean z4;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mUngroupedNotifications) {
            try {
                android.util.ArrayMap<java.lang.String, com.android.server.notification.GroupHelper.NotificationAttributes> orDefault = this.mUngroupedNotifications.getOrDefault(generatePackageKey(statusBarNotification.getUserId(), statusBarNotification.getPackageName()), new android.util.ArrayMap<>());
                if (orDefault.size() == 0) {
                    return;
                }
                int i2 = -1;
                if (!orDefault.containsKey(statusBarNotification.getKey())) {
                    z2 = false;
                    z3 = false;
                    z4 = false;
                } else {
                    z4 = true;
                    if (!hasAnyFlag(orDefault.remove(statusBarNotification.getKey()).flags, 34)) {
                        z3 = false;
                    } else {
                        i2 = getAutogroupSummaryFlags(orDefault);
                        z3 = true;
                    }
                    if (!z && statusBarNotification.getOverrideGroupKey() != null) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (orDefault.size() != 0) {
                        arrayList.addAll(orDefault.values());
                        z4 = false;
                    }
                }
                if (z4) {
                    this.mCallback.removeAutoGroupSummary(i, statusBarNotification.getPackageName());
                } else {
                    com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes = new com.android.server.notification.GroupHelper.NotificationAttributes(i2, statusBarNotification.getNotification().getSmallIcon(), statusBarNotification.getNotification().color, 0);
                    com.android.server.notification.Flags.autogroupSummaryIconUpdate();
                    if (z3) {
                        this.mCallback.updateAutogroupSummary(i, statusBarNotification.getPackageName(), notificationAttributes);
                    }
                }
                if (z2) {
                    this.mCallback.removeAutoGroup(statusBarNotification.getKey());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getNotGroupedByAppCount(int i, java.lang.String str) {
        int size;
        synchronized (this.mUngroupedNotifications) {
            size = this.mUngroupedNotifications.getOrDefault(generatePackageKey(i, str), new android.util.ArrayMap<>()).size();
        }
        return size;
    }

    com.android.server.notification.GroupHelper.NotificationAttributes getAutobundledSummaryAttributes(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<com.android.server.notification.GroupHelper.NotificationAttributes> list) {
        android.graphics.drawable.Icon icon = null;
        boolean z = true;
        boolean z2 = true;
        int i = 1;
        int i2 = 0;
        for (com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes : list) {
            if (icon == null) {
                icon = notificationAttributes.icon;
            } else if (!icon.sameAs(notificationAttributes.icon)) {
                z = false;
            }
            if (i == 1) {
                i = notificationAttributes.iconColor;
            } else if (i != notificationAttributes.iconColor) {
                z2 = false;
            }
            if (notificationAttributes.visibility == 1) {
                i2 = 1;
            }
        }
        if (!z) {
            icon = getMonochromeAppIcon(str);
        }
        if (!z2) {
            i = 0;
        }
        return new com.android.server.notification.GroupHelper.NotificationAttributes(0, icon, i, i2);
    }

    com.android.server.notification.GroupHelper.NotificationAttributes updateAutobundledSummaryAttributes(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<com.android.server.notification.GroupHelper.NotificationAttributes> list, @android.annotation.NonNull com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes) {
        com.android.server.notification.GroupHelper.NotificationAttributes autobundledSummaryAttributes = getAutobundledSummaryAttributes(str, list);
        android.graphics.drawable.Icon icon = autobundledSummaryAttributes.icon;
        int i = autobundledSummaryAttributes.iconColor;
        if (autobundledSummaryAttributes.icon == null) {
            icon = notificationAttributes.icon;
        }
        if (autobundledSummaryAttributes.iconColor == 1) {
            i = notificationAttributes.iconColor;
        }
        return new com.android.server.notification.GroupHelper.NotificationAttributes(notificationAttributes.flags, icon, i, autobundledSummaryAttributes.visibility);
    }

    @android.annotation.NonNull
    android.graphics.drawable.Icon getMonochromeAppIcon(@android.annotation.NonNull java.lang.String str) {
        android.graphics.drawable.Icon icon = null;
        try {
            android.graphics.drawable.Drawable applicationIcon = this.mPackageManager.getApplicationIcon(str);
            if ((applicationIcon instanceof android.graphics.drawable.AdaptiveIconDrawable) && ((android.graphics.drawable.AdaptiveIconDrawable) applicationIcon).getMonochrome() != null) {
                icon = android.graphics.drawable.Icon.createWithResourceAdaptiveDrawable(str, ((android.graphics.drawable.AdaptiveIconDrawable) applicationIcon).getSourceDrawableResId(), true, android.graphics.drawable.AdaptiveIconDrawable.getExtraInsetFraction() * (-2.0f));
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Failed to getApplicationIcon() in getMonochromeAppIcon()", e);
        }
        if (icon != null) {
            return icon;
        }
        return android.graphics.drawable.Icon.createWithResource(this.mContext, android.R.drawable.ic_notification_cast_2);
    }

    protected static class NotificationAttributes {
        public final int flags;
        public final android.graphics.drawable.Icon icon;
        public final int iconColor;
        public final int visibility;

        public NotificationAttributes(int i, android.graphics.drawable.Icon icon, int i2, int i3) {
            this.flags = i;
            this.icon = icon;
            this.iconColor = i2;
            this.visibility = i3;
        }

        public NotificationAttributes(@android.annotation.NonNull com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes) {
            this.flags = notificationAttributes.flags;
            this.icon = notificationAttributes.icon;
            this.iconColor = notificationAttributes.iconColor;
            this.visibility = notificationAttributes.visibility;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.notification.GroupHelper.NotificationAttributes)) {
                return false;
            }
            com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes = (com.android.server.notification.GroupHelper.NotificationAttributes) obj;
            return this.flags == notificationAttributes.flags && this.iconColor == notificationAttributes.iconColor && this.icon.sameAs(notificationAttributes.icon) && this.visibility == notificationAttributes.visibility;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.flags), java.lang.Integer.valueOf(this.iconColor), this.icon, java.lang.Integer.valueOf(this.visibility));
        }
    }
}
