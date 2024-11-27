package android.filterpacks.base;

/* loaded from: classes.dex */
public class GLTextureTarget extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(name = "texId")
    private int mTexId;

    public GLTextureTarget(java.lang.String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("frame", android.filterfw.format.ImageFormat.create(3));
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput("frame");
        android.filterfw.core.Frame newBoundFrame = filterContext.getFrameManager().newBoundFrame(android.filterfw.format.ImageFormat.create(pullInput.getFormat().getWidth(), pullInput.getFormat().getHeight(), 3, 3), 100, this.mTexId);
        newBoundFrame.setDataFromFrame(pullInput);
        newBoundFrame.release();
    }
}
