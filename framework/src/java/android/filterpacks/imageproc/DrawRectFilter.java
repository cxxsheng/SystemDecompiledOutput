package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class DrawRectFilter extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "colorBlue")
    private float mColorBlue;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "colorGreen")
    private float mColorGreen;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "colorRed")
    private float mColorRed;
    private final java.lang.String mFixedColorFragmentShader;
    private android.filterfw.core.ShaderProgram mProgram;
    private final java.lang.String mVertexShader;

    public DrawRectFilter(java.lang.String str) {
        super(str);
        this.mColorRed = 0.8f;
        this.mColorGreen = 0.8f;
        this.mColorBlue = 0.0f;
        this.mVertexShader = "attribute vec4 aPosition;\nvoid main() {\n  gl_Position = aPosition;\n}\n";
        this.mFixedColorFragmentShader = "precision mediump float;\nuniform vec4 color;\nvoid main() {\n  gl_FragColor = color;\n}\n";
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3, 3));
        addMaskedInputPort("box", android.filterfw.format.ObjectFormat.fromClass(android.filterfw.geometry.Quad.class, 1));
        addOutputBasedOnInput(android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE);
    }

    @Override // android.filterfw.core.Filter
    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override // android.filterfw.core.Filter
    public void prepare(android.filterfw.core.FilterContext filterContext) {
        this.mProgram = new android.filterfw.core.ShaderProgram(filterContext, "attribute vec4 aPosition;\nvoid main() {\n  gl_Position = aPosition;\n}\n", "precision mediump float;\nuniform vec4 color;\nvoid main() {\n  gl_FragColor = color;\n}\n");
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput(android.app.slice.SliceItem.FORMAT_IMAGE);
        android.filterfw.geometry.Quad translated = ((android.filterfw.geometry.Quad) pullInput("box").getObjectValue()).scaled(2.0f).translated(-1.0f, -1.0f);
        android.filterfw.core.GLFrame gLFrame = (android.filterfw.core.GLFrame) filterContext.getFrameManager().duplicateFrame(pullInput);
        gLFrame.focus();
        renderBox(translated);
        pushOutput(android.app.slice.SliceItem.FORMAT_IMAGE, gLFrame);
        gLFrame.release();
    }

    private void renderBox(android.filterfw.geometry.Quad quad) {
        float[] fArr = {this.mColorRed, this.mColorGreen, this.mColorBlue, 1.0f};
        float[] fArr2 = {quad.p0.x, quad.p0.y, quad.p1.x, quad.p1.y, quad.p3.x, quad.p3.y, quad.p2.x, quad.p2.y};
        this.mProgram.setHostValue("color", fArr);
        this.mProgram.setAttributeValues("aPosition", fArr2, 2);
        this.mProgram.setVertexCount(4);
        this.mProgram.beginDrawing();
        android.opengl.GLES20.glLineWidth(1.0f);
        android.opengl.GLES20.glDrawArrays(2, 0, 4);
    }
}
