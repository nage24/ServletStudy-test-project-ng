package com.servlet.study.web.domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servlet.study.web.domain.db.DBConnectionMgr;

public class UserRepositoryTest {
	
	private DBConnectionMgr pool;
	
	public UserRepositoryTest() {
		pool = DBConnectionMgr.getInstance(); // db 연결; 싱글톤 객체를 가져옴 
	}
	
	public List<Map<String, Object>> getUserList() { // 연결한 db에서 
		// sql문을 실행을 해서, 가져온 데이터를 map에 넣고 그걸 리스트로 반환해줄 거임. 
		String sql = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
		// list 생성
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		try {
			// 여기서 모든 작업을 해야 하는거임 ~ 
			con = pool.getConnection(); // db 연결
			sql = "SELECT\r\n"
					+ "	um.user_code,\r\n"
					+ "	um.user_id,\r\n"
					+ "	um.user_password,\r\n"
					+ "	um.user_name,\r\n"
					+ "	um.user_email,\r\n"
					+ "	ud.user_phone,\r\n"
					+ "	ud.user_address\r\n"
					+ "FROM\r\n"
					+ "	user_mst um\r\n"
					+ "	LEFT OUTER JOIN user_dtl ud ON(ud.user_code = um.user_code);";
			pstmt = con.prepareStatement(sql); // 연결한 db객체에 작성한 sql문을 넣어줌. 
			rs = pstmt.executeQuery(); 	// 결과셋에 sql문 실행 결과를 넣어줌. 
			rsmd = rs.getMetaData(); 	// 메타 데이터를 따로 가져와줌. 
			
			while(rs.next()) { // 실행결과셋을 반복해서 하나하나의 유저 정보를 가져올 거다. 
				Map<String, Object> map = new TreeMap<String, Object>(); // string은 user_code 이런 이름들... object는 값을 넣어줄거임
				
				for(int i = 0; i < rsmd.getColumnCount(); i++) {
					int index = i + 1;
					Object value = null;
					
					if(rsmd.getColumnType(index) == Types.INTEGER) {
						value = rs.getInt(index); 
						
					}else if(rsmd.getColumnType(index) == Types.VARCHAR) {
						value = rs.getString(index);
						
					}else if(rsmd.getColumnType(index) == Types.TIMESTAMP) {
						value = rs.getTimestamp(index).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					}
					
					map.put(rsmd.getColumnName(index), value);
				}
				
				list.add(map);
			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			System.out.println(gson.toJson(list));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // 연결 해제 하겠다 ! 꼭 해줘야 해용
		}
		
		return list;
		
	}
	
}
