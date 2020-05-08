package com.tensquare.notice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tensquare.notice.client.ArticleClient;
import com.tensquare.notice.client.UserClient;
import com.tensquare.notice.mapper.NoticeFreshMapper;
import com.tensquare.notice.mapper.NoticeMapper;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeDao;

    @Autowired
    private NoticeFreshMapper noticeFreshDao;
    @Autowired
    private ArticleClient articleClient;
    @Autowired
    private UserClient userClient;

    @Autowired
    private IdWorker idWorker;

    //完善消息通知
    public void getInfo(Notice notice){
        //通过消息ID 查询用户昵称。
        Result result = userClient.selectById(notice.getOperatorId());
        //用户对象强转成 hashMap 通过KEY 拿到value
        HashMap userMap = (HashMap) result.getData();
        notice.setOperatorName(userMap.get("nickname").toString());
        //通过文章ID 查询文章信息。
        Result byId = articleClient.findById(notice.getTargetId());
        HashMap articleMap = (HashMap) byId.getData();
        notice.setTargetName(articleMap.get("title").toString());
    }
    public Notice selectById(String id) {
        Notice notice = noticeDao.selectById(id);
        //完善消息  补充点赞用户名和被赞文章。
        getInfo(notice);
        return notice;
    }
    public IPage<Notice> selectByPage(Notice notice, Integer page, Integer size) {
        //封装分页对象
        Page<Notice> pageData = new Page<>(page, size);
        //执行分页查询
        IPage<Notice> noticeIPage = noticeDao.selectPage(pageData, new QueryWrapper<>(notice));

        //完善消息
        List<Notice> records = noticeIPage.getRecords();
        for (Notice record : records) {
            getInfo(record);
        }
        //返回
        return noticeIPage;
    }
    public void save(Notice notice) {
        //设置初始值
        //设置状态 0表示未读  1表示已读
        notice.setState("0");
        notice.setCreatetime(new Date());
        //使用分布式Id生成器，生成id
        String id = idWorker.nextId() + "";
        notice.setId(id);
        noticeDao.insert(notice);
        //待推送消息入库，新消息提醒  存在mysql中，用了mq 不需要保存。
//        NoticeFresh noticeFresh = new NoticeFresh();
//        noticeFresh.setNoticeId(id);//消息id
//        noticeFresh.setUserId(notice.getReceiverId());//待通知用户的id
//        noticeFreshDao.insert(noticeFresh);

    }
    public void updateById(Notice notice) {
        noticeDao.updateById(notice);
    }
    public IPage<NoticeFresh> freshPage(String userId, Integer page, Integer size) {
        //封装查询条件
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setUserId(userId);
        //创建分页对象
        Page<NoticeFresh> pageData = new Page<>(page, size);
        //执行查询
        IPage<NoticeFresh> noticeFreshIPage = noticeFreshDao.selectPage(pageData, new QueryWrapper<>(noticeFresh));

        //返回结果
        return noticeFreshIPage;
    }
    public void freshDelete(NoticeFresh noticeFresh) {
        noticeFreshDao.delete(new QueryWrapper<>(noticeFresh));
    }
}