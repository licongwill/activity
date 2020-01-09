package com.me.activity.po;

import java.util.Date;
import java.util.Set;

/**
 * 用户po
 */
public class MeSysUser {
    private Long id;//主键id

    private String account;//账户名称

    private String multiQuery;//复合查询字段

    private String password;//密码

    private String realname;//真实名称

    private String nickname;//昵称

    private Date birthday;//生日

    private String staffNum;//工号

    private Byte sex;//性别

    private Integer age;//年龄

    private Byte channel;//渠道

    private Byte userStatus;//用户状态

    private String mobile;//手机

    private String email;//邮件

    private String tel;//电话

    private String address;//住址

    private String imgUrl;//图片地址

    private String industryName;//行业名称

    private String industryCode;//行业编码

    private Byte education;//学历

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

    private String createUser;//创建用户

    private String updateUser;//修改用户

    private Byte yn;//有效性;0--无效,1--有效

    private String remark;//备注信息

    private  String type;//类型

    private Set<Long> userIdSet; // 用户id集合

    /**
     * OA表的ID
     */
    private String oaId;

    /**
     * 在职状态（1-在职，0-离职）
     */
    private Byte state;

    /**
     * 是否有配置系统的权限：1-有 0-否
     */
    private Byte isSysAllocation;

    private String superiorUserId; //用户上级id

    /**
     * 离职时间
     */
    private Date leaveDate;

    /**
     *  员工类型（0-无,1-一线,2-不区分,3-管理者）
     */
    private Byte managerOrFrontline;

    public String getSuperiorUserId() {
        return superiorUserId;
    }

    public void setSuperiorUserId(String superiorUserId) {
        this.superiorUserId = superiorUserId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getOaId() {
        return oaId;
    }

    public void setOaId(String oaId) {
        this.oaId = oaId;
    }

    public Set<Long> getUserIdSet()
    {
        return userIdSet;
    }

    public void setUserIdSet(Set<Long> userIdSet)
    {
        this.userIdSet = userIdSet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum == null ? null : staffNum.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName == null ? null : industryName.trim();
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode == null ? null : industryCode.trim();
    }

    public Byte getEducation() {
        return education;
    }

    public void setEducation(Byte education) {
        this.education = education;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Byte getYn() {
        return yn;
    }

    public void setYn(Byte yn) {
        this.yn = yn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMultiQuery() {
        return multiQuery;
    }

    public void setMultiQuery(String multiQuery) {
        this.multiQuery = multiQuery;
    }

    public Byte getIsSysAllocation()
    {
        return isSysAllocation;
    }

    public void setIsSysAllocation(Byte isSysAllocation)
    {
        this.isSysAllocation = isSysAllocation;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Byte getManagerOrFrontline() {
        return managerOrFrontline;
    }

    public void setManagerOrFrontline(Byte managerOrFrontline) {
        this.managerOrFrontline = managerOrFrontline;
    }
}