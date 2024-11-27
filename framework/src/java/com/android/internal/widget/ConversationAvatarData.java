package com.android.internal.widget;

/* loaded from: classes5.dex */
interface ConversationAvatarData {

    public static final class OneToOneConversationAvatarData implements com.android.internal.widget.ConversationAvatarData {
        final android.graphics.drawable.Drawable mDrawable;

        OneToOneConversationAvatarData(android.graphics.drawable.Drawable drawable) {
            this.mDrawable = drawable;
        }
    }

    public static final class GroupConversationAvatarData implements com.android.internal.widget.ConversationAvatarData {
        final android.graphics.drawable.Drawable mLastIcon;
        final android.graphics.drawable.Drawable mSecondLastIcon;

        GroupConversationAvatarData(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2) {
            this.mLastIcon = drawable;
            this.mSecondLastIcon = drawable2;
        }
    }
}
