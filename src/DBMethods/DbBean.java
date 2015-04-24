package DBMethods;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbBean {
	private String jdbcUrl = "jdbc:mysql://localhost:3306/mayor";
    private String username = "root";
    private String password = "1234";
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public List  SelectRS(String sql)  {
    	List list = new ArrayList();
        Connection conn = null;
        SQLException ex = null;
    	Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
	
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            if (!conn.isClosed()) {
            	stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				 rsmd = rs.getMetaData();
				while (rs.next()) {
					Map map = new HashMap(); 
					int columnCount = rsmd.getColumnCount();
					for(int i=0;i<columnCount;i++){
						String columnName = rsmd.getColumnName(i+1);
						map.put(columnName, rs.getObject(i+1));
					}
					list.add(map);

				}
            }
        } catch (SQLException e) {
            ex = e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    if(ex == null) {
                        ex = e;
                    }
                }
            }
            if(ex != null) {
                throw new RuntimeException(ex);
            }
        }
        return list;
    }
    public List  SelectRSwhere(String sql,List paraObj)  {
    	List list = new ArrayList();
        Connection conn = null;
        SQLException ex = null;
        PreparedStatement preparedStatement  = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
	
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            if (!conn.isClosed()) {
            	preparedStatement = conn.prepareStatement(sql);
            	for(int i=0;i<paraObj.size();i++)
					preparedStatement.setObject(i+1,paraObj.get(i));
				rs = preparedStatement.executeQuery();
				 rsmd = rs.getMetaData();
				while (rs.next()) {
					Map map = new HashMap(); 
					int columnCount = rsmd.getColumnCount();
					for(int i=0;i<columnCount;i++){
						String columnName = rsmd.getColumnName(i+1);
						map.put(columnName, rs.getObject(i+1));
					}
					list.add(map);

				}
            }
        } catch (SQLException e) {
            ex = e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    if(ex == null) {
                        ex = e;
                    }
                }
            }
            if(ex != null) {
                throw new RuntimeException(ex);
            }
        }
        return list;
    }
    
    public int InsertData(String sql,List paraObj){
    	Connection conn = null;
		PreparedStatement preparedStatement = null;
		int result = 0;
		
		try{
		
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			 conn = DriverManager.getConnection(jdbcUrl, username, password);
		
			 preparedStatement = conn.prepareStatement(sql);
			
			// 使用 Statement 執行 SQL 敘述
			
			for(int i=0;i<paraObj.size();i++)
				preparedStatement.setObject(i+1,paraObj.get(i));
			result = preparedStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println("執行SQL\"" + sql + "\"時發生例外：" + e.getMessage());
		}finally{
			if(preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
    }  
    
}