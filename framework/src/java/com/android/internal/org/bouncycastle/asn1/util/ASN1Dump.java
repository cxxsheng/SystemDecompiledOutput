package com.android.internal.org.bouncycastle.asn1.util;

/* loaded from: classes4.dex */
public class ASN1Dump {
    private static final int SAMPLE_SIZE = 32;
    private static final java.lang.String TAB = "    ";

    static void _dumpAsString(java.lang.String str, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive, java.lang.StringBuffer stringBuffer) {
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            java.util.Enumeration objects = ((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Primitive).getObjects();
            java.lang.String str2 = str + TAB;
            stringBuffer.append(str);
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.BERSequence) {
                stringBuffer.append("BER Sequence");
            } else if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERSequence) {
                stringBuffer.append("DER Sequence");
            } else {
                stringBuffer.append("Sequence");
            }
            stringBuffer.append(lineSeparator);
            while (objects.hasMoreElements()) {
                java.lang.Object nextElement = objects.nextElement();
                if (nextElement == null || nextElement.equals(com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE)) {
                    stringBuffer.append(str2);
                    stringBuffer.append("NULL");
                    stringBuffer.append(lineSeparator);
                } else if (nextElement instanceof com.android.internal.org.bouncycastle.asn1.ASN1Primitive) {
                    _dumpAsString(str2, z, (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) nextElement, stringBuffer);
                } else {
                    _dumpAsString(str2, z, ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) nextElement).toASN1Primitive(), stringBuffer);
                }
            }
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            java.lang.String str3 = str + TAB;
            stringBuffer.append(str);
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject) {
                stringBuffer.append("BER Tagged [");
            } else {
                stringBuffer.append("Tagged [");
            }
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Primitive;
            stringBuffer.append(java.lang.Integer.toString(aSN1TaggedObject.getTagNo()));
            stringBuffer.append(']');
            if (!aSN1TaggedObject.isExplicit()) {
                stringBuffer.append(" IMPLICIT ");
            }
            stringBuffer.append(lineSeparator);
            _dumpAsString(str3, z, aSN1TaggedObject.getObject(), stringBuffer);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Set) {
            java.util.Enumeration objects2 = ((com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Primitive).getObjects();
            java.lang.String str4 = str + TAB;
            stringBuffer.append(str);
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.BERSet) {
                stringBuffer.append("BER Set");
            } else if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERSet) {
                stringBuffer.append("DER Set");
            } else {
                stringBuffer.append("Set");
            }
            stringBuffer.append(lineSeparator);
            while (objects2.hasMoreElements()) {
                java.lang.Object nextElement2 = objects2.nextElement();
                if (nextElement2 == null) {
                    stringBuffer.append(str4);
                    stringBuffer.append("NULL");
                    stringBuffer.append(lineSeparator);
                } else if (nextElement2 instanceof com.android.internal.org.bouncycastle.asn1.ASN1Primitive) {
                    _dumpAsString(str4, z, (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) nextElement2, stringBuffer);
                } else {
                    _dumpAsString(str4, z, ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) nextElement2).toASN1Primitive(), stringBuffer);
                }
            }
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
            com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Primitive;
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.BEROctetString) {
                stringBuffer.append(str + "BER Constructed Octet String[" + aSN1OctetString.getOctets().length + "] ");
            } else {
                stringBuffer.append(str + "DER Octet String[" + aSN1OctetString.getOctets().length + "] ");
            }
            if (z) {
                stringBuffer.append(dumpBinaryDataAsString(str, aSN1OctetString.getOctets()));
                return;
            } else {
                stringBuffer.append(lineSeparator);
                return;
            }
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) {
            stringBuffer.append(str + "ObjectIdentifier(" + ((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Primitive).getId() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Boolean) {
            stringBuffer.append(str + "Boolean(" + ((com.android.internal.org.bouncycastle.asn1.ASN1Boolean) aSN1Primitive).isTrue() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer) {
            stringBuffer.append(str + "Integer(" + ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Primitive).getValue() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERBitString) {
            com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString = (com.android.internal.org.bouncycastle.asn1.DERBitString) aSN1Primitive;
            stringBuffer.append(str + "DER Bit String[" + dERBitString.getBytes().length + ", " + dERBitString.getPadBits() + "] ");
            if (z) {
                stringBuffer.append(dumpBinaryDataAsString(str, dERBitString.getBytes()));
                return;
            } else {
                stringBuffer.append(lineSeparator);
                return;
            }
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERIA5String) {
            stringBuffer.append(str + "IA5String(" + ((com.android.internal.org.bouncycastle.asn1.DERIA5String) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERUTF8String) {
            stringBuffer.append(str + "UTF8String(" + ((com.android.internal.org.bouncycastle.asn1.DERUTF8String) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERPrintableString) {
            stringBuffer.append(str + "PrintableString(" + ((com.android.internal.org.bouncycastle.asn1.DERPrintableString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERVisibleString) {
            stringBuffer.append(str + "VisibleString(" + ((com.android.internal.org.bouncycastle.asn1.DERVisibleString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERBMPString) {
            stringBuffer.append(str + "BMPString(" + ((com.android.internal.org.bouncycastle.asn1.DERBMPString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERT61String) {
            stringBuffer.append(str + "T61String(" + ((com.android.internal.org.bouncycastle.asn1.DERT61String) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERGraphicString) {
            stringBuffer.append(str + "GraphicString(" + ((com.android.internal.org.bouncycastle.asn1.DERGraphicString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERVideotexString) {
            stringBuffer.append(str + "VideotexString(" + ((com.android.internal.org.bouncycastle.asn1.DERVideotexString) aSN1Primitive).getString() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) {
            stringBuffer.append(str + "UTCTime(" + ((com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) aSN1Primitive).getTime() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) {
            stringBuffer.append(str + "GeneralizedTime(" + ((com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) aSN1Primitive).getTime() + ") " + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.BERApplicationSpecific) {
            stringBuffer.append(outputApplicationSpecific(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.BER, str, z, aSN1Primitive, lineSeparator));
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERApplicationSpecific) {
            stringBuffer.append(outputApplicationSpecific(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER, str, z, aSN1Primitive, lineSeparator));
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DLApplicationSpecific) {
            stringBuffer.append(outputApplicationSpecific("", str, z, aSN1Primitive, lineSeparator));
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Enumerated) {
            stringBuffer.append(str + "DER Enumerated(" + ((com.android.internal.org.bouncycastle.asn1.ASN1Enumerated) aSN1Primitive).getValue() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END + lineSeparator);
            return;
        }
        if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1External) {
            com.android.internal.org.bouncycastle.asn1.ASN1External aSN1External = (com.android.internal.org.bouncycastle.asn1.ASN1External) aSN1Primitive;
            stringBuffer.append(str + "External " + lineSeparator);
            java.lang.String str5 = str + TAB;
            if (aSN1External.getDirectReference() != null) {
                stringBuffer.append(str5 + "Direct Reference: " + aSN1External.getDirectReference().getId() + lineSeparator);
            }
            if (aSN1External.getIndirectReference() != null) {
                stringBuffer.append(str5 + "Indirect Reference: " + aSN1External.getIndirectReference().toString() + lineSeparator);
            }
            if (aSN1External.getDataValueDescriptor() != null) {
                _dumpAsString(str5, z, aSN1External.getDataValueDescriptor(), stringBuffer);
            }
            stringBuffer.append(str5 + "Encoding: " + aSN1External.getEncoding() + lineSeparator);
            _dumpAsString(str5, z, aSN1External.getExternalContent(), stringBuffer);
            return;
        }
        stringBuffer.append(str + aSN1Primitive.toString() + lineSeparator);
    }

    private static java.lang.String outputApplicationSpecific(java.lang.String str, java.lang.String str2, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive, java.lang.String str3) {
        com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecific aSN1ApplicationSpecific = com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecific.getInstance(aSN1Primitive);
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        if (aSN1ApplicationSpecific.isConstructed()) {
            try {
                com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1ApplicationSpecific.getObject(16));
                stringBuffer.append(str2 + str + " ApplicationSpecific[" + aSN1ApplicationSpecific.getApplicationTag() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END + str3);
                java.util.Enumeration objects = aSN1Sequence.getObjects();
                while (objects.hasMoreElements()) {
                    _dumpAsString(str2 + TAB, z, (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) objects.nextElement(), stringBuffer);
                }
            } catch (java.io.IOException e) {
                stringBuffer.append(e);
            }
            return stringBuffer.toString();
        }
        return str2 + str + " ApplicationSpecific[" + aSN1ApplicationSpecific.getApplicationTag() + "] (" + com.android.internal.org.bouncycastle.util.Strings.fromByteArray(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(aSN1ApplicationSpecific.getContents())) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END + str3;
    }

    public static java.lang.String dumpAsString(java.lang.Object obj) {
        return dumpAsString(obj, false);
    }

    public static java.lang.String dumpAsString(java.lang.Object obj, boolean z) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Primitive) {
            _dumpAsString("", z, (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) obj, stringBuffer);
        } else if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Encodable) {
            _dumpAsString("", z, ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive(), stringBuffer);
        } else {
            return "unknown object type " + obj.toString();
        }
        return stringBuffer.toString();
    }

    private static java.lang.String dumpBinaryDataAsString(java.lang.String str, byte[] bArr) {
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String str2 = str + TAB;
        stringBuffer.append(lineSeparator);
        for (int i = 0; i < bArr.length; i += 32) {
            if (bArr.length - i > 32) {
                stringBuffer.append(str2);
                stringBuffer.append(com.android.internal.org.bouncycastle.util.Strings.fromByteArray(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(bArr, i, 32)));
                stringBuffer.append(TAB);
                stringBuffer.append(calculateAscString(bArr, i, 32));
                stringBuffer.append(lineSeparator);
            } else {
                stringBuffer.append(str2);
                stringBuffer.append(com.android.internal.org.bouncycastle.util.Strings.fromByteArray(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(bArr, i, bArr.length - i)));
                for (int length = bArr.length - i; length != 32; length++) {
                    stringBuffer.append("  ");
                }
                stringBuffer.append(TAB);
                stringBuffer.append(calculateAscString(bArr, i, bArr.length - i));
                stringBuffer.append(lineSeparator);
            }
        }
        return stringBuffer.toString();
    }

    private static java.lang.String calculateAscString(byte[] bArr, int i, int i2) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        for (int i3 = i; i3 != i + i2; i3++) {
            if (bArr[i3] >= 32 && bArr[i3] <= 126) {
                stringBuffer.append((char) bArr[i3]);
            }
        }
        return stringBuffer.toString();
    }
}
