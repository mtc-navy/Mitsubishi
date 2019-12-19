package com.y1ang.service.impl;

import com.y1ang.dao.FeedMapper;
import com.y1ang.entity.Feed;
import com.y1ang.entity.Page;
import com.y1ang.service.IFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class IFeedServiceImpl implements IFeedService {

    @Autowired
    private FeedMapper dao;

    @Override
    public List<Feed> findFeedRecord(Page page) {
        List<Feed> feedList = dao.findFeedRecord(page);
        feedList.forEach(a -> {
            if (!StringUtils.isEmpty(a.getSendDate())) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(a.getSendDate());
                    SimpleDateFormat formatDisplay = new SimpleDateFormat("yy/MM/dd");
                    a.setSendDate(formatDisplay.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return feedList;
    }

    @Override
    public Feed findFeedRecordByID(int feedID) {
        return dao.findFeedRecordByID(feedID);
    }

    @Override
    public int updateFeedRecord(Feed feed) {
        return dao.updateFeedRecord(feed);
    }

    @Override
    public int deleteFeedRecord(int feedID) {
        return dao.deleteFeedRecord(feedID);
    }

    @Override
    public int addFeedRecord(Feed feed) {
        return dao.addFeedRecord(feed);
    }

    @Override
    public int getFeedCount(int batchNumber) {
        return dao.getFeedCount(batchNumber);
    }

}
