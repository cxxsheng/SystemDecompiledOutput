package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class Sms7BitEncodingTranslator {
    private static final java.lang.String TAG = "Sms7BitEncodingTranslator";
    private static final java.lang.String XML_CHARACTOR_TAG = "Character";
    private static final java.lang.String XML_FROM_TAG = "from";
    private static final java.lang.String XML_START_TAG = "SmsEnforce7BitTranslationTable";
    private static final java.lang.String XML_TO_TAG = "to";
    private static final java.lang.String XML_TRANSLATION_TYPE_TAG = "TranslationType";
    private static final boolean DBG = com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE;
    private static boolean mIs7BitTranslationTableLoaded = false;
    private static android.util.SparseIntArray mTranslationTable = null;
    private static android.util.SparseIntArray mTranslationTableCommon = null;
    private static android.util.SparseIntArray mTranslationTableGSM = null;
    private static android.util.SparseIntArray mTranslationTableCDMA = null;

    public static java.lang.String translate(java.lang.CharSequence charSequence, boolean z) {
        if (charSequence == null) {
            com.android.telephony.Rlog.w(TAG, "Null message can not be translated");
            return null;
        }
        int length = charSequence.length();
        if (length <= 0) {
            return "";
        }
        ensure7BitTranslationTableLoaded();
        if ((mTranslationTableCommon == null || mTranslationTableCommon.size() <= 0) && ((mTranslationTableGSM == null || mTranslationTableGSM.size() <= 0) && (mTranslationTableCDMA == null || mTranslationTableCDMA.size() <= 0))) {
            return null;
        }
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            cArr[i] = translateIfNeeded(charSequence.charAt(i), z);
        }
        return java.lang.String.valueOf(cArr);
    }

    private static char translateIfNeeded(char c, boolean z) {
        int i;
        if (noTranslationNeeded(c, z)) {
            if (DBG) {
                com.android.telephony.Rlog.v(TAG, "No translation needed for " + java.lang.Integer.toHexString(c));
            }
            return c;
        }
        ensure7BitTranslationTableLoaded();
        if (mTranslationTableCommon == null) {
            i = -1;
        } else {
            i = mTranslationTableCommon.get(c, -1);
        }
        if (i == -1) {
            if (z) {
                if (mTranslationTableCDMA != null) {
                    i = mTranslationTableCDMA.get(c, -1);
                }
            } else if (mTranslationTableGSM != null) {
                i = mTranslationTableGSM.get(c, -1);
            }
        }
        if (i != -1) {
            if (DBG) {
                com.android.telephony.Rlog.v(TAG, java.lang.Integer.toHexString(c) + " (" + c + ") translated to " + java.lang.Integer.toHexString(i) + " (" + ((char) i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            return (char) i;
        }
        if (DBG) {
            com.android.telephony.Rlog.w(TAG, "No translation found for " + java.lang.Integer.toHexString(c) + "! Replacing for empty space");
            return ' ';
        }
        return ' ';
    }

    private static boolean noTranslationNeeded(char c, boolean z) {
        if (z) {
            return com.android.internal.telephony.GsmAlphabet.isGsmSeptets(c) && com.android.internal.telephony.cdma.sms.UserData.charToAscii.get(c, -1) != -1;
        }
        return com.android.internal.telephony.GsmAlphabet.isGsmSeptets(c);
    }

    private static void ensure7BitTranslationTableLoaded() {
        synchronized (com.android.internal.telephony.Sms7BitEncodingTranslator.class) {
            if (!mIs7BitTranslationTableLoaded) {
                mTranslationTableCommon = new android.util.SparseIntArray();
                mTranslationTableGSM = new android.util.SparseIntArray();
                mTranslationTableCDMA = new android.util.SparseIntArray();
                load7BitTranslationTableFromXml();
                mIs7BitTranslationTableLoaded = true;
            }
        }
    }

    private static void load7BitTranslationTableFromXml() {
        android.content.res.Resources system = android.content.res.Resources.getSystem();
        if (DBG) {
            com.android.telephony.Rlog.d(TAG, "load7BitTranslationTableFromXml: open normal file");
        }
        android.content.res.XmlResourceParser xml = system.getXml(com.android.internal.R.xml.sms_7bit_translation_table);
        try {
            try {
                com.android.internal.telephony.util.XmlUtils.beginDocument(xml, XML_START_TAG);
                while (true) {
                    com.android.internal.telephony.util.XmlUtils.nextElement(xml);
                    java.lang.String name = xml.getName();
                    if (DBG) {
                        com.android.telephony.Rlog.d(TAG, "tag: " + name);
                    }
                    if (XML_TRANSLATION_TYPE_TAG.equals(name)) {
                        java.lang.String attributeValue = xml.getAttributeValue(null, "Type");
                        if (DBG) {
                            com.android.telephony.Rlog.d(TAG, "type: " + attributeValue);
                        }
                        if (attributeValue.equals(android.provider.ContactsContract.CommonDataKinds.PACKAGE_COMMON)) {
                            mTranslationTable = mTranslationTableCommon;
                        } else if (attributeValue.equals("gsm")) {
                            mTranslationTable = mTranslationTableGSM;
                        } else if (attributeValue.equals("cdma")) {
                            mTranslationTable = mTranslationTableCDMA;
                        } else {
                            com.android.telephony.Rlog.e(TAG, "Error Parsing 7BitTranslationTable: found incorrect type" + attributeValue);
                        }
                    } else {
                        if (!XML_CHARACTOR_TAG.equals(name) || mTranslationTable == null) {
                            break;
                        }
                        int attributeUnsignedIntValue = xml.getAttributeUnsignedIntValue(null, XML_FROM_TAG, -1);
                        int attributeUnsignedIntValue2 = xml.getAttributeUnsignedIntValue(null, XML_TO_TAG, -1);
                        if (attributeUnsignedIntValue == -1 || attributeUnsignedIntValue2 == -1) {
                            com.android.telephony.Rlog.d(TAG, "Invalid translation table file format");
                        } else {
                            if (DBG) {
                                com.android.telephony.Rlog.d(TAG, "Loading mapping " + java.lang.Integer.toHexString(attributeUnsignedIntValue).toUpperCase() + " -> " + java.lang.Integer.toHexString(attributeUnsignedIntValue2).toUpperCase());
                            }
                            mTranslationTable.put(attributeUnsignedIntValue, attributeUnsignedIntValue2);
                        }
                    }
                }
                if (DBG) {
                    com.android.telephony.Rlog.d(TAG, "load7BitTranslationTableFromXml: parsing successful, file loaded");
                }
                if (!(xml instanceof android.content.res.XmlResourceParser)) {
                    return;
                }
            } catch (java.lang.Exception e) {
                com.android.telephony.Rlog.e(TAG, "Got exception while loading 7BitTranslationTable file.", e);
                if (!(xml instanceof android.content.res.XmlResourceParser)) {
                    return;
                }
            }
            xml.close();
        } catch (java.lang.Throwable th) {
            if (xml instanceof android.content.res.XmlResourceParser) {
                xml.close();
            }
            throw th;
        }
    }
}
