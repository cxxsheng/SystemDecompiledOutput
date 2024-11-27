package android.media;

/* compiled from: Cea708CaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea708CCWidget extends android.media.ClosedCaptionWidget implements android.media.Cea708CCParser.DisplayListener {
    private final android.media.Cea708CCWidget.CCHandler mCCHandler;

    public Cea708CCWidget(android.content.Context context) {
        this(context, null);
    }

    public Cea708CCWidget(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Cea708CCWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Cea708CCWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCCHandler = new android.media.Cea708CCWidget.CCHandler((android.media.Cea708CCWidget.CCLayout) this.mClosedCaptionLayout);
    }

    @Override // android.media.ClosedCaptionWidget
    public android.media.ClosedCaptionWidget.ClosedCaptionLayout createCaptionLayout(android.content.Context context) {
        return new android.media.Cea708CCWidget.CCLayout(context);
    }

    @Override // android.media.Cea708CCParser.DisplayListener
    public void emitEvent(android.media.Cea708CCParser.CaptionEvent captionEvent) {
        this.mCCHandler.processCaptionEvent(captionEvent);
        setSize(getWidth(), getHeight());
        if (this.mListener != null) {
            this.mListener.onChanged(this);
        }
    }

    @Override // android.view.View
    public void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        ((android.view.ViewGroup) this.mClosedCaptionLayout).draw(canvas);
    }

    /* compiled from: Cea708CaptionRenderer.java */
    static class ScaledLayout extends android.view.ViewGroup {
        private static final boolean DEBUG = false;
        private static final java.lang.String TAG = "ScaledLayout";
        private static final java.util.Comparator<android.graphics.Rect> mRectTopLeftSorter = new java.util.Comparator<android.graphics.Rect>() { // from class: android.media.Cea708CCWidget.ScaledLayout.1
            @Override // java.util.Comparator
            public int compare(android.graphics.Rect rect, android.graphics.Rect rect2) {
                if (rect.top != rect2.top) {
                    return rect.top - rect2.top;
                }
                return rect.left - rect2.left;
            }
        };
        private android.graphics.Rect[] mRectArray;

        public ScaledLayout(android.content.Context context) {
            super(context);
        }

        /* compiled from: Cea708CaptionRenderer.java */
        static class ScaledLayoutParams extends android.view.ViewGroup.LayoutParams {
            public static final float SCALE_UNSPECIFIED = -1.0f;
            public float scaleEndCol;
            public float scaleEndRow;
            public float scaleStartCol;
            public float scaleStartRow;

            public ScaledLayoutParams(float f, float f2, float f3, float f4) {
                super(-1, -1);
                this.scaleStartRow = f;
                this.scaleEndRow = f2;
                this.scaleStartCol = f3;
                this.scaleEndCol = f4;
            }

            public ScaledLayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
                super(-1, -1);
            }
        }

        @Override // android.view.ViewGroup
        public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
            return new android.media.Cea708CCWidget.ScaledLayout.ScaledLayoutParams(getContext(), attributeSet);
        }

        @Override // android.view.ViewGroup
        protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            return layoutParams instanceof android.media.Cea708CCWidget.ScaledLayout.ScaledLayoutParams;
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            int i3;
            int size = android.view.View.MeasureSpec.getSize(i);
            int size2 = android.view.View.MeasureSpec.getSize(i2);
            int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
            int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
            int childCount = getChildCount();
            this.mRectArray = new android.graphics.Rect[childCount];
            int i4 = 0;
            while (i4 < childCount) {
                android.view.View childAt = getChildAt(i4);
                android.view.ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (!(layoutParams instanceof android.media.Cea708CCWidget.ScaledLayout.ScaledLayoutParams)) {
                    throw new java.lang.RuntimeException("A child of ScaledLayout cannot have the UNSPECIFIED scale factors");
                }
                android.media.Cea708CCWidget.ScaledLayout.ScaledLayoutParams scaledLayoutParams = (android.media.Cea708CCWidget.ScaledLayout.ScaledLayoutParams) layoutParams;
                float f = scaledLayoutParams.scaleStartRow;
                float f2 = scaledLayoutParams.scaleEndRow;
                float f3 = scaledLayoutParams.scaleStartCol;
                float f4 = scaledLayoutParams.scaleEndCol;
                if (f < 0.0f || f > 1.0f) {
                    throw new java.lang.RuntimeException("A child of ScaledLayout should have a range of scaleStartRow between 0 and 1");
                }
                if (f2 < f || f > 1.0f) {
                    throw new java.lang.RuntimeException("A child of ScaledLayout should have a range of scaleEndRow between scaleStartRow and 1");
                }
                if (f4 < 0.0f || f4 > 1.0f) {
                    throw new java.lang.RuntimeException("A child of ScaledLayout should have a range of scaleStartCol between 0 and 1");
                }
                if (f4 < f3 || f4 > 1.0f) {
                    throw new java.lang.RuntimeException("A child of ScaledLayout should have a range of scaleEndCol between scaleStartCol and 1");
                }
                float f5 = paddingLeft;
                int i5 = paddingLeft;
                float f6 = paddingTop;
                int i6 = size;
                int i7 = size2;
                int i8 = childCount;
                this.mRectArray[i4] = new android.graphics.Rect((int) (f3 * f5), (int) (f * f6), (int) (f4 * f5), (int) (f2 * f6));
                int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec((int) (f5 * (f4 - f3)), 1073741824);
                childAt.measure(makeMeasureSpec, android.view.View.MeasureSpec.makeMeasureSpec(0, 0));
                if (childAt.getMeasuredHeight() > this.mRectArray[i4].height()) {
                    int measuredHeight = ((childAt.getMeasuredHeight() - this.mRectArray[i4].height()) + 1) / 2;
                    this.mRectArray[i4].bottom += measuredHeight;
                    this.mRectArray[i4].top -= measuredHeight;
                    if (this.mRectArray[i4].top < 0) {
                        this.mRectArray[i4].bottom -= this.mRectArray[i4].top;
                        this.mRectArray[i4].top = 0;
                    }
                    if (this.mRectArray[i4].bottom > paddingTop) {
                        this.mRectArray[i4].top -= this.mRectArray[i4].bottom - paddingTop;
                        this.mRectArray[i4].bottom = paddingTop;
                    }
                }
                childAt.measure(makeMeasureSpec, android.view.View.MeasureSpec.makeMeasureSpec((int) (f6 * (f2 - f)), 1073741824));
                i4++;
                paddingLeft = i5;
                size = i6;
                size2 = i7;
                childCount = i8;
            }
            int i9 = size;
            int i10 = size2;
            int i11 = childCount;
            int[] iArr = new int[i11];
            android.graphics.Rect[] rectArr = new android.graphics.Rect[i11];
            int i12 = 0;
            for (int i13 = 0; i13 < i11; i13++) {
                if (getChildAt(i13).getVisibility() == 0) {
                    iArr[i12] = i12;
                    rectArr[i12] = this.mRectArray[i13];
                    i12++;
                }
            }
            java.util.Arrays.sort(rectArr, 0, i12, mRectTopLeftSorter);
            int i14 = 0;
            while (true) {
                i3 = i12 - 1;
                if (i14 >= i3) {
                    break;
                }
                int i15 = i14 + 1;
                for (int i16 = i15; i16 < i12; i16++) {
                    if (android.graphics.Rect.intersects(rectArr[i14], rectArr[i16])) {
                        iArr[i16] = iArr[i14];
                        rectArr[i16].set(rectArr[i16].left, rectArr[i14].bottom, rectArr[i16].right, rectArr[i14].bottom + rectArr[i16].height());
                    }
                }
                i14 = i15;
            }
            while (i3 >= 0) {
                if (rectArr[i3].bottom > paddingTop) {
                    int i17 = rectArr[i3].bottom - paddingTop;
                    for (int i18 = 0; i18 <= i3; i18++) {
                        if (iArr[i3] == iArr[i18]) {
                            rectArr[i18].set(rectArr[i18].left, rectArr[i18].top - i17, rectArr[i18].right, rectArr[i18].bottom - i17);
                        }
                    }
                }
                i3--;
            }
            setMeasuredDimension(i9, i10);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                android.view.View childAt = getChildAt(i5);
                if (childAt.getVisibility() != 8) {
                    childAt.layout(this.mRectArray[i5].left + paddingLeft, this.mRectArray[i5].top + paddingTop, this.mRectArray[i5].right + paddingTop, this.mRectArray[i5].bottom + paddingLeft);
                }
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public void dispatchDraw(android.graphics.Canvas canvas) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = getChildAt(i);
                if (childAt.getVisibility() != 8) {
                    if (i < this.mRectArray.length) {
                        int i2 = this.mRectArray[i].left + paddingLeft;
                        int i3 = this.mRectArray[i].top + paddingTop;
                        int save = canvas.save();
                        canvas.translate(i2, i3);
                        childAt.draw(canvas);
                        canvas.restoreToCount(save);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    static class CCLayout extends android.media.Cea708CCWidget.ScaledLayout implements android.media.ClosedCaptionWidget.ClosedCaptionLayout {
        private static final float SAFE_TITLE_AREA_SCALE_END_X = 0.9f;
        private static final float SAFE_TITLE_AREA_SCALE_END_Y = 0.9f;
        private static final float SAFE_TITLE_AREA_SCALE_START_X = 0.1f;
        private static final float SAFE_TITLE_AREA_SCALE_START_Y = 0.1f;
        private final android.media.Cea708CCWidget.ScaledLayout mSafeTitleAreaLayout;

        public CCLayout(android.content.Context context) {
            super(context);
            this.mSafeTitleAreaLayout = new android.media.Cea708CCWidget.ScaledLayout(context);
            addView(this.mSafeTitleAreaLayout, new android.media.Cea708CCWidget.ScaledLayout.ScaledLayoutParams(0.1f, 0.9f, 0.1f, 0.9f));
        }

        public void addOrUpdateViewToSafeTitleArea(android.media.Cea708CCWidget.CCWindowLayout cCWindowLayout, android.media.Cea708CCWidget.ScaledLayout.ScaledLayoutParams scaledLayoutParams) {
            if (this.mSafeTitleAreaLayout.indexOfChild(cCWindowLayout) < 0) {
                this.mSafeTitleAreaLayout.addView(cCWindowLayout, scaledLayoutParams);
            } else {
                this.mSafeTitleAreaLayout.updateViewLayout(cCWindowLayout, scaledLayoutParams);
            }
        }

        public void removeViewFromSafeTitleArea(android.media.Cea708CCWidget.CCWindowLayout cCWindowLayout) {
            this.mSafeTitleAreaLayout.removeView(cCWindowLayout);
        }

        @Override // android.media.ClosedCaptionWidget.ClosedCaptionLayout
        public void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
            int childCount = this.mSafeTitleAreaLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ((android.media.Cea708CCWidget.CCWindowLayout) this.mSafeTitleAreaLayout.getChildAt(i)).setCaptionStyle(captionStyle);
            }
        }

        @Override // android.media.ClosedCaptionWidget.ClosedCaptionLayout
        public void setFontScale(float f) {
            int childCount = this.mSafeTitleAreaLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ((android.media.Cea708CCWidget.CCWindowLayout) this.mSafeTitleAreaLayout.getChildAt(i)).setFontScale(f);
            }
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    static class CCHandler implements android.os.Handler.Callback {
        private static final int CAPTION_ALL_WINDOWS_BITMAP = 255;
        private static final long CAPTION_CLEAR_INTERVAL_MS = 60000;
        private static final int CAPTION_WINDOWS_MAX = 8;
        private static final boolean DEBUG = false;
        private static final int MSG_CAPTION_CLEAR = 2;
        private static final int MSG_DELAY_CANCEL = 1;
        private static final java.lang.String TAG = "CCHandler";
        private static final int TENTHS_OF_SECOND_IN_MILLIS = 100;
        private final android.media.Cea708CCWidget.CCLayout mCCLayout;
        private android.media.Cea708CCWidget.CCWindowLayout mCurrentWindowLayout;
        private boolean mIsDelayed = false;
        private final android.media.Cea708CCWidget.CCWindowLayout[] mCaptionWindowLayouts = new android.media.Cea708CCWidget.CCWindowLayout[8];
        private final java.util.ArrayList<android.media.Cea708CCParser.CaptionEvent> mPendingCaptionEvents = new java.util.ArrayList<>();
        private final android.os.Handler mHandler = new android.os.Handler(this);

        public CCHandler(android.media.Cea708CCWidget.CCLayout cCLayout) {
            this.mCCLayout = cCLayout;
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    delayCancel();
                    break;
                case 2:
                    clearWindows(255);
                    break;
            }
            return true;
        }

        public void processCaptionEvent(android.media.Cea708CCParser.CaptionEvent captionEvent) {
            if (this.mIsDelayed) {
                this.mPendingCaptionEvents.add(captionEvent);
            }
            switch (captionEvent.type) {
                case 1:
                    sendBufferToCurrentWindow((java.lang.String) captionEvent.obj);
                    break;
                case 2:
                    sendControlToCurrentWindow(((java.lang.Character) captionEvent.obj).charValue());
                    break;
                case 3:
                    setCurrentWindowLayout(((java.lang.Integer) captionEvent.obj).intValue());
                    break;
                case 4:
                    clearWindows(((java.lang.Integer) captionEvent.obj).intValue());
                    break;
                case 5:
                    displayWindows(((java.lang.Integer) captionEvent.obj).intValue());
                    break;
                case 6:
                    hideWindows(((java.lang.Integer) captionEvent.obj).intValue());
                    break;
                case 7:
                    toggleWindows(((java.lang.Integer) captionEvent.obj).intValue());
                    break;
                case 8:
                    deleteWindows(((java.lang.Integer) captionEvent.obj).intValue());
                    break;
                case 9:
                    delay(((java.lang.Integer) captionEvent.obj).intValue());
                    break;
                case 10:
                    delayCancel();
                    break;
                case 11:
                    reset();
                    break;
                case 12:
                    setPenAttr((android.media.Cea708CCParser.CaptionPenAttr) captionEvent.obj);
                    break;
                case 13:
                    setPenColor((android.media.Cea708CCParser.CaptionPenColor) captionEvent.obj);
                    break;
                case 14:
                    setPenLocation((android.media.Cea708CCParser.CaptionPenLocation) captionEvent.obj);
                    break;
                case 15:
                    setWindowAttr((android.media.Cea708CCParser.CaptionWindowAttr) captionEvent.obj);
                    break;
                case 16:
                    defineWindow((android.media.Cea708CCParser.CaptionWindow) captionEvent.obj);
                    break;
            }
        }

        private void setCurrentWindowLayout(int i) {
            android.media.Cea708CCWidget.CCWindowLayout cCWindowLayout;
            if (i < 0 || i >= this.mCaptionWindowLayouts.length || (cCWindowLayout = this.mCaptionWindowLayouts[i]) == null) {
                return;
            }
            this.mCurrentWindowLayout = cCWindowLayout;
        }

        private java.util.ArrayList<android.media.Cea708CCWidget.CCWindowLayout> getWindowsFromBitmap(int i) {
            android.media.Cea708CCWidget.CCWindowLayout cCWindowLayout;
            java.util.ArrayList<android.media.Cea708CCWidget.CCWindowLayout> arrayList = new java.util.ArrayList<>();
            for (int i2 = 0; i2 < 8; i2++) {
                if (((1 << i2) & i) != 0 && (cCWindowLayout = this.mCaptionWindowLayouts[i2]) != null) {
                    arrayList.add(cCWindowLayout);
                }
            }
            return arrayList;
        }

        private void clearWindows(int i) {
            if (i == 0) {
                return;
            }
            java.util.Iterator<android.media.Cea708CCWidget.CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
            while (it.hasNext()) {
                it.next().clear();
            }
        }

        private void displayWindows(int i) {
            if (i == 0) {
                return;
            }
            java.util.Iterator<android.media.Cea708CCWidget.CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
            while (it.hasNext()) {
                it.next().show();
            }
        }

        private void hideWindows(int i) {
            if (i == 0) {
                return;
            }
            java.util.Iterator<android.media.Cea708CCWidget.CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
            while (it.hasNext()) {
                it.next().hide();
            }
        }

        private void toggleWindows(int i) {
            if (i == 0) {
                return;
            }
            java.util.Iterator<android.media.Cea708CCWidget.CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
            while (it.hasNext()) {
                android.media.Cea708CCWidget.CCWindowLayout next = it.next();
                if (next.isShown()) {
                    next.hide();
                } else {
                    next.show();
                }
            }
        }

        private void deleteWindows(int i) {
            if (i == 0) {
                return;
            }
            java.util.Iterator<android.media.Cea708CCWidget.CCWindowLayout> it = getWindowsFromBitmap(i).iterator();
            while (it.hasNext()) {
                android.media.Cea708CCWidget.CCWindowLayout next = it.next();
                next.removeFromCaptionView();
                this.mCaptionWindowLayouts[next.getCaptionWindowId()] = null;
            }
        }

        public void reset() {
            this.mCurrentWindowLayout = null;
            this.mIsDelayed = false;
            this.mPendingCaptionEvents.clear();
            for (int i = 0; i < 8; i++) {
                if (this.mCaptionWindowLayouts[i] != null) {
                    this.mCaptionWindowLayouts[i].removeFromCaptionView();
                }
                this.mCaptionWindowLayouts[i] = null;
            }
            this.mCCLayout.setVisibility(4);
            this.mHandler.removeMessages(2);
        }

        private void setWindowAttr(android.media.Cea708CCParser.CaptionWindowAttr captionWindowAttr) {
            if (this.mCurrentWindowLayout != null) {
                this.mCurrentWindowLayout.setWindowAttr(captionWindowAttr);
            }
        }

        private void defineWindow(android.media.Cea708CCParser.CaptionWindow captionWindow) {
            int i;
            if (captionWindow == null || (i = captionWindow.id) < 0 || i >= this.mCaptionWindowLayouts.length) {
                return;
            }
            android.media.Cea708CCWidget.CCWindowLayout cCWindowLayout = this.mCaptionWindowLayouts[i];
            if (cCWindowLayout == null) {
                cCWindowLayout = new android.media.Cea708CCWidget.CCWindowLayout(this.mCCLayout.getContext());
            }
            cCWindowLayout.initWindow(this.mCCLayout, captionWindow);
            this.mCaptionWindowLayouts[i] = cCWindowLayout;
            this.mCurrentWindowLayout = cCWindowLayout;
        }

        private void delay(int i) {
            if (i < 0 || i > 255) {
                return;
            }
            this.mIsDelayed = true;
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), i * 100);
        }

        private void delayCancel() {
            this.mIsDelayed = false;
            processPendingBuffer();
        }

        private void processPendingBuffer() {
            java.util.Iterator<android.media.Cea708CCParser.CaptionEvent> it = this.mPendingCaptionEvents.iterator();
            while (it.hasNext()) {
                processCaptionEvent(it.next());
            }
            this.mPendingCaptionEvents.clear();
        }

        private void sendControlToCurrentWindow(char c) {
            if (this.mCurrentWindowLayout != null) {
                this.mCurrentWindowLayout.sendControl(c);
            }
        }

        private void sendBufferToCurrentWindow(java.lang.String str) {
            if (this.mCurrentWindowLayout != null) {
                this.mCurrentWindowLayout.sendBuffer(str);
                this.mHandler.removeMessages(2);
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2), 60000L);
            }
        }

        private void setPenAttr(android.media.Cea708CCParser.CaptionPenAttr captionPenAttr) {
            if (this.mCurrentWindowLayout != null) {
                this.mCurrentWindowLayout.setPenAttr(captionPenAttr);
            }
        }

        private void setPenColor(android.media.Cea708CCParser.CaptionPenColor captionPenColor) {
            if (this.mCurrentWindowLayout != null) {
                this.mCurrentWindowLayout.setPenColor(captionPenColor);
            }
        }

        private void setPenLocation(android.media.Cea708CCParser.CaptionPenLocation captionPenLocation) {
            if (this.mCurrentWindowLayout != null) {
                this.mCurrentWindowLayout.setPenLocation(captionPenLocation.row, captionPenLocation.column);
            }
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    static class CCWindowLayout extends android.widget.RelativeLayout implements android.view.View.OnLayoutChangeListener {
        private static final int ANCHOR_HORIZONTAL_16_9_MAX = 209;
        private static final int ANCHOR_HORIZONTAL_MODE_CENTER = 1;
        private static final int ANCHOR_HORIZONTAL_MODE_LEFT = 0;
        private static final int ANCHOR_HORIZONTAL_MODE_RIGHT = 2;
        private static final int ANCHOR_MODE_DIVIDER = 3;
        private static final int ANCHOR_RELATIVE_POSITIONING_MAX = 99;
        private static final int ANCHOR_VERTICAL_MAX = 74;
        private static final int ANCHOR_VERTICAL_MODE_BOTTOM = 2;
        private static final int ANCHOR_VERTICAL_MODE_CENTER = 1;
        private static final int ANCHOR_VERTICAL_MODE_TOP = 0;
        private static final int MAX_COLUMN_COUNT_16_9 = 42;
        private static final float PROPORTION_PEN_SIZE_LARGE = 1.25f;
        private static final float PROPORTION_PEN_SIZE_SMALL = 0.75f;
        private static final java.lang.String TAG = "CCWindowLayout";
        private final android.text.SpannableStringBuilder mBuilder;
        private android.media.Cea708CCWidget.CCLayout mCCLayout;
        private android.media.Cea708CCWidget.CCView mCCView;
        private android.view.accessibility.CaptioningManager.CaptionStyle mCaptionStyle;
        private int mCaptionWindowId;
        private final java.util.List<android.text.style.CharacterStyle> mCharacterStyles;
        private float mFontScale;
        private int mLastCaptionLayoutHeight;
        private int mLastCaptionLayoutWidth;
        private int mRow;
        private int mRowLimit;
        private float mTextSize;
        private java.lang.String mWidestChar;

        public CCWindowLayout(android.content.Context context) {
            this(context, null);
        }

        public CCWindowLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public CCWindowLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0);
        }

        public CCWindowLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mRowLimit = 0;
            this.mBuilder = new android.text.SpannableStringBuilder();
            this.mCharacterStyles = new java.util.ArrayList();
            this.mRow = -1;
            this.mCCView = new android.media.Cea708CCWidget.CCView(context);
            addView(this.mCCView, new android.widget.RelativeLayout.LayoutParams(-2, -2));
            android.view.accessibility.CaptioningManager captioningManager = (android.view.accessibility.CaptioningManager) context.getSystemService(android.content.Context.CAPTIONING_SERVICE);
            this.mFontScale = captioningManager.getFontScale();
            setCaptionStyle(captioningManager.getUserStyle());
            this.mCCView.setText("");
            updateWidestChar();
        }

        public void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
            this.mCaptionStyle = captionStyle;
            this.mCCView.setCaptionStyle(captionStyle);
        }

        public void setFontScale(float f) {
            this.mFontScale = f;
            updateTextSize();
        }

        public int getCaptionWindowId() {
            return this.mCaptionWindowId;
        }

        public void setCaptionWindowId(int i) {
            this.mCaptionWindowId = i;
        }

        public void clear() {
            clearText();
            hide();
        }

        public void show() {
            setVisibility(0);
            requestLayout();
        }

        public void hide() {
            setVisibility(4);
            requestLayout();
        }

        public void setPenAttr(android.media.Cea708CCParser.CaptionPenAttr captionPenAttr) {
            this.mCharacterStyles.clear();
            if (captionPenAttr.italic) {
                this.mCharacterStyles.add(new android.text.style.StyleSpan(2));
            }
            if (captionPenAttr.underline) {
                this.mCharacterStyles.add(new android.text.style.UnderlineSpan());
            }
            switch (captionPenAttr.penSize) {
                case 0:
                    this.mCharacterStyles.add(new android.text.style.RelativeSizeSpan(0.75f));
                    break;
                case 2:
                    this.mCharacterStyles.add(new android.text.style.RelativeSizeSpan(PROPORTION_PEN_SIZE_LARGE));
                    break;
            }
            switch (captionPenAttr.penOffset) {
                case 0:
                    this.mCharacterStyles.add(new android.text.style.SubscriptSpan());
                    break;
                case 2:
                    this.mCharacterStyles.add(new android.text.style.SuperscriptSpan());
                    break;
            }
        }

        public void setPenColor(android.media.Cea708CCParser.CaptionPenColor captionPenColor) {
        }

        public void setPenLocation(int i, int i2) {
            if (this.mRow >= 0) {
                for (int i3 = this.mRow; i3 < i; i3++) {
                    appendText("\n");
                }
            }
            this.mRow = i;
        }

        public void setWindowAttr(android.media.Cea708CCParser.CaptionWindowAttr captionWindowAttr) {
        }

        public void sendBuffer(java.lang.String str) {
            appendText(str);
        }

        public void sendControl(char c) {
        }

        public void initWindow(android.media.Cea708CCWidget.CCLayout cCLayout, android.media.Cea708CCParser.CaptionWindow captionWindow) {
            float f;
            float f2;
            if (this.mCCLayout != cCLayout) {
                if (this.mCCLayout != null) {
                    this.mCCLayout.removeOnLayoutChangeListener(this);
                }
                this.mCCLayout = cCLayout;
                this.mCCLayout.addOnLayoutChangeListener(this);
                updateWidestChar();
            }
            float f3 = captionWindow.anchorVertical / (captionWindow.relativePositioning ? 99 : 74);
            float f4 = captionWindow.anchorHorizontal / (captionWindow.relativePositioning ? 99 : 209);
            float f5 = 0.0f;
            float f6 = 1.0f;
            if (f3 < 0.0f || f3 > 1.0f) {
                android.util.Log.i(TAG, "The vertical position of the anchor point should be at the range of 0 and 1 but " + f3);
                f3 = java.lang.Math.max(0.0f, java.lang.Math.min(f3, 1.0f));
            }
            if (f4 < 0.0f || f4 > 1.0f) {
                android.util.Log.i(TAG, "The horizontal position of the anchor point should be at the range of 0 and 1 but " + f4);
                f4 = java.lang.Math.max(0.0f, java.lang.Math.min(f4, 1.0f));
            }
            int i = 3;
            int i2 = captionWindow.anchorId % 3;
            int i3 = captionWindow.anchorId / 3;
            switch (i2) {
                case 0:
                    this.mCCView.setAlignment(android.text.Layout.Alignment.ALIGN_NORMAL);
                    f = 1.0f;
                    break;
                case 1:
                    float min = java.lang.Math.min(1.0f - f4, f4);
                    int min2 = java.lang.Math.min(getScreenColumnCount(), captionWindow.columnCount + 1);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    for (int i4 = 0; i4 < min2; i4++) {
                        sb.append(this.mWidestChar);
                    }
                    android.graphics.Paint paint = new android.graphics.Paint();
                    paint.setTypeface(this.mCaptionStyle.getTypeface());
                    paint.setTextSize(this.mTextSize);
                    float measureText = paint.measureText(sb.toString());
                    if (this.mCCLayout.getWidth() <= 0) {
                        f2 = 0.0f;
                    } else {
                        f2 = (measureText / 2.0f) / (this.mCCLayout.getWidth() * 0.8f);
                    }
                    if (f2 > 0.0f && f2 < f4) {
                        this.mCCView.setAlignment(android.text.Layout.Alignment.ALIGN_NORMAL);
                        f4 -= f2;
                        f = 1.0f;
                        break;
                    } else {
                        this.mCCView.setAlignment(android.text.Layout.Alignment.ALIGN_CENTER);
                        float f7 = f4 - min;
                        f = f4 + min;
                        f4 = f7;
                        i = 1;
                        break;
                    }
                case 2:
                    this.mCCView.setAlignment(android.text.Layout.Alignment.ALIGN_RIGHT);
                    i = 5;
                    f = f4;
                    f4 = 0.0f;
                    break;
                default:
                    i = 17;
                    f4 = 0.0f;
                    f = 1.0f;
                    break;
            }
            switch (i3) {
                case 0:
                    i |= 48;
                    f5 = f3;
                    break;
                case 1:
                    i |= 16;
                    float min3 = java.lang.Math.min(1.0f - f3, f3);
                    float f8 = f3 - min3;
                    float f9 = f3 + min3;
                    f5 = f8;
                    f6 = f9;
                    break;
                case 2:
                    i |= 80;
                    f6 = f3;
                    break;
            }
            this.mCCLayout.addOrUpdateViewToSafeTitleArea(this, new android.media.Cea708CCWidget.ScaledLayout.ScaledLayoutParams(f5, f6, f4, f));
            setCaptionWindowId(captionWindow.id);
            setRowLimit(captionWindow.rowCount);
            setGravity(i);
            if (captionWindow.visible) {
                show();
            } else {
                hide();
            }
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int i9 = i3 - i;
            int i10 = i4 - i2;
            if (i9 != this.mLastCaptionLayoutWidth || i10 != this.mLastCaptionLayoutHeight) {
                this.mLastCaptionLayoutWidth = i9;
                this.mLastCaptionLayoutHeight = i10;
                updateTextSize();
            }
        }

        private void updateWidestChar() {
            android.graphics.Paint paint = new android.graphics.Paint();
            paint.setTypeface(this.mCaptionStyle.getTypeface());
            java.nio.charset.Charset forName = java.nio.charset.Charset.forName("ISO-8859-1");
            float f = 0.0f;
            for (int i = 0; i < 256; i++) {
                java.lang.String str = new java.lang.String(new byte[]{(byte) i}, forName);
                float measureText = paint.measureText(str);
                if (f < measureText) {
                    this.mWidestChar = str;
                    f = measureText;
                }
            }
            updateTextSize();
        }

        private void updateTextSize() {
            if (this.mCCLayout == null) {
                return;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int screenColumnCount = getScreenColumnCount();
            for (int i = 0; i < screenColumnCount; i++) {
                sb.append(this.mWidestChar);
            }
            java.lang.String sb2 = sb.toString();
            android.graphics.Paint paint = new android.graphics.Paint();
            paint.setTypeface(this.mCaptionStyle.getTypeface());
            float f = 0.0f;
            float f2 = 255.0f;
            while (f < f2) {
                float f3 = (f + f2) / 2.0f;
                paint.setTextSize(f3);
                if (this.mCCLayout.getWidth() * 0.8f > paint.measureText(sb2)) {
                    f = f3 + 0.01f;
                } else {
                    f2 = f3 - 0.01f;
                }
            }
            this.mTextSize = f2 * this.mFontScale;
            this.mCCView.setTextSize(this.mTextSize);
        }

        private int getScreenColumnCount() {
            return 42;
        }

        public void removeFromCaptionView() {
            if (this.mCCLayout != null) {
                this.mCCLayout.removeViewFromSafeTitleArea(this);
                this.mCCLayout.removeOnLayoutChangeListener(this);
                this.mCCLayout = null;
            }
        }

        public void setText(java.lang.String str) {
            updateText(str, false);
        }

        public void appendText(java.lang.String str) {
            updateText(str, true);
        }

        public void clearText() {
            this.mBuilder.clear();
            this.mCCView.setText("");
        }

        private void updateText(java.lang.String str, boolean z) {
            if (!z) {
                this.mBuilder.clear();
            }
            if (str != null && str.length() > 0) {
                int length = this.mBuilder.length();
                this.mBuilder.append((java.lang.CharSequence) str);
                java.util.Iterator<android.text.style.CharacterStyle> it = this.mCharacterStyles.iterator();
                while (it.hasNext()) {
                    this.mBuilder.setSpan(it.next(), length, this.mBuilder.length(), 33);
                }
            }
            java.lang.String[] split = android.text.TextUtils.split(this.mBuilder.toString(), "\n");
            this.mBuilder.delete(0, this.mBuilder.length() - android.text.TextUtils.join("\n", java.util.Arrays.copyOfRange(split, java.lang.Math.max(0, split.length - (this.mRowLimit + 1)), split.length)).length());
            int length2 = this.mBuilder.length() - 1;
            int i = 0;
            while (i <= length2 && this.mBuilder.charAt(i) <= ' ') {
                i++;
            }
            int i2 = length2;
            while (i2 >= i && this.mBuilder.charAt(i2) <= ' ') {
                i2--;
            }
            if (i == 0 && i2 == length2) {
                this.mCCView.setText(this.mBuilder);
                return;
            }
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
            spannableStringBuilder.append((java.lang.CharSequence) this.mBuilder);
            if (i2 < length2) {
                spannableStringBuilder.delete(i2 + 1, length2 + 1);
            }
            if (i > 0) {
                spannableStringBuilder.delete(0, i);
            }
            this.mCCView.setText(spannableStringBuilder);
        }

        public void setRowLimit(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("A rowLimit should have a positive number");
            }
            this.mRowLimit = i;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    static class CCView extends com.android.internal.widget.SubtitleView {
        private static final android.view.accessibility.CaptioningManager.CaptionStyle DEFAULT_CAPTION_STYLE = android.view.accessibility.CaptioningManager.CaptionStyle.DEFAULT;

        public CCView(android.content.Context context) {
            this(context, null);
        }

        public CCView(android.content.Context context, android.util.AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public CCView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0);
        }

        public CCView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
        }

        public void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
            setForegroundColor(captionStyle.hasForegroundColor() ? captionStyle.foregroundColor : DEFAULT_CAPTION_STYLE.foregroundColor);
            setBackgroundColor(captionStyle.hasBackgroundColor() ? captionStyle.backgroundColor : DEFAULT_CAPTION_STYLE.backgroundColor);
            setEdgeType(captionStyle.hasEdgeType() ? captionStyle.edgeType : DEFAULT_CAPTION_STYLE.edgeType);
            setEdgeColor(captionStyle.hasEdgeColor() ? captionStyle.edgeColor : DEFAULT_CAPTION_STYLE.edgeColor);
            setTypeface(captionStyle.getTypeface());
        }
    }
}
