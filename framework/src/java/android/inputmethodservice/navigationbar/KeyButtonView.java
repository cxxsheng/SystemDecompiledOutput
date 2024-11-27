package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
public class KeyButtonView extends android.widget.ImageView implements android.inputmethodservice.navigationbar.ButtonInterface {
    public static final float QUICKSTEP_TOUCH_SLOP_RATIO = 3.0f;
    private static final java.lang.String TAG = android.inputmethodservice.navigationbar.KeyButtonView.class.getSimpleName();
    private android.media.AudioManager mAudioManager;
    private int mCode;
    private float mDarkIntensity;
    private long mDownTime;
    private boolean mGestureAborted;
    private boolean mHasOvalBg;
    private android.view.View.OnClickListener mOnClickListener;
    private final android.graphics.Paint mOvalBgPaint;
    private final boolean mPlaySounds;
    private final android.inputmethodservice.navigationbar.KeyButtonRipple mRipple;
    private int mTouchDownX;
    private int mTouchDownY;
    private boolean mTracking;

    public KeyButtonView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOvalBgPaint = new android.graphics.Paint(3);
        this.mHasOvalBg = false;
        switch (getId()) {
            case com.android.internal.R.id.input_method_nav_back /* 16909147 */:
                this.mCode = 4;
                break;
            default:
                this.mCode = 0;
                break;
        }
        this.mPlaySounds = true;
        setClickable(true);
        this.mAudioManager = (android.media.AudioManager) context.getSystemService(android.media.AudioManager.class);
        this.mRipple = new android.inputmethodservice.navigationbar.KeyButtonRipple(context, this, com.android.internal.R.dimen.input_method_nav_key_button_ripple_max_width);
        setBackground(this.mRipple);
        setWillNotDraw(false);
        forceHasOverlappingRendering(false);
    }

    @Override // android.view.View
    public boolean isClickable() {
        return this.mCode != 0 || super.isClickable();
    }

    public void setCode(int i) {
        this.mCode = i;
    }

    @Override // android.view.View
    public void setOnClickListener(android.view.View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.mCode != 0) {
            accessibilityNodeInfo.addAction(new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16, (java.lang.CharSequence) null));
            if (isLongClickable()) {
                accessibilityNodeInfo.addAction(new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(32, (java.lang.CharSequence) null));
            }
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            jumpDrawablesToCurrentState();
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        if (i == 16 && this.mCode != 0) {
            sendEvent(0, 0, android.os.SystemClock.uptimeMillis());
            sendEvent(1, this.mTracking ? 512 : 0);
            this.mTracking = false;
            sendAccessibilityEvent(1);
            playSoundEffect(0);
            return true;
        }
        if (i == 32 && this.mCode != 0) {
            sendEvent(0, 128);
            sendEvent(1, this.mTracking ? 512 : 0);
            this.mTracking = false;
            sendAccessibilityEvent(2);
            return true;
        }
        return super.performAccessibilityActionInternal(i, bundle);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00c4, code lost:
    
        return true;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mGestureAborted = false;
        }
        if (this.mGestureAborted) {
            setPressed(false);
            return false;
        }
        switch (action) {
            case 0:
                this.mDownTime = android.os.SystemClock.uptimeMillis();
                setPressed(true);
                this.mTouchDownX = (int) motionEvent.getRawX();
                this.mTouchDownY = (int) motionEvent.getRawY();
                if (this.mCode != 0) {
                    sendEvent(0, 0, this.mDownTime);
                } else {
                    performHapticFeedback(1);
                }
                playSoundEffect(0);
                break;
            case 1:
                boolean isPressed = isPressed();
                setPressed(false);
                if (android.os.SystemClock.uptimeMillis() - this.mDownTime > 150) {
                    performHapticFeedback(8);
                }
                if (this.mCode != 0) {
                    if (isPressed) {
                        sendEvent(1, this.mTracking ? 512 : 0);
                        this.mTracking = false;
                        sendAccessibilityEvent(1);
                        break;
                    } else {
                        sendEvent(1, 32);
                        break;
                    }
                } else if (isPressed && this.mOnClickListener != null) {
                    this.mOnClickListener.onClick(this);
                    sendAccessibilityEvent(1);
                    break;
                }
                break;
            case 2:
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                float quickStepTouchSlopPx = getQuickStepTouchSlopPx(getContext());
                if (java.lang.Math.abs(rawX - this.mTouchDownX) > quickStepTouchSlopPx || java.lang.Math.abs(rawY - this.mTouchDownY) > quickStepTouchSlopPx) {
                    setPressed(false);
                    break;
                }
                break;
            case 3:
                setPressed(false);
                if (this.mCode != 0) {
                    sendEvent(1, 32);
                    break;
                }
                break;
        }
    }

    @Override // android.widget.ImageView, android.inputmethodservice.navigationbar.ButtonInterface
    /* renamed from: setImageDrawable */
    public void lambda$setImageURIAsync$2(android.graphics.drawable.Drawable drawable) {
        super.lambda$setImageURIAsync$2(drawable);
        if (drawable == null) {
            return;
        }
        android.inputmethodservice.navigationbar.KeyButtonDrawable keyButtonDrawable = (android.inputmethodservice.navigationbar.KeyButtonDrawable) drawable;
        keyButtonDrawable.setDarkIntensity(this.mDarkIntensity);
        this.mHasOvalBg = keyButtonDrawable.hasOvalBg();
        if (this.mHasOvalBg) {
            this.mOvalBgPaint.setColor(keyButtonDrawable.getDrawableBackgroundColor());
        }
        this.mRipple.setType(keyButtonDrawable.hasOvalBg() ? android.inputmethodservice.navigationbar.KeyButtonRipple.Type.OVAL : android.inputmethodservice.navigationbar.KeyButtonRipple.Type.ROUNDED_RECT);
    }

    @Override // android.view.View
    public void playSoundEffect(int i) {
        if (this.mPlaySounds) {
            this.mAudioManager.playSoundEffect(i);
        }
    }

    private void sendEvent(int i, int i2) {
        sendEvent(i, i2, android.os.SystemClock.uptimeMillis());
    }

    private void sendEvent(int i, int i2, long j) {
        int i3;
        android.view.inputmethod.InputConnection currentInputConnection;
        if (this.mContext instanceof android.inputmethodservice.InputMethodService) {
            boolean z = false;
            android.view.KeyEvent keyEvent = new android.view.KeyEvent(this.mDownTime, j, i, this.mCode, (i2 & 128) != 0 ? 1 : 0, 0, -1, 0, i2 | 2 | 64, 257);
            if (getDisplay() == null) {
                i3 = -1;
            } else {
                i3 = getDisplay().getDisplayId();
            }
            if (i3 != -1) {
                keyEvent.setDisplayId(i3);
            }
            android.inputmethodservice.InputMethodService inputMethodService = (android.inputmethodservice.InputMethodService) this.mContext;
            switch (i) {
                case 0:
                    boolean onKeyDown = inputMethodService.onKeyDown(keyEvent.getKeyCode(), keyEvent);
                    this.mTracking = onKeyDown && keyEvent.getRepeatCount() == 0 && (keyEvent.getFlags() & 1073741824) != 0;
                    z = onKeyDown;
                    break;
                case 1:
                    z = inputMethodService.onKeyUp(keyEvent.getKeyCode(), keyEvent);
                    break;
            }
            if (!z && (currentInputConnection = inputMethodService.getCurrentInputConnection()) != null) {
                currentInputConnection.sendKeyEvent(keyEvent);
            }
        }
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDarkIntensity(float f) {
        this.mDarkIntensity = f;
        android.graphics.drawable.Drawable drawable = getDrawable();
        if (drawable != null) {
            ((android.inputmethodservice.navigationbar.KeyButtonDrawable) drawable).setDarkIntensity(f);
            invalidate();
        }
        this.mRipple.setDarkIntensity(f);
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDelayTouchFeedback(boolean z) {
        this.mRipple.setDelayTouchFeedback(z);
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        if (this.mHasOvalBg) {
            float min = java.lang.Math.min(getWidth(), getHeight());
            canvas.drawOval(0.0f, 0.0f, min, min, this.mOvalBgPaint);
        }
        super.draw(canvas);
    }

    private static float getQuickStepTouchSlopPx(android.content.Context context) {
        return android.view.ViewConfiguration.get(context).getScaledTouchSlop() * 3.0f;
    }
}
