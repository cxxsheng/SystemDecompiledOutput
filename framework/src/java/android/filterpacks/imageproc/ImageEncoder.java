package android.filterpacks.imageproc;

/* loaded from: classes.dex */
public class ImageEncoder extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(name = "stream")
    private java.io.OutputStream mOutputStream;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "quality")
    private int mQuality;

    public ImageEncoder(java.lang.String str) {
        super(str);
        this.mQuality = 80;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort(android.app.slice.SliceItem.FORMAT_IMAGE, android.filterfw.format.ImageFormat.create(3, 0));
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        pullInput(android.app.slice.SliceItem.FORMAT_IMAGE).getBitmap().compress(android.graphics.Bitmap.CompressFormat.JPEG, this.mQuality, this.mOutputStream);
    }
}
