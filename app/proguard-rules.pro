# SPDX-FileCopyrightText: The LineageOS Project
# SPDX-License-Identifier: Apache-2.0

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep Media3 Session Services from being obfuscated or renamed
-keep class * extends androidx.media3.session.MediaLibraryService { *; }
-keep class * extends androidx.media3.session.MediaSessionService { *; }

