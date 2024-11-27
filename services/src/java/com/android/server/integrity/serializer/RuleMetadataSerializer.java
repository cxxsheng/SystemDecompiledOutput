package com.android.server.integrity.serializer;

/* loaded from: classes2.dex */
public class RuleMetadataSerializer {
    public static void serialize(com.android.server.integrity.model.RuleMetadata ruleMetadata, java.io.OutputStream outputStream) throws java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
        serializeTaggedValue(resolveSerializer, com.android.server.integrity.parser.RuleMetadataParser.RULE_PROVIDER_TAG, ruleMetadata.getRuleProvider());
        serializeTaggedValue(resolveSerializer, com.android.server.integrity.parser.RuleMetadataParser.VERSION_TAG, ruleMetadata.getVersion());
        resolveSerializer.endDocument();
    }

    private static void serializeTaggedValue(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.text(str2);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }
}
