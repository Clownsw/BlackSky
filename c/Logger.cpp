//
// By: smilex
//

#include "Interface.h"
#include "Tools.h"
#include <spdlog/spdlog.h>
#include <spdlog/sinks/stdout_color_sinks.h>
#include <spdlog/sinks/basic_file_sink.h>

inline static std::shared_ptr<spdlog::logger> realConsoleLogger = spdlog::stdout_color_mt("console");
inline static std::shared_ptr<spdlog::logger> realFileLogger;
inline static bool isEnableFileLogger = false;

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    log
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_log
    (JNIEnv* env, jobject obj, jint loglevel, jstring message) {

    const char * msg = env->GetStringUTFChars(message, JNI_FALSE);

    switch (loglevel) {
        default:
        case 1: {
            realConsoleLogger->info(msg);
            if (isEnableFileLogger) {
                realFileLogger->info(msg);
                realFileLogger->flush();
            }
            break;
        }

        case 2: {
            realConsoleLogger->warn(msg);
            if (isEnableFileLogger) {
                realFileLogger->warn(msg);
                realFileLogger->flush();
            }
            break;
        }
    }
    env->DeleteLocalRef(message);
}

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    flush
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_flush
    (JNIEnv* env, jobject obj) {

    realFileLogger->flush();

    env->DeleteLocalRef(obj);
}

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    createFileLogger
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_createFileLogger
    (JNIEnv* env, jobject obj, jstring loggerName, jstring fileName) {

    const char * _loggerName = env->GetStringUTFChars(loggerName, JNI_FALSE);
    const char * _fileName = env->GetStringUTFChars(fileName, JNI_FALSE);

    try {
        realFileLogger = spdlog::basic_logger_mt(_loggerName, _fileName);
    } catch (const spdlog::spdlog_ex &ex) {
        throwException(env, CLASSNAME_RuntimeException, ex.what());
    }

    isEnableFileLogger = true;

    env->DeleteLocalRef(obj);

}