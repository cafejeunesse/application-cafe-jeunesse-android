# application-cafe-jeunesse-android
Dépôt pour application Android du Café Jeunesse

![Preview](http://i.imgur.com/tm7YQce.png)

## Prérequis

Ce projet a été créé avec [Android studio](http://developer.android.com/tools/studio/index.html).

### Google Play Services

Si vous désirez utiliser l'émulateur, il vous faut avoir les *Google Play Services* et *Google Apis* installés dans votre android sdk (section Extras dans le sdk-manager).
Pour plus de détails, voir [android-emulator-this-app-wont-run-without-google-play-services](http://stackoverflow.com/questions/23265214/android-emulator-this-app-wont-run-without-google-play-services)

### Google Maps API

Créer le fichier `app/src/main/res/values/google_api_key.xml` et insérer:

    <resources>
        <string name="google_api_key">INSERT_YOUR_KEY_HERE</string>
    </resources>

Remplacer `INSERT_YOUR_KEY_HERE` par votre clé. ;)

Voir [console.developers.google.com](https://console.developers.google.com/)
[Plus de détails](https://developers.google.com/maps/documentation/android/start#get_an_android_certificate_and_the_google_maps_api_key)

### Récupérer la base de données sans accès root

    adb shell "run-as android.cafejeunesse.com.cafejeunesse \
    cat /data/data/android.cafejeunesse.com.cafejeunesse/databases/cafeDB.sqlite /sdcard/" \
    > cafeDB.sqlite
    
    open cafeDB.sqlite

Sur mac, `brew cask install sqlitebrowser`. Voir [sqlitebrowser](http://sqlitebrowser.org/)

### Génération d'icônes depuis [Font-Awesome](http://fortawesome.github.io/Font-Awesome/)

Installation de [Font-Awesome-SVG-PNG](https://github.com/encharm/Font-Awesome-SVG-PNG) (sur osx):

    brew install librsvg
    npm install -g font-awesome-svg-png

Utilisation:

    font-awesome-svg-png --color \#5C6BC0 --icons facebook,globe,phone,map-marker,usd,file-text-o,list --sizes 256 --dest /tmp/icons

Si vous obtenez `permission denied: font-awesome-svg-png`, 
utiliser `~/local/lib/node_modules/font-awesome-svg-png/font-awesome-svg-png.js` 
au lieu de `font-awesome-svg-png`
    
Remplacer `-` par `_`, préfixer par `icon_`, déplacer les icones vers `app/src/main/res/drawable/`:

    rename "s/-/_/g" /tmp/icons/\#5C6BC0/png/256/*
    rename 's/^/icon_/' /tmp/icons/\#5C6BC0/png/256/*
    mv /tmp/icons/\#5C6BC0/png/256/* app/src/main/res/drawable/
