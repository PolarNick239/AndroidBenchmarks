#include <jni.h>
#include <string>

extern "C"
void
Java_com_polarnick_androidbenchmarks_life_updaters_NativeUpdater_updatePart(
        JNIEnv *env,
        jobject instance,
        jintArray curJ, jintArray nextJ,
        jint width, jint height, jint n,
        jint row0, jint row1, jint col0, jint col1)
{
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


extern "C"
void
Java_com_polarnick_androidbenchmarks_life_updaters_TunedNativeUpdater_updatePartTuned(
        JNIEnv *env,
        jobject instance,
        jintArray curJ, jintArray nextJ,
        jint width, jint height, jint n,
        jint row0, jint row1, jint col0, jint col1)
{
    jint* cur = env->GetIntArrayElements(curJ, JNI_FALSE);
    jint* next = env->GetIntArrayElements(nextJ, JNI_FALSE);

    int dx[] = {-1, 0, 1, 1, 1, 0, -1, -1};
    int dy[] = {-1, -1, -1, 0, 1, 1, 1, 0};

    // Tunes:
    // 1. Remove branching (corner cases to separate loops)
    // 2. Calculate next state once (not for every neighbour)
    // 3. Break on first succeeding
    for (int y = std::max(1, row0); y < std::min(height - 1, row1); ++y) {
        const jint* curUp      = cur + width * (y - 1);
        const jint* curRow     = cur + width * (y + 0);
        const jint* curDown    = cur + width * (y + 1);

        jint* nextP            = next + width * y + 1;

        for (int x = std::max(1, col0); x < std::min(width - 1, col1); ++x) {
            int toSucceed = (curRow[1] + 1) % n;

            if (curUp[0] == toSucceed || curUp[1] == toSucceed || curUp[2] == toSucceed
                || curRow[0] == toSucceed || curRow[2] == toSucceed
                || curDown[0] == toSucceed || curDown[1] == toSucceed || curDown[2] == toSucceed) {
                *nextP = toSucceed;
            } else {
                *nextP = curRow[1];
            }

            ++curUp;
            ++curRow;
            ++curDown;
            ++nextP;
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
