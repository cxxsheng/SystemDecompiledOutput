package com.android.internal.widget;

/* loaded from: classes5.dex */
final class MessagingData {
    private com.android.internal.widget.ConversationHeaderData mConversationHeaderData;
    private final java.util.List<java.util.List<com.android.internal.widget.MessagingMessage>> mGroups;
    private final java.util.List<com.android.internal.widget.MessagingMessage> mHistoricMessagingMessages;
    private final java.util.List<com.android.internal.widget.MessagingMessage> mNewMessagingMessages;
    private final java.util.List<android.app.Person> mSenders;
    private final boolean mShowSpinner;
    private final int mUnreadCount;
    private final android.app.Person mUser;

    MessagingData(android.app.Person person, boolean z, java.util.List<com.android.internal.widget.MessagingMessage> list, java.util.List<com.android.internal.widget.MessagingMessage> list2, java.util.List<java.util.List<com.android.internal.widget.MessagingMessage>> list3, java.util.List<android.app.Person> list4) {
        this(person, z, 0, list, list2, list3, list4, null);
    }

    MessagingData(android.app.Person person, boolean z, int i, java.util.List<com.android.internal.widget.MessagingMessage> list, java.util.List<com.android.internal.widget.MessagingMessage> list2, java.util.List<java.util.List<com.android.internal.widget.MessagingMessage>> list3, java.util.List<android.app.Person> list4, com.android.internal.widget.ConversationHeaderData conversationHeaderData) {
        this.mUser = person;
        this.mShowSpinner = z;
        this.mUnreadCount = i;
        this.mHistoricMessagingMessages = list;
        this.mNewMessagingMessages = list2;
        this.mGroups = list3;
        this.mSenders = list4;
        this.mConversationHeaderData = conversationHeaderData;
    }

    public android.app.Person getUser() {
        return this.mUser;
    }

    public boolean getShowSpinner() {
        return this.mShowSpinner;
    }

    public java.util.List<com.android.internal.widget.MessagingMessage> getHistoricMessagingMessages() {
        return this.mHistoricMessagingMessages;
    }

    public java.util.List<com.android.internal.widget.MessagingMessage> getNewMessagingMessages() {
        return this.mNewMessagingMessages;
    }

    public int getUnreadCount() {
        return this.mUnreadCount;
    }

    public java.util.List<android.app.Person> getSenders() {
        return this.mSenders;
    }

    public java.util.List<java.util.List<com.android.internal.widget.MessagingMessage>> getGroups() {
        return this.mGroups;
    }

    public com.android.internal.widget.ConversationHeaderData getConversationHeaderData() {
        return this.mConversationHeaderData;
    }
}
