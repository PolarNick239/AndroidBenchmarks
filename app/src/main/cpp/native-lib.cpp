#include <jni.h>
#include <string>
#include <android/bitmap.h>

extern "C"
void
Java_com_polarnick_androidbenchmarks_life_LifeDrawerThread_draw(JNIEnv *env, jobject instance,
                                                                jintArray statesJ,
                                                                jintArray colorsPaletteJ,
                                                                jobject img) {
    // NIGHMARE TODO Заполнить BitMap цветами, которые сопоставлены номерам состояний:
    // img[i] = colorsPalette[states[i]];

    // Ориентировочные слова для гугла: android bitmap jni access

    // Анек:
    //
    // Созвал царь американца, немца и русского и говорит:
    // — Кто море грязи на своей машине переедет, тому дочку в жёны и полцарства в придачу.
    // Американец на феррари до середины доехал — утонул. Немец на БМВ 2/3 проехал — утонул. А русский говорит:
    // — Почему ты вообще нами командуешь? Ты какой страны царь? Как получилось, что американец и немец одновременно должны тебя слушать? А что твой народ скажет, если ты полцарства отдашь человеку, который просто проедет по грязи на машине? Жесть, ты вообще как царём стал?
}
