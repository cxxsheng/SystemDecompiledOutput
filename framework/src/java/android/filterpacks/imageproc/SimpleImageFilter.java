package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public abstract class SimpleImageFilter extends android.filterfw.core.Filter {
    protected int mCurrentTarget;
    protected java.lang.String mParameterName;
    protected android.filterfw.core.Program mProgram;

    protected abstract android.filterfw.core.Program getNativeProgram(android.filterfw.core.FilterContext filterContext);

    protected abstract android.filterfw.core.Program getShaderProgram(android.filterfw.core.FilterContext filterContext);

    public SimpleImageFilter(java.lang.String str, java.lang.String str2) {
        super(str);
        this.mCurrentTarget = 0;
        this.mParameterName = str2;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        if (this.mParameterName != null) {
            try {
                addProgramPort(this.mParameterName, this.mParameterName, android.filterpacks.imageproc.SimpleImageFilter.class.getDeclaredField("mProgram"), java.lang.Float.TYPE, false);
            } catch (java.lang.NoSuchFieldException e) {
                throw new java.lang.RuntimeException("Internal Error: mProgram field not found!");
            }
        }
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.core.FrameFormat format = pullInput.getFormat();
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(format);
        updateProgramWithTarget(format.getTarget(), filterContext);
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }

    protected void updateProgramWithTarget(int i, android.filterfw.core.FilterContext filterContext) {
        if (i != this.mCurrentTarget) {
            switch (i) {
                case 2:
                    this.mProgram = getNativeProgram(filterContext);
                    break;
                case 3:
                    this.mProgram = getShaderProgram(filterContext);
                    break;
                default:
                    this.mProgram = null;
                    break;
            }
            if (this.mProgram == null) {
                throw new java.lang.RuntimeException("Could not create a program for image filter " + this + "!");
            }
            initProgramInputs(this.mProgram, filterContext);
            this.mCurrentTarget = i;
        }
    }
}
