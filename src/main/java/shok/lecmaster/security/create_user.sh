#!/bin/bash

# ロールを尋ねる
echo "ロールを選んでください (student/teacher): "
read role

# CSVファイルを決定する
if [ "$role" = "student" ]; then
    file="student.csv"
elif [ "$role" = "teacher" ]; then
    file="teacher.csv"
else
    echo "ロールが不正です"
    exit 1
fi

# ユーザー名の接頭辞を尋ねる
echo "ユーザー名の先頭に記述する文字を指定してください: "
read prefix

# 開始番号と終了番号を尋ねる
echo "開始番号を指定してください: "
read start
echo "終了番号を指定してください: "
read end

# 初期パスワードを尋ねる
echo "初期パスワードを指定してください: "
read password

# ユーザー名とハッシュ化されたパスワードを生成してファイルに追記する
for (( i=start; i<=end; i++ )); do
    username="$prefix$i"
    hash=$(sshrun htpasswd -nbBC 10 "$username" "$password" | cut -d ":" -f 2)
    echo "$username,$hash" >> $file
done

echo "ユーザーを作成しました"
