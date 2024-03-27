package com.ridetogether.server.global.apiPayload.code.status;

import com.ridetogether.server.global.apiPayload.code.BaseErrorCode;
import com.ridetogether.server.global.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "접근 권한이 없는 요청입니다."),
    _LOGIN_FAILURE(HttpStatus.NOT_FOUND, "COMMON404", "요청 리소스를 찾을 수 없습니다."),
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _SERVICE_UNAVAILABLE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "COMMON503", "서버가 일시적으로 사용중지 되었습니다."),

    //유저 응답
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "유저가 존재하지 않습니다."),
    MEMBER_EMAIL_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "MEMBER4002", "이메일 또는 비밀번호가 일치하지 않습니다."),
    MEMBER_EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4003", "중복된 이메일입니다."),
    MEMBER_NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4004", "중복된 닉네임입니다."),
    MEMBER_STATE_INACTIVE(HttpStatus.BAD_REQUEST, "MEMBER4005", "휴면상태의 유저입니다."),
    MEMBER_STATE_NOT_STUDENT(HttpStatus.BAD_REQUEST, "MEMBER4006", "한양대학교 학생 인증이 미완료된 사용자입니다."),
    MEMBER_STATE_NOT_ADMIN(HttpStatus.FORBIDDEN, "MEMBER4007", "접근 권한이 없습니다"),

    //앨범 응답
    ALBUM_NOT_FOUND(HttpStatus.BAD_REQUEST, "ALBUM4001", "앨범이 존재하지 않습니다."),

    //사진 응답
    PHOTO_NOT_FOUND(HttpStatus.BAD_REQUEST, "PHOTO4001", "사진이 존재하지 않습니다."),

    //피드 응답
    FEED_NOT_FOUND(HttpStatus.BAD_REQUEST, "FEED4001", "피드가 존재하지 않습니다."),

    //푸시알림 응답
    ALARM_SEND_FAIL(HttpStatus.BAD_REQUEST,"ALARM4001", "알림 보내기를 실패하였습니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
            .message(message)
            .code(code)
            .isSuccess(false)
            .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
            .message(message)
            .code(code)
            .isSuccess(false)
            .httpStatus(httpStatus)
            .build();
    }
}