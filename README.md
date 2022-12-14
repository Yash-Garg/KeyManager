# KeyManager
[![Android CI](https://github.com/Yash-Garg/KeyManager/actions/workflows/pr_ci.yml/badge.svg)](https://github.com/Yash-Garg/KeyManager/actions/workflows/pr_ci.yml)

An application to manage your SSH and GPG keys on [GitHub](https://github.com/settings/keys) written in Kotlin & Jetpack Compose.

## Download

<a href="https://play.google.com/store/apps/details?id=dev.yash.keymanager">
  <img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png"
       alt="Get it on Google Play"
       height="80" />
</a>
<a href="https://apt.izzysoft.de/fdroid/index/apk/dev.yash.keymanager">
  <img src="https://gitlab.com/IzzyOnDroid/repo/-/raw/master/assets/IzzyOnDroid.png"
       alt="Get it on IzzyOnDroid"
       height="80" />
</a>

## Screenshots

![Feature Graphic](images/feature-graphic.png)

## Building

You will need to add your credentials in the [`Secrets.kt`](https://github.com/Yash-Garg/KeyManager/blob/develop/app/src/main/kotlin/dev/yash/keymanager/data/utils/Secrets.kt) file. 
This file will contain your GitHub OAuth app credentials.

## Third Party Libraries

- [openid/AppAuth-Android](https://github.com/openid/AppAuth-Android)
- [ChuckerTeam/chucker](https://github.com/ChuckerTeam/chucker)
- [dagger/hilt](https://dagger.dev/hilt/)
- [square/leakcanary](https://github.com/square/leakcanary/)
- [square/moshi](https://github.com/square/moshi)
- [MoshiX/moshi-metadata-reflect](https://github.com/ZacSweers/MoshiX/tree/main/moshi-metadata-reflect)
- [michaelbull/kotlin-result](https://github.com/michaelbull/kotlin-result)
- [square/okhttp](https://github.com/square/okhttp)
- [square/retrofit](https://github.com/square/retrofit)

## License

```
Copyright (c) 2021 Yash Garg

Permission is hereby granted, free of charge, to any
person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the
Software without restriction, including without
limitation the rights to use, copy, modify, merge,
publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software
is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice
shall be included in all copies or substantial portions
of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.
```
