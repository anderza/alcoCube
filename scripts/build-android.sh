#!/usr/bin/env bash
# Собирает Android-приложение и складывает результат в папку dist/.
#
#   ./scripts/build-android.sh           — отладочный APK (ставится сразу)
#   ./scripts/build-android.sh release   — подписанные APK и AAB
#
# Для release нужны переменные окружения (см. README):
#   KEYSTORE_FILE, KEYSTORE_PASSWORD, KEY_ALIAS, KEY_PASSWORD
set -euo pipefail
cd "$(dirname "$0")/.."
chmod +x gradlew
rm -rf dist && mkdir -p dist

if [ "${1:-debug}" = "release" ]; then
  : "${KEYSTORE_FILE:?Задайте KEYSTORE_FILE}"
  : "${KEYSTORE_PASSWORD:?Задайте KEYSTORE_PASSWORD}"
  : "${KEY_ALIAS:?Задайте KEY_ALIAS}"
  : "${KEY_PASSWORD:?Задайте KEY_PASSWORD}"
  ./gradlew assembleRelease bundleRelease
  cp app/build/outputs/apk/release/*.apk dist/
  cp app/build/outputs/bundle/release/*.aab dist/
else
  ./gradlew assembleDebug
  cp app/build/outputs/apk/debug/*.apk dist/
fi

echo "Готово:"
ls -la dist
