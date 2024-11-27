package android.media.effect;

/* loaded from: classes2.dex */
public class FilterGraphEffect extends android.media.effect.FilterEffect {
    private static final java.lang.String TAG = "FilterGraphEffect";
    protected android.filterfw.core.FilterGraph mGraph;
    protected java.lang.String mInputName;
    protected java.lang.String mOutputName;
    protected android.filterfw.core.GraphRunner mRunner;
    protected java.lang.Class mSchedulerClass;

    public FilterGraphEffect(android.media.effect.EffectContext effectContext, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.Class cls) {
        super(effectContext, str);
        this.mInputName = str3;
        this.mOutputName = str4;
        this.mSchedulerClass = cls;
        createGraph(str2);
    }

    private void createGraph(java.lang.String str) {
        try {
            this.mGraph = new android.filterfw.io.TextGraphReader().readGraphString(str);
            if (this.mGraph == null) {
                throw new java.lang.RuntimeException("Could not setup effect");
            }
            this.mRunner = new android.filterfw.core.SyncRunner(getFilterContext(), this.mGraph, this.mSchedulerClass);
        } catch (android.filterfw.io.GraphIOException e) {
            throw new java.lang.RuntimeException("Could not setup effect", e);
        }
    }

    @Override // android.media.effect.Effect
    public void apply(int i, int i2, int i3, int i4) {
        beginGLEffect();
        android.filterfw.core.Filter filter = this.mGraph.getFilter(this.mInputName);
        if (filter != null) {
            filter.setInputValue("texId", java.lang.Integer.valueOf(i));
            filter.setInputValue("width", java.lang.Integer.valueOf(i2));
            filter.setInputValue("height", java.lang.Integer.valueOf(i3));
            android.filterfw.core.Filter filter2 = this.mGraph.getFilter(this.mOutputName);
            if (filter2 != null) {
                filter2.setInputValue("texId", java.lang.Integer.valueOf(i4));
                try {
                    this.mRunner.run();
                    endGLEffect();
                    return;
                } catch (java.lang.RuntimeException e) {
                    throw new java.lang.RuntimeException("Internal error applying effect: ", e);
                }
            }
            throw new java.lang.RuntimeException("Internal error applying effect");
        }
        throw new java.lang.RuntimeException("Internal error applying effect");
    }

    @Override // android.media.effect.Effect
    public void setParameter(java.lang.String str, java.lang.Object obj) {
    }

    @Override // android.media.effect.Effect
    public void release() {
        this.mGraph.tearDown(getFilterContext());
        this.mGraph = null;
    }
}
