import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;

public class Duishu {

    public static void main(String[] args) throws SQLException {

/*        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
        executor.submit(futureTask);
        executor.shutdown();

        Ywjxf thread = new Ywjxf();
        thread.start();*/
    }


    //获取当天日期
    public static String time() {

        Date date = new Date();
        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        //打印当天日期
        System.out.println(dates.format(date));

        //转化成String类型
        String time = date.toString();
        return time;

    }


    //业务当天绩效费总和
    public static String YwJx(String time) throws SQLException {

        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();

        String sql = "select sum(settle_amount) from identities.balanceinfo where `status` in (1000,5000) and deal_result_time like '" + time + "%'";


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
    public  String ScJx(String time) throws SQLException {

        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();

        String sql = "select sum(settle_amount) from wetax_data_report.new_crm_today_status_flink where dates like '" + time + "%'";


        //将sql语句发送到数据库
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet a = pstmt.executeQuery(sql);

        String ScJx = "";
        while (a.next()) {
            ScJx = a.getString(1);

        }
        //打印当天绩效费总和
        System.out.println(ScJx);
        return ScJx;

    }

    class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            String s = ScJx(null);
            return null;
        }
    }
    //创建线程-业务绩效费
    static class Ywjxf extends Thread {


        public Ywjxf() {

        }

        @Override
        public void run() {
            //调用输入当天日期
            String time = time();
            //调用业务当天绩效费总和
            String ywjx = YwJx(time);
        }

        private String YwJx(String time) {
            return time;
        }

    }
}









