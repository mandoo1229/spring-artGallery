package kr.co.thereal.artgallery.global.response;

import kr.co.thereal.artgallery.global.status.StatusCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class DataHeader {
    private int successCode;
    private String resultCode;
    private String resultMessage;

    public DataHeader(int successCode, String resultCode, String resultMessage) {
        this.successCode = successCode;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public static DataHeader ok() {
        return new DataHeader(StatusCode.SUCCESS.getResult(),
                HttpStatus.OK.value() + " " + HttpStatus.OK.name(),
                StatusCode.SUCCESS.getMessage());
    }

    public static DataHeader error(HttpStatus status) {
        return new DataHeader(StatusCode.ERROR.getResult(),
                status.value() + " " + status.name(),
                StatusCode.ERROR.getMessage());
    }
}