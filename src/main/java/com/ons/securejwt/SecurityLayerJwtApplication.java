package com.ons.securejwt;

import com.ons.securejwt.service.UserService;
import com.ons.securejwt.models.Role;
import com.ons.securejwt.models.RoleName;
import com.ons.securejwt.models.User;
import com.ons.securejwt.models.IRoleRepository;
import com.ons.securejwt.models.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SecurityLayerJwtApplication {

    public static void main(String[] args) {

        /* 在启动目录下生成一个 app.id的文件。停止服务时，在启动目录中执行以下语句：'cat ./app.pid | xargs kill' */
        SpringApplication application = new SpringApplication(SecurityLayerJwtApplication.class);
        application.addListeners(new ApplicationPidFileWriter("app.pid"));
        application.run(args);
    }

    /**
     * 初始化 Demo 所需的数据
     *
     * @param userService     UserService
     * @param iRoleRepository IRoleRepository
     * @param iUserRepository IUserRepository
     * @param passwordEncoder PasswordEncoder
     * @return CommandLineRunner
     */
    @Bean
    CommandLineRunner run(UserService userService, IRoleRepository iRoleRepository, IUserRepository iUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userService.saveRole(new Role(RoleName.USER));
            userService.saveRole(new Role(RoleName.ADMIN));
            userService.saveRole(new Role(RoleName.SUPERADMIN));
            userService.saveRole(new Role(RoleName.SERVICE));

            userService.saverUser(new User("13801001234", passwordEncoder.encode("adminPassword"), new ArrayList<>()));
            userService.saverUser(new User("13901001234", passwordEncoder.encode("superadminPassword"), new ArrayList<>()));

            Role role = iRoleRepository.findByRoleName(RoleName.ADMIN);
            User user = iUserRepository.findByMobile("13801001234").orElse(null);
            if (user != null) {
                user.getRoles().add(role);
            }
            userService.saverUser(user);

            User userr = iUserRepository.findByMobile("13901001234").orElse(null);
            Role rolee = iRoleRepository.findByRoleName(RoleName.SUPERADMIN);
            if (userr != null) {
                userr.getRoles().add(rolee);
            }
            userService.saverUser(userr);
        };
    }

}

