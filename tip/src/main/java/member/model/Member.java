package member.model;

import java.util.Date;

public class Member {
	private String id;
	private String name;
	private String password;
	private Date regDate;
	
	public Member(String id, String name, String password, Date regDate) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.regDate = regDate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Date getRegDate() {
		return regDate;
	}
	
	public boolean matchPassword(String pwd) {
		return password.equals(pwd);
	}
	
	//암호 변경 기능을 위한 함수
	public void changePassword(String newPwd) {
		this.password = newPwd;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", password=" + password + ", regDate=" + regDate + "]";
	}
	
	
}
