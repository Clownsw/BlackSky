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
        case SPDLOG_LEVEL_TRACE: {
            realConsoleLogger->trace(msg);
            if (isEnableFileLogger) {
                realFileLogger->trace(msg);
            }
            break;
        }

        case SPDLOG_LEVEL_DEBUG: {
            realConsoleLogger->debug(msg);
            if (isEnableFileLogger) {
                realFileLogger->debug(msg);
            }
            break;
        }

        case SPDLOG_LEVEL_INFO: {
            realConsoleLogger->info(msg);
            if (isEnableFileLogger) {
                realFileLogger->info(msg);
            }
            break;
        }

        case SPDLOG_LEVEL_WARN: {
            realConsoleLogger->warn(msg);
            if (isEnableFileLogger) {
                realFileLogger->warn(msg);
            }
            break;
        }

        case SPDLOG_LEVEL_ERROR: {
            realConsoleLogger->error(msg);
            if (isEnableFileLogger) {
                realFileLogger->error(msg);
            }
            break;
        }

        case SPDLOG_LEVEL_CRITICAL: {
            realConsoleLogger->critical(msg);
            if (isEnableFileLogger) {
                realFileLogger->critical(msg);
            }
            break;
        }
    }

    env->DeleteLocalRef(obj);
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
        realFileLogger = spdlog::basic_logger_mt(_loggerName, _fileName, true);
        spdlog::shutdown();
    } catch (const spdlog::spdlog_ex &ex) {
        throwException(env, CLASSNAME_RuntimeException, ex.what());
    }

    isEnableFileLogger = true;

    env->DeleteLocalRef(obj);
    env->DeleteLocalRef(loggerName);
    env->DeleteLocalRef(fileName);

}

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    set_pattern
 * Signature: (Ljava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_set_1pattern
    (JNIEnv* env, jobject obj, jboolean isFileLogger, jstring pattern, jint pattern_type) {

    const char* patternBuffer = env->GetStringUTFChars(pattern, JNI_FALSE);

    switch (pattern_type) {

        default:
        case int(spdlog::pattern_time_type::local) : {
            if ((int)isFileLogger) {
                realFileLogger->set_pattern(patternBuffer);
            } else {
                realConsoleLogger->set_pattern(patternBuffer);
            }
            break;
        }

        case int(spdlog::pattern_time_type::utc) : {
            if ((int)isFileLogger) {
                realFileLogger->set_pattern(patternBuffer, spdlog::pattern_time_type::utc);
            } else {
                realConsoleLogger->set_pattern(patternBuffer, spdlog::pattern_time_type::utc);
            }
            break;
        }
    }

    env->DeleteLocalRef(obj);
    env->DeleteLocalRef(pattern);
}

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    set_level
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_set_1level
    (JNIEnv* env, jobject obj, jboolean isFileLogger, jint level) {

    switch (level) {
        default:
        case SPDLOG_LEVEL_TRACE: {
            if ((int)isFileLogger) {
                realFileLogger->set_level(spdlog::level::trace);
            } else {
                realConsoleLogger->set_level(spdlog::level::trace);
            }
            break;
        }

        case SPDLOG_LEVEL_DEBUG: {
            if ((int)isFileLogger) {
                realFileLogger->set_level(spdlog::level::debug);
            } else {
                realConsoleLogger->set_level(spdlog::level::debug);
            }
            break;
        }

        case SPDLOG_LEVEL_INFO: {
            if ((int)isFileLogger) {
                realFileLogger->set_level(spdlog::level::info);
            } else {
                realConsoleLogger->set_level(spdlog::level::info);
            }
            break;
        }

        case SPDLOG_LEVEL_WARN: {
            if ((int)isFileLogger) {
                realFileLogger->set_level(spdlog::level::warn);
            } else {
                realConsoleLogger->set_level(spdlog::level::warn);
            }
            break;
        }

        case SPDLOG_LEVEL_ERROR: {
            if ((int)isFileLogger) {
                realFileLogger->set_level(spdlog::level::err);
            } else {
                realConsoleLogger->set_level(spdlog::level::err);
            }
            break;
        }

        case SPDLOG_LEVEL_CRITICAL: {
            if ((int)isFileLogger) {
                realFileLogger->set_level(spdlog::level::critical);
            } else {
                realConsoleLogger->set_level(spdlog::level::critical);
            }
            break;
        }
    }

    env->DeleteLocalRef(obj);
}

/*
 * Class:     cn_smilex_libhv_jni_log_Logger
 * Method:    flush_on
 * Signature: (ZI)V
 */
JNIEXPORT void JNICALL Java_cn_smilex_libhv_jni_log_Logger_flush_1on
    (JNIEnv* env, jobject obj, jboolean isFileLogger, jint level) {
    switch (level) {
        default:
        case SPDLOG_LEVEL_TRACE: {
            if ((int)isFileLogger) {
                realFileLogger->flush_on(spdlog::level::trace);
            } else {
                realConsoleLogger->flush_on(spdlog::level::trace);
            }
            break;
        }

        case SPDLOG_LEVEL_DEBUG: {
            if ((int)isFileLogger) {
                realFileLogger->flush_on(spdlog::level::debug);
            } else {
                realConsoleLogger->flush_on(spdlog::level::debug);
            }
            break;
        }

        case SPDLOG_LEVEL_INFO: {
            if ((int)isFileLogger) {
                realFileLogger->flush_on(spdlog::level::info);
            } else {
                realConsoleLogger->flush_on(spdlog::level::info);
            }
            break;
        }

        case SPDLOG_LEVEL_WARN: {
            if ((int)isFileLogger) {
                realFileLogger->flush_on(spdlog::level::warn);
            } else {
                realConsoleLogger->flush_on(spdlog::level::warn);
            }
            break;
        }

        case SPDLOG_LEVEL_ERROR: {
            if ((int)isFileLogger) {
                realFileLogger->set_level(spdlog::level::err);
            } else {
                realConsoleLogger->flush_on(spdlog::level::err);
            }
            break;
        }

        case SPDLOG_LEVEL_CRITICAL: {
            if ((int)isFileLogger) {
                realFileLogger->flush_on(spdlog::level::critical);
            } else {
                realConsoleLogger->flush_on(spdlog::level::critical);
            }
            break;
        }
    }

    env->DeleteLocalRef(obj);
}