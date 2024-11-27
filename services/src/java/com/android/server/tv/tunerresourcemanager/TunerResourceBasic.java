package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public class TunerResourceBasic {
    final int mHandle;
    boolean mIsInUse;
    int mOwnerClientId = -1;

    TunerResourceBasic(com.android.server.tv.tunerresourcemanager.TunerResourceBasic.Builder builder) {
        this.mHandle = builder.mHandle;
    }

    public int getHandle() {
        return this.mHandle;
    }

    public boolean isInUse() {
        return this.mIsInUse;
    }

    public int getOwnerClientId() {
        return this.mOwnerClientId;
    }

    public void setOwner(int i) {
        this.mIsInUse = true;
        this.mOwnerClientId = i;
    }

    public void removeOwner() {
        this.mIsInUse = false;
        this.mOwnerClientId = -1;
    }

    public static class Builder {
        private final int mHandle;

        Builder(int i) {
            this.mHandle = i;
        }

        public com.android.server.tv.tunerresourcemanager.TunerResourceBasic build() {
            return new com.android.server.tv.tunerresourcemanager.TunerResourceBasic(this);
        }
    }
}
