//
// Created by Administrator on 2021/12/22.
//

#include "Interface.h"
#include "Tools.h"
#include <iostream>
#include <openssl/md5.h>

/*
 * Class:     cn_smilex_libhv_jni_ssl_MD5
 * Method:    getMD5
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_MD5_getMD5
(JNIEnv* env, jobject, jstring data, jint len) {

    std::string resultStr;
    unsigned char resultMd5[MD5_DIGEST_LENGTH];

    MD5(reinterpret_cast<const unsigned char *>(env->GetStringUTFChars(data, JNI_FALSE)), len, resultMd5);

    const char map[] = "0123456789abcdef";
    for (unsigned char i : resultMd5) {
        resultStr += map[i / 16];
        resultStr += map[i % 16];
    }

    env->DeleteLocalRef(data);

    return env->NewStringUTF(resultStr.c_str());
}