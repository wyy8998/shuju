import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class shuju1 {
    public static void main(String[]arges){

        //调用输入查看人名
        String names = name();
        System.out.println(names);
        Xiashu(names);





    }

    //输入查看人名
    public static String name(){
        Scanner na = new Scanner(System.in);
        String name = na.next();
        return name;

    }


    //输入查看人名,返回所有下属信息
    public static List<String[]> Xiashu (String name) {

        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();


        String sql = "select employee_id from permission.employee where employee_name='"+name+"'";
        PreparedStatement pstmt;
        List<String[]> XiashuList = new ArrayList<String[]>();
        try {
            //将sql语句发送到数据库
            pstmt = connection.prepareStatement(sql);
            ResultSet a = pstmt.executeQuery(sql);

            String abc = "";
            while (a.next()) {
                abc = a.getString(1);
            }

            sql = "select employee_id as eid ,employee_name as ename ,employee_organizationid as dqid,employee_type as lx from permission.employee where employee_parentid=" + abc ;
            System.out.println(abc);
            pstmt = connection.prepareStatement(sql);
            ResultSet a1 = pstmt.executeQuery(sql);


            while (a1.next()) {
                System.out.println(a1.getString(1) + "\t" +
                        a1.getString(2) + "\t" + a1.getString(3) + "\t" + a1.getString(4));
                String[] Xiashu = {a1.getString(1), a1.getString(2), a1.getString(3), a1.getString(4)};
                XiashuList.add(Xiashu);
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return XiashuList;


    }




}





