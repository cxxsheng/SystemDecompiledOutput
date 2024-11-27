package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public final class LnbResource extends com.android.server.tv.tunerresourcemanager.TunerResourceBasic {
    private LnbResource(com.android.server.tv.tunerresourcemanager.LnbResource.Builder builder) {
        super(builder);
    }

    public java.lang.String toString() {
        return "LnbResource[handle=" + this.mHandle + ", isInUse=" + this.mIsInUse + ", ownerClientId=" + this.mOwnerClientId + "]";
    }

    public static class Builder extends com.android.server.tv.tunerresourcemanager.TunerResourceBasic.Builder {
        Builder(int i) {
            super(i);
        }

        @Override // com.android.server.tv.tunerresourcemanager.TunerResourceBasic.Builder
        public com.android.server.tv.tunerresourcemanager.LnbResource build() {
            return new com.android.server.tv.tunerresourcemanager.LnbResource(this);
        }
    }
}
