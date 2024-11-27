package android.content.res;

/* loaded from: classes.dex */
class DrawableCache extends android.content.res.ThemedResourceCache<android.graphics.drawable.Drawable.ConstantState> {
    DrawableCache() {
    }

    public android.graphics.drawable.Drawable getInstance(long j, android.content.res.Resources resources, android.content.res.Resources.Theme theme) {
        android.graphics.drawable.Drawable.ConstantState constantState = get(j, theme);
        if (constantState != null) {
            return constantState.newDrawable(resources, theme);
        }
        return null;
    }

    @Override // android.content.res.ThemedResourceCache
    public boolean shouldInvalidateEntry(android.graphics.drawable.Drawable.ConstantState constantState, int i) {
        return android.content.res.Configuration.needNewResources(i, constantState.getChangingConfigurations());
    }
}
