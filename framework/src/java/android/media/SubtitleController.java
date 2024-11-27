package android.media;

/* loaded from: classes2.dex */
public class SubtitleController {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int WHAT_HIDE = 2;
    private static final int WHAT_SELECT_DEFAULT_TRACK = 4;
    private static final int WHAT_SELECT_TRACK = 3;
    private static final int WHAT_SHOW = 1;
    private android.media.SubtitleController.Anchor mAnchor;
    private android.view.accessibility.CaptioningManager mCaptioningManager;
    private android.os.Handler mHandler;
    private android.media.SubtitleController.Listener mListener;
    private android.media.SubtitleTrack mSelectedTrack;
    private android.media.MediaTimeProvider mTimeProvider;
    private final android.os.Handler.Callback mCallback = new android.os.Handler.Callback() { // from class: android.media.SubtitleController.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.media.SubtitleController.this.doShow();
                    break;
                case 2:
                    android.media.SubtitleController.this.doHide();
                    break;
                case 3:
                    android.media.SubtitleController.this.doSelectTrack((android.media.SubtitleTrack) message.obj);
                    break;
                case 4:
                    android.media.SubtitleController.this.doSelectDefaultTrack();
                    break;
            }
            return true;
        }
    };
    private android.view.accessibility.CaptioningManager.CaptioningChangeListener mCaptioningChangeListener = new android.view.accessibility.CaptioningManager.CaptioningChangeListener() { // from class: android.media.SubtitleController.2
        @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
        public void onEnabledChanged(boolean z) {
            android.media.SubtitleController.this.selectDefaultTrack();
        }

        @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
        public void onLocaleChanged(java.util.Locale locale) {
            android.media.SubtitleController.this.selectDefaultTrack();
        }
    };
    private boolean mTrackIsExplicit = false;
    private boolean mVisibilityIsExplicit = false;
    private java.util.Vector<android.media.SubtitleController.Renderer> mRenderers = new java.util.Vector<>();
    private boolean mShowing = false;
    private java.util.Vector<android.media.SubtitleTrack> mTracks = new java.util.Vector<>();

    public interface Anchor {
        android.os.Looper getSubtitleLooper();

        void setSubtitleWidget(android.media.SubtitleTrack.RenderingWidget renderingWidget);
    }

    public interface Listener {
        void onSubtitleTrackSelected(android.media.SubtitleTrack subtitleTrack);
    }

    public static abstract class Renderer {
        public abstract android.media.SubtitleTrack createTrack(android.media.MediaFormat mediaFormat);

        public abstract boolean supports(android.media.MediaFormat mediaFormat);
    }

    public SubtitleController(android.content.Context context, android.media.MediaTimeProvider mediaTimeProvider, android.media.SubtitleController.Listener listener) {
        this.mTimeProvider = mediaTimeProvider;
        this.mListener = listener;
        this.mCaptioningManager = (android.view.accessibility.CaptioningManager) context.getSystemService(android.content.Context.CAPTIONING_SERVICE);
    }

    protected void finalize() throws java.lang.Throwable {
        this.mCaptioningManager.removeCaptioningChangeListener(this.mCaptioningChangeListener);
        super.finalize();
    }

    public android.media.SubtitleTrack[] getTracks() {
        android.media.SubtitleTrack[] subtitleTrackArr;
        synchronized (this.mTracks) {
            subtitleTrackArr = new android.media.SubtitleTrack[this.mTracks.size()];
            this.mTracks.toArray(subtitleTrackArr);
        }
        return subtitleTrackArr;
    }

    public android.media.SubtitleTrack getSelectedTrack() {
        return this.mSelectedTrack;
    }

    private android.media.SubtitleTrack.RenderingWidget getRenderingWidget() {
        if (this.mSelectedTrack == null) {
            return null;
        }
        return this.mSelectedTrack.getRenderingWidget();
    }

    public boolean selectTrack(android.media.SubtitleTrack subtitleTrack) {
        if (subtitleTrack != null && !this.mTracks.contains(subtitleTrack)) {
            return false;
        }
        processOnAnchor(this.mHandler.obtainMessage(3, subtitleTrack));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSelectTrack(android.media.SubtitleTrack subtitleTrack) {
        this.mTrackIsExplicit = true;
        if (this.mSelectedTrack == subtitleTrack) {
            return;
        }
        if (this.mSelectedTrack != null) {
            this.mSelectedTrack.hide();
            this.mSelectedTrack.setTimeProvider(null);
        }
        this.mSelectedTrack = subtitleTrack;
        if (this.mAnchor != null) {
            this.mAnchor.setSubtitleWidget(getRenderingWidget());
        }
        if (this.mSelectedTrack != null) {
            this.mSelectedTrack.setTimeProvider(this.mTimeProvider);
            this.mSelectedTrack.show();
        }
        if (this.mListener != null) {
            this.mListener.onSubtitleTrackSelected(subtitleTrack);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.media.SubtitleTrack getDefaultTrack() {
        java.util.Locale locale;
        android.media.SubtitleTrack subtitleTrack;
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        int i2;
        java.util.Locale locale2 = this.mCaptioningManager.getLocale();
        if (locale2 != null) {
            locale = locale2;
        } else {
            locale = java.util.Locale.getDefault();
        }
        boolean z4 = !this.mCaptioningManager.isEnabled();
        synchronized (this.mTracks) {
            java.util.Iterator<android.media.SubtitleTrack> it = this.mTracks.iterator();
            subtitleTrack = null;
            int i3 = -1;
            while (it.hasNext()) {
                android.media.SubtitleTrack next = it.next();
                android.media.MediaFormat format = next.getFormat();
                java.lang.String string = format.getString("language");
                int i4 = 0;
                if (format.getInteger(android.media.MediaFormat.KEY_IS_FORCED_SUBTITLE, 0) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (format.getInteger(android.media.MediaFormat.KEY_IS_AUTOSELECT, 1) != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (format.getInteger(android.media.MediaFormat.KEY_IS_DEFAULT, 0) != 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (locale != null && !locale.getLanguage().equals("") && !locale.getISO3Language().equals(string) && !locale.getLanguage().equals(string)) {
                    i = 0;
                    int i5 = (!z ? 0 : 8) + ((locale2 == null || !z3) ? 0 : 4);
                    if (z2) {
                        i4 = 2;
                    }
                    i2 = i5 + i4 + i;
                    if (z4 || z) {
                        if (((locale2 == null && z3) || (i != 0 && (z2 || z || locale2 != null))) && i2 > i3) {
                            subtitleTrack = next;
                            i3 = i2;
                        }
                    }
                }
                i = 1;
                int i52 = (!z ? 0 : 8) + ((locale2 == null || !z3) ? 0 : 4);
                if (z2) {
                }
                i2 = i52 + i4 + i;
                if (z4) {
                }
                if (locale2 == null) {
                    subtitleTrack = next;
                    i3 = i2;
                }
                subtitleTrack = next;
                i3 = i2;
            }
        }
        return subtitleTrack;
    }

    public void selectDefaultTrack() {
        processOnAnchor(this.mHandler.obtainMessage(4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSelectDefaultTrack() {
        if (this.mTrackIsExplicit) {
            if (!this.mVisibilityIsExplicit) {
                if (this.mCaptioningManager.isEnabled() || (this.mSelectedTrack != null && this.mSelectedTrack.getFormat().getInteger(android.media.MediaFormat.KEY_IS_FORCED_SUBTITLE, 0) != 0)) {
                    show();
                } else if (this.mSelectedTrack != null && this.mSelectedTrack.getTrackType() == 4) {
                    hide();
                }
                this.mVisibilityIsExplicit = false;
                return;
            }
            return;
        }
        android.media.SubtitleTrack defaultTrack = getDefaultTrack();
        if (defaultTrack != null) {
            selectTrack(defaultTrack);
            this.mTrackIsExplicit = false;
            if (!this.mVisibilityIsExplicit) {
                show();
                this.mVisibilityIsExplicit = false;
            }
        }
    }

    public void reset() {
        checkAnchorLooper();
        hide();
        selectTrack(null);
        this.mTracks.clear();
        this.mTrackIsExplicit = false;
        this.mVisibilityIsExplicit = false;
        this.mCaptioningManager.removeCaptioningChangeListener(this.mCaptioningChangeListener);
    }

    public android.media.SubtitleTrack addTrack(android.media.MediaFormat mediaFormat) {
        android.media.SubtitleTrack createTrack;
        synchronized (this.mRenderers) {
            java.util.Iterator<android.media.SubtitleController.Renderer> it = this.mRenderers.iterator();
            while (it.hasNext()) {
                android.media.SubtitleController.Renderer next = it.next();
                if (next.supports(mediaFormat) && (createTrack = next.createTrack(mediaFormat)) != null) {
                    synchronized (this.mTracks) {
                        if (this.mTracks.size() == 0) {
                            this.mCaptioningManager.addCaptioningChangeListener(this.mCaptioningChangeListener);
                        }
                        this.mTracks.add(createTrack);
                    }
                    return createTrack;
                }
            }
            return null;
        }
    }

    public void show() {
        processOnAnchor(this.mHandler.obtainMessage(1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doShow() {
        this.mShowing = true;
        this.mVisibilityIsExplicit = true;
        if (this.mSelectedTrack != null) {
            this.mSelectedTrack.show();
        }
    }

    public void hide() {
        processOnAnchor(this.mHandler.obtainMessage(2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doHide() {
        this.mVisibilityIsExplicit = true;
        if (this.mSelectedTrack != null) {
            this.mSelectedTrack.hide();
        }
        this.mShowing = false;
    }

    public void registerRenderer(android.media.SubtitleController.Renderer renderer) {
        synchronized (this.mRenderers) {
            if (!this.mRenderers.contains(renderer)) {
                this.mRenderers.add(renderer);
            }
        }
    }

    public boolean hasRendererFor(android.media.MediaFormat mediaFormat) {
        synchronized (this.mRenderers) {
            java.util.Iterator<android.media.SubtitleController.Renderer> it = this.mRenderers.iterator();
            while (it.hasNext()) {
                if (it.next().supports(mediaFormat)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void setAnchor(android.media.SubtitleController.Anchor anchor) {
        if (this.mAnchor == anchor) {
            return;
        }
        if (this.mAnchor != null) {
            checkAnchorLooper();
            this.mAnchor.setSubtitleWidget(null);
        }
        this.mAnchor = anchor;
        this.mHandler = null;
        if (this.mAnchor != null) {
            this.mHandler = new android.os.Handler(this.mAnchor.getSubtitleLooper(), this.mCallback);
            checkAnchorLooper();
            this.mAnchor.setSubtitleWidget(getRenderingWidget());
        }
    }

    private void checkAnchorLooper() {
    }

    private void processOnAnchor(android.os.Message message) {
        if (android.os.Looper.myLooper() == this.mHandler.getLooper()) {
            this.mHandler.dispatchMessage(message);
        } else {
            this.mHandler.sendMessage(message);
        }
    }
}
