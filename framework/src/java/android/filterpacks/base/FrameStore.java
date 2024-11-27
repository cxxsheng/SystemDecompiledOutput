package android.filterpacks.base;

/* loaded from: classes.dex */
public class FrameStore extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFieldPort(name = "key")
    private java.lang.String mKey;

    public FrameStore(java.lang.String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("frame");
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        filterContext.storeFrame(this.mKey, pullInput("frame"));
    }
}
