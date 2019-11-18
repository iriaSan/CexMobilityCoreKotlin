# Instrucciones de uso del modulo
## Pasos para añadir el módulo a un proyecto

1. Para añadir el módulo al proyecto.
`git submodule add git@github.com:iriaSan/CexMobilityCoreKotlin.git

2. Añadir el modulo al settings.gradle
`include ':app', ':CexMobilityCoreKotlin'`

3. Importar el modulo en el fichero gradle de la app 'build.gradle'
```java
dependencies {
     // Jar libs
     implementation fileTree(dir: 'libs', include: ['*.jar'])
     
     // Android test framework
     testImplementation 'junit:junit:4.12'
     androidTestImplementation 'androidx.test:runner:1.2.0'
     androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
     
     // Core implementation
     implementation project(path: ':CexMobilityCoreKotlin')
     
     // Annotations
     annotationProcessor "com.jakewharton:butterknife-compiler:10.0.0"
     annotationProcessor "com.google.dagger:dagger-compiler:2.23.2"
     annotationProcessor "com.google.dagger:dagger-android-processor:2.23.2"
     annotationProcessor "androidx.lifecycle:lifecycle-compiler:2.0.0"
     annotationProcessor "androidx.room:room-compiler:2.1.0"
 }
 ```
 

 ## Actualizar un modulo cuando te descargar un proyecto
 `cd core`
 `git submodule init `
 `git submodule update`
 `git checkout master`