package android.content.res;

/* loaded from: classes.dex */
public class ConfigurationBoundResourceCache<T> extends android.content.res.ThemedResourceCache<android.content.res.ConstantState<T>> {
    @Override // android.content.res.ThemedResourceCache
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // android.content.res.ThemedResourceCache
    public /* bridge */ /* synthetic */ java.lang.Object get(long j, android.content.res.Resources.Theme theme) {
        return super.get(j, theme);
    }

    @Override // android.content.res.ThemedResourceCache
    public /* bridge */ /* synthetic */ int getGeneration() {
        return super.getGeneration();
    }

    @Override // android.content.res.ThemedResourceCache
    public /* bridge */ /* synthetic */ void onConfigurationChange(int i) {
        super.onConfigurationChange(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.res.ThemedResourceCache
    public /* bridge */ /* synthetic */ void put(long j, android.content.res.Resources.Theme theme, java.lang.Object obj, int i) {
        super.put(j, theme, obj, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.res.ThemedResourceCache
    public /* bridge */ /* synthetic */ void put(long j, android.content.res.Resources.Theme theme, java.lang.Object obj, int i, boolean z) {
        super.put(j, theme, obj, i, z);
    }

    public T getInstance(long j, android.content.res.Resources resources, android.content.res.Resources.Theme theme) {
        android.content.res.ConstantState constantState = (android.content.res.ConstantState) get(j, theme);
        if (constantState != null) {
            return (T) constantState.newInstance2(resources, theme);
        }
        return null;
    }

    @Override // android.content.res.ThemedResourceCache
    public boolean shouldInvalidateEntry(android.content.res.ConstantState<T> constantState, int i) {
        return android.content.res.Configuration.needNewResources(i, constantState.getChangingConfigurations());
    }
}
