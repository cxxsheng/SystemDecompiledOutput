package android.provider;

/* loaded from: classes3.dex */
public abstract class OneTimeUseBuilder<T> {
    private boolean used = false;

    public abstract T build();

    protected void markUsed() {
        checkNotUsed();
        this.used = true;
    }

    protected void checkNotUsed() {
        if (this.used) {
            throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
        }
    }
}
