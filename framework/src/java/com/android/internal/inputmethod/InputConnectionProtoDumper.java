package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class InputConnectionProtoDumper {
    public static final boolean DUMP_TEXT = false;
    static final java.lang.String TAG = "InputConnectionProtoDumper";

    private InputConnectionProtoDumper() {
    }

    public static byte[] buildGetTextAfterCursorProto(int i, int i2, java.lang.CharSequence charSequence) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        long start = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1120986464258L, i2);
        if (charSequence == null) {
            protoOutputStream.write(1138166333443L, "null result");
        }
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetTextBeforeCursorProto(int i, int i2, java.lang.CharSequence charSequence) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1120986464258L, i2);
        if (charSequence == null) {
            protoOutputStream.write(1138166333443L, "null result");
        }
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetSelectedTextProto(int i, java.lang.CharSequence charSequence) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        long start = protoOutputStream.start(1146756268035L);
        protoOutputStream.write(1120986464257L, i);
        if (charSequence == null) {
            protoOutputStream.write(1138166333442L, "null result");
        }
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetSurroundingTextProto(int i, int i2, int i3, android.view.inputmethod.SurroundingText surroundingText) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        long start = protoOutputStream.start(1146756268036L);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1120986464258L, i2);
        protoOutputStream.write(1120986464259L, i3);
        if (surroundingText == null) {
            long start2 = protoOutputStream.start(1146756268036L);
            protoOutputStream.write(1138166333441L, "null result");
            protoOutputStream.end(start2);
        }
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetCursorCapsModeProto(int i, int i2) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        long start = protoOutputStream.start(1146756268037L);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetExtractedTextProto(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i, android.view.inputmethod.ExtractedText extractedText) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        long start = protoOutputStream.start(1146756268038L);
        long start2 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1120986464257L, extractedTextRequest.token);
        protoOutputStream.write(1120986464258L, extractedTextRequest.flags);
        protoOutputStream.write(1120986464259L, extractedTextRequest.hintMaxLines);
        protoOutputStream.write(1120986464260L, extractedTextRequest.hintMaxChars);
        protoOutputStream.end(start2);
        protoOutputStream.write(1120986464258L, i);
        if (extractedText == null) {
            protoOutputStream.write(1138166333443L, "null result");
        }
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }
}
