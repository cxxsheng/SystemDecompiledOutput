package android.media;

/* loaded from: classes2.dex */
public class Cea708CaptionRenderer extends android.media.SubtitleController.Renderer {
    private android.media.Cea708CCWidget mCCWidget;
    private final android.content.Context mContext;

    public Cea708CaptionRenderer(android.content.Context context) {
        this.mContext = context;
    }

    @Override // android.media.SubtitleController.Renderer
    public boolean supports(android.media.MediaFormat mediaFormat) {
        if (mediaFormat.containsKey(android.media.MediaFormat.KEY_MIME)) {
            return "text/cea-708".equals(mediaFormat.getString(android.media.MediaFormat.KEY_MIME));
        }
        return false;
    }

    @Override // android.media.SubtitleController.Renderer
    public android.media.SubtitleTrack createTrack(android.media.MediaFormat mediaFormat) {
        if ("text/cea-708".equals(mediaFormat.getString(android.media.MediaFormat.KEY_MIME))) {
            if (this.mCCWidget == null) {
                this.mCCWidget = new android.media.Cea708CCWidget(this.mContext);
            }
            return new android.media.Cea708CaptionTrack(this.mCCWidget, mediaFormat);
        }
        throw new java.lang.RuntimeException("No matching format: " + mediaFormat.toString());
    }
}
