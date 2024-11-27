package com.android.internal.widget.remotecompose.player.platform;

/* loaded from: classes5.dex */
public class RemoteComposeCanvas extends android.widget.FrameLayout implements android.view.View.OnAttachStateChangeListener {
    static final boolean USE_VIEW_AREA_CLICK = true;
    private static final float[] sScaleOutput = new float[2];
    com.android.internal.widget.remotecompose.player.platform.AndroidRemoteContext mARContext;
    android.graphics.Point mActionDownPoint;
    boolean mDebug;
    com.android.internal.widget.remotecompose.player.RemoteComposeDocument mDocument;
    boolean mInActionDown;
    int mTheme;

    public interface ClickCallbacks {
        void click(int i, java.lang.String str);
    }

    public RemoteComposeCanvas(android.content.Context context) {
        super(context);
        this.mDocument = null;
        this.mTheme = -3;
        this.mInActionDown = false;
        this.mDebug = false;
        this.mActionDownPoint = new android.graphics.Point(0, 0);
        this.mARContext = new com.android.internal.widget.remotecompose.player.platform.AndroidRemoteContext();
        addOnAttachStateChangeListener(this);
    }

    public RemoteComposeCanvas(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDocument = null;
        this.mTheme = -3;
        this.mInActionDown = false;
        this.mDebug = false;
        this.mActionDownPoint = new android.graphics.Point(0, 0);
        this.mARContext = new com.android.internal.widget.remotecompose.player.platform.AndroidRemoteContext();
        addOnAttachStateChangeListener(this);
    }

    public RemoteComposeCanvas(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDocument = null;
        this.mTheme = -3;
        this.mInActionDown = false;
        this.mDebug = false;
        this.mActionDownPoint = new android.graphics.Point(0, 0);
        this.mARContext = new com.android.internal.widget.remotecompose.player.platform.AndroidRemoteContext();
        setBackgroundColor(-1);
        addOnAttachStateChangeListener(this);
    }

    public void setDebug(boolean z) {
        if (this.mDebug != z) {
            this.mDebug = z;
            for (int i = 0; i < getChildCount(); i++) {
                android.view.View childAt = getChildAt(i);
                if (childAt instanceof com.android.internal.widget.remotecompose.player.platform.ClickAreaView) {
                    ((com.android.internal.widget.remotecompose.player.platform.ClickAreaView) childAt).setDebug(this.mDebug);
                }
            }
            invalidate();
        }
    }

    public void setDocument(com.android.internal.widget.remotecompose.player.RemoteComposeDocument remoteComposeDocument) {
        this.mDocument = remoteComposeDocument;
        this.mDocument.initializeContext(this.mARContext);
        setContentDescription(this.mDocument.getDocument().getContentDescription());
        requestLayout();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(android.view.View view) {
        if (this.mDocument == null) {
            return;
        }
        java.util.Set<com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation> clickAreas = this.mDocument.getDocument().getClickAreas();
        removeAllViews();
        for (final com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation clickAreaRepresentation : clickAreas) {
            android.view.View clickAreaView = new com.android.internal.widget.remotecompose.player.platform.ClickAreaView(getContext(), this.mDebug, clickAreaRepresentation.getId(), clickAreaRepresentation.getContentDescription(), clickAreaRepresentation.getMetadata());
            int width = (int) clickAreaRepresentation.width();
            int height = (int) clickAreaRepresentation.height();
            android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(width, height);
            layoutParams.width = width;
            layoutParams.height = height;
            layoutParams.leftMargin = (int) clickAreaRepresentation.getLeft();
            layoutParams.topMargin = (int) clickAreaRepresentation.getTop();
            clickAreaView.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view2) {
                    com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.this.lambda$onViewAttachedToWindow$0(clickAreaRepresentation, view2);
                }
            });
            addView(clickAreaView, layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewAttachedToWindow$0(com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation clickAreaRepresentation, android.view.View view) {
        this.mDocument.getDocument().performClick(clickAreaRepresentation.getId());
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(android.view.View view) {
        removeAllViews();
    }

    public void addClickListener(final com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.ClickCallbacks clickCallbacks) {
        if (this.mDocument == null) {
            return;
        }
        this.mDocument.getDocument().addClickListener(new com.android.internal.widget.remotecompose.core.CoreDocument.ClickCallbacks() { // from class: com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas$$ExternalSyntheticLambda1
            @Override // com.android.internal.widget.remotecompose.core.CoreDocument.ClickCallbacks
            public final void click(int i, java.lang.String str) {
                com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.ClickCallbacks.this.click(i, str);
            }
        });
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void setTheme(int i) {
        this.mTheme = i;
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    public int measureDimension(int i, int i2) {
        int mode = android.view.View.MeasureSpec.getMode(i);
        int size = android.view.View.MeasureSpec.getSize(i);
        switch (mode) {
            case Integer.MIN_VALUE:
                return java.lang.Integer.min(size, i2);
            case 0:
            default:
                return i2;
            case 1073741824:
                return size;
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mDocument == null) {
            return;
        }
        setMeasuredDimension(measureDimension(i, this.mDocument.getWidth()), measureDimension(i2, this.mDocument.getHeight()));
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDocument == null) {
            return;
        }
        this.mARContext.setDebug(this.mDebug);
        this.mARContext.useCanvas(canvas);
        this.mARContext.mWidth = getWidth();
        this.mARContext.mHeight = getHeight();
        this.mDocument.paint(this.mARContext, this.mTheme);
    }
}
