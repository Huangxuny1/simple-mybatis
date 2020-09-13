package com.huangxunyi;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Log {
    private String id;
    private String create_time;
    private String ip;
    private String message;
    private String success;
    private String type;
    private String user_id;

}