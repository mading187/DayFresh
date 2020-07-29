package fresh.dao;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * 改成JNDI：Java Naming and Directory Interface, Java命名和目录接口
 */
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * ★★★尤为重要
 * 更新：执行executeUpdate()操作
 *      更新包括添加、修改、删除
 * 查询：执行executeQuery()操作
 * @author Exhausted
 *
 */
public class DBHelper{

	/**
	 * 外部封装
	 * static块在程序加载的过程中执行一次而且只会执行一次
	 * 只要类在Java虚拟机，驱动便能加载一次
	 */
//	static{
//		//2.加载驱动--只需要在
//		try {
//			Class.forName(ReadConfig.getInstance().getProperty("driverClassName"));
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	/**
	 * 外部封装
	 * 获取连接的方法
	 * @return  返回获取到的名字
	 */
	private Connection getConnection(){
		
		//3.建立连接
		Connection conn = null;
		
		try {
//			conn = DriverManager.getConnection(ReadConfig.getInstance().getProperty("url"), ReadConfig.getInstance());
			
			//从连接池中获取一个空闲的链接
			Context context= new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/fresh");  //java:comp/env/  固定部分，看成协议不可变
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} 
		return conn;
	}

	
	
	
	
	/**
	 * 外部封装
	 * 关闭资源的方法
	 * @param rs     要关闭的结果集
	 * @param pstmt  要关闭的预编译对象
	 * @param conn   要关闭的连接
	 */
	private void close(ResultSet rs, PreparedStatement pstmt, Connection conn){
		
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(pstmt != null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
/*********************************************************************************************************************************	
*****多条件组合查询(不定参数形式)*****************************************************************************************************
*********************************************************************************************************************************/	
	
	/**
	 * 外部封装
	 * 给预编译块语句中的占位符?赋值
	 * @param pstmt
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 */
	private void setParams(PreparedStatement pstmt, Object ... params){
		
		if(params == null || params.length <= 0){  //说明没有参数给我，也就意味着执行的SQL语句中没有占位符?
			return;
		}
		
		//如果有参数
		for(int i = 0, len = params.length; i < len; i++){
			try {
				/**
				 * 在使用JDBC操作数据库时，常常调用PreparedStatement的对象使用setObject方法去遍历SQL语句传入的数据，这时常常需要一个一个的去判
				 * 断数据的类型，导致代码量成倍增加，而使用setObject可以自动识别SQL中的数据类型，对大量数据量节约的时间是非常可观的。
				 */
				pstmt.setObject(i + 1, params[i]);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("第" + (i + 1) + "个参数注值失败...");
			}
		}
	}
	
	
	
	
	
	/**
	 * 1.执行更新的方法
	 * List(序列)、ArrayList(数组)、Set(集合)
	 * @param sql     要执行的更新语句，可以是insert、delete、update
	 * @param params  要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return
	 */
	public int update(String sql, Object ... params){   //采用不定参数形式
		
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {

			conn = this.getConnection();			//建立连接
			pstmt = conn.prepareStatement(sql);	    //预编译执行语句
			this.setParams(pstmt, params);			//给预编译执行语句中的占位符赋值
			result = pstmt.executeUpdate();			//执行语句块获取结果或结果集(执行更新)
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.close(null, pstmt, conn);
		}
		return result;
	}
	

	
	
	/**
	 * 2.查询多行(一般情况下用Object对象比较少)
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         满足条件的数据，每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 * 
	 *   首先Map<String, Object>是定义了一个Map集合变量, 然后List<Map<String, Object>>是定义了一个List的集合变量, 是map的一个集合; map是
	 * list中的其中一个值。
	 *       List<Map<String,Object> list=new ArrayList<Map<String,Object>>;
	 *       Map<String,Object> map=new HashMap<String,Object>;
	 *       list.add(map);  //map是list中的其中一个值
	 * List集合中的对象是一个Map对象,而这个Map对象的键是String类型,值是Object类型
	 */
	public List<Map<String, Object>> finds(String sql, Object ... params){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();          //获取连接
			pstmt = conn.prepareStatement(sql);   //预编译语句
			this.setParams(pstmt, params);        //给预编译语句中的占位符赋值
		    rs = pstmt.executeQuery();            //执行查询
		    Map<String, Object> map = null;
		    
		    //如何获取结果集中列的列名 --> 取到列名后存到一个数组中，便于后面的循环取值  --> 如何确定数组的大小?
		    String[] colNames = this.getColumnNames(rs);

		    String colType = null;   //返回这个列数据的类型名称(新增)
		    Object obj = null;       //列的数据
		    Blob blob = null;
		    byte[] bt = null;
		    while(rs.next()) {   //每次循环得到一行数据
		    	map = new HashMap<String, Object>();
		    	
		    	//循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
		    	for(String colName : colNames) {
		    		
		    		/**
		    		 * 新增更改
		    		 */
		    		//首先我们不必获取返回的这个列的数据的类型是不是clob(字符数据), 如果是blob则转成字节数组并将这个数据存到本地
		    		obj = rs.getObject(colName);
		    		
		    		//判断如果一个对象为空
		    		if(obj == null){
		    			map.put(colName, obj);
		    			continue;
		    		}
		    		
		    		//获取这个列值对象的类型
		    		colType = obj.getClass().getSimpleName();
		    		
		    		if("BLOB".equals(colType)){
		    			//用blob获取然后转成字节数组
		    			blob = rs.getBlob(colName);
		    			bt = blob.getBytes(1, (int)blob.length());
		    			map.put(colName, bt);
		    		}else{
		    			map.put(colName, obj);
		    		}
		    		
		    	}
		    	list.add(map);
		    }   
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return list;
	}
	
	
	
	
	/**
	 * 2.查询多行(一般情况下用字符串(String)比较多)
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         满足条件的数据，每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public List<Map<String, String>> gets(String sql, Object ... params){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();          //获取连接
			pstmt = conn.prepareStatement(sql);   //预编译语句
			this.setParams(pstmt, params);        //给预编译语句中的占位符赋值
		    rs = pstmt.executeQuery();            //执行查询
		    Map<String, String> map = null;
		    
		    //如何获取结果集中列的列名 --> 取到列名后存到一个数组中，便于后面的循环取值  --> 如何确定数组的大小?
		    ResultSetMetaData rsmd = rs.getMetaData();   //获取结果集中的元数据
		    int colCount = rsmd.getColumnCount();        //获取结果集中列的数量
		    String[] colNames = new String[colCount];
		    
		    for(int i = 1; i <= colCount; i++) {   //循环获取结果集中列的名字
		    	colNames[i - 1] = rsmd.getColumnName(i).toLowerCase();    //将列名改成小写存到数组中(toLowerCase()改成小写)
		    }
		    
		    while(rs.next()) {   //每次循环得到一行数据
		    	map = new HashMap<String, String>();
		    	
		    	//循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
		    	for(String colName : colNames) {
		    		map.put(colName, rs.getString(colName));
		    	}
		    	list.add(map);
		    }   
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return list;
	}
	
	
	
	
	/**
	 * 外部封装
	 * 获取结果集中的所有列的类名
	 * @param rs  结果集对象
	 * @return
	 * @throws SQLException
	 */
	private String[] getColumnNames(ResultSet rs) throws SQLException {

		ResultSetMetaData rsmd = rs.getMetaData();    //获取结果集中的元数据
		int colCount = rsmd.getColumnCount();         //获取结果集中列的数量
		String[] colNames = new String[colCount];
		for(int i = 1; i <= colCount; i++) {   //循环获取结果集中列的名字
			
			colNames[i - 1] = rsmd.getColumnName(i).toLowerCase();
		}
		return colNames;
	}	
	
	
	
	/**
	 * 2.查询单行
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         find以Object(对象)形式返回
	 */
	public Map<String, Object> find(String sql, Object ... params){
		Map<String, Object> map = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();          //获取连接
			pstmt = conn.prepareStatement(sql);   //预编译语句
			this.setParams(pstmt, params);        //给预编译语句中的占位符赋值
			rs = pstmt.executeQuery();            //执行查询

			String[] colNames = this.getColumnNames(rs);
			
			String colType = null;   //返回这个列数据的类型名称(新增)
		    Object obj = null;       //列的数据
		    Blob blob = null;
		    byte[] bt = null;
			
			//因为只查询一行数据，所以if()代替while()
			if(rs.next()) {
				map = new HashMap<String, Object>();
				
				//循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for(String colName : colNames) {
					/**
		    		 * 新增更改
		    		 */
		    		//首先我们不必获取返回的这个列的数据的类型是不是clob(字符数据), 如果是blob则转成字节数组并将这个数据存到本地
		    		obj = rs.getObject(colName);
		    		
		    		//判断如果一个对象为空
		    		if(obj == null){
		    			map.put(colName, obj);
		    			continue;
		    		}
		    		
		    		//获取这个列值对象的类型
		    		colType = obj.getClass().getSimpleName();
		    		
		    		if("BLOB".equals(colType)){
		    			//用blob获取然后转成字节数组
		    			blob = rs.getBlob(colName);
		    			bt = blob.getBytes(1, (int)blob.length());
		    			map.put(colName, bt);
		    		}else{
		    			map.put(colName, obj);
		    		}
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return map;
	}
	
	
	
	/**
	 * 2.查询单行
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         get以String(字符串)形式返回
	 */
	public Map<String, String> get(String sql, Object ... params){
		Map<String, String> map = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();          //获取连接
			pstmt = conn.prepareStatement(sql);   //预编译语句
			this.setParams(pstmt, params);        //给预编译语句中的占位符赋值
			rs = pstmt.executeQuery();            //执行查询

			String[] colNames = this.getColumnNames(rs);
			
			//因为只查询一行数据，所以if()代替while()
			if(rs.next()) {
				map = new HashMap<String, String>();
				
				//循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for(String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return map;
	}
	
	
	
	/**
	 * 2.查询获取总记录数的方法(一行一列)
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         总记录数
	 */
	public int total(String sql, Object... params) {

		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection();        //获取连接
			pstmt = conn.prepareStatement(sql); //预编译语句
			this.setParams(pstmt, params);      //给预编译语句中的占位符赋值
			rs = pstmt.executeQuery();          //执行查询

			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, conn);
		}
		return result;
	}
	
	
	
	

	
	
	
/*********************************************************************************************************************************	
*****多条件组合查询(集合方式)********************************************************************************************************	
*********************************************************************************************************************************/	
	
	/**
	 * 外部封装(新改)
	 * 给预编译块语句中的占位符?赋值
	 * @param pstmt
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 */
	private void setParams(PreparedStatement pstmt, List<Object> params){
		
		if(params == null || params.isEmpty()){  //说明没有参数给我，也就意味着执行的SQL语句中没有占位符?
			return;
		}
		
		//如果有参数
		for(int i = 0, len = params.size(); i < len; i++){
			try {
				/**
				 * 在使用JDBC操作数据库时，常常调用PreparedStatement的对象使用setObject方法去遍历SQL语句传入的数据，这时常常需要一个一个的去判
				 * 断数据的类型，导致代码量成倍增加，而使用setObject可以自动识别SQL中的数据类型，对大量数据量节约的时间是非常可观的。
				 */
				pstmt.setObject(i + 1, params.get(i));
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("第" + (i + 1) + "个参数注值失败...");
			}
		}
	}
	
	
	
	/**
	 * 1.执行更新的方法(新改)
	 * List(序列)、ArrayList(数组)、Set(集合)
	 * @param sql     要执行的更新语句，可以是insert、delete、update
	 * @param params  要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return
	 */
	public int update(String sql, List<Object> params){   //采用不定参数形式
		
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = this.getConnection();			//建立连接
			pstmt = conn.prepareStatement(sql);	    //预编译执行语句
			this.setParams(pstmt, params);			//给预编译执行语句中的占位符赋值
			result = pstmt.executeUpdate();			//执行语句块获取结果或结果集(执行更新)
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.close(null, pstmt, conn);
		}
		return result;
	}
	
	/**
	 * 1.执行更新的方法(新改)
	 * List(序列)、ArrayList(数组)、Set(集合)
	 * @param sql     要执行的更新语句，可以是insert、delete、update
	 * @param params  要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return
	 */
//	public int update(String sql, Object ... params){   
//		
//		int result = -1;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			
//			conn = this.getConnection();			//建立连接
//			pstmt = conn.prepareStatement(sql);	    //预编译执行语句
//			this.setParams(pstmt, params);			//给预编译执行语句中的占位符赋值
//			result = pstmt.executeUpdate();			//执行语句块获取结果或结果集(执行更新)
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			this.close(null, pstmt, conn);
//		}
//		return result;
//	}
//	
	
	/**
	 * 2.查询多行(一般情况下用Object对象比较少)
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         满足条件的数据，每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public List<Map<String, Object>> finds(String sql, List<Object> params){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();          //获取连接
			pstmt = conn.prepareStatement(sql);   //预编译语句
			this.setParams(pstmt, params);        //给预编译语句中的占位符赋值
			rs = pstmt.executeQuery();            //执行查询
			Map<String, Object> map = null;
			
			//如何获取结果集中列的列名 --> 取到列名后存到一个数组中，便于后面的循环取值  --> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);
			
			String colType = null;   //返回这个列数据的类型名称(新增)
			Object obj = null;       //列的数据
			Blob blob = null;
			byte[] bt = null;
			while(rs.next()) {   //每次循环得到一行数据
				map = new HashMap<String, Object>();
				
				//循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for(String colName : colNames) {
					
					/**
					 * 新增更改
					 */
					//首先我们不必获取返回的这个列的数据的类型是不是clob(字符数据), 如果是blob则转成字节数组并将这个数据存到本地
					obj = rs.getObject(colName);
					
					//判断如果一个对象为空
					if(obj == null){
						map.put(colName, obj);
						continue;
					}
					
					//获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					
					if("BLOB".equals(colType)){
						//用blob获取然后转成字节数组
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int)blob.length());
						map.put(colName, bt);
					}else{
						map.put(colName, obj);
					}
					
				}
				list.add(map);
			}   
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return list;
	}
	
	
	
	/**
	 * 2.查询多行(一般情况下用字符串(String)比较多)(新改)
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         满足条件的数据，每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public List<Map<String, String>> gets(String sql, List<Object> params){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();          //获取连接
			pstmt = conn.prepareStatement(sql);   //预编译语句
			this.setParams(pstmt, params);        //给预编译语句中的占位符赋值
			rs = pstmt.executeQuery();            //执行查询
			Map<String, String> map = null;
			
			//如何获取结果集中列的列名 --> 取到列名后存到一个数组中，便于后面的循环取值  --> 如何确定数组的大小?
			ResultSetMetaData rsmd = rs.getMetaData();   //获取结果集中的元数据
			int colCount = rsmd.getColumnCount();        //获取结果集中列的数量
			String[] colNames = new String[colCount];
			
			for(int i = 1; i <= colCount; i++) {   //循环获取结果集中列的名字
				colNames[i - 1] = rsmd.getColumnName(i).toLowerCase();    //将列名改成小写存到数组中(toLowerCase()改成小写)
			}
			
			while(rs.next()) {   //每次循环得到一行数据
				map = new HashMap<String, String>();
				
				//循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for(String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
				list.add(map);
			}   
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return list;
	}
	
	
	
	
	/**
	 * 2.查询单行(新改)
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         find以Object(对象)形式返回
	 */
	public Map<String, Object> find(String sql, List<Object> params){
		Map<String, Object> map = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();          //获取连接
			pstmt = conn.prepareStatement(sql);   //预编译语句
			this.setParams(pstmt, params);        //给预编译语句中的占位符赋值
			rs = pstmt.executeQuery();            //执行查询
			
			String[] colNames = this.getColumnNames(rs);
			
			String colType = null;   //返回这个列数据的类型名称(新增)
			Object obj = null;       //列的数据
			Blob blob = null;
			byte[] bt = null;
			
			//因为只查询一行数据，所以if()代替while()
			if(rs.next()) {
				map = new HashMap<String, Object>();
				
				//循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for(String colName : colNames) {
					/**
					 * 新增更改
					 */
					//首先我们不必获取返回的这个列的数据的类型是不是clob(字符数据), 如果是blob则转成字节数组并将这个数据存到本地
					obj = rs.getObject(colName);
					
					//判断如果一个对象为空
					if(obj == null){
						map.put(colName, obj);
						continue;
					}
					
					//获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					
					if("BLOB".equals(colType)){
						//用blob获取然后转成字节数组
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int)blob.length());
						map.put(colName, bt);
					}else{
						map.put(colName, obj);
					}
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return map;
	}
	
	
	
	/**
	 * 2.查询单行(新改)
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         get以String(字符串)形式返回
	 */
	public Map<String, String> get(String sql, List<Object> params){
		Map<String, String> map = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();          //获取连接
			pstmt = conn.prepareStatement(sql);   //预编译语句
			this.setParams(pstmt, params);        //给预编译语句中的占位符赋值
			rs = pstmt.executeQuery();            //执行查询
			
			String[] colNames = this.getColumnNames(rs);
			
			//因为只查询一行数据，所以if()代替while()
			if(rs.next()) {
				map = new HashMap<String, String>();
				
				//循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for(String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.close(rs, pstmt, conn);
		}
		return map;
	}
	
	
	
	/**
	 * 2.查询获取总记录数的方法(一行一列)(新改)
	 * @param sql      要执行的查询语句 	
	 * @param params   要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return         总记录数
	 */
	public int total(String sql, List<Object> params) {
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();        //获取连接
			pstmt = conn.prepareStatement(sql); //预编译语句
			this.setParams(pstmt, params);      //给预编译语句中的占位符赋值
			rs = pstmt.executeQuery();          //执行查询
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, conn);
		}
		return result;
	}
	
	
	private Map<String, Method> getSetters (Class<?> clazz) {
		// 获取给定类中的setter方法
	
		Method[] methods = clazz.getMethods(); //得到给定类 中的所有公共方法
		
		//从中提取出setter方法，已方法名为键，对应的方法为值，注意:我这里会将所有的方法名全部转成小写字母的方法
		Map<String, Method> setters = new HashMap<String, Method>() ;
		
		String methodName = null;
		for(Method method : methods){
			methodName = method.getName().toLowerCase() ;   //获取当前方法的方法名
			if (methodName.startsWith("set")) {   //说明是set方法
			setters.put(methodName, method) ;
			}
		}
		return setters;
	}


	
	
	/**
	 * 泛型:参数化类型: 将类型由原来的具体的类型参数化，类似于方法中的变里参数，此时类型也定义成参数形式，即类型形参
	 * T (Type Java类) E (Element,在集合中使用，因为集合中存放的都是元素) ?表示不确定的java类型 K(Key) V (Value) N (Number数值类型)基于实体类对象的查询
	 * @param <T>
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> finds(Class<T> clazz, String sql, Object ... params){
		List<T> list = new ArrayList<T>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			
			rs = pstmt.executeQuery(); // 执行查询
			
			

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);
			Map<String,Method> setters = getSetters(clazz);
			String methodName = null;
			T t;
			Method method = null;
			Class<?>[] types = null;
			Class<?> type = null;
			while (rs.next()) {
				t = clazz.newInstance();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for(String colName : colNames) {
					methodName = "set" + colName;
					method = setters.get(methodName);
					if(method == null) {
						continue; //直接执行下一次循环
					}
					
					//获取这个方法的参数列表，考虑到正常的setter方法只会有一个参数，所以我们只取第一个参数的类型
					types = method.getParameterTypes();
					
					if (types != null) {
						type = types[0];
					}
					
					if (Integer.TYPE == type || Integer.class == type) {
						method.invoke(t, rs.getInt(colName));
					}else if (Double.TYPE == type || Double.class == type) {
						method.invoke(t, rs.getDouble (colName));
					}else if (Float.TYPE == type || Float.class == type) {
						method.invoke(t, rs.getFloat (colName));
					}else {
						method.invoke(t, String.valueOf(rs.getObject(colName)));
					}
				}
				list.add(t);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e. printStackTrace() ;
		}catch (IllegalAccessException e) {
			e .printStackTrace() ;
		}catch (IllegalArgumentException e) {
			e. printStackTrace() ; 
		}catch (InvocationTargetException e) {
			e. printStackTrace() ;
		}finally {
			this.close (rs, pstmt, con) ;
		}
		return list;
	}
	public <T> T find(Class<T> clazz, String sql,Object ... params){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		T t = null;
		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			
			rs = pstmt.executeQuery(); // 执行查询
			
			//获取结果集中所有列的列名
			String[] colNames = this.getColumnNames(rs);
			
			//获取指定类的所有setter方法
			Map<String,Method> setters = this.getSetters(clazz);
			
			String methodName = null;
			Class<?>[] types = null;
			Class<?> type = null;
			Method method = null;
			
			if(rs.next()) {
				t = clazz.newInstance();
				for(String colName : colNames) {
					methodName = "set" + colName;
					method = setters.get(methodName);
					if(method == null) {
						continue;
					}
					types = method.getParameterTypes();
					if(types == null) {
						continue;
					}
					type = types[0];
					if(Integer.TYPE == type || Integer.class == type) {
						method.invoke(t, rs.getInt(colName));
					}else if(Double.TYPE == type || Double.class == type) {
						method.invoke(t, rs.getDouble (colName));
					}else if(Float.TYPE == type || Float.class == type) {
						method.invoke(t, rs.getFloat (colName));
					}else {
						method.invoke(t, String.valueOf(rs.getObject(colName)));
					}
				}
			}
		}catch (InstantiationException e) {
			e. printStackTrace() ;
		}catch (IllegalAccessException e) {
			e .printStackTrace() ;
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e. printStackTrace() ; 
		}catch (InvocationTargetException e) {
			e. printStackTrace() ;
		}finally {
			this.close (rs, pstmt, con) ;
		}
		return t;
	}
	@SuppressWarnings ("hiding")
	public <T> List<T> finds(Class<T> clazz, String sql, List<Object> params){
		List<T> list = new ArrayList<T>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection();   //获取连接
			pstmt = con.prepareStatement(sql);   //预编译语句
			this.setParams(pstmt, params);   //给预编译语句中的占位符赋值
			
			rs = pstmt.executeQuery();   //执行查询
			
			

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);
			Map<String,Method> setters = getSetters(clazz);
			String methodName = null;
			T t;
			Method method = null;
			Class<?>[] types = null;
			Class<?> type = null;
			while (rs.next()) {
				t=clazz.newInstance();
				
				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					methodName = "set" + colName;
					method = setters.get(methodName);
					if (method == null) {
						continue; //直接执行下一次循环
					}
					
					//获取这个方法的参数列表，考虑到正常的setter方法只会有一个参数，所以我们只取第一个参数的类型
					types = method. getParameterTypes();
					
					if (types != null) {
						type = types[0];
					}
					
					if (Integer.TYPE == type || Integer.class == type) {
						method. invoke(t, rs.getInt(colName));
					}else if (Double.TYPE == type||Double.class == type) {
						method.invoke(t, rs.getDouble (colName));
					}else if (Float.TYPE == type || Float.class == type) {
						method.invoke(t, rs.getFloat (colName));
					}else {
						method.invoke(t, String.valueOf(rs.getObject(colName)));
					}
				}
				list.add(t);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e. printStackTrace() ;
		}catch (IllegalAccessException e) {
			e .printStackTrace() ;
		}catch (IllegalArgumentException e) {
			e. printStackTrace() ; 
		}catch ( InvocationTargetException e) {
			e. printStackTrace() ;
		}finally {
			this.close (rs, pstmt, con) ;
		}
		return list;
	}
	public <T> T find(Class<T> clazz,String sql,List<Object> params){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		T t=null;
		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			
			rs = pstmt.executeQuery(); // 执行查询
			
			//获取结果集中所有列的列名
			String[] colNames = this.getColumnNames(rs);
			
			//获取指定类的所有setter方法
			Map<String,Method> setters = this.getSetters(clazz);
			
			String methodName = null;
			Class<?>[] types = null;
			Class<?> type = null;
			Method method = null;
			
			
			if(rs.next()) {
				t = clazz.newInstance();
				for(String colName : colNames) {
					methodName = "set" + colName;
					method = setters.get(methodName);
					if(method == null) {
						continue;
					}
					types = method.getParameterTypes();
					if(types == null) {
						continue;
					}
					type = types[0];
					if (Integer.TYPE == type || Integer.class == type) {
						method. invoke(t, rs.getInt(colName));
					}else if (Double.TYPE == type || Double.class == type) {
						method.invoke(t, rs.getDouble (colName));
					}else if (Float.TYPE == type || Float.class == type) {
						method.invoke(t, rs.getFloat (colName));
					}else {
						method.invoke(t, String.valueOf(rs.getObject(colName)));
					}
				}
			}
		}catch (InstantiationException e) {
			e. printStackTrace() ;
		}catch (IllegalAccessException e) {
			e .printStackTrace() ;
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e. printStackTrace() ; 
		}catch (InvocationTargetException e) {
			e. printStackTrace() ;
		}finally {
			this.close(rs, pstmt, con) ;
		}
		return t;
	}
}
