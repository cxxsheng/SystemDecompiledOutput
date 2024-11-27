package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public abstract class ImageCombineFilter extends android.filterfw.core.Filter {
    protected int mCurrentTarget;
    protected java.lang.String[] mInputNames;
    protected java.lang.String mOutputName;
    protected java.lang.String mParameterName;
    protected android.filterfw.core.Program mProgram;

    protected abstract android.filterfw.core.Program getNativeProgram(android.filterfw.core.FilterContext filterContext);

    protected abstract android.filterfw.core.Program getShaderProgram(android.filterfw.core.FilterContext filterContext);

    public ImageCombineFilter(java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String str3) {
        super(str);
        this.mCurrentTarget = 0;
        this.mInputNames = strArr;
        this.mOutputName = str2;
        this.mParameterName = str3;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        if (this.mParameterName != null) {
            try {
                addProgramPort(this.mParameterName, this.mParameterName, android.filterpacks.imageproc.ImageCombineFilter.class.getDeclaredField("mProgram"), java.lang.Float.TYPE, false);
            } catch (java.lang.NoSuchFieldException e) {
                throw new java.lang.RuntimeException("Internal Error: mProgram field not found!");
            }
        }
        for (java.lang.String str : this.mInputNames) {
            addMaskedInputPort(str, android.filterfw.format.ImageFormat.create(3));
        }
        addOutputBasedOnInput(this.mOutputName, this.mInputNames[0]);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    private void assertAllInputTargetsMatch() {
        int target = getInputFormat(this.mInputNames[0]).getTarget();
        for (java.lang.String str : this.mInputNames) {
            if (target != getInputFormat(str).getTarget()) {
                throw new java.lang.RuntimeException("Type mismatch of input formats in filter " + this + ". All input frames must have the same target!");
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame[] frameArr = new android.filterfw.core.Frame[this.mInputNames.length];
        java.lang.String[] strArr = this.mInputNames;
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            frameArr[i2] = pullInput(strArr[i]);
            i++;
            i2++;
        }
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(frameArr[0].getFormat());
        updateProgramWithTarget(frameArr[0].getFormat().getTarget(), filterContext);
        this.mProgram.process(frameArr, newFrame);
        pushOutput(this.mOutputName, newFrame);
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
