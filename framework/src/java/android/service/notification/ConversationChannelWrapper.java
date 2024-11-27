package android.service.notification;

/* loaded from: classes3.dex */
public final class ConversationChannelWrapper implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.notification.ConversationChannelWrapper> CREATOR = new android.os.Parcelable.Creator<android.service.notification.ConversationChannelWrapper>() { // from class: android.service.notification.ConversationChannelWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.ConversationChannelWrapper createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.ConversationChannelWrapper(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.ConversationChannelWrapper[] newArray(int i) {
            return new android.service.notification.ConversationChannelWrapper[i];
        }
    };
    private java.lang.CharSequence mGroupLabel;
    private android.app.NotificationChannel mNotificationChannel;
    private java.lang.CharSequence mParentChannelLabel;
    private java.lang.String mPkg;
    private android.content.pm.ShortcutInfo mShortcutInfo;
    private int mUid;

    public ConversationChannelWrapper() {
    }

    protected ConversationChannelWrapper(android.os.Parcel parcel) {
        this.mNotificationChannel = (android.app.NotificationChannel) parcel.readParcelable(android.app.NotificationChannel.class.getClassLoader(), android.app.NotificationChannel.class);
        this.mGroupLabel = parcel.readCharSequence();
        this.mParentChannelLabel = parcel.readCharSequence();
        this.mShortcutInfo = (android.content.pm.ShortcutInfo) parcel.readParcelable(android.content.pm.ShortcutInfo.class.getClassLoader(), android.content.pm.ShortcutInfo.class);
        this.mPkg = parcel.readStringNoHelper();
        this.mUid = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mNotificationChannel, i);
        parcel.writeCharSequence(this.mGroupLabel);
        parcel.writeCharSequence(this.mParentChannelLabel);
        parcel.writeParcelable(this.mShortcutInfo, i);
        parcel.writeStringNoHelper(this.mPkg);
        parcel.writeInt(this.mUid);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.app.NotificationChannel getNotificationChannel() {
        return this.mNotificationChannel;
    }

    public void setNotificationChannel(android.app.NotificationChannel notificationChannel) {
        this.mNotificationChannel = notificationChannel;
    }

    public java.lang.CharSequence getGroupLabel() {
        return this.mGroupLabel;
    }

    public void setGroupLabel(java.lang.CharSequence charSequence) {
        this.mGroupLabel = charSequence;
    }

    public java.lang.CharSequence getParentChannelLabel() {
        return this.mParentChannelLabel;
    }

    public void setParentChannelLabel(java.lang.CharSequence charSequence) {
        this.mParentChannelLabel = charSequence;
    }

    public android.content.pm.ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public void setShortcutInfo(android.content.pm.ShortcutInfo shortcutInfo) {
        this.mShortcutInfo = shortcutInfo;
    }

    public java.lang.String getPkg() {
        return this.mPkg;
    }

    public void setPkg(java.lang.String str) {
        this.mPkg = str;
    }

    public int getUid() {
        return this.mUid;
    }

    public void setUid(int i) {
        this.mUid = i;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.notification.ConversationChannelWrapper conversationChannelWrapper = (android.service.notification.ConversationChannelWrapper) obj;
        if (java.util.Objects.equals(getNotificationChannel(), conversationChannelWrapper.getNotificationChannel()) && java.util.Objects.equals(getGroupLabel(), conversationChannelWrapper.getGroupLabel()) && java.util.Objects.equals(getParentChannelLabel(), conversationChannelWrapper.getParentChannelLabel()) && java.util.Objects.equals(getShortcutInfo(), conversationChannelWrapper.getShortcutInfo()) && java.util.Objects.equals(getPkg(), conversationChannelWrapper.getPkg()) && getUid() == conversationChannelWrapper.getUid()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(getNotificationChannel(), getGroupLabel(), getParentChannelLabel(), getShortcutInfo(), getPkg(), java.lang.Integer.valueOf(getUid()));
    }
}
