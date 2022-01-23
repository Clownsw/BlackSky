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

static constexpr auto CLASSNAME_String =                    "Ljava/lang/String;";
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

#endif