package android.view;

/* loaded from: classes4.dex */
public interface WindowCallbacks {
    boolean onContentDrawn(int i, int i2, int i3, int i4);

    void onPostDraw(android.graphics.RecordingCanvas recordingCanvas);

    void onRequestDraw(boolean z);

    void onWindowDragResizeEnd();

    void onWindowDragResizeStart(android.graphics.Rect rect, boolean z, android.graphics.Rect rect2, android.graphics.Rect rect3);

    void onWindowSizeIsChanging(android.graphics.Rect rect, boolean z, android.graphics.Rect rect2, android.graphics.Rect rect3);
}
