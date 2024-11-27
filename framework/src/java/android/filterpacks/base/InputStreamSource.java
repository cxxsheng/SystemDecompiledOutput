package android.filterpacks.base;

/* loaded from: classes.dex */
public class InputStreamSource extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(name = "stream")
    private java.io.InputStream mInputStream;

    @android.filterfw.core.GenerateFinalPort(hasDefault = true, name = android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT)
    private android.filterfw.core.MutableFrameFormat mOutputFormat;

    @android.filterfw.core.GenerateFinalPort(name = "target")
    private java.lang.String mTarget;

    public InputStreamSource(java.lang.String str) {
        super(str);
        this.mOutputFormat = null;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        int readTargetString = android.filterfw.core.FrameFormat.readTargetString(this.mTarget);
        if (this.mOutputFormat == null) {
            this.mOutputFormat = android.filterfw.format.PrimitiveFormat.createByteFormat(readTargetString);
        }
        addOutputPort("data", this.mOutputFormat);
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            int i = 0;
            while (true) {
                int read = this.mInputStream.read(bArr);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr, 0, read);
                    i += read;
                } else {
                    java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                    this.mOutputFormat.setDimensions(i);
                    android.filterfw.core.Frame newFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
                    newFrame.setData(wrap);
                    pushOutput("data", newFrame);
                    newFrame.release();
                    closeOutputPort("data");
                    return;
                }
            }
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("InputStreamSource: Could not read stream: " + e.getMessage() + "!");
        }
    }
}
