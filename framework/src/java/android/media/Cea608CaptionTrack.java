package android.media;

/* compiled from: ClosedCaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea608CaptionTrack extends android.media.SubtitleTrack {
    private final android.media.Cea608CCParser mCCParser;
    private final android.media.Cea608CCWidget mRenderingWidget;

    Cea608CaptionTrack(android.media.Cea608CCWidget cea608CCWidget, android.media.MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mRenderingWidget = cea608CCWidget;
        this.mCCParser = new android.media.Cea608CCParser(this.mRenderingWidget);
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
