package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class TextTrackCueSpan {
    boolean mEnabled;
    java.lang.String mText;
    long mTimestampMs;

    TextTrackCueSpan(java.lang.String str, long j) {
        this.mTimestampMs = j;
        this.mText = str;
        this.mEnabled = this.mTimestampMs < 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.media.TextTrackCueSpan)) {
            return false;
        }
        android.media.TextTrackCueSpan textTrackCueSpan = (android.media.TextTrackCueSpan) obj;
        return this.mTimestampMs == textTrackCueSpan.mTimestampMs && this.mText.equals(textTrackCueSpan.mText);
    }
}
