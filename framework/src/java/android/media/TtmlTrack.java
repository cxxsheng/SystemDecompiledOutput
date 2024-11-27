package android.media;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlTrack extends android.media.SubtitleTrack implements android.media.TtmlNodeListener {
    private static final java.lang.String TAG = "TtmlTrack";
    private java.lang.Long mCurrentRunID;
    private final android.media.TtmlParser mParser;
    private java.lang.String mParsingData;
    private final android.media.TtmlRenderingWidget mRenderingWidget;
    private android.media.TtmlNode mRootNode;
    private final java.util.TreeSet<java.lang.Long> mTimeEvents;
    private final java.util.LinkedList<android.media.TtmlNode> mTtmlNodes;

    TtmlTrack(android.media.TtmlRenderingWidget ttmlRenderingWidget, android.media.MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mParser = new android.media.TtmlParser(this);
        this.mTtmlNodes = new java.util.LinkedList<>();
        this.mTimeEvents = new java.util.TreeSet<>();
        this.mRenderingWidget = ttmlRenderingWidget;
        this.mParsingData = "";
    }

    @Override // android.media.SubtitleTrack
    public android.media.TtmlRenderingWidget getRenderingWidget() {
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
                this.mParsingData += str;
                if (z) {
                    try {
                        this.mParser.parse(this.mParsingData, this.mCurrentRunID.longValue());
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    } catch (org.xmlpull.v1.XmlPullParserException e2) {
                        e2.printStackTrace();
                    }
                    finishedRun(j);
                    this.mParsingData = "";
                    this.mCurrentRunID = null;
                }
            }
        } catch (java.io.UnsupportedEncodingException e3) {
            android.util.Log.w(TAG, "subtitle data is not UTF-8 encoded: " + e3);
        }
    }

    @Override // android.media.TtmlNodeListener
    public void onTtmlNodeParsed(android.media.TtmlNode ttmlNode) {
        this.mTtmlNodes.addLast(ttmlNode);
        addTimeEvents(ttmlNode);
    }

    @Override // android.media.TtmlNodeListener
    public void onRootNodeParsed(android.media.TtmlNode ttmlNode) {
        this.mRootNode = ttmlNode;
        while (true) {
            android.media.TtmlCue nextResult = getNextResult();
            if (nextResult != null) {
                addCue(nextResult);
            } else {
                this.mRootNode = null;
                this.mTtmlNodes.clear();
                this.mTimeEvents.clear();
                return;
            }
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
        this.mRenderingWidget.setActiveCues(vector);
    }

    public android.media.TtmlCue getNextResult() {
        while (this.mTimeEvents.size() >= 2) {
            long longValue = this.mTimeEvents.pollFirst().longValue();
            long longValue2 = this.mTimeEvents.first().longValue();
            if (!getActiveNodes(longValue, longValue2).isEmpty()) {
                return new android.media.TtmlCue(longValue, longValue2, android.media.TtmlUtils.applySpacePolicy(android.media.TtmlUtils.extractText(this.mRootNode, longValue, longValue2), false), android.media.TtmlUtils.extractTtmlFragment(this.mRootNode, longValue, longValue2));
            }
        }
        return null;
    }

    private void addTimeEvents(android.media.TtmlNode ttmlNode) {
        this.mTimeEvents.add(java.lang.Long.valueOf(ttmlNode.mStartTimeMs));
        this.mTimeEvents.add(java.lang.Long.valueOf(ttmlNode.mEndTimeMs));
        for (int i = 0; i < ttmlNode.mChildren.size(); i++) {
            addTimeEvents(ttmlNode.mChildren.get(i));
        }
    }

    private java.util.List<android.media.TtmlNode> getActiveNodes(long j, long j2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mTtmlNodes.size(); i++) {
            android.media.TtmlNode ttmlNode = this.mTtmlNodes.get(i);
            if (ttmlNode.isActive(j, j2)) {
                arrayList.add(ttmlNode);
            }
        }
        return arrayList;
    }
}
