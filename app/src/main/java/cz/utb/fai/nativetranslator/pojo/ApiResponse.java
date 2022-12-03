package cz.utb.fai.nativetranslator.pojo;

import com.squareup.moshi.Json;

public class ApiResponse {
    @Json(name = "responseData")
    public ResponseData responseData;
}