# application-cafe-jeunesse-android
Dépôt pour application Android du Café Jeunesse

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
