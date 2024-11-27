package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class AidlUtil {
    static android.media.soundtrigger.RecognitionEvent newEmptyRecognitionEvent() {
        android.media.soundtrigger.RecognitionEvent recognitionEvent = new android.media.soundtrigger.RecognitionEvent();
        recognitionEvent.data = new byte[0];
        return recognitionEvent;
    }

    static android.media.soundtrigger.PhraseRecognitionEvent newEmptyPhraseRecognitionEvent() {
        android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent = new android.media.soundtrigger.PhraseRecognitionEvent();
        phraseRecognitionEvent.common = newEmptyRecognitionEvent();
        phraseRecognitionEvent.phraseExtras = new android.media.soundtrigger.PhraseRecognitionExtra[0];
        return phraseRecognitionEvent;
    }

    static android.media.soundtrigger_middleware.RecognitionEventSys newAbortEvent() {
        android.media.soundtrigger.RecognitionEvent newEmptyRecognitionEvent = newEmptyRecognitionEvent();
        newEmptyRecognitionEvent.type = 1;
        newEmptyRecognitionEvent.status = 1;
        android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys = new android.media.soundtrigger_middleware.RecognitionEventSys();
        recognitionEventSys.recognitionEvent = newEmptyRecognitionEvent;
        return recognitionEventSys;
    }

    static android.media.soundtrigger_middleware.PhraseRecognitionEventSys newAbortPhraseEvent() {
        android.media.soundtrigger.PhraseRecognitionEvent newEmptyPhraseRecognitionEvent = newEmptyPhraseRecognitionEvent();
        newEmptyPhraseRecognitionEvent.common.type = 0;
        newEmptyPhraseRecognitionEvent.common.status = 1;
        android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys = new android.media.soundtrigger_middleware.PhraseRecognitionEventSys();
        phraseRecognitionEventSys.phraseRecognitionEvent = newEmptyPhraseRecognitionEvent;
        return phraseRecognitionEventSys;
    }
}
