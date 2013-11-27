package mcuploader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author siddharth.isaiah@zocdoc.com
 */
public class IpRetrieval {
    
    private static String db_ip = "192.168.200.61";
    private static String db_user = "kapow";
    private static String db_pass = "kapow";
    private static String db_name = "KapowTest";
    
    private static List IP_Address = new ArrayList();
    
    public static List sqlFu() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String db_url = "jdbc:sqlserver://"+db_ip+";user="+db_user+";password="+db_pass+";database=" + db_name;
        Connection conn = DriverManager.getConnection(db_url);
        Statement sta = conn.createStatement();
	String Sql = "select * from MCIP_list";
	ResultSet rs = sta.executeQuery(Sql);
	while (rs.next()) {
		IP_Address.add(rs.getString("IP_Address"));                
	}
    
    return IP_Address;
       
    }
    
}