package com.android.server.integrity.serializer;

/* loaded from: classes2.dex */
class RuleIndexingDetailsIdentifier {
    RuleIndexingDetailsIdentifier() {
    }

    public static java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, java.util.List<android.content.integrity.Rule>>> splitRulesIntoIndexBuckets(java.util.List<android.content.integrity.Rule> list) {
        if (list == null) {
            throw new java.lang.IllegalArgumentException("Index buckets cannot be created for null rule list.");
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put(0, new java.util.HashMap());
        hashMap.put(1, new java.util.HashMap());
        hashMap.put(2, new java.util.HashMap());
        for (android.content.integrity.Rule rule : list) {
            try {
                com.android.server.integrity.serializer.RuleIndexingDetails indexingDetails = getIndexingDetails(rule.getFormula());
                int indexType = indexingDetails.getIndexType();
                java.lang.String ruleKey = indexingDetails.getRuleKey();
                if (!((java.util.Map) hashMap.get(java.lang.Integer.valueOf(indexType))).containsKey(ruleKey)) {
                    ((java.util.Map) hashMap.get(java.lang.Integer.valueOf(indexType))).put(ruleKey, new java.util.ArrayList());
                }
                ((java.util.List) ((java.util.Map) hashMap.get(java.lang.Integer.valueOf(indexType))).get(ruleKey)).add(rule);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException(java.lang.String.format("Malformed rule identified. [%s]", rule.toString()));
            }
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.server.integrity.serializer.RuleIndexingDetails getIndexingDetails(android.content.integrity.IntegrityFormula integrityFormula) {
        switch (integrityFormula.getTag()) {
            case 0:
                return getIndexingDetailsForCompoundFormula((android.content.integrity.CompoundFormula) integrityFormula);
            case 1:
                return getIndexingDetailsForStringAtomicFormula((android.content.integrity.AtomicFormula.StringAtomicFormula) integrityFormula);
            case 2:
            case 3:
            case 4:
                return new com.android.server.integrity.serializer.RuleIndexingDetails(0);
            default:
                throw new java.lang.IllegalArgumentException(java.lang.String.format("Invalid formula tag type: %s", java.lang.Integer.valueOf(integrityFormula.getTag())));
        }
    }

    private static com.android.server.integrity.serializer.RuleIndexingDetails getIndexingDetailsForCompoundFormula(android.content.integrity.CompoundFormula compoundFormula) {
        int connector = compoundFormula.getConnector();
        java.util.List formulas = compoundFormula.getFormulas();
        switch (connector) {
            case 0:
            case 1:
                java.util.Optional findAny = formulas.stream().map(new java.util.function.Function() { // from class: com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        com.android.server.integrity.serializer.RuleIndexingDetails indexingDetails;
                        indexingDetails = com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier.getIndexingDetails((android.content.integrity.IntegrityFormula) obj);
                        return indexingDetails;
                    }
                }).filter(new java.util.function.Predicate() { // from class: com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$getIndexingDetailsForCompoundFormula$1;
                        lambda$getIndexingDetailsForCompoundFormula$1 = com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier.lambda$getIndexingDetailsForCompoundFormula$1((com.android.server.integrity.serializer.RuleIndexingDetails) obj);
                        return lambda$getIndexingDetailsForCompoundFormula$1;
                    }
                }).findAny();
                if (findAny.isPresent()) {
                    return (com.android.server.integrity.serializer.RuleIndexingDetails) findAny.get();
                }
                java.util.Optional findAny2 = formulas.stream().map(new java.util.function.Function() { // from class: com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        com.android.server.integrity.serializer.RuleIndexingDetails indexingDetails;
                        indexingDetails = com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier.getIndexingDetails((android.content.integrity.IntegrityFormula) obj);
                        return indexingDetails;
                    }
                }).filter(new java.util.function.Predicate() { // from class: com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$getIndexingDetailsForCompoundFormula$3;
                        lambda$getIndexingDetailsForCompoundFormula$3 = com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier.lambda$getIndexingDetailsForCompoundFormula$3((com.android.server.integrity.serializer.RuleIndexingDetails) obj);
                        return lambda$getIndexingDetailsForCompoundFormula$3;
                    }
                }).findAny();
                if (findAny2.isPresent()) {
                    return (com.android.server.integrity.serializer.RuleIndexingDetails) findAny2.get();
                }
                return new com.android.server.integrity.serializer.RuleIndexingDetails(0);
            default:
                return new com.android.server.integrity.serializer.RuleIndexingDetails(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getIndexingDetailsForCompoundFormula$1(com.android.server.integrity.serializer.RuleIndexingDetails ruleIndexingDetails) {
        return ruleIndexingDetails.getIndexType() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getIndexingDetailsForCompoundFormula$3(com.android.server.integrity.serializer.RuleIndexingDetails ruleIndexingDetails) {
        return ruleIndexingDetails.getIndexType() == 2;
    }

    private static com.android.server.integrity.serializer.RuleIndexingDetails getIndexingDetailsForStringAtomicFormula(android.content.integrity.AtomicFormula.StringAtomicFormula stringAtomicFormula) {
        switch (stringAtomicFormula.getKey()) {
            case 0:
                return new com.android.server.integrity.serializer.RuleIndexingDetails(1, stringAtomicFormula.getValue());
            case 1:
                return new com.android.server.integrity.serializer.RuleIndexingDetails(2, stringAtomicFormula.getValue());
            default:
                return new com.android.server.integrity.serializer.RuleIndexingDetails(0);
        }
    }
}
