package android.view;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class NotificationHeaderView extends android.widget.RelativeLayout {
    private boolean mAcceptAllTouches;
    private android.view.View mAltExpandTarget;
    private android.graphics.drawable.Drawable mBackground;
    private boolean mEntireHeaderClickable;
    private com.android.internal.widget.NotificationExpandButton mExpandButton;
    private android.view.View.OnClickListener mExpandClickListener;
    private boolean mExpandOnlyOnButton;
    private com.android.internal.widget.CachingIconView mIcon;
    android.view.ViewOutlineProvider mProvider;
    private android.view.NotificationTopLineView mTopLineView;
    private android.view.NotificationHeaderView.HeaderTouchListener mTouchListener;
    private final int mTouchableHeight;

    public NotificationHeaderView(android.content.Context context) {
        this(context, null);
    }

    public NotificationHeaderView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationHeaderView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationHeaderView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTouchListener = new android.view.NotificationHeaderView.HeaderTouchListener();
        this.mProvider = new android.view.ViewOutlineProvider() { // from class: android.view.NotificationHeaderView.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(android.view.View view, android.graphics.Outline outline) {
                if (android.view.NotificationHeaderView.this.mBackground != null) {
                    outline.setRect(0, 0, android.view.NotificationHeaderView.this.getWidth(), android.view.NotificationHeaderView.this.getHeight());
                    outline.setAlpha(1.0f);
                }
            }
        };
        android.content.res.Resources resources = getResources();
        this.mTouchableHeight = resources.getDimensionPixelSize(com.android.internal.R.dimen.notification_header_touchable_height);
        this.mEntireHeaderClickable = resources.getBoolean(com.android.internal.R.bool.config_notificationHeaderClickableForExpand);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mIcon = (com.android.internal.widget.CachingIconView) findViewById(16908294);
        this.mTopLineView = (android.view.NotificationTopLineView) findViewById(com.android.internal.R.id.notification_top_line);
        this.mExpandButton = (com.android.internal.widget.NotificationExpandButton) findViewById(com.android.internal.R.id.expand_button);
        this.mAltExpandTarget = findViewById(com.android.internal.R.id.alternate_expand_target);
        setClipToPadding(false);
    }

    public void setHeaderBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            setWillNotDraw(false);
            this.mBackground = drawable;
            this.mBackground.setCallback(this);
            setOutlineProvider(this.mProvider);
        } else {
            setWillNotDraw(true);
            this.mBackground = null;
            setOutlineProvider(null);
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        if (this.mBackground != null) {
            this.mBackground.setBounds(0, 0, getWidth(), getHeight());
            this.mBackground.draw(canvas);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mBackground;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        if (this.mBackground != null && this.mBackground.isStateful()) {
            this.mBackground.setState(getDrawableState());
        }
    }

    private void updateTouchListener() {
        if (this.mExpandClickListener == null) {
            setOnTouchListener(null);
        } else {
            setOnTouchListener(this.mTouchListener);
            this.mTouchListener.bindTouchRects();
        }
    }

    @Override // android.view.View
    public void setOnClickListener(android.view.View.OnClickListener onClickListener) {
        this.mExpandClickListener = onClickListener;
        this.mExpandButton.setOnClickListener(this.mExpandClickListener);
        this.mAltExpandTarget.setOnClickListener(this.mExpandClickListener);
        updateTouchListener();
    }

    public void setTopLineExtraMarginEnd(int i) {
        this.mTopLineView.setHeaderTextMarginEnd(i);
    }

    @android.view.RemotableViewMethod
    public void setTopLineExtraMarginEndDp(float f) {
        setTopLineExtraMarginEnd((int) (f * getResources().getDisplayMetrics().density));
    }

    @android.view.RemotableViewMethod
    public void styleTextAsTitle(boolean z) {
        int i;
        if (z) {
            i = com.android.internal.R.style.TextAppearance_DeviceDefault_Notification_Title;
        } else {
            i = com.android.internal.R.style.TextAppearance_DeviceDefault_Notification_Info;
        }
        android.view.View findViewById = findViewById(com.android.internal.R.id.header_text);
        if (findViewById instanceof android.widget.TextView) {
            ((android.widget.TextView) findViewById).setTextAppearance(i);
        }
        android.view.View findViewById2 = findViewById(com.android.internal.R.id.app_name_text);
        if (findViewById2 instanceof android.widget.TextView) {
            ((android.widget.TextView) findViewById2).setTextAppearance(i);
        }
    }

    public class HeaderTouchListener implements android.view.View.OnTouchListener {
        private android.graphics.Rect mAltExpandTargetRect;
        private float mDownX;
        private float mDownY;
        private android.graphics.Rect mExpandButtonRect;
        private final java.util.ArrayList<android.graphics.Rect> mTouchRects = new java.util.ArrayList<>();
        private int mTouchSlop;
        private boolean mTrackGesture;

        public HeaderTouchListener() {
        }

        public void bindTouchRects() {
            this.mTouchRects.clear();
            addRectAroundView(android.view.NotificationHeaderView.this.mIcon);
            this.mExpandButtonRect = addRectAroundView(android.view.NotificationHeaderView.this.mExpandButton);
            this.mAltExpandTargetRect = addRectAroundView(android.view.NotificationHeaderView.this.mAltExpandTarget);
            addWidthRect();
            this.mTouchSlop = android.view.ViewConfiguration.get(android.view.NotificationHeaderView.this.getContext()).getScaledTouchSlop();
        }

        private void addWidthRect() {
            android.graphics.Rect rect = new android.graphics.Rect();
            rect.top = 0;
            rect.bottom = android.view.NotificationHeaderView.this.mTouchableHeight;
            rect.left = 0;
            rect.right = android.view.NotificationHeaderView.this.getWidth();
            this.mTouchRects.add(rect);
        }

        private android.graphics.Rect addRectAroundView(android.view.View view) {
            android.graphics.Rect rectAroundView = getRectAroundView(view);
            this.mTouchRects.add(rectAroundView);
            return rectAroundView;
        }

        private android.graphics.Rect getRectAroundView(android.view.View view) {
            float f = android.view.NotificationHeaderView.this.getResources().getDisplayMetrics().density * 48.0f;
            float max = java.lang.Math.max(f, view.getWidth());
            float max2 = java.lang.Math.max(f, view.getHeight());
            android.graphics.Rect rect = new android.graphics.Rect();
            if (view.getVisibility() == 8) {
                view = android.view.NotificationHeaderView.this.getFirstChildNotGone();
                rect.left = (int) (view.getLeft() - (max / 2.0f));
            } else {
                rect.left = (int) (((view.getLeft() + view.getRight()) / 2.0f) - (max / 2.0f));
            }
            rect.top = (int) (((view.getTop() + view.getBottom()) / 2.0f) - (max2 / 2.0f));
            rect.bottom = (int) (rect.top + max2);
            rect.right = (int) (rect.left + max);
            return rect;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            switch (motionEvent.getActionMasked() & 255) {
                case 0:
                    this.mTrackGesture = false;
                    if (isInside(x, y)) {
                        this.mDownX = x;
                        this.mDownY = y;
                        this.mTrackGesture = true;
                        return true;
                    }
                    break;
                case 1:
                    if (this.mTrackGesture) {
                        float x2 = android.view.NotificationHeaderView.this.mTopLineView.getX();
                        float y2 = android.view.NotificationHeaderView.this.mTopLineView.getY();
                        if (!android.view.NotificationHeaderView.this.mTopLineView.onTouchUp(x - x2, y - y2, this.mDownX - x2, this.mDownY - y2)) {
                            android.view.NotificationHeaderView.this.mExpandButton.performClick();
                            break;
                        }
                    }
                    break;
                case 2:
                    if (this.mTrackGesture && (java.lang.Math.abs(this.mDownX - x) > this.mTouchSlop || java.lang.Math.abs(this.mDownY - y) > this.mTouchSlop)) {
                        this.mTrackGesture = false;
                        break;
                    }
                    break;
            }
            return this.mTrackGesture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isInside(float f, float f2) {
            if (android.view.NotificationHeaderView.this.mAcceptAllTouches) {
                return true;
            }
            if (android.view.NotificationHeaderView.this.mExpandOnlyOnButton) {
                int i = (int) f;
                int i2 = (int) f2;
                return this.mExpandButtonRect.contains(i, i2) || this.mAltExpandTargetRect.contains(i, i2);
            }
            for (int i3 = 0; i3 < this.mTouchRects.size(); i3++) {
                if (this.mTouchRects.get(i3).contains((int) f, (int) f2)) {
                    return true;
                }
            }
            return android.view.NotificationHeaderView.this.mTopLineView.isInTouchRect(f - android.view.NotificationHeaderView.this.mTopLineView.getX(), f2 - android.view.NotificationHeaderView.this.mTopLineView.getY());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.View getFirstChildNotGone() {
        for (int i = 0; i < getChildCount(); i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                return childAt;
            }
        }
        return this;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isInTouchRect(float f, float f2) {
        if (this.mExpandClickListener == null) {
            return false;
        }
        return this.mTouchListener.isInside(f, f2);
    }

    @android.view.RemotableViewMethod
    public void setAcceptAllTouches(boolean z) {
        this.mAcceptAllTouches = this.mEntireHeaderClickable || z;
    }

    @android.view.RemotableViewMethod
    public void setExpandOnlyOnButton(boolean z) {
        this.mExpandOnlyOnButton = z;
    }
}
