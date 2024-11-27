package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public final class DemuxResource extends com.android.server.tv.tunerresourcemanager.TunerResourceBasic {
    private final int mFilterTypes;

    private DemuxResource(com.android.server.tv.tunerresourcemanager.DemuxResource.Builder builder) {
        super(builder);
        this.mFilterTypes = builder.mFilterTypes;
    }

    public int getFilterTypes() {
        return this.mFilterTypes;
    }

    public java.lang.String toString() {
        return "DemuxResource[handle=" + this.mHandle + ", filterTypes=" + this.mFilterTypes + ", isInUse=" + this.mIsInUse + ", ownerClientId=" + this.mOwnerClientId + "]";
    }

    public boolean hasSufficientCaps(int i) {
        return i == (this.mFilterTypes & i);
    }

    public int getNumOfCaps() {
        int i = 1;
        int i2 = 0;
        for (int i3 = 0; i3 < 32; i3++) {
            if ((this.mFilterTypes & i) == i) {
                i2++;
            }
            i <<= 1;
        }
        return i2;
    }

    public static class Builder extends com.android.server.tv.tunerresourcemanager.TunerResourceBasic.Builder {
        private int mFilterTypes;

        Builder(int i) {
            super(i);
        }

        public com.android.server.tv.tunerresourcemanager.DemuxResource.Builder filterTypes(int i) {
            this.mFilterTypes = i;
            return this;
        }

        @Override // com.android.server.tv.tunerresourcemanager.TunerResourceBasic.Builder
        public com.android.server.tv.tunerresourcemanager.DemuxResource build() {
            return new com.android.server.tv.tunerresourcemanager.DemuxResource(this);
        }
    }
}
