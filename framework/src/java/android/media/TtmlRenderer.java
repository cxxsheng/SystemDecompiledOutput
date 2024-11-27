package android.media;

/* loaded from: classes2.dex */
public class TtmlRenderer extends android.media.SubtitleController.Renderer {
    private static final java.lang.String MEDIA_MIMETYPE_TEXT_TTML = "application/ttml+xml";
    private final android.content.Context mContext;
    private android.media.TtmlRenderingWidget mRenderingWidget;

    public TtmlRenderer(android.content.Context context) {
        this.mContext = context;
    }

    @Override // android.media.SubtitleController.Renderer
    public boolean supports(android.media.MediaFormat mediaFormat) {
        if (mediaFormat.containsKey(android.media.MediaFormat.KEY_MIME)) {
            return mediaFormat.getString(android.media.MediaFormat.KEY_MIME).equals(MEDIA_MIMETYPE_TEXT_TTML);
        }
        return false;
    }

    @Override // android.media.SubtitleController.Renderer
    public android.media.SubtitleTrack createTrack(android.media.MediaFormat mediaFormat) {
        if (this.mRenderingWidget == null) {
            this.mRenderingWidget = new android.media.TtmlRenderingWidget(this.mContext);
        }
        return new android.media.TtmlTrack(this.mRenderingWidget, mediaFormat);
    }
}
