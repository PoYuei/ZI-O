import com.sun.istack.internal.Nullable;

import java.util.Scanner;


public class cheat {
    static int m = 0;  //表示密钥的个数


    public static void main(String args[]){

        //获取使用的明文密文对：
        Scanner sc = new Scanner(System.in);//构建一个Scanner对象
        System.out.print("输入第一个明文：");
        String P1 = sc.next();
        System.out.print("输入第一个密文：");
        String C1 = sc.next();

        System.out.print("输入第二个明文：");
        String P2 = sc.next();
        System.out.print("输入第二个密文：");
        String C2 = sc.next();

        System.out.print("使用的明文-密文对：\n"+P1+"-"+C1+"\n"+P2+"-"+C2);


//        String P1 = "10011010";
//        String P2 = "10101010";
//        String C1 = "11101111";
//        String C2 = "00001001";

        System.out.print("开始破解\n");
        long start = System.currentTimeMillis();


        char[][] keys = grab_keys(P1,C1,null);

        keys = grab_keys(P2,C2,keys);

        long finish = System.currentTimeMillis();

        System.out.println("破解完成，结果为："+m);
        for (int i=0;i<m;i++){
            System.out.println(String.valueOf(keys[i]));
        }
        System.out.println("耗时："+(finish-start)+"毫秒");
    }

    public static char[][] grab_keys(String P, String C, @Nullable char[][] keys) {
        //新建一个密钥数组，为符合条件的密钥且符合keys[][]
        char[][] my_keys = new char[1024][10];
        char[] inputArray = P.toCharArray();
        if(keys==null){
            m = 0;
            //当不给定keys[][]时，对所有密钥进行检验。
            for(int i=0;i<1024;i++){
                String key = Integer.toBinaryString(i);
                while(key.length()<10){
                    key = "0" + key;
                }
                char[] keyArray = key.toCharArray();


                String ans = String.valueOf(Main.My_Des(0, inputArray, keyArray));
                //找到合适的密钥则写入my_keys
                if(ans.equals(C)){
                    my_keys[m] = keyArray;
                    m++;
                }
            }
        } else{
            m = 0;
            for (char[] keyArray : keys) {
                String ans = String.valueOf(Main.My_Des(0, inputArray, keyArray));
                //找到合适的密钥则写入my_keys
                if (ans.equals(C)) {
                    my_keys[m] = keyArray;
                    m++;
                }
            }
        }
        return my_keys;
    }
}
