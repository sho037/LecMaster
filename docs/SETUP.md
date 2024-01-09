# セットアップマニュアル

> [!CAUTION]
> このWebアプリケーションはJDKがインストールされた環境で動作します。

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

　これらのファイルを用意した後アプリケーションのトップディレクトリで下記のスクリプトを実行してください。アプリケーションが実行されます。

```bash
bash ./gradlew
bash ./gradlew bootrun
```

[トップへ](../README.md)
