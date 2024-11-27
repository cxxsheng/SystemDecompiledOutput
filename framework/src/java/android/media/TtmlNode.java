package android.media;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlNode {
    public final java.lang.String mAttributes;
    public final java.util.List<android.media.TtmlNode> mChildren = new java.util.ArrayList();
    public final long mEndTimeMs;
    public final java.lang.String mName;
    public final android.media.TtmlNode mParent;
    public final long mRunId;
    public final long mStartTimeMs;
    public final java.lang.String mText;

    public TtmlNode(java.lang.String str, java.lang.String str2, java.lang.String str3, long j, long j2, android.media.TtmlNode ttmlNode, long j3) {
        this.mName = str;
        this.mAttributes = str2;
        this.mText = str3;
        this.mStartTimeMs = j;
        this.mEndTimeMs = j2;
        this.mParent = ttmlNode;
        this.mRunId = j3;
    }

    public boolean isActive(long j, long j2) {
        return this.mEndTimeMs > j && this.mStartTimeMs < j2;
    }
}
