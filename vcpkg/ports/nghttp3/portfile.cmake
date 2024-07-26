vcpkg_from_github(
    OUT_SOURCE_PATH SOURCE_PATH
    REPO ngtcp2/nghttp3
    REF v${VERSION}
    SHA512 56b5ac28f01a9210f0bd3de06dabe9f34867b72c68df0776f8faf3f03a4b82185fd16891f67f710b8c7db56523e29a7c51e4b2337ea9e5331bc6c21a26ed12dd
    HEAD_REF main
)

vcpkg_from_github(
    OUT_SOURCE_PATH SFPARSE_SOURCE_PATH
    REPO ngtcp2/sfparse
    REF c669673012f9d535ec3bcf679fe911c8c75a479f
    SHA512 0b16569665d794384704d95317211ad6b8ab32f1aa3ee4823450f325caaef58ca7155a83f37f7ceeb0da574e15a462b47f1dc01563f5b2f899b9386053200ea7
    HEAD_REF main
)

file(REMOVE_RECURSE "${SOURCE_PATH}/lib/sfparse")
file(MAKE_DIRECTORY "${SOURCE_PATH}/lib")
file(RENAME "${SFPARSE_SOURCE_PATH}" "${SOURCE_PATH}/lib/sfparse")


string(COMPARE EQUAL "${VCPKG_CRT_LINKAGE}" "static" ENABLE_STATIC_CRT)
string(COMPARE EQUAL "${VCPKG_LIBRARY_LINKAGE}" "static" ENABLE_STATIC_LIB)
string(COMPARE EQUAL "${VCPKG_LIBRARY_LINKAGE}" "dynamic" ENABLE_SHARED_LIB)

vcpkg_cmake_configure(
    SOURCE_PATH "${SOURCE_PATH}"
    OPTIONS
        -DENABLE_LIB_ONLY=ON
        -DBUILD_TESTING=OFF
        "-DENABLE_STATIC_CRT=${ENABLE_STATIC_CRT}"
        "-DENABLE_STATIC_LIB=${ENABLE_STATIC_LIB}"
        "-DENABLE_SHARED_LIB=${ENABLE_SHARED_LIB}"
    MAYBE_UNUSED_VARIABLES
        BUILD_TESTING
)
vcpkg_cmake_install()
vcpkg_copy_pdbs()
vcpkg_fixup_pkgconfig()

file(REMOVE_RECURSE
    "${CURRENT_PACKAGES_DIR}/debug/include"
    "${CURRENT_PACKAGES_DIR}/debug/share"
    "${CURRENT_PACKAGES_DIR}/share/man"
    "${CURRENT_PACKAGES_DIR}/share/doc"
)

if(VCPKG_LIBRARY_LINKAGE STREQUAL static)
    file(REMOVE_RECURSE
        "${CURRENT_PACKAGES_DIR}/bin"
        "${CURRENT_PACKAGES_DIR}/debug/bin"
    )
    file(APPEND "${CURRENT_PACKAGES_DIR}/include/nghttp3/version.h" [[
]])
endif()

file(INSTALL "${SOURCE_PATH}/COPYING" DESTINATION "${CURRENT_PACKAGES_DIR}/share/${PORT}" RENAME copyright)
