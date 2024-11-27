package android.media;

/* loaded from: classes2.dex */
public class SRTRenderer extends android.media.SubtitleController.Renderer {
    private final android.content.Context mContext;
    private final android.os.Handler mEventHandler;
    private final boolean mRender;
    private android.media.WebVttRenderingWidget mRenderingWidget;

    public SRTRenderer(android.content.Context context) {
        this(context, null);
    }

    SRTRenderer(android.content.Context context, android.os.Handler handler) {
        this.mContext = context;
        this.mRender = handler == null;
        this.mEventHandler = handler;
    }

    @Override // android.media.SubtitleController.Renderer
    public boolean supports(android.media.MediaFormat mediaFormat) {
        if (mediaFormat.containsKey(android.media.MediaFormat.KEY_MIME) && mediaFormat.getString(android.media.MediaFormat.KEY_MIME).equals("application/x-subrip")) {
            return this.mRender == (mediaFormat.getInteger(android.media.MediaFormat.KEY_IS_TIMED_TEXT, 0) == 0);
        }
        return false;
    }

    @Override // android.media.SubtitleController.Renderer
    public android.media.SubtitleTrack createTrack(android.media.MediaFormat mediaFormat) {
        if (this.mRender && this.mRenderingWidget == null) {
            this.mRenderingWidget = new android.media.WebVttRenderingWidget(this.mContext);
        }
        if (this.mRender) {
            return new android.media.SRTTrack(this.mRenderingWidget, mediaFormat);
        }
        return new android.media.SRTTrack(this.mEventHandler, mediaFormat);
    }
}
