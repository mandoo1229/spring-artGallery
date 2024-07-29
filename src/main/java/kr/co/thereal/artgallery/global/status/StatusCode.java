package kr.co.thereal.artgallery.global.status;

import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS(1, "정상 처리 되었습니다."),
    ERROR(0,"에러가 발생했습니다.");

    private final int result;
    private final String message;

    StatusCode(int result, String message) {
        this.result = result;
        this.message = message;
    }

}
