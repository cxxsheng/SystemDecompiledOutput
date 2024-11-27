package android.media.effect;

/* loaded from: classes2.dex */
public class SingleFilterEffect extends android.media.effect.FilterEffect {
    protected android.filterfw.core.FilterFunction mFunction;
    protected java.lang.String mInputName;
    protected java.lang.String mOutputName;

    public SingleFilterEffect(android.media.effect.EffectContext effectContext, java.lang.String str, java.lang.Class cls, java.lang.String str2, java.lang.String str3, java.lang.Object... objArr) {
        super(effectContext, str);
        this.mInputName = str2;
        this.mOutputName = str3;
        android.filterfw.core.Filter createFilterByClass = android.filterfw.core.FilterFactory.sharedFactory().createFilterByClass(cls, cls.getSimpleName());
        createFilterByClass.initWithAssignmentList(objArr);
        this.mFunction = new android.filterfw.core.FilterFunction(getFilterContext(), createFilterByClass);
    }

    @Override // android.media.effect.Effect
    public void apply(int i, int i2, int i3, int i4) {
        beginGLEffect();
        android.filterfw.core.Frame frameFromTexture = frameFromTexture(i, i2, i3);
        android.filterfw.core.Frame frameFromTexture2 = frameFromTexture(i4, i2, i3);
        android.filterfw.core.Frame executeWithArgList = this.mFunction.executeWithArgList(this.mInputName, frameFromTexture);
        frameFromTexture2.setDataFromFrame(executeWithArgList);
        frameFromTexture.release();
        frameFromTexture2.release();
        executeWithArgList.release();
        endGLEffect();
    }

    @Override // android.media.effect.Effect
    public void setParameter(java.lang.String str, java.lang.Object obj) {
        this.mFunction.setInputValue(str, obj);
    }

    @Override // android.media.effect.Effect
    public void release() {
        this.mFunction.tearDown();
        this.mFunction = null;
    }
}
