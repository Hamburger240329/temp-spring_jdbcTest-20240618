package com.gyojincompany.jdbc;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gyojincompany.jdbc.command.MJoinCommand;
import com.gyojincompany.jdbc.dao.MemberDao;
import com.gyojincompany.jdbc.dto.MemberDto;

@Controller
public class JdbcController {
	
	@RequestMapping(value = "/test")
	public void test() {
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/spring_projectdb";
		String username = "root";
		String password = "12345";
		
		Connection conn = null;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			System.out.println(conn);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "/join")
	public String join() {
		return "join";
	}
	
	@RequestMapping(value = "/joinOk")
	public String joinOk(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		
		MJoinCommand mJoinCommand = new MJoinCommand();
		int success = mJoinCommand.execute(model);
		
//		String mid = request.getParameter("mid");
//		String mpw = request.getParameter("mpw");
//		String mname = request.getParameter("mname");
//		String memail = request.getParameter("memail");
		
//		MemberDao memberDao = new MemberDao();
		
//		int success = memberDao.joinMember(mid, mpw, mname, memail);
		//success 값이 1이면 sql문 실행 성공 아니면 실패
		
		if(success == 1 ) { //회원 가입 성공
			
			model.addAttribute("mid", request.getAttribute("mid"));
			model.addAttribute("mname", request.getAttribute("mname"));
			
			return "joinOk";
		} else {
			model.addAttribute("error", "회원가입이 실패하였습니다. 다시 시도해주세요.");
			
			return "join";
		}
		
		
	}
	
	
	
	
	

}
