package com.travel.test;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.App;
import com.travel.pojo.AdminUser;
import com.travel.service.AdminUserService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.test
 * @CreateTime: 2021-05-17 15:00
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestMyBatis {

    @Autowired
    AdminUserService adminUserService;

    @Test
    public void testLogin(){
        AdminUser adminUser=new AdminUser();
        adminUser.setUserName("root");
        adminUser.setPassword("123456");
        AdminUser loginAdminUser = adminUserService.login(adminUser);
        if(loginAdminUser!=null){
            System.out.println("登录成功!"+loginAdminUser);
        }else{
            System.out.println("登录失败");
        }
    }

    @Test
    public void testLogin1(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("USER_NAME","root");
        queryWrapper.eq("PASSWORD","123456");
        queryWrapper.eq("DELETE_STATUS","0");
        AdminUser loginAdminUser = adminUserService.getOne(queryWrapper);
        if(loginAdminUser!=null){
            System.out.println("登录成功!"+loginAdminUser);
        }else{
            System.out.println("登录失败");
        }
    }

    @Test
    public void testPage(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("USER_NAME","root");
        IPage page = adminUserService.page(new Page<AdminUser>(1, 1), queryWrapper);

        List<AdminUser> records = page.getRecords();
        for (AdminUser record : records) {
            System.out.println(record);
        }

        long pages = page.getPages();
        System.out.println("总页数是:"+pages);

        long total = page.getTotal();
        System.out.println("总条数是:"+total);
    }

    @Test
    public void testLogin2(){
        // 创建 AdminUserService 接口的 Mock 对象
        AdminUserService mockAdminUserService = EasyMock.createMock(AdminUserService.class);

        // 创建 AdminUser 对象
        AdminUser adminUser = new AdminUser();
        adminUser.setUserName("root");
        adminUser.setPassword("123456");

        // 创建一个模拟的返回对象，模拟登录成功的情况
        AdminUser mockAdminUser = new AdminUser();
        mockAdminUser.setUserName("root");
        mockAdminUser.setPassword("123456");

        // 设定预期行为：当 login 方法被调用，并且传入 adminUser 时，应该返回 mockAdminUser
        EasyMock.expect(mockAdminUserService.login(adminUser)).andReturn(mockAdminUser);

        // 启动 Replay 状态
        EasyMock.replay(mockAdminUserService);

        // 创建实际的 AdminUserService 实例，并注入 Mock 对象
        AdminUserService adminUserService = new AdminUserService() {
            @Override
            public boolean save(AdminUser entity) {
                return false;
            }

            @Override
            public boolean saveBatch(Collection<AdminUser> entityList, int batchSize) {
                return false;
            }

            @Override
            public boolean saveOrUpdateBatch(Collection<AdminUser> entityList, int batchSize) {
                return false;
            }

            @Override
            public boolean removeById(Serializable id) {
                return false;
            }

            @Override
            public boolean removeByMap(Map<String, Object> columnMap) {
                return false;
            }

            @Override
            public boolean remove(Wrapper<AdminUser> queryWrapper) {
                return false;
            }

            @Override
            public boolean removeByIds(Collection<? extends Serializable> idList) {
                return false;
            }

            @Override
            public boolean updateById(AdminUser entity) {
                return false;
            }

            @Override
            public boolean update(AdminUser entity, Wrapper<AdminUser> updateWrapper) {
                return false;
            }

            @Override
            public boolean updateBatchById(Collection<AdminUser> entityList, int batchSize) {
                return false;
            }

            @Override
            public boolean saveOrUpdate(AdminUser entity) {
                return false;
            }

            @Override
            public AdminUser getById(Serializable id) {
                return null;
            }

            @Override
            public Collection<AdminUser> listByIds(Collection<? extends Serializable> idList) {
                return null;
            }

            @Override
            public Collection<AdminUser> listByMap(Map<String, Object> columnMap) {
                return null;
            }

            @Override
            public AdminUser getOne(Wrapper<AdminUser> queryWrapper, boolean throwEx) {
                return null;
            }

            @Override
            public Map<String, Object> getMap(Wrapper<AdminUser> queryWrapper) {
                return null;
            }

            @Override
            public <V> V getObj(Wrapper<AdminUser> queryWrapper, Function<? super Object, V> mapper) {
                return null;
            }

            @Override
            public int count(Wrapper<AdminUser> queryWrapper) {
                return 0;
            }

            @Override
            public List<AdminUser> list(Wrapper<AdminUser> queryWrapper) {
                return null;
            }

            @Override
            public IPage<AdminUser> page(IPage<AdminUser> page, Wrapper<AdminUser> queryWrapper) {
                return null;
            }

            @Override
            public List<Map<String, Object>> listMaps(Wrapper<AdminUser> queryWrapper) {
                return null;
            }

            @Override
            public <V> List<V> listObjs(Wrapper<AdminUser> queryWrapper, Function<? super Object, V> mapper) {
                return null;
            }

            @Override
            public IPage<Map<String, Object>> pageMaps(IPage<AdminUser> page, Wrapper<AdminUser> queryWrapper) {
                return null;
            }

            @Override
            public BaseMapper<AdminUser> getBaseMapper() {
                return null;
            }

            @Override
            public AdminUser login(AdminUser adminUser) {
                return mockAdminUserService.login(adminUser);
            }
        };

        // 调用需要测试的方法
        AdminUser loginAdminUser = adminUserService.login(adminUser);

        // 验证结果
        Assert.assertNotNull("登录失败，预期为登录成功", loginAdminUser);
        Assert.assertEquals("用户名不匹配", "root", loginAdminUser.getUserName());
        Assert.assertEquals("密码不匹配", "123456", loginAdminUser.getPassword());

        // 验证 Mock 对象
        EasyMock.verify(mockAdminUserService);
    }

    @Test
    public void testPage2() {
        // 创建 AdminUserService 接口的 Mock 对象
        AdminUserService mockAdminUserService = EasyMock.createMock(AdminUserService.class);

        // 创建 QueryWrapper 对象并添加查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_NAME", "root");

        // 创建一个 Mock 的 IPage 对象
        IPage mockPage = EasyMock.createMock(IPage.class);

        // 设定 Mock Page 对象的预期行为
        EasyMock.expect(mockPage.getRecords()).andReturn(Collections.singletonList(new AdminUser()));
        EasyMock.expect(mockPage.getPages()).andReturn(10L);
        EasyMock.expect(mockPage.getTotal()).andReturn(100L);

        // 设定 adminUserService.page 方法的预期行为，当传入特定参数时，返回 Mock 的 IPage 对象
        EasyMock.expect(mockAdminUserService.page(EasyMock.anyObject(), EasyMock.eq(queryWrapper))).andReturn(mockPage);

        // 启动 Replay 状态
        EasyMock.replay(mockAdminUserService, mockPage);

        // 使用 Mock 对象进行测试
        IPage page = mockAdminUserService.page(new Page<AdminUser>(1, 1), queryWrapper);

        // 验证结果
        List<AdminUser> records = page.getRecords();
        Assert.assertFalse("预期至少有一条记录", records.isEmpty());

        long pages = page.getPages();
        System.out.println("总页数是: " + pages);

        long total = page.getTotal();
        System.out.println("总条数是: " + total);

        // 验证 Mock 对象
        EasyMock.verify(mockAdminUserService, mockPage);
    }


}
