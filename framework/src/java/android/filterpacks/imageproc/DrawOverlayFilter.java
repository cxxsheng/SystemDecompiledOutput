package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class DrawOverlayFilter extends android.filterfw.core.Filter {
    private android.filterfw.core.ShaderProgram mProgram;

    public DrawOverlayFilter(java.lang.String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        android.filterfw.core.MutableFrameFormat create = android.filterfw.format.ImageFormat.create(3, 3);
        addMaskedInputPort(android.app.slice.Slice.SUBTYPE_SOURCE, create);
        addMaskedInputPort("overlay", create);
        addMaskedInputPort("box", android.filterfw.format.ObjectFormat.fromClass(android.filterfw.geometry.Quad.class, 1));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.Slice.SUBTYPE_SOURCE);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override // android.filterfw.core.Filter
    public void prepare(android.filterfw.core.FilterContext filterContext) {
        this.mProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.Slice.SUBTYPE_SOURCE);
        android.filterfw.core.Frame pullInput2 = pullInput("overlay");
        this.mProgram.setTargetRegion(((android.filterfw.geometry.Quad) pullInput("box").getObjectValue()).translated(1.0f, 1.0f).scaled(2.0f));
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(pullInput.getFormat());
        newFrame.setDataFromFrame(pullInput);
        this.mProgram.process(pullInput2, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }
}
