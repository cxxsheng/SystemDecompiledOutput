package com.google.android.util;

/* loaded from: classes5.dex */
public class SmileyParser extends com.google.android.util.AbstractMessageParser {
    private com.google.android.util.SmileyResources mRes;

    public SmileyParser(java.lang.String str, com.google.android.util.SmileyResources smileyResources) {
        super(str, true, false, false, false, false, false);
        this.mRes = smileyResources;
    }

    @Override // com.google.android.util.AbstractMessageParser
    protected com.google.android.util.AbstractMessageParser.Resources getResources() {
        return this.mRes;
    }

    public java.lang.CharSequence getSpannableString(android.content.Context context) {
        int smileyRes;
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
        if (getPartCount() == 0) {
            return "";
        }
        java.util.ArrayList<com.google.android.util.AbstractMessageParser.Token> tokens = getPart(0).getTokens();
        int size = tokens.size();
        for (int i = 0; i < size; i++) {
            com.google.android.util.AbstractMessageParser.Token token = tokens.get(i);
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append((java.lang.CharSequence) token.getRawText());
            if (token.getType() == com.google.android.util.AbstractMessageParser.Token.Type.SMILEY && (smileyRes = this.mRes.getSmileyRes(token.getRawText())) != -1) {
                spannableStringBuilder.setSpan(new android.text.style.ImageSpan(context, smileyRes), length, spannableStringBuilder.length(), 33);
            }
        }
        return spannableStringBuilder;
    }
}
