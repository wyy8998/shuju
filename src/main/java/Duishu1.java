import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;

public class Duishu1 {

    public static void main(String[] args) throws SQLException {


        //调用数仓当天绩效费总和时间
        Map<String, Object> stringObjectMap = ScJx();
        //获取数仓当天绩效费
        String scJx = (String) stringObjectMap.get("ScJx");

        //调用业务当天绩效费总和
        String ywjx = YwJx();

        //对比是否一致
        System.out.print("当天绩效费总和："+scJx.equals(ywjx));

    }





    //业务当天绩效费总和
    public static String YwJx() throws SQLException {
        Map<String, Object> stringObjectMap = ScJx();
        String date = (String) stringObjectMap.get("date");
        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();

        String sql = "select sum(settle_amount) from identities.balanceinfo where `status` in (1000,5000) and deal_result_time >date_sub(curdate(),interval 1 day) and deal_result_time<='"+date+"'";


        //将sql语句发送到数据库
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet a = pstmt.executeQuery(sql);

        String YwJx = "";
        while (a.next()) {
            YwJx = a.getString(1);

        }
        //打印当天绩效费总和
        System.out.println(YwJx);
        return YwJx;

    }


    //数仓当天绩效费总和
    public static Map<String,Object> ScJx() throws SQLException {
        Map<String,Object>map= new HashMap<String, Object>();
        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();

        Date date = new Date();
        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dates.format(date);

        String sql = "select sum(settle_amount) from wetax_data_report.new_crm_today_status_flink where dates =CURRENT_DATE";

        //将sql语句发送到数据库
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet a = pstmt.executeQuery(sql);

        String ScJx = "";
        while (a.next()) {
            ScJx = a.getString(1);

        }
        //打印当天绩效费总和
        System.out.println(ScJx);

        map.put("date",format);
        map.put("ScJx",ScJx);
        return map;

    }



}









