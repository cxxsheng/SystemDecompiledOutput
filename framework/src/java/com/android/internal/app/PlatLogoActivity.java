package com.android.internal.app;

/* loaded from: classes4.dex */
public class PlatLogoActivity extends android.app.Activity {
    private static final boolean FINISH_AFTER_NEXT_STAGE_LAUNCH = false;
    private static final long LAUNCH_TIME = 5000;
    private static final float MAX_WARP = 10.0f;
    private static final float MIN_WARP = 1.0f;
    private static final java.lang.String TAG = "PlatLogoActivity";
    static final java.lang.String TOUCH_STATS = "touch.stats";
    private static final java.lang.String U_EGG_UNLOCK_SETTING = "egg_mode_u";
    private android.animation.TimeAnimator mAnim;
    private float mDp;
    private android.widget.FrameLayout mLayout;
    private android.widget.ImageView mLogo;
    private java.util.Random mRandom;
    private com.android.internal.app.PlatLogoActivity.RumblePack mRumble;
    private com.android.internal.app.PlatLogoActivity.Starfield mStarfield;
    private android.animation.ObjectAnimator mWarpAnim;
    private boolean mAnimationsEnabled = true;
    private final android.view.View.OnTouchListener mTouchListener = new android.view.View.OnTouchListener() { // from class: com.android.internal.app.PlatLogoActivity.1
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                    com.android.internal.app.PlatLogoActivity.this.measureTouchPressure(motionEvent);
                    com.android.internal.app.PlatLogoActivity.this.startWarp();
                    break;
                case 1:
                case 3:
                    com.android.internal.app.PlatLogoActivity.this.stopWarp();
                    break;
            }
            return true;
        }
    };
    private final java.lang.Runnable mLaunchNextStage = new java.lang.Runnable() { // from class: com.android.internal.app.PlatLogoActivity$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.internal.app.PlatLogoActivity.this.lambda$new$0();
        }
    };
    private final android.animation.TimeAnimator.TimeListener mTimeListener = new android.animation.TimeAnimator.TimeListener() { // from class: com.android.internal.app.PlatLogoActivity.2
        @Override // android.animation.TimeAnimator.TimeListener
        public void onTimeUpdate(android.animation.TimeAnimator timeAnimator, long j, long j2) {
            com.android.internal.app.PlatLogoActivity.this.mStarfield.update(j2);
            float warp = (com.android.internal.app.PlatLogoActivity.this.mStarfield.getWarp() - 1.0f) / 9.0f;
            if (com.android.internal.app.PlatLogoActivity.this.mAnimationsEnabled) {
                com.android.internal.app.PlatLogoActivity.this.mLogo.setTranslationX(com.android.internal.app.PlatLogoActivity.this.mRandom.nextFloat() * warp * 5.0f * com.android.internal.app.PlatLogoActivity.this.mDp);
                com.android.internal.app.PlatLogoActivity.this.mLogo.setTranslationY(com.android.internal.app.PlatLogoActivity.this.mRandom.nextFloat() * warp * 5.0f * com.android.internal.app.PlatLogoActivity.this.mDp);
            }
            if (warp > 0.0f) {
                com.android.internal.app.PlatLogoActivity.this.mRumble.rumble(warp);
            }
            com.android.internal.app.PlatLogoActivity.this.mLayout.postInvalidate();
        }
    };
    double mPressureMin = 0.0d;
    double mPressureMax = -1.0d;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        stopWarp();
        launchNextStage(false);
    }

    private class RumblePack implements android.os.Handler.Callback {
        private static final int INTERVAL = 50;
        private static final int MSG = 6464;
        private boolean mSpinPrimitiveSupported;
        private final android.os.Handler mVibeHandler;
        private final android.os.VibratorManager mVibeMan;
        private long mLastVibe = 0;
        private final android.os.HandlerThread mVibeThread = new android.os.HandlerThread("VibratorThread");

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            float f = message.arg1 / 100.0f;
            if (!this.mSpinPrimitiveSupported) {
                if (com.android.internal.app.PlatLogoActivity.this.mRandom.nextFloat() < f) {
                    com.android.internal.app.PlatLogoActivity.this.mLogo.performHapticFeedback(4);
                    return false;
                }
                return false;
            }
            if (message.getWhen() > this.mLastVibe + 50) {
                this.mLastVibe = message.getWhen();
                this.mVibeMan.vibrate(android.os.CombinedVibration.createParallel(android.os.VibrationEffect.startComposition().addPrimitive(3, (float) java.lang.Math.pow(f, 3.0d)).compose()));
                return false;
            }
            return false;
        }

        RumblePack() {
            this.mVibeMan = (android.os.VibratorManager) com.android.internal.app.PlatLogoActivity.this.getSystemService(android.os.VibratorManager.class);
            this.mSpinPrimitiveSupported = this.mVibeMan.getDefaultVibrator().areAllPrimitivesSupported(3);
            this.mVibeThread.start();
            this.mVibeHandler = android.os.Handler.createAsync(this.mVibeThread.getLooper(), this);
        }

        public void destroy() {
            this.mVibeThread.quit();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void rumble(float f) {
            if (this.mVibeThread.isAlive()) {
                android.os.Message obtain = android.os.Message.obtain();
                obtain.what = MSG;
                obtain.arg1 = (int) (f * 100.0f);
                this.mVibeHandler.removeMessages(MSG);
                this.mVibeHandler.sendMessage(obtain);
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mRumble.destroy();
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setDecorFitsSystemWindows(false);
        getWindow().setNavigationBarColor(0);
        getWindow().setStatusBarColor(0);
        getWindow().getDecorView().getWindowInsetsController().hide(android.view.WindowInsets.Type.systemBars());
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        try {
            this.mAnimationsEnabled = android.provider.Settings.Global.getFloat(getContentResolver(), "animator_duration_scale") > 0.0f;
        } catch (android.provider.Settings.SettingNotFoundException e) {
            this.mAnimationsEnabled = true;
        }
        this.mRumble = new com.android.internal.app.PlatLogoActivity.RumblePack();
        this.mLayout = new android.widget.FrameLayout(this);
        this.mRandom = new java.util.Random();
        this.mDp = getResources().getDisplayMetrics().density;
        this.mStarfield = new com.android.internal.app.PlatLogoActivity.Starfield(this.mRandom, this.mDp * 2.0f);
        this.mStarfield.setVelocity((this.mRandom.nextFloat() - 0.5f) * 200.0f, (this.mRandom.nextFloat() - 0.5f) * 200.0f);
        this.mLayout.setBackground(this.mStarfield);
        float f = getResources().getDisplayMetrics().density;
        int min = (int) (java.lang.Math.min(r5.widthPixels, r5.heightPixels) * 0.75d);
        android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(min, min);
        layoutParams.gravity = 17;
        this.mLogo = new android.widget.ImageView(this);
        this.mLogo.setImageResource(com.android.internal.R.drawable.platlogo);
        this.mLogo.setOnTouchListener(this.mTouchListener);
        this.mLogo.requestFocus();
        this.mLayout.addView(this.mLogo, layoutParams);
        android.util.Log.v(TAG, "Hello");
        setContentView(this.mLayout);
    }

    private void startAnimating() {
        this.mAnim = new android.animation.TimeAnimator();
        this.mAnim.setTimeListener(this.mTimeListener);
        this.mAnim.start();
    }

    private void stopAnimating() {
        this.mAnim.cancel();
        this.mAnim = null;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (i == 62) {
            if (keyEvent.getRepeatCount() == 0) {
                startWarp();
                return true;
            }
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (i == 62) {
            stopWarp();
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startWarp() {
        stopWarp();
        this.mWarpAnim = android.animation.ObjectAnimator.ofFloat(this.mStarfield, "warp", 1.0f, MAX_WARP).setDuration(5000L);
        this.mWarpAnim.start();
        this.mLogo.postDelayed(this.mLaunchNextStage, 6000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopWarp() {
        if (this.mWarpAnim != null) {
            this.mWarpAnim.cancel();
            this.mWarpAnim.removeAllListeners();
            this.mWarpAnim = null;
        }
        this.mStarfield.setWarp(1.0f);
        this.mLogo.removeCallbacks(this.mLaunchNextStage);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        startAnimating();
    }

    @Override // android.app.Activity
    public void onPause() {
        stopWarp();
        stopAnimating();
        super.onPause();
    }

    private boolean shouldWriteSettings() {
        return getPackageName().equals("android");
    }

    private void launchNextStage(boolean z) {
        android.content.ContentResolver contentResolver = getContentResolver();
        try {
            if (shouldWriteSettings()) {
                android.util.Log.v(TAG, "Saving egg locked=" + z);
                syncTouchPressure();
                android.provider.Settings.System.putLong(contentResolver, U_EGG_UNLOCK_SETTING, z ? 0L : java.lang.System.currentTimeMillis());
            }
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Can't write settings", e);
        }
        try {
            android.content.Intent addCategory = new android.content.Intent(android.content.Intent.ACTION_MAIN).setFlags(268468224).addCategory("com.android.internal.category.PLATLOGO");
            android.util.Log.v(TAG, "launching: " + addCategory);
            startActivity(addCategory);
        } catch (android.content.ActivityNotFoundException e2) {
            android.util.Log.e("com.android.internal.app.PlatLogoActivity", "No more eggs.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void measureTouchPressure(android.view.MotionEvent motionEvent) {
        float pressure = motionEvent.getPressure();
        switch (motionEvent.getActionMasked()) {
            case 0:
                if (this.mPressureMax < 0.0d) {
                    double d = pressure;
                    this.mPressureMax = d;
                    this.mPressureMin = d;
                    break;
                }
                break;
            case 2:
                double d2 = pressure;
                if (d2 < this.mPressureMin) {
                    this.mPressureMin = d2;
                }
                if (d2 > this.mPressureMax) {
                    this.mPressureMax = d2;
                    break;
                }
                break;
        }
    }

    private void syncTouchPressure() {
        try {
            java.lang.String string = android.provider.Settings.System.getString(getContentResolver(), TOUCH_STATS);
            if (string == null) {
                string = "{}";
            }
            org.json.JSONObject jSONObject = new org.json.JSONObject(string);
            if (jSONObject.has("min")) {
                this.mPressureMin = java.lang.Math.min(this.mPressureMin, jSONObject.getDouble("min"));
            }
            if (jSONObject.has("max")) {
                this.mPressureMax = java.lang.Math.max(this.mPressureMax, jSONObject.getDouble("max"));
            }
            if (this.mPressureMax >= 0.0d) {
                jSONObject.put("min", this.mPressureMin);
                jSONObject.put("max", this.mPressureMax);
                if (shouldWriteSettings()) {
                    android.provider.Settings.System.putString(getContentResolver(), TOUCH_STATS, jSONObject.toString());
                }
            }
        } catch (java.lang.Exception e) {
            android.util.Log.e("com.android.internal.app.PlatLogoActivity", "Can't write touch settings", e);
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        syncTouchPressure();
    }

    @Override // android.app.Activity
    public void onStop() {
        syncTouchPressure();
        super.onStop();
    }

    private static class Starfield extends android.graphics.drawable.Drawable {
        private static final int NUM_PLANES = 2;
        private static final int NUM_STARS = 34;
        private float mBuffer;
        private final java.util.Random mRng;
        private final float mSize;
        private float mVx;
        private float mVy;
        private final float[] mStars = new float[136];
        private long mDt = 0;
        private final android.graphics.Rect mSpace = new android.graphics.Rect();
        private float mWarp = 1.0f;
        private final android.graphics.Paint mStarPaint = new android.graphics.Paint();

        public void setWarp(float f) {
            this.mWarp = f;
        }

        public float getWarp() {
            return this.mWarp;
        }

        Starfield(java.util.Random random, float f) {
            this.mRng = random;
            this.mSize = f;
            this.mStarPaint.setStyle(android.graphics.Paint.Style.STROKE);
            this.mStarPaint.setColor(-1);
        }

        @Override // android.graphics.drawable.Drawable
        public void onBoundsChange(android.graphics.Rect rect) {
            this.mSpace.set(rect);
            this.mBuffer = this.mSize * 2.0f * 2.0f * com.android.internal.app.PlatLogoActivity.MAX_WARP;
            this.mSpace.inset(-((int) this.mBuffer), -((int) this.mBuffer));
            float width = this.mSpace.width();
            float height = this.mSpace.height();
            for (int i = 0; i < 34; i++) {
                int i2 = i * 4;
                this.mStars[i2] = this.mRng.nextFloat() * width;
                int i3 = i2 + 1;
                this.mStars[i3] = this.mRng.nextFloat() * height;
                this.mStars[i2 + 2] = this.mStars[i2];
                this.mStars[i2 + 3] = this.mStars[i3];
            }
        }

        public void setVelocity(float f, float f2) {
            this.mVx = f;
            this.mVy = f2;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(android.graphics.Canvas canvas) {
            float f = this.mDt / 1000.0f;
            float f2 = this.mVx * f * this.mWarp;
            float f3 = this.mVy * f * this.mWarp;
            int i = 1;
            boolean z = this.mWarp > 1.0f;
            canvas.drawColor(-16777216);
            if (this.mDt > 0 && this.mDt < 1000) {
                canvas.translate((-this.mBuffer) + (this.mRng.nextFloat() * (this.mWarp - 1.0f)), (-this.mBuffer) + (this.mRng.nextFloat() * (this.mWarp - 1.0f)));
                float width = this.mSpace.width();
                float height = this.mSpace.height();
                int i2 = 0;
                while (i2 < 34) {
                    int i3 = i2 * 4;
                    int i4 = i3 + 2;
                    float f4 = ((int) ((i2 / 34.0f) * 2.0f)) + i;
                    this.mStars[i4] = ((this.mStars[i4] + (f2 * f4)) + width) % width;
                    int i5 = i3 + 3;
                    this.mStars[i5] = ((this.mStars[i5] + (f3 * f4)) + height) % height;
                    float f5 = -100.0f;
                    this.mStars[i3 + 0] = z ? this.mStars[i4] - (((this.mWarp * f2) * 2.0f) * f4) : -100.0f;
                    float[] fArr = this.mStars;
                    int i6 = i3 + 1;
                    if (z) {
                        f5 = this.mStars[i5] - (((this.mWarp * f3) * 2.0f) * f4);
                    }
                    fArr[i6] = f5;
                    i2++;
                    i = 1;
                }
            }
            int length = ((this.mStars.length / 2) / 4) * 4;
            int i7 = 0;
            while (i7 < 2) {
                int i8 = i7 + 1;
                this.mStarPaint.setStrokeWidth(this.mSize * i8);
                if (z) {
                    canvas.drawLines(this.mStars, i7 * length, length, this.mStarPaint);
                }
                canvas.drawPoints(this.mStars, i7 * length, length, this.mStarPaint);
                i7 = i8;
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -1;
        }

        public void update(long j) {
            this.mDt = j;
        }
    }
}
