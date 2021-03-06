package com.example.demo.serviceImpl;

import com.example.demo.daoImpl.UserMapper;
import com.example.demo.mapper.DeptMapper;
import com.example.demo.model.Dept;
import com.example.demo.model.User;
import com.example.demo.serviceImpl.service.IUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    //@Autowired
    //private DataSourceTransactionManager transactionManager;
    @Autowired
    private DeptMapper deptMapper;
    @Override
    public Long addUser(User user) {
        Long res=null;
       // TransactionStatus status=transactionManager.getTransaction(new DefaultTransactionDefinition());
         try {
             res=userMapper.insert(user.getName(), user.getAge());
            //transactionManager.commit(status);
         }catch(Exception e){
            // transactionManager.rollback(status);
         }

        return res;
    }

    @Override
    @Transactional
    public User getByName(String userName) {
        return userMapper.getByName(userName);
    }

    @Override
    public Long addDept(Dept dept) {
        return deptMapper.insertDept(dept);
    }

    @Override
    public PageInfo<User> listUser(int pageSize, int pageNo) {
        PageHelper.startPage(pageNo,pageSize);
        List<User> users=userMapper.listUser();
        PageInfo<User> res=new PageInfo<User>(users);
        return res;
    }
}
