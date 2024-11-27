package android.app.usage;

/* loaded from: classes.dex */
public final class UsageEvents implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.UsageEvents> CREATOR = new android.os.Parcelable.Creator<android.app.usage.UsageEvents>() { // from class: android.app.usage.UsageEvents.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.UsageEvents createFromParcel(android.os.Parcel parcel) {
            return new android.app.usage.UsageEvents(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.UsageEvents[] newArray(int i) {
            return new android.app.usage.UsageEvents[i];
        }
    };
    public static final int HIDE_LOCUS_EVENTS = 8;
    public static final int HIDE_SHORTCUT_EVENTS = 2;
    public static final java.lang.String INSTANT_APP_CLASS_NAME = "android.instant_class";
    public static final java.lang.String INSTANT_APP_PACKAGE_NAME = "android.instant_app";
    public static final java.lang.String OBFUSCATED_NOTIFICATION_CHANNEL_ID = "unknown_channel_id";
    public static final int OBFUSCATE_INSTANT_APPS = 1;
    public static final int OBFUSCATE_NOTIFICATION_EVENTS = 4;
    public static final int SHOW_ALL_EVENT_DATA = 0;
    private static final java.lang.String TAG = "UsageEvents";
    private int mEventCount;
    private java.util.List<android.app.usage.UsageEvents.Event> mEventsToWrite;
    private final boolean mIncludeTaskRoots;
    private int mIndex;
    private android.os.Parcel mParcel;
    private java.lang.String[] mStringPool;

    public static final class Event {
        public static final int ACTIVITY_DESTROYED = 24;
        public static final int ACTIVITY_PAUSED = 2;
        public static final int ACTIVITY_RESUMED = 1;
        public static final int ACTIVITY_STOPPED = 23;
        public static final int APP_COMPONENT_USED = 31;
        public static final int CHOOSER_ACTION = 9;
        public static final int CONFIGURATION_CHANGE = 5;
        public static final int CONTINUE_PREVIOUS_DAY = 4;
        public static final int CONTINUING_FOREGROUND_SERVICE = 21;
        public static final java.lang.String DEVICE_EVENT_PACKAGE_NAME = "android";
        public static final int DEVICE_SHUTDOWN = 26;
        public static final int DEVICE_STARTUP = 27;
        public static final int END_OF_DAY = 3;
        public static final int FLAG_IS_PACKAGE_INSTANT_APP = 1;
        public static final int FLUSH_TO_DISK = 25;
        public static final int FOREGROUND_SERVICE_START = 19;
        public static final int FOREGROUND_SERVICE_STOP = 20;
        public static final int KEYGUARD_HIDDEN = 18;
        public static final int KEYGUARD_SHOWN = 17;
        public static final int LOCUS_ID_SET = 30;
        public static final int MAX_EVENT_TYPE = 31;

        @java.lang.Deprecated
        public static final int MOVE_TO_BACKGROUND = 2;

        @java.lang.Deprecated
        public static final int MOVE_TO_FOREGROUND = 1;
        public static final int NONE = 0;

        @android.annotation.SystemApi
        public static final int NOTIFICATION_INTERRUPTION = 12;

        @android.annotation.SystemApi
        public static final int NOTIFICATION_SEEN = 10;
        public static final int ROLLOVER_FOREGROUND_SERVICE = 22;
        public static final int SCREEN_INTERACTIVE = 15;
        public static final int SCREEN_NON_INTERACTIVE = 16;
        public static final int SHORTCUT_INVOCATION = 8;

        @android.annotation.SystemApi
        public static final int SLICE_PINNED = 14;

        @android.annotation.SystemApi
        public static final int SLICE_PINNED_PRIV = 13;
        public static final int STANDBY_BUCKET_CHANGED = 11;

        @android.annotation.SystemApi
        public static final int SYSTEM_INTERACTION = 6;
        private static final int UNASSIGNED_TOKEN = -1;
        public static final int USER_INTERACTION = 7;
        public static final int USER_STOPPED = 29;
        public static final int USER_UNLOCKED = 28;
        public static final int VALID_FLAG_BITS = 1;
        public java.lang.String mAction;
        public int mBucketAndReason;
        public java.lang.String mClass;
        public android.content.res.Configuration mConfiguration;
        public java.lang.String[] mContentAnnotations;
        public java.lang.String mContentType;
        public int mEventType;
        public int mFlags;
        public int mInstanceId;
        public java.lang.String mLocusId;
        public java.lang.String mNotificationChannelId;
        public java.lang.String mPackage;
        public java.lang.String mShortcutId;
        public java.lang.String mTaskRootClass;
        public java.lang.String mTaskRootPackage;
        public long mTimeStamp;
        public int mPackageToken = -1;
        public int mClassToken = -1;
        public int mTaskRootPackageToken = -1;
        public int mTaskRootClassToken = -1;
        public int mShortcutIdToken = -1;
        public int mNotificationChannelIdToken = -1;
        public int mLocusIdToken = -1;
        public android.os.PersistableBundle mExtras = null;
        public android.app.usage.UsageEvents.Event.UserInteractionEventExtrasToken mUserInteractionExtrasToken = null;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface EventFlags {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface EventType {
        }

        public static class UserInteractionEventExtrasToken {
            public int mCategoryToken = -1;
            public int mActionToken = -1;
        }

        public Event() {
        }

        public Event(int i, long j) {
            this.mEventType = i;
            this.mTimeStamp = j;
        }

        public Event(android.app.usage.UsageEvents.Event event) {
            copyFrom(event);
        }

        public java.lang.String getPackageName() {
            return this.mPackage;
        }

        @android.annotation.SystemApi
        public boolean isInstantApp() {
            return (this.mFlags & 1) == 1;
        }

        public java.lang.String getClassName() {
            return this.mClass;
        }

        @android.annotation.SystemApi
        public int getInstanceId() {
            return this.mInstanceId;
        }

        @android.annotation.SystemApi
        public java.lang.String getTaskRootPackageName() {
            return this.mTaskRootPackage;
        }

        @android.annotation.SystemApi
        public java.lang.String getTaskRootClassName() {
            return this.mTaskRootClass;
        }

        public long getTimeStamp() {
            return this.mTimeStamp;
        }

        public int getEventType() {
            return this.mEventType;
        }

        public android.os.PersistableBundle getExtras() {
            return this.mExtras == null ? android.os.PersistableBundle.EMPTY : this.mExtras;
        }

        public android.content.res.Configuration getConfiguration() {
            return this.mConfiguration;
        }

        public java.lang.String getShortcutId() {
            return this.mShortcutId;
        }

        public int getAppStandbyBucket() {
            return (this.mBucketAndReason & (-65536)) >>> 16;
        }

        public int getStandbyReason() {
            return this.mBucketAndReason & 65535;
        }

        @android.annotation.SystemApi
        public java.lang.String getNotificationChannelId() {
            return this.mNotificationChannelId;
        }

        public android.app.usage.UsageEvents.Event getObfuscatedIfInstantApp() {
            if (!isInstantApp()) {
                return this;
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(this);
            event.mPackage = android.app.usage.UsageEvents.INSTANT_APP_PACKAGE_NAME;
            event.mClass = android.app.usage.UsageEvents.INSTANT_APP_CLASS_NAME;
            return event;
        }

        public android.app.usage.UsageEvents.Event getObfuscatedNotificationEvent() {
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(this);
            event.mNotificationChannelId = android.app.usage.UsageEvents.OBFUSCATED_NOTIFICATION_CHANNEL_ID;
            return event;
        }

        public java.lang.String getLocusId() {
            return this.mLocusId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void copyFrom(android.app.usage.UsageEvents.Event event) {
            this.mPackage = event.mPackage;
            this.mClass = event.mClass;
            this.mInstanceId = event.mInstanceId;
            this.mTaskRootPackage = event.mTaskRootPackage;
            this.mTaskRootClass = event.mTaskRootClass;
            this.mTimeStamp = event.mTimeStamp;
            this.mEventType = event.mEventType;
            this.mConfiguration = event.mConfiguration;
            this.mShortcutId = event.mShortcutId;
            this.mAction = event.mAction;
            this.mContentType = event.mContentType;
            this.mContentAnnotations = event.mContentAnnotations;
            this.mFlags = event.mFlags;
            this.mBucketAndReason = event.mBucketAndReason;
            this.mNotificationChannelId = event.mNotificationChannelId;
            this.mLocusId = event.mLocusId;
            this.mExtras = event.mExtras;
        }
    }

    public UsageEvents(android.os.Parcel parcel) {
        this.mEventsToWrite = null;
        this.mParcel = null;
        this.mIndex = 0;
        if (android.app.usage.Flags.useParceledList()) {
            readUsageEventsFromParcelWithParceledList(parcel);
        } else {
            readUsageEventsFromParcelWithBlob(parcel);
        }
        this.mIncludeTaskRoots = true;
    }

    private void readUsageEventsFromParcelWithParceledList(android.os.Parcel parcel) {
        this.mEventCount = parcel.readInt();
        this.mIndex = parcel.readInt();
        android.app.usage.ParcelableUsageEventList parcelableUsageEventList = (android.app.usage.ParcelableUsageEventList) parcel.readParcelable(getClass().getClassLoader(), android.app.usage.ParcelableUsageEventList.class);
        if (parcelableUsageEventList != null) {
            this.mEventsToWrite = parcelableUsageEventList.getList();
        } else {
            this.mEventsToWrite = new java.util.ArrayList();
        }
        if (this.mEventCount != this.mEventsToWrite.size()) {
            android.util.Log.w(TAG, "Partial usage event list received: " + this.mEventCount + " != " + this.mEventsToWrite.size());
            this.mEventCount = this.mEventsToWrite.size();
        }
    }

    private void readUsageEventsFromParcelWithBlob(android.os.Parcel parcel) {
        byte[] readBlob = parcel.readBlob();
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.unmarshall(readBlob, 0, readBlob.length);
        obtain.setDataPosition(0);
        this.mEventCount = obtain.readInt();
        this.mIndex = obtain.readInt();
        if (this.mEventCount > 0) {
            this.mStringPool = obtain.createStringArray();
            int readInt = obtain.readInt();
            int readInt2 = obtain.readInt();
            this.mParcel = android.os.Parcel.obtain();
            this.mParcel.setDataPosition(0);
            this.mParcel.appendFrom(obtain, obtain.dataPosition(), readInt);
            this.mParcel.setDataSize(this.mParcel.dataPosition());
            this.mParcel.setDataPosition(readInt2);
        }
    }

    UsageEvents() {
        this.mEventsToWrite = null;
        this.mParcel = null;
        this.mIndex = 0;
        this.mEventCount = 0;
        this.mIncludeTaskRoots = true;
    }

    public UsageEvents(java.util.List<android.app.usage.UsageEvents.Event> list, java.lang.String[] strArr) {
        this(list, strArr, false);
    }

    public UsageEvents(java.util.List<android.app.usage.UsageEvents.Event> list, java.lang.String[] strArr, boolean z) {
        this.mEventsToWrite = null;
        this.mParcel = null;
        this.mIndex = 0;
        this.mStringPool = strArr;
        this.mEventCount = list.size();
        this.mEventsToWrite = list;
        this.mIncludeTaskRoots = z;
    }

    public boolean hasNextEvent() {
        return this.mIndex < this.mEventCount;
    }

    public boolean getNextEvent(android.app.usage.UsageEvents.Event event) {
        if (event == null) {
            throw new java.lang.IllegalArgumentException("Given eventOut must not be null");
        }
        if (this.mIndex >= this.mEventCount) {
            return false;
        }
        if (android.app.usage.Flags.useParceledList()) {
            return getNextEventFromParceledList(event);
        }
        if (this.mParcel != null) {
            readEventFromParcel(this.mParcel, event);
        } else {
            event.copyFrom(this.mEventsToWrite.get(this.mIndex));
        }
        this.mIndex++;
        if (this.mIndex >= this.mEventCount && this.mParcel != null) {
            this.mParcel.recycle();
            this.mParcel = null;
        }
        return true;
    }

    private boolean getNextEventFromParceledList(android.app.usage.UsageEvents.Event event) {
        event.copyFrom(this.mEventsToWrite.get(this.mIndex));
        this.mIndex++;
        return true;
    }

    public void resetToStart() {
        this.mIndex = 0;
        if (this.mParcel != null) {
            this.mParcel.setDataPosition(0);
        }
    }

    private int findStringIndex(java.lang.String str) {
        int binarySearch = java.util.Arrays.binarySearch(this.mStringPool, str);
        if (binarySearch < 0) {
            throw new java.lang.IllegalStateException("String '" + str + "' is not in the string pool");
        }
        return binarySearch;
    }

    private void writeEventToParcel(android.app.usage.UsageEvents.Event event, android.os.Parcel parcel, int i) {
        int i2;
        int i3;
        int i4;
        int i5 = -1;
        if (event.mPackage != null) {
            i2 = findStringIndex(event.mPackage);
        } else {
            i2 = -1;
        }
        if (event.mClass != null) {
            i3 = findStringIndex(event.mClass);
        } else {
            i3 = -1;
        }
        if (this.mIncludeTaskRoots && event.mTaskRootPackage != null) {
            i4 = findStringIndex(event.mTaskRootPackage);
        } else {
            i4 = -1;
        }
        if (this.mIncludeTaskRoots && event.mTaskRootClass != null) {
            i5 = findStringIndex(event.mTaskRootClass);
        }
        parcel.writeInt(i2);
        parcel.writeInt(i3);
        parcel.writeInt(event.mInstanceId);
        parcel.writeInt(i4);
        parcel.writeInt(i5);
        parcel.writeInt(event.mEventType);
        parcel.writeLong(event.mTimeStamp);
        switch (event.mEventType) {
            case 5:
                event.mConfiguration.writeToParcel(parcel, i);
                break;
            case 7:
                if (event.mExtras != null) {
                    parcel.writeInt(1);
                    parcel.writePersistableBundle(event.mExtras);
                    break;
                } else {
                    parcel.writeInt(0);
                    break;
                }
            case 8:
                parcel.writeString(event.mShortcutId);
                break;
            case 9:
                parcel.writeString(event.mAction);
                parcel.writeString(event.mContentType);
                parcel.writeStringArray(event.mContentAnnotations);
                break;
            case 11:
                parcel.writeInt(event.mBucketAndReason);
                break;
            case 12:
                parcel.writeString(event.mNotificationChannelId);
                break;
            case 30:
                parcel.writeString(event.mLocusId);
                break;
        }
        parcel.writeInt(event.mFlags);
    }

    private void readEventFromParcel(android.os.Parcel parcel, android.app.usage.UsageEvents.Event event) {
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            event.mPackage = this.mStringPool[readInt];
        } else {
            event.mPackage = null;
        }
        int readInt2 = parcel.readInt();
        if (readInt2 >= 0) {
            event.mClass = this.mStringPool[readInt2];
        } else {
            event.mClass = null;
        }
        event.mInstanceId = parcel.readInt();
        int readInt3 = parcel.readInt();
        if (readInt3 >= 0) {
            event.mTaskRootPackage = this.mStringPool[readInt3];
        } else {
            event.mTaskRootPackage = null;
        }
        int readInt4 = parcel.readInt();
        if (readInt4 >= 0) {
            event.mTaskRootClass = this.mStringPool[readInt4];
        } else {
            event.mTaskRootClass = null;
        }
        event.mEventType = parcel.readInt();
        event.mTimeStamp = parcel.readLong();
        event.mConfiguration = null;
        event.mShortcutId = null;
        event.mAction = null;
        event.mContentType = null;
        event.mContentAnnotations = null;
        event.mNotificationChannelId = null;
        event.mLocusId = null;
        event.mExtras = null;
        switch (event.mEventType) {
            case 5:
                event.mConfiguration = android.content.res.Configuration.CREATOR.createFromParcel(parcel);
                break;
            case 7:
                if (parcel.readInt() != 0) {
                    event.mExtras = parcel.readPersistableBundle(getClass().getClassLoader());
                    break;
                }
                break;
            case 8:
                event.mShortcutId = parcel.readString();
                break;
            case 9:
                event.mAction = parcel.readString();
                event.mContentType = parcel.readString();
                event.mContentAnnotations = parcel.readStringArray();
                break;
            case 11:
                event.mBucketAndReason = parcel.readInt();
                break;
            case 12:
                event.mNotificationChannelId = parcel.readString();
                break;
            case 30:
                event.mLocusId = parcel.readString();
                break;
        }
        event.mFlags = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (android.app.usage.Flags.useParceledList()) {
            writeUsageEventsToParcelWithParceledList(parcel, i);
        } else {
            writeUsageEventsToParcelWithBlob(parcel, i);
        }
    }

    private void writeUsageEventsToParcelWithParceledList(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mEventCount);
        parcel.writeInt(this.mIndex);
        parcel.writeParcelable(new android.app.usage.ParcelableUsageEventList(this.mEventsToWrite), i);
    }

    private void writeUsageEventsToParcelWithBlob(android.os.Parcel parcel, int i) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.writeInt(this.mEventCount);
        obtain.writeInt(this.mIndex);
        if (this.mEventCount > 0) {
            obtain.writeStringArray(this.mStringPool);
            if (this.mEventsToWrite != null) {
                obtain = android.os.Parcel.obtain();
                try {
                    obtain.setDataPosition(0);
                    for (int i2 = 0; i2 < this.mEventCount; i2++) {
                        writeEventToParcel(this.mEventsToWrite.get(i2), obtain, i);
                    }
                    int dataPosition = obtain.dataPosition();
                    obtain.writeInt(dataPosition);
                    obtain.writeInt(0);
                    obtain.appendFrom(obtain, 0, dataPosition);
                    obtain.recycle();
                } finally {
                    obtain.recycle();
                }
            } else if (this.mParcel != null) {
                obtain.writeInt(this.mParcel.dataSize());
                obtain.writeInt(this.mParcel.dataPosition());
                obtain.appendFrom(this.mParcel, 0, this.mParcel.dataSize());
            } else {
                throw new java.lang.IllegalStateException("Either mParcel or mEventsToWrite must not be null");
            }
        }
        parcel.writeBlob(obtain.marshall());
    }
}
