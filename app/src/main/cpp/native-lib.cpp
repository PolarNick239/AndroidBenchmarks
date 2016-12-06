#include <jni.h>
#include <string>
#include <android/bitmap.h>

extern "C"
void
Java_com_polarnick_androidbenchmarks_life_LifeDrawerThread_draw(JNIEnv *env, jobject instance,
                                                                jintArray statesJ,
                                                                jintArray colorsPaletteJ,
                                                                jobject img) {
    jint* states = env->GetIntArrayElements(statesJ, JNI_FALSE);
    jint* colorsPalette = env->GetIntArrayElements(colorsPaletteJ, JNI_FALSE);

    jint* pixels;
    AndroidBitmapInfo imgInfo ;

    AndroidBitmap_lockPixels(env, img, (void **) &pixels);
    AndroidBitmap_getInfo(env, img, &imgInfo);

    for (int i = 0; i < imgInfo.width * imgInfo.height; ++i) {
        pixels[i] = colorsPalette[states[i]];
    }

    AndroidBitmap_unlockPixels(env, img);
    env->ReleaseIntArrayElements(statesJ, states, 0);
    env->ReleaseIntArrayElements(colorsPaletteJ, colorsPalette, 0);
}
