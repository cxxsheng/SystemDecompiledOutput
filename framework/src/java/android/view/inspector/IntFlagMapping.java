package android.view.inspector;

/* loaded from: classes4.dex */
public final class IntFlagMapping {
    private final java.util.List<android.view.inspector.IntFlagMapping.Flag> mFlags = new java.util.ArrayList();

    public java.util.Set<java.lang.String> get(int i) {
        java.util.HashSet hashSet = new java.util.HashSet(this.mFlags.size());
        for (android.view.inspector.IntFlagMapping.Flag flag : this.mFlags) {
            if (flag.isEnabledFor(i)) {
                hashSet.add(flag.mName);
            }
        }
        return java.util.Collections.unmodifiableSet(hashSet);
    }

    public void add(int i, int i2, java.lang.String str) {
        this.mFlags.add(new android.view.inspector.IntFlagMapping.Flag(i, i2, str));
    }

    private static final class Flag {
        private final int mMask;
        private final java.lang.String mName;
        private final int mTarget;

        private Flag(int i, int i2, java.lang.String str) {
            this.mTarget = i2;
            this.mMask = i;
            this.mName = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEnabledFor(int i) {
            return (i & this.mMask) == this.mTarget;
        }
    }
}
