package com.android.internal.view;

/* loaded from: classes5.dex */
public interface RootViewSurfaceTaker {
    void onRootViewScrollYChanged(int i);

    android.view.PendingInsetsController providePendingInsetsController();

    void setSurfaceFormat(int i);

    void setSurfaceKeepScreenOn(boolean z);

    void setSurfaceType(int i);

    android.view.InputQueue.Callback willYouTakeTheInputQueue();

    android.view.SurfaceHolder.Callback2 willYouTakeTheSurface();
}
