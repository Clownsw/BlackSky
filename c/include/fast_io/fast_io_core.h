﻿#pragma once
//fast_io_core.h is required to be usable in freestanding environment with EVEN dynamic memory allocation and exceptions are disabled.

#if !defined(__cplusplus)
#error "You are not using C++ compiler"
#endif

#if defined(__GNUC__) && __GNUC__>=11 && __cplusplus<202002L
#error "fast_io requires at least C++20 standard compiler."
#else
#include"fast_io_concept.h"

#include<version>
#include"fast_io_core_impl/empty.h"
#include<cstddef>
#include"fast_io_core_impl/freestanding/impl.h"
#include<type_traits>

#include<limits>
#include<cstdint>
#if __cpp_lib_three_way_comparison >= 201907L
#include<compare>
#endif

#include<bit>			//for std::endian, std::rotl and std::bit_cast etc

#include"fast_io_core_impl/error.h"
#include"fast_io_core_impl/asan_support.h"
//fast_io core
#include"fast_io_core_impl/utils.h"
#include"fast_io_core_impl/terminate.h"
#include"fast_io_core_impl/intrinsics.h"
#include"fast_io_core_impl/parse_code.h"

#include"fast_io_core_impl/ebcdic.h"
#include"fast_io_core_impl/literals/literal.h"
#include"fast_io_core_impl/char_category.h"


#include"fast_io_core_impl/overflow.h"


#if __cpp_lib_three_way_comparison >= 201907L
#include"fast_io_core_impl/compare.h"
#endif

#include"fast_io_core_impl/alias.h"
#include"fast_io_core_impl/pr_rsv.h"

#include"fast_io_core_impl/secure_clear_guard.h"
#include"fast_io_core_impl/local_new_array_ptr.h"
#include"fast_io_core_impl/dynamic_io_buffer.h"
#include"fast_io_core_impl/temporary_buffer.h"
//#include"fast_io_core_impl/manip/impl.h"
#include"fast_io_core_impl/mode.h"
#include"fast_io_core_impl/perms.h"
#include"fast_io_core_impl/seek.h"

#include"fast_io_core_impl/igenerator.h"
#include"fast_io_core_impl/io_ref.h"
#include"fast_io_core_impl/print_freestanding.h"

#include"fast_io_core_impl/scan_freestanding.h"
// This should provide an option macro to disable any generation for table in freestanding environments.
#include"fast_io_core_impl/integers/integer.h"

#include"fast_io_core_impl/black_hole.h"
#include"fast_io_core_impl/buffer_view.h"
#include"fast_io_core_impl/transmit/impl.h"

#ifdef __cpp_lib_source_location
#include<source_location>
#include"fast_io_core_impl/source_location.h"
#endif

#include"fast_io_core_impl/iso/isos.h"
#include"fast_io_core_impl/enums/impl.h"

#include"fast_io_core_impl/simd/impl.h"

#include"fast_io_core_impl/integers/sto/sto_contiguous.h"

#if !(defined(FAST_IO_DISABLE_CODECVT)&&(__STDC_HOSTED__==0 || (defined(_GLIBCXX_HOSTED) && _GLIBCXX_HOSTED==0)))
#include"fast_io_core_impl/codecvt/impl.h"
#endif
#include"fast_io_core_impl/io_deco_ref.h"

#include"fast_io_core_impl/unsafe_rt_fprint.h"
#include"fast_io_core_impl/timestamp_counter.h"
#include"fast_io_core_impl/dll_mode.h"
#include"fast_io_core_impl/socket/impl.h"
#include"fast_io_core_impl/read_all.h"
#include"fast_io_core_impl/to.h"
#include"fast_io_core_impl/concat/impl.h"
#endif