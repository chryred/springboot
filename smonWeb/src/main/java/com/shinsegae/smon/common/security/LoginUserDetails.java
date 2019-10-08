package com.shinsegae.smon.common.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/*******************************
 * 1. 프로젝트명 : monitoringWeb
 * 2. 패키지명   : com.sec
 * 3. 클래스명   : LoginUserDetails.java
 * 4. 작성일     : 2018.01.17
 * 5. 작성자     : 김성일
 * 6. 설명       : Spring Security UserDetails Implement Class
 *                  로그인ID로 조회한 사용자 정보와 Session 정보를 설정
 ********************************/
public class LoginUserDetails implements UserDetails {


	private static final long serialVersionUID = -8281389842757545283L;
	
	private String mgrId;
	private String mgrPwd;
	private String mgrName;
	private String mgrSys;
	private String mgrGrade;
	private String certNum;
	
	private String sid;
	private String userIp;
	private String userHostName;
	private String roleGroup;
	
	public LoginUserDetails(String mgrId, String mgrPwd, String mgrName, Collection<? extends GrantedAuthority> authorities) {
		this.mgrId = mgrId;
		this.mgrPwd = mgrPwd;
		this.mgrName = mgrName;
		this.roleGroup = authorities.iterator().next().getAuthority();
				
		Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	public LoginUserDetails(String mgrName, Collection<? extends GrantedAuthority> authorities) {
		this.mgrName = mgrName;
		Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");

		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;

	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			// Neither should ever be null as each entry is checked before adding it to the
			// set.
			// If the authority is null, it is a custom authority and should precede others.
			if (g2.getAuthority() == null) {
				return -1;
			}

			if (g1.getAuthority() == null) {
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public String getMgrPwd() {
		return mgrPwd;
	}

	public void setMgrPwd(String mgrPwd) {
		this.mgrPwd = mgrPwd;
	}

	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public String getMgrSys() {
		return mgrSys;
	}

	public void setMgrSys(String mgrSys) {
		this.mgrSys = mgrSys;
	}

	public String getMgrGrade() {
		return mgrGrade;
	}

	public void setMgrGrade(String mgrGrade) {
		this.mgrGrade = mgrGrade;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}	
	
	public String getUserHostName() {
		return userHostName;
	}

	public void setUserHostName(String userHostName) {
		this.userHostName = userHostName;
	}
	
	public String getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}

	@Override
	public String getPassword() {
		return this.mgrPwd;
	}

	@Override
	public String getUsername() {
		return this.mgrName;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
