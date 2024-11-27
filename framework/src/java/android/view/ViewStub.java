package android.view;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public final class ViewStub extends android.view.View {
    private android.view.ViewStub.OnInflateListener mInflateListener;
    private int mInflatedId;
    private java.lang.ref.WeakReference<android.view.View> mInflatedViewRef;
    private android.view.LayoutInflater mInflater;
    private int mLayoutResource;

    public interface OnInflateListener {
        void onInflate(android.view.ViewStub viewStub, android.view.View view);
    }

    public ViewStub(android.content.Context context) {
        this(context, 0);
    }

    public ViewStub(android.content.Context context, int i) {
        this(context, (android.util.AttributeSet) null);
        this.mLayoutResource = i;
    }

    public ViewStub(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewStub(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ViewStub(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ViewStub, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ViewStub, attributeSet, obtainStyledAttributes, i, i2);
        this.mInflatedId = obtainStyledAttributes.getResourceId(2, -1);
        this.mLayoutResource = obtainStyledAttributes.getResourceId(1, 0);
        this.mID = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        setVisibility(8);
        setWillNotDraw(true);
    }

    public int getInflatedId() {
        return this.mInflatedId;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setInflatedIdAsync")
    public void setInflatedId(int i) {
        this.mInflatedId = i;
    }

    public java.lang.Runnable setInflatedIdAsync(int i) {
        this.mInflatedId = i;
        return null;
    }

    public int getLayoutResource() {
        return this.mLayoutResource;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setLayoutResourceAsync")
    public void setLayoutResource(int i) {
        this.mLayoutResource = i;
    }

    public java.lang.Runnable setLayoutResourceAsync(int i) {
        this.mLayoutResource = i;
        return null;
    }

    public void setLayoutInflater(android.view.LayoutInflater layoutInflater) {
        this.mInflater = layoutInflater;
    }

    public android.view.LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
    }

    @Override // android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
    }

    @Override // android.view.View
    @android.view.RemotableViewMethod(asyncImpl = "setVisibilityAsync")
    public void setVisibility(int i) {
        if (this.mInflatedViewRef != null) {
            android.view.View view = this.mInflatedViewRef.get();
            if (view != null) {
                view.setVisibility(i);
                return;
            }
            throw new java.lang.IllegalStateException("setVisibility called on un-referenced view");
        }
        super.setVisibility(i);
        if (i == 0 || i == 4) {
            inflate();
        }
    }

    public java.lang.Runnable setVisibilityAsync(int i) {
        if (i == 0 || i == 4) {
            return new android.view.ViewStub.ViewReplaceRunnable(inflateViewNoAdd((android.view.ViewGroup) getParent()));
        }
        return null;
    }

    private android.view.View inflateViewNoAdd(android.view.ViewGroup viewGroup) {
        android.view.LayoutInflater from;
        if (this.mInflater != null) {
            from = this.mInflater;
        } else {
            from = android.view.LayoutInflater.from(this.mContext);
        }
        android.view.View inflate = from.inflate(this.mLayoutResource, viewGroup, false);
        if (this.mInflatedId != -1) {
            inflate.setId(this.mInflatedId);
        }
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceSelfWithView(android.view.View view, android.view.ViewGroup viewGroup) {
        int indexOfChild = viewGroup.indexOfChild(this);
        viewGroup.removeViewInLayout(this);
        android.view.ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            viewGroup.addView(view, indexOfChild, layoutParams);
        } else {
            viewGroup.addView(view, indexOfChild);
        }
    }

    public android.view.View inflate() {
        android.view.ViewParent parent = getParent();
        if (parent != null && (parent instanceof android.view.ViewGroup)) {
            if (this.mLayoutResource != 0) {
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) parent;
                android.view.View inflateViewNoAdd = inflateViewNoAdd(viewGroup);
                replaceSelfWithView(inflateViewNoAdd, viewGroup);
                this.mInflatedViewRef = new java.lang.ref.WeakReference<>(inflateViewNoAdd);
                if (this.mInflateListener != null) {
                    this.mInflateListener.onInflate(this, inflateViewNoAdd);
                }
                return inflateViewNoAdd;
            }
            throw new java.lang.IllegalArgumentException("ViewStub must have a valid layoutResource");
        }
        throw new java.lang.IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
    }

    public void setOnInflateListener(android.view.ViewStub.OnInflateListener onInflateListener) {
        this.mInflateListener = onInflateListener;
    }

    public class ViewReplaceRunnable implements java.lang.Runnable {
        public final android.view.View view;

        ViewReplaceRunnable(android.view.View view) {
            this.view = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.ViewStub.this.replaceSelfWithView(this.view, (android.view.ViewGroup) android.view.ViewStub.this.getParent());
        }
    }
}
