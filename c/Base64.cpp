//
// By: smilex
//

#include "Interface.h"
#include "Tools.h"
#include <hv/base64.h>

/*
 * Class:     cn_smilex_libhv_jni_ssl_Base64
 * Method:    base64_encode
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_Base64_base64_1encode
    (JNIEnv* env, jobject obj, jstring data, jint len) {

    const char* charData = env->GetStringUTFChars(data, JNI_FALSE);

    int size = BASE64_ENCODE_OUT_SIZE(len);
    char* encoded = (char *)malloc(size + 1);

    if (encoded == nullptr) {
        return nullptr;
    }

    size = hv_base64_encode(reinterpret_cast<const unsigned char *>(charData), len, encoded);

    encoded[size] = '\0';

    jstring ret = env->NewStringUTF(encoded);

    free(encoded);

    env->DeleteLocalRef(obj);
    env->DeleteLocalRef(data);

    return ret;
}

/*
 * Class:     cn_smilex_libhv_jni_ssl_Base64
 * Method:    base64_decode
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_Base64_base64_1decode
    (JNIEnv* env, jobject obj, jstring data, jint len) {

    const char* charData = env->GetStringUTFChars(data, JNI_FALSE);

    int size = BASE64_ENCODE_OUT_SIZE(len);

    unsigned char* decoded = (unsigned char *) malloc(size);

    if (decoded == nullptr) {
        return nullptr;
    }

    hv_base64_decode(charData, size, decoded);

    jstring ret = env->NewStringUTF(reinterpret_cast<const char *>(decoded));

    free(decoded);

    env->DeleteLocalRef(obj);
    env->DeleteLocalRef(data);

    return ret;
}