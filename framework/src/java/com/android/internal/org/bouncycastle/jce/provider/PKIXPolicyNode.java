package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class PKIXPolicyNode implements java.security.cert.PolicyNode {
    protected java.util.List children;
    protected boolean critical;
    protected int depth;
    protected java.util.Set expectedPolicies;
    protected java.security.cert.PolicyNode parent;
    protected java.util.Set policyQualifiers;
    protected java.lang.String validPolicy;

    public PKIXPolicyNode(java.util.List list, int i, java.util.Set set, java.security.cert.PolicyNode policyNode, java.util.Set set2, java.lang.String str, boolean z) {
        this.children = list;
        this.depth = i;
        this.expectedPolicies = set;
        this.parent = policyNode;
        this.policyQualifiers = set2;
        this.validPolicy = str;
        this.critical = z;
    }

    public void addChild(com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode) {
        this.children.add(pKIXPolicyNode);
        pKIXPolicyNode.setParent(this);
    }

    @Override // java.security.cert.PolicyNode
    public java.util.Iterator getChildren() {
        return this.children.iterator();
    }

    @Override // java.security.cert.PolicyNode
    public int getDepth() {
        return this.depth;
    }

    @Override // java.security.cert.PolicyNode
    public java.util.Set getExpectedPolicies() {
        return this.expectedPolicies;
    }

    @Override // java.security.cert.PolicyNode
    public java.security.cert.PolicyNode getParent() {
        return this.parent;
    }

    @Override // java.security.cert.PolicyNode
    public java.util.Set getPolicyQualifiers() {
        return this.policyQualifiers;
    }

    @Override // java.security.cert.PolicyNode
    public java.lang.String getValidPolicy() {
        return this.validPolicy;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    @Override // java.security.cert.PolicyNode
    public boolean isCritical() {
        return this.critical;
    }

    public void removeChild(com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode) {
        this.children.remove(pKIXPolicyNode);
    }

    public void setCritical(boolean z) {
        this.critical = z;
    }

    public void setParent(com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode) {
        this.parent = pKIXPolicyNode;
    }

    public java.lang.String toString() {
        return toString("");
    }

    public java.lang.String toString(java.lang.String str) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(this.validPolicy);
        stringBuffer.append(" {\n");
        for (int i = 0; i < this.children.size(); i++) {
            stringBuffer.append(((com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) this.children.get(i)).toString(str + "    "));
        }
        stringBuffer.append(str);
        stringBuffer.append("}\n");
        return stringBuffer.toString();
    }

    public java.lang.Object clone() {
        return copy();
    }

    public com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode copy() {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator it = this.expectedPolicies.iterator();
        while (it.hasNext()) {
            hashSet.add(new java.lang.String((java.lang.String) it.next()));
        }
        java.util.HashSet hashSet2 = new java.util.HashSet();
        java.util.Iterator it2 = this.policyQualifiers.iterator();
        while (it2.hasNext()) {
            hashSet2.add(new java.lang.String((java.lang.String) it2.next()));
        }
        com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode = new com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), this.depth, hashSet, null, hashSet2, new java.lang.String(this.validPolicy), this.critical);
        java.util.Iterator it3 = this.children.iterator();
        while (it3.hasNext()) {
            com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode copy = ((com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) it3.next()).copy();
            copy.setParent(pKIXPolicyNode);
            pKIXPolicyNode.addChild(copy);
        }
        return pKIXPolicyNode;
    }

    public void setExpectedPolicies(java.util.Set set) {
        this.expectedPolicies = set;
    }
}
