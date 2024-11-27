package android.filterfw.core;

/* loaded from: classes.dex */
public class FilterSurfaceView extends android.view.SurfaceView implements android.view.SurfaceHolder.Callback {
    private static int STATE_ALLOCATED = 0;
    private static int STATE_CREATED = 1;
    private static int STATE_INITIALIZED = 2;
    private int mFormat;
    private android.filterfw.core.GLEnvironment mGLEnv;
    private int mHeight;
    private android.view.SurfaceHolder.Callback mListener;
    private int mState;
    private int mSurfaceId;
    private int mWidth;

    public FilterSurfaceView(android.content.Context context) {
        super(context);
        this.mState = STATE_ALLOCATED;
        this.mSurfaceId = -1;
        getHolder().addCallback(this);
    }

    public FilterSurfaceView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mState = STATE_ALLOCATED;
        this.mSurfaceId = -1;
        getHolder().addCallback(this);
    }

    public synchronized void bindToListener(android.view.SurfaceHolder.Callback callback, android.filterfw.core.GLEnvironment gLEnvironment) {
        if (callback == null) {
            throw new java.lang.NullPointerException("Attempting to bind null filter to SurfaceView!");
        }
        if (this.mListener != null && this.mListener != callback) {
            throw new java.lang.RuntimeException("Attempting to bind filter " + callback + " to SurfaceView with another open filter " + this.mListener + " attached already!");
        }
        this.mListener = callback;
        if (this.mGLEnv != null && this.mGLEnv != gLEnvironment) {
            this.mGLEnv.unregisterSurfaceId(this.mSurfaceId);
        }
        this.mGLEnv = gLEnvironment;
        if (this.mState >= STATE_CREATED) {
            registerSurface();
            this.mListener.surfaceCreated(getHolder());
            if (this.mState == STATE_INITIALIZED) {
                this.mListener.surfaceChanged(getHolder(), this.mFormat, this.mWidth, this.mHeight);
            }
        }
    }

    public synchronized void unbind() {
        this.mListener = null;
    }

    public synchronized int getSurfaceId() {
        return this.mSurfaceId;
    }

    public synchronized android.filterfw.core.GLEnvironment getGLEnv() {
        return this.mGLEnv;
    }

    @Override // android.view.SurfaceHolder.Callback
    public synchronized void surfaceCreated(android.view.SurfaceHolder surfaceHolder) {
        this.mState = STATE_CREATED;
        if (this.mGLEnv != null) {
            registerSurface();
        }
        if (this.mListener != null) {
            this.mListener.surfaceCreated(surfaceHolder);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public synchronized void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mFormat = i;
        this.mWidth = i2;
        this.mHeight = i3;
        this.mState = STATE_INITIALIZED;
        if (this.mListener != null) {
            this.mListener.surfaceChanged(surfaceHolder, i, i2, i3);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public synchronized void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
        this.mState = STATE_ALLOCATED;
        if (this.mListener != null) {
            this.mListener.surfaceDestroyed(surfaceHolder);
        }
        unregisterSurface();
    }

    private void registerSurface() {
        this.mSurfaceId = this.mGLEnv.registerSurface(getHolder().getSurface());
        if (this.mSurfaceId < 0) {
            throw new java.lang.RuntimeException("Could not register Surface: " + getHolder().getSurface() + " in FilterSurfaceView!");
        }
    }

    private void unregisterSurface() {
        if (this.mGLEnv != null && this.mSurfaceId > 0) {
            this.mGLEnv.unregisterSurfaceId(this.mSurfaceId);
        }
    }
}
