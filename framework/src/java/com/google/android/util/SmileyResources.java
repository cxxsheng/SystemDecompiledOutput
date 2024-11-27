package com.google.android.util;

/* loaded from: classes5.dex */
public class SmileyResources implements com.google.android.util.AbstractMessageParser.Resources {
    private java.util.HashMap<java.lang.String, java.lang.Integer> mSmileyToRes = new java.util.HashMap<>();
    private final com.google.android.util.AbstractMessageParser.TrieNode smileys = new com.google.android.util.AbstractMessageParser.TrieNode();

    public SmileyResources(java.lang.String[] strArr, int[] iArr) {
        for (int i = 0; i < strArr.length; i++) {
            com.google.android.util.AbstractMessageParser.TrieNode.addToTrie(this.smileys, strArr[i], "");
            this.mSmileyToRes.put(strArr[i], java.lang.Integer.valueOf(iArr[i]));
        }
    }

    public int getSmileyRes(java.lang.String str) {
        java.lang.Integer num = this.mSmileyToRes.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    @Override // com.google.android.util.AbstractMessageParser.Resources
    public java.util.Set<java.lang.String> getSchemes() {
        return null;
    }

    @Override // com.google.android.util.AbstractMessageParser.Resources
    public com.google.android.util.AbstractMessageParser.TrieNode getDomainSuffixes() {
        return null;
    }

    @Override // com.google.android.util.AbstractMessageParser.Resources
    public com.google.android.util.AbstractMessageParser.TrieNode getSmileys() {
        return this.smileys;
    }

    @Override // com.google.android.util.AbstractMessageParser.Resources
    public com.google.android.util.AbstractMessageParser.TrieNode getAcronyms() {
        return null;
    }
}
