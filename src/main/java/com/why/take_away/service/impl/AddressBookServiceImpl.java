package com.why.take_away.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.take_away.entitty.AddressBook;
import com.why.take_away.mapper.AddressBookMapper;
import com.why.take_away.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
