package com.android.server.notification;

/* loaded from: classes2.dex */
public final class NotificationHistoryFilter {
    private java.lang.String mChannel;
    private int mNotificationCount;
    private java.lang.String mPackage;

    private NotificationHistoryFilter() {
    }

    public java.lang.String getPackage() {
        return this.mPackage;
    }

    public java.lang.String getChannel() {
        return this.mChannel;
    }

    public int getMaxNotifications() {
        return this.mNotificationCount;
    }

    public boolean isFiltering() {
        return (getPackage() == null && getChannel() == null && this.mNotificationCount >= Integer.MAX_VALUE) ? false : true;
    }

    public boolean matchesPackageAndChannelFilter(android.app.NotificationHistory.HistoricalNotification historicalNotification) {
        if (!android.text.TextUtils.isEmpty(getPackage())) {
            if (getPackage().equals(historicalNotification.getPackage())) {
                return android.text.TextUtils.isEmpty(getChannel()) || getChannel().equals(historicalNotification.getChannelId());
            }
            return false;
        }
        return true;
    }

    public boolean matchesCountFilter(android.app.NotificationHistory notificationHistory) {
        return notificationHistory.getHistoryCount() < this.mNotificationCount;
    }

    public static final class Builder {
        private java.lang.String mPackage = null;
        private java.lang.String mChannel = null;
        private int mNotificationCount = Integer.MAX_VALUE;

        public com.android.server.notification.NotificationHistoryFilter.Builder setPackage(java.lang.String str) {
            this.mPackage = str;
            return this;
        }

        public com.android.server.notification.NotificationHistoryFilter.Builder setChannel(java.lang.String str, java.lang.String str2) {
            setPackage(str);
            this.mChannel = str2;
            return this;
        }

        public com.android.server.notification.NotificationHistoryFilter.Builder setMaxNotifications(int i) {
            this.mNotificationCount = i;
            return this;
        }

        public com.android.server.notification.NotificationHistoryFilter build() {
            com.android.server.notification.NotificationHistoryFilter notificationHistoryFilter = new com.android.server.notification.NotificationHistoryFilter();
            notificationHistoryFilter.mPackage = this.mPackage;
            notificationHistoryFilter.mChannel = this.mChannel;
            notificationHistoryFilter.mNotificationCount = this.mNotificationCount;
            return notificationHistoryFilter;
        }
    }
}
