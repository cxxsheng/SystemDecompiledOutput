package android.content.res;

/* loaded from: classes.dex */
public abstract class ComplexColor {
    private int mChangingConfigurations;

    public abstract boolean canApplyTheme();

    public abstract android.content.res.ConstantState<android.content.res.ComplexColor> getConstantState();

    public abstract int getDefaultColor();

    public abstract android.content.res.ComplexColor obtainForTheme(android.content.res.Resources.Theme theme);

    public boolean isStateful() {
        return false;
    }

    final void setBaseChangingConfigurations(int i) {
        this.mChangingConfigurations = i;
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }
}
