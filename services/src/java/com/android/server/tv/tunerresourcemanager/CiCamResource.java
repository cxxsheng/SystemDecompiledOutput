package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public final class CiCamResource extends com.android.server.tv.tunerresourcemanager.CasResource {
    private CiCamResource(com.android.server.tv.tunerresourcemanager.CiCamResource.Builder builder) {
        super(builder);
    }

    @Override // com.android.server.tv.tunerresourcemanager.CasResource
    public java.lang.String toString() {
        return "CiCamResource[systemId=" + getSystemId() + ", isFullyUsed=" + isFullyUsed() + ", maxSessionNum=" + getMaxSessionNum() + ", ownerClients=" + ownersMapToString() + "]";
    }

    public int getCiCamId() {
        return getSystemId();
    }

    public static class Builder extends com.android.server.tv.tunerresourcemanager.CasResource.Builder {
        Builder(int i) {
            super(i);
        }

        @Override // com.android.server.tv.tunerresourcemanager.CasResource.Builder
        public com.android.server.tv.tunerresourcemanager.CiCamResource.Builder maxSessionNum(int i) {
            this.mMaxSessionNum = i;
            return this;
        }

        @Override // com.android.server.tv.tunerresourcemanager.CasResource.Builder
        public com.android.server.tv.tunerresourcemanager.CiCamResource build() {
            return new com.android.server.tv.tunerresourcemanager.CiCamResource(this);
        }
    }
}
