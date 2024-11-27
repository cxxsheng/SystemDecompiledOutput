package android.media;

/* loaded from: classes2.dex */
public class WebVttRenderer extends android.media.SubtitleController.Renderer {
    private final android.content.Context mContext;
    private android.media.WebVttRenderingWidget mRenderingWidget;

    public WebVttRenderer(android.content.Context context) {
        this.mContext = context;
    }

    @Override // android.media.SubtitleController.Renderer
    public boolean supports(android.media.MediaFormat mediaFormat) {
        if (mediaFormat.containsKey(android.media.MediaFormat.KEY_MIME)) {
            return mediaFormat.getString(android.media.MediaFormat.KEY_MIME).equals("text/vtt");
        }
        return false;
    }

    @Override // android.media.SubtitleController.Renderer
    public android.media.SubtitleTrack createTrack(android.media.MediaFormat mediaFormat) {
        if (this.mRenderingWidget == null) {
            this.mRenderingWidget = new android.media.WebVttRenderingWidget(this.mContext);
        }
        return new android.media.WebVttTrack(this.mRenderingWidget, mediaFormat);
    }
}
