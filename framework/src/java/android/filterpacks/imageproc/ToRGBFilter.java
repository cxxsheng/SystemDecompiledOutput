package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class ToRGBFilter extends android.filterfw.core.Filter {
    private int mInputBPP;
    private android.filterfw.core.FrameFormat mLastFormat;
    private android.filterfw.core.Program mProgram;

    public ToRGBFilter(java.lang.String str) {
        super(str);
        this.mLastFormat = null;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        android.filterfw.core.MutableFrameFormat mutableFrameFormat = new android.filterfw.core.MutableFrameFormat(2, 2);
        mutableFrameFormat.setDimensionCount(2);
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, mutableFrameFormat);
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return getConvertedFormat(frameFormat);
    }

    public android.filterfw.core.FrameFormat getConvertedFormat(android.filterfw.core.FrameFormat frameFormat) {
        android.filterfw.core.MutableFrameFormat mutableCopy = frameFormat.mutableCopy();
        mutableCopy.setMetaValue(android.filterfw.format.ImageFormat.COLORSPACE_KEY, 2);
        mutableCopy.setBytesPerSample(3);
        return mutableCopy;
    }

    public void createProgram(android.filterfw.core.FilterContext filterContext, android.filterfw.core.FrameFormat frameFormat) {
        this.mInputBPP = frameFormat.getBytesPerSample();
        if (this.mLastFormat == null || this.mLastFormat.getBytesPerSample() != this.mInputBPP) {
            this.mLastFormat = frameFormat;
            switch (this.mInputBPP) {
                case 1:
                    this.mProgram = new android.filterfw.core.NativeProgram("filterpack_imageproc", "gray_to_rgb");
                    return;
                case 4:
                    this.mProgram = new android.filterfw.core.NativeProgram("filterpack_imageproc", "rgba_to_rgb");
                    return;
                default:
                    throw new java.lang.RuntimeException("Unsupported BytesPerPixel: " + this.mInputBPP + "!");
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        createProgram(filterContext, pullInput.getFormat());
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(getConvertedFormat(pullInput.getFormat()));
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }
}
