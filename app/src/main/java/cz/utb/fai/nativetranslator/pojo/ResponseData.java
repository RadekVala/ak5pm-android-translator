package cz.utb.fai.nativetranslator.pojo;
import com.squareup.moshi.Json;

public class ResponseData {
    @Json(name = "translatedText")
    public String translatedText;
}
