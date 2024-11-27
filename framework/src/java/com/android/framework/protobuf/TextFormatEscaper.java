package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class TextFormatEscaper {

    private interface ByteSequence {
        byte byteAt(int i);

        int size();
    }

    private TextFormatEscaper() {
    }

    static java.lang.String escapeBytes(com.android.framework.protobuf.TextFormatEscaper.ByteSequence byteSequence) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(byteSequence.size());
        for (int i = 0; i < byteSequence.size(); i++) {
            byte byteAt = byteSequence.byteAt(i);
            switch (byteAt) {
                case 7:
                    sb.append("\\a");
                    break;
                case 8:
                    sb.append("\\b");
                    break;
                case 9:
                    sb.append("\\t");
                    break;
                case 10:
                    sb.append("\\n");
                    break;
                case 11:
                    sb.append("\\v");
                    break;
                case 12:
                    sb.append("\\f");
                    break;
                case 13:
                    sb.append("\\r");
                    break;
                case 34:
                    sb.append("\\\"");
                    break;
                case 39:
                    sb.append("\\'");
                    break;
                case 92:
                    sb.append("\\\\");
                    break;
                default:
                    if (byteAt >= 32 && byteAt <= 126) {
                        sb.append((char) byteAt);
                        break;
                    } else {
                        sb.append('\\');
                        sb.append((char) (((byteAt >>> 6) & 3) + 48));
                        sb.append((char) (((byteAt >>> 3) & 7) + 48));
                        sb.append((char) ((byteAt & 7) + 48));
                        break;
                    }
            }
        }
        return sb.toString();
    }

    static java.lang.String escapeBytes(final com.android.framework.protobuf.ByteString byteString) {
        return escapeBytes(new com.android.framework.protobuf.TextFormatEscaper.ByteSequence() { // from class: com.android.framework.protobuf.TextFormatEscaper.1
            @Override // com.android.framework.protobuf.TextFormatEscaper.ByteSequence
            public int size() {
                return com.android.framework.protobuf.ByteString.this.size();
            }

            @Override // com.android.framework.protobuf.TextFormatEscaper.ByteSequence
            public byte byteAt(int i) {
                return com.android.framework.protobuf.ByteString.this.byteAt(i);
            }
        });
    }

    static java.lang.String escapeBytes(final byte[] bArr) {
        return escapeBytes(new com.android.framework.protobuf.TextFormatEscaper.ByteSequence() { // from class: com.android.framework.protobuf.TextFormatEscaper.2
            @Override // com.android.framework.protobuf.TextFormatEscaper.ByteSequence
            public int size() {
                return bArr.length;
            }

            @Override // com.android.framework.protobuf.TextFormatEscaper.ByteSequence
            public byte byteAt(int i) {
                return bArr[i];
            }
        });
    }

    static java.lang.String escapeText(java.lang.String str) {
        return escapeBytes(com.android.framework.protobuf.ByteString.copyFromUtf8(str));
    }

    static java.lang.String escapeDoubleQuotesAndBackslashes(java.lang.String str) {
        return str.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
