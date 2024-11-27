package android.app.people;

/* loaded from: classes.dex */
public final class ConversationStatus implements android.os.Parcelable {
    public static final int ACTIVITY_ANNIVERSARY = 2;
    public static final int ACTIVITY_AUDIO = 4;
    public static final int ACTIVITY_BIRTHDAY = 1;
    public static final int ACTIVITY_GAME = 6;
    public static final int ACTIVITY_LOCATION = 7;
    public static final int ACTIVITY_NEW_STORY = 3;
    public static final int ACTIVITY_OTHER = 0;
    public static final int ACTIVITY_UPCOMING_BIRTHDAY = 8;
    public static final int ACTIVITY_VIDEO = 5;
    public static final int AVAILABILITY_AVAILABLE = 0;
    public static final int AVAILABILITY_BUSY = 1;
    public static final int AVAILABILITY_OFFLINE = 2;
    public static final int AVAILABILITY_UNKNOWN = -1;
    public static final android.os.Parcelable.Creator<android.app.people.ConversationStatus> CREATOR = new android.os.Parcelable.Creator<android.app.people.ConversationStatus>() { // from class: android.app.people.ConversationStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.people.ConversationStatus createFromParcel(android.os.Parcel parcel) {
            return new android.app.people.ConversationStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.people.ConversationStatus[] newArray(int i) {
            return new android.app.people.ConversationStatus[i];
        }
    };
    private static final java.lang.String TAG = "ConversationStatus";
    private final int mActivity;
    private int mAvailability;
    private java.lang.CharSequence mDescription;
    private long mEndTimeMs;
    private android.graphics.drawable.Icon mIcon;
    private final java.lang.String mId;
    private long mStartTimeMs;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActivityType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Availability {
    }

    private ConversationStatus(android.app.people.ConversationStatus.Builder builder) {
        this.mId = builder.mId;
        this.mActivity = builder.mActivity;
        this.mAvailability = builder.mAvailability;
        this.mDescription = builder.mDescription;
        this.mIcon = builder.mIcon;
        this.mStartTimeMs = builder.mStartTimeMs;
        this.mEndTimeMs = builder.mEndTimeMs;
    }

    private ConversationStatus(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mActivity = parcel.readInt();
        this.mAvailability = parcel.readInt();
        this.mDescription = parcel.readCharSequence();
        this.mIcon = (android.graphics.drawable.Icon) parcel.readParcelable(android.graphics.drawable.Icon.class.getClassLoader(), android.graphics.drawable.Icon.class);
        this.mStartTimeMs = parcel.readLong();
        this.mEndTimeMs = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mActivity);
        parcel.writeInt(this.mAvailability);
        parcel.writeCharSequence(this.mDescription);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeLong(this.mStartTimeMs);
        parcel.writeLong(this.mEndTimeMs);
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public int getActivity() {
        return this.mActivity;
    }

    public int getAvailability() {
        return this.mAvailability;
    }

    public java.lang.CharSequence getDescription() {
        return this.mDescription;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public long getStartTimeMillis() {
        return this.mStartTimeMs;
    }

    public long getEndTimeMillis() {
        return this.mEndTimeMs;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.people.ConversationStatus conversationStatus = (android.app.people.ConversationStatus) obj;
        if (this.mActivity == conversationStatus.mActivity && this.mAvailability == conversationStatus.mAvailability && this.mStartTimeMs == conversationStatus.mStartTimeMs && this.mEndTimeMs == conversationStatus.mEndTimeMs && this.mId.equals(conversationStatus.mId) && java.util.Objects.equals(this.mDescription, conversationStatus.mDescription) && java.util.Objects.equals(this.mIcon, conversationStatus.mIcon)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId, java.lang.Integer.valueOf(this.mActivity), java.lang.Integer.valueOf(this.mAvailability), this.mDescription, this.mIcon, java.lang.Long.valueOf(this.mStartTimeMs), java.lang.Long.valueOf(this.mEndTimeMs));
    }

    public java.lang.String toString() {
        return "ConversationStatus{mId='" + this.mId + android.text.format.DateFormat.QUOTE + ", mActivity=" + this.mActivity + ", mAvailability=" + this.mAvailability + ", mDescription=" + ((java.lang.Object) this.mDescription) + ", mIcon=" + this.mIcon + ", mStartTimeMs=" + this.mStartTimeMs + ", mEndTimeMs=" + this.mEndTimeMs + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        final int mActivity;
        java.lang.CharSequence mDescription;
        android.graphics.drawable.Icon mIcon;
        final java.lang.String mId;
        int mAvailability = -1;
        long mStartTimeMs = -1;
        long mEndTimeMs = -1;

        public Builder(java.lang.String str, int i) {
            this.mId = str;
            this.mActivity = i;
        }

        public android.app.people.ConversationStatus.Builder setAvailability(int i) {
            this.mAvailability = i;
            return this;
        }

        public android.app.people.ConversationStatus.Builder setDescription(java.lang.CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public android.app.people.ConversationStatus.Builder setIcon(android.graphics.drawable.Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public android.app.people.ConversationStatus.Builder setStartTimeMillis(long j) {
            this.mStartTimeMs = j;
            return this;
        }

        public android.app.people.ConversationStatus.Builder setEndTimeMillis(long j) {
            this.mEndTimeMs = j;
            return this;
        }

        public android.app.people.ConversationStatus build() {
            return new android.app.people.ConversationStatus(this);
        }
    }
}
