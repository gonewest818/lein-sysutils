# lein-sysutils

Query system information via Apache commons SystemUtils


[![Build Status](https://travis-ci.org/gonewest818/lein-sysutils.svg?branch=master)](https://travis-ci.org/gonewest818/lein-sysutils)
[![Code Coverage](https://codecov.io/gh/gonewest818/lein-sysutils/branch/master/graph/badge.svg)](https://codecov.io/gh/gonewest818/lein-sysutils)
[![Dependencies Status](https://jarkeeper.com/gonewest818/lein-sysutils/status.svg)](https://jarkeeper.com/gonewest818/lein-sysutils)
[![Clojars Project](https://img.shields.io/clojars/v/lein-sysutils.svg)](https://clojars.org/lein-sysutils)

## Configuration:

    :plugins [[lein-sysutils "0.1.0"]]

## Usage:

    lein sysutils [:edn] :key1 [:key2 :key3 ...]

Sysutils provides a command line interface to print and inspect
metadata about the current system config exposed through the Apache
Commons SystemUtils API. The fields are presented as a hashmap, and
can be queried by passing in one or more "keywordized" field names.

The output is available in two forms. By default keywords and values
are printed as pairs, separated with whitespace, one pair per line. If
the optional `:edn` key is set, then the requested keys are printed as EDN.
Depending on your situation you may find one format more convenient than
the other.

To make life easier for devops, the plugin provides an additional
query that is not directly available in the SystemUtils library. The
`:java-version-simple` key is mapped to a string that indicates which
"release" of Java is currently in use. Because of inconsistent
naming across vendors and releases, here the string with be normalized
to "1.1", "1.2", ... "1.8", or for newer releases "9" and "10".

## Examples:

### Print the "simple" Java version number

    lein sysutils :java-version-simple

### Print the Java VM name

    lein sysutils :java-vm-name

### Output multiple values as parseable EDN

    lein sysutils :edn :java-vm-name :java-home

## Available Keys:

    :awt-toolkit
    :file-encoding
    :file-separator
    :is-java-1-1
    :is-java-1-2
    :is-java-1-3
    :is-java-1-4
    :is-java-1-5
    :is-java-1-6
    :is-java-1-7
    :is-java-1-8
    :is-java-1-9
    :is-java-9
    :is-os-400
    :is-os-aix
    :is-os-free-bsd
    :is-os-hp-ux
    :is-os-irix
    :is-os-linux
    :is-os-mac
    :is-os-mac-osx
    :is-os-mac-osx-cheetah
    :is-os-mac-osx-el-capitan
    :is-os-mac-osx-jaguar
    :is-os-mac-osx-leopard
    :is-os-mac-osx-lion
    :is-os-mac-osx-mavericks
    :is-os-mac-osx-mountain-lion
    :is-os-mac-osx-panther
    :is-os-mac-osx-puma
    :is-os-mac-osx-snow-leopard
    :is-os-mac-osx-tiger
    :is-os-mac-osx-yosemite
    :is-os-net-bsd
    :is-os-open-bsd
    :is-os-os2
    :is-os-solaris
    :is-os-sun-os
    :is-os-unix
    :is-os-windows
    :is-os-windows-10
    :is-os-windows-2000
    :is-os-windows-2003
    :is-os-windows-2008
    :is-os-windows-2012
    :is-os-windows-7
    :is-os-windows-8
    :is-os-windows-95
    :is-os-windows-98
    :is-os-windows-me
    :is-os-windows-nt
    :is-os-windows-vista
    :is-os-windows-xp
    :is-os-zos
    :java-awt-fonts
    :java-awt-graphicsenv
    :java-awt-headless
    :java-awt-printerjob
    :java-class-path
    :java-class-version
    :java-compiler
    :java-endorsed-dirs
    :java-ext-dirs
    :java-home
    :java-io-tmpdir
    :java-library-path
    :java-runtime-name
    :java-runtime-version
    :java-specification-name
    :java-specification-vendor
    :java-specification-version
    :java-util-prefs-preferences-factory
    :java-vendor
    :java-vendor-url
    :java-version
    :java-version-simple
    :java-vm-info
    :java-vm-name
    :java-vm-specification-name
    :java-vm-specification-vendor
    :java-vm-specification-version
    :java-vm-vendor
    :java-vm-version
    :line-separator
    :os-arch
    :os-name
    :os-version
    :path-separator
    :user-country
    :user-dir
    :user-home
    :user-language
    :user-name
    :user-timezone

For more information on the available keys and what they mean, see the
Apache Documentation:

https://commons.apache.org/proper/commons-lang/javadocs/api-3.5/org/apache/commons/lang3/SystemUtils.html

## License

Copyright © 2018 Neil Okamoto

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
