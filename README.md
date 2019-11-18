# CexMobilityCoreKotlin

Pasos para añadir el módulo a un proyecto
Para añadir el módulo al proyecto.
git submodule add git submodule add git@github.com:iriaSan/CexMobilityCoreKotlin.git

Añadir el modulo al settings.gradle
include ':app', ':CexMobilityCoreKotlin'

Importar el modulo en el fichero gradle de la app 'build.gradle'
implementation project(path: ':CexMobilityCoreKotlin')
