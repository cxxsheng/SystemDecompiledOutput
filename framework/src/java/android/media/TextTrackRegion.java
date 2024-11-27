package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class TextTrackRegion {
    static final int SCROLL_VALUE_NONE = 300;
    static final int SCROLL_VALUE_SCROLL_UP = 301;
    java.lang.String mId = "";
    float mWidth = 100.0f;
    int mLines = 3;
    float mViewportAnchorPointX = 0.0f;
    float mAnchorPointX = 0.0f;
    float mViewportAnchorPointY = 100.0f;
    float mAnchorPointY = 100.0f;
    int mScrollValue = 300;

    TextTrackRegion() {
    }

    public java.lang.String toString() {
        java.lang.String str;
        java.lang.StringBuilder append = new java.lang.StringBuilder(" {id:\"").append(this.mId).append("\", width:").append(this.mWidth).append(", lines:").append(this.mLines).append(", anchorPoint:(").append(this.mAnchorPointX).append(", ").append(this.mAnchorPointY).append("), viewportAnchorPoints:").append(this.mViewportAnchorPointX).append(", ").append(this.mViewportAnchorPointY).append("), scrollValue:");
        if (this.mScrollValue == 300) {
            str = "none";
        } else {
            str = this.mScrollValue == 301 ? "scroll_up" : "INVALID";
        }
        return append.append(str).append("}").toString();
    }
}
