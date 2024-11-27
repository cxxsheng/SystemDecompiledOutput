package android.service.notification;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class NotificationStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.notification.NotificationStats> CREATOR = new android.os.Parcelable.Creator<android.service.notification.NotificationStats>() { // from class: android.service.notification.NotificationStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.NotificationStats createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.NotificationStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.NotificationStats[] newArray(int i) {
            return new android.service.notification.NotificationStats[i];
        }
    };
    public static final int DISMISSAL_AOD = 2;
    public static final int DISMISSAL_BUBBLE = 4;
    public static final int DISMISSAL_LOCKSCREEN = 5;
    public static final int DISMISSAL_NOT_DISMISSED = -1;
    public static final int DISMISSAL_OTHER = 0;
    public static final int DISMISSAL_PEEK = 1;
    public static final int DISMISSAL_SHADE = 3;
    public static final int DISMISS_SENTIMENT_NEGATIVE = 0;
    public static final int DISMISS_SENTIMENT_NEUTRAL = 1;
    public static final int DISMISS_SENTIMENT_POSITIVE = 2;
    public static final int DISMISS_SENTIMENT_UNKNOWN = -1000;
    private boolean mDirectReplied;
    private int mDismissalSentiment;
    private int mDismissalSurface;
    private boolean mExpanded;
    private boolean mInteracted;
    private boolean mSeen;
    private boolean mSmartReplied;
    private boolean mSnoozed;
    private boolean mViewedSettings;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DismissalSentiment {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DismissalSurface {
    }

    public NotificationStats() {
        this.mDismissalSurface = -1;
        this.mDismissalSentiment = -1000;
    }

    @android.annotation.SystemApi
    protected NotificationStats(android.os.Parcel parcel) {
        this.mDismissalSurface = -1;
        this.mDismissalSentiment = -1000;
        this.mSeen = parcel.readByte() != 0;
        this.mExpanded = parcel.readByte() != 0;
        this.mDirectReplied = parcel.readByte() != 0;
        if (android.app.Flags.lifetimeExtensionRefactor()) {
            this.mSmartReplied = parcel.readByte() != 0;
        }
        this.mSnoozed = parcel.readByte() != 0;
        this.mViewedSettings = parcel.readByte() != 0;
        this.mInteracted = parcel.readByte() != 0;
        this.mDismissalSurface = parcel.readInt();
        this.mDismissalSentiment = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mSeen ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mExpanded ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mDirectReplied ? (byte) 1 : (byte) 0);
        if (android.app.Flags.lifetimeExtensionRefactor()) {
            parcel.writeByte(this.mSmartReplied ? (byte) 1 : (byte) 0);
        }
        parcel.writeByte(this.mSnoozed ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mViewedSettings ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mInteracted ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mDismissalSurface);
        parcel.writeInt(this.mDismissalSentiment);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean hasSeen() {
        return this.mSeen;
    }

    public void setSeen() {
        this.mSeen = true;
    }

    public boolean hasExpanded() {
        return this.mExpanded;
    }

    public void setExpanded() {
        this.mExpanded = true;
        this.mInteracted = true;
    }

    public boolean hasDirectReplied() {
        return this.mDirectReplied;
    }

    public void setDirectReplied() {
        this.mDirectReplied = true;
        this.mInteracted = true;
    }

    public boolean hasSmartReplied() {
        return this.mSmartReplied;
    }

    public void setSmartReplied() {
        this.mSmartReplied = true;
        this.mInteracted = true;
    }

    public boolean hasSnoozed() {
        return this.mSnoozed;
    }

    public void setSnoozed() {
        this.mSnoozed = true;
        this.mInteracted = true;
    }

    public boolean hasViewedSettings() {
        return this.mViewedSettings;
    }

    public void setViewedSettings() {
        this.mViewedSettings = true;
        this.mInteracted = true;
    }

    public boolean hasInteracted() {
        return this.mInteracted;
    }

    public int getDismissalSurface() {
        return this.mDismissalSurface;
    }

    public void setDismissalSurface(int i) {
        this.mDismissalSurface = i;
    }

    public void setDismissalSentiment(int i) {
        this.mDismissalSentiment = i;
    }

    public int getDismissalSentiment() {
        return this.mDismissalSentiment;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.notification.NotificationStats notificationStats = (android.service.notification.NotificationStats) obj;
        if (this.mSeen != notificationStats.mSeen || this.mExpanded != notificationStats.mExpanded || this.mDirectReplied != notificationStats.mDirectReplied) {
            return false;
        }
        if ((!android.app.Flags.lifetimeExtensionRefactor() || this.mSmartReplied == notificationStats.mSmartReplied) && this.mSnoozed == notificationStats.mSnoozed && this.mViewedSettings == notificationStats.mViewedSettings && this.mInteracted == notificationStats.mInteracted && this.mDismissalSurface == notificationStats.mDismissalSurface) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = ((((this.mSeen ? 1 : 0) * 31) + (this.mExpanded ? 1 : 0)) * 31) + (this.mDirectReplied ? 1 : 0);
        if (android.app.Flags.lifetimeExtensionRefactor()) {
            i = (i * 31) + (this.mSmartReplied ? 1 : 0);
        }
        return (((((((i * 31) + (this.mSnoozed ? 1 : 0)) * 31) + (this.mViewedSettings ? 1 : 0)) * 31) + (this.mInteracted ? 1 : 0)) * 31) + this.mDismissalSurface;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("NotificationStats{");
        sb.append("mSeen=").append(this.mSeen);
        sb.append(", mExpanded=").append(this.mExpanded);
        sb.append(", mDirectReplied=").append(this.mDirectReplied);
        if (android.app.Flags.lifetimeExtensionRefactor()) {
            sb.append(", mSmartReplied=").append(this.mSmartReplied);
        }
        sb.append(", mSnoozed=").append(this.mSnoozed);
        sb.append(", mViewedSettings=").append(this.mViewedSettings);
        sb.append(", mInteracted=").append(this.mInteracted);
        sb.append(", mDismissalSurface=").append(this.mDismissalSurface);
        sb.append('}');
        return sb.toString();
    }
}
