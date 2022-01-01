//
// By: smilex
//

#pragma once

#ifndef TOOLS_H
#define TOOLS_H

#include <jni.h>
#include <map>
#include <string>
#include <cstring>
#include <hv/requests.h>
#include <hv/httpdef.h>
#include <fmt/color.h>
#include <fmt/core.h>
#include <hv/base64.h>

#include "Requests.h"

#define CLASSNAME_String                    "Ljava/lang/String;"
#define CLASSNAME_Set                       "Ljava/util/Set;"
#define CLASSNAME_HashMap                   "Ljava/util/HashMap;"
#define CLASSNAME_Map$Entry                 "Ljava/util/Map$Entry;"
#define CLASSNAME_Iterator                  "Ljava/util/Iterator;"
#define CLASSNAME_NullPointerException      "Ljava/lang/NullPointerException;"

#define CLASSNAME_HttpRequest              "Lcn/smilex/libhv/jni/http/HttpRequest;"
#define CLASSNAME_HttpResponse              "Lcn/smilex/libhv/jni/http/HttpResponse;"

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

#endif