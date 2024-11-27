package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class ResizeFilter extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "generateMipMap")
    private boolean mGenerateMipMap;
    private int mInputChannels;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "keepAspectRatio")
    private boolean mKeepAspectRatio;
    private android.filterfw.core.FrameFormat mLastFormat;

    @android.filterfw.core.GenerateFieldPort(name = "oheight")
    private int mOHeight;

    @android.filterfw.core.GenerateFieldPort(name = "owidth")
    private int mOWidth;
    private android.filterfw.core.MutableFrameFormat mOutputFormat;
    private android.filterfw.core.Program mProgram;

    public ResizeFilter(java.lang.String str) {
        super(str);
        this.mKeepAspectRatio = false;
        this.mGenerateMipMap = false;
        this.mLastFormat = null;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    protected void createProgram(android.filterfw.core.FilterContext filterContext, android.filterfw.core.FrameFormat frameFormat) {
        if (this.mLastFormat == null || this.mLastFormat.getTarget() != frameFormat.getTarget()) {
            this.mLastFormat = frameFormat;
            switch (frameFormat.getTarget()) {
                case 2:
                    throw new java.lang.RuntimeException("Native ResizeFilter not implemented yet!");
                case 3:
                    this.mProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
                    return;
                default:
                    throw new java.lang.RuntimeException("ResizeFilter could not create suitable program!");
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        createProgram(filterContext, pullInput.getFormat());
        android.filterfw.core.MutableFrameFormat mutableCopy = pullInput.getFormat().mutableCopy();
        if (this.mKeepAspectRatio) {
            android.filterfw.core.FrameFormat format = pullInput.getFormat();
            this.mOHeight = (this.mOWidth * format.getHeight()) / format.getWidth();
        }
        mutableCopy.setDimensions(this.mOWidth, this.mOHeight);
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(mutableCopy);
        if (this.mGenerateMipMap) {
            android.filterfw.core.GLFrame gLFrame = (android.filterfw.core.GLFrame) filterContext.getFrameManager().newFrame(pullInput.getFormat());
            gLFrame.setTextureParameter(10241, 9985);
            gLFrame.setDataFromFrame(pullInput);
            gLFrame.generateMipMap();
            this.mProgram.process(gLFrame, newFrame);
            gLFrame.release();
        } else {
            this.mProgram.process(pullInput, newFrame);
        }
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }
}
