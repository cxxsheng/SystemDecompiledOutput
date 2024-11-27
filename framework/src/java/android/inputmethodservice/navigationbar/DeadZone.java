package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
final class DeadZone {
    private static final boolean CHATTY = true;
    public static final boolean DEBUG = false;
    private static final android.util.FloatProperty<android.inputmethodservice.navigationbar.DeadZone> FLASH_PROPERTY = new android.util.FloatProperty<android.inputmethodservice.navigationbar.DeadZone>("DeadZoneFlash") { // from class: android.inputmethodservice.navigationbar.DeadZone.1
        @Override // android.util.FloatProperty
        public void setValue(android.inputmethodservice.navigationbar.DeadZone deadZone, float f) {
            deadZone.setFlash(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.inputmethodservice.navigationbar.DeadZone deadZone) {
            return java.lang.Float.valueOf(deadZone.getFlash());
        }
    };
    public static final int HORIZONTAL = 0;
    public static final java.lang.String TAG = "DeadZone";
    public static final int VERTICAL = 1;
    private int mDecay;
    private int mDisplayRotation;
    private int mHold;
    private long mLastPokeTime;
    private final android.inputmethodservice.navigationbar.NavigationBarView mNavigationBarView;
    private boolean mShouldFlash;
    private int mSizeMax;
    private int mSizeMin;
    private boolean mVertical;
    private float mFlashFrac = 0.0f;
    private final java.lang.Runnable mDebugFlash = new java.lang.Runnable() { // from class: android.inputmethodservice.navigationbar.DeadZone.2
        @Override // java.lang.Runnable
        public void run() {
            android.animation.ObjectAnimator.ofFloat(android.inputmethodservice.navigationbar.DeadZone.this, android.inputmethodservice.navigationbar.DeadZone.FLASH_PROPERTY, 1.0f, 0.0f).setDuration(150L).start();
        }
    };

    DeadZone(android.inputmethodservice.navigationbar.NavigationBarView navigationBarView) {
        this.mNavigationBarView = navigationBarView;
        onConfigurationChanged(0);
    }

    static float lerp(float f, float f2, float f3) {
        return ((f2 - f) * f3) + f;
    }

    private float getSize(long j) {
        if (this.mSizeMax == 0) {
            return 0.0f;
        }
        long j2 = j - this.mLastPokeTime;
        if (j2 > this.mHold + this.mDecay) {
            return this.mSizeMin;
        }
        if (j2 < this.mHold) {
            return this.mSizeMax;
        }
        return (int) lerp(this.mSizeMax, this.mSizeMin, (j2 - this.mHold) / this.mDecay);
    }

    public void setFlashOnTouchCapture(boolean z) {
        this.mShouldFlash = z;
        this.mFlashFrac = 0.0f;
        this.mNavigationBarView.postInvalidate();
    }

    public void onConfigurationChanged(int i) {
        this.mDisplayRotation = i;
        android.content.res.Resources resources = this.mNavigationBarView.getResources();
        this.mHold = 333;
        this.mDecay = 333;
        this.mSizeMin = android.inputmethodservice.navigationbar.NavigationBarUtils.dpToPx(12.0f, resources);
        this.mSizeMax = android.inputmethodservice.navigationbar.NavigationBarUtils.dpToPx(32.0f, resources);
        this.mVertical = resources.getConfiguration().orientation == 2;
        setFlashOnTouchCapture(false);
    }

    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        if (motionEvent.getToolType(0) == 3) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 4) {
            poke(motionEvent);
            return true;
        }
        if (action == 0) {
            int size = (int) getSize(motionEvent.getEventTime());
            if (!this.mVertical) {
                z = motionEvent.getY() < ((float) size);
            } else {
                z = this.mDisplayRotation == 3 ? motionEvent.getX() > ((float) (this.mNavigationBarView.getWidth() - size)) : motionEvent.getX() < ((float) size);
            }
            if (z) {
                android.util.Log.v(TAG, "consuming errant click: (" + motionEvent.getX() + "," + motionEvent.getY() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                if (this.mShouldFlash) {
                    this.mNavigationBarView.post(this.mDebugFlash);
                    this.mNavigationBarView.postInvalidate();
                }
                return true;
            }
        }
        return false;
    }

    private void poke(android.view.MotionEvent motionEvent) {
        this.mLastPokeTime = motionEvent.getEventTime();
        if (this.mShouldFlash) {
            this.mNavigationBarView.postInvalidate();
        }
    }

    public void setFlash(float f) {
        this.mFlashFrac = f;
        this.mNavigationBarView.postInvalidate();
    }

    public float getFlash() {
        return this.mFlashFrac;
    }

    public void onDraw(android.graphics.Canvas canvas) {
        if (!this.mShouldFlash || this.mFlashFrac <= 0.0f) {
            return;
        }
        int size = (int) getSize(android.os.SystemClock.uptimeMillis());
        if (this.mVertical) {
            if (this.mDisplayRotation == 3) {
                canvas.clipRect(canvas.getWidth() - size, 0, canvas.getWidth(), canvas.getHeight());
            } else {
                canvas.clipRect(0, 0, size, canvas.getHeight());
            }
        } else {
            canvas.clipRect(0, 0, canvas.getWidth(), size);
        }
        canvas.drawARGB((int) (this.mFlashFrac * 255.0f), 221, 238, 170);
    }
}
