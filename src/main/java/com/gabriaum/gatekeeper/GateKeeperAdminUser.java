package com.gabriaum.gatekeeper;

import com.gabriaum.gatekeeper.infra.service.CryptographyService;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.enums.GateUserRole;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GateKeeperAdminUser implements CommandLineRunner {
    private final GateUserRepository repository;
    private final CryptographyService cryptographyService;

    @Override
    public void run(String... args) throws Exception {
        if (!repository.existsByRole(GateUserRole.ADMIN)) {
            GateUser gateUser = new GateUser();
            gateUser.setCpf("admin");
            gateUser.setEmail("admin");
            gateUser.setRole(GateUserRole.ADMIN);
            gateUser.setPassword(cryptographyService.encryptPassword("admin"));

            repository.save(gateUser);

            log.info("O usuário administrativo foi criado com sucesso.");
            log.info("");
            log.info("Usuário: {}", gateUser.getUsername());
            log.info("Senha: {}", "admin [" + gateUser.getPassword() + "]");
            log.info("");
            log.warn("[!] Altere as credenciais assim que possível!");
        }
    }
}