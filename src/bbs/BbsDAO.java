package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BbsDAO {
	//DAO는 데이터 접근 객체 실제로 데이터를 빼올수있게함
	private Connection conn;
	
	private ResultSet rs;
	
	public BbsDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		return ""; // db오류
	}
	
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";//DESC 내림차순(가장최근의것부터가져옴)
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1; // 마지막에 쓰인글 +1 이 다음글의 번호가 됨 (나온결과에 +1)
			}
			 return 1;//첫게시물 인 경우
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		return -1; // db오류
	}
	
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES(?, ?, ?, ?, ?, ?)";//6개인자 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext()); //다음번에 쓰여야될 게시글 번호
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5,bbsContent);
			pstmt.setInt(6, 1); //어베일러블 글을 보여줘야하니 1을 넣어줌(삭제가안된상태)
			
			return pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		return -1; // db오류
	}
	
	
	
}
