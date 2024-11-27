package com.android.internal.widget;

/* loaded from: classes5.dex */
public interface IMessagingLayout {
    android.content.Context getContext();

    java.util.ArrayList<com.android.internal.widget.MessagingGroup> getMessagingGroups();

    com.android.internal.widget.MessagingLinearLayout getMessagingLinearLayout();

    void setMessagingClippingDisabled(boolean z);
}
