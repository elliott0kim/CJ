set(EXTRA_PATCHES "")
if (VCPKG_TARGET_IS_WINDOWS AND NOT VCPKG_TARGET_IS_MINGW)
    list(APPEND EXTRA_PATCHES msvc.diff)
endif()

vcpkg_from_sourceforge(
    OUT_SOURCE_PATH SOURCE_PATH
    REPO "giflib"
    FILENAME "giflib-${VERSION}.tar.gz"
    SHA512 0865ab2b1904fa14640c655fdb14bb54244ad18a66e358565c00287875d00912343f9be8bfac7658cc0146200d626f7ec9160d7a339f20ba3be6b9941d73975f
    PATCHES
        ${EXTRA_PATCHES}
)

file(COPY "${CMAKE_CURRENT_LIST_DIR}/CMakeLists.txt" DESTINATION "${SOURCE_PATH}")

vcpkg_cmake_configure(
    SOURCE_PATH "${SOURCE_PATH}"
    OPTIONS
        "-DGIFLIB_EXPORTS=${CMAKE_CURRENT_LIST_DIR}/exports.def"
    OPTIONS_DEBUG
        -DGIFLIB_SKIP_HEADERS=ON
)

vcpkg_cmake_install()
vcpkg_copy_pdbs()

file(INSTALL "${CMAKE_CURRENT_LIST_DIR}/vcpkg-cmake-wrapper.cmake" DESTINATION "${CURRENT_PACKAGES_DIR}/share/gif")
file(INSTALL "${CMAKE_CURRENT_LIST_DIR}/usage" DESTINATION "${CURRENT_PACKAGES_DIR}/share/${PORT}")
file(INSTALL "${SOURCE_PATH}/COPYING" DESTINATION "${CURRENT_PACKAGES_DIR}/share/${PORT}" RENAME copyright)