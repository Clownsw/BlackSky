#pragma once

#ifndef TOOLS_H
#define TOOLS_H

#include <jni.h>
#include <map>
#include <string>
#include <iostream>

#define CLASSNAME_String            "Ljava/lang/String;"
#define CLASSNAME_Set               "Ljava/util/Set;"
#define CLASSNAME_HashMap           "Ljava/util/HashMap;"
#define CLASSNAME_Map$Entry         "Ljava/util/Map$Entry;"
#define CLASSNAME_Iterator          "Ljava/util/Iterator;"

#define CLASSNAME_HttpResponse      "Lcn/smilex/libhv/jni/HttpResponse;"

static std::map<std::string, jclass> g_jclasss;

std::map<std::string, std::string> parseMap(JNIEnv* env, jobject &obj);
jclass queryJClassByName(JNIEnv* &env, const char* name, const char* className);

jobject createHashMap(JNIEnv* env);
#endif