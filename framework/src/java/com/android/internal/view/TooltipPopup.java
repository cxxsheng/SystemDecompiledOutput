package com.android.internal.view;

/* loaded from: classes5.dex */
public class TooltipPopup {
    private static final java.lang.String TAG = "TooltipPopup";
    private final android.view.View mContentView;
    private final android.content.Context mContext;
    private final android.widget.TextView mMessageView;
    private final android.view.WindowManager.LayoutParams mLayoutParams = new android.view.WindowManager.LayoutParams();
    private final android.graphics.Rect mTmpDisplayFrame = new android.graphics.Rect();
    private final int[] mTmpAnchorPos = new int[2];
    private final int[] mTmpAppPos = new int[2];

    public TooltipPopup(android.content.Context context) {
        this.mContext = context;
        this.mContentView = android.view.LayoutInflater.from(this.mContext).inflate(com.android.internal.R.layout.tooltip, (android.view.ViewGroup) null);
        this.mMessageView = (android.widget.TextView) this.mContentView.findViewById(16908299);
        this.mLayoutParams.setTitle(this.mContext.getString(com.android.internal.R.string.tooltip_popup_title));
        this.mLayoutParams.packageName = this.mContext.getOpPackageName();
        this.mLayoutParams.type = 1005;
        this.mLayoutParams.width = -2;
        this.mLayoutParams.height = -2;
        this.mLayoutParams.format = -3;
        this.mLayoutParams.windowAnimations = com.android.internal.R.style.Animation_Tooltip;
        this.mLayoutParams.flags = 24;
    }

    public void show(android.view.View view, int i, int i2, boolean z, java.lang.CharSequence charSequence) {
        if (isShowing()) {
            hide();
        }
        this.mMessageView.lambda$setTextAsync$0(charSequence);
        computePosition(view, i, i2, z, this.mLayoutParams);
        ((android.view.WindowManager) this.mContext.getSystemService(android.content.Context.WINDOW_SERVICE)).addView(this.mContentView, this.mLayoutParams);
    }

    public void hide() {
        if (!isShowing()) {
            return;
        }
        ((android.view.WindowManager) this.mContext.getSystemService(android.content.Context.WINDOW_SERVICE)).removeView(this.mContentView);
    }

    public android.view.View getContentView() {
        return this.mContentView;
    }

    public boolean isShowing() {
        return this.mContentView.getParent() != null;
    }

    private void computePosition(android.view.View view, int i, int i2, boolean z, android.view.WindowManager.LayoutParams layoutParams) {
        int height;
        int i3;
        layoutParams.token = view.getApplicationWindowToken();
        int dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(com.android.internal.R.dimen.tooltip_precise_anchor_threshold);
        if (view.getWidth() < dimensionPixelOffset) {
            i = view.getWidth() / 2;
        }
        if (view.getHeight() >= dimensionPixelOffset) {
            int dimensionPixelOffset2 = this.mContext.getResources().getDimensionPixelOffset(com.android.internal.R.dimen.tooltip_precise_anchor_extra_offset);
            height = i2 + dimensionPixelOffset2;
            i3 = i2 - dimensionPixelOffset2;
        } else {
            height = view.getHeight();
            i3 = 0;
        }
        layoutParams.gravity = 49;
        int dimensionPixelOffset3 = this.mContext.getResources().getDimensionPixelOffset(z ? com.android.internal.R.dimen.tooltip_y_offset_touch : com.android.internal.R.dimen.tooltip_y_offset_non_touch);
        android.view.View windowView = android.view.WindowManagerGlobal.getInstance().getWindowView(view.getApplicationWindowToken());
        if (windowView == null) {
            android.util.Slog.e(TAG, "Cannot find app view");
            return;
        }
        windowView.getWindowVisibleDisplayFrame(this.mTmpDisplayFrame);
        windowView.getLocationOnScreen(this.mTmpAppPos);
        view.getLocationOnScreen(this.mTmpAnchorPos);
        int[] iArr = this.mTmpAnchorPos;
        iArr[0] = iArr[0] - this.mTmpAppPos[0];
        int[] iArr2 = this.mTmpAnchorPos;
        iArr2[1] = iArr2[1] - this.mTmpAppPos[1];
        layoutParams.x = (this.mTmpAnchorPos[0] + i) - (windowView.getWidth() / 2);
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContentView.measure(makeMeasureSpec, makeMeasureSpec);
        int measuredHeight = this.mContentView.getMeasuredHeight();
        int i4 = ((this.mTmpAnchorPos[1] + i3) - dimensionPixelOffset3) - measuredHeight;
        int i5 = this.mTmpAnchorPos[1] + height + dimensionPixelOffset3;
        if (z) {
            if (i4 >= 0) {
                layoutParams.y = i4;
                return;
            } else {
                layoutParams.y = i5;
                return;
            }
        }
        if (measuredHeight + i5 <= this.mTmpDisplayFrame.height()) {
            layoutParams.y = i5;
        } else {
            layoutParams.y = i4;
        }
    }
}
