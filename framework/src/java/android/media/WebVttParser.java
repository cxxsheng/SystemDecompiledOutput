package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class WebVttParser {
    private static final java.lang.String TAG = "WebVttParser";
    private android.media.TextTrackCue mCue;
    private android.media.WebVttCueListener mListener;
    private final android.media.WebVttParser.Phase mSkipRest = new android.media.WebVttParser.Phase() { // from class: android.media.WebVttParser.1
        @Override // android.media.WebVttParser.Phase
        public void parse(java.lang.String str) {
        }
    };
    private final android.media.WebVttParser.Phase mParseStart = new android.media.WebVttParser.Phase() { // from class: android.media.WebVttParser.2
        @Override // android.media.WebVttParser.Phase
        public void parse(java.lang.String str) {
            if (str.startsWith("\ufeff")) {
                str = str.substring(1);
            }
            if (!str.equals("WEBVTT") && !str.startsWith("WEBVTT ") && !str.startsWith("WEBVTT\t")) {
                android.media.WebVttParser.this.log_warning("Not a WEBVTT header", str);
                android.media.WebVttParser.this.mPhase = android.media.WebVttParser.this.mSkipRest;
            } else {
                android.media.WebVttParser.this.mPhase = android.media.WebVttParser.this.mParseHeader;
            }
        }
    };
    private final android.media.WebVttParser.Phase mParseHeader = new android.media.WebVttParser.Phase() { // from class: android.media.WebVttParser.3
        static final /* synthetic */ boolean $assertionsDisabled = false;

        android.media.TextTrackRegion parseRegion(java.lang.String str) {
            android.media.TextTrackRegion textTrackRegion = new android.media.TextTrackRegion();
            for (java.lang.String str2 : str.split(" +")) {
                int indexOf = str2.indexOf(61);
                if (indexOf > 0 && indexOf != str2.length() - 1) {
                    java.lang.String substring = str2.substring(0, indexOf);
                    java.lang.String substring2 = str2.substring(indexOf + 1);
                    if (substring.equals("id")) {
                        textTrackRegion.mId = substring2;
                    } else if (substring.equals("width")) {
                        try {
                            textTrackRegion.mWidth = android.media.WebVttParser.parseFloatPercentage(substring2);
                        } catch (java.lang.NumberFormatException e) {
                            android.media.WebVttParser.this.log_warning("region setting", substring, "has invalid value", e.getMessage(), substring2);
                        }
                    } else if (substring.equals("lines")) {
                        if (substring2.matches(".*[^0-9].*")) {
                            android.media.WebVttParser.this.log_warning("lines", substring, "contains an invalid character", substring2);
                        } else {
                            try {
                                textTrackRegion.mLines = java.lang.Integer.parseInt(substring2);
                            } catch (java.lang.NumberFormatException e2) {
                                android.media.WebVttParser.this.log_warning("region setting", substring, "is not numeric", substring2);
                            }
                        }
                    } else if (substring.equals("regionanchor") || substring.equals("viewportanchor")) {
                        int indexOf2 = substring2.indexOf(",");
                        if (indexOf2 < 0) {
                            android.media.WebVttParser.this.log_warning("region setting", substring, "contains no comma", substring2);
                        } else {
                            java.lang.String substring3 = substring2.substring(0, indexOf2);
                            java.lang.String substring4 = substring2.substring(indexOf2 + 1);
                            try {
                                float parseFloatPercentage = android.media.WebVttParser.parseFloatPercentage(substring3);
                                try {
                                    float parseFloatPercentage2 = android.media.WebVttParser.parseFloatPercentage(substring4);
                                    if (substring.charAt(0) == 'r') {
                                        textTrackRegion.mAnchorPointX = parseFloatPercentage;
                                        textTrackRegion.mAnchorPointY = parseFloatPercentage2;
                                    } else {
                                        textTrackRegion.mViewportAnchorPointX = parseFloatPercentage;
                                        textTrackRegion.mViewportAnchorPointY = parseFloatPercentage2;
                                    }
                                } catch (java.lang.NumberFormatException e3) {
                                    android.media.WebVttParser.this.log_warning("region setting", substring, "has invalid y component", e3.getMessage(), substring4);
                                }
                            } catch (java.lang.NumberFormatException e4) {
                                android.media.WebVttParser.this.log_warning("region setting", substring, "has invalid x component", e4.getMessage(), substring3);
                            }
                        }
                    } else if (substring.equals("scroll")) {
                        if (substring2.equals(android.media.MediaMetrics.Value.UP)) {
                            textTrackRegion.mScrollValue = 301;
                        } else {
                            android.media.WebVttParser.this.log_warning("region setting", substring, "has invalid value", substring2);
                        }
                    }
                }
            }
            return textTrackRegion;
        }

        @Override // android.media.WebVttParser.Phase
        public void parse(java.lang.String str) {
            if (str.length() == 0) {
                android.media.WebVttParser.this.mPhase = android.media.WebVttParser.this.mParseCueId;
                return;
            }
            if (str.contains("-->")) {
                android.media.WebVttParser.this.mPhase = android.media.WebVttParser.this.mParseCueTime;
                android.media.WebVttParser.this.mPhase.parse(str);
                return;
            }
            int indexOf = str.indexOf(58);
            if (indexOf <= 0 || indexOf >= str.length() - 1) {
                android.media.WebVttParser.this.log_warning("meta data header has invalid format", str);
            }
            java.lang.String substring = str.substring(0, indexOf);
            java.lang.String substring2 = str.substring(indexOf + 1);
            if (substring.equals("Region")) {
                android.media.WebVttParser.this.mListener.onRegionParsed(parseRegion(substring2));
            }
        }
    };
    private final android.media.WebVttParser.Phase mParseCueId = new android.media.WebVttParser.Phase() { // from class: android.media.WebVttParser.4
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Override // android.media.WebVttParser.Phase
        public void parse(java.lang.String str) {
            if (str.length() == 0) {
                return;
            }
            if (str.equals("NOTE") || str.startsWith("NOTE ")) {
                android.media.WebVttParser.this.mPhase = android.media.WebVttParser.this.mParseCueText;
            }
            android.media.WebVttParser.this.mCue = new android.media.TextTrackCue();
            android.media.WebVttParser.this.mCueTexts.clear();
            android.media.WebVttParser.this.mPhase = android.media.WebVttParser.this.mParseCueTime;
            if (str.contains("-->")) {
                android.media.WebVttParser.this.mPhase.parse(str);
            } else {
                android.media.WebVttParser.this.mCue.mId = str;
            }
        }
    };
    private final android.media.WebVttParser.Phase mParseCueTime = new android.media.WebVttParser.Phase() { // from class: android.media.WebVttParser.5
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Override // android.media.WebVttParser.Phase
        public void parse(java.lang.String str) {
            int indexOf = str.indexOf("-->");
            if (indexOf < 0) {
                android.media.WebVttParser.this.mCue = null;
                android.media.WebVttParser.this.mPhase = android.media.WebVttParser.this.mParseCueId;
                return;
            }
            java.lang.String trim = str.substring(0, indexOf).trim();
            java.lang.String replaceFirst = str.substring(indexOf + 3).replaceFirst("^\\s+", "").replaceFirst("\\s+", " ");
            int indexOf2 = replaceFirst.indexOf(32);
            java.lang.String substring = indexOf2 > 0 ? replaceFirst.substring(0, indexOf2) : replaceFirst;
            java.lang.String substring2 = indexOf2 > 0 ? replaceFirst.substring(indexOf2 + 1) : "";
            android.media.WebVttParser.this.mCue.mStartTimeMs = android.media.WebVttParser.parseTimestampMs(trim);
            android.media.WebVttParser.this.mCue.mEndTimeMs = android.media.WebVttParser.parseTimestampMs(substring);
            for (java.lang.String str2 : substring2.split(" +")) {
                int indexOf3 = str2.indexOf(58);
                if (indexOf3 > 0 && indexOf3 != str2.length() - 1) {
                    java.lang.String substring3 = str2.substring(0, indexOf3);
                    java.lang.String substring4 = str2.substring(indexOf3 + 1);
                    if (substring3.equals(android.media.TtmlUtils.TAG_REGION)) {
                        android.media.WebVttParser.this.mCue.mRegionId = substring4;
                    } else if (substring3.equals("vertical")) {
                        if (substring4.equals("rl")) {
                            android.media.WebVttParser.this.mCue.mWritingDirection = 101;
                        } else if (substring4.equals("lr")) {
                            android.media.WebVttParser.this.mCue.mWritingDirection = 102;
                        } else {
                            android.media.WebVttParser.this.log_warning("cue setting", substring3, "has invalid value", substring4);
                        }
                    } else if (substring3.equals("line")) {
                        try {
                            if (substring4.endsWith("%")) {
                                android.media.WebVttParser.this.mCue.mSnapToLines = false;
                                android.media.WebVttParser.this.mCue.mLinePosition = java.lang.Integer.valueOf(android.media.WebVttParser.parseIntPercentage(substring4));
                            } else if (!substring4.matches(".*[^0-9].*")) {
                                android.media.WebVttParser.this.mCue.mSnapToLines = true;
                                android.media.WebVttParser.this.mCue.mLinePosition = java.lang.Integer.valueOf(java.lang.Integer.parseInt(substring4));
                            } else {
                                android.media.WebVttParser.this.log_warning("cue setting", substring3, "contains an invalid character", substring4);
                            }
                        } catch (java.lang.NumberFormatException e) {
                            android.media.WebVttParser.this.log_warning("cue setting", substring3, "is not numeric or percentage", substring4);
                        }
                    } else if (substring3.equals("position")) {
                        try {
                            android.media.WebVttParser.this.mCue.mTextPosition = android.media.WebVttParser.parseIntPercentage(substring4);
                        } catch (java.lang.NumberFormatException e2) {
                            android.media.WebVttParser.this.log_warning("cue setting", substring3, "is not numeric or percentage", substring4);
                        }
                    } else if (substring3.equals("size")) {
                        try {
                            android.media.WebVttParser.this.mCue.mSize = android.media.WebVttParser.parseIntPercentage(substring4);
                        } catch (java.lang.NumberFormatException e3) {
                            android.media.WebVttParser.this.log_warning("cue setting", substring3, "is not numeric or percentage", substring4);
                        }
                    } else if (substring3.equals("align")) {
                        if (substring4.equals("start")) {
                            android.media.WebVttParser.this.mCue.mAlignment = 201;
                        } else if (substring4.equals("middle")) {
                            android.media.WebVttParser.this.mCue.mAlignment = 200;
                        } else if (substring4.equals("end")) {
                            android.media.WebVttParser.this.mCue.mAlignment = 202;
                        } else if (substring4.equals(android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT)) {
                            android.media.WebVttParser.this.mCue.mAlignment = 203;
                        } else if (substring4.equals(android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT)) {
                            android.media.WebVttParser.this.mCue.mAlignment = 204;
                        } else {
                            android.media.WebVttParser.this.log_warning("cue setting", substring3, "has invalid value", substring4);
                        }
                    }
                }
            }
            if (android.media.WebVttParser.this.mCue.mLinePosition != null || android.media.WebVttParser.this.mCue.mSize != 100 || android.media.WebVttParser.this.mCue.mWritingDirection != 100) {
                android.media.WebVttParser.this.mCue.mRegionId = "";
            }
            android.media.WebVttParser.this.mPhase = android.media.WebVttParser.this.mParseCueText;
        }
    };
    private final android.media.WebVttParser.Phase mParseCueText = new android.media.WebVttParser.Phase() { // from class: android.media.WebVttParser.6
        @Override // android.media.WebVttParser.Phase
        public void parse(java.lang.String str) {
            if (str.length() == 0) {
                android.media.WebVttParser.this.yieldCue();
                android.media.WebVttParser.this.mPhase = android.media.WebVttParser.this.mParseCueId;
            } else if (android.media.WebVttParser.this.mCue != null) {
                android.media.WebVttParser.this.mCueTexts.add(str);
            }
        }
    };
    private android.media.WebVttParser.Phase mPhase = this.mParseStart;
    private java.lang.String mBuffer = "";
    private java.util.Vector<java.lang.String> mCueTexts = new java.util.Vector<>();

    /* compiled from: WebVttRenderer.java */
    interface Phase {
        void parse(java.lang.String str);
    }

    WebVttParser(android.media.WebVttCueListener webVttCueListener) {
        this.mListener = webVttCueListener;
    }

    public static float parseFloatPercentage(java.lang.String str) throws java.lang.NumberFormatException {
        if (!str.endsWith("%")) {
            throw new java.lang.NumberFormatException("does not end in %");
        }
        java.lang.String substring = str.substring(0, str.length() - 1);
        if (substring.matches(".*[^0-9.].*")) {
            throw new java.lang.NumberFormatException("contains an invalid character");
        }
        try {
            float parseFloat = java.lang.Float.parseFloat(substring);
            if (parseFloat < 0.0f || parseFloat > 100.0f) {
                throw new java.lang.NumberFormatException("is out of range");
            }
            return parseFloat;
        } catch (java.lang.NumberFormatException e) {
            throw new java.lang.NumberFormatException("is not a number");
        }
    }

    public static int parseIntPercentage(java.lang.String str) throws java.lang.NumberFormatException {
        if (!str.endsWith("%")) {
            throw new java.lang.NumberFormatException("does not end in %");
        }
        java.lang.String substring = str.substring(0, str.length() - 1);
        if (substring.matches(".*[^0-9].*")) {
            throw new java.lang.NumberFormatException("contains an invalid character");
        }
        try {
            int parseInt = java.lang.Integer.parseInt(substring);
            if (parseInt < 0 || parseInt > 100) {
                throw new java.lang.NumberFormatException("is out of range");
            }
            return parseInt;
        } catch (java.lang.NumberFormatException e) {
            throw new java.lang.NumberFormatException("is not a number");
        }
    }

    public static long parseTimestampMs(java.lang.String str) throws java.lang.NumberFormatException {
        if (!str.matches("(\\d+:)?[0-5]\\d:[0-5]\\d\\.\\d{3}")) {
            throw new java.lang.NumberFormatException("has invalid format");
        }
        java.lang.String[] split = str.split("\\.", 2);
        long j = 0;
        for (java.lang.String str2 : split[0].split(":")) {
            j = (j * 60) + java.lang.Long.parseLong(str2);
        }
        return (j * 1000) + java.lang.Long.parseLong(split[1]);
    }

    public static java.lang.String timeToString(long j) {
        return java.lang.String.format("%d:%02d:%02d.%03d", java.lang.Long.valueOf(j / 3600000), java.lang.Long.valueOf((j / 60000) % 60), java.lang.Long.valueOf((j / 1000) % 60), java.lang.Long.valueOf(j % 1000));
    }

    public void parse(java.lang.String str) {
        boolean z;
        this.mBuffer = (this.mBuffer + str.replace("\u0000", "�")).replace("\r\n", "\n");
        if (!this.mBuffer.endsWith("\r")) {
            z = false;
        } else {
            this.mBuffer = this.mBuffer.substring(0, this.mBuffer.length() - 1);
            z = true;
        }
        java.lang.String[] split = this.mBuffer.split("[\r\n]");
        for (int i = 0; i < split.length - 1; i++) {
            this.mPhase.parse(split[i]);
        }
        this.mBuffer = split[split.length - 1];
        if (z) {
            this.mBuffer += "\r";
        }
    }

    public void eos() {
        if (this.mBuffer.endsWith("\r")) {
            this.mBuffer = this.mBuffer.substring(0, this.mBuffer.length() - 1);
        }
        this.mPhase.parse(this.mBuffer);
        this.mBuffer = "";
        yieldCue();
        this.mPhase = this.mParseStart;
    }

    public void yieldCue() {
        if (this.mCue != null && this.mCueTexts.size() > 0) {
            this.mCue.mStrings = new java.lang.String[this.mCueTexts.size()];
            this.mCueTexts.toArray(this.mCue.mStrings);
            this.mCueTexts.clear();
            this.mListener.onCueParsed(this.mCue);
        }
        this.mCue = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log_warning(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        android.util.Log.w(getClass().getName(), str + " '" + str2 + "' " + str3 + " ('" + str5 + "' " + str4 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log_warning(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        android.util.Log.w(getClass().getName(), str + " '" + str2 + "' " + str3 + " ('" + str4 + "')");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log_warning(java.lang.String str, java.lang.String str2) {
        android.util.Log.w(getClass().getName(), str + " ('" + str2 + "')");
    }
}
