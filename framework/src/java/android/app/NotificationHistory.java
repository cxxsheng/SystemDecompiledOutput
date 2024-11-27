package android.app;

/* loaded from: classes.dex */
public final class NotificationHistory implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.NotificationHistory> CREATOR = new android.os.Parcelable.Creator<android.app.NotificationHistory>() { // from class: android.app.NotificationHistory.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.NotificationHistory createFromParcel(android.os.Parcel parcel) {
            return new android.app.NotificationHistory(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.NotificationHistory[] newArray(int i) {
            return new android.app.NotificationHistory[i];
        }
    };
    private int mHistoryCount;
    private int mIndex;
    private java.util.List<android.app.NotificationHistory.HistoricalNotification> mNotificationsToWrite;
    private android.os.Parcel mParcel;
    private java.lang.String[] mStringPool;
    private java.util.Set<java.lang.String> mStringsToWrite;

    public static final class HistoricalNotification {
        private java.lang.String mChannelId;
        private java.lang.String mChannelName;
        private java.lang.String mConversationId;
        private android.graphics.drawable.Icon mIcon;
        private java.lang.String mPackage;
        private long mPostedTimeMs;
        private java.lang.String mText;
        private java.lang.String mTitle;
        private int mUid;
        private int mUserId;

        private HistoricalNotification() {
        }

        public java.lang.String getPackage() {
            return this.mPackage;
        }

        public java.lang.String getChannelName() {
            return this.mChannelName;
        }

        public java.lang.String getChannelId() {
            return this.mChannelId;
        }

        public int getUid() {
            return this.mUid;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public long getPostedTimeMs() {
            return this.mPostedTimeMs;
        }

        public java.lang.String getTitle() {
            return this.mTitle;
        }

        public java.lang.String getText() {
            return this.mText;
        }

        public android.graphics.drawable.Icon getIcon() {
            return this.mIcon;
        }

        public java.lang.String getKey() {
            return this.mPackage + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.mUid + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.mPostedTimeMs;
        }

        public java.lang.String getConversationId() {
            return this.mConversationId;
        }

        public java.lang.String toString() {
            return "HistoricalNotification{key='" + getKey() + android.text.format.DateFormat.QUOTE + ", mChannelName='" + this.mChannelName + android.text.format.DateFormat.QUOTE + ", mChannelId='" + this.mChannelId + android.text.format.DateFormat.QUOTE + ", mUserId=" + this.mUserId + ", mUid=" + this.mUid + ", mTitle='" + this.mTitle + android.text.format.DateFormat.QUOTE + ", mText='" + this.mText + android.text.format.DateFormat.QUOTE + ", mIcon=" + this.mIcon + ", mPostedTimeMs=" + this.mPostedTimeMs + ", mConversationId=" + this.mConversationId + '}';
        }

        public boolean equals(java.lang.Object obj) {
            boolean z;
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.NotificationHistory.HistoricalNotification historicalNotification = (android.app.NotificationHistory.HistoricalNotification) obj;
            if ((getIcon() == null && historicalNotification.getIcon() == null) || (getIcon() != null && historicalNotification.getIcon() != null && getIcon().sameAs(historicalNotification.getIcon()))) {
                z = true;
            } else {
                z = false;
            }
            if (getUid() == historicalNotification.getUid() && getUserId() == historicalNotification.getUserId() && getPostedTimeMs() == historicalNotification.getPostedTimeMs() && java.util.Objects.equals(getPackage(), historicalNotification.getPackage()) && java.util.Objects.equals(getChannelName(), historicalNotification.getChannelName()) && java.util.Objects.equals(getChannelId(), historicalNotification.getChannelId()) && java.util.Objects.equals(getTitle(), historicalNotification.getTitle()) && java.util.Objects.equals(getText(), historicalNotification.getText()) && java.util.Objects.equals(getConversationId(), historicalNotification.getConversationId()) && z) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(getPackage(), getChannelName(), getChannelId(), java.lang.Integer.valueOf(getUid()), java.lang.Integer.valueOf(getUserId()), java.lang.Long.valueOf(getPostedTimeMs()), getTitle(), getText(), getIcon(), getConversationId());
        }

        public static final class Builder {
            private java.lang.String mChannelId;
            private java.lang.String mChannelName;
            private java.lang.String mConversationId;
            private android.graphics.drawable.Icon mIcon;
            private java.lang.String mPackage;
            private long mPostedTimeMs;
            private java.lang.String mText;
            private java.lang.String mTitle;
            private int mUid;
            private int mUserId;

            public android.app.NotificationHistory.HistoricalNotification.Builder setPackage(java.lang.String str) {
                this.mPackage = str;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification.Builder setChannelName(java.lang.String str) {
                this.mChannelName = str;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification.Builder setChannelId(java.lang.String str) {
                this.mChannelId = str;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification.Builder setUid(int i) {
                this.mUid = i;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification.Builder setUserId(int i) {
                this.mUserId = i;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification.Builder setPostedTimeMs(long j) {
                this.mPostedTimeMs = j;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification.Builder setTitle(java.lang.String str) {
                this.mTitle = str;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification.Builder setText(java.lang.String str) {
                this.mText = str;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification.Builder setIcon(android.graphics.drawable.Icon icon) {
                this.mIcon = icon;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification.Builder setConversationId(java.lang.String str) {
                this.mConversationId = str;
                return this;
            }

            public android.app.NotificationHistory.HistoricalNotification build() {
                android.app.NotificationHistory.HistoricalNotification historicalNotification = new android.app.NotificationHistory.HistoricalNotification();
                historicalNotification.mPackage = this.mPackage;
                historicalNotification.mChannelName = this.mChannelName;
                historicalNotification.mChannelId = this.mChannelId;
                historicalNotification.mUid = this.mUid;
                historicalNotification.mUserId = this.mUserId;
                historicalNotification.mPostedTimeMs = this.mPostedTimeMs;
                historicalNotification.mTitle = this.mTitle;
                historicalNotification.mText = this.mText;
                historicalNotification.mIcon = this.mIcon;
                historicalNotification.mConversationId = this.mConversationId;
                return historicalNotification;
            }
        }
    }

    private NotificationHistory(android.os.Parcel parcel) {
        this.mNotificationsToWrite = new java.util.ArrayList();
        this.mStringsToWrite = new java.util.HashSet();
        this.mParcel = null;
        this.mIndex = 0;
        byte[] readBlob = parcel.readBlob();
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.unmarshall(readBlob, 0, readBlob.length);
        obtain.setDataPosition(0);
        this.mHistoryCount = obtain.readInt();
        this.mIndex = obtain.readInt();
        if (this.mHistoryCount > 0) {
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

    public NotificationHistory() {
        this.mNotificationsToWrite = new java.util.ArrayList();
        this.mStringsToWrite = new java.util.HashSet();
        this.mParcel = null;
        this.mIndex = 0;
        this.mHistoryCount = 0;
    }

    public boolean hasNextNotification() {
        return this.mIndex < this.mHistoryCount;
    }

    public android.app.NotificationHistory.HistoricalNotification getNextNotification() {
        if (!hasNextNotification()) {
            return null;
        }
        android.app.NotificationHistory.HistoricalNotification readNotificationFromParcel = readNotificationFromParcel(this.mParcel);
        this.mIndex++;
        if (!hasNextNotification()) {
            this.mParcel.recycle();
            this.mParcel = null;
        }
        return readNotificationFromParcel;
    }

    public void addPooledStrings(java.util.List<java.lang.String> list) {
        this.mStringsToWrite.addAll(list);
    }

    public void poolStringsFromNotifications() {
        this.mStringsToWrite.clear();
        for (int i = 0; i < this.mNotificationsToWrite.size(); i++) {
            android.app.NotificationHistory.HistoricalNotification historicalNotification = this.mNotificationsToWrite.get(i);
            this.mStringsToWrite.add(historicalNotification.getPackage());
            this.mStringsToWrite.add(historicalNotification.getChannelName());
            this.mStringsToWrite.add(historicalNotification.getChannelId());
            if (!android.text.TextUtils.isEmpty(historicalNotification.getConversationId())) {
                this.mStringsToWrite.add(historicalNotification.getConversationId());
            }
        }
    }

    public void addNotificationToWrite(android.app.NotificationHistory.HistoricalNotification historicalNotification) {
        if (historicalNotification == null) {
            return;
        }
        this.mNotificationsToWrite.add(historicalNotification);
        this.mHistoryCount++;
    }

    public void addNewNotificationToWrite(android.app.NotificationHistory.HistoricalNotification historicalNotification) {
        if (historicalNotification == null) {
            return;
        }
        this.mNotificationsToWrite.add(0, historicalNotification);
        this.mHistoryCount++;
    }

    public void addNotificationsToWrite(android.app.NotificationHistory notificationHistory) {
        java.util.Iterator<android.app.NotificationHistory.HistoricalNotification> it = notificationHistory.getNotificationsToWrite().iterator();
        while (it.hasNext()) {
            addNotificationToWrite(it.next());
        }
        java.util.Collections.sort(this.mNotificationsToWrite, new java.util.Comparator() { // from class: android.app.NotificationHistory$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                return android.app.NotificationHistory.lambda$addNotificationsToWrite$0((android.app.NotificationHistory.HistoricalNotification) obj, (android.app.NotificationHistory.HistoricalNotification) obj2);
            }
        });
        poolStringsFromNotifications();
    }

    static /* synthetic */ int lambda$addNotificationsToWrite$0(android.app.NotificationHistory.HistoricalNotification historicalNotification, android.app.NotificationHistory.HistoricalNotification historicalNotification2) {
        return java.lang.Long.compare(historicalNotification.getPostedTimeMs(), historicalNotification2.getPostedTimeMs()) * (-1);
    }

    public void removeNotificationsFromWrite(java.lang.String str) {
        for (int size = this.mNotificationsToWrite.size() - 1; size >= 0; size--) {
            if (str.equals(this.mNotificationsToWrite.get(size).getPackage())) {
                this.mNotificationsToWrite.remove(size);
            }
        }
        poolStringsFromNotifications();
    }

    public boolean removeNotificationFromWrite(java.lang.String str, long j) {
        boolean z = false;
        for (int size = this.mNotificationsToWrite.size() - 1; size >= 0; size--) {
            android.app.NotificationHistory.HistoricalNotification historicalNotification = this.mNotificationsToWrite.get(size);
            if (str.equals(historicalNotification.getPackage()) && j == historicalNotification.getPostedTimeMs()) {
                this.mNotificationsToWrite.remove(size);
                z = true;
            }
        }
        if (z) {
            poolStringsFromNotifications();
        }
        return z;
    }

    public boolean removeConversationsFromWrite(java.lang.String str, java.util.Set<java.lang.String> set) {
        boolean z = false;
        for (int size = this.mNotificationsToWrite.size() - 1; size >= 0; size--) {
            android.app.NotificationHistory.HistoricalNotification historicalNotification = this.mNotificationsToWrite.get(size);
            if (str.equals(historicalNotification.getPackage()) && historicalNotification.getConversationId() != null && set.contains(historicalNotification.getConversationId())) {
                this.mNotificationsToWrite.remove(size);
                z = true;
            }
        }
        if (z) {
            poolStringsFromNotifications();
        }
        return z;
    }

    public boolean removeChannelFromWrite(java.lang.String str, java.lang.String str2) {
        boolean z = false;
        for (int size = this.mNotificationsToWrite.size() - 1; size >= 0; size--) {
            android.app.NotificationHistory.HistoricalNotification historicalNotification = this.mNotificationsToWrite.get(size);
            if (str.equals(historicalNotification.getPackage()) && java.util.Objects.equals(str2, historicalNotification.getChannelId())) {
                this.mNotificationsToWrite.remove(size);
                z = true;
            }
        }
        if (z) {
            poolStringsFromNotifications();
        }
        return z;
    }

    public java.lang.String[] getPooledStringsToWrite() {
        java.lang.String[] strArr = (java.lang.String[]) this.mStringsToWrite.toArray(new java.lang.String[0]);
        java.util.Arrays.sort(strArr);
        return strArr;
    }

    public java.util.List<android.app.NotificationHistory.HistoricalNotification> getNotificationsToWrite() {
        return this.mNotificationsToWrite;
    }

    public int getHistoryCount() {
        return this.mHistoryCount;
    }

    private int findStringIndex(java.lang.String str) {
        int binarySearch = java.util.Arrays.binarySearch(this.mStringPool, str);
        if (binarySearch < 0) {
            throw new java.lang.IllegalStateException("String '" + str + "' is not in the string pool");
        }
        return binarySearch;
    }

    private void writeNotificationToParcel(android.app.NotificationHistory.HistoricalNotification historicalNotification, android.os.Parcel parcel, int i) {
        int i2;
        int i3;
        int i4;
        if (historicalNotification.mPackage != null) {
            i2 = findStringIndex(historicalNotification.mPackage);
        } else {
            i2 = -1;
        }
        if (historicalNotification.getChannelName() != null) {
            i3 = findStringIndex(historicalNotification.getChannelName());
        } else {
            i3 = -1;
        }
        if (historicalNotification.getChannelId() != null) {
            i4 = findStringIndex(historicalNotification.getChannelId());
        } else {
            i4 = -1;
        }
        int findStringIndex = android.text.TextUtils.isEmpty(historicalNotification.getConversationId()) ? -1 : findStringIndex(historicalNotification.getConversationId());
        parcel.writeInt(i2);
        parcel.writeInt(i3);
        parcel.writeInt(i4);
        parcel.writeInt(findStringIndex);
        parcel.writeInt(historicalNotification.getUid());
        parcel.writeInt(historicalNotification.getUserId());
        parcel.writeLong(historicalNotification.getPostedTimeMs());
        parcel.writeString(historicalNotification.getTitle());
        parcel.writeString(historicalNotification.getText());
        parcel.writeBoolean(false);
    }

    private android.app.NotificationHistory.HistoricalNotification readNotificationFromParcel(android.os.Parcel parcel) {
        android.app.NotificationHistory.HistoricalNotification.Builder builder = new android.app.NotificationHistory.HistoricalNotification.Builder();
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            builder.mPackage = this.mStringPool[readInt];
        } else {
            builder.mPackage = null;
        }
        int readInt2 = parcel.readInt();
        if (readInt2 >= 0) {
            builder.setChannelName(this.mStringPool[readInt2]);
        } else {
            builder.setChannelName(null);
        }
        int readInt3 = parcel.readInt();
        if (readInt3 >= 0) {
            builder.setChannelId(this.mStringPool[readInt3]);
        } else {
            builder.setChannelId(null);
        }
        int readInt4 = parcel.readInt();
        if (readInt4 >= 0) {
            builder.setConversationId(this.mStringPool[readInt4]);
        } else {
            builder.setConversationId(null);
        }
        builder.setUid(parcel.readInt());
        builder.setUserId(parcel.readInt());
        builder.setPostedTimeMs(parcel.readLong());
        builder.setTitle(parcel.readString());
        builder.setText(parcel.readString());
        if (parcel.readBoolean()) {
            builder.setIcon(android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel));
        }
        return builder.build();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.writeInt(this.mHistoryCount);
        obtain.writeInt(this.mIndex);
        if (this.mHistoryCount > 0) {
            this.mStringPool = getPooledStringsToWrite();
            obtain.writeStringArray(this.mStringPool);
            if (!this.mNotificationsToWrite.isEmpty()) {
                obtain = android.os.Parcel.obtain();
                try {
                    obtain.setDataPosition(0);
                    for (int i2 = 0; i2 < this.mHistoryCount; i2++) {
                        writeNotificationToParcel(this.mNotificationsToWrite.get(i2), obtain, i);
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
                throw new java.lang.IllegalStateException("Either mParcel or mNotificationsToWrite must not be null");
            }
        }
        parcel.writeBlob(obtain.marshall());
    }
}
