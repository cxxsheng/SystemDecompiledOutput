package com.android.server.pm;

/* compiled from: SELinuxMMAC.java */
/* loaded from: classes2.dex */
final class Policy {
    private final java.util.Set<android.content.pm.Signature> mCerts;
    private final java.util.Map<java.lang.String, java.lang.String> mPkgMap;
    private final java.lang.String mSeinfo;

    private Policy(com.android.server.pm.Policy.PolicyBuilder policyBuilder) {
        this.mSeinfo = policyBuilder.mSeinfo;
        this.mCerts = java.util.Collections.unmodifiableSet(policyBuilder.mCerts);
        this.mPkgMap = java.util.Collections.unmodifiableMap(policyBuilder.mPkgMap);
    }

    public java.util.Set<android.content.pm.Signature> getSignatures() {
        return this.mCerts;
    }

    public boolean hasInnerPackages() {
        return !this.mPkgMap.isEmpty();
    }

    public java.util.Map<java.lang.String, java.lang.String> getInnerPackages() {
        return this.mPkgMap;
    }

    public boolean hasGlobalSeinfo() {
        return this.mSeinfo != null;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.util.Iterator<android.content.pm.Signature> it = this.mCerts.iterator();
        while (it.hasNext()) {
            sb.append("cert=" + it.next().toCharsString().substring(0, 11) + "... ");
        }
        if (this.mSeinfo != null) {
            sb.append("seinfo=" + this.mSeinfo);
        }
        for (java.lang.String str : this.mPkgMap.keySet()) {
            sb.append(" " + str + "=" + this.mPkgMap.get(str));
        }
        return sb.toString();
    }

    public java.lang.String getMatchedSeInfo(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.content.pm.Signature[] signatureArr = (android.content.pm.Signature[]) this.mCerts.toArray(new android.content.pm.Signature[0]);
        if (androidPackage.getSigningDetails() != android.content.pm.SigningDetails.UNKNOWN && !android.content.pm.Signature.areExactMatch(androidPackage.getSigningDetails(), signatureArr) && (signatureArr.length > 1 || !androidPackage.getSigningDetails().hasCertificate(signatureArr[0]))) {
            return null;
        }
        java.lang.String str = this.mPkgMap.get(androidPackage.getPackageName());
        if (str != null) {
            return str;
        }
        return this.mSeinfo;
    }

    /* compiled from: SELinuxMMAC.java */
    public static final class PolicyBuilder {
        private final java.util.Set<android.content.pm.Signature> mCerts = new java.util.HashSet(2);
        private final java.util.Map<java.lang.String, java.lang.String> mPkgMap = new java.util.HashMap(2);
        private java.lang.String mSeinfo;

        public com.android.server.pm.Policy.PolicyBuilder addSignature(java.lang.String str) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Invalid signature value " + str);
            }
            this.mCerts.add(new android.content.pm.Signature(str));
            return this;
        }

        public com.android.server.pm.Policy.PolicyBuilder setGlobalSeinfoOrThrow(java.lang.String str) {
            if (!validateValue(str)) {
                throw new java.lang.IllegalArgumentException("Invalid seinfo value " + str);
            }
            if (this.mSeinfo != null && !this.mSeinfo.equals(str)) {
                throw new java.lang.IllegalStateException("Duplicate seinfo tag found");
            }
            this.mSeinfo = str;
            return this;
        }

        public com.android.server.pm.Policy.PolicyBuilder addInnerPackageMapOrThrow(java.lang.String str, java.lang.String str2) {
            if (!validateValue(str)) {
                throw new java.lang.IllegalArgumentException("Invalid package name " + str);
            }
            if (!validateValue(str2)) {
                throw new java.lang.IllegalArgumentException("Invalid seinfo value " + str2);
            }
            java.lang.String str3 = this.mPkgMap.get(str);
            if (str3 != null && !str3.equals(str2)) {
                throw new java.lang.IllegalStateException("Conflicting seinfo value found");
            }
            this.mPkgMap.put(str, str2);
            return this;
        }

        private boolean validateValue(java.lang.String str) {
            if (str == null || !str.matches("\\A[\\.\\w]+\\z")) {
                return false;
            }
            return true;
        }

        public com.android.server.pm.Policy build() {
            com.android.server.pm.Policy policy = new com.android.server.pm.Policy(this);
            if (policy.mCerts.isEmpty()) {
                throw new java.lang.IllegalStateException("Missing certs with signer tag. Expecting at least one.");
            }
            if (!((policy.mSeinfo == null) ^ policy.mPkgMap.isEmpty())) {
                throw new java.lang.IllegalStateException("Only seinfo tag XOR package tags are allowed within a signer stanza.");
            }
            return policy;
        }
    }
}
