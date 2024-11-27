package com.android.server.pm;

@com.android.internal.annotations.Immutable
/* loaded from: classes2.dex */
public final class DefaultCrossProfileIntentFilter {
    public final int direction;
    public final com.android.server.pm.WatchedIntentFilter filter;
    public final int flags;
    public final boolean letsPersonalDataIntoProfile;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Direction {
        public static final int TO_PARENT = 0;
        public static final int TO_PROFILE = 1;
    }

    private DefaultCrossProfileIntentFilter(com.android.server.pm.WatchedIntentFilter watchedIntentFilter, int i, int i2, boolean z) {
        java.util.Objects.requireNonNull(watchedIntentFilter);
        this.filter = watchedIntentFilter;
        this.flags = i;
        this.direction = i2;
        this.letsPersonalDataIntoProfile = z;
    }

    static final class Builder {
        private final int mDirection;
        private final com.android.server.pm.WatchedIntentFilter mFilter = new com.android.server.pm.WatchedIntentFilter();
        private final int mFlags;
        private final boolean mLetsPersonalDataIntoProfile;

        Builder(int i, int i2, boolean z) {
            this.mDirection = i;
            this.mFlags = i2;
            this.mLetsPersonalDataIntoProfile = z;
        }

        com.android.server.pm.DefaultCrossProfileIntentFilter.Builder addAction(java.lang.String str) {
            this.mFilter.addAction(str);
            return this;
        }

        com.android.server.pm.DefaultCrossProfileIntentFilter.Builder addCategory(java.lang.String str) {
            this.mFilter.addCategory(str);
            return this;
        }

        com.android.server.pm.DefaultCrossProfileIntentFilter.Builder addDataType(java.lang.String str) {
            try {
                this.mFilter.addDataType(str);
            } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
            }
            return this;
        }

        com.android.server.pm.DefaultCrossProfileIntentFilter.Builder addDataScheme(java.lang.String str) {
            this.mFilter.addDataScheme(str);
            return this;
        }

        com.android.server.pm.DefaultCrossProfileIntentFilter build() {
            return new com.android.server.pm.DefaultCrossProfileIntentFilter(this.mFilter, this.mFlags, this.mDirection, this.mLetsPersonalDataIntoProfile);
        }
    }
}
