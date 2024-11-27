package com.google.android.mms;

/* loaded from: classes5.dex */
public class ContentType {
    public static final java.lang.String APP_DRM_CONTENT = "application/vnd.oma.drm.content";
    public static final java.lang.String APP_DRM_MESSAGE = "application/vnd.oma.drm.message";
    public static final java.lang.String APP_SMIL = "application/smil";
    public static final java.lang.String APP_WAP_XHTML = "application/vnd.wap.xhtml+xml";
    public static final java.lang.String APP_XHTML = "application/xhtml+xml";
    public static final java.lang.String AUDIO_3GPP = "audio/3gpp";
    public static final java.lang.String AUDIO_AAC = "audio/aac";
    public static final java.lang.String AUDIO_AMR = "audio/amr";
    public static final java.lang.String AUDIO_IMELODY = "audio/imelody";
    public static final java.lang.String AUDIO_MID = "audio/mid";
    public static final java.lang.String AUDIO_MIDI = "audio/midi";
    public static final java.lang.String AUDIO_MP3 = "audio/mp3";
    public static final java.lang.String AUDIO_MP4 = "audio/mp4";
    public static final java.lang.String AUDIO_MPEG = "audio/mpeg";
    public static final java.lang.String AUDIO_MPEG3 = "audio/mpeg3";
    public static final java.lang.String AUDIO_MPG = "audio/mpg";
    public static final java.lang.String AUDIO_OGG = "application/ogg";
    public static final java.lang.String AUDIO_OGG2 = "audio/ogg";
    public static final java.lang.String AUDIO_UNSPECIFIED = "audio/*";
    public static final java.lang.String AUDIO_X_MID = "audio/x-mid";
    public static final java.lang.String AUDIO_X_MIDI = "audio/x-midi";
    public static final java.lang.String AUDIO_X_MP3 = "audio/x-mp3";
    public static final java.lang.String AUDIO_X_MPEG = "audio/x-mpeg";
    public static final java.lang.String AUDIO_X_MPEG3 = "audio/x-mpeg3";
    public static final java.lang.String AUDIO_X_MPG = "audio/x-mpg";
    public static final java.lang.String AUDIO_X_WAV = "audio/x-wav";
    public static final java.lang.String IMAGE_GIF = "image/gif";
    public static final java.lang.String IMAGE_JPEG = "image/jpeg";
    public static final java.lang.String IMAGE_JPG = "image/jpg";
    public static final java.lang.String IMAGE_PNG = "image/png";
    public static final java.lang.String IMAGE_UNSPECIFIED = "image/*";
    public static final java.lang.String IMAGE_WBMP = "image/vnd.wap.wbmp";
    public static final java.lang.String IMAGE_X_MS_BMP = "image/x-ms-bmp";
    public static final java.lang.String MMS_GENERIC = "application/vnd.wap.mms-generic";
    public static final java.lang.String MMS_MESSAGE = "application/vnd.wap.mms-message";
    public static final java.lang.String MULTIPART_ALTERNATIVE = "application/vnd.wap.multipart.alternative";
    public static final java.lang.String MULTIPART_MIXED = "application/vnd.wap.multipart.mixed";
    public static final java.lang.String MULTIPART_RELATED = "application/vnd.wap.multipart.related";
    public static final java.lang.String TEXT_HTML = "text/html";
    public static final java.lang.String TEXT_PLAIN = "text/plain";
    public static final java.lang.String TEXT_VCALENDAR = "text/x-vCalendar";
    public static final java.lang.String TEXT_VCARD = "text/x-vCard";
    public static final java.lang.String VIDEO_3G2 = "video/3gpp2";
    public static final java.lang.String VIDEO_3GPP = "video/3gpp";
    public static final java.lang.String VIDEO_H263 = "video/h263";
    public static final java.lang.String VIDEO_MP4 = "video/mp4";
    public static final java.lang.String VIDEO_UNSPECIFIED = "video/*";
    private static final java.util.ArrayList<java.lang.String> sSupportedContentTypes = new java.util.ArrayList<>();
    private static final java.util.ArrayList<java.lang.String> sSupportedImageTypes = new java.util.ArrayList<>();
    private static final java.util.ArrayList<java.lang.String> sSupportedAudioTypes = new java.util.ArrayList<>();
    private static final java.util.ArrayList<java.lang.String> sSupportedVideoTypes = new java.util.ArrayList<>();

    static {
        sSupportedContentTypes.add("text/plain");
        sSupportedContentTypes.add("text/html");
        sSupportedContentTypes.add(TEXT_VCALENDAR);
        sSupportedContentTypes.add(TEXT_VCARD);
        sSupportedContentTypes.add(IMAGE_JPEG);
        sSupportedContentTypes.add(IMAGE_GIF);
        sSupportedContentTypes.add(IMAGE_WBMP);
        sSupportedContentTypes.add(IMAGE_PNG);
        sSupportedContentTypes.add(IMAGE_JPG);
        sSupportedContentTypes.add(IMAGE_X_MS_BMP);
        sSupportedContentTypes.add(AUDIO_AAC);
        sSupportedContentTypes.add(AUDIO_AMR);
        sSupportedContentTypes.add(AUDIO_IMELODY);
        sSupportedContentTypes.add(AUDIO_MID);
        sSupportedContentTypes.add(AUDIO_MIDI);
        sSupportedContentTypes.add(AUDIO_MP3);
        sSupportedContentTypes.add(AUDIO_MP4);
        sSupportedContentTypes.add(AUDIO_MPEG3);
        sSupportedContentTypes.add("audio/mpeg");
        sSupportedContentTypes.add(AUDIO_MPG);
        sSupportedContentTypes.add(AUDIO_X_MID);
        sSupportedContentTypes.add(AUDIO_X_MIDI);
        sSupportedContentTypes.add(AUDIO_X_MP3);
        sSupportedContentTypes.add(AUDIO_X_MPEG3);
        sSupportedContentTypes.add(AUDIO_X_MPEG);
        sSupportedContentTypes.add(AUDIO_X_MPG);
        sSupportedContentTypes.add(AUDIO_X_WAV);
        sSupportedContentTypes.add("audio/3gpp");
        sSupportedContentTypes.add(AUDIO_OGG);
        sSupportedContentTypes.add(AUDIO_OGG2);
        sSupportedContentTypes.add("video/3gpp");
        sSupportedContentTypes.add(VIDEO_3G2);
        sSupportedContentTypes.add(VIDEO_H263);
        sSupportedContentTypes.add(VIDEO_MP4);
        sSupportedContentTypes.add(APP_SMIL);
        sSupportedContentTypes.add(APP_WAP_XHTML);
        sSupportedContentTypes.add(APP_XHTML);
        sSupportedContentTypes.add(APP_DRM_CONTENT);
        sSupportedContentTypes.add("application/vnd.oma.drm.message");
        sSupportedImageTypes.add(IMAGE_JPEG);
        sSupportedImageTypes.add(IMAGE_GIF);
        sSupportedImageTypes.add(IMAGE_WBMP);
        sSupportedImageTypes.add(IMAGE_PNG);
        sSupportedImageTypes.add(IMAGE_JPG);
        sSupportedImageTypes.add(IMAGE_X_MS_BMP);
        sSupportedAudioTypes.add(AUDIO_AAC);
        sSupportedAudioTypes.add(AUDIO_AMR);
        sSupportedAudioTypes.add(AUDIO_IMELODY);
        sSupportedAudioTypes.add(AUDIO_MID);
        sSupportedAudioTypes.add(AUDIO_MIDI);
        sSupportedAudioTypes.add(AUDIO_MP3);
        sSupportedAudioTypes.add(AUDIO_MPEG3);
        sSupportedAudioTypes.add("audio/mpeg");
        sSupportedAudioTypes.add(AUDIO_MPG);
        sSupportedAudioTypes.add(AUDIO_MP4);
        sSupportedAudioTypes.add(AUDIO_X_MID);
        sSupportedAudioTypes.add(AUDIO_X_MIDI);
        sSupportedAudioTypes.add(AUDIO_X_MP3);
        sSupportedAudioTypes.add(AUDIO_X_MPEG3);
        sSupportedAudioTypes.add(AUDIO_X_MPEG);
        sSupportedAudioTypes.add(AUDIO_X_MPG);
        sSupportedAudioTypes.add(AUDIO_X_WAV);
        sSupportedAudioTypes.add("audio/3gpp");
        sSupportedAudioTypes.add(AUDIO_OGG);
        sSupportedAudioTypes.add(AUDIO_OGG2);
        sSupportedVideoTypes.add("video/3gpp");
        sSupportedVideoTypes.add(VIDEO_3G2);
        sSupportedVideoTypes.add(VIDEO_H263);
        sSupportedVideoTypes.add(VIDEO_MP4);
    }

    private ContentType() {
    }

    public static boolean isSupportedType(java.lang.String str) {
        return str != null && sSupportedContentTypes.contains(str);
    }

    public static boolean isSupportedImageType(java.lang.String str) {
        return isImageType(str) && isSupportedType(str);
    }

    public static boolean isSupportedAudioType(java.lang.String str) {
        return isAudioType(str) && isSupportedType(str);
    }

    public static boolean isSupportedVideoType(java.lang.String str) {
        return isVideoType(str) && isSupportedType(str);
    }

    public static boolean isTextType(java.lang.String str) {
        return str != null && str.startsWith("text/");
    }

    public static boolean isImageType(java.lang.String str) {
        return str != null && str.startsWith(com.android.internal.widget.MessagingMessage.IMAGE_MIME_TYPE_PREFIX);
    }

    public static boolean isAudioType(java.lang.String str) {
        return str != null && str.startsWith("audio/");
    }

    public static boolean isVideoType(java.lang.String str) {
        return str != null && str.startsWith("video/");
    }

    public static boolean isDrmType(java.lang.String str) {
        return str != null && (str.equals(APP_DRM_CONTENT) || str.equals("application/vnd.oma.drm.message"));
    }

    public static boolean isUnspecified(java.lang.String str) {
        return str != null && str.endsWith("*");
    }

    public static java.util.ArrayList<java.lang.String> getImageTypes() {
        return (java.util.ArrayList) sSupportedImageTypes.clone();
    }

    public static java.util.ArrayList<java.lang.String> getAudioTypes() {
        return (java.util.ArrayList) sSupportedAudioTypes.clone();
    }

    public static java.util.ArrayList<java.lang.String> getVideoTypes() {
        return (java.util.ArrayList) sSupportedVideoTypes.clone();
    }

    public static java.util.ArrayList<java.lang.String> getSupportedTypes() {
        return (java.util.ArrayList) sSupportedContentTypes.clone();
    }
}
