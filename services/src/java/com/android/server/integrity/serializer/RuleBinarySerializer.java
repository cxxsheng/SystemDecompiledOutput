package com.android.server.integrity.serializer;

/* loaded from: classes2.dex */
public class RuleBinarySerializer implements com.android.server.integrity.serializer.RuleSerializer {
    static final int INDEXED_RULE_SIZE_LIMIT = 100000;
    static final int NONINDEXED_RULE_SIZE_LIMIT = 1000;
    static final int TOTAL_RULE_SIZE_LIMIT = 200000;

    @Override // com.android.server.integrity.serializer.RuleSerializer
    public byte[] serialize(java.util.List<android.content.integrity.Rule> list, java.util.Optional<java.lang.Integer> optional) throws com.android.server.integrity.serializer.RuleSerializeException {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            serialize(list, optional, byteArrayOutputStream, new java.io.ByteArrayOutputStream());
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.Exception e) {
            throw new com.android.server.integrity.serializer.RuleSerializeException(e.getMessage(), e);
        }
    }

    @Override // com.android.server.integrity.serializer.RuleSerializer
    public void serialize(java.util.List<android.content.integrity.Rule> list, java.util.Optional<java.lang.Integer> optional, java.io.OutputStream outputStream, java.io.OutputStream outputStream2) throws com.android.server.integrity.serializer.RuleSerializeException {
        try {
            if (list == null) {
                throw new java.lang.IllegalArgumentException("Null rules cannot be serialized.");
            }
            if (list.size() > TOTAL_RULE_SIZE_LIMIT) {
                throw new java.lang.IllegalArgumentException("Too many rules provided: " + list.size());
            }
            java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, java.util.List<android.content.integrity.Rule>>> splitRulesIntoIndexBuckets = com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier.splitRulesIntoIndexBuckets(list);
            verifySize(splitRulesIntoIndexBuckets.get(1), INDEXED_RULE_SIZE_LIMIT);
            verifySize(splitRulesIntoIndexBuckets.get(2), INDEXED_RULE_SIZE_LIMIT);
            verifySize(splitRulesIntoIndexBuckets.get(0), 1000);
            com.android.server.integrity.model.ByteTrackedOutputStream byteTrackedOutputStream = new com.android.server.integrity.model.ByteTrackedOutputStream(outputStream);
            serializeRuleFileMetadata(optional, byteTrackedOutputStream);
            java.util.LinkedHashMap<java.lang.String, java.lang.Integer> serializeRuleList = serializeRuleList(splitRulesIntoIndexBuckets.get(1), byteTrackedOutputStream);
            java.util.LinkedHashMap<java.lang.String, java.lang.Integer> serializeRuleList2 = serializeRuleList(splitRulesIntoIndexBuckets.get(2), byteTrackedOutputStream);
            java.util.LinkedHashMap<java.lang.String, java.lang.Integer> serializeRuleList3 = serializeRuleList(splitRulesIntoIndexBuckets.get(0), byteTrackedOutputStream);
            com.android.server.integrity.model.BitOutputStream bitOutputStream = new com.android.server.integrity.model.BitOutputStream(outputStream2);
            serializeIndexGroup(serializeRuleList, bitOutputStream, true);
            serializeIndexGroup(serializeRuleList2, bitOutputStream, true);
            serializeIndexGroup(serializeRuleList3, bitOutputStream, false);
            bitOutputStream.flush();
        } catch (java.lang.Exception e) {
            throw new com.android.server.integrity.serializer.RuleSerializeException(e.getMessage(), e);
        }
    }

    private void verifySize(java.util.Map<java.lang.String, java.util.List<android.content.integrity.Rule>> map, int i) {
        int intValue = ((java.lang.Integer) map.values().stream().map(new java.util.function.Function() { // from class: com.android.server.integrity.serializer.RuleBinarySerializer$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$verifySize$0;
                lambda$verifySize$0 = com.android.server.integrity.serializer.RuleBinarySerializer.lambda$verifySize$0((java.util.List) obj);
                return lambda$verifySize$0;
            }
        }).collect(java.util.stream.Collectors.summingInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()))).intValue();
        if (intValue > i) {
            throw new java.lang.IllegalArgumentException("Too many rules provided in the indexing group. Provided " + intValue + " limit " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$verifySize$0(java.util.List list) {
        return java.lang.Integer.valueOf(list.size());
    }

    private void serializeRuleFileMetadata(java.util.Optional<java.lang.Integer> optional, com.android.server.integrity.model.ByteTrackedOutputStream byteTrackedOutputStream) throws java.io.IOException {
        int intValue = optional.orElse(1).intValue();
        com.android.server.integrity.model.BitOutputStream bitOutputStream = new com.android.server.integrity.model.BitOutputStream(byteTrackedOutputStream);
        bitOutputStream.setNext(8, intValue);
        bitOutputStream.flush();
    }

    private java.util.LinkedHashMap<java.lang.String, java.lang.Integer> serializeRuleList(java.util.Map<java.lang.String, java.util.List<android.content.integrity.Rule>> map, com.android.server.integrity.model.ByteTrackedOutputStream byteTrackedOutputStream) throws java.io.IOException {
        com.android.internal.util.Preconditions.checkArgument(map != null, "serializeRuleList should never be called with null rule list.");
        com.android.server.integrity.model.BitOutputStream bitOutputStream = new com.android.server.integrity.model.BitOutputStream(byteTrackedOutputStream);
        java.util.LinkedHashMap<java.lang.String, java.lang.Integer> linkedHashMap = new java.util.LinkedHashMap<>();
        linkedHashMap.put(com.android.server.integrity.model.IndexingFileConstants.START_INDEXING_KEY, java.lang.Integer.valueOf(byteTrackedOutputStream.getWrittenBytesCount()));
        int i = 0;
        for (java.lang.String str : (java.util.List) map.keySet().stream().sorted().collect(java.util.stream.Collectors.toList())) {
            if (i >= 50) {
                linkedHashMap.put(str, java.lang.Integer.valueOf(byteTrackedOutputStream.getWrittenBytesCount()));
                i = 0;
            }
            java.util.Iterator<android.content.integrity.Rule> it = map.get(str).iterator();
            while (it.hasNext()) {
                serializeRule(it.next(), bitOutputStream);
                bitOutputStream.flush();
                i++;
            }
        }
        linkedHashMap.put(com.android.server.integrity.model.IndexingFileConstants.END_INDEXING_KEY, java.lang.Integer.valueOf(byteTrackedOutputStream.getWrittenBytesCount()));
        return linkedHashMap;
    }

    private void serializeRule(android.content.integrity.Rule rule, com.android.server.integrity.model.BitOutputStream bitOutputStream) throws java.io.IOException {
        if (rule == null) {
            throw new java.lang.IllegalArgumentException("Null rule can not be serialized");
        }
        bitOutputStream.setNext();
        serializeFormula(rule.getFormula(), bitOutputStream);
        bitOutputStream.setNext(3, rule.getEffect());
        bitOutputStream.setNext();
    }

    private void serializeFormula(android.content.integrity.IntegrityFormula integrityFormula, com.android.server.integrity.model.BitOutputStream bitOutputStream) throws java.io.IOException {
        if (integrityFormula instanceof android.content.integrity.AtomicFormula) {
            serializeAtomicFormula((android.content.integrity.AtomicFormula) integrityFormula, bitOutputStream);
        } else if (integrityFormula instanceof android.content.integrity.CompoundFormula) {
            serializeCompoundFormula((android.content.integrity.CompoundFormula) integrityFormula, bitOutputStream);
        } else {
            if (integrityFormula instanceof android.content.integrity.InstallerAllowedByManifestFormula) {
                bitOutputStream.setNext(3, 3);
                return;
            }
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Invalid formula type: %s", integrityFormula.getClass()));
        }
    }

    private void serializeCompoundFormula(android.content.integrity.CompoundFormula compoundFormula, com.android.server.integrity.model.BitOutputStream bitOutputStream) throws java.io.IOException {
        if (compoundFormula == null) {
            throw new java.lang.IllegalArgumentException("Null compound formula can not be serialized");
        }
        bitOutputStream.setNext(3, 1);
        bitOutputStream.setNext(2, compoundFormula.getConnector());
        java.util.Iterator it = compoundFormula.getFormulas().iterator();
        while (it.hasNext()) {
            serializeFormula((android.content.integrity.IntegrityFormula) it.next(), bitOutputStream);
        }
        bitOutputStream.setNext(3, 2);
    }

    private void serializeAtomicFormula(android.content.integrity.AtomicFormula atomicFormula, com.android.server.integrity.model.BitOutputStream bitOutputStream) throws java.io.IOException {
        if (atomicFormula == null) {
            throw new java.lang.IllegalArgumentException("Null atomic formula can not be serialized");
        }
        bitOutputStream.setNext(3, 0);
        bitOutputStream.setNext(4, atomicFormula.getKey());
        if (atomicFormula.getTag() == 1) {
            android.content.integrity.AtomicFormula.StringAtomicFormula stringAtomicFormula = (android.content.integrity.AtomicFormula.StringAtomicFormula) atomicFormula;
            bitOutputStream.setNext(3, 0);
            serializeStringValue(stringAtomicFormula.getValue(), stringAtomicFormula.getIsHashedValue().booleanValue(), bitOutputStream);
        } else {
            if (atomicFormula.getTag() != 2) {
                if (atomicFormula.getTag() == 3) {
                    bitOutputStream.setNext(3, 0);
                    serializeBooleanValue(((android.content.integrity.AtomicFormula.BooleanAtomicFormula) atomicFormula).getValue().booleanValue(), bitOutputStream);
                    return;
                }
                throw new java.lang.IllegalArgumentException(java.lang.String.format("Invalid atomic formula type: %s", atomicFormula.getClass()));
            }
            android.content.integrity.AtomicFormula.LongAtomicFormula longAtomicFormula = (android.content.integrity.AtomicFormula.LongAtomicFormula) atomicFormula;
            bitOutputStream.setNext(3, longAtomicFormula.getOperator().intValue());
            long longValue = longAtomicFormula.getValue().longValue();
            serializeIntValue((int) (longValue >>> 32), bitOutputStream);
            serializeIntValue((int) longValue, bitOutputStream);
        }
    }

    private void serializeIndexGroup(java.util.LinkedHashMap<java.lang.String, java.lang.Integer> linkedHashMap, com.android.server.integrity.model.BitOutputStream bitOutputStream, boolean z) throws java.io.IOException {
        serializeStringValue(com.android.server.integrity.model.IndexingFileConstants.START_INDEXING_KEY, false, bitOutputStream);
        serializeIntValue(linkedHashMap.get(com.android.server.integrity.model.IndexingFileConstants.START_INDEXING_KEY).intValue(), bitOutputStream);
        if (z) {
            for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : linkedHashMap.entrySet()) {
                if (!entry.getKey().equals(com.android.server.integrity.model.IndexingFileConstants.START_INDEXING_KEY) && !entry.getKey().equals(com.android.server.integrity.model.IndexingFileConstants.END_INDEXING_KEY)) {
                    serializeStringValue(entry.getKey(), false, bitOutputStream);
                    serializeIntValue(entry.getValue().intValue(), bitOutputStream);
                }
            }
        }
        serializeStringValue(com.android.server.integrity.model.IndexingFileConstants.END_INDEXING_KEY, false, bitOutputStream);
        serializeIntValue(linkedHashMap.get(com.android.server.integrity.model.IndexingFileConstants.END_INDEXING_KEY).intValue(), bitOutputStream);
    }

    private void serializeStringValue(java.lang.String str, boolean z, com.android.server.integrity.model.BitOutputStream bitOutputStream) throws java.io.IOException {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("String value can not be null.");
        }
        byte[] bytesForString = getBytesForString(str, z);
        bitOutputStream.setNext(z);
        bitOutputStream.setNext(8, bytesForString.length);
        for (byte b : bytesForString) {
            bitOutputStream.setNext(8, b);
        }
    }

    private void serializeIntValue(int i, com.android.server.integrity.model.BitOutputStream bitOutputStream) throws java.io.IOException {
        bitOutputStream.setNext(32, i);
    }

    private void serializeBooleanValue(boolean z, com.android.server.integrity.model.BitOutputStream bitOutputStream) throws java.io.IOException {
        bitOutputStream.setNext(z);
    }

    private static byte[] getBytesForString(java.lang.String str, boolean z) {
        if (!z) {
            return str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        }
        return android.content.integrity.IntegrityUtils.getBytesFromHexDigest(str);
    }
}
