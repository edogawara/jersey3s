# Jersey RESTful API Sample

## 機能
データベースの任意のテーブルに対してAPIでレコードの参照・追加・更新・削除を行う。

## API の仕様

|URL|METHOD|機能| 詳細 |
|---|------|----|-----|
|/api/tables | GET| 照会 | テーブル一覧 |
|/api/tables/{tableid} | GET| 照会 | カラム一覧 |
|/api/tables/{tableid}/records | GET| 照会 | レコード情報(複数行) |
|/api/tables/{tableid}/records/{recordid} | GET| 照会 | レコード情報(１行) |
|/api/tables/{tableid}/records/|POST| 追加 |レコード追加|
|/api/tables/{tableid}/records/{recordid}|PUT| 更新 |レコード更新|
|/api/tables/{tableid}/records/{recordid}|DELETE|削除|レコード削除|

## 環境

* Tomcat10
* Jersey4.0
* PostgreSQL15.0
* JDK17
* Eclipse(Pleiades 2024)

## 設定

JDBC JNDI
META-INF/context.xml

JDBC Driver 
${CATALINA_HOME}/lib

## 制限

* テーブルに"id"というカラム名が存在する
* 文字列で変換可能なデータ"型"のみ

## Swagger-UI

/api/openapi.json
