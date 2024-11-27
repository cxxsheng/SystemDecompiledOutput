package android.media;

/* loaded from: classes2.dex */
public class DecoderCapabilities {

    public enum AudioDecoder {
        AUDIO_DECODER_WMA
    }

    public enum VideoDecoder {
        VIDEO_DECODER_WMV
    }

    private static final native int native_get_audio_decoder_type(int i);

    private static final native int native_get_num_audio_decoders();

    private static final native int native_get_num_video_decoders();

    private static final native int native_get_video_decoder_type(int i);

    private static final native void native_init();

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }

    public static java.util.List<android.media.DecoderCapabilities.VideoDecoder> getVideoDecoders() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int native_get_num_video_decoders = native_get_num_video_decoders();
        for (int i = 0; i < native_get_num_video_decoders; i++) {
            arrayList.add(android.media.DecoderCapabilities.VideoDecoder.values()[native_get_video_decoder_type(i)]);
        }
        return arrayList;
    }

    public static java.util.List<android.media.DecoderCapabilities.AudioDecoder> getAudioDecoders() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int native_get_num_audio_decoders = native_get_num_audio_decoders();
        for (int i = 0; i < native_get_num_audio_decoders; i++) {
            arrayList.add(android.media.DecoderCapabilities.AudioDecoder.values()[native_get_audio_decoder_type(i)]);
        }
        return arrayList;
    }

    private DecoderCapabilities() {
    }
}
