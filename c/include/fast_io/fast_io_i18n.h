﻿#pragma once
#if !defined(__cplusplus)
#error "You are not using C++ compiler"
#endif

#if defined(__GNUC__) && __GNUC__>=11 && __cplusplus<202002L
#error "fast_io requires at least C++20 standard compiler."
#else
#include"fast_io_hosted.h"


#include"fast_io_i18n/lc.h"
#include"fast_io_i18n/lc_print.h"
#if __STDC_HOSTED__==1 && (!defined(_GLIBCXX_HOSTED) || _GLIBCXX_HOSTED==1)
#include"fast_io_i18n/locale.h"
#endif
#include"fast_io_i18n/imbuer.h"
#include"fast_io_i18n/lc_print_status.h"
#include"fast_io_i18n/lc_numbers/impl.h"
#include"fast_io_i18n/lc_unsafe_rt_fprint_status.h"

#if __STDC_HOSTED__==1 && (!defined(_GLIBCXX_HOSTED) || _GLIBCXX_HOSTED==1)
#if defined(_GLIBCXX_STRING) || defined(_LIBCPP_STRING) || defined(_STRING_)
#include"fast_io_unit/string.h"
#include"fast_io_unit/string_impl/lc_concat.h"
#endif
#endif
#endif

#ifndef FAST_IO_DISABLE_FLOATING_POINT
#include"fast_io_unit/floating/lc_impl.h"
#endif