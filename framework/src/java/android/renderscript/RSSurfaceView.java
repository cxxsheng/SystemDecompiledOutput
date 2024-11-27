package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class RSSurfaceView extends android.view.SurfaceView implements android.view.SurfaceHolder.Callback {
    private android.renderscript.RenderScriptGL mRS;
    private android.view.SurfaceHolder mSurfaceHolder;

    public RSSurfaceView(android.content.Context context) {
        super(context);
        init();
    }

    public RSSurfaceView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(android.view.SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
        synchronized (this) {
            if (this.mRS != null) {
                this.mRS.setSurface(null, 0, 0);
            }
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        synchronized (this) {
            if (this.mRS != null) {
                this.mRS.setSurface(surfaceHolder, i2, i3);
            }
        }
    }

    public void pause() {
        if (this.mRS != null) {
            this.mRS.pause();
        }
    }

    public void resume() {
        if (this.mRS != null) {
            this.mRS.resume();
        }
    }

    public android.renderscript.RenderScriptGL createRenderScriptGL(android.renderscript.RenderScriptGL.SurfaceConfig surfaceConfig) {
        android.renderscript.RenderScriptGL renderScriptGL = new android.renderscript.RenderScriptGL(getContext(), surfaceConfig);
        setRenderScriptGL(renderScriptGL);
        return renderScriptGL;
    }

    public void destroyRenderScriptGL() {
        synchronized (this) {
            this.mRS.destroy();
            this.mRS = null;
        }
    }

    public void setRenderScriptGL(android.renderscript.RenderScriptGL renderScriptGL) {
        this.mRS = renderScriptGL;
    }

    public android.renderscript.RenderScriptGL getRenderScriptGL() {
        return this.mRS;
    }
}
