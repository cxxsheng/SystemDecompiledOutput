package com.android.internal.widget;

/* loaded from: classes5.dex */
public class MessagingMessageState {
    private com.android.internal.widget.MessagingGroup mGroup;
    private final android.view.View mHostView;
    private boolean mIsHidingAnimated;
    private boolean mIsHistoric;
    private android.app.Notification.MessagingStyle.Message mMessage;

    MessagingMessageState(android.view.View view) {
        this.mHostView = view;
    }

    public void setMessage(android.app.Notification.MessagingStyle.Message message) {
        this.mMessage = message;
    }

    public android.app.Notification.MessagingStyle.Message getMessage() {
        return this.mMessage;
    }

    public void setGroup(com.android.internal.widget.MessagingGroup messagingGroup) {
        this.mGroup = messagingGroup;
    }

    public com.android.internal.widget.MessagingGroup getGroup() {
        return this.mGroup;
    }

    public void setIsHistoric(boolean z) {
        this.mIsHistoric = z;
    }

    public void setIsHidingAnimated(boolean z) {
        android.view.ViewParent parent = this.mHostView.getParent();
        this.mIsHidingAnimated = z;
        this.mHostView.invalidate();
        if (parent instanceof android.view.ViewGroup) {
            ((android.view.ViewGroup) parent).invalidate();
        }
    }

    public boolean isHidingAnimated() {
        return this.mIsHidingAnimated;
    }

    public android.view.View getHostView() {
        return this.mHostView;
    }

    public void recycle() {
        this.mHostView.setAlpha(1.0f);
        this.mHostView.setTranslationY(0.0f);
        com.android.internal.widget.MessagingPropertyAnimator.recycle(this.mHostView);
        this.mIsHidingAnimated = false;
        this.mIsHistoric = false;
        this.mGroup = null;
        this.mMessage = null;
    }
}
