package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class PolicyState<V> {
    private static final java.lang.String TAG = "PolicyState";
    private static final java.lang.String TAG_ADMIN_POLICY_ENTRY = "admin-policy-entry";
    private static final java.lang.String TAG_ENFORCING_ADMIN_ENTRY = "enforcing-admin-entry";
    private static final java.lang.String TAG_POLICY_DEFINITION_ENTRY = "policy-definition-entry";
    private static final java.lang.String TAG_POLICY_VALUE_ENTRY = "policy-value-entry";
    private static final java.lang.String TAG_RESOLVED_VALUE_ENTRY = "resolved-value-entry";
    private android.app.admin.PolicyValue<V> mCurrentResolvedPolicy;
    private final java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> mPoliciesSetByAdmins = new java.util.LinkedHashMap<>();
    private final com.android.server.devicepolicy.PolicyDefinition<V> mPolicyDefinition;

    PolicyState(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition) {
        java.util.Objects.requireNonNull(policyDefinition);
        this.mPolicyDefinition = policyDefinition;
    }

    private PolicyState(@android.annotation.NonNull com.android.server.devicepolicy.PolicyDefinition<V> policyDefinition, @android.annotation.NonNull java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap, android.app.admin.PolicyValue<V> policyValue) {
        java.util.Objects.requireNonNull(policyDefinition);
        java.util.Objects.requireNonNull(linkedHashMap);
        this.mPolicyDefinition = policyDefinition;
        this.mPoliciesSetByAdmins.putAll(linkedHashMap);
        this.mCurrentResolvedPolicy = policyValue;
    }

    boolean addPolicy(@android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, @android.annotation.Nullable android.app.admin.PolicyValue<V> policyValue) {
        java.util.Objects.requireNonNull(enforcingAdmin);
        this.mPoliciesSetByAdmins.remove(enforcingAdmin);
        this.mPoliciesSetByAdmins.put(enforcingAdmin, policyValue);
        return resolvePolicy();
    }

    boolean addPolicy(@android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, @android.annotation.Nullable android.app.admin.PolicyValue<V> policyValue, java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap) {
        java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap2 = this.mPoliciesSetByAdmins;
        java.util.Objects.requireNonNull(enforcingAdmin);
        linkedHashMap2.put(enforcingAdmin, policyValue);
        return resolvePolicy(linkedHashMap);
    }

    boolean removePolicy(@android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin) {
        java.util.Objects.requireNonNull(enforcingAdmin);
        if (this.mPoliciesSetByAdmins.remove(enforcingAdmin) == null) {
            return false;
        }
        return resolvePolicy();
    }

    boolean removePolicy(@android.annotation.NonNull com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap) {
        java.util.Objects.requireNonNull(enforcingAdmin);
        if (this.mPoliciesSetByAdmins.remove(enforcingAdmin) == null) {
            return false;
        }
        return resolvePolicy(linkedHashMap);
    }

    boolean resolvePolicy(java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap) {
        if (this.mPolicyDefinition.isNonCoexistablePolicy()) {
            return false;
        }
        java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap2 = new java.util.LinkedHashMap<>(linkedHashMap);
        linkedHashMap2.putAll(this.mPoliciesSetByAdmins);
        android.app.admin.PolicyValue<V> resolvePolicy = this.mPolicyDefinition.resolvePolicy(linkedHashMap2);
        boolean z = !java.util.Objects.equals(resolvePolicy, this.mCurrentResolvedPolicy);
        this.mCurrentResolvedPolicy = resolvePolicy;
        return z;
    }

    @android.annotation.NonNull
    java.util.LinkedHashMap<com.android.server.devicepolicy.EnforcingAdmin, android.app.admin.PolicyValue<V>> getPoliciesSetByAdmins() {
        return new java.util.LinkedHashMap<>(this.mPoliciesSetByAdmins);
    }

    private boolean resolvePolicy() {
        if (this.mPolicyDefinition.isNonCoexistablePolicy()) {
            return false;
        }
        android.app.admin.PolicyValue<V> resolvePolicy = this.mPolicyDefinition.resolvePolicy(this.mPoliciesSetByAdmins);
        boolean z = !java.util.Objects.equals(resolvePolicy, this.mCurrentResolvedPolicy);
        this.mCurrentResolvedPolicy = resolvePolicy;
        return z;
    }

    @android.annotation.Nullable
    android.app.admin.PolicyValue<V> getCurrentResolvedPolicy() {
        return this.mCurrentResolvedPolicy;
    }

    android.app.admin.PolicyState<V> getParcelablePolicyState() {
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
            linkedHashMap.put(enforcingAdmin.getParcelableAdmin(), this.mPoliciesSetByAdmins.get(enforcingAdmin));
        }
        return new android.app.admin.PolicyState<>(linkedHashMap, this.mCurrentResolvedPolicy, this.mPolicyDefinition.getResolutionMechanism().mo3262getParcelableResolutionMechanism());
    }

    public java.lang.String toString() {
        return "\nPolicyKey - " + this.mPolicyDefinition.getPolicyKey() + "\nmPolicyDefinition= \n\t" + this.mPolicyDefinition + "\nmPoliciesSetByAdmins= \n\t" + this.mPoliciesSetByAdmins + ",\nmCurrentResolvedPolicy= \n\t" + this.mCurrentResolvedPolicy + " }";
    }

    /* JADX WARN: Multi-variable type inference failed */
    void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, TAG_POLICY_DEFINITION_ENTRY);
        this.mPolicyDefinition.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((java.lang.String) null, TAG_POLICY_DEFINITION_ENTRY);
        if (this.mCurrentResolvedPolicy != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_RESOLVED_VALUE_ENTRY);
            this.mPolicyDefinition.savePolicyValueToXml(typedXmlSerializer, this.mCurrentResolvedPolicy.getValue());
            typedXmlSerializer.endTag((java.lang.String) null, TAG_RESOLVED_VALUE_ENTRY);
        }
        for (com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_ADMIN_POLICY_ENTRY);
            if (this.mPoliciesSetByAdmins.get(enforcingAdmin) != null) {
                typedXmlSerializer.startTag((java.lang.String) null, TAG_POLICY_VALUE_ENTRY);
                this.mPolicyDefinition.savePolicyValueToXml(typedXmlSerializer, this.mPoliciesSetByAdmins.get(enforcingAdmin).getValue());
                typedXmlSerializer.endTag((java.lang.String) null, TAG_POLICY_VALUE_ENTRY);
            }
            typedXmlSerializer.startTag((java.lang.String) null, TAG_ENFORCING_ADMIN_ENTRY);
            enforcingAdmin.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_ENFORCING_ADMIN_ENTRY);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_ADMIN_POLICY_ENTRY);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @android.annotation.Nullable
    static <V> com.android.server.devicepolicy.PolicyState<V> readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        java.lang.String str;
        boolean z;
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        int depth = typedXmlPullParser.getDepth();
        com.android.server.devicepolicy.PolicyDefinition policyDefinition = null;
        android.app.admin.PolicyValue<V> policyValue = null;
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            java.lang.String name = typedXmlPullParser.getName();
            switch (name.hashCode()) {
                case 394982067:
                    if (name.equals(TAG_POLICY_DEFINITION_ENTRY)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 695389653:
                    if (name.equals(TAG_ADMIN_POLICY_ENTRY)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 829992641:
                    if (name.equals(TAG_RESOLVED_VALUE_ENTRY)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    int depth2 = typedXmlPullParser.getDepth();
                    java.lang.Object obj = null;
                    android.app.admin.PolicyValue<V> policyValue2 = null;
                    while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                        java.lang.String name2 = typedXmlPullParser.getName();
                        switch (name2.hashCode()) {
                            case -1365069882:
                                if (name2.equals(TAG_ENFORCING_ADMIN_ENTRY)) {
                                    z = false;
                                    break;
                                }
                                z = -1;
                                break;
                            case -698838789:
                                if (name2.equals(TAG_POLICY_VALUE_ENTRY)) {
                                    z = true;
                                    break;
                                }
                                z = -1;
                                break;
                            default:
                                z = -1;
                                break;
                        }
                        switch (z) {
                            case false:
                                obj = com.android.server.devicepolicy.EnforcingAdmin.readFromXml(typedXmlPullParser);
                                if (obj != null) {
                                    break;
                                } else {
                                    com.android.server.utils.Slogf.wtf(TAG, "Error Parsing TAG_ENFORCING_ADMIN_ENTRY, EnforcingAdmin is null");
                                    break;
                                }
                            case true:
                                policyValue2 = policyDefinition.readPolicyValueFromXml(typedXmlPullParser);
                                if (policyValue2 != null) {
                                    break;
                                } else {
                                    com.android.server.utils.Slogf.wtf(TAG, "Error Parsing TAG_POLICY_VALUE_ENTRY, PolicyValue is null");
                                    break;
                                }
                        }
                    }
                    if (obj != null && policyValue2 != null) {
                        linkedHashMap.put(obj, policyValue2);
                        break;
                    } else {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("Error Parsing TAG_ADMIN_POLICY_ENTRY for ");
                        if (policyDefinition == null) {
                            str = "unknown policy";
                        } else {
                            str = "policy with definition " + policyDefinition;
                        }
                        sb.append(str);
                        sb.append(", EnforcingAdmin is: ");
                        if (obj == null) {
                            obj = "null";
                        }
                        sb.append(obj);
                        sb.append(", value is : ");
                        if (policyValue2 == null) {
                            policyValue2 = "null";
                        }
                        sb.append(policyValue2);
                        com.android.server.utils.Slogf.wtf(TAG, sb.toString());
                        break;
                    }
                case 1:
                    policyDefinition = com.android.server.devicepolicy.PolicyDefinition.readFromXml(typedXmlPullParser);
                    if (policyDefinition != null) {
                        break;
                    } else {
                        com.android.server.utils.Slogf.wtf(TAG, "Error Parsing TAG_POLICY_DEFINITION_ENTRY, PolicyDefinition is null");
                        break;
                    }
                case 2:
                    if (policyDefinition == null) {
                        com.android.server.utils.Slogf.wtf(TAG, "Error Parsing TAG_RESOLVED_VALUE_ENTRY, policyDefinition is null");
                        break;
                    } else {
                        policyValue = policyDefinition.readPolicyValueFromXml(typedXmlPullParser);
                        if (policyValue != null) {
                            break;
                        } else {
                            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                            sb2.append("Error Parsing TAG_RESOLVED_VALUE_ENTRY for ");
                            sb2.append("policy with definition " + policyDefinition);
                            sb2.append(", currentResolvedPolicy is null");
                            com.android.server.utils.Slogf.wtf(TAG, sb2.toString());
                            break;
                        }
                    }
                default:
                    com.android.server.utils.Slogf.wtf(TAG, "Unknown tag: " + name);
                    break;
            }
        }
        if (policyDefinition != null) {
            return new com.android.server.devicepolicy.PolicyState<>(policyDefinition, linkedHashMap, policyValue);
        }
        com.android.server.utils.Slogf.wtf(TAG, "Error parsing policyState, policyDefinition is null");
        return null;
    }

    com.android.server.devicepolicy.PolicyDefinition<V> getPolicyDefinition() {
        return this.mPolicyDefinition;
    }
}
