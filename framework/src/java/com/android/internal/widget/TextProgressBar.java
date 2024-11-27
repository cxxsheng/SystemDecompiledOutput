package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class TextProgressBar extends android.widget.RelativeLayout implements android.widget.Chronometer.OnChronometerTickListener {
    static final int CHRONOMETER_ID = 16908308;
    static final int PROGRESSBAR_ID = 16908301;
    public static final java.lang.String TAG = "TextProgressBar";
    android.widget.Chronometer mChronometer;
    boolean mChronometerFollow;
    int mChronometerGravity;
    int mDuration;
    long mDurationBase;
    android.widget.ProgressBar mProgressBar;

    public TextProgressBar(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChronometer = null;
        this.mProgressBar = null;
        this.mDurationBase = -1L;
        this.mDuration = -1;
        this.mChronometerFollow = false;
        this.mChronometerGravity = 0;
    }

    public TextProgressBar(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChronometer = null;
        this.mProgressBar = null;
        this.mDurationBase = -1L;
        this.mDuration = -1;
        this.mChronometerFollow = false;
        this.mChronometerGravity = 0;
    }

    public TextProgressBar(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChronometer = null;
        this.mProgressBar = null;
        this.mDurationBase = -1L;
        this.mDuration = -1;
        this.mChronometerFollow = false;
        this.mChronometerGravity = 0;
    }

    public TextProgressBar(android.content.Context context) {
        super(context);
        this.mChronometer = null;
        this.mProgressBar = null;
        this.mDurationBase = -1L;
        this.mDuration = -1;
        this.mChronometerFollow = false;
        this.mChronometerGravity = 0;
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        int id = view.getId();
        if (id == 16908308 && (view instanceof android.widget.Chronometer)) {
            this.mChronometer = (android.widget.Chronometer) view;
            this.mChronometer.setOnChronometerTickListener(this);
            this.mChronometerFollow = layoutParams.width == -2;
            this.mChronometerGravity = this.mChronometer.getGravity() & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
            return;
        }
        if (id == 16908301 && (view instanceof android.widget.ProgressBar)) {
            this.mProgressBar = (android.widget.ProgressBar) view;
        }
    }

    @android.view.RemotableViewMethod
    public void setDurationBase(long j) {
        this.mDurationBase = j;
        if (this.mProgressBar == null || this.mChronometer == null) {
            throw new java.lang.RuntimeException("Expecting child ProgressBar with id 'android.R.id.progress' and Chronometer id 'android.R.id.text1'");
        }
        this.mDuration = (int) (j - this.mChronometer.getBase());
        if (this.mDuration <= 0) {
            this.mDuration = 1;
        }
        this.mProgressBar.setMax(this.mDuration);
    }

    @Override // android.widget.Chronometer.OnChronometerTickListener
    public void onChronometerTick(android.widget.Chronometer chronometer) {
        int i;
        if (this.mProgressBar == null) {
            throw new java.lang.RuntimeException("Expecting child ProgressBar with id 'android.R.id.progress'");
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (elapsedRealtime >= this.mDurationBase) {
            this.mChronometer.stop();
        }
        this.mProgressBar.setProgress(this.mDuration - ((int) (this.mDurationBase - elapsedRealtime)));
        if (this.mChronometerFollow) {
            android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) this.mProgressBar.getLayoutParams();
            int width = this.mProgressBar.getWidth() - (layoutParams.leftMargin + layoutParams.rightMargin);
            int progress = ((this.mProgressBar.getProgress() * width) / this.mProgressBar.getMax()) + layoutParams.leftMargin;
            int width2 = this.mChronometer.getWidth();
            if (this.mChronometerGravity == 8388613) {
                i = -width2;
            } else if (this.mChronometerGravity != 1) {
                i = 0;
            } else {
                i = -(width2 / 2);
            }
            int i2 = progress + i;
            int i3 = (width - layoutParams.rightMargin) - width2;
            if (i2 < layoutParams.leftMargin) {
                i2 = layoutParams.leftMargin;
            } else if (i2 > i3) {
                i2 = i3;
            }
            ((android.widget.RelativeLayout.LayoutParams) this.mChronometer.getLayoutParams()).leftMargin = i2;
            this.mChronometer.requestLayout();
        }
    }
}
