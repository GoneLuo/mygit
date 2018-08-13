package com.luoy.library.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luoy.library.common.util.ConstantsUtils;
import com.luoy.library.dao.ISingleBookDao;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.SingleBook;
import com.luoy.library.service.ISingleBookService;
import com.luoy.library.service.util.BaseServiceImpl;

@Service
@Transactional
public class SingleBookServiceImpl extends BaseServiceImpl<SingleBook> implements ISingleBookService {
	
	@Autowired
	private ISingleBookDao singleBookDao;

	@Override
	public IBaseDao<SingleBook> getDao() {
		return (IBaseDao<SingleBook>)singleBookDao;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SingleBook> getListByBookSetId(String bookSetId) {
		Map<String, Object> params = new HashMap();
		if (StringUtils.isNoneBlank(bookSetId)) {
			params.put("bookSetId", bookSetId);
		}
		
		return singleBookDao.queryListByParams(params);
	}

	@Override
	public List<SingleBook> getUsefulListByBookSet(String bookSetId) {
		if (StringUtils.isBlank(bookSetId)) {
			return null;
		}
		
		Map<String, Object> params = new HashMap();
		params.put("bookSetId", bookSetId);
		params.put("isCollected", ConstantsUtils.JUDGE_NOT);
		params.put("isBorrowed", ConstantsUtils.JUDGE_NOT);
		List<SingleBook> singleBooks = singleBookDao.queryListByParams(params);
		
		return singleBooks;
	}

}
