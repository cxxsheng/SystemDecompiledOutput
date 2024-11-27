package com.android.server.people.data;

/* loaded from: classes2.dex */
public class ConversationInfo {
    private static final boolean DEBUG = false;
    private static final int FLAG_BUBBLED = 4;
    private static final int FLAG_CONTACT_STARRED = 32;
    private static final int FLAG_DEMOTED = 64;
    private static final int FLAG_IMPORTANT = 1;
    private static final int FLAG_NOTIFICATION_SILENCED = 2;
    private static final int FLAG_PERSON_BOT = 16;
    private static final int FLAG_PERSON_IMPORTANT = 8;
    private static final java.lang.String TAG = com.android.server.people.data.ConversationInfo.class.getSimpleName();
    private static final int VERSION = 1;

    @android.annotation.Nullable
    private java.lang.String mContactPhoneNumber;

    @android.annotation.Nullable
    private android.net.Uri mContactUri;
    private int mConversationFlags;
    private long mCreationTimestamp;
    private java.util.Map<java.lang.String, android.app.people.ConversationStatus> mCurrStatuses;
    private long mLastEventTimestamp;

    @android.annotation.Nullable
    private android.content.LocusId mLocusId;

    @android.annotation.Nullable
    private java.lang.String mNotificationChannelId;

    @android.annotation.Nullable
    private java.lang.String mParentNotificationChannelId;
    private int mShortcutFlags;

    @android.annotation.NonNull
    private java.lang.String mShortcutId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ConversationFlags {
    }

    private ConversationInfo(com.android.server.people.data.ConversationInfo.Builder builder) {
        this.mShortcutId = builder.mShortcutId;
        this.mLocusId = builder.mLocusId;
        this.mContactUri = builder.mContactUri;
        this.mContactPhoneNumber = builder.mContactPhoneNumber;
        this.mNotificationChannelId = builder.mNotificationChannelId;
        this.mParentNotificationChannelId = builder.mParentNotificationChannelId;
        this.mLastEventTimestamp = builder.mLastEventTimestamp;
        this.mCreationTimestamp = builder.mCreationTimestamp;
        this.mShortcutFlags = builder.mShortcutFlags;
        this.mConversationFlags = builder.mConversationFlags;
        this.mCurrStatuses = builder.mCurrStatuses;
    }

    @android.annotation.NonNull
    public java.lang.String getShortcutId() {
        return this.mShortcutId;
    }

    @android.annotation.Nullable
    android.content.LocusId getLocusId() {
        return this.mLocusId;
    }

    @android.annotation.Nullable
    android.net.Uri getContactUri() {
        return this.mContactUri;
    }

    @android.annotation.Nullable
    java.lang.String getContactPhoneNumber() {
        return this.mContactPhoneNumber;
    }

    @android.annotation.Nullable
    java.lang.String getNotificationChannelId() {
        return this.mNotificationChannelId;
    }

    @android.annotation.Nullable
    java.lang.String getParentNotificationChannelId() {
        return this.mParentNotificationChannelId;
    }

    long getLastEventTimestamp() {
        return this.mLastEventTimestamp;
    }

    long getCreationTimestamp() {
        return this.mCreationTimestamp;
    }

    public boolean isShortcutLongLived() {
        return hasShortcutFlags(8192);
    }

    public boolean isShortcutCachedForNotification() {
        return hasShortcutFlags(16384);
    }

    public boolean isImportant() {
        return hasConversationFlags(1);
    }

    public boolean isNotificationSilenced() {
        return hasConversationFlags(2);
    }

    public boolean isBubbled() {
        return hasConversationFlags(4);
    }

    public boolean isDemoted() {
        return hasConversationFlags(64);
    }

    public boolean isPersonImportant() {
        return hasConversationFlags(8);
    }

    public boolean isPersonBot() {
        return hasConversationFlags(16);
    }

    public boolean isContactStarred() {
        return hasConversationFlags(32);
    }

    public java.util.Collection<android.app.people.ConversationStatus> getStatuses() {
        return this.mCurrStatuses.values();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.people.data.ConversationInfo)) {
            return false;
        }
        com.android.server.people.data.ConversationInfo conversationInfo = (com.android.server.people.data.ConversationInfo) obj;
        return java.util.Objects.equals(this.mShortcutId, conversationInfo.mShortcutId) && java.util.Objects.equals(this.mLocusId, conversationInfo.mLocusId) && java.util.Objects.equals(this.mContactUri, conversationInfo.mContactUri) && java.util.Objects.equals(this.mContactPhoneNumber, conversationInfo.mContactPhoneNumber) && java.util.Objects.equals(this.mNotificationChannelId, conversationInfo.mNotificationChannelId) && java.util.Objects.equals(this.mParentNotificationChannelId, conversationInfo.mParentNotificationChannelId) && java.util.Objects.equals(java.lang.Long.valueOf(this.mLastEventTimestamp), java.lang.Long.valueOf(conversationInfo.mLastEventTimestamp)) && this.mCreationTimestamp == conversationInfo.mCreationTimestamp && this.mShortcutFlags == conversationInfo.mShortcutFlags && this.mConversationFlags == conversationInfo.mConversationFlags && java.util.Objects.equals(this.mCurrStatuses, conversationInfo.mCurrStatuses);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mShortcutId, this.mLocusId, this.mContactUri, this.mContactPhoneNumber, this.mNotificationChannelId, this.mParentNotificationChannelId, java.lang.Long.valueOf(this.mLastEventTimestamp), java.lang.Long.valueOf(this.mCreationTimestamp), java.lang.Integer.valueOf(this.mShortcutFlags), java.lang.Integer.valueOf(this.mConversationFlags), this.mCurrStatuses);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("ConversationInfo {");
        sb.append("shortcutId=");
        sb.append(this.mShortcutId);
        sb.append(", locusId=");
        sb.append(this.mLocusId);
        sb.append(", contactUri=");
        sb.append(this.mContactUri);
        sb.append(", phoneNumber=");
        sb.append(this.mContactPhoneNumber);
        sb.append(", notificationChannelId=");
        sb.append(this.mNotificationChannelId);
        sb.append(", parentNotificationChannelId=");
        sb.append(this.mParentNotificationChannelId);
        sb.append(", lastEventTimestamp=");
        sb.append(this.mLastEventTimestamp);
        sb.append(", creationTimestamp=");
        sb.append(this.mCreationTimestamp);
        sb.append(", statuses=");
        sb.append(this.mCurrStatuses);
        sb.append(", shortcutFlags=0x");
        sb.append(java.lang.Integer.toHexString(this.mShortcutFlags));
        sb.append(" [");
        if (isShortcutLongLived()) {
            sb.append("Liv");
        }
        if (isShortcutCachedForNotification()) {
            sb.append("Cac");
        }
        sb.append("]");
        sb.append(", conversationFlags=0x");
        sb.append(java.lang.Integer.toHexString(this.mConversationFlags));
        sb.append(" [");
        if (isImportant()) {
            sb.append("Imp");
        }
        if (isNotificationSilenced()) {
            sb.append("Sil");
        }
        if (isBubbled()) {
            sb.append("Bub");
        }
        if (isDemoted()) {
            sb.append("Dem");
        }
        if (isPersonImportant()) {
            sb.append("PIm");
        }
        if (isPersonBot()) {
            sb.append("Bot");
        }
        if (isContactStarred()) {
            sb.append("Sta");
        }
        sb.append("]}");
        return sb.toString();
    }

    private boolean hasShortcutFlags(int i) {
        return (this.mShortcutFlags & i) == i;
    }

    private boolean hasConversationFlags(int i) {
        return (this.mConversationFlags & i) == i;
    }

    void writeToProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1138166333441L, this.mShortcutId);
        if (this.mLocusId != null) {
            long start = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1138166333441L, this.mLocusId.getId());
            protoOutputStream.end(start);
        }
        if (this.mContactUri != null) {
            protoOutputStream.write(1138166333443L, this.mContactUri.toString());
        }
        if (this.mNotificationChannelId != null) {
            protoOutputStream.write(1138166333444L, this.mNotificationChannelId);
        }
        if (this.mParentNotificationChannelId != null) {
            protoOutputStream.write(1138166333448L, this.mParentNotificationChannelId);
        }
        protoOutputStream.write(1112396529673L, this.mLastEventTimestamp);
        protoOutputStream.write(1112396529674L, this.mCreationTimestamp);
        protoOutputStream.write(1120986464261L, this.mShortcutFlags);
        protoOutputStream.write(1120986464262L, this.mConversationFlags);
        if (this.mContactPhoneNumber != null) {
            protoOutputStream.write(1138166333447L, this.mContactPhoneNumber);
        }
    }

    @android.annotation.Nullable
    byte[] getBackupPayload() {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF(this.mShortcutId);
            dataOutputStream.writeUTF(this.mLocusId != null ? this.mLocusId.getId() : "");
            dataOutputStream.writeUTF(this.mContactUri != null ? this.mContactUri.toString() : "");
            dataOutputStream.writeUTF(this.mNotificationChannelId != null ? this.mNotificationChannelId : "");
            dataOutputStream.writeInt(this.mShortcutFlags);
            dataOutputStream.writeInt(this.mConversationFlags);
            dataOutputStream.writeUTF(this.mContactPhoneNumber != null ? this.mContactPhoneNumber : "");
            dataOutputStream.writeUTF(this.mParentNotificationChannelId != null ? this.mParentNotificationChannelId : "");
            dataOutputStream.writeLong(this.mLastEventTimestamp);
            dataOutputStream.writeInt(1);
            dataOutputStream.writeLong(this.mCreationTimestamp);
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write fields to backup payload.", e);
            return null;
        }
    }

    @android.annotation.NonNull
    static com.android.server.people.data.ConversationInfo readFromProto(@android.annotation.NonNull android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        com.android.server.people.data.ConversationInfo.Builder builder = new com.android.server.people.data.ConversationInfo.Builder();
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    builder.setShortcutId(protoInputStream.readString(1138166333441L));
                    break;
                case 2:
                    long start = protoInputStream.start(1146756268034L);
                    while (protoInputStream.nextField() != -1) {
                        if (protoInputStream.getFieldNumber() == 1) {
                            builder.setLocusId(new android.content.LocusId(protoInputStream.readString(1138166333441L)));
                        }
                    }
                    protoInputStream.end(start);
                    break;
                case 3:
                    builder.setContactUri(android.net.Uri.parse(protoInputStream.readString(1138166333443L)));
                    break;
                case 4:
                    builder.setNotificationChannelId(protoInputStream.readString(1138166333444L));
                    break;
                case 5:
                    builder.setShortcutFlags(protoInputStream.readInt(1120986464261L));
                    break;
                case 6:
                    builder.setConversationFlags(protoInputStream.readInt(1120986464262L));
                    break;
                case 7:
                    builder.setContactPhoneNumber(protoInputStream.readString(1138166333447L));
                    break;
                case 8:
                    builder.setParentNotificationChannelId(protoInputStream.readString(1138166333448L));
                    break;
                case 9:
                    builder.setLastEventTimestamp(protoInputStream.readLong(1112396529673L));
                    break;
                case 10:
                    builder.setCreationTimestamp(protoInputStream.readLong(1112396529674L));
                    break;
                default:
                    android.util.Slog.w(TAG, "Could not read undefined field: " + protoInputStream.getFieldNumber());
                    break;
            }
        }
        return builder.build();
    }

    @android.annotation.Nullable
    static com.android.server.people.data.ConversationInfo readFromBackupPayload(@android.annotation.NonNull byte[] bArr) {
        com.android.server.people.data.ConversationInfo.Builder builder = new com.android.server.people.data.ConversationInfo.Builder();
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
        try {
            builder.setShortcutId(dataInputStream.readUTF());
            java.lang.String readUTF = dataInputStream.readUTF();
            if (!android.text.TextUtils.isEmpty(readUTF)) {
                builder.setLocusId(new android.content.LocusId(readUTF));
            }
            java.lang.String readUTF2 = dataInputStream.readUTF();
            if (!android.text.TextUtils.isEmpty(readUTF2)) {
                builder.setContactUri(android.net.Uri.parse(readUTF2));
            }
            java.lang.String readUTF3 = dataInputStream.readUTF();
            if (!android.text.TextUtils.isEmpty(readUTF3)) {
                builder.setNotificationChannelId(readUTF3);
            }
            builder.setShortcutFlags(dataInputStream.readInt());
            builder.setConversationFlags(dataInputStream.readInt());
            java.lang.String readUTF4 = dataInputStream.readUTF();
            if (!android.text.TextUtils.isEmpty(readUTF4)) {
                builder.setContactPhoneNumber(readUTF4);
            }
            java.lang.String readUTF5 = dataInputStream.readUTF();
            if (!android.text.TextUtils.isEmpty(readUTF5)) {
                builder.setParentNotificationChannelId(readUTF5);
            }
            builder.setLastEventTimestamp(dataInputStream.readLong());
            if (maybeReadVersion(dataInputStream) == 1) {
                builder.setCreationTimestamp(dataInputStream.readLong());
            }
            return builder.build();
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to read conversation info fields from backup payload.", e);
            return null;
        }
    }

    private static int maybeReadVersion(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        try {
            return dataInputStream.readInt();
        } catch (java.io.EOFException e) {
            return 0;
        }
    }

    static class Builder {

        @android.annotation.Nullable
        private java.lang.String mContactPhoneNumber;

        @android.annotation.Nullable
        private android.net.Uri mContactUri;
        private int mConversationFlags;
        private long mCreationTimestamp;
        private java.util.Map<java.lang.String, android.app.people.ConversationStatus> mCurrStatuses;
        private long mLastEventTimestamp;

        @android.annotation.Nullable
        private android.content.LocusId mLocusId;

        @android.annotation.Nullable
        private java.lang.String mNotificationChannelId;

        @android.annotation.Nullable
        private java.lang.String mParentNotificationChannelId;
        private int mShortcutFlags;
        private java.lang.String mShortcutId;

        Builder() {
            this.mCurrStatuses = new java.util.HashMap();
        }

        Builder(@android.annotation.NonNull com.android.server.people.data.ConversationInfo conversationInfo) {
            this.mCurrStatuses = new java.util.HashMap();
            if (this.mShortcutId == null) {
                this.mShortcutId = conversationInfo.mShortcutId;
            } else {
                com.android.internal.util.Preconditions.checkArgument(this.mShortcutId.equals(conversationInfo.mShortcutId));
            }
            this.mLocusId = conversationInfo.mLocusId;
            this.mContactUri = conversationInfo.mContactUri;
            this.mContactPhoneNumber = conversationInfo.mContactPhoneNumber;
            this.mNotificationChannelId = conversationInfo.mNotificationChannelId;
            this.mParentNotificationChannelId = conversationInfo.mParentNotificationChannelId;
            this.mLastEventTimestamp = conversationInfo.mLastEventTimestamp;
            this.mCreationTimestamp = conversationInfo.mCreationTimestamp;
            this.mShortcutFlags = conversationInfo.mShortcutFlags;
            this.mConversationFlags = conversationInfo.mConversationFlags;
            this.mCurrStatuses = conversationInfo.mCurrStatuses;
        }

        com.android.server.people.data.ConversationInfo.Builder setShortcutId(@android.annotation.NonNull java.lang.String str) {
            this.mShortcutId = str;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setLocusId(android.content.LocusId locusId) {
            this.mLocusId = locusId;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setContactUri(android.net.Uri uri) {
            this.mContactUri = uri;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setContactPhoneNumber(java.lang.String str) {
            this.mContactPhoneNumber = str;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setNotificationChannelId(java.lang.String str) {
            this.mNotificationChannelId = str;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setParentNotificationChannelId(java.lang.String str) {
            this.mParentNotificationChannelId = str;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setLastEventTimestamp(long j) {
            this.mLastEventTimestamp = j;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setCreationTimestamp(long j) {
            this.mCreationTimestamp = j;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setShortcutFlags(int i) {
            this.mShortcutFlags = i;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setConversationFlags(int i) {
            this.mConversationFlags = i;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setImportant(boolean z) {
            return setConversationFlag(1, z);
        }

        com.android.server.people.data.ConversationInfo.Builder setNotificationSilenced(boolean z) {
            return setConversationFlag(2, z);
        }

        com.android.server.people.data.ConversationInfo.Builder setBubbled(boolean z) {
            return setConversationFlag(4, z);
        }

        com.android.server.people.data.ConversationInfo.Builder setDemoted(boolean z) {
            return setConversationFlag(64, z);
        }

        com.android.server.people.data.ConversationInfo.Builder setPersonImportant(boolean z) {
            return setConversationFlag(8, z);
        }

        com.android.server.people.data.ConversationInfo.Builder setPersonBot(boolean z) {
            return setConversationFlag(16, z);
        }

        com.android.server.people.data.ConversationInfo.Builder setContactStarred(boolean z) {
            return setConversationFlag(32, z);
        }

        private com.android.server.people.data.ConversationInfo.Builder setConversationFlag(int i, boolean z) {
            if (z) {
                return addConversationFlags(i);
            }
            return removeConversationFlags(i);
        }

        private com.android.server.people.data.ConversationInfo.Builder addConversationFlags(int i) {
            this.mConversationFlags = i | this.mConversationFlags;
            return this;
        }

        private com.android.server.people.data.ConversationInfo.Builder removeConversationFlags(int i) {
            this.mConversationFlags = (~i) & this.mConversationFlags;
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder setStatuses(java.util.List<android.app.people.ConversationStatus> list) {
            this.mCurrStatuses.clear();
            if (list != null) {
                for (android.app.people.ConversationStatus conversationStatus : list) {
                    this.mCurrStatuses.put(conversationStatus.getId(), conversationStatus);
                }
            }
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder addOrUpdateStatus(android.app.people.ConversationStatus conversationStatus) {
            this.mCurrStatuses.put(conversationStatus.getId(), conversationStatus);
            return this;
        }

        com.android.server.people.data.ConversationInfo.Builder clearStatus(java.lang.String str) {
            this.mCurrStatuses.remove(str);
            return this;
        }

        com.android.server.people.data.ConversationInfo build() {
            java.util.Objects.requireNonNull(this.mShortcutId);
            return new com.android.server.people.data.ConversationInfo(this);
        }
    }
}
