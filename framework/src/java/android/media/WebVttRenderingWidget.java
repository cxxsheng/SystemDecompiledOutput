package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class WebVttRenderingWidget extends android.view.ViewGroup implements android.media.SubtitleTrack.RenderingWidget {
    private static final boolean DEBUG = false;
    private static final int DEBUG_CUE_BACKGROUND = -2130771968;
    private static final int DEBUG_REGION_BACKGROUND = -2147483393;
    private static final android.view.accessibility.CaptioningManager.CaptionStyle DEFAULT_CAPTION_STYLE = android.view.accessibility.CaptioningManager.CaptionStyle.DEFAULT;
    private static final float LINE_HEIGHT_RATIO = 0.0533f;
    private android.view.accessibility.CaptioningManager.CaptionStyle mCaptionStyle;
    private final android.view.accessibility.CaptioningManager.CaptioningChangeListener mCaptioningListener;
    private final android.util.ArrayMap<android.media.TextTrackCue, android.media.WebVttRenderingWidget.CueLayout> mCueBoxes;
    private float mFontSize;
    private boolean mHasChangeListener;
    private android.media.SubtitleTrack.RenderingWidget.OnChangedListener mListener;
    private final android.view.accessibility.CaptioningManager mManager;
    private final android.util.ArrayMap<android.media.TextTrackRegion, android.media.WebVttRenderingWidget.RegionLayout> mRegionBoxes;

    public WebVttRenderingWidget(android.content.Context context) {
        this(context, null);
    }

    public WebVttRenderingWidget(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WebVttRenderingWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WebVttRenderingWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRegionBoxes = new android.util.ArrayMap<>();
        this.mCueBoxes = new android.util.ArrayMap<>();
        this.mCaptioningListener = new android.view.accessibility.CaptioningManager.CaptioningChangeListener() { // from class: android.media.WebVttRenderingWidget.1
            @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
            public void onFontScaleChanged(float f) {
                android.media.WebVttRenderingWidget.this.setCaptionStyle(android.media.WebVttRenderingWidget.this.mCaptionStyle, f * android.media.WebVttRenderingWidget.this.getHeight() * android.media.WebVttRenderingWidget.LINE_HEIGHT_RATIO);
            }

            @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
            public void onUserStyleChanged(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
                android.media.WebVttRenderingWidget.this.setCaptionStyle(captionStyle, android.media.WebVttRenderingWidget.this.mFontSize);
            }
        };
        setLayerType(1, null);
        this.mManager = (android.view.accessibility.CaptioningManager) context.getSystemService(android.content.Context.CAPTIONING_SERVICE);
        this.mCaptionStyle = this.mManager.getUserStyle();
        this.mFontSize = this.mManager.getFontScale() * getHeight() * LINE_HEIGHT_RATIO;
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setSize(int i, int i2) {
        measure(android.view.View.MeasureSpec.makeMeasureSpec(i, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
        layout(0, 0, i, i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        manageChangeListener();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        manageChangeListener();
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setOnChangedListener(android.media.SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener) {
        this.mListener = onChangedListener;
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setVisible(boolean z) {
        if (z) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
        manageChangeListener();
    }

    private void manageChangeListener() {
        boolean z = isAttachedToWindow() && getVisibility() == 0;
        if (this.mHasChangeListener != z) {
            this.mHasChangeListener = z;
            if (z) {
                this.mManager.addCaptioningChangeListener(this.mCaptioningListener);
                setCaptionStyle(this.mManager.getUserStyle(), this.mManager.getFontScale() * getHeight() * LINE_HEIGHT_RATIO);
            } else {
                this.mManager.removeCaptioningChangeListener(this.mCaptioningListener);
            }
        }
    }

    public void setActiveCues(java.util.Vector<android.media.SubtitleTrack.Cue> vector) {
        android.content.Context context = getContext();
        android.view.accessibility.CaptioningManager.CaptionStyle captionStyle = this.mCaptionStyle;
        float f = this.mFontSize;
        prepForPrune();
        int size = vector.size();
        for (int i = 0; i < size; i++) {
            android.media.TextTrackCue textTrackCue = (android.media.TextTrackCue) vector.get(i);
            android.media.TextTrackRegion textTrackRegion = textTrackCue.mRegion;
            if (textTrackRegion != null) {
                android.media.WebVttRenderingWidget.RegionLayout regionLayout = this.mRegionBoxes.get(textTrackRegion);
                if (regionLayout == null) {
                    regionLayout = new android.media.WebVttRenderingWidget.RegionLayout(context, textTrackRegion, captionStyle, f);
                    this.mRegionBoxes.put(textTrackRegion, regionLayout);
                    addView(regionLayout, -2, -2);
                }
                regionLayout.put(textTrackCue);
            } else {
                android.media.WebVttRenderingWidget.CueLayout cueLayout = this.mCueBoxes.get(textTrackCue);
                if (cueLayout == null) {
                    cueLayout = new android.media.WebVttRenderingWidget.CueLayout(context, textTrackCue, captionStyle, f);
                    this.mCueBoxes.put(textTrackCue, cueLayout);
                    addView(cueLayout, -2, -2);
                }
                cueLayout.update();
                cueLayout.setOrder(i);
            }
        }
        prune();
        setSize(getWidth(), getHeight());
        if (this.mListener != null) {
            this.mListener.onChanged(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle, float f) {
        android.view.accessibility.CaptioningManager.CaptionStyle applyStyle = DEFAULT_CAPTION_STYLE.applyStyle(captionStyle);
        this.mCaptionStyle = applyStyle;
        this.mFontSize = f;
        int size = this.mCueBoxes.size();
        for (int i = 0; i < size; i++) {
            this.mCueBoxes.valueAt(i).setCaptionStyle(applyStyle, f);
        }
        int size2 = this.mRegionBoxes.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mRegionBoxes.valueAt(i2).setCaptionStyle(applyStyle, f);
        }
    }

    private void prune() {
        int size = this.mRegionBoxes.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            android.media.WebVttRenderingWidget.RegionLayout valueAt = this.mRegionBoxes.valueAt(i2);
            if (valueAt.prune()) {
                removeView(valueAt);
                this.mRegionBoxes.removeAt(i2);
                size--;
                i2--;
            }
            i2++;
        }
        int size2 = this.mCueBoxes.size();
        while (i < size2) {
            android.media.WebVttRenderingWidget.CueLayout valueAt2 = this.mCueBoxes.valueAt(i);
            if (!valueAt2.isActive()) {
                removeView(valueAt2);
                this.mCueBoxes.removeAt(i);
                size2--;
                i--;
            }
            i++;
        }
    }

    private void prepForPrune() {
        int size = this.mRegionBoxes.size();
        for (int i = 0; i < size; i++) {
            this.mRegionBoxes.valueAt(i).prepForPrune();
        }
        int size2 = this.mCueBoxes.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mCueBoxes.valueAt(i2).prepForPrune();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = this.mRegionBoxes.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mRegionBoxes.valueAt(i3).measureForParent(i, i2);
        }
        int size2 = this.mCueBoxes.size();
        for (int i4 = 0; i4 < size2; i4++) {
            this.mCueBoxes.valueAt(i4).measureForParent(i, i2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int i6 = i4 - i2;
        setCaptionStyle(this.mCaptionStyle, this.mManager.getFontScale() * LINE_HEIGHT_RATIO * i6);
        int size = this.mRegionBoxes.size();
        for (int i7 = 0; i7 < size; i7++) {
            layoutRegion(i5, i6, this.mRegionBoxes.valueAt(i7));
        }
        int size2 = this.mCueBoxes.size();
        for (int i8 = 0; i8 < size2; i8++) {
            layoutCue(i5, i6, this.mCueBoxes.valueAt(i8));
        }
    }

    private void layoutRegion(int i, int i2, android.media.WebVttRenderingWidget.RegionLayout regionLayout) {
        android.media.TextTrackRegion region = regionLayout.getRegion();
        int measuredHeight = regionLayout.getMeasuredHeight();
        int measuredWidth = regionLayout.getMeasuredWidth();
        int i3 = (int) ((region.mViewportAnchorPointX * (i - measuredWidth)) / 100.0f);
        int i4 = (int) ((region.mViewportAnchorPointY * (i2 - measuredHeight)) / 100.0f);
        regionLayout.layout(i3, i4, measuredWidth + i3, measuredHeight + i4);
    }

    private void layoutCue(int i, int i2, android.media.WebVttRenderingWidget.CueLayout cueLayout) {
        int i3;
        int i4;
        android.media.TextTrackCue cue = cueLayout.getCue();
        int layoutDirection = getLayoutDirection();
        int resolveCueAlignment = resolveCueAlignment(layoutDirection, cue.mAlignment);
        boolean z = cue.mSnapToLines;
        int measuredWidth = (cueLayout.getMeasuredWidth() * 100) / i;
        switch (resolveCueAlignment) {
            case 203:
                i3 = cue.mTextPosition;
                break;
            case 204:
                i3 = cue.mTextPosition - measuredWidth;
                break;
            default:
                i3 = cue.mTextPosition - (measuredWidth / 2);
                break;
        }
        if (layoutDirection == 1) {
            i3 = 100 - i3;
        }
        if (z) {
            int paddingLeft = (getPaddingLeft() * 100) / i;
            int paddingRight = (getPaddingRight() * 100) / i;
            if (i3 < paddingLeft && i3 + measuredWidth > paddingLeft) {
                i3 += paddingLeft;
                measuredWidth -= paddingLeft;
            }
            float f = 100 - paddingRight;
            if (i3 < f && i3 + measuredWidth > f) {
                measuredWidth -= paddingRight;
            }
        }
        int i5 = (i3 * i) / 100;
        int i6 = (measuredWidth * i) / 100;
        int calculateLinePosition = calculateLinePosition(cueLayout);
        int measuredHeight = cueLayout.getMeasuredHeight();
        if (calculateLinePosition < 0) {
            i4 = i2 + (calculateLinePosition * measuredHeight);
        } else {
            i4 = (calculateLinePosition * (i2 - measuredHeight)) / 100;
        }
        cueLayout.layout(i5, i4, i6 + i5, measuredHeight + i4);
    }

    private int calculateLinePosition(android.media.WebVttRenderingWidget.CueLayout cueLayout) {
        android.media.TextTrackCue cue = cueLayout.getCue();
        java.lang.Integer num = cue.mLinePosition;
        boolean z = cue.mSnapToLines;
        boolean z2 = num == null;
        if (!z && !z2 && (num.intValue() < 0 || num.intValue() > 100)) {
            return 100;
        }
        if (!z2) {
            return num.intValue();
        }
        if (z) {
            return -(cueLayout.mOrder + 1);
        }
        return 100;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int resolveCueAlignment(int i, int i2) {
        switch (i2) {
            case 201:
                return i == 0 ? 203 : 204;
            case 202:
                return i == 0 ? 204 : 203;
            default:
                return i2;
        }
    }

    /* compiled from: WebVttRenderer.java */
    private static class RegionLayout extends android.widget.LinearLayout {
        private android.view.accessibility.CaptioningManager.CaptionStyle mCaptionStyle;
        private float mFontSize;
        private final android.media.TextTrackRegion mRegion;
        private final java.util.ArrayList<android.media.WebVttRenderingWidget.CueLayout> mRegionCueBoxes;

        public RegionLayout(android.content.Context context, android.media.TextTrackRegion textTrackRegion, android.view.accessibility.CaptioningManager.CaptionStyle captionStyle, float f) {
            super(context);
            this.mRegionCueBoxes = new java.util.ArrayList<>();
            this.mRegion = textTrackRegion;
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            setOrientation(1);
            setBackgroundColor(captionStyle.windowColor);
        }

        public void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle, float f) {
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            int size = this.mRegionCueBoxes.size();
            for (int i = 0; i < size; i++) {
                this.mRegionCueBoxes.get(i).setCaptionStyle(captionStyle, f);
            }
            setBackgroundColor(captionStyle.windowColor);
        }

        public void measureForParent(int i, int i2) {
            android.media.TextTrackRegion textTrackRegion = this.mRegion;
            measure(android.view.View.MeasureSpec.makeMeasureSpec((((int) textTrackRegion.mWidth) * android.view.View.MeasureSpec.getSize(i)) / 100, Integer.MIN_VALUE), android.view.View.MeasureSpec.makeMeasureSpec(android.view.View.MeasureSpec.getSize(i2), Integer.MIN_VALUE));
        }

        public void prepForPrune() {
            int size = this.mRegionCueBoxes.size();
            for (int i = 0; i < size; i++) {
                this.mRegionCueBoxes.get(i).prepForPrune();
            }
        }

        public void put(android.media.TextTrackCue textTrackCue) {
            int size = this.mRegionCueBoxes.size();
            for (int i = 0; i < size; i++) {
                android.media.WebVttRenderingWidget.CueLayout cueLayout = this.mRegionCueBoxes.get(i);
                if (cueLayout.getCue() == textTrackCue) {
                    cueLayout.update();
                    return;
                }
            }
            android.media.WebVttRenderingWidget.CueLayout cueLayout2 = new android.media.WebVttRenderingWidget.CueLayout(getContext(), textTrackCue, this.mCaptionStyle, this.mFontSize);
            this.mRegionCueBoxes.add(cueLayout2);
            addView(cueLayout2, -2, -2);
            if (getChildCount() > this.mRegion.mLines) {
                removeViewAt(0);
            }
        }

        public boolean prune() {
            int size = this.mRegionCueBoxes.size();
            int i = 0;
            while (i < size) {
                android.media.WebVttRenderingWidget.CueLayout cueLayout = this.mRegionCueBoxes.get(i);
                if (!cueLayout.isActive()) {
                    this.mRegionCueBoxes.remove(i);
                    removeView(cueLayout);
                    size--;
                    i--;
                }
                i++;
            }
            return this.mRegionCueBoxes.isEmpty();
        }

        public android.media.TextTrackRegion getRegion() {
            return this.mRegion;
        }
    }

    /* compiled from: WebVttRenderer.java */
    private static class CueLayout extends android.widget.LinearLayout {
        private boolean mActive;
        private android.view.accessibility.CaptioningManager.CaptionStyle mCaptionStyle;
        public final android.media.TextTrackCue mCue;
        private float mFontSize;
        private int mOrder;

        public CueLayout(android.content.Context context, android.media.TextTrackCue textTrackCue, android.view.accessibility.CaptioningManager.CaptionStyle captionStyle, float f) {
            super(context);
            this.mCue = textTrackCue;
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            int i = textTrackCue.mWritingDirection == 100 ? 1 : 0;
            setOrientation(i);
            switch (textTrackCue.mAlignment) {
                case 200:
                    setGravity(i == 0 ? 16 : 1);
                    break;
                case 201:
                    setGravity(android.view.Gravity.START);
                    break;
                case 202:
                    setGravity(android.view.Gravity.END);
                    break;
                case 203:
                    setGravity(3);
                    break;
                case 204:
                    setGravity(5);
                    break;
            }
            update();
        }

        public void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle, float f) {
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = getChildAt(i);
                if (childAt instanceof android.media.WebVttRenderingWidget.SpanLayout) {
                    ((android.media.WebVttRenderingWidget.SpanLayout) childAt).setCaptionStyle(captionStyle, f);
                }
            }
        }

        public void prepForPrune() {
            this.mActive = false;
        }

        public void update() {
            android.text.Layout.Alignment alignment;
            this.mActive = true;
            removeAllViews();
            switch (android.media.WebVttRenderingWidget.resolveCueAlignment(getLayoutDirection(), this.mCue.mAlignment)) {
                case 203:
                    alignment = android.text.Layout.Alignment.ALIGN_LEFT;
                    break;
                case 204:
                    alignment = android.text.Layout.Alignment.ALIGN_RIGHT;
                    break;
                default:
                    alignment = android.text.Layout.Alignment.ALIGN_CENTER;
                    break;
            }
            android.view.accessibility.CaptioningManager.CaptionStyle captionStyle = this.mCaptionStyle;
            float f = this.mFontSize;
            for (android.media.TextTrackCueSpan[] textTrackCueSpanArr : this.mCue.mLines) {
                android.media.WebVttRenderingWidget.SpanLayout spanLayout = new android.media.WebVttRenderingWidget.SpanLayout(getContext(), textTrackCueSpanArr);
                spanLayout.setAlignment(alignment);
                spanLayout.setCaptionStyle(captionStyle, f);
                addView(spanLayout, -2, -2);
            }
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
        }

        public void measureForParent(int i, int i2) {
            int i3;
            android.media.TextTrackCue textTrackCue = this.mCue;
            int size = android.view.View.MeasureSpec.getSize(i);
            int size2 = android.view.View.MeasureSpec.getSize(i2);
            switch (android.media.WebVttRenderingWidget.resolveCueAlignment(getLayoutDirection(), textTrackCue.mAlignment)) {
                case 200:
                    if (textTrackCue.mTextPosition <= 50) {
                        i3 = textTrackCue.mTextPosition * 2;
                        break;
                    } else {
                        i3 = (100 - textTrackCue.mTextPosition) * 2;
                        break;
                    }
                case 201:
                case 202:
                default:
                    i3 = 0;
                    break;
                case 203:
                    i3 = 100 - textTrackCue.mTextPosition;
                    break;
                case 204:
                    i3 = textTrackCue.mTextPosition;
                    break;
            }
            measure(android.view.View.MeasureSpec.makeMeasureSpec((java.lang.Math.min(textTrackCue.mSize, i3) * size) / 100, Integer.MIN_VALUE), android.view.View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE));
        }

        public void setOrder(int i) {
            this.mOrder = i;
        }

        public boolean isActive() {
            return this.mActive;
        }

        public android.media.TextTrackCue getCue() {
            return this.mCue;
        }
    }

    /* compiled from: WebVttRenderer.java */
    private static class SpanLayout extends com.android.internal.widget.SubtitleView {
        private final android.text.SpannableStringBuilder mBuilder;
        private final android.media.TextTrackCueSpan[] mSpans;

        public SpanLayout(android.content.Context context, android.media.TextTrackCueSpan[] textTrackCueSpanArr) {
            super(context);
            this.mBuilder = new android.text.SpannableStringBuilder();
            this.mSpans = textTrackCueSpanArr;
            update();
        }

        public void update() {
            android.text.SpannableStringBuilder spannableStringBuilder = this.mBuilder;
            android.media.TextTrackCueSpan[] textTrackCueSpanArr = this.mSpans;
            spannableStringBuilder.clear();
            spannableStringBuilder.clearSpans();
            int length = textTrackCueSpanArr.length;
            for (int i = 0; i < length; i++) {
                if (textTrackCueSpanArr[i].mEnabled) {
                    spannableStringBuilder.append((java.lang.CharSequence) textTrackCueSpanArr[i].mText);
                }
            }
            setText(spannableStringBuilder);
        }

        public void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle, float f) {
            setBackgroundColor(captionStyle.backgroundColor);
            setForegroundColor(captionStyle.foregroundColor);
            setEdgeColor(captionStyle.edgeColor);
            setEdgeType(captionStyle.edgeType);
            setTypeface(captionStyle.getTypeface());
            setTextSize(f);
        }
    }
}
