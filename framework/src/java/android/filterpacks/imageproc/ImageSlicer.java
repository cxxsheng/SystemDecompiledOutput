package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class ImageSlicer extends android.filterfw.core.Filter {
    private int mInputHeight;
    private int mInputWidth;
    private android.filterfw.core.Frame mOriginalFrame;
    private int mOutputHeight;
    private int mOutputWidth;

    @android.filterfw.core.GenerateFieldPort(name = "padSize")
    private int mPadSize;
    private android.filterfw.core.Program mProgram;
    private int mSliceHeight;
    private int mSliceIndex;
    private int mSliceWidth;

    @android.filterfw.core.GenerateFieldPort(name = "xSlices")
    private int mXSlices;

    @android.filterfw.core.GenerateFieldPort(name = "ySlices")
    private int mYSlices;

    public ImageSlicer(java.lang.String str) {
        super(str);
        this.mSliceIndex = 0;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3, 3));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    private void calcOutputFormatForInput(android.filterfw.core.Frame frame) {
        this.mInputWidth = frame.getFormat().getWidth();
        this.mInputHeight = frame.getFormat().getHeight();
        this.mSliceWidth = ((this.mInputWidth + this.mXSlices) - 1) / this.mXSlices;
        this.mSliceHeight = ((this.mInputHeight + this.mYSlices) - 1) / this.mYSlices;
        this.mOutputWidth = this.mSliceWidth + (this.mPadSize * 2);
        this.mOutputHeight = this.mSliceHeight + (this.mPadSize * 2);
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        if (this.mSliceIndex == 0) {
            this.mOriginalFrame = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
            calcOutputFormatForInput(this.mOriginalFrame);
        }
        android.filterfw.core.MutableFrameFormat mutableCopy = this.mOriginalFrame.getFormat().mutableCopy();
        mutableCopy.setDimensions(this.mOutputWidth, this.mOutputHeight);
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(mutableCopy);
        if (this.mProgram == null) {
            this.mProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
        }
        int i = this.mSliceIndex % this.mXSlices;
        int i2 = this.mSliceIndex / this.mXSlices;
        ((android.filterfw.core.ShaderProgram) this.mProgram).setSourceRect(((i * this.mSliceWidth) - this.mPadSize) / this.mInputWidth, ((i2 * this.mSliceHeight) - this.mPadSize) / this.mInputHeight, this.mOutputWidth / this.mInputWidth, this.mOutputHeight / this.mInputHeight);
        this.mProgram.process(this.mOriginalFrame, newFrame);
        this.mSliceIndex++;
        if (this.mSliceIndex == this.mXSlices * this.mYSlices) {
            this.mSliceIndex = 0;
            this.mOriginalFrame.release();
            setWaitsOnInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, true);
        } else {
            this.mOriginalFrame.retain();
            setWaitsOnInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, false);
        }
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }
}
