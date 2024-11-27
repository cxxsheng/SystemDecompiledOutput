package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public final class FrontendResource extends com.android.server.tv.tunerresourcemanager.TunerResourceBasic {
    private final int mExclusiveGroupId;
    private java.util.Set<java.lang.Integer> mExclusiveGroupMemberHandles;
    private final int mType;

    private FrontendResource(com.android.server.tv.tunerresourcemanager.FrontendResource.Builder builder) {
        super(builder);
        this.mExclusiveGroupMemberHandles = new java.util.HashSet();
        this.mType = builder.mType;
        this.mExclusiveGroupId = builder.mExclusiveGroupId;
    }

    public int getType() {
        return this.mType;
    }

    public int getExclusiveGroupId() {
        return this.mExclusiveGroupId;
    }

    public java.util.Set<java.lang.Integer> getExclusiveGroupMemberFeHandles() {
        return this.mExclusiveGroupMemberHandles;
    }

    public void addExclusiveGroupMemberFeHandle(int i) {
        this.mExclusiveGroupMemberHandles.add(java.lang.Integer.valueOf(i));
    }

    public void addExclusiveGroupMemberFeHandles(java.util.Collection<java.lang.Integer> collection) {
        this.mExclusiveGroupMemberHandles.addAll(collection);
    }

    public void removeExclusiveGroupMemberFeId(int i) {
        this.mExclusiveGroupMemberHandles.remove(java.lang.Integer.valueOf(i));
    }

    public java.lang.String toString() {
        return "FrontendResource[handle=" + this.mHandle + ", type=" + this.mType + ", exclusiveGId=" + this.mExclusiveGroupId + ", exclusiveGMemeberHandles=" + this.mExclusiveGroupMemberHandles + ", isInUse=" + this.mIsInUse + ", ownerClientId=" + this.mOwnerClientId + "]";
    }

    public static class Builder extends com.android.server.tv.tunerresourcemanager.TunerResourceBasic.Builder {
        private int mExclusiveGroupId;
        private int mType;

        Builder(int i) {
            super(i);
        }

        public com.android.server.tv.tunerresourcemanager.FrontendResource.Builder type(int i) {
            this.mType = i;
            return this;
        }

        public com.android.server.tv.tunerresourcemanager.FrontendResource.Builder exclusiveGroupId(int i) {
            this.mExclusiveGroupId = i;
            return this;
        }

        @Override // com.android.server.tv.tunerresourcemanager.TunerResourceBasic.Builder
        public com.android.server.tv.tunerresourcemanager.FrontendResource build() {
            return new com.android.server.tv.tunerresourcemanager.FrontendResource(this);
        }
    }
}
