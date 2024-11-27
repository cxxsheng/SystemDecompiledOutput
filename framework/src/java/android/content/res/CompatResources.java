package android.content.res;

/* loaded from: classes.dex */
public class CompatResources extends android.content.res.Resources {
    private java.lang.ref.WeakReference<android.content.Context> mContext;

    public CompatResources(java.lang.ClassLoader classLoader) {
        super(classLoader);
        this.mContext = new java.lang.ref.WeakReference<>(null);
    }

    public void setContext(android.content.Context context) {
        this.mContext = new java.lang.ref.WeakReference<>(context);
    }

    @Override // android.content.res.Resources
    public android.graphics.drawable.Drawable getDrawable(int i) throws android.content.res.Resources.NotFoundException {
        return getDrawable(i, getTheme());
    }

    @Override // android.content.res.Resources
    public android.graphics.drawable.Drawable getDrawableForDensity(int i, int i2) throws android.content.res.Resources.NotFoundException {
        return getDrawableForDensity(i, i2, getTheme());
    }

    @Override // android.content.res.Resources
    public int getColor(int i) throws android.content.res.Resources.NotFoundException {
        return getColor(i, getTheme());
    }

    @Override // android.content.res.Resources
    public android.content.res.ColorStateList getColorStateList(int i) throws android.content.res.Resources.NotFoundException {
        return getColorStateList(i, getTheme());
    }

    private android.content.res.Resources.Theme getTheme() {
        android.content.Context context = this.mContext.get();
        if (context != null) {
            return context.getTheme();
        }
        return null;
    }
}
