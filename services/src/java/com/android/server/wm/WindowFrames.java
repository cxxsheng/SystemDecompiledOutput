package com.android.server.wm;

/* loaded from: classes3.dex */
public class WindowFrames {
    private static final java.lang.StringBuilder sTmpSB = new java.lang.StringBuilder();
    private boolean mContentChanged;
    private boolean mInsetsChanged;
    private boolean mParentFrameWasClippedByDisplayCutout;
    public final android.graphics.Rect mParentFrame = new android.graphics.Rect();
    public final android.graphics.Rect mDisplayFrame = new android.graphics.Rect();
    final android.graphics.Rect mFrame = new android.graphics.Rect();
    final android.graphics.Rect mLastFrame = new android.graphics.Rect();
    final android.graphics.Rect mRelFrame = new android.graphics.Rect();
    final android.graphics.Rect mLastRelFrame = new android.graphics.Rect();
    private boolean mFrameSizeChanged = false;
    final android.graphics.Rect mCompatFrame = new android.graphics.Rect();
    boolean mLastForceReportingResized = false;
    boolean mForceReportingResized = false;

    public void setFrames(android.graphics.Rect rect, android.graphics.Rect rect2) {
        this.mParentFrame.set(rect);
        this.mDisplayFrame.set(rect2);
    }

    public void setParentFrameWasClippedByDisplayCutout(boolean z) {
        this.mParentFrameWasClippedByDisplayCutout = z;
    }

    boolean parentFrameWasClippedByDisplayCutout() {
        return this.mParentFrameWasClippedByDisplayCutout;
    }

    boolean didFrameSizeChange() {
        return (this.mLastFrame.width() == this.mFrame.width() && this.mLastFrame.height() == this.mFrame.height()) ? false : true;
    }

    boolean setReportResizeHints() {
        this.mLastForceReportingResized |= this.mForceReportingResized;
        this.mFrameSizeChanged |= didFrameSizeChange();
        return this.mLastForceReportingResized || this.mFrameSizeChanged;
    }

    boolean isFrameSizeChangeReported() {
        return this.mFrameSizeChanged || didFrameSizeChange();
    }

    void clearReportResizeHints() {
        this.mLastForceReportingResized = false;
        this.mFrameSizeChanged = false;
    }

    void onResizeHandled() {
        this.mForceReportingResized = false;
    }

    void forceReportingResized() {
        this.mForceReportingResized = true;
    }

    public void setContentChanged(boolean z) {
        this.mContentChanged = z;
    }

    boolean hasContentChanged() {
        return this.mContentChanged;
    }

    void setInsetsChanged(boolean z) {
        this.mInsetsChanged = z;
    }

    boolean hasInsetsChanged() {
        return this.mInsetsChanged;
    }

    public void dumpDebug(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        this.mParentFrame.dumpDebug(protoOutputStream, 1146756268040L);
        this.mDisplayFrame.dumpDebug(protoOutputStream, 1146756268036L);
        this.mFrame.dumpDebug(protoOutputStream, 1146756268037L);
        this.mCompatFrame.dumpDebug(protoOutputStream, 1146756268048L);
        protoOutputStream.end(start);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "Frames: parent=" + this.mParentFrame.toShortString(sTmpSB) + " display=" + this.mDisplayFrame.toShortString(sTmpSB) + " frame=" + this.mFrame.toShortString(sTmpSB) + " last=" + this.mLastFrame.toShortString(sTmpSB) + " insetsChanged=" + this.mInsetsChanged);
    }

    java.lang.String getInsetsChangedInfo() {
        return "forceReportingResized=" + this.mLastForceReportingResized + " insetsChanged=" + this.mInsetsChanged;
    }
}
