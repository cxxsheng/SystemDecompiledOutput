package com.android.internal.widget;

/* loaded from: classes5.dex */
public interface MessagingMessage extends com.android.internal.widget.MessagingLinearLayout.MessagingChild {
    public static final java.lang.String IMAGE_MIME_TYPE_PREFIX = "image/";

    void finalizeInflate();

    com.android.internal.widget.MessagingMessageState getState();

    int getVisibility();

    void setVisibility(int i);

    static com.android.internal.widget.MessagingMessage createMessage(com.android.internal.widget.IMessagingLayout iMessagingLayout, android.app.Notification.MessagingStyle.Message message, com.android.internal.widget.ImageResolver imageResolver, boolean z) {
        if (hasImage(message) && !android.app.ActivityManager.isLowRamDeviceStatic()) {
            return com.android.internal.widget.MessagingImageMessage.createMessage(iMessagingLayout, message, imageResolver, z);
        }
        return com.android.internal.widget.MessagingTextMessage.createMessage(iMessagingLayout, message, z);
    }

    static void dropCache() {
        com.android.internal.widget.MessagingTextMessage.dropCache();
        com.android.internal.widget.MessagingImageMessage.dropCache();
    }

    static boolean hasImage(android.app.Notification.MessagingStyle.Message message) {
        return (message.getDataUri() == null || message.getDataMimeType() == null || !message.getDataMimeType().startsWith(IMAGE_MIME_TYPE_PREFIX)) ? false : true;
    }

    default boolean setMessage(android.app.Notification.MessagingStyle.Message message, boolean z) {
        getState().setMessage(message);
        return true;
    }

    default android.app.Notification.MessagingStyle.Message getMessage() {
        return getState().getMessage();
    }

    default boolean sameAs(android.app.Notification.MessagingStyle.Message message) {
        android.app.Notification.MessagingStyle.Message message2 = getMessage();
        if (message == null || message2 == null) {
            return message == message2;
        }
        if (java.util.Objects.equals(message.getText(), message2.getText()) && java.util.Objects.equals(message.getSender(), message2.getSender())) {
            return ((message.isRemoteInputHistory() != message2.isRemoteInputHistory()) || java.util.Objects.equals(java.lang.Long.valueOf(message.getTimestamp()), java.lang.Long.valueOf(message2.getTimestamp()))) && java.util.Objects.equals(message.getDataMimeType(), message2.getDataMimeType()) && java.util.Objects.equals(message.getDataUri(), message2.getDataUri());
        }
        return false;
    }

    default boolean sameAs(com.android.internal.widget.MessagingMessage messagingMessage) {
        return sameAs(messagingMessage.getMessage());
    }

    default void removeMessage(java.util.ArrayList<com.android.internal.widget.MessagingLinearLayout.MessagingChild> arrayList) {
        getGroup().removeMessage(this, arrayList);
    }

    default void setMessagingGroup(com.android.internal.widget.MessagingGroup messagingGroup) {
        getState().setGroup(messagingGroup);
    }

    default void setIsHistoric(boolean z) {
        getState().setIsHistoric(z);
    }

    default com.android.internal.widget.MessagingGroup getGroup() {
        return getState().getGroup();
    }

    default void setIsHidingAnimated(boolean z) {
        getState().setIsHidingAnimated(z);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    default boolean isHidingAnimated() {
        return getState().isHidingAnimated();
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    default void hideAnimated() {
        setIsHidingAnimated(true);
        getGroup().performRemoveAnimation(getView(), new java.lang.Runnable() { // from class: com.android.internal.widget.MessagingMessage$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.MessagingMessage.this.lambda$hideAnimated$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default void lambda$hideAnimated$0() {
        setIsHidingAnimated(false);
    }

    default boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    default void recycle() {
        getState().recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    default android.view.View getView() {
        return (android.view.View) this;
    }

    default void setColor(int i) {
    }
}
