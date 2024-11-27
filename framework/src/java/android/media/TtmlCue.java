package android.media;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlCue extends android.media.SubtitleTrack.Cue {
    public java.lang.String mText;
    public java.lang.String mTtmlFragment;

    public TtmlCue(long j, long j2, java.lang.String str, java.lang.String str2) {
        this.mStartTimeMs = j;
        this.mEndTimeMs = j2;
        this.mText = str;
        this.mTtmlFragment = str2;
    }
}
