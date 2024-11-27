package android.media;

/* compiled from: SRTRenderer.java */
/* loaded from: classes2.dex */
class SRTTrack extends android.media.WebVttTrack {
    private static final int KEY_LOCAL_SETTING = 102;
    private static final int KEY_START_TIME = 7;
    private static final int KEY_STRUCT_TEXT = 16;
    private static final int MEDIA_TIMED_TEXT = 99;
    private static final java.lang.String TAG = "SRTTrack";
    private final android.os.Handler mEventHandler;

    SRTTrack(android.media.WebVttRenderingWidget webVttRenderingWidget, android.media.MediaFormat mediaFormat) {
        super(webVttRenderingWidget, mediaFormat);
        this.mEventHandler = null;
    }

    SRTTrack(android.os.Handler handler, android.media.MediaFormat mediaFormat) {
        super(null, mediaFormat);
        this.mEventHandler = handler;
    }

    @Override // android.media.SubtitleTrack
    protected void onData(android.media.SubtitleData subtitleData) {
        try {
            android.media.TextTrackCue textTrackCue = new android.media.TextTrackCue();
            textTrackCue.mStartTimeMs = subtitleData.getStartTimeUs() / 1000;
            textTrackCue.mEndTimeMs = (subtitleData.getStartTimeUs() + subtitleData.getDurationUs()) / 1000;
            java.lang.String[] split = new java.lang.String(subtitleData.getData(), android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8).split("\\r?\\n");
            textTrackCue.mLines = new android.media.TextTrackCueSpan[split.length][];
            int length = split.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                textTrackCue.mLines[i2] = new android.media.TextTrackCueSpan[]{new android.media.TextTrackCueSpan(split[i], -1L)};
                i++;
                i2++;
            }
            addCue(textTrackCue);
        } catch (java.io.UnsupportedEncodingException e) {
            android.util.Log.w(TAG, "subtitle data is not UTF-8 encoded: " + e);
        }
    }

    @Override // android.media.WebVttTrack, android.media.SubtitleTrack
    public void onData(byte[] bArr, boolean z, long j) {
        java.lang.String readLine;
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.ByteArrayInputStream(bArr), android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8));
            while (bufferedReader.readLine() != null && (readLine = bufferedReader.readLine()) != null) {
                android.media.TextTrackCue textTrackCue = new android.media.TextTrackCue();
                java.lang.String[] split = readLine.split("-->");
                textTrackCue.mStartTimeMs = parseMs(split[0]);
                textTrackCue.mEndTimeMs = parseMs(split[1]);
                textTrackCue.mRunID = j;
                java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList();
                while (true) {
                    java.lang.String readLine2 = bufferedReader.readLine();
                    if (readLine2 == null || readLine2.trim().equals("")) {
                        break;
                    } else {
                        arrayList.add(readLine2);
                    }
                }
                textTrackCue.mLines = new android.media.TextTrackCueSpan[arrayList.size()][];
                textTrackCue.mStrings = (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
                int i = 0;
                for (java.lang.String str : arrayList) {
                    android.media.TextTrackCueSpan[] textTrackCueSpanArr = {new android.media.TextTrackCueSpan(str, -1L)};
                    textTrackCue.mStrings[i] = str;
                    textTrackCue.mLines[i] = textTrackCueSpanArr;
                    i++;
                }
                addCue(textTrackCue);
            }
        } catch (java.io.UnsupportedEncodingException e) {
            android.util.Log.w(TAG, "subtitle data is not UTF-8 encoded: " + e);
        } catch (java.io.IOException e2) {
            android.util.Log.e(TAG, e2.getMessage(), e2);
        }
    }

    @Override // android.media.WebVttTrack, android.media.SubtitleTrack
    public void updateView(java.util.Vector<android.media.SubtitleTrack.Cue> vector) {
        if (getRenderingWidget() != null) {
            super.updateView(vector);
            return;
        }
        if (this.mEventHandler == null) {
            return;
        }
        java.util.Iterator<android.media.SubtitleTrack.Cue> it = vector.iterator();
        while (it.hasNext()) {
            android.media.SubtitleTrack.Cue next = it.next();
            android.media.TextTrackCue textTrackCue = (android.media.TextTrackCue) next;
            android.os.Parcel obtain = android.os.Parcel.obtain();
            obtain.writeInt(102);
            obtain.writeInt(7);
            obtain.writeInt((int) next.mStartTimeMs);
            obtain.writeInt(16);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (java.lang.String str : textTrackCue.mStrings) {
                sb.append(str).append('\n');
            }
            byte[] bytes = sb.toString().getBytes();
            obtain.writeInt(bytes.length);
            obtain.writeByteArray(bytes);
            this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(99, 0, 0, obtain));
        }
        vector.clear();
    }

    private static long parseMs(java.lang.String str) {
        return (java.lang.Long.parseLong(str.split(":")[0].trim()) * 60 * 60 * 1000) + (java.lang.Long.parseLong(str.split(":")[1].trim()) * 60 * 1000) + (java.lang.Long.parseLong(str.split(":")[2].split(",")[0].trim()) * 1000) + java.lang.Long.parseLong(str.split(":")[2].split(",")[1].trim());
    }
}
