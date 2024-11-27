package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class RSTextureView extends android.view.TextureView implements android.view.TextureView.SurfaceTextureListener {
    private android.renderscript.RenderScriptGL mRS;
    private android.graphics.SurfaceTexture mSurfaceTexture;

    public RSTextureView(android.content.Context context) {
        super(context);
        init();
    }

    public RSTextureView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setSurfaceTextureListener(this);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(android.graphics.SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceTexture = surfaceTexture;
        if (this.mRS != null) {
            this.mRS.setSurfaceTexture(this.mSurfaceTexture, i, i2);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(android.graphics.SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceTexture = surfaceTexture;
        if (this.mRS != null) {
            this.mRS.setSurfaceTexture(this.mSurfaceTexture, i, i2);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(android.graphics.SurfaceTexture surfaceTexture) {
        this.mSurfaceTexture = surfaceTexture;
        if (this.mRS != null) {
            this.mRS.setSurfaceTexture(null, 0, 0);
            return true;
        }
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(android.graphics.SurfaceTexture surfaceTexture) {
        this.mSurfaceTexture = surfaceTexture;
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
        if (this.mSurfaceTexture != null) {
            this.mRS.setSurfaceTexture(this.mSurfaceTexture, getWidth(), getHeight());
        }
        return renderScriptGL;
    }

    public void destroyRenderScriptGL() {
        this.mRS.destroy();
        this.mRS = null;
    }

    public void setRenderScriptGL(android.renderscript.RenderScriptGL renderScriptGL) {
        this.mRS = renderScriptGL;
        if (this.mSurfaceTexture != null) {
            this.mRS.setSurfaceTexture(this.mSurfaceTexture, getWidth(), getHeight());
        }
    }

    public android.renderscript.RenderScriptGL getRenderScriptGL() {
        return this.mRS;
    }
}
