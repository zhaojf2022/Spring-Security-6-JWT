package com.ons.securejwt.service;

import com.ons.securejwt.dto.BearerToken;
import com.ons.securejwt.dto.LoginDto;
import com.ons.securejwt.dto.RegisterDto;
import com.ons.securejwt.models.Role;
import com.ons.securejwt.models.RoleName;
import com.ons.securejwt.models.User;
import com.ons.securejwt.models.IRoleRepository;
import com.ons.securejwt.models.IUserRepository;
import com.ons.securejwt.utilt.JwtUtilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final IUserRepository iUserRepository;
    private final IRoleRepository iRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;


    @Override
    public Role saveRole(Role role) {
        return iRoleRepository.save(role);
    }

    @Override
    public User saverUser(User user) {
        return iUserRepository.save(user);
    }

    /**
     * 用户注册
     * @param registerDto RegisterDto
     * @return ResponseEntity<?>
     */
    @Override
    public ResponseEntity<?> register(RegisterDto registerDto) {

        if (Boolean.TRUE.equals(iUserRepository.existsByEmail(registerDto.getEmail()))) {
            return new ResponseEntity<>("邮箱已经被使用 !", HttpStatus.SEE_OTHER);
        } else {
            // 创建新的User对象，填写注册用户的信息
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            // 缺省的角色是'user'
            Role role = iRoleRepository.findByRoleName(RoleName.USER);
            // Collections.singletonList(role)  创建了一个只包含一个元素的列表，元素为变量 role
            user.setRoles(Collections.singletonList(role));

            // 调用接口方法保存用户
            iUserRepository.save(user);
            // 生成token
            String token = jwtUtilities.generateToken(registerDto.getEmail(),
                Collections.singletonList(role.getRoleName()));

            // 创建token包装对象（指定 token 类型为："Bearer "），并返回包含这个token包装器对象的响应实体
            return new ResponseEntity<>(new BearerToken(token, "Bearer "), HttpStatus.OK);

        }
    }

    /**
     * 验证用户名称，返回 JWT 令牌
     * @param loginDto LoginDto
     * @return String
     */
    @Override
    public String authenticate(LoginDto loginDto) {

        // 使用传递的登录参数，创建一个认证对象
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
            )
        );

        // 将此认证对象设置到 SecurityContextHolder 的上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 查询账户是否存在，不存在则抛出异常
        User user = iUserRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        // 遍历用户的角色列表，并将每个角色名称添加到 rolesNames 列表中
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r -> rolesNames.add(r.getRoleName()));

        // 生成一个JWT令牌，并返回给调用者
        return jwtUtilities.generateToken(user.getUsername(), rolesNames);
    }

}

