package android.media.permission;

/* loaded from: classes2.dex */
class CompositeSafeCloseable implements android.media.permission.SafeCloseable {
    private final android.media.permission.SafeCloseable[] mChildren;

    CompositeSafeCloseable(android.media.permission.SafeCloseable... safeCloseableArr) {
        this.mChildren = safeCloseableArr;
    }

    @Override // android.media.permission.SafeCloseable, java.lang.AutoCloseable
    public void close() {
        for (int length = this.mChildren.length - 1; length >= 0; length--) {
            this.mChildren[length].close();
        }
    }
}
