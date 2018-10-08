package com.jwtAuthentication.jwtAuthentication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "employee_login", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"Username"
		})
})
public class EmployeeLogin {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private Long id;
	
	@NotBlank
	@Column(name = "username", length = 10, nullable = false)
    private String username;
	
	@NotBlank
	@Column(name = "password", length = 100, nullable = false)
    private String password;
	
	@Column(name = "enabled", nullable = false)
    private int enabled;

    @Column(name = "otpcode", length = 20, nullable = true)
    private String otpcode;

    @Column(name = "pre_hire_flag", length = 1, nullable = true)
    private String pre_hire_flag;
    
	public EmployeeLogin() {
		
	}


	public EmployeeLogin(Long id, @NotBlank String username, @NotBlank String password, int enabled, String otpcode,
			String pre_hire_flag) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.otpcode = otpcode;
		this.pre_hire_flag = pre_hire_flag;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getEnabled() {
		return enabled;
	}


	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}


	public String getOtpcode() {
		return otpcode;
	}


	public void setOtpcode(String otpcode) {
		this.otpcode = otpcode;
	}


	public String getPre_hire_flag() {
		return pre_hire_flag;
	}


	public void setPre_hire_flag(String pre_hire_flag) {
		this.pre_hire_flag = pre_hire_flag;
	}


	
}
