import java.util.ArrayList;
import java.util.Scanner;

public class lianxi {

    public static void main(String[] arges) {

/*        String[] p = {"a","b","c"};
        String[] car = {"红","黄","蓝"};

        int count = 0;
        String[] h = new String[3];


        for(int i=0; i<p.length;i++){
            h[i]=p[i]+car[0];

            for(int j=0;j<p.length;j++){
                if(i==j){continue;}
                count ++;
                h[j]=p[j]+car[1];
            }

        }

        System.out.print(count);
        System.out.print(h);*/


//打印100以内质数
/*        for(int i=2;i<100;i++){
            boolean a=true;

            for(int j=2;j<i;j++){
                if(i%j==0){
                   a=false;
                    break;
                }
            }
            if(a){
                System.out.print(i+"-");
            }

            }*/

        //判断所输入是否是质数
/*        Scanner a = new Scanner(System.in);
        String b = a.next();
        int c = Integer.parseInt(b);
        boolean m = true;


        for (int i = 2; i < c; i++) {

            if (c % i == 0) {
                m = false;
                System.out.print(c + "不是质数。");
                break;
            }

        }
        if(m){
            System.out.print(c + "是质数。");
        }*/


        //1到100的百合花数
/*        for(int i=1;i<100;i++){
            String a = String.valueOf(i);
            String [] b = a.split();










        }*/







    }
}




