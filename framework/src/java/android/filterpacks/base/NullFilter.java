package android.filterpacks.base;

/* loaded from: classes.dex */
public class NullFilter extends android.filterfw.core.Filter {
    public NullFilter(java.lang.String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("frame");
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        pullInput("frame");
    }
}
