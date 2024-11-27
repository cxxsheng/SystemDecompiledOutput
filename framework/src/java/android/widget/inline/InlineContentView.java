package android.widget.inline;

/* loaded from: classes4.dex */
public class InlineContentView extends android.view.ViewGroup {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "InlineContentView";
    private final android.view.ViewTreeObserver.OnDrawListener mOnDrawListener;
    private final android.view.SurfaceControl.OnReparentListener mOnReparentListener;
    private int[] mParentPosition;
    private android.graphics.PointF mParentScale;
    private java.lang.ref.WeakReference<android.view.SurfaceView> mParentSurfaceOwnerView;
    private final android.view.SurfaceHolder.Callback mSurfaceCallback;
    private android.widget.inline.InlineContentView.SurfaceControlCallback mSurfaceControlCallback;
    private android.widget.inline.InlineContentView.SurfacePackageUpdater mSurfacePackageUpdater;
    private final android.view.SurfaceView mSurfaceView;

    public interface SurfaceControlCallback {
        void onCreated(android.view.SurfaceControl surfaceControl);

        void onDestroyed(android.view.SurfaceControl surfaceControl);
    }

    public interface SurfacePackageUpdater {
        void getSurfacePackage(java.util.function.Consumer<android.view.SurfaceControlViewHost.SurfacePackage> consumer);

        void onSurfacePackageReleased();
    }

    public InlineContentView(android.content.Context context) {
        this(context, null);
    }

    public InlineContentView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public InlineContentView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        this.mSurfaceView.setEnableSurfaceClipping(true);
    }

    public android.view.SurfaceControl getSurfaceControl() {
        return this.mSurfaceView.getSurfaceControl();
    }

    @Override // android.view.View
    public void setClipBounds(android.graphics.Rect rect) {
        super.setClipBounds(rect);
        this.mSurfaceView.setClipBounds(rect);
    }

    public InlineContentView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSurfaceCallback = new android.view.SurfaceHolder.Callback() { // from class: android.widget.inline.InlineContentView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(android.view.SurfaceHolder surfaceHolder) {
                android.view.SurfaceControl surfaceControl = android.widget.inline.InlineContentView.this.mSurfaceView.getSurfaceControl();
                surfaceControl.addOnReparentListener(android.widget.inline.InlineContentView.this.mOnReparentListener);
                android.widget.inline.InlineContentView.this.mSurfaceControlCallback.onCreated(surfaceControl);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i3, int i4, int i5) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
                android.view.SurfaceControl surfaceControl = android.widget.inline.InlineContentView.this.mSurfaceView.getSurfaceControl();
                surfaceControl.removeOnReparentListener(android.widget.inline.InlineContentView.this.mOnReparentListener);
                android.widget.inline.InlineContentView.this.mSurfaceControlCallback.onDestroyed(surfaceControl);
            }
        };
        this.mOnReparentListener = new android.view.SurfaceControl.OnReparentListener() { // from class: android.widget.inline.InlineContentView.2
            @Override // android.view.SurfaceControl.OnReparentListener
            public void onReparent(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
                android.view.View view;
                if (surfaceControl == null) {
                    view = null;
                } else {
                    view = surfaceControl.getLocalOwnerView();
                }
                if (!(view instanceof android.view.SurfaceView)) {
                    android.widget.inline.InlineContentView.this.mParentSurfaceOwnerView = null;
                } else {
                    android.widget.inline.InlineContentView.this.mParentSurfaceOwnerView = new java.lang.ref.WeakReference((android.view.SurfaceView) view);
                }
            }
        };
        this.mOnDrawListener = new android.view.ViewTreeObserver.OnDrawListener() { // from class: android.widget.inline.InlineContentView.3
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public void onDraw() {
                android.widget.inline.InlineContentView.this.computeParentPositionAndScale();
                android.widget.inline.InlineContentView.this.mSurfaceView.setVisibility(android.widget.inline.InlineContentView.this.isShown() ? 0 : 8);
            }
        };
        this.mSurfaceView = new android.view.SurfaceView(context, attributeSet, i, i2) { // from class: android.widget.inline.InlineContentView.4
            @Override // android.view.SurfaceView
            protected void onSetSurfacePositionAndScale(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, int i3, int i4, float f, float f2) {
                int i5;
                int i6;
                if (android.widget.inline.InlineContentView.this.mParentPosition == null) {
                    i5 = i3;
                    i6 = i4;
                } else {
                    i5 = (int) ((i3 - android.widget.inline.InlineContentView.this.mParentPosition[0]) / android.widget.inline.InlineContentView.this.mParentScale.x);
                    i6 = (int) ((i4 - android.widget.inline.InlineContentView.this.mParentPosition[1]) / android.widget.inline.InlineContentView.this.mParentScale.y);
                }
                super.onSetSurfacePositionAndScale(transaction, surfaceControl, i5, i6, android.widget.inline.InlineContentView.this.getScaleX(), android.widget.inline.InlineContentView.this.getScaleY());
            }
        };
        this.mSurfaceView.setZOrderOnTop(true);
        this.mSurfaceView.getHolder().setFormat(-2);
        addView(this.mSurfaceView);
        setImportantForAccessibility(2);
    }

    public void setChildSurfacePackageUpdater(android.widget.inline.InlineContentView.SurfacePackageUpdater surfacePackageUpdater) {
        this.mSurfacePackageUpdater = surfacePackageUpdater;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mSurfacePackageUpdater != null) {
            this.mSurfacePackageUpdater.getSurfacePackage(new java.util.function.Consumer() { // from class: android.widget.inline.InlineContentView$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.widget.inline.InlineContentView.this.lambda$onAttachedToWindow$0((android.view.SurfaceControlViewHost.SurfacePackage) obj);
                }
            });
        }
        this.mSurfaceView.setVisibility(getVisibility());
        getViewTreeObserver().addOnDrawListener(this.mOnDrawListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$0(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
        if (getViewRootImpl() != null) {
            this.mSurfaceView.setChildSurfacePackage(surfacePackage);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mSurfacePackageUpdater != null) {
            this.mSurfacePackageUpdater.onSurfacePackageReleased();
        }
        getViewTreeObserver().removeOnDrawListener(this.mOnDrawListener);
        this.mSurfaceView.setVisibility(8);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mSurfaceView.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void setSurfaceControlCallback(android.widget.inline.InlineContentView.SurfaceControlCallback surfaceControlCallback) {
        if (this.mSurfaceControlCallback != null) {
            this.mSurfaceView.getHolder().removeCallback(this.mSurfaceCallback);
        }
        this.mSurfaceControlCallback = surfaceControlCallback;
        if (this.mSurfaceControlCallback != null) {
            this.mSurfaceView.getHolder().addCallback(this.mSurfaceCallback);
        }
    }

    public boolean isZOrderedOnTop() {
        return this.mSurfaceView.isZOrderedOnTop();
    }

    public boolean setZOrderedOnTop(boolean z) {
        return this.mSurfaceView.setZOrderedOnTop(z, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void computeParentPositionAndScale() {
        android.view.SurfaceView surfaceView = this.mParentSurfaceOwnerView != null ? this.mParentSurfaceOwnerView.get() : null;
        boolean z = true;
        if (surfaceView != null) {
            if (this.mParentPosition == null) {
                this.mParentPosition = new int[2];
            }
            int i = this.mParentPosition[0];
            int i2 = this.mParentPosition[1];
            surfaceView.getLocationInSurface(this.mParentPosition);
            boolean z2 = (i == this.mParentPosition[0] && i2 == this.mParentPosition[1]) ? false : true;
            if (this.mParentScale == null) {
                this.mParentScale = new android.graphics.PointF();
            }
            float width = surfaceView.getSurfaceRenderPosition().width();
            float f = this.mParentScale.x;
            if (width > 0.0f) {
                this.mParentScale.x = width / surfaceView.getWidth();
            } else {
                this.mParentScale.x = 1.0f;
            }
            if (!z2 && java.lang.Float.compare(f, this.mParentScale.x) != 0) {
                z2 = true;
            }
            float height = surfaceView.getSurfaceRenderPosition().height();
            float f2 = this.mParentScale.y;
            if (height > 0.0f) {
                this.mParentScale.y = height / surfaceView.getHeight();
            } else {
                this.mParentScale.y = 1.0f;
            }
            if (z2 || java.lang.Float.compare(f2, this.mParentScale.y) == 0) {
                z = z2;
            }
        } else if (this.mParentPosition == null && this.mParentScale == null) {
            z = false;
        } else {
            this.mParentPosition = null;
            this.mParentScale = null;
        }
        if (z) {
            this.mSurfaceView.requestUpdateSurfacePositionAndScale();
        }
    }
}
