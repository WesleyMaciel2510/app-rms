# Release preview commit pattern

Use this workflow when a change must be published with updated app previews.

1. Check the worktree and keep unrelated user changes out of the commit:
   `git status --short`
2. Run the required verification and generate the latest debug APK:
   `./gradlew.bat testDebugUnitTest`
   `./gradlew.bat assembleDebug`
3. Install the generated APK on the connected emulator/device before capturing previews:
   `adb install -r app/build/outputs/apk/debug/app-debug.apk`
4. Capture the requested app states from the installed build. For scrolling screens,
   capture distinct positions and save the images under tracked preview assets, not a
   temporary screenshots directory.
5. Copy the current APK to the tracked artifact path when the release request explicitly
   requires the APK in Git:
   `artifacts/app-debug.apk`
   The normal `app/build/` output remains ignored by `.gitignore`.
6. Review the exact staged file list before committing. The release commit should contain
   only the relevant code, screenshots, documentation/README references, and the explicitly
   requested APK. Never stage IDE state, caches, build directories, logs, or OpenSpec scratch.
7. Commit with a focused message, then push the current branch after confirming the remote:
   `git remote -v`
   `git push origin main`

For the Home preview, the expected package is `com.template.app`; use the current emulator
and scroll between captures so the screenshots show different sections of the screen.

