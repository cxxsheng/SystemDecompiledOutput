package com.android.server.pm;

/* loaded from: classes2.dex */
public class SettingsXml {
    private static final boolean DEBUG_THROW_EXCEPTIONS = false;
    private static final int DEFAULT_NUMBER = -1;
    private static final java.lang.String FEATURE_INDENT = "http://xmlpull.org/v1/doc/features.html#indent-output";
    private static final java.lang.String TAG = "SettingsXml";

    public interface ChildSection extends com.android.server.pm.SettingsXml.ReadSection {
        boolean moveToNext();

        boolean moveToNext(@android.annotation.NonNull java.lang.String str);
    }

    public interface ReadSection extends java.lang.AutoCloseable {
        com.android.server.pm.SettingsXml.ChildSection children();

        boolean getBoolean(java.lang.String str);

        boolean getBoolean(java.lang.String str, boolean z);

        @android.annotation.NonNull
        java.lang.String getDescription();

        int getInt(java.lang.String str);

        int getInt(java.lang.String str, int i);

        long getLong(java.lang.String str);

        long getLong(java.lang.String str, int i);

        @android.annotation.NonNull
        java.lang.String getName();

        @android.annotation.Nullable
        java.lang.String getString(java.lang.String str);

        @android.annotation.NonNull
        java.lang.String getString(java.lang.String str, @android.annotation.NonNull java.lang.String str2);

        boolean has(java.lang.String str);
    }

    public interface WriteSection extends java.lang.AutoCloseable {
        com.android.server.pm.SettingsXml.WriteSection attribute(java.lang.String str, int i) throws java.io.IOException;

        com.android.server.pm.SettingsXml.WriteSection attribute(java.lang.String str, long j) throws java.io.IOException;

        com.android.server.pm.SettingsXml.WriteSection attribute(java.lang.String str, @android.annotation.Nullable java.lang.String str2) throws java.io.IOException;

        com.android.server.pm.SettingsXml.WriteSection attribute(java.lang.String str, boolean z) throws java.io.IOException;

        @Override // java.lang.AutoCloseable
        void close() throws java.io.IOException;

        void finish() throws java.io.IOException;

        com.android.server.pm.SettingsXml.WriteSection startSection(@android.annotation.NonNull java.lang.String str) throws java.io.IOException;
    }

    public static com.android.server.pm.SettingsXml.Serializer serializer(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) {
        return new com.android.server.pm.SettingsXml.Serializer(typedXmlSerializer);
    }

    public static com.android.server.pm.SettingsXml.ReadSection parser(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        return new com.android.server.pm.SettingsXml.ReadSectionImpl(typedXmlPullParser);
    }

    public static class Serializer implements java.lang.AutoCloseable {
        private final com.android.server.pm.SettingsXml.WriteSectionImpl mWriteSection;

        @android.annotation.NonNull
        private final com.android.modules.utils.TypedXmlSerializer mXmlSerializer;

        private Serializer(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) {
            this.mXmlSerializer = typedXmlSerializer;
            this.mWriteSection = new com.android.server.pm.SettingsXml.WriteSectionImpl(this.mXmlSerializer);
        }

        public com.android.server.pm.SettingsXml.WriteSection startSection(@android.annotation.NonNull java.lang.String str) throws java.io.IOException {
            return this.mWriteSection.startSection(str);
        }

        @Override // java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            this.mWriteSection.closeCompletely();
            this.mXmlSerializer.flush();
        }
    }

    public static class ReadSectionImpl implements com.android.server.pm.SettingsXml.ChildSection {

        @android.annotation.NonNull
        private final java.util.Stack<java.lang.Integer> mDepthStack;

        @android.annotation.Nullable
        private final java.io.InputStream mInput;

        @android.annotation.NonNull
        private final com.android.modules.utils.TypedXmlPullParser mParser;

        public ReadSectionImpl(@android.annotation.NonNull java.io.InputStream inputStream) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            this.mDepthStack = new java.util.Stack<>();
            this.mInput = inputStream;
            this.mParser = android.util.Xml.newFastPullParser();
            this.mParser.setInput(this.mInput, java.nio.charset.StandardCharsets.UTF_8.name());
            moveToFirstTag();
        }

        public ReadSectionImpl(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            this.mDepthStack = new java.util.Stack<>();
            this.mInput = null;
            this.mParser = typedXmlPullParser;
            moveToFirstTag();
        }

        private void moveToFirstTag() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int next;
            if (this.mParser.getEventType() == 2) {
                return;
            }
            do {
                next = this.mParser.next();
                if (next == 2) {
                    return;
                }
            } while (next != 1);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        @android.annotation.NonNull
        public java.lang.String getName() {
            return this.mParser.getName();
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        @android.annotation.NonNull
        public java.lang.String getDescription() {
            return this.mParser.getPositionDescription();
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public boolean has(java.lang.String str) {
            return this.mParser.getAttributeValue((java.lang.String) null, str) != null;
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        @android.annotation.Nullable
        public java.lang.String getString(java.lang.String str) {
            return this.mParser.getAttributeValue((java.lang.String) null, str);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        @android.annotation.NonNull
        public java.lang.String getString(java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            java.lang.String attributeValue = this.mParser.getAttributeValue((java.lang.String) null, str);
            if (attributeValue == null) {
                return str2;
            }
            return attributeValue;
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public boolean getBoolean(java.lang.String str) {
            return getBoolean(str, false);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public boolean getBoolean(java.lang.String str, boolean z) {
            return this.mParser.getAttributeBoolean((java.lang.String) null, str, z);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public int getInt(java.lang.String str) {
            return getInt(str, -1);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public int getInt(java.lang.String str, int i) {
            return this.mParser.getAttributeInt((java.lang.String) null, str, i);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public long getLong(java.lang.String str) {
            return getLong(str, -1);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public long getLong(java.lang.String str, int i) {
            return this.mParser.getAttributeLong((java.lang.String) null, str, i);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public com.android.server.pm.SettingsXml.ChildSection children() {
            this.mDepthStack.push(java.lang.Integer.valueOf(this.mParser.getDepth()));
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.ChildSection
        public boolean moveToNext() {
            return moveToNextInternal(null);
        }

        @Override // com.android.server.pm.SettingsXml.ChildSection
        public boolean moveToNext(@android.annotation.NonNull java.lang.String str) {
            return moveToNextInternal(str);
        }

        private boolean moveToNextInternal(@android.annotation.Nullable java.lang.String str) {
            try {
                int intValue = this.mDepthStack.peek().intValue();
                boolean z = false;
                while (!z) {
                    int next = this.mParser.next();
                    if (next == 1) {
                        break;
                    }
                    if (next == 3 && this.mParser.getDepth() <= intValue) {
                        break;
                    }
                    if (next == 2 && (str == null || str.equals(this.mParser.getName()))) {
                        z = true;
                    }
                }
                if (!z) {
                    this.mDepthStack.pop();
                }
                return z;
            } catch (java.lang.Exception e) {
                return false;
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() throws java.lang.Exception {
            if (this.mDepthStack.isEmpty()) {
                android.util.Slog.wtf(com.android.server.pm.SettingsXml.TAG, "Children depth stack was not empty, data may have been lost", new java.lang.Exception());
            }
            if (this.mInput != null) {
                this.mInput.close();
            }
        }
    }

    private static class WriteSectionImpl implements com.android.server.pm.SettingsXml.WriteSection {

        @android.annotation.NonNull
        private final java.util.Stack<java.lang.String> mTagStack;

        @android.annotation.NonNull
        private final com.android.modules.utils.TypedXmlSerializer mXmlSerializer;

        private WriteSectionImpl(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) {
            this.mTagStack = new java.util.Stack<>();
            this.mXmlSerializer = typedXmlSerializer;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public com.android.server.pm.SettingsXml.WriteSection startSection(@android.annotation.NonNull java.lang.String str) throws java.io.IOException {
            this.mXmlSerializer.startTag((java.lang.String) null, str);
            this.mTagStack.push(str);
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public com.android.server.pm.SettingsXml.WriteSection attribute(java.lang.String str, java.lang.String str2) throws java.io.IOException {
            if (str2 != null) {
                this.mXmlSerializer.attribute((java.lang.String) null, str, str2);
            }
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public com.android.server.pm.SettingsXml.WriteSection attribute(java.lang.String str, int i) throws java.io.IOException {
            if (i != -1) {
                this.mXmlSerializer.attributeInt((java.lang.String) null, str, i);
            }
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public com.android.server.pm.SettingsXml.WriteSection attribute(java.lang.String str, long j) throws java.io.IOException {
            if (j != -1) {
                this.mXmlSerializer.attributeLong((java.lang.String) null, str, j);
            }
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public com.android.server.pm.SettingsXml.WriteSection attribute(java.lang.String str, boolean z) throws java.io.IOException {
            if (z) {
                this.mXmlSerializer.attributeBoolean((java.lang.String) null, str, z);
            }
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public void finish() throws java.io.IOException {
            close();
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            this.mXmlSerializer.endTag((java.lang.String) null, this.mTagStack.pop());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void closeCompletely() throws java.io.IOException {
            if (this.mTagStack != null) {
                while (!this.mTagStack.isEmpty()) {
                    close();
                }
            }
        }
    }
}
