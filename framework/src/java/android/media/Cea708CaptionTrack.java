package android.media;

/* compiled from: Cea708CaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea708CaptionTrack extends android.media.SubtitleTrack {
    private final android.media.Cea708CCParser mCCParser;
    private final android.media.Cea708CCWidget mRenderingWidget;

    Cea708CaptionTrack(android.media.Cea708CCWidget cea708CCWidget, android.media.MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mRenderingWidget = cea708CCWidget;
        this.mCCParser = new android.media.Cea708CCParser(this.mRenderingWidget);
    }

    @Override // android.media.SubtitleTrack
    public void onData(byte[] bArr, boolean z, long j) {
        this.mCCParser.parse(bArr);
    }

    @Override // android.media.SubtitleTrack
    public android.media.SubtitleTrack.RenderingWidget getRenderingWidget() {
        return this.mRenderingWidget;
    }

    @Override // android.media.SubtitleTrack
    public void updateView(java.util.Vector<android.media.SubtitleTrack.Cue> vector) {
    }
}
