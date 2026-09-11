# Local setup

Two files are required to build and are deliberately **not** committed.

## 1. `local.properties`

Android Studio generates this. Add the Maps key to it:

```properties
sdk.dir=/path/to/Android/sdk
MAPS_API_KEY=your_google_maps_api_key
```

The key is injected into the manifest as `${MAPS_API_KEY}` and exposed as the
`google_maps_key` string resource by `app/build.gradle.kts`. `MAPS_API_KEY` is
also read from the environment, which is what CI should use.

## 2. `app/google-services.json`

Download it from the Firebase console for this project and place it at
`app/google-services.json`. See `app/google-services.json.example` for the shape.
