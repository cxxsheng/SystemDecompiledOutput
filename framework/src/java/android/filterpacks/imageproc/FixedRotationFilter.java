package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class FixedRotationFilter extends android.filterfw.core.Filter {
    private android.filterfw.core.ShaderProgram mProgram;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "rotation")
    private int mRotation;

    public FixedRotationFilter(java.lang.String str) {
        super(str);
        this.mRotation = 0;
        this.mProgram = null;
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

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.geometry.Quad quad;
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        if (this.mRotation == 0) {
            pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, pullInput);
            return;
        }
        android.filterfw.core.FrameFormat format = pullInput.getFormat();
        if (this.mProgram == null) {
            this.mProgram = android.filterfw.core.ShaderProgram.createIdentity(filterContext);
        }
        android.filterfw.core.MutableFrameFormat mutableCopy = format.mutableCopy();
        int width = format.getWidth();
        int height = format.getHeight();
        android.filterfw.geometry.Point point = new android.filterfw.geometry.Point(0.0f, 0.0f);
        android.filterfw.geometry.Point point2 = new android.filterfw.geometry.Point(1.0f, 0.0f);
        android.filterfw.geometry.Point point3 = new android.filterfw.geometry.Point(0.0f, 1.0f);
        android.filterfw.geometry.Point point4 = new android.filterfw.geometry.Point(1.0f, 1.0f);
        switch (java.lang.Math.round(this.mRotation / 90.0f) % 4) {
            case 1:
                quad = new android.filterfw.geometry.Quad(point3, point, point4, point2);
                mutableCopy.setDimensions(height, width);
                break;
            case 2:
                quad = new android.filterfw.geometry.Quad(point4, point3, point2, point);
                break;
            case 3:
                quad = new android.filterfw.geometry.Quad(point2, point4, point, point3);
                mutableCopy.setDimensions(height, width);
                break;
            default:
                quad = new android.filterfw.geometry.Quad(point, point2, point3, point4);
                break;
        }
        android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(mutableCopy);
        this.mProgram.setSourceRegion(quad);
        this.mProgram.process(pullInput, newFrame);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, newFrame);
        newFrame.release();
    }
}
