package Model;

public class User {
	private String email;
	private String fullname;
	private Boolean role;
	
	public User() {
	}

//	public User(String email, String fullname) {
//		this.email = email;
//		this.fullname = fullname;
//	}

	public User(String email, String fullname, Boolean role) {
		this.email = email;
		this.fullname = fullname;
		this.role = role;
	}

	public boolean getRole() {
		return role;
	}

	public void setRole(Boolean role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
}
