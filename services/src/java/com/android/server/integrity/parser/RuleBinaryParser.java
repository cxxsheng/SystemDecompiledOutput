package com.android.server.integrity.parser;

/* loaded from: classes2.dex */
public class RuleBinaryParser implements com.android.server.integrity.parser.RuleParser {
    @Override // com.android.server.integrity.parser.RuleParser
    public java.util.List<android.content.integrity.Rule> parse(byte[] bArr) throws com.android.server.integrity.parser.RuleParseException {
        return parse(com.android.server.integrity.parser.RandomAccessObject.ofBytes(bArr), java.util.Collections.emptyList());
    }

    @Override // com.android.server.integrity.parser.RuleParser
    public java.util.List<android.content.integrity.Rule> parse(com.android.server.integrity.parser.RandomAccessObject randomAccessObject, java.util.List<com.android.server.integrity.parser.RuleIndexRange> list) throws com.android.server.integrity.parser.RuleParseException {
        try {
            com.android.server.integrity.parser.RandomAccessInputStream randomAccessInputStream = new com.android.server.integrity.parser.RandomAccessInputStream(randomAccessObject);
            try {
                java.util.List<android.content.integrity.Rule> parseRules = parseRules(randomAccessInputStream, list);
                randomAccessInputStream.close();
                return parseRules;
            } finally {
            }
        } catch (java.lang.Exception e) {
            throw new com.android.server.integrity.parser.RuleParseException(e.getMessage(), e);
        }
    }

    private java.util.List<android.content.integrity.Rule> parseRules(com.android.server.integrity.parser.RandomAccessInputStream randomAccessInputStream, java.util.List<com.android.server.integrity.parser.RuleIndexRange> list) throws java.io.IOException {
        randomAccessInputStream.skip(1L);
        if (list.isEmpty()) {
            return parseAllRules(randomAccessInputStream);
        }
        return parseIndexedRules(randomAccessInputStream, list);
    }

    private java.util.List<android.content.integrity.Rule> parseAllRules(com.android.server.integrity.parser.RandomAccessInputStream randomAccessInputStream) throws java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.integrity.model.BitInputStream bitInputStream = new com.android.server.integrity.model.BitInputStream(new java.io.BufferedInputStream(randomAccessInputStream));
        while (bitInputStream.hasNext()) {
            if (bitInputStream.getNext(1) == 1) {
                arrayList.add(parseRule(bitInputStream));
            }
        }
        return arrayList;
    }

    private java.util.List<android.content.integrity.Rule> parseIndexedRules(com.android.server.integrity.parser.RandomAccessInputStream randomAccessInputStream, java.util.List<com.android.server.integrity.parser.RuleIndexRange> list) throws java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.android.server.integrity.parser.RuleIndexRange ruleIndexRange : list) {
            randomAccessInputStream.seek(ruleIndexRange.getStartIndex());
            com.android.server.integrity.model.BitInputStream bitInputStream = new com.android.server.integrity.model.BitInputStream(new java.io.BufferedInputStream(new com.android.server.integrity.parser.LimitInputStream(randomAccessInputStream, ruleIndexRange.getEndIndex() - ruleIndexRange.getStartIndex())));
            while (bitInputStream.hasNext()) {
                if (bitInputStream.getNext(1) == 1) {
                    arrayList.add(parseRule(bitInputStream));
                }
            }
        }
        return arrayList;
    }

    private android.content.integrity.Rule parseRule(com.android.server.integrity.model.BitInputStream bitInputStream) throws java.io.IOException {
        android.content.integrity.IntegrityFormula parseFormula = parseFormula(bitInputStream);
        int next = bitInputStream.getNext(3);
        if (bitInputStream.getNext(1) != 1) {
            throw new java.lang.IllegalArgumentException("A rule must end with a '1' bit.");
        }
        return new android.content.integrity.Rule(parseFormula, next);
    }

    private android.content.integrity.IntegrityFormula parseFormula(com.android.server.integrity.model.BitInputStream bitInputStream) throws java.io.IOException {
        int next = bitInputStream.getNext(3);
        switch (next) {
            case 0:
                return parseAtomicFormula(bitInputStream);
            case 1:
                return parseCompoundFormula(bitInputStream);
            case 2:
                return null;
            case 3:
                return new android.content.integrity.InstallerAllowedByManifestFormula();
            default:
                throw new java.lang.IllegalArgumentException(java.lang.String.format("Unknown formula separator: %s", java.lang.Integer.valueOf(next)));
        }
    }

    private android.content.integrity.CompoundFormula parseCompoundFormula(com.android.server.integrity.model.BitInputStream bitInputStream) throws java.io.IOException {
        int next = bitInputStream.getNext(2);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.integrity.IntegrityFormula parseFormula = parseFormula(bitInputStream);
        while (parseFormula != null) {
            arrayList.add(parseFormula);
            parseFormula = parseFormula(bitInputStream);
        }
        return new android.content.integrity.CompoundFormula(next, arrayList);
    }

    private android.content.integrity.AtomicFormula parseAtomicFormula(com.android.server.integrity.model.BitInputStream bitInputStream) throws java.io.IOException {
        int next = bitInputStream.getNext(4);
        int next2 = bitInputStream.getNext(3);
        switch (next) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 7:
            case 8:
                boolean z = bitInputStream.getNext(1) == 1;
                return new android.content.integrity.AtomicFormula.StringAtomicFormula(next, com.android.server.integrity.parser.BinaryFileOperations.getStringValue(bitInputStream, bitInputStream.getNext(8), z), z);
            case 4:
                return new android.content.integrity.AtomicFormula.LongAtomicFormula(next, next2, (com.android.server.integrity.parser.BinaryFileOperations.getIntValue(bitInputStream) << 32) | com.android.server.integrity.parser.BinaryFileOperations.getIntValue(bitInputStream));
            case 5:
            case 6:
                return new android.content.integrity.AtomicFormula.BooleanAtomicFormula(next, com.android.server.integrity.parser.BinaryFileOperations.getBooleanValue(bitInputStream));
            default:
                throw new java.lang.IllegalArgumentException(java.lang.String.format("Unknown key: %d", java.lang.Integer.valueOf(next)));
        }
    }
}
