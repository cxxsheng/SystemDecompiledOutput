package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public final class ClientProfile {
    public static final int INVALID_GROUP_ID = -1;
    public static final int INVALID_RESOURCE_ID = -1;
    private int mGroupId;
    private final int mId;
    private boolean mIsPriorityOverwritten;
    private int mNiceValue;
    private int mPrimaryUsingFrontendHandle;
    private int mPriority;
    private final int mProcessId;
    private java.util.Set<java.lang.Integer> mShareFeClientIds;
    private java.lang.Integer mShareeFeClientId;
    private final java.lang.String mTvInputSessionId;
    private final int mUseCase;
    private int mUsingCasSystemId;
    private int mUsingCiCamId;
    private java.util.Set<java.lang.Integer> mUsingDemuxHandles;
    private java.util.Set<java.lang.Integer> mUsingFrontendHandles;
    private java.util.Set<java.lang.Integer> mUsingLnbHandles;

    private ClientProfile(com.android.server.tv.tunerresourcemanager.ClientProfile.Builder builder) {
        this.mGroupId = -1;
        this.mPrimaryUsingFrontendHandle = -1;
        this.mUsingFrontendHandles = new java.util.HashSet();
        this.mShareFeClientIds = new java.util.HashSet();
        this.mUsingDemuxHandles = new java.util.HashSet();
        this.mShareeFeClientId = -1;
        this.mUsingLnbHandles = new java.util.HashSet();
        this.mUsingCasSystemId = -1;
        this.mUsingCiCamId = -1;
        this.mIsPriorityOverwritten = false;
        this.mId = builder.mId;
        this.mTvInputSessionId = builder.mTvInputSessionId;
        this.mUseCase = builder.mUseCase;
        this.mProcessId = builder.mProcessId;
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String getTvInputSessionId() {
        return this.mTvInputSessionId;
    }

    public int getUseCase() {
        return this.mUseCase;
    }

    public int getProcessId() {
        return this.mProcessId;
    }

    public boolean isPriorityOverwritten() {
        return this.mIsPriorityOverwritten;
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    public int getPriority() {
        return this.mPriority - this.mNiceValue;
    }

    public void setGroupId(int i) {
        this.mGroupId = i;
    }

    public void setPriority(int i) {
        if (i < 0) {
            return;
        }
        this.mPriority = i;
    }

    public void overwritePriority(int i) {
        if (i < 0) {
            return;
        }
        this.mIsPriorityOverwritten = true;
        this.mPriority = i;
    }

    public void setNiceValue(int i) {
        this.mNiceValue = i;
    }

    public void useFrontend(int i) {
        this.mUsingFrontendHandles.add(java.lang.Integer.valueOf(i));
    }

    public void setPrimaryFrontend(int i) {
        this.mPrimaryUsingFrontendHandle = i;
    }

    public int getPrimaryFrontend() {
        return this.mPrimaryUsingFrontendHandle;
    }

    public void shareFrontend(int i) {
        this.mShareFeClientIds.add(java.lang.Integer.valueOf(i));
    }

    public void stopSharingFrontend(int i) {
        this.mShareFeClientIds.remove(java.lang.Integer.valueOf(i));
    }

    public java.util.Set<java.lang.Integer> getInUseFrontendHandles() {
        return this.mUsingFrontendHandles;
    }

    public java.util.Set<java.lang.Integer> getShareFeClientIds() {
        return this.mShareFeClientIds;
    }

    public java.lang.Integer getShareeFeClientId() {
        return this.mShareeFeClientId;
    }

    public void setShareeFeClientId(java.lang.Integer num) {
        this.mShareeFeClientId = num;
    }

    public void releaseFrontend() {
        this.mUsingFrontendHandles.clear();
        this.mShareFeClientIds.clear();
        this.mShareeFeClientId = -1;
        this.mPrimaryUsingFrontendHandle = -1;
    }

    public void useDemux(int i) {
        this.mUsingDemuxHandles.add(java.lang.Integer.valueOf(i));
    }

    public java.util.Set<java.lang.Integer> getInUseDemuxHandles() {
        return this.mUsingDemuxHandles;
    }

    public void releaseDemux(int i) {
        this.mUsingDemuxHandles.remove(java.lang.Integer.valueOf(i));
    }

    public void useLnb(int i) {
        this.mUsingLnbHandles.add(java.lang.Integer.valueOf(i));
    }

    public java.util.Set<java.lang.Integer> getInUseLnbHandles() {
        return this.mUsingLnbHandles;
    }

    public void releaseLnb(int i) {
        this.mUsingLnbHandles.remove(java.lang.Integer.valueOf(i));
    }

    public void useCas(int i) {
        this.mUsingCasSystemId = i;
    }

    public int getInUseCasSystemId() {
        return this.mUsingCasSystemId;
    }

    public void releaseCas() {
        this.mUsingCasSystemId = -1;
    }

    public void useCiCam(int i) {
        this.mUsingCiCamId = i;
    }

    public int getInUseCiCamId() {
        return this.mUsingCiCamId;
    }

    public void releaseCiCam() {
        this.mUsingCiCamId = -1;
    }

    public void reclaimAllResources() {
        this.mUsingFrontendHandles.clear();
        this.mShareFeClientIds.clear();
        this.mPrimaryUsingFrontendHandle = -1;
        this.mUsingLnbHandles.clear();
        this.mUsingCasSystemId = -1;
        this.mUsingCiCamId = -1;
    }

    public java.lang.String toString() {
        return "ClientProfile[id=" + this.mId + ", tvInputSessionId=" + this.mTvInputSessionId + ", useCase=" + this.mUseCase + ", processId=" + this.mProcessId + "]";
    }

    public static class Builder {
        private final int mId;
        private int mProcessId;
        private java.lang.String mTvInputSessionId;
        private int mUseCase;

        Builder(int i) {
            this.mId = i;
        }

        public com.android.server.tv.tunerresourcemanager.ClientProfile.Builder useCase(int i) {
            this.mUseCase = i;
            return this;
        }

        public com.android.server.tv.tunerresourcemanager.ClientProfile.Builder tvInputSessionId(java.lang.String str) {
            this.mTvInputSessionId = str;
            return this;
        }

        public com.android.server.tv.tunerresourcemanager.ClientProfile.Builder processId(int i) {
            this.mProcessId = i;
            return this;
        }

        public com.android.server.tv.tunerresourcemanager.ClientProfile build() {
            return new com.android.server.tv.tunerresourcemanager.ClientProfile(this);
        }
    }
}
