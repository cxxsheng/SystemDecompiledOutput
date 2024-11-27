package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class PowerStatsSpan {
    private static final java.time.format.DateTimeFormatter DATE_FORMAT = java.time.format.DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS").withZone(java.time.ZoneId.systemDefault());
    private static final java.lang.String TAG = "PowerStatsStore";
    private static final int VERSION = 2;
    private static final java.lang.String XML_ATTR_DURATION = "duration";
    private static final java.lang.String XML_ATTR_ID = "id";
    private static final java.lang.String XML_ATTR_MONOTONIC = "monotonic";
    private static final java.lang.String XML_ATTR_SECTION_TYPE = "type";
    private static final java.lang.String XML_ATTR_START_TIME = "start";
    private static final java.lang.String XML_ATTR_VERSION = "version";
    private static final java.lang.String XML_TAG_METADATA = "metadata";
    private static final java.lang.String XML_TAG_SECTION = "section";
    private static final java.lang.String XML_TAG_TIMEFRAME = "timeframe";
    private final com.android.server.power.stats.PowerStatsSpan.Metadata mMetadata;
    private final java.util.List<com.android.server.power.stats.PowerStatsSpan.Section> mSections;

    public interface SectionReader {
        com.android.server.power.stats.PowerStatsSpan.Section read(java.lang.String str, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException;
    }

    static class TimeFrame {
        public final long duration;
        public final long startMonotonicTime;
        public final long startTime;

        TimeFrame(long j, long j2, long j3) {
            this.startMonotonicTime = j;
            this.startTime = j2;
            this.duration = j3;
        }

        void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_TAG_TIMEFRAME);
            typedXmlSerializer.attributeLong((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_START_TIME, this.startTime);
            typedXmlSerializer.attributeLong((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_MONOTONIC, this.startMonotonicTime);
            typedXmlSerializer.attributeLong((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_DURATION, this.duration);
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_TAG_TIMEFRAME);
        }

        static com.android.server.power.stats.PowerStatsSpan.TimeFrame read(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
            return new com.android.server.power.stats.PowerStatsSpan.TimeFrame(typedXmlPullParser.getAttributeLong((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_MONOTONIC), typedXmlPullParser.getAttributeLong((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_START_TIME), typedXmlPullParser.getAttributeLong((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_DURATION));
        }

        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(com.android.server.power.stats.PowerStatsSpan.DATE_FORMAT.format(java.time.Instant.ofEpochMilli(this.startTime)));
            sb.append(" (monotonic=");
            sb.append(this.startMonotonicTime);
            sb.append(") ");
            sb.append(" duration=");
            java.lang.String formatDuration = android.util.TimeUtils.formatDuration(this.duration);
            if (formatDuration.startsWith("+")) {
                sb.append(formatDuration.substring(1));
            } else {
                sb.append(formatDuration);
            }
            indentingPrintWriter.print(sb);
        }
    }

    static class Metadata {
        static final java.util.Comparator<com.android.server.power.stats.PowerStatsSpan.Metadata> COMPARATOR = java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.power.stats.PowerStatsSpan$Metadata$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Long.valueOf(((com.android.server.power.stats.PowerStatsSpan.Metadata) obj).getId());
            }
        });
        private final long mId;
        private final java.util.List<com.android.server.power.stats.PowerStatsSpan.TimeFrame> mTimeFrames = new java.util.ArrayList();
        private final java.util.List<java.lang.String> mSections = new java.util.ArrayList();

        Metadata(long j) {
            this.mId = j;
        }

        public long getId() {
            return this.mId;
        }

        public java.util.List<com.android.server.power.stats.PowerStatsSpan.TimeFrame> getTimeFrames() {
            return this.mTimeFrames;
        }

        public java.util.List<java.lang.String> getSections() {
            return this.mSections;
        }

        void addTimeFrame(com.android.server.power.stats.PowerStatsSpan.TimeFrame timeFrame) {
            this.mTimeFrames.add(timeFrame);
        }

        void addSection(java.lang.String str) {
            if (!this.mSections.contains(str)) {
                this.mSections.add(str);
            }
        }

        void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_TAG_METADATA);
            typedXmlSerializer.attributeLong((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_ID, this.mId);
            typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_VERSION, 2);
            java.util.Iterator<com.android.server.power.stats.PowerStatsSpan.TimeFrame> it = this.mTimeFrames.iterator();
            while (it.hasNext()) {
                it.next().write(typedXmlSerializer);
            }
            for (java.lang.String str : this.mSections) {
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_TAG_SECTION);
                typedXmlSerializer.attribute((java.lang.String) null, "type", str);
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_TAG_SECTION);
            }
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_TAG_METADATA);
        }

        @android.annotation.Nullable
        public static com.android.server.power.stats.PowerStatsSpan.Metadata read(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int eventType = typedXmlPullParser.getEventType();
            com.android.server.power.stats.PowerStatsSpan.Metadata metadata = null;
            while (eventType != 1 && (eventType != 3 || !typedXmlPullParser.getName().equals(com.android.server.power.stats.PowerStatsSpan.XML_TAG_METADATA))) {
                if (eventType == 2) {
                    java.lang.String name = typedXmlPullParser.getName();
                    if (name.equals(com.android.server.power.stats.PowerStatsSpan.XML_TAG_METADATA)) {
                        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_VERSION);
                        if (!com.android.server.power.stats.PowerStatsSpan.isCompatibleXmlFormat(attributeInt)) {
                            android.util.Slog.e(com.android.server.power.stats.PowerStatsSpan.TAG, "Incompatible version " + attributeInt + "; expected 2");
                            return null;
                        }
                        metadata = new com.android.server.power.stats.PowerStatsSpan.Metadata(typedXmlPullParser.getAttributeLong((java.lang.String) null, com.android.server.power.stats.PowerStatsSpan.XML_ATTR_ID));
                    } else if (metadata != null && name.equals(com.android.server.power.stats.PowerStatsSpan.XML_TAG_TIMEFRAME)) {
                        metadata.addTimeFrame(com.android.server.power.stats.PowerStatsSpan.TimeFrame.read(typedXmlPullParser));
                    } else if (metadata != null && name.equals(com.android.server.power.stats.PowerStatsSpan.XML_TAG_SECTION)) {
                        metadata.addSection(typedXmlPullParser.getAttributeValue((java.lang.String) null, "type"));
                    }
                }
                eventType = typedXmlPullParser.next();
            }
            return metadata;
        }

        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            dump(indentingPrintWriter, true);
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter, boolean z) {
            indentingPrintWriter.print("Span ");
            if (this.mTimeFrames.size() > 0) {
                this.mTimeFrames.get(0).dump(indentingPrintWriter);
                indentingPrintWriter.println();
            }
            for (int i = 1; i < this.mTimeFrames.size(); i++) {
                com.android.server.power.stats.PowerStatsSpan.TimeFrame timeFrame = this.mTimeFrames.get(i);
                indentingPrintWriter.print("     ");
                timeFrame.dump(indentingPrintWriter);
                indentingPrintWriter.println();
            }
            if (z) {
                indentingPrintWriter.increaseIndent();
                java.util.Iterator<java.lang.String> it = this.mSections.iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.print(com.android.server.power.stats.PowerStatsSpan.XML_TAG_SECTION, it.next());
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
            }
        }

        public java.lang.String toString() {
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(stringWriter);
            indentingPrintWriter.print(com.android.server.power.stats.PowerStatsSpan.XML_ATTR_ID, java.lang.Long.valueOf(this.mId));
            for (int i = 0; i < this.mTimeFrames.size(); i++) {
                com.android.server.power.stats.PowerStatsSpan.TimeFrame timeFrame = this.mTimeFrames.get(i);
                indentingPrintWriter.print("timeframe=[");
                timeFrame.dump(indentingPrintWriter);
                indentingPrintWriter.print("] ");
            }
            java.util.Iterator<java.lang.String> it = this.mSections.iterator();
            while (it.hasNext()) {
                indentingPrintWriter.print(com.android.server.power.stats.PowerStatsSpan.XML_TAG_SECTION, it.next());
            }
            indentingPrintWriter.flush();
            return stringWriter.toString().trim();
        }
    }

    public static abstract class Section {
        private final java.lang.String mType;

        abstract void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException;

        Section(java.lang.String str) {
            this.mType = str;
        }

        public java.lang.String getType() {
            return this.mType;
        }

        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println(this.mType);
        }
    }

    public PowerStatsSpan(long j) {
        this(new com.android.server.power.stats.PowerStatsSpan.Metadata(j));
    }

    private PowerStatsSpan(com.android.server.power.stats.PowerStatsSpan.Metadata metadata) {
        this.mSections = new java.util.ArrayList();
        this.mMetadata = metadata;
    }

    public com.android.server.power.stats.PowerStatsSpan.Metadata getMetadata() {
        return this.mMetadata;
    }

    public long getId() {
        return this.mMetadata.mId;
    }

    void addTimeFrame(long j, long j2, long j3) {
        this.mMetadata.mTimeFrames.add(new com.android.server.power.stats.PowerStatsSpan.TimeFrame(j, j2, j3));
    }

    void addSection(com.android.server.power.stats.PowerStatsSpan.Section section) {
        this.mMetadata.addSection(section.getType());
        this.mSections.add(section);
    }

    @android.annotation.NonNull
    public java.util.List<com.android.server.power.stats.PowerStatsSpan.Section> getSections() {
        return this.mSections;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCompatibleXmlFormat(int i) {
        return i == 2;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void writeXml(java.io.OutputStream outputStream, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        typedXmlSerializer.startDocument((java.lang.String) null, true);
        this.mMetadata.write(typedXmlSerializer);
        for (com.android.server.power.stats.PowerStatsSpan.Section section : this.mSections) {
            typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_SECTION);
            typedXmlSerializer.attribute((java.lang.String) null, "type", section.mType);
            section.write(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_SECTION);
        }
        typedXmlSerializer.endDocument();
    }

    @android.annotation.Nullable
    static com.android.server.power.stats.PowerStatsSpan read(java.io.InputStream inputStream, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.power.stats.PowerStatsSpan.SectionReader sectionReader, java.lang.String... strArr) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.util.ArraySet newArraySet = com.google.android.collect.Sets.newArraySet(strArr);
        boolean z = !newArraySet.isEmpty();
        typedXmlPullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        com.android.server.power.stats.PowerStatsSpan.Metadata read = com.android.server.power.stats.PowerStatsSpan.Metadata.read(typedXmlPullParser);
        if (read == null) {
            return null;
        }
        com.android.server.power.stats.PowerStatsSpan powerStatsSpan = new com.android.server.power.stats.PowerStatsSpan(read);
        int eventType = typedXmlPullParser.getEventType();
        boolean z2 = false;
        int i = 0;
        while (eventType != 1) {
            if (z2) {
                if (eventType == 3 && typedXmlPullParser.getName().equals(XML_TAG_SECTION)) {
                    i--;
                    if (i == 0) {
                        z2 = false;
                    }
                } else if (eventType == 2 && typedXmlPullParser.getName().equals(XML_TAG_SECTION)) {
                    i++;
                }
            } else if (eventType == 2) {
                java.lang.String name = typedXmlPullParser.getName();
                if (name.equals(XML_TAG_SECTION)) {
                    final java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "type");
                    if (!z || newArraySet.contains(attributeValue)) {
                        com.android.server.power.stats.PowerStatsSpan.Section read2 = sectionReader.read(attributeValue, typedXmlPullParser);
                        if (read2 == null) {
                            if (z) {
                                throw new org.xmlpull.v1.XmlPullParserException("Unsupported PowerStatsStore section type: " + attributeValue);
                            }
                            read2 = new com.android.server.power.stats.PowerStatsSpan.Section(attributeValue) { // from class: com.android.server.power.stats.PowerStatsSpan.1
                                @Override // com.android.server.power.stats.PowerStatsSpan.Section
                                public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
                                    indentingPrintWriter.println("Unsupported PowerStatsStore section type: " + attributeValue);
                                }

                                @Override // com.android.server.power.stats.PowerStatsSpan.Section
                                void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) {
                                }
                            };
                        }
                        powerStatsSpan.addSection(read2);
                    } else {
                        z2 = true;
                    }
                } else if (name.equals(XML_TAG_METADATA)) {
                    com.android.server.power.stats.PowerStatsSpan.Metadata.read(typedXmlPullParser);
                }
            } else {
                continue;
            }
            eventType = typedXmlPullParser.next();
        }
        return powerStatsSpan;
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mMetadata.dump(indentingPrintWriter, false);
        for (com.android.server.power.stats.PowerStatsSpan.Section section : this.mSections) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(section.mType);
            section.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
    }
}
