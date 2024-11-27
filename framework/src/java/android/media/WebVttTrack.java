package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class WebVttTrack extends android.media.SubtitleTrack implements android.media.WebVttCueListener {
    private static final java.lang.String TAG = "WebVttTrack";
    private java.lang.Long mCurrentRunID;
    private final android.media.UnstyledTextExtractor mExtractor;
    private final android.media.WebVttParser mParser;
    private final java.util.Map<java.lang.String, android.media.TextTrackRegion> mRegions;
    private final android.media.WebVttRenderingWidget mRenderingWidget;
    private final java.util.Vector<java.lang.Long> mTimestamps;
    private final android.media.Tokenizer mTokenizer;

    WebVttTrack(android.media.WebVttRenderingWidget webVttRenderingWidget, android.media.MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mParser = new android.media.WebVttParser(this);
        this.mExtractor = new android.media.UnstyledTextExtractor();
        this.mTokenizer = new android.media.Tokenizer(this.mExtractor);
        this.mTimestamps = new java.util.Vector<>();
        this.mRegions = new java.util.HashMap();
        this.mRenderingWidget = webVttRenderingWidget;
    }

    @Override // android.media.SubtitleTrack
    public android.media.WebVttRenderingWidget getRenderingWidget() {
        return this.mRenderingWidget;
    }

    @Override // android.media.SubtitleTrack
    public void onData(byte[] bArr, boolean z, long j) {
        try {
            java.lang.String str = new java.lang.String(bArr, android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
            synchronized (this.mParser) {
                if (this.mCurrentRunID != null && j != this.mCurrentRunID.longValue()) {
                    throw new java.lang.IllegalStateException("Run #" + this.mCurrentRunID + " in progress.  Cannot process run #" + j);
                }
                this.mCurrentRunID = java.lang.Long.valueOf(j);
                this.mParser.parse(str);
                if (z) {
                    finishedRun(j);
                    this.mParser.eos();
                    this.mRegions.clear();
                    this.mCurrentRunID = null;
                }
            }
        } catch (java.io.UnsupportedEncodingException e) {
            android.util.Log.w(TAG, "subtitle data is not UTF-8 encoded: " + e);
        }
    }

    @Override // android.media.WebVttCueListener
    public void onCueParsed(android.media.TextTrackCue textTrackCue) {
        synchronized (this.mParser) {
            if (textTrackCue.mRegionId.length() != 0) {
                textTrackCue.mRegion = this.mRegions.get(textTrackCue.mRegionId);
            }
            if (this.DEBUG) {
                android.util.Log.v(TAG, "adding cue " + textTrackCue);
            }
            this.mTokenizer.reset();
            for (java.lang.String str : textTrackCue.mStrings) {
                this.mTokenizer.tokenize(str);
            }
            textTrackCue.mLines = this.mExtractor.getText();
            if (this.DEBUG) {
                android.util.Log.v(TAG, textTrackCue.appendLinesToBuilder(textTrackCue.appendStringsToBuilder(new java.lang.StringBuilder()).append(" simplified to: ")).toString());
            }
            for (android.media.TextTrackCueSpan[] textTrackCueSpanArr : textTrackCue.mLines) {
                for (android.media.TextTrackCueSpan textTrackCueSpan : textTrackCueSpanArr) {
                    if (textTrackCueSpan.mTimestampMs > textTrackCue.mStartTimeMs && textTrackCueSpan.mTimestampMs < textTrackCue.mEndTimeMs && !this.mTimestamps.contains(java.lang.Long.valueOf(textTrackCueSpan.mTimestampMs))) {
                        this.mTimestamps.add(java.lang.Long.valueOf(textTrackCueSpan.mTimestampMs));
                    }
                }
            }
            if (this.mTimestamps.size() > 0) {
                textTrackCue.mInnerTimesMs = new long[this.mTimestamps.size()];
                for (int i = 0; i < this.mTimestamps.size(); i++) {
                    textTrackCue.mInnerTimesMs[i] = this.mTimestamps.get(i).longValue();
                }
                this.mTimestamps.clear();
            } else {
                textTrackCue.mInnerTimesMs = null;
            }
            textTrackCue.mRunID = this.mCurrentRunID.longValue();
        }
        addCue(textTrackCue);
    }

    @Override // android.media.WebVttCueListener
    public void onRegionParsed(android.media.TextTrackRegion textTrackRegion) {
        synchronized (this.mParser) {
            this.mRegions.put(textTrackRegion.mId, textTrackRegion);
        }
    }

    @Override // android.media.SubtitleTrack
    public void updateView(java.util.Vector<android.media.SubtitleTrack.Cue> vector) {
        if (!this.mVisible) {
            return;
        }
        if (this.DEBUG && this.mTimeProvider != null) {
            try {
                android.util.Log.d(TAG, "at " + (this.mTimeProvider.getCurrentTimeUs(false, true) / 1000) + " ms the active cues are:");
            } catch (java.lang.IllegalStateException e) {
                android.util.Log.d(TAG, "at (illegal state) the active cues are:");
            }
        }
        if (this.mRenderingWidget != null) {
            this.mRenderingWidget.setActiveCues(vector);
        }
    }
}
