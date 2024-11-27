package android.service.notification;

/* loaded from: classes3.dex */
public class StatusBarNotification implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.notification.StatusBarNotification> CREATOR = new android.os.Parcelable.Creator<android.service.notification.StatusBarNotification>() { // from class: android.service.notification.StatusBarNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.StatusBarNotification createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.StatusBarNotification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.StatusBarNotification[] newArray(int i) {
            return new android.service.notification.StatusBarNotification[i];
        }
    };
    static final int MAX_LOG_TAG_LENGTH = 36;
    private java.lang.String groupKey;
    private final int id;
    private final int initialPid;
    private final java.lang.String key;
    private android.content.Context mContext;
    private com.android.internal.logging.InstanceId mInstanceId;
    private final android.app.Notification notification;
    private final java.lang.String opPkg;
    private java.lang.String overrideGroupKey;
    private final java.lang.String pkg;
    private final long postTime;
    private final java.lang.String tag;
    private final int uid;
    private final android.os.UserHandle user;

    public StatusBarNotification(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, int i3, android.app.Notification notification, android.os.UserHandle userHandle, java.lang.String str4, long j) {
        if (str == null) {
            throw new java.lang.NullPointerException();
        }
        if (notification == null) {
            throw new java.lang.NullPointerException();
        }
        this.pkg = str;
        this.opPkg = str2;
        this.id = i;
        this.tag = str3;
        this.uid = i2;
        this.initialPid = i3;
        this.notification = notification;
        this.user = userHandle;
        this.postTime = j;
        this.overrideGroupKey = str4;
        this.key = key();
        this.groupKey = groupKey();
    }

    @java.lang.Deprecated
    public StatusBarNotification(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, int i3, int i4, android.app.Notification notification, android.os.UserHandle userHandle, long j) {
        if (str == null) {
            throw new java.lang.NullPointerException();
        }
        if (notification == null) {
            throw new java.lang.NullPointerException();
        }
        this.pkg = str;
        this.opPkg = str2;
        this.id = i;
        this.tag = str3;
        this.uid = i2;
        this.initialPid = i3;
        this.notification = notification;
        this.user = userHandle;
        this.postTime = j;
        this.key = key();
        this.groupKey = groupKey();
    }

    public StatusBarNotification(android.os.Parcel parcel) {
        this.pkg = parcel.readString();
        this.opPkg = parcel.readString();
        this.id = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.tag = parcel.readString();
        } else {
            this.tag = null;
        }
        this.uid = parcel.readInt();
        this.initialPid = parcel.readInt();
        this.notification = new android.app.Notification(parcel);
        this.user = android.os.UserHandle.readFromParcel(parcel);
        this.postTime = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.overrideGroupKey = parcel.readString();
        }
        if (parcel.readInt() != 0) {
            this.mInstanceId = com.android.internal.logging.InstanceId.CREATOR.createFromParcel(parcel);
        }
        this.key = key();
        this.groupKey = groupKey();
    }

    public static int getUidFromKey(java.lang.String str) {
        java.lang.String[] split = str.split("\\|");
        if (split.length < 5) {
            return -1;
        }
        try {
            return java.lang.Integer.parseInt(split[4]);
        } catch (java.lang.NumberFormatException e) {
            return -1;
        }
    }

    public static java.lang.String getPkgFromKey(java.lang.String str) {
        java.lang.String[] split = str.split("\\|");
        if (split.length >= 2) {
            return split[1];
        }
        return null;
    }

    private java.lang.String key() {
        java.lang.String str = this.user.getIdentifier() + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.pkg + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.id + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.tag + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.uid;
        if (this.overrideGroupKey != null && getNotification().isGroupSummary()) {
            return str + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.overrideGroupKey;
        }
        return str;
    }

    public boolean isAppOrSystemGroupChild() {
        return isGroup() && !getNotification().isGroupSummary();
    }

    public boolean isAppOrSystemGroupSummary() {
        return isGroup() && getNotification().isGroupSummary();
    }

    private java.lang.String groupKey() {
        java.lang.String str;
        if (this.overrideGroupKey != null) {
            return this.user.getIdentifier() + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.pkg + "|g:" + this.overrideGroupKey;
        }
        java.lang.String group = getNotification().getGroup();
        java.lang.String sortKey = getNotification().getSortKey();
        if (group == null && sortKey == null) {
            return this.key;
        }
        java.lang.StringBuilder append = new java.lang.StringBuilder().append(this.user.getIdentifier()).append(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append(this.pkg).append(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        if (group == null) {
            str = "c:" + this.notification.getChannelId();
        } else {
            str = "g:" + group;
        }
        return append.append(str).toString();
    }

    public boolean isGroup() {
        if (this.overrideGroupKey != null || isAppGroup()) {
            return true;
        }
        return false;
    }

    public boolean isAppGroup() {
        if (getNotification().getGroup() != null || getNotification().getSortKey() != null) {
            return true;
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.pkg);
        parcel.writeString(this.opPkg);
        parcel.writeInt(this.id);
        if (this.tag != null) {
            parcel.writeInt(1);
            parcel.writeString(this.tag);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.uid);
        parcel.writeInt(this.initialPid);
        this.notification.writeToParcel(parcel, i);
        this.user.writeToParcel(parcel, i);
        parcel.writeLong(this.postTime);
        if (this.overrideGroupKey != null) {
            parcel.writeInt(1);
            parcel.writeString(this.overrideGroupKey);
        } else {
            parcel.writeInt(0);
        }
        if (this.mInstanceId != null) {
            parcel.writeInt(1);
            this.mInstanceId.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.service.notification.StatusBarNotification cloneLight() {
        android.app.Notification notification = new android.app.Notification();
        this.notification.cloneInto(notification, false);
        return cloneShallow(notification);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.service.notification.StatusBarNotification m3676clone() {
        return cloneShallow(this.notification.m392clone());
    }

    public android.service.notification.StatusBarNotification cloneShallow(android.app.Notification notification) {
        android.service.notification.StatusBarNotification statusBarNotification = new android.service.notification.StatusBarNotification(this.pkg, this.opPkg, this.id, this.tag, this.uid, this.initialPid, notification, this.user, this.overrideGroupKey, this.postTime);
        statusBarNotification.setInstanceId(this.mInstanceId);
        return statusBarNotification;
    }

    public java.lang.String toString() {
        return android.text.TextUtils.formatSimple("StatusBarNotification(pkg=%s user=%s id=%d tag=%s key=%s: %s)", this.pkg, this.user, java.lang.Integer.valueOf(this.id), this.tag, this.key, this.notification);
    }

    public boolean isOngoing() {
        return (this.notification.flags & 2) != 0;
    }

    public boolean isNonDismissable() {
        return (this.notification.flags & 8192) != 0;
    }

    public boolean isClearable() {
        return (this.notification.flags & 2) == 0 && (this.notification.flags & 32) == 0;
    }

    @java.lang.Deprecated
    public int getUserId() {
        return this.user.getIdentifier();
    }

    public int getNormalizedUserId() {
        int userId = getUserId();
        if (userId == -1) {
            return 0;
        }
        return userId;
    }

    public java.lang.String getPackageName() {
        return this.pkg;
    }

    public int getId() {
        return this.id;
    }

    public java.lang.String getTag() {
        return this.tag;
    }

    public int getUid() {
        return this.uid;
    }

    public java.lang.String getOpPkg() {
        return this.opPkg;
    }

    public int getInitialPid() {
        return this.initialPid;
    }

    public android.app.Notification getNotification() {
        return this.notification;
    }

    public android.os.UserHandle getUser() {
        return this.user;
    }

    public long getPostTime() {
        return this.postTime;
    }

    public java.lang.String getKey() {
        return this.key;
    }

    public java.lang.String getGroupKey() {
        return this.groupKey;
    }

    public java.lang.String getGroup() {
        if (this.overrideGroupKey != null) {
            return this.overrideGroupKey;
        }
        return getNotification().getGroup();
    }

    public void setOverrideGroupKey(java.lang.String str) {
        this.overrideGroupKey = str;
        this.groupKey = groupKey();
    }

    public java.lang.String getOverrideGroupKey() {
        return this.overrideGroupKey;
    }

    public void clearPackageContext() {
        this.mContext = null;
    }

    public com.android.internal.logging.InstanceId getInstanceId() {
        return this.mInstanceId;
    }

    public void setInstanceId(com.android.internal.logging.InstanceId instanceId) {
        this.mInstanceId = instanceId;
    }

    public android.content.Context getPackageContext(android.content.Context context) {
        if (this.mContext == null) {
            try {
                this.mContext = context.createApplicationContext(context.getPackageManager().getApplicationInfoAsUser(this.pkg, 8192, getNormalizedUserId()), 4);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                this.mContext = null;
            }
        }
        if (this.mContext == null) {
            this.mContext = context;
        }
        return this.mContext;
    }

    public android.metrics.LogMaker getLogMaker() {
        android.metrics.LogMaker addTaggedData = new android.metrics.LogMaker(0).setPackageName(getPackageName()).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.NOTIFICATION_ID, java.lang.Integer.valueOf(getId())).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.NOTIFICATION_TAG, getTag()).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_NOTIFICATION_CHANNEL_ID, getChannelIdLogTag()).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_NOTIFICATION_GROUP_ID, getGroupLogTag()).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_NOTIFICATION_GROUP_SUMMARY, java.lang.Integer.valueOf(getNotification().isGroupSummary() ? 1 : 0)).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_NOTIFICATION_CATEGORY, getNotification().category);
        if (getNotification().extras != null) {
            java.lang.String string = getNotification().extras.getString(android.app.Notification.EXTRA_TEMPLATE);
            if (string != null && !string.isEmpty()) {
                addTaggedData.addTaggedData(1745, java.lang.Integer.valueOf(string.hashCode()));
            }
            java.util.ArrayList parcelableArrayList = getNotification().extras.getParcelableArrayList(android.app.Notification.EXTRA_PEOPLE_LIST, android.app.Person.class);
            if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
                addTaggedData.addTaggedData(1744, java.lang.Integer.valueOf(parcelableArrayList.size()));
            }
        }
        return addTaggedData;
    }

    public java.lang.String getShortcutId() {
        return getNotification().getShortcutId();
    }

    public java.lang.String getGroupLogTag() {
        return shortenTag(getGroup());
    }

    public java.lang.String getChannelIdLogTag() {
        if (this.notification.getChannelId() == null) {
            return null;
        }
        return shortenTag(this.notification.getChannelId());
    }

    private java.lang.String shortenTag(java.lang.String str) {
        if (str == null || str.length() <= 36) {
            return str;
        }
        return str.substring(0, (36 - r0.length()) - 1) + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + java.lang.Integer.toHexString(str.hashCode());
    }
}
