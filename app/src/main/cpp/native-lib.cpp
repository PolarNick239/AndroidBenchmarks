#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_polarnick_androidbenchmarks_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
void
Java_com_polarnick_androidbenchmarks_life_updaters_NativeUpdater_updatePart(JNIEnv *env,
                                                                            jobject instance,
                                                                            jintArray curJ, jintArray nextJ,
                                                                            jint width, jint height, jint n,
                                                                            jint row0, jint row1, jint col0, jint col1) {
    jint* cur = env->GetIntArrayElements(curJ, JNI_FALSE);
    jint* next = env->GetIntArrayElements(nextJ, JNI_FALSE);

    int dx[] = {-1, 0, 1, 1, 1, 0, -1, -1};
    int dy[] = {-1, -1, -1, 0, 1, 1, 1, 0};

    // Tunes:
    // 1. Remove branching (corner cases to separate loops)
    // 2. Calculate next state once (not for every neighbour)
    // 3. Break on first succeeding
    for (int y = std::max(1, row0); y < std::min(height - 1, row1); ++y) {
        for (int x = std::max(1, col0); x < std::min(width - 1, col1); ++x) {
            bool succeeded = false;

            int toSucceed = (cur[width * y + x] + 1) % n;
            for (int i = 0; i < 8; ++i) {
                if (cur[width * (y + dy[i]) + x + dx[i]] == toSucceed) {
                    succeeded = true;
                    break;
                }
            }

            if (succeeded) {
                next[width * y + x] = toSucceed;
            } else {
                next[width * y + x] = cur[width * y + x];
            }
        }
    }

    // Corner cases
    for (int y = 0; y <= height - 1; y += height - 1) {
        for (int x = 0; x <= width - 1; x += width - 1) {
            bool succeeded = false;

            for (int i = 0; i < 8; ++i) {
                if (x + dx[i] < 0 || x + dx[i] >= width || y + dy[i] < 0 || y + dy[i] >= height) {
                    continue;
                }
                if (cur[width * (y + dy[i]) + x + dx[i]] == (cur[width * y + x] + 1) % n) {
                    succeeded = true;
                }
            }

            if (succeeded) {
                next[width * y + x] = (cur[width * y + x] + 1) % n;
            } else {
                next[width * y + x] = cur[width * y + x];
            }
        }
    }

    env->ReleaseIntArrayElements(curJ, cur, 0);
    env->ReleaseIntArrayElements(nextJ, next, 0);
}
