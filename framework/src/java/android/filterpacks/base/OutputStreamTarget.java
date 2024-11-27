package android.filterpacks.base;

/* loaded from: classes.dex */
public class OutputStreamTarget extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(name = "stream")
    private java.io.OutputStream mOutputStream;

    public OutputStreamTarget(java.lang.String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("data");
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        java.nio.ByteBuffer data;
        android.filterfw.core.Frame pullInput = pullInput("data");
        if (pullInput.getFormat().getObjectClass() == java.lang.String.class) {
            data = java.nio.ByteBuffer.wrap(((java.lang.String) pullInput.getObjectValue()).getBytes());
        } else {
            data = pullInput.getData();
        }
        try {
            this.mOutputStream.write(data.array(), 0, data.limit());
            this.mOutputStream.flush();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("OutputStreamTarget: Could not write to stream: " + e.getMessage() + "!");
        }
    }
}
