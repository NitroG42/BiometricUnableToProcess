# Face ID ERROR_UNABLE_TO_PROCESS

Use case :
- The app display a screen with the camera
- When the user press authenticate, a new screen is display that should immediatly start a biometric prompt to authenticate him
- If the user has a Face ID device, unfortunatelly the chain will break the prompt with a ERROR_UNABLE_TO_PROCESS

Explanation :
It seems face ID will attempt to use the camera, which is still used by the previous screen (it is shutting down and the camera is closing).
Unfortunatelly, this attempt will immediately display an error, and we can't check before hands if the biometric sensor is initialized.