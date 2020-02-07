package com.wkcto.crm.settings.domain;

/**
 * 蛙课网
 */
public class User {

    /*

        1.关于主键
            我们以前使用的主键是int/long类型的（整型）
            方便：主键不用我们自己填写，能够自动递增

            insert into tbl_student(name,age) values(?,?)

            tbl_student
            id          name        age
            1           wyf         23
            2           lh          24
            3           cxk         25
            4           hzt         26
            5           lyf         27
            --6           zyx         28

            主键自动递增：
            影响sql的添加效率
            既然是数值，如果条数比较多，则上限难于管理
            不利于数据库的移植工作

            未来的实际项目开发，如果我们确定表中的数据量比较少，那么我们就可以使用主键自动递增机制，开发起来非常方便

            一般的表，我们并不推荐使用主键自动递增机制

            我们都是使用字符串来当做主键的类型
            非空+唯一

            随机数：9128378124817
            时间：20191220114917123

            UUID,是java.util包为我们提供的随机生成的字符串
            该字符串是由数字，字母和横杆(-)所组成的36为的随机串
            每一次使用UUID机制生成的随机串，一定是全世界唯一的

            (1)UUID为什么是全世界唯一的
                随机数
                时间
                当前生成UUID所在硬件的机器编号

            (2)如果主键是UUID生成的32位的随机串，我们应该在表中为字段赋予什么类型

                          aaa

                varchar(32):变长 "aaa"


                char(32):定长 "aaa                          "


        2.关于时间

            在实际项目开发中，使用字符串表示的日期及时间，有着约定俗成的默认的格式

            日期：yyyy-MM-dd 10位
            时间：yyyy-MM-dd HH:mm:ss 19位


        3.关于登录

            （1）验证账号密码是否正确

            （2）验证失效时间

            （3）验证锁定状态

            （4）验证ip地址

     */

    private String id;  //主键
    private String loginAct;    //登录账号
    private String name;    //用户真实姓名
    private String loginPwd;    //登录密码
    private String email;   //邮箱
    private String expireTime;  //失效时间  yyyy-MM-dd HH:mm:ss 19位
    private String lockState;   //锁定状态
    private String deptno;  //部门编号
    private String allowIps;    //允许访问的ip地址群
    private String createTime;  //创建时间  yyyy-MM-dd HH:mm:ss 19位
    private String createBy;    //创建人
    private String editTime;    //修改时间  yyyy-MM-dd HH:mm:ss 19位
    private String editBy;  //修改人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}
