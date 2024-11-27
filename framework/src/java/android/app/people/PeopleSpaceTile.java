package android.app.people;

/* loaded from: classes.dex */
public class PeopleSpaceTile implements android.os.Parcelable {
    public static final int BLOCK_CONVERSATIONS = 2;
    public static final android.os.Parcelable.Creator<android.app.people.PeopleSpaceTile> CREATOR = new android.os.Parcelable.Creator<android.app.people.PeopleSpaceTile>() { // from class: android.app.people.PeopleSpaceTile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.people.PeopleSpaceTile createFromParcel(android.os.Parcel parcel) {
            return new android.app.people.PeopleSpaceTile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.people.PeopleSpaceTile[] newArray(int i) {
            return new android.app.people.PeopleSpaceTile[i];
        }
    };
    public static final int SHOW_CONTACTS = 16;
    public static final int SHOW_CONVERSATIONS = 1;
    public static final int SHOW_IMPORTANT_CONVERSATIONS = 4;
    public static final int SHOW_STARRED_CONTACTS = 8;
    private java.lang.String mBirthdayText;
    private boolean mCanBypassDnd;
    private float mContactAffinity;
    private android.net.Uri mContactUri;
    private java.lang.String mId;
    private android.content.Intent mIntent;
    private boolean mIsImportantConversation;
    private boolean mIsPackageSuspended;
    private boolean mIsUserQuieted;
    private long mLastInteractionTimestamp;
    private int mMessagesCount;
    private java.lang.String mNotificationCategory;
    private java.lang.CharSequence mNotificationContent;
    private android.net.Uri mNotificationDataUri;
    private java.lang.String mNotificationKey;
    private int mNotificationPolicyState;
    private java.lang.CharSequence mNotificationSender;
    private long mNotificationTimestamp;
    private java.lang.String mPackageName;
    private java.util.List<android.app.people.ConversationStatus> mStatuses;
    private android.os.UserHandle mUserHandle;
    private android.graphics.drawable.Icon mUserIcon;
    private java.lang.CharSequence mUserName;

    private PeopleSpaceTile(android.app.people.PeopleSpaceTile.Builder builder) {
        this.mId = builder.mId;
        this.mUserName = builder.mUserName;
        this.mUserIcon = builder.mUserIcon;
        this.mContactUri = builder.mContactUri;
        this.mUserHandle = builder.mUserHandle;
        this.mPackageName = builder.mPackageName;
        this.mBirthdayText = builder.mBirthdayText;
        this.mLastInteractionTimestamp = builder.mLastInteractionTimestamp;
        this.mIsImportantConversation = builder.mIsImportantConversation;
        this.mNotificationKey = builder.mNotificationKey;
        this.mNotificationContent = builder.mNotificationContent;
        this.mNotificationSender = builder.mNotificationSender;
        this.mNotificationCategory = builder.mNotificationCategory;
        this.mNotificationDataUri = builder.mNotificationDataUri;
        this.mMessagesCount = builder.mMessagesCount;
        this.mIntent = builder.mIntent;
        this.mNotificationTimestamp = builder.mNotificationTimestamp;
        this.mStatuses = builder.mStatuses;
        this.mCanBypassDnd = builder.mCanBypassDnd;
        this.mIsPackageSuspended = builder.mIsPackageSuspended;
        this.mIsUserQuieted = builder.mIsUserQuieted;
        this.mNotificationPolicyState = builder.mNotificationPolicyState;
        this.mContactAffinity = builder.mContactAffinity;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.CharSequence getUserName() {
        return this.mUserName;
    }

    public android.graphics.drawable.Icon getUserIcon() {
        return this.mUserIcon;
    }

    public android.net.Uri getContactUri() {
        return this.mContactUri;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getBirthdayText() {
        return this.mBirthdayText;
    }

    public long getLastInteractionTimestamp() {
        return this.mLastInteractionTimestamp;
    }

    public boolean isImportantConversation() {
        return this.mIsImportantConversation;
    }

    public java.lang.String getNotificationKey() {
        return this.mNotificationKey;
    }

    public java.lang.CharSequence getNotificationContent() {
        return this.mNotificationContent;
    }

    public java.lang.CharSequence getNotificationSender() {
        return this.mNotificationSender;
    }

    public java.lang.String getNotificationCategory() {
        return this.mNotificationCategory;
    }

    public android.net.Uri getNotificationDataUri() {
        return this.mNotificationDataUri;
    }

    public int getMessagesCount() {
        return this.mMessagesCount;
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }

    public long getNotificationTimestamp() {
        return this.mNotificationTimestamp;
    }

    public java.util.List<android.app.people.ConversationStatus> getStatuses() {
        return this.mStatuses;
    }

    public boolean canBypassDnd() {
        return this.mCanBypassDnd;
    }

    public boolean isPackageSuspended() {
        return this.mIsPackageSuspended;
    }

    public boolean isUserQuieted() {
        return this.mIsUserQuieted;
    }

    public int getNotificationPolicyState() {
        return this.mNotificationPolicyState;
    }

    public float getContactAffinity() {
        return this.mContactAffinity;
    }

    public android.app.people.PeopleSpaceTile.Builder toBuilder() {
        android.app.people.PeopleSpaceTile.Builder builder = new android.app.people.PeopleSpaceTile.Builder(this.mId, this.mUserName, this.mUserIcon, this.mIntent);
        builder.setContactUri(this.mContactUri);
        builder.setUserHandle(this.mUserHandle);
        builder.setPackageName(this.mPackageName);
        builder.setBirthdayText(this.mBirthdayText);
        builder.setLastInteractionTimestamp(this.mLastInteractionTimestamp);
        builder.setIsImportantConversation(this.mIsImportantConversation);
        builder.setNotificationKey(this.mNotificationKey);
        builder.setNotificationContent(this.mNotificationContent);
        builder.setNotificationSender(this.mNotificationSender);
        builder.setNotificationCategory(this.mNotificationCategory);
        builder.setNotificationDataUri(this.mNotificationDataUri);
        builder.setMessagesCount(this.mMessagesCount);
        builder.setIntent(this.mIntent);
        builder.setNotificationTimestamp(this.mNotificationTimestamp);
        builder.setStatuses(this.mStatuses);
        builder.setCanBypassDnd(this.mCanBypassDnd);
        builder.setIsPackageSuspended(this.mIsPackageSuspended);
        builder.setIsUserQuieted(this.mIsUserQuieted);
        builder.setNotificationPolicyState(this.mNotificationPolicyState);
        builder.setContactAffinity(this.mContactAffinity);
        return builder;
    }

    public static class Builder {
        private java.lang.String mBirthdayText;
        private boolean mCanBypassDnd;
        private float mContactAffinity;
        private android.net.Uri mContactUri;
        private java.lang.String mId;
        private android.content.Intent mIntent;
        private boolean mIsImportantConversation;
        private boolean mIsPackageSuspended;
        private boolean mIsUserQuieted;
        private long mLastInteractionTimestamp;
        private int mMessagesCount;
        private java.lang.String mNotificationCategory;
        private java.lang.CharSequence mNotificationContent;
        private android.net.Uri mNotificationDataUri;
        private java.lang.String mNotificationKey;
        private int mNotificationPolicyState;
        private java.lang.CharSequence mNotificationSender;
        private long mNotificationTimestamp;
        private java.lang.String mPackageName;
        private java.util.List<android.app.people.ConversationStatus> mStatuses;
        private android.os.UserHandle mUserHandle;
        private android.graphics.drawable.Icon mUserIcon;
        private java.lang.CharSequence mUserName;

        public Builder(java.lang.String str, java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, android.content.Intent intent) {
            this.mId = str;
            this.mUserName = charSequence;
            this.mUserIcon = icon;
            this.mIntent = intent;
            this.mPackageName = intent == null ? null : intent.getPackage();
            this.mNotificationPolicyState = 1;
        }

        public Builder(android.content.pm.ShortcutInfo shortcutInfo, android.content.pm.LauncherApps launcherApps) {
            this.mId = shortcutInfo.getId();
            this.mUserName = shortcutInfo.getLabel();
            this.mUserIcon = android.app.people.PeopleSpaceTile.convertDrawableToIcon(launcherApps.getShortcutIconDrawable(shortcutInfo, 0));
            this.mUserHandle = shortcutInfo.getUserHandle();
            this.mPackageName = shortcutInfo.getPackage();
            this.mContactUri = getContactUri(shortcutInfo);
            this.mNotificationPolicyState = 1;
        }

        public Builder(android.app.people.ConversationChannel conversationChannel, android.content.pm.LauncherApps launcherApps) {
            android.content.pm.ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
            this.mId = shortcutInfo.getId();
            this.mUserName = shortcutInfo.getLabel();
            boolean z = false;
            this.mUserIcon = android.app.people.PeopleSpaceTile.convertDrawableToIcon(launcherApps.getShortcutIconDrawable(shortcutInfo, 0));
            this.mUserHandle = shortcutInfo.getUserHandle();
            this.mPackageName = shortcutInfo.getPackage();
            this.mContactUri = getContactUri(shortcutInfo);
            this.mStatuses = conversationChannel.getStatuses();
            this.mLastInteractionTimestamp = conversationChannel.getLastEventTimestamp();
            this.mIsImportantConversation = conversationChannel.getNotificationChannel() != null && conversationChannel.getNotificationChannel().isImportantConversation();
            if (conversationChannel.getNotificationChannel() != null && conversationChannel.getNotificationChannel().canBypassDnd()) {
                z = true;
            }
            this.mCanBypassDnd = z;
            this.mNotificationPolicyState = 1;
        }

        public android.net.Uri getContactUri(android.content.pm.ShortcutInfo shortcutInfo) {
            if (shortcutInfo.getPersons() == null || shortcutInfo.getPersons().length != 1) {
                return null;
            }
            android.app.Person person = shortcutInfo.getPersons()[0];
            if (person.getUri() == null) {
                return null;
            }
            return android.net.Uri.parse(person.getUri());
        }

        public android.app.people.PeopleSpaceTile.Builder setId(java.lang.String str) {
            this.mId = str;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setUserName(java.lang.CharSequence charSequence) {
            this.mUserName = charSequence;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setUserIcon(android.graphics.drawable.Icon icon) {
            this.mUserIcon = icon;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setContactUri(android.net.Uri uri) {
            this.mContactUri = uri;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setUserHandle(android.os.UserHandle userHandle) {
            this.mUserHandle = userHandle;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setPackageName(java.lang.String str) {
            this.mPackageName = str;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setBirthdayText(java.lang.String str) {
            this.mBirthdayText = str;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setLastInteractionTimestamp(long j) {
            this.mLastInteractionTimestamp = j;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setIsImportantConversation(boolean z) {
            this.mIsImportantConversation = z;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setNotificationKey(java.lang.String str) {
            this.mNotificationKey = str;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setNotificationContent(java.lang.CharSequence charSequence) {
            this.mNotificationContent = charSequence;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setNotificationSender(java.lang.CharSequence charSequence) {
            this.mNotificationSender = charSequence;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setNotificationCategory(java.lang.String str) {
            this.mNotificationCategory = str;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setNotificationDataUri(android.net.Uri uri) {
            this.mNotificationDataUri = uri;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setMessagesCount(int i) {
            this.mMessagesCount = i;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setIntent(android.content.Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setNotificationTimestamp(long j) {
            this.mNotificationTimestamp = j;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setStatuses(java.util.List<android.app.people.ConversationStatus> list) {
            this.mStatuses = list;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setCanBypassDnd(boolean z) {
            this.mCanBypassDnd = z;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setIsPackageSuspended(boolean z) {
            this.mIsPackageSuspended = z;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setIsUserQuieted(boolean z) {
            this.mIsUserQuieted = z;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setNotificationPolicyState(int i) {
            this.mNotificationPolicyState = i;
            return this;
        }

        public android.app.people.PeopleSpaceTile.Builder setContactAffinity(float f) {
            this.mContactAffinity = f;
            return this;
        }

        public android.app.people.PeopleSpaceTile build() {
            return new android.app.people.PeopleSpaceTile(this);
        }
    }

    public PeopleSpaceTile(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mUserName = parcel.readCharSequence();
        this.mUserIcon = (android.graphics.drawable.Icon) parcel.readParcelable(android.graphics.drawable.Icon.class.getClassLoader(), android.graphics.drawable.Icon.class);
        this.mContactUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
        this.mUserHandle = (android.os.UserHandle) parcel.readParcelable(android.os.UserHandle.class.getClassLoader(), android.os.UserHandle.class);
        this.mPackageName = parcel.readString();
        this.mBirthdayText = parcel.readString();
        this.mLastInteractionTimestamp = parcel.readLong();
        this.mIsImportantConversation = parcel.readBoolean();
        this.mNotificationKey = parcel.readString();
        this.mNotificationContent = parcel.readCharSequence();
        this.mNotificationSender = parcel.readCharSequence();
        this.mNotificationCategory = parcel.readString();
        this.mNotificationDataUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
        this.mMessagesCount = parcel.readInt();
        this.mIntent = (android.content.Intent) parcel.readParcelable(android.content.Intent.class.getClassLoader(), android.content.Intent.class);
        this.mNotificationTimestamp = parcel.readLong();
        this.mStatuses = new java.util.ArrayList();
        parcel.readParcelableList(this.mStatuses, android.app.people.ConversationStatus.class.getClassLoader(), android.app.people.ConversationStatus.class);
        this.mCanBypassDnd = parcel.readBoolean();
        this.mIsPackageSuspended = parcel.readBoolean();
        this.mIsUserQuieted = parcel.readBoolean();
        this.mNotificationPolicyState = parcel.readInt();
        this.mContactAffinity = parcel.readFloat();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeCharSequence(this.mUserName);
        parcel.writeParcelable(this.mUserIcon, i);
        parcel.writeParcelable(this.mContactUri, i);
        parcel.writeParcelable(this.mUserHandle, i);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mBirthdayText);
        parcel.writeLong(this.mLastInteractionTimestamp);
        parcel.writeBoolean(this.mIsImportantConversation);
        parcel.writeString(this.mNotificationKey);
        parcel.writeCharSequence(this.mNotificationContent);
        parcel.writeCharSequence(this.mNotificationSender);
        parcel.writeString(this.mNotificationCategory);
        parcel.writeParcelable(this.mNotificationDataUri, i);
        parcel.writeInt(this.mMessagesCount);
        parcel.writeParcelable(this.mIntent, i);
        parcel.writeLong(this.mNotificationTimestamp);
        parcel.writeParcelableList(this.mStatuses, i);
        parcel.writeBoolean(this.mCanBypassDnd);
        parcel.writeBoolean(this.mIsPackageSuspended);
        parcel.writeBoolean(this.mIsUserQuieted);
        parcel.writeInt(this.mNotificationPolicyState);
        parcel.writeFloat(this.mContactAffinity);
    }

    public static android.graphics.drawable.Icon convertDrawableToIcon(android.graphics.drawable.Drawable drawable) {
        android.graphics.Bitmap createBitmap;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
            android.graphics.drawable.BitmapDrawable bitmapDrawable = (android.graphics.drawable.BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return android.graphics.drawable.Icon.createWithBitmap(bitmapDrawable.getBitmap());
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            createBitmap = android.graphics.Bitmap.createBitmap(1, 1, android.graphics.Bitmap.Config.ARGB_8888);
        } else {
            createBitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        }
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return android.graphics.drawable.Icon.createWithBitmap(createBitmap);
    }
}
