import com.sun.istack.internal.Nullable;
import java.util.Base64;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
public class Main {
    /**定义常量*/
    public static int[] LS =new int[] {2,3,4,5,1,7,8,9,10,6}; //left shift时索引,第二个LS在上一个基础进行一次迭代,此处整合
    public static int[] P10 =new int[] {3,5,2,7,4,10,1,9,8,6}; //P10
    public static int[] P8 =new int[] {6,3,7,4,8,5,10,9};   //P8
    public static int[] IP =new int[] {2,6,3,1,4,8,5,7};  //IP盒
    public static int[] IP_INV =new int[] {4,1,3,5,7,2,8,6}; //IP逆
    public static int[] EP_Box =new int[] {4,1,2,3,2,3,4,1}; // EP_BOX，四位扩展为8位
    public static int[] F_exchange = new int[] {2,4,3,1};
    public static int[][] S_Box1 = new int[][] {
            {1, 0, 3, 2},
            {3, 2, 1, 0},
            {0, 2, 1, 3},
            {3, 1, 0, 2}
    };
    public static char[][] S_Box2 = new char[][] {
            {0, 1, 2, 3},
            {2, 3, 1, 0},
            {3, 0, 1, 2},
            {2, 1, 0, 3}
    };
    public static char[] My_Des(int Mode, char[] words,char[] key){
        if(Mode == 0){  //Mode=0表示为加密过程
            return P_TO_C(words,key,IP,EP_Box,F_exchange);
        } else if (Mode == 1) { //Mode=1表示为解密过程
            return C_TO_P(words,key,IP,EP_Box,F_exchange);
        }else {
            System.out.println("模式不对");
            return words;
        }

    }

    //对于ASCII字符串的输入，返回为加密后的字符串。
    //由于此处：当一个字符串加密之后为乱码，代入为解密后结果亦为乱码，因此不能直接输出结果转为ASCII码
    //将结果转为UTF-8编码中的字符输出
    //也可以按照DES的模式，将加密解密时限为函数。
    public static String ACC_Des(int Mode, String words, char[] key) {
        StringBuilder binaryStr = new StringBuilder();
        StringBuilder result = new StringBuilder();

        if (Mode == 0) {  // Encryption
            for (char c : words.toCharArray()) {
                binaryStr.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
            }

            for (int i = 0; i < binaryStr.length(); i += 8) {
                String byteStr = binaryStr.substring(i, i + 8);
                char[] encryptedChar = My_Des(0, byteStr.toCharArray(), key);
                String encryptedBinary = new String(encryptedChar);
                int encryptedAscii = Integer.parseInt(encryptedBinary, 2);
                result.append((char) encryptedAscii);
            }
            return result.toString();

        } else if (Mode == 1) {  // Decryption
            for (char c : words.toCharArray()) {
                binaryStr.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
            }

            for (int i = 0; i < binaryStr.length(); i += 8) {
                String byteStr = binaryStr.substring(i, i + 8);
                char[] decryptedChar = My_Des(1, byteStr.toCharArray(), key);
                String decryptedBinary = new String(decryptedChar);
                int decryptedAscii = Integer.parseInt(decryptedBinary, 2);
                result.append((char) decryptedAscii);
            }
            return result.toString();

        } else {
            throw new IllegalArgumentException("Invalid Mode. Use 0 for encryption and 1 for decryption.");
        }
    }
    //加密过程
    private static char[] P_TO_C(char[] P,char[] key,int[] IP,int[] EP_Box,int[] F_exchange){

        //获取密钥
        char[][] my_keys = GetKeys(P10,P8,LS,key);
        char[] k1 = my_keys[0];
        char[] k2 = my_keys[1];
        //IP置换
        char[] temp = round(P, IP);

        //将IP操作后分为左右两部分
        char[] L =  Arrays.copyOfRange(temp,0,4);
        char[] R =  Arrays.copyOfRange(temp,4,8);
        /*  未函数化
        //fk1
        char[] temp1 = round(R,EP_Box);
        temp1 = XOR(temp1,k1);
        temp1 = Use_S_Box(temp1);
        temp1 = round(temp1,F_exchange);
        L = XOR(L,temp1);

        //此时L、R交换位置

        //fk2
        char[] temp2 = round(L,EP_Box);
        temp2 = XOR(temp2,k2);
        temp2 = Use_S_Box(temp2);
        temp2 = round(temp2, F_exchange);
        R = XOR(R, temp2);
        */

        //函数化.SW操作在R的重赋值中体现，为参数顺序先R后L
        L = fk(k1,L,R,EP_Box,F_exchange);
        R = fk(k2,R,L,EP_Box,F_exchange);

        //将R与L连接起来得到数组与IP逆置换。
        //由于中途L与R交换了位置，因此R在左L在右
        temp = (String.valueOf(R) + String.valueOf(L)).toCharArray();

        //IP逆置换
        temp = round(temp,IP_INV);

        return temp;
    }

    //解密过程
    private static char[] C_TO_P(char[] C,char[] key,int[] IP,int[] EP_Box,int[] F_exchange){
        //获取密钥
        char[][] my_keys = GetKeys(P10,P8,LS,key);
        char[] k1 = my_keys[0];
        char[] k2 = my_keys[1];
        //IP置换
        char[] temp = round(C, IP);

        //将IP操作后分为左右两部分
        char[] L =  Arrays.copyOfRange(temp,0,4);
        char[] R =  Arrays.copyOfRange(temp,4,8);

        //函数化.SW操作在R的赋值中体现，为参数顺序先R后L
        L = fk(k2,L,R,EP_Box,F_exchange);
        R = fk(k1,R,L,EP_Box,F_exchange);

        //将R与L连接起来得到数组与IP逆置换。
        //由于中途L与R交换了位置，因此R在左L在右
        temp = (String.valueOf(R) + String.valueOf(L)).toCharArray();

        //IP逆置换
        temp = round(temp,IP_INV);
        return temp;

    }

    private static char[] fk(char[] ki, char[] L, char[] R, int[] EP_Box, int[] F_exchange){
        char[] temp1 = round(R,EP_Box);
        temp1 = XOR(temp1,ki);
        temp1 = Use_S_Box(temp1);
        temp1 = round(temp1,F_exchange);
        L = XOR(L,temp1);

        return L;
    }
    private static char[] Use_S_Box(char[] a){
        char[] temp = new char[4];
        int x1 = S_Box1[2*a[0]+a[3]-3*'0'][2*a[1]+a[2]-3*'0'];  //-3*'0'来保证char类型的ASCII码的差值在范围内，否则越界
        int x2 = S_Box2[2*a[4]+a[7]-3*'0'][2*a[5]+a[6]-3*'0'];
        String s = Integer.toBinaryString(4*x1+x2); //将x1左移二位后与x2做加法即等于连接
        //当4*x1+x2小于9时，s没有四位，会导致后补0，此处修正为前补0
        while(s.length()<4){
            s = "0" + s;
        }
        temp = s.toCharArray();
        return temp;
    }
    //PT代表P10，PE代表P8，为了和常量区分
    //传入的LeftShift长度应该为10bit
    private static char[][] GetKeys(int[] PT, int[] PE, int[] LeftShift, char[] key){
        //秘钥初始置换P10
        key = round(key,PT);
        //秘钥L与R的LeftShift
        key = round(key,LeftShift);
        char[] k1 = round(key,PE);
        char[] k2 = round(round(key,LeftShift),PE); //也可以如k1分开写。

        return new char[][]{k1,k2};
    }
    /**将序列a按序号b重组,最终ans长度与传入的b相同
     * 说明：
     *      设置为b相同目的是为了完成窄变换和宽变换
     *  */
    private static char[] round(char[] a, int[] b){
        char[] ans = new char[b.length];
        for (int i=0;i<b.length;i++)
        {
            ans[i] = a[b[i]-1];     //由于定义的常量均是1起始，此处-1能够校正数组索引。
        }

        return ans;
    }
    //char数组中异或操作
    private static char[] XOR(char[] a, char[] b){
        if(a.length!=b.length){
            System.out.println("输入的数组长度不等");
            return a;
        } else{
            int n=a.length;
            char[] c = new char[n];
            for(int i=0;i<n;i++){
                if(a[i]==b[i]){
                    c[i] = '0';
                } else{
                    c[i] = '1';
                }
            }
            return c;
        }
    }
}
