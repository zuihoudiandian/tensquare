package com.tensquare.notice.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_notice_fresh")
public class NoticeFresh {

    private String userId;
    private String noticeId;
}
