# alcoCube — «Пьяный кубик»

Шуточная игра с трёхмерным игральным кубиком. Нажмите на кубик: он подлетает, кувыркается, отскакивает со стуком и падает на случайную грань. Выпавшая надпись крупно появляется вверху экрана.

Игра для развлечения, 18+.

## Режимы

Переключатель внизу экрана:

- **Выпивка** — на гранях шесть вариантов от «Не пью» до «Бухаю по чёрному».
- **Граммы** — на гранях 10, 20, 30, 40, 50 и 60 грамм. По мере бросков внизу экрана появляются дополнительные надписи.

## Как запустить

### В браузере

Откройте `web/index.html` в любом современном браузере (нужен WebGL и интернет, см. ниже). Если в репозитории включён GitHub Pages, игра будет доступна по адресу `https://anderza.github.io/alcoCube/`.

### На Android

Приложение — это `WebView` с той же страницей внутри. Собирается через Gradle (`build`).

**Автоматически, в GitHub Actions** (вкладка **Actions → Android build**). После каждого пуша в `main` сборка выдаёт артефакты:

| Артефакт | Когда появляется | Для чего |
| --- | --- | --- |
| `alcoCube-debug-apk` | всегда | поставить на телефон и проверить |
| `alcoCube-release-apk` | если добавлены секреты подписи | подписанный APK для раздачи |
| `alcoCube-release-aab` | если добавлены секреты подписи | пакет для Google Play |

Если запушить тег вида `v1.0`, файлы прикрепятся к релизу, а версия приложения станет `1.0`.

**Подпись.** Один раз создайте ключ и добавьте четыре секрета в **Settings → Secrets and variables → Actions**:

```bash
keytool -genkeypair -v -keystore alcocube.jks -alias alcocube \
  -keyalg RSA -keysize 2048 -validity 10000
base64 -w0 alcocube.jks      # на Mac: base64 -i alcocube.jks
```

| Секрет | Значение |
| --- | --- |
| `KEYSTORE_BASE64` | вывод команды `base64` |
| `KEYSTORE_PASSWORD` | пароль хранилища |
| `KEY_ALIAS` | `alcocube` |
| `KEY_PASSWORD` | пароль ключа |

Файл `alcocube.jks` храните в надёжном месте и не добавляйте в репозиторий: без него нельзя будет выпустить обновление. Он уже внесён в `.gitignore`.

**Локально.** Нужны JDK 17 и Android SDK (проще всего поставить Android Studio):

```bash
./scripts/build-android.sh            # отладочный APK в папку dist/
./scripts/build-android.sh release    # подписанные APK и AAB, нужны KEYSTORE_FILE,
                                      # KEYSTORE_PASSWORD, KEY_ALIAS, KEY_PASSWORD
```

Либо в Android Studio: `File → Open`, затем `Build → Build APK(s)`.

Чтобы поставить APK на телефон, разрешите установку из неизвестных источников.

## Структура

```
web/index.html      игра (одна страница, HTML + CSS + JS)
app/                Android-обёртка (WebView)
scripts/            локальная сборка Android (build-android.sh)
.github/workflows/  сборка APK/AAB и публикация на GitHub Pages
```

Страница `web/index.html` — единственный источник игры. Android-приложение подключает папку `web/` как свои ресурсы, поэтому после правки игры достаточно пересобрать приложение.

## Зависимости

- [Three.js](https://threejs.org/) r128, подгружается с cdnjs.
- Шрифты Russo One и Rubik, подгружаются с Google Fonts.

Для работы нужен интернет. Чтобы играть без сети, скачайте `three.min.js` в `web/`, поменяйте адрес в теге `<script src=...>` на локальный и добавьте шрифты в тот же каталог.

## Как залить на GitHub

Репозиторий: `https://github.com/anderza/alcoCube`

**Через командную строку** (из папки проекта):

```bash
git init
git add .
git commit -m "Пьяный кубик"
git branch -M main
git remote add origin https://github.com/anderza/alcoCube.git
git push -u origin main
```

**Через сайт GitHub** (без git): откройте репозиторий, нажмите **Add file → Upload files** и перетащите в окно *содержимое* папки (не архив: GitHub его не распаковывает). Скрытую папку `.github` нужно перетащить тоже, иначе не заработают автоматические сборки. На Mac скрытые папки показывает сочетание Cmd+Shift+. в окне Finder.

Для публикации сайта: **Settings → Pages → Source: GitHub Actions**.

## Лицензия

Лицензия не выбрана. Добавьте файл `LICENSE`, если хотите, чтобы другие могли использовать код.
