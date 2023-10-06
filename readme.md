用户指南
1. 简介
  本产品为一款基于S-DES算法的加密工具，允许用户对输入的文本进行加密和解密操作。

2. 开始使用
  安装/部署步骤：
  安装IDEA或者其他可运行java代码的工具，配置JDK
  
  初始设置或配置：
  启动程序。
  如果需要，配置默认加密密钥。

3. 主要功能
  文本加密：
  在“输入”框中输入您希望加密的文本。
  如果需要，输入或生成新的密钥。
  点击“加密”按钮。
  加密后的文本将显示在“输出”框中。

  文本解密：
  在“输入”框中输入您希望解密的密文。
  输入用于加密该文本的密钥。
  
  点击“解密”按钮。
  解密后的文本将显示在“输出”框中。

4. 常见问题和解决方法
Q: 我加密的文本解密后不正确怎么办？
A: 请确保您使用正确的密钥进行解密。
Q：为什么我输入的字符串加密后的密文会有很多奇怪的字符和方框？
A：因为这里我们使用的逻辑是先将明文转化为对应的ASCLL码，加密后展示密文时使用的时UTF-8编码，这样操作的目的是为了避免出现ASCLL码无法解密的情况，而UTF-8包括各种各样的字符，有些系统无法识别的会展示为方框，但实际上不影响解密

5. 联系和支持
如遇到任何问题，请联系：20215345@cqu.edu.cn
