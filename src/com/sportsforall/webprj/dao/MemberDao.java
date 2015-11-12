package com.sportsforall.webprj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sportsforall.webprj.vo.Member;

public class MemberDao {
	public List<Member> getMembers() throws SQLException{
		return getMembers(1);
	
	}
	public List<Member> getMembers(int page) throws SQLException{
		
		int start = 1+(page-1)*10;//1,11,21,31,41; a1+(n-1)d->1+(page-1)*d
		int end = page*10;
		
		String sql = "SELECT *"
				    +"FROM (SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC)NUM,MEMBERS.* FROM MEMBERS)A "
				    +"WHERE NUM BETWEEN " + start+ " AND " +end;

		// 오라클 String url = "jdbc:oracle:thin:@211.238.142.251:1521:orcl";
		String url = "jdbc:sqlserver://211.238.142.251:1433;databaseName=edudb";

		Connection con = DriverManager.getConnection(url, "edu", "class2d");// 연결
		Statement st = con.createStatement();// 실행

		ResultSet rs = st.executeQuery(sql);// select

		List<Member> list = new ArrayList<Member>();
		Member member = null;
	
		
		while (rs.next()){
			member = new Member();
			
			member.setMid(rs.getString("mid"));
			
			member.setPwd(rs.getString("pwd"));
			member.setRegDate(rs.getDate("RegDate"));
			//member.setAge(rs.getInt("age"));
			member.setName(rs.getString("name"));
			/*member.setGender(rs.getString("gender"));
			member.setBirthday(rs.getString("birthday"));
			member.setMajor(rs.getString("major"));
			member.setAddress(rs.getString("address"));
			member.setPhone(rs.getString("phone"));
			member.setSsn(rs.getString("ssn"));
			member.setIp(rs.getString("ip"));*/
			list.add(member);
		}
			
			
			
		rs.close();
		st.close();
		con.close();
		
		return list;
	}
}
