# RealmDemo

Realm Database is an alternative to SQLite and Core Data.
Thanks to its zero-copy design, Realm Database is much faster than an ORM, and often faster than raw SQLite.
Get started in minutes, not hours.

## Setup

Step 1: Add the class path dependency to the project level build.gradle file.

    buildscript {   
         repositories {        
                jcenter()
         }
         dependencies {
                classpath "io.realm:realm-gradle-plugin:5.9.0"
         }
     }

Step 2: Apply the realm-android plugin to the top of the application level build.gradle file.

    apply plugin: 'realm-android'

## Note:

To remove error "User is not a part of the Realm Object", simply delete the generatedJava folder in android project.

