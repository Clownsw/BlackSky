//
// By: smilex
//

#pragma once

#ifndef TOOLS_H
#define TOOLS_H

#include <jni/jni.h>
#include <map>
#include <string>
#include <hv/requests.h>
#include <hv/httpdef.h>
#include <fmt/color.h>
#include <fmt/core.h>

#include "Requests.h"

/* lang */
static constexpr auto CLASSNAME_Object =                    "Ljava/lang/Object;";
static constexpr auto CLASSNAME_String =                    "Ljava/lang/String;";
static constexpr auto CLASSNAME_Integer =                   "Ljava/lang/Integer;";
static constexpr auto CLASSNAME_Double =                    "Ljava/lang/Double;";
static constexpr auto CLASSNAME_Long =                      "Ljava/lang/Long;";
static constexpr auto CLASSNAME_Boolean =                   "Ljava/lang/Boolean;";

/* util */
static constexpr auto CLASSNAME_Set =                       "Ljava/util/Set;";
static constexpr auto CLASSNAME_HashMap =                   "Ljava/util/HashMap;";
static constexpr auto CLASSNAME_Map$Entry =                 "Ljava/util/Map$Entry;";
static constexpr auto CLASSNAME_Iterator =                  "Ljava/util/Iterator;";

/* 异常类 */
static constexpr auto CLASSNAME_NullPointerException =      "Ljava/lang/NullPointerException;";
static constexpr auto CLASSNAME_RuntimeException =          "Ljava/lang/RuntimeException;";

/* 自定义类 */
static constexpr auto CLASSNAME_HttpRequest =               "Lcn/smilex/blacksky/jni/http/HttpRequest;";
static constexpr auto CLASSNAME_HttpResponse =              "Lcn/smilex/blacksky/jni/http/HttpResponse;";

std::map<std::string, std::string> parseMap(JNIEnv* env, jobject &obj);

template <typename... T>
const char* formatStr(const char* first, T &&... args) {
    return fmt::format(first, args...).c_str();
}

template <typename T, typename... T2>
void print(fmt::detail::color_type color, T first, T2 &&... args) {
    fmt::print(fmt::fg(color), first, args...);
}

JavaHttpRequest* jobjectToRequest(JNIEnv* &env, jobject &obj);
jobject ResponseToHttpResponse(JNIEnv* &env, requests::Response resp);
jobject createHashMap(JNIEnv* &env);
const char* getContentTypeName(http_content_type type);
http_method getMethodName(int n);
void throwException(JNIEnv* &env, const char* exceptionName, const char* message);

static jstring jObjectTojString(JNIEnv *&env, jobject &obj) {
    jclass classString = env->FindClass(CLASSNAME_String);
    jmethodID methodStringValueOf = env->GetStaticMethodID(classString, "valueOf",
                           fmt::format("({}){}", CLASSNAME_Object, CLASSNAME_String).c_str());

    auto str = (jstring)env->CallStaticObjectMethod(classString, methodStringValueOf, obj);

    env->DeleteLocalRef(classString);
    return str;
}

static jint jStringTojInt(JNIEnv *&env, jstring &str) {
    jclass classInteger = env->FindClass(CLASSNAME_Integer);
    jmethodID methodParseInt = env->GetStaticMethodID(classInteger, "parseInt", "(Ljava/lang/String;)I");

    int i = env->CallStaticIntMethod(classInteger, methodParseInt, str);

    env->DeleteLocalRef(classInteger);
    return i;
}

static jdouble jStringToDouble(JNIEnv *&env, jstring &str) {
    jclass classDouble = env->FindClass(CLASSNAME_Double);

    jmethodID classParseDouble = env->GetStaticMethodID(classDouble, "parseDouble",
                           fmt::format("({}){}", CLASSNAME_String, "D").c_str());

    jdouble f = env->CallStaticDoubleMethod(classDouble, classParseDouble, str);

    env->DeleteLocalRef(classDouble);
    return f;
}

static jlong jStringTojLong(JNIEnv *&env, jstring &str) {
    jclass classLong = env->FindClass(CLASSNAME_Long);
    jmethodID methodParseLong = env->GetStaticMethodID(classLong, "parseLong",
                                                       fmt::format("({}){}", CLASSNAME_String, "J").c_str());

    jlong l = env->CallStaticLongMethod(classLong, methodParseLong, str);

    env->DeleteLocalRef(classLong);

    return l;
}

static jboolean jStringTojBoolean(JNIEnv *&env, jstring &str) {
    jclass classBoolean = env->FindClass(CLASSNAME_Boolean);
    jmethodID methodParseBoolean = env->GetStaticMethodID(classBoolean, "parseBoolean",
                                                       fmt::format("({}){}", CLASSNAME_String, "Z").c_str());

    jboolean z = env->CallStaticLongMethod(classBoolean, methodParseBoolean, str);

    env->DeleteLocalRef(classBoolean);

    return z;
}
#endif