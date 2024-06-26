package com.ridetogether.server.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridetogether.server.domain.member.dao.MemberRepository;
import com.ridetogether.server.global.apiPayload.ApiResponse;
import com.ridetogether.server.global.security.dto.LoginResponseDto.LoginDto;
import com.ridetogether.server.global.security.domain.JwtToken;
import com.ridetogether.server.global.security.application.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtService jwtService;

	private final MemberRepository memberRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		String memberId = extractMemberId(authentication);
		JwtToken jwtToken = jwtService.createJwtToken(authentication);

		jwtService.sendAccessAndRefreshToken(response, jwtToken);
		log.info( "로그인에 성공합니다. memberId: {}" , memberId);
		log.info( "AccessToken 을 발급합니다. AccessToken: {}" ,jwtToken.getAccessToken());
		log.info( "RefreshToken 을 발급합니다. RefreshToken: {}" ,jwtToken.getRefreshToken());

		SecurityContext context = SecurityContextHolder.createEmptyContext();//5
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		LoginDto loginDto = LoginDto.builder()
				.memberId(memberId)
				.accessToken(jwtToken.getAccessToken())
				.refreshToken(jwtToken.getRefreshToken())
				.build();

		response.getWriter().write(objectMapper.writeValueAsString(
				ApiResponse.onSuccess(loginDto)
		));
	}

	private String extractMemberId(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}
	private String extractPassword(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getPassword();
	}
}
