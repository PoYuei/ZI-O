# ZI-O
A simple course assignment
# ZI-O
A simple course assignment

1.	基本测试：
	基本GUI：密钥可以随机生成

    ![image](https://github.com/PoYuei/ZI-O/assets/140697450/1c848d02-c5a7-4411-a245-2e3e1e653b07)

  输入输出测试：
  
  ![image](https://github.com/PoYuei/ZI-O/assets/140697450/fc5b4478-07ff-4277-96e4-51748aaffe90)


3.	交叉测试：
	第一关测试数据为示例数据，与给定答案相同。同密钥解密密文可以得到明文

    ![image](https://github.com/PoYuei/ZI-O/assets/140697450/0c91cd02-d930-44e4-9a2e-b00c1f376414)

5.	扩展功能：
密钥沿用前例。同时需要注意，此处对于ASCII加密之后得到的密文采用UTF-8编码输出，原因是对于ASCII表，Java只能够展示出其中95个数据，剩下的数据乱码之后且不可复原，即不能够解密获得密文。因此采用其他编码进行输出。

同密钥情况下加解密一段字符：

  ![image](https://github.com/PoYuei/ZI-O/assets/140697450/d60a33c9-b65c-433f-bf38-c5bb8f6eb870)
  
  ![image](https://github.com/PoYuei/ZI-O/assets/140697450/aefd9e5f-ff34-448c-9612-e4b4661ca17d)


4.	给定明文密文对为：
    10011010-11101111
  	
    10101010-00001001
  	
    加密密钥为1010000010
    代入进程cheat.java求解：
  	
  	![image](https://github.com/PoYuei/ZI-O/assets/140697450/598100f7-14fe-4459-be63-1954741563cc)

6.	推理为是，理由如下：
  对于密钥生成两个子密钥，经过P10和P8只取决于除第二位之外的九位，因此对于上述密钥扩展置换，任意密钥均存在另一个密钥只需要满足第二位不同即可达成相同的加密解密效果。因此确实会出现选择不同的密钥得到相同密文的情况.
	如下：将传入的10bit密钥加密后得到的
  数字均代表位数。0表示第10位.

	k1: 17948306

	k2: 94836510

	显然与第二位无关，。
