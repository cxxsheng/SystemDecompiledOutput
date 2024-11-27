package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public class CasResource {
    private int mAvailableSessionNum;
    private int mMaxSessionNum;
    private java.util.Map<java.lang.Integer, java.lang.Integer> mOwnerClientIdsToSessionNum = new java.util.HashMap();
    private final int mSystemId;

    CasResource(com.android.server.tv.tunerresourcemanager.CasResource.Builder builder) {
        this.mSystemId = builder.mSystemId;
        this.mMaxSessionNum = builder.mMaxSessionNum;
        this.mAvailableSessionNum = builder.mMaxSessionNum;
    }

    public int getSystemId() {
        return this.mSystemId;
    }

    public int getMaxSessionNum() {
        return this.mMaxSessionNum;
    }

    public int getUsedSessionNum() {
        return this.mMaxSessionNum - this.mAvailableSessionNum;
    }

    public boolean isFullyUsed() {
        return this.mAvailableSessionNum == 0;
    }

    public void updateMaxSessionNum(int i) {
        this.mAvailableSessionNum = java.lang.Math.max(0, this.mAvailableSessionNum + (i - this.mMaxSessionNum));
        this.mMaxSessionNum = i;
    }

    public void setOwner(int i) {
        this.mOwnerClientIdsToSessionNum.put(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.mOwnerClientIdsToSessionNum.get(java.lang.Integer.valueOf(i)) == null ? 1 : this.mOwnerClientIdsToSessionNum.get(java.lang.Integer.valueOf(i)).intValue() + 1));
        this.mAvailableSessionNum--;
    }

    public void removeOwner(int i) {
        this.mAvailableSessionNum += this.mOwnerClientIdsToSessionNum.get(java.lang.Integer.valueOf(i)).intValue();
        this.mOwnerClientIdsToSessionNum.remove(java.lang.Integer.valueOf(i));
    }

    public java.util.Set<java.lang.Integer> getOwnerClientIds() {
        return this.mOwnerClientIdsToSessionNum.keySet();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("CasResource[systemId=");
        sb.append(this.mSystemId);
        sb.append(", isFullyUsed=");
        sb.append(this.mAvailableSessionNum == 0);
        sb.append(", maxSessionNum=");
        sb.append(this.mMaxSessionNum);
        sb.append(", ownerClients=");
        sb.append(ownersMapToString());
        sb.append("]");
        return sb.toString();
    }

    public static class Builder {
        protected int mMaxSessionNum;
        private int mSystemId;

        Builder(int i) {
            this.mSystemId = i;
        }

        public com.android.server.tv.tunerresourcemanager.CasResource.Builder maxSessionNum(int i) {
            this.mMaxSessionNum = i;
            return this;
        }

        public com.android.server.tv.tunerresourcemanager.CasResource build() {
            return new com.android.server.tv.tunerresourcemanager.CasResource(this);
        }
    }

    protected java.lang.String ownersMapToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("{");
        java.util.Iterator<java.lang.Integer> it = this.mOwnerClientIdsToSessionNum.keySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            sb.append(" clientId=");
            sb.append(intValue);
            sb.append(", owns session num=");
            sb.append(this.mOwnerClientIdsToSessionNum.get(java.lang.Integer.valueOf(intValue)));
            sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }
}
