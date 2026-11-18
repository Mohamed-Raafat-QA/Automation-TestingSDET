#!/usr/bin/env bash
set -euo pipefail
repo=$(cd "$(dirname "$0")/.." && pwd)
case "${1:-}" in
  android)
    gradle --project-dir "$repo/demo/android" --console=plain :app:assembleDebug >&2
    printf '%s\n' "$repo/demo/android/app/build/outputs/apk/debug/app-debug.apk"
    ;;
  ios)
    (cd "$repo/demo/ios" && xcodegen generate >&2)
    xcodebuild -quiet -project "$repo/demo/ios/ParcelDesk.xcodeproj" -scheme ParcelDesk \
      -configuration Debug -sdk iphonesimulator -destination 'generic/platform=iOS Simulator' \
      -derivedDataPath "$repo/demo/ios/build" CODE_SIGNING_ALLOWED=NO build >&2
    printf '%s\n' "$repo/demo/ios/build/Build/Products/Debug-iphonesimulator/ParcelDesk.app"
    ;;
  *) echo 'Usage: bash scripts/build-app.sh android|ios' >&2; exit 2 ;;
esac
