# MyExampleIso3Encryption
Shows how to encrypt into PIN Block format, in this case, we're using the ISO-3

- I've used Koin as Dependency injection to inject the encryptor instance (singleton)
to the View Model
- I've used the View Model as it's the recommended per Android. It keeps the data and UI separated
so I can develop apps that suffer config changes.
- This app has been tested rotating it and splitting it without losing any data.
- There are some Unit Tests created for the ViewModel, which I've abstracted to improve its testability.