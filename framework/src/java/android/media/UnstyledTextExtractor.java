package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class UnstyledTextExtractor implements android.media.Tokenizer.OnTokenListener {
    long mLastTimestamp;
    java.lang.StringBuilder mLine = new java.lang.StringBuilder();
    java.util.Vector<android.media.TextTrackCueSpan[]> mLines = new java.util.Vector<>();
    java.util.Vector<android.media.TextTrackCueSpan> mCurrentLine = new java.util.Vector<>();

    UnstyledTextExtractor() {
        init();
    }

    private void init() {
        this.mLine.delete(0, this.mLine.length());
        this.mLines.clear();
        this.mCurrentLine.clear();
        this.mLastTimestamp = -1L;
    }

    @Override // android.media.Tokenizer.OnTokenListener
    public void onData(java.lang.String str) {
        this.mLine.append(str);
    }

    @Override // android.media.Tokenizer.OnTokenListener
    public void onStart(java.lang.String str, java.lang.String[] strArr, java.lang.String str2) {
    }

    @Override // android.media.Tokenizer.OnTokenListener
    public void onEnd(java.lang.String str) {
    }

    @Override // android.media.Tokenizer.OnTokenListener
    public void onTimeStamp(long j) {
        if (this.mLine.length() > 0 && j != this.mLastTimestamp) {
            this.mCurrentLine.add(new android.media.TextTrackCueSpan(this.mLine.toString(), this.mLastTimestamp));
            this.mLine.delete(0, this.mLine.length());
        }
        this.mLastTimestamp = j;
    }

    @Override // android.media.Tokenizer.OnTokenListener
    public void onLineEnd() {
        if (this.mLine.length() > 0) {
            this.mCurrentLine.add(new android.media.TextTrackCueSpan(this.mLine.toString(), this.mLastTimestamp));
            this.mLine.delete(0, this.mLine.length());
        }
        android.media.TextTrackCueSpan[] textTrackCueSpanArr = new android.media.TextTrackCueSpan[this.mCurrentLine.size()];
        this.mCurrentLine.toArray(textTrackCueSpanArr);
        this.mCurrentLine.clear();
        this.mLines.add(textTrackCueSpanArr);
    }

    public android.media.TextTrackCueSpan[][] getText() {
        if (this.mLine.length() > 0 || this.mCurrentLine.size() > 0) {
            onLineEnd();
        }
        android.media.TextTrackCueSpan[][] textTrackCueSpanArr = new android.media.TextTrackCueSpan[this.mLines.size()][];
        this.mLines.toArray(textTrackCueSpanArr);
        init();
        return textTrackCueSpanArr;
    }
}
