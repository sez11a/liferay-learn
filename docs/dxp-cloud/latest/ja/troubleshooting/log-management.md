# ログ管理

ログはデバッグに不可欠です。 DXP Cloudでは、Webコンソールまたはターミナルから環境のログにアクセスできます。 ログファイルをダウンロードすることもできます。

## Webコンソールからログにアクセスする

環境のログにアクセスする最も簡単な方法は、Webコンソールを使用することです。 これを行うには、環境を選択し、左側のメニューの[ *Logs* ]タブをクリックします。 特定のサービスのログにアクセスするには、ツールバーの *All Services* メニューからサービスを選択します。 ログをダウンロードするには、[ *Download Logs*]をクリックします。

![図1：Webコンソールでは、ログを表示することもできます。](./log-management/images/01.png)

## ターミナルからログにアクセスする

> **注：** 端末を介してログにアクセスするには、管理者または開発者である必要があります。

次のコマンドを実行して、端末からログにアクセスします。

``` shell
lcp log
```

`lcp log` コマンドは、すべてのサービスのログを一覧表示します。 次に、アクセスするログを選択できます。 特定の環境からログにアクセスするには、そのIDを入力するか、そのIDを使用して `lcp log` コマンドを実行します。

``` shell
lcp log -p <environment-id>
```

コマンドの一部としてサービスを指定することもできます。

``` shell
lcp log -p <environment-id> -s <service-id>
```
