package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class ImageStitcher extends android.filterfw.core.Filter {
    private int mImageHeight;
    private int mImageWidth;
    private int mInputHeight;
    private int mInputWidth;
    private android.filterfw.core.Frame mOutputFrame;

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

    public ImageStitcher(java.lang.String str) {
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

    private android.filterfw.core.FrameFormat calcOutputFormatForInput(android.filterfw.core.FrameFormat frameFormat) {
        android.filterfw.core.MutableFrameFormat mutableCopy = frameFormat.mutableCopy();
        this.mInputWidth = frameFormat.getWidth();
        this.mInputHeight = frameFormat.getHeight();
        this.mSliceWidth = this.mInputWidth - (this.mPadSize * 2);
        this.mSliceHeight = this.mInputHeight - (this.mPadSize * 2);
        this.mImageWidth = this.mSliceWidth * this.mXSlices;
        this.mImageHeight = this.mSliceHeight * this.mYSlices;
        mutableCopy.setDimensions(this.mImageWidth, this.mImageHeight);
        return mutableCopy;
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.core.FrameFormat format = pullInput.getFormat();
        if (this.mSliceIndex == 0) {
            this.mOutputFrame = filterContext.getFrameManager().newFrame(calcOutputFormatForInput(format));
        } else if (format.getWidth() != this.mInputWidth || format.getHeight() != this.mInputHeight) {
            throw new java.lang.RuntimeException("Image size should not change.");
        }
        if (this.mProgram == null) {
            this.mProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
        }
        int i = (this.mSliceIndex % this.mXSlices) * this.mSliceWidth;
        int i2 = (this.mSliceIndex / this.mXSlices) * this.mSliceHeight;
        float min = java.lang.Math.min(this.mSliceWidth, this.mImageWidth - i);
        float min2 = java.lang.Math.min(this.mSliceHeight, this.mImageHeight - i2);
        ((android.filterfw.core.ShaderProgram) this.mProgram).setSourceRect(this.mPadSize / this.mInputWidth, this.mPadSize / this.mInputHeight, min / this.mInputWidth, min2 / this.mInputHeight);
        ((android.filterfw.core.ShaderProgram) this.mProgram).setTargetRect(i / this.mImageWidth, i2 / this.mImageHeight, min / this.mImageWidth, min2 / this.mImageHeight);
        this.mProgram.process(pullInput, this.mOutputFrame);
        this.mSliceIndex++;
        if (this.mSliceIndex == this.mXSlices * this.mYSlices) {
            pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, this.mOutputFrame);
            this.mOutputFrame.release();
            this.mSliceIndex = 0;
        }
    }
}
