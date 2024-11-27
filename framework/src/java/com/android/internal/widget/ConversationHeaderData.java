package com.android.internal.widget;

/* loaded from: classes5.dex */
final class ConversationHeaderData {
    private final com.android.internal.widget.ConversationAvatarData mConversationAvatarData;
    private final java.lang.CharSequence mConversationText;

    ConversationHeaderData(java.lang.CharSequence charSequence, com.android.internal.widget.ConversationAvatarData conversationAvatarData) {
        this.mConversationText = charSequence;
        this.mConversationAvatarData = conversationAvatarData;
    }

    java.lang.CharSequence getConversationText() {
        return this.mConversationText;
    }

    com.android.internal.widget.ConversationAvatarData getConversationAvatar() {
        return this.mConversationAvatarData;
    }
}
