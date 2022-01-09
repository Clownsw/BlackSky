//
// By: smilex
//

#include "Interface.h"
#include "Tools.h"
#include <openssl/sha.h>
#include <iomanip>
#include <sstream>

enum SHA_TYPE {
    sha1 = 0,
    sha224 = 1,
    sha256 = 2,
    sha384 = 3,
    sha512 = 4
};

std::string sha1_string(absl::string_view&& str) {
    unsigned char hash[SHA_DIGEST_LENGTH];
    SHA_CTX sha;
    SHA1_Init(&sha);
    SHA1_Update(&sha, std::string(str).c_str(), str.length());
    SHA1_Final(hash, &sha);

    std::stringstream ss;

    for (int i = 0; i < SHA_DIGEST_LENGTH; i++) {
        ss << std::hex << std::setw(2) << std::setfill('0') << (int)hash[i];
    }
    return ss.str();
}

std::string sha224_string(const absl::string_view&& str) {

    unsigned char hash[SHA224_DIGEST_LENGTH];
    SHA256_CTX sha256;
    SHA224_Init(&sha256);
    SHA224_Update(&sha256, std::string(str).c_str(), str.size());
    SHA224_Final(hash, &sha256);

    std::stringstream ss;

    for (int i = 0; i < SHA224_DIGEST_LENGTH; i++) {
        ss << std::hex << std::setw(2) << std::setfill('0') << (int)hash[i];
    }
    return ss.str();
}

std::string sha256_string(const std::string_view&& str)
{
    unsigned char hash[SHA256_DIGEST_LENGTH];
    SHA256_CTX sha256;
    SHA256_Init(&sha256);
    SHA256_Update(&sha256, std::string(str).c_str(), str.size());
    SHA256_Final(hash, &sha256);

    std::stringstream ss;

    for (int i = 0; i < SHA256_DIGEST_LENGTH; i++) {
        ss << std::hex << std::setw(2) << std::setfill('0') << (int)hash[i];
    }
    return ss.str();
}

std::string sha384_string(const absl::string_view&& str) {

    unsigned char hash[SHA384_DIGEST_LENGTH];
    SHA512_CTX sha384;
    SHA384_Init(&sha384);
    SHA384_Update(&sha384, std::string(str).c_str(), str.size());
    SHA384_Final(hash, &sha384);

    std::stringstream ss;

    for (int i = 0; i < SHA384_DIGEST_LENGTH; i++) {
        ss << std::hex << std::setw(2) << std::setfill('0') << (int) hash[i];
    }

    return ss.str();
}

std::string sha512_string(const std::string_view&& str) {

    unsigned char hash[SHA512_DIGEST_LENGTH];
    SHA512_CTX sha512;
    SHA512_Init(&sha512);
    SHA512_Update(&sha512, std::string(str).c_str(), str.size());
    SHA512_Final(hash, &sha512);

    std::stringstream ss;

    for (int i = 0; i < SHA512_DIGEST_LENGTH; i++) {
        ss << std::hex << std::setw(2) << std::setfill('0') << (int) hash[i];
    }
    return ss.str();
}

/*
 * Class:     cn_smilex_libhv_jni_ssl_Sha
 * Method:    sha
 * Signature: (ILjava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_smilex_libhv_jni_ssl_Sha_sha
    (JNIEnv* env, jobject obj, jint type, jstring data) {

    const char* buffer;

    switch (type) {

        default:
        case sha1: {
            buffer = sha1_string(env->GetStringUTFChars(data, JNI_FALSE)).c_str();
            break;
        }

        case sha224: {
            buffer = sha224_string(env->GetStringUTFChars(data, JNI_FALSE)).c_str();
            break;
        }

        case sha256: {
            buffer = sha256_string(env->GetStringUTFChars(data, JNI_FALSE)).c_str();
            break;
        }

        case sha384: {
            buffer = sha384_string(env->GetStringUTFChars(data, JNI_FALSE)).c_str();
            break;
        }

        case sha512: {
            buffer = sha512_string(env->GetStringUTFChars(data, JNI_FALSE)).c_str();
            break;
        }

    }

    env->DeleteLocalRef(obj);
    env->DeleteLocalRef(data);

    return env->NewStringUTF(buffer);
}


