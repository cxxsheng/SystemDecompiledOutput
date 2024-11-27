package com.android.server.pm;

/* loaded from: classes2.dex */
class PackageSignatures {

    @android.annotation.NonNull
    android.content.pm.SigningDetails mSigningDetails;

    PackageSignatures(com.android.server.pm.PackageSignatures packageSignatures) {
        if (packageSignatures != null && packageSignatures.mSigningDetails != android.content.pm.SigningDetails.UNKNOWN) {
            this.mSigningDetails = new android.content.pm.SigningDetails(packageSignatures.mSigningDetails);
        } else {
            this.mSigningDetails = android.content.pm.SigningDetails.UNKNOWN;
        }
    }

    PackageSignatures(android.content.pm.SigningDetails signingDetails) {
        this.mSigningDetails = signingDetails;
    }

    PackageSignatures() {
        this.mSigningDetails = android.content.pm.SigningDetails.UNKNOWN;
    }

    void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.util.ArrayList<android.content.pm.Signature> arrayList) throws java.io.IOException {
        if (this.mSigningDetails.getSignatures() == null) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, this.mSigningDetails.getSignatures().length);
        typedXmlSerializer.attributeInt((java.lang.String) null, "schemeVersion", this.mSigningDetails.getSignatureSchemeVersion());
        writeCertsListXml(typedXmlSerializer, arrayList, this.mSigningDetails.getSignatures(), false);
        if (this.mSigningDetails.getPastSigningCertificates() != null) {
            typedXmlSerializer.startTag((java.lang.String) null, "pastSigs");
            typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, this.mSigningDetails.getPastSigningCertificates().length);
            writeCertsListXml(typedXmlSerializer, arrayList, this.mSigningDetails.getPastSigningCertificates(), true);
            typedXmlSerializer.endTag((java.lang.String) null, "pastSigs");
        }
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    private void writeCertsListXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.util.ArrayList<android.content.pm.Signature> arrayList, android.content.pm.Signature[] signatureArr, boolean z) throws java.io.IOException {
        for (android.content.pm.Signature signature : signatureArr) {
            typedXmlSerializer.startTag((java.lang.String) null, "cert");
            int hashCode = signature.hashCode();
            int size = arrayList.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                android.content.pm.Signature signature2 = arrayList.get(i);
                if (signature2.hashCode() != hashCode || !signature2.equals(signature)) {
                    i++;
                } else {
                    typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_INDEX, i);
                    break;
                }
            }
            if (i >= size) {
                arrayList.add(signature);
                typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_INDEX, size);
                signature.writeToXmlAttributeBytesHex(typedXmlSerializer, null, "key");
            }
            if (z) {
                typedXmlSerializer.attributeInt((java.lang.String) null, "flags", signature.getFlags());
            }
            typedXmlSerializer.endTag((java.lang.String) null, "cert");
        }
    }

    void readXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.ArrayList<android.content.pm.Signature> arrayList) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.pm.SigningDetails.Builder builder = new android.content.pm.SigningDetails.Builder();
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, -1);
        if (attributeInt != -1) {
            int attributeInt2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "schemeVersion", 0);
            if (attributeInt2 == 0) {
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <sigs> has no schemeVersion at " + typedXmlPullParser.getPositionDescription());
            }
            builder.setSignatureSchemeVersion(attributeInt2);
            java.util.ArrayList<android.content.pm.Signature> arrayList2 = new java.util.ArrayList<>();
            int readCertsListXml = readCertsListXml(typedXmlPullParser, arrayList, arrayList2, attributeInt, false, builder);
            builder.setSignatures((android.content.pm.Signature[]) arrayList2.toArray(new android.content.pm.Signature[arrayList2.size()]));
            if (readCertsListXml < attributeInt) {
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <sigs> count does not match number of  <cert> entries" + typedXmlPullParser.getPositionDescription());
            }
            try {
                this.mSigningDetails = builder.build();
                return;
            } catch (java.security.cert.CertificateException e) {
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <sigs> unable to convert certificate(s) to public key(s).");
                this.mSigningDetails = android.content.pm.SigningDetails.UNKNOWN;
                return;
            }
        }
        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <sigs> has no count at " + typedXmlPullParser.getPositionDescription());
        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x011c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int readCertsListXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.ArrayList<android.content.pm.Signature> arrayList, java.util.ArrayList<android.content.pm.Signature> arrayList2, int i, boolean z, android.content.pm.SigningDetails.Builder builder) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        boolean z2;
        int i2;
        int i3;
        java.lang.String str;
        int i4;
        com.android.modules.utils.TypedXmlPullParser typedXmlPullParser2 = typedXmlPullParser;
        int depth = typedXmlPullParser.getDepth();
        android.content.pm.SigningDetails.Builder builder2 = builder;
        int i5 = 0;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return i5;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return i5;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if (name.equals("cert")) {
                    if (i5 < i) {
                        int attributeInt = typedXmlPullParser2.getAttributeInt((java.lang.String) null, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_INDEX, -1);
                        if (attributeInt != -1) {
                            try {
                                byte[] attributeBytesHex = typedXmlPullParser2.getAttributeBytesHex((java.lang.String) null, "key", (byte[]) null);
                                if (attributeBytesHex == null) {
                                    if (attributeInt >= 0 && attributeInt < arrayList.size()) {
                                        android.content.pm.Signature signature = arrayList.get(attributeInt);
                                        if (signature != null) {
                                            if (z) {
                                                arrayList2.add(new android.content.pm.Signature(signature));
                                            } else {
                                                arrayList2.add(signature);
                                            }
                                            z2 = true;
                                        } else {
                                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <cert> index " + attributeInt + " is not defined at " + typedXmlPullParser.getPositionDescription());
                                            z2 = false;
                                        }
                                    } else {
                                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <cert> index " + attributeInt + " is out of bounds at " + typedXmlPullParser.getPositionDescription());
                                        z2 = false;
                                    }
                                } else {
                                    android.content.pm.Signature signature2 = new android.content.pm.Signature(attributeBytesHex);
                                    while (arrayList.size() < attributeInt) {
                                        arrayList.add(null);
                                    }
                                    arrayList.add(signature2);
                                    arrayList2.add(signature2);
                                    z2 = true;
                                }
                            } catch (java.lang.NumberFormatException e) {
                                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <cert> index " + attributeInt + " is not a number at " + typedXmlPullParser.getPositionDescription());
                                z2 = false;
                                if (z) {
                                }
                                i5++;
                                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                                typedXmlPullParser2 = typedXmlPullParser;
                            } catch (java.lang.IllegalArgumentException e2) {
                                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <cert> index " + attributeInt + " has an invalid signature at " + typedXmlPullParser.getPositionDescription() + ": " + e2.getMessage());
                                z2 = false;
                                if (z) {
                                }
                                i5++;
                                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                                typedXmlPullParser2 = typedXmlPullParser;
                            }
                            if (z) {
                                int attributeInt2 = typedXmlPullParser2.getAttributeInt((java.lang.String) null, "flags", -1);
                                if (attributeInt2 != -1) {
                                    if (z2) {
                                        try {
                                            arrayList2.get(arrayList2.size() - 1).setFlags(attributeInt2);
                                        } catch (java.lang.NumberFormatException e3) {
                                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <cert> flags " + attributeInt2 + " is not a number at " + typedXmlPullParser.getPositionDescription());
                                        }
                                    } else {
                                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: signature not available at index " + i5 + " to set flags at " + typedXmlPullParser.getPositionDescription());
                                    }
                                } else {
                                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <cert> has no flags at " + typedXmlPullParser.getPositionDescription());
                                }
                            }
                        } else {
                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <cert> has no index at " + typedXmlPullParser.getPositionDescription());
                        }
                    } else {
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: too many <cert> tags, expected " + i + " at " + typedXmlPullParser.getPositionDescription());
                    }
                    i5++;
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                } else {
                    if (name.equals("pastSigs")) {
                        if (!z) {
                            int attributeInt3 = typedXmlPullParser2.getAttributeInt((java.lang.String) null, com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, -1);
                            if (attributeInt3 == -1) {
                                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <pastSigs> has no count at " + typedXmlPullParser.getPositionDescription());
                                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                            } else {
                                try {
                                    java.util.ArrayList<android.content.pm.Signature> arrayList3 = new java.util.ArrayList<>();
                                    i3 = 5;
                                    str = " is not a number at ";
                                    i4 = i5;
                                    try {
                                        int readCertsListXml = readCertsListXml(typedXmlPullParser, arrayList, arrayList3, attributeInt3, true, builder2);
                                        builder2 = builder2.setPastSigningCertificates((android.content.pm.Signature[]) arrayList3.toArray(new android.content.pm.Signature[arrayList3.size()]));
                                        if (readCertsListXml < attributeInt3) {
                                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <pastSigs> count does not match number of <cert> entries " + typedXmlPullParser.getPositionDescription());
                                        }
                                    } catch (java.lang.NumberFormatException e4) {
                                        com.android.server.pm.PackageManagerService.reportSettingsProblem(i3, "Error in package manager settings: <pastSigs> count " + attributeInt3 + str + typedXmlPullParser.getPositionDescription());
                                        i5 = i4;
                                        typedXmlPullParser2 = typedXmlPullParser;
                                    }
                                } catch (java.lang.NumberFormatException e5) {
                                    i3 = 5;
                                    str = " is not a number at ";
                                    i4 = i5;
                                }
                                i5 = i4;
                            }
                        } else {
                            i2 = i5;
                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "<pastSigs> encountered multiple times under the same <sigs> at " + typedXmlPullParser.getPositionDescription());
                            com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                        }
                    } else {
                        i2 = i5;
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <sigs>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                    i5 = i2;
                }
                typedXmlPullParser2 = typedXmlPullParser;
            }
            typedXmlPullParser2 = typedXmlPullParser;
            i5 = i5;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("PackageSignatures{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" version:");
        sb.append(this.mSigningDetails.getSignatureSchemeVersion());
        sb.append(", signatures:[");
        if (this.mSigningDetails.getSignatures() != null) {
            for (int i = 0; i < this.mSigningDetails.getSignatures().length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(java.lang.Integer.toHexString(this.mSigningDetails.getSignatures()[i].hashCode()));
            }
        }
        sb.append("]");
        sb.append(", past signatures:[");
        if (this.mSigningDetails.getPastSigningCertificates() != null) {
            for (int i2 = 0; i2 < this.mSigningDetails.getPastSigningCertificates().length; i2++) {
                if (i2 > 0) {
                    sb.append(", ");
                }
                sb.append(java.lang.Integer.toHexString(this.mSigningDetails.getPastSigningCertificates()[i2].hashCode()));
                sb.append(" flags: ");
                sb.append(java.lang.Integer.toHexString(this.mSigningDetails.getPastSigningCertificates()[i2].getFlags()));
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}
