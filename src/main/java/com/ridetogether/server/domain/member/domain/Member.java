package com.ridetogether.server.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ridetogether.server.domain.image.domain.Image;
import com.ridetogether.server.domain.matching.domain.MemberMatching;
import com.ridetogether.server.domain.member.dto.MemberDto.MemberUpdateDto;
import com.ridetogether.server.domain.member.dto.MemberRequestDto.UpdateMemberRequestDto;
import com.ridetogether.server.domain.member.model.ActiveState;
import com.ridetogether.server.domain.member.model.Bank;
import com.ridetogether.server.domain.member.model.Gender;
import com.ridetogether.server.domain.member.model.PayType;
import com.ridetogether.server.domain.member.model.StudentStatus;
import com.ridetogether.server.domain.member.model.Role;
import com.ridetogether.server.global.common.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "Member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

	private static final String HANYANG_EMAIL = "@hanyang.ac.kr";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_idx")
	private Long idx;

	@Column(nullable = false, unique = true)
	private String memberId;

	private String name;

	private String password;

	@Column(nullable = false, unique = true)
	private String nickName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String kakaoPayUrl;

	private String account;

	@Enumerated(EnumType.STRING)
	private Bank accountBank;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<PayType> payTypes;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private StudentStatus studentStatus;

	@Enumerated(EnumType.STRING)
	private ActiveState activeState;

	@Column(length = 1000)
	private String refreshToken;

	@OneToMany(mappedBy = "member")
	@JsonIgnore
	private List<Image> images;

	@OneToMany(mappedBy = "membermatching")
	@JsonIgnore
	private List<MemberMatching> memberMatching;

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void destroyRefreshToken() {
		this.refreshToken = null;
	}

	public void setStudentStatus(String memberId) {
		if (memberId.contains(HANYANG_EMAIL)) {
			this.studentStatus = StudentStatus.STUDENT;
		} else {
			this.studentStatus = StudentStatus.NOT_STUDENT;
		}
	}

	public boolean isStudent() {
		return this.role == Role.ROLE_STUDENT;
	}

	public boolean isAdmin() {
		return this.role == Role.ROLE_ADMIN;
	}

	public void updateMember(MemberUpdateDto dto) {
		this.name = dto.getName();
		this.nickName = dto.getNickName();
		this.gender = dto.getGender();
		this.kakaoPayUrl = dto.getKakaoPayUrl();
		this.account = dto.getAccount();
		this.accountBank = dto.getAccountBank();
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateStudentStatus(StudentStatus studentStatus) {
		this.studentStatus = studentStatus;
	}

	public void updateRole(Role role) {
		this.role = role;
	}
}
