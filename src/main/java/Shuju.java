import java.sql.*;
import java.util.*;
import java.util.List;

public class Shuju {


    public static void main(String[] arges) throws SQLException {

        //调用输入查看人名
        String names = name();
        //返回员工id
        String yid = Yid(names);
        //返回直接下属信息
        List<String[]> XiashuList = new ArrayList<String[]>();
        String a = Xiashu(yid);
        System.out.println(a);


        //自己的按商户分佣计算绩效费
        bljxf(yid);


        //所有下属员工id
/*        for(int i=0;i< XiashuList.size();i++){
          String syxiashu=XiashuList.get(i)[0];
//            System.out.println(XiashuList.get(i)[0]);*/


            //对应商户id
//            Shanghu(syxiashu);

            //对应商户绩效费总和

        }








    //输入查看人名
    public static String name() {
        Scanner na = new Scanner(System.in);
        String name = na.next();
        return name;

    }


    //输入查看人名,返回该人名对应的员工id
    public static String Yid(String name) throws SQLException {

        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();

        String sql = "select employee_id from permission.employee where employee_name='" + name + "'";

        //将sql语句发送到数据库
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet a = pstmt.executeQuery(sql);

        String Yid = "";
        while (a.next()) {
            Yid = a.getString(1);

        }
        return Yid;
    }


    //传入员工ID，返回该员工直接下属信息
    public static String Xiashu(String Yid) throws SQLException {
        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();
        PreparedStatement pstmt;


        String sql = "select employee_id as eid ,employee_name as ename ,employee_organizationid as dqid,employee_type as lx from permission.employee where employee_parentid='" + Yid + "'";

        pstmt = connection.prepareStatement(sql);
        ResultSet a1 = pstmt.executeQuery(sql);
//        System.out.print(a1.next());
//            List<String[]> XiashuList1 = new ArrayList<String[]>();

        if (a1 != null) {
            while (a1.next()) {

/*            System.out.println(a1.getString(1) + "\t" +
                    a1.getString(2) + "\t" + a1.getString(3) + "\t" + a1.getString(4));*/

                String[] Xiashu1 = {a1.getString(1), a1.getString(2), a1.getString(3), a1.getString(4)};
                List<String[]>XiashuList = null;
                        XiashuList.add(Xiashu1);

                Xiashu(Xiashu1[0]);

            }

        }

        return sql;
    }

    //人对应不同商户的按比例计算后的绩效费（sql）
    public static void bljxf(String syxiashu) throws SQLException {
        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();
        PreparedStatement pstmt;


        String sql = "select " +
                "bm.employee_name,bm.employee_id,bm.commission_rate," +
                "mid,name,绩效费,bm.commission_rate*绩效费 as 比例绩效费" +
                " from bpm.employee_merchant_mapping as bm" +
                " inner join" +
                " (select m.Name,m.id as mid,a.绩效费" +
                " from " +
                "identities.merchant m " +
                "right join " +
                " (select" +
                " sum(b.settle_amount) as 绩效费,merchant_id" +
                " from" +
                " identities.balanceinfo b" +
                " inner join" +
                " identities.settlements_extend bb" +
                " on b.settlement_code = bb.settlement_code " +
                " where b.status IN(1000,5000) " +
                " group by b.merchant_id ) a " +
                " on m.id=a.merchant_id ) j " +
                " on bm.merchant_id=mid " +
                " where bm.employee_id =" + syxiashu;

        pstmt = connection.prepareStatement(sql);
        ResultSet a1 = pstmt.executeQuery(sql);
        List<String[]> bljxf = new ArrayList<String[]>();

        while (a1.next()) {

            System.out.println(a1.getString(1) + "\t" +
                    a1.getString(2) + "\t" + a1.getString(3) + "\t" + a1.getString(4)+ "\t" +
                    a1.getString(5) + "\t" + a1.getString(6) + "\t" + a1.getString(7));

            String[] bljxf1 = {a1.getString(1), a1.getString(2), a1.getString(3), a1.getString(4), a1.getString(5), a1.getString(6), a1.getString(7)};
            bljxf.add(bljxf1);


        }

    }

    //人对应不同商户的按比例计算后的绩效费（代码）
    //对应商户id和分佣比例
    public static List<String[]> Shanghu (String syxiashu) throws SQLException {

        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();
        PreparedStatement pstmt;

        String sql = "select merchant_id,commission_rate from bpm.employee_merchant_mapping where employee_id = "+syxiashu;

        pstmt = connection.prepareStatement(sql);
        ResultSet a1 = pstmt.executeQuery(sql);
        List<String[]> Shanghu = new ArrayList<String[]>();

        while (a1.next()) {

            System.out.println(a1.getString(1) + "\t" +
                    a1.getString(2));

            String[] shanghuidbl = {a1.getString(1), a1.getString(2)};
            Shanghu.add(shanghuidbl);

        }
        return Shanghu;

    }
    //对应商户的绩效费总和(1000和5000)
    public static List<String[]> jxf (List<String[]> Shanghu) throws SQLException {

        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();
        PreparedStatement pstmt;
        //调用上个方法获取对应商户id和分佣比例

        for(int i=0;i< Shanghu.size();i++){
            String syxiashu=Shanghu.get(i)[0];
//            System.out.println(XiashuList.get(i)[0]);


            //对应商户id
            Shanghu(syxiashu);

            //对应商户绩效费总和

        }







        String sql = "select " +
                "sum(b.settle_amount) as 绩效费" +
                "from " +
                "identities.balanceinfo b " +
                "inner join " +
                "identities.settlements_extend bb " +
                "on b.settlement_code = bb.settlement_code  " +
                " where b.status IN(1000,5000) and b.merchant_id ="+ Shanghu;

        pstmt = connection.prepareStatement(sql);
        ResultSet a1 = pstmt.executeQuery(sql);
        List<String[]> jxf = new ArrayList<String[]>();

        while (a1.next()) {

            System.out.println(a1.getString(1) + "\t" +
                    a1.getString(2));

            String[] jxf1 = {a1.getString(1), a1.getString(2)};
            jxf.add(jxf1);

        }
        return jxf;

    }







}











