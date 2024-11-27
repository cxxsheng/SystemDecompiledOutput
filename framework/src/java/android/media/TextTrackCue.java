package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class TextTrackCue extends android.media.SubtitleTrack.Cue {
    static final int ALIGNMENT_END = 202;
    static final int ALIGNMENT_LEFT = 203;
    static final int ALIGNMENT_MIDDLE = 200;
    static final int ALIGNMENT_RIGHT = 204;
    static final int ALIGNMENT_START = 201;
    private static final java.lang.String TAG = "TTCue";
    static final int WRITING_DIRECTION_HORIZONTAL = 100;
    static final int WRITING_DIRECTION_VERTICAL_LR = 102;
    static final int WRITING_DIRECTION_VERTICAL_RL = 101;
    boolean mAutoLinePosition;
    java.lang.String[] mStrings;
    java.lang.String mId = "";
    boolean mPauseOnExit = false;
    int mWritingDirection = 100;
    java.lang.String mRegionId = "";
    boolean mSnapToLines = true;
    java.lang.Integer mLinePosition = null;
    int mTextPosition = 50;
    int mSize = 100;
    int mAlignment = 200;
    android.media.TextTrackCueSpan[][] mLines = null;
    android.media.TextTrackRegion mRegion = null;

    TextTrackCue() {
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.media.TextTrackCue)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        try {
            android.media.TextTrackCue textTrackCue = (android.media.TextTrackCue) obj;
            boolean z = this.mId.equals(textTrackCue.mId) && this.mPauseOnExit == textTrackCue.mPauseOnExit && this.mWritingDirection == textTrackCue.mWritingDirection && this.mRegionId.equals(textTrackCue.mRegionId) && this.mSnapToLines == textTrackCue.mSnapToLines && this.mAutoLinePosition == textTrackCue.mAutoLinePosition && (this.mAutoLinePosition || ((this.mLinePosition != null && this.mLinePosition.equals(textTrackCue.mLinePosition)) || (this.mLinePosition == null && textTrackCue.mLinePosition == null))) && this.mTextPosition == textTrackCue.mTextPosition && this.mSize == textTrackCue.mSize && this.mAlignment == textTrackCue.mAlignment && this.mLines.length == textTrackCue.mLines.length;
            if (z) {
                for (int i = 0; i < this.mLines.length; i++) {
                    if (!java.util.Arrays.equals(this.mLines[i], textTrackCue.mLines[i])) {
                        return false;
                    }
                }
            }
            return z;
        } catch (java.lang.IncompatibleClassChangeError e) {
            return false;
        }
    }

    public java.lang.StringBuilder appendStringsToBuilder(java.lang.StringBuilder sb) {
        if (this.mStrings == null) {
            sb.append("null");
        } else {
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            java.lang.String[] strArr = this.mStrings;
            int length = strArr.length;
            boolean z = true;
            int i = 0;
            while (i < length) {
                java.lang.String str = strArr[i];
                if (!z) {
                    sb.append(", ");
                }
                if (str == null) {
                    sb.append("null");
                } else {
                    sb.append("\"");
                    sb.append(str);
                    sb.append("\"");
                }
                i++;
                z = false;
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        return sb;
    }

    public java.lang.StringBuilder appendLinesToBuilder(java.lang.StringBuilder sb) {
        if (this.mLines == null) {
            sb.append("null");
        } else {
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            android.media.TextTrackCueSpan[][] textTrackCueSpanArr = this.mLines;
            int length = textTrackCueSpanArr.length;
            int i = 0;
            boolean z = true;
            while (i < length) {
                android.media.TextTrackCueSpan[] textTrackCueSpanArr2 = textTrackCueSpanArr[i];
                if (!z) {
                    sb.append(", ");
                }
                if (textTrackCueSpanArr2 == null) {
                    sb.append("null");
                } else {
                    sb.append("\"");
                    int length2 = textTrackCueSpanArr2.length;
                    long j = -1;
                    int i2 = 0;
                    boolean z2 = true;
                    while (i2 < length2) {
                        android.media.TextTrackCueSpan textTrackCueSpan = textTrackCueSpanArr2[i2];
                        if (!z2) {
                            sb.append(" ");
                        }
                        if (textTrackCueSpan.mTimestampMs != j) {
                            sb.append("<").append(android.media.WebVttParser.timeToString(textTrackCueSpan.mTimestampMs)).append(">");
                            j = textTrackCueSpan.mTimestampMs;
                        }
                        sb.append(textTrackCueSpan.mText);
                        i2++;
                        z2 = false;
                    }
                    sb.append("\"");
                }
                i++;
                z = false;
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        return sb;
    }

    public java.lang.String toString() {
        java.lang.String str;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.lang.StringBuilder append = sb.append(android.media.WebVttParser.timeToString(this.mStartTimeMs)).append(" --> ").append(android.media.WebVttParser.timeToString(this.mEndTimeMs)).append(" {id:\"").append(this.mId).append("\", pauseOnExit:").append(this.mPauseOnExit).append(", direction:");
        java.lang.String str2 = "INVALID";
        if (this.mWritingDirection == 100) {
            str = android.app.slice.Slice.HINT_HORIZONTAL;
        } else if (this.mWritingDirection == 102) {
            str = "vertical_lr";
        } else {
            str = this.mWritingDirection == 101 ? "vertical_rl" : "INVALID";
        }
        java.lang.StringBuilder append2 = append.append(str).append(", regionId:\"").append(this.mRegionId).append("\", snapToLines:").append(this.mSnapToLines).append(", linePosition:").append(this.mAutoLinePosition ? "auto" : this.mLinePosition).append(", textPosition:").append(this.mTextPosition).append(", size:").append(this.mSize).append(", alignment:");
        if (this.mAlignment == 202) {
            str2 = "end";
        } else if (this.mAlignment == 203) {
            str2 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT;
        } else if (this.mAlignment == 200) {
            str2 = "middle";
        } else if (this.mAlignment == 204) {
            str2 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT;
        } else if (this.mAlignment == 201) {
            str2 = "start";
        }
        append2.append(str2).append(", text:");
        appendStringsToBuilder(sb).append("}");
        return sb.toString();
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @Override // android.media.SubtitleTrack.Cue
    public void onTime(long j) {
        for (android.media.TextTrackCueSpan[] textTrackCueSpanArr : this.mLines) {
            for (android.media.TextTrackCueSpan textTrackCueSpan : textTrackCueSpanArr) {
                textTrackCueSpan.mEnabled = j >= textTrackCueSpan.mTimestampMs;
            }
        }
    }
}
