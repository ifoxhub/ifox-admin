package com.ifox.admin.modules.ums.service.impl;

import com.ifox.admin.common.exception.Asserts;
import com.ifox.admin.domain.AdminUserDetails;
import com.ifox.admin.modules.ums.dto.UmsAdminParam;
import com.ifox.admin.modules.ums.dto.UpdateAdminPasswordParam;
import com.ifox.admin.modules.ums.model.*;
import com.ifox.admin.modules.ums.repository.UmsAdminLoginLogRepository;
import com.ifox.admin.modules.ums.repository.UmsAdminRepository;
import com.ifox.admin.modules.ums.repository.UmsResourceRepository;
import com.ifox.admin.modules.ums.repository.UmsRoleRepository;
import com.ifox.admin.modules.ums.service.UmsAdminCacheService;
import com.ifox.admin.modules.ums.service.UmsAdminRoleRelationService;
import com.ifox.admin.modules.ums.service.UmsAdminService;
import com.ifox.admin.security.util.JoseJwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/6
 */
@Service
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private JoseJwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Autowired
    private UmsAdminRoleRelationService adminRoleRelationService;

    @Resource
    private UmsAdminRepository adminRepository;

    @Resource
    private UmsRoleRepository roleRepository;

    @Resource
    private UmsResourceRepository resourceRepository;

    @Resource
    private UmsAdminLoginLogRepository adminLoginLogRepository;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdmin admin = adminCacheService.getAdmin(username);
        if (admin!=null) {
            return  admin;
        }
        List<UmsAdmin> adminList = adminRepository.findAllByUsername(username);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            adminCacheService.setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        List<UmsAdmin> umsAdminList = adminRepository.findAllByUsername(umsAdmin.getUsername());
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminRepository.save(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateJwtToken(userDetails);
            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin==null) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        adminLoginLogRepository.save(loginLog);
    }

    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        List<UmsAdmin> umsAdminList = adminRepository.findAllByUsername(username);
        umsAdminList.forEach(umsAdmin -> umsAdmin.setLoginTime(new Date()));
        adminRepository.saveAll(umsAdminList);
    }

    @Override
    public String refreshToken(String oldToken) throws Exception {
        return jwtTokenUtil.refreshToken(oldToken);
    }

    @Override
    public Page<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        Pageable pageable = PageRequest.of(--pageNum, pageSize);
        if(Strings.isNotEmpty(keyword)){
            return adminRepository.findAllByKeyword(keyword, pageable);
        }
        return adminRepository.findAll(pageable);
    }

    @Override
    public boolean update(Long id, UmsAdmin admin) {
        admin.setId(id);
        UmsAdmin rawAdmin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        if(rawAdmin.getPassword().equals(admin.getPassword())){
            //与原加密密码相同的不需要修改
            admin.setPassword(null);
        }else{
            //与原加密密码不同的需要加密修改
            if(Strings.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else{
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        adminRepository.save(admin);
        adminCacheService.delAdmin(id);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        adminCacheService.delAdmin(id);
        adminRepository.deleteById(id);
        adminCacheService.delResourceList(id);
        return true;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        adminRoleRelationService.remove(adminId);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation roleRelation = new UmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationService.saveBatch(list);
        }
        adminCacheService.delResourceList(adminId);
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return roleRepository.getAllByAdminId(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList = adminCacheService.getResourceList(adminId);
        if(!CollectionUtils.isEmpty(resourceList)){
            return  resourceList;
        }
        resourceList = resourceRepository.findAllByAdminId(adminId);
        if(!CollectionUtils.isEmpty(resourceList)){
            adminCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if(Strings.isEmpty(param.getUsername())
                ||Strings.isEmpty(param.getOldPassword())
                ||Strings.isEmpty(param.getNewPassword())){
            return -1;
        }
        List<UmsAdmin> adminList = adminRepository.findAllByUsername(param.getUsername());
        if(CollectionUtils.isEmpty(adminList)){
            return -2;
        }
        UmsAdmin umsAdmin = adminList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),umsAdmin.getPassword())){
            return -3;
        }
        umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        adminRepository.save(umsAdmin);
        adminCacheService.delAdmin(umsAdmin.getId());
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public UmsAdmin getById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }
}
