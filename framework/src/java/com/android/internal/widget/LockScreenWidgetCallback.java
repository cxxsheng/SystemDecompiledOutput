package com.android.internal.widget;

/* loaded from: classes5.dex */
public interface LockScreenWidgetCallback {
    boolean isVisible(android.view.View view);

    void requestHide(android.view.View view);

    void requestShow(android.view.View view);

    void userActivity(android.view.View view);
}
