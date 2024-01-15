# セットアップマニュアル

> [!CAUTION]
> このWebアプリケーションはJDKがインストールされた環境で動作します。

・  サーバIP，パスワード
IP：150.89.233.203
パスワード：isDev23?203
・  サーバアクセス
isdev-bash....exe を起動して下記の ssh コマンドでアクセスします。
```
$ ssh isdev23@150.89.233.203
```
パスワードを要求されるので，上記のパスワードを入力します。
```
isdev23@150.89.233.203's password:(表示されていなくても入力されている)
```
・  ホームディレクトリに移動
アプリケーションをインストールする場所に移動します。
以下のコマンドでホームディレクトリに移動します。
```
$ cd
```
移動できたか`pwd`で確認します。以下の画面が表示されたら成功です。
```
isdev23@ubuntu203:~$ pwd
/home/isdev23
```
・  リポジトリの取得
以下のコマンドを実行して，本アプリケーションのリポジトリを取得しましょう
```
git clone https://github.com/sho037/LecMaster.git
```
リポジトリが取得できたか確認しましょう。以下の画面が表示されたら成功です。
```
isdev23@ubuntu203:~$ ls
LecMaster
```
確認ができたら`cd`コマンドを利用してリポジトリに移動しましょう。
```
cd LecMaster
```
・  実行前
　インストール後は`src/main/java/shok/lecmaster/security/`ディレクトリに`student.csv`と`teacher.csv`ファイルを設置してください。
ファイル内にはそれぞれのidとパスワード(bcriptハッシュ化済み)を記入してください。その内容でログインすることができます。
パスワードをハッシュ化するには下記のスクリプトを実行してください。
```bash
sshrun htpasswd -nbBC 10 ${user_id} ${password}
```

```例(student.csv)
student1,$2y$10$T3jSu12fw9Gmm1p591CpbuGLfI/ECh88lCpdXUGQcjr4zcDMO1nkC
student2,$2y$10$IM00QILoPNl3N.3X3oJhMOByxa2HNeUgV6GQ9v3Hl1OtoEsjNIZa.
student3,$2y$10$C.xpyPCaxuVSTITaVRRN5.bby1//m256EwKHujSdObAjTpLjMPzEy
```

```例(teacher.csv)
teacher1,$2y$10$PTIElp2sSt4AGXmM/MSMdeactS214M5/DLzjVMzTHMdUB.SZnLnYW
teacher2,$2y$10$C7hKg.ULqTg8qK7udpLSCOHxHbruUz0yf8gmC9FURcvB1c8drVEfa
```
・  実行
　これらのファイルを用意した後アプリケーションのトップディレクトリで下記のスクリプトを実行してください。アプリケーションが実行されます。

```bash
bash ./gradlew
bash ./gradlew bootrun
```
これでWebアプリケーションが公開されるため，以下のURLでアクセスできます。

URL:http://150.89.233.203:8080

[トップへ](../README.md)
