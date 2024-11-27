package android.inputmethodservice;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public class KeyboardView extends android.view.View implements android.view.View.OnClickListener {
    private static final int DEBOUNCE_TIME = 70;
    private static final boolean DEBUG = false;
    private static final int DELAY_AFTER_PREVIEW = 70;
    private static final int DELAY_BEFORE_PREVIEW = 0;
    private static final int MSG_LONGPRESS = 4;
    private static final int MSG_REMOVE_PREVIEW = 2;
    private static final int MSG_REPEAT = 3;
    private static final int MSG_SHOW_PREVIEW = 1;
    private static final int MULTITAP_INTERVAL = 800;
    private static final int NOT_A_KEY = -1;
    private static final int REPEAT_INTERVAL = 50;
    private static final int REPEAT_START_DELAY = 400;
    private boolean mAbortKey;
    private android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private android.media.AudioManager mAudioManager;
    private float mBackgroundDimAmount;
    private android.graphics.Bitmap mBuffer;
    private android.graphics.Canvas mCanvas;
    private android.graphics.Rect mClipRegion;
    private final int[] mCoordinates;
    private int mCurrentKey;
    private int mCurrentKeyIndex;
    private long mCurrentKeyTime;
    private android.graphics.Rect mDirtyRect;
    private boolean mDisambiguateSwipe;
    private int[] mDistances;
    private int mDownKey;
    private long mDownTime;
    private boolean mDrawPending;
    private android.view.GestureDetector mGestureDetector;
    android.os.Handler mHandler;
    private boolean mHeadsetRequiredToHearPasswordsAnnounced;
    private boolean mInMultiTap;
    private android.inputmethodservice.Keyboard.Key mInvalidatedKey;
    private android.graphics.drawable.Drawable mKeyBackground;
    private int[] mKeyIndices;
    private int mKeyTextColor;
    private int mKeyTextSize;
    private android.inputmethodservice.Keyboard mKeyboard;
    private android.inputmethodservice.KeyboardView.OnKeyboardActionListener mKeyboardActionListener;
    private boolean mKeyboardChanged;
    private android.inputmethodservice.Keyboard.Key[] mKeys;
    private int mLabelTextSize;
    private int mLastCodeX;
    private int mLastCodeY;
    private int mLastKey;
    private long mLastKeyTime;
    private long mLastMoveTime;
    private int mLastSentIndex;
    private long mLastTapTime;
    private int mLastX;
    private int mLastY;
    private android.inputmethodservice.KeyboardView mMiniKeyboard;
    private java.util.Map<android.inputmethodservice.Keyboard.Key, android.view.View> mMiniKeyboardCache;
    private android.view.View mMiniKeyboardContainer;
    private int mMiniKeyboardOffsetX;
    private int mMiniKeyboardOffsetY;
    private boolean mMiniKeyboardOnScreen;
    private int mOldPointerCount;
    private float mOldPointerX;
    private float mOldPointerY;
    private android.graphics.Rect mPadding;
    private android.graphics.Paint mPaint;
    private android.widget.PopupWindow mPopupKeyboard;
    private int mPopupLayout;
    private android.view.View mPopupParent;
    private int mPopupPreviewX;
    private int mPopupPreviewY;
    private int mPopupX;
    private int mPopupY;
    private boolean mPossiblePoly;
    private boolean mPreviewCentered;
    private int mPreviewHeight;
    private java.lang.StringBuilder mPreviewLabel;
    private int mPreviewOffset;
    private android.widget.PopupWindow mPreviewPopup;
    private android.widget.TextView mPreviewText;
    private int mPreviewTextSizeLarge;
    private boolean mProximityCorrectOn;
    private int mProximityThreshold;
    private int mRepeatKeyIndex;
    private int mShadowColor;
    private float mShadowRadius;
    private boolean mShowPreview;
    private boolean mShowTouchPoints;
    private int mStartX;
    private int mStartY;
    private int mSwipeThreshold;
    private android.inputmethodservice.KeyboardView.SwipeTracker mSwipeTracker;
    private int mTapCount;
    private int mVerticalCorrection;
    private static final int[] KEY_DELETE = {-5};
    private static final int[] LONG_PRESSABLE_STATE_SET = {16843324};
    private static final int LONGPRESS_TIMEOUT = android.view.ViewConfiguration.getLongPressTimeout();
    private static int MAX_NEARBY_KEYS = 12;

    public interface OnKeyboardActionListener {
        void onKey(int i, int[] iArr);

        void onPress(int i);

        void onRelease(int i);

        void onText(java.lang.CharSequence charSequence);

        void swipeDown();

        void swipeLeft();

        void swipeRight();

        void swipeUp();
    }

    public KeyboardView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, com.android.internal.R.attr.keyboardViewStyle);
    }

    public KeyboardView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public KeyboardView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCurrentKeyIndex = -1;
        this.mCoordinates = new int[2];
        this.mPreviewCentered = false;
        this.mShowPreview = true;
        this.mShowTouchPoints = true;
        this.mCurrentKey = -1;
        this.mDownKey = -1;
        this.mKeyIndices = new int[12];
        this.mRepeatKeyIndex = -1;
        this.mClipRegion = new android.graphics.Rect(0, 0, 0, 0);
        this.mSwipeTracker = new android.inputmethodservice.KeyboardView.SwipeTracker();
        this.mOldPointerCount = 1;
        this.mDistances = new int[MAX_NEARBY_KEYS];
        this.mPreviewLabel = new java.lang.StringBuilder(1);
        this.mDirtyRect = new android.graphics.Rect();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.R.styleable.KeyboardView, i, i2);
        android.view.LayoutInflater layoutInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        int indexCount = obtainStyledAttributes.getIndexCount();
        int i3 = 0;
        for (int i4 = 0; i4 < indexCount; i4++) {
            int index = obtainStyledAttributes.getIndex(i4);
            switch (index) {
                case 0:
                    this.mShadowColor = obtainStyledAttributes.getColor(index, 0);
                    break;
                case 1:
                    this.mShadowRadius = obtainStyledAttributes.getFloat(index, 0.0f);
                    break;
                case 2:
                    this.mKeyBackground = obtainStyledAttributes.getDrawable(index);
                    break;
                case 3:
                    this.mKeyTextSize = obtainStyledAttributes.getDimensionPixelSize(index, 18);
                    break;
                case 4:
                    this.mLabelTextSize = obtainStyledAttributes.getDimensionPixelSize(index, 14);
                    break;
                case 5:
                    this.mKeyTextColor = obtainStyledAttributes.getColor(index, -16777216);
                    break;
                case 6:
                    i3 = obtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 7:
                    this.mPreviewOffset = obtainStyledAttributes.getDimensionPixelOffset(index, 0);
                    break;
                case 8:
                    this.mPreviewHeight = obtainStyledAttributes.getDimensionPixelSize(index, 80);
                    break;
                case 9:
                    this.mVerticalCorrection = obtainStyledAttributes.getDimensionPixelOffset(index, 0);
                    break;
                case 10:
                    this.mPopupLayout = obtainStyledAttributes.getResourceId(index, 0);
                    break;
            }
        }
        obtainStyledAttributes.recycle();
        android.content.res.TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(com.android.internal.R.styleable.Theme);
        this.mBackgroundDimAmount = obtainStyledAttributes2.getFloat(2, 0.5f);
        obtainStyledAttributes2.recycle();
        this.mPreviewPopup = new android.widget.PopupWindow(context);
        if (i3 != 0) {
            this.mPreviewText = (android.widget.TextView) layoutInflater.inflate(i3, (android.view.ViewGroup) null);
            this.mPreviewTextSizeLarge = (int) this.mPreviewText.getTextSize();
            this.mPreviewPopup.setContentView(this.mPreviewText);
            this.mPreviewPopup.setBackgroundDrawable(null);
        } else {
            this.mShowPreview = false;
        }
        this.mPreviewPopup.setTouchable(false);
        this.mPopupKeyboard = new android.widget.PopupWindow(context);
        this.mPopupKeyboard.setBackgroundDrawable(null);
        this.mPopupParent = this;
        this.mPaint = new android.graphics.Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize(0);
        this.mPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.mPaint.setAlpha(255);
        this.mPadding = new android.graphics.Rect(0, 0, 0, 0);
        this.mMiniKeyboardCache = new java.util.HashMap();
        this.mKeyBackground.getPadding(this.mPadding);
        this.mSwipeThreshold = (int) (getResources().getDisplayMetrics().density * 500.0f);
        this.mDisambiguateSwipe = getResources().getBoolean(com.android.internal.R.bool.config_swipeDisambiguation);
        this.mAccessibilityManager = android.view.accessibility.AccessibilityManager.getInstance(context);
        this.mAudioManager = (android.media.AudioManager) context.getSystemService("audio");
        resetMultiTap();
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initGestureDetector();
        if (this.mHandler == null) {
            this.mHandler = new android.os.Handler() { // from class: android.inputmethodservice.KeyboardView.1
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    switch (message.what) {
                        case 1:
                            android.inputmethodservice.KeyboardView.this.showKey(message.arg1);
                            break;
                        case 2:
                            android.inputmethodservice.KeyboardView.this.mPreviewText.setVisibility(4);
                            break;
                        case 3:
                            if (android.inputmethodservice.KeyboardView.this.repeatKey()) {
                                sendMessageDelayed(android.os.Message.obtain(this, 3), 50L);
                                break;
                            }
                            break;
                        case 4:
                            android.inputmethodservice.KeyboardView.this.openPopupIfRequired((android.view.MotionEvent) message.obj);
                            break;
                    }
                }
            };
        }
    }

    private void initGestureDetector() {
        if (this.mGestureDetector == null) {
            this.mGestureDetector = new android.view.GestureDetector(getContext(), new android.view.GestureDetector.SimpleOnGestureListener() { // from class: android.inputmethodservice.KeyboardView.2
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onFling(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
                    if (android.inputmethodservice.KeyboardView.this.mPossiblePoly) {
                        return false;
                    }
                    float abs = java.lang.Math.abs(f);
                    float abs2 = java.lang.Math.abs(f2);
                    float x = motionEvent2.getX() - motionEvent.getX();
                    float y = motionEvent2.getY() - motionEvent.getY();
                    int width = android.inputmethodservice.KeyboardView.this.getWidth() / 2;
                    int height = android.inputmethodservice.KeyboardView.this.getHeight() / 2;
                    android.inputmethodservice.KeyboardView.this.mSwipeTracker.computeCurrentVelocity(1000);
                    float xVelocity = android.inputmethodservice.KeyboardView.this.mSwipeTracker.getXVelocity();
                    float yVelocity = android.inputmethodservice.KeyboardView.this.mSwipeTracker.getYVelocity();
                    boolean z = true;
                    if (f <= android.inputmethodservice.KeyboardView.this.mSwipeThreshold || abs2 >= abs || x <= width) {
                        if (f >= (-android.inputmethodservice.KeyboardView.this.mSwipeThreshold) || abs2 >= abs || x >= (-width)) {
                            if (f2 >= (-android.inputmethodservice.KeyboardView.this.mSwipeThreshold) || abs >= abs2 || y >= (-height)) {
                                if (f2 > android.inputmethodservice.KeyboardView.this.mSwipeThreshold && abs < abs2 / 2.0f && y > height) {
                                    if (!android.inputmethodservice.KeyboardView.this.mDisambiguateSwipe || yVelocity >= f2 / 4.0f) {
                                        android.inputmethodservice.KeyboardView.this.swipeDown();
                                        return true;
                                    }
                                } else {
                                    z = false;
                                }
                            } else if (!android.inputmethodservice.KeyboardView.this.mDisambiguateSwipe || yVelocity <= f2 / 4.0f) {
                                android.inputmethodservice.KeyboardView.this.swipeUp();
                                return true;
                            }
                        } else if (!android.inputmethodservice.KeyboardView.this.mDisambiguateSwipe || xVelocity <= f / 4.0f) {
                            android.inputmethodservice.KeyboardView.this.swipeLeft();
                            return true;
                        }
                    } else if (!android.inputmethodservice.KeyboardView.this.mDisambiguateSwipe || xVelocity >= f / 4.0f) {
                        android.inputmethodservice.KeyboardView.this.swipeRight();
                        return true;
                    }
                    if (z) {
                        android.inputmethodservice.KeyboardView.this.detectAndSendKey(android.inputmethodservice.KeyboardView.this.mDownKey, android.inputmethodservice.KeyboardView.this.mStartX, android.inputmethodservice.KeyboardView.this.mStartY, motionEvent.getEventTime());
                    }
                    return false;
                }
            });
            this.mGestureDetector.setIsLongpressEnabled(false);
        }
    }

    public void setOnKeyboardActionListener(android.inputmethodservice.KeyboardView.OnKeyboardActionListener onKeyboardActionListener) {
        this.mKeyboardActionListener = onKeyboardActionListener;
    }

    protected android.inputmethodservice.KeyboardView.OnKeyboardActionListener getOnKeyboardActionListener() {
        return this.mKeyboardActionListener;
    }

    public void setKeyboard(android.inputmethodservice.Keyboard keyboard) {
        if (this.mKeyboard != null) {
            showPreview(-1);
        }
        removeMessages();
        this.mKeyboard = keyboard;
        java.util.List<android.inputmethodservice.Keyboard.Key> keys = this.mKeyboard.getKeys();
        this.mKeys = (android.inputmethodservice.Keyboard.Key[]) keys.toArray(new android.inputmethodservice.Keyboard.Key[keys.size()]);
        requestLayout();
        this.mKeyboardChanged = true;
        invalidateAllKeys();
        computeProximityThreshold(keyboard);
        this.mMiniKeyboardCache.clear();
        this.mAbortKey = true;
    }

    public android.inputmethodservice.Keyboard getKeyboard() {
        return this.mKeyboard;
    }

    public boolean setShifted(boolean z) {
        if (this.mKeyboard != null && this.mKeyboard.setShifted(z)) {
            invalidateAllKeys();
            return true;
        }
        return false;
    }

    public boolean isShifted() {
        if (this.mKeyboard != null) {
            return this.mKeyboard.isShifted();
        }
        return false;
    }

    public void setPreviewEnabled(boolean z) {
        this.mShowPreview = z;
    }

    public boolean isPreviewEnabled() {
        return this.mShowPreview;
    }

    public void setVerticalCorrection(int i) {
    }

    public void setPopupParent(android.view.View view) {
        this.mPopupParent = view;
    }

    public void setPopupOffset(int i, int i2) {
        this.mMiniKeyboardOffsetX = i;
        this.mMiniKeyboardOffsetY = i2;
        if (this.mPreviewPopup.isShowing()) {
            this.mPreviewPopup.dismiss();
        }
    }

    public void setProximityCorrectionEnabled(boolean z) {
        this.mProximityCorrectOn = z;
    }

    public boolean isProximityCorrectionEnabled() {
        return this.mProximityCorrectOn;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        dismissPopupKeyboard();
    }

    private java.lang.CharSequence adjustCase(java.lang.CharSequence charSequence) {
        if (this.mKeyboard.isShifted() && charSequence != null && charSequence.length() < 3 && java.lang.Character.isLowerCase(charSequence.charAt(0))) {
            return charSequence.toString().toUpperCase();
        }
        return charSequence;
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        if (this.mKeyboard == null) {
            setMeasuredDimension(this.mPaddingLeft + this.mPaddingRight, this.mPaddingTop + this.mPaddingBottom);
            return;
        }
        int minWidth = this.mKeyboard.getMinWidth() + this.mPaddingLeft + this.mPaddingRight;
        if (android.view.View.MeasureSpec.getSize(i) < minWidth + 10) {
            minWidth = android.view.View.MeasureSpec.getSize(i);
        }
        setMeasuredDimension(minWidth, this.mKeyboard.getHeight() + this.mPaddingTop + this.mPaddingBottom);
    }

    private void computeProximityThreshold(android.inputmethodservice.Keyboard keyboard) {
        android.inputmethodservice.Keyboard.Key[] keyArr;
        if (keyboard == null || (keyArr = this.mKeys) == null) {
            return;
        }
        int length = keyArr.length;
        int i = 0;
        for (android.inputmethodservice.Keyboard.Key key : keyArr) {
            i += java.lang.Math.min(key.width, key.height) + key.gap;
        }
        if (i < 0 || length == 0) {
            return;
        }
        this.mProximityThreshold = (int) ((i * 1.4f) / length);
        this.mProximityThreshold *= this.mProximityThreshold;
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mKeyboard != null) {
            this.mKeyboard.resize(i, i2);
        }
        this.mBuffer = null;
    }

    @Override // android.view.View
    public void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawPending || this.mBuffer == null || this.mKeyboardChanged) {
            onBufferDraw();
        }
        canvas.drawBitmap(this.mBuffer, 0.0f, 0.0f, (android.graphics.Paint) null);
    }

    private void onBufferDraw() {
        boolean z;
        boolean z2;
        android.graphics.drawable.Drawable drawable;
        android.graphics.Rect rect;
        if (this.mBuffer == null || this.mKeyboardChanged) {
            if (this.mBuffer == null || (this.mKeyboardChanged && (this.mBuffer.getWidth() != getWidth() || this.mBuffer.getHeight() != getHeight()))) {
                this.mBuffer = android.graphics.Bitmap.createBitmap(java.lang.Math.max(1, getWidth()), java.lang.Math.max(1, getHeight()), android.graphics.Bitmap.Config.ARGB_8888);
                this.mCanvas = new android.graphics.Canvas(this.mBuffer);
            }
            invalidateAllKeys();
            this.mKeyboardChanged = false;
        }
        if (this.mKeyboard == null) {
            return;
        }
        this.mCanvas.save();
        android.graphics.Canvas canvas = this.mCanvas;
        canvas.clipRect(this.mDirtyRect);
        android.graphics.Paint paint = this.mPaint;
        android.graphics.drawable.Drawable drawable2 = this.mKeyBackground;
        android.graphics.Rect rect2 = this.mClipRegion;
        android.graphics.Rect rect3 = this.mPadding;
        int i = this.mPaddingLeft;
        int i2 = this.mPaddingTop;
        android.inputmethodservice.Keyboard.Key[] keyArr = this.mKeys;
        android.inputmethodservice.Keyboard.Key key = this.mInvalidatedKey;
        paint.setColor(this.mKeyTextColor);
        if (key != null && canvas.getClipBounds(rect2) && (key.x + i) - 1 <= rect2.left && (key.y + i2) - 1 <= rect2.top && key.x + key.width + i + 1 >= rect2.right && key.y + key.height + i2 + 1 >= rect2.bottom) {
            z = true;
        } else {
            z = false;
        }
        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
        int length = keyArr.length;
        int i3 = 0;
        while (i3 < length) {
            android.inputmethodservice.Keyboard.Key key2 = keyArr[i3];
            if (z && key != key2) {
                drawable = drawable2;
                z2 = z;
                rect = rect3;
            } else {
                drawable2.setState(key2.getCurrentDrawableState());
                java.lang.String charSequence = key2.label == null ? null : adjustCase(key2.label).toString();
                android.graphics.Rect bounds = drawable2.getBounds();
                z2 = z;
                if (key2.width != bounds.right || key2.height != bounds.bottom) {
                    drawable2.setBounds(0, 0, key2.width, key2.height);
                }
                canvas.translate(key2.x + i, key2.y + i2);
                drawable2.draw(canvas);
                if (charSequence != null) {
                    if (charSequence.length() > 1 && key2.codes.length < 2) {
                        paint.setTextSize(this.mLabelTextSize);
                        paint.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
                    } else {
                        paint.setTextSize(this.mKeyTextSize);
                        paint.setTypeface(android.graphics.Typeface.DEFAULT);
                    }
                    paint.setShadowLayer(this.mShadowRadius, 0.0f, 0.0f, this.mShadowColor);
                    canvas.drawText(charSequence, (((key2.width - rect3.left) - rect3.right) / 2) + rect3.left, (((key2.height - rect3.top) - rect3.bottom) / 2) + ((paint.getTextSize() - paint.descent()) / 2.0f) + rect3.top, paint);
                    paint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
                    drawable = drawable2;
                    rect = rect3;
                } else if (key2.icon == null) {
                    drawable = drawable2;
                    rect = rect3;
                } else {
                    canvas.translate(((((key2.width - rect3.left) - rect3.right) - key2.icon.getIntrinsicWidth()) / 2) + rect3.left, ((((key2.height - rect3.top) - rect3.bottom) - key2.icon.getIntrinsicHeight()) / 2) + rect3.top);
                    drawable = drawable2;
                    rect = rect3;
                    key2.icon.setBounds(0, 0, key2.icon.getIntrinsicWidth(), key2.icon.getIntrinsicHeight());
                    key2.icon.draw(canvas);
                    canvas.translate(-r2, -r3);
                }
                canvas.translate((-key2.x) - i, (-key2.y) - i2);
            }
            i3++;
            z = z2;
            drawable2 = drawable;
            rect3 = rect;
        }
        this.mInvalidatedKey = null;
        if (this.mMiniKeyboardOnScreen) {
            paint.setColor(((int) (this.mBackgroundDimAmount * 255.0f)) << 24);
            canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), paint);
        }
        this.mCanvas.restore();
        this.mDrawPending = false;
        this.mDirtyRect.setEmpty();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0048, code lost:
    
        if (r13.codes[r9] <= 32) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x004a, code lost:
    
        r8 = r13.codes.length;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x004d, code lost:
    
        if (r15 >= r5) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0050, code lost:
    
        r12 = r6[r10];
        r5 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0053, code lost:
    
        if (r20 != null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0055, code lost:
    
        r16 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x009c, code lost:
    
        r10 = r10 + 1;
        r1 = r18;
        r2 = r19;
        r4 = r16;
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0058, code lost:
    
        r14 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005c, code lost:
    
        if (r14 >= r17.mDistances.length) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0062, code lost:
    
        if (r17.mDistances[r14] <= r15) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x008d, code lost:
    
        r14 = r14 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0064, code lost:
    
        r2 = r14 + r8;
        r16 = r4;
        java.lang.System.arraycopy(r17.mDistances, r14, r17.mDistances, r2, (r17.mDistances.length - r14) - r8);
        java.lang.System.arraycopy(r20, r14, r20, r2, (r20.length - r14) - r8);
        r1 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007b, code lost:
    
        if (r1 >= r8) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007d, code lost:
    
        r2 = r14 + r1;
        r20[r2] = r13.codes[r1];
        r17.mDistances[r2] = r15;
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0097, code lost:
    
        r16 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x009a, code lost:
    
        r16 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0040, code lost:
    
        if (r14 != false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x003c, code lost:
    
        if (r15 >= r17.mProximityThreshold) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int getKeyIndices(int i, int i2, int[] iArr) {
        int i3;
        int i4 = i;
        int i5 = i2;
        android.inputmethodservice.Keyboard.Key[] keyArr = this.mKeys;
        int i6 = this.mProximityThreshold + 1;
        java.util.Arrays.fill(this.mDistances, Integer.MAX_VALUE);
        int[] nearestKeys = this.mKeyboard.getNearestKeys(i4, i5);
        int length = nearestKeys.length;
        int i7 = 0;
        int i8 = 0;
        int i9 = -1;
        int i10 = -1;
        while (i8 < length) {
            android.inputmethodservice.Keyboard.Key key = keyArr[nearestKeys[i8]];
            boolean isInside = key.isInside(i4, i5);
            if (isInside) {
                i9 = nearestKeys[i8];
            }
            if (!this.mProximityCorrectOn) {
                i3 = i7;
            } else {
                i3 = key.squaredDistanceFrom(i4, i5);
            }
        }
        if (i9 == -1) {
            return i10;
        }
        return i9;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detectAndSendKey(int i, int i2, int i3, long j) {
        if (i != -1 && i < this.mKeys.length) {
            android.inputmethodservice.Keyboard.Key key = this.mKeys[i];
            if (key.text != null) {
                this.mKeyboardActionListener.onText(key.text);
                this.mKeyboardActionListener.onRelease(-1);
            } else {
                int i4 = key.codes[0];
                int[] iArr = new int[MAX_NEARBY_KEYS];
                java.util.Arrays.fill(iArr, -1);
                getKeyIndices(i2, i3, iArr);
                if (this.mInMultiTap) {
                    if (this.mTapCount != -1) {
                        this.mKeyboardActionListener.onKey(-5, KEY_DELETE);
                    } else {
                        this.mTapCount = 0;
                    }
                    i4 = key.codes[this.mTapCount];
                }
                this.mKeyboardActionListener.onKey(i4, iArr);
                this.mKeyboardActionListener.onRelease(i4);
            }
            this.mLastSentIndex = i;
            this.mLastTapTime = j;
        }
    }

    private java.lang.CharSequence getPreviewText(android.inputmethodservice.Keyboard.Key key) {
        if (this.mInMultiTap) {
            this.mPreviewLabel.setLength(0);
            this.mPreviewLabel.append((char) key.codes[this.mTapCount >= 0 ? this.mTapCount : 0]);
            return adjustCase(this.mPreviewLabel);
        }
        return adjustCase(key.label);
    }

    private void showPreview(int i) {
        int i2 = this.mCurrentKeyIndex;
        android.widget.PopupWindow popupWindow = this.mPreviewPopup;
        this.mCurrentKeyIndex = i;
        android.inputmethodservice.Keyboard.Key[] keyArr = this.mKeys;
        if (i2 != this.mCurrentKeyIndex) {
            if (i2 != -1 && keyArr.length > i2) {
                android.inputmethodservice.Keyboard.Key key = keyArr[i2];
                key.onReleased(this.mCurrentKeyIndex == -1);
                invalidateKey(i2);
                int i3 = key.codes[0];
                sendAccessibilityEventForUnicodeCharacter(256, i3);
                sendAccessibilityEventForUnicodeCharacter(65536, i3);
            }
            if (this.mCurrentKeyIndex != -1 && keyArr.length > this.mCurrentKeyIndex) {
                android.inputmethodservice.Keyboard.Key key2 = keyArr[this.mCurrentKeyIndex];
                key2.onPressed();
                invalidateKey(this.mCurrentKeyIndex);
                int i4 = key2.codes[0];
                sendAccessibilityEventForUnicodeCharacter(128, i4);
                sendAccessibilityEventForUnicodeCharacter(32768, i4);
            }
        }
        if (i2 != this.mCurrentKeyIndex && this.mShowPreview) {
            this.mHandler.removeMessages(1);
            if (popupWindow.isShowing() && i == -1) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2), 70L);
            }
            if (i != -1) {
                if (popupWindow.isShowing() && this.mPreviewText.getVisibility() == 0) {
                    showKey(i);
                } else {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, i, 0), 0L);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showKey(int i) {
        android.widget.PopupWindow popupWindow = this.mPreviewPopup;
        android.inputmethodservice.Keyboard.Key[] keyArr = this.mKeys;
        if (i < 0 || i >= this.mKeys.length) {
            return;
        }
        android.inputmethodservice.Keyboard.Key key = keyArr[i];
        if (key.icon != null) {
            this.mPreviewText.setCompoundDrawables(null, null, null, key.iconPreview != null ? key.iconPreview : key.icon);
            this.mPreviewText.lambda$setTextAsync$0((java.lang.CharSequence) null);
        } else {
            this.mPreviewText.setCompoundDrawables(null, null, null, null);
            this.mPreviewText.lambda$setTextAsync$0(getPreviewText(key));
            if (key.label.length() > 1 && key.codes.length < 2) {
                this.mPreviewText.setTextSize(0, this.mKeyTextSize);
                this.mPreviewText.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
            } else {
                this.mPreviewText.setTextSize(0, this.mPreviewTextSizeLarge);
                this.mPreviewText.setTypeface(android.graphics.Typeface.DEFAULT);
            }
        }
        this.mPreviewText.measure(android.view.View.MeasureSpec.makeMeasureSpec(0, 0), android.view.View.MeasureSpec.makeMeasureSpec(0, 0));
        int max = java.lang.Math.max(this.mPreviewText.getMeasuredWidth(), key.width + this.mPreviewText.getPaddingLeft() + this.mPreviewText.getPaddingRight());
        int i2 = this.mPreviewHeight;
        android.view.ViewGroup.LayoutParams layoutParams = this.mPreviewText.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = max;
            layoutParams.height = i2;
        }
        if (!this.mPreviewCentered) {
            this.mPopupPreviewX = (key.x - this.mPreviewText.getPaddingLeft()) + this.mPaddingLeft;
            this.mPopupPreviewY = (key.y - i2) + this.mPreviewOffset;
        } else {
            this.mPopupPreviewX = 160 - (this.mPreviewText.getMeasuredWidth() / 2);
            this.mPopupPreviewY = -this.mPreviewText.getMeasuredHeight();
        }
        this.mHandler.removeMessages(2);
        getLocationInWindow(this.mCoordinates);
        int[] iArr = this.mCoordinates;
        iArr[0] = iArr[0] + this.mMiniKeyboardOffsetX;
        int[] iArr2 = this.mCoordinates;
        iArr2[1] = iArr2[1] + this.mMiniKeyboardOffsetY;
        this.mPreviewText.getBackground().setState(key.popupResId != 0 ? LONG_PRESSABLE_STATE_SET : EMPTY_STATE_SET);
        this.mPopupPreviewX += this.mCoordinates[0];
        this.mPopupPreviewY += this.mCoordinates[1];
        getLocationOnScreen(this.mCoordinates);
        if (this.mPopupPreviewY + this.mCoordinates[1] < 0) {
            if (key.x + key.width <= getWidth() / 2) {
                this.mPopupPreviewX += (int) (key.width * 2.5d);
            } else {
                this.mPopupPreviewX -= (int) (key.width * 2.5d);
            }
            this.mPopupPreviewY += i2;
        }
        if (popupWindow.isShowing()) {
            popupWindow.update(this.mPopupPreviewX, this.mPopupPreviewY, max, i2);
        } else {
            popupWindow.setWidth(max);
            popupWindow.setHeight(i2);
            popupWindow.showAtLocation(this.mPopupParent, 0, this.mPopupPreviewX, this.mPopupPreviewY);
        }
        this.mPreviewText.setVisibility(0);
    }

    private void sendAccessibilityEventForUnicodeCharacter(int i, int i2) {
        java.lang.String string;
        if (this.mAccessibilityManager.isEnabled()) {
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(i);
            onInitializeAccessibilityEvent(obtain);
            switch (i2) {
                case -6:
                    string = this.mContext.getString(com.android.internal.R.string.keyboardview_keycode_alt);
                    break;
                case -5:
                    string = this.mContext.getString(com.android.internal.R.string.keyboardview_keycode_delete);
                    break;
                case -4:
                    string = this.mContext.getString(com.android.internal.R.string.keyboardview_keycode_done);
                    break;
                case -3:
                    string = this.mContext.getString(com.android.internal.R.string.keyboardview_keycode_cancel);
                    break;
                case -2:
                    string = this.mContext.getString(com.android.internal.R.string.keyboardview_keycode_mode_change);
                    break;
                case -1:
                    string = this.mContext.getString(com.android.internal.R.string.keyboardview_keycode_shift);
                    break;
                case 10:
                    string = this.mContext.getString(com.android.internal.R.string.keyboardview_keycode_enter);
                    break;
                default:
                    string = java.lang.String.valueOf((char) i2);
                    break;
            }
            obtain.getText().add(string);
            this.mAccessibilityManager.sendAccessibilityEvent(obtain);
        }
    }

    public void invalidateAllKeys() {
        this.mDirtyRect.union(0, 0, getWidth(), getHeight());
        this.mDrawPending = true;
        invalidate();
    }

    public void invalidateKey(int i) {
        if (this.mKeys == null || i < 0 || i >= this.mKeys.length) {
            return;
        }
        android.inputmethodservice.Keyboard.Key key = this.mKeys[i];
        this.mInvalidatedKey = key;
        this.mDirtyRect.union(key.x + this.mPaddingLeft, key.y + this.mPaddingTop, key.x + key.width + this.mPaddingLeft, key.y + key.height + this.mPaddingTop);
        onBufferDraw();
        invalidate(key.x + this.mPaddingLeft, key.y + this.mPaddingTop, key.x + key.width + this.mPaddingLeft, key.y + key.height + this.mPaddingTop);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean openPopupIfRequired(android.view.MotionEvent motionEvent) {
        if (this.mPopupLayout == 0 || this.mCurrentKey < 0 || this.mCurrentKey >= this.mKeys.length) {
            return false;
        }
        boolean onLongPress = onLongPress(this.mKeys[this.mCurrentKey]);
        if (onLongPress) {
            this.mAbortKey = true;
            showPreview(-1);
        }
        return onLongPress;
    }

    protected boolean onLongPress(android.inputmethodservice.Keyboard.Key key) {
        android.inputmethodservice.Keyboard keyboard;
        int i = key.popupResId;
        if (i == 0) {
            return false;
        }
        this.mMiniKeyboardContainer = this.mMiniKeyboardCache.get(key);
        if (this.mMiniKeyboardContainer == null) {
            this.mMiniKeyboardContainer = ((android.view.LayoutInflater) getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(this.mPopupLayout, (android.view.ViewGroup) null);
            this.mMiniKeyboard = (android.inputmethodservice.KeyboardView) this.mMiniKeyboardContainer.findViewById(16908326);
            android.view.View findViewById = this.mMiniKeyboardContainer.findViewById(16908327);
            if (findViewById != null) {
                findViewById.setOnClickListener(this);
            }
            this.mMiniKeyboard.setOnKeyboardActionListener(new android.inputmethodservice.KeyboardView.OnKeyboardActionListener() { // from class: android.inputmethodservice.KeyboardView.3
                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void onKey(int i2, int[] iArr) {
                    android.inputmethodservice.KeyboardView.this.mKeyboardActionListener.onKey(i2, iArr);
                    android.inputmethodservice.KeyboardView.this.dismissPopupKeyboard();
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void onText(java.lang.CharSequence charSequence) {
                    android.inputmethodservice.KeyboardView.this.mKeyboardActionListener.onText(charSequence);
                    android.inputmethodservice.KeyboardView.this.dismissPopupKeyboard();
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void swipeLeft() {
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void swipeRight() {
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void swipeUp() {
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void swipeDown() {
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void onPress(int i2) {
                    android.inputmethodservice.KeyboardView.this.mKeyboardActionListener.onPress(i2);
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void onRelease(int i2) {
                    android.inputmethodservice.KeyboardView.this.mKeyboardActionListener.onRelease(i2);
                }
            });
            if (key.popupCharacters != null) {
                keyboard = new android.inputmethodservice.Keyboard(getContext(), i, key.popupCharacters, -1, getPaddingLeft() + getPaddingRight());
            } else {
                keyboard = new android.inputmethodservice.Keyboard(getContext(), i);
            }
            this.mMiniKeyboard.setKeyboard(keyboard);
            this.mMiniKeyboard.setPopupParent(this);
            this.mMiniKeyboardContainer.measure(android.view.View.MeasureSpec.makeMeasureSpec(getWidth(), Integer.MIN_VALUE), android.view.View.MeasureSpec.makeMeasureSpec(getHeight(), Integer.MIN_VALUE));
            this.mMiniKeyboardCache.put(key, this.mMiniKeyboardContainer);
        } else {
            this.mMiniKeyboard = (android.inputmethodservice.KeyboardView) this.mMiniKeyboardContainer.findViewById(16908326);
        }
        getLocationInWindow(this.mCoordinates);
        this.mPopupX = key.x + this.mPaddingLeft;
        this.mPopupY = key.y + this.mPaddingTop;
        this.mPopupX = (this.mPopupX + key.width) - this.mMiniKeyboardContainer.getMeasuredWidth();
        this.mPopupY -= this.mMiniKeyboardContainer.getMeasuredHeight();
        int paddingRight = this.mPopupX + this.mMiniKeyboardContainer.getPaddingRight() + this.mCoordinates[0];
        int paddingBottom = this.mPopupY + this.mMiniKeyboardContainer.getPaddingBottom() + this.mCoordinates[1];
        this.mMiniKeyboard.setPopupOffset(paddingRight < 0 ? 0 : paddingRight, paddingBottom);
        this.mMiniKeyboard.setShifted(isShifted());
        this.mPopupKeyboard.setContentView(this.mMiniKeyboardContainer);
        this.mPopupKeyboard.setWidth(this.mMiniKeyboardContainer.getMeasuredWidth());
        this.mPopupKeyboard.setHeight(this.mMiniKeyboardContainer.getMeasuredHeight());
        this.mPopupKeyboard.showAtLocation(this, 0, paddingRight, paddingBottom);
        this.mMiniKeyboardOnScreen = true;
        invalidateAllKeys();
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(android.view.MotionEvent motionEvent) {
        if (!this.mAccessibilityManager.isTouchExplorationEnabled() || motionEvent.getPointerCount() != 1) {
            return true;
        }
        switch (motionEvent.getAction()) {
            case 7:
                motionEvent.setAction(2);
                break;
            case 9:
                motionEvent.setAction(0);
                break;
            case 10:
                motionEvent.setAction(1);
                break;
        }
        return onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        int action = motionEvent.getAction();
        long eventTime = motionEvent.getEventTime();
        boolean z = true;
        if (pointerCount != this.mOldPointerCount) {
            if (pointerCount == 1) {
                android.view.MotionEvent obtain = android.view.MotionEvent.obtain(eventTime, eventTime, 0, motionEvent.getX(), motionEvent.getY(), motionEvent.getMetaState());
                boolean onModifiedTouchEvent = onModifiedTouchEvent(obtain, false);
                obtain.recycle();
                if (action != 1) {
                    z = onModifiedTouchEvent;
                } else {
                    z = onModifiedTouchEvent(motionEvent, true);
                }
            } else {
                android.view.MotionEvent obtain2 = android.view.MotionEvent.obtain(eventTime, eventTime, 1, this.mOldPointerX, this.mOldPointerY, motionEvent.getMetaState());
                z = onModifiedTouchEvent(obtain2, true);
                obtain2.recycle();
            }
        } else if (pointerCount == 1) {
            z = onModifiedTouchEvent(motionEvent, false);
            this.mOldPointerX = motionEvent.getX();
            this.mOldPointerY = motionEvent.getY();
        }
        this.mOldPointerCount = pointerCount;
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean onModifiedTouchEvent(android.view.MotionEvent motionEvent, boolean z) {
        boolean z2;
        int i;
        int i2;
        int x = ((int) motionEvent.getX()) - this.mPaddingLeft;
        int y = ((int) motionEvent.getY()) - this.mPaddingTop;
        if (y >= (-this.mVerticalCorrection)) {
            y += this.mVerticalCorrection;
        }
        int action = motionEvent.getAction();
        long eventTime = motionEvent.getEventTime();
        int keyIndices = getKeyIndices(x, y, null);
        this.mPossiblePoly = z;
        if (action == 0) {
            this.mSwipeTracker.clear();
        }
        this.mSwipeTracker.addMovement(motionEvent);
        if (this.mAbortKey && action != 0 && action != 3) {
            return true;
        }
        if (this.mGestureDetector.onTouchEvent(motionEvent)) {
            showPreview(-1);
            this.mHandler.removeMessages(3);
            this.mHandler.removeMessages(4);
            return true;
        }
        if (this.mMiniKeyboardOnScreen && action != 3) {
            return true;
        }
        switch (action) {
            case 0:
                this.mAbortKey = false;
                this.mStartX = x;
                this.mStartY = y;
                this.mLastCodeX = x;
                this.mLastCodeY = y;
                this.mLastKeyTime = 0L;
                this.mCurrentKeyTime = 0L;
                this.mLastKey = -1;
                this.mCurrentKey = keyIndices;
                this.mDownKey = keyIndices;
                this.mDownTime = motionEvent.getEventTime();
                this.mLastMoveTime = this.mDownTime;
                checkMultiTap(eventTime, keyIndices);
                this.mKeyboardActionListener.onPress(keyIndices != -1 ? this.mKeys[keyIndices].codes[0] : 0);
                if (this.mCurrentKey >= 0 && this.mKeys[this.mCurrentKey].repeatable) {
                    this.mRepeatKeyIndex = this.mCurrentKey;
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3), 400L);
                    repeatKey();
                    if (this.mAbortKey) {
                        this.mRepeatKeyIndex = -1;
                        break;
                    }
                }
                if (this.mCurrentKey != -1) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(4, motionEvent), LONGPRESS_TIMEOUT);
                }
                showPreview(keyIndices);
                break;
            case 1:
                removeMessages();
                if (keyIndices == this.mCurrentKey) {
                    this.mCurrentKeyTime += eventTime - this.mLastMoveTime;
                } else {
                    resetMultiTap();
                    this.mLastKey = this.mCurrentKey;
                    this.mLastKeyTime = (this.mCurrentKeyTime + eventTime) - this.mLastMoveTime;
                    this.mCurrentKey = keyIndices;
                    this.mCurrentKeyTime = 0L;
                }
                if (this.mCurrentKeyTime < this.mLastKeyTime && this.mCurrentKeyTime < 70 && this.mLastKey != -1) {
                    this.mCurrentKey = this.mLastKey;
                    i = this.mLastCodeX;
                    i2 = this.mLastCodeY;
                } else {
                    i = x;
                    i2 = y;
                }
                showPreview(-1);
                java.util.Arrays.fill(this.mKeyIndices, -1);
                if (this.mRepeatKeyIndex == -1 && !this.mMiniKeyboardOnScreen && !this.mAbortKey) {
                    detectAndSendKey(this.mCurrentKey, i, i2, eventTime);
                }
                invalidateKey(keyIndices);
                this.mRepeatKeyIndex = -1;
                x = i;
                y = i2;
                break;
            case 2:
                if (keyIndices != -1) {
                    if (this.mCurrentKey == -1) {
                        this.mCurrentKey = keyIndices;
                        this.mCurrentKeyTime = eventTime - this.mDownTime;
                    } else if (keyIndices == this.mCurrentKey) {
                        this.mCurrentKeyTime += eventTime - this.mLastMoveTime;
                        z2 = true;
                        if (!z2) {
                            this.mHandler.removeMessages(4);
                            if (keyIndices != -1) {
                                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(4, motionEvent), LONGPRESS_TIMEOUT);
                            }
                        }
                        showPreview(this.mCurrentKey);
                        this.mLastMoveTime = eventTime;
                        break;
                    } else if (this.mRepeatKeyIndex == -1) {
                        resetMultiTap();
                        this.mLastKey = this.mCurrentKey;
                        this.mLastCodeX = this.mLastX;
                        this.mLastCodeY = this.mLastY;
                        this.mLastKeyTime = (this.mCurrentKeyTime + eventTime) - this.mLastMoveTime;
                        this.mCurrentKey = keyIndices;
                        this.mCurrentKeyTime = 0L;
                    }
                }
                z2 = false;
                if (!z2) {
                }
                showPreview(this.mCurrentKey);
                this.mLastMoveTime = eventTime;
            case 3:
                removeMessages();
                dismissPopupKeyboard();
                this.mAbortKey = true;
                showPreview(-1);
                invalidateKey(this.mCurrentKey);
                break;
        }
        this.mLastX = x;
        this.mLastY = y;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean repeatKey() {
        android.inputmethodservice.Keyboard.Key key = this.mKeys[this.mRepeatKeyIndex];
        detectAndSendKey(this.mCurrentKey, key.x, key.y, this.mLastTapTime);
        return true;
    }

    protected void swipeRight() {
        this.mKeyboardActionListener.swipeRight();
    }

    protected void swipeLeft() {
        this.mKeyboardActionListener.swipeLeft();
    }

    protected void swipeUp() {
        this.mKeyboardActionListener.swipeUp();
    }

    protected void swipeDown() {
        this.mKeyboardActionListener.swipeDown();
    }

    public void closing() {
        if (this.mPreviewPopup.isShowing()) {
            this.mPreviewPopup.dismiss();
        }
        removeMessages();
        dismissPopupKeyboard();
        this.mBuffer = null;
        this.mCanvas = null;
        this.mMiniKeyboardCache.clear();
    }

    private void removeMessages() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(3);
            this.mHandler.removeMessages(4);
            this.mHandler.removeMessages(1);
        }
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        closing();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissPopupKeyboard() {
        if (this.mPopupKeyboard.isShowing()) {
            this.mPopupKeyboard.dismiss();
            this.mMiniKeyboardOnScreen = false;
            invalidateAllKeys();
        }
    }

    public boolean handleBack() {
        if (this.mPopupKeyboard.isShowing()) {
            dismissPopupKeyboard();
            return true;
        }
        return false;
    }

    private void resetMultiTap() {
        this.mLastSentIndex = -1;
        this.mTapCount = 0;
        this.mLastTapTime = -1L;
        this.mInMultiTap = false;
    }

    private void checkMultiTap(long j, int i) {
        if (i == -1) {
            return;
        }
        android.inputmethodservice.Keyboard.Key key = this.mKeys[i];
        if (key.codes.length <= 1) {
            if (j > this.mLastTapTime + 800 || i != this.mLastSentIndex) {
                resetMultiTap();
                return;
            }
            return;
        }
        this.mInMultiTap = true;
        if (j < this.mLastTapTime + 800 && i == this.mLastSentIndex) {
            this.mTapCount = (this.mTapCount + 1) % key.codes.length;
        } else {
            this.mTapCount = -1;
        }
    }

    private static class SwipeTracker {
        static final int LONGEST_PAST_TIME = 200;
        static final int NUM_PAST = 4;
        final long[] mPastTime;
        final float[] mPastX;
        final float[] mPastY;
        float mXVelocity;
        float mYVelocity;

        private SwipeTracker() {
            this.mPastX = new float[4];
            this.mPastY = new float[4];
            this.mPastTime = new long[4];
        }

        public void clear() {
            this.mPastTime[0] = 0;
        }

        public void addMovement(android.view.MotionEvent motionEvent) {
            long eventTime = motionEvent.getEventTime();
            int historySize = motionEvent.getHistorySize();
            for (int i = 0; i < historySize; i++) {
                addPoint(motionEvent.getHistoricalX(i), motionEvent.getHistoricalY(i), motionEvent.getHistoricalEventTime(i));
            }
            addPoint(motionEvent.getX(), motionEvent.getY(), eventTime);
        }

        private void addPoint(float f, float f2, long j) {
            long[] jArr = this.mPastTime;
            int i = -1;
            int i2 = 0;
            while (i2 < 4 && jArr[i2] != 0) {
                if (jArr[i2] < j - 200) {
                    i = i2;
                }
                i2++;
            }
            if (i2 == 4 && i < 0) {
                i = 0;
            }
            if (i == i2) {
                i--;
            }
            float[] fArr = this.mPastX;
            float[] fArr2 = this.mPastY;
            if (i >= 0) {
                int i3 = i + 1;
                int i4 = (4 - i) - 1;
                java.lang.System.arraycopy(fArr, i3, fArr, 0, i4);
                java.lang.System.arraycopy(fArr2, i3, fArr2, 0, i4);
                java.lang.System.arraycopy(jArr, i3, jArr, 0, i4);
                i2 -= i3;
            }
            fArr[i2] = f;
            fArr2[i2] = f2;
            jArr[i2] = j;
            int i5 = i2 + 1;
            if (i5 < 4) {
                jArr[i5] = 0;
            }
        }

        public void computeCurrentVelocity(int i) {
            computeCurrentVelocity(i, Float.MAX_VALUE);
        }

        public void computeCurrentVelocity(int i, float f) {
            float[] fArr;
            float[] fArr2 = this.mPastX;
            float[] fArr3 = this.mPastY;
            long[] jArr = this.mPastTime;
            int i2 = 0;
            float f2 = fArr2[0];
            float f3 = fArr3[0];
            long j = jArr[0];
            while (i2 < 4 && jArr[i2] != 0) {
                i2++;
            }
            int i3 = 1;
            float f4 = 0.0f;
            float f5 = 0.0f;
            while (i3 < i2) {
                int i4 = (int) (jArr[i3] - j);
                if (i4 == 0) {
                    fArr = fArr2;
                } else {
                    float f6 = i4;
                    float f7 = (fArr2[i3] - f2) / f6;
                    fArr = fArr2;
                    float f8 = i;
                    float f9 = f7 * f8;
                    f4 = f4 == 0.0f ? f9 : (f4 + f9) * 0.5f;
                    float f10 = ((fArr3[i3] - f3) / f6) * f8;
                    f5 = f5 == 0.0f ? f10 : (f5 + f10) * 0.5f;
                }
                i3++;
                fArr2 = fArr;
            }
            this.mXVelocity = f4 < 0.0f ? java.lang.Math.max(f4, -f) : java.lang.Math.min(f4, f);
            this.mYVelocity = f5 < 0.0f ? java.lang.Math.max(f5, -f) : java.lang.Math.min(f5, f);
        }

        public float getXVelocity() {
            return this.mXVelocity;
        }

        public float getYVelocity() {
            return this.mYVelocity;
        }
    }
}
